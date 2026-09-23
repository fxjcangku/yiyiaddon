package com.yiyiaddon.seed.observation;

import com.yiyiaddon.seed.model.OreType;
import java.util.Objects;
import net.minecraft.core.BlockPos;

/**
 * 种子挖矿正式模块 · 一条「服务器观察记录」。
 *
 * <p><b>本阶段只建数据载体</b>，不接服务器（正式化第一阶段口径第二十九、三十节）：
 * 没有 {@code Chunk} 包钩子、没有方块状态校验、没有假矿检测、没有 AutoMiner 观察器。
 * 它的作用是把「以后要拿什么跟预测比」这件事先固定下来，避免第二阶段再回头改预测侧的数据模型。</p>
 *
 * @param position       被观察的位置
 * @param oreType        预测器认为这里应该是哪种矿
 * @param state          服务器观察状态
 * @param observedBlockId 服务器给客户端的真实方块注册名；未观察到时为 null（不用空串冒充）
 */
public record OreObservation(BlockPos position, OreType oreType, OreObservationState state, String observedBlockId) {

    public OreObservation {
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(state, "state");
    }

    /** 尚未观察到的位置。 */
    public static OreObservation unobserved(BlockPos position, OreType oreType) {
        return new OreObservation(position, oreType, OreObservationState.UNOBSERVED, null);
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "(" + position.getX() + "," + position.getY() + "," + position.getZ() + ") "
                + oreType.displayNameCn() + " → " + state.displayNameCn()
                + (observedBlockId == null ? "（尚未拿到真实方块）" : "（服务器实际 " + observedBlockId + "）");
    }
}
