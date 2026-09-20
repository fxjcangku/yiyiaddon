package com.yiyiaddon.service.resourcepack;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewResourceScanner;
import com.yiyiaddon.feature.stardew.selector.StardewPreview;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.model.resource.ResourcePhase;
import com.yiyiaddon.model.resource.ResourceScanResult;
import com.yiyiaddon.model.resource.ResourceSource;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.resource.ItemModelDispatchIndex;
import com.yiyiaddon.ui.render.TextureImageCache;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 服务器资源生命周期服务（常驻，独立于任何业务模块开关）。
 *
 * <p><b>职责：</b>把「当前服务器 → 资源包推送 → 本地缓存 → 客户端加载 → 资源就绪」这条链路
 * 收敛成唯一的状态机与唯一的事实来源。业务模块只读本服务的状态，不再各自扫一遍、各自等一遍。</p>
 *
 * <p><b>就绪判定口径沿用旧项目 {@code ServerResourceService}：</b>判据是「扫到的星露谷逻辑对象数」
 * （{@code items/*.json} 按语义家族聚合去重）而不是「自定义命名空间里有几个资源文件」；扫不到即
 * {@link ResourcePhase#NO_CONTENT}「非星露谷资源」，并由本服务发出 {@code [星露谷农场]} 口径的
 * 「资源档案已建立」计数卡。</p>
 *
 * <p><b>触发模型：进服只识别、不下载。</b>玩家不一定使用依赖服务器资源的功能，每进一个普通
 * 服务器就自动下载一份资源包不可接受。进入服务器只做一件事：识别当前 ServerKey 并把阶段置为
 * {@link ResourcePhase#NOT_CHECKED}。真正的缓存检查 / 下载 / 解析由玩家主动调用
 * {@link #requestExtract()} 触发。</p>
 *
 * <p><b>核心铁律：本服务绝不主动触发 Minecraft 全量资源重载。</b>下载器是纯旁路，只把服务器
 * 已下发的 ZIP 多存一份到缓存目录，从不改变客户端选中的资源包集合。资源集合是否生效完全由原版
 * 进服流程负责，本服务只<b>观测</b>它，等待有界超时后按当前已加载资源继续解析。</p>
 *
 * <p><b>切服：</b>立即停掉当前状态机并按新 ServerKey 重开会话；订阅者在失效回调里清空自己的
 * 运行时数据，磁盘 ZIP 缓存保留。资源未就绪前，订阅者禁止使用上一服务器的数据。</p>
 */
public final class ResourceExtractionService {

    /** 提示前缀使用的模块名：旧项目 {@code ServerResourceService} 原文，禁止改动 */
    private static final String MODULE = "服务器核心";

    /** CHECKING 阶段等待服务器资源包推送的上限（tick，约 10 秒） */
    private static final int NO_PACK_TIMEOUT = 200;
    /** DOWNLOADING 阶段等待下载完成的上限（tick，约 3 分钟） */
    private static final int DOWNLOAD_TIMEOUT = 3600;
    /** LOADING 阶段等待客户端完成资源应用的上限（tick，约 20 秒；本服务不主动触发重载） */
    private static final int RELOAD_TIMEOUT = 400;
    /** 下载参数（不依赖任何模块设置，保证模块关闭时也能工作） */
    private static final int DOWNLOAD_RETRIES = 5;
    private static final int DOWNLOAD_TIMEOUT_MS = 60_000;
    /** 星露谷农场口径的提示前缀（档案建立 / 未检测到资源） */
    private static final String STARDEW_MODULE = "星露谷农场";
    /** 计数格补位用的字符像素宽（旧项目 {@code ServerResourceService} 原文） */
    private static final int FULL_WIDTH_PX = 9;
    private static final int HALF_WIDTH_PX = 4;

    // ── 会话状态（只由本类写；跨线程读取用 volatile） ──
    private static boolean extractionRequested;
    /** 只在当前客户端进程保留「已手动授权检测」的服务器；断线不丢，重启不自动下载 */
    private static final Set<String> AUTHORIZED_SERVERS = new HashSet<>();
    private static volatile ResourcePhase phase = ResourcePhase.IDLE;
    private static volatile ResourceSource source = ResourceSource.NONE;
    private static volatile String serverKey;
    private static volatile String cacheName;
    private static volatile String fingerprint;
    private static volatile String failReason;
    private static volatile long readyGeneration;
    private static volatile ResourceScanResult scanResult = ResourceScanResult.empty();

    /** 最近一次服务器资源包推送（玩家触发提取时用它下载；不触发就什么都不做） */
    private static volatile UUID packId;
    private static volatile String packUrl;
    private static volatile String packHash;

    private static int stageTicks;
    /** 本会话是否已因「观察到重载」而跳过等待 */
    private static boolean reloadApplied;

    /** 当前 ServerKey 的资源包推送发生时的重载代次基线（「当前服务器资源是否已生效」的时间锚点） */
    private static long packPushReloadGen;

    /** 是否属于「没有本地 ZIP、临时用客户端已加载资源」的降级结果 */
    private static volatile boolean temporaryFallback;

    /** 全局资源重载代次：每次「资源重载真正完成」+1 */
    private static volatile long reloadGeneration;
    /** 已完成过一次人工检测后，同一连接发生资源重载时自动重建 */
    private static volatile boolean automaticRefreshPending;
    /** 失败时的重载代次，用于失败后的自动重试判断 */
    private static long failedReloadGen;
    /** 进入 LOADING 时记录的代次基线 */
    private static long loadingBaseReloadGen;
    /** 玩家触发检测时的代次基线 */
    private static long extractBaseReloadGen;

    /** 上一次就绪时解析到的资源 id 集合（跨服串档兜底比对用） */
    private static Set<String> lastReadyContentIds = new LinkedHashSet<>();
    /** 上一次就绪对应的 ServerKey */
    private static String lastReadyServerKey;

    /** 内容探针（由业务层注册；未注册时解析为空） */
    private static volatile ResourceContentProbe probe;

    /** 本会话已提示过的阶段（每个阶段只提示一次，不刷屏） */
    private static final Set<String> ANNOUNCED = new HashSet<>();

    /** 玩家未进入世界时的提示队列 */
    private static final Queue<String> PENDING_NOTICES = new ConcurrentLinkedQueue<>();

    /** 资源就绪订阅者（重建自己的索引 / 档案 / 选择项） */
    private static final List<Runnable> READY_LISTENERS = new CopyOnWriteArrayList<>();

    /** 资源会话失效订阅者（切服 / 断线时清空上一服务器的运行时数据） */
    private static final List<Runnable> INVALIDATE_LISTENERS = new CopyOnWriteArrayList<>();

    private static boolean initialized;

    private ResourceExtractionService() {
    }

    /** 挂载事件（客户端入口调用一次）：进服识别、断线失效、每 tick 推进状态机 */
    public static void init() {
        if (initialized) return;
        initialized = true;

        // 下载器的提示统一走客户端聊天
        ResourcePackCache.addNoticeListener(ResourceExtractionService::notifyChat);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> beginSession(currentKey()));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> invalidate("已断开连接"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 下载器的主线程兜底（提示补发 / 重命名 / 原版缓存复制）不依赖任何模块开关
            ResourcePackCache.tick();
            flushPending();
            advance();
        });
    }

    /** 注册内容探针（业务层在初始化时调用一次） */
    public static void registerProbe(ResourceContentProbe contentProbe) {
        probe = contentProbe;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  只读访问
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public static ResourcePhase phase() {
        return phase;
    }

    public static ResourceSource resourceSource() {
        return source;
    }

    /** 当前服务器键（真实 host:port）；无会话返回 {@code null} */
    public static String serverKey() {
        return serverKey;
    }

    /** 当前资源包推送的 URL；未收到推送返回 {@code null} */
    public static String packUrl() {
        return packUrl;
    }

    /** 命中的资源包缓存文件名；无缓存返回 {@code null} */
    public static String cacheFileName() {
        return cacheName;
    }

    /** 资源指纹（12 位十六进制）；未算出返回 {@code null} */
    public static String fingerprint() {
        return fingerprint;
    }

    /** 失败原因（中文）；非失败状态返回 {@code null} */
    public static String failReason() {
        return failReason;
    }

    /** 最近一次解析结果（分类计数与资源 id） */
    public static ResourceScanResult scanResult() {
        return scanResult;
    }

    /** 当前服务器资源是否已就绪 */
    public static boolean isReady() {
        return phase == ResourcePhase.READY;
    }

    /** 是否尚未检测（已识别服务器但玩家还没触发提取） */
    public static boolean isNotChecked() {
        return phase == ResourcePhase.NOT_CHECKED || phase == ResourcePhase.IDLE;
    }

    /** 是否正在处理（触发按钮需要禁用） */
    public static boolean isBusy() {
        return phase == ResourcePhase.CHECKING || phase == ResourcePhase.DOWNLOADING
            || phase == ResourcePhase.LOADING || phase == ResourcePhase.PARSING;
    }

    /** 是否已解析出可识别内容 */
    public static boolean hasContent() {
        return phase == ResourcePhase.READY && !scanResult.isEmpty();
    }

    /** 就绪代次：每次就绪 +1，订阅者据此判断是否需要重建 */
    public static long readyGeneration() {
        return readyGeneration;
    }

    /** 本次结果是否为「未落盘的临时降级解析」 */
    public static boolean isTemporaryFallback() {
        return temporaryFallback;
    }

    /** 当前会话的 ServerKey 是否与给定值一致（订阅者做跨服隔离校验用） */
    public static boolean matches(String key) {
        return key != null && key.equals(serverKey);
    }

    /** 资源缓存中文文案：区分「真落盘」「临时解析」「未使用」 */
    public static String cacheLabel() {
        if (cacheName == null || cacheName.isBlank()) {
            return temporaryFallback ? "未建立（临时解析）" : "未使用本地缓存";
        }
        return cacheName;
    }

    /** 资源指纹中文文案（未算出时给出可读兜底，不显示 null） */
    public static String fingerprintLabel() {
        return fingerprint == null || fingerprint.isBlank() ? "未建立" : fingerprint;
    }

    /**
     * 资源状态中文主文案：环境优先于阶段。
     *
     * <p>主界面永远「未进入世界」，单人世界永远「不支持」——单人存档里显示「未检测」
     * 会让玩家误以为点击就能检测服务器资源包。</p>
     */
    public static String statusLabel() {
        if (!GameProbe.inWorld()) return "未进入世界";
        if (isSingleplayer()) return "不支持（单人世界）";
        return temporaryFallback && phase == ResourcePhase.READY ? "临时可用" : phase.label();
    }

    public static void addReadyListener(Runnable listener) {
        if (listener != null) READY_LISTENERS.add(listener);
    }

    public static void addInvalidateListener(Runnable listener) {
        if (listener != null) INVALIDATE_LISTENERS.add(listener);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  外部信号
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 资源包推送入口（由资源包推送 Mixin 在原版处理前无条件调用）。
     *
     * <p>只做「识别 + 记住资源包信息」，<b>绝不下载</b>：是否下载由玩家触发
     * {@link #requestExtract()} 决定。</p>
     *
     * @param explicitServerKey 由 Mixin 从监听器自身的 {@code serverData} 解析出的 ServerKey。
     *                          配置阶段玩家实体尚未创建，{@code getCurrentServer()} 与
     *                          {@code getConnection()} 都不可用，只有监听器持有的 ServerData 可靠。
     */
    public static void onResourcePackPush(ClientboundResourcePackPushPacket packet, String explicitServerKey) {
        try {
            if (packet == null) return;
            String key = explicitServerKey;
            if (key == null || key.isBlank()) key = currentKey();
            if (key == null || key.isBlank()) key = serverKey;
            if (key == null || key.isBlank()) return;

            // 切服：推送来自新服务器时重开会话（只识别，不进入任何下载阶段）
            if (!key.equals(serverKey)) beginSession(key);

            packId = packet.id();
            packUrl = packet.url();
            packHash = packet.hash();
            // 推送一定早于原版的下载 / 应用，因此此后只要客户端完成过一次资源重载，
            // 就说明本次推送的资源集合已经生效（与这份资源是不是目标资源无关）
            packPushReloadGen = reloadGeneration;
        } catch (Exception e) {
            if (extractionRequested) fail("处理资源包推送异常：" + e.getMessage());
        }
    }

    /** 资源重载完成信号（由模型管理器 Mixin 注入调用） */
    public static void onReloadApplied() {
        reloadApplied = true;
        reloadGeneration++;
        // 派发表是按"当前生效资源"读出来的：资源一换就必须作废，否则会用上一份包的
        // （基础物品 + 阈值 → 模型）映射去识别新包的物品，表现为张冠李戴
        ItemModelDispatchIndex.invalidate();
        // 图集反查表同理：sprite 名 → 真实贴图是上一份包的结论，换包后世界字牌会挂错图
        StardewPreview.invalidate();
        // 已解码的世界字牌贴图也一起丢：同名贴图在两份包里内容可以完全不同，
        // 留着就是「换服后图标还是上一台服的」（与 PlayerFaceCache 同一口径）
        TextureImageCache.clear();
        if (extractionRequested
            && (phase == ResourcePhase.READY || phase == ResourcePhase.NO_CONTENT)) {
            automaticRefreshPending = true;
        }
    }

    /**
     * 玩家主动触发：检测 / 提取当前服务器资源包。
     *
     * <p>这是资源链路的唯一入口。进服不会自动调用它，任何模块开关也不会。</p>
     */
    public static void requestExtract() {
        if (!GameProbe.inWorld()) {
            notice("§c仅多人服务器可检测服务器资源包");
            return;
        }
        if (isSingleplayer()) {
            notice("§c星露谷资源检测仅支持多人服务器");
            return;
        }
        if (isBusy()) {
            notice("§e资源正在处理中（" + phase.label() + "），请稍候…");
            return;
        }

        String key = currentKey();
        if (key == null) key = serverKey;
        if (key == null) {
            notice("§c无法识别当前服务器地址，资源检测已取消。");
            return;
        }
        if (!key.equals(serverKey)) beginSession(key);
        AUTHORIZED_SERVERS.add(key);

        notifyInvalidate();
        // 清空上一轮结果与提示记录，重新走一遍完整链路
        ANNOUNCED.clear();
        failReason = null;
        scanResult = ResourceScanResult.empty();
        fingerprint = null;
        cacheName = null;
        source = ResourceSource.NONE;
        stageTicks = 0;
        reloadApplied = false;
        temporaryFallback = false;
        extractBaseReloadGen = reloadGeneration;
        loadingBaseReloadGen = reloadGeneration;

        phase = ResourcePhase.CHECKING;
        extractionRequested = true;
        announce("check", "§e开始检测服务器资源"
                + "\n" + CommandMessageFormatter.line("服务器名称", "§b" + textOf(GameProbe.serverName()))
                + "\n" + CommandMessageFormatter.line("服务器地址", "§b" + textOf(key))
                + "\n" + CommandMessageFormatter.line("状态", "§e检测中"));
        advanceChecking();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  状态机
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 当前环境是否单人世界 */
    private static boolean isSingleplayer() {
        return GameProbe.isSingleplayer();
    }

    /** 读取当前服务器键（单人世界用固定标识） */
    private static String currentKey() {
        if (isSingleplayer()) return "singleplayer";
        return ResourcePackCache.currentServerKey();
    }

    /** 进服 / 切服：只识别 ServerKey，不检查缓存、不下载 */
    private static void beginSession(String key) {
        if (key == null || key.isBlank()) {
            // 会话键不可用（例如进世界事件早于玩家创建）：保留已识别的真实 ServerKey，
            // 绝不把正确的 host:port 覆盖成 unknown
            if (serverKey != null && !serverKey.isBlank()) return;
            key = "unknown";
        }
        boolean sameServer = key.equals(serverKey);
        boolean autoAuthorized = AUTHORIZED_SERVERS.contains(key);
        if (sameServer && extractionRequested) return;
        if (!sameServer) notifyInvalidate();

        // 资源包推送元数据属于「服务器」，不属于「本次会话」：
        // 原版在配置阶段就下发推送包，而进服事件晚于它，无条件清空会导致玩家触发检测时
        // 永远拿不到 URL / hash，只能退化成「用已加载资源」
        UUID keepId = packId;
        String keepUrl = packUrl;
        String keepHash = packHash;

        serverKey = key;
        resetSession();
        if (sameServer) {
            packId = keepId;
            packUrl = keepUrl;
            packHash = keepHash;
        }
        if (autoAuthorized && !isSingleplayer()) {
            extractionRequested = true;
            extractBaseReloadGen = reloadGeneration;
            loadingBaseReloadGen = reloadGeneration;
            phase = ResourcePhase.CHECKING;
            announce("autoSession", "§e检测到已授权的同服子服切换，正在自动读取当前资源…");
        } else {
            phase = ResourcePhase.NOT_CHECKED;
        }
    }

    /** 断线 / 会话结束：清空运行时数据（磁盘 ZIP 缓存保留） */
    private static void invalidate(String reason) {
        if (serverKey != null) notifyInvalidate();
        serverKey = null;
        resetSession();
        packId = null;
        packUrl = null;
        packHash = null;
        phase = ResourcePhase.IDLE;
        failReason = reason;
        PENDING_NOTICES.clear();
        ResourcePackCache.clearSession();
    }

    /** 清空会话级运行时数据（不含 ServerKey、磁盘文件与资源包推送元数据） */
    private static void resetSession() {
        extractionRequested = false;
        PENDING_NOTICES.clear();
        cacheName = null;
        fingerprint = null;
        failReason = null;
        source = ResourceSource.NONE;
        temporaryFallback = false;
        scanResult = ResourceScanResult.empty();
        stageTicks = 0;
        reloadApplied = false;
        automaticRefreshPending = false;
        ANNOUNCED.clear();
    }

    private static void advance() {
        if (automaticRefreshPending && extractionRequested
            && (phase == ResourcePhase.READY || phase == ResourcePhase.NO_CONTENT)) {
            beginAutomaticRefresh();
        }
        switch (phase) {
            case CHECKING -> advanceChecking();
            case DOWNLOADING -> advanceDownloading();
            case LOADING -> advanceLoading();
            case PARSING -> advanceParsing();
            case FAILED -> advanceFailed();
            default -> {
                // IDLE / NOT_CHECKED / READY / NO_CONTENT 不推进
            }
        }
    }

    /** 同服资源完成原版重载后直接解析当前资源管理器，不复用可能过期的地址级 ZIP */
    private static void beginAutomaticRefresh() {
        automaticRefreshPending = false;
        ANNOUNCED.clear();
        failReason = null;
        cacheName = null;
        fingerprint = null;
        source = ResourceSource.LOADED;
        temporaryFallback = true;
        scanResult = ResourceScanResult.empty();
        stageTicks = 0;
        phase = ResourcePhase.PARSING;
        announce("automaticRefresh", "§e检测到同服子服资源已更新，正在自动重新读取…");
    }

    /**
     * 失败后的自救：玩家触发后延迟接受资源包、或网络慢导致失败后资源才加载完时，
     * 只要观察到新的资源重载就自动重跑一次检查，不必重新触发。
     */
    private static void advanceFailed() {
        if (reloadGeneration <= failedReloadGen) return;
        failReason = null;
        scanResult = ResourceScanResult.empty();
        source = ResourceSource.NONE;
        reloadApplied = false;
        stageTicks = 0;
        extractBaseReloadGen = reloadGeneration;
        phase = ResourcePhase.CHECKING;
        announce("retry", "§e检测到资源重载，重新尝试提取星露谷资源…");
        advanceChecking();
    }

    private static void advanceChecking() {
        stageTicks++;
        if (serverKey == null) {
            fail("未识别到当前服务器，无法检查资源缓存");
            return;
        }

        // 1) 已有该服务器缓存 → 直接复用，不重复下载
        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip != null) {
            announce("cache", "§a已发现资源缓存，直接复用§8 › §f" + zip.getName());
            adoptCache(zip);
            return;
        }

        // 2) 没有缓存但有资源包推送信息 → 调下载器下载一次
        if (packId != null && packUrl != null && !packUrl.isBlank()) {
            announce("download", "§e正在下载当前服务器资源包…");
            phase = ResourcePhase.DOWNLOADING;
            stageTicks = 0;
            ResourcePackCache.downloadAsync(packId, packUrl, packHash,
                DOWNLOAD_RETRIES, DOWNLOAD_TIMEOUT_MS, true);
            return;
        }

        // 3) 都没有，但客户端已加载目标资源 → 临时降级用已加载资源。
        //    必须向玩家如实说明这是未落盘的临时结果，不假装资源包已经下载
        if (clientHasContent()) {
            temporaryFallback = true;
            source = ResourceSource.LOADED;
            announce("reuse", "§6未发现本地缓存，也没有可用的资源包下载地址，改用客户端已加载的服务器资源"
                + "\n" + CommandMessageFormatter.line("资源来源", "§6当前服务器已加载资源（临时）")
                + "\n" + CommandMessageFormatter.line("资源缓存", "§6未建立（临时解析）")
                + "\n" + CommandMessageFormatter.line("说明", "§7本次未落盘，无法留作下次复用"));
            enterLoading();
            return;
        }

        // 4) 都没等到：给玩家接受资源包的时间，超时则明确失败
        if (stageTicks > NO_PACK_TIMEOUT) {
            fail("未收到服务器资源包推送，且本地无缓存、客户端也未加载星露谷资源");
        }
    }

    private static void advanceDownloading() {
        stageTicks++;
        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip != null) {
            adoptCache(zip);
            return;
        }
        if (stageTicks > DOWNLOAD_TIMEOUT) {
            fail("资源包下载失败（超时或网络不可达）");
        }
    }

    /**
     * 复用缓存 ZIP：直接进入「等待资源加载」。
     *
     * <p>这里故意不预置指纹：指纹必须描述「当前真正生效的资源内容」，而此刻资源还没进入
     * 资源管理器。提前用 ZIP 哈希占位会让内容指纹永远得不到计算机会，于是两个内容不同、
     * 文件名相同的服务器会撞成同一指纹。</p>
     */
    private static void adoptCache(File zip) {
        cacheName = zip.getName();
        source = ResourceSource.ZIP;
        enterLoading();
    }

    /**
     * 进入加载阶段。
     *
     * <p>判断顺序：</p>
     * <ol>
     *   <li>触发之后客户端已经自发完成过一次重载 → 当前加载的就是最新资源，直接解析；</li>
     *   <li>当前服务器下发的资源集合已在资源管理器中生效 → 直接解析；</li>
     *   <li>其余情况：只等客户端自身的应用流程完成（有界等待），绝不主动触发重载，
     *       等不到就按现有资源解析，由解析结果决定就绪还是无内容。</li>
     * </ol>
     */
    private static void enterLoading() {
        if (phase == ResourcePhase.LOADING || phase == ResourcePhase.PARSING
            || phase == ResourcePhase.READY) return;

        if (reloadGeneration > extractBaseReloadGen) {
            enterParsing();
            return;
        }
        if (serverResourcesApplied()) {
            enterParsing();
            return;
        }

        phase = ResourcePhase.LOADING;
        stageTicks = 0;
        loadingBaseReloadGen = reloadGeneration;
        announce("loading", "§e正在等待客户端应用当前服务器资源…");
    }

    /**
     * 资源管理器是否已处于「当前服务器资源已生效」的状态。
     *
     * <p>只看资源生命周期，不看「资源里有没有目标内容」——后者由解析阶段判定。</p>
     */
    private static boolean serverResourcesApplied() {
        if (packUrl == null || packUrl.isBlank()) return true;
        if (reloadGeneration > packPushReloadGen) return true;
        return clientHasContent();
    }

    private static void advanceLoading() {
        stageTicks++;
        if (reloadApplied || reloadGeneration > loadingBaseReloadGen) {
            enterParsing();
            return;
        }
        if (stageTicks <= RELOAD_TIMEOUT) return;

        // 超时兜底：必须确认当前加载的资源不属于「上一台服务器」，避免把 A 服资源当 B 服解析
        if (contentFromOtherServer()) {
            fail("等待资源生效超时：客户端仍加载着上一服务器的资源包，已阻止跨服串档");
            return;
        }
        announce("fallback", "§6⚠ 未观察到资源应用完成信号，按当前已加载资源继续解析");
        enterParsing();
    }

    /**
     * 当前加载的资源是否明显来自「上一台服务器」。
     *
     * <p>只作为跨服串档的安全阀：当前确有目标资源、与上一台服务器就绪时的资源集合完全相同、
     * 且 ServerKey 已变化时成立。它不决定是否需要重载。</p>
     */
    private static boolean contentFromOtherServer() {
        Set<String> current = currentContentIds();
        return !current.isEmpty()
            && current.equals(lastReadyContentIds)
            && lastReadyServerKey != null
            && !serverKey.equals(lastReadyServerKey);
    }

    /** 进入解析阶段：解析是同步的，一帧内完成并落到就绪 / 无内容 */
    private static void enterParsing() {
        phase = ResourcePhase.PARSING;
        stageTicks = 0;
        announce("parsing", "§e正在解析资源…");
    }

    /**
     * 「非星露谷资源」的具体原因。
     *
     * <p>真机事故：玩家切到不推送该资源包的子服（主城 / 家具城）后点检测，只看到一句
     * 「未检测到可识别的星露谷资源」，无从判断是服务器没有这个包、当前子服没推、还是模块出问题，
     * 于是反复点检测。这里把「扫到多少条星露谷资源、其中多少条是物品定义」如实报出来，
     * 两种成因给两种建议，绝不让人靠猜。</p>
     */
    private static String noContentReport() {
        int scanned = 0;
        int itemDefs = 0;
        for (StardewResourceScanner.ScannedModel model : StardewResourceScanner.scan()) {
            scanned++;
            if (model.logicalItem() && StardewResourceScanner.isStardew(model.modelId())) itemDefs++;
        }
        if (scanned == 0) {
            return "§c当前服务器未检测到可识别的星露谷资源，未建立资源档案。"
                + "\n" + CommandMessageFormatter.line("原因", "§f客户端当前没有加载 customcrops 资源（扫描 0 条）")
                + "\n" + CommandMessageFormatter.line("可能情况", "§f当前子服未推送该资源包，或服务器资源包未启用")
                + "\n" + CommandMessageFormatter.line("建议", "§f回到农田所在的子服，或在「资源包」界面启用服务器资源包后重试");
        }
        return "§c当前服务器未检测到可识别的星露谷资源，未建立资源档案。"
            + "\n" + CommandMessageFormatter.line("原因",
                "§f扫到 customcrops 资源 " + scanned + " 条，但只有 " + itemDefs + " 条物品定义")
            + "\n" + CommandMessageFormatter.line("建议", "§f该资源包结构与 CustomCrops 标准布局不同，无法按物品定义建索引");
    }

    /**
     * 解析当前已加载资源里的星露谷逻辑对象，并按结果落到就绪 / 非星露谷资源。
     *
     * <p><b>就绪判定沿用旧项目口径：</b>判据是「扫到的星露谷逻辑对象数」而不是「自定义命名空间里
     * 有几个资源文件」——只统计 {@code items/*.json} 物品定义、按语义家族聚合去重，与旧项目
     * {@code ServerResourceService.advanceParsing()} 逐行对应。</p>
     */
    private static void advanceParsing() {
        ResourceContentProbe contentProbe = probe;
        ResourceScanResult result = contentProbe == null ? ResourceScanResult.empty() : contentProbe.analyze();
        if (result == null) result = ResourceScanResult.empty();

        scanResult = result;

        ProfileCounts counts = profileCounts();
        fingerprint = ensureFingerprint();
        if (cacheName == null) {
            source = ResourceSource.LOADED;
            temporaryFallback = true;
        }
        stageTicks = 0;

        if (counts.total() == 0) {
            phase = ResourcePhase.NO_CONTENT;
            announceStardewFarm("notStardew", noContentReport());
            return;
        }

        phase = ResourcePhase.READY;
        readyGeneration++;
        lastReadyContentIds = currentContentIds();
        lastReadyServerKey = serverKey;

        announce("parsed", "§a资源检测完成"
            + "\n" + CommandMessageFormatter.line("资源来源", "§f" + resourceSource().label())
            + "\n" + CommandMessageFormatter.line("资源缓存", "§f" + cacheLabel())
            + "\n" + CommandMessageFormatter.line("资源指纹", "§b" + fingerprintLabel())
            + "\n" + CommandMessageFormatter.line("资源状态", "§a星露谷资源")
            + "\n" + CommandMessageFormatter.line("状态", temporaryFallback ? "§6临时可用" : "§a成功"));
        announceStardewFarm("ready", profileText(counts));
        notifyReady();
    }

    /**
     * 解析当前已加载资源里的星露谷逻辑对象。
     *
     * <p>只统计 {@code items/*.json}（用户可见的物品定义）并按语义家族聚合去重，
     * 绝不把每个 raw resource / world model 当成一个选择项（旧项目 {@code parse()} 原文）。</p>
     */
    private static ProfileCounts profileCounts() {
        Set<String> crops = new LinkedHashSet<>();
        Set<String> pots = new LinkedHashSet<>();
        Set<String> fertilizers = new LinkedHashSet<>();
        Set<String> potions = new LinkedHashSet<>();
        Set<String> cans = new LinkedHashSet<>();
        Set<String> sprinklers = new LinkedHashSet<>();
        Set<String> shelters = new LinkedHashSet<>();

        for (StardewResourceScanner.ScannedModel model : StardewResourceScanner.scan()) {
            // 计数判据与索引层完全同源（唯一入口）：items/ 物品定义 与 资源包派发表模型都算物品定义，
            // 编号家族的非法序号（sprinkler_1_item 之类零件模型）不算，两边数出来的数必须一致
            StardewSelectorCategory category = StardewResourceIndex.logicalCategoryOf(model);
            if (category == null) continue;
            String canonical = StardewResourceIndex.canonicalOf(category, model.modelName());
            switch (category) {
                case CROP -> crops.add(canonical);
                case POT -> pots.add(canonical);
                case FERTILIZER -> fertilizers.add(canonical);
                case POTION -> potions.add(canonical);
                case WATERING_CAN -> cans.add(canonical);
                case SPRINKLER -> sprinklers.add(canonical);
                case SHELTER -> shelters.add(canonical);
            }
        }
        return new ProfileCounts(crops.size(), pots.size(), fertilizers.size(), potions.size(),
            cans.size(), sprinklers.size(), shelters.size());
    }

    /** 七类逻辑对象数量（旧项目 {@code ParseResult} 的计数部分 + 温室玻璃） */
    private record ProfileCounts(int crops, int pots, int fertilizers, int potions, int cans, int sprinklers,
                                 int shelters) {

        int total() {
            return crops + pots + fertilizers + potions + cans + sprinklers + shelters;
        }
    }

    /**
     * 「资源档案已建立」计数块：七类对象排成「三列 × 两行 + 单格」的固定表，不再一列一行摊开。
     *
     * <p>行内列起点由 {@link #countCell} 按真实字体宽度补齐，保证上下两行严格对齐（旧项目原文）；
     * 第七类（温室玻璃）另起一行占第一格，列宽与上面第一列完全一致。</p>
     */
    private static String profileText(ProfileCounts counts) {
        return "§a资源档案已建立"
            + "\n" + countRow("作物", counts.crops(), "种植盆", counts.pots(), "肥料", counts.fertilizers())
            + "\n" + countRow("药剂", counts.potions(), "水壶", counts.cans(), "洒水器", counts.sprinklers())
            + "\n" + "§r" + countCell("温室玻璃", counts.shelters());
    }

    /**
     * 一行三格计数。
     *
     * <p>行首 {@code §r} 是刻意的：对齐逻辑只作用于「以 {@code §7} 开头的单字段行」，
     * 表格行由本方法自行排布，跳过对齐才不会被二次补位（旧项目原文）。</p>
     */
    private static String countRow(String label1, int value1, String label2, int value2,
                                   String label3, int value3) {
        return "§r" + countCell(label1, value1) + countCell(label2, value2) + countCell(label3, value3);
    }

    /** 单格「§7标签 §8▶ §a数值」，整格按真实字体宽度补齐到统一像素宽（全角 / 半角空格混合补位） */
    private static String countCell(String label, int value) {
        int gap = Math.max(0, countCellPx() - Minecraft.getInstance().font.width(label + " ▶ " + value));
        return "§7" + label + " §8▶ §a" + value
            + "　".repeat(gap / FULL_WIDTH_PX) + " ".repeat((gap % FULL_WIDTH_PX) / HALF_WIDTH_PX);
    }

    /** 单格目标像素宽：取最长标签「洒水器」与三位数值，保证任何一格都不会挤到下一列 */
    private static int countCellPx() {
        return Minecraft.getInstance().font.width("洒水器 ▶ 888");
    }

    private static void fail(String reason) {
        phase = ResourcePhase.FAILED;
        failReason = reason;
        stageTicks = 0;
        failedReloadGen = reloadGeneration;
        announce("fail", "§c星露谷资源准备失败：" + reason);
    }

    /**
     * 保证就绪 / 无内容一定有可用指纹。
     *
     * <p>优先级：内容指纹（能区分同名不同内容）→ 该 ServerKey 的 ZIP 整包哈希。
     * 绝不出现「就绪但指纹未计算」的不完整状态。</p>
     */
    private static String ensureFingerprint() {
        if (fingerprint != null) return fingerprint;

        ResourceContentProbe contentProbe = probe;
        String content = contentProbe == null ? null : contentProbe.contentFingerprint();
        if (content != null) {
            if (cacheName == null) source = ResourceSource.LOADED;
            return content;
        }

        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip != null) {
            String value = ResourcePackCache.fingerprintOf(zip);
            if (value != null) {
                cacheName = zip.getName();
                source = ResourceSource.ZIP;
                return value;
            }
        }
        return null;
    }

    /** 客户端当前是否已加载目标资源 */
    private static boolean clientHasContent() {
        return !currentContentIds().isEmpty();
    }

    /** 当前已加载的目标资源 id 集合（跨服串档比对基准） */
    private static Set<String> currentContentIds() {
        ResourceContentProbe contentProbe = probe;
        if (contentProbe == null) return Set.of();
        try {
            Set<String> ids = contentProbe.loadedContentIds();
            return ids == null ? Set.of() : ids;
        } catch (Exception ignored) {
            // 重载过程中读取异常：视为尚未就绪
            return Set.of();
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  通知（客户端本地聊天，绝不发服务器公屏）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 每个阶段只提示一次 */
    private static void announce(String stage, String text) {
        announceAs(MODULE, stage, text);
    }

    /** 星露谷农场口径的提示（档案建立 / 未检测到资源） */
    private static void announceStardewFarm(String stage, String text) {
        announceAs(STARDEW_MODULE, stage, text);
    }

    /**
     * 每个阶段只提示一次（去重键带模块名，与旧项目 {@code announceAs} 同口径）。
     *
     * <p><b>不再自动追加任何行：</b>文案由调用方一次写全，阶段信息只出现在真正需要的字段里。</p>
     */
    private static void announceAs(String moduleName, String stage, String text) {
        if (!extractionRequested) return;
        if (!ANNOUNCED.add(moduleName + "/" + stage)) return;
        notifyChat(moduleName, text);
    }

    /** 无条件提示（不去重）：用于玩家操作反馈 */
    private static void notice(String text) {
        notifyChat(MODULE, text);
    }

    private static void notifyChat(String text) {
        notifyChat(MODULE, text);
    }

    private static void notifyChat(String moduleName, String text) {
        String message = ClientChat.prefix(moduleName) + text;
        if (Minecraft.getInstance().player != null) {
            ClientChat.raw(message);
        } else {
            PENDING_NOTICES.add(message);
        }
    }

    /** 空值兜底：显示层绝不出现 null（与旧项目 {@code CommandMessageFormatter.safe} 同口径） */
    private static String textOf(String value) {
        return value == null || value.isBlank() ? "无" : value;
    }

    private static void flushPending() {
        if (Minecraft.getInstance().player == null) return;
        String message;
        while ((message = PENDING_NOTICES.poll()) != null) {
            ClientChat.raw(message);
        }
    }

    private static void notifyReady() {
        for (Runnable listener : new ArrayList<>(READY_LISTENERS)) {
            try {
                listener.run();
            } catch (Exception ignored) {
                // 单个订阅者异常不影响其它订阅者
            }
        }
    }

    private static void notifyInvalidate() {
        for (Runnable listener : new ArrayList<>(INVALIDATE_LISTENERS)) {
            try {
                listener.run();
            } catch (Exception ignored) {
                // 单个订阅者异常不影响其它订阅者
            }
        }
    }
}
