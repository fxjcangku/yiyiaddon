package com.yiyiaddon.feature.packetbreak.model;

/**
 * 破坏方式（旧项目 {@code PacketInstantBreak.BreakMode} 逐字搬运）。
 *
 * <p>两个枚举项的显示名逐字保留旧项目原文（用户交互资产，禁止改写）。</p>
 */
public enum BreakMode {
    INSTANT("极速卡点（0.7 阈值）"),
    VANILLA("原版速度");

    public final String displayName;

    BreakMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
