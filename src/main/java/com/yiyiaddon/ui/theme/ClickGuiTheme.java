package com.yiyiaddon.ui.theme;

/**
 * 界面主题接口：一个主题 = 标识 + 中文名 + 调色板（颜色）+ 度量（圆角 / 边框 / 阴影等尺寸）。
 *
 * 实现类只提供颜色与尺寸数据；绘制时统一经 {@code ClickGuiThemeColors} 取色，
 * 组件与页面内禁止出现颜色字面量（开发习惯第 141 条）。
 */
public interface ClickGuiTheme {
    String id();
    String displayName();
    ClickGuiThemePalette palette();
    ClickGuiThemeMetrics metrics();
}
