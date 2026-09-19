package com.yiyiaddon.feature.packetbreak.ui.console;

import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakSettings;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakTexts;
import com.yiyiaddon.feature.packetbreak.model.BreakMode;
import com.yiyiaddon.feature.packetbreak.model.EspStyle;
import com.yiyiaddon.feature.packetbreak.model.LabelStyle;
import com.yiyiaddon.feature.packetbreak.model.TargetMode;
import com.yiyiaddon.feature.packetbreak.ui.PacketBreakConsoleScreen;
import com.yiyiaddon.feature.packetbreak.ui.PacketBreakSelectors;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 控制台设置页：旧项目原 4 个设置组（目标选择 / 发包参数 / 防同步与反作弊 / 进度显示）
 * 各一个折叠块，与 {@link PacketBreakConsoleScreen} 的页签一一对应。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧
 * {@code tactical/packetbreak/PacketInstantBreak.java:111-272} 的设置声明（集中在
 * {@link PacketBreakTexts}，同一句话只有一处），分组名沿用旧组名，不新造分类（第 183 条）。
 * 数字一律 {@link SettingNumberBox}（第 123 条禁滑块，整数步进 1）；枚举走
 * {@link SettingSegmented}（选项文案逐字取各枚举的 {@code displayName}，照飞行绕过「飞行模式」的用法）；
 * 布尔走 {@link SettingToggle}；颜色走 {@link SettingColorPicker}（第 151 条）；
 * 旧设置的描述原文留在行悬停提示里（第 213 条），不改写成另一套说法。</p>
 *
 * <p><b>行级显隐照旧条件</b>：{@code 扫描半径} 与 {@code 目标方块} 仅 {@code 范围自动}；
 * {@code 框线样式} / {@code 进度收缩} / {@code 显示百分比} 仅 {@code 显示进度}；
 * {@code 未完成/可破坏方块面色} 仅对应样式含「面」、{@code 线色} 仅含「线条」；
 * {@code 百分比颜色} / {@code 标签内容} 仅 {@code 显示进度 + 显示百分比}。整页可重建，条件不满足的行
 * 压根不加入堆叠（等价旧设置的 {@code visible(...)}）；改开关 / 改样式会触发重建，行立刻出现或消失。</p>
 *
 * <p>改动立即 {@link com.yiyiaddon.core.module.ModuleManager#saveSettings}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class PacketBreakSettingsPage {

    // ── 折叠块的记忆键（= 旧项目设置组名，与页签名同源） ──

    private static final String SECTION_KEY_TARGET = "group:" + PacketBreakTexts.GROUP_TARGET;
    private static final String SECTION_KEY_PACKET = "group:" + PacketBreakTexts.GROUP_PACKET;
    private static final String SECTION_KEY_ANTICHEAT = "group:" + PacketBreakTexts.GROUP_ANTICHEAT;
    private static final String SECTION_KEY_RENDER = "group:" + PacketBreakTexts.GROUP_RENDER;

    /** 枚举候选：顺序即枚举序，文案逐字取各枚举的 {@code displayName} */
    private static final List<String> TARGET_MODE_LABELS = labels(TargetMode.values());
    private static final List<String> BREAK_MODE_LABELS = labels(BreakMode.values());
    private static final List<String> ESP_STYLE_LABELS = labels(EspStyle.values());
    private static final List<String> LABEL_STYLE_LABELS = labels(LabelStyle.values());

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final PacketBreakSettings DEFAULTS = new PacketBreakSettings();

    private final PacketBreakConsoleScreen host;
    private final PacketInstantBreakModule module;

    public PacketBreakSettingsPage(PacketBreakConsoleScreen host, PacketInstantBreakModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 目标选择（先决定挖什么） ──

    public void buildTarget(CompactStack stack) {
        PacketBreakSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + PacketBreakTexts.GROUP_TARGET,
            SECTION_KEY_TARGET, host.collapsedSections());

        section.content().add(enumRow(PacketBreakTexts.NAME_TARGET_MODE, PacketBreakTexts.DESC_TARGET_MODE,
            TARGET_MODE_LABELS, () -> settings.targetMode.ordinal(),
            index -> settings.targetMode = TargetMode.values()[index],
            () -> DEFAULTS.targetMode.ordinal()));

        section.content().add(enumRow(PacketBreakTexts.NAME_BREAK_MODE, PacketBreakTexts.DESC_BREAK_MODE,
            BREAK_MODE_LABELS, () -> settings.breakMode.ordinal(),
            index -> settings.breakMode = BreakMode.values()[index],
            () -> DEFAULTS.breakMode.ordinal()));

        // 两项只在范围自动模式下出现（等价旧设置声明的 visible 条件）
        if (settings.targetMode == TargetMode.RANGE) {
            section.content().add(numberRow(PacketBreakTexts.NAME_RANGE, PacketBreakTexts.DESC_RANGE,
                PacketBreakSettings.RANGE_MIN, PacketBreakSettings.RANGE_MAX,
                () -> (double) settings.range,
                value -> settings.range = (int) Math.round(value),
                () -> (double) DEFAULTS.range));

            section.content().add(listRow(PacketBreakTexts.NAME_TARGET_BLOCKS,
                PacketBreakTexts.DESC_TARGET_BLOCKS,
                () -> PacketBreakSelectors.blockStatusText(module),
                () -> PacketBreakSelectors.openBlockSelector(host, module),
                () -> PacketBreakSelectors.clearBlockTargets(module),
                () -> {
                    settings.targetBlocks.clear();
                    settings.targetBlocks.addAll(DEFAULTS.targetBlocks);
                    persist();
                    host.reload();
                }));
        }

        stack.add(section);
    }

    // ── 组② 发包参数（怎么挖） ──

    public void buildPacket(CompactStack stack) {
        PacketBreakSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + PacketBreakTexts.GROUP_PACKET,
            SECTION_KEY_PACKET, host.collapsedSections());

        section.content().add(numberRow(PacketBreakTexts.NAME_DELAY, PacketBreakTexts.DESC_DELAY,
            PacketBreakSettings.DELAY_MIN, PacketBreakSettings.DELAY_MAX,
            () -> (double) settings.delay,
            value -> settings.delay = (int) Math.round(value),
            () -> (double) DEFAULTS.delay));

        section.content().add(toggleRow(PacketBreakTexts.NAME_ROTATE, PacketBreakTexts.DESC_ROTATE,
            () -> settings.rotate, value -> settings.rotate = value, () -> DEFAULTS.rotate));

        section.content().add(toggleRow(PacketBreakTexts.NAME_AUTO_SWITCH, PacketBreakTexts.DESC_AUTO_SWITCH,
            () -> settings.autoSwitch, value -> settings.autoSwitch = value, () -> DEFAULTS.autoSwitch));

        section.content().add(toggleRow(PacketBreakTexts.NAME_SWING, PacketBreakTexts.DESC_SWING,
            () -> settings.swing, value -> settings.swing = value, () -> DEFAULTS.swing));

        stack.add(section);
    }

    // ── 组③ 防同步与反作弊（怎么防） ──

    public void buildAnticheat(CompactStack stack) {
        PacketBreakSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + PacketBreakTexts.GROUP_ANTICHEAT,
            SECTION_KEY_ANTICHEAT, host.collapsedSections());

        section.content().add(toggleRow(PacketBreakTexts.NAME_OBSCURE_PROGRESS,
            PacketBreakTexts.DESC_OBSCURE_PROGRESS,
            () -> settings.obscureProgress, value -> settings.obscureProgress = value,
            () -> DEFAULTS.obscureProgress));

        section.content().add(toggleRow(PacketBreakTexts.NAME_BYPASS_ANTICHEAT,
            PacketBreakTexts.DESC_BYPASS_ANTICHEAT,
            () -> settings.bypassAnticheat, value -> settings.bypassAnticheat = value,
            () -> DEFAULTS.bypassAnticheat));

        section.content().add(toggleRow(PacketBreakTexts.NAME_RESPECT_LAG, PacketBreakTexts.DESC_RESPECT_LAG,
            () -> settings.respectLag, value -> settings.respectLag = value, () -> DEFAULTS.respectLag));

        stack.add(section);
    }

    // ── 组④ 进度显示（怎么看） ──

    public void buildRender(CompactStack stack) {
        PacketBreakSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + PacketBreakTexts.GROUP_RENDER,
            SECTION_KEY_RENDER, host.collapsedSections());

        section.content().add(toggleRow(PacketBreakTexts.NAME_RENDER, PacketBreakTexts.DESC_RENDER,
            () -> settings.render, value -> settings.render = value, () -> DEFAULTS.render));

        if (settings.render) {
            section.content().add(enumRow(PacketBreakTexts.NAME_ESP_STYLE, PacketBreakTexts.DESC_ESP_STYLE,
                ESP_STYLE_LABELS, () -> settings.espStyle.ordinal(),
                index -> settings.espStyle = EspStyle.values()[index],
                () -> DEFAULTS.espStyle.ordinal()));

            section.content().add(toggleRow(PacketBreakTexts.NAME_SHRINK_PROGRESS,
                PacketBreakTexts.DESC_SHRINK_PROGRESS,
                () -> settings.shrinkProgress, value -> settings.shrinkProgress = value,
                () -> DEFAULTS.shrinkProgress));

            // 面色 / 线色各自跟随框线样式的「面」「线条」两项（等价旧设置声明的 visible 条件）
            if (settings.espStyle.sides()) {
                section.content().add(colorRow(PacketBreakTexts.NAME_SIDE_COLOR,
                    PacketBreakTexts.DESC_SIDE_COLOR, settings.sideColor, DEFAULTS.sideColor));
                section.content().add(colorRow(PacketBreakTexts.NAME_READY_SIDE_COLOR,
                    PacketBreakTexts.DESC_READY_SIDE_COLOR, settings.readySideColor, DEFAULTS.readySideColor));
            }
            if (settings.espStyle.lines()) {
                section.content().add(colorRow(PacketBreakTexts.NAME_LINE_COLOR,
                    PacketBreakTexts.DESC_LINE_COLOR, settings.lineColor, DEFAULTS.lineColor));
                section.content().add(colorRow(PacketBreakTexts.NAME_READY_LINE_COLOR,
                    PacketBreakTexts.DESC_READY_LINE_COLOR, settings.readyLineColor, DEFAULTS.readyLineColor));
            }

            section.content().add(toggleRow(PacketBreakTexts.NAME_SHOW_PERCENT,
                PacketBreakTexts.DESC_SHOW_PERCENT,
                () -> settings.showPercent, value -> settings.showPercent = value,
                () -> DEFAULTS.showPercent));

            if (settings.showPercent) {
                section.content().add(colorRow(PacketBreakTexts.NAME_PROGRESS_COLOR,
                    PacketBreakTexts.DESC_PROGRESS_COLOR, settings.progressColor, DEFAULTS.progressColor));
                section.content().add(enumRow(PacketBreakTexts.NAME_LABEL_STYLE,
                    PacketBreakTexts.DESC_LABEL_STYLE, LABEL_STYLE_LABELS,
                    () -> settings.labelStyle.ordinal(),
                    index -> settings.labelStyle = LabelStyle.values()[index],
                    () -> DEFAULTS.labelStyle.ordinal()));
            }
        }

        stack.add(section);
    }

    // ── 行构件组装 ──

    /**
     * 枚举行：分段控件（互斥选项一眼可点），行尾带可见提示（第 213 条）。
     *
     * <p>改动后整页重建：框线样式决定四个颜色行是否出现，目标模式决定范围两项是否出现。</p>
     *
     * @param defaultValue 本行的出厂档位（行尾 ↺ 的取值来源）
     */
    private ConsoleRow enumRow(String label, String desc, List<String> options,
                               Supplier<Integer> getter, Consumer<Integer> setter,
                               Supplier<Integer> defaultValue) {
        SettingSegmented control = new SettingSegmented(options, getter, index -> {
            setter.accept(index);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, COMMENT_CYCLE,
            List.of(new Ctl(control, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 整数数值行：步进 1（旧项目 2 项数字设置均为 noSlider），改动即写盘；行尾 ↺ 恢复本行默认值 */
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

    /** 开关行：改动即写盘；写盘后整页重建，跟随本开关显隐的行立刻出现或消失 */
    private ConsoleRow toggleRow(String label, String desc,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Supplier<Boolean> defaultValue) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(toggle, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /**
     * 颜色行：调色板直接改传入的 {@link EspColor}，关窗即生效并落盘（第 151 / 173 条）。
     *
     * <p>颜色字段是 {@code final} 对象，行尾 ↺ 只能把出厂色的五个分量就地写回同一个对象。</p>
     */
    private ConsoleRow colorRow(String label, String desc, EspColor color, EspColor defaultColor) {
        return new ConsoleRow(host, () -> label, desc, COMMENT_COLOR,
            List.of(new Ctl(new SettingColorPicker(label, color, this::persist), desc),
                ConsoleWidgets.resetCtl(() -> {
                    copyColor(color, defaultColor);
                    persist();
                    host.reload();
                }, label)));
    }

    /**
     * 名单行：名称 + 状态文字 …… [选择] [清空] [↺]（与透视 / 杀戮光环的名单行同一写法）。
     *
     * @param status 名单状态文字的取值器
     * @param open   打开选择器
     * @param clear  清空名单（名单为空时无动作）
     * @param resetToDefaults 把名单恢复成出厂值（本模块出厂为空名单）并落盘刷新
     */
    private ConsoleRow listRow(String label, String desc, Supplier<String> status, Runnable open,
                               Runnable clear, Runnable resetToDefaults) {
        return new ConsoleRow(host, () -> label + " §8· §7" + status.get(), desc, null,
            List.of(new Ctl(new Button(PacketBreakTexts.SELECT, open), PacketBreakTexts.SELECT_HINT),
                new Ctl(new Button(PacketBreakTexts.CLEAR, () -> {
                    clear.run();
                    host.reload();
                }), PacketBreakTexts.CLEAR_HINT),
                ConsoleWidgets.resetCtl(resetToDefaults, label)));
    }

    /** 把出厂颜色就地写给行内控件持有的那个颜色对象（字段是 final，不能换引用） */
    private static void copyColor(EspColor target, EspColor source) {
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        module.persistSettings();
    }

    /** 枚举候选文案：顺序即枚举序（逐字取各枚举的显示名） */
    private static <E extends Enum<E>> List<String> labels(E[] values) {
        return Arrays.stream(values).map(Enum::toString).toList();
    }
}
