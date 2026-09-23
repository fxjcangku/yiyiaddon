package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import com.yiyiaddon.seed.service.SeedMiningService;
import com.yiyiaddon.seed.validation.SeedValidationEvidence;
import com.yiyiaddon.seed.validation.SeedValidationEvidenceGroup;
import com.yiyiaddon.seed.validation.SeedValidationPolicy;
import com.yiyiaddon.seed.validation.SeedValidationSnapshot;
import com.yiyiaddon.seed.validation.SeedValidationState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第六阶段（234）· <b>种子验证实机回归装置（仅开发）</b>。
 *
 * <p>它验证的是 234 口径第四十二~五十节要求的五组结论，全部在<b>真实客户端 + 真实专用服务器</b>
 * （种子 {@code 20260922}，账号需 OP）上顺序跑：</p>
 * <ol>
 *     <li><b>正确种子</b>：多目标区块收集 → 必须进入「已验证」，并记录「用了多少区块 / 多少有效确认单元 /
 *         多少确认候选」；</li>
 *     <li><b>被挖矿容错</b>：验证通过后 dev-only 用 {@code /setblock air} 分两批移除已确认钻石
 *         （先 20%、再补到 40%），验证<b>必须保持</b>「已验证」——普通缺失不允许把验证打回去；</li>
 *     <li><b>策略级合成用例</b>：单区块 45/45 确认、只有一个有效证据单元、以及 234.1 的
 *         保守独立性六例（同一 placement 空间断裂 / UNKNOWN provenance 空间断裂 / 重复描述同一位置…）
 *         —— 全部<b>不得</b>被错误计票（口径第四十三、四十四节与 234.1 第十六节）。这一组是
 *         <b>策略级</b>用例：用真实策略代码喂入构造好的证据，不是实机，报告里如实标注；</li>
 *     <li><b>错误种子矩阵</b>：客户端分别填 {@code 12345 / 2 / 0 / -7777}，全部<b>不得</b>进入已验证，
 *         并逐颗记录读数（单元 / 确认单元 / 覆盖区块 / 解释比例）；</li>
 *     <li><b>清理矩阵</b>：改种子 / 换服务器（A→B）/ 换维度（下界往返）——验证必须立刻清空，
 *         回到「未验证」，且不得携带上一套身份的证据。</li>
 * </ol>
 *
 * <p><b>为什么要实机</b>：验证证据只能由「客户端合法观察到的真实方块」产生（口径第五十八节：
 * 不得直接读 Worker 的 ServerLevel、不得主动加载区块），离线装置造不出可信样本。</p>
 *
 * <p><b>前置条件</b>：{@code .\gradlew.bat runSeedWorkerServer}（种子 20260922，端口 25565，
 * 已写好 ops.json 给测试账号管理员权限，用例要发 {@code /setblock}、{@code /tp}、
 * {@code /execute in}）。参数全部走系统属性，不落盘、不进正式产物。</p>
 */
