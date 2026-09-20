package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.opengl.GlRenderPass;
import com.yiyiaddon.compat.MissingSamplerFallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

/**
 * 给原版「draw 前校验采样器绑定」这一步补上两类兜底，避免 {@code IllegalStateException}
 * 直接崩客户端：
 *
 * <ol>
 *   <li>{@code Missing sampler Sampler1}——光影下程序变体要的槽位没绑，补上原版自己会绑的纹理；</li>
 *   <li>{@code Texture view Sampler0 (...) has been closed!}——槽位绑的纹理已被别处关闭（本机
 *       开发环境稳定复现：资源重载后服务器列表 ping 失败那一瞬间），让这一次绘制跳过，
 *       而不是把整个客户端带走。</li>
 * </ol>
 *
 * <p>注入点是 {@code GlCommandEncoder#trySetup} 的 HEAD：此时渲染类型该绑的纹理已经绑完，
 * 校验尚未开始。返回 {@code false} 是原版自身的语义「本次绘制不执行」（{@code executeDraw}
 * 拿到 false 直接 return），因此跳过绘制只是少画一笔，不动任何其它状态。</p>
 *
 * <p>不做取消、不做重定向：两条兜底都没命中时原版流程一字不改地继续跑，不开光影、纹理也都
 * 正常时行为与原版完全一致。</p>
 */
@Mixin(GlCommandEncoder.class)
public abstract class GlCommandEncoderMixin {

    @Inject(method = "trySetup", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$bindMissingSamplers(GlRenderPass pass, Collection<String> dirtyUniforms,
                                               CallbackInfoReturnable<Boolean> callbackInfo) {
        if (MissingSamplerFallback.apply(pass)) {
            return;
        }
        callbackInfo.setReturnValue(false);
    }
}
