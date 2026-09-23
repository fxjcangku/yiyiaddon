package com.yiyiaddon.feature.mining.target;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.observation.SeedOreObservationTracker;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.runtime.SeedRuntimeIdentity;
import com.yiyiaddon.seed.service.SeedMiningService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 自动挖矿 · <b>种子模式目标提供者</b>（正式化第七阶段 235）。
 *
 * <p><b>它负责什么</b>：回答「下一颗钻石在哪」，并且只回答这一件事。移动、寻路、秒破、连锁挖矿、
 * 物流、进食、战斗、回家全部还是既有那套（本类只调用它们，不重写任何一条）。</p>
 *
 * <h2>目标来源（唯一合法来源）</h2>
 * <p>输入只有三样：{@link SeedMiningService} 的预测缓存（Worker 返回的正式
 * {@link PredictionResult}）、实际区块观察（{@link SeedOreObservationTracker}）、
 * 以及种子验证闸门 {@link SeedMiningService#mayUseForAutomatedMining()}。<b>不</b>读真实世界找矿、
 * <b>不</b>让男中音按矿物类型全局搜、<b>不</b>自己算任何 worldgen。</p>
 *
 * <h2>选择口径（第一版刻意保持简单稳定）</h2>
 * <ol>
 *     <li>只考虑：当前运行时身份 + 成功结果 + {@link OreType#DIAMOND} + 落在当前覆盖方框内；</li>
 *     <li>优先级：<b>已确认（CONFIRMED）</b> → <b>未观察（UNOBSERVED）</b>；同级按玩家欧氏距离取最近；</li>
 *     <li>排除：已消费目标、{@link OreObservationState#MISSING}、暂时不可达（带冷却）；
 *         <b>不</b>因为「调度敏感」而排除——调度敏感不是假矿（真实世界里它同样可能出现）；</li>
 *     <li>不做旅行商之类的路径优化：本阶段只要「稳定 + 不绕圈」。</li>
 * </ol>
 *
 * <h2>到位之后</h2>
 * <p>目标区块没加载时照常导航（Baritone 自定义目标，精确到方块，不是类型扫描）；一旦真正加载，
 * <b>读实际 {@code BlockState} 重新确认</b>：</p>
 * <ul>
 *     <li>是 {@code diamond_ore} / {@code deepslate_diamond_ore} → 交给现有
 *         {@link MiningFastBreakController}（秒破）开挖，整条矿脉由现有 {@code MiningVeinMiner} 承接；</li>
 *     <li>不是钻石（含被 {@code /setblock air} 手动挖掉）→ 记进「已消费」并<b>立刻</b>换下一颗，
 *         绝不在这里死磕一个不存在的坐标。</li>
 * </ul>
 *
 * <h2>与其它流程的让位</h2>
 * <p>连锁挖矿 / 秒破 / 水中脱困 / 岩浆垫脚各自占用男中音或服务端唯一的破坏槽位，这些时刻本类
 * <b>一行都不写</b>（直接返回），保证同一时刻只有一个写入者。</p>
 *
 * <h2>生命周期</h2>
 * <p>目标、已消费清单、暂时不可达清单全部绑定在 {@link SeedRuntimeIdentity} 上：改种子 / 换服 /
 * 换维度 / 退世界 / 关功能都会换号，旧目标当场作废，绝不会被带到新世界。</p>
 */
public final class SeedMiningTargetProvider implements MiningTargetProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词（与种子挖矿其余部分同一个前缀，便于一次检索）。 */
    private static final String LOG_KEY = "种子挖矿｜自动挖矿";

    /** 交互距离判定的松弛量：与秒破 / 连锁同一口径（{@code isWithinBlockInteractionRange} 的第二个参数）。 */
    private static final double REACH_SLACK = 1.0;

    /** 同一目标最多重新发起几次寻路；用尽即判定「暂时够不到」并换下一颗（防止对同一格无限重试）。 */
    private static final int MAX_PATH_ISSUES = 3;

    /** 两次重新发起寻路之间的最小间隔（刻）：0.5 秒，避免与别的流程来回抢男中音。 */
    private static final int PATH_REISSUE_GAP_TICKS = 10;

    /** 暂时不可达的冷却时长（刻）：10 分钟（12000 刻）后再考虑这一颗。 */
    private static final int UNREACHABLE_COOLDOWN_TICKS = 12000;

    /** 已消费集合上限（防止长时间挂机把内存撑大；满了就整体清空重新累计）。 */
    private static final int CONSUMED_LIMIT = 4096;

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    // ── 当前目标与寻路状态（仅客户端主线程） ──

    /** 当前锁定的种子目标；{@code null} = 本刻没有目标。 */
    private BlockPos currentTarget;

    /** 当前目标是否已经发起过寻路。 */
    private boolean pathIssued;

    /** 当前目标上一次发起寻路时的刻（重新发起的限速与日志用）。 */
    private int pathIssuedTick;

    /** 当前目标累计发起过几次寻路（含首次）。 */
    private int pathIssues;

    // ── 记账（绑定在运行时身份上，身份一变整批作废） ──

    /** 已消费：被挖掉 / 实际不是钻石 / 已排除的候选，本次会话内不再考虑。 */
    private final Set<Long> consumed = new HashSet<>();

    /** 暂时不可达：坐标 → 解禁刻（男中音寻路失败 / 秒破判定挖不动）。 */
    private final Map<Long, Integer> unreachableUntil = new HashMap<>();

    /** 选出这些目标时所处的运行时身份；与当前身份不一致即整批作废。 */
    private SeedRuntimeIdentity identity;

    public SeedMiningTargetProvider(AutoMinerModule module) {
        this.module = module;
    }

    private static SeedMiningService seed() {
        return SeedMiningService.instance();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 模式描述
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public String modeNameCn() {
        return "种子模式";
    }

    @Override
    public String scanModeCn() {
        return "种子预测坐标（精确到方块）";
    }

    // ────────────────────────────────────────────────────────────────────────
    // 能否挖（fail-closed 的唯一落点）
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public boolean ready() {
        return notReadyReasonCn().isEmpty();
    }

    /**
     * 不能挖的中文原因（{@link #ready()} 为 true 时返回空串）。
     *
     * <p>判据顺序刻意「从用户最容易修的一项开始」：总开关 → 维度 → 种子 → 验证闸门 → 目标 → 秒破。
     * 全部满足才返回空串；任何一条不满足都<b>不会</b>退回普通模式的矿物类型扫描。</p>
     */
    @Override
    public String notReadyReasonCn() {
        SeedMiningService service = seed();
        if (!service.enabled()) {
            return "种子挖矿未开启 §8▸ 请在「种子挖矿」页打开总开关";
        }
        if (!service.dimensionSupported()) {
            return "种子目标只支持主世界 §8▸ 当前：" + service.dimensionDisplayCn();
        }
        if (service.seedValue() == null) {
            return "服务器种子未填写或格式无效 §8▸ 请在「种子挖矿」页填写";
        }
        if (!service.mayUseForAutomatedMining()) {
            return "种子验证未通过（当前：" + service.validationSnapshot().stateCn()
                + "）§8▸ 不启动种子自动挖矿";
        }
        if (!targetsDiamondOre()) {
            return "目标需选择钻石矿 §8▸ 种子预测当前只覆盖钻石（时运 / 精准均可）";
        }
        if (!module.getFastBreak()) {
            return "种子目标依赖秒破 §8▸ 请先在设置里打开「秒破」";
        }
        return "";
    }

    /** 当前目标选择是不是钻石矿（时运与精准两种模式解析出来的锚点都是钻石矿方块）。 */
    private boolean targetsDiamondOre() {
        Block anchor = module.getTargetBlock();
        return anchor == Blocks.DIAMOND_ORE || anchor == Blocks.DEEPSLATE_DIAMOND_ORE;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 下发与推进
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 下发目标：与 {@link #tick()} 同一条路径（选目标 → 寻路 → 到位交给秒破）。
     *
     * <p>{@code broadcast} 只影响「已锁定目标」那条播报，其余日志照记。</p>
     */
    @Override
    public boolean issue(boolean broadcast) {
        tick();
        if (currentTarget != null && broadcast) {
            module.info("§b种子挖矿 §8▸ 开始按预测坐标挖掘（精确到方块，不按矿物类型扫描）");
        }
        return currentTarget != null;
    }

    /**
     * 自愈式重下发：先判当前目标「这次够不到」并换下一颗，再走一遍正常下发。
     *
     * <p>种子模式下「重挖同一个坐标」没有意义（挖不动 / 到不了都不是重下发能解决的），
     * 因此这里换目标 —— 这正是既有「重置采掘目标脱困」在种子模式下的等价动作。</p>
     */
    @Override
    public boolean reissue() {
        BlockPos target = currentTarget;
        if (target != null) {
            markUnreachable(target);
            abandon(target, "挖不动或到不了，跳过这一颗");
        }
        tick();
        return currentTarget != null;
    }

    @Override
    public void tick() {
        // 0) 运行时身份生命周期：改种子 / 换服 / 换维度 / 退世界 / 关功能 → 立刻丢掉旧目标
        if (!syncIdentity()) {
            return;
        }
        // 1) 让位：连锁 / 秒破 / 水中脱困 / 岩浆垫脚 各自占用男中音或服务端唯一破坏槽位
        if (module.getVeinMiner().isActive()
            || MiningFastBreakController.instance().isActive()
            || module.fsm().isWaterBreaking()
            || module.fsm().isLavaBridging()) {
            return;
        }
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) {
            return;
        }

        // 2) 当前目标还成立吗（被挖掉 / 换成别的方块 / 观察为 MISSING）
        if (currentTarget != null && !isStillUsable(currentTarget)) {
            BlockPos gone = currentTarget;
            consume(gone);
            abandon(gone, "目标位置实际不是钻石矿（已被挖走或已改变）");
        }

        // 3) 没有目标 → 选下一颗（选不到就等；是否「换区域」由状态机问 exhausted()）
        if (currentTarget == null) {
            BlockPos next = selectNext(player);
            if (next == null) {
                return;
            }
            currentTarget = next;
            pathIssued = false;
            pathIssues = 0;
            LOGGER.info("{}：选定目标 ({},{},{})，已消费 {} 颗 / 暂时不可达 {} 颗", LOG_KEY,
                next.getX(), next.getY(), next.getZ(), consumed.size(), unreachableUntil.size());
            module.info("§b种子挖矿 §8▸ 已锁定预测钻石目标 §f"
                + next.getX() + " " + next.getY() + " " + next.getZ());
        }

        BlockPos target = currentTarget;

        // 4) 已经在目标旁、且实际方块确实是钻石 → 交给现有秒破（矿脉由现有连锁承接）
        if (player.isWithinBlockInteractionRange(target, REACH_SLACK) && confirmedDiamond(level, target)) {
            if (pathIssued) {
                // 到位即收起寻路：男中音的自定义目标与秒破同时活着会抢服务端唯一的破坏槽位
                module.getBaritone().stop();
                pathIssued = false;
            }
            if (MiningFastBreakController.instance().isBlocked(player.tickCount, target)) {
                markUnreachable(target);
                abandon(target, "秒破判定这一格挖不动");
                return;
            }
            MiningFastBreakController.instance().mineRequested(mc, module, target, Direction.UP);
            return;
        }

        // 5) 够不着 → 继续导航（精确坐标的 Baritone 自定义目标，不是矿物类型 mine）
        if (!pathIssued) {
            if (!issuePath(player, target)) {
                markUnreachable(target);
                abandon(target, "男中音拒绝为该目标寻路");
            }
            return;
        }
        if (module.getBaritone().isCustomGoalActive()) {
            return; // 正在寻路
        }
        // 目标进程没了：可能到达过又走开、被别的流程取消、或男中音算不出路 —— 限速重发几次
        if (player.tickCount - pathIssuedTick < PATH_REISSUE_GAP_TICKS) {
            return;
        }
        if (pathIssues < MAX_PATH_ISSUES) {
            issuePath(player, target);
            return;
        }
        markUnreachable(target);
        abandon(target, "寻路反复失败（这一颗暂时够不到）");
    }

    /** 发起一次寻路并记账；返回是否成功下发。 */
    private boolean issuePath(LocalPlayer player, BlockPos target) {
        if (!module.getBaritone().pathToOre(target)) {
            return false;
        }
        pathIssued = true;
        pathIssuedTick = player.tickCount;
        pathIssues++;
        LOGGER.info("{}：寻路前往 ({},{},{})（第 {} 次）", LOG_KEY,
            target.getX(), target.getY(), target.getZ(), pathIssues);
        return true;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 引擎状态（状态机看门狗用）
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public boolean engineActive() {
        if (!ready()) {
            return false; // 条件不满足：交给看门狗分支停机并播报原因（fail-closed）
        }
        if (currentTarget != null) {
            return true;
        }
        if (MiningFastBreakController.instance().isActive() || module.getVeinMiner().isActive()) {
            return true;
        }
        // 覆盖还没收敛：本刻没目标只是「预测还在路上」，不是「引擎退出」，不要触发重启看门狗
        return !exhausted();
    }

    /**
     * 附近是否已经没有可用目标。
     *
     * <p>判据是「本刻没有当前目标 + 条件齐备 + 覆盖调度已经收敛」：只有三者同时成立才说明
     * 「这一片区域按种子预测确实没有可挖的钻石了」，这时状态机会走「重新前往野外换区域」
     * （换区域后玩家所在区块变了，覆盖会自动在新位置重新铺开）。</p>
     */
    @Override
    public boolean exhausted() {
        if (currentTarget != null || !ready()) {
            return false;
        }
        return !seed().coverageStillWorking();
    }

    @Override
    public boolean progressWatchdogEnabled() {
        // 种子模式关掉「长时间没打穿方块」这一档：导航到远端目标途中本来就不打方块，
        // 目标够不到由本类自己的寻路失败判据处理（换下一颗），不需要状态机再插一脚
        return false;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 清理
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public void reset() {
        // 离开挖矿态：只撤掉当前目标与寻路，保留「已消费 / 暂时不可达」记账（回到挖矿态继续用）
        BlockPos target = currentTarget;
        cancelPath();
        currentTarget = null;
        if (target != null) {
            LOGGER.info("{}：离开挖矿态，撤掉当前目标 ({},{},{})", LOG_KEY,
                target.getX(), target.getY(), target.getZ());
        }
    }

    @Override
    public void resetSession() {
        cancelPath();
        currentTarget = null;
        consumed.clear();
        unreachableUntil.clear();
        identity = null;
    }

    /** 撤掉寻路（只在确实发起过时叫一次停，避免每分钟几十次无意义的 stop）。 */
    private void cancelPath() {
        if (!pathIssued) {
            return;
        }
        pathIssued = false;
        pathIssues = 0;
        module.getBaritone().stop();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 目标选择
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 按「已确认 → 未观察 → 最近」选出下一颗钻石；一个都没有返回 {@code null}。
     */
    private BlockPos selectNext(LocalPlayer player) {
        BlockPos playerPos = player.blockPosition();
        ChunkPos playerChunk = ChunkPos.containing(playerPos);
        int radius = seed().coverageRadius();
        BlockPos best = null;
        int bestRank = Integer.MAX_VALUE;
        double bestDistance = Double.MAX_VALUE;
        for (PredictionResult result : seed().cachedPredictions()) {
            if (result == null || result.failed()) {
                continue; // 失败 ≠ 没有矿：失败结果根本不进缓存，这里只做防御
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.oreType() != OreType.DIAMOND) {
                    continue;
                }
                BlockPos pos = ore.position();
                long key = pos.asLong();
                if (consumed.contains(key)) {
                    continue;
                }
                Integer until = unreachableUntil.get(key);
                if (until != null && player.tickCount < until) {
                    continue;
                }
                if (!inCoverage(pos, playerChunk, radius)) {
                    continue; // 只考虑当前覆盖范围内（默认半径 3）
                }
                int rank = rankOf(pos);
                if (rank < 0) {
                    continue;
                }
                double distance = playerPos.distSqr(pos);
                if (rank < bestRank || (rank == bestRank && distance < bestDistance)) {
                    best = pos;
                    bestRank = rank;
                    bestDistance = distance;
                }
            }
        }
        return best;
    }

    /**
     * 目标优先级：{@code 0} = 已确认（就在眼前），{@code 1} = 未观察（远端 Seed 导航目标），
     * {@code -1} = 本刻不作为挖矿目标。
     *
     * <p>{@link OreObservationState#MISSING} 不选：它表示「区块已加载、实际不是钻石」。
     * {@link OreObservationState#UNOBSERVED} 选：远端目标允许先导航过去，等区块真正加载后
     * 再由实际 {@code BlockState} 重新确认（见 {@link #tick()} 第 2、4 步）。</p>
     */
    private int rankOf(BlockPos pos) {
        return switch (seed().observationState(pos)) {
            case CONFIRMED -> 0;
            case UNOBSERVED -> 1;
            case MISSING, SUSPICIOUS -> -1; // 本阶段不产出 SUSPICIOUS；万一出现也不作为挖矿目标
        };
    }

    /** 目标方块是否落在玩家当前覆盖方框内（区块的切比雪夫距离，与覆盖调度同一口径）。 */
    private static boolean inCoverage(BlockPos pos, ChunkPos center, int radius) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        return Math.abs(chunkX - center.x()) <= radius && Math.abs(chunkZ - center.z()) <= radius;
    }

    /**
     * 目标是否仍然值得追：区块没加载时按预测继续导航；已加载则以<b>实际方块状态</b>为准。
     */
    private boolean isStillUsable(BlockPos pos) {
        ClientLevel level = mc.level;
        if (level == null) {
            return false;
        }
        if (seed().observationState(pos) == OreObservationState.MISSING) {
            return false;
        }
        if (!level.isLoaded(pos)) {
            return true;
        }
        return SeedOreObservationTracker.isOreBlock(OreType.DIAMOND, level.getBlockState(pos));
    }

    /** 实际方块状态确认：这一格现在真的是钻石矿（含深层变种）。 */
    private boolean confirmedDiamond(ClientLevel level, BlockPos pos) {
        return level.isLoaded(pos)
            && SeedOreObservationTracker.isOreBlock(OreType.DIAMOND, level.getBlockState(pos));
    }

    // ────────────────────────────────────────────────────────────────────────
    // 记账与生命周期
    // ────────────────────────────────────────────────────────────────────────

    /** 记进「已消费」：本次会话内不再考虑这一颗。 */
    private void consume(BlockPos pos) {
        if (consumed.size() >= CONSUMED_LIMIT) {
            consumed.clear();
        }
        consumed.add(pos.asLong());
    }

    /** 记进「暂时不可达」：冷却一段时间后再考虑（防止对同一个够不到的坐标反复重试）。 */
    private void markUnreachable(BlockPos pos) {
        int now = mc.player == null ? 0 : mc.player.tickCount;
        unreachableUntil.put(pos.asLong(), now + UNREACHABLE_COOLDOWN_TICKS);
    }

    /** 放弃当前目标：撤掉寻路、清空当前目标并如实播报一句（换下一颗）。 */
    private void abandon(BlockPos target, String reasonCn) {
        LOGGER.info("{}：放弃目标 ({},{},{}) —— {}", LOG_KEY,
            target.getX(), target.getY(), target.getZ(), reasonCn);
        module.info("§e种子目标已放弃 §8▸ " + reasonCn + "§8（换下一颗）");
        cancelPath();
        currentTarget = null;
    }

    /**
     * 同步运行时身份；身份不在（关功能 / 退世界 / 换维度 / 换服 / 改种子）返回 {@code false}。
     *
     * <p>身份一变，当前目标与两份记账<b>整批作废</b> —— 旧世界的坐标绝不允许被带到新世界。</p>
     */
    private boolean syncIdentity() {
        SeedRuntimeIdentity now = seed().runtimeIdentity();
        if (!Objects.equals(identity, now)) {
            if (currentTarget != null || !consumed.isEmpty() || !unreachableUntil.isEmpty()) {
                LOGGER.info("{}：运行时身份变更（{}），清空种子目标与记账（已消费 {} 颗）", LOG_KEY,
                    now == null ? "运行时已失效" : now.describeCn(), consumed.size());
            }
            cancelPath();
            currentTarget = null;
            consumed.clear();
            unreachableUntil.clear();
            identity = now;
        }
        return now != null;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 控制台只读状态
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public String statusCn() {
        if (!seed().enabled()) {
            return "种子挖矿未开启";
        }
        BlockPos target = currentTarget;
        String head = target == null
            ? "本刻无锁定目标"
            : "锁定 (" + target.getX() + " " + target.getY() + " " + target.getZ() + ")";
        return head + " · 已消费 " + consumed.size() + " 颗 · 暂时不可达 " + unreachableUntil.size() + " 颗";
    }
}
