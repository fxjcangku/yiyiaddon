package com.yiyiaddon.platform.network;

import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * 方块交互发包适配层：直接构造并发送 C2S 包，不依赖准星视角的客户端模拟。
 *
 * <p><b>sequence 机制（26.1.2 关键约束）：</b>服务端会校验每个方块交互包携带的 sequence，
 * 客户端必须通过 {@link BlockStatePredictionHandler} 取号，并在取号前登记本地已知的服务端状态。
 * 序号错乱会导致服务端回滚方块，表现为「方块闪烁复原」。正确顺序为：</p>
 * <pre>
 * startPredicting() 开启预测窗口
 *   → retainKnownServerState() 登记原状态
 *   → currentSequence() 取号
 *   → 发包
 *   → close() 关闭窗口
 * </pre>
 * <p>每个包必须独立取号，不能复用同一个 sequence。</p>
 *
 * <p>本层只做协议与 Minecraft API 适配，不含「该不该挖 / 该不该种」的业务判断。</p>
 */
public final class BlockPacketSender {

    private BlockPacketSender() {
    }

    /** 取出预测处理器；原版方法是包私有，通过 accessor Mixin 暴露 */
    private static BlockStatePredictionHandler predictionHandler(ClientLevel level) {
        return ((ClientLevelPredictionAccessor) (Object) level).yiyiaddon$getPredictionHandler();
    }

    /** 发送一个使用物品于方块的包（内部统一取号） */
    private static boolean sendUseOnBlock(InteractionHand hand, BlockHitResult hit, BlockPos predictPos) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) return false;

        BlockState original = level.getBlockState(predictPos);
        BlockStatePredictionHandler handler = predictionHandler(level);

        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(predictPos, original, player);
            int sequence = predicting.currentSequence();
            player.connection.send(new ServerboundUseItemOnPacket(hand, hit, sequence));
        }
        return true;
    }

    /**
     * 发送破坏方块包（START + STOP 同 tick 连发）。
     *
     * <p>农作物、竹子、甘蔗这类方块硬度为 0，服务端收到 START 即判定破坏完成，
     * 因此不需要走 continueDestroyBlock 的挖掘进度循环。STOP 不占用新的 sequence，
     * 服务端仅用于确认动作结束。</p>
     *
     * @param face 破坏朝向，作物一般给 UP
     * @return 是否成功发包
     */
    public static boolean breakBlock(BlockPos pos, Direction face) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null || pos == null) return false;

        BlockState original = level.getBlockState(pos);
        if (original.isAir()) return false;

        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(pos, original, player);
            int sequence = predicting.currentSequence();
            player.connection.send(new ServerboundPlayerActionPacket(
                ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, pos, face, sequence));
            // 本地立刻置空气，保证同 tick 内的后续逻辑（如补种）不会读到旧状态
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        }

        player.connection.send(new ServerboundPlayerActionPacket(
            ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, pos, face));
        return true;
    }

    /**
     * 对方块使用物品（播种）。
     *
     * @param hand    使用哪只手，副手补种传 {@code OFF_HAND}
     * @param soilPos 底盘坐标，种子会种在其上方
     * @return 是否成功发包
     */
    public static boolean useOnBlock(InteractionHand hand, BlockPos soilPos) {
        if (soilPos == null) return false;
        // 命中点取底盘上表面中心，朝向 UP，isInside=false
        Vec3 hitVec = new Vec3(soilPos.getX() + 0.5, soilPos.getY() + 1.0, soilPos.getZ() + 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, soilPos, false);
        // 预测登记的是底盘上方那一格（种子会被种在那里）
        return sendUseOnBlock(hand, hit, soilPos.above());
    }

    /**
     * 对方块本体右键（开容器用）。
     *
     * <p>与 {@link #useOnBlock} 的区别是预测登记的是方块自身：开容器不改变方块状态，
     * 但 sequence 依然要照规矩取号，否则服务端拒绝这次交互。</p>
     *
     * @param face 命中面，箱子给 UP 最稳
     */
    public static boolean interactBlock(InteractionHand hand, BlockPos pos, Direction face) {
        if (pos == null || face == null) return false;
        Vec3 hitVec = new Vec3(
            pos.getX() + 0.5 + face.getStepX() * 0.5,
            pos.getY() + 0.5 + face.getStepY() * 0.5,
            pos.getZ() + 0.5 + face.getStepZ() * 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, face, pos, false);
        return sendUseOnBlock(hand, hit, pos);
    }

    /**
     * 对方块本体右键使用锄头（锄地）。
     *
     * <p>锄地改变的是方块自身（草方块 / 泥土 → 耕地），因此预测登记目标方块自身，
     * 命中面上表面，朝向 UP。</p>
     */
    public static boolean tillBlock(InteractionHand hand, BlockPos pos) {
        if (pos == null) return false;
        Vec3 hitVec = new Vec3(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, pos, false);
        if (!sendUseOnBlock(hand, hit, pos)) return false;
        // 锄地保留挥手动画：与静默的播种 / 开箱区分开
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) mc.player.swing(hand);
        return true;
    }
}
