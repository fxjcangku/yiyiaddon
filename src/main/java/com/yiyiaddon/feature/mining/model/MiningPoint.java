package com.yiyiaddon.feature.mining.model;

import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.core.BlockPos;

/**
 * 一条点位绑定记录（旧项目 {@code WKCommand.WKData}，{@code :723-762}）。
 *
 * <p>坐标 + 维度 + 视角三件套逐字对应旧项目 JSON 的 {@code x / y / z / dimension / yaw / pitch}；
 * 视角只有挂机修复点会用（用于精准对准修补工作台），矿物箱 / 食物箱写入 0。</p>
 */
public record MiningPoint(int x, int y, int z, String dimension, float yaw, float pitch) {

    /** 方块坐标 */
    public BlockPos pos() {
        return new BlockPos(x, y, z);
    }

    /**
     * 点位是否位于当前所在维度。
     *
     * <p>与旧项目 {@code WKData.inCurrentDimension()} 等价：当前维度标识由
     * {@link WorldIdentity#dimension()} 给出（{@code identifier().toString()}），未进入世界返回
     * 空串时一律视为「不在当前维度」。</p>
     */
    public boolean inCurrentDimension() {
        String current = WorldIdentity.dimension();
        return !current.isEmpty() && current.equals(dimension);
    }
}
