package com.yiyiaddon.ui.anim;

import io.github.humbleui.skija.Canvas;

/**
 * 按压状态：元素被按下时缩到 0.97，松开后用回弹曲线恢复到 1.0。
 *
 * <p>进度 {@code progress}：0 表示未按下，1 表示完全按下。以元素中心为原点的画布变换
 * 由 {@link #applyAt} / {@link #apply} 统一提供，调用方拿到 true 就负责配对的
 * {@code canvas.restore()}，避免每个绘制点各写一份 save/translate/scale/restore。</p>
 *
 * <p>没有「松开」回调的控件（如一次性按钮）使用 {@link #pulse()}：按下后自动回弹。</p>
 */
public final class PressState {

    /** 按下时长：过短会看不出，过长会显得迟钝。 */
    private static final float PRESS_DURATION = 0.09f;
    /** 回弹时长。 */
    private static final float RELEASE_DURATION = 0.26f;
    /** 按压缩放深度：1.0 - 0.03 = 0.97。 */
    private static final float PRESS_DEPTH = 0.03f;

    private float progress;
    private float pressElapsed;
    private float releaseElapsed;
    private float releaseFrom;
    private boolean pressing;
    private boolean releasing;
    private boolean autoRelease;

    /** 按下。 */
    public void press() {
        pressing = true;
        releasing = false;
        pressElapsed = 0f;
        autoRelease = false;
    }

    /** 松开：从当前进度回弹到 0。 */
    public void release() {
        if (!pressing && !releasing) {
            autoRelease = false;
            return;
        }
        pressing = false;
        releasing = true;
        releaseElapsed = 0f;
        releaseFrom = progress;
        autoRelease = false;
    }

    /** 按下后自动回弹，供没有松开回调的控件使用。 */
    public void pulse() {
        press();
        autoRelease = true;
    }

    /** 直接复位到未按下状态，不播放回弹；用于元素已被丢弃、后续不会再绘制时。 */
    public void cancel() {
        progress = 0f;
        pressElapsed = 0f;
        releaseElapsed = 0f;
        releaseFrom = 0f;
        pressing = false;
        releasing = false;
        autoRelease = false;
    }

    /** 是否处于静止（既没按下也没在回弹）。 */
    public boolean isIdle() {
        return !pressing && !releasing;
    }

    /** 当前应使用的缩放系数，1.0 表示未按下。 */
    public float scale() {
        return 1f - PRESS_DEPTH * progress;
    }

    /** 当前按压进度，0 表示未按下、1 表示按到底。 */
    public float progress() {
        return progress;
    }

    /**
     * 以给定中心为原点应用按压缩放。
     *
     * @return true 表示已经 save，绘制完成后必须 canvas.restore()
     */
    public boolean applyAt(Canvas canvas, float centerX, float centerY) {
        float scale = scale();
        if (Math.abs(scale - 1f) < 0.0005f) return false;
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.scale(scale, scale);
        canvas.translate(-centerX, -centerY);
        return true;
    }

    /**
     * 以元素矩形中心为原点应用按压缩放。
     *
     * @return true 表示已经 save，绘制完成后必须 canvas.restore()
     */
    public boolean apply(Canvas canvas, float x, float y, float width, float height) {
        return applyAt(canvas, x + width / 2f, y + height / 2f);
    }

    public void update(float dt) {
        if (pressing) {
            pressElapsed += dt;
            progress = Easing.easeOutCubic(Math.min(1f, pressElapsed / PRESS_DURATION));
            if (autoRelease && pressElapsed >= PRESS_DURATION) {
                pressing = false;
                releasing = true;
                releaseElapsed = 0f;
                releaseFrom = progress;
                autoRelease = false;
            }
        } else if (releasing) {
            releaseElapsed += dt;
            float t = Math.min(1f, releaseElapsed / RELEASE_DURATION);
            progress = releaseFrom * (1f - Easing.easeOutBack(t));
            if (t >= 1f) {
                releasing = false;
                progress = 0f;
            }
        }
    }
}
