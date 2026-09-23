package com.yiyiaddon.dev.seedpoc;

import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

/**
 * 生物群系可复现性探针：把「我们自己按种子算出来的生物群系」与「真实区块里存着的生物群系」
 * 按 4x4x4 逐格比对，给出匹配率。
 *
 * <p><b>为什么这一层必须单独量</b>：装饰阶段用到的 feature 索引取决于「这个区块里出现了哪些生物群系」
 * （原版 {@code ChunkGenerator#applyBiomeDecoration:329-336} 收集中心 3x3 的生物群系，
 * 再取这些生物群系的 feature 列表）。生物群系算错 → 参与放置的 feature 集合就错 → 矿位必然错。
 * 而生物群系是纯种子函数（{@code BiomeSource#getNoiseBiome(quartX, quartY, quartZ, sampler)}），
 * 所以这一层**应该**能被离线复现；本探针就是把「应该」变成数字。</p>
 */
public final class BiomeReproProbe {

    /** 比对结果。 */
    public record Result(long matched, long total, long chunkCount) {
        /** 匹配率。 */
        public double rate() {
            return total == 0 ? 1.0 : (double) matched / total;
        }

        public String cn() {
            return "生物群系逐格比对：匹配 " + matched + " / 总计 " + total
                    + " / 匹配率 " + String.format(java.util.Locale.ROOT, "%.4f%%", rate() * 100.0)
                    + " / 区块数 " + chunkCount;
        }
    }

    private BiomeReproProbe() {
    }

    /**
     * 在给定范围内逐区块比对生物群系。
     *
     * @param level  真实世界（作为生物群系真值来源）
     * @param ctx    影子上下文（提供自建生物群系源与采样器）
     * @param center 中心区块
     * @param radius 区块半径
     */
    public static Result measure(ServerLevel level, ShadowWorldGenContext ctx, ChunkPos center, int radius) {
        long matched = 0;
        long total = 0;
        long chunks = 0;
        for (int cx = center.x() - radius; cx <= center.x() + radius; cx++) {
            for (int cz = center.z() - radius; cz <= center.z() + radius; cz++) {
                chunks++;
                LevelChunk chunk = level.getChunk(cx, cz);
                LevelChunkSection[] sections = chunk.getSections();
                int minSectionY = level.getMinSectionY();
                int quartBaseX = QuartPos.fromBlock(cx << 4);
                int quartBaseZ = QuartPos.fromBlock(cz << 4);
                for (int index = 0; index < sections.length; index++) {
                    LevelChunkSection section = sections[index];
                    if (section == null) {
                        continue;
                    }
                    int quartBaseY = (minSectionY + index) * QuartPos.SIZE;
                    for (int lx = 0; lx < QuartPos.SIZE; lx++) {
                        for (int ly = 0; ly < QuartPos.SIZE; ly++) {
                            for (int lz = 0; lz < QuartPos.SIZE; lz++) {
                                Holder<Biome> real = section.getBiomes().get(lx, ly, lz);
                                Holder<Biome> computed = ctx.biomeSource().getNoiseBiome(
                                        quartBaseX + lx, quartBaseY + ly, quartBaseZ + lz,
                                        ctx.randomState().sampler());
                                total++;
                                if (real == computed || real.value() == computed.value()) {
                                    matched++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new Result(matched, total, chunks);
    }
}
