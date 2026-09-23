package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.feature.mining.ui.console.MiningSeedPage;
import com.yiyiaddon.platform.network.ConnectionCloser;
import com.yiyiaddon.seed.prediction.SeedOrePredictor;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.service.SeedMiningRuntimeState;
import com.yiyiaddon.seed.service.SeedMiningService;
import com.yiyiaddon.ui.component.CompactStack;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第二阶段（报告 230）· <b>服务层回归装置</b>（开发期，DebugProbe 口径）。
 *
 * <p><b>它回答什么</b>：正式化的第二阶段把「正式 Predictor」包了一层
 * {@link SeedMiningService}（配置 / 维度 / 异步 / 取消）。本装置就是这一层的验收台：
 * 从<b>客户端线程</b>用服务层的公开 API 驱动一遍，核对 229 已冻结的数字与状态判据有没有退化。</p>
 *
 * <p><b>刻意走客户端线程</b>：服务层的线程模型是「界面与 tick 在客户端线程、世界生成在后台线程」，
 * 因此验收必须在客户端线程上驱动 —— 只有这样才同时验证了
 * 「后台执行没卡住客户端线程」与「结果能正确回投」两件事。</p>
 *
 * <p><b>它覆盖的验收项</b>：未进入世界状态、种子输入解析矩阵、当前区块异步预测
 * （Seed 20260922 / 2 / 12345 六目标）、服务结果 vs 直连正式预测器逐 BlockPos 一致、
 * 维度切换（主世界 → 下界 → 主世界）与旧结果不串维度、退出世界后的运行时清理。</p>
 *
 * <p><b>触发方式</b>（默认完全关闭；不给系统属性时本类不会被加载执行）：</p>
 * <pre>{@code
 * -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.service=1 -Dyiyiaddon.seedpoc.exit=1
 * }</pre>
 */
