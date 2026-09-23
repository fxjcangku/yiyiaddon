package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.SharedConstants;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC 第五轮 · Chunk 生成顺序决定性实验驱动器。
 *
 * <p><b>为什么不使用已有装置</b>：前三、四轮的装置都在「同一个已生成好的世界」里做重放，历史已经固定。
 * 本轮要制造的变量就是<b>历史本身</b>——所以驱动器只做四件事：</p>
 * <ol>
 *     <li>确认这个世界是全新的、目标区域从未生成（用户口径第二十二节）；</li>
 *     <li>按场景给定的顺序，对每个区块调用原版 {@code ServerLevel#getChunk(cx, cz)}
 *         （阻塞到 {@code ChunkStatus.FULL}）——<b>不伪造装饰顺序</b>，全部走
 *         {@code ChunkMap#applyStep → ChunkGenerationTask → ChunkStep → ChunkStatusTasks}
 *         这条原版流水线（用户口径第九节）；</li>
 *     <li>每步之后读一次「目标区块内的钻石」，与上一步做差——因为
 *         {@code getAccumulatedRadiusOf(FEATURES) = 0}，一步只会装饰一个区块，所以这一步的新增
 *         天然就是「那个区块的这一次装饰写进目标区块的东西」，归因不需要额外探针；</li>
 *     <li>把最终集合 + 逐步骤记录 + 写入溯源 + 环境证据写成一份真值文件，交给跨世界比较器。</li>
 * </ol>
 *
 * <p><b>为什么每个目标区块要单独读「差集」</b>：目标区块自己的装饰也会往外写（写半径 1），
 * 邻域先装饰时又会写进目标区块。逐步骤差集能把「谁在什么时候写进来的」直接读出来，
 * 这是本轮结论的证据链主体。</p>
 *
 * <p>刻意保持无副作用：不写世界（只读方块）、不改正式业务、不注册指令与界面。</p>
 */
public final class ChunkOrderTestRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    private ChunkOrderTestRunner() {
    }

    /** 跑一轮顺序实验（必须在服务端线程上调用：要阻塞式驱动原版区块生成）。 */
    public static void run(IntegratedServer server) {
        long startedAt = System.currentTimeMillis();
        String scenarioId = SeedPocFlags.orderScenario();
        ServerLevel level = server.overworld();
        Long override = SeedPocFlags.seedOverride();
        long seed = override != null ? override : level.getSeed();

        List<String> notes = new ArrayList<>();
        notes.add("驱动器：按场景顺序逐个调用 ServerLevel#getChunk（阻塞到 FULL），全部走原版 ChunkGenerationTask 流水线");
        notes.add("种子来源：" + (override != null ? "系统属性 yiyiaddon.seedpoc.seed" : "当前单人世界真实种子"));
        notes.add("探针口径：本轮把第三/四轮的快照与逐事件台账关掉（GenStageCapture 顺序实验模式），"
                + "只保留「装饰批号」与「写入溯源」两类轻量记账，避免探针改变生成期时序");

        // 顺序实验模式必须在任何区块生成之前打开
        GenStageCapture.setOrderMode(true);
        List<ChunkOrderTruth> thisRun = new ArrayList<>();
        try {
            for (long[] coordinates : SeedPocFlags.orderTargets()) {
                ChunkPos target = new ChunkPos((int) coordinates[0], (int) coordinates[1]);
                try {
                    thisRun.add(executeOne(level, target, scenarioId, seed, notes));
                } catch (Throwable error) {
                    // 单个目标失败必须如实进报告，绝不静默跳过
                    notes.add("目标 (" + target.x() + "," + target.z() + ") 实验失败："
                            + error.getClass().getName() + ": " + error.getMessage());
                    LOGGER.error("{}：顺序实验目标 ({},{}) 失败", SeedPocConstants.LOG_KEY,
                            target.x(), target.z(), error);
                }
            }
        } finally {
            GenStageCapture.setOrderMode(false);
        }

        // 落盘本轮真值 → 与运行目录里已有的全部真值合并 → 比较
        List<ChunkOrderTruth> allTruths = new ArrayList<>(ChunkOrderTruth.loadAll());
        for (ChunkOrderTruth truth : thisRun) {
            truth.write();
            allTruths.removeIf(existing -> existing.key().equals(truth.key()));
            allTruths.add(truth);
        }
        allTruths.sort(java.util.Comparator.comparing(ChunkOrderTruth::key));

        ChunkOrderScenario scenario = null;
        if (!thisRun.isEmpty()) {
            scenario = ChunkOrderScenario.of(scenarioId, thisRun.get(0).target());
        } else {
            scenario = ChunkOrderScenario.of(scenarioId,
                    new ChunkPos((int) SeedPocFlags.orderTargets().get(0)[0], (int) SeedPocFlags.orderTargets().get(0)[1]));
        }
        ChunkOrderReport.output(scenarioId, scenario, thisRun, allTruths, notes,
                System.currentTimeMillis() - startedAt);
        LOGGER.info("{}：第五轮顺序实验结束（场景 {}），真值 {} 份已在运行目录",
                SeedPocConstants.LOG_KEY, scenarioId, allTruths.size());
    }

    /**
     * 对一个目标区块跑完整轮：新鲜度自证 → 按顺序请求 → 逐步差集 → 落真值。
     */
    private static ChunkOrderTruth executeOne(ServerLevel level, ChunkPos target, String scenarioId, long seed,
                                              List<String> notes) {
        ChunkOrderScenario scenario = ChunkOrderScenario.of(scenarioId, target);
        if (scenario.targetStep() <= 0) {
            throw new IllegalStateException("场景顺序表里没有目标区块：" + scenarioId);
        }

        // ── 1) 新鲜度自证：目标 3x3 必须全部没有生成到 FULL ────────────────
        List<String> generated = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos probe = new ChunkPos(target.x() + dx, target.z() + dz);
                LevelChunk chunk = level.getChunkSource().getChunkNow(probe.x(), probe.z());
                if (chunk != null) {
                    generated.add("(" + probe.x() + "," + probe.z() + ")");
                }
            }
        }
        boolean untouched = generated.isEmpty();
        Path regionFile = regionFile(target);
        boolean regionExisted = Files.exists(regionFile);
        String freshnessNote = "#" + (SeedPocWorldFactory.lastFreshNote()) + "｜驱动开始时 saves 目录 region 文件数 "
                + countRegionFiles() + "｜目标区 region 文件 " + regionFile.getFileName() + " 存在=" + regionExisted
                + "｜level.dat 修改时间 " + levelDatTime();
        if (!untouched) {
            freshnessNote = freshnessNote + "｜警告：以下区块在请求前已生成到 FULL " + String.join(" ", generated);
        }
        notes.add("目标 (" + target.x() + "," + target.z() + ")：" + freshnessNote);

        // ── 2) 按顺序请求，逐步读差集 ───────────────────────────────────────
        ChunkOrderJournal.begin();
        List<ChunkOrderTruth.Step> steps = new ArrayList<>();
        Set<BlockPos> previous = Set.of();
        Map<Long, Integer> passBefore = targetPasses(target);
        try {
            for (int index = 0; index < scenario.order().size(); index++) {
                ChunkPos chunk = scenario.order().get(index);
                long begin = System.nanoTime();
                // 原版流水线：加 ticket → ChunkMap 调度 → ChunkGenerationTask → 装饰 → FULL
                level.getChunk(chunk.x(), chunk.z());
                long millis = (System.nanoTime() - begin) / 1_000_000L;
                int batch = GenStageCapture.currentPass(chunk.pack());
                Set<BlockPos> now = scanTargetDiamonds(level, target);
                Set<BlockPos> added = new LinkedHashSet<>(now);
                added.removeAll(previous);
                previous = now;
                // 本步内「新拿到装饰批号」的目标 3x3 区块（= 本步真的装饰了它们），按批号升序
                Map<Integer, String> passAfter = new java.util.TreeMap<>();
                Map<Long, Integer> passes = targetPasses(target);
                for (Map.Entry<Long, Integer> entry : passes.entrySet()) {
                    if (!passBefore.containsKey(entry.getKey())) {
                        passAfter.put(entry.getValue(), ChunkPos.getX(entry.getKey()) + ","
                                + ChunkPos.getZ(entry.getKey()));
                    }
                }
                passBefore = passes;
                List<String> decorated = new ArrayList<>();
                for (Map.Entry<Integer, String> entry : passAfter.entrySet()) {
                    decorated.add(entry.getValue() + ":" + entry.getKey());
                }
                steps.add(new ChunkOrderTruth.Step(index + 1, chunk, batch, added.size(), millis, decorated));
                LOGGER.info("{}：顺序实验｜场景 {}｜目标 ({},{})｜第 {} 步请求 ({},{})｜批号 {}｜"
                                + "本步装饰目标 3x3 内 {} 个区块 {}｜目标区块钻石累计 {}（本步 +{}）｜耗时 {} ms",
                        SeedPocConstants.LOG_KEY, scenarioId, target.x(), target.z(), index + 1,
                        chunk.x(), chunk.z(), batch, decorated.size(), decorated, now.size(), added.size(), millis);
            }
        } finally {
            ChunkOrderJournal.end();
        }

        // ── 2.5) 目标 3x3 的真实装饰批号（口径：谁先装饰）──────────────────
        // 必须从探针按区块读，而不是只记「我们请求过谁」：一次请求会装饰以它为中心的 3x3，
        // 没被我们直接请求的邻区块同样是真实装饰过的（它们的批号才是自变量的证据）。
        Map<ChunkPos, Integer> batches = new java.util.LinkedHashMap<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos neighbor = new ChunkPos(target.x() + dx, target.z() + dz);
                int batch = GenStageCapture.currentPass(neighbor.pack());
                if (batch > 0) {
                    batches.put(neighbor, batch);
                }
            }
        }
        if (batches.size() != 9) {
            notes.add("警告：目标 (" + target.x() + "," + target.z() + ") 的 3x3 只登记到 " + batches.size()
                    + "/9 个装饰批号（世界状态与预期不符，该目标的自变量读数不完整）");
        }

        // ── 3) 最终真值 + 溯源（只保留落在目标区块内的写入）─────────────────
        Map<BlockPos, ChunkOrderJournal.Write> journal = ChunkOrderJournal.snapshot();
        Map<BlockPos, ChunkOrderTruth.Write> provenance = new java.util.LinkedHashMap<>();
        for (Map.Entry<BlockPos, ChunkOrderJournal.Write> entry : journal.entrySet()) {
            BlockPos pos = entry.getKey();
            if ((pos.getX() >> 4) != target.x() || (pos.getZ() >> 4) != target.z()) {
                continue;
            }
            ChunkOrderJournal.Write write = entry.getValue();
            provenance.put(pos, new ChunkOrderTruth.Write(write.viewer(), write.featurePath(), write.batch(),
                    write.preStateId(), write.accepted()));
        }
        Set<BlockPos> all = previous;
        Set<BlockPos> diamondOre = new LinkedHashSet<>();
        Set<BlockPos> deepslate = new LinkedHashSet<>();
        for (BlockPos pos : all) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.DIAMOND_ORE)) {
                diamondOre.add(pos);
            } else if (state.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                deepslate.add(pos);
            }
        }
        // 自变量统计：目标 3x3 里有几个邻区块在目标之前 / 之后被装饰
        int targetBatch = batches.getOrDefault(target, -1);
        int beforeCount = 0;
        int afterCount = 0;
        for (Map.Entry<ChunkPos, Integer> entry : batches.entrySet()) {
            if (entry.getKey().equals(target)) {
                continue;
            }
            if (entry.getValue() < targetBatch) {
                beforeCount++;
            } else {
                afterCount++;
            }
        }
        notes.add("目标 (" + target.x() + "," + target.z() + ") 完成：钻石 " + all.size() + " 格（diamond_ore "
                + diamondOre.size() + " + deepslate_diamond_ore " + deepslate.size() + "）｜"
                + "写入溯源 " + provenance.size() + " 条｜3x3 装饰批号 " + batches);
        notes.add("目标 (" + target.x() + "," + target.z() + ") 自变量：在目标之前被装饰的邻区块 "
                + beforeCount + " 个 / 之后 " + afterCount + " 个（3x3 内共登记批号 " + batches.size() + " 个）");

        // ── 3.5) 指定探针坐标的最终方块状态（234.1 MISSING 补证用；只读，不改世界）──
        // 为什么需要它：三个全新世界跑不同合法请求顺序之后，必须能逐个世界读出「同一格最终是什么」，
        // 才能判定该格是「随合法请求顺序变化」还是「三个世界都相同」。
        int[] probe = SeedPocFlags.orderProbePos();
        if (probe != null && (probe[0] >> 4) == target.x() && (probe[2] >> 4) == target.z()) {
            BlockPos probePos = new BlockPos(probe[0], probe[1], probe[2]);
            BlockState probeState = level.getBlockState(probePos);
            boolean isDiamond = OreBlockLedger.isDiamond(probeState);
            String probeLine = "探针 (" + probe[0] + "," + probe[1] + "," + probe[2] + ") 最终方块 = "
                    + probeState.getBlock().getName().getString() + "｜注册名 "
                    + net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(probeState.getBlock())
                    + "｜是否钻石 = " + (isDiamond ? "是" : "否")
                    + "｜该目标区块本次钻石总数 = " + all.size();
            notes.add("目标 (" + target.x() + "," + target.z() + ") " + probeLine);
            LOGGER.info("{}：顺序实验｜场景 {}｜目标 ({},{})｜{}",
                    SeedPocConstants.LOG_KEY, scenarioId, target.x(), target.z(), probeLine);
        }

        return new ChunkOrderTruth(scenarioId, seed, target, environment(),
                SeedPocWorldFactory.LEVEL_ID, worldDir().toString(), SeedPocWorldFactory.lastFreshOk(), freshnessNote,
                untouched, regionExisted, scenario.order(), batches, steps, diamondOre, deepslate, provenance);
    }

    // ── 目标 3x3 的装饰批号读数 ─────────────────────────────────────────────

    /**
     * 读目标 3x3 里「已经拿到装饰批号」的区块 → 批号。
     *
     * <p>批号由 {@code ChunkGenerator#applyBiomeDecoration} 的 HEAD 探针发放（每个区块每次真实装饰一个号），
     * 因此「本步请求前后两份读数的差」就是「本步真的装饰了哪几个目标 3x3 区块」——
     * 这是本轮「生成顺序确实被改变」的最直接证据，也是自变量（目标之前有几个邻区块装饰过）的来源。</p>
     */
    private static Map<Long, Integer> targetPasses(ChunkPos target) {
        Map<Long, Integer> passes = new java.util.LinkedHashMap<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                long packed = ChunkPos.pack(target.x() + dx, target.z() + dz);
                int batch = GenStageCapture.currentPass(packed);
                if (batch > 0) {
                    passes.put(packed, batch);
                }
            }
        }
        return passes;
    }

    // ── 目标区块扫描（绝不能触发区块生成）──────────────────────────────────

    /**
     * 读目标区块内当前的钻石坐标。
     *
     * <p><b>这里绝不能走 {@code Level#getChunk}：</b>它带 {@code loadOrGenerate = true}，
     * 一旦在目标区块自己那一步之前调用，就会替实验把目标区块生成掉——顺序实验立刻失效
     * （尤其是「邻域优先」场景，目标本该第 9 步才装饰）。所以只用
     * {@code ServerChunkCache#getChunkNow}（纯读，未生成返回 null）。</p>
     */
    private static Set<BlockPos> scanTargetDiamonds(ServerLevel level, ChunkPos target) {
        Set<BlockPos> found = new LinkedHashSet<>();
        LevelChunk chunk = level.getChunkSource().getChunkNow(target.x(), target.z());
        if (chunk == null) {
            return found;
        }
        int minSectionY = level.getMinSectionY();
        LevelChunkSection[] sections = chunk.getSections();
        for (int index = 0; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section == null || section.hasOnlyAir()) {
                continue;
            }
            int baseY = (minSectionY + index) * 16;
            for (int localX = 0; localX < 16; localX++) {
                for (int localY = 0; localY < 16; localY++) {
                    for (int localZ = 0; localZ < 16; localZ++) {
                        if (OreBlockLedger.isDiamond(section.getBlockState(localX, localY, localZ))) {
                            found.add(new BlockPos((target.x() << 4) + localX, baseY + localY,
                                    (target.z() << 4) + localZ));
                        }
                    }
                }
            }
        }
        return found;
    }

    // ── 环境证据（用户口径第十四节）────────────────────────────────────────
    /** 采集当前实验环境：版本 + 全部已加载 Mod + 列表 hash。 */
    public static ChunkOrderTruth.Environment environment() {
        FabricLoader loader = FabricLoader.getInstance();
        List<String> mods = new ArrayList<>();
        boolean c2me = false;
        boolean modernfix = false;
        List<ModContainer> containers = new ArrayList<>(loader.getAllMods());
        containers.sort(java.util.Comparator.comparing(container -> container.getMetadata().getId()));
        for (ModContainer container : containers) {
            String id = container.getMetadata().getId();
            String version = container.getMetadata().getVersion().getFriendlyString();
            mods.add(id + "@" + version);
            if (id.contains("c2me")) {
                c2me = true;
            }
            if (id.contains("modernfix")) {
                modernfix = true;
            }
        }
        String fabricApi = containers.stream()
                .filter(container -> "fabric-api".equals(container.getMetadata().getId()))
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .findFirst().orElse("未加载");
        String fabricLoader = containers.stream()
                .filter(container -> "fabricloader".equals(container.getMetadata().getId()))
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .findFirst().orElse("未知");
        return new ChunkOrderTruth.Environment(SharedConstants.getCurrentVersion().id(),
                SharedConstants.getCurrentVersion().name(), fabricLoader, fabricApi,
                System.getProperty("java.version", "未知"), sha256(String.join(";", mods)),
                List.copyOf(mods), c2me, modernfix);
    }

    /** 环境 hash：Mods 列表排序后拼接取 SHA-256，用于自证「三个世界环境相同」。 */
    private static String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte value : bytes) {
                hex.append(String.format(java.util.Locale.ROOT, "%02x", value));
            }
            return hex.toString();
        } catch (Exception error) {
            return "hash失败:" + error.getClass().getSimpleName();
        }
    }

    // ── 世界新鲜度证据 ─────────────────────────────────────────────────────

    /** 运行目录下的存档目录。 */
    public static Path worldDir() {
        return FabricLoader.getInstance().getGameDir().resolve("saves").resolve(SeedPocWorldFactory.LEVEL_ID);
    }

    /** 目标区块所属的 region 文件（原版命名：r.<cx>>5>.<cz>>5>.mca）。 */
    private static Path regionFile(ChunkPos target) {
        return worldDir().resolve("region").resolve("r." + (target.x() >> 5) + "." + (target.z() >> 5) + ".mca");
    }

    /** saves 目录下已有的 region 文件总数（驱动开始时应当极小；世界刚建，尚未自动保存）。 */
    private static int countRegionFiles() {
        try (Stream<Path> stream = Files.walk(worldDir())) {
            return (int) stream.filter(path -> path.getFileName().toString().endsWith(".mca")).count();
        } catch (IOException error) {
            return -1;
        }
    }

    /** level.dat 的修改时间（≈ 世界创建时间）。 */
    private static String levelDatTime() {
        Path levelDat = worldDir().resolve("level.dat");
        try {
            return Files.getLastModifiedTime(levelDat).toString();
        } catch (IOException error) {
            return "读取失败:" + error.getClass().getSimpleName();
        }
    }
}
