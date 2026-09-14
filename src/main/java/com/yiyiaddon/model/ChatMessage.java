package com.yiyiaddon.model;

/**
 * 从后端拉取到的消息条目（管理员广播 / 定向消息 / 玩家回复）。
 *
 * @param id        消息 ID
 * @param message   正文
 * @param sender    发送者名
 * @param fromAdmin 是否由管理员发出
 * @param premium   发送者是否正版（仅玩家消息有意义）
 * @param createdAt 毫秒时间戳
 */
public record ChatMessage(long id, String message, String sender, boolean fromAdmin, boolean premium,
                          long createdAt) {
}
