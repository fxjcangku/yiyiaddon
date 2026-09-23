package com.yiyiaddon.seed.validation;

import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>有效证据单元</b>（正式化第六阶段 234.1 收紧；234 原名「独立证据组」）。
 *
 * <h2>一、它解决什么</h2>
 * <p>「8 个相邻的钻石方块」<b>不能</b>算成 8 份独立证据 —— 它们绝大多数来自<b>同一次 worldgen 放置</b>。
 * 如果不做这件事，<b>一个矿脉就能把种子验证通过</b>，那是伪验证。</p>
 *
 * <h2>二、234.1 为什么把「空间连通簇」从独立性判据里摘掉（源码审计结论）</h2>
 * <p>234 的规则是「（目标区块 + 写入者）内部按 26 邻域连通性聚簇，一个连通簇 = 一份独立证据」。
 * 本阶段逐行核对了 Minecraft 26.1.2 本线源码，证明这条规则<b>会系统性多计票</b>：</p>
 * <ol>
 *     <li>{@code PlacedFeature#placeWithContext:42-60} —— 一次 placed_feature 调用把 placement
 *         展开成一串坐标，<b>用同一个 {@code RandomSource}</b> 逐个调用 {@code feature.place(...)}；</li>
 *     <li>{@code ChunkGenerator#applyBiomeDecoration:327-388} —— 每次装饰前
 *         {@code random.setDecorationSeed(levelSeed, origin.getX(), origin.getZ())}，
 *         每个 placed_feature 再 {@code setFeatureSeed(decorationSeed, featureIndex, stepIndex)}。
 *         也就是说「一次放置」的真实 provenance 是
 *         <b>（写入者区块 → decorationSeed）+ 生成阶段 + placed_feature 序号</b>；</li>
 *     <li>{@code OreFeature#doPlace:71-166} —— 一条矿脉的方块是<b>若干椭球</b>的并集，
 *         中间会做重叠去重（{@code BitSet tested}），并且每个格子还要过
 *         {@code canPlaceOre}(目标方块规则测试)。<b>被规则测试拒掉的格子会留下空洞</b>，
 *         于是<b>同一次放置</b>的产出完全可能在空间上断裂成好几簇。</li>
 * </ol>
 * <p>结论：<b>空间断裂 ≠ 不同世界生成事件</b>。用 26 邻域连通簇当独立性判据，等于把
 * 「一份 provenance 被地形切断」误判成「多份证据」，正是 234.1 要堵掉的漏洞。</p>
 *
 * <h2>三、正式口径：conservative independence（宁可少算票）</h2>
 * <blockquote>
 *     无法证明两个证据独立 → 视为同一单元；而不是「无法证明它们相同 → 就拆成两个」。
 * </blockquote>
 * <p>因此正式单元键 = <b>目标区块 + 写入者（originViewer）+ 来源分类 + 矿物种类</b>，
 * <b>整桶就是一个有效证据单元</b>：</p>
 * <ul>
 *     <li><b>目标区块</b>：覆盖调度按目标区块提交，同一区块内的一次装饰批次产出天然相关；
 *         跨目标区块绝不并单元。</li>
 *     <li><b>写入者</b>：它决定 {@code decorationSeed}（原版按区块坐标播种），
 *         因此不同写入者共享的随机流不同，属于可区分的世界生成事件；同一写入者的产出共享同一段
 *         随机流，<b>不能</b>重复计票。</li>
 *     <li><b>写入者未知（UNKNOWN provenance）</b>：{@code null} 自成一种键值，
 *         于是「同一目标区块里所有来源未知的候选」合并成<b>恰好一个</b>单元 ——
 *         绝不因为空间隔了两格就声称两个独立世界生成事件（口径第十一、五十二节硬红线）。</li>
 * </ul>
 * <p>同一单元内部再分几个空间簇，只作为 <b>presentation / debug 信息</b>
 * （见 {@link #spatialComponents()}），<b>不参与</b>任何票数计算。</p>
 *
 * <h2>四、如实记录的两条局限</h2>
 * <ul>
 *     <li>正式层目前只有「写入者区块」这一级 provenance，<b>没有</b>逐 placed_feature / 逐矿脉身份
 *         （{@code PredictedOre} 只有 {@code originViewer}，且来源分类恒为 {@code UNATTRIBUTED}）。
 *         因此本单元是<b>偏保守</b>的：同一写入者自己的装饰里，多条互不相关的矿脉也会并成一个单元，
 *         代价是「少算票」，方向正确（口径第七、十节）。</li>
 *     <li>同一次放置若跨了两个目标区块（写半径 1 允许），会在两个目标区块下各算一个单元 ——
 *         这正是口径第九节给出的单元键形态（含 affectedChunk），如实登记，不假装它不存在。</li>
 * </ul>
 *
 * @param id             单元编号（由单元键稳定派生，与输入顺序 / 集合实现无关，可复现）
 * @param targetChunk    目标区块
 * @param originViewer   写入者（{@code null} = 来源未知）
 * @param oreSource      来源分类
 * @param oreType        矿物种类
 * @param certainty      单元内最强的预测确定性（用于硬冲突判据；见 {@link #certainty()}）
 * @param members        单元内成员坐标（按位置键升序，稳定；同一坐标只留一条）
 * @param confirmedCount 单元内<b>当前</b>为「已确认」的成员数
 * @param observedCount  单元内<b>当前</b>已被实际观察（已确认 + 缺失）的成员数
 * @param missingCount   单元内<b>当前</b>为「缺失」的成员数
 * @param everConfirmed  单元内是否<b>曾经</b>出现过「已确认」（锁存；被挖掉不撤销）
 * @param firstConfirmedAt 单元内首次出现「已确认」的时间（毫秒；从未为 0）
 * @param lastObservedAt 单元内最近一次有效观察的时间（毫秒；从未为 0）
 */
public record SeedValidationEvidenceGroup(long id, ChunkPos targetChunk, ChunkPos originViewer,
                                          OreSource oreSource, OreType oreType, PredictionCertainty certainty,
                                          List<BlockPos> members, int confirmedCount, int observedCount,
                                          int missingCount, boolean everConfirmed, long firstConfirmedAt,
                                          long lastObservedAt) {

    /** 「尚未分组」的哨兵编号（证据刚创建、还没被赋予单元号时使用）。 */
    public static final long UNGROUPED = 0L;

    /** 单元键里「写入者未知」的哨兵值（与任何真实区块坐标都不冲突）。 */
    private static final long NO_VIEWER = Long.MIN_VALUE;

    public SeedValidationEvidenceGroup {
        Objects.requireNonNull(targetChunk, "targetChunk");
        Objects.requireNonNull(oreSource, "oreSource");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(certainty, "certainty");
        members = List.copyOf(members);
    }

    /** 单元内成员数（= 该单元贡献的候选方块数）。 */
    public int size() {
        return members.size();
    }

    /** 该单元当前是否「已确认」（单元内至少一个成员当前真实就是矿物）。 */
    public boolean confirmed() {
        return confirmedCount > 0;
    }

    /** 该单元当前是否已被实际观察过（至少有成员被看到过）。 */
    public boolean observed() {
        return observedCount > 0;
    }

    /**
     * 本单元的 provenance 是否已知。
     *
     * <p><b>已知的只是「哪个区块的装饰写进来的」这一级</b>，不是逐 placed_feature / 逐矿脉身份
     * （正式层没有该信息）。{@code false} = UNKNOWN provenance，此时该单元按最保守规则处理。</p>
     */
    public boolean provenanceKnown() {
        return originViewer != null;
    }

    /** provenance 的一行中文说明（诊断 / 报告用，不隐藏「placement 未知」这件事）。 */
    public String provenanceCn() {
        return originViewer == null
                ? "来源未知（UNKNOWN provenance；同一目标区块内所有未知来源候选合并为一个单元）"
                : "写入者区块(" + originViewer.x() + "," + originViewer.z() + ")已知；"
                        + "placed_feature / 矿脉身份未知（正式层无此信息）";
    }

    /**
     * 单元内部在空间上断裂成几簇（26 邻域连通）。
     *
     * <p><b>只用于展示与诊断</b>：口径第九节明确「空间聚类只能作为 presentation/debug information，
     * 不能再作为 independence 的唯一依据」。它存在只是为了让报告能说清
     * 「这个单元确实是一份 provenance 被切断成多簇」。</p>
     */
    public int spatialComponents() {
        return countSpatialComponents(members);
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "单元 #" + id + " 目标区块(" + targetChunk.x() + "," + targetChunk.z() + ")"
                + (originViewer == null ? " 来源未知" : " 写入者(" + originViewer.x() + "," + originViewer.z() + ")")
                + " 成员 " + members.size() + "（当前已确认 " + confirmedCount + " / 缺失 " + missingCount
                + "，空间簇 " + spatialComponents() + "）"
                + (everConfirmed ? "【曾确认】" : "");
    }

    // ────────────────────────────────────────────────────────────────────────
    // 分组（正式口径）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 把一组证据按 <b>conservative independence</b> 聚成有效证据单元（纯函数，不读世界、不依赖服务状态）。
     *
     * <p>算法就是一次分桶：键 =（目标区块 + 写入者 + 来源 + 矿物），<b>桶内不再裂票</b>。
     * 代价 O(n)：n = 候选数（默认范围 3 时约 1100）。只在一批新观察落地时调用一次，
     * 不在渲染帧里调用。</p>
     *
     * <p><b>重复候选</b>：同一坐标若被多条候选重复描述（例如同一次预测被消费两次），
     * 只保留第一条 —— 保证「重复描述同一位置不重复加票」（口径第十六节 CASE F）。</p>
     *
     * @param evidence 全部证据（顺序不影响结果）
     * @return 有效证据单元清单；顺序按「目标区块 → 最小成员坐标」稳定排列
     */
    public static List<SeedValidationEvidenceGroup> build(List<SeedValidationEvidence> evidence) {
        if (evidence == null || evidence.isEmpty()) {
            return List.of();
        }
        Map<UnitKey, List<SeedValidationEvidence>> buckets = new LinkedHashMap<>();
        Set<Long> seenPositions = new LinkedHashSet<>();
        for (SeedValidationEvidence item : evidence) {
            if (item == null) {
                continue;
            }
            if (!seenPositions.add(item.positionKey())) {
                continue;
            }
            buckets.computeIfAbsent(UnitKey.of(item), key -> new ArrayList<>()).add(item);
        }
        List<SeedValidationEvidenceGroup> units = new ArrayList<>(buckets.size());
        for (Map.Entry<UnitKey, List<SeedValidationEvidence>> bucket : buckets.entrySet()) {
            units.add(assemble(bucket.getKey(), bucket.getValue()));
        }
        units.sort(Comparator
                .comparingInt((SeedValidationEvidenceGroup unit) -> unit.targetChunk().x())
                .thenComparingInt(unit -> unit.targetChunk().z())
                .thenComparingLong(unit -> unit.members().isEmpty() ? 0L : unit.members().get(0).asLong()));
        return units;
    }

    /**
     * <b>审计专用</b>：用 234 的旧规则（桶内 26 邻域连通簇各算一份）重算一遍分组。
     *
     * <p>它只服务于「旧口径到底高估了多少票」这条取证要求（口径第十四、十五节），
     * <b>不参与</b>任何判定；正式判定一律走 {@link #build(List)}。</p>
     */
    public static List<SeedValidationEvidenceGroup> buildLegacySpatialForAudit(
            List<SeedValidationEvidence> evidence) {
        if (evidence == null || evidence.isEmpty()) {
            return List.of();
        }
        Map<UnitKey, List<SeedValidationEvidence>> buckets = new LinkedHashMap<>();
        Set<Long> seenPositions = new LinkedHashSet<>();
        for (SeedValidationEvidence item : evidence) {
            if (item == null || !seenPositions.add(item.positionKey())) {
                continue;
            }
            buckets.computeIfAbsent(UnitKey.of(item), key -> new ArrayList<>()).add(item);
        }
        List<SeedValidationEvidenceGroup> groups = new ArrayList<>();
        for (List<SeedValidationEvidence> bucket : buckets.values()) {
            for (List<BlockPos> component : spatialComponents(bucket)) {
                List<SeedValidationEvidence> members = new ArrayList<>(component.size());
                for (SeedValidationEvidence item : bucket) {
                    if (component.contains(item.position())) {
                        members.add(item);
                    }
                }
                groups.add(assemble(UnitKey.of(bucket.get(0)), members));
            }
        }
        groups.sort(Comparator
                .comparingInt((SeedValidationEvidenceGroup group) -> group.targetChunk().x())
                .thenComparingInt(group -> group.targetChunk().z())
                .thenComparingLong(group -> group.members().isEmpty() ? 0L : group.members().get(0).asLong()));
        return groups;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 内部实现
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 单元键：目标区块 + 写入者 + 来源 + 矿物。
     *
     * <p>写入者未知用 {@link #NO_VIEWER} 表示，于是「同区块所有 UNKNOWN provenance 候选」
     * 天然落进同一个键，不需要额外分支。</p>
     */
    private record UnitKey(long targetChunkPack, long viewerPack, OreSource oreSource, OreType oreType) {

        static UnitKey of(SeedValidationEvidence item) {
            return new UnitKey(item.targetChunk().pack(),
                    item.originViewer() == null ? NO_VIEWER : item.originViewer().pack(),
                    item.oreSource(), item.oreType());
        }
    }

    /** 把一个桶装配成不可变的有效证据单元。 */
    private static SeedValidationEvidenceGroup assemble(UnitKey key, List<SeedValidationEvidence> bucket) {
        List<SeedValidationEvidence> sorted = new ArrayList<>(bucket);
        sorted.sort(Comparator.comparingLong(SeedValidationEvidence::positionKey));
        List<BlockPos> positions = new ArrayList<>(sorted.size());
        int confirmed = 0;
        int observed = 0;
        int missing = 0;
        boolean everConfirmed = false;
        long firstConfirmedAt = 0L;
        long lastObservedAt = 0L;
        PredictionCertainty strongest = null;
        for (SeedValidationEvidence item : sorted) {
            positions.add(item.position());
            if (item.confirmed()) {
                confirmed++;
            }
            if (item.observationState() == OreObservationState.MISSING) {
                missing++;
            }
            if (item.observed()) {
                observed++;
            }
            if (item.everConfirmed()) {
                everConfirmed = true;
                long at = item.firstObservedAt();
                if (at > 0L && (firstConfirmedAt == 0L || at < firstConfirmedAt)) {
                    firstConfirmedAt = at;
                }
            }
            lastObservedAt = Math.max(lastObservedAt, item.lastObservedAt());
            strongest = strongest == null ? item.certainty() : stronger(strongest, item.certainty());
        }
        if (strongest == null) {
            strongest = PredictionCertainty.UNRESOLVED;
        }
        return new SeedValidationEvidenceGroup(
                unitId(key),
                new ChunkPos(ChunkPos.getX(key.targetChunkPack()), ChunkPos.getZ(key.targetChunkPack())),
                key.viewerPack() == NO_VIEWER ? null : ChunkPos.unpack(key.viewerPack()),
                key.oreSource(), key.oreType(), strongest, positions,
                confirmed, observed, missing, everConfirmed, firstConfirmedAt, lastObservedAt);
    }

    /** 「更强」的确定性：DETERMINISTIC &gt; SCHEDULE_SENSITIVE &gt; UNRESOLVED（枚举声明顺序）。 */
    private static PredictionCertainty stronger(PredictionCertainty left, PredictionCertainty right) {
        return left.ordinal() <= right.ordinal() ? left : right;
    }

    /**
     * 派生稳定单元编号。
     *
     * <p>只做「单元键 + 64 位混合」：<b>同一份证据重新分组时编号不变</b>，
     * 与 HashMap 迭代顺序、对象 identityHashCode、当前 tick 顺序全部无关（口径第二十四节）。</p>
     */
    private static long unitId(UnitKey key) {
        long hash = 0x9E3779B97F4A7C15L;
        hash = mix(hash ^ key.targetChunkPack());
        hash = mix(hash ^ key.viewerPack());
        hash = mix(hash ^ (key.oreSource().ordinal() * 31L + key.oreType().ordinal()));
        return hash == UNGROUPED ? 1L : hash;
    }

    /** 64 位混合（splitmix 形态；确定性、无随机）。 */
    private static long mix(long value) {
        long z = value + 0x9E3779B97F4A7C15L;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    /** 一批坐标按 26 邻域连通性分成几簇。 */
    private static int countSpatialComponents(List<BlockPos> positions) {
        return componentsOf(positions).size();
    }

    /** 把一个桶按 26 邻域连通性分簇（审计 / 展示用，正式判定不使用）。 */
    private static List<List<BlockPos>> spatialComponents(List<SeedValidationEvidence> bucket) {
        List<BlockPos> positions = new ArrayList<>(bucket.size());
        for (SeedValidationEvidence item : bucket) {
            positions.add(item.position());
        }
        return componentsOf(positions);
    }

    /**
     * 26 邻域连通分量（并查集）。
     *
     * <p>为什么是 26 而不是 6：原版矿脉是三维团块，斜对角相邻同样属于同一团块；
     * 用 6 邻域把一个团块切成两半只会让「空间簇」这个数字更大。</p>
     */
    private static List<List<BlockPos>> componentsOf(List<BlockPos> positions) {
        int count = positions.size();
        int[] parent = new int[count];
        for (int index = 0; index < count; index++) {
            parent[index] = index;
        }
        Map<Long, Integer> indexByKey = new LinkedHashMap<>();
        for (int index = 0; index < count; index++) {
            indexByKey.put(positions.get(index).asLong(), index);
        }
        for (int index = 0; index < count; index++) {
            BlockPos pos = positions.get(index);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }
                        Integer other = indexByKey.get(BlockPos.asLong(
                                pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz));
                        if (other != null) {
                            union(parent, index, other);
                        }
                    }
                }
            }
        }
        Map<Integer, List<BlockPos>> components = new LinkedHashMap<>();
        for (int index = 0; index < count; index++) {
            components.computeIfAbsent(find(parent, index), key -> new ArrayList<>()).add(positions.get(index));
        }
        return new ArrayList<>(components.values());
    }

    private static int find(int[] parent, int index) {
        int root = index;
        while (parent[root] != root) {
            root = parent[root];
        }
        int walk = index;
        while (parent[walk] != root) {
            int next = parent[walk];
            parent[walk] = root;
            walk = next;
        }
        return root;
    }

    private static void union(int[] parent, int left, int right) {
        int leftRoot = find(parent, left);
        int rightRoot = find(parent, right);
        if (leftRoot != rightRoot) {
            parent[rightRoot] = leftRoot;
        }
    }
}
