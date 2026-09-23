package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC · 报告产出：把实验的全部数字排成中文文本，同时写进日志与运行目录下的文本文件。
 *
 * <p>为什么同时落文件：日志会被后续 tick 刷满，逐区块的明细需要一份能直接打开对照的东西；
 * 报告内容里每条结论都带「数字 + 来源」，便于人工复核。第二轮在原有结构上补了两节：
 * <b>真实生成期探针状态</b> 与 <b>旧 PoC / 快照 PoC 逐区块对照</b>。</p>
 */
public final class SeedPocReport {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告输入（一次实验的全部事实）。 */
    public record Summary(String worldName,
                          String dimension,
                          long seed,
                          String seedSource,
                          String biomeSourceFrom,
                          String generatorFrom,
                          String randomStateFrom,
                          int featureStepCount,
                          String indexVerifyNote,
                          List<String> diamondOreConfigs,
                          Set<String> calledMethods,
                          int callCount,
                          List<ChunkOutcome> outcomes,
                          List<ViewerOutcome> singleViewer,
                          List<ViewerOutcome> crossChunk,
                          boolean crossChunkExecuted,
                          boolean singleExact,
                          List<String> oracleNote,
                          BiomeReproProbe.Result biome,
                          NoiseTerrainProbe.Result terrain,
                          boolean seedOverridden,
                          List<String> captureNote,
                          long elapsedMillis,
                          List<OfflineOutcome> offline,
                          boolean offlineExecuted,
                          List<String> offlineNotes,
                          boolean round6,
                          boolean legacySections,
                          PredictionRegressionSuite.Result round6Result,
                          List<String> round6Notes) {
    }

    /** 口径标签 → 对照表里的短名（表宽有限，长名只在明细节出现）。 */
    private static final Map<String, String> SHORT_MODE = Map.of(
            SeedPocConstants.MODE_OLD_WORLD, "口径0 旧口径(最终世界输入)",
            SeedPocConstants.MODE_SNAPSHOT_ORES, "口径1 快照·矿步骤",
            SeedPocConstants.MODE_SNAPSHOT_ALL_STEPS, "口径2 快照·全前置步骤",
            SeedPocConstants.MODE_SNAPSHOT_PRE_DIAMOND, "口径3 快照·钻石前",
            SeedPocConstants.MODE_CONTROL_AMETHYST, "对照1 装置自证·不可替换",
            SeedPocConstants.MODE_CONTROL_STONE, "对照2 装置自证·可替换");

    private SeedPocReport() {
    }

    /** 生成报告正文（中文）。 */
    public static List<String> render(Summary summary) {
        List<String> lines = new ArrayList<>();
        if (summary.round6()) {
            lines.add("================ 种子挖矿 PoC 实验报告（第六轮 · Target ± 写半径 viewer 离线最终预测） ================");
            lines.add("本报告由开发期实验代码自动生成。第五轮已实机证明：最终钻石集合与 Chunk 生成/装饰先后顺序无关，");
            lines.add("因此正式 Predictor 不需要任何真实调度历史（装饰批号）作为输入。");
            lines.add("第六轮要证的是：只凭 版本 + 世界 Seed + 维度 + Target ChunkPos + 原版注册表/worldgen 配置，");
            lines.add("在<b>一份共享离线世界</b>里把目标区块周围「按原版写半径能写进目标」的 viewer 各跑一遍它自己的 FEATURES，");
            lines.add("只读目标区块，能否与真实世界的最终钻石逐 BlockPos 完全一致。所有数字均为本次实机运行实测值。");
            lines.add("本模式下第二/三/四轮的重口径默认不跑（见【十一】说明）；第四轮「单 viewer」模型改为在本报告第六轮章节里现场复算。");
        } else {
            lines.add("================ 种子挖矿 PoC 实验报告（第四轮 · 仅凭 Seed 离线构造 pre-diamond） ================");
            lines.add("本报告由开发期实验代码自动生成。第三轮已实机证明：给定正确的 pre-diamond 输入状态，");
            lines.add("钻石放置链可逐 BlockPos 100% 复现。第四轮回答的是它前面的那一步：");
            lines.add("只凭 版本 + 世界 Seed + 维度 + ChunkPos + 原版注册表/worldgen 配置，能不能离线构造出");
            lines.add("与原版真实生成过程一致的 pre-diamond 输入状态，并由此推出钻石 BlockPos。");
            lines.add("第三轮的结论与代码保持原样，本轮只新增离线构造与逐阶段 checkpoint 比较。所有数字均为本次实机运行实测值。");
        }
        lines.add("");
        lines.add("【零、运行环境】");
        lines.add("  世界：" + summary.worldName());
        lines.add("  维度：" + summary.dimension());
        lines.add("  被试种子：" + summary.seed() + "（来源：" + summary.seedSource() + "）");
        lines.add("  耗时：" + summary.elapsedMillis() + " ms");
        if (summary.seedOverridden() && summary.legacySections()) {
            lines.add("  注意：本次用系统属性覆盖了种子，快照口径的输入来自真实世界、与算法种子不一致，");
            lines.add("        该口径结论不可采信；快照口径必须在「种子 = 当前世界真实种子」下运行。");
        }
        if (summary.round6() && summary.seedOverridden()) {
            lines.add("  第六轮口径说明：离线预测用的种子 = 当前单人世界的真实种子（level.getSeed()），"
                    + "系统属性指定的种子只用于「新建这个测试世界」，两者一致 ⇒ 真值可比。");
        }
        lines.add("");
        lines.add("【一、上下文构造（全部自建，未被服务端对象代劳）】");
        lines.add("  生物群系源：" + summary.biomeSourceFrom());
        lines.add("  区块生成器：" + summary.generatorFrom());
        lines.add("  随机状态：" + summary.randomStateFrom());
        lines.add("  装饰步骤数：" + summary.featureStepCount() + "（原版 11 步，含 underground_ores）");
        lines.add("  索引表交叉核对：" + summary.indexVerifyNote());
        lines.add("  钻石四条 placed_feature 运行期配置（直接读注册表，不是抄源码）：");
        lines.addAll(summary.diamondOreConfigs());
        lines.add("");
        lines.add("【二、真实生成期探针】");
        lines.addAll(summary.captureNote());
        lines.add("");
        lines.addAll(renderOracle(summary));
        lines.add("");
        lines.addAll(renderSingleViewer(summary.singleViewer()));
        lines.add("");
        lines.addAll(renderSingleSummary(summary.singleViewer(), summary.singleExact()));
        lines.add("");
        lines.addAll(renderBranch(summary));
        lines.add("");
        lines.addAll(renderControl(summary.outcomes()));
        lines.add("");
        lines.add("【七、生物群系可复现性（种子函数层）】");
        lines.add(summary.legacySections()
                ? "  " + summary.biome().cn()
                : "  第六轮模式下未运行（第六轮只用最终世界真值；每个目标区块的真实生物群系在第六轮章节逐行登记）");
        lines.add("");
        lines.add("【八、噪声地形探针（地形层，仅登记差异、不声称等价）】");
        lines.add(summary.legacySections()
                ? "  " + summary.terrain().cn()
                : "  第六轮模式下未运行（该探针属第二轮口径，与本轮判据无关）");
        lines.add("");
        lines.addAll(renderLegacy(summary.outcomes()));
        lines.add("");
        lines.addAll(renderRound6(summary));
        lines.add("");
        lines.addAll(renderOffline(summary));
        lines.add("");
        lines.add("【十二、结论边界（必须与数字一致）】");
        lines.add("  1. 本实验证明的是装饰阶段：装饰种子派生 → feature 全局索引 → 逐个 setFeatureSeed →");
        lines.add("     PlacedFeature/OreFeature 放置，在给定输入状态下能否与「同一次真实装饰」逐 BlockPos 相同。");
        lines.add("  2. pre-diamond 与 post-diamond 都取自同一个 viewer 的同一次真实装饰（批号必须相同），");
        lines.add("     因此 oracle 不含任何跨 viewer、跨批次、跨最终世界倒推的成分。");
        lines.add("  3. 重放的输入是 pre-diamond 快照（安装进真实区块后再重放），因此它证明的是");
        lines.add("     「算法在正确输入下能否复现」，不代表「客户端已经能离线构造该输入」。");
        lines.add("  4. 第一阶段的判据是中心 ±1（3x3）与落点区块两个口径的逐 BlockPos 差集；");
        lines.add("     中心 ±1 的依据是原版 FEATURES 的 blockStateWriteRadius = 1。");
        lines.add("  5. 快照纵向只覆盖 y ∈ [-64, 79] 的 9 个 section；钻石扫描只用其中 y ≤ 31 的 6 个 section。");
        lines.add("  6. 若第一阶段仍未逐 BlockPos 完全一致，禁止在任何下游材料里写「精准种子挖矿已实现」。");
        lines.add("  7. 第四轮的离线上下文里，有一个环境边界必须写明：宿主世界只提供与种子无关的环境参数");
        lines.add("     （维度类型 / 世界高度 / 注册表 / 结构模板管理器 / 调色板工厂）；种子的四件东西全部自建。");
        lines.add("     离线 region 对宿主 ChunkMap 的查询次数已在【十一】列出（>0 必须逐条解释）。");
        lines.add("  8. 第四轮只做 26.1.2 原版主世界 + 钻石；其它矿物、其它维度、26.2/26.3 一概未做。");
        lines.add("  9. 第二阶段的钻石验收有<b>两个口径</b>，禁止只引用其中一个：");
        lines.add("     · 口径一（第三轮口径 · 同一次装饰内的贡献）= 真实 post-diamond − 真实 pre-diamond；");
        lines.add("       它把「先被装饰的邻域已经写进本区块的钻石」排除在真值之外；");
        lines.add("     · 口径二（真实世界最终钻石 · 只算目标区块内）= 安装离线状态前，真实世界里目标区块内的全部钻石；");
        lines.add("       产品要回答的是「这一格到底有没有钻石」，所以口径二才是最终判据。");
        lines.add("  10. 离线 pre-diamond 与真实 pre-diamond 的差异，只有在「该区块是生成波前的中心（邻域尚未装饰）」");
        lines.add("      这一种情形下才有意义地逐格比对；若真实世界里邻域先装饰过（报告里逐 viewer 给出真实装饰批号），");
        lines.add("      邻域按其写半径 1 写进本区块的地物方块属于<b>真实服务端调度顺序</b>，不是种子函数。");
        lines.add("=====================================================");
        return lines;
    }

