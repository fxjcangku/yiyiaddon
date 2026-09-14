package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.service.container.ContainerService;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.feature.stardew.adapter.StardewAdapter;
import com.yiyiaddon.feature.stardew.logistics.StardewLogisticsStore;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.memory.FarmMemoryStore;
import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.status.StardewStatusReporter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 星露谷农场协调器（Observe → Decide → Act → Verify → Replan）。
 *
 * <p>单任务独占、严格串行：同一时刻只有一个任务在推进，超时 / 失败先重新观察，
 * 绝不重复发送可能已经成功的破坏性动作。所有破坏性动作（收割 / 清理 / 种植 / 施肥）
 * 执行后都以真实世界观察为准验证，空盆 → 补种、植株回退 → 不补种、仍存在 → 重观察。</p>
 *
 * <p>资源依赖只阻塞相关任务：缺种子不阻塞浇水 / 收割 / 拾取，种子箱空不停止整个农场。
 * 成熟判定来自服务器档案（回退文档化规则），绝不猜 {@code max(stage)}。</p>
 *
 * <p>本类只保留状态与主循环骨架；决策 / 执行 / 后勤 / 验证 / 播报 / 拾取已机械拆分到
 * {@link StardewTaskPlanner}、{@link StardewFarmExecutor}、{@link StardewContainerLogistics}、
 * {@link StardewTaskVerifier}、{@link StardewFarmReporter}、{@link StardewDropCollector}，
 * 全部共享本类的可变状态，行为与拆分前完全一致。</p>
 */
public final class StardewCoordinator {

    /** 自动学习成功后交给模块持久化的完整收获事实。 */
    public record LearnedHarvest(String cropKey, String matureStage,
                                 StardewCropLifecycle lifecycle, String afterHarvestStage) {
    }

    public enum Phase {
        OBSERVE, DECIDE, NAVIGATE, INTERACT, VERIFY, REPLAN
    }

    static final int MAX_RETRY = 3;
    static final int DEFAULT_VERIFY_TICKS = 8;
    static final int LOGISTICS_COOLDOWN = 200;
    /** 关闭箱无法监听外部玩家改动时的保险重查；正常解锁优先由背包/掉落/配置事件驱动。 */
    static final long RESTOCK_SAFETY_RECHECK_MS = 15L * 60_000L;
    static final long LOGISTICS_BLOCK_RETRY_MS = 60_000L;
    static final long NAVIGATION_BLOCK_RETRY_MS = 10_000L;
    static final int NAVIGATION_NO_PROGRESS_TICKS = 80;
    static final int NAVIGATION_TIMEOUT_TICKS = 240;
    static final double NAVIGATION_PROGRESS_SQ = 0.04;
    static final int REFILL_MAX_PACKETS = 32;
    /**
     * 补水单 tick 连发硬上限。
     *
     * <p>已知水壶容量时，一轮就能把差值一次发完（瞬间补满）；这个上限保证即便容量很大，
     * 也不会在一 tick 内刷出几十个交互包。</p>
     */
    static final int REFILL_BURST_HARD_CAP = 16;
    /**
     * 拾取判定半径（格），量的是「玩家到掉落物真实坐标」的距离。
     *
     * <p>服务端的拾取是「玩家碰撞箱外扩 1 格后与掉落物碰撞箱相交」，水平方向约 1.42 格
     * （1.0 + 玩家半宽 0.3 + 掉落物半宽 0.125），这里取 1.0 留余量。</p>
     *
     * <p><b>为什么不能沿用 1.25：</b>那是量到「掉落物所在方块的中心」。掉落物贴在方格角落时，
     * 玩家虽然满足「到方块中心 ≤ 1.25」，到掉落物本身却可能隔着 1.9 格 —— 正好在磁吸之外，
     * 于是出现「站在旁边捡不起来」，每 10 秒重试一次，直到掉落物 5 分钟后过期消失。</p>
     */
    static final double COLLECT_REACH = 1.0;
    /** 拾取「最后一步」直走的最长 tick 数：一秒内走不到就交给重试与退避，绝不一直按住前进键。 */
    static final int MAX_NUDGE_TICKS = 20;
    /** 视角同步：每 tick 最多转多少度（约 0.2 秒转 90°） */
    static final float VIEW_TURN_STEP = 15.0f;
    /** 动手前的朝向容差（度）：进入这个范围才发交互包 */
    static final float VIEW_ALIGN_TOLERANCE = 25.0f;
    /** 等待视角转正的最长 tick 数：超时直接放行，绝不因为转头把任务卡住 */
    static final int VIEW_ALIGN_WAIT_TICKS = 20;
    static final double CENTER_REACH = 0.85;
    static final double REFILL_REACH = 1.35;
    static final double CONTAINER_STAND_REACH = 0.9;
    static final double PLAYER_MOVE_EPSILON_SQ = 0.0025;
    /** 背包空格降到该值及以下时，卸货提前插队，避免成品掉地。 */
    static final int PLAYER_LOW_FREE_SLOTS = 6;
    /** 精确移动（非整叠）时单次交互最多连发的点击次数，超出部分下一轮继续。 */
    static final int MAX_TRANSFER_BURST = 16;
    /** 「批量右击」的硬上限：再多也不会发，避免一 tick 内刷出一串交互包。 */
    static final int MAX_BATCH_ACTIONS = 4;

