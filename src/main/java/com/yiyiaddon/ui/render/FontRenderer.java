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

    /**
     * 字体渲染参数：<b>一律落在整像素上</b>。
     *
     * <p>用户 2026-09-16 反馈「这个选择器 点进去之后 字都糊了」。实测本机（窗口 1280×720、GUI 缩放 3、
     * 界面大小 100%）面板由 {@code PanelFrame} 按 <b>1.344 设备像素 / 设计单位</b> 缩放绘制，于是每段
     * 文字的起点、每个字形的推进量都落在非整数设备像素上：开了亚像素定位时 Skia 会按这个小数偏移把字形
     * 横向抹开，小字号（选择器整页都是 10~11 号）就糊成一片。</p>
     *
     * <p>因此这里按「像素对齐」优先来配：<b>关掉亚像素定位</b>（每个字形落到最近整像素，笔画不再被
     * 摊到两列上），并把边缘改成灰度抗锯齿 —— 面板是半透明玻璃，LCD（清彩）抗锯齿要求不透明底色，
     * 在玻璃上会出彩边，反而更糊。字号与字宽口径不变。</p>
     */
    private static Font configure(Font font) {
        font.setSubpixel(false);
        font.setHinting(FontHinting.SLIGHT);
        font.setEdging(FontEdging.ANTI_ALIAS);
        return font;
    }

    public static void drawText(Canvas canvas, String text, float x, float y, float size, int argb) {
        drawText(canvas, text, x, y, size, argb, DEFAULT);
    }

    public static void drawText(Canvas canvas, String text, float x, float y, float size, int argb, String fontName) {
        Font font = makeFont(fontName, size);
        textPaint.setColor(argb);
        // 起点对齐到整数设备像素：见 deviceScale / configure 的注释
        float snappedX = snapToDeviceX(canvas, x);
        float snappedY = snapToDeviceY(canvas, y);
        if (!needsFallback(fontName, text)) {
            canvas.drawString(text, snappedX, snappedY, font, textPaint);
            return;
        }
        drawWithFallback(canvas, text, snappedX, snappedY, size, fontName, font);
    }

    /**
     * 画布当前的设备缩放（1 设计单位 = 多少物理像素）。
     *
     * <p>取自画布总矩阵而不是页面自己的缩放系数：主界面、模块页、独立面板、选择器、世界叠加层的
     * 变换各不相同（还叠加了 {@code guiScale}），只有总矩阵是它们共同的真相。</p>
     *
     * <p>旋转 / 斜切（折叠箭头的旋转动画）下横纵轴不再与屏幕网格平行，逐轴取整会引入抖动，
     * 此时返回 {@code null} 表示不做对齐。</p>
     */
    private static float[] deviceScale(Canvas canvas) {
        if (canvas == null) return null;
        float[] mat = canvas.getLocalToDeviceAsMatrix33().getMat();
        if (Math.abs(mat[1]) > 0.001f || Math.abs(mat[3]) > 0.001f) return null;
        float scaleX = mat[0];
        float scaleY = mat[4];
        if (!(scaleX > 0f) || !(scaleY > 0f) || !Float.isFinite(scaleX) || !Float.isFinite(scaleY)) return null;
        return mat;
    }

    /** 文字起点横向对齐到整设备像素；无法判定缩放时原样返回。 */
    private static float snapToDeviceX(Canvas canvas, float x) {
        float[] mat = deviceScale(canvas);
        return mat == null ? x : Math.round(x * mat[0]) / mat[0];
    }

    /** 文字基线纵向对齐到整设备像素；无法判定缩放时原样返回。 */
    private static float snapToDeviceY(Canvas canvas, float y) {
        float[] mat = deviceScale(canvas);
        return mat == null ? y : Math.round(y * mat[4]) / mat[4];
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
            // 每段起点都重新对齐整设备像素：段间推进量是设计单位下的浮点值，第二段起若不重新对齐，
            // 整段就落在非整数设备像素上被抹开，表现为「一行字前半清、后半糊」
            canvas.drawString(chunk, snapToDeviceX(canvas, cursor), y, font, textPaint);
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

    /**
     * 某个字形在该字号下的轮廓路径（{@code SkFont::getPath}：原点在基线、y 向下、已按字号缩放）。
     *
     * <p>给「只画图标不画文字」的场合用（见 {@link com.yiyiaddon.ui.component.CardIcons}）：
     * 那条路走路径而不是 {@code drawString}，所以拿得到轮廓、可以自己决定填哪几条。</p>
     *
     * @param glyph 单个码点
     * @return 轮廓路径；字体缺该字形时返回 {@code null}
     */
    public static io.github.humbleui.skija.Path glyphPath(String fontName, float size, String glyph) {
        if (glyph == null || glyph.isEmpty()) return null;
        Font font = makeFont(fontName, size);
        short glyphId = font.getUTF32Glyph(glyph.codePointAt(0));
        return glyphId == 0 ? null : font.getPath(glyphId);
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
