package com.yiyiaddon.core.event;

/**
 * 客户端事件载荷。
 *
 * <p>载荷类型按事件类型固定，见 {@link ClientEventType} 的说明。统一用类名字符串而不是原始对象，
 * 是为了让模块无法在事件之外持有游戏内部对象引用。</p>
 */
public record ClientEvent(ClientEventType type, String payload, long timestamp) {

    /** 无载荷事件 */
    public static ClientEvent of(ClientEventType type) {
        return new ClientEvent(type, "", System.currentTimeMillis());
    }

    /** 带载荷事件；载荷为空时按无载荷处理 */
    public static ClientEvent of(ClientEventType type, String payload) {
        return new ClientEvent(type, payload == null ? "" : payload, System.currentTimeMillis());
    }

    /** 载荷的简短名（去掉包名前缀），用于播报与调试 */
    public String shortPayload() {
        if (payload == null || payload.isBlank()) return "";
        int dot = payload.lastIndexOf('.');
        return dot < 0 ? payload : payload.substring(dot + 1);
    }
}
