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
 * 其下是该分类的模块行（{@link ModuleRow} 高 36，缩进一格表示归属），一屏能看到十行以上，
 * 滚动距离大幅缩短。</p>
 *
 * <p>分类头与模块行<b>同高</b>：卡片网格的命中、滚动与悬停几何只支持一套高度
 * （{@link CardLayout}），两行不同高会让鼠标命中与画面错位。</p>
 *
 * <p>自带设置页的分类（{@code category.page() != null}，例如 Baritone设置）仍排在自己分类的
 * 第一行，右侧写「点击进入」，点它直接进该分类自己的页面。</p>
 *
 * <p><b>分类头外观</b>：不铺底色、图标不带底框的「章节标签」形态，靠左侧强调条与悬停反馈辨识；
 * 模块行才是卡片。详见 {@link #drawGroupHeader}。</p>
 *
 * <p>展开状态存在 {@link #EXPANDED} 静态集合里（<b>默认全部收起</b>）：本页每次导航都会重建对象，
 * 状态不能随对象丢。</p>
 */
public final class ModuleCenterPage extends CardPage {

    /** 行高：分类头与模块行共用。 */
    private static final float ROW_HEIGHT = 36f;

    private static final float HEADER_PAD_X = 10f;
    private static final float HEADER_ICON_BOX = 22f;
    private static final float HEADER_ICON_GLYPH = 15f;
    private static final float HEADER_GAP = 8f;
    private static final float HEADER_TITLE_SIZE = 12.5f;
    private static final float HEADER_COUNT_SIZE = 10f;
    private static final float HEADER_ARROW_GLYPH = 14f;
    private static final float HEADER_ARROW_INSET = 15f;

    /** 分类头左侧的强调条：左内缩、宽、上下内缩（章节的起头标记）。 */
    private static final float HEADER_BAR_INSET = 4f;
    private static final float HEADER_BAR_WIDTH = 3f;
    private static final float HEADER_BAR_MARGIN = 9f;

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
        setCardCount(rows.size());
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
        // 单列清单：模块名与描述都能整行铺开
        return 1;
    }

    @Override
    protected float cardHeight() {
        return ROW_HEIGHT;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        Row row = rows.get(index);
        if (row.module() != null) {
            ModuleRow.draw(canvas, row.module(), x, y, w, alpha, hover, tc);
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
