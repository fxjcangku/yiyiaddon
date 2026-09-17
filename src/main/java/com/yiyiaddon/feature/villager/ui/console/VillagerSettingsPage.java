package com.yiyiaddon.feature.villager.ui.console;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.model.VillagerProfessionChoice;
import com.yiyiaddon.feature.villager.model.VillagerTradeMode;
import com.yiyiaddon.feature.villager.ui.VillagerSelectors;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.keybind.AddonKeybind;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingKeybind;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 控制台「设置」页：默认组 7 项平铺 + 按职业折叠分组（D3 拍板，照「逐作物」先例）。
 *
 * <p><b>设置项逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧 {@code AutoVillagerTradeModule}
 * 构造器（旧 {@code :113-267}）：默认组 6 项（旧 {@code :114-165}）+ 快速停止键（旧 {@code :274-279}）
 * 平铺；每个可见职业一个折叠分组，组内 {@code <职业>交易} 物品选择器入口行
 * （旧 {@code :217-230}）+ {@code <职业> 价格上限} 行（旧 {@code :256-267}，
 * 名称含旧原文的那个空格）+ 图书管理员的 {@code 图书管理员附魔书} 行（旧 {@code :233-241}）；
 * 多任务模式另给 {@code 多任务职业} 折叠分组（旧 {@code :197-207}）。</p>
 *
 * <p><b>13 个「购买量(组)」不做界面</b>（D6 拍板）：旧项目该行是 {@code visible(() -> false)} 恒隐藏
 * （旧 {@code :243-254}，描述「已改为默认榨干模式，购买量不再生效」），状态机按默认 1 组传入。</p>
 *
 * <p><b>模式 / 职业联动必须 1:1 复刻</b>（旧 {@code :162} / {@code :202} / {@code :294-301}）：</p>
 * <ul>
 *   <li>非多任务：显示 {@code 目标职业} 行，下方只铺「当前目标职业」那一个折叠分组；</li>
 *   <li>多任务：隐藏 {@code 目标职业} 行，先铺 {@code 多任务职业} 分组（13 个勾选行），
 *       再对每个已勾选职业各铺一个折叠分组。</li>
 * </ul>
 * 可见职业名单由 {@link #visibleProfessions(AutoVillagerTradeModule)} 统一给出（等价旧
 * {@code isProfessionVisible}），状态条与控制台其它处都取它，避免两处各判一次。</p>
 *
 * <p><b>折叠状态</b>：分组按旧口径默认收起，键记在窗口侧（{@link VillagerConsoleScreen#collapsedSections()}），
 * 整页重建不丢；动态键由 {@link VillagerConsoleScreen#ensureCollapsed(String)} 登记。</p>
 *
 * <p><b>数字一律 {@link SettingNumberBox}</b>（禁止滑块，第 123 条）；数值 / 开关 / 循环改动
 * 全部立即 {@link AutoVillagerTradeModule#persistSettings()}（第 173 条）。</p>
 */
public final class VillagerSettingsPage {

    /** 折叠键前缀：一个职业一个键（{@code profession:<职业名>}） */
    private static final String SECTION_PREFIX = "profession:";
    /** 多任务职业分组的折叠键 */
    private static final String PIPELINE_SECTION_KEY = "pipeline";

    /** 图书管理员职业名（旧项目附魔书选择器只属于它，判据来自枚举常量而非新写字符串） */
    private static final String LIBRARIAN = VillagerProfessionChoice.图书管理员.name();

    /** 快速停止键的说明（旧 {@code :276} 逐字） */
    private static final String STOP_KEY_DESCRIPTION = "按此键立即停止交易";
    /** 价格上限的说明（旧 {@code :259} 逐字） */
    private static final String PRICE_DESCRIPTION = "该职业物品的绿宝石价格上限（超过此价格不购买）";
    /** 多任务勾选的说明（旧 {@code :201} 逐字） */
    private static final String PIPELINE_DESCRIPTION = "勾选后该职业加入多任务队列（需在目标物品组先选好该职业的物品）";

    private final VillagerConsoleScreen host;
    private final AutoVillagerTradeModule module;

    public VillagerSettingsPage(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 可见性（旧 isProfessionVisible :294-301 的等价物） ──

    /**
     * 当前应当显示设置的职业名单。
     *
     * <p><b>做什么</b>：多任务模式返回「已勾选」的职业（顺序 = 职业枚举顺序，与任务队列顺序一致）；
     * 其余模式返回当前目标职业一项。</p>
     * <p><b>为什么单独提出来</b>：旧 {@code isProfessionVisible} 是每个设置项各自问一次的可见性谓词，
     * 本项目控制台是「按可见职业铺分组」，模式 / 职业判据只应有一处，状态条的职业 / 物品格也取它。</p>
     */
    public static List<String> visibleProfessions(AutoVillagerTradeModule module) {
        VillagerTradeSettings settings = module.settings();
        if (settings.mode != VillagerTradeMode.PIPELINE) return List.of(settings.profession.name());
        List<String> names = new ArrayList<>();
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            if (settings.isPipelineSelected(choice.name())) names.add(choice.name());
        }
        return names;
    }

    // ── 页面装配 ──

    /** 页面装配：默认组平铺 → （多任务职业分组）→ 每个可见职业一个折叠分组 */
    public void build(CompactStack stack) {
        buildGeneral(stack);
        if (module.settings().mode == VillagerTradeMode.PIPELINE) {
            stack.add(pipelineSection());
        }
        for (String professionName : visibleProfessions(module)) {
            stack.add(professionSection(professionName));
        }
    }

    /**
     * 默认组（旧 {@code sgGeneral}，旧项目该组无组名，故本项目也平铺不套折叠块）：
     * 运行模式 / 绿宝石补给量(组) / 搜索范围(格) / 挂机循环(按钮) / 补货等待(秒) /
     * 目标职业（多任务隐藏）/ 快速停止键。
     */
    private void buildGeneral(CompactStack stack) {
        VillagerTradeSettings settings = module.settings();

        stack.add(new ConsoleRow(host, () -> "运行模式", "选择交易模式", null,
            List.of(new Ctl(cycleOf(VillagerTradeMode.values(), () -> settings.mode.ordinal(),
                index -> {
                    settings.mode = VillagerTradeMode.values()[index];
                    module.persistSettings();
                    // 模式决定「目标职业 / 多任务职业 / 各职业分组」的可见性，整页必须重排
                    host.scheduleReload();
                })))));

        stack.add(numberRow("绿宝石补给量(组)",
            "去绿宝石箱补给时，背包绿宝石补到「底限32个 + 该组数×64」即返回交易",
            1, 27, () -> (double) settings.emeraldSupplyStacks,
            value -> settings.emeraldSupplyStacks = value.intValue()));

        stack.add(numberRow("搜索范围(格)",
            "寻路模式搜索目标村民的半径，村民密集时可调小避免扫到远处村民",
            8, 256, () -> (double) settings.searchRange,
            value -> settings.searchRange = value.intValue()));

        stack.add(toggleRow("挂机循环(按钮)",
            "仅寻路单点/多任务模式的榨干模式生效：全部村民榨干后不结束，等待补货倒计时到点自动重新循环交易",
            () -> settings.idleLoop, value -> settings.idleLoop = value));

        stack.add(numberRow("补货等待(秒)",
            "挂机循环的补货等待时长，与村民补货冷却(2分钟)一致，默认120秒",
            20, 600, () -> (double) settings.restockWaitSeconds,
            value -> settings.restockWaitSeconds = value.intValue()));

        // 目标职业：多任务模式下隐藏（旧 :163 visible(() -> mode.get() != Mode.PIPELINE)）
        if (settings.mode != VillagerTradeMode.PIPELINE) {
            stack.add(new ConsoleRow(host, () -> "目标职业",
                "选择村民职业（多任务模式下由「多任务职业」勾选决定，此选项隐藏）", null,
                List.of(new Ctl(cycleOf(VillagerProfessionChoice.values(), () -> settings.profession.ordinal(),
                    index -> {
                        settings.profession = VillagerProfessionChoice.values()[index];
                        module.persistSettings();
                        // 换职业即换成另一个折叠分组，整页必须重排
                        host.scheduleReload();
                    })))));
        }

        stack.add(keybindRow());
    }

    // ── 分组 ──

    /** 多任务职业分组（旧 {@code sgPipeline}，仅多任务模式可见 旧 {@code :202-203}）：13 个勾选行 */
    private FoldSection pipelineSection() {
        host.ensureCollapsed(PIPELINE_SECTION_KEY);
        FoldSection section = new FoldSection("多任务职业", PIPELINE_SECTION_KEY, host.collapsedSections());
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            String professionName = choice.name();
            section.content().add(toggleRow(professionName, PIPELINE_DESCRIPTION,
                () -> module.settings().isPipelineSelected(professionName),
                value -> {
                    module.settings().setPipelineSelected(professionName, value);
                    module.persistSettings();
                    // 勾选 / 取消决定「它那一个折叠分组」铺不铺，整页必须重排
                    host.scheduleReload();
                },
                null));
        }
        return section;
    }

    /**
     * 单个职业的折叠分组（D3「逐作物」先例：分组标题 = 职业名，组内三行）。
     *
     * <p>组内顺序照规格：{@code <职业>交易} 入口行 → {@code <职业> 价格上限} 行 →
     * （图书管理员）{@code 图书管理员附魔书} 行。</p>
     */
    private FoldSection professionSection(String professionName) {
        String key = SECTION_PREFIX + professionName;
        host.ensureCollapsed(key);
        FoldSection section = new FoldSection("§f" + professionName, key, host.collapsedSections());
        section.content().add(VillagerSelectors.itemRow(host, module, professionName));
        section.content().add(priceRow(professionName));
        if (LIBRARIAN.equals(professionName)) {
            section.content().add(VillagerSelectors.enchantRow(host, module));
        }
        return section;
    }

    /** 某职业的价格上限行（旧 {@code :256-267}）：域 1~64、默认 32，改动即写盘 */
    private ConsoleRow priceRow(String professionName) {
        return numberRow(professionName + " 价格上限", PRICE_DESCRIPTION,
            VillagerTradeSettings.PRICE_LIMIT_MIN, VillagerTradeSettings.PRICE_LIMIT_MAX,
            () -> (double) module.settings().priceLimit(professionName),
            value -> module.settings().setPriceLimit(professionName, value.intValue()));
    }

    // ── 行构件组装 ──

    /**
     * 快速停止键行（旧 {@code :274-279}，默认组末项）：自研键位控件 {@link SettingKeybind}。
     *
     * <p><b>做什么</b>：行右侧一个 160 宽的键位块——显示
     * {@link AddonKeybind#displayName()}（未绑定 {@code None}，逐字照旧框架 {@code Keybind#toString()}），
     * 点一下进入录制（块上显示 {@code ...}），按键即绑定、{@code ESC} 取消、右键清空；
     * 写入后立刻 {@code persistSettings()}（第 172 条）。</p>
     * <p><b>为什么不用清空按钮</b>：旧框架 {@code WKeybind} 的清空是控件自身的清除动作（右键），
     * 不是另摆一个按钮；本项目键位块沿用同一交互，避免多一个旧项目没有的按钮
     * （第 163 条：禁止新增旧项目没有的按钮）。</p>
     */
    private ConsoleRow keybindRow() {
        SettingKeybind keybind = new SettingKeybind(
            () -> module.settings().stopKey,
            value -> {
                module.settings().stopKey = value;
                module.persistSettings();
            }).width(160f);
        return new ConsoleRow(host, () -> "快速停止键", STOP_KEY_DESCRIPTION, null,
            List.of(new Ctl(keybind, STOP_KEY_DESCRIPTION)));
    }

    /** 开关行：改动即写盘（第 173 条） */
    private ConsoleRow toggleRow(String label, String hint,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return toggleRow(label, hint, getter, setter, null);
    }

    /**
     * 开关行（带收尾动作）：{@code afterChange} 用于「勾选状态影响页面结构」的行
     * （多任务勾选），写完盘后再重排页面；传 {@code null} = 只写盘。
     */
    private ConsoleRow toggleRow(String label, String hint,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Runnable afterChange) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
                if (afterChange != null) afterChange.run();
            }))));
    }

    /** 数字行（旧滑条 → 数字框，第 123 条）：步进 1、整数值，改动即写盘 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 Supplier<Double> getter, Consumer<Double> setter) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }

    /** 枚举循环控件：候选 = 枚举中文名（旧 EnumSetting 界面渲染的同一批字面量） */
    private static SettingCycle cycleOf(Enum<?>[] values,
                                        Supplier<Integer> getter,
                                        Consumer<Integer> setter) {
        List<String> options = Arrays.stream(values).map(Enum::toString).toList();
        return new SettingCycle(options, getter, setter);
    }
}
