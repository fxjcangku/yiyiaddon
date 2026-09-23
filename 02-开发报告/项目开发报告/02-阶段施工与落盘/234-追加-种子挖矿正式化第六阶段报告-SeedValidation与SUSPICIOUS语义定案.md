# 234 · 种子挖矿正式化第六阶段报告
## —— Seed Validation + SUSPICIOUS 语义定案 + 233 遗留问题最终收口

- 项目：`D:/mcaddon/yiyiaddon`（分支 `master`，只做 26.1.2；本窗口未触碰 `port/26.2`、未做任何 26.2 适配）
- Minecraft：26.1.2（Fabric Loader 0.19.5 / Fabric API 0.155.2+26.1.2 / JDK 25，`--release 21`）
- 产品版本：1.0-beta2
- 本阶段施工口径：用户 2026-09-23 逐条编号指令（零～七十九节）

---

## 0. 结论摘要

1. **Seed Validation 正式落地**：新增 `com.yiyiaddon.seed.validation` 包（State / Evidence / EvidenceGroup / Snapshot / Policy / Service），
   状态模型 `UNVERIFIED / COLLECTING / VERIFIED / INCONCLUSIVE / CONFLICTING`，**与 `OreObservationState` 完全分离**。
2. **验证不是伪验证**：通过「独立证据组（targetChunk + originViewer + oreSource + oreType + 26 邻域空间聚类）」计票，
   单区块、单矿脉都不足以验证通过；实测正确种子 `20260922` 在多区块下进入**已验证**，四个错误种子（12345 / 2 / 0 / -7777）**全部未进入已验证**。
3. **被挖矿容错**：正确种子已验证后，dev-only 分批移除 20% / 40% 已确认钻石，仍保持**已验证**（锁存式策略：普通 MISSING 不撤销）。
4. **AutoMiner 安全门已定义**：`SeedMiningService#mayUseForAutomatedMining()` 仅在 `VERIFIED` 时为 true；本阶段**仍未接 AutoMiner**（零改动）。
5. **SUSPICIOUS 语义定案 = 定义完成、自动产生继续禁用**：`SeedSuspicionPolicy` 逐条列出 8 条硬前提，其中第 8 条
   （Candidate Universe 完备性）**没有证明**，因此 `AUTO_SUSPICIOUS_ENABLED = false`（fail-closed，未造半成品假矿检测器）。
6. **233 三个遗留全部收口**：① 那 1 个 MISSING 已定位（`(-6417,-48,6123)`）并两次复现 + legacy 探针交叉验证，判定为
   **A 类：合法调度 / 生成历史差异**；② legacy `seedWorkerProbe` 25/9 vs 正式 29/7 的根因已实证（**会话内装饰历史**），
   任务已改名 `seedWorkerLegacyProbe` 并在代码/日志/任务注释里标记「非正式 Release Gate」；③ ESP / UI 目视验收截图已补全
   （含确认绿框、缺失灰框、调度敏感琥珀内圈、Iris 光影开 / 关、Validation 新区块、控制台页三段滚动）。
7. **冻结 Predictor 数字未变**（243 / 110 / 29&7 / 23&1&22&0 / 争议格 SCHEDULE_SENSITIVE / Host ChunkMap Query = 0）。
8. 结论：**可以进入 235 · Diamond Seed Target → AutoMiner**（前提是接受「验证是经验性多样本一致性，不是 Seed 唯一性证明」这一限制，见 §14）。
   唯一没跑完的验收项是**半径 6 性能烟测的六项读数**（装置已就绪，本机客户端进服停滞导致未采集），属性能范畴，不影响验证正确性判定（§19.2 / §23）。
9. 顺带收掉一个发行级坑：dev 装置原先用反射戳私有字段，反射字符串被 ProGuard 改写成混淆名，
   触发发布包常量审计误报；已改为正式入口 `MiningConsoleScreen#openSeedTab()`，**构建管线一行未改**（§19.3）。

---

## 1. 233-FINAL 基线复核

开工前按口径第一节完整重读（均在 `02-开发报告/项目开发报告/02-阶段施工与落盘/` 内，根目录不再放副本；为便于查阅这里写全名）：
`233-追加-种子挖矿正式化第五阶段报告-Prediction覆盖与实际Chunk观察与钻石世界渲染.md`、
`232-FINAL-种子挖矿多人Worker与生产发行最终收口报告.md`、
`232-追加-本地专用服务器实测报告-开发客户端下Worker缓存复用与维度切换.md`、
`228-追加-种子挖矿PoC第七轮最终收口报告-FEATURES调度因果定案.md`（228 因果定案），冲突时以两份 FINAL 为准。

复核要点（本阶段所有设计都建立在这几条上）：

| 项 | 233-FINAL 冻结结论 | 234 处理 |
|---|---|---|
| 预测 | `DiamondSeedPredictor` 纯 Seed 离线重建，给 BlockPos 级候选 + 确定性 | **未改一行** |
| 观察 | Candidate-only，`LOAD_OR_GENERATE = false`，只读服务器已下发区块 | **未改一行** |
| 渲染 | `SeedOreWorldRenderer` 只读不可变快照 | 仅新增「按需计时」钩子（默认关闭），绘制路径不变 |
| 228 | 合法 FEATURES 调度历史可改变同格最终方块（diamond ↔ gravel/tuff/deepslate） | 本阶段把它写成正式约束：**调度敏感永不等于可疑**、MISSING 永不直接证明 Seed 错 |
| 两维独立 | 确定性（预测维）与观察状态（观察维）不合并 | 234 的 Validation 是**第三维**，同样不合并 |

工作树检查（开工时）：`git status` 显示 `build.gradle`、`SeedPocEntry/SeedPocFlags/LegacyWorkerProbe/MiningSeedPage/SeedMiningService` 已改，
新增 `seed/validation/` 与三个 dev 装置；无其它窗口的重叠改动。全程未执行 `git reset --hard` / `git checkout .`，
未删除任何历史报告、旧 PoC 与证据目录。

---

## 2. 本阶段修改 / 新增文件

### 2.1 正式代码（新增）

```
src/main/java/com/yiyiaddon/seed/validation/
├── SeedValidationState.java          五态枚举 + allowsAutomatedUse()
├── SeedValidationEvidence.java       单条证据（record）
├── SeedValidationEvidenceGroup.java  独立性分桶 + 26 邻域并查集聚簇
├── SeedValidationSnapshot.java       界面唯一读数入口（record）
├── SeedValidationPolicy.java         唯一阈值来源 + 判定纯函数
├── SeedValidationService.java        只跑客户端主线程的验证服务
└── SeedSuspicionPolicy.java          可疑 8 条硬前提 + AUTO_SUSPICIOUS_ENABLED=false
```

```
src/main/java/com/yiyiaddon/seed/render/SeedRenderFrameProfiler.java   （口径第五十五节：帧级 CPU 采样，默认关闭）
```

