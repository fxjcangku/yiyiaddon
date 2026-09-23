package com.yiyiaddon.dev.seedpoc;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.client.SeedWorldgenWorkerLauncher;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerArguments;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import com.yiyiaddon.seed.worker.protocol.WorkerLineReader;
import com.yiyiaddon.seed.worker.protocol.WorkerRequest;
import com.yiyiaddon.seed.worker.protocol.WorkerResponse;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerOreDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 种子挖矿 · 开发期 <b>Worker 探针（legacy · 非正式 Release Gate）</b>（不进游戏，直接验
 * 「本地隔离世界生成计算器」能不能跑）。
 *
 * <p><b>【非正式 Release Gate】</b>（234 口径第三十四、六十九节）：本探针的读数<b>不是</b>
 * 正式发布门禁。原因是它的口径与正式链路不同 —— 它在一个<b>全新、空会话</b>的离线世界里
 * 只做<b>一次</b>预测；而正式链路（覆盖调度 / parity 装置）在同一个会话里会<b>按顺序</b>预测多个
 * 目标区块，先跑过的区块会改变后续区块的邻域装饰状态（228 已实测：合法 FEATURES 调度顺序
 * 会改变同一位置最终是否为钻石）。因此同一颗种子同一个区块，两边数字<b>允许不同</b>：
 * 例如 Seed 12345 (-1,-1) 在探针里是「候选 25 / 敏感 9」，而在正式链路上是「29 / 7」。</p>
 *
 * <p><b>正式发布门禁是哪一个</b>：{@code WorkerParityRegression}（Gradle：
 * {@code runClientSeedWorkerParityTest}）与生产产物冒烟
 * （{@code gradle/production-smoke.ps1}）。冻结数字一律以它们为准；本探针只用于
 * 「宿主能否启动 / 协议是否连通 / 负路径是否 fail-closed」这类<b>连通性</b>验收。</p>
 *
 * <p><b>为什么需要它</b>：第四阶段的关键风险是「隔离进程里到底能不能构造出可用的 Vanilla
 * {@code ServerLevel}」。在游戏里调这个问题一次要几十秒，而这里可以直接起一个 Worker、
 * 走一遍 HELLO / OPEN_SESSION / PREDICT，把宿主启动、版本握手、会话与正式预测器的连通性一次验完。</p>
 *
 * <p><b>它不是正式功能</b>：只是开发期装置，参数与日志都刻意粗糙；正式路径是
 * {@code SeedWorldgenWorkerLauncher}（从 FabricLoader 取 gameDir 与模组路径）。</p>
 *
 * <p>用法（Gradle 任务 {@code seedWorkerLegacyProbe}，或直接 java 调用）：</p>
 * <pre>{@code
 * java -cp <开发 classpath> com.yiyiaddon.dev.seedpoc.LegacyWorkerProbe <工作目录> <目标区块X> <目标区块Z> <种子> [负路径用例] [预热区块表] [观察坐标]
 * }</pre>
 *
 * <p><b>观察坐标</b>（第七个参数，形如 {@code x,y,z}；可选）：每次预测都打印「该坐标是否在候选集里、
 * 确定性是什么」。234 用它做 233 遗留缺失格的最小复现 —— 冷会话与预热会话各打印一行，
 * 两行一比即可看出该坐标是否受会话内装饰历史影响。</p>
 *
 * <p><b>预热区块表</b>（第六个参数，形如 {@code 0,0;1,0}；可选）：在测量目标之前先把这些区块
 * 依次预测一遍（结果只打印不判定）。它就是 234 用来复现「探针 25/9 vs 正式 29/7」的对照手段：
 * 用同一颗种子、同一个目标，分别跑「冷会话」与「先算邻居再算目标」两种合法情形，
 * 从而证明差异来自<b>会话内的装饰历史</b>，而不是预测器有回归。</p>
 *
 * <p><b>负路径模式</b>（第五个参数 = 用例名，口径第六十六节的 fail-closed 取证）：
 * {@code unknownOp} / {@code minecraftMismatch} / {@code protocolMismatch} / {@code badToken}。
 * 每个用例都要单独起一个 Worker —— 因为协议层对「令牌不符」与「协议版本不符」的处理是
 * <b>回完错误码就关闭连接</b>，而 Worker 只接受一条客户端连接，一个进程里只能验一项。</p>
 */
public final class LegacyWorkerProbe {

    /** 启动超时（毫秒）：宿主首次要建世界，给足。 */
    private static final long STARTUP_TIMEOUT_MILLIS = 300_000L;

