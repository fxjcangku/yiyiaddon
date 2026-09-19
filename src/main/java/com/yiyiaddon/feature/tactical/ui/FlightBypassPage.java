package com.yiyiaddon.feature.tactical.ui;

import com.yiyiaddon.feature.tactical.FlightBypassModule;
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
 * 飞行绕过模块页：控制台入口 + 内嵌使用说明，形态照 {@code TeleportPage} / {@code AutoMinerPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目对应物
 * {@code tactical/FlightBypass.java} 的三个说明章节（{@code 飞行模式（26.1.2 官方机制依据）} /
 * {@code 协调器统一决策} / {@code 注意事项}），逐字保留，<b>承载方式由弹窗改为内嵌</b>
 * （旧项目这三章来自弹窗 {@code HelpScreen}；用户 2026-09-17 口径：不再单摆「查看使用说明」按钮）。</p>
 *
 * <p><b>设置不铺在本页</b>：6 项设置全部由整屏控制台 {@link FlightBypassConsoleScreen}
 * 按旧项目原 2 个设置组名分页承载（概览 / 模式选择 / 参数调整），本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建。</p>
 */
public final class FlightBypassPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照传送 / 自动挖矿 / 管理员检测模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 模式选择 / 参数调整";

    /**
     * 使用说明章节：正文逐字照旧 {@code tactical/FlightBypass.java} 的三个章节，一字未改
     * （含树形分隔行与全部颜色码；框线与 {@code [#]} 格式由 {@link HelpPanelScreen} 生成）。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("飞行模式（26.1.2 官方机制依据）",
            "§8├─ §e发包飞行 §8- §7需服务端授予飞行能力（/fly/创造/旁观）",
            "§8│   §7协调器校验 abilities 后放行，置位飞行态并同步服务端",
            "§8│   §7未授权自动切换对应的降级模式，不会停摆",
            "§8│   §7起飞后按跳跃键上升、潜行键下降（原版飞行操作）",
            "§8│",
            "§8├─ §e原版连跳（非飞行） §8- §7落地即跳的连续兔子跳 + 疾跑推进",
            "§8│   §7地面接触由原版物理重置浮空计时，全服合法",
            "§8│",
            "§8├─ §e安全滑翔 §8- §7自动换鞘翅 + 官方起伞",
            "§8│   §7fallFlying 豁免浮空判定，速度容忍 300 m/t",
            "§8│",
            "§8├─ §e烟花火箭 §8- §7滑翔中周期性使用烟花推进",
            "§8│   §7服务端完全合法，需背包有烟花与鞘翅",
            "§8│",
            "§8└─ §e序列垫脚 §8- §7真实放置方块提供物理支撑",
            "§8    §7延迟拆除并周期性留痕，需主手方块"
        ),
        new HelpPanelScreen.HelpSection("协调器统一决策",
            "§a[1] §f拉回冷却期 §8- §7全模式统一暂停 2 秒",
            "§a[2] §f连续拉回 §8- §7沿降级链逐档降级",
            "§8    §f发包飞行/烟花火箭 → 安全滑翔 → 原版连跳",
            "§a[3] §f脱离危险窗口 §8- §7每 10 秒恢复一档",
            "§a[4] §f高风险反作弊 §8- §7发包飞行自动降级到可执行模式"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "§c⚠ §f发包飞行需要服务器开 /fly 或创造/旁观权限",
            "§c⚠ §f发包飞行水平速度由服务端规则决定，想更快请用烟花火箭模式",
            "§c⚠ §f安全滑翔与烟花火箭需要背包里有鞘翅",
            "§c⚠ §f序列垫脚需要主手持有可放置方块",
            "§c⚠ §f报警恢复全程由协调器裁决，模块不自行切换模式"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final FlightBypassModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public FlightBypassPage(FlightBypassModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照传送 / 自动挖矿模块页）
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
        client.setScreen(new FlightBypassConsoleScreen(client.screen, module));
    }
}
