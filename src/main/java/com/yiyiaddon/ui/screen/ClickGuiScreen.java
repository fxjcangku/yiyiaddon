package com.yiyiaddon.ui.screen;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.service.update.UpdateService;
import com.yiyiaddon.ui.component.VersionHeader;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.component.BackButton;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.PanelFrame;
import com.yiyiaddon.ui.component.ScrollViewport;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.HomePage;
import com.yiyiaddon.ui.page.InterfacePage;
import com.yiyiaddon.ui.page.ModuleCenterPage;
import com.yiyiaddon.ui.page.SearchResultsPage;
import com.yiyiaddon.ui.page.SettingsPage;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ImeBridge;
import com.yiyiaddon.ui.render.SkiaGlBackend;
import com.yiyiaddon.ui.render.SkiaBlurRenderer;
import com.yiyiaddon.ui.render.SkiaScreen;
import com.yiyiaddon.ui.render.TooltipLayer;
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
 * <p>左侧为固定主导航（首页 / 模块 / 界面 / 设置），右侧为内容区。面板内的页面切换是
 * 瞬时切换：只绘制当前页面，页与页之间没有任何位移与交叉绘制，因此任意时刻绘制坐标都与
 * 布局坐标一致。点击模块列表里的模块不再在面板内翻页，而是由 {@link ModuleScreen}
 * 单独打开一个界面。</p>
 *
 * <p>面板几何与滚动分别由 {@link PanelFrame} 与 {@link ScrollViewport} 提供，
 * 与模块页共用同一套尺寸、缩放与滚动行为。</p>
 *
 * <p>绘制时机见 {@link SkiaScreen}：extract 阶段只记录输入，帧末由 Mixin 触发 Skija 直绘。</p>
 */
public class ClickGuiScreen extends SkiaScreen {
    // 2026-09-14 视觉更新：页面改为 160ms 交叉淡化，上述瞬时切换说明仅对应历史实现。

    private static final String[] NAV_ICONS = {"\uE88A", "\uE61D", "\uE429", "\uE8B8"};
    private static final String[] NAV_KEYS_ZH = {"首页", "模块", "界面", "设置"};
    private static final String[] NAV_KEYS_EN = {"Home", "Modules", "Interface", "Settings"};

    /** 导航项与控件圆角，对齐苹果控件层级。 */
    private static final float NAV_RADIUS = 10f;

    /** 侧栏宽度。 */
    private static final float SIDEBAR_W = 190f;
    /** 侧栏文字 / 搜索框的左右内缩。 */
    private static final float SIDEBAR_PAD_X = 18f;
    /**
     * 内容区顶部相对面板的距离，以及页头基线相对所在列顶部的偏移。
     *
     * <p><b>为什么是这几个数：</b>左侧「yiyiaddon」要贴近窗口顶部（基线 38），又要和右侧页面标题
     * 落在同一条线上，于是由 {@code CONTENT_TOP + HEADER_TITLE_Y = 38}、{@code + HEADER_SUBTITLE_Y = 55}
     * 反推出内容区上边在 16。几个数是一组合成的结果，单独改一个就会让两列页头错位。</p>
     *
     * <p>{@link #HEADER_SLOGAN_Y} 只给侧栏用（版本号之下还有一行标语），右栏页面只有标题 + 副标题两行，
     * 因此它的副标题仍停在 {@link #HEADER_SUBTITLE_Y}。</p>
     */
    private static final float CONTENT_TOP = 16f;
    private static final float HEADER_TITLE_Y = 22f;
    private static final float HEADER_SUBTITLE_Y = 39f;
    private static final float HEADER_SLOGAN_Y = 56f;
    /** 搜索框：标语基线之下留的空隙与自身高度。 */
    private static final float SEARCH_TOP_GAP = 11f;
    private static final float SEARCH_H = 28f;
    /** 搜索框到第一个导航项的距离。 */
    private static final float NAV_TOP_GAP = 16f;
    /**
     * 内容区头部高度（标题区）：标题 + 副标题之下留给内容的空间。
     *
     * <p>取值只保证「页面副标题与第一张卡片之间留出正常间距」：副标题底部约在
     * {@code CONTENT_TOP + HEADER_SUBTITLE_Y + 3 = 58}，加上 {@code CONTENT_HEADER_H} 与页面自身的
     * 首卡留白（{@code CardLayout.TOP_INSET = 20}）后，首卡顶边落在 66 附近，间距 24。</p>
     *
     * <p>不要再按「右栏首行与左栏第一个导航项齐平」去取值：侧栏页头加了一行标语后该对齐点会被推到
     * 127，页面顶部就会空出一大块（实机反馈「每个分组都留白太多、没撑满」）。</p>
     */
    private static final float CONTENT_HEADER_H = 46f;
    /** 内容底部留白，用于计算滚动上限。 */
    private static final float CONTENT_BOTTOM_PAD = 12f;
    /**
     * 页面内容相对内容区的左右内缩：左侧让卡片不贴住侧栏分隔位置，右侧为滚动条预留宽度。
     *
     * <p>取 0 时卡片会直接顶到侧栏与面板圆角上（实机反馈「边边卡进去了」），
     * 因此这两处必须留出余量，页面自己不再重复加水平内边距。</p>
     */
    private static final float PAGE_INSET_X = 14f;
    private static final float PAGE_RESERVED_W = 34f;
    /** 滚动条相对内容区的位置：贴着页头裁剪线往下一点。 */
    private static final float TRACK_INSET = 8f;
    private static final float TRACK_TOP = CONTENT_HEADER_H + 4f;
    private static final float TRACK_BOTTOM_PAD = 8f;