    /** 【三】oracle 本身的状态：谁取到了、配对约束怎么保证。 */
    private static List<String> renderOracle(Summary summary) {
        List<String> lines = new ArrayList<>();
        lines.add("【三、单次装饰 oracle（pre-diamond → post-diamond 差集）】");
        lines.add("  注入位置：同一区块同一次 applyBiomeDecoration 内，");
        lines.add("    · pre-diamond = PlacedFeature#placeWithBiomeCheck HEAD（该区块第一条钻石 placed_feature 执行前）");
        lines.add("    · post-diamond = 同方法 RETURN（该区块最后一条钻石 placed_feature 执行结束时）");
        lines.add("  配对约束：两个时刻都必须带同一个「装饰批号」（applyBiomeDecoration 每进入一次发一个号）；");
        lines.add("  批号不同即判「不可配对」，绝不跨越做差集。");
        lines.add("  差集口径：vanillaContribution = post-diamond 钻石 - pre-diamond 钻石（同一次真实装饰内）。");
        lines.addAll(summary.oracleNote());
        lines.add("  影子世界访问层调用面：装饰链路实际调用 WorldGenLevel 方法 " + summary.calledMethods().size()
                + " 种 / 共 " + summary.callCount() + " 次（这份清单就是「做一个完整离线上下文还差什么」的直接证据）：");
        lines.add("    " + String.join(", ", summary.calledMethods().stream().sorted().toList()));
        return lines;
    }

    /** 【四】第一阶段逐 viewer 明细。 */
    private static List<String> renderSingleViewer(List<ViewerOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        lines.add("【四、第一阶段：单 viewer 单次装饰逐 viewer 数据】");
        if (outcomes.isEmpty()) {
            lines.add("  未运行第一阶段（variant.single 被关闭）");
            return lines;
        }
        for (ViewerOutcome outcome : outcomes) {
            lines.add("  ── " + outcome.cn());
            lines.add("      oracle 计数：pre-diamond " + outcome.oraclePreCount() + " 个钻石 → post-diamond "
                    + outcome.oraclePostCount() + " 个；原版本次新增（中心 ±1）"
                    + outcome.vanillaFootprint().size() + " 个 / （落点区块）" + outcome.targetDiff().realCount() + " 个");
            lines.add("      重放计数：安装快照后 " + outcome.snapshotPreCount() + " 个钻石（与 pre-diamond "
                    + (outcome.snapshotInstallMatch() ? "一致" : "不一致") + "）；重放新增（中心 ±1）"
                    + outcome.replayFootprint().size() + " 个（逐条并集 " + outcome.replayUnionCount()
                    + "）/ （落点区块）" + outcome.targetDiff().predictedCount() + " 个");
            lines.add("      中心 ±1 逐 BlockPos：" + outcome.footprintDiff().cn() + " / 完全一致："
                    + (outcome.footprintDiff().exact() ? "是" : "否"));
            lines.add("      落点区块逐 BlockPos：" + outcome.targetDiff().cn() + " / 完全一致："
                    + (outcome.targetDiff().exact() ? "是" : "否"));
            lines.add("      第一处分叉：已定位到 " + outcome.divergenceLayer());
            lines.add("      还原校验：" + (outcome.restoreVerified() ? "通过" : "未通过"));
            if (!outcome.note().isEmpty()) {
                lines.add("      说明：" + outcome.note());
            }
            lines.add("      逐行明细：");
            for (String detail : outcome.details()) {
                lines.add("        " + detail);
            }
        }
        return lines;
    }

