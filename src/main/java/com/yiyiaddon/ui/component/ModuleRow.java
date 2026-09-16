package com.yiyiaddon.ui.component;

import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

/**
 * 模块中心清单里的紧凑模块行（{@link #HEIGHT} = 36）。
 *
 * <p>整行左侧缩进一格，表示它隶属于上一行的分类头；一行内放下「图标 + 模块名 + 描述 + 状态 +
 * 进入箭头」，整行可点，滚动时一屏能看十行以上。</p>
 *
 * <p>行高刻意压到 36：模块中心要的是「一屏尽量多、滚动距离尽量短」，因此一行只放一行内容，
 * 不重复卡片那套多行基线排版。</p>
 */
public final class ModuleRow {

    /** 行高。 */
    public static final float HEIGHT = 36f;

    /** 左侧缩进（相对页面内容左边界），用来表达「属于上一行的分类」。 */
    public static final float INDENT = 14f;

    private static final float PAD_X = 10f;
    private static final float ICON_BOX = 22f;
    private static final float ICON_RADIUS = 6f;
    private static final float ICON_GLYPH = 15f;
    private static final float ICON_GAP = 8f;
    private static final float TITLE_SIZE = 12f;
    private static final float CAPTION_SIZE = 9.5f;
    private static final float ARROW_GLYPH = 13f;
    private static final float ARROW_INSET = 14f;
    private static final float BADGE_GAP = 10f;
    /** 模块名占「名称 + 描述」可用宽度的比例，其余留给描述；两栏固定比例，多行之间名称与描述对齐。 */
    private static final float NAME_RATIO = 0.42f;
    private static final String ARROW = "\uE5CC";

    private ModuleRow() {
    }

    /** 模块行：图标 / 名称 / 描述 / 启用状态都取自模块注册表 */
    public static void draw(Canvas canvas, ModuleEntry entry, float x, float y, float w,
                            float alpha, float hover, ClickGuiThemeColors tc) {
        boolean enabled = entry.enabled();
        drawEntry(canvas, entry.icon(), entry.displayName(), entry.description(),
                enabled ? "已启用" : "未启用", enabled ? tc.accent : tc.labelTertiary,
                x, y, w, alpha, hover, tc);
    }

    /**
     * 通用入口行：自带设置页的分类入口（例如 Baritone设置）复用它，右侧状态传「点击进入」。
     *
     * @param stateText 右侧状态文字；{@code null} 表示不画状态标记
     * @param stateColor 状态语义色（圆点与文字同色）
     */
    public static void drawEntry(Canvas canvas, String icon, String title, String description,
                                 String stateText, int stateColor, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        float rowX = x + INDENT;
        float rowW = Math.max(0f, w - INDENT);
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover);

        GlassPanel.frost(canvas, rowX, y, rowW, HEIGHT, radius, background, 0.55f, alpha);
        GlassPanel.rim(canvas, rowX, y, rowW, HEIGHT, radius, tc.rim, alpha, 0.06f + 0.14f * hover);

        float centerY = y + HEIGHT / 2f;
        float cursor = rowX + PAD_X;
        float iconY = centerY - ICON_BOX / 2f;
        GlassPanel.fill(canvas, cursor, iconY, ICON_BOX, ICON_BOX, ICON_RADIUS, tc.accent, alpha * 0.16f);
        CardIcons.drawCentered(canvas, icon, cursor + ICON_BOX / 2f, centerY, ICON_GLYPH,
                GlassPanel.withAlpha(tc.accent, alpha));
        cursor += ICON_BOX + ICON_GAP;

        // 右侧：状态标记 + 进入箭头（都右对齐，先算出状态标记的左边界，再定左边的文字区）
        float arrowX = rowX + rowW - ARROW_INSET;
        float badgeRight = arrowX - ARROW_GLYPH - BADGE_GAP;
        float badgeWidth = stateText == null ? 0f : StatusBadge.width(stateText);
        float badgeX = stateText == null ? badgeRight : Math.max(cursor, badgeRight - badgeWidth);
        if (stateText != null) {
            StatusBadge.draw(canvas, badgeX, centerY, stateText, stateColor, alpha);
        }
        CardIcons.drawCentered(canvas, ARROW, arrowX, centerY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));

        float available = Math.max(0f, badgeX - BADGE_GAP - cursor);
        if (available <= 0f) return;
        float nameMax = available * NAME_RATIO;
        FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(title, nameMax, TITLE_SIZE), cursor,
                CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        float descX = cursor + nameMax + ICON_GAP;
        float descMax = Math.max(0f, badgeX - BADGE_GAP - descX);
        if (descMax < 16f) return;
        FontRenderer.drawText(canvas, CardLayout.ellipsize(description, descMax, CAPTION_SIZE), descX,
                CardLayout.baseline(centerY, CAPTION_SIZE), CAPTION_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));
    }
}
