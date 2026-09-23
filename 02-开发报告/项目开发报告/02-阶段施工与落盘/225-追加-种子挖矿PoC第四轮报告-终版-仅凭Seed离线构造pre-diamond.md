# 225 · 追加 · 《种子挖矿 PoC 第四轮报告》（终版 · 仅凭 Seed 离线构造 pre-diamond）

日期：2026-09-23
阶段：种子挖矿 —— **第四轮「离线构造 pre-diamond，纯 Seed 推钻石」终版**。
本报告取代 [224](224-追加-种子挖矿PoC第四轮报告-仅凭Seed离线构造pre-diamond.md) 的临时结论（224 写于 `run16`，之后又跑了
`run21` / `run22~25` / `run26` / `run27~28`，结论与数字都已更新，**以本报告为准**）。

本轮要回答的唯一问题：

> 不读取真实生成期 pre-diamond 快照的情况下，客户端能不能仅凭
> 「Minecraft 26.1.2 + 世界 Seed + 维度 + ChunkPos + 当前版本原版注册表/worldgen 配置」，
> 离线构造出与原版真实生成过程一致的 pre-diamond 输入状态，并由此推出钻石 BlockPos？

**验证方式：实机验证**（`gradlew runClient`，固定种子 `20260922`、沿用第三轮相同的 10 个固定区块
`0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`）。

---

## 零、结论速览（含 §17 二十项索引）

| §17 必答项 | 本报告节 | 结论 |
| --- | --- | --- |
| 1. 26.1.2 调用链 | 第一节 | 已按本线源码逐层列出（ChunkPyramid → ChunkGenerationTask → ChunkStep → ChunkStatusTasks） |
| 2. 用到的真实原版类 | 第二节 | 生物群系源 / 生成器 / RandomState / 结构状态 / 七个阶段实现 / WorldGenRegion 全部调原版 |
| 3. 新增 Java 文件与职责 | 第三节 | 离线 5 类 + 比较 3 类 + 真实侧 1 探针，全部在 `com.yiyiaddon.dev.seedpoc` |
| 4. 是否用 AW / Mixin / Accessor | 第四节 | **未新增 AW、未用 Accessor/Invoker**；探针 Mixin 2 处（1 新增 + 1 复用改分流） |
| 5. 离线构造所需邻域 | 第五节 | 21×21 圈（441 区块）；逐层半径 empty 10 / struct_starts 10 / struct_refs 2 / biomes 2 / noise 1 / surface 1 / carvers 1 / FEATURES 0 |
| 6. 每圈 status | 第五节 | 离线严格分层（混龄）；真实侧是**已跑完的混龄邻域**（含 `initialize_light` / `features`） |
| 7. 是否读真实世界状态当输入 | 第六节 | **没有**：离线 region 向宿主 ChunkMap 查询次数 = 0 |
| 8. Real vs Offline pre-diamond | 第七节 | 含邻域 **0/10**；仅目标区块 **1/10**（逐 BlockPos，给第一处坐标与两侧取值） |
| 9. 逐阶段 checkpoint 一致性 | 第八节 | BIOMES 10/10、NOISE 仅目标 10/10、SURFACE 仅目标 10/10、CARVERS 仅目标 1/10、pre-diamond 1/10；不可判定 0 |
| 10. FIRST DIVERGENCE 阶段 | 第九节 | 含邻域口径 **全部 10 个 viewer 都在 NOISE**；仅目标区块口径多为 CARVERS |
| 11. 修复依据源码 | 第十节 | 本轮只修**装置**（比较器三分 / 每 viewer 独立世界 / 生物群系可比判据），依据 `ChunkStatus#isOrAfter`、`ChunkGenerationTask` 半径、`WorldGenRegion#getChunk` |
| 12. OfflinePreDiamond 是否逐 BlockPos 一致 | 第七节 | **否**（含邻域 0/10、仅目标 1/10） |
| 13. 接 replay 后纯 Seed 钻石结果 | 第十一节 | **口径一 10/10（240/240，漏报 0 错报 0）**；口径二 7/10（243/232，漏报 11 错报 0） |
| 14. 10 个固定区块统计 | 第十一、十二节 | 见逐 viewer 表 |
| 15. 泛化回归 | 第十三节 | seed `12345` × 4 区块（海洋 / 石岸 / 雪针叶林）：口径一 2/4、口径二 2/4；**发现系统性偏差** |
| 16. C2ME 关闭对照 | 第十四节 | 第三轮重放口径**不变（10/10、240/240、232/232）**；离线构造因 ModernFix NPE **未取得** |
| 17. 编译结果 | 第十五节 | `gradlew compileJava --console=plain -q` → **EXIT=0** |
| 18. runClient 结果 | 第十五节 | 12 轮实机：run14 崩溃留档，其余 11 轮正常退出并落盘报告 |
| 19. 未验证事项 | 第十六节 | 逐条列出 |
| 20. 是否满足结案条件 | 第十七节 | **不满足**（第 3、5、6 条未达标）→ **本轮不宣布结案** |

