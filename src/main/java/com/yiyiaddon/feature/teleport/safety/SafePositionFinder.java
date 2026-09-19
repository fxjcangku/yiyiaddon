package com.yiyiaddon.feature.teleport.safety;

import com.yiyiaddon.feature.teleport.model.TeleportSubject;
import com.yiyiaddon.feature.teleport.model.TeleportTarget;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * 统一安全落点搜索器：三个传送模式共享，基于真实碰撞形状判定。
 *
 * <p>在不安全的目标中心附近按「由近及远」搜索可站立落点：
 * 坐标模式按三维距离最近优先；穿墙模式额外施加「准心射线约束」——
 * 落点与锁定视线的垂直距离不得超过允许偏离值，只做局部修正，
 * 绝不允许把玩家带到准心方向之外。</p>
 */
public final class SafePositionFinder {

    /** 搜索结果上限：防爆炸保护，候选枚举到上限即停止 */
    public static final int DEFAULT_MAX_CANDIDATES = 256;

    private SafePositionFinder() {
    }

    /**
     * 搜索参数。
     * rayOrigin/rayDir 非空时启用穿墙模式的射线偏离约束（完整三维）。
     */
    public static final class SearchParams {
        /** 目标中心（不安全需要修正的原始点） */
        public Vec3 center;
        /** 搜索半径（格，0 表示只查中心那一列） */
        public int radius;
        /** 穿墙约束：锁定视线起点（可空） */
        public Vec3 rayOrigin;
        /** 穿墙约束：锁定视线方向单位向量（可空） */
        public Vec3 rayDir;
        /** 穿墙约束：允许偏离准心的最大垂直距离（格） */
        public double maxDeviation = 3.0;
        /** 穿墙约束：候选沿射线的最短进展距离（格，默认 0 = 不限制；
         *  修正搜索用它防止把玩家传到锁定视线近侧/身侧） */
        public double minAhead = 0.0;
        /** 调试：记录已评估的候选格（可空，由渲染器复用） */
        public List<BlockPos> debugCells;
        /** 候选上限：超过即提前结束，防止长半径全量扫描卡顿 */
        public int maxCandidates = DEFAULT_MAX_CANDIDATES;
    }

    /**
     * 搜索最近安全落点。
     *
     * @return 找到的最近落点（含偏离值）；搜索范围内全部不安全则返回 null
     */
    public static TeleportTarget find(ClientLevel level, TeleportSubject subject, SearchParams p) {
        if (p == null || p.center == null || p.maxCandidates <= 0) return null;

        int cx = floor(p.center.x());
        int cy = floor(p.center.y());
        int cz = floor(p.center.z());
        int evaluated = 0;
        TeleportTarget best = null;
        double bestCost = Double.MAX_VALUE;
        // 坐标模式最近距离剪枝：当前环半径已超过已知最近落点的三维距离则不可能有更近点
        double bestCenterDist = Double.MAX_VALUE;

        for (int r = 0; r <= p.radius; r++) {
            if (best != null && !hasRayConstraint(p) && r > bestCenterDist + 1.0) break;

            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    // 只枚举当前环（切比雪夫环），内圈已在前几轮处理
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;

                    int x = cx + dx;
                    int z = cz + dz;
                    // 纵向优先从中心高度向下找（先找与目标等高或略低的支撑）
                    for (int y = cy + r; y >= cy - r; y--) {
                        if (++evaluated > p.maxCandidates) return best;

                        double feetX = x + 0.5;
                        double feetY = y;
                        double feetZ = z + 0.5;
                        Vec3 feet = new Vec3(feetX, feetY, feetZ);

                        if (p.debugCells != null && p.debugCells.size() < 128) {
                            p.debugCells.add(new BlockPos(x, y, z));
                        }

                        // 穿墙模式：候选必须落在锁定视线附近，只允许局部修正，且必须沿射线有最低进展
                        double deviation = 0.0;
                        if (hasRayConstraint(p)) {
                            deviation = perpendicularDistance(p.rayOrigin, p.rayDir, feet);
                            if (deviation > p.maxDeviation) continue;
                            double ahead = p.rayDir.x() * (feetX - p.rayOrigin.x())
                                + p.rayDir.y() * (feetY - p.rayOrigin.y())
                                + p.rayDir.z() * (feetZ - p.rayOrigin.z());
                            if (ahead < p.minAhead) continue;
                        }

                        String problem = subject.checkStand(level, feetX, feetY, feetZ);
                        if (problem != null) continue;

                        // 成本：穿墙=偏离权重+纵深前进距离；其余=到目标中心的三维距离
                        double cost;
                        if (hasRayConstraint(p)) {
                            double ahead = p.rayDir.x() * (feetX - p.rayOrigin.x())
                                + p.rayDir.y() * (feetY - p.rayOrigin.y())
                                + p.rayDir.z() * (feetZ - p.rayOrigin.z());
                            cost = deviation * 100.0 + Math.max(0.0, ahead);
                        } else {
                            double ddx = feetX - p.center.x();
                            double ddy = feetY - p.center.y();
                            double ddz = feetZ - p.center.z();
                            cost = Math.sqrt(ddx * ddx + ddy * ddy + ddz * ddz);
                        }

                        if (cost < bestCost) {
                            bestCost = cost;
                            best = new TeleportTarget(feet, new BlockPos(x, y, z), deviation);
                            if (!hasRayConstraint(p)) {
                                bestCenterDist = bestCost;
                            }
                        }
                    }
                }
            }
        }

        return best;
    }

    /** 是否有穿墙射线约束 */
    private static boolean hasRayConstraint(SearchParams p) {
        return p.rayOrigin != null && p.rayDir != null;
    }

    /** 点到射线的垂直距离（三维完整方向约束） */
    private static double perpendicularDistance(Vec3 origin, Vec3 dir, Vec3 point) {
        double px = point.x() - origin.x();
        double py = point.y() - origin.y();
        double pz = point.z() - origin.z();
        double ahead = dir.x() * px + dir.y() * py + dir.z() * pz;
        double ox = px - dir.x() * ahead;
        double oy = py - dir.y() * ahead;
        double oz = pz - dir.z() * ahead;
        return Math.sqrt(ox * ox + oy * oy + oz * oz);
    }

    private static int floor(double v) {
        return (int) Math.floor(v);
    }
}
