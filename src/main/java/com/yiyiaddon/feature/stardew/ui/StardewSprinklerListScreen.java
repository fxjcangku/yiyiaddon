package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.SprinklerCoverage;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.StardewSprinklerRangeStore;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 控制台「洒水器点位」列表页：逐条删除 + 清空全部洒水器。
 *
 * <p>洒水器是唯一能绑多格的点位，只有「准星移除一格」和「清空全部点位（各点位一起）」两种极端做法，
 * 中间这段空缺由本页补上：每格一行，行尾「删除」只删这一格，底部「清空全部洒水器」只清洒水器、
 * 不动箱子 / 补水点 / 种植区域。</p>
 *
 * <p>删除后立即重建本页，列表与磁盘保持一致；每条删除都各自播报一条「已删除洒水器点位」，
 * 不存在「删了全部却只报一个坐标」。</p>
 */
public final class StardewSprinklerListScreen extends PanelScreen {

    private final Screen parent;
    private final StardewFarmModule module;

    public StardewSprinklerListScreen(Screen parent, StardewFarmModule module) {
        super("洒水器点位", parent);
        this.parent = parent;
        this.module = module;
        build();
    }

    private void build() {
        List<StardewPointManager.StardewPoint> points = module.sprinklerPoints();
        addSectionTitle("§7§l已绑定 §f" + points.size() + " §7§l个");
        if (points.isEmpty()) {
            addField("§7点位", "§8暂未绑定");
        } else {
            for (StardewPointManager.StardewPoint point : points) {
                // 行图标＝洒水器的代表物品（与控制台点位卡同源，见 StardewPointType#iconItemId）
                ItemStack icon = StardewPointType.SPRINKLER.icon();
                content().add(new CompactRow("§f" + label(point), () -> hint(point),
                    new Button("§c删除", () -> {
                        module.removeSprinklerPoint(point);
                        rebuild();
                    }).danger().small()).icon(() -> icon));
            }
        }

        addGap();
        addDivider();
        addButtons(new Button("§c清空全部洒水器", this::confirmClearAll).danger(),
            new Button("§7返回", this::requestClose));
        content().add(new TextLine("§8也可以准星对准洒水器，用 §f.stardew 移除洒水器 §8删掉那一格"));
    }

    /** 删除后整页重建：行数、计数、播报都以磁盘为准 */
    private void rebuild() {
        if (minecraft != null) minecraft.gui.setScreen(new StardewSprinklerListScreen(parent, module));
    }

    private static String label(StardewPointManager.StardewPoint point) {
        return point.x() + ", " + point.y() + ", " + point.z()
            + "  §8·  §7" + (point.typeName() == null ? "洒水器" : point.typeName());
    }

    /** 悬停说明：维度 + 设置时的验证来源 + 覆盖范围（画框用物品说明的能力范围，实测当对照附上） */
    private static String hint(StardewPointManager.StardewPoint point) {
        String dimension = WorldContextFormatter.dimensionSummary(point.dimension());
        return "维度 " + (dimension == null ? "未知" : dimension)
            + " · 验证 " + (point.verify() == null ? "未验证" : point.verify())
            + " · 覆盖 " + coverageHint(point);
    }

    /**
     * 覆盖范围一行：<b>先写画框实际用的来源</b>（物品说明的真实范围优先），实测当证据附上并标明是下限 ——
     * 实测数的是湿盆，盆群铺得比能力小就只测得出一小片（真机：5×5 的盆群里，13×13 的高级也只测出 5×5，
     * 但在边角放盆试验确认 13×13 是真的）。
     */
    private static String coverageHint(StardewPointManager.StardewPoint point) {
        SprinklerCoverage measured = point.measuredCoverage();
        String stated = StardewSprinklerRangeStore.sideText(point.identity());
        String evidence = measured == null
            ? "未实测"
            : "实测 " + measured.width() + "×" + measured.depth() + "（湿盆 " + measured.cells().size() + " 格，下限）";
        if (stated != null) return "按物品说明 " + stated + " · " + evidence;
        return measured == null ? "未实测（按等级估算）" : evidence + " · 画框按此项";
    }

    private void confirmClearAll() {
        if (minecraft == null) return;
        // 原地版确认窗：确认后回控制台，而不是把整个界面关掉回游戏
        // —— 用户 2026-09-22：「二次确认之后就直接关闭 ui 了，不应该到模块设置页面吗」
        // 数量先摆出来：原来是「执行后」才在回执卡片里报，而卡片在这个面板里根本看不见
        // （用户 2026-09-21：「按确认之前不知道要删几个」）。口径同 ID 配置的清空确认窗
        // —— 第一行就是 §c§l「将删除 N 个」，这里取的是本服全部洒水器，与 clearSprinklerPoints 清掉的完全一致
        int bound = module.sprinklerPoints().size();
        minecraft.gui.setScreen(ConfirmPanelScreen.inPlace("清空全部洒水器",
            List.of("§c§l将删除本服已绑定的 " + bound + " 个洒水器点位",
                "§7种子箱 / 成品箱 / 补水点 / 种植区域不受影响",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearSprinklerPoints, parent));
    }
}
