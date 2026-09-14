package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

/**
 * 调色板的饱和度 / 亮度面板：横向为饱和度、纵向为亮度，点击或拖动取色。
 *
 * <p>色相取自当前颜色，因此改完色相条本面板会立刻换成对应色系。</p>
 *
 * <p>游标用「外深内浅」同心方框，保证在任意明暗的底色上都看得见。</p>
 */
public final class SaturationBrightnessPad implements CompactElement {

    private static final float HEIGHT = 92f;
    private static final float RADIUS = 8f;
    private static final int COLUMNS = 36;
    private static final int ROWS = 18;
    private static final float SEAM = 0.6f;
    private static final float INDICATOR_SIZE = 10f;

    private final EspColor color;
    private final Paint fillPaint = new Paint().setAntiAlias(true);
    private final Paint indicatorPaint = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);

    public SaturationBrightnessPad(EspColor color) {
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
        float hue = color.hue();
        float cellWidth = width / COLUMNS;
        float cellHeight = HEIGHT / ROWS;
        for (int row = 0; row < ROWS; row++) {
            float brightness = 1f - (row + 0.5f) / ROWS;
            float top = y + row * cellHeight;
            float bottom = top + cellHeight + SEAM;
            for (int column = 0; column < COLUMNS; column++) {
                float saturation = (column + 0.5f) / COLUMNS;
                int rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness) & 0xFFFFFF;
                fillPaint.setColor(ClickGuiThemeColors.withAlpha(rgb, alpha));
                canvas.drawRect(Rect.makeLTRB(
                        x + column * cellWidth,
                        top,
                        x + (column + 1) * cellWidth + SEAM,
                        bottom), fillPaint);
            }
        }
        canvas.restore();

        float indicatorX = x + color.saturation() * width;
        float indicatorY = y + (1f - color.brightness()) * HEIGHT;
        Rect indicator = Rect.makeLTRB(
                indicatorX - INDICATOR_SIZE / 2f,
                indicatorY - INDICATOR_SIZE / 2f,
                indicatorX + INDICATOR_SIZE / 2f,
                indicatorY + INDICATOR_SIZE / 2f);
        indicatorPaint.setStrokeWidth(3.5f);
        indicatorPaint.setColor(ClickGuiThemeColors.withAlpha(tc.shadow, alpha));
        canvas.drawRect(indicator, indicatorPaint);
        indicatorPaint.setStrokeWidth(1.8f);
        indicatorPaint.setColor(ClickGuiThemeColors.withAlpha(tc.pickerIndicator, alpha));
        canvas.drawRect(indicator, indicatorPaint);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        return applyAt(mx, my, x, y, width);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return applyAt(mx, my, x, y, width);
    }

    private boolean applyAt(float mx, float my, float x, float y, float width) {
        float saturation = (mx - x) / Math.max(1f, width);
        float brightness = 1f - (my - y) / HEIGHT;
        color.applyHsv(color.hue(), saturation, brightness, false);
        return true;
    }
}
