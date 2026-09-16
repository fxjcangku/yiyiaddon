package com.yiyiaddon.feature.mining.render;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

/**
 * 挖矿 ESP：三点位（矿物箱 / 食物箱 / 挂机修复点）标记 + 岩浆透视。
 *
 * <p>挂在本项目 {@code ui/render/world} 的 {@code WorldOverlay} 世界渲染层上：世界空间几何与字牌
 * 由 {@link EspRenderer} 封装，本类只描述「画什么」。线宽 / 不透明度 / 显示距离 / 字号 /
 * 每帧图元上限全部走「ESP 全局设置」页，本类不另建第二套渲染配置。</p>
 *
 * <p><b>文案与门限逐字来自旧项目</b>：</p>
 * <ul>
 *   <li>标签文本 {@code AutoMinerModule.onRender2D}（旧 {@code :1099-1111}）给的是
 *       {@code §6[矿物箱] §7(维度)} 这类主体，{@code AutoMinerModule_ESP.renderLabel}
 *       （旧 {@code :31-65}）在其后**再追加**「当前维度名 + 距离」，因此最终显示为
 *       {@code §6[矿物箱] §7(主世界) §7(主世界) §8[45m]}。维度名重复是旧项目原样输出，
 *       <b>用户 2026-09-16 拍板：保留重复，一比一，不许"顺手修好"</b>。</li>
 *   <li>点位方块描边框：旧项目点位只有浮空文字、无框线；本项目按自己的 ESP 语言加了描边框
 *       （<b>用户 2026-09-16 拍板：留着</b>），属 UI 呈现差异，文案与配色不变。</li>
 *   <li>距离门限：{@code > 128} 格不画；距离口径为 {@code mc.player.position()} 到
 *       「方块中心上方 1.5 格」的直线距离（旧 {@code :34-37}）。</li>
 *   <li>岩浆：{@code (255,90,0,190)} 填充 / {@code (255,50,0,40)} 描边、{@code Lines} 模式、
 *       {@code > 128} 格不画、每 10 tick 重扫一次（旧 {@code renderLava :120-128} 与
 *       {@code onRender3D :1119-1129}）。</li>
 * </ul>
 */
public final class MiningPointRenderer {

    /** 渲染距离：超过这个距离的点位与岩浆不画（旧项目 {@code renderLabel} / {@code renderLava} 的 128 格） */
    private static final double RENDER_DISTANCE = 128.0;

    /** 线框线宽（GUI 缩放坐标）；旧项目由框架渲染器固定，本项目在此显式给出 */
    private static final float LINE_THICKNESS = 1.5f;

    /** 标签高度偏移：方块顶面以上 0.5 格（旧项目 {@code pos.getY() + 1.5}） */
    private static final double LABEL_Y_OFFSET = 1.5;

    /** 岩浆重扫间隔（tick）：避免每帧全量扫方块（旧项目 {@code :1122}） */
    private static final int LAVA_SCAN_INTERVAL = 10;

    /** 岩浆固定配色：填充 / 描边（旧项目 {@code renderLava} 的两个 Color） */
    private static final EspColor LAVA_SIDE = new EspColor(0xFF5A00, 190);
    private static final EspColor LAVA_LINE = new EspColor(0xFF3200, 40);

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoMinerModule module;

    /** 上一次岩浆扫描的 tick（旧项目 {@code lastLavaScanTick}） */
    private int lastLavaScanTick = Integer.MIN_VALUE;

    /** 岩浆位置缓存（旧项目 {@code cachedLavaPositions}） */
    private Set<BlockPos> cachedLavaPositions = Set.of();

    public MiningPointRenderer(AutoMinerModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        if (mc.player == null || mc.level == null) return;

        draw(renderer, MiningPointType.MINERAL, "§6[矿物箱]", module.mineralColor());
        draw(renderer, MiningPointType.FOOD, "§2[食物箱]", module.foodColor());
        draw(renderer, MiningPointType.AFK, "§d[挂机修复点]", module.afkColor());

        renderLava(renderer);
    }

