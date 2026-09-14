package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.ui.StardewConsoleData;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Note;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Table;
import com.yiyiaddon.ui.component.CompactStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷控制台「概览」页。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildOverview}；方法体、文案与结构一字未改，
 * 只把模块快照改为从宿主窗口读取。</p>
 */
public final class StardewOverviewPage {

    private final StardewConsoleScreen owner;

    public StardewOverviewPage(StardewConsoleScreen owner) {
        this.owner = owner;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        StardewConsoleData data = owner.data();
        String detail = data.taskDetail();
        if (detail != null && !detail.isBlank()) {
            stack.add(new Note(owner, detail));
        }

        stack.add(new Note(owner, "§7§l作物进度", null, StardewConsoleScreen.SECTION_HEIGHT, StardewConsoleScreen.SECTION_SIZE));

        if (data.crops().isEmpty()) {
            stack.add(new Note(owner, "§7还没选作物：去「种植」页选一种，这里会显示每种作物的目标盆数与背包库存"));
        } else {
            List<String> header = List.of("§7§l作物", "§7§l目标盆数", "§7§l背包种子", "§7§l背包成品");
            List<List<String>> rows = new ArrayList<>();
            for (StardewConsoleData.CropRow crop : data.crops()) {
                rows.add(List.of(
                    "§f§l" + crop.name(),
                    "§b" + crop.quota() + " §7盆",
                    (crop.seeds() > 0 ? "§a" + crop.seeds() : "§70") + " §7粒",
                    (crop.produce() > 0 ? "§e" + crop.produce() : "§70") + " §7个"));
            }
            stack.add(new Table(header, rows, new float[]{0.40f, 0.19f, 0.20f, 0.21f}));
        }

        stack.add(new Note(owner, "§7目标盆数 = 这种作物最多占多少盆（在「后勤」页设置）；种子 / 成品是背包里的当前数量，不含种子箱。"
            + "多选作物时按「种植」页里的顺序依次分盆"));
    }
}
