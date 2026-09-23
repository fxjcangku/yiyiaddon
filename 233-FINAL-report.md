# 233-追加 · 种子挖矿正式化第五阶段报告
## Prediction Coverage + Actual Chunk Observation + 钻石世界渲染

- **项目**：`D:/mcaddon/yiyiaddon`（分支 `master`）
- **环境**：Minecraft 26.1.2 · Fabric Loader 0.19.5 · Fabric API 0.155.2+26.1.2 · JDK 25（`--release 21`）· Fabric Loom 1.17.20
- **产品版本**：`yiyiaddon 1.0-beta2`
- **基线**：`232-FINAL-report.md`（唯一优先基线）→ `seedmining-local-server-report.md`（性能 / 行为基线）
- **验收产物**：`build/release/yiyiaddon-1.0-beta2-26.1.2.jar`（47,052,266 字节）
  - **SHA-256 = `804ed04521299ae035f2002ac5ec2b0c832984e887384f3f9faac621f04e449e`**
  - 对外 ZIP `build/release/yiyiaddon-1.0-beta2-26.1.2.zip`（46,749,756 字节）
    **SHA-256 = `2724B1C9699ABD19C5E909999AD25A959874BFBF4D5433EF649F40FC39104EB2`**
  - 混淆映射已备份：`05-混淆文件备份/1.0-beta2/02-字符串与控制流加强版-26.1.2-804ed045`
- **证据目录**：`02-开发报告/项目开发报告/02-阶段施工与落盘/233-证据/`
- **结论**：**233 全部口径落地并通过验证；冻结数字一个未变；发布流水线全绿；生产产物在真实环境（含 103 个第三方 Mod）实机通过。本阶段完成后立即停止，不进入 234。**

---

## 一、结论摘要

233 要做的四件事，全部做完并拿到实机证据：

| # | 目标 | 落地物 | 证据 |
| --- | --- | --- | --- |
| 1 | 玩家附近**自动**预测覆盖 | `SeedPredictionCoverageController`（默认半径 3 = 49 目标，近→远，单执行位 + 有限排队） | `C1`：49/49 铺满、`O1`：玩家区块 45 候选全到齐 |
| 2 | 预测结果**有界仓储** | `SeedPredictionRepository`（上限 256 目标区块、身份绑定、淘汰策略、五条失效入口） | `O1`：换服 / 换维度 / 改种子 / 关显示 后缓存全部归零 |
| 3 | 观察**客户端已加载的真实 Chunk** | `SeedOreObservationTracker`（唯一读世界处，`loadOrGenerate=false`，O(候选数)） | `O1`：CONFIRMED / MISSING / 恢复 / 卸载→未观察 / 重新加载 五条 |
| 4 | 世界里**显示预测钻石** | `SeedOreWorldRenderer` + `SeedRenderSnapshot`（只读不可变快照，青/绿/灰 + 琥珀内圈） | `P3`/`P4`：正式产物（含 Sodium/Iris/103 Mod）实机跑通整条渲染链 |

四个目标之外的硬要求同样满足：

- **Prediction 与 Observation 严格两维独立**（`PredictionCertainty` × `OreObservationState`），
  调度敏感被观察之后仍是调度敏感（`C1` 第二段实测），且**全程零 SUSPICIOUS**（三个装置都在断这条）。
- **P0 红线**：观察层从不主动加载 / 生成区块 —— `SeedOreObservationTracker` 里只有一个常量
  `LOAD_OR_GENERATE = false`，全类只有一处 `getChunk` 调用点。
- **冻结数字一个未变**（第十一节对照表）。
- **发布链 + 生产实机**：`buildRelease` → `verifyObfuscatedJar` → 3 次生产冒烟（其中一次带 103 个真实 Mod）全通过，
  退出后零残留 Worker 进程。

---

## 二、本阶段红线与禁止项（逐条自查）

