package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.PotDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.CENTER_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.CONTAINER_STAND_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.NAVIGATION_BLOCK_RETRY_MS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.NAVIGATION_NO_PROGRESS_TICKS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.NAVIGATION_PROGRESS_SQ;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.NAVIGATION_TIMEOUT_TICKS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.PLAYER_LOW_FREE_SLOTS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.PLAYER_MOVE_EPSILON_SQ;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.learningKey;

/**
 * 星露谷任务规划：任务选择 / 目标格子推导 / 优先级判定 / 后勤需求判定 / 导航退避。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），不持有自己的决策状态，行为与拆分前完全一致。</p>
 */
final class StardewTaskPlanner {

    private final StardewCoordinator owner;

    StardewTaskPlanner(StardewCoordinator owner) {
        this.owner = owner;
    }

    /** 从当前快照选择任务；第一遍排除空盆，第二遍只允许空盆。 */
    boolean startPendingTask(boolean emptyOnly) {
        for (StardewFarmScanner.Cell cell : owner.pending) {
            boolean empty = cell.crop().state() == CropState.EMPTY;
            if (emptyOnly != empty) continue;
            TaskType resolved = resolveTask(cell);
            if (resolved == null) continue;
            owner.activeCell = cell;
            owner.targetPot = cell.potPos();
            owner.taskType = resolved;
            BlockPos navigationTarget = taskTarget();
            if (isNavigationBlocked(owner.taskType, navigationTarget)) {
                owner.activeCell = null;
                owner.targetPot = null;
                owner.taskType = null;
                continue;
            }
            owner.retryCount = 0;
            owner.taskStep = 0;
            owner.taskTicks = 0;
            resetNavigationWatchdog();
            owner.reporter.announceTaskStart();
            return true;
        }
        return false;
    }

    /** 只把已选盆上的已选生长作物计入“等待成熟”，避免空盆或未知资源误报。 */
    boolean isManagedGrowingCell(StardewFarmScanner.Cell cell) {
        return cell != null && cell.crop().state() == CropState.GROWING
            && cell.crop().cropKey() != null && cropIsManaged(cell.potPos(), cell.crop().cropKey())
            && potMatchesSelection(cell);
    }

    /**
     * 这一格上的作物是不是「本格该维护的作物」。
     *
     * <p><b>分区模式（实验）：</b>必须是本格所属区域的绑定作物；未分区格子一律不管。
     * 区域绑定的作物一旦被取消勾选，该区域整体失效跳过，重新勾选自动恢复（数据不删）。</p>
     *
     * <p><b>非分区模式：</b>与旧口径逐字一致——只要在全局已勾选列表里就算。</p>
     */
    boolean cropIsManaged(BlockPos potPos, String cropKey) {
        if (cropKey == null || !owner.selectedCropKeys.contains(cropKey)) return false;
        if (!owner.regionModeActive()) return true;
        StardewRegionManager.Region region = owner.regionAt(potPos);
        return region != null && cropKey.equals(region.cropKey());
    }

    /**
     * 分区模式下这一格该种什么：区域绑定的作物。
     *
     * <p>区域必须仍被勾选、且该作物在当前资源索引里存在，否则这一格视为「无目标」（跳过，不播种）。
     * 非分区模式返回 {@code null}，调用方走旧的记忆 / 全局勾选口径。</p>
     */
    private String regionCropFor(BlockPos potPos) {
        if (!owner.regionModeActive()) return null;
        StardewRegionManager.Region region = owner.regionAt(potPos);
        if (region == null) return null;
        if (!owner.selectedCropKeys.contains(region.cropKey())) return null;
        return owner.index != null && owner.index.cropByKey(region.cropKey()) != null ? region.cropKey() : null;
    }

    /**
     * 分区模式下「某块区域里种了别的作物」的冲突详情。
     *
     * <p>逐条给出玩家能直接走过去处理的证据：区域号、该区绑定作物、实际作物、坐标。
     * 未分区格子不参与判定（那些格子不管，种什么都不拦）。最多列三处，其余折叠成「等 N 处」。</p>
     */
    List<String> regionMismatchedCrops() {
        List<String> details = new ArrayList<>();
        int total = 0;
        for (StardewFarmScanner.Cell cell : owner.pending) {
            String key = cell.crop().cropKey();
            if (key == null || cell.crop().state() == CropState.UNKNOWN) continue;
            StardewRegionManager.Region region = owner.regionAt(cell.potPos());
            if (region == null || key.equals(region.cropKey())) continue;
            total++;
            if (details.size() >= 3) continue;
            CropDefinition crop = owner.index == null ? null : owner.index.cropByKey(key);
            String actual = crop == null ? key : crop.chineseName();
            BlockPos pos = cell.potPos();
            details.add("区域 " + region.index() + "（" + region.cropName() + "）实际 " + actual
                + " · X" + pos.getX() + " Y" + pos.getY() + " Z" + pos.getZ());
        }
        if (total > details.size()) details.add("等 " + total + " 处");
        return details;
    }

