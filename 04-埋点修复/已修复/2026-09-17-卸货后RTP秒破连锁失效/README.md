# 2026-09-17 卸货 / RTP 后秒破·连锁·ESP 全失效，以及同一块砂岩被反复重挖

| 项 | 内容 |
| --- | --- |
| 立案日期 | 2026-09-17 |
| 报障原话 | 「卸完活 重新进入状态 秒破 连锁失效 esp框也不见了 到底是什么问题」／「一直挖一个方块挖不掉 砂岩」 |
| 状态 | **已修复**（用户实机确认「好像已经修复了 可以结案了」） |
| 影响面 | 自动挖矿：秒破、连锁挖矿、挖矿点 / 进度框 ESP；发生在自动卸货 → 自动传送（RTP）回到野外之后 |
| 相关报告 | `02-开发报告/项目开发报告/79-复盘-卸货后RTP秒破连锁失效与砂岩反复重挖.md` |

## 1. 现象与复现

- 现象：卸货完成 → 自动 RTP 回野外 → **秒破不再生效**（方块不掉）、**连锁不再触发**（没提示、没扫脉）、
  **ESP 框也一起消失**；重启模块后才恢复。另外偶发「同一块砂岩反复闪、一直挖不掉」。
- 复现路径：
  1. 开自动挖矿，让它自己挖满 → 自动卸货 → 自动 RTP；
  2. 回野外后观察秒破 / 连锁 / ESP；
  3. 砂岩那条：在砂岩地形（沙漠 / 沙滩地下）挖通道时命中特定方块。
- 复现难度：第 1 条**必现**（每次卸货回来都这样）；砂岩那条偶现（依赖地形）。
- 前提条件：Minecraft 26.1.2 开发客户端 + 第三方服务器（有 RTP 传送）；自动挖矿模块开启。

## 2. 先走常规流程（第二十八章），说明为什么不够用

- 已做：chatlog 播报时间线、崩溃报告、静态读 `MiningFastBreakController` 全部入口、
  读 Minecraft / Baritone 源码对照协议语义；逐条排除「秒破间隔设为 0」「线宽倍率叠加」等猜测。
- 为什么定性不了：现象是**多刻状态机 + 网络包时序**问题——「有没有发包」「为什么没发包」「发了几次」
  这些只在运行期存在，静态读代码推不出「实际走的是哪个分支」，必须要有每个刻的裁决快照。
- 用户是否同意启用埋点修复方案：**是**（用户主动要求「你看看日志」「复现了 卸完货回来就失效了」）。

## 3. 假设清单（先写假设，再采集）

| # | 假设 | 判据（用什么数据判定） | 结论 |
| --- | --- | --- | --- |
| H1 | 状态机没真正回到能挖的状态（卡在 GO_WILD / 传送等待） | `AutoMinerModule.onTick` 心跳里的 `state` 长期不是 MINING | 不成立（状态正常回到 MINING） |
| H2 | 秒破把自己拉黑，大量请求被 PASS 回原版 | `start` 裁决分布 + `blocked` 增长 / `blocked-list` 出现 | 不成立（`blocked` 只 1 次） |
| H3 | 长期冷却锁死，秒破根本发不出包 | `tick` 心跳里的 `gap = tickCount - nextStartTick` 长期为负 | **成立**（长期 -4000 量级） |
| H4 | Baritone 的 mine 没重启，压根没有破坏入口 | 心跳里的 `miningActive` / `pathing` | 不成立 |
| H5 | 「一发即破」近路只发 START 不发 STOP，服务端永远等不到 STOP | 同坐标 `begin` 频率 + `delta` + `pending` / `brokenAge` | **成立** |
| H6 | 兜底（超时重试 → 拉黑）被「原版客户端预测破坏」造成的假记账绕过 | `sweep` 的 `credited` 计数 + 命中时该坐标的真实方块 | **成立** |

## 4. 埋点清单

| 探针 | 位置（location） | 事件（msg） | 关键字段 | 采样策略 |
| --- | --- | --- | --- | --- |
| A | `FastBreak.tick` | `heartbeat` | `tick, gap, phase, pending, blocked, brokenAge, ready` | 每刻 |
| B | `FastBreak.start` | `verdict` | `reason, result`（8 种裁决，同一组合 20 刻内只记一次） | 计数制 |
| B | `FastBreak.begin` / `advance` / `handOff` / `mineRequested` / `sweep` | `detail` / `hand-off` / `chain-dispatch` / `stale` | `pos, block, delta, required, attempts, age` | 每次 |
| C | `AutoMinerModule.onTick` | `heartbeat` | `state, pathing, mining, speed, active` | 每刻 |
| E | `VeinMiner.tick` / `root` / `resume` | `heartbeat` / `root-scan` / `chain-finished` | `scanned, queue, suspended, roots` | 每刻 / 每次 |
| G | `FastBreak.epoch` | `player-entity-changed` | `oldTickCount, newTickCount, staleNextStartTick, droppedPending` | 触发即记 |
| H | `FastBreak.sweep` | `credited` | `pos, age, live`（命中时该坐标的真实方块） | 每次 |

- 落盘会话名：`修复前` / `修复后`（同一案例下按会话分开存，便于横向对比）
- 探针代码全部用 `// #region debug-point <探针>:<用途>` 包裹，结案一次 grep 清干净

## 5. 采集与证据

采集命令（当时的调试服务器即今日《工具/采集服务.js》的前身）：

```powershell
node 04-埋点修复/工具/采集服务.js --案例 2026-09-17-卸货后RTP秒破连锁失效
node 04-埋点修复/工具/分析.js --案例 2026-09-17-卸货后RTP秒破连锁失效 --会话 修复前
node 04-埋点修复/工具/分析.js --空档 ts --最小间隔 500
```

