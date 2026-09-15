package com.yiyiaddon.ui.render;

import com.yiyiaddon.config.AddonConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
        frameMouseX = mouseX;
        frameMouseY = mouseY;
        frameDelta = partialTick;
        framePending = true;
        // 把本帧待加载的物品图标画进屏幕中心的隐藏格子，帧末截取成贴图后即可被面板覆盖。
        ItemIconCache.getInstance().renderPending(graphics);
        // 局部玻璃在帧末只采样面板区域；这里不再模糊整屏，否则折射边缘与主体失去差异。
    }

    /** 由帧末 Mixin 在主 Framebuffer blit 之前调用。 */
    public final void renderSkiaFrame() {
        if (!framePending || this.minecraft == null || this.minecraft.screen != this) {
            framePending = false;
            return;
        }
        framePending = false;
        // 每帧重申输入法开关：原版的文本输入管理会把非输入状态的 IME 关掉，
        // 只在聚焦那一刻设置会被它抢回去 —— 表现就是「拼音敲进去只有英文字母」。
        ImeBridge.keepAlive();
        // 先截取图标，再画面板：截取要求隐藏格子仍是主 Framebuffer 的最上层内容。
        ItemIconCache.getInstance().capturePending();
        drawFrame(this.width, this.height, frameMouseX, frameMouseY, frameDelta);
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
            this.minecraft.setScreen(this.parent);
        }
    }
}
