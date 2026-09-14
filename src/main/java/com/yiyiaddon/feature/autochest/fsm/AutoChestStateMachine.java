package com.yiyiaddon.feature.autochest.fsm;

import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.scan.ContainerScanner;
import com.yiyiaddon.feature.autochest.scan.ContainerSelector;
import com.yiyiaddon.feature.autochest.service.ChestInteractionService;
import com.yiyiaddon.feature.autochest.service.PathingService;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.autochest.ChestPointStore;
import com.yiyiaddon.repository.autochest.ContainerRecordStore;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AutoChest 状态机：驱动「扫描 → 选目标 → 锁目标 → 移动/等待 → 计算站位 → 寻路
 * → 到达 → 开箱 → 读槽 → 匹配 → 取物 → 校验 → 关箱 → 完成」全流程。
 *
 * <p>本状态机以「完整状态机」而非一堆互相冲突的布尔量表达运行状态：</p>
 * <ul>
 *   <li>{@code IDLE} 空闲等待开始新工作周期。</li>
 *   <li>{@code SCAN} 扫描阶段（实际分帧扫描由模块按扫描周期驱动，这里做流程锚点）。</li>
 *   <li>{@code SELECT_TARGET} 按运行模式选目标。</li>
 *   <li>{@code LOCK_TARGET} 目标锁校验（有效性 / 已处理 / 多人保护）。</li>
 *   <li>{@code WAIT_DISTANCE} 玩家控制模式：等玩家进入触发距离。</li>
 *   <li>{@code CALCULATE_STAND_POSITION} 寻路/标点模式：计算容器面前安全站位并发起寻路。</li>
 *   <li>{@code PATHING} 寻路中（限流重发 + 超时有限重试）。</li>
 *   <li>{@code ARRIVED} 到达确认。</li>
 *   <li>{@code OPENING} 开箱（发包 + 同步等待 + 超时有限重试）。</li>
 *   <li>{@code READING_SLOTS} 读取真实槽位（等 stateId 稳定）。</li>
 *   <li>{@code MATCHING} 只读匹配（决定取物 / 完成 / 背包满）。</li>
 *   <li>{@code TAKING} 取物（三态结果驱动）。</li>
 *   <li>{@code VERIFYING} 校验取物完成。</li>
 *   <li>{@code CLOSING} 关闭容器。</li>
 *   <li>{@code COMPLETED} 记录已处理。</li>
 *   <li>{@code FAILED} 失败（短暂停顿后重试对应环节）。</li>
 *   <li>{@code COOLDOWN} 冷却（完成后 / 跳过后短暂停顿再选下一个）。</li>
 *   <li>{@code STOPPED} 已停止（致命条件触发，模块即将关闭）。</li>
 * </ul>
 *
 * <p>目标锁（同一容器同时只有一个处理任务）、多人保护（其他玩家在用不抢箱）、有限重试
 * （开箱/寻路/交互/关闭失败上限）、临时冷却（连续失败/多人保护后暂时跳过）全部保留。
 * 播报遵循规范：工作状态播进度/结果，寻路过渡不播，带 lastNotifiedState 去重。</p>
 *
 * <p>与模块解耦：本类不依赖任何模块类，状态播报与停止请求经构造注入的 {@link Callbacks}
 * 回调交给上层，模块自身负责视角同步等表现层工作。</p>
 */
public final class AutoChestStateMachine {

    /** 状态机对外回调（模块侧实现；本类不依赖模块类） */
    public interface Callbacks {
        /** 状态播报（工作进度 / 结果） */
        void notifyStatus(String message);

        /** 致命条件触发，请求上层停止自动箱子 */
        void stopAutomation(String reason);

        /**
         * 视角是否已大致对准指定坐标。
         *
         * <p>旧项目开箱前以此判定是否可提前进入 {@code OPENING}，避免每次固定等满
         * {@link #ALIGN_WAIT_TICKS}；视角同步由模块层负责，本类只问结果。</p>
         */
        boolean viewAlignedTo(BlockPos pos);
    }

    /** AutoChest 流程状态 */
    public enum State {
        IDLE("空闲"),
        SCAN("扫描"),
        SELECT_TARGET("选择目标"),
        LOCK_TARGET("锁定目标"),
        WAIT_DISTANCE("等待距离"),
        CALCULATE_STAND_POSITION("计算站位"),
        PATHING("寻路中"),
        ARRIVED("已到达"),
        OPENING("打开容器"),
        READING_SLOTS("读取槽位"),
        MATCHING("匹配物品"),
        TAKING("取物中"),
        VERIFYING("校验结果"),
        CLOSING("关闭容器"),
        COMPLETED("已完成"),
        FAILED("失败"),
        COOLDOWN("冷却"),
        STOPPED("已停止");

