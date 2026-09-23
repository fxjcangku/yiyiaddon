package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第五轮 · 单次实验的真值载体（也是跨世界比较的唯一凭据）。
 *
 * <p><b>为什么真值必须落盘成独立文件</b>：本轮的实验单元是「一个全新的世界目录 + 一种 Chunk 请求顺序」。
 * 一个客户端进程只能干净地建一个全新世界（世界建好就不再是全新），所以每种顺序都要单独启动一次
 * {@code runClient}。跨世界的比较因此只能在<b>进程之外</b>做——每次运行把自己的真值写成一份文件，
 * 比较器再把运行目录里全部真值文件读回来统一比对。</p>
 *
 * <p><b>文件格式</b>：一行一条，含机器可读字段。刻意保持「纯文本 + 单行字段」，便于人工核对与
 * 事后用别的工具复核；{@link #parse(List)} 只接受自家 {@link #render()} 写出的格式。</p>
 */
public record ChunkOrderTruth(String scenario,
                              long seed,
                              ChunkPos target,
                              Environment env,
                              String worldName,
                              String worldPath,
                              boolean worldFresh,
                              String worldNote,
                              boolean targetAreaUntouched,
                              boolean regionExistedBefore,
                              List<ChunkPos> order,
                              Map<ChunkPos, Integer> batches,
                              List<Step> steps,
                              Set<BlockPos> diamondOre,
                              Set<BlockPos> deepslateDiamondOre,
                              Map<BlockPos, Write> provenance) {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 真值文件名前缀（写完不覆盖旧文件；同一场景重复跑会覆盖自己那一份，这是预期的）。 */
    public static final String TRUTH_PREFIX = "seedpoc-顺序真值-";

    /** 真值文件名后缀。 */
    public static final String TRUTH_SUFFIX = ".txt";

    /** 格式版本（解析器据此拒收不明格式）。 */
    public static final int FORMAT = 1;

    /** 实验环境证据（用户口径第十四节：三个世界必须证明环境一致）。 */
    public record Environment(String mcId,
                              String mcName,
                              String fabricLoader,
                              String fabricApi,
                              String javaVersion,
                              String modsHash,
                              List<String> mods,
                              boolean c2me,
                              boolean modernfix) {
    }

    /**
     * 一次「请求某个区块」的实验步。
     *
     * @param index     步序（1 起）
     * @param chunk     本步请求的区块
     * @param batch     本步该区块真实装饰的批号（-1 表示不在目标 3x3 内、未登记）
     * @param added     本步结束后「目标区块内新增」的钻石格数
     * @param millis    本步阻塞耗时（毫秒）
     * @param decorated 本步内新拿到装饰批号的目标 3x3 区块（形如 {@code "2999,3000:2"}）：
     *                  它是「生成顺序真的变了」的最直接证据
     */
    public record Step(int index, ChunkPos chunk, int batch, int added, long millis, List<String> decorated) {
    }

    /** 写入溯源（见 {@link ChunkOrderJournal.Write}）。 */
    public record Write(ChunkPos viewer, String featurePath, int batch, String preStateId, boolean accepted) {
    }

    /** 比较用的唯一键：场景 + 目标区块。 */
    public String key() {
        return scenario + "|" + target.x() + "," + target.z();
    }

    /** 全部钻石（两种方块合并）。 */
    public Set<BlockPos> allDiamonds() {
        Set<BlockPos> all = new LinkedHashSet<>(diamondOre);
        all.addAll(deepslateDiamondOre);
        return all;
    }

    /**
     * 目标区块的装饰批号；未记录返回 -1。
     *
     * <p>批号是「谁先装饰」的唯一凭据：{@code applyBiomeDecoration} 每进入一次就取一个新号。</p>
     */
    public int targetBatch() {
        Integer batch = batches.get(target);
        return batch == null ? -1 : batch;
    }

    /**
     * 目标 3x3 里<b>批号小于目标自己</b>的邻区块（按批号升序）。
     *
     * <p>这就是本轮实验的自变量：只有这些邻区块「在目标被装饰之前」就已经装饰完，
     * 它们的地物才会写进还处在 {@code CARVERS} 状态的目标区块、并因此改变目标自己装饰时
     * 读到的方块状态（进而改变 {@code OreFeature} 的随机数消耗）。</p>
     */
    public List<ChunkPos> neighborsBeforeTarget() {
        int targetBatch = targetBatch();
        List<ChunkPos> before = new ArrayList<>();
        if (targetBatch < 0) {
            return before;
        }
        for (Map.Entry<ChunkPos, Integer> entry : batches.entrySet()) {
            if (entry.getKey().equals(target)) {
                continue;
            }
            Integer batch = entry.getValue();
            if (batch != null && batch >= 0 && batch < targetBatch) {
                before.add(entry.getKey());
            }
        }
        before.sort((left, right) -> {
            int leftBatch = batches.getOrDefault(left, -1);
            int rightBatch = batches.getOrDefault(right, -1);
            if (leftBatch != rightBatch) {
                return Integer.compare(leftBatch, rightBatch);
            }
            if (left.x() != right.x()) {
                return Integer.compare(left.x(), right.x());
            }
            return Integer.compare(left.z(), right.z());
        });
        return before;
    }

    /** 目标之前的邻区块清单文本（报告用）。 */
    public String neighborsBeforeTargetCn() {
        List<ChunkPos> before = neighborsBeforeTarget();
        if (before.isEmpty()) {
            return "无";
        }
        StringBuilder text = new StringBuilder();
        for (ChunkPos pos : before) {
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append('(').append(pos.x()).append(',').append(pos.z()).append(" 批")
                    .append(batches.getOrDefault(pos, -1)).append(')');
        }
        return text.toString();
    }

    /** 目标之后装饰的邻区块个数（3x3 内、批号大于目标的邻区块）。 */
    public int neighborsAfterTargetCount() {
        int targetBatch = targetBatch();
        int after = 0;
        for (Map.Entry<ChunkPos, Integer> entry : batches.entrySet()) {
            if (entry.getKey().equals(target)) {
                continue;
            }
            Integer batch = entry.getValue();
            if (batch != null && batch > targetBatch) {
                after++;
            }
        }
        return after;
    }

    /**
     * 目标区块内钻石的「写入方」分布（写入方区块 → 格数，按格数降序）。
     *
     * <p><b>这是本轮的第二个核心读数</b>：目标区块里的钻石有一部分是<b>邻区块</b>装饰时按 FEATURES
     * 写半径 1 写进来的（第四轮口径二漏报 11 格的同一类现象）。把写入方列出来，就能看清
     * 「换一个生成顺序」到底有没有改变「谁写的、写了多少」。</p>
     */
    public Map<ChunkPos, Integer> writeSources() {
        Map<ChunkPos, Integer> counts = new LinkedHashMap<>();
        for (Write write : provenance.values()) {
            counts.merge(write.viewer(), 1, Integer::sum);
        }
        List<Map.Entry<ChunkPos, Integer>> ordered = new ArrayList<>(counts.entrySet());
        ordered.sort((left, right) -> Integer.compare(right.getValue(), left.getValue()));
        Map<ChunkPos, Integer> sorted = new LinkedHashMap<>();
        for (Map.Entry<ChunkPos, Integer> entry : ordered) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    /** 写入方分布文本（报告用）。 */
    public String writeSourcesCn() {
        Map<ChunkPos, Integer> sources = writeSources();
        if (sources.isEmpty()) {
            return "无（无溯源记录）";
        }
        StringBuilder text = new StringBuilder();
        for (Map.Entry<ChunkPos, Integer> entry : sources.entrySet()) {
            if (text.length() > 0) {
                text.append("，");
            }
            boolean self = entry.getKey().equals(target);
            text.append(self ? "目标自身" : "邻域").append('(').append(entry.getKey().x()).append(',')
                    .append(entry.getKey().z()).append(')').append(' ')
                    .append(entry.getValue()).append(" 格");
        }
        return text.toString();
    }

    // ── 落盘 ────────────────────────────────────────────────────────────────

    /**
     * 本份真值的文件路径。
     *
     * <p>文件名必须带上目标区块：一个进程要为多个目标区块各写一份真值，
     * 只用「场景」当文件名会让后一个目标把前一个覆盖掉（这正是首轮实测踩到的坑）。</p>
     */
    public Path file() {
        return FabricLoader.getInstance().getGameDir()
                .resolve(TRUTH_PREFIX + scenario + "_" + target.x() + "_" + target.z() + TRUTH_SUFFIX);
    }

    /** 把真值写进运行目录；返回文件路径（写失败返回 null）。 */
    public Path write() {
        List<String> lines = render();
        for (String line : lines) {
            LOGGER.info("{}｜顺序实验真值｜{}", SeedPocConstants.LOG_KEY, line);
        }
        try {
            Path path = file();
            Files.write(path, lines, StandardCharsets.UTF_8);
            LOGGER.info("{}｜顺序实验真值已落盘：{}", SeedPocConstants.LOG_KEY, path.toAbsolutePath());
            return path;
        } catch (IOException | RuntimeException error) {
            LOGGER.warn("{}｜顺序实验真值落盘失败", SeedPocConstants.LOG_KEY, error);
            return null;
        }
    }

    /** 渲染成文本行。 */
    public List<String> render() {
        List<String> lines = new ArrayList<>();
        lines.add("# 种子挖矿 PoC 第五轮 · Chunk 生成顺序决定性实验 · 单次实验真值（机器可读，请勿手改）");
        lines.add("FORMAT " + FORMAT);
        lines.add("SCENARIO " + scenario);
        lines.add("SEED " + seed);
        lines.add("TARGET " + target.x() + " " + target.z());
        lines.add("WORLD_NAME " + worldName);
        lines.add("WORLD_PATH " + worldPath);
        lines.add("WORLD_FRESH " + (worldFresh ? 1 : 0));
        lines.add("WORLD_NOTE " + worldNote);
        lines.add("TARGET_UNTOUCHED " + (targetAreaUntouched ? 1 : 0));
        lines.add("REGION_EXISTED_BEFORE " + (regionExistedBefore ? 1 : 0));
        lines.add("MC_ID " + env.mcId());
        lines.add("MC_NAME " + env.mcName());
        lines.add("FABRIC_LOADER " + env.fabricLoader());
        lines.add("FABRIC_API " + env.fabricApi());
        lines.add("JAVA " + env.javaVersion());
        lines.add("MODS_HASH " + env.modsHash());
        lines.add("C2ME " + (env.c2me() ? 1 : 0));
        lines.add("MODERNFIX " + (env.modernfix() ? 1 : 0));
        for (String mod : env.mods()) {
            lines.add("MOD " + mod);
        }
        for (ChunkPos pos : order) {
            lines.add("ORDER " + pos.x() + " " + pos.z());
        }
        for (Map.Entry<ChunkPos, Integer> entry : batches.entrySet()) {
            ChunkPos pos = entry.getKey();
            lines.add("DECOR " + (pos.x() - target.x()) + " " + (pos.z() - target.z()) + " " + entry.getValue());
        }
        for (Step step : steps) {
            lines.add("STEP " + step.index() + " " + step.chunk().x() + " " + step.chunk().z() + " "
                    + step.batch() + " " + step.added() + " " + step.millis() + " "
                    + (step.decorated().isEmpty() ? "-" : String.join(";", step.decorated())));
        }
        Map<BlockPos, Integer> appearance = appearanceMap();
        for (BlockPos pos : diamondOre) {
            lines.add("D diamond_ore " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " "
                    + appearance.getOrDefault(pos, -1));
        }
        for (BlockPos pos : deepslateDiamondOre) {
            lines.add("D deepslate_diamond_ore " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " "
                    + appearance.getOrDefault(pos, -1));
        }
        for (Map.Entry<BlockPos, Write> entry : provenance.entrySet()) {
            BlockPos pos = entry.getKey();
            Write write = entry.getValue();
            lines.add("P " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " "
                    + write.viewer().x() + " " + write.viewer().z() + " " + write.batch() + " "
                    + write.featurePath() + " " + write.preStateId() + " " + (write.accepted() ? 1 : 0));
        }
        lines.add("TOTAL_DIAMOND_ORE " + diamondOre.size());
        lines.add("TOTAL_DEEPSLATE_DIAMOND_ORE " + deepslateDiamondOre.size());
        lines.add("TOTAL " + allDiamonds().size());
        lines.add("END");
        return lines;
    }

    /**
     * 位置 → 首次出现步序。
     *
     * <p>算法：从第 1 步开始累积 {@code added} 计数，最终集合的遍历顺序与「被写入的先后」一致，
     * 因此第 k 步覆盖的区间就归第 k 步。矿石一旦写入不会被后来的 feature 覆盖（矿石不在可替换标签里），
     * 所以「首次出现」= 「最终仍存在」。</p>
     */
    public Map<BlockPos, Integer> appearanceMap() {
        Map<BlockPos, Integer> result = new LinkedHashMap<>();
        List<BlockPos> all = new ArrayList<>(allDiamonds());
        int cursor = 0;
        for (Step step : steps) {
            int end = Math.min(all.size(), cursor + Math.max(0, step.added()));
            for (int index = cursor; index < end; index++) {
                result.put(all.get(index), step.index());
            }
            cursor = end;
        }
        return result;
    }

    // ── 读盘 ────────────────────────────────────────────────────────────────

    /** 运行目录里已存在的全部真值文件。 */
    public static List<Path> truthFiles() {
        Path dir = FabricLoader.getInstance().getGameDir();
        if (!Files.isDirectory(dir)) {
            return List.of();
        }
        List<Path> found = new ArrayList<>();
        try (Stream<Path> stream = Files.list(dir)) {
            for (Path path : stream.toList()) {
                String name = path.getFileName().toString();
                if (name.startsWith(TRUTH_PREFIX) && name.endsWith(TRUTH_SUFFIX)) {
                    found.add(path);
                }
            }
        } catch (IOException error) {
            LOGGER.warn("{}｜真值文件列举失败", SeedPocConstants.LOG_KEY, error);
            return List.of();
        }
        found.sort(java.util.Comparator.comparing(path -> path.getFileName().toString()));
        return found;
    }

    /** 读回运行目录里全部真值；坏文件跳过并记日志（绝不让一份坏文件毁掉整轮报告）。 */
    public static List<ChunkOrderTruth> loadAll() {
        List<ChunkOrderTruth> result = new ArrayList<>();
        for (Path path : truthFiles()) {
            try {
                ChunkOrderTruth truth = parse(Files.readAllLines(path, StandardCharsets.UTF_8));
                if (truth != null) {
                    result.add(truth);
                } else {
                    LOGGER.warn("{}｜真值文件格式不符，已跳过：{}", SeedPocConstants.LOG_KEY, path);
                }
            } catch (IOException | RuntimeException error) {
                LOGGER.warn("{}｜真值文件读取失败，已跳过：{}", SeedPocConstants.LOG_KEY, path, error);
            }
        }
        result.sort(java.util.Comparator.comparing(truth -> truth.scenario() + "|" + truth.target().x()
                + "," + truth.target().z()));
        return result;
    }

    /** 解析一份真值文件；格式不符返回 null。 */
    public static ChunkOrderTruth parse(List<String> lines) {
        String scenario = null;
        long seed = 0L;
        ChunkPos target = null;
        String worldName = "";
        String worldPath = "";
        boolean worldFresh = false;
        StringBuilder worldNote = new StringBuilder();
        boolean untouched = false;
        boolean regionExisted = false;
        String mcId = "";
        String mcName = "";
        String fabricLoader = "";
        String fabricApi = "";
        String javaVersion = "";
        String modsHash = "";
        boolean c2me = false;
        boolean modernfix = false;
        List<String> mods = new ArrayList<>();
        List<ChunkPos> order = new ArrayList<>();
        Map<ChunkPos, Integer> batches = new LinkedHashMap<>();
        List<Step> steps = new ArrayList<>();
        Set<BlockPos> diamondOre = new LinkedHashSet<>();
        Set<BlockPos> deepslate = new LinkedHashSet<>();
        Map<BlockPos, Write> provenance = new LinkedHashMap<>();
        int format = -1;

        try {
            for (String raw : lines) {
                String line = raw.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int space = line.indexOf(' ');
                String key = space < 0 ? line : line.substring(0, space);
                String rest = space < 0 ? "" : line.substring(space + 1);
                String[] parts = rest.isEmpty() ? new String[0] : rest.split(" ", -1);
                switch (key) {
                    case "FORMAT" -> format = Integer.parseInt(parts[0]);
                    case "SCENARIO" -> scenario = rest;
                    case "SEED" -> seed = Long.parseLong(parts[0]);
                    case "TARGET" -> target = new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    case "WORLD_NAME" -> worldName = rest;
                    case "WORLD_PATH" -> worldPath = rest;
                    case "WORLD_FRESH" -> worldFresh = "1".equals(parts[0]);
                    case "WORLD_NOTE" -> worldNote.append(rest);
                    case "TARGET_UNTOUCHED" -> untouched = "1".equals(parts[0]);
                    case "REGION_EXISTED_BEFORE" -> regionExisted = "1".equals(parts[0]);
                    case "MC_ID" -> mcId = rest;
                    case "MC_NAME" -> mcName = rest;
                    case "FABRIC_LOADER" -> fabricLoader = rest;
                    case "FABRIC_API" -> fabricApi = rest;
                    case "JAVA" -> javaVersion = rest;
                    case "MODS_HASH" -> modsHash = rest;
                    case "C2ME" -> c2me = "1".equals(parts[0]);
                    case "MODERNFIX" -> modernfix = "1".equals(parts[0]);
                    case "MOD" -> mods.add(rest);
                    case "ORDER" -> order.add(new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                    case "DECOR" -> {
                        if (target != null) {
                            batches.put(new ChunkPos(target.x() + Integer.parseInt(parts[0]),
                                    target.z() + Integer.parseInt(parts[1])), Integer.parseInt(parts[2]));
                        }
                    }
                    case "STEP" -> steps.add(new Step(Integer.parseInt(parts[0]),
                            new ChunkPos(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
                            Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Long.parseLong(parts[5]),
                            parts.length > 6 && !parts[6].isEmpty() && !"-".equals(parts[6])
                                    ? List.of(parts[6].split(";")) : List.of()));
                    case "D" -> {
                        BlockPos pos = new BlockPos(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]));
                        if ("diamond_ore".equals(parts[0])) {
                            diamondOre.add(pos);
                        } else if ("deepslate_diamond_ore".equals(parts[0])) {
                            deepslate.add(pos);
                        }
                    }
                    case "P" -> provenance.put(new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2])),
                            new Write(new ChunkPos(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])),
                                    parts[6], Integer.parseInt(parts[5]), parts[7], "1".equals(parts[8])));
                    default -> {
                        // TOTALS / END 等字段在解析时按集合重算，不依赖文件里的汇总值
                    }
                }
            }
        } catch (RuntimeException error) {
            return null;
        }
        if (format != FORMAT || scenario == null || target == null || order.isEmpty()) {
            return null;
        }
        return new ChunkOrderTruth(scenario, seed, target,
                new Environment(mcId, mcName, fabricLoader, fabricApi, javaVersion, modsHash, List.copyOf(mods),
                        c2me, modernfix),
                worldName, worldPath, worldFresh, worldNote.toString(), untouched, regionExisted,
                List.copyOf(order), batches, List.copyOf(steps), diamondOre, deepslate, provenance);
    }

    /** 报告用：有记录的区块 → 批号（按相对偏移排序，便于 3x3 排版）。 */
    public List<Map.Entry<ChunkPos, Integer>> sortedBatches() {
        Map<Integer, Map.Entry<ChunkPos, Integer>> ordered = new TreeMap<>();
        for (Map.Entry<ChunkPos, Integer> entry : batches.entrySet()) {
            int dx = entry.getKey().x() - target.x();
            int dz = entry.getKey().z() - target.z();
            ordered.put((dz + 1) * 3 + (dx + 1), entry);
        }
        return new ArrayList<>(ordered.values());
    }
}
