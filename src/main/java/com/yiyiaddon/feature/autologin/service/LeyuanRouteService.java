package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.model.LeyuanRouteState;
import com.yiyiaddon.feature.autologin.model.LeyuanWelcomeEntryMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * 自用回服路线：登录服菜单 → 欢迎页 → 主城大区 → 世界传送 → 目标子服，外加挂机区自动回服与
 * 断线后的异常恢复（逐字搬旧 {@code AutoLoginModule} 的 18 态状态机、主城菜单 Shift＋F 兜底、
 * 欢迎页 7×5 扫描、侧边栏确认与恢复退避）。
 *
 * <p><b>分层</b>：本类独占「自用路线」的全部成员（路线状态、每步计时、菜单指纹、扫描游标、
 * 兜底按键相位、挂机区等待、恢复状态），模块只做编排与播报转发；通用子服路线在
 * {@link SubserverRouteService}，文本识别在 {@link ServerTextKit}。</p>
 *
 * <p><b>用户可见文案逐字保留</b>：路线播报里的服务器称呼（如「乐源回服」「乐源城」）按第十六章
 * 第 96 条（旧项目播报属不可修改资产）一字未动，只有源码注释里的称呼改成「自用」，与
 * {@link LeyuanRouteState} 的登记口径一致。</p>
 *
 * <p><b>旧行为一律保留</b>：主城菜单连续两次打不开就停下并提示；单步超时即失败防误点；
 * 「主城菜单」兜底最多两次、退避 40 tick；欢迎页扫描按蛇形 7×5 网格逐点短按左键；
 * 挂机区识别与自动回服是两个独立开关；恢复期等待按「首次重试秒数 × 次数」线性退避并以
 * 「等待上限分钟」封顶。全部照旧，未做「顺手修」。</p>
 */
public final class LeyuanRouteService {

    /** 路线回调：播报与「到达目标」交给调用方，本类不碰模块状态 */
    public interface Listener {

        /** 播报一行（调用方负责加模块前缀与 {@code §f}） */
        void notify(String message);

        /** 调试播报（仅调试模式开启时调用方会真的输出） */
        void debug(String message);

        /** 到达目标区域（本类已完成状态转移，剩下的事由调用方做） */
        void onTargetAreaConfirmed(String reason);
    }

    /** 书本直达主城时要试的槽位（逐字 = 旧 {@code LEYUAN_DIRECT_MAIN_CITY_BOOK_SLOTS}） */
    private static final int[] DIRECT_MAIN_CITY_BOOK_SLOTS = {0, 8, 9, 17, 18, 26};

    /** 欢迎页扫描网格（逐字 = 旧实现的 7 列 5 行） */
    private static final int SCAN_COLUMNS = 7;
    private static final int SCAN_ROWS = 5;

    /** 「主城菜单连续两次没打开」的判定参数（逐字 = 旧实现的 2 次与 40 tick） */
    private static final int CITY_MENU_FALLBACK_LIMIT = 2;
    private static final int CITY_MENU_FALLBACK_BACKOFF = 40;

    private final Minecraft mc;
    private final AutoLoginSettings settings;
    private final Listener listener;

    // ── 路线状态 ──
    private LeyuanRouteState state = LeyuanRouteState.IDLE;
    private int stateTicks;
    private int actionTicks;
    private int lastScreenFingerprint;
    private int scanIndex;
    private int scanClickTicks;
    private int pendingMenuSlot = -1;
    private int menuUseCooldown;

    // ── 主城 Shift＋F 兜底 ──
    private int cityMenuFallbackReleaseTicks;
    private int cityMenuFallbackPhase;
    private int cityMenuFallbackAttempts;
    private int cityMenuFallbackRetryTick;

    // ── 欢迎页扫描与阶段起点 ──
    private float scanBaseYaw;
    private float scanBasePitch;
    private ClientLevel stageWorld;
    private String stageDimension = "";
    private Vec3 stagePosition;

    // ── 挂机区 ──
    private boolean afkAreaHandled;
    private int afkAreaWaitTicks;

    // ── 异常恢复 / 被踢重进 ──
    private boolean recoveryActive;
    private int recoveryElapsedTicks;
    private LeyuanRouteState recoveryResumeState = LeyuanRouteState.IDLE;
    private boolean reenterPending;

    public LeyuanRouteService(Minecraft mc, AutoLoginSettings settings, Listener listener) {
        this.mc = mc;
        this.settings = settings;
        this.listener = listener;
    }

    // ── 只读状态（控制台读它） ──

    /** 旧 {@code isLeyuanRouteRunning}：IDLE / COMPLETE / FAILED 之外都算在跑 */
    public boolean isRunning() {
        return state != LeyuanRouteState.IDLE
            && state != LeyuanRouteState.COMPLETE
            && state != LeyuanRouteState.FAILED;
    }

