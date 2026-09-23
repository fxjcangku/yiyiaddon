package com.yiyiaddon.feature.mining.target;

/**
 * 自动挖矿 · <b>挖掘目标提供者</b>（正式化第七阶段 235 新增的唯一抽象）。
 *
 * <p><b>它解决什么</b>：本轮要把「已通过验证的钻石种子预测」接进现有自动挖矿，而现有挖矿流程
 * （状态机 / 寻路 / 秒破 / 连锁 / 物流 / 战斗 / 食物 / 背包 / 回家）一行都不该出现
 * {@code if (seedMiningEnabled)} 这种散落分支。于是把「<b>下一颗钻石在哪、怎么让男中音过去</b>」
 * 收敛成本接口一个概念，两种实现：</p>
 *
 * <ul>
 *     <li>{@link BlockTypeMiningTargetProvider} —— 普通模式（旧行为）：把目标<b>矿物类型</b>交给
 *         男中音的 {@code mine}，由它自己在附近扫描并权衡路径成本；</li>
 *     <li>{@link SeedMiningTargetProvider} —— 种子模式（235 新接）：从已验证的种子预测缓存里
 *         挑出<b>精确到方块</b>的下一颗钻石，寻路过去，到位后交给现有秒破 / 连锁。</li>
 * </ul>
 *
 * <p><b>状态机怎么用它</b>（{@code MiningStateMachine} 只有这几处调用，全部是「问提供者」而不是
 * 「判模式」，因此普通模式的行为逐字不变）：</p>
 * <ol>
 *     <li>{@link #issue(boolean)} / {@link #reissue()} —— 起 mine / 自愈重下发；</li>
 *     <li>{@link #tick()} —— 每刻推进（普通模式是空实现）；</li>
 *     <li>{@link #engineActive()} —— 挖掘引擎是否仍活跃（看门狗据此判断「进程退出」）；</li>
 *     <li>{@link #ready()} / {@link #notReadyReasonCn()} —— 现在能不能挖，不能则给中文原因并停机；</li>
 *     <li>{@link #exhausted()} —— 附近是否已经没有可用目标（据此换区域）；</li>
 *     <li>{@link #progressWatchdogEnabled()} —— 「长时间没打穿方块」这一档自愈是否适用；</li>
 *     <li>{@link #reset()} / {@link #resetSession()} —— 离开挖矿态 / 模块启停时的清理。</li>
 * </ol>
 *
 * <p>实现必须满足「普通模式零回归」：{@link BlockTypeMiningTargetProvider} 的每个返回值都要能让
 * 既有判据算出与未引入本接口时<b>完全相同</b>的结果。</p>
 */
public interface MiningTargetProvider {

    /** 模式中文名（启动报告 / 控制台「挖矿模式」行）。 */
    String modeNameCn();

    /** 扫描方式中文名（启动报告 / 控制台「扫描方式」行）。 */
    String scanModeCn();

    /**
     * 现在能不能按本模式挖矿。
     *
     * <p>返回 {@code false} 时状态机会停模块并播报 {@link #notReadyReasonCn()} —— 这是
     * 「fail-closed」的落点：种子模式验证不通过时宁可停，也不许退回按矿物类型扫描。</p>
     */
    boolean ready();

    /** 不能挖的中文原因（{@link #ready()} 为 true 时返回空串）。文案直接进聊天栏，需自足可读。 */
    String notReadyReasonCn();

    /**
     * 下发挖掘目标。
     *
     * @param broadcast 是否播报「已启动挖掘」（连锁收尾静默恢复传 false，与旧 {@code startMining} 同义）
     * @return 是否真的下发了目标
     */
    boolean issue(boolean broadcast);

    /**
     * 自愈式重下发（「挖不动 / 原地抖动卡死」脱困）。
     *
     * <p>普通模式 = 重新下发同一个 mine（旧行为）；种子模式 = 判定当前目标够不到并换下一颗，
     * 因为「重挖同一个坐标」在种子模式下没有任何意义。</p>
     */
    boolean reissue();

    /** 每刻推进（由 {@code MiningStateMachine#tickMining} 在起 mine 阶段之后调用）。 */
    void tick();

    /** 挖掘引擎是否仍然活跃（同类语义见类注释）。 */
    boolean engineActive();

    /** 附近是否已经没有可用目标（种子模式据此换区域；普通模式恒 false，由既有「重启 3 次」兜底）。 */
    boolean exhausted();

    /** 「长时间没打穿方块」这一档自愈是否适用（见类注释）。 */
    boolean progressWatchdogEnabled();

    /** 离开挖矿态时的清理：只撤掉当前目标 / 寻路，<b>保留</b>已消费与暂时不可达记录。 */
    void reset();

    /** 模块启用 / 关闭 / 换世界时的整份复位：当前目标与全部记账一起清掉。 */
    void resetSession();

    /** 控制台一行只读状态（不足一行也要能看懂「现在在干什么」）。 */
    String statusCn();
}
