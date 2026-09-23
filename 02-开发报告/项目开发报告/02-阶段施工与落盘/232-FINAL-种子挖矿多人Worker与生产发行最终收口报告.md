# 232-FINAL · 种子挖矿多人 Worker 与生产发行最终收口报告

> **阅读优先级**：本文件是种子挖矿「多人 Worker + 生产发行」的**唯一优先基线**。
> 233 及以后开发**先读本文件**；需要过程证据时再查同目录历史报告：
> `232-追加-种子挖矿正式化第四阶段报告-本地隔离WorldgenWorker与多人服务器预测闭环.md`（232 · 技术闭环过程）、
> `232P-追加-种子挖矿真实用户生产发行验收报告.md`（232-P · 生产发行过程）、
> `02-开发报告/项目开发报告/02-阶段施工与落盘/232P-证据/`（57 个取证文件）。
>
> 本文件由 232 与 232-P 重新整理，**不是拼接**：过时结论已剔除，前后矛盾已裁决（见【十六】），
> 同一组数字只出现一次。凡未实机验证的内容一律标注「未实测」。
>
> 冻结日期：2026-09-23 ｜ 分支 `master` ｜ Minecraft `26.1.2` ｜ 模组版本 `1.0-beta2`
> 正式发行产物：`yiyiaddon-1.0-beta2-26.1.2.jar`，SHA-256 `3cd0b46332cd21f0296bbc7ba61efa35b499a7724baaa31706f96c168bcb0905`
> （本文件全部结论都产生在**这个哈希的产物**上，或产生在它之前的同源构建上并已复跑确认）

---

## 零、最终状态（先给结论）

| 项 | 结论 |
| --- | --- |
| 232 技术闭环 | **通过** |
| 232-P 生产发行验收 | **通过** |
| Multiplayer Worker 架构 | **正式定案** |
| Worker 宿主形式 | **独立 JVM 进程**（正式采用） |
| 纯 Client `ServerLevel` 替代路线 | **永久停止研究**（231 已给出硬依赖反面证据） |
| 同 JVM Worker | **不再考虑**，除非未来出现新的生产级硬阻塞证据 |
| 正式 Predictor 算法 | **未重写**（`seed/prediction`、`seed/worldgen` 算法零改动） |
| 目标远程服务器 | **无需安装任何插件 / Mod** |
| Worker 与远程服务器 | **不连接、不知道地址** |
| Predictor 读取远程真实 Chunk | **否** |
| Host ChunkMap Query | **恒 0**（全部用例） |
| 正式 release Jar production smoke | **通过** |
| 用户安装形态 | **单个 yiyiaddon Mod 文件** |
| 普通用户额外依赖 | **无**（不需要额外 Java / Worker / BAT / CMD / 服务端插件） |
| 是否允许进入 233 | **允许** |

---

## 一、最终架构

```
Minecraft Client（单人 / 多人均可）
    ↓
SeedMiningService            ← 只负责业务状态：当前 Seed、维度、发请求、收结果、丢弃过期响应
    ↓
SeedWorldgenWorkerClient     ← Worker 生命周期 + IPC（懒启动 / 握手 / 停机 / 看门狗）
    ↓  127.0.0.1 回环 TCP，一行一条 JSON，协议带 requestId
独立本地 Worker JVM          ← java.home/bin/javaw.exe，自管 runtime / world / logs 目录
    ↓
Vanilla 26.1.2 ServerLevel   ← 只提供 worldgen 环境，不 tick 玩法、不开公网端口
    ↓
DiamondSeedPredictor（229 冻结口径）
    ↓
PredictionResult →（DTO 还原为正式模型）→ Client UI
```

**远程服务器与本架构的关系（正式定案）**

| 事项 | 结论 |
| --- | --- |
| 是否需要在目标服务器装插件 | 否 |
| 目标服务器是否参与 Worker | 否 |
| 目标服务器是否向 Worker 提供真实 Chunk | 否 |
| Worker 是否知道目标服务器地址 / 端口 / 会话令牌 | **否**（协议里根本没有这些字段） |
| Worker 使用的输入 | Minecraft 版本 + 用户填写的 Seed + 维度 + 目标 ChunkPos + 本机 Vanilla 资源 |

**模块边界与依赖方向（单向，禁止反向）**

