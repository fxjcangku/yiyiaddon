package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.core.CommandMessageFormatter;
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
 * 自动农场五张点位卡的唯一构建处：控制台「点位」页用它铺卡
 * （旧 {@code AutoFarmMatrix.buildLocationCard :757-796} 逐字换壳）。
 *
 * <p><b>模块页不再摆这些卡</b>（用户 2026-09-17：「控制台里面已经有点位了 为什么控制台外面还有
 * 自动农村也有这个问题」）：旧项目把卡挂在模块配置页（旧 {@code :717-734}），本项目点位统一由控制台
 * 「点位」页承载，故本类只被 {@code FarmPointPage} 调用一处，不存在两份卡片。</p>
 *
 * <p><b>农田两个角合成一张卡</b>（用户 2026-09-18）：改左右键点选后两角一次写入，见 {@link #areaCard}。</p>
 *
 * <p>卡片头图标（D9）：农田范围用小麦种子、四箱用箱子，未绑定也显示，不留空洞。</p>
 */
public final class FarmPointCards {

    private FarmPointCards() {
    }

    /** 按两列配对顺序构建五张卡片：农田范围（两角合一）、单作物箱、多作物箱、种子补货箱、杂物箱 */
    public static List<PointCardGrid.PointCard> all(AutoFarmModule module) {
        return List.of(
            areaCard(module),
            card(module, "单作物箱", SiteType.SINGLE_STORAGE, "§6"),
            card(module, "多作物箱", SiteType.MULTI_STORAGE, "§d"),
            card(module, "种子补货箱", SiteType.SEED_STORAGE, "§b"),
            card(module, "杂物箱", SiteType.POISON_STORAGE, "§c"));
    }

    /**
     * 农田范围卡：<b>两个角合成一张</b>（用户 2026-09-18：「还是有两个点位了 现在不是标点选点就好了吗」）。
     *
     * <p>改成左右键点选之后，两个角是<b>一次点完同时写入</b>（左键一角、右键对角），再摆成两张卡片
     * 只会让人以为要各点一次、各删一次。卡片上把两角坐标并排写出来，范围与所在维度写在第二行，
     * 「设置」与「删除」也都按整块地一次做完（删除会同时删掉两个角）。</p>
     */
    private static PointCardGrid.PointCard areaCard(AutoFarmModule module) {
        FarmSite start = module.site(SiteType.START);
        FarmSite end = module.site(SiteType.END);
        boolean bound = start != null && end != null;

        String info1;
        String info2;
        if (bound) {
            info1 = String.format("§7X§f%d §7Z§f%d §8→ §7X§f%d §7Z§f%d",
                start.pos().getX(), start.pos().getZ(), end.pos().getX(), end.pos().getZ());
            int rangeX = Math.abs(end.pos().getX() - start.pos().getX()) + 1;
            int rangeZ = Math.abs(end.pos().getZ() - start.pos().getZ()) + 1;
            info2 = "§7范围 §f" + rangeX + "×" + rangeZ + " §8· §7Y§f" + start.pos().getY()
                + " §8· §7" + AutoFarmModule.dimensionName(start.dimension());
        } else if (start != null || end != null) {
            // 只绑上一个角（坏档 / 旧档）时说清楚，避免玩家看到「半个范围」以为绑定成功了
            info1 = "§8范围不完整 §7（只绑了一个角）";
            info2 = "§8-";
        } else {
            info1 = "§8暂未绑定";
            info2 = "§8-";
        }

        Button setButton = new Button((bound ? "§a" : "§8") + "设置", () -> {
            // 两个角一起重设：农场范围模式的「设置」进的是同一套点选，与点的是哪一角无关
            String failure = module.siteSelector().enter(SiteType.START);
            if (failure != null) {
                CommandMessageFormatter.sendLine(AutoFarmModule.MESSAGE_MODULE, "§6" + failure);
                return;
            }
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.setScreen(null);
        });
        Button deleteButton = new Button("§c删除", () -> {
            // 一次删掉整块地：两个角属于同一个范围，留一个下来只会变成绑不完整的坏状态
            FarmCommand.removeBinding(SiteType.START);
            FarmCommand.removeBinding(SiteType.END);
            Minecraft client = Minecraft.getInstance();
            if (client != null) client.setScreen(null);
        });

        return new PointCardGrid.PointCard("§a农田范围", info1, info2,
            List.of(List.of(setButton), List.of(deleteButton)))
            .icon(() -> new ItemStack(Items.WHEAT_SEEDS));
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
        // 行为（有意差异，用户 2026-09-18 口径）：进入游戏内的点选模式 —— 手持任意物品，
        // 农田用「左键一角 + 右键对角」一次成区，箱子用「左键点容器」；旧项目是「准星对准方块 + 点按钮」。
        // 失败原因仍走聊天栏播报并留在控制台，成功才关窗回游戏选点。
        Button setButton = new Button((isBound ? "§a" : "§8") + "设置", () -> {
            String failure = module.siteSelector().enter(type);
            if (failure != null) {
                CommandMessageFormatter.sendLine(AutoFarmModule.MESSAGE_MODULE, "§6" + failure);
                return;
            }
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

    /** 卡片头图标（D9）：农田范围用小麦种子、四箱用箱子 */
    private static ItemStack cardIcon(SiteType type) {
        return switch (type) {
            case START, END -> new ItemStack(Items.WHEAT_SEEDS);
            case SINGLE_STORAGE, MULTI_STORAGE, SEED_STORAGE, POISON_STORAGE -> new ItemStack(Items.CHEST);
        };
    }
}
