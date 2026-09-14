package com.yiyiaddon.ui.component;

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
 */
public final class CompactStack {

    private final List<CompactElement> elements = new ArrayList<>();
    private final float gap;

    public CompactStack(float gap) {
        this.gap = Math.max(0f, gap);
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
        for (CompactElement element : elements) element.update(dt);
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
        for (CompactElement element : elements) {
            float h = element.height();
            if (cy + h > viewportTop && cy < viewportBottom) {
                element.draw(canvas, x, cy, width, alpha, mouseX, mouseY);
            }
            cy += h + gap;
        }
    }

    /**
     * 命中测试。
     *
     * @param visibleBottom 可见区下边界，超出部分视为不可点，保证与绘制裁剪一致
     */
    public boolean onClick(float mx, float my, float x, float y, float width, float visibleBottom, int button) {
        if (my > visibleBottom) return false;
        float cy = y;
        for (CompactElement element : elements) {
            float h = element.height();
            if (my >= cy && my <= cy + h) {
                return element.onClick(mx, my, x, cy, width, button);
            }
            cy += h + gap;
        }
        return false;
    }

    /** 拖动分发；只有需要跟手的元素会返回 true。 */
    public boolean onDrag(float mx, float my, float x, float y, float width, float visibleBottom) {
        if (my > visibleBottom) return false;
        float cy = y;
        for (CompactElement element : elements) {
            float h = element.height();
            if (my >= cy && my <= cy + h) {
                return element.onDrag(mx, my, x, cy, width);
            }
            cy += h + gap;
        }
        return false;
    }

    public void releaseDrag() {
        for (CompactElement element : elements) element.releaseDrag();
    }
}
