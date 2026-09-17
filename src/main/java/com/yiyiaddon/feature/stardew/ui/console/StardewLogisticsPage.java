package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.logistics.StardewLogisticsStore;
import com.yiyiaddon.feature.stardew.plan.StardewAmountMode;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * 星露谷控制台「后勤」页：简化后勤开关 + 逐作物配置块。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildLogistics} 与其全部配套私有方法与常量；
 * 方法体、文案与 tooltip 一字未改，只把宿主窗口访问改为经字段读取。</p>
 */
public final class StardewLogisticsPage {

    /** 后勤：每作物块的四条阈值行文案（标签 / 范围 / tooltip / 行尾注释），逐字照旧 */
    private static final String LABEL_RESTOCK_TRIGGER = "种子少于";
    private static final String LABEL_RESTOCK_TARGET = "种子补到";
    private static final String LABEL_UNLOAD_TRIGGER = "成品攒到";
    private static final String LABEL_UNLOAD_KEEP = "成品留底";

    private static final String TIP_RESTOCK_TRIGGER = "背包里这种作物的种子少于这个数时，才去种子箱取种子补货；\n"
        + "补多少由下一行「种子补到」决定。\n"
        + "填 0 = 不自动补货（也就不需要种子箱）。\n"
        + "技术名：种子补货触发";
    private static final String TIP_RESTOCK_TARGET = "每次补货都补到这个数量为止。\n"
        + "收获后多余种子的回收也看它：背包里超过这个数的种子会被送回种子箱，\n"
        + "留下的正好够下一轮补货，不用你手动清背包。\n"
        + "技术名：种子补货目标";
    private static final String TIP_UNLOAD_TRIGGER = "背包里这种作物的成品攒到这个数量，才会去成品箱卸货。\n"
        + "这是最低门槛，不是立刻执行：实际时机排在所有农田任务之后，\n"
        + "而且要走到成品箱前面才卸。背包快满时会提前插队，不会撑爆背包。\n"
        + "技术名：成品卸货触发";
    private static final String TIP_UNLOAD_KEEP = "卸货时背包里留下不卸的数量，0 = 有多少卸多少。\n"
        + "只有需要「留一点成品在手上」时才改。\n"
        + "技术名：成品卸货保留";

    private static final String COMMENT_RESTOCK_TRIGGER = "低于它才去补种";
    private static final String COMMENT_RESTOCK_TARGET = "每次补到这个数";
    private static final String COMMENT_UNLOAD_TRIGGER = "攒够它才去卸货";
    private static final String COMMENT_UNLOAD_KEEP = "卸货时留下不卸";

    /** 后勤：简化后勤下展示的一行说明（逐字照旧 {@code SIMPLE_HINT}） */
    private static final String SIMPLE_HINT = "§7后勤全自动：种子少于2补到8，成品攒到8卸光";

    /** 后勤：作物块空态 / 未识别服务器（逐字照旧 {@code renderCropBlocks}） */
    private static final String LOGISTICS_NO_SERVER =
        "§8未识别当前服务器：进入多人服务器并完成资源检测后可在这里逐作物配置";
    private static final String LOGISTICS_NO_CROP =
        "§8尚未选择作物：在「种植配置」里选中作物后，这里会出现每种作物自己的后勤参数";

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    public StardewLogisticsPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        StardewSettings s = module.settings();
        SettingToggle toggle = new SettingToggle(() -> s.logisticsSimple, value -> {
            s.logisticsSimple = value;
            module.persistSettings();
            // 旧项目该开关的 onChanged 会重建设置页（四个阈值的显示 / 隐藏随之切换）
            owner.reload();
        });
        stack.add(new ConsoleRow(owner, () -> StardewSettings.NAME_LOGISTICS_SIMPLE,
            StardewSettings.DESC_LOGISTICS_SIMPLE, null, List.of(new Ctl(toggle))));

        String serverKey = serverKey();
        if (serverKey == null || serverKey.isBlank()) {
            stack.add(new Note(owner, LOGISTICS_NO_SERVER));
        } else {
            List<CropDefinition> crops = selectedCrops();
            if (crops.isEmpty()) {
                stack.add(new Note(owner, LOGISTICS_NO_CROP));
            } else {
                for (CropDefinition crop : crops) stack.add(cropBlock(crop));
            }
        }

