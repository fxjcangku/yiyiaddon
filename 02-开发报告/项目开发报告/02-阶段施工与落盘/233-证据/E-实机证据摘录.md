# 233 实机证据摘录（原文摘录，逐条可在同目录 `*-原始日志.txt` 中检索）

> 摘录规则：只从原始日志里**整行照抄**（含时间戳与日志来源），不改一个字。
> `O1` = 开发客户端观察生命周期回归；`C1` = 开发客户端覆盖 / 观察组合回归；`C2` = 开发客户端关闭态回归。

---

## O1 · 观察生命周期（runClientSeedObservationTest · 第 5 次 · 全通过）

```
[15:55:14] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：已开启 种子挖矿 + 显示预测钻石（种子 20260922，半径 1），等待附近区块逐个预测…
[15:55:20] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：玩家所在区块 (0,0) 已有 45 个候选；快照总览 目标区块 1 / 候选 45（未观察 0 / 已确认 45 / 当前缺失 0 / 调度敏感 0）
[15:55:20] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：本区块候选 45 个 → 已确认 45 / 当前缺失 0 / 未观察 0（区块已加载，未观察应为 0）
[15:55:20] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：CONFIRMED 证据 #1 → (9,-58,8) 实际方块 minecraft:deepslate_diamond_ore
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：发送指令 /setblock 9 -58 8 air
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：MISSING 证据 #1 → (9,-58,8) 实际方块 minecraft:air，状态 缺失（未重跑预测、未重启计算器）
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：发送指令 /setblock 9 -58 8 minecraft:deepslate_diamond_ore
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：恢复证据 #1 → (9,-58,8) 实际方块 minecraft:deepslate_diamond_ore，状态 已确认
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：发送指令 /tp 4105 100 8
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：已传送到 (4105,100,8)，等待目标区块 (0,0) 从客户端卸载…
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：UNOBSERVED 证据 #1 → (9,-58,8) 区块 (0,0) 已卸载，状态 未观察
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：发送指令 /tp 9 -56 8
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：重新加载证据 #1 → 状态 已确认（实际方块 minecraft:deepslate_diamond_ore）
[15:55:21] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：A 侧残留基线 —— 缓存 3 个区块 / 候选 94 个 / 渲染条目 94 条
[15:55:22] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：SERVER A→B 证据 #1 → 已进入 B（127.0.0.1:25566），A 的残留全部归零：缓存 0 / 候选 0 / 渲染条目 0（A 侧基线 3 / 94 / 94），覆盖排队 0
[15:55:30] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：B 侧已开始预测 —— 缓存 1 个区块 / 候选 45 个（种子 20260922），接下来验证维度与种子清理
[15:55:30] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：维度清理证据 #1 → 下界（下界）中 缓存 0 / 候选 0 / 渲染条目 0 / 覆盖排队 0，身份 未建立（覆盖已停止）
[15:55:31] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：维度恢复证据 #1 → 已回到主世界，覆盖自动重新开始：缓存 1 个区块 / 候选 45 个，身份 世界 127.0.0.1:25566 / 种子 20260922 / 维度 minecraft:overworld / Minecraft 26.1.2 / 会话 #6
[15:55:31] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：改种子前 —— 缓存 2 个区块 / 候选 67 个 / 渲染条目 67 条（种子 20260922）
[15:55:31] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：改种子清理证据 #1 → 种子 20260922 → 12345 后立刻：缓存 0 / 候选 0 / 渲染条目 0 / 排队 0（改之前 2 / 67 / 67）
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：新种子恢复证据 → 身份已切换为「世界 127.0.0.1:25566 / 种子 12345 / 维度 minecraft:overworld / Minecraft 26.1.2 / 会话 #7」，已重新预测 1 个区块
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：====== 结果汇总 ======
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：CONFIRMED 证据 1 条 / MISSING 证据 1 条 / 恢复证据 1 条 / 卸载→未观察证据 1 条 / 重新加载证据 1 条 / Server A→B 证据 1 条 / 下界清理证据 1 条 / 回主世界恢复证据 1 条 / 改种子清理证据 1 条
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：最终读数 缓存目标区块 1 / 上限 256，累计淘汰 0，身份 世界 127.0.0.1:25566 / 种子 12345 / 维度 minecraft:overworld / Minecraft 26.1.2 / 会话 #7；覆盖半径 1（目标 9 个），中心 0,0，在跑 -1, 0，排队 7，累计提交 11 / 完成 7（移动丢弃排队 0，失败不再重试 0）；观察 候选 31（未观察 0 / 已确认 31 / 当前缺失 0）；已登记区块 1，绑定世界 有；渲染 目标区块 1 / 候选 31（未观察 0 / 已确认 31 / 当前缺失 0 / 调度敏感 0）
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：状态是否独立于预测：PredictionCertainty 与 OreObservationState 各自保留，全程未产生 SUSPICIOUS
[15:55:32] [Render thread/INFO] (yiyiaddon/seedpoc) 233观察回归：已关闭显示预测钻石；缓存 0 个区块，渲染快照 目标区块 0 / 候选 0（未观察 0 / 已确认 0 / 当前缺失 0 / 调度敏感 0）
```

---

## C1 · 覆盖 / 观察组合（runClientSeedCoverageTest · 三段全通过）

