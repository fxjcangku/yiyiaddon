package com.yiyiaddon.core.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.world.BossEvent;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端事件派发器：模组内唯一注册 Fabric 事件的地方。
 *
 * <p>七个事件来源：</p>
 * <ul>
 *     <li>每刻 / 进服 / 断线：Fabric 生命周期事件，主线程直接派发；</li>
 *     <li>打开界面 / 关闭界面：Fabric 界面事件，主线程直接派发；</li>
 *     <li>收包 / 发包：由 Mixin 在网络线程入队，主线程每刻出队派发；收包时另派生
 *         {@link ClientEventType#SERVER_TEXT}，把界面文本类包的原文交给订阅者。</li>
 * </ul>
 *
 * <p><b>为什么收发包要过队列：</b>网络读写发生在 Netty 线程上，若直接在网络线程回调模块，
 * 任何阻塞都会卡住整条连接。入队后由主线程派发，模块侧的行为与其余事件完全一致，
 * 代价是最多延迟一刻。</p>
 *
 * <p>派发顺序固定：先出队数据包（收包先于发包按实际发生顺序），再派发每刻事件。</p>
 */
public final class EventDispatcher {

    /** 单刻最多派发的数据包数量，防止网络突发时长时间占用主线程 */
    private static final int MAX_PACKETS_PER_TICK = 512;

    /** 队列上限：超过即丢弃并计数，避免客户端卡顿期间无上限堆积 */
    private static final int QUEUE_LIMIT = 4096;

    /**
     * 网络线程入队的原始记录：方向 + 包类名 + 包引用。
     *
     * <p>包引用只在「入队 → 主线程派发」这一小段内保留：派发时抽出类名与
     * {@link ServerTextEvent}，随后立即随记录一起被丢弃，核心与模块都不长期持有包对象。</p>
     */
    private record QueuedPacket(boolean inbound, String packetName, Packet<?> packet) {
    }

    private static final ConcurrentLinkedQueue<QueuedPacket> PACKET_QUEUE = new ConcurrentLinkedQueue<>();
    private static final AtomicInteger DROPPED_PACKETS = new AtomicInteger();

    /** 已注册关闭监听的界面（按引用判重，界面重建后重新注册） */
    private static final Set<Screen> TRACKED_SCREENS =
            Collections.synchronizedSet(Collections.newSetFromMap(new IdentityHashMap<>()));

    private static boolean initialized;

    private EventDispatcher() {
    }

    /** 注册全部事件来源；重复调用无效 */
    public static void init() {
        if (initialized) return;
        initialized = true;

        ClientTickEvents.END_CLIENT_TICK.register(EventDispatcher::onClientTick);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) ->
                ClientEventBus.publish(ClientEvent.of(ClientEventType.JOIN_SERVER)));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                ClientEventBus.publish(ClientEvent.of(ClientEventType.DISCONNECT)));
        ScreenEvents.AFTER_INIT.register(EventDispatcher::onScreenInit);
    }

    // ── 主线程派发 ──

    private static void onClientTick(Minecraft client) {
        dispatchQueuedPackets();
        ClientEventBus.publish(ClientEvent.of(ClientEventType.TICK, client == null ? "" : client.getClass().getName()));
    }

    private static void onScreenInit(Minecraft client, Screen screen, int width, int height) {
        ClientEvent event = ClientEvent.cancellable(ClientEventType.SCREEN_OPEN, screen.getClass().getName());
        ClientEventBus.publish(event);
        if (event.isCancelled()) {
            // 模块判定该界面不该弹（例如后台物流的箱子界面只取消渲染、继续用 containerMenu 发包）
            client.setScreen(null);
            return;
        }
        if (!TRACKED_SCREENS.add(screen)) return;
        ScreenEvents.remove(screen).register(removed -> {
            TRACKED_SCREENS.remove(removed);
            ClientEventBus.publish(ClientEvent.of(ClientEventType.SCREEN_CLOSE, removed.getClass().getName()));
        });
    }

    private static void dispatchQueuedPackets() {
        for (int i = 0; i < MAX_PACKETS_PER_TICK; i++) {
            QueuedPacket packet = PACKET_QUEUE.poll();
            if (packet == null) return;
            ClientEventBus.publish(ClientEvent.of(
                    packet.inbound() ? ClientEventType.PACKET_RECEIVE : ClientEventType.PACKET_SEND,
                    packet.packetName()));
            if (packet.inbound()) publishServerText(packet.packet());
        }
    }

    // ── 服务器文本抽取（模块拿不到包对象，只拿到文本） ──

    /** 把界面文本类数据包抽取为 {@link ServerTextEvent}；非文本包不做任何事 */
    private static void publishServerText(Packet<?> packet) {
        if (packet == null) return;
        if (packet instanceof ClientboundSetActionBarTextPacket p) {
            publishText(ServerTextEvent.ACTION_BAR, p.text(), "");
        } else if (packet instanceof ClientboundSetTitleTextPacket p) {
            publishText(ServerTextEvent.TITLE, p.text(), "");
        } else if (packet instanceof ClientboundSetSubtitleTextPacket p) {
            publishText(ServerTextEvent.SUBTITLE, p.text(), "");
        } else if (packet instanceof ClientboundSystemChatPacket p) {
            publishText(p.overlay() ? ServerTextEvent.CHAT_OVERLAY : ServerTextEvent.CHAT, p.content(), "");
        } else if (packet instanceof ClientboundTabListPacket p) {
            publishText(ServerTextEvent.TAB_LIST_HEADER, p.header(), "");
            publishText(ServerTextEvent.TAB_LIST_FOOTER, p.footer(), "");
        } else if (packet instanceof ClientboundSetObjectivePacket p) {
            boolean removed = p.getMethod() == ClientboundSetObjectivePacket.METHOD_REMOVE;
            publishText(removed ? ServerTextEvent.OBJECTIVE_REMOVED : ServerTextEvent.OBJECTIVE_TITLE,
                    p.getDisplayName(), String.valueOf(p.getObjectiveName()));
        } else if (packet instanceof ClientboundSetScorePacket p) {
            String objective = String.valueOf(p.objectiveName());
            Component display = p.display().orElse(null);
            if (display != null) {
                publishText(ServerTextEvent.SCORE_DISPLAY, display, objective);
            } else {
                publishText(ServerTextEvent.SCORE_OWNER, Component.literal(p.owner()), objective);
            }
        } else if (packet instanceof ClientboundSetPlayerTeamPacket p) {
            p.getParameters().ifPresent(parameters -> {
                publishText(ServerTextEvent.TEAM_DISPLAY, parameters.getDisplayName(), "");
                publishText(ServerTextEvent.TEAM_PREFIX, parameters.getPlayerPrefix(), "");
                publishText(ServerTextEvent.TEAM_SUFFIX, parameters.getPlayerSuffix(), "");
            });
        } else if (packet instanceof ClientboundBossEventPacket p) {
            p.dispatch(new ClientboundBossEventPacket.Handler() {
                @Override
                public void add(UUID id, Component name, float progress, BossEvent.BossBarColor color,
                                BossEvent.BossBarOverlay overlay, boolean darkenScreen,
                                boolean playMusic, boolean createWorldFog) {
                    publishText(ServerTextEvent.BOSS_BAR, name, "");
                }

                @Override
                public void updateName(UUID id, Component name) {
                    publishText(ServerTextEvent.BOSS_BAR, name, "");
                }
            });
        }
    }

    /** 发布一条服务器文本；文本为空时不发布（避免模块收到空证据） */
    private static void publishText(String channel, Component text, String context) {
        if (text == null) return;
        ClientEventBus.publish(ClientEvent.ofText(new ServerTextEvent(channel, text, context)));
    }

    // ── 网络线程入口（由 Mixin 调用，禁止在此做除入队之外的任何事情） ──

    /** 收到数据包 */
    public static void enqueueReceived(Packet<?> packet) {
        enqueue(true, packet);
    }

    /** 发出数据包 */
    public static void enqueueSent(Packet<?> packet) {
        enqueue(false, packet);
    }

    private static void enqueue(boolean inbound, Packet<?> packet) {
        if (packet == null) return;
        if (PACKET_QUEUE.size() >= QUEUE_LIMIT) {
            DROPPED_PACKETS.incrementAndGet();
            return;
        }
        PACKET_QUEUE.add(new QueuedPacket(inbound, packet.getClass().getName(), packet));
    }

    // ── 诊断 ──

    public static int pendingPacketCount() {
        return PACKET_QUEUE.size();
    }

    /** 因队列溢出被丢弃的数据包数量；长期大于 0 说明客户端主线程被长时间占用 */
    public static int droppedPacketCount() {
        return DROPPED_PACKETS.get();
    }

    /** 断线时清空待派发队列，避免把上一台服务器的包派发给新会话 */
    public static void clearPendingPackets() {
        PACKET_QUEUE.clear();
    }
}
