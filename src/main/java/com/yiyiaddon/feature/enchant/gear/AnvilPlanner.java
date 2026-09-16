package com.yiyiaddon.feature.enchant.gear;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 铁砧规划器（AnvilPlanner）。
 *
 * <p>职责：把「附魔台随机附魔出的多件同类型装备」通过铁砧「装备 + 装备」两两合并，
 * 把分散在各件装备上的目标附魔叠加到一件上，最终凑齐 {@link TargetProfile} 定义的
 * JSON 极品方案。核心是<b>合并判定</b>：脚本要能分辨「哪些装备该合并、合并是否合理」。</p>
 *
 * <p>合并判定三原则：</p>
 * <ul>
 *   <li><b>互补</b>：材料装备带有主装备「缺失」或「等级不足」的目标附魔，合并才有价值；</li>
 *   <li><b>不互斥</b>：材料附魔与主装备现有附魔互斥（如锋利 vs 亡灵杀手）时合并会丢附魔，判定为不合理；</li>
 *   <li><b>不冗余</b>：材料附魔主装备已有且达标，合并无收益，跳过。</li>
 * </ul>
 *
 * <p>不合理的装备不进入合并计划，交由上层砂轮磨掉重来。</p>
 *
 * <p>合并策略见 {@link AnvilStrategy}：三套策略只改变「主装备选择」与「材料排序」权重，
 * 不改互补/互斥合并判定。</p>
 *
 * <p>铁律：<b>绝不自己模拟铁砧 XP 公式</b>。执行时读 {@code AnvilMenu.getCost()} 实际
 * 显示费用作为唯一决策依据。本类只做「附魔集合的合并模拟 + 互斥判断」，用于规划排序，
 * 不估算任何经验数值。</p>
 */
public final class AnvilPlanner {

    /** 生存模式铁砧费用上限（超过即「太昂贵」） */
    public static final int MAX_ANVIL_COST = 39;

    /** 默认最大铁砧操作次数（防无限操作） */
    public static final int DEFAULT_MAX_OPERATIONS = 6;

    private AnvilPlanner() {
    }

    /**
     * 生成「装备 + 装备」铁砧合并计划。
     *
     * @param profile 目标极品方案（JSON 定义）
     * @param gears   多件已附魔的同类型装备（如 4 把钻石剑）
     * @return 铁砧合并计划（空计划表示无需合并或材料不足）
     */
    public static AnvilPlan plan(TargetProfile profile, List<ItemStack> gears, AnvilStrategy strategy) {
        if (gears == null || gears.size() < 2) {
            return new AnvilPlan(List.of(), DEFAULT_MAX_OPERATIONS);
        }
        AnvilStrategy s = strategy == null ? AnvilStrategy.SIMPLE : strategy;

        // 按策略选主装备（基础件）
        ItemStack base = selectBase(profile, gears, s);
        Map<String, Integer> baseEnch = new HashMap<>(EnchantEvaluationService.readEnchantments(base));

        List<AnvilStep> steps = new ArrayList<>();
        int order = 1;
        // 材料按策略排序
        for (ItemStack material : sortedMaterials(profile, base, baseEnch, gears, s)) {
            Map<String, Integer> matEnch = EnchantEvaluationService.readEnchantments(material);
            if (!isComplementary(baseEnch, matEnch, profile)) {
                continue;
            }
            if (hasConflict(baseEnch, matEnch)) {
                continue;
            }
            String contrib = primaryContribution(material, profile);
            steps.add(new AnvilStep(order++, base.copy(), material.copy(), contrib, targetLevelOf(profile, contrib)));
            // 模拟合并后的附魔集合，供后续材料判定使用（仅逻辑模拟，不算 XP）
            mergeInto(baseEnch, matEnch);
            // 全部活动目标已达标即停止：不再多余合并，避免无谓 XP 与铁砧次数
            if (allSatisfied(baseEnch, profile)) break;
        }
        return new AnvilPlan(steps, DEFAULT_MAX_OPERATIONS);
    }

    /** 按策略选主装备（基础件） */
    public static ItemStack selectBase(TargetProfile profile, List<ItemStack> gears, AnvilStrategy strategy) {
        ItemStack best = gears.get(0);
        int bestRepair = repairCost(best);
        int bestWeight = anvilCostWeight(best);
        int bestScore = contributionScore(best, profile);
        for (ItemStack gear : gears) {
            int rep = repairCost(gear);
            int weight = anvilCostWeight(gear);
            int score = contributionScore(gear, profile);
            if (preferBase(score, rep, weight, bestScore, bestRepair, bestWeight, strategy)) {
                best = gear;
                bestRepair = rep;
                bestWeight = weight;
                bestScore = score;
            }
        }
        return best;
    }

