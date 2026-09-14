package com.yiyiaddon.core.module;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 模块事件桥：把模块声明的事件类型转换成事件总线订阅。
 *
 * <p>订阅的所有者标识统一为 {@code module.<模块ID>}，因此：</p>
 * <ul>
 *     <li>重复启用同一模块时 {@link ClientEventBus#subscribe} 直接覆盖，不会重复监听；</li>
 *     <li>关闭模块时按所有者一次性退订全部类型，不会漏掉某个类型；</li>
 *     <li>事件监听抛异常时，总线按所有者回调失败处理器，运行时据此定位到具体模块。</li>
 * </ul>
 */
final class ModuleEventBridge {

    /** 订阅所有者前缀 */
    static final String OWNER_PREFIX = "module.";

    private ModuleEventBridge() {
    }

    /** 订阅模块声明的事件；已订阅时覆盖旧订阅 */
    static void attach(Module module) {
        String owner = ownerOf(module);
        ClientEventBus.unsubscribeAll(owner);
        Set<ClientEventType> types = module.subscribedEvents();
        if (types == null || types.isEmpty()) return;
        for (ClientEventType type : new LinkedHashSet<>(types)) {
            if (type == null) continue;
            Consumer<com.yiyiaddon.core.event.ClientEvent> handler = event -> module.onEvent(event);
            ClientEventBus.subscribe(owner, type, handler);
        }
    }

    /** 退订模块的全部事件 */
    static void detach(Module module) {
        ClientEventBus.unsubscribeAll(ownerOf(module));
    }

    /** 模块的所有者标识 */
    static String ownerOf(Module module) {
        return OWNER_PREFIX + module.id();
    }

    /** 由所有者标识还原模块 ID；不属于模块返回 {@code null} */
    static String moduleIdOf(String ownerId) {
        if (ownerId == null || !ownerId.startsWith(OWNER_PREFIX)) return null;
        return ownerId.substring(OWNER_PREFIX.length());
    }
}
