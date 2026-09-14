package com.yiyiaddon.model.resource;

/**
 * 资源解析状态。
 *
 * <p>与生命周期阶段分开：阶段描述「链路走到哪一步」，解析状态描述「这一次解析本身的结果」。
 * 解析失败必须与「解析成功但没有目标内容」区分开，否则会把接口失效误报成服务器没有资源。</p>
 */
public enum ResourceParseState {

    /** 解析成功，且找到目标资源 */
    SUCCESS("解析成功"),

    /** 解析成功，但目标命名空间下没有任何资源 */
    EMPTY("无目标资源"),

    /** 解析本身失败：资源管理器不可用、枚举异常、枚举结果与命名空间列表矛盾 */
    FAILED("解析失败");

    private final String label;

    ResourceParseState(String label) {
        this.label = label;
    }

    /** 中文文案 */
    public String label() {
        return label;
    }
}