    final StardewFarmScanner scanner;
    final StardewAdapter adapter;
    final StardewSeasonService seasonService;

    // ── 运行时配置（模块 onTick 同步） ──
    StardewServerProfile profile = null;
    StardewResourceIndex index = null;
    IdentityService idManager = null;
    FarmMemoryStore memory = null;
    StardewPointManager points = null;
    StardewInventoryService inventory = null;

    List<String> selectedCropKeys = List.of();
    List<String> selectedPotKeys = List.of();
    List<String> selectedCanKeys = List.of();
    List<String> selectedFertilizerKeys = List.of();
    List<String> selectedPotionKeys = List.of();
    List<String> selectedSprinklerKeys = List.of();

    String serverKey = "";
    String dimension = "";
    int reach = 4;
    int scanBudget = 16;
    boolean wateringEnabled = true;
    boolean switchCan = true;
    boolean restoreHand = true;
    boolean autoFertilize = false;
    boolean autoPotion = false;
    boolean sprinklerMaintenance = false;
    int sprinklerInterval = 1200;
    int returnCenterDelayTicks = 200;

    /**
     * 同一 tick 最多对几个格子右键（1 = 关闭）。
     *
     * <p>只影响「顺带多打几个」的额外发包，主目标仍然走完整的 决策 → 导航 → 交互 → 验证；
     * 额外目标不做断言（{@link #verifyResult()} 只看主目标自己那一格），
     * 服务端没执行的话下一轮观察会重新把它当待办，因此不存在「以为收了其实没收」。</p>
     */
    int batchActions = 1;

    int restockTrigger = 2;
    int restockTarget = 8;
    int unloadTrigger = 8;
    int unloadKeep = 0;

    /**
     * 「按作物」解析后勤阈值。
     *
     * <p>默认实现退回上面那四个全局值（模块里的「默认后勤参数」），模块启动时会被替换成
     * 查 {@code StardewLogisticsStore} 的实现——即每个 {@code cropKey} 各读自己的一套阈值。
     * 因此番茄的种子数不会满足玉米的补货缺口，卸货保留量也不会互相牵连。</p>
     */
    java.util.function.Function<String, StardewLogisticsStore.CropLogistics> logisticsResolver =
        cropKey -> new StardewLogisticsStore.CropLogistics(restockTrigger, restockTarget, unloadTrigger, unloadKeep);

    /** 当前隔离域的收获规则查询；模块用字段捕获 lambda 注入，换服后会读取最新映射。 */
    Function<String, StardewHarvestRule> harvestRuleResolver = cropKey -> null;
    /** 学习成功回调由模块完成原子持久化，协调器不直接接触磁盘。 */
    Consumer<LearnedHarvest> harvestLearningListener = learned -> { };
    /** 配置页与聊天共用的唯一状态出口。 */
    StardewStatusReporter status = new StardewStatusReporter(null);
    /** 作物数量配置只在这里换算成唯一实际个数。 */
    Function<String, StardewCropPlanStore.CropPlan> cropPlanResolver = key -> StardewCropPlanStore.CropPlan.DEFAULT;

    /** 注入「按作物取后勤阈值」的解析器（模块在构造时注入一次） */
    public void setLogisticsResolver(
        java.util.function.Function<String, StardewLogisticsStore.CropLogistics> resolver) {
        if (resolver != null) this.logisticsResolver = resolver;
    }

    /** 注入当前 ServerKey + fingerprint + cropKey 的规则解析器。 */
    public void setHarvestRuleResolver(Function<String, StardewHarvestRule> resolver) {
        if (resolver != null) this.harvestRuleResolver = resolver;
    }

    /** 注入自动学习结果接收器。 */
    public void setHarvestLearningListener(Consumer<LearnedHarvest> listener) {
        if (listener != null) this.harvestLearningListener = listener;
    }

    /** 取某种作物自己的后勤阈值（未配置时由解析器给出默认值） */
    StardewLogisticsStore.CropLogistics logisticsOf(String cropKey) {
        StardewLogisticsStore.CropLogistics value = logisticsResolver.apply(cropKey);
        return value == null ? StardewLogisticsStore.CropLogistics.DEFAULT : value;
    }

