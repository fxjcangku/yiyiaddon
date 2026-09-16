package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.config.AutoFarmSettings;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.ui.FarmSelectors;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;

/**
 * 控制台「设置」页：16 个可视静态项按旧项目四个分组展开
 * （分组名与默认收起状态逐字照旧项目，见 51 号第二节）。
 *
 * <p><b>设置项逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧 {@code AutoFarmMatrix}
 * 构造器（{@code :136-273}）；旧滑条形态不保留，数字一律 {@link SettingNumberBox}
 * （差异 D-13-01）。颜色项走 {@link SettingColorPicker}（调色板直接改 EspColor，
 * 改动即写盘）。</p>
 */
public final class FarmSettingsPage {

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmSettingsPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：辅助工具（收起）→ 作物选择（展开）→ 运行参数（收起）→ 渲染显示（收起） */
    public void build(CompactStack stack) {
        buildHelper(stack);
        buildCrops(stack);
        buildLogistics(stack);
        buildRender(stack);
    }

    // ── 辅助工具 ──

    private void buildHelper(CompactStack stack) {
        AutoFarmSettings settings = module.settings();
        FoldSection section = new FoldSection("辅助工具", "settings:helper", host.collapsedSections());
        section.content().add(toggleRow("防踩踏", "农田范围内拦截跳跃键，避免踩坏耕地",
            () -> settings.antiTrample, value -> settings.antiTrample = value));
        section.content().add(toggleRow("自动锄地",
            "农田范围内发现草方块/泥土时，自动拿锄头锄成耕地；背包无锄头则跳过",
            () -> settings.autoTill, value -> settings.autoTill = value));
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

        // 收割模式（旧 onChanged 的中文提示保留：切换即播报）
        section.content().add(new ConsoleRow(host,
            () -> "收割模式",
            "单个收割：每次只处理一个成熟目标；批量收割：一次性锁定全部成熟目标瞬间暴力破坏",
            null,
            List.of(new Ctl(cycleOf(HarvestMode.values(), () -> settings.harvestMode.ordinal(),
                index -> {
                    settings.harvestMode = HarvestMode.values()[index];
                    module.onHarvestModeChanged(settings.harvestMode);
                    module.persistSettings();
                })))));
        section.content().add(new ConsoleRow(host,
            () -> "补种模式",
            "顺序优先：按作物枚举顺序种满一种再种下一种；均匀轮转：启用作物轮流种保持均衡；就近跟随：空耕地种回周围已有作物的同类，保持混种分区",
            null,
            List.of(new Ctl(cycleOf(PlantMode.values(), () -> settings.plantMode.ordinal(),
                index -> {
                    settings.plantMode = PlantMode.values()[index];
                    module.persistSettings();
                })))));
        // 杂物卸货（旧 IntSetting 1~64 无滑条，可见性条件 = 启用了会产杂物的作物）
        section.content().add(numberRow("杂物卸货",
            "杂物（毒马铃薯 + 仙人掌花）攒够多少个才卸货一次，避免捡一个就跑一次",
            1, 64, () -> (double) settings.poisonUnloadThreshold,
            value -> settings.poisonUnloadThreshold = value.intValue()));

        section.content().add(numberRow("发包速率(BPT)",
            "每 tick 最多发送多少个破坏/播种/容器操作包",
            1, 30, () -> (double) settings.bpt,
            value -> settings.bpt = value.intValue()));

        section.content().add(numberRow("收割距离",
            "能操作多远的方块，原版上限约 4.5 格，低于 3 会导致寻路卡住",
            3, 8, () -> (double) settings.reachDistance,
            value -> settings.reachDistance = value.intValue()));

        stack.add(section);
    }

    // ── 渲染显示 ──

    private void buildRender(CompactStack stack) {
        AutoFarmSettings settings = module.settings();
        FoldSection section = new FoldSection("渲染显示", "settings:render", host.collapsedSections());

        section.content().add(toggleRow("农田边界", "只渲染农场范围的外框一圈（不填面），大农场也不卡",
            () -> settings.renderBounds, value -> settings.renderBounds = value));
        section.content().add(colorRow("边界框颜色", settings.boundsColor));
        section.content().add(toggleRow("目标显示", "高亮当前正在作业的目标方块",
            () -> settings.renderTarget, value -> settings.renderTarget = value));
        section.content().add(colorRow("目标颜色", settings.targetColor));
        section.content().add(toggleRow("点位字牌", "各绑定箱头顶显示防呆标签",
            () -> settings.renderLabels, value -> settings.renderLabels = value));

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 开关行：改动即写盘（第 173 条） */
    private ConsoleRow toggleRow(String label, String hint,
                                 java.util.function.Supplier<Boolean> getter,
                                 java.util.function.Consumer<Boolean> setter) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }

    /** 数字行（旧滑条 → 数字框，D-13-01）：步进 1、整数值，改动即写盘 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 java.util.function.Supplier<Double> getter,
                                 java.util.function.Consumer<Double> setter) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }

    /** 颜色行：调色板直接改传入的 EspColor，关闭窗口即生效（第 151 条），改动即写盘 */
    private ConsoleRow colorRow(String label, com.yiyiaddon.ui.render.world.EspColor color) {
        return new ConsoleRow(host, () -> label, null, null,
            List.of(new Ctl(new SettingColorPicker(label, color, module::persistSettings))));
    }

    /** 枚举循环控件：候选 = 枚举中文名（旧 EnumSetting 界面渲染的同一批字面量） */
    private static SettingCycle cycleOf(Enum<?>[] values,
                                        java.util.function.Supplier<Integer> getter,
                                        java.util.function.Consumer<Integer> setter) {
        List<String> options = Arrays.stream(values).map(Enum::toString).toList();
        return new SettingCycle(options, getter, setter);
    }
}
