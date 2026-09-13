package com.yiyiaddon.ui.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.screen.ClickGuiScreen;
import com.yiyiaddon.ui.widget.SettingModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 界面快捷键管理：绑定录制、持久化与触发。
 *
 * <p>只管理 UI 自身的快捷键，不持有任何业务模块。可绑定的对象有两类：</p>
 * <ul>
 *     <li>具名动作（当前仅 {@link #ACTION_CLICK_GUI}）；</li>
 *     <li>可切换的模块行（主控件为 {@code SettingToggle} 的行，按 {@code bindingId} 注册）。</li>
 * </ul>
 */
public final class ModuleKeybindManager {
    public static final String ACTION_CLICK_GUI = "action.clickgui";
    /** 鼠标按键以 {@code MOUSE_KEY_OFFSET - button} 编码进同一张键位表。 */
    private static final int MOUSE_KEY_OFFSET = -1000;
    private static final Map<String, Integer> KEYBINDS = new LinkedHashMap<>();
    private static final Map<String, SettingModule> MODULES = new LinkedHashMap<>();
    private static final Map<String, Boolean> LAST_DOWN = new LinkedHashMap<>();
    private static String captureId = "";
    private static boolean initialized;

    private ModuleKeybindManager() {
    }

    public static void initialize() {
        if (initialized) return;
        initialized = true;
        readBindings();
    }

    /** 每客户端 tick 调用：界面关闭时触发按下的绑定。 */
    public static void tick(Minecraft client) {
        initialize();
        if (client == null) return;
        for (Map.Entry<String, Integer> entry : KEYBINDS.entrySet()) {
            String id = entry.getKey();
            int key = entry.getValue();
            boolean down = isKeyDown(client, key);
            boolean previous = LAST_DOWN.getOrDefault(id, false);
            LAST_DOWN.put(id, down);
            if (down && !previous && client.screen == null && !isCapturing()) {
                trigger(client, id);
            }
        }
    }

    public static boolean beginCapture(String id) {
        if (id == null || id.isBlank()) return false;
        captureId = id;
        return true;
    }

    public static boolean captureKey(int key) {
        if (!isCapturing()) return false;
        String id = captureId;
        captureId = "";
        if (key == GLFW.GLFW_KEY_UNKNOWN) return true;
        KEYBINDS.put(id, key);
        LAST_DOWN.put(id, true);
        saveBindings();
        return true;
    }

    public static boolean captureMouseButton(int button) {
        if (!isCapturing() || button < GLFW.GLFW_MOUSE_BUTTON_1) return false;
        return captureKey(MOUSE_KEY_OFFSET - button);
    }

    public static boolean clearBinding(String id) {
        if (id == null || id.isBlank()) return false;
        boolean removed = KEYBINDS.remove(id) != null;
        LAST_DOWN.remove(id);
        if (id.equals(captureId)) captureId = "";
        if (removed) saveBindings();
        return removed;
    }

    public static void clearAll(boolean save) {
        KEYBINDS.clear();
        LAST_DOWN.clear();
        captureId = "";
        applyDefaultBindings();
        storeBindings();
        if (save) AddonConfig.save();
    }

    public static boolean hasBinding(String id) {
        return KEYBINDS.containsKey(id);
    }

    public static boolean isCapturing() {
        return !captureId.isBlank();
    }

    public static boolean isCapturing(String id) {
        return id != null && id.equals(captureId);
    }

    public static String keyName(String id) {
        Integer key = KEYBINDS.get(id);
        if (key == null) return "";
        if (key <= MOUSE_KEY_OFFSET) {
            int button = MOUSE_KEY_OFFSET - key;
            if (button == GLFW.GLFW_MOUSE_BUTTON_4) return "Mouse Back";
            if (button == GLFW.GLFW_MOUSE_BUTTON_5) return "Mouse Forward";
            return "Mouse " + (button + 1);
        }
        return InputConstants.getKey(new KeyEvent(key, 0, 0)).getDisplayName().getString();
    }

    public static void registerModule(SettingModule module) {
        if (module != null && module.isToggleable() && !module.usesActionKeybind() && !module.getBindingId().isBlank()) {
            MODULES.put(module.getBindingId(), module);
        }
    }

    private static void readBindings() {
        String raw = AddonConfig.moduleKeybinds;
        if (raw != null && !raw.isBlank()) {
            for (String entry : raw.split(";")) {
                int split = entry.lastIndexOf('=');
                if (split <= 0 || split >= entry.length() - 1) continue;
                try {
                    int key = Integer.parseInt(entry.substring(split + 1));
                    if (key != GLFW.GLFW_KEY_UNKNOWN) KEYBINDS.put(entry.substring(0, split), key);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        applyDefaultBindings();
    }

    public static boolean isKeyDown(Minecraft client, String id) {
        if (client == null || id == null) return false;
        Integer key = KEYBINDS.get(id);
        return key != null && isKeyDown(client, key);
    }

    private static boolean isKeyDown(Minecraft client, int key) {
        if (key <= MOUSE_KEY_OFFSET) {
            return GLFW.glfwGetMouseButton(client.getWindow().handle(), MOUSE_KEY_OFFSET - key) == GLFW.GLFW_PRESS;
        }
        return key != GLFW.GLFW_KEY_UNKNOWN && InputConstants.isKeyDown(client.getWindow(), key);
    }

    private static void trigger(Minecraft client, String id) {
        if (ACTION_CLICK_GUI.equals(id)) {
            client.setScreen(new ClickGuiScreen(null));
            return;
        }
        SettingModule module = MODULES.get(id);
        if (module != null) module.toggleFromKeybind();
    }

    private static void applyDefaultBindings() {
        KEYBINDS.putIfAbsent(ACTION_CLICK_GUI, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    private static void saveBindings() {
        storeBindings();
        AddonConfig.save();
    }

    private static void storeBindings() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Integer> entry : KEYBINDS.entrySet()) {
            if (out.length() > 0) out.append(';');
            out.append(entry.getKey()).append('=').append(entry.getValue());
        }
        AddonConfig.moduleKeybinds = out.toString();
    }
}
