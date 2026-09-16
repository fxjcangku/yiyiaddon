package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * 自动附魔控制台「点位」页：按当前模式逐条绑定 / 删除点位（旧 {@code buildPointCard:660-721} 的卡片文案）。
 *
 * <p><b>只列当前模式需要的点位</b>：过滤走
 * {@link EnchantPointType#requiredFor(com.yiyiaddon.feature.enchant.model.EnchantTargetMode)}，
 * 与 {@code .fumo} 设置的模式门禁、启动自检同一处实现（旧 {@code requiredPoints} 的三处过滤）。
 * 换模式不污染、不删除其它模式的点位。</p>
 *
 * <p><b>卡片文案逐字保留</b>：标题与配色 = {@link EnchantPointType#titleColor()} + {@code title()}；
 * 未绑定两行 {@code §8暂未绑定} / {@code §8-}；按钮 {@code §a设置}（已绑定）/ {@code §8设置}（未绑定）
 * 与 {@code §c删除}。绑定 / 删除都走唯一实现
 * {@link com.yiyiaddon.feature.enchant.service.EnchantBindingService}，与 {@code .fumo} 指令同一处校验；
 * 绑定成功 / 删除成功按旧卡片做法直接回游戏，失败留在页面（错误已播报，玩家可当场重新对准）。</p>
 */
public final class EnchantPointPage {

    private static final String BTN_SET_BOUND = "§a设置";
    private static final String BTN_SET_UNBOUND = "§8设置";
    private static final String BTN_DELETE = "§c删除";

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;

    public EnchantPointPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        for (EnchantPointType type : EnchantPointType.requiredFor(module.settings().targetMode)) {
            stack.add(pointRow(type));
        }
    }

    /**
     * 点位行：标题 + 坐标与维度明细 + 「设置」「删除」。
     *
     * <p>明细每帧现读（{@code liveComment}）：换维度 / 换服务器或刚绑定完都能立刻看到实况，
     * 不必等整页重建。</p>
     */
    private CompactElement pointRow(EnchantPointType type) {
        boolean bound = module.pointStore().has(type);

        return ConsoleRow.liveComment(owner, () -> type.titleColor() + type.title(), null,
            () -> pointDetail(type), List.of(
                new Ctl(new Button(bound ? BTN_SET_BOUND : BTN_SET_UNBOUND, () -> {
                    // 绑定失败（模式门禁 / 已绑定 / 准星未命中 / 方块类型不符）时留在页面
                    if (module.bindingService().bind(type)) closeToGame();
                })),
                new Ctl(new Button(BTN_DELETE, () -> {
                    // 旧卡片：未绑定时删除按钮什么都不做（条件门控在点击回调内）
                    if (bound && module.bindingService().remove(type)) closeToGame();
                }))))
            // 点位卡的物品图标：旧 buildPointCard:660-721 的 9 张卡片统一用附魔书
            .icon(() -> Items.ENCHANTED_BOOK.getDefaultInstance());
    }

    /** 点位明细：未绑定 {@code §8暂未绑定  §8-}；已绑定坐标串 + 维度行（与概览页同源） */
    private String pointDetail(EnchantPointType type) {
        EnchantPoint point = module.pointStore().get(type);
        if (point == null) return "§8暂未绑定  §8-";
        return EnchantConsoleText.pointDetail(point, module.pointStore());
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        owner.client().setScreen(null);
    }
}
