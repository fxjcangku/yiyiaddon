package com.yiyiaddon.feature.tactical.ui;

import com.yiyiaddon.feature.tactical.ServerDetectorModule;
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
 * 服务器检测模块页：控制台入口 + 内嵌使用说明，形态照 {@code FlightBypassPage} / {@code TeleportPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目对应物
 * {@code tactical/ServerDetector.java:157-202} 的五个章节（检测功能 / 使用方式 / 资源包模式 /
 * 检测原理 / 注意事项），逐字保留，<b>承载方式由弹窗改为内嵌</b>（旧项目这五章来自弹窗
 * {@code HelpScreen}；用户 2026-09-17 口径：不再单摆「查看使用说明」按钮）。</p>
 *
 * <p><b>设置不铺在本页</b>：8 项设置全部由整屏控制台 {@link ServerDetectorConsoleScreen}
 * 按旧项目原 2 个设置组名分页承载（概览 / 底裤侦测 / 资源包劫持），本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建。</p>
 */
public final class ServerDetectorPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照传送 / 自动挖矿 / 飞行绕过模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 底裤侦测 / 资源包劫持";

    /**
     * 使用说明章节：正文逐字照旧 {@code tactical/ServerDetector.java} 的五个章节，一字未改
     * （含树形分隔行与全部颜色码；框线与 {@code [#]} 格式由 {@link HelpPanelScreen} 生成）。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("检测功能",
            "§8├─ §f服务器核心识别",
            "§8│   §7Paper / Purpur / Leaves / Folia / 混合端",
            "§8│   §7代理层识别（Velocity / BungeeCord / Waterfall）",
            "§8│",
            "§8├─ §f反作弊检测",
            "§8│   §7通过指令树与插件频道识别",
            "§8│   §7覆盖国际主流与国内常见实现",
            "§8│   §7GrimAC / Matrix / Vulcan / Spartan / AAC等",
            "§8│",
            "§8└─ §f资源包处理",
            "§8    §7自动下载到本地（支持断点续传）",
            "§8    §7暴力绕过：自动拒绝或接受"
        ),
        new HelpPanelScreen.HelpSection("使用方式",
            "§a[1] §f加入服务器时自动启动检测",
            "§a[2] §f等待 §e3-5秒 §f让服务器发送完整信息",
            "§a[3] §f检测完成后在聊天栏显示结果",
            "§a[4] §f结果上报协调器供飞行/发包策略统一决策"
        ),
        new HelpPanelScreen.HelpSection("资源包模式",
            "§6▸ §f暴力绕过 §8- §7自动回应并拦截所有资源包",
            "§6▸ §f自动白嫖 §8- §7下载到本地（支持断点续传）",
            "§6▸ §f原版处理 §8- §7不干预，走原版弹窗",
            "§8（星露谷农场复用同一套下载/缓存能力，不受本模块开关影响）"
        ),
        new HelpPanelScreen.HelpSection("检测原理",
            "§8├─ §7Brand字符串 §8- §7最容易被改，只作线索",
            "§8├─ §7插件消息频道 §8- §7反作弊开的校验频道",
            "§8├─ §7指令树命名空间 §8- §7插件注册的实际结果（主要依据）",
            "§8└─ §7拉回频率 §8- §7说明反作弊存在且激进"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "§c⚠ §f检测结果不是100%准确，仅供参考",
            "§c⚠ §f资源包下载需要网络连接，国外服务器可能较慢",
            "§c⚠ §f暴力绕过可能被强制资源包的服务器踢出",
            "§c⚠ §f单人世界自动禁用，仅在多人服务器生效"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final ServerDetectorModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public ServerDetectorPage(ServerDetectorModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照飞行绕过 / 传送模块页）
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
        client.setScreen(new ServerDetectorConsoleScreen(client.screen, module));
    }
}
