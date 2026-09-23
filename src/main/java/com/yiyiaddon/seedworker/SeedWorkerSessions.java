package com.yiyiaddon.seedworker;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.prediction.SeedOrePredictor;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerChunkRef;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerOreDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerSessionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerStatsDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · Worker · <b>会话与预测</b>（正式 Predictor 在 Worker 侧的宿主包装）。
 *
 * <p><b>算法一行未改</b>：这里做的是「把客户端递进来的 种子 / 维度 / 目标区块 / 矿物交给正式
 * {@link SeedOrePredictor}，再把 {@link PredictionResult} 转成传输 DTO」。因此 Worker 与单人 Oracle
 * 必然逐 BlockPos 一致 —— 一致性来自「同一份代码」，而不是来自「两边都调对了参数」。</p>
 *
 * <p><b>236：按维度持有预测器</b>。预测器的宿主是<b>某一层 ServerLevel</b>，而离线 worldgen 的
 * 预设 / 噪声设置 / 世界高度都按维度不同（下界还要换随机算法，见
 * {@link SeedDimensionProfile}），因此这里改成每个受支持维度各持一个预测器实例，
 * 而不是「一个 predict 打天下」。同维度的会话（种子）仍然复用同一份离线世界。</p>
 *
 * <p><b>线程模型</b>：本类的全部方法都只在 Worker 的<b>单线程 worldgen executor</b> 上执行
 * （唯一调用者是 {@link SeedWorkerIpcServer}），因此不需要任何锁，也不会出现两个预测
 * 同时抢 CPU / 内存。{@link #sessionId()} 等只读读数供日志使用，声明为 volatile。</p>
 *
 * <p><b>会话身份</b>：会话 = （种子 + 维度）。同一个会话下多次预测复用同一份离线世界与缓存
 * （相邻区块因此更快）；种子或维度变了，旧会话<b>立刻释放</b>，绝不允许两个种子共用一份缓存。</p>
 */
public final class SeedWorkerSessions implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed-worker");

    private final SeedWorkerHost host;

    /** 会话编号（只用于日志关联）。 */
    private final AtomicLong sessionCounter = new AtomicLong();

    /** 维度 → 正式预测器（只在 worldgen executor 上创建与使用）。 */
    private final Map<SeedDimensionProfile, SeedOrePredictor> predictors = new LinkedHashMap<>();

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

    /** 当前环境具备哪些维度的宿主（诊断；形如 {@code [overworld, the_nether]}）。 */
    public String availableDimensionsCn() {
        List<String> available = new ArrayList<>();
        for (SeedDimensionProfile profile : SeedDimensionProfile.values()) {
            if (host.levelOrNull(profile.levelKey()) != null) {
                available.add(profile.displayNameCn());
            }
        }
        return available.toString();
    }

    /**
     * 本 Worker 此刻能给出的能力声明（按「宿主真的具备哪一层世界」逐条给出）。
     *
     * <p>客户端用手里的能力清单<b>提前</b>判断「这个本地计算器能不能算某个维度」，
     * 而不是等用户点了预测再报错。</p>
     */
    public List<String> capabilities() {
        List<String> capabilities = new ArrayList<>(SeedDimensionProfile.values().length);
        for (SeedDimensionProfile profile : SeedDimensionProfile.values()) {
            if (host.levelOrNull(profile.levelKey()) == null) {
                continue;
            }
            capabilities.add(switch (profile) {
                case OVERWORLD -> SeedWorkerProtocol.CAPABILITY_PREDICT_OVERWORLD;
                case NETHER -> SeedWorkerProtocol.CAPABILITY_PREDICT_NETHER;
            });
        }
        return List.copyOf(capabilities);
    }

    /**
     * 打开会话（种子 + 维度）。
     *
     * <p>维度不受支持、或本环境不具备该维度的宿主时一律拒绝（fail-closed）：
     * 绝不退回主世界参数硬算另一个维度。种子或维度与当前不同 ⇒ 释放旧会话的全部离线世界，
     * 随后同一会话下的多次预测继续复用缓存。</p>
     */
    public WorkerSessionDto openSession(long seed, String dimension) {
        SeedDimensionProfile profile = SeedDimensionProfile.of(dimension);
        if (profile == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "不支持该维度：" + dimension + "（受支持：" + SeedDimensionProfile.describeAllCn() + "）");
        }
        ServerLevel level = host.levelOrNull(profile.levelKey());
        if (level == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本环境的宿主不具备" + profile.displayNameCn() + "那一层，无法为该维度预测"
                            + "（具备的维度：" + availableDimensionsCn() + "）");
        }
        if (sessionOpen && (sessionSeed != seed || !sessionDimension.equals(dimension))) {
            LOGGER.info("会话切换：({} / {}) → ({} / {})，旧会话的离线世界全部释放",
                    sessionId, sessionSeed, seed, dimension);
            releaseSessions();
            // 会话身份必须整体作废，不能只释放离线世界：否则后续请求会撞上
            // 「请求维度与会话不一致」而被判 NO_SESSION 拒绝。
            // （236 实机：主世界 → 下界第一次预测全部失败，就是这里漏了重置。）
            sessionOpen = false;
            sessionId = "";
            sessionSeed = 0L;
            sessionDimension = "";
            lastHeldChunks = 0;
        }
        if (!sessionOpen) {
            sessionId = "s" + sessionCounter.incrementAndGet() + "-" + Long.toUnsignedString(seed, 16);
            sessionSeed = seed;
            sessionDimension = dimension;
            sessionOpen = true;
            LOGGER.info("会话已打开：{}（种子 {}，维度 {}，宿主高度 {} ~ {}）", sessionId, seed, dimension,
                    level.getMinY(), level.getMaxY());
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
     * 预测一个目标区块里的指定矿物。
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
        SeedDimensionProfile profile = SeedDimensionProfile.of(dimension);
        if (profile == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED, "不支持该维度：" + dimension);
        }
        OreType requested = OreType.parse(oreType);
        if (requested == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "不认识的矿物种类：" + oreType);
        }
        if (!SeedOreRegistry.supports(profile, requested)) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    profile.displayNameCn() + "不支持" + requested.displayNameCn() + "（受支持："
                            + SeedOreRegistry.describeSupportedOresCn(profile) + "）");
        }
        ChunkPos target = new ChunkPos(chunkX, chunkZ);
        SeedOrePredictor predictor = predictorFor(profile);
        long startedAt = System.currentTimeMillis();
        PredictionResult result = predictor.predict(seed, requested, target);
        long roundTrip = System.currentTimeMillis() - startedAt;
        totalPredictions++;
        lastHeldChunks = result.stats().heldChunks();
        if (result.stats().hostChunkSourceQueries() != 0) {
            // 这是「Worker 偷生成真实 Chunk」的唯一硬指标，非 0 一律按回归上报
            LOGGER.error("宿主 ChunkMap 查询增量 {} ≠ 0：预测主链正在向真实世界取数据，属架构回归",
                    result.stats().hostChunkSourceQueries());
        }
        LOGGER.info("预测完成：{} 种子 {} {} 区块 ({},{}) → {} 个（调度敏感 {} / 未解析 {}），"
                        + "耗时 {} ms（Worker 侧往返 {} ms），缓存区块 {}，宿主 ChunkMap 查询 {}",
                sessionId, seed, requested.displayNameCn(), chunkX, chunkZ, result.count(),
                result.scheduleSensitiveCount(), result.unresolvedCount(), result.elapsedMillis(), roundTrip,
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

    /** 取（必要时创建）某维度的正式预测器；只允许在 worldgen executor 上调用。 */
    private SeedOrePredictor predictorFor(SeedDimensionProfile profile) {
        SeedOrePredictor existing = predictors.get(profile);
        if (existing != null) {
            return existing;
        }
        ServerLevel level = host.level(profile.levelKey());
        SeedOrePredictor created = new SeedOrePredictor(level);
        predictors.put(profile, created);
        LOGGER.info("正式预测器已创建（{}，宿主高度 {} ~ {}，宿主监听 {}）", profile.describeCn(),
                level.getMinY(), level.getMaxY(), host.listenerStateCn());
        return created;
    }

    /** 释放全部会话（离线世界缓存随之回收）。 */
    private void releaseSessions() {
        for (SeedOrePredictor predictor : predictors.values()) {
            try {
                predictor.clear();
            } catch (Throwable error) {
                LOGGER.warn("释放离线世界缓存时出现异常（继续）", error);
            }
        }
    }

    /** 正式结果 → 传输 DTO（DTO 只是传输层，业务语义原样搬运）。 */
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
