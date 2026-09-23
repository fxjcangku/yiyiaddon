package com.yiyiaddon.seed.worker.client;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 种子挖矿 · Worker <b>进程句柄</b>（客户端侧）。
 *
 * <p>负责一件很具体的事：把一个已经拉起的 Worker 进程，从「刚 fork 出来」等到「宿主就绪并交出端口」，
 * 并在它死掉的时候给出可诊断的信息（退出码 + 日志尾部）。业务协议不在这里，见
 * {@link SeedWorldgenWorkerClient}。</p>
 *
 * <p><b>为什么用握手文件而不是 stdout</b>（阶段 232 口径第十七、二十五节）：
 * Minecraft / Fabric 的日志会持续写 stdout，把它当控制通道迟早会被污染；
 * 而 Worker 用系统分配的端口，客户端又必须知道端口，所以由 Worker 在<b>宿主真正可用之后</b>
 * 原子地写一个握手文件，客户端以文件出现作为「可以连接」的唯一信号。</p>
 */
public final class SeedWorkerProcess {

    /** 读日志尾部时的最大读取字节（只看末尾，避免整份读进来）。 */
    private static final int LOG_TAIL_BYTES = 256 * 1024;

    private final Process process;
    private final Path runtimeRoot;
    private final Path handshakeFile;
    private final Path logFile;
    private final String token;
    private final long startedAtMillis;

    public SeedWorkerProcess(Process process, Path runtimeRoot, Path handshakeFile, Path logFile, String token) {
        this.process = process;
        this.runtimeRoot = runtimeRoot;
        this.handshakeFile = handshakeFile;
        this.logFile = logFile;
        this.token = token;
        this.startedAtMillis = System.currentTimeMillis();
    }

    /** 进程 PID。 */
    public long pid() {
        return process.pid();
    }

    /** 令牌（IPC 鉴权用；只在客户端进程内传递，不写盘）。 */
    public String token() {
        return token;
    }

    /** Worker 运行目录。 */
    public Path runtimeRoot() {
        return runtimeRoot;
    }

    /** Worker 日志文件。 */
    public Path logFile() {
        return logFile;
    }

    /** 进程是否还活着。 */
    public boolean alive() {
        return process.isAlive();
    }

    /** 退出码；还活着返回 {@link Integer#MIN_VALUE}。 */
    public int exitCodeOrMinusOne() {
        return process.isAlive() ? Integer.MIN_VALUE : process.exitValue();
    }

    /** 强杀阶梯的最后一级（前置步骤由 {@link SeedWorldgenWorkerClient} 按顺序调用）。 */
    public void destroy() {
        if (process.isAlive()) {
            process.destroy();
        }
    }

    /** 强杀。 */
    public void destroyForcibly() {
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }

    /** 等待进程退出。 */
    public boolean awaitExit(long timeoutMillis) {
        try {
            return process.waitFor(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            return !process.isAlive();
        }
    }

    /**
     * 等握手文件出现并解析它。
     *
     * @param timeoutMillis 启动超时（口径第三十四节：绝不无限卡在 STARTING）
     */
    public Handshake awaitHandshake(long timeoutMillis) {
        long deadline = System.currentTimeMillis() + timeoutMillis;
        while (System.currentTimeMillis() < deadline) {
            if (Files.isRegularFile(handshakeFile)) {
                Handshake handshake = readHandshake();
                if (handshake != null) {
                    return handshake;
                }
            }
            if (!process.isAlive()) {
                throw new SeedWorkerException(SeedWorkerException.ERROR_LAUNCH_FAILED,
                        "本地世界生成计算器提前退出（退出码 " + process.exitValue() + "）。" + diagnostics());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new SeedWorkerException(SeedWorkerException.ERROR_STARTUP_TIMEOUT,
                        "等待本地世界生成计算器时被中断");
            }
        }
        throw new SeedWorkerException(SeedWorkerException.ERROR_STARTUP_TIMEOUT,
                "本地世界生成计算器启动超时（" + timeoutMillis + " ms）。" + diagnostics());
    }

    /** 诊断串：退出码 + 日志路径 + 日志尾部若干行（口径第三十四节要求必须带这些）。 */
    public String diagnostics() {
        StringBuilder text = new StringBuilder();
        text.append("Worker 日志：").append(logFile.toAbsolutePath());
        if (!process.isAlive()) {
            text.append("；退出码 ").append(process.exitValue());
        } else {
            text.append("；进程仍在运行（PID ").append(process.pid()).append("）");
        }
        List<String> tail = logTail(12);
        if (!tail.isEmpty()) {
            text.append("；日志尾部：");
            for (String line : tail) {
                text.append("\n  ").append(line);
            }
        }
        return text.toString();
    }

    /** 读日志尾部若干行（只读文件末尾，避免把整份日志读进内存）。 */
    public List<String> logTail(int maxLines) {
        if (!Files.isRegularFile(logFile)) {
            return List.of();
        }
        try (RandomAccessFile file = new RandomAccessFile(logFile.toFile(), "r")) {
            long length = file.length();
            long start = Math.max(0L, length - LOG_TAIL_BYTES);
            file.seek(start);
            byte[] bytes = new byte[(int) (length - start)];
            file.readFully(bytes);
            String text = new String(bytes, StandardCharsets.UTF_8);
            String[] lines = text.split("\\R");
            List<String> tail = new ArrayList<>();
            for (int index = Math.max(0, lines.length - maxLines); index < lines.length; index++) {
                if (!lines[index].isBlank()) {
                    tail.add(lines[index]);
                }
            }
            return tail;
        } catch (IOException error) {
            return List.of("（读日志失败：" + error.getMessage() + "）");
        }
    }

    /** 启动耗时（毫秒）。 */
    public long startupElapsedMillis() {
        return System.currentTimeMillis() - startedAtMillis;
    }

    private Handshake readHandshake() {
        try {
            JsonObject object = WorkerJson.parseObject(
                    Files.readString(handshakeFile, StandardCharsets.UTF_8),
                    SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE);
            return new Handshake(
                    WorkerJson.reqInt(object, "protocolVersion"),
                    WorkerJson.reqString(object, "minecraftVersion"),
                    WorkerJson.reqString(object, "modVersion"),
                    WorkerJson.reqLong(object, "workerPid"),
                    WorkerJson.reqInt(object, "port"),
                    WorkerJson.optLong(object, "readyAtEpochMillis", 0L),
                    WorkerJson.optString(object, "listenerState", ""));
        } catch (WorkerProtocolException malformed) {
            return null; // 可能正读到半份文件（虽然写了原子替换，仍保守重试）
        } catch (IOException unreadable) {
            return null;
        }
    }

    /**
     * 握手内容（Worker 自述 + 端口）。
     *
     * @param protocolVersion    协议版本
     * @param minecraftVersion   Worker 侧 Minecraft 版本
     * @param modVersion         Worker 侧模组版本
     * @param workerPid          Worker 进程 PID
     * @param port               回环 IPC 端口
     * @param readyAtEpochMillis 宿主就绪时刻
     * @param listenerState      Vanilla 宿主对外监听的处置结果（诊断）
     */
    public record Handshake(int protocolVersion, String minecraftVersion, String modVersion, long workerPid,
                            int port, long readyAtEpochMillis, String listenerState) {
    }
}
