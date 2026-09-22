package com.yiyiaddon.ui.keybind;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.screen.ClickGuiScreen;
import com.yiyiaddon.ui.screen.ModuleScreen;
import com.yiyiaddon.ui.widget.SettingModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * <p>触发条件：需要出现「按下」这一跳变、当前处于可唤出的界面状态（游戏里 / 菜单类界面）且不处于录制状态。</p>
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

    /**
     * 「显式清空」标记值：写在持久化串里表示玩家主动解绑过这一项。
     *
     * <p>缺了它分不清「从未绑定」与「已清空」——读盘时会给没绑的项套默认键
     * （{@link #applyDefaultBindings()}），清空就会在下次启动被悄悄撤销（第 214 条：
     * 能取消的东西必须真的取消得掉）。取值用 {@code GLFW_KEY_UNKNOWN}（-1），
     * 与鼠标编码（{@code KeyInputs.MOUSE_KEY_OFFSET} 起）不冲突。</p>
     */
    private static final int CLEARED_MARK = GLFW.GLFW_KEY_UNKNOWN;

    /** 被玩家显式清空、禁止再套默认键的键位 id */
    private static final Set<String> CLEARED = new HashSet<>();

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

    /** 每客户端 tick 调用：处于可唤出的界面状态、且出现「按下」跳变时触发绑定的动作。 */
    public static void tick(Minecraft client) {
        initialize();
        if (client == null) return;
        for (String id : tickIds()) {
            Integer key = keyOf(id);
            if (key == null) continue;
            boolean down = isKeyDown(client, key);
            boolean previous = LAST_DOWN.getOrDefault(id, false);
            LAST_DOWN.put(id, down);
            if (down && !previous && canTrigger(client) && !isCapturing()) {
                trigger(client, id);
            }
        }
    }

    /**
     * 当前界面状态下是否允许唤出面板。
     *
     * <p><b>放行两类：</b>游戏里（无任何界面）、以及菜单类界面（主菜单 / 暂停界面）——
     * 后者是用户实机反馈要的（2026-09-22：主菜单按 G 没反应）。
     * <b>其余界面一律不放行</b>：箱子、聊天、设置、我们自己的页面都可能是「正在输入 / 正在操作」的地方，
     * 抢键会把玩家正在干的事顶掉。</p>
     *
     * <p>菜单类界面下打开面板时把<b>当前界面当父级</b>（见 {@link #trigger}），ESC 原路返回菜单，
     * 不会掉进「既没界面又没世界」的空屏。</p>
     */
    private static boolean canTrigger(Minecraft client) {
        Screen screen = client.gui.screen();
        return screen == null || screen instanceof TitleScreen || screen instanceof PauseScreen;
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
        CLEARED.remove(id);
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
        KEYBINDS.remove(id);
        LAST_DOWN.remove(id);
        // 记下「显式清空」：不记的话下次启动 applyDefaultBindings() 会把默认键套回来，
        // 界面快捷键这种有默认值的项就等于清不掉（第 214 条）
        CLEARED.add(id);
        saveBindings();
        invalidateTickIds();
        // 即使原本没有绑定（例如有默认值的项），这次调用本身也算「已处理」，故恒为 true
        return true;
    }

    /** 清空界面快捷键并恢复默认；模块快捷键不在此范围内 */
    public static void clearAll(boolean save) {
        KEYBINDS.clear();
        LAST_DOWN.clear();
        // 「重置界面设置」要的正是回默认：连显式清空标记一起抹掉，默认键才会重新套上
        CLEARED.clear();
        captureId = "";
        applyDefaultBindings();
        storeBindings();
        if (save) AddonConfig.save();
        invalidateTickIds();
    }

    public static boolean hasBinding(String id) {
        return keyOf(id) != null;
    }

    /**
     * 这个键是不是「打开主界面」的绑定键（用户 2026-09-22：同一个键要能开也能关）。
     *
     * <p>界面里的关闭由各界面自己判定（见 {@code SkiaScreen#keyPressed}）：轮询通道的
     * {@link #canTrigger} 在扩展自己的界面打开时一律不放行（那是「别抢玩家正在操作的地方」的守卫），
     * 所以按一下 G 关界面这件事必须在界面内部处理。</p>
     */
    public static boolean isClickGuiKey(int key) {
        Integer bound = keyOf(ACTION_CLICK_GUI);
        return bound != null && bound == key;
    }

    /**
     * 吞掉这一次「打开主界面」的按下边沿。
     *
     * <p><b>为什么必须有</b>：界面里按 G 关掉整个界面之后，同一个 tick 的轮询通道会看到
     * 「G 按下 + 没有界面」这一跳变，于是刚关掉的界面立刻又被打开（开关变成没反应）。
     * 关闭界面时调它，把这一轮记成「已经按下」，边沿就不成立；松开再按才重新算一次跳变。</p>
     */
    public static void suppressClickGuiKey() {
        LAST_DOWN.put(ACTION_CLICK_GUI, true);
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
            // 父级给当前界面：从主菜单 / 暂停界面打开时 ESC 原路返回菜单；游戏里（无界面）仍是 null，
            // 与既有行为一字不差。
            Screen root = new ClickGuiScreen(client.gui.screen());
            // 上次是在某个模块页里关掉整个界面的：直接把那一页再摆回主界面之上（返回键回到主界面），
            // 而不是永远丢回首页（用户 2026-09-22：「ui 没有记得我关闭时候的记忆」）。
            ModuleEntry restored = UiNavigationMemory.moduleId() == null
                ? null : ModuleRegistry.byId(UiNavigationMemory.moduleId());
            client.gui.setScreen(restored == null ? root : new ModuleScreen(restored, root));
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
                    String id = entry.substring(0, split);
                    if (key == CLEARED_MARK) {
                        // 玩家主动清空过：记下标记，别在 applyDefaultBindings() 里把默认键套回来
                        CLEARED.add(id);
                    } else if (key != GLFW.GLFW_KEY_UNKNOWN) {
                        KEYBINDS.put(id, key);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        applyDefaultBindings();
        invalidateTickIds();
    }

    private static void applyDefaultBindings() {
        if (CLEARED.contains(ACTION_CLICK_GUI)) return;
        // 默认打开键：G（用户 2026-09-17 指令；此前自建默认是右键 Shift）
        KEYBINDS.putIfAbsent(ACTION_CLICK_GUI, GLFW.GLFW_KEY_G);
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
        // 显式清空的项写成 {@code CLEARED_MARK}（-1），读盘时据此跳过默认键套用
        for (String id : CLEARED) {
            if (KEYBINDS.containsKey(id)) continue;
            if (out.length() > 0) out.append(';');
            out.append(id).append('=').append(CLEARED_MARK);
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