一句话：**「纯 Seed → 钻石坐标」在第三轮口径（同一次装饰内的贡献）下 10/10 逐 BlockPos 通过；
但「离线 pre-diamond 与真实生成期状态逐格等同」没做到（含邻域 0/10），且换成「真实世界最终钻石」口径后
有 11 格漏报——漏报来源已定向取证到「邻域先装饰、按写半径 1 写进目标区块」，属真实服务端调度顺序，不是种子函数。**

---

## 一、26.1.2 原版 Chunk generation 调用链（本轮实际依赖的那一条）

以本机 `01-开发参考库/Minecraft-26.1.2-Mojang源码` 为准（行号即该目录下文件行号）：

```
ChunkMap#applyStep:620-646            调度一次「某状态」
  └─ ChunkGenerationTask#create:36-42 缓存半径 = ChunkPyramid.GENERATION_PYRAMID
  │                                    .getStepTo(targetStatus).getAccumulatedRadiusOf(ChunkStatus.EMPTY)
  │                                    （对 FEATURES 实测 = 10 → 21x21 区块）
  └─ ChunkGenerationTask#scheduleLayer:117-136
       逐状态铺层，每层半径 = getStepTo(targetStatus).getAccumulatedRadiusOf(status)
       铺层顺序 = EMPTY → STRUCTURE_STARTS → STRUCTURE_REFERENCES → BIOMES
                 → NOISE → SURFACE → CARVERS → FEATURES
  └─ ChunkStep#apply:21-28             调用 ChunkStatusTasks 对应方法
  └─ ChunkStep#completeChunkGeneration:30-40   ProtoChunk#setPersistedStatus(目标状态)
  └─ ChunkMap#createEmptyChunk:594-597 new ProtoChunk(pos, UpgradeData.EMPTY, level,
                                       level.palettedContainerFactory(), null)

ChunkStatusTasks（每个状态具体做什么）：
  generateStructureStarts:40-53    → ChunkGenerator#createStructures:465+
  generateStructureReferences:62-69 → ChunkGenerator#createReferences:599-631（读 ±8 区块的起点引用）
  generateBiomes:71-77             → ChunkGenerator#createBiomes:117-124
  generateNoise:79-99              → ChunkGenerator#fillFromNoise:633（+ BelowZeroRetrogen 两步修补）
  generateSurface:101-108          → ChunkGenerator#buildSurface:424
  generateCarvers:110-124          → Blender#addAroundOldChunksCarvingMaskFilter:320
                                     + ChunkGenerator#applyCarvers:126（带 level.getSeed()）
  generateFeatures:126-140         → Heightmap#primeHeightmaps
                                     + ChunkGenerator#applyBiomeDecoration:318-412
                                     + Blender#generateBorderTicks:265

区块读写语义（第三轮已证明按它复刻才一致）：
  WorldGenRegion#ensureCanWrite:229-259   写半径 = ChunkStep#blockStateWriteRadius（FEATURES = 1）
  WorldGenRegion#getChunk:107-144         依赖半径 = ChunkStep#directDependencies
  WorldGenRegion#getHeight:397-399        = chunk.getHeight(...) + 1 = getFirstAvailable
```

**FEATURES 步的逐圈依赖（实机打印）**：

```
FEATURES directDependencies = [CARVERS, CARVERS, STRUCTURE_STARTS×7]（半径 0..8）
其中 半径 0..1 至少 CARVERS，半径 2..8 至少 STRUCTURE_STARTS
```

---

## 二、离线 worldgen 实际使用的原版类（与自建项）

| 用途 | 原版类/方法（**未重写**） |
| --- | --- |
| 生物群系源 | `MultiNoiseBiomeSource.createFromPreset(minecraft:overworld 预设)` |
| 区块生成器 | `new NoiseBasedChunkGenerator(BiomeSource, Holder<NoiseGeneratorSettings>)` |
| 随机状态 | `RandomState.create(注册表, NoiseGeneratorSettings.overworld, 种子)` |
| 结构状态 | `ChunkGenerator#createState(结构集, RandomState, 种子)`（= `ChunkGeneratorStructureState.createForNormal`） |
| 逐状态驱动 | `ChunkPyramid.GENERATION_PYRAMID` + `ChunkStep#getAccumulatedRadiusOf` / `directDependencies` |
| 阶段实现 | `createStructures` / `createReferences` / `createBiomes` / `fillFromNoise` / `buildSurface` / `applyCarvers` / `applyBiomeDecoration` |
| 区域读写语义 | `WorldGenRegion`（子类化）、`Blender.of` / `addAroundOldChunksCarvingMaskFilter` / `generateBorderTicks` |
| 区块对象 | `new ProtoChunk(pos, UpgradeData.EMPTY, level, palettedContainerFactory, null)` |
| 状态推进 | `ProtoChunk#setPersistedStatus` / `ChunkAccess#getPersistedStatus` |
| 高度图 | `Heightmap.primeHeightmaps`、`ChunkAccess#getHeight` |

