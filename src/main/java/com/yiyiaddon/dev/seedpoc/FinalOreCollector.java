package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 种子挖矿 PoC 第六轮 · 「某个区块里的最终自然钻石」读取器。
 *
 * <p><b>它只回答一个问题</b>：给定一个区块（离线的 {@code ProtoChunk} 或真实世界的
 * {@code LevelChunk}，两者都是 {@link ChunkAccess}），把里面 {@code diamond_ore} /
 * {@code deepslate_diamond_ore} 的坐标原样列出来，并按方块种类分开计数。</p>
 *
 * <p><b>离线与真实共用同一份读取代码</b>（这是本轮「可比性」的前提）：两侧都用
 * {@code ChunkAccess#getSection} + {@code LevelChunkSection#getBlockState}，
 * 且纵向都只扫世界底部起的 {@link SeedPocConstants#DIAMOND_SCAN_SECTION_COUNT} 个 section。
 * 依据：钻石四条 placed_feature 的高度上界是 y = 16（{@code OrePlacements} 的
 * {@code aboveBottom(80)}，主世界 minY = -64），6 个 section 覆盖 y ∈ [-64, 31]，
 * 既一定覆盖又省算力；两侧同界 ⇒ 差集不会因覆盖不同而失真。</p>
 *
 * <p>本类只读不写、不做判定、不参与预测。</p>
 */
final class FinalOreCollector {

    /**
     * 一个区块里的最终钻石集合（按方块分两类）。
     *
     * @param diamondOre            石头版本的钻石矿坐标
     * @param deepslateDiamondOre   深板岩版本的钻石矿坐标
     */
    record OreSet(Set<BlockPos> diamondOre, Set<BlockPos> deepslateDiamondOre) {

        /** 两类合并（= 「这一区块里的最终自然钻石」）。 */
        Set<BlockPos> all() {
            Set<BlockPos> union = new LinkedHashSet<>(diamondOre);
            union.addAll(deepslateDiamondOre);
            return union;
        }

        /** 总数。 */
        int total() {
            return diamondOre.size() + deepslateDiamondOre.size();
        }

        /** 中文摘要。 */
        String cn() {
            return "diamond_ore " + diamondOre.size() + " / deepslate_diamond_ore "
                    + deepslateDiamondOre.size() + " / 合计 " + total();
        }
    }

    private static final OreSet EMPTY = new OreSet(Set.of(), Set.of());

    private FinalOreCollector() {
    }

    /** 空集合（区块缺失时用；报告必须显式说明缺失，绝不用空集合冒充「没有钻石」）。 */
    static OreSet empty() {
        return EMPTY;
    }

    /**
     * 读一个区块里的最终钻石。
     *
     * @param chunk 区块（离线 ProtoChunk 或真实 LevelChunk）
     */
    static OreSet collect(ChunkAccess chunk) {
        if (chunk == null) {
            return EMPTY;
        }
        Set<BlockPos> diamond = new LinkedHashSet<>();
        Set<BlockPos> deepslate = new LinkedHashSet<>();
        ChunkPos pos = chunk.getPos();
        int minSectionY = chunk.getMinSectionY();
        int sections = Math.min(SeedPocConstants.DIAMOND_SCAN_SECTION_COUNT, chunk.getSectionsCount());
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
                        if (state.is(Blocks.DIAMOND_ORE)) {
                            diamond.add(new BlockPos((pos.x() << 4) + localX, baseY + localY,
                                    (pos.z() << 4) + localZ));
                        } else if (state.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                            deepslate.add(new BlockPos((pos.x() << 4) + localX, baseY + localY,
                                    (pos.z() << 4) + localZ));
                        }
                    }
                }
            }
        }
        return new OreSet(diamond, deepslate);
    }

    /** 只保留落在指定区块内的坐标（跨区块口径与「本区块口径」靠它切换）。 */
    static Set<BlockPos> within(Set<BlockPos> positions, ChunkPos chunk) {
        Set<BlockPos> kept = new LinkedHashSet<>();
        for (BlockPos pos : positions) {
            if ((pos.getX() >> 4) == chunk.x() && (pos.getZ() >> 4) == chunk.z()) {
                kept.add(pos);
            }
        }
        return kept;
    }
}
