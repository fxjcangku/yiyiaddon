# 232 · 种子挖矿正式化第四阶段报告
## —— 本地隔离 Worldgen Worker 与多人服务器预测闭环

> **⚠ 历史过程报告**：最新基线与**唯一优先参考**是
> 《232-FINAL · 种子挖矿多人 Worker 与生产发行最终收口报告》（`232-FINAL-种子挖矿多人Worker与生产发行最终收口报告.md`）。
> 本文件保留为过程证据；其中已被后续阶段改写的结论（默认堆、Java 可执行文件、失败文案、
> 「未实机」三项等）**一律以 FINAL 为准**。

> 项目：`D:/mcaddon/yiyiaddon` ｜ 分支：`master` ｜ Minecraft：`26.1.2` ｜ 模组版本：`1.0-beta2`
> 本报告全部数字均为**本机实机运行实测值**（每一条都注明了产出文件 / 日志位置）；
> 凡未实机验证的项，一律在【二十、已知限制】里显式标注「未实机」。
> **配套报告**：《232-P · 真实用户生产发行验收》（`232P-追加-种子挖矿真实用户生产发行验收报告.md`）——
> 它把本报告里标着「未实机 / 推断」的三项（生产实例、超时实触发、含空格路径）全部补成了实机证据，
> 并新增低内存 Heap 矩阵与发行门槛验收；本报告已同步标注。口径第 102 节六十七项逐条对照见【二十一·补】。

---

## 一、阶段结论（先给判定）

| 项 | 结果 |
| --- | --- |
| 本阶段核心目标（多人客户端 `getSingleplayerServer() == null` 下仍能得到与单人 Oracle 逐 BlockPos 一致的 PredictionResult） | **达成** |
| 关键验收第 3 条（Worker 能提供真实 Vanilla ServerLevel） | 通过（`seedWorkerProbe` + 三个实机装置） |
| 关键验收第 9 条（Worker vs 单人 Oracle 逐 BlockPos 一致） | 通过（15 个目标全部「完全一致」） |
| 关键验收第 13 条（Dedicated Multiplayer `getSingleplayerServer() == null`） | 通过（实机专用服务器） |
| 关键验收第 15 条（Dedicated 下 20260922 (0,0) = 45） | 通过 |
| 关键验收第 16 条（远端未加载 Chunk 可预测） | 通过（客户端 ChunkCache 内没有该区块） |
| 232 二十八条通过标准 | 逐条判定见【二十二】 |

一句话结论：**多人宿主问题正式结案** —— 正式预测宿主改为「本机隔离的 Vanilla Worker 进程」，
目标服务器不需要装任何东西，客户端不读目标服务器的真实 Chunk，Worker 也不连目标服务器，
而结果与 229/230 冻结的 Singleplayer ServerLevel Oracle **逐 BlockPos、逐 certainty/source 完全一致**。

---

## 二、231 失败结论复核（不许重试旧路线）

231 报告（`231-追加-种子挖矿正式化第三阶段报告-多人服务器离线预测环境解耦.md`）的结论是类型契约级阻塞：

* 26.1.2 的 `WorldGenLevel extends ServerLevelAccessor`，而 `ServerLevelAccessor#getLevel()` 返回 `ServerLevel`；
* 因此 `OfflineChunkRegion extends WorldGenRegion` 的构造链上**必须**拿到一个真实 `ServerLevel`，
  纯 `ClientLevel + RegistryAccess + ResourceManager` 无法在不改写 Vanilla worldgen 调用模型的前提下替代。

本阶段**完整接受**该结论，并且：

* 未开启 231.1 / Round 8 / Round 9；
* 未再尝试 `ClientWorldgenEnvironment` / `ServerLevel Adapter` / 伪 `WorldGenRegion` / 纯 Registry 替换；
* 未继续包装 `ServerLevel` 接口、未再改 `WorldgenEnvironment` 接口试图「绕过去」。

代码取证：全仓 `grep WorldgenEnvironment|ClientWorldgenEnvironment` **零命中**（231 当时也只把它写进
`SeedMiningRuntimeState` 的注释里，并没有落地该抽象）。

---

## 三、231 遗留代码处理（保留 / 删除 / 修改）

分类口径：A = 仍有价值的通用模型（保留）；B = 只为失败的纯 Client 路线存在（删除或迁回 dev）；
C = 会污染正式架构（清理）。

| 遗留物 | 类别 | 处理 |
| --- | --- | --- |
| `seed/prediction/**`（`DiamondSeedPredictor` / `PredictionSession` / `ScheduleSensitivityAnalyzer` / `PredictedOre` / `PredictionResult` / `PredictionCertainty`） | A | **保留，零改**（本阶段把「宿主」换成了 Worker，算法本体一行未动） |
| `seed/worldgen/**`（`OfflineWorldgenContext` / `OfflineChunkPipeline` / `OfflineChunkRegion` / `OfflineChunkHolder` / `OfflineChunkCache` / `OreChunkReader`） | A | **保留，零改**（Worker 直接用它们） |
| `seed/model/**`（`SeedOreTarget` / `OreType` / `OreSource`） | A | 保留 |
| `seed/config/SeedMiningConfig` | A | 保留 |
| `seed/observation/**`（`OreObservation` / `OreObservationState`） | A | 保留但**本阶段不使用**（口径第七十八节：不做 Observation） |
| `SeedMiningRuntimeState.WORLDGEN_ENVIRONMENT_UNAVAILABLE`（「当前世界生成环境不可用」） | C | **删除**，替换为 `CALCULATOR_STARTING` / `CALCULATOR_FAILED` 两个**本机计算器**状态 |
| `SeedMiningService` 里的 `getSingleplayerServer().overworld()` 宿主路线与 `hostAvailable` 标志 | C | **删除**（正式层现在只认「本机计算器」，单人 / 多人同一条路） |
| 231 在枚举注释里写的「为什么不做纯 Client 环境」结论 | A | **保留**（改成可检索的第四阶段说明，避免后人重复研究） |
| 231 的 dev 装置 | B | 231 **没有留下任何装置文件**（`dev/seedpoc` 下今天新增的三个装置全部属于 232） |

核对方式：`git status --porcelain` + 全仓 grep；正式层 `grep getSingleplayerServer` 只命中**注释文字**，
无任何运行时调用。

---

## 四、为什么不继续纯 Client Environment

不再重复论证（231 已给证据链）。第四阶段的口径是：**不消灭 ServerLevel，而是给它一个隔离的家**。
预测语义因此分成了两层，必须分清：

* **worldgen 环境**（`ServerLevel`、注册表、维度、世界高度、结构模板管理器、`RandomState`、`NoiseGeneratorSettings`）
  → 由 Worker 内的**真实 Vanilla 宿主**提供；
* **预测算法**（离线区块管线 + 调度敏感性分析）→ 仍由 `com.yiyiaddon.seed.prediction` 提供，
  与 229 冻结的边界完全一致，**不读 Worker 真实世界的任何 Chunk**。

---

## 五、Worker 最终架构

