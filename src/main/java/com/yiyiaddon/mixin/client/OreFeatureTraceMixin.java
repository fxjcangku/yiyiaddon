package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.FeatureWriteJournal;
import com.yiyiaddon.dev.seedpoc.OfflineOreAttribution;
import com.yiyiaddon.dev.seedpoc.OreCandidateJournal;
import com.yiyiaddon.dev.seedpoc.OreVeinTrace;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 开发期探针（种子挖矿 PoC 第三轮）· 钻石矿脉放置取证。
 *
 * <p>注入两个点，对应 {@code OreFeature} 里两段互相独立的随机派生：</p>
 * <ol>
 *     <li>{@code OreFeature#place} 的 <b>HEAD</b>（OreFeature.java:23-27）：拿到
 *         {@code FeaturePlaceContext#origin} —— 这就是 {@code PlacementModifier} 链算出来的
 *         <b>placement origin</b>。它不同 ⇒ 分叉在 {@code PlacedFeature} / 计数与高度修饰符 /
 *         {@code featureSeed} 这一层；</li>
 *     <li>{@code OreFeature#doPlace} 的 <b>HEAD</b>（OreFeature.java:55-70）：拿到矿脉轴与候选盒
 *         这些由 {@code random} 直接算出来的量（{@code place:28-42}）。origin 相同而它不同
 *         ⇒ 分叉在 {@code OreFeature} 内部随机流或上下文这一层。</li>
 * </ol>
 *
 * <p>本探针本身不做任何判定：只有「当前正在放置的顶层 feature 是钻石四条 + 落在捕获区域内」
 * 时才会被记账（判断在 {@code OreVeinTrace} 里），其余情况只是一次 {@code ThreadLocal} 查询就返回。
 * 真实装饰期与实验回放期都会被记账，两侧靠 {@code GenStageCapture#isReplaying()} 区分。</p>
 */
@Mixin(OreFeature.class)
public abstract class OreFeatureTraceMixin {

    /**
     * 必须写全描述符：{@code Feature} 里有两个同名 {@code place}
     * （{@code place(FeaturePlaceContext)} 与 {@code place(FC, WorldGenLevel, ChunkGenerator, RandomSource, BlockPos)}），
     * 只写名字会让 Mixin 解析出多个候选。
     */
    @Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At("HEAD"))
    private void yiyiaddon$tracePlacementOrigin(FeaturePlaceContext<OreConfiguration> context,
                                                CallbackInfoReturnable<Boolean> callbackInfo) {
        OreVeinTrace.notePlace(context.origin());
    }

    @Inject(method = "doPlace", at = @At("HEAD"))
    private void yiyiaddon$traceVeinGeometry(WorldGenLevel level, RandomSource random, OreConfiguration config,
                                             double x0, double x1, double z0, double z1,
                                             double y0, double y1,
                                             int xStart, int yStart, int zStart, int sizeXZ, int sizeY,
                                             CallbackInfoReturnable<Boolean> callbackInfo) {
        OreVeinTrace.noteVein(new OreVeinTrace.Vein(x0, x1, z0, z1, y0, y1,
                xStart, yStart, zStart, sizeXZ, sizeY));
        // 第七轮：一条新矿脉开始（候选序号与「累计消耗随机序号」都从 0 起）
        OreCandidateJournal.onVeinStart();
    }

    /**
     * 候选点接受判定（接受层的唯一直接证据）。
     *
     * <p>{@code OreFeature#canPlaceOre}（OreFeature.java:168-181）在候选点可替换时会调用
     * {@code shouldSkipAirCheck}，而后者在 {@code 0 < discardChanceOnAirExposure < 1} 时
     * <b>消耗一次 {@code nextFloat()}</b>。把每次判定的「坐标 + 判定那一刻的方块 + 结果」记下来，
     * 原版与重放一逐项对比，第一处状态不同就是整条链的第一处分叉。</p>
     */
    @Inject(method = "canPlaceOre", at = @At("RETURN"))
    private static void yiyiaddon$traceCandidateAcceptance(BlockState orePosState,
                                                           java.util.function.Function<BlockPos, BlockState> blockGetter,
                                                           RandomSource random, OreConfiguration config,
                                                           OreConfiguration.TargetBlockState targetState,
                                                           BlockPos.MutableBlockPos orePos,
                                                           CallbackInfoReturnable<Boolean> callbackInfo) {
        OreVeinTrace.noteAccept(orePos, orePosState, callbackInfo.getReturnValueZ());
        // 第六轮：离线链路的写入归属记账（返回 true 就等于「这一格随后真的会被 setBlock」）
        OfflineOreAttribution.noteAccept(orePos, callbackInfo.getReturnValueZ());
        // 第七轮：候选点级诊断（序号 / 写入前方块 / 是否可替换 / 是否消耗一次随机 / 是否被接受）
        OreCandidateJournal.onCandidate(orePos, orePosState, config, callbackInfo.getReturnValueZ());
        // 第七轮：写入台账必须补上这一条路径 —— OreFeature#doPlace 通过后走的是
        // LevelChunkSection#setBlockState，不经过 WorldGenRegion#setBlock，
        // 只挂 setBlock 的台账会完全看不到矿石写入（实测 4224 条写入里一条矿石都没有）
        FeatureWriteJournal.onDirectOreWrite(orePos, orePosState,
                targetState == null ? orePosState : targetState.state, callbackInfo.getReturnValueZ());
    }
}
