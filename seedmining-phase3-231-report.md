# 231 · 追加 · 《种子挖矿正式化第三阶段报告 —— 多人服务器离线预测环境解耦》

日期：2026-09-23
分支：`master`（Minecraft 26.1.2，业务唯一真源）
阶段：种子挖矿 **正式化第三阶段**（目标是让正式 `DiamondSeedPredictor` 不再依赖 `ServerLevel`，从而在真正的多人服务器客户端上也能离线预测）。
上游依据：[229] 正式化第一阶段（正式架构 / 数据模型 / Predictor 迁移）、[230] 正式化第二阶段（UI / Seed 配置 / 维度状态 / 异步预测）、[228] FEATURES 调度因果定案。

**本阶段结论（先给答案）：目标在 Minecraft 26.1.2 上被原版类型契约挡住，判定为「架构阻塞」，未实施环境解耦。**

结论不是「研究不出来」，而是**编译期可证明的不可能**：26.1.2 的 `WorldGenLevel` 直接继承 `ServerLevelAccessor`，而 `ServerLevelAccessor#getLevel()` 的返回类型就是 `ServerLevel`；`ServerLevel` 只有一个构造器，要 `MinecraftServer` 与 `LevelStorageSource.LevelStorageAccess`（真实存档目录）——这两样在多人服务器客户端上都不存在。因此任何「离线 worldgen 环境」实现都必须给出一个 `ServerLevel`，而在多人客户端上造它等于在客户端里起一个服务器（阶段口径第三节明令禁止）。

**本阶段如实交付**：完整依赖审计 + 26.1.2 原版字节码级证据链 + 两条静态搜索结果 + 状态枚举与 UI 文案的更名与补全（口径第二十四、二十六节）+ 230 全部基线的复跑证明（零退化）。
`compileJava` / `build` 均 `EXIT=0`；`runClient` 正常启动；服务层回归装置复跑**全部判定通过**。

---

## 零、结论摘要

1. **阻塞点不是「缺某项数据」，而是「缺一个类型」**：`net.minecraft.world.level.WorldGenLevel extends net.minecraft.world.level.ServerLevelAccessor`，后者的第一个抽象方法就是 `ServerLevel getLevel()`。
2. **两个受影响的阶段不可绕过**：`ChunkGenerator#buildSurface(WorldGenRegion, …)` 与 `#applyCarvers(WorldGenRegion, …)` 是**抽象方法**，形参是**具体类** `WorldGenRegion`，而 `WorldGenRegion` 的唯一构造器要 `ServerLevel`。SURFACE 与 CARVERS 正好会改方块状态，直接决定钻石能不能被放上去 —— 口径第三节第 5 条明令禁止删这两个阶段。
3. **第二个独立阻塞点**：`StructureManager#forWorldGenRegion(WorldGenRegion)`、`new ProtoChunk` 之外，`StructureTemplateManager` 的构造器要 `LevelStorageAccess`（真实存档目录），且**在构造器内就解引用**（字节码偏移 37 调 `LevelStorageAccess#getLevelPath`）——客户端连一个空的都造不出来。
4. **现有实现没有「读真实世界状态」的架构错误**（口径第五节要求的 A/B 区分）：全部方块状态读取都走自持离线区块；`StructureManager` 的两条路径都不从真实世界读数据；宿主 ChunkMap 查询增量实测恒 0。
5. **依据口径第五十六节：报告 + 停止，不伪造多人支持**。没有写任何「环境解耦」代码、没有新建 `seed/worldgen/environment` 包、没有动 `OfflineWorldgenContext` / `OfflineChunkPipeline` / `DiamondSeedPredictor` / `PredictionSession`。
6. **本阶段唯一落地的改动是三处小改**：状态枚举 `NO_WORLD_HOST` 更名为 `WORLDGEN_ENVIRONMENT_UNAVAILABLE`（口径第二十四节）、服务层新增「预测模型」声明（口径第十二、二十六、三十四节）、种子页环境区新增「预测模型」行与适用范围小字（口径第二十六节）。
7. **230 基线零退化**：服务层回归装置复跑，Seed 20260922 `(0,0)` = 45、Seed 2 `(-400,380)` = 23（1 敏感 / 22 未解析 / 0 确定）、Seed 12345 = 31 / 29(7 敏感) / 24 / 26，服务层 vs 直连 Predictor 逐 BlockPos 一致，维度切换与退出世界清理全部通过。

---

## 一、230 结论复核（报告项 1）

| 230 已确立的事实 | 本轮状态 |
| --- | --- |
| 种子挖矿页在控制台内，`personalMode` 下隐藏 | 未破坏（复跑烟测：两种模式整窗装配成功，种子页可构建 804 像素） |
| Seed 输入只做 Java long 解析、非法不启动预测 | 未破坏（10 项解析矩阵复跑全部保持） |
| 配置按服务器 / 存档隔离（`WorldIdentity.fileSafeServer()`） | 未改动一行 |
| 维度自动识别 + 主世界 / 下界 / 末地 / 自定义显示 | 未破坏（下界 → 返回主世界复跑通过） |
| 异步预测（单线程后台 Executor）+ 任务代号取消 | 未改动一行（复跑提交耗时 59~803 微秒） |
| 退出世界释放运行时缓存、保留配置 | 未破坏（复跑：结果已清 / 预测器已释放 / 配置保留） |
| `DETERMINISTIC` 恒 0、UI 如实显示 | 未改动一行（复跑六用例「确定性 0」全部保持） |
| 230 项验收 25/25 通过 | 本轮复跑后仍全部成立（见第 45 节） |

**230 留下的最硬边界**：`SeedMiningService` 通过 `Minecraft#getSingleplayerServer().overworld()` 取宿主 ⇒ 多人服务器 `getSingleplayerServer() == null` ⇒ `WORLDGEN_ENVIRONMENT_UNAVAILABLE`（原名 `NO_WORLD_HOST`）⇒ 不能预测。本阶段的任务就是消除这条边界，结果是**证明它无法在原版 26.1.2 上消除**。

---

## 二、为什么没有直接进入渲染（报告项 2）

口径第六十节说「真正服务器上 Predictor 能运行才是现在最重要的门槛」，本节给原因：

1. 渲染层（`232`）的输入是**预测结果集合**。若预测在真实服务器上根本跑不出来，渲染层做出来也只能在单人世界里自娱 —— 排序上无意义（口径：多人可运行 > 架构正确 > 结果一致 > 性能 > UI 美化）。
2. 更坏的情况是：如果先做渲染，玩家会在多人服务器上看到一个**永远为空**的结果集，而 UI 又不敢说清「不是没有矿，是算不了」——这正是口径第十二节要防的「把不能跑说成没有」。
3. 因此本阶段把全部预算花在「多人能不能跑」这一件事上，结论是**不能**，并把证据留在报告里。

---

## 三、原 ServerLevel 依赖完整清单（报告项 3、4、5）

审计范围：`src/main/java/com/yiyiaddon/seed/**`（20 个类），逐文件读源码 + `javap` 核对 26.1.2 官方名 jar。

`ServerLevel` 全文命中 **16 行 / 6 个文件**（`rg` 结果见第 18 节）。逐项拆解如下：

