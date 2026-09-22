package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.PotDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestRecipe;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.service.StardewShelterProbe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            if (tryDispatch(cell)) return true;
        }
        return false;
    }

    /**
     * 区域粘性：先把「当前作业地块」里的活干完，再去下一块。
     *
     * <p><b>为什么要有这一遍：</b>只按任务类型排序（成熟 → 清理 → 浇水 → 播种）时，排序里没有任何
     * 位置概念，同类型按扫描的坐标顺序挑格子，于是玩家站在 A 地也可能被派去 B 地，做完再回来
     * （实机反馈「几个区域来回跑，浪费时间」）。这一遍在当前地块内先收后种，把这块地做干净再换。</p>
     *
     * <p><b>为什么判定可靠：</b>{@code pending} 是本轮「扫完整片农田」后的完整快照
     * （{@code scanner.complete()} 之后才进 DECIDE），所以「当前地块没有可派发任务」不是采样偏差，
     * 不会出现「这轮没扫到就误判做完」的来回跳。</p>
     *
     * <p><b>只对单一作物区生效：</b>混种区（农场模式）与未分区格子都不参与，行为与加这条之前一致。</p>
     */
    boolean startStickyRegionTask() {
        if (owner.stickyRegionIndex <= 0) return false;
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() != CropState.EMPTY && isStickyCell(cell) && tryDispatch(cell)) return true;
        }
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() == CropState.EMPTY && isStickyCell(cell) && tryDispatch(cell)) return true;
        }
        return false;
    }

    /** 这格是不是「当前作业地块」里的（只认单一作物区；混种区与未分区不参与粘性） */
    private boolean isStickyCell(StardewFarmScanner.Cell cell) {
        StardewRegionManager.Region region = owner.regionAt(cell.potPos());
        return region != null && !region.mixed() && region.index() == owner.stickyRegionIndex;
    }

    /**
     * 当前作业地块缺种子时，先把这一块要的种子取回来，<b>别急着去别的区域种</b>。
     *
     * <p><b>为什么要单开这一步</b>（用户 2026-09-22：「没完成一个区域的事情就跑到别的区域干活了，
     * 比如当前区域还有菜没种完」）：空盆的决策顺序是「季节 → 浇水 → 种子 → 施肥」，本区作物没种子时
     * {@link #resolveEmptyPot} 只报一句缺种子就跳过这一格；而补货（{@link #tryStartRestock}）
     * 排在<b>所有区域的格子任务之后</b>，于是只要别的区域还有种子可种，模块就先去种别的区域，
     * 本区那些空盆一直空着 —— 玩家看到的就是「这一块还没种完就跑别的地块去了」。
     * 补种子本来就是「把当前地块做完」的一部分，所以把它提到其它区域的格子任务之前。</p>
     *
     * <p><b>只管当前作业地块、只管真能种进去的空盆</b>：季节被拦、盆上没温室玻璃、需要先浇水、
     * 盆型没选中的格子都由 {@link #hasRestockDemand} 与 {@link #potMatchesSelection} 挡在外面，
     * 不会为了种不进去的格子白跑一趟。</p>
     *
     * <p><b>不会卡死</b>：种子箱没绑定 / 不在本维度 / 不可达 / 里面没有这一种时，
     * {@code restockBlockedUntil} 会让 {@link #hasRestockDemand} 这条很快失效，
     * 下一轮照常去别的区域干活。</p>
     *
     * @return 是否已经派发「去当前地块对应的种子箱取种」
     */
    boolean startStickyRegionRestock() {
        if (owner.stickyRegionIndex <= 0 || owner.inventory == null || owner.index == null) return false;
        if (owner.logisticsCooldown > 0) return false;
        StardewPointManager.StardewPoint seedBox = owner.points.get(StardewPointType.SEED_BOX);
        if (seedBox == null || !seedBox.inCurrentDimension()) return false;
        Set<String> tried = new HashSet<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() != CropState.EMPTY || !isStickyCell(cell) || !potMatchesSelection(cell)) continue;
            CropDefinition crop = cropOf(cell.potPos());
            // 同一块地只问一次每种作物：本区是单一作物区，正常只会取到一种
            if (crop == null || !tried.add(crop.cropKey())) continue;
            if (owner.inventory.countSeed(crop) > 0) continue;
            if (!hasRestockDemand(crop) || owner.logistics.restockBackedOff(crop)) continue;
            return startLogisticsTask(TaskType.RESTOCK, seedBox, crop);
        }
        return false;
    }

    /** 派发成功时记下这块地：下一轮先把它做干净（混种区 / 未分区不改口径） */
    private void noteStickyRegion(StardewFarmScanner.Cell cell) {
        StardewRegionManager.Region region = owner.regionAt(cell.potPos());
        if (region == null || region.mixed()) return;
        owner.stickyRegionIndex = region.index();
    }

    /** 单格派发：决策 → 导航退避检查 → 计时器归零；成功返回 true */
    private boolean tryDispatch(StardewFarmScanner.Cell cell) {
        TaskType resolved = resolveTask(cell);
        if (resolved == null) return false;
        owner.activeCell = cell;
        owner.targetPot = cell.potPos();
        owner.taskType = resolved;
        BlockPos navigationTarget = taskTarget();
        if (isNavigationBlocked(owner.taskType, navigationTarget)) {
            owner.activeCell = null;
            owner.targetPot = null;
            owner.taskType = null;
            return false;
        }
        owner.retryCount = 0;
        owner.taskStep = 0;
        owner.taskTicks = 0;
        resetNavigationWatchdog();
        noteStickyRegion(cell);
        owner.reporter.announceTaskStart();
        return true;
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
     * <p>必须是已勾选作物，且这一格落在某块种植区域内；未分区格子一律不管
     * （不种 / 不收 / 不浇 / 不画）。单一作物区只认它绑定的那一种，混种区域认全部已勾选作物。</p>
     *
     * <p>区域绑定的作物一旦被取消勾选，该区域整体失效跳过，重新勾选自动恢复（数据不删）。</p>
     */
    boolean cropIsManaged(BlockPos potPos, String cropKey) {
        if (cropKey == null || !owner.selectedCropKeys.contains(cropKey)) return false;
        StardewRegionManager.Region region = owner.regionAt(potPos);
        return region != null && (region.mixed() || cropKey.equals(region.cropKey()));
    }

    /**
     * 这一格的「计划作物」：单一作物区 = 区域绑定的那种作物；混种区域 = 本块缺口最大的已勾选作物。
     *
     * <p>区域必须仍被勾选、且该作物在当前资源索引里存在，否则这一格视为「无目标」（跳过，不播种）。
     * {@code exclude} 是本次空盆流程里已经试过、暂时用不上的作物（当季不能种 / 没种子）。</p>
     */
    private String regionCropKeyFor(BlockPos potPos, Set<String> exclude) {
        StardewRegionManager.Region region = owner.regionAt(potPos);
        if (region == null) return null;
        if (!region.mixed()) {
            if (exclude.contains(region.cropKey())) return null;
            if (!owner.selectedCropKeys.contains(region.cropKey())) return null;
            return owner.index != null && owner.index.cropByKey(region.cropKey()) != null ? region.cropKey() : null;
        }
        return mixedCropKey(exclude);
    }

    /**
     * 混种区域这一格该种哪种：在「还没种够」的已勾选作物里，优先挑背包还有种子的，
     * 取缺口（配额 − 已计划盆数）最大的那个。
     *
     * <p><b>为什么优先还有种子的：</b>缺种子那一种由 {@link #hasRestockDemand} 去补货，
     * 不必让整块地陪它等（空盆全跳过）。缺口算法保证每种作物都能轮到，优先有种子则保证不空等。</p>
     *
     * <p><b>为什么一种都没种子时仍要挑一个：</b>补货要靠「本格的计划作物」找到对象；
     * 全都没种子时返回缺口最大的那个，种子补回来就能立刻种上。</p>
     */
    private String mixedCropKey(Set<String> exclude) {
        if (owner.index == null || owner.inventory == null) return null;
        String best = null;
        int bestGap = 0;
        String fallback = null;
        int fallbackGap = 0;
        for (String key : owner.selectedCropKeys) {
            if (exclude.contains(key)) continue;
            CropDefinition crop = owner.index.cropByKey(key);
            if (crop == null) continue;
            int gap = cropActualTarget(key) - plannedCellCount(key);
            if (gap <= 0) continue;
            if (gap > fallbackGap) {
                fallbackGap = gap;
                fallback = key;
            }
            if (owner.inventory.countSeed(crop) <= 0) continue;
            if (gap > bestGap) {
                bestGap = gap;
                best = key;
            }
        }
        return best != null ? best : fallback;
    }

    /**
     * 现在还真的有「被季节拦下、且盆上方没有温室玻璃」的空盆吗？
     *
     * <p><b>为什么「等待季节」必须以此为准：</b>{@code seasonBlockedCrops} 记的是「本季出现过被拦的格子」，
     * 玩家随后补上温室玻璃、或自己动手把那几格种上之后，这个集合已经不是现实——只按集合非空播报，会在
     * 地里早已无事可等时播出一条「等待季节」，看着像模块卡住了。</p>
     */
    boolean hasSeasonBlockedEmptyPot() {
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() != CropState.EMPTY) continue;
            CropDefinition crop = cropOf(cell.potPos());
            if (crop == null) continue;
            if (owner.seasonService.plantingStatus(crop, owner.inventory.findSeedStack(crop))
                != StardewSeasonService.PlantingStatus.DISALLOWED) continue;
            if (StardewShelterProbe.sheltered(cell.potPos(), owner.index, owner.selectedShelterKeys)) continue;
            return true;
        }
        return false;
    }

    /** 这一格是不是落在混种区域里 */
    private boolean isMixedRegionAt(BlockPos potPos) {
        StardewRegionManager.Region region = owner.regionAt(potPos);
        return region != null && region.mixed();
    }

    /**
     * 这一格是不是「单一作物区里种了别的作物」。
     *
     * <p>混种区域恒为 false（那里种什么都算对）；自动清理开关关着时也恒为 false：
     * 那条路归「停机 + 人工清理」，这里绝不偷偷挖掉任何植株。</p>
     *
     * <p><b>只认活着的、身份可辨的作物：</b>枯死株（{@link CropState#DEAD}）与认不出的方块
     * （{@link CropState#UNKNOWN}）都不算错位——它们不是「种错了作物」，而是「这一格需要清扫」。
     * 冬天冻死的植株身份往往只能落到 {@code xxx_dead}，硬按错位处理会刷出一条
     * 「区域里的作物不是番茄」的假警报并停机；现在它们走 {@link TaskType#CLEAR_DEAD} /
     * {@link TaskType#CLEAR_JUNK} 的静默清理链。</p>
     */
    private boolean isRegionMismatchCell(StardewFarmScanner.Cell cell) {
        if (!owner.autoClearMismatch) return false;
        String key = cell.crop().cropKey();
        if (key == null || !isAliveCrop(cell)) return false;
        StardewRegionManager.Region region = owner.regionAt(cell.potPos());
        return region != null && !region.mixed() && !key.equals(region.cropKey());
    }

    /**
     * 这一格上的东西是不是「活着的、身份可辨的作物」。
     *
     * <p>枯死株与杂物（认不出的方块）都不属于「错位作物」：错位的前提是「种着另一株活作物」，
     * 死株 / 杂物要做的是清扫，不是报警。</p>
     */
    private static boolean isAliveCrop(StardewFarmScanner.Cell cell) {
        CropState state = cell.crop().state();
        return state != CropState.DEAD && state != CropState.UNKNOWN;
    }

    /**
     * 这一格是不是分区盆上的「杂物」：盆上方那格不是已识别的作物方块（结构上不是阶段化作物）。
     *
     * <p>典型来源是拦在盆上的原版 / 自定义非作物方块（冬季的积雪层、玩家掉落的方块等）。
     * 它们既不是作物也没有成熟规则，识别层如实报 {@code UNKNOWN}，
     * 不清理就会一直挡住这一格的补种（实机反馈：区域内出现杂物后那一格再也不种）。
     * 属于破坏动作，受「自动清理错位作物」总开关约束：关掉就不碰它（它只是挡住一格，
     * 不报错也不画红框——红框专指「种错了作物」）。</p>
     */
    private boolean isJunkCell(StardewFarmScanner.Cell cell) {
        if (!owner.autoClearMismatch) return false;
        return cell.crop().state() == CropState.UNKNOWN && owner.regionAt(cell.potPos()) != null;
    }

    /**
     * 「某块单一作物区里种了别的作物」的格子。
     *
     * <p>报错详情与世界高亮共用这一份判定，避免两处口径分叉。未分区格子与混种区域都不参与判定
     * （前者不管，后者种什么都算对）；枯死株与杂物同样不算——那两类归清理链，不报警、不画错位框。</p>
     */
    record RegionMismatch(BlockPos pos, StardewRegionManager.Region region, String cropKey) {
    }

    /** 分区错位格子清单（空 = 没有冲突） */
    List<RegionMismatch> regionMismatches() {
        List<RegionMismatch> result = new ArrayList<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            String key = cell.crop().cropKey();
            if (key == null || !isAliveCrop(cell)) continue;
            StardewRegionManager.Region region = owner.regionAt(cell.potPos());
            if (region == null || region.mixed() || key.equals(region.cropKey())) continue;
            result.add(new RegionMismatch(cell.potPos(), region, key));
        }
        return result;
    }

    /**
     * 单一作物区的冲突详情文案。
     *
     * <p>逐条给出玩家能直接走过去处理的证据：区域号、该区绑定作物、实际作物、坐标。
     * 最多列三处，其余折叠成「等 N 处」。</p>
     */
    List<String> regionMismatchedCrops() {
        return describeMismatches(regionMismatches());
    }

    /**
     * 把一批错位格整理成详情文案。
     *
     * <p>运行中停机播报与启动自检共用这一份口径，避免两处文案分叉。</p>
     */
    List<String> describeMismatches(List<RegionMismatch> all) {
        List<String> details = new ArrayList<>();
        for (int i = 0; i < Math.min(3, all.size()); i++) {
            RegionMismatch mismatch = all.get(i);
            CropDefinition crop = owner.index == null ? null : owner.index.cropByKey(mismatch.cropKey());
            String actual = crop == null ? mismatch.cropKey() : crop.chineseName();
            BlockPos pos = mismatch.pos();
            details.add("区域 " + mismatch.region().index() + "（" + mismatch.region().cropName() + "）实际 " + actual
                + " · X" + pos.getX() + " Y" + pos.getY() + " Z" + pos.getZ());
        }
        if (all.size() > details.size()) details.add("等 " + all.size() + " 处");
        return details;
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
        // 错位格子最优先：先把它挖掉，这一格才能按区域作物补种（清完立刻回到正常节奏）
        if (isRegionMismatchCell(cell)) return 0;
        CropState s = cell.crop().state();
        return switch (s) {
            case GROWING -> needsSupply(cell) ? 0 : (shouldProbeLearning(cell) ? probeOrder(cell) : 90);
            case MATURE, SPECIAL -> 1;
            case DEAD -> 2;
            case EMPTY -> 3;
            // 杂物排在补种之前：不清掉盆上那格，这一格永远种不进去
            case UNKNOWN -> isJunkCell(cell) ? 4 : 90;
        };
    }

    TaskType resolveTask(StardewFarmScanner.Cell cell) {
        // 特殊变种（金色 / 巨型 / 变种）是「摆在地上的大结构」，不长在已选盆里：
        // 拿盆型过滤会把它们整片跳过（实机：故意放在地上的巨型菠萝被 ESP 标出来，模块却从不动手）。
        boolean specialCrop = cell.crop().state() == CropState.SPECIAL;
        if (!specialCrop && !potMatchesSelection(cell)) return null;
        // 下界盆只在下界、末地盆只在末地：维度不对的盆一律不碰（种下去也不会长），只提示一次。
        // 普通盆不受此限——它在任何维度都能种。
        PotGroup group = cell.potGroup();
        if (!group.allowsDimension(owner.dimension)) {
            announceDimensionBlock(group);
            return null;
        }
        // 未分区格子一律不管（不种 / 不收 / 不浇 / 不画），也不参与任何报错
        if (owner.regionAt(cell.potPos()) == null) return null;
        // 单份账：这一格的「左键破坏」是否还在服务端不放行的退避期里（见 StardewCoordinator#markBreakRejected）
        boolean breakBackedOff = owner.isBreakRejected(cell.potPos().above());
        // 单一作物区错位：这一格归「清理错位」管（自动清理关着时不走这条，由停机路径等玩家手动清）
        if (isRegionMismatchCell(cell)) {
            // 清理错位也是左键破坏：它同样会把这一格的东西掉在地上，两条「满了」的闸一并管住它；
            // 挖不动的格子退避期内也不再派发（否则就是每十几秒空砸一轮，见 196 续的实机日志）
            return !storageFullHalts() && !breakBackedOff ? TaskType.CLEAR_MISMATCH : null;
        }
        // 没选中的作物：不收 / 不清理 / 不播种 / 不施肥 / 不学习。
        // 单一作物区里种了别的作物交给错位处置；混种区域与未分区格子上面已经各自分流过了。
        //
        // 枯死株例外：死株的作物身份经常只能落到 {@code xxx_dead} 这类派生键（甚至为空），
        // 拿它去比「有没有选中 / 是不是本区作物」只会把该清的东西一直留在盆里。分区内的死株
        // 一律清掉，空出的盆再按本区作物补种——这与错位清理由同一套破坏动作完成。
        CropState state = cell.crop().state();
        boolean deadInRegion = state == CropState.DEAD;
        if (!deadInRegion && cell.crop().cropKey() != null && !cropIsManaged(cell.potPos(), cell.crop().cropKey())) {
            return null;
        }
        // 背包一个空格都没有 / 产物箱没空间：一切「会把东西拿到手里」的动作都停手 —— 右键采收、
        // 特殊变种收割、以及清枯苗 / 清错位 / 清杂物这些左键破坏（破坏同样会把掉落物掉在地上）。
        // 只拦这些：浇水 / 播种 / 施肥不产出新物品，照常执行。
        // 播报与实际动作必须一致（实机：聊天栏播报「已暂停收菜」之后，模块仍在砸方块）。
        if (harvestsOrBreaks(state, cell) && storageFullHalts()) return null;
        TaskType resolved = switch (state) {
            // 普通成熟作物执行右键采摘；特殊变种必须等独立工具规则确认后再处理。
            case MATURE -> needsHarvestLearning(cell.crop().cropKey())
                ? (canProbeHarvestLearning(cell) ? TaskType.LEARN_HARVEST : null)
                : TaskType.HARVEST;
            // 特殊变种（金色 / 巨型 / 变种）：动作与手持都按本服学到 / 预置的口径来 ——
            // 老服是「手持原版金锄头右键」，本服（巨型菠萝）是「左键破坏、工具不限（空手也能挖）」，
            // 还有的服是「左键破坏但必须手持某件专用道具」（用户 2026-09-22）。
            // 口径要求的那件工具不在背包时只跳过这一格并提示（不停机、不影响收其他作物）。
            case SPECIAL -> owner.executor.hasSpecialHarvestToolFor(cell.crop())
                ? TaskType.HARVEST
                : skipSpecialWithoutTool(cell.crop());
            case DEAD -> TaskType.CLEAR_DEAD;
            case GROWING -> resolveGrowing(cell);
            case EMPTY -> resolveEmptyPot(cell);
            // 认不成作物 = 盆上的杂物（原版方块 / 非阶段化自定义方块）：挖掉这一格才能补种，
            // 静默执行、不报警（它不是「种错了作物」，报警只会误导）。
            case UNKNOWN -> isJunkCell(cell) ? TaskType.CLEAR_JUNK : null;
        };
        // 这一格刚被服务端驳回（挖不动，不是慢）：退避期内不再派发它的破坏动作，避免每十几秒
        // 重来一轮「4 次发包 + 4 轮按住」（实机：同一格空砸了两分钟，方块纹丝不动）
        if (breakBackedOff && breaksThisCell(resolved, cell)) return null;
        return resolved;
    }

    /**
     * 这一格的任务是不是「会把东西拿到手里」（采收 / 学习探测 / 左键破坏）。
     *
     * <p>判据只认这三类状态：成熟与特殊变种（右键采收 / 破坏收割）、枯死株（清枯苗）、
     * 认不成的杂物（清杂物，前提是它确实是盆上的杂物）。浇水 / 播种 / 施肥 / 补水这些不产出
     * 新物品的动作不在此列，背包满时照常执行。</p>
     */
    private boolean harvestsOrBreaks(CropState state, StardewFarmScanner.Cell cell) {
        return switch (state) {
            case MATURE, SPECIAL, DEAD -> true;
            case UNKNOWN -> isJunkCell(cell);
            default -> false;
        };
    }

    /**
     * 背包一个空格都没有：这条「满了」的闸（播报与去重都在 {@link #bagFullBlocksHarvest()} 里）。
     *
     * <p><b>为什么只剩背包这一条</b>：产物箱没有空间时模块**直接被关掉**（见
     * {@code StardewCoordinator#stopForOutputBoxFull}），根本不会走到决策层 ——
     * 用户在实机里明确要的就是这个（2026-09-22：「我说了 直接停机了 关闭模块懂？」）。</p>
     */
    boolean storageFullHalts() {
        return bagFullBlocksHarvest();
    }

    /** 这一步的动作是不是「左键破坏作物本体那一格」（清枯苗 / 清错位 / 清杂物，或学到的左键收割口径） */
    private boolean breaksThisCell(TaskType resolved, StardewFarmScanner.Cell cell) {
        if (resolved == null) return false;
        if (resolved.breaksPlant()) return true;
        return resolved == TaskType.HARVEST && cell.crop().state() == CropState.SPECIAL
            && owner.specialHarvestRecipe(cell.crop()).action() == StardewSpecialHarvestAction.BREAK;
    }

    private TaskType resolveGrowing(StardewFarmScanner.Cell cell) {
        if (needsSupply(cell)) {
            TaskType refill = resolveRefill(cell);
            if (refill != null) return refill;
        }
        // 学习试探是「对作物空手右键一次」—— 判据是我们以为它还没熟，但服务端只认它自己那套：
        // 本服上这一下会把能收的作物真的收掉。它走的是「生长中」分支，而两条「满了」的闸只按
        // 「成熟 / 特殊 / 枯死 / 盆上杂物」判定，于是背包塞满时它照样一路右键收菜（实机反馈：
        // 明明播报了「已暂停收菜」，模块还在收）。因此这里与成熟采收同口径停手。
        if (shouldProbeLearning(cell)) return storageFullHalts() ? null : TaskType.LEARN_HARVEST;
        return null;
    }

    /**
     * 这一格是否缺料、需要脚本去补。
     *
     * <p><b>「自动浇灌」是总开关：</b>三种盆型共用它——普通盆浇的是水（水壶 / 补水点），
     * 下界盆浇的是岩浆，末地盆浇的是龙息。关掉它 = 「浇灌我自己来」，脚本只保留播种 / 收割 /
     * 清理这些不与物料绑定的动作，所以这里仍然只看这一个开关。</p>
     */
    private boolean needsSupply(StardewFarmScanner.Cell cell) {
        return owner.wateringEnabled && cell.potState() == PotState.DRY;
    }

    /**
     * 干盆的补水动作：普通盆走水壶链路，下界 / 末地盆看背包里有没有对应物料。
     *
     * <p>三种盆型的物料互斥，所以这里按盆型分派，不会出现「拿着水壶去浇下界盆」：</p>
     * <ul>
     *   <li>普通盆：壶空 → {@link TaskType#REFILL} 去补水点；壶有量 → {@link TaskType#WATER}；</li>
     *   <li>下界盆 / 末地盆：背包有岩浆 / 龙息 → {@link TaskType#WATER}（执行器换手持物料右键）；
     *       没有 → 提示一次缺料并返回 {@code null}，由调用方决定是否继续（不去水点、不倒水）。</li>
     * </ul>
     *
     * @return 该格下一步要做的任务；没有可用动作（含缺料）时返回 {@code null}
     */
    private TaskType resolveRefill(StardewFarmScanner.Cell cell) {
        PotGroup group = cell.potGroup();
        if (group.refillItem() != null) {
            if (owner.inventory.countItem(group.refillItem()) > 0) return TaskType.WATER;
            // 手里没料：绑定且可用的物料箱还在，就去取一趟（岩浆 3 桶 / 龙息 10 个）
            if (startMaterialFetch(group)) return TaskType.REFILL;
            announceMaterialShortage(group);
            return null;
        }
        // 走到这里 = 被判成普通盆，会用**水壶**去浇。下界 / 末地盆落到这条就是盆型识别错了。
        if (owner.forceRefill || owner.executor.canWaterIsEmpty()) return TaskType.REFILL;
        if (owner.executor.canUsable()) return TaskType.WATER;
        return null;
    }

    /**
     * 取料前的就地准备：选中物料箱外围的一个合法站位，作为 REFILL 的导航与交互目标。
     *
     * <p>不复用 {@link #startLogisticsTask}：那条路要求作物上下文（按作物算该取多少种子 / 卸多少成品），
     * 而取料与作物无关，强行塞一个作物进去只会让语义变糊。</p>
     *
     * @return 是否已经备好可以出发；箱未绑定 / 不可达时返回 {@code false}
     */
    private boolean startMaterialFetch(PotGroup group) {
        StardewPointType boxType = StardewPointType.materialBoxFor(group);
        if (boxType == null) return false;
        // 刚验过这只箱子是空的：退避期内不再跑一趟，避免「开箱 → 取不到 → 关箱 → 重规划 → 开箱」空转
        if (System.currentTimeMillis() < owner.materialFetchBlockedUntil) return false;
        StardewPointManager.StardewPoint box = owner.points.get(boxType);
        if (box == null || !box.inCurrentDimension()) return false;
        ContainerApproachPlanner.Approach selected = null;
        for (ContainerApproachPlanner.Approach approach : ContainerApproachPlanner.candidates(box.pos(), owner.reach)) {
            if (!isNavigationBlocked(TaskType.REFILL, approach.standPos())) {
                selected = approach;
                break;
            }
        }
        if (selected == null) {
            String key = "MATERIAL:" + boxType.name() + ':' + box.x() + ':' + box.y() + ':' + box.z();
            if (owner.reportedContainerFailures.add(key)) {
                owner.status.state("MATERIAL_BOX_UNREACHABLE:" + key,
                    boxType.title() + "暂时不可达", "已继续处理其他农场任务");
            }
            return false;
        }
        owner.targetContainer = box.pos();
        owner.containerApproach = selected;
        return true;
    }

    /** 下界盆缺岩浆 / 末地盆缺龙息：同一盆型只提示一次，避免每轮扫描刷屏。 */
    private void announceMaterialShortage(PotGroup group) {
        if (!owner.announcedMaterialShortage.add(group.name())) return;
        owner.status.state("POT_MATERIAL_SHORT:" + group.name(),
            group.displayName() + "没有" + group.materialName(),
            "背包里备好" + group.materialName() + "后会自动继续");
    }

    /** 下界盆 / 末地盆不能施肥：同一盆型只提示一次，避免每格都刷一条。 */
    private void announceNoFertilize(PotGroup group) {
        if (!owner.announcedNoFertilize.add(group.name())) return;
        owner.status.state("POT_NO_FERTILIZE:" + group.name(),
            group.displayName() + "不能施肥",
            "两种特殊盆都没有肥料槽，已忽略自动施肥");
    }

    /** 盆型和当前维度不匹配：同一盆型只提示一次（换维度时会重新放行播报）。 */
    private void announceDimensionBlock(PotGroup group) {
        if (!owner.announcedDimensionBlocks.add(group.name())) return;
        String target = group == PotGroup.NETHER ? "下界" : "末地";
        owner.status.state("POT_DIMENSION:" + group.name(),
            group.displayName() + "只能种在" + target,
            "当前维度不是它的适宜维度，已跳过这类盆");
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

    /**
     * 成熟阶段未知时的试探顺序：**阶段号越大越先试**（返回 1~9，越小越优先）。
     *
     * <p><b>为什么要排序：</b>没有成熟规则时，模块会对每个见到的阶段各做一次空手右键试探，
     * 看世界是否变化。而成熟阶段几乎总是号最大的那个（customcrops 系是 {@code stage_1..stage_N}，
     * N 即成熟）—— 升序试探会先拿三个生长阶段白试三次，玩家连续收到几条「收获学习未确认」，
     * 看着像模块坏了（实机：番茄 stage_1 / stage_2 / stage_3 各播一条未确认，试到 stage_4 才确认）。
     * 降序后一次命中，最多再补一次。</p>
     *
     * <p>取值都落在 1~9：排在「需要补种（0）」之后、「无需学习（90）」之前，只调整同批试探的先后。</p>
     */
    private static int probeOrder(StardewFarmScanner.Cell cell) {
        int stage = trailingNumber(cell.crop().stageName());
        if (stage <= 0) return 5;
        return 1 + Math.max(0, 32 - Math.min(32, stage));
    }

    /** 阶段名尾部的数字（{@code stage_4} → 4）；没有数字返回 -1 */
    private static int trailingNumber(String stageName) {
        if (stageName == null) return -1;
        int end = stageName.length();
        int start = end;
        while (start > 0 && Character.isDigit(stageName.charAt(start - 1))) start--;
        if (start == end) return -1;
        try {
            return Integer.parseInt(stageName.substring(start, end));
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    /**
     * 特殊变种缺少专用工具时只提示一次（同一个具体阶段只报一次，绝不每轮刷屏）。
     *
     * <p>拿到那件工具后下一轮扫描自动恢复自动收割 —— 这里只是「没工具就先不动它」的安全闸，
     * 绝不回退成随便拿一件别的：在要求专用工具的服上，拿错工具可能把作物砸坏（不可逆）。
     * 也不停机、不影响收其他作物（用户 2026-09-22：「提示一下就好了，不影响收割其他的菜」）。</p>
     */
    private TaskType skipSpecialWithoutTool(CropRecognizer.CropRecognition crop) {
        String key = learningKey(String.valueOf(crop.cropKey()), String.valueOf(crop.stageName()));
        if (owner.reportedSpecialStages.add(key)) {
            StardewSpecialHarvestRecipe recipe = owner.specialHarvestRecipe(crop);
            // 只是提示，不停机：这一格跳过，其余任务照常推进；放上那件工具下一轮扫描就自动收。
            owner.status.state("SPECIAL_TOOL:" + key, "特殊作物缺少专用工具",
                owner.reporter.cropLabel(crop.cropKey()) + " ▸ 本服口径：" + recipe.displayName()
                    + "，背包/副手里没有" + recipe.toolDisplayName() + "｜这一格先跳过，不影响收其他作物，拿到后自动继续");
        }
        return null;
    }

    /**
     * 这一格所属区域缺对应种子时，只提示一次并跳过该区。
     *
     * <p>口径来自定稿规格：缺种子不拦模块启动、不停机、更不会用别的种子顶替；
     * 同一条（同一区域）只报一次，避免每轮扫描都刷屏。</p>
     */
    private void announceRegionSeedShortage(BlockPos potPos, CropDefinition crop) {
        if (crop == null) return;
        StardewRegionManager.Region region = owner.regionAt(potPos);
        if (region == null || !owner.announcedRegionSeedShortage.add(region.index())) return;
        owner.status.state("REGION_SEED:" + region.index(),
            "区域 " + region.index() + "（" + crop.chineseName() + "）缺种子", "本轮跳过该区域");
    }

    /**
     * 空盆的下一步：先定这一格该种哪种，再按「季节 → 浇水 → 种子 → 施肥」的固定顺序判定。
     *
     * <p><b>混种地会换下一种：</b>缺口最大的那种当季不能种、或背包已经没它的种子时，不能让整块地
     * 跟着它等（缺口不变，它会一直占着名额）。这里把这一种排除后再挑一次；单一作物区排除后挑不出
     * 别的，循环随即结束，行为与原来一致。</p>
     */
    private TaskType resolveEmptyPot(StardewFarmScanner.Cell cell) {
        Set<String> tried = new HashSet<>();
        while (true) {
            CropDefinition crop = cropOf(cell.potPos(), tried);
            if (crop == null) return null;
            tried.add(crop.cropKey());
            ItemStack seedStack = owner.inventory.findSeedStack(crop);
            // 季节闸门：当季不允许时，先看这一口盆上方 5 格内有没有已选温室玻璃——
            // 有罩（物品 lore：防止作物因季节变化枯萎）说明服务器会正常照料这株作物，照常播种；
            // 没罩才按原口径播报并跳过。判定逐格做，因为服务器是每口盆各算各的。
            if (owner.seasonService.plantingStatus(crop, seedStack) == StardewSeasonService.PlantingStatus.DISALLOWED) {
                if (StardewShelterProbe.sheltered(cell.potPos(), owner.index, owner.selectedShelterKeys)) {
                    announceSheltered(crop);
                } else {
                    announceSeasonBlock(crop);
                    if (isMixedRegionAt(cell.potPos())) continue;
                    return null;
                }
            }
            // 季节合法性高于一切空盆动作；通过后，干盆必须先完成 WATER，再进入种子检查。
            if (needsSupply(cell)) {
                TaskType refill = resolveRefill(cell);
                if (refill != null) return refill;
                // 只有下界盆 / 末地盆缺料时才卡住不种；
                // 普通盆沿用原口径——水量未知也允许先种进干盆，后续再浇。
                if (cell.potGroup().refillItem() != null) {
                    if (isMixedRegionAt(cell.potPos())) continue;
                    return null;
                }
            }
            if (owner.inventory.countSeed(crop) <= 0) {
                // 缺种子只跳过这一块地并提示一次，不拦其它区域、也不停机（绝不用别的种子顶替）
                announceRegionSeedShortage(cell.potPos(), crop);
                if (isMixedRegionAt(cell.potPos())) continue;
                return null;
            }
            // 自动施肥开启且有肥料可选，且该盆被选中类型允许施肥时，先施肥后播种
            if (owner.autoFertilize && owner.executor.hasSelectedFertilizer() && potMatchesSelection(cell)) {
                if (cell.potGroup() == PotGroup.NORMAL) return TaskType.FERTILIZE;
                // 下界盆 / 末地盆没有肥料槽（攻略：两种花盆均无法施肥），施肥会白白消耗一个肥料
                announceNoFertilize(cell.potGroup());
            }
            return TaskType.PLANT;
        }
    }

    /**
     * 季节不允许时的作物级阻塞：只暂停该 cropKey 的播种（其它作物与其它任务照常）。
     * 同一作物 + 同一季节 + 同一原因只播报一次，绝不刷屏。
     */
    private void announceSeasonBlock(CropDefinition crop) {
        String label = owner.reporter.seasonPlayerLabel();
        owner.seasonBlockedCrops.add(crop.cropKey());
        if (owner.announcedSeasonBlocks.add(crop.cropKey() + '\u0000' + label)) {
            owner.status.seasonBlocked(owner.reporter.seasonKey("限制", crop.cropKey(), label), crop.chineseName(), label);
        }
    }

    /**
     * 温室玻璃罩着、当季照常播种：同一作物 + 同一季节只播一次。
     *
     * <p>顺便把这种作物从「季节阻塞」集合里摘掉：它此刻确实在种，留着会在整片地种满后
     * 又播一条「等待季节」，让玩家以为模块卡在季节上。摘掉后若集合已空，
     * 连「等待季节」的播报去重键一起复位（与 {@code releaseSeasonBlocks} 同一口径）。</p>
     */
    private void announceSheltered(CropDefinition crop) {
        String label = owner.reporter.seasonPlayerLabel();
        if (owner.seasonBlockedCrops.remove(crop.cropKey())) {
            owner.announcedSeasonBlocks.removeIf(key -> key.startsWith(crop.cropKey() + '\u0000'));
            if (owner.seasonBlockedCrops.isEmpty()) owner.waitingSeasonAnnouncedLabel = null;
        }
        if (owner.announcedShelterCrops.add(crop.cropKey() + '\u0000' + label)) {
            owner.status.shelterPlanting(owner.reporter.seasonKey("温室", crop.cropKey(), label),
                crop.chineseName(), label);
        }
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

    /** 这一格该种哪种作物；没有目标返回 {@code null}（未分区 / 区域未勾选 / 混种区没有可种的已勾选作物） */
    private String plantCropKey(BlockPos potPos) {
        return regionCropKeyFor(potPos, Set.of());
    }

    /** 同上，但跳过 {@code exclude} 里的作物（空盆流程里已经试过、暂时用不上的那些） */
    private String plantCropKey(BlockPos potPos, Set<String> exclude) {
        return regionCropKeyFor(potPos, exclude);
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
        return cropOf(potPos, Set.of());
    }

    /** 同上，但跳过 {@code exclude} 里的作物 */
    private CropDefinition cropOf(BlockPos potPos, Set<String> exclude) {
        String key = plantCropKey(potPos, exclude);
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
                // 下界盆 / 末地盆的 REFILL 是去物料箱取料，导航目标是箱子站位而不是补水点
                StardewPointType materialBox = StardewPointType.materialBoxFor(owner.executor.targetPotGroup());
                if (materialBox != null) {
                    yield owner.containerApproach == null ? null : owner.containerApproach.standPos();
                }
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

    /**
     * 本维度<b>可维护</b>的洒水器点位。
     *
     * <p>「已确认不在了 / 绑定已失效」的点位在这里就被剔除（真机事故：洒水器被挖掉后，模块照常
     * 走过去「维护」、算进「本轮 N 台」、ESP 方框照画）。剔除以点位校验的确定结论为准，
     * 「区块还没加载」不算——那种情况正是要走过去把区块加载出来才能判。</p>
     *
     * <p>剔除的同时播报一次（同一台只提示一次，见 {@link StardewCoordinator#markSprinklerMissingReported}），
     * 让玩家知道这台已经不在了、点位该清掉；它重新出现后标记解除。</p>
     */
    List<StardewPointManager.StardewPoint> sprinklerPointsInDimension() {
        List<StardewPointManager.StardewPoint> result = new ArrayList<>();
        for (StardewPointManager.StardewPoint p : owner.points.getAll(StardewPointType.SPRINKLER)) {
            if (!p.inCurrentDimension()) continue;
            String unusable = owner.points.sprinklerUnusableReason(p, owner.index);
            if (unusable != null) {
                if (owner.markSprinklerMissingReported(p.pos())) {
                    owner.reporter.announceSprinklerMissing(p.pos(), unusable);
                }
                continue;
            }
            owner.clearSprinklerMissingReported(p.pos());
            result.add(p);
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
        int freeSlots = playerFreeSlots();
        if (freeSlots > PLAYER_LOW_FREE_SLOTS) return false;
        StardewPointManager.StardewPoint outputBox = owner.points.get(StardewPointType.OUTPUT_BOX);
        if (outputBox == null || !outputBox.inCurrentDimension()) return false;
        // 背包一个空格都不剩时，该作物自己的「卸货阈值」不再决定资格：只要还有一件超出保留量的成品
        // 就得先去卸。否则「背包被别的东西塞满、成品数没到阈值」会让插队永远不成立，模块继续收菜
        // （实机：背包装满 36 格金锭，模块照收不误）。
        boolean bagFull = freeSlots <= 0;
        for (CropDefinition crop : targetCrops()) {
            boolean need = bagFull
                ? owner.inventory.countProduce(crop) > owner.logisticsOf(crop.cropKey()).unloadKeep()
                : owner.snapshot(crop).unloadNeeded();
            if (need && !owner.logistics.logisticsBackedOff(owner.unloadBlockedUntil, crop.cropKey())) {
                return startLogisticsTask(TaskType.UNLOAD, outputBox, crop);
            }
        }
        return false;
    }

    /** 背包里还有「超出保留量的成品」可卸吗（用来区分「没得卸」与「卸不出去」） */
    boolean hasUnloadableProduce() {
        if (owner.inventory == null) return false;
        for (CropDefinition crop : targetCrops()) {
            if (owner.inventory.countProduce(crop) > owner.logisticsOf(crop.cropKey()).unloadKeep()) return true;
        }
        return false;
    }

    /**
     * 背包已满（主背包 36 格一个空格都没有）时是否停手。
     *
     * <p><b>为什么必须停</b>：收割 / 学习探测都会产出物品、拾取本身就是在装东西，而此刻一件都放不进去 ——
     * 继续做只会把成品掉在地上（被别的玩家捡走或 5 分钟后消失）。停的时候必须报出来，
     * 否则玩家只看到脚本站着不动。</p>
     *
     * <p><b>拦哪些动作：</b>一切「会把东西拿到手里」的 —— 右键采收、特殊变种收割、拾取，
     * 以及清枯苗 / 清错位 / 清杂物这些左键破坏（破坏同样掉物品）。浇水 / 播种 / 施肥不产出新物品，
     * 照常执行。</p>
     */
    boolean bagFullBlocksHarvest() {
        if (!playerBagFull()) {
            owner.forgetBagFullHalt();
            return false;
        }
        owner.reportBagFullHalt(unloadBlockReason());
        return true;
    }

    /** 背包一个空格都没有：收菜 / 拾取 / 学习探测 / 左键破坏的产出都没地方放 */
    boolean playerBagFull() {
        // 没玩家（断线 / 换世界途中）不按「满」处理：那时 playerFreeSlots() 返回 0，
        // 直接用它会把「读不到背包」误报成「背包已满」。
        if (Minecraft.getInstance().player == null) return false;
        return playerFreeSlots() <= 0;
    }

    /** 背包满了却卸不出去的原因（用于告诉玩家到底卡在哪一步） */
    private String unloadBlockReason() {
        if (!hasUnloadableProduce()) return "36 格全被占满，而且里面没有可卸的成品（占格的是种子 / 工具 / 杂物）";
        StardewPointManager.StardewPoint outputBox = owner.points.get(StardewPointType.OUTPUT_BOX);
        if (outputBox == null) return "还没绑定产物箱，成品卸不出去";
        if (!outputBox.inCurrentDimension()) return "产物箱不在当前维度，成品卸不出去";
        return "产物箱没有空间，成品卸不出去";
    }

    /** 主背包空格数；口径只此一处（见 {@link StardewInventoryService#freeMainSlots()}） */
    private int playerFreeSlots() {
        return owner.inventory == null ? 0 : owner.inventory.freeMainSlots();
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

    /**
     * 季节已允许（或有温室玻璃豁免）、盆已浇湿、背包确实无种子的空盆才构成 Restock Demand。
     *
     * <p><b>混种地例外：</b>混种地会把「缺口最大的那种」排在前面种，缺种子那种的盆可能先被别的作物
     * 占走，于是「空盆的计划作物」这条判据就再也轮不到它——这一种在混种地里会永远消失。只要洞里
     * 还有混种区域、这一种又确实还有配额缺口，就直接构成补货需求；配额种满即自动停止。</p>
     *
     * <p><b>温室玻璃必须与播种同一口径：</b>盆上方有玻璃的格子当季就能种，若这里仍按「季节不允许」
     * 跳过，会出现「播种植得下、却永远不去种子箱取种」的冬季死角（种子在箱子里的常态场景）。</p>
     */
    boolean hasRestockDemand(CropDefinition crop) {
        // 触发门槛就是「背包里一颗种子都不剩」（countSeed > 0 即不补），开关是「种子补货触发」：
        // 0 = 关掉这种作物的自动补货（也就不需要给它绑种子箱）。这里的关闭判据必须与
        // 「是否需要种子箱」的自检口径一致（StardewFarmModule / StardewStartupCheck 都看 restockTrigger），
        // 否则会出现「界面说关着、后台照样去补」这种对不上的状态（2026-09-22 统一）。
        if (crop == null || owner.inventory.countSeed(crop) > 0
            || owner.logisticsOf(crop.cropKey()).restockTrigger() <= 0) return false;
        if (mixedRegionWants(crop.cropKey())) return true;
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.crop().state() != CropState.EMPTY || !potMatchesSelection(cell)
                || !crop.cropKey().equals(plantCropKey(cell.potPos()))) continue;
            StardewSeasonService.PlantingStatus season = owner.seasonService.plantingStatus(crop, owner.inventory.findSeedStack(crop));
            if (season == StardewSeasonService.PlantingStatus.DISALLOWED
                && !StardewShelterProbe.sheltered(cell.potPos(), owner.index, owner.selectedShelterKeys)) continue;
            if (needsSupply(cell)) continue;
            return true;
        }
        return false;
    }

    /** 混种地还愿意种这一种吗：存在混种区域，且该作物已勾选、可识别、配额还没种满 */
    private boolean mixedRegionWants(String cropKey) {
        if (owner.index == null || cropKey == null) return false;
        if (!owner.selectedCropKeys.contains(cropKey) || owner.index.cropByKey(cropKey) == null) return false;
        if (plannedCellCount(cropKey) >= cropActualTarget(cropKey)) return false;
        for (StardewRegionManager.Region region : owner.regions) {
            if (region.mixed()) return true;
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
        // 取料的 REFILL 目标是箱子站位，必须按「站到箱子外围」的口径判定，与补货 / 卸货一致；
        // 沿用去水源的宽松口径（1.35）会让脚本在「够不到箱子」的位置自认为已到达
        if (owner.taskType == TaskType.REFILL) return materialRefill() ? CONTAINER_STAND_REACH : REFILL_REACH;
        if (owner.taskType == TaskType.RESTOCK || owner.taskType == TaskType.UNLOAD
            || owner.taskType == TaskType.SEED_RETURN) return CONTAINER_STAND_REACH;
        return owner.reach;
    }

    /**
     * Baritone 的 GoalNear 半径必须与任务语义一致。
     *
     * <p>拾取用 0：GoalNear 半径 0 表示「玩家所在方格必须就是掉落物所在方格」，
     * 这是唯一能保证走进磁吸范围的半径（1 格只保证「在附近」，真机上正好差一点点捡不到）。</p>
     *
     * <p>取料的 REFILL 同理用 0：箱子站位要求「站进那一格」，半径 1 会让玩家停在旁边一格，
     * 于是卡在「已到达 → 够不到箱子 → 重新导航 → 还是原地」的死循环里（实机反馈：站在岩浆箱
     * 边上一直不动、永远不拿）。</p>
     */
    int navigationRadius() {
        if (owner.taskType == TaskType.RETURN_CENTER || owner.taskType == TaskType.RESTOCK
            || owner.taskType == TaskType.UNLOAD || owner.taskType == TaskType.SEED_RETURN
            || owner.taskType == TaskType.COLLECT) return 0;
        if (owner.taskType == TaskType.REFILL) return materialRefill() ? 0 : 1;
        return 2;
    }

    /**
     * 本次 REFILL 是「去物料箱取料」（下界盆的岩浆 / 末地盆的龙息），而不是「去水源补壶」。
     *
     * <p>判定口径与 {@link #taskTarget()} 里 REFILL 分支完全一致：同一个物料箱判据，
     * 两处必须同源，否则导航目标与到达判定会互相打架。</p>
     */
    private boolean materialRefill() {
        return owner.taskType == TaskType.REFILL
            && StardewPointType.materialBoxFor(owner.executor.targetPotGroup()) != null;
    }

    /**
     * 回中心点：离玩家最近的区域的「站得住的中心」。
     *
     * <p>区域并集的几何中心可能落在走道、田外甚至另一块地里，那儿站着不产生任何任务；
     * 「最近的那块地」才是玩家眼里自然的待命位置。</p>
     */
    BlockPos regionCenter() {
        BlockPos player = Minecraft.getInstance().player == null
            ? null : Minecraft.getInstance().player.blockPosition();
        StardewRegionManager.Region nearest = null;
        double bestDistance = Double.MAX_VALUE;
        for (StardewRegionManager.Region region : owner.regions) {
            double distance = player == null ? 0 : region.center().distSqr(player);
            if (distance < bestDistance) {
                bestDistance = distance;
                nearest = region;
            }
        }
        return nearest == null ? null : standableCenter(nearest);
    }

    /** 回中心落点最多向下找几格：作物载体 / 台阶会把几何中心抬高一到两格。 */
    private static final int CENTER_STAND_DROP = 3;

    /**
     * 把区域几何中心落到一个「人真能站上去」的格子。
     *
     * <p><b>为什么必须落地：</b>几何中心只保证数学居中。这些服务器的作物载体（甘蔗、绊线之类）
     * 与泥土、箱子、玻璃一样占格，中心格很容易「脚下没有支撑」或「整格被占」。回中心用的判定是最严的一套
     * ——{@code GoalNear(半径 0)}（必须站进那一格）叠加 {@code CENTER_REACH = 0.85}，
     * 目标格站不住时 Baritone 永远算不出一条能落地的路径：路径线一直闪、人原地不动，
     * 每刻重发打断计算，80 刻无进展重试 3 次后拉黑并播报「无法继续寻路」
     * （2026-09-19 实机埋点定性：中心 19841,65,-1633 脚下是甘蔗，玩家卡在 3.27 格外）。</p>
     *
     * <p>取法：中心列自上而下，再四邻各自自上而下，取第一个站得住的格（判据与后勤站位同源，
     * 见 {@link ContainerApproachPlanner#standable}），且落点 XZ 必须仍在本区域内。
     * 全部候选都站不住时回退几何中心——保留原有的重试 / 拉黑 / 播报兜底，绝不让回中心彻底失效。</p>
     */
    private BlockPos standableCenter(StardewRegionManager.Region region) {
        BlockPos center = region.center();
        if (inRegionXZ(region, center) && ContainerApproachPlanner.standable(center)) return center;
        for (int drop = 1; drop <= CENTER_STAND_DROP; drop++) {
            BlockPos candidate = center.below(drop);
            if (inRegionXZ(region, candidate) && ContainerApproachPlanner.standable(candidate)) return candidate;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos column = center.relative(direction);
            for (int drop = 0; drop <= CENTER_STAND_DROP; drop++) {
                BlockPos candidate = column.below(drop);
                if (inRegionXZ(region, candidate) && ContainerApproachPlanner.standable(candidate)) return candidate;
            }
        }
        return center;
    }

    /** 落点是否仍落在该区域的 XZ 范围内（回中心不许跑到别的地块或田外）。 */
    private static boolean inRegionXZ(StardewRegionManager.Region region, BlockPos pos) {
        return region.containsXZ(pos.getX(), pos.getZ());
    }

    BlockPos regionMin() {
        return regionBounds(true);
    }

    BlockPos regionMax() {
        return regionBounds(false);
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
