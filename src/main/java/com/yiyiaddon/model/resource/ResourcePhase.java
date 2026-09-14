package com.yiyiaddon.model.resource;

/**
 * 服务器资源生命周期阶段。
 *
 * <p>顺序即界面展示顺序。进服只识别服务器地址、不自动下载：真正的缓存检查 / 下载 / 解析
 * 必须由玩家主动触发，避免「每进一个普通服务器就自动下载一份资源包」。</p>
 */
public enum ResourcePhase {

    /** 未进入世界 / 已断线 */
    IDLE("空闲"),

    /** 已识别服务器，但玩家尚未触发检测，不下载任何东西 */
    NOT_CHECKED("未检测"),

    /** 查找本地缓存 / 等待资源包推送 */
    CHECKING("检查中"),

    /** 正在下载资源包 */
    DOWNLOADING("下载中"),

    /** 正在等待客户端把当前服务器资源应用到资源管理器 */
    LOADING("加载中"),

    /** 正在解析当前已加载资源 */
    PARSING("解析中"),

    /** 资源就绪，可安全使用 */
    READY("就绪"),

    /** 资源已加载，但里面没有目标内容 */
    NO_CONTENT("未发现目标资源"),

    /** 失败 */
    FAILED("失败");

    private final String label;

    ResourcePhase(String label) {
        this.label = label;
    }

    /** 中文文案 */
    public String label() {
        return label;
    }
}
