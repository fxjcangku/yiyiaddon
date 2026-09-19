package com.yiyiaddon.feature.librarian.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 自动图书管理员 · 目标附魔书库存快照。
 *
 * <p>记录某一时刻背包中「目标附魔 + 目标等级」附魔书的数量，用于交易前后
 * 对比验证购买是否真正生效（服务器同步后数量增加即视为成交成功）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/InventorySnapshot}（56 行），逐字照搬。</p>
 */
public record InventorySnapshot(
    /** 附魔标识（如 minecraft:mending） */
    String enchantmentIdentifier,
    /** 附魔等级 */
    int enchantmentLevel,
    /** 匹配目标附魔与等级的附魔书数量 */
    int matchingBookCount,
    /** 采样序号（单调递增，用于区分先后快照） */
    long sampleId
) {
    /** 附魔标识合法性校验正则 */
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    public InventorySnapshot {
        enchantmentIdentifier = Objects.requireNonNull(enchantmentIdentifier, "enchantmentIdentifier")
            .toLowerCase(Locale.ROOT);
        if (!IDENTIFIER_PATTERN.matcher(enchantmentIdentifier).matches()) {
            throw new IllegalArgumentException("附魔 ID 必须包含合法命名空间: " + enchantmentIdentifier);
        }
        if (enchantmentLevel < 1) throw new IllegalArgumentException("enchantmentLevel 必须大于 0");
        if (matchingBookCount < 0) throw new IllegalArgumentException("matchingBookCount 不能小于 0");
        if (sampleId < 0) throw new IllegalArgumentException("sampleId 不能小于 0");
    }

    /** 判断本快照是否匹配指定目标附魔（ID 与等级完全一致） */
    public boolean matchesTarget(EnchantmentTarget target) {
        Objects.requireNonNull(target, "target");
        return enchantmentIdentifier.equals(target.identifier()) && enchantmentLevel == target.level();
    }

    /** 判断两个快照是否针对同一目标附魔（ID 与等级一致） */
    public boolean sameTargetAs(InventorySnapshot other) {
        Objects.requireNonNull(other, "other");
        return enchantmentIdentifier.equals(other.enchantmentIdentifier)
            && enchantmentLevel == other.enchantmentLevel;
    }

    /** 判断本快照相对 earlier 快照是否库存数量增加（用于成交验证） */
    public boolean hasIncreaseFrom(InventorySnapshot before) {
        Objects.requireNonNull(before, "before");
        return sameTargetAs(before) && matchingBookCount > before.matchingBookCount;
    }
}