- `com.yiyiaddon.seed.service`（业务状态）→ `com.yiyiaddon.seed.worker.client`（进程 + IPC）→ 独立进程
- `com.yiyiaddon.seed.worker.protocol{,.dto}`：纯传输层，被两侧共享，**不含业务语义**
- `com.yiyiaddon.seedworker`（Worker 进程侧）：可依赖 `seed.prediction` / `seed.worldgen`
- 正式 Predictor **不得**反向依赖 worker client / IPC；UI **不得**直接操作 WorkerResponse JSON
- `dev.seedpoc` 只做 Oracle / 回归 / 诊断，正式代码不得 import `dev`

---

## 二、为什么必须 Worker（231 结论，浓缩版）

231 已证明（负面但有效，不需要重做）：

- 26.1.2 Vanilla worldgen 主链是 `WorldGenRegion → ServerLevel`，
  Surface / Carvers / Features 等 API 对 `ServerLevel` / `ServerLevelAccessor` 是**硬依赖**；
- 因此 `ClientLevel + RegistryAccess + ResourceManager` 的组合**不能**在不改 Vanilla 调用模型的前提下替代它。

**最终决策**：不再消灭 `ServerLevel`，而是在本机隔离进程中**提供真正的 `ServerLevel`**。

**明确禁止（永久）**：`ClientWorldgenEnvironment`、伪 `WorldGenRegion`、`ServerLevel` 适配器、
纯 Registry 替换、继续包装 `WorldgenEnvironment` 接口 —— 这些路线全部作废，不再开新轮次。

---

## 三、正式算法冻结回归（229/230/232 一致，不再变动）

Predictor 算法在本阶段与 232-P 阶段**均未改动**，下列数字是后续所有阶段的对照基准。

### 3.1 Seed 20260922 · Overworld · 十目标（Worker 与单人 Oracle 逐 BlockPos 一致）

| Target | (0,0) | (1,0) | (0,1) | (1,1) | (-1,0) | (0,-1) | (-1,-1) | (2,2) | (3,-1) | (-2,3) | 合计 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 候选数 | **45** | 9 | 29 | 21 | 22 | 27 | 21 | 18 | 18 | 33 | **243** |

`(0,0) = 45` 当前分类：**0 deterministic / 0 schedule-sensitive / 45 unresolved**（保持既有口径）。

### 3.2 Seed 12345 · Overworld

| Target | (0,0) | (-1,-1) | (-25,17) | (120,-130) | 合计 |
| --- | --- | --- | --- | --- | --- |
| 候选数 | 31 | **29**（其中 schedule-sensitive = **7**） | 24 | 26 | **110** |

### 3.3 Seed 2 · Overworld · 冲突用例

| 指标 | 值 |
| --- | --- |
| Target | (-400,380) |
| 总候选 | **23** |
| SCHEDULE_SENSITIVE | **1** |
| UNRESOLVED | **22** |
| DETERMINISTIC | **0** |
| 争议位置 | `(-6385,-59,6085)` → **SCHEDULE_SENSITIVE** |

### 3.4 parity 的粒度

Worker 与单人 Oracle 的对比**不是只比数量**：每个 `PredictedOre` 的
`BlockPos` / `OreType` / `PredictionCertainty` / `OreSource` / 来源视图 / 冲突写入者信息**逐项一致**；
`success=true, ores=[]`（这个区块确实没有钻石）与 `success=false`（这次预测不成立）在 IPC 两端严格区分。

---

## 四、真正 Multiplayer 验收（最终事实）

环境：Minecraft 26.1.2 本机 Dedicated Server（Seed 20260922，无 C2ME / ModernFix 等改变 worldgen 的 Mod），
真实客户端连接 `localhost`。

| 检查项 | 实测结果 |
| --- | --- |
| `Minecraft#getSingleplayerServer()` | **null** |
| Seed Mining 状态 | **READY（已就绪）** |
| 20260922 / (0,0) | **45**（与单人 Oracle 一致） |
| 远端未加载 Chunk（|x| 极大，客户端从未收到） | **预测成功** |
| 该目标是否在 `ClientChunkCache` 中 | **不存在** |
| 预测完成后该区块是否被加载 | **仍未加载** |
| Host ChunkMap Query 增量 | **0** |
| 客户端是否读取远端真实 BlockState 辅助预测 | **否**（静态审计零命中 + 运行时反证） |

> 注意：多人验收那次用的远端目标与「Seed 2 冲突用例」不是同一个体，因此候选数不同属正常，
> 不要拿它去对 23/1/22/0（见【十六】冲突裁决第 9 条）。

---

## 五、232-P 生产发行验收（完整记录，不依赖其它文件）

