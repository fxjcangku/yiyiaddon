package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.widget.SettingButton;
import com.yiyiaddon.ui.widget.SettingColorPreview;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingSlider;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;

/**
 * 「测试」分类：全部内容都是 UI 验证对象，不执行任何 Minecraft 业务。
 *
 * <p>覆盖：开关、滑条、枚举选择、文本输入、按钮、颜色、一级展开、二级设置、
 * 条件显示、长页面滚动。</p>
 */
public final class TestPage extends BasePage {

    public TestPage() {
        modules.add(new SettingModule(UiText.t("开关", "Toggle"), UiText.t("验证开关控件的动画与键盘绑定录入", "Verifies toggle animation and keybind capture"), new SettingToggle(() -> AddonConfig.testToggle, value -> {
            AddonConfig.testToggle = value;
            AddonConfig.save();
        }))
                .addSub(UiText.t("开关状态", "Toggle State"), UiText.t("跟随上方开关实时变化", "Follows the toggle above in real time"),
                        new SettingToggle(() -> AddonConfig.testToggle, value -> AddonConfig.testToggle = value))
                .addSubWhen(() -> AddonConfig.testToggle, UiText.t("仅开启时可见", "Visible When On"),
                        UiText.t("关闭上方开关后本行隐藏", "Hides after the toggle above is switched off"),
                        new SettingToggle(() -> AddonConfig.testCondition, value -> {
                            AddonConfig.testCondition = value;
                            AddonConfig.save();
                        })));

        modules.add(new SettingModule(UiText.t("滑条", "Slider"), UiText.t("验证拖动、数值格式化与释放", "Verifies dragging, formatting and release"), new SettingSlider(0.0, 100.0, "%.0f%%", () -> AddonConfig.testSlider, value -> AddonConfig.testSlider = value))
                .addSub(UiText.t("小范围", "Small Range"), UiText.t("验证 0~1 的浮点格式化", "Verifies 0~1 float formatting"),
                        new SettingSlider(0.0, 1.0, "%.2f", () -> 0.5, value -> {
                        }))
                .addSub(UiText.t("整数计数", "Integer Counter"), UiText.t("验证整数值显示", "Verifies integer display"),
                        new SettingSlider(0.0, 20.0, "%.0f", () -> (double) AddonConfig.testCounter, value -> AddonConfig.testCounter = (int) Math.round(value))));

        modules.add(new SettingModule(UiText.t("枚举选择", "Cycle"), UiText.t("点击循环切换选项", "Click to cycle through the options"), new SettingCycle(List.of(UiText.t("选项一", "Option 1"), UiText.t("选项二", "Option 2"), UiText.t("选项三", "Option 3")), () -> AddonConfig.testCycle, index -> {
            AddonConfig.testCycle = index;
            AddonConfig.save();
        }))
                .addSub(UiText.t("四选项", "Four Options"), UiText.t("验证四项循环", "Verifies a four-entry cycle"),
                        new SettingCycle(List.of("A", "B", "C", "D"), () -> 0, index -> {
                        })));

        modules.add(new SettingModule(UiText.t("文本输入", "Text Input"), UiText.t("验证焦点、光标、选区与中文输入", "Verifies focus, caret, selection and IME"), new SettingTextBox(() -> AddonConfig.testText, value -> AddonConfig.testText = value, 32))
                .addSubGroup(UiText.t("二级分组", "Nested Group"), UiText.t("点击展开下级设置项", "Click to expand the nested entries"))
                .addSubChild(UiText.t("组内开关", "Nested Toggle"), UiText.t("组内二级设置项", "Nested second-level entry"),
                        new SettingToggle(() -> AddonConfig.testCondition, value -> AddonConfig.testCondition = value))
                .addSubChild(UiText.t("组内文本", "Nested Text"), UiText.t("组内文本输入", "Nested text input"),
                        new SettingTextBox(() -> AddonConfig.testText, value -> AddonConfig.testText = value, 16)));

        modules.add(new SettingModule(UiText.t("按钮", "Button"), UiText.t("验证按下动画与回调", "Verifies press animation and callback"), new SettingButton(UiText.t("点击 +1", "Click +1"), () -> {
            AddonConfig.testCounter++;
            AddonConfig.save();
        }))
                .addSub(UiText.t("重置计数", "Reset Counter"), UiText.t("把测试计数归零", "Resets the test counter to zero"),
                        new SettingButton(UiText.t("重置", "Reset"), () -> {
                            AddonConfig.testCounter = 0;
                            AddonConfig.save();
                        }))
                .addSub(UiText.t("当前计数", "Current Counter"), UiText.t("只读展示，验证动态文本", "Read-only display for dynamic text"),
                        new SettingButton(() -> UiText.t("计数", "Count") + ": " + AddonConfig.testCounter, () -> {
                        })));

        modules.add(new SettingModule(UiText.t("颜色", "Color"), UiText.t("验证纯色与双色分割预览", "Verifies solid and split color preview"), new SettingColorPreview(() -> AddonConfig.testColorPrimary, () -> AddonConfig.testColorSecondary, () -> AddonConfig.testColorSplit))
                .addSub(UiText.t("分割显示", "Split Preview"), UiText.t("在纯色与双色之间切换", "Switches between solid and split preview"),
                        new SettingToggle(() -> AddonConfig.testColorSplit, value -> {
                            AddonConfig.testColorSplit = value;
                            AddonConfig.save();
                        })));

        modules.add(buildScrollModule());
    }

    /** 12 条子项，用于验证长页面滚动、视口裁剪与滚动条。 */
    private static SettingModule buildScrollModule() {
        SettingModule module = new SettingModule(UiText.t("长页面滚动", "Long List"), UiText.t("12 条子项，用于验证滚动与裁剪", "12 entries for verifying scrolling and clipping"), null);
        for (int i = 1; i <= 12; i++) {
            final int index = i;
            module.addSub(UiText.t("滚动项 ", "Scroll Item ") + index, UiText.t("第 ", "Entry ") + index + UiText.t(" 条测试数据", " test entry"),
                    new SettingToggle(() -> AddonConfig.testCounter >= index, value -> {
                    }));
        }
        return module;
    }

    @Override
    public String getTitle() {
        return UiText.t("测试", "Test");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("UI 控件验证，不涉及任何游戏行为", "UI control verification, no gameplay behaviour");
    }
}