| 口径 | 要求 | 本阶段实际 |
| --- | --- | --- |
| 232 冻结类 | 禁止修改 `DiamondSeedPredictor` / `PredictionSession` / `ScheduleSensitivityAnalyzer` / `OfflineWorldgenContext` / `OfflineChunkPipeline` / `OfflineChunkRegion` / `OfflineChunkHolder` / `OfflineChunkCache`、`SeedWorkerMain` / `SeedWorkerHost` / `SeedWorkerIpcServer` / `SeedWorldgenWorkerLauncher` | **一行未改**（这些文件的最后修改时间都停在 232 窗口，本次窗口内没有任何写入） |
| 冻结数字 | 20260922 (0,0)=45、十目标合计 243；12345 = 31/29(7)/24/26、合计 110；Seed 2 (-400,380)=23/1/22/0；争议位置 (-6385,-59,6085) 保持 SCHEDULE_SENSITIVE；Host ChunkMap Query 恒 0 | **全部保持**（第十一节） |
| Prediction 与 Observation 分开 | 两维独立枚举，禁止合并成一个状态机 | `PredictionCertainty`（DETERMINISTIC / SCHEDULE_SENSITIVE / UNRESOLVED）与 `OreObservationState`（UNOBSERVED / CONFIRMED / MISSING / SUSPICIOUS）各自独立存在，渲染层用「底色 + 内圈琥珀」表达两个维度而不是混成一个颜色 |
| 禁止自动产生 SUSPICIOUS | 233 一行代码都不得写出 `SUSPICIOUS` | 全模块 `grep SUSPICIOUS` 只命中**文档字符串**与「归类兜底」分支（`SeedRenderSnapshot` 把未知状态按未观察计数，绝不当成有效钻石）；三个实机装置都在断言「可疑 = 0」 |
| SCHEDULE_SENSITIVE ≠ 假矿 | 不得因为观察不到就改写成可疑 | `C1` 第二段实测：争议位置观察后确定性仍是「调度敏感」，观察状态照实给「缺失」 |
| P0 红线 | Observation 绝不主动加载 Chunk（禁止 `getChunk(..., create=true)`） | `LOAD_OR_GENERATE = false` 单一常量 + 全类唯一 `getChunk` 调用点（第六节） |
| 复杂度 | 只检查候选 BlockPos，禁止 O(整区块) 重扫 | 方块更新只在「是本区块候选」时读一次方块；非候选 O(1) 查表丢弃（第八节） |
| 客户端主线程 | 读 `ClientLevel` 必须在主线程；Renderer 只读不可变快照 | 观察与快照重建都在 `SeedMiningService.onTick()`（客户端主线程）；`render(EspRenderer)` 只遍历快照里的 `BlockPos` |
| 模块边界 | `seed/observation/`、`seed/runtime/`、`seed/render/` 三包，复用已有模型，禁止第二套 PredictionResult / PredictedOre / ObservationState | 三包各就各位；`PredictionResult` / `PredictedOre` / `PredictionCertainty` / `OreObservationState` / `OreObservation` / `OreType` 全部**复用** 229 阶段建立的模型，没有第二套 |
| UI 限制 | 只允许新增种子预测渲染器设置；不显示「可疑矿 / 假矿 / 种子已验证」 | 新增仅三项：显示预测钻石（开关）、预测范围（1~6，默认 3）、显示当前缺失（开关，默认关）；界面文案全量核对见第九节 |
| 禁止项 | 不接 AutoMiner、不接 SeedValidation、不做其它矿物、不上 26.2、不重写 Predictor / Worker / 发布流水线 | 全部未触碰；`AutoMinerPage.java` 最后修改时间 `2026-09-23 11:21`（232 窗口），233 窗口内零写入 |

---

## 三、架构：三个新包与既有层怎么接上的

```
客户端主线程（每刻一次，全部 O(1) 判断）
  SeedMiningService.onTick()
    ├─ syncRuntime()            身份与开关的收敛点（六个条件）
    ├─ driveCoverage()          覆盖调度 → 提交给后台单线程 Worker
    ├─ observer.revision() 变了？ → renderer.markDirty()
    └─ renderer.dirty()？         → SeedRenderSnapshot.build(repository, observer)
                                     → renderer.updateSnapshot(快照)

后台单线程（本地世界生成计算器，独立 JVM）
    Coverage 目标 → Worker PREDICT_DIAMOND → 回写（复核身份代号）→ repository.put()

渲染线程（每帧）
    WorldOverlay → SeedOreWorldRenderer.render(EspRenderer)
       只读不可变快照 → 画 camera-relative 方框（不碰缓存、不碰世界、不调 Worker）
```

新增三个包（职责互不越界）：

| 包 | 类 | 职责 |
| --- | --- | --- |
| `seed/runtime/` | `SeedRuntimeIdentity` | 身份载体：世界键 + 种子 + 维度 + 版本 + 会话代号；换服 / 换维度 / 改种子 / 退世界 / 关功能 / 关显示 的失效判据 |
| | `SeedPredictionRepository` | 有界预测缓存（256 目标区块）、身份绑定、淘汰策略、五条失效入口 |
| | `SeedPredictionCoverageController` | 覆盖调度：半径 1~6（默认 3）、近→远排序、单执行位 + 最多 64 排队、移动重算 |
| `seed/observation/` | `SeedOreObservationTracker` | **唯一读真实区块的类**（P0 红线落点）：区块加载 / 卸载 / 方块更新三个发现入口 |
| | `SeedObservationSnapshot` | 观察维度的一行读数（候选 / 未观察 / 已确认 / 当前缺失；**没有可疑这一项**） |
| `seed/render/` | `SeedRenderEntry` | 一个待渲染条目（位置 + 矿物 + 确定性 + 观察状态） |
| | `SeedRenderSnapshot` | 不可变渲染快照 + 分类计数（渲染层与界面统计同一口径） |
| | `SeedOreWorldRenderer` | 世界空间渲染器：注册到既有 `WorldOverlay` 层体系（`LAYER_ID = "seed.prediction"`） |