**定义**：Production Packaging Smoke —— 从 `buildRelease` 的正式产物出发，用**启动器等价命令**
（版本 JSON 的 `libraries[].downloads.artifact.path` + `net.fabricmc.loader.impl.launch.knot.KnotClient`）
在**一次性实例目录**里启动真实客户端；模组**只以最终 jar 形式**存在。
不使用 `gradlew runClient`，不使用 Loom / Gradle 开发类路径（脚本 `gradle/production-smoke.ps1` 内置 7 条禁用路径断言）。

| # | 检查项 | 结果 |
| --- | --- | --- |
| 1 | 正式 release Jar-only 测试是否通过 | **通过**（14 次冒烟，全部在 `build/release` 产物上） |
| 2 | 是否依赖 Gradle | **否**：命令行的 classpath 完全由版本 JSON 组装（83 个条目，其中 7 条按 maven 坐标推导），无 Gradle |
| 3 | 是否依赖 `src` / `build/classes` / Loom 输出 | **否**：Worker 命令行 10,606 字符**零命中**禁用路径 |
| 4 | Worker 的类是否来自最终 release Jar | **是**：`SeedWorkerMain` / `SeedWorkerHost` / `SeedWorkerIpcServer` / `SeedWorkerSessions` / `SeedWorkerArgs` 均由 `Class.getProtectionDomain().getCodeSource()` 指向该 jar |
| 5 | 正式生产环境能否创建 `ServerLevel` | **能**（Vanilla Bootstrap → `DedicatedServer` 形态宿主 → `ServerLevel` 就绪） |
| 6 | `OPEN_SESSION` | **通过** |
| 7 | `PREDICT_DIAMOND` | **通过** |
| 8 | 生产 smoke 数字 | 20260922 (0,0) = **45**；Seed 2 (-400,380) = **23 / 1 / 22 / 0**；争议格仍 SCHEDULE_SENSITIVE |
| 9 | 是否在真实大量 Mod 环境测过 | **是**：真实用户的 PCL 实例（103 个 mod jar，Fabric 报告加载 286 个模组条目，含 c2me / modernfix / sodium 等）——数字与干净环境**完全相同** |
| 10 | Host ChunkMap Query 是否仍为 0 | **是**（生产与多人环境均 0） |
| 11 | 是否有孤儿进程 | **否**：14 次冒烟退出后残留 Worker 进程一律 **0**，且每次的 PID 生命周期都有记录 |
| 12 | 空格 / 中文路径 | **通过**：三个含空格 + 中文的实例目录（`D:\mcaddon\种子 Worker Production Test*`）全部正常启动、会话正常、预测 45 |
| 13 | 超时负路径 | **实测触发**（见【十】） |
| 14 | 崩溃恢复 | **实测通过**（强杀 Worker → 捕获失败 → 下一次请求自动重启一次 → 再预测仍 45） |
| 15 | 用户最终需要几个文件 | **1 个**：`yiyiaddon-<版本>-<MC 版本>.jar`（release zip 内也只有这一个 jar） |

**生产实测工程指标（默认 `-Xmx1024m`）**

| 指标 | 值 |
| --- | --- |
| Worker 冷启动耗时 | 11,251 ms（全新 runtime 目录） |
| 已有宿主复用启动 | 4,823 ~ 6,425 ms |
| 103-Mod 环境启动 | 6,117 ms（Worker 与客户端 Mod 数量无关） |
| 首次预测（含离线管线构建） | 约 4.0 ~ 4.4 s |
| 相邻 Target 预测 | 19 ~ 22 ms（命中离线缓存） |
| Worker RSS | 峰值 517 ~ 695 MB，末次采样 507 ~ 634 MB（Windows 任务管理器口径） |
| 客户端 JVM 增量 | 可忽略（Worker 是独立进程） |

---

## 六、发布流水线：混淆 / 加固（重要生产修复）

### 6.1 原问题

原发布链是「**加固 → 混淆**」：加固（自研 ASM 9.9.1 字符串加密 + 基本块重排 + 加密分派键）
会把常量改写为 `java.lang.invoke.ConstantDynamic`；ProGuard 7.9.1 / core 9.3.2 在**初始化阶段**遇到它就抛
`UnsupportedOperationException: proguard.classfile.util.DynamicClassReferenceInitializer does not support
proguard.classfile.constant.DynamicConstant`，整条 `buildRelease` 必然失败 ——
即「正式发行版从未真正走通过发布流水线」。此问题只在**发布构建**暴露，日常 `build` / `runClient` 看不见。

### 6.2 最终顺序（固定，不得调整）

