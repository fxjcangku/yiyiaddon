package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.widget.SettingLink;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;

/**
 * 「设置」分类：快捷键、全局开关，以及各配置页面的入口。
 *
 * <p>Baritone设置 / ESP 全局设置这两类是<b>配置页面</b>而不是功能模块，入口挂在这里
 * （实机反馈：跟功能模块混在「模块」里分不清哪个是设置、哪个是功能）。</p>
 */
public final class SettingsPage extends BasePage {

    public SettingsPage(PageRouter router) {
        modules.add(new SettingModule(UiText.t("指令前缀", "Command Prefix"),
                UiText.t("客户端指令的起始符号，单个字符，默认 .　留空或填入 / 时回落到默认值", "Leading symbol of client commands, single character, default .　Falls back to default when blank or /"),
                new SettingTextBox(() -> AddonConfig.commandPrefix, value -> {
                    AddonConfig.commandPrefix = value;
                    AddonConfig.save();
                }, 1)));

        modules.add(new SettingModule(UiText.t("GUI 快捷键", "GUI Keybind"), UiText.t("点击右侧按键块后按下任意键完成录入", "Click the key block, then press any key to bind"), null)
                .keybindAction(ModuleKeybindManager.ACTION_CLICK_GUI));

        modules.add(new SettingModule(UiText.t("Baritone 汉化", "Baritone Localisation"), UiText.t("把 Baritone 的命令与提示显示为中文", "Shows Baritone commands and messages in Chinese"), new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
            AddonConfig.baritoneChinese = value;
            AddonConfig.save();
        }))
                .addSub(UiText.t("当前状态", "Current State"), UiText.t("只读展示汉化开关", "Read-only display of the localisation switch"),
                        new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
                        })));

        // 配置页面入口：走与模块中心同一条路由（同一个 token），返回/记忆导航都一致
        for (ModuleCategory category : CategoryRegistry.all()) {
            if (category.page() == null || !category.settingsEntry()) continue;
            modules.add(new SettingModule(category.displayName(), category.description(),
                    new SettingLink(() -> UiText.t("点击进入", "Open"), () ->
                            router.open(category.page().get(),
                                    UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id())))));
        }
    }

    @Override
    public String getTitle() {
        return UiText.t("设置", "Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("快捷键、全局开关与各配置页面入口", "Keybinds, global switches and settings pages");
    }
}