    /** 当前阶段 */
    public LeyuanRouteState state() {
        return state;
    }

    /** 当前阶段中文名（旧 {@code leyuanStateName}，超时播报与概览页共用） */
    public String stateName() {
        return switch (state) {
            case OPEN_LOGIN_MENU -> "打开登录服菜单";
            case CLICK_LOGIN_SURVIVAL -> "点击登录服生存服";
            case WAIT_WELCOME, SCAN_WELCOME -> "欢迎页扫描";
            case WAIT_MAIN_CITY, OPEN_CITY_MENU -> "等待并打开主城菜单";
            case OPEN_AFK_MENU -> "打开挂机区菜单";
            case CLICK_RETURN_MAIN_CITY_HALL -> "点击返回主城大厅";
            case WAIT_MAIN_CITY_HALL -> "等待主城大厅";
            case OPEN_MAIN_CITY_HALL_MENU -> "打开主城大厅菜单";
            case CLICK_WORLD_TRANSFER -> "点击世界传送";
            case CLICK_SURVIVAL_FIRST -> "点击第一层生存大区";
            case CLICK_SURVIVAL_SECOND -> "点击第二层生存大区";
            case CLICK_TARGET_SERVER, WAIT_TARGET -> "进入并确认目标子服";
            default -> state.toString();
        };
    }

    /** 挂机区是否已识别并启动过回服（防重复触发） */
    public boolean afkAreaHandled() {
        return afkAreaHandled;
    }

    /** 异常恢复是否在进行（控制台显示用） */
    public boolean recoveryActive() {
        return recoveryActive;
    }

    /** 本次恢复已等待的 tick */
    public int recoveryElapsedTicks() {
        return recoveryElapsedTicks;
    }

    /** 当前阶段已等待的 tick */
    public int stateTicks() {
        return stateTicks;
    }

    // ── 启动 / 停止 / 复位 ──

    /** 旧 {@code startLeyuanRoute}：总开关关着就只提示一句，不启动 */
    public void start() {
        if (!settings.leyuanEnabled) {
            listener.notify("已选择乐源服定制模式，但定制路线开关未开启。");
            return;
        }
        lastScreenFingerprint = 0;
        scanIndex = 0;
        scanClickTicks = 0;
        pendingMenuSlot = -1;
        menuUseCooldown = 0;
        cityMenuFallbackReleaseTicks = 0;
        cityMenuFallbackPhase = 0;
        cityMenuFallbackAttempts = 0;
        cityMenuFallbackRetryTick = 0;
        captureStageOrigin();
        setState(LeyuanRouteState.OPEN_LOGIN_MENU, settings.leyuanStepDelay);
        notifyProgress(1, "已启动，准备打开登录服菜单。");
    }

    /** 旧 {@code stopLeyuanRoute}：先把兜底按键全松开再清状态 */
    public void stop() {
        if (!isRunning() && cityMenuFallbackPhase == 0) return;
        releaseShortcutKeys();
        state = LeyuanRouteState.IDLE;
        stateTicks = 0;
        actionTicks = 0;
        menuUseCooldown = 0;
        pendingMenuSlot = -1;
        cityMenuFallbackAttempts = 0;
        cityMenuFallbackRetryTick = 0;
    }

    /** 旧 {@code resetSubserverWorkflow} 里属于自用路线的那部分 */
    public void reset() {
        state = LeyuanRouteState.IDLE;
        stateTicks = 0;
        actionTicks = 0;
        lastScreenFingerprint = 0;
        scanIndex = 0;
        scanClickTicks = 0;
        pendingMenuSlot = -1;
        menuUseCooldown = 0;
        cityMenuFallbackReleaseTicks = 0;
        cityMenuFallbackPhase = 0;
        cityMenuFallbackAttempts = 0;
        cityMenuFallbackRetryTick = 0;
        stageWorld = null;
        stageDimension = "";
        stagePosition = null;
        afkAreaHandled = false;
        afkAreaWaitTicks = 0;
    }

    // ── 每刻推进 ──

