package com.yiyiaddon.ui.widget;

import io.github.humbleui.skija.Canvas;

public abstract class SettingWidget {

    protected static int withAlpha(int color, float alpha) {
        return ((int)(alpha * 255) << 24) | (color & 0x00FFFFFF);
    }

    public abstract float getWidth();
    public abstract float getHeight();
    public abstract void draw(Canvas canvas, float x, float y, float alpha);
    public void update(float dt) { }
    public boolean isAnimating() { return false; }
    public boolean onClick(float mx, float my, float x, float y, int button) { return false; }
    public boolean onDrag(float mx, float my, float x, float y) { return false; }
    public boolean onScroll(float mx, float my, float x, float y, float amount) { return false; }

    /**
     * 注入本帧鼠标位置，供需要悬停反馈的控件更新状态。
     *
     * <p>由宿主在绘制前调用（{@code CompactRow}、{@code SettingModule}、{@code ButtonRow}
     * 已自动转发）。默认无悬停反馈。</p>
     *
     * @param width 本帧分配给该控件的宽度；可能大于 {@link #getWidth()}（满宽模式）
     */
    public void hover(float mouseX, float mouseY, float x, float y, float width) { }
}
