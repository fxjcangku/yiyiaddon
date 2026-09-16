package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 自动挖矿控制台「点位」页：三点点位的绑定 / 删除。
 *
 * <p>逐字搬运自 {@link com.yiyiaddon.feature.mining.ui.AutoMinerPage#pointRow}；按钮文案、绑定 /
 * 解绑调用（{@code bind(type, true)} / {@code remove(type, true)}）、点击后回到游戏的做法
 * 与控制台入口所在的配置页卡片刻意同源，一个字未改。坐标与维度明细搬到行尾注释里
 * （已绑定 {@code §7X§f%d §7Y§f%d §7Z§f%d  §7维度名}；未绑定 {@code §8暂未绑定  §8-}）。</p>
 */
public final class MiningPointPage {

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningPointPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        for (MiningPointType type : MiningPointType.values()) stack.add(pointRow(type));
    }

    /**
     * 点位行：标题 + 坐标与维度 + 「设置」「删除」。
     *
     * <p>已绑定时坐标与维度名逐字照旧；未绑定时两段占位为 {@code §8暂未绑定} 与 {@code §8-}；
     * 删除按钮无条件显示，条件门控在点击回调内（与配置页卡片同一写法）。</p>
     */
    private CompactElement pointRow(MiningPointType type) {
        boolean bound = module.pointStore().has(type);

        return ConsoleRow.liveComment(owner, () -> pointTitleColor(type) + type.displayName(), null,
            () -> pointDetail(type), List.of(
                new Ctl(new Button(bound ? "§a设置" : "§8设置", () -> {
                    // 绑定失败（准星未命中 / 非容器 / 该类型已绑）时留在页面：错误已经播报，玩家可当场重设
                    if (module.bindingService().bind(type, true)) closeToGame();
                })),
                new Ctl(new Button("§c删除", () -> {
                    if (module.bindingService().remove(type, true)) closeToGame();
                }))));
    }

    /**
     * 点位明细（每帧现读，换服务器后不必重建整页就能看到真实绑定）。
     *
     * <p>已绑定时坐标 + 维度名；未绑定时两段占位为 {@code §8暂未绑定} 与 {@code §8-}（与配置页卡片逐字同源）。</p>
     */
    private String pointDetail(MiningPointType type) {
        MiningPoint point = module.pointStore().get(type);
        if (point == null) return "§8暂未绑定  §8-";
        return String.format("§7X§f%d §7Y§f%d §7Z§f%d  §7%s", point.x(), point.y(), point.z(),
            WorldIdentity.dimensionDisplayName(point.dimension()));
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        owner.client().setScreen(null);
    }

    /** 行标题配色（旧 {@code :1561-1566}）：矿物箱金 / 食物箱绿 / 挂机修复点粉 */
    private static String pointTitleColor(MiningPointType type) {
        return switch (type) {
            case MINERAL -> "§6";
            case FOOD -> "§2";
            case AFK -> "§d";
        };
    }
}
