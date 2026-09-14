package com.yiyiaddon.feature.autochest.service;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 寻路服务：把玩家带到「容器面前约一格」的可站立位置。
 *
 * <p>绝对禁止直接把容器中心坐标当站位目标——那样玩家会卡在方块里。
 * {@link #computeStandPosition} 按四个水平方向寻找「脚下为完整方块、脚部与头部
 * 可通行」的可站立格，再用 Baritone 的 {@link GoalBlock} 精确寻路到该站位；
 * 找不到安全站位时才退化用 {@link GoalTwoBlocks} 兜底。</p>
 */
public final class PathingService {

    private final Minecraft mc;

    public PathingService() {
        this(Minecraft.getInstance());
    }

    public PathingService(Minecraft mc) {
        this.mc = mc;
    }

    /**
     * 计算容器面前一格可站立位置（玩家可正常面对容器交互的安全站位）。
     *
     * <p>遍历容器同层、下一层、上一层的四个水平相邻格，优先返回脚下为完整碰撞方块
     * 且脚部/头部可通行的位置；找不到返回 {@code null}。绝不以容器中心作为站位。</p>
     *
     * @param chestPos 容器坐标
     * @return 可站立目标坐标；找不到安全站位返回 null
     */
    public static BlockPos computeStandPosition(BlockPos chestPos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || chestPos == null) return null;

        Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        // 先试与容器同层，再试容器下一层/上一层（容器可能悬空或高于地面一格）
        for (int dy : new int[]{0, -1, 1}) {
            BlockPos base = chestPos.offset(0, dy, 0);
            for (Direction dir : dirs) {
                BlockPos candidate = base.relative(dir);
                if (!candidate.equals(chestPos) && isStandable(candidate)) {
                    return candidate;
                }
            }
        }
        return null;
    }

    /**
     * 发起寻路到容器面前的安全站位。
     *
     * @param chestPos 容器坐标
     * @return 是否成功发起
     */
    public boolean pathTo(BlockPos chestPos) {
        return pathTo(chestPos, computeStandPosition(chestPos));
    }

    /**
     * 发起寻路到指定站位。
     *
     * @param chestPos 容器坐标（无安全站位时作 {@link GoalTwoBlocks} 兜底目标）
     * @param standPos 已算好的安全站位；为 {@code null} 时退化用容器坐标兜底
     * @return 是否成功发起
     */
    public boolean pathTo(BlockPos chestPos, BlockPos standPos) {
        IBaritone baritone = baritone();
        if (baritone == null) return false;
        try {
            if (standPos != null) {
                // 精确走到安全站位，禁止以容器中心为最终目标
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(standPos));
            } else {
                // 无安全站位时兜底：GoalTwoBlocks 保证不站在容器方块内
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(chestPos));
            }
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /** 是否正在实际寻路（区别于「算路中」） */
    public boolean isPathing() {
        IBaritone baritone = baritone();
        if (baritone == null) return false;
        try {
            return baritone.getPathingBehavior().isPathing();
        } catch (Throwable e) {
            return false;
        }
    }

    /** 停止寻路（取消路径并清除目标，使 isActive/isPathing 立即回落） */
    public void stop() {
        IBaritone baritone = baritone();
        if (baritone == null) return;
        try {
            baritone.getPathingBehavior().cancelEverything();
            baritone.getCustomGoalProcess().setGoal(null);
        } catch (Throwable ignored) {
            // 取消失败不影响
        }
    }

    /** 指定坐标是否仍存在合法容器（用于到达后二次校验） */
    public boolean isContainerAt(BlockPos pos) {
        if (mc.level == null) return false;
        BlockEntity entity = mc.level.getBlockEntity(pos);
        return entity instanceof Container;
    }

    /** 判定站位是否可站立：脚下为完整碰撞方块，脚部与头部均可通行 */
    private static boolean isStandable(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;

        BlockPos below = pos.below();
        BlockState belowState = mc.level.getBlockState(below);
        if (!belowState.isCollisionShapeFullBlock(mc.level, below)) return false;

        BlockState feet = mc.level.getBlockState(pos);
        BlockState head = mc.level.getBlockState(pos.above());
        return !feet.isCollisionShapeFullBlock(mc.level, pos)
            && !head.isCollisionShapeFullBlock(mc.level, pos.above());
    }

    private IBaritone baritone() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable e) {
            return null;
        }
    }
}
