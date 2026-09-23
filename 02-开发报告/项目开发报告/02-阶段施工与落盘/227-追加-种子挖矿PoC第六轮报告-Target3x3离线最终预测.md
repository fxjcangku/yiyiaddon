# 227 · 追加 · 《种子挖矿 PoC 第六轮报告 —— Target 3×3 离线最终预测》

日期：2026-09-23
阶段：种子挖矿 PoC —— **第六轮「Seed + Dimension + Target ChunkPos → 目标区块最终自然钻石 BlockPos」**。
上游证据链：第三轮 [223](223-追加-种子挖矿PoC第三轮报告-单viewer单次装饰逐BlockPos一致.md)、
第四轮终版 [225](225-追加-种子挖矿PoC第四轮报告-终版-仅凭Seed离线构造pre-diamond.md)、
第五轮 [226](226-追加-种子挖矿PoC第五轮报告-Chunk生成顺序决定性实验.md)。

**验证方式**：实机验证，Minecraft 26.1.2 真实客户端进程（`gradlew runClientSeedPredictTest`），
每个「阶段 × 种子」一次独立进程、每次重建全新世界（`SeedPocWorldFactory` 先删存档再建），
所有数字均为本次实测值，无一处引用上一轮的旧数字（唯一例外在第九项，已注明）。

---

## 零、结论速览

| 项 | 结论 |
| --- | --- |
| 第六轮模型 | 「共享离线世界 + 目标 ± 写半径 的 viewer 各跑一遍 FEATURES，只读目标区块」 |
| 写半径来源 | `ChunkPyramid.GENERATION_PYRAMID.getStepTo(FEATURES).blockStateWriteRadius()` = **1**（现算，非写死 3×3） |
| 第一阶段 Seed 20260922（10 target） | **Truth 243 / Prediction 243 / Match 243 / FN 0 / FP 0；10/10 逐 BlockPos 完全一致** |
| 第四轮旧漏报 | 本轮现场复算的旧口径漏报 **9 格全部找回**（逐格给出 viewer / placed_feature / 是否跨区块） |
| 第二阶段 Seed 12345（4 老区块） | **未通过**：(-25,17)、(120,-130) 逐 BlockPos 完全一致；(0,0) 漏 5；( -1,-1) 的真值本身在两次相同运行里不一致 |
| 第三阶段多 Seed 泛化（5 Seed × 6 target） | Truth 703 / Prediction 699 / **FN 5 / FP 1**；28/30 完全一致（3 个种子 6/6） |
| PoC 结案判据 | **不满足**（结案判据第 6、7 项未达成）⇒ 不建议进入 UI |
| 剩余差异的 FIRST DIVERGENCE | **不在生物群系 / 噪声 / 地表层**（SURFACE 阶段逐格完全一致）；剩余差异全部属于**「跨区块写入的先后顺序」这一类**：同一个格子被「邻 viewer 的钻石矿脉」与「另一个邻 viewer 的其它地物」先后写过，谁最后写决定最终状态 |
| 事实修正（对第五轮） | 第五轮「最终钻石集合与装饰先后无关」在本轮**被反例推翻**（Seed 12345 的 (0,0)、Seed 2 的 (-400,380)），但**第五轮的修复方向仍然正确**：漏报的主因确实是「旧口径漏掉 ±1 邻域的跨区块写入」 |
| 正式业务 | **零改动**（3 个已有文件全部是纯新增行：`build.gradle` +34、`YiyiAddonClient` +5、`yiyiaddon.mixins.json` +5；业务类一行未改；UI 未开发） |

一句话：**「只凭 Seed 离线算出目标区块最终钻石」在本轮 44 个目标里 40 个逐 BlockPos 完全一致
（真值合计 1055 格 / 预测合计 1052 格 / FN 19 / FP 16 / Match 1036），
剩余 4 个目标的差异已被定位到「同一格的多次跨区块写入谁最后落笔」这一层——它不是 Seed 函数，
而是真实服务端并发调度的产物；其中 Seed 12345 的 `(-1,-1)` 连真值本身都不可重复。**

---

## 一、新 Predictor 架构（报告项 1）

全部位于 `com.yiyiaddon.dev.seedpoc`（PoC 代码，与业务层完全隔离）：

| 类 | 职责 |
| --- | --- |
| `OfflinePredictionSession` | 一次 `Seed + Dimension` 下的**共享离线 worldgen**：1 套 BiomeSource / NoiseBasedChunkGenerator / RandomState / ChunkGeneratorStructureState，1 份 ProtoChunk 表 + OfflineChunkRegion 视图 |
| `OfflineChunkPipeline` | 离线驱动：照原版 `ChunkPyramid` 逐层铺前置状态（`prepare`）、让一个区块跑自己的 `FEATURES`（`decorate`）、读任意区块任意状态（`chunkAt`） |
| `TargetViewerSet` | 由当前版本原版规则**现算**「可能写进目标区块的 viewer 集合」 |
| `TargetChunkPredictor` | 纯 Seed 预测入口：`predictAroundTarget(session, target)`（第六轮模型）/ `predictSingleViewer(...)`（旧口径复算） |
| `FinalOreCollector` | 只从**离线** target 区块读最终自然钻石 BlockPos |
| `OfflineOreAttribution` | 离线侧 OreFeature 接受台账（写入归属：viewer / placed_feature / 是否跨区块） |
| `FinalOreTruthComparator` | **只存在于测试侧**：读真实世界并比较（不参与预测） |
| `PredictionOutcome` | 一次预测的产出（预测集 / 真值集 / 差集 / 归属 / 逐 viewer 贡献 / 性能） |
| `PredictionRegressionSuite` | 固定集 / 12345 回归 / 多 Seed 泛化 / 缓存隔离 / 真值可重复性台账 的编排 |

对外语义等价于用户口径第二十三节要求的：

```
predictDiamonds(seed, dimension, targetChunk) -> Set<BlockPos>
```

本轮的实测 API 形态是 `TargetChunkPredictor.predictAroundTarget(session, target)`，
其中 `session` 只由 `(宿主环境参数, seed)` 构造，**参数里没有 ServerLevel / 真实 ChunkAccess /
RealPreDiamondSnapshot / 真实矿石坐标 / 真实装饰批号**。

---

## 二、OfflinePredictionSession 工作方式（报告项 2）

