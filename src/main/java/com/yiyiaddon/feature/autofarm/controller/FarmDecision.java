package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.feature.autofarm.scan.FarmScanner;
import com.yiyiaddon.feature.autofarm.task.FarmTask;
import com.yiyiaddon.feature.autofarm.task.PoisonDumpTask;
import com.yiyiaddon.feature.autofarm.task.RestockTask;
import com.yiyiaddon.feature.autofarm.task.UnloadTask;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 决策器：只在 currentTask 为空时被调用，决定「下一步做什么」。
 *
 * 职责边界：决策器只产出「任务」或「目标清单」，绝不直接执行 breakBlock / placeBlock / clickContainer。
 * 实际执行全部由 Task 层负责。
 *
 * 物流（毒马铃薯 / 补货 / 卸货）优先级最高，且会打断并丢弃当前批量计划；
 * 收割目标选择支持单个 / 批量两种模式，补种兜底处理遗留空地。
 */
public final class FarmDecision {

    private final FarmScanner scanner;
    private final FarmResourceManager resources;
    private final FarmObserver observer;
    private final FarmVerifier verifier;
    private final ContainerService broker;

    /** 运行期可变配置，由模块每 tick 同步最新设置值 */
    private int poisonUnloadThreshold;
    private int bpt;
    private double reachDistance;

    /** 收割模式，随设置热更新 */
    private HarvestMode mode = HarvestMode.SINGLE;

    /** 补种模式，随设置热更新 */
    private PlantMode plantMode = PlantMode.SEQUENTIAL;
    /** 均匀轮转模式的游标，指向下一个优先补种的启用作物 */
    private int rotateCursor = 0;
    /** 自动锄地开关，随设置热更新 */
    private boolean tillEnabled = true;
    /** 作物箱被漏斗吸空等场景下暂时跳过补货的作物，等种子重新补足后自动解除 */
    private final Set<CropProfile> restockSuppressed = EnumSet.noneOf(CropProfile.class);

    public FarmDecision(FarmScanner scanner, FarmResourceManager resources, FarmObserver observer,
                        FarmVerifier verifier, ContainerService broker,
                        int poisonUnloadThreshold, int bpt, double reachDistance) {
        this.scanner = scanner;
        this.resources = resources;
        this.observer = observer;
        this.verifier = verifier;
        this.broker = broker;
        this.poisonUnloadThreshold = poisonUnloadThreshold;
        this.bpt = bpt;
        this.reachDistance = reachDistance;
    }

    /** 同步最新设置值 */
    public void update(int poisonUnloadThreshold, int bpt, double reachDistance) {
        this.poisonUnloadThreshold = poisonUnloadThreshold;
        this.bpt = bpt;
        this.reachDistance = reachDistance;
    }

    /** 同步收割模式 */
    public void updateMode(HarvestMode mode) {
        this.mode = mode;
    }

    /** 同步补种模式 */
    public void updatePlantMode(PlantMode plantMode) {
        this.plantMode = plantMode;
    }

    /** 同步自动锄地开关 */
    public void updateTill(boolean enabled) {
        this.tillEnabled = enabled;
    }

    /** 收割距离，供 Controller 在任务衔接时复用 */
    public double reachDistance() {
        return reachDistance;
    }

