package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * 杀戮光环控制台「概览」页：只读展示运行状态 / 目标 / 自检三段。
 *
 * <p>本页不放任何可编辑控件；数据全部在页面构建时现读模块状态（不为控制台给模块加任何接口），
 * 每秒随概览页整页重建一起刷新。段标题与取值口径照挖矿控制台概览页：数据行用
 * {@link ConsoleRow#liveComment} 的值位（行尾注释位）显示，自检缺项原文照抄（不改色码）。</p>
 */
public final class KillAuraOverviewPage {

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;

    public KillAuraOverviewPage(KillAuraConsoleScreen owner, KillAuraModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        KillAuraSettings settings = module.settings();

        stack.add(section("运行状态"));
        stack.add(dataRow("模块", () -> module.isEnabled() ? "§a运行中" : "§8未启用"));
        stack.add(dataRow("状态", this::stateText));
        stack.add(dataRow("武器白名单", () -> nameList(settings.weapons, true)));

        stack.add(section("目标"));
        stack.add(dataRow("目标实体", () -> nameList(settings.entityTypes, false)));
        stack.add(dataRow("攻击范围", () -> "§f" + format(settings.range)));
        stack.add(dataRow("穿墙范围", () -> "§f" + format(settings.wallsRange)));

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

    /** 只读数据行：标签定死，值每帧现读（概览页整页重建时也会重新构建一行） */
    private CompactElement dataRow(String label, Supplier<String> value) {
        return ConsoleRow.liveComment(owner, () -> label, null, value, List.of());
    }

    /**
     * 「状态」格的取值：已锁定的目标数量与当前目标名。
     *
     * <p>模块只暴露首个目标（{@code KillAuraModule.getTarget()}），因此数量按「有 / 无目标」计
     * （0 或 1）；无目标显示 {@code §8无}。</p>
     */
    private String stateText() {
        Entity target = module.getTarget();
        if (target == null) return "§f0  §8无";
        return "§f1  §f" + target.getName().getString();
    }

    /** 已选名单的显示名（空则 {@code §8未选择}）；{@code weapon} 为真时按武器白名单解析 */
    private static String nameList(List<String> ids, boolean weapon) {
        if (ids.isEmpty()) return "§8未选择";
        StringBuilder text = new StringBuilder();
        for (String id : ids) {
            if (text.length() > 0) text.append(' ');
            text.append(weapon ? KillAuraGeneralPage.weaponDisplayName(id)
                : KillAuraTargetingPage.entityDisplayName(id));
        }
        return text.toString();
    }

    /** 距离保留一位小数（与设置行的 {@code %.1f} 同一口径） */
    private static String format(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }
}
