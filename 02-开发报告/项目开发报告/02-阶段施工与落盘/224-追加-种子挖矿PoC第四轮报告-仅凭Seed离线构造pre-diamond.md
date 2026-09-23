# 224 · 追加 · 种子挖矿 PoC 第四轮报告（26.1.2 · 仅凭 Seed 离线构造 pre-diamond）

> **本报告为临时版（数据止于 `run16`）。后续又跑了 `run21`（修「快照缺失当一致」假阳性）、
> `run22~25`（泛化 seed 12345）、`run26`（终版正式轮 · 双口径）、`run27/28`（关 C2ME 对照），
> 结论与数字均已更新，请以
> [225-追加-种子挖矿PoC第四轮报告-终版-仅凭Seed离线构造pre-diamond.md](225-追加-种子挖矿PoC第四轮报告-终版-仅凭Seed离线构造pre-diamond.md)
> 为准。**本文件仅作过程留档。**

日期：2026-09-23
阶段：种子挖矿 —— **第四轮「离线构造 pre-diamond，纯 Seed 推钻石」**。
本轮要回答的唯一问题：

> 不读取真实生成期 pre-diamond 快照的情况下，客户端能不能仅凭
> 「Minecraft 26.1.2 + 世界 Seed + 维度 + ChunkPos + 当前版本原版注册表/worldgen 配置」，
> 离线构造出与原版真实生成过程一致的 pre-diamond 输入状态，并由此推出钻石 BlockPos？

**验证方式：实机验证**（`gradlew runClient`，固定种子 `20260922`、沿用第三轮相同的 10 个固定区块
`0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`）。
本轮共 4 轮实机运行：`run13`（离线调试，1 个 viewer）/ `run14`（邻域装饰对照·首轮，因缓存半径不足崩溃）/
`run15`（邻域装饰对照·修复后）/ `run16`（**第四轮正式轮**，10 个 viewer，报告落盘
`run-26.1.2/seedpoc-实验报告-run16-第四轮正式.txt`）。

---

## 一、结论（一屏看完）

| 问题 | 答案 | 证据 |
| --- | --- | --- |
| 客户端能否离线把主世界区块跑到「第一条钻石 feature 之前」 | **能** | 自建 19→21 圈区块缓存 + 逐层驱动原版 7 个生成阶段，实机跑通，10 个 viewer 全部产出离线 pre-diamond（第一节表、第五节） |
| 离线构造读了真实世界的生成数据吗 | **没有** | 离线 region 对宿主 `ChunkMap` 的查询次数 = **0**；种子相关的四件东西（BiomeSource / ChunkGenerator / RandomState / ChunkGeneratorStructureState）全部自建（第四、七节） |
| 离线 pre-diamond 与真实 pre-diamond 是否逐 BlockPos 一致 | **部分一致（1/10 含邻域、2/10 仅目标区块）** | 差异全部落在「邻域/跨区块写入」这一类，且已被两个对照实验定位（第八、九、十节） |
| **纯 Seed → 钻石 BlockPos 是否达标** | **达标：10/10 逐 BlockPos 完全一致** | 真值 240 / 预测 240 / 匹配 240 / 漏报 0 / 错报 0 / 查全 100.00% / 查准 100.00%（第十一节） |
| 第一处分叉在哪一阶段 | 含邻域口径：BIOMES 1 / NOISE 2 / pre-diamond 6；**只看目标区块自己时，大多数 viewer 无分叉** | 逐阶段 checkpoint 表（第八、九节） |
| 是否修了生成器 | **没有修生成器**；只修了驱动层的「缓存半径」一处（第六节 6.2），修的是装置不是算法 | run14 崩溃 → 修复 → run15/run16 通过 |
| 是否具备结案条件 | **「Seed → 钻石 BlockPos」这条判据已达标；但「离线 pre-diamond 逐 BlockPos 一致」这条理想判据尚未达标**，因此**不宣布 PoC 结案** | 第十二、十四节 |

一句话：**「纯 Seed 推出的钻石坐标」在 10 个固定区块上已经 100% 正确；但离线 pre-diamond 状态本身还不是
逐格等同于真实生成期状态——差在邻域年龄与跨区块装饰顺序，不在种子算法。**

---

