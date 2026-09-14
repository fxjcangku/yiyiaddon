package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

/**
 * 颜色预览块：显示当前颜色，彩虹开启时实时变色。
 *
 * <p>底下铺棋盘格表示透明度，半透明色才能看出真实观感。圆角、边框与取色方式与项目既有的
 * {@code SettingColorPreview} 保持一致。</p>
 */
public final class ColorPreview implements CompactElement {

    private static final float HEIGHT = 34f;
    private static final float CHECKER_SIZE = 8f;
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

        canvas.save();
        canvas.clipRRect(rect);

        int columns = Math.max(1, (int) Math.ceil(width / CHECKER_SIZE));
        int rows = Math.max(1, (int) Math.ceil(HEIGHT / CHECKER_SIZE));
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                boolean light = ((row + column) & 1) == 0;
                paint.setColor(ClickGuiThemeColors.withAlpha(light ? tc.checkerLight : tc.checkerDark, alpha));
                canvas.drawRect(Rect.makeLTRB(
                        x + column * CHECKER_SIZE,
                        y + row * CHECKER_SIZE,
                        Math.min(x + width, x + (column + 1) * CHECKER_SIZE),
                        Math.min(y + HEIGHT, y + (row + 1) * CHECKER_SIZE)), paint);
            }
        }

        paint.setColor(ClickGuiThemeColors.scaleAlpha(color.argb(), alpha));
        canvas.drawRect(Rect.makeLTRB(x, y, x + width, y + HEIGHT), paint);

        canvas.restore();

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
