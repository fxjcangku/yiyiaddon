package com.yiyiaddon.ui.keybind;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

/**
 * 一个键位绑定值：键盘键或鼠标按键，可带修饰键（Ctrl / Alt / Shift / Cmd）。
 *
 * <p><b>来源与自研说明</b>：旧项目的 {@code KeybindSetting} 用的是旧框架的
 * {@code Keybind}（参考库原始源码见
 * {@code 26.1.2/01-开发参考库/原始源码/.../utils/misc/Keybind.java}）。
 * 新项目不依赖旧框架，用户 2026-09-17 明确要求「快速停止键不能自研一个吗，旧框架的源代码也有参考价值」
 * ——本类即照该源码的取值模型与判据自研，**语义逐条对齐**：</p>
 *
 * <ul>
 *   <li>{@code none()} = {@code GLFW_KEY_UNKNOWN}（旧 {@code Keybind.none()} 同值）；</li>
 *   <li>{@link #isPressed()} = <b>当前是否按下</b>（不是「跳变」）：键盘需修饰键也按住
 *       （旧 {@code Keybind#isPressed()} 的 {@code modifiersPressed() && Input.isKeyPressed(value)}），
 *       鼠标走按键状态；调用方若要「只触发一次」需自己保存上一刻状态（旧项目模块在 tick 里
 *       判一次就 toggle，等效只生效一次）；</li>
 *   <li>{@link #canBindTo(boolean, int, int)} 照旧源码：修饰键本身不能作为主键、
 *       {@code ESC} 不可绑、鼠标左右键不可绑（左键要留给界面点击；**右键不再是清空入口**，
 *       清空走键位块同行的「§c清空」按钮，见 {@code ConsoleWidgets#CLEAR_BUTTON}）；</li>
 *   <li>{@link #displayName()} 照旧源码 {@code toString()}：未绑定 <b>{@code None}</b>、
 *       带修饰键时前缀顺序 Control → Cmd → Alt → Shift → Caps Lock → Num Lock（英文写法不变），
 *       键名取原版本地化名（{@link KeyInputs}）。</li>
 * </ul>
 *
 * <p><b>落盘</b>：字段名沿用旧框架 NBT 的 {@code isKey / value / modifiers}（写进模块设置的 JSON 对象），
 * 便于日后排查时与旧项目配置对照；解析失败一律回落到传入的默认值，不抛异常。</p>
 */
public final class AddonKeybind {

    /** 未绑定时 {@link #displayName()} 的文本（逐字照旧框架 {@code Keybind#toString()}） */
    public static final String NONE_NAME = "None";

    private final boolean isKey;
    private final int value;
    private final int modifiers;

    private AddonKeybind(boolean isKey, int value, int modifiers) {
        this.isKey = isKey;
        this.value = value;
        this.modifiers = modifiers;
    }

    /** 未绑定（旧 {@code Keybind.none()}） */
    public static AddonKeybind none() {
        return new AddonKeybind(true, KeyInputs.NONE, 0);
    }

    /** 绑定一个键盘键 */
    public static AddonKeybind ofKey(int key) {
        return new AddonKeybind(true, key, 0);
    }

    /** 绑定一个键盘键 + 修饰键掩码 */
    public static AddonKeybind ofKey(int key, int modifiers) {
        return new AddonKeybind(true, key, modifiers);
    }

    /** 绑定一个鼠标按键 */
    public static AddonKeybind ofButton(int button) {
        return new AddonKeybind(false, button, 0);
    }

    /** 通用构造（供读盘与控件回填） */
    public static AddonKeybind of(boolean isKey, int value, int modifiers) {
        return new AddonKeybind(isKey, value, modifiers);
    }

    public boolean isKey() {
        return isKey;
    }

    public int value() {
        return value;
    }

    public int modifiers() {
        return modifiers;
    }

    /** 是否已绑定（未绑定 = 值等于 {@code GLFW_KEY_UNKNOWN}，与旧源码同判据） */
    public boolean isSet() {
        return value != KeyInputs.NONE;
    }

    /** 是否带修饰键 */
    public boolean hasMods() {
        return isKey && modifiers != 0;
    }

