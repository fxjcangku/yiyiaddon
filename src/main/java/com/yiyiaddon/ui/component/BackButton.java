package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

/**
 * 返回按钮：圆角方形底 + Material 返回图标，带悬停过渡与按压缩放。
 *
 * <p>左上角由调用方给出，绘制与命中判定使用同一套几何，因此点击位置与视觉位置永远一致。
 * 主界面内容区头部与模块页头部共用本组件，不各写一份按钮。</p>
 */
public final class BackButton {

    /** 按钮边长。 */
    public static final float SIZE = 28f;

    private static final float RADIUS = 10f;
    private static final float ICON_SIZE = 18f;
    private static final String ICON = "\uE5C4";

    private final PressState press = new PressState();
    private final Paint backgroundPaint = new Paint().setAntiAlias(true);
    private final float iconWidth = FontRenderer.measureTextWidth(ICON, ICON_SIZE, FontRenderer.MATERIAL_SYMBOLS);

    private float hoverAlpha;
    private boolean hovered;

    /**
     * 每帧刷新悬停状态并推进按压动画。
     *
     * @param visible 按钮当前是否显示；不显示时悬停强度回落到 0
     */
    public void update(float mouseX, float mouseY, float x, float y, float dt, boolean visible) {
        hovered = visible && hit(mouseX, mouseY, x, y);
        hoverAlpha = hoverAlpha + ((hovered ? 1f : 0f) - hoverAlpha) * Math.min(1f, Math.max(0f, dt) * 12f);
        press.update(dt);
    }

    /** 命中判定。 */
    public boolean hit(float mouseX, float mouseY, float x, float y) {
        return mouseX >= x && mouseX <= x + SIZE && mouseY >= y && mouseY <= y + SIZE;
    }

    public void press() {
        press.press();
    }

    public void release() {
        press.release();
    }

    public void cancel() {
        press.cancel();
        hoverAlpha = 0f;
        hovered = false;
    }

    /** 绘制按钮；画布需已处于面板设计空间。 */
    public void draw(Canvas canvas, float x, float y, float alpha, ClickGuiThemeColors tc, boolean visible) {
        if (!visible) return;
        boolean pressed = press.apply(canvas, x, y, SIZE, SIZE);
        backgroundPaint.setColor(GlassPanel.withAlpha(hovered ? tc.hoverBackground : tc.subModule,
                ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        canvas.drawRRect(RRect.makeXYWH(x, y, SIZE, SIZE, RADIUS), backgroundPaint);
        FontRenderer.drawText(canvas, ICON, x + (SIZE - iconWidth) / 2f, y + SIZE - 5f, ICON_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha), FontRenderer.MATERIAL_SYMBOLS);
        if (pressed) canvas.restore();
    }
}
