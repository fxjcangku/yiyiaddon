# 235 · 钻石 Seed Target → AutoMiner 正式接入报告

- 项目：`D:/mcaddon/yiyiaddon`（分支 `master`，Minecraft 26.1.2）
- 阶段：高速开发线 A · 235（把已经 VERIFIED 的钻石种子预测正式接入现有自动挖矿）
- 前置：234.1 已通过（本阶段不重新研究 Validation，不改 Predictor / 不改 Worker worldgen / 不改 Validation Policy）
- 结论：**通过**。A~L 十二项实机回归在开发实例连续三轮「全部判定：通过」（收进证据的是最终修订那一轮），并已在正式产物（`build/release/yiyiaddon-1.0-beta2-26.1.2.jar`）上完整复跑一遍「全部判定：通过」；冻结 Worker 对照在正式产物上「全部判定：通过」。

---

## 一、本阶段做了什么（一句话）

给现有自动挖矿加了一层**目标来源抽象**，并新增「种子模式」实现：自动挖矿照旧负责移动 / 寻路 / 秒破 / 矿脉 / 食物 / 背包 / 回家 / 物流 / 安全，**种子只负责回答「下一颗钻石在哪一格」**。没有第二套 AutoMiner，也没有第二套寻路。

---

## 二、硬规则落实（逐条对照）

| # | 规则 | 落实位置与手法 |
|---|---|---|
| 1 | Seed OFF ⇒ 原 AutoMiner 行为 100% 不变 | `settings.seedTargetMode` 为假时 `AutoMinerModule#miningTargetProvider()` 返回 `BlockTypeMiningTargetProvider`，其 `issue()` 就是原来的 `Baritone#mine(矿物类型)`，逐字等价；`tick()` 是空实现；看门狗三档全部沿用旧语义（`progressWatchdogEnabled()=true`） |
| 2 | Seed ON ⇒ 只走 Seed Target 路径，禁止偷偷 fallback | `SeedMiningTargetProvider#issue()` 只会「选预测坐标 → 精确寻路 → 到位交秒破」；任何异常都走 `notReadyReasonCn()` 如实停机，**没有**任何一处会改调 `Baritone#mine(Block)` |
| 3 | 只有 `mayUseForAutomatedMining()==true` 才允许 Seed Target 控制 | `NotReadyReasonCn` 第 4 条硬判 `SeedMiningService#mayUseForAutomatedMining()`；同时 `AutoMinerModule` 启动自检（`AutoMinerModule:698`）与 UI（`MiningSeedPage:414`）读同一函数 |
| 4 | 非 VERIFIED ⇒ fail-closed，不启动种子自动挖矿 | 同上：闸门未开时模块自检直接拦住启动（实机 B 项：`模块是否被启动：false`、`是否偷偷下发过挖掘目标：类型 mine 无 / 精确寻路无`） |
| 5 | SUSPICIOUS 仍关闭，不做假矿判断 | `SeedMiningTargetProvider#rankOf()` 对 `MISSING, SUSPICIOUS` 一律返回 `-1`（本阶段不产出 SUSPICIOUS；万一出现也不作为挖矿目标），未新增任何假矿判据 |
| 6 | 不改 Predictor / Worker worldgen / Validation Policy | 本阶段改动全部落在 `feature/mining/**`、`seed/service`（仅解耦与需求源）、`dev/seedpoc/**`（装置）；`seed/prediction`、`seedworker`、`seed/validation` 的算法与阈值零改动（冻结 Worker 对照在正式产物上仍是原数字） |
| 7 | 测试只进 `.dev-runs/26.1.2/seed/...` | 新增运行配置 `runClientSeedTargetTest` 的 runDir = `.dev-runs/${minecraft_version}/seed/target`，并已加入 `prepareSeedWorkerClientRun` 的收口清单；`run-26.1.2` / `run-26.2` 本次零写入（见第十一节） |

---

## 三、架构

### 3.1 单一抽象：`MiningTargetProvider`

```
com/yiyiaddon/feature/mining/target/
├── MiningTargetProvider.java          ← 唯一抽象（新增）
├── BlockTypeMiningTargetProvider.java ← 普通模式：原 AutoMiner 行为（新增）
└── SeedMiningTargetProvider.java      ← 种子模式：按预测坐标工作（新增）
```

