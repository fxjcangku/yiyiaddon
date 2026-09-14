package com.yiyiaddon.core;

/**
 * 到后端的网络延迟采样。
 *
 * <p>每次心跳都单独测一次会让请求量翻倍，因此结果缓存 30 秒，两次心跳复用同一数值。</p>
 */
public final class BackendLatency {

    private static final long CACHE_MILLIS = 30_000L;

    private static volatile long value = -1L;
    private static volatile long measuredAt;

    private BackendLatency() {
    }

    /** @return 毫秒；从未测量成功过时返回 -1 */
    public static long millis() {
        long now = System.currentTimeMillis();
        if (measuredAt != 0L && now - measuredAt < CACHE_MILLIS) return value;

        long measured = HttpApi.measureLatency();
        if (measured >= 0L) value = measured;
        measuredAt = now;
        return value;
    }
}