    private static final float THUMB_SIZE = 96f;
    private static final float THUMB_GAP_X = 24f;
    private static final float THUMB_ROW_GAP = 42f;
    private static final int THUMB_COLS = 4;

    private final PageRouter router;
    private final PanelFrame frame = new PanelFrame();
    private final ScrollViewport scroll = new ScrollViewport();
    private final VersionHeader versionHeader = new VersionHeader(UpdateService::openRepository,
            () -> UpdateService.checkManually(this), UpdateService::openFeedback);

    private int hoveredTab = -1;
    private boolean closeHovered = false;
    private boolean resetHovered = false;
    private boolean resetConfirm = false;
    private boolean closingRequested = false;
    private boolean searchFocused = false;
    private String searchText = "";
    private BasePage searchResultsPage;
    /** 页面交叉淡化只改变透明度，内容坐标与输入路由保持原样。 */
    private BasePage displayedPage;
    private BasePage fadingPage;
    private float pageFade = 1f;
    private float displayedScroll;
    private float fadingScroll;
    private boolean draggingInContent = false;
    private boolean draggingScrollbar = false;

    private final float[] tabHoverAlpha = new float[NAV_KEYS_ZH.length];
    private float closeHoverAlpha = 0f;
    private float resetHoverAlpha = 0f;
    private float indicatorY = -1f;
    private float searchFocusAlpha = 0f;
    private float searchTextOffset = 0f;
    private float searchCursorTime = 0f;
    /** 上一次绘制时的搜索框设计矩形；聚焦时用它把输入法锚点一次摆到位。 */
    private float searchBoxX;
    private float searchBoxY;
    private float searchBoxW;
    private float searchBoxH;
    private long lastRenderMs = 0;

    // —— 动画状态：滚动缓动、导航指示块弹簧、按压 ——
    private final Spring indicatorSpring = Spring.critical(0.22f);
    private final PressState[] navPress = new PressState[NAV_KEYS_ZH.length];
    private final PressState closePress = new PressState();
    private final PressState resetPress = new PressState();
    private final BackButton backButton = new BackButton();

    {
        for (int i = 0; i < navPress.length; i++) navPress[i] = new PressState();
    }

