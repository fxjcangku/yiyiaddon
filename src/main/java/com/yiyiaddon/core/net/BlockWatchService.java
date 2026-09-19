package com.yiyiaddon.core.net;

import com.yiyiaddon.core.event.ServerBlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 方块反馈关注表：模块登记「我正在处理的方块坐标」，核心据此筛掉无关的方块同步包。
 *
 * <p><b>为什么必须有这一层：</b>服务端会把视野内所有方块变化推向客户端（含区块批量更新，
 * 一个包最多上千个方块）。若核心把每个变更都派发成事件，主线程会被无关流量淹没。
 * 因此沿用旧实现的做法：模块先登记关注坐标，核心只对匹配到的坐标派发
 * {@link ServerBlockEvent}；序列号确认包与坐标无关，无条件派发。</p>
 *
 * <p><b>线程：</b>登记 / 注销在主线程（模块启用、关闭、切换目标）；
 * 匹配在网络线程（{@code EventDispatcher} 入队时）。关注表用并发容器 + 不可变快照，
 * 网络线程只读。</p>
 */
public final class BlockWatchService {

    private static final Map<String, Set<BlockPos>> WATCHED = new ConcurrentHashMap<>();

    /** 全部关注坐标的快照（写入时整体替换，网络线程无锁读） */
    private static final AtomicReference<Set<BlockPos>> SNAPSHOT = new AtomicReference<>(Set.of());

    private BlockWatchService() {
    }

    /** 登记一个关注坐标（同一所有者重复登记同一坐标无副作用） */
    public static void watch(String ownerId, BlockPos pos) {
        if (ownerId == null || ownerId.isBlank() || pos == null) return;
        WATCHED.computeIfAbsent(ownerId, key -> ConcurrentHashMap.newKeySet()).add(pos.immutable());
        rebuild();
    }

    /** 取消一个关注坐标 */
    public static void unwatch(String ownerId, BlockPos pos) {
        if (ownerId == null || pos == null) return;
        Set<BlockPos> set = WATCHED.get(ownerId);
        if (set == null || !set.remove(pos)) return;
        if (set.isEmpty()) WATCHED.remove(ownerId);
        rebuild();
    }

    /** 注销某所有者的全部关注坐标（模块关闭时调用） */
    public static void clear(String ownerId) {
        if (ownerId == null || WATCHED.remove(ownerId) == null) return;
        rebuild();
    }

    /** 清空全部（断线清理用） */
    public static void clearAll() {
        if (WATCHED.isEmpty()) return;
        WATCHED.clear();
        rebuild();
    }

    /** 当前关注坐标数量（自检与调试用） */
    public static int watchedCount() {
        return SNAPSHOT.get().size();
    }

    /**
     * 匹配一个收到的包（网络线程调用）：命中则返回只读快照，否则返回 {@code null}。
     *
     * <p>三个来源：序列号确认包（无条件）、单方块更新（命中关注坐标）、
     * 区块批量更新（命中关注坐标，只取命中项）。</p>
     */
    public static ServerBlockEvent match(Packet<?> packet) {
        if (packet instanceof ClientboundBlockChangedAckPacket ack) {
            return ServerBlockEvent.ack(ack.sequence());
        }

        if (packet instanceof ClientboundBlockUpdatePacket update) {
            BlockPos pos = update.getPos();
            if (!isWatched(pos)) return null;
            return ServerBlockEvent.update(pos.immutable(), update.getBlockState(), true);
        }

        if (packet instanceof ClientboundSectionBlocksUpdatePacket sectionUpdate) {
            Set<BlockPos> watched = SNAPSHOT.get();
            if (watched.isEmpty()) return null;
            AtomicReference<ServerBlockEvent> hit = new AtomicReference<>();
            sectionUpdate.runUpdates((pos, state) -> {
                if (hit.get() != null) return;
                if (watched.contains(pos)) {
                    hit.set(ServerBlockEvent.update(pos.immutable(), state, false));
                }
            });
            return hit.get();
        }

        return null;
    }

    private static boolean isWatched(BlockPos pos) {
        Set<BlockPos> watched = SNAPSHOT.get();
        return !watched.isEmpty() && pos != null && watched.contains(pos);
    }

    private static void rebuild() {
        Set<BlockPos> next = ConcurrentHashMap.newKeySet();
        for (Set<BlockPos> set : WATCHED.values()) {
            next.addAll(set);
        }
        SNAPSHOT.set(Set.copyOf(next));
    }
}
