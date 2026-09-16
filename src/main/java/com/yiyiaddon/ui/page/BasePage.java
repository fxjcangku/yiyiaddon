package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.widget.SettingModule;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class BasePage {

    /** 模块之间的垂直间距（像素）。滚动上限与绘制都按此值计算。 */
    public static final float MODULE_GAP = 8f;

    /**
     * 卡片区顶部留白：页面副标题与第一张卡片之间。
     *
     * <p>取卡片页的同一份值：不留给白时首卡紧贴内容裁剪线，和其它页面的首卡差出一截
     * （实机反馈「有的地方贴住、有的地方空一块」）。</p>
     */
    private static final float TOP_INSET = CardLayout.TOP_INSET;

    protected final List<SettingModule> modules = new ArrayList<>();
    private final List<SettingModule> visibleModules = new ArrayList<>();
    private final List<Float> visibleModuleHeights = new ArrayList<>();
    private float cachedTotalHeight = -1f;
    private String searchQuery = "";

    public abstract String getTitle();
    public abstract String getSubtitle();

    /**
     * 模块之间的垂直间距；默认 {@link #MODULE_GAP}。
     *
     * <p>「图标行」那一类清单页（{@code SettingsPage} / {@code InterfacePage}）覆写成模块中心的行距
     * （{@link com.yiyiaddon.ui.component.ModuleRow#ROW_GAP}）：行高收到 24 后仍按 8 排行还是散，
     * 四个页面的行节奏必须一致（用户 2026-09-16 要求这两页「也一样」）。绘制、命中、拖动与总高度
     * 必须读同一个值，否则命中框会与画面错位。</p>
     */
    protected float moduleGap() {
        return MODULE_GAP;
    }

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
        return TOP_INSET + cachedTotalHeight;
    }

    /** 可见模块之间的间距总量，供内容区计算滚动上限。 */
    public float getVisibleSpacing() {
        ensureLayoutCache();
        return visibleModules.size() * moduleGap();
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
        float cy = y + TOP_INSET - scrollOffset;
        float viewportTop = y;
        float viewportBottom = y + contentH;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (cy + mh > viewportTop && cy < viewportBottom) {
                m.draw(canvas, x, cy, contentW, alpha, viewportTop, viewportBottom, mouseX, mouseY);
            }
            cy += mh + moduleGap();
        }
    }

    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset, int button) {
        ensureLayoutCache();
        float cy = contentY + TOP_INSET - scrollOffset;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (my >= cy && my <= cy + mh) {
                return m.onClick(mx, my, contentX, cy, contentW, button);
            }
            cy += mh + moduleGap();
        }
        return false;
    }

    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        ensureLayoutCache();
        float cy = contentY + TOP_INSET - scrollOffset;
        for (int i = 0; i < visibleModules.size(); i++) {
            SettingModule m = visibleModules.get(i);
            float mh = visibleModuleHeights.get(i);
            if (m.onDrag(mx, my, contentX, cy, contentW)) return true;
            cy += mh + moduleGap();
        }
        return false;
    }

    public void releaseDrag() {
        ensureLayoutCache();
        for (SettingModule m : visibleModules) m.releaseDrag();
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
