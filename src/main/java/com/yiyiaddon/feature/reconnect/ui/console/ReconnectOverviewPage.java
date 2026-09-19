package com.yiyiaddon.feature.reconnect.ui.console;

import com.yiyiaddon.feature.reconnect.AutoReconnectModule;
import com.yiyiaddon.feature.reconnect.config.ReconnectSettings;
import com.yiyiaddon.feature.reconnect.config.ReconnectTexts;
import com.yiyiaddon.feature.reconnect.ui.ReconnectConsoleScreen;
import com.yiyiaddon.platform.network.ConnectionCloser;
import com.yiyiaddon.service.reconnect.ReconnectEngine;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 控制台「概览」页：只读运行状态 + 三个重连动作。
 *
 * <p><b>三个动作</b>：立即重连 / 停止重连（与自动登入概览页同名同义）、测试重连
 * （本模块特有，用户点名要的验证入口）。三个按钮都带 {@code disabledWhen}，判据与动作读同一份数据
 * （第 214 条），因此不存在「点了没反应」的按钮：没有待执行的重连时前两个置灰，不在服务器里时
 * 测试键置灰，且各自的悬停提示会说明原因。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关、状态串、失败次数、服务器记录、接管方都读
 * {@link AutoReconnectModule} 与 {@link ReconnectEngine} 的只读接口，本页只显示、不决策。</p>
 */
public final class ReconnectOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final ReconnectConsoleScreen host;
    private final AutoReconnectModule module;

    public ReconnectOverviewPage(ReconnectConsoleScreen host, AutoReconnectModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        ReconnectSettings settings = module.settings();
        ReconnectEngine engine = module.engine();

        FoldSection status = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        status.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")
            + " §8/ §7总开关：" + onOff(settings.enabled)));
        status.content().add(new TextLine("§7状态：" + module.stateText()));
        status.content().add(new TextLine("§7重连次数：" + module.attemptsText()));
        status.content().add(new TextLine("§7最后一次服务器："
            + (engine.hasServer() ? "§f" + engine.serverAddressText() : "§8无记录（先进入一次服务器）")));
        status.content().add(new TextLine("§7谁在接管：" + module.ownerText()
            + (module.autoLoginTakesOver()
                ? " §8（自动登入模块已开着「自动重连」，同一次断线由它连，本模块不重复调度）"
                : " §8（断线后由本模块连）")));
        status.content().add(new TextLine("§7等待：§f" + seconds(settings.delayTicks)
            + " §8/ §7上限：" + (settings.unlimited ? "§f不限" : "§f" + settings.maxAttempts + " 次")
            + " §8/ §7稳定判定：§f" + seconds(settings.stableTicks)));
        status.content().add(new TextLine("§7回主菜单取消重连：" + onOff(settings.cancelOnMenu)));

        FoldSection control = new FoldSection("§f重连控制", SECTION_KEY + ":control", host.collapsedSections());
        control.content().add(new ButtonStrip(host, List.of(
            new Ctl(new Button(ReconnectTexts.BTN_RECONNECT_NOW, module::reconnectNow)
                    .disabledWhen(() -> !engine.isScheduled()),
                () -> engine.isScheduled() ? ReconnectTexts.HINT_RECONNECT_NOW : "当前没有等待中的重连"),
            new Ctl(new Button(ReconnectTexts.BTN_STOP, module::stopReconnect)
                    .disabledWhen(() -> !engine.isScheduled()),
                () -> engine.isScheduled() ? ReconnectTexts.HINT_STOP : "当前没有等待中的重连"),
            new Ctl(new Button(ReconnectTexts.BTN_TEST, this::confirmTest)
                    .disabledWhen(() -> !ConnectionCloser.hasConnection()),
                () -> ConnectionCloser.hasConnection()
                    ? ReconnectTexts.HINT_TEST : ReconnectTexts.HINT_TEST_DISABLED)
        ), ButtonStrip.BUTTON_HEIGHT));
        control.content().add(new Note(host,
            "§8测试重连：主动断开当前连接，随后按「重连等待」自动连回 —— 用来验证断线重连这条链路。"));
        control.content().add(new Note(host,
            "§8立即重连 = 跳过等待时间；停止重连 = 取消本次等待（保留服务器记录，之后仍可立即重连）。"));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f总开关 / 重连等待 / 无限重连 / 最大重连次数 / 连接稳定判定 / "
            + "回主菜单时取消重连 §8▸ 在「" + ReconnectTexts.GROUP_SETTINGS + "」页"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));
        guide.content().add(new TextLine("  §8▸ §f自己点「断开连接」退出服务器不会触发重连（原版回到主菜单，不是掉线）"));
        guide.content().add(new TextLine("  §8▸ §f管理员检测的「§c立即断线§f」是保命动作，重连不会把它撤销"));

        stack.add(status);
        stack.add(control);
        stack.add(guide);
    }

    // ── 测试键的二次确认 ──

    /**
     * 打开二次确认窗：确认后由模块倒计时若干 tick 再真正断开（见
     * {@link AutoReconnectModule#testReconnect()}），避免关窗动画与断线界面抢同一次 {@code setScreen}。
     */
    private void confirmTest() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(ConfirmPanelScreen.inPlace(
            ReconnectTexts.CONFIRM_TEST_TITLE,
            List.of(
                ReconnectTexts.CONFIRM_TEST_HEADLINE,
                "§8· " + ReconnectTexts.CONFIRM_TEST_ITEM_1,
                "§8· " + ReconnectTexts.CONFIRM_TEST_ITEM_2,
                "§8· " + ReconnectTexts.CONFIRM_TEST_ITEM_3),
            ReconnectTexts.CONFIRM_TEST_LABEL,
            module::testReconnect,
            host));
    }

    // ── 只读格式化 ──

    /** 开关的只读显示：绿开 / 灰关（与其余控制台的概览页同一口径） */
    private static String onOff(boolean value) {
        return value ? "§a开" : "§8关";
    }

    /** tick → 「N 秒」 */
    private static String seconds(int ticks) {
        return (ticks / 20) + " 秒";
    }
}
