package com.yiyiaddon.feature.villager.logistics;

import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

/**
 * 绿宝石补给搬运器（前提：绿宝石箱界面已由状态机打开）
 * 
 * 职责收窄为「箱子已开后，按节流节奏把绿宝石搬进背包」，
 * 开箱 / 关闭 / 寻路由状态机负责，本类不再碰这些环节。
 * 
 * 状态：
 * IDLE → RUNNING → COMPLETED / ERROR
 * 
 * <p>容器底座：{@link ContainerService} 负责同步观测与按槽位搬运，
 * {@link ContainerAccess} 负责菜单获取与容器侧统计；本类只决定
 * 「什么时候搬、搬几次、何时判达标 / 异常」，不自行实现槽位偏移与同步判定。</p>
 */
public final class SupplyService {

    private final Minecraft mc;
    private final ContainerService broker;
    private Consumer<String> logger;

    private State state = State.IDLE;
    private int targetEmeralds = 64;      // 本次补给目标总量
    private int opCooldown = 0;           // 槽位操作节流（tick）
    private int failStreak = 0;           // 连续操作失败计数
    private int readyTimeout = 0;         // 容器同步等待超时

    private static final int OP_INTERVAL = 2;        // 每 2 tick 最多一次槽位操作
    private static final int READY_TIMEOUT = 100;    // 容器同步 5 秒超时
    private static final int EMPTY_STREAK_LIMIT = 8; // 连续取不出绿宝石判箱空

    public SupplyService() {
        this.mc = Minecraft.getInstance();
        this.broker = new ContainerService();
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    /**
     * 开始补给搬运。
     *
     * <p>启动时必须先 {@code broker.reset()}：清掉上一个容器残留的 stateId 观测，
     * 否则新箱子会被误判为「已同步」，进而在未同步时抢跑发包被服务端回滚。</p>
     *
     * @param targetEmeralds 背包绿宝石达到该数量即完成
     */
    public void start(int targetEmeralds) {
        this.targetEmeralds = Math.max(1, targetEmeralds);
        this.opCooldown = 0;
        this.failStreak = 0;
        this.readyTimeout = 0;
        broker.reset();
        state = State.RUNNING;
        log("§b开始搬运绿宝石，目标 " + targetEmeralds + " 个");
    }

    /**
     * 由状态机在箱子界面打开后每 tick 调用。
     *
     * <p>执行顺序不可调换：先推进容器同步观测（{@code broker.tick()}）再判就绪，
     * 否则 {@code isReady()} 永远不会为真；未就绪期间只累计超时计数，绝不发包，
     * 超过 READY_TIMEOUT 才判 ERROR（菜单始终没同步上）。</p>
     *
     * <p>节流判据：每 OP_INTERVAL tick 最多一次槽位操作，冷却期内只递减计数并返回，
     * 避免连续发包被服务端判定为幽灵物品回滚。连续 EMPTY_STREAK_LIMIT 次搬不动时，
     * 用「本地菜单容器侧绿宝石总数」区分箱子真空（COMPLETED）与搬不出来（ERROR），
     * 不与真实背包数量交叉判断。</p>
     */
    public void tick() {
        if (state != State.RUNNING) return;

        LocalPlayer player = mc.player;
        if (player == null) {
            state = State.ERROR;
            return;
        }

        // 容器同步观测必须先于就绪判定
        broker.tick();
        if (!broker.isReady()) {
            if (++readyTimeout > READY_TIMEOUT) {
                log("§c容器同步超时");
                state = State.ERROR;
            }
            return;
        }

        // 背包绿宝石已达目标
        if (countEmeralds(player) >= targetEmeralds) {
            log("§a补给达标（" + countEmeralds(player) + " 个）");
            state = State.COMPLETED;
            return;
        }

        // 节流
        if (opCooldown > 0) {
            opCooldown--;
            return;
        }
        opCooldown = OP_INTERVAL;

        // 背包满则无法继续取
        if (player.getInventory().getFreeSlot() == -1) {
            log("§e背包已满，补给提前结束");
            state = State.COMPLETED;
            return;
        }

        if (broker.withdrawOne(Items.EMERALD)) {
            failStreak = 0;
            return;
        }

        // 连续取不出：判定箱子是不是空了
        if (++failStreak >= EMPTY_STREAK_LIMIT) {
            if (countInChest() == 0) {
                log("§e绿宝石箱已空，补给提前结束");
                state = State.COMPLETED;
            } else {
                log("§c补给搬运异常");
                state = State.ERROR;
            }
        }
    }

    /**
     * 统计容器侧（绿宝石箱）绿宝石总数。
     *
     * <p>只在「连续取不出」时调用，用于区分「箱子真空 → 正常收工」与
     * 「仍有货却取不出 → 异常」。取数走本地菜单槽位口径，与 withdrawOne 同源，
     * 避免真实背包尚未同步导致的误判；菜单已关返回 0。</p>
     */
    private int countInChest() {
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) return 0;
        return ContainerAccess.countInContainer(menu, Items.EMERALD);
    }

    /**
     * 统计玩家背包（含副手）绿宝石数量，作为补给达标判据。
     *
     * <p>遍历整个容器容量而非只算快捷栏 + 主背包，保证背包任意位置都计入；
     * 副手单独补计，绿宝石可能被拿在副手，漏计会导致补给量误判。</p>
     */
    private int countEmeralds(LocalPlayer player) {
        int count = 0;
        var inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == Items.EMERALD) {
                count += stack.getCount();
            }
        }
        // 副手也计入：绿宝石可能拿在副手，漏计会导致补给量误判
        ItemStack offhand = player.getOffhandItem();
        if (!offhand.isEmpty() && offhand.getItem() == Items.EMERALD) count += offhand.getCount();
        return count;
    }

    /**
     * 复位：状态回 IDLE，清空节流 / 连败 / 同步超时计时，并清空容器同步观测，
     * 保证下一次开箱重新观测 stateId（不复用上一个容器的就绪结论）。
     */
    public void reset() {
        state = State.IDLE;
        opCooldown = 0;
        failStreak = 0;
        readyTimeout = 0;
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
        ERROR
    }
}
