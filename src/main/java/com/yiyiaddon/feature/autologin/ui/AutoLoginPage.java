package com.yiyiaddon.feature.autologin.ui;

import com.yiyiaddon.feature.autologin.AutoLoginModule;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
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
 * 自动登入模块页：控制台入口 + 内嵌使用说明，形态照 {@code BonemealPage} / {@code FlightBypassPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看（第 209 / 210 条）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目
 * {@code autologin/AutoLoginModule.java:236-276} 的 {@code buildHelpContent} 六章
 * （快速上手 / 自动流程 / 自用配置 / 路线与指令 / 状态说明 / 注意事项），标题与正文一并逐字保留
 * （集中在 {@link AutoLoginTexts}），<b>承载方式由「查看使用说明」独立窗口改为模块页内嵌说明</b>
 * （用户 2026-09-17 口径：不再单摆「查看使用说明」按钮；框线与 {@code [#]} 章节格式由
 * {@link HelpPanelScreen} 生成，与其它模块页同一套）。</p>
 *
 * <p><b>设置不铺在本页</b>：全部设置由整屏控制台 {@link AutoLoginConsoleScreen} 按旧项目原 5 个设置组
 * 承载，本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建（第 180 条）。</p>
 */
public final class AutoLoginPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照飞行绕过 / 发包秒破 / 自动骨粉模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成（页签名即控制台 {@code Tab} 的标题，同源文案） */
    private static final String CONSOLE_HINT =
        "按旧项目设置组分页：概览 / 登录认证 / 自动重连 / 自动执行指令 / 进服路线 / 自用配置 / 账号与调试";

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     *
     * <p>说明六章与旧 {@code buildHelpContent} 逐字同源（{@link AutoLoginTexts#helpSections()}），
     * 静态初始化即完成，不触碰注册表。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(AutoLoginTexts.helpSections()));

    private final AutoLoginModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public AutoLoginPage(AutoLoginModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照其它模块页）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明内嵌在控制台入口下方，章节标题与正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new AutoLoginConsoleScreen(client.screen, module));
    }
}
