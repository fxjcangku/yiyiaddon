# 236 · Seed Ore Engine 通用化 + 其它 10 种矿物 + Nether 预测报告

- 项目：`D:/mcv/wt-236`（worktree，分支 `feat/236-seed-ore-engine`，基线 `ad35b7a9e4726a37960492b6373094a0f248b4e3`，Minecraft 26.1.2）
- 阶段：高速开发线 C · 236（把钻石专用预测器提炼成通用 Seed Ore Engine，覆盖主世界 8 种 + 下界 3 种矿物）
- 结论：**通过**。22 个实机用例（主世界 8 种 × 2 区块 + 下界 3 种 × 2 区块）在开发实例「全部判定：通过」；**钻石冻结数字零变化**；**235 A~L 十二项全部判定：通过**；正式产物（`build/release/yiyiaddon-1.0-beta2-26.1.2.jar`）上冻结对照与 236 矩阵都复跑一遍「全部判定：通过」。
- 本阶段**未接 AutoMiner 新矿物**：除钻石外全部矿物只开放 预测 / 观察 / ESP，自动挖矿一律 fail-closed（口径第十九节）。

---

## 一、本阶段做了什么（一句话）

把「只算钻石、只算主世界」的 `DiamondSeedPredictor` 改成按 **（维度, 矿物）** 定义表工作的 `SeedOrePredictor`：世界生成算法一行未改（仍走原版 `applyBiomeDecoration`），新增的只是**维度档案**（下界的预设 / 噪声设置 / 随机算法都与主世界不同）、**矿物注册表**（方块集合 / 扫描窗口 / 写入来源出处）与**三处隔离键**（Repository / 观察 / 渲染）。

**不是重构**：钻石链的会话复用、viewer 集合推导、逐 viewer 差集、调度敏感复核、统计口径全部逐行保持 233/235 的样子，因此冻结数字必然不变（第十节实测确认）。

---

## 二、硬规则落实（逐条对照）

| 口径 | 落实 | 证据 |
|---|---|---|
| 去钻石硬编码，Diamond 冻结零变化 | `SeedOrePredictor` 通用化；`OreType` 1 → 11；扫描窗口改由 `SeedOreRegistry` 现算（钻石窗口 −64~16 = 原 `SCAN_SECTIONS=6` 的同一组 section） | T1/T4：243 / 110 / 29（敏感 7）/ Seed2 23·1·22·0 / 争议格调度敏感 / 宿主查询 0 全部不变 |
| 只用 26.1.2 真实源码参数 | 每条矿物定义都写明 `OreFeatures` / `OrePlacements` / `PlacementUtils` / `BiomeDefaultFeatures` / `ScatteredOreFeature` / `OreVeinifier` 的行号出处（`SeedOreRegistry` 类注释） | 源码：`seed/ore/SeedOreRegistry.java` |
| 下界独立 Dimension Descriptor | `SeedDimensionProfile`（预设 + 噪声设置 + 高度），**下界 `useLegacyRandomSource=true` ⇒ 换随机算法** | 源码：`seed/ore/SeedDimensionProfile.java`；T2 第 5 节 |
| 下界 AutoMiner fail-closed | `NETHER_AUTOMINER_ENABLED=false`；`mayUseForAutomatedMining()` 加主世界闸门；`autoMiningTargetOre()` 下界恒 null | T2/T6：`闸门关闭 / 目标为 null / 三种矿物全被拒（通过）` |
| 三处隔离键 | `TargetKey`（维度+矿物+区块）、观察层 `candidatesByKey`、渲染按矿物取色 | T2 第 3/6 节：11 个区块并存 8 种矿物、维度不混入 0 条、ESP 条目与候选一一对应 |
| Observation 只读真实已加载 Chunk | 正式观察层**未改动**（仍 `LOAD_OR_GENERATE=false`）；本阶段只把「按矿物判定方块」接进去 | 观察快照 `已确认 3383`（全部来自客户端真实已加载区块） |
| 至少 3 种矿物做 request-order 对照 | 红石 / 青金石 / 煤 / 远古残骸 各做「邻块先→目标」vs「目标冷启动」两种合法顺序 | T2 第 7 节 |
| 不复制多份页面 | Seed 页面只增加「预测矿物」多选区；下界时按维度显示远古残骸 / 下界石英 / 下界金 | `MiningSeedPage` |
| Renderer 不按矿物建类 | 仍是 `SeedOreWorldRenderer` 一个渲染器；颜色集中 `SeedOrePalette` | `seed/render/*` |
| 禁止自动化写 `run-26.1.2` / `run-26.2` | 全程只写 `.dev-runs/26.1.2/seed/**` 与 `build/production-smoke/**` | 第十一节 mtime 核对 |

