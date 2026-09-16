package com.yiyiaddon.ui.render;

import com.mojang.blaze3d.platform.TextInputManager;
import com.mojang.logging.LogUtils;
import com.yiyiaddon.mixin.client.MinecraftTextInputAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

/**
 * IME / 文本输入焦点桥。
 *
 * <p>26.1.2 自带文本输入管理：{@code Minecraft#onTextInputFocusChange} 会切换文本输入模式，
 * 并在焦点变化时把上一次的 IME 预编辑事件重投给目标元素。界面侧只需要在获得 / 失去文本框焦点时
 * 调这里，其余一律不要插手。</p>
 *
 * <p><b>IME 开关一律交给原版，只做一件事：把「正在文本输入」这个事实告诉它。</b>
 * 2026-09-16 实测定性：{@code TextInputManager} 只在 {@code textInputEnabled} 为真时才停止
 * {@code tickOutsideTextInput()} 里每 tick 的 {@code setIMEInputMode(false)}；不告诉它，输入法就被
 * 每 tick 关一次，玩家按切换键也起不来（当日日志：读回恒为 {@code 0}、全程零条预编辑 / 提交事件）。</p>
 *
 * <p><b>但禁止自己去调 {@code glfwSetInputMode(handle, 208903, GLFW_TRUE)}</b>：原版
 * {@code startTextInput()} 里那一次已经够了，我们再补一次会把玩家已经切好的输入法重置为关闭
 * （同日 19:19 日志：读回由 {@code 1} 掉回 {@code 0}，此后拼音只能打出英文字母）。</p>
 */
public final class ImeBridge {

    private static final Logger LOGGER = LogUtils.getLogger();

    /** 原版硬编码的 GLFW IME 输入模式（{@code TextInputManager:39}） */
    private static final int GLFW_IME_MODE = 208903;

    private static boolean active;
    /** 上一次真正推给原版的界面：换界面后即使开关状态相同也要重推（新界面收不到旧界面的预编辑事件） */
    private static Screen pushed;
    private static boolean preeditSeen;
    private static boolean committedSeen;
    /** TODO 临时排障字段 */
    private static boolean asciiSeen;
    private static boolean nonAsciiSeen;

    private ImeBridge() {
    }

    public static void setTextInputActive(boolean value) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen screen = minecraft == null ? null : minecraft.screen;
        // TODO 临时排障：定位「搜索框打不进中文」后删除
        LOGGER.info("[yiyiaddon] [临时] 文本输入开关调用：value={}，screen={}，短路={}",
                value, screen == null ? "null" : screen.getClass().getSimpleName(),
                active == value && pushed == screen);
        if (active == value && pushed == screen) {
            return;
        }
        active = value;
        pushed = screen;
        if (minecraft == null || screen == null) {
            return;
        }
        // 原本只调原版入口，但它在自绘界面上不生效（MinecraftTextInputAccessor 注释里有日志证据）：
        // 推 true 之后 startTextInput 从未被调用，textInputEnabled 恒为 false，于是
        // tickOutsideTextInput() 会在玩家刚切到中文（读回 1）的下一 tick 把 IME 关掉 ——
        // 表现就是「搜索框里怎么切输入法都只能打英文」。这里补上直驱，保证状态机真的进入
        // 「正在文本输入」；原版入口保留，因为预编辑事件重投递还要靠它。
        minecraft.onTextInputFocusChange(screen, value);
        if (minecraft instanceof MinecraftTextInputAccessor accessor) {
            TextInputManager manager = accessor.yiyiaddon$textInputManager();
            if (manager != null) {
                if (value) {
                    manager.startTextInput();
                } else {
                    manager.stopTextInput();
                }
            }
        }
        if (value) {
            preeditSeen = false;
            forceImeOn();
        }
    }

    /**
     * 聚焦时显式把窗口输入法打开一次。
     *
     * <p>2026-09-16 定性的最终结论：这台机器上 {@code setIMEInputMode(false)} 是无效操作
     * （聊天框里原版每 tick 都在调它，中文照样输入），中文能不能打<b>只取决于 GLFW 的 IME 读回值
     * 是否为 1</b>。而玩家进入自绘输入框时读回恒为 {@code 0} 且切不上去（同日 19:43 / 19:48 日志），
     * 于是拼音只能出字母。{@code GLFW_IME} 置真会让 Mojang 的 GLFW 补丁关联并打开窗口输入法，
     * 因此这里补一次 —— 这与原版 {@code startTextInput()} 在 {@code imeRequested} 为真时做的事相同，
     * 只是本环境里那个前置条件永远不成立。</p>
     */
    private static void forceImeOn() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.getWindow() == null) {
            return;
        }
        long handle = minecraft.getWindow().handle();
        GLFW.glfwSetInputMode(handle, GLFW_IME_MODE, GLFW.GLFW_TRUE);
        int mode = GLFW.glfwGetInputMode(handle, GLFW_IME_MODE);
        // 原版 EditBox 获得焦点时还会设置「预编辑光标矩形」（glfwSetPreeditCursorRectangle）。
        // 聊天框里能切中文、自绘输入框里连候选条都不出现（用户 2026-09-16 实测），差别正在这一步：
        // 该 GLFW 调用会顺带把 IME context 关联回窗口，之后玩家的中英切换键才会生效。
        if (minecraft instanceof MinecraftTextInputAccessor accessor) {
            TextInputManager manager = accessor.yiyiaddon$textInputManager();
            if (manager != null) {
                manager.setTextInputArea(0, 0, 1, 1);
            }
        }
        // TODO 临时排障：确认补设后输入法是否真的打开（定位后删）
        LOGGER.info("[yiyiaddon] [临时] 聚焦：补设 IME=TRUE + 预编辑光标区，读回={}", mode);
    }

    /** IME 提交出非 ASCII 字符（中文真正进了输入框）：只记第一次，便于诊断时核对 */
    public static void noteCommitted(int codepoint) {
        if (committedSeen || codepoint <= 0x7F) return;
        committedSeen = true;
        LOGGER.info("[yiyiaddon] IME 已提交非 ASCII 字符：U+{}",
                Integer.toHexString(codepoint).toUpperCase(java.util.Locale.ROOT));
    }

    /** TODO 临时排障：字符事件是否到达界面、到达时有没有焦点（定位「搜索框打不进中文」后删除） */
    public static void noteCharArrived(String host, boolean hasFocus, int codepoint) {
        String hex = Integer.toHexString(codepoint).toUpperCase(java.util.Locale.ROOT);
        if (codepoint > 0x7F && !nonAsciiSeen) {
            nonAsciiSeen = true;
            LOGGER.info("[yiyiaddon] [临时] 非 ASCII 字符到达：host={}，U+{}，有焦点={}", host, hex, hasFocus);
        } else if (codepoint <= 0x7F && !asciiSeen) {
            asciiSeen = true;
            LOGGER.info("[yiyiaddon] [临时] ASCII 字符到达：host={}，有焦点={}", host, hasFocus);
        }
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