---

## 四、Prediction Coverage（口径第十七~二十二节）

- **默认半径 3**（7×7 = 49 个目标区块），允许 1~6，**上限 6**；取值域由
  `SeedPredictionCoverageController.clampRadius()` 单点裁定（配置类不重复声明，避免两处夹取不一致）。
- **近→远**：排序键是「切比雪夫距离 → 欧氏平方 → x → z」，保证同一圈内顺序也是确定的（可复现）。
- **单执行位 + 有限排队**：同时只有一个目标在 Worker 上跑，最多 64 个待办；进度行显示
  「覆盖进度 X/Y 区块 / 正在预测 / 排队中」。
- **移动重算**：中心区块或半径变化时重算目标集合，并**丢弃已经跑出范围的待办**（已提交的不撤，
  但结果回写时会复核身份与范围）。
- **半径变化必须重算顺序**：`plannedRadius` 与 `plannedCenter` 一起判定，避免「改了范围但顺序没变」。
- **渐进显示**：每个目标一落地就进缓存并进快照，不是等全部算完才显示（界面 Note 里也这么写）。

实测（`C1` 第三段）：范围 3 → **已预测 49 / 目标 49**，累计提交 53 / 完成 51（含两次边界重算），
排队 0、移动丢弃 0、失败不再重试 0。从设好到铺满约 **6 秒**（Worker 已是热态）。

---

## 五、Prediction Repository（口径第十五、十六节）

- **上限 256 个目标区块**（`MAX_TARGET_CHUNKS = 256`），界面显示「预测缓存 N/256 个区块」。
- **只收成功结果**：`success=false` 不进缓存；`success=true, ores=[]`（这个区块确实没有钻石）**要进**，
  否则「已经算过、结果是空」会在每次移动后重复提交。
- **淘汰策略**（把玩家所在区块与当前半径传进去）：
  1. **覆盖范围外**优先；
  2. 同样在范围内 → **离玩家中心最远**优先；
  3. 同样远 → **最久未被使用**（`lastUsedAt`）优先。
- **身份绑定**：`bind(identity)` 发现身份不同就整体清空；`boundTo(other)` 用于回写前的复核。
- **五条失效入口**（全部收敛到 `SeedMiningService.invalidateRuntime(reason)`）：
  退世界、换服务器、换维度、改种子、关功能 / 关显示。一次调用完成
  「递增身份代号 → 清覆盖队列 → 清预测缓存 → 解绑观察层 → 摘掉渲染层 → 关闭渲染闸门」。

实测（`O1`）：

- 换服（A→B）：`缓存 3 / 候选 94 / 渲染条目 94` → `缓存 0 / 候选 0 / 渲染条目 0`；
- 换维度（主世界→下界）：`缓存 0 / 候选 0 / 渲染条目 0 / 覆盖排队 0`，身份「未建立」；
- 改种子（20260922→12345）：立刻 `0 / 0 / 0 / 0`（改之前 `2 / 67 / 67`），随后新身份重新铺开。

---

## 六、Actual Chunk Observation（口径第二十四~三十一节 · P0 红线）

### 6.1 唯一读世界的地方

`SeedOreObservationTracker` 是全模块**唯一**读真实方块状态的类，也是 P0 红线的落点：

```java
/** 「取区块时是否允许加载 / 生成」—— 恒为 false（P0 红线） */
private static final boolean LOAD_OR_GENERATE = false;

private static BlockState readLoadedBlock(ClientLevel level, BlockPos pos) {
    ClientChunkCache cache = level.getChunkSource();
    LevelChunk chunk = cache.getChunk(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.FULL, LOAD_OR_GENERATE);
    return chunk == null ? null : chunk.getBlockState(pos);
}
```

为什么必须是 `false`：26.1.2 的 `ClientChunkCache#getChunk(x, z, status, loadOrGenerate)` 在
`loadOrGenerate = true` 时会返回 `EmptyLevelChunk`（而不是 `null`），
于是「没加载」会被误读成「区块里没有钻石」→ 把未观察错判成「当前缺失」。
写死 `false` 之后：`null` = 客户端确实没有这个区块 → `UNOBSERVED`。

### 6.2 三个发现入口（只在「服务器真的把东西发给客户端」时动作）

| 入口 | 挂载方式 | 行为 |
| --- | --- | --- |
| 区块进入客户端 | Fabric `ClientChunkEvents.CHUNK_LOAD` | 把该区块内的候选逐个观察一遍 |
| 区块离开客户端 | Fabric `ClientChunkEvents.CHUNK_UNLOAD` | 该区块的候选状态回到 `UNOBSERVED` |
| 候选方块被更新 | 自写最小 Mixin（第八节） | 只对**候选位置**读一次方块；非候选 O(1) 查表丢弃 |

### 6.3 复杂度与线程

