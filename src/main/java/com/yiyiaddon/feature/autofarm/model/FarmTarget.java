package com.yiyiaddon.feature.autofarm.model;

import net.minecraft.core.BlockPos;

/**
 * 扫描器识别出的单个作业目标。
 *
 * 每次只处理一个具体目标：收割目标是已成熟的作物方块；补种目标是底盘正确、上方为空的种植位。
 */
public record FarmTarget(TargetType type, CropProfile profile, BlockPos pos) {

    /** 目标类型 */
    public enum TargetType {
        /** 已成熟、可收割的作物 */
        HARVEST,
        /** 待补种的空地（pos 为底盘坐标，作物种在其上方） */
        PLANT,
        /** 待锄地的草方块/泥土（pos 为方块本体坐标） */
        TILL
    }

    /** 收割目标定位到作物方块本体；补种目标定位到底盘坐标 */
    public static FarmTarget harvest(CropProfile profile, BlockPos pos) {
        return new FarmTarget(TargetType.HARVEST, profile, pos);
    }

    public static FarmTarget plant(CropProfile profile, BlockPos soilPos) {
        return new FarmTarget(TargetType.PLANT, profile, soilPos);
    }

    /** 锄地目标定位到草方块/泥土本体坐标，无需作物图鉴 */
    public static FarmTarget till(BlockPos pos) {
        return new FarmTarget(TargetType.TILL, null, pos);
    }
}
