# 230 · 追加 · 《种子挖矿正式化第二阶段报告 —— UI、Seed 配置、维度状态与异步预测控制》

日期：2026-09-23
分支：`master`（Minecraft 26.1.2，业务唯一真源）
阶段：种子挖矿 **正式化第二阶段**（本阶段**不是** PoC；未重开 Round8 / Round9 / Pure Seed 唯一真值研究）。
上游依据：[229] 正式化第一阶段（正式架构、数据模型、`DiamondSeedPredictor` 迁移）、[228] 第七轮最终收口（FEATURES 调度因果定案）。
本轮产物：`com.yiyiaddon.seed` 下新增 `config` / `service` 两个子包（3 个类）+ 控制台第 8 个页签 `MiningSeedPage` + dev 侧服务层回归装置。
本轮**没有**接 AutoMiner、**没有**接 Baritone、**没有**自动寻路、**没有**自动挖矿、**没有**矿物渲染、**没有**假矿判定、**没有**扩其它矿物、**没有** Seed 真实性验证。

**验证方式**：全部实机验证 —— Minecraft 26.1.2 真实客户端进程（`gradlew runClientSeedPredictTest`，独立运行目录 `run-26.1.2-seed-predict-test/`）跑服务层回归装置，产出 `seedpoc-服务层回归.txt`（**全部判定：通过**）；另做正常 `runClient` 启动验证。
`compileJava` / `build` 本轮复跑均为 `EXIT=0`。

---

## 零、结论摘要（先给答案）

1. **界面已经接到正式预测器**：控制台新增「种子挖矿」页，点是 → `SeedMiningService` → 正式 `DiamondSeedPredictor` → `PredictionResult` → 界面读数，整条链在真实客户端进程里跑通（第 22、23 节）。
2. **回归数字与 229 / 228 完全一致，零退化**：Seed `20260922` `(0,0)` = **45**；Seed `2` `(-400,380)` = **23**（调度敏感 **1** / 未解析 **22** / 确定性 **0**），已知争议格 `(-6385,-59,6085)` 仍判 `SCHEDULE_SENSITIVE`；Seed `12345` 四目标 `31 / 29(7 敏感) / 24 / 26` 全部对上（第 23~25 节）。
3. **服务层与直连正式预测器逐 BlockPos 完全一致**：Seed `20260922` `(0,0)` 服务层 45 个 / 直连 45 个，双向差集均为 0（第 22 节）。
4. **渲染线程不跑世界生成**：点击「测试当前区块预测」后客户端线程只是提交任务，实测提交耗时 **44 ~ 718 微秒**；一次冷启动预测总耗时约 **2.5 秒**在后台单线程里跑完（第 21 节）。
5. **生命周期收口干净**：退出世界 → 状态回「等待进入世界」、结果清零、预测器释放、配置保留；主世界 → 下界 → 主世界 → 旧维度结果不复活（第 15、16、17、33 节）。
6. **UI 没有撒谎**：没有「种子已验证」、没有「假矿数量」、没有第二套矿物选择、没有精准采集 / 时运；`确定性` 恒为 0 如实显示（第 26~31 节）。
7. **AutoMiner 零行为变化**：本阶段对自动挖矿模块只改了一句页面说明文案，逻辑、配置、Baritone、精准采集 / 时运 / 食物 / 回家 / 背包全部未动（第 32 节）。

---

## 一、229 结论复核（报告项 1）

阶段口径第三节要求「229 已经证明的事实不允许回退」。本轮逐条复核，未发现任何回退：

| 229 已证明的事实 | 本轮状态 | 复核证据 |
| --- | --- | --- |
| 正式 Predictor 已完成迁移 | 未改动 `seed/prediction/**` 任何一行 | `git status` 中 `seed/prediction/**` 无修改记录 |
| Seed 20260922 正式 vs PoC `243/243`、10/10 目标一致 | 未回退 | 本轮复测 `(0,0)` = 45，与 229 固定集首个目标一致 |
| Seed 12345 正式 vs PoC `110/110`、4/4 一致 | 未回退 | 本轮复测 `31 / 29 / 24 / 26` 四项全对 |
| Seed 2 争议位置 `(-6385,-59,6085)` 分类 `SCHEDULE_SENSITIVE` | 未回退 | 本轮服务层复测仍是「调度敏感」 |
| 宿主 ChunkMap 查询增量 = 0 | 未回退 | 六个用例全部报「宿主 ChunkMap 查询增量 0（必须为 0）：通过」 |
| 正式包不依赖 `dev.seedpoc` | 未回退 | 新增的 `seed/config`、`seed/service` 只 import `com.yiyiaddon.config` / `core.event` / `platform.world` / `seed.*`，依赖方向恒为 dev → 正式 |
| `DETERMINISTIC` 本阶段不产出 | 未回退，且如实呈现 | 六用例「确定性 0（本阶段恒 0）：通过」；UI 不隐藏该行 |
| 输出主要是 `SCHEDULE_SENSITIVE` / `UNRESOLVED` | 未回退，且如实呈现 | UI 分区显示「调度敏感 / 未解析 / 确定性」三行，附口径说明 |

**未做的事**：本轮**没有**做任何「让结果更好看」的加工，特别**没有**把 `UNRESOLVED` 显示成「确定矿」。

---

## 二、本阶段新增 / 改动的 package tree（报告项 2、38）

```
src/main/java/com/yiyiaddon/
├── seed/                                          ← 229 建立；本轮只新增两个子包
│   ├── config/
│   │   └── SeedMiningConfig.java            113 行   ★本轮新增
│   ├── service/
│   │   ├── SeedMiningRuntimeState.java       56 行   ★本轮新增
│   │   └── SeedMiningService.java           573 行   ★本轮新增
│   ├── model/        （229，未改）
│   ├── observation/  （229，未改）
│   ├── prediction/   （229，未改 —— 本阶段一行未动）
│   └── worldgen/     （229，未改）
├── feature/mining/ui/
│   ├── MiningConsoleScreen.java             507 行   ✎修改（Tab 枚举 + 可见性 + 装配）
│   ├── AutoMinerPage.java                   227 行   ✎修改（仅一句分页说明文案）
│   └── console/
│       └── MiningSeedPage.java              185 行   ★本轮新增（界面页）
├── YiyiAddonClient.java                     107 行   ✎修改（挂载服务）
└── dev/seedpoc/                              （dev 侧，允许依赖正式包）
    ├── ServiceRegression.java               644 行   ★本轮新增（服务层回归装置）
    ├── SeedPocFlags.java                    395 行   ✎修改（新增 service 开关）
    └── SeedPocEntry.java                    123 行   ✎修改（分派到服务层回归）

界面**没有**另起一套 framework：种子页放在 `feature/mining/ui/console/`，
与控制台其余页面（`MiningOverviewPage` / `MiningPointPage` / … / `MiningBaritonePage`）同级同构。
```