---

## 三、架构

### 3.1 单一矿物真源：`SeedOreRegistry`

`SeedOreDefinition(oreType, dimension, blocks, configuredFeatures, placedFeatures, frequencyCn, heightCn, biomeScopeCn, discardChanceMaxCn, scanMinY, scanMaxY, writePaths, autoMinerEligible, evidenceCn)`

读取器、观察层、界面、报告全部从这里取定义；**任何地方都不允许再出现 `switch (oreType)` 形式的方块判定或写死的扫描范围** —— 那只是「钻石硬编码」换了个矿物。

扫描窗口只影响**扫描成本**：真实写入位置永远由 Worker 里原版 `applyBiomeDecoration` 决定，窗口只是「我们去哪里找它」，因此一律取**超集**并按 section 对齐。

### 3.2 维度档案：`SeedDimensionProfile`

| | 主世界 | 下界 |
|---|---|---|
| 维度高度 | min_y −64 / height 384 | min_y 0 / height 256 |
| 生物群系预设 | `MultiNoiseBiomeSourceParameterLists.OVERWORLD` | `…NETHER` |
| 噪声设置 | `NoiseGeneratorSettings.OVERWORLD` | `…NETHER` |
| 矿脉 / 含水层 / 随机源 | 开 / 开 / XOROSHIRO | **关 / 关 / LEGACY** |

最后一行是它必须独立存在的根本理由：`RandomState.create(registries, 噪声设置键, seed)` 按噪声设置选随机算法（`NoiseChunk.java:166-168` 同理），沿用主世界设置去算下界会得到「看起来正常但整体错位」的结果。
`SeedDimensionProfile.requireOf()` 取不到即抛异常，**绝不用主世界参数顶替**。

### 3.3 预测器：`SeedOrePredictor`（原 `DiamondSeedPredictor`）

- 会话键 =（版本, 种子, 维度），**刻意不含矿物**：同一份离线世界可以回答任意矿物的提问（矿物只决定「读哪一段 Y、什么方块算命中」）；
- 构造时核对宿主维度与原版该维度高度一致（`matchesHost`），不一致即拒绝；
- `predict(seed, oreType, chunk)` 走 `SeedOreTarget.of(...)`；`predictDiamond(seed, chunk)` 保留给既有开发装置。

### 3.4 协议升级（Worker IPC）

`VERSION 1 → 2`；`OP_PREDICT_DIAMOND → OP_PREDICT`（载荷带矿物）；能力从 `predict_diamond_overworld` 拆成 `predict_overworld` / `predict_nether`（**按宿主真的具备哪一层逐条声明**，客户端在开会话前就 fail-closed）。

---

## 四、11 种矿物与下界参数（出处逐条）