接口（全部与「目标从哪来」有关，与移动/破坏无关）：

| 成员 | 作用 |
|---|---|
| `modeNameCn()` / `scanModeCn()` / `usesBlockTypeScan()` | 只读展示与「本模式是否按矿物类型扫描」 |
| `ready()` / `notReadyReasonCn()` | fail-closed 的唯一落点：不能挖就把中文原因交给状态机播报 |
| `issue(boolean)` / `reissue()` | 下发 / 自愈式重下发 |
| `tick()` | 每刻推进（普通模式空实现；种子模式选目标 → 寻路 → 到位交秒破） |
| `engineActive()` / `exhausted()` | 供状态机看门狗判「引擎还在不在」「这一片是不是真没矿了」 |
| `progressWatchdogEnabled()` | 「长时间没打穿方块」这一档是否启用（种子模式关，见 4.7） |
| `reset()` / `resetSession()` | 离开挖矿态 / 换运行时身份时的清理 |
| `statusCn()` / `lockedTargetOrNull()` | 控制台只读状态；后者供 UI 与回归装置读取「当前锁定的精确坐标」 |

### 3.2 状态机侧的接入点（逐处，全部走提供者）

| 位置 | 行为 |
|---|---|
| `MiningStateMachine:1274` | 起 mine 时 `module.issueMiningTargets(true)`（原来直接 `Baritone#mine`） |
| `MiningStateMachine:1281` | 挖矿态每刻 `module.miningTargetProvider().tick()`（新增，普通模式是空实现） |
| `MiningStateMachine:1442/1447/1454` | 引擎看门狗：`engineActive()` / `ready()`+原因 / `exhausted()` |
| `MiningStateMachine:1550/1557` | 「挖不动」档：`progressWatchdogEnabled()` 与 `engineActive()` |
| `MiningStateMachine:1593/1628` | 脱困重下发 → `module.reissueMiningTargets()` |
| `MiningStateMachine:1127` | 离开挖矿态 → `provider.reset()` |
| `MiningVeinMiner:910` | 连锁收尾恢复 → `module.issueMiningTargets(false)` |
| `AutoMinerModule:1237` | 刷怪笼优先（打掉刷怪笼再挖矿）只在 `usesBlockTypeScan()` 为真时生效 |
| `AutoMinerModule:832/889` | 换服 / 换维度 / 退世界 → `provider.resetSession()` |

### 3.3 「不散落 `if (seedMiningEnabled)`」

全仓库只有一处按模式分流：`AutoMinerModule#miningTargetProvider()`（`settings.seedTargetMode ? seedTargetProvider : normalTargetProvider`，`AutoMinerModule:268-270`）。其余调用点一律面向接口。

### 3.4 UI

`MiningSeedPage` 新增「自动挖矿接入」区（`MiningSeedPage:162-170, 414-416`）：

- 开关「自动挖矿用种子预测坐标」（= `settings.seedTargetMode`，随 `MiningSettings` 持久化，`MiningSettings:81/563/665`）
- 只读行：`挖矿模式` / `扫描方式` / `目标状态`
- 闸门未开时把 `notReadyReasonCn()` 的原因直接显示在该区

---

## 四、Seed Target Provider

### 4.1 输入

| 输入 | 用途 |
|---|---|
| `SeedMiningService#runtimeIdentity()` | 当前运行时身份（worldKey + seed + dimension + MC 版本 + 会话号）；身份一变整批作废 |
| `SeedMiningService#cachedPredictions()` | 成功的 `PredictionResult`（失败结果不进缓存） |
| `SeedMiningService#observationState(pos)` | 该坐标的观察状态（CONFIRMED / UNOBSERVED / MISSING / SUSPICIOUS） |
| `SeedMiningService#coverageRadius()` | 覆盖半径（默认 3，本阶段不改默认） |
| `SeedMiningService#mayUseForAutomatedMining()` | 闸门（见第五节） |
| `ClientLevel#getBlockState` + `isLoaded` | 到位后的**实际方块状态**（唯一真值来源：客户端镜像；绝不主动加载区块） |

### 4.2 只考虑与排除

