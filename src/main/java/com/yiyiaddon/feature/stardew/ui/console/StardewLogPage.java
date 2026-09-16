package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 星露谷控制台「日志」页：最近真正发进聊天的内容。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildLog}；方法体与文案一字未改。</p>
 */
public final class StardewLogPage {

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    public StardewLogPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(new ButtonStrip(owner, List.of(
            new Ctl(new Button("§7刷新日志", owner::reload)),
            new Ctl(new Button("§c清空日志", () -> {
                module.statusReporter().clearLog();
                owner.reload();
            }), "只清这里的历史记录，不影响状态与聊天栏")), ButtonStrip.BUTTON_HEIGHT));

        List<String> logs = owner.data().logs();
        if (logs.isEmpty()) {
            stack.add(new Note(owner, "§8还没有播报：任务状态提示默认关闭，去「运行」页打开「状态提示」才会记录"));
            return;
        }
        for (String line : logs) stack.add(new Note(owner, line));
    }
}