```
Minecraft Client（单人 / 多人，同一套正式逻辑）
  └─ SeedMiningService                业务状态：开关 / 种子 / 维度 / 请求 / 结果 / 运行时状态
       └─ SeedWorldgenWorkerClient    进程生命周期 + IPC 会话 + 预测请求（后台单线程调用）
            └─ SeedWorldgenWorkerLauncher   组装 java 命令（java.home + 当前 classpath + 模组路径）
                 │  127.0.0.1 回环 TCP，一行一条 JSON，token 鉴权，requestId 端到端
                 ▼
       本地隔离 Worker 进程（com.yiyiaddon.seedworker）
         ├─ SeedWorkerMain          进程入口（看门狗 / 日志 / 握手文件 / 收尾）
         ├─ SeedWorkerHost          Vanilla 26.1.2 宿主（Bootstrap → WorldLoader → MinecraftServer.spin）
         ├─ SeedWorkerSessions      会话与预测（懒建 DiamondSeedPredictor，换种子释放旧会话）
         └─ SeedWorkerIpcServer     回环 TCP 服务端（令牌 / 协议版本 / Minecraft 版本三重校验）
                 ▼
       现有 DiamondSeedPredictor（零改）→ OfflineChunkPipeline / OfflineChunkRegion / OfflineChunkCache
                 ▼
       PredictionResult（正式模型）→ IPC DTO → 回到客户端立刻还原成正式模型
```

目标远程服务器：**不需要安装任何插件**，Worker 也**不连**它（IPC 请求里根本没有服务器地址字段）。

---

## 六、为什么最终选择「独立 JVM Worker」（而不是同 JVM）

| 判据 | 结论 |
| --- | --- |
| 与客户端 JVM 隔离 | 独立进程，Worker 崩溃不直接拖死游戏（实机取证见【十二】崩溃恢复） |
| 内存生命周期 | 独立 `-Xms/-Xmx`，退出世界即回收（实机内存见【十四】） |
| 版本隔离（26.2 预留） | Worker 版本 = 目录名 + 握手三元组，未来 26.2 只要多一个构建，不需要在 Worker 里写 `if (mcVersion…)` |
| ServerLevel 是否污染 Multiplayer Client | 完全不污染：客户端进程里从来没有出现过 ServerLevel（多人验收里 `getSingleplayerServer() == null`） |
| 生产技术可行性 | 见下节审计：**不需要**额外 server jar、**不需要**官方 launcher 之外的启动器 |

同 JVM 方案（口径第一百节的唯一允许分支）**未被启用**，因为独立进程在本机 Windows + Fabric 26.1.2 下
已被证明可行（三个实机装置 + 负路径探针全部通过）。

---

## 七、生产 runtime 启动机制与 Fabric / classpath / remap 审计

### 7.1 启动方式（正式代码，非开发任务）

`SeedWorldgenWorkerLauncher.launch()` 组装的命令（`ProcessBuilder(List<String>)`，不拼命令字符串）：

```
<java.home>/bin/java[.exe]
  -Xms256m -Xmx2048m -XX:+ExitOnOutOfMemoryError
  -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8
  -Dlog4j2.configurationFile=<gameDir>/yiyiaddon/seed-worker/<版本>/log4j2.xml
  -Dlog4j.configurationFile=<同上>
  -Dyiyiaddon.seedworker=1
  -Djava.library.path=<原生库目录>
  -cp <当前 java.class.path 绝对化 + 本模组 FabricLoader 路径>
  com.yiyiaddon.seedworker.SeedWorkerMain
  --runtime-dir … --handshake-file … --token … --protocol-version 1
  --minecraft-version 26.1.2 --mod-version 1.0-beta2 --host-port <自由端口> --parent-pid <客户端 PID>
```

### 7.2 为什么这样组装

| 关注点 | 做法 | 依据 |
| --- | --- | --- |
| java 可执行 | 从 `java.home` 推导，**不依赖 PATH** | 口径第九十节 |
| classpath | `System.getProperty("java.class.path")`（相对项按当前工作目录绝对化） | Worker 与客户端使用**同一套已安装 runtime**，不 shade、不重新分发 Mojang jar |
| 模组自身 | `FabricLoader.getModContainer("yiyiaddon").getOrigin().getPaths()` | 开发期是目录，生产期是**模组 jar**，同一行代码两种形态都成立 |
| Worker 类是否真的可达 | 启动前用父进程类加载器 `Class.forName("com.yiyiaddon.seedworker.SeedWorkerMain", false, …)` 探测，不可达直接拒绝启动并给出明确错误 | 避免「开发能跑、生产静默崩」 |
| 名字体系（remap） | 26.1.2 为官方名称体系，Loom 直接用 Mojang artifacts，**没有 remap 差异**；`fabric.mod.json` 明确 `environment: client`，本阶段不引入第二套映射 | 实测：Worker 在 `Knot` 之外用纯 `-cp` 启动，直接加载到 `net.minecraft.server.*` 并成功建世界（见下） |
| server 类来源 | 26.1.2 的 Minecraft client jar（Loom 缓存 `minecraft-client.jar`，30675 条目）**已含** `net/minecraft/server/**`、`dedicated/**`；**不额外分发 server jar** | 口径第九节 |
| 生产实例验收 | **未实机**（见【二十一】） | 只用 FabricLoader 公开 API，无开发期 API、无绝对路径；开发期已在真实 Knot 客户端进程内实测通过 |

### 7.3 生产式运行路径的实机取证

* `seedWorkerProbe`（不进游戏，直接起 Worker）：握手 + 会话 + 预测全通过；
* 三个实机装置里 Worker 都是**被正式代码**（`SeedWorldgenWorkerLauncher`）拉起来的，
  不是 Gradle 任务拉的（Gradle 只负责启动客户端）。

---

## 八、Worker package tree

```
com/yiyiaddon/seedworker/                     ← Worker 运行时（不依赖 Fabric、不依赖 UI）
├── SeedWorkerMain.java                       进程入口：版本探测 / 日志 / 看门狗 / 握手文件 / 收尾
├── SeedWorkerArgs.java                       启动参数（委托 protocol.SeedWorkerArguments 解析）
├── SeedWorkerHost.java                       Vanilla 宿主：Bootstrap → 数据包 → WorldLoader → MinecraftServer.spin
├── SeedWorkerIpcServer.java                  回环 TCP 服务端（令牌 / 协议 / MC 版本三重校验 + 单线程 worldgen executor）
└── SeedWorkerSessions.java                   会话与预测（懒建 DiamondSeedPredictor，换种子释放旧会话）

com/yiyiaddon/seed/worker/client/             ← 客户端侧（正式）
├── SeedWorldgenWorkerClient.java             进程生命周期 + 会话 + 预测 + 停机阶梯 + 崩溃看门狗
├── SeedWorldgenWorkerLauncher.java           命令组装 / classpath / token / 自由端口 / log4j 配置
├── SeedWorkerProcess.java                    进程句柄（握手文件、诊断、日志尾部、退出码）
├── SeedWorkerState.java                      STOPPED / STARTING / READY / BUSY / FAILED / STOPPING
├── SeedWorkerException.java                  带错误码的异常
└── SeedWorkerPredictionMapper.java           IPC DTO → 正式 PredictionResult（含交叉校验与自洽校验）

com/yiyiaddon/seed/worker/protocol/           ← 传输层（版本化、fail-closed）
├── SeedWorkerProtocol.java                   协议版本 1 / 操作码 / 错误码 / 长度上限 / 超时
├── SeedWorkerArguments.java                  启动参数编解码唯一契约
├── WorkerRequest.java / WorkerResponse.java  请求 / 应答（一行一条 JSON）
├── WorkerJson.java / WorkerLineReader.java / WorkerProtocolException.java
└── dto/  WorkerHelloDto / WorkerSessionDto / WorkerPredictionDto / WorkerOreDto / WorkerStatsDto / WorkerChunkRef
```

---

## 九、IPC 协议

