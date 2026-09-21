package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.PathSegment;
import io.github.humbleui.skija.PathVerb;
import io.github.humbleui.types.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卡片图标的字形绘制：把一个 Material 字形按给定中心点居中画出来（中空原样画、实心填空外轮廓）。
 *
 * <p>模块中心的分类头、模块行与页面入口行的图标都走这一处，保证「字体 / 字号 / 居中算法」只有一份。
 * 图标码点必须在 {@code MaterialSymbolsRounded.ttf} 里验真存在（开发习惯第 140 条）。</p>
 */
public final class CardIcons {

    /** 实心轮廓缓存：键为 {@code 字形#字号}，值是「只留外轮廓」的路径。 */
    private static final Map<String, Path> SOLID = new HashMap<>();
    /** 画路径用的画笔（同 {@link GlassPanel} 的静态画笔口径：抗锯齿、纯填充）。 */
    private static final Paint PAINT = new Paint().setAntiAlias(true);

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

    /**
     * 居中绘制一个 Material 字形的<b>实心</b>版本：同一个外轮廓，填空而不是描边。
     *
     * <p><b>为什么要自己填而不是换一枚实心字形</b>：{@code MaterialSymbolsRounded.ttf} 是静态实例
     * （字体表里没有 {@code fvar} 轴），实心与中空是两枚独立字形——中空的 {@code star} 有码位
     * （U+E838），实心的 {@code star.fill} <b>在 cmap 里没有任何码位映射</b>（GSUB 只有 rlig），
     * 文字接口根本够不到它。实测两枚字形的外轮廓逐位相同（包围盒一致），所以「中空字形的外轮廓
     * 单独填满」与「实心字形」是同一个形状，且与描边版<b>外缘完全重合</b>——
     * 收藏时看到的是这颗星被填满，而不是换了一颗形状略有出入的星。</p>
     *
     * @param glyph   图标码点；{@code null} 或空串时不画
     * @param centerX 水平中心
     * @param centerY 垂直中心
     * @param size    字号
     * @param argb    颜色（已含 alpha）
     */
    public static void drawCenteredFilled(Canvas canvas, String glyph, float centerX, float centerY, float size,
                                          int argb) {
        Path solid = solidPath(glyph, size);
        if (solid == null) {
            // 字体换了、这枚字形没了：退回描边版总比画不出东西强
            drawCentered(canvas, glyph, centerX, centerY, size, argb);
            return;
        }
        float glyphWidth = FontRenderer.measureTextWidth(glyph, size, FontRenderer.MATERIAL_SYMBOLS);
        PAINT.setColor(argb);
        canvas.save();
        canvas.translate(centerX - glyphWidth / 2f, CardLayout.baseline(centerY, size));
        canvas.drawPath(solid, PAINT);
        canvas.restore();
    }

    /**
     * 字形的实心轮廓（按 {@code 字形#字号} 缓存）：中空字形由「外轮廓 + 内轮廓」若干条闭合轮廓组成，
     * 只保留面积最大的那条再填充，得到的就是它的实心形状。
     */
    private static Path solidPath(String glyph, float size) {
        if (glyph == null || glyph.isEmpty()) return null;
        String key = glyph + '#' + Float.floatToIntBits(size);
        if (SOLID.containsKey(key)) return SOLID.get(key);
        Path outline = FontRenderer.glyphPath(FontRenderer.MATERIAL_SYMBOLS, size, glyph);
        Path solid = outline == null ? null : outerContour(outline);
        SOLID.put(key, solid);
        return solid;
    }

    /** 取路径里面积最大的那条轮廓并重建：中空图标的外轮廓就是它的实心形状。 */
    private static Path outerContour(Path outline) {
        List<PathSegment> current = new ArrayList<>();
        List<PathSegment> best = null;
        double bestArea = 0d;
        for (PathSegment segment : outline) {
            if (segment.getVerb() == PathVerb.MOVE && !current.isEmpty()) {
                double area = contourArea(current);
                if (area > bestArea) {
                    bestArea = area;
                    best = current;
                }
                current = new ArrayList<>();
            }
            if (segment.getVerb() != PathVerb.DONE) current.add(segment);
        }
        if (contourArea(current) > bestArea) best = current;
        return best == null ? null : rebuild(best);
    }

    /** 轮廓的近似面积（按锚点连线走鞋带公式；曲线段用弦近似，只用来挑「最外面那一条」）。 */
    private static double contourArea(List<PathSegment> contour) {
        double sum = 0d;
        float firstX = 0f;
        float firstY = 0f;
        float prevX = 0f;
        float prevY = 0f;
        boolean started = false;
        for (PathSegment segment : contour) {
            Point anchor = anchorOf(segment);
            if (anchor == null) continue;
            if (!started) {
                firstX = prevX = anchor.getX();
                firstY = prevY = anchor.getY();
                started = true;
                continue;
            }
            sum += (double) prevX * anchor.getY() - (double) anchor.getX() * prevY;
            prevX = anchor.getX();
            prevY = anchor.getY();
        }
        if (!started) return 0d;
        sum += (double) prevX * firstY - (double) firstX * prevY;
        return Math.abs(sum) / 2d;
    }

    /** 一段曲线落笔的锚点（曲线段取终点；起笔与闭合段没有锚点）。 */
    private static Point anchorOf(PathSegment segment) {
        return switch (segment.getVerb()) {
            case MOVE, LINE -> segment.getP0();
            case QUAD, CONIC -> segment.getP1();
            case CUBIC -> segment.getP2();
            case CLOSE, DONE -> null;
        };
    }

    /** 按原始段序把一条轮廓重建成新的路径（曲线段原样搬，不降级成折线）。 */
    private static Path rebuild(List<PathSegment> contour) {
        PathBuilder builder = new PathBuilder();
        for (PathSegment segment : contour) {
            Point p0 = segment.getP0();
            Point p1 = segment.getP1();
            Point p2 = segment.getP2();
            switch (segment.getVerb()) {
                case MOVE -> builder.moveTo(p0.getX(), p0.getY());
                case LINE -> builder.lineTo(p0.getX(), p0.getY());
                case QUAD -> builder.quadTo(p0.getX(), p0.getY(), p1.getX(), p1.getY());
                case CONIC -> builder.conicTo(p0.getX(), p0.getY(), p1.getX(), p1.getY(), segment.getConicWeight());
                case CUBIC -> builder.cubicTo(p0.getX(), p0.getY(), p1.getX(), p1.getY(), p2.getX(), p2.getY());
                case CLOSE -> builder.closePath();
                default -> {
                }
            }
        }
        return builder.snapshot();
    }
}
