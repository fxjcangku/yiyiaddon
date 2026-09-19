package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;

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
                content().add(new CompactRow("§f" + label(point), () -> hint(point),
                    new Button("§c删除", () -> {
                        module.removeSprinklerPoint(point);
                        rebuild();
                    }).danger().small()));
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

    /** 悬停说明：维度 + 设置时的验证来源（紧凑行的提示是单行纯文本，不带颜色码） */
    private static String hint(StardewPointManager.StardewPoint point) {
        String dimension = WorldContextFormatter.dimensionSummary(point.dimension());
        return "维度 " + (dimension == null ? "未知" : dimension)
            + " · 验证 " + (point.verify() == null ? "未验证" : point.verify());
    }

    private void confirmClearAll() {
        if (minecraft == null) return;
        minecraft.gui.setScreen(new ConfirmPanelScreen("清空全部洒水器",
            List.of("§f将删除当前服务器已绑定的全部洒水器点位",
                "§7种子箱 / 成品箱 / 补水点 / 种植区域不受影响",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearSprinklerPoints, minecraft.gui.screen()));
    }
}
