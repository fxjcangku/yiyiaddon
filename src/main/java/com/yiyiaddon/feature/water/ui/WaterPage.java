package com.yiyiaddon.feature.water.ui;

import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import com.yiyiaddon.core.module.ModuleManager;

/**
 * 水源显示模块页：状态条 + 控制台入口的薄壳页（形态照管理员检测模块页，第 182 条）。
 *
 * <p>模块的 10 个设置项不在本页平铺：全部由整屏控制台 {@link WaterConsoleScreen} 承载
 * （旧项目无模块页，设置在旧框架模块列表内；本项目按第 181 条以控制台承载，分组名与
 * 设置项名称/描述逐字见 51 号第五节）。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。</p>
 */
public final class WaterPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照既有模块页口径） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明 */
    private static final String CONSOLE_HINT = "按分组承载全部 10 项设置：灌溉范围显示 / 建议放水点 / 高级选项";

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
        setHeader(new ModuleStatusBar(
            () -> module.isEnabled() ? "运行中" : "未启用",
            module::isEnabled,
            new KeybindBadge(module.keybindId()),
            new SettingToggle(module::isEnabled,
                value -> ModuleManager.setEnabled(module.id(), value))));

        // 控制台入口（第 182 条：控制台入口在前）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());
    }

    /** 打开控制台；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        net.minecraft.client.Minecraft client = net.minecraft.client.Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new WaterConsoleScreen(client.screen, module));
    }
}
