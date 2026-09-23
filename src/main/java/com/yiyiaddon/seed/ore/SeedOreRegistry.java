package com.yiyiaddon.seed.ore;

import com.yiyiaddon.seed.model.OreType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.block.Blocks;

/**
 * 种子挖矿正式模块 · <b>矿物注册表</b>（正式化第八阶段 236；26.2 语义移植）。
 *
 * <p><b>它是全模块唯一的矿物真源</b>。读取器、观察层、界面、报告都从这里取定义；
 * 任何地方都不允许再出现 {@code switch (oreType)} 形式的方块判定或写死的扫描范围
 * （那是 236 要消灭的「钻石硬编码」的同一类问题，只是换了一种矿物）。</p>
 *
 * <h2>参数出处（Mojang 数据侧源码，逐条可核对）</h2>
 * <ul>
 *     <li>配置（尺寸 / 空气暴露丢弃率）：{@code net/minecraft/data/worldgen/features/OreFeatures.java}；</li>
 *     <li>放置（频率 / 高度 / 生物群系过滤）：{@code net/minecraft/data/worldgen/placement/OrePlacements.java}；</li>
 *     <li>高度区间常量：{@code .../placement/PlacementUtils}（{@code RANGE_10_10} /
 *         {@code RANGE_8_8} / {@code FULL_RANGE}）；</li>
 *     <li>生物群系归属：{@code net/minecraft/data/worldgen/BiomeDefaultFeatures.java}
 *         与 {@code .../biome/OverworldBiomes.java} / {@code .../biome/NetherBiomes.java}；</li>
 *     <li>散落地物算法：{@code net/minecraft/world/level/levelgen/feature/ScatteredOreFeature.java}；</li>
 *     <li>矿脉（NOISE 阶段）：{@code .../levelgen/NoiseChunk.java} 与
 *         {@code .../levelgen/OreVeinifier.java}。</li>
 * </ul>
 *
 * <h2>扫描窗口怎么算</h2>
 * <p>窗口 = vanilla 高度区间 <b>并上矿脉半径余量</b>（普通矿物地物半径 ≤ 5：尺寸最大 33 时
 * {@code maxRadius = ceil((size/16*2+1)/2) = 3}，再加 {@code OreFeature} 自身的 ±2；
 * 散落地物的偏移上界是 ±7，见 {@code ScatteredOreFeature}）。</p>
 *
 * <p>窗口只影响<b>扫描成本</b>，不影响结果正确性：真实写入位置永远由 Worker 里的原生
 * {@code applyBiomeDecoration} 决定，窗口只是「我们去哪里找它」。因此窗口一律取
 * <b>超集</b>（宁可多扫，不可漏扫），并按 section（16 格）对齐。</p>
 *
 * <p><b>26.2 移植口径</b>：上面的频率 / 高度 / 丢弃率文字是<b>对照来源</b>，不是被复述的算法。
 * 移植后 26.2 侧以「同一批种子 + 同一批目标区块跑出与 26.1.2 逐格一致的结果」为准
 * （见 236 矿物矩阵装置与 237 双版本报告）；如 26.2 数据侧参数有变，矩阵会当场暴露差异，
 * 届时以 26.2 实测为准修正此处文字。</p>
 */
public final class SeedOreRegistry {

    /** 主世界 y 下界（overworld 维度类型：min_y −64）。 */
    private static final int OVERWORLD_BOTTOM = -64;

    /** 主世界 y 上界（min_y −64 + height 384）。 */
    private static final int OVERWORLD_TOP = 319;

    /** 下界 y 下界（the_nether 维度类型：min_y 0）。 */
    private static final int NETHER_BOTTOM = 0;

    /** 下界 y 上界（min_y 0 + height 256）。 */
    private static final int NETHER_TOP = 255;

    /** 键 = （维度, 矿物）；插入序 = 界面展示序。 */
    private static final Map<String, SeedOreDefinition> DEFINITIONS = new LinkedHashMap<>();

