package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

/**
 * 键位徽章：模块页顶部的快捷键显示与录入入口。
 *
 * <p>色阶、圆角、悬停变红解绑的交互与设置行右侧的按键块保持同一套视觉；键名映射直接复用
 * {@link ModuleKeybindManager#keyName(String)}，不另写一份键名表。绑定与解绑同样走
 * {@link ModuleKeybindManager}，模块键位仍然落在模块状态配置里。</p>
 *
 * <p>宽度带过渡：从无键位的图标宽度平滑到键名宽度，录制态展开到提示文案宽度。</p>
 */
public final class KeybindBadge {

    /** 徽章高度。 */
    public static final float HEIGHT = 21f;

    private static final float MIN_WIDTH = 21f;
    private static final float RADIUS = 5f;
    private static final float WIDTH_SMOOTHING = 15f;
    private static final float HOVER_SMOOTHING = 0.2f;
    private static final float TEXT_SIZE = 9f;
    private static final float ICON_SIZE = 12f;
    private static final float CAPTURE_MIN_WIDTH = 110f;
    private static final float TEXT_PADDING = 12f;

    private static final String KEYBIND_ICON = "\uE9FE";
    private static final String CLEAR_ICON = "\uF508";

    private final String bindingId;
    private final Paint paint = new Paint().setAntiAlias(true);

    private float width = MIN_WIDTH;
    private float hover;
    private float unbind;

    public KeybindBadge(String bindingId) {
        this.bindingId = bindingId == null ? "" : bindingId;
    }

    /** 键名是否有效；无效时不参与布局与命中。 */
    public boolean active() {
        return !bindingId.isBlank();
    }

    /** 当前宽度（带过渡）；调用方按它做右对齐布局。 */
    public float width() {
        return width;
    }

    public void update(float dt) {
        if (!active()) return;
        width += (targetWidth() - width) * Math.min(1f, Math.max(0f, dt) * WIDTH_SMOOTHING);
    }

    /** 绘制徽章；绘制矩形与悬停命中矩形完全一致。 */
    public void draw(Canvas canvas, float x, float y, float alpha, float mouseX, float mouseY) {
        if (!active()) return;
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
        boolean bound = ModuleKeybindManager.hasBinding(bindingId);
        boolean capturing = ModuleKeybindManager.isCapturing(bindingId);
        hover += ((hovered ? 1f : 0f) - hover) * HOVER_SMOOTHING;
        unbind += ((bound && hovered && !capturing ? 1f : 0f)
                - unbind) * HOVER_SMOOTHING;

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        int baseGray = tc.dark ? 0x777777 : 0x555555;
        int hoverGray = tc.dark ? 0x949494 : 0x777777;
        int unbindRed = tc.dark ? 0xE14D4D : 0xCC3333;
        int color = GlassPanel.mix(GlassPanel.mix(baseGray, hoverGray, hover), unbindRed, unbind);
        float buttonAlpha = alpha * (0.34f + hover * 0.18f + unbind * 0.32f);
        paint.setColor(GlassPanel.withAlpha(color, buttonAlpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, width, HEIGHT, RADIUS), paint);

        if (capturing) {
            drawCenteredText(canvas, UiText.t("按下任意键...", "Press any key..."), x, y, alpha);
        } else if (bound && unbind > 0.12f) {
            drawCenteredIcon(canvas, CLEAR_ICON, x, y, alpha);
        } else if (bound) {
            drawCenteredText(canvas, ModuleKeybindManager.keyName(bindingId), x, y, alpha);
        } else {
            drawCenteredIcon(canvas, KEYBIND_ICON, x, y, alpha);
        }
    }

    /** 点击徽章：已绑定则解绑，未绑定则进入录制。 */
    public boolean onClick(float mx, float my, float x, float y) {
        if (!active()) return false;
        if (mx < x || mx > x + width || my < y || my > y + HEIGHT) return false;
        // 已绑定则解绑（界面快捷键同样可解，清空后由 ModuleKeybindManager 的显式清空标记保证不再套默认键）
        if (ModuleKeybindManager.hasBinding(bindingId)) {
            ModuleKeybindManager.clearBinding(bindingId);
        } else {
            ModuleKeybindManager.beginCapture(bindingId);
        }
        return true;
    }

    private float targetWidth() {
        if (ModuleKeybindManager.isCapturing(bindingId)) {
            float textWidth = FontRenderer.measureTextWidth(UiText.t("按下任意键...", "Press any key..."), TEXT_SIZE);
            return Math.max(CAPTURE_MIN_WIDTH, textWidth + TEXT_PADDING + 8f);
        }
        String keyName = ModuleKeybindManager.keyName(bindingId);
        return keyName.isBlank()
                ? MIN_WIDTH
                : Math.max(MIN_WIDTH, FontRenderer.measureTextWidth(keyName, TEXT_SIZE) + TEXT_PADDING);
    }

    private void drawCenteredText(Canvas canvas, String text, float x, float y, float alpha) {
        float textWidth = FontRenderer.measureTextWidth(text, TEXT_SIZE);
        FontRenderer.drawText(canvas, text, x + (width - textWidth) / 2f, y + HEIGHT / 2f + 3.5f, TEXT_SIZE,
                GlassPanel.withAlpha(0xFFFFFF, alpha));
    }

    private void drawCenteredIcon(Canvas canvas, String icon, float x, float y, float alpha) {
        float iconWidth = FontRenderer.measureTextWidth(icon, ICON_SIZE, FontRenderer.MATERIAL_SYMBOLS);
        FontRenderer.drawText(canvas, icon, x + (width - iconWidth) / 2f, y + HEIGHT / 2f + 6.2f, ICON_SIZE,
                GlassPanel.withAlpha(0xFFFFFF, alpha), FontRenderer.MATERIAL_SYMBOLS);
    }
}
