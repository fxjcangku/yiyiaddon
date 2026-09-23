package com.yiyiaddon.seed.ore;

import com.yiyiaddon.seed.model.OreType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.block.Blocks;

/**
 * 种子挖矿正式模块 · <b>矿物注册表</b>（正式化第八阶段 236）。
 *
 * <p><b>它是全模块唯一的矿物真源</b>。读取器、观察层、界面、报告都从这里取定义；
 * 任何地方都不允许再出现 {@code switch (oreType)} 形式的方块判定或写死的扫描范围
 * （那是 236 要消灭的「钻石硬编码」的同一类问题，只是换了一种矿物）。</p>
 *
 * <h2>参数出处（26.1.2 Mojang 源码，逐条可核对）</h2>
 * <ul>
 *     <li>配置（尺寸 / 空气暴露丢弃率）：{@code net/minecraft/data/worldgen/features/OreFeatures.java}；</li>
 *     <li>放置（频率 / 高度 / 生物群系过滤）：{@code net/minecraft/data/worldgen/placement/OrePlacements.java}；</li>
 *     <li>高度区间常量：{@code .../placement/PlacementUtils.java:35-37}（{@code RANGE_10_10} /
 *         {@code RANGE_8_8} / {@code FULL_RANGE}）；</li>
 *     <li>生物群系归属：{@code net/minecraft/data/worldgen/BiomeDefaultFeatures.java}
 *         与 {@code .../biome/OverworldBiomes.java} / {@code .../biome/NetherBiomes.java}；</li>
 *     <li>散落地物算法：{@code net/minecraft/world/level/levelgen/feature/ScatteredOreFeature.java}；</li>
 *     <li>矿脉（NOISE 阶段）：{@code .../levelgen/NoiseChunk.java:166-168} 与
 *         {@code .../levelgen/OreVeinifier.java:63-65}。</li>
 * </ul>
 *
 * <h2>扫描窗口怎么算</h2>
 * <p>窗口 = vanilla 高度区间 <b>并上矿脉半径余量</b>（普通矿物地物半径 ≤ 5：尺寸最大 33 时
 * {@code maxRadius = ceil((size/16*2+1)/2) = 3}，再加 {@code OreFeature} 自身的 ±2；
 * 散落地物的偏移上界是 ±7，见 {@code ScatteredOreFeature.java:47}）。</p>
 *
 * <p>窗口只影响<b>扫描成本</b>，不影响结果正确性：真实写入位置永远由 Worker 里的原生
 * {@code applyBiomeDecoration} 决定，窗口只是「我们去哪里找它」。因此窗口一律取
 * <b>超集</b>（宁可多扫，不可漏扫），并按 section（16 格）对齐。</p>
 */
public final class SeedOreRegistry {

    /** 主世界 y 下界（{@code DimensionTypes.java:45-52}）。 */
    private static final int OVERWORLD_BOTTOM = -64;

    /** 主世界 y 上界（min_y −64 + height 384，{@code DimensionTypes.java:45-52}）。 */
    private static final int OVERWORLD_TOP = 319;

    /** 下界 y 下界（{@code DimensionTypes.java:65-74}）。 */
    private static final int NETHER_BOTTOM = 0;

