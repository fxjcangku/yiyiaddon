package com.yiyiaddon.ui.theme;

import com.yiyiaddon.config.AddonConfig;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ClickGuiThemeManager {
    private static final Map<String, ClickGuiTheme> THEMES = new LinkedHashMap<>();
    /**
     * 只留两个主题（用户 2026-09-21：「浅色 深色 两个主题就行」）。
     *
     * <p>兜底固定为深空灰：配置里指向已下线主题 id（{@code apple_dark} / {@code dark} / {@code gray}）
     * 时，{@link #applyConfig()} 会落到它并顺手把配置改写成新 id，不会出现「主题丢了」。</p>
     */
    private static final ClickGuiTheme FALLBACK = new DeepGrayTheme();
    private static ClickGuiTheme currentTheme;

    static {
        register(new WhiteTheme());
        register(FALLBACK);
        currentTheme = FALLBACK;
    }

    private ClickGuiThemeManager() {
    }

    public static void register(ClickGuiTheme theme) {
        if (theme == null || theme.id() == null || theme.id().isBlank()) return;
        THEMES.put(theme.id(), theme);
    }

    public static Collection<ClickGuiTheme> themes() {
        return List.copyOf(THEMES.values());
    }

    public static ClickGuiTheme current() {
        return currentTheme;
    }

    public static boolean select(String id) {
        ClickGuiTheme theme = THEMES.get(id);
        if (theme == null) return false;
        currentTheme = theme;
        return true;
    }

    public static Optional<ClickGuiTheme> find(String id) {
        return Optional.ofNullable(THEMES.get(id));
    }

    /** 当前选中主题的 id。 */
    public static String currentId() {
        return currentTheme == null ? FALLBACK.id() : currentTheme.id();
    }

    /** 根据 {@link AddonConfig#uiTheme} 应用已保存的主题选择。 */
    public static void applyConfig() {
        String id = AddonConfig.uiTheme;
        if (id == null || id.isBlank() || !select(id)) {
            currentTheme = FALLBACK;
            AddonConfig.uiTheme = FALLBACK.id();
            AddonConfig.save();
        }
    }

    /** 选择主题并持久化到配置文件。 */
    public static boolean selectAndSave(String id) {
        if (!select(id)) return false;
        AddonConfig.uiTheme = id;
        AddonConfig.save();
        return true;
    }
}
