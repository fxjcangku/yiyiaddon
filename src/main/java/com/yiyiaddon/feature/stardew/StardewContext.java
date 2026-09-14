package com.yiyiaddon.feature.stardew;

import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Locale;

/**
 * 星露谷模块共享的「当前世界身份」工具。
 *
 * <p>serverKey 采用真实 {@code host:port}（{@link ResourcePackCache#currentServerKey()}
 * 统一规范化：补默认端口 25565、处理 IPv6、小写），与资源包缓存文件名
 * {@code <ServerKey>.zip} 完全一致；单人世界用固定 {@code singleplayer} 标识。
 * 维度取当前世界 {@code dimension().identifier()} 字符串。所有点位 / 记忆 / 档案 /
 * 选择器都以此隔离。</p>
 */
public final class StardewContext {

    private StardewContext() {
    }

    /** 当前服务器键（真实 host:port，或 singleplayer） */
    public static String serverKey() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.hasSingleplayerServer()) return "singleplayer";
        String key = ResourcePackCache.currentServerKey();
        if (key != null && !key.isBlank()) return key;
        return remoteAddressFallback();
    }

    /** 当前维度标识字符串；无世界返回 null */
    public static String dimension() {
        Minecraft mc = Minecraft.getInstance();
        return mc.level == null ? null : mc.level.dimension().identifier().toString();
    }

    /** 是否单人世界 */
    public static boolean isSingleplayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    /** 从网络连接兜底读取远程 host:port（直连 / ServerData 缺失时用） */
    public static String remoteAddressFallback() {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener conn = mc.getConnection();
        if (conn == null || conn.getConnection() == null) return "unknown";
        SocketAddress remote = conn.getConnection().getRemoteAddress();
        if (remote instanceof InetSocketAddress inet) {
            String host = inet.getAddress() != null ? inet.getAddress().getHostAddress() : inet.getHostString();
            return (host + ":" + inet.getPort()).toLowerCase(Locale.ROOT);
        }
        return "unknown";
    }
}
