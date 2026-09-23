package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 第三轮主流程：<b>单 viewer × 单次真实装饰</b>的对照器。
 *
 * <p><b>它严格按用户拍板的顺序做四件事</b>：</p>
 * <ol>
 *     <li><b>配对</b>：取该 viewer 的 pre-diamond 快照与 post-diamond 集合，
 *         先比装饰批号；批号不同就判「不可配对」，绝不做差集（禁止跨 viewer、跨批次配对）；</li>
 *     <li><b>算真值 oracle</b>：{@code vanillaContribution = post - pre}
 *         （完全来自同一次真实装饰的两个时刻，不假设、不倒推、不用最终世界代替）；</li>
 *     <li><b>重放</b>：把 pre-diamond 快照安装进真实区块，用影子世界访问层
 *         （同写半径、同读取语义）只重放这个 viewer 自己的钻石四条；</li>
 *     <li><b>比对</b>：{@code replayContribution = 重放后 - 安装后}，逐 BlockPos 与原版差集比，
 *         并逐条钻石 feature 对齐，交给 {@link FirstDivergenceAnalyzer} 找第一处分叉。</li>
 * </ol>
 *
 * <p><b>两种统计口径同时给出</b>：中心 ±1（3x3，第一阶段主判据，因为原版
 * {@code FEATURES} 的写半径就是 1）与落点区块（第二阶段主判据，用来单独衡量跨区块贡献）。</p>
 */
final class SingleViewerVerifier {

    private SingleViewerVerifier() {
    }

