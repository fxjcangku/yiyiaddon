package com.yiyiaddon.core;

import com.yiyiaddon.ui.render.SkiaScreen;
import com.yiyiaddon.ui.render.TooltipLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * 客户端聊天栏输出：统一加模块名前缀，并保证在渲染线程执行。
 *
 * <p><b>前缀格式（用户交互资产，禁止自行改动）：</b>{@code §f§l[模块名]§r§l} + 正文。
 * 模块名白色加粗，正文按状态色并保持加粗；与旧项目
 * {@code §c§l[yiyiaddon]§r§f§l[模块名]§r} 的唯一差别是按第十七章第 110 条去掉了
 * {@code yiyiaddon} 段。</p>
 *
 * <p><b>两条出口</b>：{@link #send} 恒定进聊天栏（世界情报、后台播报、多行卡片）；
 * {@link #ui} 是「玩家点出来的」一句回执 —— 停在扩展界面里时改走面板内顶部弹窗，
 * 因为原版在界面激活期间会把整个 HUD 收起来、聊天栏根本看不见（用户 2026-09-21
 * 「凡是 ui 点击都在 ui 里面弹窗」）。</p>
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

    /**
     * 一句「玩家点出来的」回执：停在扩展自己的界面里时发<b>面板内顶部弹窗</b>，否则照旧发聊天栏。
     *
     * <p><b>为什么需要它</b>（用户 2026-09-21：「凡是 ui 点击都在 ui 里面弹窗」）：原版在界面激活
     * 期间会把整个 HUD 收起来，聊天框与行动栏都看不见，玩家在面板里点一下、回执落进聊天栏等于
     * 没反馈，关掉窗口才一次性涌出来。判据见 {@code SkiaScreen#isOpen()}。</p>
     *
     * <p><b>只给单行回执用</b>：面板内弹窗是一行居中提示（约两秒淡出、不堆叠、不进聊天历史），
     * 装不下 {@code CommandMessageFormatter} 那种多行卡片，也不该装「管理员检测结果」这种
     * 需要留在聊天记录里的情报 —— 那些仍然走 {@link #send}。</p>
     *
     * @param moduleName 模块中文显示名，聊天栏路径用作前缀
     * @param text       正文；弹窗与聊天栏显示同一份文本
     */
    public static void ui(String moduleName, String text) {
        if (text == null) return;
        String message = prefix(moduleName) + text;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (SkiaScreen.isOpen()) {
                TooltipLayer.notify(message);
                return;
            }
            if (mc.player != null) mc.player.sendSystemMessage(Component.literal(message));
        });
    }

    /** 输出原始文本（自带颜色码与前缀，不再补任何内容）。 */
    public static void raw(String text) {
        if (text == null) return;
        rawComponent(Component.literal(text));
    }

    /**
     * 动作栏提示（{@code Gui#setOverlayMessage}）：不刷聊天框、不进聊天历史，两秒后自动消失。
     *
     * <p>用于「这次操作被模块挡下了」这类即时反馈 —— 玩家只会看到右键没反应，
     * 不提示会以为是卡住了（用户 2026-09-19）。</p>
     */
    public static void overlay(String text) {
        if (text == null) return;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.gui != null) mc.gui.setOverlayMessage(Component.literal(text), false);
        });
    }

    /**
     * 输出已构造好的组件（自带颜色码与前缀，不再补任何内容）。
     *
     * <p>给需要按真实字体测量对齐的排版器使用：对齐依赖字体测量与自定义字形，
     * 必须在组件层构造，不能先降级成纯文本。</p>
     */
    public static void rawComponent(Component text) {
        if (text == null) return;
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.player != null) mc.player.sendSystemMessage(text);
        });
    }
}
