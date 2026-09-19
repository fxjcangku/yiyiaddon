package com.yiyiaddon.core;

import java.util.ArrayDeque;

/**
 * 服务器刻率（TPS）估算：统计最近一个滑动窗口内的客户端刻数，换算成「刻/秒」。
 *
 * <p><b>为什么自研：</b>旧项目用旧框架的 {@code TickRate.INSTANCE.getTickRate()} 作为
 * 战术协调器的数据源（每 20 刻采一次、低于 18.0 判服务器卡顿）。新架构不依赖任何第三方
 * 客户端框架，因此按同样的口径自行统计：客户端刻由服务端刻驱动，服务端卡顿时客户端刻
 * 间隔被拉长，窗口内刻数下降即反映为 TPS 降低。</p>
 *
 * <p>全部方法只在客户端主线程调用（每刻由 {@code EventDispatcher} 采样），无并发问题。</p>
 */
public final class TickRateMonitor {

    /** 滑动窗口：与旧项目「约 1 秒采样一次」的口径配合，用 5 秒窗口平滑瞬时抖动 */
    private static final long WINDOW_MS = 5000L;

    /** 样本不足时返回 0（表示数据未就绪，调用方不得据此判卡顿） */
    private static final int MIN_SAMPLES = 20;

    private static final ArrayDeque<Long> STAMPS = new ArrayDeque<>();

    private TickRateMonitor() {
    }

    /** 每客户端刻采样一次；超出窗口的旧样本被丢弃 */
    public static void sample() {
        long now = System.currentTimeMillis();
        STAMPS.addLast(now);
        long cutoff = now - WINDOW_MS;
        while (!STAMPS.isEmpty() && STAMPS.peekFirst() < cutoff) {
            STAMPS.removeFirst();
        }
    }

    /** 最近窗口内的刻率；样本不足返回 0 */
    public static float tickRate() {
        if (STAMPS.size() < MIN_SAMPLES) return 0F;
        Long first = STAMPS.peekFirst();
        Long last = STAMPS.peekLast();
        if (first == null || last == null) return 0F;
        long span = last - first;
        if (span <= 0L) return 0F;
        return (float) ((STAMPS.size() - 1) * 1000.0 / span);
    }

    /** 清空样本（断线时调用，避免把上一台服务器的刻率带到新会话） */
    public static void clear() {
        STAMPS.clear();
    }
}
