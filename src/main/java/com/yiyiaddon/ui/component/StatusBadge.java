package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import io.github.humbleui.skija.Canvas;

/**
 * 状态标记：小圆点 + 文字。
 *
 * <p>苹果系统用颜色本身表达状态，不用胶囊底色，因此这里不再画底。</p>
 */
public final class StatusBadge {

    public static final float HEIGHT = 16f;

    private static final float DOT_SIZE = 6f;
    private static final float DOT_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    private StatusBadge() {
    }

    public static float width(String text) {
        return DOT_SIZE + DOT_GAP + FontRenderer.measureTextWidth(text == null ? "" : text, TEXT_SIZE);
    }

    /**
     * 绘制状态标记，左侧垂直居中于 (x, centerY)。
     *
     * @param color 状态语义色，圆点用满强度，文字用同一色
     */
    public static void draw(Canvas canvas, float x, float centerY, String text, int color, float alpha) {
        GlassPanel.fill(canvas, x, centerY - DOT_SIZE / 2f, DOT_SIZE, DOT_SIZE, DOT_SIZE / 2f, color, alpha);
        FontRenderer.drawText(canvas, text == null ? "" : text,
                x + DOT_SIZE + DOT_GAP, CardLayout.baseline(centerY, TEXT_SIZE), TEXT_SIZE,
                GlassPanel.withAlpha(color, alpha));
    }
}
