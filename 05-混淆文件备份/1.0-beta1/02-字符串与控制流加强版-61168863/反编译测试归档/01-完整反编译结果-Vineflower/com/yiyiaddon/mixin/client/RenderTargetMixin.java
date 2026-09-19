package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.yiyiaddon.l.g.i;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderTarget.class)
public abstract class RenderTargetMixin {
   @Inject(method = "blitToScreen", at = @At("HEAD"))
   private void yiyiaddon$renderSkijaFrame(CallbackInfo var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2 != null) {
         if (this == var2.getMainRenderTarget()) {
            if (var2.screen instanceof i var3) {
               var3.ks();
            }
         }
      }
   }
}
