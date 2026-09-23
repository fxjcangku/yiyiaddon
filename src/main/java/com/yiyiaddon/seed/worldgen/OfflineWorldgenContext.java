package com.yiyiaddon.seed.worldgen;

import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * 种子挖矿正式模块 · 离线 worldgen 上下文（只由 种子 + 注册表 + 原版 worldgen 配置 决定）。
 *
 * <p><b>它解决什么</b>：预测必须在「与当前世界生成了什么无关」的独立世界里跑，
 * 否则真实世界的调度历史会从输入侧污染预测。因此这里自己持有一套种子相关的四件东西：</p>
 *
 * <ol>
 *     <li>{@link BiomeSource}：{@code MultiNoiseBiomeSource.createFromPreset(预设)}
 *         （MultiNoiseBiomeSource.java:35）。生物群系源只由数据包预设决定，与种子、与世界都无关；</li>
 *     <li>{@link NoiseBasedChunkGenerator}：{@code new NoiseBasedChunkGenerator(生物群系源, 噪声设置)}
 *         （NoiseBasedChunkGenerator.java:62，public）；</li>
 *     <li>{@link RandomState}：{@code RandomState.create(注册表, 噪声设置键, 种子)} ——
 *         噪声地形、含水层、地表系统、矿脉随机全部由它派生；</li>
 *     <li>{@link ChunkGeneratorStructureState}：{@code ChunkGenerator#createState(结构集, RandomState, 种子)}
 *         （ChunkGenerator.java:109-111）—— 结构起点落在哪些区块由它派生，
 *         与原版 {@code ChunkMap} 构造时的入口相同。</li>
 * </ol>
 *
 * <p><b>236：预设与噪声设置按维度取，不再写死主世界</b>。这不是「换个 Y 范围」那么轻：
 * 下界的 {@code NoiseGeneratorSettings.nether(...)} 里 {@code useLegacyRandomSource=true}
 * （{@code NoiseGeneratorSettings.java:99-113}），也就是<b>随机算法都不是同一套</b>
 * —— 用主世界设置去算下界会得到「看起来正常但整体错位」的结果。因此这里改成查
 * {@link SeedDimensionProfile}，并且要求宿主的维度高度与该档案一致（不一致即拒绝，
 * 绝不用一份参数去凑另一份维度）。</p>
 *
 * <p><b>宿主（{@code host}）只当「环境宿主」</b>：只从它身上取维度类型 / 世界高度 / 注册表 /
 * 结构模板管理器 / 调色板工厂 / 世界是否开结构——这些都与种子无关、也与「这个世界的区块生成了什么」无关。
 * <b>任何方块状态、生物群系、高度图都不从宿主读</b>，全部来自本模块自有的离线区块；
 * 这一点由 {@link OfflineChunkRegion} 的「宿主 ChunkMap 查询次数」自证。</p>
 *
 * <p><b>本类不做任何 I/O、不持有可变状态</b>，因此可以安全地被同一会话内的多个
 * {@link OfflineChunkPipeline}（基线世界 + 调度敏感复核世界）共用。</p>
 */
public final class OfflineWorldgenContext {

    /** 环境宿主（只取与种子无关的环境参数）。 */
    private final ServerLevel host;

    /** 维度档案（预设 / 噪声设置 / 高度口径都来自它）。 */
    private final SeedDimensionProfile profile;

    /** 被试种子。 */
    private final long seed;

    /** 注册表。 */
    private final RegistryAccess registries;

    /** 自建生物群系源。 */
    private final BiomeSource biomeSource;

    /** 自建区块生成器。 */
    private final NoiseBasedChunkGenerator generator;

    /** 种子派生的随机状态。 */
    private final RandomState randomState;

    /** 种子派生的结构状态。 */
    private final ChunkGeneratorStructureState structureState;

    /** 宿主的结构管理器（起点读写都由调用方把区块显式传进去，等于只借服务不借数据）。 */
    private final StructureManager hostStructureManager;

    /** 结构模板管理器（与种子无关）。 */
    private final StructureTemplateManager templateManager;

    /** 方块状态调色板工厂（与种子无关）。 */
    private final PalettedContainerFactory containerFactory;

    private OfflineWorldgenContext(ServerLevel host, SeedDimensionProfile profile, long seed,
                                   RegistryAccess registries, BiomeSource biomeSource,
                                   NoiseBasedChunkGenerator generator, RandomState randomState,
                                   ChunkGeneratorStructureState structureState, StructureManager hostStructureManager,
                                   StructureTemplateManager templateManager,
                                   PalettedContainerFactory containerFactory) {
        this.host = host;
        this.profile = profile;
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
     * 由注册表 + 种子独立构造离线生成上下文（维度由宿主的维度决定）。
     *
     * <p>宿主维度不在 {@link SeedDimensionProfile} 里，或维度高度与原版该维度不一致时，
     * 一律抛异常（上层转成「预测失败」）—— 绝不退回主世界参数硬算。</p>
     *
     * @param host 环境宿主（只取与种子无关的环境参数，不读它的任何方块状态）
     * @param seed 被试种子
     */
    public static OfflineWorldgenContext create(ServerLevel host, long seed) {
        SeedDimensionProfile profile = SeedDimensionProfile.requireOf(host.dimension());
        if (!profile.matchesHost(host)) {
            throw new IllegalStateException("宿主维度高度与原版该维度不一致（宿主 y "
                    + host.getMinY() + "~" + host.getMaxY() + "，档案 " + profile.minY() + "~" + profile.maxY()
                    + "）：离线 worldgen 的高度口径无法套用，拒绝预测");
        }
        RegistryAccess registries = host.registryAccess();

        Holder<MultiNoiseBiomeSourceParameterList> preset = registries
                .lookupOrThrow(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                .getOrThrow(profile.biomeSourcePreset());
        BiomeSource source = MultiNoiseBiomeSource.createFromPreset(preset);

        Holder<NoiseGeneratorSettings> settings = registries
                .lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(profile.noiseSettings());
        NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(source, settings);

        // 注意：这里用的噪声设置键同时决定随机算法（下界走 LEGACY，见类注释），不能换成主世界的
        RandomState randomState = RandomState.create(registries, profile.noiseSettings(), seed);
        ChunkGeneratorStructureState structureState = generator.createState(
                registries.lookupOrThrow(Registries.STRUCTURE_SET), randomState, seed);

        return new OfflineWorldgenContext(host, profile, seed, registries, source, generator, randomState,
                structureState, host.structureManager(), host.getStructureManager(), host.palettedContainerFactory());
    }

    /** 维度档案（预设 / 噪声设置 / 高度口径）。 */
    public SeedDimensionProfile profile() {
        return profile;
    }

    /** 环境宿主（只用于与种子无关的环境参数）。 */
    public ServerLevel host() {
        return host;
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
    public NoiseBasedChunkGenerator generator() {
        return generator;
    }

    /** 种子派生的随机状态。 */
    public RandomState randomState() {
        return randomState;
    }

    /** 种子派生的结构状态。 */
    public ChunkGeneratorStructureState structureState() {
        return structureState;
    }

    /** 宿主的结构管理器（只当服务用）。 */
    public StructureManager hostStructureManager() {
        return hostStructureManager;
    }

    /** 结构模板管理器。 */
    public StructureTemplateManager templateManager() {
        return templateManager;
    }

    /** 方块状态调色板工厂。 */
    public PalettedContainerFactory containerFactory() {
        return containerFactory;
    }

    /** 世界是否开启结构生成（原版 {@code ChunkStatusTasks#generateStructureStarts} 的同一判据）。 */
    public boolean generateStructures() {
        return host.getServer().getWorldGenSettings().options().generateStructures();
    }
}
