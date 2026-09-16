package com.yiyiaddon.feature.enchant.vanilla;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 原版装备极品附魔 · 26.1.2 静态规则数据库（VanillaEnchantDatabase）。
 *
 * <p>职责：运行时只读 {@code resources/enchantment/vanilla/} 下的静态 JSON，
 * 提供三层数据的查询能力，绝不联网查询 Wiki：</p>
 * <ol>
 *   <li>基础层 {@link EnchantmentRule}：附魔定义（等级/权重/成本/宝藏/互斥组）；</li>
 *   <li>装备层 {@link GearCandidateRule}：装备附魔台适用附魔 + 30 级可达等级；</li>
 *   <li>互斥层：26.1.2 {@code exclusive_set} 双向互斥对。</li>
 * </ol>
 *
 * <p>数据来源：Minecraft 26.1.2 反编译源码（Enchantments / EnchantmentHelper /
 * EnchantmentMenu）+ 游戏数据包标签（tags/enchantment、tags/item/enchantable），
 * 由开发期脚本固化为 JSON，保证运行时零联网、零临时推理。</p>
 */
public final class VanillaEnchantDatabase {

    private static final Logger LOG = LogUtils.getLogger();

    /** 静态数据根目录（运行时只读） */
    private static final String ROOT = "/enchantment/vanilla/";

    // ── 数据模型 ──────────────────────────────────────────────────────────

    /** 基础附魔规则（第一层）：这个附魔是什么 */
    public record EnchantmentRule(String id, String name, int maxLevel, int weight,
                                  int minBase, int minPerLevel, int maxBase, int maxPerLevel,
                                  boolean treasure, String exclusiveGroup) {

        /** 该附魔指定等级的最低成本（minCost = base + perLevel × (level - 1)） */
        public int minCost(int level) {
            return minBase + minPerLevel * (level - 1);
        }

        /** 该附魔指定等级的最高成本（maxCost = base + perLevel × (level - 1)） */
        public int maxCost(int level) {
            return maxBase + maxPerLevel * (level - 1);
        }
    }

    /** 装备 30 级候选规则（第二层）：这个装备在 30 级附魔台可能得到什么 */
    public record GearCandidateRule(String itemId, String name, String category, String material, int enchantability,
                                    List<String> tableEnchantments, int costMin, int costMax,
                                    Map<String, List<Integer>> reachableLevels) {

        public GearCandidateRule {
            tableEnchantments = List.copyOf(tableEnchantments);
            reachableLevels = Collections.unmodifiableMap(new LinkedHashMap<>(reachableLevels));
        }

        /** 该附魔在 30 级附魔台的可达等级列表（不在候选池返回空列表） */
        public List<Integer> reachable(String enchantmentId) {
            return reachableLevels.getOrDefault(enchantmentId, List.of());
        }

        /** 该附魔是否可以通过 30 级附魔台获得（宝藏附魔与主项不符的附魔为 false） */
        public boolean tableReachable(String enchantmentId) {
            return !reachable(enchantmentId).isEmpty();
        }
    }

    // ── JSON 中间模型 ─────────────────────────────────────────────────────

    private static final class EnchRoot {
        List<EnchJson> enchantments = new ArrayList<>();
    }

    private static final class EnchJson {
        String id;
        String name;
        @SerializedName("max_level") int maxLevel;
        int weight;
        @SerializedName("min_cost") CostJson minCost;
        @SerializedName("max_cost") CostJson maxCost;
        boolean treasure;
        @SerializedName("exclusive_group") String exclusiveGroup;
    }

    private static final class CostJson {
        int base;
        @SerializedName("per_level") int perLevel;
    }

    private static final class ConflictRoot {
        @SerializedName("exclusive_groups") List<GroupJson> groups = new ArrayList<>();
        @SerializedName("conflict_pairs") List<PairJson> pairs = new ArrayList<>();
    }

    private static final class GroupJson {
        String id;
        List<String> members = new ArrayList<>();
    }

    private static final class PairJson {
        String a;
        String b;
    }

    private static final class GearsRoot {
        List<GearJson> items = new ArrayList<>();
    }

    private static final class GearJson {
        String id;
        String name;
        String category;
        String material;
        int enchantability;
        @SerializedName("table_enchantments") List<String> tableEnchantments = new ArrayList<>();
    }

    private static final class CandidateJson {
        String item;
        String name;
        String category;
        String material;
        int enchantability;
        @SerializedName("modified_cost_range") int[] costRange;
        @SerializedName("reachable_levels") Map<String, List<Integer>> reachableLevels;
    }

    private static final class MetaJson {
        @SerializedName("minecraft_version") String minecraftVersion;
        @SerializedName("data_version") int dataVersion;
    }

    // ── 单例 ──────────────────────────────────────────────────────────────

    private static volatile VanillaEnchantDatabase instance;

