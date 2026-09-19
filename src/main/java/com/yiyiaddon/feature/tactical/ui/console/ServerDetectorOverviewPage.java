package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.feature.tactical.ServerDetectorModule;
import com.yiyiaddon.feature.tactical.config.ServerDetectorSettings;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.ServerDetectorConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;

import java.util.Locale;

/**
 * 控制台「概览」页：只读状态 + 识别层次与设置项去处指引。
 *
 * <p><b>不放任何策略按钮</b>：旧项目 {@code tactical/ServerDetector.java} 的面板只有说明与资源包目录
 * 两个入口，没有「手动检测」这类动作，凭第 163 / 171 条不得补；本页全部是只读行与文字提示。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关读 {@link ServerDetectorModule}；服务器核心 / 反作弊 /
 * 累计拉回 / 拉回冷却 / 卡顿 / TPS 读 {@link TacticalCoordinator} 的只读接口，本页只显示、不决策。</p>
 */
public final class ServerDetectorOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final ServerDetectorConsoleScreen host;
    private final ServerDetectorModule module;

    public ServerDetectorOverviewPage(ServerDetectorConsoleScreen host, ServerDetectorModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        ServerDetectorSettings settings = module.settings();

        FoldSection section = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        section.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        section.content().add(new TextLine("§7服务器核心：" + coreText()));
        section.content().add(new TextLine("§7反作弊：" + antiCheatText()));
        section.content().add(new TextLine("§7累计拉回：§f" + TacticalCoordinator.getRubberBandTotal() + " §7次"
            + " §8/ §7拉回冷却：" + (TacticalCoordinator.isRubberBandCooldown() ? "§e冷却中 2 秒" : "§8无")));
        section.content().add(new TextLine("§7服务器状态："
            + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
            + " §8/ §7TPS §f" + String.format(Locale.ROOT, "%.1f", TacticalCoordinator.getCurrentTps())));
        section.content().add(new TextLine("§7进服侦测延迟：§f" + settings.detectDelay + " §7秒"
            + " §8/ §7后续复核间隔 §f5 §7秒"));

        FoldSection layers = new FoldSection("§f识别层次", SECTION_KEY + ":layers", host.collapsedSections());
        layers.content().add(new TextLine("  §8├─ §7Brand字符串 §8- §7最容易被改，只作线索"));
        layers.content().add(new TextLine("  §8├─ §7插件消息频道 §8- §7反作弊开的校验频道"));
        layers.content().add(new TextLine("  §8├─ §7指令树命名空间 §8- §7插件注册的实际结果（主要依据）"));
        layers.content().add(new TextLine("  §8└─ §7拉回频率 §8- §7说明反作弊存在且激进"));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f检测服务器核心 / 检测反作弊 / 公屏播报 / 侦测延迟（秒）"
            + " §8▸ 在「底裤侦测」页"));
        guide.content().add(new TextLine("  §8▸ §f资源包模式 / 重试次数 / 读取超时（秒）/ 断点续传"
            + " §8▸ 在「资源包劫持」页（后三项只在 §7自动白嫖§8 下出现）"));
        guide.content().add(new TextLine("  §8▸ §f打开资源包缓存目录 §8▸ 「资源包劫持」页的 "
            + "§b查看下载资源包"));
        guide.content().add(new TextLine("  §8▸ §f检测结论统一上报协调器 §8▸ 模块只观测，不做任何绕过决策"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(section);
        stack.add(layers);
        stack.add(guide);
    }

    /** 服务器核心显示名：未知 / 未检测时压成灰字 */
    private static String coreText() {
        String name = TacticalCoordinator.getDetectedServerCore();
        if (name == null || name.isBlank()) return "§8未知";
        if ("未知".equals(name) || "未检测".equals(name)) return "§8" + name;
        return "§6" + name;
    }

    /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
    private static String antiCheatText() {
        String name = TacticalCoordinator.getDetectedAntiCheat();
        if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
        if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
        return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
    }
}
