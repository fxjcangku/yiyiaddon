package com.yiyiaddon.feature.vision.render;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * 方块模式的绘制：给目标方块画框、从屏幕底部中心连一条射线到目标中心。
 *
 * <p><b>渲染管线复用</b>：挂在 {@code WorldOverlay} 世界渲染层上（模块启用时注册、关闭时注销），
 * 几何交给 {@link EspRenderer} 封装，本类只描述「画什么」。线宽 / 不透明度 / 显示距离等全局口径
 * 仍走「ESP 全局设置」页，本类不另建第二套配置。</p>
 *
 * <p><b>总闸</b>：入口第一行查 {@link EspGlobalSettings.Layer#VISION}（第 76 号三步中的第三步；
 * 本模块有方块 / 实体两条绘制路径，两条都在各自入口查，避免漏掉一条）。</p>
 *
 * <p><b>形状与颜色</b>：形状取自设置（`线框 / 面 / 两者`）；颜色走 {@code EspColor}，默认值来自
 * {@code ColorPresets}，本类不写 RGB 字面量（第 146 条）。盒子类 API 的参数顺序固定为
 * 「先填充色、后描边色」（第 160 条），本类两处都传同一个颜色，不存在传反的可能。</p>
 */
public final class VisionBlockRenderer {

    /** 线宽（GUI 缩放坐标）；照 {@code AdminThreatRenderer} 同一口径 */
    private static final float LINE_THICKNESS = 1.5f;

    private final Minecraft mc = Minecraft.getInstance();
    private final VisionModule module;

    public VisionBlockRenderer(VisionModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（ESP 全局设置页可一处关掉）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.VISION)) return;
        if (renderer == null || mc.player == null || mc.level == null) return;

        VisionSettings settings = module.settings();
        if (!settings.blockEnabled) return;
        // 框与射线都关掉时整段不画（用户口径：两者都可单独开关，也可同时开）
        if (!settings.blockBox && !settings.blockTracer) return;

        int argb = settings.blockColor.argb();
        ShapeMode mode = settings.blockShapeMode;

        for (BlockPos pos : module.blockScanner().visible()) {
            if (settings.blockBox) {
                renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), argb, argb, mode, LINE_THICKNESS);
            }
            if (settings.blockTracer) {
                renderer.tracer(new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
                    argb, LINE_THICKNESS);
            }
        }
    }
}
