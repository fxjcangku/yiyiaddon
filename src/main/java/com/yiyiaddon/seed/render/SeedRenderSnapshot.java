package com.yiyiaddon.seed.render;

import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.observation.SeedOreObservationTracker;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.runtime.SeedPredictionRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿正式模块 · <b>渲染快照</b>（正式化第五阶段 233）。
 *
 * <p><b>为什么必须有它</b>（口径第三十二、五十一节）：渲染层<b>不允许</b>直接访问预测缓存、
 * 不允许访问客户端世界、不允许执行观察、不允许调用 Worker。它每帧只做两件事：
 * 读这份<b>不可变</b>快照 + 画 camera-relative 的方框。因此快照必须在别处（服务层、客户端主线程）
 * 造好，且造一次就能被反复读。</p>
 *
 * <p><b>与观察快照的分工</b>：{@code SeedObservationSnapshot} 是「观察这一维度」的读数（界面统计用），
 * 本类是把「预测 + 观察」两边合一之后的呈现口径（渲染用），并顺带给出界面要用的分类计数。</p>
 *
 * <p><b>不含可疑计数</b>：{@link OreObservationState#SUSPICIOUS} 在 233 一律不产生，
 * 因此这里既不统计也不显示（口径第十二、三十九节）。</p>
 *
 * @param entries 待渲染条目（顺序稳定：按缓存区块插入序、区块内按预测顺序）
 * @param stats   分类计数与覆盖读数
 */
public record SeedRenderSnapshot(List<SeedRenderEntry> entries, Stats stats) {

    public SeedRenderSnapshot {
        entries = List.copyOf(entries);
    }

    /**
     * 渲染快照的分类计数。
     *
     * @param predictedChunks   已经有正式预测结果的目标区块数（= 客户端预测缓存规模）
     * @param candidates        候选（预测钻石）总数
     * @param unobserved        {@link OreObservationState#UNOBSERVED} 数
     * @param confirmed         {@link OreObservationState#CONFIRMED} 数
     * @param missing           {@link OreObservationState#MISSING} 数
     * @param scheduleSensitive {@link PredictionCertainty#SCHEDULE_SENSITIVE} 数（与观察状态无关）
     */
    public record Stats(int predictedChunks, int candidates, int unobserved, int confirmed, int missing,
                        int scheduleSensitive) {
    }

    /** 空快照（未启用 / 已清空 / 刚失效）。 */
    public static final SeedRenderSnapshot EMPTY =
            new SeedRenderSnapshot(List.of(), new Stats(0, 0, 0, 0, 0, 0));

    /**
     * 由预测缓存 + 观察状态构造快照（<b>只在客户端主线程调用</b>）。
     *
     * <p>代价 O(缓存里的候选数)：只在数据真的变了（有新的预测落地 / 区块加载或卸载 / 候选方块被更新）
     * 之后重建一次，不在每帧重建（口径第二十三、五十一节）。</p>
     */
    public static SeedRenderSnapshot build(SeedPredictionRepository repository,
                                           SeedOreObservationTracker tracker) {
        if (repository == null || tracker == null) {
            return EMPTY;
        }
        List<SeedRenderEntry> entries = new ArrayList<>();
        int unobserved = 0;
        int confirmed = 0;
        int missing = 0;
        int scheduleSensitive = 0;
        int predictedChunks = 0;
        for (PredictionResult result : repository.results()) {
            if (result == null || result.failed()) {
                continue;
            }
            predictedChunks++;
            for (PredictedOre ore : result.ores()) {
                OreObservationState state = tracker.stateOf(ore.position().asLong());
                entries.add(new SeedRenderEntry(ore.position(), ore.oreType(), ore.certainty(), state));
                switch (state) {
                    case CONFIRMED -> confirmed++;
                    case MISSING -> missing++;
                    // SUSPICIOUS 本阶段不产出；万一日后由别处写入，也按「未观察」归类，绝不当成有效钻石
                    default -> unobserved++;
                }
                if (ore.certainty() == PredictionCertainty.SCHEDULE_SENSITIVE) {
                    scheduleSensitive++;
                }
            }
        }
        return new SeedRenderSnapshot(entries,
                new Stats(predictedChunks, entries.size(), unobserved, confirmed, missing, scheduleSensitive));
    }

    /** 条目总数。 */
    public int size() {
        return entries.size();
    }

    /** 是否为「什么也不画」的空快照。 */
    public boolean empty() {
        return entries.isEmpty();
    }

    /** 一行中文摘要（日志 / 界面统计同一口径）。 */
    public String describeCn() {
        return "目标区块 " + stats.predictedChunks() + " / 候选 " + stats.candidates()
                + "（未观察 " + stats.unobserved() + " / 已确认 " + stats.confirmed()
                + " / 当前缺失 " + stats.missing() + " / 调度敏感 " + stats.scheduleSensitive() + "）";
    }
}