    /** 下界 y 上界（min_y 0 + height 256，{@code DimensionTypes.java:65-74}）。 */
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
                // 上界 16 是 OrePlacements.java:185 的 aboveBottom(80)（主世界 minY −64）加 ±4 半径
                OVERWORLD_BOTTOM, 16,
                Set.of(SeedOreWritePath.ORE_FEATURE, SeedOreWritePath.FOSSIL),
                // 238 起：受支持的矿物一律可进自动挖矿（见 autoMinerEligible 的类注释）
                true,
                "OreFeatures.java:109-112（尺寸 4/8/12/8 与丢弃率 0.5/0.5/0.7/1.0）；"
                        + "OrePlacements.java:184-204（count 7 / 2 / rarity 9 / 4，三角 −144~16、均匀 −64~−4）；"
                        + "BiomeDefaultFeatures.java:56-73；"
                        + "第二来源：CavePlacements.java:98-106 fossil_lower + ProcessorLists.java:607-611（煤矿规则换钻石）"));
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
                true,
                "OreFeatures.java:97-108（尺寸 8，无丢弃率）；"
                        + "OrePlacements.java:175-183（count 4 uniform(bottom,15)；count 8 triangle(aboveBottom(−32), aboveBottom(32))）；"
                        + "BiomeDefaultFeatures.java:56-73"));
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
                true,
                "OreFeatures.java:113-114（尺寸 7；埋藏型丢弃率 1.0）；"
                        + "OrePlacements.java:205-210（count 2 triangle(−32,32)；count 4 uniform(bottom,64)）；"
                        + "BiomeDefaultFeatures.java:56-73"));
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
                true,
                "OreFeatures.java:95-96（尺寸 9；埋藏型丢弃率 0.5）；"
                        + "OrePlacements.java:163-174（count 4 triangle(−64,32)；CountPlacement.of(UniformInt 0..1) uniform(−64,−48)；"
                        + "count 50 uniform(32,256)）；"
                        + "BiomeDefaultFeatures.java:76-78 与 OverworldBiomes.java:333（恶地）"));
        register(new SeedOreDefinition(OreType.IRON, SeedDimensionProfile.OVERWORLD,
                List.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE),
                List.of("ore_iron", "ore_iron_small"),
                List.of("ore_iron_upper", "ore_iron_middle", "ore_iron_small"),
                "上 90 次（三角 80 ~ 384）+ 中 10 次（三角 −24 ~ 56）+ 小 10 次（均匀 bottom ~ 72）",
                "三角 80 ~ 384；三角 −24 ~ 56；均匀 −64 ~ 72",
                "全部主世界生物群系（addDefaultOres）",
                "0（三段都不带空气暴露丢弃）",
                OVERWORLD_BOTTOM, OVERWORLD_TOP,
                // 矿脉：主世界 oreVeinsEnabled=true（NoiseGeneratorSettings.java:115-128），
                // 类型表 IRON → DEEPSLATE_IRON_ORE，y −60 ~ −8（OreVeinifier.java:63-65）
                Set.of(SeedOreWritePath.ORE_FEATURE, SeedOreWritePath.ORE_VEIN),
                true,
                "OreFeatures.java:93-94（尺寸 9 / 4）；"
                        + "OrePlacements.java:154-162（count 90 / 10 / 10）；"
                        + "BiomeDefaultFeatures.java:56-73；"
                        + "第二来源（NOISE 阶段矿脉）：NoiseChunk.java:166-168 + OreVeinifier.java:63-65"));
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
                true,
                "OreFeatures.java:145-146（尺寸 10 / 20；小簇键名原生拼写 ORE_COPPPER_SMALL）；"
                        + "OrePlacements.java:226-231（count 16 / 16）；"
                        + "BiomeDefaultFeatures.java:56-73；"
                        + "第二来源（NOISE 阶段矿脉）：NoiseChunk.java:166-168 + OreVeinifier.java:63-65（COPPER_ORE，y 0~50）"));
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
                true,
                "OreFeatures.java:91-92（尺寸 17；埋藏型丢弃率 0.5）；"
                        + "OrePlacements.java:148-153（count 30 uniform(absolute(136), top())；count 20 triangle(0,192)）；"
                        + "BiomeDefaultFeatures.java:56-73"));
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
                true,
                "OreFeatures.java:127-138（尺寸 3）；"
                        + "OrePlacements.java:214-216（count 100 triangle(−16,480)）；"
                        + "BiomeDefaultFeatures.java:80-82 addExtraEmeralds；"
                        + "调用点 OverworldBiomes.java:190（风袭丘陵）/ 770（草甸与樱花树林）/ 800（三类山峰）/ 847（雪坡）/ 870（雪林）"));
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
                true,
                "OreFeatures.java:139-144（Feature.SCATTERED_ORE，尺寸 3 / 2，丢弃率 1.0）；"
                        + "OrePlacements.java:217-225（大簇 InSquare+triangle(8,24)+BiomeFilter，无 CountPlacement；"
                        + "小簇 InSquare+RANGE_8_8+BiomeFilter）；"
                        + "PlacementUtils.java:37；ScatteredOreFeature.java:23-50（±7 散布）；"
                        + "BiomeDefaultFeatures.java:417-420 与 NetherBiomes.java:147（三角洲）"));
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
                true,
                "OreFeatures.java:82（netherrack 目标，尺寸 14）；"
                        + "OrePlacements.java:116,118（count 32 / 16，RANGE_10_10）；"
                        + "PlacementUtils.java:36（RANGE_10_10 = uniform(aboveBottom(10), belowTop(10))）；"
                        + "BiomeDefaultFeatures.java:409-415 与 NetherBiomes.java:145（三角洲）"));
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
                true,
                "OreFeatures.java:81（netherrack 目标，尺寸 10）；"
                        + "OrePlacements.java:115,117（count 20 / 10，RANGE_10_10）；"
                        + "PlacementUtils.java:36；"
                        + "BiomeDefaultFeatures.java:409-415 与 NetherBiomes.java:145（三角洲）"));
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
     * 该矿物是否可以在本维度进入自动挖矿（<b>静态资格</b>）。
     *
     * <p><b>238 的口径（用户 2026-09-24 指令）</b>：与自动挖矿的设置口径一致 ——
     * <b>本维度受支持的矿物一律可进自动挖矿</b>，一次只追一种（该矿的深层变种算同一种）。
     * 因此 11 条定义全部为 {@code true}，本方法等于「该（维度, 矿物）组合是否受支持」。</p>
     *
     * <p><b>为什么静态全开不会失去安全性</b>：真正决定「此刻能不能挖」的不是这张静态表，
     * 而是运行期两道门（{@link com.yiyiaddon.seed.service.SeedMiningService#mayUseForAutomatedMining(OreType)}）：</p>
     * <ol>
     *     <li><b>种子验证</b>：当前会话的验证必须是「已验证」（证据绑定世界 / 种子 / 维度 / 会话，
     *         换服换维度立刻作废）；</li>
     *     <li><b>证据覆盖该矿物</b>：本次会话的验证证据里必须出现过<b>正要追的那种矿</b>
     *         （只算过钻石的会话不能给红石背书）。</li>
     * </ol>
     *
     * <p>236/237 时期这两道门之一被写死在维度上（下界恒 false），238 一并解除：
     * 下界与主世界同口径，是否放行由<b>下界自己的验证证据</b>决定。</p>
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
