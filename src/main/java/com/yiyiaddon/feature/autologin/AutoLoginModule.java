package com.yiyiaddon.feature.autologin;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.ServerTextEvent;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
import com.yiyiaddon.feature.autologin.model.AuthRule;
import com.yiyiaddon.feature.autologin.model.AutoLoginState;
import com.yiyiaddon.feature.autologin.model.ServerEntryMode;
import com.yiyiaddon.feature.autologin.service.AccountChecker;
import com.yiyiaddon.feature.autologin.service.AutoLoginStyle;
import com.yiyiaddon.feature.autologin.service.ChatAnalyzer;
import com.yiyiaddon.feature.autologin.service.CommandHandler;
import com.yiyiaddon.feature.autologin.service.GuiLoginHandler;
import com.yiyiaddon.feature.autologin.service.LeyuanRouteService;
import com.yiyiaddon.feature.autologin.service.LoginHandler;
import com.yiyiaddon.feature.autologin.service.ReconnectHandler;
import com.yiyiaddon.feature.autologin.service.RegisterHandler;
import com.yiyiaddon.feature.autologin.service.ServerTextKit;
import com.yiyiaddon.feature.autologin.service.SubserverRouteService;
import com.yiyiaddon.feature.autologin.ui.AutoLoginPage;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.service.reconnect.ReconnectSuppression;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.Connection;

import java.util.Set;

/**
 * 自动登入：服务器进服助手（自动注册 / 登录、断线重连、进服后执行指令、到达目标区域）。
 *
 * <p><b>状态机流程</b>（逐字照旧 {@code autologin/AutoLoginModule}）：</p>
 * <pre>
 *   IDLE →（进服）→ WORLD_LOAD_WAIT
 *        →（倒计时）→ DETECTING_AUTH
 *        →（聊天注册提示）→ REGISTERING → DETECTING_AUTH
 *        →（聊天登录提示）→ LOGGING_IN → EXECUTING_COMMANDS / ACTIVE
 *        →（超时未收到任何提示，判定无需登录）→ EXECUTING_COMMANDS / ACTIVE
 *   任意状态 →（断线）→ RECONNECT_WAIT → IDLE
 * </pre>
 *
 * <p><b>正版验证服可以开启</b>（2026-09-21 改口径）：正版服没有密码流程，检测到加密连接时
 * 跳过注册 / 登录，直接按「认证已完成」收尾（进服指令与回服路线照常）；旧口径的「检测到
 * 加密连接立即关闭模块」不再执行。玩家账号是否正版不参与判定，正版账号进离线验证服
 * 仍走完整认证流程。</p>
 *
 * <p><b>分层</b>（第 48 条八类）：本类是<b>功能入口与流程控制</b>——
 * 主状态机 8 态、进服 / 断线 / 界面 / 文本四类事件分派、每刻编排、播报、界面接线；
 * 认证与重连的计时动作在 {@code service/} 的五个处理器；文本与侧边栏识别在
 * {@link ServerTextKit}；通用子服菜单路线与到达确认在 {@link SubserverRouteService}；
 * 自用回服路线（18 态 + 兜底 + 扫描 + 挂机区 + 异常恢复）在 {@link LeyuanRouteService}；
 * 设置与全部用户可见文案在 {@code config/}。</p>
 *
 * <p><b>框架适配清单（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>事件总线</b>：旧 {@code @EventHandler} 订阅 Meteor 事件 → 本项目由
 *         {@link #subscribedEvents()} 声明、{@code ModuleManager} 统一订阅退订。映射：
 *         {@code TickEvent.Post → TICK}（真正推进走 {@link #onTick(Minecraft)}）、
 *         {@code GameJoinedEvent → JOIN_SERVER}、{@code OpenScreenEvent → SCREEN_OPEN}、
 *         {@code ReceiveMessageEvent → SERVER_TEXT}。</li>
 *     <li><b>界面事件只带类名</b>：{@code SCREEN_OPEN} 的载荷是界面类名字符串，因此断线页 / 容器 /
 *         主菜单三处 {@code instanceof} 判定改为「类名相等」或
 *         {@code Class.forName(类名)} + {@code isAssignableFrom}（与自动农场 / 自动挖矿同一等价改写）。</li>
 *     <li><b>取消界面</b>：旧 {@code event.setCancelled(true)} → 新 {@code event.cancel()}，
 *         派发方收到取消后 {@code client.gui.setScreen(null)}，等价「静默容器菜单」。</li>
 *     <li><b>关闭模块</b>：旧 {@code toggle()} → {@code ModuleManager.setEnabled(MODULE_ID, false)}。
 *         {@link #onEnable()} 内的环境自关闭延到下一帧（旧基类注释同口径：避免状态机重入），
 *         事件与每刻路径内同步关闭。</li>
 *     <li><b>启动播报抑制</b>：旧构造器 {@code chatFeedback = false} → 覆写
 *         {@link #suppressEnableAnnounce()}，避免与本模块自己的「§a§l已启动 …」重复。</li>
 *     <li><b>主菜单可用</b>：旧 {@code runInMainMenu = true} → 本项目模块启用后每刻都会收到
 *         {@code TICK}（主菜单同样派发），不需要额外开关；断线倒计时因此能在主菜单里继续走。</li>
 *     <li><b>设置落盘</b>：旧 {@code cfg.xxx.set(...)} 由框架即时落盘 → 本项目运行期改设置后一律
 *         追一次 {@link #persistSettings()}（第 173 条），改的值仍是原来那些。</li>
 *     <li><b>HUD 状态串</b>：旧 {@code getInfoString()} 是旧框架 HUD 的接口，本项目没有 HUD 设施，
 *         该串原样保留为 {@link #hudStatus()}（「§eNs / §a连接中 / §a就绪 / null」四态），
 *         显示位置改到控制台状态条与概览页。</li>
 *     <li><b>重连倒计时控件</b>：旧 {@code getWidget()} 里 RECONNECT_WAIT 专用的倒计时表 +
 *         「立即重连 / §c停止重连」两个按钮 → 控制台概览页（{@link #reconnectNow()} /
 *         {@link #stopReconnect()} 承载同样的动作），文案逐字保留。</li>
 * </ol>
 *
 * <p><b>未搬的旧行为（只登记，不自行补）</b>：</p>
 * <ul>
 *     <li>旧 {@code handleDisconnect()} 会去关闭第三方框架「流星自带【自动重连】」并播报
 *         「§e已自动关闭流星自带【自动重连】，本模块将接管重连。」本项目不依赖任何第三方客户端框架
 *         （第 32 / 43 条），没有对应物，因此本模块只负责自己的重连，不接管他人。</li>
 *     <li>旧 {@code notify} 覆写里 {@code message.startsWith("自动登入：")} 的前缀裁剪：旧项目全树
 *         没有任何调用点会传入带该前缀的消息（{@code grep} 只命中这一处判断本身），属旧遗留，
 *         按「死代码可删」不搬。</li>
 *     <li>{@code onReceiveMessage} 首行的 {@code contains("[Jeraddon]")} 自播过滤：旧项目自身的播报
 *         前缀是 {@code [yiyiaddon]}，该判断永远不会命中（旧遗留死代码）；且本项目播报经
 *         {@link ClientChat#send} 走本地 {@code sendSystemMessage}，不会回灌成服务器文本事件。
 *         同一口径不搬。</li>
 *     <li>旧 {@code onGameLeft(GameLeftEvent)} 是空实现（只有一句 {@code isActive()} 判断），
 *         因此不声明 {@code DISCONNECT} 订阅；断线处理照旧全部由断线界面触发。</li>
 * </ul>
 *
 * @author yiyijia
 */
