package com.yiyiaddon.feature.mining.navigation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.goals.GoalTwoBlocks;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fsm.MinerState;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Baritone 寻路中间件
 * 
 * 功能：
 * · 封装 BaritoneAPI 的 #mine 调用（普通模式）
 * · 种子模式提供逐块寻路接口 pathToOre（由 MinerFSM 采集循环驱动）
 * · 寻路异常处理（找不到目标、原地滞留）
 * · 地形防卡死心跳（3分钟位移<5格判定卡死）
 *
 * <p>对应旧项目 {@code mining/navigation/BaritoneExecutor.java}（337 行）；
 * 方法名与落点逐条对齐，Baritone 设置的读写走本项目既有入口
 * （{@code BaritoneAPI.getSettings()} 的强类型字段，与 {@code FarmNav} / {@code BaritoneSettingsPage} 同一入口）。</p>
 */
public final class MiningPathing {

    private final AutoMinerModule module;
    private final Minecraft mc;

    private BlockPos lastPos = BlockPos.ZERO;
    private int stuckTicks = 0;
    private int lastCheckTick = 0;

    private static final int STUCK_CHECK_INTERVAL = 3600; // 3分钟
    private static final int STUCK_DISTANCE_THRESHOLD = 5; // 5格

    // ── Baritone 聊天输出接管 ──────────────────────────────────────────────
    // 用户 2026-09-18：「我的自动挖矿模块运行的时候能不能不出现男中音的播报，太刷屏了，
    // 取而代之的是我的自动挖矿播报，显示取消的原因要告诉用户」。

    /** 被我们替换掉的 Baritone 原生日志消费者；关闭模块时原样还回去 */
    private Consumer<Component> savedBaritoneLogger;
    /** 上一次转播的毫秒时间戳：同类事件按冷却节流，避免换成我们自己的刷屏 */
    private long lastBaritoneRelayMillis;
    private static final long BARITONE_RELAY_COOLDOWN_MILLIS = 3000;

    /**
     * 静默窗：本模块自己 stop / 重下发 mine 之后这么多刻内，Baritone 吐的「已取消」不转播。
     *
     * <p>用户 2026-09-18：「卸货的时候为什么会提示『挖矿任务已取消 ▸ 正在重新扫描目标矿』」——
     * Baritone 的 {@code stop} 命令本身会先 logDirect 一条「已取消」，转播器把这条当成了
     * 「挖矿被别人取消了」播给玩家。进物流态、连锁接管、刷怪笼优先、战斗收摊……每一处主动收摊
     * 都会产生这条副产物，玩家看到的就是「明明是卸货，却说挖矿被取消」。</p>
     */
    private int relayQuietUntilTick;
    /** 静默窗长度（刻）：2 秒，足够覆盖 stop 后 Baritone 那一两刻内的回声 */
    private static final int RELAY_QUIET_TICKS = 40;

    // ── 寻路视角（用户 2026-09-18：「没看着男中音寻路的那条线」；
    //                    2026-09-19：「寻路视角跟随抖动、不丝滑，加强算法」） ─────
    //
    // 男中音默认 freeLook = true：走路时它只把朝向「静默」发给服务端（LookBehavior.Target.Mode#resolve
    // → `antiCheat ? SERVER : NONE`），本地视角一动不动。把 freeLook 关掉后走路也落到 CLIENT 分支
    // （挖矿时本来就一直如此），玩家看得见它在往哪走、瞄向哪块。
    //
    // 但男中音每刻的写入是「直接跳到目标角度」：路径节点一换（转弯/上下坡/绕障碍）就是一次阶跃；
    // 它自带的 smoothLook 只是「最近 N 刻目标角度的窗口平均」——不处理 ±180° 环绕（视角跨中线时
    // 平均出南辕北辙的角度）、不碰俯仰、且每刻以 Δ/N 的等差步前进（角速度不连续），所以还是抖。
    //
    // 现改为本模块自己接管<b>可见视角</b>（见 {@link #applyViewFollow}）：PRE 阶段先读走男中音刚
    // 写下的「本刻目标角度」——它写给服务端与射线检测的精确角度不受任何影响，保证挖矿/交互照常精准；
    // POST 阶段再用自研平滑器写回可见视角：±180 环绕归一 + 指数趋近（角速度连续）+ 目标静止自动贴合。
    // 一个写入者、一个目标源：转弯丝滑，对准分毫不差（见 {@link #writeSmoothedView}）。

