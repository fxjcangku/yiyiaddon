package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 状态扰动实验：证明「候选点的方块状态是否进入随机数流」这一层。
 *
 * <p><b>为什么要做这个实验</b>：前面几轮已经把差异定到<b>几何/RNG 层</b>——
 * 种子派生、feature 全局索引、生物群系都能逐项对上（索引表交叉核对一致、生物群系 100%），
 * 但仍有整簇矿脉落在候选面之外。而按 26.1.2 源码逐行推，矿脉中心只由随机数决定，
 * 随机数又只由 {@code setDecorationSeed/setFeatureSeed} 决定，除非<b>随机数消耗次数</b>
 * 被世界状态改变。</p>
 *
 * <p><b>源码依据</b>（{@code OreFeature#doPlace} → {@code #canPlaceOre}）：</p>
 * <pre>
 * 若 (!targetState.target.test(orePosState, random)) return false;        // 标签不匹配：不消耗随机数
 * 否则 return shouldSkipAirCheck(random, discardChanceOnAirExposure) ...  // 0&lt;discard&lt;1 时消耗一次 nextFloat()
 * </pre>
 *
 * <p><b>两种做法</b>：单点扰动（只改一个候选点，看是否造成漂移）与全量扰动
 * （把该遍钻石四条 feature 的全部可替换候选点一起改掉）。两者都通过对比「候选点判定序列」
 * 直接判断随机数流有没有漂移——这比只看最终预测集合更能定位问题。</p>
 */
public final class CandidatePerturbation {

    /** 扰动探针方块：不属任何矿物替换标签，生成期矿物判定时不可能存在。 */
    private static final BlockState PROBE = Blocks.AMETHYST_BLOCK.defaultBlockState();

    /** 挑中的单点扰动目标。 */
    public record Target(BlockPos pos, String tag, String testStateId, BlockState originalState) {
    }

    private CandidatePerturbation() {
    }

    /** 扰动探针方块（由调用方写入，写完必须还原）。 */
    public static BlockState probe() {
        return PROBE;
    }

    /**
     * 挑选单个扰动点。
     *
     * <p>只挑<b>钻石四条 placed_feature</b> 的候选点——只有它们的随机数流里含有
     * 「可替换判定 → {@code nextFloat()}」这一环，扰动才会真的改变消耗次数。
     * 遍历顺序即判定顺序，因此拿到的是该 feature 在本遍里<b>最早被考虑的候选点</b>。</p>
     *
     * <p>约束（缺一不可）：判定时刻<b>可替换</b>、落在目标区块内、不在真值里、不在基线预测里
     * （后两条是为了避免「直接少写一个矿位」这种直接效应掩盖漂移效应）。</p>
     *
     * @return 找不到合适点时返回 null
     */
    public static Target pick(ServerLevel level, ChunkPos center, SeedPocTrace trace, int passIndex,
                              Set<BlockPos> predicted, Set<BlockPos> real) {
        String prefix = SeedPocTrace.passPrefix(passIndex);
        for (Map.Entry<String, List<SeedPocTrace.Candidate>> entry : trace.candidates().entrySet()) {
            if (!isDiamondTag(entry.getKey(), prefix)) {
                continue;
            }
            for (SeedPocTrace.Candidate candidate : entry.getValue()) {
                if (!candidate.replaceable()) {
                    continue;
                }
                BlockPos pos = candidate.pos();
                if ((pos.getX() >> 4) != center.x() || (pos.getZ() >> 4) != center.z()) {
                    continue;
                }
                if (predicted.contains(pos) || real.contains(pos)) {
                    continue;
                }
                // 还原用的状态要从「已还原的世界」上取，与判定时刻的状态可能不同（例如判定时该格被本遍清成了石头）
                return new Target(pos, entry.getKey(), candidate.stateId(), level.getBlockState(pos));
            }
        }
        return null;
    }

    /**
     * 收集某遍里钻石四条 placed_feature 全部「判定时刻可替换」的候选点，用于全量扰动。
     *
     * <p>单点扰动只改一次 {@code nextFloat()} 消耗；若这个点恰好是该 feature 在本遍最后一次消耗，
     * 表面上就看不出漂移。全量扰动让消耗次数大幅变化，是判定性的做法。</p>
     */
    public static List<BlockPos> collectReplaceableCandidates(SeedPocTrace trace, int passIndex) {
        String prefix = SeedPocTrace.passPrefix(passIndex);
        List<BlockPos> positions = new ArrayList<>();
        Set<BlockPos> seen = new LinkedHashSet<>();
        for (Map.Entry<String, List<SeedPocTrace.Candidate>> entry : trace.candidates().entrySet()) {
            if (!isDiamondTag(entry.getKey(), prefix)) {
                continue;
            }
            for (SeedPocTrace.Candidate candidate : entry.getValue()) {
                if (candidate.replaceable() && seen.add(candidate.pos())) {
                    positions.add(candidate.pos());
                }
            }
        }
        return positions;
    }

    /** 单点扰动的报告明细。 */
    public static List<String> render(Target target, Set<BlockPos> baseline, Set<BlockPos> perturbed,
                                      Set<BlockPos> real, int passIndex,
                                      SeedPocTrace baselineTrace, SeedPocTrace perturbedTrace) {
        List<String> lines = new ArrayList<>();
        if (target == null) {
            lines.add("单点扰动：本区块该遍候选面里找不到「可替换且不在真值/预测中」的点，本次未做扰动");
            return lines;
        }
        BlockPos pos = target.pos();
        lines.add("单点扰动点：(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")"
                + " 判定时刻状态=" + target.testStateId() + "（可替换） → 改成=" + OreBlockLedger.shortId(PROBE)
                + "；取自第 " + passIndex + " 遍 feature " + pathOf(target.tag()) + " 的候选面");

        Set<BlockPos> vanished = new LinkedHashSet<>(baseline);
        vanished.removeAll(perturbed);
        Set<BlockPos> appeared = new LinkedHashSet<>(perturbed);
        appeared.removeAll(baseline);
        lines.add("    扰动前：" + BlockPosDiff.of(real, baseline).cn());
        lines.add("    扰动后：" + BlockPosDiff.of(real, perturbed).cn());
        lines.add("    预测集合变化：消失 " + vanished.size() + " 个 / 新增 " + appeared.size() + " 个"
                + "；消失样本：" + sample(vanished) + "；新增样本：" + sample(appeared));
        lines.addAll(describeStream(target.tag(), passIndex, baselineTrace, perturbedTrace, "    "));
        lines.add("    扰动生效校验：" + (isRecordedAsUnreplaceable(perturbedTrace, target)
                ? "该点在扰动后仍被判定过，且判定时刻状态已变成不可替换 ⇒ 扰动确实作用到了判定路径"
                : "该点在扰动后序列中未以「不可替换」出现，扰动可能未作用到判定路径"));
        return lines;
    }

    /** 全量扰动的报告明细。 */
    public static List<String> renderMass(int probePositions, String tag, int passIndex,
                                          Set<BlockPos> baseline, Set<BlockPos> perturbed, Set<BlockPos> real,
                                          SeedPocTrace baselineTrace, SeedPocTrace perturbedTrace) {
        List<String> lines = new ArrayList<>();
        lines.add("全量扰动：第 " + passIndex + " 遍钻石四条 feature 的全部 " + probePositions
                + " 个「判定时刻可替换」候选点一起改成 " + OreBlockLedger.shortId(PROBE) + "（其余输入不动）");
        Set<BlockPos> vanished = new LinkedHashSet<>(baseline);
        vanished.removeAll(perturbed);
        Set<BlockPos> appeared = new LinkedHashSet<>(perturbed);
        appeared.removeAll(baseline);
        lines.add("    扰动前：" + BlockPosDiff.of(real, baseline).cn());
        lines.add("    扰动后：" + BlockPosDiff.of(real, perturbed).cn());
        lines.add("    预测集合变化：消失 " + vanished.size() + " 个 / 新增 " + appeared.size() + " 个");
        lines.addAll(describeStream(tag, passIndex, baselineTrace, perturbedTrace, "    "));
        return lines;
    }

    /**
     * 候选序列对比：同一遍同一 feature 的候选点判定顺序，扰动前后逐项比对。
     *
     * <p>这是判断「随机数流有没有漂移」的直接证据：若某个候选点的可替换性改变导致
     * {@code nextFloat()} 消耗次数变化，那么该 feature 在本遍后续元素的位置必然变化，
     * 候选序列会在某个下标之后整体不同。</p>
     */
    private static List<String> describeStream(String tag, int passIndex, SeedPocTrace baselineTrace,
                                               SeedPocTrace perturbedTrace, String indent) {
        List<String> lines = new ArrayList<>();
        if (baselineTrace == null || perturbedTrace == null) {
            return lines;
        }
        List<SeedPocTrace.Candidate> baseline = baselineTrace.candidates().getOrDefault(tag, List.of());
        List<SeedPocTrace.Candidate> perturbed = perturbedTrace.candidates().getOrDefault(tag, List.of());
        lines.add(indent + "候选序列对比（feature " + pathOf(tag) + "，第 " + passIndex + " 遍）：基线 "
                + baseline.size() + " 项 → 扰动后 " + perturbed.size() + " 项");

        int common = Math.min(baseline.size(), perturbed.size());
        int firstDiff = -1;
        for (int index = 0; index < common; index++) {
            if (!baseline.get(index).pos().equals(perturbed.get(index).pos())) {
                firstDiff = index;
                break;
            }
        }
        if (firstDiff < 0 && baseline.size() == perturbed.size()) {
            lines.add(indent + "    序列逐项相同 ⇒ 本次扰动没有改变该 feature 在本遍的随机数流");
        } else {
            int from = firstDiff < 0 ? common : firstDiff;
            lines.add(indent + "    序列从第 " + from + " 项起不同 ⇒ 随机数流已漂移");
            lines.add(indent + "    基线该段：" + candidatesText(baseline, from));
            lines.add(indent + "    扰动后该段：" + candidatesText(perturbed, from));
        }
        return lines;
    }

    /** 扰动点在扰动后的序列里是否以「不可替换」出现。 */
    private static boolean isRecordedAsUnreplaceable(SeedPocTrace perturbedTrace, Target target) {
        if (perturbedTrace == null) {
            return false;
        }
        for (SeedPocTrace.Candidate candidate : perturbedTrace.candidates()
                .getOrDefault(target.tag(), List.of())) {
            if (candidate.pos().equals(target.pos())) {
                return !candidate.replaceable();
            }
        }
        return false;
    }

    private static boolean isDiamondTag(String tag, String passPrefix) {
        return tag.startsWith(passPrefix) && SeedPocConstants.DIAMOND_PLACED_FEATURES.contains(pathOf(tag));
    }

    /** 从「遍前缀+步骤:索引:路径」标签里取路径。 */
    private static String pathOf(String tag) {
        int split = tag.lastIndexOf(':');
        return split < 0 ? tag : tag.substring(split + 1);
    }

    private static String candidatesText(List<SeedPocTrace.Candidate> list, int skip) {
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (int index = skip; index < list.size(); index++) {
            if (taken++ >= 4) {
                builder.append(" …");
                break;
            }
            SeedPocTrace.Candidate candidate = list.get(index);
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(candidate.pos().getX()).append(',').append(candidate.pos().getY())
                    .append(',').append(candidate.pos().getZ()).append(')')
                    .append(candidate.stateId()).append(candidate.replaceable() ? "可替换" : "不可替换");
        }
        return builder.length() == 0 ? "无" : builder.toString();
    }

    /** 最多列 6 个坐标样本。 */
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