本轮新增代码合计 **1571 行**（3 个正式类 + 1 个界面页 + 1 个 dev 装置）。

**分层（口径第二十节）**：

```
界面 MiningSeedPage
   ↓ 只读状态 / 提交动作
服务 SeedMiningService          ← 配置、维度、生命周期、后台任务、取消、结果、错误
   ↓
正式 DiamondSeedPredictor
   ↓
PredictionSession
```

界面里**不存在** `new DiamondSeedPredictor(...)`、`CompletableFuture`、`level.dimension()`、`config.save()`、世界卸载清理 —— 这些全部在服务层（见 [MiningSeedPage.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/console/MiningSeedPage.java#L22-L42) 的类注释明确声明此边界）。

---

## 三、SeedMiningConfig（报告项 3）

[SeedMiningConfig.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/config/SeedMiningConfig.java) —— **只装两项**（口径第十节）：

| 字段 | 类型 | 默认 | 含义 |
| --- | --- | --- | --- |
| `enabled` | `boolean` | `false` | 「启用种子挖矿」开关 |
| `seedText` | `String` | `""` | 服务器种子**原文**（保留原始形态，含非法输入） |

**明确不装**（口径第十、四十六、四十七节）：矿物白名单、精准采集、时运、食物、回家、Baritone、背包 —— 那些属于现有自动挖矿模块（`MiningSettings`），种子模块**不维护第二套**。

**两个公开能力**：

- `parseSeed(String)`：`trim()` → 空串返回 `null` → `Long.parseLong` 失败返回 `null`。只接受 Java long 十进制字面量，不做字符串 hash（口径第九节）。
- `seedStatusCn()`：返回**只有三种**取值 ——「未填写 / 格式无效 / 已填写」。**不产出「种子已验证」**（口径第三十一节，理由见第 28 节）。

`save(JsonObject)` / `load(JsonObject)`：键名 `enabled` / `seedText`（与 `MiningSettings` 同一英文本地风格）；`load` 缺项 / 类型不符一律回落默认值，不抛异常。

---

## 四、SeedMiningService（报告项 4、18）

[SeedMiningService.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningService.java) —— **常驻单例**（`instance()` / `init()`），由 [YiyiAddonClient.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/YiyiAddonClient.java) 在 `TacticalCoordinator.init()` 之后挂载一次。

**为什么常驻、不随模块开关**（口径第三十七、三十八节）：运行时缓存必须在「退出世界」这一刻被清掉，而那一刻模块可能是关着的。因此服务不依赖任何模块开关，只盯一件事 —— `Minecraft#level` 这个**对象**换没换。

**它持有的东西**：

| 类别 | 内容 |
| --- | --- |
| 配置 | `SeedMiningConfig config`、`configScope`、`parsedSeed` |
| 运行时 | `predictor`、`generation`、`predicting`、`lastResult`、`state`、`dimensionKey`、`dimensionId`、`worldReady`、`hostAvailable`、`requestChunkX/Z` |
| 后台 | 单线程守护 Executor（`yiyiaddon-seed-predict`） |

除 `levelRef`（仅主线程）外，全部运行时字段为 `volatile`；公开 API 全部约定在**客户端主线程**调用。

**公开 API（界面只允许碰这些）**：

```
enabled() / setEnabled(boolean)
seedText() / setSeedText(String) / seedStatusCn() / seedValue()
dimensionId() / dimensionDisplayCn() / dimensionSupportCn() / dimensionSupported()
state() / stateCn() / predicting() / canPredict()
lastResult() / predictorAllocated() / failureCn()
playerChunkCn() / predictedChunkCn()
predictCurrentChunk()          ← 本阶段唯一的预测入口
```

**它不碰的东西**（口径第二十六、三十、三十一、五十七节）：AutoMiner 目标、Baritone、世界渲染、假矿判定、其它矿物、Seed 真实性验证。

---

## 五、RuntimeState 状态机（报告项 5）

[SeedMiningRuntimeState.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningRuntimeState.java) —— 口径第二十三节要求的状态模型。**唯一产生者是 `SeedMiningService#refreshState()`**，界面只读它（口径第二十三节：不允许 UI 到处拼 `enabled && seed != null && …`）。

| 枚举值 | 中文文案 | 何时出现 |
| --- | --- | --- |
| `DISABLED` | 已关闭 | `enabled == false` |
| `WAITING_FOR_WORLD` | 等待进入世界 | 已启用但 `level == null` / 玩家未就绪 |
| `NO_WORLD_HOST` | 当前世界没有可用的世界生成环境 | 已进世界但 `getSingleplayerServer() == null`（多人服务器） |
| `WAITING_FOR_SEED` | 等待填写服务器种子 | 种子输入为空 |
| `INVALID_SEED` | 种子格式无效 | 文本非空但 `parseSeed` 返回 `null` |
| `UNSUPPORTED_DIMENSION` | 当前维度暂不支持 | 种子合法但当前维度不是主世界 |
| `READY` | 已就绪 | 条件齐备，可以点按钮 |
| `PREDICTING` | 正在预测当前区块… | 后台有预测在跑 |
| `SUCCESS` | 预测完成 | `lastResult.success() == true`（**0 个矿也算成功**） |
| `FAILED` | 预测失败 | `lastResult.success() == false` |

**判据顺序（即优先级）**：`关闭 → 没进世界 → 没有宿主 → 没填种子 → 种子不合法 → 维度不支持 → 预测中 → 有结果 → 就绪`。前面的条件一旦不满足就不再往后面看 —— 因此「关闭」永远压过一切，「维度不支持」永远压过「就绪」。

**`SUCCESS` 与 `FAILED` 的分界**由正式层 `PredictionResult#success()` 决定，服务层不自行推断（口径第四十一节：`ores.size() == 0` 是合法成功，不是失败）。

**新增了 `NO_WORLD_HOST`（口径第二十三节只列了 8 态，本实现为 9 态）** —— 如实反映「多人服务器客户端拿不到世界生成宿主」。理由：正式预测器需要一个 `ServerLevel` 当环境宿主（注册表 / 世界高度 / 结构管理器 / 调色板工厂），它只由集成服务端提供。**绝不**用别的世界的宿主顶替（顶替出来的数字不属于这个世界）。

---

## 六、UI 页面结构（报告项 6、37）

[MiningSeedPage.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/console/MiningSeedPage.java) —— 自动挖矿控制台内第 8 个页签，`build(CompactStack stack)` 装配：

```
种子挖矿（标题 + 一句功能说明）

── 配置 ──────────────
启用种子挖矿        [Toggle]  ↺
服务器种子          [输入框 220 宽 / 64 长]  ↺
种子状态            未填写 / 格式无效 / 已填写
（小字说明：只做格式校验，不表示种子属于当前服务器）

── 环境 ──────────────
当前维度            主世界 / 下界 / 末地 / 自定义维度：<id>
支持状态            支持：钻石预测 / 当前版本暂未支持下界矿物预测 / …
预测环境            可用 / 当前世界不可用 / 未进入世界 / 已关闭

── 预测状态 ──────────
状态                已关闭 / 等待进入世界 / … / 已就绪 / 正在预测当前区块… / 预测完成 / 预测失败
当前区块            12, -3
                    [测试当前区块预测]（条件不满足时禁用）
（小字说明：只做预测与展示，不加进自动挖矿目标、不寻路、不破坏方块、不调用秒破）

── 上一次预测结果 ────
预测区块            0, 0
候选钻石            45
调度敏感            0
未解析              45
确定性              0
预测耗时            2539 ms
缓存区块            529 个
失败原因            —（失败时为正式层的中文原因）

（四条口径小字）
未解析不代表没有矿，只表示当前算法尚未证明该坐标是否受世界生成调度影响。
调度敏感：原版世界生成顺序可能影响该位置最终是否为矿物。调度敏感不等于假矿。
确定性：本阶段算法尚不具备证明能力，因此恒为 0 —— 这是如实结果，不是故障。
本阶段只支持：主世界 + 钻石。下界 / 末地 / 自定义维度不会调用主世界预测器。
```

**实机状态描述（报告项 37）**：未进世界时页面显示「当前维度：未进入世界 / 支持状态：等待进入世界 / 预测环境：等待进入世界 / 状态：已关闭（关）或 等待进入世界（开）」，各结果行为「—」。进入主世界并填入 `20260922`、打开开关后，状态变为「已就绪」，按钮可点；点击后先变「正在预测当前区块…」，约 2.5 秒后变「预测完成」，结果区出现 45 / 0 / 45 / 0 / 2539 ms / 529 个。切到下界后立刻变「当前维度暂不支持」，结果区六行全部回「—」。

**页面不做的事**（口径第二十七、三十节、第四十六、四十七节）：不列几十个坐标、不展示 Observation 区、不做矿物选择 Toggle、不出现精准采集 / 时运 / 食物 / 回家 / 白名单 / 背包 / Baritone。

---

## 七、如何复用现有 UI framework（报告项 7）

种子页**没有**新建任何 framework，全部复用控制台既有构件：

| 用途 | 复用构件 |
| --- | --- |
| 页签骨架 | `MiningConsoleScreen.Tab` 枚举 + `buildInto(CompactStack)` 分派 |
| 滚动 / 分区 | `CompactStack`、`CompactElement`、`ConsoleMetrics.SECTION_HEIGHT/SIZE` |
| 分区标题 / 说明 / 只读行 | `ConsoleWidgets.Note` |
| 设置行（标签 + 控件 + 悬停说明 + ↺） | `ConsoleWidgets.ConsoleRow`、`ConsoleWidgets.Ctl`、`ConsoleWidgets.resetCtl` |
| 按钮条 | `ConsoleWidgets.ButtonStrip` + `ui.widget.Button`（`disabledWhen` 支持禁用） |
| 开关 | 现有 `SettingToggle`（44×24） |
| 输入框 | 现有 `SettingTextBox`（`width(220f)`） |

**明确没有使用**：`Button.builder`、原版 `EditBox` 默认皮肤、`OptionsScreen` 风格、Vanilla 灰色按钮（口径第六节）。玩家可见文本全部中文。

**一个关键实现决定**：本页**不用**控制台概览页那套「每秒整页重建」刷新，因为整页重建会调用 `SettingTextBox.clearFocus()` —— 本页有输入框，重建等于把玩家正在输入的焦点与光标抹掉（表现为「打字打到一半就断」）。因此本页每个动态值都用 `Supplier` **每帧现读**（Skija 每帧画面板，读数自然跟着变），只在「恢复默认」这种必须重置输入框的动作上才调 `owner.reload()`。

同理，「缓存区块」读数取自 `lastResult.stats().heldChunks()`（结果快照），**不在渲染线程上去锁预测器**。

---

## 八、personalMode 隐藏实现（报告项 8）

口径第七、四十五节要求：种子页签 `visible(boolean personalMode)` 必须返回 `!personalMode`。

实现（[MiningConsoleScreen.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/MiningConsoleScreen.java#L131-L139)）：

```java
private boolean visible(boolean personalMode) {
    return switch (this) {
        case PERSONAL -> personalMode;
        case TARGET, TELEPORT -> !personalMode;
        ...
        case SEED -> !personalMode;
    };
}
```

**安全跳转**：控制台本来就有「不可见页签落回兜底页」的机制（`reload()` 中对当前页做可见性校验，不可见则落回 `personalMode ? PERSONAL : OVERVIEW`）。因此「玩家停在第 8 页 → 系统切到 personalMode」会自动落回 `PERSONAL` 页，**不会**留下「不可见但仍 active」的种子页。

**实机验证**：服务层回归装置第二项「界面装配烟测」在真实客户端进程里分别以 `personalMode=false / true` 装配整个控制台窗口，两种模式均装配成功、不抛异常（`个人模式开：控制台整窗装配成功（种子页签走隐藏路径，不抛异常）`）。

---

## 九、Seed 输入解析（报告项 9、10）

解析实现只有一处：`SeedMiningConfig.parseSeed(String)`（`trim` → 空串 `null` → `Long.parseLong`）。

**测试矩阵（口径第四十九节，10 项，实机跑过）**：

| 输入 | 解析结果 | 输入状态 | 运行时状态 |
| --- | --- | --- | --- |
| （空串） | `null` | 未填写 | 等待填写服务器种子 |
| `0` | `0` | 已填写 | 已就绪 |
| `12345` | `12345` | 已填写 | 已就绪 |
| `-7777` | `-7777` | 已填写 | 已就绪 |
| `20260922` | `20260922` | 已填写 | 已就绪 |
| `9223372036854775807`（Long.MAX_VALUE） | 同值 | 已填写 | 已就绪 |
| `-9223372036854775808`（Long.MIN_VALUE） | 同值 | 已填写 | 已就绪 |
| `abc` | `null` | 格式无效 | 种子格式无效 |
| `12.3` | `null` | 格式无效 | 种子格式无效 |
| `--` | `null` | 格式无效 | 种子格式无效 |

**非法种子下是否允许预测：禁止**（实机判定「正确」）。

**只做手动输入**（口径第九节）：不调 `/seed`、不发聊天命令、不猜种子、不从不可靠数据反推。**未实现**字符串世界种子 hash。

**Seed 修改行为（口径第三十三节）**，`setSeedText` 逐条对应：保存配置 → 取消在途任务（`cancelPending`，递增 `generation` 并清 `lastResult`）→ 对**旧种子**的会话投递 `invalidate(oldSeed, OVERWORLD)`（走后台线程，因为释放缓存要拿预测器锁，绝不允许在渲染线程等它）→ 重新落到 `READY` / `INVALID_SEED`。

---

## 十、配置持久化（报告项 34→见后；本节对应口径第十一、三十八节）

**复用现有配置系统**：`ModuleStateConfig`（`module-state.json`），**没有**新建第二套 JSON 文件系统（口径第十一节）。

- 记录键：`RECORD_ID = "seed_mining"` —— 刻意**不**用真实模块 id，避免混进 `mining` 记录后被模块级「保存 / 恢复默认」连带改写。
- 作用域：`WorldIdentity.fileSafeServer()` —— 种子是典型的「世界相关数据」，与点位 / 设置同一口径按服务器隔离（`开发习惯` 第 236 条）。
- 写入口径与 `ModuleManager#saveSettings` 一致：先更新全局模板（它同时是新服务器 / 新存档的初始值），再往当前作用域的桶写一份；未进世界时只写全局模板（没有服务器身份时写分区等于污染别人的配置）。

**关闭客户端重新打开，Seed 输入与启用状态可恢复**：`install()` 时 `loadConfig()`；换世界 / 换服时按新作用域 `loadConfig()`。实机验证：退出世界后断线前配置为 `启用=true，种子原文=12345`，退出后仍保留。

**`PredictionSession` 不序列化**（口径第十一、三十八节）：`PredictionResult`、会话、离线区块缓存全部是运行时状态，**绝不**写进配置文件。

---

## 十一、启用开关语义（报告项 11）

`setEnabled(boolean)` 的语义（口径第十二、三十四节）：

- **OFF → ON**：写配置 + 落盘 → `refreshState()` 重新评估世界 / 种子 / 维度 → 进入相应状态。**不自动开始预测**（口径第三十五节：第一版一律由按钮触发）。
- **ON → OFF**：写配置 + 落盘 → `cancelPending()`（取消在途任务 + 清结果）→ `releasePredictor()`（释放预测器与会话缓存）→ `refreshState()` → `DISABLED`。

**OFF 的保证**：完全不运行预测器、不创建后台任务、不创建新的 `PredictionSession`、不影响现有 AutoMiner、不影响 Baritone（本阶段本来就没接 Baritone）。

**ON 的条件依赖**：只有「种子合法 + 当前维度受支持 + 世界宿主可用」三者齐备才允许预测。条件不足时**保持启用状态**，状态如实显示「等待填写服务器种子 / 种子格式无效 / 当前维度暂不支持 / 当前世界没有可用的世界生成环境」（口径第十二节）。本阶段没接 AutoMiner，因此不存在「自动回退」问题。

---

## 十二、当前维度自动识别（报告项 12）

**没有任何「手动选择维度」的入口**（口径第十三节）。维度来自客户端当前 `Level#dimension()`：

```java
ResourceKey<Level> key = level.dimension();
dimensionKey = key;
dimensionId = key.identifier().toString();   // minecraft:overworld 形式
```

`onTick()` 里用「`Minecraft#level` 对象换没换」作为第一判据，用「已知 key 与新 key 不等」作为第二判据 —— 两者相加覆盖「进世界 / 退世界 / 换维度 / 换服」（口径第三十九节）。

**绝不每刻重建预测器**，也绝不每刻去算服务器身份字符串；只有维度**实际改变**才触发生命周期动作。

---

## 十三、主世界 / 下界 / 末地 / 自定义维度显示（报告项 13、14）

显示名走现有 `WorldIdentity.dimensionDisplayName(String)`（原版三维度**精确匹配** → 语言文件中文名 → 兜底「自定义维度」），服务层再补「自定义维度」的 id 后缀：

| 维度 id | 界面显示 | 支持状态文案 |
| --- | --- | --- |
| `minecraft:overworld` | 主世界 | 支持：钻石预测 |
| `minecraft:the_nether` | 下界 | 当前版本暂未支持下界矿物预测 |
| `minecraft:the_end` | 末地 | 当前维度暂无支持矿物 |
| 其它 | 自定义维度：`<id>` | 当前维度的世界生成规则暂未支持 |
| 未进世界 | 未进入世界 | 等待进入世界 |

**当前支持范围**（口径第十五节）：本阶段正式预测器只支持「Minecraft 26.1.2 + 主世界 + 钻石」。支持判定用 `Level.OVERWORLD` 与会话键精确比较，**不**拿主世界预测器去硬跑下界 / 末地 / 自定义维度。

**绝不**把 Java 对象或 `ResourceKey[...]` 直接甩到界面上（口径第十四节）。

---

## 十四、维度切换生命周期（报告项 15）

口径第十六节的五条要求，实现如下：

1. **识别 dimension change**：`onTick` 用 `known != null && !known.equals(key)` 精确识别。
2. **取消旧维度在跑的 UI 预测任务**：`onDimensionChanged` → `cancelPending()`（递增 `generation`、`predicting = false`、清 `lastResult` 与目标区块）。
3. **`PredictionSession` 本身以（版本 + Seed + Dimension）隔离**：沿用 229 的会话键，未改。
4. **UI 不得继续显示上一维度的结果**：`lastResult` 已置空，结果区六行回「—」。
5. **回到原维度**：按正式 Predictor 当前生命周期**重新获得**（本实现取「重新获得」而非「复用合法会话」，理由见第 18 节）。

**实机验证（实机切换：主世界 → 下界 → 主世界）**：

```
切换前：状态「预测完成」，上一次结果 26 个，当前维度 主世界
下界：维度显示「下界」/ 支持状态「当前版本暂未支持下界矿物预测」/ 状态「当前维度暂不支持」（通过）
  旧维度结果是否已清除：已清除（通过）
  下界是否允许预测：禁止（正确）（通过）
返回主世界：维度显示「主世界」/ 支持状态「支持：钻石预测」/ 状态「已就绪」（通过）
  返回后结果仍为空（旧维度的结果不会复活）：（通过）
```

---

## 十五、服务器退出清理（报告项 16、33）

口径第十七、五十五节。`onLevelChanged(client, null)` 是唯一收口点：

1. `cancelPending()` —— 取消在途任务、递增 `generation`、清空结果；
2. `releasePredictor()` —— 置 `predictor = null` 并把 `close()` **投递到后台线程**（拿锁释放会话 + 离线区块缓存，绝不在渲染线程上等）；
3. 清 `worldReady` / `hostAvailable` / `dimensionKey` / `dimensionId` / `configScope`；
4. `refreshState()` → `WAITING_FOR_WORLD`；
5. **配置中的 Seed 与 enabled 保留**（口径第十七节要求保留）。

**实机验证（退出世界 / 断开连接）**：

```
断线前：状态「预测完成」，是否持有预测器：true，上一次结果：有
已回到标题界面：状态「等待进入世界」（通过）
  运行时结果已清：（通过）；预测器已释放：（通过）
  配置保留：启用=true，种子原文=12345
```

「断线前持有预测器 = true」这一点是**专门设计出来的可观测证据**：装置在回主世界后**补跑一次预测**（`REWARM` 阶段），使「持有 → 释放」的转变在日志里可核对，而不是只看到一个恒为 false 的读数。

**Session 缓存退出世界后是否释放（报告项 33）**：是。`releasePredictor()` 只要 `predictor != null` 就会 `submitQuietly(current::close)`，`DiamondSeedPredictor#close()` 释放全部会话（含 `OfflineChunkCache`）。实机判定「预测器已释放：通过」。

---

## 十六、服务器切换清理（报告项 17）

口径第十八节：A 服 → 退出 → B 服，运行时 Session 必须在**退出 A 时**清空，A 的预测任务不得活到 B。

本实现天然满足，因为「退出 A」与「退出世界」是**同一个事件**（`level` 换对象 / 变 `null`），走同一套 `onLevelChanged` 清理；进入 B 时再按新的 `WorldIdentity.fileSafeServer()` 切换配置作用域并 `loadConfig()`。不存在「跨服务器复用运行时对象」的路径。

---

## 十七、Predictor 生命周期（报告项 18）

| 时机 | 动作 |
| --- | --- |
| 首次点「测试当前区块预测」 | `predictor == null` 时 `new DiamondSeedPredictor(host)`（`host = getSingleplayerServer().overworld()`） |
| 更换种子 | 对**旧种子**投递 `invalidate(oldSeed, OVERWORLD)`；预测器本身保留 |
| 换维度 | `cancelPending()`，**并释放**预测器（见下） |
| 关闭开关 | `cancelPending()` + `releasePredictor()` |
| 退出世界 / 换服 | `cancelPending()` + `releasePredictor()` |

**为什么换维度也释放预测器**（口径第十六节允许「复用合法会话」或「按当前生命周期重新获得」，本实现取后者）：会话键**不含服务器身份**，而宿主 `ServerLevel` 属于某一次连接 —— 同一台服务器断开重连、或同一个存档退出再进，`level` 都是新对象、旧宿主已经作废。与其在这里判「是不是同一个世界」再去赌一个可能作废的宿主，不如一律释放、下次预测时按当前世界重新构造。**代价是回到主世界后的一次冷启动（约 2 秒）** —— 已如实登记在第 39 节遗留问题。

---

## 十八、异步 Executor 设计（报告项 19）

口径第二十一、二十二节：必须后台执行，单线程优先。

```java
private final ExecutorService worker = Executors.newSingleThreadExecutor(task -> {
    Thread thread = new Thread(task, WORKER_NAME);   // "yiyiaddon-seed-predict"
    thread.setDaemon(true);
    return thread;
});
```

- **单线程**：worldgen 重，避免多个预测同时抢 CPU / 内存；同一时刻只会有一个预测在跑（单线程 Executor + `predicting` 双重保证）。
- **守护线程**：随客户端进程结束，不阻止 JVM 退出。
- **跨世界不重建**：整个客户端进程一个 Executor，不随世界 / 界面开关重建。
- **结果回投**：`Minecraft.getInstance().execute(...)` 投递回客户端主线程（口径第四十二节：绝不让异常炸到 client thread）。

**本阶段不并发跑 8 个 Chunk**（口径第二十二节）；也**不自动预测周围 16 / 32 / 128 Chunk**（口径第三十五节）—— 唯一入口是按钮 → 当前 Chunk。

---

## 十九、任务取消 / request id（报告项 20）

口径第三十六节：旧任务完成时不得覆盖新状态。

**机制**：`volatile long generation`。`predictCurrentChunk()` 提交时 `++generation` 并把 `id` 捕获进任务；`cancelPending()` 也 `++generation`。任务在**两处**校验：

```java
private void runPredict(long id, ...) {
    if (id != generation) { /* 排队期间已被取消：连算都不必算 */ return; }
    ...
    deliver(id, result, seed, chunk);
}

private void deliver(long id, PredictionResult result, ...) {
    Minecraft.getInstance().execute(() -> {
        if (id != generation) { /* 已完成但已被取消：结果丢弃 */ return; }
        predicting = false;
        lastResult = result;
        refreshState();
    });
}
```

内层校验是关键：即使任务已经在算，取消之后它的结果也会在**回投到主线程的那一刻**被丢弃。

**覆盖的取消场景**（口径第三十六节全部）：预测中修改种子、预测中切维度、预测中退出服务器、预测中关闭功能 —— 四者全部走 `cancelPending()`。

---

## 二十、为什么不会卡 render thread（报告项 21）

1. `predictCurrentChunk()` 只在主线程做四件事：校验、取参数、`++generation` 置状态、`worker.execute(...)` 提交。**没有**任何 worldgen 调用。
2. 真正的 `target.predict(seed, chunk)` 只出现在 `runPredict`，而 `runPredict` 只在后台线程执行。
3. 结果回投走 `Minecraft#execute`（下一帧执行），主线程不做计算。
4. `releasePredictor()` 的 `close()` 也投递到后台（释放会话要拿预测器锁）。
5. 界面「缓存区块」读数取自结果快照，不在渲染线程上碰预测器。

**实测**：六个用例的「客户端线程提交耗时」为 **718 / 59 / 44 / 151 / 278 / 138 微秒**，而对应的完整预测耗时是 **2539 / 1820 / 1756 / 274 / 1040 / 1121 毫秒**。提交耗时与预测耗时相差三个数量级，直接证明世界生成不在渲染线程。

---

## 二十一、测试当前区块预测链路（报告项 22、50）

口径第二十五、五十节要求的完整链：

```
界面按钮 MiningSeedPage.ButtonStrip
   → SeedMiningService.predictCurrentChunk()             （主线程：取 player.blockPosition() >> 4）
      → worker.execute(runPredict)
         → DiamondSeedPredictor.predict(seed, ChunkPos)   （后台单线程）
            → PredictionSession.predict(...)              （会话级同步）
         → deliver → Minecraft#execute                   （回投主线程）
      → lastResult / predicting / refreshState
   → 界面每帧现读，结果区更新
```

**按钮不影响 AutoMiner**（口径第二十六节）：结果只存到 `SeedMiningService.lastResult`；不加进 AutoMiner 目标、不调 Baritone、不自动走路、不自动挖、不调用秒破。页面明确写着这句说明。

**服务层 vs 直连正式预测器（逐 BlockPos）**：

```
直连正式 Predictor（独立实例、独立后台线程）预测 种子 20260922 区块 (0,0)……
服务层 45 个 / 直连 45 个 → 服务有直连没有 0 / 直连有服务没有 0 → 逐 BlockPos 完全一致
```

即：**Service 包装没有改变 Predictor 的任何结果**。

---

## 二十二、Seed 20260922 `(0,0)` UI / Service 结果（报告项 23）

```
状态：预测完成；目标区块 (0,0)（期望 (0,0)）（通过）
候选钻石 45（期望 45）（通过）；调度敏感 0（期望 0）（通过）
未解析 45；确定性 0（本阶段恒 0）（通过）；未解析 = 候选 - 敏感：（通过）
预测耗时 2539 ms；会话持有离线区块 529 个；宿主 ChunkMap 查询增量 0（必须为 0）（通过）
提交后状态「正在预测当前区块…」，客户端线程提交耗时 718 微秒
```

与 229「Seed 20260922 `(0,0)` 正式 Predictor = 45」**一致**（口径第五十节）。

---

## 二十三、Seed 2 冲突回归（报告项 24）

**本阶段不要求玩家真的走到 -6400**：装置直接通过 Service / 正式预测器测 `(-400,380)`。

```
状态：预测完成；目标区块 (-400,380)（期望 (-400,380)）（通过）
候选钻石 23（期望 23）（通过）；调度敏感 1（期望 1）（通过）
未解析 22；确定性 0（本阶段恒 0）（通过）；未解析 = 候选 - 敏感：（通过）
预测耗时 1820 ms；会话持有离线区块 529 个；宿主 ChunkMap 查询增量 0（必须为 0）（通过）
已知争议位置 (-6385,-59,6085)：调度敏感；期望「调度敏感」（通过）
```

与口径第五十一节要求的「候选 23 / 调度敏感 1 / 未解析 22 / 确定性 0 / 争议格仍 `SCHEDULE_SENSITIVE`」逐项一致。

---

## 二十四、Seed 12345 回归（报告项 25）

```
(0,0)     ：候选 31（期望 31）（通过）；调度敏感 0（期望 0）；未解析 31；确定性 0；耗时 1756 ms；缓存 529
(-1,-1)   ：候选 29（期望 29）（通过）；调度敏感 7（期望 7）；未解析 22；确定性 0；耗时  274 ms；缓存 574
(-25,17)  ：候选 24（期望 24）（通过）；调度敏感 0（期望 0）；未解析 24；确定性 0；耗时 1040 ms；缓存 1103
(120,-130)：候选 26（期望 26）（通过）；调度敏感 0（期望 0）；未解析 26；确定性 0；耗时 1121 ms；缓存 1632
```

四个目标全部对上，**没有因为 UI / Service 包装而改变 Predictor 结果**（口径第五十二节）。其中 `(-1,-1)` 的 7 个调度敏感，正是 228 里「该目标真值本来就不重复」的机制性证据。

---

## 二十五、PredictionCertainty 如何展示（报告项 26）

口径第二十八、二十九节。界面**不做分类枚举直译外的任何加工**，三行并列显示，行标签就是中文分类名：

| 枚举 | 界面行标签 |
| --- | --- |
| `DETERMINISTIC` | 确定性 |
| `SCHEDULE_SENSITIVE` | 调度敏感 |
| `UNRESOLVED` | 未解析 |

数值直接取自 `PredictionResult#deterministicCount()` / `#scheduleSensitiveCount()` / `#unresolvedCount()`（即 `Stats` 里的同名字段），未做任何重算。

**页面说明句**（原样写入界面）：

- 「未解析不代表没有矿，只表示当前算法尚未证明该坐标是否受世界生成调度影响。」
- 「调度敏感：原版世界生成顺序可能影响该位置最终是否为矿物。调度敏感不等于假矿（真实世界里它同样可能出现）。」
- 「确定性：本阶段算法尚不具备证明能力，因此恒为 0 —— 这是如实结果，不是故障。」

**绝不**使用「低可信 / 假矿 / 错误矿」描述 `UNRESOLVED`；**绝不**向玩家展示 `FEATURES` / `WorldGenRegion` / `OreFeature` / 「9 viewer」等开发术语。

---

## 二十六、为什么 UNRESOLVED 不是错误（报告项 27）

口径第二十八、四十一节。

`UNRESOLVED` 是**算法的默认结论**，不是失败：`ScheduleSensitivityAnalyzer` 只对「有正面证据」的坐标判 `SCHEDULE_SENSITIVE`；其余坐标一律回落 `UNRESOLVED` —— 含义是「当前算法**尚未证明**该坐标是否受世界生成调度影响」，**不是**「这里没有矿」，也**不是**「这个坐标是错的」。

所以界面把「候选钻石」与「未解析」并列展示：45 个候选里有 45 个未解析（Seed 20260922 `(0,0)` 就是这种形态），换句话说，本次预测**算出了 45 个候选位置**，只是它们的确定性还没被证明。这正是 229 已确立的当前能力边界，本阶段如实呈现，不藏、不美化。

同理，`SUCCESS`（预测完成）与矿的数量无关：算出来 0 个矿也是成功。

---

## 二十七、为什么没有显示「Seed 已验证」（报告项 28）

口径第三十一节。

`Long.parseLong` 成功**只说明格式是数字**，**不说明**这个种子属于当前服务器。本阶段**没有**实现任何 Seed 真实性验证算法（口径第五十七节明令禁止），因此界面只能回答三个问题 ——「未填写 / 格式无效 / 已填写」—— 不能回答「对不对得上服务器」。

界面为此还专门加了一句说明：「只做格式校验：填了合法数字也只表示「格式对」，不表示这个种子属于当前服务器（本阶段没有种子真实性验证）」。

**没有任何路径**会因为 `parseSeed` 成功就显示「验证成功 / 已验证」。

---

## 二十八、为什么没有假矿数量（报告项 29）

口径第三十节。

`OreObservationState` 里确实已经有 `SUSPICIOUS`，但本阶段**没有**任何观察服务在产出它 —— 没有观察层、没有 CONFIRMED / MISSING 实际观察、没有假矿检测（口径第五十七节明令禁止）。

做一个「假矿数量」读数就等于假装这个能力已经实现。因此种子页**完全不展示** Observation 区域，等观察层正式接入后再加。

---

## 二十九、为什么没有重复矿物选择（报告项 30）

口径第四十六节。

界面**没有** `[√] 钻石 / [ ] 铁 / [ ] 金` 这类 Toggle。理由：矿物选择最终应该复用现有 AutoMiner 设置，种子模块**不维护第二套**。当前正式 Predictor 虽然只支持钻石，页面只用一行「支持状态：支持：钻石预测」把这件事说清楚，而不是给玩家一个只能选一项的假开关。

---

## 三十、为什么没有精准采集 / 时运（报告项 31）

口径第四十七节。

种子页**不出现**：精准采集、时运、食物、回家、白名单、背包、Baritone。这些继续属于现有自动挖矿模块。种子页只回答四个问题 ——「Seed 是什么 / 当前维度是哪个 / 预测能不能跑 / 预测结果怎么样」。

---

## 三十一、正常 AutoMiner 是否零行为变化（报告项 32、53）

**是，零逻辑变化。**

本阶段对自动挖矿的改动只有一处，且是**纯文案**：[AutoMinerPage.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/AutoMinerPage.java) 的 `CONSOLE_HINT` 里，分页清单加上「/ 种子挖矿」，并把互斥说明改为「自用模式与「目标选择」「传送指令」「种子挖矿」互斥显示」。

**未改动**：`MiningSettings`、`MiningModule` / AutoMiner 逻辑、Baritone 接入、精准采集、时运、食物、回家、白名单、背包、点位系统、阈值配置、传送指令页、个人模式页、Baritone 页。

**实机验证方式**：种子挖矿保持关闭，正常 `runClient` 启动，控制台其余七页装配与行为正常（服务层回归装置第二项在真实进程里以两种 `personalMode` 装配整窗均成功）。

---

## 三十二、compileJava / build / runClient（报告项 34、35、36）

```
.\gradlew.bat compileJava --console=plain -q     → EXIT=0
.\gradlew.bat build        --console=plain -q     → EXIT=0
.\gradlew.bat runClient                            → 启动正常
```

`runClient` 日志确认：

```
yiyiaddon initialised
种子挖矿：运行时服务已挂载（后台单线程预测；配置项 启用/种子）
Sound engine started
```

日志中**没有** yiyiaddon 相关的 `ERROR` / `Exception`。日志口径（口径第四十三节）：只记关键状态 —— 服务初始化、种子修改、预测开始、预测成功 / 失败、会话清理；详细 worldgen 日志默认不开启。

服务层实机回归装置（`gradlew runClientSeedPredictTest`，独立运行目录 `run-26.1.2-seed-predict-test/`）产出 `seedpoc-服务层回归.txt`，**九项判定全部通过**：

```
【判定】界面装配烟测：通过
【判定】种子输入解析矩阵：（通过）；非法种子禁止启动预测：（通过）
【判定】用例 种子 20260922 区块 (0,0)：全部 （通过）
【判定】用例 种子 2 区块 (-400,380)：全部 （通过） / 争议格调度敏感 （通过）
【判定】用例 种子 12345 区块 (0,0) / (-1,-1) / (-25,17) / (120,-130)：全部 （通过）
【判定】服务层结果 vs 直连正式 Predictor 逐 BlockPos 一致：（通过）
【判定】下界状态与旧结果清理：（通过）；返回主世界回到就绪：（通过）
【判定】退出世界清理：状态回「等待进入世界」（通过） / 结果已清 （通过） / 预测器已释放 （通过）
全部判定：通过
```

---

## 三十三、本阶段所有改动文件（报告项 38）

**新增（5 个）**

| 文件 | 行数 | 说明 |
| --- | --- | --- |
| `seed/config/SeedMiningConfig.java` | 113 | 可持久化配置（enabled / seedText + 解析 + 状态文案） |
| `seed/service/SeedMiningRuntimeState.java` | 56 | 运行时状态机（10 态 + 中文文案） |
| `seed/service/SeedMiningService.java` | 573 | 运行时服务（生命周期 / 维度 / 后台 Executor / 任务代号 / 持久化） |
| `feature/mining/ui/console/MiningSeedPage.java` | 185 | 控制台「种子挖矿」页 |
| `dev/seedpoc/ServiceRegression.java` | 644 | dev 侧服务层回归装置（**不属于正式产物**） |

**修改（5 个）**

| 文件 | 改动 |
| --- | --- |
| `feature/mining/ui/MiningConsoleScreen.java` | `Tab` 新增 `SEED("种子挖矿")`；`visible()` 新增 `case SEED -> !personalMode`；`buildInto` 新增 `case SEED`；新增 import；类注释补第 8 页签说明 |
| `feature/mining/ui/AutoMinerPage.java` | 仅 `CONSOLE_HINT` 文案：分页清单加「/ 种子挖矿」、互斥说明补该项 |
| `YiyiAddonClient.java` | `TacticalCoordinator.init()` 之后新增 `SeedMiningService.init();`（含常驻理由与顺序注释） |
| `dev/seedpoc/SeedPocFlags.java` | 新增 `serviceRegression()`（`-Dyiyiaddon.seedpoc.service=1`） |
| `dev/seedpoc/SeedPocEntry.java` | `onTick` 开头按 `serviceRegression()` 分派到 `ServiceRegression` |

**正式产物对 dev 包零引用**（口径第五十六节：`dev.seedpoc` 保留作回归 Oracle，不删 223~228 相关实验代码）。

---

## 三十四、遗留问题（报告项 39）

1. **多人服务器暂不可预测**：正式预测器需要 `ServerLevel` 当环境宿主（注册表 / 世界高度 / 结构管理器 / 调色板工厂），客户端在多人服务器上拿不到 ⇒ `NO_WORLD_HOST`（「当前世界没有可用的世界生成环境」）。**尚未**设计「从服务端取宿主」的方案；也**不会**用本地世界宿主顶替（会导致数字不属于该服务器）。这是当前最硬的边界。
2. **换维度即释放预测器 ⇒ 回主世界一次冷启动（约 2 秒）**：这是为了不赌「宿主是否仍有效」而取的安全策略。若要优化，需要引入「宿主身份」判据（连接身份 + 存档身份），本阶段刻意不做。
3. **`确定性` 恒为 0**：本阶段算法不具备证明能力（229 已定案），界面如实显示，属已知能力边界而非缺陷。
4. **只支持主世界 + 钻石**：下界 / 末地 / 自定义维度、其它矿物（含 Ancient Debris）本阶段一律不支持（口径第五十七节）。
5. **`NO_WORLD_HOST` 是本实现新增的、口径第二十三节 8 态之外的第 9 态**：需要用户确认这一命名与语义是否接受（若不接受，等价方案是合并进 `UNSUPPORTED_DIMENSION`，但那会让「维度不支持」与「世界不支持」在文案上混淆）。
6. **世界退出清理的「已释放」是异步完成的**：`releasePredictor()` 把 `close()` 投递到后台线程，因此「预测器引用已置空」是同步的，而「会话缓存真正回收」是下一拍后台完成的。实机判定用的是引用读数（同步可见），缓存回收不阻塞任何 UI 路径。
7. **内存量化未做**：本阶段只在界面显示「缓存区块 N 个」（如 529 / 1632），未做 JVM 堆量化对比。
8. **未做真实服务器（非集成服）实机验证**：全部实机验证在集成服务端环境完成。

---

## 三十五、下一阶段建议（报告项 40）

按用户口径第六十一节，本阶段结束即停止，等确认后再进入：

**《种子挖矿正式化第三阶段 —— 实际 Chunk 观察 + 预测结果渲染》**

第三阶段**仍然先不让 AutoMiner 自动挖**，先做到三件事全部正确：

1. **「我预测什么」** —— 预测结果稳定、可按区块检索（本阶段已具备单区块预测能力）。
2. **「服务器实际给我什么」** —— 接入观察层：客户端实际收到的区块数据 → `OreObservationState`（`CONFIRMED` / `MISSING` / `SUSPICIOUS` / `UNOBSERVED`）。这一层做完，「未解析 / 调度敏感」才第一次拥有来自真实世界的对证。
3. **「玩家能看到什么」** —— 预测结果渲染（此时才允许出现渲染层）。

三件事都正确之后，**最后**才接自动挖矿。

**本阶段明确不进入**（口径第五十七、六十一节）：观察层、假矿识别、矿物渲染、AutoMiner 接入、Baritone、其它矿物、Nether Predictor、Ancient Debris、26.2 / 26.3 Seed。

---

## 三十六、验收条件逐条对照（口径第六十节 25 条）

| # | 验收条件 | 状态 | 证据 |
| --- | --- | --- | --- |
| 1 | 种子挖矿独立页面存在 | ✅ | `Tab.SEED` + `MiningSeedPage` |
| 2 | 页面风格与现有项目一致 | ✅ | 全用 `ConsoleWidgets` / `SettingToggle` / `SettingTextBox` / `Button` |
| 3 | personalMode 下入口隐藏 | ✅ | `case SEED -> !personalMode`；烟测两种模式装配成功 |
| 4 | Seed 可输入并持久化 | ✅ | `SettingTextBox` → `setSeedText` → `ModuleStateConfig`；退出世界后配置保留 |
| 5 | 仅 Long 合法值可用于预测 | ✅ | 10 项解析矩阵；非法种子禁止预测 |
| 6 | 没有伪造「Seed 已验证」 | ✅ | 状态只有「未填写 / 格式无效 / 已填写」+ 界面反证说明 |
| 7 | 维度自动识别 | ✅ | `Level#dimension()`，无手动入口 |
| 8 | 主世界支持钻石 | ✅ | 「支持：钻石预测」 |
| 9 | 下界 / 末地不会错误调用主世界 Predictor | ✅ | `dimensionSupported()` 精确比较；下界「禁止预测」实测通过 |
| 10 | 预测后台执行 | ✅ | 单线程守护 Executor |
| 11 | UI 主线程不卡顿 | ✅ | 提交耗时 44~718 微秒 vs 预测耗时 274~2539 ms |
| 12 | 任务可取消 | ✅ | `cancelPending()` + `generation` 双处校验 |
| 13 | 修改 Seed 后旧任务不能回写 | ✅ | `setSeedText` → `cancelPending()`；`deliver` 内层校验 |
| 14 | 切维度后旧结果不能冒充新维度 | ✅ | 下界 → 结果已清；返回主世界结果不复活 |
| 15 | 退出服务器释放运行时缓存 | ✅ | 状态回 WAITING_FOR_WORLD / 结果已清 / 预测器已释放 |
| 16 | 20260922 `(0,0)` 仍为 45 | ✅ | 45（通过） |
| 17 | Seed 2 冲突结果仍为 23 / 1 / 22 / 0 | ✅ | 逐项（通过），争议格仍 `SCHEDULE_SENSITIVE` |
| 18 | Seed 12345 回归不退化 | ✅ | 31 / 29(7) / 24 / 26 全对 |
| 19 | AutoMiner 正式行为零变化 | ✅ | 仅改一句页面说明文案 |
| 20 | 没有接 Baritone | ✅ | 全无引用 |
| 21 | 没有假矿检测 | ✅ | 无观察层、无 SUSPICIOUS 判定 |
| 22 | 没有其它矿物 | ✅ | 仅钻石 |
| 23 | compileJava 成功 | ✅ | EXIT=0 |
| 24 | build 成功 | ✅ | EXIT=0 |
| 25 | 正常 runClient 成功 | ✅ | `yiyiaddon initialised` + `Sound engine started`，无 ERROR |

**结论：第二阶段验收条件 25 条全部满足。**

---

## 三十七、停止声明

按口径第六十一节：**本阶段到此停止**。

不自行进入观察层、假矿识别、矿物渲染、AutoMiner 接入。等待用户确认后再进入《种子挖矿正式化第三阶段》。
