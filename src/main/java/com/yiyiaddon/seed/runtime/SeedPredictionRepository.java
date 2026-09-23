package com.yiyiaddon.seed.runtime;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.prediction.PredictionResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>客户端预测缓存</b>（正式化第五阶段 233；第八阶段 236 加键隔离；26.2 语义移植）。
 *
 * <p><b>它保存什么</b>：Worker 已经返回到客户端的正式 {@link PredictionResult}，按
 * {@link TargetKey}（维度 + 矿物 + 目标区块）索引。它是「预测」这一维度的唯一客户端真源 ——
 * 观察（{@code observation}）与渲染（{@code render}）都只从它派生，绝不自算矿物。</p>
 *
 * <p><b>236 为什么必须换键</b>：同区块的钻石与红石是两条互相独立的预测，主世界与下界的同一组坐标
 * 也完全无关。用区块单独做键会让它们互相顶掉，而且<b>不会报错</b>（见 {@link TargetKey} 类注释）。
 * 另外这里还强制「写进来的结果必须与当前身份同维度」——下界的结果绝不允许落进主世界的缓存。</p>
 *
 * <p><b>身份绑定</b>：缓存绑在 {@link SeedRuntimeIdentity} 上。世界 / 种子 / 维度 / 版本 / 会话代号
 * 任何一项改变，{@link #bind(SeedRuntimeIdentity)} 会把整份缓存清空 —— 包括「退出世界」「换服务器」
 * 「换维度」「改种子」「关功能」五条入口。</p>
 *
 * <p><b>有界</b>：{@link #MAX_TARGET_CHUNKS} 是<b>每一种矿物</b>的硬上限（236 起按矿物分组计数：
 * 8 种矿物同时铺开时总量上界是 8 倍，但每一组都不会无限增长）。超限按
 * 「先踢覆盖范围外的 → 再踢离玩家最远的 → 再踢最久没被用到的」顺序淘汰。</p>
 *
 * <p><b>线程模型</b>：<b>只在客户端主线程读写</b>（提交、回投、观察、快照重建全部在主线程序列执行），
 * 因此这里刻意不加锁 —— 加锁只会掩盖「谁在后台线程碰了客户端状态」这类真问题。</p>
 */
public final class SeedPredictionRepository {

    /**
     * 客户端业务缓存硬上限（<b>每种矿物</b>的目标区块数）。
     *
     * <p>默认覆盖半径 3（7×7 = 最多 49 个目标区块）留出约 5 倍余量，玩家来回跑几屏也不会顶到上限；
     * 顶到之后按 {@link #evict} 的策略淘汰，内存占用有上界。</p>
     */
    public static final int MAX_TARGET_CHUNKS = 256;

    /** 一条缓存：正式结果 + 最近一次被使用的时间（淘汰与诊断都要用）。 */
    private static final class Entry {

        private final PredictionResult result;
        private long lastUsedAt;

        private Entry(PredictionResult result, long lastUsedAt) {
            this.result = result;
            this.lastUsedAt = lastUsedAt;
        }
    }

    /** 键 → 结果（插入序稳定，便于报告与诊断阅读）。 */
    private final Map<TargetKey, Entry> entries = new LinkedHashMap<>();

    /** 当前绑定的身份；{@code null} = 尚未绑定（此时一律不写入）。 */
    private SeedRuntimeIdentity identity;

    /** 累计淘汰次数（诊断：报告里的「有界」直接证据）。 */
    private int evictedCount;

    /** 当前身份；未绑定时为 {@code null}。 */
    public SeedRuntimeIdentity identity() {
        return identity;
    }

    /**
     * 绑定身份；身份不同则<b>整份清空</b>。
     *
     * @return 是否发生了清空（调用方据此打日志 / 让观察与渲染快照一起失效）
     */
    public boolean bind(SeedRuntimeIdentity next) {
        if (Objects.equals(identity, next)) {
            return false;
        }
        boolean cleared = !entries.isEmpty();
        entries.clear();
        identity = next;
        return cleared;
    }

    /** 缓存是否属于给定身份（异步回投前必须核对，避免把上一个世界的预测写进来）。 */
    public boolean boundTo(SeedRuntimeIdentity other) {
        return identity != null && identity.equals(other);
    }

    /**
     * 写入一条正式结果。
     *
     * <p><b>只收成功结果</b>：失败结果（{@code success=false}）表示「这次预测不成立」，
     * 不是「这个区块没有这种矿」，把它缓存下来会被后续的观察 / 渲染当成有效预测使用。
     * 成功但 0 个矿的结果<b>要收</b> —— 它就是「这个区块确实没有这种矿」的正式结论。</p>
     *
     * <p>维度不符 / 种子不符的结果一律拒收（fail-closed，且不抛异常：调用方只需看返回值）。</p>
     *
     * @return 是否真的写进去了
     */
    public boolean put(PredictionResult result) {
        if (result == null || identity == null) {
            return false;
        }
        if (result.request().seed() != identity.seed()) {
            return false;
        }
        String dimensionId = result.request().dimension().identifier().toString();
        if (!identity.dimensionId().equals(dimensionId)) {
            return false;
        }
        TargetKey key = TargetKey.of(dimensionId, result.request().oreType(), result.request().chunk());
        entries.put(key, new Entry(result, System.currentTimeMillis()));
        return true;
    }

    /** 取一条缓存并记为「刚用过」（淘汰策略的 LRU 依据）。 */
    public PredictionResult get(TargetKey key) {
        Entry entry = key == null ? null : entries.get(key);
        if (entry == null) {
            return null;
        }
        entry.lastUsedAt = System.currentTimeMillis();
        return entry.result;
    }

    /** 是否有该键的缓存（不改变使用时间）。 */
    public boolean contains(TargetKey key) {
        return key != null && entries.containsKey(key);
    }

    /** 是否有该「矿物 + 区块」的缓存（维度取当前身份）。 */
    public boolean contains(OreType oreType, ChunkPos chunk) {
        return identity != null && contains(TargetKey.of(identity.dimensionId(), oreType, chunk));
    }

    /** 只记一次「刚用过」，不取出结果。 */
    public void touch(TargetKey key) {
        Entry entry = key == null ? null : entries.get(key);
        if (entry != null) {
            entry.lastUsedAt = System.currentTimeMillis();
        }
    }

    /** 移除一条（覆盖范围外的预测被丢弃时使用）。 */
    public void remove(TargetKey key) {
        if (key != null) {
            entries.remove(key);
        }
    }

    /** 全部缓存键（副本，供遍历时安全）。 */
    public List<TargetKey> keys() {
        return new ArrayList<>(entries.keySet());
    }

    /** 全部结果（副本，供快照重建使用）。 */
    public Collection<PredictionResult> results() {
        List<PredictionResult> out = new ArrayList<>(entries.size());
        for (Entry entry : entries.values()) {
            out.add(entry.result);
        }
        return out;
    }

    /** 缓存条目数（236 起是「维度 + 矿物 + 区块」三元组的数量）。 */
    public int size() {
        return entries.size();
    }

    /** 累计淘汰次数。 */
    public int evictedCount() {
        return evictedCount;
    }

    /** 清空（不改身份）。 */
    public void clear() {
        entries.clear();
    }

    /** 清空并解除身份绑定（退出世界 / 关功能时用）。 */
    public void reset() {
        entries.clear();
        identity = null;
    }

    /**
     * 执行淘汰，把<b>每一种矿物</b>的缓存压回 {@link #MAX_TARGET_CHUNKS} 以内。
     *
     * <p>淘汰顺序（「当前覆盖外 + 离玩家最远 + 最久未使用」）：</p>
     * <ol>
     *     <li>优先淘汰<b>覆盖范围外</b>的目标区块；</li>
     *     <li>同类里优先淘汰<b>离玩家所在区块最远</b>的；</li>
     *     <li>再同类再同距离时，淘汰<b>最久没被使用</b>的。</li>
     * </ol>
     *
     * @param playerChunk    玩家当前区块（不在此范围外的判定里直接排除，只用于排序）
     * @param coverageRadius 当前覆盖半径（区块）
     * @return 本次被淘汰的键清单（调用方据此清掉对应观察状态）
     */
    public List<TargetKey> evict(ChunkPos playerChunk, int coverageRadius) {
        List<TargetKey> victims = new ArrayList<>();
        Map<OreType, List<TargetKey>> byOre = new LinkedHashMap<>();
        for (TargetKey key : entries.keySet()) {
            byOre.computeIfAbsent(key.oreType(), ignored -> new ArrayList<>()).add(key);
        }
        for (Map.Entry<OreType, List<TargetKey>> group : byOre.entrySet()) {
            List<TargetKey> keys = group.getValue();
            int overflow = keys.size() - MAX_TARGET_CHUNKS;
            if (overflow <= 0) {
                continue;
            }
            List<TargetKey> candidates = new ArrayList<>(keys);
            while (overflow-- > 0) {
                TargetKey victim = pickVictim(candidates, playerChunk, coverageRadius);
                if (victim == null) {
                    break;
                }
                candidates.remove(victim);
                entries.remove(victim);
                victims.add(victim);
                evictedCount++;
            }
        }
        return victims;
    }

    /** 从候选里选一个最该淘汰的键；空候选返回 {@code null}。 */
    private TargetKey pickVictim(List<TargetKey> candidates, ChunkPos playerChunk, int coverageRadius) {
        TargetKey victim = null;
        boolean victimInCoverage = false;
        int victimDistance = Integer.MIN_VALUE;
        long victimUsedAt = Long.MAX_VALUE;
        for (TargetKey key : candidates) {
            Entry entry = entries.get(key);
            if (entry == null) {
                continue;
            }
            ChunkPos chunk = key.chunk();
            boolean inCoverage = inCoverage(chunk, playerChunk, coverageRadius);
            int distance = chunk.distanceSquared(playerChunk);
            if (victim == null
                    || (victimInCoverage && !inCoverage)
                    || (victimInCoverage == inCoverage && distance > victimDistance)
                    || (victimInCoverage == inCoverage && distance == victimDistance
                        && entry.lastUsedAt < victimUsedAt)) {
                victim = key;
                victimInCoverage = inCoverage;
                victimDistance = distance;
                victimUsedAt = entry.lastUsedAt;
            }
        }
        return victim;
    }

    /**
     * 落在覆盖方框内、且属于这些矿物的<b>缓存条数</b>（一个「区块 × 矿物」算一条，与覆盖调度的
     * 任务口径一致）。
     *
     * @param oreTypes 只统计这些矿物（空集 = 统计全部）
     */
    public int countInCoverage(ChunkPos center, int radius, Set<OreType> oreTypes) {
        if (center == null) {
            return 0;
        }
        int count = 0;
        for (TargetKey key : entries.keySet()) {
            if (!oreTypes.isEmpty() && !oreTypes.contains(key.oreType())) {
                continue;
            }
            if (inCoverage(key.chunk(), center, radius)) {
                count++;
            }
        }
        return count;
    }

    /** 该目标区块是否落在玩家当前覆盖方框内（切比雪夫距离口径，与覆盖调度一致）。 */
    private static boolean inCoverage(ChunkPos chunk, ChunkPos center, int radius) {
        return Math.abs(chunk.x() - center.x()) <= radius && Math.abs(chunk.z() - center.z()) <= radius;
    }

    /** 一行诊断（日志 / 报告用）。 */
    public String describeCn() {
        return "缓存目标（维度+矿物+区块）" + entries.size() + " 条 / 每矿物上限 " + MAX_TARGET_CHUNKS
                + "，累计淘汰 " + evictedCount
                + "，身份 " + (identity == null ? "未绑定" : identity.describeCn());
    }
}
