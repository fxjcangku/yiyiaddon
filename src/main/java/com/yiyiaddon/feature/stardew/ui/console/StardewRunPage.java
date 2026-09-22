package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

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

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final StardewSettings DEFAULTS = new StardewSettings();

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
            () -> s.autoStart, value -> s.autoStart = value, () -> DEFAULTS.autoStart));
        stack.add(intRow(StardewSettings.NAME_REACH, StardewSettings.DESC_REACH,
            StardewSettings.REACH_MIN, StardewSettings.REACH_MAX, () -> s.reach, value -> s.reach = value,
            () -> DEFAULTS.reach));
        stack.add(intRow(StardewSettings.NAME_SCAN_BUDGET, StardewSettings.DESC_SCAN_BUDGET,
            StardewSettings.SCAN_BUDGET_MIN, StardewSettings.SCAN_BUDGET_MAX,
            () -> s.scanBudget, value -> s.scanBudget = value, () -> DEFAULTS.scanBudget));
        stack.add(intRow(StardewSettings.NAME_RETURN_CENTER_DELAY, StardewSettings.DESC_RETURN_CENTER_DELAY,
            StardewSettings.RETURN_CENTER_DELAY_MIN, StardewSettings.RETURN_CENTER_DELAY_MAX,
            () -> s.returnCenterDelay, value -> s.returnCenterDelay = value, () -> DEFAULTS.returnCenterDelay));
        stack.add(intRow(StardewSettings.NAME_BATCH_ACTIONS, StardewSettings.DESC_BATCH_ACTIONS,
            StardewSettings.BATCH_ACTIONS_MIN, StardewSettings.BATCH_ACTIONS_MAX,
            () -> s.batchActions, value -> s.batchActions = value, () -> DEFAULTS.batchActions));
        stack.add(new Note(owner, "§8批量动作 = 同一 tick 最多对几个格子连发交互包（收割 / 浇水 / 播种 / 施肥发右键，"
            + "清枯苗 / 清错位 / 清杂物发左键破坏）。调高更快，也更容易被服务器反作弊注意到"));

        stack.add(section("自动化"));
        stack.add(boolRow(StardewSettings.NAME_AUTO_WATER, StardewSettings.DESC_AUTO_WATER,
            () -> s.autoWater, value -> s.autoWater = value, () -> DEFAULTS.autoWater,
            this::autoWaterBlockReason));
        stack.add(boolRow(StardewSettings.NAME_SWITCH_CAN, StardewSettings.DESC_SWITCH_CAN,
            () -> s.switchCan, value -> s.switchCan = value, () -> DEFAULTS.switchCan));
        stack.add(boolRow(StardewSettings.NAME_RESTORE_HAND, StardewSettings.DESC_RESTORE_HAND,
            () -> s.restoreHand, value -> s.restoreHand = value, () -> DEFAULTS.restoreHand));
        stack.add(boolRow(StardewSettings.NAME_AUTO_FERTILIZE, StardewSettings.DESC_AUTO_FERTILIZE,
            () -> s.autoFertilize, value -> s.autoFertilize = value, () -> DEFAULTS.autoFertilize,
            this::autoFertilizeBlockReason));
        stack.add(boolRow(StardewSettings.NAME_AUTO_POTION, StardewSettings.DESC_AUTO_POTION,
            () -> s.autoPotion, value -> s.autoPotion = value, () -> DEFAULTS.autoPotion,
            this::autoPotionBlockReason));

        stack.add(section("洒水器维护"));
        stack.add(boolRow(StardewSettings.NAME_SPRINKLER_MAINTENANCE, StardewSettings.DESC_SPRINKLER_MAINTENANCE,
            () -> s.sprinklerMaintenance, value -> s.sprinklerMaintenance = value,
            () -> DEFAULTS.sprinklerMaintenance, this::sprinklerMaintenanceBlockReason));
        stack.add(intRow(StardewSettings.NAME_SPRINKLER_INTERVAL, StardewSettings.DESC_SPRINKLER_INTERVAL,
            StardewSettings.SPRINKLER_INTERVAL_MIN, StardewSettings.SPRINKLER_INTERVAL_MAX,
            () -> s.sprinklerInterval, value -> s.sprinklerInterval = value, () -> DEFAULTS.sprinklerInterval));

        // 分区种植：错位自动清理 + 选点工具
        stack.add(section("分区种植"));
        stack.add(boolRow(StardewSettings.NAME_AUTO_CLEAR_MISMATCH, StardewSettings.DESC_AUTO_CLEAR_MISMATCH,
            () -> s.autoClearMismatch, value -> s.autoClearMismatch = value, () -> DEFAULTS.autoClearMismatch));
        stack.add(toolRow());
        stack.add(new Note(owner, "§8每块地只种它绑定的那种作物（混种地除外），"
            + "没圈到的地一律不管；圈地用 .stardew 种植区域 <作物|混种>"));

        stack.add(section("播报"));
        stack.add(boolRow(StardewSettings.NAME_STATUS_HINTS, StardewSettings.DESC_STATUS_HINTS,
            () -> s.statusHints, value -> s.statusHints = value, () -> DEFAULTS.statusHints));
        stack.add(new Note(owner, "§8状态提示默认开启：关闭后聊天栏不再播报任务状态，结论类消息不受影响"));
    }

    /** 分组小标题（与点位页 / 概览页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            StardewConsoleScreen.SECTION_HEIGHT, StardewConsoleScreen.SECTION_SIZE);
    }

    private CompactElement boolRow(String name, String description, Supplier<Boolean> getter,
                                   Consumer<Boolean> setter, Supplier<Boolean> defaultValue) {
        return boolRow(name, description, getter, setter, defaultValue, null);
    }

    /**
     * 布尔设置行（可带「打开前置条件」）。
     *
     * <p><b>为什么有的开关要先过一道闸</b>（用户 2026-09-22：「我还打开了洒水器维护，这些都没检测的；
     * 没选洒水器不给打开洒水器维护才对」）：启动自检一次会话只跑一次，进服之后再打开一个「缺对象」的
     * 开关，不会被任何判据拦下——玩家以为它已经在干活，实际只是空转。所以「打开」这一步当场校验，
     * 缺什么就说清楚。</p>
     *
     * <p>{@code blockReason} 返回非空 = 拒绝打开（设置不写、不落盘，开关停在原位），并把原因弹出来；
     * 关闭（{@code false}）永远放行——不让人因为一个缺项连关都关不掉。</p>
     */
    private CompactElement boolRow(String name, String description, Supplier<Boolean> getter,
                                   Consumer<Boolean> setter, Supplier<Boolean> defaultValue,
                                   Supplier<String> blockReason) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            if (value && blockReason != null) {
                String reason = blockReason.get();
                if (reason != null) {
                    refuse(name, reason);
                    return;
                }
            }
            setter.accept(value);
            module.persistSettings();
        });
        return new ConsoleRow(owner, () -> name, description, null, List.of(new Ctl(toggle),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.get());
                module.persistSettings();
                owner.reload();
            }, name)));
    }

    /** 打开「自动浇灌」的前置条件：要用得上水壶的盆型得先选水壶（下界盆浇岩浆、末地盆浇龙息，用不到） */
    private String autoWaterBlockReason() {
        if (!module.selections().can().selectedKeys().isEmpty()) return null;
        PotGroup group = module.index() == null ? null
            : module.index().potGroupOfSelected(module.selections().pot().selectedKeys());
        if (group != null && group.refillItem() != null) return null;
        return "还没选水壶：先在「水壶」选择器里勾选要用的水壶，再打开自动浇灌";
    }

    /** 打开「自动施肥」的前置条件：先选肥料（没选肥料就没有可施的东西） */
    private String autoFertilizeBlockReason() {
        return module.selections().fertilizer().selectedKeys().isEmpty()
            ? "还没选肥料：先在「肥料」选择器里勾选要用的肥料，再打开自动施肥" : null;
    }

    /** 打开「自动用药剂」的前置条件：先选药剂 */
    private String autoPotionBlockReason() {
        return module.selections().potion().selectedKeys().isEmpty()
            ? "还没选药剂：先在「魔法药剂」选择器里勾选要用的药剂，再打开自动用药剂" : null;
    }

    /**
     * 打开「洒水器维护」的前置条件：先选洒水器型号，并且绑过至少一台洒水器点位。
     *
     * <p>两样缺一，这个开关打开也只是空转：没选型号就不知道维护哪一种，没有点位就没有维护对象
     * （点位按维度分档，这里只判「任意维度是否绑过」，具体本维度有没有由启动自检报出）。</p>
     */
    private String sprinklerMaintenanceBlockReason() {
        if (module.selections().sprinkler().selectedKeys().isEmpty()) {
            return "还没选洒水器型号：先在「洒水器」选择器里勾选要维护的型号，再打开洒水器维护";
        }
        if (module.pointManager() != null && module.pointManager().getAll(StardewPointType.SPRINKLER).isEmpty()) {
            return "还没绑定洒水器点位：对准洒水器实物绑一次点位，再打开洒水器维护";
        }
        return null;
    }

    /**
     * 拒绝打开：屏幕中间弹一块原位说明面板（点掉回本页），聊天栏同时留一条记录。
     *
     * <p>只发聊天容易被后面的状态播报刷走（启动自检面板当初就是为这个加的），所以两处都给。</p>
     */
    private void refuse(String settingName, String reason) {
        if (module.statusReporter() != null) {
            module.statusReporter().critical("SETTING_BLOCKED:" + settingName,
                "无法打开「" + settingName + "」", reason);
        }
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(ConfirmPanelScreen.noticeInPlace("星露谷农场 · 无法打开「" + settingName + "」",
            "§7这个开关要先满足条件：", List.of(reason), owner));
    }

    private CompactElement intRow(String name, String description, int min, int max,
                                  Supplier<Integer> getter, IntConsumer setter,
                                  Supplier<Integer> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> name, description, null, List.of(new Ctl(box),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.get());
                module.persistSettings();
                owner.reload();
            }, name)));
    }

    /**
     * 选点工具行：一键把主手物品设为圈地白名单，或清除回「不限」。
     *
     * <p>行尾注释实时显示当前工具（默认「不限（任何物品）」）；默认手持什么都能点角，
     * 设成白名单后只有空手或手持它才算点角。</p>
     *
     * <p>行内 ↺ 走与「清除」按钮同一个模块入口（出厂值就是「不限」：两个字段都是 {@code null}）。</p>
     */
    private CompactElement toolRow() {
        Button set = new Button("§e一键设定", module::setRegionToolFromHand);
        Button clear = new Button("§c清除", module::clearRegionTool);
        return ConsoleRow.liveComment(owner, () -> StardewSettings.NAME_REGION_TOOL, StardewSettings.DESC_REGION_TOOL,
            () -> "§7当前：§f" + module.regionToolDisplayName(), List.of(new Ctl(set), new Ctl(clear),
                ConsoleWidgets.resetCtl(() -> {
                    module.clearRegionTool();
                    owner.reload();
                }, StardewSettings.NAME_REGION_TOOL)));
    }
}