    /** 旧 {@code tickLeyuanRoute}：门控 → 兜底失败判定 → 单步超时 → 菜单未打开回退 → 18 态分派 */
    public void tick() {
        if (!isRunning()) return;
        if (!canRunRoute()) {
            stop();
            return;
        }
        boolean menuState = state == LeyuanRouteState.OPEN_CITY_MENU
            || state == LeyuanRouteState.OPEN_AFK_MENU
            || state == LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU;
        if (menuState && cityMenuFallbackAttempts >= CITY_MENU_FALLBACK_LIMIT
            && cityMenuFallbackPhase == 0 && stateTicks >= cityMenuFallbackRetryTick + CITY_MENU_FALLBACK_BACKOFF) {
            state = LeyuanRouteState.FAILED;
            listener.notify("主城菜单连续两次没打开，已停止操作。请确认 Shift＋F 是否仍是服务器快捷键。");
            return;
        }
        if (++stateTicks >= settings.leyuanStepTimeout) {
            String stage = stateName();
            state = LeyuanRouteState.FAILED;
            listener.notify("等了太久还没完成“" + stage + "”，已停止操作，防止误点。");
            return;
        }
        if (!menuOpen() && stateTicks > settings.leyuanStepDelay + 20) {
            if (state == LeyuanRouteState.CLICK_LOGIN_SURVIVAL) {
                setState(LeyuanRouteState.OPEN_LOGIN_MENU, settings.leyuanStepDelay);
                return;
            }
            if (state == LeyuanRouteState.CLICK_RETURN_MAIN_CITY_HALL) {
                setState(LeyuanRouteState.OPEN_AFK_MENU, settings.leyuanStepDelay);
                return;
            }
            if (state == LeyuanRouteState.CLICK_WORLD_TRANSFER
                || state == LeyuanRouteState.CLICK_SURVIVAL_FIRST
                || state == LeyuanRouteState.CLICK_SURVIVAL_SECOND
                || state == LeyuanRouteState.CLICK_TARGET_SERVER) {
                setState(afkAreaHandled
                    ? LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU
                    : LeyuanRouteState.OPEN_CITY_MENU, settings.leyuanStepDelay);
                return;
            }
        }
        if (actionTicks > 0 && --actionTicks > 0) return;

        switch (state) {
            case OPEN_LOGIN_MENU -> {
                if (openMenu()) {
                    lastScreenFingerprint = 0;
                    if (settings.leyuanWelcomeEntryMode == LeyuanWelcomeEntryMode.BOOK_DIRECT) {
                        setState(LeyuanRouteState.SCAN_WELCOME, settings.leyuanStepDelay);
                        notifyProgress(2, "已打开登录服菜单，正在寻找"
                            + AutoLoginStyle.server("直达主城书本") + "。");
                    } else if (recoveryResumeState == LeyuanRouteState.CLICK_LOGIN_SURVIVAL) {
                        setState(LeyuanRouteState.CLICK_LOGIN_SURVIVAL, settings.leyuanStepDelay);
                    } else {
                        setState(LeyuanRouteState.CLICK_LOGIN_SURVIVAL, settings.leyuanStepDelay);
                        listener.notify("乐源路线已打开登录服菜单。");
                    }
                }
            }
            case CLICK_LOGIN_SURVIVAL -> {
                if (settings.leyuanWelcomeEntryMode == LeyuanWelcomeEntryMode.BOOK_DIRECT) {
                    lastScreenFingerprint = 0;
                    setState(LeyuanRouteState.SCAN_WELCOME, 0);
                } else if (clickMenuKeyword(settings.leyuanLoginSurvivalKeyword)) {
                    captureStageOrigin();
                    setState(LeyuanRouteState.WAIT_WELCOME, 40);
                    notifyProgress(2, "已点击登录服“"
                        + AutoLoginStyle.server(settings.leyuanLoginSurvivalKeyword) + "”，等待欢迎页。");
                }
            }
            case WAIT_WELCOME -> {
                if (settings.leyuanWelcomeEntryMode == LeyuanWelcomeEntryMode.BOOK_DIRECT) {
                    lastScreenFingerprint = 0;
                    setState(LeyuanRouteState.SCAN_WELCOME, 0);
                } else if (!menuOpen()) {
                    captureStageOrigin();
                    scanBaseYaw = mc.player.getYRot();
                    scanBasePitch = mc.player.getXRot();
                    scanIndex = 33;
                    scanClickTicks = 0;
                    setState(LeyuanRouteState.SCAN_WELCOME, 0);
                    notifyProgress(3, "正在寻找" + AutoLoginStyle.server("乐源城") + "入口。");
                }
            }
            case SCAN_WELCOME -> tickWelcomeScan();
            case WAIT_MAIN_CITY -> {
                if (reachedMainCity()) {
                    setState(LeyuanRouteState.OPEN_CITY_MENU, settings.leyuanStepDelay);
                    notifyProgress(4, "已到达" + AutoLoginStyle.location("主城大区") + "，准备打开菜单。");
                }
            }
            case OPEN_AFK_MENU -> {
                tickCityMenuFallback();
                if (openMenu()) {
                    setState(LeyuanRouteState.CLICK_RETURN_MAIN_CITY_HALL, settings.leyuanStepDelay);
                    notifyProgress(1, "已打开挂机区菜单，正在点击返回主城大厅。");
                }
            }
            case CLICK_RETURN_MAIN_CITY_HALL -> {
                if (clickMenuKeywords(settings.leyuanReturnMainCityKeywords)) {
                    captureStageOrigin();
                    setState(LeyuanRouteState.WAIT_MAIN_CITY_HALL, settings.leyuanStepDelay);
                    notifyProgress(2, "已点击返回主城大厅，等待到达主城。");
                }
            }
            case WAIT_MAIN_CITY_HALL -> {
                if (reachedMainCity()) {
                    setState(LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU, settings.leyuanStepDelay);
                    cityMenuFallbackAttempts = 0;
                    cityMenuFallbackRetryTick = 0;
                    notifyProgress(3, "已到达主城大厅，准备再次打开菜单。");
                }
            }
            case OPEN_MAIN_CITY_HALL_MENU -> {
                tickCityMenuFallback();
                if (openMenu()) {
                    setState(nextClickStateAfterMenu(), settings.leyuanStepDelay);
                    notifyProgress(4, "已打开主城大厅菜单，正在点击世界传送。");
                }
            }
            case OPEN_CITY_MENU -> {
                tickCityMenuFallback();
                if (openMenu()) {
                    setState(nextClickStateAfterMenu(), settings.leyuanStepDelay);
                    notifyProgress(5, "已打开主城菜单，正在前往目标子服。");
                }
            }
            case CLICK_WORLD_TRANSFER -> advanceMenuStep(
                settings.leyuanWorldTransferKeyword, LeyuanRouteState.CLICK_SURVIVAL_FIRST, "世界传送");
            case CLICK_SURVIVAL_FIRST -> advanceMenuStep(
                settings.leyuanSurvivalFirstKeyword, LeyuanRouteState.CLICK_SURVIVAL_SECOND, "第一层生存大区");
            case CLICK_SURVIVAL_SECOND -> advanceMenuStep(
                settings.leyuanSurvivalSecondKeyword, LeyuanRouteState.CLICK_TARGET_SERVER, "第二层生存大区");
            case CLICK_TARGET_SERVER -> {
                if (clickMenuKeyword(settings.leyuanTargetServerKeyword)) {
                    captureStageOrigin();
                    setState(LeyuanRouteState.WAIT_TARGET, settings.leyuanStepDelay);
                    notifyProgress(6, "已点击最终目标“"
                        + AutoLoginStyle.text(settings.leyuanTargetServerKeyword) + "”，正在确认到达。");
                }
            }
            case WAIT_TARGET -> {
                String keyword = findTargetKeyword();
                if (keyword != null) {
                    state = LeyuanRouteState.COMPLETE;
                    listener.onTargetAreaConfirmed("乐源服路线已识别 " + AutoLoginStyle.text(keyword));
                }
            }
            default -> {
            }
        }
    }

