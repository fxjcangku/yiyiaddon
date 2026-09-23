package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.model.OreType;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 PoC · 实验参数（系统属性）。
 *
 * <p>刻意不做成界面、不做成指令、不落盘：本阶段只证明算法，任何玩家可见入口都属于后续阶段
 * （用户 2026-09-22 拍板）。参数一律从 JVM 系统属性读，缺失即回落默认值。</p>
 *
 * <p>属性清单：</p>
 * <ul>
 *     <li>{@code -Dyiyiaddon.seedpoc.enabled=1} —— 总开关，不开则整包不挂载；</li>
 *     <li>{@code -Dyiyiaddon.seedpoc.seed=<long>} —— 指定被试种子；不给则取当前单人世界的真实种子；</li>
 *     <li>{@code -Dyiyiaddon.seedpoc.chunks=cx,cz;cx,cz;…} —— 固定测试区块；不给则用默认清单；</li>
 *     <li>{@code -Dyiyiaddon.seedpoc.terrain=0} —— 关掉噪声地形探针（默认开）。</li>
 * </ul>
 */
public final class SeedPocFlags {

    private static final String PREFIX = "yiyiaddon.seedpoc.";

    /** 默认测试区块：固定且多样（含原点附近与远离原点，避免只测一个区块）。 */
    private static final String DEFAULT_CHUNKS = "0,0;1,0;0,1;-3,2;8,-5";

    /**
     * 第五轮默认目标区块：<b>必须远离世界出生点</b>。
     *
     * <p>理由是本轮的方法论前提：原版建世界时会先算出出生点，并在出生点周围 ±5 区块内逐个试放玩家
     * （{@code MinecraftServer#setInitialSpawn:529}），随后玩家视图距离内的区块也会被生成成 FULL。
     * 也就是说<b>出生点附近的区块在实验驱动器启动之前就已经装饰完了</b>，请求顺序根本轮不到我们控制
     * ——这正是第四轮「目标区块在出生点附近、装饰批号 44~53」的成因。
     * 要真正控制顺序，目标区块必须落在驱动开始时「从未被请求过」的位置，所以本轮取远端的固定区块。</p>
     */
    private static final String DEFAULT_ORDER_TARGETS = "3000,3000;-3200,2900";

    private SeedPocFlags() {
    }

    /** 总开关。 */
    public static boolean enabled() {
        return "1".equals(System.getProperty(PREFIX + "enabled", "").trim());
    }

