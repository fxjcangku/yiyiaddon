package com.yiyiaddon.feature.mining.vein;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
 * <h2>只挖够得着的方块</h2>
 * <p>连锁队列只收<b>原版交互距离内</b>（{@code isWithinBlockInteractionRange}）的方块：
 * 够不到的方块即使连着也不会入队——入队了也发不出合法的破坏包，
 * 只会让队列卡在原地。远程矿脉交给 Baritone 走过去再挖，观感与判定都更自然；
 * 识别结果会播报出来（「识别到 N 块可挖矿脉」），括号里逐类说明没接管的原因与块数
 * （够不到 / 超出搜索距离 / 已达上限，见 {@link #scanVein}）；只扫出 1 块时不做连锁
 * （{@link #MIN_CHAIN_BLOCKS}，不值得为一块停一次 Baritone）。</p>
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

        // 2) 队列空且控制器空闲 → 本次连锁结束，把 Baritone 还回去，并清掉扫描缓存
        if (queue.isEmpty()) {
            if (!controller.isActive()) {
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
                // 「已经没了」属正常出局（不算漏挖）；「还是矿但走远了够不到」要记账，
                // 否则玩家只会看到「识别到 5 块、完成 3 块」却不知道为什么（用户 2026-09-18：连锁也没生效）
                if (wentOutOfReach(player, next)) skippedCount++;
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
        queue.clear();
        visited.clear();
        baritoneSuspended = false;
        brokenCount = 0;
        veinSize = 0;
        queuedCount = 0;
        outOfReachCount = 0;
        beyondRangeCount = 0;
        skippedCount = 0;
        hitLimit = false;
        reported = false;
    }

    private void clear() {
        if (!queue.isEmpty() || baritoneSuspended) reset();
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
                    // 够不着：不入队，但仍沿矿脉继续探索，这样「够不到几块」能统计准
                    outOfReachCount++;
                    frontier.add(neighbor);
                    continue;
                }
                if (queue.size() >= queueLimit) {
                    hitLimit = true;
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
     * <p>用原版 {@link BlockTags} 判定，不做方块 id 字符串匹配——模组矿石只要自己进了对应的
     * {@code *_ores} 标签就自动生效。</p>
     */
    private static int oreFamily(BlockState state) {
        if (state.is(BlockTags.COAL_ORES)) return 1;
        if (state.is(BlockTags.COPPER_ORES)) return 2;
        if (state.is(BlockTags.IRON_ORES)) return 3;
        if (state.is(BlockTags.GOLD_ORES)) return 4;
        if (state.is(BlockTags.REDSTONE_ORES)) return 5;
        if (state.is(BlockTags.LAPIS_ORES)) return 6;
        if (state.is(BlockTags.DIAMOND_ORES)) return 7;
        if (state.is(BlockTags.EMERALD_ORES)) return 8;
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
            // 漏挖的块如实交代（见 skippedCount）：玩家看到「识别到 5 块、完成 3 块」时能知道另外 2 块去哪了
            module.info(skippedCount > 0
                ? "§a§l✓ 连锁完成 §8▸ 本次连锁破坏 " + brokenCount + " 块§7（另有 " + skippedCount
                    + " 块挖不动或走远了够不到，已交回 Baritone 补挖）"
                : "§a§l✓ 连锁完成 §8▸ 本次连锁破坏 " + brokenCount + " 块");
        }
        brokenCount = 0;
        veinSize = 0;
        queuedCount = 0;
        outOfReachCount = 0;
        beyondRangeCount = 0;
        skippedCount = 0;
        hitLimit = false;
        if (!module.getTargetBlocks().isEmpty() && !module.getBaritone().isMiningActive()) {
            // 静默重启（用户 2026-09-18：「Baritone 已启动挖掘…狂刷屏」——连锁默认开，每挖完
            // 一条矿脉都要把 mine 拉回来一次，每次都播「已启动挖掘」就是刷屏主源）：
            // 连锁结束已有「连锁完成」播报，mine 的重启用户不需要知道
            module.getBaritone().startMining(module.getMiningTargets(), false);
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
        boolean handOver = outOfReachCount > 0 || beyondRangeCount > 0;
        if (hitLimit || handOver) {
            message.append("§7（本脉共 ").append(veinSize).append(" 块：");
            if (hitLimit) {
                message.append("已达上限 ").append(module.getVeinMaxBlocks()).append(" 块");
                if (handOver) message.append("，");
            }
            if (outOfReachCount > 0) {
                message.append(outOfReachCount).append(" 块够不到");
                if (beyondRangeCount > 0) message.append("，");
            }
            if (beyondRangeCount > 0) {
                message.append(beyondRangeCount).append(" 块超出搜索距离");
            }
            message.append("，交给 Baritone 走过去挖）");
        }
        module.info(message.toString());
    }
}
