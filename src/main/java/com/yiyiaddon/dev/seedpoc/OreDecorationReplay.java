package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 装饰步骤等价复刻：把原版 {@code ChunkGenerator#applyBiomeDecoration} 里
 * 「取生物群系 → 算 decoration seed → 逐个 feature 重设 feature seed → 放置」这一段
 * 按当前版本源码逐行重写一遍。
 *
 * <p><b>逐行依据</b>（26.1.2 本线 {@code net/minecraft/world/level/chunk/ChunkGenerator.java}）：</p>
 * <ul>
 *     <li>{@code :319-322} 取中心区块、{@code SectionPos.of(chunkPos, level.getMinSectionY())} 与其 origin；</li>
 *     <li>{@code :326-328} {@code WorldgenRandom(new XoroshiroRandomSource(generateUniqueSeed()))}
 *         + {@code setDecorationSeed(level.getSeed(), origin.getX(), origin.getZ())}；</li>
 *     <li>{@code :329-337} 中心 3x3 区块里出现过的生物群系（取交集于生物群系源的可能集合）；</li>
 *     <li>{@code :365-382} 该步骤内「可能出现的 feature」→ 全局索引集合 → 升序；</li>
 *     <li>{@code :384-397} 逐个 {@code setFeatureSeed(decorationSeed, globalIndex, stepIndex)}
 *         后调用 {@code PlacedFeature#placeWithBiomeCheck}。</li>
 * </ul>
 *
 * <p><b>必须能指定「重放哪些步骤」</b>：目标矿物不一定只由 {@code underground_ores} 放。
 * 例如钻石矿除了四条 {@code ore_diamond*}，还会由<b>化石</b>放置——
 * {@code data/worldgen/features/CaveFeatures.java:153-155} 注册的 {@code fossil_diamonds}
 * （{@code ProcessorLists.FOSSIL_DIAMONDS} 会把部分骨块替换成钻石矿），
 * 而它挂在 {@code GenerationStep.Decoration.UNDERGROUND_STRUCTURES}
 * （{@code data/worldgen/BiomeDefaultFeatures.java:375-376}）。
 * 只重放矿步骤就会把这些钻石判成漏报——这正是第一轮实验暴露出来的问题。</p>
 *
 * <p><b>不放结构起点</b>：{@code applyBiomeDecoration} 在每步前半段还有结构放置分支
 * （{@code :346-363}，需要 {@code StructureManager} 与结构集），本阶段不引入；
 * 但「化石」不是结构起点而是地物（feature），所以它属于本类能覆盖的范围。</p>
 */
public final class OreDecorationReplay {

    /** 一次复刻的产出与诊断数据。 */
    public record Outcome(int candidateFeatureCount, int placedFeatureCount, int placedCalls,
                          List<String> candidateIds) {
    }

    /**
     * 单条钻石 placed_feature 在本次重放里的贡献。
     *
     * @param featurePath 注册表路径
     * @param stepIndex   装饰步骤序号
     * @param globalIndex 该 feature 在该步骤里的全局索引（{@code setFeatureSeed} 的第二个参数）
     * @param before      执行前的钻石集合（中心区块 ±1）
     * @param after       执行后的钻石集合（中心区块 ±1）
     * @param added       {@code after - before}
     */
    public record FeatureReplay(String featurePath, int stepIndex, int globalIndex,
                                Set<BlockPos> before, Set<BlockPos> after, Set<BlockPos> added) {
    }

    /**
     * 第三轮单次装饰重放的产出。
     *
     * @param candidateTotal  该步骤内「可能出现的 feature」总数（原版判定口径，用于对照索引表）
     * @param features        钻石四条的执行记录（按原版全局索引升序 = 原版执行顺序）
     * @param union           四条新增的并集
     */
    public record DiamondReplayOutcome(int candidateTotal, List<FeatureReplay> features, Set<BlockPos> union) {
    }

    private OreDecorationReplay() {
    }

    /**
     * <b>第三轮专用</b>：只重放「这个 viewer 自己这一遍」的钻石四条，并逐条记录新增集合。
     *
     * <p>与 {@link #replaySteps} 的唯一区别是「只放置钻石四条 + 每条前后各取一次钻石集合」，
     * 随机数语义完全一致：原版 {@code applyBiomeDecoration:384-397} 对每条 feature 都是
     * 先 {@code random.setFeatureSeed(decorationSeed, 全局索引, 步骤序号)} 再放置，
     * 也就是每次放置都重置随机流 ⇒ 只放钻石四条与「整步全放」在钻石这一侧得到的随机数完全相同，
     * 但世界状态只由钻石自身改动（这一点会如实进报告，供判断「夹在中间的其它矿」是否有影响）。</p>
     *
     * @param diamondFeatures 钻石四条（身份比对）
     * @param diamondScan     取「中心区块 ±1 内当前钻石集合」的扫描器
     */
    public static DiamondReplayOutcome replayDiamonds(ShadowWorldGenContext context, WorldGenLevel level,
                                                      ChunkPos center, int[] stepIndices,
                                                      Set<PlacedFeature> diamondFeatures,
                                                      java.util.function.Supplier<Set<BlockPos>> diamondScan,
                                                      SeedPocTrace trace) {
        List<FeatureSorter.StepFeatureData> featureList = context.featuresPerStep();

        SectionPos sectionPos = SectionPos.of(center, level.getMinSectionY());
        BlockPos origin = sectionPos.origin();

        WorldgenRandom random = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
        long decorationSeed = random.setDecorationSeed(context.seed(), origin.getX(), origin.getZ());

        Set<Holder<Biome>> possibleBiomes = collectPossibleBiomes(level, center);
        possibleBiomes.retainAll(context.biomeSource().possibleBiomes());

        int candidateTotal = 0;
        List<FeatureReplay> features = new ArrayList<>();
        Set<BlockPos> union = new LinkedHashSet<>();

        for (int stepIndex : stepIndices) {
            if (stepIndex < 0 || stepIndex >= featureList.size()) {
                continue;
            }
            FeatureSorter.StepFeatureData stepData = featureList.get(stepIndex);
            Set<Integer> indices = new TreeSet<>();
            for (Holder<Biome> biome : possibleBiomes) {
                List<HolderSet<PlacedFeature>> perStep = biome.value().getGenerationSettings().features();
                if (stepIndex >= perStep.size()) {
                    continue;
                }
                for (Holder<PlacedFeature> holder : perStep.get(stepIndex)) {
                    indices.add(stepData.indexMapping().applyAsInt(holder.value()));
                }
            }
            int[] sorted = indices.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(sorted);
            candidateTotal += sorted.length;

            for (int globalIndex : sorted) {
                PlacedFeature feature = stepData.features().get(globalIndex);
                if (!diamondFeatures.contains(feature)) {
                    // 非钻石的矿不重放：原版每条 feature 都会重置 featureSeed，
                    // 因此跳过它们不会改变钻石这一侧的随机流（依据见方法注释）。
                    continue;
                }
                String path = context.registries()
                        .lookupOrThrow(net.minecraft.core.registries.Registries.PLACED_FEATURE)
                        .getResourceKey(feature)
                        .map(key -> key.identifier().getPath())
                        .orElse("?");
                Set<BlockPos> before = diamondScan.get();
                random.setFeatureSeed(decorationSeed, globalIndex, stepIndex);
                if (trace == null) {
                    feature.placeWithBiomeCheck(level, context.generator(), random, origin);
                } else {
                    trace.begin(stepIndex + ":" + globalIndex + ":" + path);
                    try {
                        feature.placeWithBiomeCheck(level, context.generator(), random, origin);
                    } finally {
                        trace.end();
                    }
                }
                Set<BlockPos> after = diamondScan.get();
                Set<BlockPos> added = new LinkedHashSet<>(after);
                added.removeAll(before);
                features.add(new FeatureReplay(path, stepIndex, globalIndex, before, after, added));
                union.addAll(added);
            }
        }
        return new DiamondReplayOutcome(candidateTotal, features, union);
    }

    /**
     * 重放若干装饰步骤（按传入顺序；调用方按步骤序号升序传，与原版一致）。
     *
     * @param stepIndices  要重放的步骤序号（= {@code GenerationStep.Decoration.ordinal()}）
     * @param onlyFeatures 只重放这些 feature（身份比对）；null 表示重放这些步骤的全部 feature
     * @param trace        候选面取证；null 表示本次不取证
     */
    public static Outcome replaySteps(ShadowWorldGenContext context, WorldGenLevel level, ChunkPos center,
                                      int[] stepIndices, Set<PlacedFeature> onlyFeatures, SeedPocTrace trace) {
        List<FeatureSorter.StepFeatureData> featureList = context.featuresPerStep();

        SectionPos sectionPos = SectionPos.of(center, level.getMinSectionY());
        BlockPos origin = sectionPos.origin();

        WorldgenRandom random = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
        long decorationSeed = random.setDecorationSeed(context.seed(), origin.getX(), origin.getZ());

        Set<Holder<Biome>> possibleBiomes = collectPossibleBiomes(level, center);
        possibleBiomes.retainAll(context.biomeSource().possibleBiomes());

        int candidateTotal = 0;
        int replayedTotal = 0;
        int placedTotal = 0;
        List<String> candidateIds = new ArrayList<>();
        for (int stepIndex : stepIndices) {
            if (stepIndex < 0 || stepIndex >= featureList.size()) {
                continue;
            }
            FeatureSorter.StepFeatureData stepData = featureList.get(stepIndex);
            Set<Integer> indices = new TreeSet<>();
            for (Holder<Biome> biome : possibleBiomes) {
                List<HolderSet<PlacedFeature>> perStep = biome.value().getGenerationSettings().features();
                if (stepIndex >= perStep.size()) {
                    continue;
                }
                for (Holder<PlacedFeature> holder : perStep.get(stepIndex)) {
                    indices.add(stepData.indexMapping().applyAsInt(holder.value()));
                }
            }
            int[] sorted = indices.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(sorted);
            candidateTotal += sorted.length;

            for (int globalIndex : sorted) {
                PlacedFeature feature = stepData.features().get(globalIndex);
                String path = context.registries()
                        .lookupOrThrow(net.minecraft.core.registries.Registries.PLACED_FEATURE)
                        .getResourceKey(feature)
                        .map(key -> key.identifier().getPath())
                        .orElse("?");
                candidateIds.add(stepIndex + ":" + globalIndex + ":" + path);
                if (onlyFeatures != null && !onlyFeatures.contains(feature)) {
                    continue;
                }
                replayedTotal++;
                random.setFeatureSeed(decorationSeed, globalIndex, stepIndex);
                if (trace == null) {
                    feature.placeWithBiomeCheck(level, context.generator(), random, origin);
                } else {
                    // 候选面取证只在放置期间打开标签，避免把别的调用算进这个 feature
                    trace.begin(stepIndex + ":" + globalIndex + ":" + path);
                    try {
                        feature.placeWithBiomeCheck(level, context.generator(), random, origin);
                    } finally {
                        trace.end();
                    }
                }
                placedTotal++;
            }
        }
        return new Outcome(candidateTotal, replayedTotal, placedTotal, candidateIds);
    }

    /**
     * 复刻原版 {@code ChunkGenerator#applyBiomeDecoration:329-336} 的生物群系收集：
     * 中心 3x3 区块里所有 section 的生物群系调色板内容。
     */
    private static Set<Holder<Biome>> collectPossibleBiomes(WorldGenLevel level, ChunkPos center) {
        Set<Holder<Biome>> biomes = new HashSet<>();
        List<ChunkPos> area = new ArrayList<>();
        ChunkPos.rangeClosed(center, 1).forEach(area::add);
        for (ChunkPos pos : area) {
            ChunkAccess chunk = level.getChunk(pos.x(), pos.z());
            for (LevelChunkSection section : chunk.getSections()) {
                if (section == null) {
                    continue;
                }
                section.getBiomes().getAll(biomes::add);
            }
        }
        return biomes;
    }
}
