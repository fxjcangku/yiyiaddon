package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CategoryCard;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.Consumer;

/**
 * 模块中心：展示全部功能分类。
 *
 * <p>分类清单只来自 {@link CategoryRegistry}，页面内不存在任何分类硬编码；
 * 新增分类只需在启动注册处登记一条数据。</p>
 *
 * <p>分类下的模块列表仍在同一个面板内切换；点击模块卡片时由 {@code moduleOpener}
 * 打开模块自己的独立屏幕。</p>
 */
public final class ModuleCenterPage extends CardPage {

    private final PageRouter router;
    private final Consumer<ModuleEntry> moduleOpener;
    private final List<ModuleCategory> categories;

    public ModuleCenterPage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        super(CategoryRegistry.count());
        this.router = router;
        this.moduleOpener = moduleOpener;
        this.categories = CategoryRegistry.all();
    }

    @Override
    public String getTitle() {
        return UiText.t("模块中心", "Modules");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("管理和配置客户端功能模块", "Browse and configure client modules");
    }

    @Override
    protected int columns() {
        return CardLayout.CATEGORY_COLUMNS;
    }

    @Override
    protected float cardHeight() {
        return CategoryCard.HEIGHT;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        ModuleCategory category = categories.get(index);
        // 自带页面的分类本身就是入口（不是模块清单），显示「点击进入」而不是「暂无模块」
        String countText = category.page() != null
                ? UiText.t("点击进入", "Open")
                : ModuleRegistry.countIn(category.id()) + " " + UiText.t("个模块", "modules");
        CategoryCard.draw(canvas, category, countText, x, y, w, alpha, hover, tc);
    }

    @Override
    protected void onCardActivated(int index) {
        ModuleCategory category = categories.get(index);
        // 自带页面的分类（例如 Baritone设置）直接进它自己的页面，不走模块列表。
        // 两级页面都登记可重建标识，面板重开时能回到这里而不是回首页。
        if (category.page() != null) {
            router.open(category.page().get(), UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id()));
            return;
        }
        router.open(new ModuleListPage(category, moduleOpener),
            UiNavigationMemory.token(UiNavigationMemory.TOKEN_LIST, category.id()));
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("还没有注册任何功能分类", "No categories registered yet");
    }
}
