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
 * 给原版「draw 前校验采样器绑定」这一步补上缺的原版槽位，避免光影下的
 * {@code IllegalStateException: Missing sampler Sampler1} 直接崩客户端。
 *
 * <p>注入点是 {@code GlCommandEncoder#trySetup} 的 HEAD：此时渲染类型该绑的纹理已经绑完，
 * 校验尚未开始，正好可以按「缺哪个补哪个」的口径补齐（详见 {@link MissingSamplerFallback}）。</p>
 *
 * <p>不做取消、不做重定向：补绑之后原版流程一字不改地继续跑，因此不开光影时行为与原版完全一致。</p>
 */
@Mixin(GlCommandEncoder.class)
public abstract class GlCommandEncoderMixin {

    @Inject(method = "trySetup", at = @At("HEAD"))
    private void yiyiaddon$bindMissingSamplers(GlRenderPass pass, Collection<String> dirtyUniforms,
                                               CallbackInfoReturnable<Boolean> callbackInfo) {
        MissingSamplerFallback.apply(pass);
    }
}
