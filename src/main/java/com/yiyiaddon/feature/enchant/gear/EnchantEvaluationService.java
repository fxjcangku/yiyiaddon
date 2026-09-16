package com.yiyiaddon.feature.enchant.gear;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 原版装备附魔 · 结果评估服务。
 *
 * <p>核心职责：用 26.1.2 Data Component API 完整读取实际装备的附魔数据，
 * 与 {@link TargetProfile} 对比，输出差距（已满足 / 等级不足 / 缺失 / 多余 /
 * 被排除）、完成度、是否达标（保留）、是否最终完成。</p>
 *
 * <p>关键点：附魔台 GUI 无法直接完整展示所有附魔，因此必须读取实际产出物品的
 * {@code DataComponents.ENCHANTMENTS} 完整数据，而非只看 GUI 第一行文本。
 * 不沿用旧 NBT 方案。</p>
 */
public final class EnchantEvaluationService {

    private EnchantEvaluationService() {
    }

    /**
     * 读取装备完整附魔（Data Component API）。
     * 返回 enchantmentId → 等级的映射，附魔 ID 取 ResourceKey 的 identifier 字符串。
     */
    public static Map<String, Integer> readEnchantments(ItemStack stack) {
        Map<String, Integer> result = new LinkedHashMap<>();
        if (stack == null || stack.isEmpty()) return result;

        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return result;

        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            String id = holder.unwrapKey().map(key -> key.identifier().toString()).orElse(null);
            if (id == null) continue;
            result.put(id, entry.getIntValue());
        }
        return result;
    }

    /**
     * 评估实际装备 vs 目标。
     *
     * @param actual         实际产出装备
     * @param profile        目标方案
     * @param strategy       接受策略（严格 / 平衡 / 宽松）
     * @param minAcceptRatio 最低接受完成度（0~1，如 2/3 = 0.67）
     */
    public static EnchantEvaluationResult evaluate(ItemStack actual, TargetProfile profile,
                                                   AcceptanceStrategy strategy, double minAcceptRatio) {
        Map<String, Integer> actualEnch = readEnchantments(actual);

        List<TargetProfile.TargetEnchantment> satisfied = new ArrayList<>();
        List<TargetProfile.TargetEnchantment> underleveled = new ArrayList<>();
        List<TargetProfile.TargetEnchantment> missing = new ArrayList<>();
        List<String> extra = new ArrayList<>();
        List<String> excludedPresent = new ArrayList<>();

        // 目标 id → TargetEnchantment（含被排除项，用于识别「被排除但实际存在」）
        Map<String, TargetProfile.TargetEnchantment> allTargets = new HashMap<>();
        for (TargetProfile.TargetEnchantment t : profile.targets()) {
            allTargets.put(t.id(), t);
        }

        // 评分：遍历未排除的目标
        for (TargetProfile.TargetEnchantment target : profile.activeTargets()) {
            Integer actualLevel = actualEnch.get(target.id());
            if (actualLevel == null) {
                missing.add(target);
            } else if (actualLevel >= target.level()) {
                satisfied.add(target);
            } else {
                underleveled.add(target);
            }
        }

        // 分类实际附魔：多余 / 被排除
        for (Map.Entry<String, Integer> entry : actualEnch.entrySet()) {
            TargetProfile.TargetEnchantment target = allTargets.get(entry.getKey());
            if (target == null) {
                extra.add(entry.getKey());
            } else if (target.excluded()) {
                excludedPresent.add(entry.getKey());
            }
        }

        int total = profile.activeTargets().size();
        double completion = total == 0 ? 1.0 : (double) satisfied.size() / total;
        boolean complete = total > 0 && satisfied.size() == total;
        boolean acceptable = isAcceptable(profile, satisfied, strategy, clampRatio(minAcceptRatio));

        return new EnchantEvaluationResult(
            total, satisfied, underleveled, missing, extra, excludedPresent,
            completion, acceptable, complete
        );
    }

    /** 按策略 + 最低接受完成度判断是否值得保留 */
    private static boolean isAcceptable(TargetProfile profile, List<TargetProfile.TargetEnchantment> satisfied,
                                        AcceptanceStrategy strategy, double minAcceptRatio) {
        List<TargetProfile.TargetEnchantment> required = profile.requiredTargets();
        List<TargetProfile.TargetEnchantment> optional = profile.optionalTargets();

        int satisfiedRequired = countSatisfied(required, satisfied);
        int satisfiedOptional = countSatisfied(optional, satisfied);

        return switch (strategy) {
            // 严格：所有目标（必需+可选）满级
            case STRICT -> satisfied.size() == profile.activeTargets().size() && !profile.activeTargets().isEmpty();
            // 平衡：必需全部满级，可选达到最低接受完成度
            case BALANCED -> satisfiedRequired == required.size()
                && (optional.isEmpty() || ratio(satisfiedOptional, optional.size()) >= minAcceptRatio);
            // 宽松：必需达到最低接受完成度
            case LOOSE -> required.isEmpty() || ratio(satisfiedRequired, required.size()) >= minAcceptRatio;
        };
    }

    /** 统计目标列表中有多少已满足（满级） */
    private static int countSatisfied(List<TargetProfile.TargetEnchantment> targets,
                                      List<TargetProfile.TargetEnchantment> satisfied) {
        int count = 0;
        for (TargetProfile.TargetEnchantment target : targets) {
            if (satisfied.contains(target)) count++;
        }
        return count;
    }

    /** 计算满足比例，空集视为 1.0 */
    private static double ratio(int satisfied, int total) {
        return total == 0 ? 1.0 : (double) satisfied / total;
    }

    /** 把最低接受完成度 clamp 到 [0, 1] */
    private static double clampRatio(double ratio) {
        return Math.max(0.0, Math.min(1.0, ratio));
    }
}
