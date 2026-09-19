package com.yiyiaddon.feature.villager.fsm;

import com.yiyiaddon.feature.villager.data.VillagerProfessionRegistry;
import com.yiyiaddon.feature.villager.logistics.SupplyService;
import com.yiyiaddon.feature.villager.logistics.UnloadService;
import com.yiyiaddon.feature.villager.model.PipelineTask;
import com.yiyiaddon.feature.villager.model.VillagerTradeMode;
import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import com.yiyiaddon.feature.villager.navigation.VillagerNavigationService;
import com.yiyiaddon.feature.villager.trade.TradeEngine;
import com.yiyiaddon.feature.villager.trade.TradeMatcher;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 村民交易有限状态机（26.1.2）
 *
 * <p>三种模式的核心差异：</p>
 * <ul>
 *   <li>LOCAL（原地交易）：不寻路工作站，只与身边目标村民交易，只自动交易；
 *       绿宝石不足/背包满仅提示玩家手动补给/卸货，不自动寻路箱子</li>
 *   <li>SINGLE_PATH（寻路单点）：寻路到村民附近，交易过程中自动补给/卸货</li>
 *   <li>PIPELINE（多任务）：任务队列顺序执行，每个任务等价一次 SINGLE_PATH</li>
 * </ul>
 *
 * <p>交易协议要点：</p>
 * <ul>
 *   <li>服务端只接受玩家 containerMenu 为 MerchantMenu 时的 SelectTrade 包，
 *       所以必须先真实打开村民交易界面再发包，不存在绕过 GUI 的「静默交易」</li>
 *   <li>交易成功以 offer uses 递增为信号（不用绿宝石减少，见 {@link VillagerTradeSession}），
 *       等待服务端回包确认</li>
 *   <li>所有操作在渲染线程 tick 驱动，禁止 sleep</li>
 * </ul>
 *
 * <p><b>本类按第 38 条拆分后只保留「调度与守卫」</b>（原类 1,221 行，超千行必须先拆再迁）：</p>
 * <ul>
 *   <li>{@link VillagerTradeSession}：打开界面期间的可变状态 + 选单策略 + 确认判定（uses 快照、重试、计数）；</li>
 *   <li>{@link VillagerSupplyRunner}：补给 / 卸货六态（{@code SUPPLY_*} 与 {@code UNLOAD_*}）的推进；</li>
 *   <li>{@link VillagerPipelineQueue}：多任务队列与任务下标推进。</li>
 * </ul>
 * 拆分只改「代码放在哪个类」，状态名、常量值、判据顺序、播报文本一律与旧实现逐字一致；
 * 状态迁移（{@link #enterState(VillagerTradeState)}）、失败（{@link #fail(String)}）、
 * 播报（{@link #log(String)}）仍只有本类这一处出口，协作器通过 package-private 方法回调。
 */
public final class VillagerTradeFSM {

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  组件
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private final Minecraft mc;
    private final VillagerNavigationService navigation;
    private final SupplyService supplyService;
    private final UnloadService unloadService;
    /** 补给 / 卸货六态推进器（拆分产物，见类 javadoc） */
    private final VillagerSupplyRunner supplyRunner;
    /** 多任务队列（拆分产物，见类 javadoc） */
    private final VillagerPipelineQueue pipeline;
    /** 交易会话：选单与确认判定的唯一持有者（拆分产物，见类 javadoc） */
    private final VillagerTradeSession session;
    private Consumer<String> logger;
    private Consumer<String> completeHandler;
    private Consumer<String> errorHandler;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  任务配置
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private VillagerProfession targetProfession;
    private List<VillagerTradeTarget> targets = new ArrayList<>();
    private int maxPrice = 64;
    private int emeraldThreshold = 32;    // 低于该值触发补给
    private int supplyStacks = 1;         // 每次补给追加组数（1组=64个），补给目标=阈值+组数×64
    private int targetQuantity = 64;      // 本次购买总量（件，默认榨干下不参与退出判定）
    private int searchRange = 96;         // 寻路模式搜索目标村民的半径（格，可配置）
    private boolean idleLoop = false;     // 挂机循环模式：榨干后等待补货倒计时，到点重新循环（仅寻路用）
    private int restockWaitTicks = 2400;  // 挂机循环补货等待（tick，默认 2400 = 2 分钟）

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  运行时状态
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private VillagerTradeMode mode = VillagerTradeMode.LOCAL;
    private VillagerTradeState currentState = VillagerTradeState.IDLE;
    private int stateTicks = 0;

    private Villager currentVillager;
    private BlockPos currentWorkstation;
    private final Set<Villager> exhaustedVillagers = new HashSet<>();

    // 关闭界面后的去向
    private TradeRoute afterCloseRoute = TradeRoute.SEARCH_NEXT;
    private String stopReason = "";
    private WaitReason waitReason = WaitReason.SUPPLY;   // 原地模式等待玩家操作的原因
    private int interactTries = 0;
    private int openRetries = 0;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  超时与阈值
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 搜索超时 30 秒 */
    static final int SEARCH_TIMEOUT = 600;
    /** 寻路超时 120 秒（补给 / 卸货六态也用这条） */
    static final int NAV_TIMEOUT = 2400;
    /** 开界面超时 5 秒（箱子开启也用这条） */
    static final int OPEN_TIMEOUT = 100;
    /** 关界面超时 3 秒 */
    private static final int CLOSE_TIMEOUT = 60;
    /** 10 秒无进展换村民 */
    private static final int TRADE_IDLE_TIMEOUT = 200;
    /** 生存实体交互距离 3.0 + 容差 */
    private static final double INTERACT_RANGE = 3.2;

    public VillagerTradeFSM() {
        this.mc = Minecraft.getInstance();
        this.navigation = new VillagerNavigationService();
        this.supplyService = new SupplyService();
        this.unloadService = new UnloadService();
        this.supplyRunner = new VillagerSupplyRunner(this);
        this.pipeline = new VillagerPipelineQueue();
        this.session = new VillagerTradeSession();
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
        this.supplyService.setLogger(logger);
        this.unloadService.setLogger(logger);
    }

    public void setCompleteHandler(Consumer<String> handler) {
        this.completeHandler = handler;
    }

    public void setErrorHandler(Consumer<String> handler) {
        this.errorHandler = handler;
    }

    public void configure(VillagerProfession profession, List<VillagerTradeTarget> targets,
                          int maxPrice, int emeraldThreshold, int targetQuantity) {
        this.targetProfession = profession;
        this.targets = new ArrayList<>(targets);
        this.maxPrice = maxPrice;
        this.emeraldThreshold = emeraldThreshold;
        this.targetQuantity = Math.max(1, targetQuantity);
    }

    /**
     * 每次绿宝石补给的追加组数（1组=64个）。
     * 补给目标 = 触发阈值(32) + 组数×64，默认 1 组 → 补到 96 个。
     */
    public void setSupplyStacks(int stacks) {
        this.supplyStacks = Math.max(0, stacks);
    }

    /**
     * 设置寻路模式的村民搜索半径（格）。
     * 原地模式不受影响（固定按交互距离过滤），仅影响寻路单点/多任务模式。
     */
    public void setSearchRange(int range) {
        this.searchRange = Math.max(8, range);
    }

    /**
     * 挂机循环模式开关（仅寻路单点/多任务模式生效）：榨干全部村民后不结束，
     * 而是进入补货倒计时，到点清空「已榨干」记录重新循环交易。
     */
    public void setIdleLoop(boolean enabled) {
        this.idleLoop = enabled;
    }

    /**
     * 挂机循环的补货等待时长（tick）。默认 2400（2 分钟），
     * 与村民补货机制 {@code allowedToRestock()} 的 2400 tick 冷却一致。
     */
    public void setRestockWaitTicks(int ticks) {
        this.restockWaitTicks = Math.max(400, ticks);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  启动 / 停止
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 启动 LOCAL 或 SINGLE_PATH 模式。
     */
    public boolean start(VillagerTradeMode startMode) {
        if (currentState != VillagerTradeState.IDLE) {
            log("§c当前任务未处于空闲状态，无法启动");
            return false;
        }
        if (targetProfession == null || targets.isEmpty()) {
            log("§c未配置职业或目标物品");
            return false;
        }

        this.mode = startMode;
        resetSession();
        enterState(VillagerTradeState.SEARCHING);
        // 启动参数已由模块层 announceStartup 统一播报，此处不重复输出，避免聊天栏刷屏
        return true;
    }

    /**
     * 启动 PIPELINE 模式（任务队列）。
     */
    public boolean startPipeline(List<PipelineTask> tasks) {
        if (currentState != VillagerTradeState.IDLE) {
            log("§c当前任务未处于空闲状态，无法启动");
            return false;
        }
        if (tasks == null || tasks.isEmpty()) {
            log("§c无 Pipeline 任务");
            return false;
        }

        this.mode = VillagerTradeMode.PIPELINE;
        this.pipeline.load(tasks);
        resetSession();
        loadTask(0);
        enterState(VillagerTradeState.SEARCHING);
        // 启动参数已由模块层 announceStartup 统一播报，此处不重复输出
        return true;
    }

    /**
     * 停止状态机并复位。
     */
    public void stop() {
        boolean wasRunning = isRunning();
        navigation.stop();
        supplyService.reset();
        unloadService.reset();
        ContainerAccess.closeContainer();
        resetSession();
        enterState(VillagerTradeState.IDLE);
        if (wasRunning) log("§c✗ 状态机已停止");
    }

    /**
     * 外部异常（断线 / 死亡 / 世界切换）入口，由模块调用并自关。
     */
    public void handleException(String reason) {
        log("§e⚠ 检测到异常 §8▸ " + reason);
        stop();
    }

    /** 整场复位：会话状态交给 {@link VillagerTradeSession}，村民 / 工作站 / 路由等归状态机。 */
    private void resetSession() {
        exhaustedVillagers.clear();
        currentVillager = null;
        currentWorkstation = null;
        session.reset();
        afterCloseRoute = TradeRoute.SEARCH_NEXT;
        stopReason = "";
        interactTries = 0;
        openRetries = 0;
    }

    /**
     * 把第 {@code index} 个 Pipeline 任务应用到状态机字段，并按旧实现重置本任务维度的计数。
     *
     * @param index 任务下标（由队列保证有效）
     */
    private void loadTask(int index) {
        PipelineTask task = pipeline.at(index);
        if (task == null) return;
        this.targetProfession = task.getProfession();
        this.targets = new ArrayList<>(task.getTargets());
        this.maxPrice = task.getMaxPrice();
        this.targetQuantity = task.getTargetQuantity();
        session.resetForTask();
        this.exhaustedVillagers.clear();
        this.currentVillager = null;
        this.currentWorkstation = null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  主 tick
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public void tick() {
        stateTicks++;

        switch (currentState) {
            case IDLE -> { }
            case SEARCHING -> tickSearching();
            case RESOLVING_WORKSTATION -> tickResolving();
            case NAVIGATING -> tickNavigating();
            case OPENING_MENU -> tickOpeningMenu();
            case TRADING -> tickTrading();
            case CLOSING_MENU -> tickClosingMenu();
            case SUPPLY_NAV -> supplyRunner.tickSupplyNav();
            case SUPPLY_OPEN -> supplyRunner.tickSupplyOpen();
            case SUPPLY_TAKE -> supplyRunner.tickSupplyTake();
            case UNLOAD_NAV -> supplyRunner.tickUnloadNav();
            case UNLOAD_OPEN -> supplyRunner.tickUnloadOpen();
            case UNLOAD_TAKE -> supplyRunner.tickUnloadTake();
            case WAITING_RESTOCK -> tickWaitingRestock();
            case WAITING_PLAYER -> tickWaitingPlayer();
            case NEXT_TASK -> tickNextTask();
            case DONE -> tickDone();
            case ERROR -> tickError();
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  SEARCHING - 搜索目标村民
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void tickSearching() {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) {
            fail("玩家或世界无效");
            return;
        }
        if (stateTicks > SEARCH_TIMEOUT) {
            // 多任务模式：该职业没找到村民就跳过这个任务，不能中断整个队列
            if (mode == VillagerTradeMode.PIPELINE) {
                log("§e⚠ 搜索超时 §8▸ 未找到" + VillagerProfessionRegistry.getDisplayName(targetProfession) + "村民，跳过此任务");
                enterState(VillagerTradeState.NEXT_TASK);
            } else if (idleLoop) {
                // 挂机循环：榨干全部村民后不结束，进入补货倒计时，到点重新循环
                enterState(VillagerTradeState.WAITING_RESTOCK);
            } else {
                log("§d⚠ " + profName() + "已榨干 §8▸ 范围内没有更多可用村民");
                enterState(VillagerTradeState.DONE);
            }
            return;
        }

        // LOCAL 只搜身边，寻路模式搜可配置半径（村民寻路距离不受限，默认 96 格）
        double range = mode == VillagerTradeMode.LOCAL ? 16.0 : (double) searchRange;

        List<Villager> villagers = mc.level.getEntitiesOfClass(
            Villager.class,
            player.getBoundingBox().inflate(range),
            this::isValidTarget
        );

        if (villagers.isEmpty()) return;

        Villager nearest = villagers.stream()
            .min((a, b) -> Double.compare(player.distanceTo(a), player.distanceTo(b)))
            .orElse(null);
        if (nearest == null) return;

        currentVillager = nearest;
        log("§a✓ 锁定村民 §8▸ " + VillagerProfessionRegistry.getDisplayName(targetProfession));

        // LOCAL 直接开交易，寻路模式先解析工作站
        interactTries = 0;
        openRetries = 0;
        enterState(mode == VillagerTradeMode.LOCAL ? VillagerTradeState.OPENING_MENU : VillagerTradeState.RESOLVING_WORKSTATION);
    }

    private boolean isValidTarget(Villager villager) {
        if (villager == null || !villager.isAlive()) return false;
        // 幼年村民无法交易，直接排除
        if (villager.isBaby()) return false;
        if (exhaustedVillagers.contains(villager)) return false;

        // 职业匹配（26.1.2：VillagerProfession 是 Record，常量是 ResourceKey，
        // 用注册表 Identifier 比较，value().equals() 不可靠，会把傻子/失业村民误匹配进来）
        try {
            Identifier targetId = BuiltInRegistries.VILLAGER_PROFESSION.getKey(targetProfession);
            if (targetId == null || !villager.getVillagerData().profession().is(targetId)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        // 原地模式必须处于可交互距离内
        if (mode == VillagerTradeMode.LOCAL && mc.player != null && mc.player.distanceTo(villager) > INTERACT_RANGE) {
            return false;
        }
        return true;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  RESOLVING_WORKSTATION / NAVIGATING（寻路模式）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void tickResolving() {
        if (currentVillager == null || !currentVillager.isAlive()) {
            currentVillager = null;
            enterState(VillagerTradeState.SEARCHING);
            return;
        }

        Block workstationBlock = VillagerProfessionRegistry.getWorkstation(targetProfession);
        if (workstationBlock == null) {
            fail("无法获取工作站类型");
            return;
        }

        // 工作方块只在村民脚下或紧邻水平一格：范围收窄到 1，避免搜到隔壁村民的工作方块
        // 导致「正前方」方向算错、把站位带偏到侧面
        BlockPos workstation = findNearbyWorkstation(currentVillager.blockPosition(), workstationBlock, 1);
        if (workstation == null) {
            log("§e⚠ " + profName() + "村民附近未找到工作站 §8▸ 换下一个");
            exhaustedVillagers.add(currentVillager);
            currentVillager = null;
            enterState(VillagerTradeState.SEARCHING);
            return;
        }

        currentWorkstation = workstation;
        log("§a✓ 工作站 §8▸ " + workstation.toShortString());
        enterState(VillagerTradeState.NAVIGATING);
    }

    private void tickNavigating() {
        if (stateTicks == 1) {
            if (!startPathToVillager()) {
                fail("无法发起寻路");
            }
            return;
        }
        if (stateTicks > NAV_TIMEOUT || navigation.isStuck()) {
            fail("寻路超时或卡死");
            return;
        }
        if (currentVillager == null || !currentVillager.isAlive()) {
            fail("村民无效");
            return;
        }

        // 路径被意外取消时重试
        if (!navigation.isPathing() && stateTicks % 40 == 0) {
            startPathToVillager();
            return;
        }

        // 到达判断：有精确站位时必须走到「工作方块前面」才算到达，
        // 否则玩家从村民后面/侧面经过（距离 ≤3）会提前触发交易，永远到不了前面。
        // range 取 0.5：玩家必须站在目标方块上（distSqr=0），侧面一格(distSqr=1)不算到达。
        BlockPos standTarget = navigation.getVillagerStandTarget();
        boolean arrived = standTarget != null
            ? navigation.hasArrived(standTarget, 0.5)
            : mc.player.distanceTo(currentVillager) <= INTERACT_RANGE;
        if (arrived) {
            navigation.stop();
            interactTries = 0;
            enterState(VillagerTradeState.OPENING_MENU);
        }
    }

    private boolean startPathToVillager() {
        // 站位优先用「工作方块前面」（玩家-工作方块-村民共线），隔工作方块正面交互；
        // 工作方块已收窄到紧邻一格，识别可靠。正前方不可站立时 navigation 内部回退近程寻路。
        if (currentVillager != null && currentVillager.isAlive()) {
            return navigation.pathToVillager(currentVillager.blockPosition(), currentWorkstation);
        }
        return false;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  OPENING_MENU - 打开村民交易界面
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void tickOpeningMenu() {
        LocalPlayer player = mc.player;
        if (player == null) {
            fail("玩家无效");
            return;
        }

        // 交易界面已就绪
        if (player.containerMenu instanceof MerchantMenu) {
            prepareTradeSession();
            enterState(VillagerTradeState.TRADING);
            return;
        }

        if (currentVillager == null || !currentVillager.isAlive()) {
            currentVillager = null;
            enterState(VillagerTradeState.SEARCHING);
            return;
        }

        // 玩家自己开着容器界面时只等不做（同补给 / 卸货的守卫）：此时若继续交互村民，
        // 服务端会把 player.containerMenu 换成村民菜单，而玩家看的是自己那个箱子的界面 ——
        // 他接下来的点击就按村民菜单的 containerId 发出去（错位、丢物品）。
        // 期间原状态重入只清计时，玩家关掉界面后自然续上，不会等到 OPEN_TIMEOUT 被换掉。
        if (mc.gui.screen() instanceof AbstractContainerScreen<?>) {
            enterState(VillagerTradeState.OPENING_MENU);
            return;
        }

        if (stateTicks > OPEN_TIMEOUT || player.distanceTo(currentVillager) > INTERACT_RANGE) {
            // 寻路模式优先回到工作站重新找位，村民可能离开工作站了
            if (mode != VillagerTradeMode.LOCAL && currentWorkstation != null && openRetries == 0) {
                openRetries++;
                enterState(VillagerTradeState.NAVIGATING);
                return;
            }
            log("§e⚠ 无法与" + profName() + "村民交互 §8▸ 换下一个");
            exhaustedVillagers.add(currentVillager);
            currentVillager = null;
            enterState(VillagerTradeState.SEARCHING);
            return;
        }

        // 节流重试交互
        if (stateTicks % 15 == 0) {
            interactTries++;
            if (interactTries > 4 && player.containerMenu == player.inventoryMenu) {
                exhaustedVillagers.add(currentVillager);
                currentVillager = null;
                enterState(VillagerTradeState.SEARCHING);
                return;
            }
            if (mc.gameMode != null) {
                // 只在交互发包瞬间转视角，避免每 tick 覆盖玩家视角（不抢鼠标/视角）
                faceVillager(player, currentVillager);
                // 打点「我方刚开菜单」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
                SilentContainer.markOwnContainerOpen();
                mc.gameMode.interact(player, currentVillager,
                    new EntityHitResult(currentVillager), InteractionHand.MAIN_HAND);
            }
        }
    }

    /**
     * 把玩家视角转向村民眼睛位置（只改 yaw/pitch，原地模式无需玩家自行瞄准）。
     */
    private void faceVillager(LocalPlayer player, Villager villager) {
        Vec3 eye = player.getEyePosition();
        Vec3 target = villager.getEyePosition();
        double dx = target.x - eye.x;
        double dy = target.y - eye.y;
        double dz = target.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);

        // yaw 公式与实体 look 向量互为逆运算：yaw=0 朝向 +Z，atan2(-dx, dz) 归一
        float yaw = Mth.wrapDegrees((float) Math.toDegrees(Math.atan2(-dx, dz)));
        float pitch = (float) Math.toDegrees(-Math.atan2(dy, horiz));
        pitch = Math.max(-90.0F, Math.min(90.0F, pitch));

        player.setYRot(yaw);
        player.setXRot(pitch);
    }

    /** 打开村民界面时复位本村民维度的一切：会话状态归 {@link VillagerTradeSession}，交互重试归状态机。 */
    private void prepareTradeSession() {
        session.prepare();
        interactTries = 0;
        openRetries = 0;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  TRADING - 交易循环（SELECT → 发包 → CONFIRM）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void tickTrading() {
        LocalPlayer player = mc.player;
        if (player == null) {
            fail("玩家无效");
            return;
        }

        // 界面被意外关闭：本次村民会话结束
        if (!(player.containerMenu instanceof MerchantMenu)) {
            // 只有玩家背包屏幕真的开着（mc.gui.screen() 是 InventoryScreen）才算玩家手动干预；
            // containerMenu == inventoryMenu 只是「无容器打开」的默认态，不能据此误判玩家打开背包
            if (mc.gui.screen() instanceof InventoryScreen) {
                fail("检测到玩家打开背包，交易已中断");
                return;
            }
            log("§e⚠ " + profName() + "交易界面已关闭");
            if (currentVillager != null) exhaustedVillagers.add(currentVillager);
            currentVillager = null;
            enterState(VillagerTradeState.SEARCHING);
            return;
        }
        if (currentVillager == null || !currentVillager.isAlive()) {
            currentVillager = null;
            closeMenuAndRoute(TradeRoute.SEARCH_NEXT);
            return;
        }

        session.tickCooldown();

        // 僵局检测：10 秒没有任何成交
        if (session.stalled(stateTicks, TRADE_IDLE_TIMEOUT)) {
            log("§e⚠ " + profName() + "长时间无进展 §8▸ 换下一个");
            exhaustedVillagers.add(currentVillager);
            closeMenuAndRoute(TradeRoute.SEARCH_NEXT);
            return;
        }

        switch (session.phase()) {
            case SELECT -> tickTradeSelect(player);
            case CONFIRM -> tickTradeConfirm();
        }
    }

    private void tickTradeSelect(LocalPlayer player) {
        // 默认榨干：不因购买总量达成而退出，一路买到目标交易全部售罄/锁死为止
        // 退出条件 1：背包满
        if (!TradeEngine.hasSpace()) {
            // 原地模式：玩家自主卸货，只提示不自动寻路卸货箱
            if (mode == VillagerTradeMode.LOCAL) {
                waitReason = WaitReason.UNLOAD;
                closeMenuAndRoute(TradeRoute.WAIT_PLAYER);
                return;
            }
            log("§e⚠ 背包已满 §8▸ " + profName() + "交易暂停，前往卸货");
            closeMenuAndRoute(TradeRoute.UNLOAD);
            return;
        }

        // 客户端报价列表必须来自交易界面 MerchantMenu，Villager.getOffers() 客户端会抛异常
        var offers = ((MerchantMenu) player.containerMenu).getOffers();

        // 单次遍历按「已购最少优先 + 同物品最便宜」轮换选择，勾选多个目标时轮流买入
        int index = session.chooseOffer(offers, targets, maxPrice);
        // 退出条件 3：所有匹配交易售罄/无效
        if (index < 0) {
            // 区分「村民没刷出目标物品」与「目标物品已售罄/超价」，避免笼统提示误导
            boolean hasItem = false;
            for (var offer : offers) {
                if (TradeMatcher.matchesItemOnly(offer, targets)) { hasItem = true; break; }
            }
            if (hasItem) {
                log("§e⚠ " + targetSummary() + "已售罄或超价 §8▸ 换下一个");
            } else {
                List<String> names = new ArrayList<>();
                for (VillagerTradeTarget t : targets) names.add(t.getDisplayName());
                log("§e⚠ " + profName() + "未刷出目标物品 §8▸ " + String.join("、", names));
            }
            exhaustedVillagers.add(currentVillager);
            closeMenuAndRoute(TradeRoute.SEARCH_NEXT);
            return;
        }

        int cost = TradeMatcher.getEmeraldCost(offers.get(index));
        // 退出条件 4：绿宝石不够买这一单
        if (TradeEngine.countEmeralds() < cost) {
            // 原地模式：玩家自主补给，只提示不自动寻路绿宝石箱
            if (mode == VillagerTradeMode.LOCAL) {
                waitReason = WaitReason.SUPPLY;
                closeMenuAndRoute(TradeRoute.WAIT_PLAYER);
                return;
            }
            log("§e⚠ 绿宝石不足 §8▸ 无法购买" + targetSummary() + "，前往补给");
            closeMenuAndRoute(TradeRoute.SUPPLY);
            return;
        }

        // 发包节流
        if (session.onCooldown()) return;

        session.beginSend(index, offers);
        TradeEngine.executeTrade(index);
        session.finishSend();
    }

    private void tickTradeConfirm() {
        var offers = ((MerchantMenu) mc.player.containerMenu).getOffers();
        switch (session.tickConfirm(offers, stateTicks)) {
            case WAITING -> { }
            case SUCCESS -> {
                // 状态反馈（三种模式通用）：成功播报 + 村民交易提示音
                int index = session.offerIndex();
                String itemName = (index < offers.size() && !offers.get(index).getResult().isEmpty())
                    ? offers.get(index).getResult().getHoverName().getString()
                    : "物品";
                log("§a✓ 第 " + session.tradeCount() + " 笔成功 §7(+" + session.lastGained() + " " + itemName + ")");
                mc.player.playSound(SoundEvents.VILLAGER_TRADE, 1.0F, 1.0F);
            }
            case SKIP -> log("§e该交易连续 " + session.lastFailStreak() + " 次确认失败，跳过");
            case RESEND -> {
                log("§e✗ 第 " + (session.tradeCount() + 1) + " 笔未确认，重发 "
                    + session.lastFailStreak() + "/" + VillagerTradeSession.MAX_CONFIRM_FAILS);
                TradeEngine.executeTrade(session.offerIndex());
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  CLOSING_MENU - 关闭村民交易界面并按路由分流
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void closeMenuAndRoute(TradeRoute route) {
        afterCloseRoute = route;
        enterState(VillagerTradeState.CLOSING_MENU);
    }

    private void tickClosingMenu() {
        LocalPlayer player = mc.player;
        if (player == null) {
            fail("玩家无效");
            return;
        }

        // 静默模式下没有 Screen，containerMenu 是唯一权威判断：
        // 不等于 inventoryMenu 说明村民界面或箱子界面仍开着
        boolean containerOpen = player.containerMenu != player.inventoryMenu;

        if (containerOpen) {
            if (stateTicks % 5 == 0) {
                ContainerAccess.closeContainer();
            }
            if (stateTicks > CLOSE_TIMEOUT) {
                player.closeContainer();
            }
            return;
        }
        dispatchRoute(afterCloseRoute);
    }

    /**
     * 关闭村民界面后的路由。箱子界面（补给/卸货）关闭后也复用 CLOSING_MENU。
     */
    private void dispatchRoute(TradeRoute route) {
        switch (route) {
            case SEARCH_NEXT -> {
                session.clearSkips();
                currentVillager = null;
                enterState(VillagerTradeState.SEARCHING);
            }
            case BACK_TO_VILLAGER -> {
                if (currentVillager != null && currentVillager.isAlive() && currentWorkstation != null) {
                    enterState(VillagerTradeState.NAVIGATING);
                } else {
                    enterState(VillagerTradeState.SEARCHING);
                }
            }
            case SUPPLY -> enterState(VillagerTradeState.SUPPLY_NAV);
            case UNLOAD -> {
                if (unloadService.hasTaskItems(targets)) {
                    enterState(VillagerTradeState.UNLOAD_NAV);
                } else if (mode == VillagerTradeMode.PIPELINE) {
                    enterState(VillagerTradeState.NEXT_TASK);
                } else {
                    enterState(VillagerTradeState.DONE);
                }
            }
            case FINISH -> enterState(VillagerTradeState.DONE);
            case NEXT_TASK -> enterState(VillagerTradeState.NEXT_TASK);
            case WAIT_PLAYER -> enterState(VillagerTradeState.WAITING_PLAYER);
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  WAITING_RESTOCK / WAITING_PLAYER
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 挂机循环补货等待：榨干全部村民后倒计时，到点清空榨干记录重新循环。
     *
     * <p>村民补货由服务端 {@code WorkAtPoi} 行为触发，补货冷却
     * {@code allowedToRestock()} 要求距上次补货 ≥ 2400 tick（2 分钟），
     * 因此默认等待 2400 tick，等待结束重新打开交易界面即可买到补货后的新库存。</p>
     */
    private void tickWaitingRestock() {
        if (stateTicks == 1) {
            log("§d⚠ 全部村民已榨干 §8▸ 等待补货 " + (restockWaitTicks / 20) + " 秒后循环");
            return;
        }

        int remainingTicks = restockWaitTicks - stateTicks;
        if (remainingTicks > 0) {
            // 倒计时节流播报（每 100 tick = 5 秒一次，避免刷屏）
            if (stateTicks % 100 == 0) {
                log("§7等待村民补货 §8▸ 剩余 " + (remainingTicks / 20) + " 秒");
            }
            return;
        }

        // 倒计时结束：清空榨干记录，重新搜索开始新一轮
        exhaustedVillagers.clear();
        currentVillager = null;
        currentWorkstation = null;
        log("§a✓ 补货等待结束 §8▸ 开始新一轮交易");
        enterState(VillagerTradeState.SEARCHING);
    }

    /**
     * 原地模式玩家自主等待：不自动补给/卸货，仅提示玩家手动处理，处理完自动恢复交易。
     */
    private void tickWaitingPlayer() {
        LocalPlayer player = mc.player;
        if (player == null) {
            fail("玩家无效");
            return;
        }

        // 提示节流（首帧 + 每 60 tick = 3 秒一次）
        if (stateTicks == 1 || stateTicks % 60 == 0) {
            if (waitReason == WaitReason.SUPPLY) {
                log("§e⚠ 绿宝石不足 §8▸ 请手动补给绿宝石后继续");
            } else {
                log("§e⚠ 背包已满 §8▸ 请手动卸货后继续");
            }
        }

        // 条件恢复判定
        boolean recovered = waitReason == WaitReason.SUPPLY
            ? TradeEngine.countEmeralds() >= emeraldThreshold
            : TradeEngine.hasSpace();

        if (recovered) {
            // 玩家处理完毕且村民仍在附近：重新打开交易界面继续自动交易
            if (currentVillager != null && currentVillager.isAlive()
                && player.distanceTo(currentVillager) <= 6.0) {
                interactTries = 0;
                openRetries = 0;
                enterState(VillagerTradeState.OPENING_MENU);
            } else {
                // 村民丢失/走远：重新搜索
                enterState(VillagerTradeState.SEARCHING);
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  NEXT_TASK / DONE / ERROR
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void tickNextTask() {
        // 上一任务收官播报（purchasedCount 是上一个任务的数据，loadTask 会清零）
        log("§a✓ 任务 " + (pipeline.currentIndex() + 1) + "/" + pipeline.size() + " 完成 §8▸ "
            + VillagerProfessionRegistry.getDisplayName(targetProfession)
            + " · 购入 " + session.purchasedCount() + " 件");

        if (!pipeline.advance()) {
            log("§a§l✓ Pipeline 全部任务完成！");
            enterState(VillagerTradeState.DONE);
            return;
        }

        loadTask(pipeline.currentIndex());
        log("§b开始 Pipeline 任务 " + (pipeline.currentIndex() + 1) + "/" + pipeline.size()
            + " §8▸ " + VillagerProfessionRegistry.getDisplayName(targetProfession));
        enterState(VillagerTradeState.SEARCHING);
    }

    private void tickDone() {
        if (stateTicks != 1) return;

        String summary;
        if (session.purchasedCount() > 0) {
            summary = "§a✓ 交易完成 §8▸ " + profName() + " · 共执行 " + session.tradeCount() + " 笔 · 购入 " + session.purchasedCount() + " 件";
        } else {
            summary = "§e⚠ 交易结束 §8▸ " + profName() + "未购买到任何目标物品";
        }
        summary += " §7(榨干模式)";
        log(summary);
        if (completeHandler != null) {
            completeHandler.accept(summary);
        }
        enterState(VillagerTradeState.IDLE);
    }

    private void tickError() {
        if (stateTicks != 1) return;

        navigation.stop();
        log("§c✗ 运行出错 §8▸ " + stopReason);
        if (errorHandler != null) {
            errorHandler.accept(stopReason);
        }
        enterState(VillagerTradeState.IDLE);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  工具方法
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 搜索村民附近的工作站（与旧实现相同的扫描范围）。
     */
    private BlockPos findNearbyWorkstation(BlockPos center, Block targetBlock, int range) {
        if (mc.level == null) return null;

        for (int y = -range; y <= range; y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    if (mc.level.getBlockState(pos).getBlock() == targetBlock) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  状态迁移 / 播报（唯一的裁决出口，协作器回调这里）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 进入新状态并重置状态计时（旧 {@code enterState}）。 */
    void enterState(VillagerTradeState newState) {
        currentState = newState;
        stateTicks = 0;
    }

    /** 失败停机：记原因并进入 ERROR，由 {@code tickError} 统一播报（旧 {@code fail}）。 */
    void fail(String reason) {
        stopReason = reason;
        enterState(VillagerTradeState.ERROR);
    }

    /** 模块播报出口：统一加模式前缀 {@code §8[<模式>]§r }（旧 {@code log}）。 */
    void log(String message) {
        if (logger != null) {
            logger.accept("§8[" + modePrefix() + "]§r " + message);
        }
    }

    /** 补给 / 卸货结束后的统一出口：记路由并进入关闭等待（旧 {@code dispatchAfterContainerClose}）。 */
    void enterClosing(TradeRoute route) {
        afterCloseRoute = route;
        enterState(VillagerTradeState.CLOSING_MENU);
    }

    /**
     * 本模块当前是否正在使用容器界面：打开交易界面 / 交易中 / 关界面 / 补给 / 卸货。
     *
     * <p>给界面静默做状态门控用（用户 2026-09-19）：只有 true 时才取消容器界面显示 ——
     * 玩家挂机时手动去开自己的箱子照常显示界面，不会被模块当成「自己在开界面」静默掉。
     * 原地模式的「等待玩家」不在其中：那个相位本就是等玩家自己补给 / 卸货，玩家要开自己的箱子。</p>
     */
    public boolean isUsingContainerScreen() {
        return switch (currentState) {
            case OPENING_MENU, TRADING, CLOSING_MENU,
                 SUPPLY_OPEN, SUPPLY_TAKE, UNLOAD_OPEN, UNLOAD_TAKE -> true;
            default -> false;
        };
    }

    /**
     * 玩家自己按 E 打开了背包（生存 / 创造背包）：收掉我方静默容器，并把「取货」相位退回「开箱」。
     *
     * <p>静默模式下容器界面被取消，走不到 {@code AbstractContainerScreen#onClose}，服务端会一直认为
     * 箱子开着；此时玩家在背包里的点击会按箱子的 {@code containerId} 发出去（错位、丢物品）。
     * 收掉容器后取货服务必然判不了就绪，若不退回开箱相位会一直等一个已被收掉的容器而超时停机；
     * 退回后由 {@code VillagerSupplyRunner} 的玩家界面守卫「只等不做」，玩家关掉背包自然重开箱子。</p>
     */
    public void onPlayerInventoryOpened() {
        ContainerAccess.closeContainer();
        supplyService.reset();
        unloadService.reset();
        if (currentState == VillagerTradeState.SUPPLY_TAKE) {
            enterState(VillagerTradeState.SUPPLY_OPEN);
        } else if (currentState == VillagerTradeState.UNLOAD_TAKE) {
            enterState(VillagerTradeState.UNLOAD_OPEN);
        }
    }

    // ── 供拆分出的协作器读取的宿主状态（只读，不暴露写口） ──────────────────

    /** 当前状态已持续的 tick 数（旧字段 {@code stateTicks}）。 */
    int stateTicks() {
        return stateTicks;
    }

    /** 目标物品列表（旧字段 {@code targets}）。 */
    List<VillagerTradeTarget> targets() {
        return targets;
    }

    /** 补给触发阈值（绿宝石低于它就补给，旧字段 {@code emeraldThreshold}）。 */
    int emeraldThreshold() {
        return emeraldThreshold;
    }

    /** 每次补给追加组数（旧字段 {@code supplyStacks}）。 */
    int supplyStacks() {
        return supplyStacks;
    }

    /** 导航服务（补给 / 卸货六态共用）。 */
    VillagerNavigationService navigation() {
        return navigation;
    }

    /** 绿宝石补给服务。 */
    SupplyService supplyService() {
        return supplyService;
    }

    /** 成品卸货服务。 */
    UnloadService unloadService() {
        return unloadService;
    }

    /** 运行模式中文前缀（每次日志统一带，方便区分当前模式）。 */
    private String modePrefix() {
        return switch (mode) {
            case LOCAL -> "原地交易";
            case SINGLE_PATH -> "寻路单点";
            case PIPELINE -> "多任务";
        };
    }

    /** 目标职业中文名（统一取用，避免各处重复拼职业名）。 */
    private String profName() {
        return VillagerProfessionRegistry.getDisplayName(targetProfession);
    }

    /** 目标物品摘要：单物品返回其名，多物品返回「首个 等N种」。 */
    private String targetSummary() {
        if (targets == null || targets.isEmpty()) return "目标物品";
        if (targets.size() == 1) return targets.get(0).getDisplayName();
        return targets.get(0).getDisplayName() + " 等" + targets.size() + "种";
    }

    public VillagerTradeState getCurrentState() {
        return currentState;
    }

    public boolean isRunning() {
        return currentState != VillagerTradeState.IDLE
            && currentState != VillagerTradeState.DONE
            && currentState != VillagerTradeState.ERROR;
    }
}