    /** 预测超时（毫秒）。 */
    private static final long PREDICT_TIMEOUT_MILLIS = 300_000L;

    /** 负路径用例名 → 期望错误码（口径第六十六节：协议错误必须 fail-closed）。 */
    private static final java.util.Map<String, String> NEGATIVE_CASES = java.util.Map.of(
            "unknownOp", SeedWorkerProtocol.ERROR_UNKNOWN_OP,
            "minecraftMismatch", SeedWorkerProtocol.ERROR_MINECRAFT_MISMATCH,
            "protocolMismatch", SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH,
            "badToken", SeedWorkerProtocol.ERROR_BAD_TOKEN);

    private LegacyWorkerProbe() {
    }

    /** 探针入口。 */
    public static void main(String[] args) throws Exception {
        Path gameDir = Path.of(args.length > 0 ? args[0] : "build/seed-worker-probe").toAbsolutePath();
        int chunkX = args.length > 1 ? Integer.parseInt(args[1]) : 0;
        int chunkZ = args.length > 2 ? Integer.parseInt(args[2]) : 0;
        long seed = args.length > 3 ? Long.parseLong(args[3]) : 20260922L;
        // 第五个参数是负路径用例名；用 `-` / `none` / 空串占位表示「不跑负路径」（需要给第六个参数时用得上）
        String rawNegativeCase = args.length > 4 ? args[4].trim() : "";
        String negativeCase = rawNegativeCase.isEmpty() || "-".equals(rawNegativeCase)
                || "none".equalsIgnoreCase(rawNegativeCase) ? null : rawNegativeCase;
        List<int[]> warmChunks = args.length > 5 ? parseChunks(args[5]) : List.of();
        // 第七个参数：观察坐标 x,y,z（可选）—— 每次预测都打印「该坐标是否在候选集里、确定性是什么」。
        // 234 用它做 233 遗留缺失格的最小复现：同一个坐标在「冷会话」与「先算邻居」两种合法情形下
        // 是否都被预测到；若不同，就是 228 已定案的合法调度差异，而不是预测器回归。
        String watch = args.length > 6 ? args[6].trim() : "";

        Path runtimeRoot = gameDir.resolve("seed-worker-probe");
        Path hostDir = runtimeRoot.resolve("host");
        Path handshakeFile = runtimeRoot.resolve("handshake").resolve("worker-ready.json");
        Path logFile = runtimeRoot.resolve("probe-worker.log");
        Files.createDirectories(hostDir);
        Files.createDirectories(handshakeFile.getParent());
        Files.deleteIfExists(handshakeFile);
        installTeeOutput(runtimeRoot);

        System.out.println("================================================================================");
        System.out.println("[探针] 【非正式 Release Gate】本探针只用于「宿主能否启动 / 协议是否连通 / 负路径是否 fail-closed」"
                + "这类连通性验收。");
        System.out.println("[探针] 冻结数字的正式门禁是 WorkerParityRegression（runClientSeedWorkerParityTest）"
                + "与生产冒烟（production-smoke.ps1）。");
        System.out.println("[探针] 本探针在全新空会话里只做一次预测，与正式链路（同一会话内按序预测多个区块）"
                + "口径不同，数字允许不同；");
        System.out.println("[探针] 需要对照时用第六个参数指定预热区块表（例如 0,0），复现「先算邻居再算目标」的合法情形。");
        System.out.println("================================================================================");

        if (negativeCase != null && !NEGATIVE_CASES.containsKey(negativeCase)) {
            System.out.println("[探针] 未知负路径用例：" + negativeCase + "（可选：" + NEGATIVE_CASES.keySet() + "）");
            System.exit(6);
        }
        SeedWorldgenWorkerLauncher.writeLog4jConfig(runtimeRoot.resolve("log4j2.xml"));

        String token = randomToken();
        int hostPort = freePort();
        String javaExecutable = Path.of(System.getProperty("java.home"), "bin",
                System.getProperty("os.name", "").toLowerCase().contains("win") ? "java.exe" : "java").toString();
        String classpath = System.getProperty("java.class.path");

        List<String> command = new ArrayList<>(List.of(javaExecutable,
                "-Xms256m", "-Xmx2048m", "-XX:+ExitOnOutOfMemoryError",
                "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
                "-Dlog4j2.configurationFile=" + runtimeRoot.resolve("log4j2.xml").toAbsolutePath(),
                "-cp", classpath, "com.yiyiaddon.seedworker.SeedWorkerMain"));
        command.addAll(SeedWorkerArguments.encode(runtimeRoot.toString(), handshakeFile.toString(), token,
                SeedWorkerProtocol.VERSION, "26.1.2", "probe", hostPort, ProcessHandle.current().pid()));

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(hostDir.toFile());
        builder.redirectErrorStream(true);
        builder.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));
        long launchStart = System.currentTimeMillis();
        Process process = builder.start();
        System.out.println("[探针] Worker 已拉起 PID=" + process.pid() + "，等待宿主就绪（日志 "
                + logFile.toAbsolutePath() + "）...");

        try {
            awaitHandshake(process, handshakeFile, logFile);
            System.out.println("[探针] 握手文件出现，耗时 " + (System.currentTimeMillis() - launchStart) + " ms");

            try (Socket socket = connect(handshakeFile)) {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(4096);
                if (negativeCase == null) {
                    runPositive(in, out, buffer, token, seed, chunkX, chunkZ, warmChunks, watch);
                } else {
                    runNegative(in, out, buffer, token, negativeCase);
                }
            }
        } finally {
            if (process.isAlive()) {
                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroy();
                    if (!process.waitFor(3, TimeUnit.SECONDS)) {
                        process.destroyForcibly();
                    }
                }
            }
            System.out.println("[探针] Worker 已退出，退出码 " + process.exitValue());
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 正路径：HELLO → 会话 → 预测 → 关闭会话 → 停机
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 把探针自己的输出同时写一份到 {@code probe-report.txt}（UTF-8）。
     *
     * <p>为什么需要它：中文 Windows 上探针是被 Gradle 通过管道拉起来的，控制台两侧的编码不一致时
     * 中文会变成乱码，逐行过滤也可能失效 —— 而探针的结论是要进报告的取证材料。
     * 报告文件固定 UTF-8，不受控制台编码影响。</p>
     */
    private static void installTeeOutput(Path runtimeRoot) throws IOException {
        Path reportFile = runtimeRoot.resolve("probe-report.txt");
        Files.deleteIfExists(reportFile);
        OutputStream fileOut = Files.newOutputStream(reportFile,
                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        PrintStream original = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int value) throws IOException {
                original.write(value);
                fileOut.write(value);
            }

            @Override
            public void write(byte[] bytes, int offset, int length) throws IOException {
                original.write(bytes, offset, length);
                fileOut.write(bytes, offset, length);
            }

            @Override
            public void flush() throws IOException {
                original.flush();
                fileOut.flush();
            }
        }, true, StandardCharsets.UTF_8));
    }

    private static void runPositive(InputStream in, OutputStream out, ByteArrayOutputStream buffer,
                                    String token, long seed, int chunkX, int chunkZ, List<int[]> warmChunks,
                                    String watch) throws IOException {
        WorkerResponse hello = exchange(in, out, buffer, WorkerRequest.hello(1L, token,
                SeedWorkerProtocol.VERSION, "26.1.2"), 30_000L);
        System.out.println("[探针] HELLO：" + hello.hello());

        WorkerResponse session = exchange(in, out, buffer, WorkerRequest.openSession(2L, token, seed,
                "minecraft:overworld"), 60_000L);
        System.out.println("[探针] OPEN_SESSION：" + session.session());

        // 预热（可选）：先把指定区块按顺序预测一遍，复现「会话内先算过邻居」这一合法情形。
        // 结果只打印不判定 —— 它证明的是「同一颗种子、同一个目标，会因为会话内的装饰历史不同而给出不同数字」。
        int warmId = 100;
        for (int[] warm : warmChunks) {
            WorkerResponse warmed = exchange(in, out, buffer,
                    WorkerRequest.predict(warmId++, token, seed, "minecraft:overworld", warm[0], warm[1],
                            "DIAMOND"), PREDICT_TIMEOUT_MILLIS);
            WorkerPredictionDto warmPrediction = warmed.prediction();
            String summary = warmPrediction == null
                    ? "（应答缺少预测结果）"
                    : warmPrediction.ores().size() + " 个，调度敏感 " + warmPrediction.stats().scheduleSensitiveCount()
                      + "，缓存区块 " + warmPrediction.heldChunks();
            System.out.println("[探针] 预热 PREDICT 区块 (" + warm[0] + "," + warm[1] + ") → " + summary);
            reportWatched(watch, warmPrediction);
        }

        long predictStart = System.currentTimeMillis();
        WorkerResponse predicted = exchange(in, out, buffer, WorkerRequest.predict(3L, token, seed,
                "minecraft:overworld", chunkX, chunkZ, "DIAMOND"), PREDICT_TIMEOUT_MILLIS);
        long predictMillis = System.currentTimeMillis() - predictStart;
        WorkerPredictionDto prediction = predicted.prediction();
        if (prediction == null) {
            System.out.println("[探针] 预测失败：应答缺少预测结果");
            System.exit(4);
        }
        System.out.println("[探针] PREDICT 种子 " + seed + " 区块 (" + chunkX + "," + chunkZ + ") → 成功="
                + prediction.success() + " 候选=" + prediction.ores().size()
                + " 调度敏感=" + prediction.stats().scheduleSensitiveCount()
                + " 未解析=" + prediction.stats().unresolvedCount()
                + " 确定性=" + prediction.stats().deterministicCount()
                + " 预测器耗时=" + prediction.elapsedMillis() + " ms 端到端=" + predictMillis + " ms"
                + " 缓存区块=" + prediction.heldChunks()
                + " 宿主 ChunkMap 查询=" + prediction.stats().hostChunkSourceQueries());
        int shown = 0;
        for (WorkerOreDto ore : prediction.ores()) {
            if (shown++ >= 3) {
                break;
            }
            System.out.println("    " + ore.x() + "," + ore.y() + "," + ore.z() + " "
                    + ore.oreType() + " / " + ore.certainty() + " / " + ore.source());
        }
        exchange(in, out, buffer, WorkerRequest.closeSession(4L, token), 60_000L);
        exchange(in, out, buffer, WorkerRequest.shutdown(5L, token), 10_000L);
        reportWatched(watch, prediction);
        System.out.println("[探针] 判定：宿主可用 / 会话可用 / 正式预测器可用 → "
                + (prediction.success() ? "通过" : "**预测不成立**"));
    }

    /**
     * 打印「观察坐标」在这次预测里的存在性与确定性（第七个参数给定时才有输出）。
     *
     * <p>冷会话与预热会话各打印一次，两行一比就能看出「该坐标是否受会话内装饰历史影响」。</p>
     */
    private static void reportWatched(String watch, WorkerPredictionDto prediction) {
        if (watch == null || watch.isBlank() || prediction == null) {
            return;
        }
        String[] parts = watch.split(",");
        if (parts.length != 3) {
            System.out.println("[探针] 观察坐标格式非法：" + watch + "（应为 x,y,z）");
            return;
        }
        int x;
        int y;
        int z;
        try {
            x = Integer.parseInt(parts[0].trim());
            y = Integer.parseInt(parts[1].trim());
            z = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException bad) {
            System.out.println("[探针] 观察坐标格式非法：" + watch + "（应为 x,y,z）");
            return;
        }
        for (WorkerOreDto ore : prediction.ores()) {
            if (ore.x() == x && ore.y() == y && ore.z() == z) {
                System.out.println("[探针] 观察坐标 (" + x + "," + y + "," + z + ") → **在候选集里**，确定性 "
                        + ore.certainty() + "（本次预测候选 " + prediction.ores().size() + " 个）");
                return;
            }
        }
        System.out.println("[探针] 观察坐标 (" + x + "," + y + "," + z + ") → 不在候选集里（本次预测候选 "
                + prediction.ores().size() + " 个）");
    }

    // ────────────────────────────────────────────────────────────────────────
    // 负路径：协议层必须 fail-closed（口径第六十六节）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 单独验一项协议错误：请求必须被<b>明确拒绝</b>并带回指定错误码，
     * 绝不允许「静默按成功返回空结果」。
     */
    private static void runNegative(InputStream in, OutputStream out, ByteArrayOutputStream buffer,
                                    String token, String negativeCase) throws IOException {
        WorkerResponse hello = exchange(in, out, buffer, WorkerRequest.hello(1L, token,
                SeedWorkerProtocol.VERSION, "26.1.2"), 30_000L);
        WorkerRequest request = switch (negativeCase) {
            case "unknownOp" -> new WorkerRequest(2L, "NO_SUCH_OP", token, SeedWorkerProtocol.VERSION,
                    "26.1.2", 0L, "minecraft:overworld", 0, 0, "DIAMOND");
            case "minecraftMismatch" -> WorkerRequest.hello(2L, token, SeedWorkerProtocol.VERSION, "26.9.9");
            case "protocolMismatch" -> WorkerRequest.hello(2L, token, SeedWorkerProtocol.VERSION + 1, "26.1.2");
            case "badToken" -> WorkerRequest.hello(2L, "000000000000000000000000000000000000000000000000",
                    SeedWorkerProtocol.VERSION, "26.1.2");
            default -> throw new IllegalStateException("未处理的用例：" + negativeCase);
        };
        String expected = NEGATIVE_CASES.get(negativeCase);
        WorkerResponse response = exchange(in, out, buffer, request, 30_000L);
        boolean ok = !response.ok() && expected.equals(response.errorCode());
        System.out.println("[探针] 负路径 " + negativeCase + "：HELLO=" + (hello.ok() ? "通过" : "失败")
                + "，请求 " + request.op() + " 期望错误码 " + expected + " → 实际 "
                + (response.ok() ? "**成功（不应发生）**" : response.errorCode())
                + " / " + response.errorMessage() + " → " + (ok ? "通过" : "**不通过**"));
        if (!ok) {
            System.exit(5);
        }
    }

    private static void awaitHandshake(Process process, Path handshakeFile, Path logFile)
            throws IOException, InterruptedException {
        long deadline = System.currentTimeMillis() + STARTUP_TIMEOUT_MILLIS;
        long lastReport = 0L;
        while (System.currentTimeMillis() < deadline) {
            if (Files.isRegularFile(handshakeFile)) {
                return;
            }
            if (!process.isAlive()) {
                System.out.println("[探针] Worker 提前退出，退出码 " + process.exitValue());
                printLogTail(logFile, 30);
                System.exit(3);
            }
            if (System.currentTimeMillis() - lastReport > 5_000L) {
                lastReport = System.currentTimeMillis();
                System.out.println("[探针] 仍在等待宿主就绪...");
            }
            Thread.sleep(200L);
        }
        System.out.println("[探针] 等待宿主就绪超时");
        printLogTail(logFile, 30);
        System.exit(3);
    }

    private static Socket connect(Path handshakeFile) throws IOException {
        JsonObject handshake = WorkerJson.parseObject(Files.readString(handshakeFile, StandardCharsets.UTF_8),
                SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE);
        int port = WorkerJson.reqInt(handshake, "port");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), 10_000);
        socket.setSoTimeout((int) PREDICT_TIMEOUT_MILLIS);
        System.out.println("[探针] 已连接 127.0.0.1:" + port);
        return socket;
    }

    private static WorkerResponse exchange(InputStream in, OutputStream out, ByteArrayOutputStream buffer,
                                           WorkerRequest request, long timeoutMillis) throws IOException {
        out.write((WorkerJson.encode(request.toJson()) + "\n").getBytes(StandardCharsets.UTF_8));
        out.flush();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        while (true) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                throw new IOException("请求 " + request.op() + " 超时");
            }
            String line = WorkerLineReader.readLine(in, buffer, SeedWorkerProtocol.MAX_LINE_BYTES);
            if (line == null) {
                throw new IOException("连接被关闭");
            }
            if (line.isBlank()) {
                continue;
            }
            WorkerResponse response = WorkerResponse.fromJson(
                    WorkerJson.parseObject(line, SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE));
            if (response.id() != request.id()) {
                System.out.println("[探针] 丢弃过期应答 id=" + response.id());
                continue;
            }
            if (!response.ok()) {
                System.out.println("[探针] 请求 " + request.op() + " 失败：" + response.errorCode() + " / "
                        + response.errorMessage());
            }
            return response;
        }
    }

    private static void printLogTail(Path logFile, int lines) {
        try {
            String text = new String(Files.readAllBytes(logFile), StandardCharsets.UTF_8);
            String[] all = text.split("\\R");
            for (int index = Math.max(0, all.length - lines); index < all.length; index++) {
                System.out.println("    | " + all[index]);
            }
        } catch (IOException error) {
            System.out.println("    | （读日志失败：" + error.getMessage() + "）");
        }
    }

    /**
     * 解析预热区块表（{@code cx,cz;cx,cz;…}；非法项直接跳过）。
     *
     * <p>与 {@code SeedPocFlags#parseChunks} 同一形态，但本探针是独立 main 入口，不依赖 flags 类。</p>
     */
    private static List<int[]> parseChunks(String raw) {
        List<int[]> parsed = new ArrayList<>();
        for (String piece : raw.split(";")) {
            String[] pair = piece.trim().split(",");
            if (pair.length != 2) {
                continue;
            }
            try {
                parsed.add(new int[]{Integer.parseInt(pair[0].trim()), Integer.parseInt(pair[1].trim())});
            } catch (NumberFormatException ignored) {
                // 非法项直接跳过，不让一次手误毁掉整轮对照
            }
        }
        return parsed;
    }

    private static String randomToken() {
        byte[] bytes = new byte[24];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    private static int freePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            return socket.getLocalPort();
        }
    }
}
