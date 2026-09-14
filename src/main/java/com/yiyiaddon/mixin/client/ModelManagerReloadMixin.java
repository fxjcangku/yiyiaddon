package com.yiyiaddon.mixin.client;

import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.resources.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 资源重载「真正完成」信号注入。
 *
 * <p>26.1.2 的真实管线：{@code ModelManager} 实现 {@code PreparableReloadListener}，
 * 其重载 future 的最后一步是 {@code thenAcceptAsync(this::apply, ...)}，而
 * {@code apply(ModelManager.ReloadState)} 的第一件事就是把物品模型表整体换新。</p>
 *
 * <p>因此 {@code apply} 的 TAIL 就是「资源管理器重载真正完成、物品模型已经可以解析」的
 * 最精确时点。资源生命周期服务收到该信号后才进入解析阶段，保证解析结果一定属于当前
 * 服务器的资源包，不会拿到上一服务器的旧表。</p>
 */
@Mixin(ModelManager.class)
public abstract class ModelManagerReloadMixin {

    @Inject(
        method = "apply(Lnet/minecraft/client/resources/model/ModelManager$ReloadState;)V",
        at = @At("TAIL")
    )
    private void yiyiaddon$onModelsApplied(CallbackInfo ci) {
        ResourceExtractionService.onReloadApplied();
    }
}
