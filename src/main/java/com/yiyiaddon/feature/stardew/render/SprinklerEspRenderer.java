package com.yiyiaddon.feature.stardew.render;

import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * 星露谷渲染层：只画洒水器，不参与任何业务决策。
 *
 * <p>洒水器的三类可视对象<b>完全独立</b>，各自有自己的颜色与渲染模式，互不影响：</p>
 * <ul>
 *   <li>{@link #renderSprinklers 本体} —— 已绑定洒水器所在的方块；</li>
 *   <li>{@link #renderCoverage 覆盖范围} —— 以等级半径推导的方形范围（仅供观察）；</li>
 *   <li>{@link #renderPointMarkers 点位标记} —— 方块中心的小标记，用于确认点位已绑定。</li>
 * </ul>
 *
 * <p>以前只有一个「渲染模式」同时管这三者（以及农田边界 / 各类点位），因此关掉本体也会把覆盖范围
 * 一起关掉、改模式会一起改。现在由调用方逐类传入 {@link ShapeMode} 与颜色。</p>
 *
 * <p>绘制走本项目 {@link EspRenderer}（世界空间唯一绘制入口），参数顺序与旧项目一致：
 * 先填充色、后描边色。</p>
 */
public final class SprinklerEspRenderer {

    /** 线框线宽；旧项目由渲染器固定，本项目在此显式给出 */
    private static final float LINE_THICKNESS = 1.5f;

    private SprinklerEspRenderer() {
    }

    /** 高亮所有洒水器本体方块（颜色 / 渲染模式由调用方按「洒水器本体」自己的配置传入） */
    public static void renderSprinklers(EspRenderer renderer, List<BlockPos> sprinklers,
                                        int line, int side, ShapeMode mode) {
        for (BlockPos pos : sprinklers) {
            renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), side, line, mode, LINE_THICKNESS);
        }
    }

    /**
     * 高亮每个洒水器的覆盖范围（方形，半径由 {@code radiusOf} 按洒水器等级给出）。
     *
     * <p>半径映射由调用方负责（等级 1~4 → 半径 1~4 格），本方法只管画。</p>
     */
    public static void renderCoverage(EspRenderer renderer, List<BlockPos> sprinklers,
                                      ToIntFunction<BlockPos> radiusOf, int line, int side, ShapeMode mode) {
        for (BlockPos pos : sprinklers) {
            int radius = Math.max(1, radiusOf.applyAsInt(pos));
            AABB box = new AABB(
                pos.getX() - radius, pos.getY(), pos.getZ() - radius,
                pos.getX() + radius + 1.0, pos.getY() + 1.0, pos.getZ() + radius + 1.0);
            renderer.box(box, side, line, mode, LINE_THICKNESS);
        }
    }

    /** 每个已绑定洒水器中心的点位标记（小方块，用于确认点位已绑定；与本体渲染互不影响） */
    public static void renderPointMarkers(EspRenderer renderer, List<BlockPos> sprinklers,
                                          int line, int side, ShapeMode mode) {
        for (BlockPos pos : sprinklers) {
            renderer.box(
                new AABB(pos.getX() + 0.3, pos.getY() + 0.3, pos.getZ() + 0.3,
                         pos.getX() + 0.7, pos.getY() + 0.7, pos.getZ() + 0.7),
                side, line, mode, LINE_THICKNESS);
        }
    }
}
