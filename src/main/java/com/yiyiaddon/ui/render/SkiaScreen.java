package com.yiyiaddon.ui.render;

import com.yiyiaddon.config.AddonConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Skija 直接绘制型界面的基类。
 *
 * <p>26.1.2 的界面管线是「extract 阶段收集绘制指令」，与 Skija 的即时 GL 绘制模型不兼容。
 * 因此这里把界面拆成两段：{@link #extractRenderState} 只记录本帧的鼠标位置与时间片，
 * 真正的 Skija 绘制延后到帧末由 {@code RenderTargetMixin} 调用 {@link #renderSkiaFrame()}，
 * 此时主 Framebuffer 已包含世界与 HUD，可以对其做区域模糊后再叠加面板。</p>
 */
public abstract class SkiaScreen extends Screen {

    protected final Screen parent;

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
        // 局部玻璃在帧末只采样面板区域；这里不再模糊整屏，否则折射边缘与主体失去差异。
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
            ItemIconCache.getInstance().flushBackdrop();
            return;
        }
        framePending = false;
        // 先截取图标，再画面板：截取要求隐藏格子仍是主 Framebuffer 的最上层内容。
        ItemIconCache.getInstance().capturePending();
        // 截完<b>立刻</b>把画面备份写回，不再依赖「面板绘制路径里那一次写回」。
        //
        // 那条路径是 {@code SkiaGlBackend#begin} → {@code paintBackdrop}，它有两个前提：这一帧真的走到
        // 面板绘制、且 {@code begin} 拿得到 canvas。只要有一帧不满足（换屏、surface 重建、面板这一帧
        // 没画），格子就裸露在屏幕上；更糟的是<b>下一帧的备份会把这份裸露当成「干净画面」备份下来</b>，
        // 于是残影被反复备份、反复画回、每帧再过一次面板玻璃的模糊 —— 越糊越久。用户 2026-09-22 截图里
        // 那条横贯面板的光带（两端还露着格子底色的黑方块）就是这么滚出来的。
        //
        // 放在这里是无条件的一步：本方法的调用点（帧末 Mixin）必然在格子落地之后、面板绘制之前，
        // 既保证格子被盖住，也保证面板玻璃采样到的是干净画面。原有的两处写回（begin / flushBackdrop）
        // 此时已无备份可取，自动退化为空操作。
        ItemIconCache.getInstance().flushBackdrop();
        drawFrame(this.width, this.height, frameMouseX, frameMouseY, frameDelta);
        // 这里<b>不能</b>再补一次写回：写回只能由面板绘制路径（SkiaGlBackend#begin → paintBackdrop）
        // 在面板自己画之前完成。实测把写回挪到 drawFrame 之后，它会把上一帧备份（=面板自己的像素，
        // 平均 (20,22,27)）重新糊在面板上面 —— 屏幕正中那块比周围明显更暗的方块，并逐帧加深
        // (25→21→17→…→7，比值 0.83 正是玻璃透光率) 收敛到全黑。
        // 用户 2026-09-22「选择器一打开，屏幕正中一块纯黑」就是这么来的。
        // 换屏/关屏那两种收尾仍由本方法开头的跳过分支与 RenderTargetMixin 兜住。
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
