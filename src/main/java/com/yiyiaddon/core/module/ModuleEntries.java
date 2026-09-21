package com.yiyiaddon.core.module;

import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;

/**
 * 把功能模块转换成模块中心的注册条目。
 *
 * <p>模块元数据只有一个事实来源：{@link Module} 自身。模块中心、模块列表与模块卡片都读
 * {@link ModuleEntry}，因此这里做一次转换即可，模块接入时不需要改动任何界面代码。</p>
 *
 * <p>启用状态用方法引用绑定到模块实例，界面每次绘制读到的都是真实运行状态，不存在缓存副本。</p>
 */
public final class ModuleEntries {

    private ModuleEntries() {
    }

    /** 转换并注册到模块中心 */
    public static void publish(Module module) {
        ModuleRegistry.register(of(module));
    }

    /** 模块 → 注册条目 */
    public static ModuleEntry of(Module module) {
        var builder = ModuleEntry.builder(module.id())
                .name(module.name())
                .displayName(module.displayName())
                .category(module.categoryId())
                .description(module.description())
                .icon(module.icon())
                .order(module.order())
                .version(module.version())
                .state(module::isEnabled)
                .statusText(module.statusBadgeText());
        if (module.page() != null) builder.page(module::page);
        return builder.build();
    }
}
