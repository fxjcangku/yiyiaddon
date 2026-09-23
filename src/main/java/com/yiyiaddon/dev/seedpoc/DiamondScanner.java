package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 钻石坐标扫描器（第三轮）。
 *
 * <p><b>职责只有一件</b>：在<b>某一个固定覆盖范围</b>（中心区块 ±1 的 3x3）内，
 * 把 {@code diamond_ore} / {@code deepslate_diamond_ore} 的坐标原样列出来。
 * 两个来源共用同一套覆盖范围与同一套纵向范围，因此两个集合可以直接做差集：</p>
 * <ol>
 *     <li>{@link #scan(WorldGenLevel, ChunkPos)} —— 读<b>当前世界</b>。装饰期传进来的就是原版
 *         {@code WorldGenRegion}，因此这里读到的就是「那一刻真实生成期状态」里的钻石；</li>
 *     <li>{@link #fromSnapshot(GenStageSnapshot)} —— 读<b>阶段快照</b>（第二轮就在用的载体），
 *         用来把「第一条钻石 feature 执行前」那一瞬的钻石集合取出来。</li>
 * </ol>
 *
 * <p><b>为什么纵向只扫 6 个 section</b>：钻石四条 placed_feature 的高度上界是 y = 16
 * （见 {@link SeedPocConstants#DIAMOND_SCAN_SECTION_COUNT} 的源码依据），
 * 6 个 section 覆盖 y ∈ [-64, 31]，一定够且省算力。两个来源用的是同一个上限，
 * 所以差集不会因为覆盖范围不同而失真。</p>
 *
 * <p>本类<b>不做任何判定、不写世界、不改状态</b>：它只回答「这一片里现在有哪些钻石」。</p>
 */
public final class DiamondScanner {

    private DiamondScanner() {
    }

    /**
     * 扫描「中心区块 ±1」范围内当前世界上的全部钻石坐标。
     *
     * @param level  世界访问层：装饰期传原版 {@code WorldGenRegion}，实验期传服务端世界
     * @param viewer 覆盖范围的中心区块
     */
    public static Set<BlockPos> scan(WorldGenLevel level, ChunkPos viewer) {
        Set<BlockPos> found = new LinkedHashSet<>();
        int minSectionY = level.getMinSectionY();
        for (int slot = 0; slot < 9; slot++) {
            ChunkPos data = new ChunkPos(viewer.x() + slot / 3 - 1, viewer.z() + slot % 3 - 1);
            ChunkAccess chunk = level.getChunk(data.x(), data.z());
            int sections = Math.min(SeedPocConstants.DIAMOND_SCAN_SECTION_COUNT, chunk.getSectionsCount());
            for (int section = 0; section < sections; section++) {
                LevelChunkSection levelSection = chunk.getSection(section);
                if (levelSection == null || levelSection.hasOnlyAir()) {
                    continue;
                }
                int baseY = (minSectionY + section) * 16;
                for (int index = 0; index < 4096; index++) {
                    BlockState state = levelSection.getBlockState(index & 15, index >> 8, (index >> 4) & 15);
                    if (OreBlockLedger.isDiamond(state)) {
                        found.add(new BlockPos((data.x() << 4) + (index & 15),
                                baseY + (index >> 8),
                                (data.z() << 4) + ((index >> 4) & 15)));
                    }
                }
            }
        }
        return found;
    }

    /**
     * 从阶段快照里取出全部钻石坐标（覆盖范围就是该快照自己的中心 ±1）。
     *
     * <p>布局必须与 {@code GenStageCapture#capture} 的写入布局逐位一致：
     * 槽位 = {@code (dx+1)*3+(dz+1)}，段内下标 = {@code ly*256 + lz*16 + lx}。</p>
     */
    public static Set<BlockPos> fromSnapshot(GenStageSnapshot snapshot) {
        Set<BlockPos> found = new LinkedHashSet<>();
        int sections = Math.min(SeedPocConstants.DIAMOND_SCAN_SECTION_COUNT, snapshot.sectionCount());
        for (int slot = 0; slot < 9; slot++) {
            ChunkPos data = snapshot.chunkOfSlot(slot);
            for (int section = 0; section < sections; section++) {
                int baseY = snapshot.minY() + section * 16;
                for (int index = 0; index < 4096; index++) {
                    BlockState state = snapshot.stateOfId(snapshot.rawIdAt(slot, section, index));
                    if (OreBlockLedger.isDiamond(state)) {
                        found.add(new BlockPos((data.x() << 4) + (index & 15),
                                baseY + (index >> 8),
                                (data.z() << 4) + ((index >> 4) & 15)));
                    }
                }
            }
        }
        return found;
    }

    /** 只保留落在指定区块内的坐标（跨区块贡献与「本区块贡献」两种口径靠它切换）。 */
    public static Set<BlockPos> within(Set<BlockPos> positions, ChunkPos chunk) {
        Set<BlockPos> kept = new LinkedHashSet<>();
        for (BlockPos pos : positions) {
            if ((pos.getX() >> 4) == chunk.x() && (pos.getZ() >> 4) == chunk.z()) {
                kept.add(pos);
            }
        }
        return kept;
    }

    /** 差集 {@code left - right}（顺序稳定，便于报告样本可读）。 */
    public static Set<BlockPos> minus(Set<BlockPos> left, Set<BlockPos> right) {
        Set<BlockPos> result = new LinkedHashSet<>(left);
        result.removeAll(right);
        return result;
    }
}
