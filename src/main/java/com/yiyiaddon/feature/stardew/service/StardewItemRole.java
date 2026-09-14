package com.yiyiaddon.feature.stardew.service;

/** 作物物品在后勤中的唯一职责；种子优先级最高，绝不作为产物卸货。 */
public enum StardewItemRole {
    SEED,
    PRODUCE,
    VARIANT,
    OTHER;

    public boolean unloadable() {
        return this == PRODUCE || this == VARIANT;
    }
}