public final class AutoLoginModule extends Module {

    /** 模块 ID（状态文件键 / 快捷键键名后缀） */
    public static final String MODULE_ID = "autologin";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = AutoLoginTexts.MODULE_NAME;

    /** 重连后连续稳定多少 tick 才把重试次数清零（逐字 = 旧 {@code RECONNECT_STABLE_TICKS}） */
    private static final int RECONNECT_STABLE_TICKS = 200;

    /** 换服后等多少 tick 再识别子服名（逐字 = 旧 {@code SUBSERVER_SCAN_DELAY_TICKS}） */
    private static final int SUBSERVER_SCAN_DELAY_TICKS = 30;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（控制台页面读写） */
    private final AutoLoginSettings settings = new AutoLoginSettings();

    // ── 子处理器（认证 / 指令 / 重连 / GUI 登录） ──

    private final RegisterHandler registerHandler;
    private final LoginHandler loginHandler;
    private final CommandHandler commandHandler;
    private final ReconnectHandler reconnectHandler;
    private final GuiLoginHandler guiLoginHandler;

    // ── 路线服务（通用子服 / 自用回服） ──

    private final SubserverRouteService subserverRoute;
    private final LeyuanRouteService leyuanRoute;

    // ── 主状态机 ──

    private AutoLoginState state = AutoLoginState.IDLE;

    /** 世界加载等待计时 */
    private int worldLoadTicks;

    /** 认证提示监听计时：超时则判定该服务器无需登录 */
    private int authDetectTicks;

    /** 防重复标志 */
    private boolean registerSent;
    private boolean loginSent;
    private boolean commandSent;
    private boolean accountChecked;
    /** 文本事件重入保护（旧 {@code handlingMessage}） */
    private boolean handlingMessage;

    /** 当前会话的连接（用于区分「同一个连接」与「换了服务器」） */
    private ClientPacketListener sessionNetworkHandler;
    /** 登录大厅的服务器地址 */
    private String physicalServerAddress;
    private boolean authFlowCompleted;
    private boolean allowActiveAuthPrompt;
    private boolean lobbyConnectionAnnounced;
    private ClientPacketListener announcedSubserverHandler;

    /** 「检测服务器子服」开关的上一帧值（用于识别开关变化） */
    private boolean detectSubserverPreviously;
    private int pendingSubserverScanTicks;

    /** 到达目标区域后的指令调度（旧 {@code subserverCommandPending} / {@code subserverCommandTicks}） */
    private boolean subserverCommandPending;
    private int subserverCommandTicks;

    /** 重连后的稳定计时 */
    private int reconnectStableTicks;
    private boolean waitingForReconnectStability;

    public AutoLoginModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility", AutoLoginTexts.DESCRIPTION);

        reconnectHandler = new ReconnectHandler(mc, settings, this::onReconnectStart);
        registerHandler = new RegisterHandler(mc, settings, this::onRegisterComplete);
        loginHandler = new LoginHandler(mc, settings, this::onLoginComplete);
        commandHandler = new CommandHandler(mc, settings, this::onCommandComplete);
        guiLoginHandler = new GuiLoginHandler();

        subserverRoute = new SubserverRouteService(mc, settings, new SubserverRouteService.Listener() {
            @Override
            public void notify(String message) {
                AutoLoginModule.this.notify(message);
            }

            @Override
            public void debug(String message) {
                debugMsg(message);
            }

            @Override
            public void onTargetAreaConfirmed(String reason) {
                confirmTargetArea(reason);
            }
        });

