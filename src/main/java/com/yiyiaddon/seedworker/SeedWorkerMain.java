package com.yiyiaddon.seedworker;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.CodeSource;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · <b>本地隔离世界生成 Worker 的进程入口</b>（阶段 232）。
 *
 * <p><b>谁在什么时候拉起它</b>：游戏客户端里的 {@code SeedWorldgenWorkerLauncher} 用
 * {@code ProcessBuilder} 起一个<b>独立 JVM</b>，命令行只带运行目录 / 握手文件 / 令牌 / 版本 / 端口。
 * 它不依赖 Gradle、不依赖 IDE、不依赖开发 sourceSet —— 它用的 classpath 就是启动游戏的那一份
 * （用户自己安装的 Minecraft + Fabric 运行时），因此开发环境与正式环境走的是同一条路径
 * （口径第七、八、九节）。</p>
 *
 * <p><b>它只做一件事</b>：在这个进程里造出一个真正的 Vanilla {@code ServerLevel}，并按 IPC 协议
 * 用正式 {@code SeedOrePredictor} 回答「种子 + 维度 + 目标区块 + 矿物 → 预测结果」
 * （口径第五、十一节）。它不是游戏、不是给玩家连接的服务器、不连接任何远程服务器。</p>
 *
 * <p><b>退出路径</b>（口径第三十二、七十七节）：① 收到 SHUTDOWN；② 客户端连接断开（EOF）；
 * ③ 父进程消失（看门狗）。三条路径都走同一个收尾函数，因此不会留下孤儿进程。</p>
 */
