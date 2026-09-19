package com.yiyiaddon.feature.librarian.integration;

import com.yiyiaddon.feature.librarian.model.BlockPosition;
import com.yiyiaddon.feature.librarian.service.DebugLoggerService;
import com.yiyiaddon.feature.librarian.service.MovementService;
import com.yiyiaddon.feature.librarian.service.MovementStartResult;
import com.yiyiaddon.feature.librarian.service.MovementStatus;
import com.yiyiaddon.platform.navigation.FarmNav;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

/**
 * 自动图书管理员 · Baritone 移动实现（第三方兼容层）。
 *
 * <p>通过 Baritone 寻路移动到目标村民附近。<b>寻路只走已有通路（禁止挖方块）</b>：
 * 旧项目在启动前把 {@code allowBreak} 置 false，本项目由
 * {@link FarmNav#goTo(BlockPos, int, boolean)} 的 {@code modifyBlocks=false} 承担同一语义。</p>
 *
 * <p><b>30 tick 宽限期</b>：Baritone 收到目标后需要若干 tick 计算路径，期间
 * {@code pathing()} 可能短暂为 false，因此启动后 1.5 秒内不判定失败 —— 否则每次移动
 * 都会被误判成「移动失败」并让状态机转入 ERROR。</p>
 *
 * <p><b>与旧实现的差异（登记项）</b>：旧项目直接持有 Baritone 实例，能区分
 * {@code LinkageError}（API 未正确加载）与普通 {@code RuntimeException} 两种启动失败并分别播报；
 * 本项目统一走 {@link FarmNav} 隔离层（第 34 条：禁止业务代码直连第三方框架），
 * 该层把异常一律收成「下发失败」，故两条成因合并为一条失败文案。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricBaritoneMovementService}（140 行）。</p>
 */
public final class BaritoneMoveService implements MovementService {
    /** Baritone 路径计算需要若干 tick，启动后给予 30 tick 的宽限期（≈1.5 秒） */
    private static final int MOVEMENT_GRACE_TICKS = 30;
    /** 单 tick 毫秒数：宽限期按真实时间折算（旧项目按 50 ms/tick 估算） */
    private static final long MILLIS_PER_TICK = 50L;

    private final DebugLoggerService logger;
    private BlockPos target;
    private int arrivalRadius;
    private boolean movementStarted;
    private long movementStartedAtMs;
    private MovementStatus status = MovementStatus.IDLE;

    public BaritoneMoveService(DebugLoggerService logger) {
        this.logger = logger;
    }

    @Override
    public MovementStartResult gotoPosition(BlockPosition position, int radiusBlocks) {
        Minecraft mc = Minecraft.getInstance();
        if (position == null || radiusBlocks < 0) return MovementStartResult.REJECTED;
        if (mc.player == null || mc.level == null) {
            status = MovementStatus.UNAVAILABLE;
            logger.error("Baritone 移动启动失败：游戏世界尚未就绪。");
            return MovementStartResult.UNAVAILABLE;
        }
        if (!FarmNav.available()) {
            status = MovementStatus.UNAVAILABLE;
            logger.error("Baritone 移动启动失败：当前没有可用的 Baritone 实例。");
            return MovementStartResult.UNAVAILABLE;
        }
        BlockPos nextTarget = new BlockPos(position.x(), position.y(), position.z());
        if (nextTarget.equals(target) && isMovementActive()) {
            return MovementStartResult.ALREADY_RUNNING;
        }
        target = nextTarget;
        arrivalRadius = radiusBlocks;
        movementStarted = true;
        movementStartedAtMs = System.currentTimeMillis();
        status = MovementStatus.STARTING;
        // modifyBlocks=false：禁止 Baritone 破坏 / 放置方块寻路，只走已有的通路
        boolean started = FarmNav.goTo(nextTarget, radiusBlocks, false);
        if (!started) {
            status = MovementStatus.FAILED;
            logger.error("Baritone 移动启动失败。");
            return MovementStartResult.FAILED;
        }
        status = MovementStatus.PATHING;
        return MovementStartResult.STARTED;
    }

    @Override
    public boolean hasArrived() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || target == null) return false;
        boolean arrived = mc.player.blockPosition().distSqr(target) <= (double) arrivalRadius * arrivalRadius;
        if (arrived) status = MovementStatus.ARRIVED;
        return arrived;
    }

    @Override
    public boolean isPathing() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        return isMovementActive();
    }

    @Override
    public MovementStatus getStatus() {
        if (hasArrived()) return MovementStatus.ARRIVED;
        if (movementStarted && status == MovementStatus.PATHING && !isMovementActive()) {
            // 宽限期内（30 tick ≈ 1.5s）Baritone 还在计算路径，不算失败
            long elapsedMs = System.currentTimeMillis() - movementStartedAtMs;
            if (elapsedMs > MOVEMENT_GRACE_TICKS * MILLIS_PER_TICK) {
                status = MovementStatus.FAILED;
            }
        }
        return status;
    }

    @Override
    public void stop() {
        if (Minecraft.getInstance().player != null) FarmNav.cancel();
        target = null;
        movementStarted = false;
        status = MovementStatus.CANCELED;
    }

    @Override
    public boolean isAvailable() {
        return Minecraft.getInstance().player != null && FarmNav.available();
    }

    /** 是否已有寻路任务在跑（含自定义目标寻路，FarmNav 内部已合并两种判定） */
    private boolean isMovementActive() {
        return FarmNav.pathing();
    }
}
