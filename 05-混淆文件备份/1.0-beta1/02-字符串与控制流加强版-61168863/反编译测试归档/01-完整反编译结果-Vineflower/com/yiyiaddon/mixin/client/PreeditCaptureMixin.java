package com.yiyiaddon.mixin.client;

import com.yiyiaddon.l.g.b;
import net.minecraft.client.input.PreeditEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PreeditEvent.class)
public abstract class PreeditCaptureMixin {
   @Inject(method = "createFromCallback", at = @At("RETURN"))
   private static void yiyiaddon$capturePreedit(int var0, long var1, int var3, long var4, int var6, int var7, CallbackInfoReturnable<PreeditEvent> var8) {
      PreeditEvent var9 = (PreeditEvent)var8.getReturnValue();
      b.bv(var9 == null ? null : var9.fullText());
   }
}
