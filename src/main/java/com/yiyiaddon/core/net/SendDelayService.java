package com.yiyiaddon.core.net;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 发包延迟队列：被规则判定为 {@link SendDecision.Action#DELAY} 的包在这里排队，到点由主线程直发。
 *
 * <p>逐字移植旧项目发包防踢里「网络延迟」子功能的实现：延迟毫秒在
 * {@code [最小延迟, 最大延迟]} 内随机，入队时刻取「期望时刻」与「上一个包之后 1ms」的较大值
 * 以保证发出顺序；5 毫秒粒度的 daemon 线程轮询到期项，再切回客户端主线程实际发送
 * （网络对象只能在主线程安全使用）。</p>
 *
 * <p>关模块或断线时调用 {@link #clear()}：待发包直接丢弃，不补发（旧实现同样语义）。</p>
 */
public final class SendDelayService {

    /** 轮询周期（毫秒）：与旧实现一致，保证 20~80ms 级别的延迟精度 */
    private static final long POLL_INTERVAL_MS = 5L;

    private record Delayed(Connection connection, Packet<?> packet, long sendAt) {
    }

    private static final PriorityQueue<Delayed> QUEUE = new PriorityQueue<>(Comparator.comparingLong(Delayed::sendAt));

    private static ScheduledExecutorService worker;

    /** 上一个包的期望发出时刻，用于保证顺序 */
    private static long lastSendAt;

    private SendDelayService() {
    }

    /** 入队（网络线程调用）：毫秒数由规则给出 */
    static void enqueue(Connection connection, Packet<?> packet, long delayMillis) {
        if (connection == null || packet == null) return;
        synchronized (QUEUE) {
            long now = System.currentTimeMillis();
            long sendAt = Math.max(now + Math.max(0L, delayMillis), lastSendAt + 1L);
            lastSendAt = sendAt;
            QUEUE.add(new Delayed(connection, packet, sendAt));
            ensureWorker();
        }
    }

    /** 丢弃全部待发包（关模块 / 断线） */
    public static void clear() {
        synchronized (QUEUE) {
            QUEUE.clear();
            lastSendAt = 0L;
        }
    }

    /** 当前待发数量（自检与调试用） */
    public static int pendingCount() {
        synchronized (QUEUE) {
            return QUEUE.size();
        }
    }

    private static void ensureWorker() {
        if (worker != null) return;
        worker = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "yiyiaddon-SendDelay");
            thread.setDaemon(true);
            return thread;
        });
        worker.scheduleAtFixedRate(SendDelayService::drain, 0L, POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    private static void drain() {
        long now = System.currentTimeMillis();
        List<Delayed> due = new ArrayList<>();
        synchronized (QUEUE) {
            while (!QUEUE.isEmpty() && QUEUE.peek().sendAt() <= now) {
                due.add(QUEUE.poll());
            }
        }
        if (due.isEmpty()) return;

        Minecraft client = Minecraft.getInstance();
        for (Delayed delayed : due) {
            client.execute(() -> {
                if (delayed.connection().isConnected()) {
                    ClientPacketSender.dispatch(delayed.connection(), delayed.packet());
                }
            });
        }
    }
}
