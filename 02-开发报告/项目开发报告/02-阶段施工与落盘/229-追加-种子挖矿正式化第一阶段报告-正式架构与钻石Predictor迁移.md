# 229 · 追加 · 《种子挖矿正式化第一阶段报告 —— 正式架构、数据模型与 Diamond Predictor 迁移》

日期：2026-09-23
阶段：种子挖矿 **正式化第一阶段**（结束 PoC 研究阶段；本轮只做「正式架构 + 数据模型 + 26.1.2 钻石 Predictor 迁移 + 最小调度敏感分类 + 回归」）。
上游证据链：[223] 第三轮、[225] 第四轮终版、[226] 第五轮、[227] 第六轮、**[228] 第七轮最终收口（FEATURES 调度因果定案，本轮的最高优先级结论）**。
本轮产物：新包 `com.yiyiaddon.seed`（17 个类 / 1789 行）+ dev 侧迁移回归装置 1 个类。

**验证方式**：全部实机验证 —— Minecraft 26.1.2 真实客户端进程（`gradlew runClientSeedPredictTest`，独立运行目录
`run-26.1.2-seed-predict-test/`）两次回归（fixed / regression），另做一次正常 `runClient` 启动验证。
`compileJava` / `build` 全部 `EXIT=0`。

---

## 零、结论摘要（先给答案）

1. **228 的结论 B 已完整落进产品语义**：正式系统现在能同时表达
   「Seed 能稳定确定的目标 / 受调度影响的目标 / 未解析的目标」（`PredictionCertainty`）
   与「服务器实际观察结果」（`OreObservationState`），两个维度**互相独立**。
2. **迁移零退化**：正式 `DiamondSeedPredictor` 与 PoC 固定顺序预测器
   **逐 BlockPos 完全一致** —— Seed 20260922 固定 10 目标 `243/243`（10/10 目标一致），
   Seed 12345 旧回归 `110/110`（4/4 目标一致），正式预测失败 0 次。
3. **Seed 2 已知争议格判对**：`(-6385,-59,6085)` 被标为 `SCHEDULE_SENSITIVE`（不是确定性），
   与 228 第七节的「两种合法顺序在 gravel ↔ deepslate_diamond_ore 之间翻转」完全吻合；
   本轮**没有任何坐标被标成 `DETERMINISTIC`**（本阶段不产出该值）。
4. **额外收获（正式分析的正面证据）**：Seed 12345 `(-1,-1)` —— 228 里「真实真值本来就不重复」的那个区块 ——
   在正式分析下被查出 **7 个坐标**在两种合法顺序间存在性不同，正是该目标不稳定的机制性证据。
5. 正式层**不读真实世界**：每次预测的「向宿主 ChunkMap 查询次数」增量恒为 **0**；正式包对 dev 包**零引用**。
6. AutoMiner / Baritone / UI / 配置 **一行未改**；本阶段**没有** UI、Seed 输入、Seed 验证、假矿检测、其它矿物。

---

## 一、228 最终结论如何映射到正式产品设计（报告项 1）

228 的定案是：**「任意真实服务器已经生成的最终矿物状态」不是 Seed 的唯一函数**，
而是 `Seed + 版本/worldgen + 运行时 FEATURES 生成历史 → 最终世界状态`。

正式设计据此拆成**两条正交的轴**，而不是把五种状态塞进一个大枚举：

| 轴 | 正式类型 | 回答的问题 | 谁产出 |
| --- | --- | --- | --- |
| Seed 预测确定性 | `PredictionCertainty` | 「只凭种子能确定到什么程度」 | 正式预测器 + 调度敏感分析器 |
| 服务器观察状态 | `OreObservationState` | 「服务器把这个位置的真实状态给了客户端没有、给的是什么」 | 下一阶段接服务器后产出 |

228 里那张「产品语义边界表」的五行，映射关系逐条如下：

| 228 的写法 | 本轮正式落点 |
| --- | --- |
| `DETERMINISTIC` | `PredictionCertainty.DETERMINISTIC`（**本阶段不产出**，见第 22 节） |
| `SCHEDULE_SENSITIVE` | `PredictionCertainty.SCHEDULE_SENSITIVE`（本阶段可产出，需正面证据） |
| （228 未列） | `PredictionCertainty.UNRESOLVED`（本阶段默认结论，口径第二十二节要求） |
| `OBSERVED_CONFIRMED` | `OreObservationState.CONFIRMED` |
| `OBSERVED_MISSING` | `OreObservationState.MISSING` |
| `SUSPICIOUS` | `OreObservationState.SUSPICIOUS`（**只存在于模型定义，不自动产生**） |
| （228 未列） | `OreObservationState.UNOBSERVED` |

同时按口径第三十一、三十二节：本轮**没有**做 Seed 验证、**没有**接 AutoMiner，
架构上只保证以后能接。

---

## 二、正式 package tree（报告项 2、3）

