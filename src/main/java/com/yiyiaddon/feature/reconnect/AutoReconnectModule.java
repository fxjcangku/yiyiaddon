package com.yiyiaddon.feature.reconnect;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autologin.AutoLoginModule;
import com.yiyiaddon.feature.reconnect.config.ReconnectSettings;
import com.yiyiaddon.feature.reconnect.config.ReconnectTexts;
import com.yiyiaddon.feature.reconnect.model.ReconnectState;
import com.yiyiaddon.feature.reconnect.ui.ReconnectPage;
import com.yiyiaddon.platform.network.ConnectionCloser;
import com.yiyiaddon.service.reconnect.ReconnectEngine;
import com.yiyiaddon.service.reconnect.ReconnectSuppression;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;

import java.util.Set;

/**
 * 自动重连模块：断线后自动连回上一次进入的服务器。
 *
 * <p><b>来源（用户 2026-09-18 点名新增）</b>：旧项目没有独立的自动重连模块，重连能力原先长在
 * 自动登入里（旧 {@code autologin/service/ReconnectHandler}）。用户要求「单独拆一个自动重连功能做全点、
 * 加个测试键」，因此本模块把重连<b>独立出来</b>，并补齐自动登入里没有的三件事：
 * 可控的稳定判定、可控的「回主菜单取消」、以及一键断开的测试键。</p>
 *
 * <p><b>逻辑不重复（第 169 条）</b>：倒计时 / 服务器记录 / 失败计数 / 发起连接全部在共用引擎
 * {@link ReconnectEngine} 里，自动登入与本模块各持一个实例、各读自己的设置；
 * 断线动作走共用件 {@link ConnectionCloser}。本类只负责编排（何时调度、何时放弃、怎么播报）。</p>
 *
 * <p><b>与自动登入共存</b>：两个模块都启用且自动登入开着「自动重连」时，重连由<b>自动登入</b>接管，
 * 本模块断线时让路（不重复调度，避免同一次断线连两遍）。判据写在 {@link #autoLoginTakesOver()}，
 * 概览页把「谁在接管」显示出来，不靠玩家猜。自动登入模块本身<b>一字未改</b>，旧行为原样保留。</p>
 *
 * <p><b>什么时候不重连（口径明确，均有播报或状态可见）</b>：总开关关闭、没有服务器记录、
 * 已达失败上限、管理员检测的主动断线（{@link ReconnectSuppression} 抑制窗口内）、
 * 以及手动点「断开连接」退回主菜单（原版走主菜单、不出现断线界面）。</p>
 *
 * <p><b>测试键</b>：{@link #testReconnect()} 先倒计时 {@link #TEST_DISCONNECT_TICKS} tick 再真正断开，
 * 目的是让二次确认窗的关闭动画先走完 —— 断开界面若与关窗动画抢同一个 {@code setScreen}，
 * 玩家会看到断开界面被旧窗口盖回去。断开后走的是正常断线链路，验证的是真实重连流程。</p>
 */
public final class AutoReconnectModule extends Module {

    /** 模块 ID（状态文件键 / 快捷键键名后缀） */
    public static final String MODULE_ID = "autoreconnect";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    private static final String MESSAGE_MODULE = ReconnectTexts.MODULE_NAME;

    /**
     * 测试断开前的等待 tick（10 tick = 0.5 秒）。
     *
     * <p>等的是二次确认窗的关闭动画（{@code PanelScreen} 的关窗弹簧），动画没走完就 {@code setScreen}
     * 到断线界面，会被随后的「回到上级窗口」覆盖掉。</p>
     */
    private static final int TEST_DISCONNECT_TICKS = 10;

    /** 一秒的 tick 数（播报里的秒数换算，与既有模块同口径） */
    private static final int TICKS_PER_SECOND = 20;

    private final Minecraft mc = Minecraft.getInstance();

    /** 设置载体（控制台页面读写） */
    private final ReconnectSettings settings = new ReconnectSettings();

