package com.yiyiaddon.feature.mining.fastbreak;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 自动挖矿的单槽发包破坏状态机（秒破）。
 *
 * <p>26.1.2 服务端在 STOP 到达时要求
 * {@code getDestroyProgress * (经过 tick + 1) >= 0.7}。因此真正可靠的
 * 发包快速破坏不是同 tick 盲发 START/STOP，而是只发一次 START，在服务端
 * 最早可接受的 0.7 卡点只发一次 STOP，再等待权威方块同步。</p>
 *
 * <p>逐条移植旧项目 {@code mining/fastbreak/AutoMinerFastBreakController.java}（229 行）。
 * 差异仅两处，都是本项目口径：{@code module.isActive()} → {@code module.isEnabled()}；
 * 模块实例由 Mixin 接线层从 {@code ModuleManager} 取（旧项目走第三方框架的模块表）。
 * 开关本身默认关闭（{@code MiningSettings.fastBreak} 默认 false，用户 2026-09-16 拍板）。</p>
 */
public final class MiningFastBreakController {

    /** 服务端 STOP 的最早 tick 判据（26.1.2 服务端存活判定阈值） */
    private static final float SERVER_STOP_THRESHOLD = 0.7F;

    /** 服务端判定方块彻底破坏的进度阈值 */
    private static final float SERVER_COMPLETE_THRESHOLD = 1.0F;

    /** 等待权威方块同步的兜底余量（tick） */
    private static final int CONFIRM_MARGIN_TICKS = 40;

    private final MiningPacketProtocol protocol = new MiningPacketProtocol();

    private ClientLevel level;
    private BlockPos pos;
    private Direction direction;
    private BlockState originalState;
    private Block originalBlock;
    private Phase phase = Phase.IDLE;
    private int startTick;
    private int confirmDeadlineTick;
    private int nextStartTick;

    /** Mixin 的返回裁决；PASS 表示交还原版处理。 */
    public enum StartResult {
        PASS,
        ACCEPTED,
        COOLDOWN
    }

    private enum Phase {
        IDLE,
        MINING,
        AWAITING_CONFIRM
    }

    /** 接管一次 startDestroyBlock；不可破坏目标仍交还原版。 */
    public StartResult start(Minecraft mc, AutoMinerModule module, BlockPos target, Direction face) {
        if (!ready(mc, module)) {
            release(mc, true);
            return StartResult.PASS;
        }

        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        BlockState state = currentLevel.getBlockState(target);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) return StartResult.PASS;

        if (level != currentLevel) release(mc, false);
        if (isActive() && pos.equals(target)) return StartResult.ACCEPTED;
        if (isActive()) release(mc, true);
        if (player.tickCount < nextStartTick) return StartResult.COOLDOWN;

        level = currentLevel;
        pos = target.immutable();
        direction = face == null ? Direction.UP : face;
        originalState = state;
        originalBlock = state.getBlock();
        startTick = player.tickCount;
        phase = Phase.MINING;

        protocol.sendStart(player, currentLevel, pos, direction, state);
        updateCracks(player, 0.0F);