```
src/main/java/com/yiyiaddon/seed/            ← 全部为本轮新增（git status: ?? 未跟踪目录）
├── model/
│   ├── OreType.java                          30 行
│   ├── OreSource.java                        67 行
│   └── SeedOreTarget.java                    36 行
├── observation/
│   ├── OreObservationState.java              51 行
│   └── OreObservation.java                   38 行
├── prediction/
│   ├── DiamondSeedPredictor.java            170 行
│   ├── PredictionSession.java               346 行
│   ├── PredictionResult.java                 97 行
│   ├── PredictedOre.java                     54 行
│   ├── PredictionCertainty.java              63 行
│   └── ScheduleSensitivityAnalyzer.java     165 行
└── worldgen/
    ├── OfflineWorldgenContext.java          176 行
    ├── OfflineChunkCache.java                86 行
    ├── OfflineChunkHolder.java              104 行
    ├── OfflineChunkRegion.java              126 行
    ├── OfflineChunkPipeline.java            323 行
    └── OreChunkReader.java                   89 行
```

合计 **17 个类 / 1789 行**（含中文注释与逐条源码依据）。

**两处与用户建议结构的差异（均属口径允许的「微调」，理由写明）**：

1. **worldgen 层多了一个 `OreChunkReader`**：它把「从区块里读出某类矿的坐标」从预测逻辑里拆出来
   （只读、不判定、不参与预测）。没有它，`PredictionSession` 就要自己带一个 40 行的嵌套扫描循环，
   违反「职责清晰、不巨型类」。
2. **没有创建 `internal/` 子包**：本轮没有任何类需要「对其他模块隐藏」——
   `worldgen` 层的 6 个类是被 `prediction` 层合法调用的实现，`model` / `observation` 是纯数据。
   造一个空包不符合「只有本轮真正需要的类才创建」。

---

## 三、每个类的职责（报告项 4）

| 类 | 职责（一句话） | 关键约束 |
| --- | --- | --- |
| `OreType` | 矿物种类枚举（本阶段只有 `DIAMOND`） | 名字与结构可扩展，禁止 `DiamondPosition` 这类一扩就废的命名 |
| `OreSource` | 矿物来源分类（`ORE_FEATURE`/`STRUCTURE`/`FOSSIL`/`OTHER_WORLDGEN`/`UNATTRIBUTED`） | 逐格归属未实现 ⇒ 一律 `UNATTRIBUTED`，不冒充已支持来源 |
| `SeedOreTarget` | 一次预测请求（种子 / 维度 / 目标区块 / 矿物） | 输入边界的数据载体，不含任何真实世界字段 |
| `OreObservationState` | 服务器观察状态枚举 | 只建模型，不接服务器 |
| `OreObservation` | 一条观察记录（坐标 / 矿物 / 状态 / 真实方块名） | 只建模型，`SUSPICIOUS` 不自动产生 |
| `PredictionCertainty` | Seed 预测确定性枚举 | 与观察状态完全独立 |
| `PredictedOre` | 一块被预测出来的矿（坐标 / 种类 / 确定性 / 来源 / 来源 viewer / 冲突 viewer） | 不含真值、不含装饰 batch、不含日志台账 |
| `PredictionResult` | 一次预测的结果与统计（含成败与失败原因） | **失败 ≠ 没有矿** |
| `PredictionSession` | 一个「种子 + 维度」下共享的一份离线世界 | 会话级同步；缓存按 ChunkPos + 生成状态隔离 |
| `ScheduleSensitivityAnalyzer` | 保守的调度敏感分类（自带第二份离线世界做反向顺序复核） | 只出正面证据，不做反面推断 |
| `DiamondSeedPredictor` | 正式对外 API（会话管理 + 维度/矿物边界 + 失败结果） | 不引用客户端全局单例 |
| `OfflineWorldgenContext` | 离线 worldgen 上下文（BiomeSource / 生成器 / RandomState / 结构状态） | 只由种子 + 注册表 + 原版配置决定 |
| `OfflineChunkCache` | 一份离线世界的全部区块产出（键 = ChunkPos，按状态分槽） | 一个缓存 = 一份世界状态，两个流水线各一份 |
| `OfflineChunkHolder` | 单区块多状态持有者（原版 `GenerationChunkHolder` 子类） | 只覆盖读方法，不用 Accessor Mixin |
| `OfflineChunkRegion` | 离线生成区域视图（原版 `WorldGenRegion` 子类） | 只换 5 处「种子相关读取」，并计数自证没读真实世界 |
| `OfflineChunkPipeline` | 照原版 `ChunkPyramid` / `ChunkStatusTasks` 驱动各生成阶段 | 幂等；写半径与 viewer 集合现算 |
| `OreChunkReader` | 读出一个区块里某类矿物的坐标 | 只读；区块缺失抛异常而不是返回空集合 |

---

## 四、dev.seedpoc 与正式代码的依赖方向（报告项 5）

```
        （允许：dev → 正式）                 （禁止：正式 → dev）
com.yiyiaddon.dev.seedpoc  ────────→  com.yiyiaddon.seed
   FormalSeedRegression（新）             DiamondSeedPredictor / PredictionSession / …
   OfflinePredictionSession（Oracle）
   TargetChunkPredictor（Oracle）
```

