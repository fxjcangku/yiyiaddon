package com.yiyiaddon.feature.librarian.ui;

import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.feature.librarian.config.LibrarianSettings;
import com.yiyiaddon.feature.librarian.ui.console.LibrarianConsoleScreen;
import com.yiyiaddon.feature.villager.data.VillagerProfessionRegistry;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 自动图书管理员「目标附魔」选择器（旧 {@code EnchantmentListSetting :53-62} 的等价界面）。
 *
 * <p><b>候选集不再照抄旧项目的「注册表整表」</b>：旧 {@code EnchantmentListSetting} 的候选来自
 * {@code registryAccess.lookupOrThrow(ENCHANTMENT).keySet()}，那个年代注册表里只有原版附魔，等价于「能刷出来的那些」；
 * 本服注册表里多了一百多条自定义附魔（服务器数据包注册），照抄就会列出一堆模块永远匹配不上的目标，
 * 所以改成按「村民能刷出什么」取池（见下）。</p>
 *
 * <p><b>候选 = 村民真能刷出来的附魔</b>（用户 2026-09-18：「图书管理员 刷不出来这些附魔书你弄上去有什么意义」）：
 * 取值域取 {@link VillagerProfessionRegistry#getLibrarianEnchantments()}，与自动村民交易同源，
 * 自定义附魔 / 诅咒 / 宝藏类一律不在候选里；八个原版家族按类型分组，兜底「其他」只在家族表漏项时兜住。</p>
 *
 * <p><b>写入格式</b>：键是带命名空间的完整附魔 ID（{@code minecraft:efficiency}）。
 * 命中判据 {@code EnchantmentMatcher} 要求存储值等于报价侧
 * {@code ResourceKey.identifier().toString()}，故键必须带命名空间、不能只存 path。</p>
 *
 * <p><b>分组</b>：按附魔家族（8 组 + 兜底「其他」）铺候选，家族表与自动村民交易的附魔池分节同源；
 * 因该模块批次4 收口前包结构冻结（第 68 条），本表暂留本站一份，已登记 D-15-11 待其收口后收敛为一处。
 * 兜底组保证「认不出家族的附魔」仍然可选，不漏项。</p>
 *
 * <p><b>写回与落盘</b>：加减一个候选即整表写回设置载体并立即落盘（第 172/173 条），
 * 重启后仍在；{@code onAdd} 用候选键集二次校验，手改存档越界也加不进来。</p>
 */
public final class LibrarianSelectors {

    /** 分组标题前缀（与项目其它选择器一致的分组标题写法：{@code §b§l▌ }） */
    private static final String GROUP_PREFIX = "§b§l▌ ";
    /** 兜底分组标题（认不出家族的候选归到这一组，避免漏项） */
    private static final String GROUP_OTHER = "§7§l▌ 其他";
    /** 设置行右侧的入口按钮文案（与自动村民交易 / 自动农场的选择器行同款） */
    private static final String CONFIG_BUTTON = "§b配置";
    /** 设置名（旧 {@code :54} 逐字） */
    private static final String TARGET_LABEL = "目标附魔";
    /**
     * 设置描述：旧 {@code :57} 逐字，其后追加一句候选范围说明。
     *
     * <p>用户 2026-09-18 看着候选里的自定义附魔问「图书管理员刷不出来这些附魔书你弄上去有什么意义」，
     * 于是候选收窄到村民真能刷出的池（见 {@link #enchantEntries}）——顺手把这句写清楚，
     * 免得以后再有人疑惑「灵魂疾行怎么不在列表里」。</p>
     */
    private static final String TARGET_DESCRIPTION = "选择附魔类型，模块自动使用该附魔的最高交易等级。"
        + "候选只列村民能刷出来的附魔，宝藏类附魔（灵魂疾行 / 迅捷潜行等）不在其中。";
    /** 行尾实时计数的写法前缀 */
    private static final String SELECTED_PREFIX = "已选 ";

    /** 原版命名空间：候选只收这个命名空间的附魔（见 {@link #enchantEntries}） */
    private static final String VANILLA_NAMESPACE = "minecraft";

    /** 中文排序器：候选与分组内按本地化名稳定排序（与其它选择器同一做法） */
    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final LibrarianSettings DEFAULTS = new LibrarianSettings();

    /**
     * 附魔家族表（组名 + 该家族的附魔 path，顺序照旧项目附魔池注释里的分节）。
     *
     * <p>为什么用固定表而不是猜前缀：{@code smite / bane_of_arthropods} 这类名字与「武器 / 盔甲」
     * 没有字符串关系，按名字猜必然分错；照分节抄，每条附魔归哪一家可逐条对回注册表注释行。</p>
     */
    private static final List<EnchantFamily> ENCHANT_FAMILIES = List.of(
        new EnchantFamily("武器附魔", List.of("sharpness", "smite", "bane_of_arthropods",
            "knockback", "fire_aspect", "looting", "sweeping_edge")),
        new EnchantFamily("工具附魔", List.of("efficiency", "silk_touch", "fortune")),
        new EnchantFamily("弓箭附魔", List.of("power", "punch", "flame", "infinity")),
        new EnchantFamily("弩附魔", List.of("piercing", "multishot", "quick_charge")),
        new EnchantFamily("盔甲附魔", List.of("protection", "fire_protection", "blast_protection",
            "projectile_protection", "feather_falling", "thorns", "respiration",
            "depth_strider", "aqua_affinity", "frost_walker")),
        new EnchantFamily("钓鱼附魔", List.of("luck_of_the_sea", "lure")),
        new EnchantFamily("通用附魔", List.of("mending", "unbreaking")),
        new EnchantFamily("三叉戟附魔", List.of("loyalty", "impaling", "riptide", "channeling")));

    private LibrarianSelectors() {
    }

    /** 设置页用的「目标附魔」入口行（旧 {@code EnchantmentListSetting} 那一行） */
    public static ConsoleRow targetRow(LibrarianConsoleScreen host, AutoLibrarianModule module) {
        return ConsoleRow.liveComment(host, () -> TARGET_LABEL, TARGET_DESCRIPTION,
            () -> SELECTED_PREFIX + module.settings().targetEnchantments().size(),
            List.of(new Ctl(new Button(CONFIG_BUTTON, () -> openTargets(host, module)),
                    "打开「" + TARGET_LABEL + "」选择器"),
                ConsoleWidgets.resetCtl(() -> {
                    // 出厂值 = 新建一份设置载体时的目标附魔（默认 minecraft:mending）
                    module.settings().setTargetEnchantments(new ArrayList<>(DEFAULTS.targetEnchantments()));
                    module.persistSettings();
                    host.reload();
                    // 面板内回执：复位后剩下几条就报几条（真实 size），面板开着时聊天框看不见
                    SelectionReceipt.reset(module.settings().targetEnchantments().size());
                }, TARGET_LABEL)));
    }

    /**
     * 打开目标附魔选择器。
     *
     * <p>未进入世界时取不到附魔注册表（附魔是数据驱动的动态注册表），此时不开空窗口。</p>
     */
    private static void openTargets(LibrarianConsoleScreen host, AutoLibrarianModule module) {
        Minecraft client = host.client();
        if (client == null || client.level == null) return;
        Registry<Enchantment> registry = client.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        if (registry.keySet().isEmpty()) return;

        List<EnchantEntry> entries = enchantEntries(registry);
        Set<String> candidateKeys = new HashSet<>();
        for (EnchantEntry entry : entries) candidateKeys.add(entry.key());

        client.setScreen(new SelectorScreen(TARGET_LABEL, host,
            List.<SelectorScreen.Entry>copyOf(entries),
            () -> List.copyOf(module.settings().targetEnchantments()),
            key -> {
                // 二次校验：只有候选键集内的键能加进来（防手改存档越界）
                if (!candidateKeys.contains(key)) return;
                List<String> selected = new ArrayList<>(module.settings().targetEnchantments());
                if (selected.contains(key)) return;
                selected.add(key);
                module.settings().setTargetEnchantments(selected);
                module.persistSettings();
            },
            key -> {
                List<String> selected = new ArrayList<>(module.settings().targetEnchantments());
                if (!selected.remove(key)) return;
                module.settings().setTargetEnchantments(selected);
                module.persistSettings();
            }));
    }

    /**
     * 候选集 → 选择器条目：按 {@link #ENCHANT_FAMILIES} 顺序铺，家族内按本地化名排序；
     * 家族表认不出的候选归兜底组（不丢项）。
     *
     * <p><b>候选 = 村民真能刷出来的附魔</b>（用户 2026-09-18：「图书管理员 刷不出来这些附魔书你弄上去有什么意义」）：
     * 取值域直接取 {@link VillagerProfessionRegistry#getLibrarianEnchantments()} —— 项目里「图书管理员能刷出什么」
     * 的唯一正本（逐条注明依据，并已剔除村民永远不卖的灵魂疾行 / 迅捷潜行）。
     * 于是三类项一次性挡在门外：模组 / 数据包注册的自定义附魔、诅咒类、宝藏类。</p>
     *
     * <p><b>这一条推翻了本类早前的旧口径</b>（旧注释写着「不复用自动村民交易的选择器实现，
     * 那边取值域比这里窄」）：那是照旧项目「候选 = 注册表整表」抄的，但旧项目那个年代注册表里只有原版附魔，
     * 而本服注册表多了一百多条自定义项 —— 照抄反而选出模块永远匹配不上的目标。
     * 现在以「能刷出来」为准，两处取值域收敛成一份。</p>
     *
     * <p>命名空间仍限 {@code minecraft}：池里存的是 path，若有模组或数据包同名注册（如 {@code 某模组:sharpness}），
     * 那不是本池所指的那条附魔。</p>
     */
    private static List<EnchantEntry> enchantEntries(Registry<Enchantment> registry) {
        Set<String> tradable = VillagerProfessionRegistry.getLibrarianEnchantments();
        List<ResourceKey<Enchantment>> candidates = new ArrayList<>();
        for (ResourceKey<Enchantment> key : registry.registryKeySet()) {
            Identifier id = key.identifier();
            if (!VANILLA_NAMESPACE.equals(id.getNamespace())) continue;
            if (!tradable.contains(id.getPath())) continue;
            candidates.add(key);
        }

        List<EnchantEntry> entries = new ArrayList<>();
        Set<String> added = new HashSet<>();
        for (EnchantFamily family : ENCHANT_FAMILIES) {
            List<EnchantEntry> groupEntries = new ArrayList<>();
            for (ResourceKey<Enchantment> key : candidates) {
                String path = key.identifier().getPath();
                if (!family.paths().contains(path) || !added.add(key.identifier().toString())) continue;
                groupEntries.add(new EnchantEntry(key.identifier().toString(), displayName(registry, key),
                    GROUP_PREFIX + family.title()));
            }
            groupEntries.sort((left, right) -> COLLATOR.compare(left.title(), right.title()));
            entries.addAll(groupEntries);
        }
        List<EnchantEntry> rest = new ArrayList<>();
        for (ResourceKey<Enchantment> key : candidates) {
            if (!added.add(key.identifier().toString())) continue;
            rest.add(new EnchantEntry(key.identifier().toString(), displayName(registry, key), GROUP_OTHER));
        }
        rest.sort((left, right) -> COLLATOR.compare(left.title(), right.title()));
        entries.addAll(rest);
        return entries;
    }

    /** 附魔的本地化显示名（跟随客户端语言，取不到时回落到注册表 ID） */
    private static String displayName(Registry<Enchantment> registry, ResourceKey<Enchantment> key) {
        return registry.get(key)
            .map(holder -> holder.value().description().getString())
            .orElse(key.identifier().toString());
    }

    /** 附魔家族：中文组名 + 该家族的附魔 path */
    private record EnchantFamily(String title, List<String> paths) {
    }

    /**
     * 附魔候选项。
     *
     * <p>附魔没有专属物品，图标统一用附魔书（旧 {@code EnchantmentListSetting} 弹窗的口径，
     * 不伪造与真实物品不符的贴图）；图标懒构造，注册表已绑定时才构造 {@code ItemStack}。</p>
     */
    private static final class EnchantEntry implements SelectorScreen.Entry {

        private final String key;
        private final String title;
        private final String group;
        private ItemStack iconStack;

        private EnchantEntry(String key, String title, String group) {
            this.key = key;
            this.title = title;
            this.group = group;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String title() {
            return title;
        }

        @Override
        public String group() {
            return group;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            if (iconStack == null) iconStack = new ItemStack(Items.ENCHANTED_BOOK);
            return ItemIconCache.getInstance().draw(canvas, iconStack, x, y, size);
        }

        /** 与 drawIcon 同一份图标：统一的附魔书。 */
        @Override
        public ItemStack iconStack() {
            return new ItemStack(Items.ENCHANTED_BOOK);
        }
    }
}
