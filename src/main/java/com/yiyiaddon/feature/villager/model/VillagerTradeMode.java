package com.yiyiaddon.feature.villager.model;

/**
 * 村民交易运行模式。
 *
 * <p>逐字迁移自旧项目 {@code AutoVillagerTradeModule.Mode}（旧 {@code :665-681}）：三个枚举值、
 * 三个中文显示名与 {@link #toString()} 行为全部保留（启动报告与「运行模式」设置项直接取
 * {@code toString()}，见 55 号 §2.4）。</p>
 *
 * <p>三模式的核心差异：</p>
 * <ul>
 *   <li>{@link #LOCAL} 原地交易：不寻路，只与身边村民自动交易，绿宝石不足 / 背包满仅提示玩家手动处理；</li>
 *   <li>{@link #SINGLE_PATH} 寻路单点：寻路到村民附近，交易过程中自动去箱子补给 / 卸货；</li>
 *   <li>{@link #PIPELINE} 多任务：任务队列顺序执行，每个任务等价一次 SINGLE_PATH。</li>
 * </ul>
 */
public enum VillagerTradeMode {
    LOCAL("原地交易"),
    SINGLE_PATH("寻路单点"),
    PIPELINE("多任务");

    private final String displayName;

    VillagerTradeMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