### 2.2 正式代码（极小接线）

| 文件 | 改动 | 为什么不算破坏冻结 |
|---|---|---|
| [SeedOreWorldRenderer.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/render/SeedOreWorldRenderer.java) | `render()` 拆成「按需计时 + `draw()`」 | 关闭时该分支恒不跳转，绘制逻辑逐行不变；口径第五十五节明确要求补这项采样 |
| [SeedMiningService.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/seed/service/SeedMiningService.java) | 新增 `validation` 字段 + 观察变更时 `validation.update(...)`；`syncRuntime` 绑定身份；`invalidateRuntime` 清空；新增 `validationSnapshot()/mayUseForAutomatedMining()/restartValidation()/validationDiagnosticsCn()/validationEvidence()/validationGroups()/cachedPredictions()/validationConflictAvailabilityCn()` | 只做接线 / 生命周期 / 暴露快照（口径第七十二节） |
| [MiningSeedPage.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/console/MiningSeedPage.java) | 新增「服务器种子验证」区块（只读 snapshot） | 界面不自己算验证（口径第七十三节） |
| [MiningConsoleScreen.java](file:///d:/mcaddon/yiyiaddon/src/main/java/com/yiyiaddon/feature/mining/ui/MiningConsoleScreen.java) | 新增 `public void openSeedTab()`（内部走既有私有 `switchTab`，延到下一 tick 重建） | 让「外部流程落到种子页」有正式入口，代替 dev 装置原先对私有字段 `tab` 的反射（见 §19.3） |

### 2.3 dev 装置（不进正式产物）

| 文件 | 作用 |
|---|---|
| `dev/seedpoc/SeedValidationRegression.java` | 验证实机回归（正确种子 / 被挖矿容错 / 策略级合成用例 / 错误种子矩阵 / 清理矩阵） |
| `dev/seedpoc/SeedMissingCandidateRegression.java` | 233 遗留 1 个 MISSING 的定位与复现 |
| `dev/seedpoc/SeedVisualAcceptance.java` | ESP / UI 目视验收（画面 A~F，含控制台页三段滚动） |
| `dev/seedpoc/SeedRadius6Smoke.java` | 半径 6 压力烟测（169 目标区块） |
| `dev/seedpoc/LegacyWorkerProbe.java` | 由 `WorkerProbe.java` 改名，顶部【非正式 Release Gate】横幅 |
| `build.gradle` | 新增 `clientSeedVisualTest / clientSeedVisualMinedTest / clientSeedVisualShaderTest / clientSeedRadius6Smoke / clientSeedValidationTest / seedValidationServer / clientSeedMissingTest`；`seedWorkerProbe` → `seedWorkerLegacyProbe` |
| `build/capture-seed-visual.ps1`、`build/crop-image.ps1` | 取证工具（DPIAware 修正、裁切放大），不进产物 |

**冻结清单其它文件（`DiamondSeedPredictor` / `PredictionSession` / `ScheduleSensitivityAnalyzer` / `Offline*` / `SeedWorker*` /
`SeedPredictionRepository` / `SeedPredictionCoverageController` / `SeedOreObservationTracker`）本阶段零改动。**

---

## 3. Validation 架构

```
Observation（233 已有，主线程，Candidate-only）
        ↓  SeedMiningService 在「观察发生变化」的那一 tick 调用一次
SeedValidationService.update(Collection<PredictionResult>, SeedOreObservationTracker)
        ↓  按候选位置聚合出 SeedValidationEvidence（记录确定性 / 来源 / 观察状态 / 实际方块 / 首末观察时刻）
SeedValidationEvidenceGroup.build(...)      独立证据组（分桶 + 空间聚类，见 §6）
        ↓
SeedValidationPolicy.evaluate(Inputs)         纯函数判定（无副作用、可单测）
        ↓
SeedValidationState（五态） + SeedValidationSnapshot（不可变读数）
        ↓
MiningSeedPage 只读 snapshot 显示；SeedMiningService#mayUseForAutomatedMining() 供 235 消费
```

线程模型（口径第七十四节）：**只在客户端主线程**消费已有不可变数据；渲染线程不参与验证；没有每 tick 的后台线程读 `ClientLevel`；
更新时机是「预测落地 / 区块加载卸载 / 方块更新」这些既有事件的收口点，不是每帧。

---

## 4. ValidationState

| 状态 | 中文 | 语义 | 生产条件 |
|---|---|---|---|
| `UNVERIFIED` | 未验证 | 种子合法但还没有有效样本 | 初始 / 清空后 |
| `COLLECTING` | 收集中 | 有样本但不足以给结论 | 已确认组 ≥1 且未达阈值 |
| `VERIFIED` | 已验证 | 满足正式多样本正向策略 | 见 §11 阈值 |
| `INCONCLUSIVE` | 证据不足 | 观察量够但证据不足（大量 MISSING、独立单元不足等） | 已观察组 ≥ 阈值但确认不足 |
| `CONFLICTING` | 与当前模型冲突 | 强冲突证据 | **当前恒不可达**（fail-closed，见 §15） |

关键点：

- **`CONFLICTING` 不是装饰**：`SeedValidationPolicy#strongConflictReachable()` 明确返回 `false`，
  并要求「同一目标区块内出现 ≥2 个独立组的 **DETERMINISTIC 候选**位置实际不是钻石」才算强冲突。
  由于本阶段确定性恒无 `DETERMINISTIC` 产出（233 口径：算法尚不具备证明能力），该条件**不可达**，
  因此普通玩家挖矿 / 服务器改方块 / 合法调度差异**都不可能把状态打成冲突**。
- UI 使用「与当前模型冲突」这一措辞，绝不用「假矿 / 作弊 / Seed 错」。

---

## 5. Evidence 模型

`SeedValidationEvidence`（不可变 record，字段与口径第十一节逐项对应）：

| 字段 | 含义 | 来源 |
|---|---|---|
| `position` | 预测钻石的 BlockPos | `PredictedOre#position` |
| `targetChunk` | 目标区块 | `PredictionResult` 的目标区块 |
| `certainty` | DETERMINISTIC / SCHEDULE_SENSITIVE / UNRESOLVED | 预测维，原样保留 |
| `oreSource` | 来源归属（`OreSource`） | 预测维 |
| `originViewer` | 来源 viewer 区块（可能为空） | 预测维 |
| `oreType` | 矿物类型（本阶段只有钻石） | 预测维 |
| `observationState` | UNOBSERVED / CONFIRMED / MISSING | 观察维（233 `SeedOreObservationTracker`） |
| `actualBlockState` | 客户端实际读到的方块 id | 观察维 |
| `firstObservedAt` / `lastObservedAt` | 首次 / 最近一次观察到该状态的时刻 | 本层记录 |
| `groupId` | 所属独立证据组 | 见 §6 |

另有 `everConfirmed`（是否**曾经**确认过）：它是被挖矿容错的基础 —— 玩家把钻石挖掉后 `observationState` 会变成 `MISSING`，
但 `everConfirmed` 仍为真，因此「曾经确认」这一正向事实不会被抹掉。

---

## 6. Evidence Group 独立性规则（为什么一个矿脉只算一票）

**规则**：先按桶键分桶，再在桶内做 26 邻域连通聚类（并查集），每个连通簇 = 一个独立组。

```
桶键 = targetChunk | originViewer | oreSource | oreType
聚类 = 桶内 |dx|<=1 && |dy|<=1 && |dz|<=1（26 邻域）连通
组号 = splitmix64 混合（同为 hash 派生，跨会话稳定、不依赖 Map 迭代顺序）
```

为什么这样能压掉「同源重复计票」：

1. **同一矿脉**：一条钻石矿脉的若干方块在空间上必然相邻（原版 `OreFeature` 以 vein 为放置单位，方块位置连续），
   26 邻域聚类把它们并成一个簇 → 一条矿脉最多一票。这是最关键的一条。
2. **同一次 placement 的不同分叉**：同一 targetChunk + 同一 originViewer + 同一源，位置即使被噪声地形切开，
   也会落进同一个桶（桶内可能是多个簇，但它们共享同一「世界生成放置上下文」）；
   桶键保证了不同 origin / 不同源不会被算成同一票，**方向是保守的**（宁可多算一票也不误并）。
3. **跨区块写竞争**（228 已知现象）：同一位置可能由不同 viewer 竞争写入，`originViewer` 进桶键后，
   「同一区块被两个来源各写一次」不会被误当成两次独立观察。

> 保守性声明：本规则**无法完美恢复 vein identity**（离线模型不输出 vein id）。
> 因此我们选了「宁严勿宽」的近似：只有明确属于同一桶且空间连通才算一组。
> 这会让**通过阈值更难**（对正确种子不利、对错误种子更严），符合 fail-closed 要求。

---

## 7. 为什么不能单 Chunk / 单矿脉验证通过

- **单矿脉**：一个 `OreFeature` 一次生成 3~8 个钻石方块；若按方块计票，一条矿脉就能凑出“很多证据”。
  分组后它只有 1 票 → 阈值（独立组 ≥8）自然拦住。
- **单区块**：一个区块的钻石分布本身是同一片世界生成上下文（同一批 FEATURES 调度、同一组邻域），
  存在「局部巧合」风险（例如错误种子恰好在该区块也有钻石）。因此策略同时要求 **≥3 个不同目标区块**。

实测对照（策略级合成用例，dev-only）：

| 用例 | 独立组 | 覆盖区块 | 比例 | 期望 | 实测 |
|---|---|---|---|---|---|
| 单区块 45/45 全确认 | 少（同一区块内多簇） | 1 | 100% | 不得 VERIFIED | **证据不足** ✅ |
| 只有一个独立确认组 | 1 | 1 | 100% | 必须 COLLECTING | **收集中** ✅ |
| 9 组 / 3 区块 | 9 | 3 | 100% | 应 VERIFIED | **已验证** ✅ |

---

## 8. Calibration 测试环境（口径第十四节）

| 项 | 值 |
|---|---|
| 服务端 | 本机 Fabric 专用服务器，`level-seed=20260922`，不加载任何改 worldgen 的 Mod |
| 端口 | 25565（真值世界）/ 25567（验证回归专用，可被 dev-only 挖掘）/ 25566（Server A→B 的 B 台） |
| 客户端 | `runClientSeedValidationTest`（真客户端 + 真 Worker + OP 权限） |
| 覆盖半径 | 3（7×7 = 49 个目标区块，出厂默认） |
| 声明种子 | 20260922（正确）/ 12345 / 2 / 0 / -7777（错误） |
| 采集方式 | 客户端真实加载区块后，Validation 自动消费观察（无需人工点「验证」） |

---

## 9. 正确种子数据（20260922）

| 阶段 | 独立确认组 | 覆盖目标区块 | 已确认候选 | 比例 | 状态 |
|---|---|---|---|---|---|
| 首次达标 | 19 组 | 3 | — | 100% | 未验证 → 收集中 → 证据不足 → **已验证** |
| 满覆盖（49 区块） | 274 组 | 49 | 1097 | 100% | **已验证**（锁存） |
| 目视验收那一次（25567 被挖世界） | 303 组（曾经确认 299） | 49 | 1165（缺失 9） | 98.68% | **已验证** |

结论：正确种子在**多区块**下能稳定进入已验证；达标时用了 3 个目标区块、19 个独立组（远低于满覆盖规模），
说明阈值不是「要靠铺满才过」。

---

## 10. 错误种子矩阵（口径第十四、四十二节）

同一台服务器、同一测试路线、同样铺满覆盖（每颗种子都等到覆盖铺满再判）：

| 声明种子 | 独立确认组（曾经确认） | 覆盖目标区块 | 已观察组 | 解释比例 | 最终状态 |
|---|---|---|---|---|---|
| 20260922（正确） | 274（开发端满覆盖）/ 286（正式产物基线） | 49 | 274 | 100% | **已验证** |
| 12345 | 3 | 3 | 303 | 0.99% | 证据不足 |
| 2 | 0 | 0 | 314 | 0.00% | 证据不足 |
| 0 | 3 | 3 | 300 | 1.00% | 证据不足 |
| -7777 | 2 | 2 | 308 | 0.65% | 证据不足 |

**实测事实**：本次测试的 4 个错误种子全部未进入 VERIFIED；
（口径第七十五节）这**不能**扩展成「所有错误种子永远不可能误验证」——那是需要数学证明的命题，本阶段没有，也不声称。

---

## 11. 最终 Verify Policy（唯一阈值来源）

```java
public static final int VERIFY_MIN_CONFIRMED_GROUPS   = 8;    // 独立确认组
public static final int VERIFY_MIN_CONFIRMED_CHUNKS   = 3;    // 覆盖目标区块
public static final double VERIFY_MIN_CONFIRMED_RATIO = 0.5;  // 解释比例
public static final int INCONCLUSIVE_MIN_OBSERVED_GROUPS = 8; // 进入「证据不足」的观察量门槛
public static final int CONFLICT_MIN_DETERMINISTIC_GROUPS = 2; // 强冲突（当前不可达）
```

判定顺序（`SeedValidationPolicy#evaluate`）：**冲突 → 验证 → 证据不足 → 收集 → 未验证**。

阈值依据（不是拍脑袋）：

- 「解释比例 ≥ 50%」：正确种子满覆盖实测 98.68%~100%；被挖 40% 后仍 ≥ 60%；
  错误种子 ≤ 0.93%。50% 落在两个量级之间，且给「被大量挖走」留了很宽的余量。
- 「独立确认组 ≥ 8」：一条矿脉只算一票，8 组 ≈ 至少 8 条不同矿脉、且实测正确种子首次达标就有 19 组；
  错误种子在同一路线下最多 3 组。8 组在「正确种子轻松过」与「错误种子偶发 1~2 个巧合不过」之间留了 3 倍以上余量。
- 「覆盖 ≥ 3 个目标区块」：由 §7 的「单区块不得通过」直接给出。

**经验性说明（必须写清）**：以上阈值是**经验阈值**：来自真实专用服务器上的多样本实测（正确 1 颗 + 错误 4 颗 + 被挖 20%/40%），
**不是**「Seed 唯一性证明」，也**不是**数学上可证明的最优分割。它的正确性主张只有一条：
「在当前 26.1.2 Vanilla Overworld + 该 Seed 的模型下，已观察样本与模型的一致性足够高」。

---

## 12. 被挖矿容错与状态稳定策略

- **正向优先**：验证只依赖（曾经）确认过的独立组；MISSING 只记录、**默认不当负票**（口径第十六节）。
- **锁存**：一旦达到阈值即置 `verifiedLatch`；此后只有**强冲突**（当前不可达）才会降级。
- **实测容错**（开发端 + 正式发行产物各一次）：
  - 开发端：正确种子已验证后，dev-only 用 `/setblock air` 分批移除已确认钻石 —— 移除 20%（220 条）仍 **已验证**；
    再移除到约 40%（439 条）仍 **已验证**；
  - **正式发行产物**（§20.2）：基线 286 组 / 已确认 1158，先移除 232 条（≈20%）→ 独立确认样本仍 286 组、状态仍 **已验证**；
    再移除到 464 条（≈40%）→ 仍 286 组、仍 **已验证**（已确认 694 / 缺失 464，基线缺失 0）。
- **不抖动**：单个方块 `CONFIRMED → MISSING` 不改变状态（既不降级也不重算为新状态）。

---

## 13. 清理矩阵与 runtime-only

| 事件 | 期望 | 实测 |
|---|---|---|
| 改种子（20260922 → 12345） | 状态清零 / 证据清零 / VERIFIED 清除 | ✅ 回到未验证，随后按新种子重新收集 |
| 换服务器（A 25567 → B 25566） | B 必须未验证 | ✅ 进入 B 第一刻读数为 0 组 / 未绑定 |
| 换维度（主世界 → 下界） | 清空 / 挂起为不支持 | ✅ 清空，界面显示不支持该维度 |
| 退出世界 | 运行时证据清空 | ✅ 全部清空 |
| 重启客户端 | 不读上次结论 | ✅ **runtime-only**：证据不落盘，重新进服重新收集 |

实现：`SeedValidationService` 绑定 `SeedRuntimeIdentity`（世界身份 / 种子 / 维度 / Minecraft 版本 / 会话代号），
身份变化 → `reset(reason)`；`SeedMiningService#invalidateRuntime` 与身份建立/失效同一处收口，**没有第二套清空路径**。

---

## 14. 未来 AutoMiner 安全门（本阶段只定义、不接）

```java
// SeedMiningService
public boolean mayUseForAutomatedMining() {
    return validation.snapshot().state() == SeedValidationState.VERIFIED;   // 其它状态一律 false
}
```

- 235 只消费这一个正式 gate，不得自行重定义验证规则。
- 本阶段仍未接 AutoMiner：`MiningStateMachine / MiningPathing / MiningVeinMiner / MiningFastBreakController` **零改动**，
  种子目标**没有**进入 Baritone。

---

## 15. SUSPICIOUS 语义定案

### 15.1 正式定义

「客户端当前看到一个钻石矿，但在**当前已验证 Seed + 当前版本 + 当前 worldgen model** 下，
它不属于任何已知合法 Seed Candidate。」

### 15.2 八条硬前提（`SeedSuspicionPolicy`，逐条可查询 `blockers()`）

1. `SeedValidationState == VERIFIED`；
2. Minecraft 版本正确（26.1.2）；
3. 维度受支持（主世界）；
4. 当前模型明确是 Vanilla 26.1.2 Overworld 钻石模型；
5. 该区块已由客户端合法加载（**绝不主动加载远端区块**，233 P0 红线）；
6. 实际 BlockState 是 `diamond_ore` 或 `deepslate_diamond_ore`；
7. 该位置不属于当前模型的任何已知合法 Diamond Candidate；
8. **已证明「当前 Candidate Universe 对该判断足够完备」**。

### 15.3 Candidate Universe 完备性判定：**不完备**

本阶段复核了 `DiamondSeedPredictor` 的能力边界：

- 它能给出的是**离线调度口径下的候选集合**（含 `SCHEDULE_SENSITIVE` / `UNRESOLVED` 两类不确定标记）；
- 它**不能**证明「覆盖所有合法 FEATURES 细粒度 interleaving 可能产生的最终钻石坐标」——
  228 的因果实验已经证明：同一 Seed / 同一位置，合法调度顺序会让最终方块在 diamond 与 gravel/tuff/deepslate 之间变化；
- 也就是说「预测候选不存在」≠「任何合法调度下都不可能」。

因此 **第 8 条没有证据**。

### 15.4 最终决定：**自动 SUSPICIOUS 继续禁用**

`SeedSuspicionPolicy.AUTO_SUSPICIOUS_ENABLED = false`，生产代码**不会**产出 `SUSPICIOUS`；
UI **不显示**可疑数量（与 233 一致），界面也没有「假矿 / 作弊」任何措辞。

若将来要开启，缺的证明是（清单）：

1. 一个**穷举 / 可证明完备**的候选生成模型（覆盖所有合法 FEATURES 顺序与跨区块写竞争的组合空间），
   或其可判定的策略（例如能证明「该位置的最终方块在所有合法调度下都只能是 air/stone」）；
2. 该模型的**离线 → 真实世界一致性**验证：在多个真实种子 × 多区块上，模型判「不可能」的位置，
   真实世界里从未出现过钻石（需要可重复的对照实验）；
3. 由此得到的误报率上界（当前连一次可用的对照样本都没有，因为模型无法产出「不可能」集合）。

### 15.5 为什么 SCHEDULE_SENSITIVE 永远不是可疑

调度敏感 = 「原版 FEATURES 顺序可能影响该位置最终是否为矿物」；它描述的是**模型的不确定性**，
不是「世界里出现了不该出现的矿」。把调度敏感判成可疑，等于把「我们不知道」当成「服务器作弊」，与 228 定案直接冲突。
代码里这条是显式规则：`SeedSuspicionPolicy` 对 `SCHEDULE_SENSITIVE` 直接返回「永不可疑」。

---

## 16. 233 遗留 #1：1100 中的 1 个 MISSING —— 最终收口

### 16.1 定位（口径第二十七节，逐字段）

出现位置：**`(-6417, -48, 6123)`**（复现命令：连 25565 → `tp -6385 -59 6085` → 覆盖半径 3）

| 字段 | 值 |
|---|---|
| BlockPos | `(-6417, -48, 6123)` |
| Target Chunk | `(-402, 382)` |
| PredictionCertainty | **未解析（UNRESOLVED）** |
| OreType | 钻石 |
| OreSource | 来源未归属 |
| originViewer | 无 |
| conflictingWriters | 0 个 |
| 实际 BlockState | **`minecraft:deepslate_iron_ore`** |
| 客户端 Chunk 是否加载 | **已加载（true）** |
| ObservationState | 缺失（MISSING） |
| 该区块候选分布 | 已确认 18 / 缺失 1 / 未观察 0 |

同一片区另 9 条缺失（区块 `(-402,379)`，已确认 17 / 缺失 9）的确定性全部是**调度敏感**，实际方块为 `deepslate` / `gravel`。
该次覆盖读数：`1105 候选 / 已确认 1095 / 缺失 10 / 调度敏感 9`。

### 16.2 最小复现（口径第二十九节）

1. **同世界原地复现**：传送到远方让区块卸载 → 重新加载 → 读数**完全一致**（仍 MISSING，仍 `deepslate_iron_ore`）；
2. **断开重连**：重新进服再读 → 读数**完全一致**；
3. **legacy 探针交叉验证**（离线调度口径，原始输出见 `E-原始日志/L3`、`L4`、`L5`）：
   - 冷会话（无预热）预测区块 `(-402,382)`：候选 **19** / 调度敏感 1，观察格 `(-6417,-48,6123)` → **在候选集里**，确定性 **SCHEDULE_SENSITIVE**；
   - 先预热 `(-401,382)` 再预测同区块：候选 **18** / 调度敏感 0，观察格 `(-6417,-48,6123)` → **不在候选集里**。

第 3 条是关键证据：**该位置的候选资格随「邻域装饰历史」变化** —— 与 228 已定案的合法调度差异同类。

### 16.3 最终分类

**A 类：合法调度 / 生成历史差异。**

排除项：

- 不是 **E（Prediction bug）**：同一预测口径（预热后）离线复算与真实世界一致；且未预热口径下它被标为调度敏感，
  预测器**自己已经声明**「该位置受调度影响」。
- 不是 **D（Observation bug）**：两次复现读数一致，实际方块 `deepslate_iron_ore` 与「不是钻石」自洽。
- 不是 **B（测试环境修改）**：该区块在 `(-6417,…)` 一带远离验证回归的挖掘区（挖掘发生在出生点一圈），且无写入者 / 无冲突写入者。
- 不是 **C（旧区块历史状态）**：卸载重载后结论不变；若是历史状态造成的差异，离线重算不会给出「随历史变化」的候选资格。

仍存的不确定性（如实记录）：本阶段只做了「同世界两次复现 + 离线探针交叉验证」，
**没有**在 3 个全新独立世界存储里重生成同一区域（口径第二十九节的完整版实验），
因为那需要另起 3 次专用服务器世界生成（成本高且不影响结论方向）。缺失的证据是：
「在 3 个全新世界存储中该位置的真实方块序列」——用于把「调度历史敏感」进一步量化到具体历史类别。

---

## 17. 233 遗留 #2：legacy seedWorkerProbe 25/9 vs 正式 29/7 —— 根因与最终处理

### 17.1 根因（实测，非推断）

| 步骤 | 命令 | Seed 12345 区块 (-1,-1) 读数 |
|---|---|---|
| 冷会话 | `seedWorkerLegacyProbe -PprobeArgs="build/…-cold -1 -1 12345"` | 候选 **25** / 敏感 **9** |
| 预热 (0,0) 后 | 同上 + 先测 `0,0` | 预热 (0,0) = **31**，随后测 (-1,-1) → 候选 **29** / 敏感 **7** |

原始输出已归档（本阶段重跑并留证，不是引述旧结论）：
`E-原始日志/L1-legacy探针-12345冷会话(-1,-1)=25候选9敏感.txt`、
`E-原始日志/L2-legacy探针-12345预热(0,0)后=29候选7敏感.txt`。

差异来源：**同一会话内是否已经装饰过邻域区块**（即装饰历史 / 合法 FEATURES 调度），
与 228 的因果结论一致。**不是** Predictor 回归：同 Seed / 同版本 / 同维度 / 同目标，
在**同一口径**（预热与否）下结果可重复。

因此这不属于口径第三十三节的 C 类（硬回归），而是 B 类（探针有意测试不同口径 ⇒ 事实上是「冷会话口径」）。

### 17.2 最终处理

- 任务改名 `seedWorkerProbe` → **`seedWorkerLegacyProbe`**（`build.gradle`），主类改名 `LegacyWorkerProbe`；
- 类顶部横幅：**【非正式 Release Gate】**，并写明「本装置是冷会话 / 探索口径，禁止用于发布门禁」；
- 日志前缀与注释同步标注；
- **正式 authoritative gate 唯一**：
  - 开发侧：`runClientSeedWorkerParityTest`（Worker ↔ 集成服务端 Oracle 逐项对照）；
  - 发布侧：`verifyObfuscatedJar` + `production-smoke.ps1`（正式 Jar + 专用服务器，核对全部冻结数字）。
  **未来任何窗口不得用 legacy Probe 的数字当发布门禁，也不得与 29/7 混用。**

---

## 18. 233 遗留 #3：ESP / UI 目视验收（人工证据）

证据位置（两处，分工与 233 一致）：

- **截图（定稿 15 张）**：`02-开发报告/截图/2026-09-23-种子挖矿第六阶段-ESP与验证UI/`（按项目截图画廊既有命名 `日期-主题-NN-描述.png`）
- **原始数据**：`02-开发报告/项目开发报告/02-阶段施工与落盘/234-证据/`（`E-原始日志/` 17 份原始 stdout 与对照；`99-过程截图存档/` 目视验收各轮次全量原始批次 267 张，**一张未删**）
- 入口先看 **`234-证据/Z-证据索引.md`**：每个文件证明哪一条口径、对应本报告哪一节、怎么重跑，都在里面。

| 画面 | 截图文件（`截图/2026-09-23-…/` 下） | 判读结论 |
|---|---|---|
| 正坐标 + 负 Y（区块 0,0，玩家埋在 y=-40） | `…-01-ESP-正坐标与负Y-绿框严丝合缝.png` | 绿框与钻石方块**严丝合缝**，透墙可见 |
| 负 X / 负 Z（区块 -3,-3） | `…-02-ESP-负X负Z-绿框.png` | 框体正常，无镜像 / 偏移 |
| 打开「显示当前缺失」 | `…-03-ESP-显示当前缺失-灰细框.png` | **灰细框**（当前缺失）与绿框同屏，位置正确（读数 缺失 12 / 调度敏感 26） |
| 关闭「显示预测钻石」 | `…-04-ESP-关闭显示预测钻石-世界已清空.png` | 关闭瞬间渲染条目 4181 → 0，世界里**一个框都不剩** |
| 绿框 + **琥珀内圈**（调度敏感） | `…-05-ESP-绿框与琥珀内圈-放大.png`、`…-06-ESP-同机位调度敏感为0-内圈消失-放大.png` | 内圈内缩比例正确、无偏位；同机位另一状态（敏感 0）下内圈消失，与读数一致 |
| 被挖世界（约 40% 钻石已移除） | `…-07-ESP-被挖世界-灰细框与绿框-放大.png`、`…-08-ESP-被挖世界-出生点一圈大面积缺失.png` | 大量灰细框与绿框同屏，读数 缺失 439 / 候选 1097 一致 |
| 控制台「种子挖矿」页（三段滚动） | `…-09-验证UI-种子页顶部.png`、`…-10-验证UI-种子页中段-服务器种子验证.png`、`…-11-验证UI-种子页底部.png` | 配置 / 世界渲染 / 附近覆盖 / **服务器种子验证** / 环境 / 预测状态全部可达，无重叠、无越界 |
| Iris 光影**开启**（100+ Mods 环境） | `…-12-光影开(BSL ULTRA)-正坐标绿框与琥珀内圈.png`、`…-13-光影开-显示当前缺失-灰细框.png` | `[Iris] Using shaderpack: BSL_v10.1.3.zip`（Profile: ULTRA）；方框不偏移、无深度闪烁、不崩溃（Meteor HUD 314 fps） |
| Iris 光影**关闭**（同环境） | `…-14-光影关-正坐标绿框.png`、`…-15-光影关-显示当前缺失-灰细框.png` | `Shaders are disabled because enableShaders is set to false`；方框同样正确 |
| 渲染帧级采样 | `234-证据/E-原始日志/A1-…`、`C1-…`（`seed.prediction：渲染帧级采样` 行） | 见 §19 |
| 原始日志与对照 | `234-证据/E-原始日志/`（`V1` 验证回归、`M1` MISSING 定位、`L1~L5` legacy 探针、`P1~P5` parity / 生产冒烟 / 校验值、`R1` 半径 6 失败现场） | 见 §16、§17、§19.2、§20 |
| 各轮次全量原始批次 | `234-证据/99-过程截图存档/`（267 张） | 含重复机位与早期迭代，一张未删；需要复核像素时回这里按时间戳取原图 |

> 光影开 / 关的做法（可复现）：运行用户日常目录 `run-26.1.2`（103 Mods，含 Iris + Sodium + 9 个光影包）。
> 开 = 其 `config/iris.properties` 原状（`enableShaders=true`、`shaderPack=BSL_v10.1.3.zip`）；
> 关 = 备份该文件后临时改 `enableShaders=false`，跑完**已还原**（备份留在 `build/iris.properties.234bak`）。
> 未改动用户目录里的任何 Mod。

### 18.1 Validation 新区块实测读数（界面截图同一时刻）

```
服务器种子验证
  验证状态        已验证
  有效样本区块    49 个
  独立确认样本    299 组
  已确认候选      1165 个
  当前缺失        9 个
  [重新开始验证]
  独立确认样本 299 组（阈值 8），覆盖 49 个目标区块（阈值 3），解释比例 98.68%（阈值 50.00%）
```

界面只显示上述内容 + 三条说明（reason / policy / scope），**没有**「Seed 100% 正确 / 唯一确定 / 绝对正确」任何措辞；
未验证时另有明确提示「当前 Seed 尚未完成验证（预测仍可显示，但不代表已验证）」（口径第九、五十一节）。

---

## 19. 性能：帧级 Renderer CPU 与半径 6 压力

### 19.1 帧级 CPU 采样（口径第五十五节，dev-only、默认关闭）

装置：`SeedRenderFrameProfiler`，开关 `-Dyiyiaddon.seedprofiler.enabled=1`（**不设则为关，热路径只多一条恒不跳转的分支**）。

| 环境 | 帧数 | 平均 | P95 | 最大 | 快照条目 |
|---|---|---|---|---|---|
| 开发端（57 Mods，半径 3，画面 A~F 全程） | 7 633 | 30.8 µs | 58.1 µs | 1 812.5 µs | 1 174 ~ 4 181 |
| 日常端（103 Mods + Iris/BSL 光影开） | 14 040 | 25.3 µs | 40.6 µs | 2 105.6 µs | 1 061 ~ 3 135 |
| 日常端（103 Mods，光影关） | 13 000+ | 同量级 | — | — | 1 061 ~ 3 135 |

判读：**每帧渲染循环的 CPU 开销在几十微秒量级**（相对 16.7 ms 一帧约 0.2%~0.4%），
最大值出现在传送后快照刚换新的那一帧（一次性抖动），不构成持续压力。
另注：半径 3 + 传送会让缓存累积到多片区（快照条目可达 4 000+），这也是 P95 上升的原因。

### 19.2 半径 6 压力烟测（口径第五十六节）

装置：`SeedRadius6Smoke`（`runClientSeedRadius6Smoke`，范围 6 = 13×13 = 169 目标区块）。

**实测结论：未取得完整读数（本阶段如实记录，不编造）。** 现象与已排除项：

| 观察 | 事实 |
|---|---|
| 现象 | 客户端进服后停在「加载地形」（画面已渲染世界 + 移动提示），客户端日志静默、`SeedPocEntry` 的刻回调不再触发；装置因此在「等待进入世界」阶段停住 |
| 已排除：234 新代码 | 那一刻种子挖矿**尚未启用**（装置还没跑到设置那一步），覆盖队列 / Worker / 渲染层全都没启动 |
| 已排除：Worker | Worker 进程未启动（日志无「正在启动本地世界生成计算器」） |
| 已排除：渲染器与帧级采样 | 渲染层未附加；同一采样器在目视验收与光影验收里连续跑了 7000~14000 帧无异常 |
| 已排除：全新运行目录 | 用目视验收目录预热（`robocopy` 复用 options / 配置 / Baritone 缓存）后同样复现 |
| 对照：同级装置正常 | 同一台服务器、同一客户端版本下，目视验收装置（`visual=1`）连续三次跑完 A~F；单机 Worker 对照（`workerParity=1`）也一次跑完 |

为便于判断「刻在跑但没进世界」与「刻停了」，装置已补一行日志：`已挂载，等待进入世界（玩家 / 世界）`。

因此本阶段对半径 6 的结论只有**有实测支撑**的两条：

- `覆盖目标区块 = 169`（服务层按 `(2×6+1)²` 算出并在装置开跑时打印）；
- 预测缓存上限 `256` **足够**覆盖 169 个目标区块（未触上限，界面「预测缓存 x / 256」可见）。

其余指标（铺开耗时 / 实际候选数 / Render entries / FPS / Worker RSS / 客户端堆）**本阶段未取得有效样本**，列为「当前仍存在的限制」，不填猜测值。

### 19.3 顺带收掉的一个发行级坑：反射字符串被 ProGuard 改写

`obfuscatedJar` / `verifyObfuscatedJar` 第一次运行**失败**：

```
java.lang.AssertionError: 解密结果与原始字符串集合不符：com/yiyiaddon/e/a/ac.class
```

定位过程（可复现）：

1. 查 `build/obfuscation/mapping.txt`：该混淆名 = `com.yiyiaddon.dev.seedpoc.SeedVisualAcceptance`；
2. 审计工具原本只报类名，临时加上「长度 + 码点」后拿到失败值 = 长度 1、码点 `U+0061`（字符 `a`）；
3. `javap -v` 对比「编译产物」与「混淆后产物」的常量池：
   编译产物里**没有** `a`，混淆后产物多出 `#9 = String // a`，且它出现的位置是
   `MiningConsoleScreen.class.getDeclaredField("a")` ——
   **ProGuard 把反射成员名 `"tab"` 改写成了它自己的混淆名 `"a"`**；
4. 而发布包常量审计要求「解密出来的常量必须能在加固前输入里找到」，`a` 自然找不到 → 误报。

结论与处置：这不是我的验证层逻辑问题，而是「dev 装置用反射去戳私有字段」这件事本身在混淆发行里不稳定
（虽然 ProGuard 会同步改写，属于侥幸成立）。因此改为**正式入口**：

```java
// MiningConsoleScreen
public void openSeedTab() { switchTab(Tab.SEED); }
```

dev 装置改调它，反射整段删除（`java.lang.reflect.Field` 也不再 import）。
**构建管线本身一行未改**（临时加的诊断消息已还原），随后 `obfuscatedJar` + `verifyObfuscatedJar` 通过：
`类加载及成员校验=1403 / 密文篡改认证拒绝=通过 / 混淆静态检查通过：1362 个类已改名`。

给后来者的提醒（写进本节就是为了下次不必再查一遍）：**发布包里任何「靠反射字符串（字段名 / 方法名）」的代码，
都会撞上同一条审计红线**；要么改走正式 API，要么接受 ProGuard 的改写行为并同步调整审计口径。

---

## 20. 冻结数字回归 / Host Query / 生产发行

| 项 | 冻结值 | 234 复核 |
|---|---|---|
| 20260922 十目标 | 45 / 9 / 29 / 21 / 22 / 27 / 21 / 18 / 18 / 33 = **243** | ✅ 未变（`runClientSeedWorkerParityTest` 实测：合计 243（期望 243）（通过）） |
| 20260922 (0,0) | **45** | ✅ 未变 |
| 12345 四目标 | 31 / 29（敏感 7）/ 24 / 26 = **110** | ✅ 未变（四目标逐项判定全部通过：31 / 29+敏感 7 / 24 / 26） |
| 12345 (-1,-1) | **29 / 7** | ✅ 未变 |
| Seed 2 (-400,380) | **23 / 1 / 22 / 0** | ✅ 未变 |
| 争议位置 (-6385,-59,6085) | **SCHEDULE_SENSITIVE** | ✅ 未变 |
| Host ChunkMap Query | **0** | ✅ 未变（Worker 侧四个目标区块查询数全 0） |

回归装置与判据（可直接复跑）：

```
.\gradlew.bat runClientSeedWorkerParityTest    # Worker ↔ 集成服务端 Oracle 逐项对照（开发侧权威门禁）
```

该次运行报告落盘：`run-26.1.2-seed-worker-parity-test/seedpoc-232-Worker对照.txt`。

**发行侧权威门禁**：`obfuscatedJar` + `verifyObfuscatedJar` + `gradle/production-smoke.ps1`
（正式 Jar + 专用服务器 + `workerParity=1`，核对上表全部数字 + 退出无孤儿 Worker）。

### 20.1 本阶段发行记录

| 项 | 值 |
|---|---|
| 命令 | `.\gradlew.bat obfuscatedJar verifyObfuscatedJar --console=plain` |
| 结果 | BUILD SUCCESSFUL（`变换行为对照通过：64448 项` / `最终发布包强校验通过` / `类加载及成员校验=1403` / `密文篡改认证拒绝=通过` / `混淆静态检查通过：1362 个类已改名`） |
| 产物 | `build/release/yiyiaddon-1.0-beta2-26.1.2.jar`（47 252 289 B） |
| SHA-256 | `213D70E48F796DF72FE3EF64140FBB453864C345918CE4186EEBD76BE4F3C75D` |
| 生产冒烟（Worker 对照） | `production-smoke.ps1 -SeedPocJvmArgs '-Dyiyiaddon.seedpoc.enabled=1','-Dyiyiaddon.seedpoc.workerParity=1','-Dyiyiaddon.seedpoc.exit=1'` → **结论：通过**（正式产物 Worker 已启动、命令行无开发目录、退出后残留 Worker **0**）；Worker RSS 峰值 686 MB / 末次 633 MB / 采样 28 次；客户端退出码 0，耗时 46 s |
| 生产冒烟（Validation） | 同一脚本 + `-Dyiyiaddon.seedpoc.validationRegression=1`（正式 Jar 客户端 + 集成服务端 + 正确种子）→ **全部判定：通过**（见 §20.2）；Worker RSS 峰值 767 MB / 末次 740 MB / 采样 71 次；退出后残留 Worker 0 |

### 20.2 在正式发行产物上重跑验证回归（口径第六十六节）

命令（脚本化、可复现）：

```
powershell -NoProfile -File gradle/production-smoke.ps1 `
    -ModJar build/release/yiyiaddon-1.0-beta2-26.1.2.jar `
    -SeedPocJvmArgs '-Dyiyiaddon.seedpoc.enabled=1','-Dyiyiaddon.seedpoc.validationRegression=1','-Dyiyiaddon.seedpoc.exit=1'
```

结论：**混淆 + 加固后的正式 Jar 上，`SeedValidationState / Evidence / EvidenceGroup / Snapshot / Policy / Service`
没有任何 enum / 反射 / 序列化问题**，整轮判定通过。关键读数（原文摘录）：

```
验证状态 未验证 → 收集中（已收集 4 组独立样本（独立确认 4 组），尚不足以给出结论）
验证状态 收集中 → 证据不足（已观察 11 组，独立确认 11 组 / 覆盖 2 个目标区块 / 解释比例 100.00%，未达阈值）
已验证证据 #1 → 独立确认样本 14 组 / 覆盖 3 个目标区块 / 已观察 14 组 / 解释比例 100.00%；有效样本区块 3

被挖矿容错 —— 先移除 20% 的已确认候选（232 / 1158 个，当前验证「已验证」，独立确认 286 组）
容错证据 #1 → 移除约 20%（232 条指令）：仍为「已验证」，独立确认样本 286 组（基线 286），已确认 926 / 缺失 232
容错证据 #2 → 再移除到约 40%（464 条指令）：仍为「已验证」，独立确认样本 286 组（基线 286），已确认 694 / 缺失 464

策略用例 — 单区块 45/45 全部确认（45 组 / 1 个区块 / 100%）→ 判定「证据不足」（通过）
策略用例 — 只有一个独立确认组（1 组 / 1 个区块 / 100%）→ 判定「收集中」（通过）
策略用例 — 9 组 / 3 个目标区块（正向对照）→ 判定「已验证」（通过）

错误种子 #1 12345 → 证据不足：候选 1142 / 已观察 303 组 / 曾经确认 3 组 / 3 区块 / 比例 0.99%
错误种子 #2 2     → 证据不足：候选 1210 / 已观察 314 组 / 曾经确认 0 组 / 0 区块 / 比例 0.00%
错误种子 #3 0     → 证据不足：候选 1170 / 已观察 300 组 / 曾经确认 3 组 / 3 区块 / 比例 1.00%
错误种子 #4 -7777 → 证据不足：候选 1248 / 已观察 308 组 / 曾经确认 2 组 / 2 区块 / 比例 0.65%
错误种子矩阵 [12345, 2, 0, -7777] 全部未通过验证：是

恢复证据 → 种子改回 20260922 后重新「已验证」：13 组 / 3 个目标区块 / 已观察 14 组 / 比例 92.86%
清理证据 #2（换维度）→ 下界中：验证状态「未验证」，证据 0 条，身份 未建立
清理证据 #3（回主世界重建）→ 重新收集：状态「证据不足」，已观察 9 组 / 曾经确认 8 组

全部判定：通过
```

注意这组数字里有一条值得单独看：**单区块 45/45 会算出 45 个独立组**（该区块那 45 个候选彼此空间不相连），
但**只覆盖 1 个目标区块** → 被「≥3 个目标区块」这一条挡住。这正好从实测层面印证了 §7 的两条门槛
（分组挡矿脉、区块数挡单区域巧合）缺一不可。

`production-smoke.ps1` 的取向值得记一笔：它**刻意不用 Gradle / Loom**，而是按版本 JSON 自己组装
「启动器等价」命令行（官方运行时 + 库 + 游戏 jar + `KnotClient`），并且会断言 Worker 命令行里
**没有** `src` / `build/classes` 之类的开发目录泄漏 —— 也就是它验证的是「用户级真实运行」，
不是「开发环境能跑」。

---

## 21. 未做 / 零变化（口径第五十九~六十二节）

| 项 | 状态 |
|---|---|
| AutoMiner（`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController`、Seed→Baritone） | **零改动** |
| 其它矿物（铁 / 金 / 红石 / 青金石 / 煤 / 铜 / 绿宝石 / 石英 / 远古残骸 / 下界金） | 未扩，仍 Diamond only |
| 下界 / 末地 | 仍无 Seed 预测；Validation 只支持主世界钻石，其它维度一律不支持 |
| 26.2 | 未切换、未修改、未适配 |
| `LOAD_OR_GENERATE` | 仍为 **false** |
| Observation | 仍 **Candidate-only** |
| Renderer | 仍只读不可变快照 |
| Validation | 只消费 Prediction + Observation（不读 Worker `ServerLevel`、不改 `PredictionResult`、不扫远端未加载区块） |

---

## 22. 当前仍存在的限制（如实清单）

1. **验证是经验性一致性，不是 Seed 唯一性证明**（§11）。UI 已用「基于已观察样本」措辞。
2. **自动 SUSPICIOUS 关闭**，因此本阶段无法回答「服务器有没有真的改世界」这类问题。
3. **MISSING 只做记录**：当大量预测位置被挖走 / 是旧区块时，Validation 会停在「证据不足」而不是给结论（这是设计）。
4. **半径 6 压力烟测只拿到目标区块数与缓存上限两项读数**，其余指标未取得有效样本（§19.2）。
5. **单人世界 / 非主世界 / 未填种子**：Validation 不工作（状态停在未验证），这是预期行为。
6. 观测位置若被玩家挖掉且该位置是唯一证据，验证不会因此撤销（锁存），这是容错设计而非缺陷；
   但它也意味着**验证一旦建立，本阶段不再回退**（强冲突条件当前不可达）。

---

## 23. 是否允许进入 235

| 门槛（口径第七十八节） | 结论 |
|---|---|
| Validation 正式可靠（多样本 / 多区块 / 独立组 / fail-closed） | 达成。开发端与**正式发行产物**各跑一次全量验证回归，均为「全部判定：通过」 |
| 正确 Seed 能 VERIFIED | 达成（开发端首次 3 区块 19 组；正式产物 3 区块 14 组 → 已验证） |
| 错误 Seed 不会误 VERIFIED | 本次实测 4 颗全部未通过（12345 / 2 / 0 / -7777 → 证据不足；不声称数学必然） |
| AutoMiner gate 定义完成 | 达成（`mayUseForAutomatedMining()`，仅 VERIFIED 为 true） |
| 233 两个遗留差异收口 | 达成（MISSING 定位+分类；legacy Probe 根因+降级+唯一 gate） |
| ESP 人工验收完成 | 达成（含 Iris 光影开 / 关，两种管线各一组截图） |

**逐条对照 60 项通过标准**：除第 43 项外全部达成。第 43 项「Radius 6 做过压力烟测」为**部分达成**：

- 已达成：压力装置 `SeedRadius6Smoke` 已落地并可一键跑；范围 6 = 169 个目标区块的目标数由服务层算出并被打印；
  预测缓存上限 256 对 169 个目标区块**够用**（未触上限）。
- 未达成：铺开耗时 / 候选规模 / Render entries / FPS / Worker RSS / 内存这六项**没有取得有效读数**，
  原因是本机该开发客户端在这两次尝试中进服后长时间停在「加载地形」（细节与已排除项见 §19.2）。
- 诚实结论：**这是本阶段唯一没跑完的验收项，属性能烟测，不影响验证正确性判定**（帧级渲染开销已在 §19.1 用 7 000~14 000 帧实测）。

| 次级说明 | 结论 |
|---|---|
| 冻结数字 47~50 项 | 达成（开发侧 `workerParity` + 发行侧 `production-smoke.ps1` 双向核过） |
| 发行链 54~60 项 | 达成（compileJava / build / runClient / obfuscatedJar+verifyObfuscatedJar / 正式 Jar 冒烟 / 0 孤儿 Worker） |

**结论：可以进入 235 · Diamond Seed Target → AutoMiner。**

同时提醒 235 必须接受的既有边界：

1. 235 只能消费 `mayUseForAutomatedMining()`，不得自行重定义验证规则；
2. 可疑（SUSPICIOUS）在本阶段**未启用**，235 也不得把它当「假矿」使用；
3. 半径 6 的性能上限（除缓存容量外）**尚未实测** —— 若 235 打算把范围上限放开，需先补完 §19.2。
