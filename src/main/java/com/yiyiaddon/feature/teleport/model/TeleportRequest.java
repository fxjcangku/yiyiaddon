package com.yiyiaddon.feature.teleport.model;

import net.minecraft.world.phys.Vec3;

/**
 * 单次传送请求参数包：模块层从设置面板取值打包，协调器只消费本包，
 * 不反向依赖任何模块/设置对象，保证决策层与 UI 层完全解耦。
 */
public final class TeleportRequest {

    /** 触发模式 */
    public TeleportMode mode = TeleportMode.GROUND;

    /** 坐标模式：目标脚底坐标（可空，仅 COORD 使用） */
    public Vec3 coord;

    /** 穿墙模式：触发瞬间锁定的视线起点（可空，仅 WALL 使用） */
    public Vec3 rayOrigin;

    /** 穿墙模式：触发瞬间锁定的视线方向单位向量（可空，仅 WALL 使用） */
    public Vec3 rayDir;

    /** 穿墙模式：触发瞬间玩家眼高（视点映射脚部基准用，仅 WALL 使用） */
    public double eyeHeight = 1.62;

    /** 地面模式：向上扫描最大格数 */
    public int maxRise = 200;

    /** 穿墙模式：视线射线最大距离（格） */
    public double maxDistance = 16.0;

    /** 穿墙模式：落点允许偏离准心的最大垂直距离（格） */
    public double maxDeviation = 3.0;

    /** 穿墙模式：允许自动下落到支撑的最大落差（格） */
    public int maxFall = 6;

    /** 摔落无伤开关（玩家执行时通过服务端上移重置路径清空下落累计） */
    public boolean noFallDamage = false;

    /** 坐标模式：目标不可站立时的邻近回退搜索半径（格） */
    public int fallbackRadius = 4;

    /** 回弹判定阈值（格）：服务端权威位置与预期位置距离小于等于该值视为接受 */
    public double verifyThreshold = 1.5;

    /** 验证窗口（tick）：超时未收到回弹包即视为服务端接受 */
    public long verifyWindow = 8;
}
