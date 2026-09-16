package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.ui.FarmPointCards;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.component.CompactStack;

/**
 * 控制台「点位」页：六张点位卡片，与模块页共用 {@link FarmPointCards} 同一份数据、
 * 同一套按钮行为与图标（D2 / D9），禁止第二份卡片实现。
 */
public final class FarmPointPage {

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmPointPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：整页只有卡片网格（卡片随宿主每秒重画，绑定状态实时刷新） */
    public void build(CompactStack stack) {
        stack.add(new PointCardGrid.Grid(FarmPointCards.all(module)));
    }
}
