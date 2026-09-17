package com.yiyiaddon.ui.keybind;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.screen.ClickGuiScreen;
import com.yiyiaddon.ui.widget.SettingModule;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 按键管理：绑定录制、持久化与触发。
 *
 * <p>可绑定的对象分两类，分别存放在两处配置：</p>
 * <ul>
 *     <li><b>界面快捷键</b>：具名动作（{@link #ACTION_CLICK_GUI}）与可切换的界面设置行，
 *         按 {@code bindingId} 注册，存在界面配置里；</li>
 *     <li><b>模块快捷键</b>：键名以 {@link #MODULE_PREFIX} 开头，由模块运行时通过
 *         {@link ModuleKeybindStore} 提供读写，存在模块状态配置里，界面配置重置不会清掉它们。</li>
 * </ul>
 *
 * <p>触发条件保持原样：需要出现「按下」这一跳变、界面已关闭且不处于录制状态。</p>
 */
public final class ModuleKeybindManager {

    public static final String ACTION_CLICK_GUI = "action.clickgui";

    /** 模块快捷键键名前缀，与模块状态配置共用 */
    public static final String MODULE_PREFIX = "module.";

    /** 鼠标按键以 {@code MOUSE_KEY_OFFSET - button} 编码进同一张键位表（编码与名称统一在 {@link KeyInputs}） */
    private static final int MOUSE_KEY_OFFSET = KeyInputs.MOUSE_KEY_OFFSET;

    private static final Map<String, Integer> KEYBINDS = new LinkedHashMap<>();
    private static final Map<String, SettingModule> MODULES = new LinkedHashMap<>();
    private static final Map<String, Boolean> LAST_DOWN = new LinkedHashMap<>();

    /** 每刻遍历用的键名快照；绑定变化时失效重建，避免逐刻分配 */
    private static volatile String[] tickIds;

    private static ModuleKeybindStore moduleStore;
    private static Consumer<String> moduleToggle;

    private static String captureId = "";
    private static boolean initialized;

    private ModuleKeybindManager() {
    }

    /** 装配模块快捷键存储与触发回调；由模块运行时调用一次 */
    public static void setModuleBindings(ModuleKeybindStore store, Consumer<String> toggle) {
        moduleStore = store;
        moduleToggle = toggle;
        invalidateTickIds();
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
        for (String id : tickIds()) {
            Integer key = keyOf(id);
            if (key == null) continue;
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
        if (isModuleBinding(id)) {
            ModuleKeybindStore store = moduleStore;
            if (store != null) store.bind(id, key);
        } else {
            KEYBINDS.put(id, key);
            saveBindings();
        }
        LAST_DOWN.put(id, true);
        invalidateTickIds();
        return true;
    }

    public static boolean captureMouseButton(int button) {
        if (!isCapturing() || button < GLFW.GLFW_MOUSE_BUTTON_1) return false;
        return captureKey(MOUSE_KEY_OFFSET - button);
    }

    public static boolean clearBinding(String id) {
        if (id == null || id.isBlank()) return false;
        if (id.equals(captureId)) captureId = "";
        if (isModuleBinding(id)) {
            ModuleKeybindStore store = moduleStore;
            if (store == null) return false;
            store.unbind(id);
            LAST_DOWN.remove(id);
            invalidateTickIds();
            return true;
        }
        boolean removed = KEYBINDS.remove(id) != null;
        LAST_DOWN.remove(id);
        if (removed) saveBindings();
        invalidateTickIds();
        return removed;
    }

    /** 清空界面快捷键并恢复默认；模块快捷键不在此范围内 */
    public static void clearAll(boolean save) {
        KEYBINDS.clear();
        LAST_DOWN.clear();
        captureId = "";
        applyDefaultBindings();
        storeBindings();
        if (save) AddonConfig.save();
        invalidateTickIds();
    }

    public static boolean hasBinding(String id) {
        return keyOf(id) != null;
    }

    public static boolean isCapturing() {
        return !captureId.isBlank();
    }

    public static boolean isCapturing(String id) {
        return id != null && id.equals(captureId);
    }

    public static String keyName(String id) {
        Integer key = keyOf(id);
        if (key == null) return "";
        return keyName(key);
    }

    public static void registerModule(SettingModule module) {
        if (module != null && module.isToggleable() && !module.usesActionKeybind() && !module.getBindingId().isBlank()) {
            MODULES.put(module.getBindingId(), module);
        }
    }

    public static boolean isKeyDown(Minecraft client, String id) {
        if (client == null || id == null) return false;
        Integer key = keyOf(id);
        return key != null && isKeyDown(client, key);
    }

    // ── 内部 ──

    private static boolean isModuleBinding(String id) {
        return id != null && id.startsWith(MODULE_PREFIX);
    }

    /** 取键名对应的按键值；界面键位与模块键位分别路由 */
    private static Integer keyOf(String id) {
        if (id == null || id.isBlank()) return null;
        if (isModuleBinding(id)) {
            ModuleKeybindStore store = moduleStore;
            return store == null ? null : store.keyOf(id);
        }
        return KEYBINDS.get(id);
    }

    private static String keyName(int key) {
        return KeyInputs.name(key);
    }

    private static boolean isKeyDown(Minecraft client, int key) {
        return KeyInputs.down(client, key);
    }

    private static void trigger(Minecraft client, String id) {
        if (ACTION_CLICK_GUI.equals(id)) {
            client.setScreen(new ClickGuiScreen(null));
            return;
        }
        if (isModuleBinding(id)) {
            Consumer<String> toggle = moduleToggle;
            if (toggle != null) toggle.accept(id.substring(MODULE_PREFIX.length()));
            return;
        }
        SettingModule module = MODULES.get(id);
        if (module != null) module.toggleFromKeybind();
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
        invalidateTickIds();
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

    private static void invalidateTickIds() {
        tickIds = null;
    }

    /** 界面键位与模块键位的合并快照 */
    private static String[] tickIds() {
        String[] cached = tickIds;
        if (cached != null) return cached;

        List<String> ids = new ArrayList<>(KEYBINDS.keySet());
        ModuleKeybindStore store = moduleStore;
        if (store != null) ids.addAll(store.boundIds());
        String[] built = ids.toArray(new String[0]);
        tickIds = built;
        return built;
    }
}
