package com.yiyiaddon.seed.model;

/**
 * 种子挖矿正式模块 · 矿物来源分类。
 *
 * <p><b>它要回答的将来问题</b>：「这块矿为什么被预测出来」——是普通的矿物地物（ore placed_feature）、
 * 结构自带方块、化石处理器，还是别的 worldgen 写入。</p>
 *
 * <p><b>26.1.2 主世界钻石的真实来源（已查本线源码，不是假设）</b>：</p>
 * <ol>
 *     <li>{@code net/minecraft/data/worldgen/placement/OrePlacements.java:49-52, 184-204}
 *         —— 注册了四条 {@code ore_diamond} / {@code ore_diamond_medium} / {@code ore_diamond_large} /
 *         {@code ore_diamond_buried}，值定义在
 *         {@code net/minecraft/data/worldgen/features/OreFeatures.java:64-65}；</li>
 *     <li>{@code net/minecraft/data/worldgen/placement/CavePlacements.java:31-32, 98-106}
 *         —— {@code fossil_lower} 使用 {@code CaveFeatures#FOSSIL_DIAMONDS}
 *         （{@code CaveFeatures.java:62, 155}），其处理器
 *         {@code ProcessorLists#FOSSIL_DIAMONDS}（{@code ProcessorLists.java:607-611}）
 *         把煤矿规则替换成 {@code deepslate_diamond_ore}。<b>它不是结构起点，而是一条 placed_feature。</b></li>
 * </ol>
 *
 * <p>因此「所有 diamond_ore 一定只来自四条普通 ore feature」是错的：主世界至少还有第二条路径。
 * 但两条路径都发生在同一次 {@code FEATURES} 里，逐格区分需要对每条 placed_feature 单独记账
 * （开发期探针 {@code FeatureWriteJournal} 才有这份能力；正式层 <b>禁止依赖 dev 层</b>）。
 * 所以本阶段正式预测器的处理是<b>明确记录未覆盖</b>：</p>
 *
 * <ul>
 *     <li>{@link #ORE_FEATURE} / {@link #STRUCTURE} / {@link #FOSSIL} / {@link #OTHER_WORLDGEN}
 *         是模型预留的分类；</li>
 *     <li>逐格归属未实现，预测出来的每一块矿都标 {@link #UNATTRIBUTED}——
 *         <b>不冒充已支持的来源</b>（正式化第一阶段口径第十一节）。</li>
 * </ul>
 */
public enum OreSource {

    /** 普通矿物地物（{@code ore_diamond*} 四条 placed_feature）。模型预留，本阶段不逐格产出。 */
    ORE_FEATURE("矿物地物"),

    /** 结构自带方块（结构模板里本来就是钻石矿的那类，主世界钻石不属此类）。模型预留。 */
    STRUCTURE("结构方块"),

    /** 化石：{@code fossil_lower} + {@code ProcessorLists.FOSSIL_DIAMONDS} 产出的钻石。模型预留。 */
    FOSSIL("化石"),

    /** 其它 worldgen 写入路径（地物处理器、结构处理器等未归类者）。模型预留。 */
    OTHER_WORLDGEN("其它世界生成"),

    /**
     * 来源未归属：本阶段预测器<b>能产出</b>这些矿物坐标，但<b>还不能逐格指认是哪条路径写的</b>。
     *
     * <p>这个名字本身就是一条声明：调用方不应对它做来源相关判断；等逐条 placed_feature 归属能力
     * 正式落地后，它会退化成不会出现的值。</p>
     */
    UNATTRIBUTED("来源未归属");

    /** 中文显示名。 */
    private final String displayNameCn;

    OreSource(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
