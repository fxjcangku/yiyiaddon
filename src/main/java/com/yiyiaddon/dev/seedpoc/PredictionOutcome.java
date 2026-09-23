package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿 PoC 第六轮 · 单个目标区块的预测结果（进报告的数据载体）。
 *
 * <p>它是「纯 Seed 预测」与「真实世界真值」的合流点：</p>
 * <ol>
 *     <li>{@link #predicted()} / {@link #provenance()} / {@link #contributions()} 由
 *         {@link TargetChunkPredictor} 只凭种子离线算出；</li>
 *     <li>{@link #truth()} / {@link #diff()} 由 {@link FinalOreTruthComparator} 读真实世界补上
 *         （测试侧唯一允许读真实世界的地方）。</li>
 * </ol>
 *
 * <p>两个口径在这里仍然是分开的字段：预测侧永远不接触真值，真值侧永远不参与预测。</p>
 */
record PredictionOutcome(
        String model,
        ChunkPos target,
        int extraRadius,
        List<ChunkPos> viewers,
        Set<BlockPos> predicted,
        Map<BlockPos, OfflineOreAttribution.Write> provenance,
        List<ViewerContribution> contributions,
        FinalOreCollector.OreSet truth,
        BlockPosDiff diff,
        long elapsedMillis,
        int protoChunks,
        long stageRuns,
        long stageSkipped,
        int decoratePasses,
        String note,
        Map<BlockPos, String> divergenceStates,
        String stateDiff) {

    /**
     * 一个 viewer 这一遍 {@code FEATURES} 往目标区块新增的钻石。
     *
     * @param viewer       写入方区块（= 这一次 {@code applyBiomeDecoration} 的中心）
     * @param addedInTarget 本遍新增到目标区块的钻石坐标
     */
    record ViewerContribution(ChunkPos viewer, Set<BlockPos> addedInTarget) {
    }

    /** 是否已经与真实世界真值比较过。 */
    boolean compared() {
        return truth != null && diff != null;
    }

    /** 是否逐 BlockPos 完全一致。 */
    boolean exact() {
        return compared() && diff.exact();
    }

    /** 漏报（真值有、预测没有）。 */
    Set<BlockPos> missed() {
        Set<BlockPos> result = new LinkedHashSet<>(truth == null ? Set.of() : truth.all());
        result.removeAll(predicted);
        return result;
    }

    /** 错报（预测有、真值没有）。 */
    Set<BlockPos> extra() {
        Set<BlockPos> result = new LinkedHashSet<>(predicted);
        result.removeAll(truth == null ? Set.of() : truth.all());
        return result;
    }

    /** 跨区块写入的坐标数（预测集里由邻 viewer 写进来的那一部分）。 */
    int crossChunkCount() {
        int count = 0;
        for (OfflineOreAttribution.Write write : provenance.values()) {
            if (write.crossChunk(target)) {
                count++;
            }
        }
        return count;
    }

    /** 按 viewer 归属的跨区块写入坐标数（顺序 = viewer 首次出现顺序）。 */
    Map<String, Integer> crossChunkByViewer() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (OfflineOreAttribution.Write write : provenance.values()) {
            if (write.crossChunk(target)) {
                counts.merge(write.viewer().x() + "," + write.viewer().z(), 1, Integer::sum);
            }
        }
        return counts;
    }

    /** 逐 viewer「本遍新增到目标区块」的钻石数（0 = 该 viewer 在本会话更早的目标区块里就已装饰过）。 */
    Map<String, Integer> contributionByViewer() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (ViewerContribution contribution : contributions) {
            counts.put(contribution.viewer().x() + "," + contribution.viewer().z(),
                    contribution.addedInTarget().size());
        }
        return counts;
    }

    /** 补上真值与差异（不可变复制，预测侧字段一字不动）。 */
    PredictionOutcome withTruth(FinalOreCollector.OreSet truthSet, BlockPosDiff posDiff, String extraNote) {
        return new PredictionOutcome(model, target, extraRadius, viewers, predicted, provenance, contributions,
                truthSet, posDiff, elapsedMillis, protoChunks, stageRuns, stageSkipped, decoratePasses,
                extraNote == null || extraNote.isEmpty() ? note : (note.isEmpty() ? extraNote : note + "；" + extraNote),
                divergenceStates, stateDiff);
    }

    /**
     * 补上 FIRST DIVERGENCE 取证。
     *
     * @param states    漏报/错报坐标在「离线区块 / 真实区块」两侧的方块对照
     * @param stateDiff 目标区块逐格比对摘要（用来区分「输入状态不同」与「钻石链本身不同」）
     */
    PredictionOutcome withDivergence(Map<BlockPos, String> states, String stateDiff) {
        return new PredictionOutcome(model, target, extraRadius, viewers, predicted, provenance, contributions,
                truth, diff, elapsedMillis, protoChunks, stageRuns, stageSkipped, decoratePasses, note,
                states, stateDiff);
    }

    /**
     * 预测集 / 真值集的稳定摘要（跨运行比对用）。
     *
     * <p>{@code Set#hashCode} 是元素哈希之和、与遍历顺序无关，因此同一集合在任何一次运行里的取值相同；
     * 两次运行摘要不同即说明「那一侧真的变了」。它是判断「离线预测是否与宿主世界状态有关」
     * 与「真实世界真值是否可重复」的唯一低成本证据。</p>
     */
    String digest() {
        return "预测集摘要 h=" + Integer.toHexString(predicted.hashCode()) + " n=" + predicted.size()
                + " / 真值集摘要 h=" + (truth == null ? "无"
                : Integer.toHexString(truth.all().hashCode()) + " n=" + truth.all().size());
    }

    /** 一行判定文本。 */
    String verdictCn() {
        if (!compared()) {
            return "未与真值比较";
        }
        return diff.cn() + " / 逐 BlockPos 完全一致：" + (diff.exact() ? "是" : "否");
    }

    /** 报告用：坐标样本（最多 n 个）。 */
    static String sample(Set<BlockPos> positions, int limit) {
        if (positions.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= limit) {
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
