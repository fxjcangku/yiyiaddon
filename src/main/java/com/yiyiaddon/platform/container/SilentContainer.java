package com.yiyiaddon.platform.container;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.ui.render.SkiaScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerReconfigScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;

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
     * <p><b>只拦「玩家自己的界面」</b>（用户 2026-09-22：「一直开在这里你懂吗」）：跨服出售走的是代理的
     * <b>重新配置</b> —— 服务端在游戏阶段发 {@code StartConfiguration}，原版先
     * {@code ClientPacketListener#handleConfigurationStart} → {@code Minecraft#clearClientLevel(new
     * ServerReconfigScreen(...))}，而那个界面<b>自己永远不会关</b>（源码里只有「30 秒后可点断开连接」
     * 与转发 {@code connection.tick()}），唯一的收场路径就是随后 {@code handleLogin} 开出来的
     * {@code LevelLoadingScreen}：{@code LevelLoadingScreen#tick} 在 {@code loadTracker.isLevelReady()}
     * 时 {@code onClose()}。所以老写法「只要开着界面就拦」会把加载界面连同这条收场路径一起吞掉，
     * 客户端永久停在「重新配置中…」（实测日志：{@code 星露谷 界面观察：ServerReconfigScreen}
     * 从进服一直挂到玩家手动点「断开连接」，全程世界/实体正常、NPC 也能戳开）。
     * 系统过渡界面（重新配置 / 连接中 / 断线）一律放行，让原版自己收场。</p>
     *
     * @return true = 该拦（玩家确实开着自己的界面，且这次要开的是加载界面）
     */
    public static boolean isLevelLoadingHijack(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        if (!LevelLoadingScreen.class.getName().equals(screenClassName)) return false;
        return isPlayerOwnedScreen(Minecraft.getInstance().gui.screen());
    }

    /**
     * 值得在传送 / 换服时保住的「玩家自己的界面」。
     *
     * <p>判据必须窄：{@link SkiaScreen}（我方全部控制台 / 面板 / 选择器）、容器与背包、聊天，
     * 以及原版暂停菜单那一族（游戏菜单 / 选项 / 进度 / 统计 / 玩家列表）—— 这些都是<b>玩家自己开的</b>，
     * 传送时被「加载地形中」顶掉纯属干扰（用户 2026-09-22 截图报的就是游戏菜单被顶掉）。
     * 其余（含原版的重新配置、连接中、断线界面）都是「原版自己会收场」的过渡界面，
     * 拦掉它们的加载界面等于掐断收场路径 —— 见 {@link #isLevelLoadingHijack}。</p>
     *
     * <p>暂停菜单那一族放行是安全的：跨服「重新配置」时原版会<b>无条件</b>把
     * {@code ServerReconfigScreen} 盖上来（不经此处判断），随后收场用的仍是那上面的加载界面。</p>
     */
    public static boolean isPlayerOwnedScreen(Screen screen) {
        if (screen == null) return false;
        return screen instanceof SkiaScreen
            || screen instanceof AbstractContainerScreen<?>
            || screen instanceof ChatScreen
            || screen instanceof PauseScreen
            || screen instanceof OptionsScreen
            || screen instanceof AdvancementsScreen
            || screen instanceof StatsScreen
            || screen instanceof SocialInteractionsScreen;
    }

    /**
     * 原版自己开的「系统过渡界面」（传送 / 换服途中的那些）。
     *
     * <p><b>一律放行、绝不能拦</b>：{@code ServerReconfigScreen#tick} 是配置阶段唯一在 tick 连接的地方
     * （{@code clearClientLevel} 把 {@code gameMode} 置空，{@code MultiPlayerGameMode#tick} 不再跑），
     * 拦掉它连接直接冻住 —— 就是 2026-09-22 那次「卡在重新配置中」。{@code LevelLoadingScreen} 则是
     * 它唯一的收场路径。</p>
     *
     * <p>所以「跨服时玩家正开着的界面被顶掉」不能靠拦，只能靠 {@link #stashPlayerScreenBefore} 记账、
     * {@link #reclaimPlayerScreen} 归还。</p>
     */
    public static boolean isSystemTransitionScreen(Screen screen) {
        return screen instanceof ServerReconfigScreen || screen instanceof LevelLoadingScreen;
    }

    // ── 玩家自己的界面：被系统过渡界面顶掉时先记账，回到游戏阶段再归还 ──────────────

    /** 被过渡界面顶掉的那个玩家界面（引用），null = 没有账 */
    private static Screen stashedPlayerScreen;

    /** 记账时刻：超时作废，免得某次传送半途断线后隔很久把旧界面弹回来 */
    private static long stashedPlayerScreenAtMs;

    /** 账的有效期（毫秒）：一次跨服往返实测 1~2 秒，15 秒足够宽裕 */
    private static final long STASH_MAX_MS = 15_000L;

    /**
     * 系统过渡界面即将顶掉玩家自己的界面时先记账（{@code MinecraftSetScreenMixin} 在界面真的被换上时调）。
     *
     * <p>用户 2026-09-22：「还是关闭了我的 esc 返回那个键」—— 跨服回挖矿服走代理<b>重新配置</b>，
     * 玩家正开着的游戏菜单 / 控制台页会被「重新配置中…」无条件盖掉，而那个界面拦不得（见
     * {@link #isSystemTransitionScreen}）。盖掉之后原版收场用的是加载界面，界面一关就回到游戏里，
     * 此时把记账的这个界面放回去，玩家看到的就是「我的界面一直开着」。</p>
     *
     * <p>只在「过渡界面顶掉玩家自己的界面」时记，且已有账不覆盖 —— 一次跨服会连顶两次
     * （重新配置 → 加载地形），要保住的是最初那个。</p>
     */
    public static void stashPlayerScreenBefore(Screen incoming, Minecraft client) {
        if (client == null || incoming == null) return;
        if (!isSystemTransitionScreen(incoming)) return;
        if (stashedPlayerScreen != null) return;
        Screen current = client.gui.screen();
        if (!isPlayerOwnedScreen(current)) return;
        stashedPlayerScreen = current;
        stashedPlayerScreenAtMs = System.currentTimeMillis();
    }

    /**
     * 界面被关掉那一刻（{@code setScreen(null)}），若账还在就把它还回去。
     *
     * <p>只在「真的回到游戏里」才还：配置阶段 {@code player / level} 都为空，那几次关屏（重新配置 /
     * 加载）不能把界面提前弹出来；落地后加载界面自己关掉的那一次才是收尾。</p>
     *
     * @return 该放回来的界面；没有账 / 还没回到游戏 / 账过期都给 {@code null}
     */
    public static Screen reclaimPlayerScreen(Minecraft client) {
        Screen stashed = stashedPlayerScreen;
        if (stashed == null || client == null) return null;
        if (System.currentTimeMillis() - stashedPlayerScreenAtMs > STASH_MAX_MS) {
            stashedPlayerScreen = null;
            return null;
        }
        if (!isSystemTransitionScreen(client.gui.screen())) return null;
        if (client.player == null || client.level == null) return null;
        stashedPlayerScreen = null;
        return stashed;
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
