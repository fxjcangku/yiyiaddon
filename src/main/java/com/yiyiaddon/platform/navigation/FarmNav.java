package com.yiyiaddon.platform.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Baritone 隔离层。
 *
 * 与旧实现的关键区别：不再用「一次失败永久 disabled」的降级策略。
 * 每次调用独立判断，失败返回 false，由 Controller 把当前移动任务标记为
 * NavigationFailed 并重新规划，绝不因 Baritone 不可用而卡死或无限循环。
 */
public final class FarmNav {

    /**
     * 星露谷服务器可能用绊线或其它空碰撞方块承载作物，Baritone 的 blocksToAvoid 可能把它们禁行。
     * 这里只在星露谷导航持有控制权时保存并移除已确认载体，结束后原样恢复，避免影响其它模块。
     */
    private static List<Block> savedBlocksToAvoid;
    private static boolean farmCarrierPassActive;

    private FarmNav() {
    }

    /** Baritone 当前是否可用 */
    public static boolean available() {
        try {
            return baritone() != null;
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * 前往目标坐标附近。
     *
     * @param radius 停靠半径（格，GoalNear 内部会再平方为 rangeSq）
     * @return 是否成功下发寻路任务
     */
    public static boolean goTo(BlockPos pos, int radius) {
        return goTo(pos, radius, false);
    }

    /**
     * 前往星露谷任务目标，临时允许穿过资源身份确认且无实体碰撞的农场载体。
     *
     * <p>若下发失败会立即回滚 Baritone 全局设置；成功后由 {@link #cancel()} 在任务交接时恢复。</p>
     */
    public static boolean goToThroughFarmCarriers(BlockPos pos, int radius, Collection<Block> passableCarriers) {
        enableFarmCarrierPass(passableCarriers);
        boolean started = goToInternal(pos, radius, false);
        if (!started) restoreFarmCarrierAvoidance();
        return started;
    }

    /**
     * 前往目标坐标附近。
     *
     * @param radius       停靠半径（格，GoalNear 内部会再平方为 rangeSq）
     * @param modifyBlocks 是否允许 Baritone 破坏/放置方块（回中心点等场景需要，拾取等场景禁止）
     * @return 是否成功下发寻路任务
     */
    public static boolean goTo(BlockPos pos, int radius, boolean modifyBlocks) {
        restoreFarmCarrierAvoidance();
        return goToInternal(pos, radius, modifyBlocks);
    }

    /** 统一下发目标；调用方负责决定是否启用星露谷载体通行覆盖。 */
    private static boolean goToInternal(BlockPos pos, int radius, boolean modifyBlocks) {
        try {
            var b = baritone();
            if (b == null) return false;
            // 拾取/收割/补种等寻路禁止破坏与放置方块，避免把竹子/仙人掌等作物挖掉；
            // 回中心点寻路则需要允许，否则被栅栏/障碍挡住会半路停下。
            var settings = baritone.api.BaritoneAPI.getSettings();
            settings.allowBreak.value = modifyBlocks;
            settings.allowPlace.value = modifyBlocks;
            b.getCustomGoalProcess().setGoalAndPath(
                new baritone.api.pathing.goals.GoalNear(pos, radius));
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    /** 是否正在寻路中 */
    public static boolean pathing() {
        try {
            var b = baritone();
            if (b == null) return false;
            // goTo 走 CustomGoalProcess（setGoalAndPath），必须同时判断其 isActive，
            // 否则回中心点等自定义目标寻路会被 pathingBehavior.isPathing() 误判为「未寻路」，
            // 导致 Controller 每 tick 重复下发寻路、反复打断移动（走到一半停下）。
            return b.getPathingBehavior().isPathing() || b.getCustomGoalProcess().isActive();
        } catch (Throwable ignored) {
            return false;
        }
    }

    /** 取消当前寻路任务 */
    public static void cancel() {
        try {
            var b = baritone();
            if (b != null) {
                b.getPathingBehavior().cancelEverything();
                // 必须用 onLostControl 真正重置 CustomGoalProcess 的 state（setGoal(null) 不会清空 state，
                // 会导致 isActive() 一直为 true，令 pathing() 误判为「仍在寻路」而不再下发新寻路）。
                b.getCustomGoalProcess().onLostControl();
            }
        } catch (Throwable ignored) {
        } finally {
            restoreFarmCarrierAvoidance();
        }
    }

    /** 玩家是否已到达目标附近（用于任务层判断能否交互） */
    public static boolean arrived(BlockPos pos, double reach) {
        try {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player == null) return false;
            double dx = pos.getX() + 0.5 - mc.player.getX();
            double dy = pos.getY() + 0.5 - mc.player.getY();
            double dz = pos.getZ() + 0.5 - mc.player.getZ();
            return dx * dx + dy * dy + dz * dz <= reach * reach;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static baritone.api.IBaritone baritone() {
        return baritone.api.BaritoneAPI.getProvider().getPrimaryBaritone();
    }

    /** 保存用户原设置后只移除已确认的空碰撞载体，不能覆盖玩家配置的其它危险方块。 */
    private static synchronized void enableFarmCarrierPass(Collection<Block> passableCarriers) {
        if (farmCarrierPassActive) return;
        try {
            var setting = baritone.api.BaritoneAPI.getSettings().blocksToAvoid;
            savedBlocksToAvoid = new ArrayList<>(setting.value);
            List<Block> adjusted = new ArrayList<>(setting.value);
            if (passableCarriers != null && !passableCarriers.isEmpty()) {
                adjusted.removeIf(passableCarriers::contains);
            }
            setting.value = adjusted;
            farmCarrierPassActive = true;
        } catch (Throwable ignored) {
            savedBlocksToAvoid = null;
            farmCarrierPassActive = false;
        }
    }

    /** 星露谷任务释放导航控制时完整恢复 Baritone 原始避让列表。 */
    private static synchronized void restoreFarmCarrierAvoidance() {
        if (!farmCarrierPassActive) return;
        try {
            baritone.api.BaritoneAPI.getSettings().blocksToAvoid.value =
                savedBlocksToAvoid == null ? new ArrayList<>() : new ArrayList<>(savedBlocksToAvoid);
        } catch (Throwable ignored) {
        } finally {
            savedBlocksToAvoid = null;
            farmCarrierPassActive = false;
        }
    }
}