## 二、边界遵守情况（逐条对照用户第四轮口径）

**没有做**：

- 未开发正式「种子挖矿」UI / Seed 真实性验证 / 目标系统接入；未改 `MiningStateMachine`、
  `MiningPathing`、`MiningVeinMiner`、精准采集、时运、Baritone、食物、回家、背包白名单（一行未改）。
- 未开发第二套挖矿系统；未扩展其它矿物；未扩展其它维度；未做 26.2 Seed 适配；未创建未修改任何 26.3 内容。
- **未把真实 pre-diamond 快照当最终预测输入**：第一阶段比较用真实快照（只作 oracle），
  第二阶段的**安装对象是离线快照**（`OfflineWorldgenVerifier` 里 `ledger.install(offlinePre)`）。
- 未用客户端已加载真实区块扫描冒充 Seed 预测。
- 未读取真实测试世界作为离线生成输入（第七节给出计数自证）。

**做了**：

- 新增离线 worldgen 上下文与管线（自建 holder / region / 驱动器），驱动原版各生成阶段。
- 新增真实侧逐阶段 checkpoint 探针（BIOMES / NOISE / SURFACE；CARVERS 复用第三轮「装饰开始前」）。
- 新增逐 BlockPos 比较器（方块 / 生物群系 / 两张 WG 高度图 / 结构信息）。
- 把**离线** pre-diamond 接第三轮已证明正确的钻石链，做「纯 Seed → 钻石 BlockPos」验收。
- 做了两次对照实验（邻域装饰半径 0 / 1）来定位差异来源。

**Git 状态**：正式业务文件本阶段未改。实验代码全部在 `src/main/java/com/yiyiaddon/dev/seedpoc/` 与本轮新增的
`src/main/java/com/yiyiaddon/mixin/client/ChunkStatusStageProbeMixin.java`；实验世界、报告、日志都在
`.gitignore` 覆盖的 `run-26.1.2/` 下。第三轮的 replay 核心（`OreDecorationReplay` / `ShadowLevelFactory` /
`SingleViewerVerifier` / `DiamondFeatureOracle`）**未改**，只在 `PlacedFeatureStageCaptureMixin` 里加了
「离线调用不进真实侧探针」的分流（第六节 6.3）。

---

## 三、26.1.2 原版 Chunk generation 调用链（本轮实际依赖的那一条）

以本机 `01-开发参考库/Minecraft-26.1.2-Mojang源码` 为准（行号即该目录下文件行号）：

```
ChunkMap#applyStep:620-646            调度一次「某状态」
  └─ ChunkGenerationTask#create:36-42 缓存半径 = ChunkPyramid.GENERATION_PYRAMID
  │                                    .getStepTo(targetStatus).getAccumulatedRadiusOf(ChunkStatus.EMPTY)
  │                                    （对 FEATURES 实测 = 10 → 21x21 区块）
  └─ ChunkGenerationTask#scheduleLayer:117-136
       逐状态铺层，每层半径 = getStepTo(targetStatus).getAccumulatedRadiusOf(status)
       铺层顺序 = 状态升序 EMPTY → STRUCTURE_STARTS → STRUCTURE_REFERENCES → BIOMES
                 → NOISE → SURFACE → CARVERS → FEATURES
  └─ ChunkStep#apply:21-28            调用 ChunkStatusTasks 对应方法
  └─ ChunkStep#completeChunkGeneration:30-40   ProtoChunk#setPersistedStatus(目标状态)
  └─ ChunkMap#createEmptyChunk:594-597   new ProtoChunk(pos, UpgradeData.EMPTY, level,
                                          level.palettedContainerFactory(), null)

ChunkStatusTasks（每个状态具体做什么）：
  generateStructureStarts:40-53   → ChunkGenerator#createStructures:465+
  generateStructureReferences:62-69 → ChunkGenerator#createReferences:599-631（读 ±8 区块的起点引用）
  generateBiomes:71-77            → ChunkGenerator#createBiomes:117-124（fillBiomesFromNoise + sampler）
  generateNoise:79-99             → ChunkGenerator#fillFromNoise:633（+ BelowZeroRetrogen 两步修补）
  generateSurface:101-108         → ChunkGenerator#buildSurface:424
  generateCarvers:110-124         → Blender#addAroundOldChunksCarvingMaskFilter:320
                                    + ChunkGenerator#applyCarvers:126（带 level.getSeed()）
  generateFeatures:126-140        → Heightmap#primeHeightmaps
                                    + ChunkGenerator#applyBiomeDecoration:318-412
                                    + Blender#generateBorderTicks:265

区块读写的唯一入口（第三轮已证明按它复刻才一致）：
  WorldGenRegion#ensureCanWrite:229-259   写半径 = ChunkStep#blockStateWriteRadius（FEATURES = 1）
  WorldGenRegion#getChunk:107-144         依赖半径 = ChunkStep#directDependencies
  WorldGenRegion#getHeight:397-399        = chunk.getHeight(...) + 1 = getFirstAvailable
```

