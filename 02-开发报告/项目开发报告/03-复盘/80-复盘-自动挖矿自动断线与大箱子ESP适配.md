# 80 - 复盘：自动挖矿新增「自动断线」，与大箱子 ESP 适配 / 标签居中

- 日期：2026-09-17
- 触发：用户「我要添加一个功能 自动挖矿 我的服务器会死亡掉落 所以你帮我加个开关 血量默认掉到2格血自动点开连接 调用我模组里面的自动断线 配置就叫自动断线」
  → 实测反馈「自动挖矿 没有渲染大箱子 没适配 适配了 要剧中字体」
  → 口径补充「参考星露谷农场的 大箱子代码 你可以」
- 涉及文件：
  - `feature/mining/config/MiningSettings.java`（新增 2 项设置 + 读写）
  - `feature/mining/AutoMinerModule.java`（`onTick` 前置判定 + 断线节 + 防重锁复位）
  - `feature/mining/ui/console/MiningThresholdPage.java`（新增一行 + `intBox` 带格式重载）
  - `feature/mining/ui/AutoMinerPage.java`（使用说明「注意事项」补一行）
  - `feature/mining/render/MiningPointRenderer.java`（大箱子合并包围盒 + 标签居中）
- 验证：`.\gradlew build --console=plain -q` → **EXIT=0**；`runClient` **客户端启动验证**（`yiyiaddon initialised`，无新增崩溃报告）；**实机功能未确认**

---

## 1. 需求口径与旧项目对应物

| 项 | 结论 |
| --- | --- |
| 旧项目对应物 | **没有**。全量 grep 旧项目源码（`血量` / `低血` / `生命值` / `自动断线`）只命中第三方模块与本项目自己的 `AdminDisconnect` 前身 `autodisconnect/CometDisconnectModule`；旧项目**没有**「按血量阈值断线」这一功能 |
| 为什么非加不可 | 用户服务器开启**死亡掉落**，挂机挖矿被打死会丢光装备与矿物；血量见底时抢先退服，比「死后自动重生」更能保住财产 |
| 断线动作复用 | 用户指定「调用我模组里面的自动断线」→ 唯一实现 `feature/admindetect/service/AdminDisconnect`（旧 `CometDisconnectModule.disconnect` 的整体搬迁，见该类 javadoc）。本模块**只判时机**，不新增第二条断线链路（第 169 条：同源逻辑只留一份） |

## 2. 新增设置项（控制台「触发条件」页）

| 设置名 | 字段 | 默认 | 取值域 | 说明 |
| --- | --- | --- | --- | --- |
| 自动断线 | `autoDisconnect` | **开** | 布尔 | 开关本体 |
| （断线血量） | `autoDisconnectHealth` | **2 格** | 1~20 | 与开关同一行，格式串 `%.0f 格`（第 124 条：单位写进格式串） |

- 落盘键：`autoDisconnect` / `autoDisconnectHealth`，读写均落在 `MiningSettings#save` / `#load`，载入时 `clamp(1, 20)`（第 172~174 条：改设置必须重启后仍生效）。
- 单位取「格」不取「点」：血量条一格 = 2 点，玩家按格思考；判定时换算成点数与 `Player#getHealth()` 比较。
- 行排布照本页既有口径：数值框在前、开关在后（与自动村民交易「快速停止键」行「控件在前、辅助在后」一致）；行尾注释与两个控件的悬停说明都是本次新写的中文文案。

## 3. 判定逻辑（`AutoMinerModule`）

- 落点：`onTick` 的**最前面**（`client.player` / `client.level` 空判之后、秒破与状态机之前）。
  血量见底那一刻最要紧的是先退出；触发后 `return`，本刻不再推进秒破 / 连锁 / 状态机。
- 判据：`disconnecting == false` 且开关开 且 玩家不是 `isDeadOrDying()` 且 `getHealth() <= 格数 × 2`。
  - **死亡瞬间不断线**：那时掉落已经发生，断线救不回东西，还会抢在自动重生之前把玩家踢出游戏。
- 触发动作：聊天播报 `§c✗ 血量过低 §8▸ 已触发自动断线，正在退出服务器`（走模块统一出口 `info()`，受「状态播报」开关与 5 秒折叠约束）→ `AdminDisconnect.disconnect("自动挖矿", "血量过低（剩余 X 格）")`。
  断开界面上最终显示：`[自动挖矿] 自动断线 ▸ 血量过低（剩余 2 格）`（沿用旧「自动断线」的 `§f自动断线 §8▸ §c<原因>` 结构，仅前缀按第 110/119 条换成模块名）。
