package com.yiyiaddon.feature.librarian.platform;

import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;
import com.yiyiaddon.feature.librarian.service.InventoryService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * 自动图书管理员 · 背包实现（Minecraft 适配）。
 *
 * <p>直接读取本地玩家背包（0~35 格），提供支付能力判断、空位判断与
 * 目标附魔书数量统计。数量统计同时是交易前后库存对比验证的唯一数据来源。</p>
 *
 * <p><b>统计口径</b>：只算「附魔书的 {@code STORED_ENCHANTMENTS} 里存在
 * 附魔 ID 与等级都等于目标」的整叠数量；同附魔不同等级不算，例如目标为
 * 经验修补 I（最高等级）时，背包里的低级书不会被计入。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricInventoryService}（75 行），判据逐条照搬。</p>
 */
public final class InventoryAccess implements InventoryService {
    /** 玩家背包槽位范围（0~35，含 0~8 热键栏） */
    private static final int INVENTORY_SLOT_COUNT = 36;

    @Override
    public boolean canAfford(TradeOfferSnapshot offer) {
        return count(Items.EMERALD) >= offer.emeraldCost()
            && (offer.secondCostItemIdentifier() == null
                || !"minecraft:book".equals(offer.secondCostItemIdentifier())
                || count(Items.BOOK) >= offer.secondCostCount());
    }

    @Override
    public boolean hasOutputCapacity() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        for (int slot = 0; slot < INVENTORY_SLOT_COUNT; slot++) if (mc.player.getInventory().getItem(slot).isEmpty()) return true;
        return false;
    }

    @Override
    public int countMatchingBooks(EnchantmentTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        int count = 0;
        for (int slot = 0; slot < INVENTORY_SLOT_COUNT; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (!stack.is(Items.ENCHANTED_BOOK)) continue;
            ItemEnchantments enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
            if (enchantments == null) continue;
            boolean matches = false;
            for (var entry : enchantments.entrySet()) {
                Holder<Enchantment> holder = entry.getKey();
                String id = holder.unwrapKey().map(key -> key.identifier().toString()).orElse("");
                int level = entry.getIntValue();
                if (id.equals(target.identifier()) && level == target.level()) {
                    matches = true;
                    break;
                }
            }
            if (matches) count += stack.getCount();
        }
        return count;
    }

    /** 统计背包中指定物品的总数量（0~35 格） */
    private int count(Item item) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        int count = 0;
        for (int slot = 0; slot < INVENTORY_SLOT_COUNT; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.is(item)) count += stack.getCount();
        }
        return count;
    }
}
