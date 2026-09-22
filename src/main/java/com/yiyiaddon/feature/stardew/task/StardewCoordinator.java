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
import com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.PotDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestPresets;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestRecipe;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestStore;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.status.StardewStatusReporter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    /**
     * 「客户端估算所需 tick」超过它就别当真：实机里手拿金锄头对着一格
     * {@code minecraft:chorus_plant}，客户端算出每刻 1.0E-4、需 <b>7000</b> tick。
     *
     * <p>它的用法只有两处：{@link #BREAK_FALLBACK_HOLD_TICKS}（估算离谱时第一轮按多久）与
     * {@code StardewFarmExecutor#ensureDigTool}（判断「手上这件挖不动，该换一件试试」）。
     * 估算离谱不等于这一格挖不动 —— 换手后仍离谱时照旧按固定时长试，真挖不动由轮数上限兜底。</p>
     */
    static final int BREAK_MAX_REQUIRED_TICKS = 200;
    /**
     * 发过 STOP 之后再等这么多刻，让服务端确认拆除；这个窗口内<b>绝不重发 START</b>。
     *
     * <p><b>为什么强调「不重发」</b>：服务端的 {@code destroyProgressStart} 每收到一次 START 都会
     * 重置，于是「已挖 tick 数」永远归零、{@code delta × (已挖 tick + 1)} 永远到不了 0.7 ——
     * 实机表现就是「怎么按服务端都不认这一格」。
     * （也正因为如此，破坏<b>不能</b>交给原版 {@code MultiPlayerGameMode}：{@code Minecraft} 每刻
     * 会在攻击键没按下时调 {@code stopDestroyBlock()}，把会话状态清掉，我们的推进就变成每刻重开、
     * 每刻重发 START —— 实测原版流程挖满 201 刻仍纹丝不动。）</p>
     */
    static final int BREAK_RESEND_AFTER_TICKS = 20;
    /**
     * 单轮最少按住多少 tick（20 tick = 1 秒）。
     *
     * <p>客户端算出的破坏速度在这台服上忽大忽小（实机：同一格同一把金锄头，一次
     * {@code 物品破坏速度=0.0}、一次 {@code =1.0}），所以估算只能当参考，实际按住时间必须有下限。</p>
     */
    static final int BREAK_MIN_HOLD_TICKS = 20;
    /** 单轮最多按住多少 tick（120 tick = 6 秒）：够长的同时，真挖不动时也不至于赖在这格上太久 */
    static final int BREAK_MAX_HOLD_TICKS = 120;
    /** 估算离谱时第一轮就按这么久（40 tick = 2 秒），之后每轮翻倍 */
    static final int BREAK_FALLBACK_HOLD_TICKS = 40;
    /**
     * 同一格最多挖几轮（每轮按住的时间翻倍：20 → 40 → 80 → 120）。
     *
     * <p>轮数 × 翻倍是「客户端估算偏小」的兜底：估算偏小就靠逐轮加长收敛，真挖不动
     * （服务端保护 / 收法不对）才会走到最后一轮并报出来，绝不无限砸。</p>
     */
    static final int BREAK_MAX_ATTEMPTS = 4;
    /**
     * 「服务端没放行这一格」之后，这一格多久内不再派发任何破坏动作（60 秒）。
     *
     * <p><b>为什么必须有退避：</b>挖不动那条播报按坐标只报一次，但<b>派发原先没有退避</b> ——
     * 下一轮决策又是这一格，于是每十几秒照样来一轮「4 次发包 + 4 轮按住」。实机日志里同一格
     * 从 23:36 一直砸到 23:38，方块纹丝不动，玩家只看到模块赖在那儿空砸。到期自动再试一次
     * （服务端可能松了、玩家也可能换成了一把真挖得动的工具）。</p>
     */
    static final long BREAK_REJECT_BACKOFF_MS = 60_000L;
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

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

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

    /**
     * 启动自检查出来的分区错位格：**独立于运行期高亮，且不随停机清空**。
     *
     * <p><b>为什么要独立一份：</b>自检不通过时模块会被立刻关掉（{@code onDisable → reset()}），
     * 运行期那份高亮随之清空 —— 于是玩家只看到聊天栏一行坐标，跑到田里什么都找不到
     * （用户 2026-09-22：「这个报错显示坐标没用 应该显示红色的 ESP 全包那种 不然农田大了 不好找」）。
     * 自检这份留着，模块在未启动状态也能把这几格用红框标出来；玩家清掉后由
     * {@link #pruneStartupMismatch()} 逐步摘掉，自检通过时整体清空（{@link #clearStartupMismatch()}）。</p>
     */
    private final Set<BlockPos> startupMismatchCells = new HashSet<>();
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

    /** 注入「产物箱没有空间」的回调（模块注入的是关模块） */
    public void setOutputBoxFullHandler(Runnable handler) {
        this.outputBoxFullHandler = handler;
    }

    /** 产物箱确认放不下：交给模块关模块（与「水壶不见了」同一条路径，绝不在这里自行关闭） */
    void stopForOutputBoxFull() {
        if (outputBoxFullHandler != null) outputBoxFullHandler.run();
    }

    /** 当前错位格子（世界高亮用；空 = 没有冲突）。含运行期快照与启动自检那份 */
    public Set<BlockPos> regionMismatchCells() {
        if (startupMismatchCells.isEmpty()) return Set.copyOf(mismatchCells);
        Set<BlockPos> all = new HashSet<>(mismatchCells);
        all.addAll(startupMismatchCells);
        return Set.copyOf(all);
    }

    /** 启动自检是否留下了错位高亮（模块未启动时据此挂上只画红框的那一层） */
    public boolean hasStartupMismatch() {
        return !startupMismatchCells.isEmpty();
    }

    /** 自检通过：自检那份错位高亮整体作废 */
    public void clearStartupMismatch() {
        startupMismatchCells.clear();
    }

    /**
     * 复核自检那份错位高亮：那一格还是不是「种错了东西」。
     *
     * <p>模块被禁止启动期间没有 tick，玩家把错位作物清掉或换成该区作物之后，红框必须自己灭掉，
     * 否则玩家清完了还看着红框（比没有框更误导）。所以复核借渲染这一路：渲染层每若干帧叫一次。
     * 判据与运行期同口径 —— 区域被删、格子里已认不出作物 / 是枯死株 / 已是本区作物，都不算错位。</p>
     */
    public void pruneStartupMismatch() {
        if (startupMismatchCells.isEmpty() || profile == null || Minecraft.getInstance().level == null) return;
        startupMismatchCells.removeIf(pos -> !stillRegionMismatch(pos));
    }

    /** 某一格（盆坐标）此刻还算不算分区错位 */
    private boolean stillRegionMismatch(BlockPos potPos) {
        StardewRegionManager.Region region = regionAt(potPos);
        if (region == null || region.cropKey() == null) return false;
        CropRecognizer.CropRecognition crop = CropRecognizer.recognizeAtPot(potPos, profile);
        CropState state = crop.state();
        if (state == CropState.EMPTY || state == CropState.UNKNOWN || state == CropState.DEAD) return false;
        String key = crop.cropKey();
        return key != null && !key.equals(region.cropKey());
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
        if (found.isEmpty()) {
            // 这一轮没查出错位：上一次自检留下的高亮一并作废（玩家已经把地清好了）
            startupMismatchCells.clear();
            return null;
        }
        // 登记成「世界高亮」：自检不通过时模块会被立刻关掉，运行期那份高亮随之清空，
        // 玩家就只剩聊天栏一行坐标、跑到田里找不着（用户 2026-09-22）。这份不随停机清。
        startupMismatchCells.clear();
        for (StardewTaskPlanner.RegionMismatch mismatch : found) startupMismatchCells.add(mismatch.pos());
        return "分区错位：" + String.join("；", planner.describeMismatches(found))
            + " ▸ 已用红框在世界里标出，清掉那几格里的作物，或把该区域删掉重划";
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
    /**
     * 已提示过「这台洒水器不在了」的点位。
     *
     * <p>同一台只提示一次（每刻都在巡查的点位列表不能每 tick 刷屏）；它重新出现后再消失，
     * 会重新提示一次。</p>
     */
    private final java.util.Set<BlockPos> missingSprinklersReported = new java.util.HashSet<>();

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
    /**
     * 「产物箱没有空间」的回调，由模块注入（**关闭模块**，与「水壶不见了」同一条路径）。
     *
     * <p><b>为什么是关模块而不是停手</b>：箱子满了要玩家去开箱子清 —— 那段时间模块若还开着，
     * 它会继续跑动 / 抢动作，玩家连箱子都清不痛快（用户 2026-09-22 实机：「我刚刚打开箱子然后又进去
     * 状态机 那这样 用户想清空箱子 都没办法」；同一条反馈里明确要求：「我说了 直接停机了 关闭模块懂？」）。
     * 关掉之后世界彻底静止，玩家安心清箱，清完自己重开模块即可。</p>
     *
     * <p>协调器只判定并交付事实，不自行关模块（{@link #stopForOutputBoxFull()}）。</p>
     */
    Runnable outputBoxFullHandler = null;
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
    /**
     * 「背包已满、收不动也卸不出去」是否已经播报过。
     *
     * <p>去重到「一次阻塞只报一条」：背包重新有空格后由 {@link #forgetBagFullHalt()} 复位，
     * 于是下次再撑满会重新报一次，不会因为报过一次就永远沉默。</p>
     */
    private boolean bagFullHalted;
    /** 已报过「服务端没放行这一格」的坐标：同一格只报一次 */
    private final Set<Long> breakRejectedReported = new HashSet<>();
    /**
     * 「服务端不放行这一格」的退避：坐标 → 到期时刻（见 {@link #BREAK_REJECT_BACKOFF_MS}）。
     *
     * <p>写在这个类里而不是执行器：调度层（{@code StardewTaskPlanner#resolveTask}）要按它跳过这一格，
     * 执行器负责在挖不动时登记；两边共用同一份账，绝不各记各的（第 169 条）。</p>
     */
    private final Map<Long, Long> breakRejectedUntil = new HashMap<>();
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

    // ── 特殊变种收割口径（按服务器 + 资源指纹 + 作物学习，见 StardewSpecialHarvestStore） ──
    /**
     * 本服已学到 / 已存盘的特殊阶段收割口径：作物键 → 动作 + 手持。
     *
     * <p>取不到就看本服预置（{@link StardewSpecialHarvestPresets}），再取不到才是
     * {@link StardewSpecialHarvestRecipe#DEFAULT}（老口径：金锄头右键），
     * 绝不因为本服学到「左键破坏」就去动别的服务器的巨型 / 金色作物。</p>
     */
    final Map<String, StardewSpecialHarvestRecipe> specialHarvestRules = new HashMap<>();
    /**
     * 本服「通用口径」在档案里的键：玩家在本服示范过一次后写进去，
     * 供本服其它变种沿用（见 {@link #specialHarvestRecipe}）。作物专属记录优先于它。
     */
    static final String SPECIAL_HARVEST_SERVER_WIDE_KEY = "*";
    /** 玩家刚做过的特殊阶段收割动作，等那一格确实被收掉的证据到了才落盘 */
    SpecialObservation pendingSpecialObservation;
    /** 已提示过「本服这个作物的特殊变种还没学到收法」的作物键：只提示一次，避免每轮刷屏 */
    final Set<String> announcedSpecialUnknown = new HashSet<>();
    /** 已提示过「学到的左键口径也没砸掉」的作物键：只提示一次（服务端保护方块 / 口径其实不对） */
    final Set<String> announcedSpecialBreakFailed = new HashSet<>();
    /** 已播报过「已学会本服特殊变种收法」的作物键：只播一次 */
    private final Set<String> announcedSpecialLearned = new HashSet<>();
    private final StardewSpecialHarvestStore specialHarvestStore = new StardewSpecialHarvestStore();

    /**
     * 一次待确认的玩家手动收割观测。
     *
     * <p>玩家动手时先记下来，等那一格<b>确实被收掉</b>（方块变了 / 展示实体身份变了）才落盘：
     * 只看「玩家点了什么」会把点空、被服务端驳回、拿错工具一起学进去。</p>
     *
     * @param ruleKey 落盘用的键（{@code 作物#变种标记}；认不出变种时就是作物键）——
     *                同一作物的两种变种因此各记各的，不会互相顶掉
     * @param cropKey 仅用于播报与日志的作物键
     */
    record SpecialObservation(String ruleKey, String cropKey, StardewSpecialHarvestRecipe recipe, BlockPos pos,
                              String beforeSnapshot, long deadline) {
    }

    /** 待确认观测的有效窗口：玩家动手后这么久内那一格必须真的变了，否则当次动作不算数 */
    static final long SPECIAL_OBSERVATION_WINDOW_MS = 2500L;

    // ── 破坏（学到的「左键破坏」口径 / 清枯苗 / 清错位 / 清杂物）的挖掘进度 ──
    /** 正在挖的那一格；null 表示当前没有进行中的破坏 */
    BlockPos breakDigPos;
    /** 这一格已经挖了几轮（START → 等够 → STOP 算一轮），用来给「服务端不放行」封顶 */
    int breakDigAttempts;
    /** 本轮已经推进了多少刻（发过 STOP 后继续走，用来判「确认窗口」） */
    int breakDigTickCount;
    /** 本轮是否已经发过 STOP（发完先等服务端确认，窗口内绝不重发 START） */
    boolean breakDigStopSent;
    /** 这一轮要按住多少 tick 才发 STOP（开挖那一刻定下来，之后不再受「客户端估算」影响） */
    int breakDigHoldTicks;
    /**
     * 本服口径指定了特殊收割工具时：这一格破坏期间手里必须一直是那件，模块不得换手
     * （服务端按「拿的就是那件」放行，换掉就砸不掉）。任务结束由 {@code restoreHandNow} 清空。
     */
    StardewSpecialHarvestRecipe specialToolHold;

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
            // 破坏退避也是「某个维度里那一格方块」的结论：同一坐标在另一个维度是别的方块，
            // 带着旧退避过去只会让那边的活也停一分钟
            breakRejectedUntil.clear();
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
        // 特殊变种收割口径按「服务器 + 资源指纹」隔离：换服 / 换包必须重读，
        // 绝不能带着上一个服的结论（老服右键、本服左键破坏）去砸另一边的巨型作物。
        if (!java.util.Objects.equals(configuredServerKey, serverKey)
            || !java.util.Objects.equals(configuredFingerprint, newFingerprint)) {
            specialHarvestRules.clear();
            specialHarvestRules.putAll(specialHarvestStore.load(serverKey, newFingerprint));
            announcedSpecialUnknown.clear();
            announcedSpecialLearned.clear();
            pendingSpecialObservation = null;
            // 箱子是「某个服务器某个维度里那个方块」的状态：换服 / 换包（以及上面的换维度）必须清掉，
            // 否则新环境会凭空继承「产物箱没空间」的结论，白白停收一分钟。
            unloadBlockedUntil.clear();
            seedReturnBlockedUntil.clear();
            // 破坏退避按「服务器 + 资源包」隔离：换服 / 换包后那一格是不是同一种方块、手上那把是不是
            // 也换了把真的，都无从判断，一律重新试
            breakRejectedUntil.clear();
            LOGGER.info("[星露谷] 特殊变种收法 服务器={} 已记录 {} 个作物", serverKey, specialHarvestRules.size());
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
        // 停机 / 换世界：进行中的破坏要中止，别让服务端还记着「这个玩家在挖这一格」
        executor.abortBreakDig();
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
        // 注意：背包「已满」这条状态**故意不在这里清**：它描述的是「玩家背包此刻的实况」，
        // 模块一开一关（玩家反复开关、换维度、重载）就清掉，会让下一次启动立刻白跑一趟箱子、
        // 再刷一条同样的提示（实机：连点开关时每开一次都「正在卸货 → 产物箱没有空间」）。
        navigationBlockedUntil.clear();
        sprinklerFullUntil.clear();
        missingSprinklersReported.clear();
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
        announcedSpecialUnknown.clear();
        announcedSpecialBreakFailed.clear();
        // 背包「已满」的播报去重同样不清：背包还是满的，重启一次就再报一遍纯属噪声
        // （背包腾出空格时由 {@link #forgetBagFullHalt()} 解除）。
        breakRejectedReported.clear();
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

    /**
     * 这台洒水器已确认不在了：本次会话第一次为 {@code true}（调用方据此只提示一次）。
     *
     * <p>本方法只负责去重记账，判定与播报分别由 {@code StardewPointManager#sprinklerUnusableReason}
     * 与 {@code StardewFarmReporter#announceSprinklerMissing} 负责。</p>
     */
    boolean markSprinklerMissingReported(BlockPos pos) {
        return pos != null && missingSprinklersReported.add(pos);
    }

    /** 这台洒水器又在了：解除「已提示过不在」的记录，下次再消失会重新提示 */
    void clearSprinklerMissingReported(BlockPos pos) {
        if (pos != null) missingSprinklersReported.remove(pos);
    }

    /**
     * 背包一个空格都不剩、又（暂时）卸不出去：停收并报一条。
     *
     * <p><b>为什么要停</b>：收下来的成品和地上的掉落都无处安放 —— 继续收只会掉地，
     * 要么被别的玩家捡走、要么 5 分钟后消失（用户实机：故意把背包塞满，模块照样一格一格收）。</p>
     *
     * <p>只报一次，且背包腾出空间后自动复位（见 {@link #forgetBagFullHalt()}）。</p>
     */
    void reportBagFullHalt(String reason) {
        if (bagFullHalted) return;
        bagFullHalted = true;
        status.critical("BAG_FULL_HALT", "背包已满，已停机",
            reason + "｜出路：清空背包腾出空格（种子 / 工具 / 杂物同样占格），或清空产物箱；"
                + "腾出空间后自动继续");
    }

    /** 背包又有空格了：解除「已满」去重，下次再撑满会重新提示 */
    void forgetBagFullHalt() {
        bagFullHalted = false;
    }

    /**
     * 「这一格挖不动」的唯一出口：报一次，并登记退避（退避期内不再派发这一格的破坏动作）。
     *
     * <p>走到这里只有一种情形：START / STOP 都真发出去了、{@link #BREAK_MAX_ATTEMPTS} 轮按住都发完，
     * 服务端就是不放行（保护方块 / 收法其实不是左键）。按坐标去重，同一格只报一条。</p>
     */
    void reportBreakRejected(BlockPos pos, String cellSummary) {
        markBreakRejected(pos);
        if (pos == null || !breakRejectedReported.add(pos.asLong())) return;
        status.critical("BREAK_REJECTED:" + pos, "这一格砸不动，已跳过",
            pos.toShortString() + " ▸ " + cellSummary
                + "｜" + (BREAK_REJECT_BACKOFF_MS / 1000L) + " 秒后会自动再试；"
                + "若你手动能收掉它，收一棵给我看，口径会自动更新");
    }

    /**
     * 这一格是否还在「服务端没放行」的退避期内。
     *
     * <p>到期自动解除并清账（不留永久黑名单，服务端松了、手上换了件挖得动的都会自动恢复）。</p>
     */
    boolean isBreakRejected(BlockPos pos) {
        if (pos == null) return false;
        long key = pos.asLong();
        Long until = breakRejectedUntil.get(key);
        if (until == null) return false;
        if (System.currentTimeMillis() < until) return true;
        breakRejectedUntil.remove(key);
        return false;
    }

    /** 这一格这一轮挖不动（服务端没放行）：登记退避，退避期内不再对它派发破坏动作 */
    void markBreakRejected(BlockPos pos) {
        if (pos == null) return;
        breakRejectedUntil.put(pos.asLong(), System.currentTimeMillis() + BREAK_REJECT_BACKOFF_MS);
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
        // 挖掘序列独立推进：START 发出去后一定会等到 STOP（或 ABORT），与任务相位无关。
        // 它必须排在相位机之前 —— 相位机随时可能重扫 / 换任务 / 跳转，一旦那时不去推进序列，
        // 服务端那个「已累计的破坏会话」就永远收不了尾（实机：日志只有「开始挖掘」，方块永远挖不掉）。
        executor.tickBreakDig();
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
        // 玩家自己动手收特殊变种时同步学到的收法（按服务器分开记）
        tickSpecialObservation();
        // 满仓停机要覆盖「任务已经在跑」的情形：decide() 那道闸只管新派发，而已经派发的补货 /
        // 播种 / 补水会一路走到完成 —— 实机反馈：背包已经满到收不了菜，模块还在「正在补货
        // 菠萝种子」，又跑去种子箱拿更多种子回来。
        // 卸货与种子回收不打断（它们正是满仓的出路），破坏序列也不打断（tickBreakDig 自己收尾）。
        if (taskType != null && taskType != TaskType.UNLOAD && taskType != TaskType.SEED_RETURN
            && phase != Phase.OBSERVE && planner.storageFullHalts() && !executor.digInProgress()) {
            adapter.cancelPath();
            phase = Phase.REPLAN;
            return;
        }

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

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  特殊变种收割口径：按服务器学习 + 持久化
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 某个作物在本服特殊阶段的收割口径（动作 + 手持）。
     *
     * <p><b>认得出变种时</b>（{@code 作物#标记}）：本变种的学习记录 → 本变种的预置 → 本服通用口径
     * → 该作物的预置 → 老口径。<b>刻意不看「不带标记的作物键学习记录」</b>：那条记录说不清
     * 是哪个变种（本服番茄既有巨型又有黄金，一个左键一个右键），拿它当兜底就是用错动作 ——
     * 在左键破坏不可逆的服上等于砸坏作物。</p>
     *
     * <p><b>认不出变种时</b>（退回纯作物键）：学习记录 → 预置 → 本服通用口径 → 老口径，
     * 与旧档案完全兼容。</p>
     */
    StardewSpecialHarvestRecipe specialHarvestRecipe(String cropKey, String variantTag) {
        if (cropKey == null) return StardewSpecialHarvestRecipe.DEFAULT;
        String scoped = specialRuleKey(cropKey, variantTag);
        StardewSpecialHarvestRecipe learned = specialHarvestRules.get(scoped);
        if (learned != null) return learned;
        StardewSpecialHarvestRecipe preset = StardewSpecialHarvestPresets.find(serverKey, scoped);
        if (preset != null) return preset;
        // 本服的通用口径：玩家在本服示范过任意一种变种后，其它变种先按同一口径收，
        // 不必为每种作物各示范一次（同服的变种形态通常一致；玩家若真收到不一样的，那一次会覆盖成专属记录）
        StardewSpecialHarvestRecipe serverWide = specialHarvestRules.get(SPECIAL_HARVEST_SERVER_WIDE_KEY);
        if (serverWide != null) return serverWide;
        if (scoped.equals(cropKey)) {
            StardewSpecialHarvestRecipe byCrop = specialHarvestRules.get(cropKey);
            if (byCrop != null) return byCrop;
        }
        StardewSpecialHarvestRecipe cropPreset = StardewSpecialHarvestPresets.find(serverKey, cropKey);
        return cropPreset == null ? StardewSpecialHarvestRecipe.DEFAULT : cropPreset;
    }

    /** 口径查表：直接用识别结果（键里自动带上变种标记） */
    StardewSpecialHarvestRecipe specialHarvestRecipe(CropRecognizer.CropRecognition crop) {
        if (crop == null) return specialHarvestRecipe((String) null, null);
        return specialHarvestRecipe(crop.cropKey(),
            CropRuntimeStateResolver.variantTag(crop.modelIdentity(), crop.stageName()));
    }

    /** 口径的存档键：作物 + 变种标记。没有标记（认不出变种）时退回纯作物键，与旧档案同键 */
    static String specialRuleKey(String cropKey, String variantTag) {
        if (cropKey == null) return null;
        return variantTag == null || variantTag.isBlank() ? cropKey : cropKey + '#' + variantTag;
    }

    /** 本服这个变种的特殊阶段口径是否已经确定（学过 / 本服通用口径 / 预置；都没有时收不动要给玩家一条出路） */
    boolean specialHarvestLearned(CropRecognizer.CropRecognition crop) {
        if (crop == null || crop.cropKey() == null) return false;
        String tag = CropRuntimeStateResolver.variantTag(crop.modelIdentity(), crop.stageName());
        String scoped = specialRuleKey(crop.cropKey(), tag);
        boolean tagged = !scoped.equals(crop.cropKey());
        return specialHarvestRules.containsKey(scoped)
            || (!tagged && specialHarvestRules.containsKey(crop.cropKey()))
            || specialHarvestRules.containsKey(SPECIAL_HARVEST_SERVER_WIDE_KEY)
            || StardewSpecialHarvestPresets.find(serverKey, scoped) != null
            || StardewSpecialHarvestPresets.find(serverKey, crop.cropKey()) != null;
    }

    /**
     * 玩家手动收了一次特殊阶段作物（左键破坏 / 右键交互）：先记一条待确认观测。
     *
     * <p><b>为什么不自动试探</b>：本服的巨型作物是「左键破坏」、老服是「金锄头右键」，
     * 拿错动作去试会直接砸掉一棵巨型作物（不可逆）。所以只从玩家自己的动作里学——
     * 玩家怎么收的（连当时手上那件一起），模块就怎么收，零风险。</p>
     *
     * <p>调用方（Mixin）上报玩家当时的<b>主手物品</b>（空手传空栈 = 工具不限）；
     * 这里确认「那一格此刻确实是特殊阶段作物」，并核对「动作 + 手持」是否与本服已知口径相同。</p>
     */
    public void observePlayerSpecialHarvest(BlockPos pos, StardewSpecialHarvestAction action, ItemStack held) {
        if (pos == null || action == null || profile == null) return;
        BlockPos pot = StardewFarmScanner.normalizeToPot(pos);
        CropRecognizer.CropRecognition crop = CropRecognizer.recognizeAtPot(pot, profile);
        if (crop == null || crop.cropKey() == null || crop.state() != CropState.SPECIAL) return;
        StardewSpecialHarvestRecipe recipe = StardewSpecialHarvestRecipe.of(action, held);
        // 与本服已知口径完全一致（动作 + 手持都同）不必再学：右键服上玩家右键收一棵是最常见的动作
        if (specialHarvestRecipe(crop).compareKey().equals(recipe.compareKey())) return;
        String tag = CropRuntimeStateResolver.variantTag(crop.modelIdentity(), crop.stageName());
        pendingSpecialObservation = new SpecialObservation(specialRuleKey(crop.cropKey(), tag), crop.cropKey(),
            recipe, pos, cellSnapshot(pos), System.currentTimeMillis() + SPECIAL_OBSERVATION_WINDOW_MS);
        LOGGER.info("[星露谷] 观测到玩家手动收割特殊变种 作物={} 变种={} 口径={} 坐标={}（等那一格真的变了才落盘）",
            crop.cropKey(), tag == null ? "无标记" : tag, recipe.displayName(), pos);
    }

    /** 那一格「此刻构成」的快照：方块 ID + 展示实体身份（读不到就是空串），用于判断动作是否真的生效 */
    private static String cellSnapshot(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null) return "";
        var blockId = BuiltInRegistries.BLOCK.getKey(mc.level.getBlockState(pos).getBlock());
        String display = StardewCropDisplayProbe.liveIdentityAt(pos);
        return blockId + "|" + (display == null ? "" : display);
    }

    /** 待确认观测的推进：那一格真的变了 → 学会并落盘；窗口内没变 → 当次动作不算数 */
    void tickSpecialObservation() {
        SpecialObservation observation = pendingSpecialObservation;
        if (observation == null) return;
        if (!cellSnapshot(observation.pos()).equals(observation.beforeSnapshot())) {
            pendingSpecialObservation = null;
            specialHarvestRules.put(observation.ruleKey(), observation.recipe());
            // 本服通用口径：只在第一次学到时写入（先到者为准）。之后学到的形态各异的作物只更新
            // 自己的专属记录，不去改通用口径 —— 否则「最后学到的那条」会顺手用在下一个还没学过的
            // 变种上，而左键破坏不可逆（同服多形态时这个风险不能留）。
            specialHarvestRules.putIfAbsent(SPECIAL_HARVEST_SERVER_WIDE_KEY, observation.recipe());
            boolean saved = specialHarvestStore.save(serverKey, configuredFingerprint, specialHarvestRules);
            LOGGER.info("[星露谷] 特殊变种口径已学会 服务器={} 口径键={}（作物={}） 口径={} 本服通用口径={} 存盘={}",
                serverKey, observation.ruleKey(), observation.cropKey(), observation.recipe().displayName(),
                specialHarvestRules.get(SPECIAL_HARVEST_SERVER_WIDE_KEY).displayName(), saved);
            if (saved && announcedSpecialLearned.add(observation.ruleKey())) {
                reporter.announceSpecialHarvestLearned(observation.cropKey(), observation.recipe());
            }
            return;
        }
        if (System.currentTimeMillis() > observation.deadline()) {
            // 点空了 / 被服务端驳回：不学，也不反复重记（下次玩家再动手会重新观测）
            pendingSpecialObservation = null;
            LOGGER.info("[星露谷] 观测到的特殊变种收割动作没有生效，已丢弃 作物={} 口径={}",
                observation.cropKey(), observation.recipe().displayName());
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
     * 本轮扫到的格子按作物状态分个类（「熟 3 · 生长 12 · 空 5」）。
     *
     * <p><b>为什么必须报出来：</b>「当前农田已处理完成」有两种截然不同的含义 —— 地里真没熟菜，
     * 或熟菜在眼前却没被认成成熟。玩家看到的画面一模一样，只能靠这行分清是哪一种
     * （实机反馈：满地的熟菜，模块报「已处理完成」，无法判断是识别问题还是逻辑问题）。</p>
     */
    private String pendingBreakdown() {
        int mature = 0;
        int growing = 0;
        int empty = 0;
        int other = 0;
        for (StardewFarmScanner.Cell cell : pending) {
            switch (cell.crop().state()) {
                case MATURE, SPECIAL -> mature++;
                case GROWING -> growing++;
                case EMPTY -> empty++;
                default -> other++;
            }
        }
        return "本轮熟 " + mature + " · 生长 " + growing + " · 空盆 " + empty
            + (other > 0 ? " · 其它 " + other : "");
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

        // 满仓整机停机：背包一个空格都没有 —— 整个模块停（不浇水、不播种、不施肥、不维护洒水器、
        // 不回中心点、不收菜、不拾取）。
        // **但「会自动把背包腾空」的两条路必须放行** —— 它们正是满仓的出路，而原先这道闸排在
        // 它们前面（卸货闸在下方、种子回收更靠后），于是背包一满就再也卸不了货、也存不回超量种子，
        // 只能玩家自己动手清（实机反馈：「背包满了为什么还提示我拿种子」）。
        // 产物箱满不在这里 —— 那条已经改成**直接关闭模块**了（见 #stopForOutputBoxFull）。
        if (planner.storageFullHalts()) {
            status.silent("FULL_HALT", "背包已满，已停机",
                "腾出空间后自动继续 · " + pendingBreakdown(), "");
            if (planner.tryStartUnloadForProtection()) {
                phase = Phase.NAVIGATE;
                return;
            }
            if (planner.tryStartSeedReturn()) {
                phase = Phase.NAVIGATE;
                return;
            }
            restartObserve();
            return;
        }

        // 资源保护：背包快满且能卸货时，优先卸货避免掉落损失
        if (planner.tryStartUnloadForProtection()) {
            phase = Phase.NAVIGATE;
            return;
        }

        // 农田内掉落优先拾取（种子自给自足 + 成品入账，必须在继续收割/种植前处理）。
        // 背包一个空格都没有时拾取必然装不进去，先去卸货（上面那一步）或停下等玩家清背包。
        if (drops.hasFarmDrop() && !planner.bagFullBlocksHarvest()) {
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
            // 当前地块还有空盆等着补种：先去把这一块的种子取回来，再去别的区域
            // （补种是「把这一块做完」的一部分，见 planner#startStickyRegionRestock）
            if (planner.startStickyRegionRestock()) {
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
            if (planner.storageFullHalts()) {
                // 这一刻「没有可派发的任务」的真实原因是满仓停手，不是农活干完了。
                // 说成「当前农田已处理完成」会让玩家以为状态机坏了（实机反馈：熟菜明明在地里）。
                status.state("FULL_HALT", "背包已满，暂停收菜",
                    "腾出空间后自动继续 · " + pendingBreakdown());
            } else {
                status.state("WAIT_GROWTH", "等待作物生长", "当前农田已处理完成 · " + pendingBreakdown());
            }
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
        } else if (executor.digInProgress()) {
            // 破坏正在进行（学到的左键口径 / 清枯苗 / 清错位 / 清杂物，载体可能是非零硬度方块）：
            // 服务端要「START → 等够挖掘时长 → STOP」才会砸掉，这中间不能用普通重试上限把这一轮
            // 打断（那会变成每几刻重挖一次、永远挖不穿）。原地继续挖，不重走寻路。
            stepTick = 0;
            phase = Phase.INTERACT;
        } else if (taskType == TaskType.WATER && (executor.canWaterIsEmpty() || retryCount >= MAX_RETRY)) {
            // 水量字段缺失时以连续浇水失败兜底判断空壶，下一轮必须先去补水点。
            forceRefill = true;
            phase = Phase.REPLAN;
        } else if (retryCount >= MAX_RETRY) {
            // 杂物可能是砸不掉的东西（服务端保护方块 / 屏障）：给它一个退避窗口，
            // 否则每一轮都会重新走到那一格再试一次，观感就是站在田里原地打转。
            // 收割同理：右键确实发出去了、世界却一点没变（作物其实不熟 / 服务端另有条件）时，
            // 不该按秒重发右键刷屏——退避窗口内先去做别的格，到期再回来试一次。
            if (taskType == TaskType.CLEAR_JUNK || taskType == TaskType.HARVEST) {
                planner.blockNavigationTarget(taskType, targetPot);
            }
            // 特殊变种：默认口径（金锄头右键）在本服收不动时，给玩家一条可执行的出路
            // （本服实测是左键破坏，见 StardewSpecialHarvestAction）。每个作物只提示一次。
            if (taskType == TaskType.HARVEST) {
                reporter.announceSpecialHarvestUnknownOnce();
                // 学到的「左键破坏」口径也没砸掉（服务端保护这一格 / 口径其实不对）：同样要报出来，
                // 否则玩家只看到对着同一格反复砸却一直不变。
                reporter.announceSpecialBreakBlockedOnce();
            }
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
        // 挖掘序列还在跑时先别还原手持：服务端累计进度是按「当前手持」的破坏速度算的，
        // 中途把工具换回去（常常换回一件慢物品）会让这一轮的 STOP 被驳回。
        // 序列自己收尾后，由下一次重规划（或停机）的 restoreHandNow 还原。
        if (!executor.digInProgress()) executor.restoreHandNow();
        // 注意：这里<b>不</b>中止进行中的挖掘序列（{@link StardewFarmExecutor#tickBreakDig()} 会自己收尾）。
        // 之前每次重规划都 ABORT，而重规划随时可能发生（重扫、季节代次变化、玩家开背包…），
        // 于是「START → 被 ABORT → 再 START」无限循环，方块永远挖不掉（实机事故）。
        // 换目标时由 breakAt 自己先 ABORT 上一格，模块停机 / 换世界由 reset() 兜底。
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
