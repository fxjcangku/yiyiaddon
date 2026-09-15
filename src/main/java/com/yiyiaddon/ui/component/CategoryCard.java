package com.yiyiaddon.ui.component;

import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

/**
 * 分类入口卡片：图标、分类名、描述与模块数量。
 *
 * <p>内容层不使用玻璃：卡片是实心表面，靠表面色阶、1px 分隔线与柔和投影分出层次，
 * 悬停时提亮一档并加强投影。外观全部由主题色与主题圆角决定。</p>
 *
 * <p>高度与 {@link ModuleCard#HEIGHT} 保持一致：模块中心是一条不分分类的纵向清单，
 * 入口行与模块行同高，清单的网格几何才能共用一套计算。</p>
 */
public final class CategoryCard {

    /**
     * 卡片高度。
     *
     * <p>直接取模块行高：两者必须严格相等（同一条清单里混排），写成引用就不会各自漂移。</p>
     */
    public static final float HEIGHT = ModuleCard.HEIGHT;

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
    /** 描述行与模块数行的基线（相对卡片顶部）。 */
    private static final float DESC_BASELINE = 48f;
    private static final float COUNT_BASELINE = 66f;
    private static final String ARROW = "\uE5CC";

    private CategoryCard() {
    }

    public static void draw(Canvas canvas, ModuleCategory category, String countText, float x, float y, float w,
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
        drawIconCentered(canvas, category.icon(), iconX + ICON_BOX / 2f, iconCenterY, ICON_GLYPH,
                GlassPanel.withAlpha(tc.accent, alpha));

        float titleX = iconX + ICON_BOX + ICON_GAP;
        float titleMax = Math.max(0f, x + w - ARROW_RESERVE - titleX);
        FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(category.displayName(), titleMax, TITLE_SIZE), titleX,
                CardLayout.baseline(iconCenterY, TITLE_SIZE), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        float textMax = Math.max(0f, w - PAD_X * 2f);
        FontRenderer.drawText(canvas, CardLayout.ellipsize(category.description(), textMax, CAPTION_SIZE), x + PAD_X,
                y + DESC_BASELINE, CAPTION_SIZE, GlassPanel.withAlpha(tc.secondaryText, alpha));

        String count = countText == null || countText.isBlank() ? "暂无模块" : countText;
        FontRenderer.drawText(canvas, count, x + PAD_X, y + COUNT_BASELINE, CAPTION_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));

        drawIconCentered(canvas, ARROW, x + w - ARROW_INSET, iconCenterY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));
    }

    static void drawIconCentered(Canvas canvas, String glyph, float centerX, float centerY, float size, int argb) {
        if (glyph == null || glyph.isEmpty()) return;
        float glyphWidth = FontRenderer.measureTextWidth(glyph, size, FontRenderer.MATERIAL_SYMBOLS);
        FontRenderer.drawText(canvas, glyph, centerX - glyphWidth / 2f, CardLayout.baseline(centerY, size), size,
                argb, FontRenderer.MATERIAL_SYMBOLS);
    }
}
