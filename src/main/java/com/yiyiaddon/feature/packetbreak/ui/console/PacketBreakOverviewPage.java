package com.yiyiaddon.feature.packetbreak.ui.console;

import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakSettings;
import com.yiyiaddon.feature.packetbreak.model.PacketBreakTarget;
import com.yiyiaddon.feature.packetbreak.model.TargetMode;
import com.yiyiaddon.feature.packetbreak.ui.PacketBreakConsoleScreen;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;

import java.util.Locale;

/**
 * 控制台「概览」页：只读运行状态 + 发包/显示开关现状 + 设置项去处指引。
 *
 * <p><b>不放任何按钮</b>：旧项目 {@code tactical/packetbreak/PacketInstantBreak.java} 的面板只有一个
 * 「查看使用说明」入口，没有「手动触发」这类动作，凭第 163 / 171 条不得补；本页全部是只读行与文字提示，
 * 无任何控件。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关、队列长度、已破坏数、失败冷却数与当前目标读
 * {@link PacketInstantBreakModule} 的只读接口；开关与名单项数读 {@link PacketBreakSettings}；
 * 卡顿 / 拉回冷却 / 反作弊读 {@link TacticalCoordinator} 的只读接口。本页只显示、不决策、不改任何状态。</p>
 *
 * <p><b>行文案口径</b>：标签逐字取自旧设置名（{@code 目标模式 / 破坏方式 / 方块间隔（tick）/
 * 自动换工具 / 转向发包 / 目标方块 / 显示进度 / 显示百分比 / 标签内容}），不另造一套说法。</p>
 */
public final class PacketBreakOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final PacketBreakConsoleScreen host;
    private final PacketInstantBreakModule module;

    public PacketBreakOverviewPage(PacketBreakConsoleScreen host, PacketInstantBreakModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        PacketBreakSettings settings = module.settings();

        FoldSection section = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        section.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        section.content().add(new TextLine("§7目标模式：§f" + settings.targetMode.displayName));
        section.content().add(new TextLine("§7破坏方式：§f" + settings.breakMode.displayName));
        if (settings.targetMode == TargetMode.RANGE) {
            section.content().add(new TextLine("§7扫描半径：§f" + settings.range + " §7格"));
            section.content().add(new TextLine("§7目标方块：§f" + module.targetBlockCount() + " §7项"
                + (module.targetBlockCount() == 0 ? " §8（留空 = 挖所有可破坏方块）" : "")));
        }
        section.content().add(new TextLine("§7方块间隔：§f" + settings.delay + " §7tick"));
        section.content().add(new TextLine("§7自动换工具：" + (settings.autoSwitch ? "§a开启" : "§8关闭")
            + " §8/ §7转向发包：" + (settings.rotate ? "§a开启" : "§8关闭")
            + " §8/ §7挥动手臂：" + (settings.swing ? "§a开启" : "§8关闭")));
        section.content().add(new TextLine("§7进度显示：" + renderSummary(settings)));

        // 运行数据：一眼看出「现在挖到哪、有多少没挖」
        FoldSection running = new FoldSection("§f运行数据", SECTION_KEY + ":running", host.collapsedSections());
        running.content().add(new TextLine("§7队列：§f" + module.queueSize() + " §7块"));
        running.content().add(new TextLine("§7当前目标：" + activeText()));
        running.content().add(new TextLine("§7本次已破坏：§f" + module.successCount() + " §7块"));
        running.content().add(new TextLine("§7失败冷却：" + (module.failureCooldownCount() > 0
            ? "§e" + module.failureCooldownCount() + " §7个坐标"
            : "§8无")));
        running.content().add(new TextLine("§7服务器状态："
            + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
            + " §8/ §7TPS §f" + String.format(Locale.ROOT, "%.1f", TacticalCoordinator.getCurrentTps())
            + " §8/ §7拉回冷却：" + (TacticalCoordinator.isRubberBandCooldown() ? "§e冷却中 2 秒" : "§8无")));
        running.content().add(new TextLine("§7反作弊：" + antiCheatText()));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f21 项设置 §8▸ 按旧项目 4 个分组名分页："
            + "目标选择 / 发包参数 / 防同步与反作弊 / 进度显示"));
        guide.content().add(new TextLine("  §8▸ §f条件项随开关出现 §8▸ 扫描半径与目标方块只在「范围自动」下、"
            + "显示类各项只在「显示进度」下、面色 / 线色跟随「框线样式」、"
            + "百分比颜色与标签内容还要「显示百分比」"));
        guide.content().add(new TextLine("  §8▸ §f服务器卡顿自停 §8▸ 服务器 TPS 过低或正处于拉回冷却时暂停发包"));
        guide.content().add(new TextLine("  §8▸ §f绕过反作弊 §8▸ 高风险反作弊（协调器确认）会自动改用原版速度阈值"));
        guide.content().add(new TextLine("  §8▸ §f进度框总闸 §8▸ 「ESP 全局设置 → 各模块 ESP → 发包秒破」可一处关掉本模块的全部绘制"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(section);
        stack.add(running);
        stack.add(guide);
    }

    /** 当前目标与进度：无目标压成灰字 */
    private String activeText() {
        PacketBreakTarget active = module.activeTarget();
        if (active == null) return "§8无";
        int percent = (int) Math.round(module.progressOf(active) * 100);
        return "§f" + active.pos.getX() + "," + active.pos.getY() + "," + active.pos.getZ()
            + " §8· §f" + percent + "%";
    }

    /** 显示口径：标签内容 + 百分比开关的现行取值（概览不用翻页就能看出会看到什么） */
    private String renderSummary(PacketBreakSettings settings) {
        if (!settings.render) return "§8关闭";
        return "§a开启 §8/ §7框线样式 §f" + settings.espStyle.displayName
            + " §8/ §7收缩 " + (settings.shrinkProgress ? "§a开" : "§8关")
            + " §8/ §7标签 §f" + (settings.showPercent ? settings.labelStyle.displayName : "不显示");
    }

    /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
    private static String antiCheatText() {
        String name = TacticalCoordinator.getDetectedAntiCheat();
        if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
        if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
        return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
    }
}
