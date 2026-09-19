package com.yiyiaddon.core.event;

import com.yiyiaddon.core.TickRateMonitor;
import com.yiyiaddon.core.net.BlockWatchService;
import com.yiyiaddon.core.net.SendDelayService;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandSignedPacket;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Locale;
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
 *         {@link ClientEventType#SERVER_TEXT}，把界面文本类包的原文交给订阅者，
 *         发包时另派生 {@link ClientEventType#CLIENT_COMMAND}，把聊天指令原文交给订阅者。</li>
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
    private record QueuedPacket(boolean inbound, String packetName, Packet<?> packet,
                                ServerPositionEvent position, ServerBlockEvent block, String channel,
                                String clientChat) {
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
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            clearSessionCaches();
            ClientEventBus.publish(ClientEvent.of(ClientEventType.DISCONNECT));
        });
        ScreenEvents.AFTER_INIT.register(EventDispatcher::onScreenInit);
    }

    /** 断线时清空与连接绑定的运行时缓存，避免把上一台服务器的数据带进新会话 */
    private static void clearSessionCaches() {
        clearPendingPackets();
        TickRateMonitor.clear();
        SendDelayService.clear();
        BlockWatchService.clearAll();
    }

    // ── 主线程派发 ──

    private static void onClientTick(Minecraft client) {
        com.yiyiaddon.service.update.UpdateService.tick(client);
        dispatchQueuedPackets();
        TickRateMonitor.sample();
        ClientEventBus.publish(ClientEvent.of(ClientEventType.TICK, client == null ? "" : client.getClass().getName()));
    }

    private static void onScreenInit(Minecraft client, Screen screen, int width, int height) {
        // 界面已经建好了，这里只补「关闭追踪」；判定界面该不该弹在 onScreenOpen（setScreen 头部，见其注释）
        if (!TRACKED_SCREENS.add(screen)) return;
        ScreenEvents.remove(screen).register(removed -> {
            TRACKED_SCREENS.remove(removed);
            ClientEventBus.publish(ClientEvent.of(ClientEventType.SCREEN_CLOSE, removed.getClass().getName()));
        });
    }

    /**
     * 界面**创建之前**的拦截点（{@code MinecraftSetScreenMixin} 在 {@code Minecraft#setScreen} 头部调用）。
     *
     * <p><b>为什么必须在头部</b>（用户 2026-09-19：「开潜影盒子会抢我鼠标，之前我记得不会的呀」）：
     * {@code Minecraft#setScreen} 的顺序是「先 {@code mouseHandler.releaseMouse()} + 建界面，最后才
     * {@code screen.init(...)}」——原来挂在 {@code ScreenEvents.AFTER_INIT} 上再取消，界面已经建好、
     * 鼠标已经交还系统，只能再调一次 {@code setScreen(null)} 收场：实机就是「开箱子时鼠标被抢一下」；
     * 更糟的是那次嵌套调用在 {@code level != null} 时会把<b>聊天界面</b>恢复出来
     * （{@code setScreen(null)} 里 {@code screen = gui.getChat().restoreChatScreen()}）。
     * 在头部取消则整个 {@code setScreen} 直接不执行 —— 界面根本不建、鼠标一动不动，
     * 与旧项目 {@code OpenScreenEvent} 同一时机。</p>
     *
     * @return true 表示该界面已被模块取消（例如后台物流的箱子界面只取消显示、继续用 containerMenu 发包）
     */
    public static boolean onScreenOpen(Screen screen) {
        if (screen == null) return false;
        ClientEvent event = ClientEvent.cancellable(ClientEventType.SCREEN_OPEN, screen.getClass().getName());
        ClientEventBus.publish(event);
        return event.isCancelled();
    }

    /**
     * 原版内部收尾要关界面时，别把玩家自己开着的界面一起关掉
     * （用户 2026-09-19：「传送还是会关掉我这个界面」）。
     *
     * <p>调用点见 {@code LocalPlayerScreenGuardMixin}：重生式传送时的
     * {@code LocalPlayer#clientSideCloseContainer()}（{@code handleRespawn} 会先
     * {@code oldPlayer.closeContainer()}）与进传送门时的 {@code handlePortalTransitionEffect()}。
     * 两处都是原版自己的清理动作，但都会无条件 {@code setScreen(null)}。</p>
     *
     * <p>保留范围＝<b>玩家自己开的非容器界面</b>（游戏菜单 / 聊天 / 我们自己的控制台），
     * 死亡界面与容器界面照旧关。玩家按 ESC 关菜单走 {@code Screen#onClose} → 不经过这两个调用点，
     * 所以不会被吞掉。</p>
     */
    public static void closeScreenUnlessPlayerOwned(Minecraft client, Screen screen) {
        if (client == null) return;
        Screen current = client.screen;
        if (screen == null && current != null
            && !(current instanceof AbstractContainerScreen<?>)
            && !(current instanceof DeathScreen)) {
            return;
        }
        client.setScreen(screen);
    }

    private static void dispatchQueuedPackets() {
        for (int i = 0; i < MAX_PACKETS_PER_TICK; i++) {
            QueuedPacket packet = PACKET_QUEUE.poll();
            if (packet == null) return;
            ClientEventBus.publish(ClientEvent.of(
                    packet.inbound() ? ClientEventType.PACKET_RECEIVE : ClientEventType.PACKET_SEND,
                    packet.packetName()));
            if (packet.inbound()) {
                publishServerText(packet.packet());
                if (packet.position() != null) {
                    ClientEventBus.publish(ClientEvent.ofPosition(packet.position()));
                }
                if (packet.block() != null) {
                    ClientEventBus.publish(ClientEvent.ofBlock(packet.block()));
                }
                if (packet.channel() != null) {
                    ClientEventBus.publish(ClientEvent.of(ClientEventType.SERVER_CHANNEL, packet.channel()));
                }
            } else {
                publishClientCommand(packet.packet());
                if (packet.clientChat() != null) {
                    ClientEventBus.publish(ClientEvent.of(ClientEventType.CLIENT_CHAT, packet.clientChat()));
                }
            }
        }
    }

    // ── 客户端指令抽取（模块拿不到包对象，只拿到指令原文） ──

    /**
     * 把客户端发出的聊天指令抽取为 {@link ClientEventType#CLIENT_COMMAND}；非指令包不做任何事。
     *
     * <p>两个包都要覆盖：{@code ClientPacketListener#sendCommand}（{@code :2654-2668}）在指令
     * <b>没有参数</b>时发无签名的 {@link ServerboundChatCommandPacket}（{@code /home}、{@code /spawn}
     * 这类），<b>带参数</b>时发 {@link ServerboundChatCommandSignedPacket}（{@code /res tp 矿区} 这类）。
     * 只认前者会漏掉一大半手动指令。</p>
     *
     * <p>只包含玩家（和模块自己）真正发给服务端的指令：纯客户端指令（{@code .wk} 之类）在
     * 进聊天栏时就被客户端指令系统拦下，不会产生这两个包，因此不会误报。</p>
     *
     * <p>「是玩家敲的」还是「模块自己发的」不在这里区分——那要问发起方（自动挖矿用
     * {@code ServerCommandRunner#isOwnCommand}），核心层不认识任何业务。</p>
     */
    private static void publishClientCommand(Packet<?> packet) {
        if (packet instanceof ServerboundChatCommandPacket p) {
            ClientEventBus.publish(ClientEvent.of(ClientEventType.CLIENT_COMMAND, p.command()));
        } else if (packet instanceof ServerboundChatCommandSignedPacket p) {
            ClientEventBus.publish(ClientEvent.of(ClientEventType.CLIENT_COMMAND, p.command()));
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

    /**
     * 入队一条「被发包闸门拦下的聊天消息」。
     *
     * <p>调用点在网络线程（{@code SendInterceptor}）：模块拿不到包对象，但排队重发需要原文，
     * 因此由核心把原文抽出来交给订阅者（{@link ClientEventType#CLIENT_CHAT}）。</p>
     */
    public static void enqueueClientChat(String text) {
        if (text == null || text.isEmpty()) return;
        if (PACKET_QUEUE.size() >= QUEUE_LIMIT) {
            DROPPED_PACKETS.incrementAndGet();
            return;
        }
        PACKET_QUEUE.add(new QueuedPacket(false, "", null, null, null, null, text));
    }

    private static void enqueue(boolean inbound, Packet<?> packet) {
        if (packet == null) return;
        if (PACKET_QUEUE.size() >= QUEUE_LIMIT) {
            DROPPED_PACKETS.incrementAndGet();
            return;
        }
        PACKET_QUEUE.add(new QueuedPacket(inbound, packet.getClass().getName(), packet,
            inbound ? extractServerPosition(packet) : null,
            inbound ? BlockWatchService.match(packet) : null,
            inbound ? extractChannel(packet) : null,
            null));
    }

    /**
     * 抽取插件自定义负载的频道 id（网络线程）。
     *
     * <p>原版 {@code minecraft:brand} / {@code minecraft:register} / {@code minecraft:unregister}
     * 不派发：前三者是原版协议本身的频道，与服务端检测的指纹无关（旧实现同样剔除）。</p>
     */
    private static String extractChannel(Packet<?> packet) {
        if (!(packet instanceof ClientboundCustomPayloadPacket payloadPacket)) return null;
        String id = payloadPacket.payload().type().id().toString().toLowerCase(Locale.ROOT);
        if ("minecraft:brand".equals(id) || "minecraft:register".equals(id)
            || "minecraft:unregister".equals(id)) {
            return null;
        }
        return id;
    }

    /**
     * 收包瞬间换算服务端权威位置（网络线程），主线程派发时直接给出绝对坐标。
     *
     * <p><b>为什么必须在入队时换算：</b>相对修正的基准是「包到达瞬间客户端的
     * {@code PositionMoveRotation}」，与旧实现（网络线程事件里读玩家状态）同刻；
     * 若推迟到主线程出队再算，最多晚一刻，玩家移动会让基准漂移。</p>
     *
     * <p>换算与旧项目两处逐字相同的实现同源（传送回弹验证 / 战术拉回判定），
     * 这里是唯一一份（第 169 条）。非位置包返回 {@code null}，不产生任何事件。</p>
     */
    private static ServerPositionEvent extractServerPosition(Packet<?> packet) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return null;
        if (packet instanceof ClientboundPlayerPositionPacket p) {
            Vec3 absolute = PositionMoveRotation.calculateAbsolute(
                PositionMoveRotation.of(client.player), p.change(), p.relatives()).position();
            return new ServerPositionEvent(absolute, false, client.player.position().distanceTo(absolute));
        }
        if (packet instanceof ClientboundMoveVehiclePacket v) {
            // 载具包不参与拉回判定（旧实现只处理玩家位置包），因此不算距离
            return new ServerPositionEvent(v.position(), true, 0.0);
        }
        return null;
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
