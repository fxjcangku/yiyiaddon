package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingToggle;

/**
 * 「设置」分类：快捷键与全局开关。
 */
public final class SettingsPage extends BasePage {

    public SettingsPage() {
        modules.add(new SettingModule(UiText.t("GUI 快捷键", "GUI Keybind"), UiText.t("点击右侧按键块后按下任意键完成录入", "Click the key block, then press any key to bind"), null)
                .keybindAction(ModuleKeybindManager.ACTION_CLICK_GUI));

        modules.add(new SettingModule(UiText.t("Baritone 汉化", "Baritone Localisation"), UiText.t("把 Baritone 的命令与提示显示为中文", "Shows Baritone commands and messages in Chinese"), new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
            AddonConfig.baritoneChinese = value;
            AddonConfig.save();
        }))
                .addSub(UiText.t("当前状态", "Current State"), UiText.t("只读展示汉化开关", "Read-only display of the localisation switch"),
                        new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
                        })));
    }

    @Override
    public String getTitle() {
        return UiText.t("设置", "Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("快捷键与全局开关", "Keybinds and global switches");
    }
}
