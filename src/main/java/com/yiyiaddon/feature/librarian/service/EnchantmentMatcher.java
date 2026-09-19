package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;

/**
 * 自动图书管理员 · 附魔匹配实现。
 *
 * <p>纯逻辑匹配，不直接依赖游戏 API：判定一条交易报价是否命中目标附魔。实现
 * {@link EnchantmentService}。</p>
 *
 * <p><b>六连判据（缺一不命中）</b>：可交易 + 非失效 + 输出为附魔书 +
 * 附魔 ID 与目标相等 + 附魔等级等于目标等级 + <b>该附魔可交易最高等级也等于目标等级</b> +
 * 绿宝石成本不超上限。第五、六条合起来的语义是「只买最高级」：低级报价不会命中，</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricEnchantmentService}（25 行）。
 * 按本项目的目录口径归 {@code service/}（无 Minecraft 调用，不属 platform 适配层）。</p>
 */
public final class EnchantmentMatcher implements EnchantmentService {
    @Override
    public boolean matches(TradeOfferSnapshot offer, EnchantmentTarget target, int maximumEmeraldPrice) {
        return offer.tradable()
            && !offer.invalid()
            && "minecraft:enchanted_book".equals(offer.outputItemIdentifier())
            && offer.enchantmentIdentifier().equals(target.identifier())
            && offer.enchantmentLevel() == target.level()
            && offer.maximumEnchantmentLevel() == target.level()
            && offer.emeraldCost() <= maximumEmeraldPrice;
    }
}