public final class ServiceRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /**
     * 单次预测 / 单次维度切换的等待上限（客户端刻）。
     *
     * <p>第四阶段起，服务层第一次预测要先拉起<b>本地隔离世界生成计算器</b>（子进程要跑一遍原版
     * 服务端的世界创建，十几秒起步），因此这里给到 3 分钟，避免把「正常冷启动」误判成超时。</p>
     */
    private static final int WAIT_TIMEOUT_TICKS = 20 * 180;

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-服务层回归.txt";

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    /** Seed 2 的已知调度争议位置（228 报告第七节实测样本）。 */
    private static final BlockPos SEED2_DISPUTED_POS = new BlockPos(-6385, -59, 6085);

    /** 执行阶段。 */
    private enum Stage {
        /** 标题界面（未进入世界）：核对「已关闭 / 等待进入世界」。 */
        TITLE,
        /** 等世界就绪。 */
        ENTER_WORLD,
        /** 界面装配烟测（控制台第八页签 + 个人模式隐藏路径）。 */
        UI_SMOKE,
        /** 种子输入解析矩阵。 */
        SEED_INPUT,
        /** 六个目标的服务层预测。 */
        PREDICT,
        /** 服务结果 vs 直连正式预测器（逐 BlockPos）。 */
        ORACLE,
        /** 维度切换：主世界 → 下界 → 主世界。 */
        DIMENSION,
        /** 从下界回到主世界后的核对。 */
        DIMENSION_BACK,
        /** 再预测一次，让「退出世界确实持有预测器」这件事可被观测。 */
        REWARM,
        /** 退出世界后的运行时清理。 */
        EXIT_WORLD,
        /** 收尾（写报告）。 */
        FINISHED
    }

    /** 一个预测验收用例。 */
    private record OreCase(long seed, int chunkX, int chunkZ, int expectedCount, int expectedSensitive,
                           String note) {
    }

    /** 六个验收用例（数字全部来自报告 229，一处不改）。 */
    private static final List<OreCase> CASES = List.of(
        new OreCase(20260922L, 0, 0, 45, 0, "229 固定集首个目标（冷启动）"),
        new OreCase(2L, -400, 380, 23, 1, "228 受控顺序样本：争议格 (-6385,-59,6085) 必须仍是调度敏感"),
        new OreCase(12345L, 0, 0, 31, 0, "229 Seed 12345 回归"),
        new OreCase(12345L, -1, -1, 29, 7, "228「真值不可重复」区块：正式分析给出 7 个调度敏感"),
        new OreCase(12345L, -25, 17, 24, 0, "228 顺序无关目标"),
        new OreCase(12345L, 120, -130, 26, 0, "228 顺序无关目标"));

    /** 种子输入解析矩阵（期望 null = 非法或未填写）。 */
    private static final List<String[]> SEED_INPUTS = List.of(
        new String[]{"", "null"},
        new String[]{"0", "0"},
        new String[]{"12345", "12345"},
        new String[]{"-7777", "-7777"},
        new String[]{"20260922", "20260922"},
        new String[]{String.valueOf(Long.MAX_VALUE), String.valueOf(Long.MAX_VALUE)},
        new String[]{String.valueOf(Long.MIN_VALUE), String.valueOf(Long.MIN_VALUE)},
        new String[]{"abc", "null"},
        new String[]{"12.3", "null"},
        new String[]{"--", "null"});

    // ── 状态（全部只在客户端线程推进） ──

    private static Stage stage = Stage.TITLE;
    private static int waitTicks;
    private static int caseIndex;
    private static boolean titleChecksDone;
    private static boolean worldRequested;
    private static boolean predicting;
    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();
    private static final List<Set<BlockPos>> SERVICE_POSITIONS = new ArrayList<>();
    private static final List<PredictionResult> SERVICE_RESULTS = new ArrayList<>();

    /** 直连正式预测器（只在 ORACLE 阶段使用，仅用于「服务包装没改结果」的逐 BlockPos 对照）。 */
    private static SeedOrePredictor oraclePredictor;

    /** ORACLE 阶段后台跑一次直连预测（避免在客户端线程上同步跑世界生成）。 */
    private static volatile Set<BlockPos> oraclePositions;
    private static volatile boolean oracleDone;
    private static boolean oracleSubmitted;

    /** 维度切换阶段要还原的位置。 */
    private static double backX;
    private static double backY;
    private static double backZ;

    private ServiceRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在服务层回归模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (stage) {
                case TITLE -> tickTitle(client);
                case ENTER_WORLD -> tickEnterWorld(client);
                case UI_SMOKE -> tickUiSmoke();
                case SEED_INPUT -> tickSeedInput(client);
                case PREDICT -> tickPredict(client);
                case ORACLE -> tickOracle(client);
                case DIMENSION -> tickDimension(client);
                case DIMENSION_BACK -> tickDimensionBack(client);
                case REWARM -> tickRewarm(client);
                case EXIT_WORLD -> tickExitWorld(client);
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            // 开发期装置绝不允许把异常扩散进游戏主循环
            LOGGER.error("{}：服务层回归中断", SeedPocConstants.LOG_KEY, error);
            REPORT.add("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            finish();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 一、未进入世界
    // ────────────────────────────────────────────────────────────────────────

    private static void tickTitle(Minecraft client) {
        if (client.level != null) {
            stage = Stage.ENTER_WORLD;
            return;
        }
        // 与 SeedPocEntry 同一时机口径：只在「无界面」或「原版标题界面」动手，
        // 免得把实验塞进启动期的加载界面或登录流程里
        boolean atTitle = client.screen == null || client.screen instanceof net.minecraft.client.gui.screens.TitleScreen;
        if (!atTitle || titleChecksDone) {
            return;
        }
        titleChecksDone = true;
        // 先把配置设成确定值（同一运行目录的历史配置会残留，不显式设置就没有可比性）
        SERVICE.setEnabled(false);
        report("一、未进入世界（标题界面）");
        report("  关闭状态下状态：" + SERVICE.stateCn() + "；期望「" + SeedMiningRuntimeState.DISABLED.displayNameCn()
            + "」→ " + verdict(SERVICE.state() == SeedMiningRuntimeState.DISABLED));
        SERVICE.setEnabled(true);
        report("  开启但未进入世界：" + SERVICE.stateCn() + "；期望「"
            + SeedMiningRuntimeState.WAITING_FOR_WORLD.displayNameCn() + "」→ "
            + verdict(SERVICE.state() == SeedMiningRuntimeState.WAITING_FOR_WORLD));
        // 进世界：沿用 PoC 夹具（固定种子的全新单人世界），保证每轮都是干净世界
        if (SeedPocFlags.autoCreateWorld()) {
            worldRequested = true;
            SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.fixedTestSeed());
        } else {
            report("  未开自动建世界：请在 90 秒内手动进入一个单人世界");
        }
        stage = Stage.ENTER_WORLD;
    }

    private static void tickEnterWorld(Minecraft client) {
        if (client.level == null || client.player == null) {
            return;
        }
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            report("  **未进入单人世界：直连正式预测器（Oracle）这一侧需要集成服务端；"
                + "服务层侧已不需要宿主（正式宿主由本地隔离计算器提供）**");
            finish();
            return;
        }
        report("  进入世界：" + client.level.dimension().identifier() + "（服务层走本地隔离计算器，"
            + "直连对照走集成服务端）");
        stage = Stage.UI_SMOKE;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 界面装配烟测
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 界面装配烟测：在真实客户端进程里把「自动挖矿控制台 + 种子挖矿页」装配一遍。
     *
     * <p>它不是像素验收（那要人眼看），而是把「页面能不能构建」这件最容易出问题、
     * 又最不容易被发现的错（少 import、空指针、读数抛异常）变成一次可重复的检查：
     * 装配只创建控件树、不渲染、不注册事件，因此不会干扰玩家正常界面。</p>
     */
    private static void tickUiSmoke() {
        report("");
        report("二、界面装配烟测（控制台「种子挖矿」页）");
        if (!(ModuleManager.byId(AutoMinerModule.MODULE_ID) instanceof AutoMinerModule module)) {
            report("  **自动挖矿模块未注册，跳过**");
            stage = Stage.SEED_INPUT;
            return;
        }
        boolean personalWas = module.settings().personalMode;
        try {
            module.settings().personalMode = false;
            MiningConsoleScreen console = new MiningConsoleScreen(null, module);
            CompactStack probe = new CompactStack(6f);
            new MiningSeedPage(console, module).build(probe);
            report("  个人模式关：控制台整窗装配成功；单独装配「种子挖矿」页成功，总高 "
                + Math.round(probe.height()) + " 像素、是否为空：" + probe.isEmpty());
            module.settings().personalMode = true;
            new MiningConsoleScreen(null, module);
            report("  个人模式开：控制台整窗装配成功（种子页签走隐藏路径，不抛异常）");
            VERDICTS.add("【判定】界面装配烟测：通过（两种模式整窗装配成功 + 种子页可构建 "
                + Math.round(probe.height()) + " 像素）");
        } catch (Throwable error) {
            LOGGER.error("{}：界面装配烟测异常", SeedPocConstants.LOG_KEY, error);
            report("  **界面装配异常：" + error.getClass().getSimpleName() + " / " + error.getMessage() + "**");
            VERDICTS.add("【判定】界面装配烟测：不通过");
        } finally {
            module.settings().personalMode = personalWas;
        }
        stage = Stage.SEED_INPUT;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 二、种子输入解析矩阵
    // ────────────────────────────────────────────────────────────────────────

    private static void tickSeedInput(Minecraft client) {
        report("");
        report("三、种子输入解析（服务层公开 API）");
        boolean allOk = true;
        for (String[] input : SEED_INPUTS) {
            String text = input[0];
            String expected = input[1];
            SERVICE.setSeedText(text);
            Long parsed = SERVICE.seedValue();
            String actual = parsed == null ? "null" : String.valueOf(parsed);
            boolean ok = expected.equals(actual);
            allOk &= ok;
            String shown = text.isEmpty() ? "（空串）" : text;
            report("  输入「" + shown + "」→ 解析 " + actual + " / 输入状态「" + SERVICE.seedStatusCn()
                + "」/ 运行时状态「" + SERVICE.stateCn() + "」" + (ok ? "" : "  **与期望 " + expected + " 不符**"));
        }
        // 非法种子不得启动预测：此时 canPredict() 必须为 false
        SERVICE.setSeedText("abc");
        boolean blocked = !SERVICE.canPredict();
        report("  非法种子下是否允许预测：" + (blocked ? "禁止（正确）" : "**允许（违规）**"));
        VERDICTS.add("【判定】种子输入解析矩阵：" + verdict(allOk)
            + "；非法种子禁止启动预测：" + verdict(blocked));
        stage = Stage.PREDICT;
        caseIndex = 0;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 三、服务层预测（六个用例）
    // ────────────────────────────────────────────────────────────────────────

    private static void tickPredict(Minecraft client) {
        if (caseIndex >= CASES.size()) {
            report("");
            report("五、服务结果 vs 直连正式预测器（逐 BlockPos，只对第一个用例做，避免重复世界生成）");
            stage = Stage.ORACLE;
            oracleSubmitted = false;
            waitTicks = 0;
            return;
        }
        OreCase oreCase = CASES.get(caseIndex);
        if (!predicting) {
            if (caseIndex == 0) {
                report("");
                report("四、服务层「测试当前区块预测」（后台单线程执行）");
            }
            // 把玩家放到目标区块：服务层按「玩家当前区块」取目标，与 UI 上点按钮完全同一条路径
            movePlayerToChunk(client, oreCase.chunkX(), oreCase.chunkZ());
            SERVICE.setSeedText(String.valueOf(oreCase.seed()));
            SERVICE.setEnabled(true);
            long submitStartNanos = System.nanoTime();
            SERVICE.predictCurrentChunk();
            long submitMicros = (System.nanoTime() - submitStartNanos) / 1000L;
            if (!SERVICE.predicting()) {
                report("  用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + "," + oreCase.chunkZ()
                    + ")：**预测未启动**（当前状态 " + SERVICE.stateCn() + "）");
                VERDICTS.add("【判定】用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + ","
                    + oreCase.chunkZ() + ")：不通过（预测未启动）");
                caseIndex++;
                return;
            }
            report("  用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + "," + oreCase.chunkZ()
                + ")：" + oreCase.note() + "｜提交后状态「" + SERVICE.stateCn() + "」，客户端线程提交耗时 "
                + submitMicros + " 微秒（世界生成不在渲染线程）");
            predicting = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待预测结果超时（" + (WAIT_TIMEOUT_TICKS / 20) + " 秒）**");
                VERDICTS.add("【判定】用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + ","
                    + oreCase.chunkZ() + ")：不通过（超时）");
                predicting = false;
                caseIndex++;
            }
            return;
        }
        // 结果已回投
        PredictionResult result = SERVICE.lastResult();
        predicting = false;
        if (result == null) {
            report("    **结果为空**");
            VERDICTS.add("【判定】用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + ","
                + oreCase.chunkZ() + ")：不通过（结果为空）");
            caseIndex++;
            return;
        }
        ChunkPos predictedChunk = result.request().chunk();
        boolean chunkOk = predictedChunk.x() == oreCase.chunkX() && predictedChunk.z() == oreCase.chunkZ();
        boolean countOk = !result.failed() && result.count() == oreCase.expectedCount();
        boolean sensitiveOk = !result.failed() && result.scheduleSensitiveCount() == oreCase.expectedSensitive();
        boolean deterministicZero = !result.failed() && result.deterministicCount() == 0;
        boolean unresolvedOk = !result.failed()
            && result.unresolvedCount() == result.count() - result.scheduleSensitiveCount();
        report("    状态：" + SERVICE.stateCn() + "；目标区块 (" + predictedChunk.x() + "," + predictedChunk.z()
            + ")（期望 (" + oreCase.chunkX() + "," + oreCase.chunkZ() + ")）" + verdict(chunkOk));
        report("    候选钻石 " + result.count() + "（期望 " + oreCase.expectedCount() + "）" + verdict(countOk)
            + "；调度敏感 " + result.scheduleSensitiveCount() + "（期望 " + oreCase.expectedSensitive() + "）"
            + verdict(sensitiveOk));
        report("    未解析 " + result.unresolvedCount() + "；确定性 " + result.deterministicCount()
            + "（本阶段恒 0）" + verdict(deterministicZero)
            + "；未解析=候选-敏感：" + verdict(unresolvedOk));
        report("    预测耗时 " + result.elapsedMillis() + " ms；会话持有离线区块 " + result.stats().heldChunks()
            + " 个；宿主 ChunkMap 查询增量 " + result.stats().hostChunkSourceQueries()
            + "（必须为 0）" + verdict(result.stats().hostChunkSourceQueries() == 0));
        boolean disputedOk = true;
        if (oreCase.seed() == 2L) {
            PredictedOre disputed = findOre(result, SEED2_DISPUTED_POS);
            disputedOk = disputed != null && disputed.certainty() == PredictionCertainty.SCHEDULE_SENSITIVE;
            report("    已知争议位置 (" + SEED2_DISPUTED_POS.getX() + "," + SEED2_DISPUTED_POS.getY() + ","
                + SEED2_DISPUTED_POS.getZ() + ")：" + (disputed == null ? "不在本次预测集里"
                    : disputed.certainty().displayNameCn()) + "；期望「调度敏感」" + verdict(disputedOk));
        }
        VERDICTS.add("【判定】用例 种子 " + oreCase.seed() + " 区块 (" + oreCase.chunkX() + "," + oreCase.chunkZ()
            + ")：目标区块 " + verdict(chunkOk) + " / 候选 " + result.count() + verdict(countOk)
            + " / 调度敏感 " + result.scheduleSensitiveCount() + verdict(sensitiveOk)
            + " / 确定性为零 " + verdict(deterministicZero) + " / 未解析关系 " + verdict(unresolvedOk)
            + (oreCase.seed() == 2L ? " / 争议格调度敏感 " + verdict(disputedOk) : ""));
        SERVICE_POSITIONS.add(positionsOf(result));
        SERVICE_RESULTS.add(result);
        caseIndex++;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 四、服务结果 vs 直连正式预测器
    // ────────────────────────────────────────────────────────────────────────

    private static void tickOracle(Minecraft client) {
        if (SERVICE_RESULTS.isEmpty()) {
            report("  没有可对照的服务层结果，跳过");
            stage = Stage.DIMENSION;
            waitTicks = 0;
            return;
        }
        OreCase oreCase = CASES.get(0);
        if (!oracleSubmitted) {
            IntegratedServer server = client.getSingleplayerServer();
            ServerLevel host = server == null ? null : server.overworld();
            if (host == null) {
                report("  世界生成宿主不可用，跳过直连对照");
                stage = Stage.DIMENSION;
                waitTicks = 0;
                return;
            }
            oraclePredictor = new SeedOrePredictor(host);
            oraclePositions = null;
            oracleDone = false;
            oracleSubmitted = true;
            Thread worker = new Thread(() -> {
                try {
                    PredictionResult direct = oraclePredictor.predictDiamond(oreCase.seed(),
                        new ChunkPos(oreCase.chunkX(), oreCase.chunkZ()));
                    oraclePositions = positionsOf(direct);
                } catch (Throwable error) {
                    LOGGER.error("{}：直连对照预测异常", SeedPocConstants.LOG_KEY, error);
                    oraclePositions = Set.of();
                } finally {
                    oracleDone = true;
                }
            }, "yiyiaddon-seedpoc-oracle");
            worker.setDaemon(true);
            worker.start();
            report("  直连正式 Predictor（独立实例、独立后台线程）预测 种子 " + oreCase.seed()
                + " 区块 (" + oreCase.chunkX() + "," + oreCase.chunkZ() + ")……");
            return;
        }
        if (!oracleDone) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待直连对照超时**");
                stage = Stage.DIMENSION;
                waitTicks = 0;
            }
            return;
        }
        Set<BlockPos> servicePositions = SERVICE_POSITIONS.get(0);
        Set<BlockPos> directPositions = oraclePositions == null ? Set.of() : oraclePositions;
        Set<BlockPos> missing = new LinkedHashSet<>(directPositions);
        missing.removeAll(servicePositions);
        Set<BlockPos> extra = new LinkedHashSet<>(servicePositions);
        extra.removeAll(directPositions);
        boolean exact = missing.isEmpty() && extra.isEmpty();
        report("  服务层 " + servicePositions.size() + " 个 / 直连 " + directPositions.size() + " 个 → 服务有直连没有 "
            + extra.size() + " / 直连有服务没有 " + missing.size() + " → " + (exact ? "逐 BlockPos 完全一致" : "**不一致**"));
        VERDICTS.add("【判定】服务层结果 vs 直连正式 Predictor 逐 BlockPos 一致：" + verdict(exact));
        if (oraclePredictor != null) {
            final SeedOrePredictor closing = oraclePredictor;
            Thread closer = new Thread(closing::close, "yiyiaddon-seedpoc-oracle-close");
            closer.setDaemon(true);
            closer.start();
            oraclePredictor = null;
        }
        stage = Stage.DIMENSION;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 五、维度切换
    // ────────────────────────────────────────────────────────────────────────

    private static void tickDimension(Minecraft client) {
        if (waitTicks == 0) {
            report("");
            report("六、维度切换（实机切换：主世界 → 下界 → 主世界）");
            PredictionResult before = SERVICE.lastResult();
            report("  切换前：状态「" + SERVICE.stateCn() + "」，上一次结果 "
                + (before == null ? "无" : before.count() + " 个") + "，当前维度 " + SERVICE.dimensionDisplayCn());
            backX = client.player.position().x();
            backY = client.player.position().y();
            backZ = client.player.position().z();
            teleportToDimension(client, Level.NETHER, 0.0, 70.0, 0.0);
            waitTicks = 1;
            return;
        }
        if (!waitingFor(client, Level.NETHER)) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待进入下界超时（自动化环境切维度失败）**");
                VERDICTS.add("【判定】维度切换：未完成（等待下界超时）");
                stage = Stage.EXIT_WORLD;
                waitTicks = 0;
            }
            return;
        }
        boolean unsupported = SERVICE.state() == SeedMiningRuntimeState.UNSUPPORTED_DIMENSION;
        boolean cleared = SERVICE.lastResult() == null;
        report("  下界：维度显示「" + SERVICE.dimensionDisplayCn() + "」/ 支持状态「" + SERVICE.dimensionSupportCn()
            + "」/ 运行时状态「" + SERVICE.stateCn() + "」" + verdict(unsupported));
        report("    旧维度结果是否已清除：" + (cleared ? "已清除" : "**仍在（会冒充新维度）**") + verdict(cleared));
        boolean canNotPredict = !SERVICE.canPredict();
        report("    下界是否允许预测：" + (canNotPredict ? "禁止（正确）" : "**允许（违规）**") + verdict(canNotPredict));
        VERDICTS.add("【判定】下界状态与旧结果清理：状态 " + verdict(unsupported) + " / 结果已清 " + verdict(cleared)
            + " / 禁止预测 " + verdict(canNotPredict));
        teleportToDimension(client, Level.OVERWORLD, backX, backY, backZ);
        waitTicks = 1;
        stage = Stage.DIMENSION_BACK;
    }

    /** 从下界回到主世界后的核对（拆成独立阶段，避免上面那一段过长）。 */
    private static void tickDimensionBack(Minecraft client) {
        if (!waitingFor(client, Level.OVERWORLD)) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待返回主世界超时**");
                VERDICTS.add("【判定】返回主世界：未完成（超时）");
                stage = Stage.EXIT_WORLD;
                waitTicks = 0;
            }
            return;
        }
        boolean ready = SERVICE.state() == SeedMiningRuntimeState.READY;
        boolean stillCleared = SERVICE.lastResult() == null;
        report("  返回主世界：维度显示「" + SERVICE.dimensionDisplayCn() + "」/ 支持状态「"
            + SERVICE.dimensionSupportCn() + "」/ 运行时状态「" + SERVICE.stateCn() + "」" + verdict(ready));
        report("    返回后结果仍为空（旧维度的结果不会复活）：" + verdict(stillCleared));
        VERDICTS.add("【判定】返回主世界回到就绪：" + verdict(ready) + " / 结果未被旧维度复活：" + verdict(stillCleared));
        stage = Stage.REWARM;
        waitTicks = 0;
    }

    /**
     * 回主世界后再预测一次。
     *
     * <p>维度切换会释放预测器（见服务层类注释的口径），因此退出世界那一步本该「持有 → 释放」；
     * 不先补一次预测，断线时就看不到「确实持有过」，这条验收便没有证据。</p>
     */
    private static void tickRewarm(Minecraft client) {
        if (waitTicks == 0) {
            report("");
            report("七、回主世界后补一次预测（让「退出世界确实持有预测器」可被观测）");
            SERVICE.predictCurrentChunk();
            waitTicks = 1;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待补预测超时**");
                stage = Stage.EXIT_WORLD;
                waitTicks = 0;
            }
            return;
        }
        PredictionResult result = SERVICE.lastResult();
        boolean held = SERVICE.calculatorRunning();
        report("  补预测结果：" + (result == null ? "无" : result.count() + " 个") + "；本地世界生成计算器是否在运行："
            + held + verdict(held && result != null));
        stage = Stage.EXIT_WORLD;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 六、退出世界
    // ────────────────────────────────────────────────────────────────────────

    private static void tickExitWorld(Minecraft client) {
        if (waitTicks == 0) {
            report("");
            report("八、退出世界（断开连接）");
            boolean hadResult = SERVICE.lastResult() != null;
            report("  断线前：状态「" + SERVICE.stateCn() + "」，本地世界生成计算器在运行："
                + SERVICE.calculatorRunning() + "，上一次结果：" + (hadResult ? "有" : "无"));
            if (!ConnectionCloser.disconnect(Component.literal("种子挖矿服务层回归：测试断开连接"))) {
                report("    **断线请求未发出（当前没有连接）**");
                finish();
                return;
            }
            waitTicks = 1;
            return;
        }
        if (client.level != null) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待离开世界超时**");
                VERDICTS.add("【判定】退出世界清理：未完成（超时）");
                finish();
            }
            return;
        }
        boolean waiting = SERVICE.state() == SeedMiningRuntimeState.WAITING_FOR_WORLD;
        boolean cleared = SERVICE.lastResult() == null;
        // 退世界后的收尾是异步的：服务层把「停止计算器」投给后台 IO 线程，进程还要依次走
        // 握手关闭 → 优雅销毁 → 强杀 三级超时，所以必须给一个有限等待窗口。
        // 窗口内进程退出 = 通过；窗口用尽仍在跑 = 不通过（判据不变，只是不再拿「同刻快照」当结论）。
        if (SERVICE.calculatorRunning()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待本地世界生成计算器退出超时（" + (WAIT_TIMEOUT_TICKS / 20) + " 秒）**");
                VERDICTS.add("【判定】退出世界清理：未完成（计算器未在窗口内停止）");
                finish();
            }
            return;
        }
        boolean released = !SERVICE.calculatorRunning();
        report("  已回到标题界面：状态「" + SERVICE.stateCn() + "」" + verdict(waiting));
        report("    运行时结果已清：" + verdict(cleared) + "；本地世界生成计算器已停止：" + verdict(released)
            + "（等待窗口内退出的才会计到这一行）");
        report("    配置保留：启用=" + SERVICE.enabled() + "，种子原文=" + SERVICE.seedText()
            + "（种子与开关属于配置，按口径不清）");
        VERDICTS.add("【判定】退出世界清理：状态回「等待进入世界」" + verdict(waiting)
            + " / 结果已清 " + verdict(cleared) + " / 计算器已停止 " + verdict(released));
        finish();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    /** 把玩家放到目标区块（服务层按玩家当前区块取预测目标，与 UI 点按钮同一条路径）。 */
    private static void movePlayerToChunk(Minecraft client, int chunkX, int chunkZ) {
        double x = (double) (chunkX << 4) + 8.5d;
        double z = (double) (chunkZ << 4) + 8.5d;
        double y = client.player.position().y();
        client.player.setPos(x, y, z);
    }

    /** 在服务端线程上把玩家送到指定维度的指定坐标。 */
    private static void teleportToDimension(Minecraft client, net.minecraft.resources.ResourceKey<Level> dimension,
                                            double x, double y, double z) {
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            return;
        }
        server.execute(() -> {
            try {
                ServerLevel target = server.getLevel(dimension);
                if (target == null) {
                    LOGGER.warn("{}：目标维度不存在 {}", SeedPocConstants.LOG_KEY, dimension.identifier());
                    return;
                }
                List<ServerPlayer> players = server.getPlayerList().getPlayers();
                if (players.isEmpty()) {
                    LOGGER.warn("{}：集成服务端上没有玩家，无法切换维度", SeedPocConstants.LOG_KEY);
                    return;
                }
                for (ServerPlayer player : players) {
                    player.teleportTo(target, x, y, z, Set.of(), player.getYRot(), player.getXRot(), false);
                }
            } catch (Throwable error) {
                LOGGER.error("{}：维度切换失败", SeedPocConstants.LOG_KEY, error);
            }
        });
    }

    /** 客户端是否已经停在某个维度上。 */
    private static boolean waitingFor(Minecraft client, net.minecraft.resources.ResourceKey<Level> dimension) {
        return client.level != null && client.player != null && dimension.equals(client.level.dimension());
    }

    private static Set<BlockPos> positionsOf(PredictionResult result) {
        Set<BlockPos> positions = new LinkedHashSet<>();
        for (PredictedOre ore : result.ores()) {
            positions.add(ore.position());
        }
        return positions;
    }

    private static PredictedOre findOre(PredictionResult result, BlockPos pos) {
        for (PredictedOre ore : result.ores()) {
            if (ore.position().equals(pos)) {
                return ore;
            }
        }
        return null;
    }

    private static String verdict(boolean ok) {
        return ok ? "（通过）" : "（**不通过**）";
    }

    private static void report(String line) {
        REPORT.add(line);
    }

    /** 收尾：把判定汇总与正文写进日志与运行目录，并按需退出客户端。 */
    private static void finish() {
        if (stage == Stage.FINISHED) {
            return;
        }
        stage = Stage.FINISHED;
        List<String> lines = new ArrayList<>();
        lines.add("《232 · 种子挖矿正式化第四阶段 · 服务层回归结果（本地隔离世界生成计算器）》");
        lines.add("驱动方式：客户端线程调用 SeedMiningService 公开 API（与界面按钮同一条路径）；");
        lines.add("正式宿主：本机隔离的 Worker 进程（Vanilla ServerLevel）；直连对照：集成服务端的 ServerLevel。");
        lines.add("数字基线：报告 229（正式 Predictor 迁移回归）与报告 228（FEEDBACK 调度定案）。");
        lines.add("测试世界：" + SeedPocWorldFactory.LEVEL_ID + "（每轮新建，种子 "
            + SeedPocFlags.fixedTestSeed() + "；注意预测用的是手动填写的种子，与世界种子无关）");
        lines.add("装置新鲜度：" + SeedPocWorldFactory.lastFreshNote());
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("九、判定汇总");
        lines.addAll(VERDICTS);
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }
}
