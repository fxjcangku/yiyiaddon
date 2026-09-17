package com.yiyiaddon.feature.villager.fsm;

/**
 * 交易子阶段：一次打开村民界面期间的两步（选单 → 确认）。
 *
 * <p>逐字迁移自旧项目 {@code VillagerTradeFSM.TradePhase}，取值名称与顺序未改；
 * 状态转移由 {@link VillagerTradeSession} 负责，状态机只按 {@link VillagerTradeSession#phase()}
 * 分派 {@code tickTradeSelect} / {@code tickTradeConfirm}（与旧实现同形）。</p>
 */
enum TradePhase {
    /** 选下一笔要买的交易 */
    SELECT,
    /** 已发包，等待服务端回包确认成交 */
    CONFIRM
}
