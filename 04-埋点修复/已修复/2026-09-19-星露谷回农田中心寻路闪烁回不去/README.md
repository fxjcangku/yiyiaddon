# 2026-09-19 星露谷农场「返回农田 ▸ 正在前往农田中心」寻路路径线一直闪、永远回不到中心点

| 项 | 内容 |
| --- | --- |
| 立案日期 | 2026-09-19 |
| 报障原话 | 「星露谷农场有bug 寻路回去[星露谷农场]返回农田 ▸ 正在前往农田中心时候寻路的线一直在闪 然后回不去中心点 你要不要加个日志到本地开发客户端看看」／「无法继续寻路 / 当前任务已释放，稍后重新尝试 / 已停止」 |
| 状态 | **已修复**（用户实机确认「修好了 结案」） |
| 影响面 | 星露谷农场模块：无任何可执行任务、连续静止达到「回中心等待时间」后派发的 `RETURN_CENTER` 任务；表现为 Baritone 路径线高频闪烁、玩家不移动、状态行永久停在「返回农田 ▸ 正在前往农田中心」，3 次重试后播报「无法继续寻路」 |
| 相关报告 | `02-开发报告/项目开发报告/106-复盘-星露谷回农田中心寻路闪烁回不去.md` |

## 1. 现象与复现

- 现象：状态行固定为 `[星露谷农场] 返回农田 ▸ 正在前往农田中心`；画面上 Baritone 的路径线不断变化（闪），
  玩家角色原地不动，永远不进入「已返回农田」；期间农田里已无任何可执行任务（无成熟 / 缺水 / 空盆 / 掉落）。
- 复现路径（逐步）：
  1. 开星露谷农场，正常跑一轮，直到地里没有可做活（或把区域清成只剩生长中的作物）；
  2. 玩家离开农田中心并静止，等「回中心等待时间」到期，模块派发 `RETURN_CENTER`；
  3. 观察状态行与 Baritone 路径线：线一直闪，人不动，回不去。
- 复现难度：截图现场为必现（状态行停在前往农田中心、路径线闪烁）。
- 前提条件：Minecraft 26.1.2 开发客户端 + 第三方服务器；星露谷农场模块开启；服务器已圈定种植区域
  （截图为「区域 2 · 火龙果 · 主世界」）。

## 2. 先走常规流程（第二十八章），说明为什么不够用

- 已做的常规排查（静态）：
  - 读 `StardewCoordinator.navigate()`：回中心的停靠半径走 `planner.navigationRadius()`，`RETURN_CENTER → 0`；
    到达判定走 `planner.arrivalReach()`，`RETURN_CENTER → CENTER_REACH = 0.85`；
    无进展 80 刻 / 总超时 240 刻才 `cancelPath()` 并重试，3 次后拉黑目标 10 秒
    （`StardewCoordinator.java:1104-1131`、`StardewTaskPlanner.java:1071-1098`）。
  - 读 `StardewTaskPlanner.regionCenter()`：回中心目标 = **离玩家最近的区域的几何中心**，
    中心点由 `Region.center()` 给出：`((minX+maxX)/2, maxY+1, (minZ+maxZ)/2)` —— **只按几何算，不做可站立性判定**
    （`StardewTaskPlanner.java:1117-1131`、`StardewRegionManager.java:108-111`）。
  - 读 `FarmNav`：`pathing() = pathingBehavior.isPathing() || customGoalProcess.isActive()`；下发走
    `CustomGoalProcess.setGoalAndPath(new GoalNear(pos, radius))`，且星露谷这条路
    `allowBreak/allowPlace = false`（`FarmNav.java:67-102`）。
  - 读 Baritone 源码对照：`CustomGoalProcess.onTick` 在 `calcFailed` 时会 `onLostControl()` 并让 `state = NONE`，
    随即 `isActive() = false`；`PathingBehavior.isPathing() = hasPath() && !pausedThisTick`，
    **计算期间 `current == null`，两项同时为 false**。`PathRenderer` 在计算期间渲染的是
    `bestPathSoFar / pathToMostRecentNodeConsidered`（`baritone/utils/PathRenderer.java:120-130`）——
    即「线在闪」= **计算一直没结束（或反复重开）**，而不是「路径在走」。
