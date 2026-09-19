package com.yiyiaddon.feature.librarian.service;

/**
 * 自动图书管理员 · 移动启动结果。
 *
 * <p>表示发起一次寻路移动请求后的即时结果，用于判断移动服务是否真正接手，
 * 以及是否需要重试或降级。</p>
 *
 * <p>该结果会作为失败原因播报给玩家，因此自带中文名，禁止把枚举名直接拼进聊天消息。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/MovementStartResult}（34 行），逐字照搬。</p>
 */
public enum MovementStartResult {
    /** 移动已启动 */
    STARTED("已启动移动"),
    /** 已有移动任务在运行 */
    ALREADY_RUNNING("已有移动任务在运行"),
    /** 移动服务不可用 */
    UNAVAILABLE("移动服务不可用"),
    /** 移动请求被拒绝 */
    REJECTED("移动请求被拒绝"),
    /** 启动失败 */
    FAILED("移动启动失败");

    private final String displayName;

    MovementStartResult(String displayName) {
        this.displayName = displayName;
    }

    /** 玩家可见中文名 */
    public String displayName() {
        return displayName;
    }
}
