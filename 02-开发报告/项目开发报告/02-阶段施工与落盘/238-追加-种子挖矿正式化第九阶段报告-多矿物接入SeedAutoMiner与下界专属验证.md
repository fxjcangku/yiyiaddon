# 238 · 种子挖矿正式化第九阶段报告 —— 多矿物正式接入 Seed AutoMiner 与下界专属验证

日期：2026-09-24
版本线：26.1.2（`D:/mcv/wt-236`，分支 `feat/236-seed-ore-engine`）+ 26.2（`D:/mcv/wt-262`，分支 `port/26.2`）
状态：实现完成 + 两版本实机回归通过；**未提交**（按第 233 条，等「同步仓库」口令）

---

## 一、本轮要解决的问题

237 收口时，「种子目标模式」只驱动**钻石**一种矿：`SeedOreRegistry` 里 11 条矿物定义只有钻石
`autoMinerEligible = true`。237 已经证明 11 种矿物的 Prediction / Worker↔Oracle / Observation /
ESP / Repository 与维度隔离在两条版本线上全部通过，因此本轮把「预测」之外的最后一段 ——
**自动挖矿的目标来源** —— 正式放开到全部矿物。

用户在动工前给出了 14 条硬要求（不许为每种矿复制一套 AutoMiner、只能消费当前选中的 oreType、
目标只能来自 Seed Prediction、加载后必须查实际 BlockState、235 行为全部复用、主世界 8 种全接入、
下界不许粗暴打开 fail-closed、必须先建下界专属验证、Overworld VERIFIED ≠ Nether VERIFIED、
只消费对应矿物的候选、多选要有稳定优先级且缓存不互相污染、关掉种子目标模式后原行为完全不变、
两版本同步、可见假矿一律无视）。

### 动手前取到的三处卡点（取证口径：文件 + 方法 + 行）

| # | 卡点 | 位置 | 事实 |
| --- | --- | --- | --- |
| 1 | 静态资格只有钻石 | `seed/ore/SeedOreRegistry.java` 的 11 条 `register(...)` | 10 条为 `false`，注释写明「其余矿物尚未建立候选↔真实实机对照证据」 |
| 2 | 服务层把维度写死在主世界 | `seed/service/SeedMiningService.java` `mayUseForAutomatedMining()` / `(OreType)` | 前者要求 `dimensionProfile() == OVERWORLD`；下界恒 `false` |
| 3 | 下界没有需求源 → 下界永远无法自证 | `feature/mining/AutoMinerModule.java` `wantsSeedRuntime()` | `return "minecraft:overworld".equals(WorldIdentity.dimension())`，进下界就不再要预测，观察样本无从产生 |

另外确认了「目标源」与「缓存分区」这两件事**本来就是通用的**，不需要泛化：
`SeedMiningTargetProvider.selectNext()` 只遍历 `service.cachedPredictions()` 并过滤
`ore.oreType() != oreType`（不读真实世界、不扫描可见方块）；缓存键是
`TargetKey(维度, 矿物, 区块)`。这两点是本轮「不改目标源、不复制 AutoMiner」的底气。

---

## 二、口径定案（238）

1. **一次只追一种** —— 与自动挖矿设置口径一致（设置里也是「主世界一种矿 / 下界一种矿」）。
   追哪种矿 = 种子页勾选 ∩ 本维度受支持，按**维度声明序**取第一种：
   主世界 `钻石 → 红石 → 青金石 → 金 → 铁 → 铜 → 煤 → 绿宝石`，下界 `远古残骸 → 下界石英 → 下界金`。
   顺序稳定（`SeedMiningConfig.selectedOres()` 按 `OreType.values()` 归一化存储，
   `effectiveOres(supported)` 按维度声明序求交），不会这一次挖这个、下一次挖那个。
2. **深层变种算同一种**：`SeedOreDefinition.blocks()` 里带上 `deepslate_*` 形态，不需要分别勾选。
3. **静态资格全开**：11 条定义一律 `autoMinerEligible = true`。安全性不靠这张静态表，
   靠下面两道**运行期**门。
