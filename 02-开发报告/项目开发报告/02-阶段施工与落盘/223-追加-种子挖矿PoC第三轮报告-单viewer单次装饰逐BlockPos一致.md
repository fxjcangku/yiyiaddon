# 223 · 追加 · 种子挖矿 PoC 第三轮报告（26.1.2 · 单 viewer / 单次真实装饰 · 逐 BlockPos 对照）

日期：2026-09-23
阶段：种子挖矿 —— **第三轮「单次装饰 oracle PoC」**。
本阶段只回答一个问题：

> 同一个 viewer、同一次真实装饰、同一个 pre-diamond 输入状态下，
> 当前 Seed / decorationSeed / featureSeed / FeatureSorter / PlacedFeature / OreFeature 重放，
> 能不能**逐 BlockPos 100% 复现**原版这一遍真实写入的钻石？

**验证方式：实机验证**（`gradlew runClient`，固定种子 `20260922`、沿用前两轮相同的 10 个固定区块
`0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`，
本报告全部数字来自最后一轮实机运行，落盘文件 `run-26.1.2/seedpoc-实验报告.txt`）。

---

## 一、结论（一屏看完）

| 问题 | 答案 | 证据 |
| --- | --- | --- |
| post-diamond oracle 是否建成 | **建成** | `post-diamond` = 同一区块同一次 `applyBiomeDecoration` 内**最后一条钻石 placed_feature 的 RETURN**；pre/post 同批号校验 10/10 通过（第三、四节） |
| oracle 是否与最终世界倒推无关 | **无关** | `vanillaContribution = post-diamond 钻石 − pre-diamond 钻石`，两次取样都带同一个「装饰批号」，批号不等即判不可配对并拒绝做差集；10/10 viewer 批号相同（第四节） |
| **单 viewer / 单次装饰能否逐 BlockPos 100% 一致** | **能** | 第一阶段 **10/10 viewer 完全一致**：中心 ±1 与落点区块两个口径都是 真值 240 / 预测 240 / 匹配 240 / 漏报 0 / 错报 0 / 查全 100% / 查准 100%（第五节） |
| 第二阶段（3x3 九个 viewer 的跨区块贡献） | **逐 viewer 全部完全一致** | 10 个目标区块 × 9 个 viewer，**每个 viewer 单独比对**，全部 漏报 0 / 错报 0；10 个目标区块合计也都是真值 = 预测（第七节） |
| 本轮定位到的第一处分叉 | **不在种子链，在影子层的 `ensureCanWrite`** | 修掉「无条件越界高度判定」后 0/10 → 10/10（第六节。修复依据：`WorldGenRegion.java:228-258`） |
| 装置自证是否仍然有效 | **有效** | 对照1（不可替换）预测 **0**；对照2（可替换）预测 **293 > 243**（第八节） |
| 是否具备进入下一阶段条件 | **具备（带一条明确边界）** | 单次装饰链已被证明可逐 BlockPos 复现；但本轮证明的是「**给定正确 pre-diamond 输入**下算法成立」，**不等于**客户端已能离线构造该输入（第十节第 3 条） |

一句话：**「同一个 viewer、同一次真实装饰、同一个 pre-diamond 输入状态」这一条链，现在可以逐 BlockPos 100% 复现。**
本轮没有改动任何业务代码，也没有改动种子算法本身——**算法本来就是对的，错的是我们影子层里多加的一条判据。**

---

## 二、边界遵守情况（逐条对照用户拍板）

**没有做（本轮明令禁止的事项）**：

- 未修改任何现有自动挖矿业务逻辑（`MiningStateMachine` / `pathToOre` / `MiningVeinMiner` / 精准采集 / 时运 / 秒破 / 食物 / 回家 / 背包白名单，一行未改）。
- 未开发正式「种子挖矿」页面；未做 Seed 真实性验证；未接入正式目标来源；未渲染、未注册 UI、未注册指令。
- 未开发第二套挖矿状态机；未用实测扫描冒充 Seed 预测（真值只用于事后比对）。
- 未开始其它矿物 / 其它维度；未开始 26.2 Seed 适配；**未创建、未修改、未适配任何 26.3 内容**。
- 未切换分支，未做任何版本迁移。
- 未启动「完整离线 WorldGenRegion / ChunkStep / GenerationChunkHolder」工程（本轮只回答单次装饰链）。

