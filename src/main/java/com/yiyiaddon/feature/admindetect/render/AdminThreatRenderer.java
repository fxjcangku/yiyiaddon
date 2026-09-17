package com.yiyiaddon.feature.admindetect.render;

import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 管理员检测的威胁 ESP：给命中的危险玩家画描边框，并从自己画一条射线过去。
 *
 * <p><b>来源</b>：用户 2026-09-16 要求新增（原话「管理员检测要带 esp 检测画框」「管理员传送过来的话
 * 射线➕框」）。<b>旧项目没有这个能力</b>，属新增需求而非迁移资产。</p>
 *
 * <p><b>渲染管线复用</b>：与 {@code MiningPointRenderer} 同一范式 ——
 * 挂在 {@code WorldOverlay} 世界渲染层上（模块启用时注册、关闭时注销），几何交给 {@link EspRenderer}
 * 封装（框交给原版 gizmo 在世界空间绘制，射线走 {@link EspRenderer#tracer}），本类只描述「画什么」。
 * 线宽 / 不透明度 / 显示距离等全局口径仍走「ESP 全局设置」页，本类不另建第二套配置。</p>
 *
 * <p><b>颜色</b>：两样都是红色（用户 2026-09-16 拍板「红色框红色射线」）。按第 146 条，ESP 类模块的
 * 颜色一律走 {@link com.yiyiaddon.ui.render.world.ColorPresets}，本类不写 RGB 字面量 ——
 * 取预设索引 0（红）再各自给透明度。</p>
 */
public final class AdminThreatRenderer {

    /** 渲染距离：超过这个距离的威胁不画（口径照 {@code MiningPointRenderer.RENDER_DISTANCE}） */
    private static final double RENDER_DISTANCE = 128.0;

    /** 线宽（GUI 缩放坐标）；照 {@code MiningPointRenderer.LINE_THICKNESS} */
    private static final float LINE_THICKNESS = 1.5f;

    /** 颜色预设索引：{@code ColorPresets} 的 0 号 = 红 */
    private static final int PRESET_RED = 0;

    /** 框：只画描边时不填充，但仍按既有 API 传一个填充色（透明度压到最低） */
    private static final EspColor BOX_SIDE = EspColor.preset(PRESET_RED, 40);

    /** 框的描边色 */
    private static final EspColor BOX_LINE = EspColor.preset(PRESET_RED, 200);

    /** 射线色 */
    private static final EspColor TRACER = EspColor.preset(PRESET_RED, 200);

    private final Minecraft mc = Minecraft.getInstance();
    private final AdminDetectorModule module;

    public AdminThreatRenderer(AdminDetectorModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.ADMIN)) return;
        if (renderer == null || mc.player == null || mc.level == null) return;
        // 两个开关都关掉时整层什么都不做（用户 2026-09-16 拍板这两项都是可关的设置项）
        boolean box = module.settings().espBox;
        boolean tracer = module.settings().tracer;
        if (!box && !tracer) return;

        for (Entity threat : module.threats()) {
            // 已换世界 / 已移除 → 不画（威胁列表每刻由模块按当刻世界重建）
            if (threat == null || threat.level() != mc.level || threat.isRemoved()) continue;

            Vec3 center = threat.getBoundingBox().getCenter();
            if (mc.player.position().distanceTo(center) > RENDER_DISTANCE) continue;

            if (box) {
                AABB bounds = threat.getBoundingBox();
                // 只画描边：目标身上糊一层填充会挡模型（照 MiningPointRenderer 的做法）
                renderer.box(bounds, BOX_SIDE, BOX_LINE, ShapeMode.Lines, LINE_THICKNESS);
            }
            if (tracer) {
                renderer.tracer(center, TRACER, LINE_THICKNESS);
            }
        }
    }
}
