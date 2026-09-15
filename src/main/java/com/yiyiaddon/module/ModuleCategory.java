package com.yiyiaddon.module;

import com.yiyiaddon.ui.page.BasePage;

import java.util.function.Supplier;

/**
 * 模块分类数据对象。
 *
 * <p>分类不持有模块列表：模块与分类的从属关系由 {@link ModuleRegistry} 单向维护，
 * 避免同一份数据存在两个来源。</p>
 *
 * @param id            分类 ID，全局唯一，作为模块归属的引用键
 * @param displayName   分类中文名
 * @param description   分类描述
 * @param icon          Material Symbols 图标码位
 * @param order         排序权重，越小越靠前
 * @param page          分类自带页面的工厂；为 {@code null} 时点进去是该分类下的模块列表
 * @param settingsEntry 是否属于「设置」导航分组：这类分类是配置页面而不是功能模块
 *                      （Baritone设置 / ESP 全局设置），入口挂在设置页里，不出现在模块中心
 */
public record ModuleCategory(String id, String displayName, String description, String icon, int order,
                             Supplier<BasePage> page, boolean settingsEntry) {

    /** 普通分类：点进去是分类下的模块列表。 */
    public ModuleCategory(String id, String displayName, String description, String icon, int order) {
        this(id, displayName, description, icon, order, null, false);
    }

    /** 自带页面的功能分类：入口仍在模块中心（点进去直接进它自己的页面）。 */
    public ModuleCategory(String id, String displayName, String description, String icon, int order,
                          Supplier<BasePage> page) {
        this(id, displayName, description, icon, order, page, false);
    }

    /** 配置页面分类：入口在「设置」导航分组里。 */
    public static ModuleCategory settings(String id, String displayName, String description, String icon, int order,
                                          Supplier<BasePage> page) {
        return new ModuleCategory(id, displayName, description, icon, order, page, true);
    }
}