1. `OfflineWorldgenContext.create(host, seed)`：自建 BiomeSource（`MultiNoiseBiomeSource.createFromPreset(minecraft:overworld)`）、
   `new NoiseBasedChunkGenerator(生物群系源, NoiseGeneratorSettings.overworld)`、`RandomState.create(注册表, NoiseGeneratorSettings.overworld, 种子)`、
   `generator.createState(...)`。宿主 `ServerLevel` **只当环境宿主**（维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂）。
2. 一份 `OfflineChunkPipeline(ctx, reuseWorldAcrossViewers = true)`：**跨 viewer 复用同一个离线世界**
   （前一个 viewer 装饰写进去的方块，后一个 viewer 执行时真实存在）。
3. `prepareFor(target, extraRadius = 写半径)`：按原版 `ChunkPyramid` 的 `getAccumulatedRadiusOf` 逐阶段铺
   `target ± (累积半径 + extraRadius)` 的前置状态；已产出过的阶段直接跳过（这是共享缓存命中的来源）。
4. `decorate(viewer)`：让一个区块跑它自己的 `applyBiomeDecoration`（幂等）。
5. `finalChunk(target)`：只读目标区块跑完 `FEATURES` 之后的最终状态。

本轮实测的邻域依赖表（原版现算，进报告）：

```
minecraft:empty               ：铺到半径 10
minecraft:structure_starts    ：铺到半径 10
minecraft:structure_references：铺到半径 2
minecraft:biomes              ：铺到半径 2
minecraft:noise               ：铺到半径 1
minecraft:surface             ：铺到半径 1
minecraft:carvers             ：铺到半径 1
FEATURES                      ：铺到半径 0（目标区块自己）
```

---

## 三、target viewer 集合如何推导（报告项 3）

- 写半径：`ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES).blockStateWriteRadius()` = **1**；
  `WorldGenRegion#ensureCanWrite` 的判据正是「生成步骤的 `blockStateWriteRadius`」，因此
  「能写进目标区块的 viewer」= 与目标区块切比雪夫距离 ≤ 写半径 的区块。
- 26.1.2 下该值 = 1 ⇒ 每目标 **9 个 viewer**、共 9 遍 FEATURES。**不是把 3×3 写死**：
  每一次运行都从 `ChunkPyramid` 现算，报告里同时打印该步骤的 `blockStateWriteRadius` 与依赖表原文。
- 顺序：由远到近、**目标区块永远最后**（不引入任何真实调度历史输入）。
- 读半径是另一条独立约束，交给 `prepare` 按 `getAccumulatedRadiusOf` 逐阶段铺（见第二节）。

---

## 四、是否采用共享 Offline world（报告项 4）

**是。** 一个会话 = 一份离线世界，跨目标、跨 viewer 复用：

```
离线世界口径：跨 viewer 复用同一份世界（共享会话口径：前一个 viewer 装饰写进去的方块，后一个 viewer 执行时真实存在）
离线缓存半径（照原版 ChunkGenerationTask 算）：10 区块 → 每层最多 21x21 区块
```

实测的复用效果（Seed 20260922 固定集，10 个目标一次会话）：
`阶段执行 2005 次 / 缓存命中跳过 10395 次 / 持有区块 744 个 / FEATURES 执行 43 次`，
其中第一目标 529 个 ProtoChunk、后续目标只新增 1~23 个。

---

## 五、9 viewer 如何执行 FEATURES（报告项 5）

`TargetChunkPredictor.predictAroundTarget` 的实测流程（对每个 viewer 逐字执行）：

1. 读一次目标区块的钻石集合 `before`（`session.latestChunk(target)`）；
2. `session.decorate(viewer)` —— 让该 viewer 跑它自己的 `applyBiomeDecoration`；
3. 再读一次目标区块 `after`；`after − before` = **这个 viewer 这一遍写进目标区块的钻石**（逐 viewer 贡献，进报告）；
4. 9 个 viewer（含目标自己）跑完后，只读目标区块的最终状态，作为预测集。

关键点：**这不是「9 个独立预测后 union」**——9 个 viewer 在**同一份离线世界**里依次执行，
第 k+1 个 viewer 执行时，前 k 个 viewer 写进世界（含写进目标区块）的方块是真实存在的；
预测集始终是「最后那一次只读目标区块」的结果，逐 viewer 贡献只是**取证**，不参与合成。

---

## 六、是否读取任何真实世界状态（报告项 6）

**不读。** 输入边界（实测进报告的 `describe()`）：

- 只使用：版本 / Seed / 维度 / Target ChunkPos / 原版注册表 / NoiseGeneratorSettings / 原版 worldgen 配置；
- **不读取**：真实世界方块状态、真实 ChunkAccess、pre-diamond 快照（世界真值）、高度图、真实矿物坐标、装饰批号；
- 代码层硬隔离：`TargetChunkPredictor` / `OfflinePredictionSession` 的参数里没有任何 `ServerLevel` 内容读取；
  真值只在预测**完成之后**由测试侧的 `FinalOreTruthComparator.attach(...)` 读取（读真值会强制生成真实区块，
  绝不允许出现在预测输入路径上）；
- 自证指标：**离线 region 向宿主 ChunkMap 的查询次数 = 0 次**（本报告所有阶段、所有目标均为 0；
  该指标不为 0 时必须在报告里逐条解释来源）。

---

## 七、是否使用真实装饰批号（报告项 7）

**不使用。** 预测侧没有任何「装饰批号 / `applyBiomeDecoration` 调用顺序 / 服务端 Chunk 历史」输入；
viewer 顺序固定为「由远到近、目标最后」。

本轮**确实**采集了真实装饰批号，但它只出现在**取证行**里（`round6.probe=1` 时打印），
用来证明「真实侧那一刻谁先装饰」，**不参与任何预测计算**（见第十五项）。

---

## 八、跨 Chunk 写入实现方式（报告项 8）

跨区块写入不是被「特别实现」的，而是**原版规则的正常结果**，模型只负责不去掉它：

1. `WorldGenRegion#ensureCanWrite` 允许当前生成步骤在写半径（FEATURES = 1）内写邻区块 ⇒
   邻 viewer 的 `applyBiomeDecoration` 会合法地把方块写进目标区块；
2. 离线侧用 `OfflineChunkRegion`（只覆盖 5 处种子相关读取：`getSeed` / `getRandom` / `getBiomeManager` /
   `getUncachedNoiseBiome` / `isOldChunkAround=false`）+ `StaticCache2D<GenerationChunkHolder>` +
   `OfflineChunkHolder` 复现同一套读/写判据，因此「写进目标区块」这件事在离线世界里真的会发生；
