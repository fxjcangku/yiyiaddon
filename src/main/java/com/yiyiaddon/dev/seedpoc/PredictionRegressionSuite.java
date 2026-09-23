package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * 种子挖矿 PoC 第六轮 · 回归套件（固定集 / 12345 回归 / 多 Seed 泛化 / 缓存隔离 / 性能）。
 *
 * <p><b>为什么一轮进程只跑一个阶段</b>：真值必须来自「与 Seed 匹配的真实世界」，而一个进程只建
 * 一个固定种子的世界（{@link SeedPocWorldFactory}）。所以用户口径的「三阶段递进」在实现上是
 * <b>三个（泛化阶段是多个）独立进程</b>，每次用 {@code -Dyiyiaddon.seedpoc.seed=<种子>}
 * 与 {@code -Dyiyiaddon.seedpoc.round6.stage=<阶段>} 指定；阶段之间的「只有前一阶段达标才继续」
 * 由驱动方（人）按报告数字裁决，套件本身不加任何自动升级逻辑。</p>
 *
 * <ul>
 *     <li><b>fixed</b>：Seed 20260922 的 10 个固定目标区块 + 「只装饰目标自己」的旧口径复算
 *         （用来现场重建第四轮的 11 格漏报，而不是引用上一轮数字）+ 缓存隔离实验；</li>
 *     <li><b>regression</b>：Seed 12345 的 4 个旧回归区块（原样保留，不换好看的目标）；</li>
 *     <li><b>generalize</b>：当前进程种子的 6 个多地形目标区块（覆盖率由报告逐个标注真实生物群系）。</li>
 * </ul>
 *
 * <p>所有预测都走 {@link TargetChunkPredictor}（纯 Seed），真值都走 {@link FinalOreTruthComparator}
 * （测试侧）。本类只做编排与统计，不含任何预测算法。</p>
 */
final class PredictionRegressionSuite {

    /** 一次运行的产出（进报告）。 */
    record Result(boolean executed,
                  String stage,
                  long seed,
                  List<String> setupLines,
                  List<PredictionOutcome> targets,
                  List<PredictionOutcome> baselines,
                  boolean baselineRan,
                  List<String> cacheIsolationLines,
                  List<String> perfLines,
                  List<String> failureLines) {

        /** 目标集是否全部逐 BlockPos 完全一致。 */
        boolean allExact() {
            if (targets.isEmpty()) {
                return false;
            }
            for (PredictionOutcome outcome : targets) {
                if (!outcome.exact()) {
                    return false;
                }
            }
            return true;
        }
    }

    private PredictionRegressionSuite() {
    }

