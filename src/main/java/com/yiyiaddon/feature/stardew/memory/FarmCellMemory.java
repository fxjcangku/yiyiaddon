package com.yiyiaddon.feature.stardew.memory;

import net.minecraft.core.BlockPos;

/**
 * 单格农田的长期记忆（种植意图）。
 *
 * <p>只保存「这格应该种什么」，不保存库存 / 水量 / 掉落等动态状态。收割后不删除
 * 目标记忆；用户手动改种其他已确认作物时默认保留并报告冲突，不擅自覆盖。</p>
 *
 * <p>稳定身份不含数量、水量等动态字段，动态状态走实时观察。</p>
 */
public record FarmCellMemory(
    int x,
    int y,
    int z,
    String cropKey,   // 该格应种作物（cropKey，如 tomato）；空串表示未指定
    boolean enabled   // 该格是否启用
) {

    public BlockPos pos() {
        return new BlockPos(x, y, z);
    }

    public static FarmCellMemory empty(int x, int y, int z) {
        return new FarmCellMemory(x, y, z, "", true);
    }

    /** 该格是否有明确的种植目标 */
    public boolean hasTarget() {
        return cropKey != null && !cropKey.isBlank();
    }
}