- 观察一个区块 = 该区块的**候选数**次 `getBlockState`（每次 `getChunk` 命中内存区块，不触发加载）；
  不是 16×16×384 的全量扫描。
- 观察全过程在客户端主线程（`onTick` 里由区块事件与方块更新事件驱动），
  渲染线程永远不碰 `ClientLevel`。

### 6.4 状态语义（两维独立）

- `OreObservationState.UNOBSERVED`：服务器还没把这个位置的真实状态给客户端；
- `CONFIRMED`：客户端看到的真实方块就是该矿物（**深板岩变种也算**，见下）；
- `MISSING`：Seed 候选存在，但客户端当前看到的位置不是该矿物；
- `SUSPICIOUS`：**233 不产出**（没有 SeedValidation、没有其它钻石来源建模、没有 worldgen 一致性校验，
  228 报告已实测「多出来的那一块是可复现的合法产物」）。

`isOreBlock()` 对钻石同时认 `minecraft:diamond_ore` 与 `minecraft:deepslate_diamond_ore` ——
否则深板岩层的候选会被整体误判成「当前缺失」（这是本轮专门修掉的一个真实陷阱）。

---

## 七、渲染快照与钻石世界渲染（口径第三十二~三十七节）

### 7.1 快照是唯一通道

渲染层**不允许**访问预测缓存、客户端世界、观察层，也不允许调 Worker。它每帧只做两件事：
读不可变快照 + 画 box。快照由服务层在客户端主线程造好，
**只在观察层真的变化时重建**（`observer.revision()` 变了才 `markDirty()`），不是每帧重建。

`SeedRenderSnapshot.build()` 代价 O(缓存里的候选数)；`SUSPICIOUS` 若日后被别处写入，
会被归到「未观察」计数（`default -> unobserved++`），**绝不会当成有效钻石**。

### 7.2 颜色语义（两个维度不混色）

| 元素 | 颜色常量 | 含义 |
| --- | --- | --- |
| 预测钻石（未观察 / 其它） | `0xEB4FA8FF` 青框（半透明面 `0x2D2F6FD0`） | 客户端还没加载该区块 |
| 已确认 | `0xF53FE07A` 绿框（半透明面 `0x3721A34A`） | 当前实际就是钻石矿 |
| 当前缺失 | `0x789AA0A6` 灰细线、不填充，**默认不画** | 预测位置现在不是钻石矿 |
| 调度敏感 | `0xF5FFB020` 琥珀**内圈细框**（内缩 `0.28`、线宽 `1.2`） | 原版世界生成顺序可能影响该位置 |

**调度敏感用「叠加内圈」而不是换色**：确定性维度（青 / 绿 / 灰）与观察维度（有没有内圈琥珀）
在画面上各占一层，视觉上也就不会把「调度敏感」误读成「假矿」。
渲染统一 `occlusion(false)`（透墙），线宽 `2.0`。

### 7.3 渲染闸门

- 第一道：`renderActive`（volatile；主线程写、渲染线程读）—— 身份成立 + 显示开关打开；
- 第二道：`WorldOverlay` 层是否注册。
  两道都在 `invalidateRuntime()` 里同时收掉，因此关功能 / 退世界后**一个预测框都不会残留**。
- 关掉「显示预测钻石」的瞬间：`缓存 0 个区块 / 渲染快照 目标区块 0 / 候选 0`（`O1`、`C1` 收尾实测）。

---

## 八、方块更新 Hook（口径第二十九条）

Fabric 在 26.1.2 **没有**客户端方块状态变更事件，因此新增全项目**最小**的一个 Mixin：

`mixin/client/ClientLevelBlockUpdateMixin.java` —— 注入
`ClientLevel#setServerVerifiedBlockState(BlockPos, BlockState, int)` 的 `TAIL`：

```java
@Inject(method = "setServerVerifiedBlockState", at = @At("TAIL"))
private void yiyiaddon$afterServerVerifiedBlockState(BlockPos pos, BlockState state, int updateFlags,
                                                    CallbackInfo info) {
    try {
        SeedMiningService.instance().onClientBlockUpdated((ClientLevel) (Object) this, pos);
    } catch (Throwable ignored) { }
}
```

为什么是这个方法：26.1.2 源码里单方块包（`ClientPacketListener#handleBlockUpdate`，行 942）与
多方块包（行 854）**唯一**的汇合点就是 `ClientLevel#setServerVerifiedBlockState`（行 187），
挂在这里两种包都覆盖，且不会漏掉「区块整体重发」以外的任何一次方块状态更新。

已登记进 `src/main/resources/yiyiaddon.mixins.json` 的 `client` 数组；
`proguard-rules.pro` 的 `-keep class com.yiyiaddon.mixin.** { *; }` 保证混淆后类名不变，
`verifyObfuscatedJar` 会按 `mixins.json` 逐条核对类是否还在产物里。