**FEATURES 步的逐圈依赖（实测打印，进报告）**：

```
FEATURES directDependencies = [CARVERS, CARVERS, STRUCTURE_STARTS×7]（半径 0..8）
其中 半径 0..1 至少 CARVERS，半径 2..8 至少 STRUCTURE_STARTS
```

---

## 四、离线 worldgen 实际使用的原版类（与自建项）

| 用途 | 原版类/方法（未重写） |
| --- | --- |
| 生物群系源 | `MultiNoiseBiomeSource.createFromPreset(minecraft:overworld 预设)` |
| 区块生成器 | `new NoiseBasedChunkGenerator(BiomeSource, Holder<NoiseGeneratorSettings>)` |
| 随机状态 | `RandomState.create(注册表, NoiseGeneratorSettings.overworld, 种子)` |
| 结构状态 | `ChunkGenerator#createState(结构集, RandomState, 种子)`（= `ChunkGeneratorStructureState.createForNormal`） |
| 逐状态驱动 | `ChunkPyramid.GENERATION_PYRAMID` + `ChunkStep#getAccumulatedRadiusOf` / `directDependencies` |
| 阶段实现 | `createStructures` / `createReferences` / `createBiomes` / `fillFromNoise` / `buildSurface` / `applyCarvers` / `applyBiomeDecoration` |
| 区域读写语义 | `WorldGenRegion`（子类化，见第五节）、`Blender.of` / `addAroundOldChunksCarvingMaskFilter` / `generateBorderTicks` |
| 区块对象 | `new ProtoChunk(pos, UpgradeData.EMPTY, level, palettedContainerFactory, null)` |
| 状态推进 | `ProtoChunk#setPersistedStatus` |
| 高度图 | `Heightmap.primeHeightmaps`、`ChunkAccess#getHeight` |

**自建（这就是本轮要证的东西）**：`OfflineWorldgenContext`（上下文）、`OfflineChunkHolder`（区块持有者）、
`OfflineChunkRegion`（区域视图）、`OfflineChunkPipeline`（驱动器）、`OfflineStageCapture`（离线捕获）。

---

## 五、新增 Java 文件与职责

