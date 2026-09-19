package com.yiyiaddon.platform.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;

/**
 * 村民交易菜单访问层：把「交易界面」专属的容器原语集中在此，供各业务模块共用。
 *
 * <p>与 {@link ContainerAccess} 的分工：那边管通用容器（箱子 / 潜影盒）的开关与搬运，
 * 这边只管村民交易菜单（{@code MerchantMenu}）独有的三件事——取交易菜单、选中报价、
 * 领取结果槽。两者都只做 API 适配，不含业务判断（该不该买、价格是否合适都不在这里）。</p>
 *
 * <p><b>为什么单独一层</b>：自动村民交易（真实开界面路线）与自动图书管理员（静默交易路线）
 * 虽然流程不同，但「发 SelectTrade 包」「把结果槽快速移到背包」两步是同一份协议；
 * 按第 169 条，同源判据/协议禁止各留一份，故收敛到这里。</p>
 *
 * <p><b>接线现状（如实登记）</b>：本层由自动图书管理员使用；自动村民交易模块在其批次4 收口前
 * 包结构冻结（第 68 条），暂未改造，待其收口后改为调用本层（登记于 89 号报告）。</p>
 */
public final class MerchantTradeAccess {

    /** 交易菜单中的结果槽下标（0/1 为付款槽，2 为成品槽） */
    private static final int RESULT_SLOT_INDEX = 2;

    private MerchantTradeAccess() {
    }

    /**
     * 当前打开的交易菜单。
     *
     * <p>只认 {@code MerchantMenu}：静默交易时客户端不弹 Screen，但
     * {@code player.containerMenu} 仍会被服务端同步成交易菜单，因此这里直接读它。</p>
     */
    public static MerchantMenu merchantMenu() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return null;
        return player.containerMenu instanceof MerchantMenu menu ? menu : null;
    }

    /** 交易菜单是否已就绪 */
    public static boolean isReady() {
        return merchantMenu() != null;
    }

    /**
     * 选中指定序号的报价。
     *
     * <p>{@code ServerboundSelectTradePacket} 只携带报价序号；服务端要求玩家当前
     * {@code containerMenu} 必须是 {@code MerchantMenu}，否则包会被直接忽略。</p>
     */
    public static void selectTrade(int offerIndex) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;
        mc.getConnection().send(new ServerboundSelectTradePacket(offerIndex));
    }

    /** 结果槽是否已有成品（服务端完成选择与计价后的同步信号） */
    public static boolean hasResultItem() {
        MerchantMenu menu = merchantMenu();
        return menu != null && !menu.getSlot(RESULT_SLOT_INDEX).getItem().isEmpty();
    }

    /** 把结果槽整组快速移到玩家背包（一步成交） */
    public static void takeResult(AbstractContainerMenu menu) {
        if (menu == null) return;
        ContainerAccess.quickMove(menu, RESULT_SLOT_INDEX);
    }

    /** 关闭交易界面（只在确实开着容器时才发，避免误关本模组界面） */
    public static void close() {
        ContainerAccess.closeContainer();
    }
}
