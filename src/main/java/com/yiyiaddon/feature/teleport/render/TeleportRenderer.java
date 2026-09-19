package com.yiyiaddon.feature.teleport.render;

import com.yiyiaddon.feature.teleport.model.TeleportContext;
import com.yiyiaddon.feature.teleport.model.TeleportTarget;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 传送调试渲染层：只画最近一次传送的目标点、穿墙射线、回弹点与候选格，
 * 本身不参与任何业务决策（与 {@code MiningPointRenderer} / {@code AdminThreatRenderer} 同原则）。
 *
 * <p><b>管线</b>：挂在 {@code WorldOverlay} 世界渲染层上（模块启用时注册、关闭时注销），
 * 几何交给 {@link EspRenderer}。旧项目走框架的 {@code Render3DEvent} + {@code ShapeMode.Lines}，
 * 本类改走本项目统一入口（第 142 条）。</p>
 *
 * <p><b>颜色</b>：按第 146 条一律走 {@link com.yiyiaddon.ui.render.world.ColorPresets} 预设，
 * 语义照旧项目逐一对应：目标绿(1) / 射线青(3) / 回弹红(0) / 候选格黄(4) / 移动对象橙(7)。</p>
 *
 * <p><b>谁负责画</b>：颜色与「画什么」归模块自己的设置；ESP 全局设置只管「准不准画」这一层总闸。</p>
 */
public final class TeleportRenderer {

    /** 线宽（GUI 缩放坐标），照 {@code AdminThreatRenderer.LINE_THICKNESS} */
    private static final float LINE_THICKNESS = 1.5f;

    /** 目标落点：绿色（旧 {@code TARGET_LINE} 0,255,120 / {@code TARGET_SIDE} alpha 40） */
    private static final EspColor TARGET_LINE = EspColor.preset(1, 255);
    private static final EspColor TARGET_SIDE = EspColor.preset(1, 40);

    /** 穿墙射线：亮青（旧 {@code RAY} 0,200,255 alpha 200） */
    private static final EspColor RAY = EspColor.preset(3, 200);

    /** 回弹点：红色（旧 {@code RUBBER} 255,80,80 alpha 255） */
    private static final EspColor RUBBER = EspColor.preset(0, 255);

    /** 安全搜索候选格：黄色淡框（旧 {@code CELL} 255,255,0 alpha 60） */
    private static final EspColor CELL = EspColor.preset(4, 60);

    /** 移动对象碰撞箱（玩家 / 载具+乘客）：橙色（旧 {@code SUBJECT_LINE} alpha 200 / {@code SUBJECT_SIDE} alpha 40） */
    private static final EspColor SUBJECT_LINE = EspColor.preset(7, 200);
    private static final EspColor SUBJECT_SIDE = EspColor.preset(7, 40);

    private TeleportRenderer() {
    }

    /** 渲染最近一次传送上下文（模块的「调试渲染」开关打开时由世界渲染层调用） */
    public static void render(EspRenderer renderer, TeleportContext last) {
        // ESP 全局设置里「传送」这一层的总闸（只管画不画，不改本模块的颜色与内容）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.TELEPORT)) return;
        if (renderer == null || last == null) return;

        // 候选格（安全搜索评估记录）：只描边，颜色单一（旧实现 side 与 line 同为 CELL）
        for (BlockPos cell : last.debugCells) {
            renderer.blockBox(cell.getX(), cell.getY(), cell.getZ(), CELL, CELL, ShapeMode.Lines, LINE_THICKNESS);
        }

        // 穿墙锁定射线：始终画到「最大搜索距离」终点，表达搜索范围边界；
        // 实际选中目标由下方绿色线框单独标识，二者分离避免「必须传到最远」的错觉
        if (last.rayOrigin != null && last.rayDir != null) {
            Vec3 end = last.rayOrigin.add(last.rayDir.scale(last.rayLength));
            renderer.line(last.rayOrigin, end, RAY, LINE_THICKNESS);
        }

        // 目标落点（玩家身高线框）
        if (last.target != null) {
            boxFor(renderer, last.target);
        }

        // 移动对象实际碰撞箱（玩家 / 载具+乘客）：调试辅助判断大型载具是否容纳
        if (last.subjectBoxes != null) {
            for (AABB box : last.subjectBoxes) {
                renderer.box(box, SUBJECT_SIDE, SUBJECT_LINE, ShapeMode.Lines, LINE_THICKNESS);
            }
        }

        // 服务器回弹点
        if (last.rubberbandPos != null) {
            AABB box = new AABB(
                last.rubberbandPos.add(-0.3, 0, -0.3),
                last.rubberbandPos.add(0.3, 1.8, 0.3));
            renderer.box(box, RUBBER, RUBBER, ShapeMode.Lines, LINE_THICKNESS);
        }
    }

    /** 目标落点玩家身高线框（照旧 {@code TeleportRender.boxFor}：半宽 0.3、高 1.8） */
    private static void boxFor(EspRenderer renderer, TeleportTarget target) {
        AABB box = new AABB(
            target.feet().add(-0.3, 0, -0.3),
            target.feet().add(0.3, 1.8, 0.3));
        renderer.box(box, TARGET_SIDE, TARGET_LINE, ShapeMode.Lines, LINE_THICKNESS);
    }
}
