package com.yiyiaddon.feature.stardew.navigation;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Stardew 后勤容器外围站位规划器。
 *
 * <p>容器坐标只用于交互，绝不直接作为玩家寻路终点。双箱先展开完整 footprint，再从整个箱体
 * 外围筛选有支撑、脚部与头部无碰撞、不是箱体本身也不是箱顶的合法站位，最后按玩家距离排序。
 * 协调器可以在最近候选不可达时继续尝试下一候选。</p>
 */
public final class ContainerApproachPlanner {

    /** 一次规划结果：完整箱体、外围站位、实际交互的箱体方块。 */
    public record Approach(List<BlockPos> footprint, BlockPos standPos, BlockPos interactPos) {
        public Approach {
            footprint = List.copyOf(footprint);
        }
    }

    private ContainerApproachPlanner() {
    }

    /** 返回由近到远的全部合法外围方案。 */
    public static List<Approach> candidates(BlockPos containerPos, double interactionReach) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || containerPos == null) return List.of();
        List<BlockPos> footprint = footprint(containerPos);
        Set<BlockPos> body = Set.copyOf(footprint);
        LinkedHashSet<BlockPos> candidates = new LinkedHashSet<>();
        for (BlockPos part : footprint) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos stand = part.relative(direction);
                if (!body.contains(stand)) candidates.add(stand.immutable());
            }
        }

        List<Approach> result = new ArrayList<>();
        for (BlockPos stand : candidates) {
            if (!legalStand(stand, body)) continue;
            BlockPos interact = nearestPart(stand, footprint);
            if (!withinReachFromStand(stand, footprint, interactionReach)) continue;
            result.add(new Approach(footprint, stand, interact));
        }
        result.sort(Comparator.comparingDouble(approach -> distanceToPlayerSq(approach.standPos())));
        return result;
    }

    /** 玩家此刻必须仍在外围站位并处于整个箱体的正常交互距离内。 */
    public static boolean readyToInteract(Approach approach, double standReach, double interactionReach) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || approach == null) return false;
        double dx = approach.standPos().getX() + 0.5 - mc.player.getX();
        double dy = approach.standPos().getY() - mc.player.getY();
        double dz = approach.standPos().getZ() + 0.5 - mc.player.getZ();
        if (dx * dx + dy * dy + dz * dz > standReach * standReach) return false;
        Vec3 eye = mc.player.getEyePosition();
        double best = Double.MAX_VALUE;
        for (BlockPos part : approach.footprint()) {
            best = Math.min(best, new AABB(part).distanceToSqr(eye));
        }
        return best <= interactionReach * interactionReach;
    }

    /** 单箱为一个方块；双箱沿原版 ChestBlock 连接方向展开另一半。 */
    private static List<BlockPos> footprint(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return List.of(pos.immutable());
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
            return List.of(pos.immutable());
        }
        BlockPos connected = pos.relative(ChestBlock.getConnectedDirection(state));
        BlockState connectedState = mc.level.getBlockState(connected);
        if (!(connectedState.getBlock() instanceof ChestBlock)) return List.of(pos.immutable());
        return List.of(pos.immutable(), connected.immutable());
    }

    /** 合法站位必须有真实支撑、两格净空，并明确排除箱体与箱顶。 */
    private static boolean legalStand(BlockPos stand, Set<BlockPos> footprint) {
        for (BlockPos body : footprint) {
            if (stand.equals(body) || stand.equals(body.above())) return false;
        }
        return standable(stand);
    }

    /**
     * 「人能不能站在这一格」的唯一判据：脚下有真实支撑面、脚部与头部两格都无碰撞、玩家碰撞箱不撞世界。
     *
     * <p>后勤外围站位与回农田中心的落点共用这一份判据，禁止各写一份——两处口径一旦分叉，
     * 就会出现「站得上去的地方说站不住、站不住的地方硬要去」这类自相矛盾的导航目标。</p>
     */
    public static boolean standable(BlockPos stand) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || stand == null || !mc.level.isLoaded(stand)) return false;
        BlockPos supportPos = stand.below();
        BlockState support = mc.level.getBlockState(supportPos);
        if (support.isAir() || !support.isFaceSturdy(mc.level, supportPos, Direction.UP)) return false;
        BlockState feet = mc.level.getBlockState(stand);
        BlockState head = mc.level.getBlockState(stand.above());
        if (!feet.getCollisionShape(mc.level, stand).isEmpty()
            || !head.getCollisionShape(mc.level, stand.above()).isEmpty()) return false;
        double centerX = stand.getX() + 0.5;
        double centerZ = stand.getZ() + 0.5;
        double halfWidth = mc.player.getBbWidth() / 2.0;
        AABB playerBox = new AABB(centerX - halfWidth, stand.getY(), centerZ - halfWidth,
            centerX + halfWidth, stand.getY() + mc.player.getBbHeight(), centerZ + halfWidth);
        return mc.level.noCollision(mc.player, playerBox);
    }

    /** 从候选站位的眼睛位置计算是否能触及 footprint 任一方块。 */
    private static boolean withinReachFromStand(BlockPos stand, List<BlockPos> footprint, double reach) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        Vec3 eye = new Vec3(stand.getX() + 0.5, stand.getY() + mc.player.getEyeHeight(), stand.getZ() + 0.5);
        for (BlockPos part : footprint) {
            if (new AABB(part).distanceToSqr(eye) <= reach * reach) return true;
        }
        return false;
    }

    private static BlockPos nearestPart(BlockPos stand, List<BlockPos> footprint) {
        BlockPos best = footprint.get(0);
        double bestSq = Double.MAX_VALUE;
        for (BlockPos part : footprint) {
            double dx = part.getX() - stand.getX();
            double dz = part.getZ() - stand.getZ();
            double distanceSq = dx * dx + dz * dz;
            if (distanceSq < bestSq) {
                bestSq = distanceSq;
                best = part;
            }
        }
        return best;
    }

    private static double distanceToPlayerSq(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return Double.MAX_VALUE;
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() - mc.player.getY();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
