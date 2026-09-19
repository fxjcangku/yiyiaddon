package com.yiyiaddon.feature.villager.ui.console;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.ui.VillagerPointCards;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.screen.RenderObjectScreen;

/**
 * 控制台「点位」页：绿宝石箱与成品交易箱两张点位卡片 + 「显示与颜色」渲染设置。
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
 *
 * <p><b>显示与颜色（用户 2026-09-19：「所有标点选择点位位置的模块 参照星露谷农场的点位设置」）</b>：
 * 卡片之后追加「显示与颜色」小节 —— 绿宝石箱 / 成品交易箱各一行（显示开关 + 「设置」窗口 + 行尾 ↺，
 * 见共用件 {@link PointRenderSection}），再加一行字牌大小。设置窗口用共用件
 * {@link RenderObjectScreen}（显示 / 颜色自定义 / 彩虹 / 渲染模式：线框 / 面 / 两者）；
 * 每类对象的显示、颜色、模式与字号全部落在 {@link VillagerTradeSettings} 里，落盘走模块自己的
 * {@code persistSettings}，不新增第二套刷新链路（重建本页复用宿主的 {@code reload}）。</p>
 */
public final class VillagerPointPage {

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final VillagerTradeSettings DEFAULTS = new VillagerTradeSettings();

    private final VillagerConsoleScreen host;
    private final AutoVillagerTradeModule module;

    public VillagerPointPage(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：两张点位卡按两列排布，随后是「显示与颜色」渲染设置小节 */
    public void build(CompactStack stack) {
        stack.add(new PointCardGrid.Grid(VillagerPointCards.all()));

        stack.add(PointRenderSection.title(host));
        // 每类渲染对象一行（显示开关 + 「设置」窗口 + 行尾 ↺）+ 字牌大小一行：
        // 行构件是共用件 PointRenderSection（用户 2026-09-19：所有点位模块统一成星露谷这套）
        PointRenderSection renderSection = new PointRenderSection(host,
            module::persistSettings, host::reload, this::openRenderScreen, DEFAULTS.renderObjects());
        for (CompactElement row : renderSection.rows(module.settings().renderObjects())) {
            stack.add(row);
        }
        stack.add(renderSection.labelSizeRow(VillagerTradeSettings.NAME_LABEL_SIZE,
            VillagerTradeSettings.DESC_LABEL_SIZE,
            VillagerTradeSettings.LABEL_SIZE_MIN, VillagerTradeSettings.LABEL_SIZE_MAX,
            () -> module.settings().labelSize,
            value -> module.settings().labelSize = value,
            DEFAULTS.labelSize));
    }

    /** 打开「渲染设置 · 对象名」窗口（共用件 RenderObjectScreen，六个点位模块同一份实现） */
    private void openRenderScreen(EspRenderObject object) {
        if (host.client() == null) return;
        host.client().gui.setScreen(new RenderObjectScreen(host.client().gui.screen(), object,
            defaultsOf(object), module::persistSettings));
    }

    /**
     * 该渲染对象的出厂设置实例：按对象名在出厂设置里取同一项。
     *
     * <p>渲染对象是设置类里的固定字段（名字唯一且不变），取不到时返回自身（等价于不动作）。</p>
     */
    private static EspRenderObject defaultsOf(EspRenderObject object) {
        for (EspRenderObject candidate : DEFAULTS.renderObjects()) {
            if (candidate.name().equals(object.name())) return candidate;
        }
        return object;
    }
}
