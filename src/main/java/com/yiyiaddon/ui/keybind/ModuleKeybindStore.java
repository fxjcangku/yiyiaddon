package com.yiyiaddon.ui.keybind;

import java.util.Set;

/**
 * 模块快捷键存储端口。
 *
 * <p>界面快捷键（打开 ClickGUI、界面设置行）存在界面配置里；业务模块的快捷键属于模块运行数据，
 * 由模块运行时提供本接口的实现，存到独立的模块状态配置。这样界面配置重置不会清掉玩家给
 * 功能模块设的按键。</p>
 *
 * <p>键名统一为 {@code module.<模块ID>}，与 {@link ModuleKeybindManager#MODULE_PREFIX} 对应。</p>
 */
public interface ModuleKeybindStore {

    /** 全部已绑定的模块键名 */
    Set<String> boundIds();

    /** 键名对应的按键值；未绑定返回 {@code null} */
    Integer keyOf(String bindingId);

    /** 绑定并持久化 */
    void bind(String bindingId, int key);

    /** 解除绑定并持久化 */
    void unbind(String bindingId);
}
