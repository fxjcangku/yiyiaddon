package com.yiyiaddon.feature.villager.trade;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.core.Holder;

/**
 * 附魔书匹配器
 * 
 * 使用 26.1.2 Data Component API 解析附魔书。
 * 
 * 匹配逻辑：
 * 1. ItemStack 必须是 ENCHANTED_BOOK
 * 2. 必须有 STORED_ENCHANTMENTS 组件
 * 3. 附魔 ID 必须匹配目标
 * 4. 不比较附魔等级，任意等级都视为匹配
 */
public final class EnchantmentMatcher {

    /**
     * 匹配附魔书（使用 enchantmentId 字符串匹配）
     */
    public static boolean matches(ItemStack stack, VillagerTradeTarget target) {
        if (stack.isEmpty() || stack.getItem() != Items.ENCHANTED_BOOK) {
            return false;
        }

        if (!target.isEnchantedBook()) {
            return false;
        }

        String targetEnchantId = target.getEnchantmentId();
        if (targetEnchantId == null) {
            return true; // 没有指定附魔ID，接受所有附魔书
        }

        // 获取附魔书的 Data Component
        ItemEnchantments enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) {
            return false;
        }

        // 检查是否包含目标附魔（不限等级）
        // 注意：ResourceKey.toString() 是 "ResourceKey[.../...]" 格式，必须取 identifier() 才是 "minecraft:xxx"
        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> enchant = entry.getKey();
            String enchantId = enchant.unwrapKey()
                .map(key -> key.identifier().toString())
                .orElse("");
            
            if (enchantId.equals(targetEnchantId)) {
                return true; // 找到匹配的附魔类型，等级不参与判断
            }
        }

        return false;
    }

    /**
     * 获取 ItemStack 的附魔详情（用于调试）
     * 
     * @param stack 附魔书 ItemStack
     * @return 附魔描述字符串，非附魔书返回 "非附魔书"
     */
    public static String getEnchantmentDetails(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem() != Items.ENCHANTED_BOOK) {
            return "非附魔书";
        }

        ItemEnchantments enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) {
            return "无附魔数据";
        }

        StringBuilder sb = new StringBuilder();
        for (var entry : enchantments.entrySet()) {
            if (sb.length() > 0) sb.append(", ");
            
            Holder<Enchantment> enchantment = entry.getKey();
            int level = entry.getIntValue();
            
            String id = enchantment.unwrapKey()
                .map(key -> key.identifier().toString())
                .orElse("unknown");
            
            sb.append(id).append(" Lv.").append(level);
        }

        return sb.toString();
    }

    private EnchantmentMatcher() {
        // 工具类禁止实例化
    }
}
