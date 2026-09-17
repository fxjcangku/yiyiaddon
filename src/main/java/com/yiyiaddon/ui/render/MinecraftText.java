package com.yiyiaddon.ui.render;

import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
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
 *
 * <p><b>颜色码跟随主题明暗：</b>16 色在原版是固定 RGB，深色面板上没问题；浅色主题下
 * {@code §f} / {@code §7} / {@code §8} 会糊在白底上，因此浅色主题走
 * {@link #LIGHT_COLORS}（同色相的深色版），深色主题仍用原版调色板。</p>
 */
public final class MinecraftText {

    /** 颜色码字符。 */
    private static final char CODE_PREFIX = '\u00A7';

    /** 原版 16 色（RGB，无 alpha）。 */
    private static final int[] COLORS = {
            0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
            0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    };

    /**
     * 浅色主题下的同一套 16 色（保持色相，压暗以提高白底对比度）。
     *
     * <p>界面文案逐字保留旧项目的 {@code §} 颜色码，而旧项目面板是深色的；换到浅色主题后
     * {@code §f} 白、{@code §7} 灰、{@code §e} 亮黄在白底上会直接糊掉。这里只做「同一语义的
     * 深色版」映射，深色主题一个像素都不改。</p>
     */
    private static final int[] LIGHT_COLORS = {
            0x1C1C1E, 0x0000AA, 0x1E7A1E, 0x0B6E78, 0xB00000, 0x8A0A8A, 0xA86A00, 0x6B6B70,
            0x8A8A90, 0x2A4BD7, 0x1E8E3E, 0x0B7C8C, 0xC62828, 0xB33BB3, 0x9A6E00, 0x1C1C1E
    };

    /** 取当前主题下该颜色码实际使用的 RGB。 */
    private static int palette(int index) {
        ClickGuiThemeColors colors = ClickGuiThemeColors.current();
        return colors != null && !colors.dark ? LIGHT_COLORS[index] : COLORS[index];
    }

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
                        color = palette(index);
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

    /**
     * 按可见宽度截断带颜色码文本，超宽补省略号（保留颜色码，不切断 {@code §x} 对）。
     *
     * <p><b>为什么要按可见宽度算</b>：界面里没有裁剪原语，超宽文本会直接压到右边的列或控件上；
     * 而测量必须走 {@link #measure}（它不把颜色码字符算进宽度），不能用 {@code CardLayout.ellipsize}
     * ——后者按 {@code FontRenderer} 口径会把 {@code §x} 也算进宽度，于是一段纯颜色码就被当成超长截掉。</p>
     *
     * <p><b>单一实现（第 169 条）</b>：本方法由各控制台顶部状态区里 5 份逐字相同的私有 {@code fit}
     * 收敛而来（原位于 {@code MiningConsoleScreen / AutoFarmConsoleScreen / VillagerConsoleScreen /
     * AutoChestConsoleScreen / EnchantConsoleScreen} 的 {@code StatusStrip} 内）。</p>
     *
     * @param text     带颜色码的原文，{@code null} / 空串按 {@code ""} 处理
     * @param size     字号（与绘制时一致）
     * @param maxWidth 可见宽度上限；{@code <= 0} 时返回空串（调用点因此不会画出半个字）
     * @return 能放进 {@code maxWidth} 的文本，放不下时以 {@code …} 结尾
     */
    public static String fit(String text, float size, float maxWidth) {
        if (text == null || text.isEmpty()) return "";
        if (maxWidth <= 0f) return "";
        if (measure(text, size, false) <= maxWidth) return text;
        for (int end = text.length() - 1; end > 0; end--) {
            if (text.charAt(end - 1) == CODE_PREFIX) continue; // 不要把颜色码切一半
            String candidate = text.substring(0, end) + "…";
            if (measure(candidate, size, false) <= maxWidth) return candidate;
        }
        return "…";
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
