package com.yiyiaddon.ui.render.world;

import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * 世界坐标 → 屏幕坐标投影器。
 *
 * <p>透视变换下，线段的端点可能落在相机平面之后（{@code w <= 0}）。
 * 直接把这种端点做透视除法会得到镜像点，画出「穿过屏幕的乱线」，
 * 因此 {@link #projectLine} 会在近平面处做线性裁剪：{@code w} 沿线段是线性变化的，
 * 取插值参数使裁剪后的端点恰好落在近平面阈值上。</p>
 *
 * <p>非线程安全：每帧构建一个实例，只在渲染线程使用。</p>
 */
public final class WorldProjector {

    /** 近平面裁剪阈值，小于该值视为在相机之后。 */
    private static final float NEAR_W = 0.02f;

    private final Matrix4f viewProjection;
    private final double cameraX;
    private final double cameraY;
    private final double cameraZ;
    private final float screenWidth;
    private final float screenHeight;
    private final Vector4f scratch = new Vector4f();

    public WorldProjector(RenderCamera camera) {
        this.viewProjection = new Matrix4f(camera.viewProjection());
        this.cameraX = camera.cameraX();
        this.cameraY = camera.cameraY();
        this.cameraZ = camera.cameraZ();
        this.screenWidth = camera.screenWidth();
        this.screenHeight = camera.screenHeight();
    }

    public float screenWidth() {
        return screenWidth;
    }

    public float screenHeight() {
        return screenHeight;
    }

    /**
     * 投影单个世界坐标点。
     *
     * <p>返回值的 {@code w} 是裁剪空间深度（已保证为正），可直接用于「远的先画」排序。</p>
     */
    public ScreenPoint project(double x, double y, double z) {
        scratch.set((float) (x - cameraX), (float) (y - cameraY), (float) (z - cameraZ), 1f);
        viewProjection.transform(scratch);
        return toScreen(scratch.x, scratch.y, scratch.w);
    }

    /**
     * 投影一条世界空间线段，两端都不可见时返回 {@code null}。
     *
     * @return 长度为 2 的数组，已按需在近平面裁剪
     */
    public ScreenPoint[] projectLine(double x1, double y1, double z1,
                                     double x2, double y2, double z2) {
        Vector4f a = clip(x1, y1, z1);
        Vector4f b = clip(x2, y2, z2);
        float aw = a.w;
        float bw = b.w;

        if (aw <= NEAR_W && bw <= NEAR_W) return null;

        if (aw <= NEAR_W || bw <= NEAR_W) {
            // 在近平面处裁剪：w 沿线段线性变化，求出 w 恰好等于阈值的位置
            float t = (NEAR_W - aw) / (bw - aw);
            float cx = a.x + (b.x - a.x) * t;
            float cy = a.y + (b.y - a.y) * t;
            float cz = a.z + (b.z - a.z) * t;
            if (aw <= NEAR_W) {
                a.set(cx, cy, cz, NEAR_W);
            } else {
                b.set(cx, cy, cz, NEAR_W);
            }
        }

        return new ScreenPoint[]{
                toScreen(a.x, a.y, a.w),
                toScreen(b.x, b.y, b.w)
        };
    }

    private Vector4f clip(double x, double y, double z) {
        Vector4f vector = new Vector4f((float) (x - cameraX), (float) (y - cameraY), (float) (z - cameraZ), 1f);
        viewProjection.transform(vector);
        return vector;
    }

    private ScreenPoint toScreen(float clipX, float clipY, float w) {
        if (w <= NEAR_W) return ScreenPoint.invisible();
        float ndcX = clipX / w;
        float ndcY = clipY / w;
        return new ScreenPoint(
                (ndcX * 0.5f + 0.5f) * screenWidth,
                (0.5f - ndcY * 0.5f) * screenHeight,
                true,
                w);
    }
}