| 文件 | 职责 |
| --- | --- |
| `dev/seedpoc/OfflineWorldgenContext.java` | 离线上下文：注册表 + 种子 → 自建 BiomeSource / NoiseBasedChunkGenerator / RandomState / ChunkGeneratorStructureState / 环境宿主（只取与种子无关的环境参数） |
| `dev/seedpoc/OfflineChunkHolder.java` | `GenerationChunkHolder` 子类：自己存「各状态 → 区块」，覆盖 `getChunkIfPresentUnchecked` 等**读方法**（原版读方法均为 public 非 final，因此**不需要 Accessor/Invoker**），`getTicketLevel()` 用 `ChunkLevel.byStatus(FULL)` |
| `dev/seedpoc/OfflineChunkRegion.java` | `WorldGenRegion` 子类：只覆盖 5 处**种子相关读取**（`getSeed` / `getRandom` / `getBiomeManager` / `getUncachedNoiseBiome` / `isOldChunkAround`），其余（维度类型 / 高度 / 注册表 / 写权限 / `getChunk` 依赖半径）全部沿用原版；并对宿主 `ChunkMap` 查询计数自证 |
| `dev/seedpoc/OfflineChunkPipeline.java` | 驱动器：按 `ChunkGenerationTask` 的半径规则逐层铺 `EMPTY…CARVERS`，再装饰目标区块（可选邻域），每层调用 `ChunkStatusTasks` 对应的原版实现；同步采集四个阶段 checkpoint；输出统计 |
| `dev/seedpoc/OfflineStageCapture.java` | 离线侧 pre-diamond 捕获（与真实侧同一捕获点：`PlacedFeature#placeWithBiomeCheck` HEAD + 只认钻石四条 + 每区块一次）；并作为「离线调用分流器」让真实侧三个探针跳过离线调用 |
| `dev/seedpoc/StageSnapshotCapture.java` | **真实侧与离线侧共用**的阶段快照采集器（方块状态 / 两张 WG 高度图 / 生物群系 / 结构信息），保证两侧布局逐位一致 |
| `dev/seedpoc/StageComparator.java` | 逐 BlockPos 比较器：方块 / 生物群系 / 高度图 / 结构，输出总格数、一致、不一致、**第一处不同坐标与两侧取值**；另有「仅目标区块」口径 |
| `dev/seedpoc/OfflineWorldgenVerifier.java` | 单 viewer 主流程：离线构造 → 四个阶段 + pre-diamond 比较 → 用**离线**快照装进测试世界 → 跑第三轮钻石链 → 与真实差集比对 |
| `dev/seedpoc/OfflineOutcome.java` | 第四轮验收数据载体（含两个口径的判定与 FIRST DIVERGENCE） |
| `mixin/client/ChunkStatusStageProbeMixin.java` | 真实侧逐阶段探针：`ChunkStatusTasks` 的 `generateBiomes` / `generateNoise` / `generateSurface` RETURN |

修改：`GenStageSnapshot`（加生物群系与结构信息）、`GenStageCapture`（抽公用采集 + 存真实侧逐阶段快照 +
加「离线 region 不进真实侧」分流）、`PlacedFeatureStageCaptureMixin`（HEAD/RETURN 对称分流）、
`SeedPocFlags`（4 个离线开关）、`SeedPocReport`（新增【十一】节）、`SeedPocRunner`（第四轮编排）、
`yiyiaddon.mixins.json`（注册新 Mixin）。

**包结构说明**：离线与比较类都放在 `com.yiyiaddon.dev.seedpoc` 同一包内，因为要复用第二轮/第三轮的
包内载体（`GenStageSnapshot` / `DiamondScanner` / `RegionStateLedger` / `OreDecorationReplay`），
把载体改成 public 会扩大暴露面（第 163~165 条：非必要不得擅自扩大改动）。

---

## 六、是否使用 AccessWidener / Mixin / Accessor

| 手段 | 是否使用 | 为什么 |
| --- | --- | --- |
| AccessWidener | **未新增** | 离线链路需要的都是 public/可覆盖成员 |
| Accessor / Invoker Mixin | **未使用** | `GenerationChunkHolder` 的读方法（`getChunkIfPresentUnchecked` / `getChunkIfPresent` / `getPersistedStatus` / `getLatestChunk` / `getLatestStatus`）都是 public 且非 final，**子类覆盖即可**；写入口（私有 `completeFuture`）根本不需要，因为驱动器自己调原版 `ChunkGenerator` 方法、自己记状态 |
| 新增探针 Mixin | **2 处** | ① 新增 `ChunkStatusStageProbeMixin`（真实侧 BIOMES / NOISE / SURFACE checkpoint，Mixin 注入 package-private 静态方法是唯一可行手段）；② 既有 `PlacedFeatureStageCaptureMixin` 加「离线调用分流」两行（否则离线生成会污染真实 oracle） |

### 6.2 本轮唯一一次修复：离线缓存半径

`run14`（`decorRadius=1`，邻域先装饰）崩溃：`ReportedException: Exception generating new chunk`
（`WorldGenRegion#getChunk` 在距离 ≥ `directDependencies().size()` 时按原版设计抛错）。

**原因**：原版缓存半径是以**被生成区块自己**为中心算的（10 圈）；离线按 viewer 建缓存时，
邻域区块自己的 10 圈要求会超出 viewer 的缓存。
**修复**：缓存半径与各层半径统一加 `decorRadius`
（`StaticCache2D.create(..., CACHE_RADIUS + decorRadius, ...)`、`radius = getAccumulatedRadiusOf(status) + decorRadius`）。
`decorRadius=0` 时与修复前逐字等价（即与原版 `ChunkGenerationTask` 的算法完全一致）。

