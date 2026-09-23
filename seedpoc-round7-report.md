# 228 · 追加 · 《种子挖矿 PoC 第七轮最终收口报告 —— FEATURES 调度因果定案》

日期：2026-09-23
阶段：种子挖矿 PoC **第七轮（最终收口轮）**：`Seed → 最终自然钻石 BlockPos` 的**原理边界定案**。
上游证据链：第三轮 [223]、第四轮终版 [225]、第五轮 [226]、第六轮 [227](227-追加-种子挖矿PoC第六轮报告-Target3x3离线最终预测.md)。

**验证方式**：全部为**实机验证**——Minecraft 26.1.2 真实客户端进程（`gradlew runClientSeedPredictTest`，独立运行目录
`run-26.1.2-seed-predict-test/`），每次重建全新世界（`SeedPocWorldFactory` 先删存档再建，报告里逐次登记
`WORLD_FRESH=1`）。本轮共 **24 次真实客户端实跑**，全部 `EXIT=0`。

---

## 零、最终结论（先给答案）

> **结论 B（FEATURES 调度因果定案）。**
>
> 已取得**可复现的真实世界因果证据**：相同 Seed / 相同版本 / 相同 worldgen 配置 / 相同目标区块，
> **只改变合法的 Chunk 请求顺序**（全部走原版 `ServerLevel#getChunk` → `ChunkGenerationTask` → `ChunkStatusTasks`，
> 没有一处伪造装饰），同一个格子 `(-6385,-59,6085)` 的真实最终方块在
> **gravel（5/5 次）** 与 **deepslate_diamond_ore（3/3 次）** 之间翻转，
> 目标区块的真实最终钻石集合随之在 **22（5/5）** 与 **23（3/3）** 之间翻转。
>
> 因此：**任意真实服务器已生成 Chunk 的「最终自然钻石状态」不是 Seed 的唯一函数**，
> 它还依赖该次生成历史（多个 Chunk 的 FEATURES 对同一格的竞争写入谁先落笔）。
> `Pure Seed 唯一最终状态` 这条研究线**到此结案**，不进入第八轮。

一句话总结因果链：

```
合法请求顺序改变
  ↓
目标区块与 8 个邻区块的「谁先装饰」改变（逐批号可观测）
  ↓
同格竞争写入的先后改变（全局写序号 AtomicLong 可观测）
  ↓
该格在 ore_diamond 候选判定那一刻的方块是 deepslate 还是 gravel/tuff
  ↓
canPlaceOre 通过 / 拒绝（矿石与砾石都不在对方的目标标签里 ⇒ 先写者胜）
  ↓
最终 diamond BlockPos 集合改变
```

---

## 一、第六轮结论复核（报告项 1）

| 227 的结论 | 第七轮复核 |
| --- | --- |
| 第六轮 Predictor = 共享离线世界 + 写半径现算（=1）+ 目标 ±1 的 9 个 viewer 各跑一遍 FEATURES | 本轮沿用，未改一处算法 |
| Seed 20260922 固定 10 target：243/243、FN 0、FP 0 | **本轮实测保持**（探针关 ×2、探针开 ×2，四次全部 `真值 243 / 预测 243 / 匹配 243 / 漏报 0 / 错报 0；逐 BlockPos 完全一致 10/10`） |
| Seed 12345 老回归：(0,0) 漏 5、(-1,-1) 真值不可重复、两个远端目标完全一致 | **全部复现**，并首次给出机制级证据（见第八、九节） |
| 多 Seed 泛化 28/30、FN 5 / FP 1 | 本轮做了 **Seed 2 泛化抽查（6 目标）**：真值 132 / 预测 133 / 匹配 132 / 漏报 0 / 错报 1 / 5-6，与第六轮**逐字相同**；唯一失败样本 Seed 2 `(-400,380)` 的 FP 已在本轮**定案为「另一种合法调度的真实结果」**（见第七节、12.1） |
| 224 修正：不能再说「最终钻石与装饰先后无关」 | 本轮**在真实世界上直接反证**了它（同 Seed 同目标，两种合法顺序 ⇒ 22 / 23） |

---

## 二、本轮研究问题（报告项 2）

只回答一句：

> 「真实 Minecraft 26.1.2 中，多个 Chunk 的 FEATURES 对同一格产生竞争写入时，
> **真实并发执行顺序能否因果性地改变最终自然钻石 BlockPos？**」

要求不是「批号不同」、也不是「两个世界结果不同」，而是完整因果链：
调度 / 写入先后变化 → 同一 BlockPos 的前置状态或最终覆盖变化 → OreFeature 判定变化 → 最终 diamond BlockPos 变化。

---

## 三、测试环境（报告项 3、4）

取自运行目录落盘的真值文件（机器可读字段）：

```
MC_ID            26.1.2
MC_NAME          26.1.2
FABRIC_LOADER    0.19.5
FABRIC_API       0.155.2+26.1.2
JAVA             25.0.4.1
MODS_HASH        3c9f3d95d6675b5c4e88aafc1eab1f353e94b0c7967609df9562049f3445aa73
C2ME             0
MODERNFIX        0
WORLD_FRESH      1
WORLD_PATH       D:\mcaddon\yiyiaddon\run-26.1.2-seed-predict-test\saves\seedpoc-known-seed
```

- 运行任务：`gradlew runClientSeedPredictTest`（独立运行目录 `run-26.1.2-seed-predict-test/`，
  与用户日常 `run-26.1.2/` 的 100+ Mod 环境完全隔离）。
