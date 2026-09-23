package com.yiyiaddon.dev.seedpoc;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * 一份「真实生成期阶段快照」：某个区块执行装饰的那一刻，它要读的那片区域长什么样。
 *
 * <p><b>覆盖范围</b>：以被捕获区块为中心的 3x3 个区块，纵向取世界底部起的若干 section
 * （见 {@link SeedPocConstants#CAPTURE_SECTION_COUNT}）。这个范围不是拍脑袋定的：
 * 原版 {@code ChunkGenerator#applyBiomeDecoration} 读生物群系用的是
 * {@code ChunkPos.rangeClosed(center, 1)}（ChunkGenerator.java:330），
 * 写半径由 {@code ChunkPyramid} 的 {@code FEATURES(blockStateWriteRadius=1)} 限定为 3x3
 * （ChunkPyramid.java:29-35），而 {@code OreFeature} 的探测点最多再往外探十几格
 * （{@code OreFeature.place:38-46} 的 {@code sizeXZ}），所以 3x3 区块 + 纵向足够覆盖
 * 特征放置的全部读写范围。</p>
 *
 * <p><b>两类数据，分别对应第一轮登记的两条缺失输入</b>：</p>
 * <ol>
 *     <li>{@link #idAt} 系列 —— 生成期方块状态（第一轮缺失输入 A）；</li>
 *     <li>{@link #columnHeight} 系列 —— 生成期 {@code OCEAN_FLOOR_WG} / {@code WORLD_SURFACE_WG}
 *         高度图取值（第一轮缺失输入 B），记的是 {@code Heightmap#getFirstAvailable}，
 *         与原版 {@code WorldGenRegion#getHeight}（WorldGenRegion.java:397-399）同语义。</li>
 * </ol>
 */
final class GenStageSnapshot {

    /**
     * 「该 section 还没有数据」的编号。
     *
     * <p><b>为什么必须有这个哨兵</b>：生成期各阶段的区块是「逐级长出来」的——
     * 一个区块在 {@code BIOMES} 状态下 section 还不存在，在它身上读生物群系本来就无值可读。
     * 若把它当成「某个生物群系」，两侧比较出来的差异就全是假的（第四轮首轮
     * 「BIOMES 阶段生物群系不一致 2304 格」就是这么来的：真实邻域当时还没跑到 BIOMES）。
     * 方块状态不需要哨兵——原版读一个未生成的 section 拿到的就是空气，与「记成空气」同义。</p>
     */
    static final short NO_DATA = -1;

    /** 快照中心区块。 */
    private final ChunkPos viewer;
    /** 快照阶段标签（中文，直接进报告）。 */
    private final String stage;
    /** 世界最低方块 y（快照所有纵向索引的基准）。 */
    private final int minY;
    /** 本次快照覆盖的 section 数。 */
    private final int sectionCount;
    /** 9（3x3 区块）× sectionCount × 4096 的方块状态编号。 */
    private final short[] states;
    /** 9 × 256 的 OCEAN_FLOOR_WG 列高度（firstAvailable 语义）。 */
    private final short[] oceanFloor;
    /** 9 × 256 的 WORLD_SURFACE_WG 列高度（firstAvailable 语义）。 */
    private final short[] worldSurface;
    /**
     * 9 × sectionCount × 64 的生物群系编号（section 内 4x4x4 个 quart 单元，
     * 下标 {@code (qy*4+qz)*4+qx}）。
     *
     * <p><b>为什么要单独存</b>：第四轮要和离线状态比对「生物群系」这一层
     * （用户口径第七节明确要求），而生物群系不在方块状态里、也不在高度图里。
     * 两侧的编号空间不同，所以比较时一律比 {@link #biomeKey} 给出的注册表键。</p>
     */
    private final short[] biomes;
    /** 生物群系编号 → 注册表键（如 {@code minecraft:plains}）。 */
    private final List<String> biomeKeys;
    /** 每个区块一行的结构信息（{@code starts=…[…] refs=…}），下标 = 3x3 槽位。 */
    private final List<String> structureInfo;
    /**
     * 9 × sectionCount：该（槽位, section）在采集这一刻是否已经存在（原版 {@code ChunkAccess#getSection}
     * 返回非 null；{@code BIOMES} 与 {@code NOISE} 之间的区块就是「不存在」）。
     *
     * <p>它只服务一件事：让比较器把「这一侧还没长到这个阶段」与「两侧都长到这儿但值不同」分开报，
     * 而不是把前者也算成生成差异。</p>
     */
    private final boolean[] sectionPresent;
    /**
     * 9 个槽位各自的「已跑到的最远状态」序号（-1 = 该槽位在采集这一刻还没有区块对象）。
     *
     * <p><b>这是混龄邻域的直接证据</b>：同一个 3x3 里不同区块可能处在不同生成状态。
     * 它有两个用处：① 生物群系是否可比的判据（状态低于 {@code BIOMES} 的区块其生物群系容器
     * 还是工厂默认值，读出来是「假生物群系」，不是真实值）；② 报告里必须写明
     * 「目标区块跑到某个阶段时，周围每圈至少处在什么状态」（用户第四轮第九节）。</p>
     */
    private final int[] slotStatusIndex;
    /** 编号表（与全局共用一份，避免每份快照各存一张表）。 */
    private final StatePalette palette;
    /** 捕获序号（先抓后抓的顺序本身就是取证信息）。 */
    private final int order;

    GenStageSnapshot(ChunkPos viewer, String stage, int minY, int sectionCount, short[] states,
                     short[] oceanFloor, short[] worldSurface, short[] biomes, List<String> biomeKeys,
                     List<String> structureInfo, boolean[] sectionPresent, int[] slotStatusIndex,
                     StatePalette palette, int order) {
        this.viewer = viewer;
        this.stage = stage;
        this.minY = minY;
        this.sectionCount = sectionCount;
        this.states = states;
        this.oceanFloor = oceanFloor;
        this.worldSurface = worldSurface;
        this.biomes = biomes;
        this.biomeKeys = biomeKeys;
        this.structureInfo = structureInfo;
        this.sectionPresent = sectionPresent;
        this.slotStatusIndex = slotStatusIndex;
        this.palette = palette;
        this.order = order;
    }

    /** 快照中心区块。 */
    ChunkPos viewer() {
        return viewer;
    }

    /** 阶段标签。 */
    String stage() {
        return stage;
    }

    /** 捕获序号。 */
    int order() {
        return order;
    }

    /** 世界最低方块 y。 */
    int minY() {
        return minY;
    }

    /** 覆盖的 section 数。 */
    int sectionCount() {
        return sectionCount;
    }

    /** 单份快照里的方块格数（= 9 × sectionCount × 4096）。 */
    int blockCount() {
        return states.length;
    }

    /**
     * 该快照是否覆盖到某个区块。
     *
     * @param data 数据区块（必须是 viewer ±1 之内）
     */
    boolean covers(ChunkPos data) {
        int dx = data.x() - viewer.x();
        int dz = data.z() - viewer.z();
        return Math.abs(dx) <= 1 && Math.abs(dz) <= 1;
    }

    /** 数据区块在快照里的槽位；(dx+1)*3+(dz+1)。 */
    int slotOf(ChunkPos data) {
        return (data.x() - viewer.x() + 1) * 3 + (data.z() - viewer.z() + 1);
    }

    /** 3x3 区块槽位（顺序同 {@code ChunkPos.rangeClosed}：x 外层、z 内层）。 */
    ChunkPos chunkOfSlot(int slot) {
        return new ChunkPos(viewer.x() + slot / 3 - 1, viewer.z() + slot % 3 - 1);
    }

    /** 方块状态编号；越界（超出本次纵向覆盖）返回 null 表示「本快照不覆盖这一格」。 */
    private Short idAt(int slot, int y, int localX, int localZ) {
        int yIndex = y - minY;
        if (yIndex < 0) {
            return null;
        }
        int section = yIndex >> 4;
        if (section >= sectionCount) {
            return null;
        }
        int index = (section * 4096) + ((yIndex & 15) * 16 + localZ) * 16 + localX;
        return states[slot * sectionCount * 4096 + index];
    }

    /** 按「世界坐标」取方块状态；本快照不覆盖这一格时返回 null。 */
    BlockState stateAt(int worldX, int worldY, int worldZ) {
        ChunkPos data = new ChunkPos(worldX >> 4, worldZ >> 4);
        if (!covers(data)) {
            return null;
        }
        Short id = idAt(slotOf(data), worldY, worldX & 15, worldZ & 15);
        return id == null ? null : palette.stateOf(id);
    }

    /** 直接按槽位取状态编号（安装与取证用，避免反复算区块坐标）。 */
    short rawIdAt(int slot, int section, int index) {
        return states[(slot * sectionCount + section) * 4096 + index];
    }

    /** 按槽位还状态。 */
    BlockState stateOfId(short id) {
        return palette.stateOf(id);
    }

    /** 把任意状态换算成本快照的编号（安装时用来判断「这一格需不需要改」）。 */
    short idOf(BlockState state) {
        return palette.idOf(state);
    }

    /** 状态编号表种数。 */
    int paletteSize() {
        return palette.size();
    }

    /** 纵向覆盖是否包含某个 section 序号。 */
    boolean coversSection(int section) {
        return section >= 0 && section < sectionCount;
    }

    /**
     * 生成期高度图列高度（{@code firstAvailable} 语义，即「最高阻挡物 y + 1」）。
     *
     * @param type 只支持两个 WG 类型
     */
    int columnHeight(Heightmap.Types type, int slot, int localX, int localZ) {
        short[] source = type == Heightmap.Types.OCEAN_FLOOR_WG ? oceanFloor : worldSurface;
        return source[slot * 256 + localX + localZ * 16] & 0xFFFF;
    }

    /** 该快照覆盖的区块数（固定 9，用于报告里自证范围）。 */
    int coveredChunks() {
        return 9;
    }

    /** 生物群系编号 → 注册表键；编号非法时返回 {@code "?"}。 */
    String biomeKey(int slot, int section, int qIndex) {
        int index = (slot * sectionCount + section) * 64 + qIndex;
        if (index < 0 || index >= biomes.length) {
            return "?";
        }
        short id = biomes[index];
        return id >= 0 && id < biomeKeys.size() ? biomeKeys.get(id) : "?";
    }

    /**
     * 生物群系注册表键；该格在采集这一刻<b>没有数据</b>（section 不存在）时返回 {@code null}。
     *
     * <p>比较器靠 {@code null} 把「这一侧还没长到该阶段」从「值不同」里摘出去。</p>
     */
    String biomeKeyOrNull(int slot, int section, int qIndex) {
        int index = (slot * sectionCount + section) * 64 + qIndex;
        if (index < 0 || index >= biomes.length) {
            return null;
        }
        short id = biomes[index];
        if (id == NO_DATA || id < 0 || id >= biomeKeys.size()) {
            return null;
        }
        return biomeKeys.get(id);
    }

    /** 该（槽位, section）在采集这一刻是否已经存在。 */
    boolean sectionPresent(int slot, int section) {
        int index = slot * sectionCount + section;
        return index >= 0 && index < sectionPresent.length && sectionPresent[index];
    }

    /** 该槽位在采集这一刻「已跑到的最远状态」；没有区块对象返回 null。 */
    ChunkStatus slotStatus(int slot) {
        if (slot < 0 || slot >= slotStatusIndex.length) {
            return null;
        }
        int index = slotStatusIndex[slot];
        if (index < 0 || index >= ChunkStatus.getStatusList().size()) {
            return null;
        }
        return ChunkStatus.getStatusList().get(index);
    }

    /**
     * 该槽位的生物群系数据是否「已经填过」（区块状态达到过 {@code BIOMES}）。
     *
     * <p><b>为什么必须这么判</b>：section 存在不等于生物群系有值——还没跑 {@code BIOMES} 的区块，
     * 它的 section 生物群系容器里装的是调色板工厂的默认值（主世界是 {@code plains}），
     * 拿它去和真实算出来的生物群系比，差出来全是假的。</p>
     */
    boolean biomesReady(int slot) {
        ChunkStatus status = slotStatus(slot);
        return status != null && status.isOrAfter(ChunkStatus.BIOMES);
    }

    /** 9 个槽位的状态（报告用，逐圈说明混龄邻域）。 */
    String slotStatusesCn() {
        StringBuilder text = new StringBuilder("[");
        for (int slot = 0; slot < 9; slot++) {
            if (slot > 0) {
                text.append(", ");
            }
            ChunkStatus status = slotStatus(slot);
            text.append(status == null ? "无" : status.getName());
        }
        return text.append("]").toString();
    }

    /** 某个槽位里「还没长出来」的 section 数（报告用：说明两侧覆盖范围差在哪）。 */
    int missingSections(int slot) {
        int missing = 0;
        for (int section = 0; section < sectionCount; section++) {
            if (!sectionPresent(slot, section)) {
                missing++;
            }
        }
        return missing;
    }

    /** 整份快照里「还没长出来」的 section 数（报告用）。 */
    int missingSections() {
        int missing = 0;
        for (int slot = 0; slot < 9; slot++) {
            missing += missingSections(slot);
        }
        return missing;
    }

    /**
     * 按世界坐标取生物群系注册表键（quart 精度，向上取整到所在 quart 单元）。
     *
     * <p>返回 null 表示「本快照不覆盖这一格」（越界或纵向超出覆盖范围）。</p>
     */
    String biomeKeyAt(int worldX, int worldY, int worldZ) {
        ChunkPos data = new ChunkPos(worldX >> 4, worldZ >> 4);
        if (!covers(data)) {
            return null;
        }
        int yIndex = worldY - minY;
        if (yIndex < 0) {
            return null;
        }
        int section = yIndex >> 4;
        if (section >= sectionCount) {
            return null;
        }
        int qx = (worldX >> 2) & 3;
        int qy = (worldY >> 2) & 3;
        int qz = (worldZ >> 2) & 3;
        return biomeKeyOrNull(slotOf(data), section, (qy * 4 + qz) * 4 + qx);
    }

    /** 某个区块槽位的结构信息行；越界返回 {@code "?"}。 */
    String structureInfo(int slot) {
        return slot >= 0 && slot < structureInfo.size() ? structureInfo.get(slot) : "?";
    }

    /** 生物群系编号表种数（报告用）。 */
    int biomePaletteSize() {
        return biomeKeys.size();
    }

    /** 报告用一行摘要。 */
    String cn() {
        return stage + "（中心 " + viewer.x() + "," + viewer.z() + "，覆盖 3x3 区块 × "
                + sectionCount + " section，y ∈ [" + minY + "," + (minY + sectionCount * 16 - 1) + "]，"
                + "格数 " + blockCount() + "，捕获序号 " + order + "）";
    }

    /** 便于取证：把某个坐标在快照里的状态描述成文本。 */
    String describeAt(BlockPos pos) {
        BlockState state = stateAt(pos.getX(), pos.getY(), pos.getZ());
        return state == null ? "（本快照不覆盖）" : OreBlockLedger.shortId(state);
    }
}