```
compileJava
  → jar（未加固的普通产物）
  → ProGuard 混淆（injars = 未加固 jar + proguard-rules.pro）
  → 字节码加固（hardenJar，输入 = 混淆产物）
  → obfuscatedJar（只替换 .class，资源与 fabric.mod.json 原样复制）
  → verifyObfuscatedJar（入口 / 资源 / 加载 / 加密常量 / Gson 契约 / 跨进程入口 / 调试探针检查）
  → releaseZip → buildRelease
```

两种保护效果不变（产物依旧是「已改名 + 加密常量」），ProGuard 只见得到普通常量；
代价只有一个：加固运行期类 `com.yiyiaddon.utils.ReleaseProtection` 必须在混淆后保持原名与 `bootstrap` 签名。

### 6.3 跨进程入口要保留什么

```proguard
-keep class com.yiyiaddon.seedworker.SeedWorkerMain { public static void main(java.lang.String[]); }
-keepnames class com.yiyiaddon.seedworker.**
-keep class com.yiyiaddon.utils.ReleaseProtection {
    public static java.lang.Object bootstrap(java.lang.invoke.MethodHandles$Lookup, java.lang.String, java.lang.Class, java.lang.String, long, long, long, long);
}
```

- **类名**必须稳定（唯一以字符串跨进程寻址的类型集合），**`main(String[])` 签名**必须稳定；
- 除此之外 `seedworker` 包**成员照常混淆** —— 实测发布产物映射（`05-混淆文件备份/…/mapping.txt`）：
  `SeedWorkerArgs -> SeedWorkerArgs`、`SeedWorkerHost -> SeedWorkerHost`（类名不变），
  而 `parse(String[]) -> a`、`watchParent(SeedWorkerArgs) -> a`、`writeHandshake(...) -> a`、
  字段 `args -> a` / `sessions -> a` / `host -> a`（成员全部改名）；
- 因此**没有**用「整个 Worker 包 `-keep { *; }`」这种明显削弱保护的做法，只用了 `-keepnames`（保名不保内容）。

### 6.4 协议稳定性由什么保证

- **操作码 / 错误码**（`OP_HELLO`、`ERROR_BAD_TOKEN` 这类 `public static final String`）：加固工具按设计
  **保留** public / package 级字符串常量不做加密（统计字段 `publicOrPackageStringConstantsKept = 666`，
  原因是常量折叠会让内联点与常量本体脱钩）→ 协议文本始终是源码字面量；
- **JSON 键名等方法内联字面量**：由加固加密（`stringLoadSitesEncrypted = 19780`），运行期同一份代码解密回原文
  → 传输文本不变；
- **字段名**（如 `OP_HELLO` 这个标识符本身）会被 ProGuard 改名，但**值**不变；协议只依赖值，不依赖字段名；
- **跨进程传递的枚举**：`OreType` / `PredictionCertainty` / `OreSource` 在 DTO 里是 `String`，
  值取自枚举名，而 `proguard-rules.pro` 用
  `-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); public static final <fields>; }`
  保住枚举常量名与 `valueOf` → 两端枚举名一致；
- IPC **不使用** Java 序列化（禁止 `ObjectOutputStream` / `Serializable`）。

### 6.5 校验与实测统计

`verifyObfuscatedJar` 会断言：5 个跨进程入口类存在 + 反射取到 `SeedWorkerMain.main(String[])`；
产物内不含源码 / mapping / 调试探针；资源与未混淆时**逐字节相同**；
隔离 `URLClassLoader` 能加载全部类；Gson 字段契约往返一致；被改名的类数不为 0（否则判「混淆未生效」）。

**最终产物实测统计**（`05-混淆文件备份/1.0-beta2/02-字符串与控制流加强版-26.1.2-3cd0b463/`）：

| 指标 | 值 |
| --- | --- |
| 类总数 / 已改名 | 1364 / **1324**（未改名的 40 个 = 跨进程入口 5 + Fabric 入口 / Mixin / `ReleaseProtection` 等契约类） |
| 加固处理类数 / 契约排除 | 1333 / 31 |
| 唯一字符串常量加密 | **18,747** 个（19,780 个加载点） |
| 加密分派点 | **34,153** |
| 字符串拼接参数加密 | 2,922 |

**结论：混淆加固后的正式产物在生产环境实测正常运行** ——
本报告所有生产冒烟用的就是 `05-混淆文件备份/1.0-beta2/02-字符串与控制流加强版-26.1.2-3cd0b463/` 里
那份 `yiyiaddon-1.0-beta2-26.1.2.jar`（SHA-256 与备份记录一致）。

