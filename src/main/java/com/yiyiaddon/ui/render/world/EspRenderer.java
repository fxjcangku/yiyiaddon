package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.TextureImageCache;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.gizmos.GizmoProperties;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 世界空间 ESP 绘制器，分两个阶段工作：
 *
 * <ol>
 *   <li><b>几何阶段</b>（原版收集 gizmo 期间，由 {@link WorldOverlay#collectGeometry()} 驱动）：
 *       框 / 线 / 面交给原版 {@link Gizmos} 在世界空间直接由 GPU 绘制——顶点由 GPU 与世界一起变换，
 *       因此走动时框严丝合缝贴住方块，不存在「屏幕空间描边的亚像素颤动」与「相对世界滞后」。</li>
 *   <li><b>叠加阶段</b>（{@code GuiRenderer.render} 之前，由 {@link WorldOverlay#renderOverlay()} 驱动）：
 *       只画必须走 2D 的元素——字牌（要字体与底板）、屏幕包围框、屏幕起点射线。几何阶段把它们
 *       记进 {@link Deferred} 队列，本阶段再由 Skija 画到主 Framebuffer 上。</li>
 * </ol>
 *
 * <p><b>为什么不再用「投影到屏幕 + Skija 描边」画几何：</b>那条路每帧要开一次 Skija 画布并保存 /
 * 恢复约百项 GL 状态，还要在屏幕空间栅格化细线——实机表现就是走动时框抖动、掉帧。</p>
 *
 * <p>透视语义：默认「始终画在最上层」（原版忽略深度的实现，会先清深度再画），
 * {@link #occlusion(boolean)} 打开后改为交给深度缓冲自然遮挡。二者都是像素级精确。</p>
 *
 * <p>线宽口径：本类的 {@code thickness} 参数与旧实现一致，是 <b>GUI 缩放坐标</b>；
 * 原版 gizmo 的线宽是物理像素（顶点着色器里按 {@code LineWidth / ScreenSize} 展开，与距离无关），
 * 因此这里统一乘 GUI 缩放换算，观感与旧实现一致。</p>
 */
public final class EspRenderer {

    /** 屏幕外扩边距：略超出屏幕的图形仍值得绘制（例如只露出一角的框）。 */
    private static final float OFFSCREEN_MARGIN = 64f;

    /** 渐变（线、渐变面）的分段数：原版 gizmo 不支持逐点异色，只能分段近似。 */
    private static final int GRADIENT_SEGMENTS = 10;

    /** 颜色对象为 null 时的兜底：线框取白、填充取全透明。调用方应尽量不传 null。 */
    private static final int FALLBACK_LINE = 0xFFFFFFFF;
    private static final int FALLBACK_SIDE = 0x00000000;

    /** 未设「最远显示距离」但要淡出时的默认终点（格）：不限距离总不能淡到无穷远。 */
    private static final float DEFAULT_FADE_END = 64f;

    /**
     * 字牌图标的尺寸与间距（都按字号的比例给）。
     *
     * <p>图标按字号 1.6 倍画：原版物品贴图是 16×16，缩到字号大小时细节全糊，1.6 倍既能看清
     * 又不会把字牌撑得比区域框还宽；间距取 0.4 倍，视觉上图标与首字是一组。</p>
     */
    private static final float ICON_SCALE = 1.6f;
    private static final float ICON_GAP_SCALE = 0.4f;

    /** 叠加阶段的共享画笔。Skija 的 Paint 是原生资源，复用可避免逐帧创建与回收。 */
    private static final Paint STROKE = new Paint().setAntiAlias(true)
            .setMode(PaintMode.STROKE)
            .setStrokeCap(PaintStrokeCap.ROUND);
    private static final Paint FILL = new Paint().setAntiAlias(true).setMode(PaintMode.FILL);
    /** 字牌底板画笔：字后面压一块深色底，亮背景下文字才不糊（旧项目 LABEL_BG 的口径） */
    private static final Paint PLATE = new Paint().setAntiAlias(true).setMode(PaintMode.FILL);

    private final Canvas canvas;
    private final WorldProjector projector;
    private final RenderCamera camera;
    /** 本帧待画的 2D 元素；几何阶段写入，叠加阶段读取。叠加阶段的实例为 {@code null}。 */
    private final List<Deferred> deferred;
    /** ESP 全局设置（线宽 / 透明度 / 距离 / 字号 / 图元预算）；所有绘制层共用一份。 */
    private final EspGlobalSettings globals = EspGlobalSettings.get();
    private final ScreenPoint[] corners = new ScreenPoint[8];

    private boolean occlusion;
    /** 本帧已画图元数，配合全局预算使用；本对象每帧新建，因此天然在帧首归零。 */
    private int primitives;
    /** 当前图元的距离淡出系数（1 = 不淡出，0 = 该距离外不画）；由每个绘制入口设置。 */
    private float fade = 1f;
    /** 「只填面 / 只描边」的专用图形正在绘制：此刻不接受全局渲染模式覆盖。 */
    private boolean modeLocked;

    private EspRenderer(Canvas canvas, WorldProjector projector, RenderCamera camera, List<Deferred> deferred) {
        this.canvas = canvas;
        this.projector = projector;
        this.camera = camera;
        this.deferred = deferred;
    }

    /**
     * 几何阶段实例：几何直接交给原版 Gizmos（世界空间 GPU 绘制），2D 元素写进 {@code deferred}。
     *
     * <p>本实例必须在原版收集 gizmo 期间（{@code LevelRenderEvents.BEFORE_GIZMOS}）使用：
     * 原版 {@code Gizmos.addGizmo} 在没有收集器时会直接抛异常。</p>
     */
    public static EspRenderer geometry(RenderCamera camera, List<Deferred> deferred) {
        return new EspRenderer(null, null, camera, deferred);
    }

    /** 叠加阶段实例：只把 {@code deferred} 里的 2D 元素画到 Skija 画布上。 */
    public static EspRenderer overlay(Canvas canvas, WorldProjector projector, RenderCamera camera) {
        return new EspRenderer(canvas, projector, camera, null);
    }

    // ── 延迟绘制元素（几何阶段收集，叠加阶段绘制） ─────────────────────────────

    /** 几何阶段记下、留到 2D 叠加阶段绘制的元素。 */
    public sealed interface Deferred permits DeferredText, DeferredBox2D, DeferredTracer {
    }

    /**
     * 世界坐标处的字牌：字体与底板都只能由 Skija 画，因此留到叠加阶段。
     *
     * @param icon 字牌左侧的资源包贴图（{@code null} = 不带图标的纯文字）
     */
    public record DeferredText(String text, Identifier icon, double x, double y, double z, float size, int color,
                               float alpha, boolean shadow, float fade) implements Deferred {
    }

    /** 屏幕轴对齐包围框：屏幕坐标本身就是投影结果，只能在叠加阶段算。 */
    public record DeferredBox2D(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness,
                                float fade) implements Deferred {
    }

    /** 屏幕起点射线：起点是 GUI 缩放坐标（{@code NaN} 表示「屏幕底部中心」），终点是世界坐标。 */
    public record DeferredTracer(float startX, float startY, double x, double y, double z, int color,
                                 float thickness, float fade) implements Deferred {
    }

    // ── 全局设置 ────────────────────────────────────────────────────────────

    /** 统一上色：颜色自身的 alpha × 全局不透明度倍率 × 本图元的距离淡出系数。 */
    private int tint(int argb) {
        float scale = globals.alphaScale() * fade;
        return scale >= 0.999f ? argb : ClickGuiThemeColors.scaleAlpha(argb, Math.max(0f, scale));
    }

    /** 全局线宽倍率。 */
    private float stroke(float thickness) {
        return globals.thickness(thickness);
    }

    /**
     * GUI 缩放坐标口径的线宽 → 原版 gizmo 的物理像素线宽。
     *
     * <p>{@code rendertype_lines} 顶点着色器按 {@code LineWidth / ScreenSize} 在屏幕空间展开线条，
     * 所以这个宽度与距离无关、就是物理像素；旧实现在 GUI 缩放的画布上描边，同一视觉宽度
     * 对应的物理像素数正是「GUI 线宽 × GUI 缩放」。</p>
     */
    private static float pixelWidth(float thickness) {
        float scaled = thickness * (float) Minecraft.getInstance().getWindow().getGuiScale();
        return scaled <= 0f ? 0f : scaled;
    }

    /** 全局渲染模式覆盖；「只填面 / 只描边」的专用图形不受影响。 */
    private ShapeMode resolved(ShapeMode mode) {
        return modeLocked ? mode : globals.mode(mode);
    }

    /** 实际生效的遮挡口径（全局覆盖优先于模块自己设的）。 */
    private boolean effectiveOcclusion() {
        return globals.occlusion(occlusion);
    }

    /**
     * 该世界点的距离系数：超出「最远显示距离」返回 0，开启淡出时从「淡出起点」线性淡出到终点。
     *
     * <p>默认两项都没开，直接返回 1 —— 不产生任何距离计算。</p>
     */
    private float fadeAt(double x, double y, double z) {
        int max = globals.maxDistance();
        boolean fading = globals.fade();
        if (max <= 0 && !fading) return 1f;
        Vec3 eye = camera.position();
        double dx = x - eye.x;
        double dy = y - eye.y;
        double dz = z - eye.z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (max > 0 && distance > max) return 0f;
        if (!fading) return 1f;
        float end = max > 0 ? max : DEFAULT_FADE_END;
        float start = Math.min(globals.fadeStart(), Math.max(0f, end - 1f));
        if (distance <= start) return 1f;
        return Math.max(0f, 1f - (float) ((distance - start) / Math.max(1e-3, end - start)));
    }

    private float fadeAt(Vec3 pos) {
        return fadeAt(pos.x, pos.y, pos.z);
    }

    /** 单帧图元预算：超出后不再画新图元，返回 false。0 = 不限（默认）。 */
    private boolean affordable() {
        int budget = globals.primitiveBudget();
        if (budget > 0 && primitives >= budget) return false;
        primitives++;
        return true;
    }

    /** 把整盒 / 整面交给原版 gizmo；透视时要求「始终画在最上层」，遮挡时交给深度缓冲。 */
    private void submit(GizmoProperties properties) {
        if (!effectiveOcclusion()) properties.setAlwaysOnTop();
    }

    // ── 遮挡模式 ────────────────────────────────────────────────────────────

    /**
     * 是否启用「被方块挡住就不画」。
     *
     * <p>默认关闭（透视）。几何由深度缓冲判定（像素级精确）；字牌一类的 2D 元素仍走体素射线近似。</p>
     */
    public EspRenderer occlusion(boolean enabled) {
        this.occlusion = enabled;
        return this;
    }

    public boolean occlusion() {
        return effectiveOcclusion();
    }

    public float screenWidth() {
        return projector != null
                ? projector.screenWidth()
                : Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    public float screenHeight() {
        return projector != null
                ? projector.screenHeight()
                : Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }

    /** 投影单点，供调用方自行判断可见性；几何阶段没有投影器，返回不可见点。 */
    public ScreenPoint project(double x, double y, double z) {
        return projector == null ? ScreenPoint.invisible() : projector.project(x, y, z);
    }

    public ScreenPoint project(Vec3 pos) {
        return project(pos.x, pos.y, pos.z);
    }

    /** 该世界点当前是否应该绘制（屏幕内 + 未被遮挡 + 未超出最远显示距离）。 */
    public boolean shouldDraw(Vec3 pos) {
        if (pos == null) return false;
        if (fadeAt(pos) <= 0f) return false;
        if (projector == null) return true;
        ScreenPoint point = projector.project(pos.x, pos.y, pos.z);
        if (!visibleWithinScreen(point)) return false;
        return !effectiveOcclusion() || !Visibility.blocked(camera.position(), pos);
    }

    // ── 线条 ────────────────────────────────────────────────────────────────

    /** 画一条世界空间线段；近平面裁剪由 GPU 完成，两端都在相机后时自然不可见。 */
    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     int color, float thickness) {
        float width = pixelWidth(stroke(thickness));
        if (width <= 0f) return;
        fade = fadeAt((x1 + x2) * 0.5d, (y1 + y2) * 0.5d, (z1 + z2) * 0.5d);
        if (fade <= 0f || !affordable()) return;
        submit(Gizmos.line(new Vec3(x1, y1, z1), new Vec3(x2, y2, z2), tint(color), width));
    }

    public void line(Vec3 from, Vec3 to, int color, float thickness) {
        line(from.x, from.y, from.z, to.x, to.y, to.z, color, thickness);
    }

    /**
     * 双色渐变线：起点 {@code color1}、终点 {@code color2}。
     *
     * <p>原版线段只能一个颜色，这里按 {@link #GRADIENT_SEGMENTS} 段插值近似。</p>
     */
    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     int color1, int color2, float thickness) {
        float width = pixelWidth(stroke(thickness));
        if (width <= 0f) return;
        fade = fadeAt((x1 + x2) * 0.5d, (y1 + y2) * 0.5d, (z1 + z2) * 0.5d);
        if (fade <= 0f || !affordable()) return;

        for (int i = 0; i < GRADIENT_SEGMENTS; i++) {
            float t0 = (float) i / GRADIENT_SEGMENTS;
            float t1 = (float) (i + 1) / GRADIENT_SEGMENTS;
            submit(Gizmos.line(
                    new Vec3(lerp(x1, x2, t0), lerp(y1, y2, t0), lerp(z1, z2, t0)),
                    new Vec3(lerp(x1, x2, t1), lerp(y1, y2, t1), lerp(z1, z2, t1)),
                    tint(lerpColor(color1, color2, (t0 + t1) * 0.5f)), width));
        }
    }

    public void line(Vec3 from, Vec3 to, int color1, int color2, float thickness) {
        line(from.x, from.y, from.z, to.x, to.y, to.z, color1, color2, thickness);
    }

    // ── 方框 ────────────────────────────────────────────────────────────────

    /**
     * 3D 线框盒：几何原样交给原版 gizmo，由 GPU 在世界空间绘制（近大远小、随世界一同变换）。
     *
     * <p>参数顺序固定为<b>先填充色、后描边色</b>：这两个都是整数 ARGB，传反不会报错，只会画错。</p>
     */
    public void box(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        box(box, sideColor, lineColor, mode, thickness, 0);
    }

    /**
     * 3D 线框盒。
     *
     * @param excludeDir 被排除的面（{@link Dir} 的位或）；原版 gizmo 没有面剔除能力，
     *                   而业务调用一律传 {@code 0}，故该参数不再生效（保留签名以免调用方改动）
     */
    public void box(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness, int excludeDir) {
        mode = resolved(mode);
        float width = pixelWidth(stroke(thickness));
        Vec3 center = box.getCenter();
        fade = fadeAt(center);
        if (fade <= 0f || !affordable()) return;

        boolean lines = mode.lines() && width > 0f;
        boolean sides = mode.sides();
        if (!lines && !sides) return;

        GizmoStyle style;
        if (lines && sides) {
            style = GizmoStyle.strokeAndFill(tint(lineColor), width, tint(sideColor));
        } else if (lines) {
            style = GizmoStyle.stroke(tint(lineColor), width);
        } else {
            style = GizmoStyle.fill(tint(sideColor));
        }
        submit(Gizmos.cuboid(box, style));
    }

    /**
     * 屏幕包围矩形框：把 8 个角投影后取屏幕轴对齐包围盒。
     *
     * <p>与 {@link #box} 的区别是观感——这里始终是正对屏幕的矩形，适合方块的平面高亮。
     * 屏幕坐标本身就是投影产物，因此只能留到叠加阶段绘制。</p>
     */
    public void box2D(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        mode = resolved(mode);
        float width = stroke(thickness);
        Vec3 center = box.getCenter();
        fade = fadeAt(center);
        if (fade <= 0f || !affordable()) return;
        if (deferred == null) return;
        deferred.add(new DeferredBox2D(box, sideColor, lineColor, mode, width, fade));
    }

    public void blockBox(int x, int y, int z, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        blockBox(x, y, z, sideColor, lineColor, mode, thickness, 0);
    }

    public void blockBox(int x, int y, int z, int sideColor, int lineColor, ShapeMode mode,
                         float thickness, int excludeDir) {
        box(new AABB(x, y, z, x + 1, y + 1, z + 1), sideColor, lineColor, mode, thickness, excludeDir);
    }

    /**
     * 只填面、不描边；成片范围（例如整片农田）用它避免内部棱线堆积。
     *
     * <p>这类专用图形锁定自己的模式：全局「渲染模式覆盖」若把只填面改成线框 / 两者，
     * 描边色是 0（全透明）——结果是整片范围直接看不见。</p>
     */
    public void boxSides(AABB box, int color, int excludeDir) {
        modeLocked = true;
        try {
            box(box, color, 0, ShapeMode.Sides, 0f, excludeDir);
        } finally {
            modeLocked = false;
        }
    }

    public void blockSides(int x, int y, int z, int color, int excludeDir) {
        boxSides(new AABB(x, y, z, x + 1, y + 1, z + 1), color, excludeDir);
    }

    // ── 颜色对象重载 ──────────────────────────────────────────────────────────
    // 模块持有 EspColor（可能开启彩虹），绘制瞬间解析为 ARGB，模块无需关心变色时机。

    public void box(AABB box, EspColor side, EspColor line, ShapeMode mode, float thickness) {
        box(box, side, line, mode, thickness, 0);
    }

    public void box(AABB box, EspColor side, EspColor line, ShapeMode mode, float thickness, int excludeDir) {
        box(box, argbOf(side, FALLBACK_SIDE), argbOf(line, FALLBACK_LINE), mode, thickness, excludeDir);
    }

    public void box2D(AABB box, EspColor side, EspColor line, ShapeMode mode, float thickness) {
        box2D(box, argbOf(side, FALLBACK_SIDE), argbOf(line, FALLBACK_LINE), mode, thickness);
    }

    public void blockBox(int x, int y, int z, EspColor side, EspColor line, ShapeMode mode, float thickness) {
        blockBox(x, y, z, side, line, mode, thickness, 0);
    }

    public void blockBox(int x, int y, int z, EspColor side, EspColor line, ShapeMode mode,
                         float thickness, int excludeDir) {
        box(new AABB(x, y, z, x + 1, y + 1, z + 1), side, line, mode, thickness, excludeDir);
    }

    /** 只填面、不描边（颜色对象版）。 */
    public void boxSides(AABB box, EspColor color, int excludeDir) {
        boxSides(box, argbOf(color, FALLBACK_SIDE), excludeDir);
    }

    public void blockSides(int x, int y, int z, EspColor color, int excludeDir) {
        blockSides(x, y, z, argbOf(color, FALLBACK_SIDE), excludeDir);
    }

    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     EspColor color, float thickness) {
        line(x1, y1, z1, x2, y2, z2, argbOf(color, FALLBACK_LINE), thickness);
    }

    public void line(Vec3 from, Vec3 to, EspColor color, float thickness) {
        line(from.x, from.y, from.z, to.x, to.y, to.z, color, thickness);
    }

    public void tracer(Vec3 target, EspColor color, float thickness) {
        tracer(target.x, target.y, target.z, argbOf(color, FALLBACK_LINE), thickness);
    }

    public void tracerFrom(float startX, float startY, double x, double y, double z,
                           EspColor color, float thickness) {
        tracerFrom(startX, startY, x, y, z, argbOf(color, FALLBACK_LINE), thickness);
    }

    public boolean text(String text, double x, double y, double z, float size, EspColor color) {
        return text(text, x, y, z, size, color, 1f, false);
    }

    public boolean text(String text, double x, double y, double z, float size, EspColor color,
                        float alpha, boolean shadow) {
        if (color == null) return false;
        return text(text, x, y, z, size, color.currentRgb(), alpha, shadow);
    }

    private static int argbOf(EspColor color, int fallback) {
        return color == null ? fallback : color.argb();
    }

    // ── 面 ──────────────────────────────────────────────────────────────────

    /** 任意四点构成的多边形填充（世界坐标，需自行保证四点共面且环绕顺序正确）。 */
    public void quad(double x1, double y1, double z1, double x2, double y2, double z2,
                     double x3, double y3, double z3, double x4, double y4, double z4, int color) {
        fade = fadeAt((x1 + x2 + x3 + x4) * 0.25d, (y1 + y2 + y3 + y4) * 0.25d, (z1 + z2 + z3 + z4) * 0.25d);
        if (fade <= 0f || !affordable()) return;
        submit(Gizmos.rect(new Vec3(x1, y1, z1), new Vec3(x2, y2, z2), new Vec3(x3, y3, z3),
                new Vec3(x4, y4, z4), GizmoStyle.fill(tint(color))));
    }

    /**
     * 竖直面：沿对角线 (x1,z1)→(x2,z2) 竖直展开，从 {@code y1} 到 {@code y2}。
     *
     * <p>面颜色与描边颜色分开，描边是否绘制由 {@code mode} 决定。</p>
     */
    public void sideVertical(double x1, double y1, double z1, double x2, double y2, double z2,
                             int sideColor, int lineColor, ShapeMode mode, float thickness) {
        side(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, sideColor, lineColor, mode, thickness);
    }

    /** 水平面：{@code y} 高度上的矩形 (x1,z1)→(x2,z2)，最常用于地面范围显示。 */
    public void sideHorizontal(double x1, double y, double z1, double x2, double z2,
                               int sideColor, int lineColor, ShapeMode mode, float thickness) {
        side(x1, y, z1, x1, y, z2, x2, y, z2, x2, y, z1, sideColor, lineColor, mode, thickness);
    }

    /** 任意四边形，按 {@code mode} 决定填充与描边。 */
    public void side(double x1, double y1, double z1, double x2, double y2, double z2,
                     double x3, double y3, double z3, double x4, double y4, double z4,
                     int sideColor, int lineColor, ShapeMode mode, float thickness) {
        mode = resolved(mode);
        float width = pixelWidth(stroke(thickness));
        fade = fadeAt((x1 + x2 + x3 + x4) * 0.25d, (y1 + y2 + y3 + y4) * 0.25d, (z1 + z2 + z3 + z4) * 0.25d);
        if (fade <= 0f || !affordable()) return;

        boolean lines = mode.lines() && width > 0f;
        boolean sides = mode.sides();
        if (!lines && !sides) return;

        GizmoStyle style;
        if (lines && sides) {
            style = GizmoStyle.strokeAndFill(tint(lineColor), width, tint(sideColor));
        } else if (lines) {
            style = GizmoStyle.stroke(tint(lineColor), width);
        } else {
            style = GizmoStyle.fill(tint(sideColor));
        }
        submit(Gizmos.rect(new Vec3(x1, y1, z1), new Vec3(x2, y2, z2), new Vec3(x3, y3, z3),
                new Vec3(x4, y4, z4), style));
    }

    /** 竖直面单色填充（专用图形，锁定「只填面」）。 */
    public void quadVertical(double x1, double y1, double z1, double x2, double y2, double z2, int color) {
        modeLocked = true;
        try {
            sideVertical(x1, y1, z1, x2, y2, z2, color, 0, ShapeMode.Sides, 0f);
        } finally {
            modeLocked = false;
        }
    }

    /** 水平面单色填充（专用图形，锁定「只填面」）。 */
    public void quadHorizontal(double x1, double y, double z1, double x2, double z2, int color) {
        modeLocked = true;
        try {
            sideHorizontal(x1, y, z1, x2, z2, color, 0, ShapeMode.Sides, 0f);
        } finally {
            modeLocked = false;
        }
    }

    /**
     * 竖直渐变面：上边 {@code topColor}、下边 {@code bottomColor}。
     *
     * <p>原版面只能一个颜色，故沿竖直方向切成 {@link #GRADIENT_SEGMENTS} 段、每段单色填充，
     * 以段间跳变近似连续渐变。</p>
     */
    public void gradientQuadVertical(double x1, double y1, double z1, double x2, double y2, double z2,
                                     int topColor, int bottomColor) {
        fade = fadeAt((x1 + x2) * 0.5d, (y1 + y2) * 0.5d, (z1 + z2) * 0.5d);
        if (fade <= 0f || !affordable()) return;

        for (int i = 0; i < GRADIENT_SEGMENTS; i++) {
            float t0 = (float) i / GRADIENT_SEGMENTS;
            float t1 = (float) (i + 1) / GRADIENT_SEGMENTS;
            double lowY = lerp(y2, y1, t0);
            double highY = lerp(y2, y1, t1);
            int color = tint(lerpColor(topColor, bottomColor, (t0 + t1) * 0.5f));
            submit(Gizmos.rect(
                    new Vec3(x1, lowY, z1), new Vec3(x2, lowY, z2),
                    new Vec3(x2, highY, z2), new Vec3(x1, highY, z1),
                    GizmoStyle.fill(color)));
        }
    }

    // ── 射线 ────────────────────────────────────────────────────────────────

    /** 射线：从屏幕底部中心指向目标位置。 */
    public void tracer(double x, double y, double z, int color, float thickness) {
        tracerFrom(Float.NaN, Float.NaN, x, y, z, color, thickness);
    }

    public void tracer(Vec3 target, int color, float thickness) {
        tracer(target.x, target.y, target.z, color, thickness);
    }

    /** 指定屏幕起点的射线（例如从准星位置起画）。 */
    public void tracerFrom(float startX, float startY, double x, double y, double z,
                           int color, float thickness) {
        float width = stroke(thickness);
        if (width <= 0f) return;
        fade = fadeAt(x, y, z);
        if (fade <= 0f || !affordable()) return;
        if (deferred == null) return;
        deferred.add(new DeferredTracer(startX, startY, x, y, z, color, width, fade));
    }

    // ── 文字 ────────────────────────────────────────────────────────────────

    /** 在世界坐标处居中绘制文字，可用 {@code shadow} 叠加描边提升可读性。 */
    public boolean text(String text, double x, double y, double z, float size, int color) {
        return text(text, x, y, z, size, color, 1f, false);
    }

    /**
     * 在世界坐标处居中绘制文字（几何阶段入队，叠加阶段真正绘制）。
     *
     * <p>水平、垂直都居中到投影点：投影点既是文字块的水平中心，也是垂直中心
     * （{@link CardLayout#baseline} 的 0.46 偏移就是「中心 → 基线」的口径）。</p>
     *
     * @param size   字号（GUI 缩放坐标）；实际字号再乘全局「文字大小倍率」
     * @param alpha  整体透明度
     * @param shadow 是否画底板 + 一次偏移阴影（旧项目字牌带 LABEL_BG 底板，压底才锐利）；
     *               全局「文字底板」关掉时一律不画底板
     */
    public boolean text(String text, double x, double y, double z, float size, int color,
                        float alpha, boolean shadow) {
        return text(text, null, x, y, z, size, color, alpha, shadow);
    }

    /**
     * 带左侧图标的字牌：图标取自资源包贴图（{@code iconTexture} 为贴图 id，如
     * {@code customcrops:textures/item/crops/corn/corn.png}），与文字一起居中于投影点。
     *
     * <p><b>为什么图标走贴图而不是物品渲染</b>：本叠加层跑在 GUI 通道开始之前，没有
     * {@code GuiGraphicsExtractor} 可用，物品图标的「借位渲染 + 截取」链路在这里无从驱动；
     * 直接读资源包 PNG 成 Skija 图像是这条路径上唯一不需要主帧缓冲回读的做法。</p>
     *
     * @param iconTexture 贴图 id；为空串 / 非法 / 当前资源包里不存在时退化成纯文字字牌
     */
    public boolean textWithIcon(String text, String iconTexture, double x, double y, double z, float size,
                                int color, float alpha, boolean shadow) {
        return text(text, iconTexture == null || iconTexture.isBlank() ? null
            : Identifier.tryParse(iconTexture), x, y, z, size, color, alpha, shadow);
    }

    /** 字牌入队（{@code icon} 为 {@code null} 即纯文字） */
    private boolean text(String text, Identifier icon, double x, double y, double z, float size, int color,
                         float alpha, boolean shadow) {
        if (text == null || text.isEmpty() || size <= 0f) return false;
        fade = fadeAt(x, y, z);
        if (fade <= 0f || !affordable()) return false;
        if (deferred == null) return false;
        deferred.add(new DeferredText(text, icon, x, y, z, size, color, alpha, shadow, fade));
        return true;
    }

    // ── 叠加阶段绘制 ────────────────────────────────────────────────────────

    /** 把几何阶段收集的 2D 元素画到画布上（由 {@link WorldOverlay#renderOverlay()} 每帧调用一次）。 */
    public void drawDeferred(List<Deferred> items) {
        if (canvas == null || projector == null || items == null || items.isEmpty()) return;
        for (Deferred item : items) {
            if (item instanceof DeferredText text) {
                drawText(text);
            } else if (item instanceof DeferredBox2D box) {
                drawBox2D(box);
            } else if (item instanceof DeferredTracer tracer) {
                drawTracer(tracer);
            }
        }
    }

    private void drawText(DeferredText item) {
        fade = item.fade();
        if (effectiveOcclusion() && Visibility.blocked(camera.position(),
                new Vec3(item.x(), item.y(), item.z()))) return;

        float size = item.size() * globals.textScale();
        ScreenPoint point = projector.project(item.x(), item.y(), item.z());
        if (!visibleWithinScreen(point)) return;

        float alpha = item.alpha() * globals.alphaScale() * fade;
        String text = item.text();
        int color = item.color();
        float width = com.yiyiaddon.ui.render.MinecraftText.measure(text, size, false);
        // 「图标 + 文字」整体居中于投影点：图标高度取字号的 ICON_SCALE 倍（纯文字时一整块都不参与）
        Identifier icon = item.icon();
        float iconSize = icon == null ? 0f : size * ICON_SCALE;
        float iconGap = icon == null ? 0f : size * ICON_GAP_SCALE;
        float startX = point.x() - (width + iconSize + iconGap) * 0.5f;
        float drawX = startX + iconSize + iconGap;
        float baseline = CardLayout.baseline(point.y(), size);
        int themeShadow = ClickGuiThemeColors.current().shadow;

        if (item.shadow() && globals.textPlate()) {
            // 底板 + 单次偏移阴影。旧实现偏移三次（左上 / 右上 / 正下）会在字周围糊出一圈暗晕，
            // 字越小越明显，实机观感就是「模糊」。
            float padX = Math.max(2f, size * 0.22f);
            float padY = Math.max(1.5f, size * 0.14f);
            PLATE.setColor(GlassPanel.withAlpha(themeShadow, alpha * 0.65f));
            canvas.drawRRect(RRect.makeLTRB(
                startX - padX, point.y() - size * 0.5f - padY,
                drawX + width + padX, point.y() + size * 0.5f + padY, size * 0.45f), PLATE);
        }
        if (icon != null) {
            TextureImageCache.draw(canvas, icon, startX, point.y() - iconSize * 0.5f, iconSize, alpha);
        }
        if (item.shadow() && globals.textPlate()) {
            com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX, baseline + 1f, size,
                themeShadow, alpha);
        }
        com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX, baseline, size, color & 0xFFFFFF, alpha);
    }

    private void drawBox2D(DeferredBox2D item) {
        fade = item.fade();
        AABB box = item.box();
        if (effectiveOcclusion() && Visibility.blocked(camera.position(), box.getCenter())) return;

        projectCorners(box);
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        int visible = 0;
        for (ScreenPoint corner : corners) {
            if (corner == null || !corner.visible()) continue;
            visible++;
            if (corner.x() < minX) minX = corner.x();
            if (corner.y() < minY) minY = corner.y();
            if (corner.x() > maxX) maxX = corner.x();
            if (corner.y() > maxY) maxY = corner.y();
        }
        if (visible == 0) return;

        ShapeMode mode = item.mode();
        float thickness = item.thickness();
        Rect rect = Rect.makeLTRB(minX, minY, maxX, maxY);
        if (mode.sides()) {
            FILL.setColor(tint(item.sideColor()));
            canvas.drawRect(rect, FILL);
        }
        if (mode.lines() && thickness > 0f) {
            STROKE.setColor(tint(item.lineColor()));
            STROKE.setStrokeWidth(thickness);
            canvas.drawRect(rect, STROKE);
        }
    }

    private void drawTracer(DeferredTracer item) {
        fade = item.fade();
        if (effectiveOcclusion() && Visibility.blocked(camera.position(),
                new Vec3(item.x(), item.y(), item.z()))) return;

        ScreenPoint target = projector.project(item.x(), item.y(), item.z());
        if (!visibleWithinScreen(target)) return;

        float startX = Float.isNaN(item.startX()) ? screenWidth() * 0.5f : item.startX();
        float startY = Float.isNaN(item.startY()) ? screenHeight() : item.startY();

        STROKE.setColor(tint(item.color()));
        STROKE.setStrokeWidth(item.thickness());
        canvas.drawLine(startX, startY, target.x(), target.y(), STROKE);
    }

    // ── 内部 ────────────────────────────────────────────────────────────────

    /**
     * 投影 AABB 的 8 个角，结果写入 {@link #corners}（不可见的位置为 {@code null}）。
     *
     * <p>角序：0-3 为底面（西北 → 东北 → 东南 → 西南），4-7 为顶面对应位置。</p>
     */
    private void projectCorners(AABB box) {
        corners[0] = projectOrNull(box.minX, box.minY, box.minZ);
        corners[1] = projectOrNull(box.maxX, box.minY, box.minZ);
        corners[2] = projectOrNull(box.maxX, box.minY, box.maxZ);
        corners[3] = projectOrNull(box.minX, box.minY, box.maxZ);
        corners[4] = projectOrNull(box.minX, box.maxY, box.minZ);
        corners[5] = projectOrNull(box.maxX, box.maxY, box.minZ);
        corners[6] = projectOrNull(box.maxX, box.maxY, box.maxZ);
        corners[7] = projectOrNull(box.minX, box.maxY, box.maxZ);
    }

    private ScreenPoint projectOrNull(double x, double y, double z) {
        ScreenPoint point = projector.project(x, y, z);
        return point.visible() ? point : null;
    }

    private static double lerp(double from, double to, float t) {
        return from + (to - from) * t;
    }

    /** 按比例混合两个 ARGB 颜色（含透明度通道）。 */
    private static int lerpColor(int from, int to, float t) {
        int a = lerpChannel(from >>> 24, to >>> 24, t);
        int r = lerpChannel((from >> 16) & 0xFF, (to >> 16) & 0xFF, t);
        int g = lerpChannel((from >> 8) & 0xFF, (to >> 8) & 0xFF, t);
        int b = lerpChannel(from & 0xFF, to & 0xFF, t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static int lerpChannel(int from, int to, float t) {
        int value = (int) (from + (to - from) * t);
        if (value < 0) return 0;
        return Math.min(value, 255);
    }

    private boolean visibleWithinScreen(ScreenPoint point) {
        return point != null && point.onScreen(screenWidth(), screenHeight(), OFFSCREEN_MARGIN);
    }
}
