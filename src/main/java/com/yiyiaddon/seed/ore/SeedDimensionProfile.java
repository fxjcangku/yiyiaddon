package com.yiyiaddon.seed.ore;

import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * 种子挖矿正式模块 · <b>维度档案</b>（正式化第八阶段 236）。
 *
 * <p><b>为什么必须有它</b>：把「下界」当成「主世界换个 Y 范围」是错的。离线 worldgen 上下文
 * （{@code OfflineWorldgenContext}）的四件东西里有<b>三件</b>按维度不同：</p>
 *
 * <ol>
 *     <li><b>生物群系预设</b>：{@code MultiNoiseBiomeSourceParameterLists.java:10-11}
 *         —— {@code NETHER} / {@code OVERWORLD} 两个各自独立的预设，下界只有 5 个生物群系；</li>
 *     <li><b>噪声设置</b>：{@code NoiseGeneratorSettings.java:49,54} —— {@code OVERWORLD} / {@code NETHER}，
 *         其中<b>随机源算法都不同</b>（见下）；</li>
 *     <li><b>地形与建筑阶段的实际行为</b>：下界的 generator 设置里
 *         {@code aquifersEnabled=false}、{@code oreVeinsEnabled=false}、{@code useLegacyRandomSource=true}
 *         （{@code NoiseGeneratorSettings.java:99-113} 的 {@code nether(...)} 实参 vs
 *         {@code :115-128} 的 {@code overworld(...)}），而 {@code RandomState.create(...)}
 *         <b>就是按这个布尔选随机算法的</b>（{@code NoiseChunk.java:166-168} 的矿脉分支同理）。</li>
 * </ol>
 *
 * <p><b>最要命的一条</b>：下界 {@code useLegacyRandomSource=true}，也就是下界的 {@code RandomState}
 * 走<b>LEGACY</b> 随机算法而不是 XOROSHIRO。若沿用主世界设置去算下界，结果会整体错 ——
 * 而且错得「看起来像正常数字」，不会自己报错。这就是本档案独立存在的根本理由。</p>
 *
 * <p><b>它只声明与种子无关的环境参数</b>（预设 / 噪声设置 / 维度高度），
 * 不声明任何矿物、不含任何矿石参数：矿物定义全部在 {@link SeedOreRegistry}。
 * 维度不是本档案里列出的两个之一时，{@link #of(ResourceKey)} 返回 {@code null}，
 * 上层一律 fail-closed（不支持 = 不预测），绝不用主世界参数顶替。</p>
 */
public enum SeedDimensionProfile {

    /**
     * 主世界：{@code DimensionTypes.java:45-52}（min_y = -64，height = 384）、
     * 预设 {@code MultiNoiseBiomeSourceParameterLists.OVERWORLD}（{@code :11}）、
     * 噪声设置 {@code NoiseGeneratorSettings.OVERWORLD}（{@code :49}，矿脉开、含水层开、非 legacy 随机）。
     */
    OVERWORLD("主世界", Level.OVERWORLD, MultiNoiseBiomeSourceParameterLists.OVERWORLD,
            NoiseGeneratorSettings.OVERWORLD, -64, 384),

    /**
     * 下界：{@code DimensionTypes.java:65-74}（min_y = 0，height = 256，logical_height = 128）、
     * 预设 {@code MultiNoiseBiomeSourceParameterLists.NETHER}（{@code :10}）、
     * 噪声设置 {@code NoiseGeneratorSettings.NETHER}（{@code :54}，矿脉关、含水层关、<b>legacy 随机</b>）。
     */
    NETHER("下界", Level.NETHER, MultiNoiseBiomeSourceParameterLists.NETHER,
            NoiseGeneratorSettings.NETHER, 0, 256);

    /** 中文显示名。 */
    private final String displayNameCn;

    /** 维度键（与 {@code Level#dimension()} 同源）。 */
    private final ResourceKey<Level> levelKey;

    /** 生物群系源预设键。 */
    private final ResourceKey<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList> biomeSourcePreset;

    /** 噪声设置键。 */
    private final ResourceKey<NoiseGeneratorSettings> noiseSettings;

    /** 维度最低可建造 Y（来自维度类型；只用于一致性核对与文档）。 */
    private final int minY;

    /** 维度总高度（来自维度类型；只用于一致性核对与文档）。 */
    private final int height;

    SeedDimensionProfile(String displayNameCn, ResourceKey<Level> levelKey,
                         ResourceKey<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList> preset,
                         ResourceKey<NoiseGeneratorSettings> noiseSettings, int minY, int height) {
        this.displayNameCn = displayNameCn;
        this.levelKey = levelKey;
        this.biomeSourcePreset = preset;
        this.noiseSettings = noiseSettings;
        this.minY = minY;
        this.height = height;
    }

    /** 中文显示名（主世界 / 下界）。 */
    public String displayNameCn() {
        return displayNameCn;
    }

    /** 维度键。 */
    public ResourceKey<Level> levelKey() {
        return levelKey;
    }

    /** 维度标识（{@code minecraft:overworld} 形态，IPC 里用它传字符串）。 */
    public String dimensionId() {
        return levelKey.identifier().toString();
    }

    /** 生物群系源预设键。 */
    public ResourceKey<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList> biomeSourcePreset() {
        return biomeSourcePreset;
    }

    /** 噪声设置键（{@code RandomState.create} 也用它，随机算法因此跟着维度走）。 */
    public ResourceKey<NoiseGeneratorSettings> noiseSettings() {
        return noiseSettings;
    }

    /** 维度最低可建造 Y。 */
    public int minY() {
        return minY;
    }

    /** 维度总高度（方块数）。 */
    public int height() {
        return height;
    }

    /** 最高可建造 Y（含）。 */
    public int maxY() {
        return minY + height - 1;
    }

    /**
     * 由维度标识字符串取档案。
     *
     * @return 档案；不认识的维度返回 {@code null}（调用方必须 fail-closed）
     */
    public static SeedDimensionProfile of(String dimensionId) {
        for (SeedDimensionProfile profile : values()) {
            if (profile.dimensionId().equals(dimensionId)) {
                return profile;
            }
        }
        return null;
    }

    /** 由维度键取档案；不认识的维度返回 {@code null}。 */
    public static SeedDimensionProfile of(ResourceKey<Level> dimension) {
        if (dimension == null) {
            return null;
        }
        return of(dimension.identifier().toString());
    }

    /**
     * 由维度键取档案，取不到即抛异常。
     *
     * <p>用于「调用方已经声明只会在受支持维度里调用」的路径：宁可当场炸掉，
     * 也不允许悄悄退回主世界参数（那会算出「看起来正常但全错」的下界结果）。</p>
     */
    public static SeedDimensionProfile requireOf(ResourceKey<Level> dimension) {
        SeedDimensionProfile profile = of(dimension);
        if (profile == null) {
            throw new IllegalArgumentException("该维度没有离线 worldgen 档案（不支持预测）："
                    + (dimension == null ? "null" : dimension.identifier()));
        }
        return profile;
    }

    /**
     * 宿主维度类型是否与本档案一致（min Y / 高度）。
     *
     * <p>预测不依赖真实世界的方块，但<b>依赖它的世界高度</b>：高度不同意味着
     * {@code isOutsideBuildHeight} 的截断位置不同，矿石的可见 Y 范围也就不同。
     * 因此上层必须核对这一条，不一致即视为「不是原版该维度」并 fail-closed。</p>
     */
    public boolean matchesHost(Level level) {
        Objects.requireNonNull(level, "level");
        return level.getMinY() == minY && level.getHeight() == height;
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return displayNameCn + "（" + dimensionId() + "，y ∈ [" + minY + ", " + maxY() + "]，"
                + "预设 " + biomeSourcePreset.identifier() + "，噪声设置 " + noiseSettings.identifier() + "）";
    }

    /** 全部受支持维度的中文清单（错误提示用）。 */
    public static String describeAllCn() {
        StringBuilder builder = new StringBuilder();
        for (SeedDimensionProfile profile : values()) {
            if (builder.length() > 0) {
                builder.append(" / ");
            }
            builder.append(profile.displayNameCn).append('（').append(profile.dimensionId()).append('）');
        }
        return builder.toString();
    }
}
