package com.yiyiaddon.feature.librarian.service;

/**
 * 自动图书管理员 · 移动状态。
 *
 * <p>描述寻路移动任务在整个生命周期中的当前状态，供编排器轮询判断
 * 是否到达、失败或超时，从而决定是否进入下一环节。</p>
 *
 * <p>该状态会作为失败原因与调试信息播报给玩家，因此自带中文名。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/MovementStatus}（40 行），逐字照搬。</p>
 */
public enum MovementStatus {
    /** 空闲，未发起移动 */
    IDLE("空闲"),
    /** 移动任务启动中 */
    STARTING("启动中"),
    /** 正在寻路 */
    PATHING("寻路中"),
    /** 已到达目标 */
    ARRIVED("已到达"),
    /** 移动失败 */
    FAILED("移动失败"),
    /** 移动被取消 */
    CANCELED("移动已取消"),
    /** 移动超时 */
    TIMED_OUT("移动超时"),
    /** 移动服务不可用 */
    UNAVAILABLE("移动服务不可用");

    private final String displayName;

    MovementStatus(String displayName) {
        this.displayName = displayName;
    }

    /** 玩家可见中文名 */
    public String displayName() {
        return displayName;
    }
}
