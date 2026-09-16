package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.model.autochest.EspStyle;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「渲染」页：{@code 渲染} 分组的五项。
 *
 * <p>逐字搬自旧项目配置页的 {@code 渲染} 分组：{@code ESP高亮}、{@code ESP框样式}
 * （仅 ESP 开启时可见）、{@code 未处理颜色} / {@code 已处理颜色} / {@code 处理中颜色}
 * （同样仅 ESP 开启时可见）——设置名、描述、默认值与取值域一字未改。</p>
 *
 * <p><b>颜色落盘时机照旧</b>：调色板直接改的是颜色载体，关闭时回调
 * {@link AutoChestModule#syncColorsToSettings()}（有变化才写盘）——与配置页同一构造，
 * 不给渲染页另写一套。</p>
 */
public final class AutoChestRenderPage {

    private static final String DESC_RENDER_ESP = "高亮显示已发现但未处理的容器。";
    private static final String DESC_ESP_STYLE = "容器的 ESP 渲染样式：仅线条 / 仅面 / 线+面。";
    private static final String DESC_UNPROCESSED_COLOR = "未处理容器的 ESP 颜色。";
    private static final String DESC_PROCESSED_COLOR = "已处理容器的 ESP 颜色。";
    private static final String DESC_PROCESSING_COLOR = "正在处理容器的 ESP 颜色（处理中不显示为已处理红色）。";

    private static final List<String> ESP_STYLE_LABELS = List.of(
        EspStyle.LINES.displayName(), EspStyle.SIDES.displayName(), EspStyle.BOTH.displayName());

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestRenderPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> "ESP高亮", DESC_RENDER_ESP, null,
            List.of(new Ctl(toggle(() -> settings.renderEsp, value -> {
                settings.renderEsp = value;
                module.persistSettings();
                // 下面四行随开关显示 / 消失，本页整页重建后才会改变
                owner.reload();
            })))));

        if (settings.renderEsp) {
            stack.add(new ConsoleRow(owner, () -> "ESP框样式", DESC_ESP_STYLE, null,
                List.of(new Ctl(new SettingSegmented(ESP_STYLE_LABELS,
                    () -> settings.espStyle.ordinal(), this::pickEspStyle)))));

            stack.add(colorRow("未处理颜色", DESC_UNPROCESSED_COLOR, module.unprocessedColor()));
            stack.add(colorRow("已处理颜色", DESC_PROCESSED_COLOR, module.processedColor()));
            stack.add(colorRow("处理中颜色", DESC_PROCESSING_COLOR, module.processingColor()));
        }
    }

    /**
     * 颜色行：调色板关窗时先把载体颜色写回设置项再落盘。
     *
     * <p>只传 {@code persistSettings} 是不行的：颜色还没同步进设置项，存下去的是旧值。</p>
     */
    private ConsoleRow colorRow(String name, String hint, EspColor color) {
        return new ConsoleRow(owner, () -> name, hint, null,
            List.of(new Ctl(new SettingColorPicker(name, color, module::syncColorsToSettings))));
    }

    /** ESP 框样式分段：改动落盘（不改变任何行可见性，无需重建本页） */
    private void pickEspStyle(int index) {
        EspStyle[] values = EspStyle.values();
        if (index < 0 || index >= values.length) return;
        module.settings().espStyle = values[index];
        module.persistSettings();
    }

    /** 开关行：改动落盘（与配置页同一个 {@code persistSettings} 时机） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, setter::accept);
    }
}
