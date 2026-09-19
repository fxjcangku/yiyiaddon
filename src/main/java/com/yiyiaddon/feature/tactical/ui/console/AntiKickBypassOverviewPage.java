package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.core.net.SendDelayService;
import com.yiyiaddon.feature.tactical.AntiKickBypassModule;
import com.yiyiaddon.feature.tactical.config.AntiKickBypassSettings;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.AntiKickBypassConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;

import java.util.Locale;

/**
 * 控制台「概览」页：只读运行状态 + 伪装/防护开关现状 + 设置项去处指引。
 *
 * <p><b>不放任何按钮</b>：旧项目 {@code tactical/AntiKickBypass.java} 的面板只有一个「查看使用说明」
 * 入口，没有「手动触发」这类动作，凭第 163 / 171 条不得补；本页全部是只读行与文字提示，无任何控件。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关、限速倍率、待发聊天条数读
 * {@link AntiKickBypassModule} 的只读接口；伪装/防护各开关读 {@link AntiKickBypassSettings}；
 * 服务器核心 / 反作弊 / 累计拉回 / 拉回冷却 / 卡顿 / TPS 读 {@link TacticalCoordinator} 的只读接口；
 * 延迟队列待发数读 {@link SendDelayService#pendingCount()}。本页只显示、不决策、不改任何状态。</p>
 *
 * <p><b>行文案口径</b>：开关行标签逐字取自模块自身的进服伪装自检播报
 * （{@code announceDisguiseReady} 的「客户端名 / Mod通信 / 假潜行 / 假疾跑」）与旧设置名
 * （「防挂机检测 / 限制挖掘速度 / 限制放置速度 / 视角抖动 / 开启拉回分析」），不另造一套说法。</p>
 */
public final class AntiKickBypassOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final AntiKickBypassConsoleScreen host;
    private final AntiKickBypassModule module;

    public AntiKickBypassOverviewPage(AntiKickBypassConsoleScreen host, AntiKickBypassModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();

        FoldSection section = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        section.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        section.content().add(new TextLine("§7发包限速：" + throttleText()));
        section.content().add(new TextLine("§7聊天排队：" + (settings.enableChatQueue
            ? "§a开启 §8/ §7间隔 §f" + settings.chatInterval + " ms §8/ §7待发 §f"
                + module.pendingChatCount() + " §7条"
            : "§8关闭")));
        section.content().add(new TextLine("§7网络延迟：" + (settings.enableNetworkDelay
            ? "§a开启 §8/ §7" + Math.min(settings.minDelay, settings.maxDelay) + "~"
                + Math.max(settings.minDelay, settings.maxDelay) + " ms §8/ §7待发 §f"
                + SendDelayService.pendingCount() + " §7包"
            : "§8关闭")));
        section.content().add(new TextLine("§7服务器状态："
            + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
            + " §8/ §7TPS §f" + String.format(Locale.ROOT, "%.1f", TacticalCoordinator.getCurrentTps())));
        section.content().add(new TextLine("§7累计拉回：§f" + TacticalCoordinator.getRubberBandTotal() + " §7次"
            + " §8/ §7拉回冷却：" + (TacticalCoordinator.isRubberBandCooldown() ? "§e冷却中 2 秒" : "§8无")));
        section.content().add(new TextLine("§7反作弊：" + antiCheatText()));

        // 伪装与防护开关现状：与进服时的聊天自检同一批判据，进控制台不用翻聊天记录
        FoldSection switches = new FoldSection("§f伪装与防护开关", SECTION_KEY + ":switches",
            host.collapsedSections());
        switches.content().add(new TextLine("  §8├─ §f客户端名 §8▸ "
            + (settings.fakeBrand ? "§a伪装 vanilla" : "§8未伪装")));
        switches.content().add(new TextLine("  §8├─ §fMod通信 §8▸ "
            + (settings.blockModChannels ? "§a拦截中" : "§8未拦截")));
        switches.content().add(new TextLine("  §8├─ §f假潜行 §8▸ "
            + (settings.blockFakeSneak ? "§a拦截中" : "§8未拦截")));
        switches.content().add(new TextLine("  §8├─ §f假疾跑 §8▸ "
            + (settings.blockFakeSprint ? "§a拦截中" : "§8未拦截")));
        switches.content().add(new TextLine("  §8├─ §f防挂机检测 §8▸ "
            + (settings.antiAfk ? "§a开启" : "§8关闭")));
        switches.content().add(new TextLine("  §8├─ §f限制挖掘速度 §8▸ " + (settings.limitDigging
            ? "§a开启 §8/ §7设置 §f" + settings.maxDigPerSecond + " §7个/秒"
            : "§8关闭")));
        switches.content().add(new TextLine("  §8├─ §f限制放置速度 §8▸ " + (settings.limitInteract
            ? "§a开启 §8/ §7设置 §f" + settings.maxInteractPerSecond + " §7个/秒"
            : "§8关闭")));
        switches.content().add(new TextLine("  §8├─ §f视角抖动 §8▸ " + (settings.enableViewShake
            ? "§a开启 §8/ §7幅度 §f" + String.format(Locale.ROOT, "%.1f", settings.shakeIntensity) + "°"
            : "§8关闭")));
        switches.content().add(new TextLine("  §8└─ §f开启拉回分析 §8▸ " + (settings.enableAnalysis
            ? "§a开启 §8/ §7累积阈值 §f" + settings.analysisThreshold + " §7次"
            : "§8关闭")));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f18 项设置 §8▸ 按旧项目 6 个分组名分页："
            + "伪装客户端 / 聊天排队 / 防挂机 / 限制发包 / 拉回分析 / 模拟真人"));
        guide.content().add(new TextLine("  §8▸ §f条件项随开关出现 §8▸ 每条消息间隔只在「开启聊天排队」下、"
            + "挖掘/放置阈值各自跟随对应限速开关、累积几次后分析跟随「开启拉回分析」、"
            + "抖动幅度跟随「视角抖动」、最小/最大延迟跟随「网络延迟」"));
        guide.content().add(new TextLine("  §8▸ §f拉回冷却与统计 §8▸ 统一由协调器登记，本模块只做分析与播报"));
        guide.content().add(new TextLine("  §8▸ §f高风险反作弊 §8▸ 协调器确认后限速自动收紧（阈值打五折），"
            + "不改用户设置"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(section);
        stack.add(switches);
        stack.add(guide);
    }

    /** 动态限速倍率：被打折时黄字并注明原因，正常为白字 */
    private String throttleText() {
        int percent = (int) Math.round(module.throttleFactor() * 100);
        return percent >= 100 ? "§f" + percent + "%" : "§e" + percent + "% §8（高风险反作弊收紧）";
    }

    /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
    private static String antiCheatText() {
        String name = TacticalCoordinator.getDetectedAntiCheat();
        if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
        if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
        return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
    }
}