实机验证：`O1` / `P3` / `P4` 三处的
「`MISSING 证据 #1` → 实际方块 `minecraft:air`」都证明这条 Hook 在
**未混淆开发环境**与**混淆后的正式产物**里都生效（`P4` 还额外叠加了 103 个 Mod）。

---

## 九、配置与界面（口径第三十七、三十八、四十八节）

### 9.1 新增配置项（`SeedMiningConfig`）

| 配置 | 默认 | 说明 |
| --- | --- | --- |
| `renderPrediction` | `false` | 显示预测钻石（关闭即清空缓存 / 观察 / 渲染快照） |
| `coverageRadius` | `3` | 预测范围 1~6，界面用 `%.0f 区块` 的数字框 |
| `showMissing` | `false` | 是否把「当前缺失」也画成灰细框 |

配置仍然**按服务器隔离**（`ModuleStateConfig.settingsOf(moduleId, scopeKey)`，开发习惯第 236 条），
因此 A 服务器开着、B 服务器关着是正常现象（233 的回归装置正是踩到这一点后修正的）。

### 9.2 界面新增区块（`MiningSeedPage`）

- **「世界渲染」组**：显示预测钻石 / 预测范围 / 显示当前缺失 + 颜色语义 Note + 调度敏感说明 Note；
- **「附近覆盖」组**：覆盖进度 X/Y 区块、附近预测、尚未观察、已确认、当前缺失、调度敏感、
  正在预测、排队中、预测缓存 N/256 个区块。

### 9.3 文案核对（禁止项逐条）

界面与日志里**不存在**下列字样：`可疑矿`、`假矿`、`种子已验证`、`服务器作弊`；
出现的相关表述全部是「否定式说明」，例如：

- 显示当前缺失的说明：「…它只表示当前实际不是钻石，**不代表假矿**、**不代表服务器作弊**、**也不代表种子填错**」；
- 颜色 Note：「灰框 = 当前缺失（默认不画）」；
- 调度敏感 Note：「**调度敏感不等于假矿**：真实世界里它同样可能出现。本阶段只回答「预测」与「当前实际看到什么」，
  不做种子校验、不判定假矿」；
- 覆盖 Note：「客户端只会读「服务器已经发给它的区块」，**不会为了确认预测去请求加载任何区块**」。

`OreObservationState.MISSING` 的显示名是「缺失」（界面行标签为「当前缺失 / 显示当前缺失」），
语义只落在「预测位置现在实际不是钻石矿」这一层，不下任何关于种子真伪的结论（`C1` 第一段有原文证据）。

**未采集界面截图**：本轮的界面结论来自「源码级文案核对 + 编译 + 渲染设置走的是 230 阶段既有的
`SettingToggle` / `SettingNumberBox` 组件」这条链，**没有做目视截图**。这是本阶段唯一的展示性缺口（第十四节）。

---

## 十、实机证据（五个装置、六次运行）

| 装置 | 命令 | 环境 | 结果 |
| --- | --- | --- | --- |
| `O1` 观察生命周期 | `runClientSeedObservationTest` | 开发客户端 + 专用服务器 A(25565/20260922) + B(25566/12345) | **9 类证据各 1 条，全通过** |
| `C1` 覆盖 / 观察组合 | `runClientSeedCoverageTest` | 开发客户端 + 服务器 A | **三段全通过** |
| `C2` 关闭态回归 | `runClientSeedOffRegression` | 开发客户端 + 服务器 A（什么都不碰 20 秒） | **通过** |
| `P1` 生产 parity | `production-smoke.ps1` + `workerParity=1` | **正式产物** + 单人 Oracle | **全部判定：通过** |
| `P3` 生产观察 | `production-smoke.ps1` + `observation=1` | **正式产物** + 专用服务器 A（需 OP） | 5 类证据各 1 条，全通过 |
| `P4` 生产 + 真实 Mods | `production-smoke.ps1` + `ExtraModsDir`（103 jar） | **正式产物** + 真实用户 103 个 jar（Sodium / Iris / C2ME / Lithium / Krypton / ModernFix 等）+ 专用服务器 A | 5 类证据各 1 条，全通过 |

### 10.1 `O1` 观察生命周期（原文摘录见 `233-证据/E-实机证据摘录.md`）

- **CONFIRMED**：玩家区块 (0,0) 拿到 **45 个候选**（与冻结 20260922 (0,0)=45 一致），
  45 个全部「已确认」，抽样 `(9,-58,8)` 实际方块 `minecraft:deepslate_diamond_ore`；
- **MISSING**：`/setblock 9 -58 8 air` → 状态立刻变「缺失」，
  **未重跑预测、未重启计算器**（这正是「观察走真实方块更新、不靠重算」的证据）；