| 维度 | 矿物 | 方块 | 频率（`OrePlacements`） | 高度 | 扫描窗口 | 来源 |
|---|---|---|---|---|---|---|
| 主世界 | 钻石 | `diamond_ore` / `deepslate_diamond_ore` | 小 7 + 中 2 + 大 rarity 9 + 埋藏 4 | 三角 −144~16；中 均匀 −64~−4 | −64~16 | `OreFeatures:109-112`、`OrePlacements:184-204`、`OreFeature` + **化石**（`ProcessorLists:607-611`） |
| 主世界 | 红石 | `redstone_ore` / `deepslate_redstone_ore` | 低位 4 + 深部 8 | 均匀 −64~15；三角 −96~−32 | −64~19 | `OreFeatures:97-108`、`OrePlacements:175-183` |
| 主世界 | 青金石 | `lapis_ore` / `deepslate_lapis_ore` | 浅层 2 + 埋藏 4 | 三角 −32~32；均匀 −64~64 | −64~68 | `OreFeatures:113-114`、`OrePlacements:205-210` |
| 主世界 | 金 | `gold_ore` / `deepslate_gold_ore` | 主 4 + 深部 0~1 + **恶地额外 50** | 三角 −64~32；均匀 −64~−48；均匀 32~256 | −64~260 | `OreFeatures:95-96`、`OrePlacements:163-174`、`OverworldBiomes:333` |
| 主世界 | 铁 | `iron_ore` / `deepslate_iron_ore` | 上 90 + 中 10 + 小 10 | 三角 80~384；三角 −24~56；均匀 −64~72 | −64~319 | `OreFeatures:93-94`、`OrePlacements:154-162`、**矿脉**`OreVeinifier:63-65` |
| 主世界 | 铜 | `copper_ore` / `deepslate_copper_ore` | 小 16 + 大 16 | 三角 −16~112 | −64~116 | `OreFeatures:145-146`、`OrePlacements:226-231`、**矿脉**`OreVeinifier:63-65` |
| 主世界 | 煤 | `coal_ore` / `deepslate_coal_ore` | 上 30 + 下 20 | 均匀 136~319；三角 0~192 | −64~319 | `OreFeatures:91-92`、`OrePlacements:148-153` |
| 主世界 | 绿宝石 | `emerald_ore` / `deepslate_emerald_ore` | 100（三角 −16~480，被世界高度截断） | 三角 −16~480 | −64~319 | `OreFeatures:127-138`、`OrePlacements:214-216`、**只在 8 类山地系生物群系**（`OverworldBiomes:190/770/800/847/870`） |
| 下界 | 远古残骸 | `ancient_debris` | 大簇无 Count（InSquare+三角 8~24）+ 小簇无 Count（RANGE_8_8） | 三角 8~24；均匀 8~248 | 0~255 | `OreFeatures:139-144`、`OrePlacements:217-225`、`ScatteredOreFeature:23-50`（±7 散布） |
| 下界 | 下界石英 | `nether_quartz_ore` | 常规 16 + 三角洲 32 | RANGE_10_10（uniform 10~246） | 5~250 | `OreFeatures:82`、`OrePlacements:116,118`、`BiomeDefaultFeatures:409-415` |
| 下界 | 下界金 | `nether_gold_ore` | 常规 10 + 三角洲 20 | RANGE_10_10 | 5~250 | `OreFeatures:81`、`OrePlacements:115,117` |

**唯一 `autoMinerEligible=true` 的是主世界钻石**（235 已过 A~L 实机验证）；其余 10 种一律 false。

---

## 五、下界安全边界（fail-closed）

```
维度档案：Minecraft 26.1.2 原版下界
下界自动挖矿：关闭（fail-closed）：尚未建立下界专属验证证据；下界当前只提供预测 / 观察 / ESP
本阶段允许自动挖矿的矿物（下界）：无
mayUseForAutomatedMining() = false；autoMiningTargetOre() = null
【判定】下界自动挖矿 fail-closed（总门关 + 目标为 null + 三种矿物全被拒）：（通过）
```

理由不是「下界算法没实现」（算法已实现并用真实 26.1.2 参数验证过整条链路），而是**证据层面**：某台服务器完全可以主世界原版、下界自定义，而当前还没有任何「下界候选 ↔ 下界真实 BlockState」的实机对照证据。因此下界只开放 预测 / 观察 / ESP。

---

## 六、实机矩阵结果（22 用例，开发实例 T2 / 正式产物 T6 逐项一致）

每个用例取三类证据：**Worker（隔离进程，走服务层公开 API）↔ Oracle（集成服务端 ServerLevel）逐项一致**、
**候选 ↔ 真实方块**（目标区块 + ±1 邻域推到 FULL 后逐格扫描）、**观察 / ESP / 缓存隔离读数**。

### 6.1 候选 ↔ 真实（逐块读数）