    /** 男中音视角类设置的原值：只在第一次写入前抓一次，模块关闭时原样还回去（视角涉及玩家操作手感，必须还原） */
    private boolean viewOriginalsSaved;
    private boolean originalFreeLook;
    private double originalRandomLooking;
    private double originalRandomLooking113;
    /** 男中音 {@code smoothLook} 的原值（默认 false，见 {@link #applyViewFollow}） */
    private boolean originalSmoothLook;

    /**
     * 战斗期间把可见视角让回战斗逻辑。
     *
     * <p>战斗走位由 {@code MiningCombat} 自己发目标（纯走路，无方块交互），模块的转视角要独占可见视角；
     * 若此时仍让男中音写 CLIENT，两边每刻各写一次就是上一轮那种抖动。置 true 时把 {@code freeLook}
     * 放回男中音默认值，走路转 SERVER 静默（服务端照常收到朝向，本地不动）——这正是战斗转视角代码里
     * 「战斗期间男中音的移动朝向是 SERVER 静默模式」那条注释的由来。</p>
     */
    private boolean combatViewHold;

    // ── 自研视角平滑器（用户 2026-09-19：「寻路视角跟随抖动、不丝滑，加强算法」） ─────
    // 每刻把「男中音写下的目标角度」低通滤波成可见视角。状态字段与算法说明见 writeSmoothedView。

    /** 每刻向目标趋近的比例（指数平滑因子，0~1）：越大越跟手，越小越丝滑 */
    private static final float VIEW_SMOOTH_FACTOR = 0.35f;
    /** 与目标差值小于该角度（度）直接贴合：消除指数趋近的无穷尾巴，对准不虚 */
    private static final float VIEW_SNAP_EPS = 0.4f;
    /** 目标角度连续静止这么多刻直接贴合：转弯收尾干脆，挖矿准星分毫不差 */
    private static final int VIEW_SETTLE_TICKS = 4;

    /** 平滑器事件监听器是否已挂上男中音事件总线（挂一次即可，永不摘除，用 {@link #viewFollowActive} 闸门控制） */
    private boolean viewSmootherRegistered;
    /** 平滑器闸门：跟随开启、未被战斗接管、且男中音确有寻路/挖矿进程时才动手 */
    private boolean viewFollowActive;
    /** 平滑器初值是否已从玩家当前视角取好（切换开关后重新取，避免跳变） */
    private boolean viewSmootherSeeded;
    /** 平滑后的当前可视图视角（本模块写回玩家的值） */
    private float viewYaw;
    private float viewPitch;
    /** PRE 阶段从男中音手里读下的「本刻目标角度」；尚无样本时 yaw 为 NaN */
    private float desiredYaw = Float.NaN;
    private float desiredPitch;
    /** 上一刻的目标角度：用于「目标静止」检测（yaw 同样 NaN 表示无上一刻） */
    private float lastDesiredYaw = Float.NaN;
    private float lastDesiredPitch;
    /** 目标角度已连续静止的刻数 */
    private int desiredStillTicks;

