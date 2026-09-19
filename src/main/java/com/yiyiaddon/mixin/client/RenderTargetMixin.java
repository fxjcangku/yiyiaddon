package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.SkiaScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 为 Skija 界面提供「帧末直绘」时机。
 *
 * <p>26.1.2 的执行顺序是：{@code gameRenderer.render(...)} 把世界与 GUI 画进主 RenderTarget，
 * 紧接着 {@code mainRenderTarget.blitToScreen()} 呈现到屏幕。因此在
 * {@code RenderTarget#blitToScreen} 的 HEAD 注入，可以拿到包含世界与 HUD 的完整主 Framebuffer，
 * 做区域模糊并叠加面板，最后仍由原版完成呈现。</p>
 *
 * <p>只在「被呈现的目标就是主 RenderTarget」且当前界面是 {@link SkiaScreen} 时执行，
 * 其余情况完全等同原版行为。</p>
 */
@Mixin(RenderTarget.class)
public abstract class RenderTargetMixin {

    @Inject(method = "blitToScreen", at = @At("HEAD"))
    private void yiyiaddon$renderSkijaFrame(CallbackInfo callbackInfo) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) {
            return;
        }
        if ((Object) this != client.getMainRenderTarget()) {
            return;
        }
        if (client.screen instanceof SkiaScreen skiaScreen) {
            skiaScreen.renderSkiaFrame();
        } else {
            // 本帧换成了原版界面（或界面已关闭）而我们之前画过隐藏格子：备份必须收尾，
            // 否则那两行放大的原版图标会裸露到下一帧（与 SkiaScreen#renderSkiaFrame 的跳过分支同源）。
            ItemIconCache.getInstance().flushBackdrop();
        }
    }
}
