package com.yiyiaddon.feature.teleport.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 单次传送的上下文快照：保存触发瞬间锁定的环境、视角与决策结果，
 * 并累计本次传送的调试档案（每条传送一个独立边界，不跨次复用）。
 *
 * <p>触发瞬间锁定原则：TP穿墙的视线/射线在按键一刻快照进本对象，
 * 之后玩家移动鼠标不会改变本次传送的目标方向。</p>
 */
public final class TeleportContext {

    /** 本次传送模式 */
    public TeleportMode mode;

    /** 触发时玩家脚底坐标（距离播报的参照点） */
    public Vec3 origin;

    /** 触发时锁定维度（执行/验证期间维度变化立即中止） */
    public ResourceKey<Level> dimension;

    /** 穿墙模式：锁定视线起点（相机位置） */
    public Vec3 rayOrigin;

    /** 穿墙模式：锁定视线方向（单位向量，完整三维） */
    public Vec3 rayDir;

    /** 穿墙模式：锁定射线长度（调试渲染用） */
    public double rayLength;

    /** 决策结果：最终目标落点 */
    public TeleportTarget target;

    /** 执行后预期位置（验证基准：本体=脚底；载具=载具坐标） */
    public Vec3 expectedPos;

    /** 本次传送是否以载具整体位移方式执行 */
    public boolean executedAsVehicle;

    /** 执行时乘坐的载具（验证载具关系是否保持） */
    public Entity vehicleSnapshot;

    /** 执行时刻（tick 计数） */
    public long executeTick = -1;

    /** 验证窗口结束时刻（tick 计数） */
    public long verifyEndTick = -1;

    /** 回弹判定阈值（格） */
    public double verifyThreshold = 1.5;

    /** 验证窗口时长（tick） */
    public long verifyWindow = 8;

    /** 地面模式：本次竖直位移格数（负=向下） */
    public int stageRise;

    /** 穿墙模式：穿过的墙体层数 */
    public int wallLayers;

    /** 摔落无伤开关（玩家执行阶段据此同步重置客户端与服务端下落累计） */
    public boolean noFallDamage;

    /** 坐标模式：输入点不可站立时是否已触发安全落点搜索 */
    public boolean fallbackSearched;

    /** 决策命中「已在真正地表无需移动」等无执行场景 */
    public boolean skipExecute;

    /** 服务器回弹/修正包的目标位置（验证阶段观测到才赋值） */
    public Vec3 rubberbandPos;

    /** 服务器修正距离（格，-1 表示未观测到修正） */
    public double rubberbandDist = -1;

    /** 调试档案：安全搜索评估过的候选格（渲染用，带上限） */
    public final List<BlockPos> debugCells = new ArrayList<>();

    /** 调试渲染：本次移动对象（玩家或载具+乘客）在目标位置的实际碰撞箱（可空） */
    public List<AABB> subjectBoxes;

    /** 调试档案：单次传送的完整过程记录（有上限，防止无限膨胀） */
    public final List<String> debugLog = new ArrayList<>();

    /** 追加一条调试档案行（带上限节流，防止长任务刷爆内存） */
    public void debug(String line) {
        if (debugLog.size() < 64) debugLog.add(line);
    }
}
