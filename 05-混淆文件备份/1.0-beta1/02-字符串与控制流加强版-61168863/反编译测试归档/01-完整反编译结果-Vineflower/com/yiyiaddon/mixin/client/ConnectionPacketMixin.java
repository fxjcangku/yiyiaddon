package com.yiyiaddon.mixin.client;

import com.yiyiaddon.d.a.d;
import com.yiyiaddon.d.c.g;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ConnectionPacketMixin {
   @Inject(method = "channelRead0", at = @At("HEAD"))
   private void yiyiaddon$observeReceived(ChannelHandlerContext var1, Packet<?> var2, CallbackInfo var3) {
      d.c(var2);
   }

   @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;Z)V", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$interceptSent(Packet<?> var1, ChannelFutureListener var2, boolean var3, CallbackInfo var4) {
      if (g.b((Connection)this, var1)) {
         var4.cancel();
      }
   }
}