    /**
     * 旧 {@code onGameJoined} 里自用路线的那一段：路线在跑时恢复当前步骤（换服时菜单相位要重来）。
     *
     * @return 是否已按「路线恢复」处理（true 时调用方不再走常规进服流程）
     */
    public boolean onGameJoined() {
        if (!isRunning()) return false;
        recoveryActive = false;
        recoveryElapsedTicks = 0;
        if (state == LeyuanRouteState.CLICK_LOGIN_SURVIVAL) {
            setState(LeyuanRouteState.OPEN_LOGIN_MENU, settings.leyuanStepDelay);
        } else if (state == LeyuanRouteState.CLICK_RETURN_MAIN_CITY_HALL) {
            setState(LeyuanRouteState.OPEN_AFK_MENU, settings.leyuanStepDelay);
        } else if (state == LeyuanRouteState.CLICK_WORLD_TRANSFER
            || state == LeyuanRouteState.CLICK_SURVIVAL_FIRST
            || state == LeyuanRouteState.CLICK_SURVIVAL_SECOND
            || state == LeyuanRouteState.CLICK_TARGET_SERVER) {
            setState(afkAreaHandled
                ? LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU
                : LeyuanRouteState.OPEN_CITY_MENU, settings.leyuanStepDelay);
        }
        lastScreenFingerprint = 0;
        menuUseCooldown = 0;
        listener.debug("乐源路线已恢复当前步骤：" + state);
        markTransition();
        return true;
    }

    /** 旧 {@code markLeyuanTransition}：扫描途中换服则直接进入等待主城 */
    public void markTransition() {
        if (state == LeyuanRouteState.SCAN_WELCOME) {
            setState(LeyuanRouteState.WAIT_MAIN_CITY, settings.leyuanStepDelay);
        }
    }

