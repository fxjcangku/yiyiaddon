package com.yiyiaddon.feature.enchant.navigation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * 附魔流程的 Baritone 寻路封装。
 *
 * <p>对应旧项目 {@code AutoEnchantBook} 里的四个 Baritone 调用点，判据逐字照旧：</p>
 * <ul>
 *   <li>{@code walkToBlock}/{@code frontStandPos}/{@code isStandable}（旧 {@code :1943-1950}、
 *       {@code :1899-1927}）：寻路到方块<b>正面站位</b>，避免站在原地开箱失败；</li>
 *   <li>{@code walkToHangout}（旧 {@code :1933-1940}）：寻路到挂机点本体；</li>
 *   <li>{@code stop}（旧 {@code stopBaritone:2021-2023}）：{@code cancelEverything()}；</li>
 *   <li>{@code isPathing}（旧 {@code :2025-2027}）：状态机前置门控用它挂起非寻路状态。</li>
 * </ul>
 *
 * <p>旧项目直接调 {@code BaritoneAPI.getProvider().getPrimaryBaritone()}；本项目按
 * {@code feature/mining/navigation/MiningPathing} 的写法加一层空值与异常兜底
 * （Baritone 不在场时返回 false / 不做动作，不让状态机因外部依赖缺失而崩）。</p>
 */
public final class EnchantPathing {

    private final Minecraft mc = Minecraft.getInstance();

    /** 寻路到方块正面站位（站在相邻块而不是方块本体上方），避免开箱／开附魔台失败 */
    public void walkToBlock(BlockPos pos) {
        if (pos == null) return;
        if (isPathing()) return;
        walkTo(frontStandPos(pos));
    }

    /** 直接寻路到指定站位（旧 {@code tickGearWalkAnvilPos:2913-2918} 用铁砧朝向侧站位） */
    public void walkTo(BlockPos stand) {
        if (stand == null) return;
        if (isPathing()) return;
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return;
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(stand));
        } catch (Throwable ignored) {
            // Baritone 未加载或算路异常：本 tick 不寻路，下一 tick 再试
        }
    }

    /** 寻路到挂机点（旧 {@code walkToHangout:1933-1940}） */
    public void walkToHangout(BlockPos posHangout) {
        if (posHangout == null) return;
        if (isPathing()) return;
        walkTo(posHangout);
    }

    /** 停止寻路（旧 {@code stopBaritone:2021-2023}，只取消，不发 Baritone stop 指令） */
    public void stop() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return;
            baritone.getPathingBehavior().cancelEverything();
        } catch (Throwable ignored) {
            // 停止失败不影响后续流程
        }
    }

    /** Baritone 是否正在寻路（旧 {@code isPathing:2025-2027}） */
    public boolean isPathing() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getPathingBehavior().isPathing();
        } catch (Throwable ignored) {
            return false;
        }
    }

    // ── 站位判据（旧 :1891-1927） ──

    /**
     * 寻找方块正面站位：水平相邻且可站立的空气块，优先离玩家最近的方位
     * （旧 {@code frontStandPos:1899-1919}）。
     */
    private BlockPos frontStandPos(BlockPos pos) {
        if (pos == null || mc.level == null || mc.player == null) return pos;
        BlockPos playerPos = mc.player.blockPosition();
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (int dy = 0; dy >= -1; dy--) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos stand = pos.relative(dir).above(dy);
                if (isStandable(stand)) {
                    double dx = stand.getX() - playerPos.getX();
                    double dz = stand.getZ() - playerPos.getZ();
                    double d = dx * dx + dz * dz;
                    if (d < bestDist) {
                        bestDist = d;
                        best = stand;
                    }
                }
            }
            if (best != null) break;
        }
        // 四面被方块包裹的埋地箱无侧面站位，回退到箱子正上方（顶部裸露处）站立开箱
        if (best == null && isStandable(pos.above())) best = pos.above();
        return best != null ? best : pos;
    }

    /** 坐标是否可作为站立点：本体与上方均为空气，下方有落脚方块（旧 {@code isStandable:1922-1927}） */
    private boolean isStandable(BlockPos pos) {
        if (mc.level == null) return false;
        return mc.level.getBlockState(pos).isAir()
            && mc.level.getBlockState(pos.above()).isAir()
            && !mc.level.getBlockState(pos.below()).isAir();
    }

    private IBaritone getBaritone() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable ignored) {
            return null;
        }
    }
}
