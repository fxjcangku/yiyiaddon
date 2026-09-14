package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.anim.Easing;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * 紧凑元素的垂直堆叠容器。
 *
 * <p>绘制、命中、拖动都走同一套「自上而下累加元素高度」的推算，折叠区展开到一半时也不会出现
 * 视觉与命中错位。视口裁剪只影响绘制，命中由调用方给出的 {@code visibleBottom} 限制在可见范围内。</p>
 *
 * <p>元素数量为个位数，逐帧遍历不做缓存，避免高度动画与缓存不一致。</p>
 *
 * <p><b>入场动画</b>：默认关闭。开启后元素按顺序依次淡入（{@link #enterAnimation}），
 * <b>只改绘制透明度，不改高度与命中几何</b>，因此不会出现「看得到点不到」。
 * 宿主必须每帧调用 {@link #update}；若从未调用过，入场动画视为已完成，不会出现整屏不可见。</p>
 */
public final class CompactStack {

    /** 单个元素的入场时长（秒）。 */
    private static final float ENTER_DURATION = 0.18f;
    /** 相邻元素的入场起始间隔（秒）。 */
    private static final float ENTER_STAGGER = 0.018f;
    /** 错峰序号上限：条目很多时后续元素不再继续推迟，避免长列表尾部迟迟不出现。 */
    private static final int MAX_STAGGER_INDEX = 10;

    private final List<CompactElement> elements = new ArrayList<>();
    private final float gap;

    private boolean enterAnimated;
    private boolean enterStarted;
    private float enterElapsed;

    public CompactStack(float gap) {
        this.gap = Math.max(0f, gap);
    }

    /**
     * 开启或关闭「元素依次淡入」的入场动画。
     *
     * <p>默认关闭，由需要强调「新窗口已打开」的界面（如独立窗口）显式开启；模块页等常驻界面
     * 保持即时呈现，避免每次切页都整体闪一下。</p>
     */
    public CompactStack enterAnimation(boolean enabled) {
        this.enterAnimated = enabled;
        return this;
    }

    public CompactStack add(CompactElement element) {
        if (element != null) elements.add(element);
        return this;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /** 全部元素与间距的总高度。 */
    public float height() {
        if (elements.isEmpty()) return 0f;
        float total = gap * (elements.size() - 1);
        for (CompactElement element : elements) total += element.height();
        return total;
    }

    public void update(float dt) {
        if (enterAnimated) {
            enterStarted = true;
            enterElapsed += Math.max(0f, dt);
        }
        for (CompactElement element : elements) element.update(dt);
    }

    /**
     * 第 {@code index} 个元素的入场进度。
     *
     * <p>未开启动画、宿主从未推进过动画、或动画已播完时都返回 1，绘制走原路径。</p>
     */
    private float enterProgress(int index) {
        if (!enterAnimated || !enterStarted) return 1f;
        int slot = Math.min(index, MAX_STAGGER_INDEX);
        return Easing.easeOutCubic((enterElapsed - slot * ENTER_STAGGER) / ENTER_DURATION);
    }

    /**
     * 绘制堆叠内容。
     *
     * @param y              堆叠顶部（已扣除滚动偏移）
     * @param viewportTop    可见区顶部，完全在上方之外的元素跳过绘制
     * @param viewportBottom 可见区底部，完全在下方之外的元素跳过绘制
     */
    public void draw(Canvas canvas, float x, float y, float width, float alpha,
                     float viewportTop, float viewportBottom, float mouseX, float mouseY) {
        float cy = y;
        int index = 0;
        for (CompactElement element : elements) {
            float h = element.height();
            if (cy + h > viewportTop && cy < viewportBottom) {
                float progress = enterProgress(index);
                if (progress > 0.006f) {
                    element.draw(canvas, x, cy, width, alpha * progress, mouseX, mouseY);
                }
            }
            cy += h + gap;
            index++;
        }
    }

    /**
     * 当前被按下的元素与其当时的 y（屏幕坐标）；拖动期间锁定，避免误改其它控件。
     */
    private CompactElement active;
    private float activeY;

    /**
     * 命中测试。
     *
     * <p>坐标口径：{@code y} 是第一个元素的起点、{@code visibleBottom} 是可见区下边界，
     * 两者都是<b>屏幕坐标</b>——滚动偏移由宿主并入 {@code y}，本类不做画布平移
     * （与 {@code BasePage} 体系的口径一致）。</p>
     *
     * @param visibleBottom 可见区下边界（屏幕坐标），超出部分视为不可点，与绘制裁剪保持一致
     */
    public boolean onClick(float mx, float my, float x, float y, float width, float visibleBottom, int button) {
        if (my > visibleBottom) return false;
        float cy = y;
        for (CompactElement element : elements) {
            float h = element.height();
            if (my >= cy && my <= cy + h) {
                boolean handled = element.onClick(mx, my, x, cy, width, button);
                if (handled) {
                    active = element;
                    activeY = cy;
                }
                return handled;
            }
            cy += h + gap;
        }
        return false;
    }

    /**
     * 拖动分发。
     *
     * <p>只发给「按下时命中的那个元素」，不按当前鼠标位置重新查找——否则拖动过程中鼠标稍微偏移，
     * 事件就会落到相邻控件上（例如拖色相条时划到下面的饱和度面板，变成改另一个值）。</p>
     */
    public boolean onDrag(float mx, float my, float x, float y, float width, float visibleBottom) {
        if (active == null) return false;
        return active.onDrag(mx, my, x, activeY, width);
    }

    /** 鼠标释放时必须调用，否则拖动会一直锁在同一个元素上。 */
    public void releaseDrag() {
        active = null;
    }
}
