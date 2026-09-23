package com.yiyiaddon.dev.seedpoc;

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
 * 第四轮 · 离线生成区域视图（原版 {@code WorldGenRegion} 的子类）。
 *
 * <p><b>为什么必须继承原版而不是自己写一个 {@code WorldGenLevel}</b>：原版所有生成阶段
 * 都要求具体的 {@code WorldGenRegion} 类型（{@code ChunkGenerator#buildSurface} /
 * {@code #applyCarvers} 直接声明 {@code WorldGenRegion}；{@code ChunkGenerator#applyBiomeDecoration}
 * 里的写权限判定走 {@code WorldGenRegion#ensureCanWrite}）。自己写一份等价实现等于把
 * 「原版怎么写」重抄一遍，任何一处抄错都会被误判成「种子算法有问题」——这正是第三轮
 * {@code ensureCanWrite} 踩过的坑。所以这里只做<b>最少量的覆盖</b>。</p>
 *
 * <p><b>只覆盖「种子相关的读取」</b>（共 5 处，逐条给依据）：</p>
 * <ol>
 *     <li>{@link #getSeed()}：父类从宿主世界取种子（WorldGenRegion.java:86），
 *         这里改成被试种子——否则装饰随机（{@code applyBiomeDecoration:328}）与雕刻器
 *         （{@code ChunkStatusTasks#generateCarvers} 的 seed 参数）会用到真实世界的种子；</li>
 *     <li>{@link #getRandom()}：父类用宿主 {@code ChunkMap} 的 RandomState 构造
 *         （WorldGenRegion.java:88）；这里换成用被试种子的 RandomState 按同一口径构造
 *         （同名随机源 {@code minecraft:worldgen_region_random}，同一坐标锚点）；</li>
 *     <li>{@link #getBiomeManager()}：父类用宿主种子（WorldGenRegion.java:90）；
 *         这里换成被试种子（{@code BiomeManager.obfuscateSeed} 与原版同一 API）；</li>
 *     <li>{@link #getUncachedNoiseBiome(int, int, int)}：父类转发给宿主世界
 *         （WorldGenRegion.java:179-181，宿主用真实世界种子）；这里直接问自建生物群系源；</li>
 *     <li>{@link #isOldChunkAround(ChunkPos, int)}：父类查宿主 {@code ChunkMap}
 *         （WorldGenRegion.java:93-95）。原版用它决定「要不要做旧区块混合」
 *         （{@code Blender.of} 第一句）。本实验全程都是新生成区块（测试世界每次重建），
 *         所以两侧都应为 false；这里固定返回 false，等于「不向真实世界要任何判断」，
 *         并且由报告里的对照检查自证真实侧那一刻也是 false。</li>
 * </ol>
 *
 * <p>其余读取（维度类型、世界高度、注册表、写权限、{@code getChunk} 的依赖半径与状态检查）
 * 一律沿用父类实现——那些恰恰是我们想验证「原版自己是这么做的」的部分。</p>
 */
final class OfflineChunkRegion extends WorldGenRegion {

    /**
     * 装饰期区域随机源的注册名。
     *
     * <p>与 {@code WorldGenRegion} 私有常量逐字相同（WorldGenRegion.java:79），
     * 因为它参与 {@code RandomState#getOrCreateRandomFactory} 的哈希派生——
     * 名字不同就会拿到另一条随机流。</p>
     */
    private static final Identifier WORLDGEN_REGION_RANDOM = Identifier.withDefaultNamespace("worldgen_region_random");

    private final long offlineSeed;
    private final RandomSource offlineRandom;
    private final BiomeManager offlineBiomeManager;
    private final BiomeSource offlineBiomeSource;
    private final RandomState offlineRandomState;
    /** 宿主 {@code ChunkMap} 被查询的次数（自证「没有从真实世界读生成数据」）。 */
    private final AtomicInteger hostChunkSourceQueries = new AtomicInteger();

    OfflineChunkRegion(ServerLevel host, StaticCache2D<GenerationChunkHolder> cache, ChunkStep step,
                       ChunkAccess center, long seed, RandomState randomState, BiomeSource biomeSource) {
        super(host, cache, step, center);
        this.offlineSeed = seed;
        this.offlineRandomState = randomState;
        this.offlineBiomeSource = biomeSource;
        this.offlineRandom = randomState.getOrCreateRandomFactory(WORLDGEN_REGION_RANDOM)
                .at(center.getPos().getWorldPosition());
        this.offlineBiomeManager = new BiomeManager(this, BiomeManager.obfuscateSeed(seed));
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

    /**
     * 父类会转发到宿主的 {@code ServerChunkCache}。离线链路不该走到这里，
     * 因此这里计数并如实上报——计数一旦不为 0，说明有原版代码试图从真实世界取生成数据，
     * 报告必须标出来。
     */
    @Override
    public ChunkSource getChunkSource() {
        hostChunkSourceQueries.incrementAndGet();
        return super.getChunkSource();
    }

    /** 宿主 {@code ChunkMap} 被查询次数（报告用）。 */
    int hostChunkSourceQueries() {
        return hostChunkSourceQueries.get();
    }
}
