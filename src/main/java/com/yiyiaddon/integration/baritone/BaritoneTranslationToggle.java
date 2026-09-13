package com.yiyiaddon.integration.baritone;

import com.yiyiaddon.config.AddonConfig;

/**
 * Baritone 用户可见文本汉化的总开关。
 *
 * <p>汉化作用于 Baritone 本体的命令行输出（Mixin 注入点），调用可能发生在任意时刻
 * （含 Baritone 类初始化期间），因此这里只读 {@link AddonConfig} 的静态字段：
 * 未加载配置时返回默认值，不抛异常，也不依赖任何业务框架。</p>
 *
 * <p>只影响「用户可见文本」：命令名、帮助、设置列表、状态与错误消息。
 * 底层命令键、设置键、API 名一律保持英文，不参与汉化。</p>
 */
public final class BaritoneTranslationToggle {

    private BaritoneTranslationToggle() {
    }

    /** 默认开启：用户安装 yiyiaddon 的预期就是中文界面。 */
    public static boolean enabled() {
        return AddonConfig.baritoneChinese;
    }

    public static void set(boolean enabled) {
        AddonConfig.baritoneChinese = enabled;
        AddonConfig.save();
    }
}