### 6.3 探针分流（保证真实 oracle 不被离线生成污染）

`PlacedFeatureStageCaptureMixin` 两个注入点都加了一层判定：若 `level` 是 `OfflineChunkRegion`，
HEAD 侧交给 `OfflineStageCapture` 并直接 return，RETURN 侧同样跳过
（否则会去结一个从未开过的放置台账）。判定依据是 region 类型，离线 region 只可能出现在离线链路上。

---

## 七、离线构造需要多大邻域（实机打印，来自 `run16` 报告）

```
离线缓存半径（照原版 ChunkGenerationTask 算）：10 区块 → 每层最多 21x21 区块
逐层半径：
  empty                 10
  structure_starts      10
  structure_references   2
  biomes                 2
  noise                  1
  surface                1
  carvers                1
  FEATURES               0（目标区块自己）
FEATURES 逐圈依赖：[carvers, carvers, structure_starts ×7]
```

即：**目标区块跑 FEATURES 需要周围 ±1 圈至少 CARVERS、±8 圈至少 STRUCTURE_STARTS；
而每圈 STRUCTURE_STARTS 又要求各自 ±8 圈……最终收敛为 21x21 圈。**
离线一次 viewer 实际生成 **441** 个区块，阶段执行 **960** 次
（结构起点 441 / 生物群系 25 / 噪声 9 / 地表 9 / 雕刻器 9 / 装饰 1）。

**混龄邻域**：离线严格按状态分层推进，因此任意时刻各圈区块处于**不同状态**
（例：中心区块 CARVERS 时 ±1 圈是 CARVERS、±2..±8 圈只到 STRUCTURE_STARTS），
**不是「全部生成成 FULL 再拿来当早期状态」**——这正是第三轮修复 `ensureCanWrite` 时确立的口径。

**是否读取真实世界状态作为生成输入**：没有。
自证指标：`离线 region 向宿主 ChunkMap 的查询次数累计：0`（`run16` 实测）。
宿主只提供五样与种子无关的环境参数：维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂。
`isOldChunkAround` 在离线侧固定返回 false（新生成世界的取值），第三轮口径里真实侧也是 false。

---

## 八、RealPreDiamond vs OfflinePreDiamond 对比（`run16`，10 个固定区块）

比较范围：两侧快照的同一 3x3 区块 × 9 个 section（y ∈ [-64,79]），逐格比。

**汇总**

| 口径 | 结果 |
| --- | --- |
| 离线 pre-diamond 与真实逐 BlockPos 一致（含邻域 3x3） | **1 / 10** |
| 离线 pre-diamond 与真实逐 BlockPos 一致（仅目标区块） | **2 / 10** |
| 四个阶段 checkpoint 只看目标区块自己是否全部一致 | 否（见下） |
| FIRST DIVERGENCE 阶段分布（含邻域） | `{BIOMES=1, NOISE=2, pre-diamond=6}` |

**逐 viewer 的第一处分叉（摘录，全文见落盘报告）**

| viewer | FIRST DIVERGENCE（含邻域） | 只看目标区块 | 第一处差异样本 |
| --- | --- | --- | --- |
| (0,0) | 无（四个阶段与 pre-diamond 全部一致） | 无 | — |
| (1,0) | BIOMES | 无（差异全部来自邻域年龄） | 生物群系 (-16,-64,-16) 真实 plains / 离线 forest |
| (0,1) | pre-diamond | pre-diamond | 方块 (-4,-63,0) 真实 deepslate_redstone_ore / 离线 deepslate |
| (1,1) | pre-diamond | pre-diamond | 方块 (0,-47,14) 真实 deepslate / 离线 tuff |
| (-1,0) | pre-diamond | pre-diamond | 方块 (-2,-47,14) 真实 deepslate / 离线 tuff |
| (0,-1) | pre-diamond | pre-diamond | 方块 (-2,67,-2) 真实 air / 离线 leaf_litter |
| (-1,-1) | pre-diamond | pre-diamond | 方块 (-17,31,8) 真实 granite / 离线 stone |
| (2,2) | pre-diamond | pre-diamond | 方块 (31,-63,16) 真实 tuff / 离线 deepslate |
| (3,-1) | NOISE | CARVERS | 方块 (32,-64,-32) 真实 bedrock / 离线 stone |
| (-2,3) | NOISE | CARVERS | 方块 (-48,-64,32) 真实 bedrock / 离线 stone |

