package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.model.container.ContainerTransferResult;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * 卸货任务：把背包里满足指定过滤规则的物品卸入目标箱子。
 *
 * 过滤规则由调用方（FarmDecision）传入，对应三类箱子：种子补货箱卸种子、单作物箱卸单物品收获物、多作物箱卸双物品收获物。
 * 明确区分「卸完」与「箱子满」：箱子满时返回 CONTAINER_FULL，绝不再无脑折返。
 */
public final class UnloadTask extends ContainerTask {

    private final int bpt;
    private final Predicate<ItemStack> depositFilter;

    public UnloadTask(BlockPos boxPos, ContainerService broker, double reachDistance,
                      int bpt, Predicate<ItemStack> depositFilter) {
        super(boxPos, broker, reachDistance);
        this.bpt = bpt;
        this.depositFilter = depositFilter;
    }

    @Override
    protected TaskResult transfer() {
        // 每 tick 最多搬 bpt 次。depositOne 只读本地菜单 menu.slots，
        // 与过滤判断读取的真实背包隔离，避免快速移动后本地已清空而真实背包
        // 尚未同步，导致「真实背包还有货但本地菜单已空」被误判为箱子满。
        boolean moved = false;
        ContainerTransferResult last = ContainerTransferResult.NONE;
        for (int i = 0; i < bpt; i++) {
            last = broker.depositOne(depositFilter);
            if (last == ContainerTransferResult.MOVED) {
                moved = true;
                continue;
            }
            break;
        }

        // 已发出移动 → 下一 tick 继续推进
        if (moved) return TaskResult.IN_PROGRESS;
        // 容器尚未同步稳定 → 继续等待，不关闭容器
        if (last == ContainerTransferResult.NOT_READY) return TaskResult.IN_PROGRESS;

        // 一个都搬不动：箱子满或已无货物
        broker.close();
        return last == ContainerTransferResult.CHEST_FULL ? TaskResult.CONTAINER_FULL : TaskResult.SUCCESS;
    }
}
