package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.prediction.SeedOrePredictor;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.worldgen.OfflineChunkPipeline;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第一阶段（报告 229）· <b>正式 Predictor 迁移回归装置</b>（开发期，DebugProbe 口径）。
 *
 * <p><b>它是谁</b>：唯一一处允许「正式层与 PoC 层同框」的地方——依赖方向永远是
 * {@code dev.seedpoc → com.yiyiaddon.seed}（正式层<b>禁止</b>反向依赖，正式化第一阶段口径第二十八节）。
 * 正式代码一行都不知道 PoC 的存在，而本类用 PoC 当 Oracle 来验收迁移结果。</p>
 *
 * <p><b>它回答四件事</b>（对应正式化第一阶段口径第三十五、四十八节）：</p>
 * <ol>
 *     <li><b>A 迁移一致性</b>：同一 种子 / 维度 / 目标下，正式 {@code SeedOrePredictor}（236 前的
 *         {@code DiamondSeedPredictor}）与
 *         PoC 固定顺序预测器是否<b>逐 BlockPos</b> 一致（固定集 10 目标 + Seed 12345 四区块）；</li>
 *     <li><b>B 分类正确性</b>：Seed 2 的已知调度争议位置 {@code (-6385,-59,6085)}
 *         <b>不得</b>被标成确定性；</li>
 *     <li><b>C 会话种子隔离</b>：种子 A → B → A，两次 A 必须逐 BlockPos 完全一致；</li>
 *     <li><b>D 相邻目标缓存复用</b>：同一会话下第二个（相邻）目标必须出现缓存命中、且代价明显低于冷启动。</li>
 * </ol>
 *
 * <p><b>它只读、不写真实世界</b>：真值读数与「清空—重放—还原」那套装置一概不用——
 * 本轮的验收口径是「正式层与 PoC 层算出同一份结果」，与真实世界的最终状态无关
 * （228 已定案：真实最终状态不是 Seed 的唯一函数，不构成本轮验收条件）。</p>
 *
 * <p>触发方式（默认完全关闭，不给系统属性时本类不会被加载执行）：</p>
 * <pre>{@code
 * -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.formal=1
 * -Dyiyiaddon.seedpoc.seed=20260922 -Dyiyiaddon.seedpoc.round6.stage=fixed
 * -Dyiyiaddon.seedpoc.exit=1
 * }</pre>
 */
