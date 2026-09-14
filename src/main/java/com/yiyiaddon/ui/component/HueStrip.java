package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

/**
 * 调色板的色相条：横向铺开全部色相，点击或拖动选取。
 *
 * <p>只改色相，保留当前饱和度与亮度；写色相属于手动取色，会同时关闭彩虹。</p>
 *
 * <p>游标用「外深内浅」两条线叠加，保证在任何色相底色上都看得见——单色游标会在近似色上消失。</p>
 */
public final class HueStrip implements CompactElement {

    private static final float HEIGHT = 24f;
    private static final float RADIUS = 6f;
    private static final int SEGMENTS = 72;
    private static final float SEAM = 0.6f;

    private final EspColor color;
    private final Paint fillPaint = new Paint().setAntiAlias(true);
    private final Paint indicatorPaint = new Paint().setAntiAlias(true)
            .setMode(PaintMode.STROKE)
            .setStrokeCap(PaintStrokeCap.BUTT);

    public HueStrip(EspColor color) {
        this.color = color;
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        RRect rect = RRect.makeXYWH(x, y, width, HEIGHT, RADIUS);

        canvas.save();
        canvas.clipRRect(rect);
        float segment = width / SEGMENTS;
        for (int i = 0; i < SEGMENTS; i++) {
            float hue = (i + 0.5f) / SEGMENTS;
            int rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f) & 0xFFFFFF;
            fillPaint.setColor(ClickGuiThemeColors.withAlpha(rgb, alpha));
            canvas.drawRect(Rect.makeLTRB(
                    x + i * segment,
                    y,
                    x + (i + 1) * segment + SEAM,
                    y + HEIGHT), fillPaint);
        }
        canvas.restore();

        float indicatorX = x + color.hue() * width;
        indicatorPaint.setStrokeWidth(3.5f);
        indicatorPaint.setColor(ClickGuiThemeColors.withAlpha(tc.shadow, alpha));
        canvas.drawLine(indicatorX, y - 1f, indicatorX, y + HEIGHT + 1f, indicatorPaint);
        indicatorPaint.setStrokeWidth(1.8f);
        indicatorPaint.setColor(ClickGuiThemeColors.withAlpha(tc.pickerIndicator, alpha));
        canvas.drawLine(indicatorX, y - 1f, indicatorX, y + HEIGHT + 1f, indicatorPaint);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        return applyAt(mx, x, width);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return applyAt(mx, x, width);
    }

    private boolean applyAt(float mx, float x, float width) {
        float hue = (mx - x) / Math.max(1f, width);
        if (hue < 0f) hue = 0f;
        if (hue > 1f) hue = 1f;

        float saturation = color.saturation();
        float brightness = color.brightness();
        // 纯黑或纯灰时饱和度为 0，色相无从体现，取满饱和以便立刻看到所选色相
        if (saturation < 0.01f) saturation = 1f;
        if (brightness < 0.01f) brightness = 1f;

        color.applyHsv(hue, saturation, brightness, false);
        return true;
    }
}
