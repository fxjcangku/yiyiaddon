package com.yiyiaddon.feature.packetbreak.model;

import com.yiyiaddon.ui.render.world.ShapeMode;

/**
 * 框线样式（旧项目 {@code PacketInstantBreak.EspStyle} 逐字搬运，仅把框架侧枚举换成
 * 本项目的 {@link ShapeMode}）。
 *
 * <p>三个枚举项的显示名逐字保留旧项目原文：{@code 仅线条} / {@code 仅面} / {@code 线+面}
 * （用户交互资产，禁止改写为本项目 {@link ShapeMode#labels()} 的措辞——那是另一份设置的文案）。</p>
 */
public enum EspStyle {
    LINES("仅线条", ShapeMode.Lines),
    SIDES("仅面", ShapeMode.Sides),
    BOTH("线+面", ShapeMode.Both);

    public final String displayName;
    public final ShapeMode shapeMode;

    EspStyle(String displayName, ShapeMode shapeMode) {
        this.displayName = displayName;
        this.shapeMode = shapeMode;
    }

    public boolean lines() {
        return this == LINES || this == BOTH;
    }

    public boolean sides() {
        return this == SIDES || this == BOTH;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
