package com.yiyiaddon.model.resource;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 资源分析结果：探针对「当前已加载资源里有什么」的完整回答。
 *
 * <p>与 {@link ResourceScanResult} 的分工：{@code ResourceScanResult} 是给生命周期服务的精简输入
 * （只有分类计数与资源 id），本记录是给指令、界面与后续业务模块读取的完整结果，包含来源、指纹、
 * 未知资源、命名空间归属与解析状态。</p>
 *
 * @param source            资源来源（ZIP 缓存 / 当前已加载资源）
 * @param fingerprint       资源指纹（按内容计算，12 位十六进制）
 * @param total             资源数量合计（各分类计数之和）
 * @param counts            分类中文名 → 数量；固定按 {@link ResourceCategory} 顺序
 * @param unknownCount      未归入任何已知分类的资源数量（归在「其它」分类中的条目）
 * @param contentFull       指纹是否覆盖了全部定义类资源的内容；false 表示有文件因超限或读取失败被跳过
 * @param targetNamespaces  被当作服务器自定义内容的命名空间
 * @param ignoredNamespaces 被排除的命名空间（原版与已加载模组）
 * @param state             解析状态
 * @param reason            失败或降级原因；正常时为空串
 * @param fromCache         本次结果是否直接读取分析缓存
 * @param parsedAtMillis    本次结果产生时间
 */
public record ResourceAnalysisResult(
    ResourceSource source,
    String fingerprint,
    int total,
    Map<String, Integer> counts,
    int unknownCount,
    boolean contentFull,
    List<String> targetNamespaces,
    List<String> ignoredNamespaces,
    ResourceParseState state,
    String reason,
    boolean fromCache,
    long parsedAtMillis
) {

    public ResourceAnalysisResult {
        counts = orderCounts(counts);
        targetNamespaces = targetNamespaces == null ? List.of() : List.copyOf(targetNamespaces);
        ignoredNamespaces = ignoredNamespaces == null ? List.of() : List.copyOf(ignoredNamespaces);
        reason = reason == null ? "" : reason;
    }

    /**
     * 按 {@link ResourceCategory} 声明顺序重排分类计数，未声明的分类保持原顺序追加到末尾。
     *
     * <p>必须自己重排：记录字段若交给 {@code Map.copyOf}，迭代顺序由哈希盐决定，展示顺序会在
     * 每次启动之间抖动。实测已出现「其它 / 语言 / 方块状态 / 模型 / 贴图 / 字体」这种非声明顺序。</p>
     */
    private static Map<String, Integer> orderCounts(Map<String, Integer> source) {
        if (source == null || source.isEmpty()) return Map.of();
        Map<String, Integer> ordered = new LinkedHashMap<>();
        for (ResourceCategory category : ResourceCategory.values()) {
            Integer value = source.get(category.label());
            if (value != null) ordered.put(category.label(), value);
        }
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            ordered.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(ordered);
    }

    /** 解析失败时的结果 */
    public static ResourceAnalysisResult failed(String fingerprint, String reason,
                                                List<String> ignoredNamespaces, long now) {
        return new ResourceAnalysisResult(ResourceSource.NONE, fingerprint, 0, Map.of(), 0, false,
            List.of(), ignoredNamespaces, ResourceParseState.FAILED, reason, false, now);
    }

    /** 是否有可识别内容 */
    public boolean hasContent() {
        return total > 0;
    }

    /** 分类计数一行文本，例如 {@code 方块状态 12 / 模型 30 / 贴图 8} */
    public String countsLine() {
        if (counts.isEmpty()) return "无";
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (builder.length() > 0) builder.append(" / ");
            builder.append(entry.getKey()).append(' ').append(entry.getValue());
        }
        return builder.toString();
    }

    /** 供展示用的分类计数（跳过 0 项），保留分类顺序 */
    public Map<String, Integer> nonZeroCounts() {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() != null && entry.getValue() > 0) result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /** 结果转 {@link ResourceScanResult}：计数用分类结果，id 用目标命名空间（供跨服串档比对） */
    public ResourceScanResult toScanResult() {
        return new ResourceScanResult(counts, new LinkedHashSet<>(targetNamespaces));
    }
}
