package com.yiyiaddon.dev.seedpoc;

import java.util.List;

/**
 * 种子挖矿 PoC · 常量表。
 *
 * <p><b>本包性质</b>：开发期一次性实验代码（对齐《开发习惯》第三十三章 DebugProbe 的处置口径：
 * 一案一份、结案整包删除）。它<b>不参与任何正式业务</b>，不订阅模块生命周期、不读写模块配置、
 * 不注册指令与界面；正式自动挖矿的 Java 文件一行未改（唯一的接触点是
 * {@code YiyiAddonClient} 里一行受系统属性守卫的挂载调用）。</p>
 *
 * <p>本类只放常量，不放逻辑（第 3 条：一个类一个职责）。</p>
 */
public final class SeedPocConstants {

    /** 日志与报告里的唯一中文关键词，便于一次检索到全部 PoC 输出。 */
    public static final String LOG_KEY = "种子挖矿PoC";

    /**
     * 本次实验的目标矿物：原版钻石矿的四个 placed_feature。
     *
     * <p>依据（26.1.2 本线源码）：{@code net/minecraft/data/worldgen/placement/OrePlacements.java:184-204}
     * 注册了 {@code ore_diamond} / {@code ore_diamond_medium} / {@code ore_diamond_large} /
     * {@code ore_diamond_buried}；数值定义见 {@code data/worldgen/features/OreFeatures.java:109-112}。</p>
     */
    public static final List<String> DIAMOND_PLACED_FEATURES = List.of(
            "ore_diamond", "ore_diamond_medium", "ore_diamond_large", "ore_diamond_buried");

    /**
     * 装饰阶段（{@code ChunkStatus.FEATURES}）允许的写入半径（区块）。
     *
     * <p>依据：{@code net/minecraft/world/level/chunk/status/ChunkPyramid.java:29-35} ——
     * {@code FEATURES} 这一步 {@code blockStateWriteRadius(1)}；因此区块中心的一次装饰过程
     * 可以把方块写进相邻一圈区块，复刻时必须给同样的写权限，否则会出现「原版会写、我们被拒」的错位。</p>
     */
    public static final int FEATURE_WRITE_RADIUS = 1;

    /**
     * 真值记录 / 清空 / 还原的范围半径（区块）。
     *
     * <p>中心区块的装饰过程写半径是 1，复刻时会对中心周围 3x3 个区块各跑一遍装饰过程，
     * 角落那几遍最高可以写到中心 ±2 区块，因此记录范围取 ±2（5x5）才能把所有写入点到的地方
     * 都纳入「清空—重放—还原」的闭环。</p>
     */
    public static final int REGION_RADIUS = 2;

    /** 报告落盘文件名（写在运行目录，即 run-&lt;MC 版本&gt;/ 下）。 */
    public static final String REPORT_FILE_NAME = "seedpoc-实验报告.txt";

    // ── 第二轮（真实生成期快照 PoC）新增常量 ────────────────────────────────

    /**
     * 阶段快照覆盖的区块半径。
     *
     * <p>依据：原版 {@code ChunkPyramid} 给 {@code FEATURES} 步骤的写半径是 1
     * （ChunkPyramid.java:29-35），且 {@code ChunkGenerator#applyBiomeDecoration} 读生物群系时
     * 用的就是 {@code ChunkPos.rangeClosed(center, 1)}（ChunkGenerator.java:330）；
     * 所以「一个区块装饰时要读的那个世界」就是它自己 ±1 的 3x3。</p>
     */
    public static final int SNAPSHOT_VIEW_RADIUS = 1;

    /**
     * 阶段快照纵向覆盖的 section 数（自世界最低 section 起算）。
     *
     * <p>取 9 个 section = 主世界 y ∈ [-64, 79]：钻石四条 placed_feature 的高度区间上界是
     * y = 16（{@code data/worldgen/features/OreFeatures.java:109-112}），本口径再往上留足
     * 地表与浅层地物（湖 / 紫水晶洞 / 滴水石 / 化石）所需的纵向余量。
     * 该范围之外的方块（例如 y &gt; 79 的煤矿 / 翡翠）在本轮不参与钻石判定，
     * 报告里必须如实标注这条边界。</p>
     */
    public static final int CAPTURE_SECTION_COUNT = 9;

