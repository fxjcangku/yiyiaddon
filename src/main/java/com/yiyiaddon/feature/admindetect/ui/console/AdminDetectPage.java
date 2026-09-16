package com.yiyiaddon.feature.admindetect.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorSettings;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorTexts;
import com.yiyiaddon.feature.admindetect.ui.AdminDetectorConsoleScreen;
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
 * 管理员检测控制台「检测」页：旧 {@code sgDetect} 组 5 项。
 *
 * <p>逐字搬运自旧 {@code AdminDetectorModule.java:59-90}；行顺序、设置名、描述、取值域与落盘时机
 * 一字未改，名称与描述取自 {@link AdminDetectorTexts}。检测范围的默认值为 64（旧 10），
 * 属用户 2026-09-16 拍板的差异，见 {@link AdminDetectorSettings} 类注释。</p>
 */
public final class AdminDetectPage {

    private final AdminDetectorConsoleScreen owner;
    private final AdminDetectorModule module;

    public AdminDetectPage(AdminDetectorConsoleScreen owner, AdminDetectorModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AdminDetectorSettings settings = module.settings();

        // 检测范围：旧 .min(1).max(64)，输入框上下限即取值域；默认 64
        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_RANGE,
            AdminDetectorTexts.DESC_RANGE, null,
            List.of(new Ctl(intBox(AdminDetectorSettings.RANGE_MIN, AdminDetectorSettings.RANGE_MAX,
                () -> settings.detectRange, value -> settings.detectRange = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_SPECTATOR,
            AdminDetectorTexts.DESC_SPECTATOR, null,
            List.of(new Ctl(toggle(() -> settings.detectSpectator, value -> settings.detectSpectator = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_CREATIVE,
            AdminDetectorTexts.DESC_CREATIVE, null,
            List.of(new Ctl(toggle(() -> settings.detectCreative, value -> settings.detectCreative = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_INVISIBLE,
            AdminDetectorTexts.DESC_INVISIBLE, null,
            List.of(new Ctl(toggle(() -> settings.detectInvisible, value -> settings.detectInvisible = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_HIDDEN,
            AdminDetectorTexts.DESC_HIDDEN, null,
            List.of(new Ctl(toggle(() -> settings.detectHidden, value -> settings.detectHidden = value)))));
    }

    // ── 行构件（形态与其它控制台页一致） ──

    /** 开关行：改动落盘 */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
        });
    }

    /** 整数设置框：步进 1（旧项目 {@code .noSlider()}，本项目数值项一律用输入框带加减） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                persist();
            });
    }

    /** 立即落盘（走 ModuleManager 的统一入口） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
