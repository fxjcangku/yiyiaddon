package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;

/**
 * 补种任务：前往待补种底盘 → 准备种植材料 → 播种 → 等待更新 → 验证。
 *
 * 种植材料搜索顺序：副手 → 主手 → 快捷栏 → 主背包。
 * 材料不足时不假装成功，返回 RESOURCE_INSUFFICIENT 交由资源检查处理。
 */
public final class PlantTask implements FarmTask {

    /** 发播种包后等待服务端确认的 tick 数（批量快速补种下调，失败由重试兜底） */
    private static final int WAIT_TICKS = 2;
    private static final int MAX_RETRIES = 2;

    private final FarmTarget target;
    private final FarmVerifier verifier;
    private final double reachDistance;

    private InteractionHand hand;
    private boolean acted;
    private int waitTicks;
    private int retries;

    public PlantTask(FarmTarget target, FarmVerifier verifier, double reachDistance) {
        this.target = target;
        this.verifier = verifier;
        this.reachDistance = reachDistance;
    }

    /** 当前补种目标，供 Controller 在补种完成后衔接拾取 */
    public FarmTarget target() {
        return target;
    }

    @Override
    public TaskResult tick() {
        // 目标底盘不再可种（被占用/底盘变化）→ 重新观察
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return TaskResult.TARGET_INVALID;
        if (!target.profile().isPlantable(mc.level, target.pos())) return TaskResult.TARGET_INVALID;

        // 距离不够则导航
        if (!inReach()) {
            if (!FarmNav.available()) return TaskResult.NAVIGATION_FAILED;
            if (!FarmNav.pathing()) FarmNav.goTo(target.pos(), 1);
            return TaskResult.IN_PROGRESS;
        }
        FarmNav.cancel();

        // 准备种植材料
        if (hand == null) {
            hand = preparePlantingItem(target.profile().plantItem());
            if (hand == null) return TaskResult.RESOURCE_INSUFFICIENT;
        }

        // 发播种包
        if (!acted) {
            BlockPacketSender.useOnBlock(hand, target.pos());
            acted = true;
            waitTicks = 0;
            return TaskResult.IN_PROGRESS;
        }

        // 等待世界更新
        if (waitTicks < WAIT_TICKS) {
            waitTicks++;
            return TaskResult.IN_PROGRESS;
        }

        // 验证补种结果
        if (verifier.plantSucceeded(target)) return TaskResult.SUCCESS;

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
    }

    /** 种植材料搜索：副手已有则直接用，否则从快捷栏/主背包移到副手，保证主手不被占用 */
    private InteractionHand preparePlantingItem(Item plantItem) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;

        // 副手已是种子：直接使用
        if (mc.player.getOffhandItem().is(plantItem)) return InteractionHand.OFF_HAND;

        // 种子不在副手：从快捷栏/主背包找到并移到副手（主手保持工具/空手，不占主手播种）
        int hotbar = InventoryOps.findInHotbar(stack -> stack.is(plantItem));
        if (hotbar != InventoryOps.NOT_FOUND) {
            InventoryOps.moveToOffhand(hotbar);
            return InteractionHand.OFF_HAND;
        }

        int inventory = InventoryOps.find(stack -> stack.is(plantItem));
        if (inventory != InventoryOps.NOT_FOUND) {
            InventoryOps.moveToOffhand(inventory);
            return InteractionHand.OFF_HAND;
        }
        return null;
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
