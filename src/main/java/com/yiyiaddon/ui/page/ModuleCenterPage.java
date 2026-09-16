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
 * <p>折叠状态存在 {@link #COLLAPSED} 静态集合里：本页每次导航都会重建对象，状态不能随对象丢。</p>
 */
public final class ModuleCenterPage extends CardPage {

    /** 行高：分类头与模块行共用。 */
    private static final float ROW_HEIGHT = 36f;

    private static final float HEADER_PAD_X = 10f;
    private static final float HEADER_ICON_BOX = 22f;
    private static final float HEADER_ICON_RADIUS = 6f;
    private static final float HEADER_ICON_GLYPH = 15f;
    private static final float HEADER_GAP = 8f;
    private static final float HEADER_TITLE_SIZE = 12.5f;
    private static final float HEADER_COUNT_SIZE = 10f;
    private static final float HEADER_ARROW_GLYPH = 14f;
    private static final float HEADER_ARROW_INSET = 15f;

    /** 分类头的展开 / 收起箭头（Material 符号，均已验真存在于 MaterialSymbolsRounded.ttf）。 */
    private static final String ARROW_EXPANDED = "\uE5CF";
    private static final String ARROW_COLLAPSED = "\uE5CC";

    /** 已收起的分类 id；静态保存，本页重建后仍保持。 */
    private static final Set<String> COLLAPSED = new HashSet<>();

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
            if (COLLAPSED.contains(category.id())) continue;
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
        if (!COLLAPSED.remove(id)) COLLAPSED.add(id);
        rebuildRows();
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("还没有注册任何功能模块", "No modules registered yet");
    }

    // ── 分类头 ──

    private void drawGroupHeader(Canvas canvas, ModuleCategory category, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        GlassPanel.frost(canvas, x, y, w, ROW_HEIGHT, radius,
                GlassPanel.mix(tc.module, tc.surfaceHover, hover * 0.7f), 0.62f, alpha);
        GlassPanel.rim(canvas, x, y, w, ROW_HEIGHT, radius, tc.rim, alpha, 0.08f + 0.10f * hover);

        float centerY = y + ROW_HEIGHT / 2f;
        float cursor = x + HEADER_PAD_X;
        float iconY = centerY - HEADER_ICON_BOX / 2f;
        GlassPanel.fill(canvas, cursor, iconY, HEADER_ICON_BOX, HEADER_ICON_BOX, HEADER_ICON_RADIUS,
                tc.accent, alpha * 0.18f);
        CardIcons.drawCentered(canvas, category.icon(), cursor + HEADER_ICON_BOX / 2f, centerY,
                HEADER_ICON_GLYPH, GlassPanel.withAlpha(tc.accent, alpha));
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
                GlassPanel.withAlpha(tc.primaryText, alpha));

        boolean collapsed = COLLAPSED.contains(category.id());
        CardIcons.drawCentered(canvas, collapsed ? ARROW_COLLAPSED : ARROW_EXPANDED, arrowX, centerY,
                HEADER_ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));
    }
}
