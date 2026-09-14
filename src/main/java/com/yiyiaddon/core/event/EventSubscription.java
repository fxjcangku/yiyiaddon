package com.yiyiaddon.core.event;

/**
 * 事件订阅句柄：关闭即退订。
 *
 * <p>由 {@link ClientEventBus#subscribe} 返回，也可通过 {@link #none()} 表示「未订阅」，
 * 调用方无需判空。</p>
 */
public final class EventSubscription implements AutoCloseable {

    private static final EventSubscription NONE = new EventSubscription("", null);

    private final String ownerId;
    private final ClientEventType type;
    private boolean closed;

    EventSubscription(String ownerId, ClientEventType type) {
        this.ownerId = ownerId;
        this.type = type;
    }

    /** 空订阅：close 无副作用 */
    public static EventSubscription none() {
        return NONE;
    }

    public String ownerId() {
        return ownerId;
    }

    public ClientEventType type() {
        return type;
    }

    public boolean isActive() {
        return !closed && type != null;
    }

    @Override
    public void close() {
        if (closed || type == null) return;
        closed = true;
        ClientEventBus.unsubscribe(ownerId, type);
    }
}
