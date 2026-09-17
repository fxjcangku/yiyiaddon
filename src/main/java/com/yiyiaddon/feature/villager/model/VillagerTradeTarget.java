package com.yiyiaddon.feature.villager.model;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Objects;

/**
 * 村民交易目标定义
 * 
 * 代表一个交易目标：
 * · 普通物品（直接指定 Item）
 * · 附魔书（Item = ENCHANTED_BOOK + enchantmentId）
 */
public class VillagerTradeTarget {

    private final Item item;
    private final String displayName;
    private String enchantmentId; // 附魔ID（仅附魔书使用）

    /** 构造普通物品目标（enchantmentId 恒为 null） */
    public VillagerTradeTarget(Item item, String displayName) {
        this.item = item;
        this.displayName = displayName;
        this.enchantmentId = null;
    }

    /** 获取目标物品本体（附魔书时为 ENCHANTED_BOOK） */
    public Item getItem() {
        return item;
    }

    /** 获取目标中文显示名，用于播报与 UI 列表 */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 设置附魔ID（用于附魔书匹配）
     */
    public void setEnchantmentId(String enchantmentId) {
        this.enchantmentId = enchantmentId;
    }

    /**
     * 获取附魔ID
     */
    public String getEnchantmentId() {
        return enchantmentId;
    }

    /**
     * 是否为附魔书目标
     */
    public boolean isEnchantedBook() {
        return item == Items.ENCHANTED_BOOK;
    }

    /** 判等：item 与 enchantmentId 同时相等才算同一个目标（附魔书按附魔区分，普通物品 enchantmentId 均为 null） */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        VillagerTradeTarget that = (VillagerTradeTarget) obj;
        return Objects.equals(item, that.item) &&
               Objects.equals(enchantmentId, that.enchantmentId);
    }

    /** 哈希值必须与 {@link #equals(Object)} 同源（item + enchantmentId），否则进哈希容器会失效 */
    @Override
    public int hashCode() {
        return Objects.hash(item, enchantmentId);
    }

    /** 调试用字符串：附魔书额外打印附魔ID */
    @Override
    public String toString() {
        if (enchantmentId != null) {
            return String.format("VillagerTradeTarget[%s, enchant=%s]", item, enchantmentId);
        }
        return String.format("VillagerTradeTarget[%s]", item);
    }
}