    /** 【五】第一阶段汇总与判定。 */
    private static List<String> renderSingleSummary(List<ViewerOutcome> outcomes, boolean singleExact) {
        List<String> lines = new ArrayList<>();
        lines.add("【五、第一阶段汇总（本次装饰内的本区块自身贡献）】");
        if (outcomes.isEmpty()) {
            lines.add("  无数据");
            return lines;
        }
        BlockPosDiff footprint = BlockPosDiff.sum(outcomes.stream().map(ViewerOutcome::footprintDiff).toList());
        BlockPosDiff target = BlockPosDiff.sum(outcomes.stream().map(ViewerOutcome::targetDiff).toList());
        int exactFootprint = 0;
        int exactTarget = 0;
        int paired = 0;
        for (ViewerOutcome outcome : outcomes) {
            if (outcome.footprintDiff().exact()) {
                exactFootprint++;
            }
            if (outcome.targetDiff().exact()) {
                exactTarget++;
            }
            if (outcome.paired()) {
                paired++;
            }
        }
        lines.add("  可配对（同 viewer + 同批号）：" + paired + "/" + outcomes.size());
        lines.add("  中心 ±1 合计：" + footprint.cn() + "；逐 BlockPos 完全一致的 viewer："
                + exactFootprint + "/" + outcomes.size());
        lines.add("  落点区块合计：" + target.cn() + "；逐 BlockPos 完全一致的 viewer："
                + exactTarget + "/" + outcomes.size());
        lines.add("  第一阶段是否达到逐 BlockPos 100%："
                + (singleExact ? "是（" + outcomes.size() + "/" + outcomes.size() + "）" : "否"));
        return lines;
    }

