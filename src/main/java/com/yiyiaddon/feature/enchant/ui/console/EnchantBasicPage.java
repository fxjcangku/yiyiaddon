package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.model.EnchantRunMode;
import com.yiyiaddon.feature.enchant.model.EnchantSuccessSound;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 自动附魔控制台「基础设置」页：旧 {@code sgBasic} 分组（{@code :114-176}）的全部可见项。
 *
 * <p>设置名、描述、取值域、默认值与可见条件逐字沿旧；顺序即旧分组的声明顺序。
 * 「目标模式」分段放在本页第一行（沿用旧 {@code sgBasic} 首位）。</p>
 *
 * <p><b>可见条件照旧</b>：{@code 单轮抽取次数} / {@code 书本补给组数} 仅非 GEAR；
 * {@code 每批取用数量} / {@code 极品附魔数量} / {@code 装备运行模式} 仅 GEAR；
 * {@code 附魔书运行模式} 仅 BOOK；{@code 自定义运行模式} 仅 CUSTOM；
 * {@code 成功提示音类型} 仅 {@code 成功提示音} 开启时。整页可重建，条件不满足的行压根不加入堆叠
 * （等价旧 {@code visible(...)}）。</p>
 *
 * <p><b>两项旧 {@code .visible(() -> false)} 的设置不出现</b>：{@code ESP标点} 与 {@code 返回挂机视角}
 * 在旧项目界面永不显示（值照旧读写，落盘不受影响），控制台同样不显示这两项。
 * {@code 记录合成日志} 已按用户裁定随日志功能删除，本页不出现。</p>
 */
public final class EnchantBasicPage {

    /** 目标模式分段：顺序即枚举序（GEAR / BOOK / CUSTOM），文案逐字取自 {@code TargetMode} */
    private static final List<String> TARGET_MODE_LABELS = Arrays.stream(EnchantTargetMode.values())
        .map(EnchantTargetMode::title)
        .toList();

    /** 运行模式分段：顺序即枚举序（纯附魔模式 / 挂机循环），文案逐字取自 {@code RunMode} */
    private static final List<String> RUN_MODE_LABELS = Arrays.stream(EnchantRunMode.values())
        .map(EnchantRunMode::title)
        .toList();

    /** 提示音轮换项：12 项文案逐字取自 {@code SuccessSound} */
    private static final List<String> SOUND_LABELS = Arrays.stream(EnchantSuccessSound.values())
        .map(EnchantSuccessSound::title)
        .toList();

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final EnchantSettings DEFAULTS = new EnchantSettings();

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;

    public EnchantBasicPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        EnchantSettings settings = module.settings();
        EnchantTargetMode mode = settings.targetMode;

        stack.add(new ConsoleRow(owner, () -> "目标模式",
            "原版装备附魔 / 原版附魔书 / 自定义附魔，三模式互斥切换", null,
            List.of(new Ctl(new SettingSegmented(TARGET_MODE_LABELS,
                    () -> settings.targetMode.ordinal(), this::pickTargetMode)),
                // 恢复默认走同一条路径：写回后同样重排页签
                ConsoleWidgets.resetCtl(() -> {
                    settings.targetMode = DEFAULTS.targetMode;
                    module.persistSettings();
                    owner.onTargetModeChanged();
                }, "目标模式"))));

        if (mode != EnchantTargetMode.GEAR) {
            stack.add(intRow("单轮抽取次数", "挂机循环每轮附魔最大次数，纯附魔模式忽略此项",
                1, 100, () -> settings.singleRoundDraws, value -> settings.singleRoundDraws = value,
                () -> DEFAULTS.singleRoundDraws));
        }

        stack.add(intRow("GUI操作延迟(Tick)", "所有 GUI 点击之间的等待 Tick 数",
            1, 10, () -> settings.guiDelayTick, value -> settings.guiDelayTick = value,
            () -> DEFAULTS.guiDelayTick));

        stack.add(intRow("发包打开距离", "发包开箱/开附魔台/开砂轮/开铁砧允许的最大距离（格），超出则先寻路靠近",
            1, 32, () -> settings.openDistance, value -> settings.openDistance = value,
            () -> DEFAULTS.openDistance));

        if (mode != EnchantTargetMode.GEAR) {
            stack.add(intRow("书本补给组数", "每次去书箱抓取的组数（1组=64本）",
                1, 10, () -> settings.bookSupplyGroups, value -> settings.bookSupplyGroups = value,
                () -> DEFAULTS.bookSupplyGroups));
        }

        stack.add(intRow("青金石补给组数", "每次去青金石箱抓取的组数（1组=64个）",
            1, 10, () -> settings.lapisSupplyGroups, value -> settings.lapisSupplyGroups = value,
            () -> DEFAULTS.lapisSupplyGroups));

