package com.yiyiaddon.platform.container;

import com.yiyiaddon.core.ClientChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

/**
 * 静默容器统一闸门：所有「自己发包开方块容器」的模块共用这一份界面判据。
 *
 * <p><b>第 169 条：同类逻辑只留一份。</b>改造前每个模块各写一份 {@code isContainerScreen} +
 * 各写一遍「背包放行」，且都漏了创造背包（创造模式按 E 会先开 {@code InventoryScreen} 再切成
 * {@code CreativeModeInventoryScreen}，只放行前者会让后者被静默 —— {@code RecipeBookComponent.book}
 * 未初始化直接闪退）。</p>
 *
 * <p><b>为什么必须有「玩家开背包就收容器」这一步</b>：静默模式下容器界面被取消，
 * 走不到 {@code AbstractContainerScreen#onClose}，服务端会一直认为容器开着；此时玩家背包里的点击
 * 会按容器的 {@code containerId} 发出去 —— 表现是错位、丢物品。修法不是「不关容器」，而是
 * 「关容器但别动玩家界面」（关容器本身不动界面这一点由
 * {@code LocalPlayerScreenGuardMixin} + {@code EventDispatcher#closeScreenUnlessPlayerOwned} 保证）。</p>
 *
 * <p>模块侧统一用法（判据共用，各模块只保留自己的状态门控与相位复位）：</p>
 * <pre>{@code
 * String cls = event.payload();
 * if (SilentContainer.isLevelLoadingHijack(cls)) { event.cancel(); return; }   // 传送时别顶掉玩家界面
 * if (SilentContainer.isPlayerInventory(cls)) {                                // 玩家按 E 开背包
 *     if (SilentContainer.releaseSilentContainer()) resetMyContainerPhase();
 *     return;                                                                 // 背包永远放行
 * }
 * if (本模块正在用容器() && SilentContainer.isContainerScreen(cls)) {
 *     SilentContainer.rejectPlayerContainer();   // 玩家手动开的：压掉 + 收容器 + 动作栏提示
 *     event.cancel();
 * }
 * }</pre>
 *
 * <p><b>配套打点</b>：模块每次自己发包开容器 / 开菜单之前必须调 {@link #markOwnContainerOpen()}，
 * 否则我方自己开出来的界面会被当成玩家手动开的（每开一次箱子弹一次提示）。</p>
 */
public final class SilentContainer {

    private SilentContainer() {
    }

    /**
     * 玩家自己的背包界面：生存背包与创造背包。
     *
     * <p>两个都必须放行，且创造背包不能省 —— 创造模式下 {@code InventoryScreen.init} 会立刻切到
     * {@code CreativeModeInventoryScreen}，拦掉后者会让前者停在未初始化状态而闪退。</p>
     */
    public static boolean isPlayerInventory(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        return InventoryScreen.class.getName().equals(screenClassName)
            || CreativeModeInventoryScreen.class.getName().equals(screenClassName);
    }

    /** 原版容器界面（事件载荷只带类名，用 {@link AbstractContainerScreen} 判归属） */
    public static boolean isContainerScreen(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(screenClassName));
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * 玩家自己正开着界面时，原版传送/重生会不会把「加载地形中」盖上来。
     *
     * <p>本服传送走「重生式传送」：{@code ClientPacketListener#handleRespawn} →
     * {@code startWaitingForNewLevel} → {@code setScreenAndShow(LevelLoadingScreen)}，
     * 这是**无条件**替换玩家界面的（用户 2026-09-19：「没用，只要传送成功，就会把我这个页面关闭」）。
     * 该界面只画进度条 —— 真实加载推进与「客户端已加载」上报都在
     * {@code ClientPacketListener#tick} 里由 {@code levelLoadTracker} 完成，拦掉它不影响传送与落地发包。</p>
     *
     * @return true = 该拦（玩家确实开着界面，且这次要开的是加载界面）
     */
    public static boolean isLevelLoadingHijack(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        if (Minecraft.getInstance().gui.screen() == null) return false;
        return LevelLoadingScreen.class.getName().equals(screenClassName);
    }

    /**
     * 把我方静默容器收掉（玩家自己开背包/要退出等场景）。
     *
     * <p>只关真正的容器（{@code containerMenu != inventoryMenu}），玩家界面一动不动 ——
     * 与 {@link ContainerAccess#closeContainer()} 同一实现，这里只是为了在模块侧读起来
     * 「这件事叫收静默容器」而不是「关容器」。</p>
     *
     * @return true = 确实收掉了一个还开着的静默容器（调用方据此复位自己的容器相位）
     */
    public static boolean releaseSilentContainer() {
        if (!ContainerAccess.hasOpenContainer()) return false;
        ContainerAccess.closeContainer();
        return true;
    }

    // ── 「这个容器界面是谁开的」判据（给玩家提示用） ──────────────────────────

    /** 我方最近一次「发包开容器 / 开菜单」时的玩家 tick */
    private static int lastOwnOpenTick = Integer.MIN_VALUE;

    /**
     * 打点窗口（tick）：从发包到界面创建的服务端往返远小于它。
     *
     * <p>窗口只用来判「界面是不是我方刚开出来的」，因此宁可放宽 —— 窗口内误判为「我方的」
     * 只是少一句提示，不会误关任何界面。</p>
     */
    private static final int OWN_OPEN_WINDOW_TICKS = 40;

    /**
     * 模块每次**自己发包**开容器 / 开菜单之前调用（打点）。
     *
     * <p>必须打满所有我方开容器的入口，否则我方自己开出来的界面会被当成玩家手动开的，
     * 每开一次箱子就弹一次提示。</p>
     */
    public static void markOwnContainerOpen() {
        Minecraft mc = Minecraft.getInstance();
        lastOwnOpenTick = mc.player == null ? Integer.MIN_VALUE : mc.player.tickCount;
    }

    /** 这个容器界面是不是我方刚发包开出来的 */
    private static boolean isOwnContainerOpen() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return true;
        return mc.player.tickCount - lastOwnOpenTick <= OWN_OPEN_WINDOW_TICKS;
    }

    /**
     * 处理「玩家手动开的容器界面」：**不给打开（压掉界面 + 收掉容器）+ 动作栏提示**。
     *
     * <p>模块优先口径（用户 2026-09-19 拍板）：模块正在用容器 / 菜单时，玩家手动开的箱子
     * 不给打开 —— 界面压掉，且容器本身也收掉，然后动作栏提示一句「稍后重试」。</p>
     *
     * <p><b>为什么必须收掉容器，不能只压界面</b>：只压界面的话服务端那边玩家的箱子其实已经开了，
     * {@code player.containerMenu} 被换成玩家的箱子，而模块的「容器已打开」判据只看
     * {@code containerId != 0} —— 于是模块会把这个箱子当成自己的目标容器去搬东西
     * （取物模式从玩家箱子里取、存物模式往玩家箱子里塞）。收掉之后模块下一 tick 自然重开自己的目标容器。</p>
     *
     * <p>收容器本身不动玩家界面：{@code LocalPlayerScreenGuardMixin} 保证（见 128 号复盘）。</p>
     */
    public static void rejectPlayerContainer() {
        if (isOwnContainerOpen()) return;   // 我方自己开出来的界面：静默即可，容器要留着给模块搬货
        ContainerAccess.closeContainer();
        ClientChat.overlay("§e⚠ 容器界面发包无法打开 §8▸请关闭模块重试");
    }
}