    /**
     * 旧 {@code leyuanReenterPending} 的消费：路线完成后被踢出（如 out_of_order_chat），
     * 重连回登录服需要重新从头回服。
     *
     * @return true = 已重新启动路线，调用方不再走常规进服流程
     */
    public boolean consumeReenterPending() {
        if (!reenterPending) return false;
        reenterPending = false;
        start();
        return true;
    }

    /** 断线时登记「重连后需重新回服」（仅路线未在跑时置位，逐字 = 旧 {@code handleDisconnect}） */
    public void markReenterPending() {
        reenterPending = settings.usesLeyuanRoute();
    }

    /** 模块关闭时清掉「重连后需重新回服」（逐字 = 旧 {@code onDeactivate} 的 {@code leyuanReenterPending = false}） */
    public void clearReenterPending() {
        reenterPending = false;
    }

    // ── 异常恢复 ──

    /** 旧 {@code handleDisconnect} 首段：路线在跑则记录恢复起点，返回「是否处于恢复」 */
    public boolean beginRecovery() {
        boolean recovering = isRunning();
        if (recovering) {
            recoveryResumeState = state;
            if (!recoveryActive) recoveryElapsedTicks = 0;
        }
        return recovering;
    }

    /** 旧 {@code handleDisconnect}：恢复期间置活 */
    public void activateRecovery() {
        recoveryActive = true;
        recoveryElapsedTicks = 0;
    }

    /** 旧 {@code handleDisconnect} 里重连未调度成功时的收尾 */
    public void failRecovery() {
        recoveryActive = false;
        state = LeyuanRouteState.FAILED;
    }

    /** 旧 {@code leyuanRecoveryDelayTicks}：首次重试秒数 × 次数，按等待上限封顶 */
    public int recoveryDelayTicks(int reconnectAttempts) {
        int attempt = Math.max(1, reconnectAttempts);
        int base = settings.leyuanRecoveryRetrySeconds * 20;
        int maxWait = settings.leyuanRecoveryWaitMinutes * 60 * 20;
        return Math.min(maxWait, Math.max(base, base * attempt));
    }

    /**
     * 旧 {@code onTick} 首段的恢复看门狗：超过等待上限则判定当前步骤失败。
     *
     * @return true = 已超时（调用方需复位重连状态并回到 IDLE）
     */
    public boolean tickRecoveryWatchdog() {
        if (!recoveryActive) return false;
        if (++recoveryElapsedTicks < settings.leyuanRecoveryWaitMinutes * 60 * 20) return false;
        recoveryActive = false;
        state = LeyuanRouteState.FAILED;
        listener.notify("乐源路线异常恢复等待已超过设置上限，当前步骤失败。");
        listener.debug("乐源路线异常恢复超时：步骤=" + state);
        return true;
    }

    // ── 挂机区自动回服 ──

    /**
     * 旧 {@code tickLeyuanAfkRecoveryTrigger}：侧边栏命中挂机区关键词后计时，到点启动回服。
     *
     * @param sessionActive 主状态机是否已就绪（旧判据 {@code state == ACTIVE}）
     */
    public void tickAfkRecoveryTrigger(BooleanSupplier sessionActive) {
        if (!isAfkRecoveryEnabled()) return;
        boolean inAfkArea = ServerTextKit.findSidebarKeyword(mc, settings.leyuanAfkAreaKeywords) != null;
        int requiredWaitTicks = Math.max(0, settings.leyuanAfkMenuDelayMinutes) * 60 * 20;
        if (!inAfkArea) {
            afkAreaWaitTicks = 0;
            afkAreaHandled = false;
            return;
        }
        if (afkAreaHandled || isRunning() || !sessionActive.getAsBoolean()) return;
        if (afkAreaWaitTicks < requiredWaitTicks) {
            afkAreaWaitTicks++;
            return;
        }
        afkAreaHandled = true;
        lastScreenFingerprint = 0;
        menuUseCooldown = 0;
        cityMenuFallbackAttempts = 0;
        cityMenuFallbackRetryTick = 0;
        cityMenuFallbackPhase = 0;
        setState(LeyuanRouteState.OPEN_AFK_MENU, settings.leyuanStepDelay);
        listener.notify("检测到" + AutoLoginStyle.location("挂机区") + "，开始执行乐源自动回服路线。");
    }

    /** 旧 {@code isLeyuanAfkRecoveryEnabled} */
    private boolean isAfkRecoveryEnabled() {
        return settings.usesLeyuanMode() && settings.leyuanAfkRecoveryEnabled;
    }

    // ── 菜单步骤 ──

