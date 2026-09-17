package com.yiyiaddon.feature.villager.ui.console;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.ui.VillagerPointCards;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.PointCardGrid;

/**
 * 控制台「点位」页：绿宝石箱与成品交易箱两张点位卡片。
 *
 * <p><b>禁止第二份卡片实现</b>：卡片本体走 {@link VillagerPointCards#all()}（旧
 * {@code buildLocationCard :770-826} 逐字换壳的那一份），文案、按钮行为一处源码；
 * 本页只做「把网格铺进正文栈」这一件事。</p>
 *
 * <p><b>点位只在本页出现</b>（用户 2026-09-17：「控制台里面已经有点位了 为什么控制台外面还有」）：
 * 模块页不再重复摆这两张卡（旧项目挂在配置页的那一处已收敛到控制台），故本页是点位设置的唯一入口。</p>
 *
 * <p>卡片信息与按钮配色都按帧现读仓库，因此「设置 / 删除」后回到本页就是真实状态；
 * 整页每秒重画（宿主在点位页整页 reload），绑定变化无需手动刷新。</p>
 */
public final class VillagerPointPage {

    private final VillagerConsoleScreen host;
    private final AutoVillagerTradeModule module;

    public VillagerPointPage(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：整页只有卡片网格（两张卡按两列排布，与旧配置页的两列布局一致） */
    public void build(CompactStack stack) {
        stack.add(new PointCardGrid.Grid(VillagerPointCards.all()));
    }
}
