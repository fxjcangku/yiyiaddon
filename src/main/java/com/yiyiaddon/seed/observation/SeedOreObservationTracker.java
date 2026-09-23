package com.yiyiaddon.seed.observation;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式模块 · <b>实际 Chunk 观察器</b>（正式化第五阶段 233）。
 *
 * <p><b>预测与观察必须分开</b>（口径第七节）：本类<b>只回答</b>「客户端当前实际看到的这个位置是什么」。
 * 它不参与任何预测、不把真实方块喂给 Predictor、也不因为「服务器显示钻石但预测里没有」就下任何结论。</p>
 *
 * <p><b>三条硬规则</b>：</p>
 * <ol>
 *     <li><b>绝不主动加载区块</b>（口径第二十五节 P0 红线）：判断「区块在不在」用
 *         {@link ClientChunkCache#getChunk(int, int, ChunkStatus, boolean)} 且 {@code loadOrGenerate}
 *         恒为 {@code false}（本类只有 {@link #LOAD_OR_GENERATE} 一个常量，值写死 false）；
 *         区块不在就<b>直接</b>是 {@link OreObservationState#UNOBSERVED}，
 *         不请求、不催、不影响服务器的区块下发。</li>
 *     <li><b>只查候选位置</b>（口径第二十六节）：复杂度是 O(候选数)，不是 O(16×384×16)。
 *         本类只对「预测缓存里出现过的那几个 BlockPos」读方块状态，绝不扫描整区块找钻石。</li>
 *     <li><b>只在客户端主线程读世界</b>（口径第二十七节）：全部入口都由客户端主线程驱动
 *         （区块事件、方块更新事件、结果回投），后台线程只读 {@link #snapshot()} 那份不可变快照。</li>
 * </ol>
 *
 * <p><b>状态语义</b>（口径第九~十三节）：{@link OreObservationState#UNOBSERVED} 表示「客户端还没合法看到」；
 * {@link OreObservationState#CONFIRMED} 表示「现在看到的就是钻石矿」；
 * {@link OreObservationState#MISSING} 表示「现在看到的不是钻石矿」，<b>仅此而已</b> ——
 * 不代表假矿、不代表服务器作弊、更不代表种子错（口径第十一节列出的可能原因一个都不排除）。
 * {@link OreObservationState#SUSPICIOUS} 在 233 <b>一律不产生</b>（口径第十二节）。</p>
 *
 * <p><b>区块卸载</b>（口径第三十节）：客户端不再持有该区块时，{@code CONFIRMED} / {@code MISSING}
 * 必须回到 {@link OreObservationState#UNOBSERVED} —— 233 的定义是「当前实际观察状态」，
 * 卸载后不能假装还知道。最近一次看到的方块与时间只留作开发诊断（{@link #lastObservedBlockId}），
 * 不参与任何正式判定。</p>
 */
public final class SeedOreObservationTracker {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词。 */
    private static final String LOG_KEY = "种子挖矿｜观察";

    /**
     * 「取区块时是否允许加载 / 生成」——<b>恒为 false</b>（口径第二十五节 P0 红线）。
     *
     * <p>写成常量而不是字面量，是为了让这条红线在代码里只有一处可改，并且改它就等于改架构：</p>
     * <pre>
     * ClientChunkCache#getChunk(x, z, ChunkStatus.FULL, false)
     *     → 未加载时返回 null（客户端不会向服务器请求任何东西）
     * ClientChunkCache#getChunk(x, z, ChunkStatus.FULL, true)
     *     → 未加载时返回一个 EmptyLevelChunk（看似「读到了空气」），这才是把 MISSING 误判出来的根源
     * </pre>
     */
    private static final boolean LOAD_OR_GENERATE = false;

    private static final SeedOreObservationTracker INSTANCE = new SeedOreObservationTracker();

    /** 是否已经装过 Fabric 区块事件（重复安装无副作用）。 */
    private static boolean hooksInstalled;

    // ── 候选索引（由服务层在预测结果落地时维护） ──

    /** 区块键 → 该区块内的候选位置键（卸载 / 淘汰时按区块成批清理）。 */
    private final Map<Long, List<Long>> candidatesByChunk = new HashMap<>();

    /** 候选位置键 → 矿物种类（判断「这个坐标是不是候选」只需一次查表）。 */
    private final Map<Long, OreType> candidateTypes = new HashMap<>();

    // ── 观察状态 ──

    /** 位置键 → 当前观察状态；<b>只放 CONFIRMED / MISSING</b>，缺席即 {@code UNOBSERVED}。 */
    private final Map<Long, OreObservationState> states = new HashMap<>();

    /** 位置键 → 最近一次看到的方块注册名（开发诊断用，不参与正式判定）。 */
    private final Map<Long, String> lastObservedBlockIds = new HashMap<>();

    /** 位置键 → 最近一次观察时间（毫秒，开发诊断用）。 */
    private final Map<Long, Long> lastObservedAtMillis = new HashMap<>();

    // ── 计数与快照（增量维护，避免每帧重算） ──

    private int candidateCount;
    private int confirmedCount;
    private int missingCount;

    private SeedObservationSnapshot cachedSnapshot = SeedObservationSnapshot.EMPTY;
    private boolean dirty = true;

    /**
     * 修订号：任何候选 / 观察状态变化都会 +1。
     *
     * <p>服务层每刻只比较这一个 {@code long} 就能知道「观察这一维有没有变、要不要重建渲染快照」，
     * 不需要本类反过来依赖渲染层（依赖方向保持 observation → 无人）。</p>
     */
    private long revision;

    /** 当前绑定的客户端世界；{@code null} = 未绑定（此时所有观察入口一律直接返回）。 */
    private ClientLevel boundLevel;

    private SeedOreObservationTracker() {
    }

    /** 单例（服务层、区块事件、方块更新钩子共用同一份状态）。 */
    public static SeedOreObservationTracker instance() {
        return INSTANCE;
    }

    /**
     * 安装 Fabric 区块事件（唯一的区块加载 / 卸载发现入口，口径第二十四节优先官方事件）。
     *
     * <p>{@code CHUNK_LOAD} 在区块<b>已经进入客户端世界之后</b>触发、{@code CHUNK_UNLOAD} 在区块
     * <b>被移除之前</b>触发（见 Fabric 源码 {@code ClientChunkCacheMixin}），两者都在客户端主线程，
     * 正好满足「加载后立即观察、卸载时先清状态」。</p>
     */
    public static void installHooks() {
        if (hooksInstalled) {
            return;
        }
        hooksInstalled = true;
        ClientChunkEvents.CHUNK_LOAD.register((level, chunk) ->
                INSTANCE.onChunkLoaded(level, chunk));
        ClientChunkEvents.CHUNK_UNLOAD.register((level, chunk) ->
                INSTANCE.onChunkUnloaded(level, chunk));
        LOGGER.info("{}：已安装客户端区块加载 / 卸载监听（Fabric ClientChunkEvents）", LOG_KEY);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 生命周期（服务层调用）
    // ────────────────────────────────────────────────────────────────────────

    /** 绑定当前客户端世界（身份重建时调用）；绑定后才会响应区块与方块更新。 */
    public void bind(ClientLevel level) {
        boundLevel = level;
    }

    /** 解除绑定并清空全部状态（换种子 / 换维度 / 换服 / 退世界 / 关功能）。 */
    public void unbind() {
        boundLevel = null;
        clear();
    }

    /** 清空全部候选与观察状态（不改绑定）。 */
    public void clear() {
        candidatesByChunk.clear();
        candidateTypes.clear();
        states.clear();
        lastObservedBlockIds.clear();
        lastObservedAtMillis.clear();
        candidateCount = 0;
        confirmedCount = 0;
        missingCount = 0;
        markChanged();
    }

    /** 修订号（任何变化都会 +1；服务层据此决定是否重建渲染快照）。 */
    public long revision() {
        return revision;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 候选登记（由服务层在预测结果落地 / 缓存淘汰时调用）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 登记一个目标区块的预测候选；若该区块此刻已加载，<b>立即观察一次</b>（口径第二十八节 A）。
     *
     * @return 新登记的候选数
     */
    public int trackChunk(PredictionResult result) {
        if (result == null || result.failed()) {
            return 0;
        }
        ChunkPos chunk = result.request().chunk();
        untrackChunk(chunk);
        List<Long> keys = new ArrayList<>(result.ores().size());
        for (PredictedOre ore : result.ores()) {
            long key = ore.position().asLong();
            candidateTypes.put(key, ore.oreType());
            keys.add(key);
            candidateCount++;
        }
        if (!keys.isEmpty()) {
            candidatesByChunk.put(chunk.pack(), keys);
        }
        markChanged();
        observeChunk(chunk);
        return keys.size();
    }

    /** 注销一个目标区块的候选（缓存淘汰时调用）：候选移除，观察状态一并丢弃。 */
    public void untrackChunk(ChunkPos chunk) {
        if (chunk == null) {
            return;
        }
        List<Long> keys = candidatesByChunk.remove(chunk.pack());
        if (keys == null) {
            return;
        }
        for (long key : keys) {
            candidateTypes.remove(key);
            candidateCount--;
            transitionTo(key, null);
            lastObservedBlockIds.remove(key);
            lastObservedAtMillis.remove(key);
        }
        markChanged();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 观察入口
    // ────────────────────────────────────────────────────────────────────────

    /** 区块进入客户端（Fabric {@code CHUNK_LOAD}）：把该区块内的候选逐个观察一遍。 */
    public void onChunkLoaded(ClientLevel level, LevelChunk chunk) {
        if (chunk == null || !isBound(level)) {
            return;
        }
        observeChunk(chunk.getPos());
    }

    /** 区块即将离开客户端（Fabric {@code CHUNK_UNLOAD}）：状态回到 {@code UNOBSERVED}（口径第三十节）。 */
    public void onChunkUnloaded(ClientLevel level, LevelChunk chunk) {
        if (chunk == null || !isBound(level)) {
            return;
        }
        unobserveChunk(chunk.getPos());
    }

    /**
     * 客户端收到方块更新（{@code ClientLevel#setServerVerifiedBlockState} 之后）。
     *
     * <p>只对候选位置生效：不是候选的方块更新在这里 O(1) 被丢掉 —— 这正是口径第二十九节的要求
     * （不每刻重扫全部候选，只在真正相关的更新上做一次读取）。</p>
     *
     * @return 是否重新观察了该位置
     */
    public boolean onBlockUpdated(ClientLevel level, BlockPos pos) {
        if (pos == null || !isBound(level)) {
            return false;
        }
        long key = pos.asLong();
        if (!candidateTypes.containsKey(key)) {
            return false;
        }
        OreType oreType = candidateTypes.get(key);
        applyObservation(key, oreType, readLoadedBlock(level, pos));
        return true;
    }

    /** 观察某区块内的全部候选（区块刚加载 / 预测结果刚落地时调用）。 */
    public void observeChunk(ChunkPos chunk) {
        ClientLevel level = boundLevel;
        if (level == null || chunk == null) {
            return;
        }
        List<Long> keys = candidatesByChunk.get(chunk.pack());
        if (keys == null || keys.isEmpty()) {
            return;
        }
        for (long key : keys) {
            OreType oreType = candidateTypes.get(key);
            if (oreType == null) {
                continue;
            }
            applyObservation(key, oreType, readLoadedBlock(level, BlockPos.of(key)));
        }
    }

    /** 该区块的观察状态全部回到 {@code UNOBSERVED}（区块卸载）。 */
    public void unobserveChunk(ChunkPos chunk) {
        if (chunk == null) {
            return;
        }
        List<Long> keys = candidatesByChunk.get(chunk.pack());
        if (keys == null || keys.isEmpty()) {
            return;
        }
        for (long key : keys) {
            transitionTo(key, null);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读数
    // ────────────────────────────────────────────────────────────────────────

    /** 某个位置当前的观察状态（不是候选 / 未观察都返回 {@code UNOBSERVED}）。 */
    public OreObservationState stateOf(long positionKey) {
        OreObservationState state = states.get(positionKey);
        return state == null ? OreObservationState.UNOBSERVED : state;
    }

    /** 某个位置当前的观察状态（BlockPos 形态）。 */
    public OreObservationState stateOf(BlockPos pos) {
        return pos == null ? OreObservationState.UNOBSERVED : stateOf(pos.asLong());
    }

    /** 该位置是否已被登记为候选。 */
    public boolean isCandidate(BlockPos pos) {
        return pos != null && candidateTypes.containsKey(pos.asLong());
    }

    /** 最近一次看到的方块注册名（开发诊断用；从未看到返回空串）。 */
    public String lastObservedBlockId(BlockPos pos) {
        if (pos == null) {
            return "";
        }
        String id = lastObservedBlockIds.get(pos.asLong());
        return id == null ? "" : id;
    }

    /** 最近一次观察时间（毫秒时间戳；从未观察返回 0；开发诊断用）。 */
    public long lastObservedAt(BlockPos pos) {
        if (pos == null) {
            return 0L;
        }
        Long at = lastObservedAtMillis.get(pos.asLong());
        return at == null ? 0L : at;
    }

    /** 候选总数。 */
    public int candidateCount() {
        return candidateCount;
    }

    /**
     * 当前观察状态快照（增量维护；脏了才重建，重建代价 O(1)）。
     *
     * <p>界面对它每帧读取也不会产生额外计算 —— 计数是随状态迁移增量更新的。</p>
     */
    public SeedObservationSnapshot snapshot() {
        if (!dirty) {
            return cachedSnapshot;
        }
        int unobserved = Math.max(0, candidateCount - confirmedCount - missingCount);
        cachedSnapshot = new SeedObservationSnapshot(candidateCount, unobserved, confirmedCount, missingCount);
        dirty = false;
        return cachedSnapshot;
    }

    /** 一行诊断（日志 / 报告用）。 */
    public String describeCn() {
        return snapshot().describeCn() + "；已登记区块 " + candidatesByChunk.size()
                + "，绑定世界 " + (boundLevel == null ? "无" : "有");
    }

    // ────────────────────────────────────────────────────────────────────────
    // 内部
    // ────────────────────────────────────────────────────────────────────────

    private boolean isBound(ClientLevel level) {
        return level != null && level == boundLevel;
    }

    /**
     * 读一个候选位置的真实方块状态；<b>客户端没有该区块时返回 {@code null}</b>。
     *
     * <p>这是本模块唯一读世界的地方，也是 P0 红线的落点：{@code LOAD_OR_GENERATE} 恒为 false，
     * 因此这里永远不会创建 / 请求区块；未加载就是 null，上层直接落 UNOBSERVED。</p>
     */
    private static BlockState readLoadedBlock(ClientLevel level, BlockPos pos) {
        ClientChunkCache cache = level.getChunkSource();
        LevelChunk chunk = cache.getChunk(SectionPos.blockToSectionCoord(pos.getX()),
                SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.FULL, LOAD_OR_GENERATE);
        return chunk == null ? null : chunk.getBlockState(pos);
    }

    /** 把一次读取结果落成状态（null 视为未观察）。 */
    private void applyObservation(long key, OreType oreType, BlockState state) {
        if (state == null) {
            transitionTo(key, null);
            return;
        }
        lastObservedBlockIds.put(key, blockIdOf(state));
        lastObservedAtMillis.put(key, System.currentTimeMillis());
        transitionTo(key, isOreBlock(oreType, state)
                ? OreObservationState.CONFIRMED : OreObservationState.MISSING);
    }

    /** 状态迁移 + 增量计数 + 标脏。{@code next} 为 null 表示「回到未观察」。 */
    private void transitionTo(long key, OreObservationState next) {
        OreObservationState previous = states.get(key);
        if (previous == next) {
            return;
        }
        if (previous == OreObservationState.CONFIRMED) {
            confirmedCount--;
        } else if (previous == OreObservationState.MISSING) {
            missingCount--;
        }
        if (next == OreObservationState.CONFIRMED) {
            states.put(key, next);
            confirmedCount++;
        } else if (next == OreObservationState.MISSING) {
            states.put(key, next);
            missingCount++;
        } else {
            // UNOBSERVED 以及本阶段不产出的 SUSPICIOUS 一律按「未观察」处理（口径第九、十二节）
            states.remove(key);
        }
        markChanged();
    }

    /** 记住「观察这一维刚刚变了」：标脏 + 修订号 +1。 */
    private void markChanged() {
        dirty = true;
        revision++;
    }

    /**
     * 方块是否满足该矿物目标（口径第三十一节）。
     *
     * <p>当前模型粒度是 {@link OreType#DIAMOND}，不区分石质变体，因此
     * {@code diamond_ore} 与 {@code deepslate_diamond_ore} <b>都算 CONFIRMED</b> ——
     * 绝不能因为「模型没分石质」就把深板岩钻石矿误判成当前缺失。</p>
     */
    public static boolean isOreBlock(OreType oreType, BlockState state) {
        if (oreType == null || state == null) {
            return false;
        }
        return switch (oreType) {
            case DIAMOND -> state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE);
        };
    }

    /** 方块注册名（开发诊断用；取不到时退回类名）。 */
    private static String blockIdOf(BlockState state) {
        try {
            Identifier id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            return id == null ? state.getBlock().getClass().getSimpleName() : id.toString();
        } catch (RuntimeException ignored) {
            return state.getBlock().getClass().getSimpleName();
        }
    }

    /** 供报告使用的单行候选证据（位置 / 预测矿物 / 当前观察状态 / 最近看到的真实方块）。 */
    public String describeCandidateCn(BlockPos pos) {
        Objects.requireNonNull(pos, "pos");
        OreType oreType = candidateTypes.get(pos.asLong());
        return "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ") "
                + (oreType == null ? "非候选" : oreType.displayNameCn())
                + " → " + stateOf(pos).displayNameCn()
                + "（最近看到：" + (lastObservedBlockId(pos).isEmpty() ? "无" : lastObservedBlockId(pos)) + "）";
    }
}