    /** 旧 {@code advanceLeyuanMenuStep}：点中才推进，并按标签挑高亮色 */
    private void advanceMenuStep(String keyword, LeyuanRouteState next, String label) {
        if (!clickMenuKeyword(keyword)) return;
        setState(next, settings.leyuanStepDelay);
        String emphasized = label.contains("主城")
            ? AutoLoginStyle.location(keyword)
            : label.contains("登录") ? AutoLoginStyle.server(keyword) : AutoLoginStyle.text(keyword);
        listener.notify("乐源路线已点击" + label + "“" + emphasized + "”。");
    }

    /** 旧 {@code nextClickState} 的分支：恢复时从恢复点继续，否则从世界传送开始 */
    private LeyuanRouteState nextClickStateAfterMenu() {
        if (recoveryResumeState == LeyuanRouteState.CLICK_SURVIVAL_FIRST) return LeyuanRouteState.CLICK_SURVIVAL_FIRST;
        if (recoveryResumeState == LeyuanRouteState.CLICK_SURVIVAL_SECOND) return LeyuanRouteState.CLICK_SURVIVAL_SECOND;
        if (recoveryResumeState == LeyuanRouteState.CLICK_TARGET_SERVER) return LeyuanRouteState.CLICK_TARGET_SERVER;
        return LeyuanRouteState.CLICK_WORLD_TRANSFER;
    }