- **机器可核对**：`grep -r "dev.seedpoc" src/main/java/com/yiyiaddon/seed` → **0 命中**。
- 反向唯一接触点是本轮新增的 `dev.seedpoc.FormalSeedRegression`（dev 侧调用正式层做回归），
  另加 `SeedPocFlags.formal()` 开关与 `SeedPocEntry` 里一行受开关守卫的分发。
- 正式层里**没有一行**引用 PoC 的 `FeatureWriteJournal` / `OreCandidateJournal` / `ObservedOrderReplay` /
  `TruthComparator` / 任何 Round 驱动器 / 任何真实世界取证装置。

---

## 五、PredictionCertainty 定义（报告项 6）

| 值 | 定义 | 本阶段是否产出 |
| --- | --- | --- |
| `DETERMINISTIC` | 根据当前已实现的分析，该目标不依赖已知调度歧义 | **否**（无证明程序，见第 22 节） |
| `SCHEDULE_SENSITIVE` | 已证明存在至少两种合法 FEATURES 执行情形，会改变该 BlockPos 是否为目标矿 | 是（需正面证据） |
| `UNRESOLVED` | 当前正式分析器无法证明它属于前两种中的哪一种 | 是（默认） |

**硬约束**：未知绝不偷偷当确定性；`UNRESOLVED` 不允许被调用方当确定性使用（类注释已写明）。

---

## 六、OreObservationState 定义（报告项 7）

| 值 | 定义 |
| --- | --- |
| `UNOBSERVED` | 服务器还没把这个位置的真实状态提供给客户端 |
| `CONFIRMED` | 真实 BlockState 与矿物目标相符 |
| `MISSING` | Seed 候选存在，但服务器当前位置不是该矿物 |
| `SUSPICIOUS` | 服务器显示矿物，但无法被当前已知合法 Seed 生成候选解释 |

---

## 七、为什么两个状态维度必须拆开（报告项 8）

因为它们的**证据来源完全不同**，合并会立刻产生 228 已经实测过的误报：

- `PredictionCertainty` 只由 **纯 Seed 离线计算** 得出，回答「这个位置在我们自己的算法里稳不稳」；
- `OreObservationState` 只由 **服务器给客户端的真实状态** 得出，回答「现场实际是什么」。

若合并成一个枚举，就必然出现「服务器显示钻石但 Predictor 没有 ⇒ 标假矿」这种推断，
而 228 已经证明：那块「多出来的钻石」在真实世界上是**可复现的合法产物**（受控顺序 B 系列 3/3）。
把调度歧义当作弊证据，是这套系统最危险的误判方向，因此两条轴在类型层面就禁止互相赋值。

`SCHEDULE_SENSITIVE` 与 `SUSPICIOUS` **没有任何蕴含关系**：前者是「Seed 端有歧义」，
后者才是「现场出现了解释不了的矿」。

---

## 八、PredictedOre 数据结构（报告项 9）

```java
record PredictedOre(
    BlockPos position,              // 坐标
    OreType oreType,                // 矿物种类
    PredictionCertainty certainty,  // Seed 预测确定性
    OreSource source,               // 来源分类（本阶段恒为 UNATTRIBUTED）
    ChunkPos originViewer,          // 是哪一次 FEATURES 把它写进目标区块的（诊断，可为 null）
    List<ChunkPos> conflictingWriters // 曾改变该坐标钻石存在性的 viewer（只在 SCHEDULE_SENSITIVE 时非空）
)
```

- **允许的诊断元数据**：`originViewer`（跨区块写入归属）、`conflictingWriters`（局部冲突图证据）。
- **明确不含**：真实世界 truth、真实 decoration batch、真实服务端生成顺序、FeatureWriteJournal 数据、
  测试期 snapshot —— 全部与口径第九节一致。

---

## 九、PredictionResult 数据结构（报告项 10）

```java
record PredictionResult(
    SeedOreTarget request,   // 请求（含目标 ChunkPos / 种子 / 维度 / 矿物）
    List<PredictedOre> ores, // 预测集合（失败时为空）
    boolean success,         // 是否成功
    String failureReason,    // 失败原因（中文；成功为 null）
    long elapsedMillis,      // 本次预测耗时（含调度敏感复核）
    Stats stats              // 统计与诊断
)
```

`Stats` 字段：`protoChunks`（新建离线区块）、`stageExecutions`、`cacheHits`、`heldChunks`（会话缓存规模）、
`hostChunkSourceQueries`（必须为 0 的自证指标）、`foreignWriterViewers`、`scheduleAnalysisExecuted`、
`deterministicCount` / `scheduleSensitiveCount` / `unresolvedCount`、`notes`（中文诊断行）。

**不含** Truth / FN / FP —— 那些属于测试比较层（口径第十二节）。
`success=false` 时 `ores` 必为空，语义是「本次预测不成立」，调用方必须按失败处理（口径第三十九节）。

---

## 十、正式 Predictor API（报告项 11）

