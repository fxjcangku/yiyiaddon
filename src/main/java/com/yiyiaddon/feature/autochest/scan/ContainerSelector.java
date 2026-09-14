package com.yiyiaddon.feature.autochest.scan;

import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.repository.autochest.ContainerRecordStore;
import net.minecraft.core.BlockPos;

import java.util.Comparator;
import java.util.List;

/**
 * 容器选择器：从扫描候选 / 标点候选中选出「下一个要处理的容器」。
 *
 * <p>逐字复刻旧项目 {@code autochest/scan/ContainerSelector.java} 的选择规则：</p>
 * <ol>
 *   <li>过滤掉非当前维度的容器（维度隔离）。</li>
 *   <li>过滤掉已处理的容器（{@link ContainerRecordStore}，含容器类型比对）。</li>
 *   <li>按到玩家的直线距离升序，取最近的一个。</li>
 * </ol>
 *
 * <p>旧项目为实例方法且需要调用方传入当前维度；本项目要求静态入口，维度改由候选自身携带
 * （能通过 {@link ChestTarget#inCurrentDimension()} 的候选，其维度必等于当前维度，判据等价）。</p>
 */
public final class ContainerSelector {

    private ContainerSelector() {
        // 工具类，禁止实例化
    }

    /**
     * 从候选中选出最优目标。
     *
     * @param candidates 候选容器列表
     * @param playerPos  玩家当前坐标
     * @param records    已处理记录存储
     * @param expireMs   已处理记录的过期时长（毫秒）
     * @return 最优目标；无可选返回 null
     */
    public static ChestTarget select(List<ChestTarget> candidates, BlockPos playerPos,
                                     ContainerRecordStore records, long expireMs) {
        if (candidates == null || candidates.isEmpty()) return null;

        return candidates.stream()
            .filter(ChestTarget::inCurrentDimension)
            .filter(c -> !records.isProcessed(c.pos(), c.dimension(), c.containerType(), expireMs))
            .min(Comparator.comparingDouble(c -> c.distSqr(playerPos)))
            .orElse(null);
    }
}
