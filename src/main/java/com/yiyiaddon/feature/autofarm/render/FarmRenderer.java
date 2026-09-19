package com.yiyiaddon.feature.autofarm.render;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.feature.autofarm.task.FarmTask;
import com.yiyiaddon.feature.autofarm.task.HarvestTask;
import com.yiyiaddon.feature.autofarm.task.PlantTask;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;

/**
 * 自动农场世界渲染层：只画农场边界、当前作业目标、四个箱子方框与点位防呆字牌，
 * 不参与任何业务决策。
 *
 * <ul>
 *   <li>边界：只画外框一圈（默认 {@code Lines}，不填面），大农场也不卡（旧 {@code renderBorder}）；</li>
 *   <li>目标：当前任务的目标方块高亮（旧默认线 + 面，旧 {@code renderTarget}）；</li>
 *   <li>四个箱子：单作物箱 / 多作物箱 / 种子补货箱 / 杂物箱各画一个独立方框，
 *       大箱子（相邻两格同一套箱子方块）按两格并集画一个框（照星露谷渲染层的写法）；</li>
 *   <li>字牌：四个箱子头顶的防呆标签（走 {@link EspRenderer#text}，旧 {@code renderLabel} 的等价物）；
 *       农田两个角是区域选点，不画字牌（用户 2026-09-19：「这个不显示，默认渲染 ESP 就可以了」）。</li>
 * </ul>
 *
 * <p><b>显示 / 颜色 / 渲染模式全部来自各自独立的 {@link EspRenderObject}</b>
 * （用户 2026-09-19：「所有标点选择点位位置的模块参照星露谷农场的点位设置」）：
 * 关掉任意一类不影响其它类，颜色在绘制瞬间解析（彩虹色随 {@link EspColor} 免费获得）。
 * 字牌的样式另走共用件 {@link PointLabelText}（加粗 + 主题强调色 + 底板）：
 * 四个箱子是容器，写「[世界]名字」。</p>
 */
public final class FarmRenderer {

    /** 线宽：与本模块既有绘制同一档（旧的边界 / 目标框都是 1f） */
    private static final float LINE_THICKNESS = 1f;

    private final AutoFarmModule module;
    private final Minecraft mc = Minecraft.getInstance();

    public FarmRenderer(AutoFarmModule module) {
        this.module = module;
    }

    /** 由 {@link com.yiyiaddon.ui.render.world.WorldOverlay} 每帧回调（模块启用时注册） */
    public void renderLayer(EspRenderer renderer) {
        var scanner = module.scanner();
        var settings = module.settings();

        // 农场边界（只画外框一圈，显示 / 颜色 / 渲染模式各自独立）
        if (settings.renderBounds.show && scanner.bounded()) {
            BlockPos min = scanner.min();
            BlockPos max = scanner.max();
            AABB box = new AABB(
                min.getX(), min.getY(), min.getZ(),
                max.getX() + 1.0, max.getY() + 1.0, max.getZ() + 1.0);
            int color = settings.renderBounds.color.argb();
            renderer.box(box, color, color, settings.renderBounds.mode, LINE_THICKNESS);
        }

        // 当前作业目标：收割定位作物本体；补种定位作物落点（底盘上方一格）
        if (settings.renderTarget.show) {
            FarmTask task = module.controller().currentTask();
            BlockPos target = null;
            if (task instanceof HarvestTask harvest) target = harvest.target().pos();
            else if (task instanceof PlantTask plant) target = plant.target().pos().above();
            if (target != null) {
                int argb = settings.renderTarget.color.argb();
                renderer.blockBox(target.getX(), target.getY(), target.getZ(), argb, argb,
                    settings.renderTarget.mode, LINE_THICKNESS);
            }
        }

        // 四个箱子各画一个独立方框：关掉一类不影响另外三类
        renderBox(renderer, SiteType.SINGLE_STORAGE, settings.renderSingleBox);
        renderBox(renderer, SiteType.MULTI_STORAGE, settings.renderMultiBox);
        renderBox(renderer, SiteType.SEED_STORAGE, settings.renderSeedBox);
        renderBox(renderer, SiteType.POISON_STORAGE, settings.renderPoisonBox);

        // 字牌：只给四个箱子挂防呆标签，统一受「点位字牌」显示开关约束
        if (settings.renderLabels.show) {
            // 农田两个角（农场点位1 / 农场点位2）是区域选点，用户 2026-09-19 定稿不画字牌
            // ——「这个不显示，默认渲染 ESP 就可以了」；农场范围由「农场边界」那个框表达
            boxLabel(renderer, SiteType.SINGLE_STORAGE, "单作物箱");
            boxLabel(renderer, SiteType.MULTI_STORAGE, "多作物箱");
            boxLabel(renderer, SiteType.SEED_STORAGE, "种子补货箱");
            boxLabel(renderer, SiteType.POISON_STORAGE, "杂物箱");
        }
    }