public final class SeedValidationRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "234验证回归";

    /** 错误种子矩阵（口径第四十二节；全部是「与服务器世界 20260922 不同」的种子）。 */
    private static final List<Long> WRONG_SEEDS = List.of(12345L, 2L, 0L, -7777L);

    /** 单个用例的等待上限（客户端刻）。 */
    private static final int WAIT_WORLD_TICKS = 20 * 180;
    private static final int WAIT_VERIFY_TICKS = 20 * 300;
    private static final int WAIT_OBSERVE_TICKS = 20 * 120;
    private static final int WAIT_COVERAGE_TICKS = 20 * 300;
    private static final int WAIT_SETTLE_TICKS = 20 * 10;

    /** 每刻发送的 {@code /setblock} 条数（避免一帧塞爆连接，又不至于太慢）。 */
    private static final int MINE_COMMANDS_PER_TICK = 2;

    /** 单批移除的指令上限（防止把整个区域挖空导致用例失真）。 */
    private static final int MINE_BATCH_LIMIT = 600;

    /** 步骤状态机。 */
    private enum Step {
        WAIT_WORLD,
        WAIT_VERIFIED,
        WAIT_FULL_COVERAGE,
        MINE_FIRST_PREPARE,
        MINE_FIRST,
        WAIT_MINE_FIRST,
        MINE_SECOND_PREPARE,
        MINE_SECOND,
        WAIT_MINE_SECOND,
        SYNTHETIC_POLICY,
        SEED_CHANGE,
        WAIT_SEED_CHANGE,
        WRONG_SEED_PREPARE,
        WAIT_WRONG_SEED,
        WAIT_RESTORE_VERIFIED,
        SWITCH_TO_B,
        WAIT_SERVER_B,
        WAIT_B_EVIDENCE,
        DIMENSION_TO_NETHER,
        WAIT_NETHER,
        DIMENSION_BACK,
        WAIT_OVERWORLD,
        FINISHED
    }

    private static Step step = Step.WAIT_WORLD;
    private static int waitTicks;

    // ── 参数 ──
    private static long seed = 20260922L;
    private static int radius = 3;
    private static int minePercent = 20;
    private static String serverBKey = "";
    private static boolean connectIssued;

    // ── 运行状态 ──
    private static SeedValidationState lastState = SeedValidationState.UNVERIFIED;
    private static int wrongIndex;
    private static long currentSeed = Long.MIN_VALUE;

    /** 待移除的候选位置队列（分批发指令）。 */
    private static final List<BlockPos> mineQueue = new ArrayList<>();
    private static int mineSent;

    /** 第一批移除前的基线。 */
    private static int baselineConfirmedUnits;
    private static int baselineConfirmedPositions;
    private static int baselineMissing;

    /** 换服前的 A 侧基线。 */
    private static int aEvidenceCount;
    private static String aStateCn = "";

    /** 证据计数与违规计数。 */
    private static int verifiedSeen;
    private static int minedToleranceSeen;
    private static int syntheticSeen;
    private static int wrongSeedSeen;
    private static int clearedSeen;
    private static int violations;

    private SeedValidationRegression() {
    }

    /** 读取本次运行的参数（系统属性，缺失即默认）。 */
    public static void configure() {
        seed = SeedPocFlags.validationSeed();
        radius = SeedPocFlags.validationRadius();
        minePercent = SeedPocFlags.validationMinePercent();
        serverBKey = SeedPocFlags.validationServerB();
        if (!serverBKey.isEmpty()) {
            String canonical = WorldIdentity.canonicalServerKey(serverBKey);
            serverBKey = canonical == null ? "" : canonical;
        }
        LOGGER.info("{}：参数 正确种子 {} / 覆盖半径 {} / 逐批移除 {}% / 错误种子矩阵 {} / 换服用例 {}",
                TAG, seed, radius, minePercent, WRONG_SEEDS,
                serverBKey.isEmpty() ? "关" : "开（B = " + serverBKey + "）");
        LOGGER.info("{}：验证阈值 {}", TAG, SeedValidationPolicy.thresholdsCn());
        LOGGER.info("{}：可疑语义 —— {}", TAG, com.yiyiaddon.seed.validation.SeedSuspicionPolicy.disabledReasonCn());
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
            // 换服等待期本来就要求「不在任何世界里」（断开 A 与连上 B 之间）
            if (step == Step.WAIT_SERVER_B) {
                waitServerB(client);
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
            case WAIT_WORLD -> prepare(client);
            case WAIT_VERIFIED -> waitVerified(client);
            case WAIT_FULL_COVERAGE -> waitFullCoverage(client);
            case MINE_FIRST_PREPARE -> mineFirstPrepare(client);
            case MINE_FIRST -> mineBatch(client, Step.WAIT_MINE_FIRST);
            case WAIT_MINE_FIRST -> waitMineFirst(client);
            case MINE_SECOND_PREPARE -> mineSecondPrepare(client);
            case MINE_SECOND -> mineBatch(client, Step.WAIT_MINE_SECOND);
            case WAIT_MINE_SECOND -> waitMineSecond(client);
            case SYNTHETIC_POLICY -> syntheticPolicy(client);
            case SEED_CHANGE -> seedChange(client);
            case WAIT_SEED_CHANGE -> waitSeedChange(client);
            case WRONG_SEED_PREPARE -> wrongSeedPrepare(client);
            case WAIT_WRONG_SEED -> waitWrongSeed(client);
            case WAIT_RESTORE_VERIFIED -> waitRestoreVerified(client);
            case SWITCH_TO_B -> switchToServerB(client);
            case WAIT_SERVER_B -> waitServerB(client);
            case WAIT_B_EVIDENCE -> waitServerBEvidence(client);
            case DIMENSION_TO_NETHER -> dimensionToNether(client);
            case WAIT_NETHER -> waitNether(client);
            case DIMENSION_BACK -> dimensionBack(client);
            case WAIT_OVERWORLD -> waitOverworld(client);
            case FINISHED -> {
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第一段：正确种子 → 多目标区块 → 必须「已验证」
    // ────────────────────────────────────────────────────────────────────────

    /** 进世界后：写设置 + 开覆盖，并清出一个干净的证据收集窗口。 */
    private static void prepare(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        if (client.getSingleplayerServer() != null) {
            LOGGER.warn("{}：当前是单人世界；正确种子用例要求「服务器世界种子 = {}」，"
                    + "请改用 127.0.0.1:25565 的专用服务器", TAG, seed);
        }
        service.setEnabled(true);
        service.setSeedText(Long.toString(seed));
        service.setCoverageRadius(radius);
        // 干净窗口：先清空验证证据并设置收集起点（此时区块还没被观察），再开启覆盖
        service.restartValidation();
        service.setRenderPrediction(true);
        LOGGER.info("{}：第一段开跑 —— 客户端种子 {}（服务器世界种子 {}），范围 {}（{} 个目标区块）；"
                        + "预期：多个目标区块上的多个有效证据单元被确认 → 进入「已验证」",
                TAG, seed, seed, radius, (2 * radius + 1) * (2 * radius + 1));
        step = Step.WAIT_VERIFIED;
        waitTicks = 0;
    }

    /** 等验证进入「已验证」；到点记录「用了多少区块 / 多少组」。 */
    private static void waitVerified(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        if (snapshot.state() != lastState) {
            LOGGER.info("{}：验证状态 {} → {}（{}）", TAG, lastState.displayNameCn(),
                    snapshot.stateCn(), snapshot.reasonCn());
            lastState = snapshot.state();
        }
        if (snapshot.state() == SeedValidationState.VERIFIED) {
            verifiedSeen++;
            LOGGER.info("{}：已验证证据 #{} → 有效确认单元 {} 个 / 覆盖 {} 个目标区块 / 已观察 {} 个单元 / "
                            + "解释比例 {}；有效样本区块 {}；当前已确认候选 {} / 缺失 {}；"
                            + "证据条数 {}，候选方块 {}（渲染快照）",
                    TAG, verifiedSeen, snapshot.confirmedUnits(), snapshot.confirmedChunks(),
                    snapshot.observedUnits(), snapshot.ratioCn(), snapshot.sampleChunks(),
                    snapshot.confirmedPositions(), snapshot.missingPositions(),
                    service.validationEvidence().size(), service.renderSnapshot().stats().candidates());
            logGroupSample(service);
            LOGGER.info("{}：AutoMiner 安全门 mayUseForAutomatedMining() = {}（只有已验证才允许 true；"
                    + "本阶段仍未接 AutoMiner）", TAG, service.mayUseForAutomatedMining());
            // 等覆盖真的铺满再动容错用例：否则「移除 20%」会基于一个还没铺开的很小基数，
            // 那样测的就不是「被挖矿容错」，而是「覆盖还没铺满」（第一版就是这么被自己坑到的）
            step = Step.WAIT_FULL_COVERAGE;
            waitTicks = 0;
            return;
        }
        if (++waitTicks > WAIT_VERIFY_TICKS) {
            fail(client, "等待验证通过超时（当前「" + snapshot.stateCn() + "」，有效确认单元 "
                    + snapshot.confirmedUnits() + " 个 / 覆盖 " + snapshot.confirmedChunks()
                    + " 个区块 / 已观察 " + snapshot.observedUnits() + " 个单元；" + snapshot.reasonCn() + "）");
        }
    }

    /** 等覆盖铺满且全部候选都被观察过：由此得到一个「满覆盖」的验证读数作为容错用例的基线。 */
    private static void waitFullCoverage(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        int unobserved = service.renderSnapshot().stats().unobserved();
        if (total == 0 || done < total || unobserved != 0) {
            if (++waitTicks > WAIT_VERIFY_TICKS) {
                fail(client, "等待覆盖铺满超时（已预测 " + done + "/" + total + "，未观察 " + unobserved + "）");
            }
            return;
        }
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        LOGGER.info("{}：满覆盖读数 —— 目标区块 {} 个 / 候选 {} 个；验证「{}」：有效确认单元 {} 个 / "
                        + "覆盖 {} 个目标区块 / 已观察 {} 个单元 / 解释比例 {}；有效样本区块 {}；"
                        + "当前已确认候选 {} / 缺失 {}",
                TAG, total, service.renderSnapshot().stats().candidates(), snapshot.stateCn(),
                snapshot.confirmedUnits(), snapshot.confirmedChunks(), snapshot.observedUnits(),
                snapshot.ratioCn(), snapshot.sampleChunks(), snapshot.confirmedPositions(),
                snapshot.missingPositions());
        // 满覆盖后再做一次分组审计：此时才是「全部候选都在场」的口径，
        // 报告里的 (0,0) 分组审计与「旧口径 vs 新口径」总账都取这一份（口径第十四、十五节）
        LOGGER.info("{}：以下为【满覆盖口径】的分组审计（{} 个目标区块全部铺开、全部候选已观察）",
                TAG, total);
        logGroupSample(service);
        step = Step.MINE_FIRST_PREPARE;
        waitTicks = 0;
    }

    /**
     * 打印分组审计与单元样本（口径第十三、十四节取证）。
     *
     * <p>它做两件事：① 用<b>同一批证据</b>同时跑「234 旧口径（26 邻域连通簇）」与
     * 「234.1 新口径（conservative independence）」，把两者数量并排打出来；
     * ② 把每个已确认单元整段打印（单元编号 / provenance / 全部成员坐标），
     * 让「N 个确认单元究竟来自几次世界生成事件」可追溯。</p>
     */
    private static void logGroupSample(SeedMiningService service) {
        List<SeedValidationEvidence> evidence = new ArrayList<>(service.validationEvidence());
        List<SeedValidationEvidenceGroup> legacy = SeedValidationEvidenceGroup.buildLegacySpatialForAudit(evidence);
        List<SeedValidationEvidenceGroup> units = new ArrayList<>(service.validationGroups());
        LOGGER.info("{}：分组审计 —— 同一批证据 {} 条：旧口径（目标区块+写入者+26 邻域连通簇）{} 个 / "
                        + "新口径（目标区块+写入者+来源+矿物，桶内不裂票）{} 个；旧口径曾确认 {} 个 / 新口径曾确认 {} 个",
                TAG, evidence.size(), legacy.size(), units.size(),
                legacy.stream().filter(SeedValidationEvidenceGroup::everConfirmed).count(),
                units.stream().filter(SeedValidationEvidenceGroup::everConfirmed).count());
        logPerChunkAudit(legacy, units, evidence);
        int shown = 0;
        for (String line : service.validationUnitDiagnostics(true)) {
            if (shown++ >= 8) {
                LOGGER.info("{}：单元明细（其余 {} 个已确认单元略）", TAG,
                        units.stream().filter(SeedValidationEvidenceGroup::everConfirmed).count() - shown + 1);
                break;
            }
            LOGGER.info("{}：单元明细 {}", TAG, line);
        }
        LOGGER.info("{}：单元总数 {}（曾经确认 {} 个），候选方块总数 {}（证据条数）",
                TAG, units.size(),
                units.stream().filter(SeedValidationEvidenceGroup::everConfirmed).count(),
                evidence.size());
    }

    /**
     * 按目标区块逐块对照旧口径与新口径（口径第十四、十五节）。
     *
     * <p>输出行固定覆盖四类区块，保证「至少 3 个不同目标区块」且样本形态多样：
     * ① 目标区块 {@code (0,0)}（口径点名的分组审计对象，存在就一定打印）；
     * ② 候选数最多的 3 个；③ 候选数最少的 2 个；④ 调度敏感成员最多的 2 个。
     * 每行给出：候选数 / 旧口径组数 / 新口径单元数 / 确认单元数 / 调度敏感成员数。</p>
     */
    private static void logPerChunkAudit(List<SeedValidationEvidenceGroup> legacy,
                                         List<SeedValidationEvidenceGroup> units,
                                         List<SeedValidationEvidence> evidence) {
        Map<Long, int[]> perChunk = new LinkedHashMap<>();
        for (SeedValidationEvidenceGroup group : legacy) {
            int[] row = perChunk.computeIfAbsent(group.targetChunk().pack(), key -> new int[5]);
            row[0] += group.size();
            row[1] += 1;
        }
        for (SeedValidationEvidenceGroup unit : units) {
            int[] row = perChunk.computeIfAbsent(unit.targetChunk().pack(), key -> new int[5]);
            row[2] += 1;
            if (unit.everConfirmed()) {
                row[3] += 1;
            }
        }
        for (SeedValidationEvidence item : evidence) {
            if (item.certainty() == PredictionCertainty.SCHEDULE_SENSITIVE) {
                perChunk.computeIfAbsent(item.targetChunk().pack(), key -> new int[5])[4]++;
            }
        }
        List<Map.Entry<Long, int[]>> rows = new ArrayList<>(perChunk.entrySet());

        List<Long> chosen = new ArrayList<>();
        chosen.add(ChunkPos.ZERO.pack());
        List<Map.Entry<Long, int[]>> byCandidates = new ArrayList<>(rows);
        byCandidates.sort(Comparator.comparingInt((Map.Entry<Long, int[]> entry) -> -entry.getValue()[0]));
        for (int index = 0; index < Math.min(3, byCandidates.size()); index++) {
            chosen.add(byCandidates.get(index).getKey());
        }
        for (int index = 0; index < Math.min(2, byCandidates.size()); index++) {
            chosen.add(byCandidates.get(byCandidates.size() - 1 - index).getKey());
        }
        List<Map.Entry<Long, int[]>> bySensitive = new ArrayList<>(rows);
        bySensitive.sort(Comparator.comparingInt((Map.Entry<Long, int[]> entry) -> -entry.getValue()[4]));
        for (int index = 0; index < Math.min(2, bySensitive.size()); index++) {
            if (bySensitive.get(index).getValue()[4] > 0) {
                chosen.add(bySensitive.get(index).getKey());
            }
        }

        int printed = 0;
        for (Long key : chosen) {
            int[] row = perChunk.get(key);
            if (row == null) {
                continue;
            }
            ChunkPos chunk = ChunkPos.unpack(key);
            printed++;
            LOGGER.info("{}：区块审计 ({},{}) —— 候选 {} 个；旧口径 {} 组；新口径 {} 个单元（曾确认 {} 个）；"
                            + "调度敏感成员 {} 个",
                    TAG, chunk.x(), chunk.z(), row[0], row[1], row[2], row[3], row[4]);
        }
        LOGGER.info("{}：区块审计共覆盖 {} 个目标区块（上表打印 {} 行：含 (0,0) / 候选最多 3 个 / 候选最少 2 个 / "
                + "调度敏感最多 2 个）", TAG, rows.size(), printed);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第二段：被挖矿容错（口径第十六、十七、四十六节）
    // ────────────────────────────────────────────────────────────────────────

    /** 第一批：移除初始确认候选的 {@code minePercent}%。 */
    private static void mineFirstPrepare(Minecraft client) {
        SeedValidationSnapshot snapshot = SeedMiningService.instance().validationSnapshot();
        baselineConfirmedUnits = snapshot.confirmedUnits();
        baselineConfirmedPositions = snapshot.confirmedPositions();
        baselineMissing = snapshot.missingPositions();
        int target = Math.min(MINE_BATCH_LIMIT, (int) Math.ceil(baselineConfirmedPositions * minePercent / 100d));
        mineQueue.clear();
        mineQueue.addAll(pickConfirmed(Math.max(1, target)));
        mineSent = 0;
        LOGGER.info("{}：被挖矿容错 —— 先移除 {}% 的已确认候选（{} / {} 个，当前验证「{}」，有效确认 {} 个单元）",
                TAG, minePercent, mineQueue.size(), baselineConfirmedPositions,
                snapshot.stateCn(), baselineConfirmedUnits);
        step = Step.MINE_FIRST;
    }

    /** 第二批：把累计移除量补到 {@code 2 × minePercent}%。 */
    private static void mineSecondPrepare(Minecraft client) {
        SeedValidationSnapshot snapshot = SeedMiningService.instance().validationSnapshot();
        int totalTarget = Math.min(MINE_BATCH_LIMIT * 2,
                (int) Math.ceil(baselineConfirmedPositions * Math.min(100, minePercent * 2) / 100d));
        int remaining = Math.max(1, totalTarget - mineSent);
        mineQueue.clear();
        mineQueue.addAll(pickConfirmed(remaining));
        LOGGER.info("{}：继续移除到累计 {}%（本次再移除 {} 个，累计 {} 个；当前验证「{}」，有效确认 {} 个单元 / "
                        + "已确认候选 {}）",
                TAG, Math.min(100, minePercent * 2), mineQueue.size(), mineSent, snapshot.stateCn(),
                snapshot.confirmedUnits(), snapshot.confirmedPositions());
        step = Step.MINE_SECOND;
    }

    /** 挑一批「当前已确认」的候选（按位置键排序后等距抽样，完全确定、可复现）。 */
    private static List<BlockPos> pickConfirmed(int count) {
        List<BlockPos> confirmed = new ArrayList<>();
        for (SeedValidationEvidence evidence : SeedMiningService.instance().validationEvidence()) {
            if (evidence.observationState() == OreObservationState.CONFIRMED) {
                confirmed.add(evidence.position());
            }
        }
        confirmed.sort(Comparator.comparingLong(BlockPos::asLong));
        List<BlockPos> picked = new ArrayList<>(count);
        if (confirmed.isEmpty() || count <= 0) {
            return picked;
        }
        int stride = Math.max(1, confirmed.size() / count);
        for (int index = 0; index < confirmed.size() && picked.size() < count; index += stride) {
            picked.add(confirmed.get(index));
        }
        return picked;
    }

    /** 分批发 {@code /setblock air}。 */
    private static void mineBatch(Minecraft client, Step next) {
        for (int index = 0; index < MINE_COMMANDS_PER_TICK && !mineQueue.isEmpty(); index++) {
            BlockPos pos = mineQueue.remove(0);
            if (!sendCommand(client, "setblock " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " air")) {
                fail(client, "发送 setblock 失败（需要 OP）");
                return;
            }
            mineSent++;
        }
        if (mineQueue.isEmpty()) {
            step = next;
            waitTicks = 0;
        }
    }

    /** 等方块更新落地，然后核对验证仍在「已验证」（普通缺失不得把验证打回去）。 */
    private static void waitMineFirst(Minecraft client) {
        if (++waitTicks < WAIT_SETTLE_TICKS) {
            return;
        }
        checkMinedTolerance(client, minePercent, "第一批");
        step = Step.MINE_SECOND_PREPARE;
    }

    /** 第二批的核对（累计 40%）。 */
    private static void waitMineSecond(Minecraft client) {
        if (++waitTicks < WAIT_SETTLE_TICKS) {
            return;
        }
        checkMinedTolerance(client, minePercent * 2, "第二批");
        step = Step.SYNTHETIC_POLICY;
    }

    /** 容错核对：验证必须仍然是「已验证」，且当前缺失确实增加了。 */
    private static void checkMinedTolerance(Minecraft client, int percent, String label) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        boolean stillVerified = snapshot.state() == SeedValidationState.VERIFIED;
        boolean missingGrew = snapshot.missingPositions() > baselineMissing;
        if (!stillVerified) {
            violations++;
            LOGGER.error("{}：**容错不通过** —— 移除约 {}% 已确认候选后验证掉到了「{}」（{}）；"
                            + "口径要求普通缺失不得取消已验证",
                    TAG, percent, snapshot.stateCn(), snapshot.reasonCn());
            return;
        }
        if (!missingGrew) {
            LOGGER.warn("{}：{}移除后「当前缺失」没有增加（{} → {}）——方块更新可能还没到达客户端",
                    TAG, label, baselineMissing, snapshot.missingPositions());
        }
        minedToleranceSeen++;
        LOGGER.info("{}：被挖矿容错证据 #{}（{}）→ 已移除约 {}% 已确认候选（{} 条指令）：验证仍为「{}」，"
                        + "有效确认单元 {} 个（基线 {} 个），当前已确认候选 {} / 缺失 {}（基线缺失 {}）",
                TAG, minedToleranceSeen, label, percent, mineSent, snapshot.stateCn(),
                snapshot.confirmedUnits(), baselineConfirmedUnits, snapshot.confirmedPositions(),
                snapshot.missingPositions(), baselineMissing);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第三段：策略级合成用例（单区块 / 单单元 / 保守独立性；口径第四十三、四十四、五十~五十二节）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 用真实策略代码喂入构造好的证据，验证硬要求（234.1 第十六节的 CASE A~F + 两条 Gate）。
     *
     * <p><b>如实标注</b>：本段是<b>策略级合成用例</b>（不是实机）——它检验的是
     * {@link SeedValidationPolicy} 与 {@link SeedValidationEvidenceGroup} 的判定逻辑，
     * 与实机样本互补：实机样本证明「正确种子能达标、错误种子不能达标」，
     * 本段证明「单区块 / 单次放置 / UNKNOWN provenance 在分组与阈值上被硬性挡住」。</p>
     */
    private static void syntheticPolicy(Minecraft client) {
        long now = System.currentTimeMillis();

        // Gate 1（口径第五十节）：单目标区块 45/45 全部确认 —— 永远不得 VERIFIED
        List<SeedValidationEvidence> singleChunk = new ArrayList<>();
        for (int index = 0; index < 45; index++) {
            BlockPos pos = new BlockPos(index % 5, -59 - (index / 5), (index * 7) % 16);
            singleChunk.add(confirmedAt(pos, new ChunkPos(0, 0), new ChunkPos(0, 0), now));
        }
        checkSynthetic("单区块 45/45 全部确认（单 Chunk Gate）", singleChunk, false, 1);

        // Gate 2（口径第五十一节）：一次放置产出的 3 个相邻方块 = 恰好 1 个单元 —— 不得 VERIFIED
        List<SeedValidationEvidence> singlePlacement = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            singlePlacement.add(confirmedAt(new BlockPos(index, -59, 0), new ChunkPos(0, 0),
                    new ChunkPos(0, 0), now));
        }
        checkSynthetic("单次放置 3 个方块（单 Placement Gate）", singlePlacement, false, 1);

        // CASE A：同一个 provenance（同一写入者），8 个相邻钻石 → 必须只算 1 个单元
        List<SeedValidationEvidence> caseA = new ArrayList<>();
        for (int index = 0; index < 8; index++) {
            caseA.add(confirmedAt(new BlockPos(4 + index, -59, 8), new ChunkPos(0, 0),
                    new ChunkPos(0, 0), now));
        }
        checkSynthetic("CASE A 同一 provenance 8 个相邻方块", caseA, false, 1);

        // CASE B：同一个 provenance，8 个方块被故意切成 3 个空间断裂簇 → 仍然只能是 1 个单元
        List<SeedValidationEvidence> caseB = new ArrayList<>();
        // 簇 1（3 块相邻）与簇 2（3 块相邻）之间空 3 格，簇 3（2 块相邻）再空 3 格 → 26 邻域下确为三簇
        int[][] caseBCoords = {
                {2, -59, 2}, {3, -59, 2}, {4, -59, 2},
                {2, -59, 8}, {3, -59, 8}, {4, -59, 8},
                {2, -59, 14}, {3, -59, 14}
        };
        for (int[] coord : caseBCoords) {
            caseB.add(confirmedAt(new BlockPos(coord[0], coord[1], coord[2]), new ChunkPos(0, 0),
                    new ChunkPos(0, 0), now));
        }
        checkSynthetic("CASE B 同一 provenance 空间断裂成 3 簇", caseB, false, 1);
        checkSpatialFragmentation("CASE B", caseB, 3);

        // CASE C：同一个目标区块，但有两个可区分的写入者（两次不同的区块装饰）→ 可以是多个单元
        List<SeedValidationEvidence> caseC = new ArrayList<>();
        for (int index = 0; index < 4; index++) {
            caseC.add(confirmedAt(new BlockPos(index, -59, 0), new ChunkPos(0, 0), new ChunkPos(0, 0), now));
            caseC.add(confirmedAt(new BlockPos(index, -59, 4), new ChunkPos(0, 0), new ChunkPos(1, 0), now));
        }
        checkSynthetic("CASE C 同区块两个可区分 provenance", caseC, false, 2);

        // CASE D：不同目标区块，各一个合法独立 provenance → 至少两个单元
        List<SeedValidationEvidence> caseD = new ArrayList<>();
        caseD.add(confirmedAt(new BlockPos(0, -59, 0), new ChunkPos(0, 0), new ChunkPos(0, 0), now));
        caseD.add(confirmedAt(new BlockPos(16, -59, 0), new ChunkPos(1, 0), new ChunkPos(1, 0), now));
        checkSynthetic("CASE D 两个目标区块各一个 provenance", caseD, false, 2);

        // CASE E：UNKNOWN provenance（写入者未知）同一目标区块、多个空间断裂坐标 → 绝不能凭空间断裂裂票
        List<SeedValidationEvidence> caseE = new ArrayList<>();
        for (int index = 0; index < 6; index++) {
            caseE.add(confirmedAt(new BlockPos(index * 3, -59, index * 5), new ChunkPos(0, 0), null, now));
        }
        checkSynthetic("CASE E UNKNOWN provenance 空间断裂", caseE, false, 1);
        checkSpatialFragmentation("CASE E", caseE, 6);

        // CASE F：同一坐标被两条不同 provenance 的候选重复描述 → 不得重复计票
        List<SeedValidationEvidence> caseF = new ArrayList<>();
        caseF.add(confirmedAt(new BlockPos(6, -59, 6), new ChunkPos(0, 0), new ChunkPos(0, 0), now));
        caseF.add(confirmedAt(new BlockPos(6, -59, 6), new ChunkPos(0, 0), new ChunkPos(1, 0), now));
        checkSynthetic("CASE F 同一位置被重复描述", caseF, false, 1);
        checkUnitMemberCount("CASE F", caseF, 1);

        // 正向对照：9 个单元 / 3 个目标区块 → 必须通过（证明策略不是「永远不通过」）
        List<SeedValidationEvidence> enough = new ArrayList<>();
        for (int chunk = 0; chunk < 3; chunk++) {
            for (int viewer = -1; viewer <= 1; viewer++) {
                BlockPos pos = new BlockPos(chunk * 16 + 8 + viewer * 4, -59, 8);
                enough.add(confirmedAt(pos, new ChunkPos(chunk, 0), new ChunkPos(chunk + viewer, 0), now));
            }
        }
        checkSynthetic("9 个单元 / 3 个目标区块（正向对照）", enough, true, 9);

        step = Step.SEED_CHANGE;
        waitTicks = 0;
    }

    /** 造一条「已确认」的证据（可指定写入者；{@code null} = UNKNOWN provenance）。 */
    private static SeedValidationEvidence confirmedAt(BlockPos pos, ChunkPos targetChunk, ChunkPos viewer,
                                                      long now) {
        return SeedValidationEvidence.create(pos, targetChunk, PredictionCertainty.UNRESOLVED,
                        OreSource.UNATTRIBUTED, viewer, OreType.DIAMOND)
                .withObservation(OreObservationState.CONFIRMED, "minecraft:diamond_ore", now);
    }

    /**
     * 跑一条合成用例并与期望比对。
     *
     * @param expectUnits 期望的有效证据单元数；&lt; 0 表示不校验
     */
    private static void checkSynthetic(String what, List<SeedValidationEvidence> evidence,
                                       boolean expectVerified, int expectUnits) {
        List<SeedValidationEvidenceGroup> units = SeedValidationEvidenceGroup.build(evidence);
        SeedValidationPolicy.Inputs inputs = SeedValidationPolicy.inputsOf(units);
        SeedValidationState state = SeedValidationPolicy.evaluate(inputs).state();
        boolean verified = state == SeedValidationState.VERIFIED;
        boolean pass = verified == expectVerified && (expectUnits < 0 || units.size() == expectUnits);
        if (!pass) {
            violations++;
        }
        syntheticSeen++;
        LOGGER.info("{}：策略用例 —— {}：证据 {} 条 → 有效证据单元 {} 个（期望 {}）/ 曾经确认 {} 个 / "
                        + "覆盖 {} 个区块 / 解释比例 {}，判定「{}」，期望「{}」{}",
                TAG, what, evidence.size(), units.size(), expectUnits < 0 ? "不校验" : expectUnits,
                inputs.everConfirmedUnits(), inputs.everConfirmedChunks(),
                inputs.observedUnits() <= 0 ? "—"
                        : String.format(java.util.Locale.ROOT, "%.2f%%", inputs.ratio() * 100d),
                state.displayNameCn(), expectVerified ? "已验证" : "不是已验证",
                pass ? "（通过）" : "（**不通过**）");
    }

    /** 校验「这批证据在旧口径下确实空间断裂成 N 簇」—— 否则该用例是空测。 */
    private static void checkSpatialFragmentation(String what, List<SeedValidationEvidence> evidence,
                                                  int expectComponents) {
        List<SeedValidationEvidenceGroup> units = SeedValidationEvidenceGroup.build(evidence);
        int components = units.isEmpty() ? 0 : units.get(0).spatialComponents();
        boolean ok = components == expectComponents;
        if (!ok) {
            violations++;
        }
        LOGGER.info("{}：策略用例 —— {}：该单元内部空间簇数 {}（期望 {}，用于证明「空间断裂不裂票」不是空测）{}",
                TAG, what, components, expectComponents, ok ? "（通过）" : "（**不通过**）");
    }

    /** 校验单元内成员数（用来证明「同一位置被重复描述」不会重复计入）。 */
    private static void checkUnitMemberCount(String what, List<SeedValidationEvidence> evidence,
                                             int expectMembers) {
        List<SeedValidationEvidenceGroup> units = SeedValidationEvidenceGroup.build(evidence);
        int members = units.isEmpty() ? 0 : units.get(0).members().size();
        boolean ok = members == expectMembers;
        if (!ok) {
            violations++;
        }
        LOGGER.info("{}：策略用例 —— {}：单元成员数 {}（期望 {}）{}", TAG, what, members, expectMembers,
                ok ? "（通过）" : "（**不通过**）");
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第四段：改种子清理 + 错误种子矩阵（口径第四十二、四十七节）
    // ────────────────────────────────────────────────────────────────────────

    /** 改种子：验证必须立刻清空（此刻验证应该是「已验证」）。 */
    private static void seedChange(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot before = service.validationSnapshot();
        int beforeEvidence = service.validationEvidence().size();
        // 走界面同一条写入路径（种子输入框调用的就是它）
        service.setSeedText(String.valueOf(WRONG_SEEDS.get(0)));
        SeedValidationSnapshot after = service.validationSnapshot();
        boolean cleared = after.state() != SeedValidationState.VERIFIED
                && after.confirmedUnits() == 0 && service.validationEvidence().isEmpty();
        if (!cleared) {
            violations++;
            LOGGER.error("{}：**改种子后验证未清空** —— 状态「{}」，有效确认 {} 个单元，证据 {} 条",
                    TAG, after.stateCn(), after.confirmedUnits(), service.validationEvidence().size());
        } else {
            clearedSeen++;
            LOGGER.info("{}：清理证据 #{}（改种子）→ {} → {}：状态「{}」→「{}」，有效确认 {} 个单元 → {} 个，"
                            + "证据 {} 条 → {} 条",
                    TAG, clearedSeen, seed, WRONG_SEEDS.get(0), before.stateCn(), after.stateCn(),
                    before.confirmedUnits(), after.confirmedUnits(), beforeEvidence,
                    service.validationEvidence().size());
        }
        currentSeed = WRONG_SEEDS.get(0);
        step = Step.WAIT_SEED_CHANGE;
        waitTicks = 0;
    }

    /** 等错误种子下的样本积累起来，然后核对它<b>没有</b>通过验证。 */
    private static void waitSeedChange(Minecraft client) {
        if (!waitWrongSeedReady(client, WRONG_SEEDS.get(0), "错误种子（改种子用例）")) {
            return;
        }
        wrongIndex = 1;
        step = Step.WRONG_SEED_PREPARE;
        waitTicks = 0;
    }

    /** 矩阵里的下一颗错误种子。 */
    private static void wrongSeedPrepare(Minecraft client) {
        if (wrongIndex >= WRONG_SEEDS.size()) {
            advanceAfterWrongSeeds(client);
            return;
        }
        long next = WRONG_SEEDS.get(wrongIndex);
        SeedMiningService.instance().setSeedText(String.valueOf(next));
        currentSeed = next;
        LOGGER.info("{}：错误种子用例 —— 客户端种子改为 {}（服务器世界种子仍是 {}），等待样本积累",
                TAG, next, seed);
        step = Step.WAIT_WRONG_SEED;
        waitTicks = 0;
    }

    private static void waitWrongSeed(Minecraft client) {
        if (!waitWrongSeedReady(client, currentSeed, "错误种子")) {
            return;
        }
        wrongIndex++;
        step = Step.WRONG_SEED_PREPARE;
        waitTicks = 0;
    }

    /**
     * 等一颗错误种子的样本积累到可以下结论，并断言它<b>没有</b>进入已验证。
     *
     * <p>「可以下结论」的判据：覆盖已经铺满且全部候选都被观察过（与正确种子同一口径）——
     * 这样错误种子那张表里的样本量与正确种子可比，而不是「刚换了种子、只算出两个区块」就下结论。</p>
     *
     * @return 是否已经核对完成（false = 继续等）
     */
    private static boolean waitWrongSeedReady(Minecraft client, long wrongSeed, String label) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        int unobserved = service.renderSnapshot().stats().unobserved();
        boolean ready = total > 0 && done >= total && unobserved == 0;
        if (!ready && ++waitTicks <= WAIT_COVERAGE_TICKS) {
            return false;
        }
        if (!ready) {
            LOGGER.warn("{}：{}（种子 {}）等待覆盖铺满超时（已预测 {}/{}，未观察 {}），按当前样本下结论",
                    TAG, label, wrongSeed, done, total, unobserved);
        }
        if (snapshot.state() == SeedValidationState.VERIFIED) {
            violations++;
            LOGGER.error("{}：**错误种子误验证** —— 种子 {} 进入了「已验证」（有效确认 {} 个单元 / 覆盖 {} 个区块）",
                    TAG, wrongSeed, snapshot.confirmedUnits(), snapshot.confirmedChunks());
        } else {
            wrongSeedSeen++;
            LOGGER.info("{}：错误种子证据 #{} → 客户端种子 {}：状态「{}」，候选 {} 个 / 已观察 {} 个单元 / "
                            + "曾经确认 {} 个单元 / 覆盖 {} 个目标区块 / 解释比例 {}；有效样本区块 {}；"
                            + "当前确认候选 {} / 缺失 {}（{}）",
                    TAG, wrongSeedSeen, wrongSeed, snapshot.stateCn(),
                    service.renderSnapshot().stats().candidates(), snapshot.observedUnits(),
                    snapshot.confirmedUnits(), snapshot.confirmedChunks(), snapshot.ratioCn(),
                    snapshot.sampleChunks(), snapshot.confirmedPositions(), snapshot.missingPositions(),
                    snapshot.reasonCn());
        }
        return true;
    }

    /** 错误种子矩阵跑完：先把种子改回正确值并等它重新通过验证，再跑「A 侧已验证 → B」的换服清理。 */
    private static void advanceAfterWrongSeeds(Minecraft client) {
        SeedMiningService.instance().setSeedText(Long.toString(seed));
        currentSeed = seed;
        LOGGER.info("{}：错误种子矩阵跑完，客户端种子改回正确值 {}，等待它重新进入「已验证」"
                + "（换服清理用例要求 A 侧此刻确实是已验证）", TAG, seed);
        step = Step.WAIT_RESTORE_VERIFIED;
        waitTicks = 0;
    }

    /** 等「改回正确种子」后重新通过验证。 */
    private static void waitRestoreVerified(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        if (snapshot.state() == SeedValidationState.VERIFIED) {
            LOGGER.info("{}：恢复证据 → 种子改回 {} 后重新「已验证」：有效确认单元 {} 个 / 覆盖 {} 个目标区块 / "
                            + "已观察 {} 个单元 / 解释比例 {}；当前已确认候选 {} / 缺失 {}（此前已被移除约 {}% 的矿）",
                    TAG, seed, snapshot.confirmedUnits(), snapshot.confirmedChunks(),
                    snapshot.observedUnits(), snapshot.ratioCn(), snapshot.confirmedPositions(),
                    snapshot.missingPositions(), Math.min(100, minePercent * 2));
            if (serverBKey.isEmpty()) {
                LOGGER.info("{}：未配置 B 服务器（-Dyiyiaddon.seedpoc.validation.serverB），跳过换服清理用例", TAG);
                step = Step.DIMENSION_TO_NETHER;
                waitTicks = 0;
                return;
            }
            step = Step.SWITCH_TO_B;
            waitTicks = 0;
            return;
        }
        if (++waitTicks > WAIT_VERIFY_TICKS) {
            // 234.1：这段等待的唯一用途是「给换服清理用例准备一个『A 侧此刻确实是已验证』的基线」
            // （见 advanceAfterWrongSeeds 的注释）。换服用例关着时它不再是判据：
            // 设备刚跑完「逐批移除已确认候选」容错用例，玩家视野内的钻石已被自己挖掉一大部分，
            // 重新收集时看到的自然多为「缺失」，能否再次进入已验证取决于这一等里有没有加载到新地形
            // （口径第十六节：MISSING 不是负票，所以这不是产品缺陷 —— 同一 jar 在未被大量移除的
            // 世界上实测能重新进入已验证）。因此这里只降级为警告，不放行任何真正的判据。
            if (serverBKey.isEmpty()) {
                LOGGER.warn("{}：换服清理用例未启用（-Dyiyiaddon.seedpoc.validation.serverB 为空），"
                                + "本条前置等待不作为判据 —— 改回种子 {} 后的状态「{}」（{} 个单元 / {} 个区块 / {}）",
                        TAG, seed, snapshot.stateCn(), snapshot.confirmedUnits(), snapshot.confirmedChunks(),
                        snapshot.reasonCn());
            } else {
                violations++;
                LOGGER.error("{}：**改回正确种子后没能重新通过验证** —— 状态「{}」（{} 个单元 / {} 个区块 / {}）",
                        TAG, snapshot.stateCn(), snapshot.confirmedUnits(), snapshot.confirmedChunks(),
                        snapshot.reasonCn());
            }
            step = serverBKey.isEmpty() ? Step.DIMENSION_TO_NETHER : Step.SWITCH_TO_B;
            waitTicks = 0;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 第五段：换服务器 / 换维度清理（口径第四十八、四十九节）
    // ────────────────────────────────────────────────────────────────────────

    private static void switchToServerB(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        aEvidenceCount = service.validationEvidence().size();
        aStateCn = service.validationSnapshot().stateCn();
        LOGGER.info("{}：A 侧验证基线 —— 状态「{}」，证据 {} 条（种子 {}）", TAG, aStateCn, aEvidenceCount,
                currentSeed);
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            fail(client, "换服前拿不到与 A 的连接");
            return;
        }
        player.connection.getConnection().disconnect(Component.literal("234 换服用例：主动断开 A"));
        step = Step.WAIT_SERVER_B;
        waitTicks = 0;
    }

    /** 等世界清空 → 连 B；到达 B 的第一刻立即断言 A 的验证没有带过来。 */
    private static void waitServerB(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待进入 B 超时");
            return;
        }
        if (client.level != null) {
            if (!serverBKey.equals(WorldIdentity.server())) {
                return;
            }
            SeedMiningService service = SeedMiningService.instance();
            int evidence = service.validationEvidence().size();
            String stateCn = service.validationSnapshot().stateCn();
            boolean clean = evidence == 0 && stateCn.equals(SeedValidationState.UNVERIFIED.displayNameCn());
            if (!clean) {
                violations++;
                LOGGER.error("{}：**换服后验证残留** —— B 里状态「{}」，证据 {} 条（A 侧基线「{}」/ {} 条）",
                        TAG, stateCn, evidence, aStateCn, aEvidenceCount);
            } else {
                clearedSeen++;
                LOGGER.info("{}：清理证据 #{}（换服务器）→ 已进入 B（{}）：验证状态「{}」，证据 0 条"
                                + "（A 侧基线「{}」/ {} 条），AutoMiner 门 = {}",
                        TAG, clearedSeen, WorldIdentity.server(), stateCn, aStateCn, aEvidenceCount,
                        service.mayUseForAutomatedMining());
            }
            // B 是另一台服务器：配置按服务器隔离，这里重新写一遍，后面的维度用例才有东西可清
            SeedMiningService.instance().setEnabled(true);
            SeedMiningService.instance().setSeedText(Long.toString(seed));
            SeedMiningService.instance().setCoverageRadius(radius);
            SeedMiningService.instance().setRenderPrediction(true);
            step = Step.WAIT_B_EVIDENCE;
            waitTicks = 0;
        } else if (!connectIssued) {
            connectIssued = true;
            ServerData data = new ServerData("234 B", serverBKey, ServerData.Type.OTHER);
            LOGGER.info("{}：已断开 A，发起连接 B（{}）", TAG, serverBKey);
            ConnectScreen.startConnecting(new TitleScreen(), client, ServerAddress.parseString(serverBKey),
                    data, false, null);
        }
    }

    /**
     * 等 B 侧真的重新收集到验证证据，再跑维度清理用例。
     *
     * <p>为什么要等：换维度清理要证明的是「<b>有证据</b>的情况下切维度会被清掉」。
     * 若 B 里还没开始收集就切维度，那条断言会因为「本来就没东西」而自动通过 —— 那是空测。</p>
     */
    private static void waitServerBEvidence(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        if (snapshot.observedUnits() > 0) {
            LOGGER.info("{}：B 侧已重新收集到验证证据：状态「{}」，已观察 {} 个单元 / 曾经确认 {} 个单元 / "
                            + "覆盖 {} 个目标区块（接下来跑换维度清理）",
                    TAG, snapshot.stateCn(), snapshot.observedUnits(), snapshot.confirmedUnits(),
                    snapshot.confirmedChunks());
            step = Step.DIMENSION_TO_NETHER;
            waitTicks = 0;
            return;
        }
        if (++waitTicks > WAIT_OBSERVE_TICKS) {
            violations++;
            LOGGER.error("{}：**B 侧迟迟没有收集到验证证据** —— 状态「{}」，缓存 {} 个区块",
                    TAG, snapshot.stateCn(), service.cachedChunkCount());
            step = Step.DIMENSION_TO_NETHER;
            waitTicks = 0;
        }
    }

    /** 传送到下界。 */
    private static void dimensionToNether(Minecraft client) {
        if (!sendCommand(client, "execute in minecraft:the_nether run tp @s 0 70 0")) {
            fail(client, "发送下界传送失败（需要 OP）");
            return;
        }
        step = Step.WAIT_NETHER;
        waitTicks = 0;
    }

    /** 下界里：验证必须清空（本阶段不下界验证，口径第四十九、六十一节）。 */
    private static void waitNether(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待进入下界超时");
            return;
        }
        if (client.level == null
                || !"minecraft:the_nether".equals(client.level.dimension().identifier().toString())) {
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        int evidence = service.validationEvidence().size();
        String stateCn = service.validationSnapshot().stateCn();
        boolean clean = evidence == 0 && stateCn.equals(SeedValidationState.UNVERIFIED.displayNameCn());
        if (!clean) {
            violations++;
            LOGGER.error("{}：**下界里仍有主世界验证数据** —— 状态「{}」，证据 {} 条", TAG, stateCn, evidence);
        } else {
            clearedSeen++;
            LOGGER.info("{}：清理证据 #{}（换维度）→ 下界中：验证状态「{}」，证据 0 条，身份 {}",
                    TAG, clearedSeen, stateCn, service.runtimeIdentityCn());
        }
        step = Step.DIMENSION_BACK;
        waitTicks = 0;
    }

    /** 传回主世界。 */
    private static void dimensionBack(Minecraft client) {
        if (!sendCommand(client, "execute in minecraft:overworld run tp @s 0 100 0")) {
            fail(client, "发送主世界传送失败");
            return;
        }
        step = Step.WAIT_OVERWORLD;
        waitTicks = 0;
    }

    /** 回主世界后：验证必须重新开始收集（证明它是「按身份重建」，不是「死了」）。 */
    private static void waitOverworld(Minecraft client) {
        if (++waitTicks > WAIT_WORLD_TICKS) {
            fail(client, "等待返回主世界并重新开始验证超时");
            return;
        }
        if (client.level == null
                || !"minecraft:overworld".equals(client.level.dimension().identifier().toString())) {
            return;
        }
        SeedMiningService service = SeedMiningService.instance();
        SeedValidationSnapshot snapshot = service.validationSnapshot();
        if (snapshot.observedUnits() == 0 || snapshot.state() == SeedValidationState.UNVERIFIED) {
            return;
        }
        clearedSeen++;
        LOGGER.info("{}：清理证据 #{}（回主世界重建）→ 验证已重新开始收集：状态「{}」，已观察 {} 个单元 / "
                        + "曾经确认 {} 个单元（种子 {}）",
                TAG, clearedSeen, snapshot.stateCn(), snapshot.observedUnits(),
                snapshot.confirmedUnits(), seed);
        finish(client);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 收尾
    // ────────────────────────────────────────────────────────────────────────

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LOGGER.info("{}：====== 结果汇总 ======", TAG);
        LOGGER.info("{}：已验证证据 {} 条 / 被挖矿容错证据 {} 条 / 策略合成用例 {} 条 / "
                        + "错误种子证据 {} 条 / 清理证据 {} 条",
                TAG, verifiedSeen, minedToleranceSeen, syntheticSeen, wrongSeedSeen, clearedSeen);
        LOGGER.info("{}：错误种子矩阵 {} 全部未通过验证：{}", TAG, WRONG_SEEDS, wrongSeedSeen == WRONG_SEEDS.size()
                ? "是" : "**否（见上）**");
        LOGGER.info("{}：可疑语义 {}", TAG,
                com.yiyiaddon.seed.validation.SeedSuspicionPolicy.disabledReasonCn());
        LOGGER.info("{}：硬冲突判据 {}", TAG, service.validationConflictAvailabilityCn());
        LOGGER.info("{}：最终读数 {}", TAG, service.runtimeDiagnosticsCn());
        LOGGER.info("{}：全部判定：{}", TAG, violations == 0 ? "通过" : "**存在 " + violations + " 项不通过，见上**");
        service.setRenderPrediction(false);
        LOGGER.info("{}：已关闭显示预测钻石；缓存 {} 个区块，验证证据 {} 条",
                TAG, service.cachedChunkCount(), service.validationEvidence().size());
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        violations++;
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
        player.connection.sendCommand(command);
        return true;
    }
}
