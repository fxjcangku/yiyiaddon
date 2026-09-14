package com.yiyiaddon.model.resource;

/**
 * 方块语义解析结果。
 *
 * <p>把一个 {@code BlockState} 通过当前生效资源包的 blockstates 映射解析为「语义身份」：
 * 模型路径、自定义身份、中文名、来源、确定性，以及无法解析时的可读原因。语义身份是
 * 世界方块数据与服务器自定义内容之间的桥梁，供标识识别与后续世界级自动化复用。</p>
 *
 * @param model     命中的模型路径（多个候选时用 {@code " | "} 连接）
 * @param identity  派生出的自定义身份，如 {@code customcrops:tomato_stage_3}；无法派生为 {@code null}
 * @param name      资源包语言文件中的中文名；查不到为 {@code null}
 * @param source    解析来源文案
 * @param certainty 确定性文案：已确认 / 候选 / 未知
 * @param reason    无法确定时的可读原因；确定时为 {@code null}
 */
public record BlockSemantic(
    String model,
    String identity,
    String name,
    String source,
    String certainty,
    String reason
) {

    /** 完全未知的结果 */
    public static BlockSemantic unknown() {
        return new BlockSemantic(null, null, null, "未知", "未知", null);
    }

    /** 是否已确认（不是候选、不是未知） */
    public boolean isConfirmed() {
        return "已确认".equals(certainty);
    }

    /** 是否为多候选（不冒充确定结果） */
    public boolean isCandidate() {
        return "候选".equals(certainty);
    }
}
