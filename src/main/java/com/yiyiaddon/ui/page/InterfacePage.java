package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.screen.ClickGuiScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingLink;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 「界面」分类：面板外观与交互参数，全部落在 yiyiaddon 自己的最小配置里。
 */
public final class InterfacePage extends BasePage {

    public InterfacePage() {
        modules.add(new SettingModule(UiText.t("面板主题", "Panel Theme"), UiText.t("浏览并切换全部面板配色", "Browse and switch all panel themes"), null)
                .addSub(UiText.t("主题选择", "Theme"), UiText.t("点击进入主题缩略图预览", "Click to open the theme thumbnail preview"),
                        new SettingLink(() -> ClickGuiThemeManager.current().displayName(), () -> {
                            Minecraft minecraft = Minecraft.getInstance();
                            if (minecraft != null && minecraft.screen instanceof ClickGuiScreen screen) {
                                screen.openThemePreview();
                            }
                        })));

        modules.add(new SettingModule(UiText.t("界面大小", "GUI Size"), UiText.t("调整面板整体缩放", "Adjusts the overall panel scale"), new SettingCycle(List.of("75%", "100%", "125%"), () -> AddonConfig.uiScale, index -> {
            AddonConfig.uiScale = index;
            AddonConfig.save();
        })));

        modules.add(new SettingModule(UiText.t("面板模糊", "Panel Blur"), UiText.t("模糊面板后的游戏画面", "Blurs the game behind the panel"), new SettingToggle(() -> AddonConfig.panelBlur, value -> {
            AddonConfig.panelBlur = value;
            AddonConfig.save();
        }))
                .addSub(UiText.t("模糊强度", "Blur Strength"), UiText.t("调整高斯模糊半径", "Adjusts the Gaussian blur radius"),
                        new SettingNumberBox(0.0, 2.0, 0.05, "%.2f", () -> (double) AddonConfig.blurStrength, value -> {
                            AddonConfig.blurStrength = value.floatValue();
                            AddonConfig.save();
                        }))
                .addSubWhen(() -> AddonConfig.panelBlur, UiText.t("模糊底色", "Blur Tint"),
                        UiText.t("面板下方叠加的底色透明度", "Tint alpha composited under the panel"),
                        new SettingNumberBox(0, 255, 1, "%.0f", () -> (double) (AddonConfig.blurTint >>> 24), value -> {
                            int alpha = (int) Math.round(value) & 0xFF;
                            AddonConfig.blurTint = (alpha << 24) | (AddonConfig.blurTint & 0x00FFFFFF);
                            AddonConfig.save();
                        })));

        modules.add(new SettingModule(UiText.t("滚动", "Scrolling"), UiText.t("滚轮速度与长列表行为", "Wheel speed and long-list behaviour"), new SettingNumberBox(0.2, 5.0, 0.1, "%.1fx", () -> (double) AddonConfig.scrollSpeed, value -> {
            AddonConfig.scrollSpeed = value.floatValue();
            AddonConfig.save();
        })));
    }

    @Override
    public String getTitle() {
        return UiText.t("界面", "Interface");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("面板外观、模糊与滚动参数", "Panel appearance, blur and scrolling");
    }
}
