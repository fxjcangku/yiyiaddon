package com.yiyiaddon.feature.autochest.service;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.WithdrawMode;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.platform.identity.ItemIdentityMatcher;
import com.yiyiaddon.platform.network.BlockPacketSender;
import com.yiyiaddon.service.container.ContainerService;
import com.yiyiaddon.service.identity.IdentityService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

/**
 * 容器交互服务：开箱 → 读真实槽位 → 按取物模式精确取物 → 关箱。
 *
 * <p>复用 {@code service/container/ContainerService} 做 stateId 稳定性观测（容器同步层），
 * 复用 {@code platform/network/BlockPacketSender.interactBlock} 发包开箱（静默模式也有效）。</p>
 *
 * <p>真实 Slot 动态识别（容器 / 玩家背包 / 快捷栏，绝不写死槽位号）、
 * 完整 ItemIdentity 匹配（Item ID + 自定义名 + Data Components + 附魔）、
 * 三种取物模式（按数量差额 / 目标拿空 / 全部拿空）、背包空间确认、精确差额取物。
 * 每次操作都基于当前服务端同步的真实 Slot 状态，不缓存旧数据。</p>
 */
public final class ChestInteractionService {

    /** 取物一次的结果：决定状态机后续流转 */
    public enum Result {
        /** 发出了一次取物操作（或仍在同步/冷却中），继续取物 */
        WITHDREW,
        /** 无可取：空箱 / 无目标 / 目标已全部达标 */
        FINISHED,
        /** 背包已满，无法继续容纳任何应取物品 */
        INVENTORY_FULL
    }

    /** 匹配阶段结果（只读，无副作用）：决定取物是否继续 */
    public enum MatchResult {
        /** 还有可取目标（进入取物） */
        TAKABLE,
        /** 无任何可取（空箱 / 无目标 / 目标已达标），视为完成 */
        DONE,
        /** 有应取物品但背包放不下，停止自动箱子 */
        INVENTORY_FULL
    }

    /** 候选取物槽位（匹配 + 空间确认后）：slot=容器槽位，identity=命中身份，count=应取数量 */
    private record TakeTarget(Slot slot, ItemIdentity identity, int count) {
    }

    private static final int MAX_OPEN_ATTEMPTS = 5;

    /** 开箱可达距离（格）：玩家距容器中心超过该距离不尝试发包，交由服务端可达校验兜底 */
    private static final double OPEN_REACH = 4.5;

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoChestSettings settings;

    /** 容器同步观测器（stateId 稳定判定） */
    private final ContainerService broker = new ContainerService();

    /** 两次槽位操作之间的节流冷却 */
    private int actionCooldown = 0;

    private int openAttempts = 0;
    private int openCooldown = 0;

    /** 进行中的精确取物会话（跨 tick 完成「从单叠精确取 N 个」） */
    private PreciseWithdraw precise;

    public ChestInteractionService(AutoChestSettings settings) {
        this.settings = settings;
    }

    /** 每 tick 调用，推进 stateId 稳定性观测与冷却 */
    public void tick() {
        broker.tick();
        if (actionCooldown > 0) actionCooldown--;
        if (openCooldown > 0) openCooldown--;
    }

    // ── 开箱 / 关箱 / 同步 ──

