package com.yiyiaddon.ui.anim;

/**
 * 二阶弹簧插值器：让一个数值带阻尼地跟随目标值。
 *
 * <p>数值积分用半隐式欧拉，并按固定子步长细分，保证帧长变化时结果稳定。
 * 两个静态工厂分别给出「不过冲」与「指定过冲」两种手感。</p>
 */
public final class Spring {

    /** 单次积分子步长：过大在大帧长下会发散。 */
    private static final float MAX_STEP = 0.008f;
    /** 单帧最大推进时间，防止卡顿后一次跳变。 */
    private static final float MAX_DELTA = 0.05f;

    private final float stiffness;
    private final float damping;

    private float value;
    private float velocity;
    private float target;

    private Spring(float stiffness, float damping) {
        this.stiffness = stiffness;
        this.damping = damping;
    }

    /**
     * 临界阻尼弹簧：不过冲。
     *
     * @param settleSeconds 从静止到达目标附近（约 2% 误差）所需秒数
     */
    public static Spring critical(float settleSeconds) {
        float omega = 5.8f / Math.max(0.001f, settleSeconds);
        return new Spring(omega * omega, 2f * omega);
    }

    /**
     * 欠阻尼弹簧：会冲过目标再回落。
     *
     * @param peakSeconds    从静止首次到达目标的秒数
     * @param overshootRatio 过冲比例，0.08 表示约 8%
     */
    public static Spring overshoot(float peakSeconds, float overshootRatio) {
        float ratio = Math.max(0.001f, Math.min(0.9f, overshootRatio));
        float log = (float) Math.log(ratio);
        float zeta = -log / (float) Math.sqrt(Math.PI * Math.PI + log * log);
        float omega = (float) (Math.PI / (Math.max(0.001f, peakSeconds) * Math.sqrt(1f - zeta * zeta)));
        return new Spring(omega * omega, 2f * zeta * omega);
    }

    /** 直接落到指定值并清零速度。 */
    public void set(float newValue) {
        value = newValue;
        target = newValue;
        velocity = 0f;
    }

    public void setTarget(float newTarget) {
        target = newTarget;
    }

    public float target() {
        return target;
    }

    public float value() {
        return value;
    }

    /** 是否已稳定在目标上。 */
    public boolean isSettled() {
        return Math.abs(target - value) < 5e-4f && Math.abs(velocity) < 5e-4f;
    }

    public void update(float dt) {
        float step = Math.min(Math.max(dt, 0f), MAX_DELTA);
        int steps = Math.max(1, (int) Math.ceil(step / MAX_STEP));
        float h = step / steps;
        for (int i = 0; i < steps; i++) {
            float accel = stiffness * (target - value) - damping * velocity;
            velocity += accel * h;
            value += velocity * h;
        }
        if (isSettled()) {
            value = target;
            velocity = 0f;
        }
    }
}
