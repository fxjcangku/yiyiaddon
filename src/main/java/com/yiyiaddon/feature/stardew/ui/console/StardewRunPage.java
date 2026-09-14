package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Ctl;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.ConsoleRow;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Note;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 星露谷控制台「运行」页：全部运行参数（顺序 = 旧项目 sgRun 构造顺序）。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildRun} 与配套的 {@code boolRow} / {@code intRow}；
 * 方法体、文案与 tooltip 一字未改。</p>
 */
public final class StardewRunPage {

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    public StardewRunPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        StardewSettings s = module.settings();

        stack.add(boolRow(StardewSettings.NAME_AUTO_START, StardewSettings.DESC_AUTO_START,
            () -> s.autoStart, value -> s.autoStart = value));
        stack.add(intRow(StardewSettings.NAME_REACH, StardewSettings.DESC_REACH,
            StardewSettings.REACH_MIN, StardewSettings.REACH_MAX, () -> s.reach, value -> s.reach = value));
        stack.add(intRow(StardewSettings.NAME_SCAN_BUDGET, StardewSettings.DESC_SCAN_BUDGET,
            StardewSettings.SCAN_BUDGET_MIN, StardewSettings.SCAN_BUDGET_MAX,
            () -> s.scanBudget, value -> s.scanBudget = value));
        stack.add(intRow(StardewSettings.NAME_RETURN_CENTER_DELAY, StardewSettings.DESC_RETURN_CENTER_DELAY,
            StardewSettings.RETURN_CENTER_DELAY_MIN, StardewSettings.RETURN_CENTER_DELAY_MAX,
            () -> s.returnCenterDelay, value -> s.returnCenterDelay = value));
        stack.add(intRow(StardewSettings.NAME_BATCH_ACTIONS, StardewSettings.DESC_BATCH_ACTIONS,
            StardewSettings.BATCH_ACTIONS_MIN, StardewSettings.BATCH_ACTIONS_MAX,
            () -> s.batchActions, value -> s.batchActions = value));
        stack.add(boolRow(StardewSettings.NAME_AUTO_WATER, StardewSettings.DESC_AUTO_WATER,
            () -> s.autoWater, value -> s.autoWater = value));
        stack.add(boolRow(StardewSettings.NAME_SWITCH_CAN, StardewSettings.DESC_SWITCH_CAN,
            () -> s.switchCan, value -> s.switchCan = value));
        stack.add(boolRow(StardewSettings.NAME_RESTORE_HAND, StardewSettings.DESC_RESTORE_HAND,
            () -> s.restoreHand, value -> s.restoreHand = value));
        stack.add(boolRow(StardewSettings.NAME_AUTO_FERTILIZE, StardewSettings.DESC_AUTO_FERTILIZE,
            () -> s.autoFertilize, value -> s.autoFertilize = value));
        stack.add(boolRow(StardewSettings.NAME_AUTO_POTION, StardewSettings.DESC_AUTO_POTION,
            () -> s.autoPotion, value -> s.autoPotion = value));
        stack.add(boolRow(StardewSettings.NAME_SPRINKLER_MAINTENANCE, StardewSettings.DESC_SPRINKLER_MAINTENANCE,
            () -> s.sprinklerMaintenance, value -> s.sprinklerMaintenance = value));
        stack.add(intRow(StardewSettings.NAME_SPRINKLER_INTERVAL, StardewSettings.DESC_SPRINKLER_INTERVAL,
            StardewSettings.SPRINKLER_INTERVAL_MIN, StardewSettings.SPRINKLER_INTERVAL_MAX,
            () -> s.sprinklerInterval, value -> s.sprinklerInterval = value));
        stack.add(boolRow(StardewSettings.NAME_STATUS_HINTS, StardewSettings.DESC_STATUS_HINTS,
            () -> s.statusHints, value -> s.statusHints = value));

        stack.add(new Note(owner, "§8状态提示默认关闭：开启后聊天栏才播报任务状态，结论类消息不受影响"));
        stack.add(new Note(owner, "§8批量右击 = 同一 tick 最多对几个格子发右键（只做收割 / 浇水 / 播种 / 施肥）。"
            + "调高更快，也更容易被服务器反作弊注意到"));
    }

    private CompactElement boolRow(String name, String description, Supplier<Boolean> getter,
                                   Consumer<Boolean> setter) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        });
        return new ConsoleRow(owner, () -> name, description, null, List.of(new Ctl(toggle)));
    }

    private CompactElement intRow(String name, String description, int min, int max,
                                  Supplier<Integer> getter, IntConsumer setter) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> name, description, null, List.of(new Ctl(box)));
    }
}