- 加载 57 个 Mod，全部是 Fabric Loader / Fabric API 子模块 / Baritone 1.18.0 / DevAuth Neo（+其 jcef 依赖）
  / 本模组；**C2ME = 0、ModernFix = 0**。
- Baritone 仍是 required dependency，但**不参与 worldgen**（本轮所有结论都只涉及原版
  `ChunkStatusTasks` → `ChunkGenerator#applyBiomeDecoration` → `PlacedFeature` → `OreFeature` 这条链）。

---

## 四、本轮新增的实验装置（报告项 11、12 的取证基础）

全部位于 `com.yiyiaddon.dev.seedpoc`（PoC 代码，与业务层完全隔离）：

| 新装置 | 职责 |
| --- | --- |
| `FeatureWriteJournal` | **逐格 FEATURES 写入台账**：每次真正落笔取一个 `AtomicLong` 全局写序号（用户口径第十节要求的「主排序依据」，`nanoTime` 只作辅助）；记录 viewer / placed_feature / feature 类型 / 写入前后方块 / 是否跨区块 / 装饰批号 / feature 调用号 / 线程 / 是否离线重放 |
| `OreCandidateJournal` | **OreFeature 候选点级诊断**：矿脉序号、候选序号、写入前方块、是否可替换、是否消耗一次随机、是否被接受；两侧按 (viewer, feature, 矿脉号, 候选号) 逐项配对找第一处分叉 |
| `ObservedOrderReplay` | **Debug-only 观测顺序重放**：把某个真实世界观测到的 viewer 先后当输入，在共享离线世界里按同一顺序跑 FEATURES。**不被正式 Predictor / 业务 / UI 引用**（用户口径第十八、十九节） |
| `Round7Runner` | 第七轮驱动器：`case`（一次真实世界全量取证 + 三种顺序重放）/ `correlate`（跨运行相关性） |
| `FeatureWriteJournalMixin` | 注入 `WorldGenRegion#setBlock(BlockPos, BlockState, int, int)` 的 HEAD/RETURN |

### 4.1 一个必须写进报告的装置缺陷（本轮内发现并当场修复）

第一版台账只挂 `WorldGenRegion#setBlock`。实机第一测（Seed 2）得到

```
FEATURES 逐格写入台账：目标区块内真实侧 4224 条
y ≤ -48 的写入：0 条      ← 但真值在该区块有 y=-59 的钻石
```

逐字节核对 26.1.2 的 `OreFeature#doPlace` 才定位到原因：

```
884: invokestatic  OreFeature.canPlaceOre(...)
904: invokevirtual LevelChunkSection.setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)
```

**矿石写入走的是 `LevelChunkSection#setBlockState`，不经过 `WorldGenRegion#setBlock`。**
只挂 setBlock 的台账**一条矿石都看不到**（这正是「谁最后写这一格」在矿石上取证失败的根因）。
修复：在 `OreFeatureTraceMixin#canPlaceOre` 返回 `true` 时补记一条直接写入事件
（那一刻原版紧接着就是 `setBlockState`，中间没有任何判定，所以「接受 = 真的落笔」）。
修复后同一目标区块的写入条数从 4224 升到 5331，并首次出现矿石写入与「同格矿石竞争」记录。

**这条缺陷如实登记**：它是**取证装置的缺陷**，不是 Predictor 的缺陷；Predictor 的 243/243 与 28/30 都没有因此改变。

---

## 五、探针 OFF / ON 对照（报告项 5）

按用户口径第三十节，对一个**稳定成功案例**与一个**失败案例**各跑多次对照。

### 5.1 稳定成功案例：Seed 20260922 固定 10 target

四次独立进程（探针关 ×2、探针开 ×2；探针开 = 写入台账 + 候选点诊断全开）：

| 运行 | 探针 | 结果 | 真值可重复性汇总 |
| --- | --- | --- | --- |
| `archive-fixed-OFF1` | 关 | 真值 243 / 预测 243 / 匹配 243 / 漏报 0 / 错报 0；**10/10** | 可比较 10 个；预测摘要可重复 10 个（逐 BlockPos 一致 10 个）、真值摘要不可重复 **0** 个 |
| `archive-fixed-OFF2` | 关 | 同上 | 同上（真值摘要不可重复 0 个） |
| `archive-fixed-ON1` | 开 | 同上 | 同上（真值摘要不可重复 0 个） |
| `archive-fixed-ON2` | 开 | 同上 | 同上（真值摘要不可重复 0 个） |

**结论：① 第七轮新增探针没有破坏第六轮已经通过的 Predictor，243/243 基线保持；② 探针打开后真值摘要逐目标相同（10/10），没有可观测的探针效应。**

### 5.2 失败案例：Seed 12345 四个旧回归区块

四次独立进程（探针关 ×2、探针开 ×2），落盘摘要逐字节相同：

```
0,0,      预测h=3af6d      n=31 / 真值h=3db07      n=36 / 不一致
-1,-1,    预测h=fffcbbf3   n=29 / 真值h=fffd2d30   n=23 / 不一致
-25,17,   预测h=60ecd4     n=24 / 真值h=60ecd4     n=24 / 一致
120,-130, 预测h=fce9e4d3   n=26 / 真值h=fce9e4d3   n=26 / 一致
```

四次运行的 `合计：真值 109 / 预测 110 / 匹配 95 / 漏报 14 / 错报 15`、`逐 BlockPos 完全一致 2/4`、
`真值摘要不可重复 0 个` **完全一致**。

