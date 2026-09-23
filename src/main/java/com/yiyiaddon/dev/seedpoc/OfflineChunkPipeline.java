package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.util.StaticCache2D;
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
 * 第四轮 · 离线区块生成流水线（只凭种子把区块跑到「第一条钻石 feature 之前」）。
 *
 * <p><b>它做的是「驱动」，不是「重写」</b>：噪声、生物群系、地表、雕刻器、地物这五件事
 * 一律调用原版实现，本类只负责原版 {@code ChunkMap} / {@code ChunkGenerationTask} 做的那两件事——
 * <b>按什么半径铺哪些状态</b>、<b>每个状态调用哪些原版方法</b>。两处的逐行依据：</p>
 *
 * <ul>
 *     <li><b>半径与分层</b>：{@code ChunkGenerationTask#create:36-42} 用
 *         {@code ChunkPyramid.GENERATION_PYRAMID.getStepTo(targetStatus).getAccumulatedRadiusOf(EMPTY)}
 *         作为缓存半径（对 FEATURES 是 9，即 19x19 区块）；
 *         {@code #scheduleLayer:117-136} 对每个状态用
 *         {@code getStepTo(targetStatus).getAccumulatedRadiusOf(status)} 作为该层的半径。
 *         本类 {@link #CACHE_RADIUS} 与 {@link #layerRadius} 就是这两行。</li>
 *     <li><b>每个状态调用什么</b>：{@code ChunkStatusTasks}（26.1.2 本线）——
 *         {@code generateStructureStarts:40-53} / {@code generateStructureReferences:62-69} /
 *         {@code generateBiomes:71-77} / {@code generateNoise:79-99} / {@code generateSurface:101-108} /
 *         {@code generateCarvers:110-124} / {@code generateFeatures:126-140}。
 *         本类 {@link #applyStage} 与这几段逐行对应，唯一改动是把「种子相关参数」换成离线上下文自己的
 *         （{@code ctx.seed()} / {@code ctx.randomState()} / {@code ctx.structureState()}），
 *         并把 {@code new WorldGenRegion(...)} 换成 {@link OfflineChunkRegion}。</li>
 *     <li><b>区块对象从哪来</b>：{@code ChunkMap#createEmptyChunk:594-597} 的
 *         {@code new ProtoChunk(pos, UpgradeData.EMPTY, level, level.palettedContainerFactory(), null)}，
 *         这里逐字相同。</li>
 *     <li><b>状态推进</b>：{@code ChunkStep#completeChunkGeneration:30-40} 在 ProtoChunk 上
 *         调 {@code setPersistedStatus(targetStatus)}；本类在每阶段结束时照做
 *         （高度图与 {@code heightmapsAfter} 依赖它）。</li>
 * </ul>
 *
 * <p><b>输出</b>：每个被观察的区块给两份东西——四个阶段 checkpoint（BIOMES/NOISE/SURFACE/CARVERS）
 * 与「第一条钻石 placed_feature 执行前」的 pre-diamond，都是 {@link GenStageSnapshot}，
 * 直接与真实侧同名快照逐 BlockPos 比（{@link StageComparator}）。</p>
 *
 * <p><b>装饰范围</b>：{@link #run} 的 {@code decorRadius} 决定「除目标区块外，还要不要先把周围区块
 * 也装饰掉」。原版生成一个区块时，它周围区块可能已经跑到 FEATURES（真实世界里就是如此，
 * 因为服务端按距离由近到远生成），所以两种口径都要能跑：
 * {@code 0} = 只装饰目标区块（邻域停在 CARVERS，即 pipeline 的最小要求）；
 * {@code n>0} = 先按距离由近到远装饰半径 n 内的邻域，最后再装饰目标区块（邻域已在 FEATURES）。
 * 哪个口径与真实一致，由比较器给出，不靠猜。</p>
 */
final class OfflineChunkPipeline {

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

    private final OfflineWorldgenContext ctx;
    /**
     * 是否「每个 viewer 新建一个独立离线世界」。
     *
     * <p><b>为什么默认要新建</b>：本轮要回答的是「某个区块被装饰的那一刻，它的 3x3 邻域长什么样」。
     * 复用同一个世界时，<b>后一个 viewer 的邻域里会带上前几个 viewer 装饰时写进去的地物</b>
     * （FEATURES 的写半径是 1，装饰会把方块写进相邻区块），于是「离线这一刻」与
     * 「真实那一刻」在邻域上就不可能相同——第四轮首轮把这种差异误当成了生成差异。
     * 每个 viewer 独立新建后，每个 viewer 拿到的都是「这个区块自己是生成波前的中心」这一种口径，
     * 与真实世界里「一个新区块第一次被装饰」的情形对应。</p>
     *
     * <p>跨 viewer 复用仍保留成对照口径（{@code -Dyiyiaddon.seedpoc.offline.reuse=1}），
     * 用来量化「装饰顺序」这一项到底影响多少格。</p>
     */
    private final boolean freshWorldPerViewer;
    /** 各区块的持有者（跨 viewer 复用时存续；每 viewer 新建时每个 viewer 开始前清空）。 */
    private final Map<Long, OfflineChunkHolder> holders = new HashMap<>();
    /** 阶段 checkpoint 与 pre-diamond 共用的方块状态编号表。 */
    private final StatePalette palette = new StatePalette();
    /** 本类自己的快照序号。 */
    private int snapshotOrder;
    /** 已新建的离线世界数（报告用：说明「每个 viewer 一份世界」是否真的生效）。 */
    private int worldsBuilt;
    /** 运行量统计（报告用）。 */
    private long stageRuns;
    private long structureStartRuns;
    private long featureRuns;
    private long biomeRuns;
    private long noiseRuns;
    private long surfaceRuns;
    private long carverRuns;
    /** 报告备注（各轮驱动方往里追加）。 */
    private final List<String> notes = new ArrayList<>();
    /** 离线 region 里「宿主 ChunkMap 被查询次数」累计（自证没有从真实世界读生成数据）。 */
    private int hostChunkSourceQueries;
    /**
     * 本次观察窗口的区块缓存（中心 = 最近一次 {@link #prepare} 的区块）。
     *
     * <p>第六轮把「建缓存 + 铺前置阶段」（{@link #prepare}）与「跑一个区块的 FEATURES」
     * （{@link #decorate}）拆开，是为了让共享会话按 viewer 逐个驱动、并能在每个 viewer
     * 前后各读一次目标区块（写入归属取证）。两者合起来与原来的 {@link #run} 完全等价。</p>
     */
    private StaticCache2D<GenerationChunkHolder> cache;
    /** 当前窗口中心（= 最近一次 {@link #prepare} 的区块）。 */
    private ChunkPos windowCenter;
    /** 因「该状态已有产出」而跳过的阶段次数（共享缓存命中 / 重复生成避免，性能节用）。 */
    private long stagesSkipped;

    OfflineChunkPipeline(OfflineWorldgenContext ctx) {
        this(ctx, false);
    }

    /**
     * @param ctx                离线上下文
     * @param reuseWorldAcrossViewers true = 跨 viewer 复用同一个离线世界（对照口径）；
     *                                false = 每个 viewer 新建独立世界（默认，见 {@link #freshWorldPerViewer}）
     */
    OfflineChunkPipeline(OfflineWorldgenContext ctx, boolean reuseWorldAcrossViewers) {
        this.ctx = ctx;
        this.freshWorldPerViewer = !reuseWorldAcrossViewers;
    }

    /** 一个 viewer 的离线产出。 */
    record Result(ChunkPos viewer, GenStageSnapshot preDiamond, Map<String, GenStageSnapshot> stages) {
    }

    /**
     * 把一个 viewer 跑到「第一条钻石 feature 之前」。
     *
     * @param viewer       目标区块
     * @param decorRadius  邻域装饰半径（0 = 只装饰目标区块；见类注释）
     */
    Result run(ChunkPos viewer, int decorRadius) {
        if (freshWorldPerViewer) {
            // 每个 viewer 一份独立世界：从 EMPTY 重新长起，邻域里不带任何「别的 viewer 装饰过」的痕迹
            holders.clear();
            cache = null;
            windowCenter = null;
            worldsBuilt++;
        }
        Map<String, GenStageSnapshot> stages = new LinkedHashMap<>();
        prepare(viewer, decorRadius, viewer, stages);

        // 邻域先装饰（按切比雪夫距离由近到远），最后才装饰目标区块——
        // 这样目标区块装饰时邻域已经处在 FEATURES，与「先近后远」的真实生成顺序一致
        for (int distance = 1; distance <= decorRadius; distance++) {
            for (ChunkPos pos : ring(viewer, distance)) {
                decorate(pos);
            }
        }

        OfflineStageCapture.beginViewer(viewer);
        GenStageSnapshot preDiamond;
        try {
            decorate(viewer);
        } finally {
            OfflineStageCapture.endViewer();
        }
        preDiamond = OfflineStageCapture.take(viewer);
        return new Result(viewer, preDiamond, stages);
    }

    /**
     * 建立观察窗口并铺好 {@code center} 四周的前置阶段（不含 FEATURES）。
     *
     * <p>与 {@link #run} 里的同一段逐行等价：缓存半径 = 原版对 FEATURES 的累积半径 + {@code extraRadius}；
     * 每个阶段只铺到 {@code getAccumulatedRadiusOf(阶段) + extraRadius}，已产出过的区块直接跳过
     * （共享世界下这是「重复生成避免」的来源）。</p>
     *
     * @param center           窗口中心（= 目标区块）
     * @param extraRadius      额外的装饰圈数（第六轮 = 写半径；0 = 只装饰中心自己）
     * @param checkpointViewer 只对这个区块采阶段 checkpoint；null = 本次不采
     * @param stages           出参：阶段名 → 离线快照（checkpointViewer 为 null 时不会被写入）
     */
    void prepare(ChunkPos center, int extraRadius, ChunkPos checkpointViewer,
                 Map<String, GenStageSnapshot> stages) {
        ChunkStep featuresStep = ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES);
        // 缓存半径 = 原版对 FEATURES 的算法；装饰邻域时每个邻域区块自己也要 10 圈（原版缓存半径
        // 是以它自己为中心算的），所以整体再加 extraRadius 圈，否则邻域装饰会请求到缓存外的区块
        cache = StaticCache2D.create(center.x(), center.z(), CACHE_RADIUS + extraRadius, this::holder);
        windowCenter = center;
        for (ChunkStatus status : STAGES_BEFORE_FEATURES) {
            int radius = featuresStep.getAccumulatedRadiusOf(status) + extraRadius;
            for (int x = center.x() - radius; x <= center.x() + radius; x++) {
                for (int z = center.z() - radius; z <= center.z() + radius; z++) {
                    applyStage(status, holder(x, z), checkpointViewer, stages);
                }
            }
        }
    }

    /**
     * 让一个区块完成它自己的 {@code FEATURES}（= 一次原版 {@code applyBiomeDecoration}）。
     *
     * <p>必须先在同一个窗口里 {@link #prepare} 过；已跑过 FEATURES 的区块会直接跳过
     * （幂等），这正是「9 个 viewer 在同一份离线世界里各跑一遍、谁也不重复跑」的实现依据。</p>
     */
    void decorate(ChunkPos viewer) {
        if (cache == null) {
            throw new IllegalStateException("离线 window 尚未建立：先调用 prepare()");
        }
        applyStage(ChunkStatus.FEATURES, holder(viewer.x(), viewer.z()), null, null);
    }

    /** 当前窗口中心（= 最近一次 {@link #prepare} 的区块）；未建立窗口返回 null。 */
    ChunkPos windowCenter() {
        return windowCenter;
    }

    /** 取某区块某个状态的产出；没跑到返回 null。 */
    ChunkAccess chunkAt(ChunkPos pos, ChunkStatus status) {
        OfflineChunkHolder holder = holders.get(pos.pack());
        return holder == null ? null : holder.get(status);
    }

    /** 取某区块「已经跑到的最远状态」的产出；一个阶段都没跑返回 null。 */
    ChunkAccess latestChunk(ChunkPos pos) {
        OfflineChunkHolder holder = holders.get(pos.pack());
        return holder == null ? null : holder.get(holder.highestStatus());
    }

    /** 已经持有区块对象的区块数（报告用：离线构造规模）。 */
    int chunkCount() {
        return holders.size();
    }

    /** 距离为目标恰好 {@code distance} 的一圈区块（切比雪夫距离，与原版区块半径一致）。 */
    private static List<ChunkPos> ring(ChunkPos center, int distance) {
        List<ChunkPos> ring = new ArrayList<>();
        for (int dx = -distance; dx <= distance; dx++) {
            for (int dz = -distance; dz <= distance; dz++) {
                if (Math.max(Math.abs(dx), Math.abs(dz)) == distance) {
                    ring.add(new ChunkPos(center.x() + dx, center.z() + dz));
                }
            }
        }
        return ring;
    }

    /** 取（必要时新建）某区块的持有者。 */
    private OfflineChunkHolder holder(int chunkX, int chunkZ) {
        long key = ChunkPos.pack(chunkX, chunkZ);
        OfflineChunkHolder existing = holders.get(key);
        if (existing != null) {
            return existing;
        }
        OfflineChunkHolder created = new OfflineChunkHolder(new ChunkPos(chunkX, chunkZ));
        holders.put(key, created);
        return created;
    }

    /**
     * 跑一个阶段（原版 {@code ChunkStatusTasks} 的逐行对应，见类注释）。
     *
     * @param checkpointViewer 只对这个区块采阶段 checkpoint；null = 本次不采
     * @param stages           出参：阶段名 → 离线快照；null = 不需要 checkpoint
     */
    private void applyStage(ChunkStatus status, OfflineChunkHolder holder, ChunkPos checkpointViewer,
                            Map<String, GenStageSnapshot> stages) {
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
        OfflineChunkRegion region = new OfflineChunkRegion(ctx.host(), cache, step, chunk, ctx.seed(),
                ctx.randomState(), ctx.biomeSource());

        if (status == ChunkStatus.STRUCTURE_STARTS) {
            // 对应 ChunkStatusTasks#generateStructureStarts:43-52
            if (ctx.generateStructures()) {
                ctx.generator().createStructures(ctx.registries(), ctx.structureState(), ctx.hostStructureManager(),
                        chunk, ctx.templateManager(), ctx.host().dimension());
                structureStartRuns++;
            }
        } else if (status == ChunkStatus.STRUCTURE_REFERENCES) {
            // 对应 generateStructureReferences:65-68
            ctx.generator().createReferences(region, ctx.hostStructureManager().forWorldGenRegion(region), chunk);
        } else if (status == ChunkStatus.BIOMES) {
            // 对应 generateBiomes:74-76
            ctx.generator().createBiomes(ctx.randomState(), Blender.of(region),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk).join();
            biomeRuns++;
        } else if (status == ChunkStatus.NOISE) {
            // 对应 generateNoise:82-98
            chunk = ctx.generator().fillFromNoise(Blender.of(region), ctx.randomState(),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk).join();
            chunk = applyBelowZeroRetrogen(chunk);
            noiseRuns++;
        } else if (status == ChunkStatus.SURFACE) {
            // 对应 generateSurface:104-107
            ctx.generator().buildSurface(region, ctx.hostStructureManager().forWorldGenRegion(region),
                    ctx.randomState(), chunk);
            surfaceRuns++;
        } else if (status == ChunkStatus.CARVERS) {
            // 对应 generateCarvers:113-122
            if (chunk instanceof ProtoChunk protoChunk) {
                Blender.addAroundOldChunksCarvingMaskFilter(region, protoChunk);
            }
            ctx.generator().applyCarvers(region, ctx.seed(), ctx.randomState(), region.getBiomeManager(),
                    ctx.hostStructureManager().forWorldGenRegion(region), chunk);
            carverRuns++;
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
            throw new IllegalStateException("离线 pipeline 不支持的状态：" + status);
        }

        // 对应 ChunkStep#completeChunkGeneration（ChunkStep.java:30-40）
        if (chunk instanceof ProtoChunk protoChunk && protoChunk.getPersistedStatus().isBefore(status)) {
            protoChunk.setPersistedStatus(status);
        }
        holder.put(status, chunk);
        stageRuns++;
        hostChunkSourceQueries += region.hostChunkSourceQueries();

        String stageName = stageNameOf(status);
        if (stageName != null && checkpointViewer != null && stages != null && checkpointViewer.equals(pos)) {
            stages.put(stageName, StageSnapshotCapture.capture(region, pos, stageName + " 后", palette,
                    ++snapshotOrder));
        }
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

    /** 需要采 checkpoint 的四个阶段对应的标签；其它阶段返回 null。 */
    private static String stageNameOf(ChunkStatus status) {
        if (status == ChunkStatus.BIOMES) {
            return GenStageCapture.STAGE_BIOMES;
        }
        if (status == ChunkStatus.NOISE) {
            return GenStageCapture.STAGE_NOISE;
        }
        if (status == ChunkStatus.SURFACE) {
            return GenStageCapture.STAGE_SURFACE;
        }
        if (status == ChunkStatus.CARVERS) {
            return GenStageCapture.STAGE_CARVERS;
        }
        return null;
    }

    /** 缓存半径（报告用：说明离线构造所需的邻域范围）。 */
    static int cacheRadius() {
        return CACHE_RADIUS;
    }

    /** 各阶段「至少需要周围多少圈、每圈至少什么状态」（报告用，直接来自 ChunkPyramid）。 */
    static List<String> dependencyTable() {
        List<String> lines = new ArrayList<>();
        ChunkStep featuresStep = ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES);
        for (ChunkStatus status : STAGES_BEFORE_FEATURES) {
            lines.add("  " + status.getName() + "：铺到半径 "
                    + featuresStep.getAccumulatedRadiusOf(status));
        }
        lines.add("  FEATURES：铺到半径 0（目标区块自己）");
        lines.add("  依赖明细（FEATURES 步起算，逐圈）："
                + featuresStep.directDependencies().toString());
        return lines;
    }

    /** 运行量摘要（报告用）。 */
    List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("离线世界口径：" + (freshWorldPerViewer
                ? "每个 viewer 新建一份独立世界（从 EMPTY 重长，邻域不带别的 viewer 的装饰痕迹）；已新建 "
                + worldsBuilt + " 份"
                : "跨 viewer 复用同一份世界（共享会话口径：前一个 viewer 装饰写进去的方块，"
                + "后一个 viewer 执行时真实存在）"));
        lines.add("离线缓存半径（照原版 ChunkGenerationTask 算）：" + CACHE_RADIUS
                + " 区块 → 每层最多 " + (CACHE_RADIUS * 2 + 1) + "x" + (CACHE_RADIUS * 2 + 1) + " 区块");
        lines.add("离线阶段执行次数合计（跨 viewer 累加）：" + stageRuns
                + "（结构起点 " + structureStartRuns + " / 生物群系 " + biomeRuns + " / 噪声 " + noiseRuns
                + " / 地表 " + surfaceRuns + " / 雕刻器 " + carverRuns + " / 装饰 " + featureRuns + "）");
        lines.add("共享缓存命中（同一区块同一状态重复请求而跳过的阶段数）：" + stagesSkipped
                + "；当前持有区块 " + holders.size() + " 个");
        lines.add("离线 region 向宿主 ChunkMap 的查询次数累计：" + hostChunkSourceQueries
                + "（这是「没有从真实世界读生成数据」的自证指标：次数不为 0 时必须逐条解释来源）");
        lines.addAll(notes);
        return lines;
    }

    /** 性能节用的一行摘要（第六轮口径第二十项）。 */
    String perfCn() {
        return "阶段执行 " + stageRuns + " 次 / 缓存命中跳过 " + stagesSkipped
                + " 次 / 持有区块 " + holders.size() + " 个 / FEATURES 执行 " + featureRuns + " 次"
                + " / 宿主 ChunkMap 查询 " + hostChunkSourceQueries + " 次";
    }

    /** 累计阶段执行次数（性能节按目标区块取差值用）。 */
    long stageRuns() {
        return stageRuns;
    }

    /** 累计「因已有产出而跳过」的阶段次数（性能节按目标区块取差值用）。 */
    long stagesSkipped() {
        return stagesSkipped;
    }

    /** 累计 FEATURES 执行次数（= 真跑过的 viewer 数）。 */
    long featureRuns() {
        return featureRuns;
    }

    /** 累计「宿主 ChunkMap 被查询」次数（自证没有从真实世界读生成数据）。 */
    int hostChunkSourceQueries() {
        return hostChunkSourceQueries;
    }

    /** 记录一条备注（进报告）。 */
    void note(String text) {
        notes.add(text);
    }
}