        leyuanRoute = new LeyuanRouteService(mc, settings, new LeyuanRouteService.Listener() {
            @Override
            public void notify(String message) {
                AutoLoginModule.this.notify(message);
            }

            @Override
            public void debug(String message) {
                debugMsg(message);
            }

            @Override
            public void onTargetAreaConfirmed(String reason) {
                confirmTargetArea(reason);
            }
        });
    }

    /**
     * 图标字形（Material Symbols 的 {@code login}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标（模块 / 分类 / 控件）无占用冲突。用户 2026-09-18 反馈
     * 「加上图标」后补。</p>
     */
    private static final String ICON = "\uEA77";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：工具分类最后一位（管理员检测 → 自动重生 → 自动重连 → 传送 → 自动登入） */
    @Override
    public int order() {
        return 80;
    }

    /** 设置载体（控制台页面读写） */
    public AutoLoginSettings settings() {
        return settings;
    }

    /** 旧 {@code chatFeedback = false}：本模块自己播报启动结论，抑制框架紧随其后的重复「已开启」 */
    @Override
    protected boolean suppressEnableAnnounce() {
        return true;
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

    /** 立即写回设置（界面改动与运行期改值都即时生效，第 173 条） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 只读状态（控制台读它，不另建第二份状态） ──

    /** 主状态机当前状态 */
    public AutoLoginState state() {
        return state;
    }

    /**
     * 旧 HUD 状态串（旧 {@code getInfoString} 逐字，四态与配色原样）。
     *
     * <p>本项目没有 HUD 设施，该串改由控制台的状态条与概览页显示；未进入世界或未完成认证时
     * 返回 {@code null}（旧实现同样返回 {@code null}，表示框架不画这一格），界面侧显示为
     * {@link AutoLoginTexts#STATE_NONE}。</p>
     */
    public String hudStatus() {
        if (state == AutoLoginState.RECONNECT_WAIT) {
            int ticks = reconnectHandler.getTicksLeft();
            if (ticks >= 0) return "§e" + (ticks / 20) + "s";
            return AutoLoginTexts.STATE_CONNECTING;
        }
        return state == AutoLoginState.ACTIVE ? AutoLoginTexts.STATE_READY : null;
    }

    /** 重连处理器（概览页读倒计时与调度状态） */
    public ReconnectHandler reconnectHandler() {
        return reconnectHandler;
    }

    /** 自用回服路线（概览页读阶段名与挂机区状态） */
    public LeyuanRouteService leyuanRoute() {
        return leyuanRoute;
    }

    /** 通用子服路线（概览页读流程状态） */
    public SubserverRouteService subserverRoute() {
        return subserverRoute;
    }

    // ── 概览页动作（旧 getWidget 的两个按钮 + 旧设置的「立即检测账号」伪按钮） ──

    /** 旧「立即重连」按钮：跳过倒计时立刻连接 */
    public void reconnectNow() {
        if (reconnectHandler.isScheduled()) reconnectHandler.reconnectNow();
    }

    /** 旧「§c停止重连」按钮：取消调度并回到未连接态 */
    public void stopReconnect() {
        reconnectHandler.reset();
        transitionTo(AutoLoginState.IDLE);
        notify("§c已停止自动重连。");
    }

    /** 旧「立即检测账号」伪按钮：只在聊天栏输出检测结果，不参与登录判断 */
    public void checkAccountNow() {
        notify(AutoLoginTexts.CHECK_ACCOUNT_PREFIX + AccountChecker.describe(mc));
    }

    // ── 设置联动播报（旧 AutoLoginSettings 的 onChanged 文案逐字，改设置的界面回调这里输出） ──

    /** 旧 {@code autoLogin.onChanged}：开启自动登录时关闭自动注册那句（逐字） */
    public void announceAutoLoginExclusive() {
        notify("§a§l已开启自动登录§r，§c§l自动注册已关闭§r。");
    }

    /** 旧 {@code autoRegister.onChanged}：开启自动注册时关闭自动登录那句（逐字） */
    public void announceAutoRegisterExclusive() {
        notify("§a§l已开启自动注册§r，§c§l自动登录已关闭§r。");
    }

    /** 旧 {@code detectSubserver.onChanged}：开关各一句（逐字，含颜色码与标点） */
    public void announceSubserverDetection(boolean enabled) {
        notify(enabled
            ? "§a§l已开启服务器子服检测§r，进入子服将沿用大厅登录状态。"
            : "§c§l已关闭服务器子服检测§r，进入新服务器连接将重新检测登录。");
    }

    // ── 生命周期 ──

    /** 旧 {@code onActivate}：环境校验 → 状态复位 → 启动播报 → 已在服内则直接起流程 */
    @Override
    protected void onEnable() {
        if (closeForUnsupportedEnvironment()) {
            // 旧基类同口径：onActivate 里直接 toggle() 会状态机重入，延到下一帧
            mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
            return;
        }

        resetAllHandlers();
        sessionNetworkHandler = null;
        physicalServerAddress = null;
        authFlowCompleted = false;
        allowActiveAuthPrompt = false;
        lobbyConnectionAnnounced = false;
        announcedSubserverHandler = null;
        detectSubserverPreviously = settings.detectSubserver;
        pendingSubserverScanTicks = 0;
        subserverRoute.reset();
        leyuanRoute.reset();
        state = AutoLoginState.IDLE;
        notify("§a§l已启动 §8│ §f§l等待进入服务器...");
        debugMsg("模块已启动，等待进入服务器...");

        // 新老玩家冲突检测：自动登录和自动注册同时开启时提醒
        if (settings.autoLogin && settings.autoRegister) {
            notify("§e注意：自动登录和自动注册同时开启。新用户请关闭自动登录；老玩家请关闭自动注册。");
        }

        // 模块激活时若已在服务器中，记录服务器信息并进入就绪状态
        if (mc.player != null && mc.level != null && !mc.hasSingleplayerServer()) {
            sessionNetworkHandler = mc.getConnection();
            physicalServerAddress = currentServerAddress();
            recordCurrentServer();
            resetSessionFlags();
            beginPostLoadFlow();
        }
    }

    /** 旧 {@code onDeactivate}：清会话与路线状态，保留设置 */
    @Override
    protected void onDisable() {
        resetAllHandlers();
        sessionNetworkHandler = null;
        physicalServerAddress = null;
        authFlowCompleted = false;
        allowActiveAuthPrompt = false;
        lobbyConnectionAnnounced = false;
        announcedSubserverHandler = null;
        detectSubserverPreviously = settings.detectSubserver;
        pendingSubserverScanTicks = 0;
        subserverRoute.reset();
        leyuanRoute.reset();
        leyuanRoute.clearReenterPending();
        state = AutoLoginState.IDLE;
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(
            ClientEventType.TICK,
            ClientEventType.JOIN_SERVER,
            ClientEventType.SCREEN_OPEN,
            ClientEventType.SERVER_TEXT
        );
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            case JOIN_SERVER -> onGameJoined();
            case SCREEN_OPEN -> onOpenScreen(event);
            case SERVER_TEXT -> onServerText(event);
            default -> {
                // TICK 的推进统一走 onTick；其余事件本模块不关心
            }
        }
    }

    // ━━━ 每刻编排（旧 onTick:341-473 顺序原样） ━━━

    @Override
    public void onTick(Minecraft client) {
        if (closeForUnsupportedEnvironment()) {
            ModuleManager.setEnabled(MODULE_ID, false);
            return;
        }
        // 重连倒计时必须在 player==null 检查之前驱动，断线后才能正常触发
        reconnectHandler.tick();

        // 旧乐源异常恢复看门狗（超过等待上限则判定当前步骤失败）
        if (leyuanRoute.tickRecoveryWatchdog()) {
            reconnectHandler.reset();
            transitionTo(AutoLoginState.IDLE);
            return;
        }

        // GUI 自动登录：每 tick 检查当前屏幕。服务器用对话屏登录时，屏幕在进服加载阶段就会弹出，
        // 此时 mc.player 仍为 null，所以这里只判断屏幕，不能等玩家实体出现。
        if (settings.autoLogin && settings.guiLogin && mc.gui.screen() != null) {
            String password = settings.loginPassword;
            if (!password.isEmpty() && guiLoginHandler.tryHandle(mc.gui.screen(), password)) {
                notify("已识别 GUI 登录框，正在自动填写密码...");
            }
        }

        if (mc.player == null || mc.level == null) return;

        boolean detectSubserver = settings.detectSubserver;
        if (detectSubserver && !detectSubserverPreviously && !mc.hasSingleplayerServer() && mc.getConnection() != null) {
            sessionNetworkHandler = mc.getConnection();
            physicalServerAddress = currentServerAddress();
            if (authFlowCompleted) {
                allowActiveAuthPrompt = false;
                transitionTo(AutoLoginState.ACTIVE);
                notify("已将当前服务器设为登录大厅，后续子服将沿用当前登录状态。");
                startSubserverWorkflow();
            } else {
                notify("已开启服务器子服检测，当前登录服将先重新检测登录状态。");
                beginPostLoadFlow();
            }
        }
        detectSubserverPreviously = detectSubserver;

        if (!settings.usesStandardMenuRoute()) subserverRoute.stop();
        if (!canRunLeyuanRoute()) leyuanRoute.stop();

        subserverRoute.tick();

        leyuanRoute.tickAfkRecoveryTrigger(() -> state == AutoLoginState.ACTIVE);
        leyuanRoute.tick();

        subserverRoute.tickTargetAreaDetection();

        if (subserverCommandPending && ++subserverCommandTicks >= settings.subserverCommandDelay) {
            subserverCommandPending = false;
            String command = settings.subserverCommandText.trim();
            if (command.startsWith("/")) command = command.substring(1);
            if (!command.isEmpty() && mc.getConnection() != null) {
                notify("已回到目标子服，正在执行 " + AutoLoginStyle.command("/" + command) + "。");
                mc.getConnection().sendCommand(command);
                notify("回服指令已执行 " + AutoLoginStyle.command("/" + command) + "。");
            }
        }

        if (pendingSubserverScanTicks > 0 && --pendingSubserverScanTicks == 0) {
            if (settings.serverEntryMode == ServerEntryMode.LEYUAN_CUSTOM
                && settings.leyuanEnabled
                && ServerTextKit.findSidebarKeyword(mc, settings.leyuanMainCityKeywords) != null) {
                notify("已识别当前位置为" + AutoLoginStyle.location("主城大区") + "，继续执行乐源服定制路线。");
                return;
            }
            String subserverName = ServerTextKit.detectSubserverName(mc);
            if (subserverName != null) {
                notify(AutoLoginStyle.text("已进入子服") + "：" + AutoLoginStyle.text(subserverName)
                    + "，沿用§b§l大厅登录状态§r§f§l。");
            }
        }

        if (!mc.hasSingleplayerServer() && settings.autoCheckAccount && !accountChecked) {
            accountChecked = true;
            notify(AccountChecker.describe(mc));
            debugMsg(AccountChecker.describeDebug(mc));
        }

        if (waitingForReconnectStability) {
            reconnectStableTicks++;
            if (reconnectStableTicks >= RECONNECT_STABLE_TICKS) {
                reconnectHandler.markConnectionStable();
                reconnectStableTicks = 0;
                waitingForReconnectStability = false;
            }
        }

        switch (state) {

            case WORLD_LOAD_WAIT -> {
                worldLoadTicks++;
                if (worldLoadTicks >= settings.worldLoadWait) {
                    if (settings.noLoginDetection && !settings.autoLogin) {
                        settings.autoLogin = true;
                        persistSettings();
                        notify("未检测到服务器免登录消息，已开启自动登录检测。");
                    }
                    transitionTo(AutoLoginState.DETECTING_AUTH);
                }
            }

            case DETECTING_AUTH -> {
                // 监听期内始终等待聊天提示；超时则判定该服务器无需登录
                authDetectTicks++;
                if (authDetectTicks == 1) {
                    notify("开始监听认证提示... (超时: " + settings.authDetectTimeout + " tick)");
                }
                if (authDetectTicks >= settings.authDetectTimeout) {
                    notify("未检测到认证提示，跳过登录流程。");
                    proceedAfterAuth();
                }
            }

            case REGISTERING -> registerHandler.tick();

            case LOGGING_IN -> loginHandler.tick();

            case EXECUTING_COMMANDS -> commandHandler.tick();

            // RECONNECT_WAIT：由 tick 倒计时驱动，不需要额外操作
            default -> {
                // IDLE / ACTIVE / RECONNECT_WAIT
            }
        }
    }

    /** 旧 {@code onGameJoined}：进服 / 换服后的会话识别与流程起点 */
    private void onGameJoined() {
        if (closeForUnsupportedEnvironment()) {
            ModuleManager.setEnabled(MODULE_ID, false);
            return;
        }

        ClientPacketListener networkHandler = mc.getConnection();
        if (leyuanRoute.onGameJoined()) {
            sessionNetworkHandler = networkHandler;
            return;
        }
        // 自用路线已完成/空闲时被踢出重连：回到登入服后重新从头执行回服路线
        if (leyuanRoute.consumeReenterPending()) return;

        if (networkHandler != null && networkHandler == sessionNetworkHandler) {
            if (subserverRoute.transitionExpected() && authFlowCompleted && settings.detectSubserver) {
                pendingSubserverScanTicks = SUBSERVER_SCAN_DELAY_TICKS;
                subserverRoute.clearTransitionExpected();
            } else {
                subserverRoute.observeTransitionIfWaitingTarget();
            }
            return;
        }
        String currentAddress = currentServerAddress();
        if (settings.detectSubserver && authFlowCompleted && subserverRoute.transitionExpected()) {
            sessionNetworkHandler = networkHandler;
            allowActiveAuthPrompt = false;
            if (state != AutoLoginState.ACTIVE && state != AutoLoginState.EXECUTING_COMMANDS) {
                transitionTo(AutoLoginState.ACTIVE);
            }
            if (networkHandler != announcedSubserverHandler) {
                announcedSubserverHandler = networkHandler;
                pendingSubserverScanTicks = SUBSERVER_SCAN_DELAY_TICKS;
                subserverRoute.markSubserverConnectionSwitched();
            }
            subserverRoute.clearTransitionExpected();
            return;
        }
        if (settings.detectSubserver && physicalServerAddress != null && physicalServerAddress.equals(currentAddress)) {
            sessionNetworkHandler = networkHandler;
            if (state == AutoLoginState.IDLE || state == AutoLoginState.RECONNECT_WAIT) beginPostLoadFlow();
            return;
        }
        sessionNetworkHandler = networkHandler;
        physicalServerAddress = currentAddress;
        authFlowCompleted = false;
        subserverRoute.clearTransitionExpected();
        if (!lobbyConnectionAnnounced) {
            lobbyConnectionAnnounced = true;
            notify("你已进入登录大厅，正在检测登录状态。");
        }

        // 记录服务器连接信息（地址 + ServerData），供断线重连使用
        recordCurrentServer();

        resetSessionFlags();
        if (reconnectHandler.getReconnectAttempts() > 0) {
            waitingForReconnectStability = true;
        }

        beginPostLoadFlow();
    }

    /**
     * 旧 {@code onOpenScreen}：断线页捕获（GameLeft 有时不触发，这里作为补充）、
     * 自用路线运行中静默容器菜单、重连等待期手动回主菜单则取消重连。
     */
    private void onOpenScreen(ClientEvent event) {
        String screenClassName = event.payload();

        if (DisconnectedScreen.class.getName().equals(screenClassName)) {
            handleDisconnect();
            return;
        }
        // 玩家自己开着界面时，原版传送会把「加载地形中」无条件盖上来（用户 2026-09-19）：拦掉
        if (SilentContainer.isLevelLoadingHijack(screenClassName)) {
            event.cancel();
            return;
        }
        // 背包放行：玩家按 E 必须能开背包（生存 / 创造都算）。同时收掉我方静默容器，
        // 否则玩家在背包里的点击会按路线菜单的 containerId 发出去（错位、丢物品）
        if (SilentContainer.isPlayerInventory(screenClassName)) {
            if (leyuanRoute.isRunning() || subserverRoute.isRunning()) {
                SilentContainer.releaseSilentContainer();
            }
            return;
        }
        // 两条菜单路线运行中静默容器菜单：取消屏幕显示（不抢鼠标），菜单数据仍由 containerMenu 同步发包点击。
        // 通用子服菜单路线（subserverRoute）原先没进门控 —— 它同样会把服务器的选择界面弹出来抢鼠标；
        // 而它自己的点击代码原来只认 mc.screen，所以「不静默」当时反而是能跑的：两处必须一起改
        // （用户 2026-09-19：「检查一下所有打开 gui 的 都要以自动挖矿这个为准」）。
        if ((leyuanRoute.isRunning() || subserverRoute.isRunning())
            && SilentContainer.isContainerScreen(screenClassName)) {
            // 玩家手动开的箱子：压掉 + 收掉那个容器（真不给开）+ 动作栏提示
            SilentContainer.rejectPlayerContainer();
            event.cancel();
            return;
        }
        // 用户手动点击「返回服务器列表」或回到主菜单时，取消待执行的重连
        if (state == AutoLoginState.RECONNECT_WAIT
            && (JoinMultiplayerScreen.class.getName().equals(screenClassName)
                || TitleScreen.class.getName().equals(screenClassName))) {
            reconnectHandler.reset();
            transitionTo(AutoLoginState.IDLE);
            notify("§e已检测到手动返回主菜单，自动重连已取消。");
        }
    }

    /**
     * 旧 {@code onReceiveMessage}：只处理聊天文本，供免登录判定与认证规则识别使用。
     *
     * <p>取 {@code CHAT} 与 {@code CHAT_OVERLAY} 两路：它们同出
     * {@code ClientboundSystemChatPacket}（仅 overlay 标志不同），即旧 {@code ReceiveMessageEvent}
     * 的同一来源；覆盖两者才能照旧识别「登录提示走动作栏」这类服务器。</p>
     */
    private void onServerText(ClientEvent event) {
        ServerTextEvent text = event.text();
        if (text == null) return;
        String channel = text.channel();
        if (!ServerTextEvent.CHAT.equals(channel) && !ServerTextEvent.CHAT_OVERLAY.equals(channel)) return;
        if (handlingMessage) return;
        handlingMessage = true;
        try {
            String raw = text.text() == null ? "" : text.text().getString();

            if (settings.noLoginDetection && ChatAnalyzer.isLoginSuccess(raw)
                && (state == AutoLoginState.WORLD_LOAD_WAIT || state == AutoLoginState.DETECTING_AUTH)) {
                settings.autoLogin = false;
                persistSettings();
                authFlowCompleted = true;
                allowActiveAuthPrompt = false;
                notify("检测到服务器免登录成功消息，当前仍在两小时免登录期，已关闭自动登录。");
                transitionTo(AutoLoginState.ACTIVE);
                startSubserverWorkflow();
                return;
            }

            if (state != AutoLoginState.DETECTING_AUTH
                && state != AutoLoginState.WORLD_LOAD_WAIT
                && (state != AutoLoginState.ACTIVE || !allowActiveAuthPrompt)) {
                return;
            }

            AuthRule rule = ChatAnalyzer.analyze(raw);
            if (rule == null) return;
            if (settings.noLoginDetection && rule.type == AuthRule.Type.LOGIN && !settings.autoLogin) {
                settings.noLoginDetection = false;
                settings.autoLogin = true;
                persistSettings();
                notify("检测到服务器登录提示，免登录已失效，已关闭免检测并开启自动登录。");
            }
            if (!settings.autoLogin && !settings.autoRegister) return;

            if (state == AutoLoginState.WORLD_LOAD_WAIT) {
                debugMsg("加载等待期间收到认证提示，立即跳过等待。");
                transitionTo(AutoLoginState.DETECTING_AUTH);
            }

            debugMsg("识别到" + (rule.type == AuthRule.Type.REGISTER ? "注册" : "登录") + "提示，规则：" + rule.template);

            if (rule.type == AuthRule.Type.REGISTER && settings.autoRegister && !registerSent) {
                String password = settings.registerPassword;
                if (password.isEmpty()) {
                    notify("注册密码未设置，跳过自动注册");
                    return;
                }
                registerSent = true;
                notify("正在自动注册...");
                transitionTo(AutoLoginState.REGISTERING);
                registerHandler.start(rule);
            } else if (rule.type == AuthRule.Type.LOGIN && settings.autoLogin && !loginSent) {
                String password = settings.loginPassword;
                if (password.isEmpty()) {
                    notify("登录密码未设置，跳过自动登录");
                    return;
                }
                loginSent = true;
                allowActiveAuthPrompt = false;
                notify("正在自动登录...");
                transitionTo(AutoLoginState.LOGGING_IN);
                loginHandler.start(rule);
            }
        } finally {
            handlingMessage = false;
        }
    }

    // ── 处理器回调 ──

    /** 注册完成，继续监听登录提示 */
    private void onRegisterComplete() {
        transitionTo(AutoLoginState.DETECTING_AUTH);
    }

    private void onLoginComplete() {
        proceedAfterAuth();
    }

    private void onCommandComplete() {
        transitionTo(AutoLoginState.ACTIVE);
    }

    private void onReconnectStart() {
        notify("正在重新连接... (第 " + reconnectHandler.getReconnectAttempts() + " 次)");
    }

    /**
     * 认证阶段收尾：无论是登录成功，还是判定该服务器无需登录，都走这里。
     * 保证自动执行指令在两种情况下都能触发。
     */
    private void proceedAfterAuth() {
        authFlowCompleted = true;
        allowActiveAuthPrompt = settings.noLoginDetection;
        if (settings.autoEnterSubserver && settings.serverEntryMode == ServerEntryMode.DIRECT) {
            confirmTargetArea("服务器已就绪");
        } else {
            startSubserverWorkflow();
        }
        if (!settings.autoEnterSubserver && settings.autoCommand && !commandSent) {
            commandSent = true;
            String cmd = settings.autoCommandText;
            notify("执行: " + cmd);
            transitionTo(AutoLoginState.EXECUTING_COMMANDS);
            commandHandler.start();
        } else {
            transitionTo(AutoLoginState.ACTIVE);
        }
    }

    // ── 内部工具 ──

    /** 旧 {@code handleDisconnect}：记录恢复起点 → 清会话 → 按设置调度重连 */
    private void handleDisconnect() {
        if (state == AutoLoginState.RECONNECT_WAIT && reconnectHandler.isScheduled()) return;

        // 主动断线（管理员检测的「立即断线」）：保命动作不允许被重连撤销 —— 抑制窗口内既不调度
        // 重连，也不走乐源路线异常恢复（恢复同样会把人连回去）。会话与两条路线照常清场
        // （断线清场是任何断线都要做的，漏了会留过期会话状态）；下次手动进服从头走认证流程。
        // 判据与独立「自动重连」模块的 {@code handleDisconnect} 同源（{@link ReconnectSuppression}）。
        if (ReconnectSuppression.suppressed()) {
            waitingForReconnectStability = false;
            reconnectStableTicks = 0;
            resetSessionFlags();
            sessionNetworkHandler = null;
            physicalServerAddress = null;
            authFlowCompleted = false;
            allowActiveAuthPrompt = true;
            announcedSubserverHandler = null;
            pendingSubserverScanTicks = 0;
            leyuanRoute.stop();
            subserverRoute.reset();
            notify("本次断开是主动断线（管理员检测），不自动重连。");
            transitionTo(AutoLoginState.IDLE);
            return;
        }

        boolean recoveringLeyuan = leyuanRoute.beginRecovery();
        if (!recoveringLeyuan) {
            // 自用路线已完成/空闲时被踢出（如 out_of_order_chat），重连回登入服后需重新从头回服
            leyuanRoute.markReenterPending();
        }
        waitingForReconnectStability = false;
        reconnectStableTicks = 0;
        resetSessionFlags();
        sessionNetworkHandler = null;
        physicalServerAddress = null;
        authFlowCompleted = false;
        allowActiveAuthPrompt = true;
        announcedSubserverHandler = null;
        pendingSubserverScanTicks = 0;
        if (recoveringLeyuan) {
            subserverRoute.stop();
        } else {
            subserverRoute.reset();
        }

        if (settings.autoReconnect) {
            int delayTicks = settings.reconnectDelay;
            int maxAttempts = settings.maxReconnectAttempts;
            if (recoveringLeyuan) {
                leyuanRoute.activateRecovery();
                delayTicks = leyuanRoute.recoveryDelayTicks(reconnectHandler.getReconnectAttempts());
                maxAttempts = settings.leyuanRecoveryMaxRetries;
                debugMsg("乐源路线进入异常恢复：步骤=" + leyuanRoute.state() + "，等待=" + (delayTicks / 20) + " 秒");
            }
            int delaySeconds = delayTicks / 20;
            if (reconnectHandler.startReconnect(delayTicks, maxAttempts)) {
                notify("服务器断开，" + delaySeconds + " 秒后重新连接。");
                transitionTo(AutoLoginState.RECONNECT_WAIT);
            } else {
                if (recoveringLeyuan) {
                    leyuanRoute.failRecovery();
                    notify("乐源路线异常恢复已达到重试上限，当前步骤失败。");
                } else {
                    notify("已达到最大重连次数，自动重连停止。");
                }
                transitionTo(AutoLoginState.IDLE);
            }
        } else {
            transitionTo(AutoLoginState.IDLE);
        }
    }

    private void transitionTo(AutoLoginState next) {
        debugMsg("状态: " + state + " → " + next);
        // 每次进入监听阶段都重新计时（注册完成后回到监听也需要完整窗口）
        if (next == AutoLoginState.DETECTING_AUTH) authDetectTicks = 0;
        state = next;
    }

    /** 旧 {@code beginPostLoadFlow}：正版服跳过认证，免登录检测优先，其次认证流程，都没有就直接收尾 */
    private void beginPostLoadFlow() {
        // 正版验证服（2026-09-21 改口径：允许开启）：会话在握手阶段已由 Mojang 验证完毕，
        // 没有注册 / 登录 / GUI 密码流程 —— 跳过认证监听，直接按「认证已完成」收尾；
        // 进服指令、回服路线与断线重连照常工作。
        if (isAuthenticatedServer()) {
            notify("已识别正版验证服务器，跳过登录流程。");
            proceedAfterAuth();
            return;
        }
        if (settings.noLoginDetection) {
            settings.autoLogin = false;
            persistSettings();
            notify("进服已开启服务器免登录检测，正在监听成功登录或登录提示...");
            transitionTo(AutoLoginState.WORLD_LOAD_WAIT);
            worldLoadTicks = 0;
        } else if (settings.autoLogin || settings.autoRegister) {
            transitionTo(AutoLoginState.WORLD_LOAD_WAIT);
            worldLoadTicks = 0;
        } else {
            proceedAfterAuth();
        }
    }

    private void resetSessionFlags() {
        registerSent = false;
        loginSent = false;
        commandSent = false;
        accountChecked = false;
        worldLoadTicks = 0;
        authDetectTicks = 0;
        reconnectStableTicks = 0;
        waitingForReconnectStability = false;
    }

    private void resetAllHandlers() {
        registerHandler.reset();
        loginHandler.reset();
        commandHandler.reset();
        reconnectHandler.reset();
        guiLoginHandler.reset();
        resetSessionFlags();
    }

    /** 旧 {@code startSubserverWorkflow} 的分流：自用配置转交自用路线，其余转交通用子服路线 */
    private void startSubserverWorkflow() {
        if (!settings.autoEnterSubserver || settings.serverEntryMode == ServerEntryMode.DIRECT) return;
        if (settings.serverEntryMode == ServerEntryMode.LEYUAN_CUSTOM) {
            leyuanRoute.start();
            return;
        }
        subserverRoute.start();
    }

    /** 旧 {@code canRunLeyuanRoute} */
    private boolean canRunLeyuanRoute() {
        return settings.usesLeyuanMode() && (settings.leyuanEnabled || settings.leyuanAfkRecoveryEnabled);
    }

    /**
     * 两条路线共用的「已确认到达目标区域」收尾（旧 {@code confirmTargetArea}）。
     *
     * <p>等待标志由路线服务自己在确认时清掉（第 169 条），这里只负责播报与「到达后执行指令」的调度：
     * 指令计数从「到达稳定等待」的负值起算，等价于先等稳定再等额外延迟，逐字照旧。</p>
     */
    private void confirmTargetArea(String reason) {
        notify("已确认到达目标区域（" + reason + "）。");
        if (settings.subserverCommand) {
            subserverCommandPending = true;
            subserverCommandTicks = -settings.targetStableDelay;
        }
    }

    /**
     * 旧 {@code closeForUnsupportedEnvironment}：单人世界不需要自动登录。
     *
     * <p><b>正版验证服不再关闭模块</b>（2026-09-21 改口径）：正版服允许开启，只是没有密码流程 ——
     * {@link #beginPostLoadFlow()} 检测到加密连接时直接按「认证已完成」收尾。</p>
     *
     * <p>返回 {@code true} 表示「应关闭」，由调用方执行关闭：{@link #onEnable()} 里延到下一帧
     * （避免状态机重入），事件与每刻路径里同步关闭。</p>
     */
    private boolean closeForUnsupportedEnvironment() {
        if (!isEnabled()) return false;
        if (!mc.hasSingleplayerServer()) return false;
        notify("§c§l单人世界无需自动登录，模块已自动关闭。");
        return true;
    }

    /**
     * 当前连接是否为正版验证服。
     *
     * <p>正版验证服在登录握手阶段启用连接加密，离线服不会启用。不能比较会话 UUID 与世界 UUID：
     * 代理转发 UUID 的离线服也可能保持两者一致（旧实现注释照旧）。</p>
     *
     * <p><b>26.2 口径</b>：{@code Connection#isEncrypted()} 已被移除，且连接里不再保存「已加密」布尔值，
     * 加密状态只剩「管线里装没装 {@code CipherDecoder}」这一种表现。这里按
     * {@code setEncryptionKey} 的装配方式判断（它把解密处理器插在 {@code splitter} 之前、命名为
     * {@code decrypt}），与旧布尔值同源同义。读的是包私有字段 {@code channel}，
     * 已随本模组的 accessWidener 放开。</p>
     *
     * <p>旧口径用它关闭模块；现口径（2026-09-21）改为在 {@link #beginPostLoadFlow()} 里跳过
     * 密码流程 —— 正版服的会话已由 Mojang 验证，无需本模块再登。</p>
     */
    private boolean isAuthenticatedServer() {
        if (mc.player == null || mc.level == null || mc.getConnection() == null) return false;
        Connection connection = mc.getConnection().getConnection();
        if (connection == null || connection.channel == null) return false;
        return connection.channel.pipeline().get("decrypt") != null;
    }

    /**
     * 记录当前服务器连接信息，供断线重连使用。
     * 兼容直连（{@code getCurrentServer() == null}）：从网络连接中读取地址。
     */
    private void recordCurrentServer() {
        ServerData entry = mc.getCurrentServer();
        if (entry != null) {
            reconnectHandler.recordServer(ServerAddress.parseString(entry.ip), entry);
        } else if (mc.getConnection() != null && mc.getConnection().getServerData() != null) {
            // 直连场景（命令行 / 启动参数等）：从网络连接获取
            ServerData info = mc.getConnection().getServerData();
            reconnectHandler.recordServer(ServerAddress.parseString(info.ip), info);
        }
    }

    private String currentServerAddress() {
        ServerData entry = mc.getCurrentServer();
        if (entry != null) return entry.ip;
        if (mc.getConnection() != null && mc.getConnection().getServerData() != null) {
            return mc.getConnection().getServerData().ip;
        }
        return null;
    }

    // ── 播报（旧基类的颜色码包装逐字保留） ──

    /** 普通消息（旧基类：前缀 + {@code §f} + 正文） */
    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, "§f" + message);
    }

    /** 调试消息（仅调试模式开启时输出，逐字 = 旧 {@code debugMsg}） */
    private void debugMsg(String message) {
        if (settings.debugMode) {
            notify("§8[调试] §7" + message);
        }
    }

    // ── 界面 ──

    /** 配置页 = 薄壳模块页 {@link AutoLoginPage} + 整屏控制台（概览 + 旧 6 个设置组） */
    @Override
    public ModulePage page() {
        return new AutoLoginPage(this);
    }
}