    /** 主装备择优比较：返回 true 表示候选优于当前 */
    private static boolean preferBase(int score, int rep, int weight,
                                      int bestScore, int bestRepair, int bestWeight, AnvilStrategy strategy) {
        AnvilStrategy s = strategy == null ? AnvilStrategy.SIMPLE : strategy;
        return switch (s) {
            case SIMPLE -> score > bestScore;                       // 纯贡献优先
            case SAVE_XP -> score > bestScore
                || (score == bestScore && rep < bestRepair);        // 贡献优先 + 低惩罚
            case FAST -> score > bestScore
                || (score == bestScore && weight < bestWeight);     // 贡献优先 + 低成本
        };
    }

    /** 装备对目标的贡献分数：命中 +1，再按等级加权（等级越贴近目标越值钱，选主装备更准） */
    public static int contributionScore(ItemStack gear, TargetProfile profile) {
        Map<String, Integer> ench = EnchantEvaluationService.readEnchantments(gear);
        int score = 0;
        for (TargetProfile.TargetEnchantment target : profile.activeTargets()) {
            Integer level = ench.get(target.id());
            if (level == null) continue;
            score += 1 + Math.min(level, target.level());
        }
        return score;
    }

    /** 读装备 prior work penalty（铁砧累积惩罚），值越高后续合并越贵，缺失按 0 处理 */
    public static int repairCost(ItemStack stack) {
        return stack.getOrDefault(DataComponents.REPAIR_COST, 0);
    }

    /** 估算装备的铁砧成本权重：Σ(附魔 getAnvilCost × 等级)，用于低成本优先排序（不模拟 XP） */
    public static int anvilCostWeight(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return 0; // 注册表未就绪，返回 0 兜底
        int weight = 0;
        for (Map.Entry<String, Integer> e : EnchantEvaluationService.readEnchantments(stack).entrySet()) {
            Holder<Enchantment> holder = enchantmentOf(mc, e.getKey());
            if (holder != null) {
                weight += holder.value().getAnvilCost() * Math.max(0, e.getValue());
            }
        }
        return weight;
    }

    /** 装备对目标的主要贡献附魔 id（第一个命中的目标附魔），无贡献返回 null */
    public static String primaryContribution(ItemStack gear, TargetProfile profile) {
        Map<String, Integer> ench = EnchantEvaluationService.readEnchantments(gear);
        for (TargetProfile.TargetEnchantment target : profile.activeTargets()) {
            if (ench.containsKey(target.id())) return target.id();
        }
        return null;
    }

    /** 判断装备是否带目标附魔（可作为合并材料） */
    public static boolean hasTargetContribution(ItemStack gear, TargetProfile profile) {
        return primaryContribution(gear, profile) != null;
    }

    /**
     * 互补性判定：材料是否带来主装备「缺失」或「等级不足」的目标附魔。
     * 材料所有目标附魔主装备都已达标 → 冗余，返回 false。
     */
    private static boolean isComplementary(Map<String, Integer> baseEnch, Map<String, Integer> matEnch, TargetProfile profile) {
        for (TargetProfile.TargetEnchantment target : profile.activeTargets()) {
            Integer matLevel = matEnch.get(target.id());
            if (matLevel == null) continue;
            Integer baseLevel = baseEnch.get(target.id());
            if (baseLevel == null) return true;                 // 主装备缺失 → 互补
            if (baseLevel >= target.level()) continue;          // 已达标 → 无需再补
            if (matLevel >= baseLevel) return true;             // 材料等级不低于主装备 → 合成有提升
        }
        return false;
    }

