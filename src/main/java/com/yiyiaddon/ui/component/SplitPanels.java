package com.yiyiaddon.ui.component;

import io.github.humbleui.skija.Canvas;

/**
 * 双栏容器：左右各一个 {@link CompactStack}，中间留缝。
 *
 * <p>用于「左边候选、右边已选」这类并排布局。两栏各自是竖向堆栈，整体高度取两者较大值，
 * 短的一栏底部自然留空。整体作为<b>单个元素</b>参与外层滚动——两栏一起上下移动，
 * 不各自滚动，避免出现两个滚动条互相干扰。</p>
 *
 * <p>命中按 x 落在中线哪一侧决定分发给谁，不做重叠区优先级判定。</p>
 */
public final class SplitPanels implements CompactElement {

    private final float minHeight;
    private final float gap;

    private CompactStack left;
    private CompactStack right;

    /** 宿主给出的可见区（屏幕坐标）：两栏自己的行按它裁剪，看不见的行不画 */
    private float viewportTop = -Float.MAX_VALUE;
    private float viewportBottom = Float.MAX_VALUE;

    public SplitPanels(float minHeight, float rowGap) {
        this.minHeight = Math.max(0f, minHeight);
        this.gap = 12f;
        this.left = new CompactStack(rowGap);
        this.right = new CompactStack(rowGap);
    }

    public CompactStack left() {
        return left;
    }

    public CompactStack right() {
        return right;
    }

    /** 清空两栏；{@link CompactStack} 没有清空接口，因此整栈替换。 */
    public void reset() {
        float rowGap = 4f;
        left = new CompactStack(rowGap);
        right = new CompactStack(rowGap);
    }

    @Override
    public void viewport(float top, float bottom) {
        this.viewportTop = top;
        this.viewportBottom = bottom;
    }

    @Override
    public float height() {
        return Math.max(minHeight, Math.max(left.height(), right.height()));
    }

    @Override
    public void update(float dt) {
        left.update(dt);
        right.update(dt);
    }

    /**
     * 绘制两栏。
     *
     * <p>两栏共用宿主传下来的可见区（见 {@link CompactElement#viewport}）：候选表可能有一千多行，
     * 不裁剪就等于每帧把一千多个行控件全画一遍，帧时间全耗在看不见的东西上。两栏本身不做
     * 内部滚动，跨过可见区的部分由外层窗口的内容区裁剪负责给出正确观感。</p>
     */
    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        float columnWidth = (width - gap) * 0.5f;
        if (columnWidth <= 0f) return;
        left.draw(canvas, x, y, columnWidth, alpha, viewportTop, viewportBottom, mouseX, mouseY);
        right.draw(canvas, x + columnWidth + gap, y, columnWidth, alpha,
                viewportTop, viewportBottom, mouseX, mouseY);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        float columnWidth = (width - gap) * 0.5f;
        if (columnWidth <= 0f) return false;
        if (mx <= x + columnWidth) {
            return left.onClick(mx, my, x, y, columnWidth, Float.MAX_VALUE, button);
        }
        return right.onClick(mx, my, x + columnWidth + gap, y, columnWidth, Float.MAX_VALUE, button);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        float columnWidth = (width - gap) * 0.5f;
        if (columnWidth <= 0f) return false;
        if (mx <= x + columnWidth) {
            return left.onDrag(mx, my, x, y, columnWidth, Float.MAX_VALUE);
        }
        return right.onDrag(mx, my, x + columnWidth + gap, y, columnWidth, Float.MAX_VALUE);
    }

    public void releaseDrag() {
        left.releaseDrag();
        right.releaseDrag();
    }
}
