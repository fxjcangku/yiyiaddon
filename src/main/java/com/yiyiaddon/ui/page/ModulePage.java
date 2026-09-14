package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.ModuleEntry;

/**
 * 模块独立页面接口。
 *
 * <p>功能模块接入时实现本接口，并在 {@link ModuleEntry.Builder#page} 中注册工厂即可；
 * 模块中心与模块列表不需要任何改动。未注册页面的模块会落到占位页面。</p>
 *
 * <p>返回的页面由模块独立屏幕 {@code ui/screen/ModuleScreen} 承载：点击模块卡片单独开一个
 * 界面，左侧没有主导航，页面内容就是这里返回的 {@link BasePage}。</p>
 */
public interface ModulePage {

    /** 创建页面实例，由路由压栈展示。 */
    BasePage createPage(ModuleEntry entry);

    /** 页面关闭时的清理钩子，默认无操作。 */
    default void onClose() {
    }
}
