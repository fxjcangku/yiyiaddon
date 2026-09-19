package com.yiyiaddon.feature.librarian.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 自动图书管理员 · 附魔目标。
 *
 * <p>表示一个待刷取的附魔目标（附魔 ID + 等级 + 是否要求最高等级），并携带
 * 运行期进度（是否已完成、锁定的交易报价）与界面展示信息（显示名、图标）。</p>
 *
 * <p><b>等级口径</b>：模块层解析目标时用该附魔的 {@code getMaxLevel()} 作为目标等级，
 * 并要求交易报价里该附魔的「最高可交易等级」也等于该值（见 {@code EnchantmentMatcher}），
 * 因此配置界面里勾选的附魔一律按最高级刷取。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/EnchantmentTarget}（115 行），逐字照搬。</p>
 */
public record EnchantmentTarget(
    /** 附魔标识（如 minecraft:mending） */
    String identifier,
    /** 目标等级 */
    int level,
    /** 是否要求该附魔的最高可交易等级 */
    boolean requireMaximumLevel,
    /** 是否已完成（已成交验证） */
    boolean completed,
    /** 锁定的交易报价（命中后记录，可为 null） */
    TradeOfferSnapshot tradeOffer,
    /** 中文显示名 */
    String displayName,
    /** 图标标识 */
    String iconIdentifier
) {
    /** 附魔/图标标识合法性校验正则 */
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    public EnchantmentTarget {
        identifier = Objects.requireNonNull(identifier, "identifier").toLowerCase(Locale.ROOT);
        if (!IDENTIFIER_PATTERN.matcher(identifier).matches()) {
            throw new IllegalArgumentException("附魔 ID 必须包含合法命名空间: " + identifier);
        }
        if (level < 1) throw new IllegalArgumentException("level 必须大于 0");
        if (tradeOffer != null
            && (!identifier.equals(tradeOffer.enchantmentIdentifier()) || level != tradeOffer.enchantmentLevel())) {
            throw new IllegalArgumentException("锁定报价必须匹配目标附魔 ID 和准确等级");
        }
        displayName = Objects.requireNonNull(displayName, "displayName");
        iconIdentifier = Objects.requireNonNull(iconIdentifier, "iconIdentifier").toLowerCase(Locale.ROOT);
        if (displayName.isBlank()) throw new IllegalArgumentException("displayName 不能为空");
        if (!IDENTIFIER_PATTERN.matcher(iconIdentifier).matches()) {
            throw new IllegalArgumentException("图标 ID 必须包含合法命名空间: " + iconIdentifier);
        }
    }

    /** 便捷构造：显示名与图标默认取自附魔标识 */
    public EnchantmentTarget(
        String identifier,
        int level,
        boolean requireMaximumLevel,
        boolean completed,
        TradeOfferSnapshot tradeOffer
    ) {
        this(identifier, level, requireMaximumLevel, completed, tradeOffer, identifier, "minecraft:enchanted_book");
    }

    /** 便捷构造：默认未完成、无锁定报价 */
    public EnchantmentTarget(String identifier, int level, boolean requireMaximumLevel) {
        this(identifier, level, requireMaximumLevel, false, null);
    }

    /** 解析后的目标工厂：要求最高等级并附带中文显示名与图标 */
    public static EnchantmentTarget resolved(
        String identifier,
        String displayName,
        String iconIdentifier,
        int maximumTradeLevel
    ) {
        return new EnchantmentTarget(
            identifier,
            maximumTradeLevel,
            true,
            false,
            null,
            displayName,
            iconIdentifier
        );
    }

    /** 返回可交易等级（即当前目标等级） */
    public int maximumTradeLevel() {
        return level;
    }

    /** 返回最小等级（即当前目标等级） */
    public int minimumLevel() {
        return level;
    }

    /** 克隆并锁定指定交易报价 */
    public EnchantmentTarget withTradeOffer(TradeOfferSnapshot currentTradeOffer) {
        return new EnchantmentTarget(
            identifier, level, requireMaximumLevel, completed, currentTradeOffer, displayName, iconIdentifier
        );
    }

    /** 克隆并清除锁定的交易报价 */
    public EnchantmentTarget withoutTradeOffer() {
        return new EnchantmentTarget(identifier, level, requireMaximumLevel, completed, null, displayName, iconIdentifier);
    }

    /** 克隆并标记为已完成（幂等） */
    public EnchantmentTarget asCompleted() {
        return completed
            ? this
            : new EnchantmentTarget(
                identifier, level, requireMaximumLevel, true, tradeOffer, displayName, iconIdentifier
            );
    }
}
