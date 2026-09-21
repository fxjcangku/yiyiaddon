package com.yiyiaddon.ui.anim;

/**
 * 动画时钟：把每帧的真实间隔压成接近常数的步长。
 *
 * <p><b>为什么动画不能直接吃真实 dt</b>（用户 2026-09-21：「模块中心下拉卡顿严重 有顿挫感觉 不流畅」
 * 「特别是展开多个分组的时候…滑块就在抖动」）：这台客户端一帧多长由世界渲染决定。JFR 实测
 * （录音 {@code ui-jfr2.jfr}，面板开着录了 3.5 分钟）：限速器整段只在 Render thread 上 park 了 2 次
 * （帧率不是被 260 上限卡住的）、Render thread 只占整机 1.3% CPU（UI 开销根本排不进热点）、
 * 渲染线程无锁等待；帧长随区块构建 / 云层 / GC 起伏（GC 每约 8 秒一次、2~6ms 停顿，偶发 9~16ms）。
 * 动画按真实 dt 推进时，每帧走的像素数忽多忽少，眼睛看到的就是「一步大一步小」的顿挫 ——
 * 原版界面的动画是「每帧固定增量」，帧长再抖每帧位移都一样，所以看着是顺的。</p>
 *
 * <p>这里用一阶低通把间隔平滑成缓变量：某一帧偶尔变长时，动画这一步只多走一点点，视觉上仍是均匀位移；
 * 低通只压抖动、不压平均 —— 长期平均步长仍等于真实帧间隔，低帧率下动画总时长不变（仍然帧率无关）。</p>
 */
public final class FrameClock {

    /** 低通时间常数（秒）：越大越平，越小越跟手。 */
    private static final float TAU = 0.12f;
    /** 单帧最大推进（秒）：长卡顿后不让动画一步跳完（等于 30 帧/秒的那一步）。 */
    private static final float MAX_STEP = 1f / 30f;
    /** 超过这个间隔视为断档（切窗口、资源重载、截屏），从这一步重新起算，不把断档时间补进动画。 */
    private static final float RESTART_GAP = 0.25f;

    private float smoothed = -1f;

    /**
     * 投入本帧的真实间隔（秒），取回本帧动画应当推进的步长（秒）。
     *
     * @param rawDt 本帧与上帧的真实间隔；{@code <= 0}（同毫秒重复调用 / 首帧占位）不推进
     */
    public float tick(float rawDt) {
        if (!(rawDt > 0f)) return 0f;
        if (rawDt > RESTART_GAP) {
            smoothed = -1f;
            return MAX_STEP;
        }
        smoothed = smoothed < 0f
                ? rawDt
                : smoothed + (rawDt - smoothed) * (1f - (float) Math.exp(-rawDt / TAU));
        return Math.min(smoothed, MAX_STEP);
    }
}
