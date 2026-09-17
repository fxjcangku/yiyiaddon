package com.yiyiaddon.feature.villager.ui.console;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.feature.villager.trade.TradeEngine;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * 控制台「概览」页：只读实况（当前状态 / 运行模式 / 目标职业 / 目标物品摘要 / 绿宝石数 /
 * 两个绑定箱的坐标与维度）。
 *
 * <p>整页纯读数无副作用，随宿主每秒整页重画（与自动农场控制台概览页同口径）。
 * 文案形态沿用本项目控制台的 {@code §7标签 §8▸ 值} 写法；坐标与维度两行逐字照旧项目点位卡
 * （旧 {@code :788-795}：{@code §7X§f… §7Y§f… §7Z§f…} 与灰字维度显示名），未绑定时给旧原文
 * {@code §8未绑定}（旧 {@code CunminCommand} 状态回执里的同一写法）。</p>
 *
 * <p><b>绿宝石数</b>取 {@link TradeEngine#countEmeralds()}（玩家背包 + 副手，含盔甲槽），
 * 与模块自己的补给判据同源，不另写一份统计。</p>
 */
public final class VillagerOverviewPage {

    /** 绿宝石箱标题（旧 {@code :780} 的卡片标题字面量） */
    private static final String EMERALD_TITLE = "绿宝石箱";
    /** 成品交易箱标题（旧 {@code :780} 的卡片标题字面量） */
    private static final String UNLOAD_TITLE = "成品交易箱";

    private final VillagerConsoleScreen host;
    private final AutoVillagerTradeModule module;
    /** 两箱绑定的只读视图（旧 {@code CunminCommand.getBinding()} 的等价物；每次构造取一份新视图） */
    private final VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();

    public VillagerOverviewPage(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：状态 → 模式 → 职业 → 目标物品 → 绿宝石数 → 两箱实况 */
    public void build(CompactStack stack) {
        stack.add(new Note(host, () -> "§7当前状态 §8▸ " + stateLine()));
        stack.add(new Note(host, () -> "§7运行模式 §8▸ §f" + module.settings().mode));
        stack.add(new Note(host, () -> "§7目标职业 §8▸ " + professionLine()));
        stack.add(new Note(host, () -> "§7目标物品 §8▸ " + targetLine()));
        stack.add(new Note(host, () -> "§7绿宝石数 §8▸ §f" + TradeEngine.countEmeralds() + " 个"));
        stack.add(new Note(host, () -> boxLine(EMERALD_TITLE,
            binding.getEmeraldBox(), binding.getEmeraldBoxDimension())));
        stack.add(new Note(host, () -> boxLine(UNLOAD_TITLE,
            binding.getUnloadBox(), binding.getUnloadBoxDimension())));
    }

    /** 当前状态行：运行时给状态机状态中文名，未运行给灰字（与自动农场概览页同一口径） */
    private String stateLine() {
        return module.isEnabled()
            ? "§f" + module.fsm().getCurrentState().cn()
            : "§8未启用";
    }

    /**
     * 目标职业行：非多任务给当前目标职业，多任务给已勾选职业（顺序 = 队列顺序）。
     *
     * <p>可见职业名单取 {@link VillagerSettingsPage#visibleProfessions(AutoVillagerTradeModule)}
     * （等价旧 {@code isProfessionVisible :294-301}），与设置页铺分组用的是同一判据。</p>
     */
    private String professionLine() {
        List<String> visible = VillagerSettingsPage.visibleProfessions(module);
        if (visible.isEmpty()) return "§8未勾选";
        return "§f" + String.join("§f、", visible);
    }

    /**
     * 目标物品摘要行。
     *
     * <p>做什么：只有一个可见职业时列出它已选物品的本地化名（旧启动报告
     * {@code announceStartup} 的「目标物品」列法）；多任务多个职业时列「职业 N 项」，
     * 避免十几个职业的物品名拼成一行读不完。一个都没选给灰字 {@code §8未选择}
     * （旧自检文案 {@code §e目标物品§f·未选择} 的同一用词）。</p>
     */
    private String targetLine() {
        List<String> visible = VillagerSettingsPage.visibleProfessions(module);
        if (visible.isEmpty()) return "§8未选择";
        if (visible.size() == 1) {
            String names = itemNames(visible.get(0));
            return names.isEmpty() ? "§8未选择" : names;
        }
        StringBuilder summary = new StringBuilder();
        for (String professionName : visible) {
            int count = module.settings().itemTargets(professionName).size();
            if (count == 0) continue;
            if (summary.length() > 0) summary.append("§f、");
            summary.append("§f").append(professionName).append(" ").append(count).append(" 项");
        }
        return summary.length() == 0 ? "§8未选择" : summary.toString();
    }

    /** 某职业已选物品的本地化名（解析不出的注册表 ID 直接跳过：旧档残留不猜、不补默认物品） */
    private String itemNames(String professionName) {
        StringBuilder names = new StringBuilder();
        for (String itemId : module.settings().itemTargets(professionName)) {
            Item item = itemOf(itemId);
            if (item == null) continue;
            if (names.length() > 0) names.append("§f、");
            names.append("§f").append(item.getDefaultInstance().getHoverName().getString());
        }
        return names.toString();
    }

    /**
     * 单个绑定箱的实况行。
     *
     * <p>已绑定：{@code §7X§f… §7Y§f… §7Z§f… §8· §7<维度显示名>}（坐标写法逐字照旧 {@code :788}，
     * 维度显示名走 {@link WorldContextFormatter#dimensionSummary(String)}，等价旧
     * {@code getDimensionDisplayName :831-835}）；未绑定：旧原文 {@code §8未绑定}。</p>
     */
    private String boxLine(String title, BlockPos pos, String dimensionId) {
        if (pos == null) return "§7" + title + " §8▸ §8未绑定";
        return "§7" + title + " §8▸ §7X§f" + pos.getX() + " §7Y§f" + pos.getY() + " §7Z§f" + pos.getZ()
            + " §8· §7" + WorldContextFormatter.dimensionSummary(dimensionId);
    }

    /** 注册表 ID → 物品；解析失败（旧档残留 / 拼写错误 / 非物品 ID / 空气）返回 {@code null} */
    private static Item itemOf(String itemId) {
        if (itemId == null || itemId.isBlank()) return null;
        Identifier identifier = Identifier.tryParse(itemId);
        if (identifier == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(identifier);
        return item == null || item == Items.AIR ? null : item;
    }
}
