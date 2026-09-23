package com.yiyiaddon.seedworker;

import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import com.yiyiaddon.seed.worker.protocol.WorkerLineReader;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import com.yiyiaddon.seed.worker.protocol.WorkerRequest;
import com.yiyiaddon.seed.worker.protocol.WorkerResponse;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerHelloDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · Worker · <b>IPC 服务端</b>（回环 TCP + 一行一条 JSON）。
 *
 * <p><b>为什么是这个形态</b>（阶段 232 口径第十七、十八节）：</p>
 * <ul>
 *   <li>只绑定 <b>127.0.0.1</b>，端口由系统分配（客户端只通过握手文件得知），绝不监听 0.0.0.0；</li>
 *   <li>协议是版本化的一行 JSON，带请求代号、错误码、长度上限与超时，不用 stdout
 *       （Minecraft / Fabric 日志会污染它），也不用 Java 序列化（口径第二十节）；</li>
 *   <li>每个请求都必须携带启动时随机生成的 <b>128 位以上令牌</b>，否则连握手都过不去，
 *       本机其它进程无法向 Worker 下预测命令（口径第十八节）。</li>
 * </ul>
 *
 * <p><b>线程模型</b>（口径第五十八、五十九节）：读线程只做「解析 + 鉴权 + 写回」；
 * 真正的会话与预测全部丢给<b>单线程 worldgen executor</b>，因此同一时刻只有一个世界生成在跑，
 * 且正式预测器不需要任何锁。连接只允许一条（客户端那条）。</p>
 *
 * <p><b>连接断开即退出</b>：父进程消失 / 客户端关闭时连接会 EOF，Worker 据此自我了断，
 * 从架构上杜绝孤儿 Java 进程（口径第三十二、七十七节）。</p>
 */
