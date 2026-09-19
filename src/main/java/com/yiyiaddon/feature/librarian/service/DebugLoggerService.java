package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.fsm.LibrarianState;
import com.yiyiaddon.feature.librarian.model.LibrarianContext;

/**
 * 自动图书管理员 · 日志契约。
 *
 * <p>四个出口：状态变化、普通通知、调试明细、错误。实现由模块层提供
 * （负责前缀、配色、标点清理与节流），编排器只按语义调用，不拼字符串。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/DebugLoggerService}（19 行），逐字照搬。</p>
 */
public interface DebugLoggerService {
    /** 状态机状态变化播报 */
    void state(LibrarianState state, LibrarianContext context, MovementStatus movementStatus);

    /** 简单通知（找到附魔、完成目标等），聊天反馈开启时输出 */
    void info(String message);

    /** 详细调试数据（坐标、扫描结果等），调试模式开启时输出 */
    void debug(String message);

    /** 错误信息输出 */
    void error(String message);
}