    /** 【六】按第一阶段结果分叉：100% 走第二阶段，否则列第三阶段 first-divergence。 */
    private static List<String> renderBranch(Summary summary) {
        List<String> lines = new ArrayList<>();
        if (summary.singleExact()) {
            lines.add("【六、第二阶段：3x3 九个 viewer 的跨区块贡献】");
            if (!summary.crossChunkExecuted()) {
                lines.add("  第一阶段已 100% 一致，但第二阶段被关闭（variant.phase2=0）");
                return lines;
            }
            Map<String, List<ViewerOutcome>> byTarget = new LinkedHashMap<>();
            for (ViewerOutcome outcome : summary.crossChunk()) {
                byTarget.computeIfAbsent(outcome.target().x() + "," + outcome.target().z(),
                        key -> new ArrayList<>()).add(outcome);
            }
            for (Map.Entry<String, List<ViewerOutcome>> entry : byTarget.entrySet()) {
                lines.add("  ── 目标区块 (" + entry.getKey() + ")：九个 viewer 各自写进本区块的钻石");
                for (ViewerOutcome outcome : entry.getValue()) {
                    lines.add("      viewer (" + outcome.viewer().x() + "," + outcome.viewer().z() + ")"
                            + " 落点区块：" + outcome.targetDiff().cn()
                            + " / 完全一致：" + (outcome.targetDiff().exact() ? "是" : "否")
                            + " / 批号 pre=" + outcome.prePass() + " post=" + outcome.postPass()
                            + (outcome.paired() ? "" : "（不可配对）"));
                    for (String detail : outcome.details()) {
                        lines.add("        " + detail);
                    }
                }
                BlockPosDiff total = BlockPosDiff.sum(entry.getValue().stream()
                        .map(ViewerOutcome::targetDiff).toList());
                lines.add("      本目标区块九个 viewer 合计（按顺序合并，不提前 union）：" + total.cn());
            }
            return lines;
        }
        lines.add("【六、第三阶段：first-divergence（第一阶段未达 100%，按口径不执行第二阶段）】");
        Map<String, Integer> layers = new LinkedHashMap<>();
        for (ViewerOutcome outcome : summary.singleViewer()) {
            layers.merge(outcome.divergenceLayer(), 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> entry : layers.entrySet()) {
            lines.add("  分叉层「" + entry.getKey() + "」的 viewer 数：" + entry.getValue());
        }
        lines.add("  逐 viewer 的第一处分叉结论：");
        for (ViewerOutcome outcome : summary.singleViewer()) {
            lines.add("    viewer (" + outcome.viewer().x() + "," + outcome.viewer().z() + ") → "
                    + outcome.divergenceLayer() + "：" + outcome.note());
        }
        lines.add("  说明：placement origin 逐项对比与矿脉几何参数对比的原始明细见【四】每个 viewer 的「逐行明细」。");
        return lines;
    }

    /** 【七】装置自证对照（第二轮既有口径，本轮默认仍开）。 */
    private static List<String> renderControl(List<ChunkOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        lines.add("【七、装置自证对照（第二轮的既有口径，用来证明放置链路确实以输入状态为依据）】");
        BlockPosDiff amethyst = null;
        BlockPosDiff stone = null;
        for (ChunkOutcome outcome : outcomes) {
            if (SeedPocConstants.MODE_CONTROL_AMETHYST.equals(outcome.mode())) {
                amethyst = amethyst == null ? outcome.diff() : BlockPosDiff.sum(List.of(amethyst, outcome.diff()));
            } else if (SeedPocConstants.MODE_CONTROL_STONE.equals(outcome.mode())) {
                stone = stone == null ? outcome.diff() : BlockPosDiff.sum(List.of(stone, outcome.diff()));
            }
        }
        if (amethyst == null || stone == null) {
            lines.add("  本轮未运行装置自证对照（期望值可判定的两个对照：不可替换 ⇒ 预测 0；可替换 ⇒ 预测明显变多）");
            return lines;
        }
        lines.add("  对照1（中心区块整片改写为紫水晶块：不可替换、非空气）：预测合计 " + amethyst.predictedCount()
                + " 个钻石（期望 0）→ " + (amethyst.predictedCount() == 0 ? "符合期望，装置把状态送进了放置链路"
                        : "不符合期望，装置存疑"));
        lines.add("  对照2（中心区块整片改写为石头：可替换）：预测合计 " + stone.predictedCount()
                + " 个钻石（真值合计 " + stone.realCount() + "）→ "
                + (stone.predictedCount() > stone.realCount() ? "符合期望" : "不符合期望"));
        return lines;
    }

    /** 【九】第二轮既有口径的对照（默认不跑，跑了才输出）。 */
    private static List<String> renderLegacy(List<ChunkOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        boolean isLegacy = false;
        for (ChunkOutcome outcome : outcomes) {
            if (SeedPocConstants.MODE_CONTROL_AMETHYST.equals(outcome.mode())
                    || SeedPocConstants.MODE_CONTROL_STONE.equals(outcome.mode())) {
                continue;
            }
            isLegacy = true;
            break;
        }
        lines.add("【九、第二轮既有口径对照（本轮默认关闭，用 -Dyiyiaddon.seedpoc.variant.old=1 可打开）】");
        if (!isLegacy) {
            lines.add("  本轮未运行第二轮口径：按用户口径第三轮只验证「单 viewer 单次装饰」，");
            lines.add("  避免把九遍聚合的旧数字混进本轮结论。");
            return lines;
        }
        lines.addAll(renderComparison(outcomes));
        lines.addAll(renderVerdict(outcomes));
        return lines;
    }

    /**
     * 【十一】第四轮：仅凭 Seed 离线构造 pre-diamond，并接第三轮钻石链的结果。
     *
     * <p>输出口径严格按用户第四轮报告清单：每个 viewer 先给四个生成阶段 checkpoint 的一致性，
     * 再给「第一处分叉在哪一阶段」，最后给纯 Seed 钻石预测的逐 BlockPos 差异。</p>
     */
    private static List<String> renderOffline(Summary summary) {
        List<String> lines = new ArrayList<>();
        lines.add("【十一、第四轮：仅凭 Seed 离线构造 pre-diamond（离线 vs 真实逐 BlockPos）】");
        if (!summary.offlineExecuted()) {
            lines.add("  未运行。原因：");
            for (String note : summary.offlineNotes()) {
                lines.add("    " + note);
            }
            return lines;
        }
        lines.add("  离线输入只有：种子 / 版本 / 注册表 / NoiseGeneratorSettings / 维度与 worldgen 配置 / ChunkPos。");
        lines.add("  离线实现：自建 BiomeSource + NoiseBasedChunkGenerator + RandomState + ChunkGeneratorStructureState，");
        lines.add("  在自建 OfflineChunkHolder/OfflineChunkRegion 上按原版 ChunkPyramid 的依赖半径逐层驱动各生成阶段，");
        lines.add("  阶段实现直接调用原版（createStructures/createReferences/createBiomes/fillFromNoise/buildSurface/");
        lines.add("  applyCarvers/applyBiomeDecoration），本层只做「驱动 + 采集 + 比较」。");
        lines.addAll(summary.offlineNotes());
        if (summary.offline().isEmpty()) {
            lines.add("  没有任何 viewer 完成离线构造（详见上方异常记录），本轮离线结论不成立。");
            return lines;
        }
        int preExact = 0;
        int preExactCenter = 0;
        int diamondExact = 0;
        int worldExact = 0;
        int replayRan = 0;
        BlockPosDiff diamondTotal = BlockPosDiff.of(Set.of(), Set.of());
        BlockPosDiff worldTotal = BlockPosDiff.of(Set.of(), Set.of());
        List<BlockPosDiff> diamondDiffs = new ArrayList<>();
        List<BlockPosDiff> worldDiffs = new ArrayList<>();
        Map<String, Integer> divergenceStages = new LinkedHashMap<>();
        for (OfflineOutcome outcome : summary.offline()) {
            lines.add("  ── viewer (" + outcome.viewer().x() + "," + outcome.viewer().z() + ")");
            for (String detail : outcome.details()) {
                lines.add("      · " + detail);
            }
            lines.add("      第一阶段判定（含邻域 3x3）："
                    + (outcome.preDiamondExact() ? "完全一致" : "存在差异")
                    + "；第一阶段判定（仅目标区块）："
                    + (outcome.preDiamondExactCenterOnly() ? "完全一致" : "存在差异"));
            if (outcome.replayRan()) {
                replayRan++;
                diamondDiffs.add(outcome.diamondDiff());
                worldDiffs.add(outcome.worldDiff());
                lines.add("      第二阶段（口径一 · 同一次装饰内的贡献）：预测 " + outcome.predictedCount()
                        + " 个 / 真实本遍新增 " + outcome.truthCount() + " 个 → " + outcome.diamondDiff().cn()
                        + " → " + (outcome.diamondExact() ? "逐 BlockPos 完全一致" : "**不一致**"));
                lines.add("      第二阶段（口径二 · 真实世界最终钻石，只算目标区块内）：真实世界目标区块内 "
                        + outcome.worldDiff().realCount() + " 个（其中 " + outcome.preDiamondDiamondCount()
                        + " 个在本区块装饰前就已由邻域写入）/ 预测落在目标区块内 "
                        + outcome.worldDiff().predictedCount() + " 个 → " + outcome.worldDiff().cn() + " → "
                        + (outcome.worldExact() ? "逐 BlockPos 完全一致" : "**不一致**"));
            } else {
                lines.add("      第二阶段未执行：" + outcome.note());
            }
            lines.add("      还原校验：" + (outcome.restoreVerified() ? "通过" : "失败（测试世界有残留）"));
            if (outcome.firstDivergenceStage() != null) {
                divergenceStages.merge(outcome.firstDivergenceStage(), 1, Integer::sum);
            }
            if (outcome.preDiamondExact()) {
                preExact++;
            }
            if (outcome.preDiamondExactCenterOnly()) {
                preExactCenter++;
            }
            if (outcome.diamondExact()) {
                diamondExact++;
            }
            if (outcome.worldExact()) {
                worldExact++;
            }
        }
        if (!diamondDiffs.isEmpty()) {
            diamondTotal = BlockPosDiff.sum(diamondDiffs);
        }
        if (!worldDiffs.isEmpty()) {
            worldTotal = BlockPosDiff.sum(worldDiffs);
        }
        lines.add("  逐阶段 checkpoint 汇总（同一批 viewer 累加；口径见每行标注）"
                + "：");
        List<String> stageLabels = new ArrayList<>(GenStageCapture.STAGES);
        stageLabels.add("pre-diamond");
        for (int index = 0; index < stageLabels.size(); index++) {
            long stateTotal = 0;
            long stateDiff = 0;
            long biomeTotal = 0;
            long biomeDiff = 0;
            long biomeUncomparable = 0;
            int exactWide = 0;
            int exactCenter = 0;
            int unjudgeable = 0;
            int counted = 0;
            for (OfflineOutcome outcome : summary.offline()) {
                StageComparator.Result wide = stageResultOf(outcome, index);
                StageComparator.Result center = stageResultOfCenter(outcome, index);
                if (wide == null || center == null) {
                    continue;
                }
                counted++;
                if (wide.unjudgeable()) {
                    unjudgeable++;
                } else {
                    stateTotal += wide.stateTotal();
                    stateDiff += wide.stateDiff();
                    biomeTotal += wide.biomeTotal();
                    biomeDiff += wide.biomeDiff();
                    biomeUncomparable += wide.biomeUncomparable();
                    if (wide.exact()) {
                        exactWide++;
                    }
                }
                if (!center.unjudgeable() && center.exact()) {
                    exactCenter++;
                }
            }
            lines.add("    · " + stageLabels.get(index) + "：含邻域 3x3 逐格一致 " + exactWide + "/" + counted
                    + "；仅目标区块逐格一致 " + exactCenter + "/" + counted
                    + "；方块不符 " + stateDiff + "/" + stateTotal
                    + "；生物群系不符 " + biomeDiff + "/" + biomeTotal + "（不可比 " + biomeUncomparable + "）"
                    + (unjudgeable > 0 ? "；**不可判定 " + unjudgeable + "**" : ""));
        }
        int unjudgeableTotal = 0;
        for (OfflineOutcome outcome : summary.offline()) {
            unjudgeableTotal += outcome.unjudgeableCount();
        }
        lines.add("    · 不可判定项合计（两侧任一快照缺失，既不算一致也不算分歧）：" + unjudgeableTotal);
        lines.add("  汇总（" + summary.offline().size() + " 个 viewer）：");
        lines.add("    · 离线 pre-diamond 与真实逐 BlockPos 一致（含邻域 3x3）：" + preExact + " / "
                + summary.offline().size());
        lines.add("    · 离线 pre-diamond 与真实逐 BlockPos 一致（仅目标区块）：" + preExactCenter + " / "
                + summary.offline().size());
        lines.add("    · 四个阶段 checkpoint 只看目标区块自己是否全部一致："
                + (preExactCenter == summary.offline().size() ? "是" : "否（见上面每个 viewer 的「仅目标区块口径」行）"));
        lines.add("    · 执行了第二阶段的 viewer：" + replayRan);
        lines.add("    · 【口径一 · 同一次装饰内的贡献】逐 BlockPos 一致的 viewer：" + diamondExact + " / "
                + summary.offline().size() + "；合计：" + diamondTotal.cn());
        lines.add("    · 【口径二 · 真实世界最终钻石（只算目标区块内）】逐 BlockPos 一致的 viewer：" + worldExact
                + " / " + summary.offline().size() + "；合计：" + worldTotal.cn());
        if (divergenceStages.isEmpty()) {
            lines.add("    · FIRST DIVERGENCE 阶段分布（含邻域）：无分叉");
        } else {
            lines.add("    · FIRST DIVERGENCE 阶段分布（含邻域）：" + divergenceStages);
        }
        return lines;
    }

    /** 第 {@code index} 个比较项（0..3 = 四个阶段，4 = pre-diamond）在含邻域口径下的结果。 */
    private static StageComparator.Result stageResultOf(OfflineOutcome outcome, int index) {
        if (index >= GenStageCapture.STAGES.size()) {
            return outcome.preDiamond();
        }
        return index < outcome.stages().size() ? outcome.stages().get(index) : null;
    }

    /** 同类项在「仅目标区块」口径下的结果。 */
    private static StageComparator.Result stageResultOfCenter(OfflineOutcome outcome, int index) {
        if (index >= GenStageCapture.STAGES.size()) {
            return outcome.preDiamondCenter();
        }
        return index < outcome.centerStages().size() ? outcome.centerStages().get(index) : null;
    }

    /**
     * 【十二】第六轮：共享离线世界 → 目标区块最终钻石。
     *
     * <p>输出口径严格按用户第六轮报告清单：架构与输入边界 → 逐目标真值/预测/匹配/漏报/错报/查全查准 →
     * 合计是否 243/243 → 第四轮 11 格旧漏报逐格找回情况 → 缓存隔离 → 性能 → 失败案例与其它钻石来源。</p>
     */
    private static List<String> renderRound6(Summary summary) {
        List<String> lines = new ArrayList<>();
        lines.add("【十、第六轮：目标 ± 写半径 viewer 共享离线世界 → 最终目标区块钻石】");
        if (!summary.round6()) {
            lines.add("  本轮未运行（系统属性 yiyiaddon.seedpoc.round6=0）");
            return lines;
        }
        lines.add("  输入边界：只使用 版本 / Seed / 维度 / Target ChunkPos / 原版注册表 / NoiseGeneratorSettings /");
        lines.add("  原版 worldgen 配置。不读取真实世界的方块状态、ChunkAccess、pre-diamond 快照、高度图、矿物坐标、");
        lines.add("  装饰批号；真实世界只在「真值」一侧出现（FinalOreTruthComparator，测试侧，不参与预测）。");
        lines.add("  第六轮不使用任何真实装饰批号 / applyBiomeDecoration 顺序 / 服务端 Chunk 历史（第五轮已证不需要）。");
        for (String note : summary.round6Notes()) {
            lines.add("  " + note);
        }
        PredictionRegressionSuite.Result result = summary.round6Result();
        if (result == null) {
            lines.add("  第六轮没有产出结果（见上方异常记录），本轮第六轮结论不成立。");
            return lines;
        }
        lines.add("  阶段：" + result.stage() + "；被试种子：" + result.seed()
                + "（种子来源：" + summary.seedSource() + "）");
        for (String line : result.setupLines()) {
            lines.add("  " + line);
        }
        lines.add("");
        lines.addAll(renderRound6Targets(result));
        lines.addAll(renderRound6OldMissed(result));
        lines.add("");
        lines.add("  ── 缓存隔离（用户口径第二十一节）");
        for (String line : result.cacheIsolationLines()) {
            lines.add("    " + line);
        }
        lines.add("");
        lines.add("  ── 性能记录（用户口径第二十节）");
        for (String line : result.perfLines()) {
            lines.add("    " + line);
        }
        lines.add("");
        lines.add("  ── 失败案例与 FIRST DIVERGENCE（无失败则写明「无」）");
        if (result.failureLines().isEmpty()) {
            lines.add("    无失败案例：本次运行的全部目标区块都逐 BlockPos 完全一致。");
        } else {
            for (String line : result.failureLines()) {
                lines.add("    " + line);
            }
        }
        lines.addAll(renderRound6Boundaries(result));
        return lines;
    }

    /** 【十-1】逐目标结果 + 合计判定。 */
    private static List<String> renderRound6Targets(PredictionRegressionSuite.Result result) {
        List<String> lines = new ArrayList<>();
        lines.add("  ── 逐目标结果（第六轮模型：目标 ± 写半径 viewer）");
        int truthTotal = 0;
        int predictedTotal = 0;
        int matchedTotal = 0;
        int missedTotal = 0;
        int extraTotal = 0;
        int exactChunks = 0;
        for (PredictionOutcome outcome : result.targets()) {
            BlockPosDiff diff = outcome.diff();
            truthTotal += diff.realCount();
            predictedTotal += diff.predictedCount();
            matchedTotal += diff.matched();
            missedTotal += diff.missed();
            extraTotal += diff.extra();
            if (diff.exact()) {
                exactChunks++;
            }
            lines.add("  ── 目标 (" + outcome.target().x() + "," + outcome.target().z() + ")"
                    + " 写半径 " + TargetViewerSet.writeRadius() + "（viewer " + outcome.viewers().size() + " 个）");
            lines.add("      真值：" + outcome.truth().cn());
            lines.add("      预测：" + outcome.predicted().size() + " 个；" + diff.cn()
                    + " → " + (diff.exact() ? "逐 BlockPos 完全一致" : "**不一致**"));
            lines.add("      跨区块写入：预测集里 " + outcome.crossChunkCount() + " 个坐标由邻 viewer 写入"
                    + "（按 viewer：" + (outcome.crossChunkByViewer().isEmpty() ? "无"
                    : outcome.crossChunkByViewer().toString()) + "）");
            lines.add("      本遍逐 viewer 新增（0 = 该 viewer 在本会话更早的目标区块里就已装饰过，"
                    + "本会话直接复用）：" + outcome.contributionByViewer());
            lines.add("      归属台账：" + outcome.provenance().size() + " 个坐标有 OreFeature 接受记录；未归属（非 OreFeature 来源，"
                    + "如化石 fossil_diamonds / 结构自带钻石）"
                    + TargetChunkPredictor.countUnattributed(outcome.predicted(), outcome.provenance()) + " 个");
            if (!diff.exact()) {
                lines.add("      漏报样本：" + PredictionOutcome.sample(outcome.missed(), 8));
                lines.add("      错报样本：" + PredictionOutcome.sample(outcome.extra(), 8));
                lines.add("      FIRST DIVERGENCE 取证｜漏报/错报坐标两侧方块：" + outcome.divergenceStates());
                lines.add("      FIRST DIVERGENCE 取证｜" + outcome.stateDiff());
            }
            if (!outcome.note().isEmpty()) {
                lines.add("      说明：" + outcome.note());
            }
        }
        lines.add("  ── 合计：" + result.targets().size() + " 个目标区块");
        lines.add("      真值 " + truthTotal + " / 预测 " + predictedTotal + " / 匹配 " + matchedTotal
                + " / 漏报 " + missedTotal + " / 错报 " + extraTotal);
        lines.add("      逐 BlockPos 完全一致的目标区块：" + exactChunks + "/" + result.targets().size());
        lines.add("      本轮判定：" + (missedTotal == 0 && extraTotal == 0 && exactChunks == result.targets().size()
                ? "全部逐 BlockPos 一致（FN = 0 / FP = 0）"
                : "**未达标**（禁止把本行读成「精准种子挖矿已实现」）"));
        return lines;
    }

    /** 【十-2】第四轮旧漏报的逐格找回情况（用「只装饰目标自己」的旧口径在本轮运行里现场复算）。 */
    private static List<String> renderRound6OldMissed(PredictionRegressionSuite.Result result) {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("  ── 第四轮旧漏报的找回情况（旧口径 = 只装饰目标区块自己，在本轮运行里现场复算，"
                + "不引用上一轮数字）");
        if (!result.baselineRan() || result.baselines().isEmpty()) {
            lines.add("    本次未跑旧口径复算（系统属性 yiyiaddon.seedpoc.round6.baseline=0）");
            return lines;
        }
        Map<String, PredictionOutcome> byTarget = new LinkedHashMap<>();
        for (PredictionOutcome outcome : result.targets()) {
            byTarget.put(key(outcome.target()), outcome);
        }
        int oldMissedTotal = 0;
        int recoveredTotal = 0;
        boolean any = false;
        for (PredictionOutcome baseline : result.baselines()) {
            Set<net.minecraft.core.BlockPos> oldMissed = baseline.missed();
            if (oldMissed.isEmpty()) {
                continue;
            }
            any = true;
            PredictionOutcome now = byTarget.get(key(baseline.target()));
            int recovered = 0;
            for (net.minecraft.core.BlockPos pos : oldMissed) {
                if (now != null && now.predicted().contains(pos)) {
                    recovered++;
                }
            }
            oldMissedTotal += oldMissed.size();
            recoveredTotal += recovered;
            lines.add("    目标 (" + baseline.target().x() + "," + baseline.target().z() + ")：旧口径 真值 "
                    + baseline.truth().total() + " / 预测 " + baseline.predicted().size() + " / 漏报 "
                    + oldMissed.size() + "；第六轮模型漏报 "
                    + (now == null ? "（该目标未跑第六轮模型）" : String.valueOf(now.missed().size()))
                    + " → 找回 " + recovered + "/" + oldMissed.size());
            for (net.minecraft.core.BlockPos pos : oldMissed) {
                OfflineOreAttribution.Write write = now == null ? null : now.provenance().get(pos);
                lines.add("      · (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")"
                        + "：写入方 viewer=" + (write == null ? "未归属（非 OreFeature 接受记录）"
                        : "(" + write.viewer().x() + "," + write.viewer().z() + ")")
                        + " / placed_feature=" + (write == null ? "未归属" : write.featurePath())
                        + " / 跨区块写入=" + (write == null ? "未知"
                        : (write.crossChunk(now.target()) ? "是" : "否"))
                        + " / 第六轮预测=" + (now != null && now.predicted().contains(pos) ? "存在" : "不存在")
                        + " / Truth=" + (now != null && now.truth().all().contains(pos) ? "存在" : "不存在")
                        + " / 该格两侧方块=" + (now == null ? "?"
                        : now.divergenceStates().getOrDefault(pos, "（一致，未取证）")));
            }
        }
        if (!any) {
            lines.add("    旧口径复算在本次目标集上没有任何漏报（旧口径 = 目标自己装饰 + 只读目标区块）。");
            return lines;
        }
        lines.add("    合计：旧口径漏报 " + oldMissedTotal + " 格 → 第六轮模型找回 " + recoveredTotal + " 格"
                + (oldMissedTotal == recoveredTotal ? "（全部找回）" : "（**未全部找回，必须逐格定位**）"));
        return lines;
    }

