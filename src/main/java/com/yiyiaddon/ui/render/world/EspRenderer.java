package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.Rect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 世界空间 ESP 绘制器：把世界坐标投影到屏幕后，用 Skija 画框、面、线条、射线与文字。
 *
 * <p>绘制发生在 {@code GuiRenderer.render} 之前（世界已画完、GUI 未画），所有图形叠加在世界之上，
 * 默认不受深度缓冲遮挡——即透视语义；把 {@link #occlusion(boolean)} 打开后改为「被方块挡住则不画」。</p>
 *
 * <p>坐标口径：投影结果与画布同为 GUI 缩放坐标，无需再做换算。</p>
 *
 * <p>画笔为静态共享，本类不再持有原生资源，因此无需逐帧释放。</p>
 */
public final class EspRenderer {

    /** 屏幕外扩边距：略超出屏幕的图形仍值得绘制（例如只露出一角的框）。 */
    private static final float OFFSCREEN_MARGIN = 64f;

    /** 渐变（线、渐变面）的分段数：段间插值近似，观感足够平滑且不依赖着色器。 */
    private static final int GRADIENT_SEGMENTS = 10;

    /** 颜色对象为 null 时的兜底：线框取白、填充取全透明。调用方应尽量不传 null。 */
    private static final int FALLBACK_LINE = 0xFFFFFFFF;
    private static final int FALLBACK_SIDE = 0x00000000;

    /** 共享画笔。Skija 的 Paint 是原生资源，复用可避免逐帧创建与回收。 */
    private static final Paint STROKE = new Paint().setAntiAlias(true)
            .setMode(PaintMode.STROKE)
            .setStrokeCap(PaintStrokeCap.ROUND);
    private static final Paint FILL = new Paint().setAntiAlias(true).setMode(PaintMode.FILL);

    /**
     * 12 条棱。每项为 {@code {角A, 角B, 面1, 面2}}，面用于 {@code excludeDir} 剔除判定。
     * 角序见 {@link #projectCorners}。
     */
    private static final int[][] EDGES = {
            {0, 4, Dir.WEST, Dir.NORTH}, {3, 7, Dir.WEST, Dir.SOUTH},
            {1, 5, Dir.EAST, Dir.NORTH}, {2, 6, Dir.EAST, Dir.SOUTH},
            {0, 3, Dir.WEST, Dir.DOWN}, {1, 2, Dir.EAST, Dir.DOWN},
            {0, 1, Dir.NORTH, Dir.DOWN}, {3, 2, Dir.SOUTH, Dir.DOWN},
            {4, 7, Dir.WEST, Dir.UP}, {5, 6, Dir.EAST, Dir.UP},
            {4, 5, Dir.NORTH, Dir.UP}, {7, 6, Dir.SOUTH, Dir.UP}
    };

    /** 6 个面。每项为 {@code {面方向, 角0, 角1, 角2, 角3}}，四角按环绕顺序排列。 */
    private static final int[][] FACES = {
            {Dir.DOWN, 0, 1, 2, 3}, {Dir.UP, 7, 6, 5, 4},
            {Dir.NORTH, 0, 4, 5, 1}, {Dir.EAST, 1, 5, 6, 2},
            {Dir.SOUTH, 2, 6, 7, 3}, {Dir.WEST, 3, 7, 4, 0}
    };

    private final Canvas canvas;
    private final WorldProjector projector;
    private final RenderCamera camera;
    private final ScreenPoint[] corners = new ScreenPoint[8];
    private final ScreenPoint[] facePoints = new ScreenPoint[4];
    private final Point[] polygon = new Point[4];

    private boolean occlusion;

    public EspRenderer(Canvas canvas, WorldProjector projector, RenderCamera camera) {
        this.canvas = canvas;
        this.projector = projector;
        this.camera = camera;
    }

    // ── 遮挡模式 ────────────────────────────────────────────────────────────

    /**
     * 是否启用「被方块挡住就不画」。
     *
     * <p>默认关闭（透视）。同一帧内可随目标切换：例如透明容器用透视、普通方块用遮挡。</p>
     */
    public EspRenderer occlusion(boolean enabled) {
        this.occlusion = enabled;
        return this;
    }

    public boolean occlusion() {
        return occlusion;
    }

    public float screenWidth() {
        return projector.screenWidth();
    }

    public float screenHeight() {
        return projector.screenHeight();
    }

    /** 投影单点，供调用方自行判断可见性。 */
    public ScreenPoint project(double x, double y, double z) {
        return projector.project(x, y, z);
    }

    public ScreenPoint project(Vec3 pos) {
        return projector.project(pos.x, pos.y, pos.z);
    }

    /** 该世界点当前是否应该绘制（屏幕内 + 未被遮挡）。 */
    public boolean shouldDraw(Vec3 pos) {
        if (pos == null) return false;
        ScreenPoint point = projector.project(pos.x, pos.y, pos.z);
        if (!visibleWithinScreen(point)) return false;
        return !occlusion || !Visibility.blocked(camera.position(), pos);
    }

    // ── 线条 ────────────────────────────────────────────────────────────────

    /** 画一条世界空间线段；两端都不可见时跳过，单端不可见时在近平面裁剪。 */
    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     int color, float thickness) {
        if (thickness <= 0f) return;
        if (occlusion && Visibility.blocked(camera.position(), new Vec3(
                (x1 + x2) * 0.5d, (y1 + y2) * 0.5d, (z1 + z2) * 0.5d))) return;

        ScreenPoint[] points = projector.projectLine(x1, y1, z1, x2, y2, z2);
        if (points == null) return;
        ScreenPoint a = points[0];
        ScreenPoint b = points[1];
        if (!visibleWithinScreen(a) && !visibleWithinScreen(b)) return;

        STROKE.setColor(color);
        STROKE.setStrokeWidth(thickness);
        canvas.drawLine(a.x(), a.y(), b.x(), b.y(), STROKE);
    }

    public void line(Vec3 from, Vec3 to, int color, float thickness) {
        line(from.x, from.y, from.z, to.x, to.y, to.z, color, thickness);
    }

    /**
     * 双色渐变线：起点 {@code color1}、终点 {@code color2}。
     *
     * <p>Skija 的线段只能一个颜色，这里按 {@link #GRADIENT_SEGMENTS} 段插值近似。</p>
     */
    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     int color1, int color2, float thickness) {
        if (thickness <= 0f) return;
        if (occlusion && Visibility.blocked(camera.position(), new Vec3(
                (x1 + x2) * 0.5d, (y1 + y2) * 0.5d, (z1 + z2) * 0.5d))) return;

        ScreenPoint[] points = projector.projectLine(x1, y1, z1, x2, y2, z2);
        if (points == null) return;
        ScreenPoint a = points[0];
        ScreenPoint b = points[1];
        if (!visibleWithinScreen(a) && !visibleWithinScreen(b)) return;

        STROKE.setStrokeWidth(thickness);
        for (int i = 0; i < GRADIENT_SEGMENTS; i++) {
            float t0 = (float) i / GRADIENT_SEGMENTS;
            float t1 = (float) (i + 1) / GRADIENT_SEGMENTS;
            STROKE.setColor(lerpColor(color1, color2, (t0 + t1) * 0.5f));
            canvas.drawLine(
                    lerp(a.x(), b.x(), t0), lerp(a.y(), b.y(), t0),
                    lerp(a.x(), b.x(), t1), lerp(a.y(), b.y(), t1), STROKE);
        }
    }

    public void line(Vec3 from, Vec3 to, int color1, int color2, float thickness) {
        line(from.x, from.y, from.z, to.x, to.y, to.z, color1, color2, thickness);
    }

    // ── 方框 ────────────────────────────────────────────────────────────────

    /**
     * 3D 线框盒：把 12 条棱投影后绘制，填充模式额外画出 6 个面。
     *
     * <p>透视投影下方形会呈现真实的立体缩形（近大远小），不是屏幕轴对齐矩形。</p>
     *
     * <p>参数顺序固定为<b>先填充色、后描边色</b>：这两个都是整数 ARGB，传反不会报错，只会画错。</p>
     */
    public void box(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        box(box, sideColor, lineColor, mode, thickness, 0);
    }

    /**
     * 3D 线框盒，带面剔除。
     *
     * @param excludeDir 被排除的面（{@link Dir} 的位或）；传 {@code 0} 画全部
     */
    public void box(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness, int excludeDir) {
        if (occlusion && Visibility.blocked(camera.position(), box.getCenter())) return;

        projectCorners(box);

        if (mode.sides()) {
            fillFaces(sideColor, excludeDir);
        }
        if (mode.lines() && thickness > 0f) {
            STROKE.setColor(lineColor);
            STROKE.setStrokeWidth(thickness);
            for (int[] edge : EDGES) {
                if (!Dir.keepsEdge(excludeDir, edge[2], edge[3])) continue;
                drawEdge(corners[edge[0]], corners[edge[1]]);
            }
        }
    }

    /**
     * 屏幕包围矩形框：把 8 个角投影后取屏幕轴对齐包围盒。
     *
     * <p>与 {@link #box} 的区别是观感——这里始终是正对屏幕的矩形，适合方块的平面高亮。</p>
     */
    public void box2D(AABB box, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        if (occlusion && Visibility.blocked(camera.position(), box.getCenter())) return;

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

        Rect rect = Rect.makeLTRB(minX, minY, maxX, maxY);
        if (mode.sides()) {
            FILL.setColor(sideColor);
            canvas.drawRect(rect, FILL);
        }
        if (mode.lines() && thickness > 0f) {
            STROKE.setColor(lineColor);
            STROKE.setStrokeWidth(thickness);
            canvas.drawRect(rect, STROKE);
        }
    }

    public void blockBox(int x, int y, int z, int sideColor, int lineColor, ShapeMode mode, float thickness) {
        blockBox(x, y, z, sideColor, lineColor, mode, thickness, 0);
    }

    public void blockBox(int x, int y, int z, int sideColor, int lineColor, ShapeMode mode,
                         float thickness, int excludeDir) {
        box(new AABB(x, y, z, x + 1, y + 1, z + 1), sideColor, lineColor, mode, thickness, excludeDir);
    }

    /** 只填面、不描边；成片范围（例如整片农田）用它避免内部棱线堆积。 */
    public void boxSides(AABB box, int color, int excludeDir) {
        box(box, color, 0, ShapeMode.Sides, 0f, excludeDir);
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
        ScreenPoint p0 = projectOrNull(x1, y1, z1);
        ScreenPoint p1 = projectOrNull(x2, y2, z2);
        ScreenPoint p2 = projectOrNull(x3, y3, z3);
        ScreenPoint p3 = projectOrNull(x4, y4, z4);
        if (p0 == null || p1 == null || p2 == null || p3 == null) return;
        if (occlusion && Visibility.blocked(camera.position(), new Vec3(
                (x1 + x2 + x3 + x4) * 0.25d, (y1 + y2 + y3 + y4) * 0.25d, (z1 + z2 + z3 + z4) * 0.25d))) return;

        drawPolygon(p0.x(), p0.y(), p1.x(), p1.y(), p2.x(), p2.y(), p3.x(), p3.y(), color);
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
        ScreenPoint p0 = projectOrNull(x1, y1, z1);
        ScreenPoint p1 = projectOrNull(x2, y2, z2);
        ScreenPoint p2 = projectOrNull(x3, y3, z3);
        ScreenPoint p3 = projectOrNull(x4, y4, z4);
        if (p0 == null || p1 == null || p2 == null || p3 == null) return;

        if (mode.sides()) {
            drawPolygon(p0.x(), p0.y(), p1.x(), p1.y(), p2.x(), p2.y(), p3.x(), p3.y(), sideColor);
        }
        if (mode.lines() && thickness > 0f) {
            STROKE.setColor(lineColor);
            STROKE.setStrokeWidth(thickness);
            canvas.drawLine(p0.x(), p0.y(), p1.x(), p1.y(), STROKE);
            canvas.drawLine(p1.x(), p1.y(), p2.x(), p2.y(), STROKE);
            canvas.drawLine(p2.x(), p2.y(), p3.x(), p3.y(), STROKE);
            canvas.drawLine(p3.x(), p3.y(), p0.x(), p0.y(), STROKE);
        }
    }

    /** 竖直面单色填充。 */
    public void quadVertical(double x1, double y1, double z1, double x2, double y2, double z2, int color) {
        sideVertical(x1, y1, z1, x2, y2, z2, color, 0, ShapeMode.Sides, 0f);
    }

    /** 水平面单色填充。 */
    public void quadHorizontal(double x1, double y, double z1, double x2, double z2, int color) {
        sideHorizontal(x1, y, z1, x2, z2, color, 0, ShapeMode.Sides, 0f);
    }

    /**
     * 竖直渐变面：上边 {@code topColor}、下边 {@code bottomColor}。
     *
     * <p>难度在于 Skija 的填充不支持逐点异色，故沿竖直方向切成
     * {@link #GRADIENT_SEGMENTS} 段、每段单色填充，以段间跳变近似连续渐变。</p>
     */
    public void gradientQuadVertical(double x1, double y1, double z1, double x2, double y2, double z2,
                                     int topColor, int bottomColor) {
        ScreenPoint bottomLeft = projectOrNull(x1, y1, z1);
        ScreenPoint topLeft = projectOrNull(x1, y2, z1);
        ScreenPoint topRight = projectOrNull(x2, y2, z2);
        ScreenPoint bottomRight = projectOrNull(x2, y1, z2);
        if (bottomLeft == null || topLeft == null || topRight == null || bottomRight == null) return;

        for (int i = 0; i < GRADIENT_SEGMENTS; i++) {
            float t0 = (float) i / GRADIENT_SEGMENTS;
            float t1 = (float) (i + 1) / GRADIENT_SEGMENTS;
            drawPolygon(
                    lerp(topLeft.x(), bottomLeft.x(), t0), lerp(topLeft.y(), bottomLeft.y(), t0),
                    lerp(topRight.x(), bottomRight.x(), t0), lerp(topRight.y(), bottomRight.y(), t0),
                    lerp(topRight.x(), bottomRight.x(), t1), lerp(topRight.y(), bottomRight.y(), t1),
                    lerp(topLeft.x(), bottomLeft.x(), t1), lerp(topLeft.y(), bottomLeft.y(), t1),
                    lerpColor(topColor, bottomColor, (t0 + t1) * 0.5f));
        }
    }

    // ── 射线 ────────────────────────────────────────────────────────────────

    /** 射线：从屏幕底部中心指向目标位置。 */
    public void tracer(double x, double y, double z, int color, float thickness) {
        tracerFrom(screenWidth() * 0.5f, screenHeight(), x, y, z, color, thickness);
    }

    public void tracer(Vec3 target, int color, float thickness) {
        tracer(target.x, target.y, target.z, color, thickness);
    }

    /** 指定屏幕起点的射线（例如从准星位置起画）。 */
    public void tracerFrom(float startX, float startY, double x, double y, double z,
                           int color, float thickness) {
        if (thickness <= 0f) return;
        if (occlusion && Visibility.blocked(camera.position(), new Vec3(x, y, z))) return;

        ScreenPoint target = projector.project(x, y, z);
        if (!visibleWithinScreen(target)) return;

        STROKE.setColor(color);
        STROKE.setStrokeWidth(thickness);
        canvas.drawLine(startX, startY, target.x(), target.y(), STROKE);
    }

    // ── 文字 ────────────────────────────────────────────────────────────────

    /** 在世界坐标处居中绘制文字，可用 {@code shadow} 叠加描边提升可读性。 */
    public boolean text(String text, double x, double y, double z, float size, int color) {
        return text(text, x, y, z, size, color, 1f, false);
    }

    /**
     * 在世界坐标处居中绘制文字。
     *
     * @param size   字号（GUI 缩放坐标）
     * @param alpha  整体透明度
     * @param shadow 是否画深色描边
     */
    public boolean text(String text, double x, double y, double z, float size, int color,
                        float alpha, boolean shadow) {
        if (text == null || text.isEmpty() || size <= 0f) return false;
        if (occlusion && Visibility.blocked(camera.position(), new Vec3(x, y, z))) return false;

        ScreenPoint point = projector.project(x, y, z);
        if (!visibleWithinScreen(point)) return false;

        float width = com.yiyiaddon.ui.render.MinecraftText.measure(text, size, false);
        float drawX = point.x() - width * 0.5f;
        float drawY = point.y();

        if (shadow) {
            float offset = Math.max(1f, size * 0.06f);
            int outline = ClickGuiThemeColors.current().shadow;
            com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX + offset, drawY + offset, size, outline, alpha);
            com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX - offset, drawY + offset, size, outline, alpha);
            com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX, drawY + offset * 2f, size, outline, alpha);
        }
        com.yiyiaddon.ui.render.MinecraftText.draw(canvas, text, drawX, drawY, size, color & 0xFFFFFF, alpha);
        return true;
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

    private void drawEdge(ScreenPoint a, ScreenPoint b) {
        if (a == null || b == null) return;
        canvas.drawLine(a.x(), a.y(), b.x(), b.y(), STROKE);
    }

    /** 逐面填充；四角齐全且未被 {@code excludeDir} 排除的面才绘制。 */
    private void fillFaces(int color, int excludeDir) {
        FILL.setColor(color);
        for (int[] face : FACES) {
            if (!Dir.keeps(excludeDir, face[0])) continue;

            boolean complete = true;
            for (int i = 0; i < 4; i++) {
                ScreenPoint point = corners[face[i + 1]];
                if (point == null) {
                    complete = false;
                    break;
                }
                facePoints[i] = point;
            }
            if (!complete) continue;

            drawPolygon(
                    facePoints[0].x(), facePoints[0].y(),
                    facePoints[1].x(), facePoints[1].y(),
                    facePoints[2].x(), facePoints[2].y(),
                    facePoints[3].x(), facePoints[3].y(),
                    color);
        }
    }

    /**
     * 构造四边形路径并填充。
     *
     * <p>Skija 的 {@code Point} 与 {@code Path} 都不可变，因此每次调用仍会新建 4 个点对象与 1 条路径，
     * 这里复用的只是 {@link #polygon} 这个数组本身。</p>
     */
    private void drawPolygon(float x0, float y0, float x1, float y1,
                             float x2, float y2, float x3, float y3, int color) {
        polygon[0] = new Point(x0, y0);
        polygon[1] = new Point(x1, y1);
        polygon[2] = new Point(x2, y2);
        polygon[3] = new Point(x3, y3);

        FILL.setColor(color);
        Path path = Path.makePolygon(polygon, true);
        try {
            canvas.drawPath(path, FILL);
        } finally {
            path.close();
        }
    }

    private static float lerp(float from, float to, float t) {
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
