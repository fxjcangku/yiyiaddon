package com.yiyiaddon.core.event;

/**
 * 客户端事件载荷。
 *
 * <p>载荷类型按事件类型固定，见 {@link ClientEventType} 的说明。类名类载荷统一用字符串而不是原始
 * 对象，是为了让模块无法在事件之外持有游戏内部对象引用。</p>
 *
 * <p>{@link ClientEventType#SERVER_TEXT} / {@link ClientEventType#SERVER_POSITION} /
 * {@link ClientEventType#SERVER_BLOCK} 是仅有的三个例外：分别携带 {@link ServerTextEvent}、
 * {@link ServerPositionEvent}、{@link ServerBlockEvent}，三者都只包含不可变只读数据，
 * 都不含数据包对象；其余事件类型的这三个字段恒为 {@code null}。</p>
 */
public record ClientEvent(ClientEventType type, String payload, long timestamp, ServerTextEvent serverText,
                          ServerPositionEvent serverPosition, ServerBlockEvent serverBlock, CancelToken token) {

    /** 不含结构化载荷的事件 */
    public ClientEvent(ClientEventType type, String payload, long timestamp) {
        this(type, payload, timestamp, null, null, null, null);
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
     *
     * <p><b>注意：</b>发包的拦截**不走**可取消事件——数据包必须在写出之前决定去留，
     * 而事件在主线程派发，届时包已经发出。发包拦截走 {@code core.net.SendGate} 的同步规则链。</p>
     */
    public static ClientEvent cancellable(ClientEventType type, String payload) {
        return new ClientEvent(type, payload == null ? "" : payload, System.currentTimeMillis(), null, null, null, new CancelToken());
    }

    /** 服务器文本事件；只携带抽取后的只读文本，不携带数据包对象 */
    public static ClientEvent ofText(ServerTextEvent text) {
        return new ClientEvent(ClientEventType.SERVER_TEXT, "", System.currentTimeMillis(), text, null, null, null);
    }

    /** 服务端权威位置事件；只携带换算后的只读坐标，不携带数据包对象 */
    public static ClientEvent ofPosition(ServerPositionEvent position) {
        return new ClientEvent(ClientEventType.SERVER_POSITION, "", System.currentTimeMillis(), null, position, null, null);
    }

    /** 方块权威反馈事件；只携带只读快照，不携带数据包对象 */
    public static ClientEvent ofBlock(ServerBlockEvent block) {
        return new ClientEvent(ClientEventType.SERVER_BLOCK, "", System.currentTimeMillis(), null, null, block, null);
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

    /** 服务端权威位置载荷；非位置事件返回 {@code null} */
    public ServerPositionEvent position() {
        return serverPosition;
    }

    /** 方块权威反馈载荷；非方块反馈事件返回 {@code null} */
    public ServerBlockEvent block() {
        return serverBlock;
    }
}
