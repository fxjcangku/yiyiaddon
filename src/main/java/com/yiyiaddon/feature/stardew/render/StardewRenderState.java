package com.yiyiaddon.feature.stardew.render;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointActions;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷渲染状态：一类渲染对象的独立配置（显示 / 颜色 / 渲染模式）+ 全部世界绘制内容。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:863-1053}。框架适配点：
 * 旧 {@code Render3DEvent / Render2DEvent} → 本项目 {@code WorldOverlay} 注册的回调里拿到的
 * {@link EspRenderer}；旧 {@code event.renderer.box(...)} → {@link EspRenderer#blockBox} /
 * {@link EspRenderer#box(AABB, int, int, ShapeMode, float)}（参数顺序固定：先填充色、后描边色）；
 * 旧 {@code SettingColor} → {@link EspColor}（绘制瞬间 {@code argb()} 解析，含彩虹）；
 * 旧 {@code EspShapeMode} → {@link ShapeMode}；洒水器三类一律调 {@link SprinklerEspRenderer}。</p>
 */
public final class StardewRenderState {

    /** 线框线宽；旧项目由渲染器固定，本项目在此显式给出（照 {@code AutoChestRenderer} 先例） */
    private static final float LINE_THICKNESS = 1.5f;

    /** 当前目标指示器：固定线框，不属于任何可配置渲染对象（逐帧复用，不再每帧 new） */
    private static final EspColor TARGET_SIDE = new EspColor(0x00FF64, 75);
    private static final EspColor TARGET_LINE = new EspColor(0x00FF64, 30);
    private static final EspColor TARGET_TRACER = new EspColor(0x00FF64, 200);

    /** 准星实时预览：琥珀色，与已绑定洒水器的青色覆盖框一眼区分 */
    private static final EspColor PREVIEW_SIDE = new EspColor(0xFFC800, 35);
    private static final EspColor PREVIEW_LINE = new EspColor(0xFFC800, 200);
    private static final EspColor PREVIEW_CENTER = new EspColor(0xFFC800, 255);

    private final Minecraft mc = Minecraft.getInstance();
    private final StardewSettings settings;
    private final StardewPointManager pointManager;
    private final StardewResourceIndex index;
    private final StardewCoordinator coordinator;
    private final StardewRegionManager regionManager;

    /**
     * 附近洒水器自动预览的取数入口（由模块提供，内部按秒缓存扫描结果）：空列表 = 这一帧不预览。
     *
     * <p><b>为什么要有它：</b>不绑定洒水器也想看每台各盖多大（在外面试布局时最需要）。
     * 它只画不落盘、不参与任何决策，且只在「预览范围」开着时才有内容。</p>
     */
    private final java.util.function.Supplier<List<StardewPointActions.NearbySprinkler>> nearbySprinklerPreview;

    /** 农田边界（起点 + 终点围成的立方体） */
    private final RenderOption renderFarmBorder;
    /** 洒水器本体方块 */
    private final RenderOption renderSprinklerBody;
    /** 洒水器覆盖范围（按等级半径推导的方形范围） */
    private final RenderOption renderSprinklerCoverage;
    /** 种子箱 */
    private final RenderOption renderSeedBox;
    /** 成品箱 */
    private final RenderOption renderOutputBox;
    /** 补水点 */
    private final RenderOption renderWaterSource;
    /** 洒水器点位标记 */
    private final RenderOption renderSprinklerPoint;
    /** 点位字牌（2D 文字）：只有显示开关，颜色跟随对应点位的方框 */
    private final RenderOption renderLabels;

    public StardewRenderState(StardewSettings settings, StardewPointManager pointManager,
                              StardewResourceIndex index, StardewCoordinator coordinator,
                              StardewRegionManager regionManager,
                              java.util.function.Supplier<List<StardewPointActions.NearbySprinkler>> nearbySprinklerPreview) {
        this.settings = settings;
        this.pointManager = pointManager;
        this.index = index;
        this.coordinator = coordinator;
        this.regionManager = regionManager;
        this.nearbySprinklerPreview = nearbySprinklerPreview;

        // ── 渲染显示：所有可配置对象统一归入「点位渲染」，按农田 → 洒水器 → 后勤点位排序。──
        // 每类对象仍然保持独立的显示开关、颜色与渲染模式；这里只调整主页分组与显示顺序。
        renderFarmBorder = renderOption(settings.renderFarmBorder);
        renderSprinklerBody = renderOption(settings.renderSprinklerBody);
        renderSprinklerCoverage = renderOption(settings.renderSprinklerCoverage);
        renderSprinklerPoint = renderOption(settings.renderSprinklerPoint);
        renderSeedBox = renderOption(settings.renderSeedBox);
        renderOutputBox = renderOption(settings.renderOutputBox);
        renderWaterSource = renderOption(settings.renderWaterSource);

        // 点位字牌：只有显示开关（颜色跟随对应点位的方框，没有单独颜色与渲染模式）
        renderLabels = renderOption(settings.renderLabels);
    }

    /**
     * 一类渲染对象的独立配置：显示开关 + 颜色 + 渲染模式。
     *
     * <p><b>为什么必须拆开：</b>以前只有一个全局「渲染模式」，改一次会把农田边界、洒水器本体、
     * 起点、终点、种子箱、成品箱、补水点全部一起改掉。现在每一类各自持有一套
     * {@code 显示 / 颜色 / 模式}，互不影响；字牌只有显示开关（颜色跟随方框）。</p>
     */
    private static final class RenderOption {
        private final StardewSettings.RenderObject object;

        private RenderOption(StardewSettings.RenderObject object) {
            this.object = object;
        }

        private boolean on() {
            return object.show;
        }

        private EspColor color() {
            return object.color;
        }

        private ShapeMode mode() {
            return object.mode;
        }
    }

    /**
     * 装配一类独立渲染对象（显示 / 颜色 / 渲染模式）。
     *
     * <p><b>独立性铁律：</b>每类对象各自持有一套 {@code 显示 / 颜色 / 渲染模式}，绝不共用任何
     * 总开关。洒水器本体与洒水器覆盖范围尤其如此：关掉本体，覆盖范围照常显示，反之亦然。</p>
     */
    private static RenderOption renderOption(StardewSettings.RenderObject object) {
        return new RenderOption(object);
    }

    /** 每帧绘制（由模块注册到世界渲染层驱动）：3D 图形 + 2D 字牌 */
    public void render(EspRenderer renderer) {
        List<StardewPointActions.NearbySprinkler> preview =
            nearbySprinklerPreview == null ? List.of() : nearbySprinklerPreview.get();
        onRender3D(renderer, preview);
        onRender2D(renderer, preview);
    }

    private void onRender3D(EspRenderer renderer, List<StardewPointActions.NearbySprinkler> preview) {
        if (mc.player == null || mc.level == null) return;

        // 农田边界框：分区种植开启时画区域框（每区一个），关闭时画起止点围成的整片田
        if (renderFarmBorder.on()) {
            List<StardewRegionManager.Region> regions = currentDimensionRegions();
            if (settings.regionPlanting && !regions.isEmpty()) {
                for (StardewRegionManager.Region region : regions) {
                    BlockPos regionMin = new BlockPos(region.minX(), region.minY(), region.minZ());
                    BlockPos regionMax = new BlockPos(region.maxX(), region.minY(), region.maxZ());
                    renderBorder(renderer, regionMin, regionMax, renderFarmBorder.color(), renderFarmBorder.mode());
                }
            } else {
                StardewPointManager.StardewPoint start = pointManager.get(StardewPointType.START);
                StardewPointManager.StardewPoint end = pointManager.get(StardewPointType.END);
                if (start != null && end != null) {
                    BlockPos min = new BlockPos(Math.min(start.x(), end.x()), Math.min(start.y(), end.y()), Math.min(start.z(), end.z()));
                    BlockPos max = new BlockPos(Math.max(start.x(), end.x()), Math.max(start.y(), end.y()), Math.max(start.z(), end.z()));
                    renderBorder(renderer, min, max, renderFarmBorder.color(), renderFarmBorder.mode());
                }
            }
        }

        // 单点位方框：各类各自独立开关 / 颜色 / 模式，关掉任意一类不影响其它
        renderPointBox(renderer, StardewPointType.SEED_BOX, renderSeedBox);
        renderPointBox(renderer, StardewPointType.OUTPUT_BOX, renderOutputBox);
        renderPointBox(renderer, StardewPointType.WATER_SOURCE, renderWaterSource);

        // 洒水器三类完全独立：本体 / 覆盖范围 / 点位标记
        List<BlockPos> sprinklers = boundSprinklers();
        if (!sprinklers.isEmpty()) {
            if (renderSprinklerBody.on()) {
                EspColor c = renderSprinklerBody.color();
                SprinklerEspRenderer.renderSprinklers(renderer, sprinklers, c.argb(), c.argb(), renderSprinklerBody.mode());
            }
            if (renderSprinklerCoverage.on()) {
                EspColor c = renderSprinklerCoverage.color();
                SprinklerEspRenderer.renderCoverage(renderer, sprinklers, this::sprinklerRadiusAt, c.argb(), c.argb(),
                    renderSprinklerCoverage.mode());
            }
            if (renderSprinklerPoint.on()) {
                EspColor c = renderSprinklerPoint.color();
                SprinklerEspRenderer.renderPointMarkers(renderer, sprinklers, c.argb(), c.argb(), renderSprinklerPoint.mode());
            }
        }

        // 附近洒水器自动预览：不绑定也能看每台各盖多大——只画，不落盘、不参与决策
        for (StardewPointActions.NearbySprinkler nearby : preview) renderNearbyPreview(renderer, nearby);

        // 当前目标：瞬时指示器，不属于上面可配置的渲染对象，固定线框渲染（不再受任何全局模式影响）
        BlockPos target = coordinator.currentTarget();
        if (target != null) {
            renderTarget(renderer, target, TARGET_SIDE, TARGET_LINE, ShapeMode.Lines);
            renderTargetLine(renderer, target, TARGET_TRACER);
        }
    }

    private void onRender2D(EspRenderer renderer, List<StardewPointActions.NearbySprinkler> preview) {
        // 预览字牌：它是「临时看范围」的显式动作，不受点位字牌开关约束
        for (StardewPointActions.NearbySprinkler nearby : preview) {
            if (isBound(nearby.pos())) continue;
            int side = radiusOfLevel(nearby.definition().sprinklerIndex()) * 2 + 1;
            BlockPos pos = nearby.pos();
            renderer.text(nearby.definition().displayName() + " · " + side + "×" + side,
                pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5,
                settings.labelSize, PREVIEW_LINE, PREVIEW_LINE.alpha() / 255f, true);
        }
        if (!renderLabels.on()) return;
        // 字牌颜色一律跟随对应点位方框的颜色：文本里若再写 §b/§6/§d，就会把颜色设置整个盖掉，
        // 界面上那个色块点了没反应（实机反馈就是这个）。改方框颜色，字牌同步变色。
        renderLabel(renderer, StardewPointType.SEED_BOX, "种子箱", renderSeedBox.color());
        renderLabel(renderer, StardewPointType.OUTPUT_BOX, "成品箱", renderOutputBox.color());
        renderLabel(renderer, StardewPointType.WATER_SOURCE, "补水点", renderWaterSource.color());
        // 分区种植：每块地头顶挂自己的作物名（颜色同「农田边界」那一项）
        if (settings.regionPlanting) {
            for (StardewRegionManager.Region region : currentDimensionRegions()) {
                renderRegionLabel(renderer, region);
            }
        }
    }

    /** 当前维度内的种植区域（分区关闭时调用方不会读它） */
    private List<StardewRegionManager.Region> currentDimensionRegions() {
        String dimension = StardewContext.dimension();
        List<StardewRegionManager.Region> result = new ArrayList<>();
        for (StardewRegionManager.Region region : regionManager.all()) {
            if (region.dimension() == null || region.dimension().equals(dimension)) result.add(region);
        }
        return result;
    }

    /** 区域字牌：挂在区域矩形中心上方，文字是「区域 N · 作物」 */
    private void renderRegionLabel(EspRenderer renderer, StardewRegionManager.Region region) {
        EspColor color = renderFarmBorder.color();
        double centerX = (region.minX() + region.maxX()) / 2.0 + 0.5;
        double centerZ = (region.minZ() + region.maxZ()) / 2.0 + 0.5;
        renderer.text("区域 " + region.index() + " · " + region.cropName(),
            centerX, region.minY() + 1.4, centerZ, settings.labelSize, color, color.alpha() / 255f, true);
    }

    /**
     * 点位字牌：挂在点位方块上方，并<b>水平居中于实际画出来的方框</b>。
     *
     * <p>大箱子（相邻两格同一套方块）画的是两格并集的外框，字牌若还挂在「点位绑定的那一格」正上方，
     * 就会偏向一侧——实机表现就是「字牌没居中」。这里按同一个并集算中心，字牌正对框中心。</p>
     */
    private void renderLabel(EspRenderer renderer, StardewPointType type, String text, EspColor color) {
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p == null || !p.inCurrentDimension()) return;
        double centerX = p.pos().getX() + 0.5;
        double centerZ = p.pos().getZ() + 0.5;
        BlockPos half = connectedChestHalf(p.pos(), type);
        if (half != null) {
            AABB box = unionBox(p.pos(), half);
            centerX = (box.minX + box.maxX) * 0.5;
            centerZ = (box.minZ + box.maxZ) * 0.5;
        }
        // 字号取设置里的「字牌大小」；透明度跟着方框颜色自己的 alpha 走
        renderer.text(text, centerX, p.pos().getY() + 1.4, centerZ, settings.labelSize, color,
            color.alpha() / 255f, true);
    }

    /** 本维度的全部已绑定洒水器点位 */
    private List<BlockPos> boundSprinklers() {
        List<BlockPos> sprinklers = new ArrayList<>();
        for (StardewPointManager.StardewPoint p : pointManager.getAll(StardewPointType.SPRINKLER)) {
            if (p.inCurrentDimension()) sprinklers.add(p.pos());
        }
        return sprinklers;
    }

    /**
     * 洒水器覆盖半径：等级 1~4 → 半径 1 / 1 / 2 / 3。
     *
     * <p>口径来自服务器资料《星露谷物语游戏攻略》洒水器表（初级 1 / 二级 1 / 三级 2 / 四级 3），
     * 与资源包无关（资源包里没有范围字段）。<b>不能写成「半径 = 等级」</b>：那会让 2~4 级各多算一圈
     * （二级 5×5、三级 7×7、四级 9×9），就是实机看到的「覆盖范围偏大」。等级未知时保守取 1。</p>
     *
     * <p>只用于渲染观察，绝不参与任何决策（补水 / 维护判定一律走点位与资源包身份）。</p>
     */
    private int sprinklerRadiusAt(BlockPos pos) {
        StardewPointManager.StardewPoint point = StardewPointActions.findSprinkler(pointManager, pos);
        if (point == null || point.identity() == null) return 1;
        return index.entryByKey(point.identity()) instanceof SprinklerDefinition def
            ? radiusOfLevel(def.sprinklerIndex())
            : 1;
    }

    /** 等级 → 覆盖半径（半径 r 含中心格，即 (2r+1)×(2r+1)）；分区覆盖率统计复用同一份口径 */
    public static int radiusOfLevel(int level) {
        return switch (level) {
            case 3 -> 2;
            case 4 -> 3;
            default -> 1;
        };
    }

    /**
     * 附近自动预览：以该洒水器为中心，按它的等级半径画一个方形范围框 + 中心格高亮。
     *
     * <p>与已绑定洒水器的覆盖框同一套画法（半径含中心格，即 (2r+1)×(2r+1)），只是换一种颜色；
     * 已绑定的台跳过——它由青色覆盖框负责，同一台画两个框只会互相盖住。</p>
     */
    private void renderNearbyPreview(EspRenderer renderer, StardewPointActions.NearbySprinkler nearby) {
        if (isBound(nearby.pos())) return;
        int radius = radiusOfLevel(nearby.definition().sprinklerIndex());
        BlockPos pos = nearby.pos();
        AABB box = new AABB(
            pos.getX() - radius, pos.getY(), pos.getZ() - radius,
            pos.getX() + radius + 1.0, pos.getY() + 1.0, pos.getZ() + radius + 1.0);
        renderer.box(box, PREVIEW_SIDE, PREVIEW_LINE, ShapeMode.Lines, LINE_THICKNESS);
        renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(),
            PREVIEW_CENTER, PREVIEW_CENTER, ShapeMode.Lines, LINE_THICKNESS);
    }

    /** 这一格是否已经绑成洒水器点位：绑定后由青色覆盖框负责，预览不再重复画 */
    private boolean isBound(BlockPos pos) {
        return StardewPointActions.findSprinkler(pointManager, pos) != null;
    }

    /** 画单个点位的方块框（独立开关 / 颜色 / 模式，仅本维度已绑定点位） */
    private void renderPointBox(EspRenderer renderer, StardewPointType type, RenderOption option) {
        if (!option.on()) return;
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p == null || !p.inCurrentDimension()) return;
        EspColor color = option.color();
        // 大箱子（相邻两格同一套方块）合成一个方框画：两格各画一框会在中间叠出两条棱，很难看
        BlockPos half = connectedChestHalf(p.pos(), type);
        if (half == null) {
            renderer.blockBox(p.pos().getX(), p.pos().getY(), p.pos().getZ(), color.argb(), color.argb(),
                option.mode(), LINE_THICKNESS);
            return;
        }
        renderer.box(unionBox(p.pos(), half), color.argb(), color.argb(), option.mode(), LINE_THICKNESS);
    }

    /** 种子箱 / 成品箱是大箱子时返回另一半的坐标；其余点位或单箱返回 {@code null} */
    private BlockPos connectedChestHalf(BlockPos pos, StardewPointType type) {
        if (type != StardewPointType.SEED_BOX && type != StardewPointType.OUTPUT_BOX) return null;
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) return null;
        BlockPos connected = pos.relative(ChestBlock.getConnectedDirection(state));
        return mc.level.getBlockState(connected).getBlock() instanceof ChestBlock ? connected : null;
    }

    /** 两格方块的外包围方框（角点取两格并集） */
    private static AABB unionBox(BlockPos a, BlockPos b) {
        return new AABB(
            Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()),
            Math.max(a.getX(), b.getX()) + 1.0, Math.max(a.getY(), b.getY()) + 1.0, Math.max(a.getZ(), b.getZ()) + 1.0);
    }

    /** 画农田边界外框（min/max 是包含端点的对角格） */
    private static void renderBorder(EspRenderer renderer, BlockPos min, BlockPos max, EspColor color, ShapeMode mode) {
        AABB box = new AABB(
            min.getX(), min.getY(), min.getZ(),
            max.getX() + 1.0, max.getY() + 1.0, max.getZ() + 1.0);
        renderer.box(box, color.argb(), color.argb(), mode, LINE_THICKNESS);
    }

    /** 高亮单个方块（当前作业目标） */
    private static void renderTarget(EspRenderer renderer, BlockPos pos, EspColor line, EspColor side, ShapeMode mode) {
        renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), side.argb(), line.argb(), mode, LINE_THICKNESS);
    }

    /** 画玩家眼睛到目标方块中心顶部的连线（射线） */
    private void renderTargetLine(EspRenderer renderer, BlockPos target, EspColor color) {
        var eye = mc.player.getEyePosition();
        renderer.line(eye.x, eye.y, eye.z, target.getX() + 0.5, target.getY() + 1.0, target.getZ() + 0.5,
            color.argb(), LINE_THICKNESS);
    }
}
