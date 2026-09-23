package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.fsm.MinerState;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.observation.SeedObservationSnapshot;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreDefinition;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictedOre;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
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
 *     <li>下界自动挖矿口径（238：静态资格全开；放行只来自「下界专属验证 + 证据覆盖该矿」）；</li>
 *     <li><b>下界实际挖掘（238 新增）</b>：三种下界矿物逐个让自动挖矿在下界真的挖掉一颗 ——
 *         候选处真值命中 → 提供者锁定该候选 → 秒破把真实方块变成 air → 换下一颗；</li>
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

    /**
     * 下界实际挖掘逐个跑的矿物顺序（维度声明序：远古残骸 → 下界石英 → 下界金）。
     *
     * <p>238 的「下界也能自动挖」不能只停在「闸门放行」：本阶段让自动挖矿在下界真的挖掉一颗，
     * 逐矿物留下「候选 → 目标 → 实际方块 → air」的完整证据链。</p>
     */
    private static final List<OreType> MINE_ORES = List.of(
            OreType.ANCIENT_DEBRIS, OreType.NETHER_QUARTZ, OreType.NETHER_GOLD);

    /** 下界实际挖掘：单种矿物的等待上限（候选出现 / 目标锁定 / 挖掉）。 */
    private static final int WAIT_MINE_TICKS = 20 * 150;

    /** 下界实际挖掘用的站位（有候选的区块中心；创造模式，任何高度都摔不死）。 */
    private static final int MINE_STAND_Y = 70;

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
        /** 下界实际挖掘：换到下一种下界矿物并摆现场。 */
        MINE_NEXT,
        /** 下界实际挖掘：等候选出现 → 摆现场 → 启动模块。 */
        MINE_SETUP,
        /** 下界实际挖掘：等目标锁定与真实方块被挖掉。 */
        MINE_RUN,
        /** 收尾：停计算器并核对无孤儿进程。 */
        SHUTDOWN_WORKER,
        FINISHED
    }

    private static Stage stage = Stage.CONNECT;
    private static int waitTicks;
    private static boolean submitted;

    // ── 下界三种矿物逐个预测的游标 ──
    private static int oreIndex;

    // ── 下界实际挖掘（逐个矿物跑一遍）──
    /** 当前跑到第几种下界矿物。 */
    private static int mineIndex;
    /** 当前被追的下界矿物。 */
    private static OreType mineOre;
    /** 装置选定的候选坐标（= 现场中心）。 */
    private static BlockPos mineCandidate;
    /** 候选处摆现场之前的真实方块读数（预测命中证据）。 */
    private static String mineTruthBefore = "（未读）";
    /** 提供者锁定的目标坐标。 */
    private static BlockPos mineLocked;
    /** 目标是否就是装置摆的那一颗（强判据）。 */
    private static boolean mineLockedIsCandidate;
    /** 现场那一格是不是被真的挖掉了（真实方块变成 air）。 */
    private static boolean mineBroken;
    /** 现场是否已经被装置确认过（先看到目标矿，才允许把 air 判成「被挖掉」）。 */
    private static boolean mineSceneSeen;
    /** 挖掉之后又过了多少刻（给「换下一颗」留窗口）。 */
    private static int mineBrokenTicks;
    /** 挖掉之后是否换到了下一颗。 */
    private static boolean mineSwitched;
    /** 本矿物内的小步进（摆现场需要跨刻发指令）。 */
    private static int mineStep;
    /** 摆现场时「放回目标矿」的重试次数。 */
    private static int mineSetAttempts;
    /** 模块启用后仍未运行（启动自检未通过）的连续刻数。 */
    private static int mineDisabledTicks;
    /** 本矿物的「站位点」水平坐标（区块中心）：物流指令绑回这里，模块自己那一跳才有真实位移。 */
    private static int mineStandX;
    private static int mineStandZ;
    /** 「前往野外」由装置补跳的次数 / 连续空闲刻数（见 {@code tickMineRun}）。 */
    private static int mineGoWildInjections;
    private static int mineGoWildIdleTicks;

    /** 一种矿物最多由装置补几次「前往野外」传送：够用即可，避免在真正的故障上无限补。 */
    private static final int MAX_GO_WILD_INJECTIONS = 4;

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
            // 环境前提：装置全程要在创造模式。有人在窗口里按 F3+F4 切到生存，落体就会真的摔死人，
            // 整轮证据随之作废（实测踩过）。这里只在「真的不是创造」时才补一刀，不刷屏。
            if (stage != Stage.CONNECT && stage != Stage.FINISHED
                    && client.player != null && !client.player.isCreative()) {
                sendCommand(client, "gamemode creative @s");
            }
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
                case MINE_NEXT -> tickMineNext(client);
                case MINE_SETUP -> tickMineSetup(client);
                case MINE_RUN -> tickMineRun(client);
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
        // 强制创造模式：装置全程不需要生存逻辑，外部把模式改掉会引入摔伤 / 死亡这类噪声
        sendCommand(client, "gamemode creative @s");
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
            // 先强制加载 + 封壳再传：直接 tp 到 y=120 是「从天上落下来」（生存模式下就是摔死），
            // 观感也不像一次受控的基线取证。
            forceloadAdd(client, SeedDimensionProfile.OVERWORLD, 0, 0);
            carveSafeRoom(client, SeedDimensionProfile.OVERWORLD, 8, 120, 8);
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
            // 入界点也要先封壳：直接 tp 到 y=100 是从「天上」落进下界，落点下方就是岩浆海 ——
            // 观感就是「传进岩浆湖」；创造模式不致命，但现场已经不是可控环境了。
            forceloadAdd(client, SeedDimensionProfile.NETHER, 0, 0);
            carveSafeRoom(client, SeedDimensionProfile.NETHER, 0, 100, 0);
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
            sendCommand(client, "gamemode creative @s");
            // 先强制加载 + 封壳再传：远端区块在服务器侧尚未生成时 fill 会回「位置未加载」，
            // 人就落进原始地形（落体 + 岩浆两重噪声）。区块「未加载时仍可预测」这条证据在上一段
            // 已经取完，这里由装置自己加载它，不影响那条判据的成立。
            forceloadAdd(client, SeedDimensionProfile.NETHER, farTarget.x(), farTarget.z());
            carveSafeRoom(client, SeedDimensionProfile.NETHER, x, 100, z);
            armOwnTeleportGrace();
            sendCommand(client, "execute in minecraft:the_nether run tp @s "
                    + (x + 0.5) + " 100 " + (z + 0.5));
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
        if (!inDimension(client, SeedDimensionProfile.NETHER)) {
            // 身份随维度走：人一旦离开下界，下界证据随即作废 —— 这里的读数就不再代表下界口径。
            report("");
            report("六、下界自动挖矿口径（238：下界专属验证）");
            report("  **装置发现玩家不在下界（当前 " + client.level.dimension().identifier()
                    + "）：外部操作打断了取证，本段读数不成立**");
            VERDICTS.add("【判定】下界自动挖矿 238 口径：不通过（取证时玩家不在下界 —— 环境被外部打断）");
            stage = Stage.SHUTDOWN_WORKER;
            waitTicks = 0;
            return;
        }
        report("");
        report("六、下界自动挖矿口径（238：下界专属验证）");
        report("  维度档案：" + SERVICE.predictModelCn());
        report("  下界自动挖矿：" + SERVICE.netherAutoMiningAllowedCn());
        report("  允许自动挖矿的矿物（下界）：" + SERVICE.autoMinerEligibleOresCn());
        boolean allNetherOresEligible = true;
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            if (!SeedOreRegistry.autoMinerEligible(SeedDimensionProfile.NETHER, oreType)) {
                allNetherOresEligible = false;
            }
        }
        // 真实多人环境里核对 238 口径：放行必须来自「下界专属验证 + 证据覆盖该矿物」，
        // 而不是维度闸门被打开；且主世界（专用服务器）的证据绝不能参与下界判定。
        boolean netherValidated = SERVICE.netherValidationEstablished();
        List<OreType> evidenceOres = SERVICE.validationEvidenceOres();
        boolean overworldEvidenceLeaked = SeedDimensionProfile.OVERWORLD.dimensionId()
                .equals(SERVICE.validationDimensionId());
        boolean perOreConsistent = true;
        boolean noDimensionExcuse = true;
        for (OreType oreType : SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER)) {
            boolean allowed = SERVICE.mayUseForAutomatedMining(oreType);
            boolean expected = netherValidated && evidenceOres.contains(oreType);
            if (allowed != expected) {
                perOreConsistent = false;
            }
            String reason = SERVICE.automatedMiningBlockReasonCn(oreType);
            if (!reason.isEmpty() && (reason.contains("未开放") || reason.contains("不受支持"))) {
                noDimensionExcuse = false;
            }
        }
        report("  下界专属验证：" + netherValidated + "；证据覆盖的矿物："
                + SeedMiningService.describeOresCn(evidenceOres)
                + "；证据维度：" + (SERVICE.validationDimensionId().isEmpty()
                        ? "无" : SERVICE.validationDimensionId()));
        OreType chased = SERVICE.autoMiningTargetOre();
        report("  当前追的矿物：" + chased + "；闸门原因：" + SERVICE.automatedMiningBlockReasonCn(chased));
        VERDICTS.add("【判定】下界自动挖矿 238 口径：静态资格全开（"
                + SeedOreRegistry.oresOf(SeedDimensionProfile.NETHER).size() + " 种）"
                + verdict(allNetherOresEligible)
                + " / 逐种矿物放行 =「下界专属验证 ∧ 证据覆盖该矿」" + verdict(perOreConsistent)
                + " / 拦下原因不含维度话术" + verdict(noDimensionExcuse)
                + " / 主世界证据未参与下界" + verdict(!overworldEvidenceLeaked));
        stage = Stage.MINE_NEXT;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 下界实际挖掘（238：逐种下界矿物走一遍「候选 → 目标 → 真实方块被挖掉」）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 换到下一种下界矿物；全部跑完就进入收尾。
     *
     * <p>为什么必须有这一段：237 的「下界自动挖矿」只证到闸门（fail-closed）。238 把下界放开之后，
     * 「放行」不等于「真能挖」—— 本段让自动挖矿在下界真的挖掉一颗，逐矿物留下完整证据链。</p>
     */
    private static void tickMineNext(Minecraft client) {
        if (mineIndex >= MINE_ORES.size()) {
            workerPid = SERVICE.calculatorPid();
            report("");
            report("九、Worker 无孤儿");
            report("  收尾前计算器 PID=" + workerPid + "；启动次数 " + SERVICE.calculatorStartCount()
                    + "；状态 " + SERVICE.calculatorStateCn());
            stage = Stage.SHUTDOWN_WORKER;
            waitTicks = 0;
            return;
        }
        if (module() == null) {
            report("  **自动挖矿模块不可用：下界实际挖掘无法验收**");
            VERDICTS.add("【判定】下界实际挖掘：不通过（模块不可用）");
            mineIndex = MINE_ORES.size();
            return;
        }
        mineOre = MINE_ORES.get(mineIndex);
        mineCandidate = null;
        mineTruthBefore = "（未读）";
        mineLocked = null;
        mineLockedIsCandidate = false;
        mineBroken = false;
        mineSceneSeen = false;
        mineBrokenTicks = 0;
        mineSwitched = false;
        mineStep = 0;
        mineSetAttempts = 0;
        mineDisabledTicks = 0;
        mineGoWildInjections = 0;
        mineGoWildIdleTicks = 0;
        report("");
        report("八之" + (mineIndex + 1) + "、下界实际挖掘：" + mineOre.displayNameCn()
                + "（第 " + (mineIndex + 1) + "/" + MINE_ORES.size() + " 种）");
        // 只勾这一种：种子模式追哪种矿由种子页勾选唯一决定（一次一种，与自动挖矿设置口径一致）
        setOnlyOre(mineOre);
        report("  种子页勾选：只留「" + mineOre.displayNameCn() + "」；当前被追矿物＝"
                + SERVICE.autoMiningTargetOre());
        int cx = NETHER_TARGET.x() * 16 + 8;
        int cz = NETHER_TARGET.z() * 16 + 8;
        mineStandX = cx;
        mineStandZ = cz;
        sendCommand(client, "gamemode creative @s");
        // 强制加载候选所在区块（候选全在这儿）+ 把站位的原地形换成「封壳房间」+ 把玩家送过去
        // （直接 tp 进原始地形会被岩浆湖包住：实测观感就是「传进岩浆里」）
        forceloadAdd(client, SeedDimensionProfile.NETHER, NETHER_TARGET.x(), NETHER_TARGET.z());
        carveSafeRoom(client, SeedDimensionProfile.NETHER, cx, MINE_STAND_Y, cz);
        armOwnTeleportGrace();
        sendCommand(client, "execute in minecraft:the_nether run tp @s "
                + (cx + 0.5) + " " + MINE_STAND_Y + " " + (cz + 0.5));
        sendCommand(client, "give @s minecraft:diamond_pickaxe 1");
        sendCommand(client, "give @s minecraft:diamond_sword 1");
        sendCommand(client, "give @s minecraft:cooked_beef 8");
        report("  站位：" + cx + "," + MINE_STAND_Y + "," + cz + "（区块 "
                + NETHER_TARGET.x() + "," + NETHER_TARGET.z() + " 已强制加载；创造模式，不会摔伤）");
        stage = Stage.MINE_SETUP;
        waitTicks = 0;
    }

    /** 等候选 → 读真值 → 摆现场 → 启动自动挖矿。 */
    private static void tickMineSetup(Minecraft client) {
        if (!inDimension(client, SeedDimensionProfile.NETHER)) {
            report("  **当前不在下界，无法继续**");
            VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn() + "：不通过（不在下界）");
            nextMineOre(client);
            return;
        }
        AutoMinerModule module = module();
        if (module == null) {
            report("  **自动挖矿模块不可用**");
            VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn() + "：不通过（模块不可用）");
            nextMineOre(client);
            return;
        }
        switch (mineStep) {
            case 0 -> {
                mineCandidate = pickCandidate(client);
                if (mineCandidate == null) {
                    if (++waitTicks > WAIT_MINE_TICKS) {
                        report("  **等待候选超时：覆盖内没有 " + mineOre.displayNameCn() + " 的候选**");
                        VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn()
                                + "：不通过（覆盖内无候选）");
                        nextMineOre(client);
                    }
                    return;
                }
                waitTicks = 0;
                mineStep = 1;
            }
            case 1 -> {
                // 先读候选处的真实方块：这是「预测命中」的证据（还没有动过现场）
                mineTruthBefore = blockIdAt(client, mineCandidate);
                report("  候选 " + posText(mineCandidate) + "（玩家 "
                        + posText(client.player.blockPosition()) + "，区块距离 "
                        + chunkDistance(client, mineCandidate) + "）");
                report("  候选处真实方块：" + mineTruthBefore + "（期望 " + oreBlockId(mineOre)
                        + "）" + verdict(oreBlockId(mineOre).equals(mineTruthBefore)));
                // 摆现场：把候选那一格周围开成一间「封了壳的房间」——下界 y≈20 这一层到处是岩浆湖，
                // 只掏一个 3×3×3 的小口袋，玩家上半身仍埋在原地形里，观感就是「传进岩浆里」（实测踩过）。
                // 房间 5×5×7（内空）+ 六面下界岩外壳，令现场与地形彻底无关：没有岩浆、没有火、没有落体。
                int x = mineCandidate.getX();
                int y = mineCandidate.getY();
                int z = mineCandidate.getZ();
                carveSafeRoom(client, SeedDimensionProfile.NETHER, x, y, z);
                sendCommand(client, "execute in minecraft:the_nether run setblock "
                        + x + " " + y + " " + z + " " + oreBlockId(mineOre));
                // 装置【不】自己把玩家摆到这一颗旁边：走「前往野外」这条既有链路。
                // 但「前往野外」绑的是站位点（人本来就在那儿，幂等、不挪人）—— 真正的位移由装置在
                // 状态机空闲地等的时候补上一跳（见 tickMineRun）。原因：状态机判「传送是否生效」只看
                // 单刻大跳变，模块自己那一跳常常落在它自己的等待窗里被吃掉，于是永远进不了挖矿态
                // （实测：模块开着、聊天栏只有「发指令 tp」，一颗不挖）。与主世界 A~L 装置同一做法。
                String standTp = "execute in minecraft:the_nether run tp @s "
                        + (mineStandX + 0.5) + " " + MINE_STAND_Y + " " + (mineStandZ + 0.5);
                // 自动挖矿配成「种子目标模式 + 秒破 + 本矿物」的最小集（与主世界 A~L 装置同一套口径）
                MiningSettings settings = module.settings();
                settings.personalMode = false;
                settings.overworldOreTarget = "";
                settings.netherOreTarget = anchorId(mineOre);
                settings.blockTarget = "";
                settings.lootMode = LootMode.FORTUNE;
                settings.fastBreak = true;
                settings.veinMiner = true;
                settings.breakSpawner = false;
                settings.hungerThreshold = 1;
                settings.unloadThreshold = 36;
                settings.autoDisconnect = false;
                settings.statusBroadcast = false;
                settings.pathViewFollow = false;
                settings.teleportRetryEnabled = false;
                settings.wildCommand = standTp;
                settings.unloadCommand = standTp;
                settings.supplyCommand = standTp;
                settings.afkCommand = standTp;
                settings.respawnCommand = standTp;
                report("  自动挖矿配置：目标矿物＝" + mineOre.displayNameCn()
                        + "（锚点 " + settings.netherOreTarget + "）+ 秒破 + 种子目标模式");
                // 启动自检的其余缺项（与主世界 A~L 装置同一套做法）：
                //   ① 三个点位都绑到这一颗的落脚点（装置不走物流，只为让自检通过）；
                //   ② 食物白名单里要有手上那种食物（专用服务器的配置作用域里是空的）。
                for (MiningPointType type : MiningPointType.values()) {
                    module.pointStore().set(type, new MiningPoint(x, y + 1, z,
                            WorldIdentity.dimension(), 0f, 0f));
                }
                if (!settings.foodWhitelist.contains("minecraft:cooked_beef")) {
                    settings.foodWhitelist.add("minecraft:cooked_beef");
                }
                module.persistSettings();
                report("  自检缺项已清零：三点位＝落脚点 / 食物白名单含熟牛肉 / 镐与剑在背包");
                mineStep = 2;
                waitTicks = 0;
            }
            case 2 -> {
                // 先确认现场就位：指令是异步的，必须亲眼看到那一格真的是目标矿，才允许启动模块
                // （实测过：若不等它，客户端可能先收到「空气」那一帧，把还没摆好的现场误判成「已被挖掉」）
                String atCandidate = blockIdAt(client, mineCandidate);
                if (!oreBlockId(mineOre).equals(atCandidate)) {
                    if (++waitTicks > 20 * 15) {
                        if (mineSetAttempts < 3) {
                            mineSetAttempts++;
                            report("  现场未就位（" + atCandidate + "）→ 重新放回目标矿（第 "
                                    + mineSetAttempts + " 次）");
                            sendCommand(client, "execute in minecraft:the_nether run setblock "
                                    + mineCandidate.getX() + " " + mineCandidate.getY() + " "
                                    + mineCandidate.getZ() + " " + oreBlockId(mineOre));
                            waitTicks = 0;
                            return;
                        }
                        report("  **现场始终未就位：" + atCandidate + "**");
                        VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn()
                                + "：不通过（现场未就位）");
                        nextMineOre(client);
                        return;
                    }
                    return;
                }
                mineSceneSeen = true;
                report("  现场就位：候选处真实方块＝" + atCandidate + "（挖掘判据从这里开始）");
                module.setSeedTargetMode(true);
                ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, true);
                report("  已启动自动挖矿；该矿物的闸门原因（应为空）：「"
                        + SERVICE.automatedMiningBlockReasonCn(mineOre) + "」");
                stage = Stage.MINE_RUN;
                waitTicks = 0;
            }
            default -> stage = Stage.MINE_RUN;
        }
    }

    /** 等提供者锁定候选，并等真实方块被挖掉。 */
    private static void tickMineRun(Minecraft client) {
        if (!inDimension(client, SeedDimensionProfile.NETHER)) {
            report("  **当前不在下界，无法继续**");
            VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn() + "：不通过（不在下界）");
            nextMineOre(client);
            return;
        }
        AutoMinerModule module = module();
        if (module == null) {
            VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn() + "：不通过（模块不可用）");
            nextMineOre(client);
            return;
        }
        // 「前往野外」空闲地等一个真实位移：由装置补上那一跳（与主世界 A~L 装置 advanceIntoMining 同一做法）。
        // 补给目标取「离玩家更远的那一端」——站位点与候选点相隔 40 格以上，怎么取都是真实大位移。
        if (module.isEnabled() && module.fsm().state() == MinerState.GO_WILD) {
            if (!module.getCmdManager().isCommandExecuting() && ++mineGoWildIdleTicks > 20
                    && mineGoWildInjections < MAX_GO_WILD_INJECTIONS) {
                mineGoWildInjections++;
                mineGoWildIdleTicks = 0;
                BlockPos candidateFoot = new BlockPos(mineCandidate.getX(), mineCandidate.getY() + 1,
                        mineCandidate.getZ());
                BlockPos standFoot = new BlockPos(mineStandX, MINE_STAND_Y, mineStandZ);
                BlockPos playerPos = client.player.blockPosition();
                BlockPos dest = playerPos.distSqr(candidateFoot) >= playerPos.distSqr(standFoot)
                        ? candidateFoot : standFoot;
                report("  「前往野外」等不到位移 → 装置补一跳真传送（第 " + mineGoWildInjections + " 次）："
                        + posText(dest));
                armOwnTeleportGrace();
                sendCommand(client, "execute in minecraft:the_nether run tp @s "
                        + (dest.getX() + 0.5) + " " + dest.getY() + " " + (dest.getZ() + 0.5));
            }
            return;
        }
        mineGoWildIdleTicks = 0;
        // 模块没在运行 = 启动自检没通过（缺项由模块自己播报）。装置如实报出来，不干等。
        if (!module.isEnabled()) {
            if (++mineDisabledTicks > 20 * 5) {
                List<String> missing = module.selfCheck();
                report("  **模块未在运行（启动自检未通过）**："
                        + (missing.isEmpty() ? "（模块未给出缺项读数）"
                        : String.join(" / ", missing.subList(0, Math.min(3, missing.size())))));
                VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn()
                        + "：不通过（启动自检未通过）");
                nextMineOre(client);
                return;
            }
            return;
        }
        mineDisabledTicks = 0;
        if (mineLocked == null) {
            BlockPos locked = module.miningTargetProvider().lockedTargetOrNull();
            if (locked != null) {
                mineLocked = locked;
                mineLockedIsCandidate = locked.equals(mineCandidate);
                report("  提供者锁定目标：" + posText(locked) + "；属于本矿物候选 "
                        + verdict(isPredictedCandidate(locked)) + "；就是装置摆的那一颗 "
                        + verdict(mineLockedIsCandidate));
            }
        }
        // 以「提供者锁定的那一颗」为准读真值；还没锁定时看装置摆的那一颗
        BlockPos probe = mineLocked != null ? mineLocked : mineCandidate;
        String blockId = blockIdAt(client, probe);
        String expected = oreBlockId(mineOre);
        if (!mineSceneSeen && expected.equals(blockId)) {
            mineSceneSeen = true; // 先确认现场就是「目标矿」，之后出现 air 才算被挖掉
        }
        if (mineSceneSeen && !mineBroken && blockId.equals("minecraft:air")) {
            mineBroken = true;
            report("  目标位置真实方块：" + blockId + "（已被挖掉）");
        }
        if (mineBroken) {
            if (!mineSwitched && mineLocked != null) {
                BlockPos now = module.miningTargetProvider().lockedTargetOrNull();
                if (now != null && !now.equals(mineLocked)) {
                    mineSwitched = true;
                    report("  挖掉一颗后已换下一颗：" + posText(now));
                }
            }
            // 挖掉之后再给 20 秒看「换下一颗」；换到或到点都收口
            if (mineSwitched || ++mineBrokenTicks > 20 * 20) {
                report("  判定：真实方块 → air（下界自动挖矿真的能挖）；本矿物候选数 "
                        + countCandidates(mineOre));
                VERDICTS.add("【判定】下界实际挖掘 · " + mineOre.displayNameCn()
                        + "：候选命中 " + verdict(expected.equals(mineTruthBefore))
                        + " / 目标来自候选 " + verdict(mineLocked != null && isPredictedCandidate(mineLocked))
                        + " / 真值被挖掉 " + verdict(true)
                        + " / 换下一颗 " + verdict(mineSwitched));
                nextMineOre(client);
            }
            return;
        }
        if (++waitTicks > WAIT_MINE_TICKS) {
            report("  **等待挖掉超时**（目标 " + posText(probe) + "，真实方块 " + blockId
                    + "，模块启用 " + module.isEnabled() + "）");
            VERDICTS.add("【判定】下界实际挖掘 " + mineOre.displayNameCn()
                    + "：不通过（超时未挖掉）");
            nextMineOre(client);
        }
    }

    /** 收尾一种矿物（停模块、解除强制加载），再进入下一种。 */
    private static void nextMineOre(Minecraft client) {
        AutoMinerModule module = module();
        if (module != null) {
            ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            module.setSeedTargetMode(false);
        }
        if (mineCandidate != null) {
            forceloadRemove(client, SeedDimensionProfile.NETHER,
                    mineCandidate.getX() >> 4, mineCandidate.getZ() >> 4);
        }
        forceloadRemove(client, SeedDimensionProfile.NETHER, NETHER_TARGET.x(), NETHER_TARGET.z());
        mineIndex++;
        stage = Stage.MINE_NEXT;
        waitTicks = 0;
    }

    /** 声明「接下来这一跳是我方传送」：装置自己发的 /tp 不该被手动传送守卫判成玩家传送。 */
    private static void armOwnTeleportGrace() {
        AutoMinerModule module = module();
        if (module != null) {
            module.fsm().armOwnTeleportGraceForDev();
        }
    }

    /**
     * 在 {@code (x,y,z)} 处开一间「封了壳的房间」：内空 5×5×7（{@code y-1 … y+5}）+ 六面下界岩外壳。
     *
     * <p>为什么不是只掏一个 3×3×3 的小口袋：下界这一层到处是岩浆湖与悬空岩浆，小口袋只清到
     * {@code y+1}，玩家 tp 进去后上半身仍埋在原地形里 —— 观感就是「传进岩浆里」，而且岩浆会流回来。
     * 六面封壳之后现场只由装置决定：没有岩浆、没有火、没有落体，挖掘判据不掺地形噪声。</p>
     */
    private static void carveSafeRoom(Minecraft client, SeedDimensionProfile dim, int x, int y, int z) {
        String d = dim.dimensionId();
        // 先掏空内部，再让外壳覆盖它的六个面
        fill(client, d, x - 2, y - 1, z - 2, x + 2, y + 5, z + 2, "minecraft:air");
        fill(client, d, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, "minecraft:netherrack");
        fill(client, d, x - 3, y + 6, z - 3, x + 3, y + 6, z + 3, "minecraft:netherrack");
        fill(client, d, x - 3, y, z - 3, x - 3, y + 5, z + 3, "minecraft:netherrack");
        fill(client, d, x + 3, y, z - 3, x + 3, y + 5, z + 3, "minecraft:netherrack");
        fill(client, d, x - 2, y, z - 3, x + 2, y + 5, z - 3, "minecraft:netherrack");
        fill(client, d, x - 2, y, z + 3, x + 2, y + 5, z + 3, "minecraft:netherrack");
    }

    /** 以 /fill 摆一方块（装置的所有现场改造都走这一条，便于一眼看全）。 */
    private static void fill(Minecraft client, String dimensionId,
                             int x1, int y1, int z1, int x2, int y2, int z2, String blockId) {
        sendCommand(client, "execute in " + dimensionId + " run fill "
                + x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2 + " " + blockId);
    }

    /**
     * 强制加载一个区块。
     *
     * <p><b>{@code /forceload} 收的是方块坐标</b>（命令内部再折算成区块号）：把区块号直接当参数传进去
     * 会加载到<b>别的</b>区块上 —— 实测传 {@code (-3, 4)} 被折算成区块 {@code (-1, 0)}，于是现场所有
     * {@code fill} 回一句 "That position is not loaded"，房间一个都没建成，人最后被 tp 进未改造的原始
     * 地形里（下界那一层就是岩浆）。这里统一用 {@code chunkX << 4} 换成方块坐标。</p>
     */
    private static void forceloadAdd(Minecraft client, SeedDimensionProfile dim, int chunkX, int chunkZ) {
        sendCommand(client, "execute in " + dim.dimensionId() + " run forceload add "
                + (chunkX << 4) + " " + (chunkZ << 4));
    }

    /** 解除强制加载（同一口径：参数是方块坐标）。 */
    private static void forceloadRemove(Minecraft client, SeedDimensionProfile dim, int chunkX, int chunkZ) {
        sendCommand(client, "execute in " + dim.dimensionId() + " run forceload remove "
                + (chunkX << 4) + " " + (chunkZ << 4));
    }

    /** 自动挖矿模块（不可用时返回 {@code null}）。 */
    private static AutoMinerModule module() {
        return ModuleManager.byId(AutoMinerModule.MODULE_ID) instanceof AutoMinerModule module ? module : null;
    }

    /** 从预测缓存里挑「离玩家最近、且落在当前覆盖方框内」的本矿物候选。 */
    private static BlockPos pickCandidate(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null) {
            return null;
        }
        BlockPos playerPos = player.blockPosition();
        ChunkPos playerChunk = ChunkPos.containing(playerPos);
        int radius = SERVICE.coverageRadius();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (PredictionResult result : SERVICE.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.oreType() != mineOre) {
                    continue;
                }
                BlockPos pos = ore.position();
                if (Math.abs((pos.getX() >> 4) - playerChunk.x()) > radius
                        || Math.abs((pos.getZ() >> 4) - playerChunk.z()) > radius) {
                    continue;
                }
                // 跳过已被观察层判「当前缺失」的候选：提供者的优先级里它不作为目标
                if (SERVICE.observationState(pos) == OreObservationState.MISSING) {
                    continue;
                }
                double distance = playerPos.distSqr(pos);
                if (distance < bestDistance) {
                    best = pos;
                    bestDistance = distance;
                }
            }
        }
        return best;
    }

    /** 该坐标是不是本矿物的正式预测候选（只读预测缓存，不看真实世界）。 */
    private static boolean isPredictedCandidate(BlockPos pos) {
        if (pos == null) {
            return false;
        }
        for (PredictionResult result : SERVICE.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.oreType() == mineOre && ore.position().equals(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 本矿物在正式定义里的第一个方块 id（下界三种都没有深层变种）。 */
    private static String oreBlockId(OreType oreType) {
        SeedOreDefinition definition = SeedOreRegistry.of(SeedDimensionProfile.NETHER, oreType);
        if (definition == null || definition.blocks().isEmpty()) {
            return "minecraft:air";
        }
        return BuiltInRegistries.BLOCK.getKey(definition.blocks().get(0)).toString();
    }

    /** 自动挖矿设置里的锚点（时运模式存产物物品 id；与正式层的下界时运表逐项一致）。 */
    private static String anchorId(OreType oreType) {
        return switch (oreType) {
            case ANCIENT_DEBRIS -> "minecraft:ancient_debris";
            case NETHER_QUARTZ -> "minecraft:quartz";
            case NETHER_GOLD -> "minecraft:gold_nugget";
            default -> "";
        };
    }

    /** 客户端看到的这一格真实方块 id（专用服务器下即为服务端权威状态同步过来的结果）。 */
    private static String blockIdAt(Minecraft client, BlockPos pos) {
        ClientLevel level = client.level;
        if (level == null || pos == null || !level.isLoaded(pos)) {
            return "（未加载）";
        }
        return BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock()).toString();
    }

    /** 本矿物在预测缓存里的候选总数。 */
    private static int countCandidates(OreType oreType) {
        int counter = 0;
        for (PredictionResult result : SERVICE.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.oreType() == oreType) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static String posText(BlockPos pos) {
        return pos == null ? "（无）" : pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    /** 目标相对玩家的区块切比雪夫距离（判「是否在覆盖方框内」用）。 */
    private static int chunkDistance(Minecraft client, BlockPos pos) {
        if (pos == null || client.player == null) {
            return -1;
        }
        BlockPos playerPos = client.player.blockPosition();
        return Math.max(Math.abs((pos.getX() >> 4) - (playerPos.getX() >> 4)),
                Math.abs((pos.getZ() >> 4) - (playerPos.getZ() >> 4)));
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
