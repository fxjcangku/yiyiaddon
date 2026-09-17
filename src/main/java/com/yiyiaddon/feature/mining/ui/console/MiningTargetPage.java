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
import com.yiyiaddon.ui.console.ConsoleStateColumn;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「目标选择」页：采集模式 + 三个单值目标 + 三个名单。
 *
 * <p>行顺序、设置名、描述、状态文案（星露谷口径 {@code 未选择（共 N 项）} / {@code 已选 N / M 项}）、
 * 候选剔除空气的方式、↻ 的清空语义、采集模式切换后置 -1 失效并同步目标的写法——<b>数据与控制都在
 * {@link MiningTargetControls}</b>，本页只负责把行装进控制台的行构件。{@code 物品管理} 组的三行
 * 也在本页（页内小节标题），2026-09-16 起它是这三行的唯一落点（配置页不再平铺设置）。</p>
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
    /**
     * 状态列的下界：本页状态文字是「未选择（共 N 项）」或选中物的显示名，取前者按 999 项量宽
     * （显示名更宽时由 {@link ConsoleStateColumn} 按实测值加宽，列宽本身不回落）。
     */
    private static final String STATE_LONGEST = "未选择（共 999 项）";
    /** 行内图标的字号口径：{@code IconButton} 取自身边长的 0.58 倍（{@code IconButton#resolvedIconSize}） */
    private static final float ICON_GLYPH_RATIO = 0.58f;
    /** Material Symbols 图标网格的内缩：墨迹在 1 em 的 advance 里左右各留 1/6（量法见 {@link #measureIconInkInset()}） */
    private static final float ICON_INK_MARGIN = 1f / 6f;

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;
    private final MiningTargetControls controls;
    /** 六行共用的状态列宽度（见 {@link ConsoleStateColumn}：浮动会把「点击选择」顶得左右移动） */
    private final ConsoleStateColumn stateColumn = new ConsoleStateColumn(STATE_LONGEST);
    /** ↻ 图标的字形内缩量：本页构建时量一次（见 {@link #measureIconInkInset()}） */
    private final float iconInkInset = measureIconInkInset();

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

        // 采集模式行只有分段控件一个控件，它的右缘落在行右内边距线上；下面各行的最右是 ↻ 图标按钮，
        // 字形居中于命中框、可见右缘还要往里退 iconInkInset，两套控件的右边界于是错开
        // （用户 2026-09-16：「两套控件的右边界不在同一条竖线上」）。补上等量空白后两者同线。
        stack.add(new ConsoleRow(owner, () -> "采集模式",
            "精准采集：目标选择器显示原矿；时运：目标选择器显示掉落物（粗铁/粗金/粗铜等）。切换模式时自动同步目标",
            null, List.of(new Ctl(insetRight(new SettingSegmented(LOOT_MODE_LABELS,
                () -> settings.lootMode.ordinal(), controls::pickLootMode), iconInkInset)))));

        stack.add(selectorRow(TITLE_OVERWORLD,
            "时运模式选掉落物（粗铁/粗金/粗铜等），精准采集选原矿（铁矿石等）",
            () -> controls.oreStatus(false),
            () -> controls.openOreSelector(TITLE_OVERWORLD, false),
            () -> controls.clearOreTarget(false),
            () -> blank(settings.overworldOreTarget)));

        stack.add(selectorRow(TITLE_NETHER,
            "时运模式选掉落物（下界残骸/金粒/石英），精准采集选原矿（下界残骸等）",
            () -> controls.oreStatus(true),
            () -> controls.openOreSelector(TITLE_NETHER, true),
            () -> controls.clearOreTarget(true),
            () -> blank(settings.netherOreTarget)));

        stack.add(selectorRow(TITLE_BLOCK,
            "选择普通方块（石头、泥土、原木等）",
            controls::blockStatus,
            () -> controls.openBlockSelector(TITLE_BLOCK),
            controls::clearBlockTarget,
            () -> blank(settings.blockTarget)));

        stack.add(new Note(owner, "§7§l物品管理", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(selectorRow(TITLE_KEEP,
            "默认保留任意品质工具、白名单食物、目标矿物；此名单内的额外物品/方块也不会被丢弃",
            controls::keepStatus,
            () -> controls.openKeepSelector(TITLE_KEEP),
            controls::clearKeepList,
            () -> settings.keepWhitelist.isEmpty()));

        stack.add(selectorRow(TITLE_FOOD,
            "从食物箱只拿选中的食物（只显示能吃的食物，默认常用食物，可自由增删）",
            controls::foodStatus,
            () -> controls.openFoodSelector(TITLE_FOOD),
            controls::clearFoodList,
            () -> settings.foodWhitelist.isEmpty()));

        stack.add(selectorRow(TITLE_PLACE,
            "Baritone搭桥/填坑时使用这些方块，且只保留各一组（多余自动丢弃）",
            controls::placeStatus,
            () -> controls.openPlaceSelector(TITLE_PLACE),
            controls::clearPlaceList,
            () -> settings.placeBlocks.isEmpty()));
    }

    // ── 右基线对齐 ──

    /**
     * ↻ 图标按钮的「可见墨迹右缘」相对 24×24 命中框的内缩量。
     *
     * <p><b>为什么要量这个数：</b>控制台行内控件是整组右对齐的（{@code ConsoleRow#controlsStartX}），
     * 但 {@code IconButton} 把字形居中画在命中框里，字形两侧各留一段空白，因此 ↻ 的<b>可见</b>右缘
     * 比命中框右缘更靠里；而分段控件的玻璃是实心的，右缘正好压在命中框右缘上。两者并排即错位
     * ——正是用户 2026-09-16 截图里的「分段控件与 ↻ 图标不在同一条竖线上」。</p>
     *
     * <p><b>量法</b>（26.1.2 自带 {@code MaterialSymbolsRounded.ttf} 实测）：图标 advance = 1 em
     * （960/960 网格），刷新图标 U+E5D5 的墨迹 xMin/xMax = 160/800，即左右各留 1/6 em。
     * 命中框 24、字号 0.58×24 时：{@code (24 − 13.92)/2 + 13.92/6 ≈ 7.36}。
     * {@code IconButton} 的字号口径若变动，这里量出来的值会跟着变（不写死数字）。</p>
     */
    private static float measureIconInkInset() {
        float box = IconButton.DEFAULT_SIZE;
        float advance = FontRenderer.measureTextWidth(ConsoleMetrics.GLYPH_RESET, box * ICON_GLYPH_RATIO,
            FontRenderer.MATERIAL_SYMBOLS);
        return (box - advance) / 2f + advance * ICON_INK_MARGIN;
    }

    /** 右侧补白包装：内层控件照旧画在自己的左上角，只在自身宽度上加 {@code inset}（见 {@link RightInset}）。 */
    private static SettingWidget insetRight(SettingWidget inner, float inset) {
        return new RightInset(inner, inset);
    }

    /**
     * 行尾补白控件：把内层控件整体左移固定像素，用于「让实心控件的可见右缘与 ↻ 图标的可见右缘同线」。
     *
     * <p><b>为什么不加一个「占位控件」了事：</b>{@code ConsoleRow} 在相邻控件之间固定插
     * {@code CONTROL_GAP = 10} 的间距，补白只有 7.36 像素时占位控件反而会把分段控件推过头、还会多一条
     * 10 像素的隐形间隙；把补白直接算进本控件的宽度里，间距与命中口径都与原来一致（补白区不可点，
     * 但那段本来也是 ↻ 命中框内部的空白，用户点不到任何东西）。</p>
     */
    private static final class RightInset extends SettingWidget {

        private final SettingWidget inner;
        private final float inset;

        private RightInset(SettingWidget inner, float inset) {
            this.inner = inner;
            this.inset = Math.max(0f, inset);
        }

        @Override
        public float getWidth() {
            return inner.getWidth() + inset;
        }

        @Override
        public float getHeight() {
            return inner.getHeight();
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float alpha) {
            inner.draw(canvas, x, y, alpha);
        }

        @Override
        public void update(float dt) {
            inner.update(dt);
        }

        @Override
        public boolean isAnimating() {
            return inner.isAnimating();
        }

        @Override
        public void hover(float mouseX, float mouseY, float x, float y, float width) {
            inner.hover(mouseX, mouseY, x, y, inner.getWidth());
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, int button) {
            return inner.onClick(mx, my, x, y, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y) {
            return inner.onDrag(mx, my, x, y);
        }
    }

    // ── 选择器行 ──

    /**
     * 选择器行的统一构造（行样式照星露谷控制台页 {@code StardewPlantingPage:69-85}）：
     * 名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * <p>状态文字用 {@link SettingText}，列宽走六行共用的固定列（各自按当前文案量宽会把「点击选择」
     * 顶得左右浮动）；↻ 的图标与动作都与星露谷一致——清空本行已选，悬停说明按行标签给出，
     * 空态（{@code empty}）时按钮为禁用态（第 214 条）。</p>
     *
     * @param empty 空态判据；与 ↻ 的清空动作读同一份数据（单值目标为 blank 判定，名单为 isEmpty）
     */
    private CompactElement selectorRow(String title, String description, Supplier<String> status,
                                       Runnable open, Runnable reset, Supplier<Boolean> empty) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            new Ctl(new SettingText(status, () -> stateColumn.widthOf(status)).alignLeft()),
            // 空态禁用：判据与 MiningTargetControls 的对应 clear 读同一份数据，
            // 逐帧求值见 IconButton#disabledWhen(Supplier)
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset).disabledWhen(empty),
                "清空本行已选" + title)));
    }

    /** 单值目标的空态判据：清空动作在值缺失 / 空白时静默返回（见 {@code MiningTargetControls} 的三个 clear） */
    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    // ── 选择器候选 / 登记 ID 互转 / 显示名：统一在 MiningRegistry（本类不再自持缓存） ──

    /* 供控制台状态条与概览页复用（它们只依赖本页这两个入口，故保留一行的转发） */
    public static String itemDisplayName(String itemId) { return MiningRegistry.itemDisplayName(itemId); }
    public static String blockDisplayName(String blockId) { return MiningRegistry.blockDisplayName(blockId); }
}
