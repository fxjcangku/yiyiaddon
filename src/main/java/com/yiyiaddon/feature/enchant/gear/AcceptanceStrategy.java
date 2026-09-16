package com.yiyiaddon.feature.enchant.gear;

/**
 * 原版装备附魔 · 评估接受策略。
 *
 * <p>决定「附魔台产出的实际装备是否值得保留」，三种策略语义明确：</p>
 * <ul>
 *   <li>严格：所有目标附魔（必需 + 可选）必须满级才达标。</li>
 *   <li>平衡：必需附魔全部满级，可选附魔达到最低接受完成度。</li>
 *   <li>宽松：必需附魔达到最低接受完成度即可。</li>
 * </ul>
 */
public enum AcceptanceStrategy {

    /** 严格：所有目标（必需+可选）满级 */
    STRICT("严格"),
    /** 平衡：必需满级 + 可选达标 */
    BALANCED("平衡"),
    /** 宽松：必需达标 */
    LOOSE("宽松");

    private final String title;

    AcceptanceStrategy(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
