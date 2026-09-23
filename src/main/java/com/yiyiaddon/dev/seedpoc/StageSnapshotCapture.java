package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

/**
 * 阶段快照采集器（第四轮抽出，真实侧与离线侧<b>共用同一份采集代码</b>）。
 *
 * <p><b>为什么必须共用</b>：第四轮的判据是「离线状态 vs 真实状态逐格比」。
 * 如果两侧各写一份采集代码，任何一处布局差异都会被误报成「生成差异」，
 * 整轮结论就不可信。所以布局、覆盖范围、取值语义全部只有这一份实现：</p>
 *
 * <ul>
 *     <li><b>方块状态</b>：3x3 区块 × 若干 section，段内下标 {@code (ly*16+lz)*16+lx}（与原版
 *         {@code LevelChunkSection} 的 x/y/z 顺序一致）；</li>
 *     <li><b>两张 WG 高度图</b>：{@code level.getHeight(...)}（原版 {@code WorldGenRegion#getHeight}
 *         = {@code chunk.getHeight(...) + 1} = {@code getFirstAvailable}，WorldGenRegion.java:397-399）；</li>
 *     <li><b>生物群系</b>：section 内 4x4x4 个 quart 单元，下标 {@code (qy*4+qz)*4+qx}，
 *         值是该快照自己的编号，编号 → 注册表键存在 {@code biomeKeys} 里
 *         （两侧编号空间不同，比较时一律比<b>注册表键</b>）；</li>
 *     <li><b>结构信息</b>：每个区块一行 {@code starts=…[…] refs=…}，
 *         来自 {@code ChunkAccess#getAllStarts} / {@code #getAllReferences}。</li>
 * </ul>
 *
 * <p>采集用的读路径与原版装饰过程一致：区块走 {@code WorldGenLevel#getChunk}，
 * 方块状态走 {@code LevelChunkSection#getBlockState}，高度走 {@code WorldGenLevel#getHeight}。
 * 真实侧传入的是原版 {@code WorldGenRegion}，离线侧传入 {@link OfflineChunkRegion}，
 * 两侧都是「那一刻那块区域的真实视图」。</p>
 */
final class StageSnapshotCapture {

    private StageSnapshotCapture() {
    }

