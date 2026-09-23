package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 候选面差异定层：把「真值 vs 预测」的逐 BlockPos 差异劈成两层，并给出可证伪的数字。
 *
 * <p>判定规则（依据原版 {@code OreFeature#place/doPlace} 的调用顺序）：</p>
 * <ul>
 *     <li>真值矿位<b>不在</b>候选面里 → 差异出在<b>几何/RNG 层</b>：
 *         矿脉中心（{@code PlacementModifier} 采样）或采样循环与原版不同，
 *         要回去查 decoration seed / feature seed / 全局索引 / 采样原点；</li>
 *     <li>真值矿位<b>在</b>候选面里却未被写出 → 差异出在<b>接受层</b>：
 *         目标方块标签判定、贴空气判定（{@code discard_chance_on_air_exposure}）
 *         或写半径判据与原版不同。</li>
 * </ul>
 *
 * <p>候选面是「9 遍装饰累计被考虑过的位置」，只用于定层，不能当作预测结果本身。</p>
 */
public final class CandidateDiff {

    /** 每类样本最多列几个。 */
    private static final int MAX_SAMPLES = 5;

    /**
     * 「只可能出现在矿物步骤之后」的方块短 id。
     *
     * <p>依据 26.1.2 {@code GenerationStep.Decoration} 的步骤顺序：矿物是
     * {@code UNDERGROUND_ORES}，其后还有 {@code UNDERGROUND_DECORATION}（紫水晶洞等）、
     * {@code FLUID_SPRINGS}、{@code VEGETAL_DECORATION}（苔藓、发光地衣、洞穴藤蔓等）、
     * {@code TOP_LAYER_MODIFICATION}（雪冰）。落在这些步骤里的方块，
     * 在矿物判定那一刻<b>还不存在</b>，那个位置当时是石头/深板岩类方块。</p>
     */
    private static final Set<String> LATE_DECORATION_BLOCKS = Set.of(
            "amethyst_block", "budding_amethyst", "amethyst_cluster", "large_amethyst_bud",
            "medium_amethyst_bud", "small_amethyst_bud", "calcite", "smooth_basalt",
            "moss_block", "moss_carpet", "azalea", "flowering_azalea", "azalea_leaves",
            "flowering_azalea_leaves", "hanging_roots", "big_dripleaf", "small_dripleaf",
            "spore_blossom", "cave_vines", "cave_vines_plant", "glow_lichen",
            "sculk", "sculk_vein", "sculk_catalyst", "sculk_shrieker", "sculk_sensor",
            "calibrated_sculk_sensor", "snow", "ice", "powder_snow", "packed_ice", "blue_ice");

    private CandidateDiff() {
    }

    /**
     * 生成写入报告的中文明细行。
     *
     * @param level     真实世界（取样本点当前方块状态用）
     * @param center    被比对的目标区块
     * @param real      真值钻石矿位（目标区块内）
     * @param predicted 复刻得到的预测矿位
     * @param trace     候选面取证
     */
    public static List<String> render(ServerLevel level, ChunkPos center, Set<BlockPos> real,
                                      Set<BlockPos> predicted, SeedPocTrace trace) {
        List<String> lines = new ArrayList<>();
        Set<BlockPos> union = trace.candidateUnion();
        int inChunk = 0;
        for (BlockPos pos : union) {
            if ((pos.getX() >> 4) == center.x() && (pos.getZ() >> 4) == center.z()) {
                inChunk++;
            }
        }
        lines.add("候选面规模：9 遍装饰累计被考虑过的位置 " + union.size()
                + " 个，其中落在本区块 " + inChunk + " 个");

        Set<BlockPos> considered = new LinkedHashSet<>(real);
        considered.retainAll(union);
        Set<BlockPos> unconsidered = new LinkedHashSet<>(real);
        unconsidered.removeAll(union);
        Set<BlockPos> consideredButMissing = new LinkedHashSet<>(considered);
        consideredButMissing.removeAll(predicted);

        lines.add("定层判定：真值 " + real.size() + " 个 → 候选面覆盖 " + considered.size()
                + " 个；从未被考虑（几何/RNG 层）" + unconsidered.size()
                + " 个；被考虑但未写入（接受层）" + consideredButMissing.size() + " 个");
        lines.add("从未被考虑样本：" + sample(level, unconsidered));
        lines.add("被考虑但未写入样本：" + sample(level, consideredButMissing));
        lines.addAll(lateBlockScan(trace));
        lines.addAll(featureSummary(center, real, predicted, trace));
        return lines;
    }

    /**
     * 后期装饰方块扫描：候选面里有多少位置，在<b>判定那一刻</b>带着「只可能由矿物步骤之后的装饰步骤
     * 放上去的方块」。
     *
     * <p>这些位置就是<b>生成期方块状态与当前方块状态必然不一致</b>的点：矿物判定时那里还是石头类方块
     * （可替换、会消耗 {@code nextFloat()}），而现在已经被后期地物换掉。
     * 依据 26.1.2 步骤顺序，矿物是 {@code UNDERGROUND_ORES}，其后还有
     * {@code UNDERGROUND_DECORATION}（紫水晶洞等）、{@code FLUID_SPRINGS}、
     * {@code VEGETAL_DECORATION}（苔藓、发光地衣、洞穴藤蔓等）与 {@code TOP_LAYER_MODIFICATION}（雪冰）。</p>
     */
    private static List<String> lateBlockScan(SeedPocTrace trace) {
        Map<String, Integer> counts = new TreeMap<>();
        for (Map.Entry<BlockPos, String> entry : trace.firstStateByPosition().entrySet()) {
            if (LATE_DECORATION_BLOCKS.contains(entry.getValue())) {
                counts.merge(entry.getValue(), 1, Integer::sum);
            }
        }
        List<String> lines = new ArrayList<>();
        if (counts.isEmpty()) {
            lines.add("后期装饰方块扫描：候选面里没有「只可能出现在矿物步骤之后」的方块");
            return lines;
        }
        StringBuilder builder = new StringBuilder();
        int total = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (builder.length() > 0) {
                builder.append('、');
            }
            builder.append(entry.getKey()).append('×').append(entry.getValue());
            total += entry.getValue();
        }
        lines.add("后期装饰方块扫描：" + total + " 个候选位置在判定时刻带着「矿物步骤之后才放上去」的方块（"
                + builder + "）——这些点与真实生成期状态必然不一致");
        return lines;
    }

    /** 按 placed_feature 路径汇总候选面规模与高度闸门情况。 */
    private static List<String> featureSummary(ChunkPos center, Set<BlockPos> real, Set<BlockPos> predicted,
                                               SeedPocTrace trace) {
        // 路径 → {累计候选次数, 高度查询次数, 落本区块候选数, 该路径候选中最终出现在预测里的个数,
        //         高度最小值, 高度最大值}
        Map<String, int[]> byPath = new TreeMap<>();
        Map<String, Set<BlockPos>> candidatesByPath = new TreeMap<>();
        for (Map.Entry<String, List<SeedPocTrace.Candidate>> entry : trace.candidates().entrySet()) {
            String path = pathOf(entry.getKey());
            int[] aggregate = byPath.computeIfAbsent(path,
                    key -> new int[]{0, 0, 0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE});
            Set<BlockPos> merged = candidatesByPath.computeIfAbsent(path, key -> new LinkedHashSet<>());
            Set<BlockPos> distinct = SeedPocTrace.distinctPositions(entry.getValue());
            for (BlockPos pos : distinct) {
                aggregate[0]++;
                if ((pos.getX() >> 4) == center.x() && (pos.getZ() >> 4) == center.z()) {
                    aggregate[2]++;
                }
                if (predicted.contains(pos)) {
                    aggregate[3]++;
                }
            }
            merged.addAll(distinct);
            int[] heights = trace.heights().get(entry.getKey());
            if (heights != null) {
                aggregate[1] += heights[2];
                aggregate[4] = Math.min(aggregate[4], heights[0]);
                aggregate[5] = Math.max(aggregate[5], heights[1]);
            }
        }

        List<String> lines = new ArrayList<>();
        int shown = 0;
        int hidden = 0;
        for (Map.Entry<String, int[]> entry : byPath.entrySet()) {
            String path = entry.getKey();
            int[] aggregate = entry.getValue();
            boolean relevant = SeedPocConstants.DIAMOND_PLACED_FEATURES.contains(path)
                    || path.contains("diamond")
                    || aggregate[2] > 0
                    || !java.util.Collections.disjoint(candidatesByPath.get(path), real);
            if (!relevant || shown >= 12) {
                hidden++;
                continue;
            }
            shown++;
            String heightRange = aggregate[1] == 0
                    ? "—"
                    : aggregate[4] + "…" + aggregate[5] + "（" + aggregate[1] + " 次）";
            lines.add("    " + path + "：累计候选 " + aggregate[0] + " 次 / 放置期高度 " + heightRange
                    + " / 落本区块候选 " + aggregate[2] + " 个 / 最终出现在预测集里 " + aggregate[3] + " 个");
        }
        if (hidden > 0) {
            lines.add("    （另有 " + hidden + " 个 feature 与本区块真值/预测无关，已省略）");
        }
        if (lines.isEmpty()) {
            lines.add("    无：本区块 9 遍装饰没有任何 feature 产生过候选位置");
        }
        return lines;
    }

    /** 从「步骤:全局索引:路径」标签里取出路径。 */
    private static String pathOf(String tag) {
        int split = tag.lastIndexOf(':');
        return split < 0 ? tag : tag.substring(split + 1);
    }

    /** 样本点：坐标 + 是否贴空气 + 六邻居方块 id（判定接受层差异的直接现场）。 */
    private static String sample(ServerLevel level, Set<BlockPos> positions) {
        if (positions.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= MAX_SAMPLES) {
                builder.append(" …");
                break;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',').append(pos.getZ())
                    .append(") 贴空气=").append(adjacentToAir(level, pos) ? "是" : "否")
                    .append(" 六邻=").append(neighbours(level, pos));
        }
        return builder.toString();
    }

    /** 六个方向里是否存在空气（原版 {@code OreFeature#isAdjacentToAir} 的判据）。 */
    private static boolean adjacentToAir(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).isAir()) {
                return true;
            }
        }
        return false;
    }

    /** 六邻居方块 id 串（短名，去掉命名空间）。 */
    private static String neighbours(ServerLevel level, BlockPos pos) {
        StringBuilder builder = new StringBuilder();
        for (Direction direction : Direction.values()) {
            if (builder.length() > 0) {
                builder.append(',');
            }
            builder.append(OreBlockLedger.shortId(level.getBlockState(pos.relative(direction))));
        }
        return builder.toString();
    }
}
