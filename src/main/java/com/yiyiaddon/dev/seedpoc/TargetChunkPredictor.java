package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * 种子挖矿 PoC 第六轮 · 目标区块预测器（<b>纯 Seed</b>）。
 *
 * <p><b>它回答什么</b>：给定「一个已经打开的 {@link OfflinePredictionSession}（只含 种子 / 维度 /
 * 原版 worldgen 配置）+ 目标 ChunkPos」，目标区块里的<b>最终自然钻石 BlockPos 集合</b>是什么。</p>
 *
 * <p><b>算法（与用户口径第三、八节逐条对应）</b>：</p>
 * <ol>
 *     <li>按原版规则推导可能写进目标区块的 viewer 集合（{@link TargetViewerSet}，半径来自
 *         {@code ChunkPyramid} 的 {@code blockStateWriteRadius}，不是写死的 3×3）；</li>
 *     <li>在共享离线世界里把 {@code target ± 写半径} 所需的前置阶段铺好（{@link OfflinePredictionSession#prepareFor}）；</li>
 *     <li>逐个 viewer 跑它自己的 {@code FEATURES}，<b>每个 viewer 前后各读一次目标区块</b>，
 *         差集就是「这个 viewer 这一遍写进目标区块的钻石」（写入归属的集合侧证据）；
 *         跨区块写入因此天然包含在内 —— 它是原版 {@code ensureCanWrite}（写半径 1）允许的正常写入；</li>
 *     <li>全部 viewer 跑完后，只读<b>目标区块</b>，给出最终钻石集合。</li>
 * </ol>
 *
 * <p><b>本类参数里没有 ServerLevel / ChunkAccess / 真实矿石坐标 / 真实装饰批号</b>：
 * 预测侧只吃「会话（= 种子 + 维度）+ 目标区块」，真实世界由
 * {@link FinalOreTruthComparator} 事后比较。</p>
 *
 * <p>另外提供「只装饰目标区块」这一种<b>旧口径复算</b>（{@link #predictSingleViewer}）：
 * 它与第四轮离线口径同义，用来在本次运行里现场重建「第四轮漏了哪几格」，
 * 而不是引用上一轮的数字。</p>
 */
final class TargetChunkPredictor {

    private TargetChunkPredictor() {
    }

    /** 第六轮模型：目标 ± 写半径的 viewer 集合并跑各自 FEATURES，只读目标区块。 */
    static PredictionOutcome predictAroundTarget(OfflinePredictionSession session, ChunkPos target) {
        return predict(session, target, true);
    }

    /** 旧口径复算：只装饰目标区块自己（= 第四轮离线模型），只读目标区块。 */
    static PredictionOutcome predictSingleViewer(OfflinePredictionSession session, ChunkPos target) {
        return predict(session, target, false);
    }

    /**
     * 一次目标区块预测。
     *
     * @param session      共享离线会话（= 种子 + 维度）
     * @param target       目标区块
     * @param aroundTarget true = 第六轮模型（目标 ± 写半径）；false = 旧口径（只目标自己）
     */
    static PredictionOutcome predict(OfflinePredictionSession session, ChunkPos target, boolean aroundTarget) {
        int writeRadius = TargetViewerSet.writeRadius();
        int extraRadius = aroundTarget ? writeRadius : 0;
        List<ChunkPos> viewers = aroundTarget ? TargetViewerSet.viewers(target) : List.of(target);
        String model = aroundTarget
                ? "第六轮模型｜目标 ±" + writeRadius + " viewer（" + viewers.size() + " 遍 FEATURES）"
                : "第四轮口径复算｜只装饰目标区块自己（1 遍 FEATURES）";

        OfflineChunkPipeline pipeline = session.pipeline();
        long stageRunsBefore = pipeline.stageRuns();
        long stageSkippedBefore = pipeline.stagesSkipped();
        int chunksBefore = session.chunkCount();

        long startedAt = System.currentTimeMillis();
        session.prepareFor(target, extraRadius);
        List<PredictionOutcome.ViewerContribution> contributions = new ArrayList<>();
        OfflineOreAttribution.begin(session.sessionId());
        Set<BlockPos> predicted;
        try {
            for (ChunkPos viewer : viewers) {
                Set<BlockPos> before = diamondsOf(session.latestChunk(target));
                session.decorate(viewer);
                Set<BlockPos> after = diamondsOf(session.latestChunk(target));
                Set<BlockPos> added = new LinkedHashSet<>(after);
                added.removeAll(before);
                contributions.add(new PredictionOutcome.ViewerContribution(viewer, Set.copyOf(added)));
            }
            ChunkAccess finalChunk = session.finalChunk(target);
            predicted = diamondsOf(finalChunk);
        } finally {
            OfflineOreAttribution.end();
        }
        long elapsed = System.currentTimeMillis() - startedAt;

        // 归属台账：会话内累积，读数时只取落在目标区块内的那些（跨区块写入因此天然包含在内）
        Map<BlockPos, OfflineOreAttribution.Write> provenance =
                OfflineOreAttribution.writesIn(session.sessionId(), target);
        StringBuilder note = new StringBuilder();
        note.append("viewer 集合由 ChunkPyramid 现算（写半径 ").append(writeRadius).append("）");
        Set<ChunkPos> crossChunkViewers = new LinkedHashSet<>();
        for (OfflineOreAttribution.Write write : provenance.values()) {
            if (write.crossChunk(target)) {
                crossChunkViewers.add(write.viewer());
            }
        }
        note.append("；预测集里由邻 viewer 跨区块写入的坐标 ").append(crossChunkCount(provenance, target))
                .append(" 个（来自 ").append(crossChunkViewers.size()).append(" 个 viewer）");
        int unattributed = countUnattributed(predicted, provenance);
        if (unattributed > 0) {
            note.append("；预测集里 ").append(unattributed)
                    .append(" 个坐标没有 OreFeature 接受记录（来自化石/结构等其它来源，见报告单列）");
        }

        return new PredictionOutcome(model, target, extraRadius, viewers, Set.copyOf(predicted), provenance,
                List.copyOf(contributions), null, null, elapsed, session.chunkCount() - chunksBefore,
                pipeline.stageRuns() - stageRunsBefore, pipeline.stagesSkipped() - stageSkippedBefore,
                viewers.size(), note.toString(), Map.of(), "");
    }

    /** 预测集里「没有任何 OreFeature 接受记录」的坐标数（其它钻石来源的规模）。 */
    static int countUnattributed(Set<BlockPos> predicted,
                                 Map<BlockPos, OfflineOreAttribution.Write> provenance) {
        int count = 0;
        for (BlockPos pos : predicted) {
            if (!provenance.containsKey(pos)) {
                count++;
            }
        }
        return count;
    }

    /** 归属台账里「相对目标区块是跨区块写入」的坐标数。 */
    static int crossChunkCount(Map<BlockPos, OfflineOreAttribution.Write> provenance, ChunkPos target) {
        int count = 0;
        for (OfflineOreAttribution.Write write : provenance.values()) {
            if (write.crossChunk(target)) {
                count++;
            }
        }
        return count;
    }

    /** 读一个离线区块的最终钻石（区块缺失时返回空集，调用方按「缺失」如实登记）。 */
    private static Set<BlockPos> diamondsOf(ChunkAccess chunk) {
        return FinalOreCollector.collect(chunk).all();
    }
}
