package com.yiyiaddon.service.resourcepack;

import com.yiyiaddon.model.resource.ResourceAnalysisResult;
import com.yiyiaddon.model.resource.ResourceCategory;
import com.yiyiaddon.model.resource.ResourceParseState;
import com.yiyiaddon.model.resource.ResourceScanResult;
import com.yiyiaddon.model.resource.ResourceSource;
import com.yiyiaddon.platform.resource.ResourceEnumerator;
import com.yiyiaddon.platform.resource.ResourceFingerprint;
import com.yiyiaddon.repository.resource.ResourceAnalysisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资源内容探针默认实现：回答「当前已加载资源里有什么」。
 *
 * <p><b>分析流程：</b></p>
 * <ol>
 *     <li>枚举：{@link ResourceEnumerator} 取当前生效的全部资源（原版包 + 模组包 + 本地包 + 服务器包）；</li>
 *     <li>过滤：{@link ResourceClassifier} 挑出目标命名空间，排除原版与已加载模组；</li>
 *     <li>指纹：{@link ResourceFingerprint} 按内容算指纹（路径全进哈希，JSON 内容进哈希）；</li>
 *     <li>查缓存：{@link ResourceAnalysisCache} 命中则直接复用上次的分类结果；</li>
 *     <li>分类：未命中时才逐条按路径首段归入分类，并写回缓存。</li>
 * </ol>
 *
 * <p><b>失败与空结果严格区分：</b>资源管理器不可用、枚举抛错、或「命名空间里明明有目标命名空间
 * 却枚举不到任何资源」都判为解析失败并给出原因；只有确实没有任何目标资源才算无内容。否则接口
 * 一旦失效，会被误报成「服务器没有自定义资源」，把真正的故障藏起来。</p>
 *
 * <p><b>接口契约：</b>只读，不触发资源重载、不修改游戏状态；{@link #loadedContentIds()} 会被生命周期
 * 服务在等待阶段逐刻调用，因此结果带一秒短时缓存，避免每刻全量枚举资源树。</p>
 */
public final class ResourceIndexProbe implements ResourceContentProbe {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/resource-probe");

    /** loadedContentIds 短时缓存有效期：生命周期服务等待阶段每刻都会问一次 */
    private static final long IDS_CACHE_MILLIS = 1000L;

    private final ResourceClassifier classifier = new ResourceClassifier();

    /** 最近一次完整分析结果 */
    private volatile ResourceAnalysisResult lastResult;

    /** 最近一次分析算出的指纹，供生命周期服务读取 */
    private volatile String lastFingerprint;

    /** 与最近一次指纹配套的分类结果，保证指纹与计数同源 */
    private volatile ResourceScanResult lastScan = ResourceScanResult.empty();

    private volatile Set<String> idsCache = Set.of();
    private volatile long idsCacheAt;

    @Override
    public Set<String> loadedContentIds() {
        long now = System.currentTimeMillis();
        Set<String> cached = idsCache;
        if (now - idsCacheAt < IDS_CACHE_MILLIS) return cached;

        Set<String> fresh = collectContentIds(ResourceEnumerator.list());
        idsCache = fresh;
        idsCacheAt = now;
        return fresh;
    }

    @Override
    public ResourceScanResult analyze() {
        return analyzeDetailed().toScanResult();
    }

    /**
     * 完整分析，返回含来源、指纹、未知资源与命名空间归属的结果。
     *
     * <p>交给生命周期服务的是精简的 {@link #analyze()}，指令与界面读本方法。</p>
     */
    public ResourceAnalysisResult analyzeDetailed() {
        long now = System.currentTimeMillis();
        ResourceEnumerator.Snapshot snapshot = ResourceEnumerator.list();

        if (!snapshot.ok()) {
            ResourceAnalysisResult failure = ResourceAnalysisResult.failed(
                null, snapshot.failureReason(), List.of(), now);
            remember(failure, ResourceScanResult.empty());
            LOGGER.warn("资源解析失败：{}", snapshot.failureReason());
            return failure;
        }

        Set<String> targets = classifier.targetsOf(snapshot.namespaces());
        Set<String> ignored = classifier.ignoredOf(snapshot.namespaces());
        // 同一次枚举顺带刷新内容 id 短时缓存：指纹与 id 必须来自同一次枚举，否则会出现
        // 「指纹描述新资源、内容 id 还是旧资源」的错配
        idsCache = collectContentIds(snapshot);
        idsCacheAt = now;
        List<ResourceEnumerator.Entry> targetEntries = filterTargets(snapshot.entries());
        ResourceParseState state = ResourceParseState.SUCCESS;

        // 枚举结果与命名空间列表矛盾：命名空间里有目标命名空间，却一条资源都没枚举到。
        // 这属于接口失效，绝不当成「服务器没有资源」。
        if (targetEntries.isEmpty() && !targets.isEmpty()) {
            String reason = "资源枚举结果为空，但命名空间中存在目标命名空间：" + String.join("、", targets);
            ResourceAnalysisResult failure = ResourceAnalysisResult.failed(
                null, reason, new ArrayList<>(ignored), now);
            remember(failure, ResourceScanResult.empty());
            LOGGER.warn("资源解析失败：{}", reason);
            return failure;
        }

        ResourceFingerprint.Result fingerprint = ResourceFingerprint.compute(targetEntries);
        String fingerprintValue = fingerprint.value();

        ResourceAnalysisCache.Cached cached = ResourceAnalysisCache.load(fingerprintValue);
        Map<String, Integer> counts;
        int unknown;
        boolean fromCache;
        if (cached != null) {
            counts = cached.counts();
            unknown = cached.unknown();
            fromCache = true;
        } else {
            counts = classify(targetEntries);
            unknown = counts.getOrDefault(ResourceCategory.OTHER.label(), 0);
            fromCache = false;
        }

        int total = 0;
        for (int value : counts.values()) total += value;
        if (total == 0) {
            state = ResourceParseState.EMPTY;
        }

        // 分类顺序由 ResourceAnalysisResult 统一保证，此处不再自行重排
        ResourceAnalysisResult result = new ResourceAnalysisResult(
            ResourceSource.LOADED,
            fingerprintValue,
            total,
            counts,
            unknown,
            fingerprint.full(),
            new ArrayList<>(targets),
            new ArrayList<>(ignored),
            state,
            "",
            fromCache,
            now
        );

        if (!fromCache && state == ResourceParseState.SUCCESS) {
            ResourceAnalysisCache.save(result);
        }

        remember(result, result.toScanResult());
        logSummary(result);
        return result;
    }

    @Override
    public String contentFingerprint() {
        return lastFingerprint;
    }

    @Override
    public List<String> categoryNames() {
        List<String> names = new ArrayList<>();
        for (ResourceCategory category : ResourceCategory.values()) names.add(category.label());
        return names;
    }

    /** 最近一次完整分析结果；尚未分析过返回 {@code null} */
    public ResourceAnalysisResult lastResult() {
        return lastResult;
    }

    /** 分类规则实例，供界面与指令展示「哪些命名空间被视为目标」 */
    public ResourceClassifier classifier() {
        return classifier;
    }

    // ── 内部 ──

    /** 目标命名空间下的资源，保持枚举时的排序 */
    private List<ResourceEnumerator.Entry> filterTargets(List<ResourceEnumerator.Entry> entries) {
        List<ResourceEnumerator.Entry> result = new ArrayList<>();
        for (ResourceEnumerator.Entry entry : entries) {
            if (classifier.isTarget(entry.namespace())) result.add(entry);
        }
        return result;
    }

    /** 逐条按路径首段归类计数 */
    private Map<String, Integer> classify(List<ResourceEnumerator.Entry> entries) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (ResourceCategory category : ResourceCategory.values()) counts.put(category.label(), 0);

        for (ResourceEnumerator.Entry entry : entries) {
            ResourceCategory category = ResourceClassifier.categoryOf(entry.path());
            counts.merge(category.label(), 1, Integer::sum);
        }
        return counts;
    }

    /** 内容 id：目标命名空间下的方块状态与模型（定义类），用于就绪判断与跨服串档比对 */
    private Set<String> collectContentIds(ResourceEnumerator.Snapshot snapshot) {
        Set<String> ids = new LinkedHashSet<>();
        if (!snapshot.ok()) return ids;
        for (ResourceEnumerator.Entry entry : snapshot.entries()) {
            if (!classifier.isTarget(entry.namespace())) continue;
            ResourceCategory category = ResourceClassifier.categoryOf(entry.path());
            if (category == ResourceCategory.BLOCKSTATES || category == ResourceCategory.MODELS
                || category == ResourceCategory.LANG) {
                ids.add(entry.id());
            }
        }
        return ids;
    }

    private void remember(ResourceAnalysisResult result, ResourceScanResult scan) {
        lastResult = result;
        lastFingerprint = result.fingerprint();
        lastScan = scan;
    }

    /** 最近一次交给生命周期服务的分类结果 */
    public ResourceScanResult lastScan() {
        return lastScan;
    }

    private void logSummary(ResourceAnalysisResult result) {
        if (result.state() == ResourceParseState.FAILED) {
            LOGGER.warn("资源解析失败：{}", result.reason());
            return;
        }
        LOGGER.info("资源解析{}：指纹 {}，目标命名空间 [{}]，{}，未知 {}，来源 {}",
            result.fromCache() ? "（命中缓存）" : "（完整分析）",
            result.fingerprint() == null ? "无" : result.fingerprint(),
            String.join("、", result.targetNamespaces()),
            result.countsLine(),
            result.unknownCount(),
            result.source().label());
    }
}
