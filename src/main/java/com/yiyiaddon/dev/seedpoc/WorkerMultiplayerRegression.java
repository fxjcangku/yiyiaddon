package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿第四阶段（报告 232）· <b>真正多人服务器（Dedicated Multiplayer）验收装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>（口径第四十八~五十二节）：本阶段的产品目标是「普通多人服务器客户端」——
 * 也就是 {@code Minecraft#getSingleplayerServer() == null} 的那种环境。本装置在<b>客户端</b>里逐条核对：</p>
 *
 * <ol>
 *   <li>当前确实是多人环境（没有集成服务端）；</li>
 *   <li>种子挖矿仍能进入可预测状态，并跑出与单人 Oracle 相同的数字（Seed 20260922 区块 (0,0) = 45）；</li>
 *   <li>预测<b>远端未加载区块</b>同样成立（客户端 ChunkCache 里根本没有那个区块）；</li>
 *   <li>预测主链一次都没有去读真实世界（宿主 ChunkMap 查询为 0）；</li>
 *   <li>客户端也没有向 Worker 提供任何远端真实区块数据（架构上不可能：只传 种子 / 维度 / 区块坐标）。</li>
 * </ol>
 *
 * <p><b>配套的服务端</b>：Gradle 任务 {@code runSeedWorkerServer} 起的 26.1.2 专用服务器
 * （{@code level-seed=20260922}，不加载任何改 worldgen 的 Mod）；客户端由
 * {@code runClientSeedWorkerMultiplayerTest} 用 {@code --quickPlayMultiplayer} 自动连上去。</p>
 *
 * <p><b>触发方式</b>：{@code -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.workerMultiplayer=1
 * -Dyiyiaddon.seedpoc.exit=1}</p>
 */
public final class WorkerMultiplayerRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-232-多人验收.txt";

    /** 单次预测等待上限（客户端刻）。 */
    private static final int WAIT_TIMEOUT_TICKS = 20 * 180;

    /** 专用服务器世界的种子（同时也是本装置手动填写的预测种子）。 */
    private static final long DEDICATED_SEED = 20260922L;

    /** 近端目标（口径第五十一节：必须 45）。 */
    private static final ChunkPos NEAR_TARGET = new ChunkPos(0, 0);
    private static final int NEAR_EXPECTED = 45;

    /** 远端目标（口径第四十八节：距离玩家极远，服务端绝对没有发给客户端）。 */
    private static final ChunkPos FAR_TARGET = new ChunkPos(-400, 380);

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    private enum Stage {
        /** 等连上多人服务器。 */
        CONNECT,
        /** 近端目标预测。 */
        PREDICT_NEAR,
        /** 远端未加载区块预测。 */
        PREDICT_FAR,
        FINISHED
    }

    private static Stage stage = Stage.CONNECT;
    private static int waitTicks;
    private static boolean submitted;

    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();

    /** 收尾只允许执行一次（报告落盘 + 自动退出）。 */
    private static boolean reportWritten;

    private WorkerMultiplayerRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在多人验收模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (stage) {
                case CONNECT -> tickConnect(client);
                case PREDICT_NEAR -> tickPredict(client, NEAR_TARGET, NEAR_EXPECTED, true);
                case PREDICT_FAR -> tickPredict(client, FAR_TARGET, -1, false);
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            LOGGER.error("{}：多人验收装置中断", SeedPocConstants.LOG_KEY, error);
            REPORT.add("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            finish();
        }
    }

    private static void tickConnect(Minecraft client) {
        if (client.level == null || client.player == null) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                REPORT.add("**等待进入世界超时（请确认已自动连接到专用服务器）**");
                VERDICTS.add("【判定】多人环境：不通过（未进入世界）");
                finish();
            }
            return;
        }
        boolean multiplayer = client.getSingleplayerServer() == null;
        REPORT.add("零、环境");
        REPORT.add("  世界维度：" + client.level.dimension().identifier());
        REPORT.add("  Minecraft#getSingleplayerServer()：" + (multiplayer ? "null" : "非 null")
            + "（必须为 null 才是真正的多人环境）" + verdict(multiplayer));
        REPORT.add("  玩家位置区块：" + playerChunk(client).x() + "," + playerChunk(client).z());
        REPORT.add("  本地世界生成计算器：" + SERVICE.calculatorStateCn() + "（本阶段宿主由它提供）");
        VERDICTS.add("【判定】多人环境 getSingleplayerServer() == null：" + verdict(multiplayer));
        if (!multiplayer) {
            REPORT.add("**这不是多人环境：本轮多人验收不成立**");
            finish();
            return;
        }
        SERVICE.setSeedText(String.valueOf(DEDICATED_SEED));
        SERVICE.setEnabled(true);
        REPORT.add("");
        REPORT.add("一、近端目标（口径第五十一节：Seed " + DEDICATED_SEED + " 区块 "
            + NEAR_TARGET.x() + "," + NEAR_TARGET.z() + " 必须 " + NEAR_EXPECTED + "）");
        stage = Stage.PREDICT_NEAR;
        waitTicks = 0;
        submitted = false;
    }

    private static void tickPredict(Minecraft client, ChunkPos target, int expectedCount, boolean near) {
        if (!submitted) {
            ChunkPos playerChunk = playerChunk(client);
            boolean loaded = hasChunk(client, target);
            REPORT.add("  目标区块 (" + target.x() + "," + target.z() + ")：玩家在 (" + playerChunk.x() + ","
                + playerChunk.z() + ")；客户端是否已有该区块：" + (loaded ? "有" : "没有")
                + (near ? "（近端目标，通常已加载）" : "（远端目标，必须没有）"));
            SERVICE.predictChunk(target.x(), target.z());
            if (!SERVICE.predicting()) {
                REPORT.add("  **未能启动预测**（状态 " + SERVICE.stateCn() + "）");
                VERDICTS.add("【判定】" + label(target) + "：不通过（未启动）");
                advance();
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                REPORT.add("  **等待预测超时（" + (WAIT_TIMEOUT_TICKS / 20) + " 秒）**");
                VERDICTS.add("【判定】" + label(target) + "：不通过（超时）");
                advance();
            }
            return;
        }
        submitted = false;
        PredictionResult result = SERVICE.lastResult();
        if (result == null) {
            REPORT.add("  **结果为空**");
            VERDICTS.add("【判定】" + label(target) + "：不通过（结果为空）");
            advance();
            return;
        }
        boolean success = result.success();
        boolean countOk = expectedCount < 0 || result.count() == expectedCount;
        boolean hostClean = result.stats().hostChunkSourceQueries() == 0;
        boolean targetOk = result.request().chunk().equals(target);
        REPORT.add("  结果：成功=" + success + "；候选 " + result.count()
            + (expectedCount >= 0 ? "（期望 " + expectedCount + "）" : "") + "；调度敏感 "
            + result.scheduleSensitiveCount() + "；未解析 " + result.unresolvedCount() + "；确定性 "
            + result.deterministicCount() + "；耗时 " + result.elapsedMillis() + " ms；缓存 "
            + result.stats().heldChunks() + "；宿主 ChunkMap 查询 "
            + result.stats().hostChunkSourceQueries() + "（必须为 0）");
        REPORT.add("  目标区块一致性：" + (result.request().chunk().x() + "," + result.request().chunk().z())
            + "（期望 " + target.x() + "," + target.z() + "）" + verdict(targetOk));
        if (!near) {
            boolean notLoaded = !hasChunk(client, target);
            REPORT.add("  远端目标在客户端是否仍未加载：" + (notLoaded ? "仍未加载（正确）" : "**已加载**")
                + verdict(notLoaded));
            VERDICTS.add("【判定】远端未加载区块可预测：" + verdict(success && notLoaded && hostClean));
        }
        VERDICTS.add("【判定】" + label(target) + "：成功 " + verdict(success) + " / 候选 " + result.count()
            + verdict(countOk) + " / 目标一致 " + verdict(targetOk) + " / 宿主查询为 0 " + verdict(hostClean));
        advance();
    }

    private static void advance() {
        if (stage == Stage.PREDICT_NEAR) {
            REPORT.add("");
            REPORT.add("二、远端未加载区块（口径第四十八节：预测与「服务端有没有发过这个区块」无关）");
            stage = Stage.PREDICT_FAR;
            waitTicks = 0;
            submitted = false;
            return;
        }
        finish();
    }

    private static void finish() {
        if (reportWritten) {
            return;
        }
        reportWritten = true;
        stage = Stage.FINISHED;
        List<String> lines = new ArrayList<>();
        lines.add("《232 · 种子挖矿正式化第四阶段 · 真正多人服务器验收结果》");
        lines.add("环境：Minecraft 26.1.2 + 本地专用服务器（level-seed=" + DEDICATED_SEED + "）；");
        lines.add("客户端：真实客户端进程，由 --quickPlayMultiplayer 自动连接，");
        lines.add("       预测时客户端没有任何集成服务端（Minecraft#getSingleplayerServer() == null）。");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("三、判定汇总");
        lines.addAll(VERDICTS);
        lines.add("");
        lines.add("四、计算器诊断");
        lines.add("  " + SERVICE.calculatorDiagnosticsCn());
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    private static ChunkPos playerChunk(Minecraft client) {
        return client.player == null ? new ChunkPos(0, 0)
            : new ChunkPos(client.player.blockPosition().getX() >> 4, client.player.blockPosition().getZ() >> 4);
    }

    /** 客户端 ChunkCache 里有没有这个区块（`false` = 不要为它加载 / 生成）。 */
    private static boolean hasChunk(Minecraft client, ChunkPos target) {
        if (client.level == null) {
            return false;
        }
        return client.level.getChunkSource().getChunk(target.x(), target.z(), ChunkStatus.FULL, false) != null;
    }

    private static String label(ChunkPos target) {
        return "多人目标 (" + target.x() + "," + target.z() + ")";
    }

    private static String verdict(boolean ok) {
        return ok ? "（通过）" : "（**不通过**）";
    }
}