**做了（本轮允许且必要的事项）**：

- 新增 post-diamond 捕获与单次装饰 oracle（pre/post 差集），并强制同 viewer + 同装饰批号配对。
- 新增**单 viewer 单次装饰重放**：只重放这个 viewer 自己的钻石四条，不做 3x3 九遍聚合。
- 新增**放置取证台账 + first-divergence 分析器**，把差异钉到具体第一处。
- 第一阶段 100% 达标后，按用户口径自动执行第二阶段（3x3 九个 viewer 的跨区块贡献，逐 viewer 单独输出）。

**Git 状态**：正式业务文件本阶段未改；实验代码全部在
`src/main/java/com/yiyiaddon/dev/seedpoc/` 与 `src/main/java/com/yiyiaddon/mixin/client/` 下的两个新探针 Mixin；
实验世界、报告、运行日志都在 `.gitignore` 覆盖的 `run-26.1.2/` 下。

---

## 三、本轮新增 / 修改的实验 Java 文件

### 3.1 新增（第三轮专用）

| 文件 | 职责 |
| --- | --- |
| `dev/seedpoc/DiamondFeatureOracle.java` | post-diamond oracle 本体：在 `placeWithBiomeCheck` HEAD 取「执行前」钻石集合、RETURN 取「执行后」，差集 = 该条钻石 feature 本遍新增；最后一条的 after 即 post-diamond |
| `dev/seedpoc/DiamondScanner.java` | 统一钻石坐标扫描器：原版侧读 `WorldGenLevel`、快照侧读快照，**两侧覆盖范围与纵向上限完全一致**（中心 ±1、6 个 section = y ≤ 31），差集才可信 |
| `dev/seedpoc/OreVeinTrace.java` | 放置取证台账（**本轮重写为单条事件时间线 + 窗口栈**，见第六节） |
| `dev/seedpoc/FirstDivergenceAnalyzer.java` | first-divergence 分析器（**本轮重写为逐事件对齐**，见第六节） |
| `dev/seedpoc/SingleViewerVerifier.java` | 第三轮主流程：配对 → 算 oracle → 重放 → 逐 BlockPos 比对 |
| `dev/seedpoc/ViewerOutcome.java` | 第三轮验收数据载体（viewer/target/批号/配对/计数/两侧集合/两个 diff/还原校验/分叉层/note/details） |
| `mixin/client/OreFeatureTraceMixin.java` | 探针：`OreFeature#place` HEAD（placement origin）、`doPlace` HEAD（矿脉几何）、`canPlaceOre` RETURN（候选点接受判定） |
| `mixin/client/BiomeFilterTraceMixin.java` | 探针：`BiomeFilter#shouldPlace` RETURN（判定 + 位置 + 该点生物群系） |

### 3.2 修改

| 文件 | 改动 |
| --- | --- |
| `dev/seedpoc/ShadowLevelFactory.java` | **本轮关键修复**：`ensureCanWrite` 去掉「无条件越界高度判定」，改为逐行复刻原版（详见第六节） |
| `dev/seedpoc/GenStageCapture.java` | 新增装饰批号（每次进入 `applyBiomeDecoration` 发一个新号），用来强制「同 viewer 同一次装饰」配对 |
| `dev/seedpoc/OreDecorationReplay.java` | 新增 `replayDiamonds(...)`：只重放钻石四条并逐条记录前后钻石集合 |
| `dev/seedpoc/SeedPocRunner.java` | 第三轮编排：单 viewer 循环 → 判定 → **仅在第一阶段 100% 时**执行第二阶段；新增记账窗口与注入点计数上报 |
| `dev/seedpoc/SeedPocReport.java` | 第三轮报告（oracle 节 / 单 viewer 节 / 汇总节 / 结果分叉节 / 装置自证节） |
| `dev/seedpoc/SeedPocConstants.java` | 第三轮口径标签、钻石扫描 section 数（6） |
| `dev/seedpoc/SeedPocFlags.java` | 第二轮五种大口径默认关闭（回归用），`single` / `control` / `phase2` 默认开启 |
| `mixin/client/PlacedFeatureStageCaptureMixin.java` | HEAD 追加 oracle 开账与台账开窗；新增 RETURN 注入：先结账台账，再取 post-diamond |
| `resources/yiyiaddon.mixins.json` | 注册 `BiomeFilterTraceMixin`、`OreFeatureTraceMixin` |

