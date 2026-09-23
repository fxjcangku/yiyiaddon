package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿 PoC 第五轮 · 跨世界比较器。
 *
 * <p><b>它把「一个进程里的一次实验」拼成「跨进程的结果矩阵」</b>：每个进程只负责自己那个全新世界的
 * 真值落盘，比较器再把运行目录里全部真值读回来，按目标区块分组做逐 BlockPos 比对，输出两件事：</p>
 * <ol>
 *     <li><b>同 Scenario 的重复性</b>（A1 vs A2 …）——若同一请求顺序自己都不可复现，说明还存在
 *         并行调度 / 第三方 Mod / race 之类的额外变量，此时<b>不得</b>把差异归因给「生成顺序不同」
 *         （用户口径第二十三、二十四节）；</li>
 *     <li><b>跨 Scenario 的差异性</b>（A vs B vs C vs D）——这才是本轮的核心判据：仅改变 Chunk 请求顺序，
 *         最终钻石 BlockPos 是否变化。</li>
 * </ol>
 *
 * <p>顺带把用户口径第十七节要求的三分类算出来：在全部已跑场景中都存在的<b>稳定目标</b>、
 * 只在部分场景存在的<b>调度敏感目标</b>、以及全部场景都不存在的<b>排除目标</b>（后者恒为空，
 * 因为比较的对象就是「至少某个场景出现过」的位置集合的并集）。</p>
 *
 * <p>本类只做比较与排版，不写世界、不读世界。</p>
 */
public final class ChunkOrderComparator {

    /** 差异明细最多列多少格（其余只给计数，避免报告失控）。 */
    private static final int MAX_DIFF_DETAIL = 24;

    private ChunkOrderComparator() {
    }

    /**
     * 生成完整的跨世界比较文本。
     *
     * @param all 运行目录里全部真值（含本轮刚写的）
     */
    public static List<String> render(List<ChunkOrderTruth> all) {
        List<String> lines = new ArrayList<>();
        if (all.isEmpty()) {
            lines.add("（运行目录里没有任何第五轮真值文件，比较不成立）");
            return lines;
        }
        Map<String, List<ChunkOrderTruth>> byTarget = new TreeMap<>();
        for (ChunkOrderTruth truth : all) {
            byTarget.computeIfAbsent(truth.target().x() + "," + truth.target().z(), key -> new ArrayList<>())
                    .add(truth);
        }
        long seed = all.get(0).seed();
        boolean sameSeed = true;
        for (ChunkOrderTruth truth : all) {
            if (truth.seed() != seed) {
                sameSeed = false;
            }
        }
        lines.add("全部真值份数：" + all.size() + " ／ 目标区块 " + byTarget.size() + " 个 ／ 种子一致："
                + (sameSeed ? "是（" + seed + "）" : "否 —— 不同种子的世界不可直接比较，本轮结论无效"));
        lines.add("");

        boolean anyCrossDiff = false;
        boolean allRepeatsConsistent = true;
        for (Map.Entry<String, List<ChunkOrderTruth>> entry : byTarget.entrySet()) {
            List<ChunkOrderTruth> group = entry.getValue();
            lines.add("目标区块 (" + entry.getKey().replace(",", ", ") + ")");
            lines.addAll(renderCounts(group));
            Repetition repetition = renderRepetition(group);
            allRepeatsConsistent &= repetition.consistent();
            lines.addAll(repetition.lines());
            Cross cross = renderCross(group);
            anyCrossDiff |= cross.different();
            lines.addAll(cross.lines());
            lines.add("");
        }

        lines.add("判定（机械口径，只使用已跑过的场景）");
        lines.add("  同 Scenario 重复性：" + (allRepeatsConsistent
                ? "全部一致（同一请求顺序在多个全新世界里产出相同的最终钻石集合）"
                : "存在不一致 —— 必须优先排查并行调度 / Mod / race，不能直接判「顺序决定结果」"));
        lines.add("  跨 Scenario 差异性：" + (anyCrossDiff
                ? "存在逐 BlockPos 差异（情况 B：Chunk 生成顺序改变了最终矿物）"
                : "全部一致（情况 A：改变 Chunk 生成顺序没有改变最终钻石 BlockPos）"));
        lines.add("  口径限定：本判定只看目标区块内的钻石（diamond_ore + deepslate_diamond_ore），"
                + "不含其它矿物、不含其它维度；缺失场景在对应行标注为「只跑了 1 次」或不在矩阵里，不得当成一致。");
        return lines;
    }

