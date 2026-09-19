package com.yiyiaddon.core.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 方块权威反馈：把「方块破坏确认」相关数据包抽成只读快照交给订阅者。
 *
 * <p><b>为什么需要：</b>发包秒破必须知道两件事——服务端确认到哪个预测序列号
 * （{@code ClientboundBlockChangedAckPacket}），以及自己正在挖的坐标是否被权威
 * 改写（{@code ClientboundBlockUpdatePacket} / {@code ClientboundSectionBlocksUpdatePacket}）。
 * 前者判定 STOP 是否被接受，后者区分「破坏成功」与「被拒绝 / 回弹」。</p>
 *
 * <p>包对象一律不外流：核心在网络线程按 {@code BlockWatchService} 登记的关注坐标做匹配，
 * 只把匹配到的结果抽成快照；区块批量更新包不会逐条展开派发。</p>
 *
 * @param kind     反馈类型
 * @param pos      权威更新的方块坐标（仅 {@link Kind#UPDATE}）
 * @param state    权威方块状态（仅 {@link Kind#UPDATE}）
 * @param sequence 服务端确认的预测序列号（仅 {@link Kind#ACK}），无意义时为 -1
 * @param direct   是否为服务端针对该坐标下发的单方块修正（通常代表拒绝或回弹）
 */
public record ServerBlockEvent(Kind kind, BlockPos pos, BlockState state, int sequence, boolean direct) {

    public enum Kind {
        /** 服务端确认了某个预测序列号 */
        ACK,
        /** 服务端下发了权威方块状态 */
        UPDATE
    }

    public static ServerBlockEvent ack(int sequence) {
        return new ServerBlockEvent(Kind.ACK, null, null, sequence, false);
    }

    public static ServerBlockEvent update(BlockPos pos, BlockState state, boolean direct) {
        return new ServerBlockEvent(Kind.UPDATE, pos, state, -1, direct);
    }
}