    private final Map<String, EnchantmentRule> enchantments = new LinkedHashMap<>();
    private final Map<String, GearCandidateRule> gears = new LinkedHashMap<>();
    /** 双向互斥集合：附魔 id → 与之互斥的附魔 id 集合 */
    private final Map<String, Set<String>> conflicts = new HashMap<>();
    /** 附魔 id → 互斥组 id（供扩展校验使用） */
    private final Map<String, String> groupOf = new HashMap<>();
    private final List<String> loadErrors = new ArrayList<>();
    private final String minecraftVersion;
    private final int dataVersion;

    private VanillaEnchantDatabase(String minecraftVersion, int dataVersion) {
        this.minecraftVersion = minecraftVersion;
        this.dataVersion = dataVersion;
    }

    /** 懒加载单例：首次访问时读取全部静态 JSON */
    public static VanillaEnchantDatabase get() {
        if (instance == null) {
            synchronized (VanillaEnchantDatabase.class) {
                if (instance == null) {
                    VanillaEnchantDatabase loaded = load();
                    // 资源解析失败过去是静默空库（功能「识别不到配方」却没有任何提示），
                    // 这里统一喊一声，附带读取模式，便于区分「资源没打包」与「解析失败」。
                    if (!loaded.loadErrors.isEmpty()) {
                        LOG.error("[附魔库] 规则资源未就绪 {} 项（读取模式 {}）：{}",
                            loaded.loadErrors.size(), "classpath",
                            String.join(" | ", loaded.loadErrors));
                    }
                    instance = loaded;
                }
            }
        }
        return instance;
    }

    private static VanillaEnchantDatabase load() {
        VanillaEnchantDatabase db = new VanillaEnchantDatabase("unknown", -1);
        try {
            MetaJson meta = readJson(ROOT + "meta/rules.json", MetaJson.class);
            db = new VanillaEnchantDatabase(meta == null ? "unknown" : meta.minecraftVersion,
                meta == null ? -1 : meta.dataVersion);
        } catch (Exception e) {
            db.loadErrors.add("meta/rules.json 解析失败：" + e.getMessage());
        }
        db.loadEnchantments();
        db.loadConflicts();
        db.loadGears();
        db.loadCandidates();
        return db;
    }

    // ── 加载逻辑 ──────────────────────────────────────────────────────────

    /** 加载基础附魔规则（第一层） */
    private void loadEnchantments() {
        try {
            EnchRoot root = readJson(ROOT + "enchantments/enchantments.json", EnchRoot.class);
            if (root == null) {
                loadErrors.add("enchantments/enchantments.json 资源缺失");
                return;
            }
            for (EnchJson e : root.enchantments) {
                String id = fullId(e.id);
                if (id == null) continue;
                enchantments.put(id, new EnchantmentRule(
                    id, e.name, e.maxLevel, e.weight,
                    e.minCost == null ? 0 : e.minCost.base, e.minCost == null ? 0 : e.minCost.perLevel,
                    e.maxCost == null ? 0 : e.maxCost.base, e.maxCost == null ? 0 : e.maxCost.perLevel,
                    e.treasure, e.exclusiveGroup));
            }
        } catch (Exception e) {
            loadErrors.add("enchantments.json 解析失败：" + e.getMessage());
        }
    }

    /** 加载互斥关系（26.1.2 exclusive_set 双向展开） */
    private void loadConflicts() {
        try {
            ConflictRoot root = readJson(ROOT + "conflicts/conflicts.json", ConflictRoot.class);
            if (root == null) {
                loadErrors.add("conflicts/conflicts.json 资源缺失");
                return;
            }
            if (root.groups != null) {
                for (GroupJson g : root.groups) {
                    for (String m : g.members) groupOf.put(fullId(m), g.id);
                }
            }
            if (root.pairs != null) {
                for (PairJson p : root.pairs) {
                    registerConflict(fullId(p.a), fullId(p.b));
                }
            }
        } catch (Exception e) {
            loadErrors.add("conflicts.json 解析失败：" + e.getMessage());
        }
    }

    private void registerConflict(String a, String b) {
        if (a == null || b == null || a.equals(b)) return;
        conflicts.computeIfAbsent(a, k -> new LinkedHashSet<>()).add(b);
        conflicts.computeIfAbsent(b, k -> new LinkedHashSet<>()).add(a);
    }

    /** 加载装备定义（附魔台适用附魔来自 tags/item/enchantable 归属推算） */
    private void loadGears() {
        try {
            GearsRoot root = readJson(ROOT + "items/gears.json", GearsRoot.class);
            if (root == null) {
                loadErrors.add("items/gears.json 资源缺失");
                return;
            }
            for (GearJson g : root.items) {
                String id = fullId(g.id);
                if (id == null) continue;
                List<String> tables = new ArrayList<>();
                for (String t : g.tableEnchantments) {
                    String fid = fullId(t);
                    if (fid != null) tables.add(fid);
                }
                gears.put(id, new GearCandidateRule(id, g.name, g.category, g.material, g.enchantability,
                    tables, 0, 0, new LinkedHashMap<>()));
            }
        } catch (Exception e) {
            loadErrors.add("gears.json 解析失败：" + e.getMessage());
        }
    }

