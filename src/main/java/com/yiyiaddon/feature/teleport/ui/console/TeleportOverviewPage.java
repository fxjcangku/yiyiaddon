package com.yiyiaddon.feature.teleport.ui.console;

import com.yiyiaddon.feature.teleport.TeleportModule;
import com.yiyiaddon.feature.teleport.config.TeleportSettings;
import com.yiyiaddon.feature.teleport.model.TeleportContext;
import com.yiyiaddon.feature.teleport.ui.TeleportConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;

import java.util.Locale;

/**
 * 控制台「概览」页：只读状态 + 触发方式提示。
 *
 * <p><b>不放手动触发按钮</b>：旧项目只有三个功能键与 {@code .tp} 指令，没有面板按钮，
 * 凭第 163 / 171 条不得补（登记 D-17-08）。本页全部是只读行与文字提示，无任何控件。</p>
 */
public final class TeleportOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final TeleportConsoleScreen host;
    private final TeleportModule module;

    public TeleportOverviewPage(TeleportConsoleScreen host, TeleportModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        TeleportSettings settings = module.settings();
        TeleportContext last = module.coordinator().last();

        FoldSection section = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());

        section.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        section.content().add(new TextLine("§7状态机："
            + (module.coordinator().busy() ? "§e传送中（等待服务端权威位置或验证窗口超时）" : "§8空闲")));
        section.content().add(new TextLine("§7最近一次：" + describeLast(last)));
        section.content().add(new TextLine("§7验证参数：§f回弹阈值 "
            + String.format(Locale.ROOT, "%.2f", settings.verifyThreshold)
            + " §7格 §8/ §f验证窗口 §f" + settings.verifyWindow + " §7tick"));
        section.content().add(new TextLine("§7点位参数：§f坐标 §7X§f" + settings.coordX
            + " §7Y§f" + settings.coordY + " §7Z§f" + settings.coordZ
            + " §8/ §7回退半径 §f" + settings.fallbackRadius + " §7格"));

        FoldSection how = new FoldSection("§f触发方式", SECTION_KEY + ":how", host.collapsedSections());
        how.content().add(new TextLine("  §8▸ §fTP地面键 §8▸ 回到头顶真正的露天地面（洞穴脱身）"));
        how.content().add(new TextLine("  §8▸ §fTP穿墙键 §8▸ 沿准星方向智能落点：可穿墙 / 门窗 / 半砖，也可当方向赶路"));
        how.content().add(new TextLine("  §8▸ §fTP坐标键 §8▸ 传送到控制台「TP坐标」页里配置的坐标"));
        how.content().add(new TextLine("  §8▸ §f指令 §8▸ §f.tp X Y Z §7（当前维度，整数坐标；模块未开启时不传送）"));
        how.content().add(new TextLine("  §8▸ §f按键可在「触发按键」页绑定，松开时触发；模块未开启时按键只提醒不传送"));

        stack.add(section);
        stack.add(how);
    }

    /** 最近一次传送的一句话摘要（没有则「无」） */
    private static String describeLast(TeleportContext last) {
        if (last == null || last.mode == null) return "§8无";

        StringBuilder sb = new StringBuilder("§f" + last.mode);
        if (last.target != null) {
            sb.append(" §8▸ §f").append(last.target.posText());
        }
        if (last.rubberbandDist >= 0) {
            sb.append(" §8▸ §c被服务端修正 ")
                .append(String.format(Locale.ROOT, "%.1f", last.rubberbandDist)).append(" 格");
        }
        return sb.toString();
    }
}