    /** 旧 {@code openLeyuanMenu}：切到菜单物品槽 → 主城三步先朝下看 → 右键使用 */
    private boolean openMenu() {
        if (!canRunMenu()) return false;
        if (menuOpen()) {
            menuUseCooldown = 0;
            lastScreenFingerprint = 0;
            return true;
        }
        if (mc.screen != null || mc.gameMode == null || mc.getConnection() == null || mc.player == null) return false;
        if (menuUseCooldown > 0) {
            menuUseCooldown--;
            return false;
        }
        int slot = findMenuItemSlot();
        if (slot < 0) return false;
        if (mc.player.getInventory().getSelectedSlot() != slot) {
            mc.player.getInventory().setSelectedSlot(slot);
            pendingMenuSlot = slot;
            menuUseCooldown = 2;
            return false;
        }
        if (state == LeyuanRouteState.OPEN_CITY_MENU
            || state == LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU
            || state == LeyuanRouteState.OPEN_AFK_MENU) {
            float yaw = mc.player.getYRot();
            float pitch = -75.0f;
            mc.player.setXRot(pitch);
            ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision);
        }
        mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
        pendingMenuSlot = -1;
        menuUseCooldown = 10;
        return false;
    }

    /** 旧 {@code tickLeyuanCityMenuFallback}：识别不到钟或右键没反应时模拟 Shift＋F（四相位） */
    private void tickCityMenuFallback() {
        if (!canRunMenu()) {
            releaseShortcutKeys();
            return;
        }
        if (cityMenuFallbackPhase == 1) {
            if (--cityMenuFallbackReleaseTicks > 0) return;
            KeyboardSimulator.pressFWithShift();
            cityMenuFallbackPhase = 2;
            return;
        }
        if (cityMenuFallbackPhase == 2) {
            KeyboardSimulator.releaseFWithShift();
            cityMenuFallbackPhase = 3;
            return;
        }
        if (cityMenuFallbackPhase == 3) {
            KeyboardSimulator.releaseShift();
            cityMenuFallbackPhase = 0;
            cityMenuFallbackAttempts++;
            cityMenuFallbackRetryTick = stateTicks + CITY_MENU_FALLBACK_BACKOFF;
            return;
        }
        boolean hasClock = findMenuItemSlot() >= 0;
        if (!settings.leyuanCityMenuFallback || menuOpen()
            || cityMenuFallbackAttempts >= CITY_MENU_FALLBACK_LIMIT
            || (hasClock && stateTicks < settings.leyuanCityMenuFallbackDelay)
            || (cityMenuFallbackAttempts > 0 && stateTicks < cityMenuFallbackRetryTick)) {
            return;
        }
        KeyboardSimulator.pressShift();
        cityMenuFallbackPhase = 1;
        cityMenuFallbackReleaseTicks = 2;
        listener.notify(hasClock
            ? "主城钟未打开，正在尝试 Shift＋F 快捷菜单（第 " + (cityMenuFallbackAttempts + 1) + " 次）。"
            : "主城未识别到钟，正在尝试 Shift＋F 快捷菜单（第 " + (cityMenuFallbackAttempts + 1) + " 次）。");
    }

    /** 旧 {@code releaseLeyuanShortcutKeys}：两处按键状态位与四相位兜底全部松开 */
    public void releaseShortcutKeys() {
        KeyboardSimulator.releaseF();
        KeyboardSimulator.releaseShiftBare();
        if (cityMenuFallbackPhase != 0) {
            KeyboardSimulator.releaseF();
            KeyboardSimulator.releaseShift();
        }
        cityMenuFallbackPhase = 0;
        cityMenuFallbackReleaseTicks = 0;
    }

    /** 旧 {@code findLeyuanMenuItemSlot}：先认自用菜单工具物品，再认关键词 */
    private int findMenuItemSlot() {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(settings.leyuanMenuTool())) return i;
            for (String keyword : settings.leyuanMenuItemKeywords) {
                if (ServerTextKit.matchesKeyword(stack, keyword)) return i;
            }
        }
        return -1;
    }

    /** 旧 {@code clickLeyuanMenuKeyword} */
    private boolean clickMenuKeyword(String keyword) {
        return clickMenuKeywords(List.of(keyword));
    }

    /** 旧 {@code clickLeyuanMenuKeywords}：同一帧菜单指纹只点一次，跳过玩家背包槽 */
    private boolean clickMenuKeywords(List<String> keywords) {
        if (!canRunMenu()) return false;
        AbstractContainerMenu handler = activeMenu();
        if (handler == null) return false;
        int fingerprint = menuFingerprint(handler);
        if (fingerprint == lastScreenFingerprint) return false;
        for (int i = 0; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.container == mc.player.getInventory()
                || !ServerTextKit.matchesAnyKeyword(slot.getItem(), keywords)) {
                continue;
            }
            mc.gameMode.handleContainerInput(handler.containerId, i, 0, ContainerInput.PICKUP, mc.player);
            lastScreenFingerprint = fingerprint;
            return true;
        }
        return false;
    }

    /** 旧 {@code clickLeyuanDirectMainCityBook}：只认「无名知识之书」（书本直达主城入口） */
    private boolean clickDirectMainCityBook() {
        if (settings.leyuanWelcomeEntryMode != LeyuanWelcomeEntryMode.BOOK_DIRECT) return false;
        AbstractContainerMenu handler = activeMenu();
        if (handler == null) return false;
        int fingerprint = menuFingerprint(handler);
        if (fingerprint == lastScreenFingerprint) return false;

        for (int slotIndex : DIRECT_MAIN_CITY_BOOK_SLOTS) {
            if (slotIndex >= handler.slots.size()) continue;
            Slot slot = handler.getSlot(slotIndex);
            ItemStack stack = slot.getItem();
            if (slot.container == mc.player.getInventory() || stack.isEmpty()
                || !stack.is(Items.KNOWLEDGE_BOOK)
                || !ServerTextKit.normalizeForMatch(stack.getHoverName().getString()).isEmpty()) {
                continue;
            }
            mc.gameMode.handleContainerInput(handler.containerId, slotIndex, 0, ContainerInput.PICKUP, mc.player);
            lastScreenFingerprint = fingerprint;
            return true;
        }
        return false;
    }

    /** 旧 {@code leyuanMenuFingerprint}：容器 id + 每个非背包槽的名称与数量 */
    private int menuFingerprint(AbstractContainerMenu handler) {
        int result = 31 * handler.containerId;
        for (Slot slot : handler.slots) {
            if (slot.container == mc.player.getInventory()) continue;
            result = 31 * result + ServerTextKit.normalizeForMatch(slot.getItem().getHoverName().getString()).hashCode();
            result = 31 * result + slot.getItem().getCount();
        }
        return result;
    }

    // ── 欢迎页 ──

    /** 旧 {@code tickLeyuanWelcomeScan}：直达书本优先；否则按蛇形网格逐点转身并短按左键 */
    private void tickWelcomeScan() {
        if (reachedMainCity()) {
            setState(LeyuanRouteState.OPEN_CITY_MENU, settings.leyuanStepDelay);
            listener.notify("已确认到达" + AutoLoginStyle.location("主城大区") + "，继续执行路线。");
            return;
        }
        if (settings.leyuanWelcomeEntryMode == LeyuanWelcomeEntryMode.BOOK_DIRECT) {
            if (clickDirectMainCityBook()) {
                captureStageOrigin();
                setState(LeyuanRouteState.WAIT_MAIN_CITY, settings.leyuanStepDelay);
                notifyProgress(4, "已点击" + AutoLoginStyle.server("直达主城书本")
                    + "，正在进入" + AutoLoginStyle.location("主城大区") + "。");
            }
            return;
        }
        if (mc.screen != null || mc.getConnection() == null || mc.player == null) return;
        if (scanClickTicks > 0) {
            scanClickTicks--;
            if (scanClickTicks == 1) performLeftClick();
            return;
        }

        int total = SCAN_COLUMNS * SCAN_ROWS;
        int point = scanIndex % total;
        int row = point / SCAN_COLUMNS;
        int rawColumn = point % SCAN_COLUMNS;
        int column = row % 2 == 0 ? rawColumn : SCAN_COLUMNS - 1 - rawColumn;
        float yaw = scanBaseYaw + (float) (-35.0 + 70.0 * column / (SCAN_COLUMNS - 1));
        float pitch = Math.max(-90.0f, Math.min(90.0f,
            scanBasePitch + (float) (-22.5 + 45.0 * row / (SCAN_ROWS - 1))));
        mc.player.setYRot(yaw);
        mc.player.setYHeadRot(yaw);
        mc.player.setXRot(pitch);
        ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision);
        scanIndex++;
        scanClickTicks = 2;
    }

    /** 旧 {@code performLeyuanLeftClick}：命中实体就打、命中方块就挖，随后摆手臂 */
    private void performLeftClick() {
        if (mc.player == null || mc.gameMode == null) return;
        if (mc.hitResult instanceof EntityHitResult hit) {
            Entity entity = hit.getEntity();
            if (entity != null) mc.gameMode.attack(mc.player, entity);
        } else if (mc.hitResult instanceof BlockHitResult hit) {
            mc.gameMode.startDestroyBlock(hit.getBlockPos(), hit.getDirection());
        }
        mc.player.swing(InteractionHand.MAIN_HAND);
    }

    // ── 判据 ──

    /** 旧 {@code leyuanReachedMainCity} */
    private boolean reachedMainCity() {
        return ServerTextKit.findSidebarKeyword(mc, settings.leyuanMainCityKeywords) != null;
    }

    /** 旧 {@code findLeyuanTargetKeyword}：先看最终目标按钮关键词，再看到达确认关键词表 */
    private String findTargetKeyword() {
        String target = settings.leyuanTargetServerKeyword.trim();
        if (!target.isEmpty() && ServerTextKit.findSidebarKeyword(mc, List.of(target)) != null) return target;
        return ServerTextKit.findSidebarKeyword(mc, settings.leyuanTargetAreaKeywords);
    }

    /** 旧 {@code canRunLeyuanRoute} */
    private boolean canRunRoute() {
        return settings.usesLeyuanMode() && (settings.leyuanEnabled || settings.leyuanAfkRecoveryEnabled);
    }

    /** 旧 {@code canRunLeyuanMenu}：挂机区回服的四步在不看总开关时也要能开菜单 */
    private boolean canRunMenu() {
        return settings.usesLeyuanMode() && (settings.leyuanEnabled
            || state == LeyuanRouteState.OPEN_AFK_MENU
            || state == LeyuanRouteState.CLICK_RETURN_MAIN_CITY_HALL
            || state == LeyuanRouteState.WAIT_MAIN_CITY_HALL
            || state == LeyuanRouteState.OPEN_MAIN_CITY_HALL_MENU
            || state == LeyuanRouteState.CLICK_WORLD_TRANSFER
            || state == LeyuanRouteState.CLICK_SURVIVAL_FIRST
            || state == LeyuanRouteState.CLICK_SURVIVAL_SECOND
            || state == LeyuanRouteState.CLICK_TARGET_SERVER);
    }

    /** 旧 {@code leyuanMenuOpen}：静默看 containerMenu，失焦也有效 */
    private boolean menuOpen() {
        return mc.player != null && mc.player.containerMenu != null
            && mc.player.containerMenu != mc.player.inventoryMenu;
    }

    /** 旧 {@code leyuanActiveMenu}：先静默容器，再屏幕容器 */
    private AbstractContainerMenu activeMenu() {
        if (mc.player == null || mc.gameMode == null) return null;
        if (mc.player.containerMenu != null && mc.player.containerMenu != mc.player.inventoryMenu) {
            return mc.player.containerMenu;
        }
        if (mc.screen instanceof net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<?> screen) {
            return screen.getMenu();
        }
        return null;
    }

    // ── 内部 ──

    /** 旧 {@code captureLeyuanStageOrigin} */
    private void captureStageOrigin() {
        stageWorld = mc.level;
        stageDimension = mc.level == null ? "" : mc.level.dimension().identifier().toString();
        stagePosition = mc.player == null ? null
            : new Vec3(mc.player.getX(), mc.player.getY(), mc.player.getZ());
    }

    /** 旧 {@code setLeyuanState} */
    private void setState(LeyuanRouteState next, int delay) {
        state = next;
        stateTicks = 0;
        actionTicks = Math.max(0, delay);
    }

    /** 旧 {@code notifyLeyuanProgress}：六步进度条文案逐字保留 */
    private void notifyProgress(int step, String message) {
        listener.notify("§d§l乐源回服 §r§8[§f§l" + step + "§r§8/6] §r§f§l" + message);
    }
}
