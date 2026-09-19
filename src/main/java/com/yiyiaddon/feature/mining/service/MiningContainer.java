package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.platform.eat.SilentEat;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 容器交互助手
 * 
 * 功能：
 * · 静默垃圾丢弃器（一轮全丢：当刻扫到的垃圾一次全发，边走边丢，见 tickTrashDisposal）
 * · 极速卸货流（高速 SlotClick 转移矿物）
 * · 食物提取与自动进食
 *
 * <p>对应旧项目 {@code mining/service/ContainerHelper.java}（584 行）逐条移植：
 * 常量、状态字段、槽位映射与发包语义全部照旧；换掉的两处外部依赖是</p>
 * <ul>
 *   <li>旧 {@code farm.FarmPacketOps.interactBlock} → {@code platform/network/BlockPacketSender.interactBlock}；</li>
 *   <li>旧框架 {@code InvUtils.move()/swap()} → 26.1.2 的
 *       {@code handleContainerInput(..., ContainerInput.SWAP, ...)} 与 {@code Inventory#setSelectedSlot}
 *       （与本项目 {@code feature/stardew/adapter/DefaultStardewAdapter} 同一做法）。</li>
 * </ul>
 *
 * <p><b>本轮与旧项目的差异</b>（用户 2026-09-19 需求：「自动挖矿加一个发包功能，自动回血」）：
 * 旧 {@code autoEat} 换成<b>发包连吃</b> {@link #packetEat()} —— 吃什么<b>仍严格按食物白名单</b>、
 * 由发包驱动（不依赖按住右键，窗口失焦也成立）、吃完一件立刻接下一件。
 * 当天曾把「吃什么 / 留什么」放宽成「任何带 {@code FOOD} 组件的物品」，结果怪物掉的腐肉
 * 被保留并吃掉（用户实机反馈「白名单食物只有金苹果，怎么会吃腐肉」），已按用户裁定收口：
 * 吃（{@link #isEdible}）、留（{@link #shouldKeep}）、腾快捷栏（{@link #findTemporaryHotbarSlot}）
 * 与状态机侧 {@code hasFoodToEat / countFoodStacks}、以及补给取货（{@link #withdrawFood()}）
 * 全部统一走 {@code foodWhitelist}。「保留白名单」是另一份职责独立的名单（丢垃圾时额外免丢）。</p>
 */
public final class MiningContainer {

    /** 进食诊断日志：只写日志文件（yiyiaddon/eat），不进聊天 —— 排查「吃不上」时看这个 */
    private static final Logger EAT_LOG = LoggerFactory.getLogger("yiyiaddon/eat");
    /**
     * 食物流转诊断日志（{@code yiyiaddon/food}）：副手口粮为什么搬了 / 为什么没搬，
     * 以及补给取货每一格的处置（拿了一组 / 已够 / 背包塞不下 / 副手为什么没补）。
     * 用户 2026-09-19：「加个日志看看 打开箱子没拿食物」—— 只写日志文件，不进聊天。
     */
    private static final Logger FOOD_LOG = LoggerFactory.getLogger("yiyiaddon/food");
    /** 食物日志的限流（tick）：同一状态最多每这么多刻一条 */
    private int lastFoodLogTick = -1000;

    private final AutoMinerModule module;
    private final Minecraft mc;

    /**
     * 开箱允许的最大眼到方块中心距离的平方（4.0 格）。
     *
     * <p>原版交互距离 4.5 格，这里留 0.5 格余量；配套 {@link #withinOpenRange} 的水平/垂直邻域判据，
     * 用于「容器比玩家高」时不再垫方块上去（用户 2026-09-17 需求）。</p>
     */
    private static final double MAX_OPEN_DISTANCE_SQR = 16.0;

    private int trashDisposalCooldown = 0;
    // 两轮丢弃之间的最小间隔：每刻一轮（一轮＝把当刻扫到的垃圾全发出去，见 tickTrashDisposal）。
    // 用户 2026-09-18：「能不能发包全扔 我感觉现在扔的很慢」——旧实现 5 刻/格，满背包 30 格要 7.5 秒，
    // 观感就是「站着丢半天」。现在满背包也是当刻清空；冷却只是为了不在没有垃圾时空扫
    // （36 格 getItem 很便宜，但没必要每刻都扫）。真被服务器拍了就把这个数字往上调。
    private static final int TRASH_DISPOSAL_INTERVAL = 1;

    private AbstractContainerMenu currentMenu = null;
    private int menuStateId = -1;
    private int stableStateTicks = 0;
    private static final int STABLE_REQUIRED = 2;

    private int openAttempts = 0;
    private BlockPos openingPos = null;
    private int openingCooldown = 0;
    private int actionCooldown = 0;
    /** 上一格补给 Shift 点击正在等待到账的物品与其「点击前」数量（null 表示没有在途点击） */
    private Item pendingWithdrawItem = null;
    private int pendingWithdrawCountBefore = 0;
    private int pendingWithdrawWaitTicks = 0;
    /** 一次 Shift 点击最多等多少刻到账；超时说明背包根本塞不进（背包满 / 服务器拒绝） */
    private static final int WITHDRAW_CONFIRM_TICKS = 20;

    // 精确补一组（光标搬运）的进行中状态
    private static final int PRECISE_PICKUP = 1;
    private static final int PRECISE_FILL = 2;
    private static final int PRECISE_RETURN = 3;
    /** 0 = 空闲；非 0 时表示光标上可能拿着物品，其它点击一律让路 */
    private int precisePhase = 0;
    /** 正在精确补的那一格（箱子侧菜单下标）；-1 表示已把余量放回 */
    private int preciseChestSlot = -1;
    private Item preciseItem = null;
    private int preciseWaitTicks = 0;
    /** 精确填充开始前背包里该食物的数量，收尾时用来判断这次点击到底有没有生效 */
    private int preciseCountBefore = 0;
    /** 连续「点了却没到账」的次数：满 2 次判背包塞不进 */
    private int preciseFailStreak = 0;

    // 「直接补到副手」：一次 SWAP（button = 40 = 副手）把箱子那一格与副手互换 ——
    // 副手空就直接拿到整组，副手有同种零头则零头换回箱子、副手拿到整组。数量精确、一步到位。
    // （最初试过光标搬运「拿起 → 点副手 → 余量放回」，实测卡在拿起那一步：本地预测的 carried
    //   被服务端同步冲掉，判定成「点击被拒」→ 每 0.5 秒重来一次，二十多秒没拿到东西。）
    private int offhandSwapCooldown = 0;

    private int eatMoveCooldown = 0;
    private static final int MAX_OPEN_ATTEMPTS = 5;

    // 进食时临时占用快捷栏的记录（用户 2026-09-17：食物不在快捷栏时「跟垫脚方块切换但换不回来」）：
    // 旧实现没空槽就固定顶掉快捷栏 8 号位且永不归还，玩家的垫脚方块被顶进背包后再也不回来。
    // 现在记录被顶掉的槽位与物品，退出进食时换回原位。
    private int eatDisplacedHotbarSlot = -1;
    private Item eatDisplacedItem = null;

    // ── 发包连吃（自动回血）的进行中状态，语义见 packetEat 上方注释 ──

    /** 补发间隔（刻）：这一件还没被结算就每隔这么多刻补一包（自愈，见 advanceEating 第 3 条） */
    private static final int EAT_RESEND_INTERVAL = 4;
    /** 起手宽限（刻）：起手包发出去后至少等这么久，才允许补发一包（服务端要一拍才登记） */
    private static final int EAT_START_GRACE_TICKS = 3;
    /** 单件食物的等待上限（刻）：原版 32 刻结算，留足同步延迟；超时判这件没吃上 */
    private static final int EAT_CONSUME_TIMEOUT_TICKS = 60;
    /** 一次进食失败后的冷却（刻）：避免与 Baritone / 秒破的换手互相顶成死循环 */
    private static final int EAT_RETRY_COOLDOWN_TICKS = 20;

    /** 副手槽在 {@code Inventory} 里的下标（26.1.2：0~35 主背包 + 36~39 护甲 + 40 副手） */
    private static final int EAT_OFFHAND_SLOT = 40;

    /**
     * 副手口粮「换个更满的」的最小差值（个）：背包里同种食物要比副手那份多这么多才互换。
     *
     * <p>防抖：两份数量接近时按大小来回互换会退化成「吃一口换一次」，背包格与副手格反复跳。
     * 8 个以下就当两份一样多，等副手那份吃完再换新的一份。</p>
     */
    private static final int RATION_SWAP_MIN_GAIN = 8;

    /**
     * 本次进食是否走副手（用户 2026-09-19「边挖边吃」）。
     *
     * <p>主手进食必须把选定槽切到食物上，Baritone 的挖矿要拿镐 —— 两者抢主手，所以旧实现只能
     * 「停下挖矿 → 吃 → 重新寻路」。改走副手后主手与选定槽全程不动，挖矿可以不中断，
     * 那 1.6 秒/件（服务端 {@code Consumable} 规则，客户端改不了）就不再是浪费。</p>
     *
     * <p>与「挂机修复点」（{@code REPAIR}）互斥：修复态同样占着副手（把手上的工具切到副手让 XP 修它），
     * 而进食只可能从 {@code MINING} 触发，且退出进食时会把副手整组换回，所以两者不会抢同一个槽位。</p>
     */
    private boolean eatFromOffhand = false;
    /**
     * 常驻口粮搬运的节流（刻）：这几刻内不重复换手，也不判「食物吃完」（等副手同步到账）
     */
    private int offhandRationCooldown = 0;

    /**
     * 副手被<b>玩家自己</b>动过之后的退让刻数（用户 2026-09-19：「我故意一直按 F 切换找 bug，
     * 然后金苹果就被我放到快捷栏了」）。
     *
     * <p>玩家按 F 换手走的是自带的 {@code SWAP_ITEM_WITH_OFFHAND} 动作包（作用于<b>选定槽</b>），
     * 模块补口粮走的是容器点击 —— 两条命令在服务端按到达顺序执行，玩家连按时会互相插队：模块刚把
     * 食物换进副手，玩家的 F 又把它换到选定槽（看起来就是「金苹果跑到快捷栏了」）。所以只要检测到
     * 副手在模块没动手的情况下变了，就退让这么多刻，让玩家先玩。</p>
     */
    private static final int MANUAL_OFFHAND_BACKOFF_TICKS = 30;
    /** 模块自己换手后的豁免刻数：这几刻内副手变化（服务端回同步）不算「玩家动过」 */
    private int offhandSwapGrace = 0;
    private int offhandManualCooldown = 0;
    /** 上一刻看到的副手物品（{@code null} = 还没初始化，用于识别玩家手动换手） */
    private ItemStack lastOffhandSeen = null;

    /** 正在吃的槽位（-1 = 没在吃）：主手模式是快捷栏下标，副手模式恒为 {@link #EAT_OFFHAND_SLOT} */
    private int eatHotbarSlot = -1;
    /** 正在吃的那件食物 */
    private Item eatItem = null;
    /** 发出起手包时手持那格的数量，用来判断「服务端结算了这一件」 */
    private int eatCountBefore = 0;
    /** 这一件已经等了多久（刻） */
    private int eatWaitTicks = 0;
    /** 下一包补发的倒计时（刻） */
    private int eatResendTicks = 0;
    /** 进食诊断日志的限流（tick）：同一状态最多每秒一条 */
    private int lastEatLogTick = -1000;
    /** 一次进食失败后的冷却计时（刻） */
    private int eatCooldown = 0;

    public MiningContainer(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    public void reset() {
        currentMenu = null;
        menuStateId = -1;
        stableStateTicks = 0;
        actionCooldown = 0;
        clearPendingWithdraw();
        clearPreciseFill();
        offhandSwapCooldown = 0;
        trashDisposalCooldown = 0;
        openAttempts = 0;
        openingPos = null;
        openingCooldown = 0;
        eatMoveCooldown = 0;
        offhandRationCooldown = 0;
        offhandSwapGrace = 0;
        offhandManualCooldown = 0;
        lastOffhandSeen = null;
        stopPacketEat();
        // 收摊：把进食时被顶掉的快捷栏物品换回原位。
        // 副手口粮<b>不还原</b>（用户 2026-09-19：「关机之后自动把我的工具放回副手了 没必要」）：
        // 常驻副手就是常态，关模块后食物留在副手、工具留在它被换到的槽里，下次开模块直接用。
        // 需要副手放工具的只有挂机修复点（REPAIR），它自己换、修完自己换回，与这里无关。
        restoreEatDisplacedItem();
        eatDisplacedHotbarSlot = -1;
        eatDisplacedItem = null;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  垃圾丢弃
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 静默垃圾丢弃器（每 tick 调用）
     *
     * <p>反逻辑：默认全丢，只保留白名单内的物品。</p>
     *
     * <p><b>一轮全丢</b>（用户 2026-09-18：「能不能发包全扔 我感觉现在扔的很慢」）：同一刻扫到的垃圾
     * <b>一次全发</b>，不再「每 2~5 刻只丢一格」。依据 26.1.2 官方源码逐条核过：</p>
     * <ul>
     *   <li>{@code ServerGamePacketListenerImpl#handleContainerClick} 对背包点击<b>没有任何频率限制</b>，
     *       逐包 {@code menu.clicked(...)} 立即结算；</li>
     *   <li>官方唯一的丢弃限流 {@code dropSpamThrottler}（{@code TickThrottler(20, 1480)}）只用于
     *       <b>创造模式</b>丢物（{@code handleSetCreativeModeSlot}，slotNum&nbsp;&lt;&nbsp;0 那条路），
     *       普通背包 THROW 走不到它；</li>
     *   <li>连发时 {@code packet.stateId()} 与菜单 stateId 不等的包<b>不会被拒绝</b>，
     *       只在结算后多做一次全量补同步（{@code broadcastFullState}），
     *       所以「一轮全丢」的代价只是几 KB 反向同步，不影响物品真的被丢掉。</li>
     * </ul>
     *
     * <p>丢弃走 {@code handleContainerInput} 直发，不开任何界面、不碰 Baritone，全程边走边丢；
     * {@link #TRASH_DISPOSAL_INTERVAL} 只作为两轮之间的最小间隔（一轮已经把能丢的丢完，
     * 不需要每刻重扫）。</p>
     *
     * @param keepWhitelist 保留白名单（此名单内的物品/方块不会被丢弃）
     * @param placeBlocks Baritone搭路方块白名单（只保留各一组，多余丢弃）
     */
    public void tickTrashDisposal(List<String> keepWhitelist, List<String> placeBlocks) {
        if (mc.player == null) return;

        if (trashDisposalCooldown > 0) {
            trashDisposalCooldown--;
            return;
        }
        // 容器开着时背包点击会被客户端丢弃（日志刷 Ignoring click in mismatching container），
        // 整轮跳过，等容器收尾后再丢（与 dropStack 内部那道判断同一口径，这里提前省一次全扫）
        if (isPlayerInventoryClickBlocked()) {
            trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
            return;
        }

        Inventory inventory = mc.player.getInventory();

        // 副手：有经验修补/工具/食物/目标矿物/保留白名单/搭路方块才保留，其余丢弃
        ItemStack offhand = mc.player.getOffhandItem();
        if (!offhand.isEmpty()
            && !shouldKeep(offhand, keepWhitelist)
            && !isPlaceBlock(offhand, placeBlocks)) {
            dropOffhand();
        }

        // 主背包 0-35：一轮扫完，见类内注释「一轮全丢」
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;

            // 搭路方块：只保留一组（最多 64 个），多余的组丢掉。
            // （用户 2026-09-17 明确「我的默认是一组」；截图里留下 3 组原石就是走到了这里。
            //  早期版本「超过 64 就整组丢」曾把玩家要用的垫脚方块莫名扔掉，因此现在：
            //  ① 只保留数量最多的那一组（同数量时保留靠前的），② 手持那一格永不动，
            //  ③ 只有确实超过一组时才丢。数量判据每轮按当前背包重算，一轮丢多组也不会误判）
            if (isPlaceBlock(stack, placeBlocks)) {
                if (countItem(inventory, stack.getItem()) > 64
                    && i != inventory.getSelectedSlot()
                    && !isPrimaryPlaceStack(inventory, i, stack)) {
                    dropStack(i);
                }
                continue;
            }

            // 非搭路方块：不在保留白名单内的全部丢弃
            if (shouldKeep(stack, keepWhitelist)) continue;

            dropStack(i);
        }
        // 一轮结束才起冷却：本轮该丢的都发出去了，下一轮只是重扫（捡到新垃圾才需要再丢）
        trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
    }

    /** 是否为搭路方块白名单内的方块（旧项目按 Block 比较，本项目白名单存方块 ID） */
    private boolean isPlaceBlock(ItemStack stack, List<String> placeBlocks) {
        if (stack.isEmpty() || placeBlocks.isEmpty()) return false;
        Block block = Block.byItem(stack.getItem());
        return placeBlocks.contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

    /** 背包中指定物品的总数量（主背包 0-35） */
    private int countItem(Inventory inventory, Item item) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) count += stack.getCount();
        }
        return count;
    }

    /**
     * 该槽位是否为「要保留的那一组」搭路方块：数量最多的那一组（同数量时保留靠前的）。
     *
     * <p>用它决定「多余组」的判定：不能只用「数量 &gt; 64 就丢」，否则两组都是 64 时
     * 会互相把对方当成多余组，最后把搭路方块全丢光。</p>
     */
    private boolean isPrimaryPlaceStack(Inventory inventory, int slot, ItemStack stack) {
        for (int i = 0; i < 36; i++) {
            if (i == slot) continue;
            ItemStack other = inventory.getItem(i);
            if (other.isEmpty() || other.getItem() != stack.getItem()) continue;
            if (other.getCount() > stack.getCount()) return false;              // 有更大的组 → 本组是多余的
            if (other.getCount() == stack.getCount() && i < slot) return false; // 同数量时保留靠前的那组
        }
        return true;
    }

    /**
     * 是否保留该物品。
     * 默认只留：任意品质工具（镐/铲/斧/剑/锄）、食物白名单内的食物、目标矿物、手动保留名单、经验修补物品。
     * 其余全部视为垃圾丢弃。
     *
     * <p>「食物」按<b>白名单</b>判（用户 2026-09-19 裁定「严格白名单：只吃也只留白名单食物」）——
     * 此前按 {@code FOOD} 组件保留任何食物，怪物掉的腐肉会一直赖在背包里并被吃掉。
     * 想吃/想留白名单外的某样食物，把它加进食物白名单；想保留白名单外的其他物品，用「保留白名单」
     * （两者职责分开，见用户 2026-09-19 裁定）。</p>
     */
    private boolean shouldKeep(ItemStack stack, List<String> keepWhitelist) {
        if (stack.isEmpty()) return true;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (isTool(stack)) return true;                                    // 工具（任何品质）
        if (isWhitelistedFood(stack)) return true;                         // 食物白名单内的食物
        if (isAllowedOre(stack)) return true;                              // 目标矿物
        if (ItemIdentifier.hasMending(stack)) return true;                 // 经验修补附魔（保护好装备）
        return keepWhitelist.contains(itemId);                             // 手动保留名单
    }

    /** 是否为可保留的工具（镐/铲/斧/剑/锄，任意材质） */
    private boolean isTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel")
            || id.endsWith("_axe") || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /**
     * 丢弃指定槽位的物品（发送丢弃数据包）
     */
    private void dropStack(int slot) {
        if (mc.player == null || mc.gameMode == null) return;
        // 还有容器开着时背包点击会被客户端直接丢弃（日志会刷 Ignoring click in mismatching container），
        // 此时先不动手，等容器收尾后下一轮再丢
        if (isPlayerInventoryClickBlocked()) return;

        try {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.isEmpty()) return;
            // 玩家背包(containerId=0)的 InventoryMenu 槽位映射：快捷栏 0-8 → 36-44，主背包 9-35 → 9-35。
            // 之前直接传 inventory 下标导致快捷栏丢到错误的槽位（0-8 对应合成/盔甲区），快捷栏垃圾永远丢不掉。
            int menuSlot = slot < 9 ? 36 + slot : slot;
            // button=1 + THROW = 丢弃整组（等价 Ctrl+Q），与旧框架的 InvUtils.drop() 同语义
            mc.gameMode.handleContainerInput(0, menuSlot, 1, ContainerInput.THROW, mc.player);
        } catch (Exception e) {
            // 静默失败
        }
    }

    /** 丢弃副手物品（InventoryMenu 中副手槽位固定为 45） */
    private void dropOffhand() {
        if (mc.player == null || mc.gameMode == null) return;
        if (isPlayerInventoryClickBlocked()) return; // 容器开着时点击会被客户端丢弃，见 dropStack 注释
        try {
            if (mc.player.getOffhandItem().isEmpty()) return;
            mc.gameMode.handleContainerInput(0, 45, 1, ContainerInput.THROW, mc.player);
        } catch (Exception e) {
            // 静默失败
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  容器交互
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 是否已到「可以从这里开箱」的位置。
     *
     * <p>旧实现要求切比雪夫距离 ≤1（玩家与容器几乎同层）。用户 2026-09-17 实机：潜影盒打包机比玩家高
     * 2 格时，Baritone 为了凑到容器那一层会自动垫方块爬上去——又慢又难看。现按<b>原版交互距离</b>判：
     * 水平 ≤2 格、垂直 -2~+2 格，且眼睛到方块中心 ≤4.0 格（原版 4.5 格留余量），
     * 容器高两层时站在下面直接开箱，不必垫方块。</p>
     *
     * <p>与 {@code MiningStateMachine.isAdjacentTo} 的开箱邻域判据必须同一套，
     * 否则会出现「状态机认为到了、容器层却拒绝开」的开箱死锁。</p>
     */
    private boolean withinOpenRange(BlockPos pos) {
        if (mc.player == null || pos == null) return false;
        BlockPos player = mc.player.blockPosition();
        int dx = Math.abs(player.getX() - pos.getX());
        int dz = Math.abs(player.getZ() - pos.getZ());
        int dy = player.getY() - pos.getY();
        if (dx > 2 || dz > 2 || dy > 2 || dy < -2) return false;
        if (dx == 0 && dy == 0 && dz == 0) return false;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        return eye.distanceToSqr(center) <= MAX_OPEN_DISTANCE_SQR;
    }

    /**
     * 打开容器（发送交互包）
     */
    public void openContainer(BlockPos pos) {
        if (mc.player == null || mc.level == null) return;
        if (!withinOpenRange(pos)) return;
        if (openingCooldown > 0) {
            openingCooldown--;
            return;
        }
        if (mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0) {
            return;
        }

        openAttempts++;
        if (openAttempts > MAX_OPEN_ATTEMPTS) {
            // 打开失败多次后加长冷却再重试，而不是永久放弃（挂后台/网络抖动时可能连续失败，
            // 永久放弃会导致玩家站在箱子前傻等，切回窗口才能继续）。
            openingCooldown = 30;
            openAttempts = 0;
            return;
        }
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        if (!(blockEntity instanceof Container)) {
            return;
        }

        openingPos = pos;
        openingCooldown = 5;

        // 直接发包开箱（带 sequence 预测处理），不依赖 mc.gameMode.useItemOn：
        // 鼠标切出窗口/窗口失焦时 useItemOn 的交互会被吞，导致箱子打不开。
        // 打点「我方刚开箱」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
        SilentContainer.markOwnContainerOpen();
        BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
    }

    /**
     * 关闭容器。
     *
     * <p>{@code player.closeContainer()} 收尾里那句 {@code setScreen(null)} 会关掉当前任意界面，
     * 但它已由 {@code LocalPlayerScreenGuardMixin} 兜住：玩家自己开的非容器界面（游戏菜单 /
     * 我们的控制台）会被保留，容器本身照常收掉（关窗包 + 本地 containerMenu 复位）。
     * 于是这里不需要任何额外判断——不关容器反而会留下 {@code containerId != 0}，
     * 让 {@link #isPlayerInventoryClickBlocked()} 恒为 true、连食物都搬不进快捷栏
     * （用户 2026-09-19：「吃东西还是吃不上」）。</p>
     */
    public void closeContainer() {
        // 静默模式下没有 Screen，靠 containerMenu 判断是否真的开着容器；
        // inventoryMenu 是玩家自身背包，此时关闭会误发 close 包，无需处理。
        if (mc.player != null && mc.player.containerMenu != null
            && mc.player.containerMenu != mc.player.inventoryMenu) {
            mc.player.closeContainer();
        }
        openAttempts = 0;
        openingPos = null;
        currentMenu = null;
        menuStateId = -1;
        stableStateTicks = 0;
        // 关箱即放弃「精确补一组」的中间状态：光标上的物品由服务端随关窗归还，这里不能再接着点
        clearPreciseFill();
        clearPendingWithdraw();
    }

    /**
     * 标点位置是否还存在容器（潜影盒/箱子等）。
     * 潜影盒打包机把盒推走后若没放新盒，此处会返回 false，用于检测「无容器可开」。
     */
    public boolean isContainerAt(BlockPos pos) {
        if (mc.level == null || pos == null) return false;
        return mc.level.getBlockEntity(pos) instanceof Container;
    }

    /**
     * 发送 Shift 快速移动包。
     * 26.1.2 已把旧 clickSlot + SlotActionType 换成
     * MultiPlayerGameMode#handleContainerInput(containerId, slot, button, ContainerInput, player)。
     * 旧框架的 InvUtils.shiftClick 仍走旧 API，在 26.1.2 下发包无效（物品不被移动）。
     */
    private void quickMove(AbstractContainerMenu menu, int slotIndex) {
        ContainerAccess.quickMove(menu, slotIndex);
    }

    /**
     * 本模块是否正在执行自己的容器事务（发包开箱后到收箱前）。
     *
     * <p>给界面静默做状态门控用：只有 true 时 {@code AutoMinerModule} 才取消容器界面显示 ——
     * 玩家挂机时手动去开自己的箱子（此时 {@link #openingPos} 为空）照常显示界面，
     * 不会被模块当成「自己在开箱」静默掉（用户 2026-09-19）。</p>
     *
     * <p>不能用 {@link #isContainerOpen()} 当判据：玩家自己开的箱子同样会让
     * {@code containerMenu.containerId != 0}，那样又会把玩家的箱子拦掉。</p>
     */
    public boolean isOperatingContainer() {
        return openingPos != null;
    }

    /**
     * 容器是否已打开
     */
    public boolean isContainerOpen() {
        if (mc.player == null) return false;

        AbstractContainerMenu menu = mc.player.containerMenu;
        if (menu == null || menu.containerId == 0) {
            return false;
        }

        // 等待 stateId 稳定
        if (currentMenu != menu) {
            currentMenu = menu;
            menuStateId = menu.getStateId();
            stableStateTicks = 0;
            return false;
        }

        if (menu.getStateId() != menuStateId) {
            menuStateId = menu.getStateId();
            stableStateTicks = 0;
            return false;
        }

        stableStateTicks++;
        if (stableStateTicks >= STABLE_REQUIRED) {
            openAttempts = 0; // 容器成功稳定打开，重置开箱尝试计数
            return true;
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  卸货操作
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 卸货：把背包里的矿物 Shift 点进箱子
     * 
     * 优化策略：等待GUI稳定后按顺序快速放入
     * 
     * @return 是否还有矿物需要继续转移
     */
    public boolean depositOres() {
        if (mc.player == null || mc.gameMode == null) return false;
        if (!isContainerOpen()) {
            // 静默模式下没有 Screen，判断容器是否已开但 stateId 尚未稳定（继续等待）
            return mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0;
        }
        if (actionCooldown > 0) {
            actionCooldown--;
            return true;
        }

        AbstractContainerMenu menu = currentMenu;
        if (menu == null) return false;

        Inventory inventory = mc.player.getInventory();

        // 一次性把所有目标矿 Shift 点进箱子（不再一格一格等冷却），服务端按序处理即可
        int moved = 0;
        for (Slot slot : menu.slots) {
            if (slot.container != inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            if (isAllowedOre(stack)) {
                quickMove(menu, slot.index);
                moved++;
            }
        }

        if (moved > 0) {
            actionCooldown = 1; // 下一 tick 再补扫一次，防止有遗漏
            return true;
        }
        return false;
    }

    private boolean isAllowedOre(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        // 精准采集按原矿方块判定，时运按掉落物判定（下界残骸掉落物=自身方块，两模式共用）
        if (module.isSilkTouchMode()) {
            return module.getTargetBlockIds().contains(itemId);
        }
        return itemId.equals(module.getTargetDropItemId());
    }

    /**
     * 检测当前容器是否已满（潜影盒所有存储槽均非空）。
     * 脚本直接遍历容器槽位判断满，与打包机的比较器无关；打包机自会检测满后推盒换新盒。
     * 静默模式下直接用 containerMenu，不依赖 Screen，与 isContainerOpen 判定一致。
     */
    public boolean isContainerFull() {
        if (mc.player == null) return false;
        AbstractContainerMenu menu = mc.player.containerMenu;
        if (menu == null || menu.containerId == 0) return false;

        Inventory inventory = mc.player.getInventory();
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue; // 跳过玩家背包槽
            if (slot.getItem().isEmpty()) return false; // 存在空槽 → 未满
        }
        return true;
    }

    /**
     * 背包里是否还有目标矿（判断卸货是否彻底放完）。
     * 潜影盒打包机模式下：放完才允许 RTP，没放完就继续换盒重开。
     */
    public boolean hasOreInInventory() {
        if (mc.player == null) return false;
        Inventory inventory = mc.player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && isAllowedOre(stack)) return true;
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  补给操作
    // ═══════════════════════════════════════════════════════════════════

    /** 补给取食物的单刻结果（用户 2026-09-17：旧实现只有 true/false，「刚点完还没到账」被当成补给结束） */
    public enum FoodWithdrawResult {
        /** 还在取：有点击在飞行或冷却中，继续调用 */
        WORKING,
        /** 取完了：目标已满组，或箱子里已经没有「还缺的白名单食物」 */
        DONE,
        /** 拿不进来：Shift 点击迟迟不到账（背包满 / 服务器拒绝），别继续耗时间 */
        INVENTORY_BLOCKED
    }

    /**
     * 从食物箱提取食物（只拿白名单内、带 FOOD 组件的）。
     *
     * <p><b>拿多少</b>（用户 2026-09-17 需求：「自动计算，补到一组就可以」；2026-09-18 追加约束）：
     * 逐个物品补到<b>它自己的一组</b>——目标数量取该物品的最大堆叠数（普通食物 64、蜂蜜瓶 16、
     * 蛋糕 / 各类汤 1），因此不写死 64。用户实机反馈「我要的是假设我现在 9 个食物补到 64，
     * 而不是加上多出的几个食物，多出来的占格子」：所以箱子里那一格比缺口多时，
     * 走 {@link #startPreciseFill} 的三步精确填充（拾起整格 → 填满背包未满堆 → 余量放回箱子），
     * 背包里最终正好是一组，不会多出一个半组。</p>
     *
     * <p><b>什么时候算取完</b>：箱子里已经没有「还缺的白名单食物」才返回
     * {@link FoodWithdrawResult#DONE}；每一格点击都要等物品真正到账（背包内该物品数量增长）
     * 才发下一格，等待上限 {@link #WITHDRAW_CONFIRM_TICKS} 刻。旧实现在「点击已发出、
     * 物品还在服务器端飞行」时也会返回 true，状态机当刻读到的还是旧数量，
     * 于是被判「补给箱无白名单食物」直接回矿区——正是用户报的「根本没拿到就回状态机了」。</p>
     */
    public FoodWithdrawResult withdrawFood() {
        if (!isContainerOpen() || mc.player == null || mc.gameMode == null) return FoodWithdrawResult.DONE;
        AbstractContainerMenu menu = currentMenu;
        if (menu == null) return FoodWithdrawResult.DONE;

        Inventory inventory = mc.player.getInventory();
        List<String> whitelist = module.getFoodWhitelist();

        // 0) 精确填充的三步流程优先推进（光标可能正拿着物品，不能插别的点击）
        if (precisePhase > 0) return continuePreciseFill(menu, inventory);

        // 1) 到账确认：上一格点击的物品真的进背包了才继续，否则等（超时判「拿不进来」）
        if (pendingWithdrawItem != null) {
            if (countItemInInventory(inventory, pendingWithdrawItem) > pendingWithdrawCountBefore) {
                clearPendingWithdraw();
            } else if (++pendingWithdrawWaitTicks > WITHDRAW_CONFIRM_TICKS) {
                clearPendingWithdraw();
                return FoodWithdrawResult.INVENTORY_BLOCKED;
            } else {
                return FoodWithdrawResult.WORKING;
            }
        }

        if (actionCooldown > 0) {
            actionCooldown--;
            return FoodWithdrawResult.WORKING;
        }

        // 2) 副手口粮优先补齐（用户 2026-09-19「我说直接补到副手懂？」）：一次 SWAP 把箱子这一格
        //    与副手互换（button = 40 即「与被点击槽位交换副手物品」，等价于在容器界面把鼠标悬在
        //    那一格上按 F）—— 副手空就直接拿到整组，副手有同种零头则零头换回箱子、副手拿到整组。
        //    数量精确（不会拿超），也不需要光标搬运那三步。副手喂饱了才轮到下面那套「背包补到一组」
        if (offhandSwapCooldown > 0) {
            offhandSwapCooldown--;
            return FoodWithdrawResult.WORKING;
        }
        String offhandSkip = null;
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;
            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || !stack.has(DataComponents.FOOD)) continue;
            if (!whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
            if (!canFillOffhandWith(stack)) {
                if (offhandSkip == null) offhandSkip = offhandSkipReason(stack);
                continue;
            }
            foodLog(0, "取货 ▸ 直接补副手 ▸ 箱子槽{} {} x{} ↔ 副手（现有 {} 个）", slot.index,
                stack.getHoverName().getString(), stack.getCount(),
                mc.player.getOffhandItem().getCount());
            mc.gameMode.handleContainerInput(menu.containerId, slot.index, EAT_OFFHAND_SLOT,
                ContainerInput.SWAP, mc.player);
            offhandSwapCooldown = 6; // 等副手那一格同步到账，别连着换
            return FoodWithdrawResult.WORKING;
        }
        if (offhandSkip != null) foodLog(20, "取货 ▸ 副手这次没补：{}", offhandSkip);

        // 3) 在箱子侧找「白名单 + 带 FOOD + 还没满一组」的食物
        boolean blocked = false;
        int skippedFull = 0;   // 已够一组的格数（只为日志）
        int skippedNoRoom = 0; // 背包塞不下的格数（只为日志）
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || !stack.has(DataComponents.FOOD)) continue;
            if (!whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;

            // 目标 = 该物品自己的「一组」（普通食物 64、蜂蜜瓶 16、汤类 1），不写死 64
            int maxStack = stack.getMaxStackSize();
            int current = countItemInInventory(inventory, stack.getItem());
            if (current >= maxStack) {
                skippedFull++; // 已够一组（含整组以上的余量堆）
                continue;
            }

            if (!canAcceptIntoInventory(inventory, stack.getItem())) {
                blocked = true; // 背包塞不下这个物品，继续看还有没有别的能塞的
                skippedNoRoom++;
                continue;
            }

            if (stack.getCount() <= maxStack - current) {
                // 这一格整组拿走也不会超过一组 → Shift 整格最省事
                foodLog(0, "取货 ▸ Shift 整格 ▸ {} x{}（背包已有 {}，目标一组 {}）",
                    stack.getHoverName().getString(), stack.getCount(), current, maxStack);
                pendingWithdrawItem = stack.getItem();
                pendingWithdrawCountBefore = current;
                pendingWithdrawWaitTicks = 0;
                quickMove(menu, slot.index);
                actionCooldown = 5;
                return FoodWithdrawResult.WORKING;
            }

            // 这一格比缺口多：精确补一组，多出来的原样留在箱子里（用户 2026-09-18 要求）
            foodLog(0, "取货 ▸ 精确补一组 ▸ {}（背包已有 {}，箱子这格 x{}）",
                stack.getHoverName().getString(), current, stack.getCount());
            startPreciseFill(menu, slot.index, stack.getItem());
            return FoodWithdrawResult.WORKING;
        }

        // 没有任何「还缺的白名单食物」可拿：确实取完了；若有方块因背包塞不下而被跳过，则是「拿不进来」
        if (blocked) {
            foodLog(20, "取货 ▸ 背包塞不下（已够一组 {} 格，塞不下 {} 格）", skippedFull, skippedNoRoom);
        } else {
            foodLog(40, "取货 ▸ 箱子已无缺口可拿（已够一组 {} 格）→ 收摊", skippedFull);
        }
        return blocked ? FoodWithdrawResult.INVENTORY_BLOCKED : FoodWithdrawResult.DONE;
    }

    // ── 精确补一组（光标搬运，三步）────────────────────────────────────────
    //
    // 目的：箱子那一格比缺口多时，Shift 整格会把背包塞成「一组 + 零头」，白占一个格子。
    // 做法（每步都是原版左键 PICKUP，语义与玩家手动操作完全一致）：
    //   1. 左键箱子格 → 整组拿在光标上；
    //   2. 左键背包里该食物的未满堆 → 该堆被填满，余量仍在光标上（背包可能有多个未满堆，循环做）；
    //   3. 左键原箱子格 → 把余量放回去。
    // 结果：背包里该食物正好一组，箱子里少掉的正是缺口数量。

    /**
     * 开始精确填充。
     *
     * @param chestSlot 箱子侧那一格的菜单下标
     * @param item      该格的食物物品
     */
    private void startPreciseFill(AbstractContainerMenu menu, int chestSlot, Item item) {
        preciseItem = item;
        preciseChestSlot = chestSlot;
        preciseCountBefore = countItemInInventory(mc.player.getInventory(), item);
        precisePhase = PRECISE_PICKUP;
        preciseWaitTicks = 2;
        clickSlot(menu, chestSlot, 0, ContainerInput.PICKUP);
    }

    /** 推进精确填充：填背包未满堆 → 余量放回箱子 */
    private FoodWithdrawResult continuePreciseFill(AbstractContainerMenu menu, Inventory inventory) {
        if (preciseWaitTicks > 0) {
            preciseWaitTicks--;
            return FoodWithdrawResult.WORKING;
        }

        ItemStack carried = menu.getCarried();
        if (precisePhase == PRECISE_PICKUP) {
            // 光标没拿到东西（点击被服务端拒绝 / 那格被别人拿走）：收手，避免空转
            if (carried.isEmpty()) {
                clearPreciseFill();
                actionCooldown = 5;
                return FoodWithdrawResult.WORKING;
            }
            precisePhase = PRECISE_FILL;
            return FoodWithdrawResult.WORKING;
        }

        if (precisePhase == PRECISE_FILL) {
            Slot target = findMergeSlot(menu, inventory, preciseItem);
            if (target != null && !carried.isEmpty() && carried.is(preciseItem)) {
                clickSlot(menu, target.index, 0, ContainerInput.PICKUP);
                preciseWaitTicks = 2;
                return FoodWithdrawResult.WORKING;
            }
            precisePhase = PRECISE_RETURN;
            return FoodWithdrawResult.WORKING;
        }

        // PRECISE_RETURN：余量放回原箱子格
        if (!carried.isEmpty() && carried.is(preciseItem) && preciseChestSlot >= 0) {
            clickSlot(menu, preciseChestSlot, 0, ContainerInput.PICKUP);
            preciseWaitTicks = 2;
            preciseChestSlot = -1; // 只放一次
            return FoodWithdrawResult.WORKING;
        }

        // 收尾：数量确实涨了才算成功；没涨就记一次失败，连续 2 次判背包塞不进
        int now = countItemInInventory(inventory, preciseItem);
        boolean progressed = now > preciseCountBefore;
        clearPreciseFill();
        actionCooldown = 5;
        if (progressed) {
            preciseFailStreak = 0;
        } else if (++preciseFailStreak >= 2) {
            preciseFailStreak = 0;
            return FoodWithdrawResult.INVENTORY_BLOCKED;
        }
        return FoodWithdrawResult.WORKING;
    }

    /** 背包里该食物「还没满一组」的那一格（菜单槽，用于把光标上的余量并进去） */
    private Slot findMergeSlot(AbstractContainerMenu menu, Inventory inventory, Item item) {
        for (Slot slot : menu.slots) {
            if (slot.container != inventory) continue;
            ItemStack stack = slot.getItem();
            if (!stack.isEmpty() && stack.is(item) && stack.getCount() < stack.getMaxStackSize()) {
                return slot;
            }
        }
        return null;
    }

    /** 清掉精确填充的全部中间状态（关箱 / 复位时必须调用，避免光标搬运流程悬在半路） */
    private void clearPreciseFill() {
        precisePhase = 0;
        preciseChestSlot = -1;
        preciseItem = null;
        preciseWaitTicks = 0;
    }

    /**
     * 副手此刻能不能用箱子里的这一格食物来补：副手空着，或拿着<b>同种</b>白名单食物且没满。
     *
     * <p>走的是「与被点击槽位交换副手物品」（SWAP + button 40），副手那件东西会原样落到箱子里 ——
     * 所以副手拿着工具 / 方块等别的东西时不能这么补（会把玩家的东西塞进补给箱）。那种副手由
     * 常驻口粮互换去顶（{@link #tickOffhandRation()}，同样可逆，但作用在玩家背包内、不碰箱子）。</p>
     */
    private boolean canFillOffhandWith(ItemStack chestStack) {
        ItemStack offhand = mc.player.getOffhandItem();
        if (offhand.isEmpty()) return true;
        if (!isWhitelistedFood(offhand)) return false;
        if (!offhand.is(chestStack.getItem())) return false;
        if (offhand.getCount() >= offhand.getMaxStackSize()) return false;
        // 还要真的「更多」才换：互换是等量交换，箱子里那格比副手少（或一样多）时换过去只会
        // 越换越少，而且下一轮条件照样成立 → 无限互换
        return chestStack.getCount() > offhand.getCount();
    }

    /** 副手这次为什么没补上（只用于日志，口径与 {@link #canFillOffhandWith} 一致） */
    private String offhandSkipReason(ItemStack chestStack) {
        ItemStack offhand = mc.player.getOffhandItem();
        if (offhand.isEmpty()) return "副手是空的但没找到可补的那一格";
        if (!isWhitelistedFood(offhand)) {
            return "副手是 " + offhand.getHoverName().getString() + "（不是白名单食物，补给不碰它）";
        }
        if (!offhand.is(chestStack.getItem())) {
            return "副手是另一种食物 " + offhand.getHoverName().getString()
                + "，箱子里是 " + chestStack.getHoverName().getString() + "（不顶掉他在吃的那份）";
        }
        return "副手那份已经满一组";
    }

    /** 容器点击（原版语义）：{@code button=0} 左键、{@code ContainerInput.PICKUP} 拾取 / 放下 */
    private void clickSlot(AbstractContainerMenu menu, int slotIndex, int button, ContainerInput input) {
        if (menu == null || mc.player == null || mc.gameMode == null) return;
        mc.gameMode.handleContainerInput(menu.containerId, slotIndex, button, input, mc.player);
    }

    /** 清掉「在途 Shift 点击」的记录 */
    private void clearPendingWithdraw() {
        pendingWithdrawItem = null;
        pendingWithdrawCountBefore = 0;
        pendingWithdrawWaitTicks = 0;
    }

    /**
     * 背包内指定物品的总数量（主背包 0-35 含快捷栏，<b>加副手</b>）。
     *
     * <p>口径 = 「玩家手上一共有多少这种食物」：补给的目标是<b>总量一组</b>，副手那份口粮也算在内 ——
     * 副手已经拿着一组时就不该再往背包补一组（用户 2026-09-19：「而且还拿超了 不是64个」）。
     * 副手那份由 {@link #withdrawFood()} 的副手分支直接换进副手（一次 SWAP），不走这里。</p>
     */
    private int countItemInInventory(Inventory inventory, Item item) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) count += stack.getCount();
        }
        if (mc.player != null) {
            ItemStack offhand = mc.player.getOffhandItem();
            if (!offhand.isEmpty() && offhand.getItem() == item) count += offhand.getCount();
        }
        return count;
    }

    /** 背包是否还装得下这个物品：有空槽，或存在没满的同类堆可以并入 */
    private boolean canAcceptIntoInventory(Inventory inventory, Item item) {
        if (inventory.getFreeSlot() != -1) return true;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item && stack.getCount() < stack.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  发包连吃（自动回血，用户 2026-09-19 追加）
    // ═══════════════════════════════════════════════════════════════════
    //
    // 与旧 autoEat 的三点差异：
    //   1. 吃什么严格按食物白名单（用户裁定「严格白名单：只吃也只留白名单食物」）；
    //   2. 由发包驱动：起手走原版 gameMode.useItem（唯一验证过能吃的入口）、补发直发
    //      ServerboundUseItemPacket，不依赖按住右键，窗口失焦 / 开着界面 / 边走边吃都成立；
    //      起手后**立刻本地取消用食状态**，所以没有吃东西的动画（手臂摆动 / 咀嚼音 / 粒子），
    //      而服务端照常按 32 刻结算 —— 见 beginEating 注释；
    //   3. 吃完一件立刻接下一件（零间隔连吃），直到饱食度满或没有可吃的食物。
    //
    // 【能有多快】单件食物的用食时长由服务端按物品的 Consumable.consumeSeconds 结算
    // （原版食物 1.6 秒 = 32 刻：Consumable#consumeTicks → LivingEntity#updateUsingItem
    // → completeUsingItem），发包改不了这段时长 —— 服务端对「已在使用物品」期间重复送达的
    // UseItem 包直接忽略（LivingEntity#startUsingItem 的 !isUsingItem() 卫语句）。
    // 所以「快」来自三处：不等按键、两件之间零间隔、饱食度顶满（并带饱和度）后原版自然再生
    // ——饱食度 ≥ 18 才回血，饱和度 > 0 时 10 刻回 1 点（FoodData#tick）。
    // 实测口径：我们直发用食包，本地永远不进入用食状态（所以没有动画），
    // 服务端那边照常按 Consumable 的时长结算；「服务端按包即时结算」（自定义食物 consumeSeconds = 0）
    // 时也是一发包就吃完，同样是真秒吃。

    /** 停手并放开右键（退出进食态 / 关模块时调用） */
    public void stopPacketEat() {
        // 显式放开用食（原版停吃的同一动作）：服务端若已从我们的补发包起手了下一件，
        // 这一包会把它停掉，避免「已经吃饱了还白白多吃一件」。
        if (mc.player != null && mc.gameMode != null && !mc.player.isDeadOrDying()) {
            mc.gameMode.releaseUsingItem(mc.player);
        }
        clearEating();
        eatCooldown = 0;
        eatMoveCooldown = 0;
    }

    /** 清掉「正在吃的那一件」并松开右键（同时用于：吃完、判失败、停手） */
    private void clearEating() {
        eatHotbarSlot = -1;
        eatItem = null;
        eatCountBefore = 0;
        eatWaitTicks = 0;
        eatResendTicks = 0;
        SilentEat.setSuppressed(false);
        mc.options.keyUse.setDown(false);
    }

    /**
     * 取「进食那一格」的物品。副手槽位必须走 {@code getOffhandItem()}：
     * {@code Inventory#getItem(40)} 的下标约定不保证有值（同 {@code StardewInventoryService#stackAt}
     * 的结论），读成空栈会让 advanceEating 判成「手里已不是食物」而反复重挑 —— 副手那一口永远吃不上。
     */
    private ItemStack eatStackAt(int slot) {
        if (mc.player == null) return ItemStack.EMPTY;
        return slot == EAT_OFFHAND_SLOT ? mc.player.getOffhandItem() : mc.player.getInventory().getItem(slot);
    }

    /** 手上（正在吃的那一格）现在是什么：主手模式读选定槽，副手模式读副手槽 */
    private ItemStack eatHeldStack() {
        return eatStackAt(eatHotbarSlot);
    }

    /**
     * 进食流程 / 口粮搬运是否正在进行中（有在吃的那一件，或刚发出换手命令、正等服务端同步）。
     *
     * <p>状态机判「食物吃完」时要避开这个窗口：换手包发出后食物已经离开背包、而客户端的副手还没
     * 收到服务端同步的那几刻里，只扫背包会误判成「没食物了」→ 退出进食态 → 把食物换回背包 →
     * 下一轮又搬走，来回打转（用户 2026-09-19 实机）。</p>
     */
    public boolean isEatingInProgress() {
        return eatHotbarSlot >= 0 || eatMoveCooldown > 0 || offhandRationCooldown > 0;
    }

    /**
     * 副手此刻正拿着白名单口粮吗。
     *
     * <p>供副手口粮锁判定（用户 2026-09-19：「能不能运行期间锁死副手食物不让切换？除非停止模块」）：
     * 模块运行中且副手确实是口粮时，玩家手动的 F 换手会被发包闸门直接丢弃。</p>
     */
    public boolean isOffhandRationHeld() {
        return mc.player != null && isWhitelistedFood(mc.player.getOffhandItem());
    }

    /**
     * 维护「副手常驻口粮」（用户 2026-09-19：「不能一直放在副手？」）：让副手始终拿着一份白名单
     * 食物，边走边吃时直接吃，不必每次进食都搬一遍。挖矿态与进食态每刻调用，靠内部节流。
     *
     * <p>搬运只走 {@link #swapWithOffhand(int)}（{@code ContainerInput.SWAP} + button 40，等价于
     * 背包界面里按住 Shift 悬停按 F）：一步到位，不碰选定槽、不动护甲区，主手可以一直在挖。</p>
     */
    public void tickOffhandRation() {
        if (mc.player == null || mc.gameMode == null) return;
        ItemStack offhand = mc.player.getOffhandItem();
        // 玩家自己动过副手（连按 F 换手 / 手动往里放东西）→ 退让一段时间，别跟他的操作互相插队
        if (offhandSwapGrace > 0) {
            offhandSwapGrace--;
        } else if (lastOffhandSeen != null && isManualOffhandChange(offhand)) {
            offhandManualCooldown = MANUAL_OFFHAND_BACKOFF_TICKS;
            foodLog(10, "副手口粮 ▸ 副手被玩家动过（{} x{} → {} x{}）▸ {} 刻内不碰副手",
                lastOffhandSeen.getHoverName().getString(), lastOffhandSeen.getCount(),
                offhand.getHoverName().getString(), offhand.getCount(), MANUAL_OFFHAND_BACKOFF_TICKS);
        }
        lastOffhandSeen = offhand.copy();
        if (offhandManualCooldown > 0) {
            offhandManualCooldown--;
            return;
        }
        if (offhandRationCooldown > 0) {
            offhandRationCooldown--;
            return;
        }
        // 副手已经是白名单食物：就位。这里刻意不判「此刻吃得下」—— 饱食度满 20 时普通食物吃不下，
        // 但口粮本来就该备在副手（要吃的判断在 isEdible，不影响提前备货）
        if (isWhitelistedFood(offhand)) {
            // 不满一组时与背包里同种、明显更满的一份互换（用户 2026-09-19「我要补到副手」）：
            // 补给取货把背包补到一组后，这里就把那一组换进副手，副手始终拿满组口粮，零头退回背包。
            // 「明显更满」的差值下限见 RATION_SWAP_MIN_GAIN（防止两份接近时来回换）
            if (offhand.getCount() < offhand.getMaxStackSize() && !isPlayerInventoryClickBlocked()) {
                int fuller = findFullerSameFoodSlot(offhand);
                if (fuller >= 0) {
                    swapWithOffhand(fuller);
                    offhandRationCooldown = 6;
                    eatLog(0, "副手口粮 ▸ 与槽位{}互换（{} 个 → 一组）", fuller, offhand.getCount());
                } else {
                    foodLog(40, "副手口粮 ▸ 只有 {} 个，背包里没有明显更满的同类（等补给）", offhand.getCount());
                }
            }
            return;
        }
        // 副手不是白名单食物 → 一律让位（用户 2026-09-19 实测：副手放工具时食物也该切进来）。
        // 之前那条「带经验修补的工具不抢」的保护已按实测去掉：挂机修复点（REPAIR）自己会把要修的
        // 工具换到副手、修完换回（互换守恒），不需要这里预留；且 REPAIR 态本就不调本方法
        if (!offhand.isEmpty()) {
            foodLog(20, "副手口粮 ▸ 顶掉副手里的 {} x{}（不是白名单食物）",
                offhand.getHoverName().getString(), offhand.getCount());
        }
        // 容器开着（卸货 / 补给会话，或玩家自己开的箱子）：此刻背包点击会被客户端当「窗口不匹配」
        // 丢掉，只等下一轮。这里刻意<b>不去关它</b> —— 玩家自己开的箱子不该被模块关掉，
        // 而模块自己的容器会话由 SUPPLY / UNLOADING 那套流程负责收尾（那两个状态不调本方法）
        if (isPlayerInventoryClickBlocked()) {
            foodLog(20, "副手口粮 ▸ 搬不进去：容器开着(containerId={}, 界面={})，等收尾",
                mc.player.containerMenu == null ? -1 : mc.player.containerMenu.containerId,
                mc.screen == null ? "无" : mc.screen.getClass().getSimpleName());
            return;
        }
        int slot = findBestFoodSlot(0, 35, mc.player.getFoodData(), false);
        if (slot < 0) {
            foodLog(40, "副手口粮 ▸ 背包里没有白名单食物（白名单 {} 项）→ 交给补给判定",
                module.getFoodWhitelist().size());
            return;
        }
        ItemStack food = mc.player.getInventory().getItem(slot); // 换之前读：换完这一格就是副手那件了
        String foodName = food.getHoverName().getString();
        int foodCount = food.getCount();
        swapWithOffhand(slot);
        offhandRationCooldown = 6;
        foodLog(0, "副手口粮 ▸ 槽位{} 的 {} x{} 换入副手", slot, foodName, foodCount);
    }

    /**
     * 副手的变化是不是「玩家自己弄的」（连按 F 换手 / 手动放东西）。
     *
     * <p>只比物品与组件、不比数量：吃一口会让数量减 1，那不是玩家操作。「食物 → 空」与
     * 「食物 → 同种食物」都归为「刚好吃完一份」。</p>
     */
    private boolean isManualOffhandChange(ItemStack offhand) {
        if (ItemStack.isSameItemSameComponents(offhand, lastOffhandSeen)) return false;
        boolean wasFood = isWhitelistedFood(lastOffhandSeen);
        return !(wasFood && (offhand.isEmpty() || isWhitelistedFood(offhand)));
    }

    /**
     * 背包（0~35）里<b>同种</b>、且数量比副手那份更多的白名单食物堆 —— 与它互换就等于把更满的一份换到副手。
     *
     * <p>用户 2026-09-19「我要补到副手」：补给取货补的是背包（见 {@link #countItemInInventory}），
     * 补到一组后由这里把整组换进副手，副手那份零头退回背包 —— 副手永远是一组口粮。
     * 只认同种食物，不会把金苹果换成别的东西。</p>
     *
     * @return 槽位（{@code -1} = 背包里没有同种且更多的）
     */
    private int findFullerSameFoodSlot(ItemStack offhand) {
        Inventory inventory = mc.player.getInventory();
        int bestSlot = -1;
        int bestCount = offhand.getCount();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !stack.is(offhand.getItem()) || !isWhitelistedFood(stack)) continue;
            // 必须「明显更多」才换：两份数量接近时按数量大小来回互换，会变成吃一口换一次
            if (stack.getCount() < offhand.getCount() + RATION_SWAP_MIN_GAIN) continue;
            if (stack.getCount() > bestCount) {
                bestCount = stack.getCount();
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 当前进食用的手 */
    private InteractionHand eatHand() {
        return eatFromOffhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    /**
     * 发包连吃：把背包里「此刻真能吃」的食物换到手上并连续吃完，直到饱食度满 / 没得吃。
     *
     * <p>每刻调用（状态机在 EATING 态与「血量未满」的回血分支里调它）。取值口径：
     * 食物 = 食物白名单内且带 {@code FOOD} 组件的物品，能不能吃再按原版判据
     * （{@code Consumable#canConsume → Player#canEat}：饱食度未满，或该食物 {@code canAlwaysEat}）；
     * 优先级 = 营养 + 饱和度从高到低，金苹果类排在普通食物之后（它们能在饱食度满时吃，
     * 留给战斗中救急，平时不当作口粮消耗）。</p>
     */
    public void packetEat(boolean fromOffhand) {
        if (mc.player == null || mc.gameMode == null) return;
        // 模式只在「没在吃」时可改：中途换手会把这一口作废（LivingEntity#updatingUsingItem）
        if (eatHotbarSlot < 0) eatFromOffhand = fromOffhand;
        if (mc.player.isDeadOrDying()) {
            stopPacketEat();
            return;
        }

        FoodData foodData = mc.player.getFoodData();

        // 一、在吃的那一件：推进到「吃掉 / 判没吃上」为止，期间不挑新的、不换槽
        if (eatHotbarSlot >= 0 && !advanceEating()) return;
        if (eatCooldown > 0) {
            eatCooldown--;
            return;
        }
        // 等「背包 → 快捷栏」那一下到账：期间别发包，否则手里还是镐子就照发了用食包
        if (eatMoveCooldown > 0) {
            eatMoveCooldown--;
            return;
        }

        // 二、副手模式（「边挖边吃」）：食物常驻副手，主手与选定槽全程不动 —— Baritone 照常挖矿。
        // 副手此刻不是可吃的食物（刚吃完 / 还没备货 / 被别的东西占了）→ 交给常驻口粮维护去补一份，
        // 补上后下一轮起手。它自带节流，并在搬运窗口内挡住「食物吃完」的误判（见 isEatingInProgress）
        if (eatFromOffhand) {
            if (isEdible(mc.player.getOffhandItem(), foodData)) {
                beginEating(EAT_OFFHAND_SLOT);
                return;
            }
            tickOffhandRation();
            return;
        }

        // 三、挑食物：先看快捷栏，再看主背包（不在快捷栏就从背包搬一个进来）
        int slot = findBestFoodSlot(0, 8, foodData);
        if (slot < 0) {
            int backpackSlot = findBestFoodSlot(9, 35, foodData);
            if (backpackSlot < 0) {
                clearEating(); // 没有可吃的：交回状态机（补给 / 继续挖矿）
                return;
            }
            // 腾位置策略与旧实现一致：空槽 → 垃圾格 → 数量最少的一组目标矿 → 最后才动垫脚方块，
            // 被顶掉的那一格记下来，退出进食时由 restoreEatDisplacedItem() 换回原位
            int targetSlot = findTemporaryHotbarSlot();
            if (targetSlot < 0) {
                clearEating(); // 快捷栏全是工具与食物，腾不出位置
                return;
            }
            if (isPlayerInventoryClickBlocked()) {
                // 容器还开着：此刻换槽会被客户端当窗口不匹配吞掉，先收尾容器，下一轮再搬
                eatLog(20, "搬食物被容器挡住 ▸ 先收容器(containerId={}, 界面={})",
                    mc.player.containerMenu == null ? -1 : mc.player.containerMenu.containerId,
                    mc.screen == null ? "无" : mc.screen.getClass().getSimpleName());
                closeContainer();
                return;
            }
            ItemStack displaced = mc.player.getInventory().getItem(targetSlot);
            if (!displaced.isEmpty()) {
                eatDisplacedHotbarSlot = targetSlot;
                eatDisplacedItem = displaced.getItem();
            }
            moveToHotbar(backpackSlot, targetSlot);
            eatMoveCooldown = 5;
            return;
        }

        beginEating(slot);
    }

    /** 起手吃一件：切到那一格、按着右键、发第一包（原版入口，本地与服务端同时开始用食） */
    private void beginEating(int slot) {
        Inventory inventory = mc.player.getInventory();
        // 副手槽位（40）不走 getInventory().getItem，见 eatStackAt
        ItemStack stack = eatStackAt(slot);
        // 起手前再确认一次：换槽 / 卸货 / 拾取都可能在挑完之后改掉这一格，
        // 若那时手里是方块，useItem 会把它直接放出去
        if (!isEdible(stack, mc.player.getFoodData())) {
            eatCooldown = EAT_RETRY_COOLDOWN_TICKS;
            return;
        }

        // 副手模式绝不碰选定槽：主手要一直拿着镐给 Baritone 挖矿用
        if (!eatFromOffhand) inventory.setSelectedSlot(slot);
        eatHotbarSlot = slot;
        eatItem = stack.getItem();
        eatCountBefore = stack.getCount();
        eatWaitTicks = 0;
        eatResendTicks = EAT_START_GRACE_TICKS;
        // 自动进食期间压制本地用食态（动画与 RELEASE 的唯一来源，见 SilentEat /
        // LocalPlayerEatAnimationMixin）。必须在起手包之前打开：本地那次 ItemStack#use 会调
        // startUsingItem，服务端随后同步回来的用食标志还会再调一次 —— 两处都得压住。
        // 我们本来就只看服务端下发的那格数量，不需要本地预测（起手后也不必再手动 stopUsingItem）。
        SilentEat.setSuppressed(true);
        mc.gameMode.useItem(mc.player, eatHand());
        eatLog(0, "起手 ▸ {}槽位{} 选中槽{} {} x{} · 容器id{} · 界面{}",
            eatFromOffhand ? "副手" : "主手", slot, inventory.getSelectedSlot(), eatItem, eatCountBefore,
            mc.player.containerMenu == null ? -1 : mc.player.containerMenu.containerId,
            mc.screen == null ? "无" : mc.screen.getClass().getSimpleName());
    }

    /**
     * 推进在吃的那一件。
     *
     * @return true 表示这一件已经结束（吃掉了 / 没吃上），调用方可以接着挑下一件
     */
    private boolean advanceEating() {
        eatWaitTicks++;

        ItemStack held = eatHeldStack();
        boolean holding = !held.isEmpty() && held.getItem() == eatItem;

        // 1) 手上那格数量少了 = 服务端结算了这一件（吃完）
        if (holding && held.getCount() < eatCountBefore) {
            EAT_LOG.info("吃掉一件 ▸ {} x{} → x{}", eatItem, eatCountBefore, held.getCount());
            clearEating();
            return true;
        }
        // 2) 手上的食物被换走（Baritone 换工具 / 秒破换槽 / 拾取顶格）：这一件没吃上，冷却后重挑
        if (!holding) {
            EAT_LOG.info("中断 ▸ 手里已不是{}（实际 {} x{} · 槽位{}）▸ 冷却后重挑",
                eatItem, held.getItem(), held.getCount(), eatHotbarSlot);
            clearEating();
            eatCooldown = EAT_RETRY_COOLDOWN_TICKS;
            return true;
        }

        // 3) 定期补包（自愈）。服务端有一条「手上那格物品变了就停掉用食」的规则
        //    （LivingEntity#updatingUsingItem → ItemStack.isSameItem 不成立 → stopUsingItem），
        //    挖矿那侧的换手动作（秒破换工具 / 连锁 / 拾取顶格）随时可能命中它：
        //    只发一次起手包的话，这一口被作废就得干等满 EAT_CONSUME_TIMEOUT_TICKS 超时才重试
        //    （用户实机 2026-09-19：「吃半天才生效」）。服务端真在吃时，重复的 UseItem 包会被
        //    LivingEntity#startUsingItem 的 !isUsingItem() 卫语句忽略 —— 补发是纯冗余、无副作用，
        //    换来的是被打断后 EAT_RESEND_INTERVAL 刻内自动续上。
        if (eatResendTicks > 0) {
            eatResendTicks--;
        } else {
            resendUseItem();
            eatResendTicks = EAT_RESEND_INTERVAL;
        }
        // 诊断：还在等结算（每秒一条）——有这条而数量一直不动 = 服务端那口没吃上（被顶掉/被拒）
        eatLog(20, "等待服务端结算 ▸ 已{}刻 · 数量{}/{}",
            eatWaitTicks, held.getCount(), eatCountBefore);

        // 4) 等太久：这件判没吃上（服务端拒绝 / 一直被换手顶掉），冷却后再挑
        if (eatWaitTicks > EAT_CONSUME_TIMEOUT_TICKS) {
            EAT_LOG.info("超时放弃 ▸ 已等{}刻 · 数量{}/{} ▸ 冷却后重挑",
                eatWaitTicks, held.getCount(), eatCountBefore);
            clearEating();
            eatCooldown = EAT_RETRY_COOLDOWN_TICKS;
            return true;
        }
        return false;
    }

    /** 发一包物品使用包（走 {@link ClientPacketSender} 直发通道，不进发包闸门）：补发用 */
    private void resendUseItem() {
        if (mc.player == null) return;
        int sequence = ClientPacketSender.nextPredictionSequence();
        if (sequence < 0) return;
        ClientPacketSender.sendUseItem(eatHand(), sequence,
            mc.player.getYRot(), mc.player.getXRot());
    }

    /**
     * 进食诊断日志：写 {@code yiyiaddon/eat}（进 latest.log，不进聊天），同一状态最多每
     * {@code intervalTicks} 刻一条。排查「吃不上」时看这里的四条线：
     * 起手 / 吃掉一件 / 中断（手里被换手） / 等待服务端结算（补包不生效） / 超时放弃。
     */
    private void eatLog(int intervalTicks, String format, Object... args) {
        if (mc.player == null) return;
        if (mc.player.tickCount - lastEatLogTick < intervalTicks) return;
        lastEatLogTick = mc.player.tickCount;
        EAT_LOG.info(format, args);
    }

    /** 食物流转诊断日志（{@code yiyiaddon/food}），限流口径同 {@link #eatLog(int, String, Object...)} */
    private void foodLog(int intervalTicks, String format, Object... args) {
        if (mc.player == null) return;
        if (mc.player.tickCount - lastFoodLogTick < intervalTicks) return;
        lastFoodLogTick = mc.player.tickCount;
        FOOD_LOG.info(format, args);
    }

    /**
     * 这件物品此刻能否吃：<b>食物白名单内</b> + 带 FOOD 组件 + 原版允许（{@code Consumable#canConsume} 的口径）。
     *
     * <p>用户 2026-09-19 裁定「严格白名单：只吃也只留白名单食物」：此前只判「有 FOOD 组件」，
     * 怪物掉的腐肉会照吃不误（实机反馈「白名单食物只有金苹果，怎么会吃腐肉」）。</p>
     */
    private boolean isEdible(ItemStack stack, FoodData foodData) {
        if (stack.isEmpty() || mc.player == null) return false;
        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) return false;
        if (!isWhitelistedFood(stack)) return false;
        return food.canAlwaysEat() || mc.player.getAbilities().invulnerable || foodData.getFoodLevel() < 20;
    }

    /**
     * 是否为「食物白名单」内的食物（白名单 + 带 FOOD 组件）。
     *
     * <p>吃（{@link #isEdible}）、留（{@link #shouldKeep}）、腾快捷栏（{@link #findTemporaryHotbarSlot}）
     * 三处共用同一判据；状态机侧的同源判据是 {@code hasFoodToEat / countFoodStacks}。</p>
     */
    private boolean isWhitelistedFood(ItemStack stack) {
        if (stack.isEmpty() || !stack.has(DataComponents.FOOD)) return false;
        return module.getFoodWhitelist().contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
    }

    /** 指定区间（含两端，背包下标）里最该吃的那一格；只挑此刻真能吃下的。没有则 -1 */
    private int findBestFoodSlot(int from, int to, FoodData foodData) {
        return findBestFoodSlot(from, to, foodData, true);
    }

    /**
     * 指定区间（含两端，背包下标）里最该吃的那一格；没有则返回 -1。
     *
     * @param requireEdible true = 只挑此刻真能吃下的（进食挑食用，饱食度满 20 时普通食物不算）；
     *                      false = 只要是白名单食物就挑（备副手口粮用：口粮该提前备着，不必等饿）
     */
    private int findBestFoodSlot(int from, int to, FoodData foodData, boolean requireEdible) {
        Inventory inventory = mc.player.getInventory();
        int bestSlot = -1;
        int bestScore = -1;
        for (int i = from; i <= to; i++) {
            ItemStack stack = inventory.getItem(i);
            if (requireEdible ? !isEdible(stack, foodData) : !isWhitelistedFood(stack)) continue;
            int score = foodScore(stack.get(DataComponents.FOOD));
            if (score > bestScore) {
                bestScore = score;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /**
     * 食物的取舍分数：营养 + 饱和度越高越优先。
     *
     * <p>金苹果类（{@code canAlwaysEat}）恒为 0 分，排在所有普通食物之后 —— 它们能在饱食度满时
     * 吃下去救急，不该被当口粮在平时消耗掉（白名单里同时有金苹果与普通食物时，先吃普通食物）。</p>
     */
    private int foodScore(FoodProperties food) {
        if (food == null) return 0;
        return food.canAlwaysEat() ? 0 : 1000 + food.nutrition() * 10 + Math.round(food.saturation());
    }

    /**
     * 背包格 → 热键栏格。
     *
     * <p>对应旧项目 {@code InvUtils.move().from(背包槽).to(空热键栏槽)}。26.1.2 的等价原语是
     * {@code ContainerInput.SWAP}：{@code slotNum} 传 InventoryMenu 的主背包槽下标（9~35，与 Inventory
     * 下标同值），{@code buttonNum} 传热键栏下标（0~8）；服务端在 {@code AbstractContainerMenu#doClick}
     * 里按 {@code inventory.getItem(buttonNum)} 与目标槽互换，热键栏为空时即完成整组搬运。</p>
     */
    private void moveToHotbar(int inventorySlot, int hotbarSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, inventorySlot, hotbarSlot,
            ContainerInput.SWAP, mc.player);
    }

    /**
     * 把「背包 / 快捷栏的这一格」与副手互换。
     *
     * <p>走 {@code ContainerInput.SWAP} + {@code button = 40}：InventoryMenu 的「该槽 ↔ 副手」交换，
     * 等价于在背包界面把鼠标悬在该槽上按 F。一步到位 —— 不碰选定槽、不动护甲区。</p>
     *
     * <p><b>踩过的坑</b>（用户 2026-09-19：「怎么一直换我的头盔？」）：容器点击的 {@code slotNum} 是
     * <b>菜单槽下标</b>，0~8 落在合成格与护甲格上（5 = 头盔）；快捷栏 0~8 在菜单里对应 36~44，
     * 必须映射（与 {@link #dropStack(int)} 同一口径）。当初直接传快捷栏下标，才把头盔换进了副手。</p>
     *
     * @param invSlot 0~8 快捷栏 / 9~35 主背包（{@code Inventory} 下标）
     */
    private void swapWithOffhand(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 0 || invSlot > 35) return;
        int menuSlot = invSlot < 9 ? 36 + invSlot : invSlot;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuSlot,
            EAT_OFFHAND_SLOT, ContainerInput.SWAP, mc.player);
        offhandSwapGrace = 4; // 这几刻内副手变化是本模块造成的（服务端回同步），不算「玩家动过」
    }

    // ═══════════════════════════════════════════════════════════════════
    //  快捷栏维护（本项目新增，用户 2026-09-17 反馈）
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 是否不能操作玩家背包。
     *
     * <p>客户端对 containerId 不匹配的点击会直接丢弃并打警告
     * （{@code Ignoring click in mismatching container. Click in 0, player has 48}），
     * 也就是说「还有容器开着」（模块自己的箱子会话没收尾、或服务端开了别的容器）时，
     * 背包换槽与丢垃圾会全部静默失效且刷日志。</p>
     */
    public boolean isPlayerInventoryClickBlocked() {
        return mc.player == null || mc.player.containerMenu == null || mc.player.containerMenu.containerId != 0;
    }

    /** 第一个空的热键栏槽位；没有则返回 -1 */
    private int findEmptyHotbarSlot() {
        Inventory inventory = mc.player.getInventory();
        for (int i = 0; i < 9; i++) {
            if (inventory.getItem(i).isEmpty()) return i;
        }
        return -1;
    }

    /**
     * 可临时占用的热键栏槽位，按优先级挑：
     * <ol>
     *   <li>空槽；</li>
     *   <li>非工具 / 非白名单食物 / 非搭路方块 / 非目标矿的「垃圾格」；</li>
     *   <li>目标矿中<b>数量最少</b>的一格（只是换个格子，数量不丢，卸货时照常进箱子）；</li>
     *   <li>搭路方块中数量最少的一格（最后手段：Baritone 放方块也只看快捷栏，尽量少动）。</li>
     * </ol>
     * 工具与白名单食物永不被顶。
     *
     * <p>为什么第 3/4 档必须存在：矿工快捷栏经常被七八组目标矿塞满（用户 2026-09-17 实机截图），
     * 只肯顶垃圾格会导致「食物换不进快捷栏一直空吃」「铲子在背包却永远进不来挖土」。</p>
     */
    private int findTemporaryHotbarSlot() {
        int empty = findEmptyHotbarSlot();
        if (empty != -1) return empty;

        Inventory inventory = mc.player.getInventory();
        List<String> placeBlocks = module.settings().placeBlocks;
        int junkSlot = -1;
        int oreSlot = -1;
        int oreCount = Integer.MAX_VALUE;
        int blockSlot = -1;
        int blockCount = Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;
            if (isTool(stack)) continue;
            if (isWhitelistedFood(stack)) continue; // 白名单食物不顶：留着给发包连吃（口径同 shouldKeep）
            if (isAllowedOre(stack)) {
                if (stack.getCount() < oreCount) {
                    oreCount = stack.getCount();
                    oreSlot = i;
                }
                continue;
            }
            if (isPlaceBlock(stack, placeBlocks)) {
                if (stack.getCount() < blockCount) {
                    blockCount = stack.getCount();
                    blockSlot = i;
                }
                continue;
            }
            if (junkSlot == -1) junkSlot = i;
        }
        if (junkSlot != -1) return junkSlot;
        return oreSlot != -1 ? oreSlot : blockSlot;
    }

    /**
     * 当前准备吃的食物名（进食进度播报用）。
     *
     * <p>旧实现直接播报主手物品名 —— 食物还没换进快捷栏时主手还是镐子，于是出现
     * 「进食中 ▸ 下界合金镐」（用户 2026-09-17）。改为按「此刻真能吃」的食物解析：
     * 手上就是食物时用它，否则取背包里最该吃的那一件（与 {@link #packetEat()} 同一套挑选口径）。</p>
     */
    public String describeEatingFood() {
        if (mc.player == null) return "食物";
        Inventory inventory = mc.player.getInventory();
        // 副手模式：食物就在副手（此时选定槽还拿着镐，不能按主手读）
        ItemStack offhand = mc.player.getOffhandItem();
        if (isWhitelistedFood(offhand)) return offhand.getHoverName().getString();
        ItemStack selected = inventory.getItem(inventory.getSelectedSlot());
        if (isWhitelistedFood(selected)) return selected.getHoverName().getString();
        int slot = findBestFoodSlot(0, 35, mc.player.getFoodData());
        return slot < 0 ? "食物" : inventory.getItem(slot).getHoverName().getString();
    }

    /**
     * 把进食时被顶掉的热键栏物品换回原位（退出进食时调用）。
     *
     * <p>只有当那一格现在还是食物或空着、且被顶掉的物品确实还在背包里时才换回，
     * 避免与玩家手动整理的背包打架。</p>
     */
    public void restoreEatDisplacedItem() {
        int slot = eatDisplacedHotbarSlot;
        Item item = eatDisplacedItem;
        if (slot < 0 || item == null || mc.player == null) {
            eatDisplacedHotbarSlot = -1;
            eatDisplacedItem = null;
            return;
        }
        // 容器还开着时点击会被客户端丢弃：保留记录，下个 tick 再试（这里不能先清字段）
        if (isPlayerInventoryClickBlocked()) return;
        eatDisplacedHotbarSlot = -1;
        eatDisplacedItem = null;

        Inventory inventory = mc.player.getInventory();
        ItemStack occupied = inventory.getItem(slot);
        // 那一格现在还是白名单食物（与 shouldKeep / 发包连吃的口径一致）或空着，才可以换回
        boolean occupiedIsFoodOrEmpty = occupied.isEmpty() || isWhitelistedFood(occupied);
        if (!occupiedIsFoodOrEmpty) return;
        for (int i = 9; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                moveToHotbar(i, slot);
                return;
            }
        }
    }

    /**
     * 确保各类工具至少有一把在快捷栏里。
     *
     * <p>反编译本版 Baritone 的 {@code ToolSet#getBestSlot}：它的选工具循环上界就是 9，
     * 只扫快捷栏 0-8，背包里的工具它根本看不到（{@code InventoryBehavior#requestMove} 更是无人调用），
     * 所以「自动切换工具」开着也会出现镐子躺在背包里挖不动的情况（用户 2026-09-17）。
     * 由模块自己把缺失的工具类型搬进快捷栏：只用空槽或「最不重要」的格子，不动关键物品。</p>
     *
     * @return 是否发起了换槽（调用方可据此延后一帧再动作）
     */
    public boolean ensureToolsInHotbar() {
        if (mc.player == null) return false;
        Inventory inventory = mc.player.getInventory();
        boolean moved = false;
        for (String suffix : new String[]{"_pickaxe", "_shovel", "_axe", "_hoe", "_sword"}) {
            boolean inHotbar = false;
            for (int i = 0; i < 9; i++) {
                if (matchesToolSuffix(inventory.getItem(i), suffix)) {
                    inHotbar = true;
                    break;
                }
            }
            if (inHotbar) continue; // 该类型已在快捷栏，不折腾

            int source = -1;
            for (int i = 9; i < 36; i++) {
                if (matchesToolSuffix(inventory.getItem(i), suffix)) {
                    source = i;
                    break;
                }
            }
            if (source == -1) continue; // 背包里也没有这一类型

            if (isPlayerInventoryClickBlocked()) return moved; // 容器开着：先收尾，下一轮再搬
            // 腾位置策略与进食一致：空槽 → 垃圾格 → 数量最少的一组目标矿 → 最后才动垫脚方块。
            // 只肯顶垃圾格会出问题：快捷栏常被几组矿塞满 → 找不到位置 →
            // 铲子永远进不来（用户 2026-09-17：「铲子在背包没有被调用到快捷栏挖土」）
            int target = findTemporaryHotbarSlot();
            if (target == -1) continue; // 快捷栏全是工具与食物，放弃这一类
            moveToHotbar(source, target);
            moved = true;
        }
        return moved;
    }

    private boolean matchesToolSuffix(ItemStack stack, String suffix) {
        return !stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith(suffix);
    }
}
