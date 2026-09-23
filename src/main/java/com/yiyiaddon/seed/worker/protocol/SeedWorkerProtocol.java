package com.yiyiaddon.seed.worker.protocol;

/**
 * 种子挖矿 · 本地世界生成计算器（Seed Worldgen Worker）· <b>IPC 协议常量</b>。
 *
 * <p><b>这一层是什么</b>（阶段 232 口径第十七、十九、二十节）：客户端进程与本地隔离 Worker 进程之间
 * 的传输层协议。它<b>只</b>负责搬运「请求 → 结果」，不含任何业务语义实现，也不允许出现
 * {@code ObjectOutputStream} / {@code Serializable}（口径第二十节：版本兼容差、脆弱、有安全风险）。</p>
 *
 * <p><b>传输形态</b>：127.0.0.1 回环 TCP + <b>一行一条 JSON</b>（{@code \n} 结尾）。
 * 不用 stdout 当主协议 —— Minecraft / Fabric 日志会把主通道冲掉（口径第十七节）。
 * 每行长度硬上限见 {@link #MAX_LINE_BYTES}，超限即拒（长度受限是协议要求的一部分）。</p>
 *
 * <p><b>端到端 requestId</b>（口径第二十一节）：每个请求带一个 {@code id}，响应必须原样带回；
 * 客户端只接受「仍是当前任务」的响应，因此「Seed 改了 / 换了维度 / 退出服务器 / 关了功能」
 * 之后到达的旧响应会被整包丢弃，不会回写状态。</p>
 *
 * <p><b>版本纪律</b>（口径第十节）：协议版本与 Minecraft 版本都必须在 {@code HELLO} 里对上，
 * 对不上直接拒绝预测，绝不尝试跨版本共用 Vanilla worldgen。</p>
 */
public final class SeedWorkerProtocol {

    /**
     * 协议版本（业务协议）；与 Minecraft 版本无关，可跨版本共用。
     *
     * <p><b>236 起为 2</b>：预测请求从「只认主世界钻石」扩成「任意受支持 (维度, 矿物)」，
     * 操作码 {@code PREDICT_DIAMOND} 随之改名为 {@code PREDICT}。
     * 客户端与 Worker 来自同一个 jar，因此版本不一致只可能来自「旧进程没退干净」，
     * 那种情况必须拒绝（见客户端握手校验）。</p>
     */
    public static final int VERSION = 2;

    /** 单行 JSON 的字节上限（1 MiB）。超限即视为协议错误并断开连接。 */
    public static final int MAX_LINE_BYTES = 1 << 20;

    /** 单次 socket 读的超时（毫秒）：只有「读一半没下文」才会触发，正常响应不受影响。 */
    public static final int SOCKET_READ_TIMEOUT_MILLIS = 600_000;

    // ── 操作码（口径第十九节） ──

    /** 握手：校验协议版本 / Minecraft 版本 / 令牌，返回 Worker 自述信息。 */
    public static final String OP_HELLO = "HELLO";

    /** 心跳（诊断用；不参与业务）。 */
    public static final String OP_PING = "PING";

    /** 打开会话：绑定「种子 + 维度」，旧的其它种子会话一律失效。 */
    public static final String OP_OPEN_SESSION = "OPEN_SESSION";

    /** 预测一个目标区块里的指定矿物（236 起通用；请求里带维度与矿物枚举名）。 */
    public static final String OP_PREDICT = "PREDICT";

    /** 关闭会话并释放离线世界缓存。 */
    public static final String OP_CLOSE_SESSION = "CLOSE_SESSION";

    /** 优雅停机：Worker 释放宿主并退出进程。 */
    public static final String OP_SHUTDOWN = "SHUTDOWN";

    // ── 错误码（口径第六十六节：全部 fail-closed，绝不返回空矿假装成功） ──

    /** 请求行不是合法 JSON 或字段类型不对。 */
    public static final String ERROR_MALFORMED_REQUEST = "MALFORMED_REQUEST";
    /** 响应行不是合法 JSON 或字段类型不对（客户端侧判定）。 */
    public static final String ERROR_MALFORMED_RESPONSE = "MALFORMED_RESPONSE";
    /** 协议版本不匹配。 */
    public static final String ERROR_PROTOCOL_MISMATCH = "PROTOCOL_MISMATCH";
    /** Minecraft 版本不匹配。 */
    public static final String ERROR_MINECRAFT_MISMATCH = "MINECRAFT_MISMATCH";
    /** 令牌不对（本机其它进程不得向 Worker 下命令）。 */
    public static final String ERROR_BAD_TOKEN = "BAD_TOKEN";
    /** 未知操作码。 */
    public static final String ERROR_UNKNOWN_OP = "UNKNOWN_OP";
    /** 会话没打开 / 会话与请求不一致。 */
    public static final String ERROR_NO_SESSION = "NO_SESSION";
    /** 不支持的维度 / 矿物。 */
    public static final String ERROR_UNSUPPORTED = "UNSUPPORTED";
    /** Worker 内部异常（世界生成宿主未就绪、预测抛错等）。 */
    public static final String ERROR_INTERNAL = "INTERNAL";
    /** 预测超时（客户端侧判定：Worker 长时间无响应）。 */
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    /** Worker 进程已退出 / 连接断开。 */
    public static final String ERROR_WORKER_GONE = "WORKER_GONE";
    /** 客户端与 Worker 之间的传输故障。 */
    public static final String ERROR_TRANSPORT = "TRANSPORT";

    /**
     * 能力声明（HELLO 返回）。
     *
     * <p>按<b>维度</b>声明：Worker 只有在宿主真的具备那一层世界时才会声明对应能力，
     * 因此客户端可以据此提前判断「这个本地计算器能不能算下界」，而不是等预测时报错。</p>
     */
    public static final String CAPABILITY_PREDICT_OVERWORLD = "predict_overworld";
    public static final String CAPABILITY_PREDICT_NETHER = "predict_nether";

    private SeedWorkerProtocol() {
    }
}
