package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.yiyiaddon.ui.render.SkiaBlurRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 为世界空间叠加层（ESP）的 2D 部分提供绘制时机。
 *
 * <p>26.1.2 的一帧顺序是：{@code GameRenderer.render} 先画世界（{@code renderLevel}），
 * 再调用 {@code GuiRenderer.render} 画原版 GUI，最后 {@code mainRenderTarget.blitToScreen} 呈现。
 * 在 {@code GuiRenderer#render} 的 HEAD 注入，可以拿到「世界已画完、GUI 未画」的主 Framebuffer，
 * 于是叠加的字牌压在世界之上、被 GUI 正常遮挡。</p>
 *
 * <p>ESP 的几何不在这里画：框 / 线 / 面已交给原版 gizmo（世界空间 GPU 渲染），
 * 由 {@code WorldOverlay} 注册到 Fabric {@code LevelRenderEvents.BEFORE_GIZMOS}。
 * 本注入点只负责把几何阶段排队的 2D 元素（字牌等）叠上去。</p>
 *
 * <p>注入点位于原版 GUI 的 RenderPass 创建之前，因此不会与 GUI 的渲染通道状态冲突。</p>
 */
@Mixin(GuiRenderer.class)
public abstract class GuiRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void yiyiaddon$renderWorldOverlay(GpuBufferSlice fogBuffer, CallbackInfo callbackInfo) {
        // 先取「世界帧」：此刻主帧缓冲只有世界（界面还没上屏），是面板玻璃唯一干净的背景来源。
        // 顺序必须在叠加层之前 —— 否则截到的世界帧里会带上 ESP 字牌。
        SkiaBlurRenderer.getInstance().captureWorldFrame();
        WorldOverlay.renderOverlay();
    }
}
