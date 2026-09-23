package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第五轮 · 报告输出。
 *
 * <p>报告分两部分落盘：</p>
 * <ol>
 *     <li><b>本轮实验报告</b>（{@code seedpoc-顺序实验报告-<场景>.txt}）——按用户口径第二十八节的
 *         25 个必答项逐条给出，本进程能自证的全部写实，需要跨进程才能判定的直接引用比较器结果；</li>
 *     <li><b>跨世界对比矩阵</b>（{@code seedpoc-顺序对比矩阵.txt}）——把运行目录里全部真值一起比，
 *         每跑一轮都会用「到目前为止」的全部真值重算一次，因此最后一个场景跑完时该文件就是完整结果。</li>
 * </ol>
 *
 * <p>绝不覆盖前几轮的报告：本轮所有输出都用独立文件名（前缀 {@code seedpoc-顺序}）。</p>
 */
public final class ChunkOrderReport {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 本轮报告文件名前缀。 */
    public static final String REPORT_PREFIX = "seedpoc-顺序实验报告-";

    /** 跨世界对比矩阵文件名。 */
    public static final String MATRIX_FILE = "seedpoc-顺序对比矩阵.txt";

    private ChunkOrderReport() {
    }

    /**
     * 渲染并落盘本轮报告 + 对比矩阵。
     *
     * @param scenarioId 本轮场景名
     * @param scenario   本轮场景定义（请求顺序）
     * @param thisRun    本轮各目标区块的真值
     * @param allTruths  运行目录里全部真值（含本轮）
     * @param notes      驱动器记录的补充说明
     * @param elapsedMs  本轮实验耗时
     */
    public static Path output(String scenarioId, ChunkOrderScenario scenario,
                              List<ChunkOrderTruth> thisRun, List<ChunkOrderTruth> allTruths,
                              List<String> notes, long elapsedMs) {
        List<String> lines = new ArrayList<>();
        lines.add("种子挖矿 PoC 第五轮 · Chunk 生成顺序决定性实验 · 实验报告");
        lines.add("场景：" + scenarioId + "（" + scenario.nameCn() + "）");
        lines.add("");
        lines.add("本轮唯一问题：同版本 / 同种子 / 同原版 worldgen 配置 / 同目标区块，"
                + "只改变 Chunk 被请求（因而被装饰）的先后顺序，最终钻石 BlockPos 会不会变。");
        lines.add("");

        if (thisRun.isEmpty()) {
            lines.add("【警告】本轮没有取到任何目标区块真值，报告不成立。");
        }

        List<String> envLines = environmentSection(allTruths);
        lines.add("一、实验环境与版本（口径第 1~5、14 项）");
        lines.addAll(envLines);
        lines.add("");

        lines.add("二、本轮世界与新鲜度自证（口径第 6~8、22 项）");
        for (ChunkOrderTruth truth : thisRun) {
            lines.add("  目标 (" + truth.target().x() + "," + truth.target().z() + ")");
            lines.add("    存档名：" + truth.worldName());
            lines.add("    存档路径：" + truth.worldPath());
            lines.add("    全新世界：" + (truth.worldFresh() ? "是" : "否") + "；" + truth.worldNote());
            lines.add("    请求前目标 3x3 是否全部未生成：" + (truth.targetAreaUntouched() ? "是（9/9 未生成）" : "否"));
            lines.add("    请求前目标区 region 文件是否已存在：" + (truth.regionExistedBefore() ? "是（世界不干净）" : "否"));
        }
        lines.add("");

        lines.add("三、每个场景的 Chunk 请求顺序（口径第 9 项）");
        lines.add("  场景字母含义：" + scenario.nameCn());
        lines.add("  请求顺序：" + scenarioCn(scenario));
        lines.add("  目标区块在第 " + scenario.targetStep() + " 步被请求（= 第 " + scenario.targetStep()
                + " 步被装饰）");
        lines.add("  方法论说明（本轮实测发现，必须先读）：26.1.2 里「请求一个区块到 FULL」会建一个");
        lines.add("    ChunkGenerationTask 并逐层铺 EMPTY…FULL，每层覆盖半径 ="
                + " ChunkPyramid.GENERATION_PYRAMID");
        lines.add("    .getStepTo(FULL).getAccumulatedRadiusOf(该状态)；用 ChunkStep#buildAccumulatedDependencies");
        lines.add("    逐级算出来，FULL 这一步的累积依赖是 [FULL, INITIALIZE_LIGHT, CARVERS, BIOMES,"
                + " STRUCTURE_STARTS×8]，");
        lines.add("    于是 FEATURES 的层半径 = 1 —— 也就是说「请求 1 个区块」等价于「装饰它自己 + 一圈邻域"
                + "（3x3）」，");
        lines.add("    原版不存在「把 3x3 里的某一个区块单独挑出来装饰」的操作。");
        lines.add("  因此本轮把「生成顺序」这个变量落在可实施、且仍然完全走原版流水线的形式上：");
        lines.add("    用「距目标 2 区块」的请求（其 3x3 覆盖目标的一圈邻域、但不覆盖目标本身）先装饰目标的"
                + "某一侧/全部邻域，");
        lines.add("    最后再请求目标区块 —— 自变量 = 「目标 3x3 里有几个邻区块在目标之前被装饰」。");
        lines.add("  实测佐证：场景 A 的第一条请求就让目标 3x3 的 9 个区块在同一步内全部拿到装饰批号，"
                + "其余请求本步新增均为 0。");
        lines.add("");

        lines.add("四、真实装饰批号与逐步骤（口径第 10 项）");
        for (ChunkOrderTruth truth : thisRun) {
            lines.add("  目标 (" + truth.target().x() + "," + truth.target().z()
                    + ")｜3x3 装饰批号（北一行 / 中一行 / 南一行，- 表示该区块本次未装饰）："
                    + batchesCn(truth));
            lines.add("    自变量：目标批号 " + truth.targetBatch() + "；在目标之前被装饰的邻区块 "
                    + truth.neighborsBeforeTarget().size() + " 个（" + truth.neighborsBeforeTargetCn()
                    + "）；在目标之后装饰的邻区块 " + truth.neighborsAfterTargetCount() + " 个");
            for (ChunkOrderTruth.Step step : truth.steps()) {
                lines.add("    第 " + step.index() + " 步：请求 (" + step.chunk().x() + "," + step.chunk().z()
                        + ")（本步装饰以它为中心的 3x3）｜该区块在目标 3x3 内时的装饰批号 "
                        + (step.batch() > 0 ? step.batch() : "（不在目标 3x3 内）")
                        + "｜本步装饰到的目标 3x3 区块（区块:批号）"
                        + (step.decorated().isEmpty() ? "：无" : "：" + String.join("  ", step.decorated()))
                        + "｜本步目标区块新增钻石 " + step.added()
                        + " 格｜耗时 " + step.millis() + " ms");
            }
            lines.add("    目标区块最终钻石：diamond_ore " + truth.diamondOre().size()
                    + " + deepslate_diamond_ore " + truth.deepslateDiamondOre().size()
                    + " = " + truth.allDiamonds().size());
            lines.add("    完整 BlockPos 集合见真值文件：" + truth.file().getFileName());
            lines.add("    写入溯源台账条数：" + truth.provenance().size() + "（只记真正写进世界的候选点）");
        }
        lines.add("");

        lines.add("五、跨世界比较（口径第 11~17、25 项）");
        lines.addAll(ChunkOrderComparator.render(allTruths));
        lines.add("");

        lines.add("五之二、跨区块写入的存在性与「顺序无关性」直接读数（口径第 17、26 项）");
        lines.addAll(crossChunkSection(allTruths));
        lines.add("");

        lines.add("六、驱动器补充说明");
        if (notes.isEmpty()) {
            lines.add("  无");
        } else {
            for (String note : notes) {
                lines.add("  " + note);
            }
        }
        lines.add("  本轮耗时：" + elapsedMs + " ms");
        lines.add("");

        lines.add("七、尚未验证事项（不得当成已验证）");
        for (String item : unverified()) {
            lines.add("  - " + item);
        }

        for (String line : lines) {
            LOGGER.info("{}｜顺序实验报告｜{}", SeedPocConstants.LOG_KEY, line);
        }
        Path report = write(REPORT_PREFIX + scenarioId + ChunkOrderTruth.TRUTH_SUFFIX, lines);
        write(MATRIX_FILE, ChunkOrderComparator.render(allTruths));
        return report;
    }

