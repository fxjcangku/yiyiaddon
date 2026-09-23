package com.yiyiaddon.dev.seedpoc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 影子 worldgen 上下文：把「原版主世界」这套生成参数从注册表里独立重建一份。
 *
 * <p><b>为什么需要它</b>：要做真正的 Seed 预测，就必须自己拿到
 * {@code BiomeSource} / {@code ChunkGenerator} / {@code RandomState} / 每个装饰步骤的
 * {@code PlacedFeature} 索引表；这些若都从服务端对象借，实验就只能证明「服务端能算出矿」，
 * 证明不了「我们能算出矿」。所以本类全部<b>新建</b>，服务器对象只用于交叉核对（见
 * {@link #verifyAgainstServerGenerator(ChunkGenerator)}）。</p>
 *
 * <p><b>每条构造的源码依据（26.1.2 本线）</b></p>
 * <ul>
 *     <li>{@code MultiNoiseBiomeSource.createFromPreset(Holder)} —— MultiNoiseBiomeSource.java:35；</li>
 *     <li>预设列表键 {@code minecraft:overworld} —— MultiNoiseBiomeSourceParameterLists.java:11；</li>
 *     <li>噪声设置键 {@code NoiseGeneratorSettings.OVERWORLD} —— NoiseGeneratorSettings.java:49；</li>
 *     <li>{@code new NoiseBasedChunkGenerator(BiomeSource, Holder)} —— NoiseBasedChunkGenerator.java:62（public）；</li>
 *     <li>{@code RandomState.create(holderProvider, key, seed)} —— RandomState.java:28-34（public static）；</li>
 *     <li>索引表算法 {@code FeatureSorter.buildFeaturesPerStep} —— FeatureSorter.java:29-31，
 *         与原版 {@code ChunkGenerator} 构造函数里的用法逐字一致（ChunkGenerator.java:96-100）。</li>
 * </ul>
 */
public final class ShadowWorldGenContext {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 被试种子（原版 long 语义，可为负）。 */
    private final long seed;
    /** 注册表（取自当前世界，原版主世界数据包内容）。 */
    private final RegistryAccess registries;
    /** 自建生物群系源。 */
    private final BiomeSource biomeSource;
    /** 自建区块生成器（只用到它的生物群系生成设置查询，不做地形生成）。 */
    private final ChunkGenerator generator;
    /** 由种子派生的随机状态（生物群系采样器等）。 */
    private final RandomState randomState;
    /** 每个装饰步骤的 placed_feature 全局索引表（自建）。 */
    private final List<FeatureSorter.StepFeatureData> featuresPerStep;
    /** 索引表交叉核对结论（对报告用）。 */
    private String verifyNote = "未核对";

    private ShadowWorldGenContext(
            long seed,
            RegistryAccess registries,
            BiomeSource biomeSource,
            ChunkGenerator generator,
            RandomState randomState,
            List<FeatureSorter.StepFeatureData> featuresPerStep) {
        this.seed = seed;
        this.registries = registries;
        this.biomeSource = biomeSource;
        this.generator = generator;
        this.randomState = randomState;
        this.featuresPerStep = featuresPerStep;
    }

    /**
     * 由注册表 + 种子独立构造一套影子生成上下文。
     *
     * @param registries 当前世界的注册表（服务端数据包同步而来，含 worldgen 全部注册项）
     * @param seed       被试种子
     */
    public static ShadowWorldGenContext create(RegistryAccess registries, long seed) {
        Holder<MultiNoiseBiomeSourceParameterList> preset = registries
                .lookupOrThrow(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                .getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        BiomeSource source = MultiNoiseBiomeSource.createFromPreset(preset);

        Holder<NoiseGeneratorSettings> settings = registries
                .lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        ChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(source, settings);

        RandomState state = RandomState.create(registries, NoiseGeneratorSettings.OVERWORLD, seed);

        // 与 ChunkGenerator 构造函数逐字同源：来源列表 = 生物群系源的全部可能生物群系，
        // 取值函数 = 生物群系的 generationSettings().features()，第三参为原版的降级容错开关。
        List<FeatureSorter.StepFeatureData> steps = FeatureSorter.buildFeaturesPerStep(
                List.copyOf(source.possibleBiomes()),
                biome -> biome.value().getGenerationSettings().features(),
                true);

        return new ShadowWorldGenContext(seed, registries, source, chunkGenerator, state, steps);
    }

    /** 被试种子。 */
    public long seed() {
        return seed;
    }

    /** 注册表。 */
    public RegistryAccess registries() {
        return registries;
    }

    /** 自建生物群系源。 */
    public BiomeSource biomeSource() {
        return biomeSource;
    }

    /** 自建区块生成器。 */
    public ChunkGenerator generator() {
        return generator;
    }

    /** 种子派生的随机状态。 */
    public RandomState randomState() {
        return randomState;
    }

    /** 每个装饰步骤的 placed_feature 索引表。 */
    public List<FeatureSorter.StepFeatureData> featuresPerStep() {
        return featuresPerStep;
    }

    /** 索引表交叉核对结论（中文，直接进报告）。 */
    public String verifyNote() {
        return verifyNote;
    }

    /**
     * 直接从注册表读出钻石四条 placed_feature 的运行期配置并排成中文说明。
     *
     * <p>为什么要读运行期值：{@code discard_chance_on_air_exposure} 决定
     * {@code OreFeature#canPlaceOre} 在候选点可替换时会不会消耗一次 {@code nextFloat()}，
     * 是本 PoC 归因分析的关键前提；而配置来自数据包，不能只用源码里的默认值推。
     * 这里读到的数值会被原样写进报告，和源码 {@code data/worldgen/features/OreFeatures.java:109-112}
     * 的默认值并列，方便一眼看出是否有数据包覆盖。</p>
     */
    public List<String> describeDiamondOreConfigs() {
        List<String> lines = new ArrayList<>();
        var configuredRegistry = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        for (String id : SeedPocConstants.DIAMOND_PLACED_FEATURES) {
            PlacedFeature placed = placedFeature(id);
            Object config = placed.feature().value().config();
            if (!(config instanceof OreConfiguration ore)) {
                lines.add("    " + id + "：配置类型 " + config.getClass().getSimpleName() + "（非矿物配置）");
                continue;
            }
            List<String> targets = new ArrayList<>();
            for (OreConfiguration.TargetBlockState target : ore.targetStates) {
                targets.add(OreBlockLedger.shortId(target.state) + "←" + target.target.getClass().getSimpleName());
            }
            lines.add("    " + id + "：configured_feature="
                    + configuredRegistry.getResourceKey(placed.feature().value()).map(Object::toString).orElse("?")
                    + " / size=" + ore.size
                    + " / discardChanceOnAirExposure=" + ore.discardChanceOnAirExposure
                    + " / 目标：" + String.join("、", targets));
        }
        return lines;
    }

    /** 按标识取 placed_feature（用注册表同一实例，保证身份索引可用）。 */
    public PlacedFeature placedFeature(String id) {
        return registries.lookupOrThrow(Registries.PLACED_FEATURE)
                .getOrThrow(net.minecraft.resources.ResourceKey.create(
                        Registries.PLACED_FEATURE,
                        net.minecraft.resources.Identifier.withDefaultNamespace(id)))
                .value();
    }

    /**
     * 与服务器真实生成器缓存的索引表做交叉核对。
     *
     * <p>做法：反射只读 {@code ChunkGenerator#featuresPerStep}（private，类型
     * {@code Supplier<List<StepFeatureData>>}），与自建表逐步骤比对「feature 数量」以及
     * 「钻石四个 placed_feature 的全局索引」。这一步只读不写，失败也不影响实验主体——
     * 它存在的意义是把「索引表能不能自己算对」变成一个可证伪的数字。</p>
     */
    public void verifyAgainstServerGenerator(ChunkGenerator serverGenerator) {
        try {
            Field field = ChunkGenerator.class.getDeclaredField("featuresPerStep");
            field.setAccessible(true);
            Object raw = field.get(serverGenerator);
            if (!(raw instanceof Supplier<?> supplier)) {
                verifyNote = "核对失败：featuresPerStep 不是 Supplier";
                return;
            }
            Object cached = supplier.get();
            if (!(cached instanceof List<?> list)) {
                verifyNote = "核对失败：缓存值不是 List";
                return;
            }
            if (list.size() != featuresPerStep.size()) {
                verifyNote = "不一致：步骤数 服务器=" + list.size() + " 自建=" + featuresPerStep.size();
                return;
            }
            int comparedFeatures = 0;
            int comparedIndices = 0;
            for (int step = 0; step < list.size(); step++) {
                if (!(list.get(step) instanceof FeatureSorter.StepFeatureData serverData)) {
                    verifyNote = "核对失败：第 " + step + " 步类型不符";
                    return;
                }
                FeatureSorter.StepFeatureData mine = featuresPerStep.get(step);
                if (serverData.features().size() != mine.features().size()) {
                    verifyNote = "不一致：第 " + step + " 步 feature 数 服务器="
                            + serverData.features().size() + " 自建=" + mine.features().size();
                    return;
                }
                comparedFeatures += mine.features().size();
            }
            // 逐步骤比对钻石四个 feature 的全局索引
            for (String id : SeedPocConstants.DIAMOND_PLACED_FEATURES) {
                PlacedFeature feature = placedFeature(id);
                for (int step = 0; step < list.size(); step++) {
                    FeatureSorter.StepFeatureData serverData = (FeatureSorter.StepFeatureData) list.get(step);
                    FeatureSorter.StepFeatureData mine = featuresPerStep.get(step);
                    int serverIndex = serverData.indexMapping().applyAsInt(feature);
                    int myIndex = mine.indexMapping().applyAsInt(feature);
                    comparedIndices++;
                    if (serverIndex != myIndex) {
                        verifyNote = "不一致：" + id + " 在步骤 " + step + " 的索引 服务器="
                                + serverIndex + " 自建=" + myIndex;
                        return;
                    }
                }
            }
            verifyNote = "一致：步骤数 " + list.size() + "、feature 总数 " + comparedFeatures
                    + "、钻石索引比对 " + comparedIndices + " 项全部相同";
        } catch (Throwable error) {
            LOGGER.warn("{}：索引表交叉核对未完成（不影响实验主体）", SeedPocConstants.LOG_KEY, error);
            verifyNote = "核对未完成：" + error.getClass().getSimpleName();
        }
    }
}
