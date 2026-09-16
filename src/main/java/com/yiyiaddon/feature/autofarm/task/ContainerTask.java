package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;

/**
 * 物流任务公共基类：封装「导航 → 开箱 → 同步 → 校验容器 → 转移」的公共流程。
 *
 * 子类只实现具体的 deposit / withdraw 逻辑。所有物流任务都是独占任务，
 * 执行期间 Scanner 只能观察，不能创建新任务打断。
 */
public abstract class ContainerTask implements FarmTask {

    /** 开箱包重试节流间隔（tick） */
    private static final int OPEN_RETRY_INTERVAL = 10;

    protected final BlockPos boxPos;
    protected final ContainerService broker;
    protected final double reachDistance;

    private int openRetry;

    protected ContainerTask(BlockPos boxPos, ContainerService broker, double reachDistance) {
        this.boxPos = boxPos;
        this.broker = broker;
        this.reachDistance = reachDistance;
    }

    @Override
    public final TaskResult tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return TaskResult.CONTAINER_MISSING;

        // 目标箱必须是容器
        if (!(mc.level.getBlockEntity(boxPos) instanceof Container)) {
            return TaskResult.CONTAINER_MISSING;
        }

        // 导航到箱：必须走到箱子相邻格才开箱，避免隔着数格就发包开箱导致交互失败
        if (!arrived()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing()) FarmNav.goTo(boxPos, 1);
            return TaskResult.IN_PROGRESS;
        }
        FarmNav.cancel();

        // 开箱
        if (ContainerAccess.openMenu() == null) {
            tryOpen();
            return TaskResult.IN_PROGRESS;
        }

        // 等待容器同步稳定
        if (!broker.isReady()) return TaskResult.IN_PROGRESS;

        // 目标箱已通过上方 instanceof Container 校验，且容器已打开并同步稳定，直接转移。
        // 不再做 isBoundContainer 的 BlockEntity 引用相等校验：静默容器模式下
        // menu 箱子侧容器引用与 mc.level.getBlockEntity 返回的 BlockEntity 可能不是同一实例
        // （单/双箱子均会误判 CONTAINER_OPEN_FAILED），与挖矿/自动箱子模块的判定保持一致。
        return transfer();
    }

    /** 子类实现具体的存入 / 提取逻辑 */
    protected abstract TaskResult transfer();

    @Override
    public boolean exclusive() {
        return true;
    }

    @Override
    public void cancel() {
        FarmNav.cancel();
        ContainerAccess.closeContainer();
        broker.reset();
    }

    private boolean arrived() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        // 切比雪夫邻域判定：必须站在箱子相邻格（含对角），不同时为 0
        // 与挖矿模块 ContainerHelper.openContainer 的开箱邻域判定保持一致
        int dx = Math.abs(mc.player.blockPosition().getX() - boxPos.getX());
        int dy = Math.abs(mc.player.blockPosition().getY() - boxPos.getY());
        int dz = Math.abs(mc.player.blockPosition().getZ() - boxPos.getZ());
        return dx <= 1 && dy <= 1 && dz <= 1 && (dx | dy | dz) != 0;
    }

    private void tryOpen() {
        openRetry++;
        if (openRetry < OPEN_RETRY_INTERVAL) return;
        openRetry = 0;
        broker.reset();
        BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, boxPos, Direction.UP);
    }
}