    private final Paint hoverPaint = new Paint().setAntiAlias(true);
    private final Paint resetBgPaint = new Paint().setAntiAlias(true);
    private final Paint closeBgPaint = new Paint().setAntiAlias(true);
    private final Paint searchLinePaint = new Paint().setAntiAlias(true);
    private final Paint thumbPaint = new Paint().setAntiAlias(true);
    private final Paint previewBorderPaint = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.2f);
    private final SkiaGlBackend glBackend = new SkiaGlBackend();
    private final float resetIconWidth = FontRenderer.measureTextWidth("\uE042", 13f, FontRenderer.MATERIAL_SYMBOLS);
    private String cachedResetText = "";
    private float cachedResetTextWidth = 0f;
    private String cachedCloseText = "";
    private float cachedCloseTextWidth = 0f;

    // 主题预览模式：右侧内容区显示全部主题缩略图（左侧功能栏保持不变）
    private boolean themePreviewMode = false;
    private int themeHoveredCard = -1;
    private final List<ClickGuiTheme> previewThemes = new ArrayList<>(ClickGuiThemeManager.themes());

    public ClickGuiScreen(Screen parent) {
        super(Component.literal("yiyiaddon"), parent);
        router = new PageRouter();
        for (BasePage page : createRootPages()) router.addRoot(page);
        restoreNavigation();
    }

    /** 左侧导航的根页面，顺序与 NAV_KEYS 一一对应。 */
    private List<BasePage> createRootPages() {
        return List.of(new HomePage(router, this::openModuleScreen),
                new ModuleCenterPage(router, this::openModuleScreen), new InterfacePage(), new SettingsPage(router));
    }

    /**
     * 恢复上次关闭面板时停留的位置（导航项 + 下钻页面）。
     *
     * <p>只按标识重建：标识指向的分类若已不存在（改了注册表 / 换了存档），就地停在前一级，
     * 不抛异常也不回首页。</p>
     */
    private void restoreNavigation() {
        router.select(UiNavigationMemory.rootIndex());
        for (String token : UiNavigationMemory.tokens()) {
            BasePage page = pageForToken(token);
            if (page == null) break;
            router.open(page, token);
        }
    }

    /** 由可重建标识装配页面（与 {@link ModuleCenterPage} 的进入逻辑一一对应）。 */
    private BasePage pageForToken(String token) {
        String ownPageCategory = UiNavigationMemory.categoryId(token, UiNavigationMemory.TOKEN_PAGE);
        if (ownPageCategory != null) {
            ModuleCategory category = CategoryRegistry.byId(ownPageCategory);
            return category == null || category.page() == null ? null : category.page().get();
        }
        return null;
    }

    /** 关闭面板时记住当前位置：下次打开还停在原处，不再每次都回首页。 */
    private void rememberNavigation() {
        UiNavigationMemory.remember(router.index(), router.tokens());
    }

    /** 打开模块独立屏幕：把本界面作为返回目标交给模块页。 */
    private void openModuleScreen(ModuleEntry entry) {
        if (minecraft == null) return;
        draggingInContent = false;
        draggingScrollbar = false;
        SettingTextBox.clearFocus();
        minecraft.gui.setScreen(new ModuleScreen(entry, this));
    }

    /** 重建当前分类的页面（重置后刷新控件状态）。 */
    public void rebuildCurrentPage() {
        List<BasePage> roots = createRootPages();
        router.replaceRoot(router.index(), roots.get(router.index()));
        router.reset();
        applySearch();
    }

    // —— 布局 ——

    private float[] layout() {
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float cardW = frame.cardWidth();
        float cardH = frame.cardHeight();
        float sidebarW = SIDEBAR_W;
        // 两列页头共用 CONTENT_TOP + HEADER_*_Y：左栏标题与右栏页面标题自然落在同一条基线
        float headerTitleY = cardY + CONTENT_TOP + HEADER_TITLE_Y;
        float headerSubtitleY = cardY + CONTENT_TOP + HEADER_SUBTITLE_Y;
        float headerSloganY = cardY + CONTENT_TOP + HEADER_SLOGAN_Y;
        float searchX = cardX + SIDEBAR_PAD_X;
        float searchY = headerSloganY + SEARCH_TOP_GAP;
        float searchW = sidebarW - SIDEBAR_PAD_X * 2f;
        float tabStartY = searchY + SEARCH_H + NAV_TOP_GAP;
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
        float contentY = cardY + CONTENT_TOP;
        float contentH = cardH - CONTENT_TOP - CONTENT_BOTTOM_PAD;
        return new float[]{
                cardX, cardY, cardW, cardH,
                sidebarW, tabStartY, tabH, tabGap, tabW,
                closeX, closeY, closeH, resetY, resetH,
                contentX, contentY, contentW, contentH,
                searchX, searchY, searchW, SEARCH_H,
                headerTitleY, headerSubtitleY, headerSloganY
        };
    }

    // —— 绘制 ——

    @Override
    protected void init() {
        super.init();
        // 先算一次几何，输入处理不必等第一帧
        frame.update(minecraft, 0f);
    }

    @Override
    protected void drawFrame(int width, int height, int mouseX, int mouseY, float delta) {
        if (minecraft == null) return;
        Canvas canvas = glBackend.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) return;
        try {
            drawPanel(canvas, width, height, mouseX, mouseY);
        } finally {
            glBackend.end();
        }
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * Math.min(t, 1f);
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

    /** 导航项被选中指示块覆盖的程度，用于文字颜色过渡。 */
    private float activationAt(float tabY, float itemHeight, float itemGap) {
        float distance = Math.abs((indicatorY + itemHeight / 2f) - (tabY + itemHeight / 2f));
        return Math.max(0f, Math.min(1f, 1f - distance / (itemHeight + itemGap)));
    }

    /** 切换页面或搜索时把滚动复位到顶部。 */
    private void resetScroll() {
        scroll.jumpTo(0f);
    }

    /** 按当前页面刷新滚动上限；内容总高与滚动条几何保持一致。 */
    private void refreshScroll() {
        BasePage page = activePage();
        float contentH = layout()[17];
        // 页面绘制原点已经在页头下方，这里不能再把 CONTENT_HEADER_H 算进内容高：
        // 算进去等于屏幕底部凭空多出一段能滚过去的空白（实机看起来像「滚不到底」）。
        scroll.layout(page.getTotalHeight() + page.getVisibleSpacing() + CONTENT_BOTTOM_PAD,
                contentH - CONTENT_HEADER_H);
    }

    @Override
    public void removed() {
        SettingTextBox.clearFocus();
        ImeBridge.reset();
        rememberNavigation();
        draggingInContent = false;
        draggingScrollbar = false;
        glBackend.destroy();
        AddonConfig.save();
        super.removed();
    }

    /** 面板停在最终位置后才动隐藏格子；动画期间的画面备份与当前面板对不齐，会透出正文重影。 */
    @Override
    protected boolean canCaptureIcons() {
        return frame.animationAlpha() >= 1f;
    }

    private void drawPanel(Canvas canvas, int width, int height, int mouseX, int mouseY) {
        long now = System.currentTimeMillis();
        float dt = lastRenderMs == 0L ? 0.016f : Math.min((now - lastRenderMs) / 1000f, 0.033f);
        lastRenderMs = now;

        // 帧首清空悬停浮层登记：控件（设置项等）在绘制时登记，帧末统一绘制。
        // 本屏托管的是各功能页，没有这一步页面里的说明浮层永远画不出来（与 ModuleScreen 同一套）。
        TooltipLayer.beginFrame();

        if (frame.update(minecraft, dt)) {
            // 关闭动画播完，交回上级界面
            super.closing();
            return;
        }

        float animT = frame.animationAlpha();
        float cardRadius = frame.cardRadius();
        float layoutMouseX = frame.toDesignX(mouseX, width);
        float layoutMouseY = frame.toDesignY(mouseY, height);
        float[] l = layout();
        BasePage currentPage = activePage();
        // 仅保留上一页一个短暂引用，快速切页也不会积累页面或离屏纹理。
        if (displayedPage != currentPage) {
            fadingPage = displayedPage;
            fadingScroll = displayedScroll;
            displayedPage = currentPage;
            pageFade = fadingPage == null ? 1f : 0f;
        }
        pageFade = Math.min(1f, pageFade + Math.max(0f, dt) / 0.16f);
        if (pageFade >= 1f) fadingPage = null;
        refreshScroll();
        scroll.update(dt);
        displayedScroll = scroll.value();

        float cardX = l[0], cardY = l[1], cardW = l[2], cardH = l[3];
        float sidebarW = l[4], tabStartY = l[5], tabH = l[6], tabGap = l[7], tabW = l[8];
        float closeX = l[9], closeY = l[10], closeH = l[11], resetY = l[12], resetH = l[13];
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];
        float searchX = l[18], searchY = l[19], searchW = l[20], searchH = l[21];
        float headerTitleY = l[22], headerSubtitleY = l[23], headerSloganY = l[24];

        drawPanelGlass(canvas, width, height, cardX, cardY, cardW, cardH, cardRadius);

        // 控制中心式环境压暗把视觉焦点收回面板，透明度足够低，不掩盖游戏状态。
        ClickGuiThemeColors backdropColors = ClickGuiThemeColors.current();
        GlassPanel.fill(canvas, 0f, 0f, width, height, 0f, backdropColors.shadow,
                animT * (backdropColors.dark ? 0.16f : 0.10f));

        hoveredTab = -1;
        closeHovered = false;
        resetHovered = false;
        for (int i = 0; i < NAV_KEYS_ZH.length; i++) {
            float ty = tabStartY + i * (tabH + tabGap);
            if (layoutMouseX >= cardX + 12f && layoutMouseX <= cardX + 12f + tabW && layoutMouseY >= ty && layoutMouseY <= ty + tabH)
                hoveredTab = i;
        }
        if (layoutMouseX >= closeX && layoutMouseX <= closeX + tabW && layoutMouseY >= closeY && layoutMouseY <= closeY + closeH)
            closeHovered = true;
        if (layoutMouseX >= closeX && layoutMouseX <= closeX + tabW && layoutMouseY >= resetY && layoutMouseY <= resetY + resetH)
            resetHovered = true;

        for (int i = 0; i < NAV_KEYS_ZH.length; i++) {
            float target = (i == hoveredTab && i != router.index()) ? 1f : 0f;
            tabHoverAlpha[i] = lerp(tabHoverAlpha[i], target, dt * 12f);
        }
        closeHoverAlpha = lerp(closeHoverAlpha, closeHovered ? 1f : 0f, dt * 12f);
        resetHoverAlpha = lerp(resetHoverAlpha, resetHovered ? 1f : 0f, dt * 12f);
        searchFocusAlpha = lerp(searchFocusAlpha, searchFocused ? 1f : 0f, dt * 14f);
        searchCursorTime += dt;

        // 选中指示块用临界阻尼弹簧滑到当前导航项，约 220ms
        float targetIndicatorY = tabStartY + router.index() * (tabH + tabGap);
        if (indicatorY < 0f) {
            indicatorY = targetIndicatorY;
            indicatorSpring.set(targetIndicatorY);
        }
        indicatorSpring.setTarget(targetIndicatorY);
        indicatorSpring.update(dt);
        indicatorY = indicatorSpring.value();

        currentPage.update(dt);
        backButton.update(layoutMouseX, layoutMouseY, backButtonX(contentX, contentW),
                backButtonY(contentY), dt, backButtonVisible());

        float alpha = animT;
        ClickGuiThemeColors tc = backdropColors;

        // 面板变换、窗口裁剪、内容裁剪三层 save 与 finally 中的三次 restore 严格配对
        canvas.save();
        frame.applyTransform(canvas, width, height);
        try {
            // 窗口底座：霜化玻璃 + 外投影；关闭模糊时底色加厚，避免变成一层没磨砂的透明纸
            GlassPanel.shadow(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.shadow, alpha, 1.15f);
            GlassPanel.frost(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.window,
                    AddonConfig.panelBlur ? 0.62f : 0.94f, alpha);

            canvas.save();
            canvas.clipRRect(RRect.makeXYWH(cardX, cardY, cardW, cardH, cardRadius), true);
            try {
                // 整个窗口就是一块页面：侧栏与内容区之间不再画分隔线

                // 整窗高光：玻璃边缘的细亮线
                GlassPanel.rim(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.rim, alpha, 0.20f);

                FontRenderer.drawTextBold(canvas, "yiyiaddon", cardX + SIDEBAR_PAD_X, headerTitleY, 17f, withAlpha(tc.primaryText, alpha));
                FontRenderer.drawText(canvas, versionLine(), cardX + SIDEBAR_PAD_X, headerSubtitleY, 11f, withAlpha(tc.secondaryText, alpha));
                FontRenderer.drawText(canvas, UiText.t("本扩展免费 为爱发电", "Free for all, made with love"),
                        cardX + SIDEBAR_PAD_X, headerSloganY, 11f, withAlpha(tc.labelTertiary, alpha));
                drawSearchBox(canvas, searchX, searchY, searchW, searchH, alpha, dt, tc);

                // 选中指示块滑到当前导航项，项内文字随滑块位置在普通色与反色之间过渡
                GlassPanel.accentPill(canvas, cardX + 12f, indicatorY, tabW, tabH, NAV_RADIUS, tc, alpha);

                for (int i = 0; i < NAV_KEYS_ZH.length; i++) {
                    float tabY = tabStartY + i * (tabH + tabGap);
                    boolean active = i == router.index();
                    PressState press = navPress[i];
                    press.update(dt);
                    boolean pressed = press.applyAt(canvas, cardX + 12f + tabW / 2f, tabY + tabH / 2f);
                    if (!active && tabHoverAlpha[i] > 0.01f) {
                        hoverPaint.setColor(withAlpha(tc.hoverBackground, ClickGuiThemeColors.panelBackgroundAlpha(alpha * tabHoverAlpha[i])));
                        canvas.drawRRect(RRect.makeXYWH(cardX + 12f, tabY, tabW, tabH, NAV_RADIUS), hoverPaint);
                    }
                    float activated = activationAt(tabY, tabH, tabGap);
                    int iconColor = withAlpha(lerpColor(tc.inactiveIcon, tc.accentOn, activated), alpha);
                    int textColor = withAlpha(lerpColor(tc.inactiveText, tc.accentOn, activated), alpha);
                    FontRenderer.drawText(canvas, NAV_ICONS[i], cardX + 18f, tabY + tabH / 2f + 6f, 13f, iconColor, FontRenderer.MATERIAL_SYMBOLS);
                    FontRenderer.drawText(canvas, UiText.t(NAV_KEYS_ZH[i], NAV_KEYS_EN[i]), cardX + 38f, tabY + tabH / 2f + 6f, 13f, textColor);
                    if (pressed) canvas.restore();
                }

                int closeBgColor = lerpColor(tc.buttonBackground, tc.dangerHoverBackground, closeHoverAlpha);
                int closeTextColor = lerpColor(tc.buttonText, tc.dangerHoverText, closeHoverAlpha);
                int resetBgColor = lerpColor(tc.buttonBackground, tc.dangerHoverBackground, resetHoverAlpha);
                int resetTextColor = lerpColor(tc.buttonText, tc.dangerHoverText, resetHoverAlpha);
                resetPress.update(dt);
                boolean resetPressed = resetPress.apply(canvas, closeX, resetY, tabW, resetH);
                resetBgPaint.setColor(withAlpha(resetBgColor, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
                canvas.drawRRect(RRect.makeXYWH(closeX, resetY, tabW, resetH, NAV_RADIUS), resetBgPaint);
                String resetText = resetConfirm ? UiText.t("再次点击以确认", "Click Again to Confirm") : UiText.t("重置界面设置", "Reset UI Settings");
                if (!resetText.equals(cachedResetText)) {
                    cachedResetText = resetText;
                    cachedResetTextWidth = FontRenderer.measureTextWidth(resetText, 12f);
                }
                float resetTotalW = resetIconWidth + 6f + cachedResetTextWidth;
                float resetStartX = closeX + (tabW - resetTotalW) / 2f;
                FontRenderer.drawText(canvas, "\uE042", resetStartX, resetY + 22f, 13f, withAlpha(resetTextColor, alpha), FontRenderer.MATERIAL_SYMBOLS);
                FontRenderer.drawText(canvas, resetText, resetStartX + resetIconWidth + 6f, resetY + 22f, 12f, withAlpha(resetTextColor, alpha));
                if (resetPressed) canvas.restore();

                closePress.update(dt);
                boolean closePressed = closePress.apply(canvas, closeX, closeY, tabW, closeH);
                closeBgPaint.setColor(withAlpha(closeBgColor, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
                canvas.drawRRect(RRect.makeXYWH(closeX, closeY, tabW, closeH, NAV_RADIUS), closeBgPaint);
                String closeText = UiText.t("× 关闭", "× Close");
                if (!closeText.equals(cachedCloseText)) {
                    cachedCloseText = closeText;
                    cachedCloseTextWidth = FontRenderer.measureTextWidth(closeText, 12f);
                }
                FontRenderer.drawText(canvas, closeText, closeX + (tabW - cachedCloseTextWidth) / 2f, closeY + 22f, 12f, withAlpha(closeTextColor, alpha));
                if (closePressed) canvas.restore();

                if (themePreviewMode) {
                    FontRenderer.drawTextBold(canvas, UiText.t("面板主题", "Panel Theme"), contentX + PAGE_INSET_X, contentY + HEADER_TITLE_Y, 19f, withAlpha(tc.primaryText, alpha));
                    FontRenderer.drawText(canvas, UiText.t("点击缩略图切换面板配色", "Click a thumbnail to switch the panel theme"), contentX + PAGE_INSET_X, contentY + HEADER_SUBTITLE_Y, 11f, withAlpha(tc.secondaryText, alpha));
                } else {
                    if (fadingPage != null) drawPageHeader(canvas, fadingPage, contentX, contentY,
                            contentW, alpha * (1f - pageFade), tc);
                    drawPageHeader(canvas, currentPage, contentX, contentY, contentW, alpha * pageFade, tc);
                }

                if (showVersionHeader()) {
                    versionHeader.draw(canvas, versionHeaderX(contentX, contentW), contentY,
                            alpha * pageFade, layoutMouseX, layoutMouseY, dt,
                            UpdateService.versionLabel(), UpdateService.status());
                }
                backButton.draw(canvas, backButtonX(contentX, contentW), backButtonY(contentY), alpha, tc, backButtonVisible());

                // 内容区：只绘制当前页面，绘制坐标就是布局坐标
                canvas.save();
                canvas.clipRect(Rect.makeXYWH(contentX, contentY + CONTENT_HEADER_H, contentW, contentH - CONTENT_HEADER_H));
                try {
                    if (themePreviewMode) {
                        drawThemePreviewGrid(canvas, contentX, contentY, contentW, alpha, layoutMouseX, layoutMouseY);
                    } else {
                        if (fadingPage != null) {
                            fadingPage.draw(canvas, pageX(contentX), contentY + CONTENT_HEADER_H,
                                    pageW(contentW), contentH - CONTENT_HEADER_H,
                                    alpha * (1f - pageFade), fadingScroll, -Float.MAX_VALUE, -Float.MAX_VALUE);
                        }
                        currentPage.draw(canvas, pageX(contentX), contentY + CONTENT_HEADER_H,
                                pageW(contentW), contentH - CONTENT_HEADER_H, alpha * pageFade, scroll.value(),
                                layoutMouseX, layoutMouseY);
                    }
                } finally {
                    canvas.restore();
                }
                if (!themePreviewMode) {
                    scroll.drawScrollbar(canvas, contentX + contentW - TRACK_INSET, contentY + TRACK_TOP,
                            contentH - TRACK_TOP - TRACK_BOTTOM_PAD, alpha, tc);
                }
                // 浮层画在内容裁剪之外：说明文字不会被动滚动截断（视口按设计空间尺寸反推，与 PanelScreen 一致）
                TooltipLayer.draw(canvas, frame.cardX() * 2f + frame.cardWidth(),
                        frame.cardY() * 2f + frame.cardHeight(), alpha);
            } finally {
                canvas.restore();
            }
        } finally {
            canvas.restore();
        }
    }

    /** 一次局部背景采样同时提供磨砂主体与厚边折射。 */
    private void drawPanelGlass(Canvas canvas, int width, int height, float x, float y,
                                float w, float h, float radius) {
        if (!AddonConfig.panelBlur) return;
        SkiaBlurRenderer.getInstance().render(canvas, glBackend.getContext(), minecraft,
                SkiaGlBackend.mainFramebufferId(), frame.toScreenX(x, width), frame.toScreenY(y, height),
                frame.toScreenLength(w), frame.toScreenLength(h), frame.toScreenLength(radius),
                AddonConfig.blurTintColor(), AddonConfig.blurStrength);
    }

    /** 侧栏页头的版本行：取 fabric.mod.json 中声明的版本号。 */
    private static String versionLine() {
        return "v" + ClientIdentity.version();
    }

    /** 页面内容左边界：绘制、命中与页头标题共用同一处内缩。 */
    private static float pageX(float contentX) {
        return contentX + PAGE_INSET_X;
    }

    /** 页面内容宽度：两侧内缩后剩下的可用宽度。 */
    private static float pageW(float contentW) {
        return contentW - PAGE_INSET_X - PAGE_RESERVED_W;
    }

    /** 内容区头部：页面标题与副标题（基线偏移与侧栏页头共用，左缩进与页面卡片左缘同一处）。 */
    private void drawPageHeader(Canvas canvas, BasePage page, float contentX, float contentY, float contentW,
                                float alpha, ClickGuiThemeColors tc) {
        if (page == null || alpha <= 0.01f) return;
        float titleX = pageX(contentX);
        FontRenderer.drawTextBold(canvas, page.getTitle(), titleX, contentY + HEADER_TITLE_Y, 19f, withAlpha(tc.primaryText, alpha));
        float subtitleWidth = page instanceof HomePage ? contentW - 310f : contentW - 100f;
        FontRenderer.drawText(canvas, CardLayout.ellipsize(page.getSubtitle(), subtitleWidth, 11f),
                titleX, contentY + HEADER_SUBTITLE_Y, 11f, withAlpha(tc.secondaryText, alpha));
    }

    // —— 返回按钮几何 ——

    /** 更新通知只在界面稳定且未输入或拖动时显示，避免打断当前操作。 */
    public boolean canShowUpdatePrompt() {
        return !closingRequested && !searchFocused && !ModuleKeybindManager.isCapturing() && !draggingInContent
                && !draggingScrollbar && frame.animationAlpha() >= 1f;
    }

    private boolean showVersionHeader() {
        return !themePreviewMode && activePage() instanceof HomePage;
    }

    private static float versionHeaderX(float contentX, float contentW) {
        return contentX + contentW - PAGE_RESERVED_W - VersionHeader.WIDTH;
    }

    /** 返回按钮是否可用：主题预览与下钻页面都需要它。 */
    private boolean backButtonVisible() {
        return themePreviewMode || router.canGoBack();
    }

    private static float backButtonX(float contentX, float contentW) {
        return contentX + contentW - 40f;
    }

    private static float backButtonY(float contentY) {
        return contentY + 12f;
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
        float gridY = contentY + CONTENT_HEADER_H + 30f;
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

    private void drawSearchBox(Canvas canvas, float x, float y, float width, float height, float alpha, float dt, ClickGuiThemeColors tc) {
        searchBoxX = x;
        searchBoxY = y;
        searchBoxW = width;
        searchBoxH = height;
        if (searchFocused) {
            // 输入法锚点跟随搜索框（滚动、窗口缩放、开合动画都会让它移位）
            ImeBridge.move(x, y, width, height);
        }
        // 与 SettingTextBox 共用同一份输入框底（唯一定义在 GlassPanel#textField），
        // 侧栏搜索框此前是实心 searchBackground，比同页的霜化玻璃行更暗，像一块贴上去的深色板。
        GlassPanel.textField(canvas, x, y, width, height, 10f, tc, searchFocusAlpha, alpha);

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
            drawSearchPreedit(canvas, cursorX, textX + textW, y, height, alpha, tc);
        }
        canvas.restore();

        float linePulse = 0.3f + 0.7f * (0.5f + 0.5f * (float) Math.sin(searchCursorTime * 6f));
        searchLinePaint.setColor(withAlpha(tc.searchCursor, alpha * searchFocusAlpha * linePulse));
        canvas.drawRect(Rect.makeXYWH(x + 8f, y + height - 2f, width - 16f, 1f), searchLinePaint);

        // 搜索框获得焦点时描一圈强调色焦点环
        GlassPanel.focusRing(canvas, x, y, width, height, 10f, tc.accent, alpha * searchFocusAlpha);
    }

    /**
     * 绘制输入法组合串（拼音等）：接在光标后面，带下划线。
     *
     * <p>组合串由 {@link ImeBridge} 统一持有 —— 输入法接管模组会 cancel 掉原版派发并改画它自己的
     * 全屏浮层，那个浮层位于 GUI 阶段、会被帧末叠加的面板盖住，所以自绘输入框一律自己画。</p>
     */
    private void drawSearchPreedit(Canvas canvas, float cursorX, float limitX, float y, float height, float alpha, ClickGuiThemeColors tc) {
        String composition = ImeBridge.preeditText();
        if (composition == null) return;
        float maxWidth = limitX - cursorX;
        if (maxWidth <= 1f) return;
        canvas.save();
        canvas.clipRect(Rect.makeXYWH(cursorX, y + 2f, maxWidth, height - 4f));
        FontRenderer.drawText(canvas, composition, cursorX, y + 18.5f, 10f, withAlpha(tc.searchText, alpha));
        float underline = Math.min(maxWidth, FontRenderer.measureTextWidth(composition, 10f));
        searchLinePaint.setColor(withAlpha(tc.accent, alpha * 0.85f));
        canvas.drawRect(Rect.makeXYWH(cursorX, y + height - 5f, underline, 1f), searchLinePaint);
        canvas.restore();
    }

    // —— 搜索 ——

    private void applySearch() {
        for (BasePage page : router.roots()) {
            page.setSearchQuery(searchText);
        }
        if (searchText.isBlank()) {
            searchResultsPage = null;
        } else {
            List<SettingModule> results = new ArrayList<>();
            for (BasePage page : router.roots()) {
                for (SettingModule module : page.getModules()) {
                    if (module.isVisible() && module.matchesSearch(searchText)) {
                        results.add(module);
                    }
                }
            }
            searchResultsPage = new SearchResultsPage(searchText, results);
        }
        resetScroll();
    }

    private BasePage activePage() {
        return searchResultsPage == null ? router.current() : searchResultsPage;
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
        resetScroll();
    }

    private void closeThemePreview() {
        themePreviewMode = false;
        resetScroll();
    }

    private void setSearchFocused(boolean focused) {
        if (searchFocused == focused) {
            return;
        }
        searchFocused = focused;
        if (focused) {
            ImeBridge.focus(this, searchBoxX, searchBoxY, searchBoxW, searchBoxH);
        } else {
            ImeBridge.blur();
        }
    }

    // —— 输入 ——

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (ModuleKeybindManager.captureKey(event.key())) {
            return true;
        }
        if (SettingTextBox.keyPressed(event)) {
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
        if (event.isEscape() && router.canGoBack()) {
            // 返回上一级
            router.back();
            resetScroll();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (SettingTextBox.charTyped(event)) {
            return true;
        }
        if (!searchFocused) {
            return super.charTyped(event);
        }
        String typed = event.codepointAsString();
        if (typed != null && !typed.isEmpty()) {
            ImeBridge.clearPreedit();
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
        frame.beginClose();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        if (closingRequested) return false;
        int button = event.button();
        if (ModuleKeybindManager.captureMouseButton(button)) {
            draggingInContent = false;
            draggingScrollbar = false;
            return true;
        }
        if (button > GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            return true;
        }

        float mx = frame.toDesignX(event.x(), this.width);
        float my = frame.toDesignY(event.y(), this.height);
        float[] l = layout();
        float cardX = l[0];
        float sidebarW = l[4], tabStartY = l[5], tabH = l[6], tabGap = l[7], tabW = l[8];
        float closeX = l[9], closeY = l[10], closeH = l[11];
        float resetY = l[12], resetH = l[13];
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];
        float searchX = l[18], searchY = l[19], searchW = l[20], searchH = l[21];
        BasePage page = activePage();

        if (showVersionHeader() && versionHeader.onClick(mx, my,
                versionHeaderX(contentX, contentW), contentY, button)) return true;

        if (button == 0 && mx >= searchX && mx <= searchX + searchW && my >= searchY && my <= searchY + searchH) {
            SettingTextBox.clearFocus();
            themePreviewMode = false;
            setSearchFocused(true);
            searchCursorTime = 0f;
            return true;
        }
        setSearchFocused(false);
        SettingTextBox.clearFocus();

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && backButtonVisible()
                && backButton.hit(mx, my, backButtonX(contentX, contentW), backButtonY(contentY))) {
            backButton.press();
            if (themePreviewMode) {
                closeThemePreview();
            } else {
                router.back();
                resetScroll();
            }
            return true;
        }

        for (int i = 0; i < NAV_KEYS_ZH.length; i++) {
            float ty = tabStartY + i * (tabH + tabGap);
            if (mx >= cardX + 12f && mx <= cardX + 12f + tabW && my >= ty && my <= ty + tabH) {
                if (button == 0) {
                    navPress[i].press();
                    clearSearch();
                    themePreviewMode = false;
                    router.select(i);
                    resetScroll();
                }
                return true;
            }
        }

        if (button == 0 && mx >= closeX && mx <= closeX + tabW && my >= closeY && my <= closeY + closeH) {
            closePress.press();
            closingRequested = true;
            frame.beginClose();
            return true;
        }

        if (button == 0 && mx >= closeX && mx <= closeX + tabW && my >= resetY && my <= resetY + resetH) {
            resetPress.press();
            if (resetConfirm) {
                themePreviewMode = false;
                resetUiSettings();
                resetConfirm = false;
                rebuildCurrentPage();
            } else {
                resetConfirm = true;
            }
            return true;
        }

        resetConfirm = false;

        if (mx >= contentX && mx <= contentX + contentW && my >= contentY && my <= contentY + contentH) {
            if (themePreviewMode) {
                float gridX = contentX + (contentW - previewGridWidth()) / 2f;
                float gridY = contentY + CONTENT_HEADER_H + 30f;
                for (int i = 0; i < previewThemes.size(); i++) {
                    float cx = previewThumbX(gridX, i);
                    float cy = previewThumbY(gridY, i);
                    if (mx >= cx - 6f && mx <= cx + THUMB_SIZE + 6f && my >= cy - 6f && my <= cy + THUMB_SIZE + 26f) {
                        ClickGuiTheme theme = previewThemes.get(i);
                        if (!theme.id().equals(ClickGuiThemeManager.currentId())) {
                            ClickGuiThemeManager.selectAndSave(theme.id());
                        }
                        return true;
                    }
                }
                return true;
            }
            refreshScroll();
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && scroll.hasScrollbar()
                    && scroll.isInTrack(mx, my, contentX + contentW - TRACK_INSET, contentY + TRACK_TOP,
                    contentH - TRACK_TOP - TRACK_BOTTOM_PAD)) {
                draggingScrollbar = true;
                scroll.beginDrag(my, contentY + TRACK_TOP, contentH - TRACK_TOP - TRACK_BOTTOM_PAD);
                return true;
            }
            float moduleStartY = contentY + CONTENT_HEADER_H;
            boolean hit = page.onClick(mx, my, pageX(contentX), moduleStartY, pageW(contentW),
                    scroll.value(), button);
            if (hit && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                draggingInContent = true;
            }
            if (hit && activePage() != page) {
                // 下钻到新的页面：滚动复位到顶部，避免新页面沿用上一页的滚动位置
                resetScroll();
            }
            return hit;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        float my = frame.toDesignY(event.y(), this.height);
        float[] l = layout();
        if (draggingScrollbar) {
            float contentY = l[15], contentH = l[17];
            float trackTop = contentY + TRACK_TOP;
            float trackHeight = contentH - TRACK_TOP - TRACK_BOTTOM_PAD;
            refreshScroll();
            scroll.dragTo(my, trackTop, trackHeight);
            return true;
        }
        if (draggingInContent) {
            float mx = frame.toDesignX(event.x(), this.width);
            float contentX = l[14], contentY = l[15], contentW = l[16];
            activePage().onDrag(mx, my, pageX(contentX), contentY + CONTENT_HEADER_H,
                    pageW(contentW), scroll.value());
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        draggingInContent = false;
        draggingScrollbar = false;
        // 松开鼠标：所有按压元素进入回弹
        for (PressState press : navPress) press.release();
        closePress.release();
        resetPress.release();
        backButton.release();
        BasePage page = activePage();
        if (page != null) {
            page.releasePress();
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double hScroll, double vScroll) {
        if (themePreviewMode) return false;
        float layoutMx = frame.toDesignX(mx, this.width);
        float layoutMy = frame.toDesignY(my, this.height);
        float[] l = layout();
        float contentX = l[14], contentY = l[15], contentW = l[16], contentH = l[17];

        if (layoutMx >= contentX && layoutMx <= contentX + contentW && layoutMy >= contentY && layoutMy <= contentY + contentH) {
            refreshScroll();
            scroll.scrollBy(vScroll, AddonConfig.scrollSpeed);
            return true;
        }
        return false;
    }

    /** 恢复界面设置的默认值。 */
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
