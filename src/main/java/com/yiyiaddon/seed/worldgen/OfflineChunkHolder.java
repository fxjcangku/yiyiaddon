package com.yiyiaddon.seed.worldgen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/**
 * 种子挖矿正式模块 · 离线区块持有者（每个离线区块在每个生成状态上的一份产出）。
 *
 * <p><b>为什么继承原版 {@code GenerationChunkHolder}</b>：原版 {@code WorldGenRegion#getChunk}
 * 取区块的唯一途径就是 {@code StaticCache2D<GenerationChunkHolder>} →
 * {@code holder.getChunkIfPresentUnchecked(状态)}。要复用原版 region 的读语义（依赖半径检查、
 * 状态检查、越界报告），就必须提供原版类型的 holder，不能换成自造接口。</p>
 *
 * <p><b>为什么只覆盖读方法</b>：原版把各状态的区块存在私有 {@code AtomicReferenceArray} 里，
 * 写入口是包私有/私有的；而读方法 {@code getChunkIfPresentUnchecked} / {@code getChunkIfPresent} /
 * {@code getPersistedStatus} / {@code getLatestChunk} / {@code getLatestStatus} 都是 public 且未加 final，
 * 所以这里「自己存一份、把读方法全部覆盖」，既不需要任何 Accessor/Invoker Mixin，
 * 也不触碰原版私有状态。</p>
 *
 * <p>状态语义与原版一致：一个区块在每个状态上只存同一个实例，
 * {@link #getPersistedStatus()} 返回「已经跑到的最远状态」；离线流水线每跑完一个阶段就
 * {@link #put} 一次，因此 {@code WorldGenRegion} 看到的就是原版那一刻会看到的东西。</p>
 */
final class OfflineChunkHolder extends GenerationChunkHolder {

    /** 状态总数（EMPTY…FULL）。 */
    private static final int STATUS_COUNT = ChunkStatus.getStatusList().size();

    /** 各状态上的区块；未跑到的状态为 null。 */
    private final ChunkAccess[] byStatus = new ChunkAccess[STATUS_COUNT];

    OfflineChunkHolder(ChunkPos pos) {
        super(pos);
    }

    /** 登记某个状态产出的区块（离线流水线唯一写入口）。 */
    void put(ChunkStatus status, ChunkAccess chunk) {
        byStatus[status.getIndex()] = chunk;
    }

    /** 取某个状态的区块；没跑到返回 null。 */
    ChunkAccess get(ChunkStatus status) {
        return status == null ? null : byStatus[status.getIndex()];
    }

    /** 已经跑到的最远状态；一个都没跑到返回 null。 */
    ChunkStatus highestStatus() {
        for (int index = STATUS_COUNT - 1; index >= 0; index--) {
            if (byStatus[index] != null) {
                return ChunkStatus.getStatusList().get(index);
            }
        }
        return null;
    }

    @Override
    public ChunkAccess getChunkIfPresentUnchecked(ChunkStatus status) {
        return get(status);
    }

    @Override
    public ChunkAccess getChunkIfPresent(ChunkStatus status) {
        return get(status);
    }

    @Override
    public ChunkAccess getLatestChunk() {
        return get(highestStatus());
    }

    @Override
    public ChunkStatus getPersistedStatus() {
        return highestStatus();
    }

    @Override
    public ChunkStatus getLatestStatus() {
        return highestStatus();
    }

    /**
     * 票据等级：离线流水线不参与原版票据调度，只需要一个「能容纳到 FULL」的合法值。
     *
     * <p>用 {@code ChunkLevel.byStatus(FULL)} 而不是硬编码常量，避免版本升级后常量搬家。</p>
     */
    @Override
    public int getTicketLevel() {
        return ChunkLevel.byStatus(ChunkStatus.FULL);
    }

    @Override
    public int getQueueLevel() {
        return 0;
    }

    @Override
    protected void addSaveDependency(CompletableFuture<?> sync) {
        // 离线区块永不落盘：没有任何存档同步依赖
    }
}
