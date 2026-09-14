package com.yiyiaddon.ui.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * 方块视线遮挡判定。
 *
 * <p>本项目的世界空间渲染走「投影 + Skija」，没有深度缓冲可用，因此「被遮挡时隐藏」只能靠
 * CPU 侧的射线判定近似实现：从相机向目标点做一次体素射线检测，命中方块即视为被遮挡。</p>
 *
 * <p>这是<b>近似</b>：以目标点（通常是包围盒中心）单点判定，半掩在墙后的目标会被整体判为遮挡。
 * 换来的好处是判定成本固定且可预测，不需要额外的深度纹理采样。</p>
 */
public final class Visibility {

    private Visibility() {
    }

    /** 从 {@code from} 看 {@code to}，视线是否被方块挡住。 */
    public static boolean blocked(Vec3 from, Vec3 to) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || from == null || to == null) return false;

        BlockHitResult hit = minecraft.level.clip(new ClipContext(
                from,
                to,
                ClipContext.Block.VISUAL,
                ClipContext.Fluid.NONE,
                minecraft.player));

        return hit.getType() != HitResult.Type.MISS;
    }

    /** 视线是否通畅（未被挡住）。 */
    public static boolean clear(Vec3 from, Vec3 to) {
        return !blocked(from, to);
    }
}
