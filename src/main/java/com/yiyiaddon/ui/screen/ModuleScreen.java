package com.yiyiaddon.ui.screen;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.BackButton;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.PanelFrame;
import com.yiyiaddon.ui.component.ScrollViewport;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.ModuleDetailPage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ImeBridge;
import com.yiyiaddon.ui.render.SkiaGlBackend;
import com.yiyiaddon.ui.render.SkiaBlurRenderer;
import com.yiyiaddon.ui.render.SkiaScreen;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

/**
 * 模块独立页面屏幕。
 *
 * <p>点击模块卡片后单独打开本屏幕，不再在模块中心内部翻页：左侧没有主导航，内容只属于
 * 该模块。页面内容由模块自己的 {@link ModulePage} 提供，未接入页面的模块落到
 * {@link ModuleDetailPage} 占位页。圆角与玻璃材质与主界面共用 {@link PanelFrame} 与
 * {@link GlassPanel}，不另写一份。</p>
 *
 * <p>设计尺寸与主界面完全一致（{@link PanelFrame#CARD_W} x {@link PanelFrame#CARD_H}），因此
 * 两边的面板尺寸、缩放、圆角与玻璃完全一致。内容按设计高度单列排满，超出面板可用高度时由
 * {@link ScrollViewport} 滚动，不压缩行高、不缩小字号。</p>
 *
 * <p>返回按钮与 ESC 都回到构造时传入的上级屏幕（模块列表所在的主界面）。</p>
 */
public final class ModuleScreen extends SkiaScreen {

    /** 内容区左右留白。 */
    private static final float CONTENT_PAD = 18f;
    /** 内容区顶部（标题区下方）。 */
    private static final float CONTENT_TOP = 70f;
    /** 返回按钮相对面板左上角的位置。 */
    private static final float BACK_X = 18f;
    private static final float BACK_Y = 16f;
    /** 标题相对面板左上角的位置。 */
    private static final float TITLE_X = BACK_X + BackButton.SIZE + 12f;
    /** 有副标题时的标题基线：标题与副标题上下成组，占的是同一个标题区。 */
    private static final float TITLE_Y = 27f;
    private static final float TITLE_SIZE = 19f;
    /**
     * 标题基线比例（文字视觉中心 = 基线 − 字号 × 本比例）：与 {@code TextLine}、面板窗口同一口径。
     */
    private static final float TITLE_BASELINE_RATIO = 0.36f;
    /**
     * 页面没有副标题时的标题基线：与返回按钮垂直居中。
     *
     * <p>口径同 {@code PanelScreen#TITLE_Y_ALIGNED}（用户 2026-09-19：「所有标题都要这样对齐返回键
     * 除了底下有介绍的不用」）—— 没有说明文字的模块页，标题只占返回键那一行，就该与它同轴。</p>
     */
    private static final float TITLE_Y_ALIGNED =
            BACK_Y + BackButton.SIZE / 2f + TITLE_SIZE * TITLE_BASELINE_RATIO;
    private static final float SUBTITLE_Y = 44f;
    /** 页面内容左内缩与右侧为滚动条预留的宽度。 */
    private static final float PAGE_INSET_X = 10f;
    private static final float PAGE_RESERVED_W = 40f;
    /** 内容底部留白。 */
    private static final float CONTENT_BOTTOM_PAD = 8f;
    /** 滚动条相对内容区的位置。 */
    private static final float TRACK_INSET = 8f;
    private static final float TRACK_TOP_PAD = 4f;

    private final BasePage page;
    private final PanelFrame frame = new PanelFrame();
    private final ScrollViewport scroll = new ScrollViewport();
    private final BackButton backButton = new BackButton();
    private final SkiaGlBackend glBackend = new SkiaGlBackend();

    private boolean closingRequested;
    private boolean draggingInContent;
    private boolean draggingScrollbar;
    private long lastRenderMs;

    public ModuleScreen(ModuleEntry entry, Screen parent) {
        super(Component.literal(entry.displayName()), parent);
        this.page = createPageBody(entry);
    }

    /** 取模块自己的页面；未接入页面时使用占位页。 */
    private static BasePage createPageBody(ModuleEntry entry) {
        Supplier<ModulePage> factory = entry.page();
        ModulePage modulePage = factory == null ? null : factory.get();
        return modulePage == null ? new ModuleDetailPage(entry) : modulePage.createPage(entry);
    }

    @Override
    protected void init() {
        super.init();
        // 先算一次几何，输入处理不必等第一帧
        frame.update(minecraft, 0f);
    }

