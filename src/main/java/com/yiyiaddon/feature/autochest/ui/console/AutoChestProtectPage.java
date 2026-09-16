package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「保护」页：{@code 保护} 分组的四项。
 *
 * <p>逐字搬自旧项目配置页的 {@code 保护} 分组：{@code 多人保护}、{@code 玩家检测距离}
 * （仅多人保护开启时可见）、{@code 最大重试次数}、{@code 临时冷却}——设置名、描述、默认值与
 * 取值域一字未改（{@code 临时冷却} 下限 20 与旧项目同域）。</p>
 *
 * <p>可见性联动：整页可重建，{@code 玩家检测距离} 在开关为假时压根不加入堆叠，
 * 开关改动后重建本页（与挖矿控制台 {@code RTP需要GUI选择} → {@code GUI按钮关键词} 同做法）。</p>
 */
public final class AutoChestProtectPage {

    private static final String DESC_MULTIPLAYER_PROTECT =
        "检测到其他玩家正在使用目标容器时，不抢箱，暂时跳过并进入冷却。";
    private static final String DESC_PLAYER_DISTANCE = "其他玩家距离容器多少格内视为正在使用，触发多人保护。";
    private static final String DESC_MAX_RETRIES = "开箱/寻路/交互失败后最多重试几次，超过则本轮跳过该容器。";
    private static final String DESC_COOLDOWN = "连续失败或多人保护后，容器进入暂时不可用的冷却时长（Tick）。";

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestProtectPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> "多人保护", DESC_MULTIPLAYER_PROTECT, null,
            List.of(new Ctl(toggle(() -> settings.multiplayerProtect, value -> {
                settings.multiplayerProtect = value;
                module.persistSettings();
                // 下一行随开关显示 / 消失，本页整页重建后才会改变
                owner.reload();
            })))));

        if (settings.multiplayerProtect) {
            stack.add(new ConsoleRow(owner, () -> "玩家检测距离", DESC_PLAYER_DISTANCE, null,
                List.of(new Ctl(intBox(1, Integer.MAX_VALUE, () -> settings.playerDetectDistance,
                    value -> {
                        settings.playerDetectDistance = value;
                    })))));
        }

        stack.add(new ConsoleRow(owner, () -> "最大重试次数", DESC_MAX_RETRIES, null,
            List.of(new Ctl(intBox(1, Integer.MAX_VALUE, () -> settings.maxRetries,
                value -> {
                    settings.maxRetries = value;
                })))));

        stack.add(new ConsoleRow(owner, () -> "临时冷却", DESC_COOLDOWN, null,
            List.of(new Ctl(intBox(20, Integer.MAX_VALUE, () -> settings.cooldownTicks,
                value -> {
                    settings.cooldownTicks = value;
                })))));
    }

    /** 开关行：改动落盘（与配置页同一个 {@code persistSettings} 时机） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, setter::accept);
    }

    /** 整数设置框：步进 1，改动落盘（取值域与旧项目一致） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, Consumer<Integer> setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
    }
}
