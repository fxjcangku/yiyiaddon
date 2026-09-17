package com.yiyiaddon.feature.villager.logistics;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import com.yiyiaddon.feature.villager.trade.EnchantmentMatcher;
import com.yiyiaddon.model.container.ContainerTransferResult;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 任务物品卸货搬运器（前提：成品交易箱界面已由状态机打开）
 * 
 * 核心原则：
 * · 只卸载当前任务目标物品，不触碰玩家私人物品（装备 / 工具 / 食物 / 绿宝石）
 * · 附魔书必须精准匹配附魔类型
 * · 槽位操作走 ContainerService（按 BPT 节流，且正确处理 menu 与背包槽位映射，
 *   不再手写槽位偏移公式）
 * 
 * 状态：
 * IDLE → RUNNING → COMPLETED / ERROR
 * 
 * <p>容器底座：{@link ContainerService} 负责同步观测与按槽位搬运，本类只提供
 * 物品白名单过滤条件并解释 {@link ContainerTransferResult} 各分支语义。</p>
 */
public final class UnloadService {

    private final Minecraft mc;
    private final ContainerService broker;
    private Consumer<String> logger;

    private State state = State.IDLE;
    private List<VillagerTradeTarget> taskTargets = new ArrayList<>();
    private int opCooldown = 0;        // 槽位操作节流（tick）
    private int failStreak = 0;        // 连续操作失败计数
    private int readyTimeout = 0;      // 容器同步等待超时

    private static final int OP_INTERVAL = 3;        // 每 3 tick 最多一次槽位操作
    private static final int READY_TIMEOUT = 100;    // 容器同步 5 秒超时
    private static final int DONE_STREAK_LIMIT = 6;  // 连续无货可卸判完成

    public UnloadService() {
        this.mc = Minecraft.getInstance();
        this.broker = new ContainerService();
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    /**
     * 开始卸货搬运。
     *
     * <p>目标列表拷贝成快照，避免调用方后续修改列表时卸货判据中途变化；
     * 启动时 {@code broker.reset()} 清掉上一个容器的 stateId 观测，
     * 保证新箱子重新完成同步判定后才发包。</p>
     *
     * @param targets 当前任务目标列表，只卸载命中白名单的物品
     */
    public void start(List<VillagerTradeTarget> targets) {
        this.taskTargets = new ArrayList<>(targets);
        this.opCooldown = 0;
        this.failStreak = 0;
        this.readyTimeout = 0;
        broker.reset();
        state = State.RUNNING;
        log("§b开始卸货");
    }

    /**
     * 由状态机在箱子界面打开后每 tick 调用。
     *
     * <p>执行顺序：先推进容器同步观测（{@code broker.tick()}）再判就绪，
     * 未就绪只累计超时、绝不发包；OP_INTERVAL 节流保证每 3 tick 最多一次槽位操作。
     * 槽位归属（背包侧 / 容器侧）由底座按 {@code slot.container == 背包} 判定，
     * 本类只传过滤条件，不手写下标偏移。</p>
     *
     * <p>结果分支不可合并：MOVED 清连败；NOT_READY 是等待态，既不计连败也不判完成；
     * CHEST_FULL 置 FULL 交由 FSM 统一提示；只有连续 DONE_STREAK_LIMIT 次无货可卸
     * 才判 COMPLETED。</p>
     */
    public void tick() {
        if (state != State.RUNNING) return;

        LocalPlayer player = mc.player;
        if (player == null) {
            state = State.ERROR;
            return;
        }

        broker.tick();
        if (!broker.isReady()) {
            readyTimeout++;
            if (readyTimeout > READY_TIMEOUT) {
                log("§c容器同步超时");
                state = State.ERROR;
            }
            return;
        }

        if (opCooldown > 0) {
            opCooldown--;
            return;
        }
        opCooldown = OP_INTERVAL;

        ContainerTransferResult result = broker.depositOne(this::isTaskTarget);
        if (result == ContainerTransferResult.MOVED) {
            failStreak = 0;
            return;
        }
        if (result == ContainerTransferResult.NOT_READY) {
            return; // 容器尚未同步稳定，继续等待，不判完成
        }
        // 箱子满：还有任务物品放不下，必须停机提示，不能误判成「卸货完成」。
        // 提示语由 FSM 的 fail() 统一输出，这里只置状态，避免重复刷屏。
        if (result == ContainerTransferResult.CHEST_FULL) {
            state = State.FULL;
            return;
        }

        // 连续搬不动（已无任务物品）：判定完成
        if (++failStreak >= DONE_STREAK_LIMIT) {
            log("§a卸货完成");
            state = State.COMPLETED;
        }
    }

    /**
     * 背包是否还存在待卸任务物品（供状态机决定要不要走卸货流程）。
     *
     * <p>只扫 0~35 号背包槽位（主背包 + 快捷栏），不含护甲与副手：卸货只搬这些槽位，
     * 与 hasTaskItems 的判定范围保持与卸货能力一致，避免「判出待卸却永远搬不走」。</p>
     */
    public boolean hasTaskItems(List<VillagerTradeTarget> targets) {
        LocalPlayer player = mc.player;
        if (player == null || targets.isEmpty()) return false;

        var inventory = player.getInventory();
        List<VillagerTradeTarget> checkTargets = new ArrayList<>(targets);
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && isMatchingSheet(stack, checkTargets)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 ItemStack 是否命中任务白名单。
     *
     * <p>固定用本类的 taskTargets 快照（start 时确定），保证一次卸货过程中判据不变。</p>
     */
    private boolean isTaskTarget(ItemStack stack) {
        return isMatchingSheet(stack, taskTargets);
    }

    /**
     * 白名单匹配判据：物品 Item 必须与目标一致；附魔书还必须通过 EnchantmentMatcher
     * 精准匹配附魔类型（忽略等级），普通物品 Item 一致即命中。
     *
     * <p>附魔书不做精准匹配会把玩家私藏的不同附魔书一起搬走，属误卸。</p>
     */
    private static boolean isMatchingSheet(ItemStack stack, List<VillagerTradeTarget> targets) {
        if (stack.isEmpty()) return false;

        for (VillagerTradeTarget target : targets) {
            if (target.getItem() != stack.getItem()) continue;

            // 附魔书必须精准匹配附魔类型，普通物品 Item 一致即可
            if (target.isEnchantedBook()) {
                if (EnchantmentMatcher.matches(stack, target)) return true;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 复位：状态回 IDLE，清空节流 / 连败 / 同步超时计时与任务目标快照，
     * 并清空容器同步观测，下一次开箱必须重新完成同步判定。
     */
    public void reset() {
        state = State.IDLE;
        opCooldown = 0;
        failStreak = 0;
        readyTimeout = 0;
        taskTargets.clear();
        broker.reset();
    }

    public State getState() {
        return state;
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    private void log(String message) {
        if (logger != null) {
            logger.accept(message);
        }
    }

    public enum State {
        IDLE,
        RUNNING,
        COMPLETED,
        ERROR,
        FULL
    }
}
