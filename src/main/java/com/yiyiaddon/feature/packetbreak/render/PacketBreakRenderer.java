package com.yiyiaddon.feature.packetbreak.render;

import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakSettings;
import com.yiyiaddon.feature.packetbreak.model.EspStyle;
import com.yiyiaddon.feature.packetbreak.model.LabelStyle;
import com.yiyiaddon.feature.packetbreak.model.PacketBreakTarget;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 发包秒破的进度 ESP：给「正在发包挖掘的方块」（含排队候选）画进度框与百分比标签。
 *
 * <p><b>逐字照旧项目</b> {@code tactical/packetbreak/PacketInstantBreak.java:997-1071}
 * 的两段渲染（3D 框线 + 2D 百分比标签）：画什么、画几个、位置偏移、收缩算法、标签文字与
 * 配色语义一字未改，只把框架侧的绘制调用换成本项目世界 ESP 层。</p>
 *
 * <p><b>框架适配（旧 → 新）</b></p>
 * <ul>
 *   <li>旧的 {@code Render3DEvent.renderer.box(x1,y1,z1,x2,y2,z2, side, line, shapeMode, 0)}
 *       → {@link EspRenderer#box(AABB, int, int, com.yiyiaddon.ui.render.world.ShapeMode, float, int)}；
 *       盒子类 API 的参数顺序仍是「先填充色、后描边色」（第 160 条），形状取自
 *       {@link EspStyle#shapeMode}（旧 {@code EspStyle.shapeMode} 的同义映射）。</li>
 *   <li>旧的 {@code NametagUtils.to2D + TextRenderer} → {@link EspRenderer#text}（自带投影、
 *       居中、距离裁剪与底板），标签落点仍是「方块中心 + 上方 1.2 格」。</li>
 *   <li>旧的线宽参数 {@code 0}（旧框架默认线宽）→ 本项目的固定线宽常量，取值与本批其它模块
 *       （{@code AdminThreatRenderer} / {@code VisionBlockRenderer}）一致。</li>
 *   <li>旧的字号 {@code TextRenderer.begin(1.0, ...)}（旧框架的 1 倍字号）→ 本项目固定的世界字牌
 *       字号常量，与 {@code FarmRenderer} 的世界标签同量级。</li>
 * </ul>
 *
 * <p><b>ESP 总闸</b>：入口第一行查 {@link EspGlobalSettings.Layer#INSTANT_BREAK}
 * （新增模块接入 ESP 总闸的第三步），关掉后整层不画；模块自己的「显示进度」开关只决定
 * 「本模块要不要画」。注册 / 注销随模块开关走（{@code onEnable} / {@code onDisable}），非常驻注册。</p>
 */
public final class PacketBreakRenderer {

    /** 线框线宽（GUI 缩放坐标）：与 {@code AdminThreatRenderer}/{@code VisionBlockRenderer} 同值 */
    private static final float LINE_THICKNESS = 1.5f;

    /**
     * 百分比标签字号（GUI 缩放坐标）。
     *
     * <p>旧项目用旧框架的 1 倍字号（不加粗、无缩放）；本项目世界字牌的正常量级取
     * {@code FarmRenderer.LABEL_SIZE} 的 10，故固定为该值。本模块没有字号设置项（旧项目也没有），
     * 全局「文字大小倍率」仍可在 ESP 全局设置页统一调整。</p>
     */
    private static final float LABEL_SIZE = 10f;

    /** 标签高度偏移：方块中心 + 上方 1.2 格（逐字照旧 {@code pos.getY() + 1.2}） */
    private static final double LABEL_Y_OFFSET = 1.2;

    /** 剩余 tick 计算的最小破坏速度（逐字照旧 {@code Math.max(target.destroyDelta, 1.0E-4F)}） */
    private static final float MIN_DESTROY_DELTA = 1.0E-4F;

    private final Minecraft mc = Minecraft.getInstance();
    private final PacketInstantBreakModule module;

    public PacketBreakRenderer(PacketInstantBreakModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（ESP 全局设置页可一处关掉）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.INSTANT_BREAK)) return;
        if (renderer == null || mc.player == null || mc.level == null) return;

        PacketBreakSettings settings = module.settings();
        // 模块自己的开关：只决定「本模块要不要画」
        if (!settings.render) return;

        // 旧 {@code onRender3D} 顺序：先整队候选，再当前活跃方块
        for (PacketBreakTarget target : module.queuedTargets()) {
            renderBlock(renderer, target, module.progressOf(target));
        }
        PacketBreakTarget active = module.activeTarget();
        if (active != null) renderBlock(renderer, active, module.progressOf(active));

        // 旧 {@code onRender2D}：百分比标签只在「显示进度 + 显示百分比」都开时画
        if (!settings.showPercent) return;
        for (PacketBreakTarget target : module.queuedTargets()) {
            renderLabel(renderer, target, module.progressOf(target));
        }
        if (active != null) renderLabel(renderer, active, module.progressOf(active));
    }

    /** 渲染 3D 框线；协议状态已移出模块入口，渲染仍读取原有配置。 */
    private void renderBlock(EspRenderer renderer, PacketBreakTarget target, double progress) {
        PacketBreakSettings settings = module.settings();

        VoxelShape shape = mc.level.getBlockState(target.pos).getShape(mc.level, target.pos);
        if (shape.isEmpty()) return;

        double x1 = target.pos.getX() + shape.min(Direction.Axis.X);
        double y1 = target.pos.getY() + shape.min(Direction.Axis.Y);
        double z1 = target.pos.getZ() + shape.min(Direction.Axis.Z);
        double x2 = target.pos.getX() + shape.max(Direction.Axis.X);
        double y2 = target.pos.getY() + shape.max(Direction.Axis.Y);
        double z2 = target.pos.getZ() + shape.max(Direction.Axis.Z);
        boolean ready = progress >= 1;

        if (settings.shrinkProgress && !ready) {
            double remain = 1.0 - Math.min(1, progress);
            double cx = (x1 + x2) / 2;
            double cy = (y1 + y2) / 2;
            double cz = (z1 + z2) / 2;
            x1 = cx - (cx - x1) * remain;
            x2 = cx + (x2 - cx) * remain;
            y1 = cy - (cy - y1) * remain;
            y2 = cy + (y2 - cy) * remain;
            z1 = cz - (cz - z1) * remain;
            z2 = cz + (z2 - cz) * remain;
        }

        int side = (ready ? settings.readySideColor : settings.sideColor).argb();
        int line = (ready ? settings.readyLineColor : settings.lineColor).argb();
        renderer.box(new AABB(x1, y1, z1, x2, y2, z2), side, line,
            settings.espStyle.shapeMode, LINE_THICKNESS, 0);
    }

    /** 渲染原有百分比标签。 */
    private void renderLabel(EspRenderer renderer, PacketBreakTarget target, double progress) {
        PacketBreakSettings settings = module.settings();

        int percent = (int) (Math.min(1, progress) * 100);
        // §l = 加粗（用户 2026-09-19：「所有的点位模块都要字体加粗」，秒破的这行标签一并跟上）；
        // 注意 §7 这类颜色码会把加粗重置掉（与 MinecraftText 的解析规则一致），所以后面那段要再写一次 §l
        StringBuilder label = new StringBuilder("§l").append(percent).append('%');
        if (settings.labelStyle == LabelStyle.BLOCK) {
            label.append(' ').append(target.block.getName().getString());
        } else if (settings.labelStyle == LabelStyle.TICK) {
            int required = module.requiredElapsedTicksToStop(Math.max(target.destroyDelta, MIN_DESTROY_DELTA));
            int remaining = target.startTick == PacketBreakTarget.TICK_NONE
                ? required
                : Math.max(0, required - (mc.player.tickCount - target.startTick));
            label.append(" §7§l(剩 ").append(remaining).append("t)");
        }

        BlockPos pos = target.pos;
        renderer.text(label.toString(), pos.getX() + 0.5, pos.getY() + LABEL_Y_OFFSET, pos.getZ() + 0.5,
            LABEL_SIZE, settings.progressColor.argb(), 1f, true);
    }
}
