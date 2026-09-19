package com.yiyiaddon.feature.teleport.geo;

import com.yiyiaddon.feature.teleport.model.TeleportSubject;
import com.yiyiaddon.feature.teleport.model.TeleportTarget;
import com.yiyiaddon.feature.teleport.safety.CollisionSafety;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * TP地面 ▸ 地表扫描器：以玩家脚底为起点双向扫描竖直列。
 *
 * <p>向下：寻找「开天 + 可站立」的最近落点（空中/浮空时落回地面）；
 * 向上：逐阶段寻找最近的合理阶段空间（矿洞/空腔/真正地表），
 * 一次按键只上升一个阶段，不直接跳到最高地表。</p>
 *
 * <p>「开天」判定 = 头顶直通天空（LevelReader.canSeeSky），用于识别真正
 * 露天地表与「已在真正地表」；阶段目标判定复用统一安全判据与玩家碰撞箱。</p>
 */
public final class SurfaceScanner {

    private SurfaceScanner() {
    }

    /** 扫描结果 */
    public static final class Result {
        /** 目标落点；null 表示未找到 */
        public final TeleportTarget target;
        /** 相对起点的竖直位移（负=向下，0=站在原地） */
        public final int rise;
        /** target 为空时的失败原因（中文） */
        public final String failReason;

        Result(TeleportTarget target, int rise, String failReason) {
            this.target = target;
            this.rise = rise;
            this.failReason = failReason;
        }
    }

    /**
     * 双向扫描真正地表。
     *
     * @param feet    当前脚底坐标（主张 x/z 中心由调用方保证）
     * @param maxRise 向上扫描的最大格数
     */
    public static Result scan(ClientLevel level, TeleportSubject subject, Vec3 feet, int maxRise) {
        int fx = (int) Math.floor(feet.x());
        int fz = (int) Math.floor(feet.z());
        int startY = (int) Math.floor(feet.y());

        if (!CollisionSafety.areaLoaded(level, feet.x(), feet.z())) {
            return new Result(null, 0, "脚下区块数据未加载");
        }

        // 当前位置本身就是开天地表：无需移动
        if (passes(level, subject, fx, startY, fz)) {
            return new Result(at(fx, startY, fz), 0, null);
        }

        // 优先向下：浮空/高处时落回最近地面
        for (int y = startY - 1; y >= level.getMinY(); y--) {
            if (passes(level, subject, fx, y, fz)) {
                return new Result(at(fx, y, fz), y - startY, null);
            }
        }

        // 再向上：逐阶段寻找最近的合理空间（矿洞/空腔/真正地表），一次按键只上升一个阶段
        int top = Math.min(startY + maxRise, level.getMaxY() - 1);
        int stage = findStageAbove(level, subject, fx, fz, startY, top);
        if (stage >= 0) {
            return new Result(at(fx, stage, fz), stage - startY, null);
        }

        return new Result(null, 0, "上方未找到可站立阶段空间（可增大地表扫描上限）");
    }

    /**
     * 向上逐阶段搜索：定位当前空腔天花板，穿过实体层后，返回上方最近的可站立
     * 阶段地板（矿洞/空腔/真正地表）。阶段判定完全基于移动对象真实碰撞箱（hasSpace）
     * 与统一安全判据（checkStand），不把「开天 canSeeSky」当作阶段目标判据，
     * 因此中间矿洞/地下空腔也能作为阶段目标，只有真正露天表面才是最终地表。
     *
     * @return 阶段脚底 y 坐标；未找到返回 -1
     */
    private static int findStageAbove(ClientLevel level, TeleportSubject subject,
                                      int x, int z, int startY, int top) {
        // 1. 跳过当前空腔的开放空间，定位头顶第一个移动对象碰撞箱无法容纳的位置（天花板）
        int y = startY + 1;
        while (y <= top && subject.hasSpace(level, x + 0.5, y, z + 0.5)) {
            y++;
        }
        // 2. 穿过实体层，直到重新遇到可站立地板（checkStand 同时校验空间与支撑）
        for (; y <= top; y++) {
            if (subject.checkStand(level, x + 0.5, y, z + 0.5) == null) {
                return y;
            }
        }
        return -1;
    }

    /** 某格是否「开天 + 可安全站立」 */
    private static boolean passes(ClientLevel level, TeleportSubject subject, int x, int y, int z) {
        return level.canSeeSky(new BlockPos(x, y, z))
            && subject.checkStand(level, x + 0.5, y, z + 0.5) == null;
    }

    /** 构造格中心落点 */
    private static TeleportTarget at(int x, int y, int z) {
        return new TeleportTarget(new Vec3(x + 0.5, y, z + 0.5), new BlockPos(x, y, z), 0.0);
    }
}
