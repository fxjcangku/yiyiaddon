package com.yiyiaddon.mixin.client;

import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
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

/**
 * 服务器资源包推送观测注入。
 *
 * <p><b>为什么必须注入：</b>原版接收资源包推送的处理不经过任何可作为订阅点的事件，
 * 而资源生命周期服务需要知道「服务器下发了哪个资源包（id / url / hash）」。此处无条件
 * 把推送喂给 {@link ResourceExtractionService}，只登记元数据，<b>不下载、不取消</b>，
 * 不影响原版资源包流程。</p>
 *
 * <p><b>为什么从监听器自身的 {@code serverData} 取 ServerKey：</b>原版在配置阶段就下发
 * {@code ClientboundResourcePackPushPacket}，此时玩家实体尚未创建，
 * {@code Minecraft.getCurrentServer()} 与 {@code Minecraft.getConnection()} 都不可用，
 * 用它们读服务器地址永远拿不到 host:port。监听器自身持有的 {@link ServerData} 在配置阶段
 * 已是非 null，是唯一可靠来源。</p>
 *
 * <p><b>为什么投递回主线程：</b>本注入点在 HEAD，早于原版的线程切换，可能仍在网络线程上；
 * 登记动作会触发会话失效回调（订阅者要清空自己的运行时数据），必须在主线程执行。</p>
 */
@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class ResourcePackPushMixin {

    /** 本监听器持有的服务器信息（配置阶段即为非 null，包含玩家实际输入的 ip） */
    @Shadow @Final protected ServerData serverData;

    /** 发送数据包的方法引用（保留签名以匹配注入点所在的类） */
    @Shadow public abstract void send(Packet<?> packet);

    @Inject(
        method = "handleResourcePackPush(Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;)V",
        at = @At("HEAD")
    )
    private void yiyiaddon$observeResourcePackPush(ClientboundResourcePackPushPacket packet, CallbackInfo ci) {
        try {
            String key = null;
            ServerData data = this.serverData;
            if (data != null && data.ip != null && !data.ip.isBlank()) {
                key = ResourcePackCache.canonicalKey(data.ip);
            }
            String resolvedKey = key;
            Minecraft.getInstance().execute(
                () -> ResourceExtractionService.onResourcePackPush(packet, resolvedKey));
        } catch (Exception ignored) {
            // 观测异常绝不能影响原版资源包流程
        }
    }
}
