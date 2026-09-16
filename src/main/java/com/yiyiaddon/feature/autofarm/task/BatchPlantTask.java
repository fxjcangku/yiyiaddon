package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量瞬间补种任务：同一作物种子的可达空耕地在同一 tick 全部播下。
 *
 * 与旧的「锁定多个目标后严格串行逐个补种」不同，本任务先把当前作物种子准备到副手，
 * 然后在一帧内向所有可达且同种子的空耕地连发播种包，实现「瞬间补满」；验证失败的有限重试。
 */
public final class BatchPlantTask implements FarmTask {

    /** 发播种包后等待服务端确认的 tick 数 */
    private static final int WAIT_TICKS = 2;
    /** 验证失败后的最大重试次数 */
    private static final int MAX_RETRIES = 2;

    private final FarmVerifier verifier;
    private final double reachDistance;

    private final List<FarmTarget> remaining = new ArrayList<>();
    private final List<FarmTarget> planted = new ArrayList<>();
    private final List<FarmTarget> pending = new ArrayList<>();

    private boolean acted;
    private int waitTicks;
    private int retries;
    /** 是否已发起背包种子移动、等待副手同步 */
    private boolean itemPreparing;
    private int itemPrepTicks;

    public BatchPlantTask(List<FarmTarget> targets, FarmVerifier verifier, double reachDistance) {
        this.verifier = verifier;
        this.reachDistance = reachDistance;
        this.remaining.addAll(targets);
    }

    /** 已成功补种的目标清单，供 Controller 失效缓存 */
    public List<FarmTarget> planted() {
        return planted;
    }

    @Override
    public TaskResult tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return TaskResult.IN_PROGRESS;

        // 刚播种的一波等待服务端确认
        if (acted) {
            if (waitTicks < WAIT_TICKS) {
                waitTicks++;
                return TaskResult.IN_PROGRESS;
            }
            TaskResult result = flushPending();
            if (result.done()) return result;
        }

        // 过滤已失效目标（底盘被占用/变化）
        remaining.removeIf(t -> !verifier.targetStillValid(t));
        if (remaining.isEmpty()) {
            return pending.isEmpty() ? TaskResult.SUCCESS : flushPending();
        }

        // 以最近空耕地确定当前作物种子
        FarmTarget nearest = nearest();
        Item item = nearest.profile().plantItem();

        // 找出同种子且当前可达的所有空耕地
        List<FarmTarget> batch = new ArrayList<>();
        for (FarmTarget t : remaining) {
            if (t.profile().plantItem() == item && distSq(t.pos()) <= reachDistance * reachDistance) {
                batch.add(t);
            }
        }

        if (batch.isEmpty()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing()) FarmNav.goTo(nearest.pos(), 1);
            return TaskResult.IN_PROGRESS;
        }

        FarmNav.cancel();

        // 准备该作物种子到副手
        if (!ensureOffhand(item)) return TaskResult.IN_PROGRESS;

        // 瞬间补种这一批
        for (FarmTarget t : batch) {
            BlockPacketSender.useOnBlock(InteractionHand.OFF_HAND, t.pos());
            remaining.remove(t);
            pending.add(t);
        }
        acted = true;
        waitTicks = 0;
        return TaskResult.IN_PROGRESS;
    }

    /** 验证刚播种的一批，失败的有限重试 */
    private TaskResult flushPending() {
        List<FarmTarget> failed = new ArrayList<>();
        for (FarmTarget t : pending) {
            if (verifier.plantSucceeded(t)) planted.add(t);
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
        return TaskResult.IN_PROGRESS;
    }

    /** 确保副手持有目标种子；返回 false 表示刚发起背包移动、需等待同步 */
    private boolean ensureOffhand(Item item) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getOffhandItem().is(item)) {
            itemPreparing = false;
            itemPrepTicks = 0;
            return true;
        }

        int hotbar = InventoryOps.findInHotbar(stack -> stack.is(item));
        if (hotbar != InventoryOps.NOT_FOUND) {
            InventoryOps.moveToOffhand(hotbar);
            itemPreparing = false;
            itemPrepTicks = 0;
            return true;
        }

        int inv = InventoryOps.find(stack -> stack.is(item));
        if (inv != InventoryOps.NOT_FOUND) {
            itemPrepTicks++;
            if (!itemPreparing || itemPrepTicks > 8) {
                InventoryOps.moveToOffhand(inv);
                itemPreparing = true;
                itemPrepTicks = 0;
            }
            return false;
        }

        itemPreparing = false;
        itemPrepTicks = 0;
        return true; // 无种子：发空包后由验证失败自然放弃
    }

    @Override
    public boolean exclusive() {
        return false;
    }

    @Override
    public void cancel() {
        FarmNav.cancel();
    }

    private FarmTarget nearest() {
        FarmTarget best = null;
        double bestSq = Double.MAX_VALUE;
        for (FarmTarget t : remaining) {
            double d = distSq(t.pos());
            if (d < bestSq) {
                bestSq = d;
                best = t;
            }
        }
        return best;
    }

    private double distSq(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() + 0.5 - mc.player.getEyeY();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