    // ── 状态机 ──
    Phase phase = Phase.OBSERVE;
    TaskType taskType;
    BlockPos targetPot;
    StardewFarmScanner.Cell activeCell;
    int stepTick;
    int retryCount;
    /** 视角同步：本轮「动手前等视线转正」已等待的 tick 数 */
    int viewAlignWait;

    // ── 多步任务子状态（后勤 / 补水 / 施肥 / 洒水器） ──
    final ContainerService broker = new ContainerService();
    BlockPos targetContainer;
    ContainerApproachPlanner.Approach containerApproach;
    CropDefinition activeCrop;
    int taskStep;
    int taskTicks;
    int logisticsCooldown;
    Integer refillLastWater = null;
    int refillAttempts;
    boolean refillMadeProgress;
    int refillStableChecks;
    /** 本任务读到的水壶容量（Tooltip / LORE / custom_data）；读不到为 null，绝不伪造 */
    Integer refillCapacity = null;
    /** 自学习的「单个交互包补多少水」，跨任务保留，只在世界 / 服务器上下文重置时清空 */
    Integer refillPerPacket = null;
    /** 本轮连发了几包，用于把「一轮增量」换算成单包增量 */
    int refillBurstSent;
    boolean forceRefill;
    boolean logisticsBlocked;
    /** 空种子箱负缓存：记录当时背包种子数与箱点，真实事件变化立即解除。 */
    record RestockBlock(long safetyRetryAt, int seedCountAtBlock, BlockPos seedBoxPos) {
    }

    public void setStatusReporter(StardewStatusReporter reporter) {
        if (reporter != null) this.status = reporter;
    }

    public void setCropPlanResolver(Function<String, StardewCropPlanStore.CropPlan> resolver) {
        if (resolver != null) this.cropPlanResolver = resolver;
    }
    final Map<String, RestockBlock> restockBlocks = new HashMap<>();
    final Map<String, Integer> lastBlockedSeedCounts = new HashMap<>();
    final Map<String, Long> unloadBlockedUntil = new HashMap<>();
    /** 种子回收负缓存：箱内暂无空间 / 交互失败后短时退避，避免死循环开箱。 */
    final Map<String, Long> seedReturnBlockedUntil = new HashMap<>();
    final Map<String, Long> navigationBlockedUntil = new HashMap<>();
    int navigationTicks;
    int navigationNoProgressTicks;
    double navigationBestDistanceSq = Double.MAX_VALUE;
    int sprinklerCursor;
    long nextSprinklerCheckTick;
    boolean playerPositionKnown;
    double lastPlayerX;
    double lastPlayerY;
    double lastPlayerZ;
    int stationaryTicks;
    boolean waitingMatureNotified;
    /**
     * 已播报过「等待季节」的季节标签；null 表示未播。
     *
     * <p>同一季节只在「没有其它可执行任务且仍有作物被季节阻塞」时播一次；
     * 季节变化（标签变化）后允许重新播报一次，避免玩家永远看不到新季节。</p>
     */
    String waitingSeasonAnnouncedLabel;
    long observedSeasonRevision = -1L;
    /**
     * 本决策轮真实被季节阻塞的待播种作物 cropKey（每轮 DECIDE 重算，不留旧账）。
     *
     * <p>季节限制严格按 cropKey 独立生效：只有被阻塞作物自己的 Plant 被暂停，
     * 其它作物与 Harvest / Collect / Water / DEAD / Unload 全部照常执行；
     * 季节代次变化时逐个重新判定，允许播种的作物立即释放。</p>
     */
    final Set<String> seasonBlockedCrops = new HashSet<>();
    /** 已播报过的「作物 + 季节」阻塞组合：重算不会重复刷屏，季节变化后允许再次播报 */
    final Set<String> announcedSeasonBlocks = new HashSet<>();
    /** 本次会话已播报过回收完成的作物：保株作物会周期性回收，避免每次收获都刷一条 */
    final Set<String> announcedSeedReturns = new HashSet<>();
    /** 已上报过「拾取不到掉落物」的坐标（每处只报一次，避免每 10 秒刷屏）。 */
    final Set<String> reportedCollectFailures = new HashSet<>();
    /** 拾取「最后一步」已直走的 tick 数，以及本模块是否正按着前进 / 跳跃键（用于释放）。 */
    int nudgeTicks;
    boolean nudgeHoldForward;
    boolean nudgeHoldJump;
    final Set<String> reportedContainerFailures = new HashSet<>();
    String configuredServerKey = "";
    String configuredFingerprint;
    List<String> configuredCropKeys = List.of();
    BlockPos configuredSeedBox;
    long configuredPlanRevision = -1L;
    long configuredLogisticsRevision = -1L;