4. **两道运行期门**（`SeedMiningService.automatedMiningBlockReasonCn(OreType)` 是唯一判据与文案来源）：
   - **种子验证**：当前会话必须「已验证」（证据绑定 世界 / 种子 / 维度 / 版本 / 会话）；
   - **证据覆盖该矿物**：本次会话的验证证据里必须出现过**正要追的那种矿**
     （`validation.evidenceOreTypes().contains(oreType)`）—— 「只算过钻石的会话不能给红石背书」。
5. **下界专属验证**（`netherValidationEstablished()`）：下界放行的唯一依据是
   `证据维度 == minecraft:the_nether 且 已验证`，再叠加第 4 条的证据覆盖。
   主世界已验证**绝不**等于下界已验证（身份绑定让换维度即清空）。
   编译期开关 `NETHER_AUTOMINER_ENABLED` 保留为「是否允许下界进入种子自动挖矿」的单一开关点，
   但它本身不是放行条件 —— 刚进下界必然是「未验证」，闸门仍关。
6. **可见假矿一律无视**：目标源仍然只有 Seed Prediction（`usesBlockTypeScan() == false`），
   本轮没有、也不允许引入任何「按可见方块扫一遍」的路径。

---

## 三、14 条要求 → 落地对照

| # | 要求 | 落地 |
| --- | --- | --- |
| 1 | 不为每种矿复制 AutoMiner | 未复制任何模块：改的是资格表 + 服务层门 + 目标提供者的取矿点 |
| 2 | 泛化 `SeedMiningTargetProvider`，消费当前选中的 oreType | 提供者本来就按 `service.autoMiningTargetOre()` 取矿；本轮把资格、文案、判据统一到服务层一处 |
| 3 | 目标只来自 Seed Prediction | `selectNext()` 只读 `cachedPredictions()`；`usesBlockTypeScan=false`，无任何回退路径 |
| 4 | 加载后必须查实际 BlockState | `isStillUsable()` / `confirmedTargetOre()` → `matchesTargetOre(state)`（按被追矿物的定义，fail-closed） |
| 5 | MISSING / AIR / 寻路失败 / consumed / cooldown 全复用 | 这三处本轮一行未改；A~L 的 F / G / K / L 四条用例两版本两矿物全部通过 |
| 6 | 主世界 8 种全接入 | 资格表全开 + 服务层去掉维度写死；实机跑了「钻石」「煤」两轮完整 A~L，其余 6 种共享同一代码路径且矩阵已证候选↔真实逐格一致 |
| 7 | 下界不许粗暴打开 fail-closed | `NETHER_AUTOMINER_ENABLED=true` **但**放行改由 `netherValidationEstablished()` 决定；刚进下界仍被拦 |
| 8 | 先建 Nether-specific Validation / gate | 同上；实机在真实专用服务器的下界会话里建立了下界专属验证，并按矿物逐项放行（见第六节 T5/T10） |
| 9 | Overworld VERIFIED ≠ Nether VERIFIED | 身份绑定（世界/种子/维度/会话）本来就隔离；另加显式的证据维度判定与专门文案 |
| 10 | 只消费对应矿物的候选 | `selectNext()` 过滤 `ore.oreType() != oreType`；缓存键含矿物 |
| 11 | 多选要有稳定优先级、缓存不互相污染 | 优先级 = 维度声明序（见第二节第 1 条）；缓存按 `TargetKey(维度, 矿物, 区块)` 分区，矩阵装置的「同区块并存多矿物 / 维度不混入 / ESP 条目一一对应」持续通过 |
| 12 | 关掉种子目标模式后原行为不变 | A 项两版本两矿物均「普通模式 + 类型扫描 mine 下发 + 种子运行时零牵动」通过；普通提供者未改 |
| 13 | 26.1.2 与 26.2 同步 | 26.2 侧同口径落地并编译 + 四类实机全部通过（第七节） |
| 14 | 可见假矿不成为目标 | 同第 3 条；C / D 两项持续断言「无类型扫描」 |

---

## 四、代码改动清单

