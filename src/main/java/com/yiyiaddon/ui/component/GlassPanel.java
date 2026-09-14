package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Shader;
import io.github.humbleui.types.RRect;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 表面绘制工具：圆角填充、高光描边、分隔线与柔和投影。
 *
 * <p>苹果风格的层次由三件东西构成：表面色阶、1px 高光内描边、向下的柔和投影。
 * 这里把三者收敛到一处，颜色与圆角只由主题决定，页面层不直接构造画刷。</p>
 *
 * <p>画刷为静态复用；投影用多层半透明描边近似，绘制期间不产生新的 Skia 对象。</p>
 */
public final class GlassPanel {

    private static final Paint FILL = new Paint().setAntiAlias(true);
    private static final Paint STROKE = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);

    /** 投影层数：层数越多越柔和，开销随层数线性增长。 */
    private static final int SHADOW_LAYERS = 4;
    private static final float SHADOW_SPREAD = 1.7f;

    /** 霜化渐变：顶部白色约 9% 不透明度，底部约 3%，这是「玻璃」而非「色块」的关键。 */
    private static final float FROST_TOP_ALPHA = 0.09f;
    private static final float FROST_BOTTOM_ALPHA = 0.03f;

    /** 不透明度量化档数：用于复用渐变着色器，避免每帧新建原生对象。 */
    private static final int ALPHA_BUCKETS = 8;
    private static final int SHADER_CACHE_LIMIT = 256;

    private static final Map<Long, Shader> FROST_SHADERS = new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Shader> eldest) {
            return size() > SHADER_CACHE_LIMIT;
        }
    };

    private GlassPanel() {
    }

    /**
     * 霜化玻璃填充：底色打底 + 自上而下的白色渐变霜。
     *
     * <p>玻璃不能用实色填充，否则在暗场景下就是一块深色板子。底色负责压住背后的画面，
     * 白色渐变霜负责让表面像磨砂玻璃一样接收环境光。</p>
     *
     * @param baseAlphaFraction 底色不透明度占整体不透明度的比例
     * @param alpha             整体淡入淡出系数
     */
    public static void frost(Canvas canvas, float x, float y, float w, float h, float radius,
                             int baseColor, float baseAlphaFraction, float alpha) {
        if (alpha <= 0.01f) return;
        FILL.setShader(null);
        FILL.setColor(withAlpha(baseColor, baseAlphaFraction * alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, radius), FILL);

        FILL.setShader(frostShader(y, y + h, alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, radius), FILL);
        FILL.setShader(null);
    }

    /** 按矩形纵向范围与不透明度档位缓存线性渐变着色器。 */
    private static Shader frostShader(float top, float bottom, float alpha) {
        int bucket = Math.round(Math.max(0f, Math.min(1f, alpha)) * ALPHA_BUCKETS);
        long key = ((long) Math.round(top) << 40) ^ ((long) Math.round(bottom) << 16) ^ bucket;
        return FROST_SHADERS.computeIfAbsent(key, k -> {
            float scale = bucket / (float) ALPHA_BUCKETS;
            return Shader.makeLinearGradient(0f, top, 0f, bottom, new int[]{
                    withAlpha(0xFFFFFF, FROST_TOP_ALPHA * scale),
                    withAlpha(0xFFFFFF, FROST_BOTTOM_ALPHA * scale)});
        });
    }

    /** 填充圆角矩形。 */
    public static void fill(Canvas canvas, float x, float y, float w, float h, float radius, int color, float alpha) {
        FILL.setColor(withAlpha(color, alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, radius), FILL);
    }

    /** 描边圆角矩形。 */
    public static void stroke(Canvas canvas, float x, float y, float w, float h, float radius, int color, float alpha, float width) {
        STROKE.setStrokeWidth(width);
        STROKE.setColor(withAlpha(color, alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, radius), STROKE);
    }

    /**
     * 高光内描边：贴在内侧 1px 的浅色边，是玻璃质感的关键。
     *
     * @param strength 高光强度，苹果材质参考值约 0.25
     */
    public static void rim(Canvas canvas, float x, float y, float w, float h, float radius, int rimColor, float alpha, float strength) {
        if (strength <= 0.001f) return;
        stroke(canvas, x + 0.5f, y + 0.5f, w - 1f, h - 1f, Math.max(0f, radius - 0.5f), rimColor, alpha * strength, 1f);
    }

    /**
     * 键盘/输入焦点环：贴边 1.5px 的强调色描边，圆角与元素保持一致。
     *
     * <p>过渡系数由调用方按时间插值后传入 alpha。</p>
     */
    public static void focusRing(Canvas canvas, float x, float y, float w, float h, float radius, int color, float alpha) {
        if (alpha <= 0.01f) return;
        float width = 1.5f;
        float inset = width * 0.5f;
        stroke(canvas, x + inset, y + inset, w - width, h - width, Math.max(0f, radius - inset), color, alpha, width);
    }

    /** 向下的柔和投影，由多层渐弱的圆角描边近似。 */
    public static void shadow(Canvas canvas, float x, float y, float w, float h, float radius, int shadowColor, float alpha, float strength) {
        if (strength <= 0.001f || alpha <= 0.01f) return;
        for (int layer = SHADOW_LAYERS; layer >= 1; layer--) {
            float spread = layer * SHADOW_SPREAD;
            float layerAlpha = alpha * strength * 0.20f / layer;
            stroke(canvas,
                    x - spread, y - spread + layer * 0.9f,
                    w + spread * 2f, h + spread * 2f,
                    radius + spread,
                    shadowColor, layerAlpha, spread * 1.15f);
        }
    }

    /** 1px 分隔线。 */
    public static void divider(Canvas canvas, float x, float y, float w, int color, float alpha) {
        FILL.setColor(withAlpha(color, alpha));
        canvas.drawRect(io.github.humbleui.types.Rect.makeXYWH(x, y, w, 1f), FILL);
    }

    /**
     * 紧凑行圆角：以主题模块圆角为上限，并按行高收窄，避免矮行被圆角削成胶囊。
     *
     * <p>圆角同样只由主题决定，行高只做上限收窄，调用方不传具体数值。</p>
     */
    public static float rowRadius(float height) {
        return Math.min(ClickGuiThemeManager.current().metrics().moduleRadius(), Math.max(0f, height) * 0.30f);
    }

    /** 给 RGB 颜色叠加不透明度。 */
    public static int withAlpha(int color, float alpha) {
        int a = Math.round(Math.max(0f, Math.min(1f, alpha)) * 255f);
        return (a << 24) | (color & 0x00FFFFFF);
    }

    /** 仅混合 RGB 通道，用于悬停态与渐变的颜色过渡。 */
    public static int mix(int from, int to, float t) {
        float k = Math.max(0f, Math.min(1f, t));
        int fr = (from >> 16) & 0xFF, fg = (from >> 8) & 0xFF, fb = from & 0xFF;
        int tr = (to >> 16) & 0xFF, tg = (to >> 8) & 0xFF, tb = to & 0xFF;
        int r = Math.round(fr + (tr - fr) * k);
        int g = Math.round(fg + (tg - fg) * k);
        int b = Math.round(fb + (tb - fb) * k);
        return (r << 16) | (g << 8) | b;
    }
}
