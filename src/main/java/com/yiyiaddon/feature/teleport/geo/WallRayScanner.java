package com.yiyiaddon.feature.teleport.geo;

import com.yiyiaddon.feature.teleport.model.TeleportSubject;
import com.yiyiaddon.feature.teleport.model.TeleportTarget;
import com.yiyiaddon.feature.teleport.safety.CollisionSafety;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * TP穿墙 ▸ 三维方向落点扫描器：沿触发瞬间锁定的真实准星射线做等距采样，
 * 在最大穿墙距离（搜索上限，非目标距离）内智能寻找「最近、能真实容纳移动对象
 * （玩家或载具+乘客）、无危险、符合视线方向」的落点。
 *
 * <p>核心原则（TP穿墙语义）：
 * <ul>
 *   <li>最大穿墙距离是「搜索上限」，不是「必须传到的目标距离」；
 *       近处合理落点优先于更远的落点；</li>
 *   <li>不要求前方必须有墙——没有墙也能前进，天然承担「方向赶路」用途；</li>
 *   <li>普通方块/门窗/半砖/楼梯/栅栏/铁栏杆等全部不是阻挡，射线直接穿过，
 *       只统计穿过的实体层数供播报，绝不提前停止；</li>
 *   <li>障碍判定基于玩家真实碰撞箱（hasSpace）而非方块整格实心，
 *       因此栅栏等空心方块也按实际形状参与「第一道障碍出口」判定；</li>
 *   <li>完整三维方向（含上下与斜向）由锁定射线自然支持；</li>
 *   <li>候选容纳判定完全基于移动对象真实碰撞箱（玩家 EntityDimensions 或
 *       载具+乘客各自 BoundingBox）与方块真实 VoxelShape（统一判据），
 *       不黑名单方块、不用 isFullCube、不要求「两个完整空气方块」；</li>
 *   <li>水不是危险物，可继续向水下搜索；岩浆/火焰由统一判据拦截；</li>
 *   <li>理想落点不可用时，外层协调整合 SafePositionFinder 做
 *       「落点修正范围」内的局部修正，而不是直接失败；</li>
 *   <li>落点允许上限内的竖直下落（maxFall + 台阶/半砖支撑）。</li>
 * </ul>
 */
public final class WallRayScanner {

    /** 射线采样步长（格）：精度与主线程开销的平衡点 */
    private static final double STEP = 0.5;

    /** 采样起点（格）：跳过玩家自身脚下的近区，避免传送回原位 */
    public static final double START = 2.0;

    /** 评分权重：方向一致（垂直偏离）远高于沿射线距离，保证近处合理落点优先 */
    private static final double DEVIATION_WEIGHT = 100.0;

    private WallRayScanner() {
    }

    /** 扫描结果 */
    public static final class Landing {
        /** 主路结果：越过第一道障碍后「方向一致优先 + 距离近者优先」的可站立落点；null 表示需要外层局部修正或失败 */
        public TeleportTarget target;
        /** 射线穿过的实体层数（无墙=0，供「穿透 N 层」播报） */
        public int layers;
        /** 射线耗尽仍被障碍占据（障碍太厚，射线没出来过） */
        public boolean exhaustedInsideSolid;
        /** 扫描中途遇到未加载区块（数据不可用，禁止继续跨未知区域） */
        public boolean reachedUnloaded;
        /** 局部修正的中心：第一道障碍出口（或无墙时搜索起点）映射的脚部坐标（永远非空） */
        public Vec3 idealFeet;
    }

