package com.yiyiaddon.ui;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 界面文本与环境无关的英中对照表。
 *
 * <p>{@link #t(String, String)} 登记中英对照并返回中文（yiyiaddon 以中文为默认界面语言）。
 * 对照表同时被 {@link #matchesSearch(String, String)} 使用，使搜索框既能匹配中文也能匹配英文。</p>
 */
public final class UiText {
    private static final Map<String, String> COUNTERPARTS = new ConcurrentHashMap<>();

    private UiText() {
    }

    public static String t(String zh, String en) {
        COUNTERPARTS.put(zh, en);
        COUNTERPARTS.put(en, zh);
        return zh;
    }

    public static boolean matchesSearch(String text, String query) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String normalizedText = text.toLowerCase(Locale.ROOT);
        String compactQuery = query.replaceAll("\\s+", "");
        if (normalizedText.contains(query) || normalizedText.replaceAll("\\s+", "").contains(compactQuery)) {
            return true;
        }
        String counterpart = COUNTERPARTS.get(text);
        if (counterpart == null) {
            return false;
        }
        String normalizedCounterpart = counterpart.toLowerCase(Locale.ROOT);
        return normalizedCounterpart.contains(query) || normalizedCounterpart.replaceAll("\\s+", "").contains(compactQuery);
    }
}
