package com.yiyiaddon.feature.mining.fsm;

/**
 * 修补流程的战斗联动钩子（旧项目 {@code MinerFSM.startKillAura / stopKillAura}，{@code :1763-1779}）。
 *
 * <p>旧项目在进入修补时直接 toggle 第三方框架的 KillAura，退出修补时再关掉。本项目零第三方依赖，
 * KillAura 模块在批次 4 才落地，因此本批次先留一个接口 seam：状态机只负责「进入修补时
 * {@link #start()}、离开修补时 {@link #stop()}」，真实现由 {@code AutoMinerModule}（批次 4）
 * 通过 {@link MiningStateMachine#setRepairCombat(RepairCombatHook)} 注入；未注入时使用 {@link #NONE}。</p>
 *
 * <p><b>真实现必须承担旧项目 {@code killAuraWasOnBefore} 的卫语句语义：</b></p>
 * <ul>
 *   <li>{@link #start()}：目标未开启才开启，并把「是我们开的」记在自己身上（旧 {@code :1764-1769}）；</li>
 *   <li>{@link #stop()}：只关我们自己开启的那一个；用户进入模块前就开着的杀戮光环必须保持原样
 *       （旧 {@code :1772-1779}）。</li>
 * </ul>
 */
public interface RepairCombatHook {

    /** 空实现：批次 4 之前（或未注入真实现时）两个调用皆空转 */
    RepairCombatHook NONE = new RepairCombatHook() {
        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }
    };

    /** 开启修补联动战斗（旧 {@code startKillAura}，{@code :1763-1770}） */
    void start();

    /** 关闭修补联动战斗，且只关模块自己开启的那一个（旧 {@code stopKillAura}，{@code :1772-1779}） */
    void stop();
}
