package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.config.AutoFarmSettings;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.ui.FarmSelectors;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 控制台「设置」页：11 个可视静态项按旧项目三个分组展开
 * （分组名与默认收起状态逐字照旧项目，见 51 号第二节）。
 *
 * <p><b>设置项逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧 {@code AutoFarmMatrix}
 * 构造器（{@code :136-273}）；旧滑条形态不保留，数字一律 {@link SettingNumberBox}
 * （差异 D-13-01）。</p>
 *
 * <p><b>「渲染显示」分组已撤销</b>（用户 2026-09-19：「所有标点选择点位位置的模块参照星露谷农场的
 * 点位设置」）：农田边界 / 边界框颜色 / 目标显示 / 目标颜色 / 点位字牌五项已迁到控制台「点位」页的
 * 「显示与颜色」小节（连同新增的四个箱子方框与「字牌大小」）。同一份设置只能有一处承载
 * （项目第 209 条），留两份会出现「改了一处、另一处还是旧值」。其余设置项一个没动。</p>
 */
public final class FarmSettingsPage {

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoFarmSettings DEFAULTS = new AutoFarmSettings();

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmSettingsPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：辅助工具（收起）→ 作物选择（展开）→ 运行参数（收起） */
    public void build(CompactStack stack) {
        buildHelper(stack);
        buildCrops(stack);
        buildLogistics(stack);
    }

    // ── 辅助工具 ──

    private void buildHelper(CompactStack stack) {
        AutoFarmSettings settings = module.settings();
        FoldSection section = new FoldSection("辅助工具", "settings:helper", host.collapsedSections());
        section.content().add(toggleRow("防踩踏", "农田范围内拦截跳跃键，避免踩坏耕地",
            () -> settings.antiTrample, value -> settings.antiTrample = value,
            () -> DEFAULTS.antiTrample));
        section.content().add(toggleRow("自动锄地",
            "农田范围内发现草方块/泥土时，自动拿锄头锄成耕地；背包无锄头则跳过",
            () -> settings.autoTill, value -> settings.autoTill = value,
            () -> DEFAULTS.autoTill));
        stack.add(section);
    }

    // ── 作物选择（四类选择器，每行带图标；候选与已选两栏由 SelectorScreen 承载） ──

    private void buildCrops(CompactStack stack) {
        FoldSection section = new FoldSection("作物选择", "settings:crops", host.collapsedSections());
        section.content().add(FarmSelectors.row(host, module, "双作物", "种子与产物分离：小麦、甜菜根",
            module::isDoubleCrop, () -> module.settings().cropsDouble));
        section.content().add(FarmSelectors.row(host, module, "单作物", "产物即种子：胡萝卜、马铃薯、下界疣",
            module::isSingleCrop, () -> module.settings().cropsSingle));
        section.content().add(FarmSelectors.row(host, module, "柱状物", "切根部上方：竹子、甘蔗、仙人掌",
            module::isPillar, () -> module.settings().cropsPillar));
        section.content().add(FarmSelectors.row(host, module, "果实", "只砍果实：南瓜、西瓜",
            module::isFruit, () -> module.settings().cropsFruit));
        stack.add(section);
    }

    // ── 运行参数 ──

    private void buildLogistics(CompactStack stack) {
        AutoFarmSettings settings = module.settings();
        FoldSection section = new FoldSection("运行参数", "settings:logistics", host.collapsedSections());

        // 收割模式（旧 onChanged 的中文提示保留：切换即播报）；行尾可见提示见第 213 条
        section.content().add(new ConsoleRow(host,
            () -> "收割模式",
            "单个收割：每次只处理一个成熟目标；批量收割：一次性锁定全部成熟目标瞬间暴力破坏",
            COMMENT_CYCLE,
            List.of(new Ctl(cycleOf(HarvestMode.values(), () -> settings.harvestMode.ordinal(),
                index -> {
                    settings.harvestMode = HarvestMode.values()[index];
                    module.onHarvestModeChanged(settings.harvestMode);
                    module.persistSettings();
                })),
                ConsoleWidgets.resetCtl(() -> {
                    // 走与切换同一条路径：模块钩子照原样调用
                    settings.harvestMode = DEFAULTS.harvestMode;
                    module.onHarvestModeChanged(settings.harvestMode);
                    module.persistSettings();
                    host.reload();
                }, "收割模式"))));
        section.content().add(new ConsoleRow(host,
            () -> "补种模式",
            "顺序优先：按作物枚举顺序种满一种再种下一种；均匀轮转：启用作物轮流种保持均衡；就近跟随：空耕地种回周围已有作物的同类，保持混种分区",
            COMMENT_CYCLE,
            List.of(new Ctl(cycleOf(PlantMode.values(), () -> settings.plantMode.ordinal(),
                index -> {
                    settings.plantMode = PlantMode.values()[index];
                    module.persistSettings();
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.plantMode = DEFAULTS.plantMode;
                    module.persistSettings();
                    host.reload();
                }, "补种模式"))));
        // 杂物卸货（旧 IntSetting 1~64 无滑条，可见性条件 = 启用了会产杂物的作物）
        section.content().add(numberRow("杂物卸货",
            "杂物（毒马铃薯 + 仙人掌花）攒够多少个才卸货一次，避免捡一个就跑一次",
            1, 64, () -> (double) settings.poisonUnloadThreshold,
            value -> settings.poisonUnloadThreshold = value.intValue(),
            () -> (double) DEFAULTS.poisonUnloadThreshold));

        section.content().add(numberRow("发包速率(BPT)",
            "每 tick 最多发送多少个破坏/播种/容器操作包",
            1, 30, () -> (double) settings.bpt,
            value -> settings.bpt = value.intValue(),
            () -> (double) DEFAULTS.bpt));

        section.content().add(numberRow("收割距离",
            "能操作多远的方块，原版上限约 4.5 格，低于 3 会导致寻路卡住",
            3, 8, () -> (double) settings.reachDistance,
            value -> settings.reachDistance = value.intValue(),
            () -> (double) DEFAULTS.reachDistance));

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 开关行：改动即写盘（第 173 条） */
    private ConsoleRow toggleRow(String label, String hint,
                                 java.util.function.Supplier<Boolean> getter,
                                 java.util.function.Consumer<Boolean> setter,
                                 java.util.function.Supplier<Boolean> defaultValue) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    host.reload();
                }, label)));
    }

    /** 数字行（旧滑条 → 数字框，D-13-01）：步进 1、整数值，改动即写盘 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 java.util.function.Supplier<Double> getter,
                                 java.util.function.Consumer<Double> setter,
                                 java.util.function.Supplier<Double> defaultValue) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    host.reload();
                }, label)));
    }

    /** 枚举循环控件：候选 = 枚举中文名（旧 EnumSetting 界面渲染的同一批字面量） */
    private static SettingCycle cycleOf(Enum<?>[] values,
                                        java.util.function.Supplier<Integer> getter,
                                        java.util.function.Consumer<Integer> setter) {
        List<String> options = Arrays.stream(values).map(Enum::toString).toList();
        return new SettingCycle(options, getter, setter);
    }
}
