package com.yiyiaddon.feature.autofarm.task;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * 自动农场任务层的背包操作原语。
 *
 * <p>旧项目任务层使用旧客户端框架的背包工具（查找 / 切换 / 搬运），
 * 本类按 26.1.2 的等价原语重写同一套能力，仅服务本模块任务层：</p>
 * <ul>
 *   <li>查找：直接遍历 {@code Inventory} 槽位；</li>
 *   <li>与副手交换：{@code ContainerInput.SWAP}，button = 40（副手在 Inventory 里的索引），
 *       快捷栏 0~8 在 InventoryMenu 里对应槽位 36~44，主背包 9~35 与 Inventory 下标同值；</li>
 *   <li>切主手：设置选中槽并同步 {@code ServerboundSetCarriedItemPacket}。</li>
 * </ul>
 *
 * <p>与本项目 {@code MiningStateMachine} / {@code DefaultStardewAdapter} 同一口径。</p>
 */
final class InventoryOps {

    /** 副手在 Inventory 复合索引里的下标（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手） */
    static final int OFFHAND_INV_INDEX = 40;
    /** 副手在 InventoryMenu 里的槽位下标 */
    private static final int OFFHAND_MENU_INDEX = 45;

    private InventoryOps() {
    }

    /** 未找到时的槽位值 */
    static final int NOT_FOUND = -1;

    /** 在快捷栏（0~8）里找第一个满足条件的槽位，没有返回 {@link #NOT_FOUND} */
    static int findInHotbar(Predicate<ItemStack> filter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return NOT_FOUND;
        for (int i = 0; i < 9; i++) {
            if (filter.test(mc.player.getInventory().getItem(i))) return i;
        }
        return NOT_FOUND;
    }

    /** 在全背包（含护甲与副手的 Inventory 复合索引）里找第一个满足条件的槽位，没有返回 {@link #NOT_FOUND} */
    static int find(Predicate<ItemStack> filter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return NOT_FOUND;
        int size = mc.player.getInventory().getContainerSize();
        for (int i = 0; i < size; i++) {
            if (filter.test(mc.player.getInventory().getItem(i))) return i;
        }
        return NOT_FOUND;
    }

    /** 槽位是否属于快捷栏（0~8） */
    static boolean isHotbar(int invSlot) {
        return invSlot >= 0 && invSlot < 9;
    }

    /** 槽位是否为副手 */
    static boolean isOffhand(int invSlot) {
        return invSlot == OFFHAND_INV_INDEX;
    }

    /** Inventory 复合索引 → InventoryMenu 槽位下标（快捷栏 0~8 → 36~44，其余原样，副手 40 → 45） */
    private static int menuIndex(int invSlot) {
        if (invSlot == OFFHAND_INV_INDEX) return OFFHAND_MENU_INDEX;
        if (isHotbar(invSlot)) return 36 + invSlot;
        return invSlot;
    }

    /**
     * 把指定背包槽的物品与副手交换（旧框架 {@code move().from(slot).toOffhand()} 的等价原语）。
     *
     * <p>走 {@code ContainerInput.SWAP}，button = 40 即「与被点击槽位交换副手物品」。</p>
     */
    static void moveToOffhand(int invSlot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 0 || invSlot > OFFHAND_INV_INDEX) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuIndex(invSlot),
            OFFHAND_INV_INDEX, ContainerInput.SWAP, mc.player);
    }

    /**
     * 把指定背包槽的物品与当前选中的快捷栏槽交换
     * （旧框架 {@code move().from(slot).to(选中槽)} 的等价原语）。
     */
    static void moveToSelectedHotbar(int invSlot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;
        int selected = mc.player.getInventory().getSelectedSlot();
        if (invSlot < 0 || invSlot > OFFHAND_INV_INDEX || selected < 0 || selected > 8) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuIndex(invSlot),
            selected, ContainerInput.SWAP, mc.player);
    }

    /** 切换选中快捷栏槽并同步服务端（客户端即时生效） */
    static void selectHotbar(int slot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || slot < 0 || slot > 8) return;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }
}
