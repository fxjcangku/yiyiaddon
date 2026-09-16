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
 * 避免业务状态散落在 Mixin 内。注入点与旧项目
 * {@code mixin/MultiPlayerGameModeFastBreakMixin.java}（53 行）逐条一致：</p>
 * <ul>
 *   <li>{@code startDestroyBlock} / {@code continueDestroyBlock}：HEAD 注入，裁决后按结果改写返回值；</li>
 *   <li>{@code stopDestroyBlock}：HEAD 注入，需要清理时 {@code cancel()}。</li>
 * </ul>
 *
 * <p>模块实例取用是本项目的唯一差异：旧项目走第三方框架的模块表
 * （{@code Modules.get().get(AutoMinerModule.class)}），本项目走
 * {@link ModuleManager#byId(String)}（HashMap 查表，属热路径可接受）。</p>
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeFastBreakMixin {

    @Unique
    private final MiningFastBreakController yiyiaddon$fastBreak = new MiningFastBreakController();

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$startFastBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        AutoMinerModule module = yiyiaddon$miningModule();
        MiningFastBreakController.StartResult result = yiyiaddon$fastBreak.start(mc, module, pos, direction);
        if (result != MiningFastBreakController.StartResult.PASS) {
            cir.setReturnValue(result == MiningFastBreakController.StartResult.ACCEPTED);
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
        Minecraft mc = Minecraft.getInstance();
        AutoMinerModule module = yiyiaddon$miningModule();
        int interval = module == null ? 0 : module.getBreakInterval();
        if (yiyiaddon$fastBreak.stop(mc, interval)) ci.cancel();
    }

    /** 取自动挖矿模块；未注册或类型不符返回 {@code null}（控制器会交还原版处理） */
    @Unique
    private static AutoMinerModule yiyiaddon$miningModule() {
        Module module = ModuleManager.byId(AutoMinerModule.MODULE_ID);
        return module instanceof AutoMinerModule miner ? miner : null;
    }
}
