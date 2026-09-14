package com.yiyiaddon.ui.render.world;

/**
 * 方块面方向的位掩码。
 *
 * <p>用于 {@link EspRenderer#box(net.minecraft.world.phys.AABB, int, int, ShapeMode, float, int)}
 * 的 {@code excludeDir} 参数：被排除的面不绘制。成片方块 ESP（例如一块 9×9 农田）如果不排除面，
 * 相邻方块之间的内部面会全部绘制，既浪费又难看。</p>
 *
 * <p>方向与 Minecraft 坐标一致：{@code -Z} 为北、{@code +Z} 为南、{@code -X} 为西、{@code +X} 为东。</p>
 */
public final class Dir {

    public static final int DOWN = 1;
    public static final int UP = 2;
    public static final int NORTH = 4;
    public static final int SOUTH = 8;
    public static final int WEST = 16;
    public static final int EAST = 32;

    /** 全部方向；等价于不排除任何面。 */
    public static final int ALL = DOWN | UP | NORTH | SOUTH | WEST | EAST;

    private Dir() {
    }

    /** 该方向是否被排除。 */
    public static boolean excluded(int mask, int dir) {
        return (mask & dir) != 0;
    }

    /** 该方向是否保留（未被排除）。 */
    public static boolean keeps(int mask, int dir) {
        return (mask & dir) == 0;
    }

    /** 判定一条棱是否该画：棱归属两个面，两面都被排除时才省略。 */
    public static boolean keepsEdge(int mask, int faceA, int faceB) {
        return keeps(mask, faceA) || keeps(mask, faceB);
    }
}
