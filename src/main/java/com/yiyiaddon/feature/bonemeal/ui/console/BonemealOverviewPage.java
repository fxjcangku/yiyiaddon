package com.yiyiaddon.feature.bonemeal.ui.console;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.config.TargetList;
import com.yiyiaddon.feature.bonemeal.model.TriggerMode;
import com.yiyiaddon.feature.bonemeal.ui.BonemealConsoleScreen;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.render.world.ShapeMode;

/**
 * 控制台「概览」页：只读运行状态 + 设置项去处指引。
 *
 * <p><b>不放任何按钮</b>：旧项目 {@code bonemeal/AutoBoneMeal.java} 只有设置项，没有任何面板动作，
 * 凭第 163 / 171 条不得补「手动催熟」这类按钮。本页全部是只读行与文字提示，无任何控件。</p>
 *
 * <p><b>状态来源全部只读</b>：模块开关与运行状态读 {@link AutoBoneMealModule} 的只读接口
 * （旧 {@code getInfoString} 的 HUD 串也在这里显示，见 {@code hudStatus()}），
 * 服务器卡顿 / 反作弊读 {@link TacticalCoordinator}，本页只显示、不决策。</p>
 */
public final class BonemealOverviewPage {

    private static final String SECTION_KEY = "overview";

    private final BonemealConsoleScreen host;
    private final AutoBoneMealModule module;

    public BonemealOverviewPage(BonemealConsoleScreen host, AutoBoneMealModule module) {
        this.host = host;
        this.module = module;
    }

    public void build(CompactStack stack) {
        BonemealSettings settings = module.settings();

        FoldSection status = new FoldSection("§f当前状态", SECTION_KEY, host.collapsedSections());
        status.content().add(new TextLine("§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        status.content().add(new TextLine("§7触发模式：§f" + settings.triggerMode.label()
            + (settings.triggerMode == TriggerMode.范围自动扫描
                ? " §8（自动搜索周围所有目标）"
                : " §8（仅对准星看着的方块生效）")));
        status.content().add(new TextLine("§7目标方块：§f" + settings.totalTargetCount() + " 种"
            + groupDetail(settings)));
        if (settings.triggerMode == TriggerMode.范围自动扫描) {
            status.content().add(new TextLine("§7作用半径：§f" + settings.range + " 格"));
        }
        status.content().add(new TextLine("§7动作节流：§f" + settings.tickDelay + " Tick"
            + " §8/ §7每轮上限："
            + (settings.maxPerTick == 0 ? "§f不限制" : "§f" + settings.maxPerTick + " 块")));
        status.content().add(new TextLine("§7视角同步："
            + (settings.rotateSilent ? "§a§l已开启" : "§c§l已关闭")));

        String hud = module.hudStatus();
        status.content().add(new TextLine("§7骨粉："
            + (module.hasBoneMealInHand() ? "§a主手/副手有" : "§c无")
            + (module.pausedNoBoneMeal() ? " §8/ §c已暂停（等待骨粉）" : "")));
        status.content().add(new TextLine("§7候选 / 队列：§f" + module.candidateCount() + " 块 §8/ §f"
            + module.queueSize() + " 个 §8/ §7状态指示：" + (hud == null ? "§8无" : hud)));
        status.content().add(new TextLine("§7遮挡射线检测：" + onOff(settings.checkOcclusion)
            + " §8/ §7无目标提示：" + onOff(settings.noTargetHint)
            + " §8/ §7副手优先：" + onOff(settings.offhandFirst)
            + " §8/ §7摆动手臂：" + onOff(settings.swingHand)));
        status.content().add(new TextLine("§7卡顿自停：" + onOff(settings.respectLag)
            + " §8/ §7自动降速：" + onOff(settings.autoThrottle)
            + " §8/ §7服务器：" + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
            + " §8/ §7反作弊：" + antiCheatText()));
        status.content().add(new TextLine("§7启用ESP：" + onOff(settings.espEnabled)
            + " §8/ §7形状模式：§f" + ShapeMode.labels()[settings.shapeMode.ordinal()]
            + " §8/ §7准星提示：" + onOff(settings.crosshairHint)));

        FoldSection guide = new FoldSection("§f设置指引", SECTION_KEY + ":guide", host.collapsedSections());
        guide.content().add(new TextLine("  §8▸ §f触发模式 / 作用半径 / 准星提示 / 遮挡射线检测 / 无目标提示"
            + " §8▸ 在「" + BonemealTexts.GROUP_BASIC + "」页"));
        guide.content().add(new TextLine("  §8▸ §f五组目标方块名单（农作物 / 树苗 / 花卉 / 蘑菇 / 菌类 / 水下 / 下界）"
            + " §8▸ 在「" + BonemealTexts.GROUP_TARGETS + "」页，选择器支持搜索与整组全选"));
        guide.content().add(new TextLine("  §8▸ §f动作节流 / 每轮最大催熟数 / 视角静默同步 / 副手优先 / 摆动手臂 / "
            + "服务器卡顿自停 / 反作弊自动降速"
            + " §8▸ 在「" + BonemealTexts.GROUP_BYPASS + "」页"));
        guide.content().add(new TextLine("  §8▸ §f启用ESP / 形状模式 / 线框颜色 / 填充颜色"
            + " §8▸ 在「" + BonemealTexts.GROUP_ESP + "」页"));
        guide.content().add(new TextLine("  §8▸ §f改动即时保存 §8▸ 关闭窗口与重启游戏后仍然生效"));

        stack.add(status);
        stack.add(guide);
    }

    // ── 只读格式化 ──

    /** 五组名单的分组明细：{@code §8（农作物 11 / 树苗 9 / …）} */
    private static String groupDetail(BonemealSettings settings) {
        StringBuilder detail = new StringBuilder("§8（");
        TargetList[] groups = TargetList.values();
        for (int i = 0; i < groups.length; i++) {
            if (i > 0) detail.append(" §8/ §7");
            detail.append(groups[i].label()).append(' ').append(groups[i].of(settings).size());
        }
        return detail.append("§8）").toString();
    }

    /** 开关的只读显示：绿开 / 灰关（与旧项目「开=绿、关=红」的报告口径区分开，这里只是状态一览） */
    private static String onOff(boolean value) {
        return value ? "§a开" : "§8关";
    }

    /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
    private static String antiCheatText() {
        String name = TacticalCoordinator.getDetectedAntiCheat();
        if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
        if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
        return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
    }
}
