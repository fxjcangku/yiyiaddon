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
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
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

    /**
     * 预览层（{@code .stardew 预览范围}）的可见半径（格）：只画玩家附近这些距离内的东西。
     *
     * <p>与「附近洒水器自动预览」的扫描半径同一口径（模块那边直接引用本常量），
     * 于是「看到的东西」与「扫到的洒水器」范围一致。</p>
     */
    public static final int PREVIEW_RADIUS = 32;

    /** 当前目标指示器：固定线框，不属于任何可配置渲染对象（逐帧复用，不再每帧 new） */
    private static final EspColor TARGET_SIDE = new EspColor(0x00FF64, 75);
    private static final EspColor TARGET_LINE = new EspColor(0x00FF64, 30);
    private static final EspColor TARGET_TRACER = new EspColor(0x00FF64, 200);

    /** 准星实时预览：琥珀色，与已绑定洒水器的青色覆盖框一眼区分 */
    private static final EspColor PREVIEW_SIDE = new EspColor(0xFFC800, 35);
    private static final EspColor PREVIEW_LINE = new EspColor(0xFFC800, 200);
    private static final EspColor PREVIEW_CENTER = new EspColor(0xFFC800, 255);

    /** 分区错位高亮：固定红色（报警类，不跟随任何渲染对象的配色） */
    private static final EspColor MISMATCH_SIDE = new EspColor(0xFF2D2D, 60);
    private static final EspColor MISMATCH_LINE = new EspColor(0xFF2D2D, 220);

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

    /** 种植区域（每块已划分的地一个框） */
    private final RenderOption renderRegions;
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
    private final RenderOption renderLavaBox;
    private final RenderOption renderBreathBox;
    /** 洒水器点位标记 */
    private final RenderOption renderSprinklerPoint;
    /** 点位字牌（2D 文字）：只有显示开关，样式（加粗 / 主题色 / 底板）统一走 PointLabelText */
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
        renderRegions = renderOption(settings.renderRegions);
        renderSprinklerBody = renderOption(settings.renderSprinklerBody);
        renderSprinklerCoverage = renderOption(settings.renderSprinklerCoverage);
        renderSprinklerPoint = renderOption(settings.renderSprinklerPoint);
        renderSeedBox = renderOption(settings.renderSeedBox);
        renderOutputBox = renderOption(settings.renderOutputBox);
        renderWaterSource = renderOption(settings.renderWaterSource);
        renderLavaBox = renderOption(settings.renderLavaBox);
        renderBreathBox = renderOption(settings.renderBreathBox);

        // 点位字牌：只有显示开关（加粗 + UI 主题色 + 底板，见 PointLabelText）
        renderLabels = renderOption(settings.renderLabels);
    }

    /**
     * 一类渲染对象的独立配置：显示开关 + 颜色 + 渲染模式。
     *
     * <p><b>为什么必须拆开：</b>以前只有一个全局「渲染模式」，改一次会把种植区域、洒水器本体、
     * 覆盖范围、种子箱、成品箱、补水点全部一起改掉。现在每一类各自持有一套
     * {@code 显示 / 颜色 / 模式}，互不影响；字牌只有显示开关（颜色跟随方框）。</p>
     */
    private static final class RenderOption {
        private final EspRenderObject object;

        private RenderOption(EspRenderObject object) {
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
    private static RenderOption renderOption(EspRenderObject object) {
        return new RenderOption(object);
    }

    /** 每帧绘制（由模块自己的渲染层驱动）：整片农田都画，不做距离裁剪 */
    public void render(EspRenderer renderer) {
        draw(renderer, false);
    }

    /**
     * 预览层绘制（{@code .stardew 预览范围}）：<b>只画玩家附近的东西</b>。
     *
     * <p><b>为什么要裁剪：</b>预览层是「不启动模块也能看一眼」的临时观察工具。全局 ESP 是透视
     * 且默认不限距离，于是传送到别处之后，农场的区域框与字牌照样浮在画面上（实机反馈），
     * 远处的东西也不该再占视野。这里按 {@link #PREVIEW_RADIUS} 格裁剪；模块自己的那层不裁剪——
     * 它在作业中必须看得到整片地。</p>
     */
    public void renderNearby(EspRenderer renderer) {
        draw(renderer, true);
    }

    /** 每帧绘制：3D 图形 + 2D 字牌；{@code nearbyOnly} 为真时只画玩家附近 */
    private void draw(EspRenderer renderer, boolean nearbyOnly) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18，ESP 全局设置页 ▸ 各模块 ESP）：
        // 放在 draw 而不是两个 public 入口，这样 render（整片农田）与 renderNearby（预览层）一并拦住
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.STARDEW)) return;
        List<StardewPointActions.NearbySprinkler> preview =
            nearbySprinklerPreview == null ? List.of() : nearbySprinklerPreview.get();
        onRender3D(renderer, preview, nearbyOnly);
        onRender2D(renderer, preview, nearbyOnly);
    }

    private void onRender3D(EspRenderer renderer, List<StardewPointActions.NearbySprinkler> preview,
                            boolean nearbyOnly) {
        if (mc.player == null || mc.level == null) return;

        // 种植区域框：每块已划分的地一个框（范围来源就是它，没有起止点这一层了）
        if (renderRegions.on()) {
            for (StardewRegionManager.Region region : currentDimensionRegions()) {
                if (!nearPlayer(nearbyOnly, region)) continue;
                int y = regionFrameY(region);
                BlockPos regionMin = new BlockPos(region.minX(), y, region.minZ());
                BlockPos regionMax = new BlockPos(region.maxX(), y, region.maxZ());
                renderBorder(renderer, regionMin, regionMax, renderRegions.color(), renderRegions.mode());
            }
        }

        // 分区错位：把每一个「种了别的作物」的格子画成红框（连同那株植株一起框住），
        // 玩家照着红框走过去清掉即可；清理干净后模块自动恢复，红框随之消失
        for (BlockPos mismatchPos : coordinator.regionMismatchCells()) {
            renderer.blockBox(mismatchPos.getX(), mismatchPos.getY(), mismatchPos.getZ(),
                MISMATCH_SIDE.argb(), MISMATCH_LINE.argb(), ShapeMode.Lines, LINE_THICKNESS);
            renderer.blockBox(mismatchPos.getX(), mismatchPos.getY() + 1, mismatchPos.getZ(),
                MISMATCH_SIDE.argb(), MISMATCH_LINE.argb(), ShapeMode.Lines, LINE_THICKNESS);
        }

        // 单点位方框：各类各自独立开关 / 颜色 / 模式，关掉任意一类不影响其它
        renderPointBox(renderer, StardewPointType.SEED_BOX, renderSeedBox, nearbyOnly);
        renderPointBox(renderer, StardewPointType.OUTPUT_BOX, renderOutputBox, nearbyOnly);
        renderPointBox(renderer, StardewPointType.WATER_SOURCE, renderWaterSource, nearbyOnly);
        renderPointBox(renderer, StardewPointType.LAVA_BOX, renderLavaBox, nearbyOnly);
        renderPointBox(renderer, StardewPointType.BREATH_BOX, renderBreathBox, nearbyOnly);

        // 洒水器三类完全独立：本体 / 覆盖范围 / 点位标记
        List<BlockPos> sprinklers = boundSprinklers(nearbyOnly);
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

    private void onRender2D(EspRenderer renderer, List<StardewPointActions.NearbySprinkler> preview,
                            boolean nearbyOnly) {
        // 预览字牌：它是「临时看范围」的显式动作，不受点位字牌开关约束
        for (StardewPointActions.NearbySprinkler nearby : preview) {
            if (isBound(nearby.pos())) continue;
            int side = radiusOfLevel(nearby.definition().sprinklerIndex()) * 2 + 1;
            BlockPos pos = nearby.pos();
            renderer.text(nearby.definition().displayName() + " · " + side + "×" + side,
                pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5,
                settings.labelSize, PREVIEW_LINE, PREVIEW_LINE.alpha() / 255f, true);
        }
        // 错位字牌：报警类，不受「点位字牌」开关约束，红色不透明，写清这块地应该种什么
        for (BlockPos mismatchPos : coordinator.regionMismatchCells()) {
            StardewRegionManager.Region region = regionManager.at(mismatchPos, StardewContext.dimension());
            String want = region == null ? "本区域作物" : region.cropName();
            renderer.text("错位 · 应为 " + want,
                mismatchPos.getX() + 0.5, mismatchPos.getY() + 1.6, mismatchPos.getZ() + 0.5,
                settings.labelSize, MISMATCH_LINE, 1.0f, true);
        }
        if (!renderLabels.on()) return;
        // 字牌颜色不再取方框色：字牌样式统一跟随 UI 主题（见 PointLabelText，用户 2026-09-19）
        renderLabel(renderer, StardewPointType.SEED_BOX, "种子箱", nearbyOnly);
        renderLabel(renderer, StardewPointType.OUTPUT_BOX, "成品箱", nearbyOnly);
        renderLabel(renderer, StardewPointType.WATER_SOURCE, "补水点", nearbyOnly);
        renderLabel(renderer, StardewPointType.LAVA_BOX, "岩浆箱", nearbyOnly);
        renderLabel(renderer, StardewPointType.BREATH_BOX, "龙息箱", nearbyOnly);
        // 每块地头顶挂自己的作物名（颜色同「种植区域」那一项）
        for (StardewRegionManager.Region region : currentDimensionRegions()) {
            if (!nearPlayer(nearbyOnly, region)) continue;
            renderRegionLabel(renderer, region);
        }
    }

    /**
     * 预览层用：这一点是否在玩家附近（模块自己的层不做裁剪，一律返回 true）。
     *
     * <p>方块按格中心比三维距离；区域另走 {@link #nearPlayer(boolean, StardewRegionManager.Region)}
     * ——它只比水平距离，高度常与玩家不在同一层。</p>
     */
    private boolean nearPlayer(boolean nearbyOnly, double x, double y, double z) {
        if (!nearbyOnly || mc.player == null) return true;
        return mc.player.distanceToSqr(x, y, z) <= (double) PREVIEW_RADIUS * PREVIEW_RADIUS;
    }

    /**
     * 预览层用：这块地是否在玩家附近。
     *
     * <p>比的是「玩家到矩形最近一格」的水平距离：站在地里或贴着地边都算在附近，
     * 这样走进农田时框不会忽隐忽现（只比 XZ 与区域成员判定同口径）。</p>
     */
    private boolean nearPlayer(boolean nearbyOnly, StardewRegionManager.Region region) {
        if (!nearbyOnly || mc.player == null) return true;
        double x = clamp(mc.player.getX(), region.minX(), region.maxX() + 1.0);
        double z = clamp(mc.player.getZ(), region.minZ(), region.maxZ() + 1.0);
        double dx = mc.player.getX() - x;
        double dz = mc.player.getZ() - z;
        return dx * dx + dz * dz <= (double) PREVIEW_RADIUS * PREVIEW_RADIUS;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 当前维度内的种植区域 */
    private List<StardewRegionManager.Region> currentDimensionRegions() {
        String dimension = StardewContext.dimension();
        List<StardewRegionManager.Region> result = new ArrayList<>();
        for (StardewRegionManager.Region region : regionManager.all()) {
            if (region.dimension() == null || region.dimension().equals(dimension)) result.add(region);
        }
        return result;
    }

    /**
     * 区域字牌：挂在区域矩形中心上方，文字是「区域 N · 作物 · 维度」。
     *
     * <p><b>不加距离</b>（用户 2026-09-19 定稿：「星露谷农场的那个区域选点不要加距离，保持之前的就好了」）：
     * 它是「这块地是什么」的标识，不是可传送 / 可跑过去的点位，距离没有意义；
     * 点位字牌（种子箱 / 成品箱 / 补水点 / 岩浆箱 / 龙息箱）才写「[世界]名字[距离]」。</p>
     */
    private void renderRegionLabel(EspRenderer renderer, StardewRegionManager.Region region) {
        EspColor color = renderRegions.color();
        double centerX = (region.minX() + region.maxX()) / 2.0 + 0.5;
        double centerZ = (region.minZ() + region.maxZ()) / 2.0 + 0.5;
        // 颜色跟随「种植区域」，但透明度用满：那一项默认是半透明的方框色，照搬会把字也画成半透明
        // （实机反馈「颜色太不明显」）；高度抬到框底上方 2.6 格，不再贴着地面（实机反馈「太低了」）。
        // 维度直接读在这块地自己的档上：区域按维度划分，站着看不出这块地属于哪个维度（实机反馈）。
        renderer.text("区域 " + region.index() + " · " + region.cropName()
                + " · " + WorldIdentity.dimensionDisplayName(region.dimension()),
            centerX, regionFrameY(region) + 2.6, centerZ, settings.labelSize, color, 1.0f, true);
    }

    /**
     * 区域框画在哪一层：把两个角都归一到种植盆那一层，取较低的一个。
     *
     * <p><b>为什么不在建区时就固化好：</b>现在选区已经归一（见 {@code StardewRegionSelector}），
     * 但此前落盘的老档是「准星打中作物那一格」记下的高度，框会整体浮高一格、与洒水器覆盖框
     * 共用同一个 Y 平面互相重叠（实机反馈）。写盘的数据不动，这里读取时归一，老档自动归位。</p>
     */
    private int regionFrameY(StardewRegionManager.Region region) {
        return Math.min(
            StardewFarmScanner.normalizeToPot(new BlockPos(region.x1(), region.y1(), region.z1())).getY(),
            StardewFarmScanner.normalizeToPot(new BlockPos(region.x2(), region.y2(), region.z2())).getY());
    }

    /**
     * 点位字牌：挂在点位方块上方，并<b>水平居中于实际画出来的方框</b>。
     *
     * <p>大箱子（相邻两格同一套方块）画的是两格并集的外框，字牌若还挂在「点位绑定的那一格」正上方，
     * 就会偏向一侧——实机表现就是「字牌没居中」。这里按同一个并集算中心，字牌正对框中心。</p>
     *
     * <p>内容与样式按用户 2026-09-19 的口径：一律写「[世界]名字[距离]」，由 {@link PointLabelText}
     * 统一加粗、取 UI 主题色、带底板（唯一不写维度与距离的是自动农场那两个选点角，不在本类）。</p>
     */
    private void renderLabel(EspRenderer renderer, StardewPointType type, String text, boolean nearbyOnly) {
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p == null || !p.inCurrentDimension()) return;
        if (!nearPlayer(nearbyOnly, p.pos().getX() + 0.5, p.pos().getY() + 0.5, p.pos().getZ() + 0.5)) return;
        double centerX = p.pos().getX() + 0.5;
        double centerZ = p.pos().getZ() + 0.5;
        BlockPos half = connectedChestHalf(p.pos(), type);
        if (half != null) {
            AABB box = unionBox(p.pos(), half);
            centerX = (box.minX + box.maxX) * 0.5;
            centerZ = (box.minZ + box.maxZ) * 0.5;
        }
        // 字牌样式统一走 PointLabelText（加粗 + UI 主题色 + 底板 + 居中），内容一律「[世界]名字[距离]」
        // —— 用户 2026-09-19 定稿：「全都要标上，除了那两个农场的选点区域之外都要标上」，
        // 星露谷这边没有例外项（种植区域字牌另算，它本来就不写距离）
        double labelY = p.pos().getY() + 1.4;
        PointLabelText.containerLabel(renderer, text, p.dimension(), centerX, labelY, centerZ,
            settings.labelSize);
    }

    /** 本维度的已绑定洒水器点位（预览层只取玩家附近那些） */
    private List<BlockPos> boundSprinklers(boolean nearbyOnly) {
        List<BlockPos> sprinklers = new ArrayList<>();
        for (StardewPointManager.StardewPoint p : pointManager.getAll(StardewPointType.SPRINKLER)) {
            if (!p.inCurrentDimension()) continue;
            if (!nearPlayer(nearbyOnly, p.pos().getX() + 0.5, p.pos().getY() + 0.5, p.pos().getZ() + 0.5)) continue;
            sprinklers.add(p.pos());
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

    /** 画单个点位的方块框（独立开关 / 颜色 / 模式，仅本维度已绑定点位；预览层只画玩家附近的） */
    private void renderPointBox(EspRenderer renderer, StardewPointType type, RenderOption option,
                                boolean nearbyOnly) {
        if (!option.on()) return;
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p == null || !p.inCurrentDimension()) return;
        if (!nearPlayer(nearbyOnly, p.pos().getX() + 0.5, p.pos().getY() + 0.5, p.pos().getZ() + 0.5)) return;
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

    /** 大箱子（种子箱 / 成品箱 / 岩浆箱 / 龙息箱）是大箱子时返回另一半的坐标；其余点位或单箱返回 {@code null} */
    private BlockPos connectedChestHalf(BlockPos pos, StardewPointType type) {
        if (type != StardewPointType.SEED_BOX && type != StardewPointType.OUTPUT_BOX
            && type != StardewPointType.LAVA_BOX && type != StardewPointType.BREATH_BOX) return null;
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

    /** 画区域外框（min/max 是包含端点的对角格） */
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
