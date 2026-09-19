package com.yiyiaddon.feature.reconnect.ui;

import com.yiyiaddon.feature.reconnect.AutoReconnectModule;
import com.yiyiaddon.feature.reconnect.config.ReconnectTexts;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;

/**
 * 自动重连模块页：控制台入口 + 内嵌使用说明（形态照自动骨粉 / 自动登入等模块页）。
 *
 * <p>顶部信息块即模块屏幕自己的标题区（模块名 + 说明副标题），本页内容因此只有两段：
 * 提示行 + 居中的「§b打开控制台」按钮，以及紧贴按钮下方的说明正文；说明超出一屏时由本页自身
 * 的滚动条上下查看（第 209 / 210 条）。</p>
 *
 * <p>设置不铺在本页：六项设置全部由整屏控制台 {@link ReconnectConsoleScreen} 的
 * 「{@link ReconnectTexts#GROUP_SETTINGS}」页承载。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 在模组初始化阶段被调用一次，因此构造函数不碰任何游戏
 * 注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建（第 180 条）。</p>
 */
public final class ReconnectPage extends CompactModulePage implements ModulePage {

    /** 控制台入口说明：写清分页构成 */
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / " + ReconnectTexts.GROUP_SETTINGS;

    /** 说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方 */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(ReconnectTexts.helpSections()));

    private final AutoReconnectModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public ReconnectPage(AutoReconnectModule module) {
        this.module = module;
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            build();
        }
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    // ── 构建 ──

    private void build() {
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(ReconnectTexts.BTN_CONSOLE, this::openConsole)).centeredControl());

        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new ReconnectConsoleScreen(client.screen, module));
    }
}
