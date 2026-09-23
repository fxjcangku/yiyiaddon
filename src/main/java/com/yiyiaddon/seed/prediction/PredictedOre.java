package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · 一块被预测出来的矿。
 *
 * <p><b>为什么不是 {@code Set<BlockPos>}</b>：只给坐标的预测结果无法表达产品语义。228 报告定案后，
 * 「这块矿有没有被调度影响」「它从哪个方向被写进目标区块的」都是调用方必须能看见的信息
 * （正式化第一阶段口径第九节）。</p>
 *
 * @param position           方块坐标
 * @param oreType            矿物种类
 * @param certainty          Seed 预测确定性（见 {@link PredictionCertainty}）
 * @param source             来源分类；本阶段一律 {@link OreSource#UNATTRIBUTED}
 * @param originViewer       是哪一次 {@code FEATURES}（哪个区块自己的装饰）把它写进目标区块的；
 *                           诊断元数据，未知时为 null
 * @param conflictingWriters 该坐标上被观测到「改变过钻石存在性」的 viewer 列表（按发生先后）；
 *                           只在 {@link PredictionCertainty#SCHEDULE_SENSITIVE} 时非空，
 *                           它是调度敏感结论的局部冲突图证据（正式化第一阶段口径第二十四节）
 */
public record PredictedOre(BlockPos position, OreType oreType, PredictionCertainty certainty, OreSource source,
                           ChunkPos originViewer, List<ChunkPos> conflictingWriters) {

    public PredictedOre {
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(certainty, "certainty");
        Objects.requireNonNull(source, "source");
        conflictingWriters = List.copyOf(conflictingWriters);
    }

    /**
     * 这块矿是否由「别的区块自己的装饰」跨区块写进目标区块。
     *
     * @param targetChunk 目标区块
     */
    public boolean crossChunkWrite(ChunkPos targetChunk) {
        return originViewer != null && !originViewer.equals(targetChunk);
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "(" + position.getX() + "," + position.getY() + "," + position.getZ() + ") "
                + oreType.displayNameCn() + " / " + certainty.displayNameCn() + " / " + source.displayNameCn()
                + (originViewer == null ? "" : " / 来源 viewer(" + originViewer.x() + "," + originViewer.z() + ")")
                + (conflictingWriters.isEmpty() ? "" : " / 冲突 viewer " + conflictingWriters.size() + " 个");
    }
}