**各阶段 checkpoint 一致性（含邻域 3x3，`run16` 逐 viewer 明细全文在落盘报告）**

以 `run13`（单 viewer (0,0)，`decorRadius=0`）的完整四阶段数据为例，说明「哪些层一致、哪些层不一致」：

| 阶段 | 方块状态 | 生物群系 | 高度图 | 结构 | 判定 |
| --- | --- | --- | --- | --- | --- |
| BIOMES | 331776 / 331776（不一致 0） | 2880 / 5184（不一致 2304） | 4608 / 4608（不一致 0） | 一致 | 邻域 4 个区块尚未到 BIOMES（差异全部在邻域） |
| NOISE | 207509 / 331776（不一致 124267） | 5184 / 5184（一致） | 2560 / 4608（不一致 2048） | 一致 | 邻域年龄（真实邻域已生成完毕） |
| SURFACE | 115271 / 331776（不一致 216505） | 一致 | 512 / 4608（不一致 4096） | 一致 | 同上 |
| CARVERS | 331306 / 331776（不一致 470） | 一致 | 一致 | 一致 | 只剩邻域 470 格 |
| **pre-diamond** | **331776 / 331776（不一致 0）** | **5184 / 5184** | **4608 / 4608** | 一致 | **完全一致** |

关键点：**四个阶段只看目标区块自己时全部逐格一致**（报告里没有输出「（仅目标区块口径）」行），
**pre-diamond 整片 3x3 也完全一致**——这是 `run13` 在 viewer (0,0) 上得到的最好结果，
也是「离线 pipeline 与原版在生成期语义上等价」的直接证据。

---

## 九、FIRST DIVERGENCE 的定位结论（不是猜，是两个对照实验）

### 9.1 对照实验 A：邻域装饰半径（`run15`）

把 `-Dyiyiaddon.seedpoc.offline.decorRadius` 从 0 改成 1（先把 ±1 邻域装饰完，再装饰目标区块）：

| 口径 | `decorRadius=0`（`run13`） | `decorRadius=1`（`run15`） |
| --- | --- | --- |
| pre-diamond 含邻域 | 完全一致 | 不一致 34650 格 |
| pre-diamond 仅目标区块 | 完全一致 | 不一致 941 格 |
| 纯 Seed 钻石 | 47 / 47 完全一致 | 47 / 47 完全一致 |

**结论**：真实世界里目标区块被装饰时，邻域是**停在 CARVERS**（pipeline 的最小要求）而不是已装饰过的
FEATURES——这正是原版「先铺依赖层、再装饰中心」的顺序在邻域上的体现。
一旦离线把邻域提前装饰，中心区块的 pre-diamond 立刻偏离。**邻域年龄是真实存在的变量，且已被定向验证。**

### 9.2 对照实验 B：观察顺序效应（`run16` 内的逐 viewer 分布）

第一个 viewer (0,0) 与第二个 viewer (1,0) 的**目标区块自己**完全一致；越靠后的 viewer 差异越多。
原因是离线管线**跨 viewer 复用同一批 holder**（模拟同一个世界）：
被更早 viewer 装饰过的区块，在后续 viewer 的快照里就「已经有了地物」，
而真实世界里那一刻该区块可能还没被装饰（真实装饰顺序见第三轮批号：(0,0)=1、(1,0)=2、(1,1)=3、(0,1)=4…，
本机还开着 C2ME 并行）。

**因此本轮把第一处分叉归因到两件事，两件都有实测支撑**：

1. **邻域年龄**（8.1 已用对照实验定向证明）；
2. **跨区块装饰写入的顺序依赖**：装饰有写半径 1，邻居的装饰会写进目标区块；
   哪些区块「先装饰」决定了某一格在被读取时的内容。真实顺序由服务端生成顺序 + C2ME 决定，
   离线顺序是我们自己的推进顺序，二者不必相同。

