package com.yiyiaddon.feature.librarian.ui;

import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.feature.librarian.ui.console.LibrarianConsoleScreen;
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
 * 自动图书管理员模块页：只做「入口」——一行状态摘要 + 控制台入口 + 内嵌使用说明。
 *
 * <p><b>元素顺序（第 182/209/210 条）</b></p>
 * <ol>
 *   <li>状态行 {@link TextLine}：编排器状态中文名 + 目标附魔条数 / 搜索半径 / 最高价格
 *       （取 {@link AutoLibrarianModule#statusSummary()}，控制台概览页同一份句子，不各拼一份）；
 *       <b>本页没有顶部信息块</b>——状态文字 / 快捷键徽章 / 模块开关三件统一在控制台顶栏
 *       （{@code ConsoleHeaderBar}）；</li>
 *   <li>控制台入口 {@link CompactRow} + {@code §b打开控制台}（既有模块页同一文案与形态）；</li>
 *   <li>使用说明正文（{@link AutoLibrarianHelpContent} 五章节逐字）内嵌在入口正下方，
 *       超出时由模块页自身滚动条上下查看（第 210 条：说明内嵌，不再单摆按钮）。</li>
 * </ol>
 *
 * <p>旧项目本模块页只有一个「查看使用说明」按钮（旧 {@code :81}）——没有状态、没有配置入口；
 * 本项目按统一壳件补齐状态行与控制台入口，说明正文一字未减（只去掉了独立窗口的外框三行）。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 在模组初始化阶段就会被调用一次，构造函数里不碰
 * 任何游戏注册表与配置文件；页面内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。</p>
 */
public final class AutoLibrarianPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照既有模块页口径） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 控制台入口提示：说明本控制台承载什么（不虚构页签名） */
    private static final String CONSOLE_HINT = "整屏承载全部设置项、目标附魔选择器与运行日志";

    /** 内嵌说明行（去掉独立窗口的外框三行，正文与章节逐字） */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(AutoLibrarianHelpContent.SECTIONS));

    private final AutoLibrarianModule module;

    /** 页面内容是否已构建（第 180 条时序约束） */
    private boolean built;

    public AutoLibrarianPage(AutoLibrarianModule module) {
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

    private void build() {
        // 状态摘要（现读，未启用时给灰字）
        addCore(new TextLine(module::statusSummary));

        // 控制台入口（第 182 条：排在说明之前）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明内嵌在控制台入口下方（第 210 条），章节与正文逐字
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    /** 打开控制台（整屏分页）；ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new LibrarianConsoleScreen(module));
    }
}
