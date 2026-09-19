package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.yiyiaddon.l.g.a.l;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRenderer.class)
public abstract class GuiRendererMixin {
   @Inject(method = "render", at = @At("HEAD"))
   private void yiyiaddon$renderWorldOverlay(GpuBufferSlice var1, CallbackInfo var2) {
      l.ky();
   }
}
