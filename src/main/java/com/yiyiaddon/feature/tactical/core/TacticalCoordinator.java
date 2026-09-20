package com.yiyiaddon.feature.tactical.core;

import com.yiyiaddon.core.TickRateMonitor;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.ServerPositionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战术协调器：四个战术模块（飞行绕过 / 发包防踢 / 发包秒破 / 服务器检测）的唯一决策与状态中枢。
 *
 * <p>逐字移植旧项目 {@code tactical/core/TacticalCoordinator.java}（379 行）的判定逻辑与常量，
 * 仅做三处框架适配（旧项目为旧框架的事件总线）：</p>
 * <ol>
 *     <li><b>常驻订阅</b>：旧实现用静态块 {@code EVENT_BUS.subscribe(本类)} 挂载，生命周期不依赖任何
 *         模块开关。新架构下 {@link ClientEventBus} 是全局广播、与模块开关无关，因此改为
 *         {@link #init()} 以固定所有者 {@value #OWNER} 订阅，由入口一次性调用，语义完全一致，
 *         且不新增任何 Fabric 事件注册点（核心事件注册只在 {@code EventDispatcher}）。</li>
 *     <li><b>收包来源</b>：旧实现在收包事件里读 {@code ClientboundPlayerPositionPacket} 并当场换算
 *         绝对坐标；新架构由核心在收包瞬间完成同一份换算并派发
 *         {@link ClientEventType#SERVER_POSITION}（含入队时算好的玩家距离），
 *         判定逻辑与阈值一字不改（第 169 条：同源换算只留一份）。</li>
 *     <li><b>线程</b>：事件一律在主线程派发，因此不再需要 {@code mc.execute} 切线程；
 *         会话代次 token 仍按旧结构保留（防跨服迟到的检测结果污染）。</li>
 * </ol>
 *
 * <p>通知事件：旧项目用总线派发 {@code RubberBandDetectedEvent} / {@code AntiCheatDetectedEvent}，
 * 新架构不为业务事件扩充核心枚举，改为本类的 {@link Listener} 回调（模块在启用时注册、关闭时注销）。</p>
 *
 * @author yiyijia
 */
public final class TacticalCoordinator {

    /** 常驻订阅的所有者标识（与模块无关，永远在线） */
    private static final String OWNER = "core.tactical";

    private static final Minecraft mc = Minecraft.getInstance();

    /** 拉回冷却时长：收到拉回包后 2 秒内暂停所有绕过类动作 */
    private static final long RUBBER_BAND_COOLDOWN_MS = 2000L;

    /** 连续拉回计数的滑动窗口：超过该时长无拉回视为脱离危险，计数清零 */
    private static final long RUBBER_BAND_WINDOW_MS = 10_000L;

    /** TPS 采样周期（tick）：20 tick 约 1 秒采一次，避免高频读 TickRate */
    private static final int TPS_SAMPLE_INTERVAL = 20;

    /** 卡顿判定阈值：TPS 低于该值判定服务器卡顿 */
    private static final double LAGGING_TPS_THRESHOLD = 18.0;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  检测状态（唯一写者：reportDetection / beginSession）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 检测到的服务器核心显示名 */
    private static volatile String detectedServerCore = "未知";

    /** 检测到的反作弊显示名 */
    private static volatile String detectedAntiCheat = "未知";

    /** 是否命中高风险反作弊（按 ServerFingerprints.isHighRisk 名单统一判定） */
    private static volatile boolean highRiskAntiCheat = false;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  运行态状态（唯一写者：本类内部）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 拉回冷却截止时间戳（毫秒），0 表示不在冷却；读时自检到期，不会死锁 */
    private static volatile long rubberBandCooldownUntil = 0L;

    /** 服务器卡顿状态 */
    private static volatile boolean serverLagging = false;

    /** 最近一次采样的服务器 TPS */
    private static volatile double currentTps = 20.0;

    /** TPS 采样内部计数 */
    private static int tpsSampleTick = 0;

    /** 本次会话累计拉回次数（供检测层判断「拉回频繁」） */
    private static volatile int rubberBandTotal = 0;

    /** 滑动窗口内连续拉回次数（供飞行降级与限速收紧） */
    private static volatile int consecutiveRubberBands = 0;

    /** 最近一次拉回的时间戳 */
    private static volatile long lastRubberBandAt = 0L;

    /**
     * 传送落地锚点：最近一次「距离超过 10 格」的位置包目标，即刚落地的那个点。
     *
     * <p><b>为什么需要它</b>（2026-09-20 实机日志证据）：传送落地后，服务端会在若干秒内反复把玩家
     * 按回落点——日志里 {@code /rtp} 落地为 {@code (-841.50, 68.00, 313.50)}，随后 6 秒内下发 45 个
     * 位置包，目标**全部**是同一个落点，玩家每次只漂移 0.3~1.7 格（正常步行速度）。
     * 这些纠正的目标与落点几乎重合，属于「服务端不让我离开落点」的传送余波，
     * 不是「我移动过快被校验」的拉回；按拉回统计会直接把会话推成「拉回频繁 / 高风险」，
     * 从而触发无关的绕过策略收紧。</p>
     *
     * <p>值对象（{@link Vec3} 不可变），不持有任何世界 / 实体引用。</p>
     */
    private static volatile Vec3 teleportAnchor;

    /** 锚点建立时刻（毫秒） */
    private static volatile long teleportAnchorAt = 0L;

    /** 锚点抑制窗口：自传送落地起的这段时间内，目标仍在落点附近的纠正算传送余波 */
    private static final long TELEPORT_SETTLE_MS = 15_000L;

    /** 「仍在落点附近」的半径：按回落点的纠正目标与落点几乎重合，留 2 格容差 */
    private static final double TELEPORT_ANCHOR_RADIUS = 2.0;

    /** 会话代次：进服/断线各加一，用于丢弃跨服迟到的检测结果 */
    private static volatile long sessionId = 0L;

    /** 飞行降级档位（0~2）：连续拉回触发升档，脱离危险窗口自动降档恢复 */
    private static volatile int degradeLevel = 0;

    /** 最近一次降级/恢复档位调整的时间戳（恢复每窗口只调一档，防止一 tick 全量恢复） */
    private static volatile long lastDegradeAdjustAt = 0L;

    /** 通知监听者：拉回 / 反作弊检测完成（模块启用时注册，关闭时注销） */
    private static final List<Listener> LISTENERS = new CopyOnWriteArrayList<>();

    private static boolean initialized;

    private TacticalCoordinator() {
    }

    /** 战术模块侧的通知回调；全部在主线程调用 */
    public interface Listener {

        /** 登记到一次短距离服务端位置纠正（拉回） */
        default void onRubberBand() {
        }

        /** 反作弊检测结论更新（含行为证据补足出的「未知反作弊」） */
        default void onAntiCheat(String antiCheatName) {
        }
    }

    /**
     * 常驻挂载：本类一旦被初始化即订阅核心事件，生命周期不依赖任何模块开关（等价旧项目静态块自订阅）。
     *
     * <p>由 {@code YiyiAddonClient} 在启动装配阶段调用一次；重复调用无效。</p>
     */
    public static void init() {
        if (initialized) return;
        initialized = true;

        ClientEventBus.subscribe(OWNER, ClientEventType.TICK, TacticalCoordinator::onTick);
        ClientEventBus.subscribe(OWNER, ClientEventType.JOIN_SERVER, event -> beginSession());
        ClientEventBus.subscribe(OWNER, ClientEventType.DISCONNECT, event -> beginSession());
        ClientEventBus.subscribe(OWNER, ClientEventType.SERVER_POSITION, TacticalCoordinator::onServerPosition);
    }

    /** 注册通知监听者；同一实例重复注册会被去重 */
    public static void addListener(Listener listener) {
        if (listener != null && !LISTENERS.contains(listener)) {
            LISTENERS.add(listener);
        }
    }

    /** 注销通知监听者 */
    public static void removeListener(Listener listener) {
        LISTENERS.remove(listener);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  状态读取（模块只读，禁止直接改字段）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public static String getDetectedServerCore() {
        return detectedServerCore;
    }

    public static String getDetectedAntiCheat() {
        return detectedAntiCheat;
    }

    /** 是否命中高风险反作弊（旧 API hasAdvancedAntiCheat 的语义对齐版） */
    public static boolean hasAdvancedAntiCheat() {
        return highRiskAntiCheat;
    }

    /** 是否命中高风险反作弊名单 */
    public static boolean isHighRiskAntiCheat() {
        return highRiskAntiCheat;
    }

    /** 是否处于拉回冷却（调用即检查到期，到期自动解除，不会死锁） */
    public static boolean isRubberBandCooldown() {
        if (rubberBandCooldownUntil == 0L) return false;
        if (System.currentTimeMillis() >= rubberBandCooldownUntil) {
            rubberBandCooldownUntil = 0L;
            return false;
        }
        return true;
    }

    public static boolean isServerLagging() {
        return serverLagging;
    }

    public static double getCurrentTps() {
        return currentTps;
    }

    /** 本次会话累计拉回次数（检测层判断反作弊激进程度用） */
    public static int getRubberBandTotal() {
        return rubberBandTotal;
    }

    /** 当前会话代次（观测层调度异步任务时取走，上报时校验防串服） */
    public static long currentSession() {
        return sessionId;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  L0 观测层写入入口（唯一合法的检测写入通道）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 上报服务器检测结果（仅供观测层「服务器检测」模块调用）。
     *
     * @param reportSessionId 调度检测时取走的会话代次；与当前不一致说明结果
     *                        来自上一个服务器，直接丢弃，防止跨服污染
     * @param core            服务器核心显示名（可为 null 表示不更新）
     * @param antiCheat       反作弊显示名（可为 null 表示不更新）
     */
    public static void reportDetection(long reportSessionId, String core, String antiCheat) {
        if (reportSessionId != sessionId) return;

        if (core != null) detectedServerCore = core;
        if (antiCheat == null) return;
        // 已由三次真实短距离拉回确认存在移动校验后，后续一次“没看到静态指纹”
        // 不能把更强的行为证据降回未发现；具体型号命中时仍允许覆盖。
        if ("未发现".equals(antiCheat) && highRiskAntiCheat) return;

        boolean changed = !antiCheat.equals(detectedAntiCheat);
        detectedAntiCheat = antiCheat;
        highRiskAntiCheat = ServerFingerprints.isHighRisk(antiCheat);

        // 多轮侦测只在结论实际变化时广播，避免每轮让下游重复收紧或刷提示。
        if (changed && !"未检测".equals(antiCheat) && !"未发现".equals(antiCheat)) {
            notifyAntiCheat(antiCheat);
        }
    }

    /**
     * 飞行执行请求准入裁决（唯一决策点）。
     *
     * <p>顺序：拉回冷却（全局暂停）→ 降级档位恢复/升级 → 按档位沿降级链走靶 →
     * 发包飞行准入（高风险/无飞行权限自动改派）。执行器每 tick 以此为准，
     * 不允许绕过协调器自行换模式。</p>
     *
     * @param requested        用户设置里选定的模式
     * @param adaptiveSlowdown 自适应降速开关（执行器传入用户偏好）
     * @param degradeThreshold 连续拉回降级阈值（执行器传入用户偏好）
     * @return 本 tick 的执行决策
     */
    public static FlightPolicy.FlightDecision evaluateFlight(FlightPolicy.FlightMode requested,
                                                             boolean adaptiveSlowdown,
                                                             int degradeThreshold) {
        // 拉回冷却期统一暂停全部模式：此时继续注入移动等于顶风作案
        if (isRubberBandCooldown()) {
            return new FlightPolicy.FlightDecision(requested, FlightPolicy.FlightReason.COOLDOWN);
        }

        long now = System.currentTimeMillis();

        // 恢复：脱离危险窗口（10 秒无拉回）每窗口只降一档，避免刚脱险就全量放开
        if (degradeLevel > 0
            && now - lastRubberBandAt > RUBBER_BAND_WINDOW_MS
            && now - lastDegradeAdjustAt > RUBBER_BAND_WINDOW_MS) {
            degradeLevel--;
            lastDegradeAdjustAt = now;
        }

        // 降级：滑动窗口内连续拉回达到阈值，升一档（封顶 2 档）
        if (adaptiveSlowdown && consecutiveRubberBands >= degradeThreshold && degradeLevel < 2) {
            degradeLevel++;
            lastDegradeAdjustAt = now;
            consecutiveRubberBands = 0;
        }

        // 按档位沿降级链走向目标模式（降级链：发包飞行/烟花火箭 → 安全滑翔 → 原版连跳）
        FlightPolicy.FlightMode target = requested;
        for (int i = 0; i < degradeLevel; i++) {
            FlightPolicy.FlightMode next = degradeStep(target);
            if (next == target) break;
            target = next;
        }

        if (target != requested) {
            return new FlightPolicy.FlightDecision(target, FlightPolicy.FlightReason.DEGRADED);
        }

        // 发包飞行准入：26.1.2 官方 ServerGamePacketListenerImpl.handleMovePlayer
        // 的浮空判定只认物理支撑 + allowFlight/mayfly/鞘翅/悬浮等合法状态，
        // 发包无法豁免。只有服务端真正授予飞行能力（/fly、创造、旁观）才允许执行；
        // 未授权时不再停摆，改为沿降级链自动落到可执行模式（安全滑翔/原版连跳）。
        if (requested == FlightPolicy.FlightMode.PACKET_FLY) {
            if (highRiskAntiCheat) {
                return new FlightPolicy.FlightDecision(degradeStep(requested), FlightPolicy.FlightReason.HIGH_RISK_AC);
            }
            Player player = mc.player;
            if (player == null || (!player.getAbilities().flying && !player.getAbilities().mayfly)) {
                return new FlightPolicy.FlightDecision(degradeStep(requested), FlightPolicy.FlightReason.NO_FLY_ABILITY);
            }
        }

        return new FlightPolicy.FlightDecision(requested, FlightPolicy.FlightReason.GRANTED);
    }

    /**
     * 降级链单步：朝「不依赖任何前置条件」的方向退一档。
     * 发包飞行/烟花火箭 → 安全滑翔（鞘翅）→ 原版连跳（仅需地面，最终档）。
     *
     * <p>降级目标必须有可执行资产：鞘翅档缺鞘翅时直接跨到原版连跳，
     * 防止降级决策产出执行器无法落地的模式（决策层对资产可用性负责）。</p>
     */
    private static FlightPolicy.FlightMode degradeStep(FlightPolicy.FlightMode mode) {
        FlightPolicy.FlightMode target = switch (mode) {
            case PACKET_FLY, FIREWORK_BOOST -> FlightPolicy.FlightMode.SAFE_GLIDE;
            case SAFE_GLIDE, SEQUENCE_SCAFFOLD -> FlightPolicy.FlightMode.VANILLA_MIMIC;
            case VANILLA_MIMIC -> FlightPolicy.FlightMode.VANILLA_MIMIC;
        };

        if (target == FlightPolicy.FlightMode.SAFE_GLIDE && !hasElytra()) {
            return FlightPolicy.FlightMode.VANILLA_MIMIC;
        }
        return target;
    }

    /** 背包/护甲槽里是否有鞘翅（安全滑翔档的资产校验） */
    private static boolean hasElytra() {
        Player player = mc.player;
        if (player == null) return false;

        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == Items.ELYTRA && !stack.nextDamageWillBreak()) return true;
        }
        return false;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  生命周期协议（唯一入口，不依赖任何模块开关）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 全量重置所有全局状态（进服/离服统一走这里，供外部兜底调用） */
    public static void reset() {
        beginSession();
    }

    private static void beginSession() {
        sessionId++;
        detectedServerCore = "未知";
        detectedAntiCheat = "未知";
        highRiskAntiCheat = false;
        rubberBandCooldownUntil = 0L;
        serverLagging = false;
        currentTps = 20.0;
        tpsSampleTick = 0;
        rubberBandTotal = 0;
        consecutiveRubberBands = 0;
        lastRubberBandAt = 0L;
        degradeLevel = 0;
        lastDegradeAdjustAt = 0L;
        // 传送落点锚点同属会话内状态：跨服残留会把新会话开局的真拉回误当余波放过
        teleportAnchor = null;
        teleportAnchorAt = 0L;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  拉回响应唯一入口（只记状态，不补发包）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 收到服务端位置纠正时的统一处理。
     *
     * <p>为什么不再补发确认包：26.1.2 原版 {@code ClientPacketListener.handleMovePlayer}
     * 已自动回发 AcceptTeleportation + 1 个 PosRot 确认包；旧发包防踢额外补发的 3 个静止包
     * 既冗余，又会被飞行模块的发包拦截器二次篡改，故重构后接收链路上只有本协调器这一家处理器。</p>
     *
     * <p>真拉回与服务器传送的区分（距离阈值 10 格）与零位移过滤（1e-4）逐字照旧；
     * 距离由核心在收包瞬间（网络线程）算好，与旧实现同刻。</p>
     *
     * <p><b>换世界不算拉回</b>：跨维度传送与死亡重生前后，服务器会补发位置包，核心在收包瞬间
     * 算距离时客户端玩家实体可能仍在旧世界，得到的是跨坐标系的无意义数值
     * （跨世界 {@code /home} 落点与旧坐标数值相近时会落进 10 格阈值被误判）。
     * 核心已按「登录 / 重生包之后的静默窗口」标明来源（{@link ServerPositionEvent#worldChange()}），此处直接放行。</p>
     *
     * <p><b>传送余波不算拉回</b>：落地之后服务端可能连着若干秒把玩家按回落点，
     * 这些纠正的目标与落点几乎重合（见 {@link #teleportAnchor}），同样不登记。</p>
     */
    private static void onServerPosition(ClientEvent event) {
        ServerPositionEvent position = event.position();
        if (position == null) return;
        // 旧实现只处理玩家位置包（ClientboundPlayerPositionPacket），载具包不参与拉回判定
        if (position.vehicle()) return;

        double distance = position.playerDistance();

        // 距离超过 10 格视为传送，不触发拉回处理；同时记下落点作为后续余波判据的锚点。
        // 放在换世界放行之前：入口包后的落地同样要建立锚点，否则落地后的余波纠正会被误登记
        if (distance > 10.0) {
            markTeleportAnchor(position.position());
            return;
        }

        // 换世界静默窗口内的位置包：不是反作弊拉回，不登记也不触发冷却
        if (position.worldChange()) return;
        if (mc.player == null) return;

        // 纯旋转/零位移同步不是拉回。
        if (distance < 1.0E-4) return;

        // 传送余波：目标仍在刚落地的锚点附近，是服务端把我按在落点，不是移动校验
        if (isTeleportAftermath(position.position())) return;

        long packetSession = sessionId;
        recordRubberBand(packetSession);
    }

    /** 记录传送落点（距离超过 10 格的位置纠正 = 被传送到新位置） */
    private static void markTeleportAnchor(Vec3 landing) {
        teleportAnchor = landing;
        teleportAnchorAt = System.currentTimeMillis();
    }

    /** 该纠正的目标是否落在刚落地点附近（传送余波）；锚点过期即自行清理 */
    private static boolean isTeleportAftermath(Vec3 target) {
        Vec3 anchor = teleportAnchor;
        if (anchor == null) return false;
        if (System.currentTimeMillis() - teleportAnchorAt > TELEPORT_SETTLE_MS) {
            teleportAnchor = null;
            return false;
        }
        return target.distanceTo(anchor) <= TELEPORT_ANCHOR_RADIUS;
    }

    /** 登记一次已分类的短距离服务端位置纠正。 */
    private static void recordRubberBand(long packetSession) {
        if (packetSession != sessionId || mc.player == null) return;

        long now = System.currentTimeMillis();
        rubberBandCooldownUntil = now + RUBBER_BAND_COOLDOWN_MS;
        rubberBandTotal++;

        // 滑动窗口连续计数：超过 10 秒无拉回视为脱离危险，计数清零
        if (now - lastRubberBandAt > RUBBER_BAND_WINDOW_MS) {
            consecutiveRubberBands = 0;
        }
        lastRubberBandAt = now;
        consecutiveRubberBands++;

        // 静态指纹可能被隐藏（无指令权限、改 brand、私有核心）。连续观测到三次
        // 短距离权威纠正后，以“未知移动校验”补足识别并立即驱动全部下游策略。
        if (rubberBandTotal == 3
            && ("未知".equals(detectedAntiCheat) || "未发现".equals(detectedAntiCheat))) {
            detectedAntiCheat = "未知反作弊（拉回频繁，已确认存在移动校验）";
            highRiskAntiCheat = true;
            notifyAntiCheat(detectedAntiCheat);
        }

        notifyRubberBand();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  TPS 采样（常驻，不依赖服务器检测开关）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private static void onTick(ClientEvent event) {
        if (mc.level == null) return;

        if (++tpsSampleTick < TPS_SAMPLE_INTERVAL) return;
        tpsSampleTick = 0;

        float tps = TickRateMonitor.tickRate();
        // tps <= 0 表示未进服或数据未就绪，不能据此判卡顿
        if (tps <= 0) return;

        currentTps = tps;
        serverLagging = tps < LAGGING_TPS_THRESHOLD;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  通知回调（只做通知，不做状态）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 反作弊检测完成（状态已写入，监听者做本地反应如收紧限速） */
    private static void notifyAntiCheat(String antiCheatName) {
        for (Listener listener : LISTENERS) {
            try {
                listener.onAntiCheat(antiCheatName);
            } catch (Throwable ignored) {
                // 单个监听者异常不影响其余监听者
            }
        }
    }

    /** 一次拉回登记完成（监听者做本地反应如记录分析数据） */
    private static void notifyRubberBand() {
        for (Listener listener : LISTENERS) {
            try {
                listener.onRubberBand();
            } catch (Throwable ignored) {
                // 单个监听者异常不影响其余监听者
            }
        }
    }
}