**结论：在这个失败案例上，探针 OFF 与 ON 的分布也完全相同 ⇒ 详细探针没有改变本轮的结论分布。**

---

## 六、Seed 20260922 基线是否保持（报告项 6、27）

保持。四次实测（见 5.1）全部：

```
真值 243 / 预测 243 / 匹配 243 / 漏报 0 / 错报 0 / 查全 100.00% / 查准 100.00%
逐 BlockPos 完全一致的目标区块：10/10
```

同时旧口径复算（只装饰目标自己）仍为 `真值 243 / 预测 234 / 匹配 234 / 漏报 9`，与第六轮报告一致。

---

## 七、Seed 2 `(-400,380)`：真实世界受控顺序反转（报告项 9、15～19）——**本轮最强证据**

### 7.1 两个都合法的请求顺序

| 场景 | 请求顺序（全部是原版 `getChunk`） | 目标在 3×3 里的装饰批号 |
| --- | --- | --- |
| **A 系列** | `(-400,380)`＝目标（直接请求目标区块，它的 3×3 在同一波里被装饰） | 5 / 9（4 个邻区块在它之前） |
| **B 系列** | `(-398,382) → (-398,378) → (-402,382) → (-402,378) → (-398,380) → (-402,380) → (-400,382) → (-400,378) → (-400,380)＝目标`（8 个「距目标 2 区块」的请求先把邻域装饰完，最后才请求目标） | 9 / 9（8 个邻区块全部在它之前） |

新鲜度自证（每次）：`驱动开始时目标 3x3 未生成 = true`、`目标区 region 文件存在 = false`、`WORLD_FRESH = 1`。

### 7.2 实测结果

| 场景 | 世界数 | 真实最终钻石 | 观测顺序重放 | 反事实 / 预测器顺序重放 |
| --- | --- | --- | --- | --- |
| **A 系列**（R7S2A1/A2/B1/B2/B3） | 5 | **22（5/5 全部 22）** | 22，**逐 BlockPos 完全一致** | 倒序 23；预测器固定顺序 23 |
| **B 系列**（R7S2C1/C2/C3） | 3 | **23（3/3 全部 23）** | 23，**逐 BlockPos 完全一致** | 倒序 22 |

**关键：两个方向的重复性都成立**（A：5/5 得 22；B：3/3 得 23），且每次都满足
「观测顺序重放 = 该次真实真值」（8/8）。

### 7.3 争议格的完整写入链（谁最后写 / 谁先写）

争议格 `(-6385,-59,6085)`，三方写入链（`#序号` = `AtomicLong` 全局写序号）：

**A 系列世界（真值 = gravel）**
```
重放A（观测顺序）：#60379  viewer(-400,380)  ore_gravel/OreFeature     deepslate → gravel
重放B（倒序）    ：#135143 viewer(-399,380)  ore_diamond/OreFeature    deepslate → deepslate_diamond_ore
真实世界        ：#18293  viewer(-400,380)  ore_gravel/OreFeature     deepslate → gravel
```

**B 系列世界（真值 = deepslate_diamond_ore）**
```
重放A（观测顺序）：#63382  viewer(-399,380) ore_diamond/OreFeature     deepslate → deepslate_diamond_ore
重放B（倒序）    ：#126380 viewer(-400,380) ore_gravel/OreFeature      deepslate → gravel
真实世界        ：#21473  viewer(-399,380) ore_diamond/OreFeature      deepslate → deepslate_diamond_ore
```

读法：

1. **真实世界里最后写这一格的是谁，最终就是谁**——两边的真实写入链都与自己的真值一致；
2. 该格上真正的竞争者是 **viewer(-400,380) 的 `ore_gravel`** 与 **viewer(-399,380) 的 `ore_diamond`**，
   两者都要求目标方块属于 stone / deepslate 可替换标签，而**砾石与钻石矿都不在对方的目标标签里** ⇒
   **先写者胜**（后到的候选点 `canPlaceOre` 直接返回 false，`setBlockState` 根本不会发生）；
3. 于是「这两个 viewer 谁先被装饰」这一个纯调度量，**直接决定该格最终是 gravel 还是 diamond**。

### 7.4 受控反事实（用户口径第二十节）

同一份离线世界、同一种子，只交换执行先后：

| 对比 | 结果 |
| --- | --- |
| 重放 A（观测顺序）vs 重放 B（观测顺序整体倒序） | A 22 / B 23 ⇒ **仅改变合法 FEATURES 执行先后就改变了最终钻石集合（因果证据）** |
| 重放 A（观测顺序）vs 重放 C（正式 Predictor 的固定顺序「由远到近、目标最后」） | 同为 B 系列时二者都是 23；A 系列时 C = 23 |

并且 **重放 C（Predictor 固定顺序）在 A 系列上复现的正是第六轮 Predictor 在 Seed 2 上的那个 FP**
（`(-6385,-59,6085)`，离线=deepslate_diamond_ore / 真值=gravel）。
⇒ 第六轮那个 FP **不是 Predictor 的 Bug**，而是「Predictor 固定了一种合法顺序，而该顺序恰好给出另一种合法结果」。

---

## 八、Seed 12345 `(0,0)`：重复结果与 RNG 级取证（报告项 7、21、22、23）

### 8.1 重复真实世界