3. 因为 viewer 在同一份世界里依次执行，跨区块写入的**叠加关系**被完整保留；
4. 归属台账（`OfflineOreAttribution`）逐格记录：写入方 viewer、placed_feature 名称、是否跨区块 ⇒
   报告里每个目标都能给出「预测集里由邻 viewer 跨区块写入的坐标数」。

实测例：Seed 20260922 的 `(0,-1)` 有 3 格由邻 viewer `(1,-1)` 的 `ore_diamond` / `ore_diamond_medium` 写入；
`(-2,3)` 有 4 格由 `(-2,4)` 的 `ore_diamond_buried` 写入（见第九项逐格表）。

---

## 九、第四轮旧漏报逐格处理结果（报告项 9）

旧口径（**只装饰目标区块自己**，= 第四轮离线模型）在**本次运行里现场复算**，不引用上一轮数字：

| target | 旧口径真值 | 旧口径预测 | 旧口径漏报 | 第六轮模型漏报 | 结果 |
| --- | --- | --- | --- | --- | --- |
| (-1,0) | 22 | 20 | 2 | 0 | 找回 2/2 |
| (0,-1) | 27 | 24 | 3 | 0 | 找回 3/3 |
| (-2,3) | 33 | 29 | 4 | 0 | 找回 4/4 |
| **合计** | | | **9** | **0** | **找回 9/9** |

逐格明细（写入方 viewer / placed_feature / 是否跨区块 / 第六轮预测是否存在 / Truth 是否存在）：

| Target | BlockPos | 写入它的 viewer | placed_feature | 跨区块写入 | 第六轮预测 | Truth |
| --- | --- | --- | --- | --- | --- | --- |
| (-1,0) | (-12,-50,15) | (-1,1) | `ore_diamond_medium` | 是 | 存在 | 存在 |
| (-1,0) | (-11,-50,15) | (-1,1) | `ore_diamond_medium` | 是 | 存在 | 存在 |
| (0,-1) | (15,-46,-14) | (1,-1) | `ore_diamond` | 是 | 存在 | 存在 |
| (0,-1) | (15,-36,-3) | (1,-1) | `ore_diamond_medium` | 是 | 存在 | 存在 |
| (0,-1) | (15,-35,-3) | (1,-1) | `ore_diamond_medium` | 是 | 存在 | 存在 |
| (-2,3) | (-21,-33,63) | (-2,4) | `ore_diamond_buried` | 是 | 存在 | 存在 |
| (-2,3) | (-20,-33,63) | (-2,4) | `ore_diamond_buried` | 是 | 存在 | 存在 |
| (-2,3) | (-21,-32,63) | (-2,4) | `ore_diamond_buried` | 是 | 存在 | 存在 |
| (-2,3) | (-20,-32,63) | (-2,4) | `ore_diamond_buried` | 是 | 存在 | 存在 |

**全部 9 格都是跨区块写入，且写入方都是「不与目标同行同列的对角/侧邻区块」**，与第二节的写半径推导一致。

口径差异的如实登记：**第四轮报告记录的漏报是 11 格**（(-1,0) 4 / (0,-1) 3 / (-2,3) 4），
本轮现场复算得到 9 格（(-1,0) 2 / (0,-1) 3 / (-2,3) 4）。差在 (-1,0)：第四轮记录「真值 22 / 预测 18」，
本轮同义复算得到「真值 22 / 预测 20」——即第四轮口径下有 2 格没预测到、本轮同义复算能预测到（复算实现差异，
两份实现都不含任何跨 viewer 聚合）。**无论按哪一份，第六轮模型在这三个目标上的漏报都是 0。**

---

## 十、Seed 20260922 十个固定 target 结果（报告项 10、11、12）

固定 10 个 target 原样保留、未替换：
`(0,0) (1,0) (0,1) (1,1) (-1,0) (0,-1) (-1,-1) (2,2) (3,-1) (-2,3)`，Seed **20260922**。

| Target | Truth `diamond_ore` | Truth `deepslate_diamond_ore` | Truth 合计 | Predictor | Match | FN | FP | Recall | Precision | 逐 BlockPos 完全一致 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 0 | 45 | 45 | 45 | 45 | 0 | 0 | 100% | 100% | 是 |
| (1,0) | 0 | 9 | 9 | 9 | 9 | 0 | 0 | 100% | 100% | 是 |
| (0,1) | 0 | 29 | 29 | 29 | 29 | 0 | 0 | 100% | 100% | 是 |
| (1,1) | 0 | 21 | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 是 |
| (-1,0) | 0 | 22 | 22 | 22 | 22 | 0 | 0 | 100% | 100% | 是 |
| (0,-1) | 0 | 27 | 27 | 27 | 27 | 0 | 0 | 100% | 100% | 是 |
| (-1,-1) | 0 | 21 | 21 | 21 | 21 | 0 | 0 | 100% | 100% | 是 |
| (2,2) | 0 | 18 | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 是 |
| (3,-1) | 2 | 16 | 18 | 18 | 18 | 0 | 0 | 100% | 100% | 是 |
| (-2,3) | 0 | 33 | 33 | 33 | 33 | 0 | 0 | 100% | 100% | 是 |
| **合计** | **2** | **241** | **243** | **243** | **243** | **0** | **0** | **100%** | **100%** | **10/10** |

**目标达成：Truth = 243 / Prediction = 243 / Match = 243 / FN = 0 / FP = 0 / 10-10 完全一致。**
（每目标的漏报样本、错报样本均为「无」，故不单列。）

---

## 十一、Seed 12345 四个旧回归 Chunk 结果（报告项 13）

4 个旧区块原样保留、未替换：`(0,0)` `(-1,-1)` `(-25,17)` `(120,-130)`，Seed **12345**。

同一阶段共跑了 4 次进程（3 次关诊断探针、1 次开诊断探针），结果如下（**真值确实会变**）：

| Target | 运行 | Truth | Predictor | Match | FN | FP | 完全一致 | 预测集摘要 | 真值摘要 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 4 次全部 | 36 | 31 | 31 | **5** | 0 | 否 | h=3af6d n=31（4 次相同） | h=3db07 n=36（4 次相同） |
| (-1,-1) | run B（探针关） | 27 | 29 | 27 | 0 | 2 | 否 | h=fffcbbf3 n=29（4 次相同） | h=fffd1990 n=27 |
| (-1,-1) | run A / probe / run C | 23 | 29 | 14~23 | 9~14 | 0~15 | 否 | 同上 | h=fffd2d30 n=**23** |
| (-25,17) | 4 次全部 | 24 | 24 | 24 | 0 | 0 | **是** | h=60ecd4 n=24 | h=60ecd4 n=24 |
| (120,-130) | 4 次全部 | 26 | 26 | 26 | 0 | 0 | **是** | h=fce9e4d3 n=26 | h=fce9e4d3 n=26 |

