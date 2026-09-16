package com.yiyiaddon.feature.enchant.gear;

/**
 * 铁砧「装备+装备」合并策略。
 *
 * <p>逐字来自旧项目 {@code AnvilPlanner.Strategy}（{@code AnvilPlanner.java:50-68}），
 * 中文名即 {@code toString()}，默认 {@link #SAVE_XP}（旧项目 {@code AutoEnchantBook:461}）。</p>
 *
 * <p>三档只改变「主装备选择」与「材料排序」权重，<b>不改</b>互补 / 不互斥 / 不冗余的合并判定。</p>
 */
public enum AnvilStrategy {

    SIMPLE("简单"),
    SAVE_XP("节能"),
    FAST("快速");

    private final String title;

    AnvilStrategy(String title) {
        this.title = title;
    }

    /** 中文名（设置项选项与播报用） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
