package com.yiyiaddon.mixin.client;

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
 * <p><b>26.2 口径（本注入点由 {@code RenderTargetMixin} 迁移而来）</b>：26.1.2 的呈现入口是
 * {@code mainRenderTarget.blitToScreen()}（{@code RenderTarget} 上的方法），因此当时注入
 * {@code RenderTarget#blitToScreen} 的 HEAD 并用「{@code this == 主 RenderTarget}」筛出主画面。
 * 26.2 把 {@code blitToScreen} 整个移除，呈现改为 {@code Minecraft#renderFrame} 里的
 * {@code windowSurface.blitFromTexture(命令编码器, 主颜色纹理视图)} 加 {@code GpuSurface#present}。
 * 于是注入点改为「{@code renderFrame} 中那一句 {@code blitFromTexture} 调用之前」——
 * 此刻世界与 GUI 都已画进主 RenderTarget、尚未提交呈现，与改动前的时机逐帧等价。</p>
 *
 * <p>注：{@code RenderTarget#blitAndBlendToTexture} 不是呈现路径（它由 {@code LevelRenderer} 用于
 * 半透明层回填主画面），因此不能拿它替代 {@code blitToScreen} 当注入点。</p>
 *
 * <p>只在当前界面是 {@link SkiaScreen} 时执行，其余情况完全等同原版行为。</p>
 */
@Mixin(Minecraft.class)
public abstract class MinecraftFramePresentMixin {

    @Inject(
            method = "renderFrame",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/GpuSurface;blitFromTexture(Lcom/mojang/blaze3d/systems/CommandEncoder;Lcom/mojang/blaze3d/textures/GpuTextureView;)V"
            )
    )
    private void yiyiaddon$renderSkijaFrame(boolean advanceGameTime, CallbackInfo callbackInfo) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) {
            return;
        }
        // 与紧随其后的原版判断一致：颜色纹理还没建好时原版自己会抛错，这里不抢先画
        if (client.gameRenderer.mainRenderTarget().getColorTextureView() == null) {
            return;
        }
        if (client.gui.screen() instanceof SkiaScreen skiaScreen) {
            skiaScreen.renderSkiaFrame();
        } else {
            // 本帧换成了原版界面（或界面已关闭）而我们之前画过隐藏格子：备份必须收尾，
            // 否则那两行放大的原版图标会裸露到下一帧（与 SkiaScreen#renderSkiaFrame 的跳过分支同源）。
            // 这一帧不会再画面板，写回后直接释放备份。
            ItemIconCache.getInstance().finishBackdropFrame();
        }
    }
}