**自建（这就是本轮要证的东西）**：`OfflineWorldgenContext`（上下文）、`OfflineChunkHolder`（区块持有者）、
`OfflineChunkRegion`（区域视图）、`OfflineChunkPipeline`（驱动器）、`OfflineStageCapture`（离线捕获）。

离线链路的输入严格限定为：**种子 / 版本 / 注册表 / NoiseGeneratorSettings / 维度与 worldgen 配置 / ChunkPos**
（宿主世界只提供五样与种子无关的环境参数：维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂）。

---

## 三、新增 Java 文件与职责

新增（10 个，全部在 `src/main/java/com/yiyiaddon/dev/seedpoc/`，除 Mixin）：

| 文件 | 职责 |
| --- | --- |
| `OfflineWorldgenContext.java` | 离线上下文：注册表 + 种子 → 自建 BiomeSource / NoiseBasedChunkGenerator / RandomState / ChunkGeneratorStructureState + 环境宿主（只取与种子无关参数） |
| `OfflineChunkHolder.java` | `GenerationChunkHolder` 子类：自存「各状态 → 区块」，覆盖读方法（原版读方法均 public 非 final，故**不需要 Accessor**），`getTicketLevel()` 用 `ChunkLevel.byStatus(FULL)` |
| `OfflineChunkRegion.java` | `WorldGenRegion` 子类：只覆盖 5 处**种子相关读取**（`getSeed`/`getRandom`/`getBiomeManager`/`getUncachedNoiseBiome`/`isOldChunkAround`），其余沿用原版；并对宿主 `ChunkMap` 查询计数自证 |
| `OfflineChunkPipeline.java` | 驱动器：按原版半径规则逐层铺 `EMPTY…CARVERS` 再装饰目标区块；每 viewer 新建独立世界（`freshWorldPerViewer`，默认开；`offline.reuse=1` 保留旧口径对照） |
| `OfflineStageCapture.java` | 离线侧 pre-diamond 捕获（与真实侧同一捕获点）+ 「离线调用不进真实探针」分流器 |
| `StageSnapshotCapture.java` | 真实侧与离线侧**共用**的阶段快照采集器（方块 / 两张 WG 高度图 / 生物群系 / 结构 / **section 是否存在** / **各槽位已跑到的最远状态**） |
| `StageComparator.java` | 逐 BlockPos 比较器：把结果三分——**快照缺失（不可判定）/ 覆盖缺失（不可比）/ 值不同**；输出总格数、一致、不一致、**第一处不同坐标与两侧取值**、差异成对 Top3、跨阶段时间线、钻石影响判别；另有「仅目标区块」口径 |
| `OfflineWorldgenVerifier.java` | 单 viewer 主流程：离线构造 → 逐阶段 checkpoint 比较 → 用**离线**快照装进测试世界 → 跑第三轮钻石链 → 双口径比对 |
| `OfflineOutcome.java` | 第四轮验收数据载体（双口径判定 + FIRST DIVERGENCE + 不可判定数） |
| `mixin/client/ChunkStatusStageProbeMixin.java` | 真实侧逐阶段探针：`ChunkStatusTasks` 的 `generateBiomes` / `generateNoise` / `generateSurface` RETURN |

修改：`GenStageSnapshot`（加生物群系 / 结构 / section 存在性 / 槽位状态）、`GenStageCapture`（抽公用采集 +
真实侧逐阶段快照 + 离线分流 + 真实装饰顺序摘要）、`PlacedFeatureStageCaptureMixin`（HEAD/RETURN 对称分流）、
`SeedPocFlags`（离线开关，含 `offline.reuse`）、`SeedPocReport`（双口径汇总 + 逐阶段 checkpoint 汇总）、
`SeedPocRunner`（第四轮编排 + 离线世界口径备注）。`yiyiaddon.mixins.json` 注册新 Mixin。

**包结构说明**：离线 / 比较 / 验收类都放在 `com.yiyiaddon.dev.seedpoc` 同一包内，因为要复用第二、三轮的
包内载体（`GenStageSnapshot` / `DiamondScanner` / `RegionStateLedger` / `OreDecorationReplay`）；
把载体改 public 会扩大暴露面（第 163~165 条）。

---

## 四、是否使用 AccessWidener / Mixin / Accessor

| 手段 | 是否使用 | 为什么 |
| --- | --- | --- |
| AccessWidener | **未新增** | 离线链路需要的都是 public/可覆盖成员 |
| Accessor / Invoker Mixin | **未使用** | `GenerationChunkHolder` 读方法（`getChunkIfPresentUnchecked`/`getChunkIfPresent`/`getPersistedStatus`/`getLatestChunk`/`getLatestStatus`）都是 public 非 final，**子类覆盖即可**；私有 `completeFuture` 根本不需要（驱动器自己调原版 `ChunkGenerator` 方法、自己记状态） |
| 探针 Mixin | **2 处** | ① `ChunkStatusStageProbeMixin`（真实侧 BIOMES/NOISE/SURFACE checkpoint，注入 package-private 静态方法只能靠 Mixin）；② `PlacedFeatureStageCaptureMixin` 加「离线调用分流」（否则离线生成会污染真实 oracle） |

