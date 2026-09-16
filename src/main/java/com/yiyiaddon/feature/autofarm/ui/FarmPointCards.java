package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.command.FarmCommand;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * 自动农场六张点位卡的唯一构建处：模块页与控制台「点位」页共用同一份数据与同一套按钮行为
 * （旧 {@code AutoFarmMatrix.buildLocationCard :757-796} 逐字换壳）。
 *
 * <p>卡片头图标（D9）：农田对角用小麦种子、四箱用箱子，未绑定也显示，不留空洞。</p>
 */
public final class FarmPointCards {

    private FarmPointCards() {
    }

    /** 按旧模块页的两列配对顺序构建六张卡片：点位1/点位2、单作物箱/多作物箱、种子补货箱/杂物箱 */
    public static List<PointCardGrid.PointCard> all(AutoFarmModule module) {
        return List.of(
            card(module, "农场点位1", SiteType.START, "§a"),
            card(module, "农场点位2", SiteType.END, "§e"),
            card(module, "单作物箱", SiteType.SINGLE_STORAGE, "§6"),
            card(module, "多作物箱", SiteType.MULTI_STORAGE, "§d"),
            card(module, "种子补货箱", SiteType.SEED_STORAGE, "§b"),
            card(module, "杂物箱", SiteType.POISON_STORAGE, "§c"));
    }

    /** 单张点位卡：标题色 + 坐标/维度两行（或 暂未绑定/-）+ 设置/删除按钮 */
    private static PointCardGrid.PointCard card(AutoFarmModule module, String title, SiteType type, String color) {
        FarmSite data = module.site(type);
        boolean isBound = data != null;

        String info1;
        String info2;
        if (isBound) {
            info1 = String.format("§7X§f%d §7Y§f%d §7Z§f%d",
                data.pos().getX(), data.pos().getY(), data.pos().getZ());
            info2 = "§7" + AutoFarmModule.dimensionName(data.dimension());
        } else {
            info1 = "§8暂未绑定";
            info2 = "§8-";
        }

        // 设置按钮：已绑定时绿色（旧 :780 的颜色条件原样）；未绑定时灰色
        Button setButton = new Button((isBound ? "§a" : "§8") + "设置", () -> {
            FarmCommand.setBinding(type);
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.setScreen(null);
        });
        Button deleteButton = new Button("§c删除", () -> {
            FarmCommand.removeBinding(type);
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.setScreen(null);
        });

        return new PointCardGrid.PointCard(color + title, info1, info2,
            List.of(List.of(setButton), List.of(deleteButton)))
            .icon(() -> cardIcon(type));
    }

    /** 卡片头图标（D9）：农田对角用小麦种子、四箱用箱子 */
    private static ItemStack cardIcon(SiteType type) {
        return switch (type) {
            case START, END -> new ItemStack(Items.WHEAT_SEEDS);
            case SINGLE_STORAGE, MULTI_STORAGE, SEED_STORAGE, POISON_STORAGE -> new ItemStack(Items.CHEST);
        };
    }
}