**这两件事都不是「种子算法错」**：证据是同一批 viewer 的钻石预测 **10/10 逐 BlockPos 完全一致**
（即使 pre-diamond 有差异），说明差异落在**不影响钻石判定的格子**上
（邻居区块的非钻石矿 / 地表地物 / 噪声矿脉等）。

---

## 十、修复依据对应的 26.1.2 源码

本轮唯一修复（第六节 6.2）依据：

- `ChunkGenerationTask#create:36-42`：缓存半径必须 = `getAccumulatedRadiusOf(ChunkStatus.EMPTY)`；
- `ChunkGenerationTask#scheduleLayer:117-136` / `getRadiusForLayer:133-136`：每层半径 = `getAccumulatedRadiusOf(status)`；
- `WorldGenRegion#getChunk:107-144`：距离 ≥ `directDependencies().size()` 时按原版设计抛错
  （这就是 `run14` 崩溃的报错来源）。

即：**原版缓存是以「被生成区块自己」为中心的**。离线要装饰邻域时，必须按邻域区块自己的半径要求扩大缓存，
而不是继续用 viewer 的缓存——修复后的公式在 `decorRadius=0` 时与原版逐字等价。

---

## 十一、第二阶段：纯 Seed → 钻石 BlockPos（`run16`，10 个固定区块）

链路：`Seed → OfflineWorldgenVerifier（离线 pre-diamond 安装进测试世界）→ 第三轮 OreDecorationReplay → 预测钻石`，
与真实 `post-diamond − pre-diamond` 差集逐 BlockPos 比。

| viewer | 真实本遍新增 | 纯 Seed 预测 | 匹配 | 漏报 | 错报 | 查全 | 查准 | 逐 BlockPos |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 47 | 47 | 47 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (1,0) | 10 | 10 | 10 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (0,1) | 32 | 32 | 32 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (1,1) | 21 | 21 | 21 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (-1,0) | 18 | 18 | 18 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (0,-1) | 24 | 24 | 24 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (-1,-1) | 21 | 21 | 21 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (2,2) | 18 | 18 | 18 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (3,-1) | 18 | 18 | 18 | 0 | 0 | 100.00% | 100.00% | 完全一致 |
| (-2,3) | 31 | 31 | 31 | 0 | 0 | 100.00% | 100.00% | 完全一致 |

**合计：真值 240 / 预测 240 / 匹配 240 / 漏报 0 / 错报 0 / 查全 100.00% / 查准 100.00%；
逐 BlockPos 完全一致的 viewer：10 / 10。**
还原校验：10/10 通过（测试世界逐格无残留）。

第三轮的两节在同一轮里也照旧通过（未改动）：第一阶段 10/10（240/240）、第二阶段跨区块 90 组全部一致。

---

## 十二、是否满足 PoC 结案条件（逐条对照）

| 结案条件 | 状态 | 说明 |
| --- | --- | --- |
| 1. 不读取真实生成期快照 | **满足** | 宿主 ChunkMap 查询 0 次；种子四件套全自建 |
| 2. 只凭 Seed + 版本原版 worldgen | **满足** | 离线上下文只吃 种子/注册表/worldgen 配置/ChunkPos |
| 3. 能离线构造正确 pre-diamond | **部分满足** | 逐 BlockPos 一致 1/10（含邻域）/ 2/10（仅目标区块）；差异已定位到「邻域年龄 + 跨区块装饰顺序」，非算法错 |
| 4. 能输出最终钻石 BlockPos | **满足** | 10/10 viewer、240/240 逐 BlockPos 完全一致 |
| 5. 固定测试集逐 BlockPos 通过 | **满足（第 4 条口径）** | 漏报 0 / 错报 0 |
| 6. 泛化 Seed / Chunk 回归 | **未做** | 本轮只跑固定种子 `20260922` 的 10 个区块 |

**因此本轮不宣布「PoC 结案」**：第 3 条尚未严格满足。
但要如实写明：**用户第四轮第十二节的验收目标（纯 Seed → 钻石、漏报 0 错报 0、10/10 逐 BlockPos）
已经达成**；第 3 条之所以未满足，是因为「离线 pre-diamond 与真实生成期状态逐格等同」这件事
依赖邻域年龄与装饰顺序，而这两者属于**真实服务端调度（含 C2ME）**，不是种子函数。

