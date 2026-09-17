package com.yiyiaddon.ui.theme;

import com.yiyiaddon.config.AddonConfig;

/**
 * 颜色派生层：将 {@link ClickGuiThemePalette} 的 9 个核心色映射到 GUI 全部具体色槽。
 * 次要颜色（指示条、悬停、滚动条、搜索框、危险按钮等）根据主题明暗自动派生，
 * 使每个主题只需声明 9 个核心色即可完整覆盖所有 GUI 选项。
 *
 * <p>所有颜色均为 RGB（不含 alpha），使用方通过 {@code withAlpha(color, alpha)} 叠加透明度。</p>
 */
public final class ClickGuiThemeColors {
    /** 主题不可变，缓存派生色避免每个控件每帧重复分配整套色槽。 */
    private static ClickGuiTheme cachedTheme;
    private static ClickGuiThemeColors cachedColors;

    // —— 核心色（直接来自调色板） ——
    public final int window;
    public final int sidebar;
    public final int content;
    public final int module;
    public final int subModule;
    public final int accent;
    public final int primaryText;
    public final int secondaryText;
    public final int border;

    // —— iOS 语义层：玻璃高光、阴影、文字三级、填充 ——
    public final int separator;        // 分隔线，比边框轻
    public final int rim;              // 玻璃高光内描边
    public final int shadow;           // 阴影基色，不含 alpha
    public final int accentOn;         // 强调色之上的反色文字
    public final int labelTertiary;    // 三级文字
    public final int labelQuaternary;  // 四级文字
    public final int surfaceHover;     // 卡片/行悬停填充
    public final int field;            // 输入框等弱底填充

    // —— 派生色：文字 ——
    public final int mutedText;        // 箭头、调试次级文本等更弱化的文字
    public final int inactiveText;      // 未选中 Tab 文字
    public final int inactiveIcon;      // 未选中 Tab 图标
    public final int subModuleText;     // 子模块标题文字

    // —— 派生色：背景/指示 ——
    public final int indicator;        // 选中 Tab 指示条背景
    public final int hoverBackground;  // Tab/控件悬停背景
    public final int buttonBackground;  // 普通按钮背景
    public final int buttonText;        // 普通按钮文字

    // —— 派生色：滚动条 ——
    public final int scrollbarTrack;
    public final int scrollbarThumb;

    // —— 派生色：搜索框 ——
    // 注：搜索框的「底」不在这里——它走 GlassPanel#textField（与同页的行同一套霜化玻璃），
    // 实心填色会在霜化玻璃行之间显得像贴上去的深色板（用户 2026-09-16 反馈）。
    public final int searchIcon;
    public final int searchCursor;
    public final int searchText;
    public final int searchTextPlaceholder;

    // —— 派生色：危险按钮（关闭/重置悬停态） ——
    public final int dangerHoverBackground;
    public final int dangerHoverText;

    // —— 派生色：快捷键徽章（常态 / 悬停 / 解绑悬停） ——
    public final int keybindBackground;
    public final int keybindHoverBackground;
    public final int keybindUnbindBackground;

    // —— 派生色：模块启用状态（用户 2026-09-17：「模块启用变成绿色 没启用变成红色」） ——
    /** 模块「已启用 / 运行中」的状态色（绿）：模块中心的圆点与文字、模块页顶部状态条共用 */
    public final int stateOn;
    /** 模块「未启用」的状态色（红）：与 {@link #stateOn} 同一组语义的两个取值 */
    public final int stateOff;

    // —— 派生色：调色板 ——
    /** 透明度棋盘格的浅格 */
    public final int checkerLight;
    /** 透明度棋盘格的深格 */
    public final int checkerDark;
    /** 拾色器指示器（色相游标、面板游标、选中描边） */
    public final int pickerIndicator;

    public final boolean dark;

