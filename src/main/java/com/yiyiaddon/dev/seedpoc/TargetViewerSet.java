package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;

/**
 * 种子挖矿 PoC 第六轮 · 「可能把方块写进目标区块的 viewer 集合」推导。
 *
 * <p><b>为什么不是硬编码的「3×3」</b>：目标区块的最终方块由两部分组成 ——
 * ① 它自己那一次 {@code applyBiomeDecoration}；② 邻区块各自那一次里，
 * <b>被允许写到目标区块</b>的那些写入。第 ② 项的边界完全由当前版本原版规则决定：</p>
 *
 * <ul>
 *     <li><b>写半径</b>：{@code ChunkPyramid.GENERATION_PYRAMID} 给 {@code FEATURES} 步骤的
 *         {@code blockStateWriteRadius}（26.1.2 = 1）。{@code WorldGenRegion#ensureCanWrite}
 *         （WorldGenRegion.java:230-259）的判据就是
 *         {@code |center - target| 的两个分量都 ≤ generatingStep.blockStateWriteRadius()}；
 *         因此「能写进目标区块的 viewer」= 与目标区块切比雪夫距离 ≤ 写半径的那些区块；</li>
 *     <li><b>读半径（另一条独立约束）</b>：{@code FEATURES} 步骤的 {@code directDependencies}
 *         同时决定 {@code WorldGenRegion#getChunk} 的合法读取范围与 {@code ensureCanWrite} 之后
 *         {@code setBlock} 能否真的取到那个区块。第六轮把这条约束交给
 *         {@link OfflineChunkPipeline#prepare}（它照 {@code getAccumulatedRadiusOf} 逐阶段铺）。</li>
 * </ul>
 *
 * <p>因此本类的半径<b>每次运行都从原版 {@code ChunkPyramid} 现算</b>，报告里同时打印
 * 该步骤的 {@code blockStateWriteRadius} 与依赖表，作为「3×3 不是拍脑袋」的证据。</p>
 *
 * <p><b>顺序</b>：由远到近返回，<b>目标区块永远排在最后</b>。理由有两条：
 * ① 目标区块最后装饰 ⇒ 读到的就是「邻域已经把跨区块写入做完」之后的最终状态；
 * ② 与 {@link OfflineChunkPipeline#run} 既有的「先邻域、后中心」口径一致，不引入第二套顺序语义。
 * （第五轮已实测：最终钻石集合与装饰先后无关，所以顺序只影响可读性，不影响结论。）</p>
 */
final class TargetViewerSet {

    private TargetViewerSet() {
    }

    /** {@code FEATURES} 步骤允许的方块写入半径（区块）。 */
    static int writeRadius() {
        return featuresStep().blockStateWriteRadius();
    }

    /** 推导依据（进报告，含该步骤的写半径与依赖表原文）。 */
    static String evidence() {
        ChunkStep step = featuresStep();
        return "写半径来源：ChunkPyramid.GENERATION_PYRAMID.getStepTo(FEATURES)"
                + ".blockStateWriteRadius() = " + step.blockStateWriteRadius()
                + "；同一步直接依赖=" + step.directDependencies()
                + "；累积依赖=" + step.accumulatedDependencies();
    }

    /**
     * 目标区块的 viewer 集合（由远到近，目标区块自身在最后）。
     *
     * @param target 目标区块
     */
    static List<ChunkPos> viewers(ChunkPos target) {
        int radius = writeRadius();
        List<ChunkPos> viewers = new ArrayList<>();
        for (int distance = radius; distance >= 0; distance--) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) == distance) {
                        viewers.add(new ChunkPos(target.x() + dx, target.z() + dz));
                    }
                }
            }
        }
        return List.copyOf(viewers);
    }

    /** 某个 viewer 这一次装饰是否被允许写进目标区块（= 原版 {@code ensureCanWrite} 的判据）。 */
    static boolean canWriteInto(ChunkPos viewer, ChunkPos target) {
        int radius = writeRadius();
        return Math.abs(viewer.x() - target.x()) <= radius && Math.abs(viewer.z() - target.z()) <= radius;
    }

    /** 中文摘要（报告用）。 */
    static String describe(ChunkPos target) {
        return "目标区块 (" + target.x() + "," + target.z() + ") 的 viewer 集合共 "
                + viewers(target).size() + " 个（写半径 " + writeRadius() + " ⇒ (2×"
                + writeRadius() + "+1)² ），由远到近、目标最后：" + text(viewers(target));
    }

    /** 区块清单文本。 */
    static String text(List<ChunkPos> positions) {
        StringBuilder builder = new StringBuilder();
        for (ChunkPos pos : positions) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(pos.x()).append(',').append(pos.z()).append(')');
        }
        return builder.toString();
    }

    private static ChunkStep featuresStep() {
        return ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FEATURES);
    }
}
