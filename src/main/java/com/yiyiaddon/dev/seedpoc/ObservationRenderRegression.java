package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.render.SeedRenderEntry;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第五阶段（233）· <b>实际 Chunk 观察与渲染的实机回归装置（仅开发）</b>。
 *
 * <p>它验证的是 233 口径第四十四~四十七节要求的四件事，全部在<b>真实客户端 + 真实服务器</b>上跑
 * （不是离线装置）：</p>
 * <ol>
 *     <li><b>CONFIRMED</b>：让客户端合法加载某个候选所在区块，读出真实方块确实就是钻石矿；</li>
 *     <li><b>MISSING</b>：用 dev-only 服务端指令把该位置改成空气，客户端收到方块更新后
 *         状态必须变成「当前缺失」，<b>不重跑 Predictor、不重启 Worker</b>；</li>
 *     <li><b>恢复</b>：再改回钻石矿，状态必须回到「已确认」（证明观察取自真实 Chunk 层）；</li>
 *     <li><b>Chunk unload</b>：远距离传送让客户端卸载该区块，状态必须回到「未观察」；
 *         返回后重新加载，状态必须重新变成「已确认」/「当前缺失」。</li>
 * </ol>
 *
 * <p><b>为什么必须实机</b>：这四条全部依赖「服务器真的把区块与方块更新发给了客户端」，
 * 离线装置里没有客户端 ChunkCache、没有 ClientChunkEvents、没有方块更新包，
 * 造不出可信证据（口径第四十四节明确要求真实 PredictedOre 坐标上做）。</p>
 *
 * <p><b>前置条件</b>：客户端连着一个种子为 {@code 20260922} 的服务器，且该账号有 OP
 * （要发 {@code /setblock} 与 {@code /tp}）。参数全部走系统属性，不落盘、不进正式产物。</p>
 */
