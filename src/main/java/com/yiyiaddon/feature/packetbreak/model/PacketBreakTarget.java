package com.yiyiaddon.feature.packetbreak.model;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 单个发包挖掘目标的状态快照。
 *
 * <p>网络阶段与渲染数据集中在独立对象中，模块入口只负责事件和配置，避免
 * START、STOP、确认与重试状态散落在界面代码里。</p>
 *
 * <p><b>逐字搬运自旧项目</b> {@code tactical/packetbreak/PacketBreakTarget.java}（75 行）：
 * 字段、阶段枚举、构造语义与 {@link #resetForRetry(int)} 的重置清单一字未改，只改了包名与可见性
 * （旧项目与本类同包，本项目按「模块八类分包」拆到 {@code model/}，故提升为 {@code public}）。</p>
 */
public final class PacketBreakTarget {

    /** 尚未记录客户端 tick 的标记值。 */
    public static final int TICK_NONE = Integer.MIN_VALUE;

    /** 发包流程阶段；等待旋转回调的阶段单独表示，防止回调未执行就推进状态机。 */
    public enum Phase {
        SCHEDULED,
        START_PENDING,
        MINING,
        STOP_PENDING,
        AWAITING_CONFIRM
    }

    public final BlockPos pos;
    public final BlockState originalState;
    public final Block block;
    public final Direction direction;

    public Phase phase = Phase.SCHEDULED;
    public int waitTicks;
    public int actionRequestTick;
    public int startTick = TICK_NONE;
    public int stopTick = TICK_NONE;
    public int lastStopPacketTick = TICK_NONE;
    public int confirmDeadlineTick = TICK_NONE;
    public int startSequence = -1;
    public int stopSequence = -1;
    public int stopResends;
    public int attempts;
    public int previousSlot = -1;
    public int miningSlot = -1;
    public float destroyDelta;
    public boolean switched;
    public boolean startAcknowledged;
    public boolean stopAcknowledged;

    /** 创建不可变目标快照，方块状态变化由服务器确认路径另行判断。 */
    public PacketBreakTarget(BlockPos pos, BlockState state, Direction direction) {
        this.pos = pos.immutable();
        this.originalState = state;
        this.block = state.getBlock();
        this.direction = direction == null ? Direction.UP : direction;
    }

    /**
     * 为一次全新的 START 重试清理协议状态。
     *
     * <p>旧序列号不能复用；服务端的方块预测 ACK 是单调递增的，复用会把旧确认
     * 错认成新一轮结果。</p>
     */
    public void resetForRetry(int delayTicks) {
        phase = Phase.SCHEDULED;
        waitTicks = delayTicks;
        actionRequestTick = 0;
        startTick = TICK_NONE;
        stopTick = TICK_NONE;
        lastStopPacketTick = TICK_NONE;
        confirmDeadlineTick = TICK_NONE;
        startSequence = -1;
        stopSequence = -1;
        stopResends = 0;
        startAcknowledged = false;
        stopAcknowledged = false;
        destroyDelta = 0;
        previousSlot = -1;
        miningSlot = -1;
        switched = false;
    }
}
