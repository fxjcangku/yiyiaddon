package com.yiyiaddon.feature.librarian.service;

/**
 * 自动图书管理员 · 业务动作状态。
 *
 * <p>表示一次业务动作（放置讲台、打开交易、购买等）的执行结果状态，
 * 供编排器决定下一步推进或重试。</p>
 *
 * <p>该状态会进入调试播报，因此自带中文名，禁止把枚举名直接展示给玩家。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/ActionStatus}（32 行），逐字照搬。</p>
 */
public enum ActionStatus {
    /** 动作成功完成 */
    SUCCESS("成功"),
    /** 动作已提交，等待服务端响应 */
    WAITING("等待响应"),
    /** 动作需要重试 */
    RETRY("需要重试"),
    /** 动作失败 */
    FAILED("失败");

    private final String displayName;

    ActionStatus(String displayName) {
        this.displayName = displayName;
    }

    /** 玩家可见中文名 */
    public String displayName() {
        return displayName;
    }
}
