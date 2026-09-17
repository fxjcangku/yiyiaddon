package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.ModuleRow;
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
 *
 * <p><b>行样式与模块中心同一套</b>：用户 2026-09-16 看过本页截图后说「这些也要啊」——每行左侧补上
 * Material 图标、行高压到模块行那一档（{@link ModuleRow#HEIGHT} / {@link ModuleRow#ROW_GAP}），
 * 与模块中心、界面页合上同一个节奏。见 {@link SettingModule#icon(String)}。</p>
 */
public final class SettingsPage extends BasePage {

    /*
     * 行首图标。码点全部取自项目「已验证」集合（开发习惯第 140 条：字形直接进字体绘制，
     * 字体里没有就是一块豆腐），本页一个未验真的码点都没有引入。
     */
    /** 齿轮（settings）：通用的「配置项」；与 {@code IdConfigModule.ICON}、导航栏「设置」页签同字形。 */
    private static final String ICON_PREFIX = "\uE8B8";
    /** 键盘：指令前缀靠键盘敲入；与 {@code SettingModule.KEYBIND_ICON}（右侧按键块）同字形。 */
    private static final String ICON_KEYBIND = "\uE9FE";
    /** 书本（menu_book）：汉化＝文案与语言；取自 {@code EnchantModule} 注释里已验真的备选码点 U+EA19。 */
    private static final String ICON_LOCALISATION = "\uEA19";

    public SettingsPage(PageRouter router) {
        modules.add(new SettingModule(UiText.t("指令前缀", "Command Prefix"),
                UiText.t("客户端指令的起始符号，单个字符，默认 .　留空或填入 / 时回落到默认值", "Leading symbol of client commands, single character, default .　Falls back to default when blank or /"),
                new SettingTextBox(() -> AddonConfig.commandPrefix, value -> {
                    AddonConfig.commandPrefix = value;
                    AddonConfig.save();
                }, 1)).icon(ICON_PREFIX));

        // 说明里写明「已绑定时点击即清空」：界面快捷键过去只能重录、清不掉（第 214 条），
        // 现在可清；提示逐字写清交互，避免玩家找不到取消入口
        modules.add(new SettingModule(UiText.t("GUI 快捷键", "GUI Keybind"),
                UiText.t("点击右侧按键块后按下任意键完成录入；已绑定时点击即清空（本键是打开界面的唯一快捷键）",
                        "Click the key block, then press any key to bind; clicking while bound clears it (this is the only hotkey that opens the GUI)"), null)
                .keybindAction(ModuleKeybindManager.ACTION_CLICK_GUI).icon(ICON_KEYBIND));

        modules.add(new SettingModule(UiText.t("Baritone 汉化", "Baritone Localisation"), UiText.t("把 Baritone 的命令与提示显示为中文", "Shows Baritone commands and messages in Chinese"), new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
            AddonConfig.baritoneChinese = value;
            AddonConfig.save();
        }))
                .icon(ICON_LOCALISATION)
                .addSub(UiText.t("当前状态", "Current State"), UiText.t("只读展示汉化开关", "Read-only display of the localisation switch"),
                        new SettingToggle(() -> AddonConfig.baritoneChinese, value -> {
                        })));

        // 配置页面入口：走与模块中心同一条路由（同一个 token），返回/记忆导航都一致。
        // 图标取该分类注册的图标（AddonModules 已验真的那一批），与模块中心的页面入口行同一字形
        for (ModuleCategory category : CategoryRegistry.all()) {
            if (category.page() == null || !category.settingsEntry()) continue;
            modules.add(new SettingModule(category.displayName(), category.description(),
                    new SettingLink(() -> UiText.t("点击进入", "Open"), () ->
                            router.open(category.page().get(),
                                    UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id()))))
                    .icon(category.icon()));
        }
    }

    /** 行距与模块中心一致：四个页面的行节奏必须是同一个数（用户 2026-09-16 要求这两页「也一样」）。 */
    @Override
    protected float moduleGap() {
        return ModuleRow.ROW_GAP;
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