public final class SeedWorkerIpcServer implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed-worker");

    /** 等待客户端连接的上限（毫秒）；超时即认为客户端不要我们了（父进程已死）。 */
    private static final int ACCEPT_TIMEOUT_MILLIS = 180_000;

    /** 空闲读上限（毫秒）：超时只重置缓冲继续等，不当作错误（玩家可能长时间不点预测）。 */
    private static final int IDLE_READ_TIMEOUT_MILLIS = 600_000;

    private final SeedWorkerArgs args;
    private final SeedWorkerSessions sessions;
    private final String modVersion;

    /** 单线程 worldgen executor（会话与预测都在这里跑）。 */
    private final ExecutorService worldgen = Executors.newSingleThreadExecutor(task -> {
        Thread thread = new Thread(task, "yiyiaddon-seed-worker-worldgen");
        thread.setDaemon(true);
        return thread;
    });

    private final Object writeLock = new Object();

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream clientOut;
    private volatile boolean running;
    private volatile long handledRequests;
    private volatile long rejectedRequests;

    public SeedWorkerIpcServer(SeedWorkerArgs args, SeedWorkerSessions sessions, String modVersion) {
        this.args = args;
        this.sessions = sessions;
        this.modVersion = modVersion;
    }

    /** 绑定回环端口；返回实际监听的端口。 */
    public int bind() throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.setReuseAddress(false);
        socket.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 1);
        socket.setSoTimeout(ACCEPT_TIMEOUT_MILLIS);
        this.serverSocket = socket;
        this.running = true;
        return socket.getLocalPort();
    }

    /** 已处理的请求数（诊断）。 */
    public long handledRequests() {
        return handledRequests;
    }

    /** 被拒绝的请求数（令牌错 / 版本错 / 协议错，诊断）。 */
    public long rejectedRequests() {
        return rejectedRequests;
    }

    /**
     * 接受客户端连接并服务到它断开（或收到 SHUTDOWN）。
     *
     * <p>返回之后调用方必须收尾并退出进程 —— 本方法返回的唯一含义就是「该停机了」。</p>
     */
    public void serve() {
        while (running && clientSocket == null) {
            try {
                Socket accepted = serverSocket.accept();
                accepted.setTcpNoDelay(true);
                accepted.setSoTimeout(IDLE_READ_TIMEOUT_MILLIS);
                InetAddress remote = accepted.getInetAddress();
                if (!remote.isLoopbackAddress()) {
                    // 理论上不可能（只绑了回环）；仍然显式拒绝，避免任何误配置带来的暴露
                    LOGGER.warn("拒绝非回环连接：{}", remote);
                    rejectedRequests++;
                    closeQuietly(accepted);
                    continue;
                }
                clientSocket = accepted;
                clientOut = accepted.getOutputStream();
                LOGGER.info("客户端已连接（来自 {}）", remote);
            } catch (SocketTimeoutException timeout) {
                LOGGER.info("等待客户端连接超时（{} ms），按「父进程已消失」处理并退出",
                        ACCEPT_TIMEOUT_MILLIS);
                return;
            } catch (IOException error) {
                if (running) {
                    LOGGER.warn("接受连接失败", error);
                }
                return;
            }
        }
        try {
            readLoop();
        } finally {
            running = false;
        }
    }

    @Override
    public void close() {
        running = false;
        closeQuietly(clientSocket);
        closeQuietly(serverSocket);
        worldgen.shutdownNow();
        try {
            worldgen.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读循环
    // ────────────────────────────────────────────────────────────────────────

    private void readLoop() {
        InputStream input;
        try {
            input = clientSocket.getInputStream();
        } catch (IOException error) {
            LOGGER.warn("取连接输入流失败", error);
            return;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(4096);
        while (running && !clientSocket.isClosed()) {
            String line;
            try {
                line = WorkerLineReader.readLine(input, buffer, SeedWorkerProtocol.MAX_LINE_BYTES);
            } catch (SocketTimeoutException idle) {
                continue; // 只是长时间没请求：保持连接，不算错
            } catch (IOException error) {
                LOGGER.warn("读取请求失败（连接关闭）", error);
                return;
            }
            if (line == null) {
                LOGGER.info("客户端断开连接，Worker 准备退出");
                return;
            }
            if (line.isBlank()) {
                continue;
            }
            if (!handleLine(line)) {
                return;
            }
        }
    }

    /** 处理一行请求；返回 false 表示要求关闭连接。 */
    private boolean handleLine(String line) {
        WorkerRequest request;
        try {
            request = WorkerRequest.fromJson(WorkerJson.parseObject(line,
                    SeedWorkerProtocol.ERROR_MALFORMED_REQUEST));
        } catch (WorkerProtocolException error) {
            rejectedRequests++;
            LOGGER.warn("收到非法请求行：{}", error.getMessage());
            send(WorkerResponse.error(0L, error.errorCode(), error.getMessage()));
            return false;
        }
        if (!matchesToken(request.token())) {
            rejectedRequests++;
            LOGGER.warn("令牌校验失败（请求代号 {}，来自本机其它进程？），关闭连接", request.id());
            send(WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_BAD_TOKEN,
                    "令牌不匹配：本机其它进程不得向本地世界生成计算器发送命令"));
            return false;
        }
        if (request.protocolVersion() != SeedWorkerProtocol.VERSION) {
            rejectedRequests++;
            send(WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH,
                    "协议版本不一致：客户端 " + request.protocolVersion() + "，Worker "
                            + SeedWorkerProtocol.VERSION));
            return false;
        }
        String op = request.op();
        switch (op) {
            case SeedWorkerProtocol.OP_HELLO -> handleHello(request);
            case SeedWorkerProtocol.OP_PING -> send(WorkerResponse.success(request.id()));
            case SeedWorkerProtocol.OP_OPEN_SESSION -> submit(request,
                    () -> WorkerResponse.session(request.id(), sessions.openSession(request.seed(),
                            request.dimension())));
            case SeedWorkerProtocol.OP_PREDICT_DIAMOND -> submit(request,
                    () -> WorkerResponse.prediction(request.id(), sessions.predict(request.seed(),
                            request.dimension(), request.chunkX(), request.chunkZ(), request.oreType())));
            case SeedWorkerProtocol.OP_CLOSE_SESSION -> submit(request,
                    () -> WorkerResponse.session(request.id(), sessions.closeSession()));
            case SeedWorkerProtocol.OP_SHUTDOWN -> {
                send(WorkerResponse.success(request.id()));
                LOGGER.info("收到停机指令，准备退出（累计处理 {} 个请求）", handledRequests);
                return false;
            }
            default -> {
                rejectedRequests++;
                send(WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_UNKNOWN_OP,
                        "未知操作码：" + op));
            }
        }
        handledRequests++;
        return true;
    }

    /** 握手：校验 Minecraft 版本，返回 Worker 自述信息。 */
    private void handleHello(WorkerRequest request) {
        String workerMinecraft = SharedConstants.getCurrentVersion().id();
        if (!request.minecraftVersion().isEmpty() && !request.minecraftVersion().equals(workerMinecraft)) {
            rejectedRequests++;
            send(WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_MINECRAFT_MISMATCH,
                    "Minecraft 版本不一致：客户端 " + request.minecraftVersion() + "，Worker " + workerMinecraft));
            return;
        }
        WorkerHelloDto hello = new WorkerHelloDto(SeedWorkerProtocol.VERSION, workerMinecraft, modVersion,
                ProcessHandle.current().pid(),
                List.of(SeedWorkerProtocol.CAPABILITY_PREDICT_DIAMOND_OVERWORLD));
        LOGGER.info("握手成功：协议 {}，Minecraft {}，模组 {}，PID {}",
                hello.protocolVersion(), hello.minecraftVersion(), hello.modVersion(), hello.workerPid());
        send(WorkerResponse.hello(request.id(), hello));
    }

    /** 把耗时操作丢给单线程 worldgen executor，结果（或错误）都写成响应。 */
    private void submit(WorkerRequest request, RequestHandler handler) {
        try {
            worldgen.execute(() -> {
                WorkerResponse response;
                try {
                    response = handler.handle();
                } catch (WorkerProtocolException protocol) {
                    rejectedRequests++;
                    response = WorkerResponse.error(request.id(), protocol.errorCode(), protocol.getMessage());
                } catch (Throwable error) {
                    LOGGER.error("处理请求 {} 时出现异常", request.op(), error);
                    response = WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_INTERNAL,
                            "Worker 内部异常：" + error.getClass().getSimpleName()
                                    + (error.getMessage() == null ? "" : ": " + error.getMessage()));
                }
                send(response);
            });
        } catch (RejectedExecutionException rejected) {
            send(WorkerResponse.error(request.id(), SeedWorkerProtocol.ERROR_INTERNAL,
                    "Worker 正在停机，无法受理新请求"));
        }
    }

    private void send(WorkerResponse response) {
        OutputStream out = clientOut;
        if (out == null) {
            return;
        }
        String line = WorkerJson.encode(response.toJson());
        byte[] bytes = (line + "\n").getBytes(StandardCharsets.UTF_8);
        synchronized (writeLock) {
            try {
                out.write(bytes);
                out.flush();
            } catch (IOException error) {
                LOGGER.warn("写响应失败（连接可能已关闭）：{}", response.id());
            }
        }
    }

    /** 令牌比较（定长比较，避免逐字节提前返回带来的时序差异）。 */
    private boolean matchesToken(String candidate) {
        if (candidate == null) {
            return false;
        }
        return MessageDigest.isEqual(candidate.getBytes(StandardCharsets.UTF_8),
                args.token().getBytes(StandardCharsets.UTF_8));
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

    /** 请求处理器（在 worldgen executor 上执行）。 */
    @FunctionalInterface
    private interface RequestHandler {
        WorkerResponse handle() throws Exception;
    }
}
