package com.yiyiaddon.feature.admindetect.service;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.platform.network.ConnectionCloser;
import com.yiyiaddon.service.reconnect.ReconnectSuppression;
import net.minecraft.network.chat.Component;

/**
 * 断线保命：合并进来的旧「自动断线」核心。
 *
 * <p><b>旧项目来源</b>：{@code autodisconnect/CometDisconnectModule.java}（77 行）。断线实体动作
 * 原来是它的 {@code public static void disconnect(String reason)}（{@code :53-61}）；用户 2026-09-16
 * 裁定两个模块合并成一个「管理员检测」，因此该静态入口整体搬到这里，旧模块不再存在。</p>
 *
 * <p><b>旧「断线前先关掉自动重连」那一步的对应物</b>：旧实现（旧 {@code :56-57}）关的是第三方框架的
 * {@code AutoReconnect}，本项目零第三方依赖、没有该对象。但本项目现在<b>有自己的自动重连</b>
 * （自动登入模块与独立「自动重连」模块），保命断线若被它立刻连回去就等于白按，因此改为在断线前
 * 开一个 {@link ReconnectSuppression} 抑制窗口：窗口内的断线不触发自动重连。作用与旧那一步一致
 * （让主动断线断得掉），实现方式随本项目形态改变。</p>
 *
 * <p><b>断线动作</b>走共用件 {@link ConnectionCloser}（与原版处理服务端断线包同一条链路），
 * 与自动重连模块的「测试重连」共用同一份实现（第 169 条）。</p>
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
        if (!ConnectionCloser.hasConnection()) return false;

        // 保命断线不允许被自动重连撤销：先开抑制窗口，再发断线包
        ReconnectSuppression.suppress(0L);

        Component text = Component.literal(ClientChat.prefix(moduleName) + " §f自动断线 §8▸ §c" + reason);
        // 与原版客户端处理服务端断线包同一条链路：ClientCommonPacketListenerImpl#handleDisconnect
        // → Connection#disconnect(Component)，因此断开界面的行为与原版一致。
        return ConnectionCloser.disconnect(text);
    }
}