关键证据：

| 观察项 | 修复前 | 说明 |
| --- | --- | --- |
| `gap = tickCount - nextStartTick` | 长期 **-4000 量级** | 冷却刻是旧实体的数 → 对每个方块恒「还没冷却完」 |
| 同坐标 `begin` 次数 | `-19890,40,-49490` **1152 次**；`-19889,43,-49490` **620 次** | 每 50ms 一轮，方块是 sandstone、`delta=1.4583` |
| `begin` 的 `delta` / `required` | `1.4583` / `0` | 客户端认为「一刻即破」 |
| `pending-await` | 371 次 | 同一块一直在待确认集合里 |
| 心跳 `brokenAge` | **恒为 1** | 每刻都在记「刚破坏」，但方块其实没消失 |
| `sweep` 的 `stale` | 只有 4~56 次 | 说明兜底几乎没轮到（H6 成立） |
| 同窗口模块关掉后 | 只 14 条 `start → not-ready → PASS` | 用户「模块没开也挖不掉」→ 该砂岩是**服务端不给挖** |

## 6. 根因（三条，互相独立）

1. **换实体后 tickCount 归零，冷却刻没跟着重置**：`nextStartTick` 由旧实体刻数（4000+）写下，
   RTP 换实体后新实体从 0 起 → `player.tickCount < nextStartTick` 恒成立 → `start()` 一律 COOLDOWN
   → 秒破一包不发；连锁每一块都走同一道门 → 没有破坏 → 依赖破坏的 ESP 也一起消失。
2. **「一发即破」近路只发 START 不发 STOP**：服务端 `ServerPlayerGameMode:203` 的 insta 判定用的是
   **它自己**算的 `progress >= 1.0`；不满足时走 `:205-217` 开累计会话**等 STOP**。我们每刻补一个
   START 会把 `:185 destroyProgressStart = gameTicks` 每刻重置，服务端永远攒不到 0.7，
   同时命中 `:206-209` 回滚方块 → 每 50ms 一轮死循环。
3. **待确认期间放行原版 → 假的「记账」**：原版 `MultiPlayerGameMode:266-275` 在
   `destroyProgress >= 1.0` 时会做**客户端预测破坏**（本地删块）；下一 tick `sweepPending`
   把这次本地删除当成服务端确认 → 记账清空、`brokenAge` 刷新 → 「超时重试 → 拉黑 → 交还原版」
   那套兜底**永远不触发**；服务端随后回滚 → 又一轮。

## 7. 修复

| 文件 | 改动 |
| --- | --- |
| `feature/mining/fastbreak/MiningFastBreakController.java` | 新增 `syncEpoch(mc)`：比实体引用 + tickCount 回退，命中即整批复位（`nextStartTick`/`pending`/`retries`/`blocked`/`lastBroken*`），四个入口最前面调用 |
| 同上 | 删掉 `delta >= 1.0` 的「只发 START」近路，统一 `requiredElapsedTicks(delta) == 0 → 同刻补发 STOP`；硬度 0 的 +∞ 归为「本刻达标」 |
| 同上 | `pending` 命中时由 `PASS` 改为 `ACCEPTED`（接管、但**不放行原版也不重发包**），让「方块变化」只可能来自服务端真包 |

## 8. 验证（修复后同一路径再采一次）

| 指标 | 修复前 | 修复后 | 判定 |
| --- | --- | --- | --- |
| 单坐标 `begin` 次数 | 1152 / 620 | **最多 2** | 死循环消失 |
| `sweep credited` 的 `live` | —（无字段） | 441 条**全是 `Block{minecraft:air}`** | 记账可信、无假记账 |
| `pending-await` 裁决 | 371 次 `PASS` | 187 次 `ACCEPTED` | 新语义生效 |
| `sweep stale` | 56 | 2 | 兜底回到低概率触发 |
| `epoch player-entity-changed` | —（无探针） | **5 次**（多次 RTP 全部识别） | 归零不再锁死 |
| `VeinMiner.root` / `chain-finished` | 有（但反复被死循环打乱） | 342 / 12 | 连锁正常出根、正常收尾 |
| `tick` 序列 | 单调 | 580 → 420（被正确识别为归零） | — |

- 用户实机确认：「好像已经修复了 可以结案了」
- `.\gradlew build --console=plain -q` → **EXIT=0**

## 9. 收尾核对

- [x] 全部 `#region debug-point` 已删除（28 段、约 178 行）；`dbg` / `dbgVerdict` / `heldItemName` /
      `DBG_CLIENT` / `DBG_URL` / `dbgLastVerdict*` 一并删除；全仓库 grep `debug-point|dbgVerdict|heldItemName|7777` → 0 命中（只剩主题色 `0x777777`）
- [x] 采集服务已停止（端口 7777 不再监听）
- [x] 本文件夹已归入 `已修复/`；复盘已写在 `02-开发报告/项目开发报告/79-复盘-….md`

## 10. 遗留

- 砂岩那条的**最终定性**：服务端不给挖（模块关掉、纯原版也挖不掉），非本项目缺陷；
  本项目只保证「不死磕」——3 次重试后拉黑该坐标 600 刻、放行原版并播报
  「⚠ 秒破让位原版 ▸ 服务端未确认方块破坏」。
- 同类风险点：`syncEpoch` 只在「实体更换 / tickCount 回退」时触发；若将来出现
  「换世界但 tickCount 不回退」的场景，需要补判据（例如 `level` 引用变更）。
- 全部改动**未 commit**。
