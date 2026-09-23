package com.yiyiaddon.seed.worker.client;

import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.model.SeedOreTarget;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerChunkRef;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerOreDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerStatsDto;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * 种子挖矿 · <b>IPC DTO → 正式业务模型</b>的转换（只在客户端业务层发生一次）。
 *
 * <p>口径第五十六、五十七节：传输 DTO 只是搬运层，回到客户端必须<b>立刻</b>还原成正式
 * {@link PredictionResult} / {@link PredictedOre}，界面与业务层永远看不到 JSON。
 * 同时这里承担「不认识就拒绝」的职责（口径第六十六节）：未知的矿物 / 确定性 / 来源枚举、
 * 与请求不一致的种子或区块，都按协议错误处理，<b>绝不允许</b>变成「这个区块没有矿」。</p>
 */
public final class SeedWorkerPredictionMapper {

    private SeedWorkerPredictionMapper() {
    }

    /**
     * 把 Worker 返回的预测还原成正式结果。
     *
     * @param dto              传输载荷
     * @param expectedSeed     本次请求的种子（用于交叉校验）
     * @param expectedDimension 本次请求的维度标识（用于交叉校验）
     * @param expectedOreType  本次请求的矿物（用于交叉校验）
     * @param expectedChunk    本次请求的目标区块（用于交叉校验）
     * @throws WorkerProtocolException 载荷与请求不一致或含不认识的枚举
     */
    public static PredictionResult toResult(WorkerPredictionDto dto, long expectedSeed,
                                           String expectedDimension, OreType expectedOreType,
                                           ChunkPos expectedChunk) {
        requireMatch(dto, expectedSeed, expectedDimension, expectedOreType, expectedChunk);
        ResourceKey<Level> dimensionKey = dimensionKeyOf(expectedDimension);
        SeedOreTarget request = SeedOreTarget.of(expectedSeed, dimensionKey, expectedChunk, expectedOreType);
        if (!dto.success()) {
            String reason = dto.failureReason() == null || dto.failureReason().isBlank()
                    ? "本地世界生成计算器报告预测不成立" : dto.failureReason();
            return PredictionResult.failure(request, reason, dto.elapsedMillis());
        }
        List<PredictedOre> ores = new ArrayList<>(dto.ores().size());
        WorkerStatsDto stats = dto.stats();
        int sensitive = 0;
        int deterministic = 0;
        for (WorkerOreDto ore : dto.ores()) {
            OreType oreType = parseOreType(ore.oreType());
            PredictionCertainty certainty = parseCertainty(ore.certainty());
            OreSource source = parseSource(ore.source());
            if (certainty == PredictionCertainty.SCHEDULE_SENSITIVE) {
                sensitive++;
            } else if (certainty == PredictionCertainty.DETERMINISTIC) {
                deterministic++;
            }
            ores.add(new PredictedOre(new BlockPos(ore.x(), ore.y(), ore.z()), oreType, certainty, source,
                    toChunkPos(ore.originViewer()), toChunkPosList(ore.conflictingWriters())));
        }
        int sensitiveCount = stats.scheduleSensitiveCount();
        int deterministicCount = stats.deterministicCount();
        // 统计与逐块分类必须自洽：不一致说明传输或解析出了问题，宁可报错也不给出一个自相矛盾的结果
        if (sensitiveCount != sensitive || deterministicCount != deterministic) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "统计与逐块分类不一致（统计 敏感 " + sensitiveCount + " / 确定 " + deterministicCount
                            + "，逐块 敏感 " + sensitive + " / 确定 " + deterministic + "）");
        }
        if (sensitiveCount + deterministicCount + stats.unresolvedCount() != ores.size()) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "分类数量之和与候选总数不一致（敏感 " + sensitiveCount + " + 确定 " + deterministicCount
                            + " + 未解析 " + stats.unresolvedCount() + " ≠ " + ores.size() + "）");
        }
        return new PredictionResult(request, ores, true, null, dto.elapsedMillis(),
                new PredictionResult.Stats(stats.protoChunks(), stats.stageExecutions(), stats.cacheHits(),
                        stats.heldChunks(), stats.hostChunkSourceQueries(), stats.foreignWriterViewers(),
                        stats.scheduleAnalysisExecuted(), stats.deterministicCount(),
                        stats.scheduleSensitiveCount(), stats.unresolvedCount(), stats.notes()));
    }

    /** 载荷必须与本次请求同种子 / 同区块 / 同维度 / 同矿物。 */
    private static void requireMatch(WorkerPredictionDto dto, long expectedSeed, String expectedDimension,
                                     OreType expectedOreType, ChunkPos expectedChunk) {
        if (dto.seed() != expectedSeed) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "返回的种子与请求不一致（请求 " + expectedSeed + "，返回 " + dto.seed() + "）");
        }
        if (dto.chunkX() != expectedChunk.x() || dto.chunkZ() != expectedChunk.z()) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "返回的目标区块与请求不一致（请求 " + expectedChunk.x() + "," + expectedChunk.z()
                            + "，返回 " + dto.chunkX() + "," + dto.chunkZ() + "）");
        }
        if (expectedDimension == null || !expectedDimension.equals(dto.dimension())) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "返回的维度与请求不一致（请求 " + expectedDimension + "，返回 " + dto.dimension() + "）");
        }
        if (expectedOreType == null || !expectedOreType.name().equals(dto.oreType())) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "返回的矿物与请求不一致（请求 " + expectedOreType + "，返回 " + dto.oreType() + "）");
        }
    }

    private static OreType parseOreType(String name) {
        OreType parsed = OreType.parse(name);
        if (parsed == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "不认识的矿物种类：" + name);
        }
        return parsed;
    }

    private static PredictionCertainty parseCertainty(String name) {
        try {
            return PredictionCertainty.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException unknown) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "不认识的确定性分类：" + name);
        }
    }

    private static OreSource parseSource(String name) {
        try {
            return OreSource.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException unknown) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "不认识的来源分类：" + name);
        }
    }

    private static ChunkPos toChunkPos(WorkerChunkRef ref) {
        return ref == null ? null : new ChunkPos(ref.x(), ref.z());
    }

    private static List<ChunkPos> toChunkPosList(List<WorkerChunkRef> refs) {
        List<ChunkPos> positions = new ArrayList<>(refs.size());
        for (WorkerChunkRef ref : refs) {
            positions.add(new ChunkPos(ref.x(), ref.z()));
        }
        return positions;
    }

    /**
     * 维度标识 → 维度键。
     *
     * <p>只接受 {@code SeedDimensionProfile} 里声明过的维度：IPC 载荷是纯字符串，
     * 不认识的维度一律当作协议错误（fail-closed），绝不当成「主世界」继续算。</p>
     */
    public static ResourceKey<Level> dimensionKeyOf(String dimensionId) {
        SeedDimensionProfile profile = SeedDimensionProfile.of(dimensionId);
        if (profile == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                    "不认识的维度：" + dimensionId);
        }
        return profile.levelKey();
    }
}
