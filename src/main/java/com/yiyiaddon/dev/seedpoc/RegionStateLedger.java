package com.yiyiaddon.dev.seedpoc;

import java.util.Arrays;
import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * 区域状态台账：把回放会用到的整片区域（测试区块 ±2）的方块状态与两张 WG 高度图
 * <b>原样存下来</b>，实验结束后<b>逐格比对着写回</b>。
 *
 * <p><b>为什么必须整片存</b>：本轮要把「生成期快照」安装进真实区块，安装与回放会改动
 * 大量非矿石方块（石头 / 深板岩 / 空气 / 水 / 岩浆 / 紫水晶 / 滴水石…），第一轮那套
 * 「只记矿石位置再写回」的台账不足以还原非矿石改动。整片保存共 25 区块 ×
 * section 数 × 4096 格，按 {@code short} 编号压缩后约 5 MB，代价可接受；
 * 还原时逐格比对、只写回真正不同的格子。</p>
 *
 * <p><b>高度图为什么要单独存</b>：原版 {@code OCEAN_FLOOR_WG} / {@code WORLD_SURFACE_WG}
 * 属于 {@code Heightmap.Usage.WORLDGEN}（Heightmap.java:145-147），区块被提升为
 * {@code LevelChunk} 时会被丢掉（LevelChunk.java:122-126 只保留
 * {@code ChunkStatus.FULL.heightmapsAfter()}，即 OCEAN_FLOOR / WORLD_SURFACE /
 * MOTION_BLOCKING / MOTION_BLOCKING_NO_LEAVES）。实验期间我们会把它们按生成期取值
 * 装回去，所以要先记住「实验前它们是取值多少」（= 按当前方块状态补出来的那份），
 * 实验后原样写回，测试世界不留痕迹。</p>
 */
final class RegionStateLedger {

    private final ServerLevel level;
    private final ChunkPos center;
    private final int radius;
    private final int sideLength;
    private final int minY;
    private final int sectionCount;
    /** 区域内的方块状态编号：sideLength² × sectionCount × 4096。 */
    private final short[] savedStates;
    /** 标记哪些 section 保存时是全空气（还原时若现在仍是全空气可直接跳过）。 */
    private final boolean[] savedSectionEmpty;
    private final StatePalette palette;
    /** 高度图原始位存储（每个区块一份），两种类型分开存。 */
    private final long[] savedOceanFloor;
    private final long[] savedWorldSurface;
    private final int heightmapBits;
    private final int heightmapRawLength;
    private final short airId;

    /** 统计：安装写入格数 / 还原写回格数 / 还原后仍不一致格数。 */
    private long installWrites;
    private long restoreWrites;
    private long restoreMismatch;

    private RegionStateLedger(ServerLevel level, ChunkPos center, int radius, int minY, int sectionCount,
                              short[] savedStates, boolean[] savedSectionEmpty, StatePalette palette,
                              long[] savedOceanFloor, long[] savedWorldSurface,
                              int heightmapBits, int heightmapRawLength, short airId) {
        this.level = level;
        this.center = center;
        this.radius = radius;
        this.sideLength = radius * 2 + 1;
        this.minY = minY;
        this.sectionCount = sectionCount;
        this.savedStates = savedStates;
        this.savedSectionEmpty = savedSectionEmpty;
        this.palette = palette;
        this.savedOceanFloor = savedOceanFloor;
        this.savedWorldSurface = savedWorldSurface;
        this.heightmapBits = heightmapBits;
        this.heightmapRawLength = heightmapRawLength;
        this.airId = airId;
    }

