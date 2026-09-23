package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * 种子挖矿 PoC · 实验主流程。
 *
 * <p><b>第二轮的核心问题</b>：第一轮把「已完全生成的世界」当装饰期状态用，合计查全 59.26%。
 * 本轮在真实区块生成过程中取阶段快照（{@link GenStageCapture}），把快照安装进影子 worldgen
 * 的输入位置（{@link RegionStateLedger}），<b>算法、种子、索引表一律不动</b>，
 * 再看逐 BlockPos 能不能复现。</p>
 *
 * <p><b>四个口径，同一批固定区块各跑一遍，结果并排进报告</b>：</p>
 * <ol>
 *     <li><b>口径0（回归基线）</b>：输入 = 已完全生成的世界，清空矿石后重放 {@code underground_ores}。
 *         用来确认本装置与第一轮跑出的是同一组数字。</li>
 *     <li><b>口径1（快照输入 · 只重放矿步骤）</b>：输入 = 装饰开始前快照，重放 {@code underground_ores}。</li>
 *     <li><b>口径2（快照输入 · 全部前置 feature 步骤）</b>：输入 = 装饰开始前快照，重放
 *         {@code raw_generation} 到 {@code underground_ores} 的全部 feature 步骤
 *         （原版 {@code applyBiomeDecoration} 本来就是按步骤顺序把它们放上去的，
 *         只重放矿步骤会缺少前置步骤写入的方块）。</li>
 *     <li><b>口径3（第一条钻石 feature 前快照）</b>：输入 = 该区块第一条钻石 placed_feature
 *         执行前的世界状态，只重放钻石四条；用来区分结构 / 前置 feature 的影响。</li>
 * </ol>
 *
 * <p>每个快照口径在每个区块上跑中心 3x3 九遍装饰（写半径 1，见
 * {@code ChunkPyramid.java:29-35}），每遍安装的是<b>那一遍所属区块自己的快照</b>——
 * 因为原版那九遍本来就发生在不同时刻、读到的邻域状态也不同。</p>
 */
public final class SeedPocRunner {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 口径0 的补充口径（第一轮其余三种，默认关闭，用 -Dyiyiaddon.seedpoc.variant.legacyExtra=1 打开）。 */
    private static final String MODE_LEGACY_DIAMOND_ONLY = "口径0-旧｜只重放钻石（其它矿保持原样）";
    private static final String MODE_LEGACY_ALL_STEPS = "口径0-旧｜多步骤重放（underground_structures + underground_ores）";
    private static final String MODE_LEGACY_PERTURB = "口径0-旧｜单点状态扰动（只改一个候选点的方块状态，看预测是否整体漂移）";

    /** 中心 3x3 遍历顺序 (-1,-1)…(1,1) 里，中心那一遍的序号。 */
    private static final int CENTER_PASS_INDEX = 4;

    /** 一个测试区块要跑的装饰遍数（中心 3x3）。 */
    private static final int PASS_COUNT = 9;

    /** 复刻用的装饰步骤序号（取原版枚举序号，不写死数字）。 */
    private static final int STEP_UNDERGROUND_STRUCTURES = GenerationStep.Decoration.UNDERGROUND_STRUCTURES.ordinal();
    private static final int STEP_UNDERGROUND_ORES = GenerationStep.Decoration.UNDERGROUND_ORES.ordinal();

    private SeedPocRunner() {
    }

    /** 矿步骤序号（供第三轮单 viewer 重放复用，避免各处写死数字）。 */
    static int stepUndergroundOres() {
        return STEP_UNDERGROUND_ORES;
    }