    /** 【十-3】其它钻石来源与结论边界。 */
    private static List<String> renderRound6Boundaries(PredictionRegressionSuite.Result result) {
        List<String> lines = new ArrayList<>();
        int unattributed = 0;
        int truthTotal = 0;
        for (PredictionOutcome outcome : result.targets()) {
            unattributed += TargetChunkPredictor.countUnattributed(outcome.predicted(), outcome.provenance());
            truthTotal += outcome.truth().total();
        }
        lines.add("");
        lines.add("  ── 其它钻石来源的处理（用户口径第十八节）");
        if (unattributed == 0) {
            lines.add("    预测侧：本次 " + truthTotal + " 个钻石坐标<b>全部</b>有 OreFeature 接受记录 ⇒ "
                    + "本批数据里没有其它钻石来源（化石 fossil_diamonds / 结构自带方块）参与，"
                    + "因此不存在「把其它来源当 OreFeature 漏报」的情形。");
        } else {
            lines.add("    预测侧：目标区块最终钻石里 " + unattributed + " 个坐标没有 OreFeature 接受记录，"
                    + "说明它们由别的来源写入（化石 fossil_diamonds 的结构处理器 / 结构自带方块等）；"
                    + "它们<b>已经包含在预测集里</b>，没有被当成 OreFeature 漏报。");
        }
        lines.add("    真值侧：真值来自「真实世界最终方块」，与来源无关，因此本轮的判据"
                + "（真值 = 最终世界目标区块内的 diamond_ore + deepslate_diamond_ore）对来源不做区分。");
        lines.add("    边界：归属台账只覆盖 OreFeature（canPlaceOre 接受的候选点）；"
                + "非 OreFeature 来源的写入无法逐点归属，报告里如实标为「未归属」。"
                + "该分支的实现已具备（有未归属坐标就会逐目标列出），只是本批数据恰好没有触发。");
        lines.add("  ── 结论边界");
        lines.add("    1. 本轮的判据是「目标区块内的最终自然钻石集合」逐 BlockPos 相等，"
                + "不是「离线世界与真实世界每一格都相同」（用户口径第十九节）。");
        lines.add("    2. 预测侧输入只有 版本 / Seed / 维度 / Target ChunkPos / 原版 worldgen 配置；"
                + "会话与真值读取在代码上完全分离（PredictionRegressionSuite 只做编排）。");
        lines.add("    3. 只测 26.1.2 主世界钻石；其它矿物、其它维度、26.2/26.3 一概未做。");
        if (truthTotal == 0) {
            lines.add("    4. 注意：本次真值合计为 0，说明目标集选在了没有钻石的地形上，"
                    + "该结果不能作为任何一致性结论。");
        }
        return lines;
    }