    /** 重复性对照结果。 */
    private record Repetition(List<String> lines, boolean consistent) {
    }

    /** 跨场景对照结果。 */
    private record Cross(List<String> lines, boolean different) {
    }

    /** 每个场景一行的数量汇总。 */
    private static List<String> renderCounts(List<ChunkOrderTruth> group) {
        List<String> lines = new ArrayList<>();
        lines.add("  场景数量表（目标区块内）");
        for (ChunkOrderTruth truth : group) {
            lines.add("    " + pad(truth.scenario(), 5)
                    + " diamond_ore=" + truth.diamondOre().size()
                    + " deepslate_diamond_ore=" + truth.deepslateDiamondOre().size()
                    + " 合计=" + truth.allDiamonds().size()
                    + " ｜ 目标第 " + targetStepOrder(truth) + " 步被请求（批号 " + truth.targetBatch() + "）"
                    + " ｜ 3x3 批号 " + batchText(truth)
                    + " ｜ 全新世界=" + (truth.worldFresh() ? "是" : "否")
                    + " ｜ 请求前目标区未生成=" + (truth.targetAreaUntouched() ? "是" : "否")
                    + (truth.regionExistedBefore() ? " ｜ 警告：目标区 region 文件请求前已存在" : ""));
            lines.add("          自变量（在目标之前被装饰的邻区块 " + truth.neighborsBeforeTarget().size()
                    + " 个 / 之后 " + truth.neighborsAfterTargetCount() + " 个）："
                    + truth.neighborsBeforeTargetCn());
            lines.add("          写入方分布（跨区块写入是否发生）：" + truth.writeSourcesCn());
        }
        return lines;
    }

    /**
     * 同一场景（按字母归组：A1 与 A2 是同一种请求顺序的两次独立复现）的重复性。
     *
     * <p>这里刻意分开报两件事：<b>最终钻石集合</b>是否一致（本轮的核心判据），
     * 以及<b>批内装饰顺序</b>是否一致（原版 FEATURES 一层是并发执行的，相邻区块的批号可能互换）。
     * 把两者分开，才能避免把「批内抖动」误读成「结果不可复现」。</p>
     */
    private static Repetition renderRepetition(List<ChunkOrderTruth> group) {
        Map<String, List<ChunkOrderTruth>> byLetter = new LinkedHashMap<>();
        for (ChunkOrderTruth truth : group) {
            byLetter.computeIfAbsent(scenarioLetter(truth.scenario()), key -> new ArrayList<>()).add(truth);
        }
        List<String> lines = new ArrayList<>();
        lines.add("  同 Scenario 重复性（按场景字母归组：A1/A2 是同一种请求顺序的两次独立全新世界）：");
        boolean consistent = true;
        for (Map.Entry<String, List<ChunkOrderTruth>> entry : byLetter.entrySet()) {
            List<ChunkOrderTruth> repeats = entry.getValue();
            if (repeats.size() < 2) {
                lines.add("    " + pad(entry.getKey(), 5) + "只跑了 1 次（无重复对照）");
                continue;
            }
            ChunkOrderTruth first = repeats.get(0);
            StringBuilder verdict = new StringBuilder();
            for (int index = 1; index < repeats.size(); index++) {
                ChunkOrderTruth other = repeats.get(index);
                Set<BlockPos> diff = symmetric(first.allDiamonds(), other.allDiamonds());
                if (!diff.isEmpty()) {
                    consistent = false;
                }
                boolean sameBatches = first.batches().equals(other.batches());
                if (verdict.length() > 0) {
                    verdict.append("；");
                }
                verdict.append(first.scenario()).append(" vs ").append(other.scenario()).append("：")
                        .append(diff.isEmpty() ? "最终钻石集合逐 BlockPos 完全一致"
                                : "不一致（差 " + diff.size() + " 格：" + sample(diff) + "）")
                        .append("；批内装饰顺序").append(sameBatches ? "相同" : "存在抖动（原版同层并发所致，不影响最终集合）");
            }
            lines.add("    " + pad(entry.getKey(), 5) + verdict);
        }
        return new Repetition(lines, consistent);
    }

