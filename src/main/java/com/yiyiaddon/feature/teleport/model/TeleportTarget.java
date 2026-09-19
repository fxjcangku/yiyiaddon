package com.yiyiaddon.feature.teleport.model;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;

/**
 * 传送目标落点：脚底坐标 + 站位格 + 与锁定视线的垂直偏离（仅穿墙模式非零）。
 * feet 为脚底中心（玩家碰撞箱左下角放于该点时可安全站立）。
 */
public record TeleportTarget(Vec3 feet, BlockPos standPos, double deviation) {

    /** 播报用的坐标文本（x, y, z），取整显示：方块定位按格计算，小数无意义且刷屏 */
    public String posText() {
        return String.format(Locale.ROOT, "%.0f, %.0f, %.0f", feet.x(), feet.y(), feet.z());
    }
}
