package com.yiyiaddon.feature.teleport.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.teleport.TeleportModule;
import com.yiyiaddon.feature.teleport.ui.TeleportConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.keybind.AddonKeybind;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingKeybind;

import java.util.List;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.CLEAR_BUTTON;
import static com.yiyiaddon.ui.console.ConsoleWidgets.CLEAR_HINT;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_KEYBIND;

/**
 * 控制台「触发按键」页：旧「触发按键」组的三个功能键。
 *
 * <p><b>键位行承载方式照自动图书管理员的「暂停快捷键」</b>（第 169 条同源做法）：
 * 自研控件 {@link SettingKeybind} + 同行「§c清空」按钮 + 改动即写盘（第 172-175 条）。
 * 旧描述逐字保留，右侧补一句「点击后按任意键绑定」提示（照既有键位行口径）。</p>
 *
 * <p><b>松开触发</b>：真正的触发时机与「模块未开启时提醒」见模块的
 * {@code FunctionKeybinds} 接线，行上只负责绑定值。</p>
 */
public final class TeleportKeysPage {

    private static final String SECTION_KEY = "group:触发按键";

    /** 键位块宽度：放得下 {@code Mouse Forward} / {@code Num Lock + F12} 这类长名 */
    private static final float KEYBIND_WIDTH = 140f;
    /** 清空按钮宽度 */
    private static final float CLEAR_WIDTH = 72f;

    private final TeleportConsoleScreen host;
    private final TeleportModule module;

    public TeleportKeysPage(TeleportConsoleScreen host, TeleportModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        FoldSection section = new FoldSection("§f触发按键", SECTION_KEY, host.collapsedSections());

        section.content().add(keybindRow("TP地面键",
            "触发 TP地面：回到头顶真正露天地表（洞穴脱身）",
            () -> module.settings().groundKey,
            value -> module.settings().groundKey = value,
            () -> module.settings().groundKey = AddonKeybind.none()));

        section.content().add(keybindRow("TP穿墙键",
            "触发 TP穿墙：沿准星方向智能搜索落点，穿墙/赶路一体，前方无障碍也能前进",
            () -> module.settings().wallKey,
            value -> module.settings().wallKey = value,
            () -> module.settings().wallKey = AddonKeybind.none()));

        section.content().add(keybindRow("TP坐标键",
            "触发 TP坐标：传送到下方配置的坐标",
            () -> module.settings().coordKey,
            value -> module.settings().coordKey = value,
            () -> module.settings().coordKey = AddonKeybind.none()));

        stack.add(section);
    }

    /** 键位行：录制控件 + 清空按钮；绑定与清空都立即写盘 */
    private ConsoleRow keybindRow(String label, String desc,
                                  Supplier<AddonKeybind> getter,
                                  java.util.function.Consumer<AddonKeybind> setter,
                                  Runnable clear) {
        SettingKeybind keybind = new SettingKeybind(
            getter,
            value -> {
                setter.accept(value);
                persist();
            }).width(KEYBIND_WIDTH);
        Button clearButton = new Button(CLEAR_BUTTON, () -> {
            clear.run();
            persist();
        }).width(CLEAR_WIDTH).disabledWhen(() -> !getter.get().isSet());

        return new ConsoleRow(host, () -> label, desc, COMMENT_KEYBIND,
            List.of(new Ctl(keybind, desc + "（点击后按任意键绑定）"),
                new Ctl(clearButton, CLEAR_HINT)));
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