    // ── 收获学习快照 ──
    /** 同一运行会话每个作物阶段最多探测一次，避免对未成熟阶段循环右键刷包。 */
    final Set<String> probedLearningStages = new HashSet<>();
    /** 特殊变种只提示一次人工校准，禁止每轮扫描刷屏。 */
    final Set<String> reportedSpecialStages = new HashSet<>();
    String learningCropKey;
    String learningMatureStage;
    String learningBeforeIdentity;
    int learningBeforeInventory;
    int learningBeforeDrops;
    boolean learningInteractionSent;
    final Set<String> reportedLearningFailures = new HashSet<>();

    // ── 主手切换恢复 ──
    boolean handSwapped;
    int savedSelectedSlot = -1;
    int swappedInvSlot = -1;

    final List<StardewFarmScanner.Cell> pending = new ArrayList<>();
    Map<String, Integer> taskInventoryBefore = Map.of();
    int taskSeedBefore;
    /** 本次任务目标作物自己的种子数（回收完成量按它算，不能用全部已选作物的合计）。 */
    int taskCropSeedBefore;
    int taskProduceBefore;
    boolean farmBoundaryBlocked;

    // ── 拆分后的职责协作者（只共享本类可变状态，不新增任何状态） ──
    final StardewTaskPlanner planner = new StardewTaskPlanner(this);
    final StardewFarmExecutor executor = new StardewFarmExecutor(this);
    final StardewContainerLogistics logistics = new StardewContainerLogistics(this);
    final StardewTaskVerifier verifier = new StardewTaskVerifier(this);
    final StardewFarmReporter reporter = new StardewFarmReporter(this);
    final StardewDropCollector drops = new StardewDropCollector(this);

    public StardewCoordinator(StardewFarmScanner scanner, StardewAdapter adapter,
                              StardewSeasonService seasonService) {
        this.scanner = scanner;
        this.adapter = adapter;
        this.seasonService = seasonService;
    }

    public void configure(StardewServerProfile profile, StardewResourceIndex index, IdentityService idManager,
                          FarmMemoryStore memory, StardewPointManager points, StardewInventoryService inventory,
                          List<String> selectedCropKeys, List<String> selectedPotKeys, List<String> selectedCanKeys,
                          List<String> selectedFertilizerKeys, List<String> selectedPotionKeys, List<String> selectedSprinklerKeys,
                          String serverKey, String dimension, int reach, int scanBudget,
                          boolean wateringEnabled, boolean switchCan, boolean restoreHand,
                          boolean autoFertilize, boolean autoPotion,
                          boolean sprinklerMaintenance, int sprinklerInterval,
                          int restockTrigger, int restockTarget, int unloadTrigger, int unloadKeep,
                          int returnCenterDelaySeconds, int batchActions) {
        List<String> newCropKeys = selectedCropKeys == null ? List.of() : List.copyOf(selectedCropKeys);
        BlockPos newSeedBox = points == null || points.get(StardewPointType.SEED_BOX) == null
            ? null : points.get(StardewPointType.SEED_BOX).pos();
        String newFingerprint = com.yiyiaddon.service.resourcepack.ResourceExtractionService.fingerprint();
        if (!java.util.Objects.equals(configuredServerKey, serverKey)
            || !java.util.Objects.equals(configuredFingerprint, newFingerprint)
            || !configuredCropKeys.equals(newCropKeys)
            || !java.util.Objects.equals(configuredSeedBox, newSeedBox)
            || configuredPlanRevision != StardewCropPlanStore.revision()
            || configuredLogisticsRevision != StardewLogisticsStore.revision()) {
            restockBlocks.clear();
            lastBlockedSeedCounts.clear();
            seasonBlockedCrops.clear();
            announcedSeasonBlocks.clear();
            announcedSeedReturns.clear();
            waitingSeasonAnnouncedLabel = null;
            reportedContainerFailures.clear();
            reportedCollectFailures.clear();
        }
        configuredServerKey = serverKey;
        configuredFingerprint = newFingerprint;
        configuredCropKeys = newCropKeys;
        configuredSeedBox = newSeedBox;
        configuredPlanRevision = StardewCropPlanStore.revision();
        configuredLogisticsRevision = StardewLogisticsStore.revision();
        this.profile = profile;
        this.index = index;
        this.idManager = idManager;
        this.memory = memory;
        this.points = points;
        this.inventory = inventory;
        this.selectedCropKeys = newCropKeys;
        this.selectedPotKeys = selectedPotKeys == null ? List.of() : selectedPotKeys;
        this.selectedCanKeys = selectedCanKeys == null ? List.of() : selectedCanKeys;
        this.selectedFertilizerKeys = selectedFertilizerKeys == null ? List.of() : selectedFertilizerKeys;
        this.selectedPotionKeys = selectedPotionKeys == null ? List.of() : selectedPotionKeys;
        this.selectedSprinklerKeys = selectedSprinklerKeys == null ? List.of() : selectedSprinklerKeys;
        this.serverKey = serverKey;
        this.dimension = dimension;
        this.reach = reach;
        this.scanBudget = scanBudget;
        this.wateringEnabled = wateringEnabled;
        this.switchCan = switchCan;
        this.restoreHand = restoreHand;
        this.autoFertilize = autoFertilize;
        this.autoPotion = autoPotion;
        this.sprinklerMaintenance = sprinklerMaintenance;
        this.sprinklerInterval = sprinklerInterval;
        this.restockTrigger = restockTrigger;
        this.restockTarget = restockTarget;
        this.unloadTrigger = unloadTrigger;
        this.unloadKeep = unloadKeep;
        this.returnCenterDelayTicks = Math.max(1, returnCenterDelaySeconds) * 20;
        this.batchActions = Math.max(1, Math.min(MAX_BATCH_ACTIONS, batchActions));
        // 启动自检保证此刻背包有种子；先读取一次真实 Tooltip，后续最后一颗种子消耗完时，
        // 季节闸门仍可使用同一 ServerKey + 指纹会话中的已验证规则，绝不会先跑补货再判季节。
        if (index != null && inventory != null) {
            for (String cropKey : newCropKeys) {
                CropDefinition crop = index.cropByKey(cropKey);
                if (crop == null) continue;
                ItemStack seed = inventory.findSeedStack(crop);
                if (!seed.isEmpty()) seasonService.allowedSeasons(crop, seed);
            }
        }
    }

