package com.yiyiaddon.seedworker;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.prediction.DiamondSeedPredictor;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerChunkRef;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerOreDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerSessionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerStatsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · Worker · <b>会话与预测</b>（正式 Predictor 在 Worker 侧的宿主包装）。
 *
 * <p><b>算法一行未改</b>（阶段 232 口径第四十一节）：这里做的是「把客户端递进来的
 * 种子 / 维度 / 目标区块交给正式 {@link DiamondSeedPredictor}，再把
 * {@link PredictionResult} 转成传输 DTO」。因此 Worker 与单人 Oracle 必然逐 BlockPos 一致 ——
 * 一致性来自「同一份代码」，而不是来自「两边都调对了参数」。</p>
 *
 * <p><b>线程模型</b>（口径第五十八节）：本类的全部方法都只在 Worker 的<b>单线程 worldgen executor</b>
 * 上执行（唯一调用者是 {@link SeedWorkerIpcServer}），因此不需要任何锁，也不会出现两个预测
 * 同时抢 CPU / 内存。{@link #sessionId()} 等只读读数供日志使用，声明为 volatile。</p>
 *
 * <p><b>会话身份</b>（口径第十四、二十八、二十九节）：会话 = （种子 + 维度）。同一个会话下多次预测
 * 复用同一份离线世界与缓存（相邻区块因此更快）；种子或维度变了，旧会话<b>立刻释放</b>，
 * 绝不允许两个种子共用一份缓存。</p>
 */
public final class SeedWorkerSessions implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed-worker");

    /** 本阶段唯一支持的维度标识。 */
    private static final String OVERWORLD_ID = "minecraft:overworld";

    /** 本阶段唯一支持的矿物枚举名。 */
    private static final String DIAMOND_NAME = OreType.DIAMOND.name();

    private final SeedWorkerHost host;

    /** 会话编号（只用于日志关联）。 */
    private final AtomicLong sessionCounter = new AtomicLong();

    /** 正式预测器（只在 worldgen executor 上创建与使用）。 */
    private DiamondSeedPredictor predictor;

    private volatile String sessionId = "";
    private volatile long sessionSeed;
    private volatile String sessionDimension = "";
    private volatile boolean sessionOpen;
    private volatile int lastHeldChunks;
    private volatile long totalPredictions;

    public SeedWorkerSessions(SeedWorkerHost host) {
        this.host = host;
    }

    /** 当前会话标识（未打开为空串）。 */
    public String sessionId() {
        return sessionId;
    }

    /** 当前会话种子（未打开为 0）。 */
    public long sessionSeed() {
        return sessionSeed;
    }

    /** 当前会话维度标识（未打开为空串）。 */
    public String sessionDimension() {
        return sessionDimension;
    }

    /** 会话是否打开。 */
    public boolean sessionOpen() {
        return sessionOpen;
    }

    /** 上一次预测后会话持有的离线区块数（诊断）。 */
    public int lastHeldChunks() {
        return lastHeldChunks;
    }

    /** 累计预测次数（诊断）。 */
    public long totalPredictions() {
        return totalPredictions;
    }

    /**
     * 打开会话（种子 + 维度）。
     *
     * <p>种子或维度与当前不同 ⇒ 释放旧会话的全部离线世界（口径第二十九、三十节），
     * 随后同一会话下的多次预测继续复用缓存。</p>
     */
    public WorkerSessionDto openSession(long seed, String dimension) {
        if (!OVERWORLD_ID.equals(dimension)) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本阶段只支持主世界（" + OVERWORLD_ID + "），当前维度=" + dimension);
        }
        if (sessionOpen && (sessionSeed != seed || !sessionDimension.equals(dimension))) {
            LOGGER.info("会话切换：({} / {}) → ({} / {})，旧会话的离线世界全部释放",
                    sessionId, sessionSeed, seed, dimension);
            releaseSessions();
        }
        if (!sessionOpen) {
            sessionId = "s" + sessionCounter.incrementAndGet() + "-" + Long.toUnsignedString(seed, 16);
            sessionSeed = seed;
            sessionDimension = dimension;
            sessionOpen = true;
            LOGGER.info("会话已打开：{}（种子 {}，维度 {}）", sessionId, seed, dimension);
        }
        return new WorkerSessionDto(sessionId, sessionSeed, sessionDimension, WorkerSessionDto.STATUS_OPEN);
    }

    /** 关闭会话并释放离线世界缓存。 */
    public WorkerSessionDto closeSession() {
        String closedId = sessionId;
        long closedSeed = sessionSeed;
        String closedDimension = sessionDimension;
        releaseSessions();
        sessionOpen = false;
        sessionId = "";
        sessionSeed = 0L;
        sessionDimension = "";
        lastHeldChunks = 0;
        LOGGER.info("会话已关闭：{}（种子 {}，维度 {}），离线区块缓存已释放",
                closedId.isEmpty() ? "（无）" : closedId, closedSeed, closedDimension);
        return new WorkerSessionDto(closedId, closedSeed, closedDimension, WorkerSessionDto.STATUS_CLOSED);
    }

    /**
     * 预测一个目标区块的钻石。
     *
     * @throws WorkerProtocolException 会话不匹配 / 维度或矿物不受支持（一律 fail-closed）
     */
    public WorkerPredictionDto predict(long seed, String dimension, int chunkX, int chunkZ, String oreType) {
        if (!sessionOpen) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_NO_SESSION, "尚未打开会话");
        }
        if (sessionSeed != seed || !sessionDimension.equals(dimension)) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_NO_SESSION,
                    "请求种子 / 维度与会话不一致（会话 " + sessionSeed + " / " + sessionDimension + "）");
        }
        if (oreType != null && !DIAMOND_NAME.equals(oreType)) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本阶段只支持" + DIAMOND_NAME + "，收到 " + oreType);
        }
        ChunkPos target = new ChunkPos(chunkX, chunkZ);
        DiamondSeedPredictor target0 = predictor();
        long startedAt = System.currentTimeMillis();
        PredictionResult result = target0.predict(seed, target);
        long roundTrip = System.currentTimeMillis() - startedAt;
        totalPredictions++;
        lastHeldChunks = result.stats().heldChunks();
        if (result.stats().hostChunkSourceQueries() != 0) {
            // 口径第十六、四十七节：这是「Worker 偷生成真实 Chunk」的唯一硬指标，非 0 一律按回归上报
            LOGGER.error("宿主 ChunkMap 查询增量 {} ≠ 0：预测主链正在向真实世界取数据，属架构回归",
                    result.stats().hostChunkSourceQueries());
        }
        LOGGER.info("预测完成：{} 种子 {} 区块 ({},{}) → {} 个（调度敏感 {} / 未解析 {}），"
                        + "耗时 {} ms（Worker 侧往返 {} ms），缓存区块 {}，宿主 ChunkMap 查询 {}",
                sessionId, seed, chunkX, chunkZ, result.count(), result.scheduleSensitiveCount(),
                result.unresolvedCount(), result.elapsedMillis(), roundTrip,
                result.stats().heldChunks(), result.stats().hostChunkSourceQueries());
        return toDto(result);
    }

    @Override
    public void close() {
        releaseSessions();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 内部实现
    // ────────────────────────────────────────────────────────────────────────

    /** 取（必要时创建）正式预测器；只允许在 worldgen executor 上调用。 */
    private DiamondSeedPredictor predictor() {
        if (predictor == null) {
            predictor = new DiamondSeedPredictor(host.overworld());
            LOGGER.info("正式预测器已创建（宿主维度 {}，宿主监听 {}）",
                    host.overworld().dimension().identifier(), host.listenerStateCn());
        }
        return predictor;
    }

    /** 释放全部会话（离线世界缓存随之回收）。 */
    private void releaseSessions() {
        if (predictor == null) {
            return;
        }
        try {
            predictor.clear();
        } catch (Throwable error) {
            LOGGER.warn("释放离线世界缓存时出现异常（继续）", error);
        }
    }

    /** 正式结果 → 传输 DTO（口径第五十六、五十七节：DTO 只是传输层，业务语义原样搬运）。 */
    private static WorkerPredictionDto toDto(PredictionResult result) {
        PredictionResult.Stats stats = result.stats();
        List<WorkerOreDto> ores = new ArrayList<>(result.ores().size());
        for (PredictedOre ore : result.ores()) {
            List<WorkerChunkRef> writers = new ArrayList<>(ore.conflictingWriters().size());
            for (ChunkPos writer : ore.conflictingWriters()) {
                writers.add(new WorkerChunkRef(writer.x(), writer.z()));
            }
            ChunkPos origin = ore.originViewer();
            ores.add(new WorkerOreDto(
                    ore.position().getX(), ore.position().getY(), ore.position().getZ(),
                    ore.oreType().name(), ore.certainty().name(), ore.source().name(),
                    origin == null ? null : new WorkerChunkRef(origin.x(), origin.z()),
                    writers));
        }
        WorkerStatsDto statsDto = new WorkerStatsDto(
                stats.protoChunks(), stats.stageExecutions(), stats.cacheHits(), stats.heldChunks(),
                stats.hostChunkSourceQueries(), stats.foreignWriterViewers(), stats.scheduleAnalysisExecuted(),
                stats.deterministicCount(), stats.scheduleSensitiveCount(), stats.unresolvedCount(),
                stats.notes());
        return new WorkerPredictionDto(
                result.success(), result.failureReason(),
                result.request().chunk().x(), result.request().chunk().z(),
                result.request().seed(), result.request().dimension().identifier().toString(),
                result.request().oreType().name(), result.elapsedMillis(), statsDto, ores);
    }
}
