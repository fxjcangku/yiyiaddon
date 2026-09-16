package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.ui.console.AutoFarmConsoleScreen;
import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 自动农场模块页：保留旧项目模块页的全部元素（标题 / 状态摘要 / 使用说明 / 水源显示开关 /
 * 六张点位卡片），外加控制台入口（D2 拍板）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：标题 {@code §b§l自动农场 §r§8▸ §f全自动农业系统}
 * 与状态摘要 {@link AutoFarmModule#statusSummary()}（旧 {@code getWidget :692-696}）、按钮
 * {@code §e查看使用说明}（旧 {@code :700}）、水源开关 {@code §a关闭水源显示 / §b打开水源显示}
 * （旧 {@code :705-714}，行为 = 切换水源显示模块并关闭当前界面）、六张点位卡的
 * 标题颜色 / {@code §8暂未绑定} / {@code §8-} / 坐标与维度行 / {@code 设置} / {@code §c删除}
 * 按钮（旧 {@code buildLocationCard :757-796} 逐字）。</p>
 *
 * <p><b>控制台入口</b>是本项目新增壳件（D2）：文案与样式沿用 {@code §b打开控制台} 既有口径；
 * 旧项目模块页没有整屏控制台，此处只加一行入口，不新增任何业务设置。</p>
 */
public final class AutoFarmPage extends CompactModulePage implements ModulePage {

    // ── 按钮文案（逐字） ──
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 设置 / 逐作物 / 点位 / 日志";
    private static final String HELP_BUTTON = "§e查看使用说明";
    private static final String HELP_HINT = "打开自动农场的完整使用说明";

    private final AutoFarmModule module;

    /** 页面内容是否已构建（打开页面时才构建，第 180 条） */
    private boolean built;

    public AutoFarmPage(AutoFarmModule module) {
        this.module = module;
    }

    /** 页面内容在「真正打开页面」时才构建（第 180 条时序约束，参照 {@code AutoMinerPage}） */
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

    // ── 构建（旧 getWidget :690-736 的元素顺序：标题 → 状态摘要 → 空行 → 按钮 → 卡片） ──

    private void build() {
        setHeader(new ModuleStatusBar(
            () -> module.isEnabled() ? "运行中" : "未启用",
            module::isEnabled,
            new KeybindBadge(module.keybindId()),
            new SettingToggle(module::isEnabled,
                value -> ModuleManager.setEnabled(module.id(), value))));

        addCore(new TextLine("§b§l自动农场 §r§8▸ §f全自动农业系统"));
        addCore(new TextLine(module::statusSummary));
        addCore(new TextLine(" "));

        // 控制台入口（D2 本项目新增壳件，整屏分页）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明（旧 :700-701）
        addCore(new CompactRow("", () -> HELP_HINT,
            new Button(HELP_BUTTON, this::openHelp)).centeredControl());

        // 水源显示快捷开关（旧 :705-714：切水源模块并关当前界面）
        addCore(new CompactRow("", () -> "独立于自动农场，不开农场也能单独用",
            new Button(waterButtonLabel(), this::toggleWater)).centeredControl());

        // 六点位卡片（两列三行；旧 :717-734 的配对顺序原样；与控制台「点位」页共用 FarmPointCards）
        addCore(new PointCardGrid.Grid(FarmPointCards.all(module)));
    }

    /** 水源开关按钮文案：随模块实况切换（旧 :708 逐字） */
    private static String waterButtonLabel() {
        WaterESPModule water = waterModule();
        boolean waterActive = water != null && water.isEnabled();
        return waterActive ? "§a关闭水源显示" : "§b打开水源显示";
    }

    /** 切换水源显示模块并关闭当前界面（旧 :709-713 行为原样） */
    private void toggleWater() {
        Minecraft client = Minecraft.getInstance();
        WaterESPModule water = waterModule();
        if (water != null && client != null) {
            ModuleManager.setEnabled(WaterESPModule.MODULE_ID, !water.isEnabled());
            client.setScreen(null);
        }
    }

    private static WaterESPModule waterModule() {
        return ModuleManager.byId(WaterESPModule.MODULE_ID) instanceof WaterESPModule water ? water : null;
    }

    // ── 界面跳转 ──

    /** 打开使用说明（旧 :701）：窗口标题为「自动农场 - 使用说明」 */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(AutoFarmModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(AutoFarmHelpContent.SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new AutoFarmConsoleScreen(client.screen, module));
    }
}
