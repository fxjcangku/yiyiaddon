package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 星露谷控制台「运行」页：全部运行参数。
 *
 * <p>每一行的设置名 / 描述 / 默认值 / 取值域仍逐字来自旧项目（禁止改写），但<b>页面顺序</b>按用途
 * 重排为五组并加了分组小标题：启停与作业 / 自动化 / 洒水器维护 / 分区种植 / 播报。
 * 分组只动「先看到什么」，不改任何设置项本身。</p>
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

        // 顺序按用途分成五组（设置项本身、文案与默认值一字未改，只是重排 + 就近放说明）：
        // 启停与作业 / 自动化 / 洒水器维护 / 分区种植 / 播报
        stack.add(section("启停与作业"));
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
        stack.add(new Note(owner, "§8批量右击 = 同一 tick 最多对几个格子发右键（只做收割 / 浇水 / 播种 / 施肥）。"
            + "调高更快，也更容易被服务器反作弊注意到"));

        stack.add(section("自动化"));
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

        stack.add(section("洒水器维护"));
        stack.add(boolRow(StardewSettings.NAME_SPRINKLER_MAINTENANCE, StardewSettings.DESC_SPRINKLER_MAINTENANCE,
            () -> s.sprinklerMaintenance, value -> s.sprinklerMaintenance = value));
        stack.add(intRow(StardewSettings.NAME_SPRINKLER_INTERVAL, StardewSettings.DESC_SPRINKLER_INTERVAL,
            StardewSettings.SPRINKLER_INTERVAL_MIN, StardewSettings.SPRINKLER_INTERVAL_MAX,
            () -> s.sprinklerInterval, value -> s.sprinklerInterval = value));

        // 分区种植：错位自动清理 + 选点工具
        stack.add(section("分区种植"));
        stack.add(boolRow(StardewSettings.NAME_AUTO_CLEAR_MISMATCH, StardewSettings.DESC_AUTO_CLEAR_MISMATCH,
            () -> s.autoClearMismatch, value -> s.autoClearMismatch = value));
        stack.add(toolRow());
        stack.add(new Note(owner, "§8每块地只种它绑定的那种作物（混种地除外），"
            + "没圈到的地一律不管；圈地用 .stardew 种植区域 <作物|混种>"));

        stack.add(section("播报"));
        stack.add(boolRow(StardewSettings.NAME_STATUS_HINTS, StardewSettings.DESC_STATUS_HINTS,
            () -> s.statusHints, value -> s.statusHints = value));
        stack.add(new Note(owner, "§8状态提示默认开启：关闭后聊天栏不再播报任务状态，结论类消息不受影响"));
    }

    /** 分组小标题（与点位页 / 概览页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            StardewConsoleScreen.SECTION_HEIGHT, StardewConsoleScreen.SECTION_SIZE);
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

    /**
     * 选点工具行：一键把主手物品设为圈地白名单，或清除回「不限」。
     *
     * <p>行尾注释实时显示当前工具（默认「不限（任何物品）」）；默认手持什么都能点角，
     * 设成白名单后只有空手或手持它才算点角。</p>
     */
    private CompactElement toolRow() {
        Button set = new Button("§e一键设定", module::setRegionToolFromHand);
        Button clear = new Button("§c清除", module::clearRegionTool);
        return ConsoleRow.liveComment(owner, () -> StardewSettings.NAME_REGION_TOOL, StardewSettings.DESC_REGION_TOOL,
            () -> "§7当前：§f" + module.regionToolDisplayName(), List.of(new Ctl(set), new Ctl(clear)));
    }
}