    /** 主世界 8 种矿（顺序即界面展示序）。 */
    private static final List<OreType> OVERWORLD_ORES = List.of(
            OreType.DIAMOND, OreType.REDSTONE, OreType.LAPIS, OreType.GOLD,
            OreType.IRON, OreType.COPPER, OreType.COAL, OreType.EMERALD);

    /** 下界 3 种矿（顺序即界面展示序）。 */
    private static final List<OreType> NETHER_ORES = List.of(
            OreType.ANCIENT_DEBRIS, OreType.NETHER_QUARTZ, OreType.NETHER_GOLD);

    static {
        // ── 主世界 ───────────────────────────────────────────────────────────

        register(new SeedOreDefinition(OreType.DIAMOND, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE),
                List.of("ore_diamond_small", "ore_diamond_medium", "ore_diamond_large", "ore_diamond_buried"),
                List.of("ore_diamond", "ore_diamond_medium", "ore_diamond_large", "ore_diamond_buried"),
                "小 7 次 + 中型 2 次 + 大型平均每 9 区块 1 次 + 埋藏 4 次（每区块）",
                "小/中型/大型/埋藏均为三角分布 −144 ~ 16；中型为均匀 −64 ~ −4",
                "全部主世界生物群系（addDefaultOres）",
                "小 0.5 / 中型 0.5 / 大型 0.7 / 埋藏 1.0",
                // 上界 16 是 aboveBottom(80)（主世界 minY −64）加 ±4 半径
                OVERWORLD_BOTTOM, 16,
                Set.of(SeedOreWritePath.ORE_FEATURE, SeedOreWritePath.FOSSIL),
                // 235 已通过实机验证并正式接入自动挖矿，因此它是本阶段唯一 eligible 的矿物
                true,
                "OreFeatures（尺寸 4/8/12/8 与丢弃率 0.5/0.5/0.7/1.0）；"
                        + "OrePlacements（count 7 / 2 / rarity 9 / 4，三角 −144~16、均匀 −64~−4）；"
                        + "BiomeDefaultFeatures addDefaultOres；"
                        + "第二来源：CavePlacements fossil_lower + ProcessorLists#FOSSIL_DIAMONDS（煤矿规则换钻石）"));
        register(new SeedOreDefinition(OreType.REDSTONE, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE),
                List.of("ore_redstone"),
                List.of("ore_redstone", "ore_redstone_lower"),
                "低位 4 次（均匀 bottom ~ 15）+ 深部 8 次（三角 −96 ~ −32）",
                "均匀 −64 ~ 15；深部三角 −96 ~ −32",
                "全部主世界生物群系（addDefaultOres）",
                "0（两段都不带空气暴露丢弃）",
                OVERWORLD_BOTTOM, 19,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（尺寸 8，无丢弃率）；"
                        + "OrePlacements（count 4 uniform(bottom,15)；count 8 triangle(aboveBottom(−32), aboveBottom(32))）；"
                        + "BiomeDefaultFeatures addDefaultOres"));
        register(new SeedOreDefinition(OreType.LAPIS, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE),
                List.of("ore_lapis", "ore_lapis_buried"),
                List.of("ore_lapis", "ore_lapis_buried"),
                "浅层 2 次（三角 −32 ~ 32）+ 埋藏 4 次（均匀 bottom ~ 64）",
                "三角 −32 ~ 32；埋藏均匀 −64 ~ 64",
                "全部主世界生物群系（addDefaultOres）",
                "浅层 0 / 埋藏 1.0",
                OVERWORLD_BOTTOM, 68,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（尺寸 7；埋藏型丢弃率 1.0）；"
                        + "OrePlacements（count 2 triangle(−32,32)；count 4 uniform(bottom,64)）；"
                        + "BiomeDefaultFeatures addDefaultOres"));
        register(new SeedOreDefinition(OreType.GOLD, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE),
                List.of("ore_gold", "ore_gold_buried"),
                List.of("ore_gold", "ore_gold_lower", "ore_gold_extra"),
                "主 4 次（三角 −64 ~ 32）+ 深部 0~1 次（均匀 −64 ~ −48）+ 恶地额外 50 次（均匀 32 ~ 256）",
                "三角 −64 ~ 32；深部均匀 −64 ~ −48；恶地额外均匀 32 ~ 256",
                "全部主世界生物群系（addDefaultOres）；ore_gold_extra 只在恶地三变种（addExtraGold）",
                "主 0.5 / 深部 0.5 / 恶地额外 0",
                OVERWORLD_BOTTOM, 260,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（尺寸 9；埋藏型丢弃率 0.5）；"
                        + "OrePlacements（count 4 triangle(−64,32)；CountPlacement.of(UniformInt 0..1) uniform(−64,−48)；"
                        + "count 50 uniform(32,256)）；"
                        + "BiomeDefaultFeatures#addExtraGold 与 OverworldBiomes（恶地三变种）"));
        register(new SeedOreDefinition(OreType.IRON, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE),
                List.of("ore_iron", "ore_iron_small"),
                List.of("ore_iron_upper", "ore_iron_middle", "ore_iron_small"),
                "上 90 次（三角 80 ~ 384）+ 中 10 次（三角 −24 ~ 56）+ 小 10 次（均匀 bottom ~ 72）",
                "三角 80 ~ 384；三角 −24 ~ 56；均匀 −64 ~ 72",
                "全部主世界生物群系（addDefaultOres）",
                "0（三段都不带空气暴露丢弃）",
                OVERWORLD_BOTTOM, OVERWORLD_TOP,
                // 矿脉：主世界 oreVeinsEnabled=true（NoiseGeneratorSettings.overworld），
                // 类型表 IRON → DEEPSLATE_IRON_ORE，y −60 ~ −8（OreVeinifier 类型表）
                Set.of(SeedOreWritePath.ORE_FEATURE, SeedOreWritePath.ORE_VEIN),
                false,
                "OreFeatures（尺寸 9 / 4）；"
                        + "OrePlacements（count 90 / 10 / 10）；"
                        + "BiomeDefaultFeatures addDefaultOres；"
                        + "第二来源（NOISE 阶段矿脉）：NoiseChunk + OreVeinifier 类型表"));
        register(new SeedOreDefinition(OreType.COPPER, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE),
                List.of("ore_copper_small", "ore_copper_large"),
                List.of("ore_copper", "ore_copper_large"),
                "小 16 次 + 大 16 次（都是三角 −16 ~ 112）",
                "三角 −16 ~ 112",
                "全部主世界生物群系（addDefaultOres，寒带/温带走小簇，其余上大簇）",
                "0（两簇都不带空气暴露丢弃）",
                OVERWORLD_BOTTOM, 116,
                Set.of(SeedOreWritePath.ORE_FEATURE, SeedOreWritePath.ORE_VEIN),
                false,
                "OreFeatures（尺寸 10 / 20；小簇键名原生拼写 ORE_COPPPER_SMALL）；"
                        + "OrePlacements（count 16 / 16）；"
                        + "BiomeDefaultFeatures addDefaultOres；"
                        + "第二来源（NOISE 阶段矿脉）：NoiseChunk + OreVeinifier 类型表（COPPER_ORE，y 0~50）"));
        register(new SeedOreDefinition(OreType.COAL, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE),
                List.of("ore_coal", "ore_coal_buried"),
                List.of("ore_coal_upper", "ore_coal_lower"),
                "上层 30 次（均匀 136 ~ 世界顶）+ 下层 20 次（三角 0 ~ 192）",
                "均匀 136 ~ 319；三角 0 ~ 192",
                "全部主世界生物群系（addDefaultOres）",
                "上层 0 / 下层 0.5（下层用的是埋藏型配置）",
                OVERWORLD_BOTTOM, OVERWORLD_TOP,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（尺寸 17；埋藏型丢弃率 0.5）；"
                        + "OrePlacements（count 30 uniform(absolute(136), top())；count 20 triangle(0,192)）；"
                        + "BiomeDefaultFeatures addDefaultOres"));
        register(new SeedOreDefinition(OreType.EMERALD, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE),
                List.of("ore_emerald"),
                List.of("ore_emerald"),
                "100 次（三角 −16 ~ 480；上界超出世界高度，由 isOutsideBuildHeight 截断到 319）",
                "三角 −16 ~ 480（实际写入被世界高度截断）",
                "只在 8 类山地系生物群系：风袭丘陵系 / 草甸 / 樱花树林 / 冻峰 / 尖峭峰 / 石峰 / 雪坡 / 雪林",
                "0",
                OVERWORLD_BOTTOM, OVERWORLD_TOP,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（尺寸 3）；"
                        + "OrePlacements（count 100 triangle(−16,480)）；"
                        + "BiomeDefaultFeatures#addExtraEmeralds；"
                        + "调用点 OverworldBiomes（风袭丘陵 / 草甸与樱花树林 / 三类山峰 / 雪坡 / 雪林）"));
        // ── 下界 ─────────────────────────────────────────────────────────────

        register(new SeedOreDefinition(OreType.ANCIENT_DEBRIS, SeedDimensionProfile.NETHER,
                List.of(Blocks.ANCIENT_DEBRIS),
                List.of("ore_ancient_debris_large", "ore_ancient_debris_small"),
                List.of("ore_ancient_debris_large", "ore_debris_small"),
                "大簇每区块 1 次（无 CountPlacement）；小簇每区块 1 次（无 CountPlacement）",
                "大簇三角 8 ~ 24；小簇均匀 8 ~ 248（RANGE_8_8）；两者都是散落地物，实际散布 ±7",
                "全部下界生物群系（addAncientDebris，玄武岩三角洲也显式调用）",
                "1.0（两种尺寸都是全丢弃：暴露在空气里的候选直接放弃）",
                NETHER_BOTTOM, NETHER_TOP,
                Set.of(SeedOreWritePath.SCATTERED_ORE),
                false,
                "OreFeatures（Feature.SCATTERED_ORE，尺寸 3 / 2，丢弃率 1.0）；"
                        + "OrePlacements（大簇 InSquare+triangle(8,24)+BiomeFilter，无 CountPlacement；"
                        + "小簇 InSquare+RANGE_8_8+BiomeFilter）；"
                        + "PlacementUtils RANGE_8_8；ScatteredOreFeature（±7 散布）；"
                        + "BiomeDefaultFeatures#addAncientDebris 与 NetherBiomes（三角洲）"));
        register(new SeedOreDefinition(OreType.NETHER_QUARTZ, SeedDimensionProfile.NETHER,
                List.of(Blocks.NETHER_QUARTZ_ORE),
                List.of("ore_quartz"),
                List.of("ore_quartz_nether", "ore_quartz_deltas"),
                "常规 16 次 + 玄武岩三角洲 32 次（都是 RANGE_10_10）",
                "均匀 aboveBottom(10) ~ belowTop(10)，即 y 10 ~ 246；实际写入受地形高度图限制",
                "常规四种下界生物群系（addNetherDefaultOres）；delta 版只在玄武岩三角洲",
                "0",
                5, 250,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（netherrack 目标，尺寸 14）；"
                        + "OrePlacements（count 32 / 16，RANGE_10_10）；"
                        + "PlacementUtils RANGE_10_10 = uniform(aboveBottom(10), belowTop(10))；"
                        + "BiomeDefaultFeatures#addNetherDefaultOres 与 NetherBiomes（三角洲）"));
        register(new SeedOreDefinition(OreType.NETHER_GOLD, SeedDimensionProfile.NETHER,
                List.of(Blocks.NETHER_GOLD_ORE),
                List.of("ore_nether_gold"),
                List.of("ore_gold_nether", "ore_gold_deltas"),
                "常规 10 次 + 玄武岩三角洲 20 次（都是 RANGE_10_10）",
                "均匀 aboveBottom(10) ~ belowTop(10)，即 y 10 ~ 246；实际写入受地形高度图限制",
                "常规四种下界生物群系（addNetherDefaultOres）；delta 版只在玄武岩三角洲",
                "0",
                5, 250,
                Set.of(SeedOreWritePath.ORE_FEATURE),
                false,
                "OreFeatures（netherrack 目标，尺寸 10）；"
                        + "OrePlacements（count 20 / 10，RANGE_10_10）；"
                        + "PlacementUtils RANGE_10_10；"
                        + "BiomeDefaultFeatures#addNetherDefaultOres 与 NetherBiomes（三角洲）"));
    }

    private SeedOreRegistry() {
    }

    private static void register(SeedOreDefinition definition) {
        SeedOreDefinition previous = DEFINITIONS.put(key(definition.dimension(), definition.oreType()), definition);
        if (previous != null) {
            throw new IllegalStateException("矿物定义重复注册：" + definition.oreType() + " @ "
                    + definition.dimension().dimensionId());
        }
    }

    private static String key(SeedDimensionProfile dimension, OreType oreType) {
        return dimension.dimensionId() + "#" + oreType.name();
    }

    /**
     * 取一条定义。
     *
     * @return 定义；该维度不支持该矿物返回 {@code null}（调用方必须 fail-closed）
     */
    public static SeedOreDefinition of(SeedDimensionProfile dimension, OreType oreType) {
        if (dimension == null || oreType == null) {
            return null;
        }
        return DEFINITIONS.get(key(dimension, oreType));
    }

    /** 该维度是否支持该矿物。 */
    public static boolean supports(SeedDimensionProfile dimension, OreType oreType) {
        return of(dimension, oreType) != null;
    }

    /** 某维度支持的矿物清单（顺序即界面展示序；不可变）。 */
    public static List<OreType> oresOf(SeedDimensionProfile dimension) {
        if (dimension == null) {
            return List.of();
        }
        return switch (dimension) {
            case OVERWORLD -> OVERWORLD_ORES;
            case NETHER -> NETHER_ORES;
        };
    }

    /** 某维度支持的矿物定义清单（顺序与 {@link #oresOf} 一致）。 */
    public static List<SeedOreDefinition> definitionsOf(SeedDimensionProfile dimension) {
        List<SeedOreDefinition> out = new ArrayList<>();
        for (OreType oreType : oresOf(dimension)) {
            SeedOreDefinition definition = of(dimension, oreType);
            if (definition != null) {
                out.add(definition);
            }
        }
        return List.copyOf(out);
    }

    /** 全部定义（顺序：主世界 8 种、再下界 3 种）。 */
    public static List<SeedOreDefinition> all() {
        return List.copyOf(DEFINITIONS.values());
    }

    /**
     * 该矿物是否可以在本维度进入自动挖矿。
     *
     * <p>236 的口径：<b>只有钻石为 true</b>（235 已经过 A~L 实机验证并正式接入）。
     * 其余矿物即便预测 / 观察 / ESP 全部可用，也一律 {@code false} —— 它们还没有建立
     * 「候选 ↔ 真实 BlockState」的实机对照证据，不许进自动挖矿。</p>
     */
    public static boolean autoMinerEligible(SeedDimensionProfile dimension, OreType oreType) {
        SeedOreDefinition definition = of(dimension, oreType);
        return definition != null && definition.autoMinerEligible();
    }

    /** 某维度受支持矿物的中文清单（错误提示 / 界面用）。 */
    public static String describeSupportedOresCn(SeedDimensionProfile dimension) {
        if (dimension == null) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        for (OreType oreType : oresOf(dimension)) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(oreType.displayNameCn());
        }
        return builder.toString();
    }
}
