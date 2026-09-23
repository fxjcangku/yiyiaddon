package com.yiyiaddon.seed.worldgen;

import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.levelgen.RandomState;

/**
 * 种子挖矿正式模块 · 离线生成区域视图（原版 {@code WorldGenRegion} 的子类）。
 *
 * <p><b>为什么必须继承原版而不是自己写一个 {@code WorldGenLevel}</b>：原版所有生成阶段都要求具体的
 * {@code WorldGenRegion} 类型（{@code ChunkGenerator#buildSurface} / {@code #applyCarvers} 直接声明它；
 * {@code applyBiomeDecoration} 的写权限判定走 {@code WorldGenRegion#ensureCanWrite}）。
 * 自己写一份等价实现等于把「原版怎么写」重抄一遍，任何一处抄错都会被误判成「种子算法有问题」，
 * 所以这里<b>只做最少量的覆盖</b>：只换掉「种子相关的读取」这 5 处。</p>
 *
 * <ol>
 *     <li>{@link #getSeed()}：父类从宿主世界取种子（WorldGenRegion.java:86），这里改成被试种子，
 *         否则装饰随机与雕刻器会用到真实世界的种子；</li>
 *     <li>{@link #getRandom()}：父类用宿主 {@code ChunkMap} 的 RandomState 构造（WorldGenRegion.java:88），
 *         这里换成被试种子的 RandomState 按同一口径构造（同名随机源 {@code minecraft:worldgen_region_random}、
 *         同一坐标锚点）；</li>
 *     <li>{@link #getBiomeManager()}：父类用宿主种子（WorldGenRegion.java:90），这里换成被试种子；</li>
 *     <li>{@link #getUncachedNoiseBiome(int, int, int)}：父类转发给宿主世界（WorldGenRegion.java:179-181），
 *         这里直接问自建生物群系源；</li>
 *     <li>{@link #isOldChunkAround(ChunkPos, int)}：父类查宿主 {@code ChunkMap}（WorldGenRegion.java:93-95），
 *         用来决定「要不要做旧区块混合」。离线世界里的区块全部是本次新建的，
 *         因此固定返回 false，等于「不向真实世界要任何判断」。</li>
 * </ol>
 *
 * <p>其余读取（维度类型、世界高度、注册表、写权限、{@code getChunk} 的依赖半径与状态检查）
 * 一律沿用父类实现——那些恰恰是「原版自己就是这么做的」那部分。</p>
 *
 * <p><b>「没读真实世界」的自证</b>：{@link #getChunkSource()} 是父类唯一会把请求转给宿主
 * {@code ServerChunkCache} 的口子，这里计数并上报。计数不为 0 说明有原版代码试图从真实世界取生成数据，
 * 报告必须逐条解释来源。</p>
 */
final class OfflineChunkRegion extends WorldGenRegion {

    /**
     * 装饰期区域随机源的注册名。
     *
     * <p>与 {@code WorldGenRegion} 私有常量逐字相同（WorldGenRegion.java:79），
     * 因为它参与 {@code RandomState#getOrCreateRandomFactory} 的哈希派生——名字不同就会拿到另一条随机流。</p>
     */
    private static final Identifier WORLDGEN_REGION_RANDOM =
            Identifier.withDefaultNamespace("worldgen_region_random");

    /** 被试种子。 */
    private final long offlineSeed;

    /** 被试种子派生的区域随机源。 */
    private final RandomSource offlineRandom;

    /** 被试种子派生的生物群系管理器。 */
    private final BiomeManager offlineBiomeManager;

    /** 自建生物群系源。 */
    private final BiomeSource offlineBiomeSource;

    /** 被试种子派生的随机状态。 */
    private final RandomState offlineRandomState;

    /** 宿主 {@code ChunkMap} 被查询的次数（「没有从真实世界读生成数据」的自证指标）。 */
    private final AtomicInteger hostChunkSourceQueries = new AtomicInteger();

    OfflineChunkRegion(OfflineWorldgenContext ctx, StaticCache2D<GenerationChunkHolder> cache, ChunkStep step,
                       ChunkAccess center) {
        super(ctx.host(), cache, step, center);
        this.offlineSeed = ctx.seed();
        this.offlineRandomState = ctx.randomState();
        this.offlineBiomeSource = ctx.biomeSource();
        this.offlineRandom = offlineRandomState.getOrCreateRandomFactory(WORLDGEN_REGION_RANDOM)
                .at(center.getPos().getWorldPosition());
        this.offlineBiomeManager = new BiomeManager(this, BiomeManager.obfuscateSeed(offlineSeed));
    }

    @Override
    public long getSeed() {
        return offlineSeed;
    }

    @Override
    public RandomSource getRandom() {
        return offlineRandom;
    }

    @Override
    public BiomeManager getBiomeManager() {
        return offlineBiomeManager;
    }

    @Override
    public Holder<Biome> getUncachedNoiseBiome(int quartX, int quartY, int quartZ) {
        return offlineBiomeSource.getNoiseBiome(quartX, quartY, quartZ, offlineRandomState.sampler());
    }

    @Override
    public boolean isOldChunkAround(ChunkPos pos, int range) {
        return false;
    }

    /** 父类会转发到宿主的 {@code ServerChunkCache}；离线链路不该走到这里，因此计数上报。 */
    @Override
    public ChunkSource getChunkSource() {
        hostChunkSourceQueries.incrementAndGet();
        return super.getChunkSource();
    }

    /** 宿主 {@code ChunkMap} 被查询次数（自证指标）。 */
    int hostChunkSourceQueries() {
        return hostChunkSourceQueries.get();
    }
}