26.1.2（14 文件，+1033 / −143；26.2 同口径 14 文件，+1030 / −141）

| 文件 | 改了什么 |
| --- | --- |
| `seed/ore/SeedOreRegistry.java` | 11 条定义资格全开；`autoMinerEligible` 的 javadoc 重写为「静态资格 = 本维度受支持」，并写清运行期两道门 |
| `seed/service/SeedMiningService.java` | `NETHER_AUTOMINER_ENABLED` → true（注明它不是放行条件）；`mayUseForAutomatedMining()` 去掉维度写死；新增 `automatedMiningBlockReasonCn(OreType)`（四道门 + 唯一文案）、`netherValidationEstablished()`、`validationEvidenceOres()`、私有 `dimensionAllowsAutomatedMining()`；`netherAutoMiningAllowedCn()` 文案改为如实报告「专属验证是否建立」 |
| `seed/validation/SeedValidationService.java` | 注释改口径（下界不再恒 false，改由「证据只对自己维度有效」这条护栏放行） |
| `feature/mining/target/SeedMiningTargetProvider.java` | `notReadyReasonCn()` 改为「先取被追矿物 → 再问服务层要原因」；删掉旧文案「所选矿物尚未开放自动挖矿」；类注释写明「一次一种 + 不读真实世界」 |
| `feature/mining/AutoMinerModule.java` | `wantsSeedRuntime()` 从「只在主世界要预测」改为「当前维度受支持就要」——下界要自证就必须先有下界样本；播报改为动态矿物名（新增 `seedTargetOreNameCn()`）；注释同步 |
| `feature/mining/config/MiningSettings.java` | `seedTargetMode` 的说明与前置条件按新口径重写（一次一种、证据覆盖、下界另需专属验证） |
| `feature/mining/ui/console/MiningSeedPage.java` | 「使用种子目标」说明泛化；新增行「当前追的矿物」「验证证据覆盖」；「验证闸门」行改为按被追矿物给出放行/拦下原因 |
| `feature/mining/fsm/MiningStateMachine.java` | 目标下发处注释同步（可追 11 种，一次一种） |
| `dev/seedpoc/SeedPocFlags.java` | 新增 `targetRegressionOre()`（`-Dyiyiaddon.seedpoc.target.ore`，默认 DIAMOND） |
| `dev/seedpoc/SeedTargetRegression.java` | A~L 装置按矿物参数化：锚点按矿物换算（时运模式存产物 id）、`isPredictedTarget` / `isTargetOreId` 改走被追矿物与官方定义、背包探针按目标产物计数、报告文件名带矿物；非主世界矿物如实报「不适用」 |
| `dev/seedpoc/SeedOreMatrixRegression.java` | 下界闸门用例重写为 238 口径断言：静态资格全开 + 逐种矿物放行 ==「下界专属验证 ∧ 证据覆盖该矿」+ 拦下原因不含维度话术 + 主世界证据未参与下界 |
| `dev/seedpoc/NetherMultiplayerRegression.java` | 同上（真实多人环境下同一组断言）；**追加「八之1~3、下界实际挖掘」三种矿物逐个真挖掉**，为此补 `carveSafeRoom`（封壳房间）+ `forceloadAdd/forceloadRemove`（方块坐标口径）+ 维度守卫 + 创造模式兜底 + 「前往野外」由装置补一跳（见第十一节） |
| `build.gradle` | 新增 `-PseedTargetOre` → `-Dyiyiaddon.seedpoc.target.ore` 透传；⑭ 用法注释补充 |
| `gradle/nether-mp.ps1` | 起客户端前先删上一轮的装置报告：否则「本轮没写出报告」会被读成上一轮的 PASS（实测踩过） |

---

## 五、明确「不做」的事

- 不改 234.1 Validation Policy 的任何阈值与判据（本轮只是把「证据覆盖哪种矿物」用在消费者侧）。
- 不给每种矿复制一份 AutoMiner / 目标提供者（第 1 条要求）。
- 不引入任何「可见方块扫描」作为目标来源（第 3 / 14 条要求）。
- 不为下界另写一套预测或验证算法：下界用的仍是同一套流程，只是证据按维度各自持有。
- 不动夹具世界之外的任何运行目录；两条版本线的 `run-26.1.2` / `run-26.2` 全程未写。