**只考虑**：当前运行时身份 + 当前 Overworld + `OreType.DIAMOND` + 落在当前覆盖方框内（与覆盖调度同一口径：区块切比雪夫距离 ≤ radius）+ 成功的 `PredictionResult`。

**排除**：`MISSING` / 失效运行时身份（`syncIdentity()` 返回假则整批丢弃）/ 已消费（`consumed`）/ 暂时不可达（`unreachableUntil` 未到解禁刻）。

**允许**：`CONFIRMED` 与 `UNOBSERVED`；`SCHEDULE_SENSITIVE` **不因敏感被排除**（它只是确定性档位，不是假矿）。

### 4.3 Target Selection（第一版：简单稳定）

- 优先级：`CONFIRMED = 0` → `UNOBSERVED = 1`（`MISSING/SUSPICIOUS = -1` 不选），同级按玩家欧氏距离取最近（`selectNext()`）。
- 记账三件套：`current target` / `consumed`（已消费，上限 4096）/ `unreachableUntil`（暂时不可达，冷却 12000 刻）。三者共同保证「不会反复选同一格」「不会在同一个点上死循环」。
- 本阶段不做旅行商/批次规划；选不到就等 10 刻再扫（`NO_TARGET_RESCAN_TICKS`）。

### 4.4 MISSING 行为

- 选中过滤：`rankOf()` 直接跳过 `MISSING`。
- 追中途变 MISSING：`tick()` 第 2 步 `isStillUsable()` 为假 → `consume(pos)` + `abandon(...)` → 立刻换下一颗。
- 实机 F 项证据：`观察为缺失（通过） / 已换目标（通过） / 未反复重选（通过）`；G 项（手动 `/setblock` 空气）：`模块仍在跑（通过） / 换下一颗（通过） / 未重选同一点（通过）`。

### 4.5 UNOBSERVED 行为

- 允许作为**远端导航目标**（否则近处没预测矿时就无法起步）：`rankOf()` 给 1。
- 一旦对应区块真正加载，`tick()` 第 2/4 步**必须由实际 BlockState 重新确认**：`isStillUsable()`（不是钻石就放弃）与 `confirmedDiamond()`（是钻石才交秒破）。未加载时不做任何判断（`isLoaded` 为假直接按预测继续导航）。
- 实机 D 项证据（把玩家挪到 421 格外，目标区块在客户端卸载）：`区块已卸载（通过） / 目标保持（通过） / 精确坐标寻路（通过） / 无类型扫描（通过）`。

### 4.6 目标执行（不让 Baritone 按「钻石 Block 类型」全局搜索）

- 复用现有 `MiningPathing#pathToOre(BlockPos)`（`GoalTwoBlocks` 的精确坐标目标），**不是** `Baritone#mine(Block)`。
- 到位（`isWithinBlockInteractionRange(target, REACH_SLACK=1.0)` 且实际方块是钻石）→ 先 `Baritone#stop()` 收起寻路（同一时刻只允许一个写入者）→ 交 `MiningFastBreakController#mineRequested(..., Direction.UP)` → 矿脉由现有 `MiningVeinMiner` 承接。
- 实机 C 项证据：`目标在预测集内（通过） / 覆盖内（通过） / 无类型扫描（通过）`；E 项：`目标被挖掉（通过） / 秒破通道（通过）`。

### 4.7 失败目标处理（K 口径）

| 情形 | 处理 |
|---|---|
| 寻路下发失败 / 目标进程反复消失 | 最多重发 `MAX_PATH_ISSUES = 3` 次（限速 `PATH_REISSUE_GAP_TICKS = 10` 刻），仍不行 → `markUnreachable` + `abandon`（换下一颗，冷却 10 分钟） |
| 秒破判定「这一格挖不动」 | `MiningFastBreakController#isBlocked` → `markUnreachable` + `abandon` |
| 传送打断寻路（换区 / RTP / 装置换现场） | **不消耗**重发次数：单刻位移 > 32 格视为传送，`pathIssues` 归零（`TELEPORT_RESET_DISTANCE_SQR`）。这是本阶段实机跑出来的修正 —— 修正前一次传送会吃掉 2 次额度，第 3 次就白扔一颗好目标 |
| 「长时间没打穿方块」 | 种子模式关闭该档（`progressWatchdogEnabled()=false`）：导航到远端目标途中本来就不打方块，够不到由上面两条自管 |

