package com.yiyiaddon.feature.autologin.ui.console;

import com.yiyiaddon.feature.autologin.AutoLoginModule;
import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
import com.yiyiaddon.feature.autologin.model.AutoLoginState;
import com.yiyiaddon.feature.autologin.service.ReconnectHandler;
import com.yiyiaddon.feature.autologin.ui.AutoLoginConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 控制台「概览」页：只读运行状态 + 重连控件 + 账号检测 + 设置项去处指引。
 *
 * <p><b>页面动作全部来自旧项目，一个不多</b>（第 163 / 171 条）：</p>
 * <ul>
 *     <li>「立即重连 / §c停止重连」= 旧 {@code getWidget()} 里 {@code RECONNECT_WAIT} 专用的两个按钮，
 *         文案逐字保留（{@link AutoLoginTexts#RECONNECT_NOW} / {@link AutoLoginTexts#RECONNECT_STOP}），
 *         动作转交 {@link AutoLoginModule#reconnectNow()} / {@link AutoLoginModule#stopReconnect()}；</li>
 *     <li>「立即检测账号」= 旧设置的伪按钮 {@code checkAccountNow}（旧框架里改值触发回调），
 *         本项目由本页的一行按钮承载，动作为 {@link AutoLoginModule#checkAccountNow()}，文案与描述逐字保留。</li>
 * </ul>
 *
 * <p><b>状态来源全部只读</b>：模块开关、主状态机状态与旧 {@code getInfoString} 的 HUD 串读
 * {@link AutoLoginModule} 的只读接口，两条路线的运行阶段读
 * {@link com.yiyiaddon.feature.autologin.service.LeyuanRouteService#stateName()} 与
 * {@link com.yiyiaddon.feature.autologin.service.SubserverRouteService#isRunning()}，本页只显示、不决策。</p>
 *
 * <p><b>两个按钮的禁用态</b>：旧实现在「非重连等待期」点它们什么也不会发生（悬案），
 * 这里按第 214 条用 {@code disabledWhen} 表明「现在没有可取消 / 可提前的连接调度」——
 * 判据与动作读同一份数据（{@link ReconnectHandler#isScheduled()}），每帧重算。</p>
 */
public final class AutoLoginOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final AutoLoginConsoleScreen host;
    private final AutoLoginModule module;

    public AutoLoginOverviewPage(AutoLoginConsoleScreen host, AutoLoginModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        ReconnectHandler reconnect = module.reconnectHandler();

        // ── 运行状态（只读） ──
        FoldSection status = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        status.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        status.content().add(new TextLine("§7状态：§f" + stateText()));
        status.content().add(new TextLine("§7进入方式：§f" + settings.serverEntryMode.title()
            + (settings.autoEnterSubserver ? "" : " §8（自动进入目标区域已关闭）")));
        status.content().add(new TextLine("§7自用路线：§f"
            + (module.leyuanRoute().isRunning() ? module.leyuanRoute().stateName() : AutoLoginTexts.STATE_NONE)
            + " §8/ §7自用总开关：" + onOff(settings.leyuanEnabled)));
        status.content().add(new TextLine("§7通用子服菜单：§f"
            + (module.subserverRoute().isRunning()
                ? (module.subserverRoute().isWaitingForTargetArea() ? "等待目标区域" : "进行中")
                : AutoLoginTexts.STATE_NONE)));
        status.content().add(new TextLine("§7重连：§f"
            + (reconnect.isScheduled()
                ? "倒计时 " + (reconnect.getTicksLeft() / 20) + " 秒" : AutoLoginTexts.STATE_NONE)
            + " §8/ §7已尝试：§f" + reconnect.getReconnectAttempts() + " 次"));
        status.content().add(new TextLine("§7子服检测：" + onOff(settings.detectSubserver)
            + " §8/ §7免登录检测：" + onOff(settings.noLoginDetection)
            + " §8/ §7调试模式：" + onOff(settings.debugMode)));

        // ── 重连控件（旧 getWidget 的倒计时表 + 两个按钮） ──
        FoldSection reconnectSection = new FoldSection("§f重连控制", SECTION_KEY + ":reconnect",
            host.collapsedSections());
        reconnectSection.content().add(new TextLine(this::countdownText));
        reconnectSection.content().add(new ButtonStrip(host, List.of(
            new Ctl(new Button(AutoLoginTexts.RECONNECT_NOW, module::reconnectNow)
                    .disabledWhen(() -> !module.reconnectHandler().isScheduled()),
                "跳过倒计时，立刻连接上一次记录的服务器"),
            new Ctl(new Button(AutoLoginTexts.RECONNECT_STOP, module::stopReconnect)
                    .disabledWhen(() -> !module.reconnectHandler().isScheduled()),
                "取消本次重连调度，回到未连接状态")), ButtonStrip.BUTTON_HEIGHT));

        // ── 账号检测（旧设置的「立即检测账号」伪按钮） ──
        FoldSection account = new FoldSection("§f账号检测", SECTION_KEY + ":account", host.collapsedSections());
        account.content().add(new ConsoleRow(host, () -> AutoLoginTexts.NAME_CHECK_ACCOUNT_NOW,
            AutoLoginTexts.DESC_CHECK_ACCOUNT_NOW, null,
            List.of(new Ctl(new Button("§b检测", module::checkAccountNow),
                AutoLoginTexts.DESC_CHECK_ACCOUNT_NOW))));

        // ── 设置指引 ──
        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f自动登录 / GUI 自动登录 / 服务器免登录检测 / 检测服务器子服 / "
            + "密码与延迟 / 世界加载等待 / 认证检测超时"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_AUTH + "」页"));
        guide.content().add(new TextLine("  §8▸ §f自动重连 / 重连等待 / 无限重连 / 最大重连次数"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_RECONNECT + "」页"));
        guide.content().add(new TextLine("  §8▸ §f登录后执行指令 / 执行指令 / 指令延迟"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_COMMANDS + "」页"));
        guide.content().add(new TextLine("  §8▸ §f自动进入目标区域 / 进入方式 / 菜单工具与点击顺序 / 目标区域关键词 / "
            + "到达稳定等待 / 到达目标后执行指令"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_ROUTE + "」页"));
        guide.content().add(new TextLine("  §8▸ §f自用菜单工具 / 各层按钮关键词 / 欢迎页入口 / 挂机区自动回服 / "
            + "单步等待与超时 / 主城钟兜底 / 异常恢复"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_LEYUAN + "」页"));
        guide.content().add(new TextLine("  §8▸ §f进服时自动检测 / 调试模式"
            + " §8▸ 在「" + AutoLoginTexts.GROUP_ACCOUNT + "」页"));
        guide.content().add(new TextLine("  §8▸ §f「" + AutoLoginTexts.NAME_CHECK_ACCOUNT_NOW
            + "」是本页上方的一行按钮（旧项目里它也是同一批面板动作）"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(status);
        stack.add(reconnectSection);
        stack.add(account);
        stack.add(guide);
    }

    // ── 只读格式化 ──

    /** 主状态机状态：旧 HUD 串非空时优先显示它（就绪 / 重连倒计时），否则显示状态机名字 */
    private String stateText() {
        String hud = module.hudStatus();
        if (hud != null) return hud;
        AutoLoginState state = module.state();
        return state == null ? AutoLoginTexts.STATE_NONE : state.name();
    }

    /** 旧 getWidget 的倒计时表逐字：等待期 → 「§e重连倒计时：§fN 秒」，已发出连接 → 「§a正在连接...」 */
    private String countdownText() {
        if (module.state() != AutoLoginState.RECONNECT_WAIT) {
            return "§7重连状态：§8当前不在重连流程中";
        }
        ReconnectHandler reconnect = module.reconnectHandler();
        if (reconnect.isScheduled()) {
            return AutoLoginTexts.RECONNECT_COUNTDOWN_PREFIX + (reconnect.getTicksLeft() / 20)
                + AutoLoginTexts.RECONNECT_COUNTDOWN_SUFFIX;
        }
        return AutoLoginTexts.RECONNECT_CONNECTING;
    }

    /** 开关的只读显示：绿开 / 灰关（与旧项目「开=绿、关=红」的报告口径区分开，这里只是状态一览） */
    private static String onOff(boolean value) {
        return value ? "§a开" : "§8关";
    }
}
