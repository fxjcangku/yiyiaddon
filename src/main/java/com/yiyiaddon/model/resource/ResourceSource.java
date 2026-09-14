package com.yiyiaddon.model.resource;

/**
 * 资源来源。
 *
 * <p>必须与阶段一起判断：命中本地 ZIP 缓存与「没有缓存、临时用客户端已加载资源」是两种
 * 完全不同的结果，后者不能对外宣称资源已落盘。</p>
 */
public enum ResourceSource {

    /** 尚未确定来源 */
    NONE("无"),

    /** 磁盘上的 {@code <host>_<port>.zip} 缓存 */
    ZIP("ZIP缓存"),

    /** 没有本地缓存，直接使用客户端当前已加载的服务器资源 */
    LOADED("当前服务器已加载资源");

    private final String label;

    ResourceSource(String label) {
        this.label = label;
    }

    /** 中文文案 */
    public String label() {
        return label;
    }
}
