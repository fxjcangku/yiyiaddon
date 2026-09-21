package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.ui.console.AutoFarmConsoleScreen;
import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 自动农场模块页：保留旧项目模块页的元素（标题 / 状态摘要 / 使用说明 / 水源显示开关），
 * 外加控制台入口（D2 拍板）；六张点位卡统一由控制台「点位」页承载（用户 2026-09-17 口径，见 {@link #build()}），
 * 使用说明正文内嵌在控制台入口下方（同一批用户口径）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：标题 {@code §b§l自动农场 §r§8▸ §f全自动农业系统}
 * 与状态摘要 {@link AutoFarmModule#statusSummary()}（旧 {@code getWidget :692-696}）、水源开关
 * {@code §a关闭水源显示 / §b打开水源显示}（旧 {@code :705-714}，行为 = 切换水源显示模块并关闭当前界面）；
 * 六张点位卡的标题颜色 / {@code §8暂未绑定} / {@code §8-} / 坐标与维度行 / {@code 设置} / {@code §c删除}
 * 按钮（旧 {@code buildLocationCard :757-796} 逐字）现由控制台「点位」页呈现。</p>
 *
 * <p><b>控制台入口</b>是本项目新增壳件（D2）：文案与样式沿用 {@code §b打开控制台} 既有口径；
 * 旧项目模块页没有整屏控制台，此处只加一行入口，不新增任何业务设置。</p>
 */
public final class AutoFarmPage extends CompactModulePage implements ModulePage {

    // ── 按钮文案（逐字） ──
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 设置 / 逐作物 / 点位 / 日志";

    /**
     * 内嵌说明行（去掉窗口外框三行）：本页把使用说明直接铺在「打开控制台」入口下方
     * （用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，超出可上下滚动查看）。
     *
     * <p>章节标题与正文来自 {@link AutoFarmHelpContent}（旧项目帮助正文逐字），此处只去掉独立窗口的
     * 外框三行（{@code §8┏━┓ / §8┃ 使用说明 ┃ / §8┗━┛}），一格文案未改。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(AutoFarmHelpContent.SECTIONS));

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
            // 页面要显示磁盘上本服的锚点与设置：换服后先重读，再构建（与自动挖矿 reloadStore 同一时机）
            module.refreshScopedSettings();
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
        addCore(new TextLine("§b§l自动农场 §r§8▸ §f全自动农业系统"));
        addCore(new TextLine(module::statusSummary));
        addCore(new TextLine(" "));

        // 控制台入口（D2 本项目新增壳件，整屏分页）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明内嵌在控制台入口下方（用户 2026-09-17 口径：删掉「§e查看使用说明」按钮，
        // 正文直接铺开、超出由模块页滚动条上下查看）；旧的独立说明窗口与它的按钮提示一并撤掉。
        for (String line : HELP_LINES) addCore(new TextLine(line));

        // 水源显示快捷开关（旧 :705-714：切水源模块并关当前界面）
        addCore(new CompactRow("", () -> "独立于自动农场，不开农场也能单独用",
            new Button(waterButtonLabel(), this::toggleWater)).centeredControl());

        // 六点位卡片不在这里（用户 2026-09-17：「控制台里面已经有点位了 为什么控制台外面还有 自动农村也有这个问题」）：
        // 旧项目把六张卡挂在模块配置页（旧 :717-734 两列三行），本项目点位设置统一由控制台「点位」页承载
        // （FarmPointCards 同一份实现、同一套按钮行为），同一份点位不在两处重复摆；卡片文案与「设置 / 删除」
        // 行为一字未减，只是承载位置收敛到控制台（与村民交易 D-14-16 同一口径）。
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
            client.gui.setScreen(null);
        }
    }

    private static WaterESPModule waterModule() {
        return ModuleManager.byId(WaterESPModule.MODULE_ID) instanceof WaterESPModule water ? water : null;
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        // 控制台「点位」页读的是锚点，打开前按当前服务器重读一次（页面构建只走一次，这里每次都过）
        module.refreshScopedSettings();
        client.gui.setScreen(new AutoFarmConsoleScreen(client.gui.screen(), module));
    }
}