即：**暴露面只有两个只读探针 + 一处分流判断**，没有为方便大面积改 Minecraft 访问权限。

---

## 五、离线构造需要多大邻域 / 每圈 status

**离线缓存与逐层半径（`run26` 实机打印）**：

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

即：目标区块跑 FEATURES 需要 ±1 圈至少 CARVERS、±8 圈至少 STRUCTURE_STARTS；每圈 STRUCTURE_STARTS 又各自
要求 ±8 圈…… 最终收敛为 21×21。`run26` 实测：每 viewer 一个新世界，10 个 viewer 合计
**离线阶段执行 9600 次**（结构起点 4410 / 生物群系 250 / 噪声 90 / 地表 90 / 雕刻器 90 / 装饰 10）。

**混龄邻域（离线是严格分层的）**：离线任意时刻各圈处在不同状态，例如中心区块 CARVERS 时，
±1 圈 CARVERS、±2..±8 圈只到 STRUCTURE_STARTS。**不是「全部生成成 FULL 再拿来当早期状态」。**

**真实侧邻域年龄（`run26` viewer `(-2,3)` 的 pre-diamond checkpoint 原样打印）**：

```
邻域年龄（各槽位已跑到的最远状态）
  离线 [carvers, carvers, carvers, carvers, carvers, carvers, carvers, carvers, carvers]
  真实 [initialize_light, carvers, carvers, initialize_light, carvers, carvers,
        initialize_light, features, carvers]
```

真实侧邻域已经出现 `initialize_light` / `features`——即**真实世界在被捕获的那一刻，邻域早已生成完毕
（含地物与点灯）**，而离线把邻域严格停在 CARVERS（原版 pipeline 的最小要求）。这是本轮第一处分叉的
直接来源，也是「离线不可能与真实生成期逐格等同」的结构性原因。

---

## 六、是否读取真实世界状态作为生成输入

**没有。** 自证指标（`run16` / `run21` / `run26` 三次都为 0）：

```
离线 region 向宿主 ChunkMap 的查询次数累计：0
```

宿主只提供五样与种子无关的环境参数（维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂）；
`isOldChunkAround` 在离线侧固定返回 false（新生成世界的取值，第三轮口径里真实侧也是 false）。
真实 pre-diamond 快照仅在**第一阶段比较**与**第二阶段判定**里当 oracle 使用，**安装进测试世界的是离线快照**
（`OfflineWorldgenVerifier` 里 `ledger.install(offlinePre)`）。

---

## 七、RealPreDiamond vs OfflinePreDiamond（`run26`，10 个固定区块）

比较范围：两侧快照的同一 3×3 区块 × 9 个 section（y ∈ [-64,79]，每区块 36864 格），逐格比。

| 口径 | 结果 |
| --- | --- |
| 逐 BlockPos 一致（含邻域 3×3） | **0 / 10** |
| 逐 BlockPos 一致（仅目标区块） | **1 / 10**（唯一通过的是 viewer `(0,0)`） |
| 不可判定项（两侧任一快照缺失） | **0** |

**第一处差异样本（viewer `(-2,3)`，逐条来自报告）**：

```
含邻域口径  第一处分叉在 NOISE 阶段：第一处方块差异 @-48,-64,48：真实 air / 离线 stone
仅目标口径  第一处分叉在 CARVERS 阶段：第一处方块差异 @-17,-60,52：真实 tuff / 离线 deepslate
  同一坐标的跨阶段时间线｜离线 BIOMES=air NOISE=stone SURFACE=deepslate CARVERS=deepslate pre-diamond=deepslate
  同一坐标的跨阶段时间线｜真实 BIOMES=air NOISE=stone SURFACE=deepslate CARVERS=tuff     pre-diamond=tuff
  差异成对（真实→离线）共 16 种，最多的三对：andesite → stone 285 格；tuff → deepslate 133 格；dirt → stone 54 格
```

即：**真实侧在 SURFACE 之后、CARVERS 快照这一刻，目标区块里已经多了 `tuff` / `andesite` / `gravel`
这类非噪声地形方块**——按写半径 1，这些只能来自**已经装饰完的邻域**。

**对钻石链的影响判别（同 viewer，`run26`）**：

```
钻石纵向范围内（y ≤ 31）的差异格：
  可替换方块之间 463 格（不影响矿石放置：双方都过 target 判定、随机数消耗次数相同）
  不可替换差异（保守判据：只要有一侧不可替换就算）67 格（这类格子才可能改变随机流）
  样本：(-25,-48,48) 真实 gravel / 离线 deepslate；( -24,-48,48) 真实 gravel / 离线 deepslate；
        (-23,-48,48) 真实 gravel / 离线 deepslate
```

**装饰顺序证据（同 viewer）**：

```
真实装饰顺序：中心批号 = 53；邻域 (-3,2)=26 (-3,3)=36 (-3,4)=43 (-2,2)=19
              (-2,4)=42 (-1,2)=16 (-1,3)=32 (-1,4)=41 —— 全部「先于中心装饰 → 可写进中心」
中心区块生物群系（y=63）：minecraft:forest
```

