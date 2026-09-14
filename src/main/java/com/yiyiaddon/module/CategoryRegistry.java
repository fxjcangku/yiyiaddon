package com.yiyiaddon.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类注册表。
 *
 * <p>分类在模组初始化时注册，UI 只读取，不在任何页面里硬编码分类清单。
 * 排序结果按 order 缓存，注册新分类时才失效。</p>
 */
public final class CategoryRegistry {

    /**
     * 本项目唯一的模块分组 ID：全部功能模块统一归入这一个分组，不再按功能划分多个分类。
     *
     * <p>模块构造时传本常量作为 {@code categoryId}，保证模块归属与注册的分类始终一致。</p>
     */
    public static final String MODULE_GROUP_ID = "modules";

    private static final Comparator<ModuleCategory> BY_ORDER =
            Comparator.comparingInt(ModuleCategory::order).thenComparing(ModuleCategory::id);

    private static final Map<String, ModuleCategory> BY_ID = new LinkedHashMap<>();
    private static volatile List<ModuleCategory> sortedCache;

    private CategoryRegistry() {
    }

    /** 注册分类；ID 重复时覆盖旧条目。 */
    public static synchronized void register(ModuleCategory category) {
        if (category == null || category.id() == null || category.id().isBlank()) return;
        BY_ID.put(category.id(), category);
        sortedCache = null;
    }

    public static synchronized ModuleCategory byId(String id) {
        return id == null ? null : BY_ID.get(id);
    }

    public static boolean exists(String id) {
        return byId(id) != null;
    }

    /** 全部分类，按排序权重升序。 */
    public static List<ModuleCategory> all() {
        List<ModuleCategory> cache = sortedCache;
        if (cache != null) return cache;
        synchronized (CategoryRegistry.class) {
            if (sortedCache == null) {
                List<ModuleCategory> list = new ArrayList<>(BY_ID.values());
                list.sort(BY_ORDER);
                sortedCache = List.copyOf(list);
            }
            return sortedCache;
        }
    }

    public static int count() {
        return all().size();
    }

    public static synchronized void clear() {
        BY_ID.clear();
        sortedCache = null;
    }
}
