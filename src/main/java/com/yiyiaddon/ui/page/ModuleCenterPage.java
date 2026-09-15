package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CategoryCard;
import com.yiyiaddon.ui.component.ModuleCard;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 模块中心：一列到底的全部模块清单，不再按分类分层。
 *
 * <p>分类只是模块的归属字段，不再是导航层级：全部模块按「分类权重 → 模块权重」排成一条纵向列表
 * （排序来自 {@link ModuleRegistry#all()}），点哪一行就进哪个模块的设置页，少一层点击。</p>
 *
 * <p>自带设置页的分类（{@code category.page() != null}，例如 Baritone设置）本身不是模块清单，
 * 但又必须可达，因此排在同一列的最前面，文案为「点击进入」。</p>
 *
 * <p>行高由 {@link CategoryCard#HEIGHT} 统一给出：卡片网格的几何计算（命中、滚动、悬停动画）
 * 全部复用 {@link CardPage}，本页只负责「第 index 行画什么」。</p>
 */
public final class ModuleCenterPage extends CardPage {

    private final PageRouter router;
    private final Consumer<ModuleEntry> moduleOpener;
    /** 自带设置页的分类入口，排在模块之前。 */
    private final List<ModuleCategory> pageEntries;
    /** 全部功能模块。 */
    private final List<ModuleEntry> modules;

    public ModuleCenterPage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        super(pageEntries().size() + ModuleRegistry.count());
        this.router = router;
        this.moduleOpener = moduleOpener;
        this.pageEntries = pageEntries();
        this.modules = ModuleRegistry.all();
    }

    /** 自带设置页的分类；它们是入口而不是模块清单。归到「设置」导航的那些不算在内。 */
    private static List<ModuleCategory> pageEntries() {
        List<ModuleCategory> entries = new ArrayList<>();
        for (ModuleCategory category : CategoryRegistry.all()) {
            if (category.page() != null && !category.settingsEntry()) entries.add(category);
        }
        return List.copyOf(entries);
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
        // 单列纵向清单：模块名与说明都能整行铺开，不再被网格列宽截断
        return 1;
    }

    @Override
    protected float cardHeight() {
        return CategoryCard.HEIGHT;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        if (index < pageEntries.size()) {
            CategoryCard.draw(canvas, pageEntries.get(index), UiText.t("点击进入", "Open"), x, y, w, alpha, hover, tc);
            return;
        }
        ModuleCard.draw(canvas, modules.get(index - pageEntries.size()), x, y, w, alpha, hover, tc);
    }

    @Override
    protected void onCardActivated(int index) {
        if (index < pageEntries.size()) {
            // 自带页面的分类（例如 Baritone设置）直接进它自己的页面，不走模块列表
            ModuleCategory category = pageEntries.get(index);
            router.open(category.page().get(), UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id()));
            return;
        }
        moduleOpener.accept(modules.get(index - pageEntries.size()));
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("还没有注册任何功能模块", "No modules registered yet");
    }
}
