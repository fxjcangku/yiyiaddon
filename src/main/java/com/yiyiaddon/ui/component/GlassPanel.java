package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
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

    /** 投影圈数：笔宽 = 扩散距离 / 圈数，圈数越多过渡越平滑、开销随圈数线性增长。 */
    private static final int SHADOW_LAYERS = 10;
    /** 投影最大扩散距离。 */
    private static final float SHADOW_REACH = 10f;
    /** 投影向下偏移占扩散距离的比例：让投影呈「向下」而非四周均匀。 */
    private static final float SHADOW_DROP_RATIO = 0.35f;

    /** 霜化渐变：顶部白色约 9% 不透明度，底部约 3%，这是「玻璃」而非「色块」的关键。 */
    private static final float FROST_TOP_ALPHA = 0.15f;
    private static final float FROST_BOTTOM_ALPHA = 0.025f;

    /** 不透明度量化档数：用于复用渐变着色器，避免每帧新建原生对象。 */
    private static final int ALPHA_BUCKETS = 8;
    private static final int SHADER_CACHE_LIMIT = 256;

    private static final Map<Long, Shader> FROST_SHADERS = new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Shader> eldest) {
            if (size() <= SHADER_CACHE_LIMIT) return false;
            eldest.getValue().close();
            return true;
        }
    };

    /** 环境光与交互高光独立缓存，避免与玻璃边缘材质共享键空间。 */
    private static final Map<Long, Shader> LIGHT_SHADERS = new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Shader> eldest) {
            if (size() <= SHADER_CACHE_LIMIT) return false;
            eldest.getValue().close();
            return true;
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
        if (alpha <= 0.01f || w <= 0f || h <= 0f) return;
        FILL.setShader(null);
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        // 所有主题共享透光规律；浅主题多留乳白遮罩维持深色文字对比，暗主题保留更多场景色。
        float density = ClickGuiThemeColors.panelBackgroundAlpha(1f) < 1f
                ? (tc.dark ? 0.78f : 0.90f) : 1f;
        FILL.setColor(withAlpha(baseColor, baseAlphaFraction * density * alpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, radius), FILL);

        // 局部坐标使滚动与窗口移动复用同一材质；透明度交给画刷连续合成，避免分档闪烁。
        canvas.save();
        try {
            canvas.translate(x, y);
            FILL.setColor(withAlpha(ClickGuiThemeColors.current().rim, alpha));
            FILL.setShader(frostShader(0f, h, 1f));
            canvas.drawRRect(RRect.makeXYWH(0f, 0f, w, h, radius), FILL);
        } finally {
            FILL.setShader(null);
            canvas.restore();
        }
        // 细内阴影压住下缘，让叠层表面具有厚度，不增加背景采样或模糊通道。
        stroke(canvas, x + 1f, y + 1.5f, Math.max(0f, w - 2f), Math.max(0f, h - 3f),
                Math.max(0f, radius - 1f), ClickGuiThemeColors.current().shadow, alpha * 0.075f, 0.75f);
    }

    /** 按矩形纵向范围与不透明度档位缓存线性渐变着色器。 */
    private static Shader frostShader(float top, float bottom, float alpha) {
        int bucket = Math.round(Math.max(0f, Math.min(1f, alpha)) * ALPHA_BUCKETS);
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        long key = ((long) Math.round(bottom - top) << 32) | (tc.accent & 0xFFFFFFL)
                | (tc.dark ? 1L << 24 : 0L);
        return FROST_SHADERS.computeIfAbsent(key, k -> {
            float scale = bucket / (float) ALPHA_BUCKETS;
            // 顶部乳光、中央清透、底部淡淡的主题色反光；不使用固定灰色蒙版。
            return Shader.makeLinearGradient(0f, top, 0f, bottom, new int[]{
                    withAlpha(tc.rim, (tc.dark ? 0.12f : 0.28f) * scale),
                    withAlpha(tc.rim, 0.012f * scale),
                    withAlpha(mix(tc.rim, tc.accent, 0.24f), (tc.dark ? 0.055f : 0.12f) * scale)});
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
        if (w <= 2f || h <= 2f) return;
        // 整圈弱轮廓和斜向连续反光共同定义玻璃厚度，避免截断式上沿白线。
        stroke(canvas, x + 0.5f, y + 0.5f, w - 1f, h - 1f, Math.max(0f, radius - 0.5f), rimColor, alpha * strength * 0.45f, 1f);
        canvas.save();
        try {
            canvas.translate(x, y);
            STROKE.setColor(withAlpha(rimColor, alpha * Math.min(1f, strength * 2.1f)));
            // 对实际尺寸生成的着色器通过缓存复用，滚动不会分配新材质。
            STROKE.setShader(edgeShader(w, h));
            STROKE.setStrokeWidth(1f);
            canvas.drawRRect(RRect.makeXYWH(0.5f, 0.5f, w - 1f, h - 1f, Math.max(0f, radius - 0.5f)), STROKE);
        } finally {
            STROKE.setShader(null);
            canvas.restore();
        }
    }

    /** 斜向反光按几何缓存，颜色随主题切换即时失效，缓存淘汰释放原生资源。 */
    private static Shader edgeShader(float width, float height) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        long key = Long.MIN_VALUE | ((long) Math.round(width) << 40)
                | ((long) Math.round(height) << 24) | tc.accent;
        return FROST_SHADERS.computeIfAbsent(key, k -> Shader.makeLinearGradient(0f, 0f, width, height,
                new int[]{withAlpha(tc.rim, 0.95f), withAlpha(tc.rim, 0.08f),
                        withAlpha(mix(tc.rim, tc.accent, 0.20f), 0.62f)}));
    }

    /**
     * 主题环境光：用两束低透明径向光把平面玻璃分成受光面与背光面。
     *
     * <p>环境光只在调用方已经建立的圆角裁剪内绘制，不改变控件几何，也不会遮住文字。</p>
     */
    public static void ambientGlow(Canvas canvas, float x, float y, float w, float h,
                                   ClickGuiThemeColors tc, float alpha, float strength) {
        if (alpha <= 0.01f || strength <= 0.001f || w <= 0f || h <= 0f) return;
        canvas.save();
        try {
            canvas.translate(x, y);
            FILL.setColor(withAlpha(tc.rim, alpha * strength));
            FILL.setShader(lightShader(1, w, h, tc));
            canvas.drawRect(io.github.humbleui.types.Rect.makeXYWH(0f, 0f, w, h), FILL);
            FILL.setShader(lightShader(2, w, h, tc));
            canvas.drawRect(io.github.humbleui.types.Rect.makeXYWH(0f, 0f, w, h), FILL);
        } finally {
            FILL.setShader(null);
            canvas.restore();
        }
    }

    /** 强调色玻璃胶囊：用于选中态，使导航层级在所有主题下都足够醒目。 */
    public static void accentPill(Canvas canvas, float x, float y, float w, float h, float radius,
                                  ClickGuiThemeColors tc, float alpha) {
        if (alpha <= 0.01f || w <= 0f || h <= 0f) return;
        shadow(canvas, x, y, w, h, radius, tc.accent, alpha, 0.32f);
        canvas.save();
        try {
            canvas.translate(x, y);
            FILL.setColor(withAlpha(tc.rim, alpha));
            FILL.setShader(lightShader(3, w, h, tc));
            canvas.drawRRect(RRect.makeXYWH(0f, 0f, w, h, radius), FILL);
        } finally {
            FILL.setShader(null);
            canvas.restore();
        }
        rim(canvas, x, y, w, h, radius, tc.rim, alpha, tc.dark ? 0.30f : 0.48f);
    }

    /** 输入框底色系数：与同页的行同源（行是 0.70），低一档，使输入区不会比旁边的行抢眼。 */
    private static final float FIELD_TINT = 0.55f;

    /** 输入框聚焦时抬升的底色系数：只是极轻的重心变化，聚焦提示仍由光标与焦点环表达。 */
    private static final float FIELD_FOCUS_LIFT = 0.15f;

    /**
     * 输入框（搜索框 / 文本输入）的统一底：与同页的行共用一套玻璃语言（frost + 1px 高光内描边）。
     *
     * <p>用户 2026-09-16 原话「点击搜索的框 太深了 看见了吗 像一块东西粘住在那里一样 突兀」：
     * 输入框原来是实心填色（{@code searchBackground}），乘过 {@code panelBackgroundAlpha} 后仍是一块实心，
     * 且比同页的行更暗，夹在霜化玻璃行之间就是一块贴上去的深色板。此处是输入框底的唯一定义，
     * {@code SettingTextBox} 与主面板左侧搜索框都取这一份——两处各写一套必然会再次走样。</p>
     */
    public static void textField(Canvas canvas, float x, float y, float w, float h, float radius,
                                 ClickGuiThemeColors tc, float focusAlpha, float alpha) {
        if (alpha <= 0.01f || w <= 0f || h <= 0f) return;
        frost(canvas, x, y, w, h, radius, tc.module,
                FIELD_TINT + FIELD_FOCUS_LIFT * focusAlpha,
                ClickGuiThemeColors.panelBackgroundAlpha(alpha));
        rim(canvas, x, y, w, h, radius, tc.rim, alpha, 0.10f);
    }

    /** 悬停高光：在卡片上形成克制的斜向反射，静止时完全不绘制。 */
    public static void sheen(Canvas canvas, float x, float y, float w, float h, float radius,
                             ClickGuiThemeColors tc, float alpha, float hover) {
        if (hover <= 0.01f || alpha <= 0.01f) return;
        canvas.save();
        try {
            canvas.clipRRect(RRect.makeXYWH(x, y, w, h, radius), true);
            canvas.translate(x, y);
            FILL.setColor(withAlpha(tc.rim, alpha * hover));
            FILL.setShader(lightShader(4, w, h, tc));
            canvas.drawRect(io.github.humbleui.types.Rect.makeXYWH(0f, 0f, w, h), FILL);
        } finally {
            FILL.setShader(null);
            canvas.restore();
        }
    }

    /** 根据用途、几何与主题生成稳定缓存键，主题切换后不会复用旧配色。 */
    private static Shader lightShader(int kind, float width, float height, ClickGuiThemeColors tc) {
        int w = Math.round(width);
        int h = Math.round(height);
        long key = 1469598103934665603L;
        key = (key ^ kind) * 1099511628211L;
        key = (key ^ w) * 1099511628211L;
        key = (key ^ h) * 1099511628211L;
        key = (key ^ tc.accent) * 1099511628211L;
        key = (key ^ (tc.dark ? 1 : 0)) * 1099511628211L;
        return LIGHT_SHADERS.computeIfAbsent(key, ignored -> switch (kind) {
            case 1 -> Shader.makeRadialGradient(width * 0.08f, height * 0.02f,
                    Math.max(width, height) * 0.72f,
                    new int[]{withAlpha(mix(tc.rim, tc.accent, 0.30f), tc.dark ? 0.24f : 0.18f),
                            withAlpha(tc.accent, tc.dark ? 0.075f : 0.045f), withAlpha(tc.accent, 0f)},
                    new float[]{0f, 0.42f, 1f});
            case 2 -> Shader.makeRadialGradient(width * 0.96f, height * 0.98f,
                    Math.max(width, height) * 0.62f,
                    new int[]{withAlpha(tc.accent, tc.dark ? 0.14f : 0.08f),
                            withAlpha(tc.accent, 0.025f), withAlpha(tc.accent, 0f)},
                    new float[]{0f, 0.48f, 1f});
            case 3 -> Shader.makeLinearGradient(0f, 0f, width, height,
                    new int[]{withAlpha(mix(tc.accent, tc.rim, tc.dark ? 0.32f : 0.16f), 1f),
                            withAlpha(tc.accent, 1f),
                            withAlpha(mix(tc.accent, tc.shadow, tc.dark ? 0.10f : 0.04f), 1f)});
            default -> Shader.makeLinearGradient(-width * 0.15f, height, width * 0.75f, 0f,
                    new int[]{withAlpha(tc.rim, 0f), withAlpha(tc.rim, tc.dark ? 0.12f : 0.20f),
                            withAlpha(mix(tc.rim, tc.accent, 0.25f), 0.035f), withAlpha(tc.rim, 0f)},
                    new float[]{0f, 0.42f, 0.58f, 1f});
        });
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

    /**
     * 向下的柔和投影，由多圈互不重叠的圆角描边拼成。
     *
     * <p><b>为什么必须互不重叠：</b>早期写法用「层数 × 较大笔宽」，环带彼此压在一起，
     * 同一像素被 3~4 圈重复叠加，且外缘是最后一圈的硬边——实机看起来就是一圈发黑的
     * 重影（用户反馈「很多黑色的重影」）。这里改成：把扩散距离等分成 {@link #SHADOW_LAYERS}
     * 圈，每圈笔宽正好等于圈距，于是每个像素只被一圈覆盖；透明度沿半径按平方从贴边处
     * 向外衰减到 0，得到单调、无硬边的过渡。</p>
     *
     * @param strength 相对强度；贴边处最深处约为 {@code strength * 0.35}
     */
    public static void shadow(Canvas canvas, float x, float y, float w, float h, float radius, int shadowColor, float alpha, float strength) {
        if (strength <= 0.001f || alpha <= 0.01f) return;
        float step = SHADOW_REACH / SHADOW_LAYERS;
        float edgeAlpha = alpha * strength * 0.35f;
        for (int layer = 0; layer < SHADOW_LAYERS; layer++) {
            // u：0 = 最外圈，1 = 贴边圈；平方衰减让投影只贴着形状存在
            float u = (layer + 0.5f) / SHADOW_LAYERS;
            float spread = SHADOW_REACH * (1f - u);
            stroke(canvas,
                    x - spread, y - spread + spread * SHADOW_DROP_RATIO,
                    w + spread * 2f, h + spread * 2f,
                    radius + spread,
                    shadowColor, edgeAlpha * u * u, step);
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
