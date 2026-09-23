package com.yiyiaddon.seed.runtime;

import com.yiyiaddon.seed.model.OreType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>预测覆盖调度器</b>（正式化第五阶段 233；第八阶段 236 扩成多矿物；26.2 语义移植）。
 *
 * <p><b>它做什么</b>：以玩家当前区块为中心，决定「附近哪些目标（区块 × 矿物）需要送 Worker 预测」，
 * 并按<b>近 → 远</b>的顺序逐个提交。它<b>不</b>知道矿在哪里、不读任何世界数据、不解析种子，
 * 更不会自己算矿 —— 预测只发生在 Worker 里。</p>
 *
 * <p><b>为什么要有它</b>：禁止「一次性几十个 Future 同时跑」。Worker 侧的 worldgen 是单任务串行的，
 * 客户端硬塞请求只会把队列堆满、结果一起到达、界面一起跳。这里因此固定：</p>
 * <ul>
 *     <li><b>同时最多 1 个在 Worker 上跑</b>（{@link #active}）；</li>
 *     <li><b>有限 pending 队列</b>（{@link #MAX_PENDING}）；</li>
 *     <li>玩家跨区块 / 改矿物集合 / 改半径时<b>重算目标集合</b>：已经在 Repository 里的直接复用、
 *         已经不在新范围内的 pending 直接丢弃；</li>
 *     <li>失败过的目标记进 {@link #failed}，同一会话内不再重试，避免失败目标反复占住唯一的执行位。</li>
 * </ul>
 *
 * <p><b>236 的调度单位</b>：{@link Task} = （矿物, 目标区块）。同一区块的多种矿物是<b>先后</b>提交的
 * （不是并发），顺序在「距离」之后按矿物声明序排定，因此调度完全确定、可复现。</p>
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
     * <p>半径 6 时目标集合最多 13×13 = 169 个区块，再乘上所选矿物数会更大；
     * 队列只保留最近的一批，前面跑完再从剩余目标里按近→远补 —— 玩家回头时也能立刻出结果。</p>
     */
    private static final int MAX_PENDING = 64;

    /**
     * 一个调度单位：<b>（矿物, 目标区块）</b>。
     *
     * <p>用 record 是为了拿到值语义的 equals/hashCode：去重集合、失败集合都直接放它，
     * 不需要再拼字符串键。</p>
     */
    public record Task(OreType oreType, ChunkPos chunk) {

        /** 一行中文摘要（日志 / 界面用）。 */
        public String describeCn() {
            return oreType.displayNameCn() + "(" + chunk.x() + "," + chunk.z() + ")";
        }
    }

    /** 排序器：切比雪夫距离 → 欧氏距离 → 矿物声明序 → x → z（完全确定，不含随机）。 */
    private static Comparator<Task> nearestFirst(ChunkPos origin) {
        return Comparator
                .comparingInt((Task task) -> chebyshev(task.chunk().x() - origin.x(), task.chunk().z() - origin.z()))
                .thenComparingInt(task -> task.chunk().distanceSquared(origin))
                .thenComparingInt(task -> task.oreType().ordinal())
                .thenComparingInt(task -> task.chunk().x())
                .thenComparingInt(task -> task.chunk().z());
    }

    /** 待提交队列（近→远；元素是（矿物, 区块），中心 / 半径 / 矿物集合一变整体重算）。 */
    private final ArrayDeque<Task> pending = new ArrayDeque<>();

    /** pending + active 的任务（去重用）。 */
    private final Set<Task> inFlight = new HashSet<>();

    /** 本会话内已失败的任务（不再重试）。 */
    private final Set<Task> failed = new HashSet<>();

    /** 排列好的目标顺序（每次重算覆盖时生成一次，不在 tick 里重算）。 */
    private List<Task> desired = List.of();

    /** 当前覆盖中心（玩家区块）；{@code null} = 尚未启动。 */
    private ChunkPos center;

    /** 当前生效的覆盖半径。 */
    private int radius = RADIUS_DEFAULT;

    /** 上一次规划时用的半径（半径一变必须重算目标顺序）。 */
    private int plannedRadius = -1;

    /** 上一次规划时用的矿物集合（集合一变必须重算目标顺序）。 */
    private List<OreType> plannedOres = List.of();

    /** 正在 Worker 上跑的任务；{@code null} = 空闲。 */
    private Task active;

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
     * <p>调用方（服务层）只在「覆盖应当工作」时才调它；本方法自身不再判条件。</p>
     *
     * @param playerChunk 玩家当前区块
     * @param dimensionId 当前维度标识（缓存键的一部分，用于判断「这个任务已经有结果了」）
     * @param oreTypes    本次要预测的矿物集合（顺序即同距离下的优先级）
     * @param repository  预测缓存（已在缓存里的任务直接复用，不重复提交）
     * @param submit      真正把任务交给 Worker 的回调（由服务层实现，内部走后台单线程）
     */
    public void tick(ChunkPos playerChunk, String dimensionId, List<OreType> oreTypes,
                     SeedPredictionRepository repository, Consumer<Task> submit) {
        if (playerChunk == null || oreTypes == null || oreTypes.isEmpty() || dimensionId == null) {
            return;
        }
        if (center == null || !center.equals(playerChunk) || plannedRadius != radius
                || !plannedOres.equals(oreTypes)) {
            replan(playerChunk, oreTypes);
        }
        if (active == null) {
            submitNext(dimensionId, repository, submit);
        }
    }

    /** 玩家跨区块 / 改半径 / 改矿物集合：重算目标顺序，丢掉已经不在范围内的 pending。 */
    private void replan(ChunkPos playerChunk, List<OreType> oreTypes) {
        center = playerChunk;
        plannedRadius = radius;
        plannedOres = List.copyOf(oreTypes);
        desired = orderCoverage(oreTypes);
        // 已经不在新范围内的排队项直接丢弃；已在 Worker 上跑的那个不动，
        // 它的结果仍然合法（身份没变就必须收录，见服务层的过期守卫）。
        int before = pending.size();
        pending.removeIf(task -> chebyshev(task.chunk().x() - center.x(), task.chunk().z() - center.z()) > radius
                || !oreTypes.contains(task.oreType()));
        inFlight.clear();
        for (Task task : pending) {
            inFlight.add(task);
        }
        if (active != null) {
            inFlight.add(active);
        }
        droppedPendingCount += before - pending.size();
    }

    /** 从目标顺序里补足 pending（跳过：已在缓存 / 已在飞行中 / 已失败）。 */
    private void fillPending(String dimensionId, SeedPredictionRepository repository) {
        if (center == null) {
            return;
        }
        for (Task task : desired) {
            if (pending.size() >= MAX_PENDING) {
                return;
            }
            if (inFlight.contains(task) || failed.contains(task)
                    || repository.contains(TargetKey.of(dimensionId, task.oreType(), task.chunk()))) {
                continue;
            }
            pending.addLast(task);
            inFlight.add(task);
        }
    }

    /** 提交下一个排队任务。 */
    private void submitNext(String dimensionId, SeedPredictionRepository repository, Consumer<Task> submit) {
        if (center == null || submit == null) {
            return;
        }
        if (pending.isEmpty()) {
            fillPending(dimensionId, repository);
        }
        Task task = pending.pollFirst();
        if (task == null) {
            return;
        }
        active = task;
        inFlight.add(task);
        submittedCount++;
        submit.accept(task);
    }

    // ── 结果回收 ──

    /**
     * Worker 返回了一个任务的预测。
     *
     * @param task     任务（矿物 + 区块）
     * @param accepted 结果是否被正式收录（身份已变 / 种子或维度不符时为 false）
     */
    public void onCompleted(Task task, boolean accepted) {
        if (task != null && task.equals(active)) {
            active = null;
            completedCount++;
        }
        if (task != null) {
            inFlight.remove(task);
            if (!accepted) {
                // 结果被丢弃（身份变了 / 写入被拒）：本会话内不再重试，避免反复占住唯一执行位
                failed.add(task);
            }
        }
    }

    /** Worker 侧失败（异常 / 超时 / 进程不可用）：记下并不再重试。 */
    public void onFailed(Task task) {
        if (task != null && task.equals(active)) {
            active = null;
        }
        if (task != null) {
            inFlight.remove(task);
            failed.add(task);
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
        plannedOres = List.of();
    }

    // ── 读数（界面与报告） ──

    /** 正在 Worker 上跑的任务；空闲时为 {@code null}。 */
    public Task activeTask() {
        return active;
    }

    /** 正在 Worker 上跑的任务显示文本；空闲为「—」。 */
    public String activeTaskCn() {
        return active == null ? "—" : active.describeCn();
    }

    /** 排队中的任务数。 */
    public int pendingCount() {
        return pending.size();
    }

    /** 本次覆盖应当预测的任务总数（{@code (2r+1)² × 矿物数}）。 */
    public int desiredCount() {
        return (radius * 2 + 1) * (radius * 2 + 1) * Math.max(1, plannedOres.size());
    }

    /** 覆盖中心（玩家区块）；未启动为 {@code null}。 */
    public ChunkPos center() {
        return center;
    }

    /**
     * 已经拿到正式结果、落在当前覆盖范围内的<b>任务数</b>（一个「区块 × 矿物」算一个，
     * 与 {@link #desiredCount()} 同一口径，因此界面上的 {@code X / Y} 可直接相除）。
     */
    public int predictedInCoverage(SeedPredictionRepository repository, List<OreType> oreTypes) {
        if (center == null || repository == null) {
            return 0;
        }
        return repository.countInCoverage(center, radius, new LinkedHashSet<>(oreTypes));
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

    /** 因玩家移动 / 改矿物被丢弃的排队项总数（诊断：报告里的「移动不叠任务」证据）。 */
    public int droppedPendingCount() {
        return droppedPendingCount;
    }

    // ── 内部 ──

    /** 生成近 → 远的覆盖任务序列（含中心自身；同一区块内按矿物声明序）。 */
    private List<Task> orderCoverage(List<OreType> oreTypes) {
        ChunkPos origin = center;
        if (origin == null) {
            return List.of();
        }
        List<Task> tasks = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos chunk = new ChunkPos(origin.x() + dx, origin.z() + dz);
                for (OreType oreType : oreTypes) {
                    tasks.add(new Task(oreType, chunk));
                }
            }
        }
        tasks.sort(nearestFirst(origin));
        return tasks;
    }

    private static int chebyshev(int dx, int dz) {
        return Math.max(Math.abs(dx), Math.abs(dz));
    }

    /** 一行诊断（日志 / 报告用）。 */
    public String describeCn() {
        return "覆盖半径 " + radius + "（区块目标 " + desired.size() + " 个任务，矿物 "
                + plannedOres.size() + " 种），中心 "
                + (center == null ? "未启动" : center.x() + "," + center.z())
                + "，在跑 " + activeTaskCn() + "，排队 " + pending.size()
                + "，累计提交 " + submittedCount + " / 完成 " + completedCount
                + "（移动丢弃排队 " + droppedPendingCount + "，失败不再重试 " + failed.size() + "）";
    }
}
