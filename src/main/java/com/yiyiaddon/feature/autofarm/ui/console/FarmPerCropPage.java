package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 控制台「逐作物」页：15 个逐作物动态设置项的入口（D3 拍板——独立窗口、按作物折叠）。
 *
 * <p>旧项目该组设置跟随「作物选择」联动平铺（启用了几种作物显示几种配置）；本项目按 D3
 * 把它们收进 {@link FarmCropConfigScreen} 独立窗口，本页只留一行入口 + 一句旧项目说明原文
 * （「启用几种作物 §8- §7面板就自动显示几种独立配置」的使用说明口径）。</p>
 */
public final class FarmPerCropPage {

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    public FarmPerCropPage(AutoFarmConsoleScreen host, AutoFarmModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：空态提示 / 入口按钮 */
    public void build(CompactStack stack) {
        List<CropProfile> enabled = module.getEnabledCrops().stream()
            .filter(profile -> !profile.junk())
            .toList();

        if (enabled.isEmpty()) {
            stack.add(new Note(host, "  §8未启用任何作物：先到「设置 → 作物选择」里勾选，再回来配独立数量"));
        } else {
            stack.add(new Note(host, () -> "§7当前启用 §f" + enabled.size()
                + " §7种作物，共 " + itemCount(enabled) + " 项独立配置"));
        }

        stack.add(new com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip(host, List.of(
            new com.yiyiaddon.ui.console.ConsoleWidgets.Ctl(
                new Button("§b逐作物独立配置", () -> host.client().gui.setScreen(
                    new FarmCropConfigScreen(host, module))),
                "按作物分组折叠：卸货数量 / 补货种子数量，行文案与旧项目逐字一致")),
            com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip.BUTTON_HEIGHT));
    }

    /** 实际项数：每种非杂物作物 1 个卸货项，需补种的再加 1 个补货项（旧 :181-208 生成式） */
    private static int itemCount(List<CropProfile> enabled) {
        int count = 0;
        for (CropProfile profile : enabled) {
            count++;
            if (profile.needsReplant()) count++;
        }
        return count;
    }
}
