package com.yiyiaddon.feature.librarian.ui.console;

import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.feature.librarian.fsm.LibrarianState;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

/**
 * 控制台「概览」页：只读实况（当前状态 / 目标附魔 / 搜索半径 / 最高价格 / 命中策略 / 背包 / 自检）。
 *
 * <p>整页纯读数无副作用，随宿主每秒整页重画（与自动村民交易 / 自动农场控制台概览页同口径）。
 * 文案形态沿用本项目控制台的 {@code §7标签 §8▸ 值} 写法。</p>
 *
 * <p><b>每个格子的数据来源</b>：状态取编排器（未启用给灰字 {@code §8未初始化}，等价旧
 * {@code getInfoString} 的「未初始化」分支）；其余四项现读设置载体；背包三项取模块的
 * {@link AutoLibrarianModule#countItem} 与自检同一口径（不另写一份统计）；自检格与模块启用前的
 * 自检调用同一方法（{@link AutoLibrarianModule#selfCheck()}）。</p>
 */
public final class LibrarianOverviewPage {

    private final LibrarianConsoleScreen host;
    private final AutoLibrarianModule module;

    public LibrarianOverviewPage(LibrarianConsoleScreen host, AutoLibrarianModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：状态 → 目标附魔 → 三项运行参数 → 背包 → 自检 */
    public void build(CompactStack stack) {
        stack.add(new Note(host, () -> "§7当前状态 §8▸ " + stateLine()));
        stack.add(new Note(host, () -> "§7目标附魔 §8▸ " + targetLine()));
        stack.add(new Note(host, () -> "§7搜索半径 §8▸ §f"
            + module.settings().searchRadius + " 格"));
        stack.add(new Note(host, () -> "§7最高价格 §8▸ §f"
            + module.settings().maximumEmeraldPrice + " 绿宝石"));
        stack.add(new Note(host, () -> "§7命中策略 §8▸ §f"
            + (module.settings().removeTargetOnFound ? "找到后移除" : "找到后保留")));
        stack.add(new Note(host, () -> "§7背包 §8▸ §f讲台 "
            + module.countItem(Items.LECTERN) + " · 书 " + module.countItem(Items.BOOK)
            + " · 绿宝石 " + module.countItem(Items.EMERALD)));
        stack.add(new Note(host, () -> "§7自检 §8▸ " + selfCheckLine()));
    }

    /** 当前状态行：运行时给状态机状态中文名，未运行给灰字（与自动村民交易概览页同一口径） */
    private String stateLine() {
        LibrarianState state = module.currentState();
        if (!module.isEnabled()) return "§8未启用";
        return state == null ? "§8未初始化" : "§f" + state.displayName();
    }

    /**
     * 目标附魔行：最多列前 3 个（与启动报告同一列法），超过 3 个给「等 N 种」。
     *
     * <p>一个都没勾选给灰字 {@code §8未勾选}（与自检文案 {@code §d目标附魔§f·未勾选} 同一用词）。</p>
     */
    private String targetLine() {
        List<String> ids = module.settings().targetEnchantments();
        if (ids.isEmpty()) return "§8未勾选";
        StringBuilder names = new StringBuilder();
        int shown = 0;
        for (String id : ids) {
            if (shown >= 3) break;
            String name = displayName(id);
            if (name == null) continue;
            if (shown > 0) names.append("§f、");
            names.append("§f").append(name).append(" Lv.").append(maxLevel(id));
            shown++;
        }
        if (ids.size() > 3) names.append("§f 等 §f").append(ids.size()).append("§f 种");
        return names.toString();
    }

    /** 自检行：缺项数（与运行时启用前自检同一判据），未进世界时自检为空列表故显示通过 */
    private String selfCheckLine() {
        List<String> missing = module.selfCheck();
        if (missing.isEmpty()) return "§a通过";
        StringBuilder line = new StringBuilder("§e缺 " + missing.size() + " 项 §8▸ ");
        for (int i = 0; i < missing.size(); i++) {
            if (i > 0) line.append("§f · ");
            line.append(missing.get(i));
        }
        return line.toString();
    }

    /** 附魔注册表（客户端未进世界时为 {@code null}） */
    private static Registry<Enchantment> enchantmentRegistry() {
        Minecraft client = Minecraft.getInstance();
        return client == null || client.level == null
            ? null : client.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
    }

    /** 附魔 ID → 本地化显示名；解析不出（旧档残留 / 附魔模组移除）返回 {@code null}，不猜名字 */
    private static String displayName(String id) {
        Enchantment enchantment = enchantmentOf(id);
        return enchantment == null ? null : enchantment.description().getString();
    }

    /** 附魔 ID → 该附魔的最高等级；解析不出返回 0（不伪造等级） */
    private static int maxLevel(String id) {
        Enchantment enchantment = enchantmentOf(id);
        return enchantment == null ? 0 : enchantment.getMaxLevel();
    }

    /** 注册表 ID → 附魔；解析失败返回 {@code null} */
    private static Enchantment enchantmentOf(String id) {
        if (id == null || id.isBlank()) return null;
        Registry<Enchantment> registry = enchantmentRegistry();
        if (registry == null) return null;
        Identifier identifier = Identifier.tryParse(id);
        if (identifier == null) return null;
        return registry.get(ResourceKey.create(Registries.ENCHANTMENT, identifier))
            .map(holder -> holder.value())
            .orElse(null);
    }
}