    /** 环境与版本证据（同时校验各世界环境是否一致）。 */
    private static List<String> environmentSection(List<ChunkOrderTruth> allTruths) {
        List<String> lines = new ArrayList<>();
        ChunkOrderTruth.Environment env = allTruths.isEmpty() ? null : allTruths.get(0).env();
        if (env != null) {
            lines.add("  Minecraft：" + env.mcName() + "（id " + env.mcId() + "）");
            lines.add("  Fabric Loader：" + env.fabricLoader() + "；Fabric API：" + env.fabricApi());
            lines.add("  Java：" + env.javaVersion());
            lines.add("  C2ME 是否存在：" + (env.c2me() ? "存在" : "不存在"));
            lines.add("  ModernFix 是否存在：" + (env.modernfix() ? "存在" : "不存在"));
            lines.add("  实际启用 Mods（" + env.mods().size() + " 个）：");
            for (String mod : env.mods()) {
                lines.add("    " + mod);
            }
            lines.add("  Mods 列表 hash（SHA-256，id@版本 排序后拼接）：" + env.modsHash());
        }
        Set<String> hashes = new LinkedHashSet<>();
        Set<String> mcNames = new LinkedHashSet<>();
        for (ChunkOrderTruth truth : allTruths) {
            hashes.add(truth.env().modsHash());
            mcNames.add(truth.env().mcId() + "/" + truth.env().mcName());
        }
        lines.add("  跨世界环境一致性：Mods hash 共 " + hashes.size() + " 种、MC 版本共 " + mcNames.size()
                + " 种 → " + (hashes.size() == 1 && mcNames.size() == 1
                ? "一致（可作为同环境对照）"
                : "不一致 —— 环境变化与生成顺序变化混在一起，结论不成立"));
        return lines;
    }

