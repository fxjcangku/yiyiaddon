package com.yiyiaddon.feature.packetbreak.ui;

import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
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
 * 发包秒破模块页：控制台入口 + 内嵌使用说明，形态照 {@code FlightBypassPage} / {@code AntiKickBypassPage}。
 *
 * <p><b>顶部信息块</b>即模块屏幕自己的标题区（模块名 + 模块说明副标题，由 {@code ModuleScreen}
 * 统一绘制），本页内容因此只有两段：提示行 + 居中的「打开控制台」按钮，以及紧贴按钮下方的说明正文；
 * 说明超出一屏时由本页自身的滚动条上下查看（第 209 / 210 条）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：按钮 {@code §b打开控制台}；说明正文是旧项目对应物
 * {@code tactical/packetbreak/PacketInstantBreak.java:317-357} 的五个章节（破坏方式（26.1.2 真实机制）/
 * 为什么旧版挖不烂（本版已修）/ 目标模式 / 冲突联动 / 注意事项），逐字保留，
 * <b>承载方式由弹窗改为内嵌</b>（旧项目这五章来自弹窗 {@code HelpScreen}；用户 2026-09-17 口径：
 * 不再单摆「查看使用说明」按钮）。</p>
 *
 * <p><b>冲突联动章节的删条目</b>：旧正文列了 8 条冲突，其中 7 条指向旧项目框架自带的模块
 * （本项目无对应物），按 96 号 D-18-08 删除，只保留「自动挖矿 · 快速破坏（秒破）」一条
 * （树形符号随之由 {@code ├─} 改为 {@code └─}，单条即末条）。</p>
 *
 * <p><b>设置不铺在本页</b>：21 项设置全部由整屏控制台 {@link PacketBreakConsoleScreen}
 * 按旧项目原 4 个设置组名分页承载，本页不出现任何设置控件。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，因此构造函数里
 * 不碰任何游戏注册表；内容推迟到 {@link #createPage(ModuleEntry)}（玩家真正打开页面时）构建（第 180 条）。</p>
 */
public final class PacketBreakPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照飞行绕过 / 发包防踢 / 服务器检测模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行说明：写清分页构成 */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 目标选择 / 发包参数 / 防同步与反作弊 / 进度显示";

    /**
     * 使用说明章节：正文逐字照旧 {@code tactical/packetbreak/PacketInstantBreak.java} 的五个章节，
     * 一字未改（含树形分隔行与全部颜色码；框线与 {@code [#]} 格式由 {@link HelpPanelScreen} 生成）。
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("破坏方式（26.1.2 真实机制）",
            "§8├─ §e极速卡点 §8- §7只发 START/STOP，不走原版持续挖掘，",
            "§8│   §7在服务端 0.7 阈值满足的最早 tick 发 STOP",
            "§8│",
            "§8└─ §e原版速度 §8- §7还原版挖掘进度满格才停挖，最稳",
            "§8    §7硬方块仍受 26.1.2 服务端权威破坏时长校验"
        ),
        new HelpPanelScreen.HelpSection("为什么旧版挖不烂（本版已修）",
            "§8├─ §7服务器只有 1 个挖掘槽，旧版多块交错发 START",
            "§8│   §7互相覆盖导致全部失效 → 已改串行单槽",
            "§8├─ §7旧版 STOP 发太早，达不到服务端 0.7 阈值",
            "§8│   §7→ 已按同源公式卡点停挖",
            "§8└─ §7手上工具不对破坏速度为 0 永远挖不烂",
            "§8    §7→ 自动换工具默认开启，先切最快工具再挖"
        ),
        new HelpPanelScreen.HelpSection("目标模式",
            "§8├─ §e瞄准破坏 §8- §7按住左键瞄准目标方块发包挖掘",
            "§8└─ §e范围自动 §8- §7自动扫描周围方块排队逐个发包（Nuker式）",
            "§8    §7配合「目标方块」白名单精准挖指定方块"
        ),
        new HelpPanelScreen.HelpSection("冲突联动",
            "§c[!] §f开启本模块时会自动检测以下冲突：",
            "§8  └─ §7自动挖矿 · 快速破坏（秒破）",
            "§c[!] §f检测到冲突会拒启并提示，关闭冲突功能后才能启动"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "§c⚠ §f挖掘速度受服务器硬限制，任何客户端都无法超越卡点速度",
            "§c⚠ §f硬方块（黑曜石等）手持工具不对时破坏速度为 0，只能放弃",
            "§c⚠ §f单人世界自动禁用，仅在多人服务器生效"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行），铺在「打开控制台」入口正下方。
     * 声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final PacketInstantBreakModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public PacketBreakPage(PacketInstantBreakModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照飞行绕过 / 发包防踢模块页）
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
        client.setScreen(new PacketBreakConsoleScreen(client.screen, module));
    }
}
