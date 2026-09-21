package com.yiyiaddon.feature.tactical;

import com.google.gson.JsonObject;
import com.mojang.brigadier.tree.CommandNode;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.core.resourcepack.ResourcePackGate;
import com.yiyiaddon.feature.tactical.config.ServerDetectorSettings;
import com.yiyiaddon.feature.tactical.core.ServerFingerprints;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.ServerDetectorPage;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;

import java.awt.Desktop;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器检测模块（L0 观测层，被动只读）。
 *
 * <p><b>旧项目对应物</b>：{@code tactical/ServerDetector.java}（497 行）。识别算法、指纹匹配顺序、
 * 调度时长、播报文案（含颜色码）、设置项与分组、内部状态字段与重置时机逐条对照搬运。</p>
 *
 * <p><b>职责边界（与旧实现一致）</b>：只采集「观测事实」——服务器核心 / 反作弊指纹 / 插件频道；
 * 检测结果通过 {@link TacticalCoordinator#reportDetection} 上报（带会话代次校验，跨服迟到的结果
 * 由协调器丢弃）；不写任何执行状态（冷却/模式/抑制一律归协调器），TPS 采样与拉回统计由协调器
 * 常驻处理，本模块需要结论时只读 {@link TacticalCoordinator#getRubberBandTotal()}。</p>
 *
 * <p>识别思路是多层指纹叠加，可信度由低到高（旧注释逐字保留）：</p>
 * <ol>
 *     <li>brand / version 字符串 —— 最容易被服务端改掉，只作为线索；</li>
 *     <li>插件消息频道 —— 反作弊主动开的校验频道，命中基本可确诊；</li>
 *     <li>指令树命名空间 —— 插件注册的实际结果，伪造成本高，是主要依据；</li>
 *     <li>拉回频率 —— 只能说明反作弊存在且激进，无法定型号。</li>
 * </ol>
 *
 * <p><b>框架适配清单（旧 → 新，本项目没有、旧框架白送的能力）</b>：</p>
 * <ul>
 *     <li><b>进服 / 离服</b>：旧 {@code GameJoinedEvent} / {@code GameLeftEvent}
 *         → {@link ClientEventType#JOIN_SERVER} / {@link ClientEventType#DISCONNECT}；</li>
 *     <li><b>每刻</b>：旧 {@code TickEvent.Post} → {@link #onTick(Minecraft)}
 *         （运行时只在启用时调度，等价旧实现的开头 {@code isActive()} 判定）；</li>
 *     <li><b>收包</b>：旧 {@code PacketEvent.Receive} 读包对象 →
 *         ① 插件频道改订阅 {@link ClientEventType#SERVER_CHANNEL}（载荷即频道 id，
 *         原版 brand/register/unregister 已由核心剔除，旧实现同样剔除）；
 *         ② 指令树包只能拿到<b>包类名</b>（{@link ClientEventType#PACKET_RECEIVE} 载荷），
 *         因此按 {@link ClientboundCommandsPacket}{@code .class.getName()} 等值判定，
 *         指令树本身在侦测时直读 {@code connection.getCommands()}，不需要包对象；</li>
 *     <li><b>拉回证据回调</b>：旧总线的 {@code RubberBandDetectedEvent}
 *         → {@link TacticalCoordinator#addListener}（启用时注册、关闭时注销，语义等价）；</li>
 *     <li><b>会话代次冻结时机</b>：旧协调器用 {@code @EventHandler(priority = -1000)} 抢在模块前
 *         推进代次；本项目订阅顺序由启用时机决定（不保证），故把进服后的首次调度推迟一帧，
 *         等本次派发结束后再冻结 token，否则会冻到上一会话的代次并被 {@link #runScheduledDetection}
 *         立刻作废；</li>
 *     <li><b>单人世界不可开启</b>：旧 {@code chatFeedback=false; toggle(); chatFeedback=true;}
 *         只写在 {@code onActivate} 里，本项目本模块默认开启、装配期（主菜单）就恢复启用，
 *         那道闸门拦不住 → 改为 {@link Module#environmentRefusal()} 交给框架，启用与进世界两个时机都拦；</li>
 *     <li><b>资源包推送接管</b>：旧 {@code handleResourcePackPushFromVanilla(packet, sendPacket)}
 *         由旧 Mixin 调用 → 本类的
 *         {@link #handleResourcePackPush(UUID, String, String)}（模块拿不到包对象，改由框架层
 *         把 id / url / hash 三个字段传进来），响应包走
 *         {@link ClientPacketSender#sendResourcePackResponse}；</li>
 *     <li><b>资源包下载与提示</b>：旧 {@code ResourcePackDownloader}
 *         → {@link ResourcePackCache}（同源搬运的唯一合法下载器）。</li>
 * </ul>
 *
 * <p><b>播报</b>：旧基类 {@code notify / warning / notifyError} 的颜色码包装在本类内逐字保留
 * （{@code §f} / {@code §e§l} / {@code §6§l}），正文经 {@link ClientChat#send} 输出。</p>
 *
 * @author yiyijia
 */
public final class ServerDetectorModule extends Module {

    /** 模块 ID（状态文件键 / 快捷键键名后缀） */
    public static final String MODULE_ID = "serverdetect";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = "服务器检测";

    // ── 逐字常量（旧 ServerDetector 源码字面量） ──

    /** 插件频道登记上限（旧 {@code seenChannels.size() < 64}）：超限不再登记，避免恶意刷频道吃内存 */
    private static final int SEEN_CHANNEL_LIMIT = 64;

    /** 收到频道 / 指令树包后的复核延迟（毫秒，旧 {@code scheduleDetection(100L, 1)}） */
    private static final long EVIDENCE_RECHECK_DELAY_MS = 100L;

    /** 首轮之后的多轮复核间隔（毫秒，旧 {@code now + 5000L}） */
    private static final long PASS_INTERVAL_MS = 5000L;

    /** 每轮进服发起的侦测轮数（旧 {@code scheduleDetection(..., 3)}） */
    private static final int DETECTION_PASSES = 3;

    /** 拉回证据触发复核的累计次数门槛（旧 {@code getRubberBandTotal() >= 3}） */
    private static final int RUBBER_BAND_RECHECK_THRESHOLD = 3;

    /** 拉回证据判定「存在移动校验」的累计次数门槛（旧 {@code >= 3}，与协调器的补足结论同源） */
    private static final int RUBBER_BAND_EVIDENCE_THRESHOLD = 3;

    /** 秒 → 毫秒换算（旧 {@code detectDelay.get() * 1000L}） */
    private static final long MILLIS_PER_SECOND = 1000L;

    // ── 结论哨兵（逐字，参与播报与协调器判定，禁止改写） ──

    private static final String NOT_CHECKED = "未检测";
    private static final String UNKNOWN = "未知";
    private static final String NOT_FOUND = "未发现";

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（控制台页面读写） */
    private final ServerDetectorSettings settings = new ServerDetectorSettings();

    /** 本次连接收到的插件消息频道，用于反作弊频道指纹 */
    private final Set<String> seenChannels = ConcurrentHashMap.newKeySet();

    // ── 多轮侦测调度：主线程 tick 驱动，避免睡眠线程跨会话迟到 ──

    /** 调度时冻结的会话代次（与协调器当前代次不一致即作废本轮） */
    private long detectionSession = -1L;

    /** 下一轮侦测的执行时刻（毫秒；{@code Long.MAX_VALUE} = 无待执行轮次） */
    private long nextDetectionAt = Long.MAX_VALUE;

    /** 剩余侦测轮数 */
    private int detectionPassesRemaining;

    /** 上一次播报过的核心结论（多轮复核只在结论变化时播报，避免刷屏） */
    private String lastAnnouncedCore;

    /** 上一次播报过的反作弊结论 */
    private String lastAnnouncedAntiCheat;

    /**
     * 协调器通知回调：登记到一次拉回后立刻安排复核。
     *
     * <p>旧实现订阅总线上的 {@code RubberBandDetectedEvent}；本项目在启用时注册、关闭时注销，
     * 语义等价（模块关闭时不需要拉回复核）。</p>
     */
    private final TacticalCoordinator.Listener coordinatorListener = new TacticalCoordinator.Listener() {
        @Override
        public void onRubberBand() {
            // 行为证据变化后立即安排一次复核，让报告与协调器现态保持一致
            if (isEnabled() && TacticalCoordinator.getRubberBandTotal() >= RUBBER_BAND_RECHECK_THRESHOLD) {
                scheduleDetection(0L, 1);
            }
        }
    };

    public ServerDetectorModule() {
        super(MODULE_ID, MESSAGE_MODULE, "assist",
            "识别服务器核心，自动取资源包");
    }

    /**
     * 图标字形（Material Symbols 的 {@code dns}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE875";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：辅助分类最后一位（ID识别 → ID配置 → 服务器检测） */
    @Override
    public int order() {
        return 20;
    }

    /**
     * 默认启用（用户 2026-09-18 决策：旧项目四个战术模块均默认关闭，本项目要求「服务器检测」默认打开）。
     *
     * <p>玩家手动关闭后以状态文件里的记录为准——默认值只在首次登记时落盘一次
     * （见 {@code ModuleManager} 的默认启用落盘逻辑），不会把玩家的关闭覆盖回来。</p>
     */
    @Override
    public boolean enabledByDefault() {
        return true;
    }

    /** 设置载体（控制台页面读写） */
    public ServerDetectorSettings settings() {
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

    // ── 生命周期 ──

    /**
     * 单人世界闸门：旧 {@code onActivate} 的第一段（原因文案照旧）。
     *
     * <p>旧实现只拦「在单人世界里点开启」——旧项目本模块默认关闭，玩家只能在世界里开启，所以拦得住。
     * 本项目「服务器检测」默认开启（见 {@link #enabledByDefault()}），装配期在主菜单就恢复启用，
     * 那时没有世界、这道判据必然通过，模块就这样开着进了单人世界。因此改由框架在两个时机统一执行，
     * 见 {@link Module#environmentRefusal()}。</p>
     */
    @Override
    public String environmentRefusal() {
        return GameProbe.isSingleplayer() ? "§c单人世界无需检测" : null;
    }

    /** 旧 {@code onActivate}：进服后手动触发首轮侦测（单人世界闸门已交给框架） */
    @Override
    protected void onEnable() {
        TacticalCoordinator.addListener(coordinatorListener);

        // 资源包策略接管：注册进框架闸门，由 Mixin 在原版处理前询问（三种模式见 handleResourcePackPush）
        ResourcePackGate.register(MODULE_ID, this::handleResourcePackPush);

        // 缓存目录就位（下载、重命名、原版缓存复制都要用它）
        ResourcePackCache.dir();

        // 如果是进服后才开启模块，手动触发检测（JOIN_SERVER 已经错过了）
        if (mc.player != null && mc.level != null && !mc.hasSingleplayerServer()) {
            seenChannels.clear();
            scheduleDetection(settings.detectDelay * MILLIS_PER_SECOND, DETECTION_PASSES);

            notify("已在服务器中，将在 " + settings.detectDelay + " 秒后开始侦测");
        }
    }

    /** 关闭：注销本模块的协调器回调与资源包接管，不动任何共享状态 */
    @Override
    protected void onDisable() {
        TacticalCoordinator.removeListener(coordinatorListener);
        ResourcePackGate.unregister(MODULE_ID);
    }

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(
            ClientEventType.JOIN_SERVER,
            ClientEventType.DISCONNECT,
            ClientEventType.PACKET_RECEIVE,
            ClientEventType.SERVER_CHANNEL
        );
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null || !isEnabled()) return;
        switch (event.type()) {
            case JOIN_SERVER -> onGameJoined();
            case DISCONNECT -> onGameLeft();
            case SERVER_CHANNEL -> onChannel(event.payload());
            case PACKET_RECEIVE -> onPacketReceive(event.payload());
            default -> {
                // 未订阅的事件类型不处理
            }
        }
    }

    @Override
    public void onTick(Minecraft client) {
        runScheduledDetection();
        // 资源包提示补发 / 重命名 / 原版缓存复制统一由 ResourcePackCache.tick 常驻处理
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  进服侦测
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 旧 {@code onGameJoined}：清空观测事实并安排 3 轮侦测 */
    private void onGameJoined() {
        seenChannels.clear();
        lastAnnouncedCore = null;
        lastAnnouncedAntiCheat = null;

        // 指令树与插件频道会分批下发：初次延迟后再复核两次，降低只扫一次造成的漏判。
        // 调度完全由主线程 tick 驱动，会话 token 不一致时直接作废。
        //
        // 推迟一帧的原因见类注释「会话代次冻结时机」：协调器的 JOIN_SERVER 处理与本次派发
        // 同批执行，等它结束后再冻结 token 才不会冻到上一会话。
        mc.execute(() -> scheduleDetection(settings.detectDelay * MILLIS_PER_SECOND, DETECTION_PASSES));
    }

    /** 旧 {@code onGameLeft}：清本模块私有状态；全局状态由协调器常驻自清 */
    private void onGameLeft() {
        seenChannels.clear();
        detectionSession = -1L;
        nextDetectionAt = Long.MAX_VALUE;
        detectionPassesRemaining = 0;
        lastAnnouncedCore = null;
        lastAnnouncedAntiCheat = null;
    }

    /**
     * 登记一个插件消息频道（旧 {@code onPacketReceive} 的 CustomPayload 分支）。
     *
     * <p>26.1.2 会把 register 的频道名丢进 DiscardedPayload（只留 id、正文被丢弃），因此 register
     * 里罗列的反作弊频道名无法从包对象恢复，这里只能捕获服务端直接推送数据的真实频道。
     * 协议级频道（brand/register/unregister）由核心在派发前剔除，与旧实现同一套判据。</p>
     */
    private void onChannel(String channel) {
        if (channel == null || channel.isEmpty()) return;
        if ("minecraft:brand".equals(channel)
            || "minecraft:register".equals(channel)
            || "minecraft:unregister".equals(channel)) {
            return;
        }
        if (seenChannels.size() < SEEN_CHANNEL_LIMIT && seenChannels.add(channel)) {
            mc.execute(() -> scheduleDetection(EVIDENCE_RECHECK_DELAY_MS, 1));
        }
    }

    /**
     * 收到指令树包时安排一次复核（旧 {@code onPacketReceive} 的 ClientboundCommandsPacket 分支）。
     *
     * <p>旧注释：Receive 事件发生在原版处理前，延迟到后续 tick 再读连接里的新指令树。
     * 本项目只能从事件载荷拿到包类名，指令树在侦测时直读连接，因此判等类名即可。</p>
     */
    private void onPacketReceive(String packetName) {
        if (!ClientboundCommandsPacket.class.getName().equals(packetName)) return;
        mc.execute(() -> scheduleDetection(EVIDENCE_RECHECK_DELAY_MS, 1));
    }

    /**
     * 执行侦测并上报协调器。
     *
     * <p>三个开关全关时也要上报一次「未检测」结论——协调器的会话状态需要与真实世界对齐，
     * 而不是永远停留在上一服务器的旧值。</p>
     *
     * @param token 调度时冻结的会话代次
     */
    private void performDetection(long token) {
        if (mc.player == null || mc.getConnection() == null) return;

        String core = settings.detectCore ? detectServerCore() : NOT_CHECKED;
        String antiCheat = settings.detectAntiCheat ? detectAntiCheatPlugin() : NOT_CHECKED;

        // 唯一合法上报通道：带会话代次校验，跨服迟到的结果在协调器内直接丢弃
        TacticalCoordinator.reportDetection(token, core, antiCheat);

        // 多轮复核只在结论变化时播报，避免相同报告重复刷屏。
        boolean changed = !Objects.equals(core, lastAnnouncedCore)
            || !Objects.equals(antiCheat, lastAnnouncedAntiCheat);
        if (settings.announceDetection && changed) {
            notify(buildDetectionReport(core, antiCheat));
        }
        lastAnnouncedCore = core;
        lastAnnouncedAntiCheat = antiCheat;
    }

    /** 安排若干次主线程侦测；后来的证据刷新只会把执行时间提前，不会重复创建线程。 */
    private void scheduleDetection(long delayMs, int passes) {
        detectionSession = TacticalCoordinator.currentSession();
        nextDetectionAt = Math.min(nextDetectionAt, System.currentTimeMillis() + Math.max(0L, delayMs));
        detectionPassesRemaining = Math.max(detectionPassesRemaining, passes);
    }

    /** Tick 驱动多轮侦测：首轮后每 5 秒复核，覆盖迟下发指令树/插件频道。 */
    private void runScheduledDetection() {
        if (!isEnabled() || detectionPassesRemaining <= 0 || mc.player == null || mc.getConnection() == null) return;
        if (detectionSession != TacticalCoordinator.currentSession()) {
            detectionPassesRemaining = 0;
            nextDetectionAt = Long.MAX_VALUE;
            return;
        }
        long now = System.currentTimeMillis();
        if (now < nextDetectionAt) return;

        performDetection(detectionSession);
        detectionPassesRemaining--;
        nextDetectionAt = detectionPassesRemaining > 0 ? now + PASS_INTERVAL_MS : Long.MAX_VALUE;
    }

    /**
     * 组装侦测报告（单条多行消息块）。
     *
     * <p>排版规范：正文统一「标签 §8▸ 值」，标签固定宽度对齐；
     * 风险等级用 §a✓ / §e⚠ / §c✗ 图标统一，反作弊命中红色高亮。</p>
     */
    private String buildDetectionReport(String core, String antiCheat) {
        StringBuilder sb = new StringBuilder();
        sb.append("§b§l━━ 服务端侦测报告 ━━§r\n");

        // 服务器核心：未知/未检测用灰色弱化，命中用金色高亮
        boolean coreUnknown = UNKNOWN.equals(core) || NOT_CHECKED.equals(core);
        sb.append("§7服务器核心 §8▸ ").append(coreUnknown ? "§8" + core : highlightServer(core)).append("\n");

        // 反作弊：未检测灰色 / 未发现绿色提示 / 命中红色高亮
        if (NOT_CHECKED.equals(antiCheat)) {
            sb.append("§7反作弊   §8▸ §8未检测");
        } else if (NOT_FOUND.equals(antiCheat)) {
            sb.append("§7反作弊   §8▸ ").append(highlightText("未发现指纹")).append(" §8（不等于没有）");
        } else {
            sb.append("§7反作弊   §8▸ §c§l").append(antiCheat);
        }

        // 风险等级：只在反作弊命中时播报，高风险标 ✗、中低风险标 ⚠
        if (!NOT_CHECKED.equals(antiCheat) && !NOT_FOUND.equals(antiCheat)) {
            sb.append("\n§7风险等级 §8▸ ");
            if (ServerFingerprints.isHighRisk(antiCheat)) {
                sb.append("§c§l✗ 高风险 §8（协调器已收紧绕过策略）");
            } else {
                sb.append("§e§l⚠ 中低风险");
            }
        }

        return sb.toString();
    }

    /**
     * 识别服务端核心。
     *
     * <p>brand 与 version 都可以被服务端随手改写，所以优先看指令树命名空间，
     * 拿不到结论再退回字符串匹配。三处都没线索时不硬猜，直接报未知。</p>
     */
    private String detectServerCore() {
        ClientPacketListener connection = mc.getConnection();
        if (connection == null) return UNKNOWN;

        // 第一层：指令树命名空间。插件指令会注册成「插件名:指令」，伪造成本高
        String fromCommands = matchCommandNamespaces(ServerFingerprints.CORE_COMMANDS);
        if (fromCommands != null && !fromCommands.isEmpty()) return fromCommands + "（指令树）";

        // 第二层：brand 字符串
        String brand = connection.serverBrand();
        String fromBrand = matchKeyword(brand, ServerFingerprints.CORES);
        if (fromBrand != null) return fromBrand;

        // 第三层：服务器列表里的 version 文本
        ServerData data = connection.getServerData();
        if (data != null && data.version != null) {
            String fromVersion = matchKeyword(data.version.getString(), ServerFingerprints.CORES);
            if (fromVersion != null) return fromVersion;
        }

        // brand 精确匹配 vanilla，避免 "vanilla+custom" 误判
        if ("vanilla".equals(brand)) {
            return "原版";
        }
        if (brand != null && brand.toLowerCase(Locale.ROOT).contains("vanilla")) {
            return "原版（已改 brand）";
        }
        return UNKNOWN;
    }

    /**
     * 识别反作弊。
     *
     * <p>插件频道命中优先级最高（反作弊主动开的校验通道），其次是指令树。
     * 两者都没有时看协调器的会话累计拉回次数，只能给出「存在且激进」程度的结论。</p>
     */
    private String detectAntiCheatPlugin() {
        Set<String> matches = new LinkedHashSet<>();

        // 第一层：插件消息频道
        for (String channel : seenChannels) {
            for (Map.Entry<String, String> e : ServerFingerprints.ANTICHEAT_CHANNELS.entrySet()) {
                if (channel.contains(e.getKey())) matches.add(e.getValue());
            }
        }

        // 频道是最强证据；命中多个时全部展示，不因 Set/包到达顺序丢掉第二个核心。
        if (!matches.isEmpty()) return String.join(" + ", matches) + "（插件频道）";

        // 第二层：指令树
        matches.addAll(matchAllCommandNamespaces(ServerFingerprints.ANTICHEAT_COMMANDS));
        if (!matches.isEmpty()) return String.join(" + ", matches) + "（指令树）";

        // 第三层：拉回频率（协调器常驻统计，不依赖本模块开关）。只说明有东西在校验移动，认不出型号
        if (TacticalCoordinator.getRubberBandTotal() >= RUBBER_BAND_EVIDENCE_THRESHOLD) {
            return "未知反作弊（拉回频繁，已确认存在移动校验）";
        }

        return NOT_FOUND;
    }

    /**
     * 在服务端下发的指令树里匹配指纹表。
     *
     * <p>同时看命名空间（{@code plugin:cmd} 的前半段）与根指令名本身，
     * 因为部分插件不带命名空间直接注册根指令。</p>
     */
    private String matchCommandNamespaces(Map<String, String> fingerprints) {
        Set<String> tokens = commandTokens();
        for (Map.Entry<String, String> e : fingerprints.entrySet()) {
            // minecraft 命名空间在核心表中是空哨兵，只表示原版命令；必须跳过，
            // 不能像旧实现一样提前 return 空字符串并漏掉后续 Paper/Purpur 节点。
            if (!e.getValue().isEmpty() && tokens.contains(e.getKey())) return e.getValue();
        }
        return null;
    }

    /** 返回指令树中命中的全部不重复指纹，顺序由指纹表而非服务端节点顺序决定。 */
    private Set<String> matchAllCommandNamespaces(Map<String, String> fingerprints) {
        Set<String> tokens = commandTokens();
        Set<String> result = new LinkedHashSet<>();
        for (Map.Entry<String, String> e : fingerprints.entrySet()) {
            if (!e.getValue().isEmpty() && tokens.contains(e.getKey())) result.add(e.getValue());
        }
        return result;
    }

    /** 把根节点名、命名空间和冒号后的真实指令名都归一化为可匹配 token。 */
    private Set<String> commandTokens() {
        ClientPacketListener connection = mc.getConnection();
        if (connection == null) return Set.of();

        var dispatcher = connection.getCommands();
        if (dispatcher == null) return Set.of();

        Set<String> tokens = new LinkedHashSet<>();
        for (CommandNode<?> node : dispatcher.getRoot().getChildren()) {
            String name = node.getName();
            if (name == null || name.isEmpty()) continue;

            String lower = name.toLowerCase(Locale.ROOT);
            tokens.add(lower);
            int separator = lower.indexOf(':');
            if (separator >= 0) {
                tokens.add(lower.substring(0, separator));
                if (separator + 1 < lower.length()) tokens.add(lower.substring(separator + 1));
            }
        }
        return tokens;
    }

    /** 在字符串里按指纹表顺序匹配关键词，命中即返回展示名。 */
    private String matchKeyword(String raw, Map<String, String> fingerprints) {
        if (raw == null || raw.isEmpty()) return null;
        String lower = raw.toLowerCase(Locale.ROOT);

        for (Map.Entry<String, String> e : fingerprints.entrySet()) {
            if (lower.contains(e.getKey())) return e.getValue();
        }
        return null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  资源包策略入口（旧 ResourcePackPushMixin 调用的唯一入口）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 资源包推送的处理策略入口。
     *
     * <p><b>框架适配</b>：旧签名是
     * {@code handleResourcePackPushFromVanilla(ClientboundResourcePackPushPacket, Consumer<Packet<?>>)}，
     * 由旧 Mixin 在 {@code handleResourcePackPush} 头部调用；本项目禁止模块持有包对象与自行组包，
     * 因此改由框架层把推送包的三个字段传进来，响应包走
     * {@link ClientPacketSender#sendResourcePackResponse}。</p>
     *
     * <p><b>调用约定（框架层必须照此接线）</b>：在
     * {@code mixin/client/ResourcePackPushMixin} 的 {@code HEAD} 注入点、原版处理之前调用
     * （模块实例经 {@link ModuleManager#byId(String)} 取 {@value #MODULE_ID}）；
     * 返回 {@code true} 时框架须取消原版处理，返回 {@code false} 时放行原版。</p>
     *
     * @param packId 服务器分配的包 ID（旧 {@code packet.id()}）
     * @param url    下载地址（旧 {@code packet.url()}）
     * @param hash   服务器给的 SHA-1（旧 {@code packet.hash()}）
     * @return true 表示已接管并应取消原版处理；false 表示放行原版（可能同时已委托共享下载器缓存）
     */
    public boolean handleResourcePackPush(UUID packId, String url, String hash) {
        if (!isEnabled()) return false;

        ResourcePackMode mode = settings.resourcePackMode;

        if (mode == ResourcePackMode.BYPASS) {
            // required 和 optional 都拦截：cancel 原版处理后立即回 ACCEPTED + SUCCESSFULLY_LOADED，
            // 让服务器认为客户端已应用资源包，跳过下载直接进服。
            ClientPacketSender.sendResourcePackResponse(packId, ServerboundResourcePackPacket.Action.ACCEPTED);
            ClientPacketSender.sendResourcePackResponse(packId, ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED);
            ResourcePackCache.asyncNotify("§a✓ 已拦截资源包 §8(暴力绕过)");
            return true;
        } else if (mode == ResourcePackMode.AUTO_DOWNLOAD) {
            // required 和 optional 都必须放行原版：cancel 原版处理会让服务器一直等待
            // 资源包处理完成，客户端卡在「加入服务器中」（发 ACCEPTED 也救不回来）。
            // 旁路白嫖：交给共享下载器把文件多存一份到 yiyiaddon_resourcepacks。
            ResourcePackCache.downloadAsync(packId, url, hash,
                settings.downloadRetries, settings.downloadTimeout * 1000, settings.resumeDownload);
            return false;
        }

        return false;
    }

    /**
     * 在系统文件管理器中打开资源包缓存目录（旧 {@code SystemFileOpener.openDirectory} 的等价实现）。
     *
     * <p>26.1.2 已移除 {@code net.minecraft.Util}，改用 AWT {@code Desktop}；在独立守护线程执行，
     * 避免阻塞渲染线程；不支持时回落到 Windows 的 {@code explorer.exe}。失败原因回调到主线程播报。</p>
     *
     * <p>由控制台「资源包劫持」页的 {@code §b查看下载资源包} 按钮触发（旧项目该按钮在模块面板上，
     * 承载位置按第 209 / 210 条移到控制台）。</p>
     */
    public void openResourcePackFolder() {
        File dir = ResourcePackCache.dir();
        Thread opener = new Thread(() -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(dir);
                } else {
                    new ProcessBuilder("explorer.exe", "/select," + dir.getAbsolutePath()).start();
                }
            } catch (Exception e) {
                mc.execute(() -> notifyError("打开资源库失败：" + e.getMessage()));
            }
        }, "yiyiaddon-OpenDir");
        opener.setDaemon(true);
        opener.start();
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

    /** 服务器/核心高亮（金色粗体）——旧基类 {@code highlightServer} 逐字 */
    private static String highlightServer(String text) {
        return "§6§l" + text + "§r§f§l";
    }

    /** 文本高亮（绿色粗体）——旧基类 {@code highlightText} 逐字 */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    // ── 界面 ──

    /** 配置页 = 薄壳模块页 {@link ServerDetectorPage} + 整屏控制台（3 分页：概览 / 底裤侦测 / 资源包劫持） */
    @Override
    public ModulePage page() {
        return new ServerDetectorPage(this);
    }

    /** 资源包处理模式（旧模块内嵌枚举逐字移植）。 */
    public enum ResourcePackMode {
        BYPASS("暴力绕过"),
        AUTO_DOWNLOAD("自动白嫖"),
        VANILLA("原版处理");

        /** 中文显示名（同时用于配置存档） */
        public final String displayName;

        ResourcePackMode(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
