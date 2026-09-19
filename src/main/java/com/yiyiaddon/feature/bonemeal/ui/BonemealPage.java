package com.yiyiaddon.feature.bonemeal.ui;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
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
 * 自动骨粉模块页：控制台入口 + 内嵌使用说明，形态照 {@code FlightBypassPage} / {@code PacketBreakPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看（第 209 / 210 条）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目对应物
 * {@code bonemeal/AutoBoneMeal.java:268-296} 的 {@code buildInfoWidget} 五个章节
 * （准备 / 触发模式 / 防作弊 / 背包补给 / 准星提示），标题与正文一并逐字保留，
 * <b>承载方式由面板内嵌信息块改为模块页内嵌说明</b>（用户 2026-09-17 口径：不再单摆「查看使用说明」按钮；
 * 框线与 {@code [#]} 章节格式由 {@link HelpPanelScreen} 生成，与其它模块页同一套）。</p>
 *
 * <p><b>设置不铺在本页</b>：21 项设置全部由整屏控制台 {@link BonemealConsoleScreen}
 * 按旧项目原 3 个设置组 + 基础参数分组承载，本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建（第 180 条）。</p>
 */
public final class BonemealPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照飞行绕过 / 发包秒破 / 传送模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / 基础参数 / 目标方块 / 防作弊绕过 / ESP渲染";

    /**
     * 使用说明章节：标题与正文逐字照旧 {@code buildInfoWidget} 的五个段落，一字未改
     * （含 {@code ▌} 小节标题与全部颜色码；旧面板的总标题 {@code §l自动骨粉 · 使用说明}
     * 由模块页标题承载，不再重复）。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection(BonemealTexts.HELP_SECTION_PREPARE,
            BonemealTexts.HELP_PREPARE),
        new HelpPanelScreen.HelpSection(BonemealTexts.HELP_SECTION_TRIGGER,
            BonemealTexts.HELP_TRIGGER),
        new HelpPanelScreen.HelpSection(BonemealTexts.HELP_SECTION_ANTICHEAT,
            BonemealTexts.HELP_ANTICHEAT),
        new HelpPanelScreen.HelpSection(BonemealTexts.HELP_SECTION_SUPPLY,
            BonemealTexts.HELP_SUPPLY),
        new HelpPanelScreen.HelpSection(BonemealTexts.HELP_SECTION_CROSSHAIR,
            BonemealTexts.HELP_CROSSHAIR)
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final AutoBoneMealModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public BonemealPage(AutoBoneMealModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照飞行绕过 / 发包秒破模块页）
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
        client.gui.setScreen(new BonemealConsoleScreen(client.gui.screen(), module));
    }
}
