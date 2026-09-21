package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingSegmented;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 控制台「种植区域」列表页：逐条换作物 / 删除 + 清空全部区域。
 *
 * <p>结构逐条照 {@link StardewSprinklerListScreen}：每行是一块地，行尾两个动作只作用于这一块，
 * 底部「清空全部区域」只清区域，不动箱子 / 补水点 / 洒水器。</p>
 *
 * <p><b>「换作物」</b>（实机反馈：换个品种只能删了重圈，而区域是一格一格点出来的）：走项目现成的
 * 作物选择器（单选模式，点一下即生效），只改绑定品种，范围 / 序号 / 维度一律不动；换完重建本页，
 * 列表行、世界里的区域字牌、错位播报都读同一份 {@code Region.cropName()}，因此同步更新。</p>
 *
 * <p><b>「删除」</b>的语义是「<b>不再管这块地</b>」：不种、不收、不浇，ESP 也不画；地里的作物不会被挖掉，
 * 因此本页不做「确认后再删」——真要停手时少一步。</p>
 */
public final class StardewRegionListScreen extends PanelScreen {

    private final Screen parent;
    private final StardewFarmModule module;

    public StardewRegionListScreen(Screen parent, StardewFarmModule module) {
        super("种植区域", parent);
        this.parent = parent;
        this.module = module;
        build();
    }

    private void build() {
        List<StardewRegionManager.Region> regions = module.regionsInDimension();
        addSectionTitle("§7§l已划分 §f" + regions.size() + " §7§l个区域");
        if (regions.isEmpty()) {
            addField("§7区域", "§8暂未划分");
        } else {
            for (StardewRegionManager.Region region : regions) {
                // 图标先取好再喂给行：行每帧都会向 Supplier 取图标，供应商里不能做资源解析
                ItemStack icon = module.cropIcon(region.cropKey());
                content().add(new CompactRow("§f" + label(region), () -> hint(region),
                    new SettingSegmented(List.of("换作物", "删除"), index -> {
                        if (index == 0) {
                            openCropPicker(region);
                        } else {
                            module.removeRegion(region.index());
                            rebuild();
                        }
                    })).icon(() -> icon));
            }
        }

        addGap();
        addDivider();
        addButtons(new Button("§c清空全部区域", this::confirmClearAll).danger(),
            new Button("§7返回", this::requestClose));
        content().add(new TextLine("§8「换作物」只换这块地的品种，范围不动；「删除」只删这块地，地里的作物不会被挖掉"));
    }

    /**
     * 打开项目现成的作物选择器（单选模式）：点一下即写回并重建本页。
     *
     * <p>选择器自己不关窗（见 {@link StardewTargetSelectScreen} 单选模式的说明），由这里重建列表；
     * 否则它关闭时的「回上级窗口」会把刚重建的新列表盖掉。</p>
     */
    private void openCropPicker(StardewRegionManager.Region region) {
        if (minecraft == null) return;
        minecraft.gui.setScreen(new StardewTargetSelectScreen(this, module, StardewSelectorCategory.CROP,
            region.cropKey(), crop -> {
                String failure = module.changeRegionCrop(region.index(), crop.cropKey(), crop.chineseName());
                Minecraft client = Minecraft.getInstance();
                if (client == null) return;
                if (failure != null) {
                    // 没换成（地里还有作物 / 区域档写不进去）：原因摆到屏幕中间，聊天里也有一份，
                    // 别让人以为「点了没反应」（与启动自检同一套面板与配色）。
                    // 用原地版：点掉后回本列表继续看/继续换，而不是把整个界面一起关掉回游戏
                    // （用户 2026-09-22：「二次确认之后就直接关闭 ui 了」这一类的收尾口径）
                    client.gui.setScreen(ConfirmPanelScreen.noticeInPlace("星露谷农场 · 没换成",
                        "§7这块地还没清出来：", List.of(failure), this));
                    return;
                }
                client.gui.setScreen(new StardewRegionListScreen(parent, module));
            }));
    }

    /** 删除后整页重建：行数、计数都以磁盘为准 */
    private void rebuild() {
        if (minecraft != null) minecraft.gui.setScreen(new StardewRegionListScreen(parent, module));
    }

    private static String label(StardewRegionManager.Region region) {
        return "区域 " + region.index() + "  §8·  §7" + region.cropName()
            + "  §8·  §7" + region.rangeText() + "  §8·  §7" + region.cellCount() + " 格";
    }

    /** 悬停说明：维度 + 洒水器覆盖（紧凑行的提示是单行纯文本，不带颜色码） */
    private String hint(StardewRegionManager.Region region) {
        String dimension = WorldContextFormatter.dimensionSummary(region.dimension());
        return "维度 " + (dimension == null ? "未知" : dimension) + " · " + module.regionSprinklerInfo(region);
    }

    private void confirmClearAll() {
        if (minecraft == null) return;
        // 原地版确认窗：确认后回控制台（它 init() 会重建正文），而不是把整个界面关掉回游戏
        // —— 用户 2026-09-22：「二次确认之后就直接关闭 ui 了，不应该到模块设置页面吗」
        // 数量先摆出来（原来只有执行后的回执卡片在报，而卡片在面板里看不见）；口径同 ID 配置的清空确认窗
        // —— 取本服全部区域（各维度合计），与 clearRegions 清掉的完全一致，而不是本页只列的本维度
        int count = module.regions().size();
        minecraft.gui.setScreen(ConfirmPanelScreen.inPlace("清空全部种植区域",
            List.of("§c§l将删除本服已划分的 " + count + " 个种植区域",
                "§7地里的作物不会被挖掉，只是这些地不再被管理",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearRegions, parent));
    }
}