        float delta = destroyDelta(player, currentLevel);
        if (delta >= SERVER_COMPLETE_THRESHOLD) {
            // 硬度为零/创造类目标由服务端收到 START 时直接破坏。
            awaitConfirmation(player, delta);
        } else if (requiredElapsedTicks(delta, SERVER_STOP_THRESHOLD) == 0) {
            sendStop(player, currentLevel, module, delta);
        }
        return StartResult.ACCEPTED;
    }

    /**
     * 接管 continueDestroyBlock，并在服务端 0.7 阈值的最早 tick 发一次 STOP。
     */
    public boolean continueBreaking(Minecraft mc, AutoMinerModule module, BlockPos target, Direction face) {
        if (!ready(mc, module)) {
            release(mc, true);
            return false;
        }
        if (level != mc.level) release(mc, false);

        if (!isActive() || !pos.equals(target)) {
            return start(mc, module, target, face) != StartResult.PASS;
        }

        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        if (hasAuthoritativeChange(currentLevel)) {
            finish(player, module.getBreakInterval());
            return true;
        }
        if (!player.isWithinBlockInteractionRange(pos, 1.0)) {
            release(mc, true);
            return true;
        }

        if (phase == Phase.MINING) {
            float delta = destroyDelta(player, currentLevel);
            int elapsed = player.tickCount - startTick;
            int required = requiredElapsedTicks(delta, SERVER_STOP_THRESHOLD);
            updateCracks(player, required == Integer.MAX_VALUE ? 0.0F
                : Math.min(1.0F, delta * (elapsed + 1) / SERVER_STOP_THRESHOLD));
            if (elapsed >= required) sendStop(player, currentLevel, module, delta);
            return true;
        }

        if (phase == Phase.AWAITING_CONFIRM && player.tickCount > confirmDeadlineTick) {
            // TCP 下不重复洪泛 STOP；超时后从全新的 START 状态重新同步一次。
            clearCracks(player);
            clearState();
            nextStartTick = player.tickCount + Math.max(1, module.getBreakInterval());
        }
        return true;
    }

    /** 用户/Baritone 停止攻击时清理本地裂纹；尚未 STOP 才向服务端 ABORT。 */
    public boolean stop(Minecraft mc, int intervalTicks) {
        if (!isActive()) return false;
        LocalPlayer player = mc.player;
        if (player != null && phase == Phase.MINING) protocol.sendAbort(player, pos, direction);
        if (player != null) {
            clearCracks(player);
            nextStartTick = player.tickCount + Math.max(0, intervalTicks);
        }
        clearState();
        return true;
    }

    /** 模块关闭、断线或目标切换时的统一清理入口。 */
    public void release(Minecraft mc, boolean abortMining) {
        if (!isActive()) return;
        LocalPlayer player = mc.player;
        if (abortMining && player != null && phase == Phase.MINING) {
            protocol.sendAbort(player, pos, direction);
        }
        if (player != null) clearCracks(player);
        clearState();
    }

    public boolean isActive() {
        return phase != Phase.IDLE;
    }

    private void sendStop(LocalPlayer player, ClientLevel currentLevel, AutoMinerModule module, float delta) {
        if (phase != Phase.MINING) return;
        protocol.sendStop(player, currentLevel, pos, direction);
        if (module.getBypassAnticheat()) {
            protocol.sendAbort(player, pos.above(), direction);
        }
        awaitConfirmation(player, delta);
    }

    private void awaitConfirmation(LocalPlayer player, float delta) {
        phase = Phase.AWAITING_CONFIRM;
        int fullElapsed = requiredElapsedTicks(delta, SERVER_COMPLETE_THRESHOLD);
        if (fullElapsed == Integer.MAX_VALUE) fullElapsed = CONFIRM_MARGIN_TICKS;
        confirmDeadlineTick = Math.max(player.tickCount + CONFIRM_MARGIN_TICKS,
            startTick + fullElapsed + CONFIRM_MARGIN_TICKS);
        updateCracks(player, 1.0F);
    }

    private float destroyDelta(LocalPlayer player, ClientLevel currentLevel) {
        BlockState state = currentLevel.getBlockState(pos);
        return state.getDestroyProgress(player, currentLevel, pos);
    }

    private boolean hasAuthoritativeChange(ClientLevel currentLevel) {
        BlockState current = currentLevel.getBlockState(pos);
        return current.getBlock() != originalBlock || !current.equals(originalState);
    }

    private int requiredElapsedTicks(float delta, float threshold) {
        if (!(delta > 0.0F) || !Float.isFinite(delta)) return Integer.MAX_VALUE;
        double samples = Math.ceil(threshold / (double) delta);
        if (samples >= Integer.MAX_VALUE) return Integer.MAX_VALUE;
        int elapsed = Math.max(0, (int) samples - 1);
        while (elapsed < Integer.MAX_VALUE - 1 && delta * (elapsed + 1) < threshold) elapsed++;
        while (elapsed > 0 && delta * elapsed >= threshold) elapsed--;
        return elapsed;
    }

    private boolean ready(Minecraft mc, AutoMinerModule module) {
        return mc.player != null && mc.level != null && module != null
            && module.isEnabled() && module.getFastBreak();
    }

    private void finish(LocalPlayer player, int intervalTicks) {
        clearCracks(player);
        nextStartTick = player.tickCount + Math.max(0, intervalTicks);
        clearState();
    }

    private void updateCracks(LocalPlayer player, float progress) {
        if (level == null || pos == null) return;
        int stage = Math.min(9, Math.max(0, (int) (progress * 10.0F)));
        level.destroyBlockProgress(player.getId(), pos, stage);
    }

    private void clearCracks(LocalPlayer player) {
        if (level != null && pos != null) level.destroyBlockProgress(player.getId(), pos, -1);
    }

    private void clearState() {
        level = null;
        pos = null;
        direction = null;
        originalState = null;
        originalBlock = null;
        phase = Phase.IDLE;
        startTick = 0;
        confirmDeadlineTick = 0;
    }
}
