package com.yiyiaddon.seed.worker.client;

import com.yiyiaddon.seed.worker.protocol.SeedWorkerArguments;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · Worker <b>启动器</b>（客户端侧；只负责「怎么把一个真正的 Worker 进程拉起来」）。
 *
 * <p><b>它解决什么</b>（阶段 232 口径第七、八、九、八十八节）：正式运行时不允许依赖 Gradle / IDE /
 * 开发 sourceSet，所以启动方式必须是「用<b>启动游戏的那个 JVM 的 classpath</b>，再起一个同版本的 java」：</p>
 *
 * <ul>
 *   <li><b>java 可执行文件</b>：从当前 JVM 的 {@code java.home} 解析，绝不假设 PATH 里有 java；</li>
 *   <li><b>classpath</b>：当前进程的 {@code java.class.path}（含 Minecraft 客户端 jar 与全部库）
 *       + 本模组自己的路径（正式运行时模组 jar 由 Fabric Loader 动态挂载，不在
 *       {@code java.class.path} 里，因此要从 {@code FabricLoader} 的模组容器取它的落盘路径）；</li>
 *   <li><b>25.1.2 的 Minecraft 客户端 jar 本身就含有服务端类</b>（集成服务端要跑世界生成），
 *       所以 Worker 不需要任何额外的 server jar，也<b>不允许</b>把 Mojang 的类打进模组
 *       （口径第九节：不分发 Mojang 类）；</li>
 *   <li><b>参数一律用 {@code ProcessBuilder(List)}</b>，不做字符串拼接，因此路径含空格 / 中文都安全
 *       （口径第八十九节）。</li>
 * </ul>
 *
 * <p><b>隔离</b>：Worker 有自己独立的工作目录（{@code <gameDir>/yiyiaddon/seed-worker/<版本>/}），
 * 里面包含自己的 {@code server.properties}、自己的世界存储与自己的日志；
 * 它<b>不</b>读玩家存档（口径第十三节）、<b>不</b>继承玩家的 mods 目录（因此不会被 C2ME / ModernFix
 * 之类改 worldgen 的模组污染）。</p>
 */
public final class SeedWorldgenWorkerLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** Worker 主类（独立包，绝不与 UI / AutoMiner 混在一起，口径第二十二节）。 */
    private static final String WORKER_MAIN_CLASS = "com.yiyiaddon.seedworker.SeedWorkerMain";

    /** 本模组 id（取自己的落盘路径用）。 */
    private static final String MOD_ID = "yiyiaddon";

    /** Worker JVM 初始堆（口径第六十四节：显式设置，不继承客户端的 -Xmx）。 */
    private static final int WORKER_HEAP_INITIAL_MB = 256;

    /** Worker JVM 最大堆（实测后确定的保守值；OOM 时由 ExitOnOutOfMemoryError 明确退出而不是静默卡死）。 */
    private static final int WORKER_HEAP_MAX_MB = diagnosticsInt("yiyiaddon.seedworker.maxHeapMb", 1024);

    /** 随机令牌字节数（192 位，口径第十八节要求 128 位以上）。 */
    private static final int TOKEN_BYTES = 24;

    private SeedWorldgenWorkerLauncher() {
    }

    /**
     * 读取诊断/验收用的整型开关（默认关闭）。
     *
     * <p>只服务于验收：低内存堆矩阵要能改 {@code -Xmx}，超时负路径要能把超时压到不可能满足的值。
     * 非法值一律回落默认值 —— 用户随手设错一个属性不能让功能整体不可用。</p>
     */
    static int diagnosticsInt(String key, int fallback) {
        String raw = System.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            int value = Integer.parseInt(raw.trim());
            return value > 0 ? value : fallback;
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    /**
     * 拉起一个 Worker 进程（不等待握手）。
     *
     * @param minecraftVersion 当前 Minecraft 版本（同时作为运行目录的版本隔离键）
     * @param modVersion       当前模组版本
     */
    public static SeedWorkerProcess launch(String minecraftVersion, String modVersion) {
        Path gameDir = FabricLoader.getInstance().getGameDir();
        Path runtimeRoot = runtimeRoot(gameDir, minecraftVersion);
        Path hostDir = runtimeRoot.resolve("host");
        Path handshakeFile = runtimeRoot.resolve("handshake").resolve("worker-ready.json");
        Path logFile = runtimeRoot.resolve("seed-worker-" + minecraftVersion + ".log");
        try {
            Files.createDirectories(hostDir);
            Files.createDirectories(handshakeFile.getParent());
            Files.deleteIfExists(handshakeFile);
            writeLog4jConfig(runtimeRoot.resolve("log4j2.xml"));
        } catch (IOException error) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "无法建立本地世界生成计算器目录：" + runtimeRoot + "（" + error.getMessage() + "）", error);
        }

        Path javaExecutable = resolveJavaExecutable();
        String classpath = buildClasspath();
        String token = randomToken();
        int hostPort = pickFreeLoopbackPort();

        List<String> command = new ArrayList<>();
        command.add(javaExecutable.toAbsolutePath().toString());
        command.add("-Xms" + WORKER_HEAP_INITIAL_MB + "m");
        command.add("-Xmx" + WORKER_HEAP_MAX_MB + "m");
        command.add("-XX:+ExitOnOutOfMemoryError");
        // 编码必须显式指定：日志要能被客户端按 UTF-8 读回来，中文诊断不能变成乱码
        command.add("-Dfile.encoding=UTF-8");
        command.add("-Dstdout.encoding=UTF-8");
        command.add("-Dstderr.encoding=UTF-8");
        command.add("-Dlog4j2.configurationFile=" + runtimeRoot.resolve("log4j2.xml").toAbsolutePath());
        command.add("-Dlog4j.configurationFile=" + runtimeRoot.resolve("log4j2.xml").toAbsolutePath());
        command.add("-Dyiyiaddon.seedworker=1");
        String libraryPath = System.getProperty("java.library.path");
        if (libraryPath != null && !libraryPath.isBlank()) {
            command.add("-Djava.library.path=" + libraryPath);
        }
        command.add("-cp");
        command.add(classpath);
        command.add(WORKER_MAIN_CLASS);
        command.addAll(SeedWorkerArguments.encode(runtimeRoot.toAbsolutePath().toString(),
                handshakeFile.toAbsolutePath().toString(), token, SeedWorkerProtocol.VERSION, minecraftVersion,
                modVersion, hostPort, ProcessHandle.current().pid()));

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(hostDir.toFile());
        builder.redirectErrorStream(true);
        builder.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));

        Process process;
        try {
            process = builder.start();
        } catch (IOException error) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "无法启动本地世界生成计算器进程（" + error.getMessage() + "）", error);
        }
        SeedWorkerProcess handle = new SeedWorkerProcess(process, runtimeRoot, handshakeFile, logFile, token);
        LOGGER.info("本地世界生成计算器已拉起：PID {}，运行目录 {}，日志 {}，宿主端口 {}",
                handle.pid(), runtimeRoot.toAbsolutePath(), logFile.toAbsolutePath(), hostPort);
        return handle;
    }

    /** Worker 运行目录（按 Minecraft 版本隔离，粒度到版本而不是远程服务器，口径第七十三节）。 */
    public static Path runtimeRoot(Path gameDir, String minecraftVersion) {
        return gameDir.resolve("yiyiaddon").resolve("seed-worker").resolve(minecraftVersion);
    }

    /**
     * 定位 Worker 用的 java 可执行文件。
     *
     * <p>Windows 上优先 {@code javaw.exe}：它是 GUI 子系统程序，<b>不会为子进程弹出控制台窗口</b>；
     * {@code java.exe} 在某些启动器（父进程没有控制台）下会弹一个黑窗，普通用户会以为出了问题
     * （口径第十节）。stdout/stderr 本来就被重定向到 Worker 日志，所以用 javaw 不丢任何诊断信息。</p>
     */
    private static Path resolveJavaExecutable() {
        String javaHome = System.getProperty("java.home");
        if (javaHome == null || javaHome.isBlank()) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "无法定位当前 Java 运行时（java.home 为空）");
        }
        boolean windows = System.getProperty("os.name", "").toLowerCase().contains("win");
        // 不默认 java 在 PATH 上：必须来自「当前正在跑游戏的这个运行时」（口径第九十节）
        List<String> names = windows ? List.of("javaw.exe", "java.exe") : List.of("java");
        for (String name : names) {
            Path candidate = Paths.get(javaHome, "bin", name);
            if (Files.isExecutable(candidate)) {
                return candidate;
            }
        }
        throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                "找不到可执行的 java：" + Paths.get(javaHome, "bin", names.get(0)));
    }

    /**
     * 拼子进程 classpath：当前进程的 classpath + 本模组的落盘路径。
     *
     * <p>相对路径会按<b>当前进程的工作目录</b>绝对化 —— 因为子进程的工作目录是 Worker 自己的目录，
     * 直接透传相对路径会让子进程找不到 Minecraft。这也是「启动参数必须可靠」的一部分
     * （口径第八、九十节）。</p>
     *
     * <p>最后再用<b>父进程的类加载器</b>确认 Worker 主类确实可达：可达才说明「父进程能加载的类，
     * 子进程也能加载」，否则宁可当场失败并说清原因 —— 这正是口径第七节禁止的
     * 「开发能跑、正式跑不了」假成功的防线。</p>
     */
    private static String buildClasspath() {
        Set<String> entries = new LinkedHashSet<>();
        String current = System.getProperty("java.class.path");
        if (current == null || current.isBlank()) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "当前进程没有可用的 classpath，无法启动本地世界生成计算器");
        }
        Path workingDirectory = Paths.get("").toAbsolutePath();
        for (String entry : current.split(java.io.File.pathSeparator)) {
            if (entry.isBlank()) {
                continue;
            }
            Path path = Paths.get(entry);
            entries.add(path.isAbsolute() ? path.toString()
                    : workingDirectory.resolve(path).normalize().toString());
        }
        for (Path modPath : modPaths()) {
            entries.add(modPath.toAbsolutePath().normalize().toString());
        }
        requireWorkerClassLoadable();
        return String.join(java.io.File.pathSeparator, entries);
    }

    /** 本模组的落盘路径（开发期是 build/classes 与 build/resources，正式期是 mods 里的 jar）。 */
    private static List<Path> modPaths() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(container -> new ArrayList<>(container.getOrigin().getPaths()))
                .orElseThrow(() -> new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                        "找不到模组 " + MOD_ID + " 的落盘路径"));
    }

    /** 确认 Worker 主类在父进程类加载器里可达（不可达就没有必要去 fork 一个注定失败的子进程）。 */
    private static void requireWorkerClassLoadable() {
        try {
            Class.forName(WORKER_MAIN_CLASS, false, SeedWorldgenWorkerLauncher.class.getClassLoader());
        } catch (Throwable error) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "找不到 " + WORKER_MAIN_CLASS + "，拒绝启动（避免出现「开发能跑、正式跑不了」的假成功）",
                    error);
        }
    }

    /** 给 Vanilla 宿主挑一个空闲回环端口（宿主起来后会立刻停用监听）。 */
    private static int pickFreeLoopbackPort() {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            return socket.getLocalPort();
        } catch (IOException error) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                    "无法分配本地端口：" + error.getMessage(), error);
        }
    }

    /** 192 位随机令牌（十六进制）。 */
    private static String randomToken() {
        byte[] bytes = new byte[TOKEN_BYTES];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    /**
     * 写 Worker 侧 log4j2 配置（<b>公开</b>：开发探针与正式启动器共用同一份配置写入逻辑）。
     *
     * <p>Worker 是独立进程、独立日志文件（{@code seed-worker-<版本>.log}），
     * 因此要给它一份自己的日志配置，避免它继承客户端那份把日志写到别处
     * （口径第三十六节）。</p>
     */
    public static void writeLog4jConfig(Path path) throws IOException {
        String config = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Configuration status="WARN" name="yiyiaddonSeedWorker">
                  <Appenders>
                    <Console name="WorkerConsole" target="SYSTEM_OUT">
                      <PatternLayout pattern="[%d{HH:mm:ss}] [%t/%level] [%logger]: %msg%n"/>
                    </Console>
                  </Appenders>
                  <Loggers>
                    <Root level="info">
                      <AppenderRef ref="WorkerConsole"/>
                    </Root>
                  </Loggers>
                </Configuration>
                """;
        Files.writeString(path, config, StandardCharsets.UTF_8);
    }
}