---

## 五、闸门（gate）

唯一判据函数 `SeedMiningService#mayUseForAutomatedMining()`（234 定案，未改），在本阶段的三个位置被消费：

1. `SeedMiningTargetProvider#notReadyReasonCn()` → 状态机看门狗与启动自检；
2. `AutoMinerModule` 启动自检（`AutoMinerModule:698`，面板里显示拦住原因）；
3. `MiningSeedPage`（`MiningSeedPage:414`）只读显示。

闸门未开时的完整原因链（顺序即用户最容易修的顺序）：总开关 → 维度（只支持主世界）→ 种子是否填写 → **验证闸门** → 计算器是否异常 → 目标是否选的是钻石矿 → 秒破是否打开。任何一条不满足都**不会**退回普通模式的矿物类型扫描（硬规则 2/4）。

实机 B 项证据：`闸门关（通过） / 自检拦住（通过） / 未下发任何目标（通过） / 运行时仍在预测（通过）`；聊天实证：`§b种子目标§f·种子验证未通过（当前：未验证）§8▸ 不启动种子自动挖矿`。

---

## 六、Renderer 与 Coverage 解耦（本阶段的关键改动）

233 阶段只有 ESP 一个消费者，因此「关掉显示预测钻石」= 清覆盖 / 清预测。235 起自动挖矿是**第二个消费者**，因此改为：

```
Seed Runtime / Coverage
├── Renderer consumer   （settings.renderPrediction：只决定画不画框）
└── AutoMiner consumer  （settings.seedTargetMode + 闸门：决定要不要一直预测下去）
```

实现：`SeedMiningService` 内部以 `wantsPrediction = config.renderPrediction() || autoMiningConsumerDemand()` 作为「要不要继续跑预测」的唯一判据；自动挖矿通过 `SeedMiningService#setAutoMiningDemandSource(BooleanSupplier)`（`AutoMinerModule:255` 注册 `this::wantsSeedRuntime`）把需求接进来。

由此得到三条硬语义：

1. 关 ESP：**只**不画框（渲染快照置空），预测仓库、覆盖调度、目标队列全部原样保留；
2. 关 ESP 后渲染快照也不再每刻重建（`onTick()` 里 `renderer.attached() && renderer.dirty()`；重开时由 `applyRendererAttachment` 标脏并立刻补建一次）；
3. **只有**种子挖矿总开关 OFF，才真正停掉整个 Seed Runtime（停 Worker、清缓存/覆盖/观察/验证）。

实机 H / I 项证据：

```
【判定】H 关 ESP 不影响：ESP 关（通过） / 缓存保留（通过） / 覆盖继续（通过） / 挖矿继续（通过）
【判定】I 重开 ESP 恢复：渲染层恢复（通过） / 挖矿未受影响（通过）
```

（本轮 A~G、K 也全部在「显示预测钻石 = false」下跑完，读数见 T1。）

---

## 七、AutoMiner lifecycle

以下任一发生 → **立即**取消当前 Seed target，且旧坐标、旧记账绝不允许被带到新世界：

| 事件 | 落点 | 行为 |
|---|---|---|
| 改种子 / 关总开关 / 退世界 / 换维度 / 换服 / 会话换代 | `syncIdentity()`（每刻第一步） | 身份不一致：`cancelPath()` + 清 `currentTarget` + 清 `consumed` / `unreachableUntil` + 重置传送基准 |
| 闸门不再 VERIFIED | `ready() = false` | 看门狗分支停机并播报 `notReadyReasonCn()`（fail-closed） |
| 换服 / 换维度 / 退世界（模块侧） | `AutoMinerModule:832/889` → `resetSession()` | 整批作废（比 `reset()` 更彻底） |
| 离开挖矿态（进战斗 / 进食 / 卸货 …） | `MiningStateMachine:1127` → `reset()` | 撤当前目标与寻路，保留「已消费 / 暂时不可达」（回来继续用） |

实机 J 项证据（去下界）：

