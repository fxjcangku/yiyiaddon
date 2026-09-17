package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.function.Supplier;

/**
 * 方形图标按钮。
 *
 * <p>用于行内操作（列表行的删除、加减、刷新、关闭等）。默认为「幽灵」形态：无底色，
 * 悬停才浮出底色，避免密集列表被按钮底色切碎。可切换为常态带底，或危险变体
 * （悬停转危险红底 + 红图标）。</p>
 *
 * <p>图标码点使用 Material Symbols 字体，常用码点见
 * {@code 02-开发报告/UI开发} 各报告附录。</p>
 */
public class IconButton extends SettingWidget {

    /** 默认边长。 */
    public static final float DEFAULT_SIZE = 24f;

    private static final float HOVER_SMOOTHING = 14f;
    private static final float RADIUS_RATIO = 0.34f;

    private final String glyph;
    private final Runnable action;
    private final PressState press = new PressState();
    private final Paint paint = new Paint().setAntiAlias(true);

    private float size = DEFAULT_SIZE;
    /** 图标字号；负值表示按边长自动推算。 */
    private float iconFontSize = -1f;
    private boolean danger;
    private boolean ghost = true;
    private boolean disabled;
    /** 空态禁用判据；非 null 时每帧重算 {@link #disabled} */
    private Supplier<Boolean> disabledSupplier;

    private float hover;
    private boolean hovered;
    private float cachedGlyphWidth;
    private float cachedGlyphSize = -1f;

    public IconButton(String glyph, Runnable action) {
        this.glyph = glyph == null ? "" : glyph;
        this.action = action == null ? () -> { } : action;
    }

    // ── 链式配置 ──

    public IconButton size(float size) {
        if (size > 0f) this.size = size;
        return this;
    }

    /** 图标字号；不设置时按边长的 0.58 倍推算。 */
    public IconButton glyphSize(float glyphSize) {
        if (glyphSize > 0f) this.iconFontSize = glyphSize;
        return this;
    }

    /** 危险变体：悬停转危险红底 + 红图标。 */
    public IconButton danger() {
        this.danger = true;
        return this;
    }

    /** 常态是否带底色；默认 false（仅悬停显示底色）。 */
    public IconButton filled(boolean filled) {
        this.ghost = !filled;
        return this;
    }

    public IconButton disabledWhen(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    /**
     * 空态禁用（判据逐帧求值）。
     *
     * <p>行构件只在切页签 / 手动刷新时重建，而按钮被按下后并不重建；用构建期快照
     * （{@link #disabledWhen(boolean)}）会把禁用态停在旧值上，正是第 214 条要消灭的
     * 「点了没反应的悬案」。所以这里收判据本身，每帧在 {@link #update(float)} 里重算：
     * 判据必须与动作读取同一份数据，且要实时（通常是 {@code list::isEmpty} 之类）。</p>
     */
    public IconButton disabledWhen(Supplier<Boolean> disabled) {
        this.disabledSupplier = disabled;
        return this;
    }

    // ── 尺寸与测量 ──

    @Override
    public float getWidth() { return size; }

    @Override
    public float getHeight() { return size; }

    private float resolvedIconSize() {
        return iconFontSize > 0f ? iconFontSize : size * 0.58f;
    }

    private float glyphWidth() {
        float value = resolvedIconSize();
        if (cachedGlyphWidth <= 0f || cachedGlyphSize != value) {
            cachedGlyphSize = value;
            cachedGlyphWidth = FontRenderer.measureTextWidth(glyph, value, FontRenderer.MATERIAL_SYMBOLS);
        }
        return cachedGlyphWidth;
    }

    // ── 状态推进 ──

    @Override
    public void hover(float mouseX, float mouseY, float x, float y, float width) {
        if (disabled) {
            hovered = false;
            return;
        }
        hovered = mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }

    @Override
    public void update(float dt) {
        if (disabledSupplier != null) disabled = disabledSupplier.get();
        press.update(dt);
        hover += ((hovered ? 1f : 0f) - hover)
                * (1f - (float) Math.exp(-Math.max(0f, dt) * HOVER_SMOOTHING));
        if (hover < 0.001f) hover = 0f;
    }

    @Override
    public boolean isAnimating() {
        return !press.isIdle() || Math.abs(hover - (hovered ? 1f : 0f)) > 0.01f;
    }

    // ── 绘制 ──

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float contentAlpha = disabled ? alpha * 0.4f : alpha;
        float radius = size * RADIUS_RATIO;

        int background = danger ? tc.dangerHoverBackground : tc.hoverBackground;
        float backgroundAlpha = ClickGuiThemeColors.panelBackgroundAlpha(contentAlpha);
        backgroundAlpha *= ghost ? hover : (0.6f + 0.4f * hover);

        int foreground = GlassPanel.mix(tc.secondaryText, danger ? tc.dangerHoverText : tc.primaryText, hover);

        boolean scaled = press.apply(canvas, x, y, size, size);
        if (backgroundAlpha > 0.004f) {
            paint.setColor(GlassPanel.withAlpha(background, backgroundAlpha));
            canvas.drawRRect(RRect.makeXYWH(x, y, size, size, radius), paint);
            GlassPanel.rim(canvas, x, y, size, size, radius, tc.rim, backgroundAlpha, 0.18f);
        }

        float iconSize = resolvedIconSize();
        float iconWidth = glyphWidth();
        float centerY = y + size * 0.5f;
        FontRenderer.drawText(canvas, glyph, x + (size - iconWidth) * 0.5f,
                CardLayout.baseline(centerY, iconSize), iconSize,
                GlassPanel.withAlpha(foreground, contentAlpha), FontRenderer.MATERIAL_SYMBOLS);

        if (scaled) canvas.restore();
    }

    // ── 命中 ──

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button != 0 || disabled) return false;
        if (mx < x || mx > x + size || my < y || my > y + size) return false;
        press.pulse();
        action.run();
        return true;
    }
}
