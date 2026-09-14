package com.yiyiaddon.model;

/**
 * {@code /api/register} 的返回结果。
 *
 * @param newUser    本次是否为首次注册
 * @param rank       使用排名（按首次出现时间升序）
 * @param totalUsers 后端累计用户数
 * @param premium    后端判定结果：是否为正版账户
 */
public record RegisterOutcome(boolean newUser, int rank, int totalUsers, boolean premium) {
}