```
【判定】J 换维度：身份清空（通过） / 闸门关闭（通过） / 目标清空（通过） / 缓存清空（通过） / fail-closed 停机（通过）
【判定】J 返回主世界：身份重建（通过） / 未带旧目标（通过） / 需求源在位（通过）
```

返回主世界后闸门若再次打开，是 234 定案的既定语义（验证锁存绑定运行时身份，换维度会把证据与锁存一起清空并从零重收；同一世界 + 同一种子 + 同一维度再次收满阈值即再次锁存）。装置因此只判「旧目标 / 旧记账绝不被带回来」。

---

## 八、实机回归 A~L（开发实例）

- 装置：`com/yiyiaddon/dev/seedpoc/SeedTargetRegression.java`（29 态装置），运行配置 `runClientSeedTargetTest`，runDir `.dev-runs/26.1.2/seed/target`
- 世界：单人夹具 `seedpoc-known-seed`（建世界前删除重建），覆盖半径 3（出厂默认）
- 指令通道：玩家发包入口（单人存档自带权限）—— `give` / `spreadplayers` / `forceload` / `fill` / `setblock` / `tp`
- 真值来源：集成服务端 `ServerLevel#getBlockState`（不读客户端镜像）
- 结果：**连续三轮「全部判定：通过」**（收进证据的是最终修订那一轮，T1）

| 项 | 用例 | 判定 |
|---|---|---|
| A | 种子关闭时的原自动挖矿回归 | 普通模式 / 类型扫描 mine 下发 / 种子运行时零牵动 —— 全通过 |
| B | 种子目标模式打开但闸门未开 | 闸门关 / 自检拦住 / 未下发任何目标 / 运行时仍在预测 —— 全通过 |
| — | 闸门打开（已验证） | 通过（用时 11 秒；有效证据单元 8 / 8 个目标区块 / 解释比例 100%，锁存已锁定） |
| C | 正确种子已验证后选定真实 Diamond Seed Target | 种子模式 / 目标在预测集内 / 覆盖内 / 无类型扫描 —— 全通过 |
| D | 目标未加载仍能导航（把玩家挪到 421 格外） | 区块已卸载 / 目标保持 / 精确坐标寻路 / 无类型扫描 —— 全通过 |
| E | 加载后实际是钻石 ⇒ 走现有挖矿链 | 目标被挖掉 / 秒破通道 —— 全通过 |
| L | 挖掉一颗换下一颗 | 通过（新目标按预测坐标推进） |
| F | 加载后 MISSING ⇒ 立刻放弃选下一颗 | 观察为缺失 / 已换目标 / 未反复重选 —— 全通过 |
| G | 手动 `/setblock` 空气 ⇒ 不卡死 | 模块仍在跑 / 换下一颗 / 未重选同一点 —— 全通过 |
| K | Baritone path fail ⇒ 不无限重试 | 重试有上界 / 已换目标 / 未重选同一点 —— 全通过 |
| H | 关闭 ESP ⇒ AutoMiner 继续 | ESP 关 / 缓存保留 / 覆盖继续 / 挖矿继续 —— 全通过 |
| I | 重开 ESP ⇒ Renderer 恢复且不影响挖矿 | 渲染层恢复 / 挖矿未受影响 —— 全通过 |
| J | 换维度 / 返回主世界 | 见第七节 —— 全通过 |

### 8.1 实机挖矿证据（E / L，逐字）

```
[22:23:x] 种子挖矿｜自动挖矿：选定目标 (-23,-20,99)，已消费 0 颗 / 暂时不可达 0 颗
[22:23:x] 种子挖矿｜自动挖矿：寻路前往 (-23,-20,99)（第 1 次）
（装置强制加载该区块 → 探真值 minecraft:deepslate_diamond_ore → 挖 3×3×3 空气口袋并把矿原样放回 → tp 玩家站到矿上方）
【判定】E 加载后实际钻石走现有挖矿链：目标被挖掉（通过） / 秒破通道（通过）
【判定】L 挖掉一颗换下一颗：通过（新目标 (47,-11,-329)）
```

E 的几何是真实几何：先 `fill` 出 3×3×3 空气口袋、再 `setblock` 把这颗矿原样放回、最后 `tp` 到矿正上方 —— 所以秒破的方向（`Direction.UP`）与交互距离都不是构造出来的假值。装置用服务端真值确认挖掉，不读客户端镜像。

