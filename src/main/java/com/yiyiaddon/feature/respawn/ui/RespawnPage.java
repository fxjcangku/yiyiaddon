package com.yiyiaddon.feature.respawn.ui;

import com.yiyiaddon.feature.respawn.AutoRespawnModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 自动重生模块页：一段说明，没有设置项也没有控制台。
 *
 * <p><b>为什么此前显示「未接入」</b>：本模块刻意不做设置（{@code page()} 曾返回 {@code null}），
 * 模块中心点进去落到框架的元信息页，玩家看到「模块页面：未接入 / 该模块尚未接入独立页面」
 * （用户 2026-09-18 实机截图）。本模块确实没有设置需要承载，但「没页面」在界面上等于「坏了」，
 * 因此接一个纯说明页：说明正文 + 一句状态提示，<b>不造任何设置项</b>（模块只有一个开关，
 * 由模块卡片与控制台顶栏承载）。</p>
 *
 * <p><b>说明内容说明</b>：旧项目没有这个模块（旧挖矿流程直接 toggle 第三方 AutoRespawn），
 * 因此没有可逐字搬运的旧文案；这里是按自研实现的实际行为写的，不虚构旧资产。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次，所以构造函数不碰注册表，
 * 内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。</p>
 */
public final class RespawnPage extends CompactModulePage implements ModulePage {

    /** 说明章节（按本模块的实际行为写；无设置项，因此不讲参数） */
    private static final HelpPanelScreen.HelpSection[] SECTIONS = {
            new HelpPanelScreen.HelpSection("这个模块做什么",
                    "玩家处于死亡状态时自动替你复活，无需手动点复活按钮。",
                    "常用于挂机挖矿 / 自动农场死亡后的无人值守恢复。"),
            new HelpPanelScreen.HelpSection("开关",
                    "只有「开启 / 关闭」，没有任何可调参数。",
                    "默认开启；手动关掉后以状态记录为准，重启客户端不会自己开回来。"),
            new HelpPanelScreen.HelpSection("注意事项",
                    "复活走原版流程，不绕过服务器的复活冷却或确认界面。",
                    "服务器若有自定义死亡界面，仍由原版流程完成，可能出现短暂等待。"),
    };

    /** 内嵌说明行（去掉窗口外框三行）；静态初始化即完成，不触碰注册表 */
    private static final String[] HELP_LINES =
            HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(SECTIONS));

    private final AutoRespawnModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public RespawnPage(AutoRespawnModule module) {
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
        // 先交代「为什么没有设置」：避免玩家以为页面没做完
        addCore(new TextLine("§7本模块没有设置项；开关在模块卡片上点击，或控制台顶栏切换。"));
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }
}
