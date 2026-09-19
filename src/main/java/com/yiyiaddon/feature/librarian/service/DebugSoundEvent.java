package com.yiyiaddon.feature.librarian.service;

/**
 * 自动图书管理员 · 调试声音事件。
 *
 * <p>调试模式下各关键节点触发的音效标识，帮助玩家「听声」判断当前自动化
 * 进度处于哪个环节，便于无界面观察运行状态。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/DebugSoundEvent}（29 行），取值顺序照搬。
 * 该枚举只在 {@code 调试模式} 打开时由编排器的状态转换回调使用（无中文名，
 * 不直接展示给玩家）。</p>
 */
public enum DebugSoundEvent {
    /** 模块启动 */
    START,
    /** 搜索村民 */
    SEARCH,
    /** 开始移动 */
    MOVE,
    /** 放置讲台 */
    PLACE,
    /** 刷新交易 */
    REFRESH,
    /** 执行交易 */
    TRADE,
    /** 任务成功 */
    SUCCESS,
    /** 发生错误 */
    ERROR,
    /** 重置 */
    RESET
}
