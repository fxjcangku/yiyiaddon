package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.OreVeinTrace;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 开发期探针（种子挖矿 PoC 第三轮）· 生物群系过滤判定取证。
 *
 * <p>注入点：{@code BiomeFilter#shouldPlace} 的 RETURN（BiomeFilter.java:21-27）。
 * 它是钻石四条放置链上最后一个修饰符（{@code commonOrePlacement} / {@code rareOrePlacement}
 * 都以它收尾），实现只有两行：</p>
 *
 * <pre>
 *   Holder&lt;Biome&gt; biome = context.getLevel().getBiome(origin);
 *   return context.generator().getBiomeGenerationSettings(biome).hasFeature(feature);
 * </pre>
 *
 * <p><b>为什么必须单独取证这一层</b>：它不消耗随机数，但 {@code PlacementFilter#getPositions}
 * 在判定不通过时会把整个位置丢掉。丢掉一个位置 = 少放一次矿 = 少消耗一整段
 * {@code OreFeature} 的随机数，于是同一条 feature 之后所有矿脉一起漂移。因此
 * 「placement origin 不同」很可能只是下游症状，真正第一处分叉就在这张判定表里。</p>
 *
 * <p>探针把「原点 + 判定结果 + 判定时刻解析出的生物群系」一起记账；未开启实验、或不在某条钻石
 * feature 的放置过程中时，只是一次 {@code ThreadLocal} 查询就返回。</p>
 */
@Mixin(BiomeFilter.class)
public abstract class BiomeFilterTraceMixin {

    @Inject(method = "shouldPlace", at = @At("RETURN"))
    private void yiyiaddon$traceBiomeFilter(PlacementContext context, RandomSource random, BlockPos origin,
                                            CallbackInfoReturnable<Boolean> callbackInfo) {
        Holder<Biome> biome = context.getLevel().getBiome(origin);
        String biomeId = biome.unwrapKey().map(key -> key.identifier().getPath()).orElse("?");
        OreVeinTrace.noteBiomeCheck(origin, callbackInfo.getReturnValueZ(), biomeId);
    }
}
