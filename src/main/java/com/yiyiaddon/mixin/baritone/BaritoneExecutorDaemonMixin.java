package com.yiyiaddon.mixin.baritone;

import baritone.Baritone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 把 Baritone 共享线程池的线程改成守护线程 —— 修「退出游戏后进程卡住、被 26.2 关停看门狗判成崩溃」。
 *
 * <p><b>症状与取证</b>（用户 2026-09-21 报朋友闪退，附 PCL 日志与崩溃报告）：
 * 崩溃报告标题是 {@code Client shutdown from post-main}（{@code ClientShutdownWatchdog}），
 * {@code latest.log} 里 {@code Stopping!} 已正常存盘、没有任何异常行；线程转储只剩两个
 * <b>非守护线程</b> {@code pool-4-thread-1}（{@code baritone.cache.CachedWorld$PackerThread}）与
 * {@code pool-4-thread-2}（{@code CachedWorld.lambda$new$0} 定时存盘），两者都阻塞在队列上永不返回。
 * 非守护线程还在，{@code DestroyJavaVM} 就一直等，客户端主线程返回后 JVM 不退出，
 * 26.2 新增的关停看门狗超时后写出崩溃报告（进程存活约 47 秒、{@code Stopping!} 之后约 15 秒落报告）。</p>
 *
 * <p><b>根因</b>：{@code baritone.Baritone} 的静态字段 {@code threadPool} 由
 * {@code new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60s, new SynchronousQueue<>())} 直接构造
 * （字节码 {@code <clinit>} 的 0~24 号指令，等价 {@code Executors.newCachedThreadPool()}），
 * <b>没有传 ThreadFactory</b>，于是 JDK 默认工厂创建的是非守护线程；{@code baritone.cache.WorldData}
 * 又用 {@code Baritone.getExecutor()} 把 {@code CachedWorld} 的两个常驻任务提交进这个池
 * （进世界即发生），任务体是死循环，线程池因此永远留着两个非守护线程。</p>
 *
 * <p><b>为什么在这里修</b>：Baritone 是随包依赖（{@code maven-repo/} 下的本地构建），不在本仓库源码树内，
 * 改它的源码要另起工程重建；而它的线程在「类初始化完成」与「第一次提交任务」之间必然为 0 个，
 * 在这个窗口把线程工厂换掉，之后创建的线程全部是守护线程，且不影响任何既有调用方
 * （{@code setThreadFactory} 只作用于后续新建线程，池的容量 / 队列 / 保活语义一字未动）。</p>
 *
 * <p><b>为什么不改我们的关停流程</b>：我们的线程（{@code yiyiaddon-*}）全部已是守护线程，
 * 卡住的是 Baritone 的池；在关停时去 {@code shutdownNow} 这个池属于「替别人的池做清理」，
 * 一旦 Baritone 换实现或另有任务在跑就会引入新的竞态，不如从线程属性上根除。</p>
 *
 * <p><b>找不到类 / 注入失败时静默跳过</b>（本配置文件 {@code required: false}、{@code defaultRequire: 0}）：
 * 最多退回原来的「退出稍卡」，不会崩客户端。</p>
 */
@Mixin(value = Baritone.class, remap = false)
public abstract class BaritoneExecutorDaemonMixin {

    /**
     * 在 {@code baritone.Baritone} 类初始化末尾替换线程池的线程工厂。
     *
     * <p>{@code <clinit>} 的 TAIL 注入点位于「{@code threadPool} 已赋值」与「静态块返回」之间，
     * 因此 {@link Baritone#getExecutor()} 此时一定拿得到那个池（它就是 {@code threadPool} 本身，
     * 字节码 {@code getstatic threadPool; areturn}）。</p>
     *
     * <p>线程名沿用可辨认的前缀，便于玩家在崩溃报告的线程转储里一眼看出归属；守护属性是本注入的唯一目的。</p>
     */
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void yiyiaddon$daemonizeExecutor(CallbackInfo ci) {
        if (!(Baritone.getExecutor() instanceof ThreadPoolExecutor pool)) return;
        AtomicInteger index = new AtomicInteger();
        pool.setThreadFactory(runnable -> {
            Thread thread = new Thread(runnable, "Baritone-Worker-" + index.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
    }
}
