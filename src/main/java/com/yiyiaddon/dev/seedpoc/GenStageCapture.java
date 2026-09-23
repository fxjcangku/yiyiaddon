package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC · 真实生成期探针（第二轮）。
 *
 * <p><b>它回答什么</b>：第一轮把「已完全生成的世界」当装饰期状态用，导致查全 59.26%。
 * 本轮在<b>真实区块生成过程中</b>取阶段快照，再把快照当成影子 worldgen 的输入状态，
 * 用来判定「拿到正确生成期状态之后，当前 Seed 算法能否逐 BlockPos 复现」。</p>
 *
 * <p><b>三份快照与注入点</b>（注入点见 {@code com.yiyiaddon.mixin.client} 下两个 Mixin）：</p>
 * <ol>
 *     <li><b>post-carvers</b>：{@code ChunkGenerator#applyBiomeDecoration} 的 HEAD
 *         （ChunkGenerator.java:318）。此刻中心区块刚过 {@code CARVERS}、{@code FEATURES} 还没开始，
 *         3x3 邻域由 {@code ChunkPyramid} 保证至少处在 {@code CARVERS}（ChunkPyramid.java:29-35）
 *         —— 正是第一轮报告第三节认定的「装饰开始前的区块方块状态」。</li>
 *     <li><b>pre-diamond</b>：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD
 *         （PlacedFeature.java:38），且仅在该 feature 是钻石四条之一、且该区块尚未抓过时触发。
 *         即「这个区块第一条钻石 placed_feature 执行前」的世界状态，用来区分结构 / 前置 feature 的影响。</li>
 *     <li>两份快照都同时记录同阶段的 {@code OCEAN_FLOOR_WG} / {@code WORLD_SURFACE_WG} 列高度
 *         （第一轮缺失输入 B）。</li>
 * </ol>
 *
 * <p><b>为什么钩子本身是安全的</b>：两个钩子只在 {@code -Dyiyiaddon.seedpoc.enabled=1} 且
 * 目标区块落在测试区域内时才真正取数据，其余情况只做一次集合查询就返回；
 * 回放期间用 {@link #setReplaying(boolean)} 关闸，避免我们自己的回放触发再次捕获。</p>
 */
public final class GenStageCapture {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 全部快照共用一张编号表（对比同一格的状态时编号可直接比大小）。 */
    private static final StatePalette PALETTE = new StatePalette();

    /** 需要捕获的区块（= 测试区块 ±1；测试区块的回放要跑中心 3x3 九遍）。 */
    private static final Set<Long> REGION = Collections.synchronizedSet(new HashSet<>());

    /** 各区块在「装饰开始前」拿到的那份快照。 */
    private static final Map<Long, GenStageSnapshot> POST_CARVERS = Collections.synchronizedMap(new HashMap<>());

    /** 各区块在「第一条钻石 feature 执行前」拿到的那份快照。 */
    private static final Map<Long, GenStageSnapshot> PRE_DIAMOND = Collections.synchronizedMap(new HashMap<>());

    /**
     * 各区块当前这一次真实装饰的批号（{@code applyBiomeDecoration} 每进入一次就 +1）。
     *
     * <p><b>它存在的唯一理由</b>：第三轮要求 pre-diamond 与 post-diamond 必须属于
     * <b>同一个 viewer + 同一次真实装饰</b>，禁止跨 viewer、禁止跨装饰批次配对。
     * 批号就是这条约束的可验证形式：配对前必须先比批号，不等就判定为「不可配对」。</p>
     */
    private static final Map<Long, Integer> DECORATION_PASS = Collections.synchronizedMap(new HashMap<>());

    /** 各区块「第一条钻石 feature 执行前」快照所属的装饰批号。 */
    private static final Map<Long, Integer> PRE_DIAMOND_PASS = Collections.synchronizedMap(new HashMap<>());

    // ── 第四轮新增：真实侧逐阶段 checkpoint ────────────────────────────────

    /** 阶段名（真实侧与离线侧共用同一批标签，比较器按它配对）。 */
    public static final String STAGE_BIOMES = "BIOMES";

    /** 阶段名：噪声地形。 */
    public static final String STAGE_NOISE = "NOISE";

    /** 阶段名：地表。 */
    public static final String STAGE_SURFACE = "SURFACE";

    /** 阶段名：雕刻器（=「装饰开始前」那一刻，与原版 CARVERS 完成同义）。 */
    public static final String STAGE_CARVERS = "CARVERS";

    /** 各阶段四个标签（报告与比较器按这个顺序输出）。 */
    public static final List<String> STAGES = List.of(STAGE_BIOMES, STAGE_NOISE, STAGE_SURFACE, STAGE_CARVERS);

    /**
     * 真实侧逐阶段快照：键 = {@code 阶段@x,z}。
     *
     * <p>它存在的唯一理由：第四轮若出现差异，必须能指出「第一处分叉在哪一个 generation stage」，
     * 而不是只报一个总的「pre-diamond 不一致」。采集只在测试区块上做（成本有界），
     * 并且回放期间一律不采。</p>
     */
    private static final Map<String, GenStageSnapshot> STAGE_SNAPSHOTS =
            Collections.synchronizedMap(new HashMap<>());

    /** 逐阶段采集的次数（报告用：每项为 0 即说明该阶段没取到证）。 */
    private static final Map<String, Integer> STAGE_EVENTS = Collections.synchronizedMap(new HashMap<>());

    /** 钻石四条 placed_feature 的身份集合（与注册表实例比对，避免用到值比较）。 */
    private static volatile Set<PlacedFeature> diamondFeatures;

    /** 捕获序号（先抓后抓本身是取证信息）。 */
    private static int order;

    /** 装饰批号发放计数器（每次进入 applyBiomeDecoration 取一个新号）。 */
    private static int passSequence;

    /** 回放期间置真：钩子立刻返回，防止自激。 */
    private static volatile boolean replaying;

    /**
     * 第五轮顺序实验模式。
     *
     * <p>第五轮要比较的<b>恰恰是生成期时序本身</b>，因此在本模式下把所有会取数据、分配大对象的探针
     * （阶段快照、逐阶段 checkpoint、第一条钻石 feature 前快照、单次装饰 oracle）全部关掉，
     * 只保留「装饰批号」这一项零成本的记账：它正是「谁先装饰」的唯一证据。</p>
     */
    private static volatile boolean orderMode;

    /** 区域是否已按测试区块清单构建。 */
    private static volatile boolean regionReady;

    /** 捕获过程中的异常计数与首条异常（异常绝不允许扩散到世界生成里）。 */
    private static int failures;
    private static String firstFailure = "";

    /** 事件计数（报告用）。 */
    private static int featuresHeadEvents;
    private static int placeFeatureEvents;

    private GenStageCapture() {
    }

    /**
     * 按测试区块清单构建捕获区域。必须在任何区块生成之前调用（{@code SeedPocEntry} 挂载时即调用）。
     */
    public static void prepareRegion() {
        REGION.clear();
        for (long[] chunk : SeedPocFlags.captureChunks()) {
            for (int dx = -SeedPocConstants.SNAPSHOT_VIEW_RADIUS; dx <= SeedPocConstants.SNAPSHOT_VIEW_RADIUS; dx++) {
                for (int dz = -SeedPocConstants.SNAPSHOT_VIEW_RADIUS; dz <= SeedPocConstants.SNAPSHOT_VIEW_RADIUS; dz++) {
                    REGION.add(ChunkPos.pack((int) chunk[0] + dx, (int) chunk[1] + dz));
                }
            }
        }
        regionReady = true;
    }

    /** 是否处于「回放」状态（回放期间所有钩子立刻返回）。 */
    public static void setReplaying(boolean value) {
        replaying = value;
    }

    /** 开/关第五轮顺序实验模式（见 {@link #orderMode}）。 */
    public static void setOrderMode(boolean value) {
        orderMode = value;
    }

    /** 是否处于第五轮顺序实验模式。 */
    public static boolean isOrderMode() {
        return orderMode;
    }

    /**
     * 注入点一：{@code ChunkGenerator#applyBiomeDecoration} 的 HEAD。
     *
     * @param level 装饰期世界访问层（原版是 {@code WorldGenRegion}，读 3x3 合法）
     * @param chunk 正在被装饰的中心区块
     */
    public static void onFeaturesHead(WorldGenLevel level, ChunkAccess chunk) {
        if (level instanceof OfflineChunkRegion) {
            // 离线 pipeline 会真的调用一次原版 applyBiomeDecoration；那一遍属于离线链路，
            // 绝不允许当成「真实装饰开始前」写进真实侧快照表（否则离线会污染真实侧取证）
            return;
        }
        if (!captureActive(level)) {
            return;
        }
        ChunkPos viewer = chunk.getPos();
        if (!containsViewer(viewer)) {
            return;
        }
        // 每次进入装饰都发一个新批号：同一区块若被装饰第二次，pre/post 也不会跨批次配对
        DECORATION_PASS.put(viewer.pack(), nextPass());
        if (orderMode) {
            // 第五轮：只留批号，不取任何快照（快照会改变生成期时序，而时序正是本轮的被测量）
            featuresHeadEvents++;
            return;
        }
        if (POST_CARVERS.containsKey(viewer.pack())) {
            return;
        }
        featuresHeadEvents++;
        try {
            GenStageSnapshot snapshot = capture(level, viewer, "装饰开始前（post-CARVERS / pre-FEATURES）");
            POST_CARVERS.put(viewer.pack(), snapshot);
            // 第四轮：这一刻就是 CARVERS 阶段的终点，直接登记成 checkpoint
            putStage(level, viewer, STAGE_CARVERS, snapshot);
        } catch (Throwable error) {
            recordFailure("post-carvers", viewer, error);
        }
    }

    /**
     * 注入点三（第四轮新增）：{@code ChunkStatusTasks} 各生成阶段 RETURN。
     *
     * <p>由 {@code ChunkStatusStageProbeMixin} 调用，把 BIOMES / NOISE / SURFACE 三个阶段
     * 真实跑完那一刻的区域状态取下来。这里用 {@code context} + {@code step} + {@code cache}
     * 现场重建一个原版 {@code WorldGenRegion}——只有这样才能读到「生成期那一刻」的视图，
     * 而不是最终世界（{@code ServerLevel#getChunk} 会拿到已经完全生成好的区块，那正是
     * 第一、二轮踩过的「最终状态冒充生成期状态」）。</p>
     *
     * @param stage   阶段名（{@link #STAGE_BIOMES} 等）
     * @param context 原版生成上下文（要它的 ServerLevel 与注册表）
     * @param step    当前步骤（决定 region 的依赖半径与写半径）
     * @param cache   本步骤的区块缓存
     * @param chunk   刚跑完该阶段的中心区块
     */
    public static void onStageComplete(String stage, WorldGenContext context, ChunkStep step,
                                       StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk) {
        if (context == null || step == null || cache == null || chunk == null) {
            return;
        }
        if (orderMode) {
            // 第五轮：逐阶段 checkpoint 会各取一份 3x3 快照，属于会改变时序的重探针，本模式下关闭
            return;
        }
        ServerLevel level = context.level();
        if (!captureActive(level)) {
            return;
        }
        ChunkPos viewer = chunk.getPos();
        if (!containsViewer(viewer)) {
            return;
        }
        STAGE_EVENTS.merge(stage, 1, Integer::sum);
        String key = stageKey(stage, viewer);
        if (STAGE_SNAPSHOTS.containsKey(key)) {
            return;
        }
        try {
            WorldGenRegion region = new WorldGenRegion(level, cache, step, chunk);
            putStage(region, viewer, stage, capture(region, viewer, stage + " 后"));
        } catch (Throwable error) {
            recordFailure(stage, viewer, error);
        }
    }

    /** 取某区块某个阶段的真实快照；没取到返回 null。 */
    static GenStageSnapshot stage(String stage, ChunkPos viewer) {
        return STAGE_SNAPSHOTS.get(stageKey(stage, viewer));
    }

    /** 某区块四个阶段的真实快照（键 = 阶段名，顺序与 {@link #STAGES} 一致）；缺失的阶段不放进去。 */
    static Map<String, GenStageSnapshot> stageSnapshots(ChunkPos viewer) {
        Map<String, GenStageSnapshot> result = new LinkedHashMap<>();
        for (String stage : STAGES) {
            GenStageSnapshot snapshot = stage(stage, viewer);
            if (snapshot != null) {
                result.put(stage, snapshot);
            }
        }
        return result;
    }

    /** 逐阶段采集次数（报告用）。 */
    static Map<String, Integer> stageEvents() {
        return STAGE_EVENTS;
    }

    /** 阶段快照登记（回放期间不登记，避免把重放当真实阶段）。 */
    private static void putStage(WorldGenLevel level, ChunkPos viewer, String stage, GenStageSnapshot snapshot) {
        if (snapshot == null || replaying) {
            return;
        }
        STAGE_SNAPSHOTS.put(stageKey(stage, viewer), snapshot);
    }

    private static String stageKey(String stage, ChunkPos viewer) {
        return stage + "@" + viewer.x() + "," + viewer.z();
    }

    /**
     * 注入点二：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD。
     *
     * @param level   装饰期世界访问层
     * @param origin  本次放置的原点（= 中心区块最小角的 {@code SectionPos.origin()}，据此反推区块）
     * @param feature 即将执行的 placed_feature
     */
    public static void onBeforePlacedFeature(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        if (level instanceof OfflineChunkRegion) {
            // 第四轮：离线 pipeline 自己会驱动一次真实的 FEATURES，那一次的「第一条钻石 feature 前」
            // 由 OfflineStageCapture 单独记录，绝不能混进真实侧的快照表
            return;
        }
        if (orderMode) {
            // 第五轮：pre-diamond 快照同样是重探针，本模式下关闭
            return;
        }
        if (!captureActive(level)) {
            return;
        }
        placeFeatureEvents++;
        if (!isDiamondFeature(level, feature)) {
            return;
        }
        ChunkPos viewer = new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4);
        if (!containsViewer(viewer) || PRE_DIAMOND.containsKey(viewer.pack())) {
            return;
        }
        try {
            PRE_DIAMOND.put(viewer.pack(), capture(level, viewer, "第一条钻石 placed_feature 执行前"));
            PRE_DIAMOND_PASS.put(viewer.pack(), currentPass(viewer.pack()));
        } catch (Throwable error) {
            recordFailure("pre-diamond", viewer, error);
        }
    }

    /** 取某区块的「装饰开始前」快照；未捕获到返回 null。 */
    public static GenStageSnapshot postCarvers(ChunkPos pos) {
        return POST_CARVERS.get(pos.pack());
    }

    /** 取某区块的「第一条钻石 feature 执行前」快照；未捕获到返回 null。 */
    public static GenStageSnapshot preDiamond(ChunkPos pos) {
        return PRE_DIAMOND.get(pos.pack());
    }

    /** 某区块「第一条钻石 feature 执行前」快照所属装饰批号；未捕获到返回 -1。 */
    public static int preDiamondPass(ChunkPos pos) {
        Integer pass = PRE_DIAMOND_PASS.get(pos.pack());
        return pass == null ? -1 : pass;
    }

    /** 某区块当前这次装饰的批号；未记录返回 -1。 */
    public static int currentPass(long packedViewer) {
        Integer pass = DECORATION_PASS.get(packedViewer);
        return pass == null ? -1 : pass;
    }

    /**
     * 3x3 邻域的真实装饰批号（报告用，直接对应「谁先被装饰」）。
     *
     * <p><b>它是「跨区块写入」这条因果链的最后一环</b>：{@code FEATURES} 的写半径是 1
     * （{@code ChunkPyramid} {@code FEATURES blockStateWriteRadius(1)}），所以<b>先被装饰</b>的邻域区块
     * 会把地物方块写进中心区块。批号比中心小的邻域，就是「在中心之前装饰过、因而写进了中心」的那些区块。</p>
     *
     * @param viewer 中心区块
     */
    static String passSummaryCn(ChunkPos viewer) {
        int centerPass = currentPass(viewer.pack());
        StringBuilder text = new StringBuilder("中心批号=").append(centerPass <= 0 ? "未记录" : centerPass)
                .append("；邻域 ");
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos neighbor = new ChunkPos(viewer.x() + dx, viewer.z() + dz);
                int pass = currentPass(neighbor.pack());
                text.append('(').append(neighbor.x()).append(',').append(neighbor.z()).append(')');
                if (dx == 0 && dz == 0) {
                    text.append("=中心");
                } else if (pass <= 0) {
                    text.append("=实验开始前就已生成");
                } else {
                    text.append('=').append(pass);
                    if (centerPass > 0 && pass < centerPass) {
                        text.append("（先于中心装饰 → 可写进中心）");
                    }
                }
                text.append(' ');
            }
        }
        return text.toString();
    }

    /** 该区块是否属于本次实验的捕获区域。 */
    public static boolean containsViewer(ChunkPos viewer) {
        return REGION.contains(viewer.pack());
    }

    /** 是否处于「回放」状态（回放期间所有钩子立刻返回）。 */
    public static boolean isReplaying() {
        return replaying;
    }

    /** 实验是否武装（总开关已开且捕获区域已就绪）。 */
    public static boolean captureArmed() {
        // 第五轮顺序实验模式下，这条闸门同时代表「重探针是否武装」——顺序实验一律不武装重探针
        // （单次装饰 oracle 与逐事件台账都以它为准，见 DiamondFeatureOracle / OreVeinTrace）
        return SeedPocFlags.enabled() && regionReady && !orderMode;
    }

    /** 是否处于本区块自身的观察范围（区域已就绪、总开关已开、非回放、区块在捕获区域内）。 */
    public static boolean captureActive(WorldGenLevel level, ChunkPos viewer) {
        return captureActive(level) && containsViewer(viewer);
    }

    /** 报告用：探针自身状态与命中情况。 */
    public static List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("探针模式：" + (orderMode
                ? "第五轮顺序实验模式（只记装饰批号；阶段快照 / 逐阶段 checkpoint / pre-diamond / 逐事件台账全部关闭）"
                : "常规模式（阶段快照 + 逐阶段 checkpoint + pre-diamond + 逐事件台账）"));
        lines.add("探针挂载：ChunkGenerator#applyBiomeDecoration HEAD（装饰开始前）+ "
                + "PlacedFeature#placeWithBiomeCheck HEAD（第一条钻石 feature 前）");
        lines.add("需要捕获的区块（测试区块 ±" + SeedPocConstants.SNAPSHOT_VIEW_RADIUS + "）："
                + REGION.size() + " 个");
        lines.add("已捕获：装饰开始前 " + POST_CARVERS.size() + " 份 / 第一条钻石 feature 前 "
                + PRE_DIAMOND.size() + " 份；未捕获到快照的区块按「缺少输入」如实记录，不做任何代替");
        lines.add("钩子命中次数：applyBiomeDecoration " + featuresHeadEvents
                + " 次 / placeWithBiomeCheck " + placeFeatureEvents + " 次");
        lines.add("已发放装饰批号：" + passSequence + " 个（每个区块每次真实装饰一个号，"
                + "pre-diamond 与 post-diamond 配对前必须同号）");
        lines.add("方块状态编号表：" + PALETTE.size() + " 种（快照按 short 编号存储）");
        lines.add("捕获异常：" + (failures == 0 ? "无" : failures + " 次，首个：" + firstFailure));
        StringBuilder stageLine = new StringBuilder("逐阶段 checkpoint（真实侧，只在测试区块上采）：");
        for (String stage : STAGES) {
            stageLine.append(stage).append(" 命中 ")
                    .append(STAGE_EVENTS.getOrDefault(stage, 0)).append(" 次 / 已存快照 ")
                    .append(countStages(stage)).append(" 份；");
        }
        stageLine.append("pre-diamond（第一条钻石 feature 前）已存 ").append(PRE_DIAMOND.size()).append(" 份");
        lines.add(stageLine.toString());
        return lines;
    }

    /** 某个阶段已存快照份数（报告用）。 */
    private static int countStages(String stage) {
        int count = 0;
        synchronized (STAGE_SNAPSHOTS) {
            for (String key : STAGE_SNAPSHOTS.keySet()) {
                if (key.startsWith(stage + "@")) {
                    count++;
                }
            }
        }
        return count;
    }

    /** 钩子总闸：未开启实验、或正在回放、或区域未就绪时一律不做事。 */
    static boolean captureActive(WorldGenLevel level) {
        return SeedPocFlags.enabled() && regionReady && !replaying && level != null;
    }

    /** 发下一个装饰批号（同一批快照必须共享同一个批号）。 */
    private static int nextPass() {
        synchronized (GenStageCapture.class) {
            return ++passSequence;
        }
    }

    /** 是否钻石四条之一（按注册表实例身份比对）。 */
    static boolean isDiamondFeature(WorldGenLevel level, PlacedFeature feature) {
        Set<PlacedFeature> diamonds = diamondFeatures;
        if (diamonds == null) {
            diamonds = buildDiamondFeatures(level);
            diamondFeatures = diamonds;
        }
        return diamonds.contains(feature);
    }

    private static Set<PlacedFeature> buildDiamondFeatures(WorldGenLevel level) {
        Set<PlacedFeature> result = Collections.newSetFromMap(new IdentityHashMap<>());
        var registry = level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
        for (String id : SeedPocConstants.DIAMOND_PLACED_FEATURES) {
            Holder.Reference<PlacedFeature> holder = registry.getOrThrow(ResourceKey.create(
                    Registries.PLACED_FEATURE, Identifier.withDefaultNamespace(id)));
            result.add(holder.value());
        }
        return result;
    }

    /** 取 placed_feature 的注册表路径（短 id），取不到返回 {@code "?"}。 */
    static String pathOf(WorldGenLevel level, PlacedFeature feature) {
        return level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE)
                .getResourceKey(feature)
                .map(key -> key.identifier().getPath())
                .orElse("?");
    }

    /**
     * 取一份 3x3 阶段快照。
     *
     * <p>读路径与原版装饰过程完全一致：区块用 {@code WorldGenLevel#getChunk}（装饰期合法范围），
     * 方块状态读 section，列高度用 {@code WorldGenLevel#getHeight}（= {@code WorldGenRegion#getHeight}，
     * 见 WorldGenRegion.java:397-399）。</p>
     *
     * <p>第四轮把采集实现抽到 {@link StageSnapshotCapture}——真实侧与离线侧必须<b>共用同一份采集代码</b>，
     * 否则两侧布局一旦有差异，就会被误报成「生成差异」。</p>
     */
    private static GenStageSnapshot capture(WorldGenLevel level, ChunkPos viewer, String stage) {
        int sequence;
        synchronized (GenStageCapture.class) {
            sequence = ++order;
        }
        GenStageSnapshot snapshot = StageSnapshotCapture.capture(level, viewer, stage, PALETTE, sequence);
        LOGGER.info("{}：已捕获阶段快照｜{}", SeedPocConstants.LOG_KEY, snapshot.cn());
        return snapshot;
    }

    /** 捕获异常只登记不上抛：绝不允许实验代码把世界生成搞崩。 */
    private static void recordFailure(String stage, ChunkPos viewer, Throwable error) {
        failures++;
        if (firstFailure.isEmpty()) {
            firstFailure = stage + "@" + viewer.x() + "," + viewer.z() + " → "
                    + error.getClass().getSimpleName() + ": " + error.getMessage();
        }
        LOGGER.warn("{}：阶段快照捕获失败（{} @ {},{}）", SeedPocConstants.LOG_KEY, stage,
                viewer.x(), viewer.z(), error);
    }
}
