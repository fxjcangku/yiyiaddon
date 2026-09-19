package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;

/**
 * 控制台「概览」页：只读实况（当前状态 / 启用作物 / 锄地 / 收割模式 / 补种模式 /
 * 六点位状态 / 背包空格）——纯读数，不再设使用说明入口
 * （用户 2026-09-17 口径：说明正文内嵌在模块页 {@code AutoFarmPage} 里，本页不重复摆按钮）。
 *
 * <p>整页纯读数无副作用，随宿主每秒整页重画（与挖矿控制台概览页同口径）。</p>
 */
public final class FarmOverviewPage {

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmOverviewPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：状态行 → 六点位实况 → 背包空格 */
    public void build(com.yiyiaddon.ui.component.CompactStack stack) {
        var settings = module.settings();

        stack.add(new Note(host, () -> "§7当前状态 §8▸ " + stateLine()));
        stack.add(new Note(host, () -> "§7启用作物 §8▸ " + cropLine()));
        stack.add(new Note(host, () -> "§7锄地开关 §8▸ " + (settings.autoTill ? "§a开" : "§c关")));
        stack.add(new Note(host, () -> "§7收割模式 §8▸ §f" + settings.harvestMode));
        stack.add(new Note(host, () -> "§7补种模式 §8▸ §f" + settings.plantMode));

        // 六点位实况：绑定与否一目了然（✓/✗ 用色与挖矿控制台点位格同一口径）
        for (SiteType type : SiteType.values()) {
            stack.add(new Note(host, pointLine(type)));
        }

        stack.add(new Note(host, () -> "§7背包空格 §8▸ §f" + module.observer().freeInventorySlots() + " 格"));
    }

    /** 当前状态行：运行时给状态机状态名，未运行给灰字 */
    private String stateLine() {
        return module.isEnabled() ? "§f" + module.controller().state().cn() : "§8未启用";
    }

    /** 启用作物行：中文名拼接，未选择灰字（与状态条同一口径） */
    private String cropLine() {
        var enabled = module.getEnabledCrops();
        if (enabled.isEmpty()) return "§8未选择";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (var crop : enabled) {
            if (i++ > 0) sb.append("§f、");
            sb.append("§f").append(crop.displayName());
        }
        return sb.toString();
    }

    /** 单个点位实况行（✓ 绿 / ✗ 灰，文案为锚点中文名） */
    private String pointLine(SiteType type) {
        boolean bound = module.site(type) != null;
        return "§7" + type.cn() + " §8▸ " + (bound ? "§a✓ 已绑定" : "§8✗ 未绑定");
    }
}
