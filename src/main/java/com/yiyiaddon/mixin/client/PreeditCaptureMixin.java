package com.yiyiaddon.mixin.client;

import com.yiyiaddon.ui.render.ImeBridge;
import net.minecraft.client.input.PreeditEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 截获输入法预编辑串（拼音组合中），交给 {@link ImeBridge} 转给自绘输入框。
 *
 * <p>为什么不能走原版派发：输入法接管模组（IMBlocker）会 cancel 掉
 * {@code KeyboardHandler#preeditCallback} 的整个方法体，改画它自己的全屏浮层。原版派发因此永不
 * 到达我们界面；而那个浮层画在 GUI 阶段，会被帧末叠加的 Skija 面板盖住 —— 于是自绘输入框里打字
 * 只会看到「什么都没显示，突然蹦出中文」。</p>
 *
 * <p>这里挂在 {@code PreeditEvent} 的工厂方法返回值上：它由 GLFW 回调线程调用、不被任何模组
 * cancel，是唯一稳定能拿到组合串的地方。拿到后写进 {@link ImeBridge}（volatile 赋值），
 * 自绘输入框下一帧照常在自己的框里画出来。</p>
 */
@Mixin(PreeditEvent.class)
public abstract class PreeditCaptureMixin {

    @Inject(method = "createFromCallback", at = @At("RETURN"))
    private static void yiyiaddon$capturePreedit(int preeditSize, long preeditPtr, int blockCount,
                                                 long blockSizesPtr, int focusedBlock, int caret,
                                                 CallbackInfoReturnable<PreeditEvent> callbackInfo) {
        PreeditEvent event = callbackInfo.getReturnValue();
        ImeBridge.setPreedit(event == null ? null : event.fullText());
    }
}