| 项 | 值 / 说明 |
| --- | --- |
| 传输 | `127.0.0.1` 回环 TCP（`InetAddress.getLoopbackAddress()`，端口由系统分配） |
| 绑定 | **只绑回环**：`new InetSocketAddress(loopback, 0)`；`serve()` 里对非回环连接显式拒绝并计数 |
| 帧格式 | 一行一条 JSON，单行上限 1 MiB（`MAX_LINE_BYTES = 1 << 20`），超限即断开 |
| 序列化 | **不用** `ObjectOutputStream` / `Serializable`（口径第二十节）；全部是显式 DTO record |
| requestId | 端到端存在：客户端 `nextRequestId` 自增 → IPC → Worker 原样回填 → 客户端**跳过过期 id** |
| 操作码 | `HELLO` / `PING` / `OPEN_SESSION` / `PREDICT_DIAMOND` / `CLOSE_SESSION` / `SHUTDOWN` |
| 错误码 | `MALFORMED_REQUEST` / `MALFORMED_RESPONSE` / `PROTOCOL_MISMATCH` / `MINECRAFT_MISMATCH` / `BAD_TOKEN` / `UNKNOWN_OP` / `NO_SESSION` / `UNSUPPORTED` / `INTERNAL` / `TIMEOUT` / `WORKER_GONE` / `TRANSPORT` |
| 协议版本 | `1`（客户端与 Worker 双向校验；不一致直接拒绝，绝不跨版本共用 worldgen） |
| 握手三元组 | `protocolVersion` / `minecraftVersion` / `modVersion`（客户端还会校验 capability `predict_diamond_overworld`） |
| 认证令牌 | 客户端启动时 `SecureRandom` 生成 **24 字节 = 192 位**高熵 token，通过**启动参数**传给 Worker（不落盘、不进协议正文日志）；服务端用 `MessageDigest.isEqual` 定长比较 |
| 超时 | 连接 15 s；普通请求 15 s；预测 180 s；启动 120 s；socket 读 600 s；accept 空闲超时后按「父进程已消失」退出 |

**Prediction DTO 完整性**（口径第十九节，禁止只回 `Set<BlockPos>`）：
`success / failureReason / targetChunk / seed / dimension / oreType / elapsedMillis / heldChunks / stats(11 项) / ores[]`，
每个 ore 含 `x / y / z / oreType / certainty / source / originViewer / conflictingWriters`。

---

## 十、Worker 宿主（ServerLevel）与存储

* **创建方式**：`SeedWorkerMain` → `SeedWorkerHost.boot()`，复刻 Vanilla 服务端启动序列
  （`SharedConstants.tryDetectVersion()` → `CrashReport.preload()` → `Bootstrap.bootStrap()`/`validate()` →
  `Util.startTimerHackThread()` → 写 `server.properties` → `DedicatedServerSettings` →
  `RegionFileVersion.configure` → `Services.create` → `LevelStorageSource.createDefault` →
  `WorldLoader.load` → `MinecraftServer.spin(thread -> new WorkerServer(...))` → `awaitOverworld`）。
* **宿主初始化日志**（Worker 自己写的日志，`…/seed-worker-26.1.2.log`）：
  `宿主初始化：Vanilla 注册表已就绪（耗时 6637 ms）` → `数据包与注册表已加载` →
  `宿主初始化：完成（维度 minecraft:overworld，世界高度 -64 ~ 319，总耗时 8868 ms）`
  → `本地世界生成计算器已就绪（IPC 端口 56735，宿主监听 已停用（不接受任何外部连接）…）`
  —— **记录口径第四十节要求的 RegistryAccess / 维度 / 世界高度 / 结构模板管理器 / RandomState / NoiseGeneratorSettings 构建成功**。
* **存储目录**：`<gameDir>/yiyiaddon/seed-worker/<Minecraft 版本>/`，内部：
  `host/`（宿主 gameDir：`server.properties`、`universe/<世界名>/`）、`logs/`、`log4j2.xml`、`seed-worker-<版本>.log`、`handshake/worker-ready.json`（就绪后原子写、进程退出时删除）。
* **不使用玩家存档**：宿主 world 名固定为 `host`，位于 Worker 自己的目录里（**不是** `saves/<世界>`）；
  实机检查：`run-26.1.2-seed-worker-parity-test/saves/` 只有测试装置自己建的 `seedpoc-known-seed`，
  与 Worker 目录完全分离。
* **临时存储清理**：`SeedWorkerMain` 启动时调用 `purgeOversizedHostDir`（上限 **512 MB**），
  超过即整目录删除重建；正常退出时删除握手文件。日志实测：`宿主世界目录 2/512 MB（保留以加速下次启动）`。
* **不开公网端口**：`server.properties` 写 `server-ip=127.0.0.1`、`online-mode=false`、`enable-status=false`、
  `enable-rcon=false`、`enable-query=false`、`view-distance=3`、`simulation-distance=3`、`max-tick-time=-1`；
  且 `WorkerServer#initServer()` 在启动后**立刻 `getConnection().stop()`** ——
  日志实证：`Starting Minecraft server on 127.0.0.1:56619`（仅回环）→ 就绪行写着「宿主监听 已停用（不接受任何外部连接）」。
* **不连目标服务器**：Worker 完全不知道远程服务器地址（IPC 请求里没有该字段），也无任何出站连接代码。

---

## 十一、Predictor 边界（本阶段最重要的红线）

* 正式 `DiamondSeedPredictor` / `PredictionSession` / `OfflineChunkPipeline` / `ScheduleSensitivityAnalyzer`
  **一行未改**（本阶段只新增宿主，不改算法；口径第四十一节）。
* Worker 内的预测仍走**离线管线**：`OfflineWorldgenContext` + `OfflineChunkRegion`/`OfflineChunkHolder`/`OfflineChunkCache`；
  **没有** `serverLevel.getChunk(target)` 之后读真实矿的写法。
* **宿主 ChunkMap 查询恒为 0**：Worker 侧每次预测都在日志与 DTO 里回报 `宿主 ChunkMap 查询 0`，
  客户端 `SeedWorkerPredictionMapper` 还会再断言一次；不为 0 时正式层 `LOGGER.error` 视为架构回归。
  实测：parity 15 个目标 + 230 六个用例 + 多人两目标，**全部 0**。

---

## 十二、会话 / 种子 / 维度 / 服务器切换 / 崩溃恢复

