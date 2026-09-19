package com.yiyiaddon.feature.villager.ui;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.data.VillagerProfessionRegistry;
import com.yiyiaddon.feature.villager.ui.console.VillagerConsoleScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 自动村民交易的两个选择器（13 个职业物品选择器 + 图书管理员附魔书选择器）的唯一接线处。
 *
 * <p><b>窗口是通用件</b>：两个选择器都走 {@link SelectorScreen} 的多选构造（左栏候选＋右栏已选、
 * 组头一键全选 / 清空、会话级折叠记忆），本类只负责「候选集是什么、每行长什么样、选中怎么落盘」，
 * 不自造第二套选择器窗口。</p>
 *
 * <p><b>候选集 = 旧项目白名单，不允许平铺全物品列表</b>：物品候选逐字等于旧
 * {@code ItemListSetting.filter} 的取值域 —— {@link VillagerProfessionRegistry#getPurchaseTargets}
 * 给出的该职业可购买物品白名单；附魔候选等于 {@link VillagerProfessionRegistry#getLibrarianEnchantments()}
 * 的附魔池。按第 195/197 条，候选必须先分类：</p>
 * <ul>
 *   <li><b>物品</b>：按<b>物品大类</b>分组，组名取原版创造模式标签页的显示名
 *       （{@code CreativeModeTab#getDisplayName()}，跟随客户端语言，不新造中文词；只取内容页，
 *       见 {@link #itemGroups()}）；归不进任何内容页的物品进 {@code ▌ 其他} 兜底组，保证不漏项。</li>
 *   <li><b>附魔</b>：按附魔池自己的分节分组（武器 / 工具 / 弓箭 / 弩 / 盔甲 / 钓鱼 / 通用 / 三叉戟），
 *       与 {@code VillagerProfessionRegistry} 附魔池的注释分节逐条对应；未登记家族的附魔进兜底组。</li>
 * </ul>
 * 两级分组标题都由 {@link SelectorScreen} 负责折叠（左侧候选默认全部收起）。</p>
 *
 * <p><b>每行必须带物品图标</b>（D9 同口径）：物品行取物品自身贴图，附魔行统一取附魔书
 * （旧 {@code EnchantmentListSetting} 弹窗的口径），走 {@link ItemIconCache} 全屏统一链路。
 * 行尾「已选 N」走 {@code ConsoleRow.liveComment}，选择器里加减一个，退出后行上的数字立刻是新的。</p>
 *
 * <p><b>写回与落盘</b>：加减一个候选即调
 * {@link com.yiyiaddon.feature.villager.config.VillagerTradeSettings#setItemTargets(String, List)} /
 * {@link com.yiyiaddon.feature.villager.config.VillagerTradeSettings#setLibrarianEnchantments(List)}
 * 整表写回，再 {@link AutoVillagerTradeModule#persistSettings()} 立即落盘（第 172/173 条：
 * 界面改动必须落盘，重启后仍在）。{@code onAdd} 还会做一次白名单二次校验，手改存档越界也加不进来。</p>
 */
public final class VillagerSelectors {

    /** 分组标题前缀（与项目其它选择器一致的分组标题写法：{@code §b§l▌ }） */
    private static final String GROUP_PREFIX = "§b§l▌ ";
    /** 兜底分组标题（认不出大类的候选归到这一组，避免漏项；写法照装备选择器的「其他」组） */
    private static final String GROUP_OTHER = "§7§l▌ 其他";
    /** 兜底分组的排序权重：永远排在最后 */
    private static final ItemGroup GROUP_OTHER_ITEM = new ItemGroup(GROUP_OTHER, Integer.MAX_VALUE);

    /** 设置行右侧的入口按钮文案（与自动农场「作物选择」的选择器行同款） */
    private static final String CONFIG_BUTTON = "§b配置";
    /** 物品选择器的说明（旧 {@code :219} 逐字） */
    private static final String ITEM_DESCRIPTION = "点击选择要购买的物品";
    /** 附魔书选择器的说明（旧 {@code :236} 逐字） */
    private static final String ENCHANT_DESCRIPTION = "只选择村民能够刷出的附魔类型，交易时忽略附魔等级";
    /** 附魔书选择器的设置名（旧 {@code :235} 逐字；也是该选择器的窗口标题） */
    private static final String ENCHANT_LABEL = "图书管理员附魔书";
    /** 行尾实时计数的写法前缀 */
    private static final String SELECTED_PREFIX = "已选 ";
    /** 物品选择器的设置名后缀（旧 {@code :218} 逐字：{@code <职业名>交易}） */
    private static final String ITEM_KEY_SUFFIX = "交易";
    /**
     * 附魔存储键的命名空间前缀。
     *
     * <p>旧档与本项目落盘都用完整 ID（{@code minecraft:efficiency}）：状态机侧
     * {@code buildTargetsFor} 用 {@code Identifier.tryParse} 解析后取 {@code getPath()} 与池比对，
     * {@code EnchantmentMatcher} 则要求存储值等于 {@code ResourceKey.identifier().toString()}，
     * 因此选择器写入的键必须是带命名空间的完整 ID。</p>
     */
    private static final String ENCHANT_KEY_PREFIX = "minecraft:";

    /** 中文排序器：候选与分组内的行按本地化名稳定排序（与挖矿 / 附魔选择器同一做法） */
    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final VillagerTradeSettings DEFAULTS = new VillagerTradeSettings();

    /**
     * 附魔家族（旧 {@code VillagerProfessionRegistry} 附魔池注释里的八个分节，顺序原样）。
     *
     * <p>为什么用固定表而不是猜前缀：附魔池里 {@code smite / bane_of_arthropods} 这类名字
     * 与「武器 / 盔甲」没有字符串关系，按名字猜必然分错；分节表直接照池子自己的组织方式抄，
     * 每条附魔归哪一家可逐条对回注册表的注释行。</p>
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

    private VillagerSelectors() {
    }

    // ── 设置页用的两行 ──

    /**
     * 构建一行「{@code <职业>交易} 物品选择器」入口（旧 {@code ItemListSetting :217-230}）。
     *
     * <p>行标签 = 旧设置名（逐字），行尾注释实时给已选条数，右侧「配置」按钮打开
     * {@link SelectorScreen}；候选与写回见 {@link #openItems}。</p>
     */
    public static ConsoleRow itemRow(VillagerConsoleScreen host, AutoVillagerTradeModule module,
                                     String professionName) {
        String label = professionName + ITEM_KEY_SUFFIX;
        return ConsoleRow.liveComment(host, () -> label, ITEM_DESCRIPTION,
            () -> SELECTED_PREFIX + module.settings().itemTargets(professionName).size(),
            List.of(new Ctl(new Button(CONFIG_BUTTON, () -> openItems(host, module, professionName)),
                    "打开「" + label + "」选择器"),
                ConsoleWidgets.resetCtl(() -> {
                    // 出厂值 = 该职业没有任何已选物品
                    module.settings().setItemTargets(professionName,
                        new ArrayList<>(DEFAULTS.itemTargets(professionName)));
                    module.persistSettings();
                    host.reload();
                }, label)));
    }

    /**
     * 构建一行「图书管理员附魔书」入口（旧 {@code EnchantmentListSetting :233-241}）。
     *
     * <p>只有图书管理员的折叠分组会铺它（判据在设置页，与旧 {@code visible} 条件同一条：
     * 该选择器只属于图书管理员）。</p>
     */
    public static ConsoleRow enchantRow(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        return ConsoleRow.liveComment(host, () -> ENCHANT_LABEL, ENCHANT_DESCRIPTION,
            () -> SELECTED_PREFIX + module.settings().librarianEnchantments().size(),
            List.of(new Ctl(new Button(CONFIG_BUTTON, () -> openEnchants(host, module)),
                    "打开「" + ENCHANT_LABEL + "」选择器"),
                ConsoleWidgets.resetCtl(() -> {
                    // 出厂值 = 没有任何已选附魔书
                    module.settings().setLibrarianEnchantments(
                        new ArrayList<>(DEFAULTS.librarianEnchantments()));
                    module.persistSettings();
                    host.reload();
                }, ENCHANT_LABEL)));
    }

    // ── 物品选择器 ──

    /**
     * 打开某职业的物品选择器。
     *
     * <p><b>关键判据</b>：候选 = 该职业的购买白名单（{@link VillagerProfessionRegistry}），
     * 白名单取不到（模组环境里职业未登记）或为空时不开空窗口；写回走整表写回 + 立即落盘；
     * {@code onAdd} 再用候选键集二次校验一次。</p>
     */
    private static void openItems(VillagerConsoleScreen host, AutoVillagerTradeModule module,
                                  String professionName) {
        VillagerProfession profession = VillagerProfessionRegistry.getProfessionByDisplayName(professionName);
        if (profession == null) return;
        Set<Item> whitelist = VillagerProfessionRegistry.getPurchaseTargets(profession);
        if (whitelist.isEmpty()) return;

        Map<Item, ItemGroup> groups = itemGroups();
        List<ItemEntry> entries = new ArrayList<>();
        Set<String> candidateKeys = new HashSet<>();
        for (Item item : whitelist) {
            String key = itemKey(item);
            if (key == null) continue;
            candidateKeys.add(key);
            entries.add(new ItemEntry(key, item, "§f" + itemName(item),
                groups.getOrDefault(item, GROUP_OTHER_ITEM)));
        }
        entries.sort(Comparator.comparingInt(ItemEntry::groupOrder)
            .thenComparing(ItemEntry::title, COLLATOR));

        openScreen(host, new SelectorScreen(professionName + ITEM_KEY_SUFFIX, host,
            List.<SelectorScreen.Entry>copyOf(entries),
            () -> List.copyOf(module.settings().itemTargets(professionName)),
            key -> {
                // 二次校验：只有白名单内的键能加进来（防手改存档越界）
                if (!candidateKeys.contains(key)) return;
                List<String> selected = new ArrayList<>(module.settings().itemTargets(professionName));
                if (selected.contains(key)) return;
                selected.add(key);
                module.settings().setItemTargets(professionName, selected);
                module.persistSettings();
            },
            key -> {
                List<String> selected = new ArrayList<>(module.settings().itemTargets(professionName));
                if (!selected.remove(key)) return;
                module.settings().setItemTargets(professionName, selected);
                module.persistSettings();
            }));
    }

    /**
     * 物品大类表：{@code 物品 → 大类}。
     *
     * <p><b>分类依据</b>：原版创造模式标签页的显示名——「建筑方块 / 工具与实用物品 / 战斗用品…」
     * 这类大类本身就是原版对物品的官方分类，且显示名跟随客户端语言，界面上不会再出现一份手写的
     * 中文分类词表。</p>
     *
     * <p><b>只取 {@code Type.CATEGORY} 的内容页</b>：原版标签页里还有三个聚合页
     * （搜索页列全部物品、快捷栏页是上次保存的热键栏、背包页由物品栏界面自己铺），
     * 它们会把物品归到「搜索物品 / 已保存的快捷栏」这种对玩家没有分类意义的组里，
     * 而且搜索页在注册顺序上排在工具 / 战斗 / 食物等内容页之前，先遍历到就会抢先给物品定型。
     * 一个物品出现在多个内容页时取先出现的那一个（注册顺序即标签页顺序，稳定）。</p>
     *
     * <p><b>兜底</b>：某个物品没出现在任何内容页的展示列表里（标签页内容尚未构建或原版不展示）时，
     * 调用方把它归到 {@code ▌ 其他} 组 —— 分类只影响分组观感，绝不因此让候选消失。</p>
     */
    private static Map<Item, ItemGroup> itemGroups() {
        Map<Item, ItemGroup> groups = new LinkedHashMap<>();
        int order = 0;
        for (CreativeModeTab tab : BuiltInRegistries.CREATIVE_MODE_TAB) {
            if (tab.getType() != CreativeModeTab.Type.CATEGORY) continue;
            ItemGroup group = new ItemGroup(GROUP_PREFIX + tab.getDisplayName().getString(), order++);
            for (ItemStack stack : tab.getDisplayItems()) {
                Item item = stack.getItem();
                if (item == null || item == Items.AIR) continue;
                groups.putIfAbsent(item, group);
            }
        }
        return groups;
    }

    // ── 附魔书选择器 ──

    /**
     * 打开图书管理员附魔书选择器。
     *
     * <p><b>关键判据</b>：候选 = {@link VillagerProfessionRegistry#getLibrarianEnchantments()} 池
     * （村民真能刷出来的附魔类型，等级不参与）；写回走
     * {@link com.yiyiaddon.feature.villager.config.VillagerTradeSettings#setLibrarianEnchantments(List)}
     * 整表写回 + 立即落盘；{@code onAdd} 用候选键集二次校验。</p>
     */
    private static void openEnchants(VillagerConsoleScreen host, AutoVillagerTradeModule module) {
        Set<String> pool = VillagerProfessionRegistry.getLibrarianEnchantments();
        if (pool.isEmpty()) return;

        Map<String, String> titles = enchantTitles(pool);
        List<EnchantEntry> entries = enchantEntries(pool, titles);
        Set<String> candidateKeys = new HashSet<>();
        for (String path : pool) candidateKeys.add(ENCHANT_KEY_PREFIX + path);

        openScreen(host, new SelectorScreen(ENCHANT_LABEL, host,
            List.<SelectorScreen.Entry>copyOf(entries),
            () -> List.copyOf(module.settings().librarianEnchantments()),
            key -> {
                if (!candidateKeys.contains(key)) return;
                List<String> selected = new ArrayList<>(module.settings().librarianEnchantments());
                if (selected.contains(key)) return;
                selected.add(key);
                module.settings().setLibrarianEnchantments(selected);
                module.persistSettings();
            },
            key -> {
                List<String> selected = new ArrayList<>(module.settings().librarianEnchantments());
                if (!selected.remove(key)) return;
                module.settings().setLibrarianEnchantments(selected);
                module.persistSettings();
            }));
    }

    /**
     * 附魔池 → 本地化显示名。
     *
     * <p>附魔是数据驱动的动态注册表，名字必须走世界注册表解析（与状态机
     * {@code buildTargetsFor} 同一写法）；客户端未进世界时取不到注册表，回落到 ID 的 path
     * （界面上仍能认出来是哪一条，不伪造译名）。</p>
     */
    private static Map<String, String> enchantTitles(Set<String> pool) {
        Map<String, String> titles = new HashMap<>();
        Minecraft client = Minecraft.getInstance();
        var registry = (client != null && client.level != null)
            ? client.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
            : null;
        for (String path : pool) {
            String title = path;
            if (registry != null) {
                title = registry.get(ResourceKey.create(Registries.ENCHANTMENT,
                        Identifier.withDefaultNamespace(path)))
                    .map(holder -> holder.value().description().getString())
                    .orElse(path);
            }
            titles.put(path, title);
        }
        return titles;
    }

    /**
     * 附魔候选项：按 {@link #ENCHANT_FAMILIES} 的顺序铺，家族内按本地化名排序；
     * 池里出现家族表认不出的附魔时归到兜底组（不丢项）。
     */
    private static List<EnchantEntry> enchantEntries(Set<String> pool, Map<String, String> titles) {
        List<EnchantEntry> entries = new ArrayList<>();
        Set<String> added = new HashSet<>();
        for (EnchantFamily family : ENCHANT_FAMILIES) {
            List<String> paths = new ArrayList<>();
            for (String path : family.paths()) {
                if (pool.contains(path) && added.add(path)) paths.add(path);
            }
            paths.sort((left, right) -> COLLATOR.compare(titleOf(left, titles), titleOf(right, titles)));
            for (String path : paths) {
                entries.add(new EnchantEntry(ENCHANT_KEY_PREFIX + path, titleOf(path, titles),
                    GROUP_PREFIX + family.title()));
            }
        }
        List<String> rest = new ArrayList<>();
        for (String path : pool) {
            if (!added.contains(path)) rest.add(path);
        }
        rest.sort((left, right) -> COLLATOR.compare(titleOf(left, titles), titleOf(right, titles)));
        for (String path : rest) {
            entries.add(new EnchantEntry(ENCHANT_KEY_PREFIX + path, titleOf(path, titles), GROUP_OTHER));
        }
        return entries;
    }

    /** 附魔 path → 显示名（未解析到时回落到 path 本身） */
    private static String titleOf(String path, Map<String, String> titles) {
        return titles.getOrDefault(path, path);
    }

    // ── 原语 ──

    /** 物品的注册表 ID（落盘键）；注册表查不到时返回 {@code null}（该候选直接跳过） */
    private static String itemKey(Item item) {
        Identifier id = BuiltInRegistries.ITEM.getKey(item);
        return id == null ? null : id.toString();
    }

    /** 物品的本地化显示名（跟随客户端语言，与状态机 {@code buildTargetsFor} 同一口径） */
    private static String itemName(Item item) {
        return item.getDefaultInstance().getHoverName().getString();
    }

    /** 打开子窗口（客户端未就绪时不动，避免在非渲染线程上 setScreen） */
    private static void openScreen(VillagerConsoleScreen host, SelectorScreen screen) {
        Minecraft client = host.client();
        if (client == null || screen == null) return;
        client.setScreen(screen);
    }

    /** 物品大类：标题（含 {@code §} 前缀）+ 排序权重（标签页注册顺序） */
    private record ItemGroup(String title, int order) {
    }

    /** 附魔家族：中文组名 + 该家族的附魔 path（顺序即池子注释里的出现顺序） */
    private record EnchantFamily(String title, List<String> paths) {
    }

    /**
     * 物品候选项。
     *
     * <p>图标懒构造：构建 {@link ItemStack} 需要注册表已绑定，选择器每次打开时构造才安全
     * （与附魔选择页同一做法）。图标取物品自身贴图，走 {@link ItemIconCache} 统一链路。</p>
     */
    private static final class ItemEntry implements SelectorScreen.Entry {

        private final String key;
        private final Item item;
        private final String title;
        private final ItemGroup group;
        private ItemStack iconStack;

        private ItemEntry(String key, Item item, String title, ItemGroup group) {
            this.key = key;
            this.item = item;
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
            return group.title();
        }

        /** 分组顺序（原版创造模式标签页顺序）；不暴露给选择器接口，仅供本类排序用。 */
        private int groupOrder() {
            return group.order();
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            if (iconStack == null) iconStack = new ItemStack(item);
            return ItemIconCache.getInstance().draw(canvas, iconStack, x, y, size);
        }

        /** 与 drawIcon 同一份图标。 */
        @Override
        public ItemStack iconStack() {
            return item.getDefaultInstance();
        }
    }

    /**
     * 附魔候选项。
     *
     * <p>键是带命名空间的完整附魔 ID（见 {@link #ENCHANT_KEY_PREFIX} 的「为什么」），
     * 标题是本地化附魔名；附魔本身没有专属物品，图标统一用附魔书
     * （旧 {@code EnchantmentListSetting} 弹窗的口径，不伪造与真实物品不符的贴图）。</p>
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