    /** 内容区设计高度；绘制、滚动上限与命中测试共用同一来源。 */
    private float contentHeight() {
        return frame.cardHeight() - CONTENT_TOP - CONTENT_PAD;
    }

    /** 面板停在最终位置后才动隐藏格子；动画期间的画面备份与当前面板对不齐，会透出正文重影。 */
    @Override
    protected boolean canCaptureIcons() {
        return frame.animationAlpha() >= 1f;
    }

    // —— 绘制 ——

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

    private void drawPanel(Canvas canvas, int width, int height, int mouseX, int mouseY) {
        TooltipLayer.beginFrame();
        long now = System.currentTimeMillis();
        float dt = lastRenderMs == 0L ? 0.016f : Math.min((now - lastRenderMs) / 1000f, 0.033f);
        lastRenderMs = now;

        if (frame.update(minecraft, dt)) {
            // 关闭动画播完，交回上级界面
            super.closing();
            return;
        }

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float alpha = frame.animationAlpha();
        float cardRadius = frame.cardRadius();
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float cardW = frame.cardWidth();
        float cardH = frame.cardHeight();
        float designMouseX = frame.toDesignX(mouseX, width);
        float designMouseY = frame.toDesignY(mouseY, height);

        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = cardW - CONTENT_PAD * 2f;
        float contentH = contentHeight();
        boolean backVisible = !closingRequested;

        backButton.update(designMouseX, designMouseY, cardX + BACK_X, cardY + BACK_Y, dt, backVisible);
        refreshScroll(contentH);
        scroll.update(dt);
        page.update(dt);

        if (AddonConfig.panelBlur) {
            SkiaBlurRenderer.getInstance().render(canvas, glBackend.getContext(), minecraft,
                    SkiaGlBackend.mainFramebufferId(), frame.toScreenX(cardX, width),
                    frame.toScreenY(cardY, height), frame.toScreenLength(cardW),
                    frame.toScreenLength(cardH), frame.toScreenLength(cardRadius),
                    AddonConfig.blurTintColor(), AddonConfig.blurStrength);
        }

        // 轻微环境压暗提升玻璃与游戏场景的景深差异，不影响面板内文字对比度。
        GlassPanel.fill(canvas, 0f, 0f, width, height, 0f, tc.shadow,
                alpha * (tc.dark ? 0.16f : 0.10f));

        // 面板变换、窗口裁剪、内容裁剪三层 save 与 finally 中的三次 restore 严格配对
        canvas.save();
        frame.applyTransform(canvas, width, height);
        try {
            GlassPanel.shadow(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.shadow, alpha, 1.15f);
            GlassPanel.frost(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.window,
                    AddonConfig.panelBlur ? 0.62f : 0.94f, alpha);

            canvas.save();
            canvas.clipRRect(RRect.makeXYWH(cardX, cardY, cardW, cardH, cardRadius), true);
            try {
                GlassPanel.ambientGlow(canvas, cardX, cardY, cardW, cardH, tc, alpha, 0.46f);
                GlassPanel.rim(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.rim, alpha, 0.26f);
                drawHeader(canvas, cardX, cardY, contentW, alpha, tc, backVisible);

                canvas.save();
                canvas.clipRect(Rect.makeXYWH(contentX, contentY, contentW, contentH));
                try {
                    page.draw(canvas, contentX + PAGE_INSET_X, contentY, contentW - PAGE_RESERVED_W, contentH,
                            alpha, scroll.value(), designMouseX, designMouseY);
                } finally {
                    canvas.restore();
                }
                scroll.drawScrollbar(canvas, contentX + contentW - TRACK_INSET, contentY + TRACK_TOP_PAD,
                        contentH - TRACK_TOP_PAD * 2f, alpha, tc);
            } finally {
                canvas.restore();
            }
            // 浮层在面板变换内、内容裁剪外绘制：不会被内容裁掉，也不被后续行覆盖。
            // 视口取设计空间屏幕尺寸（PanelFrame 未暴露，按其居中几何 cardX=(designW-cardW)/2 反推）。
            TooltipLayer.draw(canvas, frame.cardX() * 2f + frame.cardWidth(),
                    frame.cardY() * 2f + frame.cardHeight(), alpha);
        } finally {
            canvas.restore();
        }
    }

