package com.yiyiaddon.integration.baritone;

import baritone.Baritone;
import com.yiyiaddon.YiyiAddon;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 把 Baritone 共享线程池的线程改成守护线程 —— 修「退出游戏后进程卡住、被 26.2 关停看门狗判成崩溃」。
 *
 * <p><b>为什么要动</b>（用户 2026-09-21 报朋友闪退，附 PCL 日志与崩溃报告）：
 * 26.2 的崩溃报告标题是 {@code Client shutdown from post-main}（{@code ClientShutdownWatchdog}），
 * {@code latest.log} 里 {@code Stopping!} 已正常存盘、0 异常行，线程转储只剩两个<b>非守护线程</b>
 * {@code pool-4-thread-1}（{@code baritone.cache.CachedWorld$PackerThread}）与
 * {@code pool-4-thread-2}（{@code CachedWorld} 的定时存盘任务），两者都阻塞在队列上永不返回；
 * 非守护线程还在，{@code DestroyJavaVM} 就一直等，主线程返回后 JVM 不退出，看门狗超时后写出「崩溃」报告。</p>
 *
 * <p><b>根因</b>：{@code baritone.Baritone} 的静态字段 {@code threadPool} 由
 * {@code new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60s, new SynchronousQueue<>())} 直接构造
 * （字节码 {@code <clinit>} 的 0~24 号指令，等价 {@code Executors.newCachedThreadPool()}），
 * <b>没有传 ThreadFactory</b>，JDK 默认工厂产出的是非守护线程；{@code baritone.cache.WorldData} 与
 * {@code CachedWorld} 又用 {@code Baritone.getExecutor()}（方法体就是 {@code getstatic threadPool; areturn}）
 * 把两个常驻任务提交进这个池，进过世界就永久留着两个非守护线程。</p>
 *
 * <p><b>为什么放在集成层而不是 Mixin</b>：本修复最初写成 {@code @Inject} 到
 * {@code baritone.Baritone.<clinit>} 的混入（26.1.2 线实测生效）。但 26.2 线上这条混入在
 * <b>准备阶段就被丢掉</b>：混入配置列了 8 条、日志只「Prepared 7」、且从未出现
 * {@code Mixing BaritoneExecutorDaemonMixin into baritone.Baritone}，同批其它 baritone 混入
 * （{@code api} / {@code command} 包）正常生效，两条线的类字节、注解、类文件版本（65）逐项一致，
 * 真实原因在混入框架内部不可见。改走这里：纯代码调用它的公开入口，两条线同一份实现、行为可读可测。</p>
 *
 * <p><b>时机</b>：由 {@code YiyiAddon#onInitialize} 调用，早于任何世界加载。池的线程在
 * <b>第一次提交任务时</b>才创建（类初始化只建池对象，实测首个 {@code pool-4-thread-1} 出现在进世界之后），
 * 因此在模组初始化时换掉线程工厂，之后创建的线程全是守护线程。若调用时池里已有线程（说明别处更早提交过任务），
 * 只记一条调试日志，不做任何破坏性动作。</p>
 */
public final class BaritoneThreadPool {

    /** 已处理过就不重复处理（初始化路径可能被多次走到） */
    private static boolean daemonized;

    private BaritoneThreadPool() {
    }

    /**
     * 把 Baritone 共享线程池的线程工厂换成守护线程工厂。
     *
     * <p>幂等；Baritone 缺失或结构变化时静默返回（最多退回原来的「退出稍卡」，不会崩客户端）。</p>
     */
    public static void daemonize() {
        if (daemonized) return;
        daemonized = true;
        try {
            Executor executor = Baritone.getExecutor();
            if (!(executor instanceof ThreadPoolExecutor pool)) {
                YiyiAddon.LOGGER.debug("Baritone 线程池不是 ThreadPoolExecutor，跳过守护线程改造：{}",
                    executor == null ? "null" : executor.getClass().getName());
                return;
            }
            if (pool.getPoolSize() > 0) {
                YiyiAddon.LOGGER.debug("Baritone 线程池已有 {} 个线程在跑，本次只影响后续新建线程", pool.getPoolSize());
            }
            AtomicInteger index = new AtomicInteger();
            pool.setThreadFactory(runnable -> {
                Thread thread = new Thread(runnable, "Baritone-Worker-" + index.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            });
        } catch (Throwable error) {
            YiyiAddon.LOGGER.debug("Baritone 线程池守护线程改造失败（不影响功能，退出时可能稍卡）", error);
        }
    }
}
