package com.yiyiaddon.feature.stardew.plan;

/** 每作物目标数量的输入单位；执行层只消费换算后的实际个数。 */
public enum StardewAmountMode {
    GROUPS("组数"), ITEMS("个数");

    private final String title;

    StardewAmountMode(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
