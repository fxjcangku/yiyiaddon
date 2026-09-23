package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.prediction.SeedOrePredictor;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿第四阶段（报告 232）· <b>Worker vs 单人 Oracle parity 装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>（口径第四十二~四十七节）：本阶段把正式世界生成宿主从「集成服务端」换成了
 * 「本机隔离的 Worker 进程」。换了宿主，就必须证明<b>结果一模一样</b>：</p>
 *
 * <ul>
 *   <li><b>Worker 侧</b>：从客户端线程调用 {@link SeedMiningService} 公开 API（与界面按钮同一条路径），
 *       由它拉起隔离 Worker 并完成预测；</li>
 *   <li><b>Oracle 侧</b>：同一个客户端进程里直接用集成服务端的 {@code ServerLevel} 构造
 *       {@link SeedOrePredictor}（236 前叫 {@code DiamondSeedPredictor}）（这就是 229/230 一直用的那条「单人 Oracle」老路）；</li>
 *   <li>两侧比较<b>完整 PredictionResult</b>：候选数量、逐 BlockPos、矿物种类、确定性分类、来源分类、
 *       跨区块写入者（originViewer）与冲突写入者列表，<b>逐项</b>一致才算通过。</li>
 * </ul>
 *
 * <p>固定集来自 229/230：Seed 20260922 十目标（合计 243）、Seed 12345 四目标（合计 110）、
 * Seed 2 (-400,380)（23 / 1 敏感 / 22 未解析 / 0 确定，争议格 (-6385,-59,6085) 必须仍是调度敏感）。</p>
 *
 * <p><b>触发方式</b>：{@code -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.workerParity=1
 * -Dyiyiaddon.seedpoc.exit=1}</p>
 */
