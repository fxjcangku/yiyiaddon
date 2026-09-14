package com.yiyiaddon.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.repository.JsonFileStore;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 模块状态配置：功能模块的启用状态、快捷键绑定与模块自身设置。
 *
 * <p>独立于界面配置 {@link AddonConfig}：界面配置只存界面外观，模块状态只存模块运行数据，
 * 两者互不污染。界面配置损坏不会导致模块开关丢失，反之亦然。</p>
 *
 * <p>文件结构（中文字段，与前几阶段的存档风格一致）：</p>
 * <pre>
 * {
 *   "模块": {
 *     "identity": {
 *       "启用": true,
 *       "快捷键": 82,
 *       "设置": { "识别模式": "自动保存" }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>容错：文件缺失按全部未启用处理；文件损坏按默认值处理且不删除原文件；字段缺失或类型不符
 * 按该字段默认值处理。任何情况都不阻断客户端启动。</p>
 */
public final class ModuleStateConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/module-state");
    private static final String FILE_NAME = "module-state.json";
    private static final String KEY_MODULES = "模块";
    private static final String KEY_ENABLED = "启用";
    private static final String KEY_KEYBIND = "快捷键";
    private static final String KEY_SETTINGS = "设置";

    /** 模块 ID → 原始记录对象；未写入过的模块不出现在这里 */
    private static final Map<String, JsonObject> RECORDS = new LinkedHashMap<>();

    private static boolean loaded;

    private ModuleStateConfig() {
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
    }

    /** 幂等载入；损坏时保持空记录并告警 */
    public static synchronized void load() {
        if (loaded) return;
        loaded = true;

        Path file = path();
        if (!file.toFile().isFile()) return;

        JsonObject root = JsonFileStore.readJson(file);
        if (root == null) {
            LOGGER.warn("模块状态文件无法解析，本次按默认状态处理：{}", file);
            return;
        }
        JsonElement modules = root.get(KEY_MODULES);
        if (modules == null || !modules.isJsonObject()) return;
        for (Map.Entry<String, JsonElement> entry : modules.getAsJsonObject().entrySet()) {
            if (entry.getValue() != null && entry.getValue().isJsonObject()) {
                RECORDS.put(entry.getKey(), entry.getValue().getAsJsonObject().deepCopy());
            }
        }
    }

    /** 写回文件；失败仅告警，不影响内存状态 */
    public static synchronized void save() {
        JsonObject modules = new JsonObject();
        for (Map.Entry<String, JsonObject> entry : RECORDS.entrySet()) {
            modules.add(entry.getKey(), entry.getValue());
        }
        JsonObject root = new JsonObject();
        root.add(KEY_MODULES, modules);

        Path file = path();
        if (!JsonFileStore.writeAtomic(file, root)) {
            LOGGER.warn("模块状态写入失败：{}", file);
        }
    }

    // ── 启用状态 ──

    public static synchronized boolean isEnabled(String moduleId) {
        JsonObject record = RECORDS.get(moduleId);
        return record != null && record.has(KEY_ENABLED) && record.get(KEY_ENABLED).isJsonPrimitive()
                && record.get(KEY_ENABLED).getAsBoolean();
    }

    public static synchronized void setEnabled(String moduleId, boolean enabled) {
        record(moduleId).addProperty(KEY_ENABLED, enabled);
    }

    // ── 快捷键 ──

    /** 绑定的按键值；未绑定返回 {@code null} */
    public static synchronized Integer keybindOf(String moduleId) {
        JsonObject record = RECORDS.get(moduleId);
        if (record == null || !record.has(KEY_KEYBIND) || !record.get(KEY_KEYBIND).isJsonPrimitive()) return null;
        try {
            return record.get(KEY_KEYBIND).getAsInt();
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized void setKeybind(String moduleId, int key) {
        record(moduleId).addProperty(KEY_KEYBIND, key);
    }

    public static synchronized void clearKeybind(String moduleId) {
        JsonObject record = RECORDS.get(moduleId);
        if (record != null) record.remove(KEY_KEYBIND);
    }

    // ── 模块设置 ──

    public static synchronized Set<String> ids() {
        return new LinkedHashSet<>(RECORDS.keySet());
    }

    /** 模块设置的副本；无记录返回空对象 */
    public static synchronized JsonObject settingsOf(String moduleId) {
        JsonObject record = RECORDS.get(moduleId);
        if (record == null || !record.has(KEY_SETTINGS) || !record.get(KEY_SETTINGS).isJsonObject()) {
            return new JsonObject();
        }
        return record.getAsJsonObject(KEY_SETTINGS).deepCopy();
    }

    /** 覆盖模块设置；传入普通对象即可，内部做深拷贝 */
    public static synchronized void putSettings(String moduleId, JsonObject settings) {
        if (settings == null) return;
        record(moduleId).add(KEY_SETTINGS, settings.deepCopy());
    }

    // ── 诊断 ──

    /** 诊断文案：记录了 N 个模块，其中 M 个启用 */
    public static synchronized String describe() {
        int enabled = 0;
        for (String id : RECORDS.keySet()) {
            if (isEnabled(id)) enabled++;
        }
        return "记录了 " + RECORDS.size() + " 个模块，其中 " + enabled + " 个启用";
    }

    private static JsonObject record(String moduleId) {
        return RECORDS.computeIfAbsent(moduleId, id -> new JsonObject());
    }
}
