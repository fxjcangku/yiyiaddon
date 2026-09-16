package com.yiyiaddon.feature.mining.fsm;

import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.service.ServerCommandRunner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 挖矿状态机 - FSM 核心引擎（旧项目 {@code mining/fsm/MinerFSM.java}，1842 行，逐条移植）。
 *
 * <p>状态转换流程（旧项目类注释 {@code :34-42} 逐字）：</p>
 * <pre>
 * IDLE → GO_WILD → MINING → [UNLOADING/SUPPLY/REPAIR] → GO_WILD → MINING ...
 *
 * 死亡事件拦截：
 * ANY_STATE → DEATH_HANDLING → RESPAWN_WAIT → GO_WILD
 * </pre>
 *
 * <p><b>对照口径：</b>本类每个方法上方的注释都写明对应的旧方法名与行号，便于逐字比对；
 * 全部判据、顺序、阈值、常量值、计时口径与旧项目一致（{@code stateTick} 在分派前自增，
 * 因此每个状态首帧 {@code stateTick == 1}；{@code transitionTo} 内把 {@code stateTick} 归零）。
 * 用户可见文案（含 {@code §} 色码、全角空格、{@code §8▸} 分隔符）逐字照抄旧项目。</p>
 *
 * <p><b>本轮留白（一行都没写，遇到旧源码对应分支整段跳过）：</b></p>
 * <ul>
 *   <li>种子模式全部：{@code tickSeedMining}（旧 {@code :614-671}）、{@code findNextSeedTarget}
 *       （{@code :674-693}）、{@code isTargetOreAt}（{@code :696-698}）、{@code breakSeedBlock}
 *       （{@code :769-805}）、{@code ensureBestToolForSeedTarget}（{@code :808-834}）、
 *       {@code ensurePickaxeInHand}（{@code :853-868}）、{@code seedVisited} / {@code seedTarget} /
 *       {@code seedBreakState} / {@code seedBreakTicks} / {@code seedBreakFace} / {@code seedPathRetries}
 *       六个字段，以及全部对 {@code OrePredictor} 的调用（{@code :480/622/625/645/657/679/683/801}）；
 *       旧源码里每一处 {@code module.isSeedMiningEnabled()} 分支都整段删除，只保留普通模式路径。</li>
 *   <li>假矿检测（{@code AutoMinerModule.checkFakeOres}）、秒破发包（{@code fastbreak} 两文件 + mixin）、
 *       {@code .wk} 指令树、配置页面：均属后续批次，本类不出现。</li>
 * </ul>
 *
 * <p>原「下界挖矿自动开岩浆透视」留白项已在 ESP 批次接上：进 {@code MINING} 且在下界时
 * {@code module.enableLavaEsp()} + 逐字播报，渲染见 {@code render/MiningPointRenderer}。</p>
 *
 * <p><b>与旧项目的差异（其余逐条一致）：</b></p>
 * <ol>
 *   <li><b>死亡自动重生的归属</b>（用户 2026-09-16 拍板）：旧项目 {@code tryMeteorAutoRespawn()}
 *       （{@code :1781-1793}，toggle 第三方框架的模块）整段删除。本项目有自研
 *       {@code feature/respawn/AutoRespawnModule}（默认开启）承担这件事，状态机不再切换任何模块，
 *       只保留 {@code mc.player.respawn()} 兜底（旧 {@code :1477-1479}）。随之两条死亡播报按最小改动
 *       换成本项目模块名（其余一字不改）：旧 {@code :1472} {@code "§c✗ 已调用流星自动重生模块"}
 *       → {@code "§c✗ 已调用自动重生模块"}；旧 {@code :1723}
 *       {@code "§c✗ 检测到死亡 §8▸ 已调用流星自动重生"} → {@code "§c✗ 检测到死亡 §8▸ 已调用自动重生"}。</li>
 *   <li><b>KillAura 修补联动</b>：旧 {@code startKillAura / stopKillAura}（{@code :1763-1779}）
 *       改为接口 seam {@link RepairCombatHook}（实现由批次 4 的 KillAura 模块注入），
 *       状态机侧调用点与 {@code killAuraWasOnBefore} 卫语句语义不变。</li>
 *   <li><b>外部依赖</b>：旧 {@code Meteor InvUtils} 换成 26.1.2 原语——选槽走
 *       {@code Inventory#setSelectedSlot} + {@code ServerboundSetCarriedItemPacket}，
 *       与副手互换走 {@code handleContainerInput(..., ContainerInput.SWAP, ...)}（按钮 40 = 副手），
 *       与本项目 {@code DefaultStardewAdapter} 同一做法；旧 {@code WKCommand.WKData} 三点位换成
 *       {@code pointStore().get(MiningPointType.*)}；{@code module.isActive() / toggle()} 换成
 *       {@code isEnabled() / ModuleManager.setEnabled(MODULE_ID, false)}。</li>
 *   <li>旧 {@code tickUnloading}（{@code :1080-1085}）与 {@code tickSupply}（{@code :1205-1210}）
 *       里两段「维度限制已临时关闭」的注释代码不再保留（被注释掉的旧逻辑，无行为）。</li>
 * </ol>
 */
public final class MiningStateMachine {

    private final AutoMinerModule module;
    private final Minecraft mc;

    /**
     * 副手在 {@code Inventory} 里的索引（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手）。
     *
     * <p>供 {@code ContainerInput.SWAP} 的 button 参数使用：button=40 表示与被点击的槽位交换副手物品，
     * 与本项目 {@code DefaultStardewAdapter.swapOffhandWith} 同一口径。</p>
     */
    private static final int OFFHAND_INV_INDEX = 40;

    // ── 状态与传送监测（旧 :48-56） ──
    private MinerState state = MinerState.IDLE;
    private int stateTick = 0;
    private BlockPos teleportStartPos = BlockPos.ZERO;
    private int teleportTimeout = 0;
    private int teleportRetries = 0;
    private int teleportCooldownTicks = 0;   // RTP 冷却剩余 tick（服务器 RTP 有冷却，失败后等冷却再重试）
    private static final int MAX_TELEPORT_RETRIES = 3; // 传送失败最多自动重试 3 次

    /** 当前状态（只读，供控制台概览页显示）；状态机内部行为不变 */
    public MinerState state() {
        return state;
    }

    // 普通模式 mine 进程退出自愈：连续重启失败判定附近无矿，RTP 换区（旧 :66-68）
    private int mineRestartCount = 0;    // mine 退出重启计数
    private int mineRestartCooldown = 0; // 重启后冷却 tick（等 mine 启动，避免误判又退出）

    // 修补模式数据（旧 :70-82）
    private ItemStack savedTool = ItemStack.EMPTY;
    private ItemStack savedWeapon = ItemStack.EMPTY;
    private int savedToolSlot = -1;
    private int savedWeaponSlot = -1;
    private boolean repairMode = false;
    private boolean repairPathIssued = false;
    private int repairSwapAttempts = 0;
    private int repairSwapRequestedTick = -1;
    private boolean unloadingPathIssued = false;
    private boolean supplyPathIssued = false;
    private boolean killAuraWasOnBefore = false; // 进入修补前 KillAura 是否本来就开着（避免误关用户自己的 KA）
    private int supplyFailCount = 0;             // 补给空手连续计数（2 次箱空直接停机，防 SUPPLY↔MINING 死循环）

    // 潜影盒打包机换盒等待（旧 :84-91）
    private boolean boxSwapWaiting = false;      // 关箱后等待打包机推盒+放新盒
    private int boxSwapTicks = 0;                // 换盒等待计时
    private int boxSwapCount = 0;                // 本轮卸货累计换盒次数
    private static final int BOX_SWAP_WAIT_TICKS = 40; // 换盒等待 2 秒（40 tick）
    private static final int MAX_BOX_SWAPS = 10;       // 换盒次数上限（防打包机坏了死循环）
    private int noContainerTicks = 0;                  // 标点位置无容器持续 tick
    private static final int NO_CONTAINER_TIMEOUT = 200; // 无容器 10 秒（200 tick）后停机

    // 死亡标志（旧 :93-94）
    private boolean playerWasDead = false;

    // 卡死监测：改用速度监测而非位置监测（旧 :96-107）
    private int lowSpeedTicks = 0;
    private int waterStuckTicks = 0;
    private boolean waterEscapeActive = false; // 水中脱困寻路是否进行中
    private int waterEscapeTicks = 0;          // 水中脱困寻路已持续时间
    private BlockPos lastPosSample = BlockPos.ZERO; // 原地抖动卡死的位置采样点
    private int noMoveTicks = 0;                    // 位移长时间不变的累计 tick
    private int stuckResetCount = 0;                // 连续原地抖动卡死次数（超过阈值才传送去野外）
    private static final double MIN_SPEED_THRESHOLD = 0.05; // 速度低于0.05判定为卡住
    private static final int STUCK_TIME_THRESHOLD = 3600;
    private static final int NO_MOVE_THRESHOLD = 3600; // 3分钟位移<2格判定原地抖动
    private static final int PATH_TIMEOUT_TICKS = 2400;

    // 自动捡取掉落物（旧 :109-114）
    private ItemEntity pickupTarget = null;
    private int pickupTimeout = 0;
    // 捡取失败黑名单：掉落物卡角落捡不起来时记录位置，避免反复寻路捡同一个
    private final Set<BlockPos> pickupBlacklist = new HashSet<>();
    private static final int MAX_PICKUP_BLACKLIST = 64; // 黑名单上限，防止无限增长

    // 岩浆避险：附近有岩浆时停止挖矿并寻路到安全位置（旧 :116-119）
    private boolean lavaEscapeActive = false;   // 岩浆脱困寻路进行中
    private int lavaEscapeTicks = 0;            // 岩浆脱困已持续 tick
    private boolean diedInLava = false;         // 死亡时是否在岩浆里（复活后跳过 back 用）

    /**
     * 修补联动战斗钩子（旧 {@code :1763-1779} 的 KillAura 联动）；
     * 实现由批次 4 的 KillAura 模块通过 {@link #setRepairCombat(RepairCombatHook)} 注入。
     */
    private volatile RepairCombatHook repairCombat = RepairCombatHook.NONE;

    public MiningStateMachine(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    /**
     * 注入修补联动战斗实现（批次 4 由 {@code AutoMinerModule} 调用）。
     *
     * <p>传 {@code null} 等价于 {@link RepairCombatHook#NONE}。</p>
     */
    public void setRepairCombat(RepairCombatHook hook) {
        this.repairCombat = hook == null ? RepairCombatHook.NONE : hook;
    }

    /** 清零全部运行期状态字段（旧 {@code reset}，{@code :126-170}；种子模式六个字段随留白去掉） */
    public void reset() {
        state = MinerState.IDLE;
        stateTick = 0;
        teleportStartPos = BlockPos.ZERO;
        teleportTimeout = 0;
        teleportRetries = 0;
        teleportCooldownTicks = 0;
        mineRestartCount = 0;
        mineRestartCooldown = 0;
        savedTool = ItemStack.EMPTY;
        savedWeapon = ItemStack.EMPTY;
        savedToolSlot = -1;
        savedWeaponSlot = -1;
        repairMode = false;
        repairPathIssued = false;
        repairSwapAttempts = 0;
        repairSwapRequestedTick = -1;
        unloadingPathIssued = false;
        supplyPathIssued = false;
        killAuraWasOnBefore = false;
        supplyFailCount = 0;
        boxSwapWaiting = false;
        boxSwapTicks = 0;
        boxSwapCount = 0;
        noContainerTicks = 0;
        playerWasDead = false;
        lowSpeedTicks = 0;
        waterStuckTicks = 0;
        waterEscapeActive = false;
        waterEscapeTicks = 0;
        lastPosSample = BlockPos.ZERO;
        noMoveTicks = 0;
        stuckResetCount = 0;
        pickupTarget = null;
        pickupTimeout = 0;
        pickupBlacklist.clear();
        lavaEscapeActive = false;
        lavaEscapeTicks = 0;
        diedInLava = false;
    }

    /**
     * 关模块 / 强制停机时的退场清理，替代 {@link #reset()}。
     *
     * <p>{@link #reset()} 直接把状态置回 IDLE，<b>不经过 {@code onStateExit}</b>（旧项目 {@code reset}
     * 亦如此，属旧实现遗留）。后果是三处副作用被跳过，玩家关掉模块后仍留在世界里：</p>
     * <ul>
     *   <li>在 <b>REPAIR</b> 态：{@code stopRepairCombat()} 不执行 → 我们开的杀戮光环一直开着；
     *       {@code restoreHotbar()} 不执行 → 镐子／武器留在副手（{@code savedToolSlot} 被清成 -1，已无法回放）。</li>
     *   <li>在 <b>EATING</b> 态：自动进食按下的右键不释放（释放点全在 {@code tickEating} 内部）。</li>
     *   <li>在 <b>物流</b>态（卸货 / 补给 / 修补）：Baritone 全局 {@code allowBreak} 停在物流值上。</li>
     * </ul>
     *
     * <p>顺序：先补做退出副作用（此时 {@code killAuraWasOnBefore} 等字段还在，能正确判断该不该还原），
     * 再 {@link #reset()}。</p>
     */
    public void shutdown() {
        onStateExit(state);
        mc.options.keyUse.setDown(false);
        if (state == MinerState.UNLOADING || state == MinerState.SUPPLY || state == MinerState.REPAIR) {
            module.getBaritone().updateSetting("allowBreak", module.getAllowBreak());
        }
        reset();
    }

    /**
     * 传送指令「生效判定」的等待窗口（tick）。
     *
     * <p>统一取设置里的「传送等待时长」（{@code GET_WILD} 与 {@code ServerCommandRunner} 的
     * {@code maxWaitTicks} 都是这个口径），下限 40 tick 与旧实现写死的 2 秒一致。
     * 旧实现三处写死 40 tick，服务器排队传送（RTP 冷却、多人在线）时指令还没落地就被判「指令无效」
     * 并直接停机，玩家看到的是模块莫名关闭。</p>
     */
    private int teleportEffectiveTicks() {
        return Math.max(40, module.getTeleportDelay() * 20);
    }

    /** 每刻主干（旧 {@code tick}，{@code :172-201} 逐字） */
    public void tick() {
        if (mc.player == null || mc.level == null) return;

        // 死亡事件拦截（最高优先级）
        if (mc.player.isDeadOrDying() && !playerWasDead) {
            playerWasDead = true;
            transitionTo(MinerState.DEATH_HANDLING);
            return;
        }

        // 复活检测
        if (playerWasDead && !mc.player.isDeadOrDying()) {
            playerWasDead = false;
            transitionTo(MinerState.RESPAWN_WAIT);
        }

        stateTick++;

        switch (state) {
            case IDLE -> tickIdle();
            case GO_WILD -> tickGoWild();
            case MINING -> tickMining();
            case UNLOADING -> tickUnloading();
            case SUPPLY -> tickSupply();
            case EATING -> tickEating();
            case REPAIR -> tickRepair();
            case DEATH_HANDLING -> tickDeathHandling();
            case RESPAWN_WAIT -> tickRespawnWait();
        }
    }

    /** 状态切换（旧 {@code transitionTo}，{@code :203-218} 逐字） */
    private void transitionTo(MinerState newState) {
        if (state == newState) return;

        // 状态退出清理
        onStateExit(state);

        MinerState oldState = state;
        state = newState;
        stateTick = 0;

        // 状态转换播报
        broadcastStateTransition(oldState, newState);

        // 状态进入初始化
        onStateEnter(newState);
    }

    /** 状态进入副作用（旧 {@code onStateEnter}，{@code :221-251} 逐条一致） */
    private void onStateEnter(MinerState newState) {
        if (newState == MinerState.UNLOADING || newState == MinerState.SUPPLY || newState == MinerState.REPAIR || newState == MinerState.DEATH_HANDLING) {
            module.getContainer().closeContainer();
            module.getBaritone().stop();
        }

        // 物流寻路（卸货/补给/修补）用独立开关控制是否破坏方块；其余状态一律回到全局破坏设置。
        //
        // 旧实现只在进入 MINING 时恢复全局值，于是 GO_WILD / EATING / DEATH_HANDLING 期间 Baritone 一直
        // 停在物流值上；玩家在物流态关模块时全局 allowBreak 会被永久改掉（关模块不会回到挖矿态来恢复）。
        module.getBaritone().updateSetting("allowBreak", switch (newState) {
            case UNLOADING, SUPPLY, REPAIR -> module.isLogisticsBreakBlocks();
            default -> module.getAllowBreak();
        });
        if (newState == MinerState.MINING) {
            // 新一轮挖矿开始，清零 mine 退出重启计数
            mineRestartCount = 0;
            mineRestartCooldown = 0;
            // 下界挖矿自动开启岩浆透视并提示一次（旧 :234-238）
            if (module.isInNether() && !module.isLavaEspEnabled()) {
                module.enableLavaEsp();
                module.info("§e⚠ 检测到下界挖矿 §8▸ 已自动开启岩浆透视");
            }
        }

        if (newState == MinerState.REPAIR) {
            repairPathIssued = false;
            repairSwapAttempts = 0;
            repairSwapRequestedTick = -1;
            repairMode = false;
            savedToolSlot = -1;
            savedWeaponSlot = -1;
        }
    }

    /** 状态退出副作用（旧 {@code onStateExit}，{@code :253-261} 逐字） */
    private void onStateExit(MinerState oldState) {
        if (oldState == MinerState.MINING) {
            module.getBaritone().stop();
        }
        if (oldState == MinerState.REPAIR) {
            stopRepairCombat();
            restoreHotbar();
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  状态处理
    // ═══════════════════════════════════════════════════════════════════

    /** 旧 {@code tickIdle}，{@code :267-270} 逐字 */
    private void tickIdle() {
        // 启动时立即进入去野外
        transitionTo(MinerState.GO_WILD);
    }

    /** 旧 {@code tickGoWild}，{@code :272-320} 逐字 */
    private void tickGoWild() {
        ServerCommandRunner cmdMgr = module.getCmdManager();

        // RTP 冷却等待中：等服务器冷却结束再重发指令（避免冷却期空发失败）
        if (teleportCooldownTicks > 0) {
            teleportCooldownTicks--;
            if (teleportCooldownTicks == 0) {
                stateTick = 0; // 冷却结束，下一 tick 重新从阶段 1 发指令
            }
            return;
        }

        // 阶段 1：记录传送前位置并发送传送命令
        if (stateTick == 1) {
            // 只有首次尝试需要记录起点，重试时起点不变（人还在原地）
            if (teleportRetries == 0) teleportStartPos = mc.player.blockPosition();
            cmdMgr.executeCommand(module.getWildCommand());
            teleportTimeout = module.getTeleportDelay() * 20; // 转换为tick
            return;
        }

        // 阶段 2：等待命令执行完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 3：检测传送是否成功（原地没动才算失败；RTP 插件可能只移几十格，同样算成功）
        BlockPos currentPos = mc.player.blockPosition();
        double distance = Math.sqrt(currentPos.distSqr(teleportStartPos));

        if (distance > 2) {
            teleportRetries = 0;
            module.getSoundNotifier().notifyTeleportSuccess();
            transitionTo(MinerState.MINING);
            return;
        }

        // 阶段 4：超时仍在原地 → 进入 RTP 冷却等待，冷却结束后重发（最多 3 次），仍失败才停机
        if (stateTick > teleportTimeout) {
            if (teleportRetries < MAX_TELEPORT_RETRIES) {
                teleportRetries++;
                teleportCooldownTicks = module.getRtpCooldown() * 20;
                module.error("§e⚠ 传送未生效 §8▸ 等待 " + module.getRtpCooldown() + " 秒冷却后重试 " + teleportRetries + "/" + MAX_TELEPORT_RETRIES);
                return;
            }
            module.error("§c✗ 传送失败 §8▸ 已重试 " + MAX_TELEPORT_RETRIES + " 次，自动停止挖矿");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        }
    }

    /** 旧 {@code tickMining}，{@code :322-607}（种子模式分支整段留白，其余逐条一致） */
    private void tickMining() {
        // 阶段 1：启动采集引擎（普通模式启动 Baritone mine；旧 :326-340）
        if (stateTick == 1) {
            lowSpeedTicks = 0;
            module.getBaritone().startMining(module.getTargetBlocks());
            // 播放开始挖矿音效
            module.getSoundNotifier().notifyMiningStart();
            return;
        }

        // 耐久预警（旧 :343）
        checkToolDurabilityWarning();

        // 优先级 1：死亡检测（已在 tick() 最开始处理）

        // 优先级 2：耐久检测（镐子必备；镐/铲/斧/锄/剑任一工具低于阈值都触发修复，旧 :348-367）
        ItemStack tool = findMiningPickaxe();
        if (tool.isEmpty()) {
            module.getBaritone().stop();
            module.error("§c✗ 缺少镐子 §8▸ 自动挖矿已停止");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }
        if (findDamagedToolSlot() != -1) {
            ItemStack damaged = findDamagedTool();
            // 挂机修复依赖经验修补附魔（打怪掉经验修工具），没有则去修复点也白挂到超时，直接停机
            if (!hasMending(damaged)) {
                module.getBaritone().stop();
                module.error("§c✗ " + toolName(damaged) + "无经验修补附魔 §8▸ 无法自动修复，请换有经验修补的工具");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }
            module.getSoundNotifier().notifyLowDurability();
            transitionTo(MinerState.REPAIR);
            return;
        }

        // 优先级 2.5：饱食度检测（低于15时暂停进食；背包没白名单食物则直接去补给，旧 :369-380）
        FoodData foodData = mc.player.getFoodData();
        if (foodData.getFoodLevel() < 15) {
            module.getBaritone().stop();
            if (hasFoodToEat()) {
                transitionTo(MinerState.EATING);
            } else {
                // 没吃的还进进食状态会死循环（超时→MINING→又饿→又进食），必须转补给
                transitionTo(MinerState.SUPPLY);
            }
            return;
        }

        // 优先级 3：食物不足检测（检查背包食物组数，旧 :382-387）
        if (countFoodStacks() < module.getHungerThreshold()) {
            module.getSoundNotifier().notifyLowFood();
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 优先级 4：满载检测（旧 :389-394）
        int oreStacks = countOreStacks();
        if (oreStacks >= module.getUnloadThreshold()) {
            transitionTo(MinerState.UNLOADING);
            return;
        }

        // 自动捡起目标矿掉落物（漏捡补偿；背包未满时才捡，旧 :396-397）
        if (tryPickupNearbyOre()) return;

        // 普通模式自愈：仅当 Baritone mine 进程意外退出时才重启。（旧 :403-428，种子分支留白）
        // 正常挖掘中并非时刻处于寻路状态（扫描/破坏时 isPathing 为 false），
        // 不能一见「没在寻路」就重启，否则每几秒重扫一遍矿、打断破坏进度。
        if (stateTick > 120) {
            if (mineRestartCooldown > 0) {
                // 重启后冷却：等 mine 进程启动，避免启动延迟被误判成「又退出」
                mineRestartCooldown--;
            } else if (!module.getBaritone().isPathing() && !module.getBaritone().isMiningActive()) {
                // 目标解析不出方块时重启永远不会成功：直接停机，否则 3 次重启→GO_WILD→回 MINING（计数清零）→
                // 再 3 次重启，变成永不停止的 RTP 循环 + 「挖矿进程已退出」刷屏
                if (module.getTargetBlocks().isEmpty()) {
                    module.error("§c✗ 采掘目标解析不出方块 §8▸ 请在配置页重新选择目标");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                // mine 进程真的退出了：重启并播报；连续 3 次仍退出判定附近无矿，RTP 换区
                mineRestartCount++;
                if (mineRestartCount >= 3) {
                    module.info("§e⚠ 附近目标矿已挖完 §8▸ 重新前往野外换区域");
                    mineRestartCount = 0;
                    mineRestartCooldown = 0;
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
                module.info("§e⚠ 挖矿进程已退出 §8▸ 正在重启（" + mineRestartCount + "/3）");
                module.getBaritone().startMining(module.getTargetBlocks());
                mineRestartCooldown = 100; // 重启后等 5 秒再判断
            } else {
                // 正在正常挖掘，清零重启计数
                mineRestartCount = 0;
            }
        }

        // 卡死检测（两种模式共用）：速度监测，3 分钟持续低速判定卡死（旧 :430-449）
        double currentSpeed = Math.sqrt(
            mc.player.getDeltaMovement().x * mc.player.getDeltaMovement().x +
            mc.player.getDeltaMovement().z * mc.player.getDeltaMovement().z
        );

        if (currentSpeed < MIN_SPEED_THRESHOLD) {
            lowSpeedTicks++;
        } else {
            lowSpeedTicks = 0;
        }

        if (lowSpeedTicks > STUCK_TIME_THRESHOLD) {
            module.error("§c✗ 检测到卡死（速度过低）§8▸ 重新前往野外");
            module.getSoundNotifier().notifyStuck();
            module.getBaritone().stop();
            lowSpeedTicks = 0;
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 原地抖动卡死检测（每 10 秒采样位置，旧 :451-490）：Baritone 在点位附近原地抖动的通病，
        // 抖动时速度不为 0 抓不到，需按位移判断。连续 3 分钟位移<2格判定卡死。
        if (stateTick % 200 == 0) {
            BlockPos curPos = mc.player.blockPosition();
            if (!lastPosSample.equals(BlockPos.ZERO) && curPos.distSqr(lastPosSample) < 4.0) {
                noMoveTicks += 200;
            } else {
                noMoveTicks = 0;
                stuckResetCount = 0; // 玩家在正常移动，重置连续卡死计数
            }
            lastPosSample = curPos;
        }

        if (noMoveTicks > NO_MOVE_THRESHOLD) {
            module.getSoundNotifier().notifyStuck();
            module.getBaritone().stop();
            noMoveTicks = 0;
            stuckResetCount++;
            if (stuckResetCount >= 2) {
                // 连续两次抖动卡死，重置采掘目标也脱不了困，才传送去野外
                stuckResetCount = 0;
                module.error("§c✗ 原地抖动卡死（连续两次）§8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
            } else {
                // 先重置采掘目标脱困（换一个矿点），不急着传送
                module.info("§e⚠ 原地抖动卡死 §8▸ 重置采掘目标脱困");
                module.getBaritone().startMining(module.getTargetBlocks());
            }
            return;
        }

        // 水中卡死：先寻路到最近陆地脱困，避免直接 RTP（水中抖动会取消服务器传送读条，旧 :492-551）
        if (mc.player.isInWater()) {
            if (currentSpeed < MIN_SPEED_THRESHOLD) {
                waterStuckTicks++;
            } else {
                waterStuckTicks = 0;
                if (waterEscapeActive) waterEscapeActive = false;
            }

            if (waterStuckTicks > 600 && !waterEscapeActive) {
                module.getSoundNotifier().notifyStuck();
                module.getBaritone().stop();
                BlockPos land = findNearestLand();
                if (land != null) {
                    var baritone = module.getBaritone().getBaritoneInstance();
                    if (baritone != null) {
                        baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(land));
                        waterEscapeActive = true;
                        waterEscapeTicks = 0;
                        module.info("§e⚠ 水中卡死 §8▸ 寻路到最近陆地脱困");
                    } else {
                        waterStuckTicks = 0;
                        transitionTo(MinerState.GO_WILD);
                        return;
                    }
                } else {
                    module.error("§c✗ 水中卡死且周围无陆地 §8▸ 重新前往野外");
                    waterStuckTicks = 0;
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
            }
        } else {
            waterStuckTicks = 0;
            waterEscapeActive = false;
        }

        // 水中脱困推进：已上岸则恢复挖矿；超时仍未脱困则 RTP 兜底（旧 :529-551）
        if (waterEscapeActive) {
            waterEscapeTicks++;
            if (!mc.player.isInWater()) {
                waterEscapeActive = false;
                waterEscapeTicks = 0;
                module.getBaritone().stop();
                module.info("§a✓ 已脱离水域 §8▸ 继续挖矿");
                module.getBaritone().startMining(module.getTargetBlocks());
                return;
            }
            if (waterEscapeTicks > 400) { // 20 秒仍未脱困，RTP 兜底
                waterEscapeActive = false;
                waterEscapeTicks = 0;
                module.getBaritone().stop();
                module.error("§c✗ 水中脱困超时 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            return; // 脱困寻路中，暂停其它挖矿逻辑
        }

        // 岩浆避险：脚下/相邻有岩浆就停止挖矿并寻路到安全位置，避免被烧/掉进去（旧 :553-574）
        if (!lavaEscapeActive && stateTick % 10 == 0 && hasLavaNear(1)) {
            module.getBaritone().stop();
            BlockPos safe = findNearestSafeSpot();
            if (safe != null) {
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone != null) {
                    baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(safe));
                    lavaEscapeActive = true;
                    lavaEscapeTicks = 0;
                    module.info("§e⚠ 附近检测到岩浆 §8▸ 寻路到安全位置");
                } else {
                    module.error("§c✗ Baritone 未加载 §8▸ 重新前往野外");
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
            } else {
                module.error("§c✗ 附近全是岩浆 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
        }

        // 岩浆脱困推进：已远离岩浆则恢复挖矿；超时仍未脱困则 RTP 兜底（旧 :576-598）
        if (lavaEscapeActive) {
            lavaEscapeTicks++;
            if (!hasLavaNear(1)) {
                lavaEscapeActive = false;
                lavaEscapeTicks = 0;
                module.getBaritone().stop();
                module.info("§a✓ 已远离岩浆 §8▸ 继续挖矿");
                module.getBaritone().startMining(module.getTargetBlocks());
                return;
            }
            if (lavaEscapeTicks > 400) { // 20 秒仍未脱困，RTP 兜底
                lavaEscapeActive = false;
                lavaEscapeTicks = 0;
                module.getBaritone().stop();
                module.error("§c✗ 岩浆脱困超时 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            return; // 脱困寻路中，暂停其它挖矿逻辑
        }

        // Baritone 卡死检测（普通模式专用，种子模式由 seedPathRetries 兜底——种子模式本轮留白，旧 :600-606）
        if (stateTick > 6000 && stateTick % 1200 == 0) {
            if (module.getBaritone().isStuck()) {
                module.getBaritone().stop();
                transitionTo(MinerState.GO_WILD);
            }
        }
    }

    /** 旧 {@code findNearestLand}，{@code :704-725} 逐字 */
    private BlockPos findNearestLand() {
        if (mc.player == null || mc.level == null) return null;
        BlockPos feet = mc.player.blockPosition();
        for (int r = 0; r <= 40; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue; // 切比雪夫环，只扫当前半径一圈
                    for (int dy = -2; dy <= 2; dy++) {
                        BlockPos p = feet.offset(dx, dy, dz);
                        // 该格无流体、非空气、上方可站（空气且无流体）
                        if (mc.level.getFluidState(p).isEmpty()
                            && !mc.level.getBlockState(p).isAir()
                            && mc.level.getBlockState(p.above()).isAir()
                            && mc.level.getFluidState(p.above()).isEmpty()) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    /** 旧 {@code hasLavaNear}，{@code :728-741} 逐字 */
    private boolean hasLavaNear(int radius) {
        if (mc.player == null || mc.level == null) return false;
        BlockPos c = mc.player.blockPosition();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (mc.level.getBlockState(c.offset(dx, dy, dz)).getBlock() == Blocks.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** 旧 {@code findNearestSafeSpot}，{@code :744-766} 逐字 */
    private BlockPos findNearestSafeSpot() {
        if (mc.player == null || mc.level == null) return null;
        BlockPos feet = mc.player.blockPosition();
        for (int r = 1; r <= 16; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;
                    for (int dy = -2; dy <= 2; dy++) {
                        BlockPos p = feet.offset(dx, dy, dz);
                        // 脚下固体、身体空气、脚/身/头三格都无岩浆
                        if (mc.level.getBlockState(p).getBlock() != Blocks.LAVA
                            && !mc.level.getBlockState(p).isAir()
                            && mc.level.getBlockState(p.above()).isAir()
                            && mc.level.getBlockState(p.above()).getBlock() != Blocks.LAVA
                            && mc.level.getBlockState(p.above(2)).getBlock() != Blocks.LAVA) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 扫描快捷栏，返回挖掘指定方块最快的工具槽位（无快于空手的工具返回 -1）。
     *
     * <p>旧 {@code findBestToolSlot}，{@code :837-850} 逐字。旧项目唯一调用方是种子模式的
     * {@code ensureBestToolForSeedTarget}（本轮留白），本方法按规格保留备用。</p>
     */
    private int findBestToolSlot(BlockState state) {
        int best = -1;
        double bestSpeed = 1.0; // 空手破坏速度基准
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            double speed = stack.getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                best = i;
            }
        }
        return best;
    }

    /** 旧 {@code findNearbyOreDrop}，{@code :871-894} 逐字 */
    private ItemEntity findNearbyOreDrop(double radius) {
        if (mc.level == null || mc.player == null) return null;
        Set<String> acceptIds = module.isSilkTouchMode()
            ? module.getTargetBlockIds()
            : Set.of(module.getTargetDropItemId());

        List<ItemEntity> items = mc.level.getEntitiesOfClass(
            ItemEntity.class, mc.player.getBoundingBox().inflate(radius), e -> true);
        ItemEntity nearest = null;
        double best = radius * radius;
        for (ItemEntity item : items) {
            ItemStack stack = item.getItem();
            if (stack.isEmpty()) continue;
            if (pickupBlacklist.contains(item.blockPosition())) continue; // 卡角落捡不起来的位置，跳过
            String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (!acceptIds.contains(id)) continue;
            double d = item.distanceToSqr(mc.player);
            if (d < best) {
                best = d;
                nearest = item;
            }
        }
        return nearest;
    }

    /**
     * 自动捡起附近掉落的目标矿（漏捡补偿）。
     * 返回 true 表示正在捡取（暂停本帧挖矿逻辑），false 表示未在捡取。
     *
     * <p>旧 {@code tryPickupNearbyOre}，{@code :900-936}；旧 {@code isSeedMiningEnabled()} 分支留白，
     * 放弃捡取后一律恢复普通模式挖掘。</p>
     */
    private boolean tryPickupNearbyOre() {
        if (mc.player == null || mc.level == null) return false;
        // 背包已满时不捡，否则捡不起来反而卡住（掉落物一直存在→反复寻路→原地打转）
        if (countOreStacks() >= module.getUnloadThreshold()) return false;

        if (pickupTarget == null) {
            // 每 10 tick 扫一次，避免每帧全量扫实体
            if (stateTick % 10 != 0) return false;
            ItemEntity drop = findNearbyOreDrop(6.0);
            if (drop == null) return false;
            pickupTarget = drop;
            pickupTimeout = 0;
            module.getBaritone().stop();
            var baritone = module.getBaritone().getBaritoneInstance();
            if (baritone != null) {
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(drop.blockPosition()));
            }
            return true;
        }

        // 已有捡取目标：消失/超时/太远 → 放弃并恢复挖矿
        pickupTimeout++;
        if (pickupTarget.isRemoved() || !pickupTarget.isAlive()
            || pickupTimeout > 300 || pickupTarget.distanceTo(mc.player) > 16) {
            // 超时/太远说明掉落物卡角落捡不起来，记黑名单避免反复寻路捡同一个
            if (pickupTimeout > 300 || pickupTarget.distanceTo(mc.player) > 16) {
                addPickupBlacklist(pickupTarget.blockPosition());
            }
            pickupTarget = null;
            module.getBaritone().stop();
            module.getBaritone().startMining(module.getTargetBlocks());
            return false;
        }
        return true;
    }

    /** 旧 {@code addPickupBlacklist}，{@code :939-944} 逐字 */
    private void addPickupBlacklist(BlockPos pos) {
        pickupBlacklist.add(pos);
        if (pickupBlacklist.size() > MAX_PICKUP_BLACKLIST) {
            pickupBlacklist.clear();
        }
    }

    /** 旧 {@code checkToolDurabilityWarning}，{@code :946-989} 逐字 */
    private void checkToolDurabilityWarning() {
        if (mc.player == null) return;

        // 找出耐久最低的可修复工具（镐/铲/斧/锄/剑，含副手），预警提示跟修复触发用同一套判定
        ItemStack lowest = ItemStack.EMPTY;
        int lowestRemaining = Integer.MAX_VALUE;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isRepairableTool(stack)) continue;
            Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
            Integer damage = stack.get(DataComponents.DAMAGE);
            if (maxDamage == null || damage == null) continue;
            int remaining = maxDamage - damage;
            if (remaining < lowestRemaining) {
                lowestRemaining = remaining;
                lowest = stack;
            }
        }

        ItemStack offhand = mc.player.getOffhandItem();
        if (isRepairableTool(offhand)) {
            Integer maxDamage = offhand.get(DataComponents.MAX_DAMAGE);
            Integer damage = offhand.get(DataComponents.DAMAGE);
            if (maxDamage != null && damage != null) {
                int remaining = maxDamage - damage;
                if (remaining < lowestRemaining) {
                    lowestRemaining = remaining;
                    lowest = offhand;
                }
            }
        }

        if (lowest.isEmpty()) return;

        int threshold = module.getDurabilityThreshold();
        // 耐久进入预警区（阈值 1.5 倍以内但未触发修复），每 30 秒提醒一次
        if (lowestRemaining <= threshold * 1.5 && lowestRemaining > threshold) {
            if (stateTick % 600 == 0) {
                module.info("§e⚠ " + toolName(lowest) + " 剩余耐久 " + lowestRemaining + " §8▸ 即将触发修复流程");
                module.getSoundNotifier().notifyLowDurability();
            }
        }
    }

    /** 旧 {@code toolName}，{@code :992-1001} 逐字 */
    private String toolName(ItemStack stack) {
        if (stack.isEmpty()) return "工具";
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        if (id.endsWith("_pickaxe")) return "镐子";
        if (id.endsWith("_shovel")) return "铲子";
        if (id.endsWith("_axe")) return "斧头";
        if (id.endsWith("_hoe")) return "锄头";
        if (id.endsWith("_sword")) return "剑";
        return "工具";
    }

    /**
     * 是否已站到容器跟前（可开箱）。
     *
     * <p>旧 {@code isAdjacentTo}，{@code :1011-1017} 逐字：切比雪夫距离 ≤ 1（含对角格）。
     * Baritone {@code GoalGetToBlock} 的合法终点就是切比雪夫邻域，玩家斜着接近时可能停在对角格。
     * 若这里用曼哈顿=1，Baritone 认为已到达不再移动，判定却永远不满足 → 开箱死锁。
     * 与 {@code MiningContainer.openContainer} 的开箱邻域判据必须同一套。</p>
     */
    private boolean isAdjacentTo(BlockPos target) {
        BlockPos player = mc.player.blockPosition();
        int dx = Math.abs(player.getX() - target.getX());
        int dy = Math.abs(player.getY() - target.getY());
        int dz = Math.abs(player.getZ() - target.getZ());
        return dx <= 1 && dy <= 1 && dz <= 1 && (dx | dy | dz) != 0;
    }

    /**
     * 智能识别矿物箱位置的潜影盒颜色，返回「§颜色代码 + 中文色名 + 潜影盒」，用于换盒公屏提示。
     * 16 色潜影盒都是独立方块变体，颜色编码在方块 ID 里（如 white_shulker_box / purple_shulker_box）。
     *
     * <p>旧 {@code shulkerColorLabel}，{@code :1023-1051} 逐字。</p>
     */
    private String shulkerColorLabel(BlockPos pos) {
        if (mc.level == null || pos == null) return "§7潜影盒";
        Block block = mc.level.getBlockState(pos).getBlock();
        String id = BuiltInRegistries.BLOCK.getKey(block).getPath();
        if (id.equals("shulker_box")) return "§7默认潜影盒";
        if (!id.endsWith("_shulker_box")) return "§7容器";

        String key = id.substring(0, id.length() - "_shulker_box".length());
        String color = switch (key) {
            case "white" -> "§f白色";
            case "orange" -> "§6橙色";
            case "magenta" -> "§d品红";
            case "light_blue" -> "§b淡蓝";
            case "yellow" -> "§e黄色";
            case "lime" -> "§a黄绿";
            case "pink" -> "§d粉色";
            case "gray" -> "§8灰色";
            case "light_gray" -> "§7淡灰";
            case "cyan" -> "§b青色";
            case "purple" -> "§5紫色";
            case "blue" -> "§9蓝色";
            case "brown" -> "§6棕色";
            case "green" -> "§2绿色";
            case "red" -> "§c红色";
            case "black" -> "§0黑色";
            default -> "§7" + key;
        };
        return color + "潜影盒";
    }

    /** 旧 {@code tickUnloading}，{@code :1053-1191} 逐字（点位来源换成 {@code MiningPointStore}） */
    private void tickUnloading() {
        ServerCommandRunner cmdMgr = module.getCmdManager();
        MiningPoint mineralChest = module.pointStore().get(MiningPointType.MINERAL);

        if (stateTick == 1) {
            unloadingPathIssued = false;
            boxSwapCount = 0;
            noContainerTicks = 0;
        }

        if (mineralChest == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 潜影盒打包机换盒等待：关箱后等 2 秒让打包机推盒+放新盒，再重走开箱流程
        if (boxSwapWaiting) {
            boxSwapTicks++;
            if (boxSwapTicks >= BOX_SWAP_WAIT_TICKS) {
                boxSwapWaiting = false;
                boxSwapTicks = 0;
                module.info("§7换盒完成 §8▸ 重新打开 " + shulkerColorLabel(mineralChest.pos()) + " §7继续卸货...");
            } else {
                return; // 继续等打包机换盒
            }
        }

        // 阶段 1：传送到矿物箱（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            cmdMgr.executeCommand(module.getUnloadCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机而非继续走到箱子）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 卸货指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到箱子相邻的一格（Baritone；寻路中断每 0.5 秒自动重发，不再一断就干等超时）
        if (!isAdjacentTo(mineralChest.pos())) {
            if (!unloadingPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                unloadingPathIssued = true;
                // 发起/重发 Baritone 走到箱子
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到矿物箱，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(mineralChest.pos()));
            }

            if (stateTick > PATH_TIMEOUT_TICKS) {
                module.getBaritone().stop();
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }
            return;
        }

        // 阶段 4：停止寻路，面向箱子后开箱（避免背对开箱）
        module.getBaritone().stop();
        faceBlock(mineralChest.pos());

        if (mc.screen != null && !(mc.screen instanceof AbstractContainerScreen<?>)) {
            if (stateTick % 20 == 0) module.getContainer().closeContainer();
            return;
        }
        if (!module.getContainer().isContainerOpen()) {
            // 标点位置无容器（潜影盒被推走后未放新盒）→ 累计计时，超时停机提示
            if (!module.getContainer().isContainerAt(mineralChest.pos())) {
                noContainerTicks++;
                if (noContainerTicks > NO_CONTAINER_TIMEOUT) {
                    module.error("§c✗ 矿物箱位置已无容器 §8▸ 自动停止模块");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
            } else {
                noContainerTicks = 0;
            }
            module.getContainer().openContainer(mineralChest.pos());
            return;
        }

        // 阶段 5：持续倒货，直到目标矿石全部转移
        module.getContainer().depositOres();

        // 背包目标矿放完 → 关箱走人（放完才 RTP）
        if (!module.getContainer().hasOreInInventory()) {
            module.getContainer().closeContainer();
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 箱子满但背包还有矿
        if (module.getContainer().isContainerFull()) {
            if (module.isShulkerPackerEnabled()) {
                // 打包机模式：关箱等打包机换盒，再重开新盒继续放
                boxSwapCount++;
                if (boxSwapCount > MAX_BOX_SWAPS) {
                    module.error("§c✗ 潜影盒换盒超过 " + MAX_BOX_SWAPS + " 次仍未放完 §8▸ 自动停止");
                    module.getContainer().closeContainer();
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                module.getContainer().closeContainer();
                boxSwapWaiting = true;
                boxSwapTicks = 0;
                module.info("§e⚠ " + shulkerColorLabel(mineralChest.pos()) + " §e已满 §8▸ 等打包机换盒后重开继续放");
                return;
            }
            // 普通箱子模式：箱子满了放不下 → 停止模块并提示，避免空塞后带矿 RTP 跑掉
            module.getContainer().closeContainer();
            module.error("§c✗ 矿物容器已满 §8▸ 无法继续卸货，自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 超时保护（非打包机模式兜底）
        if (!module.isShulkerPackerEnabled() && stateTick > 400) {
            module.getContainer().closeContainer();
            transitionTo(MinerState.GO_WILD);
        }
    }

    /** 旧 {@code tickSupply}，{@code :1193-1283} 逐字（点位来源换成 {@code MiningPointStore}） */
    private void tickSupply() {
        ServerCommandRunner cmdMgr = module.getCmdManager();
        MiningPoint foodChest = module.pointStore().get(MiningPointType.FOOD);
        if (stateTick == 1) {
            supplyPathIssued = false;
        }

        if (foodChest == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 1：传送到补给点（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            cmdMgr.executeCommand(module.getSupplyCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 补给指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到箱子相邻的一格（Baritone；寻路中断每 0.5 秒自动重发）
        if (!isAdjacentTo(foodChest.pos())) {
            if (!supplyPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                supplyPathIssued = true;
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到食物箱，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(foodChest.pos()));
            }

            if (stateTick > 1200) {
                module.getBaritone().stop();
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            }
            return;
        }

        // 阶段 4：停止寻路，面向箱子后开箱取食物
        module.getBaritone().stop();
        faceBlock(foodChest.pos());

        if (!module.getContainer().isContainerOpen()) {
            // 屏幕被其它界面占用时定期强制关闭，避免永远打不开箱子
            if (mc.screen instanceof AbstractContainerScreen<?>) return;
            if (mc.screen != null && stateTick % 20 == 0) module.getContainer().closeContainer();
            module.getContainer().openContainer(foodChest.pos());
            return;
        }

        // 阶段 5：取食物，结束后返回矿区重新前往野外，避免在食物箱原地挖矿
        boolean taken = module.getContainer().withdrawFood();
        if (taken || stateTick > 400) {
            module.getContainer().closeContainer();
            // 补给成功判定必须与触发条件一致（按白名单食物数量是否达标），
            // 不能用 hasFoodToEat()：背包哪怕只剩 1 块食物也会被判成功，
            // 导致「食物不足→补给→箱空→回来→又不足」无限 RTP 死循环。
            if (countFoodStacks() >= module.getHungerThreshold()) {
                supplyFailCount = 0;
                module.info("§a✓ 食物已补充 §8▸ 返回矿区");
                transitionTo(MinerState.GO_WILD);
            } else if (supplyFailCount++ >= 1) {
                // 连续 2 次补给空手：箱子没白名单食物，再循环也只是空转 RTP，停机让玩家补货
                module.error("§c✗ 补给箱连续 2 次无白名单食物 §8▸ 自动停止（请补充食物箱）");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            } else {
                module.warning("§e⚠ 补给箱内无白名单食物 §8▸ 返回矿区继续挖（饥饿时仍会再试一次）");
                transitionTo(MinerState.GO_WILD);
            }
        }
    }

    /** 旧 {@code tickEating}，{@code :1285-1323} 逐字 */
    private void tickEating() {
        FoodData foodData = mc.player.getFoodData();

        // 检查饱食度是否回满（满值20）
        if (foodData.getFoodLevel() >= 20) {
            mc.options.keyUse.setDown(false); // 释放右键
            module.info("§a✓ 饱食度已恢复 §8▸ 继续挖矿");
            module.getSoundNotifier().notifyMiningStart();
            transitionTo(MinerState.MINING);
            return;
        }

        // 食物耗尽（拿到手上的最后一块也吃完了）：别傻等 2 分钟超时，直接去补给
        if (!hasFoodToEat()) {
            mc.options.keyUse.setDown(false);
            module.info("§6⚠ 食物已吃完 §8▸ 前往补给点");
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 每 tick 都尝试进食：autoEat 内部幂等，未在进食时触发一次 useItem，
        // 已在使用中则仅保持按键。窗口失焦/开 GUI 时按键会被吞，靠 useItem 兜底。
        module.getContainer().autoEat();

        // 每 2 秒播报一次进食进度（避免刷屏）
        if (stateTick % 40 == 0) {
            ItemStack handItem = mc.player.getMainHandItem();
            String foodName = handItem.isEmpty() ? "食物" : handItem.getHoverName().getString();
            module.info("§e进食中 §8▸ " + foodName + " §7(饱食度: " + foodData.getFoodLevel() + "/20)");
        }

        // 超时保护：2分钟还没吃饱就放弃，回到挖矿
        if (stateTick > 2400) {
            mc.options.keyUse.setDown(false);
            module.warning("§c⚠ 进食超时 §8▸ 放弃等待");
            module.getSoundNotifier().notifyLowFood();
            transitionTo(MinerState.MINING);
        }
    }

    /** 旧 {@code tickRepair}，{@code :1325-1446} 逐字（自动换槽换成 26.1.2 原语） */
    private void tickRepair() {
        MiningPoint afkPoint = module.pointStore().get(MiningPointType.AFK);

        if (afkPoint == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        ServerCommandRunner cmdMgr = module.getCmdManager();

        // 阶段 1：传送到挂机点（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            cmdMgr.executeCommand(module.getAFKCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 挂机修补指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到挂机点（Baritone；寻路中断每 0.5 秒自动重发）
        if (!mc.player.blockPosition().closerThan(afkPoint.pos(), 3.0)) {
            if (!repairPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                repairPathIssued = true;
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到挂机点，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(afkPoint.pos()));
            }

            if (stateTick > 1200) {
                module.getBaritone().stop();
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            }
            return;
        }

        // 阶段 4：停止寻路，调整视角到记录的 Yaw/Pitch
        module.getBaritone().stop();

        if (stateTick < 100 && !isViewAligned(afkPoint.yaw(), afkPoint.pitch())) {
            smoothRotateTo(afkPoint.yaw(), afkPoint.pitch());
            return;
        }

        // 阶段 5：执行 Auto-Swap（只做一次）
        if (!repairMode) {
            if (repairSwapRequestedTick == -1) {
                int toolSlot = findDamagedToolSlot();
                if (toolSlot == -1) {
                    // 没有需要修的工具了（可能已被其它机制修好），直接返回矿区
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
                savedToolSlot = toolSlot;
                savedTool = toolSlot == -2 ? mc.player.getOffhandItem().copy()
                    : mc.player.getInventory().getItem(toolSlot).copy();
                savedWeaponSlot = findWeaponSlotInHotbar();
                repairSwapAttempts++;
                repairSwapRequestedTick = stateTick;
                if (toolSlot >= 0) swapWithOffhand(toolSlot);
                if (toolSlot == -2) {
                    repairMode = true;
                    startRepairCombat();
                }
                return;
            }

            ItemStack offhandTool = mc.player.getOffhandItem();
            boolean toolMoved = ItemStack.isSameItemSameComponents(savedTool, offhandTool);
            if (toolMoved) {
                if (savedWeaponSlot >= 0) {
                    selectHotbar(savedWeaponSlot);
                }
                repairMode = true;
                startRepairCombat();
                return;
            }

            // 工具还没到副手：最多等 15 tick（服务端到账通常 1~2 tick），超时才重发，
            // 避免像旧逻辑那样每 2 tick 就重发一次、把正在移动的光标打乱反而更慢。
            if (stateTick - repairSwapRequestedTick > 15) {
                repairSwapRequestedTick = -1;
                if (repairSwapAttempts >= 3) {
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                }
            }
            return;
        }

        // 阶段 6：持续监控耐久
        ItemStack currentTool = mc.player.getOffhandItem();
        if (currentTool.isEmpty() || isFullyRepaired(currentTool)) {
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 超时保护：10分钟没修满
        if (stateTick > 12000) {
            stopRepairCombat();
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        }
    }

    /** 旧 {@code isViewAligned}，{@code :1448-1461} 逐字（旧文件里第 1457/1458 行是同一句重复，此处只写一次） */
    private boolean isViewAligned(float targetYaw, float targetPitch) {
        if (mc.player == null) return false;

        float currentYaw = mc.player.getYRot();
        float currentPitch = mc.player.getXRot();

        float deltaYaw = Math.abs(targetYaw - currentYaw);
        float deltaPitch = Math.abs(targetPitch - currentPitch);

        if (deltaYaw > 180) deltaYaw = 360 - deltaYaw;

        return deltaYaw < 5.0f && deltaPitch < 5.0f;
    }

    /**
     * 旧 {@code tickDeathHandling}，{@code :1463-1485}。
     *
     * <p>差异（用户 2026-09-16 拍板）：旧 {@code tryMeteorAutoRespawn()}（切换第三方框架的自动重生模块）
     * 整段删除，自动重生由本项目 {@code feature/respawn/AutoRespawnModule}（默认开启）承担；
     * 因此旧 {@code :1472} 的「已调用流星自动重生模块」改成本项目的「已调用自动重生模块」，其余逐字不变。</p>
     */
    private void tickDeathHandling() {
        if (mc.player == null) return;

        // 阶段 1：首次进入时播报死亡并记录死亡点环境
        if (stateTick == 1) {
            module.getSoundNotifier().notifyDeath();
            // 死亡后玩家位置还停在死亡点，检测脚下是否有岩浆（在岩浆湖里死亡）
            diedInLava = hasLavaNear(1);
            module.error("§c✗ 已调用自动重生模块");
        }

        // 阶段 2：等待复活
        if (mc.player.isDeadOrDying()) {
            if (stateTick % 20 == 0) {
                mc.player.respawn(); // 后备方案
            }
            return;
        }

        // 阶段 3：复活完成，进入等待
        transitionTo(MinerState.RESPAWN_WAIT);
    }

    /** 旧 {@code tickRespawnWait}，{@code :1487-1514} 逐字 */
    private void tickRespawnWait() {
        ServerCommandRunner cmdMgr = module.getCmdManager();

        // 等待复活完成（玩家不再是死亡状态）
        if (mc.player.isDeadOrDying()) {
            return;
        }

        // 阶段 1：执行死亡重返指令
        if (stateTick == 1) {
            // 死亡点在岩浆里：不执行 back（会再回岩浆湖），直接去野外安全点
            if (diedInLava) {
                module.info("§e⚠ 死亡点有岩浆 §8▸ 跳过重返指令，直接前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            cmdMgr.executeCommand(module.getRespawnCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 3：返回野外恢复挖矿
        transitionTo(MinerState.GO_WILD);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  辅助方法
    // ═══════════════════════════════════════════════════════════════════

    /** 旧 {@code needsRepair}，{@code :1520-1530} 逐字 */
    private boolean needsRepair(ItemStack tool) {
        if (tool.isEmpty()) return false;
        Integer maxDamage = tool.get(DataComponents.MAX_DAMAGE);
        Integer damage = tool.get(DataComponents.DAMAGE);
        if (maxDamage == null || damage == null) return false;
        int remaining = maxDamage - damage;
        // 阈值不能超过工具最大耐久：否则满耐久（remaining == maxDamage）仍被判为
        // 「需修复」，修完回矿区又立刻触发修复，形成修复↔挖矿死循环。
        int effectiveThreshold = Math.min(module.getDurabilityThreshold(), maxDamage);
        return remaining < effectiveThreshold;
    }

    /** 旧 {@code isFullyRepaired}，{@code :1532-1536} 逐字 */
    private boolean isFullyRepaired(ItemStack tool) {
        if (tool.isEmpty()) return true;
        Integer damage = tool.get(DataComponents.DAMAGE);
        return damage == null || damage <= 5; // 接近满耐久
    }

    /** 旧 {@code findMiningPickaxe}，{@code :1538-1545} 逐字 */
    private ItemStack findMiningPickaxe() {
        ItemStack mainHand = mc.player.getMainHandItem();
        if (isPickaxe(mainHand)) return mainHand;
        ItemStack offhand = mc.player.getOffhandItem();
        if (isPickaxe(offhand)) return offhand;
        int slot = findMiningPickaxeSlot();
        return slot < 0 ? ItemStack.EMPTY : mc.player.getInventory().getItem(slot);
    }

    /** 旧 {@code findMiningPickaxeSlot}，{@code :1547-1554} 逐字 */
    private int findMiningPickaxeSlot() {
        ItemStack offhand = mc.player.getOffhandItem();
        if (isPickaxe(offhand)) return -2;
        for (int i = 0; i < 36; i++) {
            if (isPickaxe(mc.player.getInventory().getItem(i))) return i;
        }
        return -1;
    }

    /** 旧 {@code isPickaxe}，{@code :1556-1558} 逐字 */
    private boolean isPickaxe(ItemStack stack) {
        return !stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_pickaxe");
    }

    /** 旧 {@code isRepairableTool}，{@code :1560-1567} 逐字 */
    private boolean isRepairableTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.get(DataComponents.MAX_DAMAGE) == null) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel") || id.endsWith("_axe")
            || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /** 旧 {@code hasMending}，{@code :1569-1581} 逐字（附魔 holder 从世界注册表取） */
    private boolean hasMending(ItemStack stack) {
        if (stack.isEmpty() || mc.level == null) return false;
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return false;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(Enchantments.MENDING).orElse(null);
            return holder != null && enchantments.getLevel(holder) > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    /** 旧 {@code findDamagedToolSlot}，{@code :1583-1606} 逐字（副手 -2 优先） */
    private int findDamagedToolSlot() {
        if (mc.player == null) return -1;

        // 副手工具优先（已就位，直接修）
        if (isRepairableTool(mc.player.getOffhandItem()) && needsRepair(mc.player.getOffhandItem())) {
            return -2;
        }

        int bestSlot = -1;
        int lowestRemaining = Integer.MAX_VALUE;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isRepairableTool(stack) || !needsRepair(stack)) continue;
            Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
            Integer damage = stack.get(DataComponents.DAMAGE);
            int remaining = maxDamage - damage;
            if (remaining < lowestRemaining) {
                lowestRemaining = remaining;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 旧 {@code findDamagedTool}，{@code :1608-1614} 逐字 */
    private ItemStack findDamagedTool() {
        int slot = findDamagedToolSlot();
        if (slot == -2) return mc.player.getOffhandItem();
        if (slot >= 0) return mc.player.getInventory().getItem(slot);
        return ItemStack.EMPTY;
    }

    /**
     * 统计背包中的食物数量（只统计食物白名单内的物品合计，不判 FOOD 组件）。
     *
     * <p>旧 {@code countFoodStacks}，{@code :1619-1635}；白名单在本项目存登记 ID 字符串，
     * 判据由 {@code whitelist.contains(stack.getItem())} 等值换成 ID 比较，语义不变。</p>
     */
    private int countFoodStacks() {
        if (mc.player == null) return 0;

        List<String> whitelist = module.getFoodWhitelist();
        int count = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            // 只统计白名单内的食物
            if (whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) {
                count += stack.getCount(); // 统计实际数量
            }
        }
        return count;
    }

    /**
     * 背包里是否还有白名单内的可吃食物（有 FOOD 组件才算）。
     *
     * <p>旧 {@code hasFoodToEat}，{@code :1640-1650}。</p>
     */
    private boolean hasFoodToEat() {
        if (mc.player == null) return false;
        List<String> whitelist = module.getFoodWhitelist();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty()
                && whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())
                && stack.has(DataComponents.FOOD)) {
                return true;
            }
        }
        return false;
    }

    /** 旧 {@code countOreStacks}，{@code :1652-1672} 逐字（返回组数 = 总数 / 64） */
    private int countOreStacks() {
        if (mc.player == null) return 0;

        // 精准采集按原矿方块（含深层变种）计数；时运按掉落物计数（下界残骸掉落物=自身方块，两模式共用）
        Set<String> acceptIds = module.isSilkTouchMode()
            ? module.getTargetBlockIds()
            : Set.of(module.getTargetDropItemId());

        int totalCount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (acceptIds.contains(itemId)) {
                totalCount += stack.getCount(); // 统计目标矿物数量
            }
        }
        // 转换为完整组数
        return totalCount / 64;
    }

    /** 旧 {@code findWeaponInHotbar}，{@code :1674-1684} 逐字（返回副本） */
    private ItemStack findWeaponInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (id.endsWith("_sword")) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    /** 旧 {@code findWeaponSlotInHotbar}，{@code :1686-1695} 逐字 */
    private int findWeaponSlotInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_sword")) {
                return i;
            }
        }
        return -1;
    }

    /** 旧 {@code broadcastStateTransition}，{@code :1707-1728} 逐字（DEATH_HANDLING 一条按批准改点换名） */
    private void broadcastStateTransition(MinerState from, MinerState to) {
        if (from == to) return;

        int oreStacks = countOreStacks();
        int targetStacks = module.getFullLoadStacks();
        int foodCount = countFoodStacks();
        int foodThreshold = module.getHungerThreshold();

        String message = switch (to) {
            case IDLE -> "§7待机中";
            case GO_WILD -> "§a✓ 前往野外";
            case MINING -> String.format("§a✓ 开始挖矿 §8▸ 矿石 %d/%d 组 · 食物 %d/%d 个", oreStacks, targetStacks, foodCount, foodThreshold);
            case UNLOADING -> String.format("§b开始卸货 §8▸ 矿石已达 %d/%d 组", oreStacks, targetStacks);
            case SUPPLY -> String.format("§6⚠ 前往补给 §8▸ 食物不足 %d/%d 个", foodCount, foodThreshold);
            case EATING -> "§d补充饥饿值";
            case REPAIR -> "§c⚠ " + toolName(findDamagedTool()) + "耐久过低 §8▸ 联动杀戮光环修复中";
            case DEATH_HANDLING -> "§c✗ 检测到死亡 §8▸ 已调用自动重生";
            case RESPAWN_WAIT -> "§6复活完成 §8▸ 返回挂机点";
        };

        module.info(message);
    }

    /** 旧 {@code smoothRotateTo}，{@code :1730-1748} 逐字（±180 归一 + 0.3f 插值） */
    private void smoothRotateTo(float targetYaw, float targetPitch) {
        if (mc.player == null) return;
        float currentYaw = mc.player.getYRot();
        float currentPitch = mc.player.getXRot();

        float deltaYaw = targetYaw - currentYaw;
        float deltaPitch = targetPitch - currentPitch;

        // 归一化角度到 [-180, 180]
        while (deltaYaw > 180) deltaYaw -= 360;
        while (deltaYaw < -180) deltaYaw += 360;

        // 平滑插值
        float smoothYaw = currentYaw + deltaYaw * 0.3f;
        float smoothPitch = currentPitch + deltaPitch * 0.3f;

        mc.player.setYRot(smoothYaw);
        mc.player.setXRot(smoothPitch);
    }

    /** 旧 {@code faceBlock}，{@code :1750-1761} 逐字（卸货/补给开箱前面向箱子） */
    private void faceBlock(BlockPos pos) {
        if (mc.player == null) return;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        double dx = center.x - eye.x;
        double dy = center.y - eye.y;
        double dz = center.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        mc.player.setYRot((float) Math.toDegrees(Math.atan2(-dx, dz)));
        mc.player.setXRot((float) Math.toDegrees(-Math.atan2(dy, horiz)));
    }

    /**
     * 开启修补联动战斗（旧 {@code startKillAura}，{@code :1763-1770}）。
     *
     * <p>旧实现的「未开启才开启」判定（{@code killAura != null && !wasActive}）由注入的真实现承担
     * （批次 4 的 KillAura 模块），状态机侧只保留 {@code killAuraWasOnBefore} 标记：
     * 只要调用过 start，退出修补时就走 stop。</p>
     */
    private void startRepairCombat() {
        repairCombat.start();
        killAuraWasOnBefore = true; // 标记为「我们开的」，退出修补时才能关
    }

    /**
     * 关闭修补联动战斗（旧 {@code stopKillAura}，{@code :1772-1779}）。
     *
     * <p>只关我们自己开启的那一个：用户进入模块前就开着的杀戮光环保持原样（避免状态污染）。</p>
     */
    private void stopRepairCombat() {
        if (killAuraWasOnBefore) {
            repairCombat.stop();
        }
        killAuraWasOnBefore = false;
    }

    /**
     * 把修好的工具从副手放回原槽位（旧 {@code restoreHotbar}，{@code :1795-1815}）。
     *
     * <p>旧实现固定放回 0 号槽，若 0 号槽已被武器占据，会把武器顶进副手
     * （bug：修完镐子副手变成别的东西），旧注释 {@code :1798-1799} 记录了这一点，本移植保持「放回原槽」。</p>
     */
    private void restoreHotbar() {
        if (mc.player == null) return;

        // 把修好的工具从副手放回原槽位。
        if (!mc.player.getOffhandItem().isEmpty()) {
            if (savedToolSlot >= 0) {
                swapWithOffhand(savedToolSlot);
            } else if (savedToolSlot == -2) {
                // 工具原本就在副手，无需移动
            } else {
                swapWithOffhand(0);
            }
        }

        repairMode = false;
        savedTool = ItemStack.EMPTY;
        savedWeapon = ItemStack.EMPTY;
        savedToolSlot = -1;
        savedWeaponSlot = -1;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  物品栏原语（旧项目走 Meteor InvUtils；本项目换 26.1.2 原语）
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 切到指定快捷栏槽位（旧 {@code InvUtils.swap(slot, false)}）。
     *
     * <p>{@code setSelectedSlot} 只改本地选择槽，必须同时发一次携带物同步包，
     * 否则服务端仍按旧手持物处理右键（与本项目 {@code DefaultStardewAdapter.selectHotbar} 同一做法）。</p>
     */
    private void selectHotbar(int slot) {
        if (mc.player == null || slot < 0 || slot > 8) return;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }

    /**
     * 与副手交换物品（旧 {@code InvUtils.move().from(invSlot).toOffhand()}
     * 与 {@code InvUtils.move().fromOffhand().to(invSlot)}，两个方向同一次交换）。
     *
     * <p>走 {@code ContainerInput.SWAP}，button = 40 即「与被点击槽位交换副手物品」；
     * 快捷栏 0~8 在 {@code InventoryMenu} 里对应槽位 36~44，主背包 9~35 与 {@code Inventory}
     * 下标同值，因此只有快捷栏需要加 36。与本项目 {@code DefaultStardewAdapter} 同一口径。</p>
     */
    private void swapWithOffhand(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 0 || invSlot >= 36) return;
        int menuSlot = invSlot < 9 ? 36 + invSlot : invSlot;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuSlot,
            OFFHAND_INV_INDEX, ContainerInput.SWAP, mc.player);
    }
}
