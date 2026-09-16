package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.widget.SettingModule;

import java.util.Collection;

/**
 * 模块中心搜索结果页：展示与搜索关键词匹配的模块清单。
 *
 * 只复用既有模块列表渲染，不承载任何业务配置。
 */
public final class SearchResultsPage extends BasePage {
    private final String query;

    public SearchResultsPage(String query, Collection<SettingModule> results) {
        this.query = query;
        modules.addAll(results);
    }

    @Override
    public String getTitle() {
        return UiText.t("搜索结果", "Search Results");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("与“", "Matches for \"") + query + UiText.t("”匹配的功能", "\"");
    }
}
