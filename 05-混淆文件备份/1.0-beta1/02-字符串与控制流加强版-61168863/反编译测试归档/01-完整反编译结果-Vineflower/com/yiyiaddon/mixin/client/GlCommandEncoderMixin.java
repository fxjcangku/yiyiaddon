package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.opengl.GlRenderPass;
import com.yiyiaddon.b.a;
import java.util.Collection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlCommandEncoder.class)
public abstract class GlCommandEncoderMixin {
   @Inject(method = "trySetup", at = @At("HEAD"))
   private void yiyiaddon$bindMissingSamplers(GlRenderPass var1, Collection<String> var2, CallbackInfoReturnable<Boolean> var3) {
      a.a(var1);
   }
}
