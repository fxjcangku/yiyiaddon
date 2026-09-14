package com.yiyiaddon.model.autochest;

import com.yiyiaddon.ui.render.world.ShapeMode;

/**
 * 自动箱子的 ESP 框样式。
 *
 * <p>显示名逐字取自旧项目 {@code autochest/config/AutoChestSettings.java:250-252}，禁止改写；
 * 绘制模式映射沿用本项目 {@link ShapeMode}（与旧项目一一对应）。</p>
 */
public enum EspStyle {

    LINES("仅线条", ShapeMode.Lines),
    SIDES("仅面", ShapeMode.Sides),
    BOTH("线+面", ShapeMode.Both);

    private final String displayName;
    private final ShapeMode shapeMode;

    EspStyle(String displayName, ShapeMode shapeMode) {
        this.displayName = displayName;
        this.shapeMode = shapeMode;
    }

    /** 中文文案，供界面使用 */
    public String displayName() {
        return displayName;
    }

    /** 对应的绘制模式 */
    public ShapeMode shapeMode() {
        return shapeMode;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
