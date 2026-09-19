package com.yiyiaddon.platform.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;

/**
 * 主动断开当前服务器连接。
 *
 * <p><b>走的是原版同一条链路</b>：{@code ClientCommonPacketListenerImpl#handleDisconnect} →
 * {@code Connection#disconnect(Component)}，与原版客户端处理服务端断线包完全一致，因此断线界面
 * （{@code DisconnectedScreen}）的出现时机与内容也与原版一致。</p>
 *
 * <p><b>唯一实现（第 169 条）</b>：管理员检测的「§c立即断线」与自动重连模块的「测试重连」
 * 都调这里，不各自复制一份断线包构造。</p>
 */
public final class ConnectionCloser {

    private ConnectionCloser() {
    }

    /** 当前是否处于服务器连接中（未进服 / 单人存档 / 已在断线流程中时为 {@code false}） */
    public static boolean hasConnection() {
        Minecraft mc = Minecraft.getInstance();
        return mc != null && mc.player != null && mc.player.connection != null;
    }

    /**
     * 用给定原因断开连接。
     *
     * <p>无连接时静默返回 {@code false}，不产生任何播报与副作用。</p>
     *
     * @param reason 断线界面上的原因文本（含模块前缀与颜色码）
     * @return 是否真的发出了断线包
     */
    public static boolean disconnect(Component reason) {
        if (reason == null) return false;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return false;
        ClientPacketListener connection = mc.player.connection;
        if (connection == null) return false;

        connection.handleDisconnect(new ClientboundDisconnectPacket(reason));
        return true;
    }
}