    private ClickGuiThemeColors(
            int window, int sidebar, int content, int module, int subModule,
            int accent, int primaryText, int secondaryText, int border,
            int separator, int rim, int shadow, int accentOn,
            int labelTertiary, int labelQuaternary, int surfaceHover, int field,
            int mutedText, int inactiveText, int inactiveIcon, int subModuleText,
            int indicator, int hoverBackground, int buttonBackground, int buttonText,
            int scrollbarTrack, int scrollbarThumb,
            int searchIcon,
            int searchCursor, int searchText, int searchTextPlaceholder,
            int dangerHoverBackground, int dangerHoverText,
            int keybindBackground, int keybindHoverBackground, int keybindUnbindBackground,
            int stateOn, int stateOff,
            int checkerLight, int checkerDark, int pickerIndicator,
            boolean dark) {
        this.window = window;
        this.sidebar = sidebar;
        this.content = content;
        this.module = module;
        this.subModule = subModule;
        this.accent = accent;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
        this.border = border;
        this.separator = separator;
        this.rim = rim;
        this.shadow = shadow;
        this.accentOn = accentOn;
        this.labelTertiary = labelTertiary;
        this.labelQuaternary = labelQuaternary;
        this.surfaceHover = surfaceHover;
        this.field = field;
        this.mutedText = mutedText;
        this.inactiveText = inactiveText;
        this.inactiveIcon = inactiveIcon;
        this.subModuleText = subModuleText;
        this.indicator = indicator;
        this.hoverBackground = hoverBackground;
        this.buttonBackground = buttonBackground;
        this.buttonText = buttonText;
        this.scrollbarTrack = scrollbarTrack;
        this.scrollbarThumb = scrollbarThumb;
        this.searchIcon = searchIcon;
        this.searchCursor = searchCursor;
        this.searchText = searchText;
        this.searchTextPlaceholder = searchTextPlaceholder;
        this.dangerHoverBackground = dangerHoverBackground;
        this.dangerHoverText = dangerHoverText;
        this.keybindBackground = keybindBackground;
        this.keybindHoverBackground = keybindHoverBackground;
        this.keybindUnbindBackground = keybindUnbindBackground;
        this.stateOn = stateOn;
        this.stateOff = stateOff;
        this.checkerLight = checkerLight;
        this.checkerDark = checkerDark;
        this.pickerIndicator = pickerIndicator;
        this.dark = dark;
    }

    /** 给 RGB 叠上 0-1 的透明度。界面与渲染的取色统一走这里，禁止各组件自己写位运算。 */
    public static int withAlpha(int rgb, float alpha) {
        float value = alpha < 0f ? 0f : (alpha > 1f ? 1f : alpha);
        return (((int) (value * 255f)) << 24) | (rgb & 0xFFFFFF);
    }

    /** 取已有 ARGB 的透明度再乘一个系数，用于整体淡出时保留颜色自身的透明度。 */
    public static int scaleAlpha(int argb, float factor) {
        float value = factor < 0f ? 0f : (factor > 1f ? 1f : factor);
        int base = (argb >>> 24) & 0xFF;
        return (((int) (base * value)) << 24) | (argb & 0xFFFFFF);
    }