    /** 跑一轮完整实验（必须在服务端线程上调用：要读区块真值并原地重放）。 */
    public static void run(IntegratedServer server) {
        long startedAt = System.currentTimeMillis();
        ServerLevel level = server.overworld();
        Long override = SeedPocFlags.seedOverride();
        long seed = override != null ? override : level.getSeed();
        String seedSource = override != null ? "系统属性 yiyiaddon.seedpoc.seed" : "当前单人世界真实种子";

        ShadowWorldGenContext context = ShadowWorldGenContext.create(level.registryAccess(), seed);
        context.verifyAgainstServerGenerator(level.getChunkSource().getGenerator());

        Set<PlacedFeature> diamondFeatures = new HashSet<>();
        for (String id : SeedPocConstants.DIAMOND_PLACED_FEATURES) {
            diamondFeatures.add(context.placedFeature(id));
        }

        int[] stepsOres = {STEP_UNDERGROUND_ORES};
        int[] stepsUpToOres = stepsUpToOres();

        Set<String> calledMethods = new TreeSet<>();
        int[] callCount = {0};
        List<ChunkOutcome> outcomes = new ArrayList<>();
        List<ViewerOutcome> singleViewer = new ArrayList<>();
        long biomeMatched = 0;
        long biomeTotal = 0;
        long biomeChunks = 0;
        boolean terrainOk = true;
        StringBuilder terrainNote = new StringBuilder();
        long terrainCompared = 0;
        long terrainAirMismatch = 0;
        long terrainBlockMismatch = 0;
        long terrainOurNonAir = 0;
        long terrainRealNonAir = 0;

        boolean oldModes = oldModesEnabled();
        boolean singleMode = SeedPocFlags.variant("single");
        // 第六轮：只跑「最终世界真值 + 纯 Seed 离线预测」，二/三/四轮的重口径默认不跑
        // （第四轮的「单 viewer」模型改为在第六轮章节里用独立离线世界现场复算，避免跑两遍）
        boolean round6 = SeedPocFlags.round6();
        boolean legacy = !round6 || SeedPocFlags.round6Legacy();

        if (legacy) {
            for (long[] coordinates : SeedPocFlags.chunks()) {
                ChunkPos center = new ChunkPos((int) coordinates[0], (int) coordinates[1]);
                // 预加载：真值记录、快照安装、重放写入都落在中心 ±2 之内
                preload(level, center, SeedPocConstants.REGION_RADIUS);

                if (oldModes) {
                    outcomes.addAll(runOneChunk(level, context, center, diamondFeatures, stepsOres, stepsUpToOres,
                            calledMethods, callCount));
                }

                BiomeReproProbe.Result biome = BiomeReproProbe.measure(level, context, center, 1);
                biomeMatched += biome.matched();
                biomeTotal += biome.total();
                biomeChunks += biome.chunkCount();

                if (SeedPocFlags.terrainProbe()) {
                    NoiseTerrainProbe.Result terrain = NoiseTerrainProbe.measure(level, context, center);
                    if (!terrain.ok()) {
                        terrainOk = false;
                        if (terrainNote.length() > 0) {
                            terrainNote.append("；");
                        }
                        terrainNote.append(terrain.note());
                    } else {
                        terrainCompared += terrain.comparedBlocks();
                        terrainAirMismatch += terrain.airShapeMismatch();
                        terrainBlockMismatch += terrain.blockMismatch();
                        terrainOurNonAir += terrain.ourNonAir();
                        terrainRealNonAir += terrain.realNonAir();
                    }
                }

                if (singleMode) {
                    // 缺失输入量化只挂到第一个 viewer 上，避免报告里重复十份
                    List<String> evidence = new ArrayList<>(GenStageEvidence.render(level, center,
                            GenStageCapture.postCarvers(center), GenStageCapture.preDiamond(center)));
                    singleViewer.add(SingleViewerVerifier.verify(level, context, center, center, diamondFeatures,
                            calledMethods, callCount, evidence));
                }
            }
        }

        // 第一阶段判定：10 个 viewer 是否全部逐 BlockPos 一致
        boolean singleExact = !singleViewer.isEmpty();
        for (ViewerOutcome outcome : singleViewer) {
            if (!outcome.exact()) {
                singleExact = false;
                break;
            }
        }

        // 第二阶段只在第一阶段 100% 一致时执行（用户口径：不要提前做九遍聚合）
        List<ViewerOutcome> crossChunk = new ArrayList<>();
        boolean crossChunkExecuted = false;
        if (legacy && singleExact && SeedPocFlags.variant("phase2")) {
            crossChunkExecuted = true;
            for (long[] coordinates : SeedPocFlags.chunks()) {
                ChunkPos target = new ChunkPos((int) coordinates[0], (int) coordinates[1]);
                preload(level, target, SeedPocConstants.REGION_RADIUS);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        ChunkPos viewer = new ChunkPos(target.x() + dx, target.z() + dz);
                        crossChunk.add(SingleViewerVerifier.verify(level, context, viewer, target, diamondFeatures,
                                calledMethods, callCount, List.of()));
                    }
                }
            }
        }

        // ── 第四轮：仅凭 Seed 离线构造 pre-diamond，再推钻石坐标 ──────────────────
        // 顺序要求：必须在第三轮的真实快照与 oracle 全部取完之后再跑（离线构造会驱动一次真实的
        // FEATURES，探针已按 region 类型隔离，不会污染真实侧数据）
        List<OfflineOutcome> offlineOutcomes = new ArrayList<>();
        List<String> offlineNotes = new ArrayList<>();
        boolean offlineExecuted = legacy && SeedPocFlags.offline();
        if (offlineExecuted) {
            try {
                OfflineWorldgenContext offlineContext = OfflineWorldgenContext.create(level, seed);
                OfflineChunkPipeline pipeline = new OfflineChunkPipeline(offlineContext,
                        SeedPocFlags.offlineReuseWorld());
                int decorRadius = SeedPocFlags.offlineDecorRadius();
                int limit = SeedPocFlags.offlineLimit();
                offlineNotes.add("离线装饰范围（decorRadius）：" + decorRadius
                        + "（0 = 只装饰目标区块，邻域停在 CARVERS）");
                offlineNotes.add("离线世界口径："
                        + (SeedPocFlags.offlineReuseWorld()
                        ? "跨 viewer 复用同一世界（对照开关 offline.reuse=1）"
                        : "每个 viewer 新建独立世界（默认；每个 viewer 对应「该区块第一次被装饰」）"));
                offlineNotes.add("离线构造的邻域要求（照原版 ChunkPyramid 逐层算）：");
                offlineNotes.addAll(OfflineChunkPipeline.dependencyTable());
                for (long[] coordinates : SeedPocFlags.chunks()) {
                    if (limit > 0 && offlineOutcomes.size() >= limit) {
                        offlineNotes.add("按 yiyiaddon.seedpoc.offline.limit=" + limit + " 提前停止（仅调试用）");
                        break;
                    }
                    ChunkPos viewer = new ChunkPos((int) coordinates[0], (int) coordinates[1]);
                    offlineOutcomes.add(OfflineWorldgenVerifier.verify(level, context, pipeline, viewer,
                            decorRadius, diamondFeatures, calledMethods, callCount,
                            SeedPocFlags.offlineStageTwo()));
                }
                offlineNotes.addAll(pipeline.describe());
                offlineNotes.add(OfflineStageCapture.describe());
            } catch (Throwable error) {
                // 离线构造失败必须如实进报告：绝不允许因为「跑挂了」就悄悄跳过这一节
                offlineNotes.add("离线构造异常（本轮离线结论不成立）：" + error.getClass().getName()
                        + ": " + error.getMessage());
                LOGGER.error("{}：第四轮离线构造异常", SeedPocConstants.LOG_KEY, error);
            }
        } else {
            offlineNotes.add(round6
                    ? "第六轮模式：第四轮口径改为在第六轮章节里用独立离线世界现场复算（「只装饰目标自己」那一行），"
                    + "因此这里不再重复跑一遍"
                    : "按系统属性 yiyiaddon.seedpoc.offline=0 跳过第四轮离线构造");
        }

        // ── 第六轮：Target ± 写半径 viewer 共享离线世界 → 最终 Target Chunk 钻石 ──────────
        PredictionRegressionSuite.Result round6Result = null;
        List<String> round6Notes = new ArrayList<>();
        if (round6) {
            try {
                round6Result = PredictionRegressionSuite.run(level);
            } catch (Throwable error) {
                // 第六轮跑挂必须如实进报告：绝不允许因为「跑挂了」就悄悄跳过这一节
                round6Notes.add("第六轮运行异常（本轮第六轮结论不成立）：" + error.getClass().getName()
                        + ": " + error.getMessage());
                LOGGER.error("{}：第六轮运行异常", SeedPocConstants.LOG_KEY, error);
            }
        } else {
            round6Notes.add("按系统属性 yiyiaddon.seedpoc.round6=0 跳过第六轮");
        }

        BiomeReproProbe.Result biomeSummary = new BiomeReproProbe.Result(biomeMatched, biomeTotal, biomeChunks);
        NoiseTerrainProbe.Result terrainSummary = new NoiseTerrainProbe.Result(
                terrainOk, terrainOk ? "ok" : terrainNote.toString(),
                terrainCompared, terrainAirMismatch, terrainBlockMismatch,
                terrainOurNonAir, terrainRealNonAir);

        SeedPocReport.Summary summary = new SeedPocReport.Summary(
                server.getWorldData().getLevelName(),
                level.dimension().identifier().toString(),
                seed,
                seedSource,
                "自建：MultiNoiseBiomeSource.createFromPreset(minecraft:overworld)",
                "自建：new NoiseBasedChunkGenerator(生物群系源, NoiseGeneratorSettings.overworld)",
                "自建：RandomState.create(注册表, NoiseGeneratorSettings.overworld, 种子)",
                context.featuresPerStep().size(),
                context.verifyNote(),
                context.describeDiamondOreConfigs(),
                calledMethods,
                callCount[0],
                outcomes,
                singleViewer,
                crossChunk,
                crossChunkExecuted,
                singleExact,
                oracleNote(),
                biomeSummary,
                terrainSummary,
                override != null,
                GenStageCapture.describe(),
                System.currentTimeMillis() - startedAt,
                offlineOutcomes,
                offlineExecuted,
                offlineNotes,
                round6,
                legacy,
                round6Result,
                round6Notes);

        SeedPocReport.output(SeedPocReport.render(summary), reportFileName(round6, round6Result, seed));
    }

    /** 报告文件名：第六轮按「阶段 + 种子」分开落盘，便于多进程泛化时逐份留档。 */
    private static String reportFileName(boolean round6, PredictionRegressionSuite.Result result, long seed) {
        if (!round6) {
            return SeedPocConstants.REPORT_FILE_NAME;
        }
        String stage = result == null ? SeedPocFlags.round6Stage() : result.stage();
        return "seedpoc-第六轮报告-" + stage + "-" + seed + ".txt";
    }

    /** 本轮是否要跑第二轮那些大口径（默认关闭，可单独打开做回归）。 */
    private static boolean oldModesEnabled() {
        return SeedPocFlags.variant("old") || SeedPocFlags.variant("legacyExtra")
                || SeedPocFlags.variant("snapOres") || SeedPocFlags.variant("snapAll")
                || SeedPocFlags.variant("snapPreDiamond") || SeedPocFlags.variant("control");
    }

    /** 把中心 ±radius 的区块强制加载出来，保证后面的真值读取与重放都在已生成区块上。 */
    private static void preload(ServerLevel level, ChunkPos center, int radius) {
        for (int cx = center.x() - radius; cx <= center.x() + radius; cx++) {
            for (int cz = center.z() - radius; cz <= center.z() + radius; cz++) {
                level.getChunk(cx, cz);
            }
        }
    }

    /** oracle / 放置台账的全局状态（进报告，用来证明第三轮的取证面）。 */
    private static List<String> oracleNote() {
        List<String> lines = new ArrayList<>(DiamondFeatureOracle.describe());
        lines.add("OreFeature 放置台账：已落账 " + OreVeinTrace.keyCount()
                + " 组（侧 + viewer + feature）；记账异常：" + OreVeinTrace.failuresCn());
        lines.add("四个注入点被调用总次数（任何一项为 0 即说明该探针未生效，对应层的「两侧一致」不成立）："
                + OreVeinTrace.callsCn());
        lines.add("记账窗口：" + OreVeinTrace.windowsCn());
        return lines;
    }

    /** 单个测试区块：先记真值，再跑各口径，各口径各自安装/还原，互不污染。 */
    private static List<ChunkOutcome> runOneChunk(ServerLevel level, ShadowWorldGenContext context, ChunkPos center,
                                                  Set<PlacedFeature> diamondFeatures, int[] stepsOres,
                                                  int[] stepsUpToOres,
                                                  Set<String> calledMethods, int[] callCount) {
        // 真值：中心区块的钻石矿位（所有口径共用同一份真值）
        Set<BlockPos> realDiamonds = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond);

        // 缺失输入量化：快照 vs 最终世界 / 装饰开始前 vs 第一条钻石 feature 前
        List<String> evidence = new ArrayList<>(GenStageEvidence.render(level, center,
                GenStageCapture.postCarvers(center), GenStageCapture.preDiamond(center)));

        List<ChunkOutcome> results = new ArrayList<>();
        if (SeedPocFlags.variant("old")) {
            results.add(runMode(level, context, center, stepsOres, null, OreBlockLedger::isOre,
                    SeedPocConstants.MODE_OLD_WORLD, realDiamonds, calledMethods, callCount, false,
                    takeEvidence(evidence)));
        }
        if (SeedPocFlags.variant("legacyExtra")) {
            results.add(runMode(level, context, center, stepsOres, diamondFeatures, OreBlockLedger::isDiamond,
                    MODE_LEGACY_DIAMOND_ONLY, realDiamonds, calledMethods, callCount, false, takeEvidence(evidence)));
            results.add(runMode(level, context, center,
                    new int[]{STEP_UNDERGROUND_STRUCTURES, STEP_UNDERGROUND_ORES}, null,
                    OreBlockLedger::isOreOrBone, MODE_LEGACY_ALL_STEPS, realDiamonds, calledMethods, callCount,
                    true, takeEvidence(evidence)));
            results.add(runPerturbationMode(level, context, center, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
        }
        if (SeedPocFlags.variant("snapOres")) {
            results.add(runSnapshotMode(level, context, center, stepsOres, null, false,
                    SeedPocConstants.MODE_SNAPSHOT_ORES, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
        }
        if (SeedPocFlags.variant("snapAll")) {
            results.add(runSnapshotMode(level, context, center, stepsUpToOres, null, false,
                    SeedPocConstants.MODE_SNAPSHOT_ALL_STEPS, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
        }
        if (SeedPocFlags.variant("snapPreDiamond")) {
            results.add(runSnapshotMode(level, context, center, stepsOres, diamondFeatures, true,
                    SeedPocConstants.MODE_SNAPSHOT_PRE_DIAMOND, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
        }
        if (SeedPocFlags.variant("control")) {
            // 装置自证：同一套安装/重放链路，只把中心区块整片改写成统一方块。判定口径见 runFloodControl。
            results.add(runFloodControl(level, context, center, stepsOres, Blocks.AMETHYST_BLOCK.defaultBlockState(),
                    SeedPocConstants.MODE_CONTROL_AMETHYST, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
            results.add(runFloodControl(level, context, center, stepsOres, Blocks.STONE.defaultBlockState(),
                    SeedPocConstants.MODE_CONTROL_STONE, realDiamonds, calledMethods, callCount,
                    takeEvidence(evidence)));
        }
        if (!evidence.isEmpty()) {
            // 万一有口径没跑，取证明细也不能丢：挂到最后一个口径上
            if (results.isEmpty()) {
                results.add(new ChunkOutcome("未运行任何口径", center, BlockPosDiff.of(realDiamonds, Set.of()),
                        0, 0, 0, 0, false, true, "所有口径都被系统属性关掉了", evidence));
            } else {
                ChunkOutcome last = results.remove(results.size() - 1);
                List<String> details = new ArrayList<>(last.details());
                details.addAll(evidence);
                results.add(new ChunkOutcome(last.mode(), last.pos(), last.diff(), last.candidateFeatures(),
                        last.replayedFeatures(), last.passes(), last.placedCalls(), last.structuresPresent(),
                        last.restoreVerified(), last.note(), details));
            }
        }
        return results;
    }

    /** 取出取证明细（只挂到本区块的第一个口径上，避免报告里四份重复）。 */
    private static List<String> takeEvidence(List<String> holder) {
        if (holder.isEmpty()) {
            return List.of();
        }
        List<String> copy = List.copyOf(holder);
        holder.clear();
        return copy;
    }

    /** 原版 {@code GenerationStep.Decoration} 里「矿步骤之前 + 矿步骤本身」的全部步骤序号。 */
    private static int[] stepsUpToOres() {
        int[] steps = new int[STEP_UNDERGROUND_ORES + 1];
        for (int index = 0; index <= STEP_UNDERGROUND_ORES; index++) {
            steps[index] = index;
        }
        return steps;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 快照口径（第二轮主体）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 快照口径：把真实生成期快照安装进影子 worldgen 的输入位置，再按各装饰遍重放。
     *
     * @param stepIndices     要重放的装饰步骤序号
     * @param onlyFeatures    只重放这些 feature；null 表示重放这些步骤的全部 feature
     * @param preDiamondStage true 用「第一条钻石 feature 前」快照，false 用「装饰开始前」快照
     * @param extraEvidence   缺失输入量化明细（只挂到本区块第一个口径上）
     */
    private static ChunkOutcome runSnapshotMode(ServerLevel level, ShadowWorldGenContext context, ChunkPos center,
                                                int[] stepIndices, Set<PlacedFeature> onlyFeatures,
                                                boolean preDiamondStage, String mode, Set<BlockPos> realDiamonds,
                                                Set<String> calledMethods, int[] callCount,
                                                List<String> extraEvidence) {
        RegionStateLedger ledger = RegionStateLedger.take(level, center, SeedPocConstants.REGION_RADIUS);
        Set<BlockPos> predicted = new LinkedHashSet<>();
        int[] passAdded = new int[PASS_COUNT];
        int[] passInstallWrites = new int[PASS_COUNT];
        int[] passBefore = new int[PASS_COUNT];
        int[] passAfter = new int[PASS_COUNT];
        int missingSnapshots = 0;
        long installWrites = 0;
        int passIndex = 0;
        int candidateFeatures = 0;
        int replayedFeatures = 0;
        int placedCalls = 0;
        List<String> candidateIds = List.of();

        GenStageCapture.setReplaying(true);
        try {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    ChunkPos passCenter = new ChunkPos(center.x() + dx, center.z() + dz);
                    GenStageSnapshot snapshot = preDiamondStage
                            ? GenStageCapture.preDiamond(passCenter)
                            : GenStageCapture.postCarvers(passCenter);
                    if (snapshot == null) {
                        // 缺少输入必须如实记录：绝不用别的东西顶替，否则结论会失真
                        missingSnapshots++;
                        passIndex++;
                        continue;
                    }
                    int writes = (int) ledger.install(snapshot);
                    ledger.installHeightmaps(snapshot);
                    passInstallWrites[passIndex] = writes;
                    installWrites += writes;

                    Set<BlockPos> before = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond);
                    passBefore[passIndex] = before.size();
                    WorldGenLevel shadow = ShadowLevelFactory.create(level, context.seed(), passCenter,
                            calledMethods, callCount, null);
                    OreDecorationReplay.Outcome outcome = OreDecorationReplay.replaySteps(context, shadow, passCenter,
                            stepIndices, onlyFeatures, null);
                    placedCalls += outcome.placedCalls();
                    Set<BlockPos> after = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond);
                    passAfter[passIndex] = after.size();
                    // 只取「本遍新写出来的」矿位：安装进来的状态里本来就可能已经有矿（前置步骤/更早的遍放的）
                    after.removeAll(before);
                    passAdded[passIndex++] = after.size();
                    predicted.addAll(after);

                    if (dx == 0 && dz == 0) {
                        candidateFeatures = outcome.candidateFeatureCount();
                        replayedFeatures = outcome.placedFeatureCount();
                        candidateIds = outcome.candidateIds();
                    }
                }
            }
        } finally {
            GenStageCapture.setReplaying(false);
            ledger.restore();
        }
        long residual = ledger.verifyRestore();

        boolean structuresPresent = !level.getChunk(center.x(), center.z()).getAllStarts().isEmpty();
        BlockPosDiff diff = BlockPosDiff.of(realDiamonds, predicted);
        List<String> details = new ArrayList<>();
        details.add("快照口径：" + (preDiamondStage ? "输入 = 第一条钻石 feature 前快照" : "输入 = 装饰开始前快照")
                + "；每遍安装的是「那一遍所属区块自己的」快照，共 " + PASS_COUNT + " 遍");
        details.add("快照覆盖：本区块取到 " + (PASS_COUNT - missingSnapshots) + "/" + PASS_COUNT
                + " 遍，缺失 " + missingSnapshots + " 遍（缺失遍不参与预测）");
        details.add("中心遍候选 feature（步骤:全局索引:路径，共 " + candidateIds.size() + " 项）："
                + candidateIdsText(candidateIds));
        details.add("逐遍安装写入格数（3x3 区块，y ∈ [-64,79]）：" + java.util.Arrays.toString(passInstallWrites));
        details.add("逐遍安装后中心区块钻石数：" + java.util.Arrays.toString(passBefore)
                + "（输入若是「装饰开始前」，中心遍这里必须为 0）");
        details.add("逐遍重放后中心区块钻石数：" + java.util.Arrays.toString(passAfter));
        details.add("逐遍新增（按装饰遍序 (-1,-1)…(1,1)，中间那个是中心区块自己那遍）："
                + java.util.Arrays.toString(passAdded));
        details.add("快照安装写入格数合计：" + installWrites);
        details.add(ledger.cn());
        details.add("还原校验：" + (residual == 0 ? "通过（逐格比对无残留）" : "失败，残留 " + residual + " 格"));
        details.add("真值 y 分布（每 16 格一层）：" + yHistogram(realDiamonds));
        details.add("预测 y 分布（每 16 格一层）：" + yHistogram(predicted));
        Set<BlockPos> missed = new LinkedHashSet<>(realDiamonds);
        missed.removeAll(predicted);
        Set<BlockPos> extra = new LinkedHashSet<>(predicted);
        extra.removeAll(realDiamonds);
        details.add("漏报样本（最多 8 个）：" + sample(missed));
        details.add("错报样本（最多 8 个）：" + sample(extra));
        if (!extraEvidence.isEmpty()) {
            details.addAll(extraEvidence);
        }

        String note;
        if (missingSnapshots > 0) {
            note = "有 " + missingSnapshots + " 遍缺少生成期快照，差异优先怀疑此项";
        } else if (structuresPresent) {
            note = "该区块自身存在结构：结构未参与本次复刻，差异优先怀疑此项";
        } else {
            note = "";
        }
        return new ChunkOutcome(mode, center, diff, candidateFeatures, replayedFeatures, PASS_COUNT,
                placedCalls, structuresPresent, residual == 0, note, details);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 对照口径（装置自证）：证明放置链路确实以被安装的世界状态为依据
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 装置自证对照：与快照口径做完全一样的事（安装「装饰开始前」快照 → 建影子层 → 重放矿步骤），
     * <b>唯一多出来的动作</b>是在安装之后把中心区块自世界底部起 9 个 section 整片改写成一种统一方块。
     *
     * <p>两个对照的期望值是可判定的：</p>
     * <ul>
     *     <li>{@code 对照1}（改写成紫水晶块：不可替换、非空气）→ 所有候选点都不满足
     *         {@code OreFeature#canPlaceOre} 的替换标签判定，预测必须为 0；</li>
     *     <li>{@code 对照2}（改写成石头：可替换）→ 所有候选点都可替换，预测必须显著多于基线 45 那一档。</li>
     * </ul>
     *
     * <p>为什么必须做这两个对照：如果「换成真实生成期状态」以后逐 BlockPos 结果<b>一格都不变</b>，
     * 那么有两种完全相反的解释 —— 要么状态真的对结果无影响，要么这套装置压根没把状态送到放置链路里。
     * 光靠快照口径的数字分不出这两者，而这两个对照能直接分开：预测为 0 / 明显变多 ⇒ 装置有效；
     * 仍与基线一模一样 ⇒ 装置无效，本轮所有快照口径结论作废。</p>
     *
     * <p>两个对照都走同一条高度图判据（{@code OreFeature.place:46}：读 {@code OCEAN_FLOOR_WG} 决定
     * 是否进入 {@code doPlace}），且安装的是与快照口径同一份高度图，因此二者之间的差异只能来自方块状态。</p>
     */
    private static ChunkOutcome runFloodControl(ServerLevel level, ShadowWorldGenContext context, ChunkPos center,
                                                int[] stepIndices, BlockState floodState, String mode,
                                                Set<BlockPos> realDiamonds, Set<String> calledMethods,
                                                int[] callCount, List<String> extraEvidence) {
        RegionStateLedger ledger = RegionStateLedger.take(level, center, SeedPocConstants.REGION_RADIUS);
        GenStageCapture.setReplaying(true);
        long installWrites;
        long floodWrites;
        Set<BlockPos> predicted = new LinkedHashSet<>();
        List<String> details = new ArrayList<>(extraEvidence);
        try {
            GenStageSnapshot snapshot = GenStageCapture.postCarvers(center);
            if (snapshot == null) {
                return new ChunkOutcome(mode, center, BlockPosDiff.of(realDiamonds, Set.of()), 0, 0, 0, 0, false,
                        true, "缺少该区块的「装饰开始前」快照，对照口径不成立（如实登记，不做代替）", details);
            }
            installWrites = ledger.install(snapshot);
            ledger.installHeightmaps(snapshot);
            // 关键动作：整片改写成统一方块（只改中心区块，便于与基线的「中心遍」直接对比）
            floodWrites = ledger.flood(center, 0, floodState);
            int beforeCount = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond).size();

            WorldGenLevel shadow = ShadowLevelFactory.create(level, context.seed(), center, calledMethods, callCount,
                    null);
            OreDecorationReplay.Outcome outcome = OreDecorationReplay.replaySteps(context, shadow, center, stepIndices,
                    null, null);
            predicted = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond);

            details.add("对照口径：输入 = 装饰开始前快照 + 中心区块整片改写为 " + OreBlockLedger.shortId(floodState)
                    + "；只跑中心遍，重放 " + stepIndices.length + " 个装饰步骤");
            details.add("安装写入 " + installWrites + " 格 / 整片改写写入 " + floodWrites + " 格；改写后中心区块钻石数 "
                    + beforeCount + "（改写为统一方块后必然为 0）");
            details.add("重放后中心区块钻石数：" + predicted.size() + "；放置调用 " + outcome.placedCalls() + " 次");
            details.add("判定口径：本行预测数若不是 0（对照1）或显著多于基线（对照2），即为装置缺陷");
        } finally {
            GenStageCapture.setReplaying(false);
            ledger.restore();
        }
        long residual = ledger.verifyRestore();
        details.add(ledger.cn());
        details.add("还原校验：" + (residual == 0 ? "通过（逐格比对无残留）" : "失败，残留 " + residual + " 格"));

        String note;
        if (floodState.is(Blocks.STONE)) {
            note = predicted.size() > realDiamonds.size() ? ""
                    : "装置存疑：整片改写为可替换方块后，预测数（" + predicted.size() + "）并不多于真值（"
                            + realDiamonds.size() + "）";
        } else {
            note = predicted.isEmpty() ? ""
                    : "装置缺陷：整片改写为不可替换方块后仍预测出 " + predicted.size() + " 个钻石，放置链路没有以输入状态为依据";
        }
        return new ChunkOutcome(mode, center, BlockPosDiff.of(realDiamonds, predicted), 0, stepIndices.length, 1,
                0, false, residual == 0, note, details);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 旧口径（第一轮原样保留，用于回归对照）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 单点状态扰动口径：基线跑一遍 → 挑一个候选点改掉它的方块状态 → 其余输入不变再跑一遍。
     *
     * <p>两次预测的差，就是「生成期方块状态」这一个输入缺失所造成的影响规模。
     * 默认关闭（第一轮已用它证明状态进入随机数流），需要时用
     * {@code -Dyiyiaddon.seedpoc.variant.legacyExtra=1} 打开。</p>
     */
    private static ChunkOutcome runPerturbationMode(ServerLevel level, ShadowWorldGenContext context,
                                                    ChunkPos center, Set<BlockPos> realDiamonds,
                                                    Set<String> calledMethods, int[] callCount,
                                                    List<String> extraEvidence) {
        int[] steps = {STEP_UNDERGROUND_STRUCTURES, STEP_UNDERGROUND_ORES};
        Map<BlockPos, BlockState> ledger = OreBlockLedger.collect(level, center, SeedPocConstants.REGION_RADIUS,
                OreBlockLedger::isOreOrBone);
        OreBlockLedger.clear(level, ledger);

        SeedPocTrace trace = new SeedPocTrace();
        ReplayStats stats = new ReplayStats();
        Set<BlockPos> baseline = replayAllPasses(level, context, center, steps, null, calledMethods, callCount,
                new int[PASS_COUNT], stats, trace);
        // 挑点必须在「还原后的原始世界」上挑，否则读到的状态是实验中途态
        OreBlockLedger.restore(level, ledger, center, SeedPocConstants.REGION_RADIUS, OreBlockLedger::isOreOrBone);

        CandidatePerturbation.Target target = CandidatePerturbation.pick(level, center, trace, CENTER_PASS_INDEX,
                baseline, realDiamonds);

        Set<BlockPos> perturbed = Set.of();
        SeedPocTrace perturbedTrace = null;
        boolean perturbedRestored = true;
        if (target != null) {
            OreBlockLedger.clear(level, ledger);
            BlockState probe = CandidatePerturbation.probe();
            OreBlockLedger.write(level, target.pos(), probe);
            perturbedTrace = new SeedPocTrace();
            perturbed = replayAllPasses(level, context, center, steps, null, calledMethods, callCount,
                    new int[PASS_COUNT], new ReplayStats(), perturbedTrace);
            OreBlockLedger.restore(level, ledger, center, SeedPocConstants.REGION_RADIUS, OreBlockLedger::isOreOrBone);
            OreBlockLedger.write(level, target.pos(), target.originalState());
            perturbedRestored = level.getBlockState(target.pos()).equals(target.originalState());
        }

        List<String> details = new ArrayList<>(extraEvidence);
        details.addAll(CandidatePerturbation.render(target, baseline, perturbed, realDiamonds, CENTER_PASS_INDEX,
                trace, perturbedTrace));
        details.add("扰动点还原校验：" + (target == null ? "无需还原" : perturbedRestored ? "通过" : "失败"));

        boolean restoreVerified = OreBlockLedger.collect(level, center, SeedPocConstants.REGION_RADIUS,
                OreBlockLedger::isOreOrBone).keySet().equals(ledger.keySet());

        return new ChunkOutcome(MODE_LEGACY_PERTURB, center, BlockPosDiff.of(realDiamonds, perturbed),
                stats.candidateFeatures, stats.replayedFeatures, PASS_COUNT, stats.placedCalls,
                false, restoreVerified, "本行数字是「扰动后」口径，扰动前基线见明细", details);
    }

    /**
     * 旧口径：跑一个「清空—重放—还原」的重放模式。
     *
     * @param stepIndices  该口径要重放的装饰步骤（按序号升序）
     * @param onlyFeatures 只重放这些 feature；null 表示重放这些步骤的全部 feature
     * @param cleanFilter  该口径下「哪些方块算待清理的目标矿」
     * @param captureTrace 是否做候选面取证（只影响诊断输出，不影响预测集合）
     * @param extraEvidence 缺失输入量化明细（只挂到本区块第一个口径上）
     */
    private static ChunkOutcome runMode(ServerLevel level, ShadowWorldGenContext context, ChunkPos center,
                                        int[] stepIndices, Set<PlacedFeature> onlyFeatures,
                                        Predicate<BlockState> cleanFilter,
                                        String mode, Set<BlockPos> realDiamonds,
                                        Set<String> calledMethods, int[] callCount, boolean captureTrace,
                                        List<String> extraEvidence) {
        Map<BlockPos, BlockState> ledger = OreBlockLedger.collect(level, center, SeedPocConstants.REGION_RADIUS,
                cleanFilter);
        OreBlockLedger.clear(level, ledger);

        SeedPocTrace trace = captureTrace ? new SeedPocTrace() : null;
        int[] passAdded = new int[PASS_COUNT];
        ReplayStats stats = new ReplayStats();
        Set<BlockPos> predicted = replayAllPasses(level, context, center, stepIndices, onlyFeatures, calledMethods,
                callCount, passAdded, stats, trace);

        // 自一致性：先把世界还原回原始状态、再清一次（与第一遍起点完全一致），然后重跑一遍
        OreBlockLedger.restore(level, ledger, center, SeedPocConstants.REGION_RADIUS, cleanFilter);
        OreBlockLedger.clear(level, ledger);
        Set<BlockPos> predictedSecond = replayAllPasses(level, context, center, stepIndices, onlyFeatures,
                calledMethods, callCount, new int[PASS_COUNT], new ReplayStats(), null);
        boolean deterministic = predictedSecond.equals(predicted);

        // 还原并复查
        OreBlockLedger.restore(level, ledger, center, SeedPocConstants.REGION_RADIUS, cleanFilter);
        Set<BlockPos> afterRestore = OreBlockLedger.collect(level, center, SeedPocConstants.REGION_RADIUS, cleanFilter)
                .keySet();
        boolean restoreVerified = afterRestore.equals(ledger.keySet());

        boolean structuresPresent = !level.getChunk(center.x(), center.z()).getAllStarts().isEmpty();
        String structureInfo = describeStructures(level, center);

        BlockPosDiff diff = BlockPosDiff.of(realDiamonds, predicted);
        List<String> details = new ArrayList<>(extraEvidence);
        details.addAll(describeDetails(realDiamonds, predicted, passAdded, structureInfo, deterministic,
                ledger.size(), stats.candidateIds));
        if (trace != null) {
            details.addAll(CandidateDiff.render(level, center, realDiamonds, predicted, trace));
        }
        String note = structuresPresent ? "该区块自身存在结构：结构未参与本次复刻，差异优先怀疑此项" : "";

        return new ChunkOutcome(mode, center, diff, stats.candidateFeatures, stats.replayedFeatures, PASS_COUNT,
                stats.placedCalls, structuresPresent, restoreVerified, note, details);
    }

    /** 复刻用的一次性统计容器。 */
    private static final class ReplayStats {
        private int candidateFeatures;
        private int replayedFeatures;
        private int placedCalls;
        /** 中心遍的候选 feature 清单（步骤:全局索引:路径），用于证明「feature 集合与全局索引」。 */
        private List<String> candidateIds = List.of();
    }

    /**
     * 对中心 3x3 各跑一遍装饰过程等价复刻，返回中心区块被写出的钻石矿位。
     *
     * @param passAdded 出参：每一遍往中心区块新增的矿位数（顺序 (-1,-1)…(1,1)）
     * @param trace     候选面取证；null 表示不取证
     */
    private static Set<BlockPos> replayAllPasses(ServerLevel level, ShadowWorldGenContext context,
                                                 ChunkPos center, int[] stepIndices,
                                                 Set<PlacedFeature> onlyFeatures,
                                                 Set<String> calledMethods, int[] callCount,
                                                 int[] passAdded, ReplayStats stats, SeedPocTrace trace) {
        // 回放期间必须关闸：否则我们自己的重放会被探针当成「真实装饰」记进 oracle / 放置台账
        OreVeinTrace.beginReplaySession();
        GenStageCapture.setReplaying(true);
        try {
            return replayAllPassesInternal(level, context, center, stepIndices, onlyFeatures, calledMethods,
                    callCount, passAdded, stats, trace);
        } finally {
            GenStageCapture.setReplaying(false);
        }
    }

    private static Set<BlockPos> replayAllPassesInternal(ServerLevel level, ShadowWorldGenContext context,
                                                         ChunkPos center, int[] stepIndices,
                                                         Set<PlacedFeature> onlyFeatures,
                                                         Set<String> calledMethods, int[] callCount,
                                                         int[] passAdded, ReplayStats stats, SeedPocTrace trace) {
        Set<BlockPos> predicted = new LinkedHashSet<>();
        int passIndex = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos passCenter = new ChunkPos(center.x() + dx, center.z() + dz);
                if (trace != null) {
                    trace.setPassIndex(passIndex);
                }
                WorldGenLevel shadow = ShadowLevelFactory.create(level, context.seed(), passCenter,
                        calledMethods, callCount, trace);
                OreDecorationReplay.Outcome outcome =
                        OreDecorationReplay.replaySteps(context, shadow, passCenter, stepIndices, onlyFeatures, trace);
                stats.placedCalls += outcome.placedCalls();
                if (dx == 0 && dz == 0) {
                    stats.candidateFeatures = outcome.candidateFeatureCount();
                    stats.replayedFeatures = outcome.placedFeatureCount();
                    stats.candidateIds = outcome.candidateIds();
                }
                Set<BlockPos> now = OreBlockLedger.collectInChunk(level, center, OreBlockLedger::isDiamond);
                Set<BlockPos> added = new LinkedHashSet<>(now);
                added.removeAll(predicted);
                passAdded[passIndex++] = added.size();
                predicted = now;
            }
        }
        return predicted;
    }

    /** 逐遍新增数量 + 差异点分布 + 样本坐标 + 周边结构 + 自一致性，全部写进报告明细。 */
    private static List<String> describeDetails(Set<BlockPos> real, Set<BlockPos> predicted, int[] passAdded,
                                                String structureInfo, boolean deterministic, int ledgerSize,
                                                List<String> candidateIds) {
        List<String> details = new ArrayList<>();
        details.add("清理台账规模（本次口径下被清空的矿位数）：" + ledgerSize);
        details.add("中心遍候选 feature（步骤:全局索引:路径，共 " + candidateIds.size() + " 项）："
                + candidateIdsText(candidateIds));
        details.add("逐遍新增（按装饰遍序 (-1,-1)…(1,1)，中间那个是中心区块自己那遍）："
                + java.util.Arrays.toString(passAdded));
        details.add("引擎自一致性（同输入跑两遍结果是否相同）：" + (deterministic ? "一致" : "不一致"));
        details.add("真值 y 分布（每 16 格一层）：" + yHistogram(real));
        details.add("预测 y 分布（每 16 格一层）：" + yHistogram(predicted));

        Set<BlockPos> missed = new LinkedHashSet<>(real);
        missed.removeAll(predicted);
        Set<BlockPos> extra = new LinkedHashSet<>(predicted);
        extra.removeAll(real);
        details.add("漏报到区块边界的最小格距：≤2 格 " + countBorder(missed, 2, true)
                + " 个 / >2 格 " + countBorder(missed, 2, false) + " 个");
        details.add("错报到区块边界的最小格距：≤2 格 " + countBorder(extra, 2, true)
                + " 个 / >2 格 " + countBorder(extra, 2, false) + " 个");
        details.add("漏报样本（最多 8 个）：" + sample(missed));
        details.add("错报样本（最多 8 个）：" + sample(extra));
        details.add("周边 5x5 结构：" + structureInfo);
        return details;
    }

    /** 统计集合中「到区块边界的格距」是否 ≤ 阈值。 */
    private static int countBorder(Set<BlockPos> positions, int threshold, boolean within) {
        int count = 0;
        for (BlockPos pos : positions) {
            int dx = pos.getX() & 15;
            int dz = pos.getZ() & 15;
            int distance = Math.min(Math.min(dx, 15 - dx), Math.min(dz, 15 - dz));
            if ((distance <= threshold) == within) {
                count++;
            }
        }
        return count;
    }

    /** 按 16 格一层统计 y 分布。 */
    private static String yHistogram(Iterable<BlockPos> positions) {
        Map<Integer, Integer> bands = new TreeMap<>();
        for (BlockPos pos : positions) {
            int band = Math.floorDiv(pos.getY() + 64, 16) * 16 - 64;
            bands.merge(band, 1, Integer::sum);
        }
        if (bands.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : bands.entrySet()) {
            if (builder.length() > 0) {
                builder.append("、");
            }
            builder.append('[').append(entry.getKey()).append(',').append(entry.getKey() + 15)
                    .append("]×").append(entry.getValue());
        }
        return builder.toString();
    }

    /** 候选 feature 清单文本（过长时截断，避免明细行失控）。 */
    private static String candidateIdsText(List<String> candidateIds) {
        if (candidateIds.isEmpty()) {
            return "无";
        }
        int limit = Math.min(candidateIds.size(), 40);
        String text = String.join("、", candidateIds.subList(0, limit));
        return candidateIds.size() <= limit ? text : text + " …";
    }

    /** 取最多 8 个坐标样本。 */
    private static String sample(Set<BlockPos> positions) {
        if (positions.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= 8) {
                builder.append(" …");
                break;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',')
                    .append(pos.getZ()).append(')');
        }
        return builder.toString();
    }

    /** 周边 5x5 区块里的结构种类与数量（结构未参与复刻，这是差异的候选来源之一）。 */
    private static String describeStructures(ServerLevel level, ChunkPos center) {
        Map<String, Integer> counts = new TreeMap<>();
        var structureRegistry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        for (int cx = center.x() - SeedPocConstants.REGION_RADIUS;
             cx <= center.x() + SeedPocConstants.REGION_RADIUS; cx++) {
            for (int cz = center.z() - SeedPocConstants.REGION_RADIUS;
                 cz <= center.z() + SeedPocConstants.REGION_RADIUS; cz++) {
                for (Structure structure : level.getChunk(cx, cz).getAllStarts().keySet()) {
                    String id = structureRegistry.getResourceKey(structure)
                            .map(Object::toString).orElse("未知");
                    counts.merge(id, 1, Integer::sum);
                }
            }
        }
        if (counts.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (builder.length() > 0) {
                builder.append("、");
            }
            builder.append(entry.getKey()).append('×').append(entry.getValue());
        }
        return builder.toString();
    }
}
