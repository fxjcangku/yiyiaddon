package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.feature.mining.ui.MiningTargetControls;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;

import java.util.List;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「目标选择」页：采集模式 + 三个单值目标 + 三个名单。
 *
 * <p>行顺序、设置名、描述、状态文案（星露谷口径 {@code 未选择（共 N 项）} / {@code 已选 N / M 项}）、
 * 候选剔除空气的方式、↻ 的清空语义、采集模式切换后置 -1 失效并同步目标的写法——全部与配置页
 * {@code AutoMinerPage.buildTargetGroup()} / {@code buildItemsGroup()} 同源：<b>数据与控制都在
 * {@link MiningTargetControls}</b>，本页只负责把行装进控制台的行构件。</p>
 *
 * <p><b>数据仍是同一份</b>：写的是 {@link MiningSettings} 的字段，改完立即
 * {@link AutoMinerModule#persistSettings()}。</p>
 */
public final class MiningTargetPage {

    // ── 设置名（与配置页逐字同源） ──

    private static final String TITLE_OVERWORLD = "主世界矿石";
    private static final String TITLE_NETHER = "下界矿石";
    private static final String TITLE_BLOCK = "普通方块";
    private static final String TITLE_KEEP = "保留白名单";
    private static final String TITLE_FOOD = "食物白名单";
    private static final String TITLE_PLACE = "搭路方块白名单";

    /** 采集模式分段：顺序即 {@link LootMode} 的序数（精准采集 / 时运） */
    private static final List<String> LOOT_MODE_LABELS =
        List.of(LootMode.SILK_TOUCH.toString(), LootMode.FORTUNE.toString());

    /** 选择器行的「点击选择」按钮（逐字照星露谷控制台页 {@code StardewPlantingPage:69}） */
    private static final String SELECT_LABEL = "点击选择";
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;
    private final MiningTargetControls controls;

    public MiningTargetPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
        // 采集模式切换后本页整页重建，让「共 N 项」与新同步出来的目标立刻反映在行上
        this.controls = new MiningTargetControls(module, owner::reload);
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();
        controls.refreshTotals();

        stack.add(new ConsoleRow(owner, () -> "采集模式",
            "精准采集：目标选择器显示原矿；时运：目标选择器显示掉落物（粗铁/粗金/粗铜等）。切换模式时自动同步目标",
            null, List.of(new Ctl(new SettingSegmented(LOOT_MODE_LABELS,
                () -> settings.lootMode.ordinal(), controls::pickLootMode)))));

        stack.add(selectorRow(TITLE_OVERWORLD,
            "时运模式选掉落物（粗铁/粗金/粗铜等），精准采集选原矿（铁矿石等）",
            () -> controls.oreStatus(false),
            () -> controls.openOreSelector(TITLE_OVERWORLD, false),
            () -> controls.clearOreTarget(false)));

        stack.add(selectorRow(TITLE_NETHER,
            "时运模式选掉落物（下界残骸/金粒/石英），精准采集选原矿（下界残骸等）",
            () -> controls.oreStatus(true),
            () -> controls.openOreSelector(TITLE_NETHER, true),
            () -> controls.clearOreTarget(true)));

        stack.add(selectorRow(TITLE_BLOCK,
            "选择普通方块（石头、泥土、原木等）",
            controls::blockStatus,
            () -> controls.openBlockSelector(TITLE_BLOCK),
            controls::clearBlockTarget));

        stack.add(new Note(owner, "§7§l物品管理", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(selectorRow(TITLE_KEEP,
            "默认保留任意品质工具、白名单食物、目标矿物；此名单内的额外物品/方块也不会被丢弃",
            controls::keepStatus,
            () -> controls.openKeepSelector(TITLE_KEEP),
            controls::clearKeepList));

        stack.add(selectorRow(TITLE_FOOD,
            "从食物箱只拿选中的食物（只显示能吃的食物，默认常用食物，可自由增删）",
            controls::foodStatus,
            () -> controls.openFoodSelector(TITLE_FOOD),
            controls::clearFoodList));

        stack.add(selectorRow(TITLE_PLACE,
            "Baritone搭桥/填坑时使用这些方块，且只保留各一组（多余自动丢弃）",
            controls::placeStatus,
            () -> controls.openPlaceSelector(TITLE_PLACE),
            controls::clearPlaceList));
    }

    // ── 选择器行 ──

    /**
     * 选择器行的统一构造（行样式照星露谷控制台页 {@code StardewPlantingPage:69-85}）：
     * 名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * <p>状态文字用 {@link SettingText}，列宽按当前文本实测宽度给；↻ 的图标与动作都与星露谷一致
     * ——清空本行已选，空则静默。</p>
     */
    private CompactElement selectorRow(String title, String description, Supplier<String> status,
                                       Runnable open, Runnable reset) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            new Ctl(new SettingText(status,
                () -> MinecraftText.measure(status.get(), STATE_FONT_SIZE, false)).alignLeft()),
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset))));
    }

    // ── 选择器候选 / 登记 ID 互转 / 显示名：统一在 MiningRegistry（本类不再自持缓存） ──

    /* 供控制台状态条与概览页复用（它们只依赖本页这两个入口，故保留一行的转发） */
    public static String itemDisplayName(String itemId) { return MiningRegistry.itemDisplayName(itemId); }
    public static String blockDisplayName(String blockId) { return MiningRegistry.blockDisplayName(blockId); }
}
