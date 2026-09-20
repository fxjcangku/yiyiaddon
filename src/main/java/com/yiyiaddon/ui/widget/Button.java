package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 统一按钮控件。
 *
 * <p>本项目全部按钮的唯一实现。支持四种变体、三种尺寸、内容自适应或固定宽度、前缀图标、
 * 选中态与禁用态，并具备完整三态反馈：悬停底色过渡（0.14s 时间常数）、按下缩放回弹
 * （{@link PressState}）。</p>
 *
 * <p>悬停位置由宿主在绘制前通过 {@link #hover} 注入（{@code CompactRow} /
 * {@code SettingModule} / {@code ButtonRow} 已自动转发）。需要满宽排版时由 {@code ButtonRow}
 * 调用 {@link #drawAt} 传入分配宽度，此时 {@link #getWidth()} 只作为自适应宽度参考。</p>
 */
public class Button extends SettingWidget {

    /** 视觉变体。 */
    public enum Variant {
        /** 主操作：强调色实底 + 反色文字。 */
        PRIMARY,
        /** 次要操作：中性底色 + 次要文字，悬停混入强调色。 */
        SECONDARY,
        /** 危险操作：中性底色 + 危险红文字，悬停转危险底。 */
        DANGER,
        /** 极轻操作：无底色，仅悬停时浮出底色。 */
        GHOST
    }

    /** 尺寸档位。 */
    public enum Size {
        SMALL(20f, 10f, 10f, 6f),
        MEDIUM(24f, 11f, 14f, 8f),
        LARGE(28f, 12f, 16f, 9f);

        final float height;
        final float fontSize;
        final float padX;
        final float radius;

        Size(float height, float fontSize, float padX, float radius) {
            this.height = height;
            this.fontSize = fontSize;
            this.padX = padX;
            this.radius = radius;
        }
    }

    private static final float ICON_SIZE = 14f;
    private static final float ICON_GAP = 6f;
    /** 物品图标的最大边长：与模块行那一档（16）对齐，矮按钮再按高度收一档。 */
    private static final float ITEM_ICON_MAX = 16f;
    private static final float HOVER_SMOOTHING = 14f;
    private static final float HOVER_TINT = 0.10f;
    private static final float SECONDARY_HOVER_TINT = 0.14f;

    private final Supplier<String> label;
    private final Runnable action;
    private final PressState press = new PressState();

    private Variant variant = Variant.SECONDARY;
    private Size size = Size.MEDIUM;
    private String icon;
    /** 物品图标来源（与 {@link #icon} 字形图标可并存，物品图标在前）；{@code null} = 不带物品图标 */
    private Supplier<ItemStack> itemIcon;
    private Supplier<Boolean> selectedSupplier;
    private Supplier<Boolean> disabledSupplier;
    private float fixedWidth = -1f;

    /** 悬停强度；{@code -1} = 尚未落位（首帧直接取真实值，见 {@link #primeHover()}）。 */
    private float hover = -1f;
    /** 仅插值选中外观，点击回调与选中数据仍立即生效。 */
    private float selectedBlend = -1f;
    private boolean hovered;

    private String cachedLabel = "";
    private float cachedLabelWidth;
    private Size cachedSize;
    private float cachedIconWidth;

    public Button(String label, Runnable action) {
        this(() -> label, action);
    }

    public Button(Supplier<String> label, Runnable action) {
        this.label = label == null ? () -> "" : label;
        this.action = action == null ? () -> { } : action;
    }

    // ── 链式配置 ──

    public Button variant(Variant variant) {
        this.variant = variant == null ? Variant.SECONDARY : variant;
        return this;
    }

    public Button primary() { return variant(Variant.PRIMARY); }

    public Button secondary() { return variant(Variant.SECONDARY); }

    public Button danger() { return variant(Variant.DANGER); }

    public Button ghost() { return variant(Variant.GHOST); }

    public Button size(Size size) {
        if (size != null) this.size = size;
        return this;
    }

    public Button small() { return size(Size.SMALL); }

    public Button large() { return size(Size.LARGE); }

    /** 前缀图标（Material Symbols 码点）。 */
    public Button icon(String glyph) {
        this.icon = glyph;
        this.cachedIconWidth = 0f;
        return this;
    }

    /**
     * 前缀物品图标（真实的物品贴图，画在文字左侧；与 {@link #icon(String)} 可并存，物品图标在前）。
     *
     * <p>用户 2026-09-22：「区域选择没有显示农作物图标」——圈地收口窗的作物按钮从此带作物图。
     * 与 {@code CompactRow#icon} 同一观感（同一 {@code ItemIconCache} 链路、同一档尺寸）。</p>
     *
     * <p><b>供应商每帧会被问几次</b>（测量宽度、绘制各一次），调用方应先把物品栈算好再传进来 ——
     * 资源解析（{@code StardewPreview}）不能放在这里。</p>
     */
    public Button itemIcon(Supplier<ItemStack> itemIcon) {
        this.itemIcon = itemIcon;
        return this;
    }

    /** 固定宽度；不设置时按内容自适应。 */
    public Button width(float width) {
        this.fixedWidth = width > 0f ? width : -1f;
        return this;
    }

    /** 选中态来源：为 true 时按强调色实底渲染，用于筛选、切换类按钮。 */
    public Button selected(Supplier<Boolean> selected) {
        this.selectedSupplier = selected;
        return this;
    }

    /** 禁用态来源：为 true 时整体降透明且不响应点击。 */
    public Button disabledWhen(Supplier<Boolean> disabled) {
        this.disabledSupplier = disabled;
        return this;
    }

    // ── 尺寸与测量 ──

    @Override
    public float getWidth() {
        return fixedWidth > 0f ? fixedWidth : contentWidth();
    }

    @Override
    public float getHeight() {
        return size.height;
    }

    private float contentWidth() {
        float width = size.padX * 2f + textWidth();
        if (!itemIconStack().isEmpty()) width += itemIconBox() + ICON_GAP;
        if (icon != null) width += iconWidth() + ICON_GAP;
        return Math.max(size.height * 2f, width);
    }

    /** 当前物品图标（供应商没给 / 给了空栈 = 不画，也不占横向空间）。 */
    private ItemStack itemIconStack() {
        if (itemIcon == null) return ItemStack.EMPTY;
        ItemStack stack = itemIcon.get();
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** 物品图标边长：不超过 {@link #ITEM_ICON_MAX}，矮一档的按钮再按高度收一档。 */
    private float itemIconBox() {
        return Math.min(ITEM_ICON_MAX, size.height - 6f);
    }

    private float textWidth() {
        String text = label.get();
        if (!text.equals(cachedLabel) || cachedSize != size) {
            cachedLabel = text;
            cachedSize = size;
            cachedLabelWidth = MinecraftText.measure(text, size.fontSize, false);
        }
        return cachedLabelWidth;
    }

    private float iconWidth() {
        if (cachedIconWidth <= 0f) {
            cachedIconWidth = FontRenderer.measureTextWidth(icon, ICON_SIZE, FontRenderer.MATERIAL_SYMBOLS);
        }
        return cachedIconWidth;
    }

    private boolean isSelected() {
        return selectedSupplier != null && Boolean.TRUE.equals(selectedSupplier.get());
    }

    private boolean isDisabled() {
        return disabledSupplier != null && Boolean.TRUE.equals(disabledSupplier.get());
    }

    // ── 状态推进 ──

    @Override
    public void hover(float mouseX, float mouseY, float x, float y, float width) {
        if (isDisabled()) {
            hovered = false;
            return;
        }
        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + size.height;
    }

    @Override
    public void update(float dt) {
        press.update(dt);
        float blend = 1f - (float) Math.exp(-Math.max(0f, dt) * HOVER_SMOOTHING);
        if (hover < 0f) {
            hover = hovered ? 1f : 0f;
        } else {
            hover += ((hovered ? 1f : 0f) - hover) * blend;
        }
        float target = isSelected() ? 1f : 0f;
        if (selectedBlend < 0f) selectedBlend = target;
        selectedBlend += (target - selectedBlend) * blend;
        if (hover < 0.001f) hover = 0f;
    }

    /**
     * 首帧落位：把悬停强度直接设成真实值，不淡入。
     *
     * <p><b>为什么需要</b>：控制台的概览页每秒整页重建，鼠标若停在按钮上，新实例的悬停强度从 0
     * 重新淡入，表现为「按钮高亮每秒闪一次」（用户 2026-09-18 实机反馈）。落位后只有真实的
     * 移入 / 移出才产生过渡，重建不再重播动画。与 {@code selectedBlend} 的 {@code -1} 哨兵同一手法。</p>
     */
    private void primeHover() {
        if (hover < 0f) hover = hovered ? 1f : 0f;
    }

    @Override
    public boolean isAnimating() {
        return !press.isIdle() || Math.abs(hover - (hovered ? 1f : 0f)) > 0.01f
                || (selectedBlend >= 0f && Math.abs(selectedBlend - (isSelected() ? 1f : 0f)) > 0.01f);
    }

    // ── 绘制 ──

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        drawAt(canvas, x, y, getWidth(), alpha);
    }

    /** 以指定宽度绘制，供满宽排版（{@code ButtonRow}）使用。 */
    public void drawAt(Canvas canvas, float x, float y, float width, float alpha) {
        primeHover();
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float height = size.height;
        float radius = size.radius;
        float contentAlpha = isDisabled() ? alpha * 0.4f : alpha;

        int background = tc.buttonBackground;
        int foreground = tc.buttonText;
        float backgroundAlpha = ClickGuiThemeColors.panelBackgroundAlpha(contentAlpha);

        if (selectedBlend < 0f) selectedBlend = isSelected() ? 1f : 0f;
        if (selectedBlend > 0.001f) {
            background = GlassPanel.mix(tc.buttonBackground,
                    GlassPanel.mix(tc.accent, tc.rim, hover * HOVER_TINT), selectedBlend);
            foreground = GlassPanel.mix(tc.subModuleText, tc.accentOn, selectedBlend);
        } else {
            switch (variant) {
                case PRIMARY -> {
                    background = GlassPanel.mix(tc.accent, tc.rim, hover * HOVER_TINT);
                    foreground = tc.accentOn;
                }
                case DANGER -> {
                    background = GlassPanel.mix(tc.buttonBackground, tc.dangerHoverBackground, hover);
                    foreground = tc.dangerHoverText;
                }
                case GHOST -> {
                    background = tc.hoverBackground;
                    backgroundAlpha *= hover;
                    foreground = tc.secondaryText;
                }
                default -> {
                    background = GlassPanel.mix(tc.buttonBackground, tc.accent, hover * SECONDARY_HOVER_TINT);
                    foreground = tc.subModuleText;
                }
            }
        }

        boolean scaled = press.apply(canvas, x, y, width, height);
        if (backgroundAlpha > 0.004f) {
            // 玻璃只叠加轻量材质，沿用既有命中矩形与密集按钮尺寸。
            GlassPanel.frost(canvas, x, y, width, height, radius, background, 1f, backgroundAlpha);
            GlassPanel.rim(canvas, x, y, width, height, radius, tc.rim, backgroundAlpha,
                    0.16f + 0.10f * hover);
        }

        drawContent(canvas, x, y, width, height, contentAlpha, foreground);
        if (scaled) canvas.restore();
    }

    private void drawContent(Canvas canvas, float x, float y, float width, float height,
                             float alpha, int foreground) {
        String text = label.get();
        float textWidth = textWidth();
        float iconWidth = icon == null ? 0f : iconWidth() + ICON_GAP;
        ItemStack item = itemIconStack();
        boolean hasItem = !item.isEmpty();
        float itemBox = hasItem ? itemIconBox() : 0f;
        float itemAdvance = hasItem ? itemBox + ICON_GAP : 0f;
        float total = itemAdvance + iconWidth + textWidth;
        float cursor = x + Math.max(size.padX * 0.5f, (width - total) * 0.5f);
        float centerY = y + height * 0.5f;
        int color = GlassPanel.withAlpha(foreground, alpha);

        if (hasItem) {
            // 走全屏统一的物品图标链路（与 CompactRow / 控制台卡片同一套）；未命中缓存的物品当帧不画、下一帧起显示
            ItemIconCache.getInstance().draw(canvas, item, cursor, centerY - itemBox * 0.5f, itemBox);
            cursor += itemAdvance;
        }
        if (icon != null) {
            FontRenderer.drawText(canvas, icon, cursor, CardLayout.baseline(centerY, ICON_SIZE), ICON_SIZE,
                    color, FontRenderer.MATERIAL_SYMBOLS);
            cursor += iconWidth;
        }
        if (!text.isEmpty()) {
            MinecraftText.draw(canvas, text, cursor, CardLayout.baseline(centerY, size.fontSize),
                    size.fontSize, foreground, alpha);
        }
    }

    // ── 命中 ──

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        return onClickAt(mx, my, x, y, getWidth(), button);
    }

    /** 以指定宽度做命中判定，与 {@link #drawAt} 配对使用。 */
    public boolean onClickAt(float mx, float my, float x, float y, float width, int button) {
        if (button != 0 || isDisabled()) return false;
        if (mx < x || mx > x + width || my < y || my > y + size.height) return false;
        press.pulse();
        action.run();
        return true;
    }
}