```java
try (DiamondSeedPredictor predictor = new DiamondSeedPredictor(serverLevel)) {
    PredictionResult result = predictor.predict(seed, new ChunkPos(0, 0));   // 主世界钻石
    // 或： predictor.predict(SeedOreTarget.diamond(seed, chunk))
    //      predictor.predict(new SeedOreTarget(seed, Level.OVERWORLD, chunk, OreType.DIAMOND))
    if (result.success()) { /* 使用 result.ores() / result.scheduleSensitiveCount() … */ }
}   // close() 释放全部会话
```

- `invalidate(seed, dimension)`：种子被改 / 配置变化时让某个会话失效；
- `clear()` / `close()`：退出世界时整体释放；
- `sessionCount()` / `cachedChunks()`：缓存规模自查。

调用者**不需要**知道 `OfflineChunkPipeline` / `ProtoChunk` / `WorldGenRegion` / `FeatureSorter`。
不支持的维度或矿物返回**失败结果**（而不是空集合）。

---

## 十一、正式 Predictor 输入边界（报告项 12、13）

**允许使用**：`SeedOreTarget` 四项 + 当前版本原版 注册表 / `NoiseGeneratorSettings` /
worldgen 配置 / 与种子无关的环境参数（维度类型、世界高度、模板管理器、调色板工厂）。

宿主 `ServerLevel` 被用到的地方只有 6 处，全部与「这个世界生成了什么」无关：
`registryAccess()`、`dimension()`、`structureManager()`、`getStructureManager()`、
`palettedContainerFactory()`、`getServer().getWorldGenSettings().options().generateStructures()`。

**禁止**：真实 Server Chunk BlockState / pre-diamond / 矿石位置 / decoration batch / 首次生成历史 /
真实 FEATURES 调度顺序 / 真实 `WorldGenRegion` 状态。

**是否读取真实 Chunk（报告项 13）**：**不读**，且这是可核对数字而不是声明：

- `OfflineChunkRegion#getChunkSource()` 是原版唯一会把请求转给宿主 `ServerChunkCache` 的口子，正式层对它计数；
- 两次回归里**每个目标**的「宿主 ChunkMap 查询次数增量」都是 **0**；
- 迁移回归装置本身也**没有一次** `level.getChunk(...)`：验收全程不需要真实区块。

---

## 十二、Offline worldgen 迁移了哪些类（报告项 14）

| 正式类 | 来源 | 算法是否改动 |
| --- | --- | --- |
| `OfflineWorldgenContext` | PoC `OfflineWorldgenContext` | **未改**（四件自建物逐行相同） |
| `OfflineChunkRegion` | PoC `OfflineChunkRegion` | **未改**（5 处覆盖 + 宿主查询计数） |
| `OfflineChunkHolder` | PoC `OfflineChunkHolder` | **未改** |
| `OfflineChunkPipeline` | PoC `OfflineChunkPipeline`（只取 `prepare` / `decorate` / `applyStage` 主链） | **未改**；删掉了 PoC 的装饰半径/多世界对照/报告文本等研究分支 |
| `OfflineChunkCache`（新拆） | PoC 里 `OfflineChunkPipeline` 内部的 `holders` 表 + `StaticCache2D` 窗口 | 纯拆分，无语义改动 |
| `OreChunkReader`（新拆） | PoC `FinalOreCollector` 的扫描部分（6 section / 两种钻石方块） | **未改**（同一份扫描口径） |

**仍然全部调用原版实现**：`BiomeSource` / `NoiseBasedChunkGenerator` / `RandomState` /
`ChunkGeneratorStructureState` / `ChunkPyramid` / `ChunkStep` / `ChunkStatusTasks` 五段 /
`WorldGenRegion` —— 正式化**没有**重写任何一条世界生成公式（口径第十六节）。

**写半径不是写死的（报告项 16）**：`OfflineChunkPipeline.featureWriteRadius()` 每次运行都从
`ChunkPyramid.GENERATION_PYRAMID.getStepTo(FEATURES).blockStateWriteRadius()` 现算；
26.1.2 实测 = 1，因此 viewer 集合 = `target ± 1`（9 个），但代码语义是 `target ± writeRadius`
（`viewersFor` 用 `canWriteInto` 逐格判定成员，没有第二套口径）。
报告里同时打印该步骤的 `directDependencies` / `accumulatedDependencies` 作为证据。

---

## 十三、哪些 PoC 类明确没有迁移（报告项 15）

**留在 `dev.seedpoc` 继续当 Oracle / 回归基线，正式层一行都没有搬**：

`FeatureWriteJournal`、`OreCandidateJournal`、`ObservedOrderReplay`、`Round7Runner`、
`FinalOreTruthComparator`（TruthComparator）、`StageComparator`、`GenStageCapture`、
`OfflineStageCapture`、`StageSnapshotCapture`、`RegionStateLedger`、`OreBlockLedger`、
`ShadowLevelFactory`、`ShadowWorldGenContext`、`OreDecorationReplay`、`DiamondFeatureOracle`、
`OreVeinTrace`、`ChunkOrder*` 全套、`PredictionRegressionSuite`、`SeedPocReport`、
`SeedPocWorldFactory`、各种 Probe / Runner。

