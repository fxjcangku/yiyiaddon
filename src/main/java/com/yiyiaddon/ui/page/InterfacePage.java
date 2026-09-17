package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.ModuleRow;
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
 *
 * <p><b>行样式与模块中心同一套</b>：用户 2026-09-16 看过本页截图后说「这些也要啊」——每行左侧补上
 * Material 图标、行高压到模块行那一档（{@link ModuleRow#HEIGHT} / {@link ModuleRow#ROW_GAP}），
 * 与模块中心、设置页合上同一个节奏。见 {@link SettingModule#icon(String)}。</p>
 */
public final class InterfacePage extends BasePage {

    /*
     * 行首图标。码点全部取自项目「已验证」集合（开发习惯第 140 条：字形直接进字体绘制，
     * 字体里没有就是一块豆腐），本页一个未验真的码点都没有引入。
     */
    /** 魔棒（auto_fix_high）：外观美化 / 主题；取自 {@code EnchantModule.ICON}（U+E663）。 */
    private static final String ICON_THEME = "\uE663";
    /** 放大镜：界面大小＝整体缩放；取自 {@code IdIdentifyModule.ICON}（U+E8B6，界面搜索框同字形）。 */
    private static final String ICON_SCALE = "\uE8B6";
    /** 星光（auto_awesome）：模糊是视觉特效；取自 {@code AutoRespawnModule.ICON}（U+E65F）。 */
    private static final String ICON_BLUR = "\uE65F";
    /** 调节（tune）：滚动是参数；取自 {@code ClickGuiScreen.NAV_ICONS} 的「界面」页签同字形（U+E429）。 */
    private static final String ICON_SCROLL = "\uE429";

    public InterfacePage() {
        modules.add(new SettingModule(UiText.t("面板主题", "Panel Theme"), UiText.t("浏览并切换全部面板配色", "Browse and switch all panel themes"), null)
                .icon(ICON_THEME)
                .addSub(UiText.t("主题选择", "Theme"), UiText.t("点击进入主题缩略图预览", "Click to open the theme thumbnail preview"),
                        new SettingLink(() -> ClickGuiThemeManager.current().displayName(), () -> {
                            Minecraft minecraft = Minecraft.getInstance();
                            if (minecraft != null && minecraft.screen instanceof ClickGuiScreen screen) {
                                screen.openThemePreview();
                            }
                        })));

        // 行说明末尾接可见提示（第 213 条）：循环控件看不出能点
        modules.add(new SettingModule(UiText.t("界面大小", "GUI Size"), UiText.t("调整面板整体缩放", "Adjusts the overall panel scale") + HINT_CYCLE, new SettingCycle(List.of("75%", "100%", "125%"), () -> AddonConfig.uiScale, index -> {
            AddonConfig.uiScale = index;
            AddonConfig.save();
        })).icon(ICON_SCALE));

        modules.add(new SettingModule(UiText.t("面板模糊", "Panel Blur"), UiText.t("模糊面板后的游戏画面", "Blurs the game behind the panel"), new SettingToggle(() -> AddonConfig.panelBlur, value -> {
            AddonConfig.panelBlur = value;
            AddonConfig.save();
        }))
                .icon(ICON_BLUR)
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
        })).icon(ICON_SCROLL));
    }

    /** 行距与模块中心一致：四个页面的行节奏必须是同一个数（用户 2026-09-16 要求这两页「也一样」）。 */
    @Override
    protected float moduleGap() {
        return ModuleRow.ROW_GAP;
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
