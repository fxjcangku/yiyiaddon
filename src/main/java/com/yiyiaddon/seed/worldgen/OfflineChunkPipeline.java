package com.yiyiaddon.seed.worldgen;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.SharedConstants;
import net.minecraft.util.StaticCache2D;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.Blender;

/**
 * 种子挖矿正式模块 · 离线区块生成流水线（只凭种子把区块跑到 {@code FEATURES}）。
 *
 * <p><b>它做的是「驱动」，不是「重写」</b>：噪声、生物群系、地表、雕刻器、地物这五件事一律调用原版实现，
 * 本类只负责原版 {@code ChunkMap} / {@code ChunkGenerationTask} 做的那两件事——「按什么半径铺哪些状态」
 * 与「每个状态调用哪些原版方法」。逐行依据：</p>
 *
 * <ul>
 *     <li><b>半径与分层</b>：{@code ChunkGenerationTask#create:36-42} 用
 *         {@code ChunkPyramid.GENERATION_PYRAMID.getStepTo(targetStatus).getAccumulatedRadiusOf(EMPTY)}
 *         作为缓存半径（对 FEATURES 是 9，即 19x19 区块）；{@code #scheduleLayer:117-136} 对每个状态用
 *         {@code getStepTo(targetStatus).getAccumulatedRadiusOf(status)} 作为该层的半径。
 *         本类 {@link #CACHE_RADIUS} 与 {@link #prepare} 就是这两行。</li>
 *     <li><b>每个状态调用什么</b>：{@code ChunkStatusTasks}（26.1.2 本线）的
 *         {@code generateStructureStarts} / {@code generateStructureReferences} / {@code generateBiomes} /
 *         {@code generateNoise} / {@code generateSurface} / {@code generateCarvers} / {@code generateFeatures}
 *         七段，见 {@link #applyStage}；唯一改动是把「种子相关参数」换成离线上下文自己的，
 *         并把 {@code new WorldGenRegion(...)} 换成 {@link OfflineChunkRegion}。</li>
 *     <li><b>区块对象从哪来</b>：{@code ChunkMap#createEmptyChunk:594-597} 的
 *         {@code new ProtoChunk(pos, UpgradeData.EMPTY, level, level.palettedContainerFactory(), null)}，
 *         这里逐字相同。</li>
 *     <li><b>状态推进</b>：{@code ChunkStep#completeChunkGeneration:30-40} 在 ProtoChunk 上调
 *         {@code setPersistedStatus(targetStatus)}；本类在每阶段结束时照做
 *         （高度图与 {@code heightmapsAfter} 依赖它）。</li>
 * </ul>
 *
 * <p><b>幂等性</b>：{@link #applyStage} 见到「该状态已有产出」直接跳过。这是
 * 「同一份离线世界里多个 viewer 各跑一遍 FEATURES、谁也不重复跑」与「相邻目标区块复用前置阶段」
 * 的实现依据，也是缓存命中的来源。</p>
 *
 * <p>本类<b>不读宿主世界的任何方块状态</b>：宿主的 {@code ChunkMap} 只可能被
 * {@link OfflineChunkRegion#getChunkSource()} 这条口子碰到，这里累计上报。</p>
 */
public final class OfflineChunkPipeline {

    /**
     * 缓存半径 = 原版 {@code ChunkGenerationTask#create} 对 FEATURES 的算法
     * （ChunkGenerationTask.java:37-40）。对 26.1.2 的 FEATURES 等于 9 → 19x19 区块。
     */
    private static final int CACHE_RADIUS = ChunkPyramid.GENERATION_PYRAMID
            .getStepTo(ChunkStatus.FEATURES).getAccumulatedRadiusOf(ChunkStatus.EMPTY);

    /** FEATURES 之前的阶段（= 原版必须由依赖关系驱动的那一批）。 */
    private static final List<ChunkStatus> STAGES_BEFORE_FEATURES = List.of(
            ChunkStatus.EMPTY,
            ChunkStatus.STRUCTURE_STARTS,
            ChunkStatus.STRUCTURE_REFERENCES,
            ChunkStatus.BIOMES,
            ChunkStatus.NOISE,
            ChunkStatus.SURFACE,
            ChunkStatus.CARVERS);

