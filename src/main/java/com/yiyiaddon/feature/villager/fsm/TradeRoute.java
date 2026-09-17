package com.yiyiaddon.feature.villager.fsm;

/**
 * 关闭界面之后去向哪里的路由。
 *
 * <p>逐字迁移自旧项目 {@code VillagerTradeFSM.Route}（本类改名 {@code TradeRoute} 是为避免顶层类名
 * 过于笼统，七个取值名称与语义一字未改）。</p>
 *
 * <p><b>村民界面与箱子界面共用同一条关闭通道</b>：{@code CLOSING_MENU} 等到 {@code containerMenu}
 * 回到 {@code inventoryMenu} 后再按本路由分流（旧实现即如此，箱子补给/卸货结束也复用该状态）。</p>
 */
enum TradeRoute {
    /** 换下一个村民 */
    SEARCH_NEXT,
    /** 返回当前村民继续交易 */
    BACK_TO_VILLAGER,
    /** 去补给 */
    SUPPLY,
    /** 去卸货 */
    UNLOAD,
    /** 直接收尾 */
    FINISH,
    /** 推进 Pipeline 任务 */
    NEXT_TASK,
    /** 原地模式进入等待玩家操作 */
    WAIT_PLAYER
}