- 为什么定性不了：到底是「中心格不可站立导致 `GoalNear(半径 0)` 无解 → 反复计算失败」，
  还是「反复下发打断计算」「看门狗周期取消」「到达判定够不着」，四者在画面上长得一模一样，
  只靠静态读代码无法裁决；必须采「每一刻的目标 / 到达距离 / Baritone 内部计算与路径状态」。
- 用户是否同意启用埋点修复方案：**是**（用户主动提出「加个日志到本地开发客户端看看」）。

## 3. 假设清单（先写假设，再采集；禁止事后编故事）

| # | 假设 | 判据（用什么数据判定真假） | 结论 |
| --- | --- | --- | --- |
| H1 | 区域几何中心格不可站立（被箱子 / 玻璃箱 / 盆 / 作物载体等占据或悬空），`GoalNear(半径 0)` 无解 → Baritone 反复计算失败 | 探针 B 的 `centerBlock / centerFree / belowBlock`；探针 A 的 `bar` 里 `calc=true` 长期为真、`cur=-`、`goal` 恒为同一坐标 | **成立**（`belowBlock=minecraft:sugar_cane`、`belowFree=true`：中心格脚下无支撑） |
| H2 | 到达判定过严：玩家只能站到中心格相邻格（距离 ≥1.0），永远 > `CENTER_REACH=0.85` | 探针 A 的 `dist` 长期稳定在 1.0~1.4、`arrived=false`，且 `player` 坐标不再变化 | **不成立**（`dist` 恒 3.27：人一步都没动，压根不是「差一点」） |
| H3 | 寻路其实成功但被周期性取消（80 刻无进展 → `cancelPath` → 重发），表现为约 4 秒一轮的闪 | 探针 E 的 `cancel` 与探针 D 的 `dispatch` 成对、周期出现 | **部分成立**（取消确实是 80 刻一次，但根因是 H1：取消只是失败后的清场） |
| H4 | 每刻重复下发寻路（`pathing()` 误判为 false）→ 计算被反复打断 | 探针 D 在同一秒出现 ≥2 次 `dispatch` | **成立**（1 秒内 12 次以上，全为同一坐标 `radius=0`）——这是「路径线一直闪」的直接来源 |
| H5 | 目标被 `NAVIGATION_BLOCK_RETRY_MS`（10 秒）拉黑，形成「动一下 → 停 10 秒」的循环 | 探针 F 的 `block` 事件与探针 A 的 `retry / noProg` 时间线对齐 | **成立**（tick 1840 / 2788 各拉黑一次，正好是 3 次重试用尽） |
| H6 | 玩家其实在移动，只是路径可走却没走到（地形卡住） | 探针 A 的 `player` 坐标逐次变化 | **不成立**（`player=19841,64,-1636` 全程不变，`dist` 恒 3.27） |

## 4. 埋点清单

| 探针 | 位置（location） | 事件（msg） | 关键字段 | 采样策略 |
| --- | --- | --- | --- | --- |
| A | `StardewCoordinator.navigate` | `heartbeat` | `tick, target, radius, dist, arrived, pathing, retry, noProg, navTicks, player, phase, bar` | 每 5 刻（仅 `RETURN_CENTER`） |
| B | `StardewTaskPlanner.tryReturnCenter` | `start` | `center, player, dist, centerBlock, centerPassable, aboveBlock, belowBlock, regions` | 每次派发一条 |
| C | `FarmNav.debugSnapshot` | （作为 A/D/E 的 `bar` 字段） | `goal, pathing, hasPath, custom, cur, curIdx, next, calc, best` | 随 A/D/E |
| D | `FarmNav.goTo` | `dispatch` | `pos, radius, modify, ok, snap` | 每次下发 |
| E | `FarmNav.cancel` | `cancel` | `caller, snap` | 每次取消 |
| F | `StardewTaskPlanner.blockNavigationTarget` | `block` | `type, target, ms` | 每次拉黑 |
| G | `StardewTaskVerifier.verifyResult` | `verify` | `center, dist, arrived, retry, stepTick` | 仅 `RETURN_CENTER` 每次验证 |