    /** 离线上下文（种子相关的东西全在这里）。 */
    private final OfflineWorldgenContext ctx;

    /** 本流水线独占的一份离线世界区块缓存。 */
    private final OfflineChunkCache cache;

    /** 阶段执行次数（性能与复用统计）。 */
    private long stageRuns;

    /** 「因已有产出而跳过」的阶段次数（= 缓存命中）。 */
    private long stagesSkipped;

    /** FEATURES 执行次数（= 真跑过的 viewer 数）。 */
    private long featureRuns;

    /** 向宿主 {@code ChunkMap} 的查询次数累计（自证指标）。 */
    private int hostChunkSourceQueries;

    public OfflineChunkPipeline(OfflineWorldgenContext ctx, OfflineChunkCache cache) {
        this.ctx = ctx;
        this.cache = cache;
    }

    /** 本流水线独占的区块缓存。 */
    public OfflineChunkCache cache() {
        return cache;
    }

    /**
     * 建立观察窗口，并把 {@code center ± extraRadius} 所需的前置阶段（FEATURES 之前）全部铺好。
     *
     * @param center      窗口中心（= 目标区块）
     * @param extraRadius 额外圈数（本轮 = FEATURES 写半径；0 = 只铺中心自己）
     */
    public void prepare(ChunkPos center, int extraRadius) {
        ChunkStep featuresStep = ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES);
        // 缓存半径 = 原版对 FEATURES 的算法；装饰邻域时每个邻域区块自己也要它自己的缓存圈，
        // 所以整体再加 extraRadius 圈，否则邻域装饰会请求到缓存外的区块
        cache.openWindow(center, CACHE_RADIUS + extraRadius);
        for (ChunkStatus status : STAGES_BEFORE_FEATURES) {
            int radius = featuresStep.getAccumulatedRadiusOf(status) + extraRadius;
            for (int x = center.x() - radius; x <= center.x() + radius; x++) {
                for (int z = center.z() - radius; z <= center.z() + radius; z++) {
                    applyStage(status, cache.holder(x, z));
                }
            }
        }
    }

    /**
     * 让一个区块完成它自己的 {@code FEATURES}（= 一次原版 {@code applyBiomeDecoration}）。
     *
     * <p>必须先在同一个窗口里 {@link #prepare} 过；已跑过 FEATURES 的区块直接跳过（幂等）。</p>
     */
    public void decorate(ChunkPos viewer) {
        if (cache.window() == null) {
            throw new IllegalStateException("离线观察窗口尚未建立：必须先调用 prepare()");
        }
        applyStage(ChunkStatus.FEATURES, cache.holder(viewer.x(), viewer.z()));
    }

    /** 取某区块跑完 {@code FEATURES} 之后的产出；没跑到该状态返回 null。 */
    public ChunkAccess featuresChunk(ChunkPos pos) {
        return cache.chunkAt(pos, ChunkStatus.FEATURES);
    }

    /** 累计阶段执行次数。 */
    public long stageRuns() {
        return stageRuns;
    }

    /** 累计「因已有产出而跳过」的阶段次数（缓存命中）。 */
    public long stagesSkipped() {
        return stagesSkipped;
    }

    /** 累计 FEATURES 执行次数。 */
    public long featureRuns() {
        return featureRuns;
    }

    /** 累计「宿主 ChunkMap 被查询」次数（0 = 完全没有从真实世界取生成数据）。 */
    public int hostChunkSourceQueries() {
        return hostChunkSourceQueries;
    }

    // ────────────────────────────────────────────────────────────────────────
    // FEATURES 写半径与 viewer 集合（每次运行都从原版 ChunkPyramid 现算）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * {@code FEATURES} 步骤允许的方块写入半径（区块）。
     *
     * <p>依据 {@code ChunkPyramid.GENERATION_PYRAMID} 的 {@code blockStateWriteRadius}
     * （26.1.2 = 1），也就是 {@code WorldGenRegion#ensureCanWrite}（WorldGenRegion.java:230-259）
     * 用来判断「这次写入是否被允许」的那个值。</p>
     */
    public static int featureWriteRadius() {
        return featuresStep().blockStateWriteRadius();
    }

    /**
     * 「可能把方块写进目标区块」的 viewer 集合，<b>由写半径动态推导，不是写死的 3x3</b>。
     *
     * <p>顺序为「由远到近、目标区块最后」：① 目标最后装饰 ⇒ 读到的是邻域跨区块写入做完之后的最终状态；
     * ② 与基线预测器的固定顺序语义一致。该顺序<b>只影响哪一份合法结果被取作基线</b>，
     * 其「是否依赖顺序」由 {@code ScheduleSensitivityAnalyzer} 单独判定。</p>
     */
    public static List<ChunkPos> viewersFor(ChunkPos target) {
        int radius = featureWriteRadius();
        List<ChunkPos> viewers = new ArrayList<>();
        for (int distance = radius; distance >= 0; distance--) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != distance) {
                        continue;
                    }
                    ChunkPos viewer = new ChunkPos(target.x() + dx, target.z() + dz);
                    // 集合成员一律走同一条判据（= 原版 ensureCanWrite 的写半径判定），不另立第二套口径
                    if (canWriteInto(viewer, target)) {
                        viewers.add(viewer);
                    }
                }
            }
        }
        return List.copyOf(viewers);
    }

    /** 某个 viewer 这一次装饰是否被允许写进目标区块（= 原版 {@code ensureCanWrite} 的判据）。 */
    public static boolean canWriteInto(ChunkPos viewer, ChunkPos target) {
        int radius = featureWriteRadius();
        return Math.abs(viewer.x() - target.x()) <= radius && Math.abs(viewer.z() - target.z()) <= radius;
    }

    /** 写半径的推导依据（进报告，含该步骤的写半径与依赖表原文）。 */
    public static String writeRadiusEvidence() {
        ChunkStep step = featuresStep();
        return "写半径来源：ChunkPyramid.GENERATION_PYRAMID.getStepTo(FEATURES).blockStateWriteRadius() = "
                + step.blockStateWriteRadius() + "；同一步直接依赖=" + step.directDependencies()
                + "；累积依赖=" + step.accumulatedDependencies();
    }

    /** 各阶段「至少需要周围多少圈」（报告用，直接来自 ChunkPyramid）。 */
    public static List<String> dependencyTable() {
        List<String> lines = new ArrayList<>();
        ChunkStep featuresStep = featuresStep();
        for (ChunkStatus status : STAGES_BEFORE_FEATURES) {
            lines.add("  " + status.getName() + "：铺到半径 " + featuresStep.getAccumulatedRadiusOf(status));
        }
        lines.add("  FEATURES：铺到半径 0（目标区块自己）");
        lines.add("  离线缓存半径（照原版 ChunkGenerationTask 算）：" + CACHE_RADIUS
                + " 区块 → 每层最多 " + (CACHE_RADIUS * 2 + 1) + "x" + (CACHE_RADIUS * 2 + 1) + " 区块");
        return lines;
    }

    private static ChunkStep featuresStep() {
        return ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 阶段执行
    // ────────────────────────────────────────────────────────────────────────

    /** 跑一个阶段（原版 {@code ChunkStatusTasks} 的逐行对应，见类注释）。 */
    private void applyStage(ChunkStatus status, OfflineChunkHolder holder) {
        if (holder.get(status) != null) {
            stagesSkipped++;
            return;
        }
        ChunkPos pos = holder.getPos();
        if (status == ChunkStatus.EMPTY) {
            // 原版 ChunkMap#createEmptyChunk（ChunkMap.java:594-597）
            holder.put(status, new ProtoChunk(pos, UpgradeData.EMPTY, ctx.host(), ctx.containerFactory(), null));
            stageRuns++;
            return;
        }

        ChunkStep step = ChunkPyramid.GENERATION_PYRAMID.getStepTo(status);
        ChunkAccess chunk = holder.get(status.getParent());
        if (chunk == null) {
            throw new IllegalStateException("离线生成父状态缺失：" + status + " @" + pos.x() + "," + pos.z());
        }
        StaticCache2D<GenerationChunkHolder> window = cache.window();
        OfflineChunkRegion region = new OfflineChunkRegion(ctx, window, step, chunk);

        if (status == ChunkStatus.STRUCTURE_STARTS) {
            // 对应 ChunkStatusTasks#generateStructureStarts:43-52
            if (ctx.generateStructures()) {
                ctx.generator().createStructures(ctx.registries(), ctx.structureState(), ctx.hostStructureManager(),
                        chunk, ctx.templateManager(), ctx.host().dimension());
            }
        } else if (status == ChunkStatus.STRUCTURE_REFERENCES) {
            // 对应 generateStructureReferences:65-68
            ctx.generator().createReferences(region, ctx.hostStructureManager().forWorldGenRegion(region), chunk);
        } else if (status == ChunkStatus.BIOMES) {
            // 对应 generateBiomes:74-76
            ctx.generator().createBiomes(ctx.randomState(), Blender.of(region),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk).join();
        } else if (status == ChunkStatus.NOISE) {
            // 对应 generateNoise:82-98
            chunk = ctx.generator().fillFromNoise(Blender.of(region), ctx.randomState(),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk).join();
            chunk = applyBelowZeroRetrogen(chunk);
        } else if (status == ChunkStatus.SURFACE) {
            // 对应 generateSurface:104-107
            ctx.generator().buildSurface(region, ctx.hostStructureManager().forWorldGenRegion(region),
                    ctx.randomState(), chunk);
        } else if (status == ChunkStatus.CARVERS) {
            // 对应 generateCarvers:113-122
            if (chunk instanceof ProtoChunk protoChunk) {
                Blender.addAroundOldChunksCarvingMaskFilter(region, protoChunk);
            }
            ctx.generator().applyCarvers(region, ctx.seed(), ctx.randomState(), region.getBiomeManager(),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk);
        } else if (status == ChunkStatus.FEATURES) {
            // 对应 generateFeatures:129-139
            Heightmap.primeHeightmaps(chunk, EnumSet.of(Heightmap.Types.MOTION_BLOCKING,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR,
                    Heightmap.Types.WORLD_SURFACE));
            if (!SharedConstants.DEBUG_DISABLE_FEATURES) {
                ctx.generator().applyBiomeDecoration(region, chunk,
                        ctx.hostStructureManager().forWorldGenRegion(region));
            }
            Blender.generateBorderTicks(region, chunk);
            featureRuns++;
        } else {
            throw new IllegalStateException("离线流水线不支持的状态：" + status);
        }

        // 对应 ChunkStep#completeChunkGeneration（ChunkStep.java:30-40）
        if (chunk instanceof ProtoChunk protoChunk && protoChunk.getPersistedStatus().isBefore(status)) {
            protoChunk.setPersistedStatus(status);
        }
        holder.put(status, chunk);
        stageRuns++;
        hostChunkSourceQueries += region.hostChunkSourceQueries();
    }

    /** 原版 {@code generateNoise} 里对下界升级区块的两步修补（普通主世界区块不触发）。 */
    private static ChunkAccess applyBelowZeroRetrogen(ChunkAccess chunk) {
        if (chunk instanceof ProtoChunk protoChunk) {
            BelowZeroRetrogen retrogen = protoChunk.getBelowZeroRetrogen();
            if (retrogen != null) {
                BelowZeroRetrogen.replaceOldBedrock(protoChunk);
                if (retrogen.hasBedrockHoles()) {
                    retrogen.applyBedrockMask(protoChunk);
                }
            }
        }
        return chunk;
    }
}
