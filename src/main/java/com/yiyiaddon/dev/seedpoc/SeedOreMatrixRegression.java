package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.SeedObservationSnapshot;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreDefinition;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.prediction.SeedOrePredictor;
import com.yiyiaddon.seed.render.SeedRenderEntry;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import com.yiyiaddon.seed.worldgen.OfflineWorldgenContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第八阶段（236）· <b>多矿物 · 多维度实机矩阵回归装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>：236 把钻石专用预测器提炼成了按 {@code (维度, 矿物)} 定义表工作的通用引擎，
 * 并新增了 Redstone / Lapis / Gold / Iron / Copper / Coal / Emerald / Ancient Debris /
 * Nether Quartz / Nether Gold 共 10 种矿物与一整套下界维度档案。通用化本身必须被证明
 * <b>没有改变任何已冻结行为</b>，同时新矿物与下界必须各自拿出「候选 ↔ 真实方块」的证据。</p>
 *
 * <p><b>三种证据，逐矿物逐维度取</b>：</p>
 * <ol>
 *     <li><b>Worker ↔ 单人 Oracle 逐项一致</b>：正式产品走隔离进程 Worker（{@code SeedMiningService}
 *         公开 API），Oracle 走同一客户端进程里的集成服务端 {@code ServerLevel}。
 *         两侧比较完整 {@link PredictionResult}（数量 / 逐 BlockPos / 矿物 / 确定性 / 来源 / 写入者）；</li>
 *     <li><b>候选 ↔ 真实方块</b>：把目标区块与 ±1 邻域在真实世界(pipeline 到 FULL)后，
 *         按 {@code SeedOreDefinition#matches} 扫出真实存在的该种矿物。
 *         <b>硬判据</b>：每一条预测坐标在真实世界里都必须是该种矿物（错报必须为 0）。
 *         反向的「真实有而没预测到」如实登记（其它来源：化石里的煤、结构、矿脉等），并计数；</li>
 *     <li><b>观察 / ESP / 缓存隔离读数</b>：同一时刻的观察快照（候选 / 未观察 / 已确认 / 当前缺失）、
 *         渲染快照（按矿物分组的条目数与颜色）与预测缓存（按 {@code 维度 + 矿物 + 目标区块} 分组的键）。
 *         这一层用来证明 236 的三处隔离：Repository 键、观察层键、渲染层颜色。</li>
 * </ol>
 *
 * <p><b>本装置读真实世界的口径</b>：它<b>只是开发验收装置</b>，允许把目标区块推到 FULL
 * （原版 {@code ServerLevel#getChunk}）来取真值 —— 与正式观察层「只读客户端已加载区块、
 * {@code LOAD_OR_GENERATE=false}」是两件事，后者由 233 的观察回归负责，本装置不碰它。</p>
 *
 * <p><b>触发方式</b>：{@code -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.oreMatrix=1
 * -Dyiyiaddon.seedpoc.exit=1}</p>
 */
public final class SeedOreMatrixRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-236-矿物矩阵.txt";

    /** 单次预测的等待上限（客户端刻）。 */
    private static final int WAIT_PREDICT_TICKS = 20 * 240;

    /** 等待世界 / 维度切换的上限（客户端刻）。 */
    private static final int WAIT_WORLD_TICKS = 20 * 90;

    /** 主世界锚点（玩家被传送到的位置；其所在区块 = 覆盖调度的中心）。 */
    private static final BlockPos OVERWORLD_ANCHOR = new BlockPos(8, 120, 8);

    /** 下界锚点（y 取高处在空中，落地比卡在方块里安全）。 */
    private static final BlockPos NETHER_ANCHOR = new BlockPos(8, 100, 8);

    /**
     * 主世界目标区块（<b>两个都在覆盖半径之外</b>）。
     *
     * <p>为什么必须避开覆盖半径：Worker 侧的离线世界是<b>共享会话</b>，覆盖调度会先把它
     * 附近的区块装饰掉，于是同一目标区块的「邻域年龄」与 Oracle 侧的冷启动不同 ——
     * 228 已定案「跨区块写入者的归属依赖合法的执行历史」。要让 Worker 与 Oracle 的可比性成立，
     * 目标区块必须落在覆盖调度够不到的地方，两侧都是冷启动。</p>
     */
    private static final ChunkPos OVERWORLD_FAR_A = new ChunkPos(3, -1);
    private static final ChunkPos OVERWORLD_FAR_B = new ChunkPos(5, 3);

    /** 下界目标区块（同样都在覆盖半径之外）。 */
    private static final ChunkPos NETHER_FAR_A = new ChunkPos(-3, 4);
    private static final ChunkPos NETHER_FAR_B = new ChunkPos(4, -3);

    /**
     * 请求顺序对照用的邻块：<b>必须与目标区块直接相邻</b>。
     *
     * <p>理由：只有相邻区块才是目标区块的 {@code viewer}（写半径 1）。拿一个隔着两个区块的
     * 「邻块」先请求，目标区块的 viewer 集合与执行顺序<b>一点都没变</b>，
     * 那种对照是空的（第一版装置就踩了这个坑）。</p>
     */
    private static final ChunkPos OVERWORLD_ORDER_NEIGHBOUR = new ChunkPos(4, -1);
    private static final ChunkPos NETHER_ORDER_NEIGHBOUR = new ChunkPos(-2, 4);

    /** 真值扫描的邻域半径（{@code FEATURES} 写半径 1：邻区块装饰会写进目标区块）。 */
    private static final int TRUTH_WRITE_RADIUS = 1;

    /**
     * 做「两种合法请求顺序」对照的矿物（口径要求的最低必要集合）。
     *
     * <p>煤也在其中：它是本装置里唯一出现「候选 ↔ 真实」差集的矿物（见逐用例读数），
     * 必须就地判定这个差集是不是「合法请求顺序敏感」造成的。</p>
     */
    private static final List<OreType> ORDER_TEST_ORES =
            List.of(OreType.REDSTONE, OreType.LAPIS, OreType.COAL, OreType.ANCIENT_DEBRIS);

    // ── 237：绿宝石非零样本 / 远古残骸扩样本 ─────────────────────────────────

    /**
     * 绿宝石可生成的生物群系（8 类山地系）。
     *
     * <p>出处：{@code BiomeDefaultFeatures.java:80-82 addExtraEmeralds}，
     * 调用点 {@code OverworldBiomes.java:190}（风袭丘陵系）/ {@code :770}（草甸、樱花树林）/
     * {@code :800}（冻峰、尖峭峰、石峰）/ {@code :847}（雪坡）/ {@code :870}（雪林）。</p>
     */
    private static final Set<ResourceKey<Biome>> EMERALD_BIOMES = Set.of(
            Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST,
            Biomes.MEADOW, Biomes.CHERRY_GROVE, Biomes.GROVE, Biomes.SNOWY_SLOPES,
            Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS);

    /** 绿宝石补证：最多尝试的候选目标区块数（凑够 {@link #EMERALD_REQUIRED_NONZERO} 个非零即停）。 */
    private static final int EMERALD_MAX_CANDIDATES = 16;

    /** 绿宝石补证：要求取得的非零候选目标区块数。 */
    private static final int EMERALD_REQUIRED_NONZERO = 2;

    /** 绿宝石补证：生物群系粗扫的区块步长（先粗后细，避免逐区块全扫）。 */
    private static final int EMERALD_SCAN_STEP = 8;

    /** 绿宝石补证：生物群系粗扫的区块半径（±该值，覆盖 2×半径+1 见方）。 */
    private static final int EMERALD_SCAN_RADIUS_CHUNKS = 512;

    /** 绿宝石补证：判定「这一列是不是山地」的采样高度（多处采样，山地系在垂直方向上是分层的）。 */
    private static final int[] EMERALD_SCAN_YS = {288, 224, 160, 96, 32};

    /** 远古残骸扩样本：最多检查的目标区块数（口径上限 64）。 */
    private static final int DEBRIS_MAX_CANDIDATES = 64;

    /** 远古残骸扩样本：非空目标区块数达标线。 */
    private static final int DEBRIS_REQUIRED_CHUNKS = 8;

    /** 远古残骸扩样本：候选坐标总数达标线。 */
    private static final int DEBRIS_REQUIRED_POSITIONS = 20;

    /** 远古残骸扩样本：扫描窗口的起始区块（与固定用例的远端 A/B 错开，互不干扰）。 */
    private static final ChunkPos DEBRIS_SCAN_ORIGIN = new ChunkPos(16, 16);

    /** 远古残骸扩样本：扫描窗口的边长（区块），= 8 → 8×8 = 64 个目标区块。 */
    private static final int DEBRIS_SCAN_SIDE = 8;

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    /**
     * 237：目标样本补证的类型。
     *
     * <p>为什么需要区分：237 要求给「绿宝石非零样本」与「远古残骸扩样本」各自取得
     * Worker ↔ Oracle / 候选 ↔ 真实 / 观察 / ESP 证据，而这两组样本是<b>先扫描再按需取样</b>的
     * （找到达标数量即停），因此判据与 22 个固定用例不同（固定用例是「每个都必须通过」，
     * 这两组是「凑够规定数量并通过」）。</p>
     */
    private enum CaseKind {
        /** 236 原有的固定矩阵用例（主世界 8×2 + 下界 3×2）。 */
        MATRIX,
        /** 237 绿宝石补证：山地系生物群系里的候选目标区块。 */
        EMERALD_SAMPLE,
        /** 237 远古残骸扩样本：下界连续目标区块。 */
        DEBRIS_SAMPLE
    }

    /** 一个矩阵用例：某一维度、某一矿物、某一目标区块。 */
    private record OreCase(SeedDimensionProfile profile, OreType oreType, ChunkPos chunk, String roleCn,
                           CaseKind kind) {

        private String labelCn() {
            return profile.displayNameCn() + " · " + oreType.displayNameCn() + " · 区块 ("
                    + chunk.x() + "," + chunk.z() + ")[" + roleCn + "]";
        }
    }

    private enum Step {
        /** 等进入夹具世界。 */
        ENTER_WORLD,
        /** 传送到主世界锚点。 */
        OVERWORLD_ANCHOR,
        /** 237：扫描绿宝石山地候选与远古残骸扩样本候选（服务端线程执行一次）。 */
        SCAN_SAMPLES,
        /** 237：传送到绿宝石候选区块（让正式观察层真的能看到那些区块）。 */
        EMERALD_ANCHOR,
        /** 切到下一个用例（矿物集合 / 覆盖半径 / 种子）。 */
        CASE_PREPARE,
        /** 提交 Worker 侧预测并等结果。 */
        CASE_WORKER,
        /** 跑 Oracle 侧预测（后台线程）。 */
        CASE_ORACLE,
        /** 取真实世界真值（服务端线程）。 */
        CASE_TRUTH,
        /** 比较并推进。 */
        CASE_COMPARE,
        /** 传送到下界。 */
        NETHER_ANCHOR,
        /** 等进入下界并核对身份切换。 */
        WAIT_NETHER,
        /** 下界的自动挖矿闸门与有效矿物核对。 */
        NETHER_GATES,
        /** 主世界用例跑完后的缓存 / 观察 / ESP 隔离读数。 */
        SUMMARY_OVERWORLD,
        /** 下界用例跑完后的缓存 / 观察 / ESP 隔离读数。 */
        SUMMARY_NETHER,
        /** 三种矿物的两种合法请求顺序对照（服务端线程）。 */
        ORDER_TEST,
        FINISHED
    }

    private static Step step = Step.ENTER_WORLD;
    private static int waitTicks;
    private static boolean worldRequested;

    private static IntegratedServer server;
    private static final Map<SeedDimensionProfile, SeedOrePredictor> ORACLE = new LinkedHashMap<>();

    private static List<OreCase> cases = List.of();
    private static int caseIndex;
    private static int switchIndex = -1;
    private static boolean switchedToNether;

    // ── 单用例的中间状态 ──
    private static boolean workerSubmitted;
    private static PredictionResult workerResult;
    private static boolean oracleDispatched;
    private static volatile boolean oracleDone;
    private static PredictionResult oracleResult;
    private static boolean truthDispatched;
    private static volatile boolean truthDone;
    private static Set<BlockPos> truthSet = Set.of();

    /**
     * 真实世界里「预测坐标」那一格实际是什么方块（错报归因用）。
     *
     * <p>只有坐标差集不够定位问题：错报可能是「真实世界这里是空气 / 其它方块」，也可能是
     * 「真实世界这里是同类矿但被算进了别的来源」。因此真值扫描时顺手把每个<b>预测坐标</b>
     * 上的真实方块短 id 记下来，报告里直接逐条打印。</p>
     */
    private static volatile Map<BlockPos, String> realAtPredicted = Map.of();

    // ── 报告与判定 ──
    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();
    private static final Map<String, String> OUTCOME_BY_CASE = new LinkedHashMap<>();

    /** 每种矿物是否做到「候选全部能在真实世界里找到」（1 = 是）。 */
    private static final Map<OreType, Integer> FULLY_MATCHED_ORES = new LinkedHashMap<>();

    /** 全矩阵的错报 / 漏报合计（汇总章节用）。 */
    private static int EXTRA_TOTAL;
    private static int MISSED_TOTAL;

    /** 引擎自查标记为调度敏感的错报格数（合计）。 */
    private static int SENSITIVE_EXTRA_TOTAL;

    /** 用例键 → 该用例里「未被引擎自查标记为调度敏感」的错报格数（汇总判定用）。 */
    private static final Map<String, Integer> UNDIAGNOSED_EXTRA_BY_CASE = new LinkedHashMap<>();

    /** 用例键 → 显示名（报告可读性）。 */
    private static final Map<String, String> CASE_DISPLAY = new LinkedHashMap<>();

    /** 第七节证明「本是合法顺序敏感」的（矿物, 维度, 区块）。 */
    private static final Set<String> ORDER_SENSITIVE_CASES = new LinkedHashSet<>();

    /** 两个维度各自「目标区块 → 该区块上并存了哪些矿物」（维度隔离与键隔离的原始读数）。 */
    private static final Map<ChunkPos, Set<OreType>> OVERWORLD_BY_CHUNK = new LinkedHashMap<>();
    private static final Map<ChunkPos, Set<OreType>> NETHER_BY_CHUNK = new LinkedHashMap<>();

    // ── 237：样本补证的扫描结果与达标计数 ──

    /** 绿宝石候选目标区块（粗扫 + 邻域细化得到的山地系区块），最多 {@link #EMERALD_MAX_CANDIDATES} 个。 */
    private static final List<ChunkPos> EMERALD_CANDIDATES = new ArrayList<>();

    /** 绿宝石候选区块对应的生物群系名（报告用，与候选一一对应）。 */
    private static final Map<ChunkPos, String> EMERALD_BIOME_BY_CHUNK = new LinkedHashMap<>();

    /** 绿宝石第一个候选区块上的安全落点（把玩家传送过去，让正式观察层看到这一片）。 */
    private static volatile BlockPos emeraldAnchor;

    /** 远古残骸扩样本的目标区块（固定窗口，最多 {@link #DEBRIS_MAX_CANDIDATES} 个）。 */
    private static final List<ChunkPos> DEBRIS_CANDIDATES = new ArrayList<>();

    private static volatile boolean sampleScanDispatched;
    private static volatile boolean sampleScanDone;

    /** 用例清单里的分段边界（早期停止与「该不该切维度」都靠它）。 */
    private static int emeraldBlockStart = -1;
    private static int debrisBlockStart = -1;

    /** 绿宝石补证已取得的非零候选区块数。 */
    private static int emeraldNonZeroChunks;

    /** 远古残骸扩样本已取得的非空目标区块数 / 候选坐标总数。 */
    private static int debrisNonEmptyChunks;
    private static int debrisPositions;

    private static boolean emeraldObserved;

    private static String overworldIdentity = "（未记录）";
    private static String netherIdentity = "（未记录）";
    private static boolean reportWritten;

    private SeedOreMatrixRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在 236 矩阵模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (step) {
                case ENTER_WORLD -> tickEnterWorld(client);
                case OVERWORLD_ANCHOR -> tickOverworldAnchor(client);
                case SCAN_SAMPLES -> tickScanSamples(client);
                case EMERALD_ANCHOR -> tickEmeraldAnchor(client);
                case CASE_PREPARE -> tickCasePrepare(client);
                case CASE_WORKER -> tickCaseWorker(client);
                case CASE_ORACLE -> tickCaseOracle(client);
                case CASE_TRUTH -> tickCaseTruth(client);
                case CASE_COMPARE -> tickCaseCompare(client);
                case NETHER_ANCHOR -> tickNetherAnchor(client);
                case WAIT_NETHER -> tickWaitNether(client);
                case NETHER_GATES -> tickNetherGates(client);
                case SUMMARY_OVERWORLD -> tickSummary(client, SeedDimensionProfile.OVERWORLD);
                case SUMMARY_NETHER -> tickSummary(client, SeedDimensionProfile.NETHER);
                case ORDER_TEST -> tickOrderTest(client);
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            LOGGER.error("{}：236 矩阵装置中断", SeedPocConstants.LOG_KEY, error);
            report("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            VERDICTS.add("【判定】装置异常中断：不通过");
            finish();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 世界准备
    // ────────────────────────────────────────────────────────────────────────

    private static void tickEnterWorld(Minecraft client) {
        if (client.level == null || client.player == null) {
            boolean atTitle = client.screen == null
                    || client.screen instanceof net.minecraft.client.gui.screens.TitleScreen;
            if (!worldRequested && SeedPocFlags.autoCreateWorld() && atTitle) {
                worldRequested = true;
                SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.oreMatrixSeed());
            }
            return;
        }
        server = client.getSingleplayerServer();
        if (server == null) {
            report("**未进入单人世界：Oracle 侧需要集成服务端，本装置不成立**");
            VERDICTS.add("【判定】环境：不通过（未进入单人夹具世界）");
            finish();
            return;
        }
        if (server.getLevel(Level.NETHER) == null) {
            report("**集成服务端没有下界那一层：本装置需要原版普通世界预设（含 the_nether）**");
            VERDICTS.add("【判定】环境：不通过（下界不可用）");
            finish();
            return;
        }
        report("零、口径与环境");
        report("  夹具世界：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        report("  被试种子：" + SeedPocFlags.oreMatrixSeed() + "（与世界种子相同，因此可与真实区块逐个对照）");
        report("  Worker 宿主：本机隔离进程（服务层自动拉起，232 冻结）");
        report("  Oracle 宿主：集成服务端主世界 / 下界两层 ServerLevel");
        report("  真值来源：把目标区块与 ±" + TRUTH_WRITE_RADIUS + " 邻域经原版 ServerLevel#getChunk 推到 FULL 后逐格扫描");
        report("  受支持维度：" + SeedDimensionProfile.describeAllCn());
        report("  主世界矿物：" + SeedOreRegistry.describeSupportedOresCn(SeedDimensionProfile.OVERWORLD));
        report("  下界矿物：" + SeedOreRegistry.describeSupportedOresCn(SeedDimensionProfile.NETHER));
        step = Step.OVERWORLD_ANCHOR;
        waitTicks = 0;
    }

    private static void tickOverworldAnchor(Minecraft client) {
        if (waitTicks == 0) {
            if (!sendCommand(client, "execute in minecraft:overworld run tp @s "
                    + OVERWORLD_ANCHOR.getX() + " " + OVERWORLD_ANCHOR.getY() + " "
                    + OVERWORLD_ANCHOR.getZ())) {
                report("**主世界锚点传送指令发送失败（单人夹具世界应自带指令权限）**");
                VERDICTS.add("【判定】环境：不通过（无法传送）");
                finish();
                return;
            }
        }
        if (++waitTicks > WAIT_WORLD_TICKS) {
            report("**等待主世界锚点就绪超时**");
            VERDICTS.add("【判定】环境：不通过（主世界锚点超时）");
            finish();
            return;
        }
        if (!inDimension(client, SeedDimensionProfile.OVERWORLD)) {
            return;
        }
        SeedMiningService service = SERVICE;
        service.setEnabled(true);
        service.setRenderPrediction(true);
        service.setSeedText(String.valueOf(SeedPocFlags.oreMatrixSeed()));
        // 覆盖半径压到最小：本装置只关心「手工预测那一次」的结果，覆盖队列越短越好，
        // 免得 49 个区块的排队把每个用例拖到分钟级。
        service.setCoverageRadius(SeedMiningService.coverageRadiusMin());
        // 237：先扫描「绿宝石山地候选」与「远古残骸扩样本」两组样本，再据此拼用例清单
        step = Step.SCAN_SAMPLES;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 237：样本扫描（绿宝石山地候选 / 远古残骸扩样本）与服务端线程执行体
    // ────────────────────────────────────────────────────────────────────────

    /** 扫描阶段：把两份扫描都投递到服务端线程执行一次（离线 worldgen 上下文与真实高度图都在那一侧）。 */
    private static void tickScanSamples(Minecraft client) {
        if (!sampleScanDispatched) {
            sampleScanDispatched = true;
            waitTicks = 0;
            server.execute(SeedOreMatrixRegression::scanSamples);
            return;
        }
        if (!sampleScanDone) {
            if (++waitTicks > WAIT_WORLD_TICKS) {
                report("**等待样本扫描超时**");
                VERDICTS.add("【判定】237 样本扫描：不通过（超时）");
            }
            return;
        }
        buildCases();
        step = Step.CASE_PREPARE;
        waitTicks = 0;
    }

    /**
     * 两组样本的扫描（服务端线程）。
     *
     * <p><b>绿宝石</b>：用真实 26.1.2 的生物群系源（{@code OfflineWorldgenContext} 里那份
     * 种子派生的 {@code RandomState} + 原版 {@code MultiNoiseBiomeSource}）在固定种子上粗扫
     * 「有没有山地系生物群系」，命中后只在命中点邻域细化，得到有限个候选目标区块。
     * 这一步刻意<b>只发生物群系、不生成区块</b>（{@code getNoiseBiome} 是纯函数），
     * 因此扫描本身不产生任何方块、也不碰真实世界。</p>
     *
     * <p><b>远古残骸</b>：下界的扩样本不需要挑生物群系（远古残骸在所有下界生物群系都生成），
     * 因此直接取一块与固定用例错开的连续窗口（8×8 = 64 个目标区块），由用例循环按需取用、
     * 达标即停。</p>
     */
    private static void scanSamples() {
        try {
            long seed = SeedPocFlags.oreMatrixSeed();
            ServerLevel overworld = server.overworld();
            OfflineWorldgenContext context = OfflineWorldgenContext.create(overworld, seed);
            Climate.Sampler sampler = context.randomState().sampler();

            report("");
            report("一之一、237 样本扫描（绿宝石山地候选 / 远古残骸扩样本）");
            report("  绿宝石候选扫描：生物群系源 = 26.1.2 原版 MultiNoiseBiomeSource（主世界预设），"
                    + "种子 " + seed + "；粗扫步长 " + EMERALD_SCAN_STEP + " 区块、半径 ±"
                    + EMERALD_SCAN_RADIUS_CHUNKS + " 区块，采样高度 " + java.util.Arrays.toString(EMERALD_SCAN_YS));
            int coarseSamples = 0;
            List<ChunkPos> coarseHits = new ArrayList<>();
            outer:
            for (int cx = -EMERALD_SCAN_RADIUS_CHUNKS; cx <= EMERALD_SCAN_RADIUS_CHUNKS; cx += EMERALD_SCAN_STEP) {
                for (int cz = -EMERALD_SCAN_RADIUS_CHUNKS; cz <= EMERALD_SCAN_RADIUS_CHUNKS;
                     cz += EMERALD_SCAN_STEP) {
                    coarseSamples++;
                    String biome = classifyEmeraldColumn(context, sampler, cx, cz);
                    if (biome != null) {
                        coarseHits.add(new ChunkPos(cx, cz));
                        if (coarseHits.size() >= EMERALD_MAX_CANDIDATES) {
                            break outer;
                        }
                    }
                }
            }
            report("    粗扫取样 " + coarseSamples + " 列 → 命中 " + coarseHits.size() + " 列");
            refineEmeraldCandidates(context, sampler, coarseHits);
            report("    细化后候选目标区块 " + EMERALD_CANDIDATES.size() + " 个"
                    + (EMERALD_CANDIDATES.isEmpty() ? "（本种子粗扫范围内没有山地系生物群系）" : "："));
            for (ChunkPos chunk : EMERALD_CANDIDATES) {
                report("      (" + chunk.x() + "," + chunk.z() + ") 生物群系 " + EMERALD_BIOME_BY_CHUNK.get(chunk));
            }
            // 传送落点：只取区块中心的高空落点。为什么不用真实高度图：山地列的高度图读数在这种
            // 远景列上并不可靠（装置第一版据此算出 -61，落点直接掉进虚空），而落点的高度对
            // 「客户端加载了这一片区块」这件事没有影响 —— 区块加载只看 x/z。配合下面的旁观模式，
            // 高空落点既不会摔死也不会掉出世界。
            if (!EMERALD_CANDIDATES.isEmpty()) {
                ChunkPos first = EMERALD_CANDIDATES.get(0);
                emeraldAnchor = new BlockPos(first.getMiddleBlockX(), 200, first.getMiddleBlockZ());
                report("    传送落点（让正式观察层真的加载这些区块）：(" + emeraldAnchor.getX() + ","
                        + emeraldAnchor.getY() + "," + emeraldAnchor.getZ() + ")，落地方式：先旁观再传送");
            }
            report("");
            report("  远古残骸扩样本：窗口起点区块 (" + DEBRIS_SCAN_ORIGIN.x() + "," + DEBRIS_SCAN_ORIGIN.z()
                    + ")，" + DEBRIS_SCAN_SIDE + "×" + DEBRIS_SCAN_SIDE + " 共 "
                    + (DEBRIS_SCAN_SIDE * DEBRIS_SCAN_SIDE) + " 个目标区块（上限 "
                    + DEBRIS_MAX_CANDIDATES + "，达标即停）");
            for (int dx = 0; dx < DEBRIS_SCAN_SIDE && DEBRIS_CANDIDATES.size() < DEBRIS_MAX_CANDIDATES; dx++) {
                for (int dz = 0; dz < DEBRIS_SCAN_SIDE && DEBRIS_CANDIDATES.size() < DEBRIS_MAX_CANDIDATES; dz++) {
                    DEBRIS_CANDIDATES.add(new ChunkPos(DEBRIS_SCAN_ORIGIN.x() + dx, DEBRIS_SCAN_ORIGIN.z() + dz));
                }
            }
            report("    目标区块清单：" + describeChunks(DEBRIS_CANDIDATES));
        } catch (Throwable error) {
            LOGGER.error("{}：237 样本扫描异常", SeedPocConstants.LOG_KEY, error);
            report("**237 样本扫描异常：" + error.getClass().getSimpleName() + " / " + error.getMessage() + "**");
            VERDICTS.add("【判定】237 样本扫描：不通过（异常 " + error.getClass().getSimpleName() + "）");
        } finally {
            sampleScanDone = true;
        }
    }

    /** 某一区块列是不是山地系生物群系；是则返回第一个命中的生物群系 id，否则 null。 */
    private static String classifyEmeraldColumn(OfflineWorldgenContext context, Climate.Sampler sampler,
                                                int chunkX, int chunkZ) {
        int blockX = chunkX * 16 + 8;
        int blockZ = chunkZ * 16 + 8;
        for (int y : EMERALD_SCAN_YS) {
            Holder<Biome> holder = context.biomeSource().getNoiseBiome(
                    blockX >> 2, y >> 2, blockZ >> 2, sampler);
            ResourceKey<Biome> key = holder.unwrapKey().orElse(null);
            if (key != null && EMERALD_BIOMES.contains(key)) {
                return key.identifier().toString();
            }
        }
        return null;
    }

    /** 粗扫命中点邻域细化：在命中点 ±{@link #EMERALD_SCAN_STEP} 区块内逐区块判定，凑够候选数即停。 */
    private static void refineEmeraldCandidates(OfflineWorldgenContext context, Climate.Sampler sampler,
                                                List<ChunkPos> coarseHits) {
        Set<ChunkPos> seen = new LinkedHashSet<>();
        for (ChunkPos hit : coarseHits) {
            for (int dx = -EMERALD_SCAN_STEP; dx <= EMERALD_SCAN_STEP; dx++) {
                for (int dz = -EMERALD_SCAN_STEP; dz <= EMERALD_SCAN_STEP; dz++) {
                    ChunkPos candidate = new ChunkPos(hit.x() + dx, hit.z() + dz);
                    if (!seen.add(candidate)) {
                        continue;
                    }
                    String biome = classifyEmeraldColumn(context, sampler, candidate.x(), candidate.z());
                    if (biome != null) {
                        EMERALD_CANDIDATES.add(candidate);
                        EMERALD_BIOME_BY_CHUNK.put(candidate, biome);
                        if (EMERALD_CANDIDATES.size() >= EMERALD_MAX_CANDIDATES) {
                            return;
                        }
                    }
                }
            }
        }
    }

    /** 拼用例清单（237：在固定矩阵里插入绿宝石补证段与远古残骸扩样本段）。 */
    private static void buildCases() {
        List<OreCase> built = new ArrayList<>();
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.OVERWORLD)) {
            built.add(new OreCase(SeedDimensionProfile.OVERWORLD, oreType, OVERWORLD_FAR_A, "远端 A",
                    CaseKind.MATRIX));
            built.add(new OreCase(SeedDimensionProfile.OVERWORLD, oreType, OVERWORLD_FAR_B, "远端 B",
                    CaseKind.MATRIX));
        }
        emeraldBlockStart = built.size();
        for (ChunkPos chunk : EMERALD_CANDIDATES) {
            built.add(new OreCase(SeedDimensionProfile.OVERWORLD, OreType.EMERALD, chunk, "山地样本补证",
                    CaseKind.EMERALD_SAMPLE));
        }
        switchIndex = built.size();
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            built.add(new OreCase(SeedDimensionProfile.NETHER, oreType, NETHER_FAR_A, "远端 A", CaseKind.MATRIX));
            built.add(new OreCase(SeedDimensionProfile.NETHER, oreType, NETHER_FAR_B, "远端 B", CaseKind.MATRIX));
        }
        debrisBlockStart = built.size();
        for (ChunkPos chunk : DEBRIS_CANDIDATES) {
            built.add(new OreCase(SeedDimensionProfile.NETHER, OreType.ANCIENT_DEBRIS, chunk, "扩样本",
                    CaseKind.DEBRIS_SAMPLE));
        }
        cases = List.copyOf(built);
        report("");
        report("一、矩阵规模：主世界 " + SeedOreRegistry.oresOf(SeedDimensionProfile.OVERWORLD).size()
                + " 种 × 2 区块 = " + emeraldBlockStart + " 个用例；绿宝石补证 "
                + EMERALD_CANDIDATES.size() + " 个用例（凑够 " + EMERALD_REQUIRED_NONZERO + " 个非零即停）；下界 "
                + SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER).size() + " 种 × 2 区块 = "
                + (debrisBlockStart - switchIndex) + " 个用例；远古残骸扩样本上限 "
                + DEBRIS_CANDIDATES.size() + " 个用例（凑够 " + DEBRIS_REQUIRED_CHUNKS + " 个非空或 "
                + DEBRIS_REQUIRED_POSITIONS + " 个候选即停）；合计 " + cases.size());
        report("");
        report("二、逐用例证据（Worker ↔ Oracle 逐项 + 候选 ↔ 真实方块 + 观察 / ESP / 缓存隔离）");
    }

    /** 区块清单的紧凑文本（报告用）。 */
    private static String describeChunks(List<ChunkPos> chunks) {
        StringBuilder builder = new StringBuilder();
        for (ChunkPos chunk : chunks) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(chunk.x()).append(',').append(chunk.z()).append(')');
            if (builder.length() > 320) {
                builder.append(" …共 ").append(chunks.size()).append(" 个");
                break;
            }
        }
        return builder.length() == 0 ? "（无）" : builder.toString();
    }

    /**
     * 把玩家送到绿宝石候选区块并取一次观察 / ESP 读数。
     *
     * <p><b>为什么单独一步、而且要放在所有预测用例之后</b>：绿宝石的判据里有一条「Observation」——
     * 正式观察层只读<b>客户端真实已加载</b>的区块（{@code LOAD_OR_GENERATE=false}），
     * 玩家不站在山地那一片，观察层对绿宝石就永远只有「未观察」。所以必须真的把客户端送过去。
     * 但传送会让覆盖调度把那一圈区块先预测掉（会话变暖），若放在绿宝石预测用例<b>之前</b>，
     * Worker 侧就是暖、Oracle 侧是冷，{@code originViewer} 必然对不上 —— 装置的 Worker ↔ Oracle
     * 可比性优先，因此传送与观察读数放在两组预测证据都取完之后。</p>
     *
     * <p>落点由服务端真实高度图给出（地表上方 3 格），避免传送进山体、也避免高空坠落。</p>
     */
    private static void tickEmeraldAnchor(Minecraft client) {
        if (waitTicks == 0) {
            BlockPos anchor = emeraldAnchor;
            // 先切旁观再传送：落点在山地高空（区块加载只看 x/z，高度取多少都不影响取证），
            // 生存模式下从高空落下会摔死并重生回出生点 —— 装置第一版实测就是这个现象，
            // 表现为「等了 90 秒也没站到目标区块」。
            sendCommand(client, "gamemode spectator");
            if (anchor == null || !sendCommand(client, "execute in minecraft:overworld run tp @s "
                    + anchor.getX() + " " + anchor.getY() + " " + anchor.getZ())) {
                report("**绿宝石候选传送指令发送失败（观察层证据降级为未加载读数）**");
                VERDICTS.add("【判定】绿宝石补证 · 观察层：传送失败（未取证）");
                emeraldObserved = true;
                step = Step.CASE_PREPARE;
                return;
            }
        }
        if (++waitTicks > WAIT_WORLD_TICKS) {
            report("**等待绿宝石候选区块加载超时（观察层证据降级为未加载读数）**");
            VERDICTS.add("【判定】绿宝石补证 · 观察层：不通过（加载超时）");
            sendCommand(client, "gamemode survival");
            emeraldObserved = true;
            step = Step.CASE_PREPARE;
            return;
        }
        if (!inDimension(client, SeedDimensionProfile.OVERWORLD)) {
            return;
        }
        if (client.player == null || client.level == null) {
            return;
        }
        ChunkPos first = EMERALD_CANDIDATES.get(0);
        int playerChunkX = client.player.blockPosition().getX() >> 4;
        int playerChunkZ = client.player.blockPosition().getZ() >> 4;
        if (playerChunkX != first.x() || playerChunkZ != first.z()) {
            return;
        }
        // 覆盖调度会跟着玩家走：给它一点时间把这一圈的绿宝石铺开并观察
        if (waitTicks < 20 * 20) {
            return;
        }
        int entries = 0;
        for (ChunkPos chunk : EMERALD_CANDIDATES) {
            entries += renderEntries(chunk, OreType.EMERALD);
        }
        SeedObservationSnapshot observed = SERVICE.observationSnapshot();
        report("");
        report("三之一、绿宝石补证的观察层 / ESP 证据（客户端已真的加载山地候选区块）");
        report("  运行时身份：" + SERVICE.runtimeIdentityCn());
        report("  观察快照：候选 " + observed.candidates() + " / 已确认 " + observed.confirmed()
                + " / 当前缺失 " + observed.missing() + " / 未观察 " + observed.unobserved());
        report("  山地候选区块上的 ESP 条目合计：" + entries);
        boolean observedOk = observed.candidates() > 0 && observed.confirmed() > 0;
        VERDICTS.add("【判定】绿宝石补证 · 观察层（候选 " + observed.candidates() + " / 已确认 "
                + observed.confirmed() + "）+ ESP 条目 " + entries + "：" + verdict(observedOk));
        // 还原生存模式：后面的下界用例与 235 的判据都以「普通玩家」为前提，不留下模式副作用
        sendCommand(client, "gamemode survival");
        emeraldObserved = true;
        step = Step.CASE_PREPARE;
        waitTicks = 0;
    }

    /** 某矿物在指定目标区块上的 ESP 条目数。 */
    private static int renderEntries(ChunkPos chunk, OreType oreType) {
        int counter = 0;
        for (SeedRenderEntry entry : SERVICE.renderSnapshot().entries()) {
            if (entry.oreType() == oreType
                    && entry.position().getX() >> 4 == chunk.x()
                    && entry.position().getZ() >> 4 == chunk.z()) {
                counter++;
            }
        }
        return counter;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 单用例：准备 → Worker → Oracle → 真值 → 比较
    // ────────────────────────────────────────────────────────────────────────

    private static void tickCasePrepare(Minecraft client) {
        if (caseIndex >= cases.size()) {
            step = Step.SUMMARY_NETHER;
            waitTicks = 0;
            return;
        }
        // 跨维度切换：主世界用例跑完 → 先取主世界的隔离读数，再去下界
        if (caseIndex == switchIndex && !switchedToNether) {
            // 237：主世界用例（含绿宝石补证）全部跑完后，才把玩家送到绿宝石候选区块，
            // 让正式观察层真的加载那一片并取一次观察 / ESP 读数。
            // 刻意放在所有预测用例<b>之后</b>：传送 + 覆盖会改变会话的冷热历史，
            // 若在用例之前传送，绿宝石那一条就会变成「Worker 侧暖、Oracle 侧冷」，
            // Worker ↔ Oracle 的 originViewer 对不上（装置第一版实测就是这个现象）。
            if (!emeraldObserved && emeraldNonZeroChunks > 0) {
                step = Step.EMERALD_ANCHOR;
                waitTicks = 0;
                return;
            }
            step = Step.SUMMARY_OVERWORLD;
            waitTicks = 0;
            return;
        }
        // 237：两组样本的早期停止（凑够要求数量就不再白跑后续区块）
        if (caseIndex >= emeraldBlockStart && caseIndex < switchIndex
                && emeraldNonZeroChunks >= EMERALD_REQUIRED_NONZERO) {
            report("  绿宝石补证已凑够 " + emeraldNonZeroChunks + " 个非零候选区块（要求 "
                    + EMERALD_REQUIRED_NONZERO + "）→ 停止扫描剩余候选");
            caseIndex = switchIndex;
            waitTicks = 0;
            return;
        }
        if (caseIndex >= debrisBlockStart
                && (debrisNonEmptyChunks >= DEBRIS_REQUIRED_CHUNKS
                || debrisPositions >= DEBRIS_REQUIRED_POSITIONS)) {
            report("  远古残骸扩样本已达标（非空区块 " + debrisNonEmptyChunks + " / 候选坐标 "
                    + debrisPositions + "）→ 停止扫描剩余目标区块");
            caseIndex = cases.size();
            waitTicks = 0;
            return;
        }
        OreCase current = cases.get(caseIndex);
        if (!inDimension(client, current.profile())) {
            // 维度尚未切换完：原地等（正常情况下不会走到这里）
            if (++waitTicks > WAIT_WORLD_TICKS) {
                report("**等待进入 " + current.profile().displayNameCn() + " 超时**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（维度未就绪）");
                caseIndex++;
                waitTicks = 0;
            }
            return;
        }
        String previousSeed = SERVICE.seedText();
        if (!String.valueOf(SeedPocFlags.oreMatrixSeed()).equals(previousSeed.trim())) {
            SERVICE.setSeedText(String.valueOf(SeedPocFlags.oreMatrixSeed()));
        }
        if (!SERVICE.enabled()) {
            SERVICE.setEnabled(true);
        }
        if (!SERVICE.renderPrediction()) {
            SERVICE.setRenderPrediction(true);
        }
        setOnlyOre(current.oreType());
        List<OreType> effective = SERVICE.effectiveOres();
        if (effective.size() != 1 || effective.get(0) != current.oreType()) {
            report("  **" + current.labelCn() + "：矿物集合未生效（当前 " + SeedMiningService.describeOresCn(effective)
                    + "）**");
            VERDICTS.add("【判定】" + current.labelCn() + "：不通过（矿物集合未生效）");
            caseIndex++;
            return;
        }
        workerSubmitted = false;
        oracleDispatched = false;
        oracleDone = false;
        oracleResult = null;
        truthDispatched = false;
        truthDone = false;
        truthSet = Set.of();
        realAtPredicted = Map.of();
        step = Step.CASE_WORKER;
        waitTicks = 0;
    }

    private static void tickCaseWorker(Minecraft client) {
        OreCase current = cases.get(caseIndex);
        if (!workerSubmitted) {
            workerSubmitted = true;
            waitTicks = 0;
            SERVICE.predictChunk(current.chunk().x(), current.chunk().z());
            if (!SERVICE.predicting()) {
                report("  **" + current.labelCn() + "：Worker 侧未能启动预测（状态 " + SERVICE.stateCn() + "）**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Worker 未启动）");
                advanceCase();
            }
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_PREDICT_TICKS) {
                report("  **" + current.labelCn() + "：等待 Worker 预测超时**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Worker 超时）");
                advanceCase();
            }
            return;
        }
        workerResult = SERVICE.lastResult();
        if (workerResult == null) {
            report("  **" + current.labelCn() + "：Worker 侧结果为空**");
            VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Worker 结果为空）");
            advanceCase();
            return;
        }
        if (workerResult.failed()) {
            report("  **" + current.labelCn() + "：Worker 预测失败 → " + workerResult.failureReason() + "**");
            VERDICTS.add("【判定】" + current.labelCn() + "：不通过（预测失败：" + workerResult.failureReason() + "）");
            advanceCase();
            return;
        }
        if (workerResult.stats().hostChunkSourceQueries() != 0) {
            report("  **" + current.labelCn() + "：宿主 ChunkMap 查询 "
                    + workerResult.stats().hostChunkSourceQueries() + " ≠ 0（架构回归）**");
            VERDICTS.add("【判定】" + current.labelCn() + "：不通过（宿主查询不为 0）");
            advanceCase();
            return;
        }
        report("");
        report("  用例 " + (caseIndex + 1) + "/" + cases.size() + "：" + current.labelCn());
        report("    Worker（服务层公开 API）：候选 " + workerResult.count()
                + "（敏感 " + workerResult.scheduleSensitiveCount()
                + " / 未解析 " + workerResult.unresolvedCount()
                + " / 确定 " + workerResult.deterministicCount() + "），耗时 "
                + workerResult.elapsedMillis() + " ms，缓存 " + workerResult.stats().heldChunks()
                + "，宿主 ChunkMap 查询 0，校准 " + workerResult.stats().foreignWriterViewers()
                + " 个外来写入者");
        SeedObservationSnapshot observed = SERVICE.observationSnapshot();
        report("    观察快照此刻：候选 " + observed.candidates() + " / 已确认 " + observed.confirmed()
                + " / 当前缺失 " + observed.missing() + " / 未观察 " + observed.unobserved());
        step = Step.CASE_ORACLE;
        waitTicks = 0;
    }

    private static void tickCaseOracle(Minecraft client) {
        OreCase current = cases.get(caseIndex);
        if (!oracleDispatched) {
            oracleDispatched = true;
            waitTicks = 0;
            SeedDimensionProfile profile = current.profile();
            SeedOrePredictor predictor;
            try {
                predictor = ORACLE.get(profile);
                if (predictor == null) {
                    predictor = new SeedOrePredictor(server.getLevel(profile.levelKey()));
                    ORACLE.put(profile, predictor);
                }
            } catch (Throwable error) {
                report("  **" + current.labelCn() + "：Oracle 预测器构造失败 → "
                        + error.getClass().getSimpleName() + " / " + error.getMessage() + "**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Oracle 预测器构造失败）");
                advanceCase();
                return;
            }
            SeedOrePredictor target = predictor;
            // 与 232 Worker 对照同一做法：离线 worldgen 较重，放后台线程，不占服务端线程
            Thread runner = new Thread(() -> {
                try {
                    oracleResult = target.predict(SeedPocFlags.oreMatrixSeed(), current.oreType(),
                            current.chunk());
                } catch (Throwable error) {
                    LOGGER.error("{}：Oracle 预测异常", SeedPocConstants.LOG_KEY, error);
                    oracleResult = null;
                } finally {
                    oracleDone = true;
                }
            }, "yiyiaddon-seedpoc-236-oracle");
            runner.setDaemon(true);
            runner.start();
            return;
        }
        if (!oracleDone) {
            if (++waitTicks > WAIT_PREDICT_TICKS) {
                report("  **" + current.labelCn() + "：等待 Oracle 预测超时**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Oracle 超时）");
                advanceCase();
            }
            return;
        }
        if (oracleResult == null || oracleResult.failed()) {
            report("  **" + current.labelCn() + "：Oracle 预测不成立 → "
                    + (oracleResult == null ? "异常" : oracleResult.failureReason()) + "**");
            VERDICTS.add("【判定】" + current.labelCn() + "：不通过（Oracle 预测不成立）");
            advanceCase();
            return;
        }
        report("    Oracle（集成服务端 " + current.profile().displayNameCn() + "）：候选 "
                + oracleResult.count() + "，耗时 " + oracleResult.elapsedMillis() + " ms，缓存 "
                + oracleResult.stats().heldChunks() + "，宿主 ChunkMap 查询 "
                + oracleResult.stats().hostChunkSourceQueries());
        step = Step.CASE_TRUTH;
        waitTicks = 0;
    }

    private static void tickCaseTruth(Minecraft client) {
        OreCase current = cases.get(caseIndex);
        if (!truthDispatched) {
            truthDispatched = true;
            waitTicks = 0;
            ServerLevel level = server.getLevel(current.profile().levelKey());
            SeedOreDefinition definition = SeedOreRegistry.of(current.profile(), current.oreType());
            Set<BlockPos> predicted = positions(workerResult);
            server.execute(() -> {
                try {
                    truthSet = scanRealWorld(level, current.chunk(), definition, current.profile());
                    Map<BlockPos, String> actual = new LinkedHashMap<>();
                    LevelChunk real = level.getChunk(current.chunk().x(), current.chunk().z());
                    for (BlockPos pos : predicted) {
                        actual.put(pos, blockIdAt(real, pos, current.profile()));
                    }
                    realAtPredicted = actual;
                } catch (Throwable error) {
                    LOGGER.error("{}：真值扫描异常", SeedPocConstants.LOG_KEY, error);
                    truthSet = Set.of();
                    realAtPredicted = Map.of();
                } finally {
                    truthDone = true;
                }
            });
            return;
        }
        if (!truthDone) {
            if (++waitTicks > WAIT_PREDICT_TICKS) {
                report("  **" + current.labelCn() + "：等待真值扫描超时**");
                VERDICTS.add("【判定】" + current.labelCn() + "：不通过（真值超时）");
                advanceCase();
            }
            return;
        }
        step = Step.CASE_COMPARE;
    }

    private static void tickCaseCompare(Minecraft client) {
        OreCase current = cases.get(caseIndex);
        List<String> mismatches = compare(workerResult, oracleResult);
        boolean parity = mismatches.isEmpty();

        Set<BlockPos> predicted = new LinkedHashSet<>();
        for (PredictedOre ore : workerResult.ores()) {
            predicted.add(ore.position());
        }
        Set<BlockPos> extra = new LinkedHashSet<>(predicted);
        extra.removeAll(truthSet);
        Set<BlockPos> missed = new LinkedHashSet<>(truthSet);
        missed.removeAll(predicted);
        Map<BlockPos, PredictionCertainty> certaintyByPos = new LinkedHashMap<>();
        for (PredictedOre ore : workerResult.ores()) {
            certaintyByPos.put(ore.position(), ore.certainty());
        }
        int sensitiveExtra = 0;
        int undiagnosedExtra = 0;
        for (BlockPos pos : extra) {
            if (certaintyByPos.get(pos) == PredictionCertainty.SCHEDULE_SENSITIVE) {
                sensitiveExtra++;
            } else {
                undiagnosedExtra++;
            }
        }

        report("    真值（真实世界该窗口内的 " + current.oreType().displayNameCn() + "）："
                + truthSet.size() + " 格；预测命中 " + (truthSet.size() - missed.size())
                + " 格；错报（预测有真实没有）" + extra.size() + " 格；漏报（真实有预测没有）"
                + missed.size() + " 格");
        report("    预测来源分布：" + sourceBreakdown(workerResult) + "；预测 Y 分布："
                + yHistogram(predicted));
        if (!missed.isEmpty()) {
            report("      漏报说明：真实世界里存在预测链未覆盖的同类矿物（其它来源，如化石 / 结构 / 矿脉），"
                    + "或离线世界与真实世界在该格状态不同（口径第十二节 FIRST DIVERGENCE 现象）；"
                    + "样本 " + sample(missed));
        }
        if (!extra.isEmpty()) {
            report("      错报逐格归因（真实世界里这一格实际是什么）：" + extraAttribution(extra));
            report("      错报的确定性分类：" + certaintyBreakdown(workerResult, extra)
                    + "；其中引擎自查标记为调度敏感 " + sensitiveExtra + " 格"
                    + (undiagnosedExtra == 0 ? "（全部已由引擎明示随合法顺序变化）"
                    : "（其余 " + undiagnosedExtra + " 格为「未解析」候选的读数 —— 本阶段引擎不声称确定性，"
                    + "其解释由第七节的请求顺序对照给出）"));
        }

        SeedRenderSnapshot render = SERVICE.renderSnapshot();
        int renderEntries = 0;
        for (SeedRenderEntry entry : render.entries()) {
            if (entry.oreType() == current.oreType() && entry.position().getX() >> 4 == current.chunk().x()
                    && entry.position().getZ() >> 4 == current.chunk().z()) {
                renderEntries++;
            }
        }
        boolean clientLoaded = isChunkLoaded(client.level, current.chunk());
        report("    ESP 渲染快照：总条目 " + render.size() + "，其中本用例（"
                + current.oreType().displayNameCn() + " @ " + current.chunk().x() + ","
                + current.chunk().z() + "）" + renderEntries + " 条；快照统计 预测区块 "
                + render.stats().predictedChunks() + " / 候选 " + render.stats().candidates()
                + " / 已确认 " + render.stats().confirmed() + " / 当前缺失 " + render.stats().missing()
                + " / 调度敏感 " + render.stats().scheduleSensitive());
        report("    客户端是否已加载该目标区块：" + (clientLoaded ? "是" : "否（远端用例，观察层应保持未观察）"));

        SeedObservationSnapshot observed = SERVICE.observationSnapshot();
        report("    观察读数：候选 " + observed.candidates() + " / 已确认 " + observed.confirmed()
                + " / 当前缺失 " + observed.missing() + " / 未观察 " + observed.unobserved());

        boolean parityOk = parity;
        boolean countMatchesOracle = workerResult.count() == oracleResult.count();
        boolean renderCovers = renderEntries >= predicted.size();
        boolean pass = parityOk && countMatchesOracle && renderCovers;
        for (int index = 0; index < Math.min(5, mismatches.size()); index++) {
            report("      · " + mismatches.get(index));
        }
        report("    逐项比较：" + (parity ? "完全一致（数量 / BlockPos / 矿物 / 确定性 / 来源 / 写入者）"
                : "**不一致 " + mismatches.size() + " 处**")
                + "；ESP 覆盖预测坐标 " + verdict(renderCovers));
        report("    候选 ↔ 真实口径说明：本阶段引擎<b>不为任何坐标声称确定性</b>（不产出 DETERMINISTIC），"
                + "候选的语义是「可能在这里」；因此错报 / 漏报是<b>读数</b>而不是失败判据，"
                + "下面的逐格归因与第七节的请求顺序对照才是它们的解释。");

        OUTCOME_BY_CASE.put(current.labelCn(), "候选 " + workerResult.count() + " / 真值 " + truthSet.size()
                + " / 命中 " + (truthSet.size() - missed.size()) + " / 错报 " + extra.size()
                + " / 漏报 " + missed.size() + " / 命中率 " + hitRate(workerResult.count(), truthSet.size() - missed.size()));
        FULLY_MATCHED_ORES.merge(current.oreType(), extra.isEmpty() ? 1 : 0, Integer::min);
        EXTRA_TOTAL += extra.size();
        MISSED_TOTAL += missed.size();
        SENSITIVE_EXTRA_TOTAL += sensitiveExtra;
        // 237：两组样本的达标计数（绿宝石看「非零候选区块」，远古残骸看「非空区块 / 候选坐标」）
        if (current.kind() == CaseKind.EMERALD_SAMPLE) {
            if (workerResult.count() > 0) {
                emeraldNonZeroChunks++;
            }
            report("    绿宝石补证累计：非零候选区块 " + emeraldNonZeroChunks + " / "
                    + EMERALD_REQUIRED_NONZERO + "（要求），本用例候选 " + workerResult.count());
        } else if (current.kind() == CaseKind.DEBRIS_SAMPLE) {
            if (workerResult.count() > 0) {
                debrisNonEmptyChunks++;
            }
            debrisPositions += workerResult.count();
            report("    远古残骸扩样本累计：非空目标区块 " + debrisNonEmptyChunks + " / "
                    + DEBRIS_REQUIRED_CHUNKS + "，候选坐标 " + debrisPositions + " / "
                    + DEBRIS_REQUIRED_POSITIONS + "（任一达标即停）；本用例候选 " + workerResult.count());
        }
        String key = caseKey(current.profile(), current.oreType(), current.chunk());
        CASE_DISPLAY.put(key, current.labelCn());
        if (undiagnosedExtra > 0) {
            UNDIAGNOSED_EXTRA_BY_CASE.merge(key, undiagnosedExtra, Integer::sum);
        }
        VERDICTS.add("【判定】" + current.labelCn() + "：Worker↔Oracle 逐项 " + verdict(parityOk)
                + " / ESP " + verdict(renderCovers)
                + " / 读数：候选 " + workerResult.count() + " 真值 " + truthSet.size()
                + " 命中 " + (truthSet.size() - missed.size()) + " 错报 " + extra.size()
                + " 漏报 " + missed.size()
                + (extra.isEmpty() ? "" : "（错报中调度敏感 " + sensitiveExtra + " / 待第七节解释 "
                + undiagnosedExtra + "）"));
        if (!pass) {
            LOGGER.warn("{}：{} 未通过（见报告）", SeedPocConstants.LOG_KEY, current.labelCn());
        }
        advanceCase();
    }

    /** 命中率文本（候选里有多少真在真实世界存在）。 */
    private static String hitRate(int candidates, int hits) {
        if (candidates <= 0) {
            return "—";
        }
        return String.format(java.util.Locale.ROOT, "%.1f%%", 100.0 * hits / candidates);
    }

    /** 用例键 = （矿物, 维度, 目标区块）；汇总判定用它把「用例」与「第七节的顺序对照」对上。 */
    private static String caseKey(SeedDimensionProfile profile, OreType oreType, ChunkPos chunk) {
        return oreType.name() + "|" + profile.dimensionId() + "|" + chunk.x() + "," + chunk.z();
    }

    /** 推进到下一个用例（或结束主循环）。 */
    private static void advanceCase() {
        caseIndex++;
        workerResult = null;
        oracleResult = null;
        truthSet = Set.of();
        realAtPredicted = Map.of();
        waitTicks = 0;
        step = Step.CASE_PREPARE;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 下界：锚点、身份切换、自动挖矿闸门
    // ────────────────────────────────────────────────────────────────────────

    private static void tickNetherAnchor(Minecraft client) {
        if (waitTicks == 0) {
            if (!sendCommand(client, "execute in minecraft:the_nether run tp @s "
                    + NETHER_ANCHOR.getX() + " " + NETHER_ANCHOR.getY() + " " + NETHER_ANCHOR.getZ())) {
                report("**下界锚点传送指令发送失败**");
                VERDICTS.add("【判定】下界：不通过（无法传送）");
                caseIndex = cases.size();
                step = Step.ORDER_TEST;
                return;
            }
        }
        if (++waitTicks > WAIT_WORLD_TICKS) {
            report("**等待进入下界超时**");
            VERDICTS.add("【判定】下界：不通过（进入超时）");
            caseIndex = cases.size();
            step = Step.ORDER_TEST;
            return;
        }
        if (!inDimension(client, SeedDimensionProfile.NETHER)) {
            return;
        }
        int cached = SERVICE.cachedChunkCount();
        int candidates = SERVICE.observationSnapshot().candidates();
        int entries = SERVICE.renderSnapshot().size();
        report("");
        report("四、维度切换（进入下界的那一刻）");
        report("  维度支持：" + SERVICE.dimensionSupportCn());
        report("  主世界残留（必须全为 0）：预测缓存 " + cached + " / 观察候选 " + candidates
                + " / 渲染条目 " + entries);
        boolean clean = cached == 0 && candidates == 0 && entries == 0;
        VERDICTS.add("【判定】主世界 → 下界切换清空（缓存 / 观察 / 渲染全为 0）：" + verdict(clean));
        switchedToNether = true;
        step = Step.WAIT_NETHER;
        waitTicks = 0;
    }

    private static void tickWaitNether(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            report("**等待下界区块加载超时**");
            VERDICTS.add("【判定】下界：不通过（区块加载超时）");
            caseIndex = cases.size();
            step = Step.ORDER_TEST;
            return;
        }
        if (!inDimension(client, SeedDimensionProfile.NETHER)) {
            return;
        }
        SeedMiningService service = SERVICE;
        service.setEnabled(true);
        service.setRenderPrediction(true);
        service.setSeedText(String.valueOf(SeedPocFlags.oreMatrixSeed()));
        if (service.cachedChunkCount() == 0) {
            // 覆盖还没跑起来不算问题：再等几刻，实在不行也继续（矩阵用例自己会手工预测）
            if (waitTicks < 20 * 20) {
                return;
            }
        }
        report("  下界侧覆盖已开始：缓存 " + service.cachedChunkCount() + " 个区块 / 候选 "
                + service.renderSnapshot().stats().candidates() + "；身份 " + service.runtimeIdentityCn());
        step = Step.NETHER_GATES;
        waitTicks = 0;
    }

    private static void tickNetherGates(Minecraft client) {
        SeedMiningService service = SERVICE;
        report("");
        report("五、下界自动挖矿闸门（fail-closed）与有效矿物");
        report("  维度档案：" + service.predictModelCn());
        report("  下界自动挖矿：" + service.netherAutoMiningAllowedCn());
        report("  本阶段允许自动挖矿的矿物（下界）：" + service.autoMinerEligibleOresCn());
        report("  本阶段允许自动挖矿的矿物（主世界）：" + SeedOreRegistry.autoMinerEligible(
                SeedDimensionProfile.OVERWORLD, OreType.DIAMOND));
        boolean miningBlocked = !service.mayUseForAutomatedMining();
        boolean targetNull = service.autoMiningTargetOre() == null;
        boolean everyNetherOreBlocked = true;
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            if (service.mayUseForAutomatedMining(oreType)) {
                everyNetherOreBlocked = false;
            }
        }
        report("  mayUseForAutomatedMining() = " + service.mayUseForAutomatedMining()
                + "；autoMiningTargetOre() = " + service.autoMiningTargetOre());
        VERDICTS.add("【判定】下界自动挖矿 fail-closed（总门关 + 目标为 null + 三种矿物全被拒）："
                + verdict(miningBlocked && targetNull && everyNetherOreBlocked));
        step = Step.CASE_PREPARE;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 缓存 / 观察 / ESP 隔离读数
    // ────────────────────────────────────────────────────────────────────────

    /** 某一维度跑完后的隔离读数；主世界跑完转下界，下界跑完转请求顺序对照。 */
    private static void tickSummary(Minecraft client, SeedDimensionProfile profile) {
        captureIsolation(profile);
        step = profile == SeedDimensionProfile.OVERWORLD ? Step.NETHER_ANCHOR : Step.ORDER_TEST;
        waitTicks = 0;
    }

    /**
     * 取一份「预测缓存 / 观察状态 / ESP 条目」的按矿物分组读数。
     *
     * <p>这一节是 236 三处隔离的直接证据：</p>
     * <ol>
     *     <li><b>Repository 键隔离</b>：同一目标区块上并存多种矿物 ⇒ 键里必须带矿物，
     *         否则后写的会把先写的顶掉（而且不会报错）；</li>
     *     <li><b>观察层键隔离</b>：按矿物分组的「已确认 / 当前缺失」是分开统计的
     *         （同一格里钻石与红石的观察结论互不影响）；</li>
     *     <li><b>渲染层颜色分组</b>：ESP 条目按矿物种类分组，条目数与预测候选数一一对应。</li>
     * </ol>
     *
     * <p>维度隔离靠两件事证明：本次缓存里所有结果的 {@code request().dimension()} 必须等于
     * 当前维度（跨维度结果一条都不许混进来），以及主世界 / 下界各自的身份标识不同。</p>
     */
    private static void captureIsolation(SeedDimensionProfile profile) {
        SeedMiningService service = SERVICE;
        Map<ChunkPos, Set<OreType>> byChunk = new LinkedHashMap<>();
        Map<OreType, Integer> predictedByOre = new LinkedHashMap<>();
        int entries = 0;
        int wrongDimension = 0;
        for (PredictionResult result : service.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            entries++;
            if (!profile.dimensionId().equals(result.request().dimension().identifier().toString())) {
                wrongDimension++;
            }
            byChunk.computeIfAbsent(result.request().chunk(), key -> new LinkedHashSet<>())
                    .add(result.request().oreType());
            predictedByOre.merge(result.request().oreType(), result.count(), Integer::sum);
        }
        Map<OreType, int[]> renderByOre = new LinkedHashMap<>();
        for (SeedRenderEntry entry : service.renderSnapshot().entries()) {
            int[] counters = renderByOre.computeIfAbsent(entry.oreType(), key -> new int[4]);
            counters[0]++;
            switch (entry.state()) {
                case CONFIRMED -> counters[1]++;
                case MISSING -> counters[2]++;
                default -> counters[3]++;
            }
        }
        SeedObservationSnapshot observed = service.observationSnapshot();
        boolean sameChunkMultiOre = byChunk.values().stream().anyMatch(set -> set.size() > 1);
        // ESP 检查：有候选的矿物，它的 ESP 条目数必须等于候选数（0 候选的矿物不产生条目，属正确）
        boolean renderMatchesPredictions = true;
        StringBuilder renderGap = new StringBuilder();
        for (Map.Entry<OreType, Integer> ore : predictedByOre.entrySet()) {
            int esp = renderByOre.getOrDefault(ore.getKey(), new int[4])[0];
            if (esp != ore.getValue()) {
                renderMatchesPredictions = false;
                if (renderGap.length() > 0) {
                    renderGap.append('；');
                }
                renderGap.append(ore.getKey().displayNameCn()).append(" 候选 ").append(ore.getValue())
                        .append(" ≠ ESP ").append(esp);
            }
        }
        report("");
        report(profile == SeedDimensionProfile.OVERWORLD ? "三、主世界隔离读数" : "六、下界隔离读数");
        report("  运行时身份：" + service.runtimeIdentityCn());
        report("  预测缓存条目（维度 + 矿物 + 目标区块）：" + entries + " 条，其中维度不符 "
                + wrongDimension + " 条" + verdict(wrongDimension == 0));
        for (Map.Entry<OreType, Integer> ore : predictedByOre.entrySet()) {
            int[] counters = renderByOre.getOrDefault(ore.getKey(), new int[4]);
            report("    " + ore.getKey().displayNameCn() + "：累计候选 " + ore.getValue()
                    + "；ESP 条目 " + counters[0] + "（已确认 " + counters[1] + " / 当前缺失 "
                    + counters[2] + " / 未观察 " + counters[3] + "）");
        }
        report("  观察快照（全部矿物合计）：候选 " + observed.candidates() + " / 已确认 "
                + observed.confirmed() + " / 当前缺失 " + observed.missing() + " / 未观察 "
                + observed.unobserved());
        report("  同一目标区块上并存多种矿物的区块数（>0 即键隔离成立）："
                + byChunk.values().stream().filter(set -> set.size() > 1).count()
                + "；命中坐标示例 " + multiOreSample(byChunk));
        if (profile == SeedDimensionProfile.OVERWORLD) {
            OVERWORLD_BY_CHUNK.putAll(byChunk);
            overworldIdentity = service.runtimeIdentityCn();
        } else {
            NETHER_BY_CHUNK.putAll(byChunk);
            netherIdentity = service.runtimeIdentityCn();
            report("  主世界身份：" + overworldIdentity);
            report("  下界身份：" + netherIdentity);
            Set<ChunkPos> shared = new LinkedHashSet<>(OVERWORLD_BY_CHUNK.keySet());
            shared.retainAll(NETHER_BY_CHUNK.keySet());
            report("  两个维度都出现过的目标区块坐标：" + shared.size()
                    + "（同一组坐标在两个维度各有一份互不相干的缓存）" + verdict(!shared.isEmpty()));
        }
        VERDICTS.add("【判定】" + profile.displayNameCn() + "键隔离（同区块并存多矿物）："
                + verdict(sameChunkMultiOre)
                + " / 维度不混入：" + verdict(wrongDimension == 0)
                + " / ESP 条目与候选一一对应：" + verdict(renderMatchesPredictions)
                + (renderGap.length() == 0 ? "" : "（不符：" + renderGap + "）"));
    }

    /** 同区块多矿物的样例文本（报告可读性用）。 */
    private static String multiOreSample(Map<ChunkPos, Set<OreType>> byChunk) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<ChunkPos, Set<OreType>> entry : byChunk.entrySet()) {
            if (entry.getValue().size() <= 1) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append("；");
            }
            builder.append('(').append(entry.getKey().x()).append(',').append(entry.getKey().z())
                    .append(")=").append(SeedMiningService.describeOresCn(new ArrayList<>(entry.getValue())));
            if (builder.length() > 260) {
                builder.append("…");
                break;
            }
        }
        return builder.length() == 0 ? "（无）" : builder.toString();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 请求顺序对照（服务端线程）
    // ────────────────────────────────────────────────────────────────────────

    private static void tickOrderTest(Minecraft client) {
        if (!orderDispatched) {
            orderDispatched = true;
            server.execute(SeedOreMatrixRegression::runOrderTests);
            return;
        }
        if (!orderDone) {
            if (++waitTicks > WAIT_PREDICT_TICKS) {
                report("**请求顺序对照超时**");
                VERDICTS.add("【判定】请求顺序对照：不通过（超时）");
                finish();
            }
            return;
        }
        finish();
    }

    private static volatile boolean orderDispatched;
    private static volatile boolean orderDone;

    /**
     * 三种矿物的「两种合法请求顺序」对照。
     *
     * <p>同一份种子下开<b>两个互相独立的预测器</b>（各自的离线世界），用两种合法的区块请求顺序
     * 计算<b>同一个目标区块</b>：</p>
     * <ul>
     *     <li>顺序一：先请求邻块，再把目标区块作为第二次请求（目标区块到达时邻域已经装饰过）；</li>
     *     <li>顺序二：直接把目标区块作为第一次请求（冷启动）。</li>
     * </ul>
     *
     * <p>两种顺序都是原版完全合法的区块请求顺序（真实服务端由玩家移动决定，本装置只是显式指定），
     * 目标区块的候选集合必须完全相同 —— 这是 236 对 Redstone / Lapis / Ancient Debris 的
     * 最低必要 request-order 验证。同时把每次预测自带的调度敏感分类读数一起登记
     * （复用 SCHEDULE_SENSITIVE / UNRESOLVED 语义）。</p>
     */
    private static void runOrderTests() {
        report("");
        report("七、请求顺序对照（" + ORDER_TEST_ORES.size() + " 种矿物 · 同一目标区块 · 两种合法请求顺序 · 各自独立离线世界）");
        try {
            orderOne(OreType.REDSTONE, SeedDimensionProfile.OVERWORLD, OVERWORLD_FAR_A,
                    OVERWORLD_ORDER_NEIGHBOUR);
            orderOne(OreType.LAPIS, SeedDimensionProfile.OVERWORLD, OVERWORLD_FAR_A,
                    OVERWORLD_ORDER_NEIGHBOUR);
            orderOne(OreType.COAL, SeedDimensionProfile.OVERWORLD, OVERWORLD_FAR_A,
                    OVERWORLD_ORDER_NEIGHBOUR);
            orderOne(OreType.ANCIENT_DEBRIS, SeedDimensionProfile.NETHER, NETHER_FAR_A,
                    NETHER_ORDER_NEIGHBOUR);
        } catch (Throwable error) {
            LOGGER.error("{}：请求顺序对照异常", SeedPocConstants.LOG_KEY, error);
            VERDICTS.add("【判定】请求顺序对照：不通过（异常 " + error.getClass().getSimpleName() + "）");
        } finally {
            orderDone = true;
        }
    }

    private static void orderOne(OreType oreType, SeedDimensionProfile profile, ChunkPos target, ChunkPos neighbour) {
        ServerLevel level = server.getLevel(profile.levelKey());
        long seed = SeedPocFlags.oreMatrixSeed();
        SeedOreDefinition definition = SeedOreRegistry.of(profile, oreType);
        PredictionResult warmTarget;
        PredictionResult coldTarget;
        try (SeedOrePredictor first = new SeedOrePredictor(level);
             SeedOrePredictor second = new SeedOrePredictor(level)) {
            // 顺序一：邻块先 → 目标区块后（目标区块到达时邻域已经跑过装饰）
            first.predict(seed, oreType, neighbour);
            warmTarget = first.predict(seed, oreType, target);
            // 顺序二：目标区块第一次请求（冷启动）；随后才请求邻块
            coldTarget = second.predict(seed, oreType, target);
            second.predict(seed, oreType, neighbour);
        }
        Set<BlockPos> warm = positions(warmTarget);
        Set<BlockPos> cold = positions(coldTarget);
        Set<BlockPos> truth = scanRealWorld(level, target, definition, profile);
        boolean equal = warm.equals(cold);
        Set<BlockPos> onlyWarm = new LinkedHashSet<>(warm);
        onlyWarm.removeAll(cold);
        Set<BlockPos> onlyCold = new LinkedHashSet<>(cold);
        onlyCold.removeAll(warm);
        int sensitive = coldTarget.scheduleSensitiveCount();
        boolean warmIsTruth = warm.equals(truth);
        boolean coldIsTruth = cold.equals(truth);
        report("  " + oreType.displayNameCn() + "（" + profile.displayNameCn() + " 目标区块 "
                + target.x() + "," + target.z() + "，邻块 " + neighbour.x() + "," + neighbour.z()
                + "）：顺序「邻块→目标」候选 " + warm.size() + "，顺序「目标冷启动」候选 "
                + cold.size() + "，两种顺序集合相同 "
                + (equal ? "（是）" : "（否 —— 该（矿物, 区块）合法顺序敏感，判据见下）"));
        report("    与真实世界该区块比对：真实 " + truth.size() + " 格；顺序「邻块→目标」与真实一致 "
                + verdict(warmIsTruth) + "；顺序「目标冷启动」与真实一致 " + verdict(coldIsTruth));
        if (!equal) {
            report("    两种合法顺序的差异：" + onlyWarm.size() + " 格只在「邻块→目标」/ "
                    + onlyCold.size() + " 格只在「目标冷启动」；引擎把它们标为调度敏感的 "
                    + sensitive + " 格" + verdict(sensitive == onlyCold.size()));
            report("    差异样本（只在「邻块→目标」里）：" + sample(onlyWarm));
        }
        report("    调度敏感分类（正式引擎自带）：敏感 " + sensitive
                + " / 未解析 " + coldTarget.unresolvedCount()
                + " / 确定 " + coldTarget.deterministicCount()
                + "；外来写入者 " + coldTarget.stats().foreignWriterViewers()
                + "；是否真的跑了反向顺序复核 " + coldTarget.stats().scheduleAnalysisExecuted());
        for (String note : coldTarget.stats().notes()) {
            report("      · " + note);
        }
        // 引擎在「确定」这一档上的承诺：真实世界否定不了任何自称确定的坐标。
        Set<BlockPos> coldExtra = new LinkedHashSet<>(cold);
        coldExtra.removeAll(truth);
        Set<BlockPos> falseCertainty = new LinkedHashSet<>();
        for (PredictedOre ore : coldTarget.ores()) {
            if (ore.certainty() == PredictionCertainty.DETERMINISTIC && coldExtra.contains(ore.position())) {
                falseCertainty.add(ore.position());
            }
        }
        String key = caseKey(profile, oreType, target);
        if (equal) {
            VERDICTS.add("【判定】请求顺序对照 · " + oreType.displayNameCn() + "：两种合法顺序结果相同 "
                    + verdict(true) + "（真实世界 " + truth.size() + " 格，逐格一致 " + verdict(warmIsTruth) + "）");
        } else {
            // 两种合法顺序本就给出不同结果 ⇒ 该（矿物, 区块）是合法顺序敏感。
            //
            // 判据刻意<b>不</b>看「我枚举的这两种顺序里有没有一种撞上真实世界」：真实世界用的是
            // 服务端当时自己的合法请求顺序（本装置把目标区块 ±1 邻域推到 FULL 的顺序），逐次运行
            // 可能落在第三种合法顺序上（236 实测：主世界煤 (3,-1) 一次落在「邻块先」、另一次落在
            // 另一种合法顺序）。把「是否撞上」当门槛，等于让判据随运行时机随机通过或失败。
            //
            // 真正的判据是引擎的诚实承诺，逐条可证：
            //   ① 基线比另一种顺序多出来的坐标，必须正是引擎自查标记为「调度敏感」的那些；
            //   ② 该用例里引擎不产出任何「确定」候选；
            //   ③ 因此不存在「引擎自称确定、却被真实世界否定」的坐标。
            boolean diffMarked = sensitive == onlyCold.size();
            boolean noCertaintyClaim = coldTarget.deterministicCount() == 0;
            boolean noFalseCertainty = falseCertainty.isEmpty();
            boolean explained = diffMarked && noCertaintyClaim && noFalseCertainty;
            if (explained) {
                ORDER_SENSITIVE_CASES.add(key);
            }
            VERDICTS.add("【判定】请求顺序对照 · " + oreType.displayNameCn()
                    + "：两种合法顺序给出不同结果（" + warm.size() + " vs " + cold.size()
                    + "）⇒ 该（矿物, 区块）本就是合法顺序敏感；基线多出的坐标被引擎标为调度敏感 "
                    + verdict(diffMarked) + "；确定性声称 " + coldTarget.deterministicCount()
                    + "（须为 0）" + verdict(noCertaintyClaim) + "；自称确定却被真实否定 "
                    + falseCertainty.size() + " 格" + verdict(noFalseCertainty) + " ⇒ " + verdict(explained)
                    // 这一段是<b>读数</b>（真实世界取的是服务端当时那条合法顺序的结果，不参与判据），
                    // 因此刻意不用 verdict() 的「（**不通过**）」记号 —— 那会让收尾的
                    // 「全部判定」把一条纯读数误判成失败项
                    + "。（读数：真实世界 " + truth.size() + " 格；「邻块先」与真实"
                    + (warmIsTruth ? "逐格一致" : "不一致") + "、「目标冷启动」与真实"
                    + (coldIsTruth ? "逐格一致" : "不一致") + " —— 不参与判据。）");
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 真值扫描与比较
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 真实世界真值：把目标区块与 ±{@value #TRUTH_WRITE_RADIUS} 邻域推到 FULL，再只扫目标区块。
     *
     * <p>邻域也要推到 FULL 是必须的：{@code FEATURES} 的写半径是 1，邻区块装饰时会把方块写进
     * 目标区块；只生成目标区块自己会「少算」那一部分跨区块写入（第四轮口径二漏报 11 格的成因之一）。</p>
     */
    private static Set<BlockPos> scanRealWorld(ServerLevel level, ChunkPos target,
                                               SeedOreDefinition definition, SeedDimensionProfile profile) {
        for (int dx = -TRUTH_WRITE_RADIUS; dx <= TRUTH_WRITE_RADIUS; dx++) {
            for (int dz = -TRUTH_WRITE_RADIUS; dz <= TRUTH_WRITE_RADIUS; dz++) {
                level.getChunk(target.x() + dx, target.z() + dz);
            }
        }
        LevelChunk real = level.getChunk(target.x(), target.z());
        int minY = Math.max(definition.scanMinY(), profile.minY());
        int maxY = Math.min(definition.scanMaxY(), profile.maxY());
        Set<BlockPos> found = new LinkedHashSet<>();
        int baseX = target.getMinBlockX();
        int baseZ = target.getMinBlockZ();
        int first = Math.max(0, Math.floorDiv(minY, 16) - real.getMinSectionY());
        int last = Math.min(real.getSectionsCount() - 1, Math.floorDiv(maxY, 16) - real.getMinSectionY());
        for (int index = first; index <= last; index++) {
            LevelChunkSection section = real.getSection(index);
            if (section == null) {
                continue;
            }
            int baseY = (real.getMinSectionY() + index) * 16;
            for (int localY = 0; localY < 16; localY++) {
                int worldY = baseY + localY;
                if (worldY < minY || worldY > maxY) {
                    continue;
                }
                for (int localZ = 0; localZ < 16; localZ++) {
                    for (int localX = 0; localX < 16; localX++) {
                        BlockState state = section.getBlockState(localX, localY, localZ);
                        if (definition.matches(state)) {
                            found.add(new BlockPos(baseX + localX, worldY, baseZ + localZ));
                        }
                    }
                }
            }
        }
        return found;
    }

    /** 逐项比较：数量 + 逐 BlockPos 的（矿物 / 确定性 / 来源 / 写入者）。 */
    private static List<String> compare(PredictionResult worker, PredictionResult oracle) {
        List<String> problems = new ArrayList<>();
        if (worker == null || oracle == null) {
            problems.add("有一侧结果为空");
            return problems;
        }
        if (worker.success() != oracle.success()) {
            problems.add("成功标记不同：Worker=" + worker.success() + "，Oracle=" + oracle.success());
        }
        if (worker.count() != oracle.count()) {
            problems.add("候选数不同：Worker=" + worker.count() + "，Oracle=" + oracle.count());
        }
        Map<BlockPos, PredictedOre> oracleByPos = new LinkedHashMap<>();
        for (PredictedOre ore : oracle.ores()) {
            oracleByPos.put(ore.position(), ore);
        }
        for (PredictedOre workerOre : worker.ores()) {
            PredictedOre oracleOre = oracleByPos.remove(workerOre.position());
            if (oracleOre == null) {
                problems.add("Worker 多出 " + describe(workerOre));
                continue;
            }
            if (workerOre.oreType() != oracleOre.oreType()) {
                problems.add("矿物不同 " + describe(workerOre) + " vs " + describe(oracleOre));
            }
            if (workerOre.certainty() != oracleOre.certainty()) {
                problems.add("确定性不同 " + describe(workerOre) + " vs " + describe(oracleOre));
            }
            if (workerOre.source() != oracleOre.source()) {
                problems.add("来源不同 " + describe(workerOre) + " vs " + describe(oracleOre));
            }
            if (!Objects.equals(workerOre.originViewer(), oracleOre.originViewer())) {
                problems.add("写入者不同 " + describe(workerOre) + " vs " + describe(oracleOre));
            }
            if (!workerOre.conflictingWriters().equals(oracleOre.conflictingWriters())) {
                problems.add("冲突写入者不同 " + describe(workerOre) + " vs " + describe(oracleOre));
            }
        }
        for (PredictedOre leftover : oracleByPos.values()) {
            problems.add("Worker 缺少 " + describe(leftover));
        }
        return problems;
    }

    private static String describe(PredictedOre ore) {
        return "(" + ore.position().getX() + "," + ore.position().getY() + "," + ore.position().getZ() + ") "
                + ore.oreType() + "/" + ore.certainty() + "/" + ore.source();
    }

    /** 候选位置集合。 */
    private static Set<BlockPos> positions(PredictionResult result) {
        Set<BlockPos> positions = new LinkedHashSet<>();
        if (result != null && result.success()) {
            for (PredictedOre ore : result.ores()) {
                positions.add(ore.position());
            }
        }
        return positions;
    }

    /** 前几个坐标的样本文本。 */
    private static String sample(Set<BlockPos> positions) {
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= 5) {
                break;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',')
                    .append(pos.getZ()).append(')');
        }
        return builder.length() == 0 ? "（无）" : builder.toString();
    }

    /** 预测结果的「来源」分布（ORE_FEATURE / FOSSIL / ORE_VEIN / SCATTERED_ORE）。 */
    private static String sourceBreakdown(PredictionResult result) {
        Map<String, Integer> bySource = new LinkedHashMap<>();
        for (PredictedOre ore : result.ores()) {
            bySource.merge(ore.source().name(), 1, Integer::sum);
        }
        if (bySource.isEmpty()) {
            return "（无候选）";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : bySource.entrySet()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(entry.getKey()).append('=').append(entry.getValue());
        }
        return builder.toString();
    }

    /** 预测候选的 Y 分布（每 32 格一段，报告里一眼看出这批矿落在哪个高度带）。 */
    private static String yHistogram(Set<BlockPos> positions) {
        if (positions.isEmpty()) {
            return "（无候选）";
        }
        Map<Integer, Integer> bands = new java.util.TreeMap<>();
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (BlockPos pos : positions) {
            bands.merge(Math.floorDiv(pos.getY(), 32) * 32, 1, Integer::sum);
            minY = Math.min(minY, pos.getY());
            maxY = Math.max(maxY, pos.getY());
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[').append(minY).append(',').append(maxY).append("] ");
        for (Map.Entry<Integer, Integer> band : bands.entrySet()) {
            builder.append('[').append(band.getKey()).append(',')
                    .append(band.getKey() + 31).append(")=").append(band.getValue()).append(' ');
        }
        return builder.toString().trim();
    }

    /** 错报逐格归因：位置 + 真实世界该格实际是什么方块（最多前 8 条）。 */
    private static String extraAttribution(Set<BlockPos> extra) {
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : extra) {
            if (taken++ >= 8) {
                builder.append(" …共 ").append(extra.size()).append(" 格");
                break;
            }
            if (builder.length() > 0) {
                builder.append('；');
            }
            builder.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',')
                    .append(pos.getZ()).append(")=")
                    .append(realAtPredicted.getOrDefault(pos, "（未采到）"));
        }
        return builder.length() == 0 ? "（无）" : builder.toString();
    }

    /** 真实世界里某一格是什么方块（短 id；越界或区块缺失如实标注）。 */
    private static String blockIdAt(LevelChunk chunk, BlockPos pos, SeedDimensionProfile profile) {
        if (pos.getY() < profile.minY() || pos.getY() > profile.maxY()) {
            return "（纵向越界）";
        }
        return OreBlockLedger.shortId(chunk.getBlockState(pos));
    }

    /** 一组坐标在预测结果里的确定性分布（错报归因用）。 */
    private static String certaintyBreakdown(PredictionResult result, Set<BlockPos> positions) {
        Map<String, Integer> counters = new LinkedHashMap<>();
        for (PredictedOre ore : result.ores()) {
            if (positions.contains(ore.position())) {
                counters.merge(ore.certainty().displayNameCn(), 1, Integer::sum);
            }
        }
        if (counters.isEmpty()) {
            return "（无法归类）";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(entry.getKey()).append('=').append(entry.getValue());
        }
        return builder.toString();
    }

    /** 错报最集中的矿物清单（汇总用）。 */
    private static String oresWithWrongExtraCn() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<OreType, Integer> entry : FULLY_MATCHED_ORES.entrySet()) {
            if (entry.getValue() == 0) {
                if (builder.length() > 0) {
                    builder.append(' ');
                }
                builder.append(entry.getKey().displayNameCn());
            }
        }
        return builder.length() == 0 ? "无" : builder.toString();
    }

    /**
     * 「未被引擎自查标记为调度敏感」的错报去了哪里。
     *
     * <p>本装置不接受「错报只是读数」这种一句话结论：每一格未被标记的错报，都必须能落到
     * 第七节<b>实测</b>为「合法顺序敏感」的（矿物, 区块）上（判据 = 两种合法顺序给出不同结果、
     * 基线多出的坐标被引擎标为调度敏感、该用例里引擎不产出「确定」候选、且不存在「自称确定
     * 却被真实世界否定」的坐标）。落不下去的必须显式列出来。</p>
     */
    private static String undiagnosedSummaryCn() {
        int total = 0;
        StringBuilder unexplained = new StringBuilder();
        for (Map.Entry<String, Integer> entry : UNDIAGNOSED_EXTRA_BY_CASE.entrySet()) {
            total += entry.getValue();
            if (!ORDER_SENSITIVE_CASES.contains(entry.getKey())) {
                if (unexplained.length() > 0) {
                    unexplained.append('；');
                }
                unexplained.append(CASE_DISPLAY.getOrDefault(entry.getKey(), entry.getKey()))
                        .append(' ').append(entry.getValue()).append(" 格");
            }
        }
        boolean ok = unexplained.length() == 0;
        return total + " 格" + (ok
                ? "全部落在第七节实测为「合法顺序敏感」的（矿物, 区块）上：" + verdict(true)
                : "中有未被解释的部分：" + unexplained + verdict(false));
    }

    // ────────────────────────────────────────────────────────────────────────
    // 收尾
    // ────────────────────────────────────────────────────────────────────────

    private static void finish() {
        if (reportWritten) {
            return;
        }
        reportWritten = true;
        step = Step.FINISHED;
        report("");
        report("八、逐用例读数汇总（候选 / 真值 / 命中 / 错报 / 漏报）");
        for (Map.Entry<String, String> entry : OUTCOME_BY_CASE.entrySet()) {
            report("  " + entry.getKey() + " → " + entry.getValue());
        }
        int fullyMatched = 0;
        for (int value : FULLY_MATCHED_ORES.values()) {
            if (value == 1) {
                fullyMatched++;
            }
        }
        report("");
        report("  候选 ↔ 真实 汇总：11 种矿物中「候选全部能在真实世界里找到（错报 0）」的 "
                + fullyMatched + " 种；错报合计 " + EXTRA_TOTAL + " 格（其中引擎自查标记为调度敏感 "
                + SENSITIVE_EXTRA_TOTAL + " 格）/ 漏报合计 " + MISSED_TOTAL
                + " 格；出现错报的矿物：" + oresWithWrongExtraCn());
        report("  口径提醒：本阶段引擎<b>不声称确定性</b>（不产出 DETERMINISTIC），"
                + "候选语义是「可能在这里」；因此错报 / 漏报是读数，其解释见逐格归因与第七节请求顺序对照。");
        VERDICTS.add("【判定】候选 ↔ 真实：错报 " + EXTRA_TOTAL + " 格中，引擎自查标记为调度敏感 "
                + SENSITIVE_EXTRA_TOTAL + " 格；其余 " + undiagnosedSummaryCn());
        report("");
        report("八之二、237 样本补证（绿宝石非零 / 远古残骸扩样本）");
        report("  绿宝石：山地候选目标区块 " + EMERALD_CANDIDATES.size() + " 个；取得非零候选区块 "
                + emeraldNonZeroChunks + " 个（要求 ≥" + EMERALD_REQUIRED_NONZERO + "）");
        for (ChunkPos chunk : EMERALD_CANDIDATES) {
            String outcome = OUTCOME_BY_CASE.get(SeedDimensionProfile.OVERWORLD.displayNameCn() + " · "
                    + OreType.EMERALD.displayNameCn() + " · 区块 (" + chunk.x() + "," + chunk.z() + ")[山地样本补证]");
            if (outcome != null) {
                report("    (" + chunk.x() + "," + chunk.z() + ") 生物群系 "
                        + EMERALD_BIOME_BY_CHUNK.get(chunk) + " → " + outcome);
            }
        }
        VERDICTS.add("【判定】绿宝石非零样本补证（要求 ≥" + EMERALD_REQUIRED_NONZERO + " 个非零候选区块）：取得 "
                + emeraldNonZeroChunks + " 个" + verdict(emeraldNonZeroChunks >= EMERALD_REQUIRED_NONZERO));
        report("  远古残骸：目标区块上限 " + DEBRIS_CANDIDATES.size() + " 个；取得非空目标区块 "
                + debrisNonEmptyChunks + " 个 / 候选坐标 " + debrisPositions + " 个（达标线：≥"
                + DEBRIS_REQUIRED_CHUNKS + " 个非空区块 或 ≥" + DEBRIS_REQUIRED_POSITIONS + " 个候选坐标）");
        VERDICTS.add("【判定】远古残骸扩样本（≥" + DEBRIS_REQUIRED_CHUNKS + " 个非空目标区块 或 ≥"
                + DEBRIS_REQUIRED_POSITIONS + " 个候选坐标）：非空区块 " + debrisNonEmptyChunks
                + " / 候选坐标 " + debrisPositions + verdict(debrisNonEmptyChunks >= DEBRIS_REQUIRED_CHUNKS
                || debrisPositions >= DEBRIS_REQUIRED_POSITIONS));
        report("");
        report("九、计算器诊断");
        report("  " + SERVICE.calculatorDiagnosticsCn());
        report("  " + SERVICE.runtimeDiagnosticsCn());

        List<String> lines = new ArrayList<>();
        lines.add("《236 · Seed Ore Engine 通用化 + 其它矿物 + Nether · 多矿物多维度实机矩阵结果》");
        lines.add("装置：SeedOreMatrixRegression（runClientSeedOreMatrixTest）");
        lines.add("World 夹具：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        lines.add("被试种子：" + SeedPocFlags.oreMatrixSeed());
        lines.add("Worker 侧：隔离进程（服务层公开 API）；Oracle 侧：集成服务端 ServerLevel；");
        lines.add("真值与预测的对照口径：目标区块 + ±1 邻域推到 FULL 后逐格扫描（注意：这是开发验收装置，");
        lines.add("与正式观察层「只读客户端已加载区块、LOAD_OR_GENERATE=false」不是同一件事）。");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("十、判定汇总");
        lines.addAll(VERDICTS);
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    /** 只勾选一种矿物（其余全部取消）。 */
    private static void setOnlyOre(OreType oreType) {
        SeedDimensionProfile profile = SERVICE.dimensionProfile();
        if (profile == null) {
            return;
        }
        for (OreType candidate : SeedOreRegistry.oresOf(profile)) {
            SERVICE.setOreSelected(candidate, candidate == oreType);
        }
    }

    /** 客户端当前所在维度是否就是给定档案。 */
    private static boolean inDimension(Minecraft client, SeedDimensionProfile profile) {
        return client.level != null && profile.dimensionId().equals(
                client.level.dimension().identifier().toString());
    }

    /** 目标区块此刻是否真的加载在客户端（正式观察层的判据，本装置用它做旁证）。 */
    private static boolean isChunkLoaded(ClientLevel level, ChunkPos chunk) {
        return level.getChunkSource().getChunk(chunk.x(), chunk.z(), ChunkStatus.FULL, false) != null;
    }

    /** 发一条服务端指令（开发期装置专用；单人夹具世界自带指令权限）。 */
    private static boolean sendCommand(Minecraft client, String command) {
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            return false;
        }
        LOGGER.info("{}：发送指令 /{}", SeedPocConstants.LOG_KEY, command);
        player.connection.sendCommand(command);
        return true;
    }

    private static String verdict(boolean ok) {
        return ok ? "（通过）" : "（**不通过**）";
    }

    private static void report(String line) {
        REPORT.add(line);
        LOGGER.info("{}：236矩阵｜{}", SeedPocConstants.LOG_KEY, line);
    }
}