### 8.2 装置自身踩过的两个坑（已修，写下来免得下次重踩）

1. **「传送未生效」死循环**：状态机判「传送生效」看的是**单刻位置跳变**，而它自己发指令后会被 `ServerCommandRunner` 阻塞若干刻等落点稳定；一次瞬移正好落在阻塞里时，那一跳就看不见了。装置的解法是把它拆开：先等模块的指令窗口结束，再由装置自己补一次真实野外传送（`advanceIntoMining`，一轮只补一次）。
2. **窗口没焦点 ⇒ 原版弹「游戏菜单」⇒ 单人服务端暂停**：整条挖矿链（服务端破坏、区块发送、指令执行）全部停摆，表现为「目标真值是对的、秒破却始终没被激活」。装置现在每刻把 `PauseScreen` 关掉（正式产物冒烟实测踩到过，见 T4 前一版记录）。

---

## 九、生产 Jar smoke（正式产物）

产物：`build/release/yiyiaddon-1.0-beta2-26.1.2.jar`（45.2 MB，ProGuard 混淆 + 字符串/控制流加固）
脚本：`gradle/production-smoke.ps1`（独立 `gameDir`，只放 `fabric-api` + 本模组）

> 调用要点：`-SeedPocJvmArgs` 是 `[string[]]`。从非交互 shell 传参时必须给**单个空格分隔的字符串**，否则会被拍扁成一条 `-Da=1,-Db=1`，属性对不上 `"1"`，PoC 入口直接返回、客户端停在主界面什么都不做。

| 冒烟 | 场景目录 | 结论 |
|---|---|---|
| 冻结 Worker 对照 | `build/production-smoke/parity2` | **通过**（`客户端退出：退出码 0，耗时 48s`；`Worker PID=28704` 起于 t=12s、消失于 t=47s；`退出后残留 Worker 进程：0`；`命令行无开发目录`）；装置报告 `全部判定：通过`（T5/T6） |
| 235 目标接入回归 | `build/production-smoke/target` | **通过**（`退出码 0，耗时 81s`；Worker RSS 峰值 867 MB；无残留）；装置报告 `全部判定：通过` —— A~L 十二项与开发实例逐项一致（T3/T4） |

正式产物上的 A~L 判定（逐字，T4）：

```
【判定】A 种子关闭原自动挖矿回归：普通模式（通过） / 类型扫描 mine 下发（通过） / 种子运行时零牵动（通过）
【判定】B 未验证不得开始：闸门关（通过） / 自检拦住（通过） / 未下发任何目标（通过） / 运行时仍在预测（通过）
【判定】闸门打开（已验证）：通过（用时 10 秒）
【判定】C 选定真实种子目标：种子模式（通过） / 目标在预测集内（通过） / 覆盖内（通过） / 无类型扫描（通过）
【判定】D 目标未加载仍能导航：区块已卸载（通过） / 目标保持（通过） / 精确坐标寻路（通过） / 无类型扫描（通过）
【判定】E 加载后实际钻石走现有挖矿链：目标被挖掉（通过） / 秒破通道（通过）
【判定】L 挖掉一颗换下一颗：通过（新目标 (47,-11,-329)）
【判定】F MISSING 立刻放弃换颗：观察为缺失（通过） / 已换目标（通过） / 未反复重选（通过）
【判定】G 手动置空气不卡死：模块仍在跑（通过） / 换下一颗（通过） / 未重选同一点（通过）
【判定】K 寻路失败不无限重试：重试有上界（通过） / 已换目标（通过） / 未重选同一点（通过）
【判定】H 关 ESP 不影响：ESP 关（通过） / 缓存保留（通过） / 覆盖继续（通过） / 挖矿继续（通过）
【判定】I 重开 ESP 恢复：渲染层恢复（通过） / 挖矿未受影响（通过）
【判定】J 换维度：身份清空（通过） / 闸门关闭（通过） / 目标清空（通过） / 缓存清空（通过） / fail-closed 停机（通过）
【判定】J 返回主世界：身份重建（通过） / 未带旧目标（通过） / 需求源在位（通过）
全部判定：通过
```