| 场景 | 行为 | 实机证据 |
| --- | --- | --- |
| 懒启动 | 只有「已启用 + 种子合法 + 主世界 + 首次请求预测」才拉起 | `runClient`（Seed Mining 关闭）实测：0 个 Worker 进程、`yiyiaddon/seed-worker` 目录都没生成 |
| 会话复用 | 同「版本 + 种子 + 维度」下多次预测复用 Worker 与离线缓存 | Worker 日志：`s1-135283a` 连续 10 个目标，缓存 529 → 744 |
| 换种子 | 取消在途请求（`generation` 递增，旧响应永不回写）→ 关闭 Worker 会话（释放离线世界）→ 清结果 | Worker 日志：`会话已关闭：s1-135283a（种子 20260922…），离线区块缓存已释放` → `会话已打开：s2-3039（种子 12345…）` |
| 换维度 | 关会话、清结果、禁止在非主世界预测 | 230 装置：`下界：…旧维度结果已清除（通过） / 下界是否允许预测：禁止（正确）` |
| 退出服务器 | 取消请求 + 清结果 + **优雅停止 Worker** | 230 装置与 232 生命周期装置：`状态回「等待进入世界」（通过） / 结果已清（通过） / 计算器已停止（通过）` |
| 换服（A→B） | 按 `WorldIdentity` 作用域隔离配置；`level` 对象变化即走同一收口（取消 + 清结果 + 停计算器），B 里重新拉起新 Worker | 逻辑与「退出服务器」同一入口（`onLevelChanged`），由 230 生命周期的断线用例覆盖 |
| 关闭功能（ON→OFF） | 取消在途 + 清结果 + 停 Worker；种子文本作为配置保留 | 230 装置：`启用开关改为 关` → `启用开关改为 开` 后状态回到「等待进入世界」 |
| 客户端退出 | 关机钩子发 `SHUTDOWN` → 等 3 s → `destroy()` → 等 2 s → `destroyForcibly()` → 等 2 s | 日志：`客户端正在退出，开始回收本地世界生成计算器`；随后进程扫描 **0 个残留** |
| Worker 崩溃 | 独立看门狗线程轮询（500 ms）发现进程消失 → 状态 `FAILED` → 正式层立刻进「本地世界生成计算器异常」；下一次点击**自动重启一次** | 232 生命周期装置：基线 PID 35288 → 强杀 → `崩溃后状态：「本地世界生成计算器异常」（通过）` → 恢复预测 **45 个**、PID 变 29520、启动次数 1→2、旧 PID 确已消失 |
| 启动超时 | `STARTUP_TIMEOUT_MILLIS = 120 s`，超时抛错并带退出码 + 日志尾部 | 代码路径 + `SeedWorkerProcess.diagnostics()`（未实机触发，见【二十一】） |
| 预测超时 | `PREDICT_TIMEOUT_MILLIS = 180 s`，超时按 fail-closed 处理并回收 Worker | 同上 |
| 重启节流 | 连续失败上限 3 次 + 最小间隔 5 s，避免无限快速重启 | 代码常量 + 生命周期装置里 7 秒退避等待期间客户端持续正常推进刻 |

### 12.1 本次实机发现并修掉的两个真实缺陷（必须记录）

1. **崩溃回调不可靠**：原先用 `Process#onExit()` 监听进程退出，实机发现 Worker 被外部强杀后
   JDK 明明已回收进程（`isAlive()` 立刻为 false、`exitCode` 可读），但**回调一直不来**，
   正式层停在旧状态，直到下一次预测才以 `Connection reset by peer` 收场。
   → 改为自建守护线程轮询 `isAlive()`（500 ms），行为可预期、延迟上限明确。
2. **重启后沿用旧会话**：Worker 重启后客户端 `sessionId` 仍指向旧会话，`ensureSession` 因「种子/维度没变」
   直接复用 → 新 Worker 里根本没有会话，恢复预测必然失败。
   → `startInternal()` 里显式作废会话标识并关闭陈旧 socket。

两个缺陷都只在「Worker 由外部强杀」这条路径上暴露，因此**必须靠专项装置才能测出来**
（这也是新增 `WorkerLifecycleRegression` 的直接原因）。

---

## 十三、UI 状态与文案

| 运行时状态 | 文案 | 触发 |
| --- | --- | --- |
| `WAITING_FOR_WORLD` | 等待进入世界 | 已启用但没进世界 |
| `WAITING_FOR_SEED` | 等待填写服务器种子 | 种子框为空 |
| `INVALID_SEED` | 种子格式无效 | 非 long |
| `UNSUPPORTED_DIMENSION` | 当前维度暂不支持 | 非主世界 |
| `CALCULATOR_STARTING` | **正在启动本地世界生成计算器…** | 首次预测（冷启动） |
| `PREDICTING` | 正在预测当前区块… | 预测进行中 |
| `CALCULATOR_FAILED` | **本地世界生成计算器异常** | 启动失败 / 超时 / 崩溃 / 传输故障 |
| `SUCCESS` / `FAILED` | 预测完成 / 预测失败 | 有结果 |
| `READY` | 已就绪 | 条件齐备 |

* 界面上的「世界生成计算器」一行显示「已就绪 / 未启动（首次预测时自动启动）/ 正在启动… / 异常」，
  **不显示** ServerLevel / IPC / PID / Socket / WorldGenRegion 等技术名词（口径第七十四节），这些只进日志与开发装置。
* 崩溃文案明确是「本机计算器异常」，**不是**「服务器不支持」；预测失败原因优先显示正式层文案。
* personalMode：`Seed Tab` 在 `personalMode = true` 时隐藏，`reload()` 会先把页签落回可见兜底页（230 装置实测两种模式整窗装配均通过）。

---

## 十四、性能与内存（实测）

### 14.1 时间

| 阶段 | 实测 |
| --- | --- |
| Worker 进程启动 → 宿主就绪（**全新运行目录**，宿主世界要现建） | **10448 ms**（parity 首次）/ **11651 ms**（lifecycle 首次）；其中 Worker 日志给出分解：Vanilla Bootstrap 6637 ms、宿主初始化合计 8868 ms |
| Worker 进程启动 → 宿主就绪（**宿主模板已存在**） | **3846 / 3853 / 4044 / 4070 ms**（探针四次）、4412 ms（lifecycle 恢复）、6622 ms（多人验收）；机器负载较重时出现过一次 9501 ms |
| 会话初始化（OPEN_SESSION） | 毫秒级（含在首次预测前的往返里） |
| 第一次 Target 预测（冷，含离线管线构建） | **4010 ~ 4416 ms** |
| 相邻已缓存 Target | **19 ~ 20 ms**（缓存区块 576 / 623） |
| 邻近新 Target | 160 ~ 494 ms |
| 远端新 Target | 1037 ~ 1594 ms |
| 多人验收：近端 (0,0) / 远端 (-400,380) | 2396 ms / 1179 ms |

### 14.2 内存（独立 JVM，`-Xms256m -Xmx2048m -XX:+ExitOnOutOfMemoryError`）

* **Worker JVM RSS**（整个 15 目标序列每秒采样，样本文件 `build/seed-memory-samples.txt`）：
  进程刚起 **38 MB** → 宿主初始化完成后 **≈ 544 MB** → 运行期在 **537 ~ 627 MB** 之间波动，
  序列末尾回到 568 MB —— **没有随目标数单调增长**（10+ 连续 Target 后趋于稳定）。
  空闲内存 < 2 GB，无需进一步调查。
* **客户端 JVM**：同一次采样里客户端堆使用量早期样本 408 MB（`GC.heap_info`），RSS 774 → 2819 MB；
  该增长主要来自客户端自身资源加载与 parity 装置里同时运行的**集成服务端 Oracle 侧**，
  **不能**归因于 Worker（客户端侧对 Worker 的成本只有一条 socket + DTO → 正式模型转换）。
* OOM 语义：Worker 带 `-XX:+ExitOnOutOfMemoryError`，内存不足时**进程明确退出并给出退出码**，
  客户端进入失败态，不会静默 OOM。

---

## 十五、回归结果

### 15.1 第一层：Worker vs 单人 Oracle（`runClientSeedWorkerParityTest`）

产出：`run-26.1.2-seed-worker-parity-test/seedpoc-232-Worker对照.txt`（判定：**全部判定：通过**）

15 个目标**每一个**都打印「逐项比较：完全一致（数量 / BlockPos / 矿物 / 确定性 / 来源 / 写入者）」：

