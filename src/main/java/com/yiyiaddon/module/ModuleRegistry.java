package com.yiyiaddon.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块注册表。
 *
 * <p>功能模块在启动时把自己的 {@link ModuleEntry} 注册进来，模块中心与列表页只读取这里的缓存，
 * 不在打开页面时重新扫描任何东西。</p>
 */
public final class ModuleRegistry {

    private static final Map<String, ModuleEntry> BY_ID = new LinkedHashMap<>();
    private static final Map<String, List<ModuleEntry>> BY_CATEGORY = new HashMap<>();
    private static volatile List<ModuleEntry> sortedCache;

    private ModuleRegistry() {
    }

    /** 注册模块；ID 重复时覆盖旧条目。 */
    public static synchronized void register(ModuleEntry entry) {
        if (entry == null || entry.id() == null || entry.id().isBlank()) return;
        BY_ID.put(entry.id(), entry);
        invalidate();
    }

    public static synchronized ModuleEntry byId(String id) {
        return id == null ? null : BY_ID.get(id);
    }

    /** 全部模块，先按分类权重、再按模块权重排序。 */
    public static List<ModuleEntry> all() {
        List<ModuleEntry> cache = sortedCache;
        if (cache != null) return cache;
        synchronized (ModuleRegistry.class) {
            if (sortedCache == null) {
                List<ModuleEntry> list = new ArrayList<>(BY_ID.values());
                list.sort(Comparator
                        .comparingInt((ModuleEntry e) -> categoryOrder(e.categoryId()))
                        .thenComparingInt(ModuleEntry::order)
                        .thenComparing(ModuleEntry::id));
                sortedCache = List.copyOf(list);
            }
            return sortedCache;
        }
    }

    /** 指定分类下的模块，按权重升序。 */
    public static List<ModuleEntry> byCategory(String categoryId) {
        if (categoryId == null) return List.of();
        synchronized (ModuleRegistry.class) {
            List<ModuleEntry> cached = BY_CATEGORY.get(categoryId);
            if (cached != null) return cached;

            List<ModuleEntry> list = new ArrayList<>();
            for (ModuleEntry entry : all()) {
                if (categoryId.equals(entry.categoryId())) list.add(entry);
            }
            List<ModuleEntry> result = List.copyOf(list);
            BY_CATEGORY.put(categoryId, result);
            return result;
        }
    }

    public static int count() {
        return all().size();
    }

    public static int countIn(String categoryId) {
        return byCategory(categoryId).size();
    }

    public static boolean isEmpty() {
        return all().isEmpty();
    }

    public static synchronized void clear() {
        BY_ID.clear();
        invalidate();
    }

    private static int categoryOrder(String categoryId) {
        ModuleCategory category = CategoryRegistry.byId(categoryId);
        return category == null ? Integer.MAX_VALUE : category.order();
    }

    private static void invalidate() {
        sortedCache = null;
        BY_CATEGORY.clear();
    }
}
