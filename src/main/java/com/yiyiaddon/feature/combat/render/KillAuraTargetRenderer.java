package com.yiyiaddon.feature.combat.render;

import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/**
 * 杀戮光环目标 ESP：给当前锁定/正在攻击的目标实体画一个描边框。
 *
 * <p><b>来源</b>：用户 2026-09-16 要求新增（原话「打东西要显示 esp 框」）。<b>无独立开关</b>：
 * 模块开启（{@code WorldOverlay} 层已注册）且有锁定目标时才画；关掉杀戮光环即整条消失。
 * 不新增任何设置项，颜色/线宽/模式全部在本类写死（照 {@code MiningPointRenderer} 的岩浆那套做法）。</p>
 *
 * <p><b>渲染管线复用</b>：与 {@code MiningPointRenderer} 同一范式——挂在 {@code WorldOverlay}
 * 世界渲染层上，几何由 {@link EspRenderer} 封装（框交给原版 gizmo 在世界空间绘制），本类只描述
 * 「画什么」。线宽 / 不透明度 / 显示距离等全局口径仍走「ESP 全局设置」页，本类不另建第二套配置。</p>
 */
public final class KillAuraTargetRenderer {

    /** 渲染距离：超过这个距离的目标不画（口径照 {@code MiningPointRenderer.RENDER_DISTANCE}） */
    private static final double RENDER_DISTANCE = 128.0;

    /** 线框线宽（GUI 缩放坐标）；照 {@code MiningPointRenderer.LINE_THICKNESS} */
    private static final float LINE_THICKNESS = 1.5f;

    /**
     * 目标配色：填充 / 描边。
     *
     * <p>固定红色系，与「报警 / 正在攻击」的语义一致（同档参考 {@code StardewRenderState}
     * 的 {@code MISMATCH_*} 报警色）；不跟随任何可配置渲染对象，故不设设置项。</p>
     */
    private static final EspColor TARGET_SIDE = new EspColor(0xFF5555, 40);
    private static final EspColor TARGET_LINE = new EspColor(0xFF5555, 200);

    private final Minecraft mc = Minecraft.getInstance();
    private final KillAuraModule module;

    public KillAuraTargetRenderer(KillAuraModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        if (renderer == null || mc.player == null || mc.level == null) return;

        Entity target = module.getTarget();
        // 无锁定目标 / 已换世界 / 已移除或已死 → 不画（每帧的锁定目标由模块的扫描结果决定）
        if (target == null || target.level() != mc.level || target.isRemoved() || !target.isAlive()) return;

        AABB box = target.getBoundingBox();
        if (mc.player.position().distanceTo(box.getCenter()) > RENDER_DISTANCE) return;

        // 只画描边：目标身上糊一层填充会挡模型
        renderer.box(box, TARGET_SIDE, TARGET_LINE, ShapeMode.Lines, LINE_THICKNESS);
    }
}
