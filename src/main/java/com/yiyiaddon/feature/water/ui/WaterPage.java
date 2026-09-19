package com.yiyiaddon.feature.water.ui;

import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;

/**
 * 水源显示模块页：状态条 + 控制台入口 + 内嵌使用说明的薄壳页（形态照管理员检测模块页，第 182 条）。
 *
 * <p>模块的 10 个设置项不在本页平铺：全部由整屏控制台 {@link WaterConsoleScreen} 承载
 * （旧项目无模块页，设置在旧框架模块列表内；本项目按第 181 条以控制台承载，分组名与
 * 设置项名称/描述逐字见 51 号第五节）。</p>
 *
 * <p><b>使用说明内嵌在「打开控制台」正下方</b>（第 210 条 / 用户 2026-09-17：「水源提示没有使用说明」）：
 * 内容来自 {@link WaterHelpContent}，全部由本模块已有的中文界面文案组装，超出时由模块页自身滚动条
 * 上下查看；本页不再单摆「查看使用说明」按钮。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。</p>
 */
public final class WaterPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照既有模块页口径） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明 */
    private static final String CONSOLE_HINT = "按分组承载全部 10 项设置：灌溉范围显示 / 建议放水点 / 高级选项";

    /**
     * 内嵌说明行（去掉窗口外框三行）：铺在「打开控制台」入口下方，超出可上下滚动查看。
     *
     * <p>章节标题与正文出自 {@link WaterHelpContent}（本模块既有文案组装，逐字，无新写句子），
     * 此处只去掉独立窗口的外框三行（{@code §8┏━┓ / §8┃ 使用说明 ┃ / §8┗━┛}）。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(WaterHelpContent.SECTIONS));

    private final WaterESPModule module;

    /** 页面内容是否已构建（第 180 条时序约束） */
    private boolean built;

    public WaterPage(WaterESPModule module) {
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
        // 控制台入口（第 182 条：控制台入口在前）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明内嵌在控制台入口下方（第 210 条），章节标题与正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    /** 打开控制台；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        net.minecraft.client.Minecraft client = net.minecraft.client.Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new WaterConsoleScreen(client.gui.screen(), module));
    }
}
