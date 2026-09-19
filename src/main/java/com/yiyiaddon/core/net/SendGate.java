package com.yiyiaddon.core.net;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 发包闸门：模块注册发包规则的地方。
 *
 * <p>规则按<b>优先级降序</b>串行判定，**第一个非放行**的处置生效（等价旧框架里各模块同一发包事件的
 * 执行顺序）。同一优先级按注册顺序判定，因此既有模块的行为与「只有注册顺序」时完全一致。</p>
 *
 * <p><b>为什么需要优先级而不是只靠注册顺序：</b>有些模块默认关、运行时才被玩家打开
 * （例如发包秒破），它的规则必然排在启动时就注册好的模块（例如发包防踢）之后；而秒破的接管方式是
 * 「取消原版 START 再自己直发」，一旦防踢先判定并把超限的 START 丢掉，秒破的规则根本不会被执行
 * （用户 2026-09-19：「发包秒破跟发包防踢有没有冲突」）。优先级让它与启用顺序无关。</p>
 *
 * <p>注册 / 注销发生在主线程（模块启用、关闭），判定发生在网络线程：内部用「写时复制」的不可变数组
 * 对外发布，网络线程读到的永远是一个自洽快照，不加锁。</p>
 */
public final class SendGate {

    /** 默认优先级：不显式指定的模块都用它，按注册顺序判定 */
    public static final int DEFAULT_PRIORITY = 0;

    private record Registration(String ownerId, int priority, long sequence, SendRule rule) {
    }

    private record Entry(String ownerId, SendRule rule) {
    }

    private static final Map<String, Registration> RULES = new LinkedHashMap<>();

    /** 注册序号：同优先级时保证「先注册先判定」 */
    private static long sequenceCounter = 0L;

    /** 网络线程读的快照（写时整体替换） */
    private static volatile Entry[] snapshot = new Entry[0];

    private SendGate() {
    }

    /** 注册（或覆盖）某所有者的规则，用 {@link #DEFAULT_PRIORITY}；同一所有者只保留最后一条 */
    public static synchronized void register(String ownerId, SendRule rule) {
        register(ownerId, DEFAULT_PRIORITY, rule);
    }

    /** 注册（或覆盖）某所有者的规则：{@code priority} 越大越先判定，同优先级按注册顺序 */
    public static synchronized void register(String ownerId, int priority, SendRule rule) {
        if (ownerId == null || ownerId.isBlank() || rule == null) return;
        RULES.put(ownerId, new Registration(ownerId, priority, sequenceCounter++, rule));
        rebuild();
    }

    /** 注销某所有者的规则（模块关闭时调用） */
    public static synchronized void unregister(String ownerId) {
        if (ownerId == null || RULES.remove(ownerId) == null) return;
        rebuild();
    }

    /** 清空全部规则（界面重置 / 断线清理用） */
    public static synchronized void clear() {
        if (RULES.isEmpty()) return;
        RULES.clear();
        rebuild();
    }

    public static synchronized boolean isRegistered(String ownerId) {
        return ownerId != null && RULES.containsKey(ownerId);
    }

    public static synchronized int ruleCount() {
        return RULES.size();
    }

    /**
     * 判定一个即将发出的包（网络线程调用）。
     *
     * <p>规则异常一律吞掉并继续后续规则，最终放行——发包链路的健壮性优先于单个模块的意图。</p>
     */
    static SendDecision evaluate(SendView view) {
        Entry[] entries = snapshot;
        for (Entry entry : entries) {
            SendDecision decision;
            try {
                decision = entry.rule().decide(view);
            } catch (Throwable ignored) {
                continue;
            }
            if (decision != null && !decision.isPass()) {
                return decision;
            }
        }
        return SendDecision.pass();
    }

    private static void rebuild() {
        List<Registration> ordered = new ArrayList<>(RULES.values());
        // 优先级降序；同优先级按注册序号升序（= 原来的「注册顺序」口径）
        ordered.sort((a, b) -> a.priority() != b.priority()
            ? Integer.compare(b.priority(), a.priority())
            : Long.compare(a.sequence(), b.sequence()));
        Entry[] next = new Entry[ordered.size()];
        int index = 0;
        for (Registration registration : ordered) {
            next[index++] = new Entry(registration.ownerId(), registration.rule());
        }
        snapshot = next;
    }
}