| 运行 | 真实真值 | 观测顺序重放 | 重放 vs 真值 | 预测器固定顺序重放 |
| --- | --- | --- | --- | --- |
| R712345-1 | **38** | 38 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| R712345-2 | **36** | 36 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| R712345-3 | **36** | 36 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| R712345-4 | **38** | 38 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| R712345ORE1 | 36 | 36 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| R712345ORE2 | 36 | 36 | **逐 BlockPos 完全一致** | 31（h=3af6d） |
| round6 regression（探针关×2 / 开×2，共 4 次） | 36（4/4） | — | — | 31 |

两条结论都很硬：

1. **Predictor 恒定 31，与第六轮完全同值（摘要 h=3af6d n=31，逐字节对上）** ⇒ 预测器本身是确定性的，且没有因为我本轮的代码改动而漂移；
2. **真实真值在 6 次里出现 38 / 36 两种值**（38 出现 2 次、36 出现 4 次）⇒
   该区块的「真实最终钻石」本身不可重复；
3. **而每一次「按该世界自己观测到的装饰先后重放」，6/6 逐 BlockPos 复现了那一次的真实真值**
   ⇒ 离线世界模型是正确的；差异的来源是**真实世界的调度历史**，不是种子算法。

### 8.2 5 个固定漏报格的因果链（用户口径第二十一、二十三节）

第六轮的 5 个固定漏报格：`(6,-9,2) (7,-9,2) (6,-9,3) (7,-9,3) (7,-8,3)`。
本轮实测（R712345ORE1 / ORE2 两次完全同值）：

**① 候选点级逐项比对**

```
REAL vs OFFLINE（观测顺序）        ：可配对    76 条 / 左 27256 / 右 27256；差异 0 条；随机序号偏移 0 条
重放A(观测顺序) vs 重放C(预测器序)：可配对 27256 条 / 左 27256 / 右 27258；差异 554 条；随机序号偏移 10 条
```

第一处分叉候选（起点）：

```
候选 #1：viewer(1,0) ore_diorite_lower 矿脉#1 候选#131
  重放A(观测顺序)      ：pos=(21,13,1) before=stone    replaceable=true  randomOrdinal=0 accepted=true
  重放C(预测器固定顺序) ：pos=(21,13,1) before=granite  replaceable=true  randomOrdinal=0 accepted=true
候选 #2：viewer(1,0) ore_diorite_lower 矿脉#1 候选#132
  重放A：pos=(21,13,2) before=stone   / 重放C：pos=(21,13,2) before=granite
（其余同型分叉见 `seedpoc-第七轮报告-case-R712345ORE1-12345.txt`）
```

⇒ 分叉点是**「同一候选点在同一时刻看到的方块不同」**（stone vs granite / coal_ore / copper_ore / iron_ore），
**不是**随机消耗次数的整体错位（`随机序号已偏移` 仅 10/27256 条）。

**② 5 个漏报格的真实最后写者（逐格台账，来自 `F` 行）**

```
(6,-9,2)  最终=deepslate_diamond_ore ← #49035 viewer(0,0) ore_diamond_medium  deepslate → deepslate_diamond_ore
(7,-9,2)  最终=deepslate_diamond_ore ← #49037 viewer(0,0) ore_diamond_medium  tuff → deepslate_diamond_ore
      （同格更早：#48818 viewer(0,0) ore_tuff  deepslate → tuff）
(6,-9,3)  最终=deepslate_diamond_ore ← #49036 viewer(0,0) ore_diamond_medium  deepslate → deepslate_diamond_ore
(7,-9,3)  最终=deepslate_diamond_ore ← #49033 viewer(0,0) ore_diamond_medium  tuff → deepslate_diamond_ore
      （同格更早：#48819 viewer(0,0) ore_tuff  deepslate → tuff）
(7,-8,3)  最终=deepslate_diamond_ore ← #49034 viewer(0,0) ore_diamond_medium  tuff → deepslate_diamond_ore
      （同格更早：#48773 viewer(0,0) ore_tuff  deepslate → tuff）
```

**③ 因果链定案（推翻第六轮的「RNG 漂移」表述）**

5 格的最后写者都是**目标区块自己**的 `ore_diamond_medium`（viewer(0,0)，批号 8）。
Predictor 的固定顺序把目标区块排到最后（批号 9/9），此时这些候选点已经被**别的 viewer 更早写入的
不可替换方块**（coal_ore / copper_ore / iron_ore / granite 等）占据，`canPlaceOre` 直接拒绝，`setBlockState` 不会发生。
真实世界里目标区块排在批号 8（其邻区块 (0,1) 在它之后），候选点那一刻还是 deepslate/tuff ⇒ 通过。

**⇒ 正确表述是「候选点所见世界状态随调度改变」，不是「随机流整体漂移」**
（实测 `随机序号已偏移` 只有 10/27256 条，不足以解释 5 格差异）。

---

## 九、Seed 12345 `(-1,-1)`：真值不可重复 + 观测顺序重放失效（报告项 8、30、31）

| 运行 | 真实真值 | 观测顺序重放 | 预测器固定顺序重放 | 倒序重放 |
| --- | --- | --- | --- | --- |
| R712345-1 | 29 | 21（漏 8 / 错 0） | 25 | 29 |
| R712345-2 | 27 | 27（漏 8 / 错 8，计数相同但逐格不同） | 25 | 29 |
| R712345-3 | 24 | 25（漏 10 / 错 11） | 25 | 26 |
| R712345-4 | 27 | 25（漏 13 / 错 11） | 25 | 29 |

结论：

