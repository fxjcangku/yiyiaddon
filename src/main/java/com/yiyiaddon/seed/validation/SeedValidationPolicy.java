package com.yiyiaddon.seed.validation;

import com.yiyiaddon.seed.prediction.PredictionCertainty;
import java.util.List;
import java.util.Locale;

/**
 * 种子挖矿正式模块 · <b>种子验证策略</b>（正式化第六阶段 234；234.1 收紧 independence 口径）。
 *
 * <p><b>它是唯一一处阈值定义</b>（口径第十四节）：界面、服务、报告全部读这里，
 * 不允许任何调用方自己写「3 个就算验证」这类数字。</p>
 *
 * <h2>一、判定输入（全部来自有效证据单元，不来自「候选方块计数」）</h2>
 * <ul>
 *     <li><b>everConfirmedUnits</b>：<b>曾经</b>被确认过的有效证据单元数（锁存，被挖掉不撤销）；</li>
 *     <li><b>everConfirmedChunks</b>：这些单元分布在多少个<b>不同</b>目标区块上；</li>
 *     <li><b>observedUnits</b>：已经被实际观察过的单元数（已确认 + 缺失）；</li>
 *     <li><b>ratio</b> = everConfirmedUnits ÷ observedUnits：当前种子对「已经看到的东西」的解释比例。</li>
 * </ul>
 * <p><b>口径说明（234.1 第二十二节）</b>：分子与分母<b>都是单元维度的计数</b>，
 * 不存在「分子按单元、分母按候选方块」的混合口径。候选方块级别的数字只用于界面展示
 * （{@code confirmedPositions}），<b>不参与</b>任何门槛计算。</p>
 *
 * <h2>二、为什么是这三条而不是「MISSING 就扣分」</h2>
 * <p>口径第十六、十七节：MISSING 不能当作负票 —— 玩家可能已经把钻石挖走了，服务器也可能
 * 改过方块 / 清过矿 / 回过档。因此正式判定<b>只依赖正向证据</b>：</p>
 * <ul>
 *     <li>「曾经确认过」一旦发生就<b>永不撤销</b>，所以挖走钻石不会把已验证打回去；</li>
 *     <li>错误种子的候选位置与真实世界无关，因此它几乎不可能攒出「大量的、分布在不同区块的、
 *         曾经被确认过的单元」——这正是错误种子无法通过的原因；</li>
 *     <li>比例项用来挡「样本太小的巧合」：错误种子要把比例做过半，等于要求它预测出来的
 *         单元<b>一半以上</b>都恰好落在真实钻石上，概率上不可达。</li>
 * </ul>
 *
 * <h2>三、阈值是经验值，不是数学证明（口径第十五、七十五节）</h2>
 * <p>下面几个数字来自 dev-only 标定回归（正确种子 vs 四个错误种子的实测对照）。它们提供的是
 * <b>实测余量</b>，不是「唯一 seed 不可伪造」的数学证明。因此：</p>
 * <ul>
 *     <li>不得把验证结论宣传成「100% 证明服务器真实 Seed」；</li>
 *     <li>不得由「4 个错误种子没通过」外推成「所有错误种子永远不可能通过」。</li>
 * </ul>
 * <p><b>234.1 的追加纪律（口径第十七、二十一节）</b>：阈值先由<b>新的证据单元口径</b>实测得出，
 * <b>禁止</b>为了继续沿用 234 的 8 / 3 / 0.5 反过来去设计分组。</p>
 *
 * <h2>四、CONFLICTING 的硬冲突判据（本阶段刻意不可达）</h2>
 * <p>口径第八、二十四节：只有「不会因为正常玩家修改 / 合法 FEATURES 调度 / 已知模型限制而误触发」
 * 的标准才允许产出 CONFLICTING。本阶段落的唯一判据是：</p>
 * <blockquote>
 *     存在 {@link PredictionCertainty#DETERMINISTIC}（确定性）候选，且它所在的整个证据单元
 *     被观察为<b>全部非该矿物</b>。
 * </blockquote>
 * <p>为什么这条是安全的：228 已实测「合法的 FEATURES 调度顺序会改变同一位置最终是否为钻石」，
 * 因此只有<b>确定性</b>候选才有资格当硬反证（{@link PredictionCertainty#SCHEDULE_SENSITIVE}
 * 永远不能当假矿，<b>也永远不能当冲突证据</b>）。</p>
 * <p>为什么它当前不可达：正式预测器本阶段<b>不产出</b> DETERMINISTIC
 * （{@code deterministicCount} 恒为 0，见 {@link PredictionCertainty#DETERMINISTIC}），
 * 于是该判据永远凑不满 {@link #CONFLICT_MIN_DETERMINISTIC_UNITS}。
 * <b>这是刻意设计</b>：证明不了安全，就宁可永远停在 INCONCLUSIVE（fail-closed）。</p>
 *
 * <p><b>线程模型</b>：纯函数，无状态，可在任意线程调用（实际只在客户端主线程调用）。</p>
 */
