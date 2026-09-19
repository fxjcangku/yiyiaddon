package com.yiyiaddon.feature.teleport.safety;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.Blocks;

/**
 * 碰撞安全判据：完全基于 26.1.2 真实碰撞形状（VoxelShape）与玩家实际碰撞箱，
 * 不使用方块类型黑名单、不使用 isFullCube()、不把「找到空气」当作安全。
 *
 * <p>安全 = 玩家碰撞箱能真实放入（无方块碰撞）+ 脚下有真实支撑（台阶/半砖等
 * 非完整方块的碰撞形状也算）+ 无岩浆/火焰危险 + 支撑不是悬空虚空。
 * 实体（村民/怪物/掉落物等）不参与判定——游戏允许实体重叠，实体不是墙。</p>
 */
public final class CollisionSafety {

    /** 支撑探测半宽：代表玩家脚底区域的横向采样宽度 */
    private static final double SUPPORT_HALF = 0.29;

    /** 虚空判定：支撑下方连续无碰撞方块超过该格数视为悬空风险 */
    private static final int VOID_GAP_MAX = 25;

    private CollisionSafety() {
    }

    /**
     * 完整安全判定：区块可用 → 有空间 → 有支撑 → 无岩浆/火焰 → 支撑不悬空。
     *
     * @return 不安全原因（中文），全部通过返回 null
     */
    public static String checkStand(ClientLevel level, EntityDimensions dims, double x, double y, double z) {
        if (!areaLoaded(level, x, z)) {
            return "目标区域区块数据未加载";
        }
        if (!hasSpace(level, dims, x, y, z)) {
            return "玩家碰撞空间不足";
        }
        if (!hasSupport(level, x, y, z)) {
            return "目标位置没有支撑";
        }
        String hazard = hazardAt(level, dims, x, y, z);
        if (hazard != null) {
            return hazard;
        }
        return voidCheck(level, x, y, z);
    }

    /** 目标所在区块是否已加载（未知区块禁止当作安全落点） */
    public static boolean areaLoaded(ClientLevel level, double x, double z) {
        int chunkX = (int) Math.floor(x) >> 4;
        int chunkZ = (int) Math.floor(z) >> 4;
        return level.hasChunk(chunkX, chunkZ);
    }

    /**
     * 玩家碰撞箱能否放入目标位置：以真实 EntityDimensions 生成碰撞箱，
     * 用 Level.getBlockCollisions 读取所有相交的真实 VoxelShape，
     * 存在任何非空形状即判定空间不足。
     */
    public static boolean hasSpace(ClientLevel level, EntityDimensions dims, double x, double y, double z) {
        AABB box = dims.makeBoundingBox(x, y, z);
        for (VoxelShape shape : level.getBlockCollisions(null, box)) {
            if (shape != null && !shape.isEmpty()) return false;
        }
        return true;
    }

    /**
     * 脚下是否有支撑：采样脚底下方一格的薄层，凡有非空碰撞形状（含台阶/
     * 半砖顶面、活板门等非完整方块的真实形状）即视为可站立支撑。
     */
    public static boolean hasSupport(ClientLevel level, double x, double y, double z) {
        AABB support = new AABB(
            x - SUPPORT_HALF, y - 1.0, z - SUPPORT_HALF,
            x + SUPPORT_HALF, y - 0.001, z + SUPPORT_HALF);
        for (VoxelShape shape : level.getBlockCollisions(null, support)) {
            if (shape != null && !shape.isEmpty()) return true;
        }
        return false;
    }

    /**
     * 危险环境检测：只针对明确危险——岩浆流体与火焰方块。
     * 普通墙体/方块不视为危险（TP穿墙的语义就是穿过它们）。
     * 水流体不视为危险（水下位置允许）。
     *
     * @return 危险描述（中文），无危险返回 null
     */
    public static String hazardAt(ClientLevel level, EntityDimensions dims, double x, double y, double z) {
        int minY = (int) Math.floor(y);
        int maxY = (int) Math.floor(y + dims.height() - 0.01);
        for (int by = minY; by <= maxY; by++) {
            BlockPos pos = new BlockPos((int) Math.floor(x), by, (int) Math.floor(z));
            if (level.getFluidState(pos).is(FluidTags.LAVA)) {
                return "目标存在危险岩浆";
            }
            if (level.getBlockState(pos).is(Blocks.FIRE)) {
                return "目标存在火焰";
            }
        }
        return null;
    }

    /**
     * 虚空/悬空风险：支撑方块下方连续无碰撞层超过阈值（单层浮空平台等
     * 严重不稳定结构）判为风险；探测途中遇到未加载区块判为数据不可用，
     * 未知数据绝不能当作安全。
     *
     * @return 风险描述（中文），无风险返回 null
     */
    public static String voidCheck(ClientLevel level, double x, double y, double z) {
        int cx = (int) Math.floor(x);
        int cz = (int) Math.floor(z);
        int supportY = (int) Math.floor(y) - 1;
        int gap = 0;
        for (int dy = 1; dy <= VOID_GAP_MAX; dy++) {
            int by = supportY - dy;
            if (!level.hasChunk(cx >> 4, cz >> 4)) {
                return "目标区域区块数据未加载";
            }
            if (cellSolid(level, cx, by, cz)) {
                gap = 0;
            } else if (++gap >= VOID_GAP_MAX) {
                return "目标支撑悬空，存在虚空风险";
            }
        }
        return null;
    }

    /** 单格是否存在非空碰撞形状（以整格 AABB 查询真实形状） */
    public static boolean cellSolid(ClientLevel level, int x, int y, int z) {
        AABB cell = new AABB(x, y, z, x + 1, y + 1, z + 1);
        for (VoxelShape shape : level.getBlockCollisions(null, cell)) {
            if (shape != null && !shape.isEmpty()) return true;
        }
        return false;
    }
}