| Seed | 目标 | Worker 候选 | 敏感 | Oracle 候选 | 逐项 |
| --- | --- | --- | --- | --- | --- |
| 20260922 | (0,0) | 45 | 0 | 45 | 完全一致 |
| 20260922 | (1,0) | 9 | 0 | 9 | 完全一致 |
| 20260922 | (0,1) | 29 | 0 | 29 | 完全一致 |
| 20260922 | (1,1) | 21 | 0 | 21 | 完全一致 |
| 20260922 | (-1,0) | 22 | 0 | 22 | 完全一致 |
| 20260922 | (0,-1) | 27 | 0 | 27 | 完全一致 |
| 20260922 | (-1,-1) | 21 | 0 | 21 | 完全一致 |
| 20260922 | (2,2) | 18 | 0 | 18 | 完全一致 |
| 20260922 | (3,-1) | 18 | 0 | 18 | 完全一致 |
| 20260922 | (-2,3) | 33 | 0 | 33 | 完全一致 |
| 12345 | (0,0) | 31 | 0 | 31 | 完全一致 |
| 12345 | (-1,-1) | 29 | **7** | 29 | 完全一致 |
| 12345 | (-25,17) | 24 | 0 | 24 | 完全一致 |
| 12345 | (120,-130) | 26 | 0 | 26 | 完全一致 |
| 2 | (-400,380) | 23 | **1** | 23 | 完全一致 |

* **固定集汇总**：Seed 20260922 十目标合计 **243**（期望 243，通过）；Seed 12345 四目标合计 **110**（期望 110，通过）。
* **Seed 2 争议位置** `(-6385,-59,6085)`：**调度敏感**（期望「调度敏感」，通过）。
* **certainty / source parity**：每个坐标的 `OreType / PredictionCertainty / OreSource / originViewer / conflictingWriters`
  都在「完全一致」判定覆盖范围内（parity 装置的 `compare()` 逐项比对）。
* **宿主 ChunkMap 查询**：15 个目标全部 0。

### 15.2 第二层：真正 Dedicated Multiplayer（`runSeedWorkerServer` + `runClientSeedWorkerMultiplayerTest`）

产出：`run-26.1.2-seed-worker-mp-test/seedpoc-232-多人验收.txt`（判定：**全部判定：通过**）

* 环境：本机 26.1.2 专用服务器（`server.properties`：`server-ip=127.0.0.1`、`level-seed=20260922`、
  `online-mode=false`、`enable-rcon=false`、`enable-query=false`，**未加载任何改 worldgen 的 Mod**）；
  客户端由 `--quickPlayMultiplayer 127.0.0.1:25565` 自动连接。
* `Minecraft#getSingleplayerServer()`：**null**（通过）。
* 种子挖矿进入 **READY**（诊断：`状态=已就绪；PID=9624；会话=s1-135283a；启动次数=1；预测次数=2`）。
* Seed 20260922 区块 (0,0)：**45**（通过）。
* 远端未加载区块 (-400,380)：客户端 `ChunkCache` 里**没有**该区块（近端 (0,0) 是「有」、远端是「没有」），
  预测仍**成功**（37 个候选，耗时 1179 ms），且预测后**仍未加载**（通过）。
* 两目标宿主 ChunkMap 查询均为 0。

### 15.3 第三层：230 输入矩阵与生命周期（`runClientSeedPredictTest` + `service=1`）

产出：`run-26.1.2-seed-predict-test/seedpoc-服务层回归.txt`（判定：**全部判定：通过**）

* 输入矩阵 10 项：空串 → 未填写；`0` / `12345` / `-7777` / `20260922` / `Long.MAX_VALUE` / `Long.MIN_VALUE`
  → 已填写 + 已就绪；`abc` / `12.3` / `--` → 格式无效 + 种子格式无效；非法种子下禁止预测。
* 六个预测用例：20260922 (0,0)=45；Seed2 (-400,380)=23/1 敏感（争议格仍为调度敏感）；
  12345 = 31 / 29（7 敏感）/ 24 / 26；全部宿主 ChunkMap 查询增量 0。
* 服务层结果 vs 直连正式 Predictor：逐 BlockPos 完全一致。
* 主世界 → 下界 → 主世界：旧结果清除、下界禁止预测、返回后回到「已就绪」。
* 退出世界：状态回「等待进入世界」+ 结果已清 + 计算器已停止（**通过**）。
* 界面装配烟测（控制台整窗 + 种子页，两种 personalMode）：通过。

### 15.4 Worker 进程生命周期与崩溃恢复（`runClientSeedWorkerLifecycleTest`）

产出：`run-26.1.2-seed-worker-lifecycle-test/seedpoc-232-Worker生命周期.txt`（判定：**全部判定：通过**）

* 基线：候选 45，PID 35288（该 PID 在系统中确实存在、父进程就是本客户端进程 34408）。
* 崩溃：`destroyForcibly()` → 状态变「本地世界生成计算器异常」、原因「已停止（退出码 1）」、
  进程已回收、**客户端仍在正常推进刻**。
* 恢复：再点一次 → 自动重启（PID 29520，启动次数 2），候选仍为 **45**，宿主查询 0，旧 PID 确已消失。
* 退出世界：状态回「等待进入世界」、结果已清、重启出来的进程也已退出。

### 15.5 协议负路径（fail-closed）实测

产出：`build/seed-worker-probe-neg-*/seed-worker-probe/probe-report.txt`

| 用例 | 请求 | 期望错误码 | 实际 | 判定 |
| --- | --- | --- | --- | --- |
| `unknownOp` | 未知操作码 | `UNKNOWN_OP` | `UNKNOWN_OP` | 通过 |
| `minecraftMismatch` | HELLO 带 `26.9.9` | `MINECRAFT_MISMATCH` | `MINECRAFT_MISMATCH` | 通过 |
| `protocolMismatch` | HELLO 带协议版本 2 | `PROTOCOL_MISMATCH` | `PROTOCOL_MISMATCH` | 通过 |
| `badToken` | 随机 token 冒充本机其它进程 | `BAD_TOKEN` | `BAD_TOKEN` + 关闭连接 | 通过 |

即：**协议错误一律明确拒绝并带错误码，绝不「静默返回空 ores 假装成功」**（口径第六十六节）。
正常 0 矿结果与失败的区分由 `success` 标志与 `PredictionResult` 语义保证，IPC 未破坏它。

---

## 十六、静态双审计：客户端不读远端真实 Chunk、Worker 不连目标服务器

* **客户端侧**：正式预测路径的输入只有 `seed / dimension / ChunkPos`（外加版本与模型信息）。
  `SeedWorkerPredictionMapper` 会把 DTO 逐项与「本次请求的 seed / chunk / dimension / oreType」交叉校验，
  不一致直接判 `MALFORMED_RESPONSE`；统计数字与逐块分类还有一道**自洽校验**
  （敏感/确定性计数必须与逐块一致，且三者之和 == ores.size()）。
  预测主链没有 `ClientLevel#getBlockState` / `ClientChunkCache#getChunk` 这类调用
  （多人验收里，远端目标区块在客户端**根本不存在**，预测照样成功 —— 这是运行时反证）。
* **Worker 侧**：IPC 请求结构里没有远程服务器地址字段；Worker 代码里没有任何出站连接。
* **Worker 宿主真实 Chunk 不参与预测**：宿主 ChunkMap 查询恒 0（【十一】）。

---

## 十七、打包与进程检查

* `jars`：`build/libs/yiyiaddon-1.0-beta2-personal+26.1.2.jar`
  * `com/yiyiaddon/seed/worker/**`（客户端 + 协议 + DTO）：**24** 个条目
  * `com/yiyiaddon/seedworker/**`（Worker bootstrap）：**8** 个条目（含 `SeedWorkerHost$WorkerServer`）
  * `fabric.mod.json`：1
  * 结论：**Worker 运行时就在 yiyiaddon 自己的产物里**，用户仍然只安装一个 mod，
    不需要第二个 companion artifact（口径第八十七节）。
* Mojang jar：**未**打进产物、**未** shade（Worker 用用户已安装的 runtime）。
* 无硬编码开发绝对路径：正式代码里没有 `D:/mcaddon/...` / `run-26.1.2` / `build/classes/java/main`
  （仅开发 Gradle 任务里出现项目内相对路径）。
