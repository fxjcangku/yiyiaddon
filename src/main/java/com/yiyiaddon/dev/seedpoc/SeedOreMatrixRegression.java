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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
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

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    /** 一个矩阵用例：某一维度、某一矿物、某一目标区块。 */
    private record OreCase(SeedDimensionProfile profile, OreType oreType, ChunkPos chunk, String roleCn) {

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
        // 用例清单：主世界 8 种 × 2 区块，然后下界 3 种 × 2 区块
        List<OreCase> built = new ArrayList<>();
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.OVERWORLD)) {
            built.add(new OreCase(SeedDimensionProfile.OVERWORLD, oreType, OVERWORLD_FAR_A, "远端 A"));
            built.add(new OreCase(SeedDimensionProfile.OVERWORLD, oreType, OVERWORLD_FAR_B, "远端 B"));
        }
        switchIndex = built.size();
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            built.add(new OreCase(SeedDimensionProfile.NETHER, oreType, NETHER_FAR_A, "远端 A"));
            built.add(new OreCase(SeedDimensionProfile.NETHER, oreType, NETHER_FAR_B, "远端 B"));
        }
        cases = List.copyOf(built);
        report("");
        report("一、矩阵规模：主世界 " + SeedOreRegistry.oresOf(SeedDimensionProfile.OVERWORLD).size()
                + " 种 × 2 区块 = " + switchIndex + " 个用例；下界 "
                + SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER).size() + " 种 × 2 区块 = "
                + (cases.size() - switchIndex) + " 个用例；合计 " + cases.size());
        report("");
        report("二、逐用例证据（Worker ↔ Oracle 逐项 + 候选 ↔ 真实方块 + 观察 / ESP / 缓存隔离）");
        step = Step.CASE_PREPARE;
        waitTicks = 0;
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
            step = Step.SUMMARY_OVERWORLD;
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
                + cold.size() + "，集合相同 " + verdict(equal));
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
        String key = caseKey(profile, oreType, target);
        if (equal) {
            VERDICTS.add("【判定】请求顺序对照 · " + oreType.displayNameCn() + "：两种合法顺序结果相同 "
                    + verdict(true) + "（真实世界 " + truth.size() + " 格）");
        } else {
            boolean explained = sensitive == onlyCold.size() && (warmIsTruth || coldIsTruth);
            if (explained) {
                ORDER_SENSITIVE_CASES.add(key);
            }
            VERDICTS.add("【判定】请求顺序对照 · " + oreType.displayNameCn()
                    + "：两种合法顺序给出不同结果（" + warm.size() + " vs " + cold.size()
                    + "）⇒ 该（矿物, 区块）本就是合法顺序敏感；差异坐标被引擎标为调度敏感 "
                    + verdict(sensitive == onlyCold.size()) + "；其中一种顺序与真实世界完全一致 "
                    + verdict(warmIsTruth || coldIsTruth) + " ⇒ " + verdict(explained));
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
     * 差异坐标被引擎标为调度敏感、且其中一种顺序与真实世界完全一致）。落不下去的必须显式列出来。</p>
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
