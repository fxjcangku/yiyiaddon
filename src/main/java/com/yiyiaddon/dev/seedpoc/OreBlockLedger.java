package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 矿石台账：把一片区域里的矿石「记下来 → 清成替换前的样子 → 之后原样写回」。
 *
 * <p><b>为什么需要它</b>：装饰阶段放置矿石时读的是<b>生成期方块状态</b>——
 * {@code OreFeature#canPlaceOre} 先判目标方块是否属于可替换标签（石头类 / 深板岩类），
 * 再判是否贴空气。所以要让矿物「重新放置」在同一片地形上，必须先把已存在的矿石腾空，
 * 并保证腾空的方式在语义上等价于原版放置之前的状态。</p>
 *
 * <p><b>两种口径</b>：</p>
 * <ul>
 *     <li>{@link #isDiamond}：只认钻石（含深层变种）——第一轮实验用；</li>
 *     <li>{@link #isOre}：按方块 id 是否含 {@code _ore} 判定全部矿石——第二轮「整步重放」用，
 *         因为原版同一个装饰步骤里各种矿是混在一起按固定顺序放的，彼此的占位会互相影响。</li>
 * </ul>
 *
 * <p><b>坐标扫描范围</b>：只扫 y ≤ 32 的 section。依据是原版钻石四条 placed_feature 的高度区间
 * （{@code OrePlacements.java:184-204}）：{@code aboveBottom(-80)…aboveBottom(80)} 在主世界
 * （minY = -64）即 y ∈ [-64, 16]，另有 {@code ore_diamond_medium} 为 {@code absolute(-64)…absolute(-4)}；
 * 钻石不可能出现在 y &gt; 16，取 32 是留了安全余量。</p>
 */
public final class OreBlockLedger {

    /** 扫描高度上限（含）。理由见类注释。 */
    private static final int MAX_SCAN_Y = 32;

    private OreBlockLedger() {
    }

    /** 是否钻石矿（含深层变种）。 */
    public static boolean isDiamond(BlockState state) {
        return state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE);
    }

    /**
     * 是否「矿石类」方块：方块 id 含 {@code _ore}。
     *
     * <p>原版所有矿石方块（含深层变种）命名都是 {@code xxx_ore} / {@code deepslate_xxx_ore}，
     * 因此这条判据能覆盖同一装饰步骤里会被重放的所有矿石；raw 类方块不会有地物放置，不必考虑。</p>
     */
    public static boolean isOre(BlockState state) {
        var key = BuiltInRegistries.BLOCK.getResourceKey(state.getBlock()).orElse(null);
        return key != null && key.identifier().getPath().contains("_ore");
    }

    /**
     * 是否「矿石或骨块」：用于「连地下结构步骤一起重放」的口径。
     *
     * <p>化石（{@code fossil_diamonds}）会把一部分骨块替换成钻石矿，它属于
     * {@code underground_structures} 步骤；重放这一步时，骨块与矿石都要先腾空、
     * 之后由重放自己放回来，最后再按台账原样还原，测试世界才不会留下痕迹。</p>
     */
    public static boolean isOreOrBone(BlockState state) {
        return isOre(state) || state.is(Blocks.BONE_BLOCK);
    }

    /**
     * 是否属于原版「可被矿石替换」的方块。
     *
     * <p><b>这条判据是本次 PoC 定层的关键</b>：原版 {@code OreFeature#canPlaceOre} 只有在
     * 目标方块通过该标签判定后，才会调用 {@code shouldSkipAirCheck} —— 而后者在
     * {@code 0 < discard_chance_on_air_exposure < 1} 时会消耗一次 {@code nextFloat()}。
     * 也就是说：<b>候选点的方块状态会改变随机数消耗次数</b>，一旦与生成期差一格，
     * 该 feature 在该装饰遍后续所有矿脉的位置都会跟着漂移。</p>
     */
    public static boolean isReplaceable(BlockState state) {
        return state.is(BlockTags.STONE_ORE_REPLACEABLES) || state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    }

    /** 方块短 id（去掉命名空间），报告与标签里读起来更干净。 */
    public static String shortId(BlockState state) {
        String id = String.valueOf(BuiltInRegistries.BLOCK.getKey(state.getBlock()));
        int colon = id.indexOf(':');
        return colon >= 0 ? id.substring(colon + 1) : id;
    }

    /**
     * 采集范围内符合条件的矿位，并记录其当时的方块状态（还原用）。
     *
     * @param level     服务端世界
     * @param center    中心区块
     * @param radius    区块半径
     * @param predicate 判定条件（{@link #isDiamond} 或 {@link #isOre}）
     * @return 位置 → 原方块状态（顺序稳定，便于比对输出）
     */
    public static Map<BlockPos, BlockState> collect(ServerLevel level, ChunkPos center, int radius,
                                                    Predicate<BlockState> predicate) {
        Map<BlockPos, BlockState> found = new LinkedHashMap<>();
        for (int cx = center.x() - radius; cx <= center.x() + radius; cx++) {
            for (int cz = center.z() - radius; cz <= center.z() + radius; cz++) {
                LevelChunk chunk = level.getChunk(cx, cz);
                LevelChunkSection[] sections = chunk.getSections();
                int minSectionY = level.getMinSectionY();
                for (int index = 0; index < sections.length; index++) {
                    LevelChunkSection section = sections[index];
                    if (section == null) {
                        continue;
                    }
                    int baseY = (minSectionY + index) * 16;
                    if (baseY > MAX_SCAN_Y || section.hasOnlyAir()) {
                        continue;
                    }
                    for (int lx = 0; lx < 16; lx++) {
                        for (int ly = 0; ly < 16; ly++) {
                            for (int lz = 0; lz < 16; lz++) {
                                BlockState state = section.getBlockState(lx, ly, lz);
                                if (predicate.test(state)) {
                                    found.put(new BlockPos((cx << 4) + lx, baseY + ly, (cz << 4) + lz), state);
                                }
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    /** 只取单个区块内符合条件的矿位（真值 / 预测集合的口径都走这里，保证两边一样）。 */
    public static Set<BlockPos> collectInChunk(ServerLevel level, ChunkPos pos, Predicate<BlockState> predicate) {
        Set<BlockPos> found = new LinkedHashSet<>();
        LevelChunk chunk = level.getChunk(pos.x(), pos.z());
        LevelChunkSection[] sections = chunk.getSections();
        int minSectionY = level.getMinSectionY();
        for (int index = 0; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section == null) {
                continue;
            }
            int baseY = (minSectionY + index) * 16;
            if (baseY > MAX_SCAN_Y || section.hasOnlyAir()) {
                continue;
            }
            for (int lx = 0; lx < 16; lx++) {
                for (int ly = 0; ly < 16; ly++) {
                    for (int lz = 0; lz < 16; lz++) {
                        if (predicate.test(section.getBlockState(lx, ly, lz))) {
                            found.add(new BlockPos((pos.x() << 4) + lx, baseY + ly, (pos.z() << 4) + lz));
                        }
                    }
                }
            }
        }
        return found;
    }

    /** 把台账里的矿位全部清成「矿石替换前的样子」，使重放能重新放置它们。 */
    public static void clear(ServerLevel level, Map<BlockPos, BlockState> recorded) {
        for (BlockPos pos : recorded.keySet()) {
            write(level, pos, preOreState(pos));
        }
    }

    /**
     * 还原：先撤掉实验期间多写出来的矿石，再把台账里的矿位原样写回。
     *
     * <p>写回的是<b>当时记录的真实状态</b>，因此结果是原样还原，不会给测试世界留下实验痕迹。</p>
     *
     * @param predicate 判定「哪些是本次实验可能多写出来的矿石」
     */
    public static void restore(ServerLevel level, Map<BlockPos, BlockState> recorded, ChunkPos center, int radius,
                               Predicate<BlockState> predicate) {
        Map<BlockPos, BlockState> current = collect(level, center, radius, predicate);
        for (BlockPos pos : current.keySet()) {
            if (!recorded.containsKey(pos)) {
                write(level, pos, preOreState(pos));
            }
        }
        for (Map.Entry<BlockPos, BlockState> entry : recorded.entrySet()) {
            write(level, entry.getKey(), entry.getValue());
        }
    }

    /**
     * 矿石替换前的方块：y ≥ 0 记石头、y &lt; 0 记深板岩。
     *
     * <p>这不是猜测：主世界深板岩面在 y = 0；且无论原本是石头、凝灰岩还是花岗岩，只要它与被替换下来的
     * 方块属于同一条可替换标签，重放时的判定结果就与原来一致，因此清空不会改变判定路径。</p>
     */
    private static BlockState preOreState(BlockPos pos) {
        return pos.getY() >= 0 ? Blocks.STONE.defaultBlockState() : Blocks.DEEPSLATE.defaultBlockState();
    }

    /** 单点写入（走 section，避开 {@code Level#setBlock} 的邻居更新与光照重算）。 */
    public static void write(ServerLevel level, BlockPos pos, BlockState state) {
        LevelChunk chunk = level.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
        LevelChunkSection section = chunk.getSection(level.getSectionIndex(pos.getY()));
        section.setBlockState(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state, false);
    }
}
