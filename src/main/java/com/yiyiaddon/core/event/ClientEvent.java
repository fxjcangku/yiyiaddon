package com.yiyiaddon.core.event;

/**
 * 客户端事件载荷。
 *
 * <p>载荷类型按事件类型固定，见 {@link ClientEventType} 的说明。类名类载荷统一用字符串而不是原始
 * 对象，是为了让模块无法在事件之外持有游戏内部对象引用。</p>
 *
 * <p>{@link ClientEventType#SERVER_TEXT} 是唯一例外：它携带的 {@link ServerTextEvent} 只包含
 * 不可变文本，不含数据包对象；其余事件类型的该字段恒为 {@code null}。</p>
 */
public record ClientEvent(ClientEventType type, String payload, long timestamp, ServerTextEvent serverText,
                          CancelToken token) {

    /** 不含文本载荷的事件 */
    public ClientEvent(ClientEventType type, String payload, long timestamp) {
        this(type, payload, timestamp, null, null);
    }

    /** 无载荷事件 */
    public static ClientEvent of(ClientEventType type) {
        return new ClientEvent(type, "", System.currentTimeMillis());
    }

    /** 带载荷事件；载荷为空时按无载荷处理 */
    public static ClientEvent of(ClientEventType type, String payload) {
        return new ClientEvent(type, payload == null ? "" : payload, System.currentTimeMillis());
    }

    /**
     * 可取消事件。
     *
     * <p>目前只有 {@link ClientEventType#SCREEN_OPEN} 用：模块判定「这个界面不该弹」时调
     * {@link #cancel()}，派发方在发布后检查 {@link #isCancelled()} 并放弃该界面。取消令牌只是一个
     * 布尔标志，仍然不含任何游戏对象，模块拿不到界面实例。</p>
     */
    public static ClientEvent cancellable(ClientEventType type, String payload) {
        return new ClientEvent(type, payload == null ? "" : payload, System.currentTimeMillis(), null, new CancelToken());
    }

    /** 服务器文本事件；只携带抽取后的只读文本，不携带数据包对象 */
    public static ClientEvent ofText(ServerTextEvent text) {
        return new ClientEvent(ClientEventType.SERVER_TEXT, "", System.currentTimeMillis(), text, null);
    }

    /** 请求取消该事件；不可取消的事件类型调用无效 */
    public void cancel() {
        if (token != null) token.cancelled = true;
    }

    /** 该事件是否已被取消；不可取消的事件恒为 {@code false} */
    public boolean isCancelled() {
        return token != null && token.cancelled;
    }

    /** 取消令牌：可取消事件的唯一可变状态（一个布尔标志） */
    public static final class CancelToken {
        private boolean cancelled;
    }

    /** 载荷的简短名（去掉包名前缀），用于播报与调试 */
    public String shortPayload() {
        if (payload == null || payload.isBlank()) return "";
        int dot = payload.lastIndexOf('.');
        return dot < 0 ? payload : payload.substring(dot + 1);
    }

    /** 服务器文本载荷；非文本事件返回 {@code null} */
    public ServerTextEvent text() {
        return serverText;
    }
}
