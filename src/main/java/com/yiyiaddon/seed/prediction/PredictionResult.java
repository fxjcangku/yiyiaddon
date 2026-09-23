package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.SeedOreTarget;
import java.util.List;
import java.util.Objects;

/**
 * 种子挖矿正式模块 · 一次预测的结果。
 *
 * <p><b>它不包含</b>真值 / 漏报 / 错报：那些属于测试比较层，不属于正式 Predictor API
 * （正式化第一阶段口径第十二节——正式层永远不接触真实世界的最终方块状态）。</p>
 *
 * <p><b>失败 ≠ 没有矿</b>（口径第三十九节）：{@link #success()} 为 false 时
 * {@link #ores()} 必定为空，含义是「这次预测不成立」，调用方必须按失败处理，
 * 绝不允许当成「这个区块没有钻石」。失败原因在 {@link #failureReason()}。</p>
 *
 * @param request       本次请求（种子 / 维度 / 目标区块 / 矿物种类）
 * @param ores          预测出来的矿物（成功时按扫描顺序稳定排列；失败时为空表）
 * @param success       本次预测是否成立
 * @param failureReason 失败原因（中文，成功时为 null）
 * @param elapsedMillis 本次预测耗时（毫秒；含调度敏感复核）
 * @param stats         统计与诊断（见 {@link Stats}）
 */
public record PredictionResult(SeedOreTarget request, List<PredictedOre> ores, boolean success,
                               String failureReason, long elapsedMillis, Stats stats) {

    public PredictionResult {
        Objects.requireNonNull(request, "request");
        ores = List.copyOf(ores);
        Objects.requireNonNull(stats, "stats");
    }

    /**
     * 一次预测的统计与诊断。
     *
     * @param protoChunks               本次预测新建的离线区块数（代价指标）
     * @param stageExecutions           本次预测执行的生成阶段数
     * @param cacheHits                 本次预测因已有产出而跳过的阶段数（= 会话缓存复用收益）
     * @param heldChunks                会话此刻累计持有的离线区块数（缓存规模）
     * @param hostChunkSourceQueries    本次预测向宿主 ChunkMap 的查询次数增量（必须为 0）
     * @param foreignWriterViewers      基线执行中「除目标自身外、真的改过目标区块」的 viewer 个数
     * @param scheduleAnalysisExecuted  是否真的跑了反向顺序复核
     * @param deterministicCount        {@link PredictionCertainty#DETERMINISTIC} 数量（本阶段恒为 0）
     * @param scheduleSensitiveCount    {@link PredictionCertainty#SCHEDULE_SENSITIVE} 数量
     * @param unresolvedCount           {@link PredictionCertainty#UNRESOLVED} 数量
     * @param notes                     中文诊断行（顺序稳定，直接可进报告）
     */
    public record Stats(int protoChunks, long stageExecutions, long cacheHits, int heldChunks,
                        int hostChunkSourceQueries, int foreignWriterViewers, boolean scheduleAnalysisExecuted,
                        int deterministicCount, int scheduleSensitiveCount, int unresolvedCount,
                        List<String> notes) {

        public Stats {
            notes = List.copyOf(notes);
        }
    }

    /** 本次预测是否成立。 */
    public boolean failed() {
        return !success;
    }

    /** 预测数量。 */
    public int count() {
        return ores.size();
    }

    /** {@link PredictionCertainty#DETERMINISTIC} 数量（本阶段恒为 0，见 {@link PredictionCertainty}）。 */
    public int deterministicCount() {
        return stats.deterministicCount();
    }

    /** {@link PredictionCertainty#SCHEDULE_SENSITIVE} 数量。 */
    public int scheduleSensitiveCount() {
        return stats.scheduleSensitiveCount();
    }

    /** {@link PredictionCertainty#UNRESOLVED} 数量。 */
    public int unresolvedCount() {
        return stats.unresolvedCount();
    }

    /** 构造一个失败结果（失败结果一定不含矿物）。 */
    public static PredictionResult failure(SeedOreTarget request, String reason, long elapsedMillis) {
        return new PredictionResult(request, List.of(), false, reason, elapsedMillis,
                new Stats(0, 0, 0, 0, 0, 0, false, 0, 0, 0, List.of()));
    }

    /** 一行中文判定（日志 / 报告用）。 */
    public String describeCn() {
        if (!success) {
            return request.describeCn() + " → 预测失败：" + failureReason;
        }
        return request.describeCn() + " → 预测 " + count() + " 个（调度敏感 " + scheduleSensitiveCount()
                + " / 未解析 " + unresolvedCount() + "），耗时 " + elapsedMillis + " ms";
    }
}