    private static String key(net.minecraft.world.level.ChunkPos pos) {
        return pos.x() + "," + pos.z();
    }

    /**
     * 对照表：同一批测试区块，旧口径与各快照口径并排。
     *
     * <p>这张表就是本轮交付的核心数据——「换输入状态」这一个变量带来的变化，逐区块看得见。</p>
     */
    private static List<String> renderComparison(List<ChunkOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        lines.add("【四、旧 PoC 与真实生成期快照 PoC 逐区块对照（核心结果）】");
        if (outcomes.isEmpty()) {
            lines.add("  无数据");
            return lines;
        }
        // 区块顺序与口径顺序都按首次出现顺序，保证报告可读且稳定
        Map<String, Map<String, ChunkOutcome>> table = new LinkedHashMap<>();
        Map<String, BlockPosDiff> perModeTotal = new LinkedHashMap<>();
        for (ChunkOutcome outcome : outcomes) {
            String chunkKey = outcome.pos().x() + "," + outcome.pos().z();
            table.computeIfAbsent(chunkKey, key -> new LinkedHashMap<>()).put(outcome.mode(), outcome);
            String shortName = shortMode(outcome.mode());
            perModeTotal.merge(shortName, outcome.diff(), (a, b) -> BlockPosDiff.sum(List.of(a, b)));
        }
        for (Map.Entry<String, Map<String, ChunkOutcome>> chunkEntry : table.entrySet()) {
            Map<String, ChunkOutcome> modes = chunkEntry.getValue();
            int realCount = modes.values().iterator().next().diff().realCount();
            lines.add("  区块 (" + chunkEntry.getKey() + ")  真值钻石 " + realCount + " 块");
            for (Map.Entry<String, ChunkOutcome> modeEntry : modes.entrySet()) {
                BlockPosDiff diff = modeEntry.getValue().diff();
                lines.add("      " + padRight(shortMode(modeEntry.getKey()), 24) + "："
                        + "预测 " + diff.predictedCount() + " / 匹配 " + diff.matched()
                        + " / 漏报 " + diff.missed() + " / 错报 " + diff.extra()
                        + " / 查全 " + percent(diff.recall()) + " / 查准 " + percent(diff.precision())
                        + (diff.exact() ? "  ← 逐 BlockPos 完全一致" : ""));
            }
        }
        lines.add("  ── 合计（10 个测试区块）");
        for (Map.Entry<String, BlockPosDiff> entry : perModeTotal.entrySet()) {
            BlockPosDiff diff = entry.getValue();
            lines.add("      " + padRight(entry.getKey(), 24) + "：" + diff.cn()
                    + " / 逐 BlockPos 完全一致：" + (diff.exact() ? "是" : "否"));
        }
        return lines;
    }