- 落盘会话名：`修复前` / `修复后`（同一案例下按会话分开存，便于横向对比）
- 探针代码全部用 `// #region debug-point <探针>:<用途>` 包裹，结案一次 grep 清干净

## 5. 采集与证据

采集命令：

```powershell
node 04-埋点修复/工具/采集服务.js --案例 2026-09-19-星露谷回农田中心寻路闪烁回不去
# 另开一个窗口跑客户端复现，然后：
node 04-埋点修复/工具/分析.js --案例 2026-09-19-星露谷回农田中心寻路闪烁回不去 --会话 修复前
```

关键证据（会话 `修复前`，388 条，00:16:49 → 00:19:34）：

| 观察项 | 数值 | 说明 |
| --- | --- | --- |
| 回中心派发（探针 B，2 次） | `center=19841,65,-1633`；`centerBlock=air`、`centerFree=true`；**`belowBlock=minecraft:sugar_cane`、`belowFree=true`** | 中心格悬空：脚下是甘蔗（无碰撞作物载体），没有任何可站立的支撑面 |
| 区域与玩家脚层 | `R3:X19839~19843 Z-1635~-1631,y64~64`；玩家 `player=19841,64,-1634`、`onGround=true` | 真实可站层是 y64；几何中心被 `maxY+1` 抬到 y65（悬空） |
| 探针 A 心跳（116 条） | `dist=3.27` 恒定、`arrived=false`、`player=19841,64,-1636` 全程不变 | 人一步都没走，不是「差一点」而是「压根过不去」 |
| 探针 D（225 条） | 00:18:29.673 → 00:18:36.275 之间对 `19841,65,-1633`（`radius=0`）下发 **60 次以上** | 每刻重发 → Baritone 计算被反复打断，这就是「路径线一直闪」 |
| 探针 C 快照 | 交替两种：`goal=GoalNear{x=19841,y=65,z=-1633,rangeSq=0} custom=true calc=true hasPath=false cur=-` 与 `goal=null custom=false calc=false` | 目标永远算不出可落地路径；`calcFailed` 后 `CustomGoalProcess` 自清 `state` → 下一 tick 再从零开始 |
| 看门狗（探针 F，2 次） | 00:18:36.409 `tick=1840` 拉黑 10 秒；00:19:23.810 `tick=2788` 再拉黑 | `retry` 1→2→3 用尽（80 刻无进展 × 3）→ 播报「无法继续寻路 / 当前任务已释放」 |
| 10 秒后再试 | 00:19:07.265 再次派发（`dist=1.9`），00:19:11 / 00:19:15 依旧 `calc=true`，00:19:23.810 又拉黑 | 同一坐标死循环：只要中心格还站不住，就永远回不去 |
| 其它任务（探针 E，43 条） | 大量 `pathing=true hasPath=true cur=n/m@…`（`GoalNear rangeSq=4/1/0` 均正常跑） | Baritone 与避让设置都正常：**只有回中心这一个坐标无解** |

## 6. 根因

四层叠加，缺一层都不会发病：

