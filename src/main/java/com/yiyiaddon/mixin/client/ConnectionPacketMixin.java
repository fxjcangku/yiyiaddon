package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.core.net.SendInterceptor;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 数据包观测与发包拦截：收包转交 {@link EventDispatcher} 入队，发包先过
 * {@link SendInterceptor} 的闸门（放行 / 取消 / 改写 / 延迟）。
 *
 * <p>两个注入点都在网络线程：这里只做入队与同步判据，不派发、不回调模块；
 * 真正的派发在主线程每刻进行。</p>
 *
 * <p>发包只注入三参数重载：单参数与双参数重载最终都会走到它，注入一处即可覆盖全部发送路径。</p>
 *
 * <p><b>为什么发包注入是可取消的：</b>数据包必须在写出之前决定去留，主线程事件已经太晚。
 * {@link SendInterceptor} 内部只跑「模块预先注册的无阻塞判据」，不派发事件、不回调业务代码，
 * 因此不会阻塞 Netty 线程。</p>
 */
@Mixin(Connection.class)
public abstract class ConnectionPacketMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"))
    private void yiyiaddon$observeReceived(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) {
        EventDispatcher.enqueueReceived(packet);
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;Z)V",
            at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$interceptSent(Packet<?> packet, ChannelFutureListener listener, boolean flush,
                                         CallbackInfo info) {
        if (SendInterceptor.intercept((Connection) (Object) this, packet)) {
            info.cancel();
        }
    }
}
