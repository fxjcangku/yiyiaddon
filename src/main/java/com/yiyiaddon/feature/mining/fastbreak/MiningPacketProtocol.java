package com.yiyiaddon.feature.mining.fastbreak;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 自动挖矿快速破坏（秒破）的发包层。
 *
 * <p>START 与 STOP 分别取得新的预测 sequence；START 登记客户端已知的
 * 服务端方块状态，服务端确认前不在客户端预测删除方块。ABORT 不带 sequence。</p>
 *
 * <p>逐字移植旧项目 {@code mining/fastbreak/AutoMinerPacketProtocol.java}（52 行），
 * 唯一差异是包名与访问器所在包名。</p>
 *
 * <p><b>唯一的行为差异（用户 2026-09-19 决策）：</b>发包改走 {@link ClientPacketSender#sendBypassingGate}
 * 绕行「发包防踢」闸门。秒破是模块按自己的节奏连发的破坏包，走 {@code Connection.send} 会被防踢的
 * 「限制挖掘速度」（默认每秒 8 个）丢掉超出的 START —— 那一格这一秒就挖不动，服务器卡顿 / 拉回冷却期
 * 更是全部丢弃，秒破等于停摆；STOP / ABORT 虽被防踢放行，但 START 丢了整轮就没有意义。
 * 取号与登记顺序一字未改（仍是 {@code startPredicting → retainKnownServerState → currentSequence → 发包}）。</p>
 */
final class MiningPacketProtocol {

    /** 开始破坏：取新 sequence，并登记已知服务端方块状态 */
    void sendStart(LocalPlayer player, ClientLevel level, BlockPos pos, Direction direction, BlockState state) {
        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(pos, state, player);
            ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
                new ServerboundPlayerActionPacket(
                    ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK,
                    pos,
                    direction,
                    predicting.currentSequence()
                ));
        }
    }

    /** 停止破坏：取新 sequence */
    void sendStop(LocalPlayer player, ClientLevel level, BlockPos pos, Direction direction) {
        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
                new ServerboundPlayerActionPacket(
                    ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK,
                    pos,
                    direction,
                    predicting.currentSequence()
                ));
        }
    }

    /** 中止破坏：不带 sequence */
    void sendAbort(LocalPlayer player, BlockPos pos, Direction direction) {
        ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
            new ServerboundPlayerActionPacket(
                ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, pos, direction));
    }

    private BlockStatePredictionHandler predictionHandler(ClientLevel level) {
        return ((ClientLevelPredictionAccessor) (Object) level).yiyiaddon$getPredictionHandler();
    }
}
