package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.COLLECT_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MAX_NUDGE_TICKS;

/**
 * 星露谷掉落检测与拾取：农场掉落筛选、最近掉落推导、磁吸范围判定与最后一步直走。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewDropCollector {

    private final StardewCoordinator owner;

    StardewDropCollector(StardewCoordinator owner) {
        this.owner = owner;
    }

    /** 掉落物是否已经进入服务端磁吸范围（按掉落物真实坐标判定，不是它所在方格的中心）。 */
    boolean collectInRange(ItemEntity drop) {
        Minecraft mc = Minecraft.getInstance();
        return drop != null && mc.player != null
            && mc.player.distanceToSqr(drop) <= COLLECT_REACH * COLLECT_REACH;
    }

    /**
     * 拾取诊断埋点：只有在「重试到放弃、掉落物真的走不过去」时才打一次。
     *
     * <p>给出掉落物与玩家的真实坐标与间距，用于区分「够不着」和「方块不可站立」两类情况。
     * 同一坐标只报一次，避免每 10 秒刷一遍。</p>
     */
    void reportCollectFailure(BlockPos target) {
        Minecraft mc = Minecraft.getInstance();
        String key = target.getX() + "," + target.getY() + "," + target.getZ();
        if (!owner.reportedCollectFailures.add(key)) return;
        ItemEntity drop = nearestFarmItem();
        String dropText = drop == null ? "已消失"
            : String.format("§f%.1f, %.1f, %.1f", drop.getX(), drop.getY(), drop.getZ());
        String playerText = mc.player == null ? "无玩家"
            : String.format("§f%.1f, %.1f, %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
        String gap = drop == null || mc.player == null ? "未知"
            : String.format("§f%.2f 格", Math.sqrt(mc.player.distanceToSqr(drop)));
        owner.status.critical("COLLECT_FAIL:" + key, "拾取不到掉落物",
            "掉落物 " + dropText + " · 玩家 " + playerText + " · 间距 " + gap);
    }

    /**
     * 拾取的「最后一步」：直接朝掉落物走过去，把它走掉。
     *
     * <p><b>为什么必须补这一步：</b>Baritone 的 {@code GoalNear} 只按整格停靠，站在掉落物所在方格的
     * 旁边就已经算「到达」；而服务端磁吸要求「玩家碰撞箱外扩 1 格」与掉落物碰撞箱相交。
     * 差的就是这最后半格到一格 —— 表现正是「站在旁边、不走过去、捡不起来」，
     * 尤其批量收割一次掉一地时最明显。</p>
     *
     * <p><b>绝不把键按住不放：</b>进入磁吸范围、掉落物消失、任务重规划、超时都会走
     * {@link #stopNudge()} 释放前进 / 跳跃键；{@link #MAX_NUDGE_TICKS} 保证最多走一秒。</p>
     *
     * @return true 表示本 tick 正在直走，调用方不要继续下发寻路
     */
    boolean nudgeTowardDrop(ItemEntity drop) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || drop == null) {
            stopNudge();
            return false;
        }
        if (owner.nudgeTicks++ >= MAX_NUDGE_TICKS) {
            stopNudge();
            return false;
        }
        double dx = drop.getX() - mc.player.getX();
        double dz = drop.getZ() - mc.player.getZ();
        if (dx * dx + dz * dz < 1.0E-4) {
            stopNudge();
            return false;
        }
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        mc.player.setYRot(yaw);
        mc.player.setYHeadRot(yaw);
        mc.options.keyUp.setDown(true);
        owner.nudgeHoldForward = true;
        // 掉落物比脚高（掉在盆上）时补一个跳，否则只会一直贴着方块边推
        if (drop.getY() - mc.player.getY() > 0.5) {
            mc.options.keyJump.setDown(true);
            owner.nudgeHoldJump = true;
        }
        return true;
    }

    /** 释放「最后一步」按住的前进 / 跳跃键。到位、掉落物消失、重规划、重置都必须调用。 */
    void stopNudge() {
        owner.nudgeTicks = 0;
        Minecraft mc = Minecraft.getInstance();
        if (owner.nudgeHoldForward) {
            mc.options.keyUp.setDown(false);
            owner.nudgeHoldForward = false;
        }
        if (owner.nudgeHoldJump) {
            mc.options.keyJump.setDown(false);
            owner.nudgeHoldJump = false;
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  掉落检测与拾取
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    boolean hasFarmDrop() {
        return nearestFarmItem() != null;
    }

    BlockPos nearestFarmItemPos() {
        ItemEntity item = nearestFarmItem();
        return item == null ? null : item.blockPosition();
    }

    ItemEntity nearestFarmItem() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;
        BlockPos min = owner.planner.regionMin();
        BlockPos max = owner.planner.regionMax();
        if (min == null || max == null) return null;
        double px = mc.player.getX();
        double py = mc.player.getY();
        double pz = mc.player.getZ();

        ItemEntity best = null;
        double bestSq = Double.MAX_VALUE;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof ItemEntity item) {
                // 只拾取当前农场掉落（种子 / 成品 / 变种），不捡无关玩家物品
                if (!isFarmDrop(item.getItem())) continue;
                BlockPos itemPos = item.blockPosition();
                if (owner.planner.isNavigationBlocked(TaskType.COLLECT, itemPos)) continue;
                if (itemPos.getX() < min.getX() - 1 || itemPos.getX() > max.getX() + 1
                    || itemPos.getY() < min.getY() - 2 || itemPos.getY() > max.getY() + 4
                    || itemPos.getZ() < min.getZ() - 1 || itemPos.getZ() > max.getZ() + 1) continue;
                double d = item.distanceToSqr(px, py, pz);
                if (d < bestSq) {
                    bestSq = d;
                    best = item;
                }
            }
        }
        return best;
    }

    /**
     * 掉落物是否属于「用户已选作物」的农场掉落（种子 / 成品 / 变种）。
     *
     * <p>只认已选 cropKey：未选作物的掉落不属于本模块管理范围，绝不顺手捡走。
     * 这里直接遍历 {@code selectedCropKeys} 以避免每 tick 分配临时列表。</p>
     */
    private boolean isFarmDrop(ItemStack stack) {
        if (stack == null || stack.isEmpty() || owner.index == null) return false;
        for (String key : owner.selectedCropKeys) {
            CropDefinition crop = owner.index.cropByKey(key);
            if (crop == null) continue;
            if (owner.inventory.matchesSeed(stack, crop)) return true;
            if (owner.inventory.matchesProduce(stack, crop)) return true;
        }
        return false;
    }
}
