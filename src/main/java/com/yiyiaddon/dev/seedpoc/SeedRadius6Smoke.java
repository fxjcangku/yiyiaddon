package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.render.SeedRenderFrameProfiler;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第六阶段（234）· <b>半径 6 压力烟测（仅开发）</b>。
 *
 * <p><b>它回答什么</b>（口径第五十六、五十七节）：默认范围 3（49 个目标区块）已经会在渲染快照里堆出上千条候选，
 * 那把范围开到允许的最大值 6（13 × 13 = 169 个目标区块）会怎样？本装置只做<b>观察与记录</b>，
 * 不判定通过与否，不改默认值，也不为了跑这个测试去改渲染器：</p>
 * <ul>
 *     <li>覆盖队列能不能铺满（铺开耗时、是否卡住不动）；</li>
 *     <li>预测缓存上限 256 对 169 个目标区块够不够；</li>
 *     <li>计算器（Worker 进程）是否持续工作、有没有中途罢工 / 报错；</li>
 *     <li>客户端有没有明显冻结（按相邻两次客户端刻的墙钟间隔统计卡顿次数与最大值）；</li>
 *     <li>渲染快照规模（候选数 / 渲染条目数）与帧率；</li>
 *     <li>内存是否失控（客户端堆使用量；Worker 侧 RSS 由外部脚本按本装置打出的 PID 采样）。</li>
 * </ul>
 *
 * <p><b>前置条件</b>：客户端连着种子 {@code 20260922} 的专用服务器（{@code runSeedWorkerServer}，端口 25565），
 * 账号有 OP（本装置要 {@code /gamemode} 与 {@code /tp}）。参数走系统属性，不落盘、不进正式产物。</p>
 */
public final class SeedRadius6Smoke {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "234半径6烟测";

    /** 烟测范围：口径允许的最大值（13 × 13 = 169 个目标区块）。 */
    private static final int RADIUS = 6;

    private static final long SEED = 20260922L;

    /** 玩家的固定观测点：出生点附近（区块 0,0），铺开过程中不再移动，便于把规模读数对齐到「一个中心」。 */
    private static final int[] VIEW = {9, -40, 8};

    /** 每个采样点的间隔（刻）：10 秒一次，共 {@link #SAMPLE_COUNT} 次。 */
    private static final int SAMPLE_INTERVAL_TICKS = 20 * 10;
    private static final int SAMPLE_COUNT = 6;

    /** 相邻客户端刻间隔超过它就记一次卡顿（毫秒）。 */
    private static final long STALL_MILLIS = 200L;

    private static final int WAIT_WORLD_TICKS = 20 * 180;
    private static final int WAIT_COVERAGE_TICKS = 20 * 900;

    private enum Phase {
        WAIT_WORLD,
        EXPAND,
        MEASURE,
        FINISHED
    }

    private static Phase phase = Phase.WAIT_WORLD;
    private static int waitTicks;
    private static int sampleTicks;
    private static int sampleIndex;

    /** 上一次客户端刻的墙钟（用于统计卡顿）。 */
    private static long lastTickNanos;

    /** 卡顿统计（只统计进入世界之后）。 */
    private static int stalls;
    private static long worstGapMillis;

    /**
     * 234.1：分级卡顿计数与覆盖队列峰值。
     *
     * <p>口径要求「另记主线程 &gt;100 / &gt;500 / &gt;1000 毫秒 stall」，只报一个 &gt;200 毫秒的合计
     * 无法区分「偶发 200~300 毫秒」与「真的冻了半秒以上」，所以三档分开记；覆盖队列峰值
     * （{@code pending queue max}）同理，只有峰值能说明队列有没有一度堆积到失控。</p>
     */
    private static int stallsOver100;
    private static int stallsOver500;
    private static int stallsOver1000;
    private static int maxPending;

    /** 铺开计时。 */
    private static long expandStartNanos;
    private static long expandDoneNanos;

