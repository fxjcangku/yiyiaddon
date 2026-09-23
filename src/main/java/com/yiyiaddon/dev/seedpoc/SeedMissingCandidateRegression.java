package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.render.SeedRenderEntry;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第六阶段（234）· <b>233 遗留「1 个 MISSING」定位装置（仅开发）</b>。
 *
 * <p><b>它回答什么</b>（口径第二十七~三十节）：233 的覆盖回归在「种子 {@code 20260922}、范围 3、
 * 争议位置所在区块 {@code (-400,380)} 一圈」实测到「候选 1100 → 已确认 1099 / 当前缺失 1」，
 * 但没把那一格取出来。本装置把那格找出来，并把口径要求的字段逐项打出来：</p>
 * <ul>
 *     <li>BlockPos / 目标区块 / 预测确定性 / 矿物种类 / 来源分类；</li>
 *     <li>写入者 {@code originViewer} / 冲突写入者清单（{@code conflictingWriters}）；</li>
 *     <li>客户端实际方块 / 该区块是否已加载 / 观察状态；</li>
 *     <li>同区块的周边候选（确认 / 缺失各几条），用来判断这是「孤立一格」还是「整片不符」。</li>
 * </ul>
 *
 * <p><b>调查原则</b>（口径第二十八节）：本装置<b>只取证、不定性</b>，且不假设 Predictor 有问题。
 * 判读顺序是：确定性是不是调度敏感 → 实际方块是空气（被挖 / 测试改过）还是别的方块
 * （worldgen 给了别的结果，属 228 已定案的合法调度差异）→ 是否能在同一世界里稳定复现。
 * 因此它额外做两件事：</p>
 * <ol>
 *     <li><b>卸载—重载复现</b>：传送到 4096 格外让该区块真的卸载，再传回来重新观察 —— 读数必须一致；</li>
 *     <li><b>重新连接复现</b>：断开并重连同一台服务器，重新观察 —— 读数必须一致（排除观察层缓存假象）。</li>
 * </ol>
 *
 * <p><b>前置条件</b>：客户端连着种子 {@code 20260922} 的专用服务器（{@code runSeedWorkerServer}，
 * 端口 25565；233 的覆盖回归当初就是在它上面取到 1100/1099/1 的），账号有 OP。</p>
 */
