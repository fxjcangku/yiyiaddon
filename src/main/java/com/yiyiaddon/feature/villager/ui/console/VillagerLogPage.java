package com.yiyiaddon.feature.villager.ui.console;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 控制台「日志」页：最近真正发进聊天的内容（D5 页签划分；交互与口径照星露谷「日志」页参照实现
 * ——刷新 / 清空按钮、容量 80、多行卡片只留标题行）。
 *
 * <p>数据源是模块侧的环形缓冲（{@link AutoVillagerTradeModule#logs()}），由统一播报入口
 * {@code info / error} 落入——聊天里发过什么、这里就有什么（含状态机 38 处播报与启动报告），
 * 不另造第二套日志内容。清空只清历史记录（{@link AutoVillagerTradeModule#clearLogs()}），
 * 不影响状态机与聊天栏。</p>
 */
public final class VillagerLogPage {

    private final VillagerConsoleScreen host;
    private final AutoVillagerTradeModule module;

    public VillagerLogPage(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：刷新/清空按钮 + 播报流水（新 → 旧）；交互与文案照星露谷「日志」页参照实现 */
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