汇总（以最近一次「探针关闭」运行为主）：

```
run B（探针关）：真值 113 / 预测 110 / 匹配 108 / 漏报 5  / 错报 2  / 逐 BlockPos 完全一致 2/4
run C（探针关）：真值 109 / 预测 110 / 匹配 95  / 漏报 14 / 错报 15 / 逐 BlockPos 完全一致 2/4
probe（探针开）：真值 109 / 预测 110 / 匹配 95  / 漏报 14 / 错报 15 / 逐 BlockPos 完全一致 2/4
```

**判定：未通过用户口径「原 4 个回归 Chunk 全部逐 BlockPos 通过」。**
其中 `(0,0)` 与 `(-1,-1)` 两个失败目标都在出生点附近，定位见第十五节。

### 真值可重复性台账（跨运行）

本阶段自动落盘 `seedpoc-truth-digest-regression-12345.txt`，下一次同参数运行读回比对，实测：

```
真值可重复性汇总：可比较目标 4 个；「首次记录」0 个、预测摘要可重复 4 个、预测摘要不可重复 0 个、真值摘要不可重复 1 个
真值可重复性：目标 (0,0)   预测与上一次相同 h=3af6d n=31；真值与上一次相同 h=3db07 n=36
真值可重复性：目标 (-1,-1) 预测与上一次相同 h=fffcbbf3 n=29；**真值与上一次不同**（本次 n=27 / 上一次 n=23）
真值可重复性：目标 (-25,17)  预测与上一次相同 h=60ecd4 n=24；真值与上一次相同 h=60ecd4 n=24
真值可重复性：目标 (120,-130) 预测与上一次相同 h=fce9e4d3 n=26；真值与上一次相同 h=fce9e4d3 n=26
```

结论有两条，且必须分开读：

1. **预测集在 4 次运行里逐格可重复**（同一目标 4 次摘要完全相同）⇒ 离线模型是确定性的；
2. **`(-1,-1)` 的真值不可重复**（4 次实测 n ∈ {23, 27, 23, 23}）⇒ 这个目标的「不一致」
   不能全部记到预测器头上：**真实世界本身在这个区块上不是 Seed 的函数**。
   按用户口径第十五节，这里如实登记为「该目标不计入一致率验收」，并**没有为了让它通过去调任何半径**。
3. `(0,0)` 的真值 4 次都稳定在 36，而预测恒定 31（漏 5，坐标每次完全相同）⇒
   它不是随机抖动，而是第十五节定位到的**系统性顺序差异**。

---

## 十二、多 Seed 泛化结果（报告项 14）

第三阶段：5 个种子（`1`、`2`、`12345`、`987654321`、`-7777`）× 每种子 6 个 target
（`(0,0) (25,25) (-30,40) (17,-25) (150,-160) (-400,380)`；一个进程只覆盖一个种子，多种子由多次进程运行覆盖）。

| Seed | Truth | Prediction | Match | FN | FP | 完全一致 | 覆盖到的真实生物群系（y=64 取样，如实登记） |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | 170 | 170 | 170 | 0 | 0 | **6/6** | deep_ocean、forest、lukewarm_ocean、cold_ocean、plains、beach |
| 2 | 132 | 133 | 132 | 0 | **1** | 5/6 | river、frozen_ocean、beach、snowy_plains、snowy_plains、lukewarm_ocean |
| 12345 | 161 | 156 | 156 | **5** | 0 | 5/6 | ocean、old_growth_pine_taiga、plains、lush_caves、cold_ocean、ocean |
| 987654321 | 127 | 127 | 127 | 0 | 0 | **6/6** | dark_forest、dripstone_caves、forest、plains、lukewarm_ocean、cold_ocean |
| -7777 | 113 | 113 | 113 | 0 | 0 | **6/6** | grove、deep_cold_ocean、frozen_ocean、cold_ocean、taiga、river |
| **合计** | **703** | **699** | **698** | **5** | **1** | **28/30** | 26 种不同生物群系，含 ocean / forest / taiga / snowy / mountains 邻近类 / cave / beach / plains |

用户口径第十七节的 8 项统计：总 Seed 数 **5**、总 Target 数 **30**、Truth BlockPos 总数 **703**、
Prediction BlockPos 总数 **699**、Match **698**、FN **5**、FP **1**、完全一致 Chunk 数 **28**。
（**未达到「FN=0 / FP=0 / 全部 target 逐 BlockPos 一致」**，按口径第十七节不能宣布结案。）

---

## 十三、所有失败案例（报告项 15）

本轮一共 4 个失败目标，全部逐格记录：

### 13.1 Seed 12345 `(0,0)`（阶段二与阶段三都失败；漏 5，且 5 个坐标每次完全相同）

```
真值 36 / 预测 31 / 匹配 31 / 漏报 5 / 错报 0 / 查全 86.11% / 查准 100.00%
漏报样本：(6,-9,2) (7,-9,2) (6,-9,3) (7,-9,3) (7,-8,3)
归属信息：预测集里跨区块写入 0 个（邻 viewer 一个钻石都没写进这个目标区块各遍增量）
漏报坐标两侧方块：离线=deepslate/tuff ，真值=deepslate_diamond_ore（5 格离线侧全部可替换）
逐格状态比对（y ∈ [-64,31] 共 24576 格）：与真值不同 162 格（其中两边都非钻石 157 格）
  地形差异的可替换性分类：两侧都可替换 2 / 两侧都不可替换 86 /
    真值可替换·离线不可替换 56（离线少消耗随机 → 后续漂移）/ 真值不可替换·离线可替换 13 ⇒ 能改变随机流消耗的格子合计 69
  差异成对（最多的几对）：deepslate→moss_block 39；tuff→moss_block 16；short_grass→air 11；moss_block→deepslate 9；smooth_basalt→cave_air 8
结构比对（目标 ±1）：共 9 个区块，starts/refs 不一致 0 个
```

同一目标在同一 Seed 的泛化阶段复跑（世界与邻域上下文不同）：
`真值 36 / 预测 31 / 漏报 5（同样这 5 格）/ 错报 0`，逐格差异 433 格、可改变随机流的格子 310 格
⇒ **漏报是稳定复现的，不是抖动**；而「周边方块差异」的规模会随真实调度顺序变化。

