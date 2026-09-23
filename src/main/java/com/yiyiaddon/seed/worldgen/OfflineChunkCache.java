package com.yiyiaddon.seed.worldgen;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/**
 * 种子挖矿正式模块 · 离线区块缓存（一份离线世界的全部区块产出）。
 *
 * <p><b>隔离粒度</b>（正式化第一阶段口径第十八节）：</p>
 * <ul>
 *     <li>缓存对象本身归属一个预测会话，会话按 (Minecraft 版本, 种子, 维度) 隔离，
 *         因此跨种子 / 跨维度<b>不可能</b>共用同一份缓存；</li>
 *     <li>缓存内的键 = {@link ChunkPos#pack()}，即区块坐标；</li>
 *     <li>每个键下按生成状态分槽（{@link OfflineChunkHolder} 每个状态一份产出），
 *         因此「某区块跑到哪个阶段」是可以单独复用的——这正是相邻目标区块能共享前置阶段的实现依据。</li>
 * </ul>
 *
 * <p><b>一个缓存对象 = 一份世界状态</b>：基线预测与调度敏感复核必须各自持有一份缓存
 * （复核用的执行顺序不同，会写进不同的方块状态），因此本类被两个流水线各持有一份，互不污染。</p>
 *
 * <p><b>线程安全</b>：本类不做同步，由持有它的 {@code PredictionSession} 统一串行化
 * （会话级 {@code synchronized}），与「以后放进后台任务」的用法兼容：同一个会话只会被一个线程驱动。</p>
 */
public final class OfflineChunkCache {

    /** 区块键（{@link ChunkPos#pack()}）→ 持有者。 */
    private final Map<Long, OfflineChunkHolder> holders = new HashMap<>();

    /** 当前观察窗口（原版 {@code WorldGenRegion} 读区块的唯一入口）。 */
    private StaticCache2D<GenerationChunkHolder> window;

    /** 取（必要时新建）某区块的持有者。 */
    OfflineChunkHolder holder(int chunkX, int chunkZ) {
        long key = ChunkPos.pack(chunkX, chunkZ);
        OfflineChunkHolder existing = holders.get(key);
        if (existing != null) {
            return existing;
        }
        OfflineChunkHolder created = new OfflineChunkHolder(new ChunkPos(chunkX, chunkZ));
        holders.put(key, created);
        return created;
    }

    /**
     * 以某个区块为中心建立观察窗口。
     *
     * @param center 窗口中心（= 目标区块）
     * @param radius 窗口半径（区块）；原版的缓存半径 + 需要额外装饰的圈数
     */
    void openWindow(ChunkPos center, int radius) {
        window = StaticCache2D.create(center.x(), center.z(), radius, this::holder);
    }

    /** 当前观察窗口；未建立返回 null。 */
    StaticCache2D<GenerationChunkHolder> window() {
        return window;
    }

    /** 取某区块某个状态的产出；没跑到返回 null。 */
    public ChunkAccess chunkAt(ChunkPos pos, ChunkStatus status) {
        OfflineChunkHolder holder = holders.get(pos.pack());
        return holder == null ? null : holder.get(status);
    }

    /** 取某区块「已经跑到的最远状态」的产出；一个阶段都没跑返回 null。 */
    public ChunkAccess latestChunk(ChunkPos pos) {
        OfflineChunkHolder holder = holders.get(pos.pack());
        return holder == null ? null : holder.get(holder.highestStatus());
    }

    /** 当前持有的区块数（报告用：离线构造规模 / 缓存规模上界）。 */
    public int size() {
        return holders.size();
    }

    /** 清空全部区块产出（会话生命周期管理用；调用后本缓存回到「一份新世界」的状态）。 */
    public void clear() {
        holders.clear();
        window = null;
    }
}
