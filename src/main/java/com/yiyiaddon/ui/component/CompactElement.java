package com.yiyiaddon.ui.component;

import io.github.humbleui.skija.Canvas;

/**
 * 紧凑面板元素协议：在模块页里自上而下堆叠的一个单元。
 *
 * <p>高度是唯一布局依据，{@link #draw} 与 {@link #onClick} 收到的 {@code (x, y, width)} 由宿主
 * 容器给出，因此绘制坐标与命中坐标天然一致。折叠类元素的高度随动画变化，容器每次绘制与每次
 * 命中都重新读取 {@link #height()}，不会出现「画的是一处、点的是另一处」。</p>
 */
public interface CompactElement {

    /** 当前高度；带动画的元素返回动画中的高度。 */
    float height();

    /** 推进一帧动画。 */
    void update(float dt);

    /** 绘制本元素，左上角为 {@code (x, y)}，宽度为 {@code width}。 */
    void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY);

    /** 左键点击；返回 true 表示本元素消费了这次点击。 */
    boolean onClick(float mx, float my, float x, float y, float width, int button);

    /** 拖动（滑条、输入框等需要跟手的元素）。 */
    boolean onDrag(float mx, float my, float x, float y, float width);

    /** 松开鼠标；默认无拖动状态。 */
    default void releaseDrag() {
    }

    /**
     * 宿主把本元素的可见区（屏幕坐标，自上而下）传下来。
     *
     * <p>普通元素不需要关心——宽高由宿主裁剪就够。但「自己内部还要再排一层」的容器
     * （如 {@link SplitPanels} 的两栏）必须拿到它，否则容器里的每一行都会无条件绘制：
     * 候选表动辄一千多行，逐帧全画会把帧时间吃光。</p>
     */
    default void viewport(float top, float bottom) {
    }
}