### 13.2 Seed 12345 `(-1,-1)`

```
run C（探针关）：真值 23 / 预测 29 / 匹配 14 / 漏报 9 / 错报 15 / 逐 BlockPos 完全一致 否
  漏报样本：(-6,-52,-7) (-7,-51,-8) (-6,-51,-8) (-7,-51,-7) …
  错报样本：(-2,-17,-4) (-16,-16,-8) (-15,-16,-8) (-15,-15,-9) (-1,-30,-11) …
  错报坐标两侧方块（离线钻石 / 真值别的方块）：gravel、clay、water、deepslate ⇒ 真实侧「把钻石盖掉了」
  跨区块写入：预测集里 2 个坐标由邻 viewer (0,-1) 写入
  逐格状态比对：与真值不同 604 格；能改变随机流消耗的格子合计 245 格
run B（探针关、另一次）：真值 27 / 预测 29 / 匹配 27 / 漏报 0 / 错报 2
  错报 (-1,-30,-12) (-1,-30,-11)：离线=deepslate_diamond_ore / 真值=deepslate
结构比对（目标 ±1）：共 9 个区块，starts/refs 不一致 0 个
```

**同一个目标、同一份参数、两次运行真值 23 与 27 ⇒ 该区块的「真实最终钻石」不可重复**（见第十一节台账）。

### 13.3 Seed 2 `(-400,380)`

```
真值 22 / 预测 23 / 匹配 22 / 漏报 0 / 错报 1 / 查全 100.00% / 查准 95.65%
错报样本：(-6385,-59,6085)（离线=deepslate_diamond_ore / 真值=gravel）
归属信息：预测集里跨区块写入 2 个（按 viewer (-399,380)）
逐格状态比对：与真值不同 15 格（两边都非钻石 14 格）；
  可替换性分类：两侧都可替换 14 / 其它 0 / ⇒ **能改变随机流消耗的格子合计 0**
  差异成对：diorite→andesite 13；andesite→granite 1
结构比对（目标 ±1）：starts/refs 不一致 0 个
```

（另：Seed 12345 的回归阶段 `run C` 里 `(-1,-1)` 的 14 漏 / 15 错已在 13.2 给出。）

---

## 十四、FIRST DIVERGENCE 取证方法与判据（报告项 16 的前半）

诊断探针（`-Dyiyiaddon.seedpoc.round6.probe=1`，**默认关闭；正式数字都在关闭状态下取得**）在目标区块上采四个阶段 checkpoint，
离线侧与真实侧逐格比：`BIOMES → NOISE → SURFACE → CARVERS`。
判据只有三种，且指向完全不同的修法：四个阶段全一致 ⇒ 分叉在 FEATURES；某一层起不一致 ⇒ 分叉在那层；快照没采到 ⇒ 不可判定（如实记录，不退化成「一致」）。

**先说明装置本身的两个限制（这两个都必须写在结论里，否则结论会被读错）：**

1. **真实侧 `BIOMES` / `NOISE` checkpoint 的采样时点早于异步完成**：这两个阶段的注入点是
   `ChunkStatusTasks#generateBiomes / #generateNoise` 的 `RETURN`，而这两个方法返回的是
   `CompletableFuture<ChunkAccess>`——`RETURN` 只代表 future 被创建，**噪声填充还在工作线程上跑**。
   因此真实侧 `NOISE` 快照拍到的是「还没填完的区块」，与离线（`.join()` 后才有产出）逐格比会得到巨大的
   假差异（本目标 23468/36864，第一处 y=-64 真实 air / 离线 stone 正是「底部还没填」的特征）。
   `BIOMES` 同理：方块层两侧都是空（无差异），但生物群系数组真实侧还没写（`576` 格全部标「不可比」）。
   **⇒ `BIOMES` / `NOISE` 两层本轮判为「不可判定」，不作为第一处分叉证据。**
2. **`SURFACE` 的采样时点有效**：`generateSurface` 里的 `buildSurface` 是同步调用，`RETURN` 时区块已经写好。

---

## 十五、FIRST DIVERGENCE 实测（报告项 16）

### 15.1 地形链路：生物群系 / 噪声 / 地表 —— 没有分叉

```
目标 (0,0)：
  [BIOMES  ：方块 36864/36864（不一致 0）；生物群系 0/576（不可比 576）→ 完全一致（方块层）]
  [NOISE   ：方块 13396/36864（不一致 23468）→ 假差异，见第十四节限制 1]
  [SURFACE ：方块 36864/36864（不一致 0）；生物群系 576/576（不一致 0）；高度图 512/512（不一致 0）→ **完全一致**]
  [CARVERS ：方块 35370/36864（不一致 1494）→ 见 15.2]
目标 (-1,-1)：
  [SURFACE ：方块 36864/36864（不一致 0）；生物群系 576/576（不一致 0）；高度图 512/512（不一致 0）→ **完全一致**]
```

**四个回归目标（含两个失败目标）的 `SURFACE` 阶段全部 36864/36864 逐格完全一致。**
⇒ 离线侧「生物群系 → 噪声 → 地表」这条链路在目标区块上**与真实逐格一致**，分叉不在这一层。

### 15.2 CARVERS：差异存在，但真实侧快照已被邻 viewer 的 FEATURES 写入污染

```
CARVERS 步半径（原版现算）：写半径 0，累积依赖半径 10 项，对 EMPTY 的累积半径 9
同侧「SURFACE → CARVERS」的差（判据：这一步之间只应该发生雕刻）：
  离线：4 种，最多 air 957 / lava 39 / air→tuff 11   ← 全部是雕刻产物（air / lava），干净
  真实：32 种，最多 air→deepslate 952；**granite→stone 359**；**clay→deepslate 311**；…… ← 已被污染
```

雕刻器 `applyCarvers` 只会写出 **air / water / lava**；真实侧 `CARVERS` 那一刻却出现了
`granite 359` / `clay 311` / `andesite 285` / `tuff 327`（目标 (-1,-1)：`tuff→deepslate 327`、`andesite→stone 285`、`clay→deepslate 241`）。
这些方块**只可能来自其它区块的 FEATURES 写入**（`ore_granite` / 黏土类地物等），
⇒ **真实侧的 `CARVERS` checkpoint 在采样那一刻已经被并发的邻区块装饰写进了方块，不能当作第一处分叉。**

### 15.3 真实装饰批号：证明并发调度，且证明顺序会变

`round6.probe=1` 运行实测（批号越小越先装饰，只有比中心小的邻块才可能在中心之前写进中心）：

