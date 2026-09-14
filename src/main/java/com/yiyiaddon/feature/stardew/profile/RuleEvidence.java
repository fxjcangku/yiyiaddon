package com.yiyiaddon.feature.stardew.profile;

/**
 * 规则证据等级。
 *
 * <p>区分一条作物规则的可信来源，避免把「攻略记载 / 候选推导」当成「已确认」，
 * 关键破坏性动作（收割 / 死亡清理）只能基于 VERIFIED 或明确 DOCUMENTED 执行。</p>
 *
 * <ul>
 *   <li>VERIFIED   —— 真实客户端观察、可靠结构或实际行为已验证；</li>
 *   <li>DOCUMENTED —— 攻略、Lore 或明确玩法配置记载；</li>
 *   <li>CANDIDATE  —— 模型、名称、关联推导出的候选；</li>
 *   <li>UNKNOWN    —— 没有可靠证据。</li>
 * </ul>
 */
public enum RuleEvidence {

    VERIFIED("已确认"),
    DOCUMENTED("攻略记载"),
    CANDIDATE("候选推导"),
    UNKNOWN("未知");

    private final String displayName;

    RuleEvidence(String displayName) {
        this.displayName = displayName;
    }

    /** 中文显示名 */
    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
