package com.yiyiaddon.feature.water;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.water.config.WaterSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;

/**
 * 水源显示：以玩家为中心扫描周围水源，渲染灌溉范围与相邻建议位（D1 连带搬入）。
 *
 * <p>已放水渲染蓝色 9×9 灌溉范围；在已有水源的上下左右显示红色建议框，
 * 提示可放水位置，这些位置的覆盖范围刚好和现有水源相连不重叠。
 * 独立于自动农场模块，不开农场也能单独用。</p>
 *
 * <p><b>用户交互资产（逐字）</b>：模块名 {@code 水源显示}、描述
 * {@code 已放水渲染蓝色 9×9 灌溉范围，在已有水源旁显示红色建议框提示可放水位置。}
 * （旧 {@code WaterESPModule :78-79}）；10 个设置项名称 / 描述 / 默认值逐字
 * （51 号第五节全表）。</p>
 *
 * <p><b>扫描算法逐行照搬</b>（旧 {@code :236-365}）：512 格分帧、只扫「孤立静止水源」、
 * 玩家移动只剪裁缓存不清空、建议点低频重算（区域变化立即重算）。渲染走本项目
 * {@link WorldOverlay}（模块启用注册、关闭注销），无 2D 元素时零 Skija 开销。</p>
 */
public final class WaterESPModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "water";

    /** 播报前缀使用的模块名（与模块中文名一致） */
    public static final String MESSAGE_MODULE = "水源显示";

    /** 图标字形（Material Symbols：water_drop） */
    private static final String ICON = "\uE798";

    /** 每 tick 最多检查多少格，分帧扫描避免掉帧（旧 :35 原值） */
    private static final int BUDGET_PER_TICK = 512;

    /** 建议放水点重算间隔（tick），低频重算避免每帧跑覆盖算法（旧 :38 原值） */
    private static final int SUGGEST_INTERVAL = 40;

    /** 向下扫描的竖直范围，覆盖飞起来 / 站在高处也能看到下方农田的水（旧 :41 原值） */
    private static final int SCAN_DOWN = 12;

    private final WaterSettings settings = new WaterSettings();

    /** 已放水坐标缓存 */
    private final Set<BlockPos> waterBlocks = new HashSet<>();
    /** 建议放水点缓存（在已有水源的上下左右四个方向） */
    private final Set<BlockPos> suggestedSpots = new HashSet<>();

    /** 分帧扫描游标与当前扫描范围 */
    private int cursorX, cursorY, cursorZ;
    private int scanMinX, scanMinY, scanMinZ;
    private int scanMaxX, scanMaxY, scanMaxZ;
    private BlockPos lastCenter;
    private int lastRadius = -1;
    private int suggestTimer;
    /** 本 tick 是否发生了扫描区域重置，用于触发建议点立即重算 */
    private boolean regionChanged;

    private final Minecraft mc = Minecraft.getInstance();

    public WaterESPModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility",
            "已放水渲染蓝色 9×9 灌溉范围，在已有水源旁显示红色建议框提示可放水位置。");
    }

    @Override
    public String name() {
        return "WaterESP";
    }

    @Override
    public String icon() {
        return ICON;
    }

    public WaterSettings settings() {
        return settings;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    /** 模块页：薄壳入口，设置项由控制台承载（10 项逐字，51 号第五节） */
    @Override
    public com.yiyiaddon.ui.page.ModulePage page() {
        return new com.yiyiaddon.feature.water.ui.WaterPage(this);
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        lastCenter = null;
        lastRadius = -1;
        suggestTimer = 0;
        regionChanged = false;
        cursorX = cursorY = cursorZ = 0;
        waterBlocks.clear();
        suggestedSpots.clear();

        // ESP：注册世界渲染层，关闭时注销
        WorldOverlay.register(MODULE_ID, this::renderLayer);
    }

    @Override
    protected void onDisable() {
        WorldOverlay.unregister(MODULE_ID);
    }

    /** 每刻推进：分帧扫描 + 建议点低频重算（旧 onTick :165-178 原样） */
    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;
        scan();
        // 建议点重算：扫描区域变化时立即重算，否则低频重算，避免移动后红色建议框消失闪烁
        if (settings.renderSuggestion) {
            suggestTimer++;
            if (regionChanged || suggestTimer >= SUGGEST_INTERVAL) {
                regionChanged = false;
                suggestTimer = 0;
                computeSuggestions();
            }
        }
    }

    // ── 世界渲染（旧 onRender :180-226 的等价改写，绘制走 EspRenderer） ──

    /** 由 {@link WorldOverlay} 每帧回调（模块启用时注册） */
    private void renderLayer(EspRenderer renderer) {
        if (mc.player == null || mc.level == null) return;

        double renderDist = settings.renderDistance;
        double renderDist2 = renderDist * renderDist;
        double px = mc.player.getX();
        double pz = mc.player.getZ();

        boolean doSource = settings.renderSource;
        boolean doRange = settings.renderRange;
        boolean doSuggestion = settings.renderSuggestion;

        // 已放水：可选小蓝框（水源方块） + 蓝色 9×9 灌溉范围
        if (doSource || doRange) {
            int sourceArgb = settings.sourceColor.argb();
            int rangeArgb = settings.rangeColor.argb();
            com.yiyiaddon.ui.render.world.ShapeMode sourceMode = settings.sourceShapeMode;
            for (BlockPos w : waterBlocks) {
                double dx = w.getX() + 0.5 - px;
                double dz = w.getZ() + 0.5 - pz;
                if (dx * dx + dz * dz > renderDist2) continue;

                if (doSource) {
                    renderer.blockBox(w.getX(), w.getY(), w.getZ(), sourceArgb, sourceArgb, sourceMode, 1f);
                }
                if (doRange) {
                    AABB box = new AABB(
                        w.getX() - 4, w.getY(), w.getZ() - 4,
                        w.getX() + 5, w.getY() + 1, w.getZ() + 5);
                    renderer.box(box, rangeArgb, rangeArgb, com.yiyiaddon.ui.render.world.ShapeMode.Both, 1f);
                }
            }
        }

        // 建议放水点：红色放置框（线框 / 面 / 两者，默认两者）
        if (doSuggestion) {
            int suggestionArgb = settings.suggestionColor.argb();
            var suggestionMode = settings.suggestionShapeMode;
            for (BlockPos spot : suggestedSpots) {
                double dx = spot.getX() + 0.5 - px;
                double dz = spot.getZ() + 0.5 - pz;
                if (dx * dx + dz * dz > renderDist2) continue;
                renderer.blockBox(spot.getX(), spot.getY(), spot.getZ(),
                    suggestionArgb, suggestionArgb, suggestionMode, 1f);
            }
        }
    }

    // ── 分帧扫描（旧 scan :236-298 逐行照搬） ──

    /**
     * 分帧扫描玩家周围「地表水层」的水源。
     *
     * 只扫玩家脚下两层（脚下一格 + 脚下），不扫整条竖直立方体，避免把海底 / 地下
     * 连片水体全部纳入导致渲染卡死；同时只保留四周都是非水源的「孤立灌溉水源」，
     * 过滤掉海、湖、河等连片水体。玩家移动或半径变化时只剪掉移出范围的缓存块，
     * 不清空，避免已放水框一闪一闪。
     */
    private void scan() {
        BlockPos c = mc.player.blockPosition();
        int r = settings.scanRadius;

        boolean moved = lastCenter == null
            || lastRadius != r
            || Math.abs(c.getX() - lastCenter.getX()) >= 2
            || Math.abs(c.getY() - lastCenter.getY()) >= 2
            || Math.abs(c.getZ() - lastCenter.getZ()) >= 2;

        if (moved) {
            lastCenter = c;
            lastRadius = r;

            int newMinX = c.getX() - r;
            int newMaxX = c.getX() + r;
            int newMinZ = c.getZ() - r;
            int newMaxZ = c.getZ() + r;
            // 向下多扫几格，飞起来 / 站在高处也能看到下方农田的水；
            // 连片的海 / 湖 / 河由「孤立水源」过滤兜底，不会渲染卡死
            int newMinY = c.getY() - SCAN_DOWN;
            int newMaxY = c.getY();

            // 只剪掉移出扫描范围的缓存块，不清空，避免玩家移动时已放水框一闪一闪
            if (!waterBlocks.isEmpty()) {
                waterBlocks.removeIf(p ->
                    p.getX() < newMinX || p.getX() > newMaxX
                    || p.getY() < newMinY || p.getY() > newMaxY
                    || p.getZ() < newMinZ || p.getZ() > newMaxZ);
            }

            scanMinX = newMinX;
            scanMinY = newMinY;
            scanMinZ = newMinZ;
            scanMaxX = newMaxX;
            scanMaxY = newMaxY;
            scanMaxZ = newMaxZ;

            // 移动时保留游标进度，只把越界游标钳制回新范围，
            // 避免每次移动都从角落重扫导致「走动时水框消失、停下才出现」
            cursorX = Math.max(scanMinX, Math.min(cursorX, scanMaxX));
            cursorY = Math.max(scanMinY, Math.min(cursorY, scanMaxY));
            cursorZ = Math.max(scanMinZ, Math.min(cursorZ, scanMaxZ));

            // 区域重置后触发建议点立即重算
            regionChanged = true;
        }

        ClientLevel level = mc.level;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int i = 0; i < BUDGET_PER_TICK; i++) {
            cursor.set(cursorX, cursorY, cursorZ);
            BlockState state = level.getBlockState(cursor);
            // 只记录「静止且孤立」的水源：过滤流动水，也过滤海 / 湖 / 河等连片水体
            if (state.is(Blocks.WATER) && state.getFluidState().isSource() && isIsolatedSource(level, cursor)) {
                waterBlocks.add(cursor.immutable());
            } else {
                waterBlocks.remove(cursor);
            }
            advance();
        }
    }

    /** 判断某格是否为静止水源方块 */
    private boolean isSourceWater(ClientLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.WATER) && state.getFluidState().isSource();
    }

    /**
     * 判断该水源是否为「孤立灌溉水源」：四周（东 / 南 / 西 / 北）没有其它静止水源。
     * 用于过滤掉连片的海、湖、河水体，只保留耕地上一格一坑的灌溉水源。
     */
    private boolean isIsolatedSource(ClientLevel level, BlockPos pos) {
        return !isSourceWater(level, pos.offset(1, 0, 0))   // 东
            && !isSourceWater(level, pos.offset(-1, 0, 0))  // 西
            && !isSourceWater(level, pos.offset(0, 0, 1))   // 南
            && !isSourceWater(level, pos.offset(0, 0, -1)); // 北
    }

    // ── 建议点（旧 computeSuggestions :321-352 逐行照搬） ──

    /**
     * 计算建议放水点：在每个已放水源的上下左右四个方向（水平距离 9 格，刚好不重叠）
     * 找到可以放水的位置，这些位置的灌溉范围刚好和现有水源相连。
     */
    private void computeSuggestions() {
        suggestedSpots.clear();
        if (mc.level == null) return;

        ClientLevel level = mc.level;

        // 对每个已放水源，检查上下左右四个方向的建议位置
        for (BlockPos water : waterBlocks) {
            // 四个方向：X+9, X-9, Z+9, Z-9（水平距离 9 格，覆盖范围刚好相连不重叠）
            BlockPos[] candidates = new BlockPos[] {
                water.offset(9, 0, 0),   // 东
                water.offset(-9, 0, 0),  // 西
                water.offset(0, 0, 9),   // 南
                water.offset(0, 0, -9)   // 北
            };

            for (BlockPos candidate : candidates) {
                // 检查该位置是否已有水源
                if (waterBlocks.contains(candidate)) continue;

                // 检查该位置是否已在建议列表中
                if (suggestedSpots.contains(candidate)) continue;

                // 只要该位置是「地表」（上方是空气/水/可替换）就能挖坑放水；
                // 耕地/泥土/草方块上方是空气，同样显示建议，不再要求该格本身是空气
                BlockState stateAbove = level.getBlockState(candidate.above());
                if (stateAbove.isAir() || stateAbove.is(Blocks.WATER) || stateAbove.canBeReplaced()) {
                    suggestedSpots.add(candidate);
                }
            }
        }
    }

    /** 游标前进一格，越过边界回卷，循环扫描（旧 :355-365 原样） */
    private void advance() {
        cursorX++;
        if (cursorX <= scanMaxX) return;
        cursorX = scanMinX;
        cursorZ++;
        if (cursorZ <= scanMaxZ) return;
        cursorZ = scanMinZ;
        cursorY++;
        if (cursorY <= scanMaxY) return;
        cursorY = scanMinY;
    }
}
