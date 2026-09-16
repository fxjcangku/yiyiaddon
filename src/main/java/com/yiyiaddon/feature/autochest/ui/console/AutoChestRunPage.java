package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingWidget;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「运行模式」页：运行模式 / 玩家控制模式 / 标点模式三个分组。
 *
 * <p>逐字搬自旧项目配置页的 {@code 运行模式}（{@code 运行模式} 分段 + {@code 当前模式} 只读）、
 * {@code 玩家控制模式}（{@code 触发距离}）、{@code 标点模式}（{@code 标点管理} 只读 + 面板提示行）
 * 三个分组：设置名、描述、取值域与可见性条件一字未改。组名用页内小节标题承载，页签名取首组名。</p>
 *
 * <p>可见性联动：{@code 触发距离} 仅玩家控制模式出现、{@code 标点管理} 与其提示行仅标点模式出现
 * ——整页可重建，因此条件不满足的行压根不加入堆叠（配置页原 {@code visible(...)} 的等价物），
 * 运行模式改动后重建本页。</p>
 */
public final class AutoChestRunPage {

    private static final String DESC_SCAN_MODE =
        "玩家控制模式：玩家自己走位，进入触发距离自动处理；寻路模式：自动扫描并寻路；标点模式：只处理保存点位。";
    private static final String DESC_CURRENT_MODE = "当前选中的运行模式（实时显示）。";
    private static final String DESC_TRIGGER_DISTANCE =
        "玩家控制模式下，距离容器多少格内自动处理（受开箱可达距离约 4.5 格限制，上限 4）。";
    private static final String DESC_MARKER = "标点通过说明面板按钮或指令管理，模块只处理已保存的点位。";

    /** 「标点管理」的实时值：旧项目 {@code AutoChestSettings.java:118} 原文 */
    private static final String MARKER_HINT =
        "§7面板按钮或 §e.autochest 添加/移除/清空/状态§7 管理点位；目标物品用 §e.id 物品§7 识别";

    private static final List<String> SCAN_MODE_LABELS = List.of(
        ScanMode.PLAYER_CONTROL.displayName(), ScanMode.PATHING.displayName(), ScanMode.MARKER.displayName());

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestRunPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        stack.add(section("运行模式"));
        stack.add(row("运行模式", DESC_SCAN_MODE, new SettingSegmented(SCAN_MODE_LABELS,
            () -> settings.scanMode.ordinal(), pickScanMode())));
        stack.add(row("当前模式", DESC_CURRENT_MODE,
            new SettingText(() -> "§a§l" + settings.scanMode.displayName())));

        stack.add(section("玩家控制模式"));
        if (settings.scanMode == ScanMode.PLAYER_CONTROL) {
            stack.add(row("触发距离", DESC_TRIGGER_DISTANCE, intBox(1, 4,
                () -> settings.triggerDistance, value -> {
                    settings.triggerDistance = value;
                })));
        }

        stack.add(section("标点模式"));
        if (settings.scanMode == ScanMode.MARKER) {
            ConsoleRow manageRow = new ConsoleRow(owner, () -> "标点管理", DESC_MARKER, null, List.of());
            stack.add(manageRow);
            stack.add(new Note(owner, MARKER_HINT));
        }
    }

    /** 单控件设置行（标题 + 描述 + 右侧一个控件），行构件由控制台统一提供 */
    private ConsoleRow row(String title, String description, SettingWidget control) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(new Ctl(control)));
    }

    /** 分区标题（与星露谷 / 挖矿控制台各页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /**
     * 运行模式分段：改动落盘后整页重建。
     *
     * <p>切换模式会改变「触发距离」「标点管理」两组的可见性，不重建就会看到上一模式的残行
     * （与挖矿控制台 {@code 采集模式} 切换后重建同一做法）。</p>
     */
    private Consumer<Integer> pickScanMode() {
        return index -> {
            ScanMode[] values = ScanMode.values();
            if (index < 0 || index >= values.length) return;
            module.settings().scanMode = values[index];
            module.persistSettings();
            owner.reload();
        };
    }

    /** 整数设置框：步进 1（取值域与旧项目一致），改动落盘 */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, Consumer<Integer> setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
    }
}