1. **真值 29 / 27 / 24 / 27 —— 真实世界自身不可重复**（与第六轮 23/27/23/23 同类）；
2. 该目标的**观测顺序重放没能复现真值**（4 次里只有 1 次计数相同、逐格仍差 8/8）。
   原因已定位且可解释：该区块位于**出生点附近**，其实测装饰批号一路排到 `(0,1)=33 … (-2,0)=68`
   （14 个区块跨两波被装饰），**同一波里的多个区块是并发跑的**，批号只记录「谁先进入 decoration」，
   **记录不了并发中的写入交错**。因此「按批号排序重放」只是对真实并发的近似。
   ——这比「顺序不同」更强一层：**真实世界在一次生成里连「谁先写」本身都不受控**（用户口径第九节：
   出生点附近的首次生成顺序在不改 Vanilla 语义的前提下无法控制，本轮如实登记为「不可控」）。
3. 该目标不计入一致率验收（第六轮已按此口径处理）。

---

## 十、稳定对照（报告项 10）

| 目标 | 世界数 | 真值 | 观测顺序重放 | 预测器固定顺序重放 | 倒序重放 | 结论 |
| --- | --- | --- | --- | --- | --- | --- |
| Seed 12345 `(-25,17)` | 4 | 24（4/4） | 24，完全一致 | 24，完全一致 | 24，完全一致 | **四种顺序全部 24 ⇒ 顺序无关** |
| Seed 12345 `(120,-130)` | 4 | 26（4/4） | 26，完全一致 | 26，完全一致 | 26，完全一致 | **四种顺序全部 26 ⇒ 顺序无关** |

⇒ 探针并没有把「所有 Chunk 都变成不稳定」：
**远离出生点且邻域内没有跨区块竞争写入的目标，Seed 依然是最终集合的唯一决定因素**（这就是产品的确定性部分）。

---

## 十一、同格多 writer 冲突清单与「最后写决定最终方块」的直接证据（报告项 13、14）

自动输出（用户口径第十一节要求的格式，不许只写「可能存在冲突」）：

**Seed 2 A 系列**（目标区块内）
```
同格冲突位置合计：416 个（两次以上「真正改变了值」的写入碰过同一格）
其中「最终状态 = 最后一次写的后置状态」416 / 416

冲突位置 (-6400,7,6082)：
  #10215 viewer(-401,380) ore_andesite_lower/OreFeature 批3 stone → andesite
  #18593 viewer(-400,380) ore_gravel/OreFeature         批5 andesite → gravel
  FINAL：gravel（= 最后一次写 #18593 的后置状态，最后写者决定最终方块）

冲突位置 (-6398,-55,6083)：
  #11485 viewer(-401,380) ore_tuff/OreFeature           批3 deepslate → tuff
  #23112 viewer(-400,380) ore_diamond_buried/OreFeature 批5 tuff → deepslate_diamond_ore
  FINAL：deepslate_diamond_ore（= 最后一次写 #23112 的后置状态）
```

**Seed 12345 `(0,0)`**（目标区块内）
```
同格冲突位置合计：482 个；其中「最终状态 = 最后一次写的后置状态」482 / 482
样例：
  #9355  viewer(-1,0) ore_granite_lower/OreFeature 批2 stone → granite
  #29777 viewer(0,0)  ore_andesite_lower/OreFeature 批5 granite → andesite   FINAL：andesite
  #20737 viewer(0,-1) ore_diorite_lower/OreFeature 批4 stone → diorite
  #31196 viewer(0,0)  ore_iron_middle/OreFeature   批5 diorite → iron_ore   FINAL：iron_ore
  #11400 viewer(-1,0) ore_andesite_lower/OreFeature 批2 stone → andesite
  #26631 viewer(0,0)  未在 placed_feature 上下文中  批5 andesite → cave_air   FINAL：cave_air（矿井支架结构）
```

汇总：

| 目标 | 冲突位置数 | 「最终状态 = 最后一次写的后置状态」 |
| --- | --- | --- |
| Seed 2 `(-400,380)`（A 系列） | 416 | **416 / 416** |
| Seed 12345 `(0,0)` | 482 | **482 / 482** |

⇒ **「谁最后写，谁决定最终方块」从「推测」变成「逐格可核对的直接证据」**（命中率 100%）；
同时也要如实登记两类不同的冲突族：

- **覆盖型**（后写者真的改掉了前写者）：如 `ore_tuff → ore_gravel`、`ore_granite → ore_andesite`，
  两族方块都属可替换标签；
- **先写者胜型**（后写者被拒）：如 `ore_gravel ↔ ore_diamond`，双方都不在对方的目标标签里
  （Seed 2 的争议格就是这一族，所以它的最终值由**谁先执行**决定，而不是「谁最后写」）。

### 11.1 台账覆盖边界（如实登记，不用它冒充全量）

- 覆盖：`WorldGenRegion#setBlock`（绝大多数地物、结构）**+** `OreFeature#doPlace` 的
  `LevelChunkSection#setBlockState` 直写（修复后补齐）。
- 未覆盖：`WorldGenRegion#removeBlock`（写空气）与其它可能的直接 section 写入路径。
  ⇒ 报告里**不把「台账里没有两条写入」当作「没有竞争」的证据**，只把「台账里有两条写入」当作竞争的正面证据。

---

## 十二、Predictor 实现审计（报告项 24、25）

按用户口径第二十二节逐条审核（全部为静态阅读 + 实机行为对照，未预设结论）：

