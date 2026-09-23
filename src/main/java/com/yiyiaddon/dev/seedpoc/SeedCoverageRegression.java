package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.render.SeedRenderEntry;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第五阶段（233）· <b>覆盖 / 观察组合回归装置（仅开发）</b>。
 *
 * <p>与 {@link ObservationRenderRegression}（生命周期：已确认 / 当前缺失 / 恢复 / 卸载）分工不同，
 * 本装置在<b>同一台真实专用服务器</b>上顺序跑三段，回答三个单独的问题：</p>
 * <ol>
 *     <li><b>错误 Seed</b>（口径第四十八节）：客户端填的种子与服务器世界不是同一颗时，
 *         观察结果必须是「大量当前缺失」，而且<b>一条「可疑」都不能有</b> ——
 *         缺失只能说明「预测位置当前实际不是钻石」，不能得出「假矿 / 服务器作弊 / 种子错」；</li>
 *     <li><b>调度敏感 × 观察</b>（口径第十三、三十六节）：冻结口径里的争议位置
 *         （种子 2 · {@code (-6385,-59,6085)}）在真实世界里被观察之后，两个维度必须<b>各自保留</b>
 *         ——确定性仍是「调度敏感」，观察状态照实给，绝不因为看到了/没看到就把调度敏感改写成可疑；</li>
 *     <li><b>默认覆盖规模与渲染开销</b>（口径第十七、二十三节）：默认半径 3（7×7=49 目标区块）下
 *         覆盖进度能否铺满、候选总量多少、渲染快照重建要花多少 CPU。</li>
 * </ol>
 *
 * <p><b>为什么三段能同一次跑完</b>：三段都只改客户端侧设置（种子 / 范围 / 玩家位置），
 * 服务器始终是同一台（种子 {@code 20260922}）—— 这恰好就是真实玩家的用法：换种子、换地方、
 * 换范围，服务器一动不动。</p>
 *
 * <p><b>前置条件</b>：客户端连着种子 {@code 20260922} 的专用服务器，账号有 OP
 * （第二段要 {@code /gamemode} 与 {@code /tp}）。参数走系统属性，不落盘、不进正式产物。</p>
 */
