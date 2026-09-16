package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 杀戮光环控制台「时机」页：蓝本 {@code sgTiming} 组 7 项。
 *
 * <p>逐字搬运自蓝本 {@code KillAura.java:210-262}；行顺序、设置名、描述、取值域与落盘时机一字未改，
 * 名称与描述取自 {@link KillAuraTexts}。{@code 攻击间隔（刻）} 的可见性（← 自定义攻击间隔）用
 * 「整页可重建」实现：条件不满足时压根不加入堆叠（蓝本 {@code .visible(customDelay::get)} 的等价物），
 * 开关改动后重建本页。</p>
 */
public final class KillAuraTimingPage {

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;

    public KillAuraTimingPage(KillAuraConsoleScreen owner, KillAuraModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        KillAuraSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PAUSE_ON_LAG,
            KillAuraTexts.DESC_PAUSE_ON_LAG, null,
            List.of(new Ctl(toggle(() -> settings.pauseOnLag, value -> settings.pauseOnLag = value, false)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PAUSE_ON_USE,
            KillAuraTexts.DESC_PAUSE_ON_USE, null,
            List.of(new Ctl(toggle(() -> settings.pauseOnUse, value -> settings.pauseOnUse = value, false)))));

        /**
         * 本项目无 CrystalAura：该判据在模块里恒假（{@code KillAuraModule.CRYSTAL_AURA_PLACING}），
         * 设置项照蓝本保留以对齐设置面。
         */
        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PAUSE_ON_CA,
            KillAuraTexts.DESC_PAUSE_ON_CA, null,
            List.of(new Ctl(toggle(() -> settings.pauseOnCA, value -> settings.pauseOnCA = value, false)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_TPS_SYNC,
            KillAuraTexts.DESC_TPS_SYNC, null,
            List.of(new Ctl(toggle(() -> settings.tpsSync, value -> settings.tpsSync = value, false)))));

        // 自定义攻击间隔：下一行（攻击间隔（刻））的可见性依赖它
        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_CUSTOM_DELAY,
            KillAuraTexts.DESC_CUSTOM_DELAY, null,
            List.of(new Ctl(toggle(() -> settings.customDelay, value -> settings.customDelay = value, true)))));

        // 攻击间隔（刻）：只在「自定义攻击间隔」为真时加入（对应蓝本 .visible(customDelay::get)）
        if (settings.customDelay) {
            stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_HIT_DELAY,
                KillAuraTexts.DESC_HIT_DELAY, null,
                List.of(new Ctl(intBox(0, 60, () -> settings.hitDelay, value -> settings.hitDelay = value)))));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_SWITCH_DELAY,
            KillAuraTexts.DESC_SWITCH_DELAY, null,
            List.of(new Ctl(intBox(0, 10, () -> settings.switchDelay, value -> settings.switchDelay = value)))));
    }

    // ── 行构件 ──

    /** 开关行：改动落盘；{@code reload} 为真时重建本页（下一行的可见性依赖它） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter, boolean reload) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            if (reload) owner.reload();
        });
    }

    /** 整数设置框：步进 1（蓝本 {@code sliderMax} 的上界即输入框上限，最小值为蓝本 {@code min}） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                persist();
            });
    }

    /** 立即落盘（模块未提供 persistSettings，走 ModuleManager 的同一入口） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
