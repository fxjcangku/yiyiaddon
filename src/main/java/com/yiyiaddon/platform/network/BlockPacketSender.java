package com.yiyiaddon.platform.network;

import com.yiyiaddon.core.net.ClientPacketSender;
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
 *
 * <p><b>为什么绕行发包闸门（{@link ClientPacketSender#sendBypassingGate}）：</b>本层的包是模块
 * <b>按任务节奏主动批量发起</b>的交互，不是玩家手动操作。走 {@code Connection.send} 会被
 * 「发包防踢」的限速规则拦下——默认每秒最多 8 个放置 / 8 个挖掘，而星露谷一次补水就连发 8~32 包、
 * 批量收割一次 8~15 格，超出部分被直接丢弃，只能等下一轮重试，表现为「装水 / 破坏变慢」
 * （用户 2026-09-19 实机反馈）。因此本层统一走核心的绕行直发通道，与秒破的 START/STOP 同一机制。</p>
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
        return sendUseOnBlock(hand, hit, predictPos, null);
    }

    /**
     * 发送一个使用物品于方块的包（内部统一取号），可选地在本地立刻落下结果方块。
     *
     * <p><b>为什么要 {@code placedState}</b>：原版放置走 {@code BlockItem#place}，客户端会<b>立刻</b>
     * 把方块写进本地世界（服务端回包再对账），所以「同一刻连着放第二块、拿刚放的那块当锚点」是成立的。
     * 本层直发包绕过原版入口，就必须自己补这一步，否则同一刻内后续放置看到的仍是岩浆 / 空气，
     * 「一次铺一片」会退化成「一刻一块等服务器确认」。写法与 {@link #breakBlock} 的本地置空气同一口径。</p>
     *
     * @param placedState 放置成功后本地要写的方块状态；{@code null} 表示不写（右键交互类不改变方块）
     */
    private static boolean sendUseOnBlock(InteractionHand hand, BlockHitResult hit, BlockPos predictPos,
                                          BlockState placedState) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) return false;

        BlockState original = level.getBlockState(predictPos);
        BlockStatePredictionHandler handler = predictionHandler(level);

        boolean sent;
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(predictPos, original, player);
            int sequence = predicting.currentSequence();
            sent = ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
                new ServerboundUseItemOnPacket(hand, hit, sequence));
            // 本地立刻落块：保证同 tick 内的后续逻辑（垫脚的下一块拿它当锚点）读到的是放好的世界
            if (sent && placedState != null) {
                level.setBlock(predictPos, placedState, 11);
            }
        }
        return sent;
    }

    /**
     * 往任意一面放置一块方块（岩浆垫脚铺路 / 连锁封堵用，用户 2026-09-19）。
     *
     * <p>命中点取锚点方块该面中心外 0.5 格，预测登记目标格（方块会被放进那一格），并<b>本地立刻落块</b>
     * （见 {@link #sendUseOnBlock}）——垫脚要「一次铺一片」，同一刻连放多块时后一块常拿前一块当锚点。</p>
     *
     * <p>面能不能放由调用方先判（锚点面要 sturdy、玩家要看得见，见 {@code BlockPlacer#placeAt}）；
     * 本层只做协议与本地预测，不做业务判断。回归值只表示「包已发出」（与原版放置一样是乐观口径：
     * 服务端若不认，随后的方块变更包会把本地预测对账回真实状态）。</p>
     *
     * @param anchor     贴着的锚点方块
     * @param face       锚点上要贴的那一面（朝向目标格）
     * @param predictPos 会被放上方块的那一格
     */
    public static boolean placeBlockOn(InteractionHand hand, BlockPos anchor, Direction face,
                                       BlockPos predictPos, BlockState placedState) {
        if (anchor == null || face == null || predictPos == null) return false;
        Vec3 hitVec = new Vec3(
            anchor.getX() + 0.5 + face.getStepX() * 0.5,
            anchor.getY() + 0.5 + face.getStepY() * 0.5,
            anchor.getZ() + 0.5 + face.getStepZ() * 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, face, anchor, false);
        return sendUseOnBlock(hand, hit, predictPos, placedState);
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
        boolean started;
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(pos, original, player);
            int sequence = predicting.currentSequence();
            started = ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
                new ServerboundPlayerActionPacket(
                    ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, pos, face, sequence));
            // 本地立刻置空气，保证同 tick 内的后续逻辑（如补种）不会读到旧状态
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        }

        ClientPacketSender.sendBypassingGate(player.connection.getConnection(),
            new ServerboundPlayerActionPacket(
                ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, pos, face));
        return started;
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