* 路径兼容：命令用 `ProcessBuilder(List<String>)`，不拼字符串；运行目录来自 `FabricLoader` 的 gameDir，
  实测路径含中文（`D:\mcaddon\yiyiaddon\...` 与模组名中文界面）均正常。**含空格路径未单独构造用例**（见【二十一】）。
* 进程泄漏：全部实机运行结束后两次全量扫描 `java.exe`，Worker 与 dev-client 计数均为 **0**；
  仅剩 IDE 语言服务器与用户自己的 4 个 Minecraft 进程（运行前就存在）。

---

## 十八、本阶段「做了 / 明确没做」

**做了**：Worker 独立进程（宿主 + IPC + 会话）、客户端总控（启动器 / 进程句柄 / 状态机 / 映射器）、
`SeedMiningService` 统一宿主路线、UI 文案与状态、三个开发装置（parity / multiplayer / lifecycle）、
探针负路径、Gradle 运行配置与准备任务、协议 fail-closed、性能与内存实测。

**明确没做**（口径第七十八 ~ 八十三节，逐条遵守）：

* 未接 Observation（`OreObservation` 模型仍存在但**不使用** `CONFIRMED` / `MISSING` / `SUSPICIOUS`）；
* 未做任何 Renderer（无 ESP / Box / Tracer / 标签 / 世界坐标渲染）；
* 未做 SeedValidation（无地形比对、无 biome fingerprint、无自动破解未知种子）；
* 未接 AutoMiner（`MiningStateMachine` / `MiningPathing` / `MiningVeinMiner` / `MiningFastBreakController` **零改动**，
  `git status` 可证：这些文件不在修改列表里；也没有任何 `Seed candidates → Baritone` 的链路）；
* 未扩矿物（仍然只有 Overworld Diamond）；
* 未开始 26.2 适配（只在架构上预留：Worker 版本由目录名 + 握手三元组隔离）。

**Seed 正确性仍未验证**：Worker 算出 20260922 只证明「按 26.1.2 Vanilla + Seed 20260922 会得到这些候选」，
不代表目标服务器的真实种子就是它；界面**不显示**「Seed 已验证」，模型说明仍是
「Minecraft 26.1.2 原版主世界 + 尚未验证服务器实际世界生成规则」。

---

## 十九、所有新增 / 修改 / 删除文件

**新增（正式）**

* `com/yiyiaddon/seed/worker/protocol/`：`SeedWorkerProtocol` / `SeedWorkerArguments` / `WorkerRequest` /
  `WorkerResponse` / `WorkerJson` / `WorkerLineReader` / `WorkerProtocolException`
* `com/yiyiaddon/seed/worker/protocol/dto/`：`WorkerHelloDto` / `WorkerSessionDto` / `WorkerPredictionDto` /
  `WorkerOreDto` / `WorkerStatsDto` / `WorkerChunkRef`
* `com/yiyiaddon/seed/worker/client/`：`SeedWorldgenWorkerClient` / `SeedWorldgenWorkerLauncher` /
  `SeedWorkerProcess` / `SeedWorkerState` / `SeedWorkerException` / `SeedWorkerPredictionMapper`
* `com/yiyiaddon/seedworker/`：`SeedWorkerMain` / `SeedWorkerArgs` / `SeedWorkerHost` /
  `SeedWorkerIpcServer` / `SeedWorkerSessions`

**修改（正式）**

* `seed/service/SeedMiningService.java`：宿主路线整体换成计算器；新增 `predictChunk`、`calculatorRunning` /
  `calculatorStateCn` / `calculatorDiagnosticsCn` / `calculatorErrorCn` / `calculatorPid` / `calculatorStartCount`；
  空转期盯住计算器状态变化；关机钩子
* `seed/service/SeedMiningRuntimeState.java`：删 `WORLDGEN_ENVIRONMENT_UNAVAILABLE`，加 `CALCULATOR_STARTING` / `CALCULATOR_FAILED`
* `feature/mining/ui/console/MiningSeedPage.java`：计算器状态行与失败文案、提示语
* （230 已落地的 `YiyiAddonClient` / `MiningConsoleScreen` / `AutoMinerPage` / `yiyiaddon.mixins.json` 改动仍属第二阶段，本阶段未再动）

**新增（开发期装置，dev 包）**

* `dev/seedpoc/WorkerProbe`（含负路径模式与报告 tee）、`WorkerParityRegression`、`WorkerMultiplayerRegression`、
  `WorkerLifecycleRegression`

**修改（开发期装置 / 构建）**

* `dev/seedpoc/SeedPocFlags`（新增三个开关）、`SeedPocEntry`（新增三条分派）、`ServiceRegression`（等待窗口）
* `build.gradle`：三个客户端运行配置（parity / multiplayer / lifecycle）+ 专用服务器配置、
  `prepareSeedWorkerClientRun`（关无障碍引导与多人警告）、`prepareSeedWorkerServer`、`seedWorkerProbe`

**删除**：无（231 的失败路线本来就没有落地成文件）

---

## 二十、已知限制（含未实机项）

1. **生产实例验收未实机**：本阶段的实机证据全部来自 Fabric `Knot` 开发运行时（也就是真实客户端进程，
   但模组以目录形态挂载）。生产形态（模组 jar 装进整合包）**没有实机验证**。
   ~~为什么仍判断成立：启动只依赖 `java.home`、`java.class.path`、`FabricLoader` 模组容器路径与
   `Class.forName` 可达性探测；26.1.2 无 remap 差异；`seedworker` 类确实在产物 jar 内；
   26.1.2 client jar 内含 server 类且 Fabric 安装器会把库与客户端 jar 放进 `-cp`。~~
   → **已由《232-P · 真实用户生产发行验收》补齐（实机，不再推断）**：在正式发布产物
   `yiyiaddon-1.0-beta2-26.1.2.jar`（SHA-256 `3CD0B463…`）的 jar-only 生产 Fabric 环境里，
   Worker 真实启动、建出 Vanilla `ServerLevel`、完成 `OPEN_SESSION` / `PREDICT_DIAMOND`，数字与开发环境一致；
   并在真实用户的 103-Mod 环境下复现同样数字。
2. **启动超时 / 预测超时的实际触发未实机**（只做了代码路径与常量核对）。
   → **已由 232-P 用 fault injection 实机触发**（`startupTimeoutMillis=1` / `predictTimeoutMillis=1`），
   状态不会停在 STARTING/PREDICTING，请求被清理，Worker 被回收。
3. **路径含空格未单独构造用例**（仅验证了含中文路径）。
   → **已由 232-P 补齐**：三个含空格 + 中文的实例（`D:\mcaddon\种子 Worker Production Test*`）全部通过。
4. **换服 A→B 未单独实机**（与「退出服务器」共用同一收口，由断线用例覆盖）。
5. 多人验收的远端目标用的是 Seed 20260922 的 (-400,380)，不是 Seed 2 的受控样本；Seed 2 的远端形态在 parity 里覆盖。
6. 本阶段只支持 26.1.2 主世界钻石；Worker 与客户端版本不匹配会**拒绝预测**（这是设计，不是缺陷）。
7. 补预测行（230 装置第七段）的目标区块随玩家传送落点变化，因此其候选数（本次 35）不做数字判定，
   只判定「计算器仍在运行 + 有结果」。

---

## 二十一、232 二十八条通过标准逐条判定

