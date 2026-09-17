package com.yiyiaddon.feature.autochest.render;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * AutoChest ESP 渲染器：高亮扫描到的合法容器。
 *
 * <p>三态着色：未处理绿色、已处理红色、处理中黄色（处理中绝不能显示为已处理红）。
 * 挂在本项目 {@code ui/render/world} 的 {@code WorldOverlay} 世界渲染层上，
 * 距离过远（&gt;64 格）不渲染。</p>
 *
 * <p>颜色取自设置项 {@code 未处理颜色 / 已处理颜色 / 处理中颜色}（ARGB 整数），
 * 框样式取自设置项 {@code ESP框样式}（仅线条 / 仅面 / 线+面）；
 * 仅在 {@code ESP高亮} 开启时渲染。</p>
 */
public final class AutoChestRenderer {

    /** 渲染距离：超过这个距离的容器不画（旧项目 {@code AutoChestRenderer.RENDER_DISTANCE}） */
    private static final double RENDER_DISTANCE = 64.0;

    /** 线框线宽；旧项目由 Meteor 渲染器固定，本项目在此显式给出 */
    private static final float LINE_THICKNESS = 1.5f;

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoChestModule module;

    public AutoChestRenderer(AutoChestModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18）：与模块自己的 renderEsp 是「全局优先」关系
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.AUTO_CHEST)) return;
        AutoChestSettings settings = module.settings();
        if (!settings.renderEsp) return;
        if (mc.player == null || mc.level == null) return;

        List<ChestTarget> targets = module.scanner().results();
        if (targets.isEmpty()) return;

        long expireMs = settings.recordExpireMinutes * 60_000L;
        ChestTarget processing = module.stateMachine().processingTarget();
        ShapeMode shapeMode = settings.espStyle.shapeMode();
        Vec3 playerPos = mc.player.getEyePosition();

        for (ChestTarget target : targets) {
            if (!target.inCurrentDimension()) continue;

            Vec3 targetPos = new Vec3(
                    target.pos().getX() + 0.5,
                    target.pos().getY() + 0.5,
                    target.pos().getZ() + 0.5);
            if (playerPos.distanceTo(targetPos) > RENDER_DISTANCE) continue;

            int color;
            if (isProcessing(target, processing)) {
                // 处理中：黄色，绝不能显示为已处理红
                color = settings.processingColor;
            } else if (module.recordStore().isProcessed(
                    target.pos(), target.dimension(), target.containerType(), expireMs)) {
                color = settings.processedColor;
            } else {
                color = settings.unprocessedColor;
            }

            renderer.blockBox(target.pos().getX(), target.pos().getY(), target.pos().getZ(),
                    color, color, shapeMode, LINE_THICKNESS);
        }
    }

    /** 目标是否为当前处理中的容器（处理中与未处理/已处理区分开） */
    private boolean isProcessing(ChestTarget target, ChestTarget processing) {
        return processing != null
                && processing.pos().equals(target.pos())
                && processing.dimension().equals(target.dimension());
    }
}
