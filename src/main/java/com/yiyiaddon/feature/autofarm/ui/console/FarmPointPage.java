package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.config.AutoFarmSettings;
import com.yiyiaddon.feature.autofarm.ui.FarmPointCards;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.screen.RenderObjectScreen;

/**
 * 控制台「点位」页：五张点位卡片（农田范围 + 四个箱子），与模块页共用 {@link FarmPointCards}
 * 同一份数据、同一套按钮行为与图标（D2 / D9），禁止第二份卡片实现。
 *
 * <p><b>显示与颜色</b>（用户 2026-09-19：「所有标点选择点位位置的模块参照星露谷农场的点位设置」）：
 * 卡片下方追加星露谷那一套「显示与颜色」小节 —— 每类渲染对象一行（显示开关 + 「设置」窗口 + 行尾 ↺），
 * 外加「字牌大小」一行。行构件与设置窗口都是共用件
 * （{@link PointRenderSection} / {@link RenderObjectScreen}），本页只负责把模块自己的
 * 渲染对象、出厂对象与落盘回调传进去。</p>
 */
public final class FarmPointPage {

    /** 出厂设置：只作「行内恢复默认」与设置窗口 ↺ 的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoFarmSettings DEFAULTS = new AutoFarmSettings();

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmPointPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：卡片网格（随宿主每秒重画，绑定状态实时刷新）+「显示与颜色」小节 */
    public void build(CompactStack stack) {
        stack.add(new PointCardGrid.Grid(FarmPointCards.all(module)));

        stack.add(PointRenderSection.title(host));
        PointRenderSection renderSection = new PointRenderSection(host,
            module::persistSettings, host::reload, this::openRenderScreen, DEFAULTS.renderObjects());
        for (CompactElement row : renderSection.rows(module.settings().renderObjects())) {
            stack.add(row);
        }
        stack.add(renderSection.labelSizeRow(AutoFarmSettings.NAME_LABEL_SIZE, AutoFarmSettings.DESC_LABEL_SIZE,
            AutoFarmSettings.LABEL_SIZE_MIN, AutoFarmSettings.LABEL_SIZE_MAX,
            () -> module.settings().labelSize,
            value -> module.settings().labelSize = value,
            DEFAULTS.labelSize));
    }

    /** 打开「渲染设置 · 对象名」窗口（共用件 {@link RenderObjectScreen}） */
    private void openRenderScreen(EspRenderObject object) {
        if (host.client() == null) return;
        host.client().gui.setScreen(new RenderObjectScreen(host.client().gui.screen(), object,
            defaultsOf(object), module::persistSettings));
    }

    /**
     * 该渲染对象的出厂设置实例：按对象名在出厂设置里取同一项。
     *
     * <p>渲染对象是设置类里的固定字段（名字唯一且不变），取不到时返回自身（等价于 ↺ 不动作）。</p>
     */
    private static EspRenderObject defaultsOf(EspRenderObject object) {
        for (EspRenderObject candidate : DEFAULTS.renderObjects()) {
            if (candidate.name().equals(object.name())) return candidate;
        }
        return object;
    }
}
