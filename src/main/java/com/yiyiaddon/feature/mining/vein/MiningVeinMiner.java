package com.yiyiaddon.feature.mining.vein;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.feature.mining.navigation.BlockPlacer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 连锁挖矿（用户 2026-09-17 需求：「自动挖掘附近连着的矿物，搭配秒破效率拉满」）。
 *
 * <h2>怎么和「秒破」配合</h2>
 * <p>连锁本身不另开一套发包通道，而是<b>复用秒破的单槽状态机</b>
 * （{@link MiningFastBreakController}）：</p>
 * <ol>
 *   <li>玩家/Baritone 挖到第一个矿物方块时，控制器目标就是这个方块 —— 本类把它当作<b>矿脉根</b>，
 *       用 BFS 扫出与它连通的同类矿物，按距离乱序不会（队列按 BFS 顺序，近的先挖）；</li>
 *   <li>控制器每挖完一块会回到空闲态，本类立刻把队列里的下一个方块交给它
 *       （{@link MiningFastBreakController#mineRequested}），于是每一块都走完整的
 *       START →（服务端 0.7 阈值）→ STOP 发包流程，速度就是秒破的速度；</li>
 *   <li>挖完最后一块后恢复 Baritone，继续正常挖矿。</li>
 * </ol>
 *
 * <h2>为什么连锁期间必须停掉 Baritone</h2>
 * <p>服务端每个玩家只有<b>一个</b>破坏槽位（{@code destroyBlockPos / destroyProgressStartTick}）。
 * Baritone 的 MineProcess 与我们的连锁如果同时发包，两边的 START 会互相顶掉槽位，
 * 结果就是「方块怎么都挖不烂」（正是本项目秒破早期那个 bug 的同一成因）。
 * 因此本类在<b>扫描出矿脉那一刻</b>就 {@code baritone.stop()} 接管挖掘，
 * 期间状态机的 mine 自愈分支与漏捡补偿都被 {@link #isActive()} 挡掉，挖完整条脉再还给它。</p>
 *
 * <h2>只挖够得着的方块 + 尾巴收尾补挖</h2>
 * <p>连锁队列只收<b>原版交互距离内</b>（{@code isWithinBlockInteractionRange}）的方块：
 * 够不到的方块即使连着也不会入队——入队了也发不出合法的破坏包，
 * 只会让队列卡在原地。识别结果会播报出来（「识别到 N 块可挖矿脉」），括号里逐类说明没接管的原因与块数
 * （够不到 / 超出搜索距离 / 已达上限，见 {@link #scanVein}）；只扫出 1 块时不做连锁
 * （{@link #MIN_CHAIN_BLOCKS}，不值得为一块停一次 Baritone）。</p>
 *
 * <p><b>尾巴收尾补挖</b>（用户 2026-09-19：「经常漏挖，剩下一个，然后走掉，跑去挖别的，
 * 然后又跑回来再挖一次这个，真的很浪费时间」）：上面那些没接管的块过去是直接交回 Baritone 的
 * <b>全局目标池</b>，它按路径成本排序，常把这几个尾巴排到别的矿后面 —— 玩家看到的就是
 * 「剩一个 → 跑掉 → 又跑回来」。现在队列排空后由 {@link #chaseLeftovers} 自己把这批尾巴补完
 * （看得见就入队秒破，看不见就让 Baritone 单目标 {@code GoalTwoBlocks} 走过去再入队），
 * 挖干净才把 Baritone 还回去。单块 10 秒追不到、或连续 3 块放弃就整批交回（兜底与旧行为一致）。</p>
 *
 * <h2>近矿主动清扫 + 岩浆封堵（用户 2026-09-18）</h2>
 * <p><b>「旁边 3 格有矿还跑去挖远处的」「留一个不挖」</b>：连锁只认「正在挖的块」当根，
 * 矿脉够不到的尾巴交还 Baritone 后，它按自己的目标刷新慢慢磨，近矿常被远矿抢先。
 * 现在队列空闲时每秒主动扫一圈 {@link #SWEEP_RADIUS} 格内的目标矿（见
 * {@link #sweepNearbyOres}），扫到的直接入队秒破——不脉不成群的孤矿也收，
 * 停-启 Baritone 那 1 秒换的是以后不回头跑一趟。</p>
 *
 * <h2>只挖看得见的矿（用户 2026-09-19）</h2>
 * <p>用户实机：「有时候会隔着墙就开始挖我选择的矿石」——近矿清扫与脉扫描此前<b>只看距离</b>
 * （{@code isWithinBlockInteractionRange}），墙后 3 格内的目标矿会被秒破，人却过不去，
 * 于是留下墙后一堆捡不回来的掉落物，漏捡补偿再停 mine 跑去捡，来回白跑。
 * 现在两处都加「玩家看得见这一格」判据（{@link #hasClearSight}，与原版准星拾取同一档射线），
 * 看不见的矿不入队、只计入 {@link #hiddenCount} 交给 Baritone 走过去挖（它本来就要挖开通路）。</p>
 *
 * <h2>近矿主动清扫 + 岩浆封堵（用户 2026-09-18）</h2>
 * <p><b>「旁边 3 格有矿还跑去挖远处的」「留一个不挖」</b>：连锁只认「正在挖的块」当根，
 * 矿脉够不到的尾巴交还 Baritone 后，它按自己的目标刷新慢慢磨，近矿常被远矿抢先。
 * 现在队列空闲时每秒主动扫一圈 {@link #SWEEP_RADIUS} 格内的目标矿（见
 * {@link #sweepNearbyOres}），扫到的直接入队秒破——不脉不成群的孤矿也收，
 * 停-启 Baritone 那 1 秒换的是以后不回头跑一趟。</p>
 * <p><b>「老是走到岩浆旁边然后被烧」</b>：被烧不是路走进岩浆（Baritone 已把岩浆列为
 * 不可通行），而是<b>挖开贴岩浆的方块后岩浆涌进洞里</b>漫到玩家脚下。现在派发前检查
 * 目标方块 6 面有没有岩浆，有的话挖完立刻往洞里放一块搭路方块封住流入口
 * （见 {@link #tickSeal}），封完才派发下一块。</p>
 */
public final class MiningVeinMiner {

    /** 矿脉族别：不属于任何矿石族 */
    private static final int FAMILY_NONE = 0;

    /** 队列上限的硬保护：即使设置被改坏也不会一次排爆内存 */
    private static final int HARD_QUEUE_LIMIT = 512;

    /**
     * 最少连锁块数：只扫出 1 块时不做连锁。
     *
     * <p>依据实机播报（用户 2026-09-18 chatlog：19:49:34「识别到 4 块」→ 19:49:35「识别到 1 块」）：
     * 单块连锁要把 Baritone 停掉再拉回来，「连锁完成 1 块」也只是一条噪音，收益远小于打断成本。
     * 这一块交给 Baritone 按常规挖掉即可，玩家看不出差别。</p>
     */
    private static final int MIN_CHAIN_BLOCKS = 2;

    /**
     * 扫描半径（格）：只用于「把这条脉有多大数清楚」，不决定挖哪些。
     *
     * <p>只数到这么远，更远的交给 Baritone；比「连锁搜索距离」大，
     * 是为了让播报能说清「本脉还有几块没接管」，而不是只报一个 3 让玩家以为整条脉就 3 块。</p>
     */
    private static final int SCAN_RADIUS = 8;

    /**
     * 「刚被破坏的方块」还能当矿脉根的时效（刻）。
     *
     * <p>秒破对软方块是同刻完成，{@code targetPos()} 那一刻已经是空的；用这个短窗口把
     * 「刚被挖掉的那一块」接回来当根，才能扫出整条脉（用户 2026-09-18：「连锁好像没生效」）。</p>
     */
    private static final int BROKEN_ROOT_TICKS = 20;

    /**
     * 近矿主动清扫半径（格，含 Y 轴）。
     *
     * <p>用户 2026-09-18：「旁边 3 格附近有相同的明明有矿物的时候还会跑去挖远处的」。
     * 这半边圈子里的目标矿不等 Baritone 换目标，直接秒破掉——Baritone 的 mine 目标
     * 刷新有间隔，且它选目标按<b>路径成本</b>估，不按直线距离，近矿常被远矿抢先；
     * 清扫把「一定够得着的近矿」全部揽过来，剩下的才交给它。</p>
     */
    private static final int SWEEP_RADIUS = 3;

    /** 近矿清扫的采样间隔（刻）：3 秒两次全量方块读取，配合 {@link #SWEEP_COOLDOWN_TICKS} 防抖 */
    private static final int SWEEP_INTERVAL_TICKS = 20;

    /** 一轮空扫后的冷却（刻）：玩家没挪窝时附近不会有新矿，别白扫 */
    private static final int SWEEP_COOLDOWN_TICKS = 40;

    // ── 本脉收尾补挖（用户 2026-09-19：「经常漏挖，剩下一个，走掉又跑回来挖这个」） ──
    //
    // 连锁只接管「够得着 + 看得见」的块，脉尾那 1~2 块（够不到 / 隔墙看不见 / 达上限）过去是直接
    // 交回 Baritone 的全局目标池 —— 它按自己的路径成本排序，常把这几个尾巴排到别的矿后面，
    // 于是表现成「剩一个 → 跑去挖别的 → 又跑回来挖这个」，白跑一趟。
    // 现在队列排空后先自己把这批尾巴补完：看得见就入队秒破；看不见就让 Baritone 走过去
    // （单目标 {@code GoalTwoBlocks}，与种子模式同一入口），到位再入队。
    // 兜底不变：单块超过 LEFTOVER_CHASE_TIMEOUT_TICKS 够不到、或连续放弃 LEFTOVER_MAX_GIVE_UPS 块，
    // 就把剩下的整批还给 Baritone（与旧行为一致，不会卡在追补里）。

    /** 单块追补上限（刻）：10 秒还没够到 / 还没看见就放弃这一块，交回 Baritone 全局目标池 */
    private static final int LEFTOVER_CHASE_TIMEOUT_TICKS = 200;

    /** 已经走进交互距离却仍看不见时的宽限（刻）：2 秒后放弃这一块（隔在墙里的交给 Baritone 挖通路） */
    private static final int LEFTOVER_NO_SIGHT_GRACE_TICKS = 40;

    /** 追补清单上限（块）：一条脉最多记这么多尾巴，防无界 */
    private static final int LEFTOVER_LIMIT = 64;

    /** 连续放弃多少块就整批交回 Baritone：防止「一路走过去又一路放弃」把时间耗在追补上 */
    private static final int LEFTOVER_MAX_GIVE_UPS = 3;

    /**
     * 「走过去追补」时判定卡住的静止刻数（刻）：3 秒原地不动就当这条路走不通，早点放弃。
     *
     * <p>挡路的方块由秒破挖，正常停顿只有几刻；原地站满 3 秒基本就是寻路算不出路 / 被围死，
     * 与其耗满 {@link #LEFTOVER_CHASE_TIMEOUT_TICKS}（10 秒），不如早点还给 Baritone 的全局池。</p>
     */
    private static final int LEFTOVER_STUCK_TICKS = 60;

    /**
     * 岩浆封堵的等待上限（刻）。
     *
     * <p>贴岩浆的方块被挖开后，岩浆要流进那个洞还需要若干刻（主世界流体 30 刻一格）；
     * 服务端的破坏确认偶尔会迟到，超过这个时限还等不到「洞是空气」就放弃封堵，
     * 交回挖掘流程（真流进来了由状态机的岩浆逃离兜底）。</p>
     */
    private static final int SEAL_TIMEOUT_TICKS = 10;

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 待挖队列（BFS 顺序：同一层里近的先出队） */
    private final Deque<BlockPos> queue = new ArrayDeque<>();
    /** 本次连锁已见过的坐标（矿脉根 + 已入队 + 已判定不合格），避免重复扫描 */
    private final Set<Long> visited = new HashSet<>();

    /** Baritone 是否已被本类停掉（恢复时只需清标志，mine 由状态机的自愈分支重新拉起） */
    private boolean baritoneSuspended;
    /** 本次连锁已派发出去的方块数（播报统计用） */
    private int brokenCount;
    /** 本次连锁扫描出的矿脉总块数（播报统计用） */
    private int veinSize;
    /** 扫描时同属这条脉但超出交互距离、没入队的块数（播报里说明「交给 Baritone」） */
    private int outOfReachCount;
    /** 扫描时同属这条脉但超出「连锁搜索距离」、按设置没入队的块数（同样交给 Baritone） */
    private int beyondRangeCount;
    /** 扫描时同属这条脉、距离也够，但被方块挡住（看不见）而没入队的块数（用户 2026-09-19，同样交给 Baritone） */
    private int hiddenCount;
    /** 派发途中被判定「挖不动 / 走远了够不到」而丢掉的块数（播报里如实交代，不静默丢） */
    private int skippedCount;
    /** 本次扫描实际入队的块数（播报用；queue 会被逐块消费，不能用它的实时长度当统计） */
    private int queuedCount;
    /** 本次扫描是否因为达到「连锁最大方块数」而提前收手 */
    private boolean hitLimit;
    /** 上一次派发/扫描是否已经播报过（避免每 tick 刷屏） */
    private boolean reported;
    /** 「连锁开了但秒破没开」是否已提示过（一次会话只提示一次，不放进 reset 以免重复刷） */
    private boolean reportedNeedsFastBreak;

    // ── 近矿主动清扫（用户 2026-09-18：「旁边3格有矿还跑去挖远处的」「留一个不挖」） ──
    /** 空扫冷却剩余刻数（见 {@link #SWEEP_COOLDOWN_TICKS}） */
    private int sweepCooldownTicks;
    /** 待封堵的洞：刚挖开的贴岩浆方块位置，岩浆正要流进来（见 {@link #tickSeal}） */
    private BlockPos pendingSealPos;
    /** 封堵等待已过刻数（见 {@link #SEAL_TIMEOUT_TICKS}） */
    private int sealWaitTicks;
    /** 「身上没有搭路方块可封堵」是否已提示过（一次会话一次，避免每个贴岩浆方块都刷） */
    private boolean reportedNoSealBlocks;

    // ── 本脉收尾补挖的进行中状态（见字段区上文常量注释） ──
    /** 本脉没接管的尾巴（够不到 / 隔墙看不见 / 达上限），收尾时逐块走过去补挖 */
    private final Set<Long> leftovers = new HashSet<>();
    /** 正在走过去补挖的那一块（null = 没在追） */
    private BlockPos leftoverTarget;
    /** 开始追这一块时玩家所在的位置（判断「追了半天没挪窝」用） */
    private BlockPos leftoverStartPos;
    /** 当前这一块已经追了多少刻 */
    private int leftoverTicks;
    /** 已经走进交互距离、却仍看不见的连续刻数（隔离在墙里的块要早点放弃，别站着耗） */
    private int leftoverNoSightTicks;
    /** 连续放弃的尾巴块数：满 {@link #LEFTOVER_MAX_GIVE_UPS} 即整批交回 Baritone */
    private int leftoverGiveUps;
    /** 本次收尾最终交回 Baritone 的尾巴块数（播报用，交回后 leftovers 已清空） */
    private int leftoverHandedOver;

    public MiningVeinMiner(AutoMinerModule module) {
        this.module = module;
    }

    /** 连锁是否正在进行（排队中或已接管 Baritone）；状态机用它来避让 */
    public boolean isActive() {
        return baritoneSuspended || !queue.isEmpty();
    }

    /** 待挖队列长度（诊断 / 播报用） */
    public int queueSize() {
        return queue.size();
    }

    /**
     * 每客户端刻调用一次（由 {@code AutoMinerModule#onTick} 在秒破推进之后驱动）。
     *
     * @param miningState 状态机当前是否处于「采掘中」；非采掘态一律不连锁
     */
    public void tick(AutoMinerModule module, boolean miningState) {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null || !miningState || !module.isVeinMinerEnabled()) {
            clear();
            return;
        }
        // 连锁复用秒破的发包通道：秒破没开就没通道可用。这里必须明确提示一次，
        // 否则「连锁默认开、秒破默认关」会让玩家以为连锁坏了（用户 2026-09-17）
        if (!module.getFastBreak()) {
            if (!reportedNeedsFastBreak) {
                reportedNeedsFastBreak = true;
                module.warning("§e⚠ 连锁挖矿依赖秒破 §8▸ 请先把「秒破 ▸ 快速破坏」打开");
            }
            clear();
            return;
        }

        MiningFastBreakController controller = MiningFastBreakController.instance();

        // 0) 岩浆封堵待办最优先（用户 2026-09-18：「老是走到岩浆旁边然后被烧」）：
        //    贴岩浆的方块刚被挖开，那个洞就是岩浆的流入口——趁流体还没漫过来放一块搭路方块堵上，
        //    再继续派发下一块。堵洞期间不扫根、不派发（超时由 tickSeal 内部兜底清掉待办，不会卡死）
        if (pendingSealPos != null) {
            tickSeal(player, controller);
            return;
        }

        // 1) 矿脉根：优先取控制器当前正在挖的方块；它已经挖掉（软方块同刻完成，targetPos 已清空）时，
        //    退回到「刚被破坏的方块」——这是用户 2026-09-18「连锁好像没生效，偶尔一个一个挖」的根因：
        //    旧实现只认 targetPos，秒破把矿石一瞬间挖掉后那一刻我们什么都没扫到，于是这一块就不连锁了
        BlockPos root = null;
        BlockState rootState = null;
        // 目标方块集合一次取好往下传：getTargetBlocks() 内部要查方块的深层/浅层变体，
        // 放进每邻居判定的热路径会变成每 tick 上千次注册表查询
        Set<Block> targets = new HashSet<>(module.getTargetBlocks());
        for (BlockPos candidate : candidateRoots(controller)) {
            if (visited.contains(candidate.asLong())) continue;
            BlockState state = rootState(controller, candidate);
            if (state == null || oreFamily(state) == FAMILY_NONE) continue;
            // 矿脉根必须是用户选的目标矿物（用户 2026-09-18 严重 bug：「选了青金石，却把煤炭
            // 和铜矿一起挖了」）。成因：秒破会接管原版/Baritone 发起的**任何**方块破坏——Baritone
            // 走路时为通行会挖挡路方块，那块要是恰好是矿石，旧实现直接拿它的族别去连锁，
            // 于是把用户没选的矿整条挖掉。族别判定只保证「同类」，不保证「是你要的矿」。
            if (!isMiningTarget(state, targets)) continue;
            root = candidate;
            rootState = state;
            break;
        }
        if (root != null) {
            scanVein(player, root, rootState, targets);
            if (queue.size() >= MIN_CHAIN_BLOCKS) {
                suspendBaritone();
                reportVeinFound();
            } else {
                // 只扫出 0~1 块：不值得为它停一次 Baritone（见 MIN_CHAIN_BLOCKS）。
                // 注意保留 visited 里的根，避免同一块下一 tick 又被当根重扫
                queue.clear();
            }
        }

        // 2) 队列空且控制器空闲 → ① 先补完本脉尾巴（够不到 / 隔墙看不见的那几块）
        //    ② 再扫近矿 ③ 都没有才把 Baritone 还回去
        if (queue.isEmpty()) {
            if (!controller.isActive()) {
                if (chaseLeftovers(player)) return;
                if (sweepNearbyOres(player)) {
                    // 扫到了：近矿自己挖（停 Baritone 抢单槽），下一刻走派发分支。
                    // 单块也挖：这 1 秒的停-启换来的是「不必以后回头跑一趟」，远比 Baritone
                    // 按 2 秒目标刷新慢慢磨近矿划算
                    suspendBaritone();
                    return;
                }
                resumeBaritone();
                visited.clear();
            }
            return;
        }

        // 3) 控制器忙（正在发包挖上一块）→ 等它，不打断单槽
        if (controller.isActive()) return;

        // 4) 派发下一个：跳过已经不成立的坐标（被别的模块挖掉 / 变成别的方块 / 队友破坏）
        //    注意用 peek + 成功后才 poll：间隔冷却导致这次派不出去时必须把方块留在队里，
        //    否则每块都会被「冷却那一 tick」吃掉一块，连锁会漏挖
        while (!queue.isEmpty()) {
            BlockPos next = queue.peek();
            if (!isValidTarget(player, next)) {
                // 「已经没了」属正常出局（不算漏挖）；「还是矿但走远了够不到」记进「本脉尾巴」，
                // 由收尾补挖走回去挖掉（用户 2026-09-19：漏挖的块不该变成以后的回头路）
                if (wentOutOfReach(player, next)) rememberLeftover(next);
                queue.poll();
                continue;
            }
            if (controller.isBlocked(player.tickCount, next)) {
                queue.poll(); // 已被判定挖不动：丢弃换下一块，并记进「漏挖」如实交代
                skippedCount++;
                continue;
            }
            if (!controller.mineRequested(mc, module, next, Direction.UP)) {
                return; // 多半是方块间隔冷却中，保留该块，下刻再派
            }
            queue.poll();
            brokenCount++;
            // 贴岩浆的方块挖开就是流入口：标记「挖完要封堵」，下一刻 tickSeal 接手
            if (hasAdjacentLava(next)) {
                pendingSealPos = next;
                sealWaitTicks = 0;
            }
            return;
        }
        // 队列里的坐标全部失效：本刻结束，下一 tick 走「队列空」分支收尾
    }

    /**
     * 本刻可能的矿脉根，按优先级排列：正在被挖的方块 → 刚被破坏的方块（{@link #BROKEN_ROOT_TICKS} 刻内）。
     *
     * <p>为什么必须两条路：秒破对软矿石是同刻完成（START/STOP/破坏都在一 tick 内），
     * 我们这一帧拿到的 {@code targetPos()} 已经是 {@code null}，只认它就会漏掉整条矿脉——
     * 表现为「偶尔连锁、偶尔一块一块挖」。</p>
     */
    private List<BlockPos> candidateRoots(MiningFastBreakController controller) {
        List<BlockPos> candidates = new ArrayList<>(2);
        BlockPos target = controller.targetPos();
        if (target != null) candidates.add(target);

        BlockPos broken = controller.lastBrokenPos();
        LocalPlayer player = mc.player;
        if (broken != null && controller.lastBrokenBlock() != null && player != null) {
            int age = player.tickCount - controller.lastBrokenTick();
            if (age >= 0 && age <= BROKEN_ROOT_TICKS && !broken.equals(target)) candidates.add(broken);
        }
        return candidates;
    }

    /**
     * 矿脉根这一刻的方块状态。
     *
     * <p>根已经被挖掉时（空气）不能直接读世界，改用控制器记下的「刚被破坏的方块类型」造状态，
     * 否则族别判定会得到 {@code FAMILY_NONE}，整条脉都连不起来。</p>
     */
    private BlockState rootState(MiningFastBreakController controller, BlockPos root) {
        if (mc.level == null) return null;
        BlockPos broken = controller.lastBrokenPos();
        Block lastBroken = controller.lastBrokenBlock();
        if (lastBroken != null && broken != null && broken.equals(root)) {
            BlockState live = mc.level.getBlockState(root);
            return live.isAir() ? lastBroken.defaultBlockState() : live;
        }
        return mc.level.getBlockState(root);
    }

    /** 模块关闭 / 离开挖矿态 / 换世界时清空连锁状态（Baritone 交给状态机自己恢复） */
    public void reset() {
        // 追补用的单目标寻路要显式收掉：离开挖矿态后没人再推进它（只有真在追时才发一次 stop）
        if (leftoverTarget != null) {
            leftoverTarget = null;
            module.getBaritone().stop();
        }
        leftoverStartPos = null;
        queue.clear();
        visited.clear();
        baritoneSuspended = false;
        brokenCount = 0;
        veinSize = 0;
        queuedCount = 0;
        outOfReachCount = 0;
        beyondRangeCount = 0;
        hiddenCount = 0;
        skippedCount = 0;
        hitLimit = false;
        reported = false;
        sweepCooldownTicks = 0;
        pendingSealPos = null;
        sealWaitTicks = 0;
        // 本脉尾巴的追补状态：离开挖矿态的这一刻连清单一起丢掉（剩下的交回 Baritone 全局目标池）
        leftovers.clear();
        leftoverTarget = null;
        leftoverTicks = 0;
        leftoverNoSightTicks = 0;
        leftoverGiveUps = 0;
        leftoverHandedOver = 0;
    }

    private void clear() {
        if (!queue.isEmpty() || baritoneSuspended) reset();
    }

    // ── 近矿主动清扫 + 岩浆封堵（用户 2026-09-18） ───────────────────────────

    /**
     * 近矿主动清扫：把 {@link #SWEEP_RADIUS} 格内、交互距离可达的目标矿全部入队。
     *
     * <p>治两类病：<b>「旁边 3 格有矿却跑去挖远处的」</b>（Baritone 的 mine 目标按路径成本
     * 选、刷新还有间隔，近矿常被远矿抢先）与<b>「留一个不挖」</b>（连锁够不到的块交还
     * Baritone 后被目标切换遗漏）。清扫半径 ⊂ 交互距离，扫到的块一定发得出合法破坏包。</p>
     *
     * <p>这批块不成脉也挖（单块照收）：停-启 Baritone 约 1 秒，换来的是省掉以后的回头路。
     * 静默执行不播报——挖矿是常态动作，每秒一条「清扫到 N 块」就是新刷屏源。</p>
     *
     * @return true 表示扫到并已入队（调用方随后停 Baritone 抢单槽）
     */
    private boolean sweepNearbyOres(LocalPlayer player) {
        if (mc.level == null || mc.player == null) return false;
        if (sweepCooldownTicks > 0) {
            sweepCooldownTicks--;
            return false;
        }
        if (player.tickCount % SWEEP_INTERVAL_TICKS != 0) return false;
        Set<Block> targets = new HashSet<>(module.getTargetBlocks());
        if (targets.isEmpty()) return false;

        BlockPos center = player.blockPosition();
        int queueLimit = Math.min(Math.max(1, module.getVeinMaxBlocks()), HARD_QUEUE_LIMIT);
        for (int dx = -SWEEP_RADIUS; dx <= SWEEP_RADIUS; dx++) {
            for (int dy = -SWEEP_RADIUS; dy <= SWEEP_RADIUS; dy++) {
                for (int dz = -SWEEP_RADIUS; dz <= SWEEP_RADIUS; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (!isMiningTarget(mc.level.getBlockState(pos), targets)) continue;
                    if (!player.isWithinBlockInteractionRange(pos, 1.0)) continue;
                    // 隔墙不挖（用户 2026-09-19）：看不见的目标矿不秒破——挖了也过不去，
                    // 只会留下墙后一堆捡不回来的掉落物；交给 Baritone 走过去挖（它本来就要挖开通路）
                    if (!hasClearSight(player, pos)) continue;
                    if (queue.contains(pos)) continue;
                    queue.add(pos);
                    if (queue.size() >= queueLimit) return true;
                }
            }
        }
        if (queue.isEmpty()) {
            sweepCooldownTicks = SWEEP_COOLDOWN_TICKS;
            return false;
        }
        return true;
    }

    // ── 本脉尾巴收尾补挖（用户 2026-09-19：「经常漏挖剩下一个，走掉又跑回来挖这个」） ────

    /** 记一块「本脉尾巴」，收尾时走过去补挖（超过 {@link #LEFTOVER_LIMIT} 不记，退回旧行为交给 Baritone） */
    private void rememberLeftover(BlockPos pos) {
        if (leftovers.size() >= LEFTOVER_LIMIT) return;
        leftovers.add(pos.asLong());
    }

    /**
     * 收尾补挖：把本脉没接管的尾巴（够不到 / 隔墙看不见 / 达上限）自己走过去挖掉。
     *
     * <p>调用点在 {@code tick} 的「队列空 + 控制器空闲」分支，排在近矿清扫之前 ——
     * 先把刚扫出来的这条脉挖干净，再顺手扫近矿，最后才把 Baritone 还回去。
     * 还回去等于交回它的<b>全局目标池</b>：它按路径成本排序，常把这几个尾巴排到别的矿后面，
     * 那正是玩家看到的「剩一个 → 跑去挖别的 → 又跑回来挖这个」。</p>
     *
     * @return true 表示本刻仍在收尾（调用方直接结束本 tick，不要把 Baritone 还回去）
     */
    private boolean chaseLeftovers(LocalPlayer player) {
        if (leftovers.isEmpty() || mc.level == null) return finishChase();
        Set<Block> targets = new HashSet<>(module.getTargetBlocks());
        if (targets.isEmpty()) return finishChase();

        // 已经在追的那一块不作数了（被顺路挖掉 / 变成别的方块）：清掉追补状态，下一步换目标
        if (leftoverTarget != null && !isStillTarget(leftoverTarget, targets)) {
            leftoverTarget = null;
            leftoverStartPos = null;
            leftoverTicks = 0;
            leftoverNoSightTicks = 0;
        }
        leftovers.removeIf(key -> !isStillTarget(BlockPos.of(key), targets));

        // 放弃额度用尽 / 清单已空：整批交回 Baritone（旧行为兜底，不把时间耗在追补上）
        if (leftoverGiveUps >= LEFTOVER_MAX_GIVE_UPS) return finishChase();
        if (leftovers.isEmpty()) return finishChase();

        BlockPos target = nearestLeftover(player);
        if (target == null) return finishChase();

        // ① 到位且看得见 → 入队，下一刻由派发分支用秒破挖掉
        if (player.isWithinBlockInteractionRange(target, 1.0) && hasClearSight(player, target)) {
            leftovers.remove(target.asLong());
            if (target.equals(leftoverTarget)) {
                leftoverTarget = null;
                leftoverStartPos = null;
                module.getBaritone().stop(); // 撤掉「走过去」用的单目标寻路
                // 上一块收尾完成，放弃额度也还回去：这次是拿到了，不是够不到
                leftoverGiveUps = 0;
            }
            if (!queue.contains(target)) queue.add(target);
            // 马上要用秒破发包：Baritone 的 mine 必须停着，否则两边抢服务端唯一的破坏槽位
            suspendBaritone();
            leftoverTicks = 0;
            leftoverNoSightTicks = 0;
            return true;
        }

        // ② 正在追这一块：累计刻数，并检查三条放弃判据（追太久 / 到位了还看不见 / 原地卡住）
        if (target.equals(leftoverTarget)) {
            leftoverTicks++;
            leftoverNoSightTicks = player.isWithinBlockInteractionRange(target, 1.0)
                ? leftoverNoSightTicks + 1
                : 0;
            boolean stuck = leftoverTicks > LEFTOVER_STUCK_TICKS
                && leftoverStartPos != null
                && player.blockPosition().distSqr(leftoverStartPos) < 1.0;
            if (leftoverTicks > LEFTOVER_CHASE_TIMEOUT_TICKS
                || leftoverNoSightTicks > LEFTOVER_NO_SIGHT_GRACE_TICKS
                || stuck) {
                giveUpLeftover(target);
                return !leftovers.isEmpty();
            }
            return true;
        }

        // ③ 换目标：先停掉 Baritone 的 mine（两边同时发包会互相顶掉服务端唯一的破坏槽位），
        //    再让它以单目标 GoalTwoBlocks 走过去 —— 与种子模式走同一条入口，不另造寻路
        suspendBaritone();
        leftoverTarget = target;
        leftoverStartPos = player.blockPosition();
        leftoverTicks = 0;
        leftoverNoSightTicks = 0;
        if (!module.getBaritone().pathToOre(target)) {
            giveUpLeftover(target);
            return !leftovers.isEmpty();
        }
        return true;
    }

    /** 放弃一块尾巴：出清单 + 清追补状态 + 记账（连续放弃满额后由 {@link #finishChase()} 整批交回） */
    private void giveUpLeftover(BlockPos target) {
        leftovers.remove(target.asLong());
        if (target.equals(leftoverTarget)) {
            leftoverTarget = null;
            leftoverStartPos = null;
            module.getBaritone().stop();
        }
        leftoverGiveUps++;
        leftoverHandedOver++;
        leftoverTicks = 0;
        leftoverNoSightTicks = 0;
    }

    /**
     * 收尾结束：清干净追补状态与清单，并记下这次最终交回 Baritone 的块数（播报用）。
     *
     * @return 恒为 false —— 调用方据此继续走「近矿清扫 → 还 Baritone」
     */
    private boolean finishChase() {
        if (leftoverTarget != null) {
            leftoverTarget = null;
            leftoverStartPos = null;
            module.getBaritone().stop();
        }
        leftoverHandedOver += leftovers.size();
        leftovers.clear();
        leftoverTicks = 0;
        leftoverNoSightTicks = 0;
        leftoverGiveUps = 0;
        return false;
    }

    /** 离玩家最近的一块尾巴；清单为空返回 null */
    private BlockPos nearestLeftover(LocalPlayer player) {
        BlockPos playerPos = player.blockPosition();
        BlockPos best = null;
        double bestSqr = Double.MAX_VALUE;
        for (long key : leftovers) {
            BlockPos pos = BlockPos.of(key);
            double distSqr = playerPos.distSqr(pos);
            if (distSqr < bestSqr) {
                bestSqr = distSqr;
                best = pos;
            }
        }
        return best;
    }

    /** 该坐标是否还是「可挖的选中目标矿」（空气 / 换成别的方块 / 不可破坏都算没了） */
    private boolean isStillTarget(BlockPos pos, Set<Block> targets) {
        if (mc.level == null) return false;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) return false;
        return isMiningTarget(state, targets);
    }

    /**
     * 岩浆封堵推进：等洞出现（服务端确认破坏）→ 放搭路方块堵上 → 还原快捷栏。
     *
     * <p>三种出局：洞一直是岩浆/不是空气（流进来了，封不住，交给状态机的岩浆逃离兜底）、
     * 超过 {@link #SEAL_TIMEOUT_TICKS} 刻没等到洞、身上没有可放的方块（提示一次后放弃）。
     * 出局只清待办，不中断整条队列。</p>
     */
    private void tickSeal(LocalPlayer player, MiningFastBreakController controller) {
        BlockPos hole = pendingSealPos;
        if (hole == null || mc.level == null || ++sealWaitTicks > SEAL_TIMEOUT_TICKS) {
            pendingSealPos = null;
            return;
        }
        // 这块还在挖（破坏确认没到）：继续等
        if (controller.isActive()) return;
        BlockState state = mc.level.getBlockState(hole);
        if (!state.isAir()) {
            // 不是空气：要么服务端没认这笔破坏（重挖交给 Baritone），要么岩浆已经流满
            pendingSealPos = null;
            return;
        }
        trySealHole(player, hole);
    }

    /** 往刚挖开的洞里放一块搭路方块（放置链路见 {@link BlockPlacer#placeAt}） */
    private void trySealHole(LocalPlayer player, BlockPos hole) {
        boolean placed = BlockPlacer.placeAt(mc, player, module.settings().placeBlocks, hole);
        if (!placed && !reportedNoSealBlocks
            && !BlockPlacer.hasPlaceBlock(player, module.settings().placeBlocks)) {
            reportedNoSealBlocks = true;
            module.warning("§e⚠ 贴岩浆矿物挖开后无法封堵 §8▸ 搭路方块白名单里的一种都没带，建议带上圆石");
        }
        // 成功与否都清待办：失败的这一格由后续挖掘/岩浆垫脚兜底，不重试（重试会在同一格打转）
        pendingSealPos = null;
    }

    /**
     * 玩家是否<b>看得见</b>这一格（不隔墙挖的判据，用户 2026-09-19）。
     *
     * <p>做法：从眼睛朝该格中心打一条射线，第一个命中的就是这一格（或整段没命中任何方块）即算看得见；
     * 先撞到别的方块（墙）就是看不见。</p>
     *
     * <p><b>只按实心碰撞体判遮挡</b>（{@code ClipContext.Block.COLLIDER}）。判据的本意是「不隔墙挖」，
     * 而 {@code OUTLINE} 走的是方块<b>轮廓形状</b>，会把<b>不挡人的装饰方块</b>也算成遮挡：
     * 幽匿脉络 / 藤蔓 / 草丛 / 火把 / 雪片这类没有碰撞体的方块会凭空把目标矿判成「看不见」。
     * 用户 2026-09-19 实机：「有框、选择到了，就是被幽冥脉络卡住了」——矿区到处是幽匿脉络，
     * 于是整片矿被连锁反复跳过，只剩 Baritone 慢慢磨，表现就是「这块挖不了、卡住」。
     * 实心方块（石头 / 泥土 / 玻璃…）照旧挡视线，「不隔墙挖」这条没被放松。</p>
     *
     * <p>流体不挡视线（{@code Fluid.NONE}）：挖开贴岩浆的矿时，眼睛到目标那一段常泡在岩浆里，
     * 按流体挡视线判会一块都挖不了。</p>
     */
    private boolean hasClearSight(LocalPlayer player, BlockPos pos) {
        if (mc.level == null) return false;
        Vec3 eye = player.getEyePosition();
        BlockHitResult hit = mc.level.clip(new ClipContext(eye, Vec3.atCenterOf(pos),
            ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(pos);
    }

    /** 该方块的 6 个面里有没有岩浆（源或流动岩浆都算——挖开后都会往这个洞里流） */
    private boolean hasAdjacentLava(BlockPos pos) {
        if (mc.level == null) return false;
        for (Direction dir : Direction.values()) {
            if (mc.level.getBlockState(pos.relative(dir)).getBlock() == Blocks.LAVA) return true;
        }
        return false;
    }

    // ── 矿脉扫描 ────────────────────────────────────────────────────────────

    /**
     * BFS 扫出与 {@code origin} 连通、同类、且够得着的矿物入队，并<b>把「没入队的」逐类数清</b>。
     *
     * <p>用户 2026-09-18：「连锁也没生效，还是一块一块挖」——实机 chatlog 里每次都是
     * 「识别到 3 块 / 4 块 / 1 块」，也就是一条脉只接管了三四之一。旧实现把两类块静默丢了：
     * 超出交互距离的（够不到，交 Baritone）与超出「连锁搜索距离」的（按设置不连），
     * 而且后者连数都没数——玩家只看到一个 3，自然以为连锁没生效。
     * 现在 BFS 沿矿脉继续走（{@link #SCAN_RADIUS} 内），逐类统计：</p>
     * <ul>
     *   <li>{@link #queuedCount} 入队（本模块自己秒破掉）；</li>
     *   <li>{@link #outOfReachCount} 够不到 → 交给 Baritone 走过去挖；</li>
     *   <li>{@link #beyondRangeCount} 超出连锁搜索距离 → 同样交给 Baritone；</li>
     *   <li>{@link #hitLimit} 已达「连锁最大方块数」上限 → 剩下的也交给 Baritone。</li>
     * </ul>
     *
     * @param origin 矿脉根（正在被挖的那一块，不入队）
     */
    private void scanVein(LocalPlayer player, BlockPos origin, BlockState originState, Set<Block> targets) {
        visited.add(origin.asLong());
        brokenCount = 0;
        veinSize = 1; // 矿脉根自己也算这条脉的一块
        queuedCount = 0;
        outOfReachCount = 0;
        beyondRangeCount = 0;
        hiddenCount = 0;
        skippedCount = 0;
        hitLimit = false;
        reported = false;

        int family = oreFamily(originState);
        int queueLimit = Math.min(Math.max(1, module.getVeinMaxBlocks()), HARD_QUEUE_LIMIT);
        double rangeSqr = (double) Math.max(1, module.getVeinRange()) * Math.max(1, module.getVeinRange());
        double scanSqr = (double) SCAN_RADIUS * SCAN_RADIUS;
        boolean diagonals = module.getVeinDiagonal();

        Deque<BlockPos> frontier = new ArrayDeque<>();
        frontier.add(origin);
        int queueBefore = queue.size();

        while (!frontier.isEmpty()) {
            BlockPos current = frontier.poll();
            for (BlockPos neighbor : neighbors(current, diagonals)) {
                // visited 同时承担「已判定」语义：超出距离/不是矿石/够不到的格子也标记，
                // 避免同一个格子被从不同路径反复判定
                if (!visited.add(neighbor.asLong())) continue;
                if (origin.distSqr(neighbor) > scanSqr) continue;
                BlockState state = mc.level.getBlockState(neighbor);
                if (!sameFamily(state, family, targets)) continue;
                veinSize++;
                if (origin.distSqr(neighbor) > rangeSqr) {
                    // 超出「连锁搜索距离」：按设置不连，但仍沿脉继续走，把它数出来
                    beyondRangeCount++;
                    frontier.add(neighbor);
                    continue;
                }
                if (!player.isWithinBlockInteractionRange(neighbor, 1.0)) {
                    // 够不着：不入队，但仍沿矿脉继续探索，这样「够不到几块」能统计准；
                    // 同时记进「本脉尾巴」——收尾时会自己走过去挖掉（用户 2026-09-19）
                    outOfReachCount++;
                    rememberLeftover(neighbor);
                    frontier.add(neighbor);
                    continue;
                }
                if (!hasClearSight(player, neighbor)) {
                    // 隔着方块看不见（用户 2026-09-19）：同样不入队、如实计数，记进尾巴等收尾补挖
                    hiddenCount++;
                    rememberLeftover(neighbor);
                    frontier.add(neighbor);
                    continue;
                }
                if (queue.size() >= queueLimit) {
                    hitLimit = true;
                    rememberLeftover(neighbor); // 达上限没接管的也算尾巴
                    frontier.add(neighbor); // 继续数剩余部分，只为播报说清「本脉还有多少」
                    continue;
                }
                queue.add(neighbor);
                frontier.add(neighbor);
            }
        }
        queuedCount = queue.size() - queueBefore;
    }

    /** 邻接偏移：{@code diagonals} 为 true 时取 26 邻域（斜向连着的矿也算同一脉），否则只取 6 个面 */
    private Set<BlockPos> neighbors(BlockPos pos, boolean diagonals) {
        Set<BlockPos> result = new HashSet<>(diagonals ? 26 : 6);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    if (!diagonals && (Math.abs(dx) + Math.abs(dy) + Math.abs(dz)) != 1) continue;
                    result.add(pos.offset(dx, dy, dz));
                }
            }
        }
        return result;
    }

    /** 该方块是否还是可挖的目标（空气/已变方块/够不到都算失效） */
    private boolean isValidTarget(LocalPlayer player, BlockPos pos) {
        if (mc.level == null) return false;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) return false;
        return player.isWithinBlockInteractionRange(pos, 1.0);
    }

    /** 该方块是否「还在、但已经够不到」——用来把「走远了漏掉的矿」和「已经被挖掉的矿」区分开 */
    private boolean wentOutOfReach(LocalPlayer player, BlockPos pos) {
        if (mc.level == null) return false;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir() || state.getBlock().defaultDestroyTime() < 0.0F) return false;
        return !player.isWithinBlockInteractionRange(pos, 1.0);
    }

    // ── 矿石族别 ────────────────────────────────────────────────────────────

    /**
     * 矿石族别：同一族的矿石算「同类」（因此深板岩钻石矿与原版钻石矿、深层铁矿与铁矿都能连）。
     *
     * <p>用原版 {@code *_ores} 标签判定，不做方块 id 字符串匹配——模组矿石只要自己进了对应的
     * {@code *_ores} 标签就自动生效。</p>
     *
     * <p><b>26.2 口径</b>：煤 / 红石 / 青金石 / 钻石 / 绿宝石这五个标签从 {@link BlockTags}
     * 移到了 {@code BlockItemTags}，且那边是 {@code BlockItemTagId}（方块侧 + 物品侧成对），
     * 取 {@code block()} 拿回方块侧的 {@code TagKey}，与旧的 {@code BlockTags.X} 是同一个标签。
     * 铜 / 铁 / 金三个仍留在 {@link BlockTags}。</p>
     */
    private static int oreFamily(BlockState state) {
        if (state.is(BlockItemTags.COAL_ORES.block())) return 1;
        if (state.is(BlockTags.COPPER_ORES)) return 2;
        if (state.is(BlockTags.IRON_ORES)) return 3;
        if (state.is(BlockTags.GOLD_ORES)) return 4;
        if (state.is(BlockItemTags.REDSTONE_ORES.block())) return 5;
        if (state.is(BlockItemTags.LAPIS_ORES.block())) return 6;
        if (state.is(BlockItemTags.DIAMOND_ORES.block())) return 7;
        if (state.is(BlockItemTags.EMERALD_ORES.block())) return 8;
        // 下界石英没有对应的 *_ores 标签（本版本 BlockTags 无 QUARTZ_ORES），直接按方块判
        if (state.is(Blocks.NETHER_QUARTZ_ORE)) return 9;
        if (state.is(Blocks.ANCIENT_DEBRIS)) return 10;
        return FAMILY_NONE;
    }

    /** 是否与矿脉根同类；关闭「仅连锁同类矿物」后，任何矿石方块都算同类 */
    private boolean sameFamily(BlockState state, int family, Set<Block> targets) {
        if (!isMiningTarget(state, targets)) return false;
        int other = oreFamily(state);
        if (other == FAMILY_NONE) return false;
        return !module.getVeinFamilyOnly() || other == family;
    }

    /**
     * 该方块是否属于用户选定的采集目标。
     *
     * <p>连锁的第一道闸门（第二道是族别）：连「仅连锁同类矿物」关掉时也不越界——关它的本意是
     * 「让矿石连成一片一起挖」，不等于「用户选的矿之外的矿石也照挖」。目标列表由目标选择设置给出
     * （青金石 → 青金石矿石 + 深层青金石矿石，见 {@code AutoMinerModule#getTargetBlocks}），
     * Baritone 的 mine 目标用的也是同一份。</p>
     *
     * @param targets 本轮扫描的目标方块集合（调用方一次取好，避免热路径重复查注册表）
     */
    private static boolean isMiningTarget(BlockState state, Set<Block> targets) {
        return targets.contains(state.getBlock());
    }

    // ── Baritone 让位与恢复 ─────────────────────────────────────────────────

    /**
     * 连锁期间停掉 Baritone：服务端每个玩家只有一个破坏槽位，
     * 两边同时发包会互相顶掉槽位（方块永远挖不烂）。
     */
    private void suspendBaritone() {
        if (baritoneSuspended) return;
        baritoneSuspended = true;
        module.getBaritone().stop();
    }

    /**
     * 连锁结束：清标志并把 mine 拉回来。
     *
     * <p>这里主动重启一次 mine（而不是全靠状态机的「mine 退出 → 重启」自愈）：
     * 自愈分支要 {@code stateTick > 120} 才开始工作，进挖矿态头 6 秒内挖到矿脉时
     * 全靠自愈会让玩家干站到 120 刻。重启后 state 机上看到的仍是
     * {@code isMiningActive = true}，自愈不会重复启动；万一这次启动被 Baritone 立刻取消
     * （附近没矿），自愈照常接管并走它自己的「3 次 → RTP 换区」兜底。</p>
     */
    private void resumeBaritone() {
        if (!baritoneSuspended) return;
        baritoneSuspended = false;
        if (brokenCount > 0) {
            // 没补下来的块如实交代：skippedCount = 派发时判定「挖不动」丢掉的，
            // leftoverHandedOver = 收尾追补时够不到 / 看不见而放弃的（见 chaseLeftovers）
            int handedOver = skippedCount + leftoverHandedOver;
            module.info(handedOver > 0
                ? "§a§l✓ 连锁完成 §8▸ 本次连锁破坏 " + brokenCount + " 块§7（另有 " + handedOver
                    + " 块挖不动或够不到，已交回 Baritone 补挖）"
                : "§a§l✓ 连锁完成 §8▸ 本次连锁破坏 " + brokenCount + " 块");
        }
        brokenCount = 0;
        veinSize = 0;
        queuedCount = 0;
        outOfReachCount = 0;
        beyondRangeCount = 0;
        hiddenCount = 0;
        skippedCount = 0;
        leftoverHandedOver = 0;
        hitLimit = false;
        if (!module.getTargetBlocks().isEmpty() && !module.getBaritone().isMiningActive()) {
            // 静默重启（用户 2026-09-18：「Baritone 已启动挖掘…狂刷屏」——连锁默认开，每挖完
            // 一条矿脉都要把 mine 拉回来一次，每次都播「已启动挖掘」就是刷屏主源）：
            // 连锁结束已有「连锁完成」播报，mine 的重启用户不需要知道
            // 235：改走目标提供者 —— 普通模式与旧 startMining(..., false) 等价；
            // 种子模式在这里挑下一颗精确坐标并寻路（绝不允许退回按矿物类型 mine）
            module.issueMiningTargets(false);
        }
    }

    /**
     * 播报「识别到 N 块可挖矿脉，已接管挖掘」。
     *
     * <p>括号里如实交代这条脉还有多少块没接管（够不到 / 超出搜索距离 / 已达上限）——
     * 这是「连锁没生效」的关键：整条脉 12 块只接管 3 块时，玩家需要知道那 9 块去哪了，
     * 而不是以为连锁坏了（用户 2026-09-18）。</p>
     */
    private void reportVeinFound() {
        if (reported) return;
        reported = true;
        // 无符号前缀的信息行（用户 2026-09-18：「■ 不要这个符号，想一个合适的，但不能是 emoji」
        // —— 对齐状态机 §b 类播报的既定样式：信息色直接起头，分隔用 ▸）
        StringBuilder message = new StringBuilder("§b连锁挖矿 ▸ 识别到 ")
            .append(queuedCount).append(" 块可挖矿脉，已接管挖掘");
        // 没接管的逐类如实交代（用户 2026-09-18：「连锁没生效」时玩家要能看出那几块去哪了）。
        // 「已达上限」与三类没入队的块合并成一句，逗号拼接，末尾统一说明都交给了 Baritone
        List<String> handOver = new ArrayList<>(4);
        if (hitLimit) handOver.add("已达上限 " + module.getVeinMaxBlocks() + " 块");
        if (outOfReachCount > 0) handOver.add(outOfReachCount + " 块够不到");
        if (beyondRangeCount > 0) handOver.add(beyondRangeCount + " 块超出搜索距离");
        if (hiddenCount > 0) handOver.add(hiddenCount + " 块隔着方块看不见");
        if (!handOver.isEmpty()) {
            message.append("§7（本脉共 ").append(veinSize).append(" 块：")
                .append(String.join("，", handOver))
                .append("，交给 Baritone 走过去挖）");
        }
        module.info(message.toString());
    }
}
