package com.yiyiaddon.feature.librarian.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 自动图书管理员 · 交易报价快照。
 *
 * <p>从村民交易界面抓取的一条附魔书报价的完整信息，包括输出物、成本、
 * 附魔标识与等级、可交易状态等，供附魔匹配与购买验证使用。</p>
 *
 * <p>构造期全部字段强校验：索引非负、同步标识非空、三个资源标识格式合法、
 * 输出数量 ≥ 1、等级 ≥ 1、最高等级 ≥ 当前等级、成本非负、第二成本与数量互锁、
 * 「失效报价不能可交易」。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/TradeOfferSnapshot}（108 行），逐字照搬。</p>
 */
public record TradeOfferSnapshot(
    /** 交易索引（村民交易列表中的位置） */
    int tradeIndex,
    /** 服务端同步标识（用于购买包 SelectTrade 定位） */
    String synchronizationId,
    /** 输出物品标识（正常为 minecraft:enchanted_book） */
    String outputItemIdentifier,
    /** 输出物品数量 */
    int outputCount,
    /** 附魔标识（如 minecraft:mending） */
    String enchantmentIdentifier,
    /** 附魔等级 */
    int enchantmentLevel,
    /** 该附魔可交易的最高等级 */
    int maximumEnchantmentLevel,
    /** 绿宝石成本 */
    int emeraldCost,
    /** 第二成本物品标识（如书），可为 null */
    String secondCostItemIdentifier,
    /** 第二成本物品数量 */
    int secondCostCount,
    /** 是否可交易 */
    boolean tradable,
    /** 是否失效报价 */
    boolean invalid
) {
    /** 附魔标识合法性校验正则 */
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    public TradeOfferSnapshot {
        if (tradeIndex < 0) throw new IllegalArgumentException("tradeIndex 不能小于 0");
        synchronizationId = Objects.requireNonNull(synchronizationId, "synchronizationId");
        if (synchronizationId.isBlank()) throw new IllegalArgumentException("synchronizationId 不能为空");
        outputItemIdentifier = normalizeIdentifier(outputItemIdentifier, "outputItemIdentifier");
        enchantmentIdentifier = normalizeIdentifier(enchantmentIdentifier, "enchantmentIdentifier");
        if (outputCount < 1) throw new IllegalArgumentException("outputCount 必须大于 0");
        if (enchantmentLevel < 1) throw new IllegalArgumentException("enchantmentLevel 必须大于 0");
        if (maximumEnchantmentLevel < enchantmentLevel) {
            throw new IllegalArgumentException("maximumEnchantmentLevel 不能小于当前等级");
        }
        if (emeraldCost < 0 || secondCostCount < 0) throw new IllegalArgumentException("交易成本不能小于 0");
        if (secondCostItemIdentifier == null) {
            if (secondCostCount != 0) throw new IllegalArgumentException("第二成本数量缺少物品标识");
        } else {
            secondCostItemIdentifier = normalizeIdentifier(secondCostItemIdentifier, "secondCostItemIdentifier");
            if (secondCostCount < 1) throw new IllegalArgumentException("第二成本数量必须大于 0");
        }
        if (invalid && tradable) throw new IllegalArgumentException("失效报价不能处于可交易状态");
    }

    /** 兼容旧版签名的便捷构造（将 bookCost 归一为第二成本） */
    public TradeOfferSnapshot(
        int tradeIndex,
        String enchantmentIdentifier,
        int enchantmentLevel,
        int maximumEnchantmentLevel,
        int emeraldCost,
        int bookCost,
        boolean soldOut
    ) {
        this(
            tradeIndex,
            "legacy:" + tradeIndex,
            "minecraft:enchanted_book",
            1,
            enchantmentIdentifier,
            enchantmentLevel,
            maximumEnchantmentLevel,
            emeraldCost,
            bookCost == 0 ? null : "minecraft:book",
            bookCost,
            !soldOut,
            false
        );
    }

    /** 返回书的成本数量（第二成本为书时） */
    public int bookCost() {
        return "minecraft:book".equals(secondCostItemIdentifier) ? secondCostCount : 0;
    }

    /** 是否售罄（不可交易但非失效） */
    public boolean soldOut() {
        return !tradable && !invalid;
    }

    /** 归一化物品标识：小写并校验命名空间格式 */
    private static String normalizeIdentifier(String identifier, String name) {
        String normalized = Objects.requireNonNull(identifier, name).toLowerCase(Locale.ROOT);
        if (!IDENTIFIER_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(name + " 必须包含合法命名空间: " + normalized);
        }
        return normalized;
    }
}