### 9.1 生产冒烟的一次性假警报（留档）

第一次生产冻结对照跑在**沿用旧状态的实例目录**上，装置报 `不一致 45 处 · 写入者不同 … UNATTRIBUTED vs UNATTRIBUTED`，`全部判定：存在不通过项`。原因是那个实例目录里残留着「自动挖矿已启用」的配置，进世界后自动挖矿的需求源先把覆盖跑了一遍 —— Worker 的**区块生成历史**因此与 Oracle 不同，`originViewer`（写入者区块）在冷启动那一颗目标上逐条不同。换到全新实例目录复跑即 `逐项比较：完全一致`、`全部判定：通过`（T6）。冻结数字（候选合计 / 敏感 / 确定性 / Host Query）两次都是通过，差异只在「写入者」这一项上，属于**环境历史差异**，不是 235 引入的回归（本阶段没碰 Predictor / Worker）。

---

## 十、冻结回归（最小冻结 gate）

在正式产物上重跑（T6，`build/production-smoke/parity2`）：

| 冻结项 | 期望 | 实测 | 判定 |
|---|---|---|---|
| Seed 20260922 十目标候选合计 | 243 | 243 | 通过 |
| Seed 12345 四目标候选合计 | 110 | 110 | 通过 |
| 12345 (0,0) | 31 | 31 | 通过 |
| 12345 (-1,-1) | 29 / 敏感 7 | 29 / 敏感 7 | 通过 |
| 12345 (-25,17) | 24 | 24 | 通过 |
| 12345 (120,-130) | 26 | 26 | 通过 |
| Seed 2 (-400,380) | 23 / 敏感 1 | 23 / 敏感 1 | 通过 |
| 争议格 | SCHEDULE_SENSITIVE | 争议格（通过） | 通过 |
| Worker 预测主链宿主 ChunkMap 查询 | 0 | 0 | 通过 |
| **全部判定** | — | **通过** | — |

开发实例（`.dev-runs/26.1.2/seed/worker-parity`，T2）同样是 `全部判定：通过`。

---

## 十一、零写入与构建校验

- **`run-26.1.2` / `run-26.2` 零写入**：核对目录内最新写入时间均停留在本次自动化运行之前（用户 19:50 那次手动运行），本阶段所有自动化只写 `.dev-runs/26.1.2/seed/{target,worker-parity}` 与 `build/production-smoke/**`。
- **构建**：`.\gradlew.bat build obfuscatedJar verifyObfuscatedJar` → `BUILD SUCCESSFUL`；类改名 1369；类加载及成员校验 1410 项；变换行为对照 64448 项（8 组随机布局）；密文篡改认证拒绝 = 通过；最终发布包强校验通过。

---

## 十二、当前限制（如实记录）

1. **只接钻石**：`OreType.DIAMOND` 是唯一被选中的矿物；其它矿物（含深层变种以外的）本阶段不扩。
2. **只支持主世界**：下界 / 末地由闸门直接判 `dimensionSupported()=false` 而 fail-closed，不做任何降级。
3. **半径默认仍是 3**：本阶段不因为 234.1 的 Radius6 通过就改默认（性能研究不在本阶段范围）。
4. **不做批次规划**：目标选择是「CONFIRMED 优先 → UNOBSERVED 次之 → 同级最近」，没有旅行商式最优路径。
5. **`SUSPICIOUS` 仍关闭**：不产出、也不作为挖矿目标；假矿判断未实现（按拍板）。
6. **冷启动那一颗目标的「写入者」项与 Oracle 不保证逐条一致**：它取决于本进程此前的区块生成历史（见 9.1）。冻结 gate 的数字不受影响。
7. **秒破是硬前提**：`settings.fastBreak` 关闭时种子模式直接判不可用（种子模式只负责给坐标，破坏交给现有秒破链）。
8. **装置窗口必须能自己关掉「游戏菜单」**：否则单人服务端暂停，全链停摆（装置已自动关；纯人工运行时请让游戏窗口保持前台）。

---

## 十三、证据索引

