package com.yiyiaddon.feature.packetbreak.model;

/**
 * 百分比标签内容（旧项目 {@code PacketInstantBreak.LabelStyle} 逐字搬运）。
 *
 * <p>三个枚举项的显示名逐字保留旧项目原文（用户交互资产，禁止改写）。</p>
 */
public enum LabelStyle {
    PERCENT("仅百分比"),
    BLOCK("百分比+方块名"),
    TICK("百分比+剩余tick");

    public final String displayName;

    LabelStyle(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