### 3.3 实验代码的处置口径

所有探针只在 `-Dyiyiaddon.seedpoc.enabled=1` 时挂载，正式环境默认不运行；
不读写正式模块配置、不注册 UI / 指令、不进入最终发布产物。按《开发习惯》第三十三章，
**PoC 结案后整包删除**（本轮尚未结案，故暂留）。

---

## 四、post-diamond 的真实注入位置，与「同 viewer / 同装饰」的保证方式

### 4.1 注入位置（全部落在原版真实生成过程中）

同一次 `applyBiomeDecoration`（`ChunkGenerator.java:318-398`）之内，三个时点：

| 时点 | 注入点 | 含义 |
| --- | --- | --- |
| pre-diamond | `PlacedFeature#placeWithBiomeCheck` **HEAD**（`PlacedFeature.java:38`），仅当该 feature 是钻石四条之一 | 该 viewer **第一条**钻石 placed_feature 执行前的真实生成期状态 |
| 每条钻石 feature 的 before/after | 同方法 HEAD / **RETURN** | 该条钻石 feature 本遍新增 = after − before |
| **post-diamond** | 同方法 **RETURN**，取**该 viewer 最后一条**钻石 placed_feature 执行结束时 | 四条钻石全部执行结束、后续非钻石 feature 尚未继续污染的位置 |

`placeWithBiomeCheck` 在本线源码里只有一个调用方，就是 `applyBiomeDecoration` 的 feature 放置循环
（`ChunkGenerator.java:384-397`），因此这三个时点都在「真实装饰」之内，不是事后推算。

### 4.2 pre/post 配对约束（禁止跨 viewer、禁止跨批次）

- `applyBiomeDecoration` 每进入一次就发一个**装饰批号**（`passSequence` 自增），按 viewer 记录。
- pre-diamond 快照记下它所属批号；post-diamond 记录时带上当前批号。
- 比对前**先比批号**：不等即判「**不可配对**」，绝不跨越做差集，报告里该行明确标注「不得作为结论」。
- 本轮实测：**10/10 viewer 的 pre 与 post 批号相同**（例：viewer (0,0) pre=1 / post=1）。

### 4.3 oracle 计算方式

```
vanillaContribution = { post-diamond 中的 diamond_ore / deepslate_diamond_ore }
                      − { pre-diamond 中已经存在的钻石 }
```

- 两侧扫描口径由同一个 `DiamondScanner` 执行（中心 ±1、y ≤ 31 的 6 个 section），
  **不使用最终世界代替 post-diamond，也不使用最终世界倒推**。
- 本轮实测：43 个区块取到 post-diamond（逐 feature 记录 172 条），oracle 捕获异常 0。

---

## 五、第一阶段：单 viewer / 单次装饰逐 viewer 数据（10 个固定区块）

口径说明：**「中心 ±1」= viewer 自己的 3x3**（依据原版 `FEATURES` 的 `blockStateWriteRadius = 1`，
`ChunkPyramid.java:29-35`）；**「落点区块」= viewer 自身区块**。

