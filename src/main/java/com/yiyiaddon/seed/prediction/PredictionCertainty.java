package com.yiyiaddon.seed.prediction;

/**
 * 种子挖矿正式模块 · <b>Seed 预测确定性</b>（第一个维度，与 {@code OreObservationState} 完全独立）。
 *
 * <p>228 报告（第七轮最终收口）已经实机证明：相同 Seed / 版本 / worldgen 配置 / 目标区块下，
 * <b>只改变合法的 Chunk 请求顺序</b>，真实最终钻石 BlockPos 就会变。因此
 * 「任意真实服务器已经生成的最终矿物状态」不是 Seed 的唯一函数，正式产品必须能区分
 * 「Seed 能稳定确定的目标」与「受 FEATURES 调度影响的目标」。</p>
 *
 * <p><b>本阶段的保守分类原则（正式化第一阶段口径第二十一、二十二节）</b>：</p>
 * <ul>
 *     <li>{@link #SCHEDULE_SENSITIVE} 需要<b>正面证据</b>：至少存在两种合法候选执行情形，
 *         会改变某 BlockPos 是否为目标矿；</li>
 *     <li>当前算法没有能力证明「所有合法并发历史结果相同」，因此 <b>不存在</b>把某个坐标
 *         标成 {@link #DETERMINISTIC} 的证明程序——本轮<b>不产出</b>该值；</li>
 *     <li>其余一律 {@link #UNRESOLVED}。<b>未知绝不偷偷当确定性</b>：宁可 UNRESOLVED 多一点，
 *         也不能把调度敏感矿误标成确定性矿（后者会直接变成挖矿决策的错误依据）。</li>
 * </ul>
 *
 * <p>注意与观察维度分开：{@code SCHEDULE_SENSITIVE} 与 {@code SUSPICIOUS} 完全不是一回事，
 * 调度敏感<b>绝不能</b>直接当假矿（228 报告第十一节：那块「多出来的钻石」在真实世界上可复现）。</p>
 */
public enum PredictionCertainty {

    /**
     * 确定性：根据当前已经实现的分析，该目标不依赖已知调度歧义。
     *
     * <p><b>本阶段不产出</b>：正式第一阶段只有「两个合法顺序对照」这一种证据来源，
     * 它只能给出正面的调度敏感证据，不足以证明「所有合法并发历史结果相同」。
     * 该值保留给将来真正具备证明程序的版本（例如能对局部冲突图上做出全序无关性证明）。</p>
     */
    DETERMINISTIC("确定性"),

    /**
     * 调度敏感：已证明存在至少两种合法 FEATURES 执行情形，会改变该 BlockPos 是否为目标矿。
     *
     * <p>本阶段的证据形式：同一种子下用两种合法 viewer 执行顺序各跑一次纯离线预测，
     * 该坐标的最终存在性出现变化。这是「存在两种合法情形给出不同结果」的直接观测，
     * 属于正面证据（不是「跑了几种顺序都一样」那种反面推断）。</p>
     */
    SCHEDULE_SENSITIVE("调度敏感"),

    /**
     * 未解析：当前正式分析器无法证明它属于前两种中的哪一种。
     *
     * <p>这是本阶段的默认结论，也是诚实的结论：它既不保证「一定确定」，
     * 也不声称「一定受调度影响」。调用方不得把它当作确定性使用。</p>
     */
    UNRESOLVED("未解析");

    /** 中文显示名。 */
    private final String displayNameCn;

    PredictionCertainty(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
