package com.yiyiaddon.feature.villager.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

/**
 * 村民职业数据注册表
 *
 * 维护 13 种原版职业的完整交易数据：
 * · 职业 ↔ 工作站映射
 * · 每个职业可用绿宝石购买的目标物品白名单
 *
 * 所有数据来自 Minecraft 26.1.2 / 1.21 实际交易表。
 */
public final class VillagerProfessionRegistry {

    // 职业 → 工作站映射
    private static final Map<VillagerProfession, Block> PROFESSION_TO_WORKSTATION = new LinkedHashMap<>();

    // 工作站 → 职业反向映射
    private static final Map<Block, VillagerProfession> WORKSTATION_TO_PROFESSION = new HashMap<>();

    // 职业显示名称（中文）
    private static final Map<VillagerProfession, String> PROFESSION_DISPLAY_NAMES = new LinkedHashMap<>();

    // 职业 → 可购买目标物品白名单（绿宝石购买）
    private static final Map<VillagerProfession, Set<Item>> PROFESSION_PURCHASE_TARGETS = new HashMap<>();

    // 存储图书管理员附魔书类型
    private static final Set<String> LIBRARIAN_ENCHANTMENTS = new HashSet<>();

    static {
        // 13 种原版职业与工作站的精准映射
        registerProfession("minecraft:armorer", Blocks.BLAST_FURNACE, "盔甲匠",
            Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS,
            Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS,
            Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS,
            Items.SHIELD, Items.BELL);

        registerProfession("minecraft:butcher", Blocks.SMOKER, "屠夫",
            Items.COOKED_RABBIT, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_MUTTON, Items.COOKED_BEEF,
            Items.RABBIT_STEW);

        registerProfession("minecraft:cartographer", Blocks.CARTOGRAPHY_TABLE, "制图师",
            Items.MAP, Items.FILLED_MAP, Items.GLOBE_BANNER_PATTERN, Items.ITEM_FRAME,
            Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER,
            Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER,
            Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER,
            Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER);

        registerProfession("minecraft:cleric", Blocks.BREWING_STAND, "牧师",
            Items.REDSTONE, Items.LAPIS_LAZULI, Items.GLOWSTONE, Items.ENDER_PEARL,
            Items.EXPERIENCE_BOTTLE);

        registerProfession("minecraft:farmer", Blocks.COMPOSTER, "农民",
            Items.BREAD, Items.PUMPKIN_PIE, Items.CAKE, Items.COOKIE, Items.APPLE,
            Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.SUSPICIOUS_STEW);

        // 渔夫（木桶）：只卖熟鱼/鱼桶/篝火/附魔钓鱼竿。
        // 注意：渔夫是【收】船的（玩家卖船给渔夫），不是卖船；空桶也不卖。
        registerProfession("minecraft:fisherman", Blocks.BARREL, "渔夫",
            Items.COOKED_COD, Items.COOKED_SALMON, Items.COD_BUCKET,
            Items.FISHING_ROD, Items.CAMPFIRE);

        // 制箭师（制箭台）：只卖弓/弩/箭/药箭（含附魔弓弩，物品基础类型相同）。
        // 注意：燧石与光灵箭是【收】的或原版没有的卖出项，已剔除。
        registerProfession("minecraft:fletcher", Blocks.FLETCHING_TABLE, "制箭师",
            Items.BOW, Items.CROSSBOW, Items.ARROW, Items.TIPPED_ARROW);

        registerProfession("minecraft:leatherworker", Blocks.CAULDRON, "皮匠",
            Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS,
            Items.LEATHER_HORSE_ARMOR, Items.SADDLE);

        registerProfession("minecraft:librarian", Blocks.LECTERN, "图书管理员",
            Items.BOOKSHELF, Items.LANTERN, Items.GLASS, Items.CLOCK, Items.COMPASS, Items.NAME_TAG);
        // 注意：附魔书单独处理，不放在普通交易白名单

        // 图书管理员可刷附魔书列表（村民能刷出来的所有附魔，不限等级）
        registerLibrarianEnchantedBooks();

        // 石匠（切石机）：卖砖（物品）/錾制石砖/磨制三岩/滴水石块/陶瓦+带釉陶瓦/石英块+柱。
        // 注意：砖块方块、泥砖、泥坯是【没有】的交易项，已剔除；带釉陶瓦已补全。
        registerProfession("minecraft:mason", Blocks.STONECUTTER, "石匠",
            Items.BRICK, Items.CHISELED_STONE_BRICKS,
            Items.POLISHED_ANDESITE, Items.POLISHED_DIORITE, Items.POLISHED_GRANITE,
            Items.DRIPSTONE_BLOCK, Items.QUARTZ_BLOCK, Items.QUARTZ_PILLAR,
            Items.TERRACOTTA,
            Items.WHITE_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA,
            Items.YELLOW_TERRACOTTA, Items.LIME_TERRACOTTA, Items.PINK_TERRACOTTA, Items.GRAY_TERRACOTTA,
            Items.LIGHT_GRAY_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.BLUE_TERRACOTTA,
            Items.BROWN_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.RED_TERRACOTTA, Items.BLACK_TERRACOTTA,
            Items.WHITE_GLAZED_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Items.YELLOW_GLAZED_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA,
            Items.LIGHT_GRAY_GLAZED_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA,
            Items.BROWN_GLAZED_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);

        registerProfession("minecraft:shepherd", Blocks.LOOM, "牧羊人",
            Items.WHITE_WOOL, Items.ORANGE_WOOL, Items.MAGENTA_WOOL, Items.LIGHT_BLUE_WOOL,
            Items.YELLOW_WOOL, Items.LIME_WOOL, Items.PINK_WOOL, Items.GRAY_WOOL,
            Items.LIGHT_GRAY_WOOL, Items.CYAN_WOOL, Items.PURPLE_WOOL, Items.BLUE_WOOL,
            Items.BROWN_WOOL, Items.GREEN_WOOL, Items.RED_WOOL, Items.BLACK_WOOL,
            Items.WHITE_CARPET, Items.ORANGE_CARPET, Items.MAGENTA_CARPET, Items.LIGHT_BLUE_CARPET,
            Items.YELLOW_CARPET, Items.LIME_CARPET, Items.PINK_CARPET, Items.GRAY_CARPET,
            Items.LIGHT_GRAY_CARPET, Items.CYAN_CARPET, Items.PURPLE_CARPET, Items.BLUE_CARPET,
            Items.BROWN_CARPET, Items.GREEN_CARPET, Items.RED_CARPET, Items.BLACK_CARPET,
            Items.WHITE_BED, Items.ORANGE_BED, Items.MAGENTA_BED, Items.LIGHT_BLUE_BED,
            Items.YELLOW_BED, Items.LIME_BED, Items.PINK_BED, Items.GRAY_BED,
            Items.LIGHT_GRAY_BED, Items.CYAN_BED, Items.PURPLE_BED, Items.BLUE_BED,
            Items.BROWN_BED, Items.GREEN_BED, Items.RED_BED, Items.BLACK_BED,
            // 专家级牧羊人卖旗帜（随机颜色）
            Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER,
            Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER,
            Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER,
            Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER,
            Items.PAINTING, Items.SHEARS);

        registerProfession("minecraft:toolsmith", Blocks.SMITHING_TABLE, "工具匠",
            Items.STONE_AXE, Items.STONE_SHOVEL, Items.STONE_PICKAXE, Items.STONE_HOE,
            Items.IRON_AXE, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_HOE,
            Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_HOE,
            Items.BELL);

        registerProfession("minecraft:weaponsmith", Blocks.GRINDSTONE, "武器匠",
            Items.IRON_AXE, Items.IRON_SWORD, Items.DIAMOND_AXE, Items.DIAMOND_SWORD, Items.BELL);
    }

