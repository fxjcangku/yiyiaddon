package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.DiamondFeatureOracle;
import com.yiyiaddon.dev.seedpoc.FeatureWriteJournal;
import com.yiyiaddon.dev.seedpoc.GenStageCapture;
import com.yiyiaddon.dev.seedpoc.OfflineOreAttribution;
import com.yiyiaddon.dev.seedpoc.OfflineStageCapture;
import com.yiyiaddon.dev.seedpoc.OreCandidateJournal;
import com.yiyiaddon.dev.seedpoc.OreVeinTrace;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 开发期探针（种子挖矿 PoC）· 单次装饰的钻石 oracle 与放置台账。
 *
 * <p>注入点：{@code PlacedFeature#placeWithBiomeCheck}（PlacedFeature.java:38-40）。
 * 该方法在本线源码里<b>只有一个调用方</b>——{@code ChunkGenerator#applyBiomeDecoration:392}
 * 的 feature 放置循环，而且原版对每条 feature 都是
 * {@code random.setFeatureSeed(decorationSeed, 全局索引, 步骤序号)} 之后再调用它
 * （ChunkGenerator.java:388-392），因此：</p>
 * <ul>
 *     <li><b>HEAD</b> 就是「这条 feature 即将执行」的那一瞬 ——
 *         第一轮的第二阶段用它取 pre-diamond 快照（{@link GenStageCapture}），
 *         第三轮用它取该条钻石 feature 的「执行前」钻石集合（{@link DiamondFeatureOracle}）
 *         并开一次放置台账（{@link OreVeinTrace}）；</li>
 *     <li><b>RETURN</b> 就是「这条 feature 刚刚执行完、后面还什么都没跑」的那一瞬 ——
 *         第三轮用它取同一 viewer 的 post-diamond（等价于「四条钻石全部执行结束之后、
 *         后续非钻石 feature 尚未继续污染」），并结算放置台账。</li>
 * </ul>
 *
 * <p>两个钩子本身不做任何判定：开关、捕获区域、是否钻石四条、是否处于回放，
 * 全部在 {@code com.yiyiaddon.dev.seedpoc} 里判断；未开启实验时只是一次集合查询就返回。</p>
 */
@Mixin(PlacedFeature.class)
public abstract class PlacedFeatureStageCaptureMixin {

    @Inject(method = "placeWithBiomeCheck", at = @At("HEAD"))
    private void yiyiaddon$captureBeforeDiamondFeature(WorldGenLevel level, ChunkGenerator generator,
                                                       RandomSource random, BlockPos origin,
                                                       CallbackInfoReturnable<Boolean> callbackInfo) {
        PlacedFeature feature = (PlacedFeature) (Object) this;
        // 第七轮：无论真实侧还是离线重放侧，都先给「当前正在执行哪条 placed_feature」建上下文
        // （写入台账与候选点诊断都靠它把一次 setBlock / canPlaceOre 归到具体的 feature 上）
        FeatureWriteJournal.onFeatureHead(level, origin, feature);
        OreCandidateJournal.onFeatureHead(level, origin, feature);
        // 第四轮：离线 pipeline 会自己驱动一次真实的 FEATURES，那一次的 level 是离线 region；
        // 这类调用一律交给离线捕获，绝不进真实侧这三个探针（否则真实 oracle 会被离线生成污染）
        if (OfflineStageCapture.onFeatureHead(level, origin, feature)) {
            // 第六轮：离线链路额外做一次「写入归属」记账（哪个 viewer 的哪条 feature 写进了目标区块）
            OfflineOreAttribution.onFeatureHead(level, feature);
            return;
        }
        GenStageCapture.onBeforePlacedFeature(level, origin, feature);
        DiamondFeatureOracle.onFeatureHead(level, origin, feature);
        OreVeinTrace.beginFeature(level, origin, feature);
    }

    @Inject(method = "placeWithBiomeCheck", at = @At("RETURN"))
    private void yiyiaddon$captureAfterDiamondFeature(WorldGenLevel level, ChunkGenerator generator,
                                                      RandomSource random, BlockPos origin,
                                                      CallbackInfoReturnable<Boolean> callbackInfo) {
        // 第七轮：上下文必须与 HEAD 对称清掉（真实侧 / 离线侧都要清）
        OreCandidateJournal.onFeatureTail();
        FeatureWriteJournal.onFeatureTail();
        // 离线调用在 HEAD 侧已被接管，这里必须对称跳过，否则会去结一个从未开过的台账
        if (OfflineStageCapture.isOfflineCall(level)) {
            OfflineOreAttribution.onFeatureTail();
            return;
        }
        // 先结账放置台账（这一条 feature 的 OreFeature 调用已经全部结束），再取 post-diamond 集合
        OreVeinTrace.endFeature();
        DiamondFeatureOracle.onFeatureTail(level, origin, (PlacedFeature) (Object) this);
    }
}
