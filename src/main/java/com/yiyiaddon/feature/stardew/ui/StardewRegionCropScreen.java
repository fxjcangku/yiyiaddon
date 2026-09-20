package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 圈地收口窗口：两个角点完之后问「这块地种什么」。
 *
 * <p><b>为什么要有这一步：</b>原来区域模式的作物是<b>提前</b>选的（控制台卡片上的「作物」按钮，
 * 没选过就默认取第一个已勾选作物），圈完地直接就落盘成那种作物。勾了好几种时，玩家点完角
 * 才发现地绑的不是自己要的那一种，还得删了重圈（实机反馈）。
 * 现在把决定权放到最后一步：点一下作物按钮即建区，所见即所得。</p>
 *
 * <p>列表只列<b>已勾选</b>的作物（与「种植」页同一份），顺序也一致；下界 / 末地作物带上分组标签，
 * 与选择器里的标记同一套口径，免得在地里才发现种错盆型。关掉窗口（Esc / ←）视为放弃这块地，
 * 选区模式一并退出，不留半成品。</p>
 */
public final class StardewRegionCropScreen extends PanelScreen {

    /** 玩家是否已经点定了作物：没点就关窗 = 放弃，要按取消走 */
    private final Runnable onCancel;
    private boolean picked;

    /**
     * @param parent   上级窗口；传 {@code null} 表示关掉后直接回游戏
     * @param onPick   点定作物（作物键, 作物中文名）
     * @param onCancel 没点任何作物就关窗时的兜底
     */
    public StardewRegionCropScreen(Screen parent, StardewFarmModule module, BlockPos first, BlockPos second,
                                   BiConsumer<String, String> onPick, Runnable onCancel) {
        super("这块地种什么", parent);
        exitToGame();
        this.onCancel = onCancel;
        buildContent(module, first, second, onPick);
    }

    private void buildContent(StardewFarmModule module, BlockPos first, BlockPos second,
                              BiConsumer<String, String> onPick) {
        addField("范围", rangeText(first, second));
        addField("格子数", cellCount(first, second) + " 格");
        addGap();
        addSectionTitle("§b§l▌ 点一下决定这块地种什么");

        List<String> keys = module.selectedCropKeys();
        if (keys.isEmpty()) {
            content().add(new TextLine("§c已勾选的作物在当前资源里都失效了"));
            content().add(new TextLine("§8请先去「作物」选择器重新勾选，再回来圈地"));
        } else {
            for (String cropKey : keys) {
                String name = module.cropDisplayName(cropKey);
                // 作物图标（用户 2026-09-22：「区域选择没有显示农作物图标」）：先取好再喂给按钮 ——
                // 按钮每帧都要问一次供应商（测量宽度 + 绘制），资源解析不能放在里面
                ItemStack icon = module.cropIcon(cropKey);
                content().add(new ButtonRow(
                    new Button(label(cropKey, name), () -> pick(cropKey, name, onPick)).itemIcon(() -> icon)));
            }
        }
        addGap();
        content().add(new TextLine("§8点一个作物即建区；Esc / ← 取消，不写入任何区域"));
    }

    /** 作物按钮文案：下界 / 末地作物带分组标签，与选择器同一套标记 */
    private static String label(String cropKey, String name) {
        PotGroup group = CropPotGroups.of(cropKey);
        return switch (group) {
            case NETHER -> "§c[下界]§r " + name;
            case END -> "§5[末地]§r " + name;
            case NORMAL -> name;
        };
    }

    private void pick(String cropKey, String name, BiConsumer<String, String> onPick) {
        if (picked) return;
        picked = true;
        onPick.accept(cropKey, name);
        requestClose();
    }

    /** 与区域落盘后的回执同一口径：{@code X19860~19870 Z-1630~-1620} */
    private static String rangeText(BlockPos a, BlockPos b) {
        return "X" + Math.min(a.getX(), b.getX()) + "~" + Math.max(a.getX(), b.getX())
            + " Z" + Math.min(a.getZ(), b.getZ()) + "~" + Math.max(a.getZ(), b.getZ());
    }

    /** 区域格数按 XZ 平面计，与 {@code StardewRegionManager.Region#cellCount()} 一致 */
    private static int cellCount(BlockPos a, BlockPos b) {
        return (Math.abs(a.getX() - b.getX()) + 1) * (Math.abs(a.getZ() - b.getZ()) + 1);
    }

    @Override
    public void removed() {
        super.removed();
        if (!picked) onCancel.run();
    }
}
