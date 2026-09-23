package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * 生成期快照的「缺失输入量化」取证：把快照与最终世界逐格比一遍，给出第一轮登记的两条缺失输入
 * （生成期方块状态、生成期高度图）各自到底差多少。
 *
 * <p>这一类数字是本轮报告的核心证据：它把「第一轮为什么只有 59%」从推断变成可复核的差值，
 * 也用来判断「换成快照之后还剩多少差异」。所有比较都在<b>同一张编号表</b>下进行，
 * 因此不存在「看起来一样但对象不同」的假阴性。</p>
 */
final class GenStageEvidence {

    private GenStageEvidence() {
    }

    /**
     * 为单个测试区块生成取证明细。
     *
     * @param level       服务端世界（此刻已完全生成，用作「最终世界」一侧）
     * @param center      测试区块
     * @param postCarvers 装饰开始前快照（可为 null）
     * @param preDiamond  第一条钻石 feature 前快照（可为 null）
     */
    static List<String> render(ServerLevel level, ChunkPos center,
                               GenStageSnapshot postCarvers, GenStageSnapshot preDiamond) {
        List<String> lines = new ArrayList<>();
        if (postCarvers == null) {
            lines.add("缺失输入量化：未捕获到「装饰开始前」快照，本区块无法量化");
            return lines;
        }

        // ── 缺失输入 A：生成期方块状态 vs 最终世界 ──────────────────────────────
        long total = 0;
        long different = 0;
        long addedOre = 0;
        long addedDiamond = 0;
        long replaceableBecomesSolid = 0;
        long finalAirGenBlock = 0;
        long genAirFinalBlock = 0;
        int minY = postCarvers.minY();
        int usableSections = postCarvers.sectionCount();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos data = new ChunkPos(center.x() + dx, center.z() + dz);
                LevelChunk chunk = level.getChunk(data.x(), data.z());
                int slot = postCarvers.slotOf(data);
                for (int section = 0; section < usableSections && section < chunk.getSectionsCount(); section++) {
                    LevelChunkSection levelSection = chunk.getSection(section);
                    for (int index = 0; index < 4096; index++) {
                        total++;
                        BlockState generated = postCarvers.stateOfId(postCarvers.rawIdAt(slot, section, index));
                        BlockState current = levelSection.getBlockState(index & 15, index >> 8, (index >> 4) & 15);
                        if (postCarvers.idOf(current) == postCarvers.rawIdAt(slot, section, index)) {
                            continue;
                        }
                        different++;
                        if (OreBlockLedger.isOre(current) && !OreBlockLedger.isOre(generated)) {
                            if (OreBlockLedger.isDiamond(current)) {
                                addedDiamond++;
                            } else {
                                addedOre++;
                            }
                        }
                        if (OreBlockLedger.isReplaceable(generated) && !OreBlockLedger.isReplaceable(current)) {
                            replaceableBecomesSolid++;
                        }
                        if (current.isAir() && !generated.isAir()) {
                            finalAirGenBlock++;
                        }
                        if (!current.isAir() && generated.isAir()) {
                            genAirFinalBlock++;
                        }
                    }
                }
            }
        }
        lines.add("缺失输入A｜生成期方块状态 vs 最终世界（中心 3x3，y ∈ [" + minY + ","
                + (minY + usableSections * 16 - 1) + "]）：比较 " + total + " 格，不同 " + different
                + " 格（" + percent(different, total) + "）");
        lines.add("    其中：最终是钻石而生成期不是 " + addedDiamond + " 格；最终是其它矿而生成期不是 "
                + addedOre + " 格；生成期可替换而最终不可替换（会改变 nextFloat 消耗）"
                + replaceableBecomesSolid + " 格");
        lines.add("    其中：最终是空气、生成期有方块 " + finalAirGenBlock + " 格；生成期是空气、最终有方块 "
                + genAirFinalBlock + " 格（后一类正是第一轮清洁台账无法识别的「洞穴/地物空气歧义」）");

        // ── 缺失输入 B：生成期 OCEAN_FLOOR_WG vs 最终世界推出的同一高度图 ──────
        long columns = 0;
        long heightMismatch = 0;
        long generatedHigher = 0;
        long finalHigher = 0;
        long maxDelta = 0;
        long sumDelta = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos data = new ChunkPos(center.x() + dx, center.z() + dz);
                LevelChunk chunk = level.getChunk(data.x(), data.z());
                int slot = postCarvers.slotOf(data);
                for (int localX = 0; localX < 16; localX++) {
                    for (int localZ = 0; localZ < 16; localZ++) {
                        columns++;
                        int generated = postCarvers.columnHeight(Heightmap.Types.OCEAN_FLOOR_WG, slot, localX, localZ);
                        int fromFinal = deriveColumnHeight(chunk, (data.x() << 4) + localX, (data.z() << 4) + localZ,
                                Heightmap.Types.OCEAN_FLOOR_WG);
                        if (generated == fromFinal) {
                            continue;
                        }
                        heightMismatch++;
                        if (generated > fromFinal) {
                            generatedHigher++;
                        } else {
                            finalHigher++;
                        }
                        long delta = Math.abs((long) generated - fromFinal);
                        maxDelta = Math.max(maxDelta, delta);
                        sumDelta += delta;
                    }
                }
            }
        }
        lines.add("缺失输入B｜OCEAN_FLOOR_WG（中心 3x3，共 " + columns + " 列）：不一致 " + heightMismatch
                + " 列（" + percent(heightMismatch, columns) + "）；生成期更高 " + generatedHigher
                + " 列 / 最终更高 " + finalHigher + " 列；最大偏差 " + maxDelta + " 格，平均偏差 "
                + (heightMismatch == 0 ? "0" : String.format(java.util.Locale.ROOT, "%.2f", (double) sumDelta / heightMismatch)) + " 格");

        // ── 附加取证：第一条钻石 feature 执行前的状态相对装饰开始前差了多少 ──────
        if (preDiamond == null) {
            lines.add("附加取证｜第一条钻石 feature 前快照：未捕获到（该区块本次没有钻石 placed_feature 进入放置阶段）");
        } else {
            long preTotal = 0;
            long preDifferent = 0;
            long preOre = 0;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    ChunkPos data = new ChunkPos(center.x() + dx, center.z() + dz);
                    int slot = postCarvers.slotOf(data);
                    int sections = Math.min(postCarvers.sectionCount(), preDiamond.sectionCount());
                    for (int section = 0; section < sections; section++) {
                        for (int index = 0; index < 4096; index++) {
                            preTotal++;
                            short before = postCarvers.rawIdAt(slot, section, index);
                            short later = preDiamond.rawIdAt(slot, section, index);
                            if (before == later) {
                                continue;
                            }
                            preDifferent++;
                            if (OreBlockLedger.isOre(postCarvers.stateOfId(later))) {
                                preOre++;
                            }
                        }
                    }
                }
            }
            lines.add("附加取证｜「装饰开始前」→「第一条钻石 feature 前」状态变化（中心 3x3，" + preTotal
                    + " 格）：不同 " + preDifferent + " 格（" + percent(preDifferent, preTotal) + "），"
                    + "其中变成矿石的 " + preOre + " 格（这就是结构 / 前置 feature 对矿石放置的影响上限）");
        }
        return lines;
    }

    /**
     * 按「最终世界方块状态」推出某列的高度图取值。
     *
     * <p>实现与原版 {@code Heightmap#primeHeightmaps}（Heightmap.java:43-79）同语义：
     * 自 {@code getHighestSectionPosition() + 16 - 1} 往下找第一个满足
     * {@code type.isOpaque()} 的方块，取 {@code y + 1}；找不到则取 {@code minY}。
     * 这正是第一轮实验读到的那个值（第一轮读的是 {@code LevelChunk}，其 WG 高度图已被
     * LevelChunk.java:122-126 丢弃，读取时会按最终状态现补一份）。</p>
     */
    private static int deriveColumnHeight(LevelChunk chunk, int worldX, int worldZ, Heightmap.Types type) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        // 自世界顶部往下找：与原版 primeHeightmaps 同语义（高于最高非空 section 的列必然是空气，
        // 多扫几格不影响结果，故这里不依赖已过时的 getHighestSectionPosition）
        int maxY = chunk.getMinY() + chunk.getSectionsCount() * 16;
        for (int y = maxY - 1; y >= chunk.getMinY(); y--) {
            pos.set(worldX, y, worldZ);
            if (type.isOpaque().test(chunk.getBlockState(pos))) {
                return y + 1;
            }
        }
        return chunk.getMinY();
    }

    private static String percent(long part, long total) {
        if (total == 0) {
            return "0.00%";
        }
        return String.format(java.util.Locale.ROOT, "%.2f%%", (double) part / total * 100.0);
    }
}
