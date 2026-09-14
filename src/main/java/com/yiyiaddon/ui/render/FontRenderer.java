package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FontRenderer {

    public static final String DEFAULT = "harmony";
    public static final String DEFAULT_BOLD = "harmony_bold";
    public static final String ICON = "icon";
    public static final String MATERIAL_SYMBOLS = "material_symbols";

    private static final Map<String, Typeface> typefaces = new HashMap<>();
    private static final FontMgr fontMgr = FontMgr.getDefault();
    private static final Map<String, Font> fonts = new HashMap<>();
    private static final Map<String, Float> widthCache = new HashMap<>();
    private static final Paint textPaint = new Paint().setAntiAlias(true);

    /** 字形覆盖缓存：键为 {@code 字体名#码点}，值为主字体是否含该字形。 */
    private static final Map<String, Boolean> coverageCache = new HashMap<>();
    /** 缺字形字符的兜底字族：按码点缓存，null 值表示系统也没有。 */
    private static final Map<Integer, Typeface> fallbackTypefaces = new HashMap<>();
    /** 兜底字族按字号缓存的实际字体对象。 */
    private static final Map<Typeface, Map<Integer, Font>> fallbackFonts = new HashMap<>();

    static {
        registerFromResources(DEFAULT, "/assets/yiyiaddon/fonts/harmony.ttf");
        registerFromResources(DEFAULT_BOLD, "/assets/yiyiaddon/fonts/harmony_bold.ttf");
        registerFromResources(ICON, "/assets/yiyiaddon/fonts/icon.ttf");
        registerFromResources(MATERIAL_SYMBOLS, "/assets/yiyiaddon/fonts/MaterialSymbolsRounded.ttf");
    }

    public static void registerFromResources(String name, String resourcePath) {
        try (InputStream is = FontRenderer.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Font not found: " + resourcePath);
            byte[] data = is.readAllBytes();
            typefaces.put(name, fontMgr.makeFromData(Data.makeFromBytes(data)));
            clearCaches(name);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load font: " + resourcePath, e);
        }
    }

    public static void registerFromFile(String name, Path path) {
        typefaces.put(name, fontMgr.makeFromFile(path.toAbsolutePath().toString()));
        clearCaches(name);
    }

    private static Font makeFont(String fontName, float size) {
        String key = fontKey(fontName, size);
        Font font = fonts.get(key);
        if (font != null) return font;

        Typeface typeface = typefaces.getOrDefault(fontName, typefaces.get(DEFAULT));
        font = configure(new Font(typeface, size));
        fonts.put(key, font);
        return font;
    }

    private static Font configure(Font font) {
        font.setSubpixel(true);
        font.setHinting(FontHinting.SLIGHT);
        font.setEdging(FontEdging.SUBPIXEL_ANTI_ALIAS);
        return font;
    }

    public static void drawText(Canvas canvas, String text, float x, float y, float size, int argb) {
        drawText(canvas, text, x, y, size, argb, DEFAULT);
    }

    public static void drawText(Canvas canvas, String text, float x, float y, float size, int argb, String fontName) {
        Font font = makeFont(fontName, size);
        textPaint.setColor(argb);
        if (!needsFallback(fontName, text)) {
            canvas.drawString(text, x, y, font, textPaint);
            return;
        }
        drawWithFallback(canvas, text, x, y, size, fontName, font);
    }

    /**
     * 逐段绘制：主字体缺字形的字符改用系统兜底字族。
     *
     * <p>旧项目文案大量使用 {@code ▸ ⚠ ✓ ✗}（字段分隔符、状态前缀），鸿蒙字体不含这四个字形，
     * 不做兜底会渲染成空白或方框。同字体的连续字符合并成一段绘制，避免逐字调用。</p>
     */
    private static void drawWithFallback(Canvas canvas, String text, float x, float y, float size,
                                         String fontName, Font mainFont) {
        float cursor = x;
        int index = 0;
        int length = text.length();
        while (index < length) {
            Font font = resolveFont(text, index, size, fontName, mainFont);
            int start = index;
            index += Character.charCount(text.codePointAt(index));
            while (index < length && resolveFont(text, index, size, fontName, mainFont) == font) {
                index += Character.charCount(text.codePointAt(index));
            }
            String chunk = text.substring(start, index);
            canvas.drawString(chunk, cursor, y, font, textPaint);
            cursor += font.measureTextWidth(chunk);
        }
    }

    /** 取该位置字符应使用的字体：主字体覆盖则用主字体，否则用系统兜底字族。 */
    private static Font resolveFont(String text, int index, float size, String fontName, Font mainFont) {
        int codePoint = text.codePointAt(index);
        if (codePoint <= 0x7F || covers(fontName, mainFont, codePoint)) return mainFont;
        Font fallback = fallbackFont(codePoint, size);
        return fallback == null ? mainFont : fallback;
    }

    /** 主字体是否含该码点字形（按字体名 + 码点缓存）。 */
    private static boolean covers(String fontName, Font font, int codePoint) {
        String key = fontName + '#' + codePoint;
        Boolean cached = coverageCache.get(key);
        if (cached != null) return cached;
        boolean covered = font.getUTF32Glyph(codePoint) != 0;
        coverageCache.put(key, covered);
        return covered;
    }

    /** 按码点向系统字族管理器索取兜底字族，并缓存其指定字号的字体对象。 */
    private static Font fallbackFont(int codePoint, float size) {
        if (fallbackTypefaces.containsKey(codePoint)) {
            Typeface typeface = fallbackTypefaces.get(codePoint);
            return typeface == null ? null : fontOf(typeface, size);
        }
        Typeface typeface = fontMgr.matchFamilyStyleCharacter(null, FontStyle.NORMAL, null, codePoint);
        fallbackTypefaces.put(codePoint, typeface);
        return typeface == null ? null : fontOf(typeface, size);
    }

    private static Font fontOf(Typeface typeface, float size) {
        Integer sizeKey = Float.floatToIntBits(size);
        Map<Integer, Font> bySize = fallbackFonts.computeIfAbsent(typeface, key -> new HashMap<>());
        Font font = bySize.get(sizeKey);
        if (font == null) {
            font = configure(new Font(typeface, size));
            bySize.put(sizeKey, font);
        }
        return font;
    }

    /** 文本中是否存在主字体无法覆盖的字符。 */
    private static boolean needsFallback(String fontName, String text) {
        if (text == null) return false;
        Font font = makeFont(fontName, 1f);
        for (int index = 0; index < text.length(); ) {
            int codePoint = text.codePointAt(index);
            if (codePoint > 0x7F && !covers(fontName, font, codePoint)) return true;
            index += Character.charCount(codePoint);
        }
        return false;
    }

    public static void drawSegmented(Canvas canvas, Segment[] segments, float x, float y) {
        float cursor = x;
        for (Segment seg : segments) {
            drawText(canvas, seg.text, cursor, y, seg.size, seg.argb, seg.fontName);
            cursor += measureTextWidth(seg.text, seg.size, seg.fontName) + seg.gap;
        }
    }

    /** 用粗体字族绘制文本，用于标题层级。 */
    public static void drawTextBold(Canvas canvas, String text, float x, float y, float size, int argb) {
        drawText(canvas, text, x, y, size, argb, DEFAULT_BOLD);
    }

    /** 粗体文本宽度。 */
    public static float measureTextWidthBold(String text, float size) {
        return measureTextWidth(text, size, DEFAULT_BOLD);
    }

    public static float measureTextWidth(String text, float size) {
        return measureTextWidth(text, size, DEFAULT);
    }

    public static float measureTextWidth(String text, float size, String fontName) {
        if (text.length() <= 32) {
            String key = widthKey(fontName, size, text);
            Float cached = widthCache.get(key);
            if (cached != null) return cached;
            float width = measure(fontName, size, text);
            widthCache.put(key, width);
            return width;
        }
        return measure(fontName, size, text);
    }

    /** 与 {@link #drawWithFallback} 同一套分段规则计算宽度，保证测量结果与绘制终点一致。 */
    private static float measure(String fontName, float size, String text) {
        Font mainFont = makeFont(fontName, size);
        if (!needsFallback(fontName, text)) return mainFont.measureTextWidth(text);

        float total = 0f;
        int index = 0;
        int length = text.length();
        while (index < length) {
            Font font = resolveFont(text, index, size, fontName, mainFont);
            int start = index;
            index += Character.charCount(text.codePointAt(index));
            while (index < length && resolveFont(text, index, size, fontName, mainFont) == font) {
                index += Character.charCount(text.codePointAt(index));
            }
            total += font.measureTextWidth(text.substring(start, index));
        }
        return total;
    }

    public static float getLineHeight(float size) {
        return getLineHeight(size, DEFAULT);
    }

    public static float getAscent(float size) {
        return getAscent(size, DEFAULT);
    }

    public static float getAscent(float size, String fontName) {
        return -makeFont(fontName, size).getMetrics().getAscent();
    }

    public static float getLineHeight(float size, String fontName) {
        FontMetrics m = makeFont(fontName, size).getMetrics();
        return -m.getAscent() + m.getDescent();
    }

    private static String fontKey(String fontName, float size) {
        return fontName + '#' + Float.floatToIntBits(size);
    }

    private static String widthKey(String fontName, float size, String text) {
        return fontKey(fontName, size) + '#' + text;
    }

    private static void clearCaches(String fontName) {
        fonts.keySet().removeIf(key -> key.startsWith(fontName + '#'));
        widthCache.keySet().removeIf(key -> key.startsWith(fontName + '#'));
    }

    public static class Segment {
        public final String text;
        public final float size;
        public final int argb;
        public final String fontName;
        public final float gap;

        public Segment(String text, float size, int argb) { this(text, size, argb, DEFAULT, 0f); }
        public Segment(String text, float size, int argb, String fontName) { this(text, size, argb, fontName, 0f); }
        public Segment(String text, float size, int argb, String fontName, float gap) {
            this.text = text; this.size = size; this.argb = argb; this.fontName = fontName; this.gap = gap;
        }
    }
}