    /** 标题区：返回按钮、模块名与副标题（没有副标题时标题与返回按钮同轴，见 {@link #TITLE_Y_ALIGNED}）。 */
    private void drawHeader(Canvas canvas, float cardX, float cardY, float contentW, float alpha,
                            ClickGuiThemeColors tc, boolean backVisible) {
        String subtitle = page.getSubtitle();
        boolean hasSubtitle = subtitle != null && !subtitle.isEmpty();
        backButton.draw(canvas, cardX + BACK_X, cardY + BACK_Y, alpha, tc, backVisible);
        FontRenderer.drawTextBold(canvas, page.getTitle(), cardX + TITLE_X,
                cardY + (hasSubtitle ? TITLE_Y : TITLE_Y_ALIGNED), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));
        FontRenderer.drawText(canvas, CardLayout.ellipsize(subtitle, contentW - 120f, 11f),
                cardX + TITLE_X, cardY + SUBTITLE_Y, 11f, GlassPanel.withAlpha(tc.secondaryText, alpha));
    }

    /** 按当前页面的内容总高刷新滚动上限。 */
    private void refreshScroll(float contentH) {
        scroll.layout(page.getTotalHeight() + page.getVisibleSpacing() + CONTENT_BOTTOM_PAD, contentH);
    }

    // —— 输入 ——

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        if (closingRequested) return false;
        if (ModuleKeybindManager.captureMouseButton(event.button())) {
            draggingInContent = false;
            draggingScrollbar = false;
            return true;
        }
        if (event.button() > GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            return true;
        }

        float mouseX = frame.toDesignX(event.x(), this.width);
        float mouseY = frame.toDesignY(event.y(), this.height);
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && backButton.hit(mouseX, mouseY, cardX + BACK_X, cardY + BACK_Y)) {
            backButton.press();
            requestClose();
            return true;
        }

        SettingTextBox.clearFocus();

        if (mouseX >= contentX && mouseX <= contentX + contentW && mouseY >= contentY && mouseY <= contentY + contentH) {
            float trackTop = contentY + TRACK_TOP_PAD;
            float trackHeight = contentH - TRACK_TOP_PAD * 2f;
            refreshScroll(contentH);
            if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT && scroll.hasScrollbar()
                    && scroll.isInTrack(mouseX, mouseY, contentX + contentW - TRACK_INSET, trackTop, trackHeight)) {
                draggingScrollbar = true;
                scroll.beginDrag(mouseY, trackTop, trackHeight);
                return true;
            }
            boolean hit = page.onClick(mouseX, mouseY, contentX + PAGE_INSET_X, contentY,
                    contentW - PAGE_RESERVED_W, scroll.value(), event.button());
            if (hit && event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                draggingInContent = true;
            }
            return hit;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        float mouseX = frame.toDesignX(event.x(), this.width);
        float mouseY = frame.toDesignY(event.y(), this.height);
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (draggingScrollbar) {
            refreshScroll(contentH);
            float trackTop = contentY + TRACK_TOP_PAD;
            scroll.dragTo(mouseY, trackTop, contentH - TRACK_TOP_PAD * 2f);
            return true;
        }
        if (draggingInContent) {
            page.onDrag(mouseX, mouseY, contentX + PAGE_INSET_X, contentY,
                    contentW - PAGE_RESERVED_W, scroll.value());
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        draggingInContent = false;
        draggingScrollbar = false;
        backButton.release();
        page.releasePress();
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        float designX = frame.toDesignX(mouseX, this.width);
        float designY = frame.toDesignY(mouseY, this.height);
        float contentX = frame.cardX() + CONTENT_PAD;
        float contentY = frame.cardY() + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (designX >= contentX && designX <= contentX + contentW
                && designY >= contentY && designY <= contentY + contentH) {
            refreshScroll(contentH);
            scroll.scrollBy(vertical, AddonConfig.scrollSpeed);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (ModuleKeybindManager.captureKey(event.key())) return true;
        if (SettingTextBox.keyPressed(event)) return true;
        // ESC 由父类转交 onClose，因此返回上级界面
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (SettingTextBox.charTyped(event)) return true;
        return super.charTyped(event);
    }

    @Override
    public boolean preeditUpdated(PreeditEvent event) {
        SettingTextBox.onPreedit(event);
        return true;
    }

    // —— 关闭 ——

    /** 播放关闭动画，动画结束后回到上级界面。 */
    private void requestClose() {
        if (closingRequested) return;
        closingRequested = true;
        SettingTextBox.clearFocus();
        backButton.cancel();
        frame.beginClose();
    }

    @Override
    public void onClose() {
        requestClose();
    }

    @Override
    public void removed() {
        SettingTextBox.clearFocus();
        ImeBridge.reset();
        glBackend.destroy();
        super.removed();
    }
}
