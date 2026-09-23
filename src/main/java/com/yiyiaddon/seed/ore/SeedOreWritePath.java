package com.yiyiaddon.seed.ore;

/**
 * 种子挖矿正式模块 · <b>矿物的写入路径</b>（正式化第八阶段 236；26.2 语义移植）。
 *
 * <p><b>它是什么</b>：一个矿物在本维度里<b>可能有几条完全不同的代码路径把方块写进世界</b>。
 * 这是从 26.2 源码里读出来的事实，不是分类学练习 —— 因为路径不同，
 * 「为什么这块矿会被预测出来」的答案就不同，而漏掉任何一条路径都会造成漏报。</p>
 *
 * <p><b>与 {@link com.yiyiaddon.seed.model.OreSource} 的区别</b>：本枚举是<b>设计期声明</b>
 * （这个矿物一共有哪几条写入路径，来源是源码）；{@code OreSource} 是<b>运行期逐格归属</b>
 * （这一格是哪条路径写的）。正式层目前不具备逐 placed_feature 归属能力，
 * 因此逐格仍然一律标 {@code UNATTRIBUTED}（见 {@code OreSource} 类注释）。</p>
 */
public enum SeedOreWritePath {

    /**
     * 普通矿物地物：{@code Feature.ORE} + {@code OreConfiguration}
     * （{@code OreFeature} 的椭圆体算法，尺寸与空气暴露丢弃率由配置给出）。
     */
    ORE_FEATURE("矿物地物"),

    /**
     * 散落地物：{@code Feature.SCATTERED_ORE}（{@code ScatteredOreFeature}）。
     *
     * <p>与 {@link #ORE_FEATURE} <b>不是同一套算法</b>：试放次数是 {@code rand(size+1)}，
     * 每次偏移三轴独立取 {@code round((rand-rand) * min(试放序号, 7))}，
     * 因此散布半径是 <b>±7</b> 而不是椭圆体的 {@code ±(2+maxRadius)}。远古残骸走这条。</p>
     */
    SCATTERED_ORE("散落地物"),

    /**
     * 化石处理器：{@code fossil_lower}（{@code CavePlacements}）+
     * {@code ProcessorLists#FOSSIL_DIAMONDS} 把煤矿规则替换成 {@code deepslate_diamond_ore}。
     *
     * <p>它<b>不是结构起点</b>，而是一条 placed_feature，因此和矿物地物同处一个 {@code FEATURES} 阶段。</p>
     */
    FOSSIL("化石处理器"),

    /**
     * 矿脉：{@code NoiseChunk} 在 {@code oreVeinsEnabled=true} 的维度里挂上的
     * {@code OreVeinifier}。
     *
     * <p><b>它发生在 NOISE 阶段而不是 FEATURES 阶段</b>，而且<b>下界整条关闭</b>
     * （{@code NoiseGeneratorSettings.nether(...)} 传 {@code false}）。
     * 主世界的铁（{@code deepslate_iron_ore}，y −60~−8）与铜（{@code copper_ore}，y 0~50）
     * 各有这一条来源，因此铁 / 铜的候选里本来就混着矿脉产物。</p>
     */
    ORE_VEIN("矿脉（NOISE 阶段）");

    /** 中文显示名。 */
    private final String displayNameCn;

    SeedOreWritePath(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
