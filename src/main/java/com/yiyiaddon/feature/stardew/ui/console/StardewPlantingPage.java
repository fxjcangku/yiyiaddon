package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.StardewTargetSelectScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingText;

import java.util.List;
import java.util.function.Supplier;

/**
 * 星露谷控制台「种植」页：七类选择器（点击打开各自的选择页）。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildPlanting} 与其配套私有方法；方法体、文案与
 * tooltip 一字未改，只把模块与宿主窗口访问改为经字段读取。</p>
 */
public final class StardewPlantingPage {

    /** 七类选择器：顺序、标题与描述逐字照旧项目设置定义（sgCrops 构造顺序） */
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
        new SelectorDef("洒水器", "选择要维护的洒水器", StardewSelectorCategory.SPRINKLER),
        new SelectorDef("温室玻璃", "选中后在盆上方 5 格内识别它：有玻璃的盆当季/非当季都能种（作物不会因季节枯萎）", StardewSelectorCategory.SHELTER)
    );

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    /** 状态文字列的最小宽度：资源未就绪时文案很短，列太窄会把刷新图标挤到文字上 */
    private static final float STATE_COLUMN_MIN = 120f;
    /** 与 {@code SettingText} 的字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

    /** 状态列当前宽度（只增不减，见 {@link #stateColumnWidth()}） */
    private float stateColumnWidth = STATE_COLUMN_MIN;

    public StardewPlantingPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        for (SelectorDef def : SELECTORS) {
            Button select = new Button("点击选择",
                () -> owner.client().setScreen(new StardewTargetSelectScreen(owner.client().screen, module, def.category)));
            // 空态禁用：判据来自 module.selection(def.category).selectedKeys()（与下面的清空动作同源，
            // 见 selectionKeys：它返回的就是这份选中的内存镜像，与模块运行时同一个 List 实例）；
            // 逐帧求值见 IconButton#disabledWhen(Supplier)
            IconButton reset = new IconButton(StardewConsoleScreen.GLYPH_RESET, () -> {
                List<String> keys = module.selection(def.category).selectedKeys();
                if (keys.isEmpty()) return;
                clearSelection(def.category);
                module.selection(def.category).persist();
            }).disabledWhen(() -> selectionKeys(def.category).isEmpty());
            stack.add(new ConsoleRow(owner, () -> def.name, def.description, null, List.of(
                new Ctl(select, "点击打开「" + def.category.title() + "」选择器"),
                // 状态文字左对齐 + 列宽按「同列最宽文本」：控件是整组右对齐的，列宽若用 SettingText
                // 默认的 200，实际文案只有一百来像素，多出来的空白会把「点击选择」顶到行中间去
                // （实机表现就是按钮看着没靠齐、位移了）；收紧到实际宽度后，按钮—文字—刷新图标
                // 紧挨成一组贴在行的右侧，且六行都成一条竖线。
                new Ctl(new SettingText(() -> selectorCountText(def.category), this::stateColumnWidth)
                    .alignLeft()),
                new Ctl(reset, "清空本行已选" + def.name))));
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

    /**
     * 状态列宽度（旧项目 WTable 的列宽口径）。
     *
     * <p>取六行里最宽的那条文本，并<b>只增不减</b>：某行从「未选择（共 18 项）」切成
     * 「已选 1 / 18 项」时，列宽若跟着缩小，整组控件（按钮、图标）会左右跳一下，
     * 看起来仍然像「在位移」。本页生命周期内已经量到的宽度不再回收。</p>
     */
    private float stateColumnWidth() {
        float max = STATE_COLUMN_MIN;
        for (SelectorDef def : SELECTORS) {
            max = Math.max(max, MinecraftText.measure(selectorCountText(def.category), STATE_FONT_SIZE, false));
        }
        stateColumnWidth = Math.max(stateColumnWidth, max);
        return stateColumnWidth;
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
            case SHELTER -> settings.selectedShelterKeys;
        };
    }
}
