package com.yiyiaddon.feature.tactical.ui;

import com.yiyiaddon.feature.tactical.AntiKickBypassModule;
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
 * 发包防踢模块页：控制台入口 + 内嵌使用说明，形态照 {@code FlightBypassPage} / {@code ServerDetectorPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目对应物
 * {@code tactical/AntiKickBypass.java:345-378} 的四个章节（功能概览 / 与协调器的联动（重构后）/
 * 伪装 masa 全家桶 / 投影类模组（26.1.2 实测机制）/ 注意事项），逐字保留，
 * <b>承载方式由弹窗改为内嵌</b>（旧项目这四章来自弹窗 {@code HelpScreen}；用户 2026-09-17 口径：
 * 不再单摆「查看使用说明」按钮）。</p>
 *
 * <p><b>设置不铺在本页</b>：18 项设置全部由整屏控制台 {@link AntiKickBypassConsoleScreen}
 * 按旧项目原 6 个设置组名分页承载，本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建。</p>
 */
public final class AntiKickBypassPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照飞行绕过 / 服务器检测 / 传送模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / 伪装客户端 / 聊天排队 / 防挂机 / 限制发包 / 拉回分析 / 模拟真人";

    /**
     * 使用说明章节：正文逐字照旧 {@code tactical/AntiKickBypass.java} 的四个章节，一字未改
     * （含树形分隔行与全部颜色码；框线与 {@code [#]} 格式由 {@link HelpPanelScreen} 生成）。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("功能概览",
            "§8├─ §f伪装客户端 §8- §7改Brand、拦截Mod频道、摘假潜行/假疾跑",
            "§8├─ §f聊天排队 §8- §7自动排队防刷屏",
            "§8├─ §f防挂机 §8- §7假装在操作",
            "§8├─ §f限制发包 §8- §7防止挖太快/放太快被踢",
            "§8├─ §f拉回分析 §8- §7记录什么操作容易被拉回",
            "§8└─ §f模拟真人 §8- §7视角抖动、网络延迟"
        ),
        new HelpPanelScreen.HelpSection("与协调器的联动（重构后）",
            "§a[1] §f拉回冷却/统计 §8- §7统一由协调器登记，本模块只做分析",
            "§a[2] §f高风险反作弊 §8- §7协调器确认后限速自动收紧至 50%",
            "§a[3] §f服务器卡顿 §8- §7只读协调器状态，挖掘/放置即时停发",
            "§a[4] §f不再回发拉回确认包 §8- §726.1.2 原版客户端已自动确认"
        ),
        new HelpPanelScreen.HelpSection("伪装 masa 全家桶 / 投影类模组（26.1.2 实测机制）",
            "§a[1] §f官方已移除模组列表握手 §8- §726.1.2 服务器读不到 fabric 模组列表本身",
            "§a[2] §f频道注册走 fabric 自建 register §8- §7Mod通信开关全拦非原版频道",
            "§a[3] §fBrand 伪装 vanilla §8- §7查客户端名字只会得到原版",
            "§a[4] §f假潜行 / 假疾跑 §8- §7摘除 Tweakeroo 行为特征",
            "§c⚠ §f进服前必须已开启本模块 §8- §7频道注册发生在进服瞬间的配置阶段"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "§c⚠ §f单人世界自动禁用，多人世界自动启用",
            "§c⚠ §f拉回分析会记录大量数据，调试完记得关闭",
            "§c⚠ §f模拟真人功能会影响操作手感，按需开启",
            "§c⚠ §f全拦非原版频道会导致 fabric 服务器认不出你装了 fabric，生电服需要 fabric 频道功能时请暂时关闭 Mod通信开关"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final AntiKickBypassModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public AntiKickBypassPage(AntiKickBypassModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照飞行绕过 / 服务器检测模块页）
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
        client.gui.setScreen(new AntiKickBypassConsoleScreen(client.gui.screen(), module));
    }
}
