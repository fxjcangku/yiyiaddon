package com.yiyiaddon.platform.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 容器访问适配层：把 26.1.2 的容器菜单读写原语集中在此，供上层容器服务调用。
 *
 * <p>26.1.2 的容器交互 API 已从 {@code clickSlot + SlotActionType} 换成
 * {@code MultiPlayerGameMode#handleContainerInput(containerId, slot, button, ContainerInput, player)}，
 * 同步字段从 {@code getRevision()} 换成了 {@code getStateId()}，本层只做 API 适配，</p>
 * <p>不含任何「是否该搬运」「搬多少」的业务判断。</p>
 *
 * <p><b>槽位归属判据：</b>以 {@code slot.container == player.getInventory()} 区分玩家背包侧与
 * 容器侧，不使用槽位下标范围猜测，避免不同容器（箱子 / 潜影盒 / 交易界面）下误判。</p>
 */
public final class ContainerAccess {

    private ContainerAccess() {
    }

    /**
     * 当前打开的容器菜单。
     *
     * <p>只认真正的容器界面：玩家自身背包菜单（{@code containerId == 0}）返回 {@code null}。
     * 静默开容器模式下客户端不会弹出 Screen，因此这里直接读 {@code player.containerMenu}。</p>
     */
    public static AbstractContainerMenu openMenu() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return null;
        AbstractContainerMenu menu = player.containerMenu;
        if (menu == null || menu.containerId == 0) return null;
        return menu;
    }

    /** 玩家背包；玩家不在世界内返回 {@code null} */
    public static Inventory inventory() {
        LocalPlayer player = Minecraft.getInstance().player;
        return player == null ? null : player.getInventory();
    }

    /**
     * 当前可操作的服务端菜单：**静默容器优先，屏幕容器兜底**。
     *
     * <p>与 {@link #openMenu()} 的差别只在最后那步兜底：{@code containerMenu} 不是真容器时，
     * 再看 {@code mc.screen} 是不是容器界面。给「界面已被本模组静默、只剩 {@code containerMenu} 可用」
     * 的点击流程用（自动挖矿点 RTP 选单、自动登入的两条菜单路线）——
     * 这几条不能依赖 Screen 是否存在，否则静默一开就点不动了。</p>
     */
    public static AbstractContainerMenu activeMenu() {
        AbstractContainerMenu menu = openMenu();
        if (menu != null) return menu;
        // 26.2 差异：当前界面由 Minecraft#screen 搬到 Gui#screen()
        if (Minecraft.getInstance().gui.screen() instanceof AbstractContainerScreen<?> screen) {
            return screen.getMenu();
        }
        return null;
    }

    /** 是否已经打开了一个真正的容器（而非自身背包） */
    public static boolean hasOpenContainer() {
        return openMenu() != null;
    }

    /** 容器侧是否还有空位可以接收该物品（存在空槽，或同种物品未满堆叠） */
    public static boolean hasContainerSpace(AbstractContainerMenu menu, ItemStack stack) {
        Inventory inventory = inventory();
        if (menu == null || inventory == null || stack == null || stack.isEmpty()) return false;
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;
            ItemStack existing = slot.getItem();
            if (existing.isEmpty()) return true;
            if (ItemStack.isSameItemSameComponents(existing, stack)
                && existing.getCount() < existing.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    /** 统计容器侧指定物品的总数 */
    public static int countInContainer(AbstractContainerMenu menu, Item item) {
        Inventory inventory = inventory();
        if (menu == null || inventory == null || item == null) return 0;
        int total = 0;
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;
            ItemStack stack = slot.getItem();
            if (stack.is(item)) total += stack.getCount();
        }
        return total;
    }

    /**
     * 发送 shift 点击（快速移动）。
     *
     * <p>{@code QUICK_MOVE} 语义下 button 参数代表左右键，固定传 0。</p>
     */
    public static void quickMove(AbstractContainerMenu menu, int slotIndex) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (menu == null || player == null || mc.gameMode == null) return;
        mc.gameMode.handleContainerInput(menu.containerId, slotIndex, 0, ContainerInput.QUICK_MOVE, player);
    }

    /**
     * 关闭当前容器。
     *
     * <p>{@code player.closeContainer()} 会无条件关掉当前打开的任意 Screen。因此必须先确认
     * 玩家确实开着容器（{@code containerMenu != inventoryMenu}），否则在本模组界面打开时调用
     * 会把界面一起关掉。</p>
     */
    public static void closeContainer() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.containerMenu == player.inventoryMenu) return;
        player.closeContainer();
    }
}
