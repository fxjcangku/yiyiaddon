package com.yiyiaddon.seed.validation;

import java.util.Locale;

/**
 * 种子挖矿正式模块 · <b>种子验证快照</b>（正式化第六阶段 234；234.1 改为单元口径）。
 *
 * <p><b>它是界面唯一允许读的东西</b>（口径第七十三节）：{@code MiningSeedPage} 不得自己
 * 计算验证逻辑、不得直接翻证据表，只消费这份<b>不可变</b>读数。</p>
 *
 * <p><b>口径（234.1 第二十二节）</b>：{@link #confirmedUnits()} / {@link #observedUnits()} /
 * {@link #ratio()} 三者全部是<b>有效证据单元</b>维度；候选方块级别的
 * {@link #confirmedPositions()} / {@link #missingPositions()} 只用于展示，<b>不参与</b>门槛。</p>
 *
 * <p><b>文案边界</b>（口径第七、三十九、五十三、五十八节）：本快照里没有任何「服务器作弊 / 假矿 /
 * 100% 证明」的字段或文案；{@link #scopeNoteCn()} 与
 * {@link SeedValidationPolicy#scopeNoteCn()} 是唯一允许对外说的结论范围。</p>
 *
 * @param state              验证状态
 * @param reasonCn           结论依据（中文，直接可显示）
 * @param units              有效证据单元总数（含尚未被观察的单元）
 * @param confirmedUnits     <b>曾经</b>确认过的有效证据单元数（被挖掉不撤销）
 * @param confirmedChunks    这些单元覆盖的不同目标区块数
 * @param observedUnits      已被实际观察过的单元数（已确认 + 缺失）
 * @param ratio              解释比例（曾经确认单元 ÷ 已观察单元，同为单元口径）
 * @param sampleChunks       有效样本区块数（至少有一个单元被实际观察过的目标区块数）
 * @param confirmedPositions 当前实际就是该矿物的候选方块数（会随被挖走而下降；仅展示）
 * @param missingPositions   当前实际不是该矿物的候选方块数（仅展示）
 * @param automatedUseAllowed 是否允许被下游自动化消费（口径第十节：只有已验证为 true）
 * @param policyCn           当前阈值的一行说明（界面 Note 与报告同源）
 */
public record SeedValidationSnapshot(SeedValidationState state, String reasonCn, int units, int confirmedUnits,
                                     int confirmedChunks, int observedUnits, double ratio, int sampleChunks,
                                     int confirmedPositions, int missingPositions, boolean automatedUseAllowed,
                                     String policyCn) {

    /** 空快照（未启用 / 已失效 / 还没绑定身份）。 */
    public static final SeedValidationSnapshot EMPTY = new SeedValidationSnapshot(
            SeedValidationState.UNVERIFIED, "尚未开始验证", 0, 0, 0, 0, 0d, 0, 0, 0, false,
            SeedValidationPolicy.thresholdsCn());

    public SeedValidationSnapshot {
        reasonCn = reasonCn == null ? "" : reasonCn;
        policyCn = policyCn == null ? "" : policyCn;
    }

    /** 状态的中文文案（界面行文案）。 */
    public String stateCn() {
        return state.displayNameCn();
    }

    /** 解释比例的中文百分比文案（固定两位小数）。 */
    public String ratioCn() {
        return observedUnits <= 0 ? "—" : String.format(Locale.ROOT, "%.2f%%", ratio * 100d);
    }

    /** 验证结论的边界声明（界面必须显示，禁止省略）。 */
    public String scopeNoteCn() {
        return SeedValidationPolicy.scopeNoteCn();
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "验证「" + state.displayNameCn() + "」：有效证据单元 " + units + "（曾经确认 " + confirmedUnits
                + " 个 / 覆盖 " + confirmedChunks + " 个目标区块 / 已观察 " + observedUnits + " 个 / 解释比例 "
                + ratioCn() + "）；有效样本区块 " + sampleChunks + "；当前已确认候选 " + confirmedPositions
                + " / 缺失 " + missingPositions;
    }
}
