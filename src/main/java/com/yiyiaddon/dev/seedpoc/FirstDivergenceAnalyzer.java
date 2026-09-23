package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * first-divergence 分析器（第三轮第三阶段）。
 *
 * <p><b>它做的事</b>：把「原版这一遍装饰」与「重放这同一次装饰」在钻石四条上逐条、逐事件对齐，
 * 找出<b>第一处真正产生不同的步骤</b>，并按用户口径归到某一层：</p>
 * <ol>
 *     <li><b>派生层</b>：placement origin（{@code PlacementModifier} 链的输出 / 生物群系判定的位置）
 *         第 N 项就不同 ⇒ 问题在 {@code PlacedFeature} / 计数与高度修饰符 / {@code featureSeed} / 全局索引；</li>
 *     <li><b>矿物层</b>：origin 相同、但 {@code OreFeature} 的矿脉几何参数（由 {@code random} 直接算出）不同
 *         ⇒ 问题在 {@code OreFeature} 内部随机流或上下文；</li>
 *     <li><b>接受层</b>：origin 与矿脉几何都相同，但候选点上的方块状态或判定结果不同
 *         ⇒ 问题在 {@code ensureCanWrite} / 目标方块判定 / 贴空气判定 / section 写入。</li>
 * </ol>
 *
 * <p><b>为什么按「事件」对齐而不是按「两张表」对齐</b>：{@code PlacedFeature#placeWithContext} 用惰性
 * {@code Stream} 把修饰符链与 {@code ConfiguredFeature#place} 串起来，真实顺序是
 * 「判定 1 → 放置 1 → 判定 2 → 放置 2 → …」。若把判定与放置分开存两张表再按下标对照，
 * 只要两侧「判定数 / 放置数」的配比不同（第三轮实测出现过原版 7/7、重放 7/3），
 * 下标就会错位，报告会把<b>下游症状</b>当成第一处分叉。因此台账存成单条时间线，
 * 本分析器逐事件比对，并把「事件类型错位」本身也当成一处分叉如实报出来。</p>
 *
 * <p>判据全部来自两侧各自的原始取证（{@link OreVeinTrace} 与 {@link DiamondFeatureOracle}），
 * 没有任何推断成分；比不出差异时如实写「比不出」。</p>
 */
public final class FirstDivergenceAnalyzer {

    /** 分析结果：明细行 + 一句话结论 + 已定位到的层。 */
    public record Result(List<String> lines, String summary, String layer) {
    }

    /** 一处分叉。 */
    private record Divergence(String featurePath, int featureOrder, String description, String layer) {
    }

    private FirstDivergenceAnalyzer() {
    }

    /**
     * 跑一次对齐分析。
     *
     * @param viewer  本次装饰的 viewer（= 重放中心区块）
     * @param target  统计口径落点区块（第一阶段 = viewer 自身；第二阶段 = 目标区块）
     * @param vanilla 原版逐 feature 记录（执行顺序）
     * @param replay  重放逐 feature 记录（执行顺序）
     */
    public static Result analyze(ChunkPos viewer, ChunkPos target,
                                 List<DiamondFeatureOracle.FeatureStage> vanilla,
                                 List<OreDecorationReplay.FeatureReplay> replay) {
        List<String> lines = new ArrayList<>();
        int count = Math.max(vanilla.size(), replay.size());
        if (count == 0) {
            String summary = "本遍装饰里原版与重放都没有钻石 placed_feature 执行（无数据可比）";
            lines.add(summary);
            return new Result(lines, summary, "无");
        }

        int firstBad = renderFeatureTable(lines, viewer, target, vanilla, replay);
        if (firstBad < 0) {
            String summary = "逐 feature 对齐后未发现任何差异（原版与重放在每一条钻石 feature 上的执行前状态、"
                    + "事件时间线与新增集合都相同）";
            lines.add("      " + summary);
            return new Result(lines, summary, "无");
        }

        Divergence divergence = findFirst(viewer, vanilla, replay);
        if (divergence == null) {
            lines.add("      精细下钻：该 feature 的事件时间线逐项一致，"
                    + "差异只出现在最终写入集合上 ⇒ 定位到接受层（写入路径）");
            String fallback = "第一处分叉落在第 " + (firstBad + 1) + " 条钻石 feature；"
                    + "精细下钻未发现判定 / 放置 / 几何 / 候选点层面的差异，定位到接受层（写入路径）";
            return new Result(lines, fallback, "接受层（写入路径）");
        }

        lines.add("      ⇒ 第一处分叉（按执行顺序取最早一处）：第 " + (divergence.featureOrder() + 1)
                + " 条钻石 feature（" + divergence.featurePath() + "）");
        lines.add("        " + divergence.description());
        String summary = "第一处分叉落在第 " + (divergence.featureOrder() + 1) + " 条钻石 feature（"
                + divergence.featurePath() + "）；定位到的层：" + divergence.layer();
        return new Result(lines, summary, divergence.layer());
    }

    /** 逐 feature 对照表；返回第一条不一致的 feature 序号（全一致返回 -1）。 */
    private static int renderFeatureTable(List<String> lines, ChunkPos viewer, ChunkPos target,
                                          List<DiamondFeatureOracle.FeatureStage> vanilla,
                                          List<OreDecorationReplay.FeatureReplay> replay) {
        int count = Math.max(vanilla.size(), replay.size());
        int firstBad = -1;
        for (int index = 0; index < count; index++) {
            DiamondFeatureOracle.FeatureStage left = index < vanilla.size() ? vanilla.get(index) : null;
            OreDecorationReplay.FeatureReplay right = index < replay.size() ? replay.get(index) : null;
            String leftPath = left == null ? "（原版无此条）" : left.featurePath();
            String rightPath = right == null ? "（重放无此条）" : right.featurePath();
            boolean pathSame = left != null && right != null && leftPath.equals(rightPath);
            boolean beforeSame = left != null && right != null && left.before().equals(right.before());
            Set<BlockPos> leftAdded = left == null ? Set.of() : DiamondScanner.within(left.added(), target);
            Set<BlockPos> rightAdded = right == null ? Set.of() : DiamondScanner.within(right.added(), target);
            Set<BlockPos> leftAddedAll = left == null ? Set.of() : left.added();
            Set<BlockPos> rightAddedAll = right == null ? Set.of() : right.added();
            BlockPosDiff diffHere = BlockPosDiff.of(leftAdded, rightAdded);
            boolean same = pathSame && beforeSame && diffHere.exact() && leftAddedAll.equals(rightAddedAll);
            lines.add("      第 " + (index + 1) + " 条：" + leftPath + " ／ 重放 " + rightPath
                    + "（执行顺序对齐：" + (pathSame ? "一致" : "不一致")
                    + "）；执行前钻石集合（3x3）原版 " + (left == null ? 0 : left.before().size())
                    + " / 重放 " + (right == null ? 0 : right.before().size())
                    + " → " + (beforeSame ? "一致" : "不同")
                    + "；新增（本区块）原版 " + leftAdded.size() + " / 重放 " + rightAdded.size()
                    + "，新增（3x3）原版 " + leftAddedAll.size() + " / 重放 " + rightAddedAll.size()
                    + (same ? " → 一致" : " → 不同"));
            List<OreVeinTrace.Event> leftTimeline = OreVeinTrace.timeline(false, viewer, leftPath);
            List<OreVeinTrace.Event> rightTimeline = OreVeinTrace.timeline(true, viewer, leftPath);
            lines.add("      第 " + (index + 1) + " 条取证：原版 " + OreVeinTrace.ledgerCn(false, viewer, leftPath)
                    + " ／ 重放 " + OreVeinTrace.ledgerCn(true, viewer, leftPath));
            lines.add("      第 " + (index + 1) + " 条事件时间线（原版）：" + timelineText(leftTimeline));
            lines.add("      第 " + (index + 1) + " 条事件时间线（重放）：" + timelineText(rightTimeline));
            if (!same && firstBad < 0) {
                firstBad = index;
            }
        }
        return firstBad;
    }

    /**
     * 按执行顺序找第一处真正的分叉。
     *
     * <p>返回 null 表示这些层面全部一致（差异只能在「写入」这一步）。</p>
     */
    private static Divergence findFirst(ChunkPos viewer,
                                        List<DiamondFeatureOracle.FeatureStage> vanilla,
                                        List<OreDecorationReplay.FeatureReplay> replay) {
        int count = Math.min(vanilla.size(), replay.size());
        for (int index = 0; index < count; index++) {
            String path = vanilla.get(index).featurePath();
            if (!path.equals(replay.get(index).featurePath())) {
                return new Divergence(path, index, "执行顺序第 " + (index + 1) + " 条不同：原版 " + path
                        + " ／ 重放 " + replay.get(index).featurePath()
                        + "（FeatureSorter 全局索引或步骤归属不同）",
                        "派生层（feature 执行序 / 全局索引）");
            }
            Divergence found = compareTimeline(path, index, OreVeinTrace.timeline(false, viewer, path),
                    OreVeinTrace.timeline(true, viewer, path));
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /** 逐事件比对一条钻石 feature 的两侧时间线。 */
    private static Divergence compareTimeline(String path, int featureOrder,
                                              List<OreVeinTrace.Event> left,
                                              List<OreVeinTrace.Event> right) {
        int shared = Math.min(left.size(), right.size());
        for (int index = 0; index < shared; index++) {
            OreVeinTrace.Event a = left.get(index);
            OreVeinTrace.Event b = right.get(index);
            String at = "第 " + (index + 1) + " 个事件";
            if (a instanceof OreVeinTrace.BiomeEvent ba && b instanceof OreVeinTrace.BiomeEvent bb) {
                if (!ba.origin().equals(bb.origin())) {
                    return new Divergence(path, featureOrder, at + "（生物群系判定）的位置就不同（原版 "
                            + cn(ba.origin()) + " ／ 重放 " + cn(bb.origin()) + "）"
                            + " ⇒ 该位置由 CountPlacement / InSquarePlacement / 高度修饰符的随机派生决定，"
                            + "说明随机流在此之前已经漂移",
                            "派生层（随机流漂移：InSquarePlacement / 高度修饰符）");
                }
                if (ba.passed() != bb.passed()) {
                    return new Divergence(path, featureOrder, at + "（生物群系判定 " + cn(ba.origin())
                            + "）结果不同：原版 " + (ba.passed() ? "通过" : "不通过") + "（生物群系 " + ba.biomeId()
                            + "）／ 重放 " + (bb.passed() ? "通过" : "不通过") + "（生物群系 " + bb.biomeId() + "）"
                            + " ⇒ 判定不通过的位置会被整个丢弃，少放一次矿就少消耗一整段 OreFeature 随机数，"
                            + "本遍之后所有矿脉都会漂移",
                            "派生层（BiomeFilter 判定 / 该点生物群系读取）");
                }
            } else if (a instanceof OreVeinTrace.PlaceEvent pa && b instanceof OreVeinTrace.PlaceEvent pb) {
                Divergence found = comparePlace(path, featureOrder, index, pa, pb);
                if (found != null) {
                    return found;
                }
            } else {
                return new Divergence(path, featureOrder, at + "的类型就不同：原版 " + kind(a)
                        + "（" + originOf(a) + "）／ 重放 " + kind(b) + "（" + originOf(b) + "）"
                        + " ⇒ 「判定—放置」的交错顺序不同，说明某一侧的候选位置数量或判定结果在此之前已经不同",
                        "派生层（判定 / 放置交错顺序）");
            }
        }
        if (left.size() != right.size()) {
            return new Divergence(path, featureOrder, "事件条数不同：原版 " + left.size() + " 条 ／ 重放 "
                    + right.size() + " 条（前 " + shared + " 条逐项一致）"
                    + "；原版侧剩余 " + restText(left, shared) + "；重放侧剩余 " + restText(right, shared),
                    "派生层（PlacementModifier 候选数量 / 判定结果数量）");
        }
        return null;
    }

    /** 比对「一次放置」：origin → 矿脉几何 → 候选点判定序列。 */
    private static Divergence comparePlace(String path, int featureOrder, int eventIndex,
                                           OreVeinTrace.PlaceEvent left, OreVeinTrace.PlaceEvent right) {
        String at = "第 " + (eventIndex + 1) + " 个事件（放置）";
        if (!left.origin().equals(right.origin())) {
            return new Divergence(path, featureOrder, at + "的 placement origin 就不同（原版 " + cn(left.origin())
                    + " ／ 重放 " + cn(right.origin()) + "）",
                    "派生层（PlacementModifier / featureSeed / 全局索引）");
        }
        boolean leftHasVein = left.vein() != null;
        boolean rightHasVein = right.vein() != null;
        if (leftHasVein != rightHasVein) {
            return new Divergence(path, featureOrder, at + "（origin " + cn(left.origin())
                    + "）是否进入 doPlace 不同：原版 "
                    + (leftHasVein ? "进入（OreFeature.place:46 的高度闸门通过）" : "未进入（高度闸门未过）")
                    + " ／ 重放 " + (rightHasVein ? "进入" : "未进入"),
                    "矿物层（OreFeature 入口高度闸门 / 生成期高度图）");
        }
        if (leftHasVein && !left.vein().equals(right.vein())) {
            return new Divergence(path, featureOrder, at + "（origin " + cn(left.origin())
                    + "）的矿脉几何参数不同：原版 " + cn(left.vein()) + " ／ 重放 " + cn(right.vein()),
                    "矿物层（OreFeature 内部随机流或上下文）");
        }
        List<OreVeinTrace.AcceptStep> leftAccepts = left.accepts();
        List<OreVeinTrace.AcceptStep> rightAccepts = right.accepts();
        int sharedAccepts = Math.min(leftAccepts.size(), rightAccepts.size());
        for (int step = 0; step < sharedAccepts; step++) {
            OreVeinTrace.AcceptStep a = leftAccepts.get(step);
            OreVeinTrace.AcceptStep b = rightAccepts.get(step);
            if (!a.pos().equals(b.pos())) {
                return new Divergence(path, featureOrder, at + "第 " + (step + 1) + " 个候选点坐标不同：原版 "
                        + cn(a.pos()) + " ／ 重放 " + cn(b.pos()),
                        "矿物层（候选点遍历几何不同）");
            }
            if (!a.stateId().equals(b.stateId())) {
                return new Divergence(path, featureOrder, at + "第 " + (step + 1) + " 个候选点 " + cn(a.pos())
                        + " 的方块就不同：原版 " + a.stateId() + "（可替换=" + a.replaceable() + "）／ 重放 "
                        + b.stateId() + "（可替换=" + b.replaceable() + "）"
                        + " ⇒ 这一格状态不同会改变随机数消耗次数，是本遍后续矿脉漂移的直接原因",
                        "接受层（候选点世界状态 / 目标方块判定 / 贴空气判定）");
            }
            if (a.passed() != b.passed()) {
                return new Divergence(path, featureOrder, at + "第 " + (step + 1) + " 个候选点 " + cn(a.pos())
                        + "（方块 " + a.stateId() + "）判定结果不同：原版 " + a.passed() + " ／ 重放 " + b.passed()
                        + " ⇒ 坐标与方块都相同却判定不同，差异在 ensureCanWrite 或贴空气判定",
                        "接受层（ensureCanWrite / 贴空气判定）");
            }
        }
        if (leftAccepts.size() != rightAccepts.size()) {
            return new Divergence(path, featureOrder, at + "的候选点判定次数不同：原版 " + leftAccepts.size()
                    + " 次 ／ 重放 " + rightAccepts.size() + " 次（前 " + sharedAccepts + " 项逐项一致）"
                    + " ⇒ 遍历到的候选点数量不同",
                    "接受层（候选点遍历数量 / 写入范围判定）");
        }
        return null;
    }

    /** 事件类型的中文短名。 */
    private static String kind(OreVeinTrace.Event event) {
        return event instanceof OreVeinTrace.BiomeEvent ? "生物群系判定" : "放置";
    }

    /** 事件的位置。 */
    private static String originOf(OreVeinTrace.Event event) {
        if (event instanceof OreVeinTrace.BiomeEvent biome) {
            return cn(biome.origin()) + (biome.passed() ? "通过" : "不通过");
        }
        OreVeinTrace.PlaceEvent place = (OreVeinTrace.PlaceEvent) event;
        return cn(place.origin()) + (place.vein() == null ? "未进doPlace" : "进doPlace");
    }

    private static String restText(List<OreVeinTrace.Event> events, int from) {
        if (from >= events.size()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        for (int index = from; index < events.size(); index++) {
            if (index > from) {
                builder.append("  ");
            }
            builder.append(kind(events.get(index))).append(originOf(events.get(index)));
        }
        return builder.toString();
    }

    /** 时间线的紧凑文本（最多 16 项，超出截断）。 */
    private static String timelineText(List<OreVeinTrace.Event> events) {
        if (events.isEmpty()) {
            return "空（探针未落账）";
        }
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < events.size(); index++) {
            if (index >= 16) {
                builder.append(" …(共 ").append(events.size()).append(" 项)");
                break;
            }
            if (index > 0) {
                builder.append("  ");
            }
            OreVeinTrace.Event event = events.get(index);
            if (event instanceof OreVeinTrace.BiomeEvent biome) {
                builder.append("判定").append(cn(biome.origin()))
                        .append(biome.passed() ? "通过" : "**不通过**");
            } else {
                OreVeinTrace.PlaceEvent place = (OreVeinTrace.PlaceEvent) event;
                builder.append("放置").append(cn(place.origin()))
                        .append(place.vein() == null ? "(闸门未过)" : "(进doPlace)");
            }
        }
        return builder.toString();
    }

    private static String cn(BlockPos pos) {
        return "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")";
    }

    private static String cn(OreVeinTrace.Vein vein) {
        if (vein == null) {
            return "未进入 doPlace（yStart 闸门未过）";
        }
        return "x[" + trim(vein.x0()) + "→" + trim(vein.x1()) + "] y[" + trim(vein.y0()) + "→" + trim(vein.y1())
                + "] z[" + trim(vein.z0()) + "→" + trim(vein.z1()) + "] 候选盒("
                + vein.xStart() + "," + vein.yStart() + "," + vein.zStart() + ")"
                + " " + vein.sizeXZ() + "x" + vein.sizeY();
    }

    private static String trim(double value) {
        return String.format(java.util.Locale.ROOT, "%.3f", value);
    }

    /** 取最多 6 个坐标样本。 */
    static String sample(Set<BlockPos> positions) {
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
            builder.append(cn(pos));
        }
        return builder.toString();
    }
}
