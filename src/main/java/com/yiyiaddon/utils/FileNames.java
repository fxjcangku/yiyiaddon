package com.yiyiaddon.utils;

/**
 * 文本与文件名工具。
 *
 * <p>游戏内文本普遍带 {@code §} 颜色代码，而 Windows 文件名不允许 {@code \ / : * ? " < > |}
 * 与控制字符。本类提供统一的清洗入口，避免每个持久化层各写一份正则（旧实现中该逻辑被复制了多份）。</p>
 *
 * <p>本类为纯函数工具，不含任何业务语义、不访问任何游戏状态。</p>
 */
public final class FileNames {

    /** 颜色代码：{@code §0-§f}、{@code §k-§o}、{@code §r}、{@code §x} */
    private static final String COLOR_PATTERN = "§[0-9a-fk-orA-FK-ORx]";

    private FileNames() {
    }

    /** 剥离颜色代码并去首尾空格 */
    public static String cleanColors(String text) {
        if (text == null) return "";
        return text.replaceAll(COLOR_PATTERN, "").trim();
    }

    /**
     * 清洗为合法文件名主体。
     *
     * <p>依次执行：剥离颜色代码 → 剔除 Windows 非法字符与控制字符 → 压缩连续空白 → 去掉首尾点与空格
     * （Windows 不允许以点或空格结尾）→ 结果为空时回退到兜底值。</p>
     */
    public static String sanitize(String name, String fallback) {
        if (name == null || name.isBlank()) return fallback;
        String cleaned = name.replaceAll(COLOR_PATTERN, "");
        cleaned = cleaned.replaceAll("[\\\\/:*?\"<>|\\x00-\\x1f]", "");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();
        cleaned = cleaned.replaceAll("^[. ]+|[. ]+$", "");
        return cleaned.isBlank() ? fallback : cleaned;
    }
}
