package com.yiyiaddon.platform.eat;

/**
 * 自动进食期间的「本地用食态压制」开关。
 *
 * <p>为什么需要它（用户 2026-09-19：「吃东西好像修好了，但是还是有动画」）：
 * 客户端把「我在用食」同步给服务端之后，服务端也会把自己的用食标志（{@code DATA_LIVING_ENTITY_FLAGS} 第 0 位）
 * 同步回来，{@code LocalPlayer#onSyncedDataUpdated} 收到就调 {@code startUsingItem} ——
 * 于是本地进入用食态：<b>吃东西动画（手臂摆动 / 粒子 / 移速减慢）就是这么来的</b>，
 * 而且一旦本地「在用食」，原版按键循环会趁机发 RELEASE 包把这一口取消掉（这也是「吃不上」的隐藏原因）。</p>
 *
 * <p>我们的进食本来就不要本地预测（吃没吃完只看服务端下发的那格数量变化），所以自动进食期间
 * 直接把 {@code LocalPlayer#startUsingItem} 压成空操作：动画没了、RELEASE 也没了，
 * 服务端那边照常按 {@code Consumable} 的时长结算。玩家的手动进食不受影响（开关只在自动进食期间打开）。</p>
 */
public final class SilentEat {

    private static boolean suppressed;

    private SilentEat() {
    }

    /** 自动进食期间是否为「压制本地用食态」状态 */
    public static boolean suppressed() {
        return suppressed;
    }

    /** 由进食流程开合：起手吃一件时打开，这一件结束（吃掉 / 中断 / 超时）时关掉 */
    public static void setSuppressed(boolean value) {
        suppressed = value;
    }
}
