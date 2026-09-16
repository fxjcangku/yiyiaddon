package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;

import java.util.List;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「概览」页：只读展示运行状态 / 点位 / 目标 / 自检四段。
 *
 * <p>本页不放任何可编辑控件；数据全部在各页构建时现读模块状态（不为控制台给模块加任何接口），
 * 每秒随概览页整页重建一起刷新。三段的标题与取值口径与配置页一致：点位名与配色、
 * {@code X/Y/Z + 维度名} 的坐标串、三个单值目标的产物名、自检缺项原文照抄（不改色码）。</p>
 */
public final class MiningOverviewPage {

    // ── 目标三行的标题（逐字取自配置页） ──

    private static final String TITLE_OVERWORLD = "主世界矿石";
    private static final String TITLE_NETHER = "下界矿石";
    private static final String TITLE_BLOCK = "普通方块";

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningOverviewPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();

        stack.add(section("运行状态"));
        stack.add(dataRow("状态机", () -> "§f" + module.fsm().state().cn()));
        stack.add(dataRow("模块", () -> module.isEnabled() ? "§a运行中" : "§8未启用"));
        stack.add(dataRow("维度", () -> "§f"
            + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension())));

        stack.add(section("点位"));
        for (MiningPointType type : MiningPointType.values()) {
            stack.add(dataRow(pointTitleColor(type) + type.displayName(), () -> pointDetail(type)));
        }

        stack.add(section("目标"));
        stack.add(dataRow(TITLE_OVERWORLD, () -> targetName(settings.overworldOreTarget, false)));
        stack.add(dataRow(TITLE_NETHER, () -> targetName(settings.netherOreTarget, false)));
        stack.add(dataRow(TITLE_BLOCK, () -> targetName(settings.blockTarget, true)));

        stack.add(section("自检"));
        List<String> missing = module.selfCheck();
        if (missing.isEmpty()) {
            stack.add(new Note(owner, "§a自检通过"));
        } else {
            for (String item : missing) stack.add(new Note(owner, item));
        }
    }

    /** 分区标题（与星露谷控制台各页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /**
     * 只读数据行：标签 + 值写成一行 20px 的 {@link Note}。
     *
     * <p>原来用 36px 的 {@code ConsoleRow} 画纯读数：9 行加 4 个小标题约 518px，而可视区只有约 404px，
     * 「自检」段必须滚动才能看到。换成 Note 后约 354px，一屏放得下。值仍是构建时取——概览页每秒整页重画。</p>
     */
    private CompactElement dataRow(String label, Supplier<String> value) {
        return new Note(owner, "§7" + label + "  §f" + value.get());
    }

    /**
     * 点位坐标串：已绑定 {@code §7X§f%d §7Y§f%d §7Z§f%d  §7维度名}，
     * 未绑定 {@code §8暂未绑定}（与配置页卡片逐字同源）。
     */
    private String pointDetail(MiningPointType type) {
        MiningPoint point = module.pointStore().get(type);
        if (point == null) return "§8暂未绑定";
        return String.format("§7X§f%d §7Y§f%d §7Z§f%d  §7%s", point.x(), point.y(), point.z(),
            WorldIdentity.dimensionDisplayName(point.dimension()));
    }

    /** 单值目标的当前产物名；未选择显示 {@code §8未选择}（空值不显示成「空气」） */
    private static String targetName(String stored, boolean block) {
        if (stored == null || stored.isBlank()) return "§8未选择";
        return block ? MiningTargetPage.blockDisplayName(stored) : MiningTargetPage.itemDisplayName(stored);
    }

    /** 行标题配色（与配置页一致）：矿物箱金 / 食物箱绿 / 挂机修复点粉 */
    private static String pointTitleColor(MiningPointType type) {
        return switch (type) {
            case MINERAL -> "§6";
            case FOOD -> "§2";
            case AFK -> "§d";
        };
    }
}
