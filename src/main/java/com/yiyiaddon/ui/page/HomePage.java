package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

/**
 * 首页。
 *
 * <p>当前只提供入口说明，具体概览内容待后续指令接入。</p>
 */
public final class HomePage extends BasePage {

    private static final float PANEL_HEIGHT = 96f;

    @Override
    public String getTitle() {
        return UiText.t("首页", "Home");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("yiyiaddon 客户端控制中心", "yiyiaddon client control centre");
    }

    @Override
    public float getTotalHeight() {
        return PANEL_HEIGHT;
    }

    @Override
    public void update(float dt) {
        // 首页没有可动元素，无需逐帧更新。
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float top = y - scrollOffset;
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        GlassPanel.shadow(canvas, x, top, contentW, PANEL_HEIGHT, radius, tc.shadow, alpha, 0.45f);
        GlassPanel.frost(canvas, x, top, contentW, PANEL_HEIGHT, radius, tc.module, 0.70f, alpha);
        GlassPanel.rim(canvas, x, top, contentW, PANEL_HEIGHT, radius, tc.rim, alpha, 0.10f);

        String title = UiText.t("首页内容待接入", "Home content pending");
        String hint = UiText.t("功能模块接入后，概览与常用入口将显示在这里。",
                "Overview and shortcuts will appear here once modules are wired up.");
        FontRenderer.drawTextBold(canvas, title, x + 20f, CardLayout.baseline(top + 38f, 15f), 15f,
                GlassPanel.withAlpha(tc.primaryText, alpha));
        FontRenderer.drawText(canvas, hint, x + 20f, CardLayout.baseline(top + 62f, 11f), 11f,
                GlassPanel.withAlpha(tc.secondaryText, alpha));
    }

    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset,
                           int button) {
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        return false;
    }
}
