package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.model.SeedOreTarget;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * 种子挖矿正式模块 · <b>种子矿物预测器</b>（正式对外 API；236 由 {@code DiamondSeedPredictor} 通用化而来）。
 *
 * <p><b>名字为什么改</b>：233~235 期间它只算钻石，类名里的 Diamond 是事实描述；236 起它按
 * {@code (维度, 矿物)} 定义表工作，覆盖主世界 8 种与下界 3 种矿物，再叫 DiamondSeedPredictor
 * 就会误导调用方以为「换矿物要换预测器」。<b>改名不改算法</b>：会话复用、viewer 集合推导、
 * 逐 viewer 差集、调度敏感复核、统计口径全部逐行保持 233/235 的样子（钻石冻结数字因此不变）。</p>
 *
 * <p><b>调用形态</b>：调用者只需要一个「种子 + 维度 + 目标区块 + 矿物」，<b>不需要</b>知道
 * {@code OfflineChunkPipeline} / {@code ProtoChunk} / {@code WorldGenRegion} / {@code FeatureSorter}
 * 这些内部细节：</p>
 *
 * <pre>{@code
 * try (SeedOrePredictor predictor = new SeedOrePredictor(overworldLevel)) {
 *     PredictionResult result = predictor.predict(
 *             SeedOreTarget.of(seed, Level.OVERWORLD, new ChunkPos(0, 0), OreType.REDSTONE));
 *     if (result.success()) {
 *         for (PredictedOre ore : result.ores()) { ... }
 *     }
 * }
 * }</pre>
 *
 * <p><b>输入边界</b>：只吃 种子 + 维度 + 目标区块 + 矿物 + 当前版本原版注册表 / noise settings /
 * worldgen 配置。宿主 {@code ServerLevel} 只当「环境宿主」，提供与种子无关的环境参数
 * （维度类型、世界高度、注册表、模板管理器、调色板工厂）。
 * <b>禁止读取</b>真实 Server Chunk BlockState / 真实矿石位置 / 装饰批号 / FEATURES 调度顺序；
 * 一旦偷读即视为预测失败。本模块通过 {@code OfflineChunkRegion#getChunkSource()} 的计数把这一点
 * 变成可核对数字（{@link PredictionResult.Stats#hostChunkSourceQueries()} 必须为 0）。</p>
 *
 * <p><b>会话隔离与复用</b>：会话键 = （Minecraft 版本, 种子, 维度）。
 * 同一个键下多个相邻目标区块复用同一份离线世界（BiomeSource / 生成器 / RandomState / 结构状态 /
 * ProtoChunk 表 / 已完成的生成阶段），<b>与矿物无关</b> —— 同维度同种子下换矿物不会重建世界，
 * 因为矿物只是「在同一份世界里找什么」，不改变世界本身。
 * {@link #invalidate(long, ResourceKey)} 用于「种子被改」这类失效场景，
 * {@link #close()} 用于「退出世界」这类整体释放。</p>
 *
 * <p><b>异步友好</b>：本类不引用任何客户端全局单例，环境由构造参数传入；
 * 会话内部全部同步（同一会话同一时刻只被一个线程驱动），因此调用方可以整体把它放进后台任务，
 * 只需保证「一个会话不被两个线程同时调用」。离线 worldgen 可能较重（冷启动目标要铺
 * 19x19 区块的前置阶段），<b>不要</b>在渲染线程上同步调用。</p>
 */
public final class SeedOrePredictor implements AutoCloseable {

    /**
     * 当前 Minecraft 版本标识（会话隔离键的一部分）。
     *
     * <p>取 {@code SharedConstants.getCurrentVersion().id()}（26.1.2 本线源码：WorldVersion.java:11），
     * 不写死版本号、也不用已弃用的 {@code SharedConstants.WORLD_VERSION} 常量。</p>
     */
    private static final String VERSION_ID = SharedConstants.getCurrentVersion().id();

    /** 环境宿主（只取与种子无关的环境参数）。 */
    private final ServerLevel host;

    /** 宿主所属维度档案（构造时确立，本预测器只服务这一个维度）。 */
    private final SeedDimensionProfile profile;

    /** 会话表：键 = （版本, 种子, 维度）。 */
    private final Map<SessionKey, PredictionSession> sessions = new HashMap<>();

    /**
     * @param host 环境宿主；必须是某个受支持维度的那一层（主世界 / 下界），
     *             且高度与原版该维度一致（否则离线 worldgen 的高度口径不成立）
     */
    public SeedOrePredictor(ServerLevel host) {
        SeedDimensionProfile resolved = SeedDimensionProfile.of(host.dimension());
        if (resolved == null) {
            throw new IllegalArgumentException("种子挖矿不支持该维度宿主：" + host.dimension().identifier()
                    + "（受支持：" + SeedDimensionProfile.OVERWORLD.dimensionId() + " / "
                    + SeedDimensionProfile.NETHER.dimensionId() + "）");
        }
        if (!resolved.matchesHost(host)) {
            throw new IllegalArgumentException("宿主维度高度与原版该维度不一致（宿主 y " + host.getMinY()
                    + "~" + host.getMaxY() + "，档案 " + resolved.minY() + "~" + resolved.maxY() + "）");
        }
        this.host = host;
        this.profile = resolved;
    }

    /** 本预测器的维度档案。 */
    public SeedDimensionProfile profile() {
        return profile;
    }

    /** 环境宿主（诊断用）。 */
    public ServerLevel host() {
        return host;
    }

    /**
     * 预测一个目标区块里的指定矿物。
     *
     * @param seed    种子
     * @param oreType 矿物（必须在本维度受支持，见 {@link SeedOreRegistry}）
     * @param target  目标区块
     */
    public PredictionResult predict(long seed, OreType oreType, ChunkPos target) {
        return predict(SeedOreTarget.of(seed, profile.levelKey(), target, oreType));
    }

    /** 预测主世界钻石（235 及以前的默认组合，供既有开发装置沿用）。 */
    public PredictionResult predictDiamond(long seed, ChunkPos target) {
        if (profile != SeedDimensionProfile.OVERWORLD) {
            return PredictionResult.failure(SeedOreTarget.of(seed, profile.levelKey(), target, OreType.DIAMOND),
                    "本预测器的宿主是" + profile.displayNameCn() + "，不能预测主世界钻石",
                    System.currentTimeMillis());
        }
        return predict(seed, OreType.DIAMOND, target);
    }

    /**
     * 预测一个目标区块。
     *
     * <p>不支持的方向一律返回<b>失败结果</b>（而不是空集合），
     * 保证「预测不成立」与「这里没有矿」在调用侧可区分。</p>
     */
    public PredictionResult predict(SeedOreTarget request) {
        long startedAt = System.currentTimeMillis();
        SeedDimensionProfile requestProfile = SeedDimensionProfile.of(request.dimension());
        if (requestProfile == null) {
            return PredictionResult.failure(request, "本阶段不支持该维度（"
                    + request.dimension().identifier() + "）", System.currentTimeMillis() - startedAt);
        }
        if (requestProfile != profile) {
            return PredictionResult.failure(request, "请求维度（" + requestProfile.displayNameCn()
                    + "）与本预测器的宿主维度（" + profile.displayNameCn() + "）不一致",
                    System.currentTimeMillis() - startedAt);
        }
        if (!SeedOreRegistry.supports(profile, request.oreType())) {
            return PredictionResult.failure(request, profile.displayNameCn() + "不支持"
                    + request.oreType().displayNameCn() + "预测", System.currentTimeMillis() - startedAt);
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
     * <p>刻意<b>不</b>含矿物：同一份离线世界可以回答任意矿物的提问（矿物只决定「读哪一段 Y、
     * 什么方块算命中」，不改变世界生成了什么）。把它放进键里只会让「同区块换矿物」白白重建世界。</p>
     */
    private record SessionKey(String versionId, long seed, ResourceKey<Level> dimension) {
    }
}
