package com.yiyiaddon.feature.admindetect.ui;

import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
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
 * 管理员检测模块页：状态条 + 「打开控制台」入口，使用说明内嵌在该入口下方的薄壳页。
 *
 * <p><b>形态照自动挖矿模块页</b>（{@code AutoMinerPage}）：控制台入口在前、使用说明正文直接铺在
 * 入口正下方（用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，说明超出一屏时由本页自身的
 * 滚动条上下查看）。模块的设置不在本页平铺：全部由整屏控制台 {@link AdminDetectorConsoleScreen}
 * 按用途分页承载（概览 / 检测 / 名单 / 显示与警报）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §b打开控制台}；
 * 使用说明正文来自旧 {@code AdminDetectorModule.getWidget}（{@code :232-260}）与
 * {@code CometDisconnectModule.getWidget}（{@code :72-83}）两段原文，合并与改写项登记在
 * {@link AdminDetectorModule} 类注释指向的迁移记录里。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次
 * （{@code ModuleEntries.of:35}），因此构造函数里<b>不碰任何游戏注册表</b>；页面内容推迟到
 * {@link #createPage(ModuleEntry)}（玩家真正打开页面时）才构建。</p>
 */
public final class AdminDetectorPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照自动挖矿 / 星露谷模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明 */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 检测 / 名单 / 显示与警报";

    /**
     * 使用说明章节（旧两段 {@code buildInfoWidget} 原文合并；框线与 {@code [#]} 格式由
     * {@link HelpPanelScreen} 生成）。
     *
     * <p>合并时改动四句，均因两个模块合并 / 第 163 条删除项而产生，逐条登记在迁移记录里：
     * 「开启本模块即立即断线一次」与「管理员检测模块会自动调用本模块」随合并取消；
     * 「断线时会强制关闭自动重连」随该步删除（用户 2026-09-16 裁定）；「检测到危险玩家进入范围」
     * 一句补上警报与标记，因为那是用户新增的能力。</p>
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("功能",
            "  §f  · 监测附近玩家，识别管理员视察典型形态并自动断线保命。",
            "  §f  · 检测到危险玩家进入范围 §8→ §f警报 + 屏幕标记 + 聊天栏提示 + 立即断线。",
            "  §f  · 危险玩家离开范围 §8→ §f聊天栏提示。"
        ),
        new HelpPanelScreen.HelpSection("检测项",
            "  §f  · 旁观者：管理员 spectator 视察。",
            "  §f  · 创造：Tab 列表游戏模式为创造。",
            "  §f  · 隐身：实体带隐身标志。",
            "  §f  · 隐藏：不在 Tab 列表（vanish 插件典型特征）。"
        ),
        new HelpPanelScreen.HelpSection("名单",
            "  §f  · 白名单：名单内玩家来了不退出、不提示（豁免）。",
            "  §f  · 黑名单：名单内玩家来了立即断线，无视危险特征。",
            "  §f  · 两份名单都在控制台「名单」页里点选添加，不用手输名字。"
        ),
        new HelpPanelScreen.HelpSection("显示与警报",
            "  §f  · 画框：命中时给危险玩家画红色描边框。",
            "  §f  · 射线：命中时从自己画一条红色射线过去。",
            "  §f  · 警报声：命中时播放警报音效。",
            "  §f  · 三项默认开启，可在控制台「显示与警报」页里关掉。"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ 完全隐身到客户端无实体的管理员无法检测，这是客户端 hack 的固有限制。",
            "  §c⚠ 提示仅发到自己的聊天栏，不会暴露给其他玩家。",
            "  §c⚠ 断线只退出服务器，不保证完全不被记录，请结合隐身 / 伪装使用。"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行）：本页把使用说明直接铺在「打开控制台」入口下方
     * （用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，超出可上下滚动查看）。
     *
     * <p>声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final AdminDetectorModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public AdminDetectorPage(AdminDetectorModule module) {
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
        // ① 控制台入口（提示行 + 居中按钮，形态照自动挖矿模块页）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明内嵌在控制台入口下方（用户 2026-09-17 口径），章节标题与正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new AdminDetectorConsoleScreen(client.gui.screen(), module));
    }
}
