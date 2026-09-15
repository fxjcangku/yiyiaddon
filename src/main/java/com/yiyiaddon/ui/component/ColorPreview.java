package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;

/**
 * 颜色预览块：显示当前颜色，彩虹开启时实时变色。
 *
 * <p>直接画纯色块，与旧项目一致。圆角、边框与取色方式与同类的 {@code SettingColorPicker} 保持一致。</p>
 */
public final class ColorPreview implements CompactElement {

    private static final float HEIGHT = 34f;
    private static final float RADIUS = 8f;

    private final EspColor color;
    private final Paint paint = new Paint().setAntiAlias(true);
    private final Paint borderPaint = new Paint().setAntiAlias(true)
            .setMode(PaintMode.STROKE)
            .setStrokeWidth(1.2f);

    public ColorPreview(EspColor color) {
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

        paint.setColor(ClickGuiThemeColors.scaleAlpha(color.argb(), alpha));
        canvas.drawRRect(rect, paint);

        borderPaint.setColor(ClickGuiThemeColors.withAlpha(tc.border, alpha * 0.24f));
        canvas.drawRRect(rect, borderPaint);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }
}
