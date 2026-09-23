package com.yiyiaddon.seed.render;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第六阶段（234）· <b>渲染帧级 CPU 采样器（开发诊断，默认关闭）</b>。
 *
 * <p><b>为什么需要它</b>（口径第五十四、五十五节）：233 只测了「快照重建」的成本（主线程、每次观察变化才发生），
 * 没有测<b>每帧</b>真正跑在渲染路径上的那段循环。默认半径 3（49 目标区块）时快照里约 1100 条候选，
 * 每帧要遍历一遍并生成方框，这个数字必须实测，不能靠估计。</p>
 *
 * <p><b>它测什么</b>：{@link SeedOreWorldRenderer#render} 一次调用的墙钟耗时（纳秒）、本次遍历的条目数。
 * 累计到 {@link #REPORT_EVERY_FRAMES} 帧就打一条汇总（平均 / P95 / 最大 / 最近条目数）。</p>
 *
 * <p><b>为什么可以留在正式类里</b>：默认关闭 —— 关闭时 {@link #enabled()} 是一个 {@code static final boolean}
 * 常量折叠，热路径上只多一条永不跳转的分支，没有计时调用、没有分配、没有日志。</p>
 *
 * <p><b>线程</b>：只由渲染帧调用（与客户端主线程同一条线程）；{@link #summaryCn()} 只读快照，可从任意线程取。</p>
 */
public final class SeedRenderFrameProfiler {

    /** 开关的系统属性；不设或不为 {@code 1} 时整层不工作。 */
    public static final String ENABLED_PROPERTY = "yiyiaddon.seedprofiler.enabled";

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 编译期常量：关闭时整段采样代码对 JIT 不可达。 */
    private static final boolean ENABLED = "1".equals(System.getProperty(ENABLED_PROPERTY, "0").trim());

    /** 每多少帧打一条汇总（口径要求至少 1000 帧样本：600 × 2 即可覆盖）。 */
    private static final int REPORT_EVERY_FRAMES = 600;

    /** 采样环容量（P95 用；超过就覆盖最早的样本，报告的是「最近 8192 帧」）。 */
    private static final int RING = 8192;

    private static final long[] SAMPLES_NANOS = new long[RING];

    /** 采样统计（只在渲染线程写、任意线程读，读的是近似值，够报告用）。 */
    private static int cursor;
    private static int sampled;
    private static long frames;
    private static long totalNanos;
    private static long maxNanos;
    private static int lastEntries;
    private static volatile String lastSummaryCn = "（尚未采样到帧）";

    private SeedRenderFrameProfiler() {
    }

    /** 采样是否开启（关闭时渲染器走无插桩分支）。 */
    public static boolean enabled() {
        return ENABLED;
    }

    /**
     * 记一帧。
     *
     * @param startNanos 进入绘制前的 {@link System#nanoTime()}
     * @param endNanos   绘制结束时的 {@link System#nanoTime()}
     * @param snapshot   本帧读到的渲染快照（用来记条目数）
     */
    public static void record(long startNanos, long endNanos, SeedRenderSnapshot snapshot) {
        long cost = endNanos - startNanos;
        if (cost < 0L) {
            cost = 0L;
        }
        SAMPLES_NANOS[cursor] = cost;
        cursor = (cursor + 1) % RING;
        if (sampled < RING) {
            sampled++;
        }
        frames++;
        totalNanos += cost;
        if (cost > maxNanos) {
            maxNanos = cost;
        }
        lastEntries = snapshot == null || snapshot.empty() ? 0 : snapshot.entries().size();
        if (frames % REPORT_EVERY_FRAMES == 0L) {
            String summary = summarize();
            lastSummaryCn = summary;
            LOGGER.info("{}：渲染帧级采样（第 {} 帧）→ {}", LAYER_TAG, frames, summary);
        }
    }

    /** 当前累计读数（报告 / 界面诊断可读）。 */
    public static String summaryCn() {
        return lastSummaryCn;
    }

    /** 立刻生成一份读数（不依赖是否刚好走到汇报帧）。 */
    public static String snapshotSummaryCn() {
        return frames == 0L ? "（尚未采样到帧）" : summarize();
    }

    private static String summarize() {
        long count = Math.min(sampled, RING);
        if (count == 0L) {
            return "（尚未采样到帧）";
        }
        long[] copy = Arrays.copyOf(SAMPLES_NANOS, (int) count);
        Arrays.sort(copy);
        long p95 = copy[(int) Math.min(count - 1L, Math.round(count * 0.95d))];
        return String.format(
                "帧数 %d / 平均 %.1f 微秒 / P95 %.1f 微秒 / 最大 %.1f 微秒 / 最近一帧条目 %d 条",
                frames, totalNanos / 1000d / count, p95 / 1000d, maxNanos / 1000d, lastEntries);
    }

    /** 日志前缀（与渲染层所有者标识一致，便于按标识抓日志）。 */
    private static final String LAYER_TAG = SeedOreWorldRenderer.LAYER_ID;
}
