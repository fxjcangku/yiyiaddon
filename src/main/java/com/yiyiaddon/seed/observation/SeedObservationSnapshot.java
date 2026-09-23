package com.yiyiaddon.seed.observation;

/**
 * 种子挖矿正式模块 · <b>观察状态快照</b>（正式化第五阶段 233）。
 *
 * <p><b>它是谁</b>：把当前全部候选（= 预测缓存里那些位置）按 {@link OreObservationState} 归好类之后
 * 的一份<b>不可变</b>读数。界面与报告只读它，绝不自己去翻 {@link SeedOreObservationTracker} 的映射
 * —— 更不允许在渲染 / 界面线程上重算状态。</p>
 *
 * <p><b>它不是什么</b>：不含任何「假矿 / 可疑」结论。{@link OreObservationState#SUSPICIOUS} 本阶段
 * 一律不产生（口径第十二节），因此本快照根本没有可疑计数这一项。</p>
 *
 * @param candidates 候选总数（预测缓存里出现的全部预测矿位置）
 * @param unobserved 尚未观察：候选存在，但客户端当前没有合法加载该位置的区块
 * @param confirmed  已确认：区块已加载，位置当前实际就是钻石矿
 * @param missing    当前缺失：区块已加载，位置当前实际不是钻石矿
 */
public record SeedObservationSnapshot(int candidates, int unobserved, int confirmed, int missing) {

    /** 空快照（未启用 / 已清空）。 */
    public static final SeedObservationSnapshot EMPTY = new SeedObservationSnapshot(0, 0, 0, 0);

    /** 已经真正看到过的候选数（已确认 + 当前缺失）。 */
    public int observed() {
        return confirmed + missing;
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "候选 " + candidates + "（未观察 " + unobserved + " / 已确认 " + confirmed
                + " / 当前缺失 " + missing + "）";
    }
}
