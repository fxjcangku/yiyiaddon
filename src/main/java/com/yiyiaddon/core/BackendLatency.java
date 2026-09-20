package com.yiyiaddon.core;

/**
 * 到后端的网络延迟采样。
 *
 * <p>每次心跳都单独测一次会让请求量翻十倍，因此结果缓存 30 秒，两次心跳复用同一数值。
 * 测的是 {@code /api/ping}（零 D1 查询），所以这份「每 30 秒一次」的测量本身几乎不花额度。</p>
 *
 * <p><b>失败结果同样要落盘</b>：原先只在成功时写入 {@code value}，失败仅刷新 {@code measuredAt}，
 * 于是断网后 {@link #millis()} 会一直返回上一次成功时的旧值（甚至一小时前的），
 * 首页「后端状态」也就永远显示「正常」。缓存的意义是省请求，不是掩盖失败。</p>
 */
public final class BackendLatency {

    private static final long CACHE_MILLIS = 30_000L;

    private static volatile long value = -1L;
    private static volatile long measuredAt;

    private BackendLatency() {
    }

    /** @return 毫秒；最近一次实测失败（或从未测过）时返回 -1 */
    public static long millis() {
        long now = System.currentTimeMillis();
        if (measuredAt != 0L && now - measuredAt < CACHE_MILLIS) return value;

        value = HttpApi.measureLatency();
        measuredAt = now;
        return value;
    }
}