    private SeedRadius6Smoke() {
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：烟测装置异常，已停止", TAG, error);
            phase = Phase.FINISHED;
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (phase == Phase.FINISHED) {
            return;
        }
        if (client.player == null || client.level == null) {
            if (phase != Phase.WAIT_WORLD) {
                fail(client, "中途离开了世界");
                return;
            }
            // 第一次等待就打一行：把「刻真的在跑、只是还没进世界」与「客户端刻根本没跑」区分开
            // （后者在日志里会是完全静默，这正是排查「客户端卡住」时最需要的信息）
            if (waitTicks == 0) {
                LOGGER.info("{}：已挂载，等待进入世界（玩家 {} / 世界 {}）", TAG,
                        client.player == null ? "无" : "有", client.level == null ? "无" : "有");
            }
            if (++waitTicks > WAIT_WORLD_TICKS) {
                fail(client, "等待进入世界超时（请先跑 runSeedWorkerServer 并连上 127.0.0.1:25565）");
            }
            return;
        }
        long now = System.nanoTime();
        if (lastTickNanos != 0L) {
            long gapMillis = (now - lastTickNanos) / 1_000_000L;
            if (gapMillis > worstGapMillis) {
                worstGapMillis = gapMillis;
            }
            if (gapMillis > STALL_MILLIS) {
                stalls++;
            }
            if (gapMillis > 100L) {
                stallsOver100++;
            }
            if (gapMillis > 500L) {
                stallsOver500++;
            }
            if (gapMillis > 1000L) {
                stallsOver1000++;
            }
        }
        lastTickNanos = now;
        switch (phase) {
            // 234.1 修正：这里原本缺 WAIT_WORLD 分支，于是「玩家进入世界」之后
            // 阶段永远停在 WAIT_WORLD、switch 什么都不做 —— 装置表现为「进了世界但一动不动」，
            // 这正是 234 报告里「停在等待进入世界」的真因（属测试 harness 缺陷，与产品代码无关）。
            case WAIT_WORLD -> prepare(client);
            case EXPAND -> expand(client);
            case MEASURE -> measure(client);
            default -> {
            }
        }
    }

    /** 摆好参数并把玩家放到固定观测点。 */
    private static void prepare(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setEnabled(true);
        service.setSeedText(Long.toString(SEED));
        service.setRenderPrediction(true);
        service.setShowMissing(false);
        service.setCoverageRadius(RADIUS);
        sendCommand(client, "gamemode spectator");
        sendCommand(client, "tp " + VIEW[0] + " " + VIEW[1] + " " + VIEW[2]);
        expandStartNanos = System.nanoTime();
        LOGGER.info("{}：开跑 —— 种子 {} / 范围 {}（{} 个目标区块）/ 观测点 ({},{},{})；"
                        + "预测缓存上限 {} 个区块；帧级采样 {}",
                TAG, SEED, RADIUS, (2 * RADIUS + 1) * (2 * RADIUS + 1), VIEW[0], VIEW[1], VIEW[2],
                service.cachedChunkLimit(), SeedRenderFrameProfiler.enabled() ? "开" : "关（未设 "
                        + SeedRenderFrameProfiler.ENABLED_PROPERTY + "=1）");
        phase = Phase.EXPAND;
        waitTicks = 0;
        sampleTicks = 0;
        sampleIndex = 0;
    }

    /** 等覆盖铺满（或超时）；每 5 秒打一次进度，便于看队列是不是在动。 */
    private static void expand(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        int pending = service.coveragePendingCount();
        if (pending > maxPending) {
            maxPending = pending;
        }
        boolean full = total > 0 && done >= total;
        if (waitTicks % 100 == 0) {
            LOGGER.info("{}：铺开进度 {}/{} 个区块；已用 {} 秒；队列剩余 {}（峰值 {}）；计算器 {}；缓存 {}/{}；FPS {}",
                    TAG, done, total, secondsSince(expandStartNanos), pending, maxPending,
                    service.calculatorStateCn(), service.cachedChunkCount(), service.cachedChunkLimit(),
                    client.getFps());
        }
        if (full) {
            expandDoneNanos = System.nanoTime();
            LOGGER.info("{}：覆盖已铺满 —— 目标 {} 个区块，铺开耗时 {} 秒（{} 刻）；计算器 {}",
                    TAG, total, secondsSince(expandStartNanos), waitTicks, service.calculatorStateCn());
            phase = Phase.MEASURE;
            waitTicks = 0;
            sampleTicks = 0;
            sampleIndex = 0;
            return;
        }
        if (++waitTicks > WAIT_COVERAGE_TICKS) {
            LOGGER.warn("{}：等待铺满超时（{}/{} 个区块）—— 按当前状态进入采样，如实记录未铺满", TAG, done, total);
            expandDoneNanos = System.nanoTime();
            phase = Phase.MEASURE;
            waitTicks = 0;
            sampleTicks = 0;
            sampleIndex = 0;
        }
    }