    // ── 三点点位 ──

    /** 画单条点位：方块线框 + 浮空标签（仅当前维度、128 格内） */
    private void draw(EspRenderer renderer, MiningPointType type, String labelHead, EspColor color) {
        MiningPoint point = module.pointStore().get(type);
        if (point == null || !point.inCurrentDimension()) return;

        Vec3 labelPos = new Vec3(point.x() + 0.5, point.y() + LABEL_Y_OFFSET, point.z() + 0.5);
        double distance = mc.player.position().distanceTo(labelPos);
        if (distance > RENDER_DISTANCE) return;

        renderer.blockBox(point.x(), point.y(), point.z(), color, color, ShapeMode.Lines, LINE_THICKNESS);
        renderer.text(labelText(labelHead, point, distance), labelPos.x, labelPos.y, labelPos.z,
            (float) module.settings().espScale, color.argb(), 1f, true);
    }

    /**
     * 旧项目标签全文：主体 + 当前维度名 + 距离后缀（旧 {@code renderLabel :47-60} 逐字拼接）。
     *
     * <p>主体里的维度取**点位自身维度**（{@code WKData.dimensionName()}），追加段里的维度取
     * **当前世界维度**（{@code mc.level.dimension()}）——两者只在当前维度的点位上渲染，故实际一致。</p>
     */
    private String labelText(String labelHead, MiningPoint point, double distance) {
        return labelHead + " §7(" + dimensionName(point.dimension()) + ")"
            + " " + "§7(" + dimensionName(currentDimensionKey()) + ")"
            + " " + String.format("§8[%.0fm]", distance);
    }

    /** 当前世界维度键（旧 {@code mc.level.dimension().toString()} 的等价物，用本项目统一口径） */
    private String currentDimensionKey() {
        return mc.level == null ? "" : mc.level.dimension().identifier().toString();
    }

    /** 维度中文名（旧 {@code renderLabel :47-54} 的四种取值逐字） */
    private static String dimensionName(String dimension) {
        if (dimension == null) return "未知";
        if (dimension.contains("overworld")) return "主世界";
        if (dimension.contains("nether")) return "下界";
        if (dimension.contains("end")) return "末地";
        int colon = dimension.lastIndexOf(':');
        return colon < 0 ? dimension : dimension.substring(colon + 1);
    }

    // ── 岩浆透视 ──

    /** 岩浆透视：按需重扫 + 逐块画框（旧 {@code onRender3D :1119-1129}） */
    private void renderLava(EspRenderer renderer) {
        if (!module.settings().lavaEsp) return;

        int tick = mc.player.tickCount;
        if (tick - lastLavaScanTick >= LAVA_SCAN_INTERVAL) {
            lastLavaScanTick = tick;
            cachedLavaPositions = scanLava(module.settings().lavaEspRange);
        }
        if (cachedLavaPositions.isEmpty()) return;

        BlockPos center = mc.player.blockPosition();
        for (BlockPos pos : cachedLavaPositions) {
            if (center.distSqr(pos) > RENDER_DISTANCE * RENDER_DISTANCE) continue;
            renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), LAVA_SIDE, LAVA_LINE,
                ShapeMode.Lines, LINE_THICKNESS);
        }
    }

    /** 扫描玩家周围 radius 格的岩浆方块位置（旧 {@code scanLava :1150-1164} 逐字） */
    private Set<BlockPos> scanLava(int radius) {
        Set<BlockPos> result = new HashSet<>();
        if (mc.level == null) return result;
        BlockPos center = mc.player.blockPosition();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (mc.level.getBlockState(pos).getBlock() == Blocks.LAVA) {
                        result.add(pos);
                    }
                }
            }
        }
        return result;
    }
}
