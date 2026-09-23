package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 种子挖矿 PoC 第六轮 · 离线写入归属台账。
 *
 * <p><b>它回答什么</b>：预测集里的每一个钻石坐标，究竟是<b>哪一次 {@code applyBiomeDecoration}</b>
 * （即哪个 viewer）里的<b>哪一条 placed_feature</b> 写进去的、是不是跨区块写入。
 * 这是用户口径第十节要求的「补回位置逐项记录」里唯一无法由集合差集推出的那一列。</p>
 *
 * <p><b>取证方式</b>（两个注入点，都在第六轮新增，既有轮次的探针一行未改）：</p>
 * <ol>
 *     <li><b>窗口</b>：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD/RETURN。窗口用<b>栈</b>保存，
 *         与 {@code OreVeinTrace} 同一个理由（放置过程中可能嵌套进入别的 feature 调用）。
 *         只有「level 是离线 region + 正在记账 + 该 feature 是钻石四条」才把窗口标成记账态；</li>
 *     <li><b>落点</b>：{@code OreFeature#canPlaceOre} 的 RETURN。它返回 true 就等于「这一格真的被写进去了」
 *         （{@code OreFeature#doPlace} 之后立刻 {@code setBlock}），因此只记 {@code passed = true} 的点。</li>
 * </ol>
 *
 * <p><b>为什么台账按「会话」分开、且不在每个目标区块之间清空</b>：第六轮的预测在<b>一份共享离线世界</b>
 * 里跨目标推进，一个 viewer 只会被装饰一次 —— 它可能在第 3 个目标区块的预测里就被装饰掉了，
 * 而它写进第 6 个目标区块的方块要到那时才需要归属。若按「目标区块」清空台账，这类写入就会丢账
 * （第四轮口径复算里那两处「未归属」正是这么来的）。因此：</p>
 * <ul>
 *     <li>台账按<b>会话</b>（= 一份离线世界 = 一个种子）分开存，互不污染；</li>
 *     <li>会话内<b>只累积、不清空</b>，读数时按「坐标是否落在本次目标区块内」过滤；</li>
 *     <li>同一格被多次写入时以最后一次为准（后写覆盖先写，与世界的真实结果一致）。</li>
 * </ul>
 *
 * <p><b>两个必须写明的边界</b>：</p>
 * <ul>
 *     <li>只覆盖 {@code OreFeature} 写出来的钻石。若某个坐标在最终区块里是钻石、但没有任何
 *         {@code OreFeature} 接受记录，说明它来自<b>别的来源</b>（化石 {@code fossil_diamonds} 的
 *         结构处理器、结构自带方块等），报告里单独列成「未归属（非 OreFeature 来源）」，
 *         绝不当成 OreFeature 漏报；</li>
 *     <li>{@code acceptedTotal} 统计的是「所有 OreFeature 的接受点」（含铁/煤/砂砾等），
 *         用来证明探针确实挂上了；真正入台账的只有钻石四条的接受点。</li>
 * </ul>
 *
 * <p>本类只记账，不写世界、不参与预测。</p>
 */
public final class OfflineOreAttribution {

    /**
     * 一次被接受的写入。
     *
     * @param viewer      写入方区块（= 那一刻离线 region 的中心区块）
     * @param featurePath placed_feature 注册表路径（如 {@code ore_diamond_large}）
     */
    public record Write(ChunkPos viewer, String featurePath) {

        /** 对某个目标区块来说，这次写入是否跨区块。 */
        public boolean crossChunk(ChunkPos target) {
            return !viewer.equals(target);
        }
    }

    /** 一个 feature 调用窗口。 */
    private record Frame(boolean offline, boolean record, ChunkPos viewer, String featurePath) {
    }

    /** 一个会话的台账。 */
    private static final class Ledger {
        /** 坐标 → 最后一次接受的写入（全会话累积）。 */
        private final Map<BlockPos, Write> writes = new LinkedHashMap<>();
        private int featureWindows;
        private int diamondWindows;
        private int acceptedDiamond;
        private int acceptedTotal;
        private int rejectedTotal;
        private int maxDepth;
    }

    private static final ThreadLocal<Deque<Frame>> STACK = ThreadLocal.withInitial(ArrayDeque::new);

    /** 会话 id → 台账（会话 = 一份离线世界 = 一个种子）。 */
    private static final Map<Long, Ledger> LEDGERS = new HashMap<>();

    /** 当前正在记账的会话 id；未记账时为 {@link Long#MIN_VALUE}。 */
    private static volatile long activeSession = Long.MIN_VALUE;

    /** 是否正在记账。 */
    private static volatile boolean recording;

    private OfflineOreAttribution() {
    }

    /** 开一个会话（清掉同名会话的旧台账，保证可重复运行）。 */
    static void openSession(long sessionId) {
        LEDGERS.put(sessionId, new Ledger());
        activeSession = Long.MIN_VALUE;
        recording = false;
        STACK.get().clear();
    }

    /** 开始记账（一次预测；同一会话内<b>不清空</b>已有台账）。 */
    static void begin(long sessionId) {
        LEDGERS.computeIfAbsent(sessionId, ignored -> new Ledger());
        STACK.get().clear();
        activeSession = sessionId;
        recording = true;
    }

    /** 结束记账。 */
    static void end() {
        recording = false;
        activeSession = Long.MIN_VALUE;
        STACK.get().clear();
    }

    /**
     * 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD（离线分支）。
     *
     * <p>无论是否记账都要压栈，与 {@link #onFeatureTail()} 严格配对，保证嵌套不破坏外层窗口。</p>
     */
    public static void onFeatureHead(WorldGenLevel level, PlacedFeature feature) {
        Deque<Frame> stack = STACK.get();
        boolean offline = level instanceof OfflineChunkRegion;
        boolean record = false;
        ChunkPos viewer = null;
        String path = "?";
        Ledger ledger = LEDGERS.get(activeSession);
        if (offline && ledger != null) {
            ledger.featureWindows++;
            if (recording && GenStageCapture.isDiamondFeature(level, feature)) {
                record = true;
                viewer = ((OfflineChunkRegion) level).getCenter();
                path = GenStageCapture.pathOf(level, feature);
                ledger.diamondWindows++;
            }
        }
        stack.push(new Frame(offline, record, viewer, path));
        if (ledger != null && stack.size() > ledger.maxDepth) {
            ledger.maxDepth = stack.size();
        }
    }

    /** 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 RETURN（离线分支）。 */
    public static void onFeatureTail() {
        STACK.get().poll();
    }

    /**
     * 注入点：{@code OreFeature#canPlaceOre} 的 RETURN。
     *
     * <p>没有 level 参数，因此用「窗口是否是离线记账窗口」作为唯一判据 —— 窗口只在离线链路里
     * 才会被标成记账态（见 {@link #onFeatureHead}）。</p>
     *
     * @param pos    候选点
     * @param passed 判定结果（true = 这一格随后真的会被写入）
     */
    public static void noteAccept(BlockPos pos, boolean passed) {
        Frame frame = STACK.get().peek();
        Ledger ledger = LEDGERS.get(activeSession);
        if (frame == null || !frame.offline() || ledger == null) {
            return;
        }
        if (!passed) {
            ledger.rejectedTotal++;
            return;
        }
        ledger.acceptedTotal++;
        if (!frame.record()) {
            return;
        }
        ledger.acceptedDiamond++;
        ledger.writes.put(pos.immutable(), new Write(frame.viewer(), frame.featurePath()));
    }

    /** 某个会话里「落在指定区块内」的归属台账（最后一次接受的写入）。 */
    static Map<BlockPos, Write> writesIn(long sessionId, ChunkPos chunk) {
        Ledger ledger = LEDGERS.get(sessionId);
        if (ledger == null) {
            return Map.of();
        }
        Map<BlockPos, Write> filtered = new LinkedHashMap<>();
        for (Map.Entry<BlockPos, Write> entry : ledger.writes.entrySet()) {
            BlockPos pos = entry.getKey();
            if ((pos.getX() >> 4) == chunk.x() && (pos.getZ() >> 4) == chunk.z()) {
                filtered.put(pos, entry.getValue());
            }
        }
        return filtered;
    }

    /** 报告用：某个会话的台账规模与命中情况。 */
    static String describe(long sessionId) {
        Ledger ledger = LEDGERS.get(sessionId);
        if (ledger == null) {
            return "离线归属台账：该会话没有台账（未开始记账）";
        }
        return "离线归属台账（会话 " + sessionId + "）：离线 placed_feature 窗口 " + ledger.featureWindows
                + " 个（其中钻石四条 " + ledger.diamondWindows + " 个）；OreFeature 候选点接受 "
                + ledger.acceptedTotal + " 次 / 拒绝 " + ledger.rejectedTotal + " 次（其中钻石四条接受 "
                + ledger.acceptedDiamond + " 次）；会话累计归属坐标 " + ledger.writes.size()
                + " 个；窗口栈深度峰值 " + ledger.maxDepth;
    }
}
