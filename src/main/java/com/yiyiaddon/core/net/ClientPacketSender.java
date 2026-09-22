package com.yiyiaddon.core.net;

import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.common.custom.BrandPayload;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.UUID;

/**
 * 语义化直发通道：模块用「想做什么」的方式发包，包对象由核心构造。
 *
 * <p><b>为什么不给模块 {@code send(Packet)}：</b>那等于把整套包类型与构造参数暴露出去，
 * 模块就能自行拼装任意包并绕过闸门。这里只暴露战术模块真正需要的动作，全部走
 * {@link PacketSendBypass} 绕行判定（等价旧框架的 {@code sendSilently}），避免被模块自己
 * 注册的规则二次拦截。</p>
 *
 * <p><b>谁能额外绕行（{@link #sendBypassingGate}）：</b>只有平台层的方块交互发包适配器
 * {@code platform/network/BlockPacketSender}。它代表的是模块<b>按任务节奏主动批量发起</b>的交互
 * （浇水 / 播种 / 收割 / 开箱 / 锄地），不是玩家手动操作——一次补水准星露谷要连发 8~32 包，
 * 被「发包防踢」的限速丢掉大半后表现为「装水 / 破坏变慢」（用户 2026-09-19 实机反馈）。
 * 业务模块仍然禁止自行拼包绕行。</p>
 *
 * <p>方块交互的预测序列号统一由本类取号（顺序：{@code startPredicting → retainKnownServerState
 * → currentSequence → 发包}），模块不需要触碰预测处理器。</p>
 */
public final class ClientPacketSender {

    private ClientPacketSender() {
    }

    /** 当前服务器连接；未进服返回 {@code null} */
    public static Connection connection() {
        ClientPacketListener listener = Minecraft.getInstance().getConnection();
        return listener == null ? null : listener.getConnection();
    }

    // ── 玩家输入 / 聊天 / 伪装 ──

    /** 按给定标志位发送一次玩家输入包（{@code flags} 见 {@link SendView} 的 {@code INPUT_*} 常量） */
    public static boolean sendInput(int flags) {
        Connection connection = connection();
        if (connection == null) return false;
        dispatch(connection, new ServerboundPlayerInputPacket(new Input(
            (flags & SendView.INPUT_FORWARD) != 0,
            (flags & SendView.INPUT_BACKWARD) != 0,
            (flags & SendView.INPUT_LEFT) != 0,
            (flags & SendView.INPUT_RIGHT) != 0,
            (flags & SendView.INPUT_JUMP) != 0,
            (flags & SendView.INPUT_SHIFT) != 0,
            (flags & SendView.INPUT_SPRINT) != 0
        )));
        return true;
    }