public final class SeedValidationPolicy {

    /**
     * 进入 VERIFIED 所需的最少<b>曾经确认</b>的有效证据单元数。
     *
     * <p>取 8 的理由：正确种子（范围 3、49 目标区块）实测确认单元数以数十计，
     * 8 只占其中一小部分；而错误种子的确认单元数实测为个位数甚至 0，8 与实测值之间留有<b>成倍余量</b>。
     * 一次 worldgen 放置 / 一个矿脉最多贡献 1 个单元，因此这条同时挡住「一个矿脉通关」
     * （口径第十二节）。</p>
     */
    public static final int VERIFY_MIN_CONFIRMED_UNITS = 8;

    /**
     * 进入 VERIFIED 所需的最少<b>不同目标区块</b>数。
     *
     * <p>取 3 的理由（口径第十三、四十三、五十节）：单区块哪怕 45/45 全部确认也<b>绝不能</b>验证通过 ——
     * 单区块的巧合不足以支撑「整个种子的世界生成规则与声明一致」。3 是「至少跨三个彼此独立的区块区域」
     * 的最小可用值。</p>
     */
    public static final int VERIFY_MIN_CONFIRMED_CHUNKS = 3;

    /**
     * 进入 VERIFIED 所需的最低解释比例（曾经确认单元 ÷ 已观察单元）。
     *
     * <p>取 0.5：正确种子在新鲜世界里该比例接近 1.0；错误种子该比例实测远低于 0.1。
     * 0.5 意味着「种子必须解释我们已经看到的一半以上」，既挡住小样本巧合，
     * 又给「服务器早就被挖过一部分」留出容错。</p>
     */
    public static final double VERIFY_MIN_CONFIRMED_RATIO = 0.5d;

    /**
     * 判定为 INCONCLUSIVE 所需的最少已观察单元数。
     *
     * <p>低于它一律停在 COLLECTING（还在收集，不下任何结论）。</p>
     */
    public static final int INCONCLUSIVE_MIN_OBSERVED_UNITS = 8;

    /**
     * 判定 CONFLICTING 所需的最少「确定性候选却观察为非矿物」的单元数。
     *
     * <p>当前不可达（正式层不产出 DETERMINISTIC，见类注释第四节）。保留该常量是为了让
     * 「硬冲突判据」在代码里有一个可核对的位置，而不是散落在注释里。</p>
     */
    public static final int CONFLICT_MIN_DETERMINISTIC_UNITS = 2;

    private SeedValidationPolicy() {
    }

    /**
     * 一次判定的输入读数（全部由 {@link SeedValidationService} 从有效证据单元清单现算）。
     *
     * @param everConfirmedUnits 曾经确认过的有效证据单元数
     * @param everConfirmedChunks 这些单元覆盖的不同目标区块数
     * @param observedUnits      已被实际观察过的有效证据单元数
     * @param deterministicMissingUnits 确定性候选却被观察为非矿物的单元数（硬冲突判据用）
     */
    public record Inputs(int everConfirmedUnits, int everConfirmedChunks, int observedUnits,
                         int deterministicMissingUnits) {

        /** 解释比例（无观察样本时按 0 处理，绝不当成 1；分子分母同为单元计数）。 */
        public double ratio() {
            return observedUnits <= 0 ? 0d : (double) everConfirmedUnits / (double) observedUnits;
        }
    }

    /**
     * 一次判定的结果。
     *
     * @param state  结论状态
     * @param reasonCn 中文结论依据（直接可进界面 / 报告，说明「为什么是这个状态」）
     */
    public record Verdict(SeedValidationState state, String reasonCn) {
    }

    /**
     * 按正式策略给出结论（纯函数）。
     *
     * <p>判定顺序（fail-closed）：硬冲突 → 验证阈值 → 证据不足 → 收集中 → 未验证。</p>
     *
     * @param inputs 本次读数
     */
    public static Verdict evaluate(Inputs inputs) {
        if (inputs == null) {
            return new Verdict(SeedValidationState.UNVERIFIED, "尚无验证读数");
        }
        if (inputs.deterministicMissingUnits() >= CONFLICT_MIN_DETERMINISTIC_UNITS) {
            return new Verdict(SeedValidationState.CONFLICTING,
                    "存在 " + inputs.deterministicMissingUnits() + " 个确定性候选单元与当前实际方块不符"
                            + "（阈值 " + CONFLICT_MIN_DETERMINISTIC_UNITS + "）");
        }
        if (inputs.everConfirmedUnits() >= VERIFY_MIN_CONFIRMED_UNITS
                && inputs.everConfirmedChunks() >= VERIFY_MIN_CONFIRMED_CHUNKS
                && inputs.ratio() >= VERIFY_MIN_CONFIRMED_RATIO) {
            return new Verdict(SeedValidationState.VERIFIED,
                    "有效确认单元 " + inputs.everConfirmedUnits() + " 个（阈值 " + VERIFY_MIN_CONFIRMED_UNITS
                            + "），覆盖 " + inputs.everConfirmedChunks() + " 个目标区块（阈值 "
                            + VERIFY_MIN_CONFIRMED_CHUNKS + "），解释比例 "
                            + percentCn(inputs.ratio()) + "（阈值 " + percentCn(VERIFY_MIN_CONFIRMED_RATIO) + "）");
        }
        if (inputs.observedUnits() >= INCONCLUSIVE_MIN_OBSERVED_UNITS) {
            return new Verdict(SeedValidationState.INCONCLUSIVE,
                    "已观察 " + inputs.observedUnits() + " 个单元，但有效确认单元 " + inputs.everConfirmedUnits()
                            + " 个 / 覆盖 " + inputs.everConfirmedChunks() + " 个目标区块 / 解释比例 "
                            + percentCn(inputs.ratio()) + "，未达到验证阈值");
        }
        if (inputs.observedUnits() > 0) {
            return new Verdict(SeedValidationState.COLLECTING,
                    "已收集 " + inputs.observedUnits() + " 个有效证据单元（已确认 " + inputs.everConfirmedUnits()
                            + " 个），尚不足以给出结论");
        }
        return new Verdict(SeedValidationState.UNVERIFIED, "尚未获得任何有效观察样本");
    }