`run26` 的 10 个 viewer 中心批号依次为 44..53，**抽查处邻域批号全部小于中心批号**：
`(0,0)` 中心 44 / 邻域 2..9；`(-1,0)` 中心 48 / 邻域 6..46；`(-2,3)` 中心 53 / 邻域 16..43。

---

## 八、逐阶段 checkpoint 一致性（`run26` 汇总）

| 阶段 | 含邻域 3×3 逐格一致 | 仅目标区块逐格一致 | 方块不符 / 总格 | 生物群系不符 / 总数（不可比） |
| --- | --- | --- | --- | --- |
| BIOMES | **10 / 10** | **10 / 10** | 0 / 3317760 | 0 / 51840（不可比 43200） |
| NOISE | 0 / 10 | **10 / 10** | 1217018 / 3317760 | 0 / 51840（不可比 0） |
| SURFACE | 0 / 10 | **10 / 10** | 1429362 / 3317760 | 0 / 51840（不可比 0） |
| CARVERS | 0 / 10 | 1 / 10 | 131548 / 3317760 | 0 / 51840（不可比 0） |
| pre-diamond | 1 / 10 | 1 / 10 | 121431 / 3317760 | 0 / 51840（不可比 0） |
| 不可判定项合计 | **0** | | | |

两条关键读数：

1. **NOISE / SURFACE 两层的「仅目标区块」是 10/10 逐格一致**——离线自己跑出来的噪声地形与地表，
   与原版真实生成**逐 BlockPos 相同**。这正面证明「离线复用原版 pipeline」这一策略本身是成立的。
2. **CARVERS 的「仅目标区块」掉到 1/10**——目标区块自己在 CARVERS 这一刻已经含了邻域写进来的方块
   （第七节的样本与时间线），而离线此刻邻域还停在 CARVERS、什么都没写。

生物群系的「不可比 43200」是 BIOMES 阶段的正常现象：尚未跑到 BIOMES 的槽位其 section 生物群系容器是
调色板工厂默认值（假生物群系），比较器按 `ChunkStatus#isOrAfter(BIOMES)` 判为**不可比**，既不算一致也不算分歧。

---

## 九、FIRST DIVERGENCE 定位（不是猜）

| 口径 | FIRST DIVERGENCE 阶段分布（`run26`） |
| --- | --- |
| 含邻域 3×3 | **`{NOISE = 10}`**（10 个 viewer 全部在 NOISE 就分叉） |
| 仅目标区块 | 多数为 CARVERS（例：viewer `(-2,3)` 为 CARVERS） |

**分叉性质（有对照实验支撑，不是推测）**：

- 含邻域口径：分叉点在 `±8` 圈的 STRUCTURE_STARTS 邻域（`@-48,-64,48` 属邻域区块）。离线邻域停在
  STRUCTURE_STARTS（只有石头），真实邻域**早已跑完并带点灯与地物**（第五节邻域年龄行）。
- 仅目标口径：分叉点在目标区块里「被邻域写进来的非地形方块」（`tuff` / `andesite` / `gravel`）。
- **两条都不是「种子算法错」**：同一批 viewer 的 NOISE / SURFACE「仅目标区块」是 10/10 逐格一致，
  钻石链结果在口径一下 10/10 逐 BlockPos 一致。

**对照实验（`run25` vs `run24`，seed `12345`）**：把离线装饰半径从 0 改成 1（邻域先装饰再装饰中心）：

| 口径 | `decorRadius=0`（`run24`） | `decorRadius=1`（`run25`） |
| --- | --- | --- |
| 口径一 逐 BlockPos 一致 | 2 / 4 | 3 / 4 |
| 口径一 合计 | 真值 112 / 预测 119 / 匹配 112 / 漏报 0 / 错报 7 / 查准 94.12% | 真值 106 / 预测 107 / 匹配 98 / 漏报 8 / 错报 9 / 查准 91.59% |
| 口径二 逐 BlockPos 一致 | 2 / 4 | 3 / 4 |
| 口径二 合计 | 真值 108 / 预测 116 / 匹配 108 / 漏报 0 / 错报 8 / 查准 93.10% | 真值 103 / 预测 104 / 匹配 95 / 漏报 8 / 错报 9 / 查准 91.35% |

**结论**：固定模型「邻域一律先装饰」不成立——`decorRadius=1` 让某些 viewer 变好、同时让某些变坏。
「谁先装饰」必须按真实批号复刻，而真实批号由服务端调度（本机还开着 C2ME 并行）决定，
**不属于种子函数**。

---

## 十、修复依据对应的 26.1.2 源码

本轮**没有修生成器**（第三节离线链路一处未改），只修了**装置**三处，逐处给源码依据：

