package com.yiyiaddon.feature.librarian.model;

/**
 * 自动图书管理员 · 水平方向。
 *
 * <p>仅包含四个水平朝向（北东南西），用于村民朝向、讲台阅读面、玩家站位偏移等
 * 固定交易位几何计算。每个方向携带 X / Z 轴偏移量。</p>
 *
 * <p><b>枚举顺序即扫描顺序</b>：工位探测按 {@code values()} 顺序遍历四个方向，
 * 因此取值顺序（NORTH / EAST / SOUTH / WEST）属于行为的一部分，禁止调整。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/HorizontalDirection}（44 行），逐字照搬。</p>
 */
public enum HorizontalDirection {
    /** 北：Z 轴负方向 */
    NORTH(0, -1),
    /** 东：X 轴正方向 */
    EAST(1, 0),
    /** 南：Z 轴正方向 */
    SOUTH(0, 1),
    /** 西：X 轴负方向 */
    WEST(-1, 0);

    /** X 轴偏移量 */
    private final int offsetX;
    /** Z 轴偏移量 */
    private final int offsetZ;

    HorizontalDirection(int offsetX, int offsetZ) {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
    }

    /** 返回 X 轴偏移量 */
    public int offsetX() {
        return offsetX;
    }

    /** 返回 Z 轴偏移量 */
    public int offsetZ() {
        return offsetZ;
    }

    /** 返回相反方向（用于计算讲台阅读面，阅读面与村民朝向相反） */
    public HorizontalDirection opposite() {
        return values()[(ordinal() + 2) % values().length];
    }
}
