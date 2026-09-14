package com.yiyiaddon.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;

import java.util.List;
import java.util.function.Supplier;

/**
 * 客户端所处阶段的适配：主菜单 / 单人世界 / 多人服务器，以及服务器信息与延迟。
 */
public final class GameProbe {

    /** 已启用功能名来源；模组自身的功能系统尚未建立时保持为空。 */
    private static volatile Supplier<List<String>> moduleSource = List::of;

    private GameProbe() {
    }

    /**
     * 注册「已启用功能名」来源，供后端统计功能使用情况。
     * 后续接入功能系统时调用一次即可，不需要改动上报逻辑。
     */
    public static void setModuleSource(Supplier<List<String>> source) {
        moduleSource = source == null ? List::of : source;
    }

    public static List<String> enabledModules() {
        try {
            List<String> modules = moduleSource.get();
            return modules == null ? List.of() : modules;
        } catch (Exception e) {
            return List.of();
        }
    }

    /** 玩家当前所处阶段，取值与后端约定一致。 */
    public static String status() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return "menu";
        return isMultiplayer() ? "multiplayer" : "singleplayer";
    }

    /** 是否已进入世界（含单人）。 */
    public static boolean inWorld() {
        return Minecraft.getInstance().player != null;
    }

    public static boolean isMultiplayer() {
        Minecraft mc = Minecraft.getInstance();
        return mc.level != null && (mc.getCurrentServer() != null || !mc.isLocalServer());
    }

    /** 是否处于单人世界（集成服务器）。单人世界不存在「服务器资源包」概念。 */
    public static boolean isSingleplayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    /** 服务器地址；未连接时为 {@code null}。 */
    public static String serverIp() {
        Minecraft mc = Minecraft.getInstance();
        ServerData server = mc.getCurrentServer();
        if (server != null && server.ip != null && !server.ip.isBlank()) return server.ip;
        return mc.isLocalServer() ? "localhost" : null;
    }

    /** 服务器名称；主菜单与单人有各自的显示名。 */
    public static String serverName() {
        Minecraft mc = Minecraft.getInstance();
        ServerData server = mc.getCurrentServer();
        if (server != null && server.name != null && !server.name.isBlank()) return server.name;
        return mc.player == null ? "主菜单" : "单人世界";
    }

    /** 到当前服务器的连接延迟（毫秒）；不可用时返回 {@code null}。 */
    public static Integer serverLatency() {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();
        if (connection == null || mc.player == null) return null;
        PlayerInfo info = connection.getPlayerInfo(mc.player.getUUID());
        return info == null ? null : info.getLatency();
    }
}
