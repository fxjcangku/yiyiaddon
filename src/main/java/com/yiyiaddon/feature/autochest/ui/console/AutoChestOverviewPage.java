package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;

import java.util.List;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「概览」页：只读展示运行 / 容器 / 点位 / 取物 / 自检五段。
 *
 * <p>本页不放任何可编辑控件；数据在各页构建时现读模块状态（不为控制台给模块加任何接口），
 * 每秒随概览页整页重建一起刷新。取值口径与配置页一致：容器计数 {@code 已选 N / M 类}、
 * 目标物品计数 {@code 未选择目标（共 N 项）} / {@code 已选 X / N 项}、自检缺项原文照抄
 * （不改色码），点位与记录计数走本维度 / 当前服务器的存储实况。</p>
 */
public final class AutoChestOverviewPage {

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestOverviewPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        stack.add(section("运行状态"));
        stack.add(dataRow("模块", () -> module.isEnabled() ? "§a运行中" : "§8未启用"));
        stack.add(dataRow("状态机", () -> "§f" + module.stateMachine().state()));
        stack.add(dataRow("维度", () -> "§f" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension())));
        stack.add(dataRow("运行模式", () -> "§f" + settings.scanMode.displayName()));

        stack.add(section("容器"));
        stack.add(dataRow("容器类型", () -> "§f" + settings.containerTypeIds.size() + " / "
            + ContainerTypeRegistry.all().size() + " 类"));
        stack.add(dataRow("检测范围", () -> "§f" + settings.scanRadius + " 格"));
        stack.add(dataRow("扫描周期", () -> "§f" + settings.scanInterval + " Tick"));
        stack.add(dataRow("已处理记录过期", () -> "§f" + settings.recordExpireMinutes + " 分钟"));

        stack.add(section("点位与记录"));
        stack.add(dataRow("标点点位", () -> "§f" + module.pointStore().pointsInCurrentDimension().size()
            + " 个 §8（本维度）"));
        stack.add(dataRow("处理记录", () -> "§f" + module.recordStore().size() + " 条 §8（当前服务器）"));

        stack.add(section("取物"));
        stack.add(dataRow("取物模式", () -> "§f" + settings.withdrawMode.displayName()));
        stack.add(dataRow("目标物品", AutoChestOverviewPage::targetItemsCountText));

        stack.add(section("自检"));
        List<String> missing = module.selfCheck();
        if (missing.isEmpty()) {
            stack.add(new Note(owner, "§a自检通过"));
        } else {
            for (String item : missing) stack.add(new Note(owner, item));
        }
    }

    /** 分区标题（与星露谷 / 挖矿控制台各页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /**
     * 只读数据行：标签 + 值写成一行 20px 的 {@link Note}。
     *
     * <p>不用 36px 的 {@code ConsoleRow} 画纯读数：本页 17 行加 5 个小标题用 Note 约 460px，
     * 一屏基本放得下；换 ConsoleRow 会到 660px 以上，必须滚三屏。</p>
     */
    private CompactElement dataRow(String label, Supplier<String> value) {
        return new Note(owner, "§7" + label + "  §f" + value.get());
    }

    /** 目标物品计数（与配置页逐字同源）：{@code 未选择目标（共 N 项）} / {@code 已选 X / N 项} */
    private static String targetItemsCountText() {
        int total = IdentityService.shared().itemCount();
        int selected = IdentityTargetConfig.selectedItemKeys().size();
        if (selected == 0) return "未选择目标（共 " + total + " 项）";
        return "已选 " + selected + " / " + total + " 项";
    }
}