---

## 七、生产 Bug 记录：Worker 读错 `fabric.mod.json`（版本 0.19.5）

| 项 | 内容 |
| --- | --- |
| 现象 | 生产 smoke 中所有预测失败：候选恒为 0；客户端日志出现 `模组版本不一致：客户端 1.0-beta2，Worker 0.19.5` |
| 根因 | Worker 用 `getResourceAsStream("/fabric.mod.json")` 取「类路径上第一个命中」——生产环境里第一个命中的是 **Fabric Loader 自己的元数据**（Loader 版本 0.19.5），不是 yiyiaddon 的 |
| 为什么开发环境不暴露 | `runClient` / Loom 下模组资源目录排在类路径靠前，第一个命中恰好是 yiyiaddon 自己的元数据 → 开发环境**永远看不见** |
| 为什么生产才暴露 | 生产是 jar-only classpath，Loader 的元数据先出现 → 只有真实用户环境才会踩到 |
| 修复 | `SeedWorkerMain.modVersion()` 改为先读**本类所在 jar**（`getProtectionDomain().getCodeSource()`）里的 `fabric.mod.json`，并校验 `"id":"yiyiaddon"`；仅在取不到时回退遍历类路径条目逐条校验 id |
| 修复后复跑 | 生产 smoke 重跑通过：20260922 (0,0) = 45、Seed 2 = 23/1/22/0，与开发环境数字一致 |
| 硬性约束（后续版本必须遵守） | **禁止**再用「类路径第一个 `fabric.mod.json`」这种不确定方式取自身版本；取自身元数据一律以「本类所在 jar」为准 |

---

## 八、Worker 启动体验

| 项 | 最终行为 |
| --- | --- |
| Java 可执行文件 | **`<java.home>/bin/javaw.exe` 优先**（Windows GUI 子系统，**不弹控制台黑窗**），缺失时回退 `java.exe`；非 Windows 用 `java` |
| 为什么用 javaw | 普通用户点一次预测不该看到持续 CMD 窗口；Worker 的调试信息本来就写独立日志，不依赖控制台 |
| 启动方式 | `ProcessBuilder(List<String>)`（参数列表，**不拼接命令字符串**），gameDir / 路径含空格与中文均实测通过 |
| 使用的 Java | 当前 Minecraft 正在使用的运行时（`java.home`），**不要求用户另装 Java**；`resolveJavaExecutable()` 会先确认文件可执行 |
| 默认堆 | `-Xmx1024m`（见【九】）；`-Xms256m` |
| Worker 日志 | `<gameDir>/yiyiaddon/seed-worker/26.1.2/seed-worker-26.1.2.log`（独立文件，不污染客户端日志） |
| 客户端日志只记 | 启动 / 握手 / 会话 / 请求 / 响应 / 崩溃 / 关闭 等事件（不刷 Vanilla Server 日志、不刷每个 BlockPos） |
| 首次启动 UI | 持续显示「正在启动本地世界生成计算器…」→ 就绪后「已就绪」→ 预测时「正在预测当前区块…」；按钮防重复点击，不会创建第二个 Worker |
| 启动时机 | **懒启动**：功能开启 + Seed 合法 + 支持维度 + 首次请求预测时才启动（不是 Minecraft 一开就吃内存） |
| 退出清理 | SHUTDOWN → 短超时 → `destroy()` → 再超时 `destroyForcibly()`；实测退出后无孤儿进程 |

---

## 九、Heap 最终结论

同一条件（正式产物、`service` 装置、Seed 20260922 + Seed 2、连续目标）下实测：

| `-Xmx` | 是否启动 | 是否完成固定预测 | 是否 OOM/崩溃 | 峰值 RSS | 末次 RSS | 连续预测稳定性 | 覆盖深度 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 256m（下限探测） | 是 | 是（45 / 23） | 否 | 559 MB | 480 MB | 稳定 | 6 目标 |
| 768m | 是 | 是 | 否 | 653 MB | 643 MB | 稳定 | 15 目标 |
| **1024m（最终默认）** | 是 | 是 | 否 | 695 MB | 634 MB | 稳定 | 15 目标 |
| 1536m | 是 | 是 | 否 | 690 MB | 645 MB | 稳定 | 15 目标 |
| 2048m | 是 | 是 | 否 | 683 MB | 536 MB | 稳定 | 15 目标 |
| 64m（负路径） | 失败（预期） | — | 启动即失败，**优雅报错** | — | — | 客户端不崩 | 1 次 |

