package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * 顶部信息块：模块状态、模块开关与快捷键徽章。
 *
 * <p>模块名由所在屏幕的头部给出（就在本块正上方），此处不重复绘制，避免同一名字出现两次。
 * 左侧是状态圆点与状态文字，右侧依次是快捷键徽章与模块开关。</p>
 *
 * <p>圆点用强调色表示运行中、三级文字色表示未启用；开关与徽章的绘制矩形、命中矩形由同一组
 * 私有方法给出，任何宽度变化都不会造成错位。</p>
 */
public final class ModuleStatusBar implements CompactElement {

    /** 信息块高度。 */
    public static final float HEIGHT = 46f;

    private static final float PAD_X = 16f;
    private static final float DOT_SIZE = 7f;
    private static final float DOT_GAP = 8f;
    private static final float STATE_SIZE = 12f;
    private static final float BADGE_GAP = 12f;

    private final Supplier<String> stateText;
    private final BooleanSupplier running;
    private final KeybindBadge keybind;
    private final SettingWidget control;

    /**
     * @param stateText 状态文字来源，例如「运行中」「未启用」
     * @param running   状态语义来源，true 时圆点与文字用强调色
     * @param keybind   快捷键徽章，可为 null
     * @param control   右侧控件（模块开关），可为 null 表示只读
     */
    public ModuleStatusBar(Supplier<String> stateText, BooleanSupplier running, KeybindBadge keybind, SettingWidget control) {
        this.stateText = stateText;
        this.running = running;
        this.keybind = keybind;
        this.control = control;
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
        if (keybind != null) keybind.update(dt);
        if (control != null) control.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = GlassPanel.rowRadius(HEIGHT);
        GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f,
                ClickGuiThemeColors.panelBackgroundAlpha(alpha));
        GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

        float centerY = y + HEIGHT / 2f;
        int stateColor = running.getAsBoolean() ? tc.accent : tc.mutedText;
        GlassPanel.fill(canvas, x + PAD_X, centerY - DOT_SIZE / 2f, DOT_SIZE, DOT_SIZE, DOT_SIZE / 2f,
                stateColor, alpha);
        FontRenderer.drawTextBold(canvas, stateText.get(), x + PAD_X + DOT_SIZE + DOT_GAP,
                CardLayout.baseline(centerY, STATE_SIZE), STATE_SIZE, GlassPanel.withAlpha(stateColor, alpha));

        if (keybind != null && keybind.active()) {
            keybind.draw(canvas, keybindX(x, width), centerY - KeybindBadge.HEIGHT / 2f, alpha, mouseX, mouseY);
        }
        if (control != null) {
            control.draw(canvas, controlX(x, width), centerY - control.getHeight() / 2f, alpha);
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (button != 0 || my < y || my > y + HEIGHT) return false;
        if (keybind != null && keybind.active()
                && keybind.onClick(mx, my, keybindX(x, width), y + (HEIGHT - KeybindBadge.HEIGHT) / 2f)) {
            return true;
        }
        if (control == null) return false;
        float cx = controlX(x, width);
        float cy = y + (HEIGHT - control.getHeight()) / 2f;
        if (mx < cx || mx > cx + control.getWidth() || my < cy || my > cy + control.getHeight()) {
            return false;
        }
        return control.onClick(mx, my, cx, cy, button);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }

    /** 快捷键徽章左边界；绘制与命中共用。 */
    private float keybindX(float x, float width) {
        float cursor = x + width - PAD_X;
        if (control != null) cursor -= control.getWidth() + BADGE_GAP;
        return cursor - keybind.width();
    }

    /** 右侧控件左边界；绘制与命中共用。 */
    private float controlX(float x, float width) {
        return control == null ? x + width - PAD_X : x + width - PAD_X - control.getWidth();
    }
}
