package com.yiyiaddon.core.net;

import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.platform.eat.OffhandRationLock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.entity.player.Input;

/**
 * 发包拦截入口：由 Mixin 在 {@code Connection.send} 的 HEAD 处调用（网络线程）。
 *
 * <p>处置链：绕行直发（核心自己发的包）→ 规则判定 → 放行 / 取消 / 改写 / 延迟。
 * 只有需要判定的包才会被构造视图，且视图立即丢弃；包对象始终留在核心手里。</p>
 *
 * <p><b>为什么不在这里派发事件：</b>本方法跑在网络线程，派发必须回到主线程，
 * 因此被取消的聊天消息只是入队，由主线程每刻统一交给订阅者。</p>
 */
public final class SendInterceptor {

    private SendInterceptor() {
    }

    /**
     * 判定一个即将发出的包。
     *
     * @return {@code true} 表示该包已被核心处置（调用方必须取消原包的发送）
     */
    public static boolean intercept(Connection connection, Packet<?> packet) {
        if (packet == null) return false;

        // 核心自己的直发包：不判定、不观测（等价旧框架的 sendSilently）
        if (PacketSendBypass.active()) return false;

        // 副手口粮锁：模块运行期间，玩家手动的换手包直接丢弃（用户 2026-09-19「运行期间锁死副手
        // 食物不让切换？除非停止模块」）。判据只读一个 volatile 布尔 + 包类型，无阻塞；
        // 模块自己的换手走容器点击、不产生这个包，所以锁挡不到自己
        if (OffhandRationLock.locked() && packet instanceof ServerboundPlayerActionPacket lockedSwap
            && lockedSwap.getAction() == ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            OffhandRationLock.reportBlocked(); // 只自增一个计数，主线程取走后给玩家提示
            return true;
        }

        // 没有任何规则时保持原有行为：照旧入队观测
        if (SendGate.ruleCount() == 0) {
            EventDispatcher.enqueueSent(packet);
            return false;
        }

        SendView view = viewOf(packet);
        SendDecision decision = SendGate.evaluate(view);

        switch (decision.action()) {
            case CANCEL -> {
                // 被拦下的聊天消息要把原文交给订阅者（它们负责排队重发），其余包无需交接
                if (view.kind() == SendView.Kind.CHAT) {
                    EventDispatcher.enqueueClientChat(view.text());
                }
                return true;
            }
            case REPLACE_INPUT -> {
                ClientPacketSender.dispatch(connection, new ServerboundPlayerInputPacket(inputOf(decision.inputFlags())));
                return true;
            }
            case DELAY -> {
                SendDelayService.enqueue(connection, packet, decision.delayMillis());
                return true;
            }
            default -> {
                EventDispatcher.enqueueSent(packet);
                return false;
            }
        }
    }

    /** 把包抽成判据视图；未覆盖的包一律 {@link SendView.Kind#OTHER} */
    private static SendView viewOf(Packet<?> packet) {
        if (packet instanceof ServerboundChatPacket chat) {
            return SendView.chat(chat.message());
        }
        if (packet instanceof ServerboundPlayerInputPacket inputPacket) {
            return SendView.input(flagsOf(inputPacket.input()));
        }
        if (packet instanceof ServerboundCustomPayloadPacket payloadPacket) {
            return SendView.payload(payloadPacket.payload().type().id().toString().toLowerCase(java.util.Locale.ROOT));
        }
        if (packet instanceof ServerboundPlayerActionPacket actionPacket) {
            return SendView.action(actionPacket.getAction().name(), actionPacket.getPos());
        }
        if (packet instanceof ServerboundUseItemOnPacket useOnPacket) {
            BlockPos pos = useOnPacket.getHitResult().getBlockPos();
            return SendView.useItemOn(pos);
        }
        if (packet instanceof ServerboundUseItemPacket) {
            return SendView.of(SendView.Kind.USE_ITEM);
        }
        if (packet instanceof ServerboundMovePlayerPacket) {
            return SendView.of(SendView.Kind.MOVE_PLAYER);
        }
        if (packet instanceof ServerboundMoveVehiclePacket) {
            return SendView.of(SendView.Kind.MOVE_VEHICLE);
        }
        if (packet instanceof ServerboundKeepAlivePacket) {
            return SendView.of(SendView.Kind.KEEP_ALIVE);
        }
        if (packet instanceof ServerboundAcceptTeleportationPacket) {
            return SendView.of(SendView.Kind.ACCEPT_TELEPORT);
        }
        if (packet instanceof ServerboundPlayerAbilitiesPacket) {
            return SendView.of(SendView.Kind.ABILITIES);
        }
        return SendView.of(SendView.Kind.OTHER);
    }

    private static int flagsOf(Input input) {
        if (input == null) return 0;
        int flags = 0;
        if (input.forward()) flags |= SendView.INPUT_FORWARD;
        if (input.backward()) flags |= SendView.INPUT_BACKWARD;
        if (input.left()) flags |= SendView.INPUT_LEFT;
        if (input.right()) flags |= SendView.INPUT_RIGHT;
        if (input.jump()) flags |= SendView.INPUT_JUMP;
        if (input.shift()) flags |= SendView.INPUT_SHIFT;
        if (input.sprint()) flags |= SendView.INPUT_SPRINT;
        return flags;
    }

    private static Input inputOf(int flags) {
        return new Input(
            (flags & SendView.INPUT_FORWARD) != 0,
            (flags & SendView.INPUT_BACKWARD) != 0,
            (flags & SendView.INPUT_LEFT) != 0,
            (flags & SendView.INPUT_RIGHT) != 0,
            (flags & SendView.INPUT_JUMP) != 0,
            (flags & SendView.INPUT_SHIFT) != 0,
            (flags & SendView.INPUT_SPRINT) != 0
        );
    }
}
