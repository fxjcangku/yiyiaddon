package com.yiyiaddon.feature.reconnect.ui.console;

import com.yiyiaddon.feature.reconnect.AutoReconnectModule;
import com.yiyiaddon.feature.reconnect.config.ReconnectSettings;
import com.yiyiaddon.feature.reconnect.config.ReconnectTexts;
import com.yiyiaddon.feature.reconnect.ui.ReconnectConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/**
 * 控制台设置页：六项设置一个折叠块（{@link ReconnectTexts#GROUP_SETTINGS}），顺序 = 声明顺序。
 *
 * <p><b>行级显隐照自动登入的同一套条件</b>（两个模块口径一致，玩家不必学两套）：
 * 总开关关闭时只留总开关并给一句灰字说明；「重连等待」与「无限重连」仅总开关开启时出现；
 * 「最大重连次数」仅总开关开启且未开无限重连时出现。整页可重建，改开关后行立刻增删
 * （等价旧设置的 {@code visible(...)}）。</p>
 *
 * <p>数字一律 {@link SettingNumberBox}（第 123 条禁滑块，整数步进 1），布尔走 {@link SettingToggle}；
 * 旧描述留在行悬停提示里（第 213 条）；改动立即
 * {@link com.yiyiaddon.core.module.ModuleManager#saveSettings}（第 172-175 条）。</p>
 */
public final class ReconnectSettingsPage {

    /** 折叠块的记忆键（= 设置组名，同源） */
    private static final String SECTION_KEY = "group:" + ReconnectTexts.GROUP_SETTINGS;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final ReconnectSettings DEFAULTS = new ReconnectSettings();

    private final ReconnectConsoleScreen host;
    private final AutoReconnectModule module;

    public ReconnectSettingsPage(ReconnectConsoleScreen host, AutoReconnectModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        ReconnectSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + ReconnectTexts.GROUP_SETTINGS,
            SECTION_KEY, host.collapsedSections());

        // 总开关：改动后整页重建（它决定下面几项显不显示）
        section.content().add(toggleRow(ReconnectTexts.NAME_ENABLED, ReconnectTexts.DESC_ENABLED,
            () -> settings.enabled, value -> settings.enabled = value, true, () -> DEFAULTS.enabled));

        if (!settings.enabled) {
            section.content().add(new Note(host, "  §8总开关已关闭：本模块只显示状态，不发起任何连接。"));
            stack.add(section);
            return;
        }

        section.content().add(numberRow(ReconnectTexts.NAME_DELAY, ReconnectTexts.DESC_DELAY,
            ReconnectSettings.DELAY_MIN, ReconnectSettings.VALUE_MAX,
            () -> (double) settings.delayTicks,
            value -> settings.delayTicks = (int) Math.round(value),
            () -> (double) DEFAULTS.delayTicks));

        // 无限重连：改动后整页重建（它决定「最大重连次数」显不显示）
        section.content().add(toggleRow(ReconnectTexts.NAME_UNLIMITED, ReconnectTexts.DESC_UNLIMITED,
            () -> settings.unlimited, value -> settings.unlimited = value, true, () -> DEFAULTS.unlimited));

        if (!settings.unlimited) {
            section.content().add(numberRow(ReconnectTexts.NAME_MAX_ATTEMPTS, ReconnectTexts.DESC_MAX_ATTEMPTS,
                ReconnectSettings.MAX_ATTEMPTS_MIN, ReconnectSettings.VALUE_MAX,
                () -> (double) settings.maxAttempts,
                value -> settings.maxAttempts = (int) Math.round(value),
                () -> (double) DEFAULTS.maxAttempts));
        }

        section.content().add(numberRow(ReconnectTexts.NAME_STABLE_TICKS, ReconnectTexts.DESC_STABLE_TICKS,
            ReconnectSettings.STABLE_TICKS_MIN, ReconnectSettings.VALUE_MAX,
            () -> (double) settings.stableTicks,
            value -> settings.stableTicks = (int) Math.round(value),
            () -> (double) DEFAULTS.stableTicks));

        section.content().add(toggleRow(ReconnectTexts.NAME_CANCEL_ON_MENU, ReconnectTexts.DESC_CANCEL_ON_MENU,
            () -> settings.cancelOnMenu, value -> settings.cancelOnMenu = value, false,
            () -> DEFAULTS.cancelOnMenu));

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 整数数值行：步进 1（第 123 条禁滑块），改动即写盘；行尾 ↺ 恢复本行默认值 */
    private ConsoleRow numberRow(String label, String desc, int min, int max,
                                 Supplier<Double> getter, DoubleConsumer setter,
                                 Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f", getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(box, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /**
     * 开关行：改动即写盘。
     *
     * @param reloadAfter 改完是否整页重建（决定他行显隐的开关才需要，其余开关不必重建，
     *                    免得正在点的开关被换掉）
     * @param defaultValue 本行的出厂值（行尾 ↺ 的取值来源）
     */
    private ConsoleRow toggleRow(String label, String desc,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 boolean reloadAfter, Supplier<Boolean> defaultValue) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            if (reloadAfter) host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(toggle, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        module.persistSettings();
    }
}