    /** 根因判定：只看数字，不给推断；判定前先看装置自证对照。 */
    private static List<String> renderVerdict(List<ChunkOutcome> outcomes) {
        List<String> lines = new ArrayList<>();
        if (outcomes.isEmpty()) {
            lines.add("  无数据，无法判定");
            return lines;
        }
        Map<String, BlockPosDiff> totals = new LinkedHashMap<>();
        Map<String, Integer> chunkCount = new LinkedHashMap<>();
        Map<String, Integer> exactChunks = new LinkedHashMap<>();
        for (ChunkOutcome outcome : outcomes) {
            String shortName = shortMode(outcome.mode());
            totals.merge(shortName, outcome.diff(), (a, b) -> BlockPosDiff.sum(List.of(a, b)));
            chunkCount.merge(shortName, 1, Integer::sum);
            if (outcome.diff().exact()) {
                exactChunks.merge(shortName, 1, Integer::sum);
            }
        }
        for (Map.Entry<String, BlockPosDiff> entry : totals.entrySet()) {
            BlockPosDiff diff = entry.getValue();
            int chunks = chunkCount.getOrDefault(entry.getKey(), 0);
            int exact = exactChunks.getOrDefault(entry.getKey(), 0);
            lines.add("  " + padRight(entry.getKey(), 24) + "：合计 " + diff.cn()
                    + "；逐 BlockPos 完全一致的区块 " + exact + "/" + chunks);
        }

        // ① 先判装置：对照口径的期望值是可判定的
        BlockPosDiff amethyst = totals.get(shortMode(SeedPocConstants.MODE_CONTROL_AMETHYST));
        BlockPosDiff stone = totals.get(shortMode(SeedPocConstants.MODE_CONTROL_STONE));
        BlockPosDiff baseline = totals.get(shortMode(SeedPocConstants.MODE_OLD_WORLD));
        lines.add("");
        lines.add("  装置自证（先于根因判定，对照口径的期望值是硬判据）：");
        boolean apparatusOk;
        if (amethyst == null || stone == null) {
            apparatusOk = false;
            lines.add("    未运行对照口径 → 无法自证装置是否把状态送进放置链路，本轮快照口径数字不可采信。");
        } else {
            lines.add("    对照1（中心区块整片改写为紫水晶块：不可替换、非空气）：预测合计 " + amethyst.predictedCount()
                    + " 个钻石（期望 0）");
            lines.add("    对照2（中心区块整片改写为石头：可替换）：预测合计 " + stone.predictedCount()
                    + " 个钻石（真值合计 " + stone.realCount() + "；期望显著多于真值）");
            boolean amethystOk = amethyst.predictedCount() == 0;
            boolean stoneOk = baseline == null || stone.predictedCount() > baseline.predictedCount();
            apparatusOk = amethystOk && stoneOk;
            lines.add("    ⇒ " + (apparatusOk
                    ? "装置有效：改写输入状态能让预测从 0 变到远超真值，说明放置链路确实以被安装的世界状态为依据。"
                    : "装置无效：改写输入状态没有让预测发生应有的变化，说明状态没有被送进放置链路，本轮快照口径结论作废。"));
        }

        String bestSnapshotMode = null;
        BlockPosDiff bestSnapshot = null;
        for (Map.Entry<String, BlockPosDiff> entry : totals.entrySet()) {
            String name = entry.getKey();
            if (!name.startsWith("口径1") && !name.startsWith("口径2") && !name.startsWith("口径3")) {
                continue;
            }
            if (bestSnapshot == null || entry.getValue().matched() > bestSnapshot.matched()) {
                bestSnapshot = entry.getValue();
                bestSnapshotMode = name;
            }
        }
        lines.add("");
        if (bestSnapshot == null) {
            lines.add("  判定：本次没有运行任何快照口径，无法判断根因假设");
            return lines;
        }
        int bestSnapshotChunks = chunkCount.getOrDefault(bestSnapshotMode, 0);
        int bestSnapshotExact = exactChunks.getOrDefault(bestSnapshotMode, 0);
        lines.add("  最好的快照口径：" + bestSnapshotMode);
        if (!apparatusOk) {
            lines.add("  判定：根因假设【无法判定】——装置自证未通过（或未运行），快照口径的数字不反映输入状态，");
            lines.add("        不得据此下结论；下一步是先修装置再重跑，而不是扩大工程。");
            return lines;
        }
        if (bestSnapshot.exact() && bestSnapshotExact == bestSnapshotChunks) {
            lines.add("  判定：根因假设【被证实】——全部测试区块在「真实生成期状态 + 当前 Seed 算法」下逐 BlockPos 完全一致。");
            lines.add("        下一步：研究如何在客户端仅凭 Seed 离线构造该阶段状态（WorldGenRegion /");
            lines.add("        ChunkStep / GenerationChunkHolder / StaticCache2D 与必要的 AccessWidener/Mixin）。");
        } else if (bestSnapshot.missed() + bestSnapshot.extra() <= Math.max(2, bestSnapshot.realCount() / 200)) {
            lines.add("  判定：根因假设【基本被证实】——最好的快照口径残留漏报 " + bestSnapshot.missed()
                    + " / 错报 " + bestSnapshot.extra() + "，已接近逐 BlockPos 一致但未完全一致；");
            lines.add("        残留项需先定位（优先怀疑：快照纵向覆盖边界、九遍装饰的执行顺序、结构放置）。");
        } else {
            lines.add("  判定：根因假设【未被完全证实】——最好的快照口径仍有漏报 " + bestSnapshot.missed()
                    + " / 错报 " + bestSnapshot.extra() + "；按本轮结论，不得扩大工程量，先继续定位遗漏输入。");
        }
        return lines;
    }

