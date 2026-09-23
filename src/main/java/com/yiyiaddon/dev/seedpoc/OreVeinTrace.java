package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 钻石 {@code OreFeature} 的放置取证台账（第三轮 first-divergence 用）。
 *
 * <p><b>为什么需要它</b>：{@link DiamondFeatureOracle} 只能告诉我们「哪一条钻石 feature 的新增集合
 * 开始不同」，那还是现象。要按用户口径把差异钉到「第一处」，必须把这一条 feature 里
 * <b>每一次真实发生的事件</b>按<b>真实时间顺序</b>记下来，原版与重放逐项对齐：</p>
 * <ul>
 *     <li>{@code BiomeFilter#shouldPlace} 判定（位置 + 结果 + 该点生物群系）——
 *         它不消耗随机数，但判定不通过会把整个位置丢掉（{@code PlacementFilter#getPositions}
 *         只在通过时产出一个位置），于是一处判定不同 = 少放一次矿 = 少消耗一整段随机数，
 *         后续所有矿脉一起漂移；</li>
 *     <li>{@code OreFeature#place} 的 placement origin（{@code PlacementModifier} 链的输出）；
 *         它由 {@code InSquarePlacement} 的两次 {@code nextInt(16)} 与高度修饰符的随机数共同决定；</li>
 *     <li>{@code OreFeature#doPlace} 的矿脉几何参数（{@code place:28-42} 用随机数直接算出）；</li>
 *     <li>{@code OreFeature#canPlaceOre} 的每个候选点（坐标 + 判定那一刻的方块 + 结果）——
 *         它在 {@code 0 < discardChanceOnAirExposure < 1} 时消耗一次 {@code nextFloat()}，
 *         所以候选点状态不同就会改变随机数消耗次数。</li>
 * </ul>
 *
 * <p><b>为什么必须是「一条流水线」而不是两张表</b>：真实执行顺序是
 * 「判定 1 → 放置 1 → 判定 2 → 放置 2 → …」（{@code placeWithContext} 用惰性 {@code Stream}
 * 把修饰符链与 {@code ConfiguredFeature#place} 交错起来）。如果判定与放置分开存两张表，
 * 一旦某一侧出现「判定通过但看不到对应放置」的情况，两张表的下标就不再一一对应，
 * 对齐分析会把<b>下游症状</b>报成第一处分叉。第三轮实测确实出现过这种错位
 * （原版 7 次判定 / 7 次放置，重放 7 次判定 / 3 次放置），所以本轮改为单条时间线。</p>
 *
 * <p><b>嵌套安全</b>：记账窗口用<b>栈</b>保存。原因是回放期间我方调用会让服务端顺带加载/生成区块，
 * 生成过程中又会进入 {@code placeWithBiomeCheck}（真实装饰），若用单个引用保存窗口，
 * 内层一进一出就会把外层的窗口清掉，外层之后的判定与放置会全部丢账。
 * 用栈之后：内层是内层，外层弹回后继续，账目天然正确。</p>
 *
 * <p>本类只记账，不写世界、不参与预测。</p>
 */
public final class OreVeinTrace {

    /**
     * 一次 {@code OreFeature#place} 的两组随机派生量。
     *
     * @param x0     矿脉轴起点（{@code origin.x + sin(dir) * spreadXY}）
     * @param x1     矿脉轴终点
     * @param z0     矿脉轴起点
     * @param z1     矿脉轴终点
     * @param y0     矿脉轴起点高度
     * @param y1     矿脉轴终点高度
     * @param xStart 候选盒起点（{@code origin.x - ceil(spreadXY) - maxRadius}）
     * @param yStart 候选盒起点高度
     * @param zStart 候选盒起点
     * @param sizeXZ 候选盒水平边长
     * @param sizeY  候选盒纵向边长
     */
    public record Vein(double x0, double x1, double z0, double z1, double y0, double y1,
                       int xStart, int yStart, int zStart, int sizeXZ, int sizeY) {
    }

    /**
     * 一次「候选点接受判定」取证：{@code OreFeature#canPlaceOre} 被调用的完整参数与结果。
     *
     * @param pos         候选点
     * @param stateId     判定那一刻该位置的方块短 id
     * @param replaceable 该方块是否属于矿物可替换标签
     * @param passed      这次判定是否通过（= {@code canPlaceOre} 的返回值）
     */
    public record AcceptStep(BlockPos pos, String stateId, boolean replaceable, boolean passed) {
    }

    /** 时间线上的一个事件（按真实执行顺序）。 */
    public sealed interface Event permits BiomeEvent, PlaceEvent {
    }

    /**
     * 一次「生物群系过滤」判定。
     *
     * @param origin  被判定的小方格位置（{@code InSquarePlacement} + 高度修饰符之后的点）
     * @param passed  是否通过（= {@code shouldPlace} 的返回值）
     * @param biomeId 判定时刻该位置解析出来的生物群系
     */
    public record BiomeEvent(BlockPos origin, boolean passed, String biomeId) implements Event {
    }

    /**
     * 一次放置。
     *
     * @param origin  本次 placement origin（PlacementModifier 链的输出）
     * @param vein    矿脉几何参数；{@code null} 表示这次没进 {@code doPlace}
     *                （{@code OreFeature#place:44-52} 的「yStart ≤ 生成期高度」闸门没过）
     * @param accepts 本次放置内部的候选点接受判定序列（顺序 = 原版遍历顺序）
     */
    public record PlaceEvent(BlockPos origin, Vein vein, List<AcceptStep> accepts) implements Event {
    }

    /** 一次放置的临时挂账（矿脉与候选点是后续逐步回填的，因此用可变对象）。 */
    private static final class PendingPlace {
        private final BlockPos origin;
        private Vein vein;
        private final List<AcceptStep> accepts = new ArrayList<>();

        private PendingPlace(BlockPos origin) {
            this.origin = origin;
        }
    }

    /** 时间线节点：判定是定稿的，放置要先挂账再回填。 */
    private sealed interface Node permits BiomeNode, PlaceNode {
    }

    private record BiomeNode(BlockPos origin, boolean passed, String biomeId) implements Node {
    }

    private static final class PlaceNode implements Node {
        private final PendingPlace pending;

        private PlaceNode(BlockPos origin) {
            this.pending = new PendingPlace(origin);
        }
    }

    /** 一次 feature 放置的记账窗口。 */
    private static final class Window {
        /** 是否记账（非钻石四条 / 不在捕获区域 / 未开启实验时为 false）。 */
        private final boolean active;
        private final boolean replay;
        private final ChunkPos viewer;
        private final String featurePath;
        /**
         * 第五轮「薄窗口」：只把被接受的候选点转交 {@link ChunkOrderJournal}，
         * 不建逐事件时间线。理由是顺序实验要测量的就是生成期时序，不能引入大对象分配与长链表写入。
         */
        private final boolean thin;
        /** 该 viewer 本次真实装饰的批号（第五轮溯源需要；常规模式下不使用）。 */
        private final int batch;

        /** 逐事件时间线（薄窗口下恒为空）。 */
        private final List<Node> timeline = new ArrayList<>();

        private Window(boolean active, boolean replay, ChunkPos viewer, String featurePath, int batch,
                       boolean thin) {
            this.active = active;
            this.replay = replay;
            this.viewer = viewer;
            this.featurePath = featurePath;
            this.batch = batch;
            this.thin = thin;
        }

        /** 最近一次放置节点（矿脉与候选点回填目标）。 */
        private PlaceNode lastPlace() {
            for (int index = timeline.size() - 1; index >= 0; index--) {
                if (timeline.get(index) instanceof PlaceNode place) {
                    return place;
                }
            }
            return null;
        }
    }

    /** 台账键：侧 + viewer + feature 路径。 */
    private record Key(boolean replay, long viewer, String featurePath) {
    }

    /**
     * 记账窗口栈（线程局部）。
     *
     * <p>必须用栈：回放期间我方读世界可能触发区块生成，生成过程会再次进入
     * {@code placeWithBiomeCheck}，单引用保存窗口会被内层清掉，导致外层丢账。</p>
     */
    private static final ThreadLocal<Deque<Window>> STACK = ThreadLocal.withInitial(ArrayDeque::new);

    /** 逐（侧, viewer, feature）的时间线。 */
    private static final Map<Key, List<Event>> TIMELINES = Collections.synchronizedMap(new LinkedHashMap<>());

    /** 逐（侧, viewer, feature）被结算过的窗口数（&gt;1 说明同一条 feature 被执行了多次）。 */
    private static final Map<Key, Integer> WINDOWS = Collections.synchronizedMap(new LinkedHashMap<>());

    /** 记账异常计数与首条异常（绝不上抛）。 */
    private static int failures;
    private static String firstFailure = "";

    /** 各注入点的「被调用总次数」——与是否落账无关，只要探针生效就会增长。 */
    private static int placeCalls;
    private static int veinCalls;
    private static int acceptCalls;
    private static int biomeCalls;

    /** 窗口统计（报告用）：打开过的窗口数、其中记账的、嵌套深度峰值。 */
    private static int windowsOpened;
    private static int windowsActive;
    private static int maxDepth;

    private OreVeinTrace() {
    }

    /**
     * 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD。
     *
     * <p>无条件压栈（记账窗口或不记账窗口），与 {@link #endFeature()} 的弹栈严格配对，
     * 这样内层嵌套不会破坏外层的账。</p>
     */
    public static void beginFeature(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        Deque<Window> stack = STACK.get();
        Window window = new Window(false, false, null, "?", -1, false);
        try {
            // 第五轮顺序实验：不建时间线，只把被接受的候选点转交 ChunkOrderJournal（薄窗口）
            boolean thin = GenStageCapture.isOrderMode();
            boolean recording = thin ? ChunkOrderJournal.active() : GenStageCapture.captureArmed();
            if (recording && GenStageCapture.isDiamondFeature(level, feature)) {
                ChunkPos viewer = new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4);
                if (GenStageCapture.containsViewer(viewer)) {
                    window = new Window(true, !thin && GenStageCapture.isReplaying(), viewer,
                            GenStageCapture.pathOf(level, feature),
                            GenStageCapture.currentPass(viewer.pack()), thin);
                    if (!thin) {
                        windowsActive++;
                    }
                }
            }
        } catch (Throwable error) {
            recordFailure("vein-begin", origin, error);
            window = new Window(false, false, null, "?", -1, false);
        }
        windowsOpened++;
        stack.push(window);
        if (stack.size() > maxDepth) {
            maxDepth = stack.size();
        }
    }

    /** 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 RETURN（弹栈并落台账）。 */
    public static void endFeature() {
        Deque<Window> stack = STACK.get();
        Window window = stack.poll();
        if (window == null || !window.active || window.timeline.isEmpty()) {
            return;
        }
        List<Event> batch = new ArrayList<>(window.timeline.size());
        for (Node node : window.timeline) {
            if (node instanceof BiomeNode biome) {
                batch.add(new BiomeEvent(biome.origin(), biome.passed(), biome.biomeId()));
            } else if (node instanceof PlaceNode place) {
                PendingPlace pending = place.pending;
                batch.add(new PlaceEvent(pending.origin, pending.vein, List.copyOf(pending.accepts)));
            }
        }
        Key key = new Key(window.replay, window.viewer.pack(), window.featurePath);
        List<Event> target = TIMELINES.computeIfAbsent(key,
                ignored -> Collections.synchronizedList(new ArrayList<>()));
        synchronized (target) {
            target.addAll(batch);
        }
        WINDOWS.merge(key, 1, Integer::sum);
    }

    /** 注入点：{@code BiomeFilter#shouldPlace} 的 RETURN（生物群系过滤判定）。 */
    public static void noteBiomeCheck(BlockPos origin, boolean passed, String biomeId) {
        biomeCalls++;
        Window window = current();
        if (window == null || window.thin) {
            return;
        }
        window.timeline.add(new BiomeNode(origin.immutable(), passed, biomeId));
    }

    /** 注入点：{@code OreFeature#place} 的 HEAD。 */
    public static void notePlace(BlockPos origin) {
        placeCalls++;
        Window window = current();
        if (window == null || window.thin) {
            return;
        }
        window.timeline.add(new PlaceNode(origin.immutable()));
    }

    /** 注入点：{@code OreFeature#doPlace} 的 HEAD（把几何参数回填到最近一条放置上）。 */
    public static void noteVein(Vein vein) {
        veinCalls++;
        Window window = current();
        if (window == null || window.thin) {
            return;
        }
        PlaceNode place = window.lastPlace();
        if (place != null) {
            place.pending.vein = vein;
        }
    }

    /** 注入点：{@code OreFeature#canPlaceOre} 的 RETURN（候选点接受判定）。 */
    public static void noteAccept(BlockPos pos, BlockState state, boolean passed) {
        acceptCalls++;
        Window window = current();
        if (window == null) {
            return;
        }
        if (window.thin) {
            // 第五轮：只把「被接受（= 真的写进了那一格）」的候选点转交薄台账
            ChunkOrderJournal.note(window.viewer, window.featurePath, window.batch, pos, state, passed);
            return;
        }
        PlaceNode place = window.lastPlace();
        if (place == null) {
            return;
        }
        place.pending.accepts.add(new AcceptStep(pos.immutable(), OreBlockLedger.shortId(state),
                OreBlockLedger.isReplaceable(state), passed));
    }

    /** 当前正在记账的窗口；不在记账中返回 null。 */
    private static Window current() {
        Window window = STACK.get().peek();
        return window != null && window.active ? window : null;
    }

    /** 取某侧某 viewer 某条 feature 的时间线（真实执行顺序）。 */
    public static List<Event> timeline(boolean replay, ChunkPos viewer, String featurePath) {
        List<Event> found = TIMELINES.get(new Key(replay, viewer.pack(), featurePath));
        if (found == null) {
            return List.of();
        }
        synchronized (found) {
            return List.copyOf(found);
        }
    }

    /** 取某侧某 viewer 某条 feature 被结算过的窗口数（&gt;1 说明执行了多次）。 */
    public static int windowCount(boolean replay, ChunkPos viewer, String featurePath) {
        return WINDOWS.getOrDefault(new Key(replay, viewer.pack(), featurePath), 0);
    }

    /**
     * 清空「回放侧」的全部台账，开始一次新的回放会话。
     *
     * <p>实验里同一个 viewer 会被回放多次（装置自证对照会重放整步、第一阶段又会重放钻石四条），
     * 不按会话清账的话第二次回放的记录会接在第一次后面，对齐分析会被记账缺陷带偏。
     * 真实装饰侧不清：那才是原版唯一一次执行留下的证据。</p>
     */
    public static void beginReplaySession() {
        TIMELINES.keySet().removeIf(Key::replay);
        WINDOWS.keySet().removeIf(Key::replay);
        STACK.get().clear();
    }

    /** 台账里出现过的全部（侧, viewer, feature）组合数（报告用）。 */
    public static int keyCount() {
        return TIMELINES.size();
    }

    /** 报告用：四个注入点各自被调用的总次数。 */
    public static String callsCn() {
        return "noteBiomeCheck " + biomeCalls + " 次 ／ notePlace " + placeCalls + " 次 ／ noteVein "
                + veinCalls + " 次 ／ noteAccept " + acceptCalls + " 次";
    }

    /** 报告用：记账窗口统计（未结算记账窗口不为 0 才说明四个注入点真的被记上了）。 */
    public static String windowsCn() {
        return "窗口打开 " + windowsOpened + " 个（其中记账 " + windowsActive + " 个）；窗口栈深度峰值 " + maxDepth;
    }

    /** 报告用：某侧某 viewer 某条 feature 的取证条数（时间线长度 + 结算过的窗口数）。 */
    public static String ledgerCn(boolean replay, ChunkPos viewer, String featurePath) {
        return "事件 " + timeline(replay, viewer, featurePath).size() + " 条 ／ 窗口 "
                + windowCount(replay, viewer, featurePath) + " 个";
    }

    /** 报告用：异常。 */
    public static String failuresCn() {
        return failures == 0 ? "无" : failures + " 次，首个：" + firstFailure;
    }

    private static void recordFailure(String stage, BlockPos origin, Throwable error) {
        failures++;
        if (firstFailure.isEmpty()) {
            firstFailure = stage + "@" + origin.getX() + "," + origin.getY() + "," + origin.getZ()
                    + " → " + error.getClass().getSimpleName() + ": " + error.getMessage();
        }
    }
}
