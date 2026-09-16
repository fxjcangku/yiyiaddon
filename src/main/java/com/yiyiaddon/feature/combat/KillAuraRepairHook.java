package com.yiyiaddon.feature.combat;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.fsm.RepairCombatHook;

/**
 * 挖矿修补流程的杀戮光环联动真实现（旧项目 {@code MinerFSM.startKillAura / stopKillAura}，
 * {@code MinerFSM.java:1763-1779} 逐条对应）。
 *
 * <p>旧项目原文：</p>
 * <pre>
 * private void startKillAura() {
 *     KillAura killAura = Modules.get().get(KillAura.class);
 *     boolean wasActive = killAura != null &amp;&amp; killAura.isActive();
 *     if (killAura != null &amp;&amp; !wasActive) {
 *         killAura.toggle();
 *         killAuraWasOnBefore = true;   // 标记为「我们开的」，退出修补时才能关
 *     }
 * }
 *
 * private void stopKillAura() {
 *     KillAura killAura = Modules.get().get(KillAura.class);
 *     // 只关我们自己开启的 KA；用户进入模块前就开着的 KA 保持原样（避免状态污染）
 *     if (killAura != null &amp;&amp; killAura.isActive() &amp;&amp; killAuraWasOnBefore) {
 *         killAura.toggle();
 *     }
 *     killAuraWasOnBefore = false;
 * }
 * </pre>
 *
 * <p>「是我们开的」这个卫语句的状态按用户裁定放在本 hook 自己内部（状态机侧另有一份
 * {@code killAuraWasOnBefore} 只管「退出修补时是否要调 stop」）。因此用户进入挖矿前自己开着的
 * 杀戮光环，在补修结束时不会被关掉。</p>
 *
 * <p>开关走 {@link ModuleManager#setEnabled(String, boolean)}；关闭是同步回调
 * {@code KillAuraModule#onDisable()}，而本 hook 正是在状态机 tick 内被调用，
 * 因此 {@code onDisable} 必须幂等且不抛异常（该模块已按此实现）。</p>
 */
public final class KillAuraRepairHook implements RepairCombatHook {

    /** 联动目标的模块 ID（{@code KillAuraModule.MODULE_ID}） */
    private static final String TARGET_MODULE_ID = KillAuraModule.MODULE_ID;

    /** 是否由本 hook 开启（旧 {@code killAuraWasOnBefore}，只关自己开的那个） */
    private boolean enabledByUs;

    /** 开启修补联动战斗（旧 {@code startKillAura}：未开启才开，并记住是我们开的） */
    @Override
    public void start() {
        if (ModuleManager.isEnabled(TARGET_MODULE_ID)) return;
        // 返回值即「是否真的开起来了」：自检被拦下 / 模块不可用时保持未开启，退出修补时也就不会去关它
        if (ModuleManager.setEnabled(TARGET_MODULE_ID, true)) {
            enabledByUs = true;
        }
    }

    /** 关闭修补联动战斗（旧 {@code stopKillAura}：只关我们自己开的那个） */
    @Override
    public void stop() {
        if (enabledByUs && ModuleManager.isEnabled(TARGET_MODULE_ID)) {
            ModuleManager.setEnabled(TARGET_MODULE_ID, false);
        }
        enabledByUs = false;
    }
}
