package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.screen.RenderObjectScreen;
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
 *
 * <p><b>点位行之后的「显示与颜色」小节</b>（用户 2026-09-19：「所有标点选择点位位置的模块 参照星露谷
 * 农场的点位设置」）：六个点位字牌各一行（显示开关 + 「设置」窗口 + 行尾 ↺）外加「字牌大小」一行，
 * 行构件与设置窗口走共用件 {@link PointRenderSection} / {@link RenderObjectScreen}；
 * 对象清单固定为 {@link EnchantSettings#renderObjects()}（与渲染器绘制顺序一致），不随目标模式变化。</p>
 */
public final class EnchantPointPage {

    private static final String BTN_SET_BOUND = "§a设置";
    private static final String BTN_SET_UNBOUND = "§8设置";
    private static final String BTN_DELETE = "§c删除";

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final EnchantSettings DEFAULTS = new EnchantSettings();

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

        // 显示与颜色：六个点位字牌各一行（显示开关 + 「设置」窗口 + 行尾 ↺）+ 字牌大小一行。
        // 行构件与设置窗口都是共用件（用户 2026-09-19：所有点位模块统一成星露谷这套）
        stack.add(PointRenderSection.title(owner));
        PointRenderSection renderSection = new PointRenderSection(owner,
            module::persistSettings, owner::reload, this::openRenderScreen, DEFAULTS.renderObjects());
        for (CompactElement row : renderSection.rows(module.settings().renderObjects())) {
            stack.add(row);
        }
        stack.add(renderSection.labelSizeRow(EnchantSettings.NAME_LABEL_SIZE, EnchantSettings.DESC_LABEL_SIZE,
            EnchantSettings.LABEL_SIZE_MIN, EnchantSettings.LABEL_SIZE_MAX,
            () -> module.settings().labelSize,
            value -> module.settings().labelSize = value,
            DEFAULTS.labelSize));
    }

    /** 打开「渲染设置 · 对象名」窗口（共用件 RenderObjectScreen，六个点位模块同一份实现） */
    private void openRenderScreen(EspRenderObject object) {
        if (owner.client() == null) return;
        owner.client().gui.setScreen(new RenderObjectScreen(owner.client().gui.screen(), object,
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
        owner.client().gui.setScreen(null);
    }
}
