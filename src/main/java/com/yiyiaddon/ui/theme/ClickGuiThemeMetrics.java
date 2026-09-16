package com.yiyiaddon.ui.theme;

/**
 * 界面主题的度量尺寸（单位：设计像素）。
 *
 * @param windowRadius    主窗口圆角半径
 * @param moduleRadius    模块卡片圆角半径
 * @param subModuleRadius 子模块（展开行）圆角半径
 * @param borderWidth     边框线宽
 * @param shadowBlur      阴影模糊半径
 */
public record ClickGuiThemeMetrics(
        float windowRadius,
        float moduleRadius,
        float subModuleRadius,
        float borderWidth,
        float shadowBlur
) {
}