    /** 口径0：旧口径（影子输入 = 已完全生成的世界），仅重放矿步骤——第二轮回归基线。 */
    public static final String MODE_OLD_WORLD = "口径0｜旧口径：输入=已完全生成的世界 · 只重放 underground_ores（回归基线）";

    /** 口径1：真实生成期快照输入，仅重放矿步骤。 */
    public static final String MODE_SNAPSHOT_ORES = "口径1｜生成期快照输入（装饰开始前）· 只重放 underground_ores";

    /** 口径2：真实生成期快照输入，重放矿步骤之前的全部 feature 步骤。 */
    public static final String MODE_SNAPSHOT_ALL_STEPS = "口径2｜生成期快照输入 · 重放 raw_generation…underground_ores 全部 feature 步骤";

    /** 口径3：第一条钻石 feature 执行前的快照输入，仅重放钻石四条。 */
    public static final String MODE_SNAPSHOT_PRE_DIAMOND = "口径3｜第一条钻石 feature 前快照输入 · 只重放钻石四条";

    /**
     * 对照口径一（装置自证）：安装装饰开始前快照后，把中心区块整片改写成紫水晶块（不可替换、非空气），再重放矿步骤。
     *
     * <p>期望：预测 = 0。若预测仍与基线完全相同，说明放置链路根本没有以被安装的世界状态为依据，
     * 那么本轮所有快照口径的结论都不成立，必须先修装置。</p>
     */
    public static final String MODE_CONTROL_AMETHYST = "对照1｜装置自证：安装快照后把中心区块改写成紫水晶块（不可替换）· 只重放矿步骤";

    /** 对照口径二（装置自证）：同上，但改写成石头（可替换）。期望：预测显著多于基线。 */
    public static final String MODE_CONTROL_STONE = "对照2｜装置自证：安装快照后把中心区块改写成石头（可替换）· 只重放矿步骤";

    // ── 第三轮（单 viewer / 单次装饰 oracle）新增常量 ─────────────────────────

    /**
     * 单次装饰 oracle 的标签：pre-diamond → post-diamond 差集 vs 只重放钻石四条。
     *
     * <p>第三轮只回答一个问题：同一个 viewer、同一次真实装饰、同一个 pre-diamond 输入状态下，
     * 当前 Seed / decorationSeed / featureSeed / FeatureSorter / PlacedFeature / OreFeature
     * 能不能 100% 复现原版这一遍真实写出来的钻石坐标。因此<b>不做 3x3 九遍聚合</b>，
     * 一遍一遍单独验证。</p>
     */
    public static final String MODE_SINGLE_DECORATION = "第三轮｜单 viewer 单次装饰：pre-diamond→post-diamond 差集 vs 只重放钻石四条";

    /** 第二阶段标签（只有第一阶段逐 BlockPos 100% 一致时才执行）：3x3 九个 viewer 的跨区块贡献。 */
    public static final String MODE_CROSS_CHUNK = "第二阶段｜跨区块贡献：viewer 这一遍写进目标区块的钻石（九遍单独比对）";

    /**
     * 钻石扫描的纵向 section 数（自世界最低 section 起算）。
     *
     * <p>依据：钻石四条 placed_feature 的高度区间上界是 y = 16
     * （{@code OrePlacements.java:184-204} 的 {@code aboveBottom(80)}，主世界 minY = -64），
     * 加上矿脉自身半径（{@code OreFeature.place:38-42} 的 {@code maxRadius}）最多再溢出几格；
     * 取 6 个 section = y ∈ [-64, 31] 是「一定覆盖 + 明显省算力」的取法。</p>
     */
    public static final int DIAMOND_SCAN_SECTION_COUNT = 6;

    private SeedPocConstants() {
    }
}
