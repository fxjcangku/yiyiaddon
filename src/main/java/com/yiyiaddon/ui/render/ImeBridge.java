package com.yiyiaddon.ui.render;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

/**
 * IME / 文本输入焦点桥。
 *
 * <p>26.1.2 提供了原生的文本输入管理：{@code Minecraft#onTextInputFocusChange} 会切换
 * 文本输入模式并在焦点变化时重投递 IME 预编辑事件。界面侧只需要在获得/失去文本框焦点时
 * 调用这里，不需要再自行禁用 Windows IMM。</p>
 *
 * <p><b>为什么还要自己开输入法、而且每帧重申：</b>{@code TextInputManager.startTextInput()}
 * 里那句 {@code if (imeRequested)} —— 它只在「上一轮输入状态中已确认为开着」时才真正启用输入法，
 * 而 {@code tickOutsideTextInput()} 又会在任何非输入状态下把它关掉。于是<b>第一次</b>聚焦输入框时
 * 输入法必然是关的：拼音敲进去只有英文字母，中文一个字也上不来（实机反馈「搜索输入不了中文」）。
 * 只在聚焦那一刻补一脚不够稳（原版 tick 可能紧跟着抢回去），所以每帧再重申一次
 * （{@link #keepAlive()}，由 {@code SkiaScreen} 每帧调用）。</p>
 */
public final class ImeBridge {

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 输入法开关（GLFW 的 IME 输入模式号）。
     *
     * <p>26.1.2 用的是 Mojang 打过 IME 补丁的 GLFW，这个模式号在
     * {@code com.mojang.blaze3d.platform.TextInputManager} 里也是硬编码的同一个值，
     * 所以这里原样引用，不引第三方常量。</p>
     */
    private static final int GLFW_IME_MODE = 208903;

    private static boolean active;
    /** 上一次真正推给原版的界面：换界面后即使开关状态相同也要重推（新界面收不到旧界面的预编辑事件） */
    private static Screen pushed;
    /** 本次输入会话是否已经确认过输入法真的开着（只用于诊断日志，不参与逻辑） */
    private static boolean imeConfirmed;
    private static boolean preeditSeen;
    private static boolean committedSeen;

    private ImeBridge() {
    }

    public static void setTextInputActive(boolean value) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen screen = minecraft == null ? null : minecraft.screen;
        if (active == value && pushed == screen) {
            return;
        }
        active = value;
        pushed = screen;
        if (minecraft == null || screen == null) {
            return;
        }
        minecraft.onTextInputFocusChange(screen, value);
        if (value) {
            imeConfirmed = false;
            preeditSeen = false;
            forceIme(minecraft);
        }
    }

    /**
     * 每帧重申一次输入法开关。
     *
     * <p>原版的 {@code tickOutsideTextInput()} 只要不处于文本输入状态就把 IME 关掉，
     * 而它判断「是否处于文本输入状态」用的是自己那份 {@code textInputEnabled}；
     * 一旦两边节奏错开，只在聚焦那一刻设置就会被它抢回去。这里每帧重申，代价只有一次 GLFW 调用。</p>
     */
    public static void keepAlive() {
        if (!active) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.screen == null) return;
        forceIme(minecraft);
    }

    private static void forceIme(Minecraft minecraft) {
        long handle = minecraft.getWindow().handle();
        GLFW.glfwSetInputMode(handle, GLFW_IME_MODE, GLFW.GLFW_TRUE);
        // 让原版重读一次真实状态（notifyIMEChanged 只置一个「缓存已失效」标志）：
        // 否则 TextInputManager 的 cachedIMEStatus 仍是 false，它离开文本输入状态时
        // 不会把输入法关回去 —— 关掉面板后中英文输入法会赖着不走，玩游戏时抢按键。
        minecraft.textInputManager().notifyIMEChanged();
        // 读回一次确认真的开了：这是「中文打不进来」唯一没法静态判断的一环，留一行日志以便核对
        if (!imeConfirmed) {
            imeConfirmed = true;
            LOGGER.info("[yiyiaddon] IME 开关：请求开启，读回模式={}", GLFW.glfwGetInputMode(handle, GLFW_IME_MODE));
        }
    }

    /** IME 提交出非 ASCII 字符（中文真正进了输入框）：只记第一次，便于诊断时核对 */
    public static void noteCommitted(int codepoint) {
        if (committedSeen || codepoint <= 0x7F) return;
        committedSeen = true;
        LOGGER.info("[yiyiaddon] IME 已提交非 ASCII 字符：U+{}",
                Integer.toHexString(codepoint).toUpperCase(java.util.Locale.ROOT));
    }

    /** IME 预编辑事件到达（说明输入法确实在工作）：只记第一次，便于诊断时核对 */
    public static void notePreedit() {
        if (preeditSeen) return;
        preeditSeen = true;
        LOGGER.info("[yiyiaddon] IME 预编辑事件已到达（输入法处于工作状态）");
    }

    public static boolean isTextInputActive() {
        return active;
    }

    /** 界面销毁时强制复位，避免焦点残留。 */
    public static void reset() {
        setTextInputActive(false);
    }
}
