package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.SeedOreTarget;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * 种子挖矿正式模块 · 钻石 Seed 预测器（正式对外 API）。
 *
 * <p><b>调用形态</b>（正式化第一阶段口径第十三节）：调用者只需要一个「种子 + 维度 + 目标区块」，
 * <b>不需要</b>知道 {@code OfflineChunkPipeline} / {@code ProtoChunk} / {@code WorldGenRegion} /
 * {@code FeatureSorter} 这些内部细节：</p>
 *
 * <pre>{@code
 * try (DiamondSeedPredictor predictor = new DiamondSeedPredictor(overworldLevel)) {
 *     PredictionResult result = predictor.predict(SeedOreTarget.diamond(seed, new ChunkPos(0, 0)));
 *     if (result.success()) {
 *         for (PredictedOre ore : result.ores()) { ... }
 *     }
 * }
 * }</pre>
 *
 * <p><b>输入边界</b>（口径第十四节）：只吃 种子 + 维度 + 目标区块 + 当前版本原版注册表 /
 * noise settings / worldgen 配置。宿主 {@code ServerLevel} 只当「环境宿主」，
 * 提供与种子无关的环境参数（维度类型、世界高度、注册表、模板管理器、调色板工厂）。
 * <b>禁止读取</b>真实 Server Chunk BlockState / pre-diamond / 矿石位置 / 装饰 batch /
 * 首次生成历史 / FEATURES 调度顺序；一旦偷读即视为预测失败。本模块通过
 * {@code OfflineChunkRegion#getChunkSource()} 的计数把这一点变成可核对数字
 * （{@link PredictionResult.Stats#hostChunkSourceQueries()} 必须为 0）。</p>
 *
 * <p><b>会话隔离与复用</b>（口径第十八、十九节）：会话键 = （Minecraft 版本, 种子, 维度）。
 * 同一个键下多个相邻目标区块复用同一份离线世界（BiomeSource / 生成器 / RandomState / 结构状态 /
 * ProtoChunk 表 / 已完成的生成阶段），不会每预测一个矿就重新生成整个世界。
 * {@link #invalidate(long, ResourceKey)} 用于「种子被改」这类失效场景，
 * {@link #close()} 用于「退出世界」这类整体释放。</p>
 *
 * <p><b>异步友好</b>（口径第二十节）：本类不引用任何客户端全局单例，环境由构造参数传入；
 * 会话内部全部同步（同一会话同一时刻只被一个线程驱动），因此调用方可以整体把它放进后台任务，
 * 只需保证「一个会话不被两个线程同时调用」。离线 worldgen 可能较重（冷启动目标要铺
 * 19x19 区块的前置阶段），<b>不要</b>在渲染线程上同步调用。</p>
 */
public final class DiamondSeedPredictor implements AutoCloseable {

    /** 本阶段唯一被支持的维度。 */
    private static final ResourceKey<Level> SUPPORTED_DIMENSION = Level.OVERWORLD;

    /**
     * 当前 Minecraft 版本标识（会话隔离键的一部分）。
     *
     * <p>取 {@code SharedConstants.getCurrentVersion().id()}（26.1.2 本线源码：WorldVersion.java:11），
     * 不写死版本号、也不用已弃用的 {@code SharedConstants.WORLD_VERSION} 常量。</p>
     */
    private static final String VERSION_ID = SharedConstants.getCurrentVersion().id();

    /** 环境宿主（只取与种子无关的环境参数）。 */
    private final ServerLevel host;

    /** 会话表：键 = （版本, 种子, 维度）。 */
    private final Map<SessionKey, PredictionSession> sessions = new HashMap<>();

    /**
     * @param host 环境宿主；必须是主世界那一层（本阶段只支持主世界）
     */
    public DiamondSeedPredictor(ServerLevel host) {
        if (!SUPPORTED_DIMENSION.equals(host.dimension())) {
            throw new IllegalArgumentException("种子挖矿本阶段只支持主世界宿主，当前宿主维度="
                    + host.dimension().identifier());
        }
        this.host = host;
    }

    /**
     * 预测一个目标区块（主世界钻石）。
     *
     * @param seed   种子
     * @param target 目标区块
     */
    public PredictionResult predict(long seed, ChunkPos target) {
        return predict(SeedOreTarget.diamond(seed, target));
    }

    /**
     * 预测一个目标区块。
     *
     * <p>不支持的维度 / 不支持的矿物一律返回<b>失败结果</b>（而不是空集合），
     * 保证「预测不成立」与「这里没有矿」在调用侧可区分。</p>
     */
    public PredictionResult predict(SeedOreTarget request) {
        long startedAt = System.currentTimeMillis();
        if (!SUPPORTED_DIMENSION.equals(request.dimension())) {
            return PredictionResult.failure(request, "本阶段只支持主世界（"
                    + SUPPORTED_DIMENSION.identifier() + "）", System.currentTimeMillis() - startedAt);
        }
        PredictionSession session;
        try {
            session = sessionFor(request.seed(), request.dimension());
        } catch (RuntimeException error) {
            return PredictionResult.failure(request,
                    "会话创建失败：" + error.getClass().getSimpleName() + (error.getMessage() == null
                            ? "" : ": " + error.getMessage()),
                    System.currentTimeMillis() - startedAt);
        }
        return session.predict(request);
    }

    /**
     * 让某个「种子 + 维度」的会话失效（种子被修改 / 配置改变时调用）。
     *
     * <p>调用后该键下的离线世界被释放，下一次预测会重新构造；其它键的会话不受影响。</p>
     */
    public synchronized void invalidate(long seed, ResourceKey<Level> dimension) {
        PredictionSession session = sessions.remove(new SessionKey(VERSION_ID, seed, dimension));
        if (session != null) {
            session.close();
        }
    }

    /** 释放全部会话（退出世界 / 停止功能时调用）。 */
    public synchronized void clear() {
        for (PredictionSession session : sessions.values()) {
            session.close();
        }
        sessions.clear();
    }

    /** 当前会话数（报告用）。 */
    public synchronized int sessionCount() {
        return sessions.size();
    }

    /** 全部会话累计持有的离线区块数（报告用：缓存规模）。 */
    public synchronized int cachedChunks() {
        int total = 0;
        for (PredictionSession session : sessions.values()) {
            total += session.cachedChunks() + session.probeCachedChunks();
        }
        return total;
    }

    @Override
    public synchronized void close() {
        clear();
    }

    /** 取（必要时新建）某个「种子 + 维度」的会话。 */
    private synchronized PredictionSession sessionFor(long seed, ResourceKey<Level> dimension) {
        SessionKey key = new SessionKey(VERSION_ID, seed, dimension);
        PredictionSession existing = sessions.get(key);
        if (existing != null && !existing.isClosed()) {
            return existing;
        }
        PredictionSession created = PredictionSession.open(host, seed, dimension);
        sessions.put(key, created);
        return created;
    }

    /**
     * 会话键：<b>版本 + 种子 + 维度</b> 三重隔离。
     *
     * <p>版本取 {@link SharedConstants#getCurrentVersion()} 的 {@code id()}（26.1.2 本线源码：
     * WorldVersion.java:11），不写死版本号，避免版本升级后出现「跨版本复用同一份缓存」这种静默错误。</p>
     */
    private record SessionKey(String versionId, long seed, ResourceKey<Level> dimension) {
    }
}
