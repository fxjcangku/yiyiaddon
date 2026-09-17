package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 自动挖矿快速破坏（秒破）的 Mixin 接线层。
 *
 * <p>协议、进度、去重、超时与清理都在独立控制器
 * {@link MiningFastBreakController} 中；这里只截获原版三个破坏入口，
 * 避免业务状态散落在 Mixin 内。控制器实例是<b>全局唯一</b>的
 * （{@link MiningFastBreakController#instance()}）：模块每刻驱动的推进
 * （{@code AutoMinerModule#onTick}）与这里的接管入口必须共用同一份状态，
 * 否则「START 在这一份、STOP 在另一份」，方块永远挖不烂。</p>
 *
 * <ul>
 *   <li>{@code startDestroyBlock} / {@code continueDestroyBlock}：HEAD 注入，裁决后按结果改写返回值；</li>
 *   <li>{@code stopDestroyBlock}：HEAD 注入，需要接管时 {@code cancel()}（只取消原版的 ABORT 包）。</li>
 * </ul>
 *
 * <p>模块实例取用是本项目的唯一差异：旧项目走第三方框架的模块表
 * （{@code Modules.get().get(AutoMinerModule.class)}），本项目走
 * {@link ModuleManager#byId(String)}（HashMap 查表，属热路径可接受）。</p>
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeFastBreakMixin {

    @Unique
    private final MiningFastBreakController yiyiaddon$fastBreak = MiningFastBreakController.instance();

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$startFastBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        AutoMinerModule module = yiyiaddon$miningModule();
        // 只有 ACCEPTED 才接管；PASS 与 COOLDOWN 一律原样交还原版。
        //
        // 旧实现是「非 PASS 就 setReturnValue(result == ACCEPTED)」，于是 COOLDOWN（刚挖完一块的
        // 方块间隔冷却）会返回 false =「我们没挖、也没让原版挖」，而 Baritone 那一刻正好开始挖下一块
        // ——手上拿着对口的工具（铲子挖草方块）却怎么都挖不掉（用户 2026-09-18 实机）。
        // 冷却的意义只是「这一块我们不接管」，不是「这一块谁都别挖」。
        if (yiyiaddon$fastBreak.start(mc, module, pos, direction)
            == MiningFastBreakController.StartResult.ACCEPTED) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$continueFastBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        AutoMinerModule module = yiyiaddon$miningModule();
        if (yiyiaddon$fastBreak.continueBreaking(mc, module, pos, direction)) cir.setReturnValue(true);
    }

    @Inject(method = "stopDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$stopFastBreak(CallbackInfo ci) {
        if (yiyiaddon$fastBreak.stop(Minecraft.getInstance())) ci.cancel();
    }

    /** 取自动挖矿模块；未注册或类型不符返回 {@code null}（控制器会交还原版处理） */
    @Unique
    private static AutoMinerModule yiyiaddon$miningModule() {
        Module module = ModuleManager.byId(AutoMinerModule.MODULE_ID);
        return module instanceof AutoMinerModule miner ? miner : null;
    }
}
