package com.yiyiaddon.seed.worker.protocol;

/**
 * 种子挖矿 · Worker IPC 协议异常。
 *
 * <p>只表示「这一行不具备我们约定的格式 / 语义」，因此调用侧一律按
 * {@link SeedWorkerProtocol#ERROR_MALFORMED_REQUEST} /
 * {@link SeedWorkerProtocol#ERROR_MALFORMED_RESPONSE} 收缩处理（fail-closed），
 * <b>绝不</b>降级成「本次预测没有矿」。</p>
 */
public final class WorkerProtocolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 协议错误码（{@link SeedWorkerProtocol} 里的常量之一）。 */
    private final String errorCode;

    public WorkerProtocolException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /** 协议错误码。 */
    public String errorCode() {
        return errorCode;
    }
}