**最终默认：`-Xmx1024m`。理由**

1. 实测峰值约 695 MB，1024m 留出约 30% 余量，可覆盖冷启动 + 首次预测 + 15 目标连续预测；
2. 768m 虽然当次全通过，但峰值 653 MB 只剩 ~15% 余量，遇到更大 Mod 包 / 后续 26.2 模型风险偏高；
3. 2 GB 没有必要（实测峰值离 2 GB 很远），对大量真实用户是白占内存；
4. 256m 能通过但只跑了 6 目标，**不足以**作为默认值；
5. 关键原则：**不为省内存牺牲正确性** —— 内存不足时必须给出明确错误（见【十】），不允许静默 OOM。

注：RSS 为任务管理器口径（含 JVM 元空间、栈、本地缓冲），不等于堆占用；采样窗口与运行场景不同会有波动，
上表同一列为同装置同场景可比数据。

---

## 十、超时与失败路径（fault injection 实测）

诊断属性（仅用于开发/验收，不改正式默认值：启动 120 s、预测 180 s）：

- `-Dyiyiaddon.seedworker.startupTimeoutMillis=…`
- `-Dyiyiaddon.seedworker.predictTimeoutMillis=…`
- `-Dyiyiaddon.seedworker.maxHeapMb=…`

| 注入 | 实测行为 |
| --- | --- |
| 启动超时 = 1 ms | 状态**不会**永久停在 STARTING；进入失败态并给短中文提示；本批请求被清理；Worker 进程被回收（无残留）；有尝试次数上限与退避，**不会无限快速重启** |
| 预测超时 = 1 ms | 状态**不会**永久停在 PREDICTING；请求被清理；执行三级停机（SHUTDOWN → destroy → destroyForcibly）后 Worker 被回收；后续可恢复 |
| `maxHeapMb = 64`（内存不可满足） | Worker 启动即失败，客户端**不崩**；显示「本地世界生成计算器异常」类短中文；完整技术原因（退出码、日志路径、异常栈）只进日志 |

界面上普通用户看不到 `Java stacktrace` / `ServerLevel` / `WorldGenRegion` 等术语；
`SeedWorkerException.userMessageCn()` 负责把错误码翻译成一句可读中文。
协议侧全部 **fail-closed**：协议版本不符 / Minecraft 版本不符 / 令牌错 / 响应畸形 / 超时 / Worker 崩溃 /
未知枚举 → 一律失败，绝不返回「空矿 + 成功」。

---

## 十一、普通用户最终体验

**用户只需要**：`yiyiaddon-<版本>-<MC 版本>.jar`（当前即 `yiyiaddon-1.0-beta2-26.1.2.jar`）。

**不需要**：第二个 Worker jar、第二套 Java / JRE、CMD、BAT、`JAVA_HOME`、本地服务器、单人世界、
服务端插件、Gradle、项目源码、`build/classes`、启动器 JVM 参数。

**正常流程**：安装 / 更新 Mod → 放入 `mods` → 启动 Minecraft → 进入服务器 → 填写 Seed → 使用。

**计算器（Worker）对用户完全透明**：在需要时后台自动启动、自动握手、自动通信、自动退出；
用户界面里它只叫「**本地世界生成计算器**」，不出现 `ServerLevel` / IPC / PID / Socket 等字眼
（这些只在日志与开发诊断里）。

发行文件核对：`build/release/` 只包含 `yiyiaddon-1.0-beta2-26.1.2.jar`（及同名 zip）；
Worker 是**同一个 jar** 里的类，不存在用户需要人工管理的第二个文件。

---

## 十二、现有用户升级风险

| 关注点 | 结论 |
| --- | --- |
| 种子挖矿关闭（OFF） | Worker **完全不启动**（懒启动），对现有用户行为零影响 |
| AutoMiner | 相关类零改动（`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController` 未触碰），行为不变 |
| 旧配置（AutoMiner / Mining / 其它） | **不要求重置**；种子挖矿是新页签 / 新配置项，走增量读取 |
| 个人模式（`personalMode = true`） | Seed 页签照旧隐藏；隐藏时不会留下不可控的预测任务 |
| Worker 运行时目录 | 由 Worker 自管（`<gameDir>/yiyiaddon/seed-worker/<版本>/`，含 512 MB 上限自动重建），不碰玩家 `saves/` |
| 单人世界 | 正式路径**统一走 Worker**，不再保留「单人一套、多人一套」的双正式逻辑；旧单人 `ServerLevel` 路径只作为 `dev.seedpoc` Oracle 存在 |