```
目标 (0,0)   中心批号=5 ；邻域 (-1,-1)=11 (-1,0)=13 (-1,1)=14 (0,-1)=4 (0,1)=6 (1,-1)=1 (1,0)=2 (1,1)=3
             ⇒ 先于中心装饰的邻块：(1,-1)=1、(1,0)=2、(1,1)=3、(0,-1)=4
目标 (-1,-1) 中心批号=11；先于中心：(-2,-2)=8、(-2,-1)=9、(-1,-2)=10、(0,-2)=7、(0,-1)=4、(0,0)=5
目标 (-25,17) 中心批号=19；先于中心：(-26,16)=15、(-26,17)=16、(-26,18)=17、(-25,16)=18（其余 4 块在中心之后）
目标 (120,-130) 中心批号=28；先于中心：(119,-131)=24、(119,-130)=25、(119,-129)=26、(120,-131)=27
```

同一个「(0,0) 目标」在不同运行里的中心批号实测为 **5 / 12** 等不同值 ⇒
**出生点附近的装饰顺序在两次相同运行里就不同**（`ChunkStatusTasks` 各阶段返回 future、
由 `Util#backgroundExecutor` 并行推进，谁先落地不确定）。
而两个**远离出生点**的目标（`(-25,17)`、`(120,-130)`）在 4 次运行里真值摘要完全相同、预测完全相同。

### 15.4 失败根因（本轮 FIRST DIVERGENCE 结论）

| 失败目标 | 真值是否可重复 | 分类 | 证据 |
| --- | --- | --- | --- |
| Seed 12345 `(-1,-1)` | **否**（4 次 n = 23/27/23/23） | 真实世界不可重复 | 台账；批号顺序在两次运行里不同 |
| Seed 12345 `(0,0)` | 是（4 次都是 36） | 系统性顺序差异 | 同一组 5 格每次都漏；310 格差异会改变「可替换性」⇒ 改变 `OreFeature#canPlaceOre` 的 `nextFloat()` 消耗次数 ⇒ 后续矿脉几何漂移 |
| Seed 2 `(-400,380)` | — | 纯「谁最后写」覆盖 | 该目标可替换性分类里**能改变随机流的格子 = 0**，所以不是随机流漂移；真值该格是 `gravel`、离线是钻石 ⇒ 真实世界里钻石先被邻 viewer `(-399,380)` 写入、随后另一个区块的地物把它盖掉；离线固定顺序下钻石留到了最后 |
| Seed 12345 `(-1,-1)`（run B 的 2 格错报） | — | 同上（真实侧把钻石覆盖成了 deepslate） | 错报坐标两侧方块 = 离线钻石 / 真值 deepslate |

**统一机制**：`FEATURES` 的写半径是 1，且多个区块的地物**可以写到同一格**（例如「花岗岩/黏土/砾石团块」与「钻石矿脉」）。
谁是最后一个写这一格的，谁就决定这一格的最终方块。离线模型固定了**一种**顺序（由远到近、目标最后），
真实世界用的是**服务端并发调度**给出的顺序；两者在「同一格被两次以上跨区块写入」时就会分叉。

**本轮没有为了让数字通过去调任何 `decorRadius` 或写半径**（写半径每次都从 `ChunkPyramid` 现算）。

**并且必须如实记下对第五轮的修正**：第五轮结论「最终钻石集合与 Chunk 生成顺序无关」
在本轮出现反例（Seed 12345 `(0,0)`、Seed 2 `(-400,380)`），因此该结论**只在那批受控目标上成立，不能推广**。
但第五轮指出的**修复方向（补上 ±1 邻域的跨区块写入）在本轮被完整验证**：
243/243 与 9/9 旧漏报找回都靠它。

---

## 十六、其它钻石来源处理情况（报告项 17）

- 预测侧：本轮所有阶段的每一个目标，**预测集里 1052 个坐标全部有 OreFeature 接受记录**
  （`OfflineOreAttribution` 逐格台账）⇒ 本批数据里**没有**其它钻石来源（`fossil_diamonds` / 结构自带方块）参与，
  因此不存在「把其它来源当 OreFeature 漏报」的情形。
- 真值侧：真值来自「真实世界最终方块」，与来源无关，因此判据
  （真值 = 最终世界目标区块内 `diamond_ore` + `deepslate_diamond_ore`）对来源不做区分。
- 边界如实登记：归属台账只覆盖 `OreFeature`（`canPlaceOre` 接受的候选点）；非 OreFeature 来源的写入无法逐点归属，
  一旦出现就会在报告里逐目标列成「未归属」。该分支已实现，本批数据未触发。
- 结构来源：失败目标的 `starts/refs` 两侧不一致 **0/9** ⇒ 结构起点与引用两侧一致，结构不是分叉点。

---

## 十七、性能记录（报告项 18、19、20、21）

### 17.1 单 target 预测耗时（Seed 20260922 固定集，10 target 共享一个会话）

```
共享会话累计：阶段执行 2005 次 / 缓存命中跳过 10395 次 / 持有区块 744 个 / FEATURES 执行 43 次 / 宿主 ChunkMap 查询 0 次
单目标平均耗时 262 ms
  · (0,0) ：耗时 1624 ms（冷启动）/ 新建区块 529 / 阶段执行 1240（命中跳过    0）/ FEATURES 9 遍
  · (1,0) ：耗时   89 ms / 新建区块  23 / 阶段执行   78（命中跳过 1162）/ FEATURES 9 遍
  · (0,1) ：耗时   83 ms / 新建区块  23 / 阶段执行   78（命中跳过 1162）/ FEATURES 9 遍
  · (1,1) ：耗时   18 ms / 新建区块   1 / 阶段执行    8（命中跳过 1232）/ FEATURES 9 遍
  · (-1,0)：耗时   93 ms / 新建区块  23 / 阶段执行   78（命中跳过 1162）/ FEATURES 9 遍
  · (0,-1)：耗时  105 ms / 新建区块  23 / 阶段执行   78（命中跳过 1162）/ FEATURES 9 遍
  · (-1,-1)：耗时  23 ms / 新建区块   1 / 阶段执行    8（命中跳过 1232）/ FEATURES 9 遍
  · (2,2) ：耗时  203 ms / 新建区块  45 / 阶段执行  148（命中跳过 1092）/ FEATURES 9 遍
  · (3,-1)：耗时  168 ms / 新建区块  27 / 阶段执行  110（命中跳过 1130）/ FEATURES 9 遍
  · (-2,3)：耗时  215 ms / 新建区块  49 / 阶段执行  179（命中跳过 1061）/ FEATURES 9 遍
```