- **恢复**：`/setblock … minecraft:deepslate_diamond_ore` → 回到「已确认」；
- **卸载 → 未观察**：`/tp 4105 100 8` 让区块 (0,0) 真的离开客户端 ChunkCache，状态回到「未观察」；
- **重新加载**：传回后状态重新变「已确认」；
- **Server A→B**：A 侧基线 `缓存 3 / 候选 94 / 渲染条目 94` → B 里 `0 / 0 / 0`，覆盖排队 0；
- **维度清理**：下界里 `0 / 0 / 0 / 0`，身份「未建立」（覆盖已停止）；
- **维度恢复**：回主世界后自动重新开始（`缓存 1 / 候选 45`）；
- **改种子清理**：20260922 → 12345 后立刻 `0 / 0 / 0 / 0`，随后以新身份重新铺开。
- 收尾：关闭显示预测钻石 → `缓存 0 / 渲染快照 候选 0`。

### 10.2 `C1` 覆盖 / 观察组合

- **第一段 · 错误种子**：客户端填 `12345`、服务器世界是 `20260922`。
  - 出生点区块那次：**候选 31 → 已确认 0 / 当前缺失 31 / 未观察 0**（与冻结 12345 (0,0)=31 对上），抽样 `(9,-61,11)` 实际是 `minecraft:deepslate`；
  - 争议区块那次：**候选 18 → 已确认 0 / 当前缺失 18 / 未观察 0**；
  - 两次都断言「**可疑 = 0**」。
- **第二段 · 调度敏感 × 观察**：种子 2、传送到 `(-6385,-59,6085)`：
  - 预测侧：该位置确定性 = 「**调度敏感**」（与冻结口径一致，本区块调度敏感候选 1 条）；
  - 观察侧：实际方块 `minecraft:deepslate` → 观察状态「缺失」，**确定性仍为「调度敏感」**；
  - 结论：两个维度各自保留，**未产生「可疑」**。
- **第三段 · 默认范围 3**：种子回到 20260922、范围 3 →
  **49/49 目标铺满，候选 1100 个 → 已确认 1099 / 当前缺失 1 / 未观察 0 / 调度敏感 0**。

### 10.3 `C2` 关闭态回归

进服后什么都不碰、观察 20 秒，两个采样点读数完全一致：

```
计算器运行 否 / 缓存 0 个区块 / 覆盖已预测 0 / 覆盖排队 0 / 正在预测 — / 观察候选 0 /
渲染快照 0 条 / 显示预测钻石 关 / 运行时身份 未建立
```

并且原始日志里**没有任何** `Worker：正在启动本地世界生成计算器` 行 —— 计算器一个刻都没被拉起。
关于「AutoMiner 行为不变」：233 没有触碰自动挖矿的任何代码路径，
`AutoMinerPage.java` 在本次窗口内零写入（最后修改时间 `2026-09-23 11:21`，属 232 窗口）。

### 10.4 `P1` 生产 parity（正式产物上的冻结数字）

`02-开发报告/…/233-证据/P1-生产smoke-parity-固定集对照.txt`（原文）：

```
【判定】固定集候选合计：20260922 = 243 / 12345 = 110：（通过）
【判定】Worker 预测主链宿主 ChunkMap 查询恒为 0：（通过）
全部判定：通过
```

关键行：20260922 (0,0) 候选 **45**、冷启动 **3014 ms**、缓存 **529**、宿主 ChunkMap 查询 **0**；
Seed 12345 (-1,-1) 候选 **29（敏感 7）**；Seed 2 (-400,380) 候选 **23（敏感 1）**、
争议位置 `(-6385,-59,6085)` **调度敏感（通过）**。

### 10.5 `P3` / `P4` 生产观察（正式产物 + 真实 Mod 环境）

`P3`（只有正式产物 + fabric-api）：CONFIRMED / MISSING / 恢复 / 卸载→未观察 / 重新加载 五条全过；
Worker 就绪耗时 4427 ms、会话 `s1-135283a`、宿主监听「已停用（不接受任何外部连接）」；
目标区块 (-400,380) 的 37 个候选全部「已确认」。

`P4`（正式产物 + **103 个真实 Mod jar**，加载 286 个 Mod 条目，含 Sodium 0.9.1 / Iris 1.11.3 /
C2ME / Lithium / Krypton / ModernFix）：五条证据同样全过，候选 37 个全部「已确认」，
且日志里 `yiyiaddon` 自身**零报错**（只有第三方 Mod 的首次运行缺配置、离线账号鉴权等无关错误）。
这条同时回答了「**混淆 + 真实渲染环境**下渲染链是否成立」：目标区块 (-400,380) 的 37 个候选框
被提交进 `WorldOverlay` 的 `seed.prediction` 层并正常渲染，全程没有异常。

三次生产冒烟的脚本断言全部为「通过」：正式产物 Worker 已启动、Worker 命令行**不含任何开发目录**
（`\build\classes`、`\src\main\java`、`fabric-loom`、`\run-26.1.2` 等逐一否定）、命令行里含正式产物名、
退出后**残留 Worker 进程 0 个**。Worker 峰值 RSS：`P1` 677 MB / `P3` 617 MB / `P4` 601 MB。

---