    /** 场景名字母（{@code A1} → {@code A}）。 */
    private static String scenarioLetter(String scenario) {
        return scenario == null || scenario.isEmpty() ? "?" : scenario.substring(0, 1);
    }

    /** 跨场景矩阵 + 三分类 + 逐 BlockPos 差异明细。 */
    private static Cross renderCross(List<ChunkOrderTruth> group) {
        List<String> lines = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (ChunkOrderTruth truth : group) {
            names.add(truth.scenario());
        }
        boolean different = false;
        lines.add("  跨 Scenario 矩阵（= 逐 BlockPos 完全一致 ／ X = 有差异）：");
        StringBuilder header = new StringBuilder("        ");
        for (String name : names) {
            header.append(pad(name, 7));
        }
        lines.add(header.toString());
        for (int row = 0; row < group.size(); row++) {
            StringBuilder line = new StringBuilder("        " + pad(names.get(row), 7));
            for (int column = 0; column < group.size(); column++) {
                if (row == column) {
                    line.append(pad("=", 7));
                    continue;
                }
                Set<BlockPos> diff = symmetric(group.get(row).allDiamonds(), group.get(column).allDiamonds());
                different |= !diff.isEmpty();
                line.append(pad(diff.isEmpty() ? "=" : "X", 7));
            }
            lines.add(line.toString());
        }

        Map<BlockPos, Integer> hits = new TreeMap<>(POSITION_ORDER);
        Set<BlockPos> union = new LinkedHashSet<>();
        for (ChunkOrderTruth truth : group) {
            for (BlockPos pos : truth.allDiamonds()) {
                union.add(pos);
                hits.merge(pos, 1, Integer::sum);
            }
        }
        Set<BlockPos> differing = new LinkedHashSet<>();
        int stable = 0;
        for (BlockPos pos : union) {
            if (hits.getOrDefault(pos, 0) == group.size()) {
                stable++;
            } else {
                differing.add(pos);
            }
        }
        lines.add("  三分类（按出现过的场景数，共 " + group.size() + " 个场景）：稳定 " + stable
                + " 格（全部场景都存在）／ 调度敏感 " + differing.size()
                + " 格（只在部分场景存在）／ 排除 0 格（无场景存在）");

        if (differing.isEmpty()) {
            lines.add("  差异明细：无（所有已跑场景在该目标区块上逐 BlockPos 完全一致）");
            return new Cross(lines, different);
        }
        lines.add("  差异明细（共 " + differing.size() + " 格，列出前 "
                + Math.min(MAX_DIFF_DETAIL, differing.size()) + " 格）：");
        int listed = 0;
        for (BlockPos pos : differing) {
            if (listed++ >= MAX_DIFF_DETAIL) {
                lines.add("    …（其余 " + (differing.size() - MAX_DIFF_DETAIL) + " 格略）");
                break;
            }
            lines.add("    " + posText(pos) + "：" + describeByScenario(group, pos));
        }
        lines.add("  归因说明：括号里的「步 / viewer / 批 / feature / 前状态」是该场景中该格首次出现"
                + "那一步的记录；步序 = 当时被请求（因而被装饰）的区块。原始行见真值文件的 STEP / P / D 三类行。");
        return new Cross(lines, different);
    }

