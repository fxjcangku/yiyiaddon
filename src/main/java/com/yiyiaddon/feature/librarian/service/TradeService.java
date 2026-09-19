package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;

import java.util.List;
import java.util.Optional;

/**
 * 自动图书管理员 · 交易契约。
 *
 * <p>静默交易的完整契约：打开 → 就绪判定 → 读报价 → 选中 → 等同步 → 取出 → 关闭。
 * 「静默」指不向玩家显示交易界面（由模块取消容器屏），协议本身仍走真实开界面 +
 * 发包（SelectTrade + 结果槽 QUICK_MOVE）。</p>
 *
 * <p>报价只允许从 {@code MerchantMenu#getOffers()} 读取：客户端直接读
 * {@code Villager#getOffers()} 会抛异常闪退。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/TradeService}（36 行），逐字照搬。</p>
 */
public interface TradeService {
    /** 打开目标村民的交易界面 */
    ActionResult open(VillagerTarget target);

    /** 交易界面是否已就绪（containerMenu 已同步为 MerchantMenu） */
    boolean isTradeScreenReady();

    /** 读取第一笔附魔书交易报价 */
    Optional<TradeOfferSnapshot> readFirstEnchantedBookTrade();

    /** 扫描全部交易报价（默认实现退化为「只读第一笔」，实测由交易实现覆写为全量扫描） */
    default List<TradeOfferSnapshot> scanTrades() {
        return readFirstEnchantedBookTrade().stream().toList();
    }

    /** 选中指定交易 */
    ActionResult select(TradeOfferSnapshot offer);

    /** 选中的交易是否已与服务端同步 */
    boolean isSelectedTradeSynchronized(TradeOfferSnapshot offer);

    /** 取出交易产物 */
    ActionResult takeOutput();

    /** 关闭交易界面 */
    void close();
}
