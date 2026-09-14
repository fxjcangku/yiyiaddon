package com.yiyiaddon.model.resource;

import java.util.Map;
import java.util.Set;

/**
 * 资源解析结果。
 *
 * <p>由内容探针（{@link com.yiyiaddon.service.resourcepack.ResourceContentProbe}）产出：
 * 按逻辑分类聚合去重后的数量表，以及参与解析的资源 id 集合（供跨服串档比对使用）。</p>
 *
 * @param counts 分类名 → 去重后的逻辑对象数量；分类名由探针自行定义
 * @param ids    参与本次解析的资源 id 集合
 */
public record ResourceScanResult(Map<String, Integer> counts, Set<String> ids) {

    /** 空结果（未注册探针时使用） */
    public static ResourceScanResult empty() {
        return new ResourceScanResult(Map.of(), Set.of());
    }

    /** 全部分类数量之和 */
    public int total() {
        int sum = 0;
        for (int value : counts.values()) sum += value;
        return sum;
    }

    /** 是否没有任何可识别内容 */
    public boolean isEmpty() {
        return total() == 0;
    }
}
