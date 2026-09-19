package com.yiyiaddon.feature.vision.ui.console;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;

/**
 * 透视控制台「概览」页：两个模式各一行实时读数 + 扫描说明。
 *
 * <p>纯读数页，不含任何可交互控件，因此可以每秒整页重画（{@link VisionConsoleScreen} 的刷新口径）。
 * 数值全部取模块的当刻状态，不另存一份副本（第 169 条）。</p>
 */
public final class VisionOverviewPage {

    private final VisionConsoleScreen owner;
    private final VisionModule module;

    public VisionOverviewPage(VisionConsoleScreen owner, VisionModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        VisionSettings settings = module.settings();

        stack.add(new Note(owner, () -> blockLine(settings)));
        stack.add(new Note(owner, () -> entityLine(settings)));

        if (!settings.blockEnabled && !settings.entityEnabled) {
            stack.add(new Note(owner, "§7两个模式都关着：到「" + VisionTexts.TAB_BLOCK + "」「"
                + VisionTexts.TAB_ENTITY + "」页打开对应开关，并选好目标"));
        } else {
            stack.add(new Note(owner, () -> scanLine()));
        }
    }

    /** 方块模式读数：开关 / 目标数 / 命中数 / 范围 / 扫描状态 */
    private String blockLine(VisionSettings settings) {
        String range = "§7范围 §f" + settings.blockRange + " §7格（上下各 "
            + com.yiyiaddon.feature.vision.scan.BlockTargetScanner.VERTICAL_REACH + " 格）";
        if (!settings.blockEnabled) {
            return "§7方块透视：" + VisionTexts.OFF + " §8· " + range;
        }
        int hits = module.blockScanner().total();
        int drawn = module.blockScanner().visible().size();
        String hitsText = hits > drawn ? "§f" + drawn + " §7/ 共 §f" + hits : "§f" + hits;
        return "§7方块透视：" + VisionTexts.ON
            + " §8· §7目标 §f" + settings.blockTargets.size() + " §7项"
            + " §8· §7命中 " + hitsText + " §7个 §8· " + range;
    }

    /** 实体模式读数：开关 / 目标数 / 命中数 / 范围 */
    private String entityLine(VisionSettings settings) {
        String range = "§7范围 §f" + settings.entityRange + " §7格（竖直不限制）";
        if (!settings.entityEnabled) {
            return "§7实体透视：" + VisionTexts.OFF + " §8· " + range;
        }
        return "§7实体透视：" + VisionTexts.ON
            + " §8· §7目标 §f" + settings.entityTargets.size() + " §7项"
            + " §8· §7命中 §f" + module.entityScanner().visible().size() + " §7个 §8· " + range;
    }

    /** 扫描进度：某一轮尚未走完时报百分比，走完报「已完成」 */
    private String scanLine() {
        if (!module.blockScanner().scanning()) {
            return "§7方块扫描：" + VisionTexts.SCAN_DONE;
        }
        int percent = Math.round(module.blockScanner().progress() * 100f);
        return "§7方块扫描：§f" + percent + "% §8· §7正在按区块轮转（每刻 "
            + com.yiyiaddon.feature.vision.scan.BlockTargetScanner.CHUNKS_PER_TICK + " 个区块）";
    }
}