public final class SeedWorkerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed-worker");

    /** 本模组的模组 id 与元数据文件名：类路径上同名资源很多，只有 id 命中的那一份算数。 */
    private static final String MOD_ID = "yiyiaddon";
    private static final String MOD_METADATA = "fabric.mod.json";

    private static final Pattern ID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern VERSION_PATTERN = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");

    /** 宿主世界目录的体积上限：它是缓存而不是数据，超限即整份删除重建（口径第三十七节）。 */
    private static final long HOST_DIR_SIZE_LIMIT_BYTES = 512L * 1024L * 1024L;

    private SeedWorkerMain() {
    }

    /** 进程入口。 */
    public static void main(String[] args) {
        SeedWorkerArgs parsed;
        try {
            parsed = SeedWorkerArgs.parse(args);
        } catch (Throwable error) {
            System.err.println("[seed-worker] 启动参数非法：" + error.getMessage());
            System.exit(2);
            return;
        }

        SeedWorkerHost host = null;
        SeedWorkerSessions sessions = null;
        SeedWorkerIpcServer ipc = null;
        int exitCode = 3;
        // 版本必须在任何地方用到它之前确定（SharedConstants 不先探测就会抛 "Game version not set"）：
        // 日志、握手、会话隔离键都要用到它
        SharedConstants.tryDetectVersion();
        String modVersion = modVersion();
        try {
            Files.createDirectories(parsed.runtimeDir().resolve("logs"));
            deleteQuietly(parsed.handshakeFile());
            watchParent(parsed);

            LOGGER.info("本地世界生成计算器启动：PID {}，协议 {}，Minecraft {}，模组 {}，运行目录 {}",
                    ProcessHandle.current().pid(), SeedWorkerProtocol.VERSION,
                    SharedConstants.getCurrentVersion().id(), modVersion, parsed.runtimeDir().toAbsolutePath());

            host = new SeedWorkerHost(parsed.runtimeDir().resolve("host"), parsed.hostPort());
            purgeOversizedHostDir(host.universeDir());
            host.boot();

            sessions = new SeedWorkerSessions(host);
            ipc = new SeedWorkerIpcServer(parsed, sessions, modVersion);
            int port = ipc.bind();
            writeHandshake(parsed, port, modVersion, host);
            LOGGER.info("本地世界生成计算器已就绪（IPC 端口 {}，宿主监听 {}，等待客户端指令）",
                    port, host.listenerStateCn());

            ipc.serve();
            exitCode = 0;
        } catch (Throwable error) {
            LOGGER.error("本地世界生成计算器启动或运行失败", error);
            System.err.println("[seed-worker] 失败：" + error);
        } finally {
            LOGGER.info("本地世界生成计算器开始收尾（退出码 {}）", exitCode);
            closeQuietly(sessions);
            closeQuietly(host);
            closeQuietly(ipc);
            deleteQuietly(parsed.handshakeFile());
            LOGGER.info("本地世界生成计算器已退出");
        }
        System.exit(exitCode);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 生命周期辅助
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 父进程看门狗。
     *
     * <p>为什么需要它：IPC 连接断开已经能覆盖「客户端正常关闭 / 崩溃」，但如果客户端进程被强杀、
     * 连接还来不及关闭，Worker 也可能残留。看门狗按 PID 直接判定父进程是否还在，
     * 是杜绝孤儿 Java 进程的最后一道保险（口径第三十二、七十七节）。</p>
     */
    private static void watchParent(SeedWorkerArgs args) {
        Thread watchdog = new Thread(() -> {
            while (true) {
                Optional<ProcessHandle> parent = ProcessHandle.of(args.parentPid());
                if (parent.isEmpty() || !parent.get().isAlive()) {
                    LOGGER.warn("父进程（PID {}）已消失，Worker 自我了断，避免留下孤儿进程", args.parentPid());
                    Runtime.getRuntime().halt(0);
                }
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "yiyiaddon-seed-worker-parent-watchdog");
        watchdog.setDaemon(true);
        watchdog.start();
    }

    /**
     * 写握手文件（原子替换）。
     *
     * <p>它是客户端「可以连接了」的唯一信号，因此只在宿主真正就绪之后写：客户端看到端口的那一刻，
     * 对应的 {@code ServerLevel} 一定已经可用（口径第二十五、三十四节）。</p>
     */
    private static void writeHandshake(SeedWorkerArgs args, int port, String modVersion, SeedWorkerHost host)
            throws IOException {
        JsonObject handshake = new JsonObject();
        handshake.addProperty("protocolVersion", SeedWorkerProtocol.VERSION);
        handshake.addProperty("minecraftVersion", SharedConstants.getCurrentVersion().id());
        handshake.addProperty("modVersion", modVersion);
        handshake.addProperty("workerPid", ProcessHandle.current().pid());
        handshake.addProperty("port", port);
        handshake.addProperty("readyAtEpochMillis", System.currentTimeMillis());
        handshake.addProperty("listenerState", host.listenerStateCn());
        Path temporary = args.handshakeFile().resolveSibling(args.handshakeFile().getFileName() + ".tmp");
        Files.writeString(temporary, handshake.toString(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        Files.move(temporary, args.handshakeFile(), StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 小工具
    // ────────────────────────────────────────────────────────────────────────

    /** 宿主世界目录是缓存：超限就整份删掉（下次启动重建），避免无限增长（口径第三十七节）。 */
    private static void purgeOversizedHostDir(Path universeDir) {
        if (!Files.isDirectory(universeDir)) {
            return;
        }
        long size = directorySize(universeDir);
        if (size <= HOST_DIR_SIZE_LIMIT_BYTES) {
            LOGGER.info("宿主世界目录 {}/{} MB（保留以加速下次启动）",
                    size / (1024 * 1024), HOST_DIR_SIZE_LIMIT_BYTES / (1024 * 1024));
            return;
        }
        LOGGER.warn("宿主世界目录 {} MB 超过上限 {} MB，整份删除后重建",
                size / (1024 * 1024), HOST_DIR_SIZE_LIMIT_BYTES / (1024 * 1024));
        deleteRecursively(universeDir);
    }

    private static long directorySize(Path directory) {
        try (Stream<Path> paths = Files.walk(directory)) {
            return paths.filter(Files::isRegularFile).mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException ignored) {
                    return 0L;
                }
            }).sum();
        } catch (IOException error) {
            return 0L;
        }
    }

    private static void deleteRecursively(Path path) {
        if (!Files.exists(path)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(path)) {
            for (Path item : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(item);
            }
        } catch (IOException error) {
            LOGGER.warn("删除 {} 失败（下次启动可能仍偏大）", path, error);
        }
    }

    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // 握手文件删不掉不影响正确性（下次启动会先删一次）
        }
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignored) {
            // 收尾阶段的关闭失败没有处置价值
        }
    }

    /**
     * 本 Worker 实际加载的 yiyiaddon 版本。
     *
     * <p>不能读类路径上第一个 {@code /fabric.mod.json}：正式 Fabric 环境里 Loader 自己也在类路径上，
     * 那份元数据的版本是 loader 的（232-P 实测读到 0.19.5），于是客户端把一切正常的情况误判成
     * 「模组版本不一致」并拒绝预测 —— 开发环境先命中模组资源目录，永远看不到这个问题。</p>
     *
     * <p>做法：优先读「本类所在的那个 jar」的元数据 —— 它才是 Worker 真正加载的那份 yiyiaddon，
     * 同时也正好能识破「类路径混进第二个旧 yiyiaddon jar」这种真事故；开发期类在目录里，
     * 就退回按类路径逐个条目找 id = yiyiaddon 的那份元数据。</p>
     */
    private static String modVersion() {
        String fromOwnJar = modVersionFromOwnJar();
        if (fromOwnJar != null) {
            return fromOwnJar;
        }
        for (String entry : System.getProperty("java.class.path", "").split(File.pathSeparator)) {
            if (entry.isBlank()) {
                continue;
            }
            Path path = Path.of(entry);
            String version = Files.isDirectory(path)
                    ? modVersionFromText(readQuietly(path.resolve(MOD_METADATA)))
                    : modVersionFromJar(path);
            if (version != null) {
                return version;
            }
        }
        return "unknown";
    }

    /** 本类所在 jar 的元数据版本；类在目录里（开发期）时返回 null。 */
    private static String modVersionFromOwnJar() {
        try {
            CodeSource source = SeedWorkerMain.class.getProtectionDomain().getCodeSource();
            if (source == null) {
                return null;
            }
            Path location = Path.of(source.getLocation().toURI());
            return Files.isRegularFile(location) ? modVersionFromJar(location) : null;
        } catch (Throwable error) {
            return null;
        }
    }

    private static String modVersionFromJar(Path jar) {
        try (JarFile file = new JarFile(jar.toFile())) {
            JarEntry entry = file.getJarEntry(MOD_METADATA);
            if (entry == null) {
                return null;
            }
            try (InputStream stream = file.getInputStream(entry)) {
                return modVersionFromText(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
            }
        } catch (Throwable error) {
            return null;
        }
    }

    /** 带模组 id 校验的版本提取：类路径上很多 fabric.mod.json，只有本模组那一份算数。 */
    private static String modVersionFromText(String text) {
        if (text == null) {
            return null;
        }
        Matcher id = ID_PATTERN.matcher(text);
        if (!id.find() || !MOD_ID.equals(id.group(1))) {
            return null;
        }
        Matcher version = VERSION_PATTERN.matcher(text);
        return version.find() ? version.group(1) : null;
    }

    private static String readQuietly(Path file) {
        try {
            return Files.isRegularFile(file) ? Files.readString(file, StandardCharsets.UTF_8) : null;
        } catch (Throwable error) {
            return null;
        }
    }
}
