package com.yiyiaddon.feature.enchant.gear;

/**
 * 原版装备附魔 · 任务异常原因。
 *
 * <p>覆盖所有装备处理失败（跳过该件）的触发条件，语义明确，避免用模糊字符串。</p>
 */
public enum TaskErrorReason {

    /** 铁砧实际显示「太昂贵」 */
    TOO_EXPENSIVE("太昂贵"),
    /** 无法生成合法合并方案 */
    CANNOT_PLAN("无法规划"),
    /** 附魔冲突（互斥附魔无法共存） */
    ENCHANTMENT_CONFLICT("附魔冲突"),
    /** 无法达到目标（经验/资源/点位无法满足） */
    CANNOT_REACH_TARGET("无法达到目标"),
    /** 装备身份异常（空/非钻石装备等） */
    GEAR_IDENTITY_INVALID("装备身份异常"),
    /** 装备耐久不足（低于最低耐久比例） */
    DURABILITY_LOW("耐久不足"),
    /** 状态机异常 */
    STATE_MACHINE_ERROR("状态机异常"),
    /** 连续执行失败 */
    REPEATED_FAILURE("连续执行失败"),
    /** 超过最大铁砧操作次数 */
    MAX_ANVIL_OPERATIONS("超过最大铁砧次数");

    private final String title;

    TaskErrorReason(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
