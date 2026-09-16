package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;

import java.util.List;
import java.util.function.Supplier;

/**
 * 自动附魔控制台「概览」页：只读展示运行状态 / 点位 / 目标 / 自检四段。
 *
 * <p>本页不放任何可编辑控件；数据在构建时现读模块与点位（不为控制台给模块加任何业务接口），
 * 每秒随概览页整页重建一起刷新。文案口径与其它页一致：点位名与配色取
 * {@link EnchantPointType#titleColor()}（旧 {@code buildPointCard} 的 {@code titleColor}）、
 * 坐标串与未绑定占位取旧卡片原文、自检缺项原文照抄（不改色码）。</p>
 */
public final class EnchantOverviewPage {

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;

    public EnchantOverviewPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        EnchantPointStore store = module.pointStore();

        stack.add(section("运行状态"));
        stack.add(dataRow("状态机", () -> "§f" + module.fsm().state().cn()));
        stack.add(dataRow("模块", () -> module.isEnabled() ? "§a运行中" : "§8未启用"));
        stack.add(dataRow("维度", () -> "§f"
            + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension())));
        stack.add(dataRow("运行模式", () -> "§f" + module.currentRunMode().title()));
        stack.add(dataRow("世界绑定", () -> store.matchesCurrentContext()
            ? "§a当前世界与点位匹配" : "§6当前世界与点位不匹配"));

        stack.add(section("点位"));
        for (EnchantPointType type : EnchantPointType.values()) {
            stack.add(dataRow(type.titleColor() + type.title(), () -> pointDetail(type)));
        }

        stack.add(section("目标"));
        if (module.settings().targetMode == EnchantTargetMode.GEAR) {
            TargetProfile profile = module.gearProfile();
            stack.add(dataRow("目标装备", () -> profile == null ? "§8未选择" : "§f" + profile.gearName()));
            stack.add(dataRow("极品方案", () -> profile == null ? "§8未选择" : "§f" + profile.profileName()));
            stack.add(dataRow("目标附魔", () -> profile == null
                ? "§80 项" : "§f" + profile.activeTargets().size() + " 项"));
        } else {
            stack.add(dataRow("目标词条", () -> "§f" + module.settings().allSelected().size() + " 条"));
        }

        stack.add(section("自检"));
        List<String> missing = module.selfCheck();
        if (missing.isEmpty()) {
            stack.add(new Note(owner, "§a自检通过"));
        } else {
            for (String item : missing) stack.add(new Note(owner, item));
        }
    }

    /** 分区标题（与自动挖矿控制台各页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /** 只读数据行：标签 + 值写成一行 20px 的 {@link Note}（值在构建时取，概览页每秒整页重画） */
    private CompactElement dataRow(String label, Supplier<String> value) {
        return new Note(owner, "§7" + label + "  " + value.get());
    }

    /**
     * 点位明细：已绑定时坐标串 {@code §7X§f<x> §7Y§f<y> §7Z§f<z>} + 维度行；
     * 未绑定为旧卡片的两段占位 {@code §8暂未绑定} / {@code §8-}（合并进同一行，文字逐字保留）。
     */
    private String pointDetail(EnchantPointType type) {
        EnchantPoint point = module.pointStore().get(type);
        if (point == null) return "§8暂未绑定  §8-";
        return EnchantConsoleText.pointDetail(point, module.pointStore());
    }
}
