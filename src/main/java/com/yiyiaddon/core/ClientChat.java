package com.yiyiaddon.core;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * 客户端聊天栏输出：统一加前缀，并保证在渲染线程执行。
 */
public final class ClientChat {

    public static final String PREFIX = "§8[§byiyiaddon§8] §r";

    private ClientChat() {
    }

    /** 输出一行提示；玩家不在世界内时静默丢弃。 */
    public static void send(String text) {
        if (text == null) return;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.player != null) mc.player.sendSystemMessage(Component.literal(PREFIX + text));
        });
    }

    /** 输出原始文本（自带颜色码，不再补前缀）。 */
    public static void raw(String text) {
        if (text == null) return;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.player != null) mc.player.sendSystemMessage(Component.literal(text));
        });
    }
}
