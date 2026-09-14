package com.yiyiaddon.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * 客户端世界预测处理器访问器。
 *
 * <p>26.1.2 中 {@code ClientLevel#getBlockStatePredictionHandler()} 是包私有方法，
 * 外部包无法直接调用。本访问器只暴露读取入口，不修改任何游戏行为。</p>
 *
 * <p>用途：发包层取方块交互包的 sequence 序号，见
 * {@code com.yiyiaddon.platform.network.BlockPacketSender}。</p>
 */
@Mixin(ClientLevel.class)
public interface ClientLevelPredictionAccessor {

    /** 取当前客户端世界的方块状态预测处理器 */
    @Invoker("getBlockStatePredictionHandler")
    BlockStatePredictionHandler yiyiaddon$getPredictionHandler();
}