---

## 六、实机验收（26.1.2）

| 装置 | 命令 | 结论 | 关键读数 |
| --- | --- | --- | --- |
| 目标接入 A~L（钻石） | `runClientSeedTargetTest` | **全部判定：通过** | A 普通模式回归 / B 未验证不得开始 / C~L 十二项全通过 |
| 目标接入 A~L（煤） | `runClientSeedTargetTest -PseedTargetOre=COAL` | **全部判定：通过** | E 项实测「加载后实际**煤矿**走现有挖矿链：目标被挖掉（通过）/ 秒破通道（通过）」；L「挖掉一颗换下一颗」通过 |
| 多矿物矩阵 | `runClientSeedOreMatrixTest` | **全部判定：通过** | 主世界 8 + 下界 3 逐项 Worker↔Oracle 一致、候选↔真实命中无错报；**238 判定**：静态资格全开 3 种（通过）/ 逐种放行 =「专属验证 ∧ 证据覆盖」（通过）/ 拦下原因不含维度话术（通过）/ 主世界证据未参与下界（通过） |
| 下界真正多人 | `gradle\nether-mp.ps1`（端口 25865，专用服务器不加载本体） | 装置 **全部判定：通过**、驱动器 **判决：通过** | 下界专属验证 **true**；证据覆盖 **远古残骸 下界石英 下界金**；证据维度 `minecraft:the_nether`；被追矿物 `ANCIENT_DEBRIS` 的闸门原因为**空**（放行）；其余 237 项（基线 45 / 切换清空 / 远端 3 候选 / 观察 552 / Host Query 0 / 孤儿 0）逐项通过；**下界三种矿物逐个真挖掉**（见第十一节与 T5/T12） |
| 下界真正多人 · 实际挖矿 | 同上 | 三种各 **候选命中（通过）/ 目标来自候选（通过）/ 真值被挖掉（通过）/ 换下一颗（通过）** | 远古残骸 `→ air` 后换下一颗 `-67,20,63`；下界石英 `→ air` 后换下一颗 `-39,78,64`；下界金 `→ air` 后换下一颗 `-47,101,76`；候选数 1 / 70 / 48 |

`build`（`gradlew build`）EXIT=0。

---

## 七、实机验收（26.2）

| 装置 | 命令 | 结论 | 关键读数 |
| --- | --- | --- | --- |
| 多矿物矩阵 | `runClientSeedOreMatrixTest` | **全部判定：通过** | 238 判定四项全通过；下界口径读数与 26.1.2 同形 |
| 目标接入 A~L（钻石） | `runClientSeedTargetTest` | **全部判定：通过** | 十二项逐条通过（含 J 换维度 fail-closed 停机） |
| 目标接入 A~L（煤） | `runClientSeedTargetTest -PseedTargetOre=COAL` | **全部判定：通过** | E 项实测「加载后实际**煤矿**…挖掉 + 秒破通道」通过 |
| 下界真正多人 | `gradle\nether-mp.ps1` | 装置 **全部判定：通过**、驱动器 **判决：通过** | 下界专属验证 true、三种下界矿全被证据覆盖、被追矿物闸门原因为空（放行）；孤儿 0；**下界三种矿物逐个真挖掉**（远古残骸 → air 换 `-32,10,70` / 下界石英 → air 换 `-39,78,64` / 下界金 → air 换 `-47,101,76`，四项判定各全通过，见 T10/T13） |

`build`（`gradlew build`）EXIT=0。

### 26.2 语义移植口径（第 237 条）

- 改动集不含任何版本相关 API（无 `screen` / `gui`、无 BlockTags / BlockItemTags、无 `DedicatedServer`
  构造器、无 `DedicatedServerProperties` 字段），因此允许按「同一语义逐处落地」的方式移植；
  移植后逐条复核了 diff 中**没有**任何被删的 26.2 专有写法（用 `git diff HEAD` 过滤
  `gui.screen` / `setScreen` / `BlockItemTags` / `BlockTags` / `NotificationManager` 全部为空），
  并再跑 `compileJava` 与 `build` 双通过。
