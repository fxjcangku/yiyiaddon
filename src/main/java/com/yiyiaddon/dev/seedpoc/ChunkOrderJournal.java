package com.yiyiaddon.dev.seedpoc;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 种子挖矿 PoC 第五轮 · 钻石写入溯源台账（极薄版）。
 *
 * <p><b>为什么第五轮需要一份「薄」台账</b>：用户口径第二十六节要求，一旦出现不同的钻石 BlockPos，
 * 必须能回答「这一格最早由哪个 viewer 的哪一次 placed_feature 写入、写入前那一格是什么方块」，
 * 而不是只报「两个世界差了 4 块」。</p>
 *
 * <p>第三轮那份 {@link OreVeinTrace} 台账为「逐事件时间线对齐」设计，会为每一条钻石 feature 记下
 * 全部判定与放置事件（实机单轮上百万条），代价高、且会改变生成期线程的时序。第五轮要比较的
 * <b>恰恰是时序本身</b>，所以这里另建一份只保留「真正写进世界的那些位置」的台账：
 * 每条记录只有一个坐标 + 来源 viewer + feature 路径 + 装饰批号 + 写入前状态。</p>
 *
 * <p><b>为什么记录是不可变的、且只记第一次</b>：原版 {@code OreFeature#doPlace} 的写入是
 * {@code section.setBlockState(...)}，而矿石方块不在 {@code stone_ore_replaceables} /
 * {@code deepslate_ore_replaceables} 标签里，因此<b>后到的 feature 不可能改掉先写的矿石</b>——
 * 「第一次被接受的位置」就等于「最终那一格矿石的来源」。用 {@code putIfAbsent} 保留首条即可。</p>
 *
 * <p><b>为什么只记 {@code accepted == true}</b>：{@code canPlaceOre} 返回 false 的候选点不会被写入
 * （{@code OreFeature.java:145-150}：只有通过的那一个 {@code TargetBlockState} 才会
 * {@code setBlockState} 并 {@code break}）；而能走到 {@code canPlaceOre} 的候选点都先过了
 * {@code level.ensureCanWrite(orePos)}，所以「被接受」恒等于「真的写进了那一格」。</p>
 */
public final class ChunkOrderJournal {

    /**
     * 一条写入记录。
     *
     * @param viewer       执行这次装饰的区块（按原版写半径 1，它可以写到目标区块里）
     * @param featurePath  placed_feature 注册表路径（如 {@code ore_diamond}）
     * @param batch        该 viewer 本次真实装饰的批号（由 {@link GenStageCapture#currentPass(long)} 发放）
     * @param preStateId   判定那一刻该位置的方块短 id（= 写入前的状态）
     * @param accepted     是否被 {@code canPlaceOre} 接受（本台账只记 true）
     */
    public record Write(ChunkPos viewer, String featurePath, int batch, String preStateId, boolean accepted) {
    }

    /** 位置 → 首条写入记录（顺序稳定，报告样本可读）。 */
    private static final Map<BlockPos, Write> WRITES = Collections.synchronizedMap(new LinkedHashMap<>());

    /** 是否正在记账（只在顺序实验的「按顺序请求」窗口内为真）。 */
    private static volatile boolean active;

    private ChunkOrderJournal() {
    }

    /** 开启一轮记账并清空上一轮。 */
    public static void begin() {
        WRITES.clear();
        active = true;
    }

    /** 结束记账（之后的写入不再落账）。 */
    public static void end() {
        active = false;
    }

    /** 是否正在记账。 */
    public static boolean active() {
        return active;
    }

    /** 记一条候选点判定；只有被接受的候选点才落账，且每格只保留第一条。 */
    public static void note(ChunkPos viewer, String featurePath, int batch, BlockPos pos, BlockState preState,
                            boolean accepted) {
        if (!active || !accepted) {
            return;
        }
        // 传入的是 OreFeature 复用的 MutableBlockPos，必须取不可变副本
        WRITES.putIfAbsent(pos.immutable(),
                new Write(viewer, featurePath, batch, OreBlockLedger.shortId(preState), true));
    }

    /** 取一份快照副本。 */
    public static Map<BlockPos, Write> snapshot() {
        synchronized (WRITES) {
            return new LinkedHashMap<>(WRITES);
        }
    }

    /** 已落账条数。 */
    public static int size() {
        return WRITES.size();
    }

    /** 取某位置的写入记录；没有返回 null。 */
    public static Write at(BlockPos pos) {
        return WRITES.get(pos);
    }
}