        private final String displayName;

        State(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 开箱超时：多少个 tick 仍打不开就记一次失败（约 3 秒） */
    private static final int OPEN_TIMEOUT_TICKS = 60;

    /** 寻路超时：多少个 tick 仍未到达/寻路进程仍未激活就记一次失败（约 10 秒） */
    private static final int MOVE_TIMEOUT_TICKS = 200;

    /** 完成后到寻找下一个之间的冷却 tick，避免瞬间连开造成刷屏 */
    private static final int NEXT_COOLDOWN = 5;

    /** 失败后的短暂停顿 tick，避免立即重试刷屏 */
    private static final int FAIL_WAIT_TICKS = 10;

    /** 空闲 / 无目标时的重新扫描等待 tick，避免每 tick 空转 */
    private static final int IDLE_SCAN_WAIT = 5;

    /**
     * 开箱前等待视角转到位的最长 tick 数（约 1 秒）。
     *
     * <p>视角同步每 tick 最多转 15°，180° 需要 12 tick，20 tick 足够；
     * 超时只是放行开箱，绝不因为转头跟不上把任务卡死。</p>
     */
    private static final int ALIGN_WAIT_TICKS = 20;

    private final AutoChestSettings settings;
    private final ContainerScanner scanner;
    private final ChestInteractionService interaction;
    private final PathingService pathing;
    private final ChestPointStore points;
    private final ContainerRecordStore records;
    private final Callbacks callbacks;
    private final Minecraft mc;

    private State state = State.IDLE;
    private int stateTick = 0;

    /** 目标锁：当前正在处理的容器（寻路/标点模式锁定后不轻易更换） */
    private ChestTarget lockedTarget;

    /** 当前目标的连续失败次数（跨开箱/寻路/交互/关闭累计，达到上限跳过） */
    private int retryCount = 0;

    /** 失败后要重试的状态（FAILED 停顿结束回跳到这里） */
    private State retryToState = null;

    /** FAILED 状态的停顿计数 */
    private int failWait = 0;

    /** 冷却状态（完成后 / 跳过后）的停顿计数 */
    private int cooldownWait = 0;

    /** 空闲状态（无目标）的重新扫描等待计数 */
    private int idleWait = 0;

    /** 开箱超时计数 */
    private int openTimeout = 0;

    /** 寻路超时计数 */
    private int pathTimeout = 0;

    /** 开箱前等待视角转正的计数（见 {@link #ALIGN_WAIT_TICKS}） */
    private int alignWait = 0;

    /** 临时冷却表：键 = 维度+坐标，值 = 冷却截止毫秒（连续失败/多人保护后短暂跳过） */
    private final Map<String, Long> cooldowns = new HashMap<>();

    /** 上次运行模式（用于检测模式切换，触发旧模式状态清理） */
    private ScanMode lastMode = null;

    /** 上次维度（用于检测换维度，触发当前任务停止与数据重载） */
    private String lastDimension = null;

    public AutoChestStateMachine(AutoChestSettings settings, ContainerScanner scanner,
                                 ChestInteractionService interaction, PathingService pathing,
                                 ChestPointStore points, ContainerRecordStore records,
                                 Callbacks callbacks) {
        this.settings = settings;
        this.scanner = scanner;
        this.interaction = interaction;
        this.pathing = pathing;
        this.points = points;
        this.records = records;
        this.callbacks = callbacks;
        this.mc = Minecraft.getInstance();
    }

    /** 当前正在处理的容器（供渲染器把「处理中」与「未处理/已处理」区分开） */
    public ChestTarget processingTarget() {
        return lockedTarget;
    }

    /** 每 tick 调用（由模块 onTick 驱动） */
    public void tick() {
        if (mc.player == null || mc.level == null) return;
        stateTick++;

        // 致命条件（死亡 / 换维度）优先处理
        if (checkFatalConditions()) return;

        ScanMode mode = settings.scanMode;

        // 模式切换：立即清理旧模式状态，三模式绝不同时执行
        if (lastMode != null && lastMode != mode) {
            onModeSwitch();
        }
        lastMode = mode;

        advance(mode);
    }

    /**
     * 致命条件检测：玩家死亡立即停止；换维度停止当前任务并重载新维度数据。
     *
     * <p>退服时 {@code mc.level} 为空，已在 {@link #tick()} 入口拦截，不会继续发包；
     * 换维度时 {@code mc.level} 仍非空，这里靠维度标识变化捕获。</p>
     *
     * @return true 表示已触发致命停机，本 tick 不再推进
     */
    private boolean checkFatalConditions() {
        // 玩家死亡：立即停止自动任务
        if (mc.player.isDeadOrDying()) {
            stopToStopped("玩家死亡");
            return true;
        }

        // 换维度：停止当前任务（同一服务器数据仍有效，重置状态机在新维度重新扫描）
        String dim = WorldIdentity.dimension();
        if (lastDimension != null && !lastDimension.isEmpty() && !lastDimension.equals(dim)) {
            pathing.stop();
            interaction.close();
            reset();
        }
        lastDimension = dim;
        return false;
    }

    /** 模式切换：停止寻路、关闭容器、释放锁、清空状态，再按新模式启动 */
    private void onModeSwitch() {
        pathing.stop();
        interaction.close();
        reset();
    }

    /**
     * 进入安全停机：停止寻路/交互、释放目标锁、进入 STOPPED 并请求关闭模块。
     *
     * <p>背包满、玩家死亡等致命条件走这里，绝不再继续寻找下一个箱子。</p>
     */
    private void stopToStopped(String reason) {
        pathing.stop();
        interaction.close();
        lockedTarget = null;
        retryCount = 0;
        transitionTo(State.STOPPED);
        callbacks.stopAutomation(reason);
    }

    /**
     * 锁定目标是否仍然有效：维度未切换、容器仍存在、未被记录已处理、未被其他玩家占用。
     *
     * <p>容器被破坏或切维度 → 旧记录失效并释放锁；被记录已处理 → 换下一个；
     * 多人保护命中（其他玩家正在用）→ 不抢箱，临时跳过进入冷却，绝不标记已处理。</p>
     */
    private boolean validLocked() {
        // 容器被破坏或切维度：旧记录失效，释放锁定
        if (!lockedTarget.inCurrentDimension() || !isContainerAt(lockedTarget.pos())) {
            records.invalidate(lockedTarget.pos(), lockedTarget.dimension());
            lockedTarget = null;
            transitionTo(State.IDLE);
            return false;
        }
        // 已被记录为已处理：换下一个
        if (isProcessed(lockedTarget)) {
            lockedTarget = null;
            transitionTo(State.IDLE);
            return false;
        }
        // 多人保护：其他玩家正在使用目标容器，不抢、不强制操作，临时跳过
        if (settings.multiplayerProtect && hasNearbyPlayer(lockedTarget.pos())) {
            skipWithCooldown("检测到其他玩家使用容器");
            return false;
        }
        return true;
    }

    /** 按运行模式选取下一个目标（已过滤冷却中 / 被其他玩家占用的容器） */
    private ChestTarget selectTarget(ScanMode mode) {
        BlockPos playerPos = mc.player.blockPosition();
        String dim = WorldIdentity.dimension();
        long expireMs = settings.recordExpireMinutes * 60_000L;

        if (mode == ScanMode.MARKER) {
            // 标点模式：只读标点，不处理普通扫描箱子
            return ContainerSelector.select(filterAvailable(points.pointsInCurrentDimension()),
                playerPos, records, expireMs);
        }

        List<ChestTarget> candidates = filterAvailable(scanner.results());
        if (mode == ScanMode.PLAYER_CONTROL) {
            // 玩家控制：只处理触发距离内的容器，玩家自己走，模块不寻路
            int trigger = settings.triggerDistance;
            int triggerSqr = trigger * trigger;
            return candidates.stream()
                .filter(c -> c.distSqr(playerPos) <= triggerSqr)
                .filter(c -> !records.isProcessed(c.pos(), dim, c.containerType(), expireMs))
                .min(Comparator.comparingDouble(c -> c.distSqr(playerPos)))
                .orElse(null);
        }

        // 寻路模式：选最近的未处理容器
        return ContainerSelector.select(candidates, playerPos, records, expireMs);
    }

    /** 过滤候选：仅保留当前维度、不在临时冷却中、未被其他玩家占用的容器（多人保护开关关闭时不按玩家占用过滤） */
    private List<ChestTarget> filterAvailable(List<ChestTarget> candidates) {
        boolean protect = settings.multiplayerProtect;
        return candidates.stream()
            .filter(ChestTarget::inCurrentDimension)
            .filter(c -> !isCoolingDown(c))
            .filter(c -> !protect || !hasNearbyPlayer(c.pos()))
            .toList();
    }

    /** 推进当前状态 */
    private void advance(ScanMode mode) {
        switch (state) {
            case IDLE -> {
                // 空闲：短暂停顿后进入新一轮扫描
                if (--idleWait <= 0) transitionTo(State.SCAN);
            }

            case SCAN -> {
                // 扫描由模块按扫描周期驱动，这里只做流程锚点后进入选目标
                transitionTo(State.SELECT_TARGET);
            }

            case SELECT_TARGET -> {
                if (lockedTarget != null) {
                    // 重试回来仍持有锁：直接进入锁定校验
                    transitionTo(State.LOCK_TARGET);
                    return;
                }
                ChestTarget target = selectTarget(mode);
                if (target == null) {
                    // 无目标：回到空闲，稍后重新扫描
                    idleWait = IDLE_SCAN_WAIT;
                    transitionTo(State.IDLE);
                    return;
                }
                lockedTarget = target;
                retryCount = 0;
                transitionTo(State.LOCK_TARGET);
            }

            case LOCK_TARGET -> {
                if (!validLocked()) return; // validLocked 内部处理释放 / 跳过
                // 锁定有效：按模式进入等待距离或计算站位
                if (mode == ScanMode.PLAYER_CONTROL) {
                    transitionTo(State.WAIT_DISTANCE);
                } else {
                    transitionTo(State.CALCULATE_STAND_POSITION);
                }
            }

            case WAIT_DISTANCE -> {
                // 玩家控制：等玩家进入触发距离；目标失效则释放
                if (!lockedTarget.inCurrentDimension() || !isContainerAt(lockedTarget.pos())) {
                    records.invalidate(lockedTarget.pos(), lockedTarget.dimension());
                    lockedTarget = null;
                    transitionTo(State.IDLE);
                    return;
                }
                // 等待期间仍受多人保护：他人正在用则跳过
                if (settings.multiplayerProtect && hasNearbyPlayer(lockedTarget.pos())) {
                    skipWithCooldown("检测到其他玩家使用容器");
                    return;
                }
                int trigger = settings.triggerDistance;
                if (lockedTarget.distSqr(mc.player.blockPosition()) <= trigger * trigger) {
                    transitionTo(State.OPENING);
                }
                // 否则继续等待，玩家自己走
            }

            case CALCULATE_STAND_POSITION -> {
                // 目标容器消失则释放（生命周期间歇校验）
                if (!isContainerAt(lockedTarget.pos())) {
                    records.invalidate(lockedTarget.pos(), lockedTarget.dimension());
                    lockedTarget = null;
                    transitionTo(State.IDLE);
                    return;
                }
                // 清理旧路径后计算安全站位并发起寻路
                pathing.stop();
                if (!pathing.pathTo(lockedTarget.pos())) {
                    // 寻路服务不可用（Baritone 缺失）：直接跳过，不卡死
                    skipWithCooldown("寻路不可用");
                    return;
                }
                pathTimeout = 0;
                transitionTo(State.PATHING);
            }

            case PATHING -> {
                if (isAdjacent(lockedTarget.pos())) {
                    // 到达：停止移动，进入到达确认（开箱内部做距离/视线二次确认）
                    pathing.stop();
                    pathTimeout = 0;
                    transitionTo(State.ARRIVED);
                } else if (!pathing.isPathing()) {
                    // 未在寻路（算路中 / 失败 / 已取消）：限流重发，超时记一次失败
                    if (++pathTimeout > MOVE_TIMEOUT_TICKS) {
                        pathTimeout = 0;
                        pathing.stop();
                        fail("寻路失败", State.CALCULATE_STAND_POSITION);
                    }
                } else {
                    // 正在寻路：说明在前进，重置超时计数
                    pathTimeout = 0;
                }
            }

            case ARRIVED -> {
                // 到达确认：邻接已满足。开箱前等视角转到容器上（已对准则立即放行），
                // 超时兜底放行，绝不因转头卡住任务。
                if (lockedTarget == null
                        || callbacks.viewAlignedTo(lockedTarget.pos())
                        || ++alignWait > ALIGN_WAIT_TICKS) {
                    alignWait = 0;
                    transitionTo(State.OPENING);
                }
            }

            case OPENING -> {
                if (interaction.isOpen()) {
                    openTimeout = 0;
                    transitionTo(State.READING_SLOTS);
                } else if (interaction.open(lockedTarget.pos())) {
                    openTimeout = 0; // 发包成功，等待同步
                } else if (++openTimeout > OPEN_TIMEOUT_TICKS) {
                    // 打不开（太远/卡住/被占用）：有限重试
                    openTimeout = 0;
                    interaction.reset();
                    fail("开箱失败", State.OPENING);
                }
            }

            case READING_SLOTS -> {
                if (!interaction.isOpen()) {
                    // 容器意外关闭（多人抢/被关）：交互失败，有限重试回开箱，不记已处理
                    interaction.reset();
                    fail("容器意外关闭", State.OPENING);
                    return;
                }
                if (interaction.isSynced()) {
                    transitionTo(State.MATCHING);
                }
                // 否则继续等同步稳定
            }

            case MATCHING -> {
                // 匹配阶段容器被他人抢关：有限重试回开箱，绝不能误标已处理
                if (!interaction.isOpen()) {
                    interaction.reset();
                    fail("容器意外关闭", State.OPENING);
                    return;
                }
                ChestInteractionService.MatchResult match =
                    interaction.matchOnce(settings.withdrawMode);
                switch (match) {
                    case TAKABLE -> transitionTo(State.TAKING);
                    case DONE -> transitionTo(State.VERIFYING);
                    case INVENTORY_FULL -> stopToStopped("背包空间不足");
                }
            }

            case TAKING -> {
                if (!interaction.isOpen()) {
                    // 取物中容器意外关闭：有限重试回开箱，不记已处理
                    interaction.reset();
                    fail("容器意外关闭", State.OPENING);
                    return;
                }
                ChestInteractionService.Result result =
                    interaction.withdrawOnce(settings.withdrawMode);
                switch (result) {
                    case WITHDREW -> {
                        // 继续取物
                    }
                    case FINISHED -> transitionTo(State.VERIFYING);
                    case INVENTORY_FULL -> stopToStopped("背包空间不足");
                }
            }

            case VERIFYING -> {
                // 校验：容器已关视为取完（能到这里说明 TAKING 已判 FINISHED 无可取）；
                // 仍开着则复核是否真的无可取，兜底「发包即完成」导致的漏取
                if (!interaction.isOpen()) {
                    transitionTo(State.CLOSING);
                    return;
                }
                ChestInteractionService.MatchResult verify =
                    interaction.matchOnce(settings.withdrawMode);
                switch (verify) {
                    case DONE -> transitionTo(State.CLOSING);
                    case TAKABLE -> transitionTo(State.TAKING);
                    case INVENTORY_FULL -> stopToStopped("背包空间不足");
                }
            }

            case CLOSING -> {
                interaction.close();
                transitionTo(State.COMPLETED);
            }

            case COMPLETED -> {
                // 记录已处理并释放锁，短暂冷却后寻找下一个
                records.markProcessed(
                    lockedTarget.pos(), lockedTarget.dimension(), lockedTarget.containerType());
                lockedTarget = null;
                retryCount = 0;
                cooldownWait = NEXT_COOLDOWN;
                transitionTo(State.COOLDOWN);
            }

            case FAILED -> {
                // 短暂停顿后重试对应环节
                if (--failWait <= 0) {
                    State retry = retryToState != null ? retryToState : State.IDLE;
                    retryToState = null;
                    transitionTo(retry);
                }
            }

            case COOLDOWN -> {
                if (--cooldownWait <= 0) {
                    transitionTo(State.IDLE);
                }
            }

            case STOPPED -> {
                // 终止态：模块已请求关闭，不再推进
            }
        }
    }

    /** 记录一次失败；达到最大重试次数则跳过该容器（进入临时冷却），否则进入 FAILED 后重试 */
    private void fail(String reason, State retryState) {
        retryCount++;
        int max = maxRetries();
        if (retryCount >= max) {
            skipWithCooldown(reason);
            return;
        }
        callbacks.notifyStatus("§e⚠ " + reason + " §8▸ 重试 " + retryCount + "/" + max);
        retryToState = retryState;
        failWait = FAIL_WAIT_TICKS;
        transitionTo(State.FAILED);
    }

    /**
     * 暂时跳过当前容器：进入临时冷却，释放锁定，不标记已处理。
     *
     * <p>多人保护与达到最大失败次数都走这里——区别只在于触发原因，都不写「已处理」。</p>
     */
    private void skipWithCooldown(String reason) {
        if (lockedTarget != null) {
            cooldowns.put(cooldownKey(lockedTarget), System.currentTimeMillis() + cooldownMs());
        }
        interaction.reset();
        pathing.stop();
        lockedTarget = null;
        retryCount = 0;
        cooldownWait = NEXT_COOLDOWN;
        callbacks.notifyStatus("§e⚠ " + reason + " §8▸ 暂时跳过");
        transitionTo(State.COOLDOWN);
    }

    /** 目标是否已处理（含容器类型比对与过期判定） */
    private boolean isProcessed(ChestTarget target) {
        long expireMs = settings.recordExpireMinutes * 60_000L;
        return records.isProcessed(
            target.pos(), target.dimension(), target.containerType(), expireMs);
    }

    /** 指定坐标是否仍存在容器（生命周期校验） */
    private boolean isContainerAt(BlockPos pos) {
        if (mc.level == null) return false;
        return mc.level.getBlockEntity(pos) instanceof Container;
    }

    /** 玩家是否已贴近容器（切比雪夫距离 ≤ 1，满足开箱邻接） */
    private boolean isAdjacent(BlockPos pos) {
        BlockPos p = mc.player.blockPosition();
        int dx = Math.abs(p.getX() - pos.getX());
        int dy = Math.abs(p.getY() - pos.getY());
        int dz = Math.abs(p.getZ() - pos.getZ());
        return dx <= 1 && dy <= 1 && dz <= 1 && (dx | dy | dz) != 0;
    }

    /** 是否有其他玩家（非旁观者）在目标容器附近，视为「正在使用」 */
    private boolean hasNearbyPlayer(BlockPos pos) {
        if (mc.level == null) return false;
        double range = settings.playerDetectDistance;
        double rangeSqr = range * range;
        for (Player player : mc.level.players()) {
            if (player == mc.player || player.isSpectator()) continue;
            double distSqr = player.position().distanceToSqr(
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            if (distSqr <= rangeSqr) return true;
        }
        return false;
    }

    /** 目标是否处于临时冷却中 */
    private boolean isCoolingDown(ChestTarget target) {
        Long until = cooldowns.get(cooldownKey(target));
        return until != null && until > System.currentTimeMillis();
    }

    private int maxRetries() {
        return Math.max(1, settings.maxRetries);
    }

    private long cooldownMs() {
        return settings.cooldownTicks * 50L;
    }

    private String cooldownKey(ChestTarget target) {
        return target.dimension() + ":" + target.pos().getX() + "," + target.pos().getY() + "," + target.pos().getZ();
    }

    /** 重置状态机（清空锁定、计数、临时冷却与模式/维度记忆） */
    public void reset() {
        state = State.IDLE;
        stateTick = 0;
        lockedTarget = null;
        retryCount = 0;
        retryToState = null;
        failWait = 0;
        cooldownWait = 0;
        idleWait = 0;
        openTimeout = 0;
        pathTimeout = 0;
        alignWait = 0;
        cooldowns.clear();
        lastMode = null;
        lastDimension = null;
    }

    public State state() {
        return state;
    }

    public int stateTick() {
        return stateTick;
    }

    /**
     * 状态切换（带结果播报）。
     *
     * <p>寻路过渡状态（SCAN / PATHING / ARRIVED 等）不播，避免每 tick 刷屏；
     * 开箱/取物/完成等有实质动作的状态播进度/结果，同一状态不重复播。</p>
     */
    public void transitionTo(State next) {
        if (state == next) return;
        state = next;
        stateTick = 0;
        broadcast(next);
    }

    /** 按状态播报进度/结果（进度 §7，结果 §a✓） */
    private void broadcast(State s) {
        if (mc.player == null || lockedTarget == null) return;
        String coords = formatCoords(
            lockedTarget.pos().getX(), lockedTarget.pos().getY(), lockedTarget.pos().getZ());
        switch (s) {
            case OPENING -> callbacks.notifyStatus("§7正在打开容器 §8▸ " + coords);
            case TAKING -> callbacks.notifyStatus("§7正在取物 §8▸ " + coords);
            case COMPLETED -> callbacks.notifyStatus("§a✓ 处理完成 §8▸ " + coords);
            default -> {
                // 其余状态不播
            }
        }
    }

    /** 坐标排版，逐字复刻旧项目 {@code YiyiaddonModule.formatCoords} */
    private static String formatCoords(int x, int y, int z) {
        return "§7X§f" + x + " §7Y§f" + y + " §7Z§f" + z;
    }
}
