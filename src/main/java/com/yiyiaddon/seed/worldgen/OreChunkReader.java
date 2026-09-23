package com.yiyiaddon.seed.worldgen;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreDefinition;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 种子挖矿正式模块 · 从离线区块里读出某类矿物的全部坐标。
 *
 * <p>只读、不写、不做判定，也不参与预测算法本身：它把「区块里的矿物在哪」这件事从预测逻辑里
 * 单独拆出来。离线侧与测试侧共用同一份读取代码，两侧口径一致，差集才不会因覆盖范围不同而失真。</p>
 *
 * <h2>236：扫描范围与方块集合都来自定义，不再写死</h2>
 * <p>233 时这里有两处「只有钻石」的硬编码：固定的 section 数与
 * {@code isTarget} 里的 {@code Blocks.DIAMOND_ORE} 判定。236 把它们一起改成查
 * {@link SeedOreRegistry}：</p>
 * <ul>
 *     <li><b>方块集合</b>：按 {@code (维度, 矿物)} 定义取（同矿不同维度的方块不同，
 *         例：主世界金是 {@code gold_ore}，下界金是 {@code nether_gold_ore}）；</li>
 *     <li><b>扫描窗口</b>：由定义里的 {@code scanMinY} ~ {@code scanMaxY} 现算 section 范围，
 *         再与区块自身的高度范围求交（自定义高度维度不会越界）。</li>
 * </ul>
 *
 * <p><b>钻石没有回归</b>：钻石定义的窗口是 y ∈ [−64, 16]，落在 section 0~5 内，
 * 与 233 的「自最低 section 起 6 个」（y ∈ [−64, 31]）<b>得到同一组 section</b>，
 * 因此扫描成本与结果集逐格不变（见 {@code SeedOreRegistry} 里钻石窗口的出处注释）。</p>
 *
 * <p><b>区块缺失绝不冒充「没有矿」</b>：{@code chunk} 为 null 时直接抛异常，
 * 由调用方按「预测失败」处理。</p>
 */
public final class OreChunkReader {

    /**
     * 单次扫描的 section 上限（防御性：定义写错时不允许退化成整世界遍历）。
     *
     * <p>主世界 24 个 section、下界 16 个，因此 32 对两个原版维度都是<b>不可达</b>的上限，
     * 只在维度高度异常时才会触发截断。</p>
     */
    private static final int MAX_SCAN_SECTIONS = 32;

    private OreChunkReader() {
    }

    /**
     * 读出一个区块里指定矿物的全部坐标（按扫描顺序，稳定可复现）。
     *
     * @param chunk      区块（离线 {@code ProtoChunk} 或真实 {@code LevelChunk}，都是 {@link ChunkAccess}）
     * @param oreType    矿物种类
     * @param dimension  维度档案（决定用哪一份定义；同矿不同维度的方块与窗口不同）
     */
    public static Set<BlockPos> collect(ChunkAccess chunk, OreType oreType, SeedDimensionProfile dimension) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(dimension, "dimension");
        SeedOreDefinition definition = SeedOreRegistry.of(dimension, oreType);
        if (definition == null) {
            throw new IllegalArgumentException("该维度不支持该矿物："
                    + dimension.dimensionId() + " / " + oreType.name());
        }
        Set<BlockPos> positions = new LinkedHashSet<>();
        ChunkPos chunkPos = chunk.getPos();
        int minSectionY = chunk.getMinSectionY();
        int firstSection = sectionOf(definition.scanMinY(), minSectionY);
        int lastSection = sectionOf(definition.scanMaxY(), minSectionY);
        firstSection = Math.max(0, firstSection);
        lastSection = Math.min(Math.min(chunk.getSectionsCount() - 1, MAX_SCAN_SECTIONS - 1), lastSection);
        for (int section = firstSection; section <= lastSection; section++) {
            LevelChunkSection levelSection = chunk.getSection(section);
            if (levelSection == null || levelSection.hasOnlyAir()) {
                continue;
            }
            int baseY = (minSectionY + section) * 16;
            for (int localY = 0; localY < 16; localY++) {
                int worldY = baseY + localY;
                if (worldY < definition.scanMinY() || worldY > definition.scanMaxY()) {
                    continue;
                }
                for (int localZ = 0; localZ < 16; localZ++) {
                    for (int localX = 0; localX < 16; localX++) {
                        BlockState state = levelSection.getBlockState(localX, localY, localZ);
                        if (definition.matches(state)) {
                            positions.add(new BlockPos((chunkPos.x() << 4) + localX, worldY,
                                    (chunkPos.z() << 4) + localZ));
                        }
                    }
                }
            }
        }
        return positions;
    }

    /** 某个绝对 Y 落在第几个 section（相对区块自己的 minSectionY）。 */
    private static int sectionOf(int worldY, int minSectionY) {
        return Math.floorDiv(worldY, 16) - minSectionY;
    }
}
