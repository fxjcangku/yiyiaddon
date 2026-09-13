package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.widget.SettingButton;
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

        modules.add(new SettingModule(UiText.t("重置测试状态", "Reset Test State"), UiText.t("把 UI 测试值恢复为初始值", "Restores the UI test values"), null)
                .addSub(UiText.t("重置", "Reset"), UiText.t("清零计数器与条件开关", "Clears the counter and condition switch"),
                        new SettingButton(UiText.t("执行重置", "Reset"), () -> {
                            AddonConfig.testCounter = 0;
                            AddonConfig.testCondition = true;
                            AddonConfig.testToggle = true;
                            AddonConfig.testSlider = 35.0;
                            AddonConfig.testCycle = 0;
                            AddonConfig.testColorSplit = false;
                            AddonConfig.save();
                        }))
                .addSub(UiText.t("枚举归零", "Cycle to First"), UiText.t("把枚举选择恢复到第一项", "Restores the cycle selection to the first entry"),
                        new SettingButton(UiText.t("恢复", "Restore"), () -> {
                            AddonConfig.testCycle = 0;
                            AddonConfig.save();
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