    /**
     * 该输入能否绑定：修饰键本身不可作为主键（否则会绑成「Shift + Shift」这类无意义组合）、
     * {@code ESC} 不可绑（界面里 ESC 是退出/取消）、鼠标左右键不可绑。判据照旧源码
     * {@code Keybind#canBindTo}，只是它不依赖实例状态，故这里写成静态方法，录制时直接调。
     */
    public static boolean canBindTo(boolean isKey, int value, int modifiers) {
        if (isKey) {
            if (modifiers != 0 && isModifierKey(value)) return false;
            return value != KeyInputs.NONE && value != GLFW.GLFW_KEY_ESCAPE;
        }
        return value != GLFW.GLFW_MOUSE_BUTTON_LEFT && value != GLFW.GLFW_MOUSE_BUTTON_RIGHT;
    }

    /**
     * 当前是否按下（旧 {@code Keybind#isPressed()} 同语义：当前状态，不是跳变）。
     *
     * <p>键盘绑定带修饰键时，所有修饰键也必须按住才算命中；未绑定时恒 {@code false}。</p>
     */
    public boolean isPressed() {
        if (!isSet()) return false;
        if (!isKey) return KeyInputs.buttonDown(Minecraft.getInstance(), value);
        return modifiersPressed() && KeyInputs.keyDown(Minecraft.getInstance(), value);
    }

    /** 显示名（未绑定 → {@code None}；带修饰键 → 前缀 + 键名） */
    public String displayName() {
        if (!isSet()) return NONE_NAME;
        if (!isKey) return KeyInputs.mouseName(value);
        if (modifiers == 0) return KeyInputs.keyName(value);
        return KeyInputs.modifierPrefix(modifiers) + KeyInputs.keyName(value);
    }

    // ── 落盘（字段名照旧框架 NBT：isKey / value / modifiers） ──

    /** 写成 JSON 对象 */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("isKey", isKey);
        json.addProperty("value", value);
        json.addProperty("modifiers", modifiers);
        return json;
    }

    /**
     * 从 JSON 读出；元素缺失或不是对象时返回 {@code fallback}（不抛异常，坏档不该让模块起不来）。
     */
    public static AddonKeybind fromJson(JsonElement element, AddonKeybind fallback) {
        if (element == null || !element.isJsonObject()) return fallback;
        JsonObject json = element.getAsJsonObject();
        boolean key = !json.has("isKey") || json.get("isKey").getAsBoolean();
        int value = json.has("value") && json.get("value").isJsonPrimitive()
            ? json.get("value").getAsInt() : KeyInputs.NONE;
        int mods = json.has("modifiers") && json.get("modifiers").isJsonPrimitive()
            ? json.get("modifiers").getAsInt() : 0;
        return new AddonKeybind(key, value, mods);
    }

    // ── 内部 ──

    /** 修饰键位段：GLFW 的左 Shift ~ 右 Super 这一段都是修饰键 */
    private static boolean isModifierKey(int key) {
        return key >= GLFW.GLFW_KEY_LEFT_SHIFT && key <= GLFW.GLFW_KEY_RIGHT_SUPER;
    }

    /** 该绑定的修饰键是否全部按住（照旧源码 {@code modifiersPressed()} 的逐项校验） */
    private boolean modifiersPressed() {
        if (!hasMods()) return true;
        Minecraft client = Minecraft.getInstance();
        if (!isModPressed(client, GLFW.GLFW_MOD_CONTROL, GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL)) return false;
        if (!isModPressed(client, GLFW.GLFW_MOD_SUPER, GLFW.GLFW_KEY_LEFT_SUPER, GLFW.GLFW_KEY_RIGHT_SUPER)) return false;
        if (!isModPressed(client, GLFW.GLFW_MOD_ALT, GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_RIGHT_ALT)) return false;
        return isModPressed(client, GLFW.GLFW_MOD_SHIFT, GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    private boolean isModPressed(Minecraft client, int mask, int... keys) {
        if ((modifiers & mask) == 0) return true;
        for (int key : keys) {
            if (KeyInputs.keyDown(client, key)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof AddonKeybind keybind)) return false;
        return isKey == keybind.isKey && value == keybind.value && modifiers == keybind.modifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isKey, value, modifiers);
    }

    @Override
    public String toString() {
        return displayName();
    }
}
