package com.yiyiaddon.feature.autofarm.model;

/**
 * 自动农场的收割模式。
 *
 * 单颗收割：每次 Observe 只锁定一个成熟目标，处理完整闭环后重新 Observe。
 * 批量收割：一次 Observe 锁定最多 N 个成熟目标，但仍是严格串行执行，
 * 每个目标都必须完成 Harvest → Verify → Plant → Verify → Collect 闭环后才会进入下一个。
 *
 * toString() 返回中文显示名，供枚举设置项界面渲染。
 */
public enum HarvestMode {

    /** 单个收割：一次只处理一个成熟目标 */
    SINGLE("单个收割"),

    /** 批量收割：一次锁定多个目标，串行逐颗处理 */
    BATCH("批量收割");

    private final String displayName;

    HarvestMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
