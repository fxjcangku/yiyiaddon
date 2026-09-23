package com.yiyiaddon.dev.seedpoc;

import java.util.List;
import net.minecraft.world.level.ChunkPos;

/**
 * 第四轮 · 单个 viewer 的离线验收结果（进报告的数据载体）。
 *
 * <p>它回答用户口径第十一、十二节要求的两件事：</p>
 * <ol>
 *     <li><b>第一阶段</b>：{@link #stages()}（四个生成阶段 checkpoint）与 {@link #preDiamond()}
 *         是否为「离线 vs 真实」逐 BlockPos 一致；不一致时 {@link #firstDivergenceStage()}
 *         给出<b>第一处分叉发生在哪一个生成阶段</b>；</li>
 *     <li><b>第二阶段</b>：把离线构造出来的 pre-diamond 接第三轮已证明正确的钻石 replay 链，
 *         得到的钻石坐标与真实世界真值的差异（{@link #diamondDiff()}）。</li>
 * </ol>
 */
record OfflineOutcome(
        ChunkPos viewer,
        List<StageComparator.Result> stages,
        List<StageComparator.Result> centerStages,
        StageComparator.Result preDiamond,
        StageComparator.Result preDiamondCenter,
        String firstDivergenceStage,
        String firstDivergenceDetail,
        String firstDivergenceStageCenter,
        int truthCount,
        int predictedCount,
        BlockPosDiff diamondDiff,
        BlockPosDiff worldDiff,
        int realWorldDiamondCount,
        int preDiamondDiamondCount,
        boolean replayRan,
        boolean restoreVerified,
        String note,
        List<String> details) {

    /** 第一阶段是否达标（阶段与 pre-diamond 全部一致，含邻域）。 */
    boolean preDiamondExact() {
        if (preDiamond == null || !preDiamond.exact()) {
            return false;
        }
        for (StageComparator.Result result : stages) {
            if (!result.exact()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 目标区块自己是否在所有阶段与 pre-diamond 都逐格一致。
     *
     * <p>它与 {@link #preDiamondExact()} 的差别只在「邻域年龄」：真实世界里邻域常常已经生成完了，
     * 离线 pipeline 是按依赖半径逐层铺的，阶段边界上邻域年龄天然不同。两个口径都必须报出来，
     * 否则要么把邻域年龄误报成生成错误，要么把真实差异藏起来。</p>
     */
    boolean preDiamondExactCenterOnly() {
        if (preDiamondCenter == null || !preDiamondCenter.exact()) {
            return false;
        }
        for (StageComparator.Result result : centerStages) {
            if (!result.exact()) {
                return false;
            }
        }
        return true;
    }

    /** 第二阶段是否达标（逐 BlockPos 完全一致）。 */
    boolean diamondExact() {
        return replayRan && diamondDiff != null && diamondDiff.exact();
    }

    /**
     * 换算到「真实世界最终钻石」口径后是否达标（预测集里每一格在真实世界都确实是钻石，
     * 且真实世界里这个范围内的钻石一个都没漏）。
     *
     * <p><b>为什么必须单列这个口径</b>：本轮第一口径是第三轮的「同一次装饰内的贡献」
     * （post-diamond − pre-diamond），它会把<b>邻域先装饰时写进本区块的钻石</b>排除在真值之外。
     * 于是「我们预测了某一格钻石、而这一遍装饰没写它（因为邻域已经写过）」会被记成错报——
     * 在真实世界里那一格确实是钻石。产品要回答的是「这一格到底有没有钻石」，
     * 所以两个口径都必须给出。</p>
     */
    boolean worldExact() {
        return replayRan && worldDiff != null && worldDiff.exact();
    }

    /**
     * 「不可判定」的比较项数（某一侧快照缺失）。
     *
     * <p><b>为什么要单独计</b>：缺失既不是「一致」也不是「分歧」。若把它算成一致，
     * 报告会假通过；若算成分歧，第一处分叉会被误导到一个根本没取到证的阶段。</p>
     */
    int unjudgeableCount() {
        int count = 0;
        if (preDiamond != null && preDiamond.unjudgeable()) {
            count++;
        }
        if (preDiamondCenter != null && preDiamondCenter.unjudgeable()) {
            count++;
        }
        for (StageComparator.Result result : stages) {
            if (result.unjudgeable()) {
                count++;
            }
        }
        for (StageComparator.Result result : centerStages) {
            if (result.unjudgeable()) {
                count++;
            }
        }
        return count;
    }
}
