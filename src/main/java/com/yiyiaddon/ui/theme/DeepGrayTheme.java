package com.yiyiaddon.ui.theme;

/**
 * 深空灰主题：深灰底 + 苹果系统蓝强调，本项目唯一的暗色主题。
 *
 * <p>调色板取自 iOS 暗色模式公开系统色：卡片用 secondarySystemBackground，
 * 次级表面用 tertiarySystemBackground，强调色用暗色模式 systemBlue，
 * 边框用 systemGray4，文字为 label / secondaryLabel。</p>
 *
 * <p>窗口与侧栏底色压到接近纯黑，配合面板模糊与 {@code SkiaBlurRenderer} 的主题色蒙版后
 * 呈现「深空灰」的毛玻璃底板（蒙版强度见 {@code glassTint}：暗色主题给足，否则明亮场景
 * 会把面板冲成浅灰）；卡片本身不使用玻璃，靠明度差与高光描边分出层次。</p>
 */
public final class DeepGrayTheme implements ClickGuiTheme {

    private static final ClickGuiThemePalette PALETTE = new ClickGuiThemePalette(
            0xFF0C0C10,   // 窗口底座
            0xFF14141A,   // 左栏：略亮于右栏
            0xFF0E0E13,   // 右栏内容面板
            0xFF1A1A21,   // 卡片
            0xFF22222A,   // 次级表面
            0xFF0A84FF,   // 强调色 systemBlue（暗色）
            0xFFFFFFFF,   // label
            0xFF9E9EA7,   // secondaryLabel
            0xFF303038    // 边框
    );

    private static final ClickGuiThemeMetrics METRICS = new ClickGuiThemeMetrics(22f, 16f, 12f, 1f, 28f);

    @Override
    public String id() {
        return "deep_gray";
    }

    @Override
    public String displayName() {
        return "深色";
    }

    @Override
    public ClickGuiThemePalette palette() {
        return PALETTE;
    }

    @Override
    public ClickGuiThemeMetrics metrics() {
        return METRICS;
    }
}
