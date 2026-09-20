package com.yiyiaddon.core.resourcepack;

import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;

import java.util.UUID;

/**
 * 资源包接管闸门：把「是否由本模组接管服务器资源包推送」的判定权交给模块，Mixin 只做执行。
 *
 * <p><b>为什么需要这一层：</b>原版 {@code handleResourcePackPush} 没有任何可作为订阅点的
 * 事件，接管（拒绝 / 接受并伪装加载完成）必须在原版处理之前同步决定，因此只能注入 Mixin。
 * 但 Mixin 属于框架层，不允许反向依赖业务模块（否则每加一个资源包策略都要改 Mixin）。
 * 折中做法：模块在启用时把自己的处理器注册进来，Mixin 只问闸门「要不要取消原版流程」。</p>
 *
 * <p><b>响应必须走 {@link Responder}</b>（2026-09-21 修）：响应包要发回<b>收到这次推送的那条连接</b>。
 * 早先接管方拿 {@code ClientPacketSender} 按「当前游戏连接」发包，而原版在<b>配置阶段</b>就下发资源包
 * 推送，那时玩家实体尚未创建、{@code Minecraft#getConnection()} 为 {@code null} —— 包根本发不出去，
 * 服务端就一直在等资源包处理完成，客户端卡死在「重新配置中…」（跨服回到子服时必现）。</p>
 *
 * <p><b>线程：</b>{@link #handle} 在 Mixin 的 {@code HEAD} 注入点被调用，可能仍在网络线程
 * （早于原版的线程切换）。处理器实现只允许做「读设置 + 发响应包（{@code Connection.send}
 * 本身线程安全）+ 交给异步提示通道」这三件事，禁止触碰客户端世界与界面。</p>
 *
 * <p><b>唯一性：</b>同一时刻只允许一个所有者注册（资源包策略是全局行为，两个模块同时接管会
 * 互相打架）。后注册者覆盖前者，且只允许原所有者注销。</p>
 */
public final class ResourcePackGate {

    /**
     * 响应通道：把资源包响应发回「收到这次推送的那条连接」。
     *
     * <p>由框架层实现（Mixin 用推送方的 {@code send}），配置阶段与游戏阶段都能发出。</p>
     */
    @FunctionalInterface
    public interface Responder {

        void respond(UUID packId, ServerboundResourcePackPacket.Action action);
    }

    /** 资源包推送处理器：返回 {@code true} 表示已接管，框架须取消原版处理 */
    @FunctionalInterface
    public interface Handler {

        boolean handle(UUID packId, String url, String hash, Responder responder);
    }

    private static volatile String ownerId;
    private static volatile Handler handler;

    private ResourcePackGate() {
    }

    /** 注册处理器（模块启用时调用） */
    public static void register(String owner, Handler newHandler) {
        if (owner == null || owner.isBlank() || newHandler == null) return;
        ownerId = owner;
        handler = newHandler;
    }

    /** 注销处理器（模块关闭时调用）；只有当前所有者本人能注销自己 */
    public static void unregister(String owner) {
        if (owner == null || !owner.equals(ownerId)) return;
        handler = null;
        ownerId = null;
    }

    public static boolean isRegistered() {
        return handler != null;
    }

    /**
     * 询问是否接管这次推送（Mixin 调用）。
     *
     * <p>处理器抛异常时一律返回 {@code false}：接管失败必须退回原版流程，
     * 否则玩家会卡在资源包界面进不去服务器。</p>
     *
     * @param responder 响应通道（见 {@link Responder}）；为 {@code null} 时直接放行原版
     */
    public static boolean handle(UUID packId, String url, String hash, Responder responder) {
        Handler current = handler;
        if (current == null || responder == null) return false;
        try {
            return current.handle(packId, url, hash, responder);
        } catch (Throwable ignored) {
            return false;
        }
    }
}
