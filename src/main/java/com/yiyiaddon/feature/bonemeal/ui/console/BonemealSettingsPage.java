package com.yiyiaddon.feature.bonemeal.ui.console;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.config.TargetList;
import com.yiyiaddon.feature.bonemeal.model.TriggerMode;
import com.yiyiaddon.feature.bonemeal.ui.BonemealConsoleScreen;
import com.yiyiaddon.feature.bonemeal.ui.BonemealSelectors;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 控制台设置页：旧项目原 3 个设置组（目标方块 / 防作弊绕过 / ESP渲染）+ 默认组（基础参数）
 * 各一个折叠块，与 {@link BonemealConsoleScreen} 的页签一一对应。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧
 * {@code bonemeal/AutoBoneMeal.java:59-243} 的设置声明（集中在 {@link BonemealTexts} 与
 * {@link BonemealSettings}，同一句话只有一处），分组名沿用旧组名与旧源码分节标题，
 * 不新造分类（第 183 条）。数字一律 {@link SettingNumberBox}（第 123 条禁滑块，整数步进 1）；
 * 枚举走 {@link SettingSegmented}（触发模式两项互斥、形状模式三项互斥）；布尔走 {@link SettingToggle}；
 * 颜色走 {@link SettingColorPicker}（第 151 条）；旧设置的描述原文留在行悬停提示里（第 213 条），
 * 两个颜色项旧项目本来就没写描述，因此这两行没有悬停提示。</p>
 *
 * <p><b>行级显隐照旧条件</b>：{@code 作用半径} 与 {@code 遮挡射线检测} 仅 {@code 范围自动扫描}；
 * {@code 准星提示} 仅 {@code 准星精准指向}。整页可重建，条件不满足的行压根不加入堆叠
 * （等价旧设置的 {@code visible(...)}）；改触发模式会触发重建，行立刻出现或消失。</p>
 *
 * <p><b>五组目标方块</b>：行文案 = 旧设置名 + 名单状态，右侧「选择 / 清空」两个按钮走通用选择器
 * （{@link BonemealSelectors}）；旧框架的列表控件属框架层，不搬（第 34 条）。</p>
 *
 * <p>改动立即 {@link com.yiyiaddon.core.module.ModuleManager#saveSettings}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class BonemealSettingsPage {

    // ── 折叠块的记忆键（= 旧项目设置组名 / 页签名，同源） ──

    private static final String SECTION_KEY_BASIC = "group:" + BonemealTexts.GROUP_BASIC;
    private static final String SECTION_KEY_TARGETS = "group:" + BonemealTexts.GROUP_TARGETS;
    private static final String SECTION_KEY_BYPASS = "group:" + BonemealTexts.GROUP_BYPASS;
    private static final String SECTION_KEY_ESP = "group:" + BonemealTexts.GROUP_ESP;

    /** 枚举候选：顺序即枚举序（触发模式文案 = 枚举名 = 落盘名） */
    private static final List<String> TRIGGER_MODE_LABELS = List.of(TriggerMode.labels());
    /** 形状模式候选：标签逐字取自 {@link ShapeMode#labels()}（线框 / 面 / 两者，第 143 条） */
    private static final List<String> SHAPE_MODE_LABELS = List.of(ShapeMode.labels());

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final BonemealSettings DEFAULTS = new BonemealSettings();

    private final BonemealConsoleScreen host;
    private final AutoBoneMealModule module;

    public BonemealSettingsPage(BonemealConsoleScreen host, AutoBoneMealModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 基础参数（旧默认组 sgGeneral 的 5 项） ──

    public void buildBasic(CompactStack stack) {
        BonemealSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + BonemealTexts.GROUP_BASIC,
            SECTION_KEY_BASIC, host.collapsedSections());

        // 触发模式决定下面两项哪一条出现，改动后整页重建
        section.content().add(enumRow(BonemealTexts.NAME_TRIGGER_MODE, BonemealTexts.DESC_TRIGGER_MODE,
            TRIGGER_MODE_LABELS, () -> settings.triggerMode.ordinal(),
            index -> settings.triggerMode = TriggerMode.values()[index],
            () -> DEFAULTS.triggerMode.ordinal()));

        if (settings.triggerMode == TriggerMode.范围自动扫描) {
            section.content().add(numberRow(BonemealTexts.NAME_RANGE, BonemealTexts.DESC_RANGE,
                BonemealSettings.RANGE_MIN, BonemealSettings.RANGE_MAX,
                () -> (double) settings.range,
                value -> settings.range = (int) Math.round(value),
                () -> (double) DEFAULTS.range));

            section.content().add(toggleRow(BonemealTexts.NAME_CHECK_OCCLUSION,
                BonemealTexts.DESC_CHECK_OCCLUSION,
                () -> settings.checkOcclusion, value -> settings.checkOcclusion = value,
                () -> DEFAULTS.checkOcclusion));
        } else {
            section.content().add(toggleRow(BonemealTexts.NAME_CROSSHAIR_HINT,
                BonemealTexts.DESC_CROSSHAIR_HINT,
                () -> settings.crosshairHint, value -> settings.crosshairHint = value,
                () -> DEFAULTS.crosshairHint));
        }

        section.content().add(toggleRow(BonemealTexts.NAME_NO_TARGET_HINT,
            BonemealTexts.DESC_NO_TARGET_HINT,
            () -> settings.noTargetHint, value -> settings.noTargetHint = value,
            () -> DEFAULTS.noTargetHint));

        stack.add(section);
    }

    // ── 组② 目标方块（旧 5 个 BlockListSetting，按旧声明顺序） ──

    public void buildTargets(CompactStack stack) {
        FoldSection section = new FoldSection("§f" + BonemealTexts.GROUP_TARGETS,
            SECTION_KEY_TARGETS, host.collapsedSections());

        for (TargetList group : TargetList.values()) {
            section.content().add(listRow(group.label(), group.description(),
                () -> BonemealSelectors.statusText(module, group),
                () -> BonemealSelectors.openBlockSelector(host, module, group),
                () -> BonemealSelectors.clearTargets(module, group)));
        }

        stack.add(section);
    }

    // ── 组③ 防作弊绕过（旧 sgBypass 的 7 项，按旧声明顺序） ──

    public void buildBypass(CompactStack stack) {
        BonemealSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + BonemealTexts.GROUP_BYPASS,
            SECTION_KEY_BYPASS, host.collapsedSections());

        section.content().add(numberRow(BonemealTexts.NAME_TICK_DELAY, BonemealTexts.DESC_TICK_DELAY,
            BonemealSettings.TICK_DELAY_MIN, BonemealSettings.TICK_DELAY_MAX,
            () -> (double) settings.tickDelay,
            value -> settings.tickDelay = (int) Math.round(value),
            () -> (double) DEFAULTS.tickDelay));

        section.content().add(numberRow(BonemealTexts.NAME_MAX_PER_TICK, BonemealTexts.DESC_MAX_PER_TICK,
            BonemealSettings.MAX_PER_TICK_MIN, BonemealSettings.MAX_PER_TICK_MAX,
            () -> (double) settings.maxPerTick,
            value -> settings.maxPerTick = (int) Math.round(value),
            () -> (double) DEFAULTS.maxPerTick));

        section.content().add(toggleRow(BonemealTexts.NAME_ROTATE_SILENT,
            BonemealTexts.DESC_ROTATE_SILENT,
            () -> settings.rotateSilent, value -> settings.rotateSilent = value,
            () -> DEFAULTS.rotateSilent));

        section.content().add(toggleRow(BonemealTexts.NAME_OFFHAND_FIRST,
            BonemealTexts.DESC_OFFHAND_FIRST,
            () -> settings.offhandFirst, value -> settings.offhandFirst = value,
            () -> DEFAULTS.offhandFirst));

        section.content().add(toggleRow(BonemealTexts.NAME_SWING_HAND,
            BonemealTexts.DESC_SWING_HAND,
            () -> settings.swingHand, value -> settings.swingHand = value,
            () -> DEFAULTS.swingHand));

        section.content().add(toggleRow(BonemealTexts.NAME_RESPECT_LAG,
            BonemealTexts.DESC_RESPECT_LAG,
            () -> settings.respectLag, value -> settings.respectLag = value,
            () -> DEFAULTS.respectLag));

        section.content().add(toggleRow(BonemealTexts.NAME_AUTO_THROTTLE,
            BonemealTexts.DESC_AUTO_THROTTLE,
            () -> settings.autoThrottle, value -> settings.autoThrottle = value,
            () -> DEFAULTS.autoThrottle));

        stack.add(section);
    }

    // ── 组④ ESP渲染（旧 sgEsp 的 4 项，按旧声明顺序） ──

    public void buildEsp(CompactStack stack) {
        BonemealSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + BonemealTexts.GROUP_ESP,
            SECTION_KEY_ESP, host.collapsedSections());

        section.content().add(toggleRow(BonemealTexts.NAME_ESP_ENABLED,
            BonemealTexts.DESC_ESP_ENABLED,
            () -> settings.espEnabled, value -> settings.espEnabled = value,
            () -> DEFAULTS.espEnabled));

        section.content().add(enumRow(BonemealTexts.NAME_SHAPE_MODE, BonemealTexts.DESC_SHAPE_MODE,
            SHAPE_MODE_LABELS, () -> settings.shapeMode.ordinal(),
            index -> settings.shapeMode = ShapeMode.of(index),
            () -> DEFAULTS.shapeMode.ordinal()));

        // 两个颜色项旧项目没有描述，故第二参数传 null（行内不给悬停提示）
        section.content().add(colorRow(BonemealTexts.NAME_LINE_COLOR, BonemealTexts.DESC_LINE_COLOR,
            settings.lineColor, DEFAULTS.lineColor));
        section.content().add(colorRow(BonemealTexts.NAME_FILL_COLOR, BonemealTexts.DESC_FILL_COLOR,
            settings.fillColor, DEFAULTS.fillColor));

        stack.add(section);
    }

    // ── 行构件组装 ──

    /**
     * 枚举行：分段控件（互斥选项一眼可点），行尾带可见提示（第 213 条）。
     *
     * <p>改动后整页重建：触发模式决定「作用半径 / 遮挡射线检测」与「准星提示」哪一组出现。</p>
     */
    private ConsoleRow enumRow(String label, String desc, List<String> options,
                               Supplier<Integer> getter, Consumer<Integer> setter,
                               Supplier<Integer> defaultValue) {
        SettingSegmented control = new SettingSegmented(options, getter, index -> {
            setter.accept(index);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, COMMENT_CYCLE, List.of(new Ctl(control, desc),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.get());
                persist();
                host.reload();
            }, label)));
    }

    /** 整数数值行：步进 1（旧项目两项数字设置都是 noSlider，第 123 条禁滑块），改动即写盘 */
    private ConsoleRow numberRow(String label, String desc, int min, int max,
                                 Supplier<Double> getter, DoubleConsumer setter,
                                 Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f", getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(box, desc),
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
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(toggle, desc),
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

    /** 把出厂颜色就地写给行内控件持有的那个颜色对象（字段是 final，不能换引用） */
    private static void copyColor(EspColor target, EspColor source) {
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
    }

    /**
     * 名单行：名称 + 名单状态 …… [选择] [清空]（与发包秒破 / 透视的名单行同一写法）。
     *
     * @param status 名单状态文字的取值器
     * @param open   打开选择器
     * @param clear  清空名单（名单为空时无动作）
     */
    private ConsoleRow listRow(String label, String desc, Supplier<String> status, Runnable open, Runnable clear) {
        return new ConsoleRow(host, () -> label + " §8· §7" + status.get(), desc, null,
            List.of(new Ctl(new Button(BonemealTexts.SELECT, open), BonemealTexts.SELECT_HINT),
                new Ctl(new Button(BonemealTexts.CLEAR, () -> {
                    clear.run();
                    host.reload();
                }), BonemealTexts.CLEAR_HINT)));
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        module.persistSettings();
    }
}
