package com.yiyiaddon.feature.enchant.vanilla;

import com.yiyiaddon.feature.enchant.gear.AnvilPlan;
import com.yiyiaddon.feature.enchant.gear.AnvilPlanner;
import com.yiyiaddon.feature.enchant.gear.AnvilStrategy;
import com.yiyiaddon.feature.enchant.gear.EnchantEvaluationService;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 原版装备极品附魔 · 规划引擎（EnchantPlanningEngine）。
 *
 * <p>职责：接收「当前背包已附魔装备 + 目标 JSON」，输出下一步最佳操作。它是
 * 数据基础层与执行层的衔接点，回答 §54 的问题 A~D：</p>
 * <ul>
 *   <li>问题 A：当前附魔结果有没有价值？→ 由 {@link TargetMatcher} 判定；</li>
 *   <li>问题 B：保留还是砂轮？→ 垃圾（禁止/互斥/零命中）才砂轮，高价值中间态保留；</li>
 *   <li>问题 C/D：是否值得合并、顺序？→ 复用既有 {@link AnvilPlanner}，不新建第二套铁砧规划。</li>
 * </ul>
 *
 * <p>铁律（§30 / §45 / §46）：本引擎<b>不</b>重写状态机、不重写 .fumo、不重写发包，
 * 只返回 {@link Decision} 交由现有 FSM 执行；需要铁砧时委托既有 {@link AnvilPlanner}。</p>
 */
public final class EnchantPlanningEngine {

    private EnchantPlanningEngine() {
    }

    /** 下一步动作 */
    public enum Action {
        /** 已出现 100% 达标成品，直接卸货 */
        COMPLETE,
        /** 有价值中间态 ≥ 2 且可互补合并，进入铁砧 */
        ANVIL,
        /** 存在必须砂轮的垃圾装备（禁止/互斥/零命中），先去砂轮 */
        GRIND,
        /** 无可合并、无垃圾，继续附魔更多裸装备 */
        CONTINUE,
        /** 必需附魔不可达，目标无法达成 */
        UNREACHABLE
    }

    /** 规划决策结果 */
    public record Decision(Action action, ItemStack completeItem, AnvilPlan anvilPlan,
                           int valuableCount, int junkCount, String detail) {

        public boolean hasAnvilPlan() {
            return anvilPlan != null && anvilPlan.hasNext();
        }
    }

    /**
     * 对一批已附魔装备做规划决策。
     *
     * @param profile 目标极品方案
     * @param gears   当前背包已附魔的目标装备（含保留的中间态）
     */
    public static Decision decide(TargetProfile profile, List<ItemStack> gears, AnvilStrategy strategy) {
        if (gears == null || gears.isEmpty()) {
            return new Decision(Action.CONTINUE, null, null, 0, 0, "无已附魔装备，继续附魔");
        }

        List<ItemStack> valuable = new ArrayList<>();
        ItemStack complete = null;
        int junk = 0;
        boolean unreachable = false;

        for (ItemStack gear : gears) {
            TargetMatcher.Result m = TargetMatcher.match(gear, profile);
            if (m.complete()) {
                complete = gear;
            } else if (TargetMatcher.shouldGrind(m, profile, EnchantEvaluationService.readEnchantments(gear))) {
                // 统一判定：禁止/互斥/零命中/低密度（离满级远）送砂轮，与执行层 findJunkGear 保持一致
                junk++;
            } else {
                valuable.add(gear);
            }
            if (m.state() == TargetMatcher.State.UNREACHABLE) {
                unreachable = true;
            }
        }

        // 1. 出现成品 → 立即卸货
        if (complete != null) {
            return new Decision(Action.COMPLETE, complete, null, valuable.size(), junk, "已达成极品目标");
        }

        // 2. 必需附魔不可达 → 直接判死，不再循环
        if (unreachable) {
            return new Decision(Action.UNREACHABLE, null, null, valuable.size(), junk, "必需附魔无法由 30 级附魔台获得");
        }

        // 3. 存在垃圾 → 先砂轮磨掉（高价值中间态保留，只磨垃圾）。
        //    优先于铁砧：避免垃圾装备（含 fire_protection 等互斥附魔）被误当主装备合并，导致费用=0 死循环
        if (junk > 0) {
            return new Decision(Action.GRIND, null, null, valuable.size(), junk, "存在 " + junk + " 件垃圾需砂轮");
        }

        // 4. 有价值中间态 ≥ 2 且可互补合并 → 铁砧
        if (valuable.size() >= 2) {
            AnvilPlan plan = AnvilPlanner.plan(profile, valuable, strategy);
            if (plan.hasNext()) {
                return new Decision(Action.ANVIL, null, plan, valuable.size(), junk, "规划铁砧合并 " + plan.steps().size() + " 步");
            }
        }

        // 5. 无合并、无垃圾 → 继续附魔（补更多裸装备扩充候选池）
        return new Decision(Action.CONTINUE, null, null, valuable.size(), 0, "候选不足，继续附魔");
    }
}
