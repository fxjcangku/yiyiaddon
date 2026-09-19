package com.yiyiaddon.feature.combat.ui;

import com.yiyiaddon.feature.combat.KillAuraModule;
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
 * 杀戮光环模块页：状态条 + 一个「打开控制台」入口 + 内嵌使用说明的薄壳页（形态照星露谷
 * {@code StardewResourcePanelPage} 与自动挖矿 {@code AutoMinerPage}）。
 *
 * <p>模块的 26 项设置不在这里平铺：全部由整屏控制台 {@link KillAuraConsoleScreen} 按用途分页承载
 * （概览 / 常规 / 目标 / 时机），本页只负责入口、模块开关与使用说明。</p>
 *
 * <p><b>使用说明内嵌在「打开控制台」正下方</b>（第 210 条 / 用户 2026-09-17：「杀截也是」）：
 * 内容来自 {@link KillAuraHelpContent}，由 {@code KillAuraTexts} 的 26 项名称与描述、概览页数据行、
 * 控制台页脚按钮文案组装而成（旧项目是旧框架原生模块，本模块的中文文案本就是本项目落盘的，
 * 故说明只能组装、不新写句子）；超出时由模块页自身滚动条上下查看。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次
 * （{@code ModuleEntries.of:35}），因此构造函数里<b>不碰任何游戏注册表</b>——不构造
 * {@code ItemStack}、不取物品 / 实体类型的显示名、不建候选表；页面内容推迟到
 * {@link #createPage(ModuleEntry)}（用户真正打开页面时）才构建。</p>
 */
public final class KillAuraPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照星露谷模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明（本任务新造文案） */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 常规 / 目标 / 时机";

    /**
     * 内嵌说明行（去掉窗口外框三行）：铺在「打开控制台」入口下方，超出可上下滚动查看。
     *
     * <p>章节标题与正文出自 {@link KillAuraHelpContent}（逐字引用 {@code KillAuraTexts} 与
     * 控制台页面上的既有文案），此处只去掉独立窗口的外框三行
     * （{@code §8┏━┓ / §8┃ 使用说明 ┃ / §8┗━┛}）。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(KillAuraHelpContent.SECTIONS));

    private final KillAuraModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public KillAuraPage(KillAuraModule module) {
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
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明内嵌在控制台入口下方（第 210 条），章节标题与正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new KillAuraConsoleScreen(client.screen, module));
    }
}
