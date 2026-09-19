package com.yiyiaddon.feature.librarian.ui.console;

import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 控制台「日志」页：最近真正发进聊天的内容（D4 页签划分；交互与口径照自动村民交易「日志」页
 * ——刷新 / 清空按钮、容量 80、多行卡片只留标题行）。
 *
 * <p>数据源是模块侧的环形缓冲（{@link AutoLibrarianModule#logs()}），由统一播报入口落入 ——
 * 聊天里发过什么、这里就有什么（含编排器全部播报、启动报告与「已强制结束」），
 * 不另造第二套日志内容。清空只清历史记录（{@link AutoLibrarianModule#clearLogs()}），
 * 不影响状态与聊天栏。</p>
 */
public final class LibrarianLogPage {

    private final LibrarianConsoleScreen host;
    private final AutoLibrarianModule module;

    public LibrarianLogPage(LibrarianConsoleScreen host, AutoLibrarianModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：刷新 / 清空按钮 + 播报流水（新 → 旧） */
    public void build(CompactStack stack) {
        stack.add(new ButtonStrip(host, List.of(
            new Ctl(new Button("§7刷新日志", host::reload)),
            new Ctl(new Button("§c清空日志", () -> {
                module.clearLogs();
                host.reload();
            }), "只清这里的历史记录，不影响状态与聊天栏")), ButtonStrip.BUTTON_HEIGHT));

        List<String> logs = module.logs();
        if (logs.isEmpty()) {
            stack.add(new Note(host, "§8还没有播报：启用模块后，状态与任务结果会出现在这里"));
            return;
        }
        for (String line : logs) stack.add(new Note(host, line));
    }
}
