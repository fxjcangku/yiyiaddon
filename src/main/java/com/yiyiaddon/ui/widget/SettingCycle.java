package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.render.FontRenderer;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingCycle extends SettingWidget {

    private final List<String> options;
    private final Supplier<Integer> getter;
    private final Consumer<Integer> setter;
    private final PressState press = new PressState();
    private final Paint bgPaint = new Paint().setAntiAlias(true);
    private int cachedIndex = Integer.MIN_VALUE;
    private String cachedLabel = "";
    private float cachedTextWidth = 0f;

    public SettingCycle(List<String> options, Supplier<Integer> getter, Consumer<Integer> setter) {
        this.options = options;
        this.getter = getter;
        this.setter = setter;
    }

    @Override public float getWidth() { return 100f; }
    @Override public float getHeight() { return 24f; }

    @Override
    public void update(float dt) {
        press.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        int index = getter.get() % options.size();
        if (index != cachedIndex) {
            cachedIndex = index;
            cachedLabel = options.get(index);
            cachedTextWidth = FontRenderer.measureTextWidth(cachedLabel, 12f);
        }
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        bgPaint.setColor(withAlpha(tc.buttonBackground, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        boolean pressed = press.apply(canvas, x, y, getWidth(), getHeight());
        canvas.drawRRect(RRect.makeXYWH(x, y, getWidth(), getHeight(), 6f), bgPaint);
        FontRenderer.drawText(canvas, cachedLabel, x + (getWidth() - cachedTextWidth) / 2f, y + 16f, 12f, withAlpha(tc.subModuleText, alpha));
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
        setter.accept((getter.get() + 1) % options.size());
        return true;
    }
}