| 维度 | 矿物 | 区块 | 候选 | 真实 | 命中 | 错报 | 漏报 | 命中率 |
|---|---|---|---|---|---|---|---|---|
| 主世界 | 钻石 | (3,−1) | 18 | 18 | 18 | 0 | 0 | 100.0% |
| 主世界 | 钻石 | (5,3) | 10 | 8 | 8 | 2 | 0 | 80.0% |
| 主世界 | 红石 | (3,−1) / (5,3) | 28 / 21 | 28 / 21 | 28 / 21 | 0 / 0 | 0 / 0 | 100% |
| 主世界 | 青金石 | (3,−1) / (5,3) | 28 / 13 | 28 / 13 | 28 / 13 | 0 / 0 | 0 / 0 | 100% |
| 主世界 | 金 | (3,−1) / (5,3) | 37 / 8 | 37 / 8 | 37 / 8 | 0 / 0 | 0 / 0 | 100% |
| 主世界 | 铁 | (3,−1) / (5,3) | 60 / 54 | 60 / 54 | 60 / 54 | 0 / 0 | 0 / 0 | 100% |
| 主世界 | 铜 | (3,−1) / (5,3) | 69 / 92 | 69 / 92 | 69 / 92 | 0 / 0 | 0 / 0 | 100% |
| 主世界 | 煤 | (3,−1) | 77 | 99 | 56 | 21 | 43 | 72.7% |
| 主世界 | 煤 | (5,3) | 186 | 186 | 186 | 0 | 0 | 100% |
| 主世界 | 绿宝石 | (3,−1) / (5,3) | 0 / 0 | 0 / 0 | 0 / 0 | 0 / 0 | 0 / 0 | —（非山地生物群系） |
| 下界 | 远古残骸 | (−3,4) / (4,−3) | 1 / 2 | 1 / 2 | 1 / 2 | 0 / 0 | 0 / 0 | 100% |
| 下界 | 下界石英 | (−3,4) / (4,−3) | 70 / 41 | 70 / 41 | 70 / 41 | 0 / 0 | 0 / 0 | 100% |
| 下界 | 下界金 | (−3,4) / (4,−3) | 48 / 14 | 48 / 14 | 48 / 14 | 0 / 0 | 0 / 0 | 100% |

汇总：**11 种矿物中 9 种做到「候选全部能在真实世界里找到（错报 0）」**；错报合计 23 格、漏报合计 43 格，全部集中在钻石 (5,3) 与煤 (3,−1) 两处，逐格归因与解释见 6.3。

**口径说明（重要）**：本阶段引擎**不为任何坐标声称确定性**（不产出 `DETERMINISTIC`，229/234 定案），候选的语义是「可能在这里」。因此错报 / 漏报是**读数**，不是失败判据；真正的硬判据是「Worker↔Oracle 逐项一致 + 宿主 ChunkMap 查询恒为 0 + 确定性恒为 0 + ESP 覆盖预测坐标」。装置对每一格**未被引擎自查标记**的错报都要求能落到第七节实测的顺序敏感用例上，落不下去必须显式列出。

### 6.2 逐项一致性（22/22 通过）

- **Worker ↔ Oracle 逐项完全一致**（数量 / 逐 BlockPos / 矿物 / 确定性 / 来源 / 写入者 / 冲突写入者）：22 个用例全部「完全一致」；
- **宿主 ChunkMap 查询恒为 0**（每一项、两侧都是 0）：预测主链没有向真实世界取过任何数据；
- **确定性恒为 0**：与口径一致；
- **ESP 覆盖预测坐标**：每个用例的渲染条目数 ≥ 该用例候选数（累计层面「ESP 条目 = 候选数」逐一相等）。

### 6.3 请求顺序对照（第七节原文）

```
红石（主世界 目标区块 3,-1，邻块 4,-1）：顺序「邻块→目标」候选 28，顺序「目标冷启动」候选 28，集合相同（通过）
  与真实世界比对：真实 28 格；两种顺序都与真实一致
青金石（主世界 目标区块 3,-1，邻块 4,-1）：28 / 28 集合相同（通过）
煤（主世界 目标区块 3,-1，邻块 4,-1）：顺序「邻块→目标」候选 99，顺序「目标冷启动」候选 77，集合相同（**不通过**）
  与真实世界该区块比对：真实 99 格；顺序「邻块→目标」与真实一致（通过）；顺序「目标冷启动」与真实一致（不通过）
  两种合法顺序的差异：43 格只在「邻块→目标」/ 21 格只在「目标冷启动」；引擎把它们标为调度敏感的 21 格（通过）
  调度敏感分析：基线 77 个 / 反向 125 个；基线有而反向没有 21 个、反向有而基线没有 69 个
  调度敏感分析判定：将 21 个坐标标为调度敏感（存在两种合法顺序给出不同结果，属正面证据）
远古残骸（下界 目标区块 -3,4，邻块 -2,4）：1 / 1 集合相同（通过）
```

**结论（这是本阶段最值得记下的一条）**：煤在区块 (3,−1) 上的 21 格「错报」与 43 格「漏报」，成因是**合法请求顺序敏感**，而且

1. 那 21 格**正是引擎自己标记为「调度敏感」的 21 格**（`21 = 21`，一个不差）；
2. 43 格漏报**正是「邻块先」这条合法顺序多出来的 43 格**；
3. 换成「邻块先→目标」这一合法顺序，引擎给出 **99 格，与真实世界的 99 格完全一致**。

