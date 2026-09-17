package com.yiyiaddon.feature.mining.navigation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
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

    // ── 寻路视角（用户 2026-09-18：「寻路视角 还是没看着 男中音寻路的那条线」） ─────
    //
    // 男中音默认 freeLook = true：走路时它只把朝向「静默」发给服务端（LookBehavior.Target.Mode#resolve
    // → `antiCheat ? SERVER : NONE`），本地视角一动不动 —— 所以寻路的那个视角看不见。
    // 把 freeLook 关掉后走路也落到 CLIENT 分支（挖矿时本来就一直如此），视角由男中音每刻写向路径
    // 下一个节点。此时本地视角只有男中音一个写入者，因此不抖；抖动来自第二个写入者（本模块自己写角度）。

    /** 男中音视角类设置的原值：只在第一次写入前抓一次，模块关闭时原样还回去（视角涉及玩家操作手感，必须还原） */
    private boolean viewOriginalsSaved;
    private boolean originalFreeLook;
    private double originalRandomLooking;
    private double originalRandomLooking113;

    /**
     * 战斗期间把可见视角让回战斗逻辑。
     *
     * <p>战斗走位由 {@code MiningCombat} 自己发目标（纯走路，无方块交互），模块的转视角要独占可见视角；
     * 若此时仍让男中音写 CLIENT，两边每刻各写一次就是上一轮那种抖动。置 true 时把 {@code freeLook}
     * 放回男中音默认值，走路转 SERVER 静默（服务端照常收到朝向，本地不动）——这正是战斗转视角代码里
     * 「战斗期间男中音的移动朝向是 SERVER 静默模式」那条注释的由来。</p>
     */
    private boolean combatViewHold;

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
     * 可见视角（{@code randomLooking113} 默认 2 即每刻 ±1 度），正是「视角抖」的直接来源。
     * 关：三项全部还原成写入前的原值，男中音回到出厂行为（走路视角不动、鼠标完全由玩家控制）。</p>
     */
    public void applyViewFollow() {
        try {
            Settings settings = BaritoneAPI.getSettings();
            if (!viewOriginalsSaved) {
                originalFreeLook = settings.freeLook.value;
                originalRandomLooking = settings.randomLooking.value;
                originalRandomLooking113 = settings.randomLooking113.value;
                viewOriginalsSaved = true;
            }

            boolean follow = module.settings().pathViewFollow && !combatViewHold;
            settings.freeLook.value = !follow;
            settings.randomLooking.value = follow ? 0.0 : originalRandomLooking;
            settings.randomLooking113.value = follow ? 0.0 : originalRandomLooking113;

        } catch (Throwable e) {
            // 设置写入失败不影响主体逻辑
        }
    }

    /** 战斗态进出时调用：进战斗让回可见视角（见 {@link #combatViewHold}），出战斗交回寻路视角 */
    public void setCombatViewHold(boolean hold) {
        if (combatViewHold == hold) return;
        combatViewHold = hold;
        applyViewFollow();
    }

    /** 模块关闭时还原视角类设置（{@code freeLook} / 两项随机偏移），不留副作用给玩家自己用男中音 */
    public void restoreViewSettings() {
        if (!viewOriginalsSaved) return;
        try {
            Settings settings = BaritoneAPI.getSettings();
            settings.freeLook.value = originalFreeLook;
            settings.randomLooking.value = originalRandomLooking;
            settings.randomLooking113.value = originalRandomLooking113;
        } catch (Throwable e) {
            // 还原失败不影响关闭流程
        } finally {
            viewOriginalsSaved = false;
            combatViewHold = false;
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
