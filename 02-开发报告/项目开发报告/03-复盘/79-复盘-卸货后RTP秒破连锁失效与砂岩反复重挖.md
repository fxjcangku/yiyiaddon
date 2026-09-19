# 79 - 复盘：卸货 / RTP 后秒破·连锁·ESP 全失效，与「同一块被反复重挖」

- 日期：2026-09-17
- 触发：用户「卸完活 重新进入状态 秒破 连锁失效 esp框也不见了 到底是什么问题」「一直挖一个方块挖不掉 砂岩」
- 涉及文件：`feature/mining/fastbreak/MiningFastBreakController.java`（`feature/mining/vein/MiningVeinMiner.java`、`feature/mining/AutoMinerModule.java` 仅插桩）
- 验证：`./gradlew build --console=plain -q` → **EXIT=0**；**实机已确认**（用户「好像已经修复了 可以结案了」，数据见 §6）

---

## 1. 取证方式

用一次性插桩（把 JSON 事件 POST 到本地 `127.0.0.1:7777` 的调试服务器）开了 8 组探针：
`A` 秒破主循环心跳、`B` 四入口裁决与 begin 明细、`C` 模块 onTick 心跳、`E` 连锁心跳/根扫描/收尾、
`G` 换实体、`H` 待确认集合记账。复现窗口 `tick 260 → 6300`（含两次 RTP）。

结案时插桩**全部删除**（28 个 region、约 178 行），调试服务器已停，`.dbg/` 与 `debug-mining-stall-after-rtp.md` 已删。

## 2. 根因一：RTP / 换维度后 `player.tickCount` 归零，冷却刻却还是旧实体的数

**证据**：`FastBreak.tick` 心跳里 `gap = player.tickCount - nextStartTick` 长期是 **-4000 量级**的负数。

**机理**：`nextStartTick` 是「方块间隔冷却」的下次可开始刻，由**旧实体**的 tickCount 写下（4000+）。
RTP 换实体后新实体 `tickCount` 从 0 起，而 `start()` 的门槛是

```java
if (player.tickCount < nextStartTick) return StartResult.COOLDOWN;   // 对每个方块恒成立
```

于是**秒破一包不发**（裁决里 `cooldown` 计数可见）。连锁的每一块都走 `start()`/`mineRequested()` 同一道门
→ 派不出任何一块（没有矿脉根）→ 连锁自然「失效」；没有破坏动作，依赖破坏的
挖掘点 / 进度框 / 矿物箱一类的 **ESP 也一起消失**。状态机的「没进展」判据还会把这个静止当成卡死，
反复重下发 mine，看起来就像模块坏了、重开一次才好（重开清空了全部状态）。

**修复**：新增 `syncEpoch(mc)` —— 记住上次的 `mc.player` 引用与 `tickCount`，
发现**实体换了**或**tickCount 回退**（归零 / 换世界）就整批复位（`nextStartTick`、`pending`、`retries`、
`blocked`、`lastBroken*` 等），并在 `start` / `continueBreaking` / `mineRequested` / `tick` 四个入口最前面调用。

**验证**：新会话里 `G FastBreak.epoch player-entity-changed` 触发 5 次（多次 RTP 都命中），
复现窗口 `tick` 从 580 掉到 420 被正确识别；`gap` 不再出现 -4000 级负数，RTP 后秒破 / 连锁 / 框全部回来。

## 3. 根因二：秒破的「一发即破」近路只发 START、不发 STOP

**证据**（同一块砂岩 `BlockPos{x=-19889, y=43, z=-49490}`）：

| 观察 | 数值 |
| --- | --- |
| `FastBreak.begin` / `handOff` | **各 416 次**（50ms 一轮 = 每客户端刻一次） |
| `begin` 的 `delta` | **1.4583**（效率 V 下界合金镐，`required=0`） |
| `pending-await` | 371 次（同一块一直在待确认集合里） |
| 心跳 `brokenAge` | **恒为 1** |
| `sweep stale` / `blocked` | 只有 4 / 1（兜底几乎没轮到） |

**机理**（服务端源码 `ServerPlayerGameMode`）：

- `:203` 「一发即破」要的是**服务端自己**算出的 `progress >= 1.0F`，判完才 `destroyAndAck("insta mine")`；
- 否则走 `:205-217` else 分支：开一个**累计破坏会话**，等服务端等我们的 **STOP**；
- 我们每刻补一个新 START → `:185 destroyProgressStart = gameTicks` 被**每刻重置** → 服务端自己永远攒不到 0.7；
- 新 START 又命中 `:206-209`：`isDestroyingBlock` 仍为真 → **服务端回滚一次方块** → 再挖 → 死循环。