    public void reset() {
        phase = Phase.OBSERVE;
        taskType = null;
        targetPot = null;
        activeCell = null;
        stepTick = 0;
        retryCount = 0;
        viewAlignWait = 0;
        taskStep = 0;
        taskTicks = 0;
        targetContainer = null;
        containerApproach = null;
        activeCrop = null;
        pending.clear();
        executor.restoreHandNow();
        adapter.cancelPath();
        adapter.updatePassableCarriers(Set.of());
        scanner.reset();
        ContainerAccess.closeContainer();
        broker.reset();
        logisticsCooldown = 0;
        refillLastWater = null;
        refillAttempts = 0;
        refillMadeProgress = false;
        refillStableChecks = 0;
        refillCapacity = null;
        refillPerPacket = null;
        refillBurstSent = 0;
        forceRefill = false;
        logisticsBlocked = false;
        restockBlocks.clear();
        lastBlockedSeedCounts.clear();
        unloadBlockedUntil.clear();
        seedReturnBlockedUntil.clear();
        navigationBlockedUntil.clear();
        planner.resetNavigationWatchdog();
        playerPositionKnown = false;
        stationaryTicks = 0;
        waitingMatureNotified = false;
        drops.stopNudge();
        probedLearningStages.clear();
        reportedSpecialStages.clear();
        reportedLearningFailures.clear();
        seasonBlockedCrops.clear();
        announcedSeasonBlocks.clear();
        announcedSeedReturns.clear();
        waitingSeasonAnnouncedLabel = null;
        reportedContainerFailures.clear();
        reportedCollectFailures.clear();
        observedSeasonRevision = seasonService.revision();
        verifier.clearLearningSnapshot();
        taskInventoryBefore = Map.of();
        taskSeedBefore = 0;
        taskCropSeedBefore = 0;
        taskProduceBefore = 0;
        farmBoundaryBlocked = false;
    }

    public TaskType currentTask() {
        return taskType;
    }

    public BlockPos currentTarget() {
        return planner.taskTarget();
    }

    public boolean busy() {
        return taskType != null;
    }

    /** 当前是否正在通过后台菜单操作种子箱或成品箱。 */
    public boolean usingSilentContainer() {
        return taskType == TaskType.RESTOCK || taskType == TaskType.UNLOAD
            || taskType == TaskType.SEED_RETURN;
    }

