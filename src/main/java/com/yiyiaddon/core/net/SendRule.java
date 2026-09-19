package com.yiyiaddon.core.net;

/**
 * 发包规则：模块对发包链路的唯一参与方式。
 *
 * <p><b>实现约定（必须遵守）：</b></p>
 * <ol>
 *     <li><b>在网络线程上被同步调用</b>：包必须在写出之前决定去留，因此本方法不在主线程。
 *         禁止在这里做任何阻塞操作（IO、锁等待、切线程）、禁止派发事件、禁止调用游戏 API。</li>
 *     <li><b>只能读模块自己的状态快照</b>：需要跨线程共享的状态请用 {@code volatile} /
 *         原子类型保存；这些字段由模块在主线程（每刻）维护，本方法只读。</li>
 *     <li><b>不得保存视图</b>：{@link SendView} 仅在调用期内有效，保存引用会读到失效数据。</li>
 *     <li><b>返回 {@code null} 视为放行</b>；抛异常由核心吞掉并放行，绝不让规则异常影响连接。</li>
 * </ol>
 */
@FunctionalInterface
public interface SendRule {

    /** 判定一个即将发出的数据包 */
    SendDecision decide(SendView view);
}
