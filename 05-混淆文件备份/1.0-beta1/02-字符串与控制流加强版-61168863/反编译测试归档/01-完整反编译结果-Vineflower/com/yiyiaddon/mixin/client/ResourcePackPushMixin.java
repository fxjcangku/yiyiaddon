package com.yiyiaddon.mixin.client;

import com.yiyiaddon.d.d.a;
import com.yiyiaddon.k.e.c;
import com.yiyiaddon.k.e.e;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class ResourcePackPushMixin {
   @Shadow
   @Final
   protected ServerData serverData;

   @Shadow
   public abstract void send(Packet<?> var1);

   @Inject(method = "handleResourcePackPush(Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;)V", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$observeResourcePackPush(ClientboundResourcePackPushPacket var1, CallbackInfo var2) {
      try {
         String var3 = null;
         ServerData var4 = this.serverData;
         if (var4 != null && var4.ip != null && !var4.ip.isBlank()) {
            var3 = e.bZ(var4.ip);
         }

         String var5 = var3;
         Minecraft.getInstance().execute(() -> c.a(var1, var5));
      } catch (Exception var7) {
      }

      try {
         if (a.handle(var1.id(), var1.url(), var1.hash())) {
            var2.cancel();
         }
      } catch (Exception var6) {
      }
   }
}
