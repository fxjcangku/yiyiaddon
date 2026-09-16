package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.Rect;
import io.github.humbleui.types.RRect;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

/**
 * 设置页颜色预览块：只读展示一个（或左右双色的）颜色值，点击跳转调色板由宿主页面负责。
 *
 * 视觉规格对齐本项目既有控件：圆角 8f、边框取主题 border 的 24% 透明度、尺寸 100x24
 * （开发习惯第 141、153 条）。
 */
public class SettingColorPreview extends SettingWidget {
    private final IntSupplier colorSupplier;
    private final IntSupplier secondColorSupplier;
    private final BooleanSupplier splitSupplier;
    private final Paint fillPaint = new Paint().setAntiAlias(true);
    private final Paint secondFillPaint = new Paint().setAntiAlias(true);
    private final Paint borderPaint = new Paint().setAntiAlias(true);

    public SettingColorPreview(IntSupplier colorSupplier) {
        this(colorSupplier, colorSupplier, () -> false);
    }

    public SettingColorPreview(IntSupplier colorSupplier, IntSupplier secondColorSupplier, BooleanSupplier splitSupplier) {
        this.colorSupplier = colorSupplier;
        this.secondColorSupplier = secondColorSupplier;
        this.splitSupplier = splitSupplier;
        borderPaint.setMode(PaintMode.STROKE);
        borderPaint.setStrokeWidth(1.4f);
    }

    @Override public float getWidth() { return 100f; }
    @Override public float getHeight() { return 24f; }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        int color = colorSupplier.getAsInt();
        fillPaint.setColor(withAlpha(color, alpha));
        borderPaint.setColor(withAlpha(ClickGuiThemeColors.current().border, alpha * 0.24f));
        RRect rect = RRect.makeXYWH(x, y, getWidth(), getHeight(), 8f);
        if (splitSupplier.getAsBoolean()) {
            int secondColor = secondColorSupplier.getAsInt();
            secondFillPaint.setColor(withAlpha(secondColor, alpha));
            canvas.save();
            canvas.clipRRect(rect);
            canvas.drawRect(Rect.makeXYWH(x, y, getWidth() * 0.5f, getHeight()), fillPaint);
            canvas.drawRect(Rect.makeXYWH(x + getWidth() * 0.5f, y, getWidth() * 0.5f, getHeight()), secondFillPaint);
            canvas.restore();
        } else {
            canvas.drawRRect(rect, fillPaint);
        }
        canvas.drawRRect(rect, borderPaint);
    }
}