    /**
     * 物流决策：毒马铃薯 → 补货 → 卸货。
     * 返回非 null 表示需要执行物流任务，Controller 应丢弃当前批量计划。
     */
    public FarmTask decideLogistics(Map<SiteType, FarmSite> sites) {
        // 1. 杂物处理（毒马铃薯 + 仙人掌花，独立，最高优先级）：攒够阈值才卸，避免捡一个就跑一次
        if (resources.countJunk() >= poisonUnloadThreshold) {
            FarmSite junk = validSite(sites, SiteType.POISON_STORAGE);
            if (junk != null) {
                return new PoisonDumpTask(junk.pos(), broker, reachDistance, bpt, resources::shouldDepositJunk);
            }
        }

        // 2. 补货：某作物种植材料不足安全库存，按作物类型智能选箱。
        // 作物箱被漏斗吸空（补货返回 CONTAINER_EMPTY）会抑制该作物，先收菜等种子回升再补。
        for (CropProfile crop : scanner.enabledCrops()) {
            if (!resources.needsRestock(crop)) {
                restockSuppressed.remove(crop); // 种子已补足，解除抑制
                continue;
            }
            if (restockSuppressed.contains(crop)) continue; // 箱子空，先跳过补货去收菜
            // 单物品作物（种子==收获物，如马铃薯/胡萝卜/下界疣）补货去单作物箱；双物品作物（小麦/甜菜根）去种子补货箱
            SiteType boxType = crop.plantItem() != crop.harvestItem()
                ? SiteType.SEED_STORAGE : SiteType.SINGLE_STORAGE;
            FarmSite box = validSite(sites, boxType);
            if (box != null) {
                return new RestockTask(box.pos(), broker, reachDistance, resources, crop);
            }
        }

        // 3. 卸货：任一作物产物超过自己的逐作物卸货数量 或 背包快满，按物品去向智能分流三类箱子
        if (resources.hasDepositableSeed()
            || resources.hasDepositableSingle()
            || resources.hasDepositableDual()
            || observer.freeInventorySlots() <= 2) {
            // 双物品作物种子 → 种子补货箱
            if (resources.hasDepositableSeed()) {
                FarmSite seedBox = validSite(sites, SiteType.SEED_STORAGE);
                if (seedBox != null) {
                    return new UnloadTask(seedBox.pos(), broker, reachDistance, bpt, resources::shouldUnloadSeed);
                }
            }
            // 单物品作物收获物 → 单作物箱
            if (resources.hasDepositableSingle()) {
                FarmSite singleBox = validSite(sites, SiteType.SINGLE_STORAGE);
                if (singleBox != null) {
                    return new UnloadTask(singleBox.pos(), broker, reachDistance, bpt, resources::shouldUnloadSingle);
                }
            }
            // 双物品/无种子作物收获物 → 多作物箱
            if (resources.hasDepositableDual()) {
                FarmSite dualBox = validSite(sites, SiteType.MULTI_STORAGE);
                if (dualBox != null) {
                    return new UnloadTask(dualBox.pos(), broker, reachDistance, bpt, resources::shouldUnloadDual);
                }
            }
        }

        return null;
    }

    /** 作物箱无货时抑制该作物补货，避免每 tick 反复补货卡死；种子回升后自动解除 */
    public void suppressRestock(CropProfile crop) {
        restockSuppressed.add(crop);
    }

    /**
     * 收割目标选择：按当前模式返回待处理的成熟目标清单。
     * 单个返回 0~1 个，批量返回全部成熟目标（无数量上限，按距离升序）。
     */
    public List<FarmTarget> selectHarvestTargets() {
        int max = (mode == HarvestMode.BATCH) ? Integer.MAX_VALUE : 1;
        return scanner.nearestHarvests(max);
    }

    /** 补种目标选择：按补种模式为全部空耕地选定作物（材料不足自动过滤） */
    public List<FarmTarget> selectPlantTargets() {
        List<FarmTarget> result = new ArrayList<>();
        for (BlockPos soil : scanner.nearestPlantablePositions(Integer.MAX_VALUE)) {
            FarmTarget target = selectCropFor(soil);
            if (target != null && resources.countItem(target.profile().plantItem()) > 0) {
                result.add(target);
            }
        }
        return result;
    }

    /** 锄地目标选择：自动锄地开启、存在需要耕地的作物且背包有锄头时，取全部草方块/泥土 */
    public List<FarmTarget> selectTillTargets() {
        List<FarmTarget> result = new ArrayList<>();
        if (!tillEnabled || !needsTill() || !hasHoe()) return result;
        for (BlockPos pos : scanner.nearestTillablePositions(Integer.MAX_VALUE)) {
            result.add(FarmTarget.till(pos));
        }
        return result;
    }

    /** 当前是否存在可锄地工作（开启、有需要耕地的作物且有草方块/泥土目标） */
    public boolean tillWorkAvailable() {
        return tillEnabled && needsTill() && scanner.hasTillable();
    }

