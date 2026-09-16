package com.yiyiaddon.feature.enchant.service;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * 附魔流程的容器与发包操作层。
 *
 * <p>对应旧项目 {@code AutoEnchantBook} 里的容器工具方法，判据、槽位换算与发包语义逐字照旧：</p>
 * <ul>
 *   <li>{@code countInInventory:1785-1792} / {@code inventoryFull:1795-1797} /
 *       {@code findInInventory:1799-1804} / {@code findInInventoryAtLeast:1806-1812}；</li>
 *   <li>{@code containerSlotOf:1814-1821}——全流程共用的唯一背包槽→菜单槽换算，不得就地硬编码；</li>
 *   <li>{@code mergeBooksStep:1827-1870}（含 {@code mergeBookSrc/Dst/Step} 三连点击子状态）；</li>
 *   <li>{@code canOpenNow:1891-1896} / {@code interactBlock:1952-1959}
 *       （旧 {@code FarmPacketOps.interactBlock} → 本项目
 *       {@link BlockPacketSender#interactBlock}）；</li>
 *   <li>{@code selectItem:2813-2820}（快捷栏直接选槽，主背包用 {@code ContainerInput.SWAP} 换入当前槽）；</li>
 *   <li>菜单就绪判断 {@code enchantMenuOpen:1876-1878} / {@code grindMenuOpen:1881-1883} /
 *       {@code chestMenuOpen:1886-1888} / {@code anvilMenuOpen:2231-2233}。</li>
 * </ul>
 *
 * <p>{@link #closeContainer()} 相对旧项目 {@code mc.player.closeContainer()} 多一层
 * 「当前菜单不是玩家自身背包」防护：静默容器操作下菜单即界面，无防护时误调会把玩家自己开着的
 * 背包一起关掉（与 {@code feature/mining/service/MiningContainer#closeContainer} 同一做法）。</p>
 */
public final class EnchantContainer {

    /** 玩家背包（主背包 + 快捷栏）格数，旧项目全部循环的上界 {@code 36} */
    private static final int INVENTORY_SLOTS = 36;

    private final EnchantModule module;
    private final Minecraft mc = Minecraft.getInstance();

    // 空白书合并子状态：砂轮洗练后把背包里分散的空白书堆叠合并到一起（旧 :406-409）
    private int mergeBookSrc = -1;   // 待合并来源槽位（玩家背包 0-35）
    private int mergeBookDst = -1;   // 待合并目标槽位
    private int mergeBookStep = 0;   // 0 待找 / 1 已拿起 / 2 已放上 / 3 归还剩余

    public EnchantContainer(EnchantModule module) {
        this.module = module;
    }

    // ── 背包查询（旧 :1785-1812） ──

    /** 背包内指定物品总数（主背包 + 快捷栏 36 格） */
    public int countInInventory(Item item) {
        if (mc.player == null) return 0;
        int count = 0;
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack s = mc.player.getInventory().getItem(i);
            if (s.getItem() == item) count += s.getCount();
        }
        return count;
    }

    /** 玩家背包是否已满（无空槽），满则无法再往背包塞东西 */
    public boolean inventoryFull() {
        return mc.player != null && mc.player.getInventory().getFreeSlot() == -1;
    }

    /** 背包内指定物品的第一个槽位，无则 -1 */
    public int findInInventory(Item item) {
        if (mc.player == null) return -1;
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            if (mc.player.getInventory().getItem(i).getItem() == item) return i;
        }
        return -1;
    }

    /** 背包内数量达到下限的指定物品的第一个槽位，无则 -1 */
    public int findInInventoryAtLeast(Item item, int minimumCount) {
        if (mc.player == null) return -1;
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.getItem() == item && stack.getCount() >= minimumCount) return i;
        }
        return -1;
    }

    /**
     * 背包槽位 → 容器菜单槽位（旧 {@code containerSlotOf:1814-1821}）。
     *
     * <p>全流程共用这一处换算：菜单前若干槽是容器本体，之后是玩家背包（最后 36 槽）。</p>
     */
    public int containerSlotOf(AbstractContainerMenu handler, int invSlot) {
        int containerSize = handler.slots.size() - INVENTORY_SLOTS;
        if (invSlot < 9) {
            return containerSize + 27 + invSlot;
        } else {
            return containerSize + (invSlot - 9);
        }
    }

    // ── 空白书堆叠合并（旧 :1823-1870） ──

    /** 清空合并子状态（旧 {@code tickGrinding} phase2 的 else 分支） */
    public void resetMergeBooks() {
        mergeBookSrc = -1;
        mergeBookDst = -1;
        mergeBookStep = 0;
    }

    /**
     * 单步推进空白书合并：每 tick 只发一次点击（拿起→放上→归还），
     * 直到背包里所有同种空白书都堆叠满，返回 false 表示本轮已无更多可合并堆叠。
     */
    public boolean mergeBooksStep(AbstractContainerMenu handler, int syncId) {
        // 已锁定一对待合并堆叠时，按子步骤推进三连点击
        if (mergeBookSrc >= 0 && mergeBookDst >= 0) {
            switch (mergeBookStep) {
                case 1 -> {
                    // 拿起来源整叠
                    mc.gameMode.handleContainerInput(syncId, containerSlotOf(handler, mergeBookSrc), 0, ContainerInput.PICKUP, mc.player);
                    mergeBookStep = 2;
                    return true;
                }
                case 2 -> {
                    // 放到目标堆叠上（自动合并，超出上限的留在光标）
                    mc.gameMode.handleContainerInput(syncId, containerSlotOf(handler, mergeBookDst), 0, ContainerInput.PICKUP, mc.player);
                    mergeBookStep = 3;
                    return true;
                }
                case 3 -> {
                    // 归还光标剩余到来源槽，结束本次合并
                    mc.gameMode.handleContainerInput(syncId, containerSlotOf(handler, mergeBookSrc), 0, ContainerInput.PICKUP, mc.player);
                    mergeBookSrc = -1;
                    mergeBookDst = -1;
                    mergeBookStep = 0;
                    return true;
                }
            }
        }

        // 寻找下一对可合并的空白书堆叠：目标必须未满，来源须与目标同种
        for (int dst = 0; dst < INVENTORY_SLOTS; dst++) {
            ItemStack target = mc.player.getInventory().getItem(dst);
            if (!target.is(Items.BOOK) || target.getCount() >= target.getMaxStackSize()) continue;
            for (int src = 0; src < INVENTORY_SLOTS; src++) {
                if (src == dst) continue;
                ItemStack source = mc.player.getInventory().getItem(src);
                if (source.getCount() >= source.getMaxStackSize()) continue;
                if (!ItemStack.isSameItemSameComponents(source, target)) continue;
                mergeBookSrc = src;
                mergeBookDst = dst;
                mergeBookStep = 1;
                return true;
            }
        }
        return false;
    }

    // ── 物品与容器操作 ──

    /** 把背包槽位的物品换到当前选中快捷栏（旧 {@code selectItem:2813-2820}） */
    public void selectItem(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 9) {
            mc.player.getInventory().setSelectedSlot(invSlot);
            return;
        }
        int selectedSlot = mc.player.getInventory().getSelectedSlot();
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, invSlot, selectedSlot, ContainerInput.SWAP, mc.player);
    }

    /** 静默关闭当前容器（菜单不是玩家自身背包时才关） */
    public void closeContainer() {
        if (mc.player != null && mc.player.containerMenu != null
            && mc.player.containerMenu != mc.player.inventoryMenu) {
            mc.player.closeContainer();
        }
    }

    /** 玩家能否直接交互到该方块：眼睛到方块中心在「发包打开距离」内（旧 {@code canOpenNow:1891-1896}） */
    public boolean canOpenNow(BlockPos pos) {
        if (pos == null || mc.player == null || mc.level == null) return false;
        // 埋地箱（四面被方块包裹、仅顶部裸露）只能站在正上方往下开，因此不排除站在方块上方
        double range = module.settings().openDistance;
        return mc.player.getEyePosition().distanceTo(Vec3.atCenterOf(pos)) <= range;
    }

    /**
     * 发包开启方块容器（旧 {@code interactBlock:1952-1959}）。
     *
     * <p>直接发包不依赖 {@code mc.gameMode.useItemOn}：窗口失焦时后者会被吞导致开箱失败；
     * 命中面固定顶面（UP），侧面命中会因站位高低／贴墙被服务端 raycast 拒绝。</p>
     */
    public void interactBlock(BlockPos pos) {
        if (pos == null) return;
        if (!canOpenNow(pos)) return;
        BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
    }

    // ── 菜单就绪判断（旧 :1876-1888、:2231-2233） ──

    /** 附魔台菜单是否已打开（containerMenu 已同步为 EnchantmentMenu） */
    public boolean enchantMenuOpen() {
        return mc.player != null && mc.player.containerMenu instanceof EnchantmentMenu;
    }

    /** 砂轮菜单是否已打开 */
    public boolean grindMenuOpen() {
        return mc.player != null && mc.player.containerMenu instanceof GrindstoneMenu;
    }

    /** 箱子菜单是否已打开（书本箱/青金石箱/成品箱/装备箱/铁砧箱共用 ChestMenu） */
    public boolean chestMenuOpen() {
        return mc.player != null && mc.player.containerMenu instanceof ChestMenu;
    }

    /** 铁砧菜单是否已打开 */
    public boolean anvilMenuOpen() {
        return mc.player != null && mc.player.containerMenu instanceof AnvilMenu;
    }
}
