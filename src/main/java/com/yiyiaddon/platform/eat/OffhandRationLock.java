package com.yiyiaddon.platform.eat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 副手口粮锁：自动挖矿运行期间，<b>玩家手动的换手</b>（F 键 / {@code SWAP_ITEM_WITH_OFFHAND}）被丢弃。
 *
 * <p>用户 2026-09-19（压力测试）：「我故意一直按 F 切换找 bug，然后金苹果就被我放到快捷栏了」
 * → 「能不能运行期间锁死副手食物不让切换？除非停止模块」。</p>
 *
 * <p>为什么必须拦在发包处：F 键在客户端只是把<b>选定槽</b>与副手互换的一条动作包
 * （{@code Minecraft#handleKeybinds} → {@code ServerboundPlayerActionPacket}），
 * 服务端照单执行 —— 副手食物与快捷栏镐子就此对调，模块事后纠正必然慢一拍、还会和玩家互相插队。
 * 在 {@code Connection#send} 的闸门里直接丢掉这个包，副手连服务端都不会变。</p>
 *
 * <p>为什么不影响模块自己：模块换手走的是容器点击（{@code ContainerInput.SWAP} + button 40，
 * 作用于自带菜单），根本不产生这个动作包；挂机修复点（REPAIR）同理。玩家的手动进食 / 手动换手
 * 只在模块<b>未运行</b>或副手不是口粮时才放行。</p>
 *
 * <p>读写跨线程（网络线程读、主线程写），故用 {@code volatile}；判据本身无锁无阻塞，
 * 满足 {@code SendInterceptor} 对网络线程的要求。</p>
 */
public final class OffhandRationLock {

    private static volatile boolean locked;
    /** 被闸门拦下的玩家换手次数（网络线程累加、主线程取走清零） */
    private static final AtomicInteger BLOCKED = new AtomicInteger();

    private OffhandRationLock() {
    }

    /** 是否处于「副手口粮锁」状态（网络线程会读） */
    public static boolean locked() {
        return locked;
    }

    /** 由自动挖矿每刻开合：运行中且副手确实是口粮时上锁，停模块 / 副手非口粮时解锁 */
    public static void setLocked(boolean value) {
        locked = value;
    }

    /** 闸门拦下一次玩家手动换手时调用（网络线程；只自增，不做任何业务） */
    public static void reportBlocked() {
        BLOCKED.incrementAndGet();
    }

    /** 取走并清零「被拦下」的次数（主线程每刻调用，用来给玩家提示） */
    public static int consumeBlocked() {
        return BLOCKED.getAndSet(0);
    }
}