| # | 审计项 | 结论 |
| --- | --- | --- |
| 1 | 同一 viewer 是否会被错误重复 decorate | 不会。`OfflineChunkPipeline#decorate` 幂等（该状态已有产出即跳过）；实测同一会话里 `FEATURES 执行 43 次 / 10 目标`（10 目标 × 9 viewer = 90 次请求，其中大量被前置状态复用吸收） |
| 2 | 应该共享的状态是否被 copy | 共享。会话只建 1 套 BiomeSource / NoiseBasedChunkGenerator / RandomState / ChunkGeneratorStructureState + 1 份 ProtoChunk 表，viewer 之间真实叠加 |
| 3 | Heightmap 是否随跨 viewer setBlock 正确更新 | 是。第六轮已修掉 `LevelChunkSection#hasOnlyAir()` 短路；本轮 Seed 2 的争议格（跨 viewer 写入 y=-59）被正确读出就是旁证 |
| 4 | 不同 viewer 的 Random 是否各自正确初始化 | 是。`featureSeed` 由 `(decorationSeed, 全局索引, 步骤序号)` 派生，与 viewer 位置绑定；实测同一 viewer 在 A / B 两种全局顺序下的候选序列一致（27256 条可配对） |
| 5 | FeatureSorter 是否按该 viewer 自己的 biome set 运行 | 是。离线走原版 `applyBiomeDecoration`，未替换任何 feature 排序逻辑 |
| 6 | 真实 Vanilla 允许并行、离线强制串行 | **成立，且这就是剩余差异的唯一来源**。本轮实测：换顺序即可复现 / 复现不出真值（第八、九节）。**按用户口径第二十二节第 6 条，本轮没有把它「修成猜一个更好的固定顺序」** |
| 7 | 是否存在与调度无关的明确实现 Bug | **没有发现**。243/243、旧 4 目标里 2 个逐 BlockPos 一致、泛化 28/30 全部保持；本轮唯一的缺陷是**取证装置**漏了 `OreFeature` 直写路径（第四节 4.1，已当场修复） |

**由于没有发现 Predictor 实现 Bug，本轮按用户口径第二十三节不做 Predictor 回归重跑**；
但仍重跑了「固定集 4 次」（第六节）、「旧回归 4 目标 4 次」（5.2）与「多 Seed 泛化抽查 1 次」（12.1）
作为「代码改动后未破坏既有结论」的证据。

### 12.1 多 Seed 泛化抽查（Seed 2，报告项 29）

为确认本轮 Mixin 改动（写入台账 / 候选点诊断的两处新增注入）没有改变 Predictor 的行为，
重跑第六轮泛化阶段的 Seed 2（6 个目标，其中包含本轮的焦点目标 `(-400,380)`）：

```
本种子合计：真值 132 / 预测 133 / 匹配 132 / 漏报 0 / 错报 1 / 查全 100.00% / 查准 99.25%
逐 BlockPos 完全一致 5/6
目标 (-400,380)：真值 22 / 预测 23 / 匹配 22 / 错报 1 = (-6385,-59,6085)（离线=deepslate_diamond_ore / 真值=gravel）
真值可重复性：目标 (-400,380) 预测与上一次相同 h=80125ef n=23；真值与上一次相同 h=7a80b80 n=22
```

与第六轮**逐字相同**（真值 132 / 预测 133 / 漏报 0 / 错报 1、同一个错报坐标）。
更有说服力的是两个摘要哈希的对上（两轮独立运行）：

| 来源 | 摘要 |
| --- | --- |
| 第六轮 Predictor（本轮抽查复现） | `h=80125ef n=23` |
| 本轮 `重放 C`（Debug 重放·Predictor 固定顺序） | `h=80125ef n=23` |
| 第六轮真实真值（本轮抽查复现） | `h=7a80b80 n=22` |
| 本轮 `重放 A`（Debug 重放·观测顺序） | `h=7a80b80 n=22` |

⇒ 第六轮那个「FP」在本轮被**三条独立路径**指向同一件事：
它是 Predictor 固定顺序下的**合法**结果，而真实世界那一波用的是另一种合法顺序。

---

## 十三、是否还存在「真实 Truth 自身不可重复」（报告项 30）

**存在，而且本轮把它量化了：**

| 目标 | 重复次数 | 真实真值分布 | 判定 |
| --- | --- | --- | --- |
| Seed 12345 `(0,0)` | 6 次 round7 进程 + 4 次 round6 进程 | 38 / 36 / 36 / 38 / 36 / 36（round7）与 36×4（round6） | **不可重复**（且对驱动器/探针敏感） |
| Seed 12345 `(-1,-1)` | 4 次 | 29 / 27 / 24 / 27 | **不可重复** |
| Seed 12345 `(-25,17)` | 4 次 | 24 ×4 | 可重复 |
| Seed 12345 `(120,-130)` | 4 次 | 26 ×4 | 可重复 |
| Seed 20260922 固定 10 目标 | 4 次 | 逐目标摘要 4/4 相同 | 可重复 |
| Seed 2 `(-400,380)` | 8 次（A5 + B3） | A：22×5；B：23×3 | **同一顺序内可重复，不同顺序间不同** |

必须同时登记的一条：`(0,0)` 在 round6 驱动器下 4/4 = 36，在 round7 驱动器下出现 38 ——
说明该目标的真值不只依赖 Seed 与请求顺序，还依赖**窗口外并发执行的时序**（出生点区块同一波并发装饰）。
这正是结论 B 的另一种表现形式。

---

## 十四、最终结论（报告项 31、32）

------------------------------
### FINAL B
------------------------------

