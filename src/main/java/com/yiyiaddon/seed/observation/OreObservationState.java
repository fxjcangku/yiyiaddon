package com.yiyiaddon.seed.observation;

/**
 * 种子挖矿正式模块 · <b>服务器观察状态</b>（第二个维度，与 {@code PredictionCertainty} 完全独立）。
 *
 * <p>它描述的是「服务器当前把这个位置的真实状态给了客户端没有、给的是什么」，与
 * 「Seed 预测有多确定」<b>不是一回事</b>（正式化第一阶段口径第八、三十节）：</p>
 *
 * <table border="1">
 *     <tr><th>值</th><th>含义</th></tr>
 *     <tr><td>{@link #UNOBSERVED}</td>
 *         <td>服务器还没把这个位置的真实状态提供给客户端（区块未加载 / 未同步）。</td></tr>
 *     <tr><td>{@link #CONFIRMED}</td>
 *         <td>真实方块状态与该矿物目标相符。</td></tr>
 *     <tr><td>{@link #MISSING}</td>
 *         <td>Seed 候选存在，但服务器当前位置不是该矿物。</td></tr>
 *     <tr><td>{@link #SUSPICIOUS}</td>
 *         <td>服务器显示矿物，但它无法被当前已知的合法 Seed 生成候选解释。</td></tr>
 * </table>
 *
 * <p><b>本阶段只建模型，不接服务器</b>（口径第二十九、三十节）：不写 Chunk 包钩子、不做方块状态校验、
 * 不做假矿检测，{@link #SUSPICIOUS} <b>不会自动产生</b>——因为 Seed 有效性验证、调度敏感候选覆盖、
 * 其它钻石来源、版本/worldgen 一致性都还没有正式完成，现在自动判定等于把原版自己的调度歧义
 * 误报成作弊证据（228 报告已实测该风险：那块「多出来的钻石」是可复现的合法产物）。</p>
 */
public enum OreObservationState {

    /** 服务器尚未把该位置的真实状态提供给客户端。 */
    UNOBSERVED("未观察"),

    /** 服务器真实方块状态与该矿物相符。 */
    CONFIRMED("已确认"),

    /** Seed 候选存在，但服务器当前实际不是该矿物。 */
    MISSING("缺失"),

    /** 服务器显示矿物，但无法被当前已知合法 Seed 生成候选解释（本阶段不由代码产出）。 */
    SUSPICIOUS("可疑");

    /** 中文显示名。 */
    private final String displayNameCn;

    OreObservationState(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
