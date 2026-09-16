package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.command.WkCommand;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingNumberBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动挖矿控制台「点位」页：三点点位的绑定 / 删除 + 显示与颜色。
 *
 * <p><b>样式与星露谷点位页同款</b>（用户 2026-09-16 指令「自动挖矿箱子esp 点位 给我跟星露谷点位设置
 * 带我那个一样」）：小节标题行 → 2 列卡片网格 → 「清空全部点位」按钮 + 二次确认 →
 * 「显示与颜色」小节（三行颜色 + 一行字牌大小）→ 底部指令提示。卡片本体是公共件
 * {@link com.yiyiaddon.ui.console.PointCardGrid.PointCard}（由星露谷点位页抽出），卡片外观、
 * 等高口径与命中算法两处完全一致。</p>
 *
 * <p><b>交互与文案与控制台入口所在配置页刻意同源：</b>按钮文案（{@code §a设置} / {@code §8设置} /
 * {@code §c删除}）、绑定 / 解绑调用（{@code bind(type, true)} / {@code remove(type, true)}）、
 * 点击后回到游戏的做法沿用旧写法；坐标串 {@code §7X§f%d §7Y§f%d §7Z§f%d} 与维度名
 * {@code §7维度 §f<名>} 沿用本页原有取值，未绑定 {@code §8暂未绑定} / {@code §8-}。</p>
 *
 * <p><b>清空与指令同源：</b>二次确认的确认动作直接调 {@link WkCommand#clearAllBindings()}
 * ——{@code .wk 清空} 与这里共用同一处清空实现与同一份回执（不写第二套）。</p>
 *
 * <p><b>颜色与字牌大小接的是模块既有机制：</b>调色板关窗回调 {@code module::syncColorsToSettings}
 * （把载体 ARGB 写回 {@code settings.*Color} 并落盘，与自动箱子渲染页同一构造），
 * 字牌大小走 {@code module.persistSettings()}。</p>
 */
public final class MiningPointPage {

    // ── 「显示与颜色」行文案（名称与默认值取自 MiningSettings 里这四项的中文注释） ──

    private static final String NAME_MINERAL_COLOR = "矿物箱 ESP 颜色";
    private static final String DESC_MINERAL_COLOR = "默认 (255,215,0)";
    private static final String NAME_FOOD_COLOR = "食物箱 ESP 颜色";
    private static final String DESC_FOOD_COLOR = "默认 (100,255,100)";
    private static final String NAME_AFK_COLOR = "挂机修复点 ESP 颜色";
    private static final String DESC_AFK_COLOR = "默认 (255,100,255)";
    private static final String NAME_ESP_SCALE = "ESP 字号倍率";
    private static final String DESC_ESP_SCALE = "默认 2.0";

    /** 字号倍率取值域 / 步长（与设置默认值同一口径） */
    private static final double ESP_SCALE_MIN = 0.5;
    private static final double ESP_SCALE_MAX = 8.0;
    private static final double ESP_SCALE_STEP = 0.5;

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningPointPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(new Note(owner, "§7§l点位绑定 §8（准星对准方块后点「设置」）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new PointCardGrid.Grid(pointCards()));
        stack.add(new ButtonStrip(owner, List.of(new Ctl(new Button("§c清空全部点位", this::openClearConfirm),
            "删除当前服务器已绑定的全部点位（不可恢复，会二次确认）")), ButtonStrip.BUTTON_HEIGHT));

        stack.add(new Note(owner, "§7§l显示与颜色", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(colorRow(NAME_MINERAL_COLOR, DESC_MINERAL_COLOR, module.mineralColor()));
        stack.add(colorRow(NAME_FOOD_COLOR, DESC_FOOD_COLOR, module.foodColor()));
        stack.add(colorRow(NAME_AFK_COLOR, DESC_AFK_COLOR, module.afkColor()));
        stack.add(labelSizeRow());
        stack.add(new Note(owner, "§8也可以用指令：§f.wk 设置 … §8/ §f.wk 移除 …"));
    }

    // ── 点位卡网格 ──

    /** 三张点位卡：矿物箱 / 食物箱 / 挂机修复点（顺序取自枚举，题目配色沿用本页 {@link #pointTitleColor}） */
    private List<PointCardGrid.PointCard> pointCards() {
        List<PointCardGrid.PointCard> cards = new ArrayList<>();
        for (MiningPointType type : MiningPointType.values()) cards.add(pointCard(type));
        return cards;
    }

    /**
     * 点位卡：标题 + 坐标与维度两行 + 「设置」「删除」两行按钮。
     *
     * <p>已绑定：坐标串 + {@code §7维度 §f<维度名>}；已绑定但不在当前维度：
     * {@code §8本维度未绑定 §7（其它维度已绑）} + {@code §8-}；未绑定：{@code §8暂未绑定} + {@code §8-}。
     * 删除按钮无条件显示，条件门控在点击回调内（与配置页卡片同一写法）。</p>
     */
    private PointCardGrid.PointCard pointCard(MiningPointType type) {
        MiningPoint point = module.pointStore().get(type);
        String info1;
        String info2;
        if (point != null && !point.inCurrentDimension()) {
            // 其它维度标过就说清楚，避免玩家以为点位丢了、跑去删了重标
            info1 = "§8本维度未绑定 §7（其它维度已绑）";
            info2 = "§8-";
        } else if (point != null) {
            info1 = String.format("§7X§f%d §7Y§f%d §7Z§f%d", point.x(), point.y(), point.z());
            info2 = "§7维度 §f" + WorldIdentity.dimensionDisplayName(point.dimension());
        } else {
            info1 = "§8暂未绑定";
            info2 = "§8-";
        }
        return new PointCardGrid.PointCard(pointTitleColor(type) + type.displayName(), info1, info2, List.of(
            List.of(new Button((point != null ? "§a" : "§8") + "设置", () -> {
                // 绑定失败（准星未命中 / 非容器 / 该类型已绑）时留在页面：错误已经播报，玩家可当场重设
                if (module.bindingService().bind(type, true)) closeToGame();
            })),
            List.of(new Button("§c删除", () -> {
                if (module.bindingService().remove(type, true)) closeToGame();
            }))));
    }

    /** 清空全部点位：二次确认（确认动作与 {@code .wk 清空} 同源，正文按本模块三个点位写） */
    private void openClearConfirm() {
        if (owner.client() == null) return;
        owner.client().setScreen(new ConfirmPanelScreen("清空全部点位",
            List.of("§f将删除当前服务器已绑定的全部点位",
                "§7矿物箱 · 食物箱 · 挂机修复点",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", WkCommand::clearAllBindings, owner.client().screen));
    }

    // ── 显示与颜色 ──

    /**
     * 颜色行：调色板关窗时先把载体颜色同步回设置项再落盘。
     *
     * <p>只传 {@code persistSettings} 是不行的：颜色还没同步进 {@code settings.*Color}，
     * 存下去的是旧值（与自动箱子渲染页同一构造）。</p>
     */
    private ConsoleRow colorRow(String name, String description, EspColor color) {
        return new ConsoleRow(owner, () -> name, description, null,
            List.of(new Ctl(new SettingColorPicker(name, color, module::syncColorsToSettings))));
    }

    /** 字牌大小：ESP 头顶文字的字号倍率，与三行颜色分开一行（改动落盘） */
    private CompactElement labelSizeRow() {
        MiningSettings settings = module.settings();
        SettingNumberBox box = new SettingNumberBox(ESP_SCALE_MIN, ESP_SCALE_MAX, ESP_SCALE_STEP, "%.1f",
            () -> settings.espScale,
            value -> {
                settings.espScale = value;
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> NAME_ESP_SCALE, DESC_ESP_SCALE, null, List.of(new Ctl(box)));
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        owner.client().setScreen(null);
    }

    /** 卡片标题配色（旧 {@code :1561-1566}）：矿物箱金 / 食物箱绿 / 挂机修复点粉 */
    private static String pointTitleColor(MiningPointType type) {
        return switch (type) {
            case MINERAL -> "§6";
            case FOOD -> "§2";
            case AFK -> "§d";
        };
    }
}
