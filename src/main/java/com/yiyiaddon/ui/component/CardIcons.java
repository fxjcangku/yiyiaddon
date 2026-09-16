package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import io.github.humbleui.skija.Canvas;

/**
 * 卡片图标的字形绘制：把一个 Material 字形按给定中心点居中画出来。
 *
 * <p>模块中心的分类头、模块行与页面入口行的图标都走这一处，保证「字体 / 字号 / 居中算法」只有一份。
 * 图标码点必须在 {@code MaterialSymbolsRounded.ttf} 里验真存在（开发习惯第 140 条）。</p>
 */
public final class CardIcons {

    private CardIcons() {
    }

    /**
     * 居中绘制一个 Material 字形。
     *
     * @param glyph   图标码点；{@code null} 或空串时不画
     * @param centerX 水平中心
     * @param centerY 垂直中心
     * @param size    字号
     * @param argb    颜色（已含 alpha）
     */
    public static void drawCentered(Canvas canvas, String glyph, float centerX, float centerY, float size, int argb) {
        if (glyph == null || glyph.isEmpty()) return;
        float glyphWidth = FontRenderer.measureTextWidth(glyph, size, FontRenderer.MATERIAL_SYMBOLS);
        FontRenderer.drawText(canvas, glyph, centerX - glyphWidth / 2f, CardLayout.baseline(centerY, size), size,
                argb, FontRenderer.MATERIAL_SYMBOLS);
    }
}