| 装置修复 | 现象 | 依据 |
| --- | --- | --- |
| ① 比较器改「缺失 / 不可比 / 值不同」三分 | `run16` 里 8 个 viewer 的阶段 checkpoint 全是 `0/0` 却判「完全一致」（假阳性） | `ChunkAccess#getPersistedStatus`：快照缺失必须单独归类，不能当 0 差异 |
| ② 离线每 viewer 新建独立世界 | 跨 viewer 复用同一批 holder，导致后续 viewer 的阶段快照取不到（`run16` 的假阳性来源之一） | `ChunkGenerationTask#create:36-42` 缓存半径以**被生成区块自己**为中心；一个 viewer = 一次「该区块第一次被装饰」 |
| ③ 生物群系可比判据加 `biomesReady` | BIOMES 阶段报 2304 格不一致，实际是未跑 BIOMES 的槽位容器里的工厂默认值 `plains` | `ChunkStatus#isOrAfter(ChunkStatus.BIOMES)`：未到 BIOMES 的 section 生物群系无意义 |

另有一处**装置**修复沿用上一轮（`run14` 崩溃 → `run15` 通过），依据：`ChunkGenerationTask#create:36-42` /
`scheduleLayer:117-136` 的半径规则 + `WorldGenRegion#getChunk:107-144` 在距离 ≥ `directDependencies().size()`
时按原版设计抛错——离线按 viewer 建缓存时，邻域区块自己的半径要求会超出该缓存，故缓存半径统一
`+ decorRadius`（`decorRadius=0` 时与原版逐字等价）。

---

## 十一、第二阶段：纯 Seed → 钻石 BlockPos（`run26`，10 个固定区块）

链路：`Seed → OfflineWorldgenVerifier（离线 pre-diamond 安装进测试世界）→ 第三轮 OreDecorationReplay → 预测钻石`。

**口径一（第三轮口径 · 同一次装饰内的贡献）**

| viewer | 真实本遍新增 | 纯 Seed 预测 | 匹配 | 漏报 | 错报 | 查全 | 查准 | 逐 BlockPos |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 47 | 47 | 47 | 0 | 0 | 100% | 100% | 完全一致 |
| (1,0) | 10 | 10 | 10 | 0 | 0 | 100% | 100% | 完全一致 |
| (0,1) | 32 | 32 | 32 | 0 | 0 | 100% | 100% | 完全一致 |
| (1,1) | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 完全一致 |
| (-1,0) | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 完全一致 |
| (0,-1) | 24 | 24 | 24 | 0 | 0 | 100% | 100% | 完全一致 |
| (-1,-1) | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 完全一致 |
| (2,2) | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 完全一致 |
| (3,-1) | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 完全一致 |
| (-2,3) | 31 | 31 | 31 | 0 | 0 | 100% | 100% | 完全一致 |

**合计：真值 240 / 预测 240 / 匹配 240 / 漏报 0 / 错报 0 / 查全 100.00% / 查准 100.00%；逐 BlockPos 一致 10/10。**

**口径二（真实世界最终钻石 · 只算目标区块内）**

| viewer | 真值（目标区块内） | 预测（落在目标区块内） | 匹配 | 漏报 | 错报 | 查全 | 查准 | 逐 BlockPos |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 45 | 45 | 45 | 0 | 0 | 100% | 100% | 完全一致 |
| (1,0) | 9 | 9 | 9 | 0 | 0 | 100% | 100% | 完全一致 |
| (0,1) | 29 | 29 | 29 | 0 | 0 | 100% | 100% | 完全一致 |
| (1,1) | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 完全一致 |
| **(-1,0)** | **22** | **18** | 18 | **4** | 0 | 81.82% | 100% | **不一致** |
| **(0,-1)** | **27** | **24** | 24 | **3** | 0 | 88.89% | 100% | **不一致** |
| (-1,-1) | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 完全一致 |
| (2,2) | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 完全一致 |
| (3,-1) | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 完全一致 |
| **(-2,3)** | **33** | **29** | 29 | **4** | 0 | 87.88% | 100% | **不一致** |

**合计：真值 243 / 预测 232 / 匹配 232 / 漏报 11 / 错报 0 / 查全 95.47% / 查准 100.00%；逐 BlockPos 一致 7/10。**

**11 格漏报的性质**：全部是「目标区块自己的装饰还没开始时，先被装饰的邻域按写半径 1 写进来的钻石」。
定向证据：第七节的「真实装饰顺序」（8 个邻域批号全部小于中心批号）+ 报告里的
「真实世界目标区块内 X 个（其中 Y 个在本区块装饰前就已由邻域写入）」。离线默认只装饰目标区块
（`decorRadius=0`），故这部分算不出；而按 `decorRadius=1` 补装饰邻域并不会稳定变好（第九节对照实验）。

**第三轮两节在同轮里未改动、照旧通过**：第一阶段 10/10（中心 ±1 240/240、落点区块 232/232）；
跨区块口径每个目标区块「9 个 viewer 顺序合并」全部逐 BlockPos 一致（33/33、45/45、27/27 …）。

---

## 十二、装置自证与还原校验

`run26` 内两个对照（证明放置链路确实以输入状态为依据）：

```
对照1（中心区块整片改写为紫水晶块：不可替换、非空气）：预测合计 0 个钻石（期望 0）→ 符合期望
对照2（中心区块整片改写为石头：可替换）：预测合计 293 个钻石（真值合计 243）→ 符合期望
还原校验：10/10 通过（测试世界逐格无残留）
```