    /** 请求顺序文本。 */
    private static String scenarioCn(ChunkOrderScenario scenario) {
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < scenario.order().size(); index++) {
            if (index > 0) {
                text.append(" → ");
            }
            var pos = scenario.order().get(index);
            text.append('(').append(pos.x()).append(',').append(pos.z()).append(')');
        }
        return text.toString();
    }

    /** 3x3 批号文本。 */
    private static String batchesCn(ChunkOrderTruth truth) {
        StringBuilder text = new StringBuilder();
        for (int dz = -1; dz <= 1; dz++) {
            if (dz > -1) {
                text.append(" / ");
            }
            for (int dx = -1; dx <= 1; dx++) {
                if (dx > -1) {
                    text.append(' ');
                }
                Integer batch = truth.batches().get(
                        new net.minecraft.world.level.ChunkPos(truth.target().x() + dx, truth.target().z() + dz));
                text.append(batch == null ? "-" : String.valueOf(batch));
            }
        }
        return text.toString();
    }

    /**
     * 跨区块写入的证据节：把「目标区块里的钻石是谁写的」与「写的时候目标是否已经装饰完」并排列出。
     *
     * <p>第四轮口径二的 11 格漏报，成因类型就是「邻区块按 FEATURES 写半径 1 把钻石写进目标区块」。
     * 本节用写入溯源台账把这件事逐格钉住，并直接回答本轮的问题：<b>改变生成顺序之后，
     * 「谁写的、写了哪几格」有没有变</b>。</p>
     */
    private static List<String> crossChunkSection(List<ChunkOrderTruth> allTruths) {
        List<String> lines = new ArrayList<>();
        java.util.Map<String, List<ChunkOrderTruth>> byTarget = new java.util.TreeMap<>();
        for (ChunkOrderTruth truth : allTruths) {
            byTarget.computeIfAbsent(truth.target().x() + "," + truth.target().z(), key -> new ArrayList<>())
                    .add(truth);
        }
        if (byTarget.isEmpty()) {
            lines.add("  （没有真值，无法读数）");
            return lines;
        }
        for (java.util.Map.Entry<String, List<ChunkOrderTruth>> entry : byTarget.entrySet()) {
            List<ChunkOrderTruth> group = entry.getValue();
            lines.add("  目标区块 (" + entry.getKey().replace(",", ", ") + ")");
            // 写入方（邻域）→ 该写入方在各场景里「相对目标早/晚」的关系集合，以及它写的位置集合
            java.util.Map<String, java.util.Set<String>> relations = new java.util.LinkedHashMap<>();
            java.util.Map<String, java.util.Set<net.minecraft.core.BlockPos>> positions =
                    new java.util.LinkedHashMap<>();
            for (ChunkOrderTruth truth : group) {
                lines.add("    " + truth.scenario() + "：写入方分布 = " + truth.writeSourcesCn());
                int targetBatch = truth.targetBatch();
                for (java.util.Map.Entry<net.minecraft.core.BlockPos, ChunkOrderTruth.Write> write
                        : truth.provenance().entrySet()) {
                    ChunkOrderTruth.Write record = write.getValue();
                    if (record.viewer().equals(truth.target())) {
                        continue;
                    }
                    String key = "(" + record.viewer().x() + "," + record.viewer().z() + ")"
                            + " 批" + record.batch() + " " + record.featurePath();
                    String relation = record.batch() < targetBatch ? "早于目标装饰" : "晚于目标装饰";
                    relations.computeIfAbsent(key, ignored -> new java.util.LinkedHashSet<>()).add(relation);
                    java.util.Set<net.minecraft.core.BlockPos> set =
                            positions.computeIfAbsent(key + "｜" + relation, ignored -> new java.util.LinkedHashSet<>());
                    set.add(write.getKey());
                }
            }
            if (relations.isEmpty()) {
                lines.add("    → 该目标区块在所有场景里都没有跨区块写入（全部钻石都由目标自己装饰写下）");
                continue;
            }
            for (java.util.Map.Entry<String, java.util.Set<String>> writeEntry : relations.entrySet()) {
                String key = writeEntry.getKey();
                java.util.Set<String> relationSet = writeEntry.getValue();
                StringBuilder texts = new StringBuilder();
                for (String relation : relationSet) {
                    java.util.Set<net.minecraft.core.BlockPos> set = positions.get(key + "｜" + relation);
                    if (texts.length() > 0) {
                        texts.append("；");
                    }
                    texts.append(relation).append(" 共 ").append(set == null ? 0 : set.size()).append(" 格：")
                            .append(sampleText(set));
                }
                lines.add("    → 写入方 " + key + " 在不同场景里相对目标的先后 ="
                        + String.join(" / ", relationSet) + "；写入内容：" + texts);
            }
            lines.add("    → 结论：若同一写入方在「早于目标」与「晚于目标」两种情形下写入的格数与坐标完全相同，"
                    + "则跨区块写入与生成顺序无关。");
        }
        return lines;
    }

    /** 坐标样本文本（最多 8 个）。 */
    private static String sampleText(java.util.Set<net.minecraft.core.BlockPos> positions) {
        if (positions == null || positions.isEmpty()) {
            return "无";
        }
        StringBuilder text = new StringBuilder();
        int taken = 0;
        for (net.minecraft.core.BlockPos pos : positions) {
            if (taken++ >= 8) {
                text.append(" …");
                break;
            }
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append('(').append(pos.getX()).append(',').append(pos.getY()).append(',').append(pos.getZ())
                    .append(')');
        }
        return text.toString();
    }

    /** 本轮口径下明确未验证的条目（如实写进报告，避免被当成已验证）。 */
    private static List<String> unverified() {
        return List.of(
                "只验证了主世界钻石（diamond_ore + deepslate_diamond_ore），其它矿物 / 下界 / 末地未验证",
                "只验证了目标区块内的钻石，邻域区块（跨区块写入的中间层）未逐格验证",
                "只验证了 1 个种子（20260922）× 2 个目标区块 × 8 个全新世界；不能外推到全部种子",
                "每个场景只重复 2 个全新世界（用户口径第二十三节的下限）",
                "同一 FEATURES 层内的并发执行会让相邻区块的装饰批号互换（批内抖动），"
                        + "因此「谁的批号更小」在同场景两次运行间可能微变；本轮据此只把「最终集合一致」当判据",
                "未验证真实多人服务器环境（本轮全程单人集成服务端）",
                "未做 26.2 / 26.3 适配",
                "未接入任何正式业务：本轮结论不改变自动挖矿的现有行为");
    }

    /** 写文件（失败只记日志，绝不抛出）。 */
    private static Path write(String fileName, List<String> lines) {
        try {
            Path path = FabricLoader.getInstance().getGameDir().resolve(fileName);
            Files.write(path, lines, StandardCharsets.UTF_8);
            LOGGER.info("{}｜报告已落盘：{}", SeedPocConstants.LOG_KEY, path.toAbsolutePath());
            return path;
        } catch (IOException | RuntimeException error) {
            LOGGER.warn("{}｜报告落盘失败（日志里仍有全文）：{}", SeedPocConstants.LOG_KEY, fileName, error);
            return null;
        }
    }
}