public final class WorkerParityRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-232-Worker对照.txt";

    /** 单次预测的等待上限（客户端刻）：Worker 首次要现起宿主，给 3 分钟。 */
    private static final int WAIT_TIMEOUT_TICKS = 20 * 180;

    /** Seed 2 的已知调度争议位置（228 报告第七节实测样本）。 */
    private static final BlockPos SEED2_DISPUTED_POS = new BlockPos(-6385, -59, 6085);

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    /**
     * 一个对照目标。
     *
     * @param seed           种子
     * @param chunkX         目标区块 X
     * @param chunkZ         目标区块 Z
     * @param expectedCount  预期候选数（-1 = 本装置只记录不判定；数字全部来自 229/230）
     * @param expectedSensitive 预期调度敏感数（-1 = 不判定）
     * @param note           说明
     */
    private record Target(long seed, int chunkX, int chunkZ, int expectedCount, int expectedSensitive,
                          String note) {
    }

    /** 固定集：229/230 的十 + 四 + 一，共 15 个目标。 */
    private static final List<Target> TARGETS = List.of(
        // Seed 20260922 十目标（229 固定集；合计 243，见口径第三十五节的汇总判定）
        new Target(20260922L, 0, 0, 45, 0, "229 固定集首个目标（冷启动；口径要求 45 / 0 / 45 / 0）"),
        new Target(20260922L, 1, 0, -1, -1, "229 固定集"),
        new Target(20260922L, 0, 1, -1, -1, "229 固定集"),
        new Target(20260922L, 1, 1, -1, -1, "229 固定集"),
        new Target(20260922L, -1, 0, -1, -1, "229 固定集"),
        new Target(20260922L, 0, -1, -1, -1, "229 固定集"),
        new Target(20260922L, -1, -1, -1, -1, "229 固定集"),
        new Target(20260922L, 2, 2, -1, -1, "229 固定集"),
        new Target(20260922L, 3, -1, -1, -1, "229 固定集"),
        new Target(20260922L, -2, 3, -1, -1, "229 固定集"),
        // Seed 12345 四目标（233 沿用 229 的口径）
        new Target(12345L, 0, 0, 31, 0, "229 Seed 12345 回归"),
        new Target(12345L, -1, -1, 29, 7, "228「真值不可重复」区块"),
        new Target(12345L, -25, 17, 24, 0, "228 顺序无关目标"),
        new Target(12345L, 120, -130, 26, 0, "228 顺序无关目标"),
        // Seed 2 争议目标
        new Target(2L, -400, 380, 23, 1, "228 受控顺序样本（争议格必须仍是调度敏感）"));

    /** 229 固定集十目标的候选总数（口径第三十五节：保持 229/230 口径）。 */
    private static final int EXPECTED_20260922_TOTAL = 243;

    /** 229 Seed 12345 四目标的候选总数。 */
    private static final int EXPECTED_12345_TOTAL = 110;

    private enum Stage {
        /** 等进入单人世界（Oracle 需要集成服务端）。 */
        ENTER_WORLD,
        /** Worker 侧预测。 */
        WORKER,
        /** Oracle 侧预测。 */
        ORACLE,
        /** 比较并推进下一个目标。 */
        COMPARE,
        FINISHED
    }

    private static Stage stage = Stage.ENTER_WORLD;
    private static int targetIndex;
    private static int waitTicks;
    private static boolean worldRequested;

    private static ServerLevel oracleHost;
    private static SeedOrePredictor oraclePredictor;
    private static volatile PredictionResult oracleResult;
    private static volatile boolean oracleDone;

    private static PredictionResult workerResult;
    private static boolean workerSubmitted;
    private static long seedApplied = Long.MIN_VALUE;

    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();
    private static final Map<String, PredictionResult> WORKER_BY_TARGET = new LinkedHashMap<>();

    /** 收尾只允许执行一次（报告落盘 + 自动退出）。 */
    private static boolean reportWritten;

    private WorkerParityRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在 Worker 对照模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (stage) {
                case ENTER_WORLD -> tickEnterWorld(client);
                case WORKER -> tickWorker(client);
                case ORACLE -> tickOracle();
                case COMPARE -> tickCompare();
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            LOGGER.error("{}：Worker 对照装置中断", SeedPocConstants.LOG_KEY, error);
            REPORT.add("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            finish();
        }
    }

    private static void tickEnterWorld(Minecraft client) {
        if (client.level == null || client.player == null) {
            boolean atTitle = client.gui.screen() == null || client.gui.screen() instanceof net.minecraft.client.gui.screens.TitleScreen;
            if (!worldRequested && SeedPocFlags.autoCreateWorld() && atTitle) {
                worldRequested = true;
                SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.fixedTestSeed());
            }
            return;
        }
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            abort("未进入单人世界：Oracle 侧需要集成服务端，本轮对照不成立");
            return;
        }
        oracleHost = server.overworld();
        if (oracleHost == null) {
            abort("集成服务端的主世界不可用");
            return;
        }
        oraclePredictor = new SeedOrePredictor(oracleHost);
        report("零、环境");
        report("  Oracle 宿主：集成服务端主世界（229/230 一直用的老路）");
        report("  Worker 宿主：本机隔离进程（服务层自动拉起，口径第三、五节）");
        report("  固定集：Seed 20260922 十目标 / Seed 12345 四目标 / Seed 2 (-400,380)");
        report("  世界夹具：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        report("  （预测用的种子是手动填写的，与世界种子无关）");
        report("");
        report("一、逐目标对照（Worker 侧走服务层公开 API；Oracle 侧走集成服务端 ServerLevel）");
        stage = Stage.WORKER;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Worker 侧
    // ────────────────────────────────────────────────────────────────────────

    private static void tickWorker(Minecraft client) {
        if (targetIndex >= TARGETS.size()) {
            stage = Stage.FINISHED;
            summarise();
            return;
        }
        Target target = TARGETS.get(targetIndex);
        if (seedApplied != target.seed()) {
            SERVICE.setSeedText(String.valueOf(target.seed()));
            SERVICE.setEnabled(true);
            seedApplied = target.seed();
        }
        if (!workerSubmitted) {
            SERVICE.predictChunk(target.chunkX(), target.chunkZ());
            if (!SERVICE.predicting()) {
                report("  **Worker 侧未能启动预测**（状态 " + SERVICE.stateCn() + "）");
                VERDICTS.add("【判定】" + label(target) + "：不通过（Worker 侧未启动）");
                targetIndex++;
                return;
            }
            workerSubmitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("  **等待 Worker 侧预测超时（" + (WAIT_TIMEOUT_TICKS / 20) + " 秒）**");
                VERDICTS.add("【判定】" + label(target) + "：不通过（Worker 侧超时）");
                workerSubmitted = false;
                targetIndex++;
                waitTicks = 0;
            }
            return;
        }
        workerSubmitted = false;
        workerResult = SERVICE.lastResult();
        if (workerResult == null) {
            report("  **Worker 侧结果为空**");
            VERDICTS.add("【判定】" + label(target) + "：不通过（Worker 结果为空）");
            targetIndex++;
            return;
        }
        WORKER_BY_TARGET.put(label(target), workerResult);
        report("");
        report("  目标 " + label(target) + "：" + target.note());
        report("    Worker（服务层）：候选 " + workerResult.count()
            + "（敏感 " + workerResult.scheduleSensitiveCount()
            + " / 未解析 " + workerResult.unresolvedCount()
            + " / 确定 " + workerResult.deterministicCount()
            + "），耗时 " + workerResult.elapsedMillis() + " ms，缓存 " + workerResult.stats().heldChunks()
            + "，宿主 ChunkMap 查询 " + workerResult.stats().hostChunkSourceQueries()
            + "（必须为 0）" + verdict(workerResult.stats().hostChunkSourceQueries() == 0));
        stage = Stage.ORACLE;
        oracleResult = null;
        oracleDone = false;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Oracle 侧
    // ────────────────────────────────────────────────────────────────────────

    private static void tickOracle() {
        Target target = TARGETS.get(targetIndex);
        if (!oracleDone && oracleResult == null) {
            if (waitTicks == 0) {
                Thread runner = new Thread(() -> {
                    try {
                        oracleResult = oraclePredictor.predictDiamond(target.seed(),
                            new ChunkPos(target.chunkX(), target.chunkZ()));
                    } catch (Throwable error) {
                        LOGGER.error("{}：Oracle 预测异常", SeedPocConstants.LOG_KEY, error);
                        oracleResult = PredictionResult.failure(
                            com.yiyiaddon.seed.model.SeedOreTarget.diamond(target.seed(),
                                new ChunkPos(target.chunkX(), target.chunkZ())),
                            "Oracle 异常：" + error.getClass().getSimpleName(), 0L);
                    } finally {
                        oracleDone = true;
                    }
                }, "yiyiaddon-seedpoc-worker-oracle");
                runner.setDaemon(true);
                runner.start();
                waitTicks = 1;
                return;
            }
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待 Oracle 预测超时**");
                VERDICTS.add("【判定】" + label(target) + "：不通过（Oracle 超时）");
                targetIndex++;
                workerResult = null;
                waitTicks = 0;
                stage = Stage.WORKER;
            }
            return;
        }
        if (!oracleDone) {
            return;
        }
        report("    Oracle（集成服务端）：候选 " + (oracleResult == null ? -1 : oracleResult.count())
            + "，耗时 " + (oracleResult == null ? 0 : oracleResult.elapsedMillis()) + " ms，缓存 "
            + (oracleResult == null ? 0 : oracleResult.stats().heldChunks()) + "，宿主 ChunkMap 查询 "
            + (oracleResult == null ? -1 : oracleResult.stats().hostChunkSourceQueries())
            + (oracleResult != null && oracleResult.stats().hostChunkSourceQueries() == 0 ? "" : " **异常**"));
        stage = Stage.COMPARE;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 比较
    // ────────────────────────────────────────────────────────────────────────

    private static void tickCompare() {
        Target target = TARGETS.get(targetIndex);
        List<String> mismatches = compare(workerResult, oracleResult);
        boolean exact = mismatches.isEmpty();
        boolean countExpected = target.expectedCount() < 0 || workerResult.count() == target.expectedCount();
        boolean sensitiveExpected = target.expectedSensitive() < 0
            || workerResult.scheduleSensitiveCount() == target.expectedSensitive();
        boolean deterministicZero = workerResult.deterministicCount() == 0;
        report("    逐项比较：" + (exact ? "完全一致（数量 / BlockPos / 矿物 / 确定性 / 来源 / 写入者）"
            : "**不一致 " + mismatches.size() + " 处**"));
        for (int index = 0; index < Math.min(5, mismatches.size()); index++) {
            report("      · " + mismatches.get(index));
        }
        if (target.expectedCount() >= 0) {
            report("    候选数：" + workerResult.count() + "（期望 " + target.expectedCount() + "）"
                + verdict(countExpected) + "；调度敏感：" + workerResult.scheduleSensitiveCount()
                + "（期望 " + target.expectedSensitive() + "）" + verdict(sensitiveExpected)
                + "；确定性为零：" + verdict(deterministicZero));
        }
        boolean disputedOk = true;
        if (target.seed() == 2L) {
            PredictedOre disputed = findOre(workerResult, SEED2_DISPUTED_POS);
            disputedOk = disputed != null && disputed.certainty() == PredictionCertainty.SCHEDULE_SENSITIVE;
            report("    争议位置 (" + SEED2_DISPUTED_POS.getX() + "," + SEED2_DISPUTED_POS.getY() + ","
                + SEED2_DISPUTED_POS.getZ() + ")：" + (disputed == null ? "不在预测集里"
                    : disputed.certainty().displayNameCn()) + "；期望「调度敏感」" + verdict(disputedOk));
        }
        boolean pass = exact && countExpected && sensitiveExpected && deterministicZero && disputedOk;
        VERDICTS.add("【判定】" + label(target) + "：逐项 " + verdict(exact)
            + (target.expectedCount() >= 0 ? " / 候选 " + workerResult.count() + verdict(countExpected)
                + " / 敏感 " + workerResult.scheduleSensitiveCount() + verdict(sensitiveExpected) : "")
            + " / 确定性零 " + verdict(deterministicZero)
            + (target.seed() == 2L ? " / 争议格 " + verdict(disputedOk) : ""));
        if (!pass) {
            LOGGER.warn("{}：{} 对照不通过（见报告）", SeedPocConstants.LOG_KEY, label(target));
        }
        targetIndex++;
        workerResult = null;
        oracleResult = null;
        waitTicks = 0;
        stage = Stage.WORKER;
    }

    /** 提前收尾（Oracle 不可用时）。 */
    private static void abort(String summary) {
        report("**" + summary + "**");
        VERDICTS.add("【判定】Worker vs 单人 Oracle：不通过（" + summary + "）");
        finish();
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
            if (!java.util.Objects.equals(workerOre.originViewer(), oracleOre.originViewer())) {
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

    // ────────────────────────────────────────────────────────────────────────
    // 收尾
    // ────────────────────────────────────────────────────────────────────────

    /** 汇总：十目标 / 四目标的候选总数必须与 229/230 一致（口径第三十五、三十六节）。 */
    private static void summarise() {
        int total20260922 = 0;
        int total12345 = 0;
        for (Map.Entry<String, PredictionResult> entry : WORKER_BY_TARGET.entrySet()) {
            if (entry.getKey().startsWith("Seed 20260922")) {
                total20260922 += entry.getValue().count();
            } else if (entry.getKey().startsWith("Seed 12345")) {
                total12345 += entry.getValue().count();
            }
        }
        report("");
        report("二、固定集汇总（数字必须与 229/230 相同，口径第三十五、三十六节）");
        report("  Seed 20260922 十目标候选合计：" + total20260922 + "（期望 " + EXPECTED_20260922_TOTAL + "）"
            + verdict(total20260922 == EXPECTED_20260922_TOTAL));
        report("  Seed 12345 四目标候选合计：" + total12345 + "（期望 " + EXPECTED_12345_TOTAL + "）"
            + verdict(total12345 == EXPECTED_12345_TOTAL));
        boolean totalsOk = total20260922 == EXPECTED_20260922_TOTAL && total12345 == EXPECTED_12345_TOTAL;
        VERDICTS.add("【判定】固定集候选合计：20260922 = " + total20260922 + " / 12345 = " + total12345 + "："
            + verdict(totalsOk));
        report("");
        report("三、宿主 ChunkMap 查询（口径第十六、四十七节）");
        boolean hostClean = true;
        for (Map.Entry<String, PredictionResult> entry : WORKER_BY_TARGET.entrySet()) {
            int queries = entry.getValue().stats().hostChunkSourceQueries();
            hostClean &= queries == 0;
            report("  " + entry.getKey() + "：Worker 侧宿主 ChunkMap 查询 " + queries
                + (queries == 0 ? "" : " **必须为 0**"));
        }
        VERDICTS.add("【判定】Worker 预测主链宿主 ChunkMap 查询恒为 0：" + verdict(hostClean));
        report("");
        report("四、计算器诊断");
        report("  " + SERVICE.calculatorDiagnosticsCn());
        finish();
    }

    private static void finish() {
        if (reportWritten) {
            return;
        }
        reportWritten = true;
        stage = Stage.FINISHED;
        List<String> lines = new ArrayList<>();
        lines.add("《232 · 种子挖矿正式化第四阶段 · Worker vs 单人 Oracle 对照结果》");
        lines.add("对照方式：Worker 侧从客户端线程走 SeedMiningService 公开 API（与界面按钮同一条路径）；");
        lines.add("Oracle 侧在同一客户端进程里用集成服务端 ServerLevel 直接构造正式 Predictor。");
        lines.add("世界夹具：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("五、判定汇总");
        lines.addAll(VERDICTS);
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    private static String label(Target target) {
        String seedLabel = target.seed() == 20260922L ? "Seed 20260922"
            : target.seed() == 12345L ? "Seed 12345" : "Seed " + target.seed();
        return seedLabel + " (" + target.chunkX() + "," + target.chunkZ() + ")";
    }

    private static PredictedOre findOre(PredictionResult result, BlockPos position) {
        if (result == null) {
            return null;
        }
        for (PredictedOre ore : result.ores()) {
            if (ore.position().equals(position)) {
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
        LOGGER.info("{}：232对照｜{}", SeedPocConstants.LOG_KEY, line);
    }
}
