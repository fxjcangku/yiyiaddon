package com.yiyiaddon.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 后端下发的远程开关。
 *
 * <p>语义与后端约定一致：未知键默认「开启」，只有值明确为 {@code false}（忽略大小写）才视为关闭，
 * 因此后端不可达时全部功能保持可用。</p>
 */
public final class RemoteFlags {

    private final Map<String, String> values = new ConcurrentHashMap<>();

    public void replace(Map<String, String> source) {
        values.clear();
        if (source != null) values.putAll(source);
    }

    public boolean enabled(String key) {
        String value = values.get(key);
        return value == null || !value.trim().equalsIgnoreCase("false");
    }

    public String text(String key, String fallback) {
        String value = values.get(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