- `SeedOreRegistry` 一处补丁冲突（26.2 侧 237 期已把源码行号改成散文式出处）按**本线文本**解冲突，
  只把 10 处 `false` 改成 `true`；解完 `true` 11 / `false` 0，238 注释与 javadoc 均在。

---

## 八、未覆盖与诚实标注

1. **主世界其余 6 种矿物（红石 / 青金石 / 金 / 铁 / 铜 / 绿宝石）没有逐个跑 A~L**：
   它们与「钻石 / 煤」走**同一条**目标提供者与状态机代码路径（矿物只作为参数），
   且矩阵装置已经对它们逐个取了「候选↔真实逐格一致 + 观察 + ESP + 键隔离」证据。
   要逐个再跑 A~L，命令现成：`gradlew runClientSeedTargetTest -PseedTargetOre=REDSTONE`（同理换其它）。
2. **下界「实际挖掉」用例的取证边界（第十一节）**：装置在候选处先读真值（`候选命中` 就是这一步的读数），
   再把该格周围封成一间石壳房间、把那一颗原样 `setblock` 放回，然后启动探索链。
   也就是说被挖掉的那一格是**装置放回的同一块方块**，而它是不是「预测命中的那一格」
   由放回之前的那次真值读数证明（三个矿物的期望值与实读值逐字相同）。这是刻意的：
   否则秒破的方向、交互距离、以及「挖完换下一颗」都会被原始地形的岩浆 / 落体 / 未加载噪声淹没。
3. **本轮没有重跑 100+ Mod 真实用户环境 smoke**：改动面只在种子挖矿的目标源与闸门，
   夹具世界已覆盖；真实 Mod 环境结论沿用 237（103 jar / 295 mods / Iris+Sodium / 0 ERROR / 0 孤儿）。
4. 矩阵 / 多人装置里出现的文案「下界专属验证尚未建立（当前证据维度：minecraft:the_nether）」
   指「下界证据已开始积累但尚未达到『已验证』」，读数上方同时打印了
   `下界专属验证：false` 与 `证据维度`，两者合读即为准确含义。

---

## 九、证据索引（`02-阶段施工与落盘/238-证据/`）

| 文件 | 出处 |
| --- | --- |
| T1-2612-目标接入A到L-钻石.txt | `wt-236/.dev-runs/26.1.2/seed/target/seedpoc-目标接入回归.txt` |
| T2-2612-目标接入A到L-煤.txt | `wt-236/.dev-runs/26.1.2/seed/target/seedpoc-目标接入回归-coal.txt` |
| T3-2612-多矿物矩阵.txt | `wt-236/.dev-runs/26.1.2/seed/ore-matrix/seedpoc-236-矿物矩阵.txt` |
| T5-2612-下界多人装置.txt | `wt-236/.dev-runs/26.1.2/seed/nether-mp/client/seedpoc-237-下界多人验收.txt`（含「八之1~3、下界实际挖掘」三节） |
| T6-2612-下界多人判决.txt | `wt-236/.dev-runs/26.1.2/seed/nether-mp/client/nether-mp-judgement.txt` |
| T7-262-多矿物矩阵.txt | `wt-262/.dev-runs/26.2/seed/ore-matrix/seedpoc-236-矿物矩阵.txt` |
| T8-262-目标接入A到L-钻石.txt | `wt-262/.dev-runs/26.2/seed/target/seedpoc-目标接入回归.txt` |
| T9-262-目标接入A到L-煤.txt | `wt-262/.dev-runs/26.2/seed/target/seedpoc-目标接入回归-coal.txt` |
| T10-262-下界多人装置.txt | `wt-262/.dev-runs/26.2/seed/nether-mp/client/seedpoc-237-下界多人验收.txt`（含「八之1~3、下界实际挖掘」三节） |
| T11-262-下界多人判决.txt | `wt-262/.dev-runs/26.2/seed/nether-mp/client/nether-mp-judgement.txt` |
| T12-2612-下界三矿实际挖掉-驱动器.txt | `wt-236/build/tmp/238-nether-mp-mine-r8.txt`（驱动器 stdout：装置判定 + 残留 Worker 0 + 判决） |
| T13-262-下界三矿实际挖掉-驱动器.txt | `wt-262/build/tmp/238-nether-mp-mine-262.txt`（同上，26.2 线） |

