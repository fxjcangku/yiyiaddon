package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.anim.ScrollTracker;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

/**
 * 内容区滚动：滚动缓动、滚动条几何、命中与拖动。
 *
 * <p>只负责「滚动到哪里、滚动条画在哪里」，内容区的坐标与裁剪由界面决定。滚动上限由
 * 内容总高与可视高度得出，每帧绘制前调用一次 {@link #layout}。主界面与模块页共用同一套
 * 滚动行为，避免两处各写一份几何。</p>
 */
public final class ScrollViewport {

    /** 滚动条宽度与圆角。 */
    private static final float TRACK_WIDTH = 4f;
    private static final float TRACK_RADIUS = 2f;
    /** 滚动条命中区域的左右扩展量。 */
    private static final float HIT_PADDING = 6f;
    /** 滚动条最小高度，避免内容很长时滑块细到抓不住。 */
    private static final float MIN_THUMB_HEIGHT = 20f;
    /** 滚轮一格折算的内容位移。 */
    private static final float WHEEL_STEP = 16f;

    private final ScrollTracker tracker = new ScrollTracker();
    private final Paint trackPaint = new Paint().setAntiAlias(true);
    private final Paint thumbPaint = new Paint().setAntiAlias(true);

    private float pendingTarget;
    private float contentHeight;
    private float viewportHeight;
    private float maxScroll;
    private float dragOffset;

    /** 记录内容总高与可视高度，重算滚动上限。 */
    public void layout(float contentHeight, float viewportHeight) {
        this.contentHeight = Math.max(0f, contentHeight);
        this.viewportHeight = Math.max(0f, viewportHeight);
        maxScroll = Math.max(0f, this.contentHeight - this.viewportHeight);
    }

    /** 当前滚动的显示值（已缓动）。 */
    public float value() {
        return tracker.value();
    }

    /** 当前滚动目标（已钳制到有效区间）。 */
    public float target() {
        return tracker.target();
    }

    public float maxScroll() {
        return maxScroll;
    }

    /** 设定滚动目标，推进时才钳制到有效区间。 */
    public void setTarget(float value) {
        pendingTarget = value;
    }

    /** 立即定位，不经过缓动；拖动滚动条与切页时使用。 */
    public void jumpTo(float value) {
        pendingTarget = value;
        tracker.jumpTo(value);
    }

    /** 推进一帧滚动缓动。 */
    public void update(float dt) {
        tracker.setTarget(pendingTarget);
        tracker.update(dt, 0f, maxScroll);
        pendingTarget = tracker.target();
    }

    /** 滚轮滚动：方向取滚轮增量，幅度受界面配置的滚动速度影响。 */
    public void scrollBy(double wheel, float speed) {
        setTarget(pendingTarget + (float) (-wheel * WHEEL_STEP * Math.max(0.2f, speed)));
    }

    /** 内容是否超过一屏。 */
    public boolean hasScrollbar() {
        return contentHeight > viewportHeight;
    }

    /** 滚动条滑块高度。 */
    public float thumbHeight(float trackHeight) {
        if (!hasScrollbar()) return trackHeight;
        return Math.max(MIN_THUMB_HEIGHT, trackHeight * viewportHeight / contentHeight);
    }

    /** 滚动条滑块顶部位置；{@code scroll} 传显示值或目标值分别用于绘制与拖动。 */
    public float thumbTop(float trackTop, float trackHeight, float scroll) {
        float thumbH = thumbHeight(trackHeight);
        float progress = Math.min(1f, scroll / Math.max(1f, maxScroll));
        return Math.min(trackTop + (trackHeight - thumbH) * progress, trackTop + trackHeight - thumbH);
    }

    /** 命中滚动条（含左右扩展）。 */
    public boolean isInTrack(float mouseX, float mouseY, float trackX, float trackTop, float trackHeight) {
        return mouseX >= trackX - HIT_PADDING && mouseX <= trackX + TRACK_WIDTH + HIT_PADDING
                && mouseY >= trackTop && mouseY <= trackTop + trackHeight;
    }

    /** 按下滚动条：落在滑块上则抓取，否则跳到该处。 */
    public void beginDrag(float mouseY, float trackTop, float trackHeight) {
        float thumbTop = thumbTop(trackTop, trackHeight, target());
        float thumbH = thumbHeight(trackHeight);
        dragOffset = mouseY >= thumbTop && mouseY <= thumbTop + thumbH ? mouseY - thumbTop : thumbH * 0.5f;
        dragTo(mouseY, trackTop, trackHeight);
    }

    /** 拖动滚动条到指定位置。 */
    public void dragTo(float mouseY, float trackTop, float trackHeight) {
        float available = Math.max(1f, trackHeight - thumbHeight(trackHeight));
        float top = Math.max(trackTop, Math.min(mouseY - dragOffset, trackTop + available));
        jumpTo(maxScroll * ((top - trackTop) / available));
    }

    /** 绘制滚动条；内容不足一屏时不画。 */
    public void drawScrollbar(Canvas canvas, float trackX, float trackTop, float trackHeight, float alpha, ClickGuiThemeColors tc) {
        if (!hasScrollbar()) return;
        float thumbH = thumbHeight(trackHeight);
        float top = thumbTop(trackTop, trackHeight, tracker.value());
        trackPaint.setColor(GlassPanel.withAlpha(tc.scrollbarTrack, alpha * 0.5f));
        canvas.drawRRect(RRect.makeXYWH(trackX, trackTop, TRACK_WIDTH, trackHeight, TRACK_RADIUS), trackPaint);
        thumbPaint.setColor(GlassPanel.withAlpha(tc.scrollbarThumb, alpha));
        canvas.drawRRect(RRect.makeXYWH(trackX, top, TRACK_WIDTH, thumbH, TRACK_RADIUS), thumbPaint);
    }
}
