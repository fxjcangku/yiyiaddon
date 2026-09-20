package com.yiyiaddon.ui.render;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Skija 直接绘制型界面的基类。
 *
 * <p>26.1.2 的界面管线是「extract 阶段收集绘制指令」，与 Skija 的即时 GL 绘制模型不兼容。
 * 因此这里把界面拆成两段：{@link #extractRenderState} 只记录本帧的鼠标位置与时间片，
 * 真正的 Skija 绘制延后到帧末由 {@code MinecraftFramePresentMixin} 调用 {@link #renderSkiaFrame()}，
 * 此时主 Framebuffer 已包含世界与 HUD，可以对其做区域模糊后再叠加面板。</p>
 */
public abstract class SkiaScreen extends Screen {

    protected final Screen parent;

    /**
     * 面板玻璃矩形四周的外扩量（GUI 逻辑像素）：容下模糊半径与开合动画期间面板一帧的位移。
     *
     * <p>玻璃背景取的是「世界帧」（GUI 通道之前的画面，见 {@code SkiaBlurRenderer#requestWorldFrame}），
     * 而截取发生在抽帧之后、界面绘制之前，因此这里登记时多留一圈，动画期间也不会采到面板之外。</p>
     */
    protected static final float GLASS_MARGIN = 48f;

    private boolean framePending;
    private int frameMouseX;
    private int frameMouseY;
    private float frameDelta;

    protected SkiaScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // 输入法锚点（ImeBridge 的隐形原版输入框）必须每帧真的走到绘制阶段：
        // 输入法接管模组用「这一帧渲染过没有」判断文本框是否还在界面上，跳过绘制就等于告诉它
        // 「输入框已经不在了」，它会立刻把焦点和输入法一起收走。
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        frameMouseX = mouseX;
        frameMouseY = mouseY;
        frameDelta = partialTick;
        framePending = true;
        // 把本帧待加载的物品图标画进屏幕中心的隐藏格子，帧末截取成贴图后即可被面板覆盖。
        if (canCaptureIcons()) {
            ItemIconCache.getInstance().renderPending(graphics);
        }
        // 登记本帧面板玻璃要采样的世界帧区域：抽帧早于「世界画完、GUI 未画」那个截取点，
        // 所以第一帧画面板也有干净背景可采（否则那一帧会退回现场采样，闪一下自反馈的蓝雾）。
        if (AddonConfig.panelBlur) {
            float[] glass = glassRegion();
            if (glass != null) {
                SkiaBlurRenderer.getInstance().requestWorldFrame(glass[0], glass[1], glass[2], glass[3]);
            }
        }
        // 局部玻璃在帧末只采样面板区域；这里不再模糊整屏，否则折射边缘与主体失去差异。
    }

    /**
     * 本帧面板玻璃要采样的矩形（GUI 逻辑坐标，{@link #GLASS_MARGIN} 外扩请用
     * {@link #glassRegionOf}）；返回 {@code null} 表示本帧不取世界帧。
     *
     * <p>画面板的界面覆写它；不覆写时玻璃退回现场采样（会形成自反馈，浅色面板下是那团蓝雾）。</p>
     */
    protected float[] glassRegion() {
        return null;
    }

    /** 把面板矩形按 {@link #GLASS_MARGIN} 外扩成登记用的玻璃区域。 */
    protected static float[] glassRegionOf(float x, float y, float width, float height) {
        return new float[]{x - GLASS_MARGIN, y - GLASS_MARGIN,
                width + GLASS_MARGIN * 2f, height + GLASS_MARGIN * 2f};
    }

    /**
     * 环境压暗：把世界压暗一档（黑主题 0.16 / 浅色 0.10），让玻璃与场景拉开景深。
     *
     * <p><b>必须画在玻璃之前</b>，帧内顺序是「写回隐藏格子备份 → 环境压暗 → 玻璃 → 面板底 / 内容」。
     * 玻璃是不透明的，落在面板区域的那部分压暗会被它整个盖住，面板深浅只由玻璃的主题色蒙版与面板底
     * 决定（两者都不随帧累积）。反过来画在玻璃之后就麻烦：压暗压在面板上，并且会跟「唯一没被压暗的
     * 那块」——隐藏格子矩形——显出对比，用户 2026-09-20「屏幕正中一块纯黑」、2026-09-21「现在有白块」
     * 都是它。</p>
     */
    protected static void ambientDim(Canvas canvas, int width, int height, ClickGuiThemeColors tc, float alpha) {
        float dim = alpha * (tc.dark ? 0.16f : 0.10f);
        if (dim <= 0.01f) return;
        GlassPanel.fill(canvas, 0f, 0f, width, height, 0f, tc.shadow, dim);
    }

    /** 挂上输入法锚点：只进 renderables、不进 children —— 它不接收事件，只参与焦点体系。 */
    void attachImeSink(AbstractWidget widget) {
        removeWidget(widget);
        addRenderableOnly(widget);
    }

    /** 摘下输入法锚点。 */
    void detachImeSink(AbstractWidget widget) {
        removeWidget(widget);
    }

    /**
     * 本帧是否允许向主帧缓冲写「隐藏格子」并回读画面备份。
     *
     * <p>默认允许。面板类界面在开合动画期间必须返回 {@code false}：画面备份取自<b>上一帧</b>的
     * 主帧缓冲（抽帧阶段读，那时上一帧画面还在，本帧还没清屏），而上一帧的面板还在另一档缩放与
     * 位移上，把它画回去之后新面板（更小、且半透明）盖不住它，于是旧面板的文字与新面板错位叠在
     * 一起 —— 就是用户 2026-09-16 反馈的「字糊了」（动画一结束两帧面板完全重合，糊味自动消失）。</p>
     */
    protected boolean canCaptureIcons() {
        return true;
    }

    /** 由帧末 Mixin 在主 Framebuffer blit 之前调用。 */
    public final void renderSkiaFrame() {
        if (!framePending || this.minecraft == null || this.minecraft.gui.screen() != this) {
            framePending = false;
            // 这一帧不画面板，但隐藏格子可能已经落地：本帧抽帧时还轮得到本界面（画了格子），
            // 帧末却已经换了界面 —— 两边都不收尾，屏幕中央那两行放大的原版图标会裸露一帧
            // （用户 2026-09-19：「点开选择器之后会闪出来原版贴图的放大版，闪了一下，偶尔发生」）。
            // 这一帧不会再画面板，写回后直接释放备份，免得旧备份贴到后续帧上。
            ItemIconCache.getInstance().finishBackdropFrame();
            return;
        }
        framePending = false;
        // 先截取图标，再画面板：截取要求隐藏格子仍是主 Framebuffer 的最上层内容。
        ItemIconCache.getInstance().capturePending();
        // 兜底写回一次（幂等）：主路径是接下来的 drawFrame → SkiaGlBackend#beginScreenFrame
        // （界面自己的后端 + 同表面 + flush），这里先用共享后端补一次，覆盖「界面这一帧取不到画布」
        // 等异常情形。格子裸露的观感见 ItemIconCache#paintBackdrop 注释。
        ItemIconCache.getInstance().flushBackdrop();
        drawFrame(this.width, this.height, frameMouseX, frameMouseY, frameDelta);
        // 这里<b>不能</b>再补一次写回：写回只能由面板绘制路径（SkiaGlBackend#begin → paintBackdrop）
        // 在面板自己画之前完成。实测把写回挪到 drawFrame 之后，它会把上一帧备份（=面板自己的像素，
        // 平均 (20,22,27)）重新糊在面板上面 —— 屏幕正中那块比周围明显更暗的方块，并逐帧加深
        // (25→21→17→…→7，比值 0.83 正是玻璃透光率) 收敛到全黑。
        // 用户 2026-09-22「选择器一打开，屏幕正中一块纯黑」就是这么来的。
        // 换屏/关屏那两种收尾仍由本方法开头的跳过分支与 MinecraftFramePresentMixin 兜住。
    }

    /** 本帧的 Skija 绘制。canvas 已按 GUI Scale 缩放，坐标系为 GUI 逻辑坐标。 */
    protected abstract void drawFrame(int width, int height, int mouseX, int mouseY, float delta);

    @Override
    protected void init() {
        super.init();
        requestRedraw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        requestRedraw();
    }

    /** 供子类在内容变化时通知重绘。 */
    protected void requestRedraw() {
    }

    /**
     * 背景：在存档内保持透明（面板下方由 Skija 模糊原画面），
     * 在主菜单等没有世界的情况下退化为全景图，避免出现纯黑底。
     */
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft != null && this.minecraft.level == null) {
            this.extractPanorama(graphics, partialTick);
        } else {
            this.extractTransparentBackground(graphics);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.closing();
    }

    protected void closing() {
        if (this.minecraft != null) {
            this.minecraft.gui.setScreen(this.parent);
        }
    }
}
