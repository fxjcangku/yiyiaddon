package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingToggle extends SettingWidget {

    /** 滑块行程。 */
    private static final float TRACK_W = 44f;
    private static final float TRACK_H = 24f;
    private static final float THUMB_SIZE = 20f;
    private static final float THUMB_ON_X = 22f;
    private static final float THUMB_OFF_X = 2f;

    private final Supplier<Boolean> getter;
    private final Consumer<Boolean> setter;
    /** 滑块用欠阻尼弹簧：约 200ms 到位，过冲约 8% 后回落。 */
    private final Spring thumbSpring = Spring.overshoot(0.20f, 0.08f);
    private boolean initialized;
    private float colorT = -1f;
    private float lastDrawX = 0f;
    private final Paint trackPaint = new Paint().setAntiAlias(true);
    private final Paint thumbPaint = new Paint().setAntiAlias(true);

    public SettingToggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public void toggle() {
        setter.accept(!getter.get());
    }

    @Override public float getWidth() { return TRACK_W; }
    @Override public float getHeight() { return TRACK_H; }

    @Override
    public void update(float dt) {
        if (!initialized) return;
        thumbSpring.setTarget(getter.get() ? lastDrawX + THUMB_ON_X : lastDrawX + THUMB_OFF_X);
        thumbSpring.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        boolean on = getter.get();
        lastDrawX = x;

        if (!initialized) {
            initialized = true;
            thumbSpring.set(on ? x + THUMB_ON_X : x + THUMB_OFF_X);
        }
        if (colorT < 0f) colorT = on ? 1f : 0f;

        float targetColorT = on ? 1f : 0f;
        colorT += (targetColorT - colorT) * 0.2f;
        float thumbX = thumbSpring.value();

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        int trackColor = lerpColor(tc.scrollbarTrack, tc.accent, colorT);

        trackPaint.setColor(withAlpha(trackColor, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        thumbPaint.setColor(withAlpha(0xFFFFFF, alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, TRACK_W, TRACK_H, TRACK_H / 2f), trackPaint);
        canvas.drawRRect(RRect.makeXYWH(thumbX, y + 2f, THUMB_SIZE, THUMB_SIZE, THUMB_SIZE / 2f), thumbPaint);
    }

    @Override
    public boolean isAnimating() {
        if (!initialized) return false;
        return !thumbSpring.isSettled() || Math.abs(colorT - (getter.get() ? 1f : 0f)) > 0.01f;
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button != 0) return false;
        toggle();
        return true;
    }

    private static int lerpColor(int a, int b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int ar = (a >> 16) & 0xFF, ag = (a >> 8) & 0xFF, ab = a & 0xFF;
        int br = (b >> 16) & 0xFF, bg = (b >> 8) & 0xFF, bb = b & 0xFF;
        return ((int)(ar+(br-ar)*t) << 16) | ((int)(ag+(bg-ag)*t) << 8) | (int)(ab+(bb-ab)*t);
    }
}