    /** 加载 30 级候选池（每个装备一个 level30 JSON，文件名由装备 id 推导） */
    private void loadCandidates() {
        for (String itemId : new ArrayList<>(gears.keySet())) {
            GearCandidateRule gear = gears.get(itemId);
            String file = ROOT + "candidates/level30/" + pathOf(itemId) + "-level30.json";
            try {
                CandidateJson c = readJson(file, CandidateJson.class);
                if (c == null) {
                    loadErrors.add("候选池缺失：" + file);
                    continue;
                }
                Map<String, List<Integer>> reachable = new LinkedHashMap<>();
                if (c.reachableLevels != null) {
                    for (Map.Entry<String, List<Integer>> e : c.reachableLevels.entrySet()) {
                        String fid = fullId(e.getKey());
                        if (fid != null) reachable.put(fid, List.copyOf(e.getValue()));
                    }
                }
                int min = (c.costRange == null || c.costRange.length < 2) ? 0 : c.costRange[0];
                int max = (c.costRange == null || c.costRange.length < 2) ? 0 : c.costRange[1];
                gears.put(itemId, new GearCandidateRule(gear.itemId(), gear.name(), gear.category(), gear.material(),
                    gear.enchantability(), gear.tableEnchantments(), min, max, reachable));
            } catch (Exception e) {
                loadErrors.add("候选池解析失败：" + file + "：" + e.getMessage());
            }
        }
    }

    // ── 公共查询 API ──────────────────────────────────────────────────────

    /** 查询附魔规则，未知附魔返回 null */
    public EnchantmentRule rule(String enchantmentId) {
        return enchantments.get(fullId(enchantmentId));
    }

    /** 查询装备候选规则，未知装备返回 null */
    public GearCandidateRule gear(String itemId) {
        return gears.get(fullId(itemId));
    }

    /** 两个附魔是否互斥（按 26.1.2 exclusive_set 静态数据） */
    public boolean conflictsWith(String a, String b) {
        Set<String> set = conflicts.get(fullId(a));
        return set != null && set.contains(fullId(b));
    }

    /** 返回与某附魔互斥的全部附魔 id 集合（不可变） */
    public Set<String> conflictSetOf(String enchantmentId) {
        Set<String> set = conflicts.get(fullId(enchantmentId));
        return set == null ? Set.of() : Collections.unmodifiableSet(set);
    }

    /** 该附魔是否可以通过 30 级附魔台附加到该装备 */
    public boolean tableReachable(String itemId, String enchantmentId) {
        GearCandidateRule gear = gear(itemId);
        if (gear == null) return false;
        return gear.tableReachable(fullId(enchantmentId));
    }

    /** 该附魔在 30 级附魔台下该装备的可达等级列表 */
    public List<Integer> reachableLevels(String itemId, String enchantmentId) {
        GearCandidateRule gear = gear(itemId);
        if (gear == null) return List.of();
        return gear.reachable(fullId(enchantmentId));
    }

    /** 附魔静态最大等级（注册表未就绪时兜底），未知返回 -1 */
    public int maxLevelOf(String enchantmentId) {
        EnchantmentRule r = rule(enchantmentId);
        return r == null ? -1 : r.maxLevel();
    }

    /** 全量规则表（校验 / 报告用） */
    public List<EnchantmentRule> rules() {
        return List.copyOf(enchantments.values());
    }

    /** 全量装备候选规则（校验 / 报告用） */
    public List<GearCandidateRule> gears() {
        return List.copyOf(gears.values());
    }

    /** 数据版本信息 */
    public String minecraftVersion() {
        return minecraftVersion;
    }

    public int dataVersion() {
        return dataVersion;
    }

    /** 加载期间收集的错误（校验器从中取数） */
    public List<String> loadErrors() {
        return List.copyOf(loadErrors);
    }

    // ── 工具 ──────────────────────────────────────────────────────────────

    /** 把裸 id（如 sharpness）补全为完整 id（minecraft:sharpness），带命名空间原样返回 */
    public static String fullId(String raw) {
        if (raw == null || raw.isEmpty()) return null;
        return raw.indexOf(':') < 0 ? "minecraft:" + raw : raw;
    }

    /** 装备完整 id 去命名空间（diamond_pickaxe），用于候选文件路径推导 */
    private static String pathOf(String itemId) {
        int idx = itemId.indexOf(':');
        return idx < 0 ? itemId : itemId.substring(idx + 1);
    }

    private static <T> T readJson(String path, Class<T> type) {
        // 资源读取：classpath 直读（src/main/resources/enchantment/vanilla/ 下的静态 JSON）
        String json;
        try (InputStream in = VanillaEnchantDatabase.class.getResourceAsStream(path)) {
            if (in == null) return null;
            json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
        return new Gson().fromJson(json, type);
    }
}