    /**
     * 注册职业的基本信息和可购买物品白名单
     *
     * 为什么：四张表（正向映射、反向映射、中文名、白名单）必须在同一处一次性写齐，
     * 避免出现「有工作站却没有白名单」的半截数据。
     * 关键判据：物品 ID 在注册表里查不到（如模组职业）直接放弃本次注册，不写任何表。
     */
    private static void registerProfession(String professionId, Block workstation, String displayName, Item... purchaseItems) {
        // 获取职业枚举
        Identifier id = Identifier.parse(professionId);
        var optional = BuiltInRegistries.VILLAGER_PROFESSION.get(id);
        if (optional.isEmpty()) return;

        VillagerProfession profession = optional.get().value();

        // 注册工作站映射
        PROFESSION_TO_WORKSTATION.put(profession, workstation);
        WORKSTATION_TO_PROFESSION.put(workstation, profession);

        // 注册显示名称
        PROFESSION_DISPLAY_NAMES.put(profession, displayName);

        // 注册可购买物品白名单
        PROFESSION_PURCHASE_TARGETS.put(profession, Set.of(purchaseItems));
    }

    /**
     * 注册图书管理员附魔书（村民能刷出来的所有附魔）
     *
     * 为什么：附魔书不是普通物品白名单能表达的（同一本书按附魔区分），必须单独维护一张附魔类型表。
     */
    private static void registerLibrarianEnchantedBooks() {
        // 这里只存储附魔类型名称，实际匹配时会忽略等级。
        // 依据：灵魂疾行（猪灵以物换物/堡垒宝藏）与迅捷潜行（远古城市）村民【永远不卖】，已剔除。
        LIBRARIAN_ENCHANTMENTS.addAll(Set.of(
            // 武器附魔
            "sharpness",           // 锋利
            "smite",               // 亡灵杀手
            "bane_of_arthropods",  // 节肢杀手
            "knockback",           // 击退
            "fire_aspect",         // 火焰附加
            "looting",             // 抢夺
            "sweeping_edge",       // 横扫之刃

            // 工具附魔
            "efficiency",          // 效率
            "silk_touch",          // 精准采集
            "fortune",             // 时运

            // 弓箭附魔
            "power",               // 力量
            "punch",               // 冲击
            "flame",               // 火矢
            "infinity",            // 无限

            // 弩附魔（村民可刷）
            "piercing",            // 穿透
            "multishot",           // 多重射击
            "quick_charge",        // 快速装填

            // 盔甲附魔
            "protection",          // 保护
            "fire_protection",     // 火焰保护
            "blast_protection",    // 爆炸保护
            "projectile_protection", // 弹射物保护
            "feather_falling",     // 摔落保护
            "thorns",              // 荆棘
            "respiration",         // 水下呼吸
            "depth_strider",       // 深海探索者
            "aqua_affinity",       // 水下速掘
            "frost_walker",        // 冰霜行者

            // 钓鱼附魔（村民可刷）
            "luck_of_the_sea",     // 海之眷顾
            "lure",                // 饵钓

            // 通用附魔
            "mending",             // 经验修补
            "unbreaking",          // 耐久

            // 三叉戟附魔（村民可刷）
            "loyalty",             // 忠诚
            "impaling",            // 穿刺
            "riptide",             // 激流
            "channeling"           // 引雷
        ));
    }