> **已取得可复现因果证据：相同 Seed / 版本 / worldgen 配置下，
> 合法 FEATURES 调度 / 跨区块写入先后不同，会导致最终自然钻石 BlockPos 不同。**
>
> 因此任意真实服务器「最终矿物状态」**不是 Seed 的唯一函数**。
>
> `Pure Seed 唯一最终状态` 研究到此结案，
> 后续产品应采用「**确定性目标 + 调度歧义目标 + 实际 Chunk 确认**」的语义。

证据汇总（每条都可回查落盘文件）：

| # | 证据 | 出处 |
| --- | --- | --- |
| 1 | 同一格 `(-6385,-59,6085)` 在真实世界随合法请求顺序在 gravel ↔ deepslate_diamond_ore 之间翻转 | R7S2A1/B1…/C1… 报告 + journal |
| 2 | 目标区块真实钻石 22（A：5/5）与 23（B：3/3），两个方向都重复性成立 | 同上 |
| 3 | 每次「观测顺序重放」都逐 BlockPos 复现该次真实真值（Seed2 8/8、`(0,0)` 6/6） | 各处 `对照①` |
| 4 | 重放 C（Predictor 固定顺序）逐字节复现第六轮 Predictor 的输出（`(0,0)` h=3af6d n=31；Seed2 的 FP） | 各处 `对照③` |
| 5 | 同格冲突 416/416 与 482/482 满足「最终状态 = 最后一次写的后置状态」 | 第十一节 |
| 6 | `(0,0)` 真实真值 38/36/36/38 不可重复；`(-1,-1)` 29/27/24/27 不可重复 | 第八、九节 |
| 7 | 候选点级：差异源于「候选点所见方块不同」，而非随机消耗整体漂移（仅 10/27256 条序号偏移） | 8.2 |

**产品语义边界（本轮只写方案，不实现；用户口径第二十六、二十七节）**

| 分类 | 含义 | 例（本轮实测） |
| --- | --- | --- |
| `DETERMINISTIC` | Seed 稳定决定最终钻石集合，与调度无关 | Seed 12345 `(-25,17)` = 24、`(120,-130)` = 26；Seed 20260922 固定 10 目标 243 格 |
| `SCHEDULE_SENSITIVE` | 存在合法 FEATURES 顺序冲突，Seed 本身不足以决定最终状态 | Seed 2 `(-400,380)`（22 或 23）；Seed 12345 `(0,0)`（36 或 38）；`(-1,-1)`（24/27/29） |
| `OBSERVED_CONFIRMED` | 区块已加载后，实际 BlockState 与预测相符 | 各次「观测顺序重放 = 真值」的逐 BlockPos 一致 |
| `OBSERVED_MISSING` | Seed 候选存在，但服务器当前实际不存在 | Predictor 固定顺序下 Seed 2 的 `(-6385,-59,6085)` 在 A 类世界里不存在 |
| `SUSPICIOUS` | 服务器显示矿，但不属于任何已知合法 Seed 生成候选 | 本轮未出现（所有观测到的钻石都能被某个合法顺序解释） |

**特别说明（用户口径第二十六节末段）：`SCHEDULE_SENSITIVE` 绝不能直接标成「假矿」**——
本轮已经证明这块「多出来的钻石」在真实世界上是可复现的合法产物（B 系列 3/3），
把它判成反透视假矿就是把原版自己的调度歧义误报成作弊证据。

**本轮不实现**：Stable/Ambiguous 正式算法、9! 全排列稳定集合、概率评分、UI、假矿过滤器
（用户口径第二十七节：先把原理边界定案，产品语义由用户决定后再设计）。

---

## 十五、正式业务代码是否仍零改动（报告项 34）

**零改动。** 本轮全部代码位于：

- `com/yiyiaddon/dev/seedpoc/`（PoC）：新增 `FeatureWriteJournal`、`OreCandidateJournal`、
  `ObservedOrderReplay`、`Round7Runner`；修改 `SeedPocFlags`（新增第七轮开关）、
  `SeedPocEntry`（多一行受系统属性守卫的分发）、`ChunkOrderScenario`（新增显式偏移入口）。
- `com/yiyiaddon/mixin/client/`（开发期探针）：新增 `FeatureWriteJournalMixin`；
  在 `PlacedFeatureStageCaptureMixin`、`OreFeatureTraceMixin`、`ChunkGeneratorStageCaptureMixin`
  的**已有注入点**上各加了一行取证调用。
- `src/main/resources/yiyiaddon.mixins.json`：新增一行注册。

`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController` / Baritone 正式逻辑 /
精准采集 / 时运 / 自动食物 / 自动回家 / 背包 / 白名单 / 正式 Renderer / 正式 Config / 正式 UI **一行未改**；
本轮**没有**新增任何 UI、指令、SeedValidation、正式 TargetProvider、假矿检测，也没有接 AutoMiner，
没有扩其它矿 / 下界 / 26.2 / 26.3。

`git diff --stat` 实测（已有文件的改动，全部是新增行）：

```
build.gradle                                     | 34 +++++++++++   （第五/六轮已加的 PoC run 配置）
src/main/java/com/yiyiaddon/YiyiAddonClient.java |  5 ++++         （一行受系统属性守卫的挂载调用）
src/main/resources/yiyiaddon.mixins.json         |  6 +++          （本轮 +1 行：注册 FeatureWriteJournalMixin）
```

（同一次 `git status` 里另有 `06-发布页面/`、`07-bug记录/` 若干文件的改动，与本轮无关，是此前遗留的未提交改动。）