判定标准就是口径第十五节那一句：**这个类是不是正式算法必需？**
「真实世界取证 / 对照重放 / 报告器 / 驱动器」一律不是。

---

## 十四、PredictionSession cache 设计（报告项 17）

- 会话键 = **（Minecraft 版本 id, 种子, 维度）**；版本取 `SharedConstants.getCurrentVersion().id()`
  （26.1.2 本线源码 `WorldVersion.java:11`；不用已弃用的 `WORLD_VERSION` 常量，也不写死版本号）。
- 会话内：区块键 = `ChunkPos`；**键下按生成状态分槽**（`OfflineChunkHolder` 每个状态一份产出），
  因此「某区块跑到哪个阶段」可以单独复用 —— 相邻目标比冷启动快一个量级的来源就是这个。
- 一个会话里其实有**两份**离线世界：基线世界（`OfflineChunkCache`）与调度敏感复核世界
  （`ScheduleSensitivityAnalyzer` 自带的一份）。反向顺序写进去的方块不会污染基线世界。
- 实测（fixed 阶段末）：基线 744 个区块 / 复核 743 个区块；会话累计 2 个（两个种子）。

## 十五、Session 生命周期（报告项 18）

| 场景 | 接口 | 行为 |
| --- | --- | --- |
| 同一个「种子 + 维度」多目标 | `predict(...)` 复用会话 | 已铺好的阶段直接跳过（缓存命中） |
| 种子被修改 | `DiamondSeedPredictor#invalidate(seed, dimension)` | 关闭并释放该键的会话，其余键不受影响 |
| 维度切换 | 会话键里已含维度 | 自动使用另一个会话对象 |
| 退出世界 / 停止功能 | `clear()` / `close()` | 释放全部会话；关闭后的预测请求返回失败结果 |
| 内存回收 | `PredictionSession#clear()` | 清空基线世界与复核世界的全部区块产出 |

本阶段**没有**做极限 LRU（口径第十九节明确不要求）。

## 十六、Seed / dimension 缓存隔离（报告项 19）

- 隔离是**结构性**的：跨种子/跨维度不可能共用同一份缓存对象（键不同就换会话）。
- 实测（A→B→A）：种子 A 第一次 45 个 / 种子 B 31 个 / 种子 A 第二次 45 个，
  **两次 A 逐 BlockPos 完全一致**，B 没有污染 A（fixed 与 regression 两次回归都通过）。

---

## 十七、ScheduleSensitivityAnalyzer 当前能力（报告项 20）

**它做了什么**：

1. 触发条件 = 「基线执行中，除目标自身外还有其它 viewer 真的往目标区块里写过方块」。
   判定用**目标区块全区块方块指纹**（`Block#getId` 逐格滚动哈希，`Block.java:132`），
   **不是**只看钻石成员 —— 只看钻石会漏掉「砾石/凝灰岩把钻石候选挤掉、但钻石成员没变」这一族竞争
   （228 第七节实测的正是这种「先写者胜」）。
2. 触发后，在**独立的第二份离线世界**里按「基线顺序的整体反向」跑一遍合法 FEATURES 调度
   （目标区块先装饰、邻域由近到远），比较两次的最终集合：
   - **基线有、反向没有** ⇒ 这些坐标标 `SCHEDULE_SENSITIVE`（正面证据）；
   - 其余 ⇒ `UNRESOLVED`；
   - 反向多出来的坐标只如实登记数量（不属于本次预测集合）。

**它明确不做**：9! 全排列、把「邻域先 vs 目标先」当成整个状态空间、
以「跑了几种顺序结果都一样」反推 `DETERMINISTIC`、读任何真实世界台账或真实装饰顺序。

**已知能力边界（如实登记）**：反向顺序只是**两种**合法情形，因此它的产出必然只能是
「发现了敏感的」或「未解析的」——这与口径第二十一、二十二节要求的保守方向一致。
真实世界里同一波内的**并发交错**（228 第九节实测：出生点附近连「谁先写」都不受控）
目前无法穷举，本阶段不声称覆盖。

## 十八、三种分类分别成立于什么条件（报告项 21、22、23）

| 分类 | 成立条件（本阶段实现） |
| --- | --- |
| `DETERMINISTIC` | **本阶段不产出**。要标它必须能证明「所有合法并发历史结果相同」，当前只有两种顺序的对照，没有这个证明程序 ⇒ 一律不下这个结论。 |
| `SCHEDULE_SENSITIVE` | 存在至少两种合法 FEATURES 执行情形，使该 BlockPos 是否为目标矿不同。本阶段的证据形式：同种子下「由远到近、目标最后」与「整体反向」两次纯离线预测，该坐标在其中一次存在、另一次不存在。 |
| `UNRESOLVED` | 其余全部（包括「两种顺序结果相同」与「根本没有跨 viewer 写入、跳过复核」）。 |

**特别说明**：跳过复核（无跨 viewer 写入）时也标 `UNRESOLVED`，不标确定性 ——
因为「没有观测到竞争」不等于「证明了不存在竞争」。

