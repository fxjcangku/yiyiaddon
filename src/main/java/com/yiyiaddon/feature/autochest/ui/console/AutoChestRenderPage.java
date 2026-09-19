package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.model.autochest.EspStyle;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;

/**
 * 自动箱子控制台「渲染」页：{@code 渲染} 分组的六项。
 *
 * <p>逐字搬自旧项目配置页的 {@code 渲染} 分组：{@code ESP高亮}、{@code ESP框样式}
 * （仅 ESP 开启时可见）、{@code 未处理颜色} / {@code 已处理颜色} / {@code 处理中颜色}
 * （同样仅 ESP 开启时可见）——设置名、描述、默认值与取值域一字未改。</p>
 *
 * <p><b>字牌大小（用户 2026-09-19）</b>：用户要求点位类模块的渲染设置向星露谷点位页对齐
 * （点位颜色自定义 / 文字大小自定义 / ESP 样式），本页在三色之后追加「字牌大小」一行 ——
 * 行构件用共用件 {@link PointRenderSection#labelSizeRow}，与星露谷 / 自动挖矿点位页同一份实现。</p>
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

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoChestSettings DEFAULTS = new AutoChestSettings();

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
            })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.renderEsp = DEFAULTS.renderEsp;
                    module.persistSettings();
                    owner.reload();
                }, "ESP高亮"))));

        if (settings.renderEsp) {
            stack.add(new ConsoleRow(owner, () -> "ESP框样式", DESC_ESP_STYLE, null,
                List.of(new Ctl(new SettingSegmented(ESP_STYLE_LABELS,
                    () -> settings.espStyle.ordinal(), this::pickEspStyle)),
                    ConsoleWidgets.resetCtl(() -> {
                        settings.espStyle = DEFAULTS.espStyle;
                        module.persistSettings();
                        owner.reload();
                    }, "ESP框样式"))));

            stack.add(colorRow("未处理颜色", DESC_UNPROCESSED_COLOR, module.unprocessedColor(),
                colorOf(DEFAULTS.unprocessedColor)));
            stack.add(colorRow("已处理颜色", DESC_PROCESSED_COLOR, module.processedColor(),
                colorOf(DEFAULTS.processedColor)));
            stack.add(colorRow("处理中颜色", DESC_PROCESSING_COLOR, module.processingColor(),
                colorOf(DEFAULTS.processingColor)));

            // 字牌大小：与星露谷 / 自动挖矿点位页同一行构件（数字框 + 行尾 ↺ 全部走共用件），
            // 因此本页不另写一份行组装。本模块没有「一类对象一行」的渲染对象清单，openScreen 传 null、
            // 出厂对象清单传空（这两项只有对象行才用得到）。
            PointRenderSection renderSection = new PointRenderSection(owner,
                module::persistSettings, owner::reload, null, List.of());
            stack.add(renderSection.labelSizeRow(AutoChestSettings.NAME_LABEL_SIZE,
                AutoChestSettings.DESC_LABEL_SIZE, AutoChestSettings.LABEL_SIZE_MIN,
                AutoChestSettings.LABEL_SIZE_MAX,
                () -> settings.labelSize,
                value -> settings.labelSize = value,
                DEFAULTS.labelSize));
        }
    }

    /**
     * 颜色行：调色板关窗时先把载体颜色写回设置项再落盘。
     *
     * <p>只传 {@code persistSettings} 是不行的：颜色还没同步进设置项，存下去的是旧值。</p>
     *
     * <p>行尾带可见提示（第 213 条）：色块本身只是纯色底，看不出能点。颜色载体由模块持有且不可换引用，
     * 行尾 ↺ 因此把出厂色的五个分量就地写回，再走同一条 {@code syncColorsToSettings} 落盘。</p>
     */
    private ConsoleRow colorRow(String name, String hint, EspColor color, EspColor defaultColor) {
        return new ConsoleRow(owner, () -> name, hint, COMMENT_COLOR,
            List.of(new Ctl(new SettingColorPicker(name, color, module::syncColorsToSettings)),
                ConsoleWidgets.resetCtl(() -> {
                    copyColor(color, defaultColor);
                    module.syncColorsToSettings();
                    owner.reload();
                }, name)));
    }

    /** 出厂色（设置项里是打包 ARGB）→ 界面用的颜色对象 */
    private static EspColor colorOf(int argb) {
        return new EspColor(argb & 0xFFFFFF, (argb >>> 24) & 0xFF);
    }

    /** 把出厂颜色就地写给模块持有的那个颜色对象（载体不可换引用） */
    private static void copyColor(EspColor target, EspColor source) {
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
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
