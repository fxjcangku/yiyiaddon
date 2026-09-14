package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.ModuleCard;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.Consumer;

/**
 * 分类下的模块列表。
 *
 * <p>点击模块卡片不展开设置，而是由 {@code moduleOpener} 打开该模块的独立屏幕；
 * 模块页面内容由 {@link ModulePage} 提供，未提供时落到 {@link ModuleDetailPage} 占位页。</p>
 */
public final class ModuleListPage extends CardPage {

    private final ModuleCategory category;
    private final List<ModuleEntry> entries;
    private final Consumer<ModuleEntry> moduleOpener;

    public ModuleListPage(ModuleCategory category, Consumer<ModuleEntry> moduleOpener) {
        super(ModuleRegistry.countIn(category.id()));
        this.category = category;
        this.moduleOpener = moduleOpener;
        this.entries = ModuleRegistry.byCategory(category.id());
    }

    @Override
    public String getTitle() {
        return category.displayName();
    }

    @Override
    public String getSubtitle() {
        return category.description();
    }

    @Override
    protected int columns() {
        return CardLayout.MODULE_COLUMNS;
    }

    @Override
    protected float cardHeight() {
        return ModuleCard.HEIGHT;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        ModuleCard.draw(canvas, entries.get(index), x, y, w, alpha, hover, tc);
    }

    @Override
    protected void onCardActivated(int index) {
        moduleOpener.accept(entries.get(index));
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("该分类下还没有模块", "No modules in this category yet");
    }
}
