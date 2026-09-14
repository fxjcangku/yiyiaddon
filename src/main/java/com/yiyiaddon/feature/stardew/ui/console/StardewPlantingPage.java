package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.StardewTargetSelectScreen;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Ctl;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.ConsoleRow;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Note;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingText;

import java.util.List;

/**
 * 星露谷控制台「种植」页：六类选择器（点击打开各自的选择页）。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildPlanting} 与其配套私有方法；方法体、文案与
 * tooltip 一字未改，只把模块与宿主窗口访问改为经字段读取。</p>
 */
public final class StardewPlantingPage {

    /** 六类选择器：顺序、标题与描述逐字照旧项目设置定义（sgCrops 构造顺序） */
    private static final class SelectorDef {
        private final String name;
        private final String description;
        private final StardewSelectorCategory category;

        private SelectorDef(String name, String description, StardewSelectorCategory category) {
            this.name = name;
            this.description = description;
            this.category = category;
        }
    }

    private static final List<SelectorDef> SELECTORS = List.of(
        new SelectorDef("种植作物", "选择要种植的目标作物（成熟产物展示 + 关联种子）", StardewSelectorCategory.CROP),
        new SelectorDef("种植盆", "选择要管理的种植盆类型", StardewSelectorCategory.POT),
        new SelectorDef("肥料", "选择使用的肥料（需开启自动施肥）", StardewSelectorCategory.FERTILIZER),
        new SelectorDef("魔法药剂", "选择使用的魔法药剂（需开启自动用药剂）", StardewSelectorCategory.POTION),
        new SelectorDef("水壶", "选择浇水用的水壶；选中即自动联动补水——用尽自动回补水点、一次连发补满", StardewSelectorCategory.WATERING_CAN),
        new SelectorDef("洒水器", "选择要维护的洒水器", StardewSelectorCategory.SPRINKLER)
    );

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    public StardewPlantingPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        for (SelectorDef def : SELECTORS) {
            Button select = new Button("点击选择",
                () -> owner.client().setScreen(new StardewTargetSelectScreen(owner.client().screen, module, def.category)));
            IconButton reset = new IconButton(StardewConsoleScreen.GLYPH_RESET, () -> {
                List<String> keys = module.selection(def.category).selectedKeys();
                if (keys.isEmpty()) return;
                clearSelection(def.category);
                module.selection(def.category).persist();
            });
            stack.add(new ConsoleRow(owner, () -> def.name, def.description, null, List.of(
                new Ctl(select, "点击打开「" + def.category.title() + "」选择器"),
                new Ctl(new SettingText(() -> selectorCountText(def.category))),
                new Ctl(reset))));
        }
        stack.add(new Note(owner, "§8肥料 / 药剂只表示「用哪个」，要真正生效还得在「运行」页打开自动施肥 / 自动用药剂"));
    }

    /** 清空某类别的内存选择（旧 {@code setting.reset()} 的等价物：清空选中集合） */
    private void clearSelection(StardewSelectorCategory category) {
        selectionKeys(category).clear();
    }

    /**
     * 选择状态文本（逐字照旧 {@code StardewTargetSetting.countText()}）：
     * 索引为空 = 当前服务器资源尚未就绪，带上真实阶段；否则给已选 / 总数。
     */
    private String selectorCountText(StardewSelectorCategory category) {
        int total = category == StardewSelectorCategory.CROP
            ? module.index().crops().size() : module.index().entriesFor(category).size();
        if (total == 0) return "§8资源未就绪（" + ResourceExtractionService.phase().label() + "）";
        int selected = selectionKeys(category).size();
        if (selected == 0) return "未选择（共 " + total + " 项）";
        return "已选 " + selected + " / " + total + " 项";
    }

    /** 该类别的内存选择集合（与模块运行时使用的是同一个 List 实例） */
    private List<String> selectionKeys(StardewSelectorCategory category) {
        StardewSettings settings = module.settings();
        return switch (category) {
            case CROP -> settings.selectedCropKeys;
            case POT -> settings.selectedPotKeys;
            case FERTILIZER -> settings.selectedFertilizerKeys;
            case POTION -> settings.selectedPotionKeys;
            case WATERING_CAN -> settings.selectedCanKeys;
            case SPRINKLER -> settings.selectedSprinklerKeys;
        };
    }
}