    /** 跑当前进程指定的阶段（阶段名来自系统属性，见 {@link SeedPocFlags#round6Stage()}）。 */
    static Result run(ServerLevel level) {
        String stage = SeedPocFlags.round6Stage();
        long seed = level.getSeed();
        if ("regression".equals(stage)) {
            return runRegression(level, seed);
        }
        if ("generalize".equals(stage)) {
            return runGeneralize(level, seed);
        }
        return runFixed(level, seed);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第一阶段：Seed 20260922 固定集 + 旧口径复算 + 缓存隔离
    // ────────────────────────────────────────────────────────────────────────

    private static Result runFixed(ServerLevel level, long seed) {
        List<String> setup = new ArrayList<>();
        setup.add("阶段：fixed（Seed " + seed + " 的 10 个固定目标区块）");
        setup.add("写半径推导：" + TargetViewerSet.evidence());

        List<ChunkPos> targets = chunksOf(SeedPocFlags.round6FixedTargets());
        OfflinePredictionSession session = OfflinePredictionSession.open(level, seed);
        // 旧口径复算必须用「另一份独立离线世界」：共享会话里邻域已经被装饰过，
        // 而旧口径要的正是「目标区块自己是生成波前中心」这一情形
        boolean baselineRan = SeedPocFlags.round6Baseline();
        OfflinePredictionSession baselineSession = baselineRan
                ? OfflinePredictionSession.open(level, seed) : null;

        List<PredictionOutcome> outcomes = new ArrayList<>();
        List<PredictionOutcome> baselines = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        for (ChunkPos target : targets) {
            preload(level, target);
            PredictionOutcome outcome = predictAndCompare(level, session, target);
            outcomes.add(outcome);
            addFailure(failures, "第六轮模型", outcome, level, session);
            if (baselineSession != null) {
                PredictionOutcome baselinePredicted = TargetChunkPredictor.predictSingleViewer(baselineSession, target);
                PredictionOutcome baseline = FinalOreTruthComparator.attach(level, baselinePredicted,
                        baselineSession.finalChunk(target));
                baselines.add(baseline);
                addFailure(failures, "第四轮口径复算", baseline, level, baselineSession);
            }
        }
        setup.addAll(session.describe());

        List<String> cacheLines = SeedPocFlags.round6CacheIsolation()
                ? cacheIsolation(level, seed, targets.isEmpty() ? new ChunkPos(0, 0) : targets.get(targets.size() - 1))
                : List.of("按系统属性 yiyiaddon.seedpoc.round6.cache=0 跳过缓存隔离实验");

        List<String> perf = new ArrayList<>();
        BlockPosDiff total = BlockPosDiff.sum(outcomes.stream().map(PredictionOutcome::diff).toList());
        perf.add("【第六轮模型】目标 " + outcomes.size() + " 个 合计：" + total.cn()
                + "；逐 BlockPos 完全一致 " + countExact(outcomes) + "/" + outcomes.size());
        perf.add("【第六轮模型】共享会话累计：" + session.pipeline().perfCn()
                + "；单目标平均耗时 " + averageMillis(outcomes) + " ms");
        for (PredictionOutcome outcome : outcomes) {
            perf.add("  · 目标 (" + outcome.target().x() + "," + outcome.target().z() + ")：耗时 "
                    + outcome.elapsedMillis() + " ms / 新建区块 " + outcome.protoChunks()
                    + " / 阶段执行 " + outcome.stageRuns() + "（缓存命中跳过 " + outcome.stageSkipped()
                    + "） / FEATURES " + outcome.decoratePasses() + " 遍" + probeCn(outcome));
        }
        if (baselineRan && !baselines.isEmpty()) {
            BlockPosDiff baselineTotal = BlockPosDiff.sum(baselines.stream().map(PredictionOutcome::diff).toList());
            perf.add("【旧口径复算】目标 " + baselines.size() + " 个 合计：" + baselineTotal.cn()
                    + "；逐 BlockPos 完全一致 " + countExact(baselines) + "/" + baselines.size());
            perf.add("【旧口径复算】每个目标都是「一份独立离线世界 + 1 遍 FEATURES」，"
                    + "与第六轮模型的「一份共享离线世界 + " + (TargetViewerSet.writeRadius() * 2 + 1)
                    + "² 遍 FEATURES」对比见上行两行数字；平均耗时 " + averageMillis(baselines) + " ms");
            perf.add("【对比结论】重复生成避免：第六轮模型在同一份世界里跨目标复用区块，"
                    + "旧口径每个目标都从 EMPTY 重长一遍邻域（缓存命中跳过次数见上一行 stageSkipped）");
        }
        perf.add("【真值可重复性台账】（同阶段同种子的上一次运行摘要比对；真值摘要不可重复的目标不计入一致率验收）");
        perf.addAll(truthRepeatability("fixed", seed, outcomes));
        perf.add(memoryCn());

        return new Result(true, "fixed", seed, setup, outcomes, baselines, baselineRan, cacheLines, perf, failures);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第二阶段：Seed 12345 的四个旧回归区块
    // ────────────────────────────────────────────────────────────────────────

    private static Result runRegression(ServerLevel level, long seed) {
        List<String> setup = new ArrayList<>();
        setup.add("阶段：regression（Seed " + seed + " 的 4 个旧回归区块，原样保留不替换）");
        setup.add("写半径推导：" + TargetViewerSet.evidence());
        List<ChunkPos> targets = chunksOf(SeedPocFlags.round6RegressionTargets());
        OfflinePredictionSession session = OfflinePredictionSession.open(level, seed);

        List<PredictionOutcome> outcomes = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        for (ChunkPos target : targets) {
            preload(level, target);
            PredictionOutcome outcome = predictAndCompare(level, session, target);
            outcomes.add(outcome);
            addFailure(failures, "第六轮模型", outcome, level, session);
        }
        setup.addAll(session.describe());

        List<String> perf = new ArrayList<>();
        BlockPosDiff total = BlockPosDiff.sum(outcomes.stream().map(PredictionOutcome::diff).toList());
        perf.add("合计：" + total.cn() + "；逐 BlockPos 完全一致 " + countExact(outcomes) + "/" + outcomes.size());
        perf.add("共享会话累计：" + session.pipeline().perfCn() + "；单目标平均耗时 " + averageMillis(outcomes) + " ms");
        for (PredictionOutcome outcome : outcomes) {
            perf.add("  · 目标 (" + outcome.target().x() + "," + outcome.target().z() + ")：耗时 "
                    + outcome.elapsedMillis() + " ms / 新建区块 " + outcome.protoChunks()
                    + " / 阶段执行 " + outcome.stageRuns() + "（缓存命中跳过 " + outcome.stageSkipped()
                    + "） / FEATURES " + outcome.decoratePasses() + " 遍" + outcome.digest() + probeCn(outcome));
        }
        perf.add("【真值可重复性台账】（同阶段同种子的上一次运行摘要比对；真值摘要不可重复的目标不计入一致率验收）");
        perf.addAll(truthRepeatability("regression", seed, outcomes));
        perf.add(memoryCn());
        return new Result(true, "regression", seed, setup, outcomes, List.of(), false,
                List.of("本阶段不跑缓存隔离实验（第一阶段已跑）"), perf, failures);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第三阶段：多 Seed 泛化（一个进程 = 一个种子）
    // ────────────────────────────────────────────────────────────────────────

    private static Result runGeneralize(ServerLevel level, long seed) {
        List<String> setup = new ArrayList<>();
        setup.add("阶段：generalize（当前进程种子 " + seed + " 的 " + SeedPocFlags.round6GeneralizeTargets().size()
                + " 个多地形目标区块；一个进程只覆盖一个种子，多 Seed 由多次进程运行覆盖）");
        setup.add("写半径推导：" + TargetViewerSet.evidence());
        List<ChunkPos> targets = chunksOf(SeedPocFlags.round6GeneralizeTargets());
        OfflinePredictionSession session = OfflinePredictionSession.open(level, seed);

        List<PredictionOutcome> outcomes = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        List<String> perf = new ArrayList<>();
        List<String> biomes = new ArrayList<>();
        for (ChunkPos target : targets) {
            preload(level, target);
            PredictionOutcome outcome = predictAndCompare(level, session, target);
            outcomes.add(outcome);
            biomes.add("  目标 (" + target.x() + "," + target.z() + ") 真实生物群系（y=64 取样）："
                    + FinalOreTruthComparator.surfaceBiome(level, target));
            addFailure(failures, "第六轮模型", outcome, level, session);
        }
        setup.addAll(session.describe());
        BlockPosDiff total = BlockPosDiff.sum(outcomes.stream().map(PredictionOutcome::diff).toList());
        perf.add("本种子合计：" + total.cn() + "；逐 BlockPos 完全一致 " + countExact(outcomes) + "/"
                + outcomes.size());
        perf.add("共享会话累计：" + session.pipeline().perfCn() + "；单目标平均耗时 " + averageMillis(outcomes) + " ms");
        perf.add("目标地形覆盖（如实登记实际取到的生物群系，不声称覆盖了哪些类别）：");
        perf.addAll(biomes);
        perf.add("【真值可重复性台账】（同阶段同种子的上一次运行摘要比对；真值摘要不可重复的目标不计入一致率验收）");
        perf.addAll(truthRepeatability("generalize", seed, outcomes));
        perf.add(memoryCn());
        return new Result(true, "generalize", seed, setup, outcomes, List.of(), false,
                List.of("本阶段不跑缓存隔离实验（第一阶段已跑）"), perf, failures);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 缓存隔离实验（用户口径第二十一节）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 缓存隔离：种子 A → 目标 X、种子 B → 目标 X、再回到种子 A → 目标 X。
     *
     * <p>第一次 A 与第二次 A 必须逐 BlockPos 完全一致；B 不能污染 A。本轮的实现方式是
     * 「一个会话 = 一个种子的一份独立离线世界」，因此隔离是结构性的；本实验给出实测证据。</p>
     */
    private static List<String> cacheIsolation(ServerLevel level, long seed, ChunkPos target) {
        List<String> lines = new ArrayList<>();
        long otherSeed = seed == 12345L ? 20260922L : 12345L;
        preload(level, target);
        OfflinePredictionSession sessionA1 = OfflinePredictionSession.open(level, seed);
        Set<BlockPos> firstA = TargetChunkPredictor.predictAroundTarget(sessionA1, target).predicted();
        OfflinePredictionSession sessionB = OfflinePredictionSession.open(level, otherSeed);
        Set<BlockPos> setB = TargetChunkPredictor.predictAroundTarget(sessionB, target).predicted();
        OfflinePredictionSession sessionA2 = OfflinePredictionSession.open(level, seed);
        Set<BlockPos> secondA = TargetChunkPredictor.predictAroundTarget(sessionA2, target).predicted();
        BlockPosDiff aa = BlockPosDiff.of(firstA, secondA);
        BlockPosDiff ab = BlockPosDiff.of(firstA, setB);
        lines.add("实验目标区块：(" + target.x() + "," + target.z() + ")；种子 A=" + seed + " / B=" + otherSeed);
        lines.add("第一次 A：" + firstA.size() + " 个；B：" + setB.size() + " 个；第二次 A：" + secondA.size() + " 个");
        lines.add("第一次 A vs 第二次 A：" + aa.cn() + " → "
                + (aa.exact() ? "完全一致（同种子可重复）" : "**不一致，缓存不隔离**"));
        lines.add("A vs B：" + ab.cn() + "（两个种子本来就该不同；这里只用来证明 B 确实换了种子，"
                + "不构成任何一致性要求）");
        lines.add("隔离维度兑现方式：会话对象 = (版本, 种子, 维度)；区块键 = ChunkPos；"
                + "同一键下按生成状态分槽（EMPTY…FEATURES 各一份产出）。跨种子不共用任何缓存对象。");
        lines.add(OfflineOreAttribution.describe(sessionA2.sessionId()));
        return lines;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 真值可重复性（跨运行）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 真值可重复性台账：把本次每个目标的「预测集摘要 / 真值集摘要」落到运行目录，
     * 下一次同阶段同种子运行时读回来对比，并在报告里给出结论。
     *
     * <p><b>为什么必须有它</b>：第六轮验收要求「逐 BlockPos 完全一致」，而一致率的分母是
     * <b>真实世界的最终状态</b>。真值如果本身在两次完全相同的运行里都不一样，那么
     * 「不一致」就不能算到预测器头上。真实世界这一点是会发生的：服务端区块调度是并发的
     * （{@code ChunkStatusTasks} 各阶段返回 {@code CompletableFuture}，由 {@code Util#backgroundExecutor}
     * 并行推进），而 {@code FEATURES} 的写半径是 1 —— 邻居在目标之后装饰时会把方块写进目标、
     * 也会覆盖目标自己的落点，因此「谁先装饰」会改变最终状态；出生点附近的区块还会在实验驱动器
     * 启动之前就被服务端自己的出生点/视野波次装饰掉。本台账把这件事变成报告里的一个可核对数字：
     * 同一目标两次运行的预测摘要必须一致（预测可重复），真值摘要若不一致即标为
     * 「真值不可重复」，该目标不计入一致率验收。</p>
     */
    private static List<String> truthRepeatability(String stage, long seed, List<PredictionOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        Path file = FabricLoader.getInstance().getGameDir()
                .resolve("seedpoc-truth-digest-" + stage + "-" + seed + ".txt");
        Map<String, String[]> previous = new LinkedHashMap<>();
        if (Files.exists(file)) {
            try {
                for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                    String[] parts = line.split(",");
                    if (parts.length == 7) {
                        previous.put(parts[0] + "," + parts[1], parts);
                    }
                }
            } catch (IOException error) {
                lines.add("真值可重复性：上一次台账读取失败（" + error.getClass().getSimpleName() + "）");
            }
        } else {
            lines.add("真值可重复性：本阶段+种子首次运行（已记录本次摘要，下一次同参数运行会自动比对）");
        }

        List<String> current = new ArrayList<>();
        int compared = 0;
        int repeatable = 0;
        int repeatableExact = 0;
        int unstableTruth = 0;
        int unstablePrediction = 0;
        for (PredictionOutcome outcome : outcomes) {
            if (!outcome.compared()) {
                continue;
            }
            compared++;
            ChunkPos target = outcome.target();
            Set<BlockPos> truth = outcome.truth().all();
            String predictedHash = Integer.toHexString(outcome.predicted().hashCode());
            String truthHash = Integer.toHexString(truth.hashCode());
            current.add(target.x() + "," + target.z() + "," + predictedHash + "," + truthHash + ","
                    + outcome.predicted().size() + "," + truth.size() + "," + (outcome.exact() ? "1" : "0"));
            String[] before = previous.get(target.x() + "," + target.z());
            if (before == null) {
                lines.add("真值可重复性：目标 (" + target.x() + "," + target.z() + ") 本次首次记录："
                        + "预测 h=" + predictedHash + " n=" + outcome.predicted().size()
                        + " / 真值 h=" + truthHash + " n=" + truth.size()
                        + " / " + (outcome.exact() ? "一致" : "不一致"));
                continue;
            }
            boolean predictionSame = predictedHash.equals(before[2]) && String.valueOf(outcome.predicted().size()).equals(before[4]);
            boolean truthSame = truthHash.equals(before[3]) && String.valueOf(truth.size()).equals(before[5]);
            if (predictionSame) {
                repeatable++;
            } else {
                unstablePrediction++;
            }
            if (truthSame) {
                if (outcome.exact()) {
                    repeatableExact++;
                }
            } else {
                unstableTruth++;
            }
            lines.add("真值可重复性：目标 (" + target.x() + "," + target.z() + ")"
                    + (predictionSame ? " 预测与上一次相同 h=" + predictedHash + " n=" + outcome.predicted().size()
                    : " **预测与上一次不同**（本次 h=" + predictedHash + " n=" + outcome.predicted().size()
                    + " / 上一次 h=" + before[2] + " n=" + before[4] + "）")
                    + (truthSame ? "；真值与上一次相同 h=" + truthHash + " n=" + truth.size()
                    : "；**真值与上一次不同**（本次 h=" + truthHash + " n=" + truth.size()
                    + " / 上一次 h=" + before[3] + " n=" + before[5]
                    + " ⇒ 真实世界该区块的最终状态在两次相同运行里不同，本目标不计入一致率验收）"));
        }
        try {
            Files.write(file, current, StandardCharsets.UTF_8);
        } catch (IOException error) {
            lines.add("真值可重复性：本次台账落盘失败（" + error.getClass().getSimpleName() + "）");
        }
        int firstTime = compared - repeatable - unstablePrediction;
        lines.add("真值可重复性汇总（与上一次同阶段+同种子运行比对）：可比较目标 " + compared
                + " 个；其中「首次记录」" + firstTime + " 个、预测摘要可重复 " + repeatable
                + " 个（其中逐 BlockPos 一致 " + repeatableExact + " 个）、预测摘要不可重复 " + unstablePrediction
                + " 个、真值摘要不可重复 " + unstableTruth + " 个");
        return lines;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    /** 工具：只凭种子预测 → 与真实世界真值比较 → 不一致时补 FIRST DIVERGENCE 取证。 */
    private static PredictionOutcome predictAndCompare(ServerLevel level, OfflinePredictionSession session,
                                                       ChunkPos target) {
        PredictionOutcome predicted = TargetChunkPredictor.predictAroundTarget(session, target);
        // 真值必须在预测完成之后再读：读真值会强制生成真实区块，绝不能出现在预测输入路径上
        return FinalOreTruthComparator.attach(level, predicted, session.finalChunk(target));
    }

    /** 把系统属性里的区块清单解析成 ChunkPos 列表（正式化迁移回归装置也复用这一份）。 */
    static List<ChunkPos> chunksOf(List<long[]> raw) {
        List<ChunkPos> chunks = new ArrayList<>();
        for (long[] pair : raw) {
            chunks.add(new ChunkPos((int) pair[0], (int) pair[1]));
        }
        return chunks;
    }

    /** 把目标 ±2 的真实区块强制生成出来（真值读取与地形登记都需要它已完全生成）。 */
    private static void preload(ServerLevel level, ChunkPos target) {
        int radius = SeedPocConstants.REGION_RADIUS;
        for (int cx = target.x() - radius; cx <= target.x() + radius; cx++) {
            for (int cz = target.z() - radius; cz <= target.z() + radius; cz++) {
                level.getChunk(cx, cz);
            }
        }
    }

    private static int countExact(List<PredictionOutcome> outcomes) {
        int count = 0;
        for (PredictionOutcome outcome : outcomes) {
            if (outcome.exact()) {
                count++;
            }
        }
        return count;
    }

    private static long averageMillis(List<PredictionOutcome> outcomes) {
        if (outcomes.isEmpty()) {
            return 0;
        }
        long total = 0;
        for (PredictionOutcome outcome : outcomes) {
            total += outcome.elapsedMillis();
        }
        return total / outcomes.size();
    }

    /** 粗略内存（用户口径第二十节最后一项）：本进程堆在会话结束时的使用 / 上限。 */
    private static String memoryCn() {
        Runtime runtime = Runtime.getRuntime();
        return "粗略内存（本阶段全部目标跑完时的本进程堆）：已用 "
                + ((runtime.totalMemory() - runtime.freeMemory()) >> 20) + " MB / 当前堆 "
                + (runtime.totalMemory() >> 20) + " MB / 上限 " + (runtime.maxMemory() >> 20) + " MB";
    }

    /** 失败目标的一行定位信息（逐 BlockPos 漏报/错报样本）。 */
    private static String describeFailure(String model, PredictionOutcome outcome) {
        StringBuilder text = new StringBuilder();
        text.append(model).append(" 目标 (").append(outcome.target().x()).append(',')
                .append(outcome.target().z()).append(")：").append(outcome.verdictCn())
                .append("；漏报样本 ").append(PredictionOutcome.sample(outcome.missed(), 8))
                .append("；错报样本 ").append(PredictionOutcome.sample(outcome.extra(), 8));
        Set<BlockPos> crossChunk = new LinkedHashSet<>();
        for (Map.Entry<BlockPos, OfflineOreAttribution.Write> entry : outcome.provenance().entrySet()) {
            if (entry.getValue().crossChunk(outcome.target())) {
                crossChunk.add(entry.getKey());
            }
        }
        text.append("；归属信息：预测集里跨区块写入 ").append(crossChunk.size()).append(" 个");
        Map<String, Integer> byViewer = outcome.crossChunkByViewer();
        if (!byViewer.isEmpty()) {
            text.append("（按 viewer ").append(byViewer).append("）");
        }
        if (!outcome.divergenceStates().isEmpty()) {
            text.append("；漏报/错报坐标两侧方块：").append(outcome.divergenceStates());
        }
        if (!outcome.stateDiff().isEmpty()) {
            text.append("；").append(outcome.stateDiff());
        }
        return text.toString();
    }

    /** 失败目标：先记一行漏报/错报定位，再补一行结构比对（结构来源差异的直接证据）。 */
    private static void addFailure(List<String> failures, String model, PredictionOutcome outcome,
                                   ServerLevel level, OfflinePredictionSession session) {
        if (outcome.exact()) {
            return;
        }
        failures.add(describeFailure(model, outcome));
        failures.add(describeStructure(level, session, outcome.target()));
        failures.add(describeStages(session, outcome.target()));
    }

    /**
     * 诊断行（只在 {@link SeedPocFlags#round6Probe()} 打开时输出）：逐目标打印
     * ① 真实侧的装饰批号顺序（谁先装饰）；② 真实侧「SURFACE 之后到 CARVERS 之后」多出来的方块。
     *
     * <p>② 的意义：{@code CARVERS} 只会把方块改成空气 / 水 / 岩浆，因此该差值里若出现
     * 花岗岩 / 凝灰岩 / 苔藓 / 木栅栏这类方块，就说明真实侧那一刻已经有 {@code FEATURES}
     * 跨区块写入落进目标区块了 —— 即「邻 viewer 已经在目标装饰之前写过目标」。
     * 两行合起来就能判定「离线固定的『邻域全先于目标』这一顺序」与真实是否一致。</p>
     */
    private static String probeCn(PredictionOutcome outcome) {
        if (!SeedPocFlags.round6Probe()) {
            return "";
        }
        ChunkPos target = outcome.target();
        StringBuilder text = new StringBuilder();
        if (GenStageCapture.containsViewer(target)) {
            text.append("；真实装饰批号：").append(GenStageCapture.passSummaryCn(target));
        }
        text.append("；真实 SURFACE→CARVERS 多出的方块：")
                .append(StageComparator.centerDifferencePairs(GenStageCapture.stage(GenStageCapture.STAGE_SURFACE, target),
                        GenStageCapture.stage(GenStageCapture.STAGE_CARVERS, target)));
        return text.toString();
    }

    /**
     * 逐阶段 FIRST DIVERGENCE 取证（用户口径第十五节：「先定位再继续」）。
     *
     * <p>把离线侧与真实侧的 {@code BIOMES / NOISE / SURFACE / CARVERS} 四个 checkpoint
     * 在目标区块自己身上逐格比。结论只有三种，且三者指向完全不同的修法：</p>
     * <ul>
     *     <li>四个阶段全部一致 ⇒ 分叉发生在 {@code FEATURES}（装饰链路 / 跨区块写入 / 随机流）；</li>
     *     <li>前面几个一致、某一阶段起不一致 ⇒ 分叉就在那一层（生物群系源 / 噪声 / 地表 / 雕刻器）；</li>
     *     <li>快照没采到 ⇒ 不可判定，如实记录而不是退化成「一致」（真实侧采样的开关见
     *         {@link SeedPocFlags#round6Probe()}）。</li>
     * </ul>
     */
    private static String describeStages(OfflinePredictionSession session, ChunkPos target) {
        if (!SeedPocFlags.round6Probe()) {
            return "  ↳ 逐阶段比对：未采样（需 -Dyiyiaddon.seedpoc.round6.probe=1 才在真实侧采逐阶段 checkpoint）";
        }
        Map<String, GenStageSnapshot> offline = session.stagesOf(target);
        StringBuilder text = new StringBuilder("  ↳ 逐阶段比对（离线 vs 真实，只比目标区块自己）：");
        for (String stage : GenStageCapture.STAGES) {
            StageComparator.Result compared = StageComparator.compareCenterOnly(stage, target,
                    offline.get(stage), GenStageCapture.stage(stage, target));
            text.append('[').append(compared.cn());
            if (!compared.unjudgeable() && !compared.exact()) {
                text.append("；").append(compared.firstDifferenceCn());
            }
            text.append("] ");
        }
        // 依赖半径证据：判断「离线铺的邻域够不够」这一支是不是第一处分叉
        ChunkStep carvers = ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.CARVERS);
        text.append("；CARVERS 步半径：写半径=").append(carvers.blockStateWriteRadius())
                .append("，累积依赖半径=").append(carvers.accumulatedDependencies().size())
                .append(" 项，对 EMPTY 的累积半径=").append(carvers.getAccumulatedRadiusOf(ChunkStatus.EMPTY));

        // 取证一：CARVERS 那一刻到底是不是「只有雕刻」。雕刻只写 air/water/lava，
        // 所以「同侧 CARVERS 与同侧 SURFACE 的差」里若出现苔藓/黏土/木栅栏这类方块，
        // 说明那一刻已经有 FEATURES 链路跨区块写进来了 —— 该层取证被污染，不能当第一处分叉。
        text.append("；同侧 SURFACE → CARVERS 的差（判断该层取证是否被跨区块写入污染）：离线 ")
                .append(StageComparator.centerDifferencePairs(offline.get(GenStageCapture.STAGE_SURFACE),
                        offline.get(GenStageCapture.STAGE_CARVERS)))
                .append(" ／ 真实 ")
                .append(StageComparator.centerDifferencePairs(GenStageCapture.stage(GenStageCapture.STAGE_SURFACE, target),
                        GenStageCapture.stage(GenStageCapture.STAGE_CARVERS, target)));

        // 取证二：真实侧的装饰批号（越小越先装饰）。它是「谁先装饰」的唯一证据：
        // 批号小于中心的邻 viewer 才有机会在中心装饰之前把方块跨区块写进中心。
        if (GenStageCapture.containsViewer(target)) {
            text.append("；真实装饰批号顺序：").append(GenStageCapture.passSummaryCn(target));
        }
        return text.toString();
    }

    /**
     * 结构比对：目标 ±1 的每个区块，离线侧与真实侧的 {@code starts} / {@code refs}。
     *
     * <p>依据用户口径第十五节「结构来源」这一支：结构（尤其废弃矿井）在 {@code FEATURES} 阶段
     * 由 {@code StructureStart#placeInChunk} 写入，且它写的方块（比如橡木栅栏 / 洞穴空气）
     * 会改变同一步骤之后矿石的 {@code canPlaceOre} 判定与随机消耗次数。若两侧 starts/refs 不一致，
     * 第一处分叉就落在结构起点/引用这一层，而不是钻石链本身。</p>
     */
    private static String describeStructure(ServerLevel level, OfflinePredictionSession session, ChunkPos target) {
        RegistryAccess registries = level.registryAccess();
        int checked = 0;
        int differing = 0;
        StringBuilder detail = new StringBuilder();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos pos = new ChunkPos(target.x() + dx, target.z() + dz);
                String offline = structureLine(session.latestChunk(pos), registries);
                String real = structureLine(level.getChunk(pos.x(), pos.z()), registries);
                checked++;
                if (!offline.equals(real)) {
                    differing++;
                    if (differing <= 9) {
                        detail.append("[(").append(pos.x()).append(',').append(pos.z()).append(") 离线 ")
                                .append(offline).append(" / 真实 ").append(real).append("] ");
                    }
                }
            }
        }
        return "  ↳ 结构比对（目标 ±1）：共 " + checked + " 个区块，starts/refs 不一致 " + differing + " 个"
                + (differing == 0 ? "（结构起点与引用两侧一致）" : "；" + detail);
    }

    /** 一个区块的结构摘要（离线侧取已跑到的最远状态；结构只写一次，读哪一档都一样）。 */
    private static String structureLine(ChunkAccess chunk, RegistryAccess registries) {
        if (chunk == null) {
            return "（区块缺失）";
        }
        var structureRegistry = registries.lookupOrThrow(Registries.STRUCTURE);
        List<String> ids = new ArrayList<>();
        for (Structure structure : chunk.getAllStarts().keySet()) {
            var key = structureRegistry.getKey(structure);
            ids.add(key == null ? String.valueOf(structure) : key.toString());
        }
        ids.sort(String::compareTo);
        long refs = 0;
        for (Collection<Long> perStructure : chunk.getAllReferences().values()) {
            refs += perStructure.size();
        }
        return "starts=" + ids.size() + "[" + String.join(",", ids) + "] refs=" + refs;
    }
}
