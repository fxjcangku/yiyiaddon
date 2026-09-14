package com.yiyiaddon.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 后台任务调度：所有网络与采样工作都在守护线程上进行，不阻塞渲染线程。
 */
public final class BackgroundTasks {

    private BackgroundTasks() {
    }

    /** 起一个具名守护线程立即执行一次任务。 */
    public static Thread run(String name, Runnable task) {
        Thread thread = new Thread(task, name);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    /**
     * 创建单线程定时器，按固定周期执行任务。
     *
     * @param name   线程名
     * @param period 周期间隔
     * @param task   任务体，内部异常会被吞掉，避免单次失败终止整个定时器
     */
    public static ScheduledExecutorService schedule(String name, long period, TimeUnit unit, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, name);
                thread.setDaemon(true);
                return thread;
            }
        });
        executor.scheduleAtFixedRate(() -> {
            try {
                task.run();
            } catch (Throwable ignored) {
                // 后台任务失败不影响游戏，忽略并等待下一个周期。
            }
        }, 0L, period, unit);
        return executor;
    }
}
