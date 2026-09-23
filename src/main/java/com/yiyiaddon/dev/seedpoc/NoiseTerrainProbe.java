package com.yiyiaddon.dev.seedpoc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blending.Blender;

/**
 * 噪声地形离线探针：不借助任何世界对象，只用「注册表 + 种子」把一块噪声地形算出来，
 * 再与真实区块逐方块比一比，量出「地形层离线复现」目前走到了哪一步。
 *
 * <p><b>它要回答的问题</b>：矿脉放置读的是生成期方块状态与生成期高度图，所以做完整离线预测
 * 迟早要能自己生成地形。26.1.2 里地形链路是
 * {@code ChunkGenerator#fillFromNoise(:633 抽象)} → 噪声填充，
 * 而 {@code buildSurface(:424)} 与 {@code applyCarvers(:126)} 的形参是<b>具体类
 * {@code WorldGenRegion}</b>（不是接口），{@code WorldGenRegion} 的构造又要求
 * {@code ServerLevel} + {@code StaticCache2D<GenerationChunkHolder>} + {@code ChunkStep}
 * （WorldGenRegion.java:81-91）。本探针就是把「噪声层能测到哪、地表与雕刻层卡在哪」
 * 分成两件事量清楚，而不是笼统说一句「客户端做不到」。</p>
 *
 * <p><b>它不声称等价</b>：真实区块还叠了地表、雕刻、地物三层，噪声输出必然与真实方块状态不同，
 * 所以这里只登记差异量级，绝不作为「地形已复现」的结论。</p>
 */
public final class NoiseTerrainProbe {

    /** 比对高度上限（含），与矿位扫描范围保持一致。 */
    private static final int MAX_SCAN_Y = 32;

    /** 探针结果。 */
    public record Result(boolean ok, String note, long comparedBlocks, long airShapeMismatch,
                         long blockMismatch, long ourNonAir, long realNonAir) {
        public String cn() {
            if (!ok) {
                return "噪声地形探针未完成：" + note;
            }
            return "噪声地形逐方块比对：比较 " + comparedBlocks + " 格 / 空气差异 " + airShapeMismatch
                    + " / 方块差异 " + blockMismatch
                    + " / 我方非空气 " + ourNonAir + " / 真实非空气 " + realNonAir;
        }
    }

    private NoiseTerrainProbe() {
    }

    /**
     * 对指定区块跑一次「纯噪声地形」离线生成并比对。
     *
     * @param level  真实世界（提供高度范围与结构管理器）
     * @param ctx    影子上下文（提供自建生成器与种子随机状态）
     * @param center 目标区块
     */
    public static Result measure(ServerLevel level, ShadowWorldGenContext ctx, ChunkPos center) {
        try {
            PalettedContainerFactory factory = PalettedContainerFactory.create(ctx.registries());
            ProtoChunk proto = new ProtoChunk(center, UpgradeData.EMPTY, level, factory, null);
            // Blender.empty()：不做旧区块过渡混合（实验中区块都是新生成的）
            // StructureManager：fillFromNoise 内部经 createNoiseChunk → Beardifier.forStructuresInChunk
            //   需要它（NoiseBasedChunkGenerator.java:98-102），本阶段借用世界的实例，报告里如实登记
            ctx.generator()
                    .fillFromNoise(Blender.empty(), ctx.randomState(), level.structureManager(), proto)
                    .join();

            LevelChunk real = level.getChunk(center.x(), center.z());
            long compared = 0;
            long airMismatch = 0;
            long blockMismatch = 0;
            long ourNonAir = 0;
            long realNonAir = 0;
            LevelChunkSection[] sections = proto.getSections();
            int minSectionY = level.getMinSectionY();
            for (int index = 0; index < sections.length; index++) {
                LevelChunkSection ourSection = sections[index];
                LevelChunkSection realSection = real.getSection(index);
                if (ourSection == null || realSection == null) {
                    continue;
                }
                int baseY = (minSectionY + index) * 16;
                if (baseY > MAX_SCAN_Y) {
                    continue;
                }
                for (int lx = 0; lx < 16; lx++) {
                    for (int ly = 0; ly < 16; ly++) {
                        for (int lz = 0; lz < 16; lz++) {
                            BlockState ours = ourSection.getBlockState(lx, ly, lz);
                            BlockState theirs = realSection.getBlockState(lx, ly, lz);
                            compared++;
                            boolean ourAir = ours.isAir();
                            boolean realAir = theirs.isAir();
                            if (ourAir) {
                                // 都不算非空气
                            } else {
                                ourNonAir++;
                            }
                            if (realAir) {
                                // 同上
                            } else {
                                realNonAir++;
                            }
                            if (ourAir != realAir) {
                                airMismatch++;
                            } else if (ours != theirs) {
                                blockMismatch++;
                            }
                        }
                    }
                }
            }
            return new Result(true, "ok", compared, airMismatch, blockMismatch, ourNonAir, realNonAir);
        } catch (Throwable error) {
            return new Result(false,
                    error.getClass().getSimpleName() + (error.getMessage() == null ? "" : "：" + error.getMessage()),
                    0, 0, 0, 0, 0);
        }
    }

    /** 供报告展示的坐标工具：区块最小方块坐标。 */
    public static BlockPos minBlock(ChunkPos pos) {
        return new BlockPos(pos.getMinBlockX(), 0, pos.getMinBlockZ());
    }
}
