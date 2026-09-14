package com.yiyiaddon.feature.stardew.recognition;

/**
 * 作物世界状态。
 *
 * <p>与「成熟阶段编号」解耦：识别器只产出状态分类，成熟判定由服务器档案的
 * 成熟阶段规则决定，绝不把 {@code max(stage)} 直接当成熟。</p>
 */
public enum CropState {
    /** 空盆（盆上方无作物方块） */
    EMPTY("空盆"),
    /** 生长中（存在作物且非成熟阶段） */
    GROWING("生长中"),
    /** 成熟（命中服务器档案的成熟阶段或特殊变种阶段） */
    MATURE("成熟"),
    /** 特殊变种成熟（金色番茄 / 巨大菠萝等） */
    SPECIAL("特殊变种"),
    /** 死亡 */
    DEAD("死亡"),
    /** 未知（无法可靠识别） */
    UNKNOWN("未知");

    private final String displayName;

    CropState(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