也就是说：真实世界那一格方块对应的是**另一种合法执行顺序**，而引擎（a）没有隐藏它（标了调度敏感），（b）在另一种合法顺序下给出了与真实世界逐格相同的结果。钻石 (5,3) 的 2 格错报同理 —— 那 2 格在用例里就被**引擎自查标记为调度敏感**（`错报的确定性分类：调度敏感=2`）。

### 6.4 隔离读数

```
【主世界】预测缓存条目（维度 + 矿物 + 目标区块）：88 条，其中维度不符 0 条（通过）
  同一目标区块上并存多种矿物的区块数：11
  命中坐标示例 (0,0)=钻石 红石 青金石 金 铁 铜 煤 绿宝石；(3,-1)=钻石 红石 青金石 金 铁 铜 煤 绿宝石…
【判定】主世界键隔离（同区块并存多矿物）：（通过） / 维度不混入：（通过） / ESP 条目与候选一一对应：（通过）
【下界】预测缓存条目：33 条，其中维度不符 0 条（通过）；并存多矿物的区块数：11
  两个维度都出现过的目标区块坐标：6（同一组坐标在两个维度各有一份互不相干的缓存）（通过）
【判定】主世界 → 下界切换清空（缓存 / 观察 / 渲染全为 0）：（通过）
【判定】下界键隔离（同区块并存多矿物）：（通过） / 维度不混入：（通过） / ESP 条目与候选一一对应：（通过）
```

第 1 条是真问题被真解决：**同一区块的钻石与红石是两条互相独立的预测**，用区块单独做键会让它们互相顶掉，而且**不会报错**。336 起键含矿物，11 个区块上 8 种矿物共存、累计 88 条缓存互不覆盖。

---

## 七、观察 / ESP 泛化（实机读数）

- 观察层：`候选 3408 / 已确认 3383 / 当前缺失 25 / 未观察 0`（主世界）、`候选 1881 / 已确认 1881 / 当前缺失 0`（下界）——「已确认」= 预测坐标在**客户端真实已加载的区块**里确实看到该矿物，全部按 `(维度, 矿物)` 独立判定；
- 观察层仍然只读客户端已加载区块（`LOAD_OR_GENERATE=false`），本阶段没有碰这条铁律；
- 渲染层：一个 `SeedOreWorldRenderer`，颜色集中在 `SeedOrePalette`；快照条目与预测候选**逐一相等**（累计层面按矿物分组相等）；
- 钻石颜色与 233/235 逐位相同（`预测 0xEB4FA8FF / 侧 0x2D2F6FD0`、`已确认 0xF53FE07A / 0x3721A34A`）。

---

## 八、UI

Seed 页面在「种子 / 覆盖范围 / 显示当前缺失」之外新增**「预测矿物」多选区**（当前维度支持的矿物才列出来，tooltip 直接来自 `SeedOreDefinition.evidenceCn` 的原版出处），并显示「可自动挖矿矿物」「下界自动挖矿（fail-closed）」两行说明。**没有复制第二份页面**，下界切回来勾选状态保留（配置存的是全集，只有当前维度支持的那部分生效）。

---

## 九、生产 Jar smoke（正式产物）

产物：`build/release/yiyiaddon-1.0-beta2-26.1.2.jar`（45.4 MB，ProGuard 混淆 + 字符串 / 控制流加固；SHA-256 `209577b2bd1fe85bee95a34ea1ad96c32037afe2b12e393bf659684fe39946a2`）
脚本：`gradle/production-smoke.ps1`（独立 `gameDir`，只放 `fabric-api` + 本模组）

| 冒烟 | 场景目录 | 结论 |
|---|---|---|
| 冻结 Worker 对照 | `build/production-smoke/parity236` | **通过**（退出码 0，耗时 49s；Worker PID=30584 起于 t=12s、消失于 t=48s；`退出后残留 Worker 进程：0`；`命令行无开发目录`）；装置报告 `全部判定：通过`，243 / 110 逐项与开发实例一致 |
| 236 多矿物矩阵 | `build/production-smoke/orematrix236` | **通过**（退出码 0，耗时 56s；Worker PID=35312 起于 t=13s、消失于 t=55s；`退出后残留 Worker 进程：0`；Worker RSS 峰值 651 MB）；装置报告 `全部判定：通过`，与开发实例逐项一致 |

---

## 十、冻结回归（最小冻结 gate）

在正式产物上重跑（T4）：

