package com.yiyiaddon.feature.admindetect.ui.console;

import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorSettings;
import com.yiyiaddon.feature.admindetect.ui.AdminDetectorConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 管理员检测控制台「概览」页：只读实况 + 一个 {@code §c立即断线} 按钮。
 *
 * <p><b>按钮来源</b>：旧「自动断线」模块 {@code CometDisconnectModule.getWidget}（{@code :63-71}）
 * 里那个 {@code §c立即断线} 按钮，文案逐字保留；两个模块合并后它落在本页（旧模块不再有独立页面）。</p>
 *
 * <p><b>只读行</b>是实况读数（检测项开合、名单计数、当前威胁数），每行用 {@code Supplier} 现取，
 * 该页每秒整页重建一次（照其它控制台概览页的口径），因此不需要各字段自己刷新。</p>
 */
public final class AdminOverviewPage {

    private final AdminDetectorConsoleScreen owner;
    private final AdminDetectorModule module;

    public AdminOverviewPage(AdminDetectorConsoleScreen owner, AdminDetectorModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(section("§7检测"));
        stack.add(new Note(owner, () -> "§7检测范围：§f" + module.settings().detectRange + "§7 格"));
        stack.add(new Note(owner, this::detectItemsText));
        stack.add(new Note(owner, () -> "§7范围内威胁：§f" + module.threats().size() + " §7个"));

        stack.add(section("§7名单"));
        stack.add(new Note(owner, () -> "§7白名单：§f" + module.settings().whitelist.size() + " §7项"));
        stack.add(new Note(owner, () -> "§7黑名单：§f" + module.settings().blacklist.size() + " §7项"));

        stack.add(section("§7命中后"));
        stack.add(new Note(owner, this::reactionText));
        stack.add(new ButtonStrip(owner, List.of(
            new Ctl(new Button("§c立即断线", module::disconnectNow),
                "应急断开服务器连接；无连接时静默无动作")), ButtonStrip.BUTTON_HEIGHT));
    }

    /** 分区标题行（尺寸取自 ConsoleMetrics，与其它控制台同一套） */
    private Note section(String title) {
        return new Note(owner, title, null, ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /** 四个检测项的开关一览（顺序照旧项目判定优先级：旁观 / 创造 / 隐身 / 隐藏） */
    private String detectItemsText() {
        AdminDetectorSettings settings = module.settings();
        return "§7检测项："
            + item("旁观", settings.detectSpectator) + "§7 / "
            + item("创造", settings.detectCreative) + "§7 / "
            + item("隐身", settings.detectInvisible) + "§7 / "
            + item("隐藏", settings.detectHidden);
    }

    /** 命中后的反应一览：断线是固定行为（无开关），后三项是设置项 */
    private String reactionText() {
        AdminDetectorSettings settings = module.settings();
        return "§7命中后：§f报警 + 断线§7 / "
            + item("画框", settings.espBox) + "§7 / "
            + item("射线", settings.tracer) + "§7 / "
            + item("警报声", settings.alarmSound);
    }

    /** 单项读数：开=绿，关=灰 */
    private static String item(String label, boolean enabled) {
        return "§f" + label + " " + (enabled ? "§a开" : "§8关");
    }
}
