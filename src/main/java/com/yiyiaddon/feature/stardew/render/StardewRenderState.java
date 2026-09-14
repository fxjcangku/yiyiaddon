package com.yiyiaddon.feature.stardew.render;

import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointActions;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
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

    /** 点位字牌字号（GUI 缩放坐标）；旧项目字牌走 Meteor NametagUtils，本项目用 EspRenderer.text 等价绘制 */
    private static final float LABEL_SIZE = 12f;

    private final Minecraft mc = Minecraft.getInstance();
    private final StardewSettings settings;
    private final StardewPointManager pointManager;
    private final StardewResourceIndex index;
    private final StardewCoordinator coordinator;

    /** 农田边界（起点 + 终点围成的立方体） */
    private final RenderOption renderFarmBorder;
    /** 洒水器本体方块 */
    private final RenderOption renderSprinklerBody;
    /** 洒水器覆盖范围（按等级半径推导的方形范围） */
    private final RenderOption renderSprinklerCoverage;
    /** 农田起点 */
    private final RenderOption renderFarmStart;
    /** 农田终点 */
    private final RenderOption renderFarmEnd;
    /** 种子箱 */
    private final RenderOption renderSeedBox;
    /** 成品箱 */
    private final RenderOption renderOutputBox;
    /** 补水点 */
    private final RenderOption renderWaterSource;
    /** 洒水器点位标记 */
    private final RenderOption renderSprinklerPoint;
    /** 点位字牌（2D 文字）：显示开关 + 文字颜色（没有渲染模式） */
    private final RenderOption renderLabels;

    public StardewRenderState(StardewSettings settings, StardewPointManager pointManager,
                              StardewResourceIndex index, StardewCoordinator coordinator) {
        this.settings = settings;
        this.pointManager = pointManager;
        this.index = index;
        this.coordinator = coordinator;

        // ── 渲染显示：所有可配置对象统一归入「点位渲染」，按农田 → 洒水器 → 后勤点位排序。──
        // 每类对象仍然保持独立的显示开关、颜色与渲染模式；这里只调整主页分组与显示顺序。
        renderFarmBorder = renderOption(settings.renderFarmBorder);
        renderFarmStart = renderOption(settings.renderFarmStart);
        renderFarmEnd = renderOption(settings.renderFarmEnd);
        renderSprinklerBody = renderOption(settings.renderSprinklerBody);
        renderSprinklerCoverage = renderOption(settings.renderSprinklerCoverage);
        renderSprinklerPoint = renderOption(settings.renderSprinklerPoint);
        renderSeedBox = renderOption(settings.renderSeedBox);
        renderOutputBox = renderOption(settings.renderOutputBox);
        renderWaterSource = renderOption(settings.renderWaterSource);

        // 点位字牌：只有显示 + 文字颜色，没有渲染模式
        renderLabels = renderOption(settings.renderLabels);
    }

    /**
     * 一类渲染对象的独立配置：显示开关 + 颜色 + 渲染模式。
     *
     * <p><b>为什么必须拆开：</b>以前只有一个全局「渲染模式」，改一次会把农田边界、洒水器本体、
     * 起点、终点、种子箱、成品箱、补水点全部一起改掉。现在每一类各自持有一套
     * {@code 显示 / 颜色 / 模式}，互不影响；字牌只有显示 + 文字颜色。</p>
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
        onRender3D(renderer);
        onRender2D(renderer);
    }

    private void onRender3D(EspRenderer renderer) {
        if (mc.player == null || mc.level == null) return;

        // 农田边界框（需起点 + 终点都绑定）：独立颜色 + 独立渲染模式
        if (renderFarmBorder.on()) {
            StardewPointManager.StardewPoint start = pointManager.get(StardewPointType.START);
            StardewPointManager.StardewPoint end = pointManager.get(StardewPointType.END);
            if (start != null && end != null) {
                BlockPos min = new BlockPos(Math.min(start.x(), end.x()), Math.min(start.y(), end.y()), Math.min(start.z(), end.z()));
                BlockPos max = new BlockPos(Math.max(start.x(), end.x()), Math.max(start.y(), end.y()), Math.max(start.z(), end.z()));
                renderBorder(renderer, min, max, renderFarmBorder.color(), renderFarmBorder.mode());
            }
        }

        // 单点位方框：五类各自独立开关 / 颜色 / 模式，关掉任意一类不影响其它
        renderPointBox(renderer, StardewPointType.START, renderFarmStart);
        renderPointBox(renderer, StardewPointType.END, renderFarmEnd);
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

        // 当前目标：瞬时指示器，不属于上面 10 类可配置对象，固定线框渲染（不再受任何全局模式影响）
        BlockPos target = coordinator.currentTarget();
        if (target != null) {
            renderTarget(renderer, target, new EspColor(0x00FF64, 75), new EspColor(0x00FF64, 30), ShapeMode.Lines);
            renderTargetLine(renderer, target, new EspColor(0x00FF64, 200));
        }
    }

    private void onRender2D(EspRenderer renderer) {
        if (!renderLabels.on()) return;
        EspColor color = renderLabels.color();
        renderLabel(renderer, StardewPointType.SEED_BOX, "§b种子箱", color);
        renderLabel(renderer, StardewPointType.OUTPUT_BOX, "§6成品箱", color);
        renderLabel(renderer, StardewPointType.WATER_SOURCE, "§d补水点", color);
    }

    private void renderLabel(EspRenderer renderer, StardewPointType type, String text, EspColor color) {
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p != null && p.inCurrentDimension()) {
            renderer.text(text, p.pos().getX() + 0.5, p.pos().getY() + 1.4, p.pos().getZ() + 0.5,
                LABEL_SIZE, color, 1f, true);
        }
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
     * 洒水器覆盖半径：按资源包里该洒水器的等级序号（1~4）推导，等级未知时保守取 1。
     *
     * <p>只用于渲染观察，绝不参与任何决策（补水 / 维护判定一律走点位与资源包身份）。</p>
     */
    private int sprinklerRadiusAt(BlockPos pos) {
        StardewPointManager.StardewPoint point = StardewPointActions.findSprinkler(pointManager, pos);
        if (point == null || point.identity() == null) return 1;
        return index.entryByKey(point.identity()) instanceof SprinklerDefinition def
            ? Math.max(1, Math.min(4, def.sprinklerIndex()))
            : 1;
    }

    /** 画单个点位的方块框（独立开关 / 颜色 / 模式，仅本维度已绑定点位） */
    private void renderPointBox(EspRenderer renderer, StardewPointType type, RenderOption option) {
        if (!option.on()) return;
        StardewPointManager.StardewPoint p = pointManager.get(type);
        if (p == null || !p.inCurrentDimension()) return;
        EspColor color = option.color();
        renderer.blockBox(p.pos().getX(), p.pos().getY(), p.pos().getZ(), color.argb(), color.argb(),
            option.mode(), LINE_THICKNESS);
        if (type != StardewPointType.SEED_BOX && type != StardewPointType.OUTPUT_BOX) return;
        BlockState state = mc.level.getBlockState(p.pos());
        if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) return;
        BlockPos connected = p.pos().relative(ChestBlock.getConnectedDirection(state));
        if (mc.level.getBlockState(connected).getBlock() instanceof ChestBlock) {
            renderer.blockBox(connected.getX(), connected.getY(), connected.getZ(), color.argb(), color.argb(),
                option.mode(), LINE_THICKNESS);
        }
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
