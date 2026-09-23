package com.yiyiaddon.seed.worker.client;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import com.yiyiaddon.seed.worker.protocol.WorkerLineReader;
import com.yiyiaddon.seed.worker.protocol.WorkerProtocolException;
import com.yiyiaddon.seed.worker.protocol.WorkerRequest;
import com.yiyiaddon.seed.worker.protocol.WorkerResponse;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · <b>本地世界生成计算器客户端</b>（进程生命周期 + IPC 会话 + 预测请求）。
 *
 * <p><b>它解决的原始问题</b>（阶段 232 口径第三、五、九十二条）：正式预测需要一个真正的
 * Vanilla {@code ServerLevel}，而多人服务器客户端没有；因此本类负责把一个<b>隔离的本地 Worker 进程</b>
 * 拉起来、握手、开后端会话，再把「种子 + 维度 + 目标区块」发过去。
 * 无论单人还是多人，正式产品都走这一条路（口径第九十三节），因此不存在两套正式逻辑。</p>
 *
 * <p><b>调用约定</b>：本类的全部方法都在<b>后台单线程</b>（服务层的 worldgen executor）上调用；
 * {@link #state()} 等读数可以从任意线程读（volatile）。同一时刻只有一个请求在飞，
 * 因此不需要为「并发请求」设计任何东西。</p>
 *
 * <p><b>超时与恢复</b>（口径第三十三、三十四、三十五节）：</p>
 * <ul>
 *   <li>启动有超时，绝不允许永远停在「正在启动」；超时会带上退出码与日志尾部；</li>
 *   <li>每次预测有超时，绝不允许界面永远停在「正在预测」；</li>
 *   <li>Worker 崩溃 / 传输故障 ⇒ 进入 FAILED，并允许用户在<b>下一次点击</b>时自动重启，
 *       但连续失败有次数上限与最小间隔，避免无限快速重启循环。</li>
 * </ul>
 */
public final class SeedWorldgenWorkerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词。 */
    private static final String LOG_KEY = "种子挖矿｜Worker";

    /**
     * 启动超时（毫秒）：宿主首次建世界时最慢，实测后取一个留足余量的值。
     *
     * <p>验收可覆盖：{@code -Dyiyiaddon.seedworker.startupTimeoutMillis=1} 用来真实触发
     * 「启动超时」这条路（口径第十六、三十四节），正式运行不设该属性即走默认值。</p>
     */
    public static final long STARTUP_TIMEOUT_MILLIS =
            SeedWorldgenWorkerLauncher.diagnosticsInt("yiyiaddon.seedworker.startupTimeoutMillis", 120_000);

    /** 单次预测超时（毫秒）：正式预测器冷启动约 2~3 秒，这里给足余量但绝不无限等。 */
    public static final long PREDICT_TIMEOUT_MILLIS =
            SeedWorldgenWorkerLauncher.diagnosticsInt("yiyiaddon.seedworker.predictTimeoutMillis", 180_000);

    /** 连接回环端口的超时（毫秒）。 */
    private static final long CONNECT_TIMEOUT_MILLIS = 15_000L;

    /** 连续启动失败上限（同一世界会话内）。 */
    private static final int MAX_CONSECUTIVE_FAILURES = 3;

    /** 两次启动尝试之间的最小间隔（毫秒）。 */
    private static final long RESTART_MIN_INTERVAL_MILLIS = 5_000L;

    /** 崩溃看门狗的轮询间隔（毫秒）：意外退出的发现延迟上限就是它。 */
    private static final long WATCHDOG_POLL_MILLIS = 500L;

    /** 优雅停机等待（毫秒）。 */
    private static final long SHUTDOWN_GRACE_MILLIS = 3_000L;

    /** destroy 之后等待（毫秒）。 */
    private static final long DESTROY_GRACE_MILLIS = 2_000L;

    private final Object requestLock = new Object();

    private volatile SeedWorkerState state = SeedWorkerState.STOPPED;

    // ── 进程与连接 ──
    private SeedWorkerProcess process;
    private Socket socket;
    private OutputStream socketOut;
    private InputStream socketIn;
    private ByteArrayOutputStream readBuffer;
    private volatile boolean stopping;

    // ── 握手信息 ──
    private SeedWorkerProcess.Handshake handshake;

    /** 最近一次握手拿到的能力清单（诊断与「该维度能不能算」的判据）。 */
    private volatile java.util.List<String> lastCapabilities = java.util.List.of();

    // ── 会话 ──
    private String sessionId;
    private long sessionSeed;
    private String sessionDimension;

    // ── 请求代号 ──
    private long nextRequestId;

    // ── 恢复策略与诊断 ──
    private int consecutiveFailures;
    private long lastFailureAtMillis;
    private volatile String lastErrorCn = "";
    private volatile long lastStartupMillis;
    private volatile long lastPredictMillis;
    private volatile int lastHostChunkSourceQueries = -1;
    private volatile int startCount;
    private volatile int predictCount;

    /** 当前状态（可从任意线程读）。 */
    public SeedWorkerState state() {
        return state;
    }

    /** 最近一次错误的中文说明（没有错误时为空串）。 */
    public String lastErrorCn() {
        return lastErrorCn;
    }

    /** Worker 是否已经在跑（可读）。 */
    public boolean processRunning() {
        SeedWorkerProcess current = process;
        return current != null && current.alive();
    }

    /** Worker 进程 PID（没有进程时返回 -1）。 */
    public long workerPid() {
        SeedWorkerProcess current = process;
        return current == null ? -1L : current.pid();
    }

    /** 最近一次启动耗时（毫秒，含宿主初始化与握手）。 */
    public long lastStartupMillis() {
        return lastStartupMillis;
    }

    /** 最近一次预测的端到端耗时（毫秒，含 IPC 往返）。 */
    public long lastPredictMillis() {
        return lastPredictMillis;
    }

    /** 最近一次预测报告的宿主 ChunkMap 查询次数（-1 = 还没预测过；必须为 0）。 */
    public int lastHostChunkSourceQueries() {
        return lastHostChunkSourceQueries;
    }

    /** 启动次数（诊断：报告里的生命周期证据）。 */
    public int startCount() {
        return startCount;
    }

    /** 预测次数（诊断）。 */
    public int predictCount() {
        return predictCount;
    }

    /** 当前会话种子（没有会话时为 0）。 */
    public long sessionSeed() {
        return sessionSeed;
    }

    /** 当前会话标识（没有会话时为空串）。 */
    public String sessionId() {
        return sessionId == null ? "" : sessionId;
    }

    /** 一行诊断（日志用；正式界面不显示这些技术细节，口径第七十四节）。 */
    public String diagnosticsCn() {
        SeedWorkerProcess current = process;
        return "状态=" + state.displayNameCn()
                + "；PID=" + (current == null ? "无" : current.pid())
                + "；会话=" + (sessionId == null || sessionId.isEmpty() ? "无" : sessionId)
                + "；启动次数=" + startCount
                + "；预测次数=" + predictCount
                + "；最近启动耗时=" + lastStartupMillis + " ms"
                + "；最近预测耗时=" + lastPredictMillis + " ms"
                + "；宿主 ChunkMap 查询=" + lastHostChunkSourceQueries;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 生命周期
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 确保 Worker 已启动并完成握手（懒启动：只有真的要用才拉起，口径第二十七节）。
     *
     * @throws SeedWorkerException 启动失败 / 超时 / 版本不符 / 达到重试上限
     */
    public void ensureStarted(String minecraftVersion, String modVersion) {
        synchronized (requestLock) {
            if (state.usable()) {
                return;
            }
            if (process != null && process.alive() && state == SeedWorkerState.STARTING) {
                // 上一次调用只走到「进程已起、握手未完」：接着等，不重复 fork
                completeHandshake(minecraftVersion, modVersion);
                return;
            }
            if (consecutiveFailures >= MAX_CONSECUTIVE_FAILURES
                    && System.currentTimeMillis() - lastFailureAtMillis < RESTART_MIN_INTERVAL_MILLIS) {
                throw new SeedWorkerException(SeedWorkerException.ERROR_NOT_READY,
                        "本地世界生成计算器连续启动失败 " + consecutiveFailures + " 次，暂停自动重试（请稍后再试或重新进入世界）");
            }
            startInternal(minecraftVersion, modVersion);
        }
    }

    /** 打开（或复用）「种子 + 维度」会话；换种子会让 Worker 释放旧会话。 */
    public void ensureSession(long seed, String dimension) {
        synchronized (requestLock) {
            if (sessionId != null && sessionSeed == seed && dimension.equals(sessionDimension)) {
                return;
            }
            requireUsable("打开会话");
            // 236：先看 Worker 声明了没有这个维度的能力，避免「会话开了一半才发现宿主缺那一层」
            requireDimensionCapability(dimension);
            WorkerResponse response = exchange(WorkerRequest.openSession(++nextRequestId,
                    process.token(), seed, dimension));
            if (response.session() == null) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                        "打开会话的应答缺少会话信息");
            }
            sessionId = response.session().sessionId();
            sessionSeed = response.session().seed();
            sessionDimension = response.session().dimension();
            LOGGER.info("{}：会话已打开 {}（种子 {}，维度 {}）", LOG_KEY, sessionId, sessionSeed,
                    sessionDimension);
        }
    }

    /** 关闭会话并释放 Worker 侧离线世界缓存（换种子 / 退世界 / 关功能时调用）。 */
    public void closeSession() {
        synchronized (requestLock) {
            if (sessionId == null) {
                return;
            }
            String closed = sessionId;
            sessionId = null;
            sessionSeed = 0L;
            sessionDimension = null;
            if (!state.usable() || process == null || !process.alive()) {
                LOGGER.info("{}：会话 {} 的 Worker 已不在运行，跳过关闭请求", LOG_KEY, closed);
                return;
            }
            try {
                exchange(WorkerRequest.closeSession(++nextRequestId, process.token()));
                LOGGER.info("{}：会话 {} 已关闭（Worker 侧离线世界缓存已释放）", LOG_KEY, closed);
            } catch (SeedWorkerException error) {
                LOGGER.warn("{}：关闭会话失败（不影响正确性，Worker 会被整体停止）：{}",
                        LOG_KEY, error.getMessage());
            }
        }
    }

    /**
     * 预测一个目标区块里的指定矿物。
     *
     * @throws SeedWorkerException 传输 / 协议 / 超时 / Worker 内部错误
     */
    public PredictionResult predict(long seed, String dimension, OreType oreType, ChunkPos target) {
        synchronized (requestLock) {
            requireUsable("预测");
            state = SeedWorkerState.BUSY;
            try {
                long startedAt = System.currentTimeMillis();
                long requestId = ++nextRequestId;
                WorkerResponse response = exchange(WorkerRequest.predict(requestId, process.token(),
                        seed, dimension, target.x(), target.z(), oreType.name()));
                lastPredictMillis = System.currentTimeMillis() - startedAt;
                predictCount++;
                WorkerPredictionDto prediction = response.prediction();
                if (prediction == null) {
                    throw new SeedWorkerException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                            "预测应答缺少预测结果");
                }
                PredictionResult result = SeedWorkerPredictionMapper.toResult(prediction, seed, dimension,
                        oreType, target);
                lastHostChunkSourceQueries = result.stats().hostChunkSourceQueries();
                if (lastHostChunkSourceQueries != 0) {
                    LOGGER.error("{}：宿主 ChunkMap 查询增量 {} ≠ 0，预测主链正在向真实世界取数据（架构回归）",
                            LOG_KEY, lastHostChunkSourceQueries);
                }
                return result;
            } catch (SeedWorkerException error) {
                if (breaksWorker(error.errorCode())) {
                    failAndRelease(error);
                }
                throw error;
            } finally {
                if (state == SeedWorkerState.BUSY) {
                    state = SeedWorkerState.READY;
                }
            }
        }
    }

    /**
     * 停止 Worker（停机阶梯：SHUTDOWN → destroy → destroyForcibly）。
     *
     * <p>口径第三十二节：绝不允许留下孤儿 Java 进程。</p>
     */
    public void stop() {
        synchronized (requestLock) {
            stopInternal();
        }
    }

    /** 重新进入世界 / 换服时重置失败计数（口径第三十三节：允许下一次点击自动重启一次）。 */
    public void resetRestartGuard() {
        consecutiveFailures = 0;
        lastFailureAtMillis = 0L;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 内部实现
    // ────────────────────────────────────────────────────────────────────────

    private void startInternal(String minecraftVersion, String modVersion) {
        state = SeedWorkerState.STARTING;
        startCount++;
        lastErrorCn = "";
        // 新进程 = 全新的会话空间：旧会话标识必须作废。否则 ensureSession 看到「种子/维度没变」
        // 会以为会话还在，而新起的 Worker 里根本没有会话，预测会被 Worker 以「无会话」拒绝
        // （阶段 232 生命周期装置实测：崩溃重启后沿用旧会话，恢复预测必然失败）。
        sessionId = null;
        sessionSeed = 0L;
        sessionDimension = null;
        // 旧连接也一并关掉：崩溃现场留下的 socket 已经没用了，但文件描述符还在
        closeSocket();
        handshake = null;
        lastCapabilities = java.util.List.of();
        LOGGER.info("{}：正在启动本地世界生成计算器（第 {} 次，版本 {}，模组 {}）",
                LOG_KEY, startCount, minecraftVersion, modVersion);
        SeedWorkerProcess created;
        try {
            created = SeedWorldgenWorkerLauncher.launch(minecraftVersion, modVersion);
        } catch (SeedWorkerException launchError) {
            recordFailure(launchError);
            throw launchError;
        }
        process = created;
        watchExit(created);
        try {
            completeHandshake(minecraftVersion, modVersion);
        } catch (SeedWorkerException error) {
            stopInternal();
            recordFailure(error);
            throw error;
        }
    }

    /** 等握手文件 → 连接端口 → HELLO 校验；成功后进入 READY。 */
    private void completeHandshake(String minecraftVersion, String modVersion) {
        SeedWorkerProcess current = process;
        if (current == null) {
            SeedWorkerException error = new SeedWorkerException(SeedWorkerException.ERROR_NOT_READY,
                    "本地世界生成计算器进程不存在");
            recordFailure(error);
            throw error;
        }
        long startedAt = System.currentTimeMillis();
        SeedWorkerProcess.Handshake info = current.awaitHandshake(STARTUP_TIMEOUT_MILLIS);
        if (info.protocolVersion() != SeedWorkerProtocol.VERSION) {
            stopInternal();
            SeedWorkerException error = new SeedWorkerException(SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH,
                    "协议版本不一致：客户端 " + SeedWorkerProtocol.VERSION + "，Worker " + info.protocolVersion());
            recordFailure(error);
            throw error;
        }
        if (!minecraftVersion.equals(info.minecraftVersion())) {
            stopInternal();
            SeedWorkerException error = new SeedWorkerException(SeedWorkerProtocol.ERROR_MINECRAFT_MISMATCH,
                    "Minecraft 版本不一致：客户端 " + minecraftVersion + "，Worker " + info.minecraftVersion()
                            + "（本阶段禁止跨版本共用世界生成）");
            recordFailure(error);
            throw error;
        }
        if (!info.modVersion().equals(modVersion) && !"unknown".equals(info.modVersion())) {
            stopInternal();
            SeedWorkerException error = new SeedWorkerException(SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH,
                    "模组版本不一致：客户端 " + modVersion + "，Worker " + info.modVersion());
            recordFailure(error);
            throw error;
        }
        connect(info.port());
        handshake = info;
        WorkerResponse hello = exchange(WorkerRequest.hello(++nextRequestId, current.token(),
                SeedWorkerProtocol.VERSION, minecraftVersion));
        if (hello.hello() == null) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE, "HELLO 应答缺少握手信息");
        }
        if (!hello.hello().hasCapability(SeedWorkerProtocol.CAPABILITY_PREDICT_OVERWORLD)
                && !hello.hello().hasCapability(SeedWorkerProtocol.CAPABILITY_PREDICT_NETHER)) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本地世界生成计算器没有声明任何维度预测能力；已声明：" + hello.hello().capabilities());
        }
        lastCapabilities = hello.hello().capabilities();
        lastStartupMillis = System.currentTimeMillis() - startedAt;
        consecutiveFailures = 0;
        state = SeedWorkerState.READY;
        LOGGER.info("{}：已就绪（PID {}，协议 {}，Minecraft {}，模组 {}，耗时 {} ms，宿主监听 {}）",
                LOG_KEY, info.workerPid(), info.protocolVersion(), info.minecraftVersion(), info.modVersion(),
                lastStartupMillis, info.listenerState());
    }

    private void connect(int port) {
        try {
            Socket created = new Socket();
            created.setTcpNoDelay(true);
            created.connect(new InetSocketAddress(java.net.InetAddress.getLoopbackAddress(), port),
                    (int) CONNECT_TIMEOUT_MILLIS);
            created.setSoTimeout(SeedWorkerProtocol.SOCKET_READ_TIMEOUT_MILLIS);
            socket = created;
            socketOut = created.getOutputStream();
            socketIn = created.getInputStream();
            readBuffer = new ByteArrayOutputStream(4096);
        } catch (IOException error) {
            stopInternal();
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_TRANSPORT,
                    "连接本地世界生成计算器失败（127.0.0.1:" + port + "）：" + error.getMessage(), error);
        }
    }

    /** 发一个请求并等它的应答（跳过过期应答；超时 / 断连一律 fail-closed）。 */
    private WorkerResponse exchange(WorkerRequest request) {
        SeedWorkerProcess current = process;
        if (current == null || socketOut == null || socketIn == null) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_WORKER_GONE, "本地世界生成计算器未连接");
        }
        long timeoutMillis = request.op().equals(SeedWorkerProtocol.OP_PREDICT)
                ? PREDICT_TIMEOUT_MILLIS : CONNECT_TIMEOUT_MILLIS;
        long deadline = System.currentTimeMillis() + timeoutMillis;
        try {
            socketOut.write((WorkerJson.encode(request.toJson()) + "\n").getBytes(StandardCharsets.UTF_8));
            socketOut.flush();
        } catch (IOException error) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_TRANSPORT,
                    "向本地世界生成计算器发送请求失败：" + error.getMessage(), error);
        }
        while (true) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_TIMEOUT,
                        "本地世界生成计算器在 " + timeoutMillis + " ms 内没有应答（" + request.op() + "）");
            }
            String line;
            try {
                socket.setSoTimeout((int) Math.min(remaining, Integer.MAX_VALUE));
                line = WorkerLineReader.readLine(socketIn, readBuffer, SeedWorkerProtocol.MAX_LINE_BYTES);
            } catch (SocketTimeoutException timeout) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_TIMEOUT,
                        "本地世界生成计算器应答超时（" + request.op() + "，" + timeoutMillis + " ms）");
            } catch (IOException error) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_WORKER_GONE,
                        "本地世界生成计算器连接中断：" + error.getMessage(), error);
            }
            if (line == null) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_WORKER_GONE,
                        "本地世界生成计算器已断开连接");
            }
            if (line.isBlank()) {
                continue;
            }
            WorkerResponse response;
            try {
                JsonObject object = WorkerJson.parseObject(line, SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE);
                response = WorkerResponse.fromJson(object);
            } catch (WorkerProtocolException malformed) {
                throw new SeedWorkerException(SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE,
                        "本地世界生成计算器返回了非法应答：" + malformed.getMessage());
            }
            if (response.id() != request.id()) {
                LOGGER.info("{}：丢弃过期应答（请求代号 {} ≠ 当前 {}）", LOG_KEY, response.id(), request.id());
                continue;
            }
            if (!response.ok()) {
                throw new SeedWorkerException(response.errorCode() == null
                        ? SeedWorkerProtocol.ERROR_INTERNAL : response.errorCode(),
                        response.errorMessage() == null ? "本地世界生成计算器报告失败" : response.errorMessage());
            }
            return response;
        }
    }

    /** 进程退出监听：把「意外崩溃」变成显式状态与日志（口径第三十三、六十五节）。 */
    private void watchExit(SeedWorkerProcess watched) {
        // 刻意不用 Process#onExit()：阶段 232 的实机取证里，Worker 被外部强杀时 JDK 明明已经
        // 回收了进程（isAlive() 立刻为 false），但那个回调一直没来 —— 结果是正式层停在旧状态，
        // 直到下一次预测才以传输错误收场。改成自建守护线程轮询 isAlive()：
        // 行为可预期、延迟上限 = 一个轮询间隔，且不依赖任何平台回调实现。
        Thread watchdog = new Thread(() -> {
            while (watched.alive()) {
                if (stopping) {
                    return; // 正常停机：收尾由 stopInternal 负责，不再报「意外退出」
                }
                try {
                    Thread.sleep(WATCHDOG_POLL_MILLIS);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (stopping) {
                return;
            }
            SeedWorkerProcess current = process;
            if (current != watched) {
                return;
            }
            state = SeedWorkerState.FAILED;
            // 界面文案走 userMessageCn()：退出码这类技术细节只进日志（口径第十四节）
            lastErrorCn = "本地世界生成计算器已意外停止，已自动回收；再次预测会自动重启。";
            LOGGER.warn("{}：进程意外退出（PID {}，退出码 {}）。{}",
                    LOG_KEY, watched.pid(), watched.exitCodeOrMinusOne(), watched.diagnostics());
        }, "yiyiaddon-seed-worker-watchdog");
        watchdog.setDaemon(true);
        watchdog.start();
    }

    /** 停机阶梯。 */
    private void stopInternal() {
        stopping = true;
        try {
            SeedWorkerProcess current = process;
            if (current == null) {
                state = SeedWorkerState.STOPPED;
                return;
            }
            state = SeedWorkerState.STOPPING;
            sessionId = null;
            sessionSeed = 0L;
            sessionDimension = null;
            if (socketOut != null && current.alive()) {
                try {
                    long id = ++nextRequestId;
                    socketOut.write((WorkerJson.encode(
                            WorkerRequest.shutdown(id, current.token()).toJson()) + "\n")
                            .getBytes(StandardCharsets.UTF_8));
                    socketOut.flush();
                    LOGGER.info("{}：已发送停机指令（PID {}）", LOG_KEY, current.pid());
                } catch (IOException error) {
                    LOGGER.info("{}：发送停机指令失败（进程可能已退出）：{}", LOG_KEY, error.getMessage());
                }
            }
            closeSocket();
            if (!current.awaitExit(SHUTDOWN_GRACE_MILLIS)) {
                LOGGER.warn("{}：{} ms 内未退出，改用 destroy()（PID {}）",
                        LOG_KEY, SHUTDOWN_GRACE_MILLIS, current.pid());
                current.destroy();
                if (!current.awaitExit(DESTROY_GRACE_MILLIS)) {
                    LOGGER.warn("{}：destroy() 后仍未退出，改用 destroyForcibly()（PID {}）",
                            LOG_KEY, current.pid());
                    current.destroyForcibly();
                    if (!current.awaitExit(DESTROY_GRACE_MILLIS)) {
                        LOGGER.error("{}：强制结束后进程仍然存活（PID {}），可能有系统级问题",
                                LOG_KEY, current.pid());
                    }
                }
            }
            LOGGER.info("{}：本地世界生成计算器已停止（PID {}，退出码 {}）",
                    LOG_KEY, current.pid(), current.exitCodeOrMinusOne());
            process = null;
            handshake = null;
            state = SeedWorkerState.STOPPED;
        } finally {
            stopping = false;
        }
    }

    /** 把 Worker 判为不可用并立即回收进程。 */
    private void failAndRelease(SeedWorkerException error) {
        stopInternal();
        state = SeedWorkerState.FAILED;
        recordFailure(error);
    }

    /**
     * 记一次失败：技术原因进日志，界面文案只取短中文。
     *
     * <p>{@link #lastErrorCn} 是<b>给界面看的</b>，因此存的是 {@link SeedWorkerException#userMessageCn()}
     * 的短句；异常自己的 message（日志路径 / 退出码 / 日志尾部，可能含 Worker 侧异常栈）
     * 一律只写日志，绝不进界面（口径第十四节）。</p>
     */
    private void recordFailure(SeedWorkerException error) {
        consecutiveFailures++;
        lastFailureAtMillis = System.currentTimeMillis();
        lastErrorCn = error == null ? "" : error.userMessageCn();
        LOGGER.warn("{}：本地世界生成计算器失败（界面文案：{}）技术原因：{}", LOG_KEY, lastErrorCn,
                error == null ? "（未提供）" : error.getMessage());
        state = SeedWorkerState.FAILED;
    }

    private void requireUsable(String action) {
        if (!state.usable()) {
            throw new SeedWorkerException(SeedWorkerException.ERROR_NOT_READY,
                    "本地世界生成计算器当前不可用（状态：" + state.displayNameCn() + "），无法" + action);
        }
    }

    /**
     * 该维度本地计算器能不能算（fail-closed）。
     *
     * <p>能力来自 Worker 的 HELLO（它按「宿主真的具备哪一层世界」逐条声明）。
     * 客户端在这里提前拒绝，而不是把请求发过去等 Worker 报错 —— 后者会把失败原因
     * 混进预测链路，让「环境不支持」看起来像「预测失败」。</p>
     */
    private void requireDimensionCapability(String dimension) {
        SeedDimensionProfile profile = SeedDimensionProfile.of(dimension);
        if (profile == null) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本版本不支持该维度：" + dimension);
        }
        String required = switch (profile) {
            case OVERWORLD -> SeedWorkerProtocol.CAPABILITY_PREDICT_OVERWORLD;
            case NETHER -> SeedWorkerProtocol.CAPABILITY_PREDICT_NETHER;
        };
        if (!lastCapabilities.contains(required)) {
            throw new SeedWorkerException(SeedWorkerProtocol.ERROR_UNSUPPORTED,
                    "本地世界生成计算器不支持" + profile.displayNameCn() + "（能力："
                            + (lastCapabilities.isEmpty() ? "无" : String.join(" / ", lastCapabilities)) + "）");
        }
    }

    /** 最近一次握手的能力清单（诊断用）。 */
    public java.util.List<String> capabilities() {
        return lastCapabilities;
    }

    private void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
            // 关闭失败没有处置价值
        }
        socket = null;
        socketOut = null;
        socketIn = null;
        readBuffer = null;
    }

    /** 哪些错误码意味着「Worker 本身坏了」，需要回收进程重建。 */
    private static boolean breaksWorker(String errorCode) {
        return SeedWorkerProtocol.ERROR_TIMEOUT.equals(errorCode)
                || SeedWorkerProtocol.ERROR_WORKER_GONE.equals(errorCode)
                || SeedWorkerProtocol.ERROR_TRANSPORT.equals(errorCode)
                || SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE.equals(errorCode)
                || SeedWorkerProtocol.ERROR_BAD_TOKEN.equals(errorCode)
                || SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH.equals(errorCode)
                || SeedWorkerProtocol.ERROR_MINECRAFT_MISMATCH.equals(errorCode);
    }
}