public final class ObservationRenderRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "233观察回归";

    /** 等待世界 / 等待预测 / 等待方块更新 / 等待区块卸载的 tick 上限（超时即判失败并说明）。 */
    private static final int WAIT_WORLD_TICKS = 20 * 120;
    private static final int WAIT_PREDICTION_TICKS = 20 * 60;
    private static final int WAIT_UPDATE_TICKS = 20 * 15;
    private static final int WAIT_UNLOAD_TICKS = 20 * 20;
    private static final int WAIT_RELOAD_TICKS = 20 * 30;

    /** 步骤状态机。 */
    private enum Step {
        WAIT_WORLD,
        WAIT_PREDICTION,
        CHECK_CONFIRMED,
        MUTATE_TO_AIR,
        WAIT_MISSING,
        RESTORE_BLOCK,
        WAIT_RESTORED,
        TELEPORT_AWAY,
        WAIT_UNLOAD,
        TELEPORT_BACK,
        WAIT_RELOAD,
        SWITCH_TO_B,
        WAIT_SERVER_B,
        WAIT_B_COVERAGE,
        DIMENSION_TO_NETHER,
        WAIT_NETHER,
        DIMENSION_BACK,
        WAIT_OVERWORLD,
        SEED_CHANGE,
        WAIT_SEED_CHANGED,
        FINISHED
    }

    private static Step step = Step.WAIT_WORLD;
    private static int waitTicks;
    private static long seed = 20260922L;
    private static int radius = 1;
    private static boolean mutate = true;
    private static boolean unload = true;

    /** Server A→B 用例的目标服务器键（空串 = 不跑该用例）；形如 {@code 127.0.0.1:25566}。 */
    private static String serverBKey = "";

    /** 是否已发起对 B 的连接（避免每刻重复发起）。 */
    private static boolean connectIssued;

    /** 测试对象：一个已确认的候选位置与其原始方块注册名。 */
    private static BlockPos targetPos;
    private static String targetBlockId = "";
    private static ChunkPos targetChunk;

    /** A 侧残留基线（换服前记录，换服后必须归零）。 */
    private static int aCachedChunks;
    private static int aCandidates;
    private static int aRenderEntries;

    /** 证据计数。 */
    private static int confirmedSeen;
    private static int missingSeen;
    private static int restoredSeen;
    private static int unobservedSeen;
    private static int reloadedSeen;
    private static int serverSwitchSeen;
    private static int netherSeen;
    private static int overworldSeen;
    private static int seedChangedSeen;

    private ObservationRenderRegression() {
    }

    /** 读取本次运行的参数（系统属性，缺失即默认）。 */
    public static void configure() {
        seed = SeedPocFlags.observationSeed();
        radius = SeedPocFlags.observationRadius();
        mutate = SeedPocFlags.observationMutate();
        unload = SeedPocFlags.observationUnload();
        serverBKey = SeedPocFlags.observationServerB();
        if (!serverBKey.isEmpty()) {
            // 与正式层同一套规范化（缺端口补 25565、统一小写），保证与 WorldIdentity#server() 可比
            String canonical = WorldIdentity.canonicalServerKey(serverBKey);
            serverBKey = canonical == null ? "" : canonical;
        }
        LOGGER.info("{}：参数 种子 {} / 覆盖半径 {} / 方块更新用例 {} / 区块卸载用例 {} / 换服用例 {}",
                TAG, seed, radius, mutate ? "开" : "关", unload ? "开" : "关",
                serverBKey.isEmpty() ? "关" : "开（B = " + serverBKey + "）");
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：回归装置异常，已停止", TAG, error);
            step = Step.FINISHED;
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (step == Step.FINISHED) {
            return;
        }
        if (client.player == null || client.level == null) {
            // A→B 用例的等待期本来就要求「不在任何世界里」（断开 A 与连上 B 之间），
            // 因此这一条豁免必须放在通用守卫之前
            if (step == Step.WAIT_SERVER_B) {
                waitServerB(client);
                return;
            }
            if (step != Step.WAIT_WORLD) {
                fail(client, "中途离开了世界");
                return;
            }
            if (++waitTicks > WAIT_WORLD_TICKS) {
                fail(client, "等待进入世界超时（请先连上种子 " + seed + " 的测试服务器）");
            }
            return;
        }
        switch (step) {
            case WAIT_WORLD -> prepare(client);
            case WAIT_PREDICTION -> waitPrediction(client);
            case CHECK_CONFIRMED -> checkConfirmed(client);
            case MUTATE_TO_AIR -> mutateToAir(client);
            case WAIT_MISSING -> waitState(client, OreObservationState.MISSING, Step.RESTORE_BLOCK,
                    WAIT_UPDATE_TICKS, "当前缺失");
            case RESTORE_BLOCK -> restoreBlock(client);
            case WAIT_RESTORED -> waitState(client, OreObservationState.CONFIRMED, Step.TELEPORT_AWAY,
                    WAIT_UPDATE_TICKS, "恢复已确认");
            case TELEPORT_AWAY -> teleportAway(client);
            case WAIT_UNLOAD -> waitUnload(client);
            case TELEPORT_BACK -> teleportBack(client);
            case WAIT_RELOAD -> waitReload(client);
            case SWITCH_TO_B -> switchToServerB(client);
            case WAIT_SERVER_B -> waitServerB(client);
            case WAIT_B_COVERAGE -> waitServerBCoverage(client);
            case DIMENSION_TO_NETHER -> dimensionToNether(client);
            case WAIT_NETHER -> waitNether(client);
            case DIMENSION_BACK -> dimensionBack(client);
            case WAIT_OVERWORLD -> waitOverworld(client);
            case SEED_CHANGE -> seedChange(client);
            case WAIT_SEED_CHANGED -> waitSeedChanged(client);
            case FINISHED -> {
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 各步骤
    // ────────────────────────────────────────────────────────────────────────

    /** 进世界后：开启种子挖矿 + 显示预测钻石 + 设置种子与范围。 */
    private static void prepare(Minecraft client) {
        applySettings();
        LOGGER.info("{}：已开启 种子挖矿 + 显示预测钻石（种子 {}，半径 {}），等待附近区块逐个预测…",
                TAG, seed, radius);
        step = Step.WAIT_PREDICTION;
        waitTicks = 0;
    }

    /**
     * 把本轮要用的一套设置写进当前配置作用域。
     *
     * <p>种子挖矿的配置是<b>按服务器隔离</b>的（{@code 开发习惯} 第 236 条），因此每换一台服务器
     * 都要重新确认一次 —— 这正是「A 里开着、B 里关着」这种真实情形的来源；
     * 本装置每次落到新世界后都显式写一遍，避免上一轮收尾（关闭显示预测钻石）把下一轮带偏。</p>
     */
    private static void applySettings() {
        SeedMiningService service = SeedMiningService.instance();
        service.setEnabled(true);
        service.setSeedText(Long.toString(seed));
        service.setCoverageRadius(radius);
        service.setRenderPrediction(true);
    }

    /** 等待玩家所在区块拿到正式预测结果（覆盖调度会先算最近的一圈）。 */
    private static void waitPrediction(Minecraft client) {
        if (++waitTicks > WAIT_PREDICTION_TICKS) {
            fail(client, "等待附近预测超时（缓存 " + SeedMiningService.instance().cachedChunkCount() + " 个区块）");
            return;
        }
        SeedRenderSnapshot snapshot = SeedMiningService.instance().renderSnapshot();
        if (snapshot.empty()) {
            return;
        }
        LocalPlayer player = client.player;
        ChunkPos playerChunk = ChunkPos.containing(player.blockPosition());
        int inChunk = 0;
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (ChunkPos.containing(entry.position()).equals(playerChunk)) {
                inChunk++;
            }
        }
        if (inChunk == 0) {
            return;
        }
        LOGGER.info("{}：玩家所在区块 ({},{}) 已有 {} 个候选；快照总览 {}",
                TAG, playerChunk.x(), playerChunk.z(), inChunk, snapshot.describeCn());
        step = Step.CHECK_CONFIRMED;
        waitTicks = 0;
    }

    /** 打印候选证据并挑选一个「已确认」的目标；没有已确认时如实报告（可能是错误种子 / 服务器改过地形）。 */
    private static void checkConfirmed(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LocalPlayer player = client.player;
        ChunkPos playerChunk = ChunkPos.containing(player.blockPosition());
        List<SeedRenderEntry> candidates = new ArrayList<>();
        for (SeedRenderEntry entry : service.renderSnapshot().entries()) {
            if (ChunkPos.containing(entry.position()).equals(playerChunk)) {
                candidates.add(entry);
            }
        }
        int confirmed = 0;
        int missing = 0;
        int unobserved = 0;
        BlockPos firstConfirmed = null;
        for (SeedRenderEntry entry : candidates) {
            OreObservationState state = service.observationState(entry.position());
            switch (state) {
                case CONFIRMED -> {
                    confirmed++;
                    if (firstConfirmed == null) {
                        firstConfirmed = entry.position();
                    }
                }
                case MISSING -> missing++;
                default -> unobserved++;
            }
            if (confirmed + missing + unobserved <= 8) {
                LOGGER.info("{}：候选证据 ({},{},{}) 确定性={} 实际方块={} 观察状态={}",
                        TAG, entry.position().getX(), entry.position().getY(), entry.position().getZ(),
                        entry.certainty().displayNameCn(),
                        blank(service.observedBlockId(entry.position())), state.displayNameCn());
            }
        }
        LOGGER.info("{}：本区块候选 {} 个 → 已确认 {} / 当前缺失 {} / 未观察 {}（区块已加载，未观察应为 0）",
                TAG, candidates.size(), confirmed, missing, unobserved);
        if (firstConfirmed == null) {
            fail(client, "本区块内没有「已确认」候选，无法继续方块更新用例（"
                    + "可能：服务器种子不是 " + seed + "、地形被改过、或区块尚未完全下发）");
            return;
        }
        targetPos = firstConfirmed;
        targetBlockId = service.observedBlockId(firstConfirmed);
        targetChunk = ChunkPos.containing(firstConfirmed);
        confirmedSeen++;
        LOGGER.info("{}：CONFIRMED 证据 #{} → ({},{},{}) 实际方块 {}",
                TAG, confirmedSeen, targetPos.getX(), targetPos.getY(), targetPos.getZ(), targetBlockId);
        if (!mutate) {
            LOGGER.info("{}：方块更新用例已关闭（-Dyiyiaddon.seedpoc.observation.mutate=0），跳过", TAG);
            advanceAfterMain(client);
            return;
        }
        step = Step.MUTATE_TO_AIR;
        waitTicks = 0;
    }

    /** 用 dev-only 服务端指令把目标位置改成空气（真实玩家发包路径，模拟「被挖掉」）。 */
    private static void mutateToAir(Minecraft client) {
        if (!sendCommand(client, "setblock " + targetPos.getX() + " " + targetPos.getY() + " "
                + targetPos.getZ() + " air")) {
            fail(client, "发送 setblock 失败（需要 OP）");
            return;
        }
        step = Step.WAIT_MISSING;
        waitTicks = 0;
    }

    /** 等状态变成指定值（方块更新包到达后观察器应立刻刷新）。 */
    private static void waitState(Minecraft client, OreObservationState expected, Step next,
                                  int timeoutTicks, String what) {
        OreObservationState actual = SeedMiningService.instance().observationState(targetPos);
        if (actual == expected) {
            if (expected == OreObservationState.MISSING) {
                missingSeen++;
                LOGGER.info("{}：MISSING 证据 #{} → ({},{},{}) 实际方块 {}，状态 {}（未重跑预测、未重启计算器）",
                        TAG, missingSeen, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                        blank(SeedMiningService.instance().observedBlockId(targetPos)), actual.displayNameCn());
            } else {
                restoredSeen++;
                LOGGER.info("{}：恢复证据 #{} → ({},{},{}) 实际方块 {}，状态 {}",
                        TAG, restoredSeen, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                        blank(SeedMiningService.instance().observedBlockId(targetPos)), actual.displayNameCn());
            }
            step = next;
            waitTicks = 0;
            return;
        }
        if (++waitTicks > timeoutTicks) {
            fail(client, "等待「" + what + "」超时（当前状态 " + actual.displayNameCn() + "，实际方块 "
                    + blank(SeedMiningService.instance().observedBlockId(targetPos)) + "）");
        }
    }

    /** 把目标位置改回原方块（真实钻石矿）。 */
    private static void restoreBlock(Minecraft client) {
        String block = targetBlockId.isBlank() ? "diamond_ore" : targetBlockId;
        if (!sendCommand(client, "setblock " + targetPos.getX() + " " + targetPos.getY() + " "
                + targetPos.getZ() + " " + block)) {
            fail(client, "发送恢复 setblock 失败");
            return;
        }
        step = Step.WAIT_RESTORED;
        waitTicks = 0;
    }

    /** 远距离传送，让客户端把目标区块卸载掉。 */
    private static void teleportAway(Minecraft client) {
        if (!unload) {
            LOGGER.info("{}：区块卸载用例已关闭（-Dyiyiaddon.seedpoc.observation.unload=0），跳过", TAG);
            advanceAfterMain(client);
            return;
        }
        int farX = targetPos.getX() + 4096;
        if (!sendCommand(client, "tp " + farX + " 100 " + targetPos.getZ())) {
            fail(client, "发送 tp 失败（需要 OP）");
            return;
        }
        LOGGER.info("{}：已传送到 ({},100,{})，等待目标区块 ({},{}) 从客户端卸载…",
                TAG, farX, targetPos.getZ(), targetChunk.x(), targetChunk.z());
        step = Step.WAIT_UNLOAD;
        waitTicks = 0;
    }

    /** 等目标区块真的不在客户端 ChunkCache 里（用与观察器完全相同的判据：loadOrGenerate=false）。 */
    private static void waitUnload(Minecraft client) {
        ClientLevel level = client.level;
        if (level == null) {
            fail(client, "等待卸载期间离开了世界");
            return;
        }
        boolean loaded = isChunkLoaded(level, targetChunk);
        if (!loaded && SeedMiningService.instance().observationState(targetPos) == OreObservationState.UNOBSERVED) {
            unobservedSeen++;
            LOGGER.info("{}：UNOBSERVED 证据 #{} → ({},{},{}) 区块 ({},{}) 已卸载，状态 {}",
                    TAG, unobservedSeen, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                    targetChunk.x(), targetChunk.z(),
                    SeedMiningService.instance().observationState(targetPos).displayNameCn());
            step = Step.TELEPORT_BACK;
            waitTicks = 0;
            return;
        }
        if (++waitTicks > WAIT_UNLOAD_TICKS) {
            fail(client, "等待区块卸载超时（区块是否仍加载：" + loaded + "）");
        }
    }

    /** 传回原位置。 */
    private static void teleportBack(Minecraft client) {
        if (!sendCommand(client, "tp " + targetPos.getX() + " " + (targetPos.getY() + 2) + " "
                + targetPos.getZ())) {
            fail(client, "发送返回 tp 失败");
            return;
        }
        LOGGER.info("{}：已传回目标位置，等待区块重新加载并重新观察…", TAG);
        step = Step.WAIT_RELOAD;
        waitTicks = 0;
    }

    /** 等区块重新加载：状态必须重新变成 CONFIRMED 或 MISSING（绝不是未观察）。 */
    private static void waitReload(Minecraft client) {
        ClientLevel level = client.level;
        if (level == null) {
            fail(client, "等待重新加载期间离开了世界");
            return;
        }
        if (!isChunkLoaded(level, targetChunk)) {
            if (++waitTicks > WAIT_RELOAD_TICKS) {
                fail(client, "等待区块重新加载超时");
            }
            return;
        }
        OreObservationState state = SeedMiningService.instance().observationState(targetPos);
        if (state == OreObservationState.UNOBSERVED) {
            if (++waitTicks > WAIT_RELOAD_TICKS) {
                fail(client, "区块已重新加载但状态仍是未观察（观察钩子可能没生效）");
            }
            return;
        }
        reloadedSeen++;
        LOGGER.info("{}：重新加载证据 #{} → 状态 {}（实际方块 {}）",
                TAG, reloadedSeen, state.displayNameCn(),
                blank(SeedMiningService.instance().observedBlockId(targetPos)));
        advanceAfterMain(client);
    }

    /** 主用例跑完：配了 B 就去跑「Server A→B 残留」用例，否则直接收尾。 */
    private static void advanceAfterMain(Minecraft client) {
        if (serverBKey.isEmpty()) {
            finish(client);
            return;
        }
        step = Step.SWITCH_TO_B;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Server A→B：换服后 A 的预测 / 观察 / 渲染必须一条都不剩（口径第四十三条）
    // ────────────────────────────────────────────────────────────────────────

    /** 记录 A 侧读数 → 主动断开 A。 */
    private static void switchToServerB(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        aCachedChunks = service.cachedChunkCount();
        aCandidates = service.observationSnapshot().candidates();
        aRenderEntries = service.renderSnapshot().size();
        LOGGER.info("{}：A 侧残留基线 —— 缓存 {} 个区块 / 候选 {} 个 / 渲染条目 {} 条",
                TAG, aCachedChunks, aCandidates, aRenderEntries);
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            fail(client, "换服前拿不到与 A 的连接");
            return;
        }
        player.connection.getConnection().disconnect(Component.literal("233 A→B 用例：主动断开 A"));
        step = Step.WAIT_SERVER_B;
        waitTicks = 0;
    }

    /** 等世界清空 → 连 B；到达 B 的<b>第一刻</b>立即断言 A 的残留全为 0。 */
    private static void waitServerB(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待进入 B 超时");
            return;
        }
        if (client.level != null) {
            if (!serverBKey.equals(WorldIdentity.server())) {
                // 还没断开干净，或连到了别处
                return;
            }
            assertServerBHasNoLeftover(client);
            return;
        }
        if (!connectIssued) {
            connectIssued = true;
            ServerData data = new ServerData("233 B", serverBKey, ServerData.Type.OTHER);
            LOGGER.info("{}：已断开 A，发起连接 B（{}）", TAG, serverBKey);
            ConnectScreen.startConnecting(new TitleScreen(), client, ServerAddress.parseString(serverBKey),
                    data, false, null);
        }
    }

    /** B 建立后的第一时间读数：三项必须全为 0（此刻不可能已有 B 的预测结果）。 */
    private static void assertServerBHasNoLeftover(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int cached = service.cachedChunkCount();
        int candidates = service.observationSnapshot().candidates();
        int renderEntries = service.renderSnapshot().size();
        int pending = service.coveragePendingCount();
        if (cached != 0 || candidates != 0 || renderEntries != 0) {
            fail(client, "B 世界里仍有 A 的残留：缓存 " + cached + " 个区块 / 候选 " + candidates
                    + " 个 / 渲染条目 " + renderEntries + " 条（A 侧基线 " + aCachedChunks + " / "
                    + aCandidates + " / " + aRenderEntries + "）");
            return;
        }
        serverSwitchSeen++;
        LOGGER.info("{}：SERVER A→B 证据 #{} → 已进入 B（{}），A 的残留全部归零："
                        + "缓存 {} / 候选 {} / 渲染条目 {}（A 侧基线 {} / {} / {}），覆盖排队 {}",
                TAG, serverSwitchSeen, WorldIdentity.server(), cached, candidates, renderEntries,
                aCachedChunks, aCandidates, aRenderEntries, pending);
        // B 是另一台服务器：配置按服务器隔离，这里重新确认一轮（换服后玩家本来也要重新开一次）
        applySettings();
        step = Step.WAIT_B_COVERAGE;
        waitTicks = 0;
    }

    /** 等 B 侧真的开始预测（这样后面的「下界清理」才有东西可清）。 */
    private static void waitServerBCoverage(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待 B 侧开始预测超时");
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        if (service.cachedChunkCount() == 0) {
            return;
        }
        LOGGER.info("{}：B 侧已开始预测 —— 缓存 {} 个区块 / 候选 {} 个（种子 {}），接下来验证维度与种子清理",
                TAG, service.cachedChunkCount(), service.renderSnapshot().stats().candidates(), seed);
        step = Step.DIMENSION_TO_NETHER;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 维度切换与种子修改：同样必须把预测 / 观察 / 渲染全部清空（口径第四十一、四十二条）
    // ────────────────────────────────────────────────────────────────────────

    /** 传送到下界。 */
    private static void dimensionToNether(Minecraft client) {
        if (!sendCommand(client, "execute in minecraft:the_nether run tp @s 0 70 0")) {
            fail(client, "发送下界传送失败（需要 OP）");
            return;
        }
        step = Step.WAIT_NETHER;
        waitTicks = 0;
    }

    /** 下界里：覆盖必须停止、预测缓存 / 观察 / 渲染快照必须全清。 */
    private static void waitNether(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待进入下界超时");
            return;
        }
        if (client.level == null || !"minecraft:the_nether".equals(client.level.dimension().identifier().toString())) {
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        int cached = service.cachedChunkCount();
        int candidates = service.observationSnapshot().candidates();
        int renderEntries = service.renderSnapshot().size();
        int pending = service.coveragePendingCount();
        if (cached != 0 || candidates != 0 || renderEntries != 0 || pending != 0) {
            fail(client, "下界里仍有主世界数据：缓存 " + cached + " / 候选 " + candidates
                    + " / 渲染条目 " + renderEntries + " / 排队 " + pending);
            return;
        }
        netherSeen++;
        LOGGER.info("{}：维度清理证据 #{} → 下界（{}）中 缓存 {} / 候选 {} / 渲染条目 {} / 覆盖排队 {}，"
                        + "身份 {}（覆盖已停止）",
                TAG, netherSeen, service.dimensionDisplayCn(), cached, candidates, renderEntries, pending,
                service.runtimeIdentityCn());
        step = Step.DIMENSION_BACK;
        waitTicks = 0;
    }

    /** 传回主世界（回到本用例的目标位置附近）。 */
    private static void dimensionBack(Minecraft client) {
        if (!sendCommand(client, "execute in minecraft:overworld run tp @s " + targetPos.getX() + " "
                + (targetPos.getY() + 2) + " " + targetPos.getZ())) {
            fail(client, "发送主世界传送失败");
            return;
        }
        step = Step.WAIT_OVERWORLD;
        waitTicks = 0;
    }

    /** 回主世界后：覆盖必须自动重新开始（缓存重新长起来）。 */
    private static void waitOverworld(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待返回主世界并重新预测超时");
            return;
        }
        if (client.level == null || !"minecraft:overworld".equals(client.level.dimension().identifier().toString())) {
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        if (service.cachedChunkCount() == 0) {
            return;
        }
        overworldSeen++;
        LOGGER.info("{}：维度恢复证据 #{} → 已回到主世界，覆盖自动重新开始：缓存 {} 个区块 / 候选 {} 个，身份 {}",
                TAG, overworldSeen, service.cachedChunkCount(),
                service.renderSnapshot().stats().candidates(), service.runtimeIdentityCn());
        step = Step.SEED_CHANGE;
        waitTicks = 0;
    }

    /** 改种子：缓存 / 观察 / 渲染必须立刻清空（口径第四十一条）。 */
    private static void seedChange(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int beforeCached = service.cachedChunkCount();
        int beforeCandidates = service.observationSnapshot().candidates();
        int beforeEntries = service.renderSnapshot().size();
        LOGGER.info("{}：改种子前 —— 缓存 {} 个区块 / 候选 {} 个 / 渲染条目 {} 条（种子 {}）",
                TAG, beforeCached, beforeCandidates, beforeEntries, seed);
        // 直接走界面同一条写入路径（就是种子输入框调用的那个方法）
        service.setSeedText("12345");
        int cached = service.cachedChunkCount();
        int candidates = service.observationSnapshot().candidates();
        int entries = service.renderSnapshot().size();
        int pending = service.coveragePendingCount();
        if (cached != 0 || candidates != 0 || entries != 0 || pending != 0) {
            fail(client, "改种子后仍有旧数据：缓存 " + cached + " / 候选 " + candidates
                    + " / 渲染条目 " + entries + " / 排队 " + pending);
            return;
        }
        seedChangedSeen++;
        LOGGER.info("{}：改种子清理证据 #{} → 种子 {} → 12345 后立刻：缓存 {} / 候选 {} / 渲染条目 {} / 排队 {}"
                        + "（改之前 {} / {} / {}）",
                TAG, seedChangedSeen, seed, cached, candidates, entries, pending,
                beforeCached, beforeCandidates, beforeEntries);
        step = Step.WAIT_SEED_CHANGED;
        waitTicks = 0;
    }

    /** 新种子重新建立身份并重新开始覆盖（证明不是「死了」，而是「换了一套身份重来」）。 */
    private static void waitSeedChanged(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待新种子重新预测超时");
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        String identity = service.runtimeIdentityCn();
        if (!identity.contains("12345")) {
            return;
        }
        if (service.cachedChunkCount() == 0) {
            return;
        }
        LOGGER.info("{}：新种子恢复证据 → 身份已切换为「{}」，已重新预测 {} 个区块",
                TAG, identity, service.cachedChunkCount());
        finish(client);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 收尾
    // ────────────────────────────────────────────────────────────────────────

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LOGGER.info("{}：====== 结果汇总 ======", TAG);
        LOGGER.info("{}：CONFIRMED 证据 {} 条 / MISSING 证据 {} 条 / 恢复证据 {} 条 / "
                        + "卸载→未观察证据 {} 条 / 重新加载证据 {} 条 / Server A→B 证据 {} 条 / "
                        + "下界清理证据 {} 条 / 回主世界恢复证据 {} 条 / 改种子清理证据 {} 条",
                TAG, confirmedSeen, missingSeen, restoredSeen, unobservedSeen, reloadedSeen,
                serverSwitchSeen, netherSeen, overworldSeen, seedChangedSeen);
        LOGGER.info("{}：最终读数 {}", TAG, service.runtimeDiagnosticsCn());
        LOGGER.info("{}：状态是否独立于预测：PredictionCertainty 与 OreObservationState 各自保留，"
                + "全程未产生 SUSPICIOUS", TAG);
        // 收尾后关掉显示预测钻石，顺带验证「关闭即清空」（人工验收步骤 12 的自动化等价物）
        service.setRenderPrediction(false);
        LOGGER.info("{}：已关闭显示预测钻石；缓存 {} 个区块，渲染快照 {}",
                TAG, service.cachedChunkCount(), service.renderSnapshot().describeCn());
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        LOGGER.error("{}：用例失败 —— {}", TAG, reason);
        LOGGER.error("{}：失败时读数 {}", TAG, SeedMiningService.instance().runtimeDiagnosticsCn());
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

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

    /**
     * 目标区块此刻是否真的加载在客户端（判据与观察器完全一致：
     * {@code ClientChunkCache#getChunk(..., loadOrGenerate=false)}，绝不触发任何加载）。
     */
    private static boolean isChunkLoaded(ClientLevel level, ChunkPos chunk) {
        ClientChunkCache cache = level.getChunkSource();
        return cache.getChunk(chunk.x(), chunk.z(), ChunkStatus.FULL, false) != null;
    }

    private static String blank(String text) {
        return text == null || text.isBlank() ? "（未知）" : text;
    }
}
