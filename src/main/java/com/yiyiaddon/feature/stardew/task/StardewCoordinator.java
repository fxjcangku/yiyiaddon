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
import com.yiyiaddon.feature.stardew.profile.PotDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
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
    /** 点位方块巡检间隔（tick）：绑定的箱子 / 水源 / 洒水器被挖掉时及时发现并报错 */
    static final int POINT_WATCH_INTERVAL = 20;
    /**
     * 给洒水器灌水时，同一 tick 连发几包（1 包 = 灌 1 点水）。
     *
     * <p><b>为什么要连发：</b>一包包按 tick 摊开、每包还等一次验证，灌满一台高级洒水器要两三秒，
     * 观感就是「一下一下太慢」。同一 tick 连发时服务端最多吞掉个别包（水源实测 10 包生效 9 包），
     * 多发的包对已满的洒水器是空操作、不浪费壶里的水，验证阶段读一次壶水差额就能发现还差多少并补齐。</p>
     */
    static final int SPRINKLER_BURST_PACKETS = 8;
    /** 单台洒水器最多灌几批（每批 {@link #SPRINKLER_BURST_PACKETS} 包），服务端行为异常时的硬兜底 */
    static final int SPRINKLER_MAX_BURSTS = 3;
    /**
     * 判定「满」的洒水器要跳过几轮普通检查。
     *
     * <p><b>为什么要有这个：</b>一台洒水器灌满后，下一轮维护又要走过去点一下才发现「还是满的」，
     * 白白跑腿。这里把刚验过是满的台记住 4 个检查间隔，期间不再挑它当目标；到点后照常重新检查
     * （洒水器灌溉会消耗水量，所以只推迟、不永久跳过）。</p>
     */
    static final int SPRINKLER_FULL_SKIP_ROUNDS = 4;
    static final int LOGISTICS_COOLDOWN = 200;
    /** 关闭箱无法监听外部玩家改动时的保险重查；正常解锁优先由背包/掉落/配置事件驱动。 */
    static final long RESTOCK_SAFETY_RECHECK_MS = 15L * 60_000L;
    static final long LOGISTICS_BLOCK_RETRY_MS = 60_000L;
    static final long NAVIGATION_BLOCK_RETRY_MS = 10_000L;
    /**
     * 料箱（岩浆箱 / 龙息箱）是空的之后，隔多久再去开一次。
     *
     * <p><b>为什么要有这个：</b>箱子里没料时，脚本唯一能做的判断就是「开箱看一眼」——不隔一段时间
     * 重看，就会在「开箱 → 取不到 → 关箱 → 重规划 → 开箱」之间原地打转，玩家看到的就是站着不动
     * （实机反馈：「箱子里没料了，去拿的时候就停在那」）。退避 30 秒既不再空转，补了料也能自动继续。</p>
     */
    static final long MATERIAL_FETCH_RETRY_MS = 30_000L;
    static final int NAVIGATION_NO_PROGRESS_TICKS = 80;
    static final int NAVIGATION_TIMEOUT_TICKS = 240;
    static final double NAVIGATION_PROGRESS_SQ = 0.04;
    static final int REFILL_MAX_PACKETS = 32;
    /**
     * 读不到水壶容量时，一轮补水保守发多少包。
     *
     * <p>容量读得到时按「差多少发多少」一次打满；只有 Tooltip / LORE / custom_data 全读不出容量，
     * 才退回这个固定批量，靠验证阶段一点点逼近。</p>
     */
    static final int REFILL_BURST_UNKNOWN = 8;
    /**
     * 补水时「连续几轮验证都读不到增量」才判定推不动。
     *
     * <p>服务端偶尔会吞掉同一 tick 里的某几个交互，所以不能一次没涨就收工：
     * 一轮已经把缺的水全发出去了，连续 {@code REFILL_STABLE_LIMIT} 轮没涨才认为补不动
     * （已经补进去的水照常使用）。</p>
     */
    static final int REFILL_STABLE_LIMIT = 2;
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
    /** 「批量动作」的硬上限：再多也不会发，避免一 tick 内刷出一串交互包。 */
    static final int MAX_BATCH_ACTIONS = 15;

    /**
     * 启动自检里「分区错位」一次性扫描的总格数预算。
     *
     * <p>自检跑在主线程上（模块开关 / 进服那一刻），不能像运行时那样分帧；区域格子数是玩家
     * 自己圈出来的，正常也就几百格。超预算的地块直接跳过——它只是提前拦截，运行中那轮扫描
     * 仍会查出来。</p>
     */
    static final int STARTUP_MISMATCH_SCAN_LIMIT = 8192;
    /** 一次性扫描的单次推进格数：整轮扫完即止，不需要和运行时一样小步走 */
    static final int STARTUP_MISMATCH_SCAN_STEP = 512;

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
    /** 已选温室玻璃：盆上方 5 格内有它 → 该盆无视季节限制照常播种（空 = 这条机制未启用） */
    List<String> selectedShelterKeys = List.of();

    /**
     * 自动清理错位作物：开着时错位格由 CLEAR_MISMATCH 任务挖掉、盆上杂物由 CLEAR_JUNK 挖掉，
     * 关着则错位格只画红框不动手、杂物保持原样不碰（枯死株清理不受本开关影响）。
     */
    boolean autoClearMismatch = false;
    /** 当前维度内的种植区域快照（模块每 tick 同步） */
    List<StardewRegionManager.Region> regions = List.of();

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
     * 同一 tick 最多对几个格子连发交互包（1 = 关闭）：收割 / 浇水 / 播种 / 施肥发右键，
     * 清枯苗 / 清错位 / 清杂物发左键破坏。
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

    /** 「某块单一作物区里种了别的作物」的回调，由模块注入 */
    Consumer<List<String>> regionMismatchHandler = null;
    /** 错位清理干净后的恢复回调，由模块注入 */
    Runnable regionMismatchRecoveredHandler = null;
    /** 「水壶不见了（快捷栏 / 背包 / 副手都没有）」的回调，由模块注入（停机） */
    Runnable missingCanHandler = null;
    /** 分区错位格子：只用于世界高亮，每轮决策按最新快照刷新 */
    private final Set<BlockPos> mismatchCells = new HashSet<>();
    /** 是否已因错位停住等玩家清理（停住期间一个任务都不派发） */
    private boolean regionMismatchPaused = false;
    /** 已提示过「缺种子」的区域号（同一块地只报一次，不刷屏） */
    final Set<Integer> announcedRegionSeedShortage = new HashSet<>();
    /** 是否已提示过「还没有种植区域」 */
    boolean regionEmptyAnnounced = false;
    /**
     * 当前作业的单一作物区序号（区域粘性）：先把这块地的活干完再去下一块，避免在地块之间来回跑。
     * {@code 0} = 还没定（下一轮由派发决定）；混种区（农场模式）与未分区格子都不参与。
     */
    int stickyRegionIndex = 0;
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

    /**
     * 注入「某个单一作物区内种了别的作物」的回调。
     *
     * <p>协调器只判定与交付冲突详情，是否停住由模块决定；错位清干净后回调
     * {@link #setRegionMismatchRecoveredHandler(Runnable)} 的恢复动作。</p>
     */
    public void setRegionMismatchHandler(Consumer<List<String>> handler) {
        this.regionMismatchHandler = handler;
    }

    /** 注入「分区错位已清理干净」的恢复回调 */
    public void setRegionMismatchRecoveredHandler(Runnable handler) {
        this.regionMismatchRecoveredHandler = handler;
    }

    /**
     * 注入「水壶不见了」的回调。
     *
     * <p>浇水、补水、洒水器维护全靠这把壶，而「读不到水量」在旧口径里等于「当作已补满」——
     * 实机反馈就是壶被挪走后脚本照样宣布完成。协调器只判定并交付事实，是否停机由模块决定
     * （与玩家死亡走同一条停机路径）。</p>
     */
    public void setMissingCanHandler(Runnable handler) {
        this.missingCanHandler = handler;
    }

    /** 找不到水壶：交给模块停机（模块未注入回调时不做任何事，绝不在这里自行关模块） */
    void stopForMissingCan() {
        if (missingCanHandler != null) missingCanHandler.run();
    }

    /** 当前错位格子（世界高亮用；空 = 没有冲突） */
    public Set<BlockPos> regionMismatchCells() {
        return Set.copyOf(mismatchCells);
    }

    /**
     * 启动自检用：一次性扫描当前维度的每个单一作物区，找出「地里长着别的作物」的格子。
     *
     * <p><b>为什么要有它：</b>运行中的判定在 {@code DECIDE} 阶段，属于「启动后跑完一轮扫描才停机」；
     * 实机反馈是「三块区域里有一块被换成了别的作物，模块照样能开机」。开机自检必须先把这种地拦下来，
     * 与其它启动条件一样列出来，别让玩家以为它跑得好好的。</p>
     *
     * <p><b>代价与边界：</b>只扫区域自己的格子（不扫区域之间的空档），并按
     * {@link #STARTUP_MISMATCH_SCAN_LIMIT} 格设总预算——预算不够的地块直接跳过（不是漏判：
     * 运行中那一轮扫描照样会查出来，只是那时已经开机了）。它只用于拦截，不产生任何任务、
     * 不参与任何决策。</p>
     *
     * @return 给玩家看的一行结论；没有冲突返回 {@code null}
     */
    public String startupRegionMismatchProblem() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || profile == null || regions.isEmpty()) return null;

        StardewFarmScanner oneShot = new StardewFarmScanner();
        List<StardewTaskPlanner.RegionMismatch> found = new ArrayList<>();
        int budget = STARTUP_MISMATCH_SCAN_LIMIT;
        for (StardewRegionManager.Region region : regions) {
            // 混种区种什么都算对，不必扫
            if (region.mixed()) continue;
            int cells = region.sizeX() * (region.maxY() - region.minY() + 1) * region.sizeZ();
            if (cells > budget) continue;
            budget -= cells;
            oneShot.begin(new BlockPos(region.minX(), region.minY(), region.minZ()),
                new BlockPos(region.maxX(), region.maxY(), region.maxZ()));
            while (!oneShot.complete()) {
                for (StardewFarmScanner.Cell cell : oneShot.scanTick(profile, STARTUP_MISMATCH_SCAN_STEP)) {
                    String key = cell.crop().cropKey();
                    if (key == null) continue;
                    // 与运行中同口径：枯死株 / 认不出的方块不是「种错了作物」，它们是清理链的活，
                    // 不该在开机时被报成「区域里的作物与绑定不符」（冬季冻死一片时尤其明显）。
                    CropState state = cell.crop().state();
                    if (state == CropState.DEAD || state == CropState.UNKNOWN) continue;
                    if (!key.equals(region.cropKey())) {
                        found.add(new StardewTaskPlanner.RegionMismatch(cell.potPos(), region, key));
                    }
                }
            }
        }
        if (found.isEmpty()) return null;
        return "分区错位：" + String.join("；", planner.describeMismatches(found))
            + " ▸ 清掉那几格里的作物，或把该区域删掉重划";
    }

    /**
     * 启动自检：当前维度的地里，一口「已选盆型」的盆都没有。
     *
     * <p><b>为什么必须在开机前拦：</b>盆型是互斥单选，玩家在上一个维度（或上一次测试）选过普通盆，
     * 换到下界的地里就会一口都匹配不上；此时每一格都被静默跳过，表现是「启动后什么都没发生」——
     * 玩家看不到任何解释，只能干等（实机反馈）。这条与其它启动条件一样列进自检，开机就摊开说清。</p>
     *
     * <p><b>边界：</b>与分区错位自检共用同一份一格预算；有地块因预算被跳过时**不作结论**，
     * 避免「没扫到」被误报成「不匹配」（运行中那一轮扫描仍会照常提示）。</p>
     *
     * @return 给玩家看的一行结论；没有不匹配或无法确证时返回 {@code null}
     */
    public String startupPotMismatchProblem() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || profile == null || regions.isEmpty() || selectedPotKeys.isEmpty()) return null;

        StardewFarmScanner oneShot = new StardewFarmScanner();
        Map<String, Integer> foundGroups = new java.util.LinkedHashMap<>();
        int matched = 0;
        int budget = STARTUP_MISMATCH_SCAN_LIMIT;
        for (StardewRegionManager.Region region : regions) {
            int cells = region.sizeX() * (region.maxY() - region.minY() + 1) * region.sizeZ();
            // 预算不够就整块跳过：宁可不说，也不能把「没扫到」说成「不匹配」
            if (cells > budget) return null;
            budget -= cells;
            oneShot.begin(new BlockPos(region.minX(), region.minY(), region.minZ()),
                new BlockPos(region.maxX(), region.maxY(), region.maxZ()));
            while (!oneShot.complete()) {
                for (StardewFarmScanner.Cell cell : oneShot.scanTick(profile, STARTUP_MISMATCH_SCAN_STEP)) {
                    collectPotGroup(foundGroups, cell);
                    if (planner.potMatchesSelection(cell)) matched++;
                }
            }
        }
        if (foundGroups.isEmpty() || matched > 0) return null;
        return "种植盆不匹配：地里 " + formatGroups(foundGroups) + "，已选 " + selectedPotNames()
            + " ▸ 去选择器里改「种植盆」";
    }

    /** 按「盆型中文名」聚合一口盆；识别不出盆键时如实记「(未识别)」，不冒充普通盆。 */
    private static void collectPotGroup(Map<String, Integer> groups, StardewFarmScanner.Cell cell) {
        String key = cell.potKey();
        groups.merge(key == null || key.isBlank() ? "(未识别)" : PotGroup.ofPotKey(key).displayName(), 1, Integer::sum);
    }

    /** {@code 下界种植盆 ×16、普通种植盆 ×2} */
    private static String formatGroups(Map<String, Integer> groups) {
        StringBuilder out = new StringBuilder();
        groups.forEach((name, count) -> {
            if (out.length() > 0) out.append('、');
            out.append(name).append(" ×").append(count);
        });
        return out.toString();
    }

    /** 已选盆型的中文名，例如 {@code 普通种植盆}；认不出的键原样列出，不编造名字 */
    private String selectedPotNames() {
        StringBuilder out = new StringBuilder();
        for (String key : selectedPotKeys) {
            StardewToolDefinition entry = index == null ? null : index.entryByKey(key);
            if (out.length() > 0) out.append('、');
            out.append(entry instanceof PotDefinition pot ? PotGroup.ofIndex(pot.potIndex()).displayName() : key);
        }
        return out.toString();
    }

    /**
     * 换品种前的占地检查（规范 12.1 的 A 口径）：区域里还有活着的作物时不允许改绑。
     *
     * <p><b>为什么不许：</b>改绑会让地里现有的作物立刻变成「异种作物」，模块随后就会按新目标把它
     * 挖掉——那是把玩家可能还要收的作物默默清掉，与 D12 / D13「不允许默默换目标」同源。
     * 空盆、死株、识别不出的格不算占地（那几样本就要清掉重种）。</p>
     *
     * <p>扫描范围只限这块区域自己的格子；格数超过启动自检那份预算时直接放行（不误伤，也不把
     * 一次点按钮变成卡顿）。</p>
     *
     * @return 区域内第一个「活着作物」的格；没有返回 {@code null}（可以直接改绑）
     */
    public RegionOccupied regionOccupied(StardewRegionManager.Region region) {
        Minecraft mc = Minecraft.getInstance();
        if (region == null || mc.level == null || profile == null) return null;
        int cells = region.sizeX() * (region.maxY() - region.minY() + 1) * region.sizeZ();
        if (cells > STARTUP_MISMATCH_SCAN_LIMIT) return null;
        StardewFarmScanner oneShot = new StardewFarmScanner();
        oneShot.begin(new BlockPos(region.minX(), region.minY(), region.minZ()),
            new BlockPos(region.maxX(), region.maxY(), region.maxZ()));
        while (!oneShot.complete()) {
            for (StardewFarmScanner.Cell cell : oneShot.scanTick(profile, STARTUP_MISMATCH_SCAN_STEP)) {
                if (cell.crop().cropKey() == null || !isLivingCrop(cell.crop().state())) continue;
                return new RegionOccupied(cell.crop().cropKey(), cell.crop().state(), cell.potPos());
            }
        }
        return null;
    }

    /** 占地判定结果：生长中的作物键 / 状态 / 坐标（中文名由模块用资源索引补，协调器不带索引） */
    public record RegionOccupied(String cropKey, CropState state, BlockPos pos) {
    }

    /** 生长中 / 成熟 / 特殊变种算「地里还有作物」；空盆、死株、识别不出的不算 */
    private static boolean isLivingCrop(CropState state) {
        return state == CropState.GROWING || state == CropState.MATURE || state == CropState.SPECIAL;
    }

    StardewRegionManager.Region regionAt(BlockPos pos) {
        if (pos == null) return null;
        for (StardewRegionManager.Region region : regions) {
            if (region.contains(pos)) return region;
        }
        return null;
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
    /** 点位方块巡检的 tick 计数（见 {@link #POINT_WATCH_INTERVAL}） */
    int pointWatchTicks;
    /** 洒水器灌注：点击前的壶水量（验证阶段比对用），读不到为 null */
    Integer sprinklerWaterBefore;
    /** 当前这台洒水器已灌了几批（换台或新一轮归零，见 {@link #SPRINKLER_MAX_BURSTS}） */
    int sprinklerBursts;
    /** 当前正在灌注的洒水器坐标：与它不同即视为换台，次数重新计 */
    BlockPos sprinklerPourTarget;
    /** 「刚验过是满的」洒水器：坐标 → 到期时间（毫秒），见 {@link #SPRINKLER_FULL_SKIP_ROUNDS} */
    private final java.util.Map<BlockPos, Long> sprinklerFullUntil = new java.util.HashMap<>();

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
    /** 已播报过的「作物 + 季节」温室豁免组合：有玻璃照常播种只播一次，绝不每格刷屏 */
    final Set<String> announcedShelterCrops = new HashSet<>();
    /** 已播报过「盆型 × 维度不匹配」的盆型：下界盆 / 末地盆各只提示一次，避免每轮扫描刷屏 */
    final Set<String> announcedDimensionBlocks = new HashSet<>();
    /** 已播报过「下界盆缺岩浆 / 末地盆缺龙息」的盆型：备好料之前只提醒一次 */
    final Set<String> announcedMaterialShortage = new HashSet<>();
    /** 已播报过「下界盆 / 末地盆不能施肥」的盆型：两种特殊盆没有肥料槽，只提醒一次 */
    final Set<String> announcedNoFertilize = new HashSet<>();
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
    /** 「发现特殊作物但背包没有金锄头」每个具体阶段只提示一次，禁止每轮扫描刷屏。 */
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
    /** 上一轮扫描概览：内容不变就不重复打，避免每轮扫描刷屏 */
    private String lastScanSummary;
    /** 料箱刚验过是空的：在此时间戳之前不再去开箱（见 {@link #MATERIAL_FETCH_RETRY_MS}） */
    long materialFetchBlockedUntil;
    Map<String, Integer> taskInventoryBefore = Map.of();
    int taskSeedBefore;
    /** 本次任务目标作物自己的种子数（回收完成量按它算，不能用全部已选作物的合计）。 */
    int taskCropSeedBefore;
    int taskProduceBefore;

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
                          List<String> selectedShelterKeys,
                          String serverKey, String dimension, int reach, int scanBudget,
                          boolean wateringEnabled, boolean switchCan, boolean restoreHand,
                          boolean autoFertilize, boolean autoPotion,
                          boolean sprinklerMaintenance, int sprinklerInterval,
                          int restockTrigger, int restockTarget, int unloadTrigger, int unloadKeep,
                          int returnCenterDelaySeconds, int batchActions,
                          boolean autoClearMismatch,
                          List<StardewRegionManager.Region> regions) {
        List<String> newCropKeys = selectedCropKeys == null ? List.of() : List.copyOf(selectedCropKeys);
        BlockPos newSeedBox = points == null || points.get(StardewPointType.SEED_BOX) == null
            ? null : points.get(StardewPointType.SEED_BOX).pos();
        String newFingerprint = com.yiyiaddon.service.resourcepack.ResourceExtractionService.fingerprint();
        // 换维度后「盆型 × 维度」的结论会整个反过来（下界盆出了下界就种不了），必须允许重新播报一次；
        // 盆型选择本身也分维度，「盆型不匹配」的结论也要按新维度重算。
        if (!java.util.Objects.equals(this.dimension, dimension)) {
            announcedDimensionBlocks.clear();
            lastScanSummary = null;
            // 料箱退避按维度 / 服务器生效：换环境后那只箱子未必还是空的，不该带着旧退避
            materialFetchBlockedUntil = 0L;
        }
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
            announcedShelterCrops.clear();
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
        this.selectedShelterKeys = selectedShelterKeys == null ? List.of() : selectedShelterKeys;
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
        this.autoClearMismatch = autoClearMismatch;
        this.regions = regions == null ? List.of() : List.copyOf(regions);
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
        pointWatchTicks = 0;
        reporter.forgetLostPoints();
        taskStep = 0;
        taskTicks = 0;
        targetContainer = null;
        containerApproach = null;
        activeCrop = null;
        pending.clear();
        mismatchCells.clear();
        regionMismatchPaused = false;
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
        sprinklerFullUntil.clear();
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
        announcedShelterCrops.clear();
        announcedDimensionBlocks.clear();
        announcedMaterialShortage.clear();
        announcedNoFertilize.clear();
        announcedSeedReturns.clear();
        announcedRegionSeedShortage.clear();
        regionEmptyAnnounced = false;
        waitingSeasonAnnouncedLabel = null;
        reportedContainerFailures.clear();
        reportedCollectFailures.clear();
        observedSeasonRevision = seasonService.revision();
        verifier.clearLearningSnapshot();
        taskInventoryBefore = Map.of();
        taskSeedBefore = 0;
        taskCropSeedBefore = 0;
        taskProduceBefore = 0;
        stickyRegionIndex = 0;
    }

    public TaskType currentTask() {
        return taskType;
    }

    /** 这台洒水器是否在「刚验过是满的」窗口内（窗口到点后自动失效，不会永久跳过） */
    boolean isSprinklerRecentlyFull(BlockPos pos) {
        if (pos == null) return false;
        Long until = sprinklerFullUntil.get(pos);
        if (until == null) return false;
        if (System.currentTimeMillis() < until) return true;
        sprinklerFullUntil.remove(pos);
        return false;
    }

    /** 刚验证这台是满的：记下跳过窗口，下一轮不再为它白跑 */
    void markSprinklerFull(BlockPos pos) {
        if (pos == null) return;
        sprinklerFullUntil.put(pos, System.currentTimeMillis()
            + sprinklerInterval * 50L * SPRINKLER_FULL_SKIP_ROUNDS);
    }

    /** 这台又在吃水了（灌进去过）：立刻取消跳过，恢复正常检查节奏 */
    void markSprinklerConsuming(BlockPos pos) {
        if (pos != null) sprinklerFullUntil.remove(pos);
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
        // 点位方块巡检：绑定的箱子 / 水源 / 洒水器被挖掉时立即报警，不必等下一次真正用到才发现
        if (++pointWatchTicks >= POINT_WATCH_INTERVAL) {
            pointWatchTicks = 0;
            reporter.watchPointBlocks();
        }
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

    private void observeCropGroups(Iterable<StardewFarmScanner.Cell> cells) {
        for (StardewFarmScanner.Cell cell : cells) {
            if (cell == null || cell.crop() == null) continue;
            CropPotGroups.observe(cell.crop().cropKey(), cell.potGroup());
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
        var fresh = scanner.scanTick(profile, scanBudget);
        pending.addAll(fresh);
        observeCropGroups(fresh);
        if (scanner.complete()) {
            planner.refreshPassableFarmCarriers();
            reportScanSummary();
            phase = Phase.DECIDE;
        }
    }

    /**
     * 扫描概览：这一轮到底扫到了什么，并在「一口都没匹配上」时明确报出来。
     *
     * <p><b>为什么需要它：</b>「没扫到盆」和「扫到了但盆型没选中」在游戏里长得一模一样，
     * 而后者会让每一格都被静默跳过，玩家看不到任何解释（实机反馈「启动后毫无反应」）。
     * 这里在零命中时给一条可执行的结论；结论没变就不再重复播报。</p>
     */
    private void reportScanSummary() {
        Map<String, Integer> foundGroups = new java.util.LinkedHashMap<>();
        int matched = 0;
        int scanned = 0;
        for (StardewFarmScanner.Cell cell : pending) {
            scanned++;
            collectPotGroup(foundGroups, cell);
            if (planner.potMatchesSelection(cell)) matched++;
        }
        String summary = selectedPotKeys + "/" + foundGroups + "/" + matched + '/' + scanned;
        if (summary.equals(lastScanSummary)) return;
        lastScanSummary = summary;
        if (matched == 0 && scanned > 0) announcePotMismatch(foundGroups);
    }

    /**
     * 地里一口「已选盆型」的盆都没有：运行中同样要报，不能只靠开机自检。
     *
     * <p>开机自检覆盖「开机那一刻就选错」；这条覆盖运行中换盆、换维度、玩家中途改选择这些情况。
     * 结论每变化一次只播一条（调用方已按扫描概览去重），不会刷屏。</p>
     */
    private void announcePotMismatch(Map<String, Integer> foundGroups) {
        status.critical("POT_SELECT_MISMATCH", "已选种植盆在地里一口都没匹配上",
            "地里：" + formatGroups(foundGroups) + " · 已选：" + selectedPotNames() + " ▸ 去选择器里改「种植盆」");
    }

    private void decide() {
        boolean waitingForGrowth = pending.stream().anyMatch(planner::isManagedGrowingCell);
        // 季节阻塞每轮重算：只有「本轮真实需要播种却被季节拒绝」的作物才算阻塞，
        // 旧阻塞不会残留（播报去重由 announcedSeasonBlocks 独立承担，重算不会重复刷屏）
        seasonBlockedCrops.clear();

        // 单一作物区错位（区内种了别的作物）：停住等玩家清理——既不收也不浇，清干净自动继续。
        // 必须排在所有派发之前，否则这条判定形同虚设（玩家开批量的那次就是踩着这个缝种错的）。
        // 停住期间照常重扫（restartObserve），所以错位清掉后下一轮就能发现并自动恢复。
        if (regionMismatchHandler != null) {
            List<StardewTaskPlanner.RegionMismatch> found = planner.regionMismatches();
            mismatchCells.clear();
            for (StardewTaskPlanner.RegionMismatch mismatch : found) mismatchCells.add(mismatch.pos());
            if (autoClearMismatch) {
                // 自动清理开着：不停机，交给 CLEAR_MISMATCH 任务逐格挖掉再补种（红框照旧画）
                if (regionMismatchPaused) {
                    regionMismatchPaused = false;
                    if (regionMismatchRecoveredHandler != null) regionMismatchRecoveredHandler.run();
                }
            } else if (!found.isEmpty()) {
                if (!regionMismatchPaused) {
                    regionMismatchPaused = true;
                    regionMismatchHandler.accept(planner.regionMismatchedCrops());
                }
                restartObserve();
                return;
            } else if (regionMismatchPaused) {
                regionMismatchPaused = false;
                if (regionMismatchRecoveredHandler != null) regionMismatchRecoveredHandler.run();
            }
        }

        // 一块区域都没有：什么都不做。此时「未分区 = 不管」口径下整片田都不属于模块，
        // 绝不能悄悄退回旧口径去管整片田。
        if (regions.isEmpty()) {
            if (!regionEmptyAnnounced) {
                regionEmptyAnnounced = true;
                status.state("REGION_EMPTY", "还没有种植区域", "用 .stardew 种植区域 <作物|混种> 圈一块");
            }
            restartObserve();
            return;
        }

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
            // 区域粘性：当前作业地块还有活就先做它，这块地做干净再换下一块（只认单一作物区）
            if (planner.startStickyRegionTask()) {
                phase = Phase.NAVIGATE;
                return;
            }
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
        // 判据取「现实里还有没有被拦的格子」：玩家补上温室玻璃、或自己把那几格种上之后，
        // seasonBlockedCrops 已不是现实，只按集合非空会误播一条「等待季节」。
        if (!seasonBlockedCrops.isEmpty() && planner.hasSeasonBlockedEmptyPot()) {
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
            case CLEAR_DEAD, CLEAR_MISMATCH, CLEAR_JUNK -> executor.doBreakPlant();
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
        } else if (taskType == TaskType.SPRINKLER_CHECK) {
            // 洒水器灌注循环：不受普通重试上限（MAX_RETRY=3）约束，
            // 上限由 SPRINKLER_MAX_POURS 兜底，否则高级洒水器永远灌不满。
            stepTick = 0;
            phase = Phase.NAVIGATE;
        } else if (taskType == TaskType.WATER && (executor.canWaterIsEmpty() || retryCount >= MAX_RETRY)) {
            // 水量字段缺失时以连续浇水失败兜底判断空壶，下一轮必须先去补水点。
            forceRefill = true;
            phase = Phase.REPLAN;
        } else if (retryCount >= MAX_RETRY) {
            // 杂物可能是砸不掉的东西（服务端保护方块 / 屏障）：给它一个退避窗口，
            // 否则每一轮都会重新走到那一格再试一次，观感就是站在田里原地打转。
            if (taskType == TaskType.CLEAR_JUNK) planner.blockNavigationTarget(TaskType.CLEAR_JUNK, targetPot);
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
