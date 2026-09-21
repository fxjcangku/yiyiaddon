package com.yiyiaddon.feature.tactical;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.core.net.SendDecision;
import com.yiyiaddon.core.net.SendDelayService;
import com.yiyiaddon.core.net.SendGate;
import com.yiyiaddon.core.net.SendView;
import com.yiyiaddon.feature.tactical.config.AntiKickBypassSettings;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.AntiKickBypassPage;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 发包防踢模块（L2 发包执行器）：伪装客户端 + 聊天排队 + 防挂机 + 挖掘/放置限速 + 视角抖动 + 网络延迟。
 *
 * <p><b>旧项目对应物</b>：{@code tactical/AntiKickBypass.java}（741 行）。全部算法、常量（超时 / 间隔 /
 * 上限 / 随机范围 / 节流窗口）、播报文案（含 {@code §} 颜色码与 {@code §8▸} 排版）、设置名与默认值
 * 逐条对照搬运；旧项目的三处疑似缺陷原样保留（见下方「保留缺陷」）。</p>
 *
 * <p><b>职责边界（与旧实现一致）</b>：只负责「发包侧执行」——伪装客户端、聊天排队、防挂机、
 * 挖掘/放置限速、视角抖动、网络延迟、拉回分析记录；拉回包本身不处理（冷却登记与统计统一由
 * {@link TacticalCoordinator} 完成），不从本模块补发任何拉回确认包。</p>
 *
 * <p><b>不拦谁（用户 2026-09-19 决策）</b>：平台层方块交互发包适配器
 * {@code platform/network/BlockPacketSender} 的包（星露谷 / 自动农场 / 自动箱子 / 村民补给 /
 * 开挖容器等的浇水、播种、收割、破坏、开箱）走绕行直发，<b>不进本模块的限速与网络延迟判据</b>——
 * 那是模块按任务节奏主动批量发起的交互，一次补水连发 8~32 包被打掉大半会让功能变慢甚至卡住。
 * 本模块的限速与延迟只作用于玩家手动操作的包与聊天等其余包。</p>
 *
 * <p><b>发包秒破的优先级（用户 2026-09-19 追问）</b>：秒破接管原版 START 的方式是「取消原版包、
 * 自己直发」，若本模块先判定，超限时会把那条 START 丢掉、秒破的规则根本不会执行（那一下既不挖也不秒破）。
 * 因此秒破用 {@link SendGate} 的高优先级注册（见 {@code PacketInstantBreakModule.GATE_PRIORITY}），
 * 先于本模块拿到原版 START；它自己发的包仍走核心绕行通道。本模块保持 {@link SendGate#DEFAULT_PRIORITY}，
 * 对其它模块的判定顺序与本次改动前完全一致。</p>
 *
 * <p><b>框架适配点（旧 → 新，逐条）</b>：</p>
 * <ol>
 *     <li><b>发包拦截</b>：旧 {@code PacketEvent.Send} + {@code event.cancel()/packet=/sendSilently}
 *         → {@link SendGate#register} 注册本模块的 {@link #onSendRule(SendView)}；摘假潜行/假疾跑改用
 *         {@link SendDecision#replaceInput(int)}，取消用 {@link SendDecision#cancel()}，
 *         网络延迟改用 {@link SendDecision#delay(long)}（保序与到点重发由核心
 *         {@link SendDelayService} 负责，等价旧实现的 {@code lastDelaySendAt} 与 5ms 轮询线程）。</li>
 *     <li><b>线程模型</b>：规则在网络线程被同步调用，因此规则内<b>只读快照、只做原子增减</b>：
 *         玩家侧判据（水平速度 / 脚下是否冰面 / 是否疾跑）由主线程每刻刷新到 {@code volatile} 快照；
 *         计数一律 {@link AtomicInteger}；规则会读的开关与阈值（{@code AntiKickBypassSettings} 里标注
 *         volatile 的 12 个字段）同样保证跨线程可见。真正的补发包（品牌伪装、聊天重发、防挂机转身、视角抖动）
 *         全部在主线程 {@link #onTick(Minecraft)} 里走 {@link ClientPacketSender} 语义直发
 *         （绕行闸门，等价旧 {@code sendSilently}，不会再被自己的规则二次拦截）。</li>
 *     <li><b>聊天排队</b>：旧实现靠 {@code forwardingChat} 转发标记避免重发再入队；本项目
 *         {@link ClientPacketSender#sendChat} 走核心绕行通道，天然不再入队，故该标记取消
 *         （语义等价，登记为有意差异）。被取消的聊天原文由核心派发
 *         {@link ClientEventType#CLIENT_CHAT}，本模块订阅后入队、按旧间隔重发。</li>
 *     <li><b>生命周期</b>：旧 {@code GameJoinedEvent} / {@code GameLeftEvent}
 *         → {@link ClientEventType#JOIN_SERVER} / {@link ClientEventType#DISCONNECT}；
 *         旧 {@code SessionResetEvent} → 协调器在 JOIN/DISCONNECT 时上调 {@code beginSession()}，
 *         本模块按旧事件顺序自行先 {@code resetSessionState()} 再处理进/离服，
 *         与旧项目「协调器优先级 −1000 先跑、模块后跑」的顺序一致。</li>
 *     <li><b>协调器通知</b>：旧总线 {@code RubberBandDetectedEvent} / {@code AntiCheatDetectedEvent}
 *         → {@link TacticalCoordinator.Listener#onRubberBand()} / {@link TacticalCoordinator.Listener#onAntiCheat(String)}
 *         （启用时注册、关闭时注销；协调器保证在主线程回调，可直接读玩家状态）。</li>
 *     <li><b>单人世界不可开启</b>：旧 {@code chatFeedback=false; toggle(); chatFeedback=true;}
 *         只写在 {@code onActivate} 里，本项目本模块默认开启、装配期（主菜单）就恢复启用，
 *         那道闸门拦不住 → 改为 {@link Module#environmentRefusal()} 交给框架，启用与进世界两个时机都拦。</li>
 *     <li><b>播报</b>：旧基类 {@code notify / warning / notifyError} 与
 *         {@code highlightText / highlightNumber} 的颜色码包装在本类内逐字保留，正文经
 *         {@link ClientChat#send} 输出。</li>
 * </ol>
 *
 * <p><b>保留缺陷（旧项目原样，禁止顺手修复，已在开发报告登记）</b>：</p>
 * <ol>
 *     <li>{@code throttleFactor} 在会话重置 {@link #resetSessionState()} 里被打回 1.0，
 *         而旧实现只在开模块时按协调器现态取过一次 0.5，因此「进服/离服后高风险反作弊的
 *         收紧会丢」，直到下一次反作弊结论变化才恢复；</li>
 *     <li>载具移动包未进 {@code shouldDelay} 的豁免名单（旧 {@code shouldDelay:795-801} 只豁免
 *         KeepAlive / AcceptTeleportation / MovePlayer / PlayerAction），因此开启网络延迟时载具包
 *         同样被随机延迟。</li>
 * </ol>
 *
 * <p><b>已知能力缺口（既有框架件缺能力，本批未改既有文件）</b>：{@link SendView} 对自定义负载只给
 * 频道 id，拿不到 {@code BrandPayload.brand()} 的值，因此旧实现的 {@code !"vanilla".equals(brand)}
 * 判据无法照抄，改为「{@code 改客户端名字} 开启即伪装」（fabric 环境下客户端 brand 实为
 * {@code fabric}，两者等效）。</p>
 *
 * @author yiyijia
 */
public final class AntiKickBypassModule extends Module implements TacticalCoordinator.Listener {

    /** 模块 ID（状态文件键 / 发包闸门所有者 / 快捷键键名后缀） */
    public static final String MODULE_ID = "antikick";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = "发包防踢";

    // ── 逐字常量（旧 AntiKickBypass 源码字面量） ──

    /** 品牌伪装值（旧 {@code new BrandPayload("vanilla")}） */
    private static final String BRAND_VALUE = "vanilla";

    /** 品牌频道 id（旧 {@code BrandPayload} 的类型 id） */
    private static final String BRAND_CHANNEL = "minecraft:brand";

    /** 频道注册 / 注销的本体 id（旧实现一并拦截，否则伪造客户端形同虚设） */
    private static final String REGISTER_CHANNEL = "minecraft:register";
    private static final String UNREGISTER_CHANNEL = "minecraft:unregister";

    /** 限速计数窗口（毫秒，旧 {@code now - lastThrottleResetTime >= 1000}） */
    private static final long THROTTLE_WINDOW_MS = 1000L;

    /** 动态限速下限（旧 {@code Math.max(2, ...)}）：无论如何不放行到 2 个/秒以下 */
    private static final int MIN_THROTTLE_LIMIT = 2;

    /** 假潜行判据的速度阈值（旧 {@code speed > 0.16}） */
    private static final double FAKE_SNEAK_SPEED_THRESHOLD = 0.16;

    /** 防挂机周期（tick，旧 {@code antiAfkTicker >= 100}，即 5 秒） */
    private static final int ANTI_AFK_PERIOD_TICKS = 100;

    /** 拉回播报节流窗口（毫秒，旧 {@code now - lastRubberBandNotice >= 5000}） */
    private static final long RUBBER_BAND_NOTICE_INTERVAL_MS = 5000L;

    /** 短会话判定窗口（毫秒，旧 {@code sessionDuration < 60_000}） */
    private static final long SESSION_SHORT_MS = 60_000L;

    /** 拉回记录里「飞行时被拉」的判据（旧 {@code getDeltaMovement().y > -0.08}） */
    private static final double RUBBER_BAND_FLYING_VY = -0.08;

    /** 拉回记录里「高速时被拉」的判据（旧 {@code r.speed > 0.3}） */
    private static final double RUBBER_BAND_SPEED_THRESHOLD = 0.3;

    /** 分析建议的触发占比（旧 {@code > total * 0.5} / {@code > total * 0.4}） */
    private static final double RECOMMEND_FLYING_RATIO = 0.5;
    private static final double RECOMMEND_DIGGING_RATIO = 0.4;
    private static final double RECOMMEND_PLACING_RATIO = 0.4;

    /** 高风险反作弊时的收紧倍率（旧 {@code throttleFactor = 0.5}） */
    private static final double HIGH_RISK_THROTTLE_FACTOR = 0.5;

    /** 视角抖动的位移判据与间隔随机范围（旧 {@code > 0.01} / {@code 3 + nextInt(6)}） */
    private static final double SHAKE_MOVING_EPSILON = 0.01;
    private static final int SHAKE_INTERVAL_BASE = 3;
    private static final int SHAKE_INTERVAL_BOUND = 6;
    private static final float SHAKE_PITCH_LIMIT = 90f;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（控制台页面读写） */
    private final AntiKickBypassSettings settings = new AntiKickBypassSettings();

    // ━━━ 网络线程只读 / 主线程写入的状态快照 ━━━

    /**
     * 假潜行判据的玩家侧快照（旧实现直接在发包回调里读 {@code mc.player}）。
     *
     * <p>规则跑在未知线程（Netty），禁止碰游戏对象，因此把三项判据在主线程每刻刷成
     * {@code volatile}：水平移动速度 / 脚下是否是冰 / 是否正在疾跑。三者与发包同属一刻，
     * 判据（{@code 速度 > 0.16 且不在冰面且未疾跑}）逐字不变。</p>
     */
    private volatile double fakeSneakSpeed;
    private volatile boolean fakeSneakOnIce;
    private volatile boolean fakeSneakSprinting;

    /** 品牌伪装待补发标记：规则取消品牌包后由主线程下一句 tick 补发（见类注释「能力缺口」） */
    private volatile boolean brandSpoofPending;

    /** 挖掘/放置限速计数器（网络线程自增，主线程每秒重置） */
    private final AtomicInteger digThisSecond = new AtomicInteger();
    private final AtomicInteger interactThisSecond = new AtomicInteger();

    /** 本会话伪装统计（网络线程自增，主线程播报） */
    private final AtomicInteger sessionBrandSpoofs = new AtomicInteger();
    private final AtomicInteger sessionChannelBlocks = new AtomicInteger();

    /**
     * 已放行 START 的方块；对应 STOP/ABORT 必须无条件放行，保证协议动作成对。
     *
     * <p>并发集合：网络线程增删、主线程清空（旧实现直接用了 {@code HashSet}，属同类竞态，
     * 这里只换成并发容器，判据与调用时机一字未改）。</p>
     */
    private final Set<BlockPos> permittedDigTargets = ConcurrentHashMap.newKeySet();

    // ━━━ 主线程私有状态（仅 onTick / 事件 / 生命周期访问） ━━━

    /** 聊天队列（仅主线程访问：CLIENT_CHAT 在主线程派发，重发也在 onTick） */
    private final Queue<String> chatQueue = new LinkedList<>();
    private long lastChatSendTime;

    /** 防挂机计数器 */
    private int antiAfkTicker;

    /** 限速窗口起点（毫秒；旧实现是 1 秒窗口，这里改由主线程每刻检查，阈值不变） */
    private long lastThrottleResetTime = System.currentTimeMillis();

    /**
     * 动态限速倍率：协调器确认高风险反作弊时收紧（1.0 = 正常，越小越严格），
     * 运行时打折不改用户设置。规则在网络线程读，故 {@code volatile}。
     */
    private volatile double throttleFactor = 1.0;

    /** 拉回播报节流（拉回可能连发，5 秒只报一次，避免刷屏） */
    private long lastRubberBandNotice;

    /** 本次会话起始时刻（进服时登记，离服时算存活时长；会话重置不碰它） */
    private long sessionStartAt;

    /** 上次观察到的「网络延迟」开关值：关掉时清空待发延迟队列（等价旧 {@code updateDelayWorker}） */
    private boolean lastNetworkDelayEnabled;

    /** 拉回分析记录 */
    private record RubberBandRecord(boolean flying, boolean digging, boolean placing, double speed) {
    }

    /** 拉回记录列表（旧实现用 {@code synchronizedList}，因规则/通知已全部回主线程，这里用普通列表） */
    private final List<RubberBandRecord> rubberBandHistory = new ArrayList<>();

    /** 最近一次发包时是否在挖掘 / 放置（网络线程写、主线程读，故 {@code volatile}） */
    private volatile boolean currentlyDigging;
    private volatile boolean currentlyPlacing;

    /** 视角抖动状态 */
    private int shakeTickCounter;
    private int nextShakeAt = 5;
    private Vec3 lastPosition = Vec3.ZERO;

    /** 随机源：{@code java.util.Random} 内部种子为原子量，主线程与规则线程共用安全（与旧实现一致） */
    private final Random random = new Random();

    public AntiKickBypassModule() {
        super(MODULE_ID, MESSAGE_MODULE, "combat",
            "防踢六合一：伪装排队防挂机");
    }

    /**
     * 图标字形（Material Symbols 的 {@code shield}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE9E0";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：战斗分类第三位（杀戮光环 → 飞行绕过 → 发包防踢 → 发包秒破） */
    @Override
    public int order() {
        return 30;
    }

    /**
     * 默认启用（用户 2026-09-18 决策：旧项目四个战术模块均默认关闭，本项目要求「发包防踢」默认打开）。
     *
     * <p>玩家手动关闭后以状态文件里的记录为准——默认值只在首次登记时落盘一次
     * （见 {@code ModuleManager} 的默认启用落盘逻辑），不会把玩家的关闭覆盖回来。</p>
     */
    @Override
    public boolean enabledByDefault() {
        return true;
    }

    /** 设置载体（控制台页面读写） */
    public AntiKickBypassSettings settings() {
        return settings;
    }

    /** 当前动态限速倍率（只读，控制台概览显示用） */
    public double throttleFactor() {
        return throttleFactor;
    }

    /** 当前待发聊天条数（只读，控制台概览显示用） */
    public int pendingChatCount() {
        return chatQueue.size();
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

    // ── 生命周期 ──

    /**
     * 单人世界闸门：旧 {@code onActivate} 的第一段（原因文案照旧）。
     *
     * <p>旧实现只拦「在单人世界里点开启」——旧项目本模块默认关闭，玩家只能在世界里开启，所以拦得住。
     * 本项目「发包防踢」默认开启（见 {@link #enabledByDefault()}），装配期在主菜单就恢复启用，
     * 那时没有世界、这道判据必然通过，模块就这样开着进了单人世界。因此改由框架在两个时机统一执行，
     * 见 {@link Module#environmentRefusal()}。</p>
     */
    @Override
    public String environmentRefusal() {
        return GameProbe.isSingleplayer() ? "§c单人世界无需防踢" : null;
    }

    /** 旧 {@code onActivate}：清会话状态 → 读协调器现态 → 注册发包闸门（单人世界闸门已交给框架） */
    @Override
    protected void onEnable() {
        resetSessionState();

        // 模块可能在检测完成后才开启，启动时必须立即读取协调器现态，不能只等一次性事件。
        throttleFactor = TacticalCoordinator.hasAdvancedAntiCheat() ? HIGH_RISK_THROTTLE_FACTOR : 1.0;
        lastNetworkDelayEnabled = settings.enableNetworkDelay;

        // 唯一参与发包链路的入口：规则在网络线程被同步调用（见类注释的线程模型）
        SendGate.register(MODULE_ID, this::onSendRule);
        TacticalCoordinator.addListener(this);
    }

    /** 旧 {@code onDeactivate}：注销闸门与协调器回调，并清空待发延迟队列（旧实现取消任务 + clear） */
    @Override
    protected void onDisable() {
        SendGate.unregister(MODULE_ID);
        TacticalCoordinator.removeListener(this);
        SendDelayService.clear();
    }

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(
            ClientEventType.TICK,
            ClientEventType.JOIN_SERVER,
            ClientEventType.DISCONNECT,
            ClientEventType.CLIENT_CHAT
        );
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null || !isEnabled()) return;
        switch (event.type()) {
            case JOIN_SERVER -> {
                // 旧项目顺序：协调器（优先级 −1000）先发 SessionResetEvent，模块随后才处理进服
                resetSessionState();
                onGameJoined();
            }
            case DISCONNECT -> {
                resetSessionState();
                onGameLeft();
            }
            case CLIENT_CHAT -> onClientChat(event.payload());
            default -> {
                // TICK 由 ModuleManager 统一走 onTick，其余类型未订阅
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  生命周期：进服登记会话起点；会话重置清私有状态（不碰 sessionStartAt）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void onGameJoined() {
        sessionStartAt = System.currentTimeMillis();
        // 会话级伪装统计独立归零（不依赖会话重置的调用顺序，避免脏计数）
        sessionBrandSpoofs.set(0);
        sessionChannelBlocks.set(0);
        announceDisguiseReady();
    }

    /** 进服伪装自检：单条多行块，播报四个伪装组件的实际开关状态（每会话一次） */
    private void announceDisguiseReady() {
        notify(
            "进服伪装自检：" +
                "\n§8├─ §f客户端名 §8▸ " + (settings.fakeBrand ? highlightText("vanilla") : "§c未伪装") +
                "\n§8├─ §fMod通信 §8▸ " + (settings.blockModChannels ? highlightText("拦截中") : "§c未拦截") +
                "\n§8├─ §f假潜行 §8▸ " + (settings.blockFakeSneak ? highlightText("拦截中") : "§c未拦截") +
                "\n§8└─ §f假疾跑 §8▸ " + (settings.blockFakeSprint ? highlightText("拦截中") : "§c未拦截")
        );
    }

    private void onGameLeft() {
        // 用进服时刻算存活时长，不看任何会被重置的计时器
        long sessionDuration = System.currentTimeMillis() - sessionStartAt;
        if (sessionStartAt > 0 && sessionDuration < SESSION_SHORT_MS && settings.enableAnalysis) {
            notifyError("§c存活不到 1 分钟，可能被踢了");
        }
        // 离服汇总：本会话伪装层实际拦截量。被踢/秒退时玩家最需要这个证据
        if (sessionBrandSpoofs.get() > 0 || sessionChannelBlocks.get() > 0) {
            notify(
                "本会话伪装统计：" +
                    "\n§8├─ §f品牌替换 §8▸ " + highlightNumber(String.valueOf(sessionBrandSpoofs.get())) + " 次" +
                    "\n§8└─ §fMod频道拦截 §8▸ " + highlightNumber(String.valueOf(sessionChannelBlocks.get())) + " 个"
            );
        }
        sessionStartAt = 0L;
        sessionBrandSpoofs.set(0);
        sessionChannelBlocks.set(0);
    }

    /** 会话重置（旧 {@code SessionResetEvent} → {@code resetSessionState}）：清本模块全部私有状态 */
    private void resetSessionState() {
        chatQueue.clear();
        lastChatSendTime = 0;
        antiAfkTicker = 0;
        digThisSecond.set(0);
        interactThisSecond.set(0);
        permittedDigTargets.clear();
        lastThrottleResetTime = System.currentTimeMillis();
        // 旧实现的原样缺陷：这里把动态收紧打回 1.0，且不再重新读取协调器现态
        throttleFactor = 1.0;
        lastRubberBandNotice = 0L;
        rubberBandHistory.clear();
        currentlyDigging = false;
        currentlyPlacing = false;
        sessionBrandSpoofs.set(0);
        sessionChannelBlocks.set(0);
        brandSpoofPending = false;
        shakeTickCounter = 0;
        nextShakeAt = SHAKE_INTERVAL_BASE + random.nextInt(SHAKE_INTERVAL_BOUND);
        lastPosition = mc.player != null ? mc.player.position() : Vec3.ZERO;
        // 旧实现同步清空延迟队列：不让旧队列以后突然补发
        SendDelayService.clear();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  Tick 处理（主线程）- 补发品牌 / 快照 / 限速窗口 / 聊天排队 / 防挂机 / 视角抖动
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @Override
    public void onTick(Minecraft client) {
        if (client == null) return;

        syncNetworkDelayToggle();
        // 品牌补发不依赖世界：品牌包在进服的配置阶段发出，此时可能还没有玩家实体
        flushBrandSpoof();

        if (client.player == null || client.level == null) return;

        refreshFakeSneakSnapshot(client);
        resetThrottleWindow();
        flushChatQueue();
        handleAntiAfk(client);
        if (settings.enableViewShake) handleViewShake(client);
    }

    /** 刷新假潜行判据快照（步进 1：水平速度 / 脚下冰面 / 是否疾跑），供网络线程只读 */
    private void refreshFakeSneakSnapshot(Minecraft client) {
        fakeSneakSpeed = client.player.getDeltaMovement().horizontalDistance();
        fakeSneakOnIce = client.level.getBlockState(client.player.blockPosition().below())
            .getBlock() instanceof IceBlock;
        fakeSneakSprinting = client.player.isSprinting();
    }

    /** 限速计数窗口：满 1 秒清零（旧实现在发包回调里顺带重置，阈值与窗口一字未改） */
    private void resetThrottleWindow() {
        long now = System.currentTimeMillis();
        if (now - lastThrottleResetTime < THROTTLE_WINDOW_MS) return;
        digThisSecond.set(0);
        interactThisSecond.set(0);
        lastThrottleResetTime = now;
    }

    /** 网络延迟开关切换：关掉时清空待发队列（等价旧 {@code updateDelayWorker(false)}） */
    private void syncNetworkDelayToggle() {
        boolean enabled = settings.enableNetworkDelay;
        if (enabled == lastNetworkDelayEnabled) return;
        lastNetworkDelayEnabled = enabled;
        if (!enabled) SendDelayService.clear();
    }

    /**
     * 补发被拦下的品牌包。
     *
     * <p>旧实现用 {@code event.sendSilently(...)} 在拦截点同步补发；本项目规则跑在未知线程且
     * 禁止调用游戏 API，因此改由主线程下一句 tick 走语义直发——内容与目标一致，仅晚一句 tick。</p>
     */
    private void flushBrandSpoof() {
        if (!brandSpoofPending) return;
        brandSpoofPending = false;
        ClientPacketSender.sendBrand(BRAND_VALUE);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  发包规则（网络线程被同步调用）- 伪装 + 聊天 + 限速 + 网络延迟
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 唯一参与发包链路的判据，顺序逐字照旧 {@code onPacketSend}：
     * ① 伪装客户端 → ② 摘假潜行/假疾跑 → ③ 聊天排队 → ④ 限制发包 → ⑤ 网络延迟。
     */
    private SendDecision onSendRule(SendView view) {
        if (view == null) return SendDecision.pass();

        // ① 伪装客户端（品牌伪装与 Mod 频道拦截）
        if (view.kind() == SendView.Kind.CUSTOM_PAYLOAD) {
            SendDecision payloadDecision = decideCustomPayload(view.detail());
            if (payloadDecision != null) return payloadDecision;
        }

        // ② 摘掉假潜行 / 假疾跑标记（不取消整包，只改写输入标志位）
        if (view.kind() == SendView.Kind.PLAYER_INPUT) {
            SendDecision inputDecision = decidePlayerInput(view);
            if (inputDecision != null) return inputDecision;
        }

        // ③ 聊天排队（重发走核心绕行通道，天然不会再入队，故不需要旧实现的转发标记）
        if (view.kind() == SendView.Kind.CHAT && settings.enableChatQueue) {
            return SendDecision.cancel();
        }

        // ④ 限制发包
        SendDecision throttleDecision = decideThrottle(view);
        if (throttleDecision != null) return throttleDecision;

        // ⑤ 网络延迟：随机毫秒由模块算好，保序与到点重发由核心负责
        if (settings.enableNetworkDelay && shouldDelay(view.kind())) {
            return SendDecision.delay(nextRandomDelay());
        }
        return SendDecision.pass();
    }

    /**
     * 自定义负载处置：品牌伪装 → Mod 频道拦截。
     *
     * <p>旧实现在品牌分支里<b>一律 return</b>（连限速与延迟都不走），这里保持同样的提前返回语义。
     *
     * @return 处置；{@code null} 表示继续走后续判据
     */
    private SendDecision decideCustomPayload(String channel) {
        if (channel == null || channel.isEmpty()) return null;

        if (settings.fakeBrand && BRAND_CHANNEL.equals(channel)) {
            sessionBrandSpoofs.incrementAndGet();
            brandSpoofPending = true;
            return SendDecision.cancel();
        }

        // 拦截 mod 频道：非 minecraft 命名空间直接暴露 Mod 列表；minecraft:register /
        // unregister 的正文就是 mod 频道列表，一并拦掉，否则伪造客户端形同虚设
        if (settings.blockModChannels && isModChannel(channel)) {
            sessionChannelBlocks.incrementAndGet();
            return SendDecision.cancel();
        }
        return null;
    }

    /** 是否是 Mod 自建频道（旧 {@code !namespace.equals("minecraft") || id 为 register/unregister}） */
    private static boolean isModChannel(String channel) {
        int separator = channel.indexOf(':');
        String namespace = separator < 0 ? "minecraft" : channel.substring(0, separator);
        return !"minecraft".equals(namespace)
            || REGISTER_CHANNEL.equals(channel)
            || UNREGISTER_CHANNEL.equals(channel);
    }

    /**
     * 玩家输入包处置：摘掉 Tweakeroo 的假潜行 / 假疾跑标记。
     *
     * @return {@link SendDecision#replaceInput(int)} 或 {@code null}（无需改写，继续走后续判据）
     */
    private SendDecision decidePlayerInput(SendView view) {
        int flags = view.inputFlags();
        int updated = flags;

        // 假潜行：shift 标记与移动速度矛盾（潜行应慢，高速说明假潜行）。
        // 不能取消整包——Input 包携带全部按键，改写包体只摘掉 shift 标记
        if (settings.blockFakeSneak && view.hasInput(SendView.INPUT_SHIFT)
            && fakeSneakSpeed > FAKE_SNEAK_SPEED_THRESHOLD && !fakeSneakOnIce && !fakeSneakSprinting) {
            updated &= ~SendView.INPUT_SHIFT;
        }

        // 假疾跑：sprint 标记与移动方向矛盾。sprint=true 却无前进输入或正在后退，即假疾跑特征
        if (settings.blockFakeSprint && view.hasInput(SendView.INPUT_SPRINT)
            && (!view.hasInput(SendView.INPUT_FORWARD) || view.hasInput(SendView.INPUT_BACKWARD))) {
            updated &= ~SendView.INPUT_SPRINT;
        }

        return updated == flags ? null : SendDecision.replaceInput(updated);
    }

    /**
     * 限制发包：挖掘 / 放置限速。
     *
     * <p>服务器卡顿或拉回冷却期（只读协调器）直接丢弃两类包，避免顶风作案；
     * START 之外的 STOP / ABORT 是已开始动作的收尾，丢掉会让服务端挖掘槽卡住，一律放行。</p>
     *
     * @return {@link SendDecision#cancel()} 或 {@code null}（放行，继续走后续判据）
     */
    private SendDecision decideThrottle(SendView view) {
        boolean stressed = TacticalCoordinator.isServerLagging() || TacticalCoordinator.isRubberBandCooldown();

        if (settings.limitDigging && view.kind() == SendView.Kind.PLAYER_ACTION) {
            String action = view.detail();
            BlockPos pos = view.pos();
            if ("START_DESTROY_BLOCK".equals(action)) {
                currentlyDigging = true;
                // 同一目标重复 START 不重复占额度；服务端仍可收到并自行裁决
                if (pos != null && permittedDigTargets.contains(pos)) return null;

                if (stressed || digThisSecond.get() >= throttleLimit(settings.maxDigPerSecond)) {
                    return SendDecision.cancel();
                }
                if (pos != null) {
                    permittedDigTargets.add(pos.immutable());
                    digThisSecond.incrementAndGet();
                }
                return null;
            }
            if ("STOP_DESTROY_BLOCK".equals(action) || "ABORT_DESTROY_BLOCK".equals(action)) {
                // STOP/ABORT 是已开始动作的收尾，丢掉它会让服务端挖掘槽卡住。
                // 即便 START 发生在模块开启前，也宁可放行一个安全收尾包。
                if (pos != null) permittedDigTargets.remove(pos);
                currentlyDigging = true;
            }
        }

        if (settings.limitInteract
            && (view.kind() == SendView.Kind.USE_ITEM_ON || view.kind() == SendView.Kind.USE_ITEM)) {
            currentlyPlacing = true;
            if (stressed || interactThisSecond.get() >= throttleLimit(settings.maxInteractPerSecond)) {
                return SendDecision.cancel();
            }
            interactThisSecond.incrementAndGet();
        }
        return null;
    }

    /** 动态限速：高风险反作弊时按 {@code throttleFactor} 收紧阈值（运行时打折不改用户设置） */
    private int throttleLimit(int configured) {
        return (int) Math.max(MIN_THROTTLE_LIMIT, configured * throttleFactor);
    }

    /**
     * 是否进延迟队列。
     *
     * <p>逐字照旧 {@code shouldDelay:795-801}：只豁免 KeepAlive / AcceptTeleportation / MovePlayer /
     * PlayerAction（移动包不进队列，避免与飞行注入互相抖动；方块动作的服务端进度依赖真实 tick 间隔）。
     * <b>载具包不在豁免名单</b>——旧项目原样如此，保留不修（见类注释「保留缺陷」）。</p>
     */
    private static boolean shouldDelay(SendView.Kind kind) {
        return switch (kind) {
            case KEEP_ALIVE, ACCEPT_TELEPORT, MOVE_PLAYER, PLAYER_ACTION -> false;
            default -> true;
        };
    }

    /** 延迟毫秒：在 [最小延迟, 最大延迟] 内随机（下限上限颠倒时自动交换，逐字照旧） */
    private long nextRandomDelay() {
        int lower = Math.min(settings.minDelay, settings.maxDelay);
        int upper = Math.max(settings.minDelay, settings.maxDelay);
        return lower + random.nextInt(upper - lower + 1);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  聊天排队（CLIENT_CHAT 入队 → onTick 按间隔重发）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 被闸门拦下的聊天原文（主线程派发）：入队等待按间隔重发 */
    private void onClientChat(String message) {
        if (!settings.enableChatQueue || message == null) return;
        chatQueue.offer(message);
    }

    private void flushChatQueue() {
        if (!settings.enableChatQueue || chatQueue.isEmpty()) return;
        long now = System.currentTimeMillis();
        if (now - lastChatSendTime < settings.chatInterval) return;

        String message = chatQueue.poll();
        if (message == null) return;
        // 直发绕行闸门：产出的聊天包不会再次被判据拦下，故旧实现的转发标记不再需要
        ClientPacketSender.sendChat(message);
        lastChatSendTime = now;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  防挂机（每 5 秒一个微小转身包，刷新服务端活跃时间戳）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** RecipeBook 设置包不刷新活跃时间戳，任何移动/转身才会，因此这里发只含旋转的位置包 */
    private void handleAntiAfk(Minecraft client) {
        if (!settings.antiAfk) return;
        if (++antiAfkTicker < ANTI_AFK_PERIOD_TICKS) return;
        antiAfkTicker = 0;

        float yaw = client.player.getYRot() + (random.nextFloat() - 0.5f);
        ClientPacketSender.sendMoveRotation(yaw, client.player.getXRot(),
            client.player.onGround(), client.player.horizontalCollision);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  视角抖动（移动时随机小幅转身，模拟手抖）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void handleViewShake(Minecraft client) {
        Vec3 currentPos = client.player.position();
        boolean isMoving = currentPos.distanceTo(lastPosition) > SHAKE_MOVING_EPSILON;
        lastPosition = currentPos;

        if (!isMoving) return;

        if (++shakeTickCounter < nextShakeAt) return;
        shakeTickCounter = 0;
        nextShakeAt = SHAKE_INTERVAL_BASE + random.nextInt(SHAKE_INTERVAL_BOUND);

        float intensity = (float) settings.shakeIntensity;
        float deltaYaw = (random.nextFloat() - 0.5f) * 2 * intensity;
        float deltaPitch = (random.nextFloat() - 0.5f) * 2 * intensity;

        float newYaw = client.player.getYRot() + deltaYaw;
        float newPitch = Math.max(-SHAKE_PITCH_LIMIT,
            Math.min(SHAKE_PITCH_LIMIT, client.player.getXRot() + deltaPitch));

        ClientPacketSender.sendMoveRotation(newYaw, newPitch,
            client.player.onGround(), client.player.horizontalCollision);
        client.player.setYRot(newYaw);
        client.player.setXRot(newPitch);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  拉回分析（协调器在主线程回调，可直接读玩家状态）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 一次拉回登记（旧 {@code onRubberBand}）。
     *
     * <p>26.1.2 原版 {@code ClientPacketListener.handleMovePlayer} 已自动回发
     * AcceptTeleportation + 1 个 PosRot 确认包，这里只记分析数据，不再发包。</p>
     */
    @Override
    public void onRubberBand() {
        if (!isEnabled() || mc.player == null) return;

        if (settings.enableAnalysis) {
            boolean flying = !mc.player.onGround() && mc.player.getDeltaMovement().y > RUBBER_BAND_FLYING_VY;
            double speed = mc.player.getDeltaMovement().horizontalDistance();

            rubberBandHistory.add(new RubberBandRecord(flying, currentlyDigging, currentlyPlacing, speed));
            currentlyDigging = false;
            currentlyPlacing = false;

            if (rubberBandHistory.size() >= settings.analysisThreshold) {
                analyzeRubberBands();
                rubberBandHistory.clear();
            }
        }

        // 播报节流：拉回可能连发，5 秒只报一次
        long now = System.currentTimeMillis();
        if (now - lastRubberBandNotice >= RUBBER_BAND_NOTICE_INTERVAL_MS) {
            lastRubberBandNotice = now;
            notify("§e⚠ 被拉回 §8▸ 冷却与统计已由协调器登记");
        }
    }

    /**
     * 反作弊检测联动：协调器确认高风险反作弊时动态收紧发包限速（阈值打五折），
     * 规避高频挖掘/放置包被判定为自动化而踢出。运行时打折不改用户设置。
     */
    @Override
    public void onAntiCheat(String antiCheatName) {
        if (!isEnabled() || !TacticalCoordinator.hasAdvancedAntiCheat()) return;

        if (throttleFactor > HIGH_RISK_THROTTLE_FACTOR) {
            throttleFactor = HIGH_RISK_THROTTLE_FACTOR;
            notify("检测到 " + antiCheatName + "，发包限速收紧到 50%");
        }
    }

    /** 拉回分析报告：单条多行块，正文「标签 §8▸ 值」对齐，禁止逐条刷屏 */
    private void analyzeRubberBands() {
        int total = rubberBandHistory.size();
        int flyingCount = 0;
        int diggingCount = 0;
        int placingCount = 0;
        int speedCount = 0;

        for (RubberBandRecord record : rubberBandHistory) {
            if (record.flying()) flyingCount++;
            if (record.digging()) diggingCount++;
            if (record.placing()) placingCount++;
            if (record.speed() > RUBBER_BAND_SPEED_THRESHOLD) speedCount++;
        }

        StringBuilder sb = new StringBuilder("§e§l拉回分析报告 §8（").append(total).append(" 次）§r\n");
        sb.append("§8├─ §f飞行时被拉 §8▸ ").append(highlightNumber(String.valueOf(flyingCount)))
            .append(" 次 §8（").append(percent(flyingCount, total)).append("%）\n");
        sb.append("§8├─ §f挖掘时被拉 §8▸ ").append(highlightNumber(String.valueOf(diggingCount)))
            .append(" 次 §8（").append(percent(diggingCount, total)).append("%）\n");
        sb.append("§8├─ §f放置时被拉 §8▸ ").append(highlightNumber(String.valueOf(placingCount)))
            .append(" 次 §8（").append(percent(placingCount, total)).append("%）\n");
        sb.append("§8└─ §f高速时被拉 §8▸ ").append(highlightNumber(String.valueOf(speedCount)))
            .append(" 次 §8（").append(percent(speedCount, total)).append("%）");

        // 建议行独立于统计树之外（用 ▸ 而非 └─），避免出现双 └─ 破坏树形结构
        if (flyingCount > total * RECOMMEND_FLYING_RATIO) {
            sb.append("\n§8▸ §a建议 §8▸ 把「飞行绕过」切换到安全滑翔或原版连跳");
        } else if (diggingCount > total * RECOMMEND_DIGGING_RATIO) {
            sb.append("\n§8▸ §a建议 §8▸ 降低「每秒最多挖几个」的值");
        } else if (placingCount > total * RECOMMEND_PLACING_RATIO) {
            sb.append("\n§8▸ §a建议 §8▸ 降低「每秒最多放几个」的值");
        }

        notify(sb.toString());
    }

    private static int percent(int part, int total) {
        return total == 0 ? 0 : (int) ((double) part / total * 100);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  播报（旧基类的颜色码包装逐字保留）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 普通消息（旧基类：前缀 + {@code §f} + 正文） */
    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, "§f" + message);
    }

    /** 警告消息（旧基类：{@code §e§l} + 正文） */
    private void warning(String message) {
        notify("§e§l" + message);
    }

    /** 错误消息（旧基类：{@code §6§l} + 正文） */
    private void notifyError(String message) {
        notify("§6§l" + message);
    }

    /** 文本高亮（绿色粗体）——旧基类 {@code highlightText} 逐字 */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /** 数值/阈值高亮（黄色粗体）——旧基类 {@code highlightNumber} 逐字 */
    private static String highlightNumber(String text) {
        return "§e§l" + text + "§r§f§l";
    }

    // ── 界面 ──

    /** 配置页 = 薄壳模块页 {@link AntiKickBypassPage} + 整屏控制台（概览 + 旧 6 个设置组） */
    @Override
    public ModulePage page() {
        return new AntiKickBypassPage(this);
    }
}
