package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.model.SeedOreTarget;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.worldgen.OfflineChunkCache;
import com.yiyiaddon.seed.worldgen.OfflineChunkPipeline;
import com.yiyiaddon.seed.worldgen.OfflineWorldgenContext;
import com.yiyiaddon.seed.worldgen.OreChunkReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式模块 · 预测会话（一个「种子 + 维度」下共享的一份离线世界）。
 *
 * <p><b>为什么必须有它</b>：目标区块的最终钻石里有一部分是邻区块各自那一次
 * {@code applyBiomeDecoration} 按写半径写进来的。要算出这部分，就必须让目标 ± 写半径的每个 viewer
 * 都在<b>同一份离线世界状态</b>里跑它自己的 {@code FEATURES}：viewer A 写进去的方块，viewer B 执行时
 * 真的要存在，否则跨区块写入的先后叠加关系就丢了。第六轮 PoC 已经实测这条路线有效
 * （Seed 20260922 固定 10 目标：真值 243 / 预测 243 / 漏报 0 / 错报 0 / 逐 BlockPos 10/10）。</p>
 *
 * <p><b>缓存与隔离</b>（正式化第一阶段口径第十八节）：</p>
 * <ul>
 *     <li>会话键 = （Minecraft 版本, 种子, 维度），由 {@link SeedOrePredictor} 负责隔离；
 *         会话内部只有一个种子的一份离线世界，跨种子不可能共用；</li>
 *     <li>会话内区块键 = {@link ChunkPos}，键下按生成状态分槽（见 {@link OfflineChunkCache}），
 *         因此相邻目标区块能复用已铺好的前置阶段（这是「相邻目标比冷启动快一个量级」的来源）；</li>
 *     <li>调度敏感复核使用<b>另一份</b>离线世界（{@link ScheduleSensitivityAnalyzer} 自己的缓存），
 *         反向顺序写进去的方块不会污染基线世界。</li>
 *     <li><b>暖会话一致性（237）</b>：目标区块第一次被装饰的那一次取到的「外来写入者」证据被记住
 *         （见 {@link #firstForeignWriterEvidence}），后续暖请求照旧做调度敏感复核，
 *         因此同一格的分类不会因为缓存变暖而退化。</li>
 * </ul>
 *
 * <p><b>生命周期</b>（口径第十九节）：{@link #clear()} 释放本会话全部离线区块产出，
 * {@link #close()} 关闭会话（关闭后的一切预测请求都返回失败结果，绝不返回空集合冒充「没有矿」）。
 * 世界断开 → 由上层调用 {@code close()}；Seed 修改 / 维度切换 → 由
 * {@link SeedOrePredictor} 换用另一个会话对象，旧会话不受影响。</p>
 *
 * <p><b>线程安全</b>（口径第二十、三十八节）：本类的全部公开方法都是 {@code synchronized}，
 * 内部状态全部是实例字段（没有任何全局可变 static），因此「整会话只有一个线程在驱动」这个前提
 * 就是它的并发策略；这样以后放进后台任务时，同一个会话不会被两个线程同时推进。
 * 本类也<b>不引用任何客户端全局单例</b>（{@code MinecraftClient} 之类），
 * 需要的环境由构造参数传入。</p>
 */
public final class PredictionSession implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词（中文，便于一次检索到正式层输出）。 */
    private static final String LOG_KEY = "种子挖矿";

    /** 会话的种子。 */
    private final long seed;

    /** 会话的维度。 */
    private final ResourceKey<Level> dimension;

    /** 只读的种子上下文（自建 BiomeSource / 生成器 / RandomState / 结构状态）。 */
    private final OfflineWorldgenContext context;

    /** 基线世界的区块缓存。 */
    private final OfflineChunkCache cache = new OfflineChunkCache();

    /** 基线世界流水线。 */
    private final OfflineChunkPipeline pipeline;

    /** 调度敏感分析器（自带一份独立的复核世界）。 */
    private final ScheduleSensitivityAnalyzer analyzer;

    /**
     * 目标区块 → <b>首次有效基线执行</b>观测到的外来写入者个数（237 修复）。
     *
     * <p>为什么必须记住：触发「调度敏感复核」的前提是「基线执行中确有其它 viewer 改过目标区块」，
     * 而这件事只在目标区块<b>第一次被装饰</b>的那一次成立；会话内后续请求全部命中缓存，
     * 指纹不再变化，触发条件就丢了 —— 同一格于是出现「冷会话 SCHEDULE_SENSITIVE / 暖会话 UNRESOLVED」
     * 两套分类。这里保存的是<b>会话内首次真实观测到的读数</b>（不是推断出来的），
     * 后续暖请求拿它与本次读数取较大者，让分类语义在会话内保持稳定。</p>
     *
     * <p>键只到目标区块、不含矿物：写入者判定走的是<b>全区块方块指纹</b>，与「找哪一种矿」无关。</p>
     */
    private final Map<ChunkPos, Integer> firstForeignWriterEvidence = new LinkedHashMap<>();

    /** 会话是否已关闭。 */
    private boolean closed;

    private PredictionSession(OfflineWorldgenContext context, long seed, ResourceKey<Level> dimension) {
        this.context = context;
        this.seed = seed;
        this.dimension = dimension;
        this.pipeline = new OfflineChunkPipeline(context, cache);
        this.analyzer = new ScheduleSensitivityAnalyzer(context);
    }

    /**
     * 打开一个会话（只凭种子构造离线 worldgen，不读真实世界的任何方块状态）。
     *
     * @param host      环境宿主（只取与种子无关的环境参数；必须是本会话维度的那一层）
     * @param seed      被试种子
     * @param dimension 本会话服务的维度
     */
    public static PredictionSession open(ServerLevel host, long seed, ResourceKey<Level> dimension) {
        if (!dimension.equals(host.dimension())) {
            throw new IllegalArgumentException("会话维度与宿主维度不一致：会话=" + dimension.identifier()
                    + "，宿主=" + host.dimension().identifier() + "（离线 worldgen 的环境参数只能来自同维度宿主）");
        }
        return new PredictionSession(OfflineWorldgenContext.create(host, seed), seed, dimension);
    }

    /** 会话的种子。 */
    public long seed() {
        return seed;
    }

    /** 会话的维度。 */
    public ResourceKey<Level> dimension() {
        return dimension;
    }

    /** 会话是否已关闭。 */
    public synchronized boolean isClosed() {
        return closed;
    }

    /** 基线世界当前持有的区块数（缓存规模）。 */
    public synchronized int cachedChunks() {
        return cache.size();
    }

    /** 复核世界当前持有的区块数（缓存规模）。 */
    public synchronized int probeCachedChunks() {
        return analyzer.cachedChunks();
    }

    /**
     * 预测一个目标区块里的矿物（纯 Seed）。
     *
     * <p>算法与 PoC 第六轮逐行一致（迁移不改算法）：</p>
     * <ol>
     *     <li>按原版规则推导可能写进目标区块的 viewer 集合（写半径现算，见
     *         {@link OfflineChunkPipeline#viewersFor}）；</li>
     *     <li>在共享离线世界里把目标 ± 写半径所需的前置阶段铺好；</li>
     *     <li>逐个 viewer 跑它自己的 {@code FEATURES}（由远到近、目标最后），
     *         每遍前后各读一次目标区块，差集即「这一遍写进目标区块的矿」——跨区块写入因此天然包含在内；</li>
     *     <li><b>另外</b>用另一种合法顺序在独立世界里复核一遍，只用于判定调度敏感
     *         （见 {@link ScheduleSensitivityAnalyzer}）；</li>
     *     <li>全部跑完后只读目标区块，给出最终矿物集合与每块的确定性分类。</li>
     * </ol>
     */
    public synchronized PredictionResult predict(SeedOreTarget request) {
        long startedAt = System.currentTimeMillis();
        if (closed) {
            return PredictionResult.failure(request, "会话已关闭", 0);
        }
        if (request.seed() != seed) {
            return PredictionResult.failure(request, "种子与会话不一致（会话种子 " + seed + "）", 0);
        }
        if (!request.dimension().equals(dimension)) {
            return PredictionResult.failure(request,
                    "维度与会话不一致（会话维度 " + dimension.identifier() + "）", 0);
        }
        OreType oreType = request.oreType();
        SeedDimensionProfile profile = context.profile();
        if (!SeedOreRegistry.supports(profile, oreType)) {
            return PredictionResult.failure(request, profile.displayNameCn() + "不支持"
                    + oreType.displayNameCn() + "预测", 0);
        }
        ChunkPos target = request.chunk();
        try {
            return predictTarget(request, target, oreType, startedAt);
        } catch (Throwable error) {
            // 预测失败必须显式上报，绝不允许「异常 → 空集合 → 被当成这个区块没有矿」
            LOGGER.error("{}：预测失败 {}", LOG_KEY, request.describeCn(), error);
            return PredictionResult.failure(request,
                    "预测异常：" + error.getClass().getSimpleName() + (error.getMessage() == null
                            ? "" : ": " + error.getMessage()),
                    System.currentTimeMillis() - startedAt);
        }
    }

    /** 清空会话持有的全部离线区块产出（复核重启 / 内存回收用）。 */
    public synchronized void clear() {
        cache.clear();
        analyzer.clear();
        // 证据属于「那份被清掉的离线世界」：世界一清，区块会重新装饰，首次执行读数必然重新取得
        firstForeignWriterEvidence.clear();
    }

    @Override
    public synchronized void close() {
        if (closed) {
            return;
        }
        clear();
        closed = true;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 预测主体
    // ────────────────────────────────────────────────────────────────────────

    private PredictionResult predictTarget(SeedOreTarget request, ChunkPos target, OreType oreType,
                                           long startedAt) {
        long stagesBefore = pipeline.stageRuns();
        long skippedBefore = pipeline.stagesSkipped();
        int chunksBefore = cache.size();
        int hostQueriesBefore = pipeline.hostChunkSourceQueries();
        int probeChunksBefore = analyzer.cachedChunks();

        int writeRadius = OfflineChunkPipeline.featureWriteRadius();
        pipeline.prepare(target, writeRadius);

        List<ChunkPos> viewers = OfflineChunkPipeline.viewersFor(target);
        // 坐标 → 改过这一格「钻石存在性」的 viewer 列表（按发生先后）；坐标 → 最后一次把它变成钻石的 viewer
        Map<BlockPos, List<ChunkPos>> writersByPosition = new LinkedHashMap<>();
        Map<BlockPos, ChunkPos> originByPosition = new LinkedHashMap<>();
        // 除目标自身外、真的往目标区块里写过方块的 viewer（= 跨 viewer 竞争的前提，见下面 blockFingerprint）
        Set<ChunkPos> foreignWriters = new LinkedHashSet<>();
        Set<BlockPos> current = readOres(target, oreType);
        long currentFingerprint = blockFingerprint(latestChunkOrFail(target));
        for (ChunkPos viewer : viewers) {
            Set<BlockPos> before = current;
            pipeline.decorate(viewer);
            ChunkAccess chunk = latestChunkOrFail(target);
            long fingerprint = blockFingerprint(chunk);
            Set<BlockPos> after = OreChunkReader.collect(chunk, oreType, context.profile());
            if (!viewer.equals(target) && fingerprint != currentFingerprint) {
                foreignWriters.add(viewer);
            }
            for (BlockPos pos : symmetricDifference(before, after)) {
                writersByPosition.computeIfAbsent(pos, ignored -> new ArrayList<>()).add(viewer);
            }
            for (BlockPos pos : after) {
                if (!before.contains(pos)) {
                    originByPosition.put(pos, viewer);
                }
            }
            current = after;
            currentFingerprint = fingerprint;
        }
        Set<BlockPos> predicted = new LinkedHashSet<>(current);

        ChunkAccess finalChunk = pipeline.featuresChunk(target);
        if (finalChunk == null) {
            return PredictionResult.failure(request, "目标区块没有跑到 FEATURES，本次预测不成立",
                    System.currentTimeMillis() - startedAt);
        }

        // 调度敏感复核的有效证据：本次基线执行观测到的写入者，与会话内首次有效基线执行记住的取较大者。
        // 目标区块暖起来之后本次读数必然为 0，若不用记住的那一份，同一格的分类就会在会话内前后不一致。
        int observedForeignWriters = foreignWriters.size();
        Integer rememberedForeignWriters = firstForeignWriterEvidence.get(target);
        int evidenceForeignWriters = Math.max(observedForeignWriters,
                rememberedForeignWriters == null ? 0 : rememberedForeignWriters);
        if (observedForeignWriters > 0) {
            firstForeignWriterEvidence.put(target, observedForeignWriters);
        }
        boolean evidenceRemembered = observedForeignWriters <= 0 && evidenceForeignWriters > 0;
        Set<BlockPos> scheduleSensitive = analyzer.analyze(target, oreType, predicted,
                evidenceForeignWriters, evidenceRemembered);

        List<PredictedOre> ores = new ArrayList<>(predicted.size());
        int sensitiveCount = 0;
        for (BlockPos pos : predicted) {
            boolean sensitive = scheduleSensitive.contains(pos);
            if (sensitive) {
                sensitiveCount++;
            }
            ores.add(new PredictedOre(pos, oreType,
                    sensitive ? PredictionCertainty.SCHEDULE_SENSITIVE : PredictionCertainty.UNRESOLVED,
                    OreSource.UNATTRIBUTED,
                    originByPosition.get(pos),
                    sensitive ? writersByPosition.getOrDefault(pos, List.of()) : List.of()));
        }
        long elapsed = System.currentTimeMillis() - startedAt;

        List<String> notes = new ArrayList<>();
        notes.add("viewer 集合 " + viewers.size() + " 个（写半径 " + writeRadius + "，由 ChunkPyramid 现算）");
        notes.add("基线执行中改过目标区块方块的其它 viewer（本次）：" + observedForeignWriters
                + " 个（按目标区块全区块方块指纹判定，不是只看钻石）；本目标本会话首次有效执行读数："
                + (observedForeignWriters > 0 ? String.valueOf(observedForeignWriters)
                : (rememberedForeignWriters == null ? "（无，该区块本次是首次有效装饰）"
                : String.valueOf(rememberedForeignWriters))));
        notes.add("调度敏感复核采用的有效外来写入者证据：" + evidenceForeignWriters
                + " 个" + (evidenceRemembered ? "（本次暖会话，复用首次有效基线执行的证据）" : "（本次基线执行实测）"));
        notes.addAll(analyzer.notes());
        notes.add("复核世界累计持有区块：" + analyzer.cachedChunks() + " 个（本次新增 "
                + (analyzer.cachedChunks() - probeChunksBefore) + "）；基线世界累计持有区块："
                + cache.size() + " 个");
        notes.add("宿主 ChunkMap 查询次数增量：" + (pipeline.hostChunkSourceQueries() - hostQueriesBefore)
                + "（必须为 0，>0 说明有原版代码试图从真实世界取生成数据）");

        PredictionResult.Stats stats = new PredictionResult.Stats(
                cache.size() - chunksBefore,
                pipeline.stageRuns() - stagesBefore,
                pipeline.stagesSkipped() - skippedBefore,
                cache.size(),
                pipeline.hostChunkSourceQueries() - hostQueriesBefore,
                foreignWriters.size(),
                analyzer.executed(),
                0,
                sensitiveCount,
                ores.size() - sensitiveCount,
                notes);
        return new PredictionResult(request, ores, true, null, elapsed, stats);
    }

    /** 读某区块当前（已是该区块跑到的最远状态）的产出；区块缺失即抛异常，由上层转成失败结果。 */
    private ChunkAccess latestChunkOrFail(ChunkPos pos) {
        ChunkAccess chunk = cache.latestChunk(pos);
        if (chunk == null) {
            throw new IllegalStateException("离线区块缺失：(" + pos.x() + "," + pos.z() + ")");
        }
        return chunk;
    }

    /** 读某区块当前的矿物集合。 */
    private Set<BlockPos> readOres(ChunkPos pos, OreType oreType) {
        return OreChunkReader.collect(latestChunkOrFail(pos), oreType, context.profile());
    }

    /**
     * 一个离线区块的<b>方块指纹</b>（全区块逐格方块状态编号的滚动哈希）。
     *
     * <p><b>它解决什么</b>：调度敏感分析的触发条件必须是「另一个 viewer 有没有真的往目标区块里写过方块」，
     * 只看钻石成员是否变化是不够的——一块砾石/凝灰岩完全可能在不改变钻石成员的前提下，
     * 让另一种顺序下的钻石候选被 {@code canPlaceOre} 拒掉（228 报告第七节实测的正是这种「先写者胜」）。
     * 只看钻石会把这类竞争漏掉，于是把本该复核的目标当成「没有竞争」。
     * 指纹只回答「变了没有」，不做任何位置级推断，因此不会引入新的假设。</p>
     *
     * <p>取 {@code Block#getId(BlockState)}（Block.java:132）作为格值，逐 section 升序、逐 y/z/x 升序组合，
     * 保证同一份世界状态在同一版本上稳定得到同一个值。</p>
     */
    private static long blockFingerprint(ChunkAccess chunk) {
        long hash = 1125899906842597L;
        int sections = chunk.getSectionsCount();
        for (int section = 0; section < sections; section++) {
            LevelChunkSection levelSection = chunk.getSection(section);
            if (levelSection == null || levelSection.hasOnlyAir()) {
                continue;
            }
            for (int localY = 0; localY < 16; localY++) {
                for (int localZ = 0; localZ < 16; localZ++) {
                    for (int localX = 0; localX < 16; localX++) {
                        BlockState state = levelSection.getBlockState(localX, localY, localZ);
                        hash = hash * 31L + Block.getId(state);
                    }
                }
            }
        }
        return hash;
    }

    /** 两个集合的对称差（= 这一遍真正改变了存在性的坐标）。 */
    private static Set<BlockPos> symmetricDifference(Set<BlockPos> before, Set<BlockPos> after) {
        Set<BlockPos> diff = new LinkedHashSet<>();
        for (BlockPos pos : after) {
            if (!before.contains(pos)) {
                diff.add(pos);
            }
        }
        for (BlockPos pos : before) {
            if (!after.contains(pos)) {
                diff.add(pos);
            }
        }
        return diff;
    }
}