| # | 使用位置 | 从 ServerLevel 取什么 | 性质 | 客户端能否独立提供 | 新来源 |
| --- | --- | --- | --- | --- | --- |
| 1 | `OfflineWorldgenContext:102` | `host.registryAccess()` | **A 环境数据** | ✅ 能 | 客户端 `Level#registryAccess()`（见第 10 节来源说明） |
| 2 | `OfflineWorldgenContext:114` | `RandomState.create(registries, NOISE_SETTINGS.OVERWORLD, seed)` | A（由注册表 + 种子派生） | ✅ 能 | 与 #1 同一份注册表，纯种子派生 |
| 3 | `OfflineWorldgenContext:115` | `generator.createState(STRUCTURE_SET, randomState, seed)` | A | ✅ 能 | 同上 |
| 4 | `OfflineWorldgenContext:119` | `host.structureManager()` | A（服务）：只用 `setStartForStructure` / `getStartForStructure`，两方法都带**显式 `StructureAccess` 参数**，不读 level | ⚠️ 需自建 | `new StructureManager(LevelAccessor, WorldOptions, StructureCheck)` — 但 `LevelAccessor` 的自建实现同样撞在 #11 上 |
| 5 | `OfflineWorldgenContext:119` | `host.getStructureManager()`（`StructureTemplateManager`） | A（资源） | ❌ **不能** | 构造器要 `LevelStorageAccess`（真实存档目录），见第 13 节 |
| 6 | `OfflineWorldgenContext:119` | `host.palettedContainerFactory()` | A | ✅ 能 | `PalettedContainerFactory.create(RegistryAccess)`（**静态工厂**，无需 level） |
| 7 | `OfflineWorldgenContext:174` | `host.getServer().getWorldGenSettings().options().generateStructures()` | A | ⚠️ 需替代 | 客户端没有 `WorldOptions`；可用环境声明（原版默认 true） |
| 8 | `OfflineChunkPipeline:244` | `new ProtoChunk(pos, UpgradeData.EMPTY, ctx.host(), factory, null)` —— 实参只用 `LevelHeightAccessor` | A | ✅ 能 | `LevelHeightAccessor.create(minY, height)`（`DimensionType#minY()/height()`） |
| 9 | `OfflineChunkRegion:81` | `super(ctx.host(), cache, step, center)` → `WorldGenRegion(ServerLevel, …)` | **类型契约** | ❌ **不能** | **无解**，见第六节 |
| 10 | `DiamondSeedPredictor:70`, `:74` | `host.dimension()`（构造校验） | A | ✅ 能 | 环境里的维度键 |
| 11 | `PredictionSession:102-104` | `host.dimension()`（会话 / 宿主维度一致性校验） | A | ✅ 能 | 环境里的维度键 |
| 12 | `SeedMiningService:393` | `client.getSingleplayerServer().overworld()`（取宿主） | 取宿主 | ❌ 不能 | **本阶段目标，未达成** |

**性质判定（口径第五节 A / B 区分）**：
- **A 类（环境 / 资源数据）**：#1~#8、#10~#11 全部是环境数据或与种子无关的服务对象，理论上都能由客户端侧数据替代 —— **除了 #5（`StructureTemplateManager`）与 #9 的落地形态（`WorldGenRegion` 必须有 `ServerLevel` 才能构造）**。
- **B 类（真实世界状态）**：**一处都没有**。详见第五节。

---

## 四、哪些依赖只是环境数据（报告项 4）

把上表 A 类逐项归到「离线 worldgen 真正需要什么」，可以得到一张**环境需求清单**（这张清单本身是本阶段的有效产出，即使解耦未实施）：

| 环境需求 | 26.1.2 原型出处 | 客户端可得性 |
| --- | --- | --- |
| 注册表（`RegistryAccess`） | `Level#registryAccess()` | ✅（客户端 `RegistryAccess.Frozen` 由登录包下发，见第 10 节） |
| 维度类型（minY / height / seaLevel） | `DimensionType#minY()/height()` | ✅（来自 `ClientLevel#dimensionType()`） |
| 区块高度访问器 | `LevelHeightAccessor.create(minY, height)`（静态） | ✅ |
| 方块状态调色板工厂 | `PalettedContainerFactory.create(RegistryAccess)`（静态） | ✅ |
| 调色板工厂 / 生物群系源 / 噪声设置 | `MultiNoiseBiomeSourceParameterLists.OVERWORLD`、`NoiseGeneratorSettings.OVERWORLD` 走注册表 | ✅ |
| 结构模板管理器 | `StructureTemplateManager(ResourceManager, LevelStorageAccess, DataFixer, HolderGetter<Block>)` | ❌ 缺 `LevelStorageAccess` |
| 结构管理器 | `StructureManager(LevelAccessor, WorldOptions, StructureCheck)` | ❌ `LevelAccessor` 的实现撞 `ServerLevel` |
| 是否生成结构 | `WorldGenSettings#options().generateStructures()` | ⚠️ 需环境声明 |
| **离线世界视图（`WorldGenLevel`）** | `WorldGenRegion(ServerLevel, …)` | ❌ **无解** |

即：**环境需求清单里 8 项有 6 项客户端本来就拿得到，卡死的是最后两项**，而最后一项（`WorldGenLevel`）是全部生成阶段的公共入口。

---

## 五、是否存在真实世界状态依赖（报告项 5、15、16、17）

**审计结论：没有。** 现有正式实现是「只借服务，不借数据」，四条独立证据：

