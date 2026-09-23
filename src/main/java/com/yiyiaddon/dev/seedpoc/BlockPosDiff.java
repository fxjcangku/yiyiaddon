package com.yiyiaddon.dev.seedpoc;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;

/**
 * 逐 BlockPos 集合比对结果（一个区块一份，另有合计）。
 *
 * <p>四个数字就是本次实验的验收口径，缺一不可：预测数、真值数、完全匹配数，
 * 以及漏报（真值有、预测无）与错报（预测有、真值无）。错位不需要单独一列——
 * 只要不是同一个坐标，就必然同时表现为一条漏报 + 一条错报，这正是 BlockPos 级比对相对
 * 「矿脉大概位置」判定的根本区别。</p>
 */
public record BlockPosDiff(int realCount, int predictedCount, int matched, int missed, int extra) {

    /** 按集合算一份差异。 */
    public static BlockPosDiff of(Set<BlockPos> real, Set<BlockPos> predicted) {
        Set<BlockPos> hit = new LinkedHashSet<>(real);
        hit.retainAll(predicted);
        int matched = hit.size();
        return new BlockPosDiff(real.size(), predicted.size(), matched,
                real.size() - matched, predicted.size() - matched);
    }

    /** 合计多个区块的结果。 */
    public static BlockPosDiff sum(Iterable<BlockPosDiff> parts) {
        int real = 0;
        int predicted = 0;
        int matched = 0;
        for (BlockPosDiff part : parts) {
            real += part.realCount();
            predicted += part.predictedCount();
            matched += part.matched();
        }
        return new BlockPosDiff(real, predicted, matched, real - matched, predicted - matched);
    }

    /** 查全率：真值里有多少被复刻出来。真值为 0 时按 1.0 记（无可漏），报告里会同时标注样本量。 */
    public double recall() {
        return realCount == 0 ? 1.0 : (double) matched / realCount;
    }

    /** 查准率：复刻出来的有多少是真的。预测为 0 时按 1.0 记。 */
    public double precision() {
        return predictedCount == 0 ? 1.0 : (double) matched / predictedCount;
    }

    /** 是否逐 BlockPos 完全一致。 */
    public boolean exact() {
        return missed == 0 && extra == 0;
    }

    /** 中文单行摘要。 */
    public String cn() {
        return "真值 " + realCount + " / 预测 " + predictedCount + " / 匹配 " + matched
                + " / 漏报 " + missed + " / 错报 " + extra
                + " / 查全 " + percent(recall()) + " / 查准 " + percent(precision());
    }

    private static String percent(double value) {
        return String.format(java.util.Locale.ROOT, "%.2f%%", value * 100.0);
    }
}
