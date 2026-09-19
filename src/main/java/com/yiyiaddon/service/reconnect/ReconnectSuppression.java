package com.yiyiaddon.service.reconnect;

/**
 * 主动断线的抑制窗口：告诉自动重连「这次断开是我自己按的，别连回来」。
 *
 * <p><b>为什么需要它</b>：断线重连的触发时机是「断线界面出现」，它区分不了「服务器踢人」与
 * 「本模组主动断开」。管理员检测的「§c立即断线」是保命动作 —— 断完立刻被自己家的重连模块连回去，
 * 等于这个动作白按。旧项目用「断线前关掉第三方 AutoReconnect」解决同一问题；本项目零第三方依赖，
 * 对应物就是这个抑制窗口。</p>
 *
 * <p><b>用时间窗口而不是 tick 计数</b>：抑制要跨越「断开 → 断线界面出现 → 模块处理」这一小段主线程
 * 流程，期间不一定有 tick 推进（例如断线发生在 tick 尾部），用墙钟更稳；窗口内的多次判定都返回
 * 「抑制中」，窗口过后自动失效，不需要任何一方去清理。</p>
 */
public final class ReconnectSuppression {

    /** 抑制窗口长度：足够覆盖「断开 → 断线界面 → 模块处理」的一次完整流程 */
    private static final long WINDOW_MILLIS = 5_000L;

    private static volatile long untilMillis;

    private ReconnectSuppression() {
    }

    /**
     * 主动断开前调用：随后 {@link #WINDOW_MILLIS} 毫秒内的断线不触发自动重连。
     *
     * @param millis 窗口长度（毫秒），非正数按默认窗口处理
     */
    public static void suppress(long millis) {
        long window = millis > 0L ? millis : WINDOW_MILLIS;
        untilMillis = System.currentTimeMillis() + window;
    }

    /** 当前是否处于抑制窗口内（断线处理里第一步就查它） */
    public static boolean suppressed() {
        return System.currentTimeMillis() < untilMillis;
    }
}