    /**
     * 根据主题解析全部颜色槽。
     */
    public static ClickGuiThemeColors of(ClickGuiTheme theme) {
        if (theme == null) {
            theme = new WhiteTheme();
        }
        ClickGuiThemePalette p = theme.palette();
        boolean dark = luminance(p.primaryText()) > luminance(p.windowBackground());

        int primaryText = rgb(p.primaryText());
        // 玻璃背景会引入场景明暗变化，次级文字向主文字收拢以保留可读性。
        int secondaryText = mix(rgb(p.secondaryText()), primaryText, dark ? 0.18f : 0.38f);
        int window = rgb(p.windowBackground());
        int sidebar = rgb(p.sidebarBackground());
        int accent = rgb(p.accent());
        int border = rgb(p.border());

        // 文字派生：弱化与次级
        int mutedText = mix(secondaryText, dark ? window : 0x000000, dark ? 0.35f : 0.25f);
        int inactiveText = mix(primaryText, dark ? window : 0x000000, dark ? 0.45f : 0.40f);
        int inactiveIcon = mix(secondaryText, dark ? window : 0x000000, dark ? 0.25f : 0.20f);
        int subModuleText = mix(primaryText, dark ? window : 0x000000, dark ? 0.14f : 0.12f);

        // 指示/悬停：accent 极淡地叠加在窗口底色上
        int indicator = mix(window, accent, dark ? 0.22f : 0.20f);
        int hoverBackground = mix(window, accent, dark ? 0.12f : 0.15f);

        // 按钮：取模块底色附近
        int buttonBackground = mix(rgb(p.moduleBackground()), dark ? window : 0x000000, dark ? 0.20f : 0.06f);
        int buttonText = secondaryText;

        // 滚动条
        int scrollbarTrack = mix(border, window, 0.5f);
        int scrollbarThumb = mix(secondaryText, dark ? window : 0x000000, dark ? 0.20f : 0.10f);

        // 搜索框：底色不在这里（走 GlassPanel#textField），只留文字与图标几支派生色
        int searchIcon = secondaryText;
        int searchCursor = accent;
        int searchText = primaryText;
        int searchTextPlaceholder = mix(secondaryText, window, 0.15f);

        // 危险按钮（关闭/重置悬停）：语义红色，按明暗自适应
        int dangerHoverBackground = dark ? 0x3D2226 : 0xFFE5E5;
        int dangerHoverText = dark ? 0xFF6B6B : 0xCC2222;

        // 快捷键徽章：常态/悬停用中性灰，解绑悬停用语义红；按明暗自适应
        int keybindBackground = dark ? 0x777777 : 0x555555;
        int keybindHoverBackground = dark ? 0x949494 : 0x777777;
        int keybindUnbindBackground = dark ? 0xE14D4D : 0xCC3333;

        // 模块启用状态：语义沿用第 114 条的「成功绿 / 失败红」，按明暗自适应 ——
        // 暗色底取亮一档（霜化玻璃会压暗文字），浅色底取压深一档（亮色在上面不可读）。
        int stateOn = dark ? 0x5BD37A : 0x1E8E4A;
        int stateOff = dark ? 0xFF6B6B : 0xCC2222;

        // 调色板：棋盘格用于表现透明度，按明暗自适应；拾色游标取与底色高对比的一端
        int checkerLight = dark ? 0x8A8A8A : 0xC9C9C9;
        int checkerDark = dark ? 0x5C5C5C : 0xA8A8A8;
        int pickerIndicator = dark ? 0xFFFFFF : 0x1A1A1A;

        // iOS 语义层：分隔线取边框的弱化版；高光恒为白，靠 alpha 控制强度；
        // 强调色上的文字按强调色亮度取黑或白，保证对比度。
        int separator = mix(border, dark ? 0x000000 : 0xFFFFFF, dark ? 0.35f : 0.45f);
        int rim = 0xFFFFFF;
        int shadow = 0x000000;
        int accentOn = luminance(accent) > 0.62f ? 0x000000 : 0xFFFFFF;
        int labelTertiary = mix(secondaryText, window, 0.12f);
        int labelQuaternary = mix(secondaryText, window, 0.60f);
        int surfaceHover = mix(rgb(p.moduleBackground()), dark ? 0xFFFFFF : accent, dark ? 0.12f : 0.07f);
        int field = mix(sidebar, dark ? 0xFFFFFF : 0x000000, dark ? 0.06f : 0.04f);

        return new ClickGuiThemeColors(
                window, sidebar, rgb(p.contentBackground()), rgb(p.moduleBackground()), rgb(p.subModuleBackground()),
                accent, primaryText, secondaryText, border,
                separator, rim, shadow, accentOn, labelTertiary, labelQuaternary, surfaceHover, field,
                mutedText, inactiveText, inactiveIcon, subModuleText,
                indicator, hoverBackground, buttonBackground, buttonText,
                scrollbarTrack, scrollbarThumb,
                searchIcon,
                searchCursor, searchText, searchTextPlaceholder,
                dangerHoverBackground, dangerHoverText,
                keybindBackground, keybindHoverBackground, keybindUnbindBackground,
                stateOn, stateOff,
                checkerLight, checkerDark, pickerIndicator,
                dark);
    }

    /** 取当前已选主题的颜色。 */
    public static ClickGuiThemeColors current() {
        ClickGuiTheme theme = ClickGuiThemeManager.current();
        if (cachedColors == null || cachedTheme != theme) {
            cachedTheme = theme;
            cachedColors = of(theme);
        }
        return cachedColors;
    }

    /** 模糊开启时保留场景透光，关闭时恢复主题声明的不透明度。 */
    public static float panelBackgroundAlpha(float alpha) {
        return AddonConfig.panelBlur ? alpha * 0.46f : alpha;
    }

    // —— 颜色数学工具 ——

    /** 取 RGB（丢弃 alpha 字节）。 */
    private static int rgb(int argb) {
        return argb & 0x00FFFFFF;
    }

    /** 相对亮度（0~1）。 */
    private static float luminance(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return (0.2126f * r + 0.7152f * g + 0.0722f * b) / 255f;
    }

    /** 在 a 与 b 之间按 t 线性混合（仅 RGB 通道）。 */
    private static int mix(int a, int b, float t) {
        int ar = (a >> 16) & 0xFF, ag = (a >> 8) & 0xFF, ab = a & 0xFF;
        int br = (b >> 16) & 0xFF, bg = (b >> 8) & 0xFF, bb = b & 0xFF;
        int r = Math.round(ar + (br - ar) * t);
        int g = Math.round(ag + (bg - ag) * t);
        int bl = Math.round(ab + (bb - ab) * t);
        return (r << 16) | (g << 8) | bl;
    }
}
