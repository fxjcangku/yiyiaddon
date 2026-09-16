package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量瞬间收割任务：一次把可达范围内的多个成熟目标在同一 tick 全部破坏。
 *
 * 与旧的「锁定多个目标后严格串行逐个破坏」不同，本任务在一帧内向所有可达目标
 * 连发破坏包，实现「瞬间破坏附近一片」；破坏完成后统一验证，失败的有限重试，
 * 全部结束后把成功清单交给 Controller 统一补种与拾取。
 */
public final class BatchHarvestTask implements FarmTask {

    /** 发破坏包后等待服务端确认的 tick 数 */
    private static final int WAIT_TICKS = 2;
    /** 验证失败后的最大重试次数 */
    private static final int MAX_RETRIES = 2;
    /** 每波最多破坏的目标数（分波交错：收一小批→补种→拾取→下一批，避免掉落物堆积与补种延迟） */
    private static final int WAVE_SIZE = 32;

    private final FarmVerifier verifier;
    private final double reachDistance;

    private final List<FarmTarget> remaining = new ArrayList<>();
    private final List<FarmTarget> harvested = new ArrayList<>();
    private final List<FarmTarget> pending = new ArrayList<>();

    private boolean acted;
    private int waitTicks;
    private int retries;

    /** 时运工具准备器，破坏前先把最高时运工具换到主手 */
    private final FortuneTool fortune = new FortuneTool();
    /** 时运工具是否已就绪（主手已有时运或确认无时运工具） */
    private boolean toolPrepared;

    public BatchHarvestTask(List<FarmTarget> targets, FarmVerifier verifier, double reachDistance) {
        this.verifier = verifier;
        this.reachDistance = reachDistance;
        this.remaining.addAll(targets);
    }

    /** 取出并清空本波已成功收割的目标，供 Controller 补种与拾取（分波时每次取增量） */
    public List<FarmTarget> drainHarvested() {
        List<FarmTarget> out = new ArrayList<>(harvested);
        harvested.clear();
        return out;
    }

    @Override
    public TaskResult tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return TaskResult.IN_PROGRESS;

        // 刚破坏的一波等待服务端确认
        if (acted) {
            if (waitTicks < WAIT_TICKS) {
                waitTicks++;
                return TaskResult.IN_PROGRESS;
            }
            TaskResult result = flushPending();
            if (result.done()) return result;
        }

        // 过滤已失效目标
        remaining.removeIf(t -> !verifier.targetStillValid(t));

        if (remaining.isEmpty()) {
            return pending.isEmpty() ? TaskResult.SUCCESS : flushPending();
        }

        // 找出当前可达的成熟目标，以及最近的导航目标
        List<FarmTarget> inReach = new ArrayList<>();
        FarmTarget nearest = null;
        double nearestSq = Double.MAX_VALUE;
        for (FarmTarget t : remaining) {
            double d = distSq(t.pos());
            if (d < nearestSq) {
                nearestSq = d;
                nearest = t;
            }
            if (d <= reachDistance * reachDistance) inReach.add(t);
        }

        if (inReach.isEmpty()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing() && nearest != null) FarmNav.goTo(nearest.pos(), 1);
            return TaskResult.IN_PROGRESS;
        }

        FarmNav.cancel();

        // 优先装备时运工具，未就绪则等待
        if (!toolPrepared) {
            toolPrepared = fortune.ensure();
            if (!toolPrepared) return TaskResult.IN_PROGRESS;
        }

        // 瞬间破坏一批可达目标（每波限量，实现边收边补边捡，避免掉落物堆积与补种延迟）
        int budget = WAVE_SIZE;
        for (FarmTarget t : inReach) {
            if (budget-- <= 0) break;
            BlockPacketSender.breakBlock(t.pos(), Direction.UP);
            remaining.remove(t);
            pending.add(t);
        }
        acted = true;
        waitTicks = 0;
        return TaskResult.IN_PROGRESS;
    }

    /** 验证刚破坏的一波目标，失败的有限重试，全部处理完返回 SUCCESS */
    private TaskResult flushPending() {
        List<FarmTarget> failed = new ArrayList<>();
        for (FarmTarget t : pending) {
            if (verifier.harvestSucceeded(t)) harvested.add(t);
            else failed.add(t);
        }
        pending.clear();
        acted = false;
        waitTicks = 0;

        if (!failed.isEmpty() && retries < MAX_RETRIES) {
            retries++;
            remaining.addAll(failed);
        }

        if (remaining.isEmpty()) {
            retries = 0;
            return TaskResult.SUCCESS;
        }
        // 分波交错：本波已收完且仍有剩余，返回 WAVE_DONE 让 Controller 先补种+拾取，
        // 再恢复本任务继续下一波，实现「边收边补边捡」。
        return TaskResult.WAVE_DONE;
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

    private double distSq(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() + 0.5 - mc.player.getEyeY();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
