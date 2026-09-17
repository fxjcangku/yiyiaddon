package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 容器交互助手
 * 
 * 功能：
 * · 静默垃圾丢弃器（分频发包规避反作弊）
 * · 极速卸货流（高速 SlotClick 转移矿物）
 * · 食物提取与自动进食
 *
 * <p>对应旧项目 {@code mining/service/ContainerHelper.java}（584 行）逐条移植：
 * 常量、状态字段、槽位映射与发包语义全部照旧；换掉的两处外部依赖是</p>
 * <ul>
 *   <li>旧 {@code farm.FarmPacketOps.interactBlock} → {@code platform/network/BlockPacketSender.interactBlock}；</li>
 *   <li>旧 Meteor {@code InvUtils.move()/swap()} → 26.1.2 的
 *       {@code handleContainerInput(..., ContainerInput.SWAP, ...)} 与 {@code Inventory#setSelectedSlot}
 *       （与本项目 {@code feature/stardew/adapter/DefaultStardewAdapter} 同一做法）。</li>
 * </ul>
 */
public final class MiningContainer {

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
    private static final int TRASH_DISPOSAL_INTERVAL = 5; // 每5 tick丢一次垃圾

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
    private int eatMoveCooldown = 0;
    private static final int MAX_OPEN_ATTEMPTS = 5;

    // 进食时临时占用快捷栏的记录（用户 2026-09-17：食物不在快捷栏时「跟垫脚方块切换但换不回来」）：
    // 旧实现没空槽就固定顶掉快捷栏 8 号位且永不归还，玩家的垫脚方块被顶进背包后再也不回来。
    // 现在记录被顶掉的槽位与物品，退出进食时换回原位。
    private int eatDisplacedHotbarSlot = -1;
    private Item eatDisplacedItem = null;

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
        trashDisposalCooldown = 0;
        openAttempts = 0;
        openingPos = null;
        openingCooldown = 0;
        eatMoveCooldown = 0;
        eatDisplacedHotbarSlot = -1;
        eatDisplacedItem = null;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  垃圾丢弃
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 静默垃圾丢弃器（每 tick 调用）
     * 
     * 反逻辑：默认全丢，只保留白名单内的物品
     * 分频发包规避反作弊：每5 tick丢一个物品
     * 
     * @param keepWhitelist 保留白名单（此名单内的物品/方块不会被丢弃）
     * @param placeBlocks Baritone搭路方块白名单（只保留各一组，多余丢弃）
     */
    public void tickTrashDisposal(List<String> keepWhitelist, List<String> placeBlocks) {
        if (mc.player == null) return;

        trashDisposalCooldown--;
        if (trashDisposalCooldown > 0) return;

        Inventory inventory = mc.player.getInventory();

        // 副手优先：有经验修补/工具/食物/目标矿物/保留白名单/搭路方块才保留，其余丢弃
        ItemStack offhand = mc.player.getOffhandItem();
        if (!offhand.isEmpty()
            && !shouldKeep(offhand, keepWhitelist)
            && !isPlaceBlock(offhand, placeBlocks)) {
            dropOffhand();
            trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
            return;
        }

        // 主背包 0-35
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;

            // 搭路方块：只保留一组（最多 64 个），多余的组丢掉。
            // （用户 2026-09-17 明确「我的默认是一组」；截图里留下 3 组原石就是走到了这里。
            //  早期版本「超过 64 就整组丢」曾把玩家要用的垫脚方块莫名扔掉，因此现在：
            //  ① 只保留数量最多的那一组（同数量时保留靠前的），② 手持那一格永不动，
            //  ③ 只有确实超过一组时才丢，且每次丢一组、分频发包）
            if (isPlaceBlock(stack, placeBlocks)) {
                if (countItem(inventory, stack.getItem()) > 64
                    && i != inventory.getSelectedSlot()
                    && !isPrimaryPlaceStack(inventory, i, stack)) {
                    dropStack(i);
                    trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
                    return;
                }
                continue;
            }

            // 非搭路方块：不在保留白名单内的全部丢弃
            if (shouldKeep(stack, keepWhitelist)) continue;

            dropStack(i);
            trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
            return;
        }
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
     * 默认只留：任意品质工具（镐/铲/斧/剑/锄）、食物白名单、目标矿物、手动保留名单、经验修补物品。
     * 其余全部视为垃圾丢弃。
     */
    private boolean shouldKeep(ItemStack stack, List<String> keepWhitelist) {
        if (stack.isEmpty()) return true;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (isTool(stack)) return true;                                    // 工具（任何品质）
        if (module.getFoodWhitelist().contains(itemId)) return true;        // 食物白名单
        if (isAllowedOre(stack)) return true;                              // 目标矿物
        if (hasMending(stack)) return true;                                // 经验修补附魔（保护好装备）
        return keepWhitelist.contains(itemId);                             // 手动保留名单
    }

    /** 是否为可保留的工具（镐/铲/斧/剑/锄，任意材质） */
    private boolean isTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel")
            || id.endsWith("_axe") || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /** 物品是否带经验修补附魔（26.x 附魔是动态注册表，需从世界注册表解析） */
    private boolean hasMending(ItemStack stack) {
        if (stack.isEmpty() || mc.level == null) return false;
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return false;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(Enchantments.MENDING).orElse(null);
            return holder != null && enchantments.getLevel(holder) > 0;
        } catch (Exception ignored) {
            return false;
        }
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
            // button=1 + THROW = 丢弃整组（等价 Ctrl+Q），与 Meteor InvUtils.drop() 同语义
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
        BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
    }

    /**
     * 关闭容器
     *
     * 只在当前 Screen 确实是容器界面时才关：player.closeContainer() 会无条件关掉
     * 当前打开的任意 Screen，若在 Meteor GUI 打开时调用会把面板一起关掉。
     * 注意 containerMenu 判空没用——玩家自身背包菜单始终非 null。
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
     * Meteor 的 InvUtils.shiftClick 仍走旧 API，在 26.1.2 下发包无效（物品不被移动）。
     */
    private void quickMove(AbstractContainerMenu menu, int slotIndex) {
        ContainerAccess.quickMove(menu, slotIndex);
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

        // 2) 在箱子侧找「白名单 + 带 FOOD + 还没满一组」的食物
        boolean blocked = false;
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || !stack.has(DataComponents.FOOD)) continue;
            if (!whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;

            // 目标 = 该物品自己的「一组」（普通食物 64、蜂蜜瓶 16、汤类 1），不写死 64
            int maxStack = stack.getMaxStackSize();
            int current = countItemInInventory(inventory, stack.getItem());
            if (current >= maxStack) continue; // 已够一组（含整组以上的余量堆）

            if (!canAcceptIntoInventory(inventory, stack.getItem())) {
                blocked = true; // 背包塞不下这个物品，继续看还有没有别的能塞的
                continue;
            }

            if (stack.getCount() <= maxStack - current) {
                // 这一格整组拿走也不会超过一组 → Shift 整格最省事
                pendingWithdrawItem = stack.getItem();
                pendingWithdrawCountBefore = current;
                pendingWithdrawWaitTicks = 0;
                quickMove(menu, slot.index);
                actionCooldown = 5;
                return FoodWithdrawResult.WORKING;
            }

            // 这一格比缺口多：精确补一组，多出来的原样留在箱子里（用户 2026-09-18 要求）
            startPreciseFill(menu, slot.index, stack.getItem());
            return FoodWithdrawResult.WORKING;
        }

        // 没有任何「还缺的白名单食物」可拿：确实取完了；若有方块因背包塞不下而被跳过，则是「拿不进来」
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

    /** 背包内指定物品的总数量（主背包 0-35，含快捷栏） */
    private int countItemInInventory(Inventory inventory, Item item) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) count += stack.getCount();
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

    /**
     * 自动进食直到饥饿值回满。
     *
     * 修正要点：
     * 1. 从背包拿食物到热键栏这一步「只移动、不进食」，等物品到账后再吃，避免空手按住右键一直放方块。
     * 2. 用 gameMode.useItem 直接触发进食，不依赖 keyUse 按键状态（窗口失焦/开 GUI 时按键会被吞）。
     * 3. keyUse.setDown(true) 仅用于防止游戏循环主动 releaseUsingItem，保持持续进食。
     */
    public void autoEat() {
        if (mc.player == null) return;

        FoodData foodData = mc.player.getFoodData();
        if (foodData.getFoodLevel() >= 20) {
            mc.options.keyUse.setDown(false);
            return;
        }

        // 正在等待「背包→热键栏」的移动到账，期间不发重复包，也不按住右键
        if (eatMoveCooldown > 0) {
            eatMoveCooldown--;
            mc.options.keyUse.setDown(false);
            return;
        }

        List<String> foodWhitelist = module.getFoodWhitelist();
        Inventory inventory = mc.player.getInventory();

        // 第一步：热键栏找白名单中营养值最高的食物
        int bestHotbarSlot = -1;
        int bestHotbarNutrition = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !foodWhitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
            var foodComp = stack.get(DataComponents.FOOD);
            if (foodComp == null) continue;
            if (foodComp.nutrition() > bestHotbarNutrition) {
                bestHotbarNutrition = foodComp.nutrition();
                bestHotbarSlot = i;
            }
        }

        // 第二步：热键栏没有，从背包拿一个到热键栏（只移动，不进食）
        if (bestHotbarSlot == -1) {
            int bestBackpackSlot = -1;
            int bestBackpackNutrition = 0;
            for (int i = 9; i < 36; i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.isEmpty() || !foodWhitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
                var foodComp = stack.get(DataComponents.FOOD);
                if (foodComp == null) continue;
                if (foodComp.nutrition() > bestBackpackNutrition) {
                    bestBackpackNutrition = foodComp.nutrition();
                    bestBackpackSlot = i;
                }
            }

            if (bestBackpackSlot == -1) {
                mc.options.keyUse.setDown(false);
                return;
            }

            // 目标热键栏槽位：优先空槽；没有空槽时临时顶掉一格并记录，退出进食时换回原位。
            // 旧实现固定顶 8 号位且不归还，玩家的垫脚方块因此被顶进背包再也不回来（用户 2026-09-17）；
            // 而只肯顶「垃圾格」又会出现「快捷栏被几组矿塞满 → 食物永远换不进去 → 一直空吃」，
            // 所以这里允许临时顶掉一组矿石/垫脚方块（数量最少的那一格），吃完由 restoreEatDisplacedItem() 换回。
            int targetHotbarSlot = findTemporaryHotbarSlot();
            if (targetHotbarSlot == -1) {
                // 快捷栏全是工具与食物，确实没有可腾的位置：本 tick 先不吃，交给超时保护
                mc.options.keyUse.setDown(false);
                return;
            }
            ItemStack displaced = inventory.getItem(targetHotbarSlot);
            if (!displaced.isEmpty()) {
                eatDisplacedHotbarSlot = targetHotbarSlot;
                eatDisplacedItem = displaced.getItem();
            }

            if (isPlayerInventoryClickBlocked()) {
                // 还有容器开着：此刻换槽会被客户端当窗口不匹配吞掉（日志刷 Ignoring click），先收尾容器
                closeContainer();
                mc.options.keyUse.setDown(false);
                return;
            }

            moveToHotbar(bestBackpackSlot, targetHotbarSlot);
            eatMoveCooldown = 5; // 等 5 tick 到账
            mc.options.keyUse.setDown(false);
            return;
        }

        // 第三步：切到食物槽，直接触发进食
        inventory.setSelectedSlot(bestHotbarSlot);
        mc.options.keyUse.setDown(true);
        if (!mc.player.isUsingItem()) {
            // 直接 useItem 触发进食（食物使用与准星/方块无关，窗口失焦也能吃到）
            if (mc.gameMode != null) mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
        }
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
            if (module.getFoodWhitelist().contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
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
     * 「进食中 ▸ 下界合金镐」（用户 2026-09-17）。改为按白名单食物解析。</p>
     */
    public String describeEatingFood() {
        if (mc.player == null) return "食物";
        Inventory inventory = mc.player.getInventory();
        List<String> foodWhitelist = module.getFoodWhitelist();
        ItemStack best = ItemStack.EMPTY;
        int bestNutrition = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !foodWhitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) {
                continue;
            }
            var foodComp = stack.get(DataComponents.FOOD);
            if (foodComp == null) continue;
            if (i == inventory.getSelectedSlot()) return stack.getHoverName().getString(); // 正拿在手上的优先
            if (foodComp.nutrition() > bestNutrition) {
                bestNutrition = foodComp.nutrition();
                best = stack;
            }
        }
        return best.isEmpty() ? "食物" : best.getHoverName().getString();
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
        boolean occupiedIsFoodOrEmpty = occupied.isEmpty()
            || module.getFoodWhitelist().contains(BuiltInRegistries.ITEM.getKey(occupied.getItem()).toString());
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
