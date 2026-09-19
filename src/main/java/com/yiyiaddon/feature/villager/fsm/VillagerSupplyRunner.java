package com.yiyiaddon.feature.villager.fsm;

import com.yiyiaddon.feature.villager.logistics.SupplyService;
import com.yiyiaddon.feature.villager.logistics.UnloadService;
import com.yiyiaddon.feature.villager.navigation.VillagerNavigationService;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.feature.villager.trade.TradeEngine;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;

/**
 * 补给 / 卸货六态的推进器：{@code SUPPLY_NAV → SUPPLY_OPEN → SUPPLY_TAKE} 与
 * {@code UNLOAD_NAV → UNLOAD_OPEN → UNLOAD_TAKE}。
 *
 * <p>职责拆分自旧项目 {@code VillagerTradeFSM}（原类 1,221 行，按第 38 条先拆后迁）。
 * 六态的共同点是「走向绑定的箱子 → 打开它 → 让物流服务把货搬完 → 回到村民」，
 * 与主交易线（搜索 / 寻路 / 开界面 / 交易）没有共享状态，适合独立成类。</p>
 *
 * <p><b>裁决权仍在状态机</b>：状态迁移（{@code enterState}）、失败（{@code fail}）、播报（{@code log}）
 * 一律回调宿主 {@link VillagerTradeFSM}，保证全流程只有一处决定「下一步去哪」——
 * 与旧实现同形，只是调用点从「同类内部」变成「宿主方法」。</p>
 *
 * <p><b>箱子来源</b>：每次推进都现取 {@link VillagerBindingStore#getBinding()}（旧项目是
 * {@code CunminCommand.getBinding()}），因为玩家可能在流程中改绑；绑定点缺失或跨维度一律失败停机，
 * 与旧实现逐字一致。</p>
 */
final class VillagerSupplyRunner {

    /** 状态机宿主（唯一的状态迁移与播报出口） */
    private final VillagerTradeFSM host;
    private final Minecraft mc = Minecraft.getInstance();

    VillagerSupplyRunner(VillagerTradeFSM host) {
        this.host = host;
    }

    private VillagerNavigationService navigation() {
        return host.navigation();
    }

    private SupplyService supplyService() {
        return host.supplyService();
    }

    private UnloadService unloadService() {
        return host.unloadService();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  补给流程（绿宝石箱）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    void tickSupplyNav() {
        VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();
        BlockPos box = binding == null ? null : binding.getEmeraldBox();
        if (box == null) {
            host.fail("未绑定绿宝石箱");
            return;
        }
        if (!isSameDimension(binding.getEmeraldBoxDimension())) {
            host.fail("绿宝石箱在其他维度");
            return;
        }
        if (host.stateTicks() > VillagerTradeFSM.NAV_TIMEOUT || navigation().isStuck()) {
            host.fail("前往绿宝石箱超时或卡死");
            return;
        }

        if (host.stateTicks() == 1 || (!navigation().isPathing() && host.stateTicks() % 60 == 0)) {
            if (!navigation().pathToContainer(box)) {
                host.fail("无法前往绿宝石箱");
            }
            return;
        }

        if (navigation().hasArrived(box, 3.0)) {
            navigation().stop();
            host.enterState(VillagerTradeState.SUPPLY_OPEN);
        }
    }

    void tickSupplyOpen() {
        VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();
        BlockPos box = binding == null ? null : binding.getEmeraldBox();
        if (box == null) {
            host.fail("未绑定绿宝石箱");
            return;
        }
        if (holdForPlayerScreen(VillagerTradeState.SUPPLY_OPEN)) return;
        if (host.stateTicks() > VillagerTradeFSM.OPEN_TIMEOUT) {
            host.fail("无法打开绿宝石箱");
            return;
        }
        if (mc.player != null && mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0) {
            host.enterState(VillagerTradeState.SUPPLY_TAKE);
            return;
        }
        if (host.stateTicks() % 10 == 0) {
            // 打点「我方刚开箱」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
            SilentContainer.markOwnContainerOpen();
            if (!BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, box, Direction.UP)) {
                host.fail("绿宝石箱交互失败");
            }
        }
    }

