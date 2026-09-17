package com.yiyiaddon.ui.page;

import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeyValueRow;
import com.yiyiaddon.ui.widget.SettingText;

import java.util.List;

/**
 * 模块独立页面占位实现。
 *
 * <p>模块尚未接入自己的 {@link ModulePage} 时使用本页，视觉与真实模块页保持同一套紧凑两段布局：
 * 中间注册信息、底部接入提示。模块名与说明由所在屏幕的头部给出，此处不重复；
 * 状态文字 / 快捷键徽章 / 模块开关三件已统一搬进各模块自己的控制台顶栏
 * （用户 2026-09-17 口径），<b>模块页不再有顶部状态块</b>。</p>
 */
public final class ModuleDetailPage extends CompactModulePage {

    private static final float VALUE_WIDTH = 220f;

    private final ModuleEntry entry;

    public ModuleDetailPage(ModuleEntry entry) {
        this.entry = entry;
        ModuleCategory category = CategoryRegistry.byId(entry.categoryId());

        addCore(new CompactRow("模块 ID", null, new SettingText(entry::id, VALUE_WIDTH)));
        addCore(new CompactRow("英文名", null,
                new SettingText(() -> blankTo(entry.name(), "未标注"), VALUE_WIDTH)));
        addCore(new CompactRow("所属分类", null,
                new SettingText(() -> category == null ? "未分类" : category.displayName(), VALUE_WIDTH)));
        addCore(new CompactRow("版本", null,
                new SettingText(() -> blankTo(entry.version(), "未标注"), VALUE_WIDTH)));
        addCore(new CompactRow("模块页面", null,
                new SettingText(() -> entry.page() == null ? "未接入" : "已接入", VALUE_WIDTH)));

        addFooter(new KeyValueRow("独立页面", List.of(KeyValueRow.Value.of(
                () -> UiText.t("该模块尚未接入独立页面，接入后点击模块卡片将直接打开它自己的页面。",
                        "This module has no dedicated page yet.")))));
    }

    @Override
    public String getTitle() {
        return entry.displayName();
    }

    @Override
    public String getSubtitle() {
        return entry.description();
    }

    private static String blankTo(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