        if (mode == EnchantTargetMode.GEAR) {
            stack.add(intRow("每批取用数量",
                "每次任务最多从装备箱取用的目标装备数量，铁砧合并会消耗装备，最终完成数可能小于此值",
                1, 16, () -> settings.batchTakeCount, value -> settings.batchTakeCount = value,
                () -> DEFAULTS.batchTakeCount));
            stack.add(intRow("极品附魔数量", "本次运行最终产出的极品装备数量，达到后自动停机",
                1, 64, () -> settings.topGearCount, value -> settings.topGearCount = value,
                () -> DEFAULTS.topGearCount));
            stack.add(runModeRow("装备运行模式",
                "原版装备附魔：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验",
                () -> settings.gearRunMode.ordinal(),
                index -> settings.gearRunMode = EnchantRunMode.values()[index],
                () -> DEFAULTS.gearRunMode.ordinal()));
        }

        if (mode == EnchantTargetMode.BOOK) {
            stack.add(runModeRow("附魔书运行模式",
                "原版附魔书：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验",
                () -> settings.bookRunMode.ordinal(),
                index -> settings.bookRunMode = EnchantRunMode.values()[index],
                () -> DEFAULTS.bookRunMode.ordinal()));
        }

        if (mode == EnchantTargetMode.CUSTOM) {
            stack.add(runModeRow("自定义运行模式",
                "自定义附魔：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验",
                () -> settings.customRunMode.ordinal(),
                index -> settings.customRunMode = EnchantRunMode.values()[index],
                () -> DEFAULTS.customRunMode.ordinal()));
        }

        stack.add(new ConsoleRow(owner, () -> "成功提示音",
            "达成目标时播放本地提示音（命中附魔书 / 装备达成极品）", null,
            List.of(new Ctl(new SettingToggle(() -> settings.successSoundEnabled, value -> {
                settings.successSoundEnabled = value;
                module.persistSettings();
                // 下一行的显示随开关切换，本页整页重建后才会出现 / 消失
                owner.reload();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.successSoundEnabled = DEFAULTS.successSoundEnabled;
                    module.persistSettings();
                    owner.reload();
                }, "成功提示音"))));

        if (settings.successSoundEnabled) {
            // 行尾可见提示见第 213 条（循环控件与只读数值框同族，看不出能点）
            stack.add(new ConsoleRow(owner, () -> "成功提示音类型", "选择达成目标时播放的音效", COMMENT_CYCLE,
                List.of(new Ctl(new SettingCycle(SOUND_LABELS,
                        () -> settings.successSoundType.ordinal(),
                        index -> {
                            settings.successSoundType = EnchantSuccessSound.values()[index];
                            module.persistSettings();
                        })),
                    ConsoleWidgets.resetCtl(() -> {
                        settings.successSoundType = DEFAULTS.successSoundType;
                        module.persistSettings();
                        owner.reload();
                    }, "成功提示音类型"))));
        }
    }

    // ── 行构件 ──

    /** 整数设置行：步进 1、无滑块（旧项目全部 {@code noSlider}），改动即时落盘；行尾 ↺ 写回出厂值 */
    private ConsoleRow intRow(String title, String description, int min, int max,
                              IntSupplier getter, IntConsumer setter, IntSupplier defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.getAsInt(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> title, description, null, List.of(new Ctl(box),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.getAsInt());
                module.persistSettings();
                owner.reload();
            }, title)));
    }

    /** 运行模式行：分段控件（旧项目是下拉；本项目枚举设置统一用分段 / 轮换控件），改动即时落盘 */
    private ConsoleRow runModeRow(String title, String description,
                                  IntSupplier selected, IntConsumer setter, IntSupplier defaultValue) {
        return new ConsoleRow(owner, () -> title, description, null,
            List.of(new Ctl(new SettingSegmented(RUN_MODE_LABELS, () -> selected.getAsInt(), index -> {
                    setter.accept(index);
                    module.persistSettings();
                })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.getAsInt());
                    module.persistSettings();
                    owner.reload();
                }, title)));
    }

    /**
     * 切换目标模式：写回 → 落盘 → 页签立即重排（整页重建）。
     *
     * <p>等价旧 {@code 目标模式.onChanged(m -> {同步模式分组(); 刷新模式界面();})} 与
     * {@code 目标模式} 的自动保存。</p>
     */
    private void pickTargetMode(int index) {
        if (index < 0 || index >= EnchantTargetMode.values().length) return;
        module.settings().targetMode = EnchantTargetMode.values()[index];
        module.persistSettings();
        owner.onTargetModeChanged();
    }
}
