package com.yiyiaddon.platform.container;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * 玩家背包操作原语：查找、切换选中快捷栏槽、把背包物品穿到护甲槽。
 *
 * <p>等价旧客户端框架的背包工具（{@code InvUtils.find / findInHotbar / swap / move().toArmor}），
 * 按 26.1.2 的原版协议实现：切槽必须同时发携带物同步包，穿装备走
 * {@code ContainerInput.SWAP}（护甲槽在 {@code InventoryMenu} 里的下标为 5~8）。</p>
 *
 * <p><b>为什么不复用既有模块内的同类实现：</b>项目里 {@code InventoryOps} /
 * {@code MiningContainer} / {@code MiningCombat} / {@code LecternOps} / {@code DefaultStardewAdapter}
 * 各自内联过一份等价逻辑（迁移过程中逐个模块铺开的），它们是已实机验收的代码，
 * 本批次不触碰；本类供战术模块使用，收口统一留待专门批次（登记在 98 号记录）。</p>
 */
public final class InventoryAccess {

    /** 未找到 */
    public static final int NOT_FOUND = -1;

    /** 护甲槽在 {@code InventoryMenu} 里的下标：5 头盔 / 6 胸甲 / 7 护腿 / 8 靴子 */
    private static final int CHEST_MENU_SLOT = 6;

    /** 快捷栏在 {@code InventoryMenu} 里的起始下标 */
    private static final int HOTBAR_MENU_BASE = 36;

    private InventoryAccess() {
    }

    /** 在快捷栏（0~8）里找第一个满足条件的槽位；没有返回 {@link #NOT_FOUND} */
    public static int findInHotbar(Predicate<ItemStack> filter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || filter == null) return NOT_FOUND;
        for (int slot = 0; slot < 9; slot++) {
            if (filter.test(mc.player.getInventory().getItem(slot))) return slot;
        }
        return NOT_FOUND;
    }

    /**
     * 在整个背包复合索引（0~8 快捷栏 / 9~35 主背包 / 36~39 护甲 / 40 副手）里查找。
     *
     * <p>与旧框架 {@code InvUtils.find(filter, 0, 35)} 的扫描范围一致：调用方自行用下标上限约束。</p>
     */
    public static int find(Predicate<ItemStack> filter, int upperBound) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || filter == null) return NOT_FOUND;
        int size = Math.min(upperBound, mc.player.getInventory().getContainerSize());
        for (int slot = 0; slot < size; slot++) {
            if (filter.test(mc.player.getInventory().getItem(slot))) return slot;
        }
        return NOT_FOUND;
    }

    /** 切换选中的快捷栏槽并同步服务端（客户端即时生效） */
    public static boolean selectHotbar(int slot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || slot < 0 || slot > 8) return false;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
        return true;
    }

    /** 当前选中的快捷栏槽 */
    public static int selectedHotbar() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player == null ? NOT_FOUND : mc.player.getInventory().getSelectedSlot();
    }

    /**
     * 把背包槽里的物品与胸甲槽交换（旧框架 {@code move().from(slot).toArmor(胸甲)} 的等价原语）。
     *
     * <p>旧调用为 {@code InvUtils.move().from(elytra.slot()).toArmor(2)}：其 {@code toArmor}
     * 参数语义（护甲槽序号）在本项目参考库中无源码可查，按方法用途（把鞘翅穿到胸甲）
     * 落地为 {@code InventoryMenu} 的胸甲槽 6；列为实机验证项。</p>
     */
    public static boolean moveToChest(int invSlot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return false;
        if (invSlot < 0 || invSlot >= mc.player.getInventory().getContainerSize()) return false;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuIndex(invSlot),
            CHEST_MENU_SLOT, ContainerInput.SWAP, mc.player);
        return true;
    }

    /**
     * 把背包槽 {@code invSlot} 的物品与当前选中的快捷栏槽交换，选中槽下标不变
     * （旧框架 {@code InvUtils.swap(slot, false)} 的等价原语：换到的工具就此握在主手）。
     *
     * <p>走 {@code InventoryMenu} 的 {@code ContainerInput.SWAP}，与 {@link #moveToChest(int)}
     * 同一套协议路径；同槽位调用直接视为成功（与旧框架快速交换的早退一致）。</p>
     */
    public static boolean swapWithSelectedHotbar(int invSlot) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return false;
        int selected = mc.player.getInventory().getSelectedSlot();
        if (invSlot == selected) return true;
        if (invSlot < 0 || invSlot >= mc.player.getInventory().getContainerSize()) return false;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuIndex(invSlot),
            menuIndex(selected), ContainerInput.SWAP, mc.player);
        return true;
    }

    /** 背包复合索引 → {@code InventoryMenu} 槽位下标（快捷栏 0~8 → 36~44，其余原样） */
    private static int menuIndex(int invSlot) {
        return invSlot < 9 ? HOTBAR_MENU_BASE + invSlot : invSlot;
    }
}
