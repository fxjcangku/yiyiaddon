package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 候选面取证：记录「某个 placed_feature 在放置时到底考虑过哪些方块位置、当时那一格是什么方块」。
 *
 * <p><b>要回答的问题不是「预测对不对」，而是「差异卡在哪一层」</b>。原版
 * {@code OreFeature#doPlace} 在写方块之前，会对每个候选位置先调用
 * {@code WorldGenLevel#ensureCanWrite}；把每个 feature 的这串候选位置记下来，
 * 逐 BlockPos 差异就能被劈成两半：</p>
 * <ul>
 *     <li><b>几何/RNG 层</b>——真值矿位压根不在候选面里。说明「矿脉中心 + 采样循环」与原版不同；</li>
 *     <li><b>接受层</b>——真值矿位在候选面里却没被写出来。说明目标方块判定 / 贴空气判定 / 写半径不同。</li>
 * </ul>
 *
 * <p><b>必须连同「当时那一格的方块」一起记</b>，理由是
 * {@code OreFeature#canPlaceOre}：只有目标方块通过可替换标签判定后，才会调用
 * {@code shouldSkipAirCheck}，而后者在 {@code 0 < discard_chance_on_air_exposure < 1} 时消耗一次
 * {@code nextFloat()}。也就是说<b>候选点的方块状态会改变该 feature 在本遍的随机数消耗次数</b>，
 * 从而影响它在本遍后续所有矿脉的位置。事后到世界上回读方块是不够的——
 * 那时候世界已经被本遍更早的地物改过了；必须是「判定那一刻」的状态。</p>
 *
 * <p>另外顺带记录放置期 {@code getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z)} 的返回值范围：
 * {@code OreFeature#place} 开头那道「yStart ≤ 生成期高度」的闸门若与原版不同，
 * 整个 feature 会直接 {@code return false}，候选面为空，这是另一类可证伪的失败。</p>
 */
public final class SeedPocTrace {

    /**
     * 一个候选点。
     *
     * @param pos         坐标
     * @param stateId     判定那一刻该位置的方块短 id
     * @param replaceable 判定那一刻是否属于矿物可替换标签
     *                    （决定了这次判定会不会消耗 {@code nextFloat()}）
     */
    public record Candidate(BlockPos pos, String stateId, boolean replaceable) {
    }

    /** feature 标签 → 候选点（按判定顺序）。 */
    private final Map<String, List<Candidate>> candidates = new LinkedHashMap<>();

    /** feature 标签 → {最小值, 最大值, 次数}，对应放置期的高度查询。 */
    private final Map<String, int[]> heights = new LinkedHashMap<>();

    /** 当前正在重放的装饰遍序号（中心 3x3 里的第几个），用于区分同一个 feature 的不同遍。 */
    private int passIndex;

    /** 当前正在放置的 feature 标签；null 表示不在放置中。 */
    private String tag;

    /** 某一遍装饰的标签前缀。 */
    public static String passPrefix(int passIndex) {
        return "遍" + passIndex + "|";
    }

    /** 切换当前重放遍序号。 */
    public void setPassIndex(int passIndex) {
        this.passIndex = passIndex;
    }

    /** 当前是否真的在记录（不在某个 feature 的放置过程中就没有记录价值）。 */
    public boolean isRecording() {
        return tag != null;
    }

    /** 进入某个 feature 的放置过程。 */
    public void begin(String featureTag) {
        this.tag = passPrefix(passIndex) + featureTag;
    }

    /** 离开该 feature 的放置过程。 */
    public void end() {
        this.tag = null;
    }

    /** 记录一个被 {@code ensureCanWrite} 询问过的候选点及其判定时刻的方块状态。 */
    public void noteCandidate(BlockPos pos, BlockState state) {
        if (tag == null) {
            return;
        }
        // 传入的是 OreFeature 复用的 MutableBlockPos，必须取不可变副本
        candidates.computeIfAbsent(tag, key -> new ArrayList<>())
                .add(new Candidate(pos.immutable(), OreBlockLedger.shortId(state),
                        OreBlockLedger.isReplaceable(state)));
    }

    /** 记录一次放置期的高度查询返回值。 */
    public void noteHeight(int value) {
        if (tag == null) {
            return;
        }
        int[] stats = heights.computeIfAbsent(tag, key -> new int[]{value, value, 0});
        stats[0] = Math.min(stats[0], value);
        stats[1] = Math.max(stats[1], value);
        stats[2]++;
    }

    /** 标签 → 候选点序列。 */
    public Map<String, List<Candidate>> candidates() {
        return candidates;
    }

    /** 标签 → {最小高度, 最大高度, 查询次数}。 */
    public Map<String, int[]> heights() {
        return heights;
    }

    /** 所有标签的候选点并集（最宽的候选面，用于「几何层」判定）。 */
    public Set<BlockPos> candidateUnion() {
        Set<BlockPos> union = new LinkedHashSet<>();
        for (List<Candidate> list : candidates.values()) {
            for (Candidate candidate : list) {
                union.add(candidate.pos());
            }
        }
        return union;
    }

    /** 某个标签下的去重位置集合。 */
    public static Set<BlockPos> distinctPositions(List<Candidate> list) {
        Set<BlockPos> positions = new LinkedHashSet<>();
        for (Candidate candidate : list) {
            positions.add(candidate.pos());
        }
        return positions;
    }

    /** 每个位置在判定时刻看到的第一个状态（同一位置在不同遍可能被改过多次）。 */
    public Map<BlockPos, String> firstStateByPosition() {
        Map<BlockPos, String> states = new LinkedHashMap<>();
        for (List<Candidate> list : candidates.values()) {
            for (Candidate candidate : list) {
                states.putIfAbsent(candidate.pos(), candidate.stateId());
            }
        }
        return states;
    }
}
