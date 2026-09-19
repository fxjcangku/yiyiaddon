package com.yiyiaddon.feature.vision.render;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/**
 * 实体模式的绘制：给目标实体画包围框、从屏幕底部中心连一条射线到实体中心。
 *
 * <p>与 {@link VisionBlockRenderer} 同一套口径：总闸判断、形状取自设置、颜色走 {@code EspColor}、
 * 盒子 API 参数顺序「先填充后描边」（第 160 条）、线宽与管理员检测的威胁框一致。</p>
 *
 * <p><b>为什么默认线框</b>：给实体糊一层填充会挡住模型本体（管理员检测的威胁框同此处理），
 * 用户仍可在设置里逐模式改成「面」或「两者」。</p>
 */
public final class VisionEntityRenderer {

    /** 线宽（GUI 缩放坐标）；与方块模式、管理员检测同一口径 */
    private static final float LINE_THICKNESS = 1.5f;

    private final Minecraft mc = Minecraft.getInstance();
    private final VisionModule module;

    public VisionEntityRenderer(VisionModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（本模块两条绘制路径各查一次，第 76 号第三步）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.VISION)) return;
        if (renderer == null || mc.player == null || mc.level == null) return;

        VisionSettings settings = module.settings();
        if (!settings.entityEnabled) return;
        if (!settings.entityBox && !settings.entityTracer) return;

        int argb = settings.entityColor.argb();
        ShapeMode mode = settings.entityShapeMode;

        for (Entity entity : module.entityScanner().visible()) {
            // 已换世界 / 已移除 → 不画（快照每刻重建，这里再挡一次竞态）
            if (entity == null || entity.level() != mc.level || entity.isRemoved()) continue;
            AABB bounds = entity.getBoundingBox();
            if (settings.entityBox) {
                renderer.box(bounds, argb, argb, mode, LINE_THICKNESS);
            }
            if (settings.entityTracer) {
                renderer.tracer(bounds.getCenter(), argb, LINE_THICKNESS);
            }
        }
    }
}