---

## 十、交付物状态

- 两条版本线的工作区改动**未提交**（第 233 条：等用户说「同步仓库」）。
- 玩家可见口径已同步到 `07-bug记录/1.0-beta3.md`（「本版支持的矿物」一节按 238 重写：
  11 种全部可挖、一次一种、下界需先建专属验证）。
- Diamond frozen 数字（243 / 110 / 29 / Seed2 23·1·22·0 / 争议格 SCHEDULE_SENSITIVE / Host Query 0）
  本轮未触碰相关代码路径，矩阵与多人装置的对应读数与 237 一致。

---

## 十一、追加验收：下界三种矿物「实际挖掉」

237 的下界多人装置只证到闸门（fail-closed）；238 把下界放开之后，「放行」不等于「真能挖」。
本节补的正是这条链：三种下界矿物逐个走完
**候选命中 → 提供者锁定该候选 → 秒破把真实方块变成 air → 换下一颗**，两线各一遍（T5/T10、T12/T13）。

### 装置侧为此必须解决的四处问题（都不是产品代码的问题，是取证现场的问题）

| # | 现象（实测日志） | 原因 | 处置 |
| --- | --- | --- | --- |
| 1 | `That position is not loaded` × N，房间一个没建成，人最后被 tp 进原始地形（下界那一层就是岩浆湖） | **`/forceload add` 收的是方块坐标，不是区块号**：传 `(-3, 4)` 被折算成区块 `(-1, 0)`，目标区块始终没加载 | 新增 `forceloadAdd/forceloadRemove`，统一 `chunkX << 4` 换成方块坐标；主世界入界 / 下界入界 / 远端 / 站位四处都是「先 forceload → 再 fill → 再 tp」 |
| 2 | 人「从天上落下来」、传进岩浆湖 | 入界点 `tp 0 100 0`（下界）/ `tp 8 120 8`（主世界）落在未改造地形上；只掏 3×3×3 小口袋时上半身仍埋在地形里 | 新增 `carveSafeRoom`：内空 5×5×7 + 六面下界岩壳，四处落点全部先封壳再传 |
| 3 | 模块开着，聊天栏只有「发指令 tp」，**一颗不挖** | 「前往野外」判「传送是否生效」只认**单刻大跳变**，而模块自己那一跳落地时状态机还在自己的「指令执行中」等待窗里；`lastWatchPos` 每刻刷新，等走到判据那一步跳变已被抹平 | 照主世界 A~L 装置既有做法：`前往野外` 绑站位点（幂等），真正的位移由装置在状态机空闲等的时候**补一跳**（取「站位点 / 候选点」里离玩家更远的那一端，40+ 格） |
| 4 | 人离开下界后读数全错（下界证据作废却继续记） | 没有维度守卫 | `tickGates / tickMineSetup / tickMineRun` 三处先判 `inDimension(NETHER)`，不在就如实报「环境被外部打断」并收口；另加每刻 `isCreative()` 兜底（外部按 F3+F4 切生存会真的摔死人，实测踩过） |

### 结论

- 26.1.2：远古残骸 `1` 候选 / 下界石英 `70` / 下界金 `48`；三个矿物各
  **候选命中（通过）/ 目标来自候选（通过）/ 真值被挖掉（通过）/ 换下一颗（通过）**；装置 **全部判定：通过**，
  驱动器 **判决：通过**（T5/T6/T12）。
- 26.2：同形，装置 **全部判定：通过**、驱动器 **判决：通过**（T10/T11/T13）。
- 两线 `gradlew build` EXIT=0。