---

## 十三、已知限制

### 13.1 已解决（从「当前限制」中移除，不要再引用为限制）

| 曾经的问题 | 解决方式 |
| --- | --- |
| 发布流水线必然失败（ProGuard 读不了 `ConstantDynamic`） | 固定顺序「先混淆、后加固」+ 跨进程入口 keep + 发布校验断言（【六】） |
| ProGuard 改名跨进程入口 → 生产按类名启动必挂 | `-keepnames` + `verifyObfuscatedJar` 断言（【六.3】） |
| Worker 读错 `fabric.mod.json` → 生产预测恒 0 | 改为读自身 jar 元数据 + id 校验（【七】） |
| 失败时把 Java 异常栈暴露给普通用户 | `userMessageCn()`：界面短中文，技术原因只进日志（【十】） |
| Windows 下弹出持续控制台窗口 | 改用 `javaw.exe`（【八】） |
| 默认堆 2 GB 偏大 | 实测后降为 1024m（【九】） |
| 「生产实例未实机」「空格路径未测」「超时未实机」 | 232-P 已全部实机（【五】【十】） |

### 13.2 仍存在的限制（必须保留声明）

| 限制 | 说明 |
| --- | --- |
| Minecraft 版本 | 当前只支持 **26.1.2**；Worker 握手会拒绝版本不符 |
| 矿物种类 | 当前只有 **主世界钻石**（无铁/金/红石/青金/煤/铜/绿宝石/下界系等） |
| Seed 正确性 | 预测只说明「26.1.2 Vanilla + 该 Seed 会得到这些候选」，**不代表**远程服务器 Seed 真的等于它；UI 不得显示「Seed 已验证」 |
| 服务器世界生成规则 | 自定义 datapack / 插件 / Mod worldgen / 旧区块 / 版本迁移 **均未验证**；UI 保持「预测模型：Minecraft 26.1.2 原版主世界」并提示尚未验证 |
| 下界 / 末地 | 不支持（进入下界即关闭会话，不做 Overworld 模型的越界调用） |
| 启动器覆盖 | **实测 1 个**：PCL 产物的等价启动命令跑通全流程；官方启动器 / HMCL / Prism 仅**静态审计**（审计点：`java.class.path`、`ModContainer` 来源、`gameDir`、`java.home`，未发现 dev-only 假设）——不得表述为「全部已支持」 |
| 干净新机 | 未在无 Minecraft / 无缓存的干净机器上实测「装机 → 首次启动」 |
| Server A→B | 走与「退出服务器」相同的收口（停 Worker + 清会话 + 清结果），但**未单独构造 A→B 连续用例** |
| 宿主初始化期出网 | 采集到一次来自 Vanilla 宿主的短促出网（断网模拟不影响可用性），**具体目标未定位** |
| 宿主短暂监听 | 就绪后监听已停用（套接字采样确认），但初始化瞬间存在短暂的 Vanilla 监听窗口 |
| `maxHeapMb < 256` | 未实测（诊断属性可降到更低，未做矩阵） |
| 平台 | 仅 Windows 实测通过；macOS / Linux 未验（协议层与 `resolveJavaExecutable()` 未写死 Windows） |
| 内存读数 | RSS 为任务管理器口径，不是精确堆统计 |

---

## 十四、后续阶段边界（233）

**下一阶段**：《233 · 实际 Chunk Observation + 预测钻石世界渲染》。

**233 允许**：读取「已加载的客户端 Chunk」的真实 BlockState；把 Seed 预测坐标渲染到世界；开始接入 Observation 状态。

**233 不允许**（除非发现 232-FINAL 的硬回归并另行说明）：
改 Predictor 算法、改 Worker 架构、接 AutoMiner、扩其它矿物、开始 26.2、开始未知 Seed 破解。

**232-FINAL 之前与之后都没做（继续保持）**：Observation 状态实际接入（`CONFIRMED` / `MISSING` / `SUSPICIOUS` 未启用）、
任何 Renderer（ESP / Box / Tracer / 标签）、SeedValidation（地形比对 / Biome 指纹 / 自动破解）、AutoMiner 联动。

---

## 十五、文档地图（FINAL 优先）