public final class SeedMissingCandidateRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "234遗留缺失定位";

    /** 233 覆盖回归第三段的被试位置（争议位置；覆盖中心区块 = -400,380）。 */
    private static final int CONTESTED_X = -6385;
    private static final int CONTESTED_Y = -59;
    private static final int CONTESTED_Z = 6085;

    /** 与 233 第三段一致的覆盖范围。 */
    private static final int RADIUS = 3;

    private static final long SEED = 20260922L;

    private static final int WAIT_WORLD_TICKS = 20 * 180;
    private static final int WAIT_COVERAGE_TICKS = 20 * 300;
    private static final int WAIT_RELOAD_TICKS = 20 * 90;

    private enum Step {
        WAIT_WORLD,
        SPECTATOR,
        TELEPORT,
        WAIT_COVERAGE,
        DUMP,
        TELEPORT_AWAY,
        WAIT_UNLOAD,
        TELEPORT_BACK,
        WAIT_RELOAD,
        RECONNECT,
        WAIT_RECONNECT,
        WAIT_RECONNECT_COVERAGE,
        DUMP_AFTER_RECONNECT,
        FINISHED
    }

    private static Step step = Step.WAIT_WORLD;
    private static int waitTicks;
    private static boolean reconnectIssued;

    /** 重连目标（断开<b>之前</b>取好的服务器键；断开后 WorldIdentity 已经拿不到了）。 */
    private static String reconnectKey = "";

    /** 首次定位到的缺失候选（复现用例要比对它）。 */
    private static BlockPos missingPos;
    private static String missingBlockId = "";
    private static OreObservationState missingState = OreObservationState.UNOBSERVED;

    /** 证据计数。 */
    private static int dumpCount;
    private static int reproduceCount;
    private static int mismatches;

    private SeedMissingCandidateRegression() {
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：装置异常，已停止", TAG, error);
            step = Step.FINISHED;
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (step == Step.FINISHED) {
            return;
        }
        if (client.player == null || client.level == null) {
            if (step == Step.WAIT_RECONNECT) {
                waitReconnect(client);
                return;
            }
            if (step != Step.WAIT_WORLD) {
                fail(client, "中途离开了世界");
                return;
            }
            if (++waitTicks > WAIT_WORLD_TICKS) {
                fail(client, "等待进入世界超时（请先跑 runSeedWorkerServer 并连上 127.0.0.1:25565）");
            }
            return;
        }
        switch (step) {
            case WAIT_WORLD -> setup(client);
            case SPECTATOR -> spectator(client);
            case TELEPORT -> teleport(client);
            case WAIT_COVERAGE -> waitCoverage(client, Step.DUMP);
            case DUMP -> dump(client);
            case TELEPORT_AWAY -> teleportAway(client);
            case WAIT_UNLOAD -> waitUnload(client);
            case TELEPORT_BACK -> teleportBack(client);
            case WAIT_RELOAD -> waitReload(client);
            case RECONNECT -> reconnect(client);
            case WAIT_RECONNECT -> waitReconnect(client);
            case WAIT_RECONNECT_COVERAGE -> waitCoverage(client, Step.DUMP_AFTER_RECONNECT);
            case DUMP_AFTER_RECONNECT -> dumpAfterReconnect(client);
            case FINISHED -> {
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────

    /** 开启种子挖矿 + 走界面同一条写入路径设置种子与范围。 */
    private static void setup(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        if (client.getSingleplayerServer() != null) {
            LOGGER.warn("{}：当前是单人世界；本装置要复现 233 的读数，请连到 127.0.0.1:25565 的专用服务器", TAG);
        }
        service.setEnabled(true);
        service.setSeedText(Long.toString(SEED));
        service.setCoverageRadius(RADIUS);
        service.setRenderPrediction(true);
        LOGGER.info("{}：世界 {}；种子 {}，范围 {}，准备传送到 233 第三段的被试位置 ({},{},{})",
                TAG, WorldIdentity.server(), SEED, RADIUS, CONTESTED_X, CONTESTED_Y, CONTESTED_Z);
        step = Step.SPECTATOR;
        waitTicks = 0;
    }

    /** 切观察者模式：目标位置在 y=-59 的石头里，生存模式会被闷死。 */
    private static void spectator(Minecraft client) {
        if (!sendCommand(client, "gamemode spectator")) {
            fail(client, "发送 gamemode 失败（需要 OP）");
            return;
        }
        step = Step.TELEPORT;
        waitTicks = 0;
    }

    private static void teleport(Minecraft client) {
        if (!sendCommand(client, "tp " + CONTESTED_X + " " + CONTESTED_Y + " " + CONTESTED_Z)) {
            fail(client, "发送 tp 失败（需要 OP）");
            return;
        }
        step = Step.WAIT_COVERAGE;
        waitTicks = 0;
    }

    /** 等覆盖铺满且全部候选都被观察过（与 233 第三段同一读数口径）。 */
    private static void waitCoverage(Minecraft client, Step next) {
        SeedMiningService service = SeedMiningService.instance();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        int unobserved = service.renderSnapshot().stats().unobserved();
        if (total == 0 || done < total || unobserved != 0) {
            if (++waitTicks > WAIT_COVERAGE_TICKS) {
                fail(client, "等待覆盖铺满 / 观察齐全超时（已预测 " + done + "/" + total + "，未观察 " + unobserved + "）");
            }
            return;
        }
        step = next;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────

    /** 定位并逐字段打印全部「当前缺失」候选。 */
    private static void dump(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedRenderSnapshot snapshot = service.renderSnapshot();
        SeedRenderSnapshot.Stats stats = snapshot.stats();
        dumpCount++;
        LOGGER.info("{}：定位证据 #{} → 范围 {}（{} 个目标区块）：已预测 {} 个区块 / 候选 {} 个"
                        + "（未观察 {} / 已确认 {} / 当前缺失 {} / 调度敏感 {}）",
                TAG, dumpCount, RADIUS, service.coverageTargetCount(), stats.predictedChunks(),
                stats.candidates(), stats.unobserved(), stats.confirmed(), stats.missing(),
                stats.scheduleSensitive());

        List<SeedRenderEntry> missingEntries = new ArrayList<>();
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (entry.state() == OreObservationState.MISSING) {
                missingEntries.add(entry);
            }
        }
        if (missingEntries.isEmpty()) {
            LOGGER.info("{}：本次没有「当前缺失」候选（233 记录的 1100/1099/1 未复现，需在报告里如实记录）", TAG);
            afterFirstDump(client);
            return;
        }
        for (SeedRenderEntry entry : missingEntries) {
            BlockPos pos = entry.position();
            PredictedOre ore = findPredicted(service, pos);
            ChunkPos chunk = ChunkPos.containing(pos);
            boolean loaded = isChunkLoaded(client.level, chunk);
            String blockId = service.observedBlockId(pos);
            LOGGER.info("{}：MISSING 候选 ({},{},{}) —— 目标区块 ({},{}) / 预测确定性「{}」/ 矿物 {} / "
                            + "来源 {} / 写入者 {} / 冲突写入者 {} 个 / 实际方块 {} / 区块已加载 {} / 观察状态「{}」",
                    TAG, pos.getX(), pos.getY(), pos.getZ(), chunk.x(), chunk.z(),
                    entry.certainty().displayNameCn(), entry.oreType().displayNameCn(),
                    ore == null ? "（预测记录未命中）" : ore.source().displayNameCn(),
                    ore == null || ore.originViewer() == null ? "无"
                            : "(" + ore.originViewer().x() + "," + ore.originViewer().z() + ")",
                    ore == null ? -1 : ore.conflictingWriters().size(),
                    blockId.isBlank() ? "（未知）" : blockId, loaded, entry.state().displayNameCn());
            if (ore != null && !ore.conflictingWriters().isEmpty()) {
                StringBuilder writers = new StringBuilder();
                for (ChunkPos writer : ore.conflictingWriters()) {
                    writers.append(" (").append(writer.x()).append(',').append(writer.z()).append(')');
                }
                LOGGER.info("{}：MISSING 候选冲突写入者清单{}", TAG, writers);
            }
            logChunkNeighbours(service, chunk);
            if (missingPos == null) {
                missingPos = pos;
                missingBlockId = blockId;
                missingState = entry.state();
            }
        }
        afterFirstDump(client);
    }

    /** 首次定位完成：决定接下来跑哪个复现用例。 */
    private static void afterFirstDump(Minecraft client) {
        if (missingPos == null) {
            // 没复现出缺失：仍然在报告里给出读数（可能是世界被改过 / 版本差异）
            finish(client);
            return;
        }
        step = Step.TELEPORT_AWAY;
        waitTicks = 0;
    }

    /** 打印该区块内的候选分布（判断「孤立一格」还是「整片不符」）。 */
    private static void logChunkNeighbours(SeedMiningService service, ChunkPos chunk) {
        int confirmed = 0;
        int missing = 0;
        int unobserved = 0;
        for (SeedRenderEntry entry : service.renderSnapshot().entries()) {
            if (!ChunkPos.containing(entry.position()).equals(chunk)) {
                continue;
            }
            switch (entry.state()) {
                case CONFIRMED -> confirmed++;
                case MISSING -> missing++;
                default -> unobserved++;
            }
        }
        LOGGER.info("{}：该区块 ({},{}) 候选分布 —— 已确认 {} / 缺失 {} / 未观察 {}（区块已加载 {}）",
                TAG, chunk.x(), chunk.z(), confirmed, missing, unobserved, true);
    }

    /** 从预测缓存里找该位置的完整记录（确定性 / 来源 / 写入者 / 冲突写入者）。 */
    private static PredictedOre findPredicted(SeedMiningService service, BlockPos pos) {
        for (PredictionResult result : service.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.position().equals(pos)) {
                    return ore;
                }
            }
        }
        return null;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 复现用例 ①：区块卸载 → 重新加载
    // ────────────────────────────────────────────────────────────────────────

    private static void teleportAway(Minecraft client) {
        int farX = missingPos.getX() + 4096;
        if (!sendCommand(client, "tp " + farX + " 100 " + missingPos.getZ())) {
            fail(client, "发送远距离 tp 失败（需要 OP）");
            return;
        }
        LOGGER.info("{}：已传送到 ({},100,{})，等目标区块卸载…", TAG, farX, missingPos.getZ());
        step = Step.WAIT_UNLOAD;
        waitTicks = 0;
    }

    private static void waitUnload(Minecraft client) {
        ChunkPos chunk = ChunkPos.containing(missingPos);
        if (isChunkLoaded(client.level, chunk)) {
            if (++waitTicks > WAIT_RELOAD_TICKS) {
                fail(client, "等待目标区块卸载超时");
            }
            return;
        }
        LOGGER.info("{}：目标区块 ({},{}) 已从客户端卸载，观察状态「{}」", TAG, chunk.x(), chunk.z(),
                SeedMiningService.instance().observationState(missingPos).displayNameCn());
        step = Step.TELEPORT_BACK;
        waitTicks = 0;
    }

    private static void teleportBack(Minecraft client) {
        if (!sendCommand(client, "tp " + missingPos.getX() + " " + missingPos.getY() + " " + missingPos.getZ())) {
            fail(client, "发送返回 tp 失败");
            return;
        }
        step = Step.WAIT_RELOAD;
        waitTicks = 0;
    }

    private static void waitReload(Minecraft client) {
        OreObservationState state = SeedMiningService.instance().observationState(missingPos);
        if (state == OreObservationState.UNOBSERVED) {
            if (++waitTicks > WAIT_RELOAD_TICKS) {
                fail(client, "区块重载后状态仍是未观察");
            }
            return;
        }
        reproduce(client, "区块卸载→重新加载", state);
        step = Step.RECONNECT;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 复现用例 ②：断开重连同一台服务器
    // ────────────────────────────────────────────────────────────────────────

    private static void reconnect(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            fail(client, "重连前拿不到连接");
            return;
        }
        // 服务器键必须在断开之前取：断开后 getCurrentServer() 已经为空，会得到 singleplayer:unknown
        reconnectKey = WorldIdentity.server();
        player.connection.getConnection().disconnect(
                net.minecraft.network.chat.Component.literal("234 遗留缺失复现：主动断开重连"));
        step = Step.WAIT_RECONNECT;
        waitTicks = 0;
    }

    private static void waitReconnect(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待重连超时");
            return;
        }
        if (client.level != null) {
            // 重连后配置按服务器隔离，重新写一遍；观察会在区块重新下发后自动重建
            SeedMiningService service = SeedMiningService.instance();
            service.setEnabled(true);
            service.setSeedText(Long.toString(SEED));
            service.setCoverageRadius(RADIUS);
            service.setRenderPrediction(true);
            // 传送回目标位置附近：重连后玩家回到出生点，先把位置拉回来才有观察样本
            sendCommand(client, "tp " + missingPos.getX() + " " + missingPos.getY() + " " + missingPos.getZ());
            step = Step.WAIT_RECONNECT_COVERAGE;
            waitTicks = 0;
            return;
        }
        if (!reconnectIssued) {
            reconnectIssued = true;
            LOGGER.info("{}：已断开，重新连接同一台服务器（{}）", TAG, reconnectKey);
            net.minecraft.client.gui.screens.ConnectScreen.startConnecting(
                    new net.minecraft.client.gui.screens.TitleScreen(), client,
                    net.minecraft.client.multiplayer.resolver.ServerAddress.parseString(reconnectKey),
                    new net.minecraft.client.multiplayer.ServerData("234 reconnect", reconnectKey,
                            net.minecraft.client.multiplayer.ServerData.Type.OTHER),
                    false, null);
        }
    }

    private static void dumpAfterReconnect(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedRenderSnapshot.Stats stats = service.renderSnapshot().stats();
        LOGGER.info("{}：重连后读数 —— 候选 {}（未观察 {} / 已确认 {} / 缺失 {}），缓存 {} 个区块",
                TAG, stats.candidates(), stats.unobserved(), stats.confirmed(), stats.missing(),
                service.cachedChunkCount());
        OreObservationState state = service.observationState(missingPos);
        reproduce(client, "断开重连同一服务器", state);
        finish(client);
    }

    /** 复现核对：同一位置、同一服务器，读数必须与首次一致。 */
    private static void reproduce(Minecraft client, String what, OreObservationState state) {
        String blockId = SeedMiningService.instance().observedBlockId(missingPos);
        reproduceCount++;
        boolean same = state == missingState && blockId.equals(missingBlockId);
        if (!same) {
            mismatches++;
        }
        LOGGER.info("{}：复现证据 #{}（{}）→ ({},{},{}) 实际方块 {} / 观察状态「{}」；"
                        + "首次为 {} / 「{}」{}",
                TAG, reproduceCount, what, missingPos.getX(), missingPos.getY(), missingPos.getZ(),
                blockId.isBlank() ? "（未知）" : blockId, state.displayNameCn(),
                missingBlockId.isBlank() ? "（未知）" : missingBlockId, missingState.displayNameCn(),
                same ? "（一致）" : "（**不一致**）");
    }

    // ────────────────────────────────────────────────────────────────────────

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LOGGER.info("{}：====== 结果汇总 ======", TAG);
        if (missingPos == null) {
            LOGGER.info("{}：本次没有定位到「当前缺失」候选（233 的 1100/1099/1 未复现）", TAG);
        } else {
            LOGGER.info("{}：缺失候选 ({},{},{})：实际方块 {}（若为 air = 被挖 / 测试改过；"
                            + "若为石头 / 深板岩 / 沙砾等 = worldgen 给了别的结果）",
                    TAG, missingPos.getX(), missingPos.getY(), missingPos.getZ(),
                    missingBlockId.isBlank() ? "（未知）" : missingBlockId);
        }
        LOGGER.info("{}：定位证据 {} 条 / 复现证据 {} 条 / 不一致 {} 条", TAG, dumpCount, reproduceCount, mismatches);
        LOGGER.info("{}：最终读数 {}", TAG, service.runtimeDiagnosticsCn());
        service.setRenderPrediction(false);
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        LOGGER.error("{}：用例失败 —— {}", TAG, reason);
        LOGGER.error("{}：失败时读数 {}", TAG, SeedMiningService.instance().runtimeDiagnosticsCn());
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
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

    /** 该区块是否真的加载在客户端（判据与观察器一致：loadOrGenerate=false，绝不触发加载）。 */
    private static boolean isChunkLoaded(ClientLevel level, ChunkPos chunk) {
        if (level == null) {
            return false;
        }
        ClientChunkCache cache = level.getChunkSource();
        return cache.getChunk(chunk.x(), chunk.z(), ChunkStatus.FULL, false) != null;
    }
}