| 冻结项 | 期望 | 实测 | 判定 |
|---|---|---|---|
| Seed 20260922 十目标候选合计 | 243 | 243 | 通过 |
| Seed 12345 四目标候选合计 | 110 | 110 | 通过 |
| Seed 12345 (−1,−1) | 29（其中调度敏感 7） | 29 / 7 | 通过 |
| Seed 2 (−400,380) | 23 / 1 敏感 / 22 未解析 / 0 确定 | 23 / 1 / 22 / 0 | 通过 |
| Seed 2 争议格 (−6385,−59,6085) | SCHEDULE_SENSITIVE | 调度敏感 | 通过 |
| 宿主 ChunkMap 查询 | 0 | 全部 0 | 通过 |
| 逐项一致性（15 个目标） | 完全一致 | 15/15 完全一致 | 通过 |

**通用化没有让任何一个冻结数字发生变化** —— 这正是本阶段最关键的回归要求。

---

## 十一、235 兼容性（A~L 十二项）

`runClientSeedTargetTest`（T3）在 236 修订后：**全部判定：通过**。

```
A 种子关闭原自动挖矿回归：通过   B 未验证不得开始：通过   C 选定真实种子目标：通过
D 目标未加载仍能导航：通过       E 加载后实际钻石走现有挖矿链：通过   L 挖掉一颗换下一颗：通过
F MISSING 立刻放弃换颗：通过     G 手动置空气不卡死：通过   K 寻路失败不无限重试：通过
H 关 ESP 不影响：通过            I 重开 ESP 恢复：通过
J 换维度：身份换成下界（主世界身份作废）通过 / 闸门关闭 通过 / 目标清空 通过 / 无主世界残留条目 通过 / fail-closed 停机 通过
J 返回主世界：身份重建 通过 / 未带旧目标 通过 / 需求源在位 通过
```

**J 项的判据更新（必须写清楚）**：235 期「换维度 ⇒ 身份必须为**空**、缓存必须为 0」，前提是「下界不受支持」。236 起下界是受支持维度，进下界会**换成下界身份并重新开始预测 / 观察 / ESP**，所以那两条断言不再成立。本阶段把 J 的判据换成 236 形态，但**它要守的实质一条没变**：旧身份必须整批作废（换成下界身份）、旧维度的结果一条都不许带过来（缓存里 0 条主世界条目）、自动挖矿必须 fail-closed 停机。

---

## 十二、本轮实际修掉的缺陷

| # | 缺陷 | 影响 | 修法 |
|---|---|---|---|
| 1 | `SeedWorkerSessions.openSession`：会话切换（种子 / 维度变化）时**只释放离线世界，没有清会话状态** | **下界预测 100% 失败**（后续请求撞上「请求维度与会话不一致」被拒 `NO_SESSION`）；236 第一次实机就暴露 | 切换时把 `sessionOpen/sessionId/sessionSeed/sessionDimension/lastHeldChunks` 一起作废 |
| 2 | `SeedMiningConfig.selectedOres()`：空集回落 `DEFAULT_ORES` | **「取消勾选矿物」这个动作失效**（取消钻石后它自己又回来）；装置也无法只勾一种矿物 | 加 `oresExplicitlySet`：没改写过 = 出厂默认钻石；改写过 = 以用户写的为准（含空集，服务层再回落到本维度第一种矿物） |
| 3 | `SeedWorkerIpcServer`：协议级拒绝（`WorkerProtocolException`）**不写日志** | 上条缺陷在 Worker 日志里一句证据都没有，只能从客户端那句通用文案反推 | 拒绝原因与错误码写 `WARN` 日志 |
| 4 | `SeedMiningService.syncRuntime` 注释写「维度为主世界」 | 与代码（`dimensionSupported()` 已含下界）不符，会误导后人 | 注释改为「维度受支持（主世界或下界）」 |
| 5 | 装置（dev）：请求顺序对照用了一个**隔着两个区块的「邻块」** | 目标区块的 viewer 集合与执行顺序根本没变 ⇒ 对照是空的（第一版装置因此得出「煤顺序无关」的假结论） | 邻块改成**直接相邻**（写半径 1 内），并把「真实世界该区块」拉进来做第三方差 |
| 6 | 235 装置（dev）：J 项两条断言按 236 维度语义更新 | 见第十一节 | 判据换成「身份换成下界 + 无主世界残留条目」 |

第 1 条是本阶段的真实收获：它不是抽象出来的，而是**第一次下界实机就炸出来的**，并且证明了「有没有真机跑」是有区别的。

---

## 十三、零写入与构建校验

