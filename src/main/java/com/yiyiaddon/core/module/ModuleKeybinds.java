package com.yiyiaddon.core.module;

import com.yiyiaddon.config.ModuleStateConfig;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.keybind.ModuleKeybindStore;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 模块快捷键存储：把界面快捷键管理器与模块状态配置接起来。
 *
 * <p>业务模块的快捷键存放在 {@code module-state.json}（模块运行数据），界面自身的快捷键仍存放在
 * 界面配置里。两者在 {@link ModuleKeybindManager} 中按 {@code module.} 前缀分流。</p>
 */
final class ModuleKeybinds implements ModuleKeybindStore {

    /** 绑定键名前缀 */
    static final String BINDING_PREFIX = ModuleKeybindManager.MODULE_PREFIX;

    private ModuleKeybinds() {
    }

    /** 装配快捷键存储与触发回调；由模块运行时调用一次 */
    static void install() {
        ModuleKeybindManager.setModuleBindings(new ModuleKeybinds(), ModuleManager::toggle);
    }

    @Override
    public Set<String> boundIds() {
        Set<String> result = new LinkedHashSet<>();
        for (String moduleId : ModuleStateConfig.ids()) {
            if (ModuleStateConfig.keybindOf(moduleId) != null) result.add(BINDING_PREFIX + moduleId);
        }
        return result;
    }

    @Override
    public Integer keyOf(String bindingId) {
        String moduleId = moduleIdOf(bindingId);
        return moduleId == null ? null : ModuleStateConfig.keybindOf(moduleId);
    }

    @Override
    public void bind(String bindingId, int key) {
        String moduleId = moduleIdOf(bindingId);
        if (moduleId == null) return;
        ModuleStateConfig.setKeybind(moduleId, key);
        ModuleStateConfig.save();
    }

    @Override
    public void unbind(String bindingId) {
        String moduleId = moduleIdOf(bindingId);
        if (moduleId == null) return;
        ModuleStateConfig.clearKeybind(moduleId);
        ModuleStateConfig.save();
    }

    /** 从绑定键名还原模块 ID */
    private static String moduleIdOf(String bindingId) {
        if (bindingId == null || !bindingId.startsWith(BINDING_PREFIX)) return null;
        String moduleId = bindingId.substring(BINDING_PREFIX.length());
        return moduleId.isBlank() ? null : moduleId;
    }
}
