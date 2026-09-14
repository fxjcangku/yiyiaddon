package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.Canvas;

/**
 * Minecraft 颜色码文本工具：把带 {@code §} 的文本解析后绘制或测量。
 *
 * <p>旧项目的界面文案全部带 {@code §} 颜色码（例如 {@code §7物品 §8▸ §f12}）。按迁移铁律，
 * 这些文案必须逐字保留，因此界面层需要能解析并渲染它们。规则与原版一致：</p>
 *
 * <ul>
 *   <li>{@code §0}~{@code §9}、{@code §a}~{@code §f}：设置颜色，并重置粗体。</li>
 *   <li>{@code §l}：加粗（切换到粗体字族）。</li>
 *   <li>{@code §r}：恢复到传入的基准颜色与基准粗细。</li>
 *   <li>其余格式码（{@code §o} 斜体、{@code §n} 下划线、{@code §m} 删除线、{@code §k} 随机）暂不渲染，
 *       仅从文本中剔除，避免颜色码字符漏到界面上。</li>
 * </ul>
 */
public final class MinecraftText {

    /** 颜色码字符。 */
    private static final char CODE_PREFIX = '\u00A7';

    /** 原版 16 色（RGB，无 alpha）。 */
    private static final int[] COLORS = {
            0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
            0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    };

    private MinecraftText() {
    }

    /**
     * 解析并绘制带颜色码的文本。
     *
     * @param baseColor 未指定颜色时使用的颜色（RGB）
     * @param alpha     整体透明度
     * @param bold      基准是否加粗
     * @return 绘制的总宽度
     */
    public static float draw(Canvas canvas, String text, float x, float y, float size,
                             int baseColor, float alpha, boolean bold) {
        if (canvas == null || text == null || text.isEmpty()) return 0f;
        float cursor = x;
        int color = baseColor;
        boolean currentBold = bold;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == CODE_PREFIX && i + 1 < text.length()) {
                cursor = flush(canvas, buffer, cursor, y, size, color, alpha, currentBold);
                char code = Character.toLowerCase(text.charAt(++i));
                if (code == 'r') {
                    color = baseColor;
                    currentBold = bold;
                } else if (code == 'l') {
                    currentBold = true;
                } else {
                    int index = Character.digit(code, 16);
                    if (index >= 0) {
                        color = COLORS[index];
                        currentBold = false;
                    }
                }
                continue;
            }
            buffer.append(ch);
        }
        cursor = flush(canvas, buffer, cursor, y, size, color, alpha, currentBold);
        return cursor - x;
    }

    /** 按默认基准（给定颜色、不加粗）绘制。 */
    public static float draw(Canvas canvas, String text, float x, float y, float size,
                             int baseColor, float alpha) {
        return draw(canvas, text, x, y, size, baseColor, alpha, false);
    }

    /** 解析并测量带颜色码文本的宽度（不含颜色码字符）。 */
    public static float measure(String text, float size, boolean bold) {
        if (text == null || text.isEmpty()) return 0f;
        float total = 0f;
        boolean currentBold = bold;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == CODE_PREFIX && i + 1 < text.length()) {
                total += flushWidth(buffer, size, currentBold);
                char code = Character.toLowerCase(text.charAt(++i));
                if (code == 'r') {
                    currentBold = bold;
                } else if (code == 'l') {
                    currentBold = true;
                } else if (Character.digit(code, 16) >= 0) {
                    currentBold = false;
                }
                continue;
            }
            buffer.append(ch);
        }
        return total + flushWidth(buffer, size, currentBold);
    }

    /** 去掉全部颜色码，用于按可见宽度排版或截断。 */
    public static String strip(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == CODE_PREFIX && i + 1 < text.length()) {
                i++;
                continue;
            }
            out.append(ch);
        }
        return out.toString();
    }

    /** 文本是否含颜色码。 */
    public static boolean hasFormatting(String text) {
        return text != null && text.indexOf(CODE_PREFIX) >= 0;
    }

    private static float flush(Canvas canvas, StringBuilder buffer, float cursor, float y, float size,
                               int color, float alpha, boolean bold) {
        if (buffer.isEmpty()) return cursor;
        String chunk = buffer.toString();
        buffer.setLength(0);
        int a = (int) (Math.max(0f, Math.min(1f, alpha)) * 255f);
        int argb = (a << 24) | (color & 0xFFFFFF);
        if (bold) {
            FontRenderer.drawTextBold(canvas, chunk, cursor, y, size, argb);
            return cursor + FontRenderer.measureTextWidthBold(chunk, size);
        }
        FontRenderer.drawText(canvas, chunk, cursor, y, size, argb);
        return cursor + FontRenderer.measureTextWidth(chunk, size);
    }

    private static float flushWidth(StringBuilder buffer, float size, boolean bold) {
        if (buffer.isEmpty()) return 0f;
        String chunk = buffer.toString();
        buffer.setLength(0);
        return bold ? FontRenderer.measureTextWidthBold(chunk, size) : FontRenderer.measureTextWidth(chunk, size);
    }
}