    public MiningPathing(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    /**
     * 接管 Baritone 的聊天输出。
     *
     * <p>{@code Settings.logger} 是 Baritone 所有 {@code logDirect} 的唯一出口——「已取消」
     * 「正在挖掘 [目标方块：…]」「无法找到前往 … 的路径」全部走它。把它换成过滤器后，
     * 模块运行期间聊天栏不再出现 {@code [Baritone]} 那套刷屏消息，只把有信息量的事件
     * （取消 / 目标不可达）转成自动挖矿自己的播报。</p>
     */
    public void suppressChat() {
        Settings settings = BaritoneAPI.getSettings();
        if (savedBaritoneLogger != null) return;
        savedBaritoneLogger = settings.logger.value;
        settings.logger.value = this::relayBaritoneMessage;
    }

    /** 还原 Baritone 原生日志输出（模块关闭 / 卸载时调用） */
    public void restoreChat() {
        if (savedBaritoneLogger == null) return;
        BaritoneAPI.getSettings().logger.value = savedBaritoneLogger;
        savedBaritoneLogger = null;
    }

    /**
     * Baritone 消息过滤 + 转播。
     *
     * <p>「正在挖掘 [目标方块：…]」这类纯状态噪音直接丢弃（它一秒能刷好几条）；
     * 有信息量的转成自动挖矿格式（第 101/112/114 条：模块前缀 + 状态色）并按冷却节流。</p>
     *
     * <p>两道闸门（用户 2026-09-18：「卸货的时候为什么提示挖矿任务已取消」）：</p>
     * <ol>
     *   <li><b>状态门</b>：只有当状态机在「采掘中 / 前往野外」时才把这些转成挖矿播报。
     *       卸货、补给、修补、进食、战斗这些状态下的「已取消」是我方主动收摊的副产物，
     *       读成「挖矿被取消」纯属误导（用户确认：状态机本身没问题，是提示不对）。</li>
     *   <li><b>静默窗</b>：本模块刚 {@link #stop()} 或刚重下发 mine 的 2 秒内不转播
     *       （见 {@link #relayQuietUntilTick}）——连锁接管、刷怪笼优先、物流收摊都走这两条路。</li>
     * </ol>
     */
    private void relayBaritoneMessage(Component message) {
        if (message == null) return;
        String text = message.getString();
        if (text.isEmpty()) return;

        String relay;
        if (text.contains("无法找到") && text.contains("路径")) {
            relay = "§e⚠ 挖矿目标不可达 §8▸ 放弃该矿点，正在重新选择目标矿";
        } else if (text.contains("已取消")) {
            relay = "§e⚠ 挖矿任务已取消 §8▸ 正在重新扫描目标矿";
        } else {
            return; // 其余（正在挖掘 / 计算中 / 调试信息）一律丢弃
        }

        if (!isMiningIntentState()) return;
        if (mc.player != null && mc.player.tickCount < relayQuietUntilTick) return;

        long now = System.currentTimeMillis();
        if (now - lastBaritoneRelayMillis < BARITONE_RELAY_COOLDOWN_MILLIS) return;
        lastBaritoneRelayMillis = now;
        module.info(relay);
    }

    /**
     * 当前状态是否「本来就在挖矿」——只有这两态下的 Baritone 取消/不可达才值得播给玩家。
     *
     * <p>取不到状态机（模块未就绪）时按 false 处理：宁可少播一条，也不要在物流流程里
     * 冒出一条「挖矿任务已取消」。</p>
     */
    private boolean isMiningIntentState() {
        try {
            MinerState state = module.fsm().state();
            return state == MinerState.MINING || state == MinerState.GO_WILD;
        } catch (Throwable e) {
            return false;
        }
    }

    /** 进入静默窗：本模块主动 stop / 重下发 mine 之后调用 */
    private void enterRelayQuietWindow() {
        if (mc.player == null) return;
        relayQuietUntilTick = mc.player.tickCount + RELAY_QUIET_TICKS;
    }

    /**
     * 启动 Baritone 挖矿（普通模式）。
     * 种子模式不经过这里：由 MinerFSM 的种子采集循环逐块 pathToOre + 破坏。
     */
    public void startMining(List<Block> targets) {
        startMining(targets, true);
    }

    /**
     * 启动 Baritone 挖矿。
     *
     * @param broadcast 是否播报「Baritone 已启动挖掘」。连锁 / 刷怪笼优先这类由模块自己
     *                  播报原因的重下发传 false，避免同一次重下发出现两条消息
     */
    public void startMining(List<Block> targets, boolean broadcast) {
        if (targets == null || targets.isEmpty()) {
            module.error("未选择目标矿石，无法启动挖矿");
            return;
        }

        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) {
                module.error("Baritone 未加载");
                return;
            }

            // 普通模式同时挖深层/浅层变种：mine 命令支持多 block 参数
            StringBuilder cmd = new StringBuilder("mine");
            for (Block b : targets) {
                cmd.append(" ").append(BuiltInRegistries.BLOCK.getKey(b));
            }
            baritone.getCommandManager().execute(cmd.toString());
            enterRelayQuietWindow(); // 重下发 mine 会先取消旧进程，那条「已取消」不该播给玩家

            if (!broadcast) {
                resetStuckTracking();
                return;
            }

            // 旧项目走 translations.BaritoneChatTranslations.translateBlockId，
            // 本项目等价入口是 platform/identity/BlockIdentifier.localizedBlockName
            String blockId = BuiltInRegistries.BLOCK.getKey(targets.get(0)).toString();
            String firstName = BlockIdentifier.localizedBlockName(blockId);
            if (firstName == null) firstName = blockId;
            String name = targets.size() > 1 ? firstName + " §8等 " + targets.size() + " 种变体" : firstName;
            module.info("§aBaritone 已启动挖掘：" + name);

            resetStuckTracking();

        } catch (Throwable e) {
            module.error("Baritone 调用失败：" + e.getMessage());
        }
    }

    /** 重置卡死检测基准（每次重下发挖掘目标都从当前位置重新计时） */
    private void resetStuckTracking() {
        if (mc.player != null) {
            lastPos = mc.player.blockPosition();
        }
        stuckTicks = 0;
        lastCheckTick = 0;
    }

    /**
     * 种子模式：寻路到指定预测矿位置（走到跟前，破坏由 MinerFSM 负责）
     *
     * @param pos 预测矿位置
     * @return 是否成功发起寻路
     */
    public boolean pathToOre(BlockPos pos) {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) {
                return false;
            }
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(pos));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 采集引擎是否活跃：普通模式 mine 进程运行中，或种子模式寻路目标激活。
     * 用于「进程意外退出才重启」的自愈判断，避免打断正常挖矿。
     */
    public boolean isMiningActive() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getMineProcess().isActive() || baritone.getCustomGoalProcess().isActive();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 停止 Baritone
     *
     * <p>进静默窗：{@code stop} 命令本身会 logDirect 一条「已取消」，那是我方主动收摊的副产物，
     * 不该被转播成「挖矿任务已取消」（用户 2026-09-18 在卸货时看到的就是这条）。</p>
     */
    public void stop() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return;

            enterRelayQuietWindow();
            baritone.getCommandManager().execute("stop");
            baritone.getPathingBehavior().cancelEverything();

        } catch (Throwable e) {
            // 停止失败不影响后续，忽略
        }
    }

    /**
     * 批量应用 Baritone 设置（模块启动时调用）
     */
    public void applySettings(boolean avoidLava, boolean mobAvoidance, int mobAvoidanceRadius,
                              boolean allowBreak, boolean allowPlace, int maxFallHeight,
                              boolean pauseMiningForFallingBlocks,
                              boolean allowInventory, boolean autoTool, boolean sprintAscends,
                              boolean allowParkour, boolean allowParkourPlace,
                              boolean allowDiagonalAscend, boolean allowDiagonalDescend,
                              boolean allowOnlyExposedOres,
                              int allowOnlyExposedOresDistance, int minYLevelWhileMining,
                              int maxYLevelWhileMining, int mineMaxOreLocationsCount,
                              boolean blacklistClosestOnFailure, boolean legitMine,
                              int legitMineYLevel, boolean legitMineIncludeDiagonals) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            List<Block> blocksToAvoid = new ArrayList<>(settings.blocksToAvoid.value);
            if (avoidLava && !blocksToAvoid.contains(Blocks.LAVA)) blocksToAvoid.add(Blocks.LAVA);
            if (!avoidLava) blocksToAvoid.removeIf(block -> block == Blocks.LAVA);
            settings.blocksToAvoid.value = blocksToAvoid;
            
            // 已确认存在的设置（26.1.2 官方映射下逐个核实）
            settings.allowSprint.value = true;           // 启用疾跑
            settings.allowBreak.value = allowBreak;      // 是否破坏方块
            settings.allowPlace.value = allowPlace;      // 是否放置方块
            settings.avoidance.value = mobAvoidance;
            settings.mobAvoidanceRadius.value = mobAvoidanceRadius;
            settings.maxFallHeightNoWater.value = maxFallHeight;
            settings.pauseMiningForFallingBlocks.value = pauseMiningForFallingBlocks;
            settings.allowInventory.value = allowInventory;
            settings.autoTool.value = autoTool;
            settings.assumeExternalAutoTool.value = false; // 关闭「外部工具假设」，确保 Baritone 自身 autoTool 真正生效
            settings.sprintAscends.value = sprintAscends;
            settings.allowParkour.value = allowParkour;
            settings.allowParkourPlace.value = allowParkourPlace;
            settings.allowDiagonalAscend.value = allowDiagonalAscend;
            settings.allowDiagonalDescend.value = allowDiagonalDescend;
            settings.allowOnlyExposedOres.value = allowOnlyExposedOres;
            settings.allowOnlyExposedOresDistance.value = allowOnlyExposedOresDistance;
            settings.minYLevelWhileMining.value = minYLevelWhileMining;
            settings.maxYLevelWhileMining.value = maxYLevelWhileMining;
            settings.mineMaxOreLocationsCount.value = mineMaxOreLocationsCount;
            settings.blacklistClosestOnFailure.value = blacklistClosestOnFailure;
            settings.legitMine.value = legitMine;
            settings.legitMineYLevel.value = legitMineYLevel;
            settings.legitMineIncludeDiagonals.value = legitMineIncludeDiagonals;
            
            // 优先挖掘附近矿石的设置
            settings.mineGoalUpdateInterval.value = module.getMineGoalUpdateInterval();  // 使用配置值
            settings.blockReachDistance.value = 4.5f;    // 缩小交互距离，优先近处

            // 寻路视角（把 freeLook 关掉，走路也写可见视角）
            applyViewFollow();

        } catch (Throwable e) {
            // 设置应用失败不影响主体逻辑，下次启动重新应用
        }
    }

    // ── 寻路视角 ──────────────────────────────────────────────────────────────

    /**
     * 应用「寻路视角跟随」设置（模块启动与界面开关改动时调用）。
     *
     * <p>开：{@code freeLook = false} → 走路也走 CLIENT 分支，视角由男中音每刻写向路径下一节点；
     * 同时把每刻随机偏移 {@code randomLooking / randomLooking113} 归零——它们会在 CLIENT 模式下直接写进
     * 可见视角（{@code randomLooking113} 默认 2 即每刻 ±1 度），正是第一代「视角抖」的来源。
     * 关：三项全部还原成写入前的原值，男中音回到出厂行为（走路视角不动、鼠标完全由玩家控制）。</p>
     *
     * <p><b>自研平滑接管</b>（用户 2026-09-19：「视角跟随还是有一点轻微的抖动不流畅，走路的时候」；
     * 旧版开的是男中音自带 {@code smoothLook}，它只做「最近 N 刻目标角度的窗口平均」，
     * 三类毛病：±180° 环绕不处理（视角跨中线时平均出反方向）、俯仰不平滑、每刻 Δ/N 等差步
     * （角速度不连续）。现改为 {@code smoothLook = false} + 本模块在
     * {@code PlayerUpdateEvent} 的 PRE/POST 之间接管：PRE 读走男中音刚写下的精确目标角度
     * （它写给服务端与射线检测的角度原样保留），POST 用
     * 「环绕归一 + 指数趋近 + 静止贴合」的平滑器写回可见视角（{@link #writeSmoothedView}）。
     * 代价是转向略滞后（约 0.2~0.3 秒），对挂机挖矿无影响；战斗期间由 {@link #setCombatViewHold}
     * 把 {@code freeLook} 放回原值并关闭本平滑器，可见视角归战斗逻辑。</p>
     */
    public void applyViewFollow() {
        try {
            Settings settings = BaritoneAPI.getSettings();
            if (!viewOriginalsSaved) {
                originalFreeLook = settings.freeLook.value;
                originalRandomLooking = settings.randomLooking.value;
                originalRandomLooking113 = settings.randomLooking113.value;
                originalSmoothLook = settings.smoothLook.value;
                viewOriginalsSaved = true;
            }

            boolean follow = module.settings().pathViewFollow && !combatViewHold;
            settings.freeLook.value = !follow;
            settings.randomLooking.value = follow ? 0.0 : originalRandomLooking;
            settings.randomLooking113.value = follow ? 0.0 : originalRandomLooking113;
            // 平滑由本模块接管：男中音自带的窗口平均要么叠加抖动、要么跨 ±180 抽风，一律关掉
            settings.smoothLook.value = false;

            viewFollowActive = follow;
            viewSmootherSeeded = false; // 重新开启时从玩家当前视角起跳，避免从旧角度猛拉
            if (follow) {
                ensureViewSmootherRegistered();
            }
        } catch (Throwable e) {
            // 设置写入失败不影响主体逻辑
        }
    }

    // ── 自研视角平滑器 ────────────────────────────────────────────────────────

    /**
     * 把平滑器事件监听器挂上男中音事件总线（只挂一次）。
     *
     * <p>挂在男中音所有行为之后（{@code CopyOnWriteArrayList} 按注册序派发）：PRE 阶段读到的
     * {@code player.getYRot()} 正是男中音 {@code LookBehavior} 刚写下的「本刻精确目标角度」；
     * POST 阶段再晚于它的复原/窗口平均写入，我们写回的值就是本刻可见的最终视角。
     * 事件总线没有摘除接口，因此用 {@link #viewFollowActive} 做软闸门，模块开关无忧。</p>
     */
    private void ensureViewSmootherRegistered() {
        if (viewSmootherRegistered) return;
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return;
            baritone.getGameEventHandler().registerEventListener(new AbstractGameEventListener() {
                @Override
                public void onPlayerUpdate(PlayerUpdateEvent event) {
                    if (!viewFollowActive || !smootherGatePasses()) return;
                    if (mc.player == null) return;
                    if (event.getState() == EventState.PRE) {
                        // 男中音这一刻的目标角度（已含它本刻的钳制），先记下，POST 再算
                        desiredYaw = mc.player.getYRot();
                        desiredPitch = mc.player.getXRot();
                    } else if (event.getState() == EventState.POST) {
                        writeSmoothedView();
                    }
                }
            });
            viewSmootherRegistered = true;
        } catch (Throwable e) {
            // 挂载失败不影响主体逻辑（视角回退男中音原生行为）
        }
    }

    /**
     * 平滑器闸门：只有男中音真的在「寻路 / 挖矿 / 物流寻路」时才接管可见视角。
     *
     * <p>其余时刻（战斗接管、挂机修复对齐、水流脱困、连锁挖矿接管）这些流程自己写精确视角，
     * 平滑器不掺和；男中音没有进程时也不写角度，无需兜底。</p>
     */
    private boolean smootherGatePasses() {
        try {
            if (mc.player == null || mc.player.isFallFlying() || !module.isEnabled()) return false;
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getPathingBehavior().isPathing()
                || baritone.getMineProcess().isActive()
                || baritone.getCustomGoalProcess().isActive();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 自研平滑写回：把本刻目标角度低通滤波后写回可见视角。
     *
     * <p><b>算法三件套</b>（对应旧问题的三个病根）：</p>
     * <ul>
     *   <li><b>±180 环绕归一</b>：yaw 差值先归一进 [-180,180) 再参与计算——旧窗口平均在视角跨
     *       「-179 → 179」中线时会把 (-179+179)/2=0 当平均，镜头瞬间抽向反方向，这就是最刺眼的一档抖；</li>
     *   <li><b>指数趋近</b>：每刻消掉剩余差值的 {@code VIEW_SMOOTH_FACTOR} 比例，角速度随剩余角度
     *       单调递减且连续——转弯是「快起慢收」的弧线，而不是旧版每刻 Δ/N 的等距顿挫；</li>
     *   <li><b>静止贴合</b>：目标稳定（{@code VIEW_SETTLE_TICKS} 刻没变）或已足够近
     *       （{@code VIEW_SNAP_EPS}）就直接贴合目标——指数趋近的无穷尾巴被截断，挖矿准星、
     *       收货开箱方向的最终对准分毫不差（男中音的射线检测走它自己写下的精确角度，不经过本平滑器）。</li>
     * </ul>
     *
     * <p>写入时机：本监听器注册在男中音所有行为之后，POST 后不再有男中音的视角写入，
     * 本刻渲染拿到的就是这个值；下一刻男中音的包发送与射线检测照常使用它自己在 PRE 写的
     * 精确角度（{@code freeLook = false} 时服务端收到的是男中音原目标角，玩家看到的是平滑角，
     * 防疯狗反作弊且视觉丝滑）。</p>
     */
    private void writeSmoothedView() {
        if (Float.isNaN(desiredYaw)) return;
        if (!viewSmootherSeeded) {
            viewYaw = mc.player.getYRot();
            viewPitch = mc.player.getXRot();
            viewSmootherSeeded = true;
        }

        float yawDelta = wrapDegrees(desiredYaw - viewYaw);
        float pitchDelta = desiredPitch - viewPitch;

        // 目标静止检测：拿上一刻目标与本刻目标比（yaw 走环绕归一后的差值）
        boolean targetStill = !Float.isNaN(lastDesiredYaw)
            && Math.abs(wrapDegrees(desiredYaw - lastDesiredYaw)) < VIEW_SNAP_EPS
            && Math.abs(desiredPitch - lastDesiredPitch) < VIEW_SNAP_EPS;
        desiredStillTicks = targetStill ? desiredStillTicks + 1 : 0;
        lastDesiredYaw = desiredYaw;
        lastDesiredPitch = desiredPitch;

        boolean nearEnough = Math.abs(yawDelta) < VIEW_SNAP_EPS && Math.abs(pitchDelta) < VIEW_SNAP_EPS;
        if (desiredStillTicks >= VIEW_SETTLE_TICKS || nearEnough) {
            // 直接贴合：转弯收尾干脆、对准分毫不差
            viewYaw = desiredYaw;
            viewPitch = desiredPitch;
        } else {
            // 指数趋近：角速度连续，转大弯也是丝滑弧线
            viewYaw = wrapDegrees(viewYaw + yawDelta * VIEW_SMOOTH_FACTOR);
            viewPitch += pitchDelta * VIEW_SMOOTH_FACTOR;
        }

        mc.player.setYRot(viewYaw);
        mc.player.setXRot(viewPitch);
    }

    /** 角度差归一进 [-180,180)：跨 ±180° 中线时取最短转角 */
    private static float wrapDegrees(float angle) {
        angle %= 360f;
        if (angle >= 180f) angle -= 360f;
        if (angle < -180f) angle += 360f;
        return angle;
    }

    /** 战斗态进出时调用：进战斗让回可见视角（见 {@link #combatViewHold}），出战斗交回寻路视角 */
    public void setCombatViewHold(boolean hold) {
        if (combatViewHold == hold) return;
        combatViewHold = hold;
        applyViewFollow();
    }

    /** 模块关闭时还原视角类设置（{@code freeLook} / 两项随机偏移 / {@code smoothLook}），不留副作用给玩家自己用男中音 */
    public void restoreViewSettings() {
        if (!viewOriginalsSaved) return;
        try {
            Settings settings = BaritoneAPI.getSettings();
            settings.freeLook.value = originalFreeLook;
            settings.randomLooking.value = originalRandomLooking;
            settings.randomLooking113.value = originalRandomLooking113;
            settings.smoothLook.value = originalSmoothLook;
        } catch (Throwable e) {
            // 还原失败不影响关闭流程
        } finally {
            viewOriginalsSaved = false;
            combatViewHold = false;
            viewFollowActive = false;
            viewSmootherSeeded = false;
            desiredYaw = Float.NaN;
            lastDesiredYaw = Float.NaN;
            desiredStillTicks = 0;
        }
    }

    /**
     * 动态更新单个设置
     */
    public void updateSetting(String key, Object value) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            
            if (key.equals("allowBreak")) settings.allowBreak.value = (Boolean) value;
            else if (key.equals("allowPlace")) settings.allowPlace.value = (Boolean) value;
            else if (key.equals("avoidLava")) {
                List<Block> blocksToAvoid = new ArrayList<>(settings.blocksToAvoid.value);
                if ((Boolean) value && !blocksToAvoid.contains(Blocks.LAVA)) blocksToAvoid.add(Blocks.LAVA);
                if (!(Boolean) value) blocksToAvoid.removeIf(block -> block == Blocks.LAVA);
                settings.blocksToAvoid.value = blocksToAvoid;
            }
            else if (key.equals("avoidance")) settings.avoidance.value = (Boolean) value;
            else if (key.equals("mobAvoidanceRadius")) settings.mobAvoidanceRadius.value = (Integer) value;
            else if (key.equals("maxFallHeightNoWater")) settings.maxFallHeightNoWater.value = (Integer) value;
            else if (key.equals("pauseMiningForFallingBlocks")) settings.pauseMiningForFallingBlocks.value = (Boolean) value;
            else if (key.equals("allowInventory")) settings.allowInventory.value = (Boolean) value;
            else if (key.equals("autoTool")) {
                settings.autoTool.value = (Boolean) value;
                settings.assumeExternalAutoTool.value = false; // 关闭「外部工具假设」，确保 autoTool 真正生效
            }
            else if (key.equals("sprintAscends")) settings.sprintAscends.value = (Boolean) value;
            else if (key.equals("allowParkour")) settings.allowParkour.value = (Boolean) value;
            else if (key.equals("allowParkourPlace")) settings.allowParkourPlace.value = (Boolean) value;
            else if (key.equals("allowDiagonalAscend")) settings.allowDiagonalAscend.value = (Boolean) value;
            else if (key.equals("allowDiagonalDescend")) settings.allowDiagonalDescend.value = (Boolean) value;
            else if (key.equals("allowOnlyExposedOres")) settings.allowOnlyExposedOres.value = (Boolean) value;
            else if (key.equals("allowOnlyExposedOresDistance")) settings.allowOnlyExposedOresDistance.value = (Integer) value;
            else if (key.equals("minYLevelWhileMining")) settings.minYLevelWhileMining.value = (Integer) value;
            else if (key.equals("maxYLevelWhileMining")) settings.maxYLevelWhileMining.value = (Integer) value;
            else if (key.equals("mineMaxOreLocationsCount")) settings.mineMaxOreLocationsCount.value = (Integer) value;
            else if (key.equals("blacklistClosestOnFailure")) settings.blacklistClosestOnFailure.value = (Boolean) value;
            else if (key.equals("legitMine")) settings.legitMine.value = (Boolean) value;
            else if (key.equals("legitMineYLevel")) settings.legitMineYLevel.value = (Integer) value;
            else if (key.equals("legitMineIncludeDiagonals")) settings.legitMineIncludeDiagonals.value = (Boolean) value;
            else if (key.equals("mineGoalUpdateInterval")) settings.mineGoalUpdateInterval.value = (Integer) value;
            
        } catch (Throwable e) {
            // 单设置更新失败不影响整体
        }
    }

    /**
     * 更新搭路方块白名单
     */
    public void updatePlaceBlocks(List<Block> blocks) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            settings.acceptableThrowawayItems.value = new ArrayList<>(
                blocks.stream()
                    .map(Block::asItem)
                    .filter(item -> item != Items.AIR)
                    .toList()
            );
        } catch (Throwable e) {
            // 忽略
        }
    }

    /**
     * 检测 Baritone 是否卡死（原地滞留超过3分钟且位移<5格）
     */
    public boolean isStuck() {
        if (mc.player == null) return false;

        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;

            // 只有在寻路状态下才检测卡死
            if (!baritone.getPathingBehavior().isPathing()) {
                return false;
            }

            stuckTicks++;

            // 每3分钟检测一次
            if (stuckTicks - lastCheckTick < STUCK_CHECK_INTERVAL) {
                return false;
            }

            BlockPos currentPos = mc.player.blockPosition();
            double distance = Math.sqrt(currentPos.distSqr(lastPos));

            lastCheckTick = stuckTicks;
            lastPos = currentPos;

            // 3分钟内位移小于5格，判定卡死
            return distance < STUCK_DISTANCE_THRESHOLD;

        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Baritone 是否正在寻路
     */
    public boolean isPathing() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getPathingBehavior().isPathing();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 自定义目标进程是否仍活跃（含寻路计算中）。
     * 用于物流寻路（卸货/补给/修补）的重发判断：
     * isPathing 在「路径计算中」为 false，若用它做重发条件，会在计算期间反复重发目标、重置计算，导致傻站。
     * isActive 从 setGoalAndPath 起即为 true，直到目标完成/取消才变 false，正好区分「算路中」与「真的断了」。
     */
    public boolean isCustomGoalActive() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getCustomGoalProcess().isActive();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 获取 Baritone 实例（供状态机调用）。失败返回 null。
     */
    public IBaritone getBaritoneInstance() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 获取 Baritone 实例（内部使用）。失败返回 null。
     */
    private IBaritone getBaritone() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable e) {
            return null;
        }
    }
}
