package com.yiyiaddon.feature.stardew.profile;

/**
 * 星露谷作物收获动作。
 *
 * <p>当前只开放低风险空手右键；这里保留独立枚举是为了让持久化规则显式记录动作，
 * 同时从类型层阻止学习失败后偷偷降级为 {@code breakBlock}。</p>
 */
public enum StardewHarvestAction {

    /** 空手对成熟作物发送右键交互。 */
    RIGHT_CLICK
}
