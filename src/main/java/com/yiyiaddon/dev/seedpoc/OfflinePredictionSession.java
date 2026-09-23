package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/**
 * 种子挖矿 PoC 第六轮 · 「一次 Seed + 维度下的共享离线 worldgen」。
 *
 * <p><b>为什么必须有它</b>：目标区块的最终钻石里有一部分是<b>邻区块各自那一次
 * {@code applyBiomeDecoration}</b> 按写半径 1 写进来的。要复现这部分，就必须让目标 ±写半径
 * 的每个 viewer 都在<b>同一份离线世界状态</b>里跑它自己的 {@code FEATURES}：
 * viewer A 写进去的方块，viewer B 执行时真的要存在；否则跨区块写入的先后叠加关系就丢了。</p>
 *
 * <p>因此本会话持有<b>一套</b>（不是 9 套）种子相关上下文，并复用
 * {@link OfflineChunkPipeline} 的共享世界模式（{@code reuseWorldAcrossViewers = true}）：</p>
 * <ul>
 *     <li>共享：{@link OfflineWorldgenContext}（自建 BiomeSource / NoiseBasedChunkGenerator /
 *         RandomState / ChunkGeneratorStructureState）+ {@code OfflineChunkHolder} 表 +
 *         {@code OfflineChunkRegion}；</li>
 *     <li>只推进到真正需要的最高阶段：{@link #prepareFor} 按原版
 *         {@code ChunkPyramid} 的累积半径逐阶段铺前置状态，已产出过的阶段直接跳过；</li>
 *     <li>{@link #decorate} 让一个区块跑它自己的 {@code FEATURES}，幂等；</li>
 *     <li>{@link #finalChunk} 读某个区块跑完 {@code FEATURES} 之后的最终状态（= 离线世界里的它）。</li>
 * </ul>
 *
 * <p><b>输入边界</b>（用户口径第七节）：本会话只吃 种子 + 注册表 + 原版 worldgen 配置；
 * 宿主 {@code ServerLevel} 只当「环境宿主」（维度类型 / 世界高度 / 注册表 / 模板管理器 /
 * 调色板工厂），<b>不读它的任何方块状态</b>，这一点由
 * {@code OfflineChunkPipeline.describe()} 里的「宿主 ChunkMap 查询次数」自证。
 * 真实世界只出现在 {@link FinalOreTruthComparator}（测试侧）。</p>
 *
 * <p><b>不使用任何真实装饰批号 / 调度历史</b>：第五轮已实测最终钻石集合与装饰先后无关，
 * 因此本会话的 viewer 顺序只取「由远到近、目标最后」这一种固定顺序，不引入任何真实时序输入。</p>
 */
final class OfflinePredictionSession {

    /** 离线上下文（种子的四件东西全在这里，全部自建）。 */
    private final OfflineWorldgenContext ctx;

    /** 共享离线世界（跨 viewer 复用，本会话内区块状态持续累积）。 */
    private final OfflineChunkPipeline pipeline;

    /** 被试种子（会写进报告）。 */
    private final long seed;

    /** 会话序号（全局唯一；归属台账按它分开存，互不污染）。 */
    private final long sessionId;

    /** 会话序号发放器。 */
    private static final java.util.concurrent.atomic.AtomicLong SESSION_SEQUENCE =
            new java.util.concurrent.atomic.AtomicLong();

    /** 已经开过窗口的目标区块数（报告用）。 */
    private final List<String> preparedTargets = new ArrayList<>();

    /**
     * 各目标区块自己的四个阶段 checkpoint（离线侧），键 = {@code ChunkPos#pack}。
     *
     * <p>只在本会话用于 FIRST DIVERGENCE 取证：真实侧由 {@link GenStageCapture} 在真实生成期采同名
     * checkpoint，两侧用 {@link StageComparator} 逐格比，就能指出第一处分叉落在
     * {@code BIOMES / NOISE / SURFACE / CARVERS} 的哪一层。
     * 未开启 {@code yiyiaddon.seedpoc.round6.probe} 时一律为空表（不采，不影响正式数字）。</p>
     */
    private final Map<Long, Map<String, GenStageSnapshot>> targetStages = new LinkedHashMap<>();

    private OfflinePredictionSession(OfflineWorldgenContext ctx, long seed) {
        this.ctx = ctx;
        this.pipeline = new OfflineChunkPipeline(ctx, true);
        this.seed = seed;
        this.sessionId = SESSION_SEQUENCE.incrementAndGet();
        OfflineOreAttribution.openSession(sessionId);
    }

    /**
     * 打开一个会话（只凭种子构造离线 worldgen，不读真实世界内容）。
     *
     * @param host 环境宿主（只取与种子无关的环境参数）
     * @param seed 被试种子
     */
    static OfflinePredictionSession open(ServerLevel host, long seed) {
        return new OfflinePredictionSession(OfflineWorldgenContext.create(host, seed), seed);
    }

