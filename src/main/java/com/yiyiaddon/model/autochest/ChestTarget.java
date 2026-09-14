package com.yiyiaddon.model.autochest;

import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.core.BlockPos;

/**
 * 容器目标：一次「发现到的合法容器 / 用户保存的点位」的不可变快照。
 *
 * <p>由容器扫描器产出扫描结果，或由标点管理器存为用户点位。持坐标、维度、服务器、容器类型
 * 与发现时间，供选择器与状态机消费。服务器 + 维度 + 坐标共同决定身份，跨服 / 跨维度即便
 * XYZ 相同也不视为同一容器。</p>
 */
public final class ChestTarget {

    /** 容器所在方块坐标 */
    private final BlockPos pos;

    /** 容器所在维度标识（{@code minecraft:overworld} 等） */
    private final String dimension;

    /** 服务器/世界标识；扫描结果可为空 */
    private final String server;

    /** 容器类型稳定键（如 {@code chest}）；纯坐标点位可为空 */
    private final String containerType;

    /** 发现/保存时间（毫秒，{@code System.currentTimeMillis()}） */
    private final long discoveredAt;

    public ChestTarget(BlockPos pos, String dimension) {
        this(pos, dimension, null, null);
    }

    public ChestTarget(BlockPos pos, String dimension, String server, String containerType) {
        this.pos = pos.immutable();
        this.dimension = dimension;
        this.server = server;
        this.containerType = containerType;
        this.discoveredAt = System.currentTimeMillis();
    }

    public BlockPos pos() {
        return pos;
    }

    public String dimension() {
        return dimension;
    }

    public String server() {
        return server;
    }

    public String containerType() {
        return containerType;
    }

    public long discoveredAt() {
        return discoveredAt;
    }

    /** 目标是否位于当前所在维度（判据统一走 {@link WorldIdentity#dimension()}，禁止另建第二套） */
    public boolean inCurrentDimension() {
        return dimension != null && dimension.equals(WorldIdentity.dimension());
    }

    /** 目标到给定玩家坐标的直线距离平方（用于就近排序，避免开方） */
    public double distSqr(BlockPos playerPos) {
        return pos.distSqr(playerPos);
    }
}
