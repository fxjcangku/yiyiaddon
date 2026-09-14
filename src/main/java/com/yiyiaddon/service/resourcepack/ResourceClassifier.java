package com.yiyiaddon.service.resourcepack;

import com.yiyiaddon.model.resource.ResourceCategory;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * 资源命名空间分类规则：判断哪些命名空间属于「服务器自定义内容」。
 *
 * <p><b>为什么需要它：</b>客户端资源管理器里同时躺着原版资源、所有已加载模组自带的资源，
 * 以及服务器资源包与本地资源包的资源。只有第三类才是需要识别与分析的目标；把原版的
 * {@code minecraft} 或模组自己的命名空间算进来，会让每个服务器都「有几百个目标资源」，
 * 分析结果彻底失去意义。</p>
 *
 * <p><b>判定口径（可审计）：</b>排除原版与平台命名空间，再排除所有已加载模组的模组 id
 * （含把连字符替换成下划线的形式）。模组用与自身 id 无关的命名空间（例如 {@code fabric}）
 * 时由内置基线兜住。被排除的命名空间会随分析结果一并输出，误判可以直接看出来，不需要猜。</p>
 *
 * <p>本类只做规则判定，不接触任何 Minecraft 接口，也不读文件。</p>
 */
public final class ResourceClassifier {

    /** 内置排除项：原版与平台命名空间 */
    private static final Set<String> BASELINE = Set.of(
        "minecraft",
        "realms",
        "fabric",
        "yiyiaddon",
        "baritone"
    );

    private final Set<String> excluded;
    private final Set<String> modNamespaces;

    public ResourceClassifier() {
        this.modNamespaces = collectModNamespaces();
        Set<String> all = new LinkedHashSet<>(BASELINE);
        all.addAll(modNamespaces);
        this.excluded = Collections.unmodifiableSet(all);
    }

    /** 该命名空间是否属于需要分析的目标内容 */
    public boolean isTarget(String namespace) {
        if (namespace == null || namespace.isBlank()) return false;
        return !excluded.contains(namespace.toLowerCase(Locale.ROOT));
    }

    /** 被排除的命名空间（原版 + 平台 + 已加载模组） */
    public Set<String> excludedNamespaces() {
        return excluded;
    }

    /** 来自已加载模组的排除项 */
    public Set<String> modNamespaces() {
        return modNamespaces;
    }

    /** 从命名空间集合里挑出目标命名空间，按字母序 */
    public Set<String> targetsOf(Iterable<String> namespaces) {
        Set<String> targets = new TreeSet<>();
        if (namespaces == null) return targets;
        for (String namespace : namespaces) {
            if (isTarget(namespace)) targets.add(namespace);
        }
        return targets;
    }

    /** 从命名空间集合里挑出被排除的命名空间，按字母序 */
    public Set<String> ignoredOf(Iterable<String> namespaces) {
        Set<String> ignored = new TreeSet<>();
        if (namespaces == null) return ignored;
        for (String namespace : namespaces) {
            if (namespace != null && !namespace.isBlank() && !isTarget(namespace)) ignored.add(namespace);
        }
        return ignored;
    }

    /** 按资源路径判定分类 */
    public static ResourceCategory categoryOf(String path) {
        return ResourceCategory.ofPath(path);
    }

    private static Set<String> collectModNamespaces() {
        Set<String> result = new TreeSet<>();
        try {
            for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
                String id = mod.getMetadata().getId();
                if (id == null || id.isBlank()) continue;
                String lower = id.toLowerCase(Locale.ROOT);
                result.add(lower);
                result.add(lower.replace('-', '_'));
            }
        } catch (Throwable ignored) {
            // 模组列表不可用（极端早期阶段）时只用内置基线
        }
        return Collections.unmodifiableSet(result);
    }
}