    /** 会话序号（归属台账的隔离键）。 */
    long sessionId() {
        return sessionId;
    }

    /** 被试种子。 */
    long seed() {
        return seed;
    }

    /** 共享离线世界（性能计数用）。 */
    OfflineChunkPipeline pipeline() {
        return pipeline;
    }

    /**
     * 为目标区块建立观察窗口，并把 {@code target ± extraRadius} 所需的前置阶段全部铺好。
     *
     * @param target      目标区块
     * @param extraRadius 额外圈数（第六轮 = 写半径；0 = 只装饰目标区块自己）
     */
    void prepareFor(ChunkPos target, int extraRadius) {
        if (SeedPocFlags.round6Probe()) {
            // 诊断口径：采这一目标区块自己的四个阶段 checkpoint（离线侧）。
            // 必须用一份「只服务这一个中心区块」的独立流水线，不能拿共享会话的 pipeline 采：
            // 目标区块的邻域很可能已经被上一个目标推进过（共享会话的复用正体现在这里），
            // applyStage 见到「该状态已有产出」就直接跳过，于是第二个目标起一个 checkpoint 都采不到。
            // 阶段 checkpoint 只到 CARVERS（FEATURES 之前），是种子的纯函数，不与共享世界互相影响。
            Map<String, GenStageSnapshot> stages = new LinkedHashMap<>();
            new OfflineChunkPipeline(ctx, true).prepare(target, extraRadius, target, stages);
            targetStages.put(target.pack(), Map.copyOf(stages));
        }
        pipeline.prepare(target, extraRadius, null, null);
        preparedTargets.add("(" + target.x() + "," + target.z() + ") r=" + extraRadius);
    }

    /** 某目标区块的离线阶段 checkpoint（键 = 阶段名）；未采或没采到返回空表。 */
    Map<String, GenStageSnapshot> stagesOf(ChunkPos target) {
        return targetStages.getOrDefault(target.pack(), Map.of());
    }

    /** 让一个区块跑它自己的 {@code FEATURES}（幂等；已跑过直接跳过）。 */
    void decorate(ChunkPos viewer) {
        pipeline.decorate(viewer);
    }

    /** 某区块跑完 {@code FEATURES} 之后的最终状态；没跑到该状态返回 null。 */
    ChunkAccess finalChunk(ChunkPos target) {
        return pipeline.chunkAt(target, ChunkStatus.FEATURES);
    }

    /** 某区块「已经跑到的最远状态」的状态（读增量用：目标可能还停在 CARVERS）。 */
    ChunkAccess latestChunk(ChunkPos pos) {
        return pipeline.latestChunk(pos);
    }

    /** 当前持有区块数（峰值缓存区块数的上界）。 */
    int chunkCount() {
        return pipeline.chunkCount();
    }

    /** 报告用：会话说明（共享了哪些东西、开了几次窗口）。 */
    List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("OfflinePredictionSession：一个 种子=" + seed + " 下的共享离线 worldgen，"
                + "持有 1 套 BiomeSource / NoiseBasedChunkGenerator / RandomState / ChunkGeneratorStructureState，"
                + "1 份 ProtoChunk 表 + OfflineChunkRegion 视图；目标周围 " + TargetViewerSet.writeRadius()
                + " 圈（共 " + (TargetViewerSet.writeRadius() * 2 + 1) * (TargetViewerSet.writeRadius() * 2 + 1)
                + " 个 viewer）全部在这同一份世界状态里推进。");
        lines.add("会话内已开窗口的目标区块：" + (preparedTargets.isEmpty() ? "无" : String.join("、", preparedTargets)));
        lines.add("共享缓存隔离维度：会话本身 = (版本, 种子, 维度)；区块键 = ChunkPos；每个键下按生成状态分槽"
                + "（{@code OfflineChunkHolder} 一个状态一份产出）。跨种子不可能共用同一份缓存。");
        lines.add("viewer 顺序：由远到近、目标区块最后（第五轮已实测最终钻石集合与装饰先后无关，"
                + "因此顺序不引入任何真实调度历史输入）。");
        lines.add("离线构造的邻域要求（照原版 ChunkPyramid 逐层算）：");
        lines.addAll(OfflineChunkPipeline.dependencyTable());
        lines.addAll(pipeline.describe());
        lines.add(OfflineStageCapture.describe());
        lines.add(OfflineOreAttribution.describe(sessionId));
        lines.add("宿主只当环境宿主：离线 region 向宿主 ChunkMap 的查询次数 = "
                + pipeline.hostChunkSourceQueries() + "（>0 必须逐条解释来源）");
        return lines;
    }
}