    /** 每隔 10 秒采一次样：规模 + 帧率 + 卡顿 + 内存 + 帧级渲染耗时。 */
    private static void measure(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        if (sampleTicks == 0) {
            sampleIndex++;
            int pending = service.coveragePendingCount();
            if (pending > maxPending) {
                maxPending = pending;
            }
            SeedRenderSnapshot snapshot = service.renderSnapshot();
            SeedRenderSnapshot.Stats stats = snapshot.stats();
            Runtime runtime = Runtime.getRuntime();
            long usedMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L);
            long committedMb = runtime.totalMemory() / (1024L * 1024L);
            long maxMb = runtime.maxMemory() / (1024L * 1024L);
            LOGGER.info("{}：采样 #{}/{} → 范围 {}（{} 个目标区块，已预测 {} 个）：候选 {} 条"
                            + "（未观察 {} / 已确认 {} / 当前缺失 {} / 调度敏感 {}）；渲染条目 {} 条；"
                            + "缓存 {}/{} 个区块",
                    TAG, sampleIndex, SAMPLE_COUNT, RADIUS, service.coverageTargetCount(),
                    stats.predictedChunks(), stats.candidates(), stats.unobserved(), stats.confirmed(),
                    stats.missing(), stats.scheduleSensitive(), snapshot.size(),
                    service.cachedChunkCount(), service.cachedChunkLimit());
            LOGGER.info("{}：采样 #{} 运行态 → FPS {}；客户端堆 已用 {} / 已提交 {} / 上限 {} MB；"
                            + "卡顿（相邻客户端刻间隔 >{} 毫秒）{} 次（分级 >100/>500/>1000 毫秒 = {}/{}/{}），"
                            + "最大间隔 {} 毫秒；覆盖队列峰值 {}；"
                            + "计算器 {}（PID {}，启动 {} 次）",
                    TAG, sampleIndex, client.getFps(), usedMb, committedMb, maxMb,
                    STALL_MILLIS, stalls, stallsOver100, stallsOver500, stallsOver1000, worstGapMillis,
                    maxPending, service.calculatorStateCn(),
                    service.calculatorPid(), service.calculatorStartCount());
            LOGGER.info("{}：采样 #{} 渲染器帧级读数 → {}", TAG, sampleIndex,
                    SeedRenderFrameProfiler.snapshotSummaryCn());
            if (sampleIndex >= SAMPLE_COUNT) {
                finish(client);
                return;
            }
        }
        if (++sampleTicks >= SAMPLE_INTERVAL_TICKS) {
            sampleTicks = 0;
        }
    }

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LocalPlayer player = client.player;
        ChunkPos chunk = player == null ? null : ChunkPos.containing(player.blockPosition());
        LOGGER.info("{}：====== 结果汇总 ======", TAG);
        LOGGER.info("{}：范围 {} → 铺开 {} 秒；目标区块 {}；缓存 {}/{}（上限是否够用：{}）；"
                        + "卡顿 >{} 毫秒 {} 次（分级 >100/>500/>1000 = {}/{}/{}）/ 最大刻间隔 {} 毫秒；"
                        + "覆盖队列峰值 {}；计算器重启 {} 次；FPS {}",
                TAG, RADIUS, secondsSince(expandStartNanos), service.coverageTargetCount(),
                service.cachedChunkCount(), service.cachedChunkLimit(),
                service.cachedChunkCount() <= service.cachedChunkLimit() ? "够（未触上限）" : "已触上限",
                STALL_MILLIS, stalls, stallsOver100, stallsOver500, stallsOver1000, worstGapMillis,
                maxPending, service.calculatorStartCount() - 1, client.getFps());
        LOGGER.info("{}：队列 / 失败读数 {}", TAG, coverageQueueSummaryCn(service));
        LOGGER.info("{}：玩家区块 {}；最终读数 {}", TAG, chunk == null ? "（未知）" : chunk, service.runtimeDiagnosticsCn());
        LOGGER.info("{}：最终帧级渲染读数 → {}", TAG, SeedRenderFrameProfiler.snapshotSummaryCn());
        service.setRenderPrediction(false);
        phase = Phase.FINISHED;
        LOGGER.info("{}：已关闭显示预测钻石并收尾（世界里不再留预测框）", TAG);
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        LOGGER.error("{}：用例失败 —— {}", TAG, reason);
        LOGGER.error("{}：失败时读数 {}", TAG, SeedMiningService.instance().runtimeDiagnosticsCn());
        phase = Phase.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    /** 自某起点起的秒数（保留一位小数）。 */
    private static String secondsSince(long startNanos) {
        return String.format("%.1f", (System.nanoTime() - startNanos) / 1_000_000_000d);
    }

    /**
     * 从运行诊断里抠出覆盖队列的四项计数，单独成行。
     *
     * <p>它们本来就在 {@code runtimeDiagnosticsCn()} 的长串里，但口径把它们列成独立取证项
     * （累计提交 / 完成 / 移动丢弃排队 / 失败不再重试），埋在长句里取证时容易看漏，所以单独打一行。
     * 这里只读正式层的既有诊断文本，不新增也不改动任何正式接口。</p>
     */
    private static String coverageQueueSummaryCn(SeedMiningService service) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("累计提交 (\\d+) / 完成 (\\d+)（移动丢弃排队 (\\d+)，失败不再重试 (\\d+)）")
                .matcher(service.runtimeDiagnosticsCn());
        if (!matcher.find()) {
            return "（运行诊断里没有覆盖队列段，队列峰值 " + maxPending + "）";
        }
        return "累计提交 " + matcher.group(1) + " / 完成 " + matcher.group(2)
                + " / 移动丢弃排队 " + matcher.group(3) + " / 失败不再重试 " + matcher.group(4)
                + "；覆盖队列峰值 " + maxPending;
    }

    private static boolean sendCommand(Minecraft client, String command) {
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            return false;
        }
        player.connection.sendCommand(command);
        return true;
    }
}
