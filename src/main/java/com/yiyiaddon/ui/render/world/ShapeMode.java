package com.yiyiaddon.ui.render.world;

/**
 * 世界空间图形的绘制模式。
 *
 * <p>与设置项一一对应：线框 / 面 / 两者（标签见 {@link #labels()}）。每一项 ESP 都应提供该模式设置，
 * 默认值由各模块自行决定。</p>
 */
public enum ShapeMode {
    /** 只画描边线。 */
    Lines,
    /** 只画填充面。 */
    Sides,
    /** 描边与填充都画。 */
    Both;

    public boolean lines() {
        return this == Lines || this == Both;
    }

    public boolean sides() {
        return this == Sides || this == Both;
    }

    /** 设置项标签。逐字取自旧项目 {@code stardew/render/EspShapeMode} 的中文显示，禁止改写。 */
    public static String[] labels() {
        return new String[]{"线框", "面", "两者"};
    }

    public static ShapeMode of(int index) {
        ShapeMode[] values = values();
        if (index < 0 || index >= values.length) return Both;
        return values[index];
    }

    public int index() {
        return ordinal();
    }
}
