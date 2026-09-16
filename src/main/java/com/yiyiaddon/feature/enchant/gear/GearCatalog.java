package com.yiyiaddon.feature.enchant.gear;

import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 原版装备附魔 · 装备三级分类目录（GearCatalog）。
 *
 * <p>数据源为 {@link VanillaEnchantDatabase}（官方 26.1.2 装备全集 75 件），
 * 按「大类 → 品质（材质）→ 类型」三级动态组织，供装备附魔配置页（批次5）三级下拉
 * 动态生成，绝不硬编码按钮，绝不显示虚构 Item（如钻石弓 / 铜弓）。</p>
 *
 * <p>品质分支优先：先选材质（木/石/铜/铁/金/钻石/下界合金…），再选类型（镐/斧/剑/头盔…），
 * 避免同类型下 7 种材质堆在一个下拉里，选择更直观。</p>
 */
public final class GearCatalog {

    /** 大类（中文标题 + category 前缀） */
    public record Category(String key, String title) {
        @Override
        public String toString() {
            return title;
        }
    }

    /** 类型（英文 key + 中文标题） */
    public record Type(String key, String title) {
        @Override
        public String toString() {
            return title;
        }
    }

    /** 品质（材质 key + 中文标题 + 是否"无材质"特殊项） */
    public record Material(String key, String title, boolean none) {
        @Override
        public String toString() {
            return title;
        }
    }

    /** 材质中文名映射（material 字段 → 中文） */
    private static final Map<String, String> MATERIAL_CN = materialNames();

    /** 类型中文名映射（category 后缀 → 中文） */
    private static final Map<String, String> TYPE_CN = typeNames();

    /** 品质排序（低 → 高，无材质 / 特殊项排最后） */
    private static final Map<String, Integer> MATERIAL_ORDER = materialOrder();

    private GearCatalog() {
    }

    /** 三大类（工具 / 武器 / 护甲） */
    public static List<Category> categories() {
        return List.of(
            new Category("TOOL", "工具"),
            new Category("WEAPON", "武器"),
            new Category("ARMOR", "护甲")
        );
    }

    /** 某大类下的全部品质（材质，去重、按低→高排序） */
    public static List<Material> materials(String categoryKey) {
        Map<String, Material> map = new LinkedHashMap<>();
        for (VanillaEnchantDatabase.GearCandidateRule gear : VanillaEnchantDatabase.get().gears()) {
            String[] parts = split(gear.category());
            if (parts == null || !parts[0].equals(categoryKey)) continue;
            String mat = gear.material() == null ? "none" : gear.material();
            boolean none = "none".equals(mat);
            map.putIfAbsent(mat, new Material(mat, none ? "特殊" : materialCn(mat), none));
        }
        List<Material> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparingInt(m -> MATERIAL_ORDER.getOrDefault(m.key(), 99)));
        return list;
    }

    /** 某大类 + 品质下的全部类型（去重、稳定顺序） */
    public static List<Type> types(String categoryKey, String materialKey) {
        Map<String, Type> map = new LinkedHashMap<>();
        for (VanillaEnchantDatabase.GearCandidateRule gear : VanillaEnchantDatabase.get().gears()) {
            String[] parts = split(gear.category());
            if (parts == null || !parts[0].equals(categoryKey)) continue;
            String mat = gear.material() == null ? "none" : gear.material();
            if (!mat.equals(materialKey)) continue;
            map.putIfAbsent(parts[1], new Type(parts[1], typeCn(parts[1])));
        }
        return new ArrayList<>(map.values());
    }

    /** 按「大类 + 品质 + 类型」定位唯一装备，找不到返回 null */
    public static VanillaEnchantDatabase.GearCandidateRule gear(String categoryKey, String materialKey, String typeKey) {
        for (VanillaEnchantDatabase.GearCandidateRule gear : VanillaEnchantDatabase.get().gears()) {
            String[] parts = split(gear.category());
            if (parts == null || !parts[0].equals(categoryKey) || !parts[1].equals(typeKey)) continue;
            String mat = gear.material() == null ? "none" : gear.material();
            if (mat.equals(materialKey)) return gear;
        }
        return null;
    }

    /** 拆分 category（TOOL_PICKAXE → [TOOL, PICKAXE]），非法返回 null */
    private static String[] split(String category) {
        int idx = category == null ? -1 : category.indexOf('_');
        if (idx <= 0) return null;
        return new String[]{category.substring(0, idx), category.substring(idx + 1)};
    }

    /** 类型后缀 → 中文名 */
    private static String typeCn(String type) {
        return TYPE_CN.getOrDefault(type, type);
    }

    /** 材质 key → 中文名 */
    private static String materialCn(String material) {
        return MATERIAL_CN.getOrDefault(material, material);
    }

    private static Map<String, String> typeNames() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("PICKAXE", "镐");
        m.put("AXE", "斧");
        m.put("SHOVEL", "锹");
        m.put("HOE", "锄");
        m.put("SWORD", "剑");
        m.put("SPEAR", "矛");
        m.put("BOW", "弓");
        m.put("CROSSBOW", "弩");
        m.put("TRIDENT", "三叉戟");
        m.put("MACE", "重锤");
        m.put("HELMET", "头盔");
        m.put("CHESTPLATE", "胸甲");
        m.put("LEGGINGS", "护腿");
        m.put("BOOTS", "靴子");
        return m;
    }

    private static Map<String, String> materialNames() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("wooden", "木");
        m.put("stone", "石");
        m.put("copper", "铜");
        m.put("iron", "铁");
        m.put("golden", "金");
        m.put("diamond", "钻石");
        m.put("netherite", "下界合金");
        m.put("leather", "皮革");
        m.put("chainmail", "锁链");
        m.put("turtle", "海龟壳");
        return m;
    }

    /** 品质排序权重（低→高；none 特殊项排最后） */
    private static Map<String, Integer> materialOrder() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("wooden", 1);
        m.put("stone", 2);
        m.put("copper", 3);
        m.put("iron", 4);
        m.put("golden", 5);
        m.put("diamond", 6);
        m.put("netherite", 7);
        m.put("leather", 8);
        m.put("chainmail", 9);
        m.put("turtle", 10);
        m.put("none", 99);
        return m;
    }
}