    /** 发送聊天消息（沿用原版签名与时间戳逻辑，只是绕行本模组的发包闸门） */
    public static boolean sendChat(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || message == null || message.isEmpty()) return false;
        PacketSendBypass.run(() -> player.connection.sendChat(message));
        return true;
    }

    /** 发送客户端品牌（伪装用） */
    public static boolean sendBrand(String brand) {
        Connection connection = connection();
        if (connection == null || brand == null) return false;
        dispatch(connection, new ServerboundCustomPayloadPacket(new BrandPayload(brand)));
        return true;
    }

    // ── 能力 / 起伞 / 视角 ──

    /** 置位或还原飞行态并同步服务端（客户端 {@code Abilities} 对象同步改写，与旧实现一致） */
    public static boolean sendAbilities(boolean flying) {
        LocalPlayer player = Minecraft.getInstance().player;
        Connection connection = connection();
        if (player == null || connection == null) return false;
        player.getAbilities().flying = flying;
        dispatch(connection, new ServerboundPlayerAbilitiesPacket(player.getAbilities()));
        return true;
    }

    /** 官方起伞（START_FALL_FLYING） */
    public static boolean sendStartFallFlying() {
        LocalPlayer player = Minecraft.getInstance().player;
        Connection connection = connection();
        if (player == null || connection == null) return false;
        dispatch(connection, new ServerboundPlayerCommandPacket(
            player, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        return true;
    }

    /** 只含旋转的位置包（防挂机转身 / 视角抖动） */
    public static boolean sendMoveRotation(float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
        Connection connection = connection();
        if (connection == null) return false;
        dispatch(connection, new ServerboundMovePlayerPacket.Rot(yaw, pitch, onGround, horizontalCollision));
        return true;
    }

    /** 当前生效的发包规则条数（诊断用：大于 0 表示模块发出的包会先经过本模组的规则判定） */
    public static int sendRuleCount() {
        return SendGate.ruleCount();
    }

    // ── 换手 / 选中槽 ──

    /**
     * 切换快捷栏选中槽（0~8），并把这次选择同步给服务端。
     *
     * <p><b>为什么必须走绕行通道</b>：服务端算方块破坏进度读的是<b>它那边</b>的
     * {@code getSelectedItem()}，而模块原先这个包是直接 {@code connection.send} 的 —— 一旦被本模组
     * 的发包规则拦下（延迟或取消），客户端与服务端就会对「手上那件」产生认知分叉：客户端算出每刻
     * 0.0833、服务端那边拿着的是采掘速度为 0 的道具、算出 0，表现就是「怎么按服务端都不认这一格」
     * （实机：同一格按住 120 tick、原版流程挖满 10 秒仍纹丝不动）。</p>
     */
    public static boolean selectHotbar(int slot) {
        LocalPlayer player = Minecraft.getInstance().player;
        Connection connection = connection();
        if (player == null || connection == null || slot < 0 || slot > 8) return false;
        player.getInventory().setSelectedSlot(slot);
        dispatch(connection, new ServerboundSetCarriedItemPacket(slot));
        return true;
    }

    /**
     * 把背包槽（0~35）与当前选中的快捷栏槽交换（原版「背包里那格 ↔ 手上那格」的同一个动作）。
     *
     * <p>同样必须绕行：这条点击包一旦被拦，客户端本地预测已经换了手、服务端却没换，
     * 分叉会一直留到下一次全量同步，其间所有需要「手持物正确」的动作（尤其是破坏）全部失效。</p>
     */
    public static boolean swapToHotbar(int invSlot) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null || invSlot < 0 || invSlot >= 36) return false;
        int selected = player.getInventory().getSelectedSlot();
        PacketSendBypass.run(() -> mc.gameMode.handleContainerInput(
            player.inventoryMenu.containerId, invSlot, selected, ContainerInput.SWAP, player));
        return true;
    }

    /**
     * 把背包槽（9~35）与副手交换（原版「F 键换副手」的同一个动作），同样绕行发放。
     *
     * @param offhandMenuSlot 副手在物品栏菜单里的槽位索引（40）
     */
    public static boolean swapOffhandWith(int invSlot, int offhandMenuSlot) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null || invSlot < 9 || invSlot >= 36) return false;
        PacketSendBypass.run(() -> mc.gameMode.handleContainerInput(
            player.inventoryMenu.containerId, invSlot, offhandMenuSlot, ContainerInput.SWAP, player));
        return true;
    }

    // ── 方块破坏（含预测序列号） ──

    /** 开始破坏：取新序列号并登记客户端已知的服务端方块状态 */
    public static boolean sendBreakStart(BlockPos pos, Direction face, BlockState knownServerState) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        Connection connection = connection();
        if (player == null || level == null || connection == null || pos == null) return false;

        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(pos, knownServerState, player);
            dispatch(connection, new ServerboundPlayerActionPacket(
                ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK,
                pos,
                face == null ? Direction.UP : face,
                predicting.currentSequence()
            ));
        }
        return true;
    }

    /** 停止破坏：取新序列号 */
    public static boolean sendBreakStop(BlockPos pos, Direction face) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        Connection connection = connection();
        if (player == null || level == null || connection == null || pos == null) return false;

        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            dispatch(connection, new ServerboundPlayerActionPacket(
                ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK,
                pos,
                face == null ? Direction.UP : face,
                predicting.currentSequence()
            ));
        }
        return true;
    }

    /** 中止破坏：不带序列号 */
    public static boolean sendBreakAbort(BlockPos pos, Direction face) {
        Connection connection = connection();
        if (connection == null || pos == null) return false;
        dispatch(connection, new ServerboundPlayerActionPacket(
            ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK,
            pos,
            face == null ? Direction.UP : face));
        return true;
    }

    // ── 物品使用 ──

    /**
     * 取一个新的预测序列号（物品使用包与方块动作包都要带）。
     *
     * <p>必须在客户端主线程调用；取号本身不改变世界状态，包体由调用方紧接着发出。</p>
     */
    public static int nextPredictionSequence() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return -1;
        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            return predicting.currentSequence();
        }
    }

    /** 使用物品（烟花推进等）：序列号由调用方从预测处理器取得 */
    public static boolean sendUseItem(InteractionHand hand, int sequence, float yRot, float xRot) {
        Connection connection = connection();
        if (connection == null) return false;
        dispatch(connection, new ServerboundUseItemPacket(hand, sequence, yRot, xRot));
        return true;
    }

    /** 对方块使用物品（垫脚放置等）：取新序列号并登记目标坐标的已知状态 */
    public static boolean sendUseItemOn(InteractionHand hand, BlockHitResult hit, BlockPos predictPos) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        Connection connection = connection();
        if (player == null || level == null || connection == null || hit == null || predictPos == null) return false;

        BlockStatePredictionHandler handler = predictionHandler(level);
        try (BlockStatePredictionHandler predicting = handler.startPredicting()) {
            predicting.retainKnownServerState(predictPos, level.getBlockState(predictPos), player);
            dispatch(connection, new ServerboundUseItemOnPacket(hand, hit, predicting.currentSequence()));
        }
        return true;
    }

    // ── 资源包响应 ──

    /** 回应服务器资源包推送（暴力绕过：直接接受并声明加载完成） */
    public static boolean sendResourcePackResponse(UUID packId, ServerboundResourcePackPacket.Action action) {
        Connection connection = connection();
        if (connection == null || packId == null || action == null) return false;
        dispatch(connection, new ServerboundResourcePackPacket(packId, action));
        return true;
    }

    // ── 内部 ──

    /**
     * 平台层发包适配器专用的绕行入口：直发一个包，不进发包闸门。
     *
     * <p>给 {@code platform/network/BlockPacketSender} 用（见类注释「谁能额外绕行」）。
     * 连接由调用方给出，避免两边各自取一次 {@code Minecraft} 连接出现不一致。</p>
     */
    public static boolean sendBypassingGate(Connection connection, Packet<?> packet) {
        if (connection == null || packet == null) return false;
        dispatch(connection, packet);
        return true;
    }

    /** 绕行闸门直发一个包（核心内部与延迟队列共用） */
    static void dispatch(Connection connection, Packet<?> packet) {
        if (connection == null || packet == null) return;
        PacketSendBypass.run(() -> connection.send(packet));
    }

    private static BlockStatePredictionHandler predictionHandler(ClientLevel level) {
        return ((ClientLevelPredictionAccessor) (Object) level).yiyiaddon$getPredictionHandler();
    }
}
