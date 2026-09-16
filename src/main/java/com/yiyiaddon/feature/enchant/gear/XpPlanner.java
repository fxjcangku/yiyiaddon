package com.yiyiaddon.feature.enchant.gear;

import java.util.List;

/**
 * 原版装备附魔 · 经验规划器。
 *
 * <p>把「附魔台」与「铁砧」的经验逻辑分开：</p>
 * <ul>
 *   <li>附魔台：固定需要 {@value #ENCHANT_TABLE_LEVEL} 级（30 级附魔），不足则挂机补到 30。</li>
 *   <li>铁砧：需要多少级由 {@code AnvilMenu.getCost()} 实际显示决定，不足则挂机补到该级。</li>
 * </ul>
 *
 * <p>批次 XP 规划：不一件装备刷一次，而是分析整批所有已知必要 XP，取最高需求
 * 一次性刷到位，再连续处理多件装备，避免反复往返挂机点。</p>
 */
public final class XpPlanner {

    /** 附魔台固定经验需求（30 级附魔） */
    public static final int ENCHANT_TABLE_LEVEL = 30;

    private XpPlanner() {
    }

    /**
     * 计算批次目标经验等级：附魔台固定 30 + 各铁砧实际费用，取最高。
     *
     * @param anvilCosts 本批次各装备的铁砧实际费用（来自 AnvilMenu.getCost()）
     */
    public static int batchTargetLevel(List<Integer> anvilCosts) {
        int target = ENCHANT_TABLE_LEVEL;
        if (anvilCosts != null) {
            for (int cost : anvilCosts) {
                if (cost > target) target = cost;
            }
        }
        return target;
    }

    /** 附魔台是否经验不足（低于 30 级） */
    public static boolean needsEnchantGrinding(int currentLevel) {
        return currentLevel < ENCHANT_TABLE_LEVEL;
    }

    /** 铁砧是否经验不足（低于该次铁砧实际费用） */
    public static boolean needsAnvilGrinding(int currentLevel, int anvilCost) {
        return currentLevel < anvilCost;
    }

    /** 通用判断：当前经验是否低于目标等级 */
    public static boolean needsGrinding(int currentLevel, int targetLevel) {
        return currentLevel < targetLevel;
    }
}
