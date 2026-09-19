package com.yiyiaddon.feature.packetbreak.model;

/**
 * 目标模式（旧项目 {@code PacketInstantBreak.TargetMode} 逐字搬运）。
 *
 * <p>两个枚举项的显示名逐字保留旧项目原文（用户交互资产，禁止改写）。</p>
 */
public enum TargetMode {
    AIM("瞄准破坏"),
    RANGE("范围自动");

    public final String displayName;

    TargetMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
