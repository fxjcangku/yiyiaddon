package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.SeedObservationSnapshot;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.render.SeedRenderEntry;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第九阶段（237）· <b>下界真正多人服务器（Dedicated Multiplayer）验收装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>：232 已经证明主世界在「专用服务器 + 客户端本地计算器」下成立，但
 * 236 新增的下界维度<b>只在单人集成服务端上验证过</b>（236 报告「当前仍存在的限制」第 3 条）。
 * 下界 AutoMiner 虽然是 fail-closed、不影响安全边界，但「下界预测 / 观察 / ESP 在真实多人环境下
 * 能不能跑」仍是一条真实证据缺口。本装置补的就是它。</p>
 *
 * <p><b>环境</b>：Gradle 任务 {@code runSeedNetherMultiplayerServer} 起的 26.1.2 专用服务器
 * （端口 25865、{@code level-seed=20260922}、{@code allow-nether=true}、创造模式、无 yiyiaddon ——
 * 本模组 {@code fabric.mod.json} 声明 {@code "environment": "client"}，专用服务端由 Fabric Loader
 * 直接不加载它）。客户端由 {@code runClientSeedNetherMultiplayerTest} 用
 * {@code --quickPlayMultiplayer 127.0.0.1:25865} 自动连上去。</p>
 *
 * <p><b>逐条证据</b>：</p>
 * <ol>
 *     <li>多人环境：{@code Minecraft#getSingleplayerServer() == null}；</li>
 *     <li>主世界 → 下界：身份切换到下界、主世界的预测 / 观察 / 渲染残留全为 0；</li>
 *     <li>下界预测：远古残骸 / 下界石英 / 下界金 各一个有效目标（候选非零），宿主 ChunkMap 查询恒为 0；</li>
 *     <li>下界观察 / ESP：真实已加载区块上的候选被观察层确认为真实方块，渲染快照条目与候选一一对应；</li>
 *     <li>远端未加载区块：<b>未加载时仍可预测</b>，且预测本身不导致客户端去加载它；随后真实进入该区域，
 *         观察层按实际 BlockState 更新；</li>
 *     <li>下界自动挖矿 fail-closed（总门关 + 目标为 null + 三种矿物全被拒）；</li>
 *     <li>Worker 退出无孤儿（本机隔离计算器进程在收尾后消失）。</li>
 * </ol>
 *
 * <p><b>触发方式</b>：{@code -Dyiyiaddon.seedpoc.enabled=1
 * -Dyiyiaddon.seedpoc.netherMultiplayer=1 -Dyiyiaddon.seedpoc.exit=1}</p>
 */
public final class NetherMultiplayerRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告文件名（落在客户端运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-237-下界多人验收.txt";

    /** 单次预测 / 等待的上限（客户端刻）。 */
    private static final int WAIT_TIMEOUT_TICKS = 20 * 240;

    /** 进入下界 / 远端区域的等待上限（客户端刻）。 */
    private static final int WAIT_DIMENSION_TICKS = 20 * 120;

    /** 专用服务器世界的种子（预测种子必须与它一致）。 */
    private static final long DEDICATED_SEED = 20260922L;

    /** 主世界基线目标（232 冻结口径：Seed 20260922 区块 (0,0) 钻石 = 45）。 */
    private static final ChunkPos OVERWORLD_TARGET = new ChunkPos(0, 0);
    private static final int OVERWORLD_EXPECTED = 45;

    /**
     * 下界的三个有效目标（与 236 矩阵同一批区块，候选数在离线预测里必然非零）：
     * 远古残骸 1 / 下界石英 70 / 下界金 48（236 实测读数）。
     */
    private static final ChunkPos NETHER_TARGET = new ChunkPos(-3, 4);
    private static final List<OreType> NETHER_TARGET_ORES =
            List.of(OreType.ANCIENT_DEBRIS, OreType.NETHER_QUARTZ, OreType.NETHER_GOLD);

    /** 下界远端未加载候选区块（逐个试到第一个候选非零的为止）。 */
    private static final List<ChunkPos> FAR_CANDIDATES = List.of(
            new ChunkPos(400, -400), new ChunkPos(401, -400), new ChunkPos(400, -401));

    /** 远端目标使用的矿物（远古残骸：下界三种里最难碰巧命中的一种，非零即最有说服力）。 */
    private static final OreType FAR_ORE = OreType.ANCIENT_DEBRIS;

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    private enum Stage {
        /** 等连上专用服务器。 */
        CONNECT,
        /**
         * 确保从主世界开始。
         *
         * <p>为什么需要这一步：专用服务器会保存玩家位置，上一轮验收把玩家留在了下界，
         * 于是下一次连服时玩家直接生成在下界 —— 「主世界基线 → 切下界」这条判据链就没有起点。
         * 装置必须自己把起点定下来，不能依赖服务器上次把玩家放在哪。</p>
         */
        OVERWORLD_TP,
        /** 主世界基线预测（切换前的身份 / 残留基线）。 */
        OVERWORLD_PREDICT,
        /** 传送到下界。 */
        NETHER_TP,
        /** 下界三种矿物逐个预测。 */
        NETHER_ORES,
        /** 下界远端未加载区块预测。 */
        NETHER_FAR,
        /** 传送到远端区块。 */
        FAR_TP,
        /** 等远端区块加载并核对观察层按真实方块更新。 */
        FAR_OBSERVE,
        /** 下界自动挖矿闸门。 */
        GATES,
        /** 收尾：停计算器并核对无孤儿进程。 */
        SHUTDOWN_WORKER,
        FINISHED
    }

    private static Stage stage = Stage.CONNECT;
    private static int waitTicks;
    private static boolean submitted;

    // ── 下界三种矿物逐个预测的游标 ──
    private static int oreIndex;

    // ── 远端未加载候选的游标与命中结果 ──
    private static int farIndex;
    private static ChunkPos farTarget;
    private static int farCount;
    private static boolean farLoadedBefore;

    private static long workerPid;

    /** 连服时玩家所在维度（服务器会保存玩家位置，可能直接生成在下界）。 */
    private static String entryDimension = "（未记录）";

    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();

    /** 下界每种矿物的实测候选数（报告用）。 */
    private static final Map<OreType, Integer> NETHER_COUNTS = new LinkedHashMap<>();

    /** 远端区块加载后观察层的「已确认」读数。 */
    private static int farConfirmed;
    private static int farCandidates;

    private static boolean reportWritten;

    private NetherMultiplayerRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在下界多人验收模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (stage) {
                case CONNECT -> tickConnect(client);
                case OVERWORLD_TP -> tickOverworldTeleport(client);
                case OVERWORLD_PREDICT -> tickOverworldPredict(client);
                case NETHER_TP -> tickNetherTeleport(client);
                case NETHER_ORES -> tickNetherOres(client);
                case NETHER_FAR -> tickNetherFar(client);
                case FAR_TP -> tickFarTeleport(client);
                case FAR_OBSERVE -> tickFarObserve(client);
                case GATES -> tickGates(client);
                case SHUTDOWN_WORKER -> tickShutdownWorker();
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            LOGGER.error("{}：下界多人验收装置中断", SeedPocConstants.LOG_KEY, error);
            report("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            VERDICTS.add("【判定】装置异常中断：不通过");
            finish();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 连接与环境
    // ────────────────────────────────────────────────────────────────────────

    private static void tickConnect(Minecraft client) {
        if (client.level == null || client.player == null) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("**等待进入世界超时（请确认已自动连接到专用服务器）**");
                VERDICTS.add("【判定】多人环境：不通过（未进入世界）");
                finish();
            }
            return;
        }
        boolean multiplayer = client.getSingleplayerServer() == null;
        report("零、口径与环境");
        report("  服务端：本机专用服务器（端口 25865 / level-seed=" + DEDICATED_SEED
                + " / allow-nether=true / 不加载 yiyiaddon —— 本模组声明 environment=client）");
        report("  客户端：真实客户端进程，--quickPlayMultiplayer 自动连接");
        report("  进入时维度：" + client.level.dimension().identifier());
        report("  Minecraft#getSingleplayerServer()：" + (multiplayer ? "null" : "非 null")
                + "（必须为 null 才是真正的多人环境）" + verdict(multiplayer));
        report("  本地世界生成计算器：" + SERVICE.calculatorStateCn() + "（宿主由它提供）");
        VERDICTS.add("【判定】多人环境 getSingleplayerServer() == null：" + verdict(multiplayer));
        if (!multiplayer) {
            report("**这不是多人环境：本轮下界多人验收不成立**");
            finish();
            return;
        }
        SERVICE.setSeedText(String.valueOf(DEDICATED_SEED));
        SERVICE.setEnabled(true);
        SERVICE.setRenderPrediction(true);
        SERVICE.setCoverageRadius(SeedMiningService.coverageRadiusMin());
        setOnlyOre(OreType.DIAMOND);
        entryDimension = client.level.dimension().identifier().toString();
        stage = Stage.OVERWORLD_TP;
        waitTicks = 0;
        submitted = false;
    }

    /** 把玩家送回主世界（服务器可能把玩家留在下界），再开始主世界基线。 */
    private static void tickOverworldTeleport(Minecraft client) {
        if (waitTicks == 0) {
            if (SeedDimensionProfile.OVERWORLD.dimensionId().equals(entryDimension)) {
                stage = Stage.OVERWORLD_PREDICT;
                return;
            }
            report("  上一次验收把玩家留在了 " + entryDimension + " → 先送回主世界，再开始基线"
                    + "（判据链的起点必须由装置自己定下来）");
            sendCommand(client, "execute in minecraft:overworld run tp @s 8 120 8");
        }
        if (++waitTicks > WAIT_DIMENSION_TICKS) {
            report("**等待回到主世界超时**");
            VERDICTS.add("【判定】主世界基线：不通过（无法回到主世界）");
            gotoNetherTeleport();
            return;
        }
        if (!inDimension(client, SeedDimensionProfile.OVERWORLD)) {
            return;
        }
        stage = Stage.OVERWORLD_PREDICT;
        waitTicks = 0;
        submitted = false;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 主世界基线
    // ────────────────────────────────────────────────────────────────────────

    private static void tickOverworldPredict(Minecraft client) {
        if (!submitted) {
            report("");
            report("一、主世界基线（切换前的身份与残留基线；232 冻结口径 Seed " + DEDICATED_SEED
                    + " 区块 " + OVERWORLD_TARGET.x() + "," + OVERWORLD_TARGET.z() + " 钻石 = "
                    + OVERWORLD_EXPECTED + "）");
            report("  预测前身份：" + SERVICE.runtimeIdentityCn() + "；维度支持：" + SERVICE.dimensionSupportCn());
            SERVICE.predictChunk(OVERWORLD_TARGET.x(), OVERWORLD_TARGET.z());
            if (!SERVICE.predicting()) {
                report("  **未能启动预测（状态 " + SERVICE.stateCn() + "）**");
                VERDICTS.add("【判定】主世界基线：不通过（未启动）");
                gotoNetherTeleport();
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("  **等待主世界预测超时**");
                VERDICTS.add("【判定】主世界基线：不通过（超时）");
                gotoNetherTeleport();
            }
            return;
        }
        submitted = false;
        PredictionResult result = SERVICE.lastResult();
        if (result == null || result.failed()) {
            report("  **主世界基线预测不成立：" + (result == null ? "结果为空" : result.failureReason()) + "**");
            VERDICTS.add("【判定】主世界基线：不通过（预测不成立）");
            gotoNetherTeleport();
            return;
        }
        boolean countOk = result.count() == OVERWORLD_EXPECTED;
        boolean hostClean = result.stats().hostChunkSourceQueries() == 0;
        report("  候选人 " + result.count() + "（期望 " + OVERWORLD_EXPECTED + "）" + verdict(countOk)
                + "；宿主 ChunkMap 查询 " + result.stats().hostChunkSourceQueries() + verdict(hostClean));
        report("  切换前读数：预测缓存 " + SERVICE.cachedChunkCount() + " / 观察候选 "
                + SERVICE.observationSnapshot().candidates() + " / 渲染条目 " + SERVICE.renderSnapshot().size());
        VERDICTS.add("【判定】主世界基线（多人环境）：候选 " + result.count() + verdict(countOk)
                + " / 宿主查询为 0 " + verdict(hostClean));
        gotoNetherTeleport();
    }

    private static void gotoNetherTeleport() {
        stage = Stage.NETHER_TP;
        waitTicks = 0;
        submitted = false;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 主世界 → 下界
    // ────────────────────────────────────────────────────────────────────────

    private static void tickNetherTeleport(Minecraft client) {
        if (waitTicks == 0) {
            if (!sendCommand(client, "execute in minecraft:the_nether run tp @s 0 100 0")) {
                report("**下界传送指令发送失败**");
                VERDICTS.add("【判定】下界：不通过（无法传送）");
                stage = Stage.GATES;
                return;
            }
        }
        if (++waitTicks > WAIT_DIMENSION_TICKS) {
            report("**等待进入下界超时**");
            VERDICTS.add("【判定】下界：不通过（进入超时）");
            stage = Stage.GATES;
            return;
        }
        if (client.level == null || !SeedDimensionProfile.NETHER.dimensionId()
                .equals(client.level.dimension().identifier().toString())) {
            return;
        }
        int cached = SERVICE.cachedChunkCount();
        int candidates = SERVICE.observationSnapshot().candidates();
        int entries = SERVICE.renderSnapshot().size();
        report("");
        report("二、维度切换（进入下界的那一刻）");
        report("  进入后维度：" + client.level.dimension().identifier());
        report("  运行时身份：" + SERVICE.runtimeIdentityCn());
        report("  维度支持：" + SERVICE.dimensionSupportCn() + "；当前档案：" + SERVICE.predictModelCn());
        report("  主世界残留（必须全为 0）：预测缓存 " + cached + " / 观察候选 " + candidates
                + " / 渲染条目 " + entries);
        boolean clean = cached == 0 && candidates == 0 && entries == 0;
        boolean identityIsNether = SERVICE.dimensionId() != null
                && SeedDimensionProfile.NETHER.dimensionId().equals(SERVICE.dimensionId());
        VERDICTS.add("【判定】主世界 → 下界切换（身份换成下界 + 缓存 / 观察 / 渲染全为 0）："
                + verdict(clean && identityIsNether));
        stage = Stage.NETHER_ORES;
        waitTicks = 0;
        submitted = false;
        oreIndex = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 下界三种矿物
    // ────────────────────────────────────────────────────────────────────────

    private static void tickNetherOres(Minecraft client) {
        if (oreIndex >= NETHER_TARGET_ORES.size()) {
            stage = Stage.NETHER_FAR;
            waitTicks = 0;
            submitted = false;
            farIndex = 0;
            return;
        }
        OreType oreType = NETHER_TARGET_ORES.get(oreIndex);
        if (!submitted) {
            setOnlyOre(oreType);
            if (!SERVICE.seedText().trim().equals(String.valueOf(DEDICATED_SEED))) {
                SERVICE.setSeedText(String.valueOf(DEDICATED_SEED));
            }
            report("");
            report("三." + (oreIndex + 1) + "、" + oreType.displayNameCn() + "（下界 目标区块 "
                    + NETHER_TARGET.x() + "," + NETHER_TARGET.z() + "）");
            report("  客户端是否已加载该区块：" + (hasChunk(client, NETHER_TARGET) ? "是" : "否"));
            SERVICE.predictChunk(NETHER_TARGET.x(), NETHER_TARGET.z());
            if (!SERVICE.predicting()) {
                report("  **未能启动预测（状态 " + SERVICE.stateCn() + "）**");
                VERDICTS.add("【判定】下界 · " + oreType.displayNameCn() + "：不通过（未启动）");
                oreIndex++;
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("  **等待预测超时**");
                VERDICTS.add("【判定】下界 · " + oreType.displayNameCn() + "：不通过（超时）");
                oreIndex++;
                submitted = false;
            }
            return;
        }
        submitted = false;
        PredictionResult result = SERVICE.lastResult();
        if (result == null || result.failed()) {
            report("  **预测不成立：" + (result == null ? "结果为空" : result.failureReason()) + "**");
            VERDICTS.add("【判定】下界 · " + oreType.displayNameCn() + "：不通过（预测不成立）");
            oreIndex++;
            return;
        }
        boolean hostClean = result.stats().hostChunkSourceQueries() == 0;
        boolean dimensionOk = SeedDimensionProfile.NETHER.dimensionId()
                .equals(result.request().dimension().identifier().toString());
        int renderEntries = renderEntriesFor(result.request().oreType(), NETHER_TARGET);
        boolean renderCovers = renderEntries >= result.count();
        NETHER_COUNTS.put(oreType, result.count());
        report("  候选 " + result.count() + "（敏感 " + result.scheduleSensitiveCount() + " / 未解析 "
                + result.unresolvedCount() + "）；耗时 " + result.elapsedMillis() + " ms；缓存 "
                + result.stats().heldChunks() + "；宿主 ChunkMap 查询 "
                + result.stats().hostChunkSourceQueries() + "（必须为 0）");
        report("  请求维度：" + result.request().dimension().identifier() + verdict(dimensionOk)
                + "；ESP 条目 " + renderEntries + verdict(renderCovers));
        SeedObservationSnapshot observed = SERVICE.observationSnapshot();
        report("  观察读数：候选 " + observed.candidates() + " / 已确认 " + observed.confirmed()
                + " / 当前缺失 " + observed.missing() + " / 未观察 " + observed.unobserved());
        VERDICTS.add("【判定】下界 · " + oreType.displayNameCn() + "：候选 " + result.count()
                + "（有效目标 = 候选非零）" + verdict(result.count() > 0)
                + " / 维度正确 " + verdict(dimensionOk) + " / 宿主查询为 0 " + verdict(hostClean)
                + " / ESP 覆盖 " + verdict(renderCovers));
        oreIndex++;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 远端未加载区块
    // ────────────────────────────────────────────────────────────────────────

    private static void tickNetherFar(Minecraft client) {
        if (farTarget != null) {
            // 已找到非零的远端目标 → 直接进入传送
            stage = Stage.FAR_TP;
            waitTicks = 0;
            return;
        }
        if (farIndex >= FAR_CANDIDATES.size()) {
            report("");
            report("四、远端未加载区块：三个候选都没有非零候选（下界远古残骸本身就稀疏），"
                    + "仍以「未加载可预测」为判据继续");
            farTarget = FAR_CANDIDATES.get(0);
            stage = Stage.FAR_TP;
            waitTicks = 0;
            return;
        }
        ChunkPos candidate = FAR_CANDIDATES.get(farIndex);
        if (!submitted) {
            setOnlyOre(FAR_ORE);
            farLoadedBefore = hasChunk(client, candidate);
            report("");
            report("四." + (farIndex + 1) + "、远端未加载区块候选 (" + candidate.x() + "," + candidate.z()
                    + ")：客户端是否已有该区块 " + (farLoadedBefore ? "有（不合格，跳过）" : "没有"));
            if (farLoadedBefore) {
                farIndex++;
                return;
            }
            SERVICE.predictChunk(candidate.x(), candidate.z());
            if (!SERVICE.predicting()) {
                report("  **未能启动预测（状态 " + SERVICE.stateCn() + "）**");
                VERDICTS.add("【判定】下界远端 (" + candidate.x() + "," + candidate.z() + ")：不通过（未启动）");
                farIndex++;
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("  **等待预测超时**");
                VERDICTS.add("【判定】下界远端 (" + candidate.x() + "," + candidate.z() + ")：不通过（超时）");
                farIndex++;
                submitted = false;
            }
            return;
        }
        submitted = false;
        PredictionResult result = SERVICE.lastResult();
        if (result == null || result.failed()) {
            report("  **预测不成立：" + (result == null ? "结果为空" : result.failureReason()) + "**");
            VERDICTS.add("【判定】下界远端 (" + candidate.x() + "," + candidate.z() + ")：不通过（预测不成立）");
            farIndex++;
            return;
        }
        boolean stillUnloaded = !hasChunk(client, candidate);
        boolean hostClean = result.stats().hostChunkSourceQueries() == 0;
        report("  候选 " + result.count() + "；耗时 " + result.elapsedMillis() + " ms；宿主 ChunkMap 查询 "
                + result.stats().hostChunkSourceQueries() + "（必须为 0）");
        report("  预测之后客户端是否仍未加载该区块：" + (stillUnloaded ? "仍未加载（预测没有导致真实区块加载）"
                : "**已加载**") + verdict(stillUnloaded));
        VERDICTS.add("【判定】下界远端未加载区块 (" + candidate.x() + "," + candidate.z() + ") 可预测 "
                + verdict(!farLoadedBefore && result.success() && stillUnloaded && hostClean)
                + "（谓词：预测前未加载 + 预测成功 + 预测后仍未加载 + 宿主查询 0）；候选 " + result.count());
        if (result.count() > 0) {
            farTarget = candidate;
            farCount = result.count();
            stage = Stage.FAR_TP;
            waitTicks = 0;
            return;
        }
        farIndex++;
    }

    private static void tickFarTeleport(Minecraft client) {
        if (farTarget == null) {
            stage = Stage.GATES;
            return;
        }
        if (waitTicks == 0) {
            int x = farTarget.getMiddleBlockX();
            int z = farTarget.getMiddleBlockZ();
            sendCommand(client, "execute in minecraft:the_nether run tp @s " + x + " 100 " + z);
            report("");
            report("五、进入远端区域（区块 " + farTarget.x() + "," + farTarget.z() + "）：把客户端真的送过去，"
                    + "观察层必须按实际 BlockState 更新");
        }
        if (++waitTicks > WAIT_DIMENSION_TICKS) {
            report("**等待远端区域加载超时**");
            VERDICTS.add("【判定】下界远端观察：不通过（加载超时）");
            stage = Stage.GATES;
            return;
        }
        if (client.player == null || client.level == null) {
            return;
        }
        if (!SeedDimensionProfile.NETHER.dimensionId()
                .equals(client.level.dimension().identifier().toString())) {
            return;
        }
        int playerChunkX = client.player.blockPosition().getX() >> 4;
        int playerChunkZ = client.player.blockPosition().getZ() >> 4;
        if (playerChunkX != farTarget.x() || playerChunkZ != farTarget.z()) {
            return;
        }
        report("  已进入目标区块（玩家区块 " + playerChunkX + "," + playerChunkZ + "）；"
                + "客户端是否已加载该区块：" + (hasChunk(client, farTarget) ? "是" : "否"));
        stage = Stage.FAR_OBSERVE;
        waitTicks = 0;
    }

    private static void tickFarObserve(Minecraft client) {
        // 观察层依赖「覆盖 / 手工预测 + 客户端已加载区块」，给它一点时间铺开
        if (++waitTicks < 20 * 20) {
            return;
        }
        if (SERVICE.predicting() && waitTicks < 20 * 60) {
            return;
        }
        SeedObservationSnapshot observed = SERVICE.observationSnapshot();
        farCandidates = observed.candidates();
        farConfirmed = observed.confirmed();
        int renderEntries = renderEntriesFor(OreType.ANCIENT_DEBRIS, farTarget);
        report("  观察读数：候选 " + observed.candidates() + " / 已确认 " + observed.confirmed()
                + " / 当前缺失 " + observed.missing() + " / 未观察 " + observed.unobserved());
        report("  该远端区块（" + farTarget.x() + "," + farTarget.z() + "）的 ESP 条目：" + renderEntries
                + "；预测候选 " + farCount);
        boolean loaded = hasChunk(client, farTarget);
        boolean observedOk = loaded && farCandidates > 0 && farConfirmed > 0;
        VERDICTS.add("【判定】下界远端加载后观察层按真实 BlockState 更新（用户已加载 + 候选 "
                + farCandidates + " + 已确认 " + farConfirmed + "）：" + verdict(observedOk));
        stage = Stage.GATES;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 闸门与收尾
    // ────────────────────────────────────────────────────────────────────────

    private static void tickGates(Minecraft client) {
        report("");
        report("六、下界自动挖矿闸门（fail-closed）");
        report("  维度档案：" + SERVICE.predictModelCn());
        report("  下界自动挖矿：" + SERVICE.netherAutoMiningAllowedCn());
        report("  允许自动挖矿的矿物（下界）：" + SERVICE.autoMinerEligibleOresCn());
        boolean miningBlocked = !SERVICE.mayUseForAutomatedMining();
        boolean targetNull = SERVICE.autoMiningTargetOre() == null;
        boolean everyNetherOreBlocked = true;
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            if (SERVICE.mayUseForAutomatedMining(oreType)) {
                everyNetherOreBlocked = false;
            }
        }
        report("  mayUseForAutomatedMining() = " + SERVICE.mayUseForAutomatedMining()
                + "；autoMiningTargetOre() = " + SERVICE.autoMiningTargetOre());
        VERDICTS.add("【判定】下界自动挖矿 fail-closed（总门关 + 目标为 null + 三种矿物全被拒）："
                + verdict(miningBlocked && targetNull && everyNetherOreBlocked));
        workerPid = SERVICE.calculatorPid();
        report("");
        report("七、Worker 无孤儿");
        report("  收尾前计算器 PID=" + workerPid + "；启动次数 " + SERVICE.calculatorStartCount()
                + "；状态 " + SERVICE.calculatorStateCn());
        stage = Stage.SHUTDOWN_WORKER;
        waitTicks = 0;
    }

    private static void tickShutdownWorker() {
        if (waitTicks == 0) {
            SERVICE.shutdownForExit();
        }
        if (++waitTicks < 20 * 8) {
            return;
        }
        boolean orphan = workerPid > 0
                && ProcessHandle.of(workerPid).map(ProcessHandle::isAlive).orElse(false);
        report("  收尾后计算器 PID=" + workerPid + " 是否仍在：" + (orphan ? "**仍在（孤儿）**" : "已消失"));
        VERDICTS.add("【判定】Worker 退出无孤儿（客户端收尾后隔离计算器进程消失）：" + verdict(!orphan));
        finish();
    }

    private static void finish() {
        if (reportWritten) {
            return;
        }
        reportWritten = true;
        stage = Stage.FINISHED;
        List<String> lines = new ArrayList<>();
        lines.add("《237 · 种子挖矿 · 下界真正多人服务器（Dedicated Multiplayer）验收结果》");
        lines.add("环境：Minecraft 26.1.2 + 本机专用服务器（端口 25865 / level-seed=" + DEDICATED_SEED
                + " / allow-nether=true / 创造模式）；");
        lines.add("服务端不加载 yiyiaddon（本模组 fabric.mod.json 声明 environment=client）；");
        lines.add("客户端：真实客户端进程，由 --quickPlayMultiplayer 自动连接，"
                + "getSingleplayerServer() == null；Worker 由客户端本地拉起。");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("八、判定汇总");
        lines.addAll(VERDICTS);
        lines.add("");
        lines.add("九、下界三种矿物读数（目标区块 " + NETHER_TARGET.x() + "," + NETHER_TARGET.z() + "）");
        for (Map.Entry<OreType, Integer> entry : NETHER_COUNTS.entrySet()) {
            lines.add("  " + entry.getKey().displayNameCn() + "：候选 " + entry.getValue());
        }
        lines.add("  远端未加载目标："
                + (farTarget == null ? "（未取得非零候选）"
                : "(" + farTarget.x() + "," + farTarget.z() + ") 候选 " + farCount
                + "，加载后观察层候选 " + farCandidates + " / 已确认 " + farConfirmed));
        lines.add("");
        lines.add("十、计算器诊断");
        lines.add("  " + SERVICE.calculatorDiagnosticsCn());
        lines.add("  " + SERVICE.runtimeDiagnosticsCn());
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    /** 只勾选一种矿物（本维度其余全部取消）。 */
    private static void setOnlyOre(OreType oreType) {
        for (OreType candidate : SERVICE.supportedOres()) {
            SERVICE.setOreSelected(candidate, candidate == oreType);
        }
    }

    /** 某矿物在指定目标区块上的 ESP 条目数。 */
    private static int renderEntriesFor(OreType oreType, ChunkPos chunk) {
        if (oreType == null || chunk == null) {
            return 0;
        }
        SeedRenderSnapshot snapshot = SERVICE.renderSnapshot();
        int counter = 0;
        for (SeedRenderEntry entry : snapshot.entries()) {
            if (entry.oreType() != oreType) {
                continue;
            }
            if (entry.position().getX() >> 4 == chunk.x() && entry.position().getZ() >> 4 == chunk.z()) {
                counter++;
            }
        }
        return counter;
    }

    /** 客户端当前是否已经在指定维度档案的维度里。 */
    private static boolean inDimension(Minecraft client, SeedDimensionProfile profile) {
        return client.level != null && profile.dimensionId().equals(
                client.level.dimension().identifier().toString());
    }

    /** 客户端 ChunkCache 里有没有这个区块（{@code false} = 不为它加载 / 生成）。 */
    private static boolean hasChunk(Minecraft client, ChunkPos target) {
        ClientLevel level = client.level;
        if (level == null) {
            return false;
        }
        return level.getChunkSource().getChunk(target.x(), target.z(), ChunkStatus.FULL, false) != null;
    }

    /** 发一条服务端指令（专用服务器需要 OP，运行目录的 ops.json 已给测试账号 4 级权限）。 */
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
        LOGGER.info("{}：237下界多人｜{}", SeedPocConstants.LOG_KEY, line);
    }
}
