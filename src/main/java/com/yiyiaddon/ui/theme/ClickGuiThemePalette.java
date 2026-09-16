package com.yiyiaddon.ui.theme;

/**
 * 界面主题调色板：一个主题的全部语义色槽。
 *
 * 颜色字面量只允许出现在各主题类与 {@code ClickGuiThemeColors} 本身（开发习惯第 141 条），
 * 组件与页面一律经 {@code ClickGuiThemeColors.current()} 取色。
 *
 * @param windowBackground    主窗口背景
 * @param sidebarBackground   侧栏背景
 * @param contentBackground   内容区背景
 * @param moduleBackground    模块卡片背景
 * @param subModuleBackground 子模块（展开行）背景
 * @param accent              强调色（选中 / 激活）
 * @param primaryText         主文字色
 * @param secondaryText       次要文字色
 * @param border              边框色
 */
public record ClickGuiThemePalette(
        int windowBackground,
        int sidebarBackground,
        int contentBackground,
        int moduleBackground,
        int subModuleBackground,
        int accent,
        int primaryText,
        int secondaryText,
        int border
) {
}
