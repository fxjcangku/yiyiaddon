package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.feature.tactical.FlightBypassModule;
import com.yiyiaddon.feature.tactical.config.FlightBypassSettings;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.FlightBypassConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;

import java.util.Locale;

/**
 * 控制台「概览」页：只读状态 + 五种模式的一句话说明 + 设置项去处指引。
 *
 * <p><b>不放任何按钮</b>：旧项目 {@code tactical/FlightBypass.java} 只有模式与参数，没有面板动作，
 * 凭第 163 / 171 条不得补「手动触发」这类按钮。本页全部是只读行与文字提示，无任何控件。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关与当前模式读 {@link FlightBypassSettings}；
 * 服务器核心 / 反作弊 / 累计拉回 / 拉回冷却 / 卡顿 / TPS 读
 * {@link TacticalCoordinator} 的只读接口，本页只显示、不决策。</p>
 */
public final class FlightBypassOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final FlightBypassConsoleScreen host;
    private final FlightBypassModule module;

    public FlightBypassOverviewPage(FlightBypassConsoleScreen host, FlightBypassModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        FlightBypassSettings settings = module.settings();

        FoldSection section = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());

        section.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        section.content().add(new TextLine("§7当前设置模式：§f" + settings.mode.displayName));
        section.content().add(new TextLine("§7自适应降级：" + (settings.adaptiveSlowdown
            ? "§a开 §8（连续拉回 §f" + settings.rubberBandThreshold + " §8次即降一档）"
            : "§8关")));
        section.content().add(new TextLine("§7服务器核心：§f" + TacticalCoordinator.getDetectedServerCore()
            + " §8/ §7反作弊：" + antiCheatText()));
        section.content().add(new TextLine("§7累计拉回：§f" + TacticalCoordinator.getRubberBandTotal() + " §7次"
            + " §8/ §7拉回冷却：" + (TacticalCoordinator.isRubberBandCooldown() ? "§e冷却中 2 秒" : "§8无")));
        section.content().add(new TextLine("§7服务器状态："
            + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
            + " §8/ §7TPS §f" + String.format(Locale.ROOT, "%.1f", TacticalCoordinator.getCurrentTps())));

        FoldSection modes = new FoldSection("§f模式说明", SECTION_KEY + ":modes", host.collapsedSections());
        modes.content().add(new TextLine("  §8├─ §e发包飞行 §8- §7需服务端授予飞行能力（/fly/创造/旁观）"));
        modes.content().add(new TextLine("  §8├─ §e原版连跳（非飞行） §8- §7落地即跳的连续兔子跳 + 疾跑推进"));
        modes.content().add(new TextLine("  §8├─ §e安全滑翔 §8- §7自动换鞘翅 + 官方起伞"));
        modes.content().add(new TextLine("  §8├─ §e烟花火箭 §8- §7滑翔中周期性使用烟花推进"));
        modes.content().add(new TextLine("  §8└─ §e序列垫脚 §8- §7真实放置方块提供物理支撑"));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f飞行模式 §8▸ 在「模式选择」页切换（五模式互斥）"));
        guide.content().add(new TextLine("  §8▸ §f滑翔速度 / 跳跃间隔 / 垫脚延迟 §8▸ 在「参数调整」页，"
            + "只在对应模式下出现（§7安全滑翔 / 原版连跳 / 序列垫脚§8）"));
        guide.content().add(new TextLine("  §8▸ §f自适应降级 / 拉回降级阈值 §8▸ 在「参数调整」页，"
            + "阈值随自适应降级开启才出现"));
        guide.content().add(new TextLine("  §8▸ §f报警与降级全程由协调器裁决 §8▸ 模块不自行切换模式"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(section);
        stack.add(modes);
        stack.add(guide);
    }

    /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
    private static String antiCheatText() {
        String name = TacticalCoordinator.getDetectedAntiCheat();
        if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
        if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
        return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
    }
}
