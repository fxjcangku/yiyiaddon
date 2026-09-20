package com.yiyiaddon.feature.stardew.render;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.SprinklerCoverage;
import com.yiyiaddon.feature.stardew.point.StardewPointActions;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewSprinklerRangeStore;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.selector.StardewPreview;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 模型键 → 贴图资源路径的运行期缓存（{@code ""} = 解析不出来）。
     *
     * <p>字牌每帧都画，而「模型键 → 贴图」要读资源包 JSON（见 {@link StardewPreview#textureOf}），
     * 逐帧解析会白白吃掉帧预算；这里只解析一次，之后每帧只是一次查表。</p>
     */
    private final Map<String, String> iconTextures = new HashMap<>();

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
    /** 点位字牌（2D 文字）：只有显示开关，样式（加粗 / 各类方框色 / 底板）统一走 PointLabelText */
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
                SprinklerEspRenderer.renderCoverage(renderer, sprinklers, this::sprinklerCoverageBox, c.argb(), c.argb(),
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
        // 本模块所有世界文字一律加粗（用户 2026-09-19：「所有的点位模块都要字体加粗」）：
        // §l 前缀是唯一做法，MinecraftText 的测量与绘制都认它（居中宽度不会算错）。
        // 预览字牌：它是「临时看范围」的显式动作，不受点位字牌开关约束
        for (StardewPointActions.NearbySprinkler nearby : preview) {
            if (isBound(nearby.pos())) continue;
            // 未绑定的台没有实测可用，用物品说明的真实范围（取不到才退等级估算）——如实标出来源
            String stated = StardewSprinklerRangeStore.sideText(nearby.definition().key());
            int[] range = statedOrLevelRange(nearby.definition().key(), nearby.definition().sprinklerIndex());
            String size = (range[1] - range[0] + 1) + "×" + (range[3] - range[2] + 1);
            BlockPos pos = nearby.pos();
            renderer.text("§l" + nearby.definition().displayName() + " · " + size
                    + "（" + (stated == null ? "估算" : "物品说明") + "）",
                pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5,
                settings.labelSize, PREVIEW_LINE, PREVIEW_LINE.alpha() / 255f, true);
        }
        // 错位字牌：报警类，不受「点位字牌」开关约束，红色不透明，写清这块地应该种什么
        for (BlockPos mismatchPos : coordinator.regionMismatchCells()) {
            StardewRegionManager.Region region = regionManager.at(mismatchPos, StardewContext.dimension());
            String want = region == null ? "本区域作物" : region.cropName();
            renderer.text("§l错位 · 应为 " + want,
                mismatchPos.getX() + 0.5, mismatchPos.getY() + 1.6, mismatchPos.getZ() + 0.5,
                settings.labelSize, MISMATCH_LINE, 1.0f, true);
        }
        if (!renderLabels.on()) return;
        // 字牌颜色跟随各自的方框色（用户 2026-09-21：「不同颜色合理分配」）：
        // 五个点位各传自己的颜色，与「区域字牌跟种植区域色」的口径一致
        renderLabel(renderer, StardewPointType.SEED_BOX, "种子箱", renderSeedBox, nearbyOnly);
        renderLabel(renderer, StardewPointType.OUTPUT_BOX, "成品箱", renderOutputBox, nearbyOnly);
        renderLabel(renderer, StardewPointType.WATER_SOURCE, "补水点", renderWaterSource, nearbyOnly);
        renderLabel(renderer, StardewPointType.LAVA_BOX, "岩浆箱", renderLavaBox, nearbyOnly);
        renderLabel(renderer, StardewPointType.BREATH_BOX, "龙息箱", renderBreathBox, nearbyOnly);
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
     * <p><b>保持原样</b>（用户 2026-09-19 定稿：「星露谷农场的那个区域选点不要加距离，保持之前的就好了」）：
     * 它是「这块地是什么」的标识，不是可传送 / 可跑过去的点位，故既不写成点位那套 {@code [世界]名字}，
     * 也不带距离；点位字牌（种子箱 / 成品箱 / 补水点 / 岩浆箱 / 龙息箱）才走 {@link PointLabelText}。</p>
     */
    private void renderRegionLabel(EspRenderer renderer, StardewRegionManager.Region region) {
        EspColor color = renderRegions.color();
        double centerX = (region.minX() + region.maxX()) / 2.0 + 0.5;
        double centerZ = (region.minZ() + region.maxZ()) / 2.0 + 0.5;
        // 颜色跟随「种植区域」，但透明度用满：那一项默认是半透明的方框色，照搬会把字也画成半透明
        // （实机反馈「颜色太不明显」）；高度抬到框底上方 2.6 格，不再贴着地面（实机反馈「太低了」）。
        // 维度直接读在这块地自己的档上：区域按维度划分，站着看不出这块地属于哪个维度（实机反馈）。
        // 加粗（§l）同本模块其余世界文字（用户 2026-09-19：「所有的点位模块都要字体加粗」）。
        // 作物图标（用户 2026-09-22：「esp 点位也可以加一个农作物吗」）：挂在名字左侧，
        // 「区域 N」与作物名都还在，只是多一张图，作物名字段与图同源（都读 region.cropKey()）。
        CropDefinition crop = index.cropByKey(region.cropKey());
        renderWorldLabel(renderer, "§l区域 " + region.index() + " · " + region.cropName()
                + " · " + WorldIdentity.dimensionDisplayName(region.dimension()),
            crop == null ? null : textureOf(crop.iconModel()),
            centerX, regionFrameY(region) + 2.6, centerZ, settings.labelSize, color.currentRgb());
    }

    /**
     * 世界字牌（带可选图标）：有图标走「图标 + 文字」那份，没有就纯文字。
     *
     * <p>图标取不到不是错误（资源包里没有那张图 / 作物还没识别出来），字牌照画，只是没有图标。</p>
     */
    private void renderWorldLabel(EspRenderer renderer, String text, String iconTexture,
                                  double x, double y, double z, float size, int color) {
        if (iconTexture == null) {
            renderer.text(text, x, y, z, size, color, 1.0f, true);
            return;
        }
        renderer.textWithIcon(text, iconTexture, x, y, z, size, color, 1.0f, true);
    }

    /**
     * 模型键 → 贴图资源路径（带运行期缓存）；解析不出来返回 {@code null}。
     *
     * <p>用空串当「查过了，没有」的哨兵：{@code computeIfAbsent} 的返回值不允许为 {@code null}，
     * 而解析失败是常态（作物没识别出来、资源包里没这张图），必须缓存住这个结论。</p>
     */
    private String textureOf(String modelKey) {
        if (modelKey == null || modelKey.isBlank()) return null;
        String cached = iconTextures.computeIfAbsent(modelKey, key -> {
            String texture = StardewPreview.textureOf(key);
            return texture == null ? "" : texture;
        });
        return cached.isEmpty() ? null : cached;
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
     * <p>内容与样式按用户 2026-09-19 的口径：一律写「[世界]名字」（不带距离），由 {@link PointLabelText}
     * 统一加粗、带底板（唯一不写世界前缀的是自动农场那两个选点角，不在本类）。</p>
     *
     * <p><b>颜色跟随各自的方框色</b>（用户 2026-09-21：「不同颜色合理分配」，并要求补水点为水蓝）：
     * 字牌取 {@code option} 的当前 RGB（含彩虹），与 {@link #renderRegionLabel} 同一条口径——
     * 于是「种子箱绿字 / 成品箱金字 / 补水点水蓝字 / 岩浆箱橙字 / 龙息箱紫字」一眼分得开。
     * 之所以给覆盖色而不跟 UI 主题（旧口径）：三套内置主题的强调色都是蓝，五个字牌全一个颜色，
     * 实机就是用户看到的「怎么都是蓝色」。</p>
     */
    private void renderLabel(EspRenderer renderer, StardewPointType type, String text, RenderOption option,
                             boolean nearbyOnly) {
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
        // 字牌样式统一走 PointLabelText（加粗 + 底板 + 居中），颜色取本点位自己的方框色（含彩虹）
        // —— 用户 2026-09-19 定稿：「全都要标上，除了那两个农场的选点区域之外都要标上」+「距离不要了」，
        // 星露谷这边五个点位没有例外项（种植区域字牌另算，它是「区域 N · 作物 · 维度」，不是点位）
        // 图标（用户 2026-09-22：「esp 点位也可以加一个农作物吗」）：点位类型 → 代表物品 → 贴图，
        // 映射只在 StardewPointType#iconItemId() 里写一次，控制台卡片与这里同源
        double labelY = p.pos().getY() + 1.4;
        PointLabelText.containerLabel(renderer, text, p.dimension(), centerX, labelY, centerZ,
            settings.labelSize, option.color().currentRgb(), textureOf(type.iconItemId()));
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
     * 洒水器覆盖框：<b>物品说明的真实覆盖优先</b>，没学到时用点位实测（下限），两者都没有才退回等级估算的方形。
     *
     * <p>物品说明来自服务器（「工作范围 5 * 5」），真机试验确认过它就是真实覆盖
     * （见 {@link #coverageRange}）；实测数的是湿盆，盆群铺得比能力小就只是下限，
     * 但它能在没有物品说明的服务器上给出实证范围。</p>
     */
    private AABB sprinklerCoverageBox(BlockPos pos) {
        StardewPointManager.StardewPoint point = StardewPointActions.findSprinkler(pointManager, pos);
        String identity = point == null ? null : point.identity();
        int[] range = coverageRange(identity, point == null ? null : point.measuredCoverage());
        if (range != null) {
            return SprinklerEspRenderer.coverageBox(pos, range[0], range[1], range[2], range[3]);
        }
        return SprinklerEspRenderer.coverageBox(pos, sprinklerRadiusAt(pos));
    }

    /**
     * 覆盖范围的<b>定版口径</b>（不含等级兜底），按可信度取第一个有结论的：
     *
     * <ol>
     *   <li><b>物品说明</b>（{@link StardewSprinklerRangeStore}）：服务器自己下发的「工作范围 A * B」，
     *       就是这台洒水器的<b>真实覆盖</b>；</li>
     *   <li><b>点位实测</b>（{@link SprinklerCoverage}）：世界里数湿盆得来的，是<b>下限</b>——
     *       它只能在「盆已经铺到」的地方证明浇到了，盆群比能力小就永远测不出真范围；</li>
     * </ol>
     *
     * <p><b>口径怎么定下来的</b>（真机 2026-09-21 04:2x，用户亲手做试验）：先在 13×13 的边角放盆、
     * 等清晨洒水，<b>远处的盆确实湿了</b> —— 说明「工作范围 13 * 13」是真实覆盖，而实测的
     * {@code 5×5 · 湿盆 24 格} 只是「盆群只有 5×5 大」造成的下限。前后两次口径翻转的教训见
     * {@code 160-复盘}：说明里的数字要先当成<b>待验证的声明</b>，再用一次最小试验定语义，
     * 不要凭「看起来太大」直接否掉。</p>
     *
     * @param identity 洒水器逻辑键（{@code customcrops:sprinkler_1}）；{@code null} = 身份未知
     * @param measured 该点位的实测结论；{@code null} = 没测过 / 换服后已失效
     * @return {@code {dxMin, dxMax, dzMin, dzMax}}（相对洒水器那一格，含 0）；两个来源都没有返回 {@code null}
     */
    public static int[] coverageRange(String identity, SprinklerCoverage measured) {
        int[] stated = StardewSprinklerRangeStore.rangeOf(identity);
        if (stated != null) return stated;
        if (measured != null) {
            return new int[]{measured.dxMin(), measured.dxMax(), measured.dzMin(), measured.dzMax()};
        }
        return null;
    }

    /** 方形范围（物品说明优先、等级兜底）：洒水器预览没有点位，只能用身份级来源 */
    private static int[] statedOrLevelRange(String identity, int level) {
        int[] stated = StardewSprinklerRangeStore.rangeOf(identity);
        if (stated != null) return stated;
        int radius = radiusOfLevel(level);
        return new int[]{-radius, radius, -radius, radius};
    }

    /**
     * 洒水器覆盖半径（等级估算）：初级 5×5 / 中级 9×9 / 高级 13×13（半径 2 / 4 / 6）。
     *
     * <p><b>口径（2026-09-21 用户实机取证，本服「季明月种植」包的物品说明）：</b>
     * 初级「工作范围 5 * 5」、中级「9 * 9」、高级「13 * 13」——边长 = 4n+1，即半径 = 2n。
     * 旧表「1 / 1 / 2 / 3」抄的是《星露谷物语》原版攻略，与本服 customcrops 配置差一圈到三圈，
     * 实机表现就是覆盖框画小了（初级按 3×3 画、实际 5×5），而且实测总「顶到扫描边界」。</p>
     *
     * <p>四级按同一边长等差外推 17×17（半径 8）；本服资源包里没有这一档，等级越界一律夹到端点上，
     * 绝不因为一个脏数据画出一个荒唐的大框。等级未知时取最小的一档（初级 5×5）。</p>
     *
     * <p>只在<b>没有实测结论</b>时使用（见 {@link #sprinklerCoverageBox(BlockPos)}），
     * 以及分区覆盖率统计的兜底；绝不参与任何决策（补水 / 维护判定一律走点位与资源包身份）。</p>
     */
    public static int radiusOfLevel(int level) {
        return switch (Math.max(1, Math.min(4, level))) {
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 6;
            default -> 8;
        };
    }

    /**
     * 某一格已绑定洒水器的覆盖半径（等级估算）：按资源包给出的等级序号查表，认不出身份时取最小的一档。
     *
     * <p>只有<b>没有实测结论</b>的洒水器会走到这里（见 {@link #sprinklerCoverageBox(BlockPos)}）。</p>
     */
    private int sprinklerRadiusAt(BlockPos pos) {
        StardewPointManager.StardewPoint point = StardewPointActions.findSprinkler(pointManager, pos);
        if (point == null || point.identity() == null) return radiusOfLevel(1);
        return index.entryByKey(point.identity()) instanceof SprinklerDefinition def
            ? radiusOfLevel(def.sprinklerIndex())
            : radiusOfLevel(1);
    }

    /**
     * 附近自动预览：以该洒水器为中心，按它的等级半径画一个方形范围框 + 中心格高亮。
     *
     * <p>与已绑定洒水器的覆盖框同一套画法（半径含中心格，即 (2r+1)×(2r+1)），只是换一种颜色；
     * 已绑定的台跳过——它由青色覆盖框负责，同一台画两个框只会互相盖住。</p>
     */
    private void renderNearbyPreview(EspRenderer renderer, StardewPointActions.NearbySprinkler nearby) {
        if (isBound(nearby.pos())) return;
        int[] range = statedOrLevelRange(nearby.definition().key(), nearby.definition().sprinklerIndex());
        BlockPos pos = nearby.pos();
        renderer.box(SprinklerEspRenderer.coverageBox(pos, range[0], range[1], range[2], range[3]),
            PREVIEW_SIDE, PREVIEW_LINE, ShapeMode.Lines, LINE_THICKNESS);
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
