package com.yiyiaddon.seed.worker.client;

import com.yiyiaddon.seed.worker.protocol.SeedWorkerProtocol;

/**
 * 种子挖矿 · Worker 客户端异常。
 *
 * <p>只表示「这次请求没有拿到合法结果」，<b>不</b>表示「这个区块没有矿」。
 * 调用方必须把它转成失败结果（{@code success=false}），绝不允许降级成空矿物表
 * （阶段 232 口径第六十六、六十七节）。</p>
 */
public final class SeedWorkerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码（{@link SeedWorkerProtocol} 的 {@code ERROR_*} 常量，或客户端自定义码）。 */
    private final String errorCode;

    /** 客户端自定义：无法启动 / 找不到可用的 Java。 */
    public static final String ERROR_LAUNCH_FAILED = "LAUNCH_FAILED";

    /** 客户端自定义：启动超时（进程没能在限定时间内交接）。 */
    public static final String ERROR_STARTUP_TIMEOUT = "STARTUP_TIMEOUT";

    /** 客户端自定义：Worker 不可用（未启动 / 已失败）。 */
    public static final String ERROR_NOT_READY = "NOT_READY";

    public SeedWorkerException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SeedWorkerException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /** 错误码。 */
    public String errorCode() {
        return errorCode;
    }

    /**
     * <b>给普通用户看</b>的短中文说明（阶段 232-P 口径第十四节）。
     *
     * <p>异常自己的 {@code message} 是给开发者排障用的：里面有 Worker 日志路径、退出码、
     * 日志尾部若干行 —— 那几行里完全可能是 Worker 侧的 Java 异常栈。正式界面的「失败原因」
     * 只允许显示本方法给出的短句，技术细节一律留在日志里。</p>
     */
    public String userMessageCn() {
        return switch (errorCode == null ? "" : errorCode) {
            case ERROR_LAUNCH_FAILED ->
                    "本地世界生成计算器无法启动（可能是本机内存不足或被安全软件拦截），已停止；"
                            + "可稍后重试，其它功能不受影响。";
            case ERROR_STARTUP_TIMEOUT ->
                    "本地世界生成计算器启动超时（本机资源紧张或初始化失败），已停止；"
                            + "重新进入世界后可再试。";
            case ERROR_NOT_READY -> "本地世界生成计算器暂时不可用，请稍后重试或重新进入世界。";
            case SeedWorkerProtocol.ERROR_TIMEOUT ->
                    "本地世界生成计算器响应超时，已自动停止；再次预测会自动重启。";
            case SeedWorkerProtocol.ERROR_WORKER_GONE, SeedWorkerProtocol.ERROR_TRANSPORT ->
                    "本地世界生成计算器进程已中断，已自动停止；再次预测会自动重启。";
            case SeedWorkerProtocol.ERROR_MALFORMED_RESPONSE ->
                    "本地世界生成计算器返回了无法识别的结果（版本可能不一致），已停止。";
            case SeedWorkerProtocol.ERROR_BAD_TOKEN ->
                    "本地世界生成计算器拒绝了本次连接（安全校验未通过），已停止。";
            case SeedWorkerProtocol.ERROR_PROTOCOL_MISMATCH ->
                    "本地世界生成计算器与当前版本不一致（协议不匹配），已停止。";
            case SeedWorkerProtocol.ERROR_MINECRAFT_MISMATCH ->
                    "本地世界生成计算器与当前 Minecraft 版本不一致，已停止。";
            default -> "本地世界生成计算器发生错误，已停止；详情见日志，可稍后重试。";
        };
    }
}
