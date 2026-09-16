package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.HoeItem;

/**
 * 锄地任务：前往待开垦草方块/泥土 → 准备锄头 → 右键锄成耕地 → 等待更新 → 验证。
 *
 * 锄地必须手持锄头（主手），副手锄头无法触发原版 useOn。
 * 背包没有锄头时返回 RESOURCE_INSUFFICIENT，由 Controller 跳过并继续状态机。
 */
public final class TillTask implements FarmTask {

    /** 发锄地包后等待服务端确认的 tick 数（批量快速锄地下调） */
    private static final int WAIT_TICKS = 2;
    private static final int MAX_RETRIES = 2;

    private final FarmTarget target;
    private final FarmVerifier verifier;
    private final double reachDistance;

    private InteractionHand hand;
    private boolean acted;
    private boolean swapped;
    /** 切换快捷栏前的原选中槽，锄完恢复 */
    private int previousSelected = -1;
    private int waitTicks;
    private int retries;
    /** 已发起背包锄头移动、等待主手同步 */
    private boolean preparing;
    /** 等待主手同步的 tick 计数，超时重发移动防卡死 */
    private int preparingTicks;

    public TillTask(FarmTarget target, FarmVerifier verifier, double reachDistance) {
        this.target = target;
        this.verifier = verifier;
        this.reachDistance = reachDistance;
    }

    /** 当前锄地目标 */
    public FarmTarget target() {
        return target;
    }

    @Override
    public TaskResult tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return TaskResult.TARGET_INVALID;

        // 执行前校验：只在尚未锄地时检查目标是否仍可开垦
        if (!acted && !verifier.targetStillValid(target)) return TaskResult.TARGET_INVALID;

        // 距离不够则导航
        if (!inReach()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing()) FarmNav.goTo(target.pos(), 1);
            return TaskResult.IN_PROGRESS;
        }
        FarmNav.cancel();

        // 准备锄头（必须主手）
        if (hand == null) {
            hand = prepareHoe();
            if (hand == null) {
                // 背包锄头正在移动到主手：等待同步，本 tick 不发包
                return preparing ? TaskResult.IN_PROGRESS : TaskResult.RESOURCE_INSUFFICIENT;
            }
        }

        // 发锄地包
        if (!acted) {
            BlockPacketSender.tillBlock(hand, target.pos());
            acted = true;
            waitTicks = 0;
            return TaskResult.IN_PROGRESS;
        }

        // 等待世界状态更新
        if (waitTicks < WAIT_TICKS) {
            waitTicks++;
            return TaskResult.IN_PROGRESS;
        }

        // 验证锄地结果
        if (verifier.tillSucceeded(target)) return TaskResult.SUCCESS;

        if (retries < MAX_RETRIES) {
            retries++;
            acted = false;
            waitTicks = 0;
            return TaskResult.IN_PROGRESS;
        }
        return TaskResult.PLANT_FAILED;
    }

    @Override
    public boolean exclusive() {
        return false;
    }

    @Override
    public void cancel() {
        FarmNav.cancel();
        if (swapped) {
            InventoryOps.selectHotbar(previousSelected);
            swapped = false;
        }
    }

    /** 锄头准备：主手已是锄头直接用，否则从快捷栏切换或从背包移到主手并等待同步 */
    private InteractionHand prepareHoe() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;

        // 主手已是锄头
        if (mc.player.getMainHandItem().getItem() instanceof HoeItem) {
            preparing = false;
            preparingTicks = 0;
            return InteractionHand.MAIN_HAND;
        }

        // 快捷栏有锄头：直接切换到主手（客户端即时生效），锄完恢复原选中槽
        int hotbar = InventoryOps.findInHotbar(stack -> stack.getItem() instanceof HoeItem);
        if (hotbar != InventoryOps.NOT_FOUND) {
            previousSelected = mc.player.getInventory().getSelectedSlot();
            InventoryOps.selectHotbar(hotbar);
            swapped = true;
            preparing = false;
            preparingTicks = 0;
            return InteractionHand.MAIN_HAND;
        }

        // 全背包找锄头（含主背包/副手），移到主手；move 是异步移动，需等待主手同步再锄
        int inventory = InventoryOps.find(stack -> stack.getItem() instanceof HoeItem);
        if (inventory != InventoryOps.NOT_FOUND) {
            preparingTicks++;
            // 首次发起，或等待超时后重新发起移动（防移动失败后无限等待）
            if (!preparing || preparingTicks > 8) {
                InventoryOps.moveToSelectedHotbar(inventory);
                preparing = true;
                preparingTicks = 0;
            }
            return null; // 等待主手同步
        }

        preparing = false;
        preparingTicks = 0;
        return null; // 背包确实没有锄头
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
