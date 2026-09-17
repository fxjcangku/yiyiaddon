package com.yiyiaddon.feature.mining.fastbreak;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自动挖矿的单槽发包破坏状态机（秒破）。
 *
 * <p>26.1.2 服务端在 STOP 到达时要求
 * {@code getDestroyProgress * (经过 tick + 1) >= 0.7}。因此真正可靠的
 * 发包快速破坏不是同 tick 盲发 START/STOP，而是只发一次 START，在服务端
 * 最早可接受的 0.7 卡点只发一次 STOP，再等待权威方块同步。</p>
 *
 * <h2>本轮重做（用户 2026-09-17：「开启秒破后挖不烂方块，调慢速度也没用」）</h2>
 *
 * <p>旧实现（逐条移植旧项目 {@code AutoMinerFastBreakController}）有三个结构性缺陷，
 * 每一个都会表现为「一直挖、方块永远挖不烂」：</p>
 * <ol>
 *   <li><b>推进完全依赖外部调用节奏</b>：只有 Baritone 调到
 *       {@code continueDestroyBlock} 才会检查阈值、才可能发 STOP。Baritone 的破坏助手
 *       （反编译 {@code baritone.utils.BlockBreakHelper}）在「准星不在方块上 / 正在换目标 /
 *       正在算路」的那些刻根本不调用它，于是 START 发出后没有任何一刻满足发 STOP 的条件，
 *       服务端永远收不到 STOP，方块自然不动。现改为 <b>由模块每客户端刻驱动
 *       {@link #tick(Minecraft, AutoMinerModule)}</b>，外部调用只用来登记目标与让位，不再决定节奏。</li>
 *   <li><b>停挖记账会中止服务端槽位</b>：Baritone 的 {@code BlockBreakHelper.stopBreakingBlock()}
 *       → {@code resetBlockRemoving()} → 原版 {@code stopDestroyBlock()}，旧实现在这里发 ABORT。
 *       Baritone 每次给方块破坏收尾（{@code PathExecutor.cancel} / {@code PathingBehavior} 暂停）
 *       都会把服务端累计破坏进度清零，于是 START→ABORT→START 无限循环：<b>挖不烂</b>。
 *       现改为 {@link #stop(Minecraft)} 只取消原版那一个 ABORT 包，循环交给 tick 驱动收尾；
 *       真正要放弃（模块关闭 / 目标非法）才由 {@link #release(Minecraft, boolean)} 发 ABORT。</li>
 *   <li><b>没有失败会让位</b>：工具不对（破坏速度为 0）、服务端拒绝、方块受保护时，旧实现会一直占着
 *       这个方块反复发包，原版路径被完全屏蔽，玩家看到的就是「挖不烂」。现补 {@code MAX_ATTEMPTS}
 *       次重试；仍失败就把<b>这一个坐标</b>拉黑 {@code BLOCKLIST_TICKS} 刻彻底放行原版
 *       （坐标级而不是全局暂停——全局暂停会在下一轮接管时又打断原版进度，照样挖不烂），
 *       期间原版能安安稳稳把方块按原速挖掉。</li>
 * </ol>
 *
 * <p>单槽串行语义保持不变：任意时刻只有一个方块处于「START 已发、正在累积进度」。
 * 但<b>确认阶段已流水线化</b>（见下）。进度与目标位置对外只读暴露
 * （{@link #progress()} / {@link #targetPos()}），供 ESP 的「百分比 + 收缩框」渲染使用。</p>
 *
 * <h2>流水线（用户 2026-09-18：「秒破还可以升级加强吗，真正的暴力，还是太慢了」）</h2>
 *
 * <p>旧实现每块要等一个网络往返：发完 STOP → 等权威方块变化包 → 再等方块间隔 → 才开始下一块。
 * 实机 chatlog 里连锁是 5~6 刻/块（19:48:10 识别到 3 块 → 19:48:11 完成），
 * 而服务端按 0.7 阈值算出来的下限只要 1~2 刻（效率镐挖青金石），也就是 4~5 刻全是我方自耗。</p>
 *
 * <p>现在：发完 STOP 就把这块交给 {@link #pending}（待确认集合）并当刻回到空闲，
 * 下一块只受「方块间隔」与 1 刻保险约束立刻开始。为什么协议上安全（26.1.2 服务端
 * {@code ServerPlayerGameMode}）：</p>
 * <ul>
 *   <li>{@code handleBlockBreakAction} 的 STOP 分支只认
 *       {@code getDestroyProgress * (ticksSpent + 1) >= 0.7}，成功即 {@code destroyAndAck}
 *       并清掉自己的槽位；我方 STOP 是按同一公式算准的，服务端当刻就把方块删掉。</li>
 *   <li>万一 STOP 发早了（工具切换的服务端同步还没到，f 被服务端算小），
 *       {@code tick:111-121} 的 delayed destroy 槽会接管这块，
 *       {@code isDestroyingBlock} 同时被置 false，所以下一块的 START 不会把它顶掉——
 *       服务端会自己在后台把它挖完。我方 {@link #sweepPending} 到点也校验得出来。</li>
 *   <li>校验超时（{@link #STOP_RESEND_AFTER_TICKS}）仍会重试，重试用尽照旧只拉黑那一个坐标、放行原版。</li>
 * </ul>
 */
public final class MiningFastBreakController {

    /** 服务端 STOP 的最早 tick 判据（26.1.2 服务端存活判定阈值） */
    private static final float SERVER_STOP_THRESHOLD = 0.7F;

    /** STOP 发出后超过这么多刻方块仍未消失，判定服务器没接受这次破坏 → 重试 */
    private static final int STOP_RESEND_AFTER_TICKS = 20;

    /** MINING 阶段超过「达标刻数 + 该值」仍未打破，判定本次发包没被服务端登记 → 重发 START */
    private static final int RESTART_AFTER_TICKS = 30;

    /** 同一目标的最大重试次数（含首次），超过即判定该方块挖不动 */
    private static final int MAX_ATTEMPTS = 3;

    /** 判定挖不动后放行原版的时长（刻）：只针对那一个坐标，期间原版可完整挖完它 */
    private static final int BLOCKLIST_TICKS = 600;

    /** 流水线：相邻两块之间至少留的刻数保险（用户 2026-09-18 裁定「留 1 刻保险」） */
    private static final int PIPELINE_MIN_GAP_TICKS = 1;

    /**
     * 待确认集合的安全上限。
     *
     * <p>正常情况下到不了：校验窗口 {@link #STOP_RESEND_AFTER_TICKS} 内按 2~3 刻/块最多也就十来个。
     * 到顶只有一种可能——服务端长时间不回方块变化包，此时按「已破坏」收尾最旧的几条，
     * 免得集合无界增长（真没破坏掉的块，连锁那边还会作为矿脉根被重扫）。</p>
     */
    private static final int MAX_PENDING = 16;

    /** 同一块重试之间的最小间隔（刻）：重试要重发 START，太快会与 Baritone 抢同一个方块的进度 */
    private static final int RETRY_MIN_GAP_TICKS = 10;

    /** 同一条「让位原版」提示的最小间隔（刻），避免刷屏 */
    private static final int WARN_COOLDOWN_TICKS = 600;

    /** 全局唯一状态机：Mixin 接管入口与模块每刻驱动共用同一份状态 */
    private static final MiningFastBreakController INSTANCE = new MiningFastBreakController();

    public static MiningFastBreakController instance() {
        return INSTANCE;
    }

    private final MiningPacketProtocol protocol = new MiningPacketProtocol();

    private ClientLevel level;
    private BlockPos pos;
    private Direction direction;
    private Block originalBlock;
    private Phase phase = Phase.IDLE;

    /** 本轮 START 发出的客户端 tick（服务端公式的 elapsed 起点） */
    private int startTick;
    /** 同一目标的发包尝试次数 */
    private int attempts;
    /** 下一个目标的最早 START tick（方块间隔） */
    private int nextStartTick;
    /** 「挖不动」的方块坐标 → 放行原版的截止 tick（键为 {@code BlockPos#asLong}） */
    private final Map<Long, Integer> blockedUntil = new HashMap<>();
    /** 上一条「让位原版」提示的 tick */
    private int lastWarnTick = Integer.MIN_VALUE;
    /**
     * 上一次看到的玩家 tickCount；用于识别「玩家实体被换掉」，见 {@link #syncEpoch(Minecraft)}。
     *
     * <p>初值 -1 而不是 0：第一个 tick 的 tickCount 可能是 0，用 0 当哨兵会误判成「倒退」。</p>
     */
    private int lastSeenTick = -1;
    /** 当前目标的进度（0~1），每刻更新，供 ESP 读取 */
    private float progress;
    /**
     * 刚被破坏的方块与其客户端 tick。
     *
     * <p>给 ESP 的「完成残影」用：软方块（泥土、沙子、矿石…）在这套发包破坏下常常
     * START / STOP 同刻完成，活跃窗口只有 1~2 刻，渲染帧经常一帧都赶不上——方块是被秒破挖掉的，
     * 玩家却看不到任何进度。破坏后把这个位置留一段时间，让 ESP 补显示完成态
     * （用户 2026-09-17：「自动挖矿状态下不管挖什么方块都要有这个进度显示」）。</p>
     */
    private BlockPos lastBrokenPos;
    private int lastBrokenTick;
    /** 刚被破坏的是哪种方块（方块已变成空气，连锁挖矿只能靠它判断矿脉族别） */
    private Block lastBrokenBlock;

    /**
     * 流水线：已发出破坏请求、等权威方块变化的方块（键为 {@code BlockPos#asLong}）。
     *
     * <p>进入这里意味着控制器已经空闲、下一块可以走了——{@link #isActive()} <b>不含</b> pending，
     * 否则连锁会一直卡在「等确认」上，流水线就白做了。</p>
     */
    private final Map<Long, PendingBreak> pending = new LinkedHashMap<>();
    /** 校验超时、等待重试的方块（空闲刻由 {@link #dispatchRetry} 补挖） */
    private final Deque<PendingBreak> retries = new ArrayDeque<>();
    /** 下一次允许重试的 tick（限速，见 {@link #RETRY_MIN_GAP_TICKS}） */
    private int nextRetryTick;

    /** 一件「破坏请求已发出、等权威同步」的记账（流水线核心，见类注释） */
    private static final class PendingBreak {
        final BlockPos pos;
        final Block originalBlock;
        final Direction direction;
        /** 请求发出刻（STOP 的登记刻），超时判定起点 */
        final int sentTick;
        /** 已尝试次数（沿用 MAX_ATTEMPTS 语义） */
        int attempts;

        PendingBreak(BlockPos pos, Block originalBlock, Direction direction, int sentTick, int attempts) {
            this.pos = pos;
            this.originalBlock = originalBlock;
            this.direction = direction;
            this.sentTick = sentTick;
            this.attempts = attempts;
        }
    }

    private MiningFastBreakController() {
    }

    /** Mixin 的返回裁决；PASS 表示交还原版处理。 */
    public enum StartResult {
        PASS,
        ACCEPTED,
        COOLDOWN
    }

    private enum Phase {
        IDLE,
        MINING
    }

    // ── Mixin 接管入口 ──────────────────────────────────────────────────────

    /** 接管一次 startDestroyBlock；不可破坏目标与未接管状态仍交还原版。 */
    public StartResult start(Minecraft mc, AutoMinerModule module, BlockPos target, Direction face) {
        syncEpoch(mc);
        if (!ready(mc, module)) {
            release(mc, true);
            return StartResult.PASS;
        }

        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        BlockState state = currentLevel.getBlockState(target);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) {
            return StartResult.PASS;
        }
        // 这个方块刚被判定「秒破挖不动」：整条路径放行原版，让原版按原速挖掉它（不再插手）
        if (isBlocked(player.tickCount, target)) {
            return StartResult.PASS;
        }
        // 这块刚从我们手里交出去、还在等服务端确认（pending）：**接管但不重发包**。
        //
        // 两侧都不能走：
        // ① 不能重复 START —— 会把服务端已累计的破坏进度清零（它只记一个方块），
        //    于是 Baritone 又请求一遍、我们又顶掉一遍 → 「一直不挖前面的方块」；
        // ② 更不能放行原版 —— 原版会跑它自己的破坏进度，并在 `destroyProgress >= 1.0` 时做
        //    **客户端预测破坏**（本地直接把方块删掉）。那个「本地删除」下一 tick 会被
        //    {@link #sweepPending} 当成服务端确认 → 记账清空、`brokenAge` 刷新，
        //    于是重试 / 拉黑那套兜底**永远不触发**；服务端随后回滚方块 → 每 tick 重来一次。
        //    实机证据（用户 2026-09-18 砂岩，且该方块模块关掉、纯原版也挖不掉 = 服务端不让挖）：
        //    同一坐标被按「每 50ms 一次 START」死磕 1152 + 620 次，`brokenAge` 恒为 1、`stale` 只有 56。
        // 接管（不 cancel 也不发包）后，方块只会因**服务端真的删掉它**而变化，记账才可信：
        // 真破了下一 tick 就认账；服务端不认则 20 刻超时 → 重试 3 次 → 拉黑该坐标放行原版，不再死磕。
        if (pending.containsKey(target.asLong())) {
            return StartResult.ACCEPTED;
        }

        // 只有「手上有目标」时才谈得上世界切换：level 为空是空闲态，
        // 此时若按 level != currentLevel 判，会把流水线的待确认记账整个清掉
        if (level != null && level != currentLevel) release(mc, false);
        if (isActive() && pos != null && pos.equals(target)) {
            return StartResult.ACCEPTED;
        }
        // 换目标：只丢当前这一块，待确认集合里别的方块的账照留（流水线）
        if (isActive()) dropTarget(mc, true);
        if (player.tickCount < nextStartTick) {
            return StartResult.COOLDOWN;
        }

        begin(player, currentLevel, target, face, state, module);
        return StartResult.ACCEPTED;
    }

    /** 接管一次 continueDestroyBlock；只作为「同一目标仍在被挖」的心跳。 */
    public boolean continueBreaking(Minecraft mc, AutoMinerModule module, BlockPos target, Direction face) {
        syncEpoch(mc);
        if (!ready(mc, module)) {
            release(mc, true);
            return false;
        }
        // 同 start()：只在手上有目标时判世界切换，空闲态的 level 为空不算切换
        if (level != null && level != mc.level) release(mc, false);

        if (!isActive() || pos == null || !pos.equals(target)) {
            // 只有真正接管（ACCEPTED）才算数；COOLDOWN（方块间隔冷却）与 PASS 都必须交还原版。
            //
            // 旧实现是 `return start(...) != StartResult.PASS`，把 COOLDOWN 也当成「已接管」返回 true，
            // 于是 Mixin 把原版 continueDestroyBlock 拦掉、我们又没发包：Baritone 收到「正在挖」的
            // 假信号，进度永远是 0 —— 手上工具对口（铲子挖草方块）却挖不掉（用户 2026-09-18 实机）
            return start(mc, module, target, face) == StartResult.ACCEPTED;
        }
        advance(mc, module);
        return true;
    }

    /**
     * 外部请求破坏一个方块（连锁挖矿用）。
     *
     * <p>这些方块不是原版 / Baritone 调进来的（没有 {@code startDestroyBlock} 调用可以截获），
     * 所以由模块自己发起：语义等同一次被接受的 START，其余流程完全复用同一套
     * （服务端 0.7 阈值 → STOP → 交进待确认集合 → 校验超时重试 / 挖不动拉黑），因此连锁的每一块
     * 都享受秒破的速度与安全性。</p>
     *
     * <p>与 {@link #start} 的差别：不通过 Mixin、不做「同目标去重」（调用方已保证不重复请求），
     * 但<b>保留</b>方块间隔冷却、挖不动黑名单、交互距离与可破坏性校验——
     * 否则连锁会在冷却期空转或反复请求同一个挖不动的方块。</p>
     *
     * @return true 表示已受理并发出 START
     */
    public boolean mineRequested(Minecraft mc, AutoMinerModule module, BlockPos target, Direction face) {
        syncEpoch(mc);
        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        if (isActive() || !ready(mc, module)) return false;
        if (player == null || currentLevel == null || target == null) return false;
        if (level != null && level != currentLevel) {
            // 换世界：先放掉上一世界的状态，本轮不派发（下一 tick level 已归零，正常继续）
            release(mc, false);
            return false;
        }
        if (player.tickCount < nextStartTick) return false;
        if (isBlocked(player.tickCount, target)) return false;
        // 还在等服务端确认（见 start() 里同名分支的说明），本轮不派发
        if (pending.containsKey(target.asLong())) return false;
        BlockState state = currentLevel.getBlockState(target);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) return false;
        if (!player.isWithinBlockInteractionRange(target, 1.0)) return false;
        begin(player, currentLevel, target, face == null ? Direction.UP : face, state, module);
        return true;
    }

    /**
     * 每客户端刻驱动（由 {@code AutoMinerModule#onTick} 调用）。
     *
     * <p>这是本轮重做的核心：循环推进不再依赖 Baritone 是否调到 {@code continueDestroyBlock}，
     * 因此「准星不在方块上」「正在算路」的那几刻也不会让 START 白发给服务端。</p>
     *
     * <p>流水线之后本方法还有两件事要做：先把待确认集合结掉（成功的记入「刚破坏」，
     * 超时的排进重试），再在空闲刻补挖漏掉的块——所以它不能再像以前那样「不活跃就直接 return」。</p>
     */
    public void tick(Minecraft mc, AutoMinerModule module) {
        if (mc.player == null) return;
        // 换实体检测要排在心跳之前：心跳报的必须是本实体（归零后）的真实计时刻
        syncEpoch(mc);
        if (!ready(mc, module)) {
            release(mc, true);
            return;
        }
        // level 只有「正在挖」时才非空；空闲刻不能拿它做世界切换判定，否则会每刻把 pending / 重试清掉
        if (level != null && level != mc.level) {
            release(mc, false);
            return;
        }
        sweepPending(mc, module);
        if (isActive()) advance(mc, module);
        dispatchRetry(mc, module);
    }

    /**
     * 接管原版停挖入口（Baritone 的 {@code resetBlockRemoving}、原版 {@code stopDestroyBlock}）。
     *
     * <p>只取消原版那一个 ABORT 包，<b>不中止本次发包破坏</b>：Baritone 每次给破坏助手收尾都会走到这里，
     * 旧实现在这里发 ABORT，等于每几刻把服务端累计进度清零（「一直挖却挖不烂」的成因）。
     * 循环由 {@link #tick} 推进到方块真正被破坏或判定失败为止。</p>
     *
     * @return true 表示本次调用被接管（Mixin 应 {@code cancel()} 掉原版实现）
     */
    public boolean stop(Minecraft mc) {
        return isActive();
    }

    /** 模块关闭、断线、换世界时的统一清理入口（流水线记账一并丢弃：模块都关了就不该再补挖）。 */
    public void release(Minecraft mc, boolean abortMining) {
        dropTarget(mc, abortMining);
        pending.clear();
        retries.clear();
    }

    /**
     * 只丢掉当前目标（换目标 / 走出交互距离）。
     *
     * <p>流水线记账不动：待确认集合里是<b>别的方块</b>的账，换个目标不该把它们一起抹掉
     * （旧实现里「换目标 = release」是因为那时只有单块账，流水线之后必须分开）。</p>
     */
    private void dropTarget(Minecraft mc, boolean abortMining) {
        if (!isActive()) {
            clearState();
            return;
        }
        LocalPlayer player = mc.player;
        if (abortMining && player != null && phase == Phase.MINING && pos != null && direction != null) {
            protocol.sendAbort(player, pos, direction);
        }
        if (player != null) clearCracks(player);
        clearState();
    }

    // ── 对外只读状态（ESP / 状态机用） ────────────────────────────────────────

    public boolean isActive() {
        return phase != Phase.IDLE;
    }

    /** 是否处于「START 已发、等 STOP / 等确认」的挖掘中段落 */
    public boolean isMining() {
        return phase == Phase.MINING;
    }

    /** 当前正在发包破坏的方块；无目标返回 {@code null}。 */
    public BlockPos targetPos() {
        return pos;
    }

    /** 当前目标的进度（0~1）：按服务端同源公式算出，供 ESP 的百分比与收缩框使用。 */
    public float progress() {
        if (!isActive()) return 0f;
        return Math.max(0f, Math.min(1f, progress));
    }

    /** 刚被破坏的方块位置（渲染「完成残影」用）；从未破坏过返回 {@code null}。 */
    public BlockPos lastBrokenPos() {
        return lastBrokenPos;
    }

    /** 刚被破坏方块的那一客户端 tick（渲染端用它算残影年龄）。 */
    public int lastBrokenTick() {
        return lastBrokenTick;
    }

    /** 刚被破坏的方块类型；从未破坏过返回 {@code null}（方块此时已是空气，连锁挖矿靠它认矿脉族别）。 */
    public Block lastBrokenBlock() {
        return lastBrokenBlock;
    }

    // ── 内部推进 ────────────────────────────────────────────────────────────

    /** 登记一个新目标并发出 START（必要时同刻发 STOP 实现真瞬破） */
    private void begin(LocalPlayer player, ClientLevel currentLevel, BlockPos target, Direction face,
                       BlockState state, AutoMinerModule module) {
        begin(player, currentLevel, target, face, state, module, 0);
    }

    /**
     * 登记一个新目标并发出 START（必要时同刻发 STOP 实现真瞬破）。
     *
     * @param carriedAttempts 之前已经尝试过几次（流水线重试时沿用旧计数，避免无限重试；
     *                        正常登记传 0）
     */
    private void begin(LocalPlayer player, ClientLevel currentLevel, BlockPos target, Direction face,
                       BlockState state, AutoMinerModule module, int carriedAttempts) {
        level = currentLevel;
        pos = target.immutable();
        direction = face == null ? Direction.UP : face;
        originalBlock = state.getBlock();
        startTick = player.tickCount;
        attempts = carriedAttempts;
        progress = 0f;
        phase = Phase.MINING;

        // 先把手持切到更快的工具再发 START：否则客户端与服务端都按「手上那块垫脚方块」算速度
        // （青金石矿 7 刻 → 210 刻），表现就是「卡死不动，手动换镐才动」（用户 2026-09-18 实机）
        ensureFasterTool(player, state);
        protocol.sendStart(player, currentLevel, pos, direction, state);
        updateCracks(player, 0.0F);

        float delta = destroyDelta(player, currentLevel);
        if (!(delta > 0.0F)) {
            // 手上工具破坏速度为 0（工具不对 / 方块受保护）：立刻让位原版，别占着方块反复发包
            fail(player, module, "当前工具挖不动");
            return;
        }
        // delta 已满足 0.7 阈值（含硬度 0 的 +∞）：同刻严格按 START→STOP 顺序真正瞬破。
        //
        // 这里曾经有一条「delta ≥ 1.0 就只发 START、不发 STOP」的近路，假设服务端收到 START 会
        // 直接 destroyAndAck。那条假设只在「服务端自己算出来的进度也 ≥ 1.0」时成立；一旦服务端的
        // 进度落在 [0.7, 1.0)（工具/效果与客户端不一致、被插件插手），它就只开一个「累计破坏会话」
        // 等我们的 STOP —— 而 Baritone 每刻都会重新请求同一块，于是我们每刻补发一个 START，
        // 服务端每次 START 都把 destroyProgressStart 重置（顺带回滚一次上一轮的方块），
        // 累计永远攒不够 0.7 → 方块永远挖不掉。
        // 实机证据（用户 2026-09-18「一直挖一个方块挖不掉 砂岩」）：同一坐标 begin / handOff 各 416 次、
        // 每 50ms 一轮，delta = 1.4583（效率 V 镐），全程 pending 被「假破坏」清空、兜底从未触发。
        if (requiredElapsedTicks(delta) == 0) {
            sendStop(player, currentLevel, module);
        }
    }

    /** 单刻推进：目标变化 → 结算；否则按服务端阈值发 STOP / 超时重试 */
    private void advance(Minecraft mc, AutoMinerModule module) {
        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        if (player == null || currentLevel == null || pos == null || direction == null) {
            clearState();
            return;
        }

        // 权威方块已变化 = 方块被破坏（或变成别的方块）：本轮结束
        if (hasAuthoritativeChange(currentLevel)) {
            finish(player, module.getBreakInterval());
            return;
        }
        // 走出交互距离：服务端不会再接受该方块的 START/STOP，直接收摊（不 ABORT，避免多打一个包）。
        // 注意只丢当前目标：待确认集合里的其它块是别的方块的账，不能一起清
        if (!player.isWithinBlockInteractionRange(pos, 1.0)) {
            clearCracks(player);
            clearState();
            return;
        }

        float delta = destroyDelta(player, currentLevel);
        if (!(delta > 0.0F)) {
            fail(player, module, "当前工具挖不动");
            return;
        }
        int elapsed = player.tickCount - startTick;
        int required = requiredElapsedTicks(delta);
        if (required == Integer.MAX_VALUE) {
            // 破坏速度小到永远够不到阈值（工具不对 / 方块受保护）：别占着方块，直接让位原版
            fail(player, module, "当前工具挖不动");
            return;
        }
        updateCracks(player, Math.min(1.0F, delta * (elapsed + 1) / SERVER_STOP_THRESHOLD));
        if (elapsed >= required) {
            sendStop(player, currentLevel, module);
            return;
        }
        // 远超达标刻数还没打破：多半是服务端槽位被别的 START 顶掉了，重发一次 START 重新登记
        if (elapsed > required + RESTART_AFTER_TICKS && retry(player, module, "服务端未登记本次破坏")) {
            launch(player, currentLevel);
        }
    }

    /** 重发 START 前的重试计数；超过上限判定挖不动并让位原版 */
    private boolean retry(LocalPlayer player, AutoMinerModule module, String reason) {
        attempts++;
        if (attempts <= MAX_ATTEMPTS) return true;
        fail(player, module, reason);
        return false;
    }

    /** 以全新 sequence 重新登记 START，回到 MINING 段落 */
    private void launch(LocalPlayer player, ClientLevel currentLevel) {
        if (pos == null || direction == null) return;
        startTick = player.tickCount;
        progress = 0f;
        phase = Phase.MINING;
        protocol.sendStart(player, currentLevel, pos, direction, currentLevel.getBlockState(pos));
    }

    /**
     * 到达服务端 0.7 卡点：发 STOP 并<b>当刻把这块交给待确认集合</b>，控制器立刻回到空闲。
     *
     * <p>旧实现在这里进入 AWAITING_CONFIRM，要等权威方块变化包回来才算完事——那就是每块一个网络往返。
     * 现在只发一个包就走人（见类注释「流水线」）。</p>
     */
    private void sendStop(LocalPlayer player, ClientLevel currentLevel, AutoMinerModule module) {
        if (phase != Phase.MINING || pos == null || direction == null) return;
        protocol.sendStop(player, currentLevel, pos, direction);
        if (module.getBypassAnticheat()) {
            protocol.sendAbort(player, pos.above(), direction);
        }
        clearCracks(player); // 我方进度条收掉（服务端破坏成功时也会发 -1）
        handOffToPending(player, module);
    }

    /**
     * 把当前目标交给待确认集合并结束本轮占用。
     *
     * <p>唯一入口是 {@link #sendStop}（STOP 已发）：从这里开始只需要「等权威同步」这一件事，
     * 由 {@link #sweepPending} 结算——方块变了记入「刚破坏」，超时没变则重试 / 拉黑。</p>
     */
    private void handOffToPending(LocalPlayer player, AutoMinerModule module) {
        if (pos != null && originalBlock != null && direction != null) {
            long key = pos.asLong();
            // 「同一方块再次交进待确认」绝不能刷新 sentTick（用户 2026-09-18 实机：一直不挖前面的方块）：
            // 服务端没吃下这次破坏时，Baritone 会反复请求同一块，于是这里每 ≤20 刻被覆盖一次，
            // sweepPending 的超时（STOP_RESEND_AFTER_TICKS）永远命中不了 → 既不重试也不拉黑，
            // 方块被我们反复 START 顶掉服务端进度，谁都挖不烂。保留最早那条的时间戳才能如实超时。
            if (!pending.containsKey(key)) {
                if (pending.size() >= MAX_PENDING) {
                    // 安全阀（正常到不了）：最旧的一条按已破坏收尾，避免集合无界增长
                    Iterator<Map.Entry<Long, PendingBreak>> iterator = pending.entrySet().iterator();
                    if (iterator.hasNext()) {
                        PendingBreak oldest = iterator.next().getValue();
                        iterator.remove();
                        noteBroken(player, oldest);
                    }
                }
                pending.put(key,
                    new PendingBreak(pos.immutable(), originalBlock, direction, player.tickCount, attempts));
            }
        }
        // 下一块的最早受理刻：用户设置的方块间隔与 1 刻保险取较大者（用户 2026-09-18：「留 1 刻保险」）
        nextStartTick = player.tickCount + Math.max(PIPELINE_MIN_GAP_TICKS, module.getBreakInterval());
        clearState();
    }

    /**
     * 结算待确认集合：方块已变 = 破坏成功（记入「刚破坏」，ESP 残影与连锁的矿脉根都读它）；
     * 超过 {@link #STOP_RESEND_AFTER_TICKS} 还没变 = 这次破坏服务端没吃下，排进重试。
     */
    private void sweepPending(Minecraft mc, AutoMinerModule module) {
        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        if (player == null || currentLevel == null || pending.isEmpty()) return;
        Iterator<Map.Entry<Long, PendingBreak>> iterator = pending.entrySet().iterator();
        while (iterator.hasNext()) {
            PendingBreak entry = iterator.next().getValue();
            if (currentLevel.getBlockState(entry.pos).getBlock() != entry.originalBlock) {
                noteBroken(player, entry);
                iterator.remove();
                continue;
            }
            if (player.tickCount - entry.sentTick <= STOP_RESEND_AFTER_TICKS) continue;
            iterator.remove();
            entry.attempts++;
            if (entry.attempts <= MAX_ATTEMPTS) {
                retries.add(entry);
            } else {
                // 与旧实现同一口径：重试用尽只拉黑这一个坐标，放行原版按原速挖
                maybeWarn(player, module, "服务端未确认方块破坏");
                blockedUntil.entrySet().removeIf(e -> e.getValue() <= player.tickCount);
                blockedUntil.put(entry.pos.asLong(), player.tickCount + BLOCKLIST_TICKS);
            }
        }
    }

    /**
     * 空闲刻补挖校验失败的块（流水线版的「重发 START」）。
     *
     * <p>限速 {@link #RETRY_MIN_GAP_TICKS}，且一次只放一块：重试要重发 START，
     * 连发会与 Baritone 抢同一个方块的进度（那正是「一直挖却挖不烂」的老成因）。</p>
     */
    private void dispatchRetry(Minecraft mc, AutoMinerModule module) {
        if (retries.isEmpty() || phase != Phase.IDLE) return;
        LocalPlayer player = mc.player;
        ClientLevel currentLevel = mc.level;
        if (player == null || currentLevel == null) return;
        if (player.tickCount < nextStartTick || player.tickCount < nextRetryTick) return;

        PendingBreak entry = retries.poll();
        BlockState state = currentLevel.getBlockState(entry.pos);
        // 服务端 delayed destroy 槽已经替我们把它挖掉了：什么都不用做
        if (state.isAir() || state.getBlock() != entry.originalBlock) return;
        if (state.getBlock().defaultDestroyTime() < 0.0F) return;
        if (isBlocked(player.tickCount, entry.pos)) return;
        if (!player.isWithinBlockInteractionRange(entry.pos, 1.0)) return; // 走远了：交给 Baritone 处理
        nextRetryTick = player.tickCount + RETRY_MIN_GAP_TICKS;
        begin(player, currentLevel, entry.pos, entry.direction, state, module, entry.attempts);
    }

    /** 记入「刚破坏」：ESP 的完成残影与连锁挖矿的矿脉根都靠这三项 */
    private void noteBroken(LocalPlayer player, PendingBreak entry) {
        lastBrokenPos = entry.pos;
        lastBrokenTick = player.tickCount;
        lastBrokenBlock = entry.originalBlock;
    }

    /** 判定挖不动：清理 + 把该坐标拉黑一段时间彻底放行原版，并按节流提示一次 */
    private void fail(LocalPlayer player, AutoMinerModule module, String reason) {
        maybeWarn(player, module, reason);
        clearCracks(player);
        if (pos != null) {
            // 只拉黑这一个坐标（不是全局暂停）：放行原版后原版能按原速把它挖掉；
            // 若改成全局暂停，下一轮接管又会打断原版进度，反而变成「永远挖不烂」
            blockedUntil.entrySet().removeIf(entry -> entry.getValue() <= player.tickCount);
            blockedUntil.put(pos.asLong(), player.tickCount + BLOCKLIST_TICKS);
        }
        clearState();
    }

    /** 该坐标是否处于「秒破挖不动」放行期（连锁挖矿据此跳过挖不动的方块，避免队列卡死） */
    public boolean isBlocked(int currentTick, BlockPos target) {
        if (target == null) return false;
        Integer until = blockedUntil.get(target.asLong());
        return until != null && currentTick < until;
    }

    private void maybeWarn(LocalPlayer player, AutoMinerModule module, String reason) {
        // 哨兵必须显式判：lastWarnTick = Integer.MIN_VALUE 参与减法会溢出成负数，
        // 于是「第一条提示」永远发不出来（与岩浆透视那次是同一类坑）
        if (lastWarnTick != Integer.MIN_VALUE && player.tickCount - lastWarnTick < WARN_COOLDOWN_TICKS) return;
        lastWarnTick = player.tickCount;
        module.info("§e⚠ 秒破让位原版 §8▸ " + reason + " §8（该方块按原速挖掘）");
    }

    /** 目标打破：清裂纹、进入方块间隔冷却（与流水线口径一致，至少留 1 刻保险） */
    private void finish(LocalPlayer player, int intervalTicks) {
        clearCracks(player);
        nextStartTick = player.tickCount + Math.max(PIPELINE_MIN_GAP_TICKS, intervalTicks);
        if (pos != null) {
            // 先记下位置再 clearState（clearState 会把 pos 清空）：
            // ESP 靠这两项补显示「完成残影」，否则同刻挖掉的方块一帧进度都看不到
            lastBrokenPos = pos;
            lastBrokenTick = player.tickCount;
            lastBrokenBlock = originalBlock;
        }
        clearState();
    }

    private float destroyDelta(LocalPlayer player, ClientLevel currentLevel) {
        if (pos == null) return 0.0F;
        BlockState state = currentLevel.getBlockState(pos);
        return state.getDestroyProgress(player, currentLevel, pos);
    }

    /**
     * 保证主手拿的是「比当前更快的工具」（只扫快捷栏 0~8，与 Baritone {@code ToolSet#getBestSlot} 同口径）。
     *
     * <p>用户 2026-09-18 实机：「有时候切换到垫脚方块就卡死状态了，然后我切换成镐子才启动」。
     * 根因：主手破坏速度决定服务端 0.7 判定所需的刻数（青金石矿配镐约 7 刻，拿垫脚方块/空手约 210 刻），
     * 而秒破此前完全依赖 Baritone 的 autoTool —— 连锁接管会把 Baritone stop 掉
     * （{@code suspended=true}），它的 autoTool 随之停摆，垫脚方块就留在手上，于是「卡死不动」。</p>
     *
     * <p>判据用<b>严格更快才切</b>，不用 {@code isCorrectToolForDrops}：两把镐（时运/精准）速度相同时
     * 保持玩家与 Baritone 的当前选择，不破坏它们的选工具偏好；快捷栏里确实没有更快工具时什么都不做，
     * 那属于 {@code MiningContainer#ensureToolsInHotbar()} 的职责（把背包里的工具搬进快捷栏）。</p>
     */
    private void ensureFasterTool(LocalPlayer player, BlockState state) {
        net.minecraft.world.entity.player.Inventory inventory = player.getInventory();
        int selected = inventory.getSelectedSlot();
        float currentSpeed = inventory.getItem(selected).getDestroySpeed(state);
        int bestSlot = -1;
        float bestSpeed = currentSpeed;
        for (int slot = 0; slot < 9; slot++) {
            float speed = inventory.getItem(slot).getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = slot;
            }
        }
        if (bestSlot < 0) return;
        inventory.setSelectedSlot(bestSlot);
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() != null) {
            client.getConnection().send(
                new net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket(bestSlot));
        }
    }

    private boolean hasAuthoritativeChange(ClientLevel currentLevel) {
        if (pos == null || originalBlock == null) return false;
        return currentLevel.getBlockState(pos).getBlock() != originalBlock;
    }

    /**
     * 返回服务端公式中满足 0.7 阈值的最小 elapsed tick。
     *
     * <p>与服务端 {@code f * (gameTicks - startTick + 1) >= threshold} 同源，用 float 乘法复核，
     * 保证发 STOP 的那一 tick 服务端恰好判定成功（早一刻会被丢掉并清零累计进度）。</p>
     *
     * <p>两个哨兵值的归属：{@link Integer#MAX_VALUE} 表示「永远够不到」（破坏速度为 0 / NaN，
     * 即工具不对或方块受保护，由调用方判挖不动）；<b>0</b> 表示「本刻就达标」——除了
     * {@code delta ≥ 0.7} 的正常情形，还包括硬度 0 方块算出来的 <b>+∞</b>（草、火把、花这类
     * 一发即破的目标）。+∞ 不能按「够不到」处理，否则零硬度方块会被判成挖不动并拉黑。</p>
     */
    private int requiredElapsedTicks(float delta) {
        if (!(delta > 0.0F)) return Integer.MAX_VALUE;
        if (!Float.isFinite(delta)) return 0;
        double samples = Math.ceil(SERVER_STOP_THRESHOLD / (double) delta);
        if (samples >= Integer.MAX_VALUE) return Integer.MAX_VALUE;
        int elapsed = Math.max(0, (int) samples - 1);
        while (elapsed < Integer.MAX_VALUE - 1 && delta * (elapsed + 1) < SERVER_STOP_THRESHOLD) elapsed++;
        while (elapsed > 0 && delta * elapsed >= SERVER_STOP_THRESHOLD) elapsed--;
        return elapsed;
    }

    private boolean ready(Minecraft mc, AutoMinerModule module) {
        return mc.player != null && mc.level != null && module != null
            && module.isEnabled() && module.getFastBreak();
    }

    private void updateCracks(LocalPlayer player, float value) {
        progress = value;
        if (level == null || pos == null) return;
        int stage = Math.min(9, Math.max(0, (int) (value * 10.0F)));
        level.destroyBlockProgress(player.getId(), pos, stage);
    }

    private void clearCracks(LocalPlayer player) {
        if (level != null && pos != null) level.destroyBlockProgress(player.getId(), pos, -1);
    }

    private void clearState() {
        level = null;
        pos = null;
        direction = null;
        originalBlock = null;
        phase = Phase.IDLE;
        startTick = 0;
        attempts = 0;
        progress = 0f;
    }

    /**
     * 玩家实体换了一茬就把全部「绝对值计时刻」归零（判断依据：{@link LocalPlayer#tickCount} 倒退）。
     *
     * <p><b>为什么必须做</b>（用户 2026-09-18 实机：「卸完活 重新进入状态，秒破 连锁失效，
     * esp 框也不见了」）：RTP / 换维度 / 复活都会重建 {@code LocalPlayer}，新实体的
     * {@code tickCount} <b>从 0 重新开始</b>，而本类的冷却与记账写的都是<b>上一个实体</b>的绝对刻。
     * 于是 {@code nextStartTick}（上一轮挖完那块时记的 {@code 旧tickCount + 间隔}）在新实体看来
     * 落在很远的未来，{@link #start} 一直判 {@link StartResult#COOLDOWN}、
     * {@link #mineRequested} 全部拒绝——连锁因此派不出任何一块（没有矿脉根），
     * ESP 的挖掘进度框也因为 {@link #isActive()} 恒假 + 残影过期而不画。三者是同一个成因。</p>
     *
     * <p>实测证据（debug 会话 mining-stall-after-rtp，卸货 → RTP 回野外之后）：
     * {@code gap = tickCount - nextStartTick = -4516} 且每 20 刻只回 +20，也就是要
     * <b>静默冷却 4516 刻（约 3.8 分钟）</b>才会自己恢复；同期 45 条 {@code FastBreak.start}
     * 裁决<b>全部</b>是 {@code cooldown}，{@code begin / handOff / root-scan} 一条都没有。</p>
     *
     * <p>放在四个对外入口（{@link #start} / {@link #continueBreaking} / {@link #mineRequested} /
     * {@link #tick}）的最前面：Mixin 的 {@code startDestroyBlock} 可能比模块的每刻驱动先跑到，
     * 只在 {@link #tick} 里判会漏掉换实体后的第一次接管。</p>
     */
    private void syncEpoch(Minecraft mc) {
        if (mc == null || mc.player == null) return;
        int tick = mc.player.tickCount;
        if (tick < lastSeenTick) {
            resetTimers();
        }
        lastSeenTick = tick;
    }

    /**
     * 换世界（换维度 / 重连 / 换玩家实体）时把冷却、黑名单与流水线记账一起清掉。
     *
     * <p>这些值都是按当时那个实体的 {@code tickCount} 记的绝对刻，带过去就是「未来的冷却」；
     * 同一个成因的入口见 {@link #syncEpoch(Minecraft)}（它自动调用本方法）。</p>
     */
    public void resetTimers() {
        nextStartTick = 0;
        nextRetryTick = 0;
        pending.clear();
        retries.clear();
        blockedUntil.clear();
        lastWarnTick = Integer.MIN_VALUE;
        // 「刚被破坏」同样是按旧实体记的：不清掉，换实体后 age 会算出负数或误命中连锁的
        // 20 刻取根窗口，让连锁拿上一个世界的坐标当矿脉根
        lastBrokenPos = null;
        lastBrokenBlock = null;
        lastBrokenTick = 0;
        clearState();
    }
}