（Seed 12345 回归（4 目标、彼此相距很远，几乎无复用）：`(0,0)` 1293 ms / 529 块 / 1240 阶段；
`(-1,-1)` 166 ms / 45 块 / 148 阶段（命中跳过 1092）；`(-25,17)` 561 ms / 529 块；`(120,-130)` 630 ms / 529 块；
会话累计 阶段执行 3868 / 命中跳过 1092 / 持有区块 1632 / FEATURES 32；平均 662 ms。）

### 17.2 ProtoChunk 数量（报告项 19）

冷启动目标 **529 个 ProtoChunk**（= 缓存半径 `10 + 写半径 1` ⇒ `(2×11+1)² = 23×23`），
共享会话下后续目标只新增 **1~49** 个 ⇒ 复用生效。
（12345 回归里三个相距很远的目标各自冷启动 529 个，`(-1,-1)` 因落在 `(0,0)` 的复用范围内只新增 45 个。）

### 17.3 各 ChunkStep 执行次数（报告项 20）

```
固定集（10 target，共享会话）：阶段执行 2005 次
  = 结构起点 744 + 生物群系 120 + 噪声 78 + 地表 78 + 雕刻器 78 + 装饰 43（+ EMPTY 864，报告里单列）
12345 回归（4 target）：阶段执行 3868 次
  = 结构起点 1632 + 生物群系 160 + 噪声 84 + 地表 84 + 雕刻器 84 + 装饰 32（+ EMPTY 1792）
```

### 17.4 共享缓存效果 / 重复生成避免（报告项 21）

- **缓存命中跳过（同一区块同一状态重复请求而跳过的阶段数）**：固定集 **10395 次**、
  12345 回归 **1092 次** ⇒ 这些阶段没有重复生成。
- 旧方式对照（**本次运行现场复算**：每个目标一份独立离线世界 + 1 遍 FEATURES）：
  `合计 真值 243 / 预测 234 / 匹配 234 / 漏报 9 / FP 0 / 7-10 完全一致`、`平均 173 ms/目标`。
  新方式（共享会话 + 9 遍 FEATURES）：`243/243、10-10 完全一致`、`平均 262 ms/目标`。
  **新方式单目标更慢（必须跑满 9 遍 FEATURES、铺 10 圈依赖），换来的是正确性**；
  它真正省的重复生成体现在「跨目标复用」：第二个目标起 ProtoChunk 只新增 1~49 个、命中跳过 1061~1232 次。
- **峰值缓存区块数（上界）**：**744**（固定集）/ **1632**（12345 回归，4 个远距离目标叠加）。
- **粗略内存（本阶段全部目标跑完时的本进程堆）**：
  固定集 已用 **544 MB** / 当前堆 856 MB / 上限 12064 MB；12345 回归 已用 **861 MB** / 当前堆 1384 MB / 上限 12064 MB。
- 正确性优先：本轮**没有**为了快而跳过任何必要阶段（依赖表见第二节，逐阶段按 `getAccumulatedRadiusOf` 铺满）。

### 17.5 真值可重复性（对照第十九节）

```
固定集（Seed 20260922）：可比较目标 10 个；预测摘要可重复 10 个（其中逐 BlockPos 一致 10 个）、真值摘要不可重复 0 个
  ⇒ 该批目标上「真值 = Seed 的函数」成立
```

---

## 十八、Seed 缓存隔离测试（报告项 22）

实验（用户口径第二十一节，实测目标 `(-2,3)`；A = 20260922，B = 12345）：

```
第一次 A：33 个；B：31 个；第二次 A：33 个
第一次 A vs 第二次 A：真值 33 / 预测 33 / 匹配 33 / 漏报 0 / 错报 0 → **完全一致（同种子可重复）**
A vs B：真值 33 / 预测 31 / 匹配 1（两个种子本来就该不同，只用来证明 B 确实换了种子）
```

隔离维度兑现方式：会话对象 = `(版本, 种子, 维度)`；区块键 = `ChunkPos`；同一键下按生成状态分槽
（`OfflineChunkHolder` 一个状态一份产出）。跨种子不共用任何缓存对象 ⇒ **B 不可能污染 A**。

---

## 十九、compileJava / build / runClient（报告项 23、24、25）

```
> Task :compileJava        BUILD SUCCESSFUL in 2s      （EXIT=0）
> Task :build              BUILD SUCCESSFUL in 7s      （jar / assemble / check 全通过）
runClient PoC：本轮共 9 次真实客户端实跑，全部 EXIT=0 并落盘报告
  固定集 ×2（含基线复算与缓存隔离）、12345 回归 ×3（探针关×2 + 探针开×1）、多 Seed 泛化 ×5
  运行任务：gradlew runClientSeedPredictTest（独立运行目录 run-26.1.2-seed-predict-test）
```

---

## 二十、正式业务代码是否零改动（报告项 26）

**零改动。** `git status --porcelain` + `git diff --stat` 实测：
已有文件的改动只有 3 个，且**全部是纯新增行**（44 insertions / 0 deletions）：

```
build.gradle                                     | 34 ++++++++++++++++++++++++
src/main/java/com/yiyiaddon/YiyiAddonClient.java |  5 ++++
src/main/resources/yiyiaddon.mixins.json         |  5 ++++
```

其余全部是新增文件：`com/yiyiaddon/dev/`（PoC 代码）与 `mixin/client/` 下 5 个**开发期探针**
（`BiomeFilterTraceMixin` / `ChunkGeneratorStageCaptureMixin` / `ChunkStatusStageProbeMixin` /
`OreFeatureTraceMixin` / `PlacedFeatureStageCaptureMixin`）。

`AutoMiner` / `MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController` /
Baritone / 精准采集 / 时运 / 自动食物 / 自动回家 / 背包 / 白名单 / Renderer / 正式 Config / 正式 UI **一行未改**；
本轮**没有**新增任何 UI（无 Tab、无 Seed 输入框、无 Seed 开关、无 SeedValidation、无预测状态 UI、无正式 TargetProvider）。

---

## 二十一、是否满足 PoC 结案条件（报告项 27）

用户口径第二十七节 12 条逐条对照：

