package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.event.EventDispatcher;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 数据包观测：把收发包转交给 {@link EventDispatcher} 入队。
 *
 * <p>两个注入点都是网络线程，因此这里只做入队，不派发、不解析、不回调模块；真正的派发在主线程每刻进行。</p>
 *
 * <p>发包只注入三参数重载：单参数与双参数重载最终都会走到它，注入一处即可覆盖全部发送路径。</p>
 */
@Mixin(Connection.class)
public abstract class ConnectionPacketMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"))
    private void yiyiaddon$observeReceived(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) {
        EventDispatcher.enqueueReceived(packet);
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;Z)V",
            at = @At("HEAD"))
    private void yiyiaddon$observeSent(Packet<?> packet, ChannelFutureListener listener, boolean flush,
                                       CallbackInfo info) {
        EventDispatcher.enqueueSent(packet);
    }
}