    /**
     * 采一份阶段快照。
     *
     * @param level   区域视图（真实侧 = 原版 WorldGenRegion；离线侧 = 离线 region）
     * @param viewer  快照中心区块
     * @param stage   阶段标签（中文，直接进报告）
     * @param palette 方块状态编号表（真实侧与离线侧各一份，互不干扰）
     * @param order   采集序号
     */
    static GenStageSnapshot capture(WorldGenLevel level, ChunkPos viewer, String stage, StatePalette palette,
                                    int order) {
        RegistryAccess registries = level.registryAccess();
        int minY = level.getMinY();
        ChunkAccess center = level.getChunk(viewer.x(), viewer.z());
        int sectionCount = Math.min(SeedPocConstants.CAPTURE_SECTION_COUNT, center.getSectionsCount());

        short[] states = new short[9 * sectionCount * 4096];
        short[] oceanFloor = new short[9 * 256];
        short[] worldSurface = new short[9 * 256];
        short[] biomes = new short[9 * sectionCount * 64];
        boolean[] sectionPresent = new boolean[9 * sectionCount];
        int[] slotStatusIndex = new int[9];
        Arrays.fill(slotStatusIndex, -1);
        List<String> biomeKeys = new ArrayList<>();
        Map<Holder<Biome>, Short> biomeIds = new IdentityHashMap<>();
        List<String> structureInfo = new ArrayList<>(9);
        short airId = palette.idOf(Blocks.AIR.defaultBlockState());

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos data = new ChunkPos(viewer.x() + dx, viewer.z() + dz);
                ChunkAccess chunk = level.getChunk(data.x(), data.z());
                int slot = (dx + 1) * 3 + (dz + 1);
                ChunkStatus status = chunk.getPersistedStatus();
                slotStatusIndex[slot] = status == null ? -1 : status.getIndex();
                for (int section = 0; section < sectionCount; section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    int base = (slot * sectionCount + section) * 4096;
                    int biomeBase = (slot * sectionCount + section) * 64;
                    sectionPresent[slot * sectionCount + section] = levelSection != null;
                    if (levelSection == null) {
                        // section 不存在 = 这一侧还没长到该阶段，整段记空气（比较器另外用 sectionPresent 标注）
                        Arrays.fill(states, base, base + 4096, airId);
                    } else {
                        // 注意：这里绝不能走 LevelChunkSection#hasOnlyAir() 的短路 ——
                        // 生成期 section 的 nonEmptyBlockCount 是缓存值，用原始调色板写入（噪声填充走的就是这条路）
                        // 并不会刷新它，短路会把已经填满的整段 section 记成空气，制造出假的阶段差异。
                        for (int ly = 0; ly < 16; ly++) {
                            for (int lz = 0; lz < 16; lz++) {
                                for (int lx = 0; lx < 16; lx++) {
                                    states[base + (ly * 16 + lz) * 16 + lx] =
                                            palette.idOf(levelSection.getBlockState(lx, ly, lz));
                                }
                            }
                        }
                    }
                    if (levelSection != null) {
                        for (int qy = 0; qy < 4; qy++) {
                            for (int qz = 0; qz < 4; qz++) {
                                for (int qx = 0; qx < 4; qx++) {
                                    Holder<Biome> biome = levelSection.getNoiseBiome(qx, qy, qz);
                                    Short id = biomeIds.get(biome);
                                    if (id == null) {
                                        if (biomeKeys.size() >= Short.MAX_VALUE - 1) {
                                            throw new IllegalStateException("生物群系编号表溢出：" + biomeKeys.size());
                                        }
                                        biomeKeys.add(biomeKey(registries, biome));
                                        id = (short) (biomeKeys.size() - 1);
                                        biomeIds.put(biome, id);
                                    }
                                    biomes[biomeBase + (qy * 4 + qz) * 4 + qx] = id;
                                }
                            }
                        }
                    } else {
                        // section 还没长出来：这一刻没有生物群系数据可读，记哨兵而不是记成「某个生物群系」
                        Arrays.fill(biomes, biomeBase, biomeBase + 64, GenStageSnapshot.NO_DATA);
                    }
                }

                int baseX = data.x() << 4;
                int baseZ = data.z() << 4;
                for (int lx = 0; lx < 16; lx++) {
                    for (int lz = 0; lz < 16; lz++) {
                        int index = slot * 256 + lx + lz * 16;
                        oceanFloor[index] = (short) level.getHeight(
                                Heightmap.Types.OCEAN_FLOOR_WG, baseX + lx, baseZ + lz);
                        worldSurface[index] = (short) level.getHeight(
                                Heightmap.Types.WORLD_SURFACE_WG, baseX + lx, baseZ + lz);
                    }
                }

                structureInfo.add(structureInfoOf(registries, chunk));
            }
        }

        return new GenStageSnapshot(viewer, stage, minY, sectionCount, states, oceanFloor, worldSurface,
                biomes, biomeKeys, structureInfo, sectionPresent, slotStatusIndex, palette, order);
    }

    /** 生物群系的注册表键（取不到就退化成实例描述，保证两侧至少可比）。 */
    private static String biomeKey(RegistryAccess registries, Holder<Biome> biome) {
        String key = biome.unwrapKey()
                .map(resourceKey -> resourceKey.identifier().toString())
                .orElse(null);
        if (key != null) {
            return key;
        }
        var registry = registries.lookup(Registries.BIOME).orElse(null);
        if (registry != null) {
            var direct = registry.getKey(biome.value());
            if (direct != null) {
                return direct.toString();
            }
        }
        return String.valueOf(biome.value());
    }

    /**
     * 结构信息一行：{@code starts=N[key,…] refs=R}。
     *
     * <p>只记「有几个结构起点、分别是哪个结构、有几条跨区块引用」——
     * 这三项就是「结构阶段是否与真实一致」的可比指纹；结构写下的方块本身由方块状态那一列覆盖。</p>
     */
    private static String structureInfoOf(RegistryAccess registries, ChunkAccess chunk) {
        Map<Structure, StructureStart> starts = chunk.getAllStarts();
        var structureRegistry = registries.lookupOrThrow(Registries.STRUCTURE);
        List<String> keys = new ArrayList<>(starts.size());
        for (Structure structure : starts.keySet()) {
            var key = structureRegistry.getKey(structure);
            keys.add(key == null ? String.valueOf(structure) : key.toString());
        }
        keys.sort(String::compareTo);
        long refs = 0;
        for (Collection<Long> perStructure : chunk.getAllReferences().values()) {
            refs += perStructure.size();
        }
        return "starts=" + starts.size() + "[" + String.join(",", keys) + "] refs=" + refs;
    }
}