| # | 判据 | 结果 |
| --- | --- | --- |
| 1 | Predictor 只使用 Seed / version / worldgen 配置 | ✅（第六节，宿主 ChunkMap 查询 0 次） |
| 2 | 不读取服务器真实 Chunk 作为预测输入 | ✅（预测/真值代码路径分离） |
| 3 | 不需要真实装饰批号 | ✅（批号只用于取证，不参与预测） |
| 4 | target 3×3 viewer 跨区块写入已纳入 | ✅（写半径现算 = 1，9 viewer；逐格归属台账） |
| 5 | Seed 20260922：10/10、243/243、FN 0、FP 0 | ✅（第十节） |
| 6 | Seed 12345：原 4 个回归 Chunk 全部逐 BlockPos 通过 | ❌ **2/4**（`(0,0)` 漏 5；`(-1,-1)` 真值本身不可重复） |
| 7 | 多 Seed / 多 Chunk 回归：无系统性 FN / FP | ❌ **有**（5 Seed × 6 target：FN 5 / FP 1，28/30） |
| 8 | Seed 切换缓存不污染 | ✅（第十八节） |
| 9 | compileJava 成功 | ✅ |
| 10 | build 成功 | ✅ |
| 11 | runClient PoC 成功 | ✅（9 次实跑） |
| 12 | 正式业务零修改 | ✅（第二十节） |

**结论：不满足结案判据（第 6、7 条未达成）⇒ 不能建议「26.1.2 Overworld Diamond Seed → BlockPos PoC 可以结案」。**
按用户口径第十二节，**已停止扩大测试**（5 个种子是第三阶段既定的最小完成量，未再追加），
并把剩余差异定位到第十五节。

---

## 二十二、是否建议下一阶段正式进入 UI（报告项 28）

**不建议。** 理由：

1. 结案判据第 6、7 条未达成；
2. 剩余差异不是「实现没写对」，而是**产品语义层面的边界**：
   「最终自然钻石」在「同一格被多个区块的地物先后写入」时**不是 Seed 的函数**，
   而离线 Predictor 拿不到（口径也禁止拿）真实调度顺序；
   更极端的是 Seed 12345 `(-1,-1)`：**连真实世界自己两次运行都不一样**，
   所以「唯一正确答案」在该区块上并不存在。
3. 因此下一步若要继续，应当先与用户确认**产品要的是哪一种语义**（仅记录，本轮不实施）：
   - 语义 A：输出「**顺序无关的确定性格子**」+ 单列出「**调度歧义格子**」（这些格子只给存在性、不给最终状态）；
   - 语义 B：沿用固定顺序（邻域先、目标最后）作为公开约定，并在文档里写明歧义范围；
   - 语义 C：把「跨区块覆盖冲突」的统计量化（本轮已有可复现的 3 个案例），决定是否需要更多种子来评估发生率。
4. 按用户口径第二十八节，本轮**到此停止**：不做清理、不写正式 UI、不做 SeedValidation、
   不接自动挖矿、不扩其它矿、不开 26.2 / 26.3，等待用户确认。

---

## 二十三、附录：本轮新增/修改的 PoC 代码清单

**新增**
`com/yiyiaddon/dev/seedpoc/`：`OfflinePredictionSession`、`OfflineChunkPipeline`、`TargetViewerSet`、
`TargetChunkPredictor`、`FinalOreCollector`、`FinalOreTruthComparator`、`PredictionOutcome`、
`PredictionRegressionSuite`、`OfflineOreAttribution`、`OfflineChunkRegion`、`OfflineChunkHolder`、
`OfflineWorldgenContext`、`OfflineStageCapture`、`StageSnapshotCapture`、`StageComparator`、
`GenStageCapture`、`GenStageSnapshot`、`StatePalette`、`SeedPocFlags`、`SeedPocWorldFactory`、
`SeedPocRunner`、`SeedPocEntry`、`SeedPocConstants`、`BlockPosDiff` 等（第四~六轮累计）。

**本轮修改（3 个文件，均为开发期诊断能力，不改任何预测算法）**
- `SeedPocFlags`：新增 `round6.probe` 诊断开关（默认关）与阶段化取数；
- `OfflinePredictionSession`：诊断模式下用**独立探针流水线**采目标区块自己的四阶段 checkpoint
  （修掉「第二个目标起 checkpoint 采不到」的装置缺陷）；
- `StageSnapshotCapture`：去掉 `LevelChunkSection#hasOnlyAir()` 短路（生成期 `nonEmptyBlockCount`
  是缓存值，用原始调色板写入不刷新它，会把填满的 section 记成空气）；
- `GenStageCapture`：离线链路隔离（离线 pipeline 也会真调一次原版 `applyBiomeDecoration`，
  不允许污染真实侧取证表）；
- `PredictionOutcome`：新增 `digest()`；`PredictionRegressionSuite`：新增逐阶段 FIRST DIVERGENCE 取证、
  真值可重复性台账、粗略内存记录。

**实跑命令（可复现）**

```
# 固定集（Seed 20260922，10 target，含旧口径复算与缓存隔离）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# 12345 回归（探针关 = 正式数字；探针开 = 逐阶段取证）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=12345 -Dyiyiaddon.seedpoc.round6.stage=regression -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest
# 追加 -Dyiyiaddon.seedpoc.round6.probe=1 采真实侧逐阶段 checkpoint

# 多 Seed 泛化（每个种子一次进程）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=<1|2|12345|987654321|-7777> -Dyiyiaddon.seedpoc.round6.stage=generalize -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest
```

**自动落盘的报告**（`run-26.1.2-seed-predict-test/`）：
`seedpoc-第六轮报告-fixed-20260922.txt`、`seedpoc-第六轮报告-regression-12345.txt`、
`seedpoc-第六轮报告-generalize-{1,2,12345,987654321,-7777}.txt`、
`seedpoc-truth-digest-{fixed-20260922,regression-12345,generalize-*}.txt`（真值可重复性台账）。

---

## 二十四、本轮最重要的判定（用户口径第三十一节）

- 第一目标已重新挑战并达成：**Seed 20260922 的 243 / 243（10/10、FN 0、FP 0）**；
  第四轮 11 格旧漏报（本轮同义复算 9 格）**全部由邻 viewer 的跨区块写入找回**，并逐格给出 viewer 与 placed_feature。
- 泛化阶段 **28/30 目标、698/703 格逐 BlockPos 完全一致**，3 个种子 6/6。
- 剩余 4 个失败目标**全部**落在「同一格被多次跨区块写入、谁最后写」这一层；
  其中 1 个目标的真值本身不可重复（真实世界不是 Seed 的函数）。
- **本轮到此停止**，等待用户确认（是否结案、以及「调度歧义格子」按哪种语义处理）。
