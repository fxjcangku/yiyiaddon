package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 种子挖矿 PoC 第六轮 · 真值比较器（<b>只存在于测试侧</b>）。
 *
 * <p>它是本轮唯一读「真实世界最终状态」的地方，职责只有两件：</p>
 * <ol>
 *     <li>取真值：把目标区块与它 ±1 邻域都推到 FULL（原版 {@code ServerLevel#getChunk} 阻塞式生成），
 *         然后只读目标区块里的 {@code diamond_ore} / {@code deepslate_diamond_ore}
 *         （读取代码与离线侧完全共用 {@link FinalOreCollector}）；</li>
 *     <li>比较：把真值与预测做逐 BlockPos 差集，补进 {@link PredictionOutcome}。</li>
 * </ol>
 *
 * <p><b>为什么真值必须先把 ±1 邻域也推到 FULL</b>：{@code FEATURES} 的写半径是 1，
 * 邻区块装饰时会把方块写进目标区块。若只生成目标区块自己，邻域可能还没装饰，
 * 真值就会「少算」那些跨区块写入 —— 这正是第四轮口径二漏 11 格的成因之一。</p>
 *
 * <p>本类<b>不参与预测</b>：它的输出只进报告，不影响任何预测算法。</p>
 */
final class FinalOreTruthComparator {

    private FinalOreTruthComparator() {
    }

    /**
     * 真实世界最终钻石真值。
     *
     * @param level  测试世界（单人世界的集成服务端世界）
     * @param target 目标区块
     */
    static FinalOreCollector.OreSet truth(ServerLevel level, ChunkPos target) {
        int radius = TargetViewerSet.writeRadius();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                // 阻塞式生成到 FULL：保证目标区块的邻域也装饰完毕，跨区块写入已经落定
                level.getChunk(target.x() + dx, target.z() + dz);
            }
        }
        return FinalOreCollector.collect(level.getChunk(target.x(), target.z()));
    }

    /** 取真值并与预测比较，返回补上真值的结果（原结果一字不动）。 */
    static PredictionOutcome attach(ServerLevel level, PredictionOutcome outcome) {
        FinalOreCollector.OreSet truth = truth(level, outcome.target());
        BlockPosDiff diff = BlockPosDiff.of(truth.all(), outcome.predicted());
        return outcome.withTruth(truth, diff, null);
    }

    /**
     * 取真值 → 比较 → 不一致时做 FIRST DIVERGENCE 取证（用户口径第十二节）。
     *
     * <p>取证分两层，用来把「输入状态不同」与「钻石链本身不同」分开：</p>
     * <ol>
     *     <li><b>逐格状态比对</b>：把<b>离线目标区块</b>与<b>真实目标区块</b>在 y ∈ 钻石扫描范围内逐格比，
     *         给出「不同的格数」「其中两边都非钻石（= 地形/其它地物差异）的格数」与首几条样本。
     *         不同的格数为 0 ⇒ 输入状态完全相同，问题只能在钻石链；不为 0 ⇒ 离线世界并没有复现出
     *         真实世界的方块状态，第一处分叉在生成阶段（不是装饰算法）；</li>
     *     <li><b>漏报/错报坐标两侧对照</b>：每个 FN / FP 坐标在离线侧与真实侧各是什么方块。</li>
     * </ol>
     *
     * @param offlineChunk 离线目标区块的最终状态（{@code FEATURES} 之后）；null = 未能取得
     */
    static PredictionOutcome attach(ServerLevel level, PredictionOutcome outcome, ChunkAccess offlineChunk) {
        PredictionOutcome compared = attach(level, outcome);
        if (compared.exact()) {
            return compared;
        }
        Map<BlockPos, String> states = new LinkedHashMap<>();
        Set<BlockPos> interesting = new LinkedHashSet<>(compared.missed());
        interesting.addAll(compared.extra());
        LevelChunk real = level.getChunk(compared.target().x(), compared.target().z());
        int taken = 0;
        for (BlockPos pos : interesting) {
            if (taken++ >= 16) {
                break;
            }
            states.put(pos, "离线=" + idAt(offlineChunk, pos) + " / 真值=" + idAt(real, pos));
        }
        return compared.withDivergence(states,
                describeStateDiff(offlineChunk, real, compared.target()) + describeMissed(offlineChunk, compared));
    }

    /**
     * 漏报坐标在<b>离线侧</b>的方块与可替换性。
     *
     * <p>这是「随机流漂移」这一机制的直接证据：若漏报点在离线世界是<b>不可替换</b>方块（例如凝灰岩），
     * 该候选点就不会通过 {@code OreFeature#canPlaceOre} 的 target 判定，于是<b>少消耗一次随机</b>，
     * 同一条矿脉后续候选点的随机值整体错位，正好表现为「漏若干格、且不产生多余格」。</p>
     */
    private static String describeMissed(ChunkAccess offline, PredictionOutcome outcome) {
        if (offline == null || outcome.missed().isEmpty()) {
            return "";
        }
        int replaceable = 0;
        int notReplaceable = 0;
        StringBuilder samples = new StringBuilder();
        for (BlockPos pos : outcome.missed()) {
            BlockState state = offline.getBlockState(pos);
            if (OreBlockLedger.isReplaceable(state)) {
                replaceable++;
            } else {
                notReplaceable++;
                if (samples.length() < 200) {
                    samples.append(pos).append("离线=").append(OreBlockLedger.shortId(state)).append(' ');
                }
            }
        }
        return "；漏报坐标在离线侧的方块：可替换 " + replaceable + " 格 / 不可替换 " + notReplaceable + " 格"
                + (notReplaceable == 0 ? "" : "；不可替换样本：" + samples);
    }

    /**
     * 逐格比对离线目标区块与真实目标区块（只比 y ∈ 钻石扫描范围），返回中文摘要。
     *
     * <p>两侧都按「section 内取方块状态」读（{@code null} section 记作空气），
     * 保证读法与 {@link FinalOreCollector} 一致。</p>
     */
    private static String describeStateDiff(ChunkAccess offline, LevelChunk real, ChunkPos target) {
        if (offline == null || real == null) {
            return "逐格状态比对：区块缺失，无法比对（离线 " + (offline == null ? "缺失" : "有")
                    + " / 真实 " + (real == null ? "缺失" : "有") + "）";
        }
        int sections = Math.min(SeedPocConstants.DIAMOND_SCAN_SECTION_COUNT, offline.getSectionsCount());
        int compared = 0;
        int different = 0;
        int terrainDifferent = 0;
        int offlineOnlyDiamond = 0;
        int truthOnlyDiamond = 0;
        int bothReplaceable = 0;
        int bothNotReplaceable = 0;
        int truthReplaceableOfflineNot = 0;
        int offlineReplaceableTruthNot = 0;
        Map<String, Integer> pairs = new LinkedHashMap<>();
        StringBuilder samples = new StringBuilder();
        for (int section = 0; section < sections; section++) {
            LevelChunkSection offlineSection = offline.getSection(section);
            LevelChunkSection realSection = real.getSection(section);
            int baseY = (offline.getMinSectionY() + section) * 16;
            for (int localY = 0; localY < 16; localY++) {
                for (int localZ = 0; localZ < 16; localZ++) {
                    for (int localX = 0; localX < 16; localX++) {
                        compared++;
                        BlockState offlineState = stateOf(offlineSection, localX, localY, localZ);
                        BlockState realState = stateOf(realSection, localX, localY, localZ);
                        if (offlineState.equals(realState)) {
                            continue;
                        }
                        different++;
                        boolean offlineDiamond = OreBlockLedger.isDiamond(offlineState);
                        boolean truthDiamond = OreBlockLedger.isDiamond(realState);
                        if (offlineDiamond && truthDiamond) {
                            continue;
                        }
                        if (offlineDiamond) {
                            offlineOnlyDiamond++;
                        } else if (truthDiamond) {
                            truthOnlyDiamond++;
                        } else {
                            terrainDifferent++;
                            // 关键分类：OreFeature#canPlaceOre 先做 target 标签判定，通过后才调 shouldSkipAirCheck
                            // 消耗一次随机。因此「一侧可替换、另一侧不可替换」的格子才会改变随机流消耗次数，
                            // 从而让同一 feature 后续矿脉整体漂移；两边都同类的格子不影响随机流。
                            boolean offlineReplaceable = OreBlockLedger.isReplaceable(offlineState);
                            boolean truthReplaceable = OreBlockLedger.isReplaceable(realState);
                            if (offlineReplaceable && truthReplaceable) {
                                bothReplaceable++;
                            } else if (!offlineReplaceable && !truthReplaceable) {
                                bothNotReplaceable++;
                            } else if (truthReplaceable) {
                                truthReplaceableOfflineNot++;
                            } else {
                                offlineReplaceableTruthNot++;
                            }
                            pairs.merge(OreBlockLedger.shortId(realState) + "→" + OreBlockLedger.shortId(offlineState),
                                    1, Integer::sum);
                        }
                        if (samples.length() < 300) {
                            samples.append("(").append((target.x() << 4) + localX).append(',')
                                    .append(baseY + localY).append(',').append((target.z() << 4) + localZ)
                                    .append(")离线=").append(OreBlockLedger.shortId(offlineState))
                                    .append("/真值=").append(OreBlockLedger.shortId(realState)).append(' ');
                        }
                    }
                }
            }
        }
        return "逐格状态比对（y ∈ [" + offline.getMinY() + ","
                + (offline.getMinY() + sections * 16 - 1) + "] 共 " + compared + " 格）：与真值不同 " + different
                + " 格；其中" + (different == 0 ? "无差异" : "「离线有钻石、真值没有」" + offlineOnlyDiamond
                + " 格、「真值有钻石、离线没有」" + truthOnlyDiamond + " 格、「两边都非钻石」" + terrainDifferent
                + " 格；前若干样本：" + samples)
                + (different == 0 ? "" : "（两边都非钻石的那部分 = 地形/其它地物差异，说明离线世界未复现真实方块状态）")
                + (terrainDifferent == 0 ? "" : "；地形差异的可替换性分类（保守判据：按最终状态判定，"
                + "两侧都可替换 " + bothReplaceable + " 格（不改变随机流消耗）/ 两侧都不可替换 " + bothNotReplaceable
                + " 格（不改变随机流消耗）/ 真值可替换·离线不可替换 " + truthReplaceableOfflineNot
                + " 格（离线少消耗随机 → 后续漂移）/ 真值不可替换·离线可替换 " + offlineReplaceableTruthNot
                + " 格（离线多消耗随机 → 后续漂移）⇒ 能改变随机流的格子合计 "
                + (truthReplaceableOfflineNot + offlineReplaceableTruthNot) + " 格")
                + (pairs.isEmpty() ? "" : "；差异成对（真值→离线）共 " + pairs.size() + " 种，最多的 "
                + topPairs(pairs, 6));
    }

    /** 取差异成对里的前 n 个（按格数降序，便于报告一眼看出是哪几种方块在换位）。 */
    private static String topPairs(Map<String, Integer> pairs, int limit) {
        List<Map.Entry<String, Integer>> entries = new java.util.ArrayList<>(pairs.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < Math.min(limit, entries.size()); i++) {
            if (i > 0) {
                text.append("；");
            }
            text.append(entries.get(i).getKey()).append(' ').append(entries.get(i).getValue()).append(" 格");
        }
        return text.toString();
    }

    /** 取某一格状态（section 为 null 记作空气）。 */
    private static BlockState stateOf(LevelChunkSection section, int localX, int localY, int localZ) {
        return section == null ? Blocks.AIR.defaultBlockState()
                : section.getBlockState(localX, localY, localZ);
    }

    /** 某个坐标在给定区块里的方块短 id（区块为 null 或越界时如实标注）。 */
    private static String idAt(ChunkAccess chunk, BlockPos pos) {
        if (chunk == null) {
            return "（区块缺失）";
        }
        if (pos.getY() < chunk.getMinY() || pos.getY() >= chunk.getMinY() + chunk.getSectionsCount() * 16) {
            return "（纵向越界）";
        }
        return OreBlockLedger.shortId(chunk.getBlockState(pos));
    }

    /**
     * 目标区块中心那一格（y = 地表取样点）的真实生物群系路径。
     *
     * <p>只用于报告里说明「这一批目标覆盖了哪些地形」，不参与预测（预测侧不读真实生物群系）。</p>
     */
    static String surfaceBiome(ServerLevel level, ChunkPos target) {
        BlockPos pos = new BlockPos((target.x() << 4) + 8, 64, (target.z() << 4) + 8);
        Holder<Biome> biome = level.getBiome(pos);
        return biome.unwrapKey().map(key -> key.identifier().getPath()).orElse("?");
    }
}
