package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * 第四轮 · 逐 BlockPos 比较器（离线状态 vs 真实状态）。
 *
 * <p><b>用户口径第七节要求逐项比对</b>：方块状态、生物群系、两张 WG 高度图、结构信息；
 * 并且<b>不能只输出 hash</b>——必须给出「第一处不同的坐标 + 两侧各自是什么」。</p>
 *
 * <p><b>三类结果必须分开，否则数字会骗人</b>（第四轮首轮就踩过）：</p>
 * <ol>
 *     <li><b>快照缺失</b>：某一侧整份快照没取到（{@link Result#offlineMissing()} /
 *         {@link Result#realMissing()}）。这种情况<b>不能</b>算「完全一致」，只能算「不可判定」；</li>
 *     <li><b>覆盖缺失（不可比）</b>：格子在某一侧「还没长出来」（section 不存在）。
 *         生成期各阶段区块逐级长成，读一个还没到该阶段的区块本来就无值可读，
 *         把它算成差异是假差异。本比较器把这类格子单独计数，不参与「值不同」；</li>
 *     <li><b>值不同</b>：两侧都有数据、值不相等——这才是真正的分歧。</li>
 * </ol>
 *
 * <p>比较范围严格取两侧快照的交集（同一 3x3、同样的 section 数），
 * 所以「一致率」不会被覆盖范围差异污染。</p>
 */
final class StageComparator {

    /**
     * 一个阶段（或 pre-diamond）的比较结果。
     *
     * @param stage                 阶段标签（中文）
     * @param viewer                中心区块
     * @param offlineMissing        离线侧整份快照缺失
     * @param realMissing           真实侧整份快照缺失
     * @param stateTotal            方块状态总比较格数
     * @param stateDiff             值不同的格数
     * @param offlineMissingSections 离线侧「还没长出来」的 section 数（覆盖缺失）
     * @param realMissingSections   真实侧「还没长出来」的 section 数（覆盖缺失）
     * @param firstStatePos         第一处值不同坐标（"x,y,z"）；全同则为 null
     * @param firstStateReal        第一处不同处「真实侧」的方块
     * @param firstStateOffline     第一处不同处「离线侧」的方块
     * @param biomeTotal            生物群系总比较单元数（quart）
     * @param biomeDiff             值不同的生物群系单元数
     * @param biomeUncomparable     某一侧无生物群系数据的单元数（覆盖缺失）
     * @param firstBiomeDiff        第一处不同的生物群系描述
     * @param heightTotal           高度图总比较列数（两张图合计）
     * @param heightDiff            值不同的列数
     * @param heightUncomparable    某一侧「整列区块还没长出来」的列数（覆盖缺失）
     * @param firstHeightDiff       第一处不同的高度描述
     * @param structureDiff         结构信息不一致的区块数
     * @param structureDetail       结构信息不一致的明细（最多两条）
     */
    record Result(String stage, ChunkPos viewer,
                  boolean offlineMissing, boolean realMissing,
                  long stateTotal, long stateDiff, int offlineMissingSections, int realMissingSections,
                  String firstStatePos, String firstStateReal, String firstStateOffline,
                  long biomeTotal, long biomeDiff, long biomeUncomparable, String firstBiomeDiff,
                  long heightTotal, long heightDiff, long heightUncomparable, String firstHeightDiff,
                  int structureDiff, String structureDetail) {

        /** 是否两侧都有快照、可比范围内四类数据全部一致。 */
        boolean exact() {
            return !offlineMissing && !realMissing && stateDiff == 0 && biomeDiff == 0
                    && heightDiff == 0 && structureDiff == 0;
        }

        /** 是否根本判不了（某一侧快照缺失）。缺失绝不允许被当成「一致」。 */
        boolean unjudgeable() {
            return offlineMissing || realMissing;
        }

        /** 一侧快照缺失时的描述。 */
        String missingCn() {
            if (offlineMissing && realMissing) {
                return "两侧快照都缺失";
            }
            return offlineMissing ? "离线快照缺失" : "真实快照缺失";
        }

        /** 一行中文摘要（进报告）。 */
        String cn() {
            if (unjudgeable()) {
                return stage + "：不可判定（" + missingCn() + "）";
            }
            return stage + "：方块 " + (stateTotal - stateDiff) + "/" + stateTotal
                    + "（不一致 " + stateDiff + "；未长出的 section 离线 " + offlineMissingSections
                    + " / 真实 " + realMissingSections + "）"
                    + "；生物群系 " + (biomeTotal - biomeDiff - biomeUncomparable) + "/" + biomeTotal
                    + "（不一致 " + biomeDiff + "；不可比 " + biomeUncomparable + "）"
                    + "；高度图 " + (heightTotal - heightDiff - heightUncomparable) + "/" + heightTotal
                    + "（不一致 " + heightDiff + "；不可比 " + heightUncomparable + "）"
                    + "；结构 9 区块中不一致 " + structureDiff
                    + " → " + (exact() ? "完全一致" : "**存在差异**");
        }

        /** 第一处分叉的完整描述（两个字段都为空表示无差异）。 */
        String firstDifferenceCn() {
            if (unjudgeable()) {
                return missingCn() + "，本次不可判定";
            }
            if (stateDiff > 0) {
                return "第一处方块差异 @" + firstStatePos + "：真实 " + firstStateReal + " / 离线 " + firstStateOffline;
            }
            if (biomeDiff > 0) {
                return "第一处生物群系差异：" + firstBiomeDiff;
            }
            if (heightDiff > 0) {
                return "第一处高度图差异：" + firstHeightDiff;
            }
            if (structureDiff > 0) {
                return "结构信息差异：" + structureDetail;
            }
            return "";
        }
    }

    private StageComparator() {
    }

    /** 把 {@link Result#firstStatePos()} 的 "x,y,z" 解回 {@link BlockPos}；不合法返回 null。 */
    static BlockPos parsePos(String text) {
        if (text == null) {
            return null;
        }
        String[] parts = text.split(",");
        if (parts.length != 3) {
            return null;
        }
        try {
            return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /**
     * 同一个坐标在四个阶段与 pre-diamond 上的取值时间线（离线 / 真实各一行）。
     *
     * <p><b>它是「第一处分叉在哪一阶段」的取证工具</b>：只看某一阶段的差异，无法判断差异是这一刻产生的
     * 还是更早就有了；把同一个坐标在 5 个快照上的取值排成时间线，一眼就能看出它在哪一步开始分叉。</p>
     *
     * @param stages     四个阶段的快照（键 = 阶段名）
     * @param preDiamond pre-diamond 快照
     * @param pos        坐标
     * @param offline    true = 取离线快照，false = 取真实快照
     */
    static String timeline(Map<String, GenStageSnapshot> stages, GenStageSnapshot preDiamond,
                           BlockPos pos, boolean offline) {
        StringBuilder text = new StringBuilder();
        for (String stage : GenStageCapture.STAGES) {
            GenStageSnapshot snapshot = stages.get(stage);
            text.append(stage).append('=')
                    .append(snapshot == null ? "（快照缺失）" : snapshot.describeAt(pos)).append(' ');
        }
        text.append("pre-diamond=")
                .append(preDiamond == null ? "（快照缺失）" : preDiamond.describeAt(pos));
        return text.toString();
    }

    /**
     * 「仅目标区块」口径下差异方块的成对统计（真实 → 离线），取出现次数最多的三对，附差异的 y 区间。
     *
     * <p>用来一眼判断差异的性质：若全是「air ↔ 某方块」，说明是雕刻/洞穴层的差异；
     * 若是「方块 ↔ 方块」的成对替换，说明是噪声地形或矿脉层的差异。</p>
     */
    static String centerDifferencePairs(GenStageSnapshot offline, GenStageSnapshot real) {
        if (offline == null || real == null) {
            return "（快照缺失）";
        }
        int sections = Math.min(offline.sectionCount(), real.sectionCount());
        Map<String, Integer> pairs = new LinkedHashMap<>();
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int section = 0; section < sections; section++) {
            int baseY = offline.minY() + section * 16;
            for (int index = 0; index < 4096; index++) {
                BlockState realState = real.stateOfId(real.rawIdAt(4, section, index));
                BlockState offlineState = offline.stateOfId(offline.rawIdAt(4, section, index));
                if (realState == offlineState) {
                    continue;
                }
                String key = OreBlockLedger.shortId(realState) + " → " + OreBlockLedger.shortId(offlineState);
                pairs.merge(key, 1, Integer::sum);
                int y = baseY + (index >> 8);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        }
        if (pairs.isEmpty()) {
            return "无差异";
        }
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(pairs.entrySet());
        sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        StringBuilder text = new StringBuilder("差异成对（真实→离线）共 ").append(pairs.size())
                .append(" 种，y ∈ [").append(minY).append(',').append(maxY).append("]，最多的三对：");
        for (int i = 0; i < Math.min(3, sorted.size()); i++) {
            if (i > 0) {
                text.append("；");
            }
            text.append(sorted.get(i).getKey()).append(' ').append(sorted.get(i).getValue()).append(" 格");
        }
        return text.toString();
    }

    /**
     * 「会真正影响钻石链」的差异格统计（仅目标区块口径、只统计钻石纵向范围内）。
     *
     * <p><b>为什么这个数才是关键</b>：{@code OreFeature#canPlaceOre}（OreFeature.java:168-181）
     * 只对<b>可替换方块</b>才会做放置判定并消耗随机数：
     * {@code targetState.target.test(...)}（{@code TagMatchTest}，即
     * {@code #stone_ore_replaceables} / {@code #deepslate_ore_replaceables}）不成立时直接返回 false，
     * <b>不消耗随机数、不放置</b>。所以：</p>
     * <ul>
     *     <li>两侧都是可替换方块（如 {@code tuff ↔ deepslate}、{@code granite ↔ stone}）的差异
     *         <b>不影响矿石放置</b>——判定都通过、随机数消耗次数相同、落点是同一个坐标；</li>
     *     <li>只有「一侧可替换、另一侧不可替换」（如 {@code gravel}、{@code smooth_basalt}、
     *         树叶、别的矿石）的差异才可能改变随机数流与落点。</li>
     * </ul>
     *
     * @param maxY 钻石特征的纵向上限（本实验 = 31，见 {@code DIAMOND_SCAN_SECTION_COUNT}）
     */
    static String diamondRelevantDifferences(GenStageSnapshot offline, GenStageSnapshot real, int maxY) {
        if (offline == null || real == null) {
            return "（快照缺失）";
        }
        int sections = Math.min(offline.sectionCount(), real.sectionCount());
        int relevant = 0;
        int replaceableOnly = 0;
        List<String> samples = new ArrayList<>(3);
        for (int section = 0; section < sections; section++) {
            int baseY = offline.minY() + section * 16;
            if (baseY > maxY) {
                break;
            }
            for (int index = 0; index < 4096; index++) {
                BlockState realState = real.stateOfId(real.rawIdAt(4, section, index));
                BlockState offlineState = offline.stateOfId(offline.rawIdAt(4, section, index));
                if (realState == offlineState) {
                    continue;
                }
                int worldY = baseY + (index >> 8);
                if (worldY > maxY) {
                    continue;
                }
                boolean realReplaceable = isOreReplaceable(realState);
                boolean offlineReplaceable = isOreReplaceable(offlineState);
                if (realReplaceable && offlineReplaceable) {
                    replaceableOnly++;
                    continue;
                }
                relevant++;
                if (samples.size() < 3) {
                    int worldX = (offline.chunkOfSlot(4).x() << 4) + (index & 15);
                    int worldZ = (offline.chunkOfSlot(4).z() << 4) + ((index >> 4) & 15);
                    samples.add("(" + worldX + "," + worldY + "," + worldZ + ") 真实 "
                            + OreBlockLedger.shortId(realState) + " / 离线 "
                            + OreBlockLedger.shortId(offlineState));
                }
            }
        }
        String text = "钻石纵向范围内（y ≤ " + maxY + "）的差异格：可替换方块之间 "
                + replaceableOnly + " 格（不影响矿石放置，因为双方都过 target 判定、随机数消耗次数相同）；"
                + "**不可替换差异（保守判据：只要有一侧不可替换就算）" + relevant + " 格**（这类格子才可能改变随机流）";
        if (!samples.isEmpty()) {
            text += "，样本：" + String.join("；", samples);
        }
        return text;
    }

    /** 是否可被矿石替换（{@code #stone_ore_replaceables} ∪ {@code #deepslate_ore_replaceables}）。 */
    private static boolean isOreReplaceable(BlockState state) {
        return state.is(BlockTags.STONE_ORE_REPLACEABLES) || state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    }

    /**
     * 比两份快照。
     *
     * @param stage   阶段标签
     * @param offline 离线快照
     * @param real    真实快照
     */
    static Result compare(String stage, ChunkPos viewer, GenStageSnapshot offline, GenStageSnapshot real) {
        return compare(stage, viewer, offline, real, false);
    }

    /**
     * 只比目标区块自己（3x3 里中间那一格）。
     *
     * <p><b>为什么需要这个口径</b>：真实世界里目标区块被装饰时，邻域往往「已经生成完了」
     * （服务端按由近到远生成，且本机开着 C2ME 并行），而离线 pipeline 是按依赖半径逐层铺的，
     * 邻域在阶段边界上的年龄天然不同。把「邻域年龄」造成的差异与「生成器算错了」分开，
     * 唯一的办法就是单独看目标区块自己。</p>
     */
    static Result compareCenterOnly(String stage, ChunkPos viewer, GenStageSnapshot offline,
                                    GenStageSnapshot real) {
        return compare(stage, viewer, offline, real, true);
    }

    private static Result compare(String stage, ChunkPos viewer, GenStageSnapshot offline, GenStageSnapshot real,
                                  boolean centerOnly) {
        if (offline == null || real == null) {
            // 快照缺失只报「缺失」，绝不退化成「一致」
            return new Result(stage, viewer, offline == null, real == null,
                    0, 0, 0, 0, null, null, null,
                    0, 0, 0, null, 0, 0, 0, null, 0,
                    offline == null ? "离线快照缺失" : "真实快照缺失");
        }

        int sections = Math.min(offline.sectionCount(), real.sectionCount());
        int firstSlot = centerOnly ? 4 : 0;
        int lastSlot = centerOnly ? 4 : 8;
        long stateTotal = 0;
        long stateDiff = 0;
        int offlineMissingSections = 0;
        int realMissingSections = 0;
        String firstStatePos = null;
        String firstStateReal = null;
        String firstStateOffline = null;

        long biomeTotal = 0;
        long biomeDiff = 0;
        long biomeUncomparable = 0;
        String firstBiomeDiff = null;

        for (int slot = firstSlot; slot <= lastSlot; slot++) {
            ChunkPos data = offline.chunkOfSlot(slot);
            int baseX = data.x() << 4;
            int baseZ = data.z() << 4;
            for (int section = 0; section < sections; section++) {
                int baseY = offline.minY() + section * 16;
                boolean offlineHasSection = offline.sectionPresent(slot, section);
                boolean realHasSection = real.sectionPresent(slot, section);
                if (!offlineHasSection) {
                    offlineMissingSections++;
                }
                if (!realHasSection) {
                    realMissingSections++;
                }
                for (int index = 0; index < 4096; index++) {
                    BlockState realState = real.stateOfId(real.rawIdAt(slot, section, index));
                    BlockState offlineState = offline.stateOfId(offline.rawIdAt(slot, section, index));
                    stateTotal++;
                    if (realState == offlineState) {
                        continue;
                    }
                    stateDiff++;
                    if (firstStatePos == null) {
                        int worldX = baseX + (index & 15);
                        int worldY = baseY + (index >> 8);
                        int worldZ = baseZ + ((index >> 4) & 15);
                        firstStatePos = worldX + "," + worldY + "," + worldZ;
                        firstStateReal = OreBlockLedger.shortId(realState);
                        firstStateOffline = OreBlockLedger.shortId(offlineState);
                    }
                }
                for (int qIndex = 0; qIndex < 64; qIndex++) {
                    String realBiome = real.biomeKeyOrNull(slot, section, qIndex);
                    String offlineBiome = offline.biomeKeyOrNull(slot, section, qIndex);
                    biomeTotal++;
                    if (realBiome == null || offlineBiome == null) {
                        // 某一侧这一刻还没有生物群系数据（section 不存在）：不可比，不算差异
                        biomeUncomparable++;
                        continue;
                    }
                    if (!real.biomesReady(slot) || !offline.biomesReady(slot)) {
                        // 该区块这一侧还没跑过 BIOMES：容器里是调色板工厂的默认值（不是真实生物群系），
                        // 拿它比出来的差异全是假的，按不可比记
                        biomeUncomparable++;
                        continue;
                    }
                    if (realBiome.equals(offlineBiome)) {
                        continue;
                    }
                    biomeDiff++;
                    if (firstBiomeDiff == null) {
                        int qx = qIndex & 3;
                        int qz = (qIndex >> 2) & 3;
                        int worldX = baseX + qx * 4;
                        int worldY = baseY + ((qIndex >> 4) & 3) * 4;
                        int worldZ = baseZ + qz * 4;
                        firstBiomeDiff = "(" + worldX + "," + worldY + "," + worldZ + ") 真实 "
                                + realBiome + " / 离线 " + offlineBiome;
                    }
                }
            }
        }

        long heightTotal = 0;
        long heightDiff = 0;
        long heightUncomparable = 0;
        String firstHeightDiff = null;
        Heightmap.Types[] types = {Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG};
        for (Heightmap.Types type : types) {
            for (int slot = firstSlot; slot <= lastSlot; slot++) {
                ChunkPos data = offline.chunkOfSlot(slot);
                // 该侧整列区块还没长出来（所有 section 都不存在）时高度图没有意义，按不可比记
                boolean offlineHasTerrain = offline.missingSections(slot) < sections;
                boolean realHasTerrain = real.missingSections(slot) < sections;
                for (int localX = 0; localX < 16; localX++) {
                    for (int localZ = 0; localZ < 16; localZ++) {
                        heightTotal++;
                        if (!offlineHasTerrain || !realHasTerrain) {
                            heightUncomparable++;
                            continue;
                        }
                        int realHeight = real.columnHeight(type, slot, localX, localZ);
                        int offlineHeight = offline.columnHeight(type, slot, localX, localZ);
                        if (realHeight == offlineHeight) {
                            continue;
                        }
                        heightDiff++;
                        if (firstHeightDiff == null) {
                            firstHeightDiff = type.name() + " @(" + ((data.x() << 4) + localX) + ","
                                    + ((data.z() << 4) + localZ) + ") 真实 " + realHeight
                                    + " / 离线 " + offlineHeight;
                        }
                    }
                }
            }
        }

        int structureDiff = 0;
        String firstStructureDiff = "";
        for (int slot = firstSlot; slot <= lastSlot; slot++) {
            String realInfo = real.structureInfo(slot);
            String offlineInfo = offline.structureInfo(slot);
            if (realInfo.equals(offlineInfo)) {
                continue;
            }
            structureDiff++;
            if (firstStructureDiff.isEmpty()) {
                firstStructureDiff = "区块 " + offline.chunkOfSlot(slot).x() + "," + offline.chunkOfSlot(slot).z()
                        + " 真实 " + realInfo + " / 离线 " + offlineInfo;
            }
        }

        return new Result(stage, viewer, false, false, stateTotal, stateDiff,
                offlineMissingSections, realMissingSections, firstStatePos, firstStateReal, firstStateOffline,
                biomeTotal, biomeDiff, biomeUncomparable, firstBiomeDiff,
                heightTotal, heightDiff, heightUncomparable, firstHeightDiff,
                structureDiff, firstStructureDiff);
    }
}