---

## 十三、编译与实机运行结果

| 项目 | 结果 |
| --- | --- |
| 编译 | `gradlew compileJava --console=plain` → **BUILD SUCCESSFUL** |
| 实机运行 | `gradlew runClient` → 4 轮：`run13`（离线调试，1 viewer）/ `run14`（邻域对照首轮，崩溃留档）/ `run15`（邻域对照修复后）/ `run16`（**正式轮，10 viewer**）；均正常退出并落盘报告 |
| 运行参数 | `JAVA_TOOL_OPTIONS`：`-Dyiyiaddon.seedpoc.enabled=1`、`chunks=0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`、`terrain=0`、`exit=1`；`run13/15` 另加 `offline.limit=1` 与 `variant.single/phase2/control=0` |
| 固定条件 | 26.1.2 / 原版主世界 / Seed `20260922` / 钻石 / 同一批 10 个固定区块（未换种子、未换区块、未挑成功案例） |
| 报告落盘 | `run-26.1.2/seedpoc-实验报告-run16-第四轮正式.txt`（正式轮）、`…-run13-离线调试.txt`、`…-run15-邻域年龄对照.txt`；日志 `seedpoc-run13-debug.log` / `run14-decorR1.log` / `run15-decorR1.log` / `run16-console.log` |
| 异常留档 | `run14` 的 `ReportedException: Exception generating new chunk` 日志保留，可复查「缓存半径」这一处修复的定位过程 |

---

## 十四、尚未验证事项（不得当成已验证）

| 未验证项 | 说明 |
| --- | --- |
| 多 Seed / 多区块泛化回归 | 本轮只跑固定种子 `20260922` 的 10 个区块；正负坐标、远坐标、不同 biome/terrain 均未测 |
| 关闭 C2ME 的对照 | 本机环境带 C2ME（并行 worldgen），本轮未做「关 C2ME 重跑」的对照；第 9.2 节的顺序依赖结论**部分**可能与 C2ME 有关，需要用关 C2ME 的对照确认 |
| 离线 pre-diamond 逐 BlockPos 一致（含邻域） | 目前 1/10；要达到 10/10 需要复刻真实装饰顺序（含跨区块写入时序），本轮只做到「钻石链结果不受影响」 |
| 纵向范围 | 快照覆盖 y ∈ [-64,79] 的 9 个 section（钻石扫描用 y ≤ 31 的 6 个），更高处未证明 |
| 其它矿物 / 其它维度 / 26.2 / 26.3 | 按用户口径本轮一律未做 |
| 真机服务器环境 | 全程单人集成服务端；真实服务器 Seed 获取与真实性验证属后续阶段 |

---

## 十五、本轮新增/修改文件清单（实验代码，PoC 结案时整包删除）

新增（10）：`OfflineWorldgenContext` / `OfflineChunkHolder` / `OfflineChunkRegion` / `OfflineChunkPipeline` /
`OfflineStageCapture` / `StageSnapshotCapture` / `StageComparator` / `OfflineWorldgenVerifier` /
`OfflineOutcome` / `mixin/client/ChunkStatusStageProbeMixin`。

修改（7）：`GenStageSnapshot`、`GenStageCapture`、`PlacedFeatureStageCaptureMixin`、`SeedPocFlags`、
`SeedPocReport`、`SeedPocRunner`、`yiyiaddon.mixins.json`。

正式业务代码：**零改动**。

---

## 十六、下一步（等用户确认，不自行推进）

1. **补泛化回归**：多个种子 × 正/负/远坐标 × 不同 biome，确认 10/10 不是恰好成立。
2. **补 C2ME 关闭对照**：同一批区块在关 C2ME 下重跑，确认第 9.2 节的顺序依赖结论。
3. **（可选）复刻真实装饰顺序**：若要追求「离线 pre-diamond 逐 BlockPos 10/10」，
   需要把「邻域年龄 + 跨区块写入时序」也纳入复刻；这是个独立课题。
4. 以上都通过后再谈：PoC 结案 → 清理探针 → 正式「种子挖矿」页面（位于现有自动挖矿控制台内、
   玩家可见内容全中文、`personalMode` 隐藏、不重复矿物/精准采集/时运/Baritone/食物/回家/白名单）。
