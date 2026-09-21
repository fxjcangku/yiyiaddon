# 复盘：后台发消息玩家收不到（ChatService 轮询从未启动）

日期：2026-09-21

## 一、用户反馈

用户报：后台管理面板的聊天功能根本没生效 —— 在后台给玩家发消息，玩家收不到。

## 二、取证

1. **后台侧正常**。`03-后端API/src/worker.js` 第 955 行 `/api/messages/send` 写库成功（面板 `admin-modern.js`
   第 1489-1502 行 `{message, target_name}` + 选中玩家时补 `target_uuid`），第 1004 行 `/api/messages/poll`
   按 `target_uuid` 取定向消息、按 `target_uuid IS NULL` 取广播并按 `message_reads` 去重。
2. **客户端侧断链**。`src/main/java/com/yiyiaddon/service/ChatService.java` 里写好了完整的轮询链路：
   - 第 40 行 `start()`（每 3 秒 `BackgroundTasks.schedule` 打一次 `/api/messages/poll`）；
   - 第 48 行 `stop()`；第 102 行 `pollNow()`（拉取并把消息 `ClientChat.raw` 进聊天栏）。
3. **`ChatService` 在整个 `src/` 里只有 3 处命中，全在它自己文件内**：类声明（25）、私有构造（36）、
   自身方法引用（44）。`YiyiAddonClient.onInitializeClient()`（第 35-82 行）只挂了
   `RegisterService.register()` / `HeartbeatService.reportOffline()`，**没有 `ChatService.start()` / `stop()`**。
4. `03-后端API/README.md` 第 93-94 行明确写着本应接线：
   「玩家进服：`RegisterService.register()`、`ChatService.start()`；玩家断开：`HeartbeatService.reportOffline()`、
   `ChatService.stop()`、`RegisterService.reset()`」。
5. 结论：消息一直能写进 D1，但客户端从未发起过轮询 —— 表现就是「后台发得出、玩家永远收不到」。

## 三、修改

只动一处，`src/main/java/com/yiyiaddon/YiyiAddonClient.java`：

1. 新增订阅所有者常量 `CHAT_OWNER = "service.chat"`（与统计链路的 `STATS_OWNER` 并列，互不影响退订粒度）；
2. 新增 `import com.yiyiaddon.service.ChatService;`；
3. 在统计链路订阅之后，按 README 的接线契约补两条订阅：
   - `JOIN_SERVER` → `ChatService.start()`
   - `DISCONNECT` → `ChatService.stop()`

`ChatService` 本身一行未改（`start()` 幂等 + `stop()` 置空，重连可反复进出）。

## 四、验证结果

- `.\gradlew build --console=plain -q`：**EXIT=0**；
- 编译产物核对：`build/classes/java/main/com/yiyiaddon/YiyiAddonClient.class` 字节码中已含 `ChatService` 引用，
  `build/libs/yiyiaddon-1.0-beta1-personal+26.1.2.jar` 已重新生成（18:13:39）；
- **后端链路端到端实测**（生产 D1 + 生产 Worker，非推断）：
  1. 用 `wrangler d1 execute --remote` 向 `messages` 插入一条定向给测试 UUID 的管理员消息（id 40）；
  2. `POST https://yiyiaddon.asia/api/messages/poll {"uuid":"TEST-UUID-DELETEME"}` → `count:11`，含该条；
  3. 再 poll 一次 → `count:0`（`delivered=1` 去重生效）；
  4. 用 UTF-8 原始字节解码响应，管理员中文消息内容完整（`有人吗嘻嘻` / `测试一下` / `加我qq488733117` …），
     `Content-Type` 未带 charset，但 `HttpApi` 用 JDK `BodyHandlers.ofString()`（缺省 UTF-8），链路无乱码；
  5. 测试数据已清理（删除 id 40 与 4 个测试 UUID 的 `message_reads`，共 41 行）；
- **未实机**：未进游戏跑 `runClient`，玩家端「聊天栏真的弹出消息」仍需实机确认（本次改动不涉及 Mixin）。

## 五、顺带查出的三处遗留（**均未改动，等用户决定**）

1. **旧广播会在玩家下次进服时一次性刷屏**：`message_reads` 里只有部分老玩家的记录，
   `messages` 表中 8 条管理员广播（id 30/32/33/34/35/36/38/39，内容为「测试一下」「1」「有人吗」这类）
   对另外十几个玩家仍是未读 —— 修复上线后他们会一口气收到这些旧消息。
   面板「系统设置 ▸ 立即清理」（`/api/admin/clean-old-data`，worker.js 第 1143 行）可清空聊天记录，
   但会**连指令记录一起清**；需要只清这 8 条广播可以单独出一条 SQL。
2. **`TelemetryService` 同样从未启动**：`src/` 中同样只有类内引用，README 第 92 行要求它在客户端初始化里
   `start()` —— 即崩溃/异常上报当前也是断的。
3. **玩家侧发不出消息**：`ChatService` 的 `reply()`（回复管理员）、`send()/broadcast()`（公共聊天）、
   `onlinePlayers()` 全部没有调用点，旧项目对应的指令类（`YiyiaddonChatCommand`、`ReplyAdminCommand`）
   没有迁进本项目；但 `ChatService.render()` 第 145 行还在给玩家打印
   「使用 `.回复 <内容>` 回复」——**提示了一个不存在的指令**。

## 六、下一阶段开发规划

### 一、下一阶段目标

1. 用户实机确认管理员消息能进聊天栏；
2. 决定第 5 节三处遗留的处理方式。

### 二、执行原因

后端已端到端实测通过、客户端接线已编译进包，但「游戏里真的弹出来」只有实机能证明。

### 三、前置条件检查

- 已完成：根因定位、接线、编译、后端端到端实测、测试数据清理；
- 依赖：用户换上新 jar 进服测试。

### 四、任务拆分

1. 目标：实机验收；涉及目录：无代码改动；影响范围：进服后聊天栏。
2. 目标（待定）：清理 8 条旧广播，或由用户点面板「立即清理」。
3. 目标（待定）：补 `TelemetryService.start()`，恢复崩溃上报。
4. 目标（待定）：按旧项目口径移植玩家侧聊天指令（`.回复` 等），需要用户先同意。

### 五、验收标准

- 后台发一条消息，玩家 3 秒内能在聊天栏看到 `[管理员] …`；
- 同一条消息不会重复弹（`delivered` / `message_reads` 去重）；
- 当前状态：代码完成、编译通过、后端实测通过、**未实机**。

### 六、禁止事项

不改 `/api/messages/*` 的接口与数据表结构；不在未经同意时执行数据清理或新增玩家侧指令；
不在缺少实机结果时擅自改动消息渲染文案。

## 项目迁移路线图

已完成：√ 后台发消息玩家收不到（补上 `ChatService` 进服/断线接线）

当前：→ 等待用户换包实机验证

下一步：→ 按用户决定处理广播积压 / 崩溃上报 / 玩家侧聊天指令

未来：→ 按明确反馈继续最小化调整
