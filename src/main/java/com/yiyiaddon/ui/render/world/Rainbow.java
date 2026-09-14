package com.yiyiaddon.ui.render.world;

/**
 * 彩虹色：色相随时间循环，饱和度与亮度固定为满值。
 *
 * <p>与「每刻刷新颜色对象」的做法不同，这里在绘制瞬间按单调时钟直接算色相，
 * 因此刷新率与帧率一致，不受刻率限制，也不会因为没人调用 update 而卡住变色。</p>
 */
public final class Rainbow {

    /** 默认速度：每秒推进 1/8 个色环，即 8 秒走完一轮。 */
    public static final double DEFAULT_SPEED = 0.125d;

    /** 速度上限（每秒色环数），防止配置出病态值把画面变成频闪。 */
    public static final double MAX_SPEED = 4d;

    private Rainbow() {
    }

    /**
     * 取当前彩虹色。
     *
     * @param speed  每秒推进的色环数
     * @param offset 相位偏移（0-1），用于让多个目标错开颜色
     * @param alpha  透明度 0-255
     */
    public static int argb(double speed, double offset, int alpha) {
        float hue = (float) ((nowSeconds() * clampSpeed(speed) + offset) % 1d);
        if (hue < 0f) hue += 1f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f) & 0xFFFFFF;
        return ((alpha & 0xFF) << 24) | rgb;
    }

    public static int argb(int alpha) {
        return argb(DEFAULT_SPEED, 0d, alpha);
    }

    /** 把速度夹到合法区间。 */
    public static double clampSpeed(double speed) {
        if (speed < 0d) return 0d;
        return Math.min(speed, MAX_SPEED);
    }

    private static double nowSeconds() {
        return System.nanoTime() / 1_000_000_000d;
    }
}
