package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yiyiaddon.compat.MissingGlobalUniformFallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 在原版读取「全局设置 UBO」（{@code Globals}）之前，把 26.2 首帧之前必然缺失的那份补上，
 * 避免 {@code GlCommandEncoder#trySetup} 在开发端抛
 * {@code Missing uniform Globals (should be UNIFORM_BUFFER)} 直接崩客户端。
 *
 * <p>注入点是 {@code RenderSystem#bindDefaultUniforms} 的 HEAD：这里正是原版拿
 * {@code getGlobalSettingsUniform()} 往 RenderPass 里塞 {@code Globals} 的唯一入口，
 * 早一步补建即可让原版自己的 {@code if (globalUniform != null)} 走通，
 * 因此不重定向、不取消，原版流程一字不改（详见 {@link MissingGlobalUniformFallback}）。</p>
 */
@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    @Inject(method = "bindDefaultUniforms", at = @At("HEAD"))
    private static void yiyiaddon$ensureGlobalSettingsUniform(RenderPass renderPass, CallbackInfo callbackInfo) {
        MissingGlobalUniformFallback.ensure();
    }
}
