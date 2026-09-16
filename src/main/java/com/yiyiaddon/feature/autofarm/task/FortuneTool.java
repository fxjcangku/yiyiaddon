package com.yiyiaddon.feature.autofarm.task;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * 时运工具准备器：把背包里时运等级最高的工具换到主手，供单个/批量收割任务复用。
 *
 * 主手已有时运或确认背包无时运工具时返回 true；快捷栏有时运工具时直接切换；
 * 否则从全背包（含副手）移到主手并等待同步，返回 false 由任务下一 tick 重试。
 */
public final class FortuneTool {

    /** 是否通过快捷栏切换过工具（restore 时恢复原槽位） */
    private boolean swapped;
    /** 切换前的原选中槽，restore 时恢复 */
    private int previousSelected = -1;
    /** 是否已发起背包移动、等待主手同步 */
    private boolean preparing;
    /** 等待主手同步的 tick 计数，超时重发移动防卡死 */
    private int prepTicks;

    /** 确保主手拿时运工具；返回 false 表示刚发起背包移动、需等待主手同步后重试 */
    public boolean ensure() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return true;

        // 主手已有时运：直接使用
        if (fortuneLevel(mc.player.getMainHandItem()) > 0) {
            preparing = false;
            prepTicks = 0;
            return true;
        }

        // 快捷栏找最高时运工具，找到直接切换
        int hotbar = bestFortuneHotbarSlot();
        if (hotbar >= 0) {
            previousSelected = mc.player.getInventory().getSelectedSlot();
            InventoryOps.selectHotbar(hotbar);
            swapped = true;
            preparing = false;
            prepTicks = 0;
            return true;
        }

        // 全背包（含副手）找时运工具并移到主手，等待同步
        int best = findBestFortune();
        if (best != InventoryOps.NOT_FOUND) {
            prepTicks++;
            if (!preparing || prepTicks > 8) {
                InventoryOps.moveToSelectedHotbar(best);
                preparing = true;
                prepTicks = 0;
            }
            return false;
        }

        // 没有时运工具：裸手收割
        preparing = false;
        prepTicks = 0;
        return true;
    }

    /** 恢复被切换的快捷栏槽位（任务结束/取消时调用） */
    public void restore() {
        if (swapped) {
            InventoryOps.selectHotbar(previousSelected);
            swapped = false;
        }
    }

    /** 快捷栏里时运等级最高的槽位，没有返回 -1 */
    private int bestFortuneHotbarSlot() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return -1;
        int bestSlot = -1;
        int bestLevel = 0;
        for (int i = 0; i < 9; i++) {
            int lvl = fortuneLevel(mc.player.getInventory().getItem(i));
            if (lvl > bestLevel) {
                bestLevel = lvl;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 全背包（含副手）里时运等级最高的工具槽位，没有返回 -1 */
    private int findBestFortune() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return InventoryOps.NOT_FOUND;

        int bestSlot = InventoryOps.NOT_FOUND;
        int bestLevel = 0;
        int size = mc.player.getInventory().getContainerSize();
        for (int i = 0; i < size; i++) {
            int lvl = fortuneLevel(mc.player.getInventory().getItem(i));
            if (lvl > bestLevel) {
                bestLevel = lvl;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 读取物品的时运附魔等级（26.x 附魔为动态注册表，从世界注册表解析） */
    private int fortuneLevel(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        if (stack.isEmpty() || mc.level == null) return 0;
        ItemEnchantments ench = stack.get(DataComponents.ENCHANTMENTS);
        if (ench == null || ench.isEmpty()) return 0;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(Enchantments.FORTUNE).orElse(null);
            return holder == null ? 0 : ench.getLevel(holder);
        } catch (Exception ignored) {
            return 0;
        }
    }
}