四个探针注入点调用总次数（任一为 0 就说明该层「两侧一致」不成立）：`noteBiomeCheck 105156 次 /
notePlace 65558 次 / noteVein 25701 次 / noteAccept 1913198 次`。影子世界访问层实际调用
`WorldGenLevel` 12 种方法 / 共 1356212 次（这份清单就是「做一个完整离线上下文还差什么」的直接证据）。

---

## 十三、泛化回归（seed `12345` × 4 个区块）

测试区块（覆盖正坐标 / 负坐标 / 近原点 / 远坐标 / 不同 biome）：

| viewer | 中心区块生物群系（y=63） | 口径一 | 口径二 |
| --- | --- | --- | --- |
| (0,0) | `minecraft:ocean` | **不一致**（真值 37 / 预测 39 / 错报 2） | **不一致**（真值 36 / 预测 39 / 错报 3） |
| (-1,-1) | `minecraft:ocean` | **不一致**（真值 22 / 预测 27 / 错报 5） | **不一致**（真值 22 / 预测 27 / 错报 5） |
| (-25,17) | `minecraft:stony_shore` | 完全一致（24/24） | 完全一致（24/24） |
| (120,-130) | `minecraft:snowy_taiga` | 完全一致（29/29） | 完全一致（26/26） |

**合计（`run24`，`decorRadius=0`）**：口径一 2/4（真值 112 / 预测 119 / 匹配 112 / 漏报 0 / 错报 7 / 查准 94.12%）；
口径二 2/4（真值 108 / 预测 116 / 匹配 108 / 漏报 0 / 错报 8 / 查准 93.10%）。
**`run25`（`decorRadius=1`）**：口径一 3/4（真值 106 / 预测 107 / 匹配 98 / 漏报 8 / 错报 9）；
口径二 3/4（真值 103 / 预测 104 / 匹配 95 / 漏报 8 / 错报 9）。

**泛化结论（必须如实写）**：**发现了系统性偏差，且方向一致**——偏差全部落在
「装饰顺序 / 跨区块写入」这一类：离线多算的（错报）与真实邻域先写进来的（漏报）都由此产生；
`decorRadius` 的取值只是在不同 viewer 间搬动误差，不能消掉它。**目前 100% 只在「目标区块恰好是
生成波前中心」的固定测试集上成立。**

---

## 十四、C2ME 关闭对照

**做法**：把 `run-26.1.2/mods/c2me-fabric-mc26.1.2-0.4.0-alpha.0.54.jar` 改名为 `.disabled` 后
同 Seed `20260922`、同 10 个区块重跑（`run27`；`run28` 另试了
`config/modernfix-mixins.properties` 的 `mixin.perf.dedicated_reload_executor=false`）；跑完已把 jar 改回原文件名。

**结果**：

| 项目 | 结果 |
| --- | --- |
| 第三轮单 viewer 单次装饰（中心 ±1） | **真值 240 / 预测 240 / 匹配 240 / 漏报 0 / 错报 0 → 10/10 不变** |
| 第三轮落点区块 | **232 / 232 → 10/10 不变** |
| 离线构造 | **未取得**：`NullPointerException: Cannot invoke "net.minecraft.TracingExecutor.forName(String)" because the return value of "…ChunkGeneratorStructureState.redirect$…$modernfix$useDedicatedService(…)" is null`，10 个 viewer 全部未完成 |

**结论边界**：第三轮已证明的重放结论**与 C2ME 开关无关**（关掉后逐 BlockPos 结果不变）；
但**「关 C2ME 环境下跑离线构造」这件事没做成**——离线自建 `ChunkGeneratorStructureState` 时命中
ModernFix 的 `useDedicatedService` 重定向（返回 null），修 `modernfix-mixins.properties` 未生效。
**该项如实记为未验证**，报告里任何结论都不得依赖「关 C2ME 下的离线数据」。

---

## 十五、编译与实机运行结果

| 项目 | 结果 |
| --- | --- |
| 编译 | `.\gradlew.bat compileJava --console=plain -q` → **EXIT=0**（本轮每次改动后均通过） |
| 实机运行 | `gradlew runClient` **12 轮**（`run14` 崩溃留档，其余 11 轮正常退出并落盘报告）：`run13`（离线调试）/`run14`（邻域对照首轮，崩溃留档）/`run15`（邻域对照修复后）/`run16`（首版正式）/`run21`（修假阳性后正式）/`run22`、`run23`、`run24`（泛化 seed 12345）/`run25`（泛化 decorRadius=1）/`run26`（**终版正式轮**）/`run27`、`run28`（关 C2ME 对照） |
| 运行参数 | `JAVA_TOOL_OPTIONS`：`-Dyiyiaddon.seedpoc.enabled=1`、`chunks=0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`、`terrain=0`、`exit=1`；泛化轮改 `seed=12345` 与 `chunks=0,0;-1,-1;-25,17;120,-130`；对照轮再加 `offline.decorRadius=1` |
| 固定条件 | 26.1.2 / 原版主世界 / 固定 Seed / 同一批固定区块（未换区块、未挑成功案例） |
| 报告落盘 | `run-26.1.2/seedpoc-实验报告-run26-第四轮正式.txt`（终版）等 12 份 |
| 异常留档 | `run14` 的 `ReportedException: Exception generating new chunk`；`run27/28` 的 ModernFix NPE |