- **`run-26.1.2` / `run-26.2` 零写入**：`D:\mcaddon\yiyiaddon\run-26.1.2` 最新写入 `2026/9/23 19:50:48`、`run-26.2` 最新写入 `2026/9/23 19:35:36`，均早于本次 236 自动化运行起点 `23:19`；本工作区 `D:\mcv\wt-236\run-26.1.2` 为空目录、`run-26.2` 不存在。本阶段所有自动化只写 `.dev-runs/26.1.2/seed/**` 与 `build/production-smoke/**`（两者都在 `.gitignore` 内）。
- **构建**：`.\gradlew.bat build obfuscatedJar verifyObfuscatedJar` → `BUILD SUCCESSFUL`；类改名 1381 个；类加载及成员校验 1423 项；变换行为对照 64448 项（8 组随机布局）；密文篡改认证拒绝 = 通过；最终发布包强校验通过。
- 未触碰 `D:\mcaddon\yiyiaddon`（master 工作区）与 `D:\mcv\wt-262`。

---

## 十四、当前限制（如实记录）

1. **调度敏感分析的触发条件在「暖会话」下会漏**：`ScheduleSensitivityAnalyzer` 只在「基线执行中确有其它 viewer 改过目标区块」时才跑反向顺序复核；目标区块已在会话缓存里时该判据读到 0，于是煤在矩阵用例里呈现为「未解析 21 格」而不是「调度敏感 21 格」（冷会话的第七节对照下就是 21 格调度敏感）。**引擎没有错报（它本来就不声称确定性），但「同一格在冷 / 暖会话下的分类不一致」是一个真实的可用性瑕疵**，建议后续阶段修（例：暖会话下复用首次基线执行的外来写入者读数）。
2. **绿宝石没有取到非零样本**：夹具世界两个目标区块都不是山地系生物群系 ⇒ 绿宝石候选 0 / 真实 0（两侧一致，但没证明「有矿时也对」）。要补证需要挑到山地系生物群系区块。
3. **下界只在单人集成服务端上验证**：生产 smoke 用的也是单人夹具世界，**没有**在「专用服务器 + 下界」上跑过（下界 AutoMiner 本来就是 fail-closed，因此不影响安全边界，但足以说明该证据缺口）。
4. **下界观察 / ESP 只在夹具世界验证**：真实服务器上「下界被自定义、主世界原版」的组合没有覆盖。
5. **远古残骸样本量极小**（1 / 2 格每区块），读数可信但统计意义弱。
6. **只在 26.1.2 线上跑过**：本工作区只构建 / 验证了 26.1.2；26.2 线未跑（`buildBoth` 未执行）。
7. **真实用户环境（100+ Mods + Iris / Sodium）未在本阶段复跑**：235 已做过该口径，本阶段只跑 Vanilla + Fabric 可控基线。

---

## 十五、证据索引

| 编号 | 文件 | 内容 |
|---|---|---|
| T1 | `236-证据/T1-冻结Worker对照-开发实例.txt` | 开发实例冻结回归（243 / 110 / 29·7 / Seed2 23·1·22·0 / 争议格 / 宿主查询 0，全部判定：通过） |
| T2 | `236-证据/T2-236多矿物矩阵-开发实例.txt` | 22 用例矩阵（逐项一致 / 候选↔真实 / 隔离 / 闸门 / 顺序对照，全部判定：通过） |
| T3 | `236-证据/T3-235目标接入A到L-开发实例.txt` | 235 A~L 十二项（236 修订后，全部判定：通过） |
| T4 | `236-证据/T4-生产冒烟-冻结Worker对照-装置报告.txt` | 正式产物上的冻结对照（243 / 110，全部判定：通过） |
| T5 | `236-证据/T5-生产冒烟-冻结对照-Worker命令行.txt` | 正式产物 Worker 命令行（可核对「无开发目录」） |
| T6 | `236-证据/T6-生产冒烟-236矩阵-装置报告.txt` | 正式产物上的 236 矩阵（与开发实例逐项一致，全部判定：通过） |
| T7 | `236-证据/T7-生产冒烟-236矩阵-Worker命令行.txt` | 正式产物 Worker 命令行（可核对「无开发目录」） |
| T8 | `236-证据/T8-生产冒烟-冻结对照-冒烟判决.txt` | 冒烟脚本判决（残留 Worker 0） |
| T9 | `236-证据/T9-生产冒烟-236矩阵-冒烟判决.txt` | 冒烟脚本判决（残留 Worker 0） |

