package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 列表行：图标 + 名称 + 补充信息 + 状态徽标 + 行内操作按钮。
 *
 * <p>用于 ID 清单、选择器的候选行、各类「一条记录一行」的清单。结构：</p>
 *
 * <pre>
 * [图标][名称][补充信息] ................................ [徽标][操作按钮…]
 * </pre>
 *
 * <p>整行可点击（{@link #onActivate}），行内操作按钮独立响应、优先于整行点击。文案支持
 * Minecraft 颜色码，由 {@link MinecraftText} 解析，因此旧项目原文可直接填入。</p>
 *
 * <p>名称与补充信息在操作区左侧被裁切，不会压到按钮上。</p>
 */
public class ListRow implements CompactElement {

    /**
     * 行高：与外部页面（模块中心 / 设置 / 界面 / 模块页）的清单行同高（{@link ModuleRow#HEIGHT}）。
     *
     * <p>用户 2026-09-16 原话「控制台里面 也要缩小啊 你只缩小外面的 控制台里面都没变」——本行是
     * 控制台的「点击选择 / 目标物品 / 名单」这些选择器窗口（以及 Baritone 列表、ID 清单、箱子清单）
     * 唯一的清单行，原来是 36，比上一轮统一后的 24 大出半行，从控制台点进选择器就立刻变大一号。
     * 行高直接读外面那一个常量，不再各写一份数字。</p>
     *
     * <p>收的只是几何：字形（名称 11 / 补充信息 10 / 徽标 10）一个都没动；图标底框取模块行的
     * {@link ModuleRow#ICON_BOX}，与同一轮的其它行同一套度量。命中 / 绘制 / 滚动高度三处都读
     * {@link #HEIGHT}（{@code draw} 里的裁切矩形也由它推导），行高变化不会出现裁切或点击错位。</p>
     */
    public static final float HEIGHT = ModuleRow.HEIGHT;

    private static final float PAD_X = 10f;
    private static final float ICON_SIZE = ModuleRow.ICON_BOX;
    private static final float ICON_GAP = 8f;
    private static final float NAME_SIZE = 11f;
    private static final float DETAIL_SIZE = 10f;
    private static final float DETAIL_GAP = 6f;
    private static final float BADGE_SIZE = 10f;
    private static final float BADGE_GAP = 8f;
    private static final float ACTION_GAP = 6f;
    private static final float HOVER_SMOOTHING = 14f;
    /** 选中态的强调色镀层系数：能一眼看出「这条已加入」，又不至于盖掉行内的文字与图标。 */
    private static final float SELECTED_TINT = 0.18f;
    /** 选中态的强调色描边强度。 */
    private static final float SELECTED_RIM = 0.35f;

    /** 图标绘制回调：由调用方决定画物品、方块还是实体图标。 */
    @FunctionalInterface
    public interface IconPainter {
        boolean draw(Canvas canvas, float x, float y, float size);
    }

    private final Supplier<String> name;
    private IconPainter icon;
    private Supplier<String> detail;
    private Supplier<String> badge;
    /** 徽标颜色；负值表示未指定，绘制时取当前主题的次级文字色。 */
    private int badgeColor = -1;
    private final List<SettingWidget> actions = new ArrayList<>();
    private Runnable onActivate;
    /** 选中态：整行镀一层强调色（通用选择器里「已加入」的行）。 */
    private boolean selected;

    private float hover;
    private boolean hovered;

    public ListRow(Supplier<String> name) {
        this.name = name == null ? () -> "" : name;
    }

    public ListRow(String name) {
        this(() -> name);
    }

    // ── 链式配置 ──

    /** 行首图标；不设置则整行左移，不留空位。 */
    public ListRow icon(IconPainter painter) {
        this.icon = painter;
        return this;
    }

    /** 名称右侧的补充信息（技术 ID、坐标等），次要颜色。 */
    public ListRow detail(Supplier<String> detail) {
        this.detail = detail;
        return this;
    }

    /** 操作区左侧的状态徽标；{@code rgb} 传负值表示跟随主题次级文字色。 */
    public ListRow badge(Supplier<String> badge, int rgb) {
        this.badge = badge;
        this.badgeColor = rgb < 0 ? -1 : rgb & 0xFFFFFF;
        return this;
    }

    /** 追加一个行内操作控件（按钮、图标按钮等），从左到右排列在行尾。 */
    public ListRow action(SettingWidget widget) {
        if (widget != null) actions.add(widget);
        return this;
    }

    /** 整行点击（操作按钮之外的区域）。 */
    public ListRow onActivate(Runnable action) {
        this.onActivate = action;
        return this;
    }

    /**
     * 选中态：整行镀一层强调色 + 强调色描边。
     *
     * <p>通用选择器改成单栏后，选中项不再跳到另一栏，只能靠行自己说明「这条已经加了」——
     * 只有右侧按钮从 ＋ 变 − 不够醒目，扫一眼列表分不出哪些是已选。行本身不可变，
     * 选中变化由调用方重建行，因此这里用普通布尔而不是 Supplier。</p>
     */
    public ListRow selected(boolean selected) {
        this.selected = selected;
        return this;
    }

    // ── CompactElement ──

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
        for (SettingWidget widget : actions) widget.update(dt);
        hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * HOVER_SMOOTHING);
        if (hover < 0.001f) hover = 0f;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = GlassPanel.rowRadius(HEIGHT);
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f, rowAlpha);
        GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
        if (hover > 0.01f) {
            GlassPanel.fill(canvas, x, y, width, HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
        }
        if (selected) {
            GlassPanel.fill(canvas, x, y, width, HEIGHT, radius, tc.accent, rowAlpha * SELECTED_TINT);
            GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.accent, alpha, SELECTED_RIM);
        }

        float centerY = y + HEIGHT / 2f;
        float cursor = x + PAD_X;
        if (icon != null) {
            icon.draw(canvas, cursor, centerY - ICON_SIZE / 2f, ICON_SIZE);
            cursor += ICON_SIZE + ICON_GAP;
        }

        float actionsLeft = actionsLeft(x, width);

        // 名称与补充信息：裁切到操作区左侧，避免压住按钮
        float textRight = actionsLeft - DETAIL_GAP;
        canvas.save();
        canvas.clipRect(Rect.makeXYWH(cursor, y + 2f, Math.max(0f, textRight - cursor), HEIGHT - 4f));
        try {
            MinecraftText.draw(canvas, name.get(), cursor, CardLayout.baseline(centerY, NAME_SIZE),
                    NAME_SIZE, tc.primaryText, alpha, true);
            if (detail != null) {
                String text = detail.get();
                if (text != null && !text.isEmpty()) {
                    float nameWidth = MinecraftText.measure(name.get(), NAME_SIZE, true);
                    MinecraftText.draw(canvas, text, cursor + nameWidth + DETAIL_GAP,
                            CardLayout.baseline(centerY, DETAIL_SIZE), DETAIL_SIZE, tc.labelTertiary, alpha);
                }
            }
        } finally {
            canvas.restore();
        }

        drawBadge(canvas, centerY, actionsLeft, alpha, tc);
        drawActions(canvas, y, centerY, actionsLeft, alpha, mouseX, mouseY);
    }

    private void drawBadge(Canvas canvas, float centerY, float actionsLeft, float alpha, ClickGuiThemeColors tc) {
        if (badge == null) return;
        String text = badge.get();
        if (text == null || text.isEmpty()) return;
        float badgeWidth = MinecraftText.measure(text, BADGE_SIZE, false);
        float badgeX = actionsLeft - BADGE_GAP - badgeWidth;
        MinecraftText.draw(canvas, text, badgeX, CardLayout.baseline(centerY, BADGE_SIZE), BADGE_SIZE,
                badgeColor >= 0 ? badgeColor : tc.secondaryText, alpha);
    }

    private void drawActions(Canvas canvas, float y, float centerY, float actionsLeft, float alpha,
                             float mouseX, float mouseY) {
        float cursor = actionsLeft;
        for (SettingWidget widget : actions) {
            float widgetY = centerY - widget.getHeight() / 2f;
            widget.hover(mouseX, mouseY, cursor, widgetY, widget.getWidth());
            widget.draw(canvas, cursor, widgetY, alpha);
            cursor += widget.getWidth() + ACTION_GAP;
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (button != 0 || my < y || my > y + HEIGHT) return false;
        float cursor = actionsLeft(x, width);
        float centerY = y + HEIGHT / 2f;
        for (SettingWidget widget : actions) {
            float widgetY = centerY - widget.getHeight() / 2f;
            if (widget.onClick(mx, my, cursor, widgetY, button)) return true;
            cursor += widget.getWidth() + ACTION_GAP;
        }
        if (onActivate != null && mx >= x && mx <= x + width) {
            onActivate.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        float cursor = actionsLeft(x, width);
        float centerY = y + HEIGHT / 2f;
        for (SettingWidget widget : actions) {
            float widgetY = centerY - widget.getHeight() / 2f;
            if (widget.onDrag(mx, my, cursor, widgetY)) return true;
            cursor += widget.getWidth() + ACTION_GAP;
        }
        return false;
    }

    /** 行内操作区的左边界；绘制与命中共用。 */
    private float actionsLeft(float x, float width) {
        if (actions.isEmpty()) return x + width - PAD_X;
        float total = 0f;
        for (SettingWidget widget : actions) total += widget.getWidth() + ACTION_GAP;
        return x + width - PAD_X - (total - ACTION_GAP);
    }
}