    /** 互斥判定：材料附魔与主装备附魔是否存在互斥（用 26.1.2 官方 areCompatible） */
    private static boolean hasConflict(Map<String, Integer> baseEnch, Map<String, Integer> matEnch) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false; // 注册表未就绪，跳过互斥检测
        for (String baseId : baseEnch.keySet()) {
            for (String matId : matEnch.keySet()) {
                if (baseId.equals(matId)) continue; // 相同附魔不互斥（会升级）
                Holder<Enchantment> a = enchantmentOf(mc, baseId);
                Holder<Enchantment> b = enchantmentOf(mc, matId);
                if (a != null && b != null && !Enchantment.areCompatible(a, b)) return true;
            }
        }
        return false;
    }

    /** 附魔 id → Holder，找不到返回 null */
    private static Holder<Enchantment> enchantmentOf(Minecraft mc, String id) {
        Identifier ident = Identifier.tryParse(id);
        if (ident == null) return null;
        var holder = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
            .get(ResourceKey.create(Registries.ENCHANTMENT, ident));
        return holder.orElse(null);
    }

    /** 模拟附魔合并（仅用于规划排序，不算 XP）：遵循原版铁砧规则 */
    private static void mergeInto(Map<String, Integer> target, Map<String, Integer> source) {
        for (Map.Entry<String, Integer> e : source.entrySet()) {
            String id = e.getKey();
            int level = e.getValue();
            Integer existing = target.get(id);
            if (existing == null) {
                target.put(id, level);          // 新增附魔
            } else if (existing.equals(level)) {
                target.put(id, existing + 1);   // 同等级合并 → 等级 +1
            } else if (level > existing) {
                target.put(id, level);          // 材料等级更高 → 取较高者
            }
            // level < existing：无提升，保持原样
        }
    }

    /** 材料按策略排序（仅排序，互补/互斥判定仍走主流程） */
    private static List<ItemStack> sortedMaterials(TargetProfile profile, ItemStack base,
                                                   Map<String, Integer> baseEnch, List<ItemStack> gears,
                                                   AnvilStrategy strategy) {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack gear : gears) {
            if (gear != base) list.add(gear);
        }
        list.sort((a, b) -> compareMaterial(a, b, baseEnch, profile,
            strategy == null ? AnvilStrategy.SIMPLE : strategy));
        return list;
    }

    /** 材料排序比较：返回负数表示 a 优先，正数表示 b 优先 */
    private static int compareMaterial(ItemStack a, ItemStack b, Map<String, Integer> base,
                                       TargetProfile profile, AnvilStrategy strategy) {
        int impA = improvement(base, EnchantEvaluationService.readEnchantments(a), profile);
        int impB = improvement(base, EnchantEvaluationService.readEnchantments(b), profile);
        int repA = repairCost(a);
        int repB = repairCost(b);
        int wA = anvilCostWeight(a);
        int wB = anvilCostWeight(b);
        return switch (strategy) {
            case SIMPLE -> Integer.compare(impB, impA);                 // 纯提升价值降序
            case SAVE_XP -> {
                int c = Integer.compare(impB, impA);                    // 提升价值降序（减少合并步数=省XP，避免PWP累积触发太昂贵）
                if (c != 0) yield c;
                yield Integer.compare(repA, repB);                      // 低惩罚升序
            }
            case FAST -> {
                int c = Integer.compare(impB, impA);                    // 提升价值降序
                if (c != 0) yield c;
                yield Integer.compare(wA, wB);                          // 低成本升序
            }
        };
    }

    /** 材料相对主装备的提升价值：新增缺失附魔 +2，升级等级不足 +1 */
    private static int improvement(Map<String, Integer> base, Map<String, Integer> mat, TargetProfile profile) {
        int value = 0;
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            Integer ml = mat.get(t.id());
            if (ml == null) continue;
            Integer bl = base.get(t.id());
            if (bl == null) value += 2;                       // 缺失 → 直接补上
            else if (bl < t.level() && ml >= bl) value += 1;  // 等级不足且材料可升级
        }
        return value;
    }

    /** 主装备附魔集合是否已满足全部活动目标（用于提前终止合并） */
    private static boolean allSatisfied(Map<String, Integer> ench, TargetProfile profile) {
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            Integer level = ench.get(t.id());
            if (level == null || level < t.level()) return false;
        }
        return true;
    }

    /** 查某附魔在目标方案里的目标等级 */
    private static int targetLevelOf(TargetProfile profile, String enchantId) {
        for (TargetProfile.TargetEnchantment target : profile.activeTargets()) {
            if (target.id().equals(enchantId)) return target.level();
        }
        return 0;
    }

    /**
     * 读取铁砧当前实际显示的等级费用。
     * 这是唯一的费用来源，禁止用自算公式替代。
     */
    public static int readCost(AnvilMenu menu) {
        return menu.getCost();
    }

    /** 判断费用是否「太昂贵」（超过生存上限） */
    public static boolean isTooExpensive(int cost) {
        return cost > MAX_ANVIL_COST;
    }
}
