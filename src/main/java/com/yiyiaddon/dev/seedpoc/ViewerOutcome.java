package com.yiyiaddon.dev.seedpoc;

import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 单个 viewer 在「一次真实装饰」上的完整对照结果（第三轮主数据）。
 *
 * <p><b>为什么单独一个类</b>：第三轮的验收单位不是「测试区块」，而是
 * 「一个 viewer 的一次真实装饰」。同一个测试区块在第二阶段会被九个不同 viewer 各写一遍，
 * 这三条信息（viewer / 落点区块 / 装饰批号）必须一路带到底，才能保证
 * 「pre 与 post 同 viewer 同批次」这条约束在报告里可复核。</p>
 *
 * @param mode                口径标签（第一阶段单次装饰 / 第二阶段跨区块）
 * @param viewer              本次装饰的 viewer 区块（重放中心）
 * @param target              统计落点区块（第一阶段 = viewer 自身；第二阶段 = 目标区块）
 * @param prePass             pre-diamond 快照所属装饰批号
 * @param postPass            post-diamond 所属装饰批号（两者不等即禁止配对）
 * @param paired              是否满足「同 viewer + 同批次」的配对前提
 * @param oraclePreCount      原版 pre-diamond 里的钻石数（中心 ±1）
 * @param oraclePostCount     原版 post-diamond 里的钻石数（中心 ±1）
 * @param snapshotPreCount    重放安装快照之后的钻石数（用于证明安装确实生效且等于 pre-diamond）
 * @param snapshotInstallMatch 安装后钻石集合是否与 pre-diamond 完全一致
 * @param vanillaFootprint    原版本次新增钻石（中心 ±1）
 * @param replayFootprint     重放本次新增钻石（中心 ±1）
 * @param replayUnionCount    重放逐条新增的并集大小（应与 replayFootprint 相同，用于自证）
 * @param footprintDiff       中心 ±1 口径的逐 BlockPos 差异（第一阶段主判据）
 * @param targetDiff          落点区块口径的逐 BlockPos 差异（第二阶段主判据）
 * @param restoreVerified     安装—重放—还原之后，世界是否逐格还原
 * @param divergenceLayer     第一处分叉定位到的层（未达 100% 时才有意义）
 * @param note                一句话说明（中文）
 * @param details             逐行明细（逐 feature 对齐、第一处分叉、样本坐标等）
 */
public record ViewerOutcome(String mode,
                            ChunkPos viewer,
                            ChunkPos target,
                            int prePass,
                            int postPass,
                            boolean paired,
                            int oraclePreCount,
                            int oraclePostCount,
                            int snapshotPreCount,
                            boolean snapshotInstallMatch,
                            Set<BlockPos> vanillaFootprint,
                            Set<BlockPos> replayFootprint,
                            int replayUnionCount,
                            BlockPosDiff footprintDiff,
                            BlockPosDiff targetDiff,
                            boolean restoreVerified,
                            String divergenceLayer,
                            String note,
                            List<String> details) {

    /** 第一阶段是否达到「逐 BlockPos 完全一致」。 */
    public boolean exact() {
        return paired && footprintDiff.exact();
    }

    /** 报告用一行头部。 */
    public String cn() {
        return "viewer (" + viewer.x() + "," + viewer.z() + ") → 落点 (" + target.x() + "," + target.z() + ")"
                + "：装饰批号 pre=" + prePass + " / post=" + postPass
                + "（" + (paired ? "同批可配对" : "不可配对") + "）"
                + "；原版本次新增 " + vanillaFootprint.size() + "（3x3）/ " + targetDiff.realCount()
                + "（落点区块）；重放本次新增 " + replayFootprint.size() + "（3x3）/ "
                + targetDiff.predictedCount() + "（落点区块）"
                + "；3x3 逐 BlockPos：" + (footprintDiff.exact() ? "完全一致" : "不一致")
                + "；落点区块逐 BlockPos：" + (targetDiff.exact() ? "完全一致" : "不一致");
    }
}
