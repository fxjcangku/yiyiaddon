package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.GenStageCapture;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 开发期探针（种子挖矿 PoC 第四轮）· 真实生成过程的逐阶段 checkpoint。
 *
 * <p><b>为什么第四轮必须新增这个探针</b>：用户口径第八节要求「如果 pre-diamond 不一致，
 * 不许直接猜可能是 CARVERS，必须按阶段 checkpoint 找到 FIRST DIVERGENCE」。
 * 第三轮的探针只在「装饰开始前」和「第一条钻石 feature 前」两个时点取证，
 * 中间少了 BIOMES / NOISE / SURFACE 三段，无法定位分叉发生在哪一层。</p>
 *
 * <p><b>注入点</b>：{@code ChunkStatusTasks} 的 {@code generateBiomes} /
 * {@code generateNoise} / {@code generateSurface} 的 RETURN（26.1.2 本线
 * ChunkStatusTasks.java:71-108）。这三个方法都是 package-private 静态方法，
 * Mixin 可以直接注入；{@code CARVERS} 阶段不在这里注——它等价于
 * {@code ChunkGenerator#applyBiomeDecoration} 的 HEAD，已经由
 * {@link ChunkGeneratorStageCaptureMixin} 取了。</p>
 *
 * <p>探针本体不在这里判断任何开关：{@code GenStageCapture.onStageComplete} 第一件事就是
 * 查总闸、查捕获区域、查是否处于回放，未开启时只做几次集合查询就返回。</p>
 */
@Mixin(ChunkStatusTasks.class)
public abstract class ChunkStatusStageProbeMixin {

    @Inject(method = "generateBiomes", at = @At("RETURN"))
    private static void yiyiaddon$captureAfterBiomes(WorldGenContext context, ChunkStep step,
                                                     StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk,
                                                     CallbackInfoReturnable<CompletableFuture<ChunkAccess>> callbackInfo) {
        GenStageCapture.onStageComplete(GenStageCapture.STAGE_BIOMES, context, step, cache, chunk);
    }

    @Inject(method = "generateNoise", at = @At("RETURN"))
    private static void yiyiaddon$captureAfterNoise(WorldGenContext context, ChunkStep step,
                                                    StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk,
                                                    CallbackInfoReturnable<CompletableFuture<ChunkAccess>> callbackInfo) {
        GenStageCapture.onStageComplete(GenStageCapture.STAGE_NOISE, context, step, cache, chunk);
    }

    @Inject(method = "generateSurface", at = @At("RETURN"))
    private static void yiyiaddon$captureAfterSurface(WorldGenContext context, ChunkStep step,
                                                      StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk,
                                                      CallbackInfoReturnable<CompletableFuture<ChunkAccess>> callbackInfo) {
        GenStageCapture.onStageComplete(GenStageCapture.STAGE_SURFACE, context, step, cache, chunk);
    }
}