    /** 重连引擎（与自动登入共用同一实现，各持一个实例） */
    private final ReconnectEngine engine;

    /** 运行状态（显示用；判据全部来自引擎，见 {@link ReconnectState}） */
    private ReconnectState state = ReconnectState.IDLE;

    /** 稳定判定窗口：重新连上后开始计时 */
    private boolean waitingStability;
    private int stableTicks;

    /** 测试断开的倒计时；{@code -1} = 没有待执行的测试 */
    private int testDisconnectTicks = -1;

    public AutoReconnectModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility", ReconnectTexts.DESCRIPTION);

        engine = new ReconnectEngine(mc, new ReconnectEngine.Policy() {
            @Override
            public boolean enabled() {
                return settings.enabled;
            }

            @Override
            public int delayTicks() {
                return settings.delayTicks;
            }

            @Override
            public int maxAttempts() {
                return settings.maxAttempts;
            }

            @Override
            public boolean unlimited() {
                return settings.unlimited;
            }
        }, this::onReconnectStart);
    }

    /**
     * 图标字形（Material Symbols 的 {@code sync}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE627";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：工具分类第三位（管理员检测 → 服务器检测 → 自动重连 → 传送 → 水源显示） */
    @Override
    public int order() {
        return 30;
    }

    /** 本模块自己播报启动结论，抑制运行时紧随其后的重复「已开启」 */
    @Override
    protected boolean suppressEnableAnnounce() {
        return true;
    }

    // ── 设置 ──

    /** 设置载体（控制台页面读写） */
    public ReconnectSettings settings() {
        return settings;
    }

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，第 173 条） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 只读状态（控制台读它，不另建第二份状态） ──

    /** 重连引擎（概览页读倒计时、失败次数与服务器记录） */
    public ReconnectEngine engine() {
        return engine;
    }

    /** 当前状态 */
    public ReconnectState state() {
        return state;
    }

    /**
     * 状态显示串（状态条与概览页共用一处拼装）。
     *
     * <p>等待期显示剩余秒数；设置关闭时显示「已关闭」，与「空闲（未连接）」区分开 ——
     * 后者是没进服，前者是被设置关掉了，玩家看到的现象完全不同。</p>
     */
    public String stateText() {
        if (!settings.enabled) return ReconnectTexts.STATE_DISABLED;
        if (state == ReconnectState.WAITING && engine.isScheduled()) {
            return ReconnectTexts.STATE_WAITING_PREFIX
                + (engine.getTicksLeft() / TICKS_PER_SECOND)
                + ReconnectTexts.STATE_WAITING_SUFFIX;
        }
        return state.title();
    }

    /** 失败次数 / 上限的显示串（无限重连时显示「不限」） */
    public String attemptsText() {
        return engine.getReconnectAttempts() + " §8/ "
            + (settings.unlimited ? "§f不限" : "§f" + settings.maxAttempts);
    }

    /**
     * 重连是否由自动登入模块接管（两个模块都开时，避免同一次断线连两遍）。
     *
     * <p>读的是自动登入模块的只读接口与它自己的设置字段，不改对方任何状态；自动登入关闭或它自己
     * 关掉「自动重连」时，本模块立刻接手。</p>
     */
    public boolean autoLoginTakesOver() {
        Module module = ModuleManager.byId(AutoLoginModule.MODULE_ID);
        return module instanceof AutoLoginModule login
            && login.isEnabled()
            && login.settings().autoReconnect;
    }

    /** 概览页「谁在接管」一行的取值 */
    public String ownerText() {
        return autoLoginTakesOver() ? ReconnectTexts.YIELD_TEXT : "§a本模块";
    }

    // ── 概览页动作 ──

    /** 立即重连：跳过等待时间，立刻连接 */
    public void reconnectNow() {
        if (!engine.isScheduled()) return;
        engine.reconnectNow();
        state = ReconnectState.CONNECTING;
    }

    /** 停止重连：取消本次等待（保留服务器记录，之后仍可用「立即重连」再点一次） */
    public void stopReconnect() {
        if (!engine.isScheduled()) return;
        engine.cancelScheduled();
        state = ReconnectState.IDLE;
        notify(ReconnectTexts.MSG_STOPPED);
    }

    /**
     * 测试重连：等 {@link #TEST_DISCONNECT_TICKS} tick 后主动断开当前连接，随后走正常断线链路自动连回。
     *
     * <p>不在服务器里时只播报一句，不做任何动作。</p>
     */
    public void testReconnect() {
        if (!ConnectionCloser.hasConnection()) {
            notify(ReconnectTexts.MSG_TEST_NO_CONNECTION);
            return;
        }
        if (autoLoginTakesOver()) notify(ReconnectTexts.MSG_YIELD);
        notify(ReconnectTexts.MSG_TEST_ARMED);
        testDisconnectTicks = TEST_DISCONNECT_TICKS;
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        engine.reset();
        waitingStability = false;
        stableTicks = 0;
        testDisconnectTicks = -1;
        state = ReconnectState.IDLE;
        notify(ReconnectTexts.MSG_STARTUP);

        // 已在服务器里开启本模块：先补一条服务器记录，否则这次断线无从重连
        if (inServer()) {
            recordCurrentServer();
            state = ReconnectState.READY;
        }
    }

    @Override
    protected void onDisable() {
        engine.reset();
        waitingStability = false;
        stableTicks = 0;
        testDisconnectTicks = -1;
        state = ReconnectState.IDLE;
    }

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(
            ClientEventType.TICK,
            ClientEventType.JOIN_SERVER,
            ClientEventType.SCREEN_OPEN
        );
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            case JOIN_SERVER -> onGameJoined();
            case SCREEN_OPEN -> onOpenScreen(event);
            default -> {
                // TICK 的推进统一走 onTick；其余事件本模块不关心
            }
        }
    }

    @Override
    public void onTick(Minecraft client) {
        tickTestDisconnect();

        // 倒计时必须在 player==null 检查之前驱动：断线之后玩家实体已经为 null
        engine.tick();

        // 倒计时到点、连接已经发起：状态从「等待重连」转为「正在连接」
        if (state == ReconnectState.WAITING && !engine.isScheduled()) {
            state = ReconnectState.CONNECTING;
        }

        if (!inServer()) {
            if (state == ReconnectState.READY) state = ReconnectState.IDLE;
            return;
        }

        // 中途开启模块 / 换了服务器：没有记录就补一条
        if (!engine.hasServer()) recordCurrentServer();
        if (state != ReconnectState.WAITING) state = ReconnectState.READY;

        tickStability();
    }

    // ── 事件处理 ──

    /** 进入服务器：记录服务器信息；若是重连回来的，进入稳定判定窗口并播报成功 */
    private void onGameJoined() {
        recordCurrentServer();
        if (engine.getReconnectAttempts() > 0) {
            waitingStability = true;
            stableTicks = 0;
            notify(ReconnectTexts.MSG_READY);
        }
        state = ReconnectState.READY;
    }

    private void onOpenScreen(ClientEvent event) {
        String screenClassName = event.payload();

        // 断线界面出现 = 掉线（被踢 / 服务器关闭 / 连接失败），这是唯一的调度入口
        if (DisconnectedScreen.class.getName().equals(screenClassName)) {
            handleDisconnect();
            return;
        }

        // 等待重连期间手动回到服务器列表 / 主菜单 → 按设置取消本次重连
        if (settings.cancelOnMenu
            && (JoinMultiplayerScreen.class.getName().equals(screenClassName)
                || TitleScreen.class.getName().equals(screenClassName))
            && engine.isScheduled()) {
            engine.cancelScheduled();
            state = ReconnectState.IDLE;
            notify(ReconnectTexts.MSG_MANUAL_CANCEL);
        }
    }

    /**
     * 断线处理：判定条件 → 调度重连 → 播报。
     *
     * <p>不订阅 {@code DISCONNECT}：那个事件在「玩家自己点断开连接」时同样会派发，用它触发重连会把
     * 「主动退出服务器」也连回去；断线界面（{@code DisconnectedScreen}）只在真的掉线时出现，
     * 与旧项目自动登入的判据一致。</p>
     */
    private void handleDisconnect() {
        // 已调度时不重复处理：断线界面可能被多个来源触发（连接失败会再次出现断线界面）
        if (state == ReconnectState.WAITING && engine.isScheduled()) return;

        // 主动断线（管理员检测的「立即断线」）：保命动作不允许被重连撤销
        if (ReconnectSuppression.suppressed()) {
            state = ReconnectState.IDLE;
            notify(ReconnectTexts.MSG_SUPPRESSED);
            return;
        }

        waitingStability = false;
        stableTicks = 0;

        // 两个模块都开时让路给自动登入，避免同一次断线连两遍（概览页显示谁在接管）
        if (autoLoginTakesOver()) {
            state = ReconnectState.IDLE;
            return;
        }
        if (!settings.enabled) {
            state = ReconnectState.IDLE;
            return;
        }
        if (!engine.hasServer()) {
            state = ReconnectState.IDLE;
            notify(ReconnectTexts.MSG_NO_SERVER);
            return;
        }
        if (!engine.startReconnect()) {
            state = ReconnectState.IDLE;
            notify(ReconnectTexts.MSG_GIVE_UP);
            return;
        }

        state = ReconnectState.WAITING;
        notify(ReconnectTexts.MSG_DISCONNECTED_PREFIX
            + (settings.delayTicks / TICKS_PER_SECOND)
            + ReconnectTexts.MSG_DISCONNECTED_SUFFIX);
    }

    // ── 内部 ──

    /** 每条重连发起时的播报（引擎回调） */
    private void onReconnectStart() {
        notify(ReconnectTexts.MSG_RECONNECTING_PREFIX + engine.getReconnectAttempts()
            + ReconnectTexts.MSG_RECONNECTING_SUFFIX);
    }

    /** 稳定判定：连上后连续 {@code stableTicks} 个 tick 都在服务器里，就把失败计数归零 */
    private void tickStability() {
        if (!waitingStability) return;
        if (++stableTicks < settings.stableTicks) return;
        waitingStability = false;
        stableTicks = 0;
        engine.markConnectionStable();
    }

    /** 测试断开倒计时（玩家点了「测试重连」之后才会走这条路） */
    private void tickTestDisconnect() {
        if (testDisconnectTicks < 0) return;
        if (--testDisconnectTicks > 0) return;
        testDisconnectTicks = -1;
        doTestDisconnect();
    }

    private void doTestDisconnect() {
        Component text = Component.literal(
            ClientChat.prefix(MESSAGE_MODULE) + " §f测试重连 §8▸ §e主动断开以验证自动重连");
        ConnectionCloser.disconnect(text);
    }

    /** 记录当前服务器（地址 + 服务器信息），供断线重连使用 */
    private void recordCurrentServer() {
        ServerData entry = mc.getCurrentServer();
        if (entry != null) {
            engine.recordServer(ServerAddress.parseString(entry.ip), entry);
            return;
        }
        if (mc.getConnection() != null && mc.getConnection().getServerData() != null) {
            // 直连场景（命令行 / 启动参数等）：从网络连接获取
            ServerData info = mc.getConnection().getServerData();
            engine.recordServer(ServerAddress.parseString(info.ip), info);
        }
    }

    private boolean inServer() {
        return mc.player != null && mc.level != null && !mc.hasSingleplayerServer();
    }

    /** 普通播报（前缀 + {@code §f} + 正文，与其余模块同一写法） */
    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, "§f" + message);
    }

    // ── 界面 ──

    /** 配置页 = 模块页 {@link ReconnectPage} + 整屏控制台（概览 / 重连设置） */
    @Override
    public ModulePage page() {
        return new ReconnectPage(this);
    }
}
