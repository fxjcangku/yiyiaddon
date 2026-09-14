package com.yiyiaddon.ui.anim;

/**
 * 缓动曲线：只提供纯函数，不持有任何状态，供其余动画类复用。
 *
 * <p>输入统一为归一化进度 0~1，输出可略大于 1（回弹类曲线）。</p>
 */
public final class Easing {

    /** 标准回弹过头系数，峰值约 1.10。 */
    private static final float BACK_C1 = 1.70158f;
    private static final float BACK_C3 = BACK_C1 + 1f;

    private Easing() {
    }

    /** 把任意值钳制到 0~1。 */
    public static float clamp01(float value) {
        if (value < 0f) return 0f;
        return value > 1f ? 1f : value;
    }

    /** 先快后慢，收尾平滑；用于大多数状态过渡。 */
    public static float easeOutCubic(float t) {
        float x = 1f - clamp01(t);
        return 1f - x * x * x;
    }

    /**
     * 回弹曲线：末段冲过目标后再回落。
     *
     * <p>峰值约 1.10（过冲 10%），t=1 时精确返回 1。</p>
     */
    public static float easeOutBack(float t) {
        float x = clamp01(t);
        float p = x - 1f;
        return 1f + BACK_C3 * p * p * p + BACK_C1 * p * p;
    }
}
