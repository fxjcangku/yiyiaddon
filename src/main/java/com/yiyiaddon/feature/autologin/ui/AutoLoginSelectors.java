package com.yiyiaddon.feature.autologin.ui;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

/**
 * 自动登入两个物品项（{@code 菜单工具} / {@code 自用菜单工具}）的唯一接线处：打开选择器、状态文字、行内图标。
 *
 * <p><b>用的是既有通用选择器</b>（{@link SelectorScreen#pick}），本类不新造任何控件（第 126 / 130 / 153 条）：
 * 单选形态（点任一行即选中并关窗）对应旧项目的旧框架 {@code ItemSetting}——旧壳同样是「挑一个物品」，
 * 没有「已选名单」可加减，也没有「清空」动作；<b>但选中要回执</b>：点行即关窗，若不弹一句，
 * 关窗后玩家只能靠行上的物品名变化判断挑没挑上（见 {@link #pick}）。</p>
 *
 * <p><b>框架适配</b>：旧项目由旧框架序列化 {@code Item} 对象，本项目落盘为物品登记 ID 字符串
 * （见 {@link AutoLoginSettings} 类注释第 1 条），判定处用 {@link AutoLoginSettings#itemOf} 还原成物品比较，
 * 语义不变。</p>
 *
 * <p><b>候选表复用，不留两份</b>（第 169 条）：物品候选取自自动挖矿的
 * {@link MiningRegistry#itemEntries()}，与透视 / 保留白名单同一来源、同一过滤口径；
 * 空气在界面层剔除（{@link MiningRegistry#isAirItem}，与 {@code MiningTargetControls#openKeepSelector} 同一表达式）。</p>
 *
 * <p><b>时序</b>：候选表构建会访问物品注册表，只在「打开选择器」时惰性构建并缓存，
 * 不在模块构造或初始化阶段调用（第 180 条）。</p>
 */
public final class AutoLoginSelectors {

    /** 物品候选缓存（注册表运行期不变，构建一次即可；已剔除空气） */
    private static List<SelectorScreen.Entry> itemCandidates;

    private AutoLoginSelectors() {
    }

    /** 打开「菜单工具」选择器（写 {@code menuToolId}） */
    public static void openMenuToolSelector(Screen parent, AutoLoginSettings settings) {
        pick(parent, AutoLoginTexts.NAME_MENU_TOOL, id -> {
            settings.menuToolId = id;
        });
    }

    /** 打开「自用菜单工具」选择器（写 {@code leyuanMenuToolId}） */
    public static void openLeyuanMenuToolSelector(Screen parent, AutoLoginSettings settings) {
        pick(parent, AutoLoginTexts.NAME_LEYUAN_MENU_TOOL, id -> {
            settings.leyuanMenuToolId = id;
        });
    }

    private static void pick(Screen parent, String settingName, Consumer<String> onPick) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || settingName == null) return;
        client.gui.setScreen(SelectorScreen.pick(AutoLoginTexts.SELECT_TITLE_PREFIX + settingName, parent,
            itemCandidates(), id -> {
                onPick.accept(id);
                // 单选窗（SelectorScreen.pick）点行即关窗，那套「已添加 / 已移除」回执走的是
                // 加减两栏，这里没有，因此单独补一条「已选择 <物品名>」：
                // 关窗后回落到控制台窗口，弹窗正好在那儿显示，玩家能看到自己挑中了什么
                SelectionReceipt.selected(MiningRegistry.itemDisplayName(id));
            }));
    }

    /** 行状态文字：未选 → {@code 未选择}；已选 → 物品显示名（旧设置行同样显示该项名称） */
    public static String statusText(String itemId) {
        return AutoLoginSettings.itemOf(itemId) == null
            ? AutoLoginTexts.ITEM_UNSELECTED
            : MiningRegistry.itemDisplayName(itemId);
    }

    /** 行内图标；未选择返回 {@code null}（{@code ConsoleRow#icon} 对 null 既不画也不占横向空间） */
    public static ItemStack iconOf(String itemId) {
        Item item = AutoLoginSettings.itemOf(itemId);
        return item == null ? null : new ItemStack(item);
    }

    /** 物品候选：全物品里剔除空气（空气＝「未选择」，不作为候选项） */
    private static List<SelectorScreen.Entry> itemCandidates() {
        if (itemCandidates == null) {
            itemCandidates = MiningRegistry.filter(MiningRegistry.itemEntries(),
                key -> !MiningRegistry.isAirItem(key));
        }
        return itemCandidates;
    }
}