    /** 被试种子；未指定返回 null（由调用方取单人世界真实种子）。 */
    public static Long seedOverride() {
        String raw = System.getProperty(PREFIX + "seed", "").trim();
        if (raw.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /** 固定测试区块清单（区块坐标）。 */
    public static List<long[]> chunks() {
        return parseChunks(System.getProperty(PREFIX + "chunks", DEFAULT_CHUNKS));
    }

    /**
     * 探针需要捕获的区块清单：顺序实验时 = 顺序实验的目标区块，否则 = 常规测试区块。
     *
     * <p>第五轮的顺序实验只关心目标 3x3 的装饰批号，而 {@code GenStageCapture#prepareRegion}
     * 会把清单里每个区块展开成 ±1，因此这里给「目标区块」就够。</p>
     */
    public static List<long[]> captureChunks() {
        if (round6() && !round6Legacy()) {
            // 第六轮的判据只依赖「最终世界真值」与纯 Seed 离线预测，正常不采真实生成期快照；
            // 只有诊断开关 round6.probe=1 打开时才按当前阶段的目标区块采逐阶段 checkpoint
            // （用来定位 FIRST DIVERGENCE 落在哪个生成阶段）
            if (!round6Probe()) {
                return List.of();
            }
            return switch (round6Stage()) {
                case "regression" -> round6RegressionTargets();
                case "generalize" -> round6GeneralizeTargets();
                default -> round6FixedTargets();
            };
        }
        return orderScenario() == null ? chunks() : orderTargets();
    }

    /** 把 {@code cx,cz;cx,cz;…} 解析成区块坐标清单（非法项直接跳过，不让一个手误毁掉整轮实验）。 */
    private static List<long[]> parseChunks(String raw) {
        List<long[]> result = new ArrayList<>();
        for (String piece : raw.split(";")) {
            String[] xy = piece.trim().split(",");
            if (xy.length != 2) {
                continue;
            }
            try {
                result.add(new long[]{Long.parseLong(xy[0].trim()), Long.parseLong(xy[1].trim())});
            } catch (NumberFormatException ignored) {
                // 非法项直接跳过
            }
        }
        return result;
    }

    /** 是否运行噪声地形探针。 */
    public static boolean terrainProbe() {
        return !"0".equals(System.getProperty(PREFIX + "terrain", "1").trim());
    }

    /**
     * 被试种子；三者优先级：系统属性 → 固定测试种子。
     *
     * <p>固定测试种子是「已知种子测试世界」的默认值——不给属性时也必须每次都是同一个种子，
     * 否则实验不可重复（用户要求：固定已知 Seed + 固定 Chunk）。</p>
     */
    public static long fixedTestSeed() {
        Long override = seedOverride();
        return override != null ? override : 20260922L;
    }

    /** 是否允许实验自行新建测试世界（默认允许；设 0 则需先手动进一个世界）。 */
    public static boolean autoCreateWorld() {
        return !"0".equals(System.getProperty(PREFIX + "autocreate", "1").trim());
    }

    /** 实验结束（报告已落盘）后是否自动退出客户端，便于脚本化重复运行。 */
    public static boolean exitWhenDone() {
        return "1".equals(System.getProperty(PREFIX + "exit", "0").trim());
    }

    /**
     * 实验口径是否参与本轮对照。
     *
     * <p>键：{@code old}（旧口径回归基线）/ {@code legacyExtra}（旧口径的三种补充）/
     * {@code snapOres}（快照输入·只重放矿步骤）/ {@code snapAll}（快照输入·重放全部前置 feature 步骤）/
     * {@code snapPreDiamond}（第一条钻石 feature 前快照输入）/ {@code control}（装置自证对照）/
     * {@code single}（第三轮单 viewer 单次装饰）/ {@code phase2}（第二阶段跨区块贡献）。</p>
     *
     * <p><b>第三轮的默认值</b>：第二轮那五种大口径默认关闭（它们已被第二轮的实测否掉，
     * 且每跑一遍要几十秒），保留 {@code control} 与新增的 {@code single}/{@code phase2} 默认开启。
     * 需要回归旧口径时用 {@code -Dyiyiaddon.seedpoc.variant.old=1} 单独打开，不要改默认值。</p>
     */
    public static boolean variant(String key) {
        String fallback = SECOND_ROUND_VARIANTS.contains(key) ? "0" : "1";
        return !"0".equals(System.getProperty(PREFIX + "variant." + key, fallback).trim());
    }

    /** 第二轮的五种大口径（默认关闭，可单独打开做回归）。 */
    private static final java.util.Set<String> SECOND_ROUND_VARIANTS =
            java.util.Set.of("old", "legacyExtra", "snapOres", "snapAll", "snapPreDiamond");

    // ── 第四轮（仅凭 Seed 离线构造 pre-diamond）──────────────────────────────

    /** 是否运行第四轮离线构造（默认开；设 0 则回到第三轮口径）。 */
    public static boolean offline() {
        return !"0".equals(System.getProperty(PREFIX + "offline", "1").trim());
    }

    /**
     * 离线装饰范围（区块半径）：0 = 只装饰目标区块（邻域停在 CARVERS，即原版 pipeline 的最小要求）；
     * n&gt;0 = 先把半径 n 内的邻域按由近到远装饰掉，最后再装饰目标区块。
     *
     * <p>真实世界里服务端是「由近到远」生成区块的，目标区块被装饰时邻域往往已经跑过 FEATURES，
     * 所以这个值决定「离线复刻的是哪一种邻域年龄」——由比较器给出结论，不靠猜。</p>
     */
    public static int offlineDecorRadius() {
        String raw = System.getProperty(PREFIX + "offline.decorRadius", "0").trim();
        try {
            return Math.max(0, Math.min(3, Integer.parseInt(raw)));
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    /** 是否跑第四轮第二阶段（离线 pre-diamond → 钻石坐标），默认开。 */
    public static boolean offlineStageTwo() {
        return !"0".equals(System.getProperty(PREFIX + "offline.stageTwo", "1").trim());
    }

    /**
     * 离线世界是否跨 viewer 复用（默认 0 = 每个 viewer 新建一份独立世界）。
     *
     * <p>复用会让后一个 viewer 的邻域带上「前一个 viewer 装饰时写进去的方块」，与真实的
     * 「一个区块第一次被装饰」不符；保留这个开关是为了把「装饰顺序」这一项单独量化出来
     * （{@code -Dyiyiaddon.seedpoc.offline.reuse=1}，第四轮首轮用的就是这一口径）。</p>
     */
    public static boolean offlineReuseWorld() {
        return "1".equals(System.getProperty(PREFIX + "offline.reuse", "0").trim());
    }

    /**
     * 第四轮最多验几个 viewer（默认 0 = 全部）。
     *
     * <p>离线构造的代价是每层最多 19x19 区块（含结构起点），调试期用它把一轮压到几十秒，
     * 正式跑一轮仍用 0（全部 10 个固定区块），否则报告不成立。</p>
     */
    public static int offlineLimit() {
        String raw = System.getProperty(PREFIX + "offline.limit", "0").trim();
        try {
            return Math.max(0, Integer.parseInt(raw));
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    // ── 第五轮（Chunk 生成顺序决定性实验）────────────────────────────────────

    /**
     * 第五轮场景名（如 {@code A1}）；返回 null 表示不跑第五轮、走既有流程。
     *
     * <p>刻意不做成界面/指令：本轮仍属实验代码，入口只有系统属性
     * {@code -Dyiyiaddon.seedpoc.order=A1}。</p>
     */
    public static String orderScenario() {
        String raw = System.getProperty(PREFIX + "order", "").trim();
        return raw.isEmpty() ? null : raw;
    }

    /** 第五轮目标区块清单（每项 = 一个 3x3 的中心区块）。 */
    public static List<long[]> orderTargets() {
        return parseChunks(System.getProperty(PREFIX + "order.targets", DEFAULT_ORDER_TARGETS));
    }

    // ── 第六轮（Target 3×3 离线最终预测）────────────────────────────────────

    /** 第六轮固定集：Seed 20260922 的 10 个目标区块（第四轮沿用下来的原清单）。 */
    private static final String DEFAULT_ROUND6_TARGETS =
            "0,0;1,0;0,1;1,1;-1,0;0,-1;-1,-1;2,2;3,-1;-2,3";

    /** 第六轮第二阶段：Seed 12345 的 4 个旧回归区块（原样保留，禁止换掉失败案例）。 */
    private static final String DEFAULT_ROUND6_REGRESSION = "0,0;-1,-1;-25,17;120,-130";

    /**
     * 第六轮第三阶段：多地形目标区块。
     *
     * <p>刻意覆盖「原点附近 / 正坐标 / 负坐标 / 正负混合 / 远坐标」五种位置形态；
     * 地形类别不做预设（预设等于猜），改为在报告里逐个登记<b>实际取到的真实生物群系</b>。</p>
     */
    private static final String DEFAULT_ROUND6_GENERALIZE = "0,0;25,25;-30,40;17,-25;150,-160;-400,380";

    /** 是否运行第六轮（默认开；设 0 回到第五轮及以前的口径）。 */
    public static boolean round6() {
        return !"0".equals(System.getProperty(PREFIX + "round6", "1").trim());
    }

    /**
     * 第六轮阶段名：{@code fixed}（Seed 20260922 固定 10 目标）/ {@code regression}（Seed 12345 四区块）
     * / {@code generalize}（当前进程种子的多地形目标）。
     *
     * <p>非法值一律回落 {@code fixed}，避免一次手误把整轮实验静默跳过。</p>
     */
    public static String round6Stage() {
        String raw = System.getProperty(PREFIX + "round6.stage", "fixed").trim().toLowerCase(java.util.Locale.ROOT);
        return switch (raw) {
            case "regression", "generalize" -> raw;
            default -> "fixed";
        };
    }

    /** 第六轮固定集目标区块清单。 */
    public static List<long[]> round6FixedTargets() {
        return parseChunks(System.getProperty(PREFIX + "round6.targets", DEFAULT_ROUND6_TARGETS));
    }

    /** 第六轮 Seed 12345 回归区块清单。 */
    public static List<long[]> round6RegressionTargets() {
        return parseChunks(System.getProperty(PREFIX + "round6.regression", DEFAULT_ROUND6_REGRESSION));
    }

    /** 第六轮多 Seed 泛化目标区块清单。 */
    public static List<long[]> round6GeneralizeTargets() {
        return parseChunks(System.getProperty(PREFIX + "round6.generalize", DEFAULT_ROUND6_GENERALIZE));
    }

    /** 固定集阶段是否同时跑「只装饰目标自己」的旧口径复算（默认开，用来现场重建第四轮漏报）。 */
    public static boolean round6Baseline() {
        return !"0".equals(System.getProperty(PREFIX + "round6.baseline", "1").trim());
    }

    /** 固定集阶段是否跑缓存隔离实验（默认开）。 */
    public static boolean round6CacheIsolation() {
        return !"0".equals(System.getProperty(PREFIX + "round6.cache", "1").trim());
    }

    /** 第六轮模式下是否仍跑第二/三/四轮的重口径（默认关；开着只为回归旧数字）。 */
    public static boolean round6Legacy() {
        return "1".equals(System.getProperty(PREFIX + "round6.legacy", "0").trim());
    }

    /**
     * 第六轮诊断开关：是否在真实侧采「逐阶段 checkpoint」（默认关）。
     *
     * <p>开车后 {@link #captureChunks()} 会返回当前阶段的目标区块，于是真实的
     * {@code BIOMES / NOISE / SURFACE / CARVERS} 四个阶段的方块状态会被采下来，
     * 与离线侧同名 checkpoint 逐格比 —— 这是用户口径第十五节要求的
     * 「先定位 FIRST DIVERGENCE 落在哪一层，再继续」的唯一证据来源。</p>
     *
     * <p>默认关：采快照会改变真实生成的时序与耗时，正式数字必须在关掉它的前提下取得。</p>
     */
    public static boolean round6Probe() {
        return "1".equals(System.getProperty(PREFIX + "round6.probe", "0").trim());
    }

    // ── 第七轮（FEATURES 调度因果定案）──────────────────────────────────────

    /** 读一个系统属性（统一 trim，避免手误带空格把开关判错）。 */
    private static String prop(String key, String fallback) {
        return System.getProperty(PREFIX + key, fallback).trim();
    }

    /** 第七轮总开关（默认关；开着才走 {@code Round7Runner} 的专用阶段）。 */
    public static boolean round7() {
        return "1".equals(prop("round7", "0"));
    }

    /**
     * 第七轮逐格 FEATURES 写入 Journal（默认关）。
     *
     * <p>开着时会在真实生成期的 {@code WorldGenRegion#setBlock} 上记账：每一次真正落笔的写入
     * 取一个全局自增序号（进程内「哪次写先发生」的主排序依据），并记下 viewer / placed_feature /
     * 写入前后方块 / 是否跨区块 / 装饰批号。它<b>只做取证</b>，不参与任何预测计算。</p>
     */
    public static boolean round7Journal() {
        return "1".equals(prop("round7.journal", "0"));
    }

    /** 第七轮 OreFeature 候选点级诊断（默认关）：候选序号 / 写入前方块 / 是否可替换 / 是否消耗随机 / 是否被接受。 */
    public static boolean round7Ore() {
        return "1".equals(prop("round7.ore", "0"));
    }

    /** 第七轮 Journal / 候选点诊断覆盖的区块清单（复用第五轮的目标清单属性）。 */
    public static List<long[]> round7Targets() {
        return orderTargets();
    }

    /**
     * 第七轮 Debug-only 顺序重放：要重放的场景标签（形如 {@code A1}）；空 = 不跑重放。
     *
     * <p>它读的是同场景真实世界已经落盘的真值文件（{@link ChunkOrderTruth}），
     * 按真实观测到的装饰先后在共享离线世界里重放，用来做因果对照。</p>
     */
    public static String round7ReplayScenario() {
        String raw = prop("round7.replay", "");
        return raw.isEmpty() ? null : raw;
    }

    /**
     * 重放顺序的变异方式：{@code observed}（按真实观测顺序，默认）/ {@code reverse}（倒序）/
     * {@code first}（把 {@link #round7ReplayPivot()} 指定的 viewer 提到最前）/
     * {@code last}（挪到最后）。
     */
    public static String round7ReplayMutate() {
        return prop("round7.replay.mutate", "observed").toLowerCase(java.util.Locale.ROOT);
    }

    /** 变异方式要操作的那个 viewer（相对目标区块的偏移 {@code dx,dz}）；缺省 {@code 0,0}。 */
    public static int[] round7ReplayPivot() {
        String raw = prop("round7.replay.pivot", "0,0");
        String[] parts = raw.split(",");
        try {
            return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
        } catch (RuntimeException ignored) {
            return new int[]{0, 0};
        }
    }

    /**
     * 第五轮顺序场景的显式请求偏移表（覆盖字母场景），形如 {@code -2,0;0,0}；空 = 用字母场景。
     *
     * <p>第七轮需要「把某两个 writer 的真实先后调过来」，而字母场景只有四组固定偏移，
     * 覆盖不到任意两个邻区块，因此增加这个显式入口——请求方式仍然是原版
     * {@code ServerLevel#getChunk}，没有任何伪造装饰。</p>
     */
    public static List<long[]> orderExplicit() {
        String raw = prop("order.explicit", "");
        return raw.isEmpty() ? null : parseChunks(raw);
    }

    /**
     * 顺序实验要额外打印「最终方块状态」的探针坐标（形如 {@code -6417,-48,6123}）；空 = 不打。
     *
     * <p>234.1 的 MISSING 补证要用：三个全新世界跑不同合法请求顺序后，逐个世界读<b>同一格</b>
     * 的最终 BlockState，才能说清「这一格随合法请求顺序变化」还是「三个世界都一样」。</p>
     */
    public static int[] orderProbePos() {
        String raw = prop("order.probe", "");
        if (raw.isEmpty()) {
            return null;
        }
        String[] parts = raw.split(",");
        if (parts.length != 3) {
            return null;
        }
        try {
            return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim())};
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /** 是否在第七轮里输出「同格多 writer 冲突清单」（默认开）。 */
    public static boolean round7Conflicts() {
        return !"0".equals(prop("round7.conflicts", "1"));
    }

    /**
     * 第七轮阶段：{@code case}（单次真实世界全量取证 + Debug 重放，默认）/ {@code correlate}（跨运行相关性）。
     */
    public static String round7Stage() {
        String raw = prop("round7.stage", "case").toLowerCase(java.util.Locale.ROOT);
        return "correlate".equals(raw) ? "correlate" : "case";
    }

    /** 第七轮本次运行的标签（进文件名与报告：重复运行必须用不同标签，否则会互相覆盖）。 */
    public static String round7Label() {
        String raw = prop("round7.label", "R7A");
        return raw.isEmpty() ? "R7A" : raw;
    }

    /** 跨运行相关性的标签前缀（默认 = 本次标签，即把所有同名前缀的运行拿来两两比）。 */
    public static String round7Prefix() {
        String raw = prop("round7.prefix", round7Label());
        return raw.isEmpty() ? round7Label() : raw;
    }

    /**
     * 反事实重放要交换的一对 viewer（相对目标区块的偏移，形如 {@code -1,0;1,0}）；
     * 空 = 用「观测顺序整体倒序」作为反事实。
     */
    public static List<long[]> round7Swap() {
        String raw = prop("round7.swap", "");
        return raw.isEmpty() ? null : parseChunks(raw);
    }

    // ── 正式化第一阶段（报告 229：正式 Predictor 迁移回归）────────────────────

    /**
     * 正式化迁移回归总开关（默认关）。
     *
     * <p>开着时整轮实验换人：不跑第六/七轮的 PoC 流程，改为跑
     * {@link FormalSeedRegression}（正式 Predictor vs PoC Predictor 逐 BlockPos 对照 +
     * Seed 2 争议位置分类 + 会话隔离 + 缓存复用），这正是「正式层不依赖 dev 层、
     * dev 层可以调用正式层做回归」的体现。</p>
     */
    public static boolean formal() {
        return "1".equals(prop("formal", "0"));
    }

    /** 正式化回归里 Seed 2 争议样本的种子（默认 2，即 228 报告的受控顺序实验用的那一个）。 */
    public static long formalConflictSeed() {
        String raw = prop("formal.conflict.seed", "2");
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return 2L;
        }
    }

    /** 正式化回归里 Seed 2 争议样本的区块清单（默认 (-400,380)，争议格 (-6385,-59,6085) 所在区块）。 */
    public static List<long[]> formalConflictTargets() {
        return parseChunks(prop("formal.conflict.targets", "-400,380"));
    }

    // ── 正式化第二阶段（报告 230：服务层回归）─────────────────────────────────

    /**
     * 服务层回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link ServiceRegression}：从<b>客户端线程</b>调用
     * {@code SeedMiningService} 的公开 API，核对第二阶段（配置 / 维度 / 异步预测 / 取消与清理）
     * 有没有让 229 已冻结的数字退化。它不走服务端线程，因为被验收的正是
     * 「界面与 tick 在客户端线程、世界生成在后台线程」这套线程模型。</p>
     */
    public static boolean serviceRegression() {
        return "1".equals(prop("service", "0"));
    }

    // ── 正式化第四阶段（报告 232：本地隔离世界生成 Worker）────────────────────

    /**
     * Worker 对照开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link WorkerParityRegression}：Worker 侧从客户端线程走
     * {@code SeedMiningService} 公开 API（与界面按钮同一条路径），Oracle 侧用集成服务端的
     * {@code ServerLevel} 直接构造正式预测器，两侧<b>逐项</b>比较完整 PredictionResult。</p>
     */
    public static boolean workerParity() {
        return "1".equals(prop("workerParity", "0"));
    }

    /**
     * 多人验收开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link WorkerMultiplayerRegression}：要求客户端连着一个真正的专用服务器
     * （{@code getSingleplayerServer() == null}），核对固定数字与「远端未加载区块也能预测」。</p>
     */
    public static boolean workerMultiplayer() {
        return "1".equals(prop("workerMultiplayer", "0"));
    }

    /**
     * Worker 进程生命周期与崩溃恢复开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link WorkerLifecycleRegression}：先拿到基线预测，再从本进程的
     * 子进程里强杀计算器，核对「自动进失败态 → 下一次点击自动重启一次 → 退世界无残留」。</p>
     */
    public static boolean workerLifecycle() {
        return "1".equals(prop("workerLifecycle", "0"));
    }

    // ── 正式化第五阶段（报告 233：实际 Chunk 观察 + 世界渲染）──────────────────

    /**
     * 实际 Chunk 观察与渲染回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link ObservationRenderRegression}：在<b>真实客户端 + 真实专用服务器</b>上
     * 验证「已确认 / 当前缺失 / 恢复 / 区块卸载回未观察」四条实机证据（口径第四十四~四十七节）。
     * 它需要 OP（要发 {@code /setblock} 与 {@code /tp}）。</p>
     */
    public static boolean observation() {
        return "1".equals(prop("observation", "0"));
    }

    /** 观察回归的被试种子（默认 20260922，与冻结回归同一颗）。 */
    public static long observationSeed() {
        String raw = prop("observation.seed", "20260922");
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return 20260922L;
        }
    }

    /** 观察回归的覆盖半径（默认 1：只要玩家所在区块这一圈，跑得快且证据足够）。 */
    public static int observationRadius() {
        String raw = prop("observation.radius", "1");
        try {
            return Math.max(1, Math.min(6, Integer.parseInt(raw)));
        } catch (NumberFormatException ignored) {
            return 1;
        }
    }

    /** 观察回归是否跑「方块更新 → 当前缺失 → 恢复」用例（默认开）。 */
    public static boolean observationMutate() {
        return !"0".equals(prop("observation.mutate", "1"));
    }

    /** 观察回归是否跑「远距离传送 → 区块卸载 → 未观察」用例（默认开）。 */
    public static boolean observationUnload() {
        return !"0".equals(prop("observation.unload", "1"));
    }

    /**
     * Server A→B 换服残留用例的目标服务器地址（空 = 不跑）。
     *
     * <p>配了就要求另起一台地址不同的服务器（本仓库用 {@code runSeedWorkerServerB}，端口 25566）：
     * 主用例跑完后装置会自动断开 A 并连 B，在进入 B 的第一刻核对「A 的预测 / 观察 / 渲染残留全为 0」。</p>
     */
    public static String observationServerB() {
        return prop("observation.serverB", "");
    }

    /**
     * 覆盖 / 观察组合回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedCoverageRegression}：在真实专用服务器上顺序跑三段
     * ——「客户端种子与服务器世界不符 → 大量当前缺失（且绝不产生可疑）」、
     * 「调度敏感候选（种子 2 · 争议位置）观察后仍保持调度敏感」、
     * 「默认范围 3（49 目标）的覆盖规模与渲染开销读数」。</p>
     */
    public static boolean coverageRegression() {
        return "1".equals(prop("coverageRegression", "0"));
    }

    /**
     * 种子挖矿关闭态回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedOffRegression}：进入真实服务器后一个刻都不碰任何界面开关，
     * 核对「没开种子挖矿 = 不启动计算器 / 不做覆盖 / 缓存为空 / 观察为空 / 渲染快照为空 / 世界里无预测框」。</p>
     */
    public static boolean offRegression() {
        return "1".equals(prop("offRegression", "0"));
    }

    // ── 正式化第六阶段（报告 234：种子验证 + 可疑语义定案）────────────────────

    /**
     * 种子验证回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedValidationRegression}：在<b>真实客户端 + 真实专用服务器</b>
     * （种子 {@code 20260922}，账号需 OP）上顺序跑：</p>
     * <ol>
     *     <li>正确种子 → 多目标区块收集 → 必须进入「已验证」，并记录用了多少区块 / 多少独立证据组；</li>
     *     <li>被挖矿容错 → dev-only 用 {@code /setblock air} 逐批移除已确认钻石（20% / 再 20%），
     *         验证必须保持「已验证」；</li>
     *     <li>策略级合成用例 → 单区块 45/45 确认、只有一个独立组：都<b>不得</b>进入已验证；</li>
     *     <li>错误种子（12345 / 2 / 0 / -7777）→ 全部<b>不得</b>进入已验证，并记录各自读数；</li>
     *     <li>改种子 / 换服务器 / 换维度 → 验证必须立刻清空（口径第四十七~五十节）。</li>
     * </ol>
     */
    public static boolean validationRegression() {
        return "1".equals(prop("validationRegression", "0"));
    }

    /** 验证回归的被试种子（默认 20260922，与冻结回归同一颗）。 */
    public static long validationSeed() {
        String raw = prop("validation.seed", "20260922");
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return 20260922L;
        }
    }

    /** 验证回归的覆盖半径（默认 3：与出厂默认一致，能覆盖多个目标区块）。 */
    public static int validationRadius() {
        String raw = prop("validation.radius", "3");
        try {
            return Math.max(1, Math.min(6, Integer.parseInt(raw)));
        } catch (NumberFormatException ignored) {
            return 3;
        }
    }

    /**
     * 验证回归「换服务器」用例的目标服务器地址（空 = 不跑）。
     *
     * <p>配了就要求另起一台地址不同的服务器（本仓库用 {@code runSeedWorkerServerB}，端口 25566）：
     * 主用例跑完后装置会自动断开 A 并连 B，在进入 B 的第一刻核对「A 的验证结论与证据全部归零」。</p>
     */
    public static String validationServerB() {
        return prop("validation.serverB", "");
    }

    /** 验证回归被挖矿容错用例的移除比例（百分比；默认 20，即先 20% 再 20%）。 */
    public static int validationMinePercent() {
        String raw = prop("validation.minePercent", "20");
        try {
            return Math.max(0, Math.min(50, Integer.parseInt(raw)));
        } catch (NumberFormatException ignored) {
            return 20;
        }
    }

    /**
     * 233 遗留「1 个 MISSING」定位开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedMissingCandidateRegression}：连到种子 {@code 20260922} 的
     * 专用服务器，传送到 233 覆盖回归第三段的位置（{@code -6385,-59,6085}，覆盖中心区块
     * {@code -400,380}），把「当前缺失」那一格逐字段打出来，并做「卸载重载 / 断开重连」两次复现。</p>
     */
    public static boolean missingCandidateRegression() {
        return "1".equals(prop("missingCandidate", "0"));
    }

    /**
     * ESP / UI 目视验收装置开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedVisualAcceptance}：它把画面稳定停在五个状态上
     * （正坐标 / 负坐标 / 打开显示缺失 / 关闭渲染 / 种子挖矿控制台页），每个状态停留 15 秒并打时间戳，
     * 供人工或截图脚本取画面。</p>
     */
    public static boolean visualAcceptance() {
        return "1".equals(prop("visual", "0"));
    }

    /**
     * 半径 6 压力烟测开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedRadius6Smoke}：把覆盖范围开到允许的最大值 6（13 × 13 =
     * 169 个目标区块），记录铺开耗时 / 候选规模 / 渲染条目 / 帧率 / 卡顿 / 内存 / 计算器是否持续工作，
     * 只做观察与记录，不改默认范围、不改渲染器（口径第五十六、五十七节）。</p>
     */
    public static boolean radius6Smoke() {
        return "1".equals(prop("radius6", "0"));
    }

    // ── 正式化第七阶段（报告 235：钻石 Seed Target → AutoMiner 正式接入）────────

    /**
     * 目标接入回归开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedTargetRegression}：在真实客户端 + 固定种子夹具世界上，
     * 把「种子目标模式」真正接进现有自动挖矿并逐条核对 A~L 十二项（普通模式零回归 / 未验证
     * fail-closed / 选定真实候选 / 目标未加载能导航 / 到位真挖掉 / MISSING 立刻换颗 /
     * 手动 setblock air 不卡死 / 关 ESP 不影响 / 重开 ESP 恢复 / 换维度清空 / 寻路失败不无限重试 /
     * 挖掉一颗换下一颗）。</p>
     */
    public static boolean targetRegression() {
        return "1".equals(prop("target", "0"));
    }

    /** 目标接入回归的被试种子（默认 20260922，与冻结回归同一颗）。 */
    public static long targetSeed() {
        String raw = prop("target.seed", "20260922");
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return 20260922L;
        }
    }

    /** 目标接入回归的覆盖半径（默认 3：与出厂默认一致）。 */
    public static int targetRadius() {
        String raw = prop("target.radius", "3");
        try {
            return Math.max(1, Math.min(6, Integer.parseInt(raw)));
        } catch (NumberFormatException ignored) {
            return 3;
        }
    }

    /**
     * 目标接入回归<b>追哪一种矿物</b>（238；默认 {@code DIAMOND}，与 235 逐字一致）。
     *
     * <p>多了这个开关，同一套 A~L 装置就能逐个矿物跑一遍 —— 238 把自动挖矿放开到全部受支持矿物，
     * 「除了钻石以外还能不能真的挖到」只能靠实机同一套用例去证。</p>
     *
     * <p>不认识的值一律回落钻石：装置的一次手误不该让整轮回归指向别的矿物。</p>
     */
    public static OreType targetRegressionOre() {
        OreType parsed = OreType.parse(System.getProperty(PREFIX + "target.ore", "DIAMOND").trim());
        return parsed == null ? OreType.DIAMOND : parsed;
    }

    // ── 正式化第八阶段（报告 236：Seed Ore Engine 通用化 + 其它矿物 + Nether）────

    /**
     * 多矿物 · 多维度实机矩阵开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link SeedOreMatrixRegression}：在真实客户端 + 固定种子的单人夹具世界上，
     * 对「主世界 8 种 + 下界 3 种」共 11 种矿物逐个取三类证据 —— Worker ↔ 单人 Oracle 逐项一致、
     * 候选 ↔ 真实方块（把目标区块推到 FULL 后逐格扫描）、以及观察 / ESP / 缓存隔离读数；
     * 并在中途真实传送进下界，核对身份切换清空与「下界自动挖矿 238 口径（下界专属验证）」。</p>
     */
    public static boolean oreMatrix() {
        return "1".equals(prop("oreMatrix", "0"));
    }

    /** 矩阵回归的被试种子（默认 20260922：与世界种子相同，因此预测可与真实区块逐个对照）。 */
    public static long oreMatrixSeed() {
        String raw = prop("oreMatrix.seed", "20260922");
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ignored) {
            return 20260922L;
        }
    }

    // ── 正式化第九阶段（报告 237：双版本合流与下界多人）──────────────────────────

    /**
     * 下界真正多人（Dedicated Multiplayer）验收开关（默认关）。
     *
     * <p>开着时整轮实验换成 {@link NetherMultiplayerRegression}：连到本机专用服务器
     * （端口 25865 / 种子 20260922 / allow-nether=true，服务端不加载 yiyiaddon），
     * 核对「主世界 → 下界身份切换清空 / 下界三种矿物预测 / 下界观察与 ESP /
     * 远端未加载区块可预测且不会导致客户端加载 / 下界自动挖矿 238 口径 / Worker 无孤儿」。</p>
     */
    public static boolean netherMultiplayer() {
        return "1".equals(prop("netherMultiplayer", "0"));
    }
}