    /**
     * 获取图书管理员附魔书列表
     *
     * 为什么：返回新集合，外部增删不会污染静态表本身。
     */
    public static Set<String> getLibrarianEnchantments() {
        return new HashSet<>(LIBRARIAN_ENCHANTMENTS);
    }

    /**
     * 获取职业可购买的目标物品白名单
     *
     * @param profession 村民职业
     * @return 可购买物品集合，失败返回空集合
     */
    public static Set<Item> getPurchaseTargets(VillagerProfession profession) {
        return PROFESSION_PURCHASE_TARGETS.getOrDefault(profession, Collections.emptySet());
    }

    /**
     * 判断物品是否属于职业可购买目标
     *
     * 关键判据：仅当该职业已登记白名单且物品在内才为 true，未登记职业一律 false。
     *
     * @param profession 村民职业
     * @param item 物品
     * @return true 表示可购买
     */
    public static boolean isPurchaseTarget(VillagerProfession profession, Item item) {
        Set<Item> targets = PROFESSION_PURCHASE_TARGETS.get(profession);
        return targets != null && targets.contains(item);
    }

    /**
     * 获取职业对应的工作站方块
     *
     * 关键判据：正向表由 {@link #registerProfession} 写入，未登记职业返回 null。
     */
    public static Block getWorkstation(VillagerProfession profession) {
        return PROFESSION_TO_WORKSTATION.get(profession);
    }

    /**
     * 通过工作站方块反查职业
     *
     * 为什么：反向表与正向表同源写入，用于「看到工作站就知道要等什么职业的村民」。
     */
    public static VillagerProfession getProfessionByWorkstation(Block workstation) {
        return WORKSTATION_TO_PROFESSION.get(workstation);
    }

    /**
     * 获取职业中文显示名称
     *
     * 关键判据：三级回退 —— 中文名表命中即返回；未收录则回退注册表 ID 字符串；
     * 职业为 null 时返回「未知职业」，绝不返回 null（调用方直接拼进播报文本）。
     */
    public static String getDisplayName(VillagerProfession profession) {
        if (profession == null) return "未知职业";
        String name = PROFESSION_DISPLAY_NAMES.get(profession);
        if (name != null) return name;
        Identifier id = BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession);
        return id == null ? "未知职业" : id.toString();
    }

    /**
     * 验证职业是否有效（有工作站的职业）
     *
     * 关键判据：非 null 且已登记工作站映射才算有效职业（无业游民等未登记职业不参与交易）。
     */
    public static boolean isValidProfession(VillagerProfession profession) {
        return profession != null && PROFESSION_TO_WORKSTATION.containsKey(profession);
    }

    /**
     * 获取所有可交易职业列表
     */
    public static List<VillagerProfession> getAllProfessions() {
        return new ArrayList<>(PROFESSION_TO_WORKSTATION.keySet());
    }

    /**
     * 获取所有职业显示名称（用于 UI）
     *
     * 为什么：职业表是 LinkedHashMap，注册顺序即列表顺序，UI 选择器顺序因此在重启后保持稳定。
     */
    public static List<String> getAllDisplayNames() {
        return new ArrayList<>(PROFESSION_DISPLAY_NAMES.values());
    }

    /**
     * 通过显示名称查找职业
     *
     * 为什么：配置界面与旧档里只存了中文名，需要线性反查回职业枚举；未命中返回 null。
     */
    public static VillagerProfession getProfessionByDisplayName(String displayName) {
        for (Map.Entry<VillagerProfession, String> entry : PROFESSION_DISPLAY_NAMES.entrySet()) {
            if (entry.getValue().equals(displayName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private VillagerProfessionRegistry() {
        // 工具类禁止实例化
    }
}