（探针按《开发习惯》第三十三章 DebugProbe 口径处置：默认关闭、只在 `-Dyiyiaddon.seedpoc.enabled=1` 时挂载、
结案时整包删除；本轮仍处于研究期，暂不删除。）

---

## 十六、编译 / 运行（报告项 35～38）

```
> .\gradlew.bat compileJava --console=plain -q      BUILD SUCCESSFUL（EXIT=0）
> .\gradlew.bat build       --console=plain -q      BUILD SUCCESSFUL（EXIT=0，jar / assemble / check 全通过）
runClient PoC：本轮共 24 次真实客户端实跑，全部 EXIT=0
  固定集 4（探针关×2 + 探针开×2）
  12345 回归 4（探针关×2 + 探针开×2）
  多 Seed 泛化抽查 1（Seed 2，6 目标）
  Seed 2 case 8（A 请求序 5 + B 请求序 3）
  12345 case 4（四目标一次一进程，标签 R712345-1..4）
  12345 (0,0) 候选点级 case 2（标签 R712345ORE1/2）
  Seed 2 首次冒烟 1（版本 1 探针，用于暴露第 4.1 节的取证缺口）
运行任务：gradlew runClientSeedPredictTest（独立运行目录 run-26.1.2-seed-predict-test）
```

**复现命令（原样可跑）**

```powershell
# ① 基线（探针关）：Seed 20260922 固定 10 目标
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# ② 基线 + 第七轮探针（写入台账与候选点诊断全开）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed -Dyiyiaddon.seedpoc.round7.journal=1 -Dyiyiaddon.seedpoc.round7.ore=1 -Dyiyiaddon.seedpoc.order.targets=0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3 -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# ③ Seed 2 受控顺序 A（直接请求目标）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=2 -Dyiyiaddon.seedpoc.round7=1 -Dyiyiaddon.seedpoc.round7.stage=case -Dyiyiaddon.seedpoc.round7.label=R7S2A1 -Dyiyiaddon.seedpoc.round7.journal=1 -Dyiyiaddon.seedpoc.round7.ore=1 -Dyiyiaddon.seedpoc.order=A1 -Dyiyiaddon.seedpoc.order.targets=-400,380 -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# ④ Seed 2 受控顺序 B（邻域优先，目标最后）—— 只需换 order=B1
#    或任意自定义合法顺序：-Dyiyiaddon.seedpoc.order.explicit=-2,2;-2,-2;2,2;0,0

# ⑤ Seed 12345 四目标 case（写入台账）
$env:JAVA_TOOL_OPTIONS='-Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.seed=12345 -Dyiyiaddon.seedpoc.round7=1 -Dyiyiaddon.seedpoc.round7.stage=case -Dyiyiaddon.seedpoc.round7.label=R712345-1 -Dyiyiaddon.seedpoc.round7.journal=1 -Dyiyiaddon.seedpoc.order=A1 -Dyiyiaddon.seedpoc.order.targets=0,0;-1,-1;-25,17;120,-130 -Dyiyiaddon.seedpoc.exit=1'
.\gradlew.bat runClientSeedPredictTest

# ⑥ (0,0) 候选点级（RNG 取证）
#    同上，把 round7.ore=1、order.targets=0,0、label=R712345ORE1

# ⑦ 跨运行相关性（把同一标签前缀的多次运行两两比）
#    -Dyiyiaddon.seedpoc.round7.stage=correlate -Dyiyiaddon.seedpoc.round7.prefix=R7S2C
```

**落盘证据路径**（全部在 `run-26.1.2-seed-predict-test/`）：

```
seedpoc-第七轮报告-case-<标签>-<种子>.txt          单次取证报告（含冲突清单 / 四方对照 / 候选点分叉 / 差异格写入链）
seedpoc-第七轮报告-correlate-<标签>-<种子>.txt     跨运行相关性报告
seedpoc-第七轮journal-<标签>_<x>_<z>.txt           逐格写入台账（W/O/F 行 + 全局写序号）
seedpoc-顺序真值-<标签>_<x>_<z>.txt                单次真值（可被 correlate 与重放复用）
seedpoc-第六轮报告-fixed-20260922.txt              基线（+ archive-fixed-{OFF,ON}{1,2}.txt 四次留档）
seedpoc-第六轮报告-regression-12345.txt            12345 回归（+ archive-reg-{OFF,ON}{1,2}.txt）
seedpoc-第六轮报告-generalize-2.txt                多 Seed 泛化抽查 Seed 2（+ archive-generalize-2-round7.txt）
seedpoc-truth-digest-fixed-20260922.txt            真值可重复性台账
seedpoc-truth-digest-regression-12345.txt          真值可重复性台账
```

---

## 十七、本轮明确没有做的事（用户口径第三十二、三十六、三十七节）

- 没有大规模撒网（没有扩 Seed 数量；算力全部花在失败 Case 的深度取证上）；
- 没有为了让数字"更好看"调整任何半径、顺序、采样口径；
- 没有实现 Stable / Ambiguous 正式算法、UI、假矿检测、AutoMiner 接入；
- 没有扩其它矿物 / 下界 / 26.2 / 26.3；
- **不再自行提出第八轮 / 第九轮**。

**下一步由用户决定**：若接受结论 B，则进入产品语义设计（`DETERMINISTIC` /
`SCHEDULE_SENSITIVE` / `OBSERVED_*` 五分类的正式实现方案）；本轮到此停止。