        stack.add(new Note(owner, "§8上面的「简化后勤」勾上时，四个阈值固定用默认值，界面和执行层都用这一份"));
    }

    /** 已选、且在索引中真实存在的作物（顺序与选择器一致；同旧 {@code selectedCrops()}） */
    private List<CropDefinition> selectedCrops() {
        List<CropDefinition> result = new ArrayList<>();
        for (String key : module.selection(StardewSelectorCategory.CROP).selectedKeys()) {
            CropDefinition crop = module.index().cropByKey(key);
            if (crop != null) result.add(crop);
        }
        return result;
    }

    /** 一种作物一块配置：标题 + 种植数量 / 目标数量 +（简化关闭时）四个阈值 + 恢复默认 */
    private CompactElement cropBlock(CropDefinition crop) {
        String cropKey = crop.cropKey();
        FoldSection section = new FoldSection(
            "§b§l" + crop.chineseName() + " §8· §7独立配置  §8(点标题可收起)",
            "logistics:" + cropKey, owner.collapsedSections());
        CompactStack content = section.content();

        String modeTooltip = "决定右边那个数字怎么算：\n"
            + "· 个数：数字就是盆数，填 1 = 种 1 盆\n"
            + "· 组数：数字 × 64，填 1 = 64 盆\n"
            + "只影响要种多少，不影响补货 / 卸货";
        SettingSegmented mode = new SettingSegmented(
            List.of(StardewAmountMode.GROUPS.toString(), StardewAmountMode.ITEMS.toString()),
            () -> cropPlan(cropKey).amountMode().ordinal(),
            index -> {
                StardewCropPlanStore.CropPlan current = cropPlan(cropKey);
                putPlan(cropKey, new StardewCropPlanStore.CropPlan(amountMode(index), current.amountValue()));
            });
        content.add(new ConsoleRow(owner, () -> "§7种植数量", modeTooltip, "§7个数算盆 组数×64",
            List.of(new Ctl(mode))));

        String amountTooltip = "要维持的种植数量：\n"
            + "· 已经种够 → 不再补种，多出来的种子会被回收走\n"
            + "· 不够（有盆空着 / 作物枯死）→ 自动去种子箱取种子补种\n"
            + "想把整个农场种满，就填一个大于总盆数的值";
        SettingNumberBox amount = new SettingNumberBox(1, 9999, 1, "%.0f",
            () -> (double) cropPlan(cropKey).amountValue(),
            value -> putPlan(cropKey, new StardewCropPlanStore.CropPlan(
                cropPlan(cropKey).amountMode(), (int) Math.round(value))));
        content.add(new ConsoleRow(owner, () -> amountLabelText(cropPlan(cropKey).amountMode()),
            amountTooltip, "§7种够这么多就停", List.of(new Ctl(amount))));

        if (module.settings().logisticsSimple) {
            // 简化模式：四个阈值不画出来，改用一行灰字把「实际在用什么值」写明
            content.add(new Note(owner, SIMPLE_HINT, simpleHintTooltip()));
        } else {
            content.add(thresholdRow(cropKey, LABEL_RESTOCK_TRIGGER, 0, 64,
                TIP_RESTOCK_TRIGGER, COMMENT_RESTOCK_TRIGGER,
                value -> {
                    StardewLogisticsStore.CropLogistics current = logistics(cropKey);
                    return new StardewLogisticsStore.CropLogistics(value,
                        current.restockTarget(), current.unloadTrigger(), current.unloadKeep());
                }));
            content.add(thresholdRow(cropKey, LABEL_RESTOCK_TARGET, 1, 64,
                TIP_RESTOCK_TARGET, COMMENT_RESTOCK_TARGET,
                value -> {
                    StardewLogisticsStore.CropLogistics current = logistics(cropKey);
                    return new StardewLogisticsStore.CropLogistics(current.restockTrigger(),
                        value, current.unloadTrigger(), current.unloadKeep());
                }));
            content.add(thresholdRow(cropKey, LABEL_UNLOAD_TRIGGER, 1, 64,
                TIP_UNLOAD_TRIGGER, COMMENT_UNLOAD_TRIGGER,
                value -> {
                    StardewLogisticsStore.CropLogistics current = logistics(cropKey);
                    return new StardewLogisticsStore.CropLogistics(current.restockTrigger(),
                        current.restockTarget(), value, current.unloadKeep());
                }));
            content.add(thresholdRow(cropKey, LABEL_UNLOAD_KEEP, 0, 64,
                TIP_UNLOAD_KEEP, COMMENT_UNLOAD_KEEP,
                value -> {
                    StardewLogisticsStore.CropLogistics current = logistics(cropKey);
                    return new StardewLogisticsStore.CropLogistics(current.restockTrigger(),
                        current.restockTarget(), current.unloadTrigger(), value);
                }));

            String resetTooltip = resetTooltip(crop);
            // 空态禁用：判据来自本作物当前的数量方案与后勤参数（与下面重置动作写入的两个 store 同源，
            // 都是 CropPlan.DEFAULT / CropLogistics.DEFAULT；两者已是默认值时按钮禁用），
            // 逐帧求值见 IconButton#disabledWhen(Supplier)
            IconButton reset = new IconButton(StardewConsoleScreen.GLYPH_RESET, () -> {
                putPlan(cropKey, StardewCropPlanStore.CropPlan.DEFAULT);
                StardewLogisticsStore.put(serverKey(), fingerprint(), cropKey,
                    StardewLogisticsStore.CropLogistics.DEFAULT);
            }).disabledWhen(() -> cropPlan(cropKey).equals(StardewCropPlanStore.CropPlan.DEFAULT)
                && logistics(cropKey).equals(StardewLogisticsStore.CropLogistics.DEFAULT));
            content.add(new ConsoleRow(owner, () -> "§7恢复默认", resetTooltip, "§7还原本作物参数",
                List.of(new Ctl(reset, resetTooltip))));
        }
        return section;
    }

    /** 单行「§7标签 + 整数输入 + §7行尾注释」 */
    private CompactElement thresholdRow(String cropKey, String label, int min, int max,
                                        String tooltip, String comment,
                                        IntFunction<StardewLogisticsStore.CropLogistics> updater) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) thresholdValue(cropKey, label),
            value -> StardewLogisticsStore.put(serverKey(), fingerprint(), cropKey,
                updater.apply((int) Math.round(value))));
        return new ConsoleRow(owner, () -> "§7" + label, tooltip, "§7" + comment, List.of(new Ctl(box, tooltip)));
    }

    /** 按标签取该作物当前阈值（四个输入框各自独立，改一个不会顶回另一个） */
    private int thresholdValue(String cropKey, String label) {
        StardewLogisticsStore.CropLogistics current = logistics(cropKey);
        return switch (label) {
            case LABEL_RESTOCK_TRIGGER -> current.restockTrigger();
            case LABEL_RESTOCK_TARGET -> current.restockTarget();
            case LABEL_UNLOAD_TRIGGER -> current.unloadTrigger();
            default -> current.unloadKeep();
        };
    }

    private static String amountLabelText(StardewAmountMode mode) {
        return mode == StardewAmountMode.ITEMS ? "§7目标个数" : "§7目标组数";
    }

    private static StardewAmountMode amountMode(int index) {
        StardewAmountMode[] values = StardewAmountMode.values();
        return index >= 0 && index < values.length ? values[index] : StardewAmountMode.GROUPS;
    }

    private static String simpleHintTooltip() {
        StardewLogisticsStore.CropLogistics defaults = StardewLogisticsStore.CropLogistics.DEFAULT;
        return "后勤全自动，不用设置：\n"
            + "· 种子少于 " + defaults.restockTrigger()
            + " 才去种子箱补，补到 " + defaults.restockTarget() + "\n"
            + "· 成品攒到 " + defaults.unloadTrigger()
            + " 才去成品箱卸，卸完背包不留底\n"
            + "想自己调这四个数：到「后勤参数」分组顶上关掉「简化后勤」";
    }

    private static String resetTooltip(CropDefinition crop) {
        StardewLogisticsStore.CropLogistics defaults = StardewLogisticsStore.CropLogistics.DEFAULT;
        return "把「" + crop.chineseName() + "」的数量模式与四个后勤阈值恢复默认"
            + "（组数 1，后勤 " + defaults.restockTrigger()
            + " / " + defaults.restockTarget()
            + " / " + defaults.unloadTrigger()
            + " / " + defaults.unloadKeep() + "）\n"
            + "只影响这一种作物，其它作物各自的参数不动";
    }

    private StardewCropPlanStore.CropPlan cropPlan(String cropKey) {
        return StardewCropPlanStore.get(serverKey(), fingerprint(), cropKey);
    }

    private void putPlan(String cropKey, StardewCropPlanStore.CropPlan plan) {
        StardewCropPlanStore.put(serverKey(), fingerprint(), cropKey, plan);
    }

    private StardewLogisticsStore.CropLogistics logistics(String cropKey) {
        return StardewLogisticsStore.get(serverKey(), fingerprint(), cropKey);
    }

    private static String serverKey() {
        return ResourceExtractionService.serverKey();
    }

    private static String fingerprint() {
        return ResourceExtractionService.fingerprint();
    }
}
