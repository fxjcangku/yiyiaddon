package com.yiyiaddon.feature.admindetect.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorSettings;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorTexts;
import com.yiyiaddon.feature.admindetect.ui.AdminDetectorConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 管理员检测控制台「显示与警报」页：命中之后除了断线，还能看到什么、听到什么。
 *
 * <p><b>本页三项全部是新增设置</b> —— 旧项目 {@code AdminDetectorModule} 只有「聊天提示 + 断线」，
 * 没有 ESP 画框、没有射线、没有警报音；三项由用户 2026-09-16 要求新增，默认全关不掉地开着
 * （用户拍板「默认设置」= 这三项默认开）。名称与描述见 {@link AdminDetectorTexts}。</p>
 */
public final class AdminRenderPage {

    private final AdminDetectorConsoleScreen owner;
    private final AdminDetectorModule module;

    public AdminRenderPage(AdminDetectorConsoleScreen owner, AdminDetectorModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AdminDetectorSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_ESP_BOX,
            AdminDetectorTexts.DESC_ESP_BOX, null,
            List.of(new Ctl(toggle(() -> settings.espBox, value -> settings.espBox = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_TRACER,
            AdminDetectorTexts.DESC_TRACER, null,
            List.of(new Ctl(toggle(() -> settings.tracer, value -> settings.tracer = value)))));

        stack.add(new ConsoleRow(owner, () -> AdminDetectorTexts.NAME_ALARM,
            AdminDetectorTexts.DESC_ALARM, null,
            List.of(new Ctl(toggle(() -> settings.alarmSound, value -> settings.alarmSound = value)))));
    }

    /** 开关行：改动落盘 */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            ModuleManager.saveSettings(module);
        });
    }
}
