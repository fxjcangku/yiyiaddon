# 171 - 复盘：退出游戏被关停看门狗判成崩溃（Baritone 线程池非守护线程）

日期：2026-09-21
分支：`master` + `port/26.2`（业务改动先落主线，再 cherry-pick）
验证：`.\gradlew build` EXIT=0；**实机验证通过**（真实 Fabric 实例起客户端 → 线程转储确认守护属性 → 优雅关停 1.1 秒退出、无崩溃报告）

## 一、用户报告

> 朋友用 26.2 发布包进单人世界后闪退，附 PCL 启动日志、`latest.log`、崩溃报告与启动脚本。

崩溃报告：`Description: Client shutdown from post-main`，`java.lang.Error: Watchdog (Client shutdown from post-main)`。

## 二、取证（第 167 条）

时间线（朋友那份 `latest.log`，全程 155 行、**0 异常行**）：

| 时刻 | 事件 |
| --- | --- |
| 15:56:57 | 启动、加载 55 个模组（含 `yiyiaddon 1.0-beta1`） |
| 15:57:15 | 进入单人世界「新的世界」，Baritone 缓存开始加载区域 |
| 15:57:27 | `Stopping!` → 保存世界 → **全部维度存盘完成**（正常退出流程走完） |
| 15:57:42 | 关停看门狗写出崩溃报告（距 `Stopping!` 约 15 秒） |

线程转储里，`DestroyJavaVM` 之后仍活着的**非守护线程**只有两个：

```
"pool-4-thread-1" prio=5 Id=126 WAITING
    at baritone.cache.CachedWorld$PackerThread.run(CachedWorld.java:312)
"pool-4-thread-2" prio=5 Id=127 TIMED_WAITING
    at baritone.cache.CachedWorld.lambda$new$0(CachedWorld.java:95)
```

本模组自己的线程（`yiyiaddon-heartbeat` / `yiyiaddon-home-stats` / `yiyiaddon-remote-config`）全部带 `daemon` 标记，与本次无关。

字节码取证（`javap -p -c`，随包 `maven-repo/baritone/baritone-fabric/1.18.0`）：

```
baritone.Baritone <clinit>:
  0: new  java/util/concurrent/ThreadPoolExecutor
  4: iconst_4                       // corePoolSize = 4
  5: ldc_w 2147483647               // maxPoolSize = MAX
  8: ldc2_w 60l + TimeUnit.SECONDS  // keepAlive = 60s
 14: new  java/util/concurrent/SynchronousQueue
 21: invokespecial ThreadPoolExecutor.<init>:(IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;)V
 24: putstatic threadPool
```

即 `Executors.newCachedThreadPool()` 的手写等价物，**没有传 ThreadFactory** → JDK 默认工厂产出非守护线程（`pool-N-thread-M`）。
`baritone.cache.WorldData` 用 `Baritone.getExecutor()`（方法体就是 `getstatic threadPool; areturn`）把 `CachedWorld` 的打包线程与定时存盘线程提交进这个池，两个任务体都是死循环（`take()` / `sleep`），线程永不回收。

**结论**：进过世界后，JVM 里必然长期挂着两个非守护线程 → 主线程返回后 `DestroyJavaVM` 一直等 → 26.2 新增的 `ClientShutdownWatchdog` 超时后写出「崩溃」报告。这是随包 Baritone 的缺陷被 26.2 的新看门狗放大，不是我们自己的线程问题。

## 三、修复（最小改动，一处注入）

新增 `com.yiyiaddon.mixin.baritone.BaritoneExecutorDaemonMixin`（并在 `yiyiaddon.baritone.mixins.json` 登记）：

- 注入 `baritone.Baritone.<clinit>` 的 `@At("TAIL")` —— 该时刻 `threadPool` 已赋值、且**任何任务都还没来得及提交**（类未初始化完别人拿不到它），在这唯一的空窗里调用 `ThreadPoolExecutor#setThreadFactory`，换成守护线程工厂（线程名 `Baritone-Worker-N`，便于在崩溃报告的转储里辨认归属）。
- 只影响后续新建线程的守护属性；池的容量、队列、保活语义一字未动，Baritone 其它调用方（`ExploreProcess` 等共用同一池）不受影响。
- 配置沿用既有 Baritone 混入配置（`required: false` / `defaultRequire: 0`）：注入失败最多退回「退出稍卡」，不会崩客户端。

不改的地方（第 192 条最小化）：

- 不动我们的关停流程、不加 `System.exit` / `halt`：卡的是 Baritone 的池，替别人的池做 `shutdownNow` 会在 Baritone 换实现或另有任务在跑时引入竞态。
- 不重建 Baritone 构件：`baritone.Baritone` 不在本仓库源码树内（是 `maven-repo/` 下的本地构建），改源要另起工程，代价远大于一处注入。
- 26.2 线同样适用：`baritone-fabric-1.19.0` 的 `<clinit>` 逐条比对与 1.18.0 **完全一致**（同参数、同无工厂），属同一处业务修复，无 API 差异。

## 四、验证

1. `.\gradlew build --console=plain -q` → EXIT=0。
2. **实机**（第 235 条同一套实例法）：用工作区实例跑真实客户端（Fabric Loader 0.19.5 + Fabric API 0.155.2+26.1.2 + 本次个人版产物），进单人世界后 `jstack` 抓转储：

```
"Baritone-Worker-1" #96 [34060] daemon prio=10 ... waiting on condition
"Baritone-Worker-2" #97 [32248] daemon prio=10 ... waiting on condition
```

两个线程已改名为 `Baritone-Worker-*` 且带 **daemon** 标记（修复前是 `pool-4-thread-1/2`、无 daemon）。
3. **优雅关停计时**：对客户端进程发 `taskkill`（不带 `/F`，等同点窗口关闭按钮）→ 进程 **1.1 秒内退出**（修复前该路径会挂到看门狗超时约 15 秒）；实例 `crash-reports` 目录**不存在**，日志末尾以「全部维度存盘完成」正常收尾，无看门狗报告。
4. 验证实例建在工作区（沙箱只允许写 `d:\mcaddon`，`D:\mcv` 被拦），验证后已删除，未留痕。

## 五、影响面

- 线上 `v1.0-beta1` 两个发布包（26.1.2 与 26.2）都还没带这次修复：**任何玩家进过世界后正常退出游戏，都会在 26.2 上看到一份「客户端崩溃」报告**（26.1.2 没有这个看门狗，表现为进程多留十几秒）。
- 结论边界：本次只修「退出卡住被误判崩溃」这一条；朋友的报告里没有别的异常行，未发现第二个缺陷。

## 六、遗留

- **需要重出发布包**：修复已在源码，但线上附件未更新；是否立即同标签覆盖（与上次一致、版本号不变）由用户决定。
- 本报告第四节第 3 步的「优雅关停计时」是本次新加的验收手段（此前验收脚本是到点强杀进程，**永远看不到关停路径的问题**）—— 建议以后出包都把这一步补上。
