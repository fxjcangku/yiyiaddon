package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
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
 * 自动挖矿控制台「触发条件」页：满载 / 食物 / 耐久 / 潜影盒打包机。
 *
 * <p>逐字搬自旧项目配置页的 {@code 触发条件} 分组；顺序、设置名、描述、取值域与落盘时机一字未改，
 * 只把行容器换成本项目的控制台行构件。2026-09-16 起本页是这四行的唯一落点（配置页不再平铺设置）。</p>
 */
public final class MiningThresholdPage {

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningThresholdPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> "满载组数",
            "背包矿物达到多少组时触发卸货", null,
            List.of(new Ctl(intBox(1, 36, () -> settings.unloadThreshold,
                value -> settings.unloadThreshold = value, null)))));

        stack.add(new ConsoleRow(owner, () -> "食物阈值",
            "背包食物少于此数量时触发补给", null,
            List.of(new Ctl(intBox(1, 64, () -> settings.hungerThreshold,
                value -> settings.hungerThreshold = value, null)))));

        stack.add(new ConsoleRow(owner, () -> "耐久阈值",
            "工具剩余耐久低于此值时前往挂机点修补（下界合金镐耐久 2031，上限已放宽）", null,
            List.of(new Ctl(intBox(1, 3000, () -> settings.durabilityThreshold,
                value -> settings.durabilityThreshold = value, null)))));

        stack.add(new ConsoleRow(owner, () -> "潜影盒打包机",
            "卸货时把矿物箱(潜影盒)填满，检测到满后等红石推盒换新盒，自动重开箱继续放，直到背包目标矿放完才RTP。给搭配潜影盒打包机的挂机用户使用。",
            null,
            List.of(new Ctl(toggle(() -> settings.shulkerPacker,
                value -> settings.shulkerPacker = value)))));
    }

    /** 开关行：改动落盘（与配置页同一个 {@code persistSettings} 时机） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        });
    }

    /** 整数设置框：步进 1、无滑块（旧项目全部 {@code noSlider}），改动落盘 */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter,
                                    String baritoneKey) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                int next = (int) Math.round(value);
                setter.accept(next);
                module.persistSettings();
                if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, next);
            });
    }
}
