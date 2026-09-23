package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第七轮 · FEATURES 逐格写入 Journal（<b>只取证，不参与预测</b>）。
 *
 * <p><b>它回答什么</b>：用户口径第十节要求「不能只记 applyBiomeDecoration 的批号」——
 * 批号只能说明谁先<b>进入</b> decoration，说明不了并行情况下某个 BlockPos 到底谁<b>最后写</b>。
 * 本台账把 FEATURES 阶段每一次真正落笔的 {@code WorldGenRegion#setBlock} 记下来，
 * 并且给每次写发一个进程内全局自增序号
 * （{@link #GLOBAL_SEQUENCE}，{@code incrementAndGet()}），
 * 它就是「这一次写到底比那一次写早还是晚」的主排序依据（{@code nanoTime} 只作辅助，不作唯一依据）。</p>
 *
 * <p><b>为什么挂在 {@code WorldGenRegion#setBlock}</b>：原版所有生成阶段的方块写入都走它
 * （{@code Feature#setBlock(LevelWriter, BlockPos, BlockState)} → {@code LevelWriter#setBlock}
 * → {@code WorldGenRegion#setBlock(BlockPos, BlockState, int, int)}），而 FEATURES 步骤的
 * {@code WorldGenRegion} 的 {@code getCenter()} 就是<b>当前正在装饰的那个 viewer 区块</b>，
 * 因此「这条写是谁写的」不需要额外推导。</p>
 *
 * <p><b>成本有界</b>：只在 {@link #armed()} 为真、且写入位置落在被跟踪的 3×3 区块内时记账；
 * 其余情况只是一次集合查询。开关默认关闭（{@code -Dyiyiaddon.seedpoc.round7.journal=1} 才开）。</p>
 *
 * <p><b>已知边界（如实登记，不用它冒充全量）</b>：本台账只覆盖 {@code setBlock}；
 * {@code removeBlock}（= 写空气）与直接 {@code LevelChunkSection.setBlockState} 的路径不在其中。
 * 这一点写进报告，不作为「没有冲突」的证据。</p>
 */
public final class FeatureWriteJournal {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 用户口径第十节明确要求的全局写序号（进程内唯一，逐次自增）。 */
    private static final AtomicLong GLOBAL_SEQUENCE = new AtomicLong();

    /** feature 调用序号发放器（每次进入一条 placed_feature 的执行就给一个新号）。 */
    private static final AtomicLong FEATURE_INVOCATIONS = new AtomicLong();

    /** 装饰批号发放器（本台账自己发，口径 = 进入 applyBiomeDecoration 的先后）。 */
    private static final AtomicInteger BATCH_SEQUENCE = new AtomicInteger();

    /** 各 viewer 的批号（只登记被跟踪区块的 viewer，号段紧凑可读）。 */
    private static final Map<Long, Integer> VIEWER_BATCH = Collections.synchronizedMap(new HashMap<>());

    /** 全部写入事件（顺序 = 落账顺序，与 {@link #GLOBAL_SEQUENCE} 同序）。 */
    private static final List<Entry> ENTRIES = Collections.synchronizedList(new ArrayList<>());

    /** 当前线程正在执行的那条 placed_feature（HEAD 设置、RETURN 清除）。 */
    private static final ThreadLocal<FeatureContext> CURRENT_FEATURE = new ThreadLocal<>();

    /** setBlock 的「写入前状态」暂存栈（HEAD push、RETURN pop）。 */
    private static final ThreadLocal<ArrayDeque<String>> PENDING_BEFORE =
            ThreadLocal.withInitial(ArrayDeque::new);

    /** 是否武装（总开关 + 跟踪区域已就绪）。 */
    private static volatile boolean armed;

    /** 被跟踪的区块（packed ChunkPos）——只有落在这些区块里的写入才记账。 */
    private static volatile Set<Long> tracked = Set.of();

    /** 本次实验的目标区块（报告里用来算「是否跨区块」）。 */
    private static volatile ChunkPos target;

    /** 跟踪半径（区块）。 */
    private static volatile int trackedRadius = 1;

    private FeatureWriteJournal() {
    }

    /** 一条写入事件。 */
    public record Entry(long sequence,
                        long nanoTime,
                        String thread,
                        ChunkPos viewer,
                        String placedFeature,
                        String featureType,
                        BlockPos pos,
                        String beforeId,
                        String afterId,
                        boolean crossChunk,
                        int batch,
                        long featureInvocationId,
                        boolean offline,
                        String writePath) {

        /** 这次写是否真的改变了方块（写同值不算竞争）。 */
        public boolean changed() {
            return !beforeId.equals(afterId);
        }

        /** 报告一行（紧凑、可核对）。 */
        String render() {
            return sequence + " " + viewer.x() + " " + viewer.z() + " " + pos.getX() + " " + pos.getY()
                    + " " + pos.getZ() + " " + featureName() + " " + beforeId + " " + afterId + " "
                    + (crossChunk ? 1 : 0) + " " + batch + " " + featureInvocationId + " "
                    + (offline ? 1 : 0) + " " + writePath + " " + thread + " " + nanoTime;
        }

        /** placed_feature 路径 + feature 类型（取不到时如实标注）。 */
        String featureName() {
            String path = placedFeature == null || placedFeature.isEmpty() ? "?" : placedFeature;
            String type = featureType == null || featureType.isEmpty() ? "?" : featureType;
            return path.equals("?") ? type : path + "/" + type;
        }
    }

    /** 写入路径一：原版 {@code WorldGenRegion#setBlock}（绝大多数地物走这条）。 */
    public static final String PATH_SET_BLOCK = "WorldGenRegion.setBlock";

    /**
     * 写入路径二：{@code OreFeature#doPlace} 直接写 section。
     *
     * <p><b>为什么必须单独接这一条</b>：26.1.2 的 {@code OreFeature#doPlace} 字节码里，
     * 候选点通过 {@code canPlaceOre} 之后走的是
     * {@code LevelChunkSection#setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)}
     * ——<b>不经过 {@code WorldGenRegion#setBlock}</b>。第一轮只挂 setBlock 的台账因此
     * 完全看不到矿石写入（实测：目标区块 4224 条写入里一条矿石都没有），
     * 这会直接导致「谁最后写这一格」在矿石上取证失败。</p>
     */
    public static final String PATH_ORE_DIRECT = "OreFeature.doPlace→LevelChunkSection.setBlockState";

    /** 当前 feature 的上下文（{@code offline} 在 HEAD 那一刻判定，直接写 section 的那条路径拿不到世界层）。 */
    private record FeatureContext(ChunkPos viewer, String placedFeature, String featureType,
                                  long invocationId, boolean offline) {
    }

    // ── 武装 / 复位 ────────────────────────────────────────────────────────

    /**
     * 武装台账：把目标区块 ± 半径 圈内的区块设为被跟踪区。
     *
     * <p>必须在任何区块生成之前调用（{@code SeedPocEntry} 挂载时即调用），
     * 否则目标区块可能在实验开始前就生成完了。</p>
     */
    public static void arm(List<ChunkPos> targets, int radius) {
        if (!SeedPocFlags.round7Journal() && !SeedPocFlags.round7Ore()) {
            return;
        }
        Set<Long> region = new LinkedHashSet<>();
        for (ChunkPos center : targets) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    region.add(ChunkPos.pack(center.x() + dx, center.z() + dz));
                }
            }
        }
        tracked = Set.copyOf(region);
        trackedRadius = radius;
        target = targets.isEmpty() ? null : targets.get(0);
        armed = true;
        LOGGER.info("{}：第七轮写入台账已武装，跟踪 {} 个区块（半径 {}），目标 {}",
                SeedPocConstants.LOG_KEY, tracked.size(), radius,
                target == null ? "无" : "(" + target.x() + "," + target.z() + ")");
    }

    /** 是否武装。 */
    public static boolean armed() {
        return armed;
    }

    /** 清空台账（新一次真实请求窗口开始前调用）。 */
    public static void reset() {
        ENTRIES.clear();
        VIEWER_BATCH.clear();
        GLOBAL_SEQUENCE.set(0L);
        FEATURE_INVOCATIONS.set(0L);
        BATCH_SEQUENCE.set(0);
    }

    /**
     * 只清掉重放（离线）侧记录：真实侧证据必须保留下来与重放侧逐项比。
     *
     * <p>一次 case 运行里有两次离线重放（观测顺序 / 反事实顺序），
     * 它们必须各自从干净状态开始，否则两次的写入会混在一起。</p>
     */
    public static void resetOffline() {
        synchronized (ENTRIES) {
            ENTRIES.removeIf(Entry::offline);
        }
    }

    /** 切换当前聚焦的目标区块（跨区块标记与冲突过滤都以它为准）。 */
    public static void focusTarget(ChunkPos target) {
        FeatureWriteJournal.target = target;
    }

    /** 已落账条数。 */
    public static int size() {
        return ENTRIES.size();
    }

    /** 全局写序号当前值（报告用：证明「每次写都自增」）。 */
    public static long sequenceValue() {
        return GLOBAL_SEQUENCE.get();
    }

    // ── 注入点 ─────────────────────────────────────────────────────────────

    /**
     * {@code ChunkGenerator#applyBiomeDecoration} 的 HEAD：给本次装饰发一个批号。
     *
     * <p>批号口径与第五轮完全一致（进入 decoration 的先后），只发给被跟踪区块的 viewer，
     * 号段因此紧凑可读。</p>
     */
    public static void onDecorationHead(WorldGenLevel level, ChunkAccess chunk) {
        if (!armed || chunk == null) {
            return;
        }
        ChunkPos viewer = chunk.getPos();
        if (!tracked.contains(viewer.pack())) {
            return;
        }
        VIEWER_BATCH.put(viewer.pack(), BATCH_SEQUENCE.incrementAndGet());
    }

    /**
     * {@code PlacedFeature#placeWithBiomeCheck} 的 HEAD：登记「当前线程正在执行哪条 placed_feature」。
     *
     * @param level   装饰期世界访问层（真实侧是 WorldGenRegion，离线侧是它的子类）
     * @param origin  本次放置的原点（中心区块最小角），据此反推 viewer 区块
     * @param feature 即将执行的 placed_feature
     */
    public static void onFeatureHead(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        if (!armed) {
            return;
        }
        ChunkPos viewer = new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4);
        if (!tracked.contains(viewer.pack())) {
            return;
        }
        CURRENT_FEATURE.set(new FeatureContext(viewer, GenStageCapture.pathOf(level, feature),
                featureType(level, feature), FEATURE_INVOCATIONS.incrementAndGet(),
                OfflineStageCapture.isOfflineCall(level)));
    }

    /** {@code PlacedFeature#placeWithBiomeCheck} 的 RETURN：清掉当前 feature 上下文。 */
    public static void onFeatureTail() {
        if (!armed) {
            return;
        }
        CURRENT_FEATURE.remove();
    }

    /** {@code WorldGenRegion#setBlock} 的 HEAD：暂存写入前状态（只对被跟踪位置做）。 */
    public static void onBeforeWrite(WorldGenRegion region, BlockPos pos) {
        if (!armed || !trackedPos(pos)) {
            return;
        }
        PENDING_BEFORE.get().push(OreBlockLedger.shortId(region.getBlockState(pos)));
    }

    /**
     * {@code WorldGenRegion#setBlock} 的 RETURN：只有 {@code written == true} 才落账。
     *
     * <p>{@code written == false} 表示这次调用被 {@code ensureCanWrite} 拒了（超出写半径同一圈），
     * 世界状态没有变化，不能算一次写——这类事件如实丢弃，不记成「写了但没生效」。</p>
     */
    public static void onAfterWrite(WorldGenRegion region, BlockPos pos, BlockState afterState, boolean written) {
        if (!armed || !trackedPos(pos)) {
            return;
        }
        ArrayDeque<String> pending = PENDING_BEFORE.get();
        String before = pending.isEmpty() ? "?" : pending.pop();
        if (!written) {
            return;
        }
        ChunkPos viewer = region.getCenter();
        FeatureContext context = CURRENT_FEATURE.get();
        long sequence = GLOBAL_SEQUENCE.incrementAndGet();
        boolean crossChunk = target != null
                && ((pos.getX() >> 4) != target.x() || (pos.getZ() >> 4) != target.z());
        Entry entry = new Entry(sequence, System.nanoTime(), Thread.currentThread().getName(), viewer,
                context == null ? "" : context.placedFeature(),
                context == null ? featureTypeOf(afterState) : context.featureType(),
                pos.immutable(), before, OreBlockLedger.shortId(afterState), crossChunk,
                VIEWER_BATCH.getOrDefault(viewer.pack(), -1),
                context == null ? -1L : context.invocationId(),
                OfflineStageCapture.isOfflineCall(region), PATH_SET_BLOCK);
        ENTRIES.add(entry);
    }

    /**
     * {@code OreFeature#doPlace} 的直接 section 写入（见 {@link #PATH_ORE_DIRECT}）。
     *
     * <p>由 {@code OreFeatureTraceMixin} 在 {@code canPlaceOre} 返回 {@code true} 时调用——
     * 那一刻原版紧接着就是 {@code LevelChunkSection#setBlockState}，没有任何中间判定，
     * 所以「接受 = 真的落笔」。</p>
     *
     * @param pos      候选点
     * @param before   判定那一刻的方块（{@code canPlaceOre} 的入参）
     * @param after    即将写入的方块（{@code TargetBlockState.state}）
     * @param accepted {@code canPlaceOre} 的结果
     */
    public static void onDirectOreWrite(BlockPos pos, BlockState before, BlockState after, boolean accepted) {
        if (!armed || !accepted || !trackedPos(pos)) {
            return;
        }
        FeatureContext context = CURRENT_FEATURE.get();
        if (context == null) {
            // 没有 placed_feature 上下文：无法归因，如实丢弃（不能让一条无来源记录污染「谁最后写」）
            return;
        }
        long sequence = GLOBAL_SEQUENCE.incrementAndGet();
        boolean crossChunk = target != null
                && ((pos.getX() >> 4) != target.x() || (pos.getZ() >> 4) != target.z());
        ENTRIES.add(new Entry(sequence, System.nanoTime(), Thread.currentThread().getName(),
                context.viewer(), context.placedFeature(), context.featureType(), pos.immutable(),
                OreBlockLedger.shortId(before), OreBlockLedger.shortId(after), crossChunk,
                VIEWER_BATCH.getOrDefault(context.viewer().pack(), -1), context.invocationId(),
                context.offline(), PATH_ORE_DIRECT));
    }

    /** 位置是否落在被跟踪区块内。 */
    private static boolean trackedPos(BlockPos pos) {
        return tracked.contains(ChunkPos.pack(pos.getX() >> 4, pos.getZ() >> 4));
    }

    /** 取一条 placed_feature 的 feature 类型名（{@code OreFeature} / {@code ReplaceBlobsFeature} 等）。 */
    private static String featureType(WorldGenLevel level, PlacedFeature feature) {
        try {
            return feature.feature().value().feature().getClass().getSimpleName();
        } catch (Throwable ignored) {
            return "?";
        }
    }

    /** 没有 placed_feature 上下文时的兜底类型名（例如结构自带方块的写入）。 */
    private static String featureTypeOf(BlockState state) {
        return "未在placed_feature上下文中";
    }

    // ── 读数：同格冲突清单 ─────────────────────────────────────────────────

    /**
     * 同格多 writer 冲突清单：目标区块内「被两次以上<b>真正改变了值</b>的写入」碰过的位置。
     *
     * <p>用户口径第十一节明确要求：不能只输出「可能存在冲突」，必须输出
     * {@code #序号 写者 特征 前置状态 → 后置状态 … FINAL 最终状态}，
     * 这样才能直接证明「最后写的是谁」。</p>
     *
     * @param level  真实世界（读最终状态用；离线台账不走这里）
     * @param onlyTarget true = 只列目标区块内的位置
     */
    public static List<String> describeConflicts(ServerLevel level, boolean onlyTarget) {
        List<String> lines = new ArrayList<>();
        List<Entry> real = realEntries();
        Map<BlockPos, List<Entry>> byPos = groupByPos(real);
        int conflicted = 0;
        int matches = 0;
        for (Map.Entry<BlockPos, List<Entry>> entry : byPos.entrySet()) {
            BlockPos pos = entry.getKey();
            if (onlyTarget && target != null
                    && ((pos.getX() >> 4) != target.x() || (pos.getZ() >> 4) != target.z())) {
                continue;
            }
            List<Entry> changed = new ArrayList<>();
            for (Entry item : entry.getValue()) {
                if (item.changed()) {
                    changed.add(item);
                }
            }
            if (changed.size() < 2) {
                continue;
            }
            conflicted++;
            Entry last = changed.get(changed.size() - 1);
            String finalId = OreBlockLedger.shortId(level.getBlockState(pos));
            boolean lastWins = finalId.equals(last.afterId());
            if (lastWins) {
                matches++;
            }
            if (conflicted <= 40) {
                lines.add("  冲突位置 (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")：");
                for (Entry item : changed) {
                    lines.add("    #" + item.sequence() + " viewer(" + item.viewer().x() + ","
                            + item.viewer().z() + ") " + item.featureName() + " 批" + item.batch() + " "
                            + item.beforeId() + " → " + item.afterId()
                            + (item.crossChunk() ? "（跨区块）" : ""));
                }
                lines.add("    FINAL：" + finalId
                        + (lastWins ? "（= 最后一次写 #" + last.sequence() + " 的后置状态，最后写者决定最终方块）"
                        : "（**与最后一次写的后置状态不一致**：最后一次写 #" + last.sequence() + " 写的是 "
                        + last.afterId() + "，说明还有本台账未覆盖的写入路径）"));
            }
        }
        lines.add(0, "  同格冲突位置合计：" + conflicted + " 个（两次以上「真正改变了值」的写入碰过同一格）；"
                + "其中「最终状态 = 最后一次写的后置状态」" + matches + " / " + conflicted
                + (conflicted == 0 ? "（本目标区块内没有出现同格竞争）" : ""));
        if (conflicted > 40) {
            lines.add("  （冲突位置过多，只列前 40 个；完整清单见落盘的 journal 文件）");
        }
        return lines;
    }

    /**
     * 某个位置在真实 FEATURES 里的完整写入链（用户口径第十四节：世界 A / 世界 B 的最后写者对比用）。
     */
    public static List<Entry> chainAt(BlockPos pos) {
        return chainIn(realEntries(), pos);
    }

    /** 某个位置在给定写入事件表里的完整写入链（只取改变了值的那些）。 */
    public static List<Entry> chainIn(List<Entry> entries, BlockPos pos) {
        List<Entry> chain = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.pos().equals(pos) && entry.changed()) {
                chain.add(entry);
            }
        }
        return chain;
    }

    /** 某个位置在给定写入事件表里「最后一次改变值」的那一次写（谁最后写 = 谁决定最终方块）。 */
    public static Entry lastChanged(List<Entry> entries, BlockPos pos) {
        Entry last = null;
        for (Entry entry : entries) {
            if (entry.pos().equals(pos) && entry.changed()) {
                last = entry;
            }
        }
        return last;
    }

    /** 写入链的文本（报告用）。 */
    public static String chainCn(List<Entry> chain) {
        if (chain.isEmpty()) {
            return "（无写入记录）";
        }
        StringBuilder text = new StringBuilder();
        for (Entry entry : chain) {
            if (text.length() > 0) {
                text.append(" → ");
            }
            text.append('#').append(entry.sequence()).append(" viewer(").append(entry.viewer().x()).append(',')
                    .append(entry.viewer().z()).append(") ").append(entry.featureName()).append(' ')
                    .append(entry.beforeId()).append("→").append(entry.afterId());
        }
        return text.toString();
    }

    /** 真实侧（非离线）写入事件。 */
    public static List<Entry> realEntries() {
        List<Entry> result = new ArrayList<>();
        synchronized (ENTRIES) {
            for (Entry entry : ENTRIES) {
                if (!entry.offline()) {
                    result.add(entry);
                }
            }
        }
        return result;
    }

    /** 离线侧（Debug 重放）写入事件。 */
    public static List<Entry> offlineEntries() {
        List<Entry> result = new ArrayList<>();
        synchronized (ENTRIES) {
            for (Entry entry : ENTRIES) {
                if (entry.offline()) {
                    result.add(entry);
                }
            }
        }
        return result;
    }

    /** 目标位置 → 全部写入事件。 */
    private static Map<BlockPos, List<Entry>> groupByPos(List<Entry> source) {
        Map<BlockPos, List<Entry>> byPos = new LinkedHashMap<>();
        for (Entry entry : source) {
            byPos.computeIfAbsent(entry.pos(), key -> new ArrayList<>()).add(entry);
        }
        return byPos;
    }

    /** 各 viewer 的装饰批号（报告用）。 */
    public static Map<ChunkPos, Integer> viewerBatches() {
        Map<ChunkPos, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Long, Integer> entry : VIEWER_BATCH.entrySet()) {
            result.put(new ChunkPos(ChunkPos.getX(entry.getKey()), ChunkPos.getZ(entry.getKey())),
                    entry.getValue());
        }
        return result;
    }

    /** 各 viewer 的写入条数（真实侧，报告用）。 */
    public static Map<ChunkPos, Integer> writesByViewer(boolean onlyTarget) {
        Map<ChunkPos, Integer> counts = new LinkedHashMap<>();
        for (Entry entry : realEntries()) {
            if (onlyTarget && target != null && entry.crossChunk()) {
                continue;
            }
            counts.merge(entry.viewer(), 1, Integer::sum);
        }
        return counts;
    }

    /** 目标区块（报告用）。 */
    public static ChunkPos target() {
        return target;
    }

    /** 跟踪半径。 */
    public static int trackedRadius() {
        return trackedRadius;
    }

    // ── 落盘 / 回读 ────────────────────────────────────────────────────────

    /** 台账文件名（一次真实世界请求窗口一份；标签用来区分场景/重复序号）。 */
    public static Path file(String label, ChunkPos targetChunk) {
        String name = "seedpoc-第七轮journal-" + label + "_" + targetChunk.x() + "_" + targetChunk.z();
        return FabricLoader.getInstance().getGameDir().resolve(name + ".txt");
    }

    /**
     * 把本次台账落盘（供跨世界相关性比对与人工核对）。
     *
     * @param level  真实世界（读每格最终状态）
     * @param label  场景/重复标签
     * @param notes  本次运行的补充说明（世界路径、新鲜度、请求顺序等）
     */
    public static Path dump(ServerLevel level, String label, List<String> notes) {
        ChunkPos targetChunk = target == null ? new ChunkPos(0, 0) : target;
        List<String> lines = new ArrayList<>();
        lines.add("# 种子挖矿 PoC 第七轮 · FEATURES 逐格写入台账（机器可读，请勿手改）");
        lines.add("FORMAT 1");
        lines.add("LABEL " + label);
        lines.add("TARGET " + targetChunk.x() + " " + targetChunk.z());
        lines.add("TRACK_RADIUS " + trackedRadius);
        for (String note : notes) {
            lines.add("NOTE " + note);
        }
        lines.add("GLOBAL_SEQUENCE_END " + GLOBAL_SEQUENCE.get());
        for (Map.Entry<ChunkPos, Integer> entry : viewerBatches().entrySet()) {
            lines.add("BATCH " + entry.getKey().x() + " " + entry.getKey().z() + " " + entry.getValue());
        }
        for (Entry entry : realEntries()) {
            if (inTarget(entry.pos())) {
                lines.add("W " + entry.render());
            }
        }
        lines.add("WCOUNT " + countInTarget(realEntries()));
        lines.add("OCOUNT " + countInTarget(offlineEntries()));
        for (Entry entry : offlineEntries()) {
            if (inTarget(entry.pos())) {
                lines.add("O " + entry.render());
            }
        }
        // 目标区块内每一格的最终状态（谁最后写 = 谁决定最终方块，逐格核对用）
        for (Map.Entry<BlockPos, List<Entry>> entry : groupByPos(realEntries()).entrySet()) {
            BlockPos pos = entry.getKey();
            if (!inTarget(pos)) {
                continue;
            }
            Entry last = null;
            for (Entry item : entry.getValue()) {
                if (item.changed()) {
                    last = item;
                }
            }
            if (last == null) {
                continue;
            }
            lines.add("F " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " "
                    + OreBlockLedger.shortId(level.getBlockState(pos)) + " " + last.sequence() + " "
                    + last.viewer().x() + " " + last.viewer().z() + " " + last.featureName() + " "
                    + last.beforeId() + " " + last.afterId());
        }
        lines.add("END");
        Path path = file(label, targetChunk);
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
            LOGGER.info("{}：第七轮写入台账已落盘 {}", SeedPocConstants.LOG_KEY, path.toAbsolutePath());
        } catch (IOException error) {
            LOGGER.warn("{}：第七轮写入台账落盘失败", SeedPocConstants.LOG_KEY, error);
            return null;
        }
        return path;
    }

    /** 目标区块内的写入条数（报告用：真实侧 / 离线侧分开数）。 */
    public static int writesInTarget(boolean offline) {
        return countInTarget(offline ? offlineEntries() : realEntries());
    }

    /** 位置是否落在目标区块内。 */
    private static boolean inTarget(BlockPos pos) {
        return target != null && (pos.getX() >> 4) == target.x() && (pos.getZ() >> 4) == target.z();
    }

    /** 统计落在目标区块内的写入条数。 */
    private static int countInTarget(List<Entry> entries) {
        int count = 0;
        for (Entry entry : entries) {
            if (inTarget(entry.pos())) {
                count++;
            }
        }
        return count;
    }

    /** 读回一份台账文件里的「最终状态行」：位置 → 最后写者描述。 */
    public static Map<BlockPos, String> loadFinalWriters(Path path) {
        Map<BlockPos, String> result = new LinkedHashMap<>();
        if (!Files.exists(path)) {
            return result;
        }
        try {
            for (String raw : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String line = raw.trim();
                if (!line.startsWith("F ")) {
                    continue;
                }
                String[] parts = line.split(" ");
                if (parts.length < 11) {
                    continue;
                }
                BlockPos pos = new BlockPos(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
                result.put(pos, "最终=" + parts[4] + " ← #" + parts[5] + " viewer(" + parts[6] + ","
                        + parts[7] + ") " + parts[8] + " " + parts[9] + "→" + parts[10]);
            }
        } catch (IOException | RuntimeException error) {
            LOGGER.warn("{}：第七轮台账回读失败 {}", SeedPocConstants.LOG_KEY, path, error);
        }
        return result;
    }

    /** 文件路径里带标签的那一份（跨运行相关性比对用）。 */
    public static Path fileOf(String label, ChunkPos targetChunk) {
        return file(label, targetChunk);
    }
}