    /**
     * 画一个箱子点位的方框：受该箱子的显示 / 颜色 / 渲染模式控制。
     *
     * <p>大箱子按两格并集画一个框：两格各画一框会在中间叠出两条棱，很难看
     * （与星露谷渲染层同一做法）。</p>
     */
    private void renderBox(EspRenderer renderer, SiteType type, EspRenderObject object) {
        if (!object.show) return;
        FarmSite site = module.site(type);
        if (site == null || !site.inCurrentDimension()) return;
        BlockPos pos = site.pos();
        EspColor color = object.color;
        BlockPos half = connectedChestHalf(pos);
        if (half == null) {
            renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), color.argb(), color.argb(),
                object.mode, LINE_THICKNESS);
            return;
        }
        renderer.box(unionBox(pos, half), color.argb(), color.argb(), object.mode, LINE_THICKNESS);
    }

    /**
     * 大箱子（相邻两格同一套箱子方块）返回另一半的坐标；单箱 / 非箱子方块返回 {@code null}。
     *
     * <p>判据先读 {@code mc.level} 的方块状态：只有 {@link ChestBlock} 且
     * {@link ChestBlock#TYPE} 不是 {@link ChestType#SINGLE} 才可能是大箱子。</p>
     */
    private BlockPos connectedChestHalf(BlockPos pos) {
        if (mc.level == null) return null;
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) return null;
        BlockPos connected = pos.relative(ChestBlock.getConnectedDirection(state));
        return mc.level.getBlockState(connected).getBlock() instanceof ChestBlock ? connected : null;
    }

    /** 两格方块的外包围方框（角点取两格并集） */
    private static AABB unionBox(BlockPos a, BlockPos b) {
        return new AABB(
            Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()),
            Math.max(a.getX(), b.getX()) + 1.0, Math.max(a.getY(), b.getY()) + 1.0,
            Math.max(a.getZ(), b.getZ()) + 1.0);
    }

    /**
     * 箱子字牌（容器）：挂在方框正上方；大箱子水平居中于两格并集，否则字牌会偏向一侧。
     *
     * <p>样式与内容统一走 {@link PointLabelText}（用户 2026-09-19）：加粗 + 主题强调色 + 底板，
     * 容器写「[世界]名字」，字号取设置里的「字牌大小」。</p>
     */
    private void boxLabel(EspRenderer renderer, SiteType type, String text) {
        FarmSite site = module.site(type);
        if (site == null || !site.inCurrentDimension()) return;
        BlockPos pos = site.pos();
        double centerX = pos.getX() + 0.5;
        double centerZ = pos.getZ() + 0.5;
        BlockPos half = connectedChestHalf(pos);
        if (half != null) {
            AABB box = unionBox(pos, half);
            centerX = (box.minX + box.maxX) * 0.5;
            centerZ = (box.minZ + box.maxZ) * 0.5;
        }
        double labelY = pos.getY() + 1.4;
        PointLabelText.containerLabel(renderer, text, site.dimension(), centerX, labelY, centerZ,
            module.settings().labelSize);
    }
}