    /** 口径短名（未登记的标签原样返回）。 */
    private static String shortMode(String mode) {
        return SHORT_MODE.getOrDefault(mode, mode);
    }

    private static String padRight(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        return text + " ".repeat(width - text.length());
    }

    private static String percent(double value) {
        return String.format(java.util.Locale.ROOT, "%.2f%%", value * 100.0);
    }

    /** 把报告写进日志与运行目录文件；返回文件路径（写失败返回 null）。 */
    public static Path output(List<String> lines) {
        return output(lines, SeedPocConstants.REPORT_FILE_NAME);
    }

    /**
     * 把报告写进日志与运行目录下的指定文件。
     *
     * <p>第六轮按「阶段 + 种子」分开落盘：泛化阶段一个种子一个进程，
     * 文件名带上种子才可能逐份留档、事后汇总。</p>
     */
    public static Path output(List<String> lines, String fileName) {
        for (String line : lines) {
            LOGGER.info("{}｜{}", SeedPocConstants.LOG_KEY, line);
        }
        try {
            Path path = FabricLoader.getInstance().getGameDir().resolve(fileName);
            Files.write(path, lines, StandardCharsets.UTF_8);
            LOGGER.info("{}｜报告已落盘：{}", SeedPocConstants.LOG_KEY, path.toAbsolutePath());
            return path;
        } catch (IOException | RuntimeException error) {
            LOGGER.warn("{}｜报告落盘失败（日志里仍有全文）", SeedPocConstants.LOG_KEY, error);
            return null;
        }
    }
}
