package com.yiyiaddon.ui.screen;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.InterfacePage;
import com.yiyiaddon.ui.page.SearchResultsPage;
import com.yiyiaddon.ui.page.SettingsPage;
import com.yiyiaddon.ui.page.TestPage;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ImeBridge;
import com.yiyiaddon.ui.render.SkiaGlBackend;
import com.yiyiaddon.ui.render.SkiaScreen;
import com.yiyiaddon.ui.render.SkiaBlurRenderer;
import com.yiyiaddon.ui.theme.ClickGuiTheme;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * yiyiaddon ClickGUI 主界面。
 *
 * <p>界面结构、布局常量、动画曲线与交互方式移植自 PVPUtils 的
 * {@code client/gui/clickgui/NewSettingsScreen.java}（Source-Available Non-Commercial
 * License，署名见 NOTICE）。本阶段只做「可运行移植」：业务页面全部替换为 UI 测试页，
 * 未对视觉与布局做任何重新设计。</p>
 *
 * <p>绘制时机见 {@link SkiaScreen}：extract 阶段只记录输入，帧末由 Mixin 触发 Skija 直绘。</p>
 */
public class ClickGuiScreen extends SkiaScreen {

    private static final String[] TAB_ICONS = {"\uE868", "\uE40A", "\uE8B8"};
    private static final String[] TAB_ICON_FONTS = {FontRenderer.MATERIAL_SYMBOLS, FontRenderer.MATERIAL_SYMBOLS, FontRenderer.MATERIAL_SYMBOLS};
    private static final String[] TAB_KEYS_ZH = {"测试", "界面", "设置"};
    private static final String[] TAB_KEYS_EN = {"Test", "Interface", "Settings"};

    private static final float OPEN_DURATION = 0.16f;
    private static final float BASE_CARD_W = 740f;
    private static final float BASE_CARD_H = 500f;
    private static final float SCREEN_MARGIN = 24f;
    private static final float THUMB_SIZE = 96f;
    private static final float THUMB_GAP_X = 24f;
    private static final float THUMB_ROW_GAP = 42f;
    private static final int THUMB_COLS = 4;

    private final List<BasePage> pages;

    private int selectedTab = 0;
    private int hoveredTab = -1;
    private boolean closeHovered = false;
    private boolean resetHovered = false;
    private boolean resetConfirm = false;
    private boolean closingRequested = false;
    private boolean searchFocused = false;
    private String searchText = "";
    private BasePage searchResultsPage;

    private final float[] tabHoverAlpha = new float[TAB_KEYS_ZH.length];
    private float closeHoverAlpha = 0f;
    private float resetHoverAlpha = 0f;
    private float indicatorY = -1f;
    private float openProgress = 0f;
    private float searchFocusAlpha = 0f;
    private float searchTextOffset = 0f;
    private float searchCursorTime = 0f;
    private long lastRenderMs = 0;
    private float animatedClickGuiScale = -1f;
    private float lastDelta = 0f;

    private float contentScrollOffset = 0f;
    private float targetScrollOffset = 0f;
    private boolean draggingInContent = false;
    private boolean draggingScrollbar = false;
    private float scrollbarDragOffset = 0f;