## 十一、冻结数字对照（开工前 vs 完工后）

| 口径 | 232-FINAL 冻结值 | 233 完工后实测 | 判定 |
| --- | --- | --- | --- |
| Seed 20260922 (0,0) | 45 | **45** | 保持 |
| Seed 20260922 十目标合计 | 243 | **243** | 保持 |
| Seed 12345 (0,0) | 31 | **31** | 保持 |
| Seed 12345 (-1,-1) | 29（敏感 7） | **29（敏感 7）** | 保持 |
| Seed 12345 (-25,17) | 24 | **24** | 保持 |
| Seed 12345 (120,-130) | 26 | **26** | 保持 |
| Seed 12345 四目标合计 | 110 | **110** | 保持 |
| Seed 2 (-400,380) | 23 / 1 / 22 / 0 | **23 / 1 / 22 / 0** | 保持 |
| 争议位置 (-6385,-59,6085) | SCHEDULE_SENSITIVE | **调度敏感**（观察后仍是调度敏感） | 保持 |
| Worker 主链宿主 ChunkMap Query | 恒 0 | **恒 0** | 保持 |
| Worker 缓存（(0,0) 冷启动后） | 529 | **529** | 保持 |

> 说明：本轮**唯一**影响这些读数的代码路径是新增的覆盖 / 观察 / 渲染层，而它们全部在
> 「预测主链之外」（只消费 `PredictionResult`）。冻结口径的复核走的是 232 阶段同一套 parity 装置（`P1`），
> 因此对照有效。

---

## 十二、性能记录（默认范围 3）

| 指标 | 实测 | 说明 |
| --- | --- | --- |
| 覆盖铺满（范围 3 = 49 目标） | 约 **6 秒**（Worker 热态） | `C1` 第三段：16:06:49 → 16:06:55 |
| 单区块预测（热态） | 63~557 ms / 区块 | `P1` 逐目标数据（229 基线一致） |
| Worker 冷启动 | 3014 ms（`P1`）/ 4427 ms（`P3`，正式产物 + 首次） | 232-LOCAL 基线 4215 ms 量级一致 |
| **渲染快照重建** | **平均 129.4 µs / 次**（1100 候选，窗口内 50 次） | 只在观察层变化时重建，不是每帧 |
| 每次重建的分配 | 候选数 × 1 个 `SeedRenderEntry` + 1 次列表副本 + 1 个计数 + 1 个快照 | 1100 候选 ≈ 1103 个对象 |
| 客户端 FPS | **119**（1280×720，开发客户端） | 与 232 日常基线同量级 |
| 渲染条目数 | 1100（范围 3，49 区块） | 每帧只遍历快照并按 box 提交给原版 `Gizmos` |
| Worker 峰值 RSS | 601~677 MB | 与 232-P 结论一致 |

**如实说明未测项**：本轮**没有做帧级 CPU 采样**（没有对 `Gizmos` 提交与 `WorldOverlay` 的单帧耗时做单独计时），
因此「每帧 CPU」这一问只能给上述间接结论：快照不每帧重建（129.4 µs 只在变化时付一次），
渲染每帧只读快照 + 提交方框，实测 FPS 未见退化。

---

## 十三、失败与清理路径

| 情形 | 程序行为 | 证据 |
| --- | --- | --- |
| 关闭「显示预测钻石」 | 立刻清空缓存 / 观察 / 渲染快照，世界里不再有框 | `O1`、`C1` 收尾 |
| 换服务器 | 断线即失效；进入新服时旧数据全为 0 | `O1` Server A→B |
| 换维度 | 覆盖停止、数据全清；回主世界自动重开 | `O1` 下界 / 回主世界 |
| 改种子 | 立刻清空；以新身份重新建立 | `O1` 改种子清理 |
| 退出世界 / 关功能 | `invalidateRuntime` 同一收口 | 代码单一入口 + `O1` 实测 |
| 覆盖结果迟到 | 回写前复核身份代号（epoch），旧身份结果直接丢弃 | `SeedPredictionCoverageController` / `SeedMiningService` 三段复核 |
| Worker 失败态 | 覆盖调度暂停提交（不放大失败），界面仍可手动重试 | `driveCoverage()` 的 `FAILED` 早退 |
| 预测失败 | `coverage.onFailed()`，不重试同一目标（避免死循环） | 诊断行「失败不再重试 0」 |

---

## 十四、未验证项 / 已知差异 / 风险（如实列出）

1. **界面没有目视截图**：新增的「世界渲染」「附近覆盖」两组控件只做了源码级文案核对 + 编译验证，
   没有采集截图。建议后续人工打开种子挖矿页确认排版（这是唯一的展示性缺口）。
2. **1100 个候选里有 1 个「当前缺失」**：`C1` 第三段（种子 20260922、范围 3、区块中心 -400,380）
   实测「已确认 1099 / 当前缺失 1」。它说明的是「预测与客户端实际收到的方块在这一个位置上不一致」，
   **不是**假矿结论 —— 但本轮没有把这一格单独取出来做逐方块复现（232 阶段的同类现象已被解释为
   原版装饰顺序的合法产物），因此这里只当作**待观察项**记录，不下定性结论。