| # | viewer | 装饰批号 pre/post | pre 钻石 | post 钻石 | 原版本遍新增<br>（中心±1 / 落点） | 重放本遍新增<br>（中心±1 / 落点） | 匹配 | 漏报 | 错报 | 查全 | 查准 | 逐 BlockPos 完全一致 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | (0,0) | 1 / 1 | 0 | 47 | 47 / 45 | 47 / 45 | 47 / 45 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 2 | (1,0) | 2 / 2 | 45 | 55 | 10 / 9 | 10 / 9 | 10 / 9 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 3 | (0,1) | 4 / 4 | 77 | 109 | 32 / 29 | 32 / 29 | 32 / 29 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 4 | (1,1) | 3 / 3 | 54 | 75 | 21 / 21 | 21 / 21 | 21 / 21 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 5 | (-1,0) | 9 / 9 | 142 | 160 | 18 / 18 | 18 / 18 | 18 / 18 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 6 | (0,-1) | 5 / 5 | 57 | 81 | 24 / 24 | 24 / 24 | 24 / 24 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 7 | (-1,-1) | 6 / 6 | 71 | 92 | 21 / 21 | 21 / 21 | 21 / 21 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 8 | (2,2) | 25 / 25 | 31 | 49 | 18 / 18 | 18 / 18 | 18 / 18 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 9 | (3,-1) | 39 / 39 | 94 | 112 | 18 / 18 | 18 / 18 | 18 / 18 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |
| 10 | (-2,3) | 21 / 21 | 88 | 119 | 31 / 29 | 31 / 29 | 31 / 29 | 0 / 0 | 0 / 0 | 100% | 100% | **是** |

**合计（10 个固定区块）**

- 中心 ±1：真值 **240** / 预测 **240** / 匹配 **240** / 漏报 **0** / 错报 **0** / 查全 **100.00%** / 查准 **100.00%**；逐 BlockPos 完全一致的 viewer：**10 / 10**
- 落点区块：真值 **232** / 预测 **232** / 匹配 **232** / 漏报 **0** / 错报 **0** / 查全 **100.00%** / 查准 **100.00%**；逐 BlockPos 完全一致的 viewer：**10 / 10**
- 第一阶段是否达到逐 BlockPos 100%：**是（10/10）**

> **数字口径提醒（禁止误读）**：这里的 240 / 232 是「**单次装饰的本遍新增**」，
> 与第一、二轮的 243 是**不同口径**（那一轮统计的是最终世界里中心区块的钻石总量，含更早各遍写入与结构来源）。
> 两组数字不可直接相减比较。

### 5.1 同一 viewer 的逐条钻石 feature 事件时间线（示例：viewer (0,0)）

每条钻石 feature 的完整时间线都被两侧逐事件记录，本轮结果**完全一致**：

```
ore_diamond            原版 14 事件 / 重放 14 事件
  两侧同为：判定(4,-49,12)通过 → 放置(4,-49,12)进doPlace → 判定(11,-11,5)通过 → 放置(11,-11,5)进doPlace
           → 判定(9,-75,6)通过 → 放置(9,-75,6)进doPlace → 判定(3,15,12)通过 → 放置(3,15,12)进doPlace
           → 判定(13,-113,5)通过 → 放置(13,-113,5)进doPlace → 判定(10,-47,15)通过 → 放置(10,-47,15)进doPlace
           → 判定(15,-49,12)通过 → 放置(15,-49,12)进doPlace
ore_diamond_medium     原版 4 事件 / 重放 4 事件 → 两侧逐项一致
ore_diamond_large      原版 0 事件 / 重放 0 事件（本遍该区块内无候选位置通过）
ore_diamond_buried     原版 8 事件 / 重放 8 事件 → 两侧逐项一致
逐 feature 对齐后未发现任何差异
```

---

## 六、本轮定位到的第一处分叉（已修复，且已在源码里核对）

### 6.1 修复前的实测证据

修 `ensureCanWrite` 之前的那一轮（`run11`），10 个 viewer 全部落在同一层分叉，结论行原文：

```
⇒ 第一处分叉（按执行顺序取最早一处）：第 1 条钻石 feature（ore_diamond）
  第 6 个事件的类型就不同：原版 放置（(9,-75,6)进doPlace）
                       ／ 重放 生物群系判定（(7,-94,14)通过）
  ⇒「判定—放置」的交错顺序不同，说明某一侧的候选位置数量或判定结果在此之前已经不同
```

