package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.render.FontRenderer;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.function.Supplier;

/**
 * 子项链接控件：点击打开对应界面（如主题预览界面）。
 * 显示当前值文本 + "›" 箭头，样式与 SettingCycle 一致。
 *
 * <p>默认宽度 150；紧凑双列里放不下的场景可由 {@link #width(float)} 收窄——横向窄一格，
 * 左侧标题就多一格可用宽度。</p>
 */
public class SettingLink extends SettingWidget {
    private final Supplier<String> label;
    private final Runnable action;
    private final PressState press = new PressState();
    private final Paint bgPaint = new Paint().setAntiAlias(true);
    private String cachedText = "";
    private float cachedTextWidth = 0f;
    /** 控件宽度；默认 150，可由 {@link #width(float)} 覆盖。 */
    private float width = 150f;

    public SettingLink(Supplier<String> label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    /** 链式设置控件宽度。 */
    public SettingLink width(float width) {
        this.width = Math.max(48f, width);
        return this;
    }

    @Override public float getWidth() { return width; }
    @Override public float getHeight() { return 24f; }

    @Override
    public void update(float dt) {
        press.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        // 与 SettingCycle 同一口径：用「输入域」色，不用按钮色（按钮色压在模块底色上就是小黑块，
        // 实机反馈「这些小框发黑严重」）。
        bgPaint.setColor(withAlpha(tc.field, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        String text = label.get() + " \u203A";
        if (!text.equals(cachedText)) {
            cachedText = text;
            cachedTextWidth = FontRenderer.measureTextWidth(text, 12f);
        }
        boolean pressed = press.apply(canvas, x, y, getWidth(), getHeight());
        canvas.drawRRect(RRect.makeXYWH(x, y, getWidth(), getHeight(), 6f), bgPaint);
        FontRenderer.drawText(canvas, text, x + (getWidth() - cachedTextWidth) / 2f, y + 16f, 12f, withAlpha(tc.subModuleText, alpha));
        if (pressed) canvas.restore();
    }

    @Override
    public boolean isAnimating() {
        return !press.isIdle();
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button != 0) return false;
        press.pulse();
        action.run();
        return true;
    }
}
