package com.yiyiaddon.ui.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;

/**
 * 按键输入的底层原语：键 / 鼠标按键的「编码、名称、当前是否按下、修饰键前缀」。
 *
 * <p><b>为什么单独成类</b>：这套原语原先只写在 {@link ModuleKeybindManager} 里（模块开关快捷键用），
 * 现在键位设置控件（{@link com.yiyiaddon.ui.widget.SettingKeybind} + {@link AddonKeybind}）也要用同一套
 * 名称与按下判定。名称表与按下判定各写一份必然漂移（第 169 条），故收敛到这里一处，
 * 两侧都调这里。</p>
 *
 * <p><b>鼠标按键编码</b>：{@link ModuleKeybindManager} 的键位表只有 {@code int} 一列，
 * 鼠标按键按 {@link #encodeMouse(int)} 编成负数（{@code MOUSE_KEY_OFFSET - button}）与键盘键共存；
 * {@link AddonKeybind} 自带 {@code isKey} 标志，不需要这套编码，但它显示鼠标名时同样调
 * {@link #mouseName(int)}，保证两种承载方式在界面上念出来的名字一致。</p>
 *
 * <p><b>名称口径</b>：键盘键名走原版 {@link InputConstants}（跟随客户端语言，不新造中文键名）；
 * 鼠标沿用本项目既有口径 {@code Mouse Back / Mouse Forward / Mouse N}。</p>
 */
public final class KeyInputs {

    /** 未绑定键位（GLFW 的 {@code GLFW_KEY_UNKNOWN}，与 Meteor {@code Keybind.none()} 同值） */
    public static final int NONE = GLFW.GLFW_KEY_UNKNOWN;

    /** 鼠标按键以 {@code MOUSE_KEY_OFFSET - button} 编码进同一张键位表 */
    public static final int MOUSE_KEY_OFFSET = -1000;

    private KeyInputs() {
    }

    /** 鼠标按键下标 → 键位表编码（负数） */
    public static int encodeMouse(int button) {
        return MOUSE_KEY_OFFSET - button;
    }

    /** 该键位表编码是否代表鼠标按键 */
    public static boolean isMouseCode(int code) {
        return code <= MOUSE_KEY_OFFSET;
    }

    /**
     * 键位表编码 → 显示名（键盘走原版译名，鼠标走 {@link #mouseName(int)}）。
     *
     * @param code 键位表编码（键盘键码或 {@link #encodeMouse(int)}）
     */
    public static String name(int code) {
        if (isMouseCode(code)) return mouseName(MOUSE_KEY_OFFSET - code);
        return keyName(code);
    }

    /** 键盘键码 → 原版显示名（跟随客户端语言） */
    public static String keyName(int key) {
        return InputConstants.getKey(new KeyEvent(key, 0, 0)).getDisplayName().getString();
    }

    /** 鼠标按键下标 → 显示名（4/5 号侧键给专名，其余给序号） */
    public static String mouseName(int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_4) return "Mouse Back";
        if (button == GLFW.GLFW_MOUSE_BUTTON_5) return "Mouse Forward";
        return "Mouse " + (button + 1);
    }

    /**
     * 修饰键前缀（照 Meteor {@code Keybind#toString()} 的顺序与英文写法）。
     *
     * @param modifiers GLFW 修饰键掩码
     */
    public static String modifierPrefix(int modifiers) {
        StringBuilder label = new StringBuilder();
        if ((modifiers & GLFW.GLFW_MOD_CONTROL) != 0) label.append("Ctrl + ");
        if ((modifiers & GLFW.GLFW_MOD_SUPER) != 0) label.append("Cmd + ");
        if ((modifiers & GLFW.GLFW_MOD_ALT) != 0) label.append("Alt + ");
        if ((modifiers & GLFW.GLFW_MOD_SHIFT) != 0) label.append("Shift + ");
        if ((modifiers & GLFW.GLFW_MOD_CAPS_LOCK) != 0) label.append("Caps Lock + ");
        if ((modifiers & GLFW.GLFW_MOD_NUM_LOCK) != 0) label.append("Num Lock + ");
        return label.toString();
    }

    /** 键位表编码当前是否按下（键盘或鼠标） */
    public static boolean down(Minecraft client, int code) {
        if (client == null || client.getWindow() == null) return false;
        return isMouseCode(code) ? buttonDown(client, MOUSE_KEY_OFFSET - code) : keyDown(client, code);
    }

    /** 键盘键当前是否按下（未绑定恒 false） */
    public static boolean keyDown(Minecraft client, int key) {
        if (client == null || client.getWindow() == null || key == NONE) return false;
        return InputConstants.isKeyDown(client.getWindow(), key);
    }

    /** 鼠标按键当前是否按下 */
    public static boolean buttonDown(Minecraft client, int button) {
        if (client == null || client.getWindow() == null) return false;
        return GLFW.glfwGetMouseButton(client.getWindow().handle(), button) == GLFW.GLFW_PRESS;
    }
}