同一条 feature 的两侧时间线（`run11`）：

```
原版：判定(4,-49,12)通过 放置(4,-49,12)进doPlace  判定(11,-11,5)通过 放置(11,-11,5)进doPlace
      判定(9,-75,6)通过  放置(9,-75,6)进doPlace   判定(3,15,12)通过 放置(3,15,12)进doPlace …（共 7 次放置）

重放：判定(4,-49,12)通过 放置(4,-49,12)进doPlace  判定(11,-11,5)通过 放置(11,-11,5)进doPlace
      判定(9,-75,6)通过  ← 通过，但**没有**对应的放置
      判定(7,-94,14)通过  判定(4,-42,2)通过 放置(4,-42,2)进doPlace  判定(10,-116,11)通过  判定(3,-105,13)通过
```

关键相关性非常硬：

| 侧 | 被调用的 `OreFeature#place` 的 origin | 共同特征 |
| --- | --- | --- |
| 原版 | y = −49、−11、−75、15、−113、−47、−49（7 次） | 含 y < −64 的 **2** 个 |
| 重放 | y = −49、−11、−42（3 次） | **全部** y ≥ −64 |

被重放「吞掉」的 4 个位置（y = −75、−94、−116、−105）**全部低于世界底部 −64**；
被放行的 3 个（y = −49、−11、−42）**全部在世界高度内**。差异点因此被唯一地锁定在
「**放置之前的那一次写权限判定**」。

### 6.2 第一处分叉的确切位置

`PlacedFeature#placeWithContext` 拿到候选位置后调用的是 `ConfiguredFeature#place`，
后者转发到 `Feature#place` 的 5 参重载（`Feature.java:180-182`）：

```java
public boolean place(FC config, WorldGenLevel level, ChunkGenerator chunkGenerator,
                     RandomSource random, BlockPos origin) {
    return level.ensureCanWrite(origin)
         ? this.place(new FeaturePlaceContext<>(...))   // ← 只有这里通过，OreFeature#place 才会被调用
         : false;
}
```

而 `ConfiguredFeature#place`（`ConfiguredFeature.java:22-24`）正是走的这条 5 参重载：

```java
public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
    return this.feature.place(this.config, level, chunkGenerator, random, origin);
}
```

