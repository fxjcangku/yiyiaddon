package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardIcons;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ModuleRow;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 模块中心：<b>按分类分组的模块清单</b>，每个分类头可点击收起 / 展开，整页纵向滚动。
 *
 * <p><b>为什么改成清单</b>：原先是一列到底的大卡（高 74、一张卡三行内容），模块一多就要长距离滚动、
 * 也看不出归属。现在按分类分组：分类头一行（图标 + 分类名 + 模块数 + 展开箭头），
 * 其下是该分类的模块行（{@link ModuleRow} 高 24，缩进一格表示归属）。</p>
 *
 * <p><b>展开后为什么不按「一行一个」排</b>：一个分类常常只有两三个模块，一行一个就是两三行，
 * 纵向白白吃掉滚动距离。用户 2026-09-16 要求「模块展开之后 下面的模块能不能变成一排一排的 而
 * 不是三排」，于是展开后的模块改成一排多格的网格：列数按可用宽度算（见 {@link #gridColumns}），
 * 分类头仍独占一整行、状态与尺寸一个不动（用户已认可，不动它）。</p>
 *
 * <p>分类头与模块行<b>同高</b>：网格的命中、滚动与悬停几何只认一套高度，两行不同高会让鼠标命中
 * 与画面错位。因此每次收窄都是收这一套共用行高：用户 2026-09-16 先说「展开的分组之后下面的模块
 * 做小一点」（36 → 28），看过后又说「不用 现在很满意 整体都缩小一下 方便阅读」，于是 28 → 24
 * 再统一收一档——本轮分类头的外观参数（图标框、字形、字号、强调条、左右内边距）也跟着按同一比例
 * 收了一档，但颜色与左对齐口径不变，命中判定仍与画面严格同源。</p>
 *
 * <p><b>几何怎么算</b>：本页的行宽逐行不同（整行的分类头 / 半宽的网格单元），所以不再走
 * {@link CardLayout} 的整页等宽网格，而是覆写 {@link CardPage} 的逐张几何钩子，由 {@link #ensureLayout}
 * 按「可用宽度」算出每个下标所在网格行与列，绘制、悬停、点击、滚动总高度全部读同一份结果。</p>
 *
 * <p>自带设置页的分类（{@code category.page() != null}，例如 Baritone设置）仍排在自己分类的
 * 第一行，右侧写「点击进入」，点它直接进该分类自己的页面；它是带描述的入口行，仍独占一整行。</p>
 *
 * <p><b>分类头外观</b>：不铺底色、图标不带底框的「章节标签」形态，靠左侧强调条与悬停反馈辨识；
 * 模块行才是卡片。详见 {@link #drawGroupHeader}。</p>
 *
 * <p>展开状态存在 {@link #EXPANDED} 静态集合里（<b>默认全部收起</b>）：本页每次导航都会重建对象，
 * 状态不能随对象丢。</p>
 */
public final class ModuleCenterPage extends CardPage {

    /** 行高：分类头与模块行共用，直接取自 {@link ModuleRow#HEIGHT}（两处各写一个字面量早晚跑偏）。 */
    private static final float ROW_HEIGHT = ModuleRow.HEIGHT;

    /**
     * 行间距：比全局 {@link CardLayout#GAP_Y}（14）更紧。
     *
     * <p>用户 2026-09-16 要求模块行「做小一点」，随后又说「整体都缩小一下 方便阅读」。行高收窄后
     * 若仍按 14 排行，整组会松散、节奏断裂；14 是留给带投影的卡片网格的（投影会向外糊开），
     * 而模块行只有 {@code frost + rim}、没有投影，6 就够分开了。分类头与模块行共用这一份间距，
     * 展开后整组节奏统一。</p>
     *
     * <p>值取自 {@link ModuleRow#ROW_GAP}：同一天「设置 / 界面」两页也改成这种紧凑行，
     * 四个页面的行节奏必须是同一个数（各写一份早晚跑偏）。</p>
     */
    private static final float ROW_GAP = ModuleRow.ROW_GAP;

    /**
     * 网格单元的最小宽度：可用宽度至少放得下这么宽，才多加一列。
     *
     * <p>150 是「一格真的能读」的下限：单元里要放下图标（16 + 两侧内边距与间距共 22）＋模块名
     * （12 号，最长五个汉字约 60）＋状态标记（约 45）。取值还要保证默认面板下「自动化」的三个模块
     * 排成一排——面板 740、页面可用宽 501，减掉缩进 14 与两个间距 6 之后每格 158.3 &gt; 150，
     * 正好三列（{@link #gridColumns}）；再往上取就会掉成两列，与用户要的「一排」不符。</p>
     */
    private static final float MIN_CELL_W = 150f;

    /**
     * 兜底页面宽度：第一帧刷新滚动上限时还没绘制过，页面还不知道自己的可用宽度。
     *
     * <p>取面板基准尺寸算出来的实际页宽（740 面板 − 190 导航 − 1 分隔 − 14 页面左缩进 − 34
     * 滚动条预留 = 501），于是兜底值与真实值一致，滚动范围从第一帧起就是对的；
     * 窗口尺寸变了也只在下一帧生效一次，随后立刻按真值重算。</p>
     */
    private static final float FALLBACK_CONTENT_W = 501f;

    /**
     * 分类头度量：与模块行同一比例收一档（用户「整体都缩小一下 方便阅读」），字号仍压在模块名之下。
     *
     * <p>标题字号读 {@link ModuleRow#HEADER_TITLE_SIZE}：同一天模块页里的分组标题也要和分类头同一量级
     * （用户「还有点击进去的时候 模块也要缩小 现在都不对称」），两处各写一个数早晚跑偏。</p>
     */
    private static final float HEADER_PAD_X = 9f;
    private static final float HEADER_ICON_BOX = 20.5f;
    private static final float HEADER_ICON_GLYPH = 13.5f;
    private static final float HEADER_GAP = 8f;
    private static final float HEADER_TITLE_SIZE = ModuleRow.HEADER_TITLE_SIZE;
    private static final float HEADER_COUNT_SIZE = 9.5f;
    private static final float HEADER_ARROW_GLYPH = 13f;
    private static final float HEADER_ARROW_INSET = 14f;

    /**
     * 分类头左侧的强调条：左内缩、宽、上下内缩（章节的起头标记）。
     *
     * <p>随行高 28 → 24 按同比例收（宽与内缩保持原比例），上下内缩同步收一点让强调条不会被
     * 矮行挤成一条顶天立地的竖杠。</p>
     */
    private static final float HEADER_BAR_INSET = 3.5f;
    private static final float HEADER_BAR_WIDTH = 2.5f;
    private static final float HEADER_BAR_MARGIN = 8f;

    /** 标题往强调色偏的比重：让分类名读起来是「章节标签」，不是又一个可点的模块名。 */
    private static final float HEADER_TITLE_ACCENT = 0.30f;

    /** 分类头的展开 / 收起箭头（Material 符号，均已验真存在于 MaterialSymbolsRounded.ttf）。 */
    private static final String ARROW_EXPANDED = "\uE5CF";
    private static final String ARROW_COLLAPSED = "\uE5CC";

    /**
     * 已展开的分类 id；静态保存，本页重建后仍保持。
     *
     * <p><b>默认全部收起</b>（用户 2026-09-16 要求「模块中心的展开 默认能不能关掉」）：
     * 存「已展开」而不是「已收起」，空集合即「全部收起」，不需要在初始化时预填任何东西。</p>
     */
    private static final Set<String> EXPANDED = new HashSet<>();

    /** 清单里的一行：分类头（{@code module == null && !pageEntry}）、页面入口（{@code pageEntry}）或模块行。 */
    private record Row(ModuleCategory category, ModuleEntry module, boolean pageEntry) {
    }

    private final PageRouter router;
    private final Consumer<ModuleEntry> moduleOpener;
    private final List<Row> rows = new ArrayList<>();

    /**
     * 逐行的网格排布（与 {@link #rows} 同长、同下标）：所在网格行、行内列号、该行的列数。
     *
     * <p>{@code cols == 0} 表示「独占一整行」（分类头、页面入口、无分类的兜底模块），此时列号无意义；
     * {@code cols >= 1} 表示模块网格里的一格。三个数组由 {@link #ensureLayout} 与 {@link #rows}
     * 同步重建（展开 / 收起时长度会变，因此重建而不是复用）。</p>
     */
    private int[] slotOf = new int[0];
    private int[] colOf = new int[0];
    private int[] colsOf = new int[0];

    /** 网格总行数：滚动总高度与它一一对应。 */
    private int gridRows;

    /** 上面三个数组是按哪个可用宽度算出来的；窗口宽度不变就不重算。 */
    private float layoutWidth = -1f;

    public ModuleCenterPage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        super(0);
        this.router = router;
        this.moduleOpener = moduleOpener;
        rebuildRows();
    }

    /**
     * 按分类装配清单：分类头 + （未收起时）页面入口行 + 各模块行。
     *
     * <p>末尾兜底一次：分类注册表里查不到的模块也追加进清单，避免模块在界面上凭空消失。</p>
     */
    private void rebuildRows() {
        rows.clear();
        List<ModuleEntry> remaining = new ArrayList<>(ModuleRegistry.all());
        for (ModuleCategory category : CategoryRegistry.all()) {
            // 归到「设置」导航的分类不在模块中心出现（沿用原口径）
            if (category.settingsEntry()) continue;
            List<ModuleEntry> entries = new ArrayList<>(ModuleRegistry.byCategory(category.id()));
            entries.retainAll(remaining);
            boolean pageEntry = category.page() != null;
            if (entries.isEmpty() && !pageEntry) continue;

            rows.add(new Row(category, null, false));
            remaining.removeAll(entries);
            if (!EXPANDED.contains(category.id())) continue;
            if (pageEntry) rows.add(new Row(category, null, true));
            for (ModuleEntry entry : entries) rows.add(new Row(category, entry, false));
        }
        for (ModuleEntry entry : remaining) rows.add(new Row(null, entry, false));
        resetLayout();
        setCardCount(rows.size());
    }

    /** 清单变了：作废上一次的网格排布（长度也变了，直接重建三个数组），下一次几何调用按新宽度重算。 */
    private void resetLayout() {
        slotOf = new int[rows.size()];
        colOf = new int[rows.size()];
        colsOf = new int[rows.size()];
        gridRows = 0;
        layoutWidth = -1f;
    }

    @Override
    public String getTitle() {
        return UiText.t("模块中心", "Modules");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("全部功能模块，点击进入各自的设置", "All modules — click one to open its settings");
    }

    @Override
    protected int columns() {
        // 兜底值：本页的列数逐行不同（分类头独占一行、模块按网格并排），实际几何由下面那组钩子给出
        return 1;
    }

    @Override
    protected float cardHeight() {
        return ROW_HEIGHT;
    }

    /**
     * 行间距：见 {@link #ROW_GAP}。必须由绘制、命中、悬停与总高度共用这一个值
     * （{@link CardPage} 已经统一走它），否则行高变了以后命中框会与画面错位。
     */
    @Override
    protected float rowGap() {
        return ROW_GAP;
    }

    // ── 网格几何（绘制 / 悬停 / 点击 / 总高度共用） ──

    /**
     * 按可用宽度重算逐行的网格排布；宽度没变就直接返回。
     *
     * <p><b>为什么按宽度缓存</b>：同一帧里绘制、悬停与命中会问很多次几何，逐次重排毫无意义；
     * 而窗口宽度变了（面板是固定设计尺寸，正常不会变）必须立刻按新宽度重排，否则列数一改、
     * 每一格的位置全变，上一步算出来的命中框就全错了。</p>
     *
     * <p>分块规则：连续的「同分类模块行」组成一个网格块，块内按 {@link #gridColumns} 的列数从左到右
     * 铺开、铺满一行换下一行；分类头、页面入口行（带「点击进入」）与无分类的兜底模块各占一整行，
     * 与改动前完全一致。每个块占的网格行数 = ⌈块内模块数 ÷ 列数⌉。</p>
     */
    private void ensureLayout(float contentW) {
        float width = contentW > 0f ? contentW : FALLBACK_CONTENT_W;
        if (Math.abs(width - layoutWidth) < 0.01f) return;
        layoutWidth = width;

        int count = rows.size();
        int columns = gridColumns(width);
        int slot = 0;
        int i = 0;
        while (i < count) {
            if (!isGridModule(rows.get(i))) {
                slotOf[i] = slot;
                colOf[i] = 0;
                colsOf[i] = 0;
                slot++;
                i++;
                continue;
            }
            int end = i + 1;
            while (end < count && sameCategory(rows.get(i), rows.get(end))) end++;
            int size = end - i;
            for (int k = 0; k < size; k++) {
                slotOf[i + k] = slot + k / columns;
                colOf[i + k] = k % columns;
                colsOf[i + k] = columns;
            }
            slot += (size + columns - 1) / columns;
            i = end;
        }
        gridRows = slot;
    }

    /** 该行是否是模块网格里的一格：有分类的普通模块行才算（分类头、页面入口行、兜底模块都不算）。 */
    private static boolean isGridModule(Row row) {
        return row.module() != null && !row.pageEntry() && row.category() != null;
    }

    /** 下一个模块行是否与块首属于同一分类（同分类才继续并排，跨分类就另起一块）。 */
    private static boolean sameCategory(Row start, Row next) {
        return next.module() != null && !next.pageEntry() && next.category() != null
                && start.category().id().equals(next.category().id());
    }

    /**
     * 模块网格的列数：按「最小格宽 + 固定间距」取最大的整数列数。
     *
     * <p>整数运算（先加一个间距再整除），不做浮点累加：浮点累加出来的列数在宽度临界点上会抖动，
     * 同一宽度可能这一帧三列、下一帧两列，界面就会闪。</p>
     *
     * <p>默认面板（页面可用宽 501）下，减掉缩进 14 得 487，{@code (487 + 6) / (150 + 6) = 3}，
     * 即「自动化」的三个模块正好排成一排；窗口更宽则更多列，更窄自动降列，最少一列。</p>
     */
    private static int gridColumns(float contentW) {
        float gridW = Math.max(0f, contentW - ModuleRow.INDENT);
        return Math.max(1, (int) Math.floor((gridW + ROW_GAP) / (MIN_CELL_W + ROW_GAP)));
    }

    /** 网格单元宽度：先扣掉单元之间的横向间距（与 {@link #ROW_GAP} 同值）再均分，同一行每格等宽。 */
    private static float cellWidth(float contentW, int columns) {
        float gridW = Math.max(0f, contentW - ModuleRow.INDENT);
        return Math.max(0f, (gridW - ROW_GAP * (columns - 1)) / columns);
    }

    /** 第 index 行的宽度：独占一行的＝可用宽度，网格单元＝格宽。 */
    private float widthOf(float contentW, int index) {
        int columns = colsOf[index];
        return columns <= 0 ? contentW : cellWidth(contentW, columns);
    }

    /**
     * 第 index 行的左边界。
     *
     * <p>网格整体按 {@link ModuleRow#INDENT} 缩进一格表示「属于上面的分类头」；单元内部不再缩进
     * （缩进由这一层统一给，单元里的图标与文字才排得开）。</p>
     */
    private float leftOf(float originX, float contentW, int index) {
        int columns = colsOf[index];
        if (columns <= 0) return originX;
        return originX + ModuleRow.INDENT + colOf[index] * (cellWidth(contentW, columns) + ROW_GAP);
    }

    /** 第 index 行的顶边界：锚在它所在的网格行上。 */
    private float topOf(float originY, int index) {
        return originY + slotOf[index] * (ROW_HEIGHT + ROW_GAP);
    }

    @Override
    protected float cardWidth(float contentW, int index) {
        ensureLayout(contentW);
        return widthOf(contentW, index);
    }

    @Override
    protected float cardX(float originX, float contentW, int index) {
        ensureLayout(contentW);
        return leftOf(originX, contentW, index);
    }

    @Override
    protected float cardY(float originY, float contentW, int index) {
        ensureLayout(contentW);
        return topOf(originY, index);
    }

    /** 滚动总高度：网格行数 ×（行高 + 行距）再扣掉末尾多算的一个行距。 */
    @Override
    protected float contentHeight(float contentW) {
        ensureLayout(contentW);
        return gridRows <= 0 ? 0f : gridRows * ROW_HEIGHT + (gridRows - 1) * ROW_GAP;
    }

    @Override
    protected int indexAt(float mx, float my, float originX, float originY, float contentW) {
        ensureLayout(contentW);
        for (int i = 0; i < rows.size(); i++) {
            float left = leftOf(originX, contentW, i);
            if (mx < left || mx > left + widthOf(contentW, i)) continue;
            float top = topOf(originY, i);
            if (my >= top && my <= top + ROW_HEIGHT) return i;
        }
        return -1;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        Row row = rows.get(index);
        if (row.module() != null) {
            // 同分类的模块行在网格里并排（见 ensureLayout），格子里的排版见 ModuleRow.drawCell
            if (colsOf[index] > 0) {
                ModuleRow.drawCell(canvas, row.module(), x, y, w, frameMouseX(), frameMouseY(), alpha, hover, tc);
            } else {
                // 无分类的兜底模块没有并排的对象，仍按整行画（区别于网格单元的「图标 + 名称 + 状态」）
                ModuleRow.draw(canvas, row.module(), x, y, w, alpha, hover, tc);
            }
            return;
        }
        if (row.pageEntry()) {
            ModuleCategory category = row.category();
            ModuleRow.drawEntry(canvas, category.icon(), category.displayName(), category.description(),
                    UiText.t("点击进入", "Open"), tc.labelTertiary, x, y, w, alpha, hover, tc);
            return;
        }
        drawGroupHeader(canvas, row.category(), x, y, w, alpha, hover, tc);
    }

    @Override
    protected void onCardActivated(int index) {
        Row row = rows.get(index);
        if (row.module() != null) {
            moduleOpener.accept(row.module());
            return;
        }
        if (row.pageEntry()) {
            ModuleCategory category = row.category();
            router.open(category.page().get(), UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id()));
            return;
        }
        // 分类头：收起 / 展开
        String id = row.category().id();
        if (!EXPANDED.remove(id)) EXPANDED.add(id);
        rebuildRows();
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("还没有注册任何功能模块", "No modules registered yet");
    }

    // ── 分类头 ──

    /**
     * 分类头：<b>「章节标签」形态</b>——不铺底色、图标不带底框，只有左侧强调条 + 悬停时的一层淡反馈。
     *
     * <p><b>为什么不铺底色</b>：模块行是卡片（{@code tc.module} 素底 + 描边），分类头若也用卡片底，
     * 五个分类一展开就是一排大色块，跟模块行抢视线、整页糊成一片（用户 2026-09-16：「也太丑了吧」
     * 「展开跟合并的样式太像了 没有鲜明的对比 然后看花眼」）。改成「静章节 + 亮卡片」两级对比后，
     * 分类一眼可辨，视觉重心落在真正可点的模块行上。</p>
     *
     * <p><b>图标为什么不带底框</b>：模块行的图标是「底框 + 字形」，分类头不给底框、字号略小、
     * 颜色偏弱，两者就不会长成同一个东西。文字起始位置仍按 {@link #HEADER_ICON_BOX} 推进，
     * 保证分类名与模块名左对齐。</p>
     */
    private void drawGroupHeader(Canvas canvas, ModuleCategory category, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        // 只有悬停时给一层很淡的反馈，标明「这一行可点」
        if (hover > 0.01f) {
            GlassPanel.frost(canvas, x, y, w, ROW_HEIGHT, radius, tc.surfaceHover, 0.30f * hover, alpha);
        }
        // 左侧强调条：章节的起头标记，悬停时更亮
        GlassPanel.fill(canvas, x + HEADER_BAR_INSET, y + HEADER_BAR_MARGIN,
                HEADER_BAR_WIDTH, ROW_HEIGHT - HEADER_BAR_MARGIN * 2f,
                HEADER_BAR_WIDTH / 2f, tc.accent, alpha * (0.55f + 0.45f * hover));

        float centerY = y + ROW_HEIGHT / 2f;
        float cursor = x + HEADER_PAD_X;
        CardIcons.drawCentered(canvas, category.icon(), cursor + HEADER_ICON_BOX / 2f, centerY,
                HEADER_ICON_GLYPH, GlassPanel.withAlpha(GlassPanel.mix(tc.accent, tc.primaryText, 0.25f), alpha));
        cursor += HEADER_ICON_BOX + HEADER_GAP;

        int moduleCount = ModuleRegistry.byCategory(category.id()).size();
        String countText = moduleCount == 0
                ? ""
                : UiText.t(moduleCount + " 个模块", moduleCount + " modules");
        float countWidth = countText.isEmpty() ? 0f : FontRenderer.measureTextWidth(countText, HEADER_COUNT_SIZE);
        float arrowX = x + w - HEADER_ARROW_INSET;
        if (!countText.isEmpty()) {
            FontRenderer.drawText(canvas, countText,
                    arrowX - HEADER_ARROW_GLYPH - HEADER_GAP - countWidth,
                    CardLayout.baseline(centerY, HEADER_COUNT_SIZE), HEADER_COUNT_SIZE,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }

        float titleRight = arrowX - HEADER_ARROW_GLYPH - HEADER_GAP
                - (countText.isEmpty() ? 0f : countWidth + HEADER_GAP);
        float titleMax = Math.max(0f, titleRight - cursor);
        FontRenderer.drawTextBold(canvas,
                CardLayout.ellipsize(category.displayName(), titleMax, HEADER_TITLE_SIZE), cursor,
                CardLayout.baseline(centerY, HEADER_TITLE_SIZE), HEADER_TITLE_SIZE,
                GlassPanel.withAlpha(GlassPanel.mix(tc.primaryText, tc.accent, HEADER_TITLE_ACCENT), alpha));

        // 箭头颜色随状态走：收起 = 强调色（招手让你点），展开 = 弱色（已经打开了）
        boolean expanded = EXPANDED.contains(category.id());
        CardIcons.drawCentered(canvas, expanded ? ARROW_EXPANDED : ARROW_COLLAPSED, arrowX, centerY,
                HEADER_ARROW_GLYPH,
                GlassPanel.withAlpha(expanded
                        ? GlassPanel.mix(tc.labelTertiary, tc.accent, hover)
                        : GlassPanel.mix(tc.accent, tc.primaryText, hover * 0.6f), alpha));
    }
}