    /** 某格在各场景的存在情况与首次出现记录。 */
    private static String describeByScenario(List<ChunkOrderTruth> group, BlockPos pos) {
        StringBuilder text = new StringBuilder();
        for (ChunkOrderTruth truth : group) {
            if (text.length() > 0) {
                text.append(" ； ");
            }
            text.append(truth.scenario()).append('=');
            if (!truth.allDiamonds().contains(pos)) {
                text.append("无");
                continue;
            }
            int step = truth.appearanceMap().getOrDefault(pos, -1);
            ChunkOrderTruth.Step stepRecord = null;
            for (ChunkOrderTruth.Step candidate : truth.steps()) {
                if (candidate.index() == step) {
                    stepRecord = candidate;
                }
            }
            ChunkOrderTruth.Write write = truth.provenance().get(pos);
            text.append("有[步").append(step);
            if (stepRecord != null) {
                text.append('(').append(stepRecord.chunk().x()).append(',').append(stepRecord.chunk().z())
                        .append(") 批").append(stepRecord.batch());
            }
            if (write != null) {
                text.append(" feature=").append(write.featurePath())
                        .append(" 前状态=").append(write.preStateId())
                        .append(" 写入方(").append(write.viewer().x()).append(',').append(write.viewer().z())
                        .append(") 批").append(write.batch());
            } else {
                text.append(" 溯源缺失");
            }
            text.append(']');
        }
        return text.toString();
    }

    /** 目标区块在请求顺序里排第几步（由 STEP 行反查）。 */
    private static int targetStepOrder(ChunkOrderTruth truth) {
        for (ChunkOrderTruth.Step step : truth.steps()) {
            if (step.chunk().equals(truth.target())) {
                return step.index();
            }
        }
        return -1;
    }

    /** 3x3 装饰批号文本（按 北一行 / 中间一行 / 南一行 排）。 */
    private static String batchText(ChunkOrderTruth truth) {
        Map<Long, Integer> byPack = new LinkedHashMap<>();
        for (Map.Entry<ChunkPos, Integer> entry : truth.batches().entrySet()) {
            byPack.put(entry.getKey().pack(), entry.getValue());
        }
        StringBuilder text = new StringBuilder();
        for (int dz = -1; dz <= 1; dz++) {
            if (dz > -1) {
                text.append(" / ");
            }
            for (int dx = -1; dx <= 1; dx++) {
                if (dx > -1) {
                    text.append(' ');
                }
                int batch = byPack.getOrDefault(
                        ChunkPos.pack(truth.target().x() + dx, truth.target().z() + dz), -1);
                text.append(batch < 0 ? "-" : String.valueOf(batch));
            }
        }
        return text.toString();
    }

    /** 位置集合的对称差。 */
    private static Set<BlockPos> symmetric(Set<BlockPos> left, Set<BlockPos> right) {
        Set<BlockPos> diff = new LinkedHashSet<>(left);
        diff.removeAll(right);
        Set<BlockPos> other = new LinkedHashSet<>(right);
        other.removeAll(left);
        diff.addAll(other);
        return diff;
    }

    /** 坐标样本（最多 6 个）。 */
    private static String sample(Set<BlockPos> positions) {
        StringBuilder text = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= 6) {
                text.append(" …");
                break;
            }
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append(posText(pos));
        }
        return text.toString();
    }

    private static String posText(BlockPos pos) {
        return "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")";
    }

    private static String pad(String text, int width) {
        if (text.length() >= width) {
            return text + " ";
        }
        return text + " ".repeat(width - text.length());
    }

    /** 差异明细按坐标稳定排序（x → y → z）。 */
    private static final Comparator<BlockPos> POSITION_ORDER = (left, right) -> {
        if (left.getX() != right.getX()) {
            return Integer.compare(left.getX(), right.getX());
        }
        if (left.getY() != right.getY()) {
            return Integer.compare(left.getY(), right.getY());
        }
        return Integer.compare(left.getZ(), right.getZ());
    };
}