    void tickSupplyTake() {
        if (holdForPlayerScreen(VillagerTradeState.SUPPLY_TAKE)) return;
        if (host.stateTicks() == 1) {
            supplyService().start(host.emeraldThreshold() + host.supplyStacks() * 64);
        }
        supplyService().tick();

        if (supplyService().getState() == SupplyService.State.COMPLETED) {
            supplyService().reset();
            // 补给结束后校验：背包绿宝石仍低于阈值，说明绿宝石箱已空拿不到货，
            // 必须停机，否则会陷入「绿宝石不足 → 空箱补给 → 不足」的无限循环
            if (TradeEngine.countEmeralds() < host.emeraldThreshold()) {
                host.fail("绿宝石箱已空 §8▸ 无法补给，任务停止");
                return;
            }
            host.log("§a✓ 补给完成 §8▸ 背包 " + TradeEngine.countEmeralds() + " 绿宝石");
            host.enterClosing(TradeRoute.BACK_TO_VILLAGER);
        } else if (supplyService().getState() == SupplyService.State.ERROR) {
            supplyService().reset();
            if (TradeEngine.countEmeralds() < host.emeraldThreshold()) {
                host.fail("补给异常 §8▸ 绿宝石不足，任务停止");
                return;
            }
            host.log("§e⚠ 补给异常 §8▸ 有多少算多少继续交易");
            host.enterClosing(TradeRoute.BACK_TO_VILLAGER);
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  卸货流程（成品交易箱）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    void tickUnloadNav() {
        VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();
        BlockPos box = binding == null ? null : binding.getUnloadBox();
        if (box == null) {
            host.fail("未绑定成品交易箱");
            return;
        }
        if (!isSameDimension(binding.getUnloadBoxDimension())) {
            host.fail("成品交易箱在其他维度");
            return;
        }
        if (host.stateTicks() > VillagerTradeFSM.NAV_TIMEOUT || navigation().isStuck()) {
            host.fail("前往成品交易箱超时或卡死");
            return;
        }

        if (host.stateTicks() == 1 || (!navigation().isPathing() && host.stateTicks() % 60 == 0)) {
            if (!navigation().pathToContainer(box)) {
                host.fail("无法前往成品交易箱");
            }
            return;
        }

        if (navigation().hasArrived(box, 3.0)) {
            navigation().stop();
            host.enterState(VillagerTradeState.UNLOAD_OPEN);
        }
    }

    void tickUnloadOpen() {
        VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();
        BlockPos box = binding == null ? null : binding.getUnloadBox();
        if (box == null) {
            host.fail("未绑定成品交易箱");
            return;
        }
        if (holdForPlayerScreen(VillagerTradeState.UNLOAD_OPEN)) return;
        if (host.stateTicks() > VillagerTradeFSM.OPEN_TIMEOUT) {
            host.fail("无法打开成品交易箱");
            return;
        }
        if (mc.player != null && mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0) {
            host.enterState(VillagerTradeState.UNLOAD_TAKE);
            return;
        }
        if (host.stateTicks() % 10 == 0) {
            // 打点「我方刚开箱」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
            SilentContainer.markOwnContainerOpen();
            if (!BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, box, Direction.UP)) {
                host.fail("成品交易箱交互失败");
            }
        }
    }

    void tickUnloadTake() {
        if (holdForPlayerScreen(VillagerTradeState.UNLOAD_TAKE)) return;
        if (host.stateTicks() == 1) {
            unloadService().start(host.targets());
        }
        unloadService().tick();

        // 成品箱满：停机提示，不能「跳过继续」否则背包一直满、交易永远失败
        if (unloadService().getState() == UnloadService.State.FULL) {
            unloadService().reset();
            host.fail("成品交易箱已满 §8▸ 请清空后重试");
            return;
        }

        if (unloadService().getState() == UnloadService.State.COMPLETED
            || unloadService().getState() == UnloadService.State.ERROR) {
            boolean ok = unloadService().getState() == UnloadService.State.COMPLETED;
            if (!ok) host.log("§e⚠ 卸货异常 §8▸ 跳过");
            unloadService().reset();
            // 默认榨干：卸完货回去继续榨，不因背包满而收工
            host.enterClosing(TradeRoute.BACK_TO_VILLAGER);
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  工具方法
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 玩家自己开着容器界面（背包 / 创造背包）时只等不做。
     *
     * <p>此时若继续静默开箱，会把 {@code player.containerMenu} 悄悄换成箱子菜单，玩家背包里的
     * 点击就按箱子的 containerId 发出去（错位、丢物品）。我方开箱的界面一律被 SCREEN_OPEN
     * 拦掉，所以这里能看到的容器界面就是玩家自己的。等待期间原状态重入、只清计时，
     * 玩家看背包不算卡住，关掉背包后自然续上。</p>
     *
     * @param state 调用方当前状态
     * @return true = 本 tick 不推进
     */
    private boolean holdForPlayerScreen(VillagerTradeState state) {
        if (!(mc.gui.screen() instanceof AbstractContainerScreen<?>)) return false;
        host.enterState(state);
        return true;
    }

    /**
     * 判断绑定箱子的维度是否等于当前玩家所在维度（用字符串 contains 兼容裸 ID 与旧 ResourceKey 格式）。
     */
    private boolean isSameDimension(String boundDim) {
        if (mc.level == null || boundDim == null) return false;
        String cur = mc.level.dimension().toString();
        if (boundDim.contains("the_nether")) return cur.contains("the_nether");
        if (boundDim.contains("the_end")) return cur.contains("the_end");
        return cur.contains("overworld");
    }
}