**修复**：删掉那条近路，统一走 `requiredElapsedTicks(delta) == 0 → 同刻补发 STOP`，把 0.7 判定交回服务端；
同时把「硬度 0 的 +∞」归为「本刻达标」而不是「挖不动」，避免草 / 火把被误拉黑。
**验证**：同一坐标的 `begin` 从 1152 次降到 **2 次**。

## 4. 根因三：`pending` 期间「放行原版」，原版的**客户端预测破坏**被当成服务端确认

**机理**：`sweepPending` 认账的唯一依据是「这个坐标的方块不再是原来那个」。
而待确认期间我们把请求**放行给原版**后，原版 `MultiPlayerGameMode:266-275` 会在
`destroyProgress >= 1.0` 时做**客户端预测破坏**（本地直接把方块删掉）——
下一 tick 的 `sweepPending` 把这次「本地删除」当成服务端确认 → **记账清空、`brokenAge` 刷新**，
于是「超时重试 → 拉黑该坐标 → 交还原版」那套兜底**永远不会触发**；服务端随后回滚方块 → 每刻重来一轮。
这就是 §3 里 `brokenAge` 恒为 1、`stale` 只有个位数的直接原因。

**修复**：`pending` 命中时改为 `return StartResult.ACCEPTED`（**接管、但不放行原版、也不重发包**）。
这样方块的唯一变化来源变成**服务端真包**，记账才可信：真破了下一 tick 就认账；
服务端不认则 20 刻超时 → 重试 3 次 → 拉黑该坐标 600 刻放行原版，**不再死磕**。

**验证**：`H FastBreak.sweep credited` 441 条，`live` **全部是 `Block{minecraft:air}`**（真破坏），
`sweep stale` 只有 2 条，单坐标 `begin` 最多 2 次。

## 5. 砂岩本身：服务端不让挖，不是秒破的锅

用户实测「**模块没开也挖不掉**」；同一窗口里我们只收到 14 条 `FastBreak.start → not-ready → PASS`
（模块关着，我们只是放行给原版），随后原版同样挖不动。结论：该处方块被**服务端拒绝**
（领地 / 主城保护、防作弊或假方块一类），谁都挖不掉，与该模块无关。

我们该做的只是「**不死磕**」——见 §4 的兜底：3 次重试后拉黑该坐标、放行原版并播报
「⚠ 秒破让位原版 ▸ 服务端未确认方块破坏」，让状态机/Baritone 转去挖别的块。

## 6. 实机验证结论（用户确认「可以结案了」）

新会话 `3563` 条事件，全部指标正常：

| 指标 | 数值 | 判定 |
| --- | --- | --- |
| 单坐标 `begin` 最多 | **2**（修复前 1152 / 620） | 死循环消失 |
| `H credited` 的 `live` | 441 条**全是 air** | 记账可信、无假记账 |
| `pending-await` | 187 条 `ACCEPTED` | 新语义生效 |
| `sweep stale` | 2 条 | 兜底按预期低概率触发 |
| `G epoch player-entity-changed` | 5 次 | 多次 RTP 全部识别、归零不再锁死 |
| `VeinMiner.root` / `chain-finished` | 342 / 12 | 连锁正常出根、正常收尾 |
| `tick` 序列 | 580 → 420 | 归零被正确识别 |

## 7. 收尾清理

- 删除 28 个 `#region debug-point` 块（`MiningFastBreakController` 24、`MiningVeinMiner` 3、`AutoMinerModule` 1，约 178 行）；
- 删除整套插桩设施：`dbg` / `dbgVerdict` / `heldItemName` / `DBG_CLIENT` / `DBG_URL` / `dbgLastVerdict` / `dbgLastVerdictTick`；
- 全仓库 `src` grep `debug-point|dbgVerdict|heldItemName|DBG_URL|7777` → **0 命中**（仅剩主题色 `0x777777`）；
- `mineRequested` 里原本「插桩变量赋值与功能守卫混写」的 if / else-if 链改成扁平早返回，逐条比对判定条件与顺序一致；
  世界切换分支按要求补回 `return false`（与改前「本轮不派发」一致，下一 tick `level` 已归零可正常继续）；
- 调试服务器停止（7777 不再监听）、`.dbg/trae-debug-log-mining-stall-after-rtp.ndjson` 与 `debug-mining-stall-after-rtp.md` 删除；
- `.\gradlew build --console=plain -q` → **EXIT=0**。

## 8. 遗留

- 本轮与之前数轮改动**均未 commit**。
- 第 78 篇（ESP 使用说明 + `§7` 颜色码字面显示）的实机确认与本次一并完成。
- 同类风险点：`syncEpoch` 只在「实体更换 / tickCount 回退」时触发；若将来出现「换世界但 tickCount 不回退」的场景，
  需要再补一条判据（例如 `level` 引用变更）。
- 「各模块 ESP」开关（第 75/76 篇）本轮未单独验证，功能上仅多一道早退守卫。
