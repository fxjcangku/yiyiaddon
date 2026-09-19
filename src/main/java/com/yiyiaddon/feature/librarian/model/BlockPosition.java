package com.yiyiaddon.feature.librarian.model;

/**
 * 自动图书管理员 · 方块坐标。
 *
 * <p>轻量三维坐标值对象，仅承载 X/Y/Z 整数坐标，避免直接依赖 Minecraft 的
 * {@code BlockPos}，使 model 包脱离游戏 API 也可独立测试。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/BlockPosition}（32 行），逐字照搬。</p>
 */
public record BlockPosition(
    /** X 坐标 */
    int x,
    /** Y 坐标 */
    int y,
    /** Z 坐标 */
    int z
) {
    /** 沿指定水平方向偏移一格（Y 不变） */
    public BlockPosition offset(HorizontalDirection direction) {
        return new BlockPosition(x + direction.offsetX(), y, z + direction.offsetZ());
    }

    /** 返回下方一格的坐标 */
    public BlockPosition down() {
        return new BlockPosition(x, y - 1, z);
    }

    /** 返回上方一格的坐标 */
    public BlockPosition up() {
        return new BlockPosition(x, y + 1, z);
    }
}