1. **区块数据全部自持**：`OfflineChunkCache` / `OfflineChunkHolder` 自己按 `ChunkPos` 存每个状态的产出；`WorldGenRegion#getChunk` 的唯一入口是 `StaticCache2D<GenerationChunkHolder>`（由 `OfflineChunkCache#openWindow` 用自建 holder 建立），**不会**转到宿主。
2. **五处会读真实世界的入口已被覆盖**（[OfflineChunkRegion.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/worldgen/OfflineChunkRegion.java#L90-L113)）：`getSeed` / `getRandom` / `getBiomeManager` / `getUncachedNoiseBiome` / `isOldChunkAround`（最后一个固定 false = 不向真实世界要任何判断）。
3. **唯一的真实世界口子被计数**：`getChunkSource()` 是父类唯一会转发给宿主 `ServerChunkCache` 的路径，已计数并在每次预测里上报。本轮复跑六个用例的「宿主 ChunkMap 查询增量」全部为 **0**。
4. **`StructureManager` 两条路径都不读真实世界**：
   - `createStructures(...)` 用的是宿主的结构管理器，但它只被调用 `setStartForStructure` / `getStartForStructure`，两者都带**显式 `StructureAccess` 参数**（= 传入的那个离线区块），不经过 level（字节码核对：仅 `#733` / `#316` 两处 `StructureManager.*` 调用）。
   - 其余阶段用 `hostStructureManager.forWorldGenRegion(region)` → `new StructureManager(region, worldOptions, structureCheck)`，之后的 `startsForStructure` 读的是 `LevelAccessor#getChunk(x, z, STRUCTURE_REFERENCES)`，也就是**离线 region**。

因此口径第五节 B 类（真实 BlockState / 真实 Chunk / Heightmap / 真实结构状态）**未被污染**，不需要「先停下来报告架构错误」。

---

## 六、为什么「环境解耦」在 26.1.2 上做不到（报告项 6、7；口径第五十六节五问）

### 6.1 证据链（全部来自 26.1.2 官方名 jar：`fabric-loom/26.1.2/minecraft-merged.jar`，`javap` 直读）

```
（1）public interface net.minecraft.world.level.WorldGenLevel
         extends net.minecraft.world.level.ServerLevelAccessor {
         long getSeed();
         default boolean ensureCanWrite(BlockPos);
         default void setCurrentlyGenerating(Supplier<String>);
     }

（2）public interface net.minecraft.world.level.ServerLevelAccessor
         extends net.minecraft.world.level.LevelAccessor {
         ServerLevel getLevel();                       ← 抽象方法，返回类型硬绑定 ServerLevel
         DifficultyInstance getCurrentDifficultyAt(BlockPos);
         default void addFreshEntityWithPassengers(Entity);
     }

（3）public class net.minecraft.server.level.WorldGenRegion
         implements net.minecraft.world.level.WorldGenLevel {
         public WorldGenRegion(ServerLevel, StaticCache2D<GenerationChunkHolder>, ChunkStep, ChunkAccess);
     }                                                  ← 唯一构造器

（4）public class net.minecraft.server.level.ServerLevel extends Level implements WorldGenLevel {
         public ServerLevel(MinecraftServer, Executor, LevelStorageSource$LevelStorageAccess,
                            ServerLevelData, ResourceKey<Level>, LevelStem, boolean, long,
                            List<CustomSpawner>, boolean);
     }                                                  ← 唯一构造器

（5）public abstract class net.minecraft.server.MinecraftServer ... { }   ← 抽象类，14 个抽象方法

（6）net.minecraft.world.level.chunk.ChunkGenerator：
         public abstract void buildSurface(WorldGenRegion, StructureManager, RandomState, ChunkAccess);
         public abstract void applyCarvers(WorldGenRegion, long, RandomState, BiomeManager,
                                           StructureManager, ChunkAccess);
         public void applyBiomeDecoration(WorldGenLevel, ChunkAccess, StructureManager);
         public void createReferences(WorldGenLevel, StructureManager, ChunkAccess);

（7）net.minecraft.world.level.StructureManager：
         public StructureManager(LevelAccessor, WorldOptions, StructureCheck);
         public StructureManager forWorldGenRegion(WorldGenRegion);

（8）net.minecraft.world.level.chunk.status.WorldGenContext(
         ServerLevel, ChunkGenerator, StructureTemplateManager, ThreadedLevelLightEngine,
         Executor, LevelChunk$UnsavedListener)

（9）net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager(
         ResourceManager, LevelStorageSource$LevelStorageAccess, DataFixer, HolderGetter<Block>)
         —— 构造器字节码偏移 37 即 invokevirtual LevelStorageAccess.getLevelPath(LevelResource)
```

### 6.2 推论（三步，全部是类型层面的，与运行环境无关）

1. 任何 `WorldGenLevel` 实现都必须提供 `ServerLevel getLevel()`（证据 1+2）；
2. `WorldGenLevel` 的实际落地形态 `WorldGenRegion` 只有 `WorldGenRegion(ServerLevel, …)` 一个构造器，且构造器内即解引用 `ServerLevel`（`getSeed` / `getLevelData` / `getChunkSource` / `dimensionType`），传 `null` 立刻 NPE（证据 3）；
3. `ServerLevel` 只能由 `MinecraftServer` 造，而 `MinecraftServer` 是抽象类、`ServerLevel` 构造器还要真实存档目录（证据 4+5）。

⇒ **在多人服务器客户端（无 `MinecraftServer`、无存档目录）上，SURFACE / CARVERS 无法执行**（证据 6：形参是具体类 `WorldGenRegion`），**FEATURES 也无法执行**（`applyBiomeDecoration` 的形参 `WorldGenLevel` 同样继承 `ServerLevelAccessor`）。而 FEATURES 正是放钻石的那一步 —— 也就是说，卡住的不是「环境数据」，是**放矿那一步的入口**。

### 6.3 口径第五十六节五问，逐条回答

**问 1：明确是哪项资源？**
不是资源，是一个**实例类型**：`net.minecraft.server.level.ServerLevel`。它被 26.1.2 的世界生成接口**在类型层面**要求（`WorldGenLevel extends ServerLevelAccessor` 的抽象方法 `ServerLevel getLevel()`），因此不是「取某个字段」的问题，而是「必须能给出这个类型」的问题。

**问 2：为什么 ServerLevel 有而 Client 没有？**
`ServerLevel` 只由服务端创建：集成服务端走 `MinecraftServer#createLevels`；专用服务端同理。多人客户端连接的是**别人的**服务器，本地只有一个 `ClientLevel`（`net.minecraft.client.multiplayer.ClientLevel extends Level`）—— 它不是 `ServerLevel`，两者无继承关系，无法转换，也无法「补一个」出来。

**问 3：是否存在 Vanilla / ResourceManager 重建途径？**
**没有。** 这条不依赖资源管理器：`ServerLevel` 的构造需要 `MinecraftServer`（抽象类，14 个抽象方法）与 `LevelStorageSource.LevelStorageAccess`（真实存档目录）。用 `ResourceManager`、数据包或注册表都换不来这两样东西。**任何「重建」都等价于在客户端进程里起一个服务器**，即口径第三节第 1、2 条与第十三节明令禁止的做法。

**问 4：为什么尝试失败？**（逐条列出被否决的方案与被否决原因）

| 方案 | 否决原因 |
| --- | --- |
| 自己写一个 `WorldGenLevel` 实现（把 `WorldGenRegion` 逐行搬过来，去掉 ServerLevel） | **编译期不可能**：接口要求 `ServerLevel getLevel()`，没有可返回的对象 |
| 子类化 `WorldGenRegion` 并给 `super(...)` 传 `null` 或占位对象 | 构造器内立即解引用（`getSeed` / `getLevelData` / `getChunkSource` / `dimensionType`）→ NPE |
| 用反射 / `Unsafe` 绕过构造器造一个「空壳 ServerLevel」 | 仍是伪造服务器世界（口径第十三节禁止）；且未初始化字段会被 worldgen 读到，结果不可信 |
| 自建 `StructureTemplateManager` | 构造器要求 `LevelStorageAccess` 且立即解引用 → 客户端连空壳都造不出（第九节） |
| 自建 `StructureManager` 替换宿主那个 | 它要 `LevelAccessor`，而 `WorldGenLevel`（唯一合适的实现来源）又撞回 `ServerLevel` |
| 起一个本地存档 / 集成服务端当宿主（口径第三节 1、2） | 明令禁止；且会带进**本地**世界的注册表与设置，不是服务器的 |
| 跳过 SURFACE / CARVERS（口径第三节 5） | 这两阶段会改方块状态，直接决定 `canPlaceOre` 的判定 → 会破坏 223~229 已证明的精确链 |
| 自己重写 SURFACE / CARVERS / FEATURES 的驱动（口径第三节 6） | FEATURES 的入口 `applyBiomeDecoration(WorldGenLevel, …)` 照样卡在同一处；且属「手写 worldgen」禁止项 |
| 在客户端跑一个 headless `MinecraftServer` | 与「起服务器」等价（问 3），明令禁止 |

**问 5：当前还能支持到什么程度？**
**单人世界（集成服务端存在）全链可用，且 229 / 230 的全部数字本轮复跑保持不变**；多人服务器只能做到「如实告知环境不可用」（`WORLDGEN_ENVIRONMENT_UNAVAILABLE`：当前世界生成环境不可用），并明确不伪装成「这个区块没有矿」。

### 6.4 为什么「不做运行时实测」也能定论

口径第五十五节要求「不能只编译通过就宣布多人已支持」。本轮**没有**宣布支持 —— 结论是**不支持**，而「不支持」这个方向的证明不需要运行时：上面三条推论是**类型层面的不可能性**（编译期就能判定：不存在合法实现）。任何一次多人实测最多只能重复观察到 `getSingleplayerServer() == null` → 环境不可用，无法推翻类型事实。因此本阶段**没有**搭建 Dedicated Server 测试环境（详见第七节）。

---

## 七、WorldgenEnvironment 架构：未实施（报告项 6、7 的落地情况）

口径第七、九、十、十七节期望的形态：

```
DiamondSeedPredictor → OfflineWorldgenContext → WorldgenEnvironment（只暴露 RegistryAccess / 维度 / 高度 / 资源 …）
```

**本阶段未实施**，理由（也是遵守口径第五十六节「然后停止」与项目铁律第 163~165 条、第 192 条改动最小化）：

1. **它解决不了本阶段要解决的问题**。`WorldgenEnvironment` 再干净，`OfflineChunkRegion` 仍然必须调 `super(serverLevel, …)`。做出来只会得到「架构看起来解耦了，但多人依旧跑不了」的结果。
2. **它会引入无收益的大改**：`OfflineWorldgenContext` / `OfflineChunkPipeline` / `DiamondSeedPredictor` / `PredictionSession` 的构造链全部要改签名，而 `PredictionSession.open(ServerLevel host, …)` 的维度一致性校验、`DiamondSeedPredictor` 的宿主维度校验等逻辑都得重排 —— 这些改动**没有任何可验收的收益**，只增加回归面。
3. **它会把「环境数据」与「宿主类型」混为一谈**。真正卡死的是宿主类型；把能拿到的环境数据搬进一个新接口，反而会让后来的人误以为「已经解耦完了」。

**结论**：本阶段不新增 `com.yiyiaddon.seed.worldgen.environment` 包，不做 `WorldgenEnvironment*` 抽象。已在 [SeedMiningRuntimeState.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningRuntimeState.java#L29-L42) 的枚举注释里把「为什么」写成了可检索的结论，避免后续重复研究。

---

## 八、正式 package tree（报告项 7）

```
src/main/java/com/yiyiaddon/seed/            ← 本阶段零结构改动
├── config/   SeedMiningConfig.java              （229/230，未改）
├── service/  SeedMiningRuntimeState.java        ✎本阶段：NO_WORLD_HOST → WORLDGEN_ENVIRONMENT_UNAVAILABLE
│             SeedMiningService.java             ✎本阶段：同步枚举 + hostAvailable 语义注释 + 预测模型声明
├── model/ · observation/ · prediction/ · worldgen/   （未改一行）
└── （本阶段**没有**新增 environment/ 子包，理由见第七节）

src/main/java/com/yiyiaddon/feature/mining/ui/console/
└── MiningSeedPage.java                          ✎本阶段：环境区新增「预测模型」行 + 适用范围小字
```

---

## 九、Client Environment 数据来源（报告项 8；可解耦部分）

虽然整体未实施，但客户端侧的数据来源已逐项核实（这张表是将来真要动手时的施工依据）：

| 需要的东西 | 客户端来源 | 核实方式 |
| --- | --- | --- |
| 注册表 | `Level#registryAccess()`（`ClientLevel` 继承得到） | `javap` 确认 `ClientLevel` 无自有实现，走 `Level` |
| 维度键 / 维度类型 | `Level#dimension()` / `Level#dimensionType()`；`DimensionType#minY()` / `#height()` | `javap` 确认 `DimensionType` 有 `minY()` / `height()` |
| 高度访问器 | `LevelHeightAccessor.create(int minY, int height)`（静态） | `javap` 确认静态方法存在 |
| 调色板工厂 | `PalettedContainerFactory.create(RegistryAccess)`（静态） | `javap` 确认静态方法存在 |
| 区块生成器 / 噪声设置 / 生物群系源 | 全部由 `RegistryAccess` 派生（不含任何 level 引用） | 现有 `OfflineWorldgenContext.create` 前 3 行 |
| 世界种子 | 由用户手填 | 230 已实现 |
| 是否生成结构 | 客户端无 `WorldOptions`；需环境声明 | 现有代码走 `host.getServer().getWorldGenSettings().options()` |
| **离线世界视图（WorldGenLevel）** | **无来源** | 第六节 |

---

## 十、Registry 来源逐项说明（报告项 9）

口径第十五节要求区分三套来源。逐项如下（这是本阶段能给出的**已核实部分**）：

| registry | 现有正式 Predictor 实际用哪套 | 客户端侧可得性 | 备注 |
| --- | --- | --- | --- |
| `MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST` | `host.registryAccess()`（= 世界 stem 的注册表） | 客户端有本地 Vanilla 副本 | 只取 `OVERWORLD` 预设 |
| `NOISE_SETTINGS` | 同上 | 客户端有本地 Vanilla 副本 | 只取 `OVERWORLD` |
| `STRUCTURE_SET` | 同上 | 客户端有本地 Vanilla 副本 | `ChunkGeneratorStructureState` 用 |
| `BIOME` | 同上（经 `generator.createBiomes`） | **在服务端同步集合内** | 见下 |
| `BLOCK`（`HolderGetter<Block>`） | 同上（`StructureTemplateManager` 用） | ✅ | 内建注册表 |
| `CONFIGURED_FEATURE` / `PLACED_FEATURE` / `NOISE` / `DENSITY_FUNCTION` / `STRUCTURE` / `TEMPLATE_POOL` / `PROCESSOR_LIST` | 同上（经注册表查表） | 客户端有本地 Vanilla 副本 | 是否随服务器同步**未逐项核对** |

**已核实的事实**：客户端的 `RegistryAccess.Frozen` **来自登录包**，不是客户端自己拼的 —— `ClientPacketListener` 里 `registryAccess` 字段的赋值链是 `CommonListenerCookie#receivedRegistries()`（字节码：`CommonListenerCookie.receivedRegistries() → RegistryAccess$Frozen` → 赋给 `ClientPacketListener.registryAccess`）。也就是说「客户端注册表 = 服务端在握手时下发的集合 + 本地 Vanilla」。

**未核实的事实（如实登记为遗留）**：26.1.2 的 `RegistryDataLoader` 里 `WORLDGEN_REGISTRIES` / `DIMENSION_REGISTRIES` / `SYNCHRONIZED_REGISTRIES` 三个清单的**逐项成员**，本阶段只确认了类与字段存在（`javap` 可见三个 `List<RegistryData<?>>` 字段），**没有**逐项核对哪些 worldgen 注册表在同步集合内。原因：整体已被类型契约阻塞，这一项核对失去意义；且它属于「即使解耦成功也仍要重新验证」的下一层问题。

**与产品口径的连带结论（口径第十二、三十四节）**：即使将来某条路能绕开 `ServerLevel`，客户端能拿到的 worldgen 注册表**从来不是「服务器真正用的那一套」的可信证据**（服务器可能装数据包 / 插件生成器 / 改 worldgen 的 Mod）。因此产品措辞只能停在「**按 Minecraft 26.1.2 原版主世界模型预测**」，绝不能写成「支持所有 26.1.2 服务器」。本阶段已把这句话写进 UI（第三十四节）。

---

## 十一、DimensionType 来源（报告项 10）

- 现有实现：**没有**直接取 `DimensionType`，而是走 `WorldGenRegion` 的继承行为（父类构造器缓存 `level.dimensionType()`，`getMinY()` / `getHeight()` 转发给它）。
- 客户端可得：`ClientLevel#dimensionType()`（`Level#dimensionType()`）—— 与服务器一致的原版维度类型；`DimensionType#minY()` / `#height()` / `#logicalHeight()` 均为 public（`javap` 已核）。
- **本阶段未改**。

---

## 十二、NoiseGeneratorSettings 来源（报告项 11）

- 现有实现：`registries.lookupOrThrow(Registries.NOISE_SETTINGS).getOrThrow(NoiseGeneratorSettings.OVERWORLD)` —— **只从注册表取**，不碰 level 的任何数据；`RandomState.create(registries, NOISE_SETTINGS.OVERWORLD, seed)` 同理。
- 客户端可得性：`NOISE_SETTINGS` 属于 worldgen 注册表；客户端有本地 Vanilla 副本，是否由服务器同步**未逐项核对**（见第十节）。
- **本阶段未改**。

---

## 十三、StructureTemplateManager 解决方案（报告项 12）

口径第十四节把这一项列为重点检查项。核实结果：

```
public StructureTemplateManager(ResourceManager, LevelStorageSource$LevelStorageAccess,
                                DataFixer, HolderGetter<Block>)
构造器字节码偏移 37：invokevirtual LevelStorageAccess.getLevelPath(LevelResource) → Path
```

| 构造参数 | 客户端可得性 |
| --- | --- |
| `ResourceManager` | ✅ `Minecraft#getResourceManager()` |
| `DataFixer` | ✅ `DataFixers.getDataFixer()`（静态） |
| `HolderGetter<Block>` | ✅ `registries.lookupOrThrow(Registries.BLOCK)` |
| **`LevelStorageAccess`** | ❌ **客户端没有任何来源**；且构造器内立即调用 `getLevelPath(...)` ⇒ 传 `null` 直接 NPE |

**结论**：客户端**造不出** `StructureTemplateManager`，而它是 `createStructures(...)`（STRUCTURE_STARTS 阶段）的必需入参之一。这与 `WorldGenRegion` 是**两个独立的阻塞点**，即使解决了后者，前者仍然挡着。

**为什么不能跳过**（口径第十四节明确禁止「因为麻烦直接跳过结构阶段」）：结构起点直接影响 `startsForStructure` → 影响挂在结构上的地物放置，进而影响目标区块的方块状态。223~229 的精确链正是在**完整七阶段**下取得的，跳过就等于换了一套算法。

**现有实现为什么不受影响**：单人世界里 `host.getStructureManager()` 就是集成服务端自己的那一个，实例现成、`LevelStorageAccess` 现成。

---

## 十四、ResourceManager 来源（报告项 13）

- 客户端可得：`Minecraft#getResourceManager()`（`javap` 已核）。
- 现有正式实现**没有**直接使用 `ResourceManager`：`StructureTemplateManager` 是从宿主 `ServerLevel` 拿的（第二十节），因此本模块**没有引入任何对资源管理器的常驻引用**。
- 这带来一个副作用值得登记：**本阶段未引入新的资源 reload 风险**（见第三十七节第 4 项）。

---

## 十五、高度 / minY 来源（报告项 14）

- 现有实现：`new ProtoChunk(pos, UpgradeData.EMPTY, ctx.host(), ctx.containerFactory(), null)` —— 第三参数的类型是 `LevelHeightAccessor`（`javap` 已核 `ProtoChunk` 两个构造器签名），**实际只用到高度信息**。
- 客户端可得：`DimensionType#minY()` + `#height()` → `LevelHeightAccessor.create(minY, height)`（静态，已核）。
- 结论：这是**最容易解耦、但收益也最小**的一项（它不构成阻塞）。
- **本阶段未改**（避免无收益改动）。

---

## 十六、是否使用真实 Chunk（报告项 15、31、32）

**否。** 证据：

1. 全部区块对象来自 `OfflineChunkCache`（自建 `OfflineChunkHolder extends GenerationChunkHolder`，读方法全部覆盖，见 [OfflineChunkHolder.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/worldgen/OfflineChunkHolder.java#L60-L83)）。
2. 观察窗口 `StaticCache2D.create(centerX, centerZ, radius, this::holder)` 由离线缓存自己建，`WorldGenRegion#getChunk` 只可能落到这份窗口。
3. 唯一可能外泄的口子 `getChunkSource()` 已计数，复跑六用例增量恒 **0**。
4. 第 32 节（「未加载远端 Chunk 是否可预测」）在单人环境下**无法严格实测**（集成服务端会加载玩家附近区块）；本阶段用的是 `hostChunkSourceQueries == 0` 这一**更强**的静态+运行时证据：即便区块存在，离线链路也一次都没去取。

---

## 十七、是否调用 ClientLevel#getBlockState（报告项 16）

**没有。** 静态搜索结果（`rg`，范围 `src/main/java/com/yiyiaddon/seed`）：

```
ClientLevel          → 0 命中
ClientChunkCache     → 0 命中
getBlockState        → 3 命中，全部是「读离线区块里的 LevelChunkSection」：
   OreChunkReader.java:65              levelSection.getBlockState(localX, localY, localZ)
   PredictionSession.java:325          levelSection.getBlockState(localX, localY, localZ)
   （第三处为 OfflineChunkRegion 的父类覆盖注释）
```

即：本模块对 `getBlockState` 的全部调用都发生在**自己生成的离线区块**上，且 `seed` 包内**完全不出现** `ClientLevel` / `ClientChunkCache` 这两个类型。

---

## 十八、正式 seed 包 ServerLevel 搜索结果（报告项 17）

搜索命令口径：`rg -n "ServerLevel" src/main/java/com/yiyiaddon/seed`，命中 **16 行 / 6 文件**：

```
OfflineChunkRegion.java:7      import net.minecraft.server.level.ServerLevel;
OfflineWorldgenContext.java:6  import net.minecraft.server.level.ServerLevel;
OfflineWorldgenContext.java:49 private final ServerLevel host;
OfflineWorldgenContext.java:78 private OfflineWorldgenContext(ServerLevel host, long seed, ...)
OfflineWorldgenContext.java:101 public static OfflineWorldgenContext create(ServerLevel host, long seed)
OfflineWorldgenContext.java:123 public ServerLevel host()
DiamondSeedPredictor.java:8    import ...
DiamondSeedPredictor.java:29   注释
DiamondSeedPredictor.java:61   private final ServerLevel host;
DiamondSeedPredictor.java:69   public DiamondSeedPredictor(ServerLevel host)
PredictionSession.java:18      import ...
PredictionSession.java:101     public static PredictionSession open(ServerLevel host, long seed, ResourceKey<Level> dimension)
SeedMiningService.java:20      import ...
SeedMiningService.java:56      注释
SeedMiningService.java:394     ServerLevel host = server == null ? null : server.overworld();
SeedMiningRuntimeState.java:32 注释（本阶段已改写为类型契约说明）
```

**判定**：`worldgen` 核心与 `prediction` 核心**仍然强依赖 `ServerLevel`**，本阶段**未能消除**（原因见第六节）。

---

## 十九、正式 seed 包 getSingleplayerServer 搜索结果（报告项 18）

搜索口径：`rg -n "getSingleplayerServer" src/main/java/com/yiyiaddon/seed`，命中 **3 行 / 1 文件**：

```
SeedMiningService.java:393   IntegratedServer server = client.getSingleplayerServer();   ← 取宿主
SeedMiningService.java:512   hostAvailable = client.getSingleplayerServer() != null;     ← 进世界时判可用
SeedMiningService.java:535   hostAvailable = client.getSingleplayerServer() != null;     ← 世界变化时判可用
```

**判定**：`SeedMiningService` **仍然依赖** `getSingleplayerServer()` 才能进入 `READY`（口径第五十七节验收第 2 条**未满足**）。

---

## 二十~二十五、Parity 相关（报告项 19~25）：未实施

口径第十八~二十五节要求的「Old Server-backed vs Client-only」A/B、固定回归集、逐 `PredictedOre` certainty 对比、stage-level parity、结构非空区域 parity —— **全部未实施**。

原因：**A/B 的 B 侧不存在**（Client-only 环境无法构造）。在没有 B 侧的情况下，「比较 A 与 B」这件事本身不成立。

**作为替代证据**，本阶段复跑了 230 的服务层回归装置（同一台机器、同一装置、未改动装置代码），确认本阶段的三处小改**没有让 229/230 的任何已冻结数字退化**：

| 用例 | 候选 | 调度敏感 | 未解析 | 确定性 | 耗时 | 缓存区块 | 宿主 ChunkMap 查询 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Seed 20260922 `(0,0)` | **45** | 0 | 45 | 0 | 2757 ms | 529 | **0** |
| Seed 2 `(-400,380)` | **23** | **1** | 22 | 0 | 1758 ms | 529 | **0** |
| Seed 12345 `(0,0)` | **31** | 0 | 31 | 0 | 1559 ms | 529 | **0** |
| Seed 12345 `(-1,-1)` | **29** | **7** | 22 | 0 | 307 ms | 574 | **0** |
| Seed 12345 `(-25,17)` | **24** | 0 | 24 | 0 | 1054 ms | 1103 | **0** |
| Seed 12345 `(120,-130)` | **26** | 0 | 26 | 0 | 1310 ms | 1632 | **0** |

- 争议位置 `(-6385,-59,6085)`：仍是 **`SCHEDULE_SENSITIVE`**（通过）。
- 服务层结果 vs 直连正式 Predictor：45 / 45，双向差集均为 0，**逐 BlockPos 完全一致**。
- 客户端线程提交耗时：59 / 80 / 143 / 147 / 246 / 803 微秒（世界生成不在渲染线程）。

（口径第十九节要求的 Seed 20260922「原 10 target」在本阶段没有整体复跑 —— 装置当前固定集是六用例。这是 231 相对 229 的**证据覆盖差异**，如实登记；229 的 `243/243` 结论未被本轮否定，也未被本轮重新证明。）

---

## 二十六、强制 Client-only 单人测试（报告项 26）：未实施

口径第二十三节要求的「即使运行在单人世界，也强制正式 Service 使用 Client-only WorldgenEnvironment」—— **未实施**，因为不存在 Client-only 环境可强制使用。

---

## 二十七~三十三、Dedicated Multiplayer 测试（报告项 27~33）：未实施

口径第二十八~三十三节要求：搭 26.1.2 Dedicated Server、客户端连接、断言 `getSingleplayerServer() == null`、确认状态仍能 `READY`、完成一次预测并与本地一致、验证未加载远端 Chunk 也能预测。

**全部未实施**，理由：

1. **前提在类型层面被证伪**（第六节）：连接成功后必然 `getSingleplayerServer() == null` → 必然 `WORLDGEN_ENVIRONMENT_UNAVAILABLE` → 必然无法预测。这不需要实测就能确定。
2. **搭环境成本高且不产生新信息**：本仓库没有 26.1.2 专用服务端的运行配置（`build.gradle` 只有四个 `client()` 运行任务），搭建需要额外下载服务端与安装 Fabric，且专用服务端的 forked 进程还要与开发客户端联动，属于一次性大投入。
3. **口径第二十八节本身写了「如果本地开发环境允许」**，条件不满足。

**如实说明**：本阶段**没有**任何一次真实多人服务器实测。多人端的结论完全建立在第六节的类型证明上；这也正是本阶段判定为「阻塞」而不是「已验证支持」的原因。

---

## 三十四、UI 文案调整（报告项 33、34）

按口径第十二、二十六、三十四节，种子挖矿页的环境区新增了两项：

```
── 环境 ──────────────
当前维度            主世界
支持状态            支持：钻石预测
预测模型            Minecraft 26.1.2 原版主世界          ← 本阶段新增
预测环境            可用 / 当前世界生成环境不可用 / …      ← 文案随状态枚举改名
（小字）预测模型只声明「按哪套规则算」：尚未验证服务器是否使用自定义世界生成。
        服务器若使用数据包 / 插件生成器 / 改 worldgen 的 Mod，本预测不保证成立。
```

- 「预测模型」文案的唯一来源是服务层的 `SeedMiningService#predictModelCn()` / `#predictModelNoteCn()`（同源唯一，不违反铁律第 169 条）。
- 口径第二十六节要求的「多人服务器成功时不要再显示『当前世界没有可用的世界生成环境』」——**当前仍会显示**（因为多人确实不可用），但文案已换成更准确的「当前世界生成环境不可用」。
- 口径第十二节禁止的「服务器世界生成已验证」**没有出现**；「Seed 已验证」也仍然**没有出现**（种子页只有未填写 / 格式无效 / 已填写）。

---

## 三十五、NO_WORLD_HOST 是否更名（报告项 34）

**已更名**（口径第二十四节建议 A 方案）：

| 项 | 改前（230） | 改后（231） |
| --- | --- | --- |
| 枚举常量 | `NO_WORLD_HOST` | `WORLDGEN_ENVIRONMENT_UNAVAILABLE` |
| 中文文案 | 当前世界没有可用的世界生成环境 | **当前世界生成环境不可用** |
| 语义注释 | 「正式预测器要一个 ServerLevel 当环境宿主」 | 补入 26.1.2 类型契约（`WorldGenLevel extends ServerLevelAccessor` → `ServerLevel getLevel()`）与「因此本状态在多人服务器上必然成立」 |

改名后的状态机语义与判据顺序**未变**（仍为：关闭 → 没进世界 → 环境不可用 → 没填种子 → 种子非法 → 维度不支持 → 预测中 → 有结果 → 就绪）。

**回归**：服务层回归装置复跑全部通过，装置本身未改动（该状态在单人世界里不会出现，因此装置不会覆盖它；其可达性由类型证明给出，不由运行覆盖）。

口径第二十五节提到的「再新增 `WORLDGEN_MODEL_UNSUPPORTED`」——**未新增**：本阶段的结论是「环境不可用」，而不是「模型不受支持」（模型只支持主世界钻石这件事已由 `UNSUPPORTED_DIMENSION` + UI「支持状态」表达）。按口径第二十五节「不要无意义堆状态」，维持 10 态不变。

---

## 三十六、Environment 生命周期 / 世界切换 / 服务器切换（报告项 35、36、37）

- **Environment 生命周期**：未实施（无 Environment）。现有的 Predictor 生命周期**一行未改**：换维度即释放预测器（会话键不含服务器身份，宿主属于某一次连接）、退出世界取消任务并释放预测器与会话缓存、配置保留。
- **世界切换 / 服务器切换**：逻辑未改；本轮复跑验证：主世界 → 下界 → 主世界（下界状态「当前维度暂不支持」、旧结果已清、禁止预测；回主世界「已就绪」且旧结果不复活）；退出世界（状态回「等待进入世界」、结果已清、预测器已释放、配置保留 `启用=true / 种子=12345`）。
- 依据：报告 230 第十一、十五、十六、十七节，本轮为**复跑确认**，不是新实现。

---

## 三十七、资源 reload（报告项 38）

口径第三十八节要求检查「资源包 reload 是否会让 `StructureTemplateManager` / `ResourceManager` 相关句柄需要重建」。核实与结论：

1. `StructureTemplateManager` **有** `public void onResourceManagerReload(ResourceManager)`（`javap` 已核），说明原版把「资源热重载」当作它需要响应的事件。
2. 现有正式实现**不自己创建** `StructureTemplateManager`，它来自宿主 `ServerLevel`（单人世界里由集成服务端持有）⇒ reload 的失效与重建由**原版服务端自己**处理，本模块不需要额外接线。
3. 现有正式实现**不持有** `ResourceManager` 引用 ⇒ 本模块没有「reload 后句柄过期」的问题。
4. **本阶段没有引入任何新的常驻资源句柄**（因为环境解耦未实施），因此也没有引入新的 reload 风险。

⇒ 结论：**当前无需接线**；一旦将来真的引入自建 `StructureTemplateManager`，就必须按本条重新评估（登记为遗留）。

---

## 三十八、线程模型（报告项 39）

**未改**（沿用 230）：

| 工作 | 线程 |
| --- | --- |
| 读 `Minecraft#level` / `player` / `getSingleplayerServer()`、取 `BlockPos → ChunkPos`、提交任务、回投结果 | 客户端主线程 |
| 构造 `DiamondSeedPredictor`、`predict(...)`（含离线 worldgen 全链）、`invalidate` / `close` | 后台单线程守护 Executor `yiyiaddon-seed-predict` |
| 结果落地 | `Minecraft#execute` 回投主线程，校验任务代号 `generation` |

**未把 `Minecraft` 传进 worldgen core**（口径第四十节）：`OfflineWorldgenContext` 不持有 `Minecraft`，`DiamondSeedPredictor` / `PredictionSession` 也不引用任何客户端单例；`Minecraft` 只出现在 `SeedMiningService`（边界）与 `MiningSeedPage`（界面）。

---

## 三十九、性能 / 缓存 / 内存（报告项 40、41、42、43）

**本轮复跑实测（同一台机器，与 230 同口径）：**

| 指标 | 数值 |
| --- | --- |
| 客户端线程提交耗时 | 59 ~ 803 微秒（六用例） |
| 冷启动预测（Seed 20260922 `(0,0)`，19x19 前置铺开） | 2757 ms（230 同用例为 2539 ms，差 8.6%，属机器负载波动） |
| 第二个相邻目标（Seed 2 `(-400,380)`） | 1758 ms（230 为 1820 ms） |
| 同种子相邻/远端目标（Seed 12345 四例） | 1559 / 307 / 1054 / 1310 ms（230 为 1756 / 274 / 1040 / 1121 ms） |
| 会话持有离线区块 | 529 / 529 / 529 / 574 / 1103 / 1632 |
| 宿主 ChunkMap 查询增量 | 全部 0 |

- **与 230 对比结论**：无「无原因慢一个数量级」的情况（口径第四十二节要求），全部在 ±10% 量级内波动，即本阶段的三处小改没有触碰热路径。
- **缓存**：未改（`OfflineChunkCache` + `SessionKey(版本, 种子, 维度)`）。
- **内存**：口径第四十二、四十三节建议本轮补上粗略内存值。**未做** —— 原因：本阶段没有引入任何新的常驻对象（环境解耦未实施），内存曲线与 230 完全同构；为此专门做一次堆采样属于无收益投入。如实登记为未完成项。

---

## 四十、SeedMiningService 是否继续膨胀（报告项 44）

- 行数：573 → **593 行**（+20，全部是 `predictModelCn()` / `predictModelNoteCn()` 两个方法与其注释）。
- **没有**继续塞入 Registry / StructureTemplateManager / NoiseSettings / ResourceManager 任何一项（口径第八、四十三节）。
- 未做任何拆分：口径第四十三节的标准是「职责是否清晰」而不是行数；服务层职责依旧是「生命周期 / 配置 / 异步任务 / 当前维度 / 预测器 / 结果」，本阶段没有破坏它。

---

## 四十一、配置是否保持服务器隔离（报告项 45）

**保持，未改动一行。** `SeedMiningService` 仍以 `WorldIdentity.fileSafeServer()` 作为配置作用域，`RECORD_ID = "seed_mining"` 仍落在现有 `module-state.json`；本轮复跑的第 8 章证据：退出世界后「配置保留：启用=true，种子原文=12345」。跨服务器隔离逻辑（A → 退出 → B，退出 A 时清空运行时）未受影响。

---

## 四十二、230 Seed 输入矩阵回归（报告项 46）

**复跑，10 项全部保持：**

| 输入 | 解析 | 输入状态 | 运行时状态 |
| --- | --- | --- | --- |
| （空串） | null | 未填写 | 等待填写服务器种子 |
| `0` | 0 | 已填写 | 已就绪 |
| `12345` | 12345 | 已填写 | 已就绪 |
| `-7777` | -7777 | 已填写 | 已就绪 |
| `20260922` | 20260922 | 已填写 | 已就绪 |
| `9223372036854775807` | 同值 | 已填写 | 已就绪 |
| `-9223372036854775808` | 同值 | 已填写 | 已就绪 |
| `abc` / `12.3` / `--` | null | 格式无效 | 种子格式无效 |

非法种子下**禁止预测**（复跑判定：正确）。UI 仍**没有**「种子已验证」。

---

## 四十三、AutoMiner 是否零行为变化（报告项 47）

**是，零变化。** 本阶段没有碰 `feature/mining` 下任何逻辑：`MiningSettings` / AutoMiner / Baritone 接入 / 精准采集 / 时运 / 食物 / 回家 / 白名单 / 背包 全部未改；`AutoMinerPage` 的文案也未改（沿用 230 的版本）。种子页新增的只是一行数据行与一行小字。

---

## 四十四、是否仍未做 Observation / Renderer / Baritone / 其它矿物（报告项 48、49、50）

- **Observation**：未做（无 `CONFIRMED` / `MISSING` / `SUSPICIOUS` 产出，无假矿判定，UI 无假矿数量）。
- **Renderer**：未做（无 ESP、无世界坐标标签、无方框 / 射线 / 颜色 / 脉冲）。口径第五十九节的下一阶段目标仍是「先多人能跑通再渲染」，而本阶段证明多人跑不通 ⇒ 渲染更不能先行。
- **Baritone / AutoMiner 接入**：未做。`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController` 未改，Seed targets 未交给 Baritone。
- **其它矿物**：未做（仍只有 Overworld Diamond）。

---

## 四十五、compileJava / build / runClient（报告项 51、52、53）

```
.\gradlew.bat compileJava --console=plain -q     → EXIT=0
.\gradlew.bat build        --console=plain -q     → EXIT=0
.\gradlew.bat runClient                            → 正常启动
```

`runClient`（用户日常 100+ Mod 环境）实测日志：

```
[11:58:47] (yiyiaddon) yiyiaddon initialised (baritone localisation: on)
[11:58:48] (yiyiaddon/seed) 种子挖矿：运行时服务已挂载（后台单线程预测；配置项 启用/种子）
[11:58:56] (Minecraft) Sound engine started
```

`ERROR] (yiyiaddon…)` / `Fatal` / `crash` 命中数：**0**。

服务层回归装置复跑（`runClientSeedPredictTest`，独立运行目录 `run-26.1.2-seed-predict-test/`）：

```
[11:58:16] 种子挖矿PoC｜全部判定：通过
[11:58:16] 种子挖矿PoC｜报告已落盘：…\seedpoc-服务层回归.txt
BUILD SUCCESSFUL in 30s
```

证据文件（运行目录，不进版本库）：

- `seedpoc-服务层回归.txt` —— 本阶段复跑结果（页高 **804** 像素，230 为 750，差值 = 新增一行 + 一行小字 + 间距）
- `seedpoc-服务层回归-230归档.txt` —— 复跑前对 230 原证据的归档（保证 230 报告引用的那份内容可追溯）

**必须如实标注的边界**：全部实机验证都在**单人集成服务端**环境完成。**没有任何一次多人服务器实机验证**（原因见第二十七节）。

---

## 四十六、所有改动文件（报告项 54）

本阶段改动 **3 个文件、共约 +45 行**（无新增文件、无删除文件）：

| 文件 | 改动 |
| --- | --- |
| [SeedMiningRuntimeState.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningRuntimeState.java) | `NO_WORLD_HOST("当前世界没有可用的世界生成环境")` → `WORLDGEN_ENVIRONMENT_UNAVAILABLE("当前世界生成环境不可用")`；补入类型契约注释；类注释状态序同步用词 |
| [SeedMiningService.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningService.java) | 同步枚举引用 1 处；`hostAvailable` 字段注释补「为什么它等价于『本世界有没有集成服务端』」；新增 `predictModelCn()` / `predictModelNoteCn()` |
| [MiningSeedPage.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/console/MiningSeedPage.java) | 环境区新增「预测模型」数据行 + 适用范围小字；`hostStateCn()` 同步新枚举常量与新文案 |

**未改动**：`seed/model`、`seed/observation`、`seed/prediction`（含 `DiamondSeedPredictor` / `PredictionSession` / `ScheduleSensitivityAnalyzer`）、`seed/worldgen`（含 `OfflineWorldgenContext` / `OfflineChunkPipeline` / `OfflineChunkRegion` / `OfflineChunkCache` / `OfflineChunkHolder` / `OreChunkReader`）、`seed/config`、`dev/seedpoc`（含回归装置）、AutoMiner 全部、`build.gradle`。

---

## 四十七、遗留边界（报告项 55）

1. **【核心】多人服务器不可预测**：`WorldGenLevel extends ServerLevelAccessor` → `ServerLevel getLevel()`，而 `ServerLevel` 只能由 `MinecraftServer` + 真实存档目录创建。原版 26.1.2 未提供任何绕过途径。这是**类型层面的阻塞**，不是实现缺陷。
2. **第二个阻塞点：`StructureTemplateManager` 需要 `LevelStorageAccess`**，且构造器内立即解引用 ⇒ 客户端连空壳都造不出。即使将来解决了 `WorldGenRegion`，这一项仍然挡着。
3. **`StructureCheck` 同样是服务端资源**：`StructureManager` 的第三个构造参数需要 `ChunkScanAccess`（区块存储扫描），本阶段未深入评估其客户端可得性（因为前两条已足够定论），登记为「若将来重启本方向，需一并评估」。
4. **worldgen 注册表来源未逐项核对**（第十节）：`RegistryDataLoader` 的三个清单成员未逐项验证。
5. **Client-only vs Server-backed 的 parity 从未做过**：因为没有 B 侧。229 的 `243/243` 与 230 的六用例基线是**唯一**的可信数字来源，本阶段只复跑了后者。
6. **229 的 Seed 20260922 十目标整体回归本轮未复跑**（装置固定集为六用例）。
7. **内存量化未做**（第三十九节）。
8. **`NO_WORLD_HOST` 更名后的多人可达性未经运行验证**：该状态在单人世界里不会出现，其可达性由类型证明给出。
9. **`generateStructures` 的客户端替代来源未落地**：客户端没有 `WorldOptions`，需要「环境声明」这种新概念（未实施）。
10. **本阶段没有产出任何多人环境下的实机证据**（第二十七节）。

---

## 四十八、下一阶段建议（报告项 56）—— 需要用户决策

口径第五十九节的下一步（`232` 观察 + 渲染）**前置条件未满足**，因此本阶段不建议直接进入。四条可选路线，请用户裁定：

**路线 A：接受边界，进入渲染阶段（仅在单人世界可用）**
- 做法：按原计划做 `232`（实际 Chunk 观察 + 预测矿物世界渲染），明确标注「仅在单人世界可用」。
- 代价：多人目标继续搁置；渲染层在多人环境下无输入。
- 收益：单人场景立刻可用，且观察层（`CONFIRMED` / `MISSING`）第一次有真实世界对证。

**路线 B：把多人预测移到服务端 / 代理侧（本项目已有 `03-后端API/`）**
- 做法：由服务端（或代理）持有真实 `ServerLevel` 跑正式 Predictor，把 `PredictionResult` 下发给客户端渲染。
- 代价：需要服务端插件 / 代理组件；客户端模组单独无法完成。
- 收益：**唯一能同时满足「多人可用」与「不伪造世界、不重写 worldgen」的方案**。

**路线 C：放宽口径第十三节（用本地同种子世界当宿主）**
- 做法：在客户端内起一个同种子、同版本、原版 worldgen 的本地世界，把它的 `ServerLevel` 当宿主。
- 代价：违反口径第三节第 1、2 条与第十三节；且环境数据（注册表、worldgen 配置）来自**本地**而非服务器 ⇒ 对装数据包 / 插件生成器 / 改 worldgen 的服务器会算错，且客户端无从察觉。
- 收益：能立刻在多人服务器上「跑出数字」。
- **本阶段不建议选它**：它产出的正是口径第十二、三十四节明令禁止的那种「看起来支持、实际不保证」的结论。

**路线 D：自研 worldgen 复刻（自己实现 WorldGenRegion 的等价语义 + worldgen 驱动）**
- 代价：违反口径第三节第 6 条；等于把原版七阶段驱动重写一遍，223~229 的精确链全部要重新证明；工程量与风险最大。
- 收益：理论上可完全脱离 `ServerLevel`。

**建议**：若坚持「多人服务器种子挖矿」这一产品目标，**路线 B** 是唯一不自欺的路径；若优先级是「尽快看到矿物」，则**路线 A**（并明确标注单人可用），把多人能力交给 B 或 D 另行立项。

**本阶段到此停止**：按口径第五十九、六十节，不自行进入观察层、假矿识别、矿物渲染、AutoMiner 接入，等待用户确认路线后再开工。

---

## 四十九、验收门槛逐条对照（口径第五十七节）

| # | 门槛 | 结果 |
| --- | --- | --- |
| 1 | 正式 Predictor 不再要求 `ServerLevel` | ❌ **未满足**（类型契约阻塞，第六节） |
| 2 | `SeedMiningService` 不再依赖 `getSingleplayerServer()` 才能 `READY` | ❌ **未满足**（第 19 节：仍有 3 处） |
| 3 | Client-only Environment 在单人强制路径可运行 | ❌ 未实施（无 B 侧） |
| 4 | Client-only vs Old Server-backed 固定回归逐 BlockPos 一致 | ❌ 未实施（无 B 侧） |
| 5 | `PredictionCertainty` 一致 | ❌ 未实施（无 B 侧） |
| 6 | Seed 20260922 回归不退化 | ✅ **满足**（45 / 0 / 45 / 0） |
| 7 | Seed 12345 回归不退化 | ✅ **满足**（31 / 29(7) / 24 / 26） |
| 8 | Seed 2 冲突格仍正确 | ✅ **满足**（23 / 1 敏感 / 22 未解析 / 0 确定，`(-6385,-59,6085)` 仍 `SCHEDULE_SENSITIVE`） |
| 9 | 正式 Predictor 不读取真实 Chunk | ✅ **满足**（宿主 ChunkMap 查询增量恒 0；`ClientLevel` / `ClientChunkCache` 零命中） |
| 10 | Multiplayer 环境失败时 fail-closed | ✅ **满足**（构造不出宿主即 `WORLDGEN_ENVIRONMENT_UNAVAILABLE`，不预测、不返回空矿集合） |
| 11 | 生命周期仍正确 | ✅ **满足**（复跑：下界 / 回主世界 / 退出世界全部通过） |
| 12 | UI 不伪称 Seed / worldgen 已验证 | ✅ **满足**（种子状态三态；新增「尚未验证服务器是否使用自定义世界生成」小字） |
| 13 | `compileJava` 成功 | ✅ **满足**（EXIT=0） |
| 14 | `build` 成功 | ✅ **满足**（EXIT=0） |
| 15 | 正常 `runClient` 成功 | ✅ **满足**（initialised + 服务挂载 + Sound engine started，零 ERROR） |
| 16 | 真实 Dedicated 连接下 `getSingleplayerServer() == null` | ⛔ 未实测（类型层面必然成立，第二十七节） |
| 17 | 该环境下 Seed Mining 能 `READY` | ⛔ 未实测（**类型层面已证伪**：必然不可用） |
| 18 | 能完成一次正式预测 | ⛔ 未实测（同上） |
| 19 | 结果与同参数本地 Predictor 一致 | ⛔ 未实测（同上） |

**判定：第 1~5 条未满足，第 16~19 条被类型证明证伪 ⇒ 第三阶段「多人服务器离线预测环境解耦」未通过（架构阻塞）。**
第 6~15 条全部满足：本阶段**没有**让任何既有能力退化，UI 与配置保持诚实。