- 血量文本：整格写整数（`2 格`），半格带一位小数（`1.5 格`）——血量条本身按半格递进，直接打印点数对不上玩家看到的血条。
- 防重锁：`disconnecting` 在一次会话内只置一次，`onEnable` / `onDisable` 复位（断线后模块由既有 `DISCONNECT` 事件自动关闭，重新进服再开模块必须能再次触发）。
- `onTick` 只在模块启用时被运行时调用（`ModuleManager#tickAll` 的 `isEnabled` 守卫），因此模块关着时不会断线。

## 4. 使用说明同步（第 212 条）

`AutoMinerPage` 的「注意事项」章节末尾补一行：

```
§c⚠ §f控制台「触发条件」页的「自动断线」开启时，血量掉到设定格数会立即退出服务器（防死亡掉落）
```

旧项目原文其余行一字未改；新增行按第 118 条（旧项目不存在的新功能提示）用本章规范撰写。

## 5. 大箱子 ESP 适配 + 标签居中

**问题**（用户实机反馈）：点位框按 `blockBox` 只框「绑定那一格」，大箱子（双箱）画面上只有半个箱子被框住；标签锚点也在绑定那一格中心，看着不居中。

**修复**（`MiningPointRenderer`）：

1. 新增 `pointBox(point)`：非双箱返回 `new AABB(pos)`（与原来逐像素一致），双箱返回 `unionBox(pos, half)` 的 2×1×1 合并框。
2. 新增 `connectedChestHalf(pos)`：**判据照星露谷农场那套**（用户指定参考 `stardew/render/StardewRenderState#connectedChestHalf`）——
   `BlockState#getBlock() instanceof ChestBlock`（铜箱 `CopperChestBlock` 继承它，一并覆盖）、
   `ChestBlock.TYPE != SINGLE`、且**相邻那格确实是箱子**（多校这一条可排除错位 / 被拆一半的异常状态：
   那时 `TYPE` 仍可能是 `LEFT/RIGHT`，照连会让框凭空宽出一格）。
3. `unionBox(a, b)` 的两格并集算法与星露谷那份逐字一致。
4. 标签锚点改为「合并框中心的正上方」：`(box.getCenter().x, point.y() + 1.5, box.getCenter().z)`。
   `EspRenderer.text` 本身就按投影点水平居中绘制，因此框与字一起对着整只箱子居中；单箱时中心与原来完全相同。

**未动的部分**：旧项目 `renderLabel` 会把维度名追加两次（`§7(主世界) §7(主世界)`），属旧项目原样输出，
2026-09-16 用户已拍板「保留重复，一比一」，本次未改。

## 6. 验证状态（第 19 / 46 条：严禁把编译通过当功能已验证）

| 项 | 状态 |
| --- | --- |
| 代码完成 | 已完成（5 个文件） |
| 编译通过 | `.\gradlew build --console=plain -q` → **EXIT=0** |
| 客户端启动验证 | 已做：`runClient` 启动，日志出现 `yiyiaddon initialised (baritone localisation: on)`，`run/crash-reports/` 无当日新增 |
| 实际功能验证 | **未做**。待用户在服务器确认：① 控制台「触发条件」页「自动断线」显示与改动落盘；② 血量掉到设定格数时是否断线、断开界面文案；③ 大箱子框是否覆盖整只箱子、标签是否居中 |
| 待真实环境验证 | 同上（死亡掉落服务器） |

## 7. 遗留

- 本轮及之前数轮改动**均未 commit**；`build/libs/yiyiaddon-1.0-beta1.jar` 为本次 `build` 产物。
- 大箱子判据目前在项目内共有 **4 份**同款实现（星露谷渲染 `StardewRenderState`、星露谷寻路 `ContainerApproachPlanner`、
  自动村民交易 `ContainerESP`、本次自动挖矿 `MiningPointRenderer`）。第 215 条鼓励收敛重复实现，
  但收敛需改动另外三个模块的代码，**未获用户同意，本次不动**；若要收敛，建议提到 `ui/render/world/` 作为共用件后逐处替换。
- 「断线血量」不单独成行、也不做可见性联动：与开关同处一行，开关关掉时数值照常显示并保留（只作为下次开启时的取值）。
  若希望关掉开关时收起该项，需用户点名后再改。
