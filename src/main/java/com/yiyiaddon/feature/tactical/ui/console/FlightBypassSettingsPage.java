package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.tactical.FlightBypassModule;
import com.yiyiaddon.feature.tactical.config.FlightBypassSettings;
import com.yiyiaddon.feature.tactical.core.FlightPolicy;
import com.yiyiaddon.feature.tactical.ui.FlightBypassConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/**
 * 控制台设置页：旧项目原 2 个设置组（模式选择 / 参数调整）各一个折叠块。
 *
 * <p><b>设置逐字</b>：名称 / 默认值 / 取值域全部来自旧 {@code tactical/FlightBypass.java} 的设置声明，
 * 分组名沿用旧组名，不新造分类（第 183 条）。数字一律 {@link SettingNumberBox}（第 123 条禁滑块，
 * 整数步进 1）；枚举走 {@link SettingSegmented}（五模式互斥，照自动附魔「目标模式」的用法）；
 * 布尔走 {@link SettingToggle}。</p>
 *
 * <p><b>行级显隐照旧条件</b>：{@code 滑翔速度} 仅 {@code SAFE_GLIDE}、{@code 跳跃间隔} 仅
 * {@code VANILLA_MIMIC}、{@code 垫脚延迟} 仅 {@code SEQUENCE_SCAFFOLD}、
 * {@code 拉回降级阈值} 仅 {@code 自适应降级} 为真。整页可重建，条件不满足的行压根不加入堆叠
 * （等价旧设置的 {@code visible(...)}）；改模式切页 / 改开关会触发重建，新行立刻出现或消失。</p>
 *
 * <p>改动立即 {@link ModuleManager#saveSettings(com.yiyiaddon.core.module.Module)}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class FlightBypassSettingsPage {

    private static final String SECTION_KEY_MODE = "group:模式选择";
    private static final String SECTION_KEY_PARAMS = "group:参数调整";

    // ── 行名（逐字 = 旧设置名） ──

    private static final String LABEL_MODE = "飞行模式";
    private static final String LABEL_GLIDE_SPEED = "滑翔速度（0.01格/tick）";
    private static final String LABEL_JUMP_INTERVAL = "跳跃间隔（tick）";
    private static final String LABEL_SCAFFOLD_DELAY = "垫脚延迟（ms）";
    private static final String LABEL_ADAPTIVE = "自适应降级";
    private static final String LABEL_DEGRADE_THRESHOLD = "拉回降级阈值";

    // ── 行描述（与模块页内嵌说明同一件事，措辞取自说明正文） ──

    private static final String DESC_MODE = "发包飞行 / 原版连跳（非飞行）/ 安全滑翔 / 烟花火箭 / 序列垫脚，五模式互斥切换；"
        + "实际执行模式由协调器裁决（未授权 / 高风险自动降级）";
    private static final String DESC_GLIDE_SPEED = "安全滑翔的垂直升降档位，1 = 0.01 格/tick；"
        + "跳跃键上升、潜行键下降、无输入微降";
    private static final String DESC_JUMP_INTERVAL = "原版连跳两次起跳之间的最小间隔，防止落地判定抖动导致同帧连跳";
    private static final String DESC_SCAFFOLD_DELAY = "序列垫脚放置方块后延迟拆除的时间；每 5 次留一块不拆，模拟手动失误";
    private static final String DESC_ADAPTIVE = "连续拉回达到阈值时沿降级链逐档降级，脱离危险窗口后每 10 秒恢复一档";
    private static final String DESC_DEGRADE_THRESHOLD = "滑动窗口内连续拉回达到该次数即降一档（任何拉回都先触发 2 秒冷却暂停）";

    /** 枚举候选：顺序即枚举序，文案逐字取自 {@link FlightPolicy.FlightMode} 的中文显示名 */
    private static final List<String> MODE_LABELS = Arrays.stream(FlightPolicy.FlightMode.values())
        .map(mode -> mode.displayName)
        .toList();

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final FlightBypassSettings DEFAULTS = new FlightBypassSettings();

    private final FlightBypassConsoleScreen host;
    private final FlightBypassModule module;

    public FlightBypassSettingsPage(FlightBypassConsoleScreen host, FlightBypassModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 模式选择 ──

    public void buildMode(CompactStack stack) {
        FoldSection section = new FoldSection("§f模式选择", SECTION_KEY_MODE, host.collapsedSections());

        SettingSegmented control = new SettingSegmented(MODE_LABELS,
            () -> module.settings().mode.ordinal(),
            index -> {
                FlightPolicy.FlightMode[] modes = FlightPolicy.FlightMode.values();
                if (index < 0 || index >= modes.length) return;
                module.settings().mode = modes[index];
                persist();
            });
        section.content().add(new ConsoleRow(host, () -> LABEL_MODE, DESC_MODE, null,
            List.of(new Ctl(control, DESC_MODE),
                ConsoleWidgets.resetCtl(() -> {
                    module.settings().mode = DEFAULTS.mode;
                    persist();
                    host.reload();
                }, LABEL_MODE))));

        stack.add(section);
    }

    // ── 组② 参数调整 ──

    public void buildParams(CompactStack stack) {
        FlightBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f参数调整", SECTION_KEY_PARAMS, host.collapsedSections());

        // 三项模式专属参数：只有当前模式用到时才加入堆叠（等价旧设置声明的 visible 条件）
        if (settings.mode == FlightPolicy.FlightMode.SAFE_GLIDE) {
            section.content().add(numberRow(LABEL_GLIDE_SPEED, DESC_GLIDE_SPEED,
                FlightBypassSettings.GLIDE_SPEED_MIN, FlightBypassSettings.GLIDE_SPEED_MAX,
                () -> (double) settings.glideSpeed,
                value -> settings.glideSpeed = (int) Math.round(value),
                () -> (double) DEFAULTS.glideSpeed));
        }
        if (settings.mode == FlightPolicy.FlightMode.VANILLA_MIMIC) {
            section.content().add(numberRow(LABEL_JUMP_INTERVAL, DESC_JUMP_INTERVAL,
                FlightBypassSettings.JUMP_INTERVAL_MIN, FlightBypassSettings.JUMP_INTERVAL_MAX,
                () -> (double) settings.vanillaJumpInterval,
                value -> settings.vanillaJumpInterval = (int) Math.round(value),
                () -> (double) DEFAULTS.vanillaJumpInterval));
        }
        if (settings.mode == FlightPolicy.FlightMode.SEQUENCE_SCAFFOLD) {
            section.content().add(numberRow(LABEL_SCAFFOLD_DELAY, DESC_SCAFFOLD_DELAY,
                FlightBypassSettings.SCAFFOLD_DELAY_MIN, FlightBypassSettings.SCAFFOLD_DELAY_MAX,
                () -> (double) settings.scaffoldDelay,
                value -> settings.scaffoldDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.scaffoldDelay));
        }

        section.content().add(toggleRow(LABEL_ADAPTIVE, DESC_ADAPTIVE,
            () -> settings.adaptiveSlowdown,
            value -> settings.adaptiveSlowdown = value, () -> DEFAULTS.adaptiveSlowdown));

        // 降级阈值只在自适应降级开启时出现：开关写盘后整页重建，本行随条件加入或移除
        if (settings.adaptiveSlowdown) {
            section.content().add(numberRow(LABEL_DEGRADE_THRESHOLD, DESC_DEGRADE_THRESHOLD,
                FlightBypassSettings.DEGRADE_THRESHOLD_MIN, FlightBypassSettings.DEGRADE_THRESHOLD_MAX,
                () -> (double) settings.rubberBandThreshold,
                value -> settings.rubberBandThreshold = (int) Math.round(value),
                () -> (double) DEFAULTS.rubberBandThreshold));
        }

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 整数数值行：步进 1（旧项目均为 noSlider），改动即写盘；描述同时作为悬停提示 */
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

    /** 开关行：改动即写盘；「拉回降级阈值」的显隐跟随本开关，故写盘后整页重建 */
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

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
