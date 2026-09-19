package com.yiyiaddon.core.net;

/**
 * 直发绕行标记：核心代替模块主动构造并发出的包，不再经过 {@link SendGate} 判定。
 *
 * <p>等价旧框架的 {@code PacketEvent.Send.sendSilently(...)}：Brand 伪装重发、聊天排队重发、
 * 延迟队列到期重发、秒破的 START/STOP 等都必须绕行，否则会被自己刚注册的规则二次取消，
 * 形成死循环或功能自锁。</p>
 *
 * <p>用 {@link ThreadLocal} 计数而不是布尔：直发可能嵌套（例如规则里触发一次同步直发），
 * 计数归零才解除绕行状态。线程维度也保证了「主线程直发」不会影响网络线程的正常判定。</p>
 */
final class PacketSendBypass {

    private static final ThreadLocal<int[]> DEPTH = ThreadLocal.withInitial(() -> new int[1]);

    private PacketSendBypass() {
    }

    /** 当前是否处于绕行状态 */
    static boolean active() {
        return DEPTH.get()[0] > 0;
    }

    /** 在绕行状态下执行一次动作为原子的直发 */
    static void run(Runnable action) {
        int[] depth = DEPTH.get();
        depth[0]++;
        try {
            action.run();
        } finally {
            depth[0]--;
            if (depth[0] <= 0) {
                DEPTH.remove();
            }
        }
    }
}
