package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.model.ServerEntryMode;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 通用子服路线：用快捷栏菜单物品打开服务器菜单，按「菜单点击顺序」逐项点进目标区域，
 * 再用侧边栏关键词确认到达（逐字搬旧 {@code AutoLoginModule} 的
 * {@code startSubserverWorkflow / tickSubserverWorkflow / findMenuItemSlot /
 * stopStandardMenuRoute / tickTargetAreaDetection / confirmTargetArea}）。
 *
 * <p><b>分层</b>（第 48 条）：本类只负责「菜单路线 + 到达确认」这一段流程与它自己的计时器，
 * 认证流程、断线重连、自用路线都不在这里；播报与「到达后执行指令」的调度由调用方
 * （{@code AutoLoginModule}）经 {@link Listener} 承担，本类不直接碰模块状态。</p>
 *
 * <p><b>与旧实现逐项对齐</b>：超时判据（{@code menuTimeout}）、每步等待（{@code menuActionDelay}）、
 * 菜单物品判定（物品本身或关键词命中名称 / Lore）、跳过玩家背包槽、最后一步在「子服网络」模式下
 * 置「预期换服」标志、必须先离开玩家背包界面再点、到达确认的两条判据
 * （侧边栏命中 / 未要求关键词时以世界或维度变化为准）—— 全部照旧，未做任何「顺手优化」。</p>
 *
 * <p><b>成员 {@code transitionExpected} 的归属</b>：它在路线内部置位，却被模块的进服回调消费，
 * 因此按唯一所有权放在本类，模块只经 {@link #transitionExpected()} / {@link #clearTransitionExpected()}
 * 读写，避免同一标志两处各存一份（第 169 条）。</p>
 */
public final class SubserverRouteService {

    /** 路线回调：本类不持有模块状态，播报与后续动作由调用方决定 */
    public interface Listener {

        /** 播报一行（调用方负责加模块前缀与 {@code §f}） */
        void notify(String message);

        /** 调试播报（仅调试模式开启时调用方会真的输出） */
        void debug(String message);

        /** 到达目标区域（本类已清掉自己的等待标志，剩下的事由调用方做） */
        void onTargetAreaConfirmed(String reason);
    }

    private final Minecraft mc;
    private final AutoLoginSettings settings;
    private final Listener listener;

    /** 菜单点击流程是否在跑（旧 {@code subserverWorkflowRunning}） */
    private boolean running;
    /** 当前点击到第几步（-1 = 还没使用菜单物品，旧 {@code subserverMenuStep}） */
    private int menuStep = -1;
    /** 当前步骤的剩余等待 tick（旧 {@code subserverActionTicks}） */
    private int actionTicks;
    /** 流程 / 到达确认共用的超时计数（旧 {@code subserverTimeoutTicks}） */
    private int timeoutTicks;
    /** 是否在等目标区域确认（旧 {@code waitingForTargetArea}） */
    private boolean waitingForTargetArea;
    /** 是否观察到世界 / 维度变化（旧 {@code targetTransitionObserved}） */
    private boolean targetTransitionObserved;
    /** 是否预期服务器会换连接（旧 {@code subserverTransitionExpected}） */
    private boolean transitionExpected;

    /** 出发时的世界与维度，用于识别「已经换服」（旧 {@code routeStartWorld} / {@code routeStartDimension}） */
    private ClientLevel routeStartWorld;
    private String routeStartDimension = "";

    public SubserverRouteService(Minecraft mc, AutoLoginSettings settings, Listener listener) {
        this.mc = mc;
        this.settings = settings;
        this.listener = listener;
    }

    // ── 只读状态（控制台读它，不另建第二份） ──

    /** 菜单点击流程是否在跑 */
    public boolean isRunning() {
        return running;
    }

    /** 是否在等目标区域确认 */
    public boolean isWaitingForTargetArea() {
        return waitingForTargetArea;
    }

    /** 是否预期换服（模块的进服回调消费） */
    public boolean transitionExpected() {
        return transitionExpected;
    }

    /** 清掉「预期换服」标志（模块的进服回调消费后调用） */
    public void clearTransitionExpected() {
        transitionExpected = false;
    }

    /** 路线超时计数（控制台显示用） */
    public int timeoutTicks() {
        return timeoutTicks;
    }

    // ── 启动 / 停止 / 复位 ──

    /** 旧 {@code startSubserverWorkflow}：按进入方式分流（自用配置转交自用路线，直接进入不启动） */
    public void start() {
        if (!settings.autoEnterSubserver || settings.serverEntryMode == ServerEntryMode.DIRECT) return;
        if (settings.serverEntryMode == ServerEntryMode.LEYUAN_CUSTOM) return;   // 由模块转交自用路线
        if (settings.menuClickSteps.isEmpty()) return;
        running = true;
        menuStep = -1;
        actionTicks = settings.menuActionDelay;
        timeoutTicks = 0;
        routeStartWorld = mc.level;
        routeStartDimension = mc.level == null ? "" : mc.level.dimension().identifier().toString();
        targetTransitionObserved = false;
        listener.notify("大厅认证完成，准备自动打开子服菜单。");
    }

    /** 旧 {@code stopStandardMenuRoute}：条件不满足时静默停下（没有任何等待时不做任何事） */
    public void stop() {
        if (!running && !waitingForTargetArea && !transitionExpected) return;
        running = false;
        menuStep = -1;
        actionTicks = 0;
        timeoutTicks = 0;
        waitingForTargetArea = false;
        targetTransitionObserved = false;
        transitionExpected = false;
    }

    /** 旧 {@code resetSubserverWorkflow} 里属于通用子服路线的那部分（自用路线的复位由自用服务自己做） */
    public void reset() {
        running = false;
        menuStep = -1;
        actionTicks = 0;
        timeoutTicks = 0;
        waitingForTargetArea = false;
        targetTransitionObserved = false;
        transitionExpected = false;
        routeStartWorld = null;
        routeStartDimension = "";
    }

    // ── 每刻推进 ──

    /** 旧 {@code tickSubserverWorkflow}：逐项对齐（超时 → 等待 → 打开菜单 → 按顺序点步骤） */
    public void tick() {
        if (!running || !settings.usesStandardMenuRoute()) return;
        if (++timeoutTicks >= settings.menuTimeout) {
            listener.notify("等待子服菜单超时，已停止自动点击。");
            running = false;
            return;
        }
        if (actionTicks > 0 && --actionTicks > 0) return;

        if (menuStep < 0) {
            int slot = findMenuItemSlot();
            if (slot < 0 || mc.gameMode == null || mc.player == null) return;
            mc.player.getInventory().setSelectedSlot(slot);
            // 打点「我方刚开菜单」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
            SilentContainer.markOwnContainerOpen();
            mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
            menuStep = 0;
            actionTicks = settings.menuActionDelay;
            timeoutTicks = 0;
            listener.notify("已使用菜单物品，等待服务器选择界面。");
            return;
        }

        if (menuStep >= settings.menuClickSteps.size()) {
            running = false;
            waitingForTargetArea = true;
            timeoutTicks = 0;
            listener.notify("菜单点击完成，正在确认目标区域。");
            return;
        }
        if (!(mc.screen instanceof net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<?> screen)
            || mc.gameMode == null || mc.player == null) {
            return;
        }

        String keyword = settings.menuClickSteps.get(menuStep).trim();
        if (keyword.isEmpty()) {
            menuStep++;
            return;
        }
        AbstractContainerMenu handler = screen.getMenu();
        for (int i = 0; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.container == mc.player.getInventory() || !ServerTextKit.matchesKeyword(slot.getItem(), keyword)) {
                continue;
            }
            boolean isFinalStep = menuStep + 1 >= settings.menuClickSteps.size();
            mc.gameMode.handleContainerInput(handler.containerId, i, 0, ContainerInput.PICKUP, mc.player);
            menuStep++;
            if (isFinalStep && settings.serverEntryMode == ServerEntryMode.SUBSERVER_NETWORK) {
                transitionExpected = true;
            }
            actionTicks = settings.menuActionDelay;
            timeoutTicks = 0;
            listener.notify("已点击菜单步骤 " + menuStep + "：" + keyword + "。");
            return;
        }
    }

    /** 旧 {@code tickTargetAreaDetection}：先看侧边栏命中，未要求关键词时再以世界或维度变化兜底 */
    public void tickTargetAreaDetection() {
        if (!waitingForTargetArea || !settings.autoEnterSubserver) return;
        if (++timeoutTicks >= settings.menuTimeout) {
            waitingForTargetArea = false;
            listener.notify("目标区域确认超时，未执行目标区域指令。");
            return;
        }

        ClientLevel level = mc.level;
        if (level == null) return;
        String currentDimension = level.dimension().identifier().toString();
        if (level != routeStartWorld || !currentDimension.equals(routeStartDimension)) {
            targetTransitionObserved = true;
        }
        String matchedKeyword = ServerTextKit.findTargetAreaKeyword(mc, settings.targetAreaKeywords);
        if (matchedKeyword != null) {
            confirmTargetArea("侧边栏已识别 " + matchedKeyword);
            return;
        }
        if (!settings.requireTargetKeyword && targetTransitionObserved && !menuOpen()) {
            confirmTargetArea(settings.serverEntryMode == ServerEntryMode.MENU_TRANSFER
                ? "已检测到世界或维度变化"
                : "已检测到子服切换");
        }
    }

    /**
     * 旧 {@code onGameJoined} 里「新的子服连接已建立」那一步：停掉菜单点击流程，并直接记下
     * 「已经换服」这个事实（不再要求必须处于等目标区域状态）。
     *
     * <p>与 {@link #stop()} 的区别：这里只停流程、只置「已换服」，不清「预期换服」与「等目标区域」，
     * 因为旧实现在这一段之后紧接着还会读这两个标志（逐字保留旧顺序）。</p>
     */
    public void markSubserverConnectionSwitched() {
        running = false;
        targetTransitionObserved = true;
    }

    /**
     * 旧 {@code onGameJoined} 里与「等目标区域」相关的那一步：换服回调到达时补记世界变化。
     *
     * <p>判据逐字照旧：仅在「等目标区域」且进入方式为子服网络时置位。</p>
     */
    public void observeTransitionIfWaitingTarget() {
        if (waitingForTargetArea && settings.serverEntryMode == ServerEntryMode.SUBSERVER_NETWORK) {
            targetTransitionObserved = true;
        }
    }

    // ── 内部 ──

    /** 旧 {@code confirmTargetArea}：清自己的等待标志，其余交给调用方 */
    private void confirmTargetArea(String reason) {
        waitingForTargetArea = false;
        running = false;
        listener.onTargetAreaConfirmed(reason);
    }

    /** 旧 {@code findMenuItemSlot}：快捷栏 0-8 里先认物品本身，再认关键词 */
    private int findMenuItemSlot() {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(settings.menuTool())) return i;
            for (String keyword : settings.menuItemKeywords) {
                if (ServerTextKit.matchesKeyword(stack, keyword)) return i;
            }
        }
        return -1;
    }

    /** 菜单是否已打开（旧 {@code leyuanMenuOpen} 的同一判据，通用路线里用于兜底判据的排除项） */
    private boolean menuOpen() {
        return mc.player != null && mc.player.containerMenu != null
            && mc.player.containerMenu != mc.player.inventoryMenu;
    }

    /** 关键词表只读视图（供控制台显示「当前配了几步」，不新建第二份数据） */
    public List<String> clickSteps() {
        return settings.menuClickSteps;
    }
}