    /**
     * 沿锁定射线三维方向扫描落点。
     *
     * <p>分两阶段：先沿射线定位第一道障碍（墙/栅栏/铁栏杆等占用玩家碰撞箱的
     * 结构）的出口，再从出口向后按「方向一致优先 + 距离近者优先」找第一个
     * 「完整安全判据通过」的脚部落点。无任何障碍（方向赶路）时直接从起点搜索。</p>
     *
     * @param rayOrigin  触发瞬间锁定的视线起点（相机位置）
     * @param rayDir     触发瞬间锁定的视线方向单位向量（完整三维）
     * @param eyeHeight  玩家当前眼高（把视点映射回脚部基准用）
     * @param maxDist    最大穿墙距离（格，搜索上限）
     * @param maxFall    允许自动下落到支撑的最大落差（格）
     * @param debugCells 调试候选格记录（可空，供渲染复用，带上限）
     */
    public static Landing findLanding(ClientLevel level, TeleportSubject subject,
                                      Vec3 rayOrigin, Vec3 rayDir, double eyeHeight,
                                      double maxDist, int maxFall, List<BlockPos> debugCells) {
        Landing landing = new Landing();

        // ── 第一阶段：定位第一道障碍出口，并统计障碍层数 ──
        double searchStart = START;
        boolean inObstacle = false;
        boolean anyObstacle = false;
        boolean exitedFirst = false;
        int obstacleRuns = 0;

        for (double t = START; t <= maxDist; t += STEP) {
            Vec3 p = rayOrigin.add(rayDir.scale(t));
            double baseY = p.y() - eyeHeight;

            // 数据有效性：未知区块一律终止，绝不把未知区域当穿透后的出口
            if (!CollisionSafety.areaLoaded(level, p.x(), p.z())) {
                landing.reachedUnloaded = true;
                break;
            }

            // 障碍判定：以玩家真实碰撞箱（脚底基准）在该点是否被方块占据为准。
            // 栅栏/铁栏杆等空心方块因占用碰撞箱同样判为障碍，普通方块不提前阻挡，
            // 水（无碰撞形状）不判为障碍，可继续向水下搜索。
            boolean blocked = !subject.hasSpace(level, p.x(), baseY, p.z());
            if (blocked && !inObstacle) {
                obstacleRuns++;
                anyObstacle = true;
            } else if (!blocked && inObstacle && !exitedFirst) {
                // 第一次离开障碍：记录第一道墙/栅栏的出口作为候选搜索起点
                searchStart = t;
                exitedFirst = true;
            }
            inObstacle = blocked;
        }

        landing.layers = anyObstacle ? obstacleRuns : 0;
        landing.exhaustedInsideSolid = !landing.reachedUnloaded && inObstacle;

        // 局部修正中心：第一道障碍出口（或无墙时搜索起点）的脚部映射
        Vec3 exitPoint = rayOrigin.add(rayDir.scale(searchStart));
        landing.idealFeet = new Vec3(exitPoint.x(), exitPoint.y() - eyeHeight, exitPoint.z());

        // ── 第二阶段：从出口（或起点）向后，按「方向一致优先 + 距离近者优先」找落点 ──
        TeleportTarget best = null;
        double bestScore = Double.MAX_VALUE;

        for (double t = searchStart; t <= maxDist; t += STEP) {
            Vec3 p = rayOrigin.add(rayDir.scale(t));
            double baseY = p.y() - eyeHeight;

            if (!CollisionSafety.areaLoaded(level, p.x(), p.z())) {
                landing.reachedUnloaded = true;
                break;
            }

            for (int d = 0; d <= maxFall; d++) {
                double y = baseY - d;
                if (y < level.getMinY()) break;

                // 完整判据：区块可用 + 真实碰撞容纳 + 支撑 + 无岩浆/火焰 + 不悬空
                if (subject.checkStand(level, p.x(), y, p.z()) != null) continue;

                if (debugCells != null && debugCells.size() < 128) {
                    debugCells.add(new BlockPos(floor(p.x()), floor(y), floor(p.z())));
                }

                Vec3 feet = new Vec3(p.x(), y, p.z());
                double deviation = perpendicularDistance(rayOrigin, rayDir, feet);
                double ahead = rayDir.x() * (feet.x() - rayOrigin.x())
                    + rayDir.y() * (feet.y() - rayOrigin.y())
                    + rayDir.z() * (feet.z() - rayOrigin.z());
                // 评分：方向一致（垂直偏离）权重高，其次按沿射线进展距离近者优先
                double score = deviation * DEVIATION_WEIGHT + Math.max(0.0, ahead);
                if (score < bestScore) {
                    bestScore = score;
                    best = new TeleportTarget(feet, new BlockPos(floor(p.x()), floor(y), floor(p.z())), deviation);
                }
                break;
            }
        }

        landing.target = best;
        return landing;
    }

    /** 点到射线的垂直距离（三维） */
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
