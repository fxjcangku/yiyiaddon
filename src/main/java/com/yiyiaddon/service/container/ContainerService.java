package com.yiyiaddon.service.container;

import com.yiyiaddon.model.container.ContainerTransferResult;
import com.yiyiaddon.platform.container.ContainerAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * 容器交互服务：负责「容器当前是否可安全操作」的同步观测与按槽位搬运。
 *
 * <p>所有箱子 / 潜影盒操作都强依赖菜单同步状态，禁止在菜单未就绪时抢跑发包，
 * 否则服务端会判定为幽灵物品并回滚。就绪判定采用三重校验：</p>
 * <ol>
 *   <li>存在真正的容器菜单（{@code containerId != 0}）；</li>
 *   <li>{@code stateId} 连续若干 tick 保持稳定，说明服务端已把初始物品同步完；</li>
 *   <li>调用方每次开新容器前显式 {@link #reset()}，避免复用上一个容器的观测值。</li>
 * </ol>
 *
 * <p>本类为有状态实例：每个使用容器的模块持有自己的实例，禁止共享，
 * 避免两个模块的 stateId 观测互相干扰。</p>
 */
public final class ContainerService {

    /** stateId 需要连续稳定多少 tick 才认为容器同步完成 */
    private static final int STABLE_TICKS_REQUIRED = 3;

    private int lastStateId = Integer.MIN_VALUE;
    private int stableTicks;

    /** 每次开新容器前调用，清空上一次的同步观测数据 */
    public void reset() {
        lastStateId = Integer.MIN_VALUE;
        stableTicks = 0;
    }

    /**
     * 每 tick 调用一次，推进 stateId 稳定性观测。
     *
     * <p>必须在读写容器之前调用，否则 {@link #isReady()} 永远不会为真。</p>
     */
    public void tick() {
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) {
            reset();
            return;
        }
        int stateId = menu.getStateId();
        if (stateId == lastStateId) {
            stableTicks++;
        } else {
            lastStateId = stateId;
            stableTicks = 0;
        }
    }

    /** 容器是否已完成同步，可以安全发起槽位操作 */
    public boolean isReady() {
        return ContainerAccess.hasOpenContainer() && stableTicks >= STABLE_TICKS_REQUIRED;
    }

    /** 当前是否开着容器（不要求已同步） */
    public boolean hasContainer() {
        return ContainerAccess.hasOpenContainer();
    }

    /**
     * 把玩家背包侧符合条件的物品 shift 点进容器。
     *
     * <p>一次只处理一个槽位，由调用方按自己的节流节奏分批执行。遍历到能放下的物品就快速移动；
     * 有匹配但都放不下返回箱子满；没有任何匹配返回无匹配；未同步稳定返回等待。</p>
     *
     * @param filter 物品筛选条件
     * @return 单次搬运结果
     */
    public ContainerTransferResult depositOne(Predicate<ItemStack> filter) {
        if (!isReady()) return ContainerTransferResult.NOT_READY;

        AbstractContainerMenu menu = ContainerAccess.openMenu();
        Inventory inventory = ContainerAccess.inventory();
        if (menu == null || inventory == null) return ContainerTransferResult.NONE;

        boolean hasMatching = false;
        for (Slot slot : menu.slots) {
            // 只处理玩家背包侧的槽位
            if (slot.container != inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || filter == null || !filter.test(stack)) continue;
            hasMatching = true;

            // 该物品当前放不进容器时跳过它，尝试其它匹配槽位，避免误判容器已满
            if (!ContainerAccess.hasContainerSpace(menu, stack)) continue;

            ContainerAccess.quickMove(menu, slot.index);
            return ContainerTransferResult.MOVED;
        }
        return hasMatching ? ContainerTransferResult.CHEST_FULL : ContainerTransferResult.NONE;
    }

    /**
     * 从容器取出指定物品到玩家背包。
     *
     * <p>背包没有空位时不发包，避免物品被服务端塞回容器造成来回抖动。</p>
     *
     * @param item 要提取的物品
     * @return 是否实际发出了一次操作
     */
    public boolean withdrawOne(Item item) {
        if (!isReady()) return false;

        AbstractContainerMenu menu = ContainerAccess.openMenu();
        Inventory inventory = ContainerAccess.inventory();
        if (menu == null || inventory == null || item == null) return false;
        if (inventory.getFreeSlot() == -1) return false;

        for (Slot slot : menu.slots) {
            // 只处理容器侧的槽位
            if (slot.container == inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || !stack.is(item)) continue;

            ContainerAccess.quickMove(menu, slot.index);
            return true;
        }
        return false;
    }

    /** 统计容器侧指定物品总数（供差额取物判断） */
    public int countInContainer(Item item) {
        return ContainerAccess.countInContainer(ContainerAccess.openMenu(), item);
    }

    /** 容器侧是否还有空位接收该物品 */
    public boolean hasSpace(ItemStack stack) {
        return ContainerAccess.hasContainerSpace(ContainerAccess.openMenu(), stack);
    }

    /**
     * 关闭容器并清空同步观测。
     *
     * <p>只关真正的容器，不会误关本模组界面。</p>
     */
    public void close() {
        ContainerAccess.closeContainer();
        reset();
    }
}
