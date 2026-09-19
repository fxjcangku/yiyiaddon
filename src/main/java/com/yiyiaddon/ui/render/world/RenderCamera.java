package com.yiyiaddon.ui.render.world;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/**
 * 一帧的相机快照：相机位置、视图×投影矩阵、屏幕尺寸。
 *
 * <p>每帧采集一次，整帧共用。世界坐标先减去 {@link #position()} 得到相机相对坐标，
 * 再乘 {@link #viewProjection()} 得到裁剪空间坐标。</p>
 */
public record RenderCamera(Vec3 position, Matrix4f viewProjection, float screenWidth, float screenHeight) {

    /** 采集当前帧的相机状态；无世界或相机未初始化时返回 {@code null}。 */
    public static RenderCamera capture() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.level == null || minecraft.player == null) return null;

        Camera camera = minecraft.gameRenderer.mainCamera();
        if (!camera.isInitialized()) return null;

        float width = minecraft.getWindow().getGuiScaledWidth();
        float height = minecraft.getWindow().getGuiScaledHeight();
        if (width <= 0f || height <= 0f) return null;

        return new RenderCamera(
                camera.position(),
                camera.getViewRotationProjectionMatrix(new Matrix4f()),
                width,
                height);
    }

    public double cameraX() {
        return position.x;
    }

    public double cameraY() {
        return position.y;
    }

    public double cameraZ() {
        return position.z;
    }
}
