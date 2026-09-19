package com.yiyiaddon.mixin.client;

import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.j.c.a;
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

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeFastBreakMixin {
   @Unique
   private final a yiyiaddon$fastBreak = a.a();

   @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$startFastBreak(BlockPos var1, Direction var2, CallbackInfoReturnable<Boolean> var3) {
      Minecraft var4 = Minecraft.getInstance();
      com.yiyiaddon.e.j.a var5 = yiyiaddon$miningModule();
      if (this.yiyiaddon$fastBreak.a(var4, var5, var1, var2) == a.c.ACCEPTED) {
         var3.setReturnValue(true);
      }
   }

   @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$continueFastBreak(BlockPos var1, Direction var2, CallbackInfoReturnable<Boolean> var3) {
      Minecraft var4 = Minecraft.getInstance();
      com.yiyiaddon.e.j.a var5 = yiyiaddon$miningModule();
      if (this.yiyiaddon$fastBreak.a(var4, var5, var1, var2)) {
         var3.setReturnValue(true);
      }
   }

   @Inject(method = "stopDestroyBlock", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$stopFastBreak(CallbackInfo var1) {
      if (this.yiyiaddon$fastBreak.a(Minecraft.getInstance())) {
         var1.cancel();
      }
   }

   @Unique
   private static com.yiyiaddon.e.j.a yiyiaddon$miningModule() {
      return e.b("mining") instanceof com.yiyiaddon.e.j.a var1 ? var1 : null;
   }
}
