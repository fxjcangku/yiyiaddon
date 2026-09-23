package com.yiyiaddon.dev.seedpoc;

import java.util.List;
import net.minecraft.world.level.ChunkPos;

/**
 * 单个测试区块在某一实验口径下的完整结果（一个区块一行，报告里逐行列出，不做「只报总计」）。
 *
 * @param mode              实验口径（中文标签）：只重放钻石 / 整步重放
 * @param pos               测试区块
 * @param diff              逐 BlockPos 差异
 * @param candidateFeatures 该步骤内「可能出现的 feature」个数（原版判定口径）
 * @param replayedFeatures  本次口径下实际重放的 feature 个数
 * @param passes            实际执行了几遍装饰过程（中心 3x3，共 9 遍）
 * @param placedCalls       实际调用了多少次 feature 放置
 * @param structuresPresent 该区块自身是否带结构
 * @param restoreVerified   清空—重放之后是否已把世界原样还原
 * @param note              一句话结论（中文，可为空串）
 * @param details           逐行诊断明细（每遍装饰各自放了多少、差异点分布与样本坐标、周边结构、自一致性等），
 *                          这些是定位「错在哪一层」的直接证据，必须原样进报告
 */
public record ChunkOutcome(String mode, ChunkPos pos, BlockPosDiff diff, int candidateFeatures,
                           int replayedFeatures, int passes, int placedCalls, boolean structuresPresent,
                           boolean restoreVerified, String note, List<String> details) {
}
