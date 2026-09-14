package com.yiyiaddon.ui.anim;

/**
 * 滚动缓动：当前值以固定时间常数逼近目标值。
 *
 * <p>用指数逼近（帧率无关），末端直接钳制在有效区间内，因此不会越界回弹。
 * 拖动滚动条这类需要即时跟手的场景用 {@link #jumpTo(float)} 跳过缓动。</p>
 */
public final class ScrollTracker {

    /** 时间常数（秒）：越小跟手越快。 */
    private static final float TIME_CONSTANT = 0.085f;
    /** 与目标距离小于该值时直接吸附，避免长期残留微小差值。 */
    private static final float SNAP_EPSILON = 0.05f;

    private float value;
    private float target;

    public void setTarget(float newTarget) {
        target = newTarget;
    }

    public float target() {
        return target;
    }

    public float value() {
        return value;
    }

    /** 立即定位，不经过缓动。 */
    public void jumpTo(float newValue) {
        value = newValue;
        target = newValue;
    }

    /**
     * 推进一帧。
     *
     * @param min 允许的最小值（含）
     * @param max 允许的最大值（含）
     * @return 推进后的当前值
     */
    public float update(float dt, float min, float max) {
        float lo = Math.min(min, max);
        float hi = Math.max(min, max);
        target = Math.max(lo, Math.min(hi, target));
        float k = 1f - (float) Math.exp(-Math.max(dt, 0f) / TIME_CONSTANT);
        value += (target - value) * k;
        value = Math.max(lo, Math.min(hi, value));
        if (Math.abs(target - value) < SNAP_EPSILON) {
            value = target;
        }
        return value;
    }
}