    /**
     * 田里是否「整片都不是已选作物」。
     *
     * <p><b>为什么需要它：</b>{@link #resolveTask} 对「作物不在已选列表」的格子一律返回 null
     * （旧项目同一口径），于是整片田都种着别的作物时，模块既不收也不浇，直接回中心站着，
     * 玩家只会以为模块卡死。这里把这种情况认出来，交给协调器报错停机。</p>
     *
     * <p><b>判据（玩家口径）：</b>本轮快照里种着东西的格子非空，且没有一格的作物在已选列表里。
     * 只要有一格是已选作物就不算冲突——田里混着自己要种的作物时模块还有活干，绝不能停机。</p>
     *
     * @return 冲突作物的中文名（去重、按扫描顺序）；无冲突返回空表
     */
    List<String> mismatchedCrops() {
        List<String> plantedKeys = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            String key = cell.crop().cropKey();
            if (key == null || cell.crop().state() == CropState.UNKNOWN) continue;
            if (owner.selectedCropKeys.contains(key)) return List.of();
            if (plantedKeys.contains(key)) continue;
            plantedKeys.add(key);
            CropDefinition crop = owner.index == null ? null : owner.index.cropByKey(key);
            names.add(crop == null ? key : crop.chineseName());
        }
        return names;
    }

    /** 导航必须持续缩短距离；Baritone 有路径线但原地闪烁同样属于无进展。 */
    void updateNavigationWatchdog(BlockPos target) {
        Minecraft mc = Minecraft.getInstance();
        owner.navigationTicks++;
        if (mc.player == null) return;
        double dx = target.getX() + 0.5 - mc.player.getX();
        double dy = target.getY() + 0.5 - mc.player.getY();
        double dz = target.getZ() + 0.5 - mc.player.getZ();
        double distanceSq = dx * dx + dy * dy + dz * dz;
        if (distanceSq + NAVIGATION_PROGRESS_SQ < owner.navigationBestDistanceSq) {
            owner.navigationBestDistanceSq = distanceSq;
            owner.navigationNoProgressTicks = 0;
        } else {
            owner.navigationNoProgressTicks++;
        }
    }

    /** 每个新任务和每次重试独立计时，不能沿用上一条路径的停滞数据。 */
    void resetNavigationWatchdog() {
        owner.navigationTicks = 0;
        owner.navigationNoProgressTicks = 0;
        owner.navigationBestDistanceSq = Double.MAX_VALUE;
    }

    /** 失败目标短暂退避，让状态机继续处理其它格，禁止围着同一条不可达路径闪烁。 */
    void blockNavigationTarget(TaskType type, BlockPos target) {
        if (type == null || target == null) return;
        owner.navigationBlockedUntil.put(navigationKey(type, target),
            System.currentTimeMillis() + NAVIGATION_BLOCK_RETRY_MS);
    }

    /** 退避到期自动释放，避免一次临时碰撞永久屏蔽农田任务。 */
    boolean isNavigationBlocked(TaskType type, BlockPos target) {
        if (type == null || target == null) return false;
        String key = navigationKey(type, target);
        Long until = owner.navigationBlockedUntil.get(key);
        if (until == null) return false;
        if (System.currentTimeMillis() >= until) {
            owner.navigationBlockedUntil.remove(key);
            return false;
        }
        return true;
    }

    /** 任务类型参与键值，防止同一坐标的浇水失败误伤收割或拾取。 */
    private static String navigationKey(TaskType type, BlockPos target) {
        return type.name() + ":" + target.getX() + ":" + target.getY() + ":" + target.getZ();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  决策辅助
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    int priority(StardewFarmScanner.Cell cell) {
        CropState s = cell.crop().state();
        return switch (s) {
            case GROWING -> (owner.wateringEnabled && cell.potState() == PotState.DRY) ? 0
                : (shouldProbeLearning(cell) ? 1 : 90);
            case MATURE, SPECIAL -> 1;
            case DEAD -> 2;
            case EMPTY -> 3;
            default -> 90;
        };
    }

    TaskType resolveTask(StardewFarmScanner.Cell cell) {
        if (!potMatchesSelection(cell)) return null;
        // 分区模式：未分区格子一律不管（不种 / 不收 / 不浇 / 不画），也不参与任何报错
        if (owner.regionModeActive() && owner.regionAt(cell.potPos()) == null) return null;
        if (cell.crop().cropKey() != null && !cropIsManaged(cell.potPos(), cell.crop().cropKey())) {
            // 没选中的作物：旧口径只放行「给干盆浇水 / 补水」，收割 / 清理 / 播种 / 施肥 / 学习一律不做。
            // 分区模式不替别人浇：本区种了别的作物交给停机报错，未分区的格子上一步已经跳过。
            return owner.regionModeActive() ? null : resolveUnselectedWatering(cell);
        }
        CropState state = cell.crop().state();
        return switch (state) {
            // 普通成熟作物执行右键采摘；特殊变种必须等独立工具规则确认后再处理。
            case MATURE -> needsHarvestLearning(cell.crop().cropKey())
                ? (canProbeHarvestLearning(cell) ? TaskType.LEARN_HARVEST : null)
                : TaskType.HARVEST;
            // 特殊变种需要独立工具策略；未建立可靠规则前绝不按普通作物破坏。
            case SPECIAL -> {
                notifySpecialCalibration(cell.crop());
                yield null;
            }
            case DEAD -> TaskType.CLEAR_DEAD;
            case GROWING -> resolveGrowing(cell);
            case EMPTY -> resolveEmptyPot(cell);
            default -> null;
        };
    }

    private TaskType resolveGrowing(StardewFarmScanner.Cell cell) {
        if (owner.wateringEnabled && cell.potState() == PotState.DRY) {
            // 水壶已明确为空 → 先补水；未知 / 有水量 → 直接浇水
            if (owner.forceRefill || owner.executor.canWaterIsEmpty()) return TaskType.REFILL;
            if (owner.executor.canUsable()) return TaskType.WATER;
        }
        if (shouldProbeLearning(cell)) return TaskType.LEARN_HARVEST;
        return null;
    }

    /**
     * 未选作物的格子：只放行「浇水 / 补水」。
     *
     * <p><b>为什么要放行浇水：</b>浇水是纯增益动作——浇到没选中的作物上没有任何副作用，而盆一干作物就
     * 枯死。实机反馈：田里混种两种作物、只选了一种时，另一种的干盆一直不浇水。其余动作仍要求先选中
     * 该作物，避免误收、误清、误施肥。</p>
     *
     * <p>注意「整片田都不是已选作物」不走这里：那种情况由 {@link #mismatchedCrops()} 报错停机，
     * 玩家口径是「先收获或先把它选进列表」，不是替他把别人的作物浇上。</p>
     */
    private TaskType resolveUnselectedWatering(StardewFarmScanner.Cell cell) {
        if (!owner.wateringEnabled || cell.potState() != PotState.DRY) return null;
        // 与已选作物同一口径：水壶明确为空先补水，未知 / 有水直接浇
        if (owner.forceRefill || owner.executor.canWaterIsEmpty()) return TaskType.REFILL;
        return owner.executor.canUsable() ? TaskType.WATER : null;
    }

    /** 有成熟阶段但生命周期未验证，或完全没有规则时，都需要低风险学习。 */
    private boolean needsHarvestLearning(String cropKey) {
        StardewHarvestRule rule = owner.harvestRuleResolver.apply(cropKey);
        return rule == null || !rule.completeVerified();
    }

    /** 成熟已明确但动作/生命周期未 VERIFIED 时，允许一次空手右键试探并保证会话内收敛。 */
    private boolean canProbeHarvestLearning(StardewFarmScanner.Cell cell) {
        return cell != null && cell.crop() != null && cell.crop().cropKey() != null
            && cell.crop().stageName() != null
            && !owner.probedLearningStages.contains(learningKey(cell.crop().cropKey(), cell.crop().stageName()));
    }

    /** 未知成熟阶段只在当前会话首次看到该具体阶段时探测一次。 */
    private boolean shouldProbeLearning(StardewFarmScanner.Cell cell) {
        if (cell == null || cell.crop() == null || cell.crop().cropKey() == null
            || cell.crop().stageName() == null) return false;
        StardewHarvestRule rule = owner.harvestRuleResolver.apply(cell.crop().cropKey());
        if (rule != null && rule.hasMatureStage()) return false;
        return !owner.probedLearningStages.contains(learningKey(cell.crop().cropKey(), cell.crop().stageName()));
    }

    /** 特殊变种缺少低风险可确认规则时只提示一次，绝不自动回退左键。 */
    private void notifySpecialCalibration(CropRecognizer.CropRecognition crop) {
        String key = learningKey(String.valueOf(crop.cropKey()), String.valueOf(crop.stageName()));
        if (!owner.reportedSpecialStages.add(key)) return;
        owner.status.state("SPECIAL:" + key, "发现特殊作物", "收割动作尚未确认，已安全跳过");
    }

    /**
     * 分区模式：这一格所属区域缺对应种子时，只提示一次并跳过该区。
     *
     * <p>口径来自定稿规格：缺种子不拦模块启动、不停机、更不会用别的种子顶替；
     * 同一条（同一区域）只报一次，避免每轮扫描都刷屏。</p>
     */
    private void announceRegionSeedShortage(BlockPos potPos, CropDefinition crop) {
        if (crop == null || !owner.regionModeActive()) return;
        StardewRegionManager.Region region = owner.regionAt(potPos);
        if (region == null || !owner.announcedRegionSeedShortage.add(region.index())) return;
        owner.status.state("REGION_SEED:" + region.index(),
            "区域 " + region.index() + "（" + crop.chineseName() + "）缺种子", "本轮跳过该区域");
    }

    private TaskType resolveEmptyPot(StardewFarmScanner.Cell cell) {
        if (!hasPlantTarget(cell.potPos())) return null;
        CropDefinition crop = cropOf(cell.potPos());
        if (crop == null) return null;
        ItemStack seedStack = owner.inventory.findSeedStack(crop);
        StardewSeasonService.PlantingStatus seasonStatus = owner.seasonService.plantingStatus(crop, seedStack);
        if (seasonStatus == StardewSeasonService.PlantingStatus.DISALLOWED) {
            // 作物级阻塞：只暂停该 cropKey 的 Plant（其它作物与其它任务照常）。
            // 同一作物 + 同一季节 + 同一原因只播报一次，绝不刷屏。
            String label = owner.reporter.seasonPlayerLabel();
            owner.seasonBlockedCrops.add(crop.cropKey());
            if (owner.announcedSeasonBlocks.add(crop.cropKey() + '\u0000' + label)) {
                owner.status.seasonBlocked(owner.reporter.seasonKey("限制", crop.cropKey(), label), crop.chineseName(), label);
            }
            return null;
        }
        // 季节合法性高于一切空盆动作；通过后，干盆必须先完成 WATER，再进入种子检查。
        if (owner.wateringEnabled && cell.potState() == PotState.DRY) {
            if (owner.forceRefill || owner.executor.canWaterIsEmpty()) return TaskType.REFILL;
            if (owner.executor.canUsable()) return TaskType.WATER;
        }
        if (owner.inventory.countSeed(crop) <= 0) {
            // 分区模式：缺种子只跳过这一块地并提示一次，不拦其它区域、也不停机（绝不用别的种子顶替）
            announceRegionSeedShortage(cell.potPos(), crop);
            return null;
        }
        // 自动施肥开启且有肥料可选，且该盆被选中类型允许施肥时，先施肥后播种
        if (owner.autoFertilize && owner.executor.hasSelectedFertilizer() && potMatchesSelection(cell)) return TaskType.FERTILIZE;
        return TaskType.PLANT;
    }

    boolean potMatchesSelection(StardewFarmScanner.Cell cell) {
        if (owner.selectedPotKeys.isEmpty()) return true;
        String potKey = cell.potKey();
        if (potKey == null) return false;
        // 已选「普通/下界/末地」逻辑盆型同时认识 DRY 与 WET 世界状态，命中任一即允许管理
        for (String selected : owner.selectedPotKeys) {
            StardewToolDefinition entry = owner.index.entryByKey(selected);
            if (entry instanceof PotDefinition pot && pot.matchesPotKey(potKey)) return true;
        }
        return false;
    }

    private boolean hasPlantTarget(BlockPos potPos) {
        if (owner.regionModeActive()) return regionCropFor(potPos) != null;
        var cell = owner.memory.get(owner.serverKey, owner.dimension, potPos);
        if (cell.hasTarget()) return owner.selectedCropKeys.contains(cell.cropKey()) && owner.index.cropByKey(cell.cropKey()) != null;
        return !owner.selectedCropKeys.isEmpty();
    }

    private String plantCropKey(BlockPos potPos) {
        if (owner.regionModeActive()) return regionCropFor(potPos);
        var cell = owner.memory.get(owner.serverKey, owner.dimension, potPos);
        if (cell.hasTarget()) return owner.selectedCropKeys.contains(cell.cropKey()) && owner.index.cropByKey(cell.cropKey()) != null ? cell.cropKey() : null;
        for (String key : owner.selectedCropKeys) {
            if (plannedCellCount(key) < cropActualTarget(key)) return key;
        }
        return null;
    }

    /** 已有作物与明确单盆目标共同占用该 cropKey 的计划数量。 */
    private int plannedCellCount(String cropKey) {
        int count = 0;
        Set<BlockPos> counted = new HashSet<>();
        for (StardewFarmScanner.Cell scanned : owner.pending) {
            if (cropKey.equals(scanned.crop().cropKey())) {
                count++;
                counted.add(scanned.potPos());
                continue;
            }
            var remembered = owner.memory.get(owner.serverKey, owner.dimension, scanned.potPos());
            if (remembered.hasTarget() && cropKey.equals(remembered.cropKey()) && counted.add(scanned.potPos())) count++;
        }
        return count;
    }

    CropDefinition cropOf(BlockPos potPos) {
        String key = plantCropKey(potPos);
        return key == null ? null : owner.index.cropByKey(key);
    }

    /**
     * 从当前服务器真实方块状态建立可通行载体集合。
     *
     * <p>资源参考表明作物、种植盆和洒水器载体可能是绊线、craftengine 自定义块、树叶或蘑菇柄；
     * 只有同时满足“已识别为当前农田资源”与“真实碰撞为空”才允许从 Baritone 避让表移除，
     * 实体碰撞块仍保留。</p>
     */
    void refreshPassableFarmCarriers() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Set<Block> carriers = new HashSet<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            BlockState potState = mc.level.getBlockState(cell.potPos());
            if ((cell.potState() == PotState.DRY || cell.potState() == PotState.WET)
                && potState.getCollisionShape(mc.level, cell.potPos()).isEmpty()) {
                carriers.add(potState.getBlock());
            }
            BlockPos cropPos = cell.cropPos();
            BlockState cropState = mc.level.getBlockState(cropPos);
            if (cell.crop().state() != CropState.EMPTY && cell.crop().state() != CropState.UNKNOWN
                && cropState.getCollisionShape(mc.level, cropPos).isEmpty()) {
                carriers.add(cropState.getBlock());
            }
        }
        for (StardewPointManager.StardewPoint sprinkler : owner.points.getAll(StardewPointType.SPRINKLER)) {
            if (!sprinkler.inCurrentDimension()
                || owner.points.validationFailure(StardewPointType.SPRINKLER, sprinkler, owner.index) != null) continue;
            BlockState state = mc.level.getBlockState(sprinkler.pos());
            if (state.getCollisionShape(mc.level, sprinkler.pos()).isEmpty()) carriers.add(state.getBlock());
        }
        owner.adapter.updatePassableCarriers(carriers);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  任务目标与后勤决策
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    BlockPos taskTarget() {
        // 渲染层每帧调用 currentTarget()，任务为空时直接返回 null，避免 switch(null) 抛 NPE
        if (owner.taskType == null) return null;
        return switch (owner.taskType) {
            case RESTOCK, SEED_RETURN, UNLOAD -> owner.containerApproach == null ? null : owner.containerApproach.standPos();
            case REFILL -> {
                StardewPointManager.StardewPoint p = owner.points.get(StardewPointType.WATER_SOURCE);
                yield p == null ? null : p.pos();
            }
            case SPRINKLER_CHECK, SPRINKLER_REFILL -> sprinklerTarget();
            case COLLECT -> owner.drops.nearestFarmItemPos();
            case RETURN_CENTER -> regionCenter();
            default -> owner.targetPot;
        };
    }

    BlockPos sprinklerTarget() {
        List<StardewPointManager.StardewPoint> list = sprinklerPointsInDimension();
        if (list.isEmpty() || owner.sprinklerCursor >= list.size()) return null;
        return list.get(owner.sprinklerCursor).pos();
    }

    List<StardewPointManager.StardewPoint> sprinklerPointsInDimension() {
        List<StardewPointManager.StardewPoint> result = new ArrayList<>();
        for (StardewPointManager.StardewPoint p : owner.points.getAll(StardewPointType.SPRINKLER)) {
            if (p.inCurrentDimension()) result.add(p);
        }
        return result;
    }

    boolean sprinklerDue() {
        long now = System.currentTimeMillis();
        return now >= owner.nextSprinklerCheckTick;
    }

    boolean startSprinklerTask() {
        List<StardewPointManager.StardewPoint> list = sprinklerPointsInDimension();
        if (list.isEmpty()) return false;
        if (owner.sprinklerCursor >= list.size()) owner.sprinklerCursor = 0;
        // 跳过「刚验过是满的」台：满了的洒水器不必每轮都走过去点一下才发现它还是满的。
        // 只推迟不永久跳过——窗口到点后照样重新检查（灌溉会消耗水量）。
        int skipped = 0;
        while (skipped < list.size()) {
            StardewPointManager.StardewPoint candidate = list.get(owner.sprinklerCursor);
            if (!owner.isSprinklerRecentlyFull(candidate.pos())) break;
            owner.reporter.announceSprinklerSkipped(candidate.pos());
            owner.sprinklerCursor = (owner.sprinklerCursor + 1) % list.size();
            skipped++;
        }
        if (skipped >= list.size()) {
            // 这一轮全是刚验过满的：本轮不跑腿，等下一个检查间隔
            owner.nextSprinklerCheckTick = System.currentTimeMillis() + owner.sprinklerInterval * 50L;
            owner.reporter.announceSprinklerAllSkipped(list.size());
            return false;
        }
        if (isNavigationBlocked(TaskType.SPRINKLER_CHECK, list.get(owner.sprinklerCursor).pos())) return false;
        owner.taskType = TaskType.SPRINKLER_CHECK;
        owner.targetPot = null;
        owner.activeCell = null;
        owner.retryCount = 0;
        owner.taskStep = 0;
        owner.reporter.broadcast(TaskType.SPRINKLER_CHECK.cn());
        return true;
    }

    boolean tryReturnCenter() {
        BlockPos center = regionCenter();
        if (center == null) return false;
        if (isNavigationBlocked(TaskType.RETURN_CENTER, center)) return false;
        if (owner.adapter.arrived(center, CENTER_REACH) || owner.stationaryTicks < owner.returnCenterDelayTicks) return false;
        // 只有连续静止达到等待时间且不在中心附近时才接管移动，玩家正常走动期间不抢控制。
        owner.taskType = TaskType.RETURN_CENTER;
        owner.targetPot = null;
        owner.activeCell = null;
        owner.retryCount = 0;
        owner.reporter.announceTaskStart();
        return true;
    }

    /** 以真实坐标变化累计静止时间，避免无任务时立即用 Baritone 抢走玩家移动控制。 */
    void updatePlayerIdle() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            owner.playerPositionKnown = false;
            owner.stationaryTicks = 0;
            return;
        }
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();
        if (!owner.playerPositionKnown) {
            owner.playerPositionKnown = true;
            owner.lastPlayerX = x;
            owner.lastPlayerY = y;
            owner.lastPlayerZ = z;
            owner.stationaryTicks = 0;
            return;
        }
        double dx = x - owner.lastPlayerX;
        double dy = y - owner.lastPlayerY;
        double dz = z - owner.lastPlayerZ;
        if (dx * dx + dy * dy + dz * dz > PLAYER_MOVE_EPSILON_SQ) owner.stationaryTicks = 0;
        else if (owner.stationaryTicks < Integer.MAX_VALUE) owner.stationaryTicks++;
        owner.lastPlayerX = x;
        owner.lastPlayerY = y;
        owner.lastPlayerZ = z;
    }

    boolean tryStartUnloadForProtection() {
        if (owner.logisticsCooldown > 0 || owner.inventory == null) return false;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        // 背包空格已经不多（≤ 6 格）且成品达标时插队卸货，保护掉落。
        // 注意：Inventory#getFreeSlot() 返回的是「第一个空槽的下标」而不是空格数量，
        // 用它判断剩余空间会把保护条件变成「前两格是否占用」，因此这里一律按真实空格计数。
        if (playerFreeSlots() > PLAYER_LOW_FREE_SLOTS) return false;
        StardewPointManager.StardewPoint outputBox = owner.points.get(StardewPointType.OUTPUT_BOX);
        if (outputBox == null || !outputBox.inCurrentDimension()) return false;
        // 只按「已选作物的独立成品数」判断，未选作物一律不参与
        for (CropDefinition crop : targetCrops()) {
            if (owner.snapshot(crop).unloadNeeded() && !owner.logistics.logisticsBackedOff(owner.unloadBlockedUntil, crop.cropKey())) {
                return startLogisticsTask(TaskType.UNLOAD, outputBox, crop);
            }
        }
        return false;
    }

    /** 玩家主背包（36 格）里真正为空的格数；不要用 {@code Inventory#getFreeSlot()} 代替。 */
    private int playerFreeSlots() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        var playerInventory = mc.player.getInventory();
        int free = 0;
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            if (playerInventory.getItem(i).isEmpty()) free++;
        }
        return free;
    }

    boolean tryStartLogistics() {
        if (owner.logisticsCooldown > 0 || owner.inventory == null || owner.index == null) return false;
        // 补种已在“非空盆任务完成后、空盆任务之前”的唯一入口处理，这里只负责普通卸货。
        return tryStartUnload();
    }

    /** 补货只服务于当前扫描快照中的真实 Plant Demand。 */
    boolean tryStartRestock() {
        if (owner.logisticsCooldown > 0 || owner.inventory == null || owner.index == null) return false;

        StardewPointManager.StardewPoint seedBox = owner.points.get(StardewPointType.SEED_BOX);
        if (seedBox != null && seedBox.inCurrentDimension()) {
            for (CropDefinition crop : targetCrops()) {
                if (hasRestockDemand(crop)
                    && !owner.logistics.restockBackedOff(crop)) {
                    return startLogisticsTask(TaskType.RESTOCK, seedBox, crop);
                }
            }
        }

        return false;
    }

    /**
     * 种子回收：把背包里多余的同种种子存回种子箱。
     *
     * <p><b>为什么需要它：</b>保株作物（收获后植株回退、不需要补种）种满配额后播种这条唯一的
     * 种子消耗路径就断了，而收获又会掉种子、成品卸货又刻意跳过种子，于是种子只增不减，最终
     * 挤占背包格。</p>
     *
     * <p><b>为什么不会和补货打架：</b>补货的前提是「有空盆要播这个作物」，回收的前提是
     * 「该作物配额已种满」；有空盆时不回收，配额满时无补货需求，两者互斥。</p>
     */
    boolean tryStartSeedReturn() {
        if (owner.logisticsCooldown > 0 || owner.inventory == null || owner.index == null) return false;

        StardewPointManager.StardewPoint seedBox = owner.points.get(StardewPointType.SEED_BOX);
        if (seedBox == null || !seedBox.inCurrentDimension()) return false;
        for (CropDefinition crop : targetCrops()) {
            if (hasSeedReturnDemand(crop)
                && !owner.logistics.logisticsBackedOff(owner.seedReturnBlockedUntil, crop.cropKey())) {
                return startLogisticsTask(TaskType.SEED_RETURN, seedBox, crop);
            }
        }
        return false;
    }

    /** 该作物配额已满、且背包种子超过保留量时，构成种子回收需求。 */
    private boolean hasSeedReturnDemand(CropDefinition crop) {
        if (crop == null || owner.inventory == null) return false;
        int keep = seedReturnKeep(crop);
        if (keep <= 0) return false;
        if (owner.inventory.countSeed(crop) <= keep) return false;
        return plannedCellCount(crop.cropKey()) >= cropActualTarget(crop.cropKey());
    }

    /** 保留量沿用「种子补货目标」：取到多少就留多少，不新增设置项。 */
    int seedReturnKeep(CropDefinition crop) {
        return owner.logisticsOf(crop.cropKey()).restockTarget();
    }

    /** 该作物的数量配额（「个数」直接是盆数，「组数」×64），与播种决策同一份换算。 */
    private int cropActualTarget(String cropKey) {
        StardewCropPlanStore.CropPlan plan = owner.cropPlanResolver.apply(cropKey);
        return plan == null ? StardewCropPlanStore.CropPlan.DEFAULT.actualAmount() : plan.actualAmount();
    }

    /** 背包种子是否已降到保留量（回收完成判据）。 */
    boolean seedReturnComplete(CropDefinition crop) {
        return crop == null || owner.inventory == null || owner.inventory.countSeed(crop) <= seedReturnKeep(crop);
    }

    /** 普通成品卸货保持在农田任务之后，背包保护卸货仍由更高优先级入口负责。 */
    boolean tryStartUnload() {
        if (owner.logisticsCooldown > 0 || owner.inventory == null || owner.index == null) return false;

        // 成品卸货：某已选作物成品达到阈值且成品箱已绑定
        StardewPointManager.StardewPoint outputBox = owner.points.get(StardewPointType.OUTPUT_BOX);
        if (outputBox != null && outputBox.inCurrentDimension()) {
            for (CropDefinition crop : targetCrops()) {
                if (owner.snapshot(crop).unloadNeeded()
                    && !owner.logistics.logisticsBackedOff(owner.unloadBlockedUntil, crop.cropKey())) {
                    return startLogisticsTask(TaskType.UNLOAD, outputBox, crop);
                }
            }
        }
        return false;
    }

    /** 季节已允许、盆已浇湿、背包确实无种子的空盆才构成 Restock Demand。 */
    boolean hasRestockDemand(CropDefinition crop) {
        if (crop == null || owner.inventory.countSeed(crop) > 0
            || owner.logisticsOf(crop.cropKey()).restockTarget() <= 0) return false;
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() != CropState.EMPTY || !potMatchesSelection(cell)
                || !hasPlantTarget(cell.potPos()) || !crop.cropKey().equals(plantCropKey(cell.potPos()))) continue;
            StardewSeasonService.PlantingStatus season = owner.seasonService.plantingStatus(crop, owner.inventory.findSeedStack(crop));
            if (season == StardewSeasonService.PlantingStatus.DISALLOWED) continue;
            if (owner.wateringEnabled && cell.potState() == PotState.DRY) continue;
            return true;
        }
        return false;
    }

    /** 后勤统一先选整个箱体外围的合法站位，箱子本体仅作为后续交互目标。 */
    private boolean startLogisticsTask(TaskType type, StardewPointManager.StardewPoint point,
                                       CropDefinition crop) {
        if (point == null || crop == null) return false;
        List<ContainerApproachPlanner.Approach> approaches =
            ContainerApproachPlanner.candidates(point.pos(), owner.reach);
        ContainerApproachPlanner.Approach selected = null;
        for (ContainerApproachPlanner.Approach approach : approaches) {
            if (!isNavigationBlocked(type, approach.standPos())) {
                selected = approach;
                break;
            }
        }
        if (selected == null) {
            String failureKey = type.name() + ":" + point.x() + ":" + point.y() + ":" + point.z();
            if (owner.reportedContainerFailures.add(failureKey)) {
                owner.status.state("CONTAINER_UNREACHABLE:" + failureKey,
                    (type == TaskType.UNLOAD ? "产物箱" : "种子箱") + "暂时不可达",
                    "已继续处理其他农场任务");
            }
            return false;
        }
        owner.reportedContainerFailures.remove(type.name() + ":" + point.x() + ":" + point.y() + ":" + point.z());
        owner.taskType = type;
        owner.targetContainer = point.pos();
        owner.containerApproach = selected;
        owner.activeCrop = crop;
        owner.retryCount = 0;
        owner.taskStep = 0;
        owner.taskTicks = 0;
        resetNavigationWatchdog();
        owner.reporter.captureTaskInventory();
        owner.reporter.broadcast(type.cn());
        return true;
    }

    /**
     * 用户已选择的目标作物。
     *
     * <p>空选择 = 什么都不管。这里**绝不回退到「资源索引里的全部作物」**：一旦回退，
     * 未选作物的种子/成品就会混进补货与卸货统计，直接违反「按已选 cropKey 独立运行」。</p>
     */
    List<CropDefinition> targetCrops() {
        List<CropDefinition> result = new ArrayList<>();
        if (owner.index == null) return result;
        for (String key : owner.selectedCropKeys) {
            CropDefinition crop = owner.index.cropByKey(key);
            if (crop != null) result.add(crop);
        }
        return result;
    }

    /** 拾取必须真正走进磁吸范围，中心点则用独立近距判定，其余任务沿用交互距离。 */
    double arrivalReach() {
        if (owner.taskType == TaskType.RETURN_CENTER) return CENTER_REACH;
        if (owner.taskType == TaskType.REFILL) return REFILL_REACH;
        if (owner.taskType == TaskType.RESTOCK || owner.taskType == TaskType.UNLOAD
            || owner.taskType == TaskType.SEED_RETURN) return CONTAINER_STAND_REACH;
        return owner.reach;
    }

    /**
     * Baritone 的 GoalNear 半径必须与任务语义一致。
     *
     * <p>拾取用 0：GoalNear 半径 0 表示「玩家所在方格必须就是掉落物所在方格」，
     * 这是唯一能保证走进磁吸范围的半径（1 格只保证「在附近」，真机上正好差一点点捡不到）。</p>
     */
    int navigationRadius() {
        if (owner.taskType == TaskType.RETURN_CENTER || owner.taskType == TaskType.RESTOCK
            || owner.taskType == TaskType.UNLOAD || owner.taskType == TaskType.SEED_RETURN
            || owner.taskType == TaskType.COLLECT) return 0;
        return owner.taskType == TaskType.REFILL ? 1 : 2;
    }

    BlockPos regionCenter() {
        if (owner.regionModeActive()) return nearestRegionCenter();
        StardewPointManager.StardewPoint start = owner.points.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = owner.points.get(StardewPointType.END);
        if (start == null || end == null) return null;
        return new BlockPos((start.x() + end.x()) / 2, Math.max(start.y(), end.y()) + 1, (start.z() + end.z()) / 2);
    }

    /**
     * 分区模式的回中心点：离玩家最近的区域中心。
     *
     * <p>区域并集的几何中心可能落在走道、田外甚至另一块地里，那儿站着不产生任何任务；
     * 「最近的那块地」才是玩家眼里自然的待命位置。</p>
     */
    private BlockPos nearestRegionCenter() {
        BlockPos player = Minecraft.getInstance().player == null
            ? null : Minecraft.getInstance().player.blockPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (StardewRegionManager.Region region : owner.regions) {
            BlockPos center = region.center();
            double distance = player == null ? 0 : center.distSqr(player);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = center;
            }
        }
        return best;
    }

    BlockPos regionMin() {
        if (owner.regionModeActive()) return regionBounds(true);
        StardewPointManager.StardewPoint start = owner.points.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = owner.points.get(StardewPointType.END);
        if (start == null || end == null) return null;
        return new BlockPos(Math.min(start.x(), end.x()), Math.min(start.y(), end.y()), Math.min(start.z(), end.z()));
    }

    BlockPos regionMax() {
        if (owner.regionModeActive()) return regionBounds(false);
        StardewPointManager.StardewPoint start = owner.points.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = owner.points.get(StardewPointType.END);
        if (start == null || end == null) return null;
        return new BlockPos(Math.max(start.x(), end.x()), Math.max(start.y(), end.y()), Math.max(start.z(), end.z()));
    }

    /**
     * 全部区域的并集包围盒。
     *
     * <p><b>为什么是包围盒而不是逐区域扫：</b>扫描器按「一个矩形逐格推进」工作，改成多段扫描要动
     * 整个扫描器；用并集包围盒多扫到的格子（区域之间的走道）由 {@code resolveTask} 一句
     * 「未分区格子不管」直接过滤，行为一致而改动面最小。</p>
     */
    private BlockPos regionBounds(boolean min) {
        if (owner.regions.isEmpty()) return null;
        int x = min ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int y = min ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int z = min ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (StardewRegionManager.Region region : owner.regions) {
            x = min ? Math.min(x, region.minX()) : Math.max(x, region.maxX());
            y = min ? Math.min(y, region.minY()) : Math.max(y, region.maxY());
            z = min ? Math.min(z, region.minZ()) : Math.max(z, region.maxZ());
        }
        return new BlockPos(x, y, z);
    }
}
