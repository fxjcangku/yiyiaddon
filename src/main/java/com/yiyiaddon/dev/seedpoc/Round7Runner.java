package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第七轮 · 最终收口驱动器。
 *
 * <p><b>它只回答一个问题</b>（用户口径第三节）：
 * 「真实 Minecraft 26.1.2 中，多个 Chunk 的 FEATURES 对同一格产生竞争写入时，
 * 真实并发执行顺序能否因果性地改变最终自然钻石 BlockPos？」</p>
 *
 * <p><b>两个阶段</b>：</p>
 * <ol>
 *     <li>{@code case}：一个全新世界 + 一种合法请求顺序，一次把证据取全——
 *         真实最终钻石集合、FEATURES 逐格写入台账（含全局写序号）、同格冲突清单、
 *         OreFeature 候选点级真实序列；随后用 {@link ObservedOrderReplay} 在共享离线世界里
 *         ① 按真实观测顺序重放、② 按反事实顺序（对调两个 writer / 整体倒序）重放，
 *         并把三次结果与真值逐 BlockPos 比；</li>
 *     <li>{@code correlate}：把同一标签前缀的多次 {@code case} 运行拿来两两比——
 *         哪些 diamond BlockPos 不同、每个不同点在世界 A / 世界 B 里「最后写它的是谁」，
 *         以及两次的装饰批号顺序是否真的不同（用户口径第十四节）。</li>
 * </ol>
 *
 * <p>请求区块一律走原版 {@code ServerLevel#getChunk}（阻塞到 FULL），
 * <b>没有一处手工伪造装饰顺序</b>。</p>
 */
public final class Round7Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    private Round7Runner() {
    }

    /** 跑第七轮（必须在服务端线程上调用）。 */
    public static void run(IntegratedServer server) {
        long startedAt = System.currentTimeMillis();
        ServerLevel level = server.overworld();
        long seed = level.getSeed();
        String stage = SeedPocFlags.round7Stage();
        List<String> lines = new ArrayList<>();
        try {
            if ("correlate".equals(stage)) {
                lines.addAll(correlate());
            } else {
                lines.addAll(caseRun(level, seed));
            }
        } catch (Throwable error) {
            lines.add("第七轮运行异常（本阶段结论不成立）：" + error.getClass().getName() + ": "
                    + error.getMessage());
            LOGGER.error("{}：第七轮运行异常", SeedPocConstants.LOG_KEY, error);
        }
        long elapsed = System.currentTimeMillis() - startedAt;
        lines.add("END 阶段=" + stage + " 标签=" + SeedPocFlags.round7Label() + " 耗时=" + elapsed + " ms");
        Path path = FabricLoader.getInstance().getGameDir()
                .resolve("seedpoc-第七轮报告-" + stage + "-" + SeedPocFlags.round7Label() + "-" + seed + ".txt");
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
            LOGGER.info("{}：第七轮报告已落盘 {}", SeedPocConstants.LOG_KEY, path.toAbsolutePath());
        } catch (IOException error) {
            LOGGER.warn("{}：第七轮报告落盘失败", SeedPocConstants.LOG_KEY, error);
        }
        for (String line : lines) {
            LOGGER.info("{}｜第七轮｜{}", SeedPocConstants.LOG_KEY, line);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 阶段一：case（一个全新世界 + 一种合法请求顺序）
    // ────────────────────────────────────────────────────────────────────────

    private static List<String> caseRun(ServerLevel level, long seed) {
        List<String> out = new ArrayList<>();
        String label = SeedPocFlags.round7Label();
        List<long[]> raw = SeedPocFlags.round7Targets();
        if (raw.isEmpty()) {
            out.add("没有配置目标区块（-Dyiyiaddon.seedpoc.order.targets=cx,cz;…）");
            return out;
        }
        String scenarioId = SeedPocFlags.orderScenario() == null ? "A1" : SeedPocFlags.orderScenario();
        List<PredictionOutcome> outcomes = new ArrayList<>();
        // 台账只在进程内清一次：出生点附近的目标区块（例如 Seed 12345 的 (0,0)）在世界加载时
        // 就已经被装饰过了，那些「世界加载期」的写入与批号正是本轮要的证据，不能清掉。
        FeatureWriteJournal.reset();
        OreCandidateJournal.reset();

        for (long[] pair : raw) {
            ChunkPos target = new ChunkPos((int) pair[0], (int) pair[1]);
            caseOne(level, seed, target, scenarioId, label, outcomes, out);
        }
        if (!outcomes.isEmpty()) {
            BlockPosDiff total = BlockPosDiff.sum(outcomes.stream().map(PredictionOutcome::diff).toList());
            out.add("【汇总】目标 " + outcomes.size() + " 个（重放=观测顺序）：" + total.cn());
        }
        return out;
    }

    /** 一个目标区块的完整取证（明细全部追加进 out）。 */
    private static void caseOne(ServerLevel level, long seed, ChunkPos target, String scenarioId, String label,
                                List<PredictionOutcome> outcomes, List<String> out) {
        List<String> detail = new ArrayList<>();
        ChunkOrderScenario scenario = ChunkOrderScenario.of(scenarioId, target);
        FeatureWriteJournal.focusTarget(target);

        // ── 0) 新鲜度自证：驱动开始时目标 3x3 必须没有生成到 FULL ────────────
        boolean untouched = true;
        StringBuilder generated = new StringBuilder();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos probe = new ChunkPos(target.x() + dx, target.z() + dz);
                if (level.getChunkSource().getChunkNow(probe.x(), probe.z()) != null) {
                    untouched = false;
                    generated.append('(').append(probe.x()).append(',').append(probe.z()).append(") ");
                }
            }
        }
        boolean regionExisted = Files.exists(regionFile(target));

        // ── 1) 真实窗口：按合法顺序请求区块（原版 getChunk，阻塞到 FULL）─────
        List<ChunkOrderTruth.Step> steps = new ArrayList<>();
        Set<BlockPos> previous = Set.of();
        Map<Long, Integer> passBefore = journalBatches();
        for (int index = 0; index < scenario.order().size(); index++) {
            ChunkPos chunk = scenario.order().get(index);
            long begin = System.nanoTime();
            level.getChunk(chunk.x(), chunk.z());
            long millis = (System.nanoTime() - begin) / 1_000_000L;
            Set<BlockPos> now = diamondsIn(level, target);
            Set<BlockPos> added = new LinkedHashSet<>(now);
            added.removeAll(previous);
            previous = now;
            Map<Long, Integer> passes = journalBatches();
            List<String> decorated = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : passes.entrySet()) {
                if (!passBefore.containsKey(entry.getKey())) {
                    decorated.add(ChunkPos.getX(entry.getKey()) + "," + ChunkPos.getZ(entry.getKey()) + ":"
                            + entry.getValue());
                }
            }
            passBefore = passes;
            steps.add(new ChunkOrderTruth.Step(index + 1, chunk,
                    FeatureWriteJournal.viewerBatches().getOrDefault(chunk, -1),
                    added.size(), millis, List.copyOf(decorated)));
        }

        // ── 2) 真值 + 真实写入台账 + 冲突清单 ────────────────────────────────
        Set<BlockPos> truth = FinalOreTruthComparator.truth(level, target).all();
        Map<ChunkPos, Integer> batches = FeatureWriteJournal.viewerBatches();
        List<String> notes = new ArrayList<>();
        notes.add("请求顺序=" + scenario.orderCn());
        notes.add("驱动开始时目标 3x3 未生成=" + untouched
                + (generated.length() == 0 ? "" : "；已生成区块：" + generated));
        notes.add("目标区 region 文件存在=" + regionExisted);
        notes.add("本进程世界=" + level.getServer().getWorldData().getLevelName());
        FeatureWriteJournal.dump(level, label, notes);

        detail.add("请求顺序：" + scenario.orderCn());
        detail.add("新鲜度：驱动开始时目标 3x3 未生成=" + untouched + (generated.length() == 0 ? ""
                : "（例外：" + generated + "）") + "；目标区 region 文件存在=" + regionExisted);
        detail.add("真实装饰批号（本台账自记，越小越先进入 decoration）：" + batchesCn(batches));
        detail.add("真实最终钻石：" + truth.size() + " 格");
        detail.add("FEATURES 逐格写入台账：目标区块内真实侧 " + FeatureWriteJournal.writesInTarget(false)
                + " 条（全进程全局写序号到 " + FeatureWriteJournal.sequenceValue() + "）；"
                + "按 viewer：" + writesCn(FeatureWriteJournal.writesByViewer(true)));
        if (SeedPocFlags.round7Conflicts()) {
            detail.addAll(FeatureWriteJournal.describeConflicts(level, true));
        }
        if (OreCandidateJournal.armed()) {
            detail.add("OreFeature 候选点真实侧：" + OreCandidateJournal.size() + " 条");
        }

        // ── 3) Debug 重放 A：按真实观测顺序 ─────────────────────────────────
        List<ChunkPos> observed = ObservedOrderReplay.observedOrder(target, batches);
        FeatureWriteJournal.resetOffline();
        OreCandidateJournal.resetOffline();
        ObservedOrderReplay.Outcome replayObserved = ObservedOrderReplay.replay(level, seed, target, observed);
        List<FeatureWriteJournal.Entry> offlineChainA = FeatureWriteJournal.offlineEntries();
        List<OreCandidateJournal.Candidate> candA = OreCandidateJournal.offlineSnapshot();
        detail.add("重放 A（观测顺序）：" + replayObserved.digest() + "；顺序 " + replayObserved.orderCn());
        if (OreCandidateJournal.armed()) {
            detail.addAll(OreCandidateJournal.firstDivergence(truth));
        }

        // ── 3.5) Debug 重放 C：正式 Predictor 的固定顺序（由远到近、目标最后）────
        // 它就是第六轮 Predictor 内部真正用的顺序；把它与观测顺序重放放在一起比，
        // 才能把「Predictor 的固定顺序」与「真实世界的调度顺序」这两个量分开。
        FeatureWriteJournal.resetOffline();
        OreCandidateJournal.resetOffline();
        ObservedOrderReplay.Outcome replayPredictor =
                ObservedOrderReplay.replay(level, seed, target, TargetViewerSet.viewers(target));
        List<OreCandidateJournal.Candidate> candC = OreCandidateJournal.offlineSnapshot();
        BlockPosDiff predictorDiff = BlockPosDiff.of(truth, replayPredictor.diamonds());
        detail.add("重放 C（正式 Predictor 固定顺序）：" + replayPredictor.digest() + "；顺序 "
                + replayPredictor.orderCn());
        detail.add("对照③ 真值 vs 重放 C（Predictor 固定顺序）：" + predictorDiff.cn()
                + "（这一行应当等于第六轮 Predictor 在该目标上的成绩）");
        if (OreCandidateJournal.armed() && !candA.isEmpty()) {
            detail.addAll(OreCandidateJournal.firstDivergence(candA, candC,
                    "重放A(观测顺序)", "重放C(预测器固定顺序)", Set.of()));
        }

        // ── 4) Debug 重放 B：反事实顺序（对调两个 writer / 整体倒序）─────────
        List<ChunkPos> counterfactual;
        String counterfactualCn;
        List<long[]> swap = SeedPocFlags.round7Swap();
        if (swap != null && swap.size() == 2) {
            ChunkPos first = new ChunkPos(target.x() + (int) swap.get(0)[0], target.z() + (int) swap.get(0)[1]);
            ChunkPos second = new ChunkPos(target.x() + (int) swap.get(1)[0], target.z() + (int) swap.get(1)[1]);
            counterfactual = ObservedOrderReplay.swapOrder(observed, first, second);
            counterfactualCn = "对调 (" + first.x() + "," + first.z() + ") 与 (" + second.x() + ","
                    + second.z() + ") 的真实先后";
        } else {
            counterfactual = ObservedOrderReplay.reversedOrder(observed);
            counterfactualCn = "观测顺序整体倒序";
        }
        FeatureWriteJournal.resetOffline();
        OreCandidateJournal.resetOffline();
        ObservedOrderReplay.Outcome replayCounter = ObservedOrderReplay.replay(level, seed, target, counterfactual);
        List<FeatureWriteJournal.Entry> offlineChainB = FeatureWriteJournal.offlineEntries();
        detail.add("重放 B（反事实：" + counterfactualCn + "）：" + replayCounter.digest() + "；顺序 "
                + replayCounter.orderCn());

        // ── 5) 三方对照：真值 / 观测顺序重放 / 反事实重放 ────────────────────
        BlockPosDiff observedDiff = BlockPosDiff.of(truth, replayObserved.diamonds());
        BlockPosDiff counterDiff = BlockPosDiff.of(replayObserved.diamonds(), replayCounter.diamonds());
        detail.add("对照① 真值 vs 重放 A（观测顺序）：" + observedDiff.cn()
                + (observedDiff.exact() ? " → 观测顺序重放复现了真实集合" : ""));
        detail.add("对照② 重放 A vs 重放 B（只差执行先后）：" + counterDiff.cn()
                + (counterDiff.exact()
                ? " → 本目标上「换顺序不改变最终集合」"
                : " → **仅改变合法 FEATURES 执行先后就改变了最终钻石集合**（因果证据）"));
        if (!counterDiff.exact()) {
            Set<BlockPos> onlyA = onlyIn(replayObserved.diamonds(), replayCounter.diamonds());
            Set<BlockPos> onlyB = onlyIn(replayCounter.diamonds(), replayObserved.diamonds());
            detail.add("  差集样本（重放 A 有 / 重放 B 无）：" + PredictionOutcome.sample(onlyA, 8));
            detail.add("  差集样本（重放 B 有 / 重放 A 无）：" + PredictionOutcome.sample(onlyB, 8));
            // 用户口径第十四 / 二十节：差异格必须给出「谁最后写」
            List<BlockPos> keyPositions = new ArrayList<>(onlyB);
            keyPositions.addAll(onlyA);
            int shown = 0;
            for (BlockPos pos : keyPositions) {
                if (shown++ >= 8) {
                    detail.add("  （差异格过多，只列前 8 个；完整写入链见落盘的 journal 文件）");
                    break;
                }
                detail.add("  差异格 (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")：");
                detail.add("    重放A（观测顺序）："
                        + FeatureWriteJournal.chainCn(FeatureWriteJournal.chainIn(offlineChainA, pos)));
                detail.add("    重放B（反事实顺序）："
                        + FeatureWriteJournal.chainCn(FeatureWriteJournal.chainIn(offlineChainB, pos)));
                detail.add("    真实世界："
                        + FeatureWriteJournal.chainCn(FeatureWriteJournal.chainAt(pos)));
            }
        }

        // ── 6) 落盘真值（供 correlate 跨运行比对）───────────────────────────
        writeTruth(level, label, seed, target, scenario, batches, steps, truth, untouched, regionExisted);

        // 汇总行
        PredictionOutcome outcome = new PredictionOutcome("第七轮·观测顺序重放", target,
                TargetViewerSet.writeRadius(), observed, Set.copyOf(replayObserved.diamonds()), Map.of(), List.of(),
                null, null, 0L, 0, 0, 0, observed.size(), "", Map.of(), "");
        outcomes.add(FinalOreTruthComparator.attach(level, outcome));
        out.add("目标 (" + target.x() + "," + target.z() + ")：真值 " + truth.size() + " / 重放A(观测顺序) "
                + replayObserved.diamonds().size() + " / 重放B(反事实) " + replayCounter.diamonds().size()
                + "；真值 vs 重放A：" + (observedDiff.exact() ? "完全一致" : observedDiff.cn())
                + "；重放A vs 重放B：" + (counterDiff.exact() ? "完全一致（顺序不改变集合）" : counterDiff.cn()));
        out.addAll(detail);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 阶段二：correlate（跨运行相关性）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 跨运行相关性（用户口径第十四节）：
     * 同一标签前缀的多次运行，两两比最终钻石集合；每个不同的 BlockPos 回查两次运行里
     * 「最后一次写它的是谁」；并比较两次的装饰批号顺序是否真的不同。
     */
    private static List<String> correlate() {
        List<String> out = new ArrayList<>();
        String prefix = SeedPocFlags.round7Prefix();
        List<ChunkOrderTruth> truths = new ArrayList<>();
        for (ChunkOrderTruth truth : ChunkOrderTruth.loadAll()) {
            if (truth.scenario().startsWith(prefix)) {
                truths.add(truth);
            }
        }
        truths.sort(Comparator.comparing(ChunkOrderTruth::scenario));
        out.add("【相关性】标签前缀 " + prefix + " 的运行份数：" + truths.size());
        if (truths.size() < 2) {
            out.add("少于两份运行，无法做相关性比对（每一份运行必须用不同的 round7.label）");
            return out;
        }
        for (ChunkOrderTruth truth : truths) {
            out.add("  " + truth.scenario() + "：真值 " + truth.allDiamonds().size() + " 格；批号 "
                    + batchesCn(truth.batches()) + "；顺序 " + orderCn(truth.order()));
        }
        for (int i = 0; i < truths.size(); i++) {
            for (int j = i + 1; j < truths.size(); j++) {
                ChunkOrderTruth left = truths.get(i);
                ChunkOrderTruth right = truths.get(j);
                if (!left.target().equals(right.target())) {
                    continue;
                }
                BlockPosDiff diff = BlockPosDiff.of(left.allDiamonds(), right.allDiamonds());
                out.add("【" + left.scenario() + " vs " + right.scenario() + "】" + diff.cn());
                boolean sameBatches = left.batches().equals(right.batches());
                out.add("  装饰批号相同=" + sameBatches + "：左 " + batchesCn(left.batches())
                        + " ／ 右 " + batchesCn(right.batches()));
                if (diff.exact()) {
                    continue;
                }
                Map<BlockPos, String> leftWriters = FeatureWriteJournal.loadFinalWriters(
                        FeatureWriteJournal.fileOf(left.scenario(), left.target()));
                Map<BlockPos, String> rightWriters = FeatureWriteJournal.loadFinalWriters(
                        FeatureWriteJournal.fileOf(right.scenario(), right.target()));
                int shown = 0;
                Set<BlockPos> interesting = new LinkedHashSet<>(onlyIn(left.allDiamonds(), right.allDiamonds()));
                interesting.addAll(onlyIn(right.allDiamonds(), left.allDiamonds()));
                for (BlockPos pos : interesting) {
                    if (shown++ >= 12) {
                        break;
                    }
                    out.add("  差异格 (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")：" + left.scenario()
                            + " " + leftWriters.getOrDefault(pos, "（无写入记录）") + " ／ " + right.scenario() + " "
                            + rightWriters.getOrDefault(pos, "（无写入记录）"));
                }
                out.add("  差异格合计 " + interesting.size() + " 个（最多列 12 个）");
            }
        }
        return out;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    /** 只在 A 里、不在 B 里的坐标（集合差，跨重放比对用）。 */
    private static Set<BlockPos> onlyIn(Set<BlockPos> left, Set<BlockPos> right) {
        Set<BlockPos> result = new LinkedHashSet<>(left);
        result.removeAll(right);
        return result;
    }

    /** 本台账自记的装饰批号（键 = packed ChunkPos）。 */
    private static Map<Long, Integer> journalBatches() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<ChunkPos, Integer> entry : FeatureWriteJournal.viewerBatches().entrySet()) {
            map.put(entry.getKey().pack(), entry.getValue());
        }
        return map;
    }

    /** 读目标区块当前的钻石（只读，未生成返回空集：绝不能替实验把目标区块生成掉）。 */
    private static Set<BlockPos> diamondsIn(ServerLevel level, ChunkPos target) {
        Set<BlockPos> found = new LinkedHashSet<>();
        LevelChunk chunk = level.getChunkSource().getChunkNow(target.x(), target.z());
        if (chunk == null) {
            return found;
        }
        int minSectionY = level.getMinSectionY();
        LevelChunkSection[] sections = chunk.getSections();
        for (int index = 0; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section == null || section.hasOnlyAir()) {
                continue;
            }
            int baseY = (minSectionY + index) * 16;
            for (int localX = 0; localX < 16; localX++) {
                for (int localY = 0; localY < 16; localY++) {
                    for (int localZ = 0; localZ < 16; localZ++) {
                        if (OreBlockLedger.isDiamond(section.getBlockState(localX, localY, localZ))) {
                            found.add(new BlockPos((target.x() << 4) + localX, baseY + localY,
                                    (target.z() << 4) + localZ));
                        }
                    }
                }
            }
        }
        return found;
    }

    /** 落盘一份 ChunkOrderTruth（供 correlate 跨运行比对；provenance 走第七轮台账，不在这里重复）。 */
    private static void writeTruth(ServerLevel level, String label, long seed, ChunkPos target,
                                   ChunkOrderScenario scenario, Map<ChunkPos, Integer> batches,
                                   List<ChunkOrderTruth.Step> steps, Set<BlockPos> truth, boolean untouched,
                                   boolean regionExisted) {
        Set<BlockPos> diamondOre = new LinkedHashSet<>();
        Set<BlockPos> deepslate = new LinkedHashSet<>();
        for (BlockPos pos : truth) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.DIAMOND_ORE)) {
                diamondOre.add(pos);
            } else if (state.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                deepslate.add(pos);
            }
        }
        ChunkOrderTruth orderTruth = new ChunkOrderTruth(label, seed, target, ChunkOrderTestRunner.environment(),
                SeedPocWorldFactory.LEVEL_ID, ChunkOrderTestRunner.worldDir().toString(),
                SeedPocWorldFactory.lastFreshOk(), SeedPocWorldFactory.lastFreshNote(), untouched, regionExisted,
                scenario.order(), batches, steps, diamondOre, deepslate, Map.of());
        orderTruth.write();
    }

    /** 目标区块所属 region 文件（原版命名：r.<cx>>5>.<cz>>5>.mca）。 */
    private static Path regionFile(ChunkPos target) {
        return ChunkOrderTestRunner.worldDir().resolve("region")
                .resolve("r." + (target.x() >> 5) + "." + (target.z() >> 5) + ".mca");
    }

    /** 批号表文本（相对目标区块的偏移，报告可读）。 */
    private static String batchesCn(Map<ChunkPos, Integer> batches) {
        if (batches.isEmpty()) {
            return "无（本次窗口内没有登记到装饰批号）";
        }
        List<Map.Entry<ChunkPos, Integer>> entries = new ArrayList<>(batches.entrySet());
        entries.sort(Comparator.comparingInt(Map.Entry::getValue));
        StringBuilder text = new StringBuilder();
        for (Map.Entry<ChunkPos, Integer> entry : entries) {
            if (text.length() > 0) {
                text.append("、");
            }
            text.append('(').append(entry.getKey().x()).append(',').append(entry.getKey().z()).append(")=")
                    .append(entry.getValue());
        }
        return text.toString();
    }

    /** 写入条数表文本。 */
    private static String writesCn(Map<ChunkPos, Integer> counts) {
        if (counts.isEmpty()) {
            return "无";
        }
        StringBuilder text = new StringBuilder();
        for (Map.Entry<ChunkPos, Integer> entry : counts.entrySet()) {
            if (text.length() > 0) {
                text.append("、");
            }
            text.append('(').append(entry.getKey().x()).append(',').append(entry.getKey().z()).append(")×")
                    .append(entry.getValue());
        }
        return text.toString();
    }

    /** 顺序文本。 */
    private static String orderCn(List<ChunkPos> order) {
        StringBuilder text = new StringBuilder();
        for (ChunkPos pos : order) {
            if (text.length() > 0) {
                text.append(" → ");
            }
            text.append('(').append(pos.x()).append(',').append(pos.z()).append(')');
        }
        return text.toString();
    }
}
