package com.yiyiaddon.platform.resource;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资源枚举适配层：唯一接触 Minecraft 资源管理接口的地方，只向外输出纯数据。
 *
 * <p>枚举范围是客户端资源包类型下的<b>全部</b>已加载资源（原版包 + 模组包 + 本地资源包 +
 * 服务器资源包），并由原版资源管理器按优先级合并，同 id 只保留优先级最高的那一份。因此本层
 * 拿到的就是「客户端当前真正生效的资源」，不需要自己去比较资源包优先级。</p>
 *
 * <p>枚举结果按 id 排序后再输出：指纹是按遍历顺序累加的，顺序必须稳定，否则同一份资源会
 * 算出不同指纹，缓存直接失效。</p>
 *
 * <p>失败不吞异常：资源管理器不可用或枚举抛错时返回带原因的失败快照，由上层如实上报，
 * 绝不把「枚举失败」伪装成「服务器没有资源」。</p>
 */
public final class ResourceEnumerator {

    private ResourceEnumerator() {
    }

    /**
     * 一条已加载资源。
     *
     * @param namespace 命名空间，形如 {@code customcrops}
     * @param path      路径，形如 {@code blockstates/tomato_stage_0.json}
     * @param resource  资源句柄，用于按需读取内容
     */
    public record Entry(String namespace, String path, Resource resource) {

        /** 完整资源标识 */
        public String id() {
            return namespace + ":" + path;
        }
    }

    /**
     * 一次枚举的快照。
     *
     * @param namespaces    当前资源管理器报告的全部命名空间
     * @param entries       全部资源（已按 id 排序）
     * @param failureReason 失败原因；成功为 {@code null}
     */
    public record Snapshot(Set<String> namespaces, List<Entry> entries, String failureReason) {

        public boolean ok() {
            return failureReason == null;
        }

        public static Snapshot failure(String reason) {
            return new Snapshot(Set.of(), List.of(), reason);
        }
    }

    /** 枚举当前客户端已加载的全部资源 */
    public static Snapshot list() {
        ResourceManager manager = ResourcePackAccess.resourceManager();
        if (manager == null) return Snapshot.failure("资源管理器不可用");

        try {
            Set<String> namespaces = new LinkedHashSet<>(manager.getNamespaces());
            Map<Identifier, Resource> resources = manager.listResources("", id -> true);
            List<Entry> entries = new ArrayList<>(resources.size());
            for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
                Identifier id = entry.getKey();
                Resource resource = entry.getValue();
                if (id == null || resource == null) continue;
                entries.add(new Entry(id.getNamespace(), id.getPath(), resource));
            }
            entries.sort(Comparator.comparing(Entry::id));
            return new Snapshot(namespaces, entries, null);
        } catch (Throwable error) {
            return Snapshot.failure("枚举资源异常：" + error.getClass().getSimpleName()
                + (error.getMessage() == null ? "" : "：" + error.getMessage()));
        }
    }
}
