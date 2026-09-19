package com.yiyiaddon.feature.teleport.model;

import com.yiyiaddon.feature.teleport.safety.CollisionSafety;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 传送移动对象（Teleport Subject）：统一描述「本轮真正要移动的东西」。
 *
 * <p>玩家未骑乘时是 Player；骑乘时是根载具 + 全部乘客（含嵌套多乘客，
 * 如 Happy Ghast 上的多名玩家）。碰撞判定完全基于各实体触发瞬间的真实
 * BoundingBox（当前 Pose / 尺寸），不做实体类型白名单、不硬编码尺寸、
 * 不把尺寸简单相加，而是把整棵树整体平移后逐一校验真实碰撞形状。</p>
 *
 * <p>玩家单独 TP 复用 {@link CollisionSafety} 原判据（含支撑/虚空）；
 * 载具树只校验「碰撞容纳 + 岩浆/火焰 + 世界边界 + 区块加载」，不强制
 * 「脚下必须有方块」，从而允许飞行载具（空中）与水生载具（水中）存在。</p>
 */
public final class TeleportSubject {

    /** 是否仅玩家（未骑乘） */
    private final boolean playerOnly;

    /** 触发瞬间各实体真实碰撞箱（世界坐标，顺序与移动对象树一致） */
    private final List<AABB> boxes = new ArrayList<>();

    /** 触发瞬间本地玩家脚底位置（目标位移统一映射到整棵树的基准） */
    private final Vec3 origin;

    /** 玩家当前尺寸（playerOnly 时复用 CollisionSafety 原判据） */
    private final EntityDimensions playerDims;

    private TeleportSubject(boolean playerOnly, Vec3 origin, EntityDimensions playerDims) {
        this.playerOnly = playerOnly;
        this.origin = origin;
        this.playerDims = playerDims;
    }

    /**
     * 从本地玩家构建移动对象：未骑乘取玩家本身；骑乘取根载具及其全部
     * 间接乘客（{@code getSelfAndPassengers()} 递归展开嵌套）。
     */
    public static TeleportSubject of(LocalPlayer player) {
        Vec3 origin = player.position();
        EntityDimensions dims = player.getDimensions(player.getPose());
        TeleportSubject subject = new TeleportSubject(!player.isPassenger(), origin, dims);
        Entity root = player.isPassenger() ? player.getRootVehicle() : player;
        // 根载具可能为 null（极端状态），兜底回退到玩家本身
        if (root == null) root = player;
        root.getSelfAndPassengers().forEach(e -> subject.boxes.add(e.getBoundingBox()));
        return subject;
    }

    /** 把整棵移动对象平移到目标（本地玩家脚底 = x,y,z）后，各实体碰撞箱 */
    public List<AABB> boxesAt(double x, double y, double z) {
        double dx = x - origin.x();
        double dy = y - origin.y();
        double dz = z - origin.z();
        List<AABB> moved = new ArrayList<>(boxes.size());
        for (AABB box : boxes) {
            moved.add(box.move(dx, dy, dz));
        }
        return moved;
    }

    /** 目标位置能否容纳整棵移动对象（无方块碰撞）。玩家单独走原判据。 */
    public boolean hasSpace(ClientLevel level, double x, double y, double z) {
        if (playerOnly) {
            return CollisionSafety.hasSpace(level, playerDims, x, y, z);
        }
        for (AABB box : boxesAt(x, y, z)) {
            for (VoxelShape shape : level.getBlockCollisions(null, box)) {
                if (shape != null && !shape.isEmpty()) return false;
            }
        }
        return true;
    }

    /**
     * 完整安全判定。玩家单独复用 {@link CollisionSafety#checkStand}；
     * 载具树检查：区块加载 → 碰撞容纳 → 岩浆/火焰 → 世界边界（虚空）。
     *
     * @return 不安全原因（中文），全部通过返回 null
     */
    public String checkStand(ClientLevel level, double x, double y, double z) {
        if (playerOnly) {
            return CollisionSafety.checkStand(level, playerDims, x, y, z);
        }

        List<AABB> moved = boxesAt(x, y, z);

        if (!areaLoadedForBoxes(level, moved)) {
            return "目标区域区块数据未加载";
        }
        for (AABB box : moved) {
            for (VoxelShape shape : level.getBlockCollisions(null, box)) {
                if (shape != null && !shape.isEmpty()) {
                    return "载具/乘客碰撞空间不足";
                }
            }
        }

        String hazard = hazardInBoxes(level, moved);
        if (hazard != null) {
            return hazard;
        }

        // 世界边界/虚空：整棵移动对象的底部不得低于当前维度世界最低建筑高度
        double minBottom = Double.MAX_VALUE;
        for (AABB box : moved) {
            minBottom = Math.min(minBottom, box.minY);
        }
        if (minBottom < level.getMinY()) {
            return "目标位于世界边界外（虚空）";
        }
        return null;
    }

    /** 载具树覆盖的所有区块是否均已加载（未知区块绝不当作安全空间） */
    private static boolean areaLoadedForBoxes(ClientLevel level, List<AABB> moved) {
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;
        for (AABB box : moved) {
            minX = Math.min(minX, box.minX);
            maxX = Math.max(maxX, box.maxX);
            minZ = Math.min(minZ, box.minZ);
            maxZ = Math.max(maxZ, box.maxZ);
        }
        int cx0 = (int) Math.floor(minX) >> 4;
        int cx1 = (int) Math.floor(maxX) >> 4;
        int cz0 = (int) Math.floor(minZ) >> 4;
        int cz1 = (int) Math.floor(maxZ) >> 4;
        for (int cx = cx0; cx <= cx1; cx++) {
            for (int cz = cz0; cz <= cz1; cz++) {
                if (!level.hasChunk(cx, cz)) return false;
            }
        }
        return true;
    }

    /** 整棵移动对象范围内的岩浆/火焰危险检测（水不判危险） */
    private static String hazardInBoxes(ClientLevel level, List<AABB> moved) {
        for (AABB box : moved) {
            String hazard = hazardInBox(level, box);
            if (hazard != null) return hazard;
        }
        return null;
    }

    /** 单个碰撞箱覆盖格内的岩浆/火焰检测 */
    private static String hazardInBox(ClientLevel level, AABB box) {
        int minX = (int) Math.floor(box.minX);
        int maxX = (int) Math.floor(box.maxX);
        int minY = (int) Math.floor(box.minY);
        int maxY = (int) Math.floor(box.maxY);
        int minZ = (int) Math.floor(box.minZ);
        int maxZ = (int) Math.floor(box.maxZ);
        for (int bx = minX; bx <= maxX; bx++) {
            for (int by = minY; by <= maxY; by++) {
                for (int bz = minZ; bz <= maxZ; bz++) {
                    BlockPos pos = new BlockPos(bx, by, bz);
                    if (level.getFluidState(pos).is(FluidTags.LAVA)) {
                        return "目标存在危险岩浆";
                    }
                    if (level.getBlockState(pos).is(Blocks.FIRE)) {
                        return "目标存在火焰";
                    }
                }
            }
        }
        return null;
    }
}
