package com.yiyiaddon.mixin.client;

import com.yiyiaddon.platform.eat.SilentEat;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 自动进食期间不让本地进入用食态 —— 吃东西动画的唯一来源。
 *
 * <p>用户 2026-09-19：「吃东西好像修好了，但是还是有动画」。动画来自本地
 * {@code startedUsingItem}（{@code LocalPlayer#isUsingItem} 读的就是它），而它会被两条路点亮：
 * 我方起手包本地调用的 {@code ItemStack#use}，以及<b>服务端把用食标志同步回来</b>时
 * {@code onSyncedDataUpdated} 里那次 {@code startUsingItem}。后者还会让原版按键循环
 * 立刻发 RELEASE 把这一口取消 —— 这就是「吃不上」的隐藏成因。</p>
 *
 * <p>自动进食不需要本地预测（吃没吃完看服务端下发的那格数量），所以在
 * {@link SilentEat} 打开期间把 {@code startUsingItem} 压成空操作：动画没了、RELEASE 没了，
 * 服务端照常结算。手动进食期间开关是关的，所以玩家自己吃东西一切照旧。</p>
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerEatAnimationMixin {

    @Inject(method = "startUsingItem", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$suppressAutoEatAnimation(InteractionHand hand, CallbackInfo ci) {
        if (SilentEat.suppressed()) ci.cancel();
    }
}