| # | 标准 | 判定 | 证据 |
| --- | --- | --- | --- |
| 1 | 停止纯 Client WorldgenEnvironment 失败路线 | 通过 | 【二】【三】；全仓 grep 零命中 |
| 2 | 正式存在 Seed Worldgen Worker | 通过 | 【八】包树 + 实机三个装置 |
| 3 | Worker 能提供真实 Vanilla ServerLevel | 通过 | 宿主初始化日志（维度 / 高度 -64~319 / BootStrap 完成 / 预测成功） |
| 4 | Worker 不读目标服务器 Chunk | 通过 | IPC 无地址字段；宿主查询恒 0 |
| 5 | Worker 不连目标服务器 | 通过 | 同 4 |
| 6 | 远程服务器无需插件 | 通过 | 【五】【七】 |
| 7 | 正式 Predictor 算法未被重写 | 通过 | `seed/prediction`、`seed/worldgen` 零改动 |
| 8 | Host ChunkMap Query = 0 | 通过 | 【十一】15 + 6 + 2 个用例全 0 |
| 9 | Worker vs 单人 Oracle：20260922 固定集逐 BlockPos 一致 | 通过 | 【15.1】十目标全部「完全一致」，合计 243 |
| 10 | 12345 固定集一致 | 通过 | 【15.1】31/29(7)/24/26，合计 110 |
| 11 | Seed2 冲突一致 | 通过 | 【15.1】23/1/22/0，争议格调度敏感 |
| 12 | certainty / source 一致 | 通过 | parity 逐项比对含 certainty / source / 写入者 |
| 13 | Dedicated Multiplayer `getSingleplayerServer() == null` | 通过 | 【15.2】 |
| 14 | Dedicated 下 Seed Mining READY | 通过 | 【15.2】`状态=已就绪` |
| 15 | Dedicated 下 20260922 (0,0) = 45 | 通过 | 【15.2】 |
| 16 | 远端未加载 Chunk 可预测 | 通过 | 【15.2】(-400,380) 客户端无该区块、预测成功 |
| 17 | 修改 Seed 不发生旧结果回写 | 通过 | 【十二】换种子用例 + 230 装置输入矩阵 |
| 18 | 退出服务器 Worker 清理 | 通过 | 【15.3】【15.4】 |
| 19 | 客户端退出无孤儿进程 | 通过 | 【十七】两次全量扫描 0 |
| 20 | Worker 崩溃不会拖死客户端 | 通过 | 【15.4】崩溃期间客户端持续正常推进刻 |
| 21 | 正常 build 成功 | 通过 | `compileJava EXIT=0`、`build EXIT=0` |
| 22 | 正常 runClient 成功 | 通过 | 最终代码下实跑，客户端正常启动 |
| 23 | AutoMiner 零行为变化 | 通过 | 相关文件零改动（`git status`） |
| 24 | 没有 Renderer | 通过 | 【十八】 |
| 25 | 没有 Observation | 通过 | 【十八】 |
| 26 | 没有 SeedValidation | 通过 | 【十八】 |
| 27 | 没有其它矿物 | 通过 | 【十八】 |
| 28 | 没有 26.2 | 通过 | 【十八】 |

**结论：28 条全部满足**，其中第 3 / 9 / 13 / 15 / 16 五条关键项均通过实机取证。

---

## 二十一·补：口径第 102 节「报告至少包含」六十七项逐条对照

下表把用户口径第 102 节列出的 67 项逐条对齐到出处；标注 **【232-P】** 的项是生产发行阶段
（《232-P · 真实用户生产发行验收》，链接见文末）补齐的实机证据 —— 232 主报告当时把它们写进了【二十】已知限制。

