package com.yiyiaddon.mixin.client;

import com.yiyiaddon.k.e.c;
import net.minecraft.client.resources.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelManager.class)
public abstract class ModelManagerReloadMixin {
   @Inject(method = "apply(Lnet/minecraft/client/resources/model/ModelManager$ReloadState;)V", at = @At("TAIL"))
   private void yiyiaddon$onModelsApplied(CallbackInfo var1) {
      c.js();
   }
}
