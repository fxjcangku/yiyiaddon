package com.yiyiaddon.seed.runtime;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>预测覆盖调度器</b>（正式化第五阶段 233）。
 *
 * <p><b>它做什么</b>：以玩家当前区块为中心，决定「附近哪些目标区块需要送 Worker 预测」，
 * 并按<b>近 → 远</b>的顺序逐个提交。它<b>不</b>知道钻石在哪里、不读任何世界数据、不解析种子，
 * 更不会自己算矿 —— 预测只发生在 Worker 里（口径第七、十七节）。</p>
 *
 * <p><b>为什么要有它</b>：口径第二十一节明令禁止「一次性 49 个 Future 同时跑」。
 * Worker 侧的 worldgen 是单任务串行的（232 已定案），客户端硬塞 49 个请求只会把队列堆满、
 * 结果一起到达、界面一起跳。这里因此固定：</p>
 * <ul>
 *     <li><b>同时最多 1 个在 Worker 上跑</b>（{@link #active}）；</li>
 *     <li><b>有限 pending 队列</b>（{@link #MAX_PENDING}）；</li>
 *     <li>玩家跨区块时<b>重算目标集合</b>：已经在 Repository 里的直接复用、已经不在新范围内的
 *         pending 直接丢弃（口径第二十二节）；</li>
 *     <li>失败过的目标区块记进 {@link #failed}，同一会话内不再重试，避免失败目标反复占住唯一的执行位。</li>
 * </ul>
 *
 * <p><b>顺序</b>（口径第二十节）：切比雪夫距离由近到远；同距离再按欧氏距离，最后按 x/z 升序 ——
 * 完全确定，不含任何随机。玩家脚下与最近一圈永远最先出结果。</p>
 *
 * <p><b>线程模型</b>：只在客户端主线程调用（提交、玩家跨区块、结果回投都在这条线程上）。</p>
 */
public final class SeedPredictionCoverageController {

    /** 覆盖半径下限（区块）。 */
    public static final int RADIUS_MIN = 1;
    /** 覆盖半径上限（区块）：口径第十九节明确「不要默认 8」，允许范围 1~6。 */
    public static final int RADIUS_MAX = 6;
    /** 出厂默认覆盖半径：3（7×7 = 最多 49 个目标区块）。 */
    public static final int RADIUS_DEFAULT = 3;

    /**
     * pending 队列上限。
     *
     * <p>半径 6 时目标集合最多 13×13 = 169 个，远超「排队有意义」的量；队列只保留最近的一批，
     * 前面跑完再从剩余目标里按近→远补 —— 玩家回头时也能立刻出结果。</p>
     */
    private static final int MAX_PENDING = 64;

    /** 排序器：切比雪夫距离 → 欧氏距离 → x → z（完全确定，不含随机）。 */
    private static final Comparator<int[]> NEAREST_FIRST = Comparator
            .comparingInt((int[] d) -> chebyshev(d[0], d[1]))
            .thenComparingInt(d -> d[0] * d[0] + d[1] * d[1])
            .thenComparingInt(d -> d[0])
            .thenComparingInt(d -> d[1]);

    /** 待提交队列（近→远；元素是相对中心的偏移，中心一变整体重算）。 */
    private final ArrayDeque<int[]> pending = new ArrayDeque<>();

    /** pending + active 的区块键（去重用；区块键为 {@link ChunkPos#pack(int, int)}）。 */
    private final Set<Long> inFlight = new HashSet<>();

    /** 本会话内已失败的目标区块（不再重试）。 */
    private final Set<Long> failed = new HashSet<>();

    /** 排列好的目标顺序（每次重算覆盖时生成一次，不在 tick 里重算）。 */
    private List<int[]> desired = List.of();

    /** 当前覆盖中心（玩家区块）；{@code null} = 尚未启动。 */
    private ChunkPos center;

    /** 当前生效的覆盖半径。 */
    private int radius = RADIUS_DEFAULT;

    /** 上一次生成目标顺序时用的半径（半径一变必须重算目标顺序）。 */
    private int plannedRadius = -1;

    /** 正在 Worker 上跑的目标区块；{@code null} = 空闲。 */
    private ChunkPos active;

    /** 诊断计数。 */
    private int submittedCount;
    private int completedCount;
    private int droppedPendingCount;

    // ── 配置 ──

    /** 覆盖半径（取值被夹在 {@link #RADIUS_MIN} ~ {@link #RADIUS_MAX}）。 */
    public int radius() {
        return radius;
    }

    /**
     * 设置覆盖半径。
     *
     * @return 半径是否真的变了（变了要重算目标顺序）
     */
    public boolean setRadius(int value) {
        int clamped = clampRadius(value);
        if (clamped == radius) {
            return false;
        }
        radius = clamped;
        return true;
    }

    /** 把任意输入夹进允许区间。 */
    public static int clampRadius(int value) {
        return Math.max(RADIUS_MIN, Math.min(RADIUS_MAX, value));
    }

    // ── 调度 ──

    /**
     * 每刻推进一次调度。
     *
     * <p>调用方（服务层）只在「覆盖应当工作」时才调它（口径第十八节）；本方法自身不再判条件。</p>
     *
     * @param playerChunk 玩家当前区块
     * @param repository  预测缓存（已在缓存里的目标区块直接复用，不重复提交）
     * @param submit      真正把目标区块交给 Worker 的回调（由服务层实现，内部走后台单线程）
     */
    public void tick(ChunkPos playerChunk, SeedPredictionRepository repository, Consumer<ChunkPos> submit) {
        if (playerChunk == null) {
            return;
        }
        if (center == null || !center.equals(playerChunk) || plannedRadius != radius) {
            replan(playerChunk, repository);
        }
        if (active == null) {
            submitNext(repository, submit);
        }
    }

    /** 玩家跨区块（或半径变化）：重算目标顺序，丢掉已经不在范围内的 pending。 */
    private void replan(ChunkPos playerChunk, SeedPredictionRepository repository) {
        center = playerChunk;
        plannedRadius = radius;
        desired = orderCoverage(radius);
        // 已经不在新范围内的排队项直接丢弃（口径第二十二节）；已在 Worker 上跑的那个不动，
        // 它的结果仍然合法（身份没变就必须收录，见服务层的过期守卫）。
        int before = pending.size();
        pending.removeIf(offset -> chebyshev(offset[0], offset[1]) > radius);
        inFlight.clear();
        for (int[] offset : pending) {
            inFlight.add(ChunkPos.pack(playerChunk.x() + offset[0], playerChunk.z() + offset[1]));
        }
        if (active != null) {
            inFlight.add(active.pack());
        }
        droppedPendingCount += before - pending.size();
        fillPending(repository);
    }

    /** 从目标顺序里补足 pending（跳过：已在缓存 / 已在飞行中 / 已失败）。 */
    private void fillPending(SeedPredictionRepository repository) {
        if (center == null) {
            return;
        }
        for (int[] offset : desired) {
            if (pending.size() >= MAX_PENDING) {
                return;
            }
            ChunkPos chunk = new ChunkPos(center.x() + offset[0], center.z() + offset[1]);
            long key = chunk.pack();
            if (inFlight.contains(key) || failed.contains(key) || repository.contains(chunk)) {
                continue;
            }
            pending.addLast(offset);
            inFlight.add(key);
        }
    }

    /** 提交下一个排队目标。 */
    private void submitNext(SeedPredictionRepository repository, Consumer<ChunkPos> submit) {
        if (center == null || submit == null) {
            return;
        }
        if (pending.isEmpty()) {
            fillPending(repository);
        }
        int[] offset = pending.pollFirst();
        if (offset == null) {
            return;
        }
        ChunkPos chunk = new ChunkPos(center.x() + offset[0], center.z() + offset[1]);
        active = chunk;
        inFlight.add(chunk.pack());
        submittedCount++;
        submit.accept(chunk);
    }

    // ── 结果回收 ──

    /**
     * Worker 返回了一个目标区块的预测。
     *
     * @param target   目标区块
     * @param accepted 结果是否被正式收录（身份已变 / 种子不符时为 false）
     */
    public void onCompleted(ChunkPos target, boolean accepted) {
        if (target != null && target.equals(active)) {
            active = null;
            completedCount++;
        }
        if (target != null) {
            inFlight.remove(target.pack());
            if (!accepted) {
                // 结果被丢弃（身份变了 / 写入被拒）：本会话内不再重试，避免反复占住唯一执行位
                failed.add(target.pack());
            }
        }
    }

    /** Worker 侧失败（异常 / 超时 / 进程不可用）：记下并不再重试。 */
    public void onFailed(ChunkPos target) {
        if (target != null && target.equals(active)) {
            active = null;
        }
        if (target != null) {
            inFlight.remove(target.pack());
            failed.add(target.pack());
        }
    }

    /** 会话失效（换种子 / 换维度 / 换服 / 退世界 / 关功能）：清空全部调度状态。 */
    public void reset() {
        pending.clear();
        inFlight.clear();
        failed.clear();
        desired = List.of();
        center = null;
        active = null;
        plannedRadius = -1;
    }

    // ── 读数（界面与报告） ──

    /** 正在 Worker 上跑的目标区块；空闲时为 {@code null}。 */
    public ChunkPos activeChunk() {
        return active;
    }

    /** 正在 Worker 上跑的目标区块显示文本（{@code 12, -3}）；空闲为「—」。 */
    public String activeChunkCn() {
        return active == null ? "—" : active.x() + ", " + active.z();
    }

    /** 排队中的目标数。 */
    public int pendingCount() {
        return pending.size();
    }

    /** 本次覆盖应当预测的目标区块总数（{@code (2r+1)²}）。 */
    public int desiredCount() {
        return (radius * 2 + 1) * (radius * 2 + 1);
    }

    /** 覆盖中心（玩家区块）；未启动为 {@code null}。 */
    public ChunkPos center() {
        return center;
    }

    /** 已经拿到正式结果、落在当前覆盖范围内的目标区块数（「已预测 X / Y」的 X）。 */
    public int predictedInCoverage(SeedPredictionRepository repository) {
        if (center == null) {
            return 0;
        }
        int count = 0;
        for (ChunkPos chunk : repository.chunks()) {
            if (chebyshev(chunk.x() - center.x(), chunk.z() - center.z()) <= radius) {
                count++;
            }
        }
        return count;
    }

    /** 排队上限（界面说明用）。 */
    public int pendingLimit() {
        return MAX_PENDING;
    }

    /** 累计提交数（诊断）。 */
    public int submittedCount() {
        return submittedCount;
    }

    /** 累计完成数（诊断）。 */
    public int completedCount() {
        return completedCount;
    }

    /** 因玩家移动被丢弃的排队项总数（诊断：报告里的「移动不叠任务」证据）。 */
    public int droppedPendingCount() {
        return droppedPendingCount;
    }

    // ── 内部 ──

    /** 生成近 → 远的覆盖偏移序列（含中心自身）。 */
    private List<int[]> orderCoverage(int coverageRadius) {
        List<int[]> offsets = new ArrayList<>();
        for (int dx = -coverageRadius; dx <= coverageRadius; dx++) {
            for (int dz = -coverageRadius; dz <= coverageRadius; dz++) {
                offsets.add(new int[]{dx, dz});
            }
        }
        offsets.sort(NEAREST_FIRST);
        return offsets;
    }

    private static int chebyshev(int dx, int dz) {
        return Math.max(Math.abs(dx), Math.abs(dz));
    }

    /** 一行诊断（日志 / 报告用）。 */
    public String describeCn() {
        return "覆盖半径 " + radius + "（目标 " + desiredCount() + " 个），中心 "
                + (center == null ? "未启动" : center.x() + "," + center.z())
                + "，在跑 " + activeChunkCn() + "，排队 " + pending.size()
                + "，累计提交 " + submittedCount + " / 完成 " + completedCount
                + "（移动丢弃排队 " + droppedPendingCount + "，失败不再重试 " + failed.size() + "）";
    }
}
