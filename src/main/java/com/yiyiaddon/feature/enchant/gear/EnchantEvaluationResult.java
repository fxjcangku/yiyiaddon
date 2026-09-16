package com.yiyiaddon.feature.enchant.gear;

import java.util.List;

/**
 * 原版装备附魔 · 评估结果（不可变快照）。
 *
 * <p>由 {@link EnchantEvaluationService} 生成，描述实际装备与 {@link TargetProfile}
 * 的差距，供「保留 / 砂轮」决策与后续铁砧规划使用。</p>
 */
public record EnchantEvaluationResult(
    /** 目标总数（未排除的目标附魔数量） */
    int totalTargets,
    /** 已满足目标（实际等级 >= 目标等级） */
    List<TargetProfile.TargetEnchantment> satisfied,
    /** 等级不足目标（实际有但等级低于目标） */
    List<TargetProfile.TargetEnchantment> underleveled,
    /** 缺失目标（实际没有该附魔） */
    List<TargetProfile.TargetEnchantment> missing,
    /** 多余附魔（实际有但不在目标内） */
    List<String> extra,
    /** 被排除但实际存在的附魔（玩家已排除，装备上却仍有） */
    List<String> excludedPresent,
    /** 完成度（0~1，已满足 / 目标总数） */
    double completionRatio,
    /** 是否达到最低接受标准（按策略 + 最低接受完成度） */
    boolean acceptable,
    /** 是否最终完成（所有目标满级） */
    boolean complete
) {
    /** 已满足目标数量 */
    public int satisfiedCount() {
        return satisfied.size();
    }

    /** 等级不足数量 */
    public int underleveledCount() {
        return underleveled.size();
    }

    /** 缺失目标数量 */
    public int missingCount() {
        return missing.size();
    }

    /** 多余附魔数量 */
    public int extraCount() {
        return extra.size();
    }
}