---

## 十九、Seed 2 已知争议格的分类结果（报告项 24）

实机结果（两次回归均相同）：

```
Seed 2 目标 (-400,380)：23 个（调度敏感 1 / 未解析 22 / 确定性 0）
  · 基线执行中改过目标区块方块的其它 viewer：7 个
  · 反向顺序复核：基线 23 个 / 反向 22 个；基线有而反向没有 1 个、反向有而基线没有 0 个
  · 已知争议位置 (-6385,-59,6085) 钻石 / 调度敏感 / 来源未归属 / 来源 viewer(-399,380) / 冲突 viewer 1 个
  · 本目标是否存在被标成确定性的坐标：无
```

对照 228 第七节：真值 A 系列 22（gravel）/ B 系列 23（deepslate_diamond_ore）、
争议格的竞争双方是 `viewer(-400,380)` 的 `ore_gravel` 与 `viewer(-399,380)` 的 `ore_diamond`。
本轮正式分析给出的 **23（基线顺序）/ 22（反向顺序）与争议格 1 个、来源 viewer `(-399,380)`**
与 228 的取证逐项吻合 —— 说明这不是装置造出来的假差异，而是真实存在的调度歧义被正式分析复现了。

**判定 B 通过**：该坐标没有（也不可能）被标成 `DETERMINISTIC`。

---

## 二十、Seed 20260922 正式 vs PoC 回归（报告项 25）

独立进程一次（`-Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed`）：

```
目标 (0,0)   ：PoC 45 / 正式 45 → 漏报 0 错报 0 → 逐 BlockPos 完全一致
目标 (1,0)   ：PoC  9 / 正式  9 → 逐 BlockPos 完全一致
目标 (0,1)   ：PoC 29 / 正式 29 → 逐 BlockPos 完全一致
目标 (1,1)   ：PoC 21 / 正式 21 → 逐 BlockPos 完全一致
目标 (-1,0)  ：PoC 22 / 正式 22 → 逐 BlockPos 完全一致
目标 (0,-1)  ：PoC 27 / 正式 27 → 逐 BlockPos 完全一致
目标 (-1,-1) ：PoC 21 / 正式 21 → 逐 BlockPos 完全一致
目标 (2,2)   ：PoC 18 / 正式 18 → 逐 BlockPos 完全一致
目标 (3,-1)  ：PoC 18 / 正式 18 → 逐 BlockPos 完全一致
目标 (-2,3)  ：PoC 33 / 正式 33 → 逐 BlockPos 完全一致
合计：真值 243 / 预测 243 / 匹配 243 / 漏报 0 / 错报 0；逐 BlockPos 完全一致 10/10；正式预测失败 0 个
```

与 227（第六轮）/228（第七轮）的 `243/243、FN 0、FP 0、10/10` **逐字相同** ⇒ 迁移没有丢任何一格。

（同一行的「真值」列在本装置里就是 PoC 基线集合，不是真实世界最终状态 —— 本轮验收口径不含真实世界，
理由见 228 定案。）

**分类统计（同一批目标）**：10 个目标 243 个坐标，`调度敏感 0 / 未解析 243 / 确定性 0`。
其中 **8 个目标**观测到跨 viewer 写入并执行了反向复核（`(1,1)` 与 `(-1,-1)` 这两个区块没有其它 viewer 写入，
按保守口径跳过复核），**两种合法顺序给出的集合完全相同**；
按口径第二十二节，它们仍然只标 `UNRESOLVED`，**不标确定性**。

## 二十一、Seed 12345 正式 vs PoC 回归（报告项 26）

独立进程一次（`-Dyiyiaddon.seedpoc.seed=12345 -Dyiyiaddon.seedpoc.round6.stage=regression`）：

```
目标 (0,0)    ：PoC 31 / 正式 31 → 逐 BlockPos 完全一致   （228 第七轮 Predictor 固定顺序 h=3af6d n=31）
目标 (-1,-1)  ：PoC 29 / 正式 29 → 逐 BlockPos 完全一致   （227 第六轮 Predictor h=fffcbbf3 n=29）
目标 (-25,17) ：PoC 24 / 正式 24 → 逐 BlockPos 完全一致
目标 (120,-130)：PoC 26 / 正式 26 → 逐 BlockPos 完全一致
合计：真值 110 / 预测 110 / 匹配 110 / 漏报 0 / 错报 0；逐 BlockPos 完全一致 4/4；正式预测失败 0 个
```

**分类结果（本阶段额外产出，且与 228 的结论互相印证）**：

| 目标 | 基线顺序 | 反向顺序 | 调度敏感 |
| --- | --- | --- | --- |
| `(0,0)` | 31 | 38 | 0（反向多出 7 个，基线成员全部稳定） |
| `(-1,-1)` | 29 | 22 | **7** |
| `(-25,17)` | 24 | 24 | 0 |
| `(120,-130)` | 26 | 26 | 0 |