| 编号 | 文件 | 说明 |
|---|---|---|
| T1 | `235-证据/T1-开发期目标接入回归-装置报告.txt` | 开发实例 A~L 十二项装置报告（全部判定：通过） |
| T2 | `235-证据/T2-开发期冻结Worker对照-装置报告.txt` | 开发实例冻结 Worker 对照（全部判定：通过） |
| T3 | `235-证据/T3-生产冒烟目标接入-冒烟判决.txt` | 正式产物冒烟判决（目标接入） |
| T4 | `235-证据/T4-生产冒烟目标接入-装置报告.txt` | 正式产物上的 A~L 装置报告（全部判定：通过） |
| T5 | `235-证据/T5-生产冒烟冻结Worker对照-冒烟判决.txt` | 正式产物冒烟判决（冻结对照） |
| T6 | `235-证据/T6-生产冒烟冻结Worker对照-装置报告.txt` | 正式产物冻结对照报告（全部判定：通过） |
| T7/T8 | `235-证据/T7-…-Worker命令行.txt` / `T8-…-Worker命令行.txt` | 两轮生产冒烟的 Worker 命令行（可核对「无开发目录」） |

原始日志与装置输出（未收进报告目录，供复查）：`.dev-runs/26.1.2/seed/target/**`、`.dev-runs/26.1.2/seed/worker-parity/**`、`build/production-smoke/{parity2,target}/**`。

---

## 十四、本阶段改动清单（代码）

| 文件 | 改动 |
|---|---|
| `feature/mining/target/MiningTargetProvider.java` | 新增：目标来源抽象 |
| `feature/mining/target/BlockTypeMiningTargetProvider.java` | 新增：普通模式（= 原 AutoMiner 行为，逐字等价） |
| `feature/mining/target/SeedMiningTargetProvider.java` | 新增：种子模式（选目标 / 精确寻路 / 到位交秒破 / 记账 / 失败上界 / 传送识别） |
| `feature/mining/fsm/MiningStateMachine.java` | 所有 mine 下发点改走提供者；新增 `armOwnTeleportGraceForDev()`（**开发期回归装置专用**的传送声明入口，正式流程不调用） |
| `feature/mining/AutoMinerModule.java` | `miningTargetProvider()` 分流；`issueMiningTargets` / `reissueMiningTargets` 转提供者；注册自动挖矿需求源；启动自检与面板走提供者原因；刷怪笼优先只在类型扫描模式生效 |
| `feature/mining/vein/MiningVeinMiner.java` | 连锁收尾恢复改走提供者 |
| `feature/mining/config/MiningSettings.java` | 新增 `seedTargetMode`（含持久化） |
| `feature/mining/ui/console/MiningSeedPage.java` | 新增「自动挖矿接入」区（开关 + 三个只读状态 + 未就绪原因） |
| `seed/service/SeedMiningService.java` | Renderer / Coverage 解耦（`wantsPrediction` 双消费者）；自动挖矿需求源接口；关 ESP 后不再每刻重建渲染快照 |
| `dev/seedpoc/SeedTargetRegression.java` | 新增：A~L 十二项实机回归装置 |
| `dev/seedpoc/SeedPocFlags.java` / `SeedPocEntry.java` | 新增 `yiyiaddon.seedpoc.target` / `.targetSeed` / `.targetRadius` 与入口分支 |
| `build.gradle` | 新增运行配置 `clientSeedTargetTest`；`prepareSeedWorkerClientRun` 收口新增 `.dev-runs/<MC 版本>/seed/target` |

---

## 十五、判定

- A~L 十二项：**开发实例连续三轮 + 正式产物一轮，全部通过**
- 冻结 Worker 对照：**开发实例与正式产物均「全部判定：通过」**（243 / 110 / 29·敏感7 / 23·敏感1 / 争议格 / Host Query 0）
- 生产 Jar smoke：**两轮均「结论：通过」**（Worker 启动、命令行无开发目录、退出后无残留）
- 构建与混淆校验：**BUILD SUCCESSFUL + 强校验通过**
- `run-26.1.2` / `run-26.2`：**零写入**
- 硬规则 1~7：**逐条落实**（第二节）

**235 通过。本阶段到此停止**：不扩其它矿物、不做下界、不碰 26.2、不改 Predictor / Worker worldgen / Validation Policy。