    /** 背包里是否有任意锄头（任意品质的锄头都能开垦耕地） */
    public boolean hasHoe() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        int size = mc.player.getInventory().getContainerSize();
        for (int i = 0; i < size; i++) {
            if (mc.player.getInventory().getItem(i).getItem() instanceof HoeItem) return true;
        }
        return false;
    }

    /** 按当前补种模式为单个空耕地选定作物 */
    private FarmTarget selectCropFor(BlockPos soil) {
        return switch (plantMode) {
            case SEQUENTIAL -> selectSequential(soil);
            case ROTATE -> selectRotate(soil);
            case FOLLOW_NEARBY -> selectFollowNearby(soil);
        };
    }

    /** 顺序优先：按枚举声明顺序返回第一个能种在该底盘的启用作物 */
    private FarmTarget selectSequential(BlockPos soil) {
        Minecraft mc = Minecraft.getInstance();
        for (CropProfile crop : scanner.enabledCrops()) {
            if (crop.isPlantable(mc.level, soil)) {
                return FarmTarget.plant(crop, soil);
            }
        }
        return null;
    }

    /** 均匀轮转：从轮转游标开始找第一个能种在该底盘的启用作物，种完游标后移保证均衡 */
    private FarmTarget selectRotate(BlockPos soil) {
        Minecraft mc = Minecraft.getInstance();
        List<CropProfile> list = new ArrayList<>(scanner.enabledCrops());
        if (list.isEmpty()) return null;

        // 从游标开始环形找第一个能种的作物，避免某底盘被跳过导致空转
        for (int i = 0; i < list.size(); i++) {
            int index = (rotateCursor + i) % list.size();
            CropProfile crop = list.get(index);
            if (crop.isPlantable(mc.level, soil)) {
                rotateCursor = (index + 1) % list.size();
                return FarmTarget.plant(crop, soil);
            }
        }
        return null;
    }

    /** 就近跟随的搜索半径（水平格数），空耕地周围此范围内有作物则种回同类 */
    private static final int FOLLOW_RADIUS = 4;

    /** 就近跟随：空耕地种回周围已有作物的同类；周围没有则按启用顺序兜底 */
    private FarmTarget selectFollowNearby(BlockPos soil) {
        Minecraft mc = Minecraft.getInstance();
        Set<CropProfile> enabled = scanner.enabledCrops();

        // 统计底盘水平范围内、作物层（底盘上方一格）已有的启用作物类型与数量
        EnumMap<CropProfile, Integer> nearby = new EnumMap<>(CropProfile.class);
        for (int dx = -FOLLOW_RADIUS; dx <= FOLLOW_RADIUS; dx++) {
            for (int dz = -FOLLOW_RADIUS; dz <= FOLLOW_RADIUS; dz++) {
                if (dx == 0 && dz == 0) continue;
                Block block = mc.level.getBlockState(soil.offset(dx, 0, dz).above()).getBlock();
                CropProfile profile = CropProfile.byBlock(block);
                if (profile != null && profile.needsReplant() && enabled.contains(profile)) {
                    nearby.merge(profile, 1, Integer::sum);
                }
            }
        }

        // 附近有作物：优先种数量最多的同类（保持手动规划的混种分区）
        if (!nearby.isEmpty()) {
            CropProfile best = null;
            int bestCount = -1;
            for (Map.Entry<CropProfile, Integer> entry : nearby.entrySet()) {
                if (entry.getValue() > bestCount) {
                    bestCount = entry.getValue();
                    best = entry.getKey();
                }
            }
            if (best != null && best.isPlantable(mc.level, soil)) {
                return FarmTarget.plant(best, soil);
            }
        }

        // 附近无作物：按启用顺序兜底
        return selectSequential(soil);
    }

    /** 取已绑定且位于当前维度、箱子方块仍存在的站点，否则返回 null */
    private FarmSite validSite(Map<SiteType, FarmSite> sites, SiteType type) {
        if (type == null) return null;
        FarmSite site = sites.get(type);
        if (site == null || !site.inCurrentDimension()) return null;
        // 容器类站点：箱子被摧毁（不再是 Container 方块实体）时视为失效，
        // 避免每 tick 都触发物流任务原地寻路失败刷屏
        if (type.requiresContainer()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || !(mc.level.getBlockEntity(site.pos()) instanceof Container)) {
                return null;
            }
        }
        return site;
    }

    /** 是否存在需要耕地底盘的启用作物，没有则锄地无意义（果实/柱状物/下界疣都不需要耕地） */
    private boolean needsTill() {
        for (CropProfile crop : scanner.enabledCrops()) {
            if (crop.soil() == Blocks.FARMLAND) return true;
        }
        return false;
    }
}