    /**
     * 发包打开指定容器。
     *
     * <p>仅在可达距离内且目标确为容器时开箱；失败多次后加长冷却再重试，
     * 避免后台挂机时永久傻等。</p>
     */
    public boolean open(BlockPos pos) {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return false;

        // 距离过远不尝试发包（可达距离内才开箱）；不站在容器方块上
        double distSqr = player.position().distanceToSqr(
            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        if (distSqr > OPEN_REACH * OPEN_REACH) return false;
        if (player.blockPosition().equals(pos)) return false;

        if (openCooldown > 0) return false;
        if (ContainerAccess.openMenu() != null) return false; // 已有容器开着

        BlockEntity entity = mc.level.getBlockEntity(pos);
        if (!(entity instanceof Container)) return false;

        openAttempts++;
        if (openAttempts > MAX_OPEN_ATTEMPTS) {
            openCooldown = 30;
            openAttempts = 0;
            return false;
        }

        openCooldown = 5;
        // 直接发包开箱（带 sequence 预测），窗口失焦时也能打开
        // 打点「我方刚开箱」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
        SilentContainer.markOwnContainerOpen();
        return BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
    }

    /** 容器是否真的打开（玩家当前 menu 是容器而非自身背包） */
    public boolean isOpen() {
        return ContainerAccess.openMenu() != null;
    }

    /** 容器是否已同步稳定（stateId 连续稳定，可安全读写槽位） */
    public boolean isSynced() {
        return broker.isReady();
    }

    /** 关闭容器并重置同步观测与精确取物会话 */
    public void close() {
        ContainerAccess.closeContainer();
        reset();
    }

    public void reset() {
        broker.reset();
        openAttempts = 0;
        actionCooldown = 0;
        precise = null;
    }

    // ── 真实 Slot 分类 ──

    /** 槽位归属：容器侧 / 快捷栏 / 玩家背包（主背包） */
    private enum SlotKind { CONTAINER, HOTBAR, INVENTORY }

    /**
     * 判定槽位归属（动态识别真实 Slot，绝不写死槽位号）。
     *
     * <p>容器侧以 {@code slot.container != 玩家背包} 判定，对箱子 / 陷阱箱 / 潜影盒 /
     * 木桶 / 铜箱等不同 Menu 统一生效，不需要按容器外观写多套核心逻辑。
     * 玩家背包侧再按 {@link Inventory#isHotbarSlot} 细分快捷栏与主背包。</p>
     */
    private static SlotKind kindOf(Slot slot, Inventory inventory) {
        if (slot.container != inventory) return SlotKind.CONTAINER;
        return Inventory.isHotbarSlot(slot.getContainerSlot()) ? SlotKind.HOTBAR : SlotKind.INVENTORY;
    }

    // ── 取物执行（三态结果驱动状态机） ──

    /**
     * 取物一次：按取物模式遍历容器侧真实槽位，命中即取。
     *
     * <p>每次调用都重新读取当前 {@code menu.slots} 的真实状态（多人同步：其他玩家
     * 拿走则本槽自动跳过），不缓存旧数据。返回 {@link Result} 供状态机决定
     * 继续 / 完成 / 背包满停止。</p>
     */
    public Result withdrawOnce(WithdrawMode mode) {
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null) return Result.FINISHED;
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) return Result.FINISHED; // 容器已关闭
        Inventory inventory = player.getInventory();

        // 进行中的精确取物会话优先推进
        if (precise != null) {
            return tickPrecise(menu, inventory);
        }

        // 同步稳定前不操作（等 stateId 稳定），冷却中也不操作
        if (!isSynced()) return Result.WITHDREW;
        if (actionCooldown > 0) return Result.WITHDREW;

        boolean[] full = {false};
        TakeTarget target = findTarget(mode, menu, inventory, full);
        if (target == null) {
            // 无可取：区分「完成」与「背包满」
            return full[0] ? Result.INVENTORY_FULL : Result.FINISHED;
        }

