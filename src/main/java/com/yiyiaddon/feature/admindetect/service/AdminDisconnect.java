package com.yiyiaddon.feature.admindetect.service;

import com.yiyiaddon.core.ClientChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;

/**
 * 断线保命：合并进来的旧「自动断线」核心。
 *
 * <p><b>旧项目来源</b>：{@code autodisconnect/CometDisconnectModule.java}（77 行）。断线实体动作
 * 原来是它的 {@code public static void disconnect(String reason)}（{@code :53-61}）；用户 2026-09-16
 * 裁定两个模块合并成一个「管理员检测」，因此该静态入口整体搬到这里，旧模块不再存在。</p>
 *
 * <p><b>与旧项目的差异（用户 2026-09-16 拍板，勿自行改回）</b>：删掉旧实现里「断线前强制关闭自动重连」
 * 那一步（旧 {@code :56-57} 关的是第三方框架的 {@code AutoReconnect}）。本项目零第三方依赖，
 * 且 grep 确认本项目<b>没有</b>任何自动重连实现，因此这一步在新项目里无事可做、行为零差异。</p>
 *
 * <p><b>断线文案</b>：旧原文 {@code §c§l[yiyiaddon]§r §f自动断线 §8▸ §c<原因>}。按第 110/119 条
 * （禁止显示模组自身名称）去掉 {@code yiyiaddon} 段，前缀改由 {@link ClientChat#prefix(String)}
 * 生成 {@code §f§l[管理员检测]§r§l}，其余（空格、{@code §f自动断线}、{@code §8▸}、
 * 状态色 {@code §c}）逐字保留。</p>
 */
public final class AdminDisconnect {

    private AdminDisconnect() {
    }

    /**
     * 断开当前服务器连接。
     *
     * <p>无连接（未进服 / 单人 / 已在断线流程中）时静默返回 —— 与旧实现
     * {@code CometDisconnectModule.disconnect} 首行的静默 return 一致，不产生任何播报。</p>
     *
     * @param moduleName 模块中文显示名，仅用于断线界面上的前缀
     * @param reason     断线原因，显示在断开界面上
     * @return 是否真的发出了断线包
     */
    public static boolean disconnect(String moduleName, String reason) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return false;
        ClientPacketListener connection = mc.player.connection;
        if (connection == null) return false;

        Component text = Component.literal(ClientChat.prefix(moduleName) + " §f自动断线 §8▸ §c" + reason);
        // 与原版客户端处理服务端断线包同一条链路：ClientCommonPacketListenerImpl#handleDisconnect
        // → Connection#disconnect(Component)，因此断开界面的行为与原版一致。
        connection.handleDisconnect(new ClientboundDisconnectPacket(text));
        return true;
    }
}