public final class FormalSeedRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** Seed 2 的已知调度争议位置（228 报告第七节实测：gravel ↔ deepslate_diamond_ore 随合法顺序翻转）。 */
    private static final BlockPos SEED2_DISPUTED_POS = new BlockPos(-6385, -59, 6085);

    private FormalSeedRegression() {
    }

    /** 跑一次迁移回归（必须在服务端线程上调用）。 */
    public static void run(IntegratedServer server) {
        long startedAt = System.currentTimeMillis();
        ServerLevel level = server.overworld();
        long seed = SeedPocFlags.fixedTestSeed();
        String stage = SeedPocFlags.round6Stage();
        List<ChunkPos> targets = targetsFor(stage);

        List<String> lines = new ArrayList<>();
        lines.add("《229 · 种子挖矿正式化第一阶段 · 正式 Predictor 迁移回归结果》");
        lines.add("阶段：" + stage + "；种子：" + seed + "；维度：" + level.dimension().identifier());
        lines.add("正式包：com.yiyiaddon.seed（正式层不依赖 dev.seedpoc；本装置是 dev 侧调用正式层）");
        lines.add("验收口径：正式 Predictor 与 PoC 固定顺序预测器逐 BlockPos 一致；"
                + "不比较真实世界最终状态（228 已定案：它不是 Seed 的唯一函数）");
        lines.add("正式写半径依据：" + OfflineChunkPipeline.writeRadiusEvidence());
        lines.add("PoC 写半径依据：" + TargetViewerSet.evidence());
        lines.addAll(OfflineChunkPipeline.dependencyTable());

        // ── A / D：正式 vs PoC 逐 BlockPos + 各目标代价统计 ──────────────────────
        List<String> sectionA = new ArrayList<>();
        List<PredictionResult> formalResults = new ArrayList<>();
        List<PredictionResult> failureResults = new ArrayList<>();
        List<BlockPosDiff> diffs = new ArrayList<>();
        SeedOrePredictor predictor = new SeedOrePredictor(level);
        OfflinePredictionSession pocSession = OfflinePredictionSession.open(level, seed);
        try {
            for (ChunkPos target : targets) {
                PredictionResult formal = predictor.predictDiamond(seed, target);
                formalResults.add(formal);
                if (formal.failed()) {
                    failureResults.add(formal);
                    sectionA.add("目标 (" + target.x() + "," + target.z() + ")：**正式预测失败** —— "
                            + formal.failureReason());
                    continue;
                }
                Set<BlockPos> formalSet = positionsOf(formal);
                Set<BlockPos> pocSet = TargetChunkPredictor.predictAroundTarget(pocSession, target).predicted();
                BlockPosDiff diff = BlockPosDiff.of(pocSet, formalSet);
                diffs.add(diff);
                sectionA.add("目标 (" + target.x() + "," + target.z() + ")：PoC " + pocSet.size()
                        + " 个 / 正式 " + formalSet.size() + " 个 → " + diff.cn() + " → "
                        + (diff.exact() ? "逐 BlockPos 完全一致" : "**不一致（迁移前后行为不同）**"));
                if (!diff.exact()) {
                    sectionA.add("    差异样本：PoC 有正式没有 " + sample(difference(pocSet, formalSet))
                            + "；正式有 PoC 没有 " + sample(difference(formalSet, pocSet)));
                }
            }
        } finally {
            predictor.close();
        }
        BlockPosDiff total = diffs.isEmpty() ? null : BlockPosDiff.sum(diffs);
        int exactTargets = 0;
        for (BlockPosDiff diff : diffs) {
            if (diff.exact()) {
                exactTargets++;
            }
        }
        sectionA.add("合计：" + (total == null ? "无可比较目标" : total.cn())
                + "；逐 BlockPos 完全一致 " + exactTargets + "/" + targets.size()
                + "；正式预测失败 " + failureResults.size() + " 个");

        // ── 各目标分类与代价（D：冷启动 vs 相邻） ─────────────────────────────
        List<String> sectionD = new ArrayList<>();
        for (PredictionResult result : formalResults) {
            ChunkPos target = result.request().chunk();
            if (result.failed()) {
                sectionD.add("目标 (" + target.x() + "," + target.z() + ")：预测失败（" + result.failureReason() + "）");
                continue;
            }
            PredictionResult.Stats stats = result.stats();
            sectionD.add("目标 (" + target.x() + "," + target.z() + ")：正式 " + result.count() + " 个"
                    + "（调度敏感 " + result.scheduleSensitiveCount() + " / 未解析 " + result.unresolvedCount()
                    + " / 确定性 " + result.deterministicCount() + "）；耗时 " + result.elapsedMillis() + " ms"
                    + " / 新建区块 " + stats.protoChunks()
                    + " / 阶段执行 " + stats.stageExecutions()
                    + "（缓存命中跳过 " + stats.cacheHits() + "）"
                    + " / 会话持有区块 " + stats.heldChunks()
                    + " / 宿主查询 " + stats.hostChunkSourceQueries());
            for (String note : stats.notes()) {
                sectionD.add("    · " + note);
            }
        }

        // ── B：Seed 2 已知争议位置的分类 ──────────────────────────────────────
        List<String> sectionB = new ArrayList<>();
        boolean disputedNotDeterministic = true;
        long conflictSeed = SeedPocFlags.formalConflictSeed();
        List<ChunkPos> conflictTargets = PredictionRegressionSuite.chunksOf(SeedPocFlags.formalConflictTargets());
        SeedOrePredictor conflictPredictor = new SeedOrePredictor(level);
        try {
            for (ChunkPos target : conflictTargets) {
                PredictionResult result = conflictPredictor.predictDiamond(conflictSeed, target);
                sectionB.add("Seed " + conflictSeed + " 目标 (" + target.x() + "," + target.z() + ")："
                        + (result.failed() ? "预测失败：" + result.failureReason() : result.count() + " 个"
                        + "（调度敏感 " + result.scheduleSensitiveCount() + " / 未解析 "
                        + result.unresolvedCount() + " / 确定性 " + result.deterministicCount() + "）"));
                if (result.failed()) {
                    continue;
                }
                for (String note : result.stats().notes()) {
                    sectionB.add("    · " + note);
                }
                PredictedOre disputed = findOre(result, SEED2_DISPUTED_POS);
                if (disputed == null) {
                    sectionB.add("    已知争议位置 (" + SEED2_DISPUTED_POS.getX() + ","
                            + SEED2_DISPUTED_POS.getY() + "," + SEED2_DISPUTED_POS.getZ()
                            + ") 不在本次预测集里（另一种合法结果），因此不存在误标风险");
                } else {
                    sectionB.add("    已知争议位置 " + disputed.describeCn());
                    if (disputed.certainty() == PredictionCertainty.DETERMINISTIC) {
                        disputedNotDeterministic = false;
                    }
                }
                boolean anyDeterministic = false;
                for (PredictedOre ore : result.ores()) {
                    if (ore.certainty() == PredictionCertainty.DETERMINISTIC) {
                        anyDeterministic = true;
                        break;
                    }
                }
                sectionB.add("    本目标是否存在被标成确定性的坐标：" + (anyDeterministic ? "**存在（违规）**" : "无"));
                if (anyDeterministic) {
                    disputedNotDeterministic = false;
                }
            }
        } finally {
            conflictPredictor.close();
        }

        // ── C：会话种子隔离（A → B → A） ─────────────────────────────────────
        List<String> sectionC = new ArrayList<>();
        ChunkPos isolationTarget = targets.isEmpty() ? new ChunkPos(0, 0) : targets.get(0);
        long seedB = seed == 12345L ? 20260922L : 12345L;
        SeedOrePredictor isolationPredictor = new SeedOrePredictor(level);
        boolean isolationOk;
        try {
            PredictionResult first = isolationPredictor.predictDiamond(seed, isolationTarget);
            PredictionResult other = isolationPredictor.predictDiamond(seedB, isolationTarget);
            PredictionResult second = isolationPredictor.predictDiamond(seed, isolationTarget);
            sectionC.add("实验目标区块：(" + isolationTarget.x() + "," + isolationTarget.z()
                    + ")；种子 A=" + seed + " / B=" + seedB);
            if (first.failed() || other.failed() || second.failed()) {
                isolationOk = false;
                sectionC.add("**有预测失败**：" + first.failureReason() + " / " + other.failureReason()
                        + " / " + second.failureReason());
            } else {
                BlockPosDiff aa = BlockPosDiff.of(positionsOf(first), positionsOf(second));
                BlockPosDiff ab = BlockPosDiff.of(positionsOf(first), positionsOf(other));
                isolationOk = aa.exact();
                sectionC.add("第一次 A：" + first.count() + " 个；B：" + other.count() + " 个；第二次 A："
                        + second.count() + " 个");
                sectionC.add("第一次 A vs 第二次 A：" + aa.cn() + " → "
                        + (aa.exact() ? "完全一致（同种子可重复，跨种子未污染）" : "**不一致，缓存未隔离**"));
                sectionC.add("A vs B：" + ab.cn() + "（两个种子本来就该不同，这里只证明 B 确实换了种子）");
            }
            sectionC.add("会话数（A / B 两个会话）：" + isolationPredictor.sessionCount()
                    + "；累计持有离线区块 " + isolationPredictor.cachedChunks() + " 个");
        } finally {
            isolationPredictor.close();
        }

        // ── D：相邻目标缓存复用判定 ──────────────────────────────────────────
        boolean cacheReuseOk = false;
        List<String> reuseLines = new ArrayList<>();
        for (int index = 0; index < formalResults.size(); index++) {
            PredictionResult result = formalResults.get(index);
            if (result.failed()) {
                continue;
            }
            if (index > 0 && result.stats().cacheHits() > 0) {
                cacheReuseOk = true;
                reuseLines.add("第 " + (index + 1) + " 个目标 (" + result.request().chunk().x() + ","
                        + result.request().chunk().z() + ") 复用前置阶段：缓存命中跳过 "
                        + result.stats().cacheHits() + " 次、新建区块仅 " + result.stats().protoChunks()
                        + " 个，耗时 " + result.elapsedMillis() + " ms");
                break;
            }
        }
        if (reuseLines.isEmpty()) {
            reuseLines.add("未观察到缓存复用（本阶段目标之间可能不相邻）");
        }

        // ── 判定汇总 ────────────────────────────────────────────────────────
        boolean migrationOk = total != null && exactTargets == targets.size() && failureResults.isEmpty();
        Runtime runtime = Runtime.getRuntime();
        List<String> verdict = new ArrayList<>();
        verdict.add("【判定 A】正式 Predictor vs PoC 固定顺序预测器逐 BlockPos 一致："
                + (migrationOk ? "通过" : "**不通过**") + "（一致 " + exactTargets + "/" + targets.size()
                + "，失败 " + failureResults.size() + "）");
        verdict.add("【判定 B】Seed 2 已知争议位置未被错误标成确定性：" + (disputedNotDeterministic ? "通过" : "**不通过**"));
        verdict.add("【判定 C】会话种子隔离 A→B→A：" + (isolationOk ? "通过" : "**不通过**"));
        verdict.add("【判定 D】相邻目标缓存复用：" + (cacheReuseOk ? "通过" : "**不通过**"));
        verdict.add("【附加】全部结果里确定性分类计数恒为 0（本阶段不产出 DETERMINISTIC）："
                + "见各目标行；宿主 ChunkMap 查询次数增量必须为 0");

        lines.add("");
        lines.add("一、正式 Predictor VS PoC Predictor（逐 BlockPos，左侧=PoC 基线 / 右侧=正式）");
        lines.addAll(sectionA);
        lines.add("");
        lines.add("二、正式 Predictor 各目标分类与代价（含调度敏感复核诊断）");
        lines.addAll(sectionD);
        lines.add("");
        lines.add("三、Seed 2 已知调度争议位置分类（228 报告第七节样本）");
        lines.addAll(sectionB);
        lines.add("");
        lines.add("四、会话种子隔离 A → B → A");
        lines.addAll(sectionC);
        lines.add("");
        lines.add("五、相邻目标缓存复用");
        lines.addAll(reuseLines);
        lines.add("");
        lines.add("六、判定汇总");
        lines.addAll(verdict);
        lines.add("");
        lines.add("粗略内存（本阶段结束时的本进程堆）：已用 "
                + ((runtime.totalMemory() - runtime.freeMemory()) >> 20) + " MB / 当前堆 "
                + (runtime.totalMemory() >> 20) + " MB / 上限 " + (runtime.maxMemory() >> 20) + " MB");
        lines.add("装置总耗时：" + (System.currentTimeMillis() - startedAt) + " ms");

        Path written = SeedPocReport.output(lines, "seedpoc-正式化回归-" + stage + "-" + seed + ".txt");
        LOGGER.info("{}：正式化迁移回归完成，报告 {}", SeedPocConstants.LOG_KEY, written);
        for (String line : verdict) {
            LOGGER.info("{}：{}", SeedPocConstants.LOG_KEY, line);
        }
    }

    /** 当前阶段的固定测试目标（沿用第六轮清单，不做任何替换——失败案例也原样保留）。 */
    private static List<ChunkPos> targetsFor(String stage) {
        List<long[]> raw = switch (stage) {
            case "regression" -> SeedPocFlags.round6RegressionTargets();
            case "generalize" -> SeedPocFlags.round6GeneralizeTargets();
            default -> SeedPocFlags.round6FixedTargets();
        };
        return PredictionRegressionSuite.chunksOf(raw);
    }

    /** 正式结果里的坐标集合（按插入顺序）。 */
    private static Set<BlockPos> positionsOf(PredictionResult result) {
        Set<BlockPos> positions = new LinkedHashSet<>();
        for (PredictedOre ore : result.ores()) {
            positions.add(ore.position());
        }
        return positions;
    }

    /** 找出某个具体坐标的预测条目；不在预测集里返回 null。 */
    private static PredictedOre findOre(PredictionResult result, BlockPos pos) {
        for (PredictedOre ore : result.ores()) {
            if (ore.position().equals(pos)) {
                return ore;
            }
        }
        return null;
    }

    /** 左集合减去右集合。 */
    private static Set<BlockPos> difference(Set<BlockPos> left, Set<BlockPos> right) {
        Set<BlockPos> result = new LinkedHashSet<>(left);
        result.removeAll(right);
        return result;
    }

    /** 坐标样本（最多 6 个）。 */
    private static String sample(Set<BlockPos> positions) {
        if (positions.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= 6) {
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
}
