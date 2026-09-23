package com.yiyiaddon.dev.seedpoc;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * 第四轮 · 离线 worldgen 上下文。
 *
 * <p><b>它解决什么</b>：第三轮证明的是「给定正确的 pre-diamond 输入」下钻石放置链逐 BlockPos 正确。
 * 第四轮要证的是：**这份输入本身能不能只凭 Seed 离线算出来**。所以必须自己持有一套
 * 「与当前世界无关、只由 种子 + 注册表 + 原版 dimension/worldgen 配置 决定」的生成上下文。</p>
 *
 * <p><b>种子相关的四件东西全部自建</b>（这是「不读真实世界」的可验证形式，逐条给源码依据）：</p>
 * <ol>
 *     <li>{@link BiomeSource}：{@code MultiNoiseBiomeSource.createFromPreset(overworld 预设)}
 *         （MultiNoiseBiomeSource.java:35）。生物群系源只由数据包预设决定，与种子、与世界都无关；</li>
 *     <li>{@link NoiseBasedChunkGenerator}：{@code new NoiseBasedChunkGenerator(生物群系源, NoiseGeneratorSettings.overworld)}
 *         （NoiseBasedChunkGenerator.java:62，public）；</li>
 *     <li>{@link RandomState}：{@code RandomState.create(注册表, NoiseGeneratorSettings.overworld, 种子)}
 *         （RandomState.java:28-34）——噪声地形、含水层、地表系统、矿脉随机全部由它派生；</li>
 *     <li>{@link ChunkGeneratorStructureState}：{@code ChunkGenerator#createState(结构集, RandomState, 种子)}
 *         （ChunkGenerator.java:109-111）——结构起点是否落在某个区块、结构随机全部由它派生。
 *         原版在 {@code ChunkMap} 构造时创建它（ChunkMap.java:183 附近），这里用同一个入口。</li>
 * </ol>
 *
 * <p><b>宿主（{@code host}）只当「环境宿主」，不当数据来源</b>：离线 pipeline 会从它身上取
 * 维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂——这些都与种子无关、也与「这个世界
 * 生成了什么」无关；<b>任何方块状态、生物群系、高度图都不会从宿主读</b>，
 * 全部来自 {@link OfflineChunkHolder} 里的自有区块。这一点由
 * {@link OfflineChunkRegion#hostChunkSourceQueries()} 计数自证（报告里给出）。</p>
 */
final class OfflineWorldgenContext {

    private final ServerLevel host;
    private final long seed;
    private final RegistryAccess registries;
    private final BiomeSource biomeSource;
    private final NoiseBasedChunkGenerator generator;
    private final RandomState randomState;
    private final ChunkGeneratorStructureState structureState;
    private final StructureManager hostStructureManager;
    private final StructureTemplateManager templateManager;
    private final PalettedContainerFactory containerFactory;

    private OfflineWorldgenContext(ServerLevel host, long seed, RegistryAccess registries, BiomeSource biomeSource,
                                   NoiseBasedChunkGenerator generator, RandomState randomState,
                                   ChunkGeneratorStructureState structureState, StructureManager hostStructureManager,
                                   StructureTemplateManager templateManager,
                                   PalettedContainerFactory containerFactory) {
        this.host = host;
        this.seed = seed;
        this.registries = registries;
        this.biomeSource = biomeSource;
        this.generator = generator;
        this.randomState = randomState;
        this.structureState = structureState;
        this.hostStructureManager = hostStructureManager;
        this.templateManager = templateManager;
        this.containerFactory = containerFactory;
    }

    /**
     * 由注册表 + 种子独立构造离线生成上下文。
     *
     * @param host 环境宿主（只取与种子无关的环境参数，不读它的任何方块状态）
     * @param seed 被试种子
     */
    static OfflineWorldgenContext create(ServerLevel host, long seed) {
        RegistryAccess registries = host.registryAccess();

        Holder<MultiNoiseBiomeSourceParameterList> preset = registries
                .lookupOrThrow(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                .getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        BiomeSource source = MultiNoiseBiomeSource.createFromPreset(preset);

        Holder<NoiseGeneratorSettings> settings = registries
                .lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(source, settings);

        RandomState randomState = RandomState.create(registries, NoiseGeneratorSettings.OVERWORLD, seed);
        ChunkGeneratorStructureState structureState = generator.createState(
                registries.lookupOrThrow(Registries.STRUCTURE_SET), randomState, seed);

        return new OfflineWorldgenContext(host, seed, registries, source, generator, randomState, structureState,
                host.structureManager(), host.getStructureManager(), host.palettedContainerFactory());
    }

    /** 环境宿主（只用于与种子无关的环境参数）。 */
    ServerLevel host() {
        return host;
    }

    /** 被试种子。 */
    long seed() {
        return seed;
    }

    /** 注册表。 */
    RegistryAccess registries() {
        return registries;
    }

    /** 自建生物群系源。 */
    BiomeSource biomeSource() {
        return biomeSource;
    }

    /** 自建区块生成器。 */
    NoiseBasedChunkGenerator generator() {
        return generator;
    }

    /** 种子派生的随机状态。 */
    RandomState randomState() {
        return randomState;
    }

    /** 种子派生的结构状态（决定结构起点落在哪些区块）。 */
    ChunkGeneratorStructureState structureState() {
        return structureState;
    }

    /** 宿主的结构管理器（只当服务用：起点读写都由调用方把区块显式传进去）。 */
    StructureManager hostStructureManager() {
        return hostStructureManager;
    }

    /** 结构模板管理器（拼结构用，与种子无关）。 */
    StructureTemplateManager templateManager() {
        return templateManager;
    }

    /** 方块状态调色板工厂（与种子无关）。 */
    PalettedContainerFactory containerFactory() {
        return containerFactory;
    }

    /** 世界是否开启结构生成（原版 {@code ChunkStatusTasks#generateStructureStarts} 的同一判据）。 */
    boolean generateStructures() {
        return host.getServer().getWorldGenSettings().options().generateStructures();
    }
}
