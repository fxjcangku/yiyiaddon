package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第七轮 · OreFeature 候选点级诊断（<b>只取证，不参与预测</b>）。
 *
 * <p><b>它回答什么</b>：用户口径第十二、二十一节要求，Seed 12345 {@code (0,0)} 的 5 格固定漏报
 * 不能只写「顺序导致 RNG 漂移」，必须给出<b>第一条发生不同的 candidate</b>：
 * 候选序号 / 坐标 / 写入前方块 / 是否可替换 / 是否消耗一次随机 / 是否被接受。
 * 本类逐候选点记下这六项（真实侧与 Debug 重放侧各记一份，标签区分），
 * 两侧逐项对齐后，第一处不同就是整条随机链的分叉点。</p>
 *
 * <p><b>为什么「是否消耗一次随机」可以算出来</b>（不去读 Java Random 的内部状态）：
 * 原版 {@code OreFeature#canPlaceOre} 的流程是
 * ① {@code targetState.target.test(state, random)}（石头 / 深板岩的 {@code BlockMatchTest} 不消耗随机）；
 * ② 通过后才调 {@code shouldSkipAirCheck(random, config.discardChanceOnAirExposure)}，
 * 而它只在 {@code 0 < chance < 1} 时消耗一次 {@code nextFloat()}
 * （{@code OreFeature#shouldSkipAirCheck}）。因此「是否消耗随机」= 「目标标签判定通过 且
 * {@code 0 < discardChanceOnAirExposure < 1}」，这是一条可复核的行为级判据，不依赖内部状态。</p>
 *
 * <p><b>注意</b>：这里的「可替换」用 {@link OreBlockLedger#isReplaceable}（原版
 * {@code stone_ore_replaceables} / {@code deepslate_ore_replaceables} 标签）作为目标判定的代理，
 * 与钻石四条的 {@code RuleTest} 逐字一致；报告里据此统计「累计消耗了几次随机」。</p>
 */
public final class OreCandidateJournal {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 矿脉序号发放器（本 (viewer, placed_feature) 下第几条矿脉，两侧按它配对）。 */
    private static final Map<String, AtomicLong> VEIN_ORDINALS = Collections.synchronizedMap(new LinkedHashMap<>());

    /** 全部候选点记录。 */
    private static final List<Candidate> CANDIDATES = Collections.synchronizedList(new ArrayList<>());

    /** 当前线程正在处理的矿脉（{@code doPlace} HEAD 开始、feature RETURN 结束）。 */
    private static final ThreadLocal<Vein> CURRENT_VEIN = new ThreadLocal<>();

    /** 当前线程正在执行的 placed_feature 上下文。 */
    private static final ThreadLocal<Context> CURRENT = new ThreadLocal<>();

    private static volatile boolean armed;

    private OreCandidateJournal() {
    }

    /**
     * 一条候选点判定记录。
     *
     * @param offline          是否来自 Debug 重放（离线）侧
     * @param viewer           执行这次装饰的 viewer 区块
     * @param placedFeature    placed_feature 路径
     * @param veinOrdinal      本 (viewer, placed_feature) 下第几条矿脉（1 起；两侧按它配对）
     * @param candidateIndex   本矿脉内第几个候选点（0 起）
     * @param randomOrdinal    本矿脉内「第几次会消耗随机」的累计序号（可替换候选点的序号）
     * @param pos              候选点坐标
     * @param beforeId         判定那一刻该位置的方块
     * @param replaceable      是否属于可替换标签（目标判定通过的代理）
     * @param consumedRandom   是否消耗了一次 {@code nextFloat()}
     * @param accepted         是否被 {@code canPlaceOre} 接受（接受 = 随后真的 setBlock）
     */
    public record Candidate(boolean offline,
                            ChunkPos viewer,
                            String placedFeature,
                            int veinOrdinal,
                            int candidateIndex,
                            int randomOrdinal,
                            BlockPos pos,
                            String beforeId,
                            boolean replaceable,
                            boolean consumedRandom,
                            boolean accepted) {

        /** 报告一行。 */
        String render() {
            return (offline ? "O " : "W ") + viewer.x() + " " + viewer.z() + " " + placedFeature + " "
                    + veinOrdinal + " " + candidateIndex + " " + randomOrdinal + " " + pos.getX() + " "
                    + pos.getY() + " " + pos.getZ() + " " + beforeId + " " + (replaceable ? 1 : 0) + " "
                    + (consumedRandom ? 1 : 0) + " " + (accepted ? 1 : 0);
        }
    }

    /** 一条矿脉的上下文（候选点先落本矿脉的缓冲，只有「碰到目标区块」的矿脉才进全局台账）。 */
    private static final class Vein {
        private final int ordinal;
        private final List<Candidate> buffer = new ArrayList<>();
        private boolean touchesTarget;
        private int candidateIndex;
        private int randomOrdinal;

        Vein(int ordinal) {
            this.ordinal = ordinal;
        }

        /** 下一个候选点序号。 */
        int nextCandidate() {
            return candidateIndex++;
        }

        /** 当前累计的「会消耗随机」的候选点数。 */
        int randomOrdinal() {
            return randomOrdinal;
        }

        /** 记录一次会消耗随机的判定。 */
        void countRandom() {
            randomOrdinal++;
        }
    }

    /** 被跟踪的目标区块（packed ChunkPos）：只有写进这些区块的矿脉才值得记账。 */
    private static volatile Set<Long> trackedTargets = Set.of();

    /**
     * 一条 placed_feature 的上下文。
     *
     * <p>{@code offline} 必须在 HEAD 那一刻判定（{@code canPlaceOre} 的签名里没有 level，
     * 拿不到世界访问层，只能在这里把「这一遍是真实生成还是 Debug 重放」带下去）。</p>
     */
    private record Context(ChunkPos viewer, String placedFeature, boolean offline) {
    }

    /** 是否武装（总开关 + 跟踪区域已就绪）。 */
    public static void arm(List<ChunkPos> targets) {
        if (!SeedPocFlags.round7Ore()) {
            return;
        }
        Set<Long> tracked = new LinkedHashSet<>();
        for (ChunkPos target : targets) {
            tracked.add(target.pack());
        }
        trackedTargets = Set.copyOf(tracked);
        armed = true;
        LOGGER.info("{}：第七轮 OreFeature 候选点诊断已武装（只记「碰到目标区块」的矿脉，共 {} 个目标）",
                SeedPocConstants.LOG_KEY, tracked.size());
    }

    /** 是否武装。 */
    public static boolean armed() {
        return armed;
    }

    /** 清空（每次真实请求窗口开始前调用）。 */
    public static void reset() {
        CANDIDATES.clear();
        VEIN_ORDINALS.clear();
    }

    /**
     * 只清掉重放（离线）侧记录：真实侧候选序列要留着与重放侧逐项比。
     *
     * <p><b>矿脉序号表也必须一起清</b>：它按 (viewer, placed_feature) 计数，
     * 只有每次重放都从 1 开始，两侧的「第 k 条矿脉」才是同一条矿脉。
     * 否则第二次重放的序号会接着上一次往上加，逐项配对会全部对不上。</p>
     */
    public static void resetOffline() {
        synchronized (CANDIDATES) {
            CANDIDATES.removeIf(Candidate::offline);
        }
        VEIN_ORDINALS.clear();
    }

    /** {@code PlacedFeature#placeWithBiomeCheck} HEAD：登记当前 feature 上下文。 */
    public static void onFeatureHead(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        if (!armed) {
            return;
        }
        CURRENT.set(new Context(new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4),
                GenStageCapture.pathOf(level, feature), OfflineStageCapture.isOfflineCall(level)));
    }

    /** {@code PlacedFeature#placeWithBiomeCheck} RETURN：清上下文（先把最后一条矿脉结算掉）。 */
    public static void onFeatureTail() {
        if (!armed) {
            return;
        }
        flushVein();
        CURRENT.remove();
        CURRENT_VEIN.remove();
    }

    /** {@code OreFeature#doPlace} HEAD：开一条新矿脉（候选序号与随机序号都从 0 起）。 */
    public static void onVeinStart() {
        if (!armed) {
            return;
        }
        Context context = CURRENT.get();
        if (context == null) {
            return;
        }
        // 上一条矿脉到此结束，先结算它
        flushVein();
        String key = context.viewer().x() + "," + context.viewer().z() + "|" + context.placedFeature();
        int ordinal = (int) VEIN_ORDINALS
                .computeIfAbsent(key, ignored -> new AtomicLong())
                .incrementAndGet();
        CURRENT_VEIN.set(new Vein(ordinal));
    }

    /** 结算当前矿脉：只有「有候选点落在目标区块内」的矿脉才进全局台账（其余不占内存、不进报告）。 */
    private static void flushVein() {
        Vein vein = CURRENT_VEIN.get();
        if (vein == null) {
            return;
        }
        CURRENT_VEIN.remove();
        if (vein.touchesTarget && !vein.buffer.isEmpty()) {
            CANDIDATES.addAll(vein.buffer);
        }
    }

    /**
     * {@code OreFeature#canPlaceOre} RETURN：记一条候选点判定。
     *
     * @param pos      候选点（原版复用 MutableBlockPos，这里取不可变副本）
     * @param state    判定那一刻该位置的方块状态
     * @param config   当前矿脉配置（只读它的 {@code discardChanceOnAirExposure}）
     * @param accepted 判定结果
     */
    public static void onCandidate(BlockPos pos, BlockState state, OreConfiguration config, boolean accepted) {
        if (!armed) {
            return;
        }
        Context context = CURRENT.get();
        Vein vein = CURRENT_VEIN.get();
        if (context == null || vein == null || pos == null || state == null) {
            return;
        }
        boolean replaceable = OreBlockLedger.isReplaceable(state);
        float chance = config == null ? 0.0F : config.discardChanceOnAirExposure;
        boolean consumed = replaceable && chance > 0.0F && chance < 1.0F;
        int candidateIndex = vein.nextCandidate();
        int randomOrdinal = vein.randomOrdinal();
        if (consumed) {
            vein.countRandom();
        }
        BlockPos immutable = pos.immutable();
        if (trackedTargets.contains(ChunkPos.pack(immutable.getX() >> 4, immutable.getZ() >> 4))) {
            vein.touchesTarget = true;
        }
        vein.buffer.add(new Candidate(context.offline(), context.viewer(), context.placedFeature(),
                vein.ordinal, candidateIndex, randomOrdinal, immutable, OreBlockLedger.shortId(state),
                replaceable, consumed, accepted));
    }

    /** 全部候选记录（副本）。 */
    public static List<Candidate> snapshot() {
        synchronized (CANDIDATES) {
            return List.copyOf(CANDIDATES);
        }
    }

    /** 真实侧候选记录（副本）。 */
    public static List<Candidate> realSnapshot() {
        return filter(false);
    }

    /** 重放（离线）侧候选记录（副本）。 */
    public static List<Candidate> offlineSnapshot() {
        return filter(true);
    }

    /** 已落账候选点数。 */
    public static int size() {
        return CANDIDATES.size();
    }

    /**
     * 真实侧 vs 重放侧：在指定位置的候选序列上找「第一处不一致」的候选点（用户口径第二十一节）。
     */
    public static List<String> firstDivergence(Set<BlockPos> onlyPositions) {
        return firstDivergence(filter(false), filter(true), "REAL", "OFFLINE", onlyPositions);
    }

    /**
     * 两侧候选序列的逐项比对。
     *
     * <p>对齐口径：<b>按 (viewer, placed_feature, 矿脉序号, 候选序号)</b> 逐项比
     * （同一 viewer、同一条 placed_feature、同一条矿脉的第 k 个候选），
     * 比对四项：坐标 / 写入前方块 / 是否可替换 / 是否被接受。
     * 第一项不同的那一行就是漂移起点。</p>
     *
     * @param onlyPositions 只在这些位置里找（空集 = 全部位置都算）
     */
    public static List<String> firstDivergence(List<Candidate> real, List<Candidate> offline,
                                               String realLabel, String offlineLabel,
                                               Set<BlockPos> onlyPositions) {
        Map<String, Candidate> offlineByKey = new LinkedHashMap<>();
        for (Candidate candidate : offline) {
            offlineByKey.put(key(candidate), candidate);
        }
        List<String> lines = new ArrayList<>();
        int compared = 0;
        int diverged = 0;
        int randomShifted = 0;
        for (Candidate candidate : real) {
            if (!onlyPositions.isEmpty() && !onlyPositions.contains(candidate.pos())) {
                continue;
            }
            Candidate other = offlineByKey.get(key(candidate));
            if (other == null) {
                continue;
            }
            compared++;
            boolean samePosition = candidate.pos().equals(other.pos());
            boolean sameBefore = candidate.beforeId().equals(other.beforeId());
            boolean sameAccepted = candidate.accepted() == other.accepted();
            if (samePosition && sameBefore && sameAccepted) {
                continue;
            }
            diverged++;
            if (candidate.randomOrdinal() != other.randomOrdinal()) {
                randomShifted++;
            }
            if (diverged <= 12) {
                lines.add("  第一处分叉候选 #" + (diverged == 1 ? "1（起点）" : String.valueOf(diverged))
                        + "：viewer(" + candidate.viewer().x() + "," + candidate.viewer().z() + ") "
                        + candidate.placedFeature() + " 矿脉#" + candidate.veinOrdinal()
                        + " 候选#" + candidate.candidateIndex());
                lines.add("    " + realLabel + "：pos=(" + candidate.pos().getX() + ","
                        + candidate.pos().getY() + "," + candidate.pos().getZ() + ") before="
                        + candidate.beforeId() + " replaceable=" + candidate.replaceable()
                        + " randomOrdinal=" + candidate.randomOrdinal() + " consumed="
                        + candidate.consumedRandom() + " accepted=" + candidate.accepted());
                lines.add("    " + offlineLabel + "：pos=(" + other.pos().getX() + ","
                        + other.pos().getY() + "," + other.pos().getZ() + ") before=" + other.beforeId()
                        + " replaceable=" + other.replaceable() + " randomOrdinal=" + other.randomOrdinal()
                        + " consumed=" + other.consumedRandom() + " accepted=" + other.accepted());
            }
        }
        lines.add(0, "  候选点逐项比对（" + realLabel + " vs " + offlineLabel + "）：可配对 " + compared
                + " 条 / 左 " + real.size() + " 条 / 右 " + offline.size() + " 条；出现差异 " + diverged
                + " 条；其中「累计消耗随机序号已偏移」" + randomShifted + " 条");
        if (compared == 0) {
            lines.add("  （没有可配对的候选点：两侧的矿脉/候选序列本身就对不上，"
                    + "此时不能声称「随机流漂移」，只能声称「矿脉几何不同」）");
        }
        return lines;
    }

    /** 某条 placed_feature 在某一侧的全部候选（报告用）。 */
    public static List<Candidate> byFeature(boolean offline, String placedFeature) {
        List<Candidate> result = new ArrayList<>();
        for (Candidate candidate : filter(offline)) {
            if (candidate.placedFeature().equals(placedFeature)) {
                result.add(candidate);
            }
        }
        return result;
    }

    private static List<Candidate> filter(boolean offline) {
        List<Candidate> result = new ArrayList<>();
        synchronized (CANDIDATES) {
            for (Candidate candidate : CANDIDATES) {
                if (candidate.offline() == offline) {
                    result.add(candidate);
                }
            }
        }
        return result;
    }

    private static String key(Candidate candidate) {
        return candidate.viewer().x() + "," + candidate.viewer().z() + "|" + candidate.placedFeature() + "|"
                + candidate.veinOrdinal() + "|" + candidate.candidateIndex();
    }

    /** 把候选台账落盘（一次真实请求窗口 + 其后的 Debug 重放写一份）。 */
    public static List<String> render() {
        List<String> lines = new ArrayList<>();
        lines.add("  候选点台账：真实侧 " + filter(false).size() + " 条 / 重放侧 " + filter(true).size()
                + " 条（每条 = 一次 canPlaceOre 判定，含候选序号、写入前方块、是否消耗随机、是否被接受）");
        Set<BlockPos> conflicts = new LinkedHashSet<>();
        for (Candidate candidate : snapshot()) {
            conflicts.add(candidate.pos());
        }
        lines.add("  候选点覆盖位置合计：" + conflicts.size() + " 个");
        return lines;
    }

    /** 目标区块内、真实侧被接受的候选点（= 真实写出来的矿石来源，报告用）。 */
    public static Map<BlockPos, Candidate> acceptedInTarget(ChunkPos target) {
        Map<BlockPos, Candidate> result = new LinkedHashMap<>();
        for (Candidate candidate : filter(false)) {
            if (!candidate.accepted()) {
                continue;
            }
            if ((candidate.pos().getX() >> 4) != target.x() || (candidate.pos().getZ() >> 4) != target.z()) {
                continue;
            }
            result.putIfAbsent(candidate.pos(), candidate);
        }
        return result;
    }
}
