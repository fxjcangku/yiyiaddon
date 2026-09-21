package com.yiyiaddon.feature.teleport.ui;

import com.yiyiaddon.feature.teleport.TeleportModule;
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
 * 传送模块页：控制台入口 + 内嵌使用说明，形态照 {@code AdminDetectorPage} / {@code AutoMinerPage}。
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；使用说明正文来自旧项目
 * {@code TeleportModule.getWidget}（{@code :332-348}）的 2 条标题 + 7 条条目，共 9 条，
 * 这里只做**章节切分**（功能 / 触发与指令 / 验证与载具），一行文字都没有改。</p>
 *
 * <p><b>设置不铺在本页</b>：15 项设置与 3 个功能键全部由整屏控制台
 * {@link TeleportConsoleScreen} 按旧项目原 6 个设置组名分页承载
 * （概览 / 触发按键 / TP地面 / TP穿墙 / TP坐标 / 验证与调试）。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，
 * 因此构造函数里不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)} 构建。</p>
 */
public final class TeleportPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照自动挖矿 / 管理员检测模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 触发按键 / TP地面 / TP穿墙 / TP坐标 / 验证与调试";

    /** 旧面板标题两行中的第二行（第一行与模块名重复，作为引语放在最前） */
    private static final String HELP_LEAD = "§b§l传送 ▸ 三模式安全传送";

    /**
     * 使用说明章节：正文逐字照旧 {@code getWidget} 的 7 条条目，仅按用途分到 3 个章节下。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("功能",
            "  " + HELP_LEAD,
            "  §7基于 26.1.2 真实碰撞形状判据 + 服务端回弹验证",
            "  §8▸ §fTP地面 §8▸ 回到头顶真正的露天地面（洞穴脱身）",
            "  §8▸ §fTP穿墙 §8▸ 沿准星方向智能落点：可穿墙/门窗/半砖，山体建筑直接穿过",
            "  §8▸ §f方向赶路 §8▸ 前方无墙也能前进，连续按键逐步赶路，理想落点被占自动就近修正"
        ),
        new HelpPanelScreen.HelpSection("触发与指令",
            "  §8▸ §fTP坐标 §8▸ 传送到配置坐标，或指令 .tp X Y Z",
            "  §8▸ §f独立按键 §8▸ 三个功能各绑一个按键（松开触发）"
        ),
        new HelpPanelScreen.HelpSection("验证与载具",
            "  §8▸ §f回弹验证 §8▸ 服务端拉回时自动跟随并播报偏差",
            "  §8▸ §f载具支持 §8▸ 乘坐本机权威载具时整体位移传送"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final TeleportModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public TeleportPage(TeleportModule module) {
        this.module = module;
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
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

    // ── 构建 ──

    private void build() {
        // ① 控制台入口（提示行 + 居中按钮，形态照自动挖矿 / 管理员检测模块页）
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
        // 控制台显示的是本服的坐标与阈值：打开前按当前服务器重读一次（页面构建只走一次，这里每次都过）
        module.refreshScopedSettings();
        client.setScreen(new TeleportConsoleScreen(client.screen, module));
    }
}