```
[16:06:40] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：第一段开跑 —— 客户端种子填 12345（服务器世界种子 20260922），范围 1，预期结果：候选几乎全部是「当前缺失」，且一条「可疑」都不该出现
[16:06:47] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：错误种子证据 #1 → 客户端种子 12345 / 服务器世界种子 20260922：候选 18 个 → 已确认 0 / 当前缺失 18 / 未观察 0（候选文案「缺失」；全程无「可疑」）
[16:06:47] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：错误种子抽样 → (-6393,-60,6080) 实际方块 minecraft:deepslate，观察状态「缺失」（只说「预测位置当前实际不是钻石」，不下「种子错 / 假矿」结论）
[16:06:47] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：第二段开跑 —— 种子改为 2，准备传送到冻结口径的争议位置 (-6385,-59,6085)
[16:06:47] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：发送指令 /gamemode spectator
[16:06:47] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：发送指令 /tp -6385 -59 6085
[16:06:49] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：调度敏感证据（预测侧）→ (-6385,-59,6085) 确定性「调度敏感」（种子 2；与冻结口径一致）
[16:06:49] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：调度敏感证据 #1（观察侧）→ (-6385,-59,6085) 实际方块 minecraft:deepslate，观察状态「缺失」；确定性仍为「调度敏感」（两个维度各自保留，未产生「可疑」）
[16:06:49] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：第二段所在区块预测总览 （本区块调度敏感候选 1 条）
[16:06:49] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：第三段开跑 —— 种子回到 20260922，范围设为默认 3（目标 49 个区块），等待铺满并采集渲染开销
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：渲染开销证据 #1 → 范围 3（49 个目标区块）：已预测 49 个区块 / 候选 1100 个（未观察 0 / 已确认 1099 / 当前缺失 1 / 调度敏感 0）
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：快照重建读数 → 本窗口重建 50 次、平均每次 129.4 微秒；每次固定分配 = 1100 个候选各一条SeedRenderEntry + 1 次列表副本 + 1 个计数 + 1 个快照；当前 FPS 119；缓存 49/256 个区块
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：====== 结果汇总 ======
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：错误种子证据 1 条 / 调度敏感观察证据 1 条 / 渲染开销证据 1 条
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：最终读数 缓存目标区块 49 / 上限 256，累计淘汰 0，身份 世界 127.0.0.1:25565 / 种子 20260922 / 维度 minecraft:overworld / Minecraft 26.1.2 / 会话 #4；覆盖半径 3（目标 49 个），中心 -400,380，在跑 —，排队 0，累计提交 53 / 完成 51（移动丢弃排队 0，失败不再重试 0）；观察 候选 1100（未观察 0 / 已确认 1099 / 当前缺失 1）；已登记区块 49，绑定世界 有；渲染 目标区块 49 / 候选 1100（未观察 0 / 已确认 1099 / 当前缺失 1 / 调度敏感 0）
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：关闭显示预测钻石（顺带核对关闭即清空）
[16:06:55] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：关闭后 缓存 0 个区块 / 渲染快照 目标区块 0 / 候选 0（未观察 0 / 已确认 0 / 当前缺失 0 / 调度敏感 0）
```

### C1 · 第一次运行的「错误种子」补充证据（出生点区块，与冻结 12345 (0,0)=31 对上）

```
[16:03:52] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：错误种子证据 #1 → 客户端种子 12345 / 服务器世界种子 20260922：候选 31 个 → 已确认 0 / 当前缺失 31 / 未观察 0（候选文案「缺失」；全程无「可疑」）
[16:03:52] [Render thread/INFO] (yiyiaddon/seedpoc) 233覆盖回归：错误种子抽样 → (9,-61,11) 实际方块 minecraft:deepslate，观察状态「缺失」（只说「预测位置当前实际不是钻石」，不下「种子错 / 假矿」结论）
```

---

## C2 · 关闭态（runClientSeedOffRegression · 通过）

原始日志里**没有任何** `种子挖矿｜Worker：正在启动本地世界生成计算器` 行（证明计算器一个刻都没被拉起）。

```
[16:08:07] [Render thread/INFO] (yiyiaddon/seedpoc) 233关闭态回归：已进入世界（主世界），全程不碰任何开关，观察 20 秒
[16:08:07] [Render thread/INFO] (yiyiaddon/seedpoc) 233关闭态回归：关闭态证据 #1（静置 3 秒后）→ 计算器运行 否 / 缓存 0 个区块 / 覆盖已预测 0 / 覆盖排队 0 / 正在预测 —（范围设定值 49 只是配置的函数，不代表在工作） / 观察候选 0 / 渲染快照 0 条 / 显示预测钻石 关 / 运行时身份 未建立
[16:08:24] [Render thread/INFO] (yiyiaddon/seedpoc) 233关闭态回归：关闭态证据 #2（再观察 17 秒后）→ 计算器运行 否 / 缓存 0 个区块 / 覆盖已预测 0 / 覆盖排队 0 / 正在预测 —（范围设定值 49 只是配置的函数，不代表在工作） / 观察候选 0 / 渲染快照 0 条 / 显示预测钻石 关 / 运行时身份 未建立
[16:08:24] [Render thread/INFO] (yiyiaddon/seedpoc) 233关闭态回归：结论 —— 计算器未启动 / 覆盖不工作 / 缓存与观察与渲染快照全空 / 渲染闸门关闭
```

---

## P1 / P3 / P4 · 生产产物冒烟（本目录内的三份 `P*-*.txt` 为正本）

| 文件 | 场景 |
| --- | --- |
| `P1-生产smoke-parity-固定集对照.txt` | 正式产物 · Worker vs 单人 Oracle 固定集（15 目标） |
| `P3-生产smoke-观察回归-client-stdout.txt` | 正式产物 · 233 观察生命周期（真实专用服务器，需 OP） |
| `P4-生产smoke-真实103Mods-client-stdout.txt` | 正式产物 + 真实用户 103 个 Mod（Sodium / Iris / C2ME / Lithium）· 233 观察生命周期 |
