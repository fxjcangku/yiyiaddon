package com.yiyiaddon.feature.autochest.render;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
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
 * 框样式取自设置项 {@code ESP框样式}（仅线条 / 仅面 / 线+面），
 * 头顶字牌字号取自设置项 {@code 字牌大小}（用户 2026-09-19：「文字大小自定义」），
 * 字牌颜色跟着上面这套三态框色（用户 2026-09-21：「不同颜色合理分配」）；
 * 仅在 {@code ESP高亮} 开启时渲染。</p>
 */
public final class AutoChestRenderer {

    /** 渲染距离：超过这个距离的容器不画（旧项目 {@code AutoChestRenderer.RENDER_DISTANCE}） */
    private static final double RENDER_DISTANCE = 64.0;

    /** 线框线宽；旧项目由旧框架渲染器固定，本项目在此显式给出 */
    private static final float LINE_THICKNESS = 1.5f;

    /** 字牌高度偏移：方块顶面以上 0.4 格（与星露谷 / 村民容器字牌同一档：{@code Y + 1.4}） */
    private static final double LABEL_Y_OFFSET = 1.4;

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

            // 头顶字牌：写「[世界]名字」（共用件 PointLabelText：加粗 + 底板 + 居中，用户 2026-09-19）；
            // 字号走设置项「字牌大小」（实际字号再由渲染器乘全局「文字大小倍率」）；
            // 颜色直接用上面这一帧算出来的框色（用户 2026-09-21：「不同颜色合理分配」）——
            // 于是「绿字 = 未处理、黄字 = 处理中、红字 = 已处理」与框色同一套三态语义，不再是一个颜色
            double centerX = target.pos().getX() + 0.5;
            double labelY = target.pos().getY() + LABEL_Y_OFFSET;
            double centerZ = target.pos().getZ() + 0.5;
            PointLabelText.containerLabel(renderer, AutoChestModule.formatContainerName(target.containerType()),
                target.dimension(), centerX, labelY, centerZ, settings.labelSize, color & 0xFFFFFF);
        }
    }

    /** 目标是否为当前处理中的容器（处理中与未处理/已处理区分开） */
    private boolean isProcessing(ChestTarget target, ChestTarget processing) {
        return processing != null
                && processing.pos().equals(target.pos())
                && processing.dimension().equals(target.dimension());
    }
}