    /**
     * 对照一个 viewer 的一次装饰。
     *
     * @param level           服务端世界（实验期间会被临时改写，结束后逐格还原）
     * @param context         影子 worldgen 上下文
     * @param viewer          本次装饰的 viewer（重放中心）
     * @param target          统计落点区块（第一阶段 = viewer；第二阶段 = 目标区块）
     * @param diamondFeatures 钻石四条
     * @param calledMethods   影子层调用面（出参）
     * @param callCount       影子层调用次数（出参）
     * @param evidence        额外取证明细（只挂到第一条上，避免重复）
     */
    static ViewerOutcome verify(ServerLevel level, ShadowWorldGenContext context, ChunkPos viewer, ChunkPos target,
                                Set<PlacedFeature> diamondFeatures, Set<String> calledMethods, int[] callCount,
                                List<String> evidence) {
        GenStageSnapshot pre = GenStageCapture.preDiamond(viewer);
        int prePass = GenStageCapture.preDiamondPass(viewer);
        Set<BlockPos> postSet = DiamondFeatureOracle.postDiamond(viewer);
        int postPass = DiamondFeatureOracle.postDiamondPass(viewer);
        List<DiamondFeatureOracle.FeatureStage> vanillaStages = DiamondFeatureOracle.stages(viewer);
        boolean paired = postSet != null && prePass >= 0 && prePass == postPass;

        List<String> details = new ArrayList<>(evidence);
        if (pre == null) {
            details.add("缺少「第一条钻石 feature 前」快照：该 viewer 本次装饰没有钻石 placed_feature 进入放置阶段，"
                    + "或探针未覆盖到。按口径如实登记，不做任何代替。");
            return new ViewerOutcome(SeedPocConstants.MODE_SINGLE_DECORATION, viewer, target, prePass, postPass,
                    false, 0, 0, 0, false, Set.of(), Set.of(), 0,
                    BlockPosDiff.of(Set.of(), Set.of()), BlockPosDiff.of(Set.of(), Set.of()), true, "无",
                    "缺少 pre-diamond 快照，本 viewer 不参与判定", details);
        }

        Set<BlockPos> oraclePre = DiamondScanner.fromSnapshot(pre);
        Set<BlockPos> oraclePost = postSet == null ? oraclePre : postSet;
        Set<BlockPos> vanillaFootprint = DiamondScanner.minus(oraclePost, oraclePre);
        Set<BlockPos> vanillaTarget = DiamondScanner.within(vanillaFootprint, target);

        RegionStateLedger ledger = RegionStateLedger.take(level, target, SeedPocConstants.REGION_RADIUS);
        Set<BlockPos> replayPre;
        Set<BlockPos> replayFootprint;
        Set<BlockPos> replayTarget;
        int replayUnion;
        int candidateTotal;
        List<OreDecorationReplay.FeatureReplay> replayStages;
        boolean restoreVerified;
        // 安装 + 重放期间必须关闸：否则我们自己的重放会被探针当成「真实装饰」记进 oracle；
        // 同时开一次新的回放会话，把之前（例如装置自证对照）留下的回放侧放置台账清干净
        OreVeinTrace.beginReplaySession();
        GenStageCapture.setReplaying(true);
        try {
            ledger.install(pre);
            ledger.installHeightmaps(pre);
            replayPre = DiamondScanner.scan(level, viewer);
            WorldGenLevel shadow = ShadowLevelFactory.create(level, context.seed(), viewer, calledMethods, callCount,
                    null);
            OreDecorationReplay.DiamondReplayOutcome outcome = OreDecorationReplay.replayDiamonds(context, shadow,
                    viewer, new int[]{SeedPocRunner.stepUndergroundOres()}, diamondFeatures,
                    () -> DiamondScanner.scan(level, viewer), null);
            replayStages = outcome.features();
            candidateTotal = outcome.candidateTotal();
            replayUnion = outcome.union().size();
            Set<BlockPos> afterAll = DiamondScanner.scan(level, viewer);
            replayFootprint = DiamondScanner.minus(afterAll, replayPre);
            replayTarget = DiamondScanner.within(replayFootprint, target);
        } finally {
            GenStageCapture.setReplaying(false);
            ledger.restore();
        }
        restoreVerified = ledger.verifyRestore() == 0;

        boolean installMatch = replayPre.equals(oraclePre);
        BlockPosDiff footprintDiff = BlockPosDiff.of(vanillaFootprint, replayFootprint);
        BlockPosDiff targetDiff = BlockPosDiff.of(vanillaTarget, replayTarget);

        details.add("配对：装饰批号 pre=" + prePass + " / post=" + postPass + " → "
                + (paired ? "同一次装饰，允许做差集"
                        : "**不可配对**（pre 与 post 不属同一次装饰），本行差集仅供参考、不得作为结论"));
        details.add("原版 oracle：pre-diamond 钻石 " + oraclePre.size() + " → post-diamond 钻石 "
                + oraclePost.size() + "；本次新增 " + vanillaFootprint.size() + "（中心 ±1）/ "
                + vanillaTarget.size() + "（落点区块）");
        details.add("重放：安装快照后钻石 " + replayPre.size() + "（与 pre-diamond "
                + (installMatch ? "完全一致 → 安装生效" : "不一致 → 安装或读取有问题，本行结论不可用") + "）；"
                + "重放候选 feature " + candidateTotal + " 条，其中钻石 " + replayStages.size() + " 条；"
                + "新增 " + replayFootprint.size() + "（中心 ±1）/ " + replayTarget.size() + "（落点区块）");
        details.add("重放自证：逐条新增并集 " + replayUnion + " 个，中心 ±1 新增 " + replayFootprint.size()
                + " 个 → " + (replayUnion == replayFootprint.size() ? "一致" : "不一致（需查）"));
        details.add("中心 ±1 逐 BlockPos：" + footprintDiff.cn() + " → "
                + (footprintDiff.exact() ? "完全一致" : "不一致"));
        details.add("落点区块逐 BlockPos：" + targetDiff.cn() + " → "
                + (targetDiff.exact() ? "完全一致" : "不一致"));
        details.add("漏报样本（原版有、重放无，中心 ±1，最多 8 个）："
                + sample(DiamondScanner.minus(vanillaFootprint, replayFootprint)));
        details.add("错报样本（重放有、原版无，中心 ±1，最多 8 个）："
                + sample(DiamondScanner.minus(replayFootprint, vanillaFootprint)));
        details.add("还原校验：" + (restoreVerified ? "通过（逐格比对无残留）" : "失败，测试世界有残留"));
        details.add("逐条钻石 feature 对齐与第一处分叉：");

        FirstDivergenceAnalyzer.Result divergence = FirstDivergenceAnalyzer.analyze(viewer, target, vanillaStages,
                replayStages);
        details.addAll(divergence.lines());

        String note;
        if (!paired) {
            note = "pre 与 post 不属于同一次装饰（批号不等），本 viewer 的差集不可采信";
        } else if (!installMatch) {
            note = "快照安装后与 pre-diamond 不一致，本 viewer 的重放输入不成立";
        } else if (!restoreVerified) {
            note = "世界未逐格还原，测试世界可能已被污染";
        } else if (footprintDiff.exact()) {
            note = "";
        } else {
            note = divergence.summary();
        }

        return new ViewerOutcome(SeedPocConstants.MODE_SINGLE_DECORATION, viewer, target, prePass, postPass, paired,
                oraclePre.size(), oraclePost.size(), replayPre.size(), installMatch,
                vanillaFootprint, replayFootprint, replayUnion, footprintDiff, targetDiff, restoreVerified,
                divergence.layer(), note, details);
    }

    /** 取最多 8 个坐标样本。 */
    static String sample(Set<BlockPos> positions) {
        if (positions == null || positions.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        int taken = 0;
        for (BlockPos pos : positions) {
            if (taken++ >= 8) {
                builder.append(" …");
                break;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',')
                    .append(pos.getZ()).append(')');
        }
        return builder.toString();
    }
}