    /** 把整片区域的当前状态（含两张 WG 高度图）记下来。必须在任何安装动作之前调用。 */
    static RegionStateLedger take(ServerLevel level, ChunkPos center, int radius) {
        StatePalette palette = new StatePalette();
        short airId = palette.idOf(Blocks.AIR.defaultBlockState());
        int minY = level.getMinY();
        int sectionCount = level.getChunk(center.x(), center.z()).getSectionsCount();
        int side = radius * 2 + 1;
        int sectionTotal = side * side * sectionCount;
        short[] saved = new short[sectionTotal * 4096];
        boolean[] empty = new boolean[sectionTotal];

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(center.x() + dx, center.z() + dz);
                int chunkSlot = slotOf(dx, dz, radius);
                for (int section = 0; section < sectionCount; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    int sectionSlot = chunkSlot * sectionCount + section;
                    int base = sectionSlot * 4096;
                    if (levelSection.hasOnlyAir()) {
                        // 全空气 section 不逐格取状态，整段按空气编号记；还原时若现在仍是全空气直接跳过
                        Arrays.fill(saved, base, base + 4096, airId);
                        empty[sectionSlot] = true;
                        continue;
                    }
                    for (int index = 0; index < 4096; index++) {
                        saved[base + index] = palette.idOf(levelSection.getBlockState(
                                index & 15, index >> 8, (index >> 4) & 15));
                    }
                }
            }
        }

        int bits = Mth.ceillog2(level.getHeight() + 1);
        int rawLength = (256 * bits + 63) / 64;
        long[] oceanFloor = new long[side * side * rawLength];
        long[] worldSurface = new long[side * side * rawLength];
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(center.x() + dx, center.z() + dz);
                int chunkSlot = slotOf(dx, dz, radius);
                // 先按当前方块状态把两张 WG 高度图补齐（原版缺失时也是这么懒补的），再取原始数据
                Heightmap.primeHeightmaps(chunk, EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG));
                Heightmap.primeHeightmaps(chunk, EnumSet.of(Heightmap.Types.WORLD_SURFACE_WG));
                System.arraycopy(chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG).getRawData(), 0,
                        oceanFloor, chunkSlot * rawLength, rawLength);
                System.arraycopy(chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG).getRawData(), 0,
                        worldSurface, chunkSlot * rawLength, rawLength);
            }
        }

        return new RegionStateLedger(level, center, radius, minY, sectionCount, saved, empty, palette,
                oceanFloor, worldSurface, bits, rawLength, airId);
    }

    /**
     * 把一份阶段快照安装进真实区块：逐格比对，只有与快照不同才写。
     *
     * <p>这就是本轮实验的<b>唯一变量</b>——影子 worldgen 的算法、种子、索引表一律不动，
     * 只把「放置时读到的状态」换成真实生成期状态。</p>
     *
     * @return 本次实际写入的格数
     */
    long install(GenStageSnapshot snapshot) {
        long writes = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos data = new ChunkPos(snapshot.viewer().x() + dx, snapshot.viewer().z() + dz);
                LevelChunk chunk = level.getChunk(data.x(), data.z());
                int slot = (dx + 1) * 3 + (dz + 1);
                int usable = Math.min(snapshot.sectionCount(), sectionCount);
                for (int section = 0; section < usable; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    for (int index = 0; index < 4096; index++) {
                        short capturedId = snapshot.rawIdAt(slot, section, index);
                        int localX = index & 15;
                        int localY = index >> 8;
                        int localZ = (index >> 4) & 15;
                        if (snapshot.idOf(levelSection.getBlockState(localX, localY, localZ)) == capturedId) {
                            continue;
                        }
                        levelSection.setBlockState(localX, localY, localZ, snapshot.stateOfId(capturedId), false);
                        writes++;
                    }
                }
            }
        }
        installWrites += writes;
        return writes;
    }

    /**
     * 对照口径专用：把一个区块（±radius）自世界底部起 {@link SeedPocConstants#CAPTURE_SECTION_COUNT}
     * 个 section 整片改写成同一种方块。
     *
     * <p>它存在的唯一理由是把「放置链路到底有没有读世界方块状态」变成可测事实：
     * 同一片区域改写成不可替换方块时预测必须为 0、改写成可替换方块时预测必须显著变多。
     * 两个对照都走同一条高度图判据，所以二者的差异只可能来自方块状态本身。</p>
     *
     * @return 实际写入格数
     */
    long flood(ChunkPos target, int radius, BlockState state) {
        long writes = 0;
        int limit = Math.min(SeedPocConstants.CAPTURE_SECTION_COUNT, sectionCount);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(target.x() + dx, target.z() + dz);
                for (int section = 0; section < limit; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    for (int index = 0; index < 4096; index++) {
                        int localX = index & 15;
                        int localY = index >> 8;
                        int localZ = (index >> 4) & 15;
                        if (levelSection.getBlockState(localX, localY, localZ) == state) {
                            continue;
                        }
                        levelSection.setBlockState(localX, localY, localZ, state, false);
                        writes++;
                    }
                }
            }
        }
        installWrites += writes;
        return writes;
    }

    /** 把快照里的两张 WG 高度图装回真实区块。 */
    void installHeightmaps(GenStageSnapshot snapshot) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos data = new ChunkPos(snapshot.viewer().x() + dx, snapshot.viewer().z() + dz);
                LevelChunk chunk = level.getChunk(data.x(), data.z());
                int slot = (dx + 1) * 3 + (dz + 1);
                chunk.setHeightmap(Heightmap.Types.OCEAN_FLOOR_WG,
                        buildRaw(snapshot, Heightmap.Types.OCEAN_FLOOR_WG, slot));
                chunk.setHeightmap(Heightmap.Types.WORLD_SURFACE_WG,
                        buildRaw(snapshot, Heightmap.Types.WORLD_SURFACE_WG, slot));
            }
        }
    }

    /** 把快照里的列高度打包成原版高度图的位存储格式。 */
    private long[] buildRaw(GenStageSnapshot snapshot, Heightmap.Types type, int slot) {
        SimpleBitStorage storage = new SimpleBitStorage(heightmapBits, 256);
        for (int localX = 0; localX < 16; localX++) {
            for (int localZ = 0; localZ < 16; localZ++) {
                storage.set(localX + localZ * 16,
                        snapshot.columnHeight(type, slot, localX, localZ) - minY);
            }
        }
        return storage.getRaw().clone();
    }

    /** 还原：方块状态逐格比对写回，随后把两张 WG 高度图写回实验前的取值。 */
    void restore() {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(center.x() + dx, center.z() + dz);
                int chunkSlot = slotOf(dx, dz, radius);
                for (int section = 0; section < sectionCount; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    int sectionSlot = chunkSlot * sectionCount + section;
                    int base = sectionSlot * 4096;
                    if (savedSectionEmpty[sectionSlot] && levelSection.hasOnlyAir()) {
                        continue;
                    }
                    for (int index = 0; index < 4096; index++) {
                        short savedId = savedStates[base + index];
                        int localX = index & 15;
                        int localY = index >> 8;
                        int localZ = (index >> 4) & 15;
                        if (palette.idOf(levelSection.getBlockState(localX, localY, localZ)) == savedId) {
                            continue;
                        }
                        levelSection.setBlockState(localX, localY, localZ, palette.stateOf(savedId), false);
                        restoreWrites++;
                    }
                }
            }
        }
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(center.x() + dx, center.z() + dz);
                int chunkSlot = slotOf(dx, dz, radius);
                chunk.setHeightmap(Heightmap.Types.OCEAN_FLOOR_WG,
                        Arrays.copyOfRange(savedOceanFloor, chunkSlot * heightmapRawLength,
                                (chunkSlot + 1) * heightmapRawLength));
                chunk.setHeightmap(Heightmap.Types.WORLD_SURFACE_WG,
                        Arrays.copyOfRange(savedWorldSurface, chunkSlot * heightmapRawLength,
                                (chunkSlot + 1) * heightmapRawLength));
            }
        }
    }

    /** 还原校验：再逐格比对一遍，返回仍与台账不一致的格数（必须为 0）。 */
    long verifyRestore() {
        long mismatch = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = level.getChunk(center.x() + dx, center.z() + dz);
                int chunkSlot = slotOf(dx, dz, radius);
                for (int section = 0; section < sectionCount; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    int sectionSlot = chunkSlot * sectionCount + section;
                    int base = sectionSlot * 4096;
                    if (savedSectionEmpty[sectionSlot] && levelSection.hasOnlyAir()) {
                        continue;
                    }
                    for (int index = 0; index < 4096; index++) {
                        if (palette.idOf(levelSection.getBlockState(index & 15, index >> 8, (index >> 4) & 15))
                                != savedStates[base + index]) {
                            mismatch++;
                        }
                    }
                }
            }
        }
        restoreMismatch = mismatch;
        return mismatch;
    }

    /** 报告用一行摘要。 */
    String cn() {
        return "区域台账（中心 " + center.x() + "," + center.z() + "，半径 " + radius + " 区块 = "
                + (sideLength * sideLength) + " 区块 × " + sectionCount + " section）："
                + "保存 " + savedStates.length + " 格；安装写入 " + installWrites
                + " 格 / 还原写回 " + restoreWrites + " 格 / 还原后仍不一致 " + restoreMismatch + " 格";
    }

    /** 台账覆盖的区块数。 */
    int chunkCount() {
        return sideLength * sideLength;
    }

    private static int slotOf(int dx, int dz, int radius) {
        int side = radius * 2 + 1;
        return (dx + radius) * side + (dz + radius);
    }
}