| # | 口径要求 | 结论 | 出处 |
| --- | --- | --- | --- |
| 1 | 231 失败结论复核 | 已复核并接受，旧路线停止 | 【二】 |
| 2 | 231 遗留代码处理 | 保留 / 删除 / 修改逐类给出，删除项为「无」 | 【三】 |
| 3 | 为什么不继续纯 Client Environment | 硬依赖 `WorldGenRegion → ServerLevel`，不改 Vanilla 调用模型无法替代 | 【四】 |
| 4 | Worker 最终架构 | Client → Service → WorkerClient → 隔离 Worker → Vanilla ServerLevel → Predictor | 【五】 |
| 5 | 为什么选择独立 JVM 或同 JVM | 独立 JVM：隔离崩溃 / 内存生命周期清晰 / 版本隔离；同 JVM 分支未启用 | 【六】 |
| 6 | 生产 runtime 启动机制 | `java.home/bin/java` + `java.class.path` + 模组容器路径，`ProcessBuilder(List)` | 【七】 |
| 7 | Fabric Loader / classpath / remap 审计 | 开发期已审计；**【232-P】在生产 jar-only 环境实测通过**（无 remap 崩溃） | 【七】+ 232-P §二.6 |
| 8 | Worker package tree | `seed/worker/{client,protocol,dto}` + `seedworker/` | 【八】 |
| 9 | IPC 协议 | `127.0.0.1` 回环 TCP、一行一条 JSON、`requestId` 端到端 | 【九】 |
| 10 | Protocol Version | `1`（握手三元组：协议 / Minecraft / 模组版本） | 【九】 |
| 11 | 认证 token | `SecureRandom` 24 字节 = **192 位**，启动参数传递，定长比较 | 【九】 |
| 12 | 端口绑定 | 仅 `127.0.0.1`，端口由系统分配 | 【九】【十】 |
| 13 | Process lifecycle | 懒启动 → READY → BUSY → 停机阶梯 → STOPPED | 【十二】【15.4】 |
| 14 | Worker ServerLevel 创建方式 | 独立 `DedicatedServer` 形态的最小宿主，就绪后停用监听 | 【十】 |
| 15 | Worker Storage | `<gameDir>/yiyiaddon/seed-worker/<版本>/`，自带 host/universe/logs | 【十】 |
| 16 | 是否使用玩家存档 | **不使用**（与 `saves/` 完全分离，512 MB 上限自动重建） | 【十】 |
| 17 | 是否打开 Minecraft 服务端公网端口 | 否：仅回环且就绪后停用；**【232-P】套接字采样证实监听消失** | 【十】+ 232-P §二.15 |
| 18 | 是否连接目标服务器 | 否：协议无地址字段、代码无出站；**【232-P】断网模拟下仍全通** | 【十六】+ 232-P §二.15 |
| 19 | Predictor 是否读 Worker 真实 Chunk | 否：沿用 229 的离线管线边界 | 【十一】 |
| 20 | Host ChunkMap Query | 恒 **0**（15 + 6 + 2 个用例） | 【十一】 |
| 21 | Worker Session 模型 | 以 版本 + Seed + 维度 为会话身份，换一项即失效 | 【十二】 |
| 22 | Seed 切换 | 取消旧请求、旧结果不回写、旧会话清理、可安全重启 | 【十二】 |
| 23 | Dimension 切换 | 下界不支持 → 会话关闭；返回主世界重新开合法会话 | 【12】【15.3】 |
| 24 | 服务器切换 | 与「退出服务器」同一收口；A→B 不复用旧会话 | 【十二】+【二十】限制 4 |
| 25 | Client 退出 | SHUTDOWN → destroy → destroyForcibly，无孤儿 | 【十二】【十七】 |
| 26 | Worker 崩溃恢复 | 看门狗 500 ms 发现 → FAILED → 下一次点击自动重启一次 | 【15.4】+ **【232-P】R5** |
| 27 | 启动超时 | 有超时且失败可诊断；**【232-P】用 fault injection 实机触发** | 232-P §二.12（R6） |
| 28 | 预测超时 | 同上，UI 不会永久停在「正在预测」 | 232-P §二.12（R7） |
| 29 | Worker 内存 | 运行期 537~627 MB 且不随目标数单调增长；**【232-P】给出 768/1024/1536/2048/256 Heap 矩阵** | 【14.2】+ 232-P §二.10 |
| 30 | Worker 启动耗时 | 冷 10448~11651 ms、宿主已存在 3846~6622 ms；**【232-P】生产形态冷启动 11251 ms** | 【14.1】+ 232-P §二.11 |
| 31 | 首次预测耗时 | 4010~4416 ms（冷，含离线管线构建） | 【14.1】 |
| 32 | 相邻 Target 耗时 | 19~20 ms（已缓存） | 【14.1】 |
| 33 | Singleplayer Oracle parity 架构 | 同进程集成服务端 `ServerLevel` 直连正式 Predictor | 【15.1】 |
| 34 | 20260922 十目标完整结果 | 45/9/29/21/22/27/21/18/18/33，合计 **243** | 【15.1】 |
| 35 | 20260922 (0,0) = 45 | 通过 | 【15.1】+ **【232-P】R1/R9/R12–R14** |
| 36 | 12345 四目标结果 | 31 / 29(7 敏感) / 24 / 26，合计 **110** | 【15.1】 |
| 37 | Seed2 冲突结果 | 23 / 1 敏感 / 22 未解析 / 0 确定，争议格调度敏感 | 【15.1】 |
| 38 | PredictedOre certainty/source parity | `OreType / PredictionCertainty / OreSource / originViewer / conflictingWriters` 逐项一致 | 【15.1】 |
| 39 | 正式 Predictor 是否改算法 | **未改**（`seed/prediction`、`seed/worldgen` 零改动） | 【三】【十八】 |
| 40 | Dedicated Server 环境 | 本机 26.1.2 专用服务器，无改 worldgen 的 Mod | 【15.2】 |
| 41 | Dedicated Server Seed | `level-seed=20260922` | 【15.2】 |
| 42 | Client `getSingleplayerServer()` 实测 | **null** | 【15.2】 |
| 43 | Dedicated 下 Worker READY | `状态=已就绪` | 【15.2】 |
| 44 | Dedicated 下 (0,0) 结果 | **45** | 【15.2】 |
| 45 | 未加载远端 Chunk 测试 | (-400,380) 预测成功且预测后仍未加载 | 【15.2】 |
| 46 | ClientChunkCache 是否含目标 | 近端「有」/远端「**没有**」（运行时观测） | 【15.2】 |
| 47 | 是否读取远端 BlockState | 否：静态审计零命中 + 远端无该区块仍预测成功（运行时反证） | 【十六】 |
| 48 | UI Worker 状态 | 正在启动 → 已就绪 → 正在预测 → 异常（不显示「服务器不支持」） | 【十三】 |
| 49 | 230 输入矩阵回归 | 10 项全通过 | 【15.3】 |
| 50 | 230 生命周期回归 | 未进世界 / 进世界 / 开启 / 预测 / 改 Seed / 关闭 / 维度往返 / 退服 全通过 | 【15.3】 |
| 51 | personalMode 回归 | 开/关两种模式界面装配均通过 | 【15.3】 |
| 52 | Worker PID 生命周期 | 基线 → 强杀 → 重启 PID 更换、启动次数 +1；**【232-P】生产形态同样记录** | 【15.4】+ 232-P §二.14 |
| 53 | 是否有孤儿进程 | **0**（232 两次全量扫描 + **【232-P】14 次冒烟**） | 【十七】+ 232-P §二.14 |
| 54 | 生产式启动是否依赖 Gradle | 232 为推断（【二十】限制 1）；**【232-P】已在 jar-only 生产环境实机否定依赖** | 232-P §二.3–2.6 |
| 55 | Windows 路径测试 | 中文路径通过；**含空格路径【232-P】用三个实例补齐** | 【二十】限制 3 + 232-P §二.9 |
| 56 | `compileJava` | `EXIT=0` | 【二十一】21 |
| 57 | `build` | `EXIT=0`；正式产物流水线 `buildRelease` 在【232-P】修复后产出可用 jar | 【二十一】21 + 232-P §二.7 |
| 58 | `runClient` | 正常启动；Seed Mining OFF 时 Worker 不启动 | 【二十一】22 |
| 59 | AutoMiner 零变化证明 | 相关文件零改动（`git status`）+ 控制台仅新增「种子挖矿」页 | 【十八】【十九】 |
| 60 | 未做 Observation | 是（无 `CONFIRMED/MISSING/SUSPICIOUS` 接入） | 【十八】 |
| 61 | 未做 Renderer | 是 | 【十八】 |
| 62 | 未做 SeedValidation | 是 | 【十八】 |
| 63 | 未扩矿物 | 是（仅主世界钻石） | 【十八】 |
| 64 | 未做 26.2 | 是 | 【十八】 |
| 65 | 所有新增/修改/删除文件 | 逐文件列出（删除项：无） | 【十九】 |
| 66 | 已知限制 | 7 条 + **【232-P】新增 6 条**（启动器覆盖、干净新机、`maxHeapMb<256` 边界、出网、短暂监听、整包实测） | 【二十】+ 232-P §六 |
| 67 | 最终是否满足 232 验收 | **满足**：28 条通过标准全通过 + **【232-P】14 条发行门槛全通过** | 【二十一】+ 232-P §三 |

---

## 二十二、人工体验验收（很短，不需要看日志）

1. 进入一个多人测试服（普通服务器，**不需要装任何插件**）。
2. 打开自动挖矿控制台 → 「种子挖矿」页。
3. 填入种子（例如 `20260922`）→ 打开启用开关。
4. 点「测试当前区块预测」。
5. 依次应看到：**正在启动本地世界生成计算器…** → **正在预测当前区块…** → **预测完成**
   （首次启动约十秒，属正常）。
6. 退出服务器，再进一次：上一次结果不会残留，状态自动回到「等待进入世界」。

---

## 二十三、下一阶段

只有 232 通过后才进入《233 · 种子挖矿正式化第五阶段 —— 实际 Chunk Observation + 预测钻石世界渲染》：
把预测坐标与客户端真实已加载区块状态合起来做世界渲染，再逐步引入 `CONFIRMED` / `MISSING` / `SUSPICIOUS`；
仍然暂不接 AutoMiner。

---

## 附：本阶段产出物索引

| 产出 | 位置 |
| --- | --- |
| Worker vs 单人 Oracle 对照 | `run-26.1.2-seed-worker-parity-test/seedpoc-232-Worker对照.txt` |
| 真正多人验收 | `run-26.1.2-seed-worker-mp-test/seedpoc-232-多人验收.txt` |
| 进程生命周期与崩溃恢复 | `run-26.1.2-seed-worker-lifecycle-test/seedpoc-232-Worker生命周期.txt` |
| 230 服务层回归（Worker 路线复跑） | `run-26.1.2-seed-predict-test/seedpoc-服务层回归.txt` |
| 协议负路径 | `build/seed-worker-probe-neg-*/seed-worker-probe/probe-report.txt` |
| Worker 自身日志 | `run-26.1.2-seed-worker-*/yiyiaddon/seed-worker/26.1.2/seed-worker-26.1.2.log` |
| 内存采样 | `build/seed-memory-samples.txt` |
| 配套：生产发行验收（jar-only / 真实 Mod 环境 / Heap 矩阵 / 超时注入） | `232P-追加-种子挖矿真实用户生产发行验收报告.md`（同目录） |
| 配套：生产冒烟脚本与证据 | `gradle/production-smoke.ps1`、`02-开发报告/…/232P-证据/`（57 个文件） |
