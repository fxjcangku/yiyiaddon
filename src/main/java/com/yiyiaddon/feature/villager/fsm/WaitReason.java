package com.yiyiaddon.feature.villager.fsm;

/**
 * 原地模式（LOCAL）等待玩家手动操作的原因。
 *
 * <p>逐字迁移自旧项目 {@code VillagerTradeFSM.WaitReason}，取值名称与顺序未改；
 * 等待期间按本枚举决定提示文案与「恢复条件」（绿宝石回阈值 / 背包腾出空位）。</p>
 */
enum WaitReason {
    /** 等玩家手动补给绿宝石 */
    SUPPLY,
    /** 等玩家手动卸货 */
    UNLOAD
}
