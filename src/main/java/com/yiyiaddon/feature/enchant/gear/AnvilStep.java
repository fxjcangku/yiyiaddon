package com.yiyiaddon.feature.enchant.gear;

import net.minecraft.world.item.ItemStack;

/**
 * 铁砧操作步骤（AnvilPlanner 生成的单步合并计划）。
 *
 * <p>描述一次「左槽装备 + 右槽附魔书」的铁砧合并，目标是给装备补上某个附魔到目标等级。</p>
 *
 * <p>实际 XP 费用不在规划阶段计算（禁止自模拟铁砧公式），而是执行时通过
 * {@code AnvilMenu.getCost()} 读取 Minecraft 实际显示费用后回填到本对象。</p>
 */
public final class AnvilStep {

    /** 操作顺序（1 起） */
    private final int order;
    /** 左槽：当前装备快照 */
    private final ItemStack leftItem;
    /** 右槽：合并物品（附魔书）快照 */
    private final ItemStack rightItem;
    /** 目标附魔 ID（如 minecraft:efficiency） */
    private final String targetEnchantment;
    /** 目标等级 */
    private final int targetLevel;

    /** 实际 XP 费用（执行时读取，null 表示尚未读取） */
    private Integer actualXpCost;
    /** 是否太昂贵（Minecraft 实际显示「太昂贵」） */
    private boolean tooExpensive;
    /** 是否已执行 */
    private boolean executed;

    public AnvilStep(int order, ItemStack leftItem, ItemStack rightItem, String targetEnchantment, int targetLevel) {
        this.order = order;
        this.leftItem = leftItem;
        this.rightItem = rightItem;
        this.targetEnchantment = targetEnchantment;
        this.targetLevel = targetLevel;
    }

    public int order() {
        return order;
    }

    public ItemStack leftItem() {
        return leftItem;
    }

    public ItemStack rightItem() {
        return rightItem;
    }

    public String targetEnchantment() {
        return targetEnchantment;
    }

    public int targetLevel() {
        return targetLevel;
    }

    public Integer actualXpCost() {
        return actualXpCost;
    }

    public void actualXpCost(Integer actualXpCost) {
        this.actualXpCost = actualXpCost;
    }

    public boolean tooExpensive() {
        return tooExpensive;
    }

    public void tooExpensive(boolean tooExpensive) {
        this.tooExpensive = tooExpensive;
    }

    public boolean executed() {
        return executed;
    }

    public void executed(boolean executed) {
        this.executed = executed;
    }

    /** 是否可执行：尚未执行且未标记太昂贵 */
    public boolean executable() {
        return !executed && !tooExpensive;
    }
}
