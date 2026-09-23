package com.yiyiaddon.seed.worldgen;

import com.yiyiaddon.seed.model.OreType;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 种子挖矿正式模块 · 从离线区块里读出某类矿物的全部坐标。
 *
 * <p>只读、不写、不做判定，也不参与预测算法本身：它把「区块里的矿物在哪」这件事从预测逻辑里
 * 单独拆出来（正式化第一阶段口径：职责分类清楚，不巨型类）。离线侧与测试侧共用同一份读取代码，
 * 两侧口径一致，差集才不会因覆盖范围不同而失真。</p>
 *
 * <p><b>纵向扫描范围</b>：自世界最低 section 起 {@link #SCAN_SECTIONS} 个 section（主世界 = y ∈ [-64, 31]）。依据：</p>
 * <ul>
 *     <li>{@code net/minecraft/data/worldgen/placement/OrePlacements.java:184-204}
 *         —— 四条钻石 placed_feature 的高度区间由 {@code aboveBottom(-80)} 到 {@code aboveBottom(80)} 的三角分布给出
 *         （主世界 minY = -64），因此钻石矿的 y 上界是 16，加上矿脉自身半径也远低于 31；</li>
 *     <li>{@code net/minecraft/data/worldgen/placement/CavePlacements.java:98-106}
 *         —— 化石钻石（{@code fossil_lower}）的高度区间是「世界底部 ~ 绝对 y=-8」，同样落在 [-64, -8]。</li>
 * </ul>
 * <p>即：本范围对当前版本的主世界钻石是<b>全覆盖</b>，同时省掉 3/4 的 section 扫描量
 * （每次预测要对目标区块读很多遍，这项开销直接体现在耗时上）。</p>
 *
 * <p><b>区块缺失绝不冒充「没有矿」</b>：{@code chunk} 为 null 时直接抛异常，
 * 由调用方按「预测失败」处理（正式化第一阶段口径第三十九节）。</p>
 */
public final class OreChunkReader {

    /** 自世界最低 section 起扫描的 section 数（主世界 6 个 = y ∈ [-64, 31]）。 */
    public static final int SCAN_SECTIONS = 6;

    private OreChunkReader() {
    }

    /**
     * 读出一个区块里指定矿物的全部坐标（按扫描顺序，稳定可复现）。
     *
     * @param chunk   区块（离线 {@code ProtoChunk} 或真实 {@code LevelChunk}，都是 {@link ChunkAccess}）
     * @param oreType 矿物种类
     */
    public static Set<BlockPos> collect(ChunkAccess chunk, OreType oreType) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(oreType, "oreType");
        Set<BlockPos> positions = new LinkedHashSet<>();
        ChunkPos chunkPos = chunk.getPos();
        int minSectionY = chunk.getMinSectionY();
        int sections = Math.min(SCAN_SECTIONS, chunk.getSectionsCount());
        for (int section = 0; section < sections; section++) {
            LevelChunkSection levelSection = chunk.getSection(section);
            if (levelSection == null || levelSection.hasOnlyAir()) {
                continue;
            }
            int baseY = (minSectionY + section) * 16;
            for (int localY = 0; localY < 16; localY++) {
                for (int localZ = 0; localZ < 16; localZ++) {
                    for (int localX = 0; localX < 16; localX++) {
                        BlockState state = levelSection.getBlockState(localX, localY, localZ);
                        if (isTarget(oreType, state)) {
                            positions.add(new BlockPos((chunkPos.x() << 4) + localX, baseY + localY,
                                    (chunkPos.z() << 4) + localZ));
                        }
                    }
                }
            }
        }
        return positions;
    }

    /**
     * 方块是否属于该矿物。
     *
     * <p>钻石有两种方块形态（{@code diamond_ore} / {@code deepslate_diamond_ore}），
     * 两者都算钻石矿——这是 26.1.2 的 {@code OreFeatures.java:64-65} 决定的
     * （同一配置里对石头系与深板岩系各给一条目标）。</p>
     */
    private static boolean isTarget(OreType oreType, BlockState state) {
        return switch (oreType) {
            case DIAMOND -> state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE);
        };
    }
}