**⇒ 第一处分叉就在 `WorldGenLevel#ensureCanWrite`，且发生在 `OreFeature` 内部之前。**
原版判据（[`WorldGenRegion.java:228-258`](file:///d:/mcaddon/yiyiaddon/01-开发参考库/Minecraft-26.1.2-Mojang源码/net/minecraft/server/level/WorldGenRegion.java#L228-L258)）只有两条：

1. 目标方块所在区块与中心区块的切比雪夫距离 ≤ 本步骤写半径（`FEATURES` 是 1）；
2. **仅当中心区块处于「升级中」**（`ChunkAccess#isUpgrading()` = `getBelowZeroRetrogen() != null`，
   `ChunkAccess.java:476-478`）时，才追加一条「越界高度即拒」。

普通生成期**根本没有第 2 条**。而我们影子层原先无条件加了 `!isOutsideBuildHeight(y)`，
于是把原版放行的低空 origin 全部拒掉。

### 6.3 为什么这个错会放大成整片漂移

钻石四条的高度修饰符是 `HeightRangePlacement.triangle(aboveBottom(-80), aboveBottom(80))`
（`OrePlacements.java:184-204`，主世界即 y ∈ [−144, 16]），placement origin 常态化低于 −64。
- 少一次 `OreFeature#place` 调用 = 少消耗一整段随机数（`nextFloat` + 2×`nextInt(3)` + size×`nextDouble` + 候选点 `nextFloat`）；
- 随机流一旦错位，同一条 feature **之后所有矿脉**一起漂移 ⇒ 命中率看着像「算法不对」，实际是装置不对。

### 6.4 修复

`ShadowLevelFactory#canWrite` 改为逐行复刻原版（判据写成条件式，不把「本实验恰好不升级」当成「原版不判」）：

```java
int dx = |chunkX(pos) − center.x|, dz = |chunkZ(pos) − center.z|;
if (dx > FEATURE_WRITE_RADIUS || dz > FEATURE_WRITE_RADIUS) return false;
return !(centerUpgrading && delegate.isOutsideBuildHeight(pos.getY()));
```

修复后（`run12`）：第一阶段 **10/10 viewer 完全一致**，第二阶段 **10/10 目标区块**、
**90/90 组「目标区块 × viewer」全部完全一致**。

### 6.5 本轮同时修正的一处台账缺陷（会影响定位准确性）

第一版台账把「生物群系判定」与「放置」存成两张表再按下标对照。实测出现
「原版 7 判定 / 7 放置，重放 7 判定 / 3 放置」——两张表下标不再一一对应，
分析器会把**下游症状**报成第一处分叉（当时报的是「第 3 次放置的 origin 不同」）。
本轮改为：

- **单条事件时间线**：`判定 1 → 放置 1 → 判定 2 → 放置 2 → …` 按真实交错顺序记录，
  并把「事件类型错位」本身当成一处分叉如实报出；
- **记账窗口用栈**：回放期间我方读世界可能触发区块生成、生成过程会再次进入 `placeWithBiomeCheck`，
  单引用保存窗口会被内层清掉（实测窗口栈深度峰值 1，本轮未发生嵌套，但已按更坏情况加固）。

这四个注入点的「被调用总次数」与「记账窗口数」都进了报告，**任何一项为 0 即说明该探针未生效**，
对应层的「两侧一致」一律不成立——用来防止把「没取证」误读成「没有差异」。

---

## 七、第二阶段：3x3 九个 viewer 的跨区块贡献（逐 viewer 单独输出）

第一阶段的判据是「viewer 自己的 3x3」；第二阶段换成「**每个 viewer 这一遍实际写进目标区块的钻石**」，
九个 viewer **一遍一遍单独比对**，不先 union。

**10 个目标区块的九遍合计（按顺序合并，不提前 union）**

| 目标区块 | 真值 | 预测 | 匹配 | 漏报 | 错报 | 查全 | 查准 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| (0,0) | 45 | 45 | 45 | 0 | 0 | 100.00% | 100.00% |
| (1,0) | 9 | 9 | 9 | 0 | 0 | 100.00% | 100.00% |
| (0,1) | 29 | 29 | 29 | 0 | 0 | 100.00% | 100.00% |
| (1,1) | 21 | 21 | 21 | 0 | 0 | 100.00% | 100.00% |
| (-1,0) | 22 | 22 | 22 | 0 | 0 | 100.00% | 100.00% |
| (0,-1) | 27 | 27 | 27 | 0 | 0 | 100.00% | 100.00% |
| (-1,-1) | 21 | 21 | 21 | 0 | 0 | 100.00% | 100.00% |
| (2,2) | 18 | 18 | 18 | 0 | 0 | 100.00% | 100.00% |
| (3,-1) | 18 | 18 | 18 | 0 | 0 | 100.00% | 100.00% |
| (-2,3) | 33 | 33 | 33 | 0 | 0 | 100.00% | 100.00% |

**逐 viewer 维度**：10 个目标区块 × 9 个 viewer = **90 组**，每一组都是
「漏报 0 / 错报 0 / 逐 BlockPos 完全一致：是」（报告全文检索 `完全一致：否` 命中 0 次）。
九遍装饰中的每一遍都用自己的 viewer 快照安装、各自还原校验通过。

要点（对照用户第十一节列的检查项）：

- 九个 viewer 的真实装饰执行顺序按原版顺序逐遍复现，**没有无序 union**；
- viewer 与目标区块的切比雪夫距离由修复后的 `ensureCanWrite` 判定，与原版一致；
- 跨区块写入（矿脉跨区块边界、跨 Chunk Section 写入）在两个口径下都落在同一点集；
- 「viewer 开始之前已经由其它区块写进来的钻石」被 pre-diamond 快照如实带入，两侧一致（`安装快照后钻石数与 pre-diamond 完全一致`）。

---

## 八、装置自证与其它支撑数据

| 项目 | 结果 |
| --- | --- |
| 对照1（中心区块整片改写为**紫水晶块**，不可替换） | 预测 **0** 个钻石 ⇒ 符合期望（说明世界状态确实进入了放置链路） |
| 对照2（中心区块整片改写为**石头**，可替换） | 预测 **293** 个 > 真值 243 ⇒ 符合期望 |
| 索引表交叉核对 | 一致：步骤数 11、feature 总数 164、钻石索引比对 **44 项全部相同** |
| 上下文构造 | 生物群系源 / ChunkGenerator / RandomState 全部自建，未被服务端对象代劳 |
| 修复后 oracle 取证 | 43 个区块取到 post-diamond，逐 feature 记录 172 条；记账异常 0 |
| 注入点调用计数 | `noteBiomeCheck` 99641 次 / `notePlace` 62054 次 / `noteVein` 24275 次 / `noteAccept` 1811483 次（四项均 > 0，探针全部生效） |
| 还原校验 | 10/10（第一阶段）+ 90/90（第二阶段）全部通过，测试世界逐格无残留 |
| 生物群系可复现性 | 匹配 138240 / 总计 138240 = **100.0000%** |

---

## 九、已排除 / 未验证（证据边界）

### 9.1 已经排除的原因（都有实测证据）

| 原因 | 排除依据 |
| --- | --- |
| decorationSeed / featureSeed / FeatureSorter 全局索引错误 | 事件时间线前 3 个事件（含随机消耗最大的前两次放置）两侧逐位一致；索引表 44 项钻石索引全部相同 |
| PlacedFeature / PlacementModifier 重放错误 | 修复后同一 viewer 的每条钻石 feature 事件时间线（判定位置 + 判定结果 + 放置 origin）逐项一致 |
| OreFeature 内部随机流或上下文不同 | 修复后每条放置的矿脉几何参数与候选点接受序列逐项一致 |
| 「40.7% 残差来自拿不到正确生成期状态」（一、二轮的假设） | 二轮已否掉；本轮进一步证明：在正确 pre-diamond 输入下，剩余差异**完全来自我方影子层的一处判据错误**，与种子算法无关 |
| 最终世界状态 ≠ 生成期状态导致的错报 | 第二轮结论；本轮 oracle 与重放输入都取自同一次装饰的 pre-diamond，不受最终世界影响 |
| 生物群系过滤（BiomeFilter）判定不一致 | 生物群系判定轨迹两侧逐项一致，且该层已单独取证（`noteBiomeCheck` 99641 次） |
| 其它钻石来源被误判成「钻石 OreFeature 漏报」 | oracle 的 pre-diamond 快照取自**矿步骤第一条钻石 feature 之前**，此刻 `UNDERGROUND_STRUCTURES` 步（含 `fossil_diamonds`）已执行完，其产出已包含在 pre 中，因此不会被算成本遍新增；本轮 0 漏报 0 错报与该口径自洽 |
| 记账缺陷伪造成「分叉」 | 已改为单条事件时间线 + 窗口栈；四个注入点计数与窗口数进报告可直接核对 |

### 9.2 仍未验证（本轮**没有**证明的事）

| 未验证项 | 说明 |
| --- | --- |
| **客户端能否仅凭 Seed 离线构造 pre-diamond 输入状态** | 本轮重放的输入是**从真实生成过程抓取并安装**的 pre-diamond 快照。它证明的是「**算法在正确输入下成立**」，**不证明**「客户端已经能离线构造该输入」。这是下一阶段的全部工作量所在（离线 WorldGenRegion / ChunkStep / GenerationChunkHolder 等） |
| 关闭 C2ME 等 worldgen 优化 Mod 的对照 | 本轮的实机环境带 C2ME（并行 worldgen）。本轮未做「关 C2ME 重跑同一批区块」的对照实验 |
| 更高纵向范围的钻石 | 扫描只覆盖 y ≤ 31 的 6 个 section。钻石四条高度区间上界是 y = 16 + 矿脉半径，理论上足够，但**未逐个证明** y > 31 不存在钻石 |
| 其它矿物 / 其它维度 | 按用户口径本轮不涉及 |
| 26.2 / 26.3 | 按用户口径**未做任何 26.2 适配、未创建未修改任何 26.3 内容** |
| 真机服务器环境 | 本轮全程单人集成服务端；服务器真实 Seed 的获取与真实性验证属后续阶段 |

---

## 十、编译与运行结果

| 项目 | 结果 |
| --- | --- |
| 编译 | `gradlew compileJava --console=plain` → **EXIT=0** |
| 实机运行 | `gradlew runClient` → **BUILD SUCCESSFUL**；报告落盘 `run-26.1.2/seedpoc-实验报告.txt`，日志 `run-26.1.2/seedpoc-run12-console.log` |
| 运行参数 | `-Dyiyiaddon.seedpoc.enabled=1`、`chunks=0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3`、`terrain=0`、`exit=1` |
| 固定条件 | Minecraft 26.1.2 / 原版主世界 / Seed 20260922 / 钻石 / 上述 10 个固定区块（与前两轮相同，**未换 Seed、未换区块、未删除失败区块、未只测成功案例**） |
| 失败轮次留档 | `run11`（修复前，第一阶段 0/10）与 `run12`（修复后，10/10）两份日志都保留，可复查定位过程 |

---

## 十一、是否具备进入下一阶段条件

**具备，且边界必须写清楚：**

1. 单 viewer / 单次真实装饰 / 同一 pre-diamond 输入状态下，
   Seed + decorationSeed + featureSeed + FeatureSorter + PlacedFeature + OreFeature 重放
   **已经可以逐 BlockPos 100% 复现**原版这一遍真实写入的钻石（10/10 viewer、90/90 跨区块比对）。
2. 跨区块贡献的口径也已在九个 viewer 上单独验证通过（第二阶段），**不是只验了 union**。
3. 但本轮**没有**证明客户端能离线构造 pre-diamond 输入状态。这是下一阶段（离线 worldgen 上下文）
   的全部工作内容，**不得跳过**。

因此：

- **本轮不作为「精准种子挖矿已实现」的依据**，只在报告里写硬结论：
  「单次装饰的钻石放置链在给定正确输入下可逐 BlockPos 复现」。
- 下一步（尚未开始）：研究如何在客户端仅凭 Seed 离线构造该 pre-diamond 状态
  （`WorldGenRegion` / `ChunkStep` / `GenerationChunkHolder` / `StaticCache2D` 与必要的
  AccessWidener / Mixin），完成之后才谈正式「种子挖矿」页面与 Seed 真实性验证。

---

## 十二、后续路线（保持不变）

```
本轮：单次装饰链已证明可逐 BlockPos 复现（输入由真实生成过程提供）
  ↓
下一阶段：证明客户端能仅凭 Seed 离线构造该输入状态
  ↓
PoC 结案（实验探针整包删除）
  ↓
正式「种子挖矿」独立页签（位于现有自动挖矿控制台内部）
  + Seed 真实性验证
  + 读取现有自动挖矿矿物选择
  + Seed 预测结果接现有目标 / 渲染系统
  + 继续复用现有自动挖矿执行链（怎么过去 / 怎么挖 / 怎么吃 / 怎么回家 / 怎么处理背包）
  ↓
真机服务器验证
```

正式页面仍然遵守：玩家可见内容全部中文；只负责 Seed 开关、服务器 Seed、验证状态、预测状态；
矿物选择继续使用现有自动挖矿配置；精准采集 / 时运继续使用现有逻辑；
`personalMode` / 自用模式下隐藏「种子挖矿」页签。**本轮未实现这些正式功能。**
