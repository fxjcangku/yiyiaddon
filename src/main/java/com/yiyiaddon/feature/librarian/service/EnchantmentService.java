package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;

/**
 * 自动图书管理员 · 附魔匹配契约。
 *
 * <p>命中一条报价的唯一判据入口；实现是纯逻辑（{@link EnchantmentMatcher}），
 * 不直接调用游戏 API，因此可脱离客户端单独验证。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/EnchantmentService}（35 行）。
 * 按 D1 拍板，删除旧项目中零调用的三个默认方法
 * （{@code getMaximumTradeLevel} / {@code isLibrarianTradeable} / {@code findMatch}，
 * 前者一旦被调用还会抛 {@code UnsupportedOperationException}）；
 * 「在未完成目标里找第一个命中」的语义由编排器内联实现，不在契约里留第二份。</p>
 */
public interface EnchantmentService {
    /** 判断交易报价是否命中目标附魔（附魔 ID + 等级 + 价格上限三重匹配） */
    boolean matches(TradeOfferSnapshot offer, EnchantmentTarget target, int maximumEmeraldPrice);
}
