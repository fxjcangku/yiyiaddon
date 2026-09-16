package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;

/**
 * 收割任务：前往单个成熟目标 → 优先装备时运工具 → 发破坏包 → 等待世界更新 → 验证结果。
 *
 * 不再以「breakBlock() 返回 true」作为成功依据，必须读到世界状态真正变化才算成功。
 * 破坏前会优先把最高时运附魔的工具换到主手，让胡萝卜/马铃薯/下界疣等作物吃满时运加成。
 */
public final class HarvestTask implements FarmTask {

    /** 发破坏包后等待服务端回滚/确认的 tick 数 */
    private static final int WAIT_TICKS = 2;
    /** 验证失败后的最大重试次数 */
    private static final int MAX_RETRIES = 2;

    private final FarmTarget target;
    private final FarmVerifier verifier;
    private final double reachDistance;

    private boolean acted;
    private int waitTicks;
    private int retries;

    /** 时运工具准备器，负责把最高时运工具换到主手 */
    private final FortuneTool fortune = new FortuneTool();
    /** 时运工具是否已就绪（主手已有时运或确认无时运工具） */
    private boolean toolPrepared;

    public HarvestTask(FarmTarget target, FarmVerifier verifier, double reachDistance) {
        this.target = target;
        this.verifier = verifier;
        this.reachDistance = reachDistance;
    }

    /** 当前收割目标，供 Controller 在收割完成后衔接补种/拾取 */
    public FarmTarget target() {
        return target;
    }

    @Override
    public TaskResult tick() {
        // 执行前校验：只在尚未破坏方块时检查目标是否仍成熟。
        // breakBlock 会本地立即置空气，若每 tick 都重新校验，下一 tick 会把
        // 已置空气的目标误判为 TARGET_INVALID，导致收割成功却无法衔接补种/拾取。
        if (!acted && !verifier.targetStillValid(target)) return TaskResult.TARGET_INVALID;

        // 距离不够则导航
        if (!inReach()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing()) FarmNav.goTo(target.pos(), 1);
            return TaskResult.IN_PROGRESS;
        }
        FarmNav.cancel();

        // 优先装备时运工具，未就绪则等待
        if (!acted && !toolPrepared) {
            toolPrepared = fortune.ensure();
            if (!toolPrepared) return TaskResult.IN_PROGRESS;
        }

        // 发破坏包
        if (!acted) {
            BlockPacketSender.breakBlock(target.pos(), Direction.UP);
            acted = true;
            waitTicks = 0;
            return TaskResult.IN_PROGRESS;
        }

        // 等待世界状态更新（让服务端有机会回滚超距/被拒绝的破坏）
        if (waitTicks < WAIT_TICKS) {
            waitTicks++;
            return TaskResult.IN_PROGRESS;
        }

        // 验证世界状态
        if (verifier.harvestSucceeded(target)) return TaskResult.SUCCESS;

        // 验证失败：重试有限次，超限返回明确失败
        if (retries < MAX_RETRIES) {
            retries++;
            acted = false;
            waitTicks = 0;
            return TaskResult.IN_PROGRESS;
        }
        return TaskResult.HARVEST_FAILED;
    }

    @Override
    public boolean exclusive() {
        return false;
    }

    @Override
    public void cancel() {
        FarmNav.cancel();
        fortune.restore();
    }

    private boolean inReach() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        double dx = target.pos().getX() + 0.5 - mc.player.getX();
        double dy = target.pos().getY() + 0.5 - mc.player.getEyeY();
        double dz = target.pos().getZ() + 0.5 - mc.player.getZ();
        return dx * dx + dy * dy + dz * dz <= reachDistance * reachDistance;
    }
}