`(-1,-1)` 正是 228 第九节里「真实真值 29/27/24/27 不可重复、连观测顺序重放都失效」的那个区块 ——
正式分析现在把它标出 **7 个调度敏感坐标**，且 `(-25,17)` / `(120,-130)`（228 里顺序无关的两个目标）
保持 0 敏感。这是调度敏感分类在**已知样本上判对**的证据，不是调参调出来的数字。

---

## 二十二、性能数据（报告项 27）

| 指标 | 正式 Predictor（本轮，落盘报告口径） | PoC（227 第六轮口径） |
| --- | --- | --- |
| 冷启动目标（首个目标） | **2045 ms** / 新建 529 区块 / 阶段执行 1240 | 1530 ms / 新建 529 区块 / 阶段执行 1240 |
| 相邻目标（第 2 个） | **195 ms** / 新建 23 区块 / 缓存命中跳过 **1162** | 112 ms / 新建 23 区块 / 缓存命中跳过 1162 |
| 同会话远目标 | 158～429 ms | 187～276 ms |
| 会话缓存规模（fixed 10 目标后） | 基线 744 + 复核 743 区块 | 744 区块 |
| 粗略内存（阶段结束） | 已用 679 MB / 当前堆 928 MB / 上限 12064 MB | 已用 751 MB / 1104 MB / 12064 MB |
| 装置总耗时（含 PoC 对照） | 10026 ms（fixed）/ 12134 ms（regression） | 9324 ms（fixed 阶段） |

（冷启动耗时在多次运行间有 ±5% 波动：同一目标实测 1952 / 2045 / 2127 ms，上表取与落盘报告同一次运行的值。）

**为什么正式侧略慢（冷启动 +34%、相邻 +74%）且不算「一个数量级」的回退**：

1. 每遍 viewer 之后多一次**目标区块全区块方块指纹**扫描（用于第 17 节那个正确的触发判据）；
2. 触发后要跑**第二份离线世界**做反向顺序复核（本次 fixed 有 8/10 个目标触发）；
3. 本装置一轮里跑了**三份世界生成链**（正式基线 + 正式复核 + PoC 对照），而 PoC 那一轮只有两份。

相邻目标的 1162 次「缓存命中跳过」与 PoC 完全同值 ⇒ 复用机制没有退化。
本阶段不做极限优化（口径第三十七节），但上述差异**有明确原因、可逐项定位**。

---

## 二十三、compileJava / build / runClient（报告项 28、29、30）

**代码冻结与取证顺序**：cleanup（删除无用访问器 / 复用写半径判据 / 去掉无用接口）之后重跑了
`build` 与两次回归；最后只再改了一处**失败分支的中文日志常量**（`LOG_KEY`），
之后重跑 `build`（EXIT=0）并**重跑 fixed 回归**（结论与判定不变，见第 20 节与落盘文件）。
regression 阶段的那份落盘文件产生于同一份逻辑代码（差仅该日志常量）。

```
> .\gradlew.bat compileJava --console=plain -q   BUILD SUCCESSFUL（EXIT=0）
> .\gradlew.bat build       --console=plain -q   BUILD SUCCESSFUL（EXIT=0，jar/assemble/check 全通过）
```

**正常启动验证（未开任何 seedpoc 系统属性）**：`.\gradlew.bat runClient`（用户日常
`run-26.1.2/` 目录，100+ Mod 环境）

```
[10:53:54] (yiyiaddon) yiyiaddon initialised (baritone localisation: on)
[10:54:04] (Minecraft) Sound engine started          ← 已进标题界面
[10:54:07] 客户端停在标题界面，无异常、无 yiyiaddon 相关 ERROR
```

- 整段日志里**没有**「种子挖矿PoC」任何一行 ⇒ `SeedPocEntry.installIfEnabled()` 立刻返回，
  正式实验与 PoC 探针都没有挂载；
- 正式包 `com.yiyiaddon.seed` 不被任何业务入口引用（本轮没有接 UI/AutoMiner），因此它在正常游戏里
  **根本不会被加载执行**，正常游戏行为与之前一致。

**PoC Mixin 处置（口径第四十四节自查）**：现有 6 个开发期探针 Mixin
（`FeatureWriteJournalMixin` / `PlacedFeatureStageCaptureMixin` / `OreFeatureTraceMixin` /
`GenStageCapture` 相关三个）**只服务 dev 取证**，正式包对它**零引用**；
默认启动时它们的记录开关全部为关（`FeatureWriteJournal.arm` 内部第一句就查第七轮开关），
**不会产生任何 Round7 写入台账**。

---

## 二十四、AutoMiner 是否零修改 / UI 是否仍未开发（报告项 31、32）

- **AutoMiner 零修改**：`git status` / `git diff --stat` 里**没有任何** `feature/mining/**`
  或 Baritone 相关文件；`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` /
  `MiningFastBreakController` / 精准采集 / 时运 / 食物 / 回家 / 背包一行未改，
  本轮也没有把任何 Seed target 交给 Baritone。
