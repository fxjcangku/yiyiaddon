package com.yiyiaddon.core;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * 客户端聊天栏输出：统一加模块名前缀，并保证在渲染线程执行。
 *
 * <p><b>前缀格式（用户交互资产，禁止自行改动）：</b>{@code §f§l[模块名]§r§l} + 正文。
 * 模块名白色加粗，正文按状态色并保持加粗；与旧项目
 * {@code §c§l[yiyiaddon]§r§f§l[模块名]§r} 的唯一差别是按第十七章第 110 条去掉了
 * {@code yiyiaddon} 段。</p>
 */
public final class ClientChat {

    /** 颜色代码，与 {@code 旧项目 YiyiaddonModule.stripColorCodes} 同规则 */
    private static final String COLOR_CODES = "§[0-9a-fk-orA-FK-OR]";

    private ClientChat() {
    }

    /**
     * 生成模块名前缀。
     *
     * @param moduleName 模块中文显示名；为空时退回 {@code 客户端}
     */
    public static String prefix(String moduleName) {
        String clean = moduleName == null ? "" : moduleName.replaceAll(COLOR_CODES, "");
        clean = clean.replace("[yiyiaddon]", "").trim();
        if (clean.isEmpty()) clean = "客户端";
        return "§f§l[" + clean + "]§r§l";
    }

    /** 输出一行模块消息；玩家不在世界内时静默丢弃。 */
    public static void send(String moduleName, String text) {
        if (text == null) return;
        String message = prefix(moduleName) + text;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.player != null) mc.player.sendSystemMessage(Component.literal(message));
        });
    }

    /** 输出原始文本（自带颜色码与前缀，不再补任何内容）。 */
    public static void raw(String text) {
        if (text == null) return;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.player != null) mc.player.sendSystemMessage(Component.literal(text));
        });
    }
}
