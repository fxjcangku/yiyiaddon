package com.yiyiaddon.platform.world;

/** 技术状态的中文显示适配；不改写物品身份、资源路径、序列化值或玩家输入。 */
public final class PlayerText {
    private PlayerText() { }

    /** 仅用于明确的状态字段；未知技术值保留为附加 ID，不能冒充主文案。 */
    public static String status(String value) {
        if (value == null || value.isBlank()) return "未确认";
        return switch (value) {
            case "UNKNOWN" -> "未确认";
            case "READY" -> "已就绪";
            case "IDLE" -> "空闲";
            case "NOT_CHECKED" -> "未检测";
            case "CHECKING" -> "检测中";
            case "DOWNLOADING" -> "下载中";
            case "LOADING" -> "加载中";
            case "PARSING" -> "解析中";
            case "CANDIDATE" -> "候选";
            case "DOCUMENTED" -> "文档记载";
            case "VERIFIED", "CONFIRMED" -> "已确认";
            case "FAILED", "ERROR", "FAILURE" -> "失败";
            case "SUCCESS", "COMPLETED", "DONE" -> "完成";
            case "NOT_STARDEW" -> "未发现支持的玩法资源";
            default -> value.matches("[A-Z][A-Z0-9_]+") ? "未确认（状态 ID：" + value + "）" : value;
        };
    }

    /** 网络或系统异常给出中文主因，原始技术细节仅作附加信息。 */
    public static String failure(Throwable error) {
        if (error instanceof java.net.http.HttpTimeoutException || error instanceof java.net.SocketTimeoutException) return "连接超时，请稍后重试";
        if (error instanceof java.net.ConnectException) return "无法连接服务，请检查网络";
        String detail = error.getMessage();
        return "操作未完成" + (detail == null || detail.isBlank() ? "" : "（技术信息：" + detail + "）");
    }
}
