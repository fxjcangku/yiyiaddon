package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.FeatureWriteJournal;
import com.yiyiaddon.dev.seedpoc.GenStageCapture;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 开发期探针（种子挖矿 PoC 第二轮）· 装饰开始前的阶段快照。
 *
 * <p>注入点：{@code ChunkGenerator#applyBiomeDecoration} 的 HEAD（ChunkGenerator.java:318）。</p>
 *
 * <p><b>为什么这个点就是「post-CARVERS / pre-FEATURES」</b>：该方法由 {@code FEATURES} 步骤调用
 * （{@code ChunkStatusTasks#generateFeatures} 先补 FINAL 高度图，再 new WorldGenRegion，
 * 再调 applyBiomeDecoration），此时中心区块的 {@code persistedStatus} 是 {@code CARVERS}、
 * 方块状态只含「噪声 + 地表 + 雕刻器」的结果；3x3 邻域由 {@code ChunkPyramid} 的
 * {@code addRequirement(ChunkStatus.CARVERS, 1)} 保证至少处在 CARVERS（ChunkPyramid.java:29-35）。</p>
 *
 * <p>探针本体不在这里判断开关：{@code GenStageCapture} 内部第一件事就是查总闸与捕获区域，
 * 未开启时不取任何数据、不产生任何副作用。</p>
 */
@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorStageCaptureMixin {

    @Inject(method = "applyBiomeDecoration", at = @At("HEAD"))
    private void yiyiaddon$captureGenerationStage(WorldGenLevel level, ChunkAccess chunk,
                                                  StructureManager structureManager, CallbackInfo callbackInfo) {
        GenStageCapture.onFeaturesHead(level, chunk);
        // 第七轮：给本次装饰发一个批号（口径与第五轮一致 = 进入 applyBiomeDecoration 的先后）
        FeatureWriteJournal.onDecorationHead(level, chunk);
    }
}
