package com.yiyiaddon.ui.render.world;

/**
 * 世界坐标投影到屏幕后的结果。
 *
 * <p>坐标口径是 <b>GUI 缩放坐标</b>（与 {@code Window#getGuiScaledWidth()} 一致），
 * 可直接交给 Skija 画布使用——{@code SkiaGlBackend} 已在画布上应用过 {@code guiScale}。</p>
 *
 * @param x       屏幕 X
 * @param y       屏幕 Y
 * @param visible 是否位于相机前方（{@code w} 大于近平面阈值）
 * @param w       裁剪空间 w，可用于深度排序（越大越远）
 */
public record ScreenPoint(float x, float y, boolean visible, float w) {

    private static final ScreenPoint INVISIBLE = new ScreenPoint(Float.NaN, Float.NaN, false, Float.NaN);

    public static ScreenPoint invisible() {
        return INVISIBLE;
    }

    /** 是否在屏幕矩形范围内（含外扩边距，用于判断是否值得绘制）。 */
    public boolean onScreen(float screenWidth, float screenHeight, float margin) {
        if (!visible) return false;
        return x >= -margin && y >= -margin && x <= screenWidth + margin && y <= screenHeight + margin;
    }
}
