package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.platform.navigation.FarmNav;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

/**
 * 拾取任务：以玩家为中心拾取附近掉落物。
 *
 * 成功标准是「玩家附近连续若干 tick 不再有掉落物」，而非固定等待，保证掉落物捡完整；
 * 掉落物落水（甘蔗/竹子水渠）时导航到附近干燥方块，避免玩家跳进水里卡住起不来；
 * 掉落物卡在竹子/仙人掌等实心方块内时原地等自然吸入，超时放弃；总时长兜底确保绝不卡死。
 */
public final class CollectTask implements FarmTask {

    /** 连续无掉落物这么久才判定捡完（tick），避免服务端尚未把产物刷成 ItemEntity 就提前收工 */
    private static final int EMPTY_GRACE = 20;
    /** 整段拾取的总时长兜底（tick），到点无条件结束，避免状态机卡死 */
    private static final int MAX_TOTAL_TICKS = 400;
    /** 追同一目标、原地吸不到或落水找不到干燥站位时，超过此 tick 数放弃 */
    private static final int STUCK_TICKS = 50;
    /** 玩家磁吸掉落物的半径平方（约 1.5 格），在此范围内等待自然入包 */
    private static final double PICKUP_RADIUS_SQ = 2.25;
    /** 掉落物落水时，水平搜索干燥站位的最远距离（格） */
    private static final int DRY_SEARCH_RADIUS = 4;

    private final BlockPos ref;
    private final double collectRange;
    private int waited;
    private int emptyTicks;
    private int stuckTicks;
    private int targetId = -1;

    public CollectTask(BlockPos ref, double collectRange) {
        this.ref = ref;
        this.collectRange = collectRange;
    }

    @Override
    public TaskResult tick() {
        waited++;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return TaskResult.IN_PROGRESS;

        // 总时长兜底：无论掉落物多难捡，到点直接结束，绝不卡死状态机
        if (waited >= MAX_TOTAL_TICKS) {
            FarmNav.cancel();
            return TaskResult.SUCCESS;
        }

        ItemEntity nearest = nearestItem(mc);
        if (nearest == null) {
            FarmNav.cancel();
            stuckTicks = 0;
            targetId = -1;
            emptyTicks++;
            if (emptyTicks >= EMPTY_GRACE) {
                return TaskResult.SUCCESS;
            }
            return TaskResult.IN_PROGRESS;
        }
        emptyTicks = 0;

        double dist = mc.player.distanceToSqr(nearest);

        // 已进入磁吸范围：等待自然入包，长时间吸不到则放弃
        if (dist <= PICKUP_RADIUS_SQ) {
            FarmNav.cancel();
            stuckTicks++;
            if (stuckTicks >= STUCK_TICKS) {
                return TaskResult.SUCCESS;
            }
            return TaskResult.IN_PROGRESS;
        }

        // 换目标时重置卡死计时，持续追同一目标无进展则累计
        if (nearest.getId() != targetId) {
            targetId = nearest.getId();
            stuckTicks = 0;
        } else {
            stuckTicks++;
        }

        if (stuckTicks >= STUCK_TICKS) {
            FarmNav.cancel();
            return TaskResult.SUCCESS;
        }

        if (!FarmNav.available()) return TaskResult.IN_PROGRESS;

        // 掉落物落水：导航到附近干燥方块，绝不直接导航进水
        BlockPos nav = navTarget(mc, nearest);
        if (nav == null) {
            FarmNav.cancel();
            stuckTicks++;
            if (stuckTicks >= STUCK_TICKS) {
                return TaskResult.SUCCESS;
            }
            return TaskResult.IN_PROGRESS;
        }

        if (!FarmNav.pathing()) FarmNav.goTo(nav, 1);
        return TaskResult.IN_PROGRESS;
    }

    @Override
    public boolean exclusive() {
        return false;
    }

    @Override
    public void cancel() {
        FarmNav.cancel();
    }

    /** 以拾取参考中心（批量=农田中心，单个=目标位置）搜索掉落物，严格限定在农田范围内 */
    private ItemEntity nearestItem(Minecraft mc) {
        double radius = collectRange;
        double rangeSq = radius * radius;
        double px = ref.getX() + 0.5;
        double py = ref.getY() + 0.5;
        double pz = ref.getZ() + 0.5;

        ItemEntity best = null;
        double bestSq = Double.MAX_VALUE;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof ItemEntity item) {
                double d = item.distanceToSqr(px, py, pz);
                if (d <= rangeSq && d < bestSq) {
                    bestSq = d;
                    best = item;
                }
            }
        }
        return best;
    }

    /**
     * 掉落物导航目标：不在水里直接导航到掉落物；在水里则找水平方向最近的干燥方块，
     * 让玩家站在水渠边上把掉落物吸进来，避免跳进水里起不来。附近全是水返回 null。
     */
    private BlockPos navTarget(Minecraft mc, ItemEntity item) {
        BlockPos ip = item.blockPosition();
        if (!isLiquid(mc, ip)) return ip;

        for (int r = 1; r <= DRY_SEARCH_RADIUS; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx == 0 && dz == 0) continue;
                    BlockPos p = ip.offset(dx, 0, dz);
                    if (!isLiquid(mc, p) && !isLiquid(mc, p.below())) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /** 该坐标是否有液体（水/岩浆） */
    private boolean isLiquid(Minecraft mc, BlockPos pos) {
        return !mc.level.getFluidState(pos).isEmpty();
    }
}
