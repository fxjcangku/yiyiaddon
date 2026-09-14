package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.screen.ColorPickerScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;

/**
 * 颜色设置项：显示当前颜色的色块，点击打开调色板窗口。
 *
 * <p>尺寸、圆角、边框取色与同类的 {@code SettingColorPreview} 保持一致：边框取
 * {@code theme.border} 的 24% 透明度，而不是写死的白色。彩虹开启时色块本身在变色，
 * 右上角另有一个小标记提示。</p>
 */
public final class SettingColorPicker extends SettingWidget {

    private static final float WIDTH = 100f;
    private static final float HEIGHT = 24f;
    private static final float RADIUS = 8f;
    private static final float CHECKER_SIZE = 6f;
    private static final float MARKER_SIZE = 4f;

    private final String title;
    private final EspColor color;
    private final Paint fillPaint = new Paint().setAntiAlias(true);
    private final Paint borderPaint = new Paint().setAntiAlias(true)
            .setMode(PaintMode.STROKE)
            .setStrokeWidth(1.4f);

    /**
     * @param title 调色板窗口标题，用来说明这是哪个颜色（例如「线条颜色」）
     */
    public SettingColorPicker(String title, EspColor color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        RRect rect = RRect.makeXYWH(x, y, WIDTH, HEIGHT, RADIUS);

        canvas.save();
        canvas.clipRRect(rect);

        int columns = Math.max(1, (int) Math.ceil(WIDTH / CHECKER_SIZE));
        int rows = Math.max(1, (int) Math.ceil(HEIGHT / CHECKER_SIZE));
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                boolean light = ((row + column) & 1) == 0;
                fillPaint.setColor(withAlpha(light ? tc.checkerLight : tc.checkerDark, alpha));
                canvas.drawRect(Rect.makeLTRB(
                        x + column * CHECKER_SIZE,
                        y + row * CHECKER_SIZE,
                        Math.min(x + WIDTH, x + (column + 1) * CHECKER_SIZE),
                        Math.min(y + HEIGHT, y + (row + 1) * CHECKER_SIZE)), fillPaint);
            }
        }

        fillPaint.setColor(ClickGuiThemeColors.scaleAlpha(color.argb(), alpha));
        canvas.drawRect(Rect.makeLTRB(x, y, x + WIDTH, y + HEIGHT), fillPaint);

        canvas.restore();

        borderPaint.setColor(withAlpha(tc.border, alpha * 0.24f));
        canvas.drawRRect(rect, borderPaint);

        if (color.rainbow()) {
            fillPaint.setColor(withAlpha(tc.pickerIndicator, alpha));
            canvas.drawRect(Rect.makeLTRB(
                    x + WIDTH - MARKER_SIZE - 5f,
                    y + 5f,
                    x + WIDTH - 5f,
                    y + MARKER_SIZE + 5f), fillPaint);
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) return false;
        minecraft.setScreen(new ColorPickerScreen(title, color, minecraft.screen));
        return true;
    }
}