    private final Paint cardPaint = new Paint().setAntiAlias(true);
    private final Paint sidebarPaint = new Paint().setAntiAlias(true);
    private final Paint dividerPaint = new Paint().setAntiAlias(true);
    private final Paint indicatorPaint = new Paint().setAntiAlias(true);
    private final Paint hoverPaint = new Paint().setAntiAlias(true);
    private final Paint resetBgPaint = new Paint().setAntiAlias(true);
    private final Paint closeBgPaint = new Paint().setAntiAlias(true);
    private final Paint scrollbarTrackPaint = new Paint().setAntiAlias(true);
    private final Paint scrollbarThumbPaint = new Paint().setAntiAlias(true);
    private final Paint searchBgPaint = new Paint().setAntiAlias(true);
    private final Paint searchLinePaint = new Paint().setAntiAlias(true);
    private final Paint thumbPaint = new Paint().setAntiAlias(true);
    private final Paint previewBorderPaint = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.2f);
    private final SkiaGlBackend glBackend = new SkiaGlBackend();
    private final float resetIconWidth = FontRenderer.measureTextWidth("\uE042", 13f, FontRenderer.MATERIAL_SYMBOLS);
    private final float themeBackIconWidth = FontRenderer.measureTextWidth("\uE5C4", 18f, FontRenderer.MATERIAL_SYMBOLS);
    private String cachedResetText = "";
    private float cachedResetTextWidth = 0f;
    private String cachedCloseText = "";
    private float cachedCloseTextWidth = 0f;
    private BasePage cachedScrollPage = null;
    private float cachedScrollContentH = Float.NaN;
    private float cachedContentTotalHeight = 0f;
    private float cachedScrollAreaHeight = 0f;
    private float cachedScrollMax = 0f;
    private int lastHoverSignature = Integer.MIN_VALUE;

    // 主题预览模式：右侧内容区显示全部主题缩略图（左侧功能栏保持不变）
    private boolean themePreviewMode = false;
    private int themeHoveredCard = -1;
    private boolean themeBackHovered = false;
    private final List<ClickGuiTheme> previewThemes = new ArrayList<>(ClickGuiThemeManager.themes());

    public ClickGuiScreen(Screen parent) {
        super(Component.literal("yiyiaddon"), parent);
        pages = new ArrayList<>(List.of(new TestPage(), new InterfacePage(), new SettingsPage()));
    }

    /** 重建当前分类的页面（重置后刷新控件状态）。 */
    public void rebuildCurrentPage() {
        pages.set(selectedTab, switch (selectedTab) {
            case 0 -> new TestPage();
            case 1 -> new InterfacePage();
            default -> new SettingsPage();
        });
        invalidateScrollLayout();
        applySearch();
    }

    // —— 布局 ——

    private float[] layout() {
        int layoutWidth = layoutWidth();
        int layoutHeight = layoutHeight();
        float cardW = BASE_CARD_W;
        float cardH = BASE_CARD_H;
        float cardX = (layoutWidth - cardW) / 2f;
        float cardY = (layoutHeight - cardH) / 2f;
        float sidebarW = 190f;
        float tabStartY = cardY + 110f;
        float tabH = 38f;
        float tabGap = 2f;
        float tabW = sidebarW - 24f;
        float closeH = 34f;
        float resetH = 34f;
        float closeY = cardY + cardH - 48f;
        float resetY = closeY - resetH - 8f;
        float closeX = cardX + 12f;
        float contentX = cardX + sidebarW + 1f;
        float contentW = cardW - sidebarW - 1f;
        float contentY = cardY + 66f;
        float contentH = cardH - 66f - 12f;
        return new float[]{
                cardX, cardY, cardW, cardH,
                sidebarW, tabStartY, tabH, tabGap, tabW,
                closeX, closeY, closeH, resetY, resetH,
                contentX, contentY, contentW, contentH
        };
    }

    private float getUiScale() {
        float fitX = Math.max(0.1f, (layoutWidth() - SCREEN_MARGIN) / BASE_CARD_W);
        float fitY = Math.max(0.1f, (layoutHeight() - SCREEN_MARGIN) / BASE_CARD_H);
        float fit = Math.min(1f, Math.min(fitX, fitY));
        float guiScale = minecraft == null ? 2f : Math.max(1f, (float) minecraft.getWindow().getGuiScale());
        float[] scales = {0.75f, 1.0f, 1.25f};
        float targetScale = scales[Math.max(0, Math.min(AddonConfig.uiScale, scales.length - 1))];
        if (animatedClickGuiScale < 0f) animatedClickGuiScale = targetScale;
        animatedClickGuiScale += (targetScale - animatedClickGuiScale) * Math.min(1f, lastDelta * 12f);
        if (Math.abs(animatedClickGuiScale - targetScale) < 0.001f) animatedClickGuiScale = targetScale;
        return fit * 2f / guiScale * animatedClickGuiScale;
    }

    private float getVisualScale() {
        return getUiScale() * (0.88f + 0.12f * easeOutCubic(openProgress));
    }

    private int layoutWidth() {
        return minecraft == null ? Math.max(1, this.width) : Math.max(1, Math.round(minecraft.getWindow().getWidth() * 0.5f));
    }

    private int layoutHeight() {
        return minecraft == null ? Math.max(1, this.height) : Math.max(1, Math.round(minecraft.getWindow().getHeight() * 0.5f));
    }

    // —— 绘制 ——

    @Override
    protected void drawFrame(int width, int height, int mouseX, int mouseY, float delta) {
        if (minecraft == null) return;
        renderPanelBlur();
        Canvas canvas = glBackend.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) return;
        try {
            drawPanel(canvas, width, height, mouseX, mouseY, delta);
        } finally {
            glBackend.end();
        }
    }

    private void renderPanelBlur() {
        if (!AddonConfig.panelBlur || minecraft == null) return;
        float[] l = layout();
        float visualScale = getVisualScale();
        float panelX = this.width * 0.5f + (l[0] - layoutWidth() * 0.5f) * visualScale;
        float panelY = this.height * 0.5f + (l[1] - layoutHeight() * 0.5f) * visualScale;
        SkiaBlurRenderer.getInstance().render(minecraft, panelX, panelY, l[2] * visualScale, l[3] * visualScale,
                16f * visualScale, AddonConfig.blurTintColor(), AddonConfig.blurStrength);
    }

    private float toLayoutX(double x, int width, float scale) {
        return layoutWidth() * 0.5f + ((float) x - width * 0.5f) / scale;
    }

    private float toLayoutY(double y, int height, float scale) {
        return layoutHeight() * 0.5f + ((float) y - height * 0.5f) / scale;
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * Math.min(t, 1f);
    }

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private static float easeOutCubic(float t) {
        float x = 1f - clamp01(t);
        return 1f - x * x * x;
    }

    private static int withAlpha(int color, float alpha) {
        return ((int) (alpha * 255) << 24) | (color & 0x00FFFFFF);
    }

    private static int lerpColor(int a, int b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int ar = (a >> 16) & 0xFF, ag = (a >> 8) & 0xFF, ab = a & 0xFF;
        int br = (b >> 16) & 0xFF, bg = (b >> 8) & 0xFF, bb = b & 0xFF;
        return ((int) (ar + (br - ar) * t) << 16) | ((int) (ag + (bg - ag) * t) << 8) | (int) (ab + (bb - ab) * t);
    }

    private float getContentTotalHeight(BasePage page) {
        return 54f + page.getTotalHeight() + getVisibleModuleGapTotal(page) + 12f;
    }

    private float getVisibleModuleGapTotal(BasePage page) {
        int visibleCount = 0;
        for (SettingModule m : page.getModules()) if (m.isVisible()) visibleCount++;
        return visibleCount * 8f;
    }

    private void updateScrollCache(BasePage page, float contentH) {
        float contentTotalHeight = getContentTotalHeight(page);
        if (cachedScrollPage == page
                && cachedScrollContentH == contentH
                && Math.abs(cachedContentTotalHeight - contentTotalHeight) < 0.01f) {
            return;
        }
        cachedScrollPage = page;
        cachedScrollContentH = contentH;
        cachedContentTotalHeight = contentTotalHeight;
        cachedScrollAreaHeight = contentH - 54f;
        cachedScrollMax = Math.max(0f, cachedContentTotalHeight - cachedScrollAreaHeight);
    }

    private void invalidateScrollLayout() {
        cachedScrollPage = null;
    }

    @Override
    public void removed() {
        SettingTextBox.clearFocus();
        ImeBridge.reset();
        glBackend.destroy();
        AddonConfig.save();
        super.removed();
    }

    private int computeHoverSignature(double mouseX, double mouseY) {
        float visualScale = getVisualScale();
        float mx = toLayoutX(mouseX, this.width, visualScale);
        float my = toLayoutY(mouseY, this.height, visualScale);
        float[] l = layout();
        float cardX = l[0];
        float tabStartY = l[5], tabH = l[6], tabGap = l[7], tabW = l[8];
        float closeX = l[9], closeY = l[10], closeH = l[11];
        float resetY = l[12], resetH = l[13];

        int hovered = -1;
        for (int i = 0; i < TAB_KEYS_ZH.length; i++) {
            float ty = tabStartY + i * (tabH + tabGap);
            if (mx >= cardX + 12f && mx <= cardX + 12f + tabW && my >= ty && my <= ty + tabH) {
                hovered = i;
                break;
            }
        }

        boolean close = mx >= closeX && mx <= closeX + tabW && my >= closeY && my <= closeY + closeH;
        boolean reset = mx >= closeX && mx <= closeX + tabW && my >= resetY && my <= resetY + resetH;

        int signature = hovered + 2;
        if (close) signature |= 1 << 8;
        if (reset) signature |= 1 << 9;
        return signature;
    }

    private void drawPanel(Canvas canvas, int width, int height, int mouseX, int mouseY, float delta) {
        long now = System.currentTimeMillis();
        float dt = lastRenderMs == 0 ? 0.016f : Math.min((now - lastRenderMs) / 1000f, 0.033f);
        lastRenderMs = now;
        lastDelta = dt;

        if (closingRequested) {
            openProgress = clamp01(openProgress - dt / OPEN_DURATION);
            if (openProgress < 0.005f) {
                super.closing();
                return;
            }
        } else {
            openProgress = clamp01(openProgress + dt / OPEN_DURATION);
        }

        float animT = easeOutCubic(openProgress);
        float animationScale = 0.88f + 0.12f * animT;
        float visualScale = getUiScale() * animationScale;
        float cardRadius = 16f / animationScale;
        float layoutMouseX = toLayoutX(mouseX, width, visualScale);
        float layoutMouseY = toLayoutY(mouseY, height, visualScale);
        float[] l = layout();
        BasePage currentPage = activePage();
        updateScrollCache(currentPage, l[17]);
        targetScrollOffset = Math.min(targetScrollOffset, cachedScrollMax);
        contentScrollOffset = lerp(contentScrollOffset, targetScrollOffset, dt * 18f);
        float cardX = l[0], cardY = l[1], cardW = l[2], cardH = l[3];
        float sidebarW = l[4], tabStartY = l[5], tabH = l[6], tabGap = l[7], tabW = l[8];
        float closeX = l[9], closeY = l[10], closeH = l[11], resetY = l[12], resetH = l[13];
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];
        float searchX = cardX + 18f, searchY = cardY + 66f, searchW = sidebarW - 36f, searchH = 28f;

        hoveredTab = -1;
        closeHovered = false;
        resetHovered = false;
        for (int i = 0; i < TAB_KEYS_ZH.length; i++) {
            float ty = tabStartY + i * (tabH + tabGap);
            if (layoutMouseX >= cardX + 12f && layoutMouseX <= cardX + 12f + tabW && layoutMouseY >= ty && layoutMouseY <= ty + tabH)
                hoveredTab = i;
        }
        if (layoutMouseX >= closeX && layoutMouseX <= closeX + tabW && layoutMouseY >= closeY && layoutMouseY <= closeY + closeH)
            closeHovered = true;
        if (layoutMouseX >= closeX && layoutMouseX <= closeX + tabW && layoutMouseY >= resetY && layoutMouseY <= resetY + resetH)
            resetHovered = true;
        themeBackHovered = themePreviewMode && isThemeBackButton(layoutMouseX, layoutMouseY, contentX, contentY, contentW);

        for (int i = 0; i < TAB_KEYS_ZH.length; i++) {
            float target = (i == hoveredTab && i != selectedTab) ? 1f : 0f;
            tabHoverAlpha[i] = lerp(tabHoverAlpha[i], target, dt * 12f);
        }
        closeHoverAlpha = lerp(closeHoverAlpha, closeHovered ? 1f : 0f, dt * 12f);
        resetHoverAlpha = lerp(resetHoverAlpha, resetHovered ? 1f : 0f, dt * 12f);
        searchFocusAlpha = lerp(searchFocusAlpha, searchFocused ? 1f : 0f, dt * 14f);
        searchCursorTime += dt;

        float targetIndicatorY = tabStartY + selectedTab * (tabH + tabGap);
        if (indicatorY < 0f) indicatorY = targetIndicatorY;
        indicatorY = lerp(indicatorY, targetIndicatorY, dt * 12f);

        currentPage.update(dt);

        float alpha = animT;
        float cx = width / 2f;
        float cy = height / 2f;
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();

        canvas.save();
        canvas.translate(cx, cy);
        canvas.scale(visualScale, visualScale);
        canvas.translate(-layoutWidth() * 0.5f, -layoutHeight() * 0.5f);

        float panelAlpha = AddonConfig.panelBlur ? alpha * 0.52f : alpha;
        cardPaint.setColor(withAlpha(tc.window, panelAlpha));
        canvas.drawRRect(RRect.makeXYWH(cardX, cardY, cardW, cardH, cardRadius), cardPaint);

        // 缩放动画期间让所有子表面保持在同一个圆角卡片内
        canvas.save();
        canvas.clipRRect(RRect.makeXYWH(cardX, cardY, cardW, cardH, cardRadius), true);

        sidebarPaint.setColor(withAlpha(tc.sidebar, AddonConfig.panelBlur ? alpha * 0.44f : alpha));
        canvas.save();
        canvas.clipRRect(RRect.makeXYWH(cardX, cardY, sidebarW, cardH, cardRadius), true);
        canvas.drawRect(Rect.makeXYWH(cardX, cardY, sidebarW, cardH), sidebarPaint);
        canvas.restore();

        dividerPaint.setColor(withAlpha(tc.border, alpha));
        canvas.drawRect(Rect.makeXYWH(cardX + sidebarW, cardY + 14f, 1f, cardH - 28f), dividerPaint);

        FontRenderer.drawText(canvas, "yiyiaddon", cardX + 18f, cardY + 38f, 16f, withAlpha(tc.primaryText, alpha));
        FontRenderer.drawText(canvas, UiText.t("在下方调整设置...", "Adjust the settings below..."), cardX + 18f, cardY + 54f, 10f, withAlpha(tc.secondaryText, alpha));
        drawSearchBox(canvas, searchX, searchY, searchW, searchH, alpha, dt, tc);

        indicatorPaint.setColor(withAlpha(tc.indicator, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        canvas.drawRRect(RRect.makeXYWH(cardX + 12f, indicatorY, tabW, tabH, 8f), indicatorPaint);

        for (int i = 0; i < TAB_KEYS_ZH.length; i++) {
            float tabY = tabStartY + i * (tabH + tabGap);
            if (tabHoverAlpha[i] > 0.01f) {
                hoverPaint.setColor(withAlpha(tc.hoverBackground, ClickGuiThemeColors.panelBackgroundAlpha(alpha * tabHoverAlpha[i])));
                canvas.drawRRect(RRect.makeXYWH(cardX + 12f, tabY, tabW, tabH, 8f), hoverPaint);
            }
            boolean active = i == selectedTab;
            int iconColor = active ? withAlpha(tc.accent, alpha) : withAlpha(tc.inactiveIcon, alpha);
            int textColor = active ? withAlpha(tc.accent, alpha) : withAlpha(tc.inactiveText, alpha);
            FontRenderer.drawText(canvas, TAB_ICONS[i], cardX + 18f, tabY + tabH / 2f + 6f, 13f, iconColor, TAB_ICON_FONTS[i]);
            FontRenderer.drawText(canvas, UiText.t(TAB_KEYS_ZH[i], TAB_KEYS_EN[i]), cardX + 38f, tabY + tabH / 2f + 6f, 13f, textColor);
        }

        int closeBgColor = lerpColor(tc.buttonBackground, tc.dangerHoverBackground, closeHoverAlpha);
        int closeTextColor = lerpColor(tc.buttonText, tc.dangerHoverText, closeHoverAlpha);
        int resetBgColor = lerpColor(tc.buttonBackground, tc.dangerHoverBackground, resetHoverAlpha);
        int resetTextColor = lerpColor(tc.buttonText, tc.dangerHoverText, resetHoverAlpha);
        resetBgPaint.setColor(withAlpha(resetBgColor, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        canvas.drawRRect(RRect.makeXYWH(closeX, resetY, tabW, resetH, 8f), resetBgPaint);
        String resetText = resetConfirm ? UiText.t("再次点击以确认", "Click Again to Confirm") : UiText.t("重置界面设置", "Reset UI Settings");
        if (!resetText.equals(cachedResetText)) {
            cachedResetText = resetText;
            cachedResetTextWidth = FontRenderer.measureTextWidth(resetText, 12f);
        }
        float resetTotalW = resetIconWidth + 6f + cachedResetTextWidth;
        float resetStartX = closeX + (tabW - resetTotalW) / 2f;
        FontRenderer.drawText(canvas, "\uE042", resetStartX, resetY + 22f, 13f, withAlpha(resetTextColor, alpha), FontRenderer.MATERIAL_SYMBOLS);
        FontRenderer.drawText(canvas, resetText, resetStartX + resetIconWidth + 6f, resetY + 22f, 12f, withAlpha(resetTextColor, alpha));

        closeBgPaint.setColor(withAlpha(closeBgColor, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        canvas.drawRRect(RRect.makeXYWH(closeX, closeY, tabW, closeH, 8f), closeBgPaint);
        String closeText = UiText.t("× 关闭", "× Close");
        if (!closeText.equals(cachedCloseText)) {
            cachedCloseText = closeText;
            cachedCloseTextWidth = FontRenderer.measureTextWidth(closeText, 12f);
        }
        FontRenderer.drawText(canvas, closeText, closeX + (tabW - cachedCloseTextWidth) / 2f, closeY + 22f, 12f, withAlpha(closeTextColor, alpha));

        if (themePreviewMode) {
            FontRenderer.drawText(canvas, UiText.t("面板主题", "Panel Theme"), contentX + 18f, contentY + 26f, 18f, withAlpha(tc.primaryText, alpha));
            FontRenderer.drawText(canvas, UiText.t("点击缩略图切换面板配色", "Click a thumbnail to switch the panel theme"), contentX + 18f, contentY + 42f, 10f, withAlpha(tc.secondaryText, alpha));
            float backX = themeBackX(contentX, contentW);
            hoverPaint.setColor(withAlpha(themeBackHovered ? tc.hoverBackground : tc.subModule, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
            canvas.drawRRect(RRect.makeXYWH(backX, contentY + 12f, 28f, 28f, 6f), hoverPaint);
            FontRenderer.drawText(canvas, "\uE5C4", backX + (28f - themeBackIconWidth) / 2f, contentY + 35f, 18f, withAlpha(tc.primaryText, alpha), FontRenderer.MATERIAL_SYMBOLS);
        } else {
            FontRenderer.drawText(canvas, currentPage.getTitle(), contentX + 18f, contentY + 26f, 18f, withAlpha(tc.primaryText, alpha));
            FontRenderer.drawText(canvas, currentPage.getSubtitle(), contentX + 18f, contentY + 42f, 10f, withAlpha(tc.secondaryText, alpha));
        }

        float clipTop = contentY + 54f;
        float clipBottom = contentY + contentH;
        canvas.save();
        canvas.clipRect(Rect.makeXYWH(contentX, clipTop, contentW, clipBottom - clipTop));

        if (themePreviewMode) {
            drawThemePreviewGrid(canvas, contentX, contentY, contentW, alpha, layoutMouseX, layoutMouseY);
        } else {
            float moduleStartY = contentY + 54f;
            currentPage.draw(canvas, contentX + 10f, moduleStartY, contentW - 40f, contentH - 54f, alpha, contentScrollOffset, layoutMouseX, layoutMouseY);
            drawScrollbar(canvas, currentPage, contentX, contentY, contentW, contentH, alpha, tc);
        }

        canvas.restore();
        canvas.restore();
        canvas.restore();
    }

    // —— 主题预览网格 ——

    private float previewGridWidth() {
        return THUMB_COLS * THUMB_SIZE + (THUMB_COLS - 1) * THUMB_GAP_X;
    }

    private float previewThumbX(float gridX, int index) {
        int row = index / THUMB_COLS;
        int rows = (previewThemes.size() + THUMB_COLS - 1) / THUMB_COLS;
        int count = THUMB_COLS;
        if (row == rows - 1 && previewThemes.size() % THUMB_COLS != 0) {
            count = previewThemes.size() % THUMB_COLS;
        }
        float rowW = count * THUMB_SIZE + (count - 1) * THUMB_GAP_X;
        return gridX + (previewGridWidth() - rowW) / 2f + (index % THUMB_COLS) * (THUMB_SIZE + THUMB_GAP_X);
    }

    private float previewThumbY(float gridY, int index) {
        return gridY + (index / THUMB_COLS) * (THUMB_SIZE + THUMB_ROW_GAP);
    }

    private void drawThemePreviewGrid(Canvas canvas, float contentX, float contentY, float contentW, float alpha, float mouseX, float mouseY) {
        float gridX = contentX + (contentW - previewGridWidth()) / 2f;
        float gridY = contentY + 54f + 30f;
        themeHoveredCard = -1;
        String currentId = ClickGuiThemeManager.currentId();
        for (int i = 0; i < previewThemes.size(); i++) {
            ClickGuiTheme theme = previewThemes.get(i);
            float cx = previewThumbX(gridX, i);
            float cy = previewThumbY(gridY, i);
            if (mouseX >= cx - 6f && mouseX <= cx + THUMB_SIZE + 6f && mouseY >= cy - 6f && mouseY <= cy + THUMB_SIZE + 26f) {
                themeHoveredCard = i;
            }
            drawPreviewThemeCard(canvas, cx, cy, theme, theme.id().equals(currentId), themeHoveredCard == i, alpha);
        }
    }

    private void drawPreviewThemeCard(Canvas canvas, float cx, float cy, ClickGuiTheme theme, boolean selected, boolean hovered, float alpha) {
        ClickGuiThemeColors c = ClickGuiThemeColors.of(theme);
        thumbPaint.setColor(withAlpha(c.window, alpha));
        canvas.drawRRect(RRect.makeXYWH(cx, cy, THUMB_SIZE, THUMB_SIZE, 14f), thumbPaint);
        // 迷你面板：左侧边栏竖条
        thumbPaint.setColor(withAlpha(c.sidebar, alpha));
        canvas.drawRRect(RRect.makeXYWH(cx + 12f, cy + 14f, 16f, 68f, 8f), thumbPaint);
        // 强调色指示条
        thumbPaint.setColor(withAlpha(c.accent, alpha));
        canvas.drawRRect(RRect.makeXYWH(cx + 14f, cy + 16f, 4f, 14f, 2f), thumbPaint);
        // 模块色块
        thumbPaint.setColor(withAlpha(c.module, alpha));
        canvas.drawRRect(RRect.makeXYWH(cx + 36f, cy + 18f, 44f, 18f, 6f), thumbPaint);
        canvas.drawRRect(RRect.makeXYWH(cx + 36f, cy + 42f, 30f, 18f, 6f), thumbPaint);
        // 次级文字色条
        thumbPaint.setColor(withAlpha(c.secondaryText, alpha * 0.55f));
        canvas.drawRRect(RRect.makeXYWH(cx + 36f, cy + 66f, 36f, 5f, 2.5f), thumbPaint);
        // 边框
        int borderColor = selected ? c.accent : (hovered ? c.secondaryText : c.border);
        previewBorderPaint.setStrokeWidth(selected ? 2f : 1.2f);
        previewBorderPaint.setColor(withAlpha(borderColor, alpha));
        canvas.drawRRect(RRect.makeXYWH(cx, cy, THUMB_SIZE, THUMB_SIZE, 14f), previewBorderPaint);
        // 选中勾选
        if (selected) {
            FontRenderer.drawText(canvas, "\uE5CA", cx + THUMB_SIZE - 24f, cy + 20f, 13f, withAlpha(c.accent, alpha), FontRenderer.MATERIAL_SYMBOLS);
        }
        // 主题名
        String name = theme.displayName();
        float nw = FontRenderer.measureTextWidth(name, 12f);
        FontRenderer.drawText(canvas, name, cx + (THUMB_SIZE - nw) / 2f, cy + THUMB_SIZE + 17f, 12f, withAlpha(ClickGuiThemeColors.current().primaryText, alpha));
    }

    private void drawScrollbar(Canvas canvas, BasePage page, float contentX, float contentY, float contentW, float contentH, float alpha, ClickGuiThemeColors tc) {
        updateScrollCache(page, contentH);
        if (cachedContentTotalHeight <= cachedScrollAreaHeight) return;

        float trackX = contentX + contentW - 8f;
        float trackTop = contentY + 60f;
        float trackH = contentH - 60f - 8f;
        float thumbH = Math.max(20f, trackH * cachedScrollAreaHeight / cachedContentTotalHeight);
        float maxScroll = Math.max(1f, cachedScrollMax);
        float progress = Math.min(1f, contentScrollOffset / maxScroll);
        float thumbTop = trackTop + (trackH - thumbH) * progress;
        thumbTop = Math.min(thumbTop, trackTop + trackH - thumbH);

        scrollbarTrackPaint.setColor(withAlpha(tc.scrollbarTrack, alpha * 0.5f));
        canvas.drawRRect(RRect.makeXYWH(trackX, trackTop, 4f, trackH, 2f), scrollbarTrackPaint);
        scrollbarThumbPaint.setColor(withAlpha(tc.scrollbarThumb, alpha));
        canvas.drawRRect(RRect.makeXYWH(trackX, thumbTop, 4f, thumbH, 2f), scrollbarThumbPaint);
    }

    private void drawSearchBox(Canvas canvas, float x, float y, float width, float height, float alpha, float dt, ClickGuiThemeColors tc) {
        int background = lerpColor(tc.searchBackground, tc.searchFocusedBackground, searchFocusAlpha);
        searchBgPaint.setColor(withAlpha(background, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        canvas.drawRRect(RRect.makeXYWH(x, y, width, height, 7f), searchBgPaint);

        FontRenderer.drawText(canvas, "\uE8B6", x + 9f, y + 19f, 12f, withAlpha(tc.searchIcon, alpha), FontRenderer.MATERIAL_SYMBOLS);
        float textX = x + 28f;
        float textW = Math.max(1f, width - 36f);
        boolean empty = searchText.isEmpty();
        String display = empty ? UiText.t("输入以查找...", "Type to search...") : searchText;
        float realTextWidth = FontRenderer.measureTextWidth(searchText, 10f);
        float targetOffset = empty ? 0f : Math.max(0f, realTextWidth - textW + 3f);
        searchTextOffset = lerp(searchTextOffset, targetOffset, dt * 16f);

        canvas.save();
        canvas.clipRect(Rect.makeXYWH(textX, y + 2f, textW, height - 4f));
        FontRenderer.drawText(canvas, display, textX - (empty ? 0f : searchTextOffset), y + 18.5f, 10f,
                withAlpha(empty ? tc.searchTextPlaceholder : tc.searchText, alpha));
        if (searchFocused) {
            float cursorPulse = 0.35f + 0.65f * (0.5f + 0.5f * (float) Math.sin(searchCursorTime * 6f));
            float cursorX = textX + Math.min(textW - 1f, Math.max(0f, realTextWidth - searchTextOffset));
            searchLinePaint.setColor(withAlpha(tc.searchCursor, alpha * cursorPulse));
            canvas.drawRect(Rect.makeXYWH(cursorX, y + 7f, 1f, 14f), searchLinePaint);
        }
        canvas.restore();

        float linePulse = 0.3f + 0.7f * (0.5f + 0.5f * (float) Math.sin(searchCursorTime * 6f));
        searchLinePaint.setColor(withAlpha(tc.searchCursor, alpha * searchFocusAlpha * linePulse));
        canvas.drawRect(Rect.makeXYWH(x + 8f, y + height - 2f, width - 16f, 1f), searchLinePaint);
    }

    // —— 搜索 ——

    private void applySearch() {
        for (BasePage page : pages) {
            page.setSearchQuery(searchText);
        }
        if (searchText.isBlank()) {
            searchResultsPage = null;
        } else {
            List<SettingModule> results = new ArrayList<>();
            for (BasePage page : pages) {
                for (SettingModule module : page.getModules()) {
                    if (module.isVisible() && module.matchesSearch(searchText)) {
                        results.add(module);
                    }
                }
            }
            searchResultsPage = new SearchResultsPage(searchText, results);
        }
        targetScrollOffset = 0f;
        contentScrollOffset = 0f;
        invalidateScrollLayout();
    }

    private BasePage activePage() {
        return searchResultsPage == null ? pages.get(selectedTab) : searchResultsPage;
    }

    private void clearSearch() {
        setSearchFocused(false);
        searchText = "";
        searchTextOffset = 0f;
        applySearch();
    }

    /** 打开面板主题预览模式：右侧内容区切换为主题缩略图网格。 */
    public void openThemePreview() {
        clearSearch();
        themePreviewMode = true;
        targetScrollOffset = 0f;
        contentScrollOffset = 0f;
        invalidateScrollLayout();
    }

    private void closeThemePreview() {
        themePreviewMode = false;
        themeBackHovered = false;
        targetScrollOffset = 0f;
        contentScrollOffset = 0f;
        invalidateScrollLayout();
    }

    private static float themeBackX(float contentX, float contentW) {
        return contentX + contentW - 40f;
    }

    private static boolean isThemeBackButton(float mouseX, float mouseY, float contentX, float contentY, float contentW) {
        float x = themeBackX(contentX, contentW);
        return mouseX >= x && mouseX <= x + 28f && mouseY >= contentY + 12f && mouseY <= contentY + 40f;
    }

    private void setSearchFocused(boolean focused) {
        if (searchFocused == focused) {
            return;
        }
        searchFocused = focused;
        ImeBridge.setTextInputActive(focused);
    }

    // —— 滚动条几何 ——

    private boolean hasScrollbar(BasePage page, float contentH) {
        updateScrollCache(page, contentH);
        return cachedContentTotalHeight > cachedScrollAreaHeight;
    }

    private float scrollbarTrackX(float contentX, float contentW) {
        return contentX + contentW - 8f;
    }

    private float scrollbarTrackTop(float contentY) {
        return contentY + 60f;
    }

    private float scrollbarTrackH(float contentH) {
        return contentH - 60f - 8f;
    }

    private float scrollbarThumbH(BasePage page, float contentH) {
        updateScrollCache(page, contentH);
        return Math.max(20f, scrollbarTrackH(contentH) * cachedScrollAreaHeight / cachedContentTotalHeight);
    }

    private float scrollbarThumbTop(BasePage page, float contentY, float contentH) {
        updateScrollCache(page, contentH);
        float trackTop = scrollbarTrackTop(contentY);
        float trackH = scrollbarTrackH(contentH);
        float thumbH = scrollbarThumbH(page, contentH);
        float maxScroll = Math.max(1f, cachedScrollMax);
        float progress = Math.min(1f, targetScrollOffset / maxScroll);
        return Math.min(trackTop + (trackH - thumbH) * progress, trackTop + trackH - thumbH);
    }

    private boolean isInScrollbar(float mx, float my, float contentX, float contentY, float contentW, float contentH) {
        float x = scrollbarTrackX(contentX, contentW) - 6f;
        float top = scrollbarTrackTop(contentY);
        float h = scrollbarTrackH(contentH);
        return mx >= x && mx <= x + 16f && my >= top && my <= top + h;
    }

    private void setScrollFromScrollbar(BasePage page, float my, float contentY, float contentH) {
        updateScrollCache(page, contentH);
        float maxScroll = cachedScrollMax;
        float trackTop = scrollbarTrackTop(contentY);
        float trackH = scrollbarTrackH(contentH);
        float thumbH = scrollbarThumbH(page, contentH);
        float available = Math.max(1f, trackH - thumbH);
        float thumbTop = Math.max(trackTop, Math.min(my - scrollbarDragOffset, trackTop + available));
        targetScrollOffset = maxScroll * ((thumbTop - trackTop) / available);
    }

    // —— 输入 ——

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (ModuleKeybindManager.captureKey(event.key())) {
            invalidateScrollLayout();
            return true;
        }
        if (SettingTextBox.keyPressed(event)) {
            invalidateScrollLayout();
            return true;
        }
        if (themePreviewMode && event.isEscape()) {
            closeThemePreview();
            return true;
        }
        if (searchFocused) {
            if (event.key() == GLFW.GLFW_KEY_BACKSPACE && !searchText.isEmpty()) {
                int end = searchText.offsetByCodePoints(searchText.length(), -1);
                searchText = searchText.substring(0, end);
                applySearch();
            } else if (event.isEscape()) {
                clearSearch();
            }
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (SettingTextBox.charTyped(event)) {
            invalidateScrollLayout();
            return true;
        }
        if (!searchFocused) {
            return super.charTyped(event);
        }
        String typed = event.codepointAsString();
        if (typed != null && !typed.isEmpty()) {
            searchText += typed;
            applySearch();
        }
        return true;
    }

    @Override
    public boolean preeditUpdated(PreeditEvent event) {
        SettingTextBox.onPreedit(event);
        return true;
    }

    @Override
    public void onClose() {
        SettingTextBox.clearFocus();
        clearSearch();
        closingRequested = true;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        int hoverSignature = computeHoverSignature(mouseX, mouseY);
        if (hoverSignature != lastHoverSignature) {
            lastHoverSignature = hoverSignature;
            invalidateScrollLayout();
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        if (closingRequested) return false;
        int button = event.button();
        if (ModuleKeybindManager.captureMouseButton(button)) {
            draggingInContent = false;
            draggingScrollbar = false;
            invalidateScrollLayout();
            return true;
        }
        if (button > GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            return true;
        }

        float visualScale = getVisualScale();
        float mx = toLayoutX(event.x(), this.width, visualScale);
        float my = toLayoutY(event.y(), this.height, visualScale);
        float[] l = layout();
        float cardX = l[0];
        float sidebarW = l[4], tabStartY = l[5], tabH = l[6], tabGap = l[7], tabW = l[8];
        float closeX = l[9], closeY = l[10], closeH = l[11];
        float resetY = l[12], resetH = l[13];
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];
        float searchX = cardX + 18f, searchY = l[1] + 66f, searchW = sidebarW - 36f, searchH = 28f;
        BasePage page = activePage();

        if (button == 0 && mx >= searchX && mx <= searchX + searchW && my >= searchY && my <= searchY + searchH) {
            SettingTextBox.clearFocus();
            themePreviewMode = false;
            setSearchFocused(true);
            searchCursorTime = 0f;
            invalidateScrollLayout();
            return true;
        }
        setSearchFocused(false);
        SettingTextBox.clearFocus();

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && themePreviewMode && isThemeBackButton(mx, my, contentX, contentY, contentW)) {
            closeThemePreview();
            return true;
        }

        for (int i = 0; i < TAB_KEYS_ZH.length; i++) {
            float ty = tabStartY + i * (tabH + tabGap);
            if (mx >= cardX + 12f && mx <= cardX + 12f + tabW && my >= ty && my <= ty + tabH) {
                if (button == 0) {
                    clearSearch();
                    themePreviewMode = false;
                    selectedTab = i;
                    targetScrollOffset = 0f;
                    contentScrollOffset = 0f;
                    invalidateScrollLayout();
                }
                return true;
            }
        }

        if (button == 0 && mx >= closeX && mx <= closeX + tabW && my >= closeY && my <= closeY + closeH) {
            closingRequested = true;
            invalidateScrollLayout();
            return true;
        }

        if (button == 0 && mx >= closeX && mx <= closeX + tabW && my >= resetY && my <= resetY + resetH) {
            if (resetConfirm) {
                themePreviewMode = false;
                resetUiSettings();
                resetConfirm = false;
                rebuildCurrentPage();
            } else {
                resetConfirm = true;
            }
            invalidateScrollLayout();
            return true;
        }

        resetConfirm = false;

        if (mx >= contentX && mx <= contentX + contentW && my >= contentY && my <= contentY + contentH) {
            if (themePreviewMode) {
                float gridX = contentX + (contentW - previewGridWidth()) / 2f;
                float gridY = contentY + 54f + 30f;
                for (int i = 0; i < previewThemes.size(); i++) {
                    float cx = previewThumbX(gridX, i);
                    float cy = previewThumbY(gridY, i);
                    if (mx >= cx - 6f && mx <= cx + THUMB_SIZE + 6f && my >= cy - 6f && my <= cy + THUMB_SIZE + 26f) {
                        ClickGuiTheme theme = previewThemes.get(i);
                        if (!theme.id().equals(ClickGuiThemeManager.currentId())) {
                            ClickGuiThemeManager.selectAndSave(theme.id());
                        }
                        invalidateScrollLayout();
                        return true;
                    }
                }
                return true;
            }
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && hasScrollbar(page, contentH) && isInScrollbar(mx, my, contentX, contentY, contentW, contentH)) {
                draggingScrollbar = true;
                float thumbTop = scrollbarThumbTop(page, contentY, contentH);
                float thumbH = scrollbarThumbH(page, contentH);
                scrollbarDragOffset = my >= thumbTop && my <= thumbTop + thumbH ? my - thumbTop : thumbH * 0.5f;
                setScrollFromScrollbar(page, my, contentY, contentH);
                contentScrollOffset = targetScrollOffset;
                invalidateScrollLayout();
                return true;
            }
            float moduleStartY = contentY + 54f;
            boolean hit = page.onClick(mx, my, contentX + 10f, moduleStartY, contentW - 40f, contentScrollOffset, button);
            if (hit && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                draggingInContent = true;
                invalidateScrollLayout();
            }
            return hit;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (draggingScrollbar) {
            float visualScale = getVisualScale();
            float my = toLayoutY(event.y(), this.height, visualScale);
            float[] l = layout();
            setScrollFromScrollbar(activePage(), my, l[15], l[17]);
            contentScrollOffset = targetScrollOffset;
            invalidateScrollLayout();
            return true;
        }
        if (draggingInContent) {
            float visualScale = getVisualScale();
            float mx = toLayoutX(event.x(), this.width, visualScale);
            float my = toLayoutY(event.y(), this.height, visualScale);
            float[] l = layout();
            float contentX = l[14], contentY = l[15], contentW = l[16];
            activePage().onDrag(mx, my, contentX + 10f, contentY + 54f, contentW - 40f, contentScrollOffset);
            invalidateScrollLayout();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        draggingInContent = false;
        draggingScrollbar = false;
        activePage().releaseDrag();
        invalidateScrollLayout();
        return false;
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double hScroll, double vScroll) {
        if (themePreviewMode) return false;
        float visualScale = getVisualScale();
        float layoutMx = toLayoutX(mx, this.width, visualScale);
        float layoutMy = toLayoutY(my, this.height, visualScale);
        float[] l = layout();
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];

        if (layoutMx >= contentX && layoutMx <= contentX + contentW && layoutMy >= contentY && layoutMy <= contentY + contentH) {
            BasePage page = activePage();
            updateScrollCache(page, contentH);
            targetScrollOffset = Math.max(0f, Math.min(cachedScrollMax, targetScrollOffset + (float) (-vScroll * 16f * Math.max(0.2f, AddonConfig.scrollSpeed))));
            invalidateScrollLayout();
            return true;
        }
        return false;
    }

    private void resetUiSettings() {
        ClickGuiThemeManager.selectAndSave(ClickGuiThemeManager.themes().iterator().next().id());
        AddonConfig.uiScale = 1;
        AddonConfig.panelBlur = true;
        AddonConfig.blurStrength = 0.6f;
        AddonConfig.blurTint = 0x50101014;
        AddonConfig.scrollSpeed = 1.0f;
        ModuleKeybindManager.clearAll(false);
        AddonConfig.save();
    }
}
