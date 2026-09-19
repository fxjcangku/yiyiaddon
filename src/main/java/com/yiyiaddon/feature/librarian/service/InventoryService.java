package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;

/**
 * 自动图书管理员 · 库存契约。
 *
 * <p>背包三问：付得起吗、放得下吗、目标附魔书有几本。
 * 第三问同时是成交验证的唯一判据来源（购买前后计数对比）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/InventoryService}（16 行），逐字照搬。</p>
 */
public interface InventoryService {
    /** 是否能支付该交易（绿宝石与第二成本均充足） */
    boolean canAfford(TradeOfferSnapshot offer);

    /** 背包是否还有空位存放交易产物 */
    boolean hasOutputCapacity();

    /** 统计背包中匹配目标附魔的附魔书数量 */
    int countMatchingBooks(EnchantmentTarget target);
}
