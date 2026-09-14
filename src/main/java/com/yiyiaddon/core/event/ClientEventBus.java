package com.yiyiaddon.core.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 客户端事件总线：事件的唯一订阅与派发点。
 *
 * <p>设计约束：</p>
 * <ol>
 *     <li><b>按所有者去重</b>：同一所有者对同一事件类型只保留一个监听，重复订阅直接覆盖，
 *         从根上排除「模块被重复启用后收到两次事件」；</li>
 *     <li><b>整所有者退订</b>：模块关闭时按所有者一次性退订全部事件，不会漏掉某个类型；</li>
 *     <li><b>异常隔离</b>：单个监听抛异常不影响其余监听，异常上报给失败处理器（由模块运行时
 *         负责报错并关闭出错的模块）；</li>
 *     <li><b>派发不加锁</b>：在锁内只做监听快照，实际回调在锁外执行，避免监听里再订阅导致死锁。</li>
 * </ol>
 *
 * <p>全部事件都在客户端主线程派发：数据包事件由 {@link EventDispatcher} 从网络线程入队、
 * 在主线程出队，模块不会在网络线程上被回调。</p>
 */
public final class ClientEventBus {

    /** 事件类型 → （所有者 → 监听） */
    private static final Map<ClientEventType, Map<String, Consumer<ClientEvent>>> LISTENERS =
            new EnumMap<>(ClientEventType.class);

    private static volatile BiConsumer<String, Throwable> failureHandler = (owner, error) -> {
    };

    private ClientEventBus() {
    }

    static {
        for (ClientEventType type : ClientEventType.values()) {
            LISTENERS.put(type, new LinkedHashMap<>());
        }
    }

    /**
     * 订阅事件；同一所有者对同一类型重复订阅时覆盖旧监听。
     *
     * @param ownerId 所有者标识，模块统一使用 {@code module.<模块ID>}
     */
    public static EventSubscription subscribe(String ownerId, ClientEventType type, Consumer<ClientEvent> handler) {
        if (ownerId == null || ownerId.isBlank() || type == null || handler == null) {
            return EventSubscription.none();
        }
        synchronized (ClientEventBus.class) {
            LISTENERS.get(type).put(ownerId, handler);
        }
        return new EventSubscription(ownerId, type);
    }

    /** 退订单个所有者的一类事件 */
    static void unsubscribe(String ownerId, ClientEventType type) {
        if (ownerId == null || type == null) return;
        synchronized (ClientEventBus.class) {
            LISTENERS.get(type).remove(ownerId);
        }
    }

    /** 退订某个所有者的全部事件 */
    public static void unsubscribeAll(String ownerId) {
        if (ownerId == null || ownerId.isBlank()) return;
        synchronized (ClientEventBus.class) {
            for (Map<String, Consumer<ClientEvent>> byOwner : LISTENERS.values()) {
                byOwner.remove(ownerId);
            }
        }
    }

    public static boolean isSubscribed(String ownerId, ClientEventType type) {
        if (ownerId == null || type == null) return false;
        synchronized (ClientEventBus.class) {
            return LISTENERS.get(type).containsKey(ownerId);
        }
    }

    /** 某类事件的监听数量，用于自检与调试 */
    public static int subscriberCount(ClientEventType type) {
        if (type == null) return 0;
        synchronized (ClientEventBus.class) {
            return LISTENERS.get(type).size();
        }
    }

    /** 当前全部监听数量 */
    public static int totalSubscribers() {
        int total = 0;
        synchronized (ClientEventBus.class) {
            for (Map<String, Consumer<ClientEvent>> byOwner : LISTENERS.values()) {
                total += byOwner.size();
            }
        }
        return total;
    }

    /** 派发事件；监听异常交给失败处理器 */
    public static void publish(ClientEvent event) {
        if (event == null) return;
        List<Map.Entry<String, Consumer<ClientEvent>>> snapshot;
        synchronized (ClientEventBus.class) {
            Map<String, Consumer<ClientEvent>> byOwner = LISTENERS.get(event.type());
            if (byOwner.isEmpty()) return;
            snapshot = new ArrayList<>(byOwner.entrySet());
        }
        for (Map.Entry<String, Consumer<ClientEvent>> entry : snapshot) {
            try {
                entry.getValue().accept(event);
            } catch (Throwable error) {
                report(entry.getKey(), error);
            }
        }
    }

    /** 注册失败处理器：监听抛异常时调用，参数为所有者与异常 */
    public static void setFailureHandler(BiConsumer<String, Throwable> handler) {
        failureHandler = handler == null ? (owner, error) -> {
        } : handler;
    }

    /** 清空全部监听（界面重置与自动化测试用） */
    public static void clear() {
        synchronized (ClientEventBus.class) {
            for (Map<String, Consumer<ClientEvent>> byOwner : LISTENERS.values()) {
                byOwner.clear();
            }
        }
    }

    private static void report(String ownerId, Throwable error) {
        BiConsumer<String, Throwable> handler = failureHandler;
        try {
            handler.accept(ownerId, error);
        } catch (Throwable ignored) {
            // 失败处理器自身出错时不再扩散：事件派发绝不能因为报错处理而中断
        }
    }
}