| 文件 | 定位 |
| --- | --- |
| **本文件（232-FINAL）** | **唯一优先基线**，233 及以后先读这一份 |
| `232-追加-种子挖矿正式化第四阶段报告-本地隔离WorldgenWorker与多人服务器预测闭环.md` | 232 历史过程（含 231 遗留处理、IPC/生命周期设计与 §102 六十七项对照） |
| `232P-追加-种子挖矿真实用户生产发行验收报告.md` | 232-P 历史过程（含 14 次冒烟逐次记录、附录 A 文件清单与附录 B 证据索引） |
| `02-开发报告/项目开发报告/02-阶段施工与落盘/232P-证据/` | 生产冒烟取证（命令行 / PID 生命周期 / RSS / 装置报告 / Worker 日志） |
| `02-开发报告/项目开发报告/02-阶段施工与落盘/232-证据/` | 232 阶段取证（内存采样、协议负路径） |
| `gradle/production-smoke.ps1` | 生产冒烟脚本（可复跑；不进正式产物） |
| `05-混淆文件备份/<版本>/…` | 混淆映射与产物摘要（**仅内部还原堆栈用，禁止外发**） |

后续开发若修改文档：**新结论写进 232-FINAL（或更新的 FINAL 版本）**，历史报告只做归档，不再追改。

---

## 十六、数字冲突裁决记录（过程报告 → 最终采用值）

| # | 冲突 / 过时点 | 来源 | 最终采用值（依据） |
| --- | --- | --- | --- |
| 1 | Worker 默认堆：232 为 `-Xmx2048m`，232-P 为 `-Xmx1024m` | 232 §十二 vs 232-P §二.10 | **1024m**（Heap 矩阵实测：峰值 695 MB，余量充足；代码中 `WORKER_HEAP_MAX_MB` 默认已是 1024） |
| 2 | Java 可执行文件：232 用 `java.exe` | 232 §七 vs 232-P §八 | **`javaw.exe` 优先**，回退 `java.exe`（避免控制台窗口；生产冒烟按此实测） |
| 3 | 失败提示：232 直接显示技术串 | 232 §十三 vs 232-P §十 | **界面短中文 + 技术原因只进日志** |
| 4 | 生产实例：232 标「推断 / 未实机」 | 232 §二十限制 1 | **已实机**（232-P 生产冒烟；232 报告已同步改写为「已由 232-P 补齐」） |
| 5 | 超时触发：232 标「未实机」 | 232 §二十限制 2 | **已实机**（fault injection：1 ms 注入） |
| 6 | 含空格路径：232 标「未测」 | 232 §二十限制 3 | **已实测通过**（三个含空格 + 中文实例） |
| 7 | 打包检查基准：232 §十七基于 `build/libs`（`jars` 任务产物） | 232 §十七 | 正式发行基准是 **`build/release`（`buildRelease` 流水线）**；232 阶段该流水线因 `ConstantDynamic` 必挂，232-P 修复后才产出可用发行物 |
| 8 | Worker RSS 区间：232 记 537~627 MB，232-P 记 517~695 MB | 232 §十四.2 vs 232-P §二.11 | 两者**场景不同**（开发 Knot + `-Xmx2048m` vs 生产 jar-only + `-Xmx1024m`，采样窗口不同），非矛盾。最终采**生产形态区间 517~695 MB**，并注明口径 |
| 9 | 远端目标候选数：多人验收里出现过 37 | 232 §十五.2 | 那是 Seed 20260922 下的另一个远端目标，**与 Seed 2 的 (-400,380)=23 不是同一用例**；冻结数字以【三】为准 |
| 10 | 供应商库解析：smoke 首次报「找不到 KnotClient」 | 232-P 过程 | 版本 JSON 的 7 条库无 `downloads.artifact`，脚本改为按 maven 坐标推导（`derived=7`）；最终脚本已固化该逻辑 |

---

## 十七、验收判定

| 判定 | 结果 |
| --- | --- |
| 232 · 28 条通过标准 | **全部满足**（其中第 3 / 9 / 13 / 15 / 16 五条关键项均实机取证） |
| 232-P · 14 条发行门槛 | **全部满足** |
| 是否允许进入 233 | **允许** |

**一句话总结**：

> 普通 Multiplayer Client（`getSingleplayerServer() == null`）+ 远程服务器无插件 + 目标 Chunk 未加载 +
> 用户提供 Seed，仍然可以得到与单人 Oracle **逐 BlockPos 完全一致**的钻石 `PredictionResult`，
> 且该能力在**正式发行 jar** 的生产 Fabric 环境、真实 103-Mod 环境、含空格/中文路径下均已实测通过。
> **多人宿主问题正式结案。**

---

**本阶段到此停止，不新增功能、不进入 233。**