- 本轮已跟踪业务文件的改动只有三处，且**全部来自前几轮（第五/六/七轮 PoC 遗留，非本轮产生）**：
  `build.gradle`（PoC run 配置）、`YiyiAddonClient.java`（一行受系统属性守卫的挂载调用）、
  `yiyiaddon.mixins.json`（PoC Mixin 注册）。本轮新增代码全部落在**未跟踪**的
  `src/main/java/com/yiyiaddon/seed/` 与 `src/main/java/com/yiyiaddon/dev/seedpoc/` 里。
- **UI 仍未开发**：没有「种子挖矿」Tab、没有 Seed 输入框/开关、没有状态页、
  没有 Seed 验证、没有假矿检测、没有 `SeedMiningConfig`、没有扩其它矿物/下界/26.2/26.3。
- Seed 值仍然只由 **测试装置 / API 参数** 输入（口径第四十一节）。

---

## 二十五、本轮新增 / 未新增的东西（自查）

**新增**：`com.yiyiaddon.seed`（17 类）、`dev.seedpoc.FormalSeedRegression`、
`SeedPocFlags.formal()/formalConflictSeed()/formalConflictTargets()`、`SeedPocEntry` 一行分发、
`PredictionRegressionSuite.chunksOf` 由 private 改 package-private（复用同一份解析，不留第二份）。

**未新增**：UI、指令、配置项、Seed 验证、假矿检测、观察器、AutoMiner 接入、
其它矿物、下界、26.2/26.3，以及第八/第九轮 PoC。

---

## 二十六、本轮遗留 / 已知边界（如实登记）

1. **`OreSource` 逐格归属未实现**：26.1.2 主世界的钻石有**两条**来源路径（已查源码，不是假设）：
   - `OrePlacements.java:49-52, 184-204` 的四条 `ore_diamond*`；
   - `CavePlacements.java:31-32, 98-106` 的 `fossil_lower` → `CaveFeatures#FOSSIL_DIAMONDS`
     → `ProcessorLists#FOSSIL_DIAMONDS`（`ProcessorLists.java:607-611`，煤矿规则替换成深板岩钻石矿）。

   两条路径都在同一次 `FEATURES` 里发生，逐格区分需要对每条 placed_feature 单独记账
   （只有 dev 探针 `FeatureWriteJournal` 有这份能力，正式层禁止依赖它）。
   因此本阶段**所有预测坐标一律标 `UNATTRIBUTED`**，并把这条边界写进枚举注释 ——
   **不冒充已支持的来源**。
2. **`DETERMINISTIC` 不可达**（本阶段设计如此），见第 18 节。
3. **反向顺序只是两种合法情形**，不构成对并发交错的完全覆盖，见第 17 节。
4. **PoC 与正式层的 worldgen 代码目前是两份**（口径第十五节允许、第四十三节要求保留 PoC 当 Oracle）；
   正式层稳定后再单独清理，届时 PoC 侧改为直接调用正式层。

---

## 二十七、下一阶段建议（报告项 33）

按口径第五十一节，本轮到此停止，等待用户审查本报告。若 229 通过，建议第二阶段按以下顺序推进：

1. **UI + Seed 配置/状态**（口径划定的第二阶段内容）：Seed 输入、开关、状态页；
   在此之前先确定「Seed 从哪来」（手动输入 / 从服务器读）与界面文案（中文）。
2. **观察层接入前的两个前置**：`PredictionCertainty` 的消费口径（UI 如何展示「未解析」）
   与 `OreObservationState` 的 Chunk 数据来源（客户端已同步的 `LevelChunk` 即可，无需新包钩子）。
3. **矿物选择复用**：接入时读现有自动挖矿的矿物选择，**不要**在 Seed 模块里维护第二份开关
   （口径第三十三节）。
4. **调度敏感分析的加强方向**（不属于第二阶段，仅登记）：在局部冲突图上做 writer 级别的
   顺序交换试验，把「两种顺序」扩展成「冲突对交换」而不是全排列。

---

## 附：复现命令与落盘证据

```powershell
# A. Seed 20260922 固定 10 目标（正式 vs PoC + 分类 + 会话隔离 + 缓存复用）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.formal=1 -Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# B. Seed 12345 旧回归四区块
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.formal=1 -Dyiyiaddon.seedpoc.seed=12345 -Dyiyiaddon.seedpoc.round6.stage=regression -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# C. 正常启动（不开任何 seedpoc 属性）
Remove-Item Env:\JAVA_TOOL_OPTIONS -ErrorAction SilentlyContinue
.\gradlew.bat runClient
```

落盘证据（运行目录 `run-26.1.2-seed-predict-test/`）：

```
seedpoc-正式化回归-fixed-20260922.txt        10 目标逐 BlockPos 对照 + 分类 + 隔离 + 缓存复用 + 判定汇总
seedpoc-正式化回归-regression-12345.txt      Seed 12345 四区块同上
```

两次回归的「判定汇总」：

```
【判定 A】正式 Predictor vs PoC 固定顺序预测器逐 BlockPos 一致：通过（fixed 10/10、regression 4/4，失败 0）
【判定 B】Seed 2 已知争议位置未被错误标成确定性：通过
【判定 C】会话种子隔离 A→B→A：通过
【判定 D】相邻目标缓存复用：通过
```