    /** 玩家主动打开背包时终止后台箱子事务，避免两个菜单争用同一个 containerMenu。 */
    public void interruptSilentContainer() {
        if (!usingSilentContainer()) return;
        ContainerAccess.closeContainer();
        broker.reset();
        adapter.cancelPath();
        adapter.updatePassableCarriers(Set.of());
        phase = Phase.REPLAN;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  主循环
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public void tick() {
        if (profile == null || index == null || idManager == null || memory == null || inventory == null) return;
        if (points == null) return;
        if (reporter.farmBoundaryFailure()) return;
        if (logisticsCooldown > 0) logisticsCooldown--;
        long seasonRevision = seasonService.revision();
        if (observedSeasonRevision != seasonRevision) {
            observedSeasonRevision = seasonRevision;
            // 季节变化只逐个释放「当前季节已允许播种」的作物，等待中的作物保持阻塞且不重复播报
            boolean released = reporter.releaseSeasonBlocks();
            boolean plantTask = taskType == TaskType.PLANT || taskType == TaskType.FERTILIZE;
            boolean idleTask = taskType == null || taskType == TaskType.RETURN_CENTER;
            // 绝不打断与季节无关的真实任务（收割 / 采集 / 浇水 / 清枯苗 / 卸货）
            if (plantTask || (released && idleTask)) {
                adapter.cancelPath();
                phase = Phase.REPLAN;
            }
        }
        logistics.refreshRestockBlocks();
        planner.updatePlayerIdle();

        switch (phase) {
            case OBSERVE -> observe();
            case DECIDE -> decide();
            case NAVIGATE -> navigate();
            case INTERACT -> interact();
            case VERIFY -> verify();
            case REPLAN -> replan();
        }
    }

    private void observe() {
        if (!scanner.bounded()) {
            BlockPos min = planner.regionMin();
            BlockPos max = planner.regionMax();
            if (min == null || max == null) return;
            scanner.begin(min, max);
            pending.clear();
        }
        pending.addAll(scanner.scanTick(profile, scanBudget));
        if (scanner.complete()) {
            planner.refreshPassableFarmCarriers();
            phase = Phase.DECIDE;
        }
    }

    private void decide() {
        boolean waitingForGrowth = pending.stream().anyMatch(planner::isManagedGrowingCell);
        // 季节阻塞每轮重算：只有「本轮真实需要播种却被季节拒绝」的作物才算阻塞，
        // 旧阻塞不会残留（播报去重由 announcedSeasonBlocks 独立承担，重算不会重复刷屏）
        seasonBlockedCrops.clear();

        // 资源保护：背包快满且能卸货时，优先卸货避免掉落损失
        if (planner.tryStartUnloadForProtection()) {
            phase = Phase.NAVIGATE;
            return;
        }

        // 农田内掉落优先拾取（种子自给自足 + 成品入账，必须在继续收割/种植前处理）
        if (drops.hasFarmDrop()) {
            taskType = TaskType.COLLECT;
            targetPot = null;
            activeCell = null;
            retryCount = 0;
            phase = Phase.NAVIGATE;
            reporter.broadcast(TaskType.COLLECT.cn());
            return;
        }

        // 成熟、枯死、缺水任务优先于补种箱：成熟作物可能自产种子，先收割才能真正自给自足。
        if (!pending.isEmpty()) {
            pending.sort(Comparator.comparingInt(planner::priority));
            if (planner.startPendingTask(false)) {
                phase = Phase.NAVIGATE;
                return;
            }
        }

        // 空盆严格按“季节 → 干湿 → 种子”决策：干盆先浇水，有种子直接种。
        if (planner.startPendingTask(true)) {
            phase = Phase.NAVIGATE;
            return;
        }

        // 只有当前快照里仍有合法、已浇湿且缺种子的真实 Plant Demand 才允许补货。
        if (planner.tryStartRestock()) {
            phase = Phase.NAVIGATE;
            return;
        }
        activeCell = null;
        targetPot = null;

        // 洒水器定时维护
        if (sprinklerMaintenance && planner.sprinklerDue()) {
            if (planner.startSprinklerTask()) {
                phase = Phase.NAVIGATE;
                return;
            }
        }

        // 普通后勤（补货 / 卸货）
        if (planner.tryStartLogistics()) {
            phase = Phase.NAVIGATE;
            return;
        }

        // 最低优先级：种子回收。保株作物（如番茄）种满后不再消耗种子，收获又会掉种子，
        // 背包种子只增不减；这里把超出「种子补货目标」的部分存回种子箱。
        // 放在所有实活之后、回中心之前，空闲才做，不抢主线也不影响任何现有判定。
        if (planner.tryStartSeedReturn()) {
            phase = Phase.NAVIGATE;
            return;
        }

        if (waitingForGrowth && !waitingMatureNotified) {
            waitingMatureNotified = true;
            status.state("WAIT_GROWTH", "等待作物生长", "当前农田已处理完成");
        }

        // 所有待播种作物都被季节阻塞、且没有任何可执行任务：模块保持开启，回中心等待。
        // 每个季节只播一次（同一季节标签不再重复播报，绝不刷屏）；季节标签变化后允许再播一次。
        // 季节代次变化时由 releaseSeasonBlocks() 释放并 Replan，不需要玩家重开模块。
        if (!seasonBlockedCrops.isEmpty()) {
            String seasonLabel = reporter.seasonPlayerLabel();
            if (!seasonLabel.equals(waitingSeasonAnnouncedLabel)) {
                waitingSeasonAnnouncedLabel = seasonLabel;
                status.waitingSeason("WAIT_SEASON:" + serverKey + '\u0000' + seasonLabel, seasonLabel);
            }
        }

        // 无任何可执行任务：达到静止等待时间后才回农田中心，再进入下一轮观察
        if (planner.tryReturnCenter()) {
            phase = Phase.NAVIGATE;
            return;
        }
        restartObserve();
    }

    private void navigate() {
        BlockPos target = planner.taskTarget();
        if (target == null) {
            phase = Phase.REPLAN;
            return;
        }
        // 拾取不用「到方块中心」判定：必须真的贴到掉落物身上才进得了服务端磁吸范围。
        // 见 COLLECT_REACH 的说明 —— 这条判定就是「站在旁边捡不起来」的根因修复。
        if (taskType == TaskType.COLLECT) {
            ItemEntity drop = drops.nearestFarmItem();
            if (drop == null) {
                // 已经没有可拾取的农场掉落了：直接重规划，不在旧路径上继续空转
                drops.stopNudge();
                adapter.cancelPath();
                phase = Phase.REPLAN;
                return;
            }
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && drops.collectInRange(drop)) {
                drops.stopNudge();
                adapter.cancelPath();
                planner.resetNavigationWatchdog();
                stepTick = 0;
                phase = Phase.INTERACT;
                return;
            }
            // 已经走到掉落物 2 格以内、Baritone 也不再推进时，剩下的「最后半格」交给直走：
            // GoalNear 只能按整格停靠，站在盆格旁边就差这一点点，磁吸要求真的贴上去。
            if (mc.player != null && mc.player.distanceToSqr(drop) <= 4.0 && !adapter.pathing()
                && drops.nudgeTowardDrop(drop)) {
                planner.updateNavigationWatchdog(target);
                return;
            }
        } else if (adapter.arrived(target, planner.arrivalReach())) {
            adapter.cancelPath();
            planner.resetNavigationWatchdog();
            stepTick = 0;
            phase = Phase.INTERACT;
            return;
        }
        planner.updateNavigationWatchdog(target);
        if (navigationNoProgressTicks >= NAVIGATION_NO_PROGRESS_TICKS
            || navigationTicks >= NAVIGATION_TIMEOUT_TICKS) {
            adapter.cancelPath();
            retryCount++;
            planner.resetNavigationWatchdog();
            if (retryCount >= MAX_RETRY) {
                planner.blockNavigationTarget(taskType, target);
                if (taskType == TaskType.COLLECT) {
                    drops.reportCollectFailure(target);
                } else {
                    status.critical("NAV_BLOCK:" + taskType + ':' + target, "无法继续寻路", "当前任务已释放，稍后重新尝试");
                }
                phase = Phase.REPLAN;
            }
            return;
        }
        if (!adapter.pathing()) {
            int radius = planner.navigationRadius();
            boolean dispatched = adapter.goTo(target, radius);
            // 拾取先压到 0 格（必须走进掉落物所在方格）；掉落物卡在盆上、目标格不可站立时
            // 用 1 格退一步贴到旁边 —— 同样在磁吸范围内，不该直接判成不可达。
            if (!dispatched && taskType == TaskType.COLLECT) dispatched = adapter.goTo(target, 1);
            if (!dispatched) {
                // 寻路失败：释放本任务，重规划，不卡死
                planner.blockNavigationTarget(taskType, target);
                phase = Phase.REPLAN;
            }
        }
    }

