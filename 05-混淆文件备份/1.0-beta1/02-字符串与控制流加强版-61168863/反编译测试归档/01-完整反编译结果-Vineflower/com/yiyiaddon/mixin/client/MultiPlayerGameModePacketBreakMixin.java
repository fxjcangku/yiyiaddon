package com.yiyiaddon.mixin.client;

import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.k.a;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModePacketBreakMixin {
   @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$packetBreakStart(BlockPos var1, Direction var2, CallbackInfoReturnable<Boolean> var3) {
      a var4 = yiyiaddon$packetBreakModule();
      if (var4 != null && var4.c(var1, var2)) {
         var3.setReturnValue(true);
      }
   }

   @Unique
   private static a yiyiaddon$packetBreakModule() {
      return e.b("packetbreak") instanceof a var1 ? var1 : null;
   }
}