        // 执行取物：整叠不超量走 shift，单叠超量走精确取物
        if (target.count() >= target.slot().getItem().getCount()) {
            quickMove(menu, target.slot());
        } else {
            startPrecise(menu, target.slot(), target.count());
        }
        actionCooldown = settings.actionDelay;
        return Result.WITHDREW;
    }

    /**
     * 匹配阶段（只读，无副作用）：判断容器内是否还有可取目标。
     *
     * <p>供状态机 MATCHING 状态使用，决定进入取物 / 完成 / 背包满停机。
     * 精确取物会话进行中视为仍有活要干。</p>
     */
    public MatchResult matchOnce(WithdrawMode mode) {
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null) return MatchResult.DONE;
        if (precise != null) return MatchResult.TAKABLE; // 精确取物会话未完
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) return MatchResult.DONE; // 容器已关闭
        if (!isSynced()) return MatchResult.TAKABLE; // 同步未稳定，等待

        boolean[] full = {false};
        TakeTarget target = findTarget(mode, menu, player.getInventory(), full);
        if (target != null) return MatchResult.TAKABLE;
        return full[0] ? MatchResult.INVENTORY_FULL : MatchResult.DONE;
    }

    /**
     * 在容器侧真实槽位中查找下一个「匹配且可放入背包」的目标。
     *
     * <p>与 {@link #withdrawOnce} 共用同一套匹配 + 差额 + 空间确认判据，保证
     * 匹配阶段（只读）与取物阶段（有副作用）语义一致，不产生逻辑漂移。</p>
     *
     * @param mode      取物模式
     * @param menu      当前容器菜单
     * @param inventory 玩家背包
     * @param full      输出参数：是否发现「应取但背包放不下」的目标
     * @return 可取目标；无可取返回 null（full 区分空箱完成与背包满）
     */
    private TakeTarget findTarget(WithdrawMode mode, AbstractContainerMenu menu, Inventory inventory, boolean[] full) {
        List<ItemIdentity> targets = mode == WithdrawMode.TAKE_ALL
            ? null : IdentityTargetConfig.selectedItems(IdentityService.shared());

        for (Slot slot : menu.slots) {
            if (kindOf(slot, inventory) != SlotKind.CONTAINER) continue; // 只看容器侧真实槽位
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            // 完整身份匹配：TAKE_ALL 不匹配，其它模式按目标列表精确匹配
            ItemIdentity hit = mode == WithdrawMode.TAKE_ALL
                ? null : ItemIdentityMatcher.matchTarget(stack, targets);
            if (mode != WithdrawMode.TAKE_ALL && hit == null) continue;

            // 应取数量：TARGET_COUNT 按差额，其它整叠
            int takeCount = stack.getCount();
            if (mode == WithdrawMode.TARGET_COUNT) {
                int target = settings.quantityOf(hit.identityKey());
                int need = target - countPlayerHas(inventory, hit);
                if (need <= 0) continue; // 目标已全部达标，跳过
                takeCount = need;
            }

            // 背包空间确认：放不下则标记满并继续找下一个能放下的
            if (!canFit(menu, inventory, stack, takeCount)) {
                full[0] = true;
                continue;
            }
            return new TakeTarget(slot, hit, takeCount);
        }
        return null;
    }

    /**
     * 背包侧（含快捷栏，不含容器槽）能否再容纳 {@code count} 个该物品。
     *
     * <p><b>不要用 {@code Inventory#getSlotWithRemainingSpace} 判「背包满」</b>：它只在
     * 「已存在同种未满堆叠」时返回槽位下标，空槽一律 -1（原始源码 {@code hasRemainingSpaceForItem}
     * 首条要求 {@code !slotItemStack.isEmpty()}）。于是取一件背包里<b>没有</b>同种堆叠的物品时，
     * 哪怕背包空着 30 格也返回 -1，被这里判成「背包空间不足」→ 开箱即停机。</p>
     *
     * <p>这里按真实容量求和：空槽算 {@code maxStackSize}，同种堆叠算剩余空间；
     * 只要其中任意一个槽位能装下就直接返回，不做无谓遍历。</p>
     */
    private boolean canFit(AbstractContainerMenu menu, Inventory inventory, ItemStack stack, int count) {
        int max = stack.getMaxStackSize();
        int room = 0;
        for (Slot slot : menu.slots) {
            if (kindOf(slot, inventory) == SlotKind.CONTAINER) continue; // 只看玩家背包侧（含快捷栏）
            ItemStack item = slot.getItem();
            if (item.isEmpty()) {
                room += max;
            } else if (ItemStack.isSameItemSameComponents(item, stack)) {
                room += Math.max(0, Math.min(max, item.getMaxStackSize()) - item.getCount());
            }
            if (room >= count) return true;
        }
        return room >= count;
    }

    /** 统计玩家背包（含快捷栏，共 36 格）+ 副手中匹配给定身份的物品总数 */
    private int countPlayerHas(Inventory inventory, ItemIdentity identity) {
        int total = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;
            if (ItemIdentityMatcher.matches(stack, identity)) total += stack.getCount();
        }
        // 副手也计入：TARGET_COUNT 差额计算需覆盖副手持有的目标物品，否则会多取
        ItemStack offhand = mc.player.getOffhandItem();
        if (!offhand.isEmpty() && ItemIdentityMatcher.matches(offhand, identity)) total += offhand.getCount();
        return total;
    }

    /** 发送 shift 点击（整叠快速移动到背包） */
    private void quickMove(AbstractContainerMenu menu, Slot slot) {
        ContainerAccess.quickMove(menu, slot.index);
    }

    // ── 精确差额取物（单叠超量时跨 tick 完成「取 N 个」） ──

    /** 精确取物会话：左键拿整叠 → 右键逐格放 N 个 → 左键放回剩余 */
    private static final class PreciseWithdraw {
        /** 拿起整叠的容器槽位（menu 中的 slot.index） */
        int sourceSlot;
        /** 还需要放到背包的数量 */
        int need;
        /** 已放到背包的数量 */
        int placed;
        /** 光标持物的物品快照（用于找同种未满堆叠） */
        ItemStack carried;
        /** 当前阶段 */
        Phase phase = Phase.PLACE;

        enum Phase { PLACE, RETURN }
    }

    /** 启动精确取物：左键拿起整叠，后续逐格放 */
    private void startPrecise(AbstractContainerMenu menu, Slot slot, int need) {
        PreciseWithdraw p = new PreciseWithdraw();
        p.sourceSlot = slot.index;
        p.need = need;
        p.placed = 0;
        p.carried = slot.getItem().copy();
        precise = p;
        // 左键（button=0）拿起整叠
        mc.gameMode.handleContainerInput(menu.containerId, slot.index, 0, ContainerInput.PICKUP, mc.player);
        actionCooldown = settings.actionDelay;
    }

    /** 推进精确取物会话（同步稳定后每 tick 一次操作） */
    private Result tickPrecise(AbstractContainerMenu menu, Inventory inventory) {
        if (!isSynced()) return Result.WITHDREW;
        if (actionCooldown > 0) return Result.WITHDREW;

        switch (precise.phase) {
            case PLACE -> {
                if (precise.placed >= precise.need) {
                    precise.phase = PreciseWithdraw.Phase.RETURN;
                    return Result.WITHDREW;
                }
                Slot target = findPlaceSlot(menu, inventory, precise.carried);
                if (target == null) {
                    // 背包放不下：放回剩余并上报背包满
                    returnRemainder(menu);
                    return Result.INVENTORY_FULL;
                }
                // 右键（button=1）放 1 个
                mc.gameMode.handleContainerInput(menu.containerId, target.index, 1, ContainerInput.PICKUP, mc.player);
                precise.placed++;
                actionCooldown = settings.actionDelay;
                return Result.WITHDREW;
            }
            case RETURN -> {
                returnRemainder(menu);
                return Result.WITHDREW;
            }
        }
        return Result.WITHDREW;
    }

    /** 左键放回光标剩余到原容器槽位，结束精确取物会话 */
    private void returnRemainder(AbstractContainerMenu menu) {
        mc.gameMode.handleContainerInput(menu.containerId, precise.sourceSlot, 0, ContainerInput.PICKUP, mc.player);
        precise = null;
        actionCooldown = settings.actionDelay;
    }

    /** 找背包侧可放 1 个的槽位：同种未满堆叠优先，其次空槽；无则返回 null */
    private Slot findPlaceSlot(AbstractContainerMenu menu, Inventory inventory, ItemStack carried) {
        Slot empty = null;
        for (Slot s : menu.slots) {
            if (kindOf(s, inventory) == SlotKind.CONTAINER) continue; // 只看玩家背包侧（含快捷栏）
            ItemStack item = s.getItem();
            if (item.isEmpty()) {
                if (empty == null) empty = s;
            } else if (ItemStack.isSameItemSameComponents(item, carried)
                && item.getCount() < item.getMaxStackSize()) {
                return s;
            }
        }
        return empty;
    }
}