---

## 十六、尚未验证事项（不得当成已验证）

| 未验证项 | 说明 |
| --- | --- |
| 离线 pre-diamond 逐 BlockPos 一致（含邻域） | 0/10；要 10/10 需复刻真实装饰顺序（含跨区块写入时序），本轮只做到「钻石链在第三轮口径下不受影响」 |
| 真实世界最终钻石口径 100% | 7/10，漏报 11；缺口已定向取证到「邻域先装饰」 |
| 泛化回归 | 只跑了 1 个种子 `12345` × 4 个区块；**已发现系统性偏差**，不能声称算法普遍成立 |
| 关 C2ME 下的离线构造 | 未取得（ModernFix NPE）；只证明了关 C2ME 不影响第三轮重放结论 |
| 纵向范围 | 快照覆盖 y ∈ [-64,79] 的 9 个 section（钻石扫描用 y ≤ 31 的 6 个），更高处未证明 |
| 其它矿物 / 其它维度 / 26.2 / 26.3 | 按用户口径本轮一律未做 |
| 真机服务器环境 | 全程单人集成服务端；真实服务器 Seed 获取与真实性验证属后续阶段 |

---

## 十七、是否满足 PoC 结案条件（逐条对照 §18）

| 结案条件 | 状态 | 依据 |
| --- | --- | --- |
| 1. 不读取真实生成期快照 | **满足** | 宿主 ChunkMap 查询 0 次；种子四件套全自建；安装用的是离线快照 |
| 2. 只凭 Seed + 版本原版 worldgen | **满足** | 离线输入严格限定为种子/注册表/配置/ChunkPos |
| 3. 能离线构造正确 pre-diamond | **未满足** | 含邻域 0/10、仅目标区块 1/10；NOISE/SURFACE 仅目标 10/10，CARVERS 起掉到 1/10 |
| 4. 能输出最终钻石 BlockPos | **部分满足** | 口径一 10/10（240/240）；口径二 7/10（漏报 11） |
| 5. 固定测试集逐 BlockPos 通过 | **未满足** | 口径一通过、口径二未通过 |
| 6. 泛化 Seed / Chunk 回归无系统性偏差 | **未满足** | `run24/25` 已发现方向一致的系统性偏差（装饰顺序） |

**因此本轮不宣布 PoC 结案。**

同时如实写明两件已完成的事：

- 用户第四轮第十二节的验收目标（`Seed → 离线 pre-diamond → 第三轮 replay → 钻石 BlockPos`，
  漏报 0 / 错报 0 / 10/10 逐 BlockPos）在**第三轮口径（同一次装饰内的贡献）下已经达成**：240/240、10/10。
- 「离线自己跑出来的噪声地形与地表，在目标区块内与真实生成逐 BlockPos 相同（NOISE / SURFACE 各 10/10）」
  已经证明——**复用原版 pipeline 离线造状态的路线是成立的**，卡点只在「跨区块装饰写入的时序」这一层。

---

## 十八、下一步（等用户确认，不自行推进）

1. **补「跨区块装饰写入时序」**：把真实装饰批号（同一 3×3 邻域内的先后顺序）作为离线装饰顺序的输入，
   目标是让口径二的 11 格漏报归零——**注意这会把「服务端调度顺序」引入预测，须先由用户确认是否可接受**。
2. **补关 C2ME 下的离线构造**：定位 ModernFix `useDedicatedService` 重定向返回 null 的成因。
3. 以上两条通过后，再谈 PoC 结案 → 清理探针 → 正式「种子挖矿」页面
   （位于现有自动挖矿控制台内、玩家可见内容全中文、`personalMode` 隐藏、不重复矿物/精准采集/时运/
   Baritone/食物/回家/白名单）。
4. 按用户口径：**本轮到此停止**，正式业务代码零改动，UI 未开发。

---

## 十九、本轮新增/修改文件清单（实验代码，PoC 结案时整包删除）

新增（10）：`OfflineWorldgenContext` / `OfflineChunkHolder` / `OfflineChunkRegion` / `OfflineChunkPipeline` /
`OfflineStageCapture` / `StageSnapshotCapture` / `StageComparator` / `OfflineWorldgenVerifier` /
`OfflineOutcome` / `mixin/client/ChunkStatusStageProbeMixin`。

修改（7）：`GenStageSnapshot`、`GenStageCapture`、`PlacedFeatureStageCaptureMixin`、`SeedPocFlags`、
`SeedPocReport`、`SeedPocRunner`、`yiyiaddon.mixins.json`。

**正式业务代码：零改动**（`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / 精准采集 / 时运 /
Baritone / 食物 / 回家 / 背包白名单 一行未改）。