    private void interact() {
        if (taskType == null) {
            phase = Phase.REPLAN;
            return;
        }
        // 视角同步：先看着目标再动手；未转到位时本 tick 只转头，不发包
        if (!executor.faceInteractionTarget()) return;
        if (taskType == TaskType.RESTOCK || taskType == TaskType.UNLOAD
            || taskType == TaskType.SEED_RETURN) {
            logistics.interactLogistics();
            return;
        }
        if (taskType == TaskType.COLLECT) {
            // 拾取靠走近磁吸，不主动交互；verify 阶段判断掉落是否已入包
            stepTick = 0;
            phase = Phase.VERIFY;
            return;
        }
        if (taskType == TaskType.SPRINKLER_CHECK || taskType == TaskType.SPRINKLER_REFILL) {
            executor.interactSprinkler();
            return;
        }
        if (taskType == TaskType.REFILL) {
            executor.interactRefill();
            return;
        }
        if (taskType == TaskType.RETURN_CENTER) {
            stepTick = 0;
            phase = Phase.VERIFY;
            return;
        }

        boolean acted = switch (taskType) {
            case HARVEST -> executor.doHarvest();
            case LEARN_HARVEST -> executor.doLearnHarvest();
            case CLEAR_DEAD -> executor.doClearDead();
            case WATER -> executor.doWater();
            case PLANT -> executor.doPlant();
            case FERTILIZE -> executor.doFertilize();
            case POTION -> executor.doPotion();
            default -> false;
        };
        stepTick = 0;
        phase = Phase.VERIFY;
        if (!acted) {
            retryCount++;
            return;
        }
        executor.sendBatchActions();
    }