3. **`seedWorkerProbe` 与 parity 装置的数字差异**：232 遗留的独立探针 `seedWorkerProbe`
   单独跑 `12345 (-1,-1)` 得到的是「候选 25 / 敏感 9」，而走 `SeedMiningService` 的 parity / 覆盖装置
   得到「29 / 7」。**冻结口径以 parity 装置为准**（本轮 `P1` 再次确认 29 / 7）。
   本阶段没有改动该遗留探针，差异来源仍是 232 已记录的「装置口径不同」，此处如实标注。
4. **像素级视觉正确性**：负坐标 / 负 Y 的候选已实测被正确收集、观察并进入渲染快照
   （`(-6390,-62,6083)`、`(9,-58,8)`），但「画面上的框位置是否与方块严丝合缝」属于人工目视项，
   本轮未采集画面截图。
5. **性能只覆盖本机基线环境**：范围 3 + 1100 候选 + 119 FPS 是在本机（1280×720）实测；
   更大范围（半径 6 = 169 目标）与更低配机器的表现未测。
6. **历史遗留**：`seedmining-phase*` 报告、`232-证据/`、`232P-证据/` 与旧 PoC 装置一律保留未删，
   本阶段未清理任何历史证据。

---

## 十五、文件清单（233 全部新增 / 修改）

### 新增（12 个源码 + 3 个开发装置）

| 文件 | 用途 |
| --- | --- |
| `seed/runtime/SeedRuntimeIdentity.java` | 运行时身份（世界 / 种子 / 维度 / 版本 / 会话代号） |
| `seed/runtime/SeedPredictionRepository.java` | 有界预测缓存（256）+ 淘汰 + 五条失效入口 |
| `seed/runtime/SeedPredictionCoverageController.java` | 覆盖调度（近→远 / 单执行位 / 有限排队 / 移动重算） |
| `seed/observation/SeedObservationSnapshot.java` | 观察维度读数（无「可疑」项） |
| `seed/observation/SeedOreObservationTracker.java` | **唯一读真实区块处**（P0 红线落点） |
| `seed/render/SeedRenderEntry.java` | 渲染条目模型 |
| `seed/render/SeedRenderSnapshot.java` | 不可变渲染快照 + 分类计数 |
| `seed/render/SeedOreWorldRenderer.java` | 世界空间渲染器（青 / 绿 / 灰 + 琥珀内圈） |
| `mixin/client/ClientLevelBlockUpdateMixin.java` | 方块更新 Hook（`setServerVerifiedBlockState` TAIL） |
| `dev/seedpoc/ObservationRenderRegression.java` | 233 观察生命周期实机回归装置（CONFIRMED / MISSING / 恢复 / 卸载 / 换服 / 换维度 / 改种子） |
| `dev/seedpoc/SeedCoverageRegression.java` | 233 覆盖 / 观察组合回归装置（错误种子 / 调度敏感观察 / 渲染开销） |
| `dev/seedpoc/SeedOffRegression.java` | 233 关闭态回归装置 |

### 修改（6 个）

| 文件 | 改动 |
| --- | --- |
| `seed/config/SeedMiningConfig.java` | 新增 `renderPrediction` / `coverageRadius` / `showMissing` 三项配置与存取（取值域交给覆盖调度类裁定） |
| `seed/service/SeedMiningService.java` | 233 全部接线：身份同步与失效、覆盖驱动、观察挂载、快照重建、渲染层注册；新增渲染快照重建次数 / 累计耗时两个诊断读数 |
| `src/main/resources/yiyiaddon.mixins.json` | `client` 数组新增 `ClientLevelBlockUpdateMixin` |
| `feature/mining/ui/console/MiningSeedPage.java` | 新增「世界渲染」与「附近覆盖」两组（含颜色语义与调度敏感说明） |
| `build.gradle` | 新增 `runClientSeedCoverageTest` / `runClientSeedOffRegression` 两个运行配置 + 对应运行目录预处理 |
| `gradle/production-smoke.ps1` | 新增 `-Username` 参数（离线服务器的 OP 由名字派生，233 观察用例要发 `/setblock` 与 `/tp`） |

**没有删除任何文件**；232 及更早的 PoC 装置、报告、证据目录全部原样保留。
`232-FINAL-report.md` 中冻结的类与数字一行未动。

---

## 十六、停止说明

233 的口径全部落地，验证全部通过，冻结数字一个未变，发布产物已生成并完成生产实机冒烟。

**本阶段到此停止，不进入 234。** 后续阶段的口径（例如 SUSPICIOUS 的合法产生条件、
种子有效性验证、其它矿物、26.2 迁移）都不在本阶段范围内，本轮未做任何预留实现或半成品开关。
