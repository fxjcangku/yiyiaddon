package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.widget.SettingModule;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class BasePage {

    /** 模块之间的垂直间距（像素）。滚动上限与绘制都按此值计算。 */
    public static final float MODULE_GAP = 8f;

    protected final List<SettingModule> modules = new ArrayList<>();
    private final List<SettingModule> visibleModules = new ArrayList<>();
    private final List<Float> visibleModuleHeights = new ArrayList<>();
    private float cachedTotalHeight = -1f;
    private String searchQuery = "";

    public abstract String getTitle();
    public abstract String getSubtitle();

    public List<SettingModule> getModules() {
        assignBindingIds();
        return modules;
    }

    public void setSearchQuery(String query) {
        String normalized = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        if (!searchQuery.equals(normalized)) {
            searchQuery = normalized;
            cachedTotalHeight = -1f;
        }
    }

    public float getTotalHeight() {
        ensureLayoutCache();
        return cachedTotalHeight;
    }

    /** 可见模块之间的间距总量，供内容区计算滚动上限。 */
    public float getVisibleSpacing() {
        ensureLayoutCache();
        return visibleModules.size() * MODULE_GAP;
    }

    public boolean hasSearchResults() {
        ensureLayoutCache();
        return !visibleModules.isEmpty();
    }

    public void update(float dt) {
        rebuildLayoutCache();
        for (SettingModule m : visibleModules) m.update(dt);
        rebuildLayoutCache();
    }

    public boolean hasAnimatingModules() {
        ensureLayoutCache();
        for (SettingModule m : visibleModules) {
            if (m.isAnimating()) return true;
        }
        return false;
    }

    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset, float mouseX, float mouseY) {
        ensureLayoutCache();
        float cy = y - scrollOffset;
        float viewportTop = y;
        float viewportBottom = y + contentH;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (cy + mh > viewportTop && cy < viewportBottom) {
                m.draw(canvas, x, cy, contentW, alpha, viewportTop, viewportBottom, mouseX, mouseY);
            }
            cy += mh + MODULE_GAP;
        }
    }

    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset, int button) {
        ensureLayoutCache();
        float cy = contentY - scrollOffset;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (my >= cy && my <= cy + mh) {
                return m.onClick(mx, my, contentX, cy, contentW, button);
            }
            cy += mh + MODULE_GAP;
        }
        return false;
    }

    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        ensureLayoutCache();
        float cy = contentY - scrollOffset;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (m.onDrag(mx, my, contentX, cy, contentW)) return true;
            cy += mh + MODULE_GAP;
        }
        return false;
    }

    /** 鼠标松开时释放按压动画；默认页面没有按压元素。 */
    public void releasePress() {
    }

    /** 页面被丢弃、不再绘制时直接复位按压动画，避免留下按下的残影。 */
    public void cancelPress() {
    }

    private void ensureLayoutCache() {
        if (cachedTotalHeight >= 0f) return;
        rebuildLayoutCache();
    }

    private void rebuildLayoutCache() {
        assignBindingIds();
        visibleModules.clear();
        visibleModuleHeights.clear();
        float totalHeight = 0f;
        for (SettingModule m : modules) {
            if (!m.isVisible() || !m.matchesSearch(searchQuery)) continue;
            float moduleHeight = m.getTotalHeight();
            visibleModules.add(m);
            visibleModuleHeights.add(moduleHeight);
            totalHeight += moduleHeight;
        }
        cachedTotalHeight = totalHeight;
    }

    private void assignBindingIds() {
        String pageId = getClass().getName();
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).setBindingId(pageId + "." + i);
        }
    }
}
