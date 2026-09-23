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
 * 第四轮主流程：<b>只凭种子离线构造 pre-diamond，并接到第三轮已证明正确的钻石链上</b>。
 *
 * <p><b>输入边界（用户口径第十节）</b>：离线生成器只吃「种子 + 注册表 + 原版 worldgen 配置 + ChunkPos」。
 * 真实世界在这里只出现两次，都是<b>事后验证</b>而不是生成输入：</p>
 * <ol>
 *     <li>比较真实阶段快照（第三轮探针在真实生成过程中取的 oracle）；</li>
 *     <li>第二阶段把<b>离线</b> pre-diamond 安装进测试世界区块，用第三轮同一条影子链路重放钻石，
 *         再与真实世界真值比——装进去的是我们离线算出来的状态，不是真实快照。</li>
 * </ol>
 *
 * <p><b>两组数据分开统计</b>：第一阶段（离线状态 vs 真实状态）与第二阶段（离线状态推出的钻石 vs 真实钻石）
 * 各自独立判定，第一阶段不达标时第二阶段的结果照常如实输出、但注明「输入不成立」。</p>
 */
final class OfflineWorldgenVerifier {

    private OfflineWorldgenVerifier() {
    }

    /**
     * 验证一个 viewer。
     *
     * @param level            测试世界（第二阶段会被临时改写，结束后逐格还原）
     * @param replayContext    第三轮的影子上下文（种子 / 索引表 / 放置链，一行未改）
     * @param pipeline         离线 pipeline（跨 viewer 复用，模拟「同一个世界」）
     * @param viewer           目标区块
     * @param decorRadius      离线装饰范围（0 = 只装饰目标区块）
     * @param diamondFeatures  钻石四条
     * @param calledMethods    影子层调用面（出参）
     * @param callCount        影子层调用次数（出参）
     * @param runStageTwo      是否跑第二阶段
     */
    static OfflineOutcome verify(ServerLevel level, ShadowWorldGenContext replayContext,
                                 OfflineChunkPipeline pipeline, ChunkPos viewer, int decorRadius,
                                 Set<PlacedFeature> diamondFeatures, Set<String> calledMethods, int[] callCount,
                                 boolean runStageTwo) {
        List<String> details = new ArrayList<>();
        OfflineChunkPipeline.Result offline = pipeline.run(viewer, decorRadius);

        // ── 第一阶段：阶段 checkpoint 与 pre-diamond 逐 BlockPos 比较 ──────────────
        List<StageComparator.Result> stageResults = new ArrayList<>();
        List<StageComparator.Result> centerStageResults = new ArrayList<>();
        String firstDivergenceStage = null;
        String firstDivergenceDetail = "";
        String firstDivergenceStageCenter = null;
        List<String> unjudgeable = new ArrayList<>();
        for (String stage : GenStageCapture.STAGES) {
            GenStageSnapshot offlineStage = offline.stages().get(stage);
            GenStageSnapshot realStage = GenStageCapture.stage(stage, viewer);
            StageComparator.Result result = StageComparator.compare(stage, viewer, offlineStage, realStage);
            StageComparator.Result center = StageComparator.compareCenterOnly(stage, viewer, offlineStage, realStage);
            stageResults.add(result);
            centerStageResults.add(center);
            details.add(result.cn());
            details.add("      邻域年龄（各槽位已跑到的最远状态）｜离线 " + statusesOf(offlineStage)
                    + "；真实 " + statusesOf(realStage));
            if (!center.exact() && !center.unjudgeable()) {
                details.add("      ↳（仅目标区块口径）" + center.cn());
                details.add("      ↳（仅目标区块口径）" + center.firstDifferenceCn());
                details.add("      ↳（仅目标区块口径）" + StageComparator.centerDifferencePairs(offlineStage, realStage));
                BlockPos first = StageComparator.parsePos(center.firstStatePos());
                if (first != null && firstDivergenceStageCenter == null) {
                    // 首个「目标区块自己」分叉的阶段：把这个坐标在全部快照上的取值排成时间线，
                    // 一眼看出它是这一刻才分叉，还是更早就分叉了
                    details.add("      ↳（仅目标区块口径）坐标 " + center.firstStatePos()
                            + " 的跨阶段时间线｜离线 " + StageComparator.timeline(
                            offline.stages(), offline.preDiamond(), first, true));
                    details.add("      ↳（仅目标区块口径）坐标 " + center.firstStatePos()
                            + " 的跨阶段时间线｜真实 " + StageComparator.timeline(
                            GenStageCapture.stageSnapshots(viewer), GenStageCapture.preDiamond(viewer), first, false));
                }
            }
            if (result.unjudgeable()) {
                unjudgeable.add(stage + "（" + result.missingCn() + "）");
            } else if (!result.exact() && firstDivergenceStage == null) {
                firstDivergenceStage = stage;
                firstDivergenceDetail = result.firstDifferenceCn();
            }
            if (!center.unjudgeable() && !center.exact() && firstDivergenceStageCenter == null) {
                firstDivergenceStageCenter = stage;
            }
        }
        StageComparator.Result preDiamond = StageComparator.compare("pre-diamond（第一条钻石 feature 前）",
                viewer, offline.preDiamond(), GenStageCapture.preDiamond(viewer));
        StageComparator.Result preDiamondCenter = StageComparator.compareCenterOnly(
                "pre-diamond（第一条钻石 feature 前）· 仅目标区块",
                viewer, offline.preDiamond(), GenStageCapture.preDiamond(viewer));
        details.add(preDiamond.cn());
        details.add("      邻域年龄（各槽位已跑到的最远状态）｜离线 " + statusesOf(offline.preDiamond())
                + "；真实 " + statusesOf(GenStageCapture.preDiamond(viewer)));
        if (!preDiamondCenter.exact() && !preDiamondCenter.unjudgeable()) {
            details.add("      ↳（仅目标区块口径）" + preDiamondCenter.cn());
            details.add("      ↳（仅目标区块口径）" + preDiamondCenter.firstDifferenceCn());
            details.add("      ↳（仅目标区块口径）"
                    + StageComparator.centerDifferencePairs(offline.preDiamond(), GenStageCapture.preDiamond(viewer)));
            details.add("      ↳（仅目标区块口径）对钻石链的影响判别："
                    + StageComparator.diamondRelevantDifferences(offline.preDiamond(),
                    GenStageCapture.preDiamond(viewer),
                    level.getMinY() + SeedPocConstants.DIAMOND_SCAN_SECTION_COUNT * 16 - 1));
        }
        details.add("      ↳ 真实装饰顺序（判断哪些邻域先于中心被装饰）：" + GenStageCapture.passSummaryCn(viewer));
        if (preDiamond.unjudgeable()) {
            unjudgeable.add("pre-diamond（" + preDiamond.missingCn() + "）");
        } else if (!preDiamond.exact() && firstDivergenceStage == null) {
            firstDivergenceStage = "pre-diamond";
            firstDivergenceDetail = preDiamond.firstDifferenceCn();
        }
        if (!preDiamondCenter.unjudgeable() && !preDiamondCenter.exact() && firstDivergenceStageCenter == null) {
            firstDivergenceStageCenter = "pre-diamond";
        }
        if (!unjudgeable.isEmpty()) {
            details.add("不可判定项（快照缺失，一律不算「一致」也不算「分歧」）：" + String.join("、", unjudgeable));
        }
        if (offline.preDiamond() == null) {
            details.add("离线 pre-diamond 缺失：该 viewer 本次离线装饰里没有钻石 placed_feature 进入放置阶段"
                    + "（与真实侧同一判据），按口径如实登记，不做替代");
        } else {
            details.add("离线快照规模：覆盖 3x3 区块 × " + offline.preDiamond().sectionCount()
                    + " section，生物群系编号表 " + offline.preDiamond().biomePaletteSize() + " 种");
            details.add("中心区块生物群系（y=63 处，用来标注本区块的地形/群系条件）："
                    + offline.preDiamond().biomeKeyAt((viewer.x() << 4) + 8, 63, (viewer.z() << 4) + 8));
        }
        if (firstDivergenceStage == null) {
            details.add("第一处分叉：无（四个阶段与 pre-diamond 全部一致）");
        } else {
            details.add("**第一处分叉在 " + firstDivergenceStage + " 阶段**：" + firstDivergenceDetail);
            details.add("   同一批数据只看目标区块自己时，第一处分叉是："
                    + (firstDivergenceStageCenter == null ? "无（差异全部来自邻域年龄）"
                            : firstDivergenceStageCenter + " 阶段"));
        }

        // ── 第二阶段：离线 pre-diamond → 第三轮钻石链 → 钻石坐标 ──────────────────
        int truthCount = 0;
        int predictedCount = 0;
        BlockPosDiff diamondDiff = BlockPosDiff.of(Set.of(), Set.of());
        BlockPosDiff worldDiff = BlockPosDiff.of(Set.of(), Set.of());
        int realWorldDiamondCount = 0;
        int preDiamondDiamondCount = 0;
        boolean replayRan = false;
        boolean restoreVerified = true;
        String note = "";

        GenStageSnapshot offlinePre = offline.preDiamond();
        GenStageSnapshot realPreSnapshot = GenStageCapture.preDiamond(viewer);
        Set<BlockPos> realPreDiamond = realPreSnapshot == null
                ? Set.of() : DiamondScanner.fromSnapshot(realPreSnapshot);
        Set<BlockPos> realPostDiamond = DiamondFeatureOracle.postDiamond(viewer);
        Set<BlockPos> truthFootprint = realPostDiamond == null
                ? Set.of() : DiamondScanner.minus(realPostDiamond, realPreDiamond);
        truthCount = truthFootprint.size();
        preDiamondDiamondCount = realPreDiamond.size();

        if (!runStageTwo) {
            note = "第二阶段未启用（系统属性关闭）";
        } else if (offlinePre == null) {
            note = "离线 pre-diamond 缺失，第二阶段无法执行";
        } else {
            // 「世界口径」的真值：安装离线状态之前，真实世界里这个范围内的全部钻石
            // （= 本区块自己这一遍的贡献 + 先装饰的邻域写进来的贡献）
            Set<BlockPos> realWorldDiamonds = DiamondScanner.scan(level, viewer);
            // 口径二只算<b>目标区块内</b>：±1 范围里有 9 个区块的钻石，而我们这一遍只负责目标区块自己的
            // 四条钻石 feature，拿 9 个区块当分母是错的（会把邻域自己的钻石算成漏报）
            Set<BlockPos> realWorldInChunk = DiamondScanner.within(realWorldDiamonds, viewer);
            realWorldDiamondCount = realWorldInChunk.size();
            int realWorldWideCount = realWorldDiamonds.size();
            RegionStateLedger ledger = RegionStateLedger.take(level, viewer, SeedPocConstants.REGION_RADIUS);
            OreVeinTrace.beginReplaySession();
            GenStageCapture.setReplaying(true);
            try {
                // 装进去的是「离线算出来的」状态，不是真实快照
                ledger.install(offlinePre);
                ledger.installHeightmaps(offlinePre);
                Set<BlockPos> replayPre = DiamondScanner.scan(level, viewer);
                WorldGenLevel shadow = ShadowLevelFactory.create(level, replayContext.seed(), viewer,
                        calledMethods, callCount, null);
                OreDecorationReplay.DiamondReplayOutcome outcome = OreDecorationReplay.replayDiamonds(
                        replayContext, shadow, viewer, new int[]{SeedPocRunner.stepUndergroundOres()},
                        diamondFeatures, () -> DiamondScanner.scan(level, viewer), null);
                predictedCount = outcome.union().size();
                Set<BlockPos> after = DiamondScanner.scan(level, viewer);
                Set<BlockPos> replayedFootprint = DiamondScanner.minus(after, replayPre);
                diamondDiff = BlockPosDiff.of(truthFootprint, replayedFootprint);
                Set<BlockPos> predictedInChunk = DiamondScanner.within(outcome.union(), viewer);
                worldDiff = BlockPosDiff.of(realWorldInChunk, predictedInChunk);
                replayRan = true;
                details.add("第二阶段：安装离线 pre-diamond 后钻石 " + replayPre.size()
                        + " 个（真实 pre-diamond 快照里已有 " + realPreDiamond.size()
                        + " 个由先装饰的邻域写进来的钻石，两侧可比性见第一阶段）");
                details.add("第二阶段（口径一 · 第三轮口径「同一次装饰内的贡献」，范围 = 中心 ±1）：纯 Seed 预测 "
                        + predictedCount + " 个 / 真实本遍新增 " + truthCount + " 个 → " + diamondDiff.cn());
                details.add("第二阶段（口径二 · 真实世界最终钻石，范围 = 只算目标区块内）：真实世界目标区块内钻石 "
                        + realWorldInChunk.size() + " 个 / 纯 Seed 预测（落在目标区块内）"
                        + predictedInChunk.size() + " 个 → " + worldDiff.cn()
                        + "；同一时刻真实世界 ±1 范围共 " + realWorldWideCount + " 个钻石（含邻域自己的）");
            } finally {
                GenStageCapture.setReplaying(false);
                ledger.restore();
            }
            restoreVerified = ledger.verifyRestore() == 0;
            details.add("还原校验：" + (restoreVerified ? "通过（逐格比对无残留）" : "失败，测试世界有残留"));
        }

        if (note.isEmpty()) {
            if (!preDiamond.exact()) {
                note = "离线 pre-diamond 与真实不一致（第一处分叉：" + firstDivergenceStage + "）";
            } else if (replayRan && !diamondDiff.exact()) {
                note = "离线 pre-diamond 一致但钻石链结果不一致";
            } else if (!restoreVerified) {
                note = "世界未逐格还原";
            } else {
                note = "";
            }
        }

        return new OfflineOutcome(viewer, stageResults, centerStageResults, preDiamond, preDiamondCenter,
                firstDivergenceStage, firstDivergenceDetail, firstDivergenceStageCenter,
                truthCount, predictedCount, diamondDiff, worldDiff, realWorldDiamondCount,
                preDiamondDiamondCount, replayRan, restoreVerified, note, details);
    }

    /** 快照的「逐槽位状态」文本；快照缺失时如实标注。 */
    private static String statusesOf(GenStageSnapshot snapshot) {
        return snapshot == null ? "（快照缺失）" : snapshot.slotStatusesCn();
    }
}
