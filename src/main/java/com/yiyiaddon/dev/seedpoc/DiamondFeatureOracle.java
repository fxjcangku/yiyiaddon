package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 单次装饰的钻石 oracle（第三轮核心）。
 *
 * <p><b>它回答什么</b>：原版 Minecraft <b>这一遍 viewer 装饰</b>到底把哪些钻石写进了世界。
 * 取值方式严格按用户拍板的口径：</p>
 * <ol>
 *     <li>在 {@code PlacedFeature#placeWithBiomeCheck} 的 <b>HEAD</b>（该 feature 是钻石四条之一）
 *         记下「执行前」的钻石集合；</li>
 *     <li>在同一个方法的 <b>RETURN</b> 记下「执行后」的钻石集合；</li>
 *     <li>两者之差 = <b>这一条钻石 placed_feature 本遍新增的钻石</b>；</li>
 *     <li>四条依次执行完后，最后那一次的「执行后」集合就是 <b>post-diamond</b>，
 *         而第一次的「执行前」集合就是 <b>pre-diamond</b>（等价于第二轮那份完整快照的钻石部分）。</li>
 * </ol>
 *
 * <p><b>为什么必须在 RETURN 而不是「下一个 feature 的 HEAD」取样</b>：钻石四条在
 * {@code UNDERGROUND_ORES} 步骤里与其它矿（煤 / 铁 / 铜 / 金 / 红石 / 青金石…）按全局索引混排，
 * 用「下一个 feature 的 HEAD」会在两条钻石之间夹进别的矿；而 RETURN 正好落在
 * 「这一条钻石刚刚写完、后面什么都没有跑」的那一刻，也就是用户要求的
 * 「四条钻石全部执行结束之后、后续非钻石 feature 尚未继续污染」。</p>
 *
 * <p><b>配对约束</b>：每条记录都带上 {@link GenStageCapture#currentPass(long)} 发出来的装饰批号；
 * 只有「同一个 viewer + 同一个批号」的 pre/post 才允许做差集，禁止跨 viewer、禁止跨批次。</p>
 *
 * <p>本类只做<b>记账</b>：不写世界、不改状态、不参与预测。所有扫描都包在 try/catch 里，
 * 异常只登记不上抛（绝不允许实验代码把世界生成搞崩）。</p>
 */
public final class DiamondFeatureOracle {

    /**
     * 一条钻石 placed_feature 在<b>一次装饰内</b>的完整记录。
     *
     * @param featurePath 注册表路径（如 {@code ore_diamond}）
     * @param order       在本遍装饰里的执行次序（0 起）
     * @param passId      装饰批号
     * @param before      HEAD 时刻的钻石集合（中心区块 ±1）
     * @param after       RETURN 时刻的钻石集合（中心区块 ±1）
     * @param added       {@code after - before}，即这一条 feature 本遍真正新增的钻石
     */
    public record FeatureStage(String featurePath, int order, int passId,
                               Set<BlockPos> before, Set<BlockPos> after, Set<BlockPos> added) {
    }

    /** 逐 viewer 的逐 feature 记录（按执行顺序）。 */
    private static final Map<Long, List<FeatureStage>> STAGES = Collections.synchronizedMap(new HashMap<>());

    /** 逐 viewer 的 post-diamond 集合（= 最后一条钻石 feature 的 RETURN 集合）。 */
    private static final Map<Long, Set<BlockPos>> POST_DIAMOND = Collections.synchronizedMap(new HashMap<>());

    /** 逐 viewer 的 post-diamond 批号。 */
    private static final Map<Long, Integer> POST_DIAMOND_PASS = Collections.synchronizedMap(new HashMap<>());

    /** HEAD 与 RETURN 之间的挂账（同一线程内 HEAD→RETURN 之间不会插入别的 feature）。 */
    private static final ThreadLocal<Pending> PENDING = new ThreadLocal<>();

    /** 捕获异常计数与首条异常。 */
    private static int failures;
    private static String firstFailure = "";

    /** HEAD 挂账。 */
    private record Pending(ChunkPos viewer, String featurePath, int passId, Set<BlockPos> before) {
    }

    private DiamondFeatureOracle() {
    }

    /**
     * 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD。
     *
     * <p>只有「钻石四条 + 落在捕获区域内 + 实验武装中」才真的取数；其余情况一次集合查询就返回。</p>
     */
    public static void onFeatureHead(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        PENDING.remove();
        if (!GenStageCapture.captureArmed() || !GenStageCapture.captureActive(level)) {
            return;
        }
        try {
            if (!GenStageCapture.isDiamondFeature(level, feature)) {
                return;
            }
            ChunkPos viewer = new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4);
            if (!GenStageCapture.containsViewer(viewer)) {
                return;
            }
            PENDING.set(new Pending(viewer, GenStageCapture.pathOf(level, feature),
                    GenStageCapture.currentPass(viewer.pack()), DiamondScanner.scan(level, viewer)));
        } catch (Throwable error) {
            recordFailure("diamond-head", origin, error);
            PENDING.remove();
        }
    }

    /**
     * 注入点：{@code PlacedFeature#placeWithBiomeCheck} 的 RETURN。
     *
     * <p>落账「这一条钻石 feature 新增了什么」，并把本次集合更新为 post-diamond。</p>
     */
    public static void onFeatureTail(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        Pending pending = PENDING.get();
        PENDING.remove();
        if (pending == null || !GenStageCapture.captureArmed()) {
            return;
        }
        try {
            ChunkPos viewer = new ChunkPos(origin.getX() >> 4, origin.getZ() >> 4);
            if (!viewer.equals(pending.viewer())) {
                // HEAD 与 RETURN 落在不同 viewer 上说明取证链断了：如实丢弃，绝不硬凑
                recordFailure("diamond-tail-mismatch", origin, new IllegalStateException("批内 viewer 不一致"));
                return;
            }
            Set<BlockPos> after = DiamondScanner.scan(level, viewer);
            Set<BlockPos> added = DiamondScanner.minus(after, pending.before());
            List<FeatureStage> stages = STAGES.computeIfAbsent(viewer.pack(),
                    key -> Collections.synchronizedList(new ArrayList<>()));
            stages.add(new FeatureStage(pending.featurePath(), stages.size(), pending.passId(),
                    pending.before(), after, added));
            POST_DIAMOND.put(viewer.pack(), after);
            POST_DIAMOND_PASS.put(viewer.pack(), pending.passId());
        } catch (Throwable error) {
            recordFailure("diamond-tail", origin, error);
        }
    }

    /** 逐 feature 记录（执行顺序）；未捕获到返回空表。 */
    public static List<FeatureStage> stages(ChunkPos viewer) {
        List<FeatureStage> stages = STAGES.get(viewer.pack());
        if (stages == null) {
            return List.of();
        }
        synchronized (stages) {
            return List.copyOf(stages);
        }
    }

    /** post-diamond 集合；未捕获到返回 null（= 该区块本次没有钻石 placed_feature 进入放置阶段）。 */
    public static Set<BlockPos> postDiamond(ChunkPos viewer) {
        return POST_DIAMOND.get(viewer.pack());
    }

    /** post-diamond 所属装饰批号；未捕获到返回 -1。 */
    public static int postDiamondPass(ChunkPos viewer) {
        Integer pass = POST_DIAMOND_PASS.get(viewer.pack());
        return pass == null ? -1 : pass;
    }

    /** 报告用：oracle 记账规模与异常。 */
    public static List<String> describe() {
        List<String> lines = new ArrayList<>();
        int stageTotal = 0;
        for (List<FeatureStage> stages : STAGES.values()) {
            synchronized (stages) {
                stageTotal += stages.size();
            }
        }
        lines.add("单次装饰 oracle：已取到 post-diamond 的区块 " + POST_DIAMOND.size()
                + " 个；逐 feature 记录 " + stageTotal + " 条（每条 = 一次 HEAD/RETURN 差集）");
        lines.add("oracle 捕获异常：" + (failures == 0 ? "无" : failures + " 次，首个：" + firstFailure));
        return lines;
    }

    /** 捕获异常只登记不上抛。 */
    private static void recordFailure(String stage, BlockPos origin, Throwable error) {
        failures++;
        if (firstFailure.isEmpty()) {
            firstFailure = stage + "@" + origin.getX() + "," + origin.getY() + "," + origin.getZ()
                    + " → " + error.getClass().getSimpleName() + ": " + error.getMessage();
        }
    }
}
