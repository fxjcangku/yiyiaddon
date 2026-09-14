package com.yiyiaddon.ui.component;

import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

/**
 * 模块卡片：图标、模块名、模块 ID、描述与启用状态。
 *
 * <p>与分类卡片同属内容层，使用实心表面；卡片本身不展开任何设置，
 * 点击后由调用方打开模块独立页面。</p>
 *
 * <p>紧凑规格与分类卡片一致（旧值 → 新值）：卡片高度 112 → 74，图标块 34 → 26，
 * 图标码位 19 → 17，标题 15 → 13，说明与模块 ID 11 → 10。图标与模块名同行，
 * 第二行左侧模块 ID、右侧状态标记，第三行整宽描述；模块 ID 与描述超长时按
 * {@link CardLayout#ellipsize} 截断，状态标记始终完整显示。</p>
 */
public final class ModuleCard {

    /** 卡片高度。 */
    public static final float HEIGHT = 74f;

    private static final float PAD_X = 12f;
    private static final float PAD_TOP = 8f;
    private static final float ICON_BOX = 26f;
    private static final float ICON_RADIUS = 8f;
    private static final float ICON_GLYPH = 17f;
    private static final float ICON_GAP = 8f;
    private static final float ARROW_GLYPH = 14f;
    private static final float ARROW_INSET = 16f;
    private static final float TITLE_SIZE = 13f;
    private static final float CAPTION_SIZE = 10f;
    /** 标题右侧为箭头预留的宽度。 */
    private static final float ARROW_RESERVE = ARROW_INSET + ARROW_GLYPH * 0.5f + 6f;
    /** 模块 ID 与状态标记所在行的中心线。 */
    private static final float NAME_ROW_CENTER = 46f;
    /** 模块 ID 文字与状态标记之间的最小空隙。 */
    private static final float BADGE_GAP = 10f;
    /** 描述行基线（相对卡片顶部）。 */
    private static final float DESC_BASELINE = 66f;
    private static final String ARROW = "\uE5CC";

    private ModuleCard() {
    }

    public static void draw(Canvas canvas, ModuleEntry entry, float x, float y, float w,
                            float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover);

        // 悬停时投影与描边高光同步提升（抬升 1px 由卡片页统一施加）
        GlassPanel.shadow(canvas, x, y, w, HEIGHT, radius, tc.shadow, alpha, 0.55f + 0.65f * hover);
        GlassPanel.frost(canvas, x, y, w, HEIGHT, radius, background, 0.70f, alpha);
        GlassPanel.sheen(canvas, x, y, w, HEIGHT, radius, tc, alpha, hover);
        GlassPanel.rim(canvas, x, y, w, HEIGHT, radius, tc.rim, alpha, 0.10f + 0.16f * hover);

        float iconX = x + PAD_X;
        float iconY = y + PAD_TOP;
        float iconCenterY = iconY + ICON_BOX / 2f;
        GlassPanel.fill(canvas, iconX, iconY, ICON_BOX, ICON_BOX, ICON_RADIUS, tc.accent, alpha * 0.16f);
        CategoryCard.drawIconCentered(canvas, entry.icon(), iconX + ICON_BOX / 2f, iconCenterY,
                ICON_GLYPH, GlassPanel.withAlpha(tc.accent, alpha));

        float titleX = iconX + ICON_BOX + ICON_GAP;
        float titleMax = Math.max(0f, x + w - ARROW_RESERVE - titleX);
        FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(entry.displayName(), titleMax, TITLE_SIZE), titleX,
                CardLayout.baseline(iconCenterY, TITLE_SIZE), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        boolean enabled = entry.enabled();
        String stateText = enabled ? "已启用" : "未启用";
        float badgeWidth = StatusBadge.width(stateText);
        float badgeX = x + w - PAD_X - badgeWidth;
        StatusBadge.draw(canvas, badgeX, y + NAME_ROW_CENTER, stateText,
                enabled ? tc.accent : tc.labelTertiary, alpha);

        float nameMax = Math.max(0f, badgeX - BADGE_GAP - (x + PAD_X));
        FontRenderer.drawText(canvas, CardLayout.ellipsize(entry.name(), nameMax, CAPTION_SIZE), x + PAD_X,
                CardLayout.baseline(y + NAME_ROW_CENTER, CAPTION_SIZE), CAPTION_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));

        FontRenderer.drawText(canvas, CardLayout.ellipsize(entry.description(), w - PAD_X * 2f, CAPTION_SIZE),
                x + PAD_X, y + DESC_BASELINE, CAPTION_SIZE, GlassPanel.withAlpha(tc.secondaryText, alpha));

        CategoryCard.drawIconCentered(canvas, ARROW, x + w - ARROW_INSET, iconCenterY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));
    }
}
