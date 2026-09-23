package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 方块状态调色板：把 {@link BlockState} 压成 {@code short} 编号。
 *
 * <p><b>为什么需要它</b>：本轮实验要整片保存「生成期方块状态」（3x3 区块 × 若干 section），
 * 直接持有 {@code BlockState} 引用数组会让内存爆掉（每个引用 8 字节，一片区域几百万格）。
 * 方块状态的数量在单次实验里只有几百种，压成 short 之后同样的区域只占几 MB。</p>
 *
 * <p><b>为什么用身份比较（{@link IdentityHashMap}）</b>：{@code BlockState} 是原版在方块注册期
 * 一次性构造并被调色板容器直接持有的对象，同一个状态在整个运行期是同一个实例；
 * 身份比较既省掉 {@code equals/hashCode} 的开销，也不会把逻辑相同的状态拆成两份。</p>
 *
 * <p>线程安全：捕获发生在区块生成线程，读取发生在服务端线程，方法全部加锁（竞争极低）。</p>
 */
final class StatePalette {

    /** 状态编号上限（short 非负区）。单次实验用不到这么多，超出说明用法有问题。 */
    private static final int MAX_ENTRIES = Short.MAX_VALUE - 1;

    private final List<BlockState> states = new ArrayList<>();
    private final Map<BlockState, Short> ids = new IdentityHashMap<>();

    /** 取状态编号；新状态追加登记。 */
    synchronized short idOf(BlockState state) {
        Short existing = ids.get(state);
        if (existing != null) {
            return existing;
        }
        if (states.size() >= MAX_ENTRIES) {
            throw new IllegalStateException("方块状态调色板溢出：" + states.size());
        }
        short id = (short) states.size();
        states.add(state);
        ids.put(state, id);
        return id;
    }

    /** 按编号取回状态。 */
    BlockState stateOf(short id) {
        return states.get(id);
    }

    /** 已登记的状态种数（进报告，用于说明快照体积与数据分布）。 */
    synchronized int size() {
        return states.size();
    }
}
