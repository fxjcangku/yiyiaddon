package com.yiyiaddon.seed.validation;

/**
 * 种子挖矿正式模块 · <b>种子验证状态</b>（正式化第六阶段 234）。
 *
 * <p><b>它回答什么</b>：「用户填写的这个种子，是否已经拿到足够多的真实世界正向证据支持。」
 * 它<b>不是</b>数学意义上的唯一种子破解证明，也<b>不是</b>「服务器所有区块都是原版」的证明
 * —— 它只声明「当前已经观察到的原版世界生成样本，与这个种子高度一致」。</p>
 *
 * <p><b>为什么不复用 {@code OreObservationState}</b>（口径第八节）：那个枚举描述的是
 * <b>单个方块位置</b>当前实际是什么；本枚举描述的是<b>整颗种子</b>在当前会话里的验证结论。
 * 两者粒度、生命周期、失效条件都不同，合并只会让「某个位置缺失」被误读成「种子错」。</p>
 *
 * <p><b>失败关闭（fail-closed）</b>：任何条件不足的情况一律停在
 * {@link #UNVERIFIED} / {@link #COLLECTING} / {@link #INCONCLUSIVE}，
 * <b>绝不</b>靠「看起来差不多」升到 {@link #VERIFIED}。</p>
 */
public enum SeedValidationState {

    /**
     * 未验证：种子合法，但还没有拿到任何有效观察样本。
     *
     * <p>典型场景：刚进服务器、刚填好种子、预测还没落地、或者观察层尚未绑定。</p>
     */
    UNVERIFIED("未验证"),

    /**
     * 收集中：已经开始拿到有效观察样本，但数量不足以给出任何结论。
     *
     * <p>它<b>不是</b>负面结论 —— 界面必须把它显示成「还在收集」，不能显示成「不可信」。</p>
     */
    COLLECTING("收集中"),

    /**
     * 已验证：满足正式多样本正向验证策略（多个有效确认单元 + 多个不同目标区块）。
     *
     * <p><b>它一旦成立就锁定</b>（口径第十八、四十六节）：玩家之后把某些钻石挖掉只会让
     * 观察状态从「已确认」变成「缺失」，<b>不会</b>撤销已经发生过的正向证据 ——
     * 因为「这里曾经真的是钻石」本身就是对种子正确的证明。</p>
     */
    VERIFIED("已验证"),

    /**
     * 证据不足：已经观察到不少数据，但既不足以验证、也不足以安全否定。
     *
     * <p>例如：大量预测位置被玩家挖掉、区块是旧世界生成的、样本太少、
     * 或者可用有效证据单元不足。这是错误种子在本阶段的<b>正常归宿</b>之一
     * （口径第十六、四十二节：缺 MISSING 不能反过来证明种子错）。</p>
     */
    INCONCLUSIVE("证据不足"),

    /**
     * 与当前模型冲突：存在明显违背当前世界生成模型的证据。
     *
     * <p><b>严格性要求</b>（口径第八、二十二、二十四节）：只有「不会因为正常玩家修改、
     * 合法 FEATURES 调度、已知模型限制而误触发」的标准才允许产出本状态；
     * 证明不了安全就宁可停在 {@link #INCONCLUSIVE}。</p>
     *
     * <p>本阶段落地的唯一硬冲突判据是「<b>确定性（DETERMINISTIC）候选被观察为非矿物</b>」——
     * 而当前正式预测器<b>不产出</b>任何 DETERMINISTIC 候选（恒为 0），
     * 因此本状态在当前模型下<b>不可达</b>（详见 {@link SeedValidationPolicy}）。
     * 这是刻意设计：宁可永远不产出，也不制造一个会被合法调度误触发的假冲突。</p>
     */
    CONFLICTING("与当前模型冲突");

    /** 玩家可见中文文案（界面与报告共用一份，禁止在 UI 里另写一套）。 */
    private final String displayNameCn;

    SeedValidationState(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }

    /** 是否允许被下游自动化（未来 AutoMiner）消费（口径第十节：只有已验证才允许）。 */
    public boolean allowsAutomatedUse() {
        return this == VERIFIED;
    }
}
