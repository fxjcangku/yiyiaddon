package com.yiyiaddon.feature.vision.ui;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.ui.console.VisionConsoleScreen;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;

/**
 * 透视模块页：控制台入口 + 内嵌使用说明的薄壳页（形态照水源显示 / 管理员检测模块页）。
 *
 * <p>全部设置项不在本页平铺，由整屏控制台 {@code VisionConsoleScreen} 的三个页签承载
 * （第 181 / 182 / 209 / 210 条）。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，构造函数里不碰任何游戏注册表；
 * 内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。</p>
 */
public final class VisionPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照既有模块页口径） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明 */
    private static final String CONSOLE_HINT = "承载方块与实体两个模式的全部设置：开关 / 目标选择 / 范围 / 框 / 射线 / 颜色";

    /** 内嵌说明行（去掉独立窗口的外框三行），铺在控制台入口下方，超出由模块页自身滚动条查看 */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(VisionHelpContent.SECTIONS));

    private final VisionModule module;

    /** 页面内容是否已构建（第 180 条时序约束） */
    private boolean built;

    public VisionPage(VisionModule module) {
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
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    /** 打开控制台；父屏是当前模块页，ESC / 返回回到这里 */
    private void openConsole() {
        net.minecraft.client.Minecraft client = net.minecraft.client.Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new VisionConsoleScreen(client.gui.screen(), module));
    }
}
