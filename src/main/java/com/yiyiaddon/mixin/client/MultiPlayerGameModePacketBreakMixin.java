package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 发包秒破的 Mixin 接线层：拦下原版本的开始破坏入口，交给
 * {@link PacketInstantBreakModule#interceptStart(BlockPos, Direction)} 接管。
 *
 * <p>旧实现在开始破坏事件里 {@code cancel()}，原版本地破坏预测与原版 START 包一起被拦掉；
 * 本项目发包闸门（{@code core.net.SendGate}）只能拦包，本地预测仍会跑，因此在原版入口补这一层。
 * 判定、入队、发包协议全部在模块内，这里只做一次转发。</p>
 *
 * <p>与自动挖矿秒破的 {@link MultiPlayerGameModeFastBreakMixin} 各自独立：两个模块互斥
 * （同开时拒启 / 巡检停机），因此同一时刻只有一个会真正接管；先落盘的 {@code cancel/return}
 * 生效，另一个取到的是同一坐标但会被自身状态机忽略。</p>
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModePacketBreakMixin {

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$packetBreakStart(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        PacketInstantBreakModule module = yiyiaddon$packetBreakModule();
        if (module != null && module.interceptStart(pos, direction)) {
            // 与原版「已开始破坏」的返回值一致：不跑原版方法体，本地预测与本条 START 包都不发
            cir.setReturnValue(true);
        }
    }

    /** 取发包秒破模块；未注册或类型不符返回 {@code null}（原版照常处理） */
    @Unique
    private static PacketInstantBreakModule yiyiaddon$packetBreakModule() {
        Module module = ModuleManager.byId(PacketInstantBreakModule.MODULE_ID);
        return module instanceof PacketInstantBreakModule packetBreak ? packetBreak : null;
    }
}