原始日志与装置输出（未收进报告目录，供复查）：`.dev-runs/26.1.2/seed/{ore-matrix,worker-parity,target}/**`、`build/production-smoke/{parity236,orematrix236}/**`。

---

## 十六、本阶段改动清单（代码）

**新增**

- `seed/ore/SeedDimensionProfile.java` —— 维度档案（主世界 / 下界：预设 / 噪声设置 / 高度）
- `seed/ore/SeedOreDefinition.java` —— 一条矿物定义（方块 / 频率 / 窗口 / 写入来源 / 资格）
- `seed/ore/SeedOreRegistry.java` —— **11 种矿物的唯一真源**（含原版出处行号）
- `seed/ore/SeedOreWritePath.java` —— 写入来源枚举（`ORE_FEATURE` / `SCATTERED_ORE` / `FOSSIL` / `ORE_VEIN`）
- `seed/ore/SeedOrePalette.java` —— 按矿物取色（钻石与 233/235 逐位相同）
- `seed/prediction/SeedOrePredictor.java` —— 通用预测器（替换 `DiamondSeedPredictor`）
- `seed/runtime/TargetKey.java` —— `(维度 + 矿物 + 目标区块)` 隔离键
- `dev/seedpoc/SeedOreMatrixRegression.java` —— 236 多矿物多维度实机矩阵装置
- `build.gradle` 新增 `runClientSeedOreMatrixTest`（运行目录 `.dev-runs/<版本>/seed/ore-matrix`）

**删除**

- `seed/prediction/DiamondSeedPredictor.java`（能力整体并入 `SeedOrePredictor`）

**修改（按职责）**

- 模型 / 配置：`OreType`（1 → 11）、`SeedOreTarget`（加 `of(...)`）、`SeedMiningConfig`（`selectedOres` + `oresExplicitlySet`）
- 世界生成：`OfflineWorldgenContext`（按维度取预设 / 噪声设置）、`OreChunkReader`（窗口改由定义现算）
- 预测：`PredictionSession`、`ScheduleSensitivityAnalyzer`（维度 + 矿物透传）
- 运行时：`SeedPredictionRepository`（键 + 按矿物分组限流）、`SeedPredictionCoverageController`（`Task(oreType, chunk)`）
- 观察 / 渲染：`SeedOreObservationTracker`（按 key 分组 + 按矿物判定方块）、`SeedOreWorldRenderer`（集中取色）
- 服务 / 校验：`SeedMiningService`（维度闸门 + 矿物集合 + 下界 fail-closed）、`SeedValidationService` / `SeedSuspicionPolicy`（证据维度 / 矿物）
- Worker：`SeedWorkerProtocol`（`VERSION=2`、`OP_PREDICT`、两个维度能力）、`WorkerRequest`、`SeedWorldgenWorkerClient`、`SeedWorkerPredictionMapper`、`SeedWorkerHost`（下界那一层）、`SeedWorkerSessions`（按维度持预测器 + **会话切换修复**）、`SeedWorkerIpcServer`（拒绝留痕）、`SeedWorkerMain`
- 界面：`MiningSeedPage`（预测矿物多选 + 下界说明）
- 目标接入：`SeedMiningTargetProvider`（按 `SeedOreRegistry` 判定目标矿物，不再写死钻石）
- 开发装置：`FormalSeedRegression` / `ServiceRegression` / `WorkerParityRegression` / `LegacyWorkerProbe` / `SeedPocEntry` / `SeedPocFlags` / `SeedTargetRegression`

---

## 十七、判定

**通过。**

- 通用化零回归：钻石 243 / 110 / 29·7 / Seed2 23·1·22·0 / 争议格 / 宿主查询 0 全部不变（开发实例 + 正式产物各一遍）；
- 11 种矿物：22 个用例 Worker↔Oracle 逐项完全一致、宿主查询 0、确定性 0，9 种矿物候选 100% 落在真实方块上；剩余 23 格错报全部有解释（2 格引擎自查调度敏感 + 21 格由顺序对照实证为合法顺序敏感）；
- 下界：100% 命中（远古残骸 / 下界石英 / 下界金 6 个用例），自动挖矿恒 fail-closed；
- 隔离：Repository / 观察 / 渲染三处键隔离成立，跨维度零混入；
- 235 兼容：A~L 十二项全部通过（J 项判据按 236 维度语义更新，实质未放宽）；
- 生产 smoke：两轮通过，残留 Worker 0；`run-26.1.2` / `run-26.2` 零写入。