1. **中心点被抬到空中**：`Region.center()` 取 `maxY() + 1`
   （[StardewRegionManager.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/stardew/region/StardewRegionManager.java#L108-L111)）。
   这个 `+1` 是旧项目 START/END 点（记的是地面层）的口径；本项目的区域是**点方块圈出来的**，
   在这些服务器上作物载体（甘蔗）与玩家脚层同层（y64），再 +1 就落到了 y65 —— 而中心列 y64 是甘蔗，
   **脚下没有任何支撑面**。
2. **回中心用的是全模块最严的一套判定**：`navigationRadius()==0`（必须站进那一格）
   + `CENTER_REACH==0.85`（必须贴到格子中心 0.85 格内）
   （[StardewTaskPlanner.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/stardew/task/StardewTaskPlanner.java#L1120-L1147)）。
   目标格站不住 → `GoalNear(半径 0)` 无解 → Baritone 算不出任何可落地路径。
3. **失败后立刻重新下发**：`calcFailed` 时 `CustomGoalProcess.onLostControl()` 会把 `state` 清成 `NONE`，
   于是 `FarmNav.pathing()`（`isPathing() || customGoalProcess.isActive()`）返回 false
   （[FarmNav.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/platform/navigation/FarmNav.java#L91-L102)），
   `navigate()` 每刻重发同一目标 → 计算永远打不完 → 玩家看到的「路径线一直闪、人不动」。
4. **兜底变成循环**：80 刻无进展 → `cancelPath()` + `retryCount++`；3 次用尽 → 拉黑 10 秒并播报
   「无法继续寻路」；10 秒后再派发还是同一个站不住的中心点 → 无限循环
   （[StardewCoordinator.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/stardew/task/StardewCoordinator.java#L1141-L1156)）。

## 7. 修复

| 文件 | 改动 |
| --- | --- |
| `feature/stardew/task/StardewTaskPlanner.java` | `regionCenter()` 选出最近区域后，把几何中心落到**站得住的格子**（新增 `standableCenter`：中心列自上而下 → 四邻各自自上而下，最多向下 3 格，落点 XZ 仍须在本区域内；全部候选都站不住时回退几何中心，保留原兜底） |
| `feature/stardew/navigation/ContainerApproachPlanner.java` | 把「可站立」判据抽成唯一一份 `public static boolean standable(BlockPos)`，`legalStand` 复用它（同源，禁止两处各写一套） |

## 8. 验证（修复后同一路径再采一次）

会话 `修复后`（53 条，00:23:38 → 00:25:16）：两次回中心全部一次到位。

| 指标 | 修复前 | 修复后 | 判定 |
| --- | --- | --- | --- |
| 回中心目标点（探针 B） | `19841,65,-1633`；`belowBlock=minecraft:sugar_cane`、`belowFree=true`（脚下无支撑） | `19841,64,-1633`；`belowBlock=minecraft:sand`、`belowFree=false`（脚下有支撑） | 落点已回到真实可站层 |
| Baritone 快照（探针 C） | `calc=true hasPath=false cur=-` 与 `custom=false` 反复横跳 | `pathing=true hasPath=true cur=1/6@19841,64,-1633` → `cur=4/6` | 一次算出可落地路径并执行 |
| 玩家位移（探针 A） | `player` 全程 `19841,64,-1636` 不变，`dist` 恒 `3.27` | `19846→19845→19844→19843→19842`，`dist` `5.47→4.93→3.82→2.45→1.12` | 真的走过去了 |
| 验证阶段（探针 G） | **0 条**（从未到达，进不了 verify） | 2 条：`arrived=true, dist=0.51 / 0.53` | 到达判定通过，播报「已返回农田」 |
| `dispatch` 次数（探针 D） | 225（6 秒内同一坐标 60+ 次） | 19 | 闪烁源头消失 |
| `cancel` 次数（探针 E） | 43 | 23 | 无 80 刻无进展取消 |
| 看门狗拉黑（探针 F） | 2 次（tick 1840 / 2788，播报「无法继续寻路」） | **0 次** | 兜底不再被触发 |
| 心跳条数（探针 A） | 116 | 7 | 回中心「发一次、走到、结束」 |

- 用户实机确认：「修好了 结案」
- 插桩删除后 `.\gradlew build --console=plain -q` → **EXIT=0**

## 9. 收尾核对（三件，缺一不许归档）

- [x] 全部 `#region debug-point` 已删除；`Grep "debug-point|DebugProbe|7777"` → 仅剩主题色 `0x777777`（4 处，与本案无关）
- [x] 采集服务已停止（端口 7777 连接数 0）
- [x] 本文件夹已从 `进行中/` 移到 `已修复/`，并在 `02-开发报告/项目开发报告/` 留复盘 `106-复盘-星露谷回农田中心寻路闪烁回不去.md`

## 10. 遗留

- 同类风险点：**任何「只按几何算出来的落点」都可能站不住**（不止回中心）。本次只修了回中心；
  若将来给其它模块加「固定站立点」类目标，一律先过 `ContainerApproachPlanner.standable`。
- 区域存储的 Y 语义（点方块得到的是作物载体层）与旧项目 START/END 点（地面层）不同源，
  本次选择「就地落地」而不是改区域语义，避免影响扫描范围与既有区域存档。
- 全部改动**未 commit**。