    /**
     * 从有效证据单元清单算出判定输入。
     *
     * <p>口径要求「验证必须基于有效证据单元，而不是候选方块计数」，
     * 因此这里全部按<b>单元</b>统计；目标区块用单元自身的 {@code targetChunk} 去重。</p>
     */
    public static Inputs inputsOf(List<SeedValidationEvidenceGroup> units) {
        if (units == null || units.isEmpty()) {
            return new Inputs(0, 0, 0, 0);
        }
        int confirmedUnits = 0;
        int observedUnits = 0;
        int deterministicMissing = 0;
        java.util.Set<Long> confirmedChunks = new java.util.HashSet<>();
        for (SeedValidationEvidenceGroup unit : units) {
            if (unit.everConfirmed()) {
                confirmedUnits++;
                confirmedChunks.add(unit.targetChunk().pack());
            }
            if (unit.observed()) {
                observedUnits++;
            }
            // 硬冲突判据：确定性（DETERMINISTIC）候选整单元观察为非该矿物。
            // 本阶段确定性候选恒为 0，因此这条分支不可达（刻意 fail-closed）。
            if (unit.certainty() == PredictionCertainty.DETERMINISTIC
                    && unit.observed()
                    && unit.confirmedCount() == 0
                    && unit.missingCount() == unit.size()) {
                deterministicMissing++;
            }
        }
        return new Inputs(confirmedUnits, confirmedChunks.size(), observedUnits, deterministicMissing);
    }

    /**
     * 硬冲突判据当前是否<b>可能</b>被触发。
     *
     * <p>它只回答一件事：当前正式预测模型会不会产出 {@link PredictionCertainty#DETERMINISTIC} 候选。
     * 恒为 false 时，{@link SeedValidationState#CONFLICTING} 在本模型下不可达 ——
     * 这正是 234 的正式结论（口径第二十三节：宁可禁用，也不制造不可靠的假冲突 / 假矿检测）。</p>
     */
    public static boolean strongConflictReachable() {
        return false;
    }

    /** 为什么硬冲突判据当前不可达（报告与界面说明共用一份口径）。 */
    public static String strongConflictDisabledReasonCn() {
        return "硬冲突判据只接受「确定性（DETERMINISTIC）候选与实际方块不符」，"
                + "而本阶段正式预测器不产出确定性候选（恒为 0）：合法 FEATURES 调度顺序本身就会改变"
                + "同一位置最终是否为钻石，因此调度敏感候选永远不能当作冲突证据。证明不了安全，就不产出冲突结论。";
    }

    /** 阈值的一行中文说明（界面 Note 与报告共用一份）。 */
    public static String thresholdsCn() {
        return "验证阈值：有效确认单元 ≥ " + VERIFY_MIN_CONFIRMED_UNITS + " 个、覆盖 ≥ "
                + VERIFY_MIN_CONFIRMED_CHUNKS + " 个目标区块、解释比例 ≥ " + percentCn(VERIFY_MIN_CONFIRMED_RATIO)
                + "（比例 = 曾经确认单元 ÷ 已观察单元，分子分母同为单元口径）";
    }

    /** 验证结论的边界声明（口径第七节的正式文案：不允许宣传成「100% 证明服务器真实 Seed」）。 */
    public static String scopeNoteCn() {
        return "验证基于当前客户端已观察到的原版世界生成样本；"
                + "不代表对服务器所有历史区块 / 自定义世界生成规则作绝对证明。";
    }

    /** 百分比文案（固定两位小数，避免各语言环境差异影响报告可复现性）。 */
    private static String percentCn(double value) {
        return String.format(Locale.ROOT, "%.2f%%", value * 100d);
    }
}