public final class SeedCoverageRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "233覆盖回归";

    /** 第一段的「错误种子」：与服务器世界（20260922）不是同一颗。 */
    private static final String MISMATCH_SEED = "12345";

    /** 第二段的调度敏感被试：冻结口径的种子 2 与争议位置。 */
    private static final String SENSITIVE_SEED = "2";
    private static final int CONTESTED_X = -6385;
    private static final int CONTESTED_Y = -59;
    private static final int CONTESTED_Z = 6085;

    /** 第三段的覆盖范围：口径第十七节的默认值 3（7×7 = 49 个目标区块）。 */
    private static final int PERF_RADIUS = 3;

    private static final int WAIT_WORLD_TICKS = 20 * 120;
    private static final int WAIT_OBSERVE_TICKS = 20 * 90;
    private static final int WAIT_COVERAGE_TICKS = 20 * 300;

    private enum Phase {
        WAIT_WORLD,
        A_WAIT,
        B_PREPARE,
        B_SPECTATOR,
        B_TELEPORT,
        B_WAIT_PREDICTED,
        B_WAIT_OBSERVED,
        C_PREPARE,
        C_WAIT,
        C_MEASURE,
        FINISHED
    }

    private static Phase phase = Phase.WAIT_WORLD;
    private static int waitTicks;

    /** Phase B 是否已经打过一次取样诊断。 */
    private static boolean sampledB;

    /** 第三段的窗口基线（快照重建次数与累计耗时）。 */
    private static long perfBuildBaseline;
    private static long perfNanosBaseline;

    /** 证据计数。 */
    private static int mismatchSeen;
    private static int sensitiveSeen;
    private static int perfSeen;

    private SeedCoverageRegression() {
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：回归装置异常，已停止", TAG, error);
            phase = Phase.FINISHED;
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (phase == Phase.FINISHED) {
            return;
        }
        if (client.player == null || client.level == null) {
            if (phase != Phase.WAIT_WORLD) {
                fail(client, "中途离开了世界");
                return;
            }
            if (++waitTicks > WAIT_WORLD_TICKS) {
                fail(client, "等待进入世界超时（请先连上种子 20260922 的测试服务器）");
            }
            return;
        }
        switch (phase) {
            case WAIT_WORLD -> prepareA(client);
            case A_WAIT -> waitMismatch(client);
            case B_PREPARE -> prepareB(client);
            case B_SPECTATOR -> enterSpectator(client);
            case B_TELEPORT -> teleportToContested(client);
            case B_WAIT_PREDICTED -> waitContestedPredicted(client);
            case B_WAIT_OBSERVED -> waitContestedObserved(client);
            case C_PREPARE -> prepareC(client);
            case C_WAIT -> waitCoverageFull(client);
            case C_MEASURE -> measure(client);
            case FINISHED -> {
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第一段：错误 Seed → 大量「当前缺失」，且绝不产生「可疑」
    // ────────────────────────────────────────────────────────────────────────

    private static void prepareA(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setEnabled(true);
        service.setRenderPrediction(true);
        service.setCoverageRadius(1);
        // 走界面同一条写入路径：种子输入框填什么，这里就填什么
        service.setSeedText(MISMATCH_SEED);
        LOGGER.info("{}：第一段开跑 —— 客户端种子填 {}（服务器世界种子 20260922），范围 1，"
                + "预期结果：候选几乎全部是「当前缺失」，且一条「可疑」都不该出现", TAG, MISMATCH_SEED);
        phase = Phase.A_WAIT;
        waitTicks = 0;
    }

    private static void waitMismatch(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedRenderSnapshot snapshot = service.renderSnapshot();
        if (snapshot.empty()) {
            if (++waitTicks > WAIT_OBSERVE_TICKS) {
                fail(client, "等待错误种子下的预测落地超时");
            }
            return;
        }
        SeedRenderSnapshot.Stats stats = snapshot.stats();
        if (stats.unobserved() != 0) {
            // 玩家出生点周围这一圈区块是已经加载的，不该有「未观察」
            if (++waitTicks > WAIT_OBSERVE_TICKS) {
                fail(client, "候选迟迟没有从「未观察」变成实际观察结果（仍有 " + stats.unobserved() + " 条）");
            }
            return;
        }
        if (stats.candidates() == 0) {
            fail(client, "错误种子下没有任何候选（预期 12345 在出生点区块应有候选）");
            return;
        }
        int suspicious = 0;
        BlockPos sample = null;
        String sampleBlock = "";
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (entry.state() == OreObservationState.SUSPICIOUS) {
                suspicious++;
            }
            if (sample == null) {
                sample = entry.position();
                sampleBlock = service.observedBlockId(entry.position());
            }
        }
        if (suspicious != 0) {
            fail(client, "错误种子用例里出现了 " + suspicious + " 条「可疑」—— 233 不允许自动产生可疑");
            return;
        }
        if (stats.missing() == 0) {
            fail(client, "错误种子下居然一条「当前缺失」都没有（候选 " + stats.candidates()
                    + " / 已确认 " + stats.confirmed() + "），用例前提不成立");
            return;
        }
        mismatchSeen++;
        LOGGER.info("{}：错误种子证据 #{} → 客户端种子 {} / 服务器世界种子 20260922：候选 {} 个 → "
                        + "已确认 {} / 当前缺失 {} / 未观察 {}（候选文案「{}」；全程无「可疑」）",
                TAG, mismatchSeen, MISMATCH_SEED, stats.candidates(), stats.confirmed(), stats.missing(),
                stats.unobserved(), OreObservationState.MISSING.displayNameCn());
        LOGGER.info("{}：错误种子抽样 → ({},{},{}) 实际方块 {}，观察状态「{}」（只说「预测位置当前实际不是钻石」，"
                        + "不下「种子错 / 假矿」结论）",
                TAG, sample.getX(), sample.getY(), sample.getZ(),
                sampleBlock.isBlank() ? "（未知）" : sampleBlock,
                service.observationState(sample).displayNameCn());
        phase = Phase.B_PREPARE;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第二段：调度敏感候选 × 观察（两个维度各自保留）
    // ────────────────────────────────────────────────────────────────────────

    private static void prepareB(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setCoverageRadius(1);
        service.setSeedText(SENSITIVE_SEED);
        LOGGER.info("{}：第二段开跑 —— 种子改为 {}，准备传送到冻结口径的争议位置 ({},{},{})",
                TAG, SENSITIVE_SEED, CONTESTED_X, CONTESTED_Y, CONTESTED_Z);
        phase = Phase.B_SPECTATOR;
        waitTicks = 0;
    }

    /** 先切成观察者模式：争议位置在 y=-59 的石头里，生存模式会被闷死，取证据不需要冒这个险。 */
    private static void enterSpectator(Minecraft client) {
        if (!sendCommand(client, "gamemode spectator")) {
            fail(client, "发送 gamemode 失败（需要 OP）");
            return;
        }
        phase = Phase.B_TELEPORT;
        waitTicks = 0;
    }

    private static void teleportToContested(Minecraft client) {
        if (!sendCommand(client, "tp " + CONTESTED_X + " " + CONTESTED_Y + " " + CONTESTED_Z)) {
            fail(client, "发送 tp 失败（需要 OP）");
            return;
        }
        phase = Phase.B_WAIT_PREDICTED;
        waitTicks = 0;
    }

    /** 等争议位置所在区块拿到正式预测结果（覆盖调度会先算玩家所在区块）。 */
    private static void waitContestedPredicted(Minecraft client) {
        SeedRenderEntry entry = contestedEntry();
        if (entry != null) {
            if (entry.certainty() != PredictionCertainty.SCHEDULE_SENSITIVE) {
                fail(client, "争议位置 (" + CONTESTED_X + "," + CONTESTED_Y + "," + CONTESTED_Z
                        + ") 的确定性是「" + entry.certainty().displayNameCn() + "」，冻结口径要求「调度敏感」");
                return;
            }
            LOGGER.info("{}：调度敏感证据（预测侧）→ ({},{},{}) 确定性「{}」（种子 {}；与冻结口径一致）",
                    TAG, CONTESTED_X, CONTESTED_Y, CONTESTED_Z, entry.certainty().displayNameCn(), SENSITIVE_SEED);
            phase = Phase.B_WAIT_OBSERVED;
            waitTicks = 0;
            return;
        }
        // 等不到时先取样一次，把「玩家在哪、该区块预测到了什么」如实打出来，便于定位
        if (!sampledB && waitTicks >= 100) {
            sampledB = true;
            logContestedSample(client);
        }
        if (++waitTicks > WAIT_OBSERVE_TICKS) {
            fail(client, "等待争议位置所在区块的预测结果超时（缓存 "
                    + SeedMiningService.instance().cachedChunkCount() + " 个区块）");
        }
    }

    /** Phase B 的一次性取样：玩家区块 + 该区块内的候选 + 全快照里的调度敏感条目。 */
    private static void logContestedSample(Minecraft client) {
        LocalPlayer player = client.player;
        ChunkPos playerChunk = ChunkPos.containing(player.blockPosition());
        SeedRenderSnapshot snapshot = SeedMiningService.instance().renderSnapshot();
        int inChunk = 0;
        StringBuilder sample = new StringBuilder();
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (!ChunkPos.containing(entry.position()).equals(playerChunk)) {
                continue;
            }
            inChunk++;
            if (inChunk <= 6) {
                sample.append("\n    · (").append(entry.position().getX()).append(',')
                        .append(entry.position().getY()).append(',')
                        .append(entry.position().getZ()).append(") ")
                        .append(entry.certainty().displayNameCn());
            }
        }
        StringBuilder sensitive = new StringBuilder();
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (entry.certainty() == PredictionCertainty.SCHEDULE_SENSITIVE) {
                sensitive.append("\n    · (").append(entry.position().getX()).append(',')
                        .append(entry.position().getY()).append(',')
                        .append(entry.position().getZ()).append(')');
            }
        }
        LOGGER.info("{}：取样 —— 玩家 ({},{},{}) 所在区块 ({},{})，该区块内候选 {} 条；快照 {}",
                TAG, player.blockPosition().getX(), player.blockPosition().getY(),
                player.blockPosition().getZ(), playerChunk.x(), playerChunk.z(), inChunk,
                snapshot.describeCn());
        LOGGER.info("{}：取样 · 该区块候选（最多 6 条）{}", TAG,
                sample.isEmpty() ? "（无）" : sample.toString());
        LOGGER.info("{}：取样 · 快照里的调度敏感条目{}", TAG,
                sensitive.isEmpty() ? "（无）" : sensitive.toString());
    }

    /** 等该位置真的被观察（区块加载）后核对：观察状态照实给，确定性仍是调度敏感，绝不因此变成可疑。 */
    private static void waitContestedObserved(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        BlockPos pos = contestedPos();
        OreObservationState state = service.observationState(pos);
        if (state == OreObservationState.UNOBSERVED) {
            if (++waitTicks > WAIT_OBSERVE_TICKS) {
                fail(client, "等待争议位置被服务器下发并观察超时（区块是否已加载）");
            }
            return;
        }
        if (state == OreObservationState.SUSPICIOUS) {
            fail(client, "争议位置被标成「可疑」—— 调度敏感绝不等于假矿（口径第十三、三十六节）");
            return;
        }
        SeedRenderEntry entry = contestedEntry();
        if (entry == null || entry.certainty() != PredictionCertainty.SCHEDULE_SENSITIVE) {
            fail(client, "观察之后确定性被改掉了（应为「调度敏感」，实际 "
                    + (entry == null ? "条目消失" : "「" + entry.certainty().displayNameCn() + "」"));
            return;
        }
        sensitiveSeen++;
        LOGGER.info("{}：调度敏感证据 #{}（观察侧）→ ({},{},{}) 实际方块 {}，观察状态「{}」；"
                        + "确定性仍为「{}」（两个维度各自保留，未产生「可疑」）",
                TAG, sensitiveSeen, CONTESTED_X, CONTESTED_Y, CONTESTED_Z,
                blank(service.observedBlockId(pos)), state.displayNameCn(),
                entry.certainty().displayNameCn());
        LOGGER.info("{}：第二段所在区块预测总览 {}",
                TAG, service.renderSnapshot().stats().scheduleSensitive() == 0
                        ? "（本区块无调度敏感候选）" : "（本区块调度敏感候选 "
                        + service.renderSnapshot().stats().scheduleSensitive() + " 条）");
        phase = Phase.C_PREPARE;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第三段：默认范围 3 的覆盖规模与渲染开销
    // ────────────────────────────────────────────────────────────────────────

    private static void prepareC(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setSeedText("20260922");
        service.setCoverageRadius(PERF_RADIUS);
        perfBuildBaseline = service.snapshotBuildCount();
        perfNanosBaseline = service.snapshotBuildTotalNanos();
        LOGGER.info("{}：第三段开跑 —— 种子回到 20260922，范围设为默认 {}（目标 {} 个区块），"
                        + "等待铺满并采集渲染开销",
                TAG, PERF_RADIUS, (2 * PERF_RADIUS + 1) * (2 * PERF_RADIUS + 1));
        phase = Phase.C_WAIT;
        waitTicks = 0;
    }

    private static void waitCoverageFull(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        if (total == 0 || done < total) {
            if (++waitTicks > WAIT_COVERAGE_TICKS) {
                fail(client, "等待范围 " + PERF_RADIUS + " 铺满超时（" + done + " / " + total + " 个区块）");
            }
            return;
        }
        phase = Phase.C_MEASURE;
        waitTicks = 0;
    }

    /** 在「刚铺满」这一刻取材：规模读数 + 窗口内的快照重建成本 + 分配量 + FPS。 */
    private static void measure(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedRenderSnapshot snapshot = service.renderSnapshot();
        SeedRenderSnapshot.Stats stats = snapshot.stats();
        long builds = service.snapshotBuildCount() - perfBuildBaseline;
        long nanos = service.snapshotBuildTotalNanos() - perfNanosBaseline;
        double micros = builds <= 0 ? -1d : nanos / 1000d / builds;
        perfSeen++;
        LOGGER.info("{}：渲染开销证据 #{} → 范围 {}（{} 个目标区块）：已预测 {} 个区块 / 候选 {} 个"
                        + "（未观察 {} / 已确认 {} / 当前缺失 {} / 调度敏感 {}）",
                TAG, perfSeen, PERF_RADIUS, service.coverageTargetCount(), stats.predictedChunks(),
                stats.candidates(), stats.unobserved(), stats.confirmed(), stats.missing(),
                stats.scheduleSensitive());
        LOGGER.info("{}：快照重建读数 → 本窗口重建 {} 次、平均每次 {} 微秒；每次固定分配 = {} 个候选各一条"
                        + "SeedRenderEntry + 1 次列表副本 + 1 个计数 + 1 个快照；当前 FPS {}；缓存 {}/{} 个区块",
                TAG, builds, micros < 0 ? "（未采样到）" : String.format("%.1f", micros),
                stats.candidates(), client.getFps(),
                service.cachedChunkCount(), service.cachedChunkLimit());
        finish(client);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 收尾与工具
    // ────────────────────────────────────────────────────────────────────────

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LOGGER.info("{}：====== 结果汇总 ======", TAG);
        LOGGER.info("{}：错误种子证据 {} 条 / 调度敏感观察证据 {} 条 / 渲染开销证据 {} 条",
                TAG, mismatchSeen, sensitiveSeen, perfSeen);
        LOGGER.info("{}：最终读数 {}", TAG, service.runtimeDiagnosticsCn());
        LOGGER.info("{}：关闭显示预测钻石（顺带核对关闭即清空）", TAG);
        service.setRenderPrediction(false);
        LOGGER.info("{}：关闭后 缓存 {} 个区块 / 渲染快照 {}",
                TAG, service.cachedChunkCount(), service.renderSnapshot().describeCn());
        phase = Phase.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        LOGGER.error("{}：用例失败 —— {}", TAG, reason);
        LOGGER.error("{}：失败时读数 {}", TAG, SeedMiningService.instance().runtimeDiagnosticsCn());
        phase = Phase.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static BlockPos contestedPos() {
        return new BlockPos(CONTESTED_X, CONTESTED_Y, CONTESTED_Z);
    }

    /** 渲染快照里争议位置那一条（未预测到时为 {@code null}）。 */
    private static SeedRenderEntry contestedEntry() {
        for (SeedRenderEntry entry : SeedMiningService.instance().renderSnapshot().entries()) {
            if (entry.position().getX() == CONTESTED_X && entry.position().getY() == CONTESTED_Y
                    && entry.position().getZ() == CONTESTED_Z) {
                return entry;
            }
        }
        return null;
    }

    /** 发一条服务端指令（开发期用例专用：本装置只在带 OP 的测试服上跑）。 */
    private static boolean sendCommand(Minecraft client, String command) {
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            return false;
        }
        LOGGER.info("{}：发送指令 /{}", TAG, command);
        player.connection.sendCommand(command);
        return true;
    }

    private static String blank(String text) {
        return text == null || text.isBlank() ? "（未知）" : text;
    }
}