    private void verify() {
        stepTick++;
        if (stepTick < verifier.verifyDelayTicks()) return;
        if (taskType == TaskType.REFILL) {
            executor.verifyRefill();
            return;
        }
        if (taskType == TaskType.LEARN_HARVEST) {
            verifier.verifyLearningHarvest();
            phase = Phase.REPLAN;
            return;
        }
        boolean success = verifier.verifyResult();
        if (success) {
            verifier.notifyTaskSuccess();
            phase = Phase.REPLAN;
        } else if (taskType == TaskType.WATER && (executor.canWaterIsEmpty() || retryCount >= MAX_RETRY)) {
            // 水量字段缺失时以连续浇水失败兜底判断空壶，下一轮必须先去补水点。
            forceRefill = true;
            phase = Phase.REPLAN;
        } else if (retryCount >= MAX_RETRY) {
            phase = Phase.REPLAN;
        } else {
            retryCount++;
            stepTick = 0;
            phase = Phase.NAVIGATE;
        }
    }

    private void replan() {
        boolean wasLogistics = taskType == TaskType.RESTOCK || taskType == TaskType.UNLOAD
            || taskType == TaskType.SEED_RETURN;
        boolean wasReturnCenter = taskType == TaskType.RETURN_CENTER;
        executor.restoreHandNow();
        drops.stopNudge();
        taskType = null;
        targetPot = null;
        activeCell = null;
        retryCount = 0;
        stepTick = 0;
        viewAlignWait = 0;
        taskStep = 0;
        taskTicks = 0;
        refillLastWater = null;
        refillAttempts = 0;
        refillMadeProgress = false;
        refillStableChecks = 0;
        refillCapacity = null;
        refillBurstSent = 0;
        logisticsBlocked = false;
        planner.resetNavigationWatchdog();
        verifier.clearLearningSnapshot();
        if (!wasLogistics) activeCrop = null;
        if (wasLogistics) {
            logisticsCooldown = LOGISTICS_COOLDOWN;
            targetContainer = null;
            containerApproach = null;
            activeCrop = null;
            ContainerAccess.closeContainer();
            broker.reset();
        }
        if (wasReturnCenter) stationaryTicks = 0;
        restartObserve();
    }

    private void restartObserve() {
        BlockPos min = planner.regionMin();
        BlockPos max = planner.regionMax();
        // 每轮观察只能消费本轮真实快照；保留上一轮空盆会在播种成功后继续对已种植格空种。
        pending.clear();
        if (min != null && max != null) scanner.begin(min, max);
        phase = Phase.OBSERVE;
    }

    /**
     * 单个作物的独立库存状态。
     *
     * <p>每种已选作物各持一份：种子身份、成品身份、种子数、成品数、是否需要补货、是否需要卸货。
     * 番茄种子不能拿去满足玉米的种子缺口，一个作物补到目标值也不会让另一个作物误以为自己补满。</p>
     */
    public record CropInventoryState(
        String cropKey,
        String seedIdentity,
        List<String> produceIdentities,
        int seedCount,
        int produceCount,
        boolean restockNeeded,
        boolean unloadNeeded
    ) {
    }

    /** 采集某种作物的独立库存状态（只读快照，不参与决策之外的任何全局混合统计） */
    CropInventoryState snapshot(CropDefinition crop) {
        int seedCount = inventory == null ? 0 : inventory.countSeed(crop);
        int produceCount = inventory == null ? 0 : inventory.countProduce(crop);
        // 阈值取「该作物自己的」那一套：不同作物之间不共享、不抵扣
        StardewLogisticsStore.CropLogistics cfg = logisticsOf(crop.cropKey());
        return new CropInventoryState(
            crop.cropKey(),
            crop.seedKey(),
            crop.produceKeys() == null ? List.of() : crop.produceKeys(),
            seedCount,
            produceCount,
            planner.hasRestockDemand(crop),
            produceCount >= cfg.unloadTrigger());
    }

    /** 作物与阶段共同构成会话内探测去重键。 */
    static String learningKey(String cropKey, String stageName) {
        return cropKey + "\u0000" + stageName;
    }

    /** 全量已选作物的独立库存快照（供状态播报 / 调试使用） */
    public List<CropInventoryState> inventoryStates() {
        List<CropInventoryState> states = new ArrayList<>();
        for (CropDefinition crop : planner.targetCrops()) states.add(snapshot(crop));
        return states;
    }

    /**
     * 是否处于「等待季节」：没有正在执行的真实任务，且仍有待播种作物被季节阻塞。
     *
     * <p>配置页据此显示「任务 ▶ 等待季节 / 模块保持运行」；只要还有 Harvest / Collect / Water /
     * DEAD / Unload 等真实任务在跑，就显示真实任务，绝不把整个模块写成等待季节。</p>
     */
    public boolean waitingForSeason() {
        return !seasonBlockedCrops.isEmpty() && (taskType == null || taskType == TaskType.RETURN_CENTER);
    }
}
